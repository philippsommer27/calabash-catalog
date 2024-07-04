import os
import json
import re
import time
from datetime import datetime

def main():
    external_repetitions = os.getenv("REPETITIONS")
    timesheet_path = os.getenv("TS_PATH")
    experiment(int(external_repetitions), timesheet_path)

def timestamp(file_path, event_id, start_time, end_time):
    duration_seconds = end_time - start_time

    event = {
        "name": event_id,
        "start": start_time,
        "end": end_time,
        "duration": duration_seconds
    }

    events = []
    if os.path.exists(file_path):
        try:
            with open(file_path, 'r') as file:
                content = file.read()
                events = json.loads(content)
        except Exception as e:
            print(f"Error reading the file: {e}")

    events.append(event)

    try:
        with open(file_path, 'w') as file:
            json.dump(events, file, indent=4)
    except Exception as e:
        print(f"Error writing to the file: {e}")

def experiment(repetitions, out):
    start = time.time()
    regex = r"(\d{3})-(\d{2})-(\d{4})|([a-z]+)\s([A-Z][a-z]+)"
    pattern = re.compile(regex)
    dummy = 0
    for i in range(repetitions):
        is_match = bool(pattern.match("123-45-6789 or john Doe"))
        dummy += 1 if is_match else 0
    end = time.time()

    timestamp(out, "block", start, end)

if __name__ == "__main__":
    main()
