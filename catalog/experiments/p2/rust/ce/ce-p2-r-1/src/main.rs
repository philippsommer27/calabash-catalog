use std::env;
use std::fs::{File, OpenOptions};
use std::io::{Read, Write};
use std::time::{SystemTime, UNIX_EPOCH};
use regex::Regex;
use serde_json::{json};

fn main() {
    let repetitions: i64 = env::var("REPETITIONS")
        .expect("REPETITIONS environment variable not set")
        .parse()
        .expect("REPETITIONS must be an integer");
    let timesheet_path = env::var("TS_PATH").expect("TS_PATH environment variable not set");
    experiment(repetitions, &timesheet_path);
}

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

fn experiment(repetitions: i64, out: &str) {
    let start = current_time_in_seconds();
    let regex = Regex::new(r"(\d{3})-(\d{2})-(\d{4})|([a-z]+)\s([A-Z][a-z]+)").unwrap();
    let mut dummy = 0;
    for _ in 0..repetitions {
        let is_match = regex.is_match("123-45-6789 or john Doe");
        dummy += if is_match { 1 } else { 0 };
    }
    println!("{}", dummy);
    let end = current_time_in_seconds();

    timestamp(out, "block", start, end);
}

fn current_time_in_seconds() -> f64 {
    let start = SystemTime::now();
    let since_the_epoch = start
        .duration_since(UNIX_EPOCH)
        .expect("Time went backwards");
    since_the_epoch.as_secs() as f64 + since_the_epoch.subsec_millis() as f64 / 1000.0
}
