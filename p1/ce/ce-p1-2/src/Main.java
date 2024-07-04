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
        String[] words = generateRandomWords(100, 20);

        double start = Instant.now().toEpochMilli() / 1000.0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < repetitions; i++) {
            for (String word : words) {
                sb.append(word);
            }
        }
        String result = sb.toString();
        double end = Instant.now().toEpochMilli() / 1000.0;

        timestamp(out, "block", start, end);
    }

    public static String[] generateRandomWords(int count, int length) {
        Random random = new Random(42);
        String[] words = new String[count];
        StringBuilder wordBuilder = new StringBuilder(length);

        char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (int j = 0; j < length; j++) {
            wordBuilder.append('a');
        }
        String baseWord = wordBuilder.toString();

        for (int i = 0; i < count; i++) {
            words[i] = baseWord;
            if (length > 0) {
                char[] wordArray = baseWord.toCharArray();
                wordArray[random.nextInt(length)] = ALPHABET[random.nextInt(ALPHABET.length)];
                words[i] = new String(wordArray);
            }
        }

        return words;
    }
}
