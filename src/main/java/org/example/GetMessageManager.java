package org.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

public class GetMessageManager {

    private final String BOT_TOKEN;;
    LocalDateTime lastDate;

    public GetMessageManager() {
        Dotenv dotenv = Dotenv.load();
        BOT_TOKEN = dotenv.get("BOT_TOKEN");
        lastDate = LocalDateTime.now();
    }

    public String getLatestMessage() throws IOException {

        URL url;
        BufferedReader reader;
        StringBuilder Message;
        String line;
        String text;
        LocalDateTime currentDate;

        url = new URL("https://api.telegram.org/bot" + BOT_TOKEN + "/getUpdates");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        connection.setRequestMethod("GET");

        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        Message = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            Message.append(line);
        }

        reader.close();
        connection.disconnect();

        currentDate = parseDate(Message);
        text = parseText(Message);

        if (currentDate.isEqual(lastDate) || lastDate.isAfter(currentDate)) {
            return "No recent message";
        } else {
            lastDate = LocalDateTime.of(currentDate.toLocalDate(), currentDate.toLocalTime());
            return text;
        }
    }

    private LocalDateTime parseDate(StringBuilder Message) {
        int start_index = Message.lastIndexOf("\"date\":");
        int end_index = Message.lastIndexOf(",\"text");

        long timestamp = Long.parseLong(Message.substring(start_index + 7, end_index));

        Date date = new Date(timestamp * 1000);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        StringBuilder readableDate = new StringBuilder(sdf.format(date));

        return LocalDateTime.of(Integer.parseInt(readableDate.substring(6, 10)), Integer.parseInt(readableDate.substring(3, 5)), Integer.parseInt(readableDate.substring(0, 2)), Integer.parseInt(readableDate.substring(11, 13)), Integer.parseInt(readableDate.substring(14, 16)), Integer.parseInt(readableDate.substring(17, 19)));
    }

    private String parseText(StringBuilder Message) {
        int start_index = Message.lastIndexOf("\"text\":");
        int end_index = Message.lastIndexOf("\"}}]}");

        return Message.substring(start_index + 8, end_index);
    }
}
