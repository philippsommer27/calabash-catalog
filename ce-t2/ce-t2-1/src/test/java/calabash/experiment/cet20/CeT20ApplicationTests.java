package calabash.experiment.cet20;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CeT20ApplicationTests {

	@Test
	void contextLoads() {
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

	@Test
	public void testPatternMatching() throws JSONException {
		long start = Instant.now().toEpochMilli();
		String regex = "(\\d{3})-(\\d{2})-(\\d{4})|([a-z]+)\\s([A-Z][a-z]+)";
		Pattern pattern = Pattern.compile(regex);
		for (int i = 0; i < 1000000; i++) {
			boolean isMatch = pattern.matcher("123-45-6789 or john Doe").matches();
			System.out.println(isMatch);
		}
		long end = Instant.now().toEpochMilli();
		assertThat(true).isTrue();

		timestamp("/home/timesheet.json", "ce-t2-1/block", start, end);
	}

}
