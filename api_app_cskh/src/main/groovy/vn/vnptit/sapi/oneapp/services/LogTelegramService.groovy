package vn.vnptit.sapi.oneapp.services

import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged

@Component
public class LogTelegramService {

    private static final Logger logger = LoggerFactory.getLogger(LogTelegramService.class)

    // TODO: Fill in your Telegram Bot Token (get from @BotFather)
    private static final String BOT_TOKEN = "8597251602:AAEdp5_PuPfBkLXOx7uOnNms_zMW8Pacddk" // e.g. "123456789:ABCdefGHIjklMNOpqrsTUVwxyz"

    // TODO: Fill in your Telegram Group Chat ID (can be negative for groups)
    private static final String CHAT_ID = "-5052061755" // e.g. "-1001234567890"

    private static final String SERVICE_NAME = "app_cskh_prod"

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot${BOT_TOKEN}/sendMessage"

    /**
     * Send a formatted log message to the configured Telegram group.
     *
     * @param endpoint     The API endpoint being called
     * @param message      The log message / error detail
     */
    void sendMessage(String endpoint, String message) {
        try {
            String text = formatMessage(SERVICE_NAME, endpoint, message)

            def bodyMap = [
                "chat_id": CHAT_ID,
                "text": text,
                "parse_mode": "HTML"
            ]
            String body = groovy.json.JsonOutput.toJson(bodyMap)

            String url = "https://api.telegram.org/bot${BOT_TOKEN}/sendMessage"

            String response = HttpManaged.post(
                    ["url": url],
                    ["Content-Type": "application/json; charset=utf-8"],
                    body
            )

            def res = new JsonSlurper().parseText(response)

            if (res?.ok) {
                logger.info("Telegram message sent successfully for service: {}", SERVICE_NAME)
            } else {
                logger.error("Telegram API returned error: {}", response)
            }
        } catch (Exception ex) {
            logger.error("Failed to send Telegram message: {}", ex.getMessage(), ex)
            throw new RuntimeException("Failed to send Telegram message: " + ex.getMessage(), ex)
        }
    }

    /**
     * Format the message with HTML styling for Telegram.
     */
    private static String formatMessage(String serviceName, String endpoint, String message) {
        return """🔔 <b>API Log Notification</b>

<b>Service:</b> ${serviceName}
<b>Endpoint:</b> ${endpoint}
<b>Message:</b>
<pre>${message}</pre>

⏰ ${new Date().format("yyyy-MM-dd HH:mm:ss")}"""
    }
}
