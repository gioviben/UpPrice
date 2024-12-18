package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendMessageManager {
    final private String BOT_TOKEN;
    final private String CHAT_ID;
    private String query;

    public void setMessage(String message) {
        query = "chat_id=" + CHAT_ID + "&text=" + message;
    }

    public SendMessageManager() {
        Dotenv dotenv = Dotenv.load();
        BOT_TOKEN = dotenv.get("BOT_TOKEN");
        CHAT_ID = dotenv.get("CHAT_ID");
    }

    public void sendMessage() throws IOException {

        String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = query.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Messaggio inviato con successo!");
        } else {
            System.out.println("Errore nell'invio del messaggio. Codice di risposta: " + responseCode);
        }
    }
}
