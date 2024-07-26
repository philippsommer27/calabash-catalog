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

            if (monthInput.equals("January2023") || monthInput.equals("January2024")) {
                monthString = "Jan";
            } else if (monthInput.equals("February2023") || monthInput.equals("February2024")) {
                monthString = "Feb";
            } else if (monthInput.equals("March2023") || monthInput.equals("March2024")) {
                monthString = "Mar";
            } else if (monthInput.equals("April2023") || monthInput.equals("April2024")) {
                monthString = "Apr";
            } else if (monthInput.equals("May2023") || monthInput.equals("May2024")) {
                monthString = "May";
            } else if (monthInput.equals("June2023") || monthInput.equals("June2024")) {
                monthString = "Jun";
            } else if (monthInput.equals("July2023") || monthInput.equals("July2024")) {
                monthString = "Jul";
            } else if (monthInput.equals("August2023") || monthInput.equals("August2024")) {
                monthString = "Aug";
            } else if (monthInput.equals("September2023") || monthInput.equals("September2024")) {
                monthString = "Sep";
            } else if (monthInput.equals("October2023") || monthInput.equals("October2024")) {
                monthString = "Oct";
            } else if (monthInput.equals("November2023") || monthInput.equals("November2024")) {
                monthString = "Nov";
            } else if (monthInput.equals("December2023") || monthInput.equals("December2024")) {
                monthString = "Dec";
            } else {
                monthString = "Invalid month";
            }

            accumulator += monthString.length() * (i % 256);
        }
        System.out.println(result.length());

        double end = Instant.now().toEpochMilli() / 1000.0 ;

        timestamp(out, "block", start, end);
    }
}
