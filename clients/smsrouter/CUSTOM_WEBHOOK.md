# Custom Webhook Feature

This document describes the custom webhook feature for SMS Router Android application.

## Overview

The custom webhook feature allows you to send SMS data to any webhook endpoint with completely customizable request format. You can define:
- Custom HTTP headers
- Custom JSON body template with variable substitution
- Any webhook URL

## How to Use

1. **Open Settings Screen** in the SMS Router app
2. **Select "CUSTOM"** from the Webhook Service dropdown for your SIM card
3. **Configure the following fields:**

### Webhook URL
Enter the full URL of your webhook endpoint:
```
https://your-webhook-endpoint.com/api/sms
```

### Custom Headers
Enter HTTP headers one per line in the format `HeaderName:HeaderValue`:
```
Authorization:Bearer your-token-here
Content-Type:application/json
X-Custom-Header:custom-value
```

### Body Template
Enter a JSON template for the request body. You can use variables that will be replaced with actual SMS data:
```json
{
  "message": "{message}",
  "from": "{sender}",
  "sim": "{simId}",
  "operator": "{operatorName}",
  "timestamp": "{timestamp}"
}
```

## Available Variables

The following variables can be used in the body template:

- `{message}` or `{messageBody}` - The SMS message content
- `{sender}` - The phone number that sent the SMS
- `{simId}` - The SIM card ID
- `{operatorName}` - The mobile operator name
- `{timestamp}` - Message timestamp in milliseconds

## Examples

### Example 1: Simple Webhook
**URL:** `https://webhook.site/your-unique-url`

**Headers:**
```
Content-Type:application/json
```

**Body Template:**
```json
{
  "text": "{message}",
  "sender": "{sender}"
}
```

### Example 2: Authenticated Webhook
**URL:** `https://api.example.com/webhooks/sms`

**Headers:**
```
Authorization:Bearer abc123xyz456
Content-Type:application/json
X-API-Key:your-api-key
```

**Body Template:**
```json
{
  "sms_content": "{message}",
  "sender_number": "{sender}",
  "sim_id": "{simId}",
  "operator": "{operatorName}",
  "received_at": "{timestamp}"
}
```

### Example 3: Slack-Compatible Format
**URL:** `https://hooks.slack.com/services/YOUR/WEBHOOK/URL`

**Headers:**
```
Content-Type:application/json
```

**Body Template:**
```json
{
  "text": "New SMS from {sender}: {message}",
  "username": "SMS Router",
  "icon_emoji": ":incoming_envelope:"
}
```

### Example 4: Discord-Compatible Format
**URL:** `https://discord.com/api/webhooks/YOUR_WEBHOOK_ID/YOUR_TOKEN`

**Headers:**
```
Content-Type:application/json
```

**Body Template:**
```json
{
  "content": "📱 New SMS from **{sender}**",
  "embeds": [{
    "description": "{message}",
    "color": 3447003,
    "footer": {
      "text": "SIM: {simId} | Operator: {operatorName}"
    }
  }]
}
```

## Technical Details

- The webhook **only sends HTTP POST requests** (other methods are not currently supported)
- Request timeout is 60 seconds
- Failed requests are retried up to 3 times
- Supports any valid JSON structure in the body template
- All headers are sent exactly as configured
- Variables are replaced at runtime before sending the request
- The `{timestamp}` variable represents the time when the SMS forwarding was initiated (in milliseconds since epoch)

## Troubleshooting

### SMS not being forwarded
1. Check that the Webhook URL is correct and accessible
2. Verify that custom headers are properly formatted (one per line, with colon separator)
3. Ensure the body template is valid JSON
4. Check the app logs for error messages

### Invalid JSON errors
- Make sure your body template is valid JSON
- Use double quotes for strings, not single quotes
- Ensure all braces and brackets are properly closed
- Test your JSON template in a JSON validator before using it

### Authentication failures
- Verify that your authentication headers are correct
- Check if your API token or key is still valid
- Ensure the header names match what your API expects (case-sensitive)

## Notes

- When CUSTOM webhook type is selected, the "Secret Key" field is not used
- Authentication should be configured via custom headers instead
- The feature is designed for maximum flexibility while remaining simple to use
- All variables are replaced with their actual values when an SMS is received
