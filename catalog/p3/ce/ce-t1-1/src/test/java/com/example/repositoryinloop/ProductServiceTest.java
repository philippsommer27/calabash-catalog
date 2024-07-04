package com.example.repositoryinloop;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    public static void timestamp(String filePath, String eventId, long startTime, long endTime) throws JSONException {
        long durationSeconds = endTime - startTime;

        JSONObject event = new JSONObject();
        event.put("start", startTime);
        event.put("end", endTime);
        event.put("duration", durationSeconds);

        JSONObject eventEntry = new JSONObject();
        eventEntry.put(eventId, event);

        JSONArray events = new JSONArray();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                events = new JSONArray(content);
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        }

        events.put(eventEntry);

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(events.toString(4));
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    @Test
    public void findAll() throws JSONException {
        long start = Instant.now().toEpochMilli();

        for (int i = 0; i < 100000; i++) {
            List<Product> products = productRepository.findAll();
            System.out.println(products);
        }
        long end = Instant.now().toEpochMilli();

        timestamp("/home/timesheet.json", "ce-t1-1/loop run", start, end);

        assertThat(true).isTrue();
    }
}