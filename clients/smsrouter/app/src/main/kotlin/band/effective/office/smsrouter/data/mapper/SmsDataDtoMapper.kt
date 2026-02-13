package band.effective.office.smsrouter.data.mapper

import band.effective.office.smsrouter.data.models.CustomSmsDataRequest
import band.effective.office.smsrouter.data.models.MattermostSmsDataRequest
import band.effective.office.smsrouter.data.models.SmsDataRequest
import band.effective.office.smsrouter.data.models.TelegramSmsDataRequest
import band.effective.office.smsrouter.domain.model.CustomWebhookConfig
import band.effective.office.smsrouter.domain.model.SmsData
import band.effective.office.smsrouter.domain.model.WebhookType

internal class SmsDataDtoMapper {

    /**
     * Maps a domain SmsData object to a data layer SmsDataRequest object.
     *
     * @param domain The domain SmsData object to map.
     * @param webhookType The type of webhook service to use.
     * @param chatId The chat ID for Telegram webhooks (only used when webhookType is TELEGRAM).
     * @param customConfig The custom webhook configuration (only used when webhookType is CUSTOM).
     * @return The appropriate SmsDataRequest implementation based on the webhook type.
     */
    fun map(
        domain: SmsData,
        webhookType: WebhookType,
        chatId: String = "",
        customConfig: CustomWebhookConfig = CustomWebhookConfig()
    ): SmsDataRequest = when (webhookType) {
        WebhookType.MATTERMOST -> MattermostSmsDataRequest(
            text = domain.messageBody,
        )
        WebhookType.TELEGRAM -> TelegramSmsDataRequest(
            chat_id = chatId,
            text = domain.messageBody,
        )
        WebhookType.CUSTOM -> {
            // Perform variable substitution in the body template
            val bodyWithVariables = substituteVariables(customConfig.bodyTemplate, domain)
            CustomSmsDataRequest(jsonString = bodyWithVariables)
        }
    }

    /**
     * Substitutes variables in the template with actual SMS data.
     * 
     * Supported variables:
     * - {message} or {messageBody} - SMS message body
     * - {sender} - SMS sender phone number
     * - {simId} - SIM card ID
     * - {operatorName} - Mobile operator name
     * - {timestamp} - Message timestamp
     */
    private fun substituteVariables(template: String, smsData: SmsData): String {
        return template
            .replace("{message}", smsData.messageBody)
            .replace("{messageBody}", smsData.messageBody)
            .replace("{sender}", smsData.sender)
            .replace("{simId}", smsData.simId)
            .replace("{operatorName}", smsData.operatorName)
            .replace("{timestamp}", System.currentTimeMillis().toString())
    }
}
