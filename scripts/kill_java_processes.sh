#!/bin/bash

# Function to kill all Java processes
kill_java_processes() {
  # Find all Java processes and kill them
  pkill -f "java" # -f matches against the full command line
  # OR, for more control (e.g., excluding certain processes):
  # ps aux | grep java | grep -v "your_exclusion_pattern" | awk '{print $2}' | xargs kill

  if [[ $? -eq 0 ]]; then # Check if the kill command was successful
    echo "All Java processes terminated."
  else
    echo "Some Java processes may not have been terminated (or none were running)."
  fi
}

# Function to stop the specific Spring Boot applications (optional)
stop_spring_apps() {
  PROJECT_DIRS=("../profile" "../address" "../occupation" "../rules")
  for PROJECT_DIR in "${PROJECT_DIRS[@]}"; do
    echo "Stopping project in: $PROJECT_DIR"
    cd "$PROJECT_DIR" || {
      echo "Error: Could not change directory to $PROJECT_DIR"
      continue
    }                    # Use continue to avoid exiting the loop
    mvn spring-boot:stop # Attempt graceful shutdown first
    cd .. || {
      echo "Error: Could not change back to parent directory"
      continue
    }
  done
}

# Main script execution:

echo "Stopping Spring Boot applications (graceful shutdown)..."
stop_spring_apps

echo "Killing all Java processes..."
kill_java_processes

echo "Done."

