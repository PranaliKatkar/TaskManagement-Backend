package com.Verzat.VerzatTechno.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    public void sendHtmlEmail(String to, String subject, String htmlBody) {

        try {
            String payload = """
            {
              "sender": {
                "email": "%s",
                "name": "%s"
              },
              "to": [
                {
                  "email": "%s"
                }
              ],
              "subject": "%s",
              "htmlContent": "%s"
            }
            """.formatted(
                senderEmail,
                senderName,
                to,
                subject,
                htmlBody.replace("\"", "\\\"")
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", apiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed", e);
        }
    }
}
