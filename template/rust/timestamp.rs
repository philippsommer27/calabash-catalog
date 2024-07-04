use std::fs::{File, OpenOptions};

fn timestamp(file_path: &str, event_id: &str, start_time: f64, end_time: f64) {
    let duration_seconds = end_time - start_time;

    let event = json!({
        "name": event_id,
        "start": start_time,
        "end": end_time,
        "duration": duration_seconds,
    });

    let mut events = Vec::new();
    if let Ok(mut file) = File::open(file_path) {
        let mut content = String::new();
        if file.read_to_string(&mut content).is_ok() {
            events = serde_json::from_str(&content).unwrap_or(Vec::new());
        }
    }

    events.push(event);

    let events_json = serde_json::to_string_pretty(&events).expect("Failed to serialize events");

    let mut file = OpenOptions::new()
        .write(true)
        .create(true)
        .truncate(true)
        .open(file_path)
        .expect("Failed to open file");
    file.write_all(events_json.as_bytes())
        .expect("Failed to write to file");
}