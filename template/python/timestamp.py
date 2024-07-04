import os
import json

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
