import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public static void timestamp(String filePath, String eventId, double startTime, double endTime) throws JSONException {
    double durationSeconds = endTime - startTime;

    JSONObject event = new JSONObject();
    event.put("name", eventId);
    event.put("start", startTime);
    event.put("end", endTime);
    event.put("duration", durationSeconds);

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

    events.put(event);

    try (FileWriter fileWriter = new FileWriter(filePath)) {
        fileWriter.write(events.toString(4));
    } catch (IOException e) {
        System.err.println("Error writing to the file: " + e.getMessage());
    }
}