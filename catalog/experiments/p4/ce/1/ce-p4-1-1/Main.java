import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.regex.Pattern;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {

    public static void main(String[] args) throws JSONException {
        String externalRepetitions = System.getenv("REPETITIONS");
        String timesheetPath = System.getenv("TS_PATH");
        experiment(Integer.parseInt(externalRepetitions), timesheetPath);
    }

    public static void timestamp(String filePath, String eventId, double startTime, double endTime) throws JSONException {
        double durationSeconds = endTime - startTime;
        System.out.println("Duration: " + durationSeconds);

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

    public static void experiment(int repetitions, String out) throws JSONException {
        double start = Instant.now().toEpochMilli() / 1000.0;
        StringBuilder result = new StringBuilder();
        String[] MONTHS = {
                "January2023", "February2023", "March2023", "April2023", "May2023", "June2023",
                "July2023", "August2023", "September2023", "October2023", "November2023", "December2023",
                "January2024", "February2024", "March2024", "April2024", "May2024", "June2024",
                "July2024", "August2024", "September2024", "October2024", "November2024", "December2024"
        };
        long accumulator = 0;
        long seed = 12345L;
        Random rand = new Random(seed);
        for (int i = 0; i < repetitions; i++) {
            String monthInput = MONTHS[rand.nextInt(MONTHS.length)];
            String monthString;

            switch (monthInput) {
                case "January2023": case "January2024":
                    monthString = "Jan";
                    break;
                case "February2023": case "February2024":
                    monthString = "Feb";
                    break;
                case "March2023": case "March2024":
                    monthString = "Mar";
                    break;
                case "April2023": case "April2024":
                    monthString = "Apr";
                    break;
                case "May2023": case "May2024":
                    monthString = "May";
                    break;
                case "June2023": case "June2024":
                    monthString = "Jun";
                    break;
                case "July2023": case "July2024":
                    monthString = "Jul";
                    break;
                case "August2023": case "August2024":
                    monthString = "Aug";
                    break;
                case "September2023": case "September2024":
                    monthString = "Sep";
                    break;
                case "October2023": case "October2024":
                    monthString = "Oct";
                    break;
                case "November2023": case "November2024":
                    monthString = "Nov";
                    break;
                case "December2023": case "December2024":
                    monthString = "Dec";
                    break;
                default:
                    monthString = "Invalid month";
                    break;
            }

            accumulator += monthString.length() * (i % 256);
        }
        System.out.println(result.length());

        double end = Instant.now().toEpochMilli() / 1000.0 ;

        timestamp(out, "block", start, end);
    }
}
