# Use the official Rust image as the base image
FROM rust:latest

# Set the working directory
WORKDIR /app

# Copy the Cargo.toml file
COPY Cargo.toml ./

# Create a dummy source file to build dependencies
RUN mkdir src && echo "fn main() {}" > src/main.rs

# Build the dependencies
RUN cargo build --release

# Remove the dummy source file
RUN rm -f src/main.rs

# Copy the actual source files
COPY src ./src

# Build the actual application
RUN cargo build --release

# Set the command to run the compiled binary
CMD ["./target/release/experiment"]
