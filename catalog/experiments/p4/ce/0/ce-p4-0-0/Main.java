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

        long seed = 12345L;

        Random rand = new Random(seed);
        long accumulator = 0;

        for (int i = 0; i < repetitions; i++) {
            int month = rand.nextInt(25);  // 0-24 to cover 24 cases plus default
            String monthString;

            if (month == 1 || month == 13) {
                monthString = "January";
            } else if (month == 2 || month == 14) {
                monthString = "February";
            } else if (month == 3 || month == 15) {
                monthString = "March";
            } else if (month == 4 || month == 16) {
                monthString = "April";
            } else if (month == 5 || month == 17) {
                monthString = "May";
            } else if (month == 6 || month == 18) {
                monthString = "June";
            } else if (month == 7 || month == 19) {
                monthString = "July";
            } else if (month == 8 || month == 20) {
                monthString = "August";
            } else if (month == 9 || month == 21) {
                monthString = "September";
            } else if (month == 10 || month == 22) {
                monthString = "October";
            } else if (month == 11 || month == 23) {
                monthString = "November";
            } else if (month == 12 || month == 24) {
                monthString = "December";
            } else {
                monthString = "Invalid month";
            }

            accumulator += monthString.length() * (i % 256);
        }

        System.out.println("Accumulator: " + accumulator);

        double end = Instant.now().toEpochMilli() / 1000.0 ;

        timestamp(out, "block", start, end);
    }
}
