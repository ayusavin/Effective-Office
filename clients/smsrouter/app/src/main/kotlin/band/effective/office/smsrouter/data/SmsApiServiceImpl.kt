package band.effective.office.smsrouter.data

import band.effective.office.shared.core.network.HttpRequestUtil
import band.effective.office.smsrouter.data.models.CustomSmsDataRequest
import band.effective.office.smsrouter.data.models.SmsDataRequest
import band.effective.office.smsrouter.domain.Either
import band.effective.office.smsrouter.domain.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

internal class SmsApiServiceImpl(
    private val client: HttpClient,
) : SmsApiService {

    companion object {
        // Map to store retry counts by SMS ID
        private val retryCountMap = mutableMapOf<String, Int>()

        // Get the current retry count for an SMS
        fun getRetryCount(smsId: String): Int {
            return retryCountMap[smsId] ?: 0
        }

        // Reset retry count for an SMS
        fun resetRetryCount(smsId: String) {
            retryCountMap.remove(smsId)
        }
    }

    override suspend fun sendSms(
        url: String,
        secretKey: String,
        body: SmsDataRequest,
        customHeaders: Map<String, String>,
        smsId: String,
        onRetry: ((smsId: String, retryCount: Int) -> Unit)?,
    ): Either<ErrorResponse, Unit> {
        var lastResult: HttpRequestUtil.Result<Unit>? = null
        val maxAttempts = 3
        val timeoutMillis = 60000L // 1 minute timeout for the entire process
        val startTime = System.currentTimeMillis()

        // Only track retries if smsId is provided
        if (smsId.isNotEmpty()) {
            // Initialize or increment retry count
            val currentRetries = retryCountMap[smsId] ?: 0
            retryCountMap[smsId] = currentRetries
        }

        for (attempt in 0..maxAttempts) {
            // Check if we've exceeded the timeout
            if (System.currentTimeMillis() - startTime >= timeoutMillis) {
                break // Exit the retry loop if we've exceeded the timeout
            }

            lastResult = HttpRequestUtil.request<Unit>(
                client = client,
                url = url,
                method = HttpRequestUtil.Method.POST,
            ) {
                contentType(ContentType.Application.Json)
                
                // Apply custom headers if provided, otherwise use default Bearer token
                if (customHeaders.isNotEmpty()) {
                    customHeaders.forEach { (key, value) ->
                        header(key, value)
                    }
                } else if (secretKey.isNotEmpty()) {
                    header(HttpHeaders.Authorization, "Bearer $secretKey")
                }
                
                // Set body - handle custom JSON specially
                when (body) {
                    is CustomSmsDataRequest -> {
                        // For custom webhooks, send the raw JSON string
                        setBody(body.jsonString)
                    }
                    else -> {
                        // For standard webhooks, use normal serialization
                        setBody(body)
                    }
                }
            }

            when (lastResult) {
                is HttpRequestUtil.Result.Success -> {
                    // Reset retry count on success if smsId is provided
                    if (smsId.isNotEmpty()) {
                        resetRetryCount(smsId)
                    }
                    return Either.Success(Unit)
                }
                is HttpRequestUtil.Result.Error -> {
                    if (attempt < maxAttempts) {
                        // Increment retry count if smsId is provided
                        if (smsId.isNotEmpty()) {
                            val currentRetries = retryCountMap[smsId] ?: 0
                            val newRetryCount = currentRetries + 1
                            retryCountMap[smsId] = newRetryCount

                            // Notify about retry via callback
                            onRetry?.invoke(smsId, newRetryCount)
                        }

                        val elapsedTime = System.currentTimeMillis() - startTime
                        val remainingTime = timeoutMillis - elapsedTime
                        if (remainingTime <= 0) break // Exit the retry loop if no time remains
                    }
                }
            }
        }

        // If we get here, all attempts failed or timeout occurred
        val errorResult = lastResult as? HttpRequestUtil.Result.Error
            ?: return Either.Error(ErrorResponse(408, "Request timeout after $timeoutMillis ms"))

        return Either.Error(ErrorResponse(errorResult.code, errorResult.message))
    }
}
