package band.effective.office.smsrouter.domain.model

import kotlinx.serialization.Serializable

/**
 * Data class representing the settings for the SMS router.
 *
 * @property simCards List of SIM cards with their webhook URLs and secret keys
 */
@Serializable
data class Settings(
    val simCards: List<SimCardSettings> = emptyList()
)

/**
 * Enum representing the type of webhook service.
 */
@Serializable
enum class WebhookType {
    MATTERMOST,
    TELEGRAM,
    CUSTOM
}

/**
 * Data class representing custom webhook configuration.
 *
 * @property headers Map of custom HTTP headers (e.g., "Authorization" -> "Bearer token")
 * @property bodyTemplate JSON body template with variable placeholders (e.g., {"message": "{message}", "sender": "{sender}"})
 */
@Serializable
data class CustomWebhookConfig(
    val headers: Map<String, String> = emptyMap(),
    val bodyTemplate: String = ""
)

/**
 * Data class representing the settings for a SIM card.
 *
 * @property simId Unique identifier for the SIM card
 * @property simName Name of the SIM card
 * @property webhookUrl URL to which SMS messages will be forwarded
 * @property secretKey Secret key for webhook authorization
 * @property webhookType Type of webhook service (Mattermost or Telegram)
 * @property chatId Chat ID for Telegram webhook (only used when webhookType is TELEGRAM)
 * @property customWebhookConfig Configuration for custom webhook (only used when webhookType is CUSTOM)
 */
@Serializable
data class SimCardSettings(
    val simId: String,
    val simName: String,
    val webhookUrl: String = "",
    val secretKey: String = "",
    val webhookType: WebhookType = WebhookType.MATTERMOST,
    val chatId: String = "",
    val customWebhookConfig: CustomWebhookConfig = CustomWebhookConfig()
)
