package band.effective.office.smsrouter.data.repository

import band.effective.office.smsrouter.data.SmsApiService
import band.effective.office.smsrouter.data.mapper.SmsDataDtoMapper
import band.effective.office.smsrouter.domain.Either
import band.effective.office.smsrouter.domain.ErrorResponse
import band.effective.office.smsrouter.domain.model.CustomWebhookConfig
import band.effective.office.smsrouter.domain.model.SmsData
import band.effective.office.smsrouter.domain.model.WebhookType
import band.effective.office.smsrouter.domain.repository.SmsForwardingRepository

internal class SmsForwardingRepositoryImpl(
    private val smsApiService: SmsApiService,
    private val smsDataDtoMapper: SmsDataDtoMapper,
) : SmsForwardingRepository {
    override suspend fun forwardSms(
        url: String,
        secretKey: String,
        smsData: SmsData,
        webhookType: WebhookType,
        chatId: String,
        customConfig: CustomWebhookConfig,
        smsId: String,
        onRetry: ((smsId: String, retryCount: Int) -> Unit)?,
    ): Either<ErrorResponse, Unit> {
        return smsApiService.sendSms(
            url = url,
            secretKey = secretKey,
            body = smsDataDtoMapper.map(smsData, webhookType, chatId, customConfig),
            customHeaders = customConfig.headers,
            smsId = smsId,
            onRetry = onRetry
        )
    }
}
