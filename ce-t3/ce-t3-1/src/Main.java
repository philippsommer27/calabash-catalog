import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws JSONException {
        experiment();
    }

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

    public static void experiment() throws JSONException {
        long start = Instant.now().toEpochMilli();
        String regex = "(\\d{3})-(\\d{2})-(\\d{4})|([a-z]+)\\s([A-Z][a-z]+)";
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < 1000000; i++) {
            boolean isMatch = pattern.matcher("123-45-6789 or john Doe").matches();
            System.out.println(isMatch);
        }
        long end = Instant.now().toEpochMilli();

        timestamp("/home/ce-t3-1_t.json", "ce-t3-1/block", start, end);
    }
}
