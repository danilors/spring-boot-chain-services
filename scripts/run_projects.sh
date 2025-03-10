#!/bin/bash

# Define the project directories
PROJECT_DIRS=("./profile" "./address" "./occupation" "./rules")

# Array to store PIDs with project names
PID_INFO=()

# Loop through each project directory
for PROJECT_DIR in "${PROJECT_DIRS[@]}"; do
  echo "Starting project in: $PROJECT_DIR"

  # Change to the project directory
  cd "$PROJECT_DIR" || {
    echo "Error: Could not change directory to $PROJECT_DIR"
    exit 1
  }

  # Run the Maven Spring command in the background
  mvn spring-boot:run &

  # Store the process ID (PID) and project name
  PID_INFO+=("${PROJECT_DIR}:${!}") # Store project name and PID

  # Change back to the parent directory
  cd .. || {
    echo "Error: Could not change back to parent directory"
    exit 1
  }

  # Extract PID from the last element of PID_INFO
  PID="${PID_INFO[-1]##*:}"

  echo "Project $PROJECT_DIR started in background (PID: $PID)"

done

echo "All projects started."

# Optionally print all PID information at the end:
echo ""
echo "Project PID Information:"
for info in "${PID_INFO[@]}"; do
  project_name="${info%%:*}"
  pid="${info##*:}"
  echo "Project: $project_name, PID: $pid"
done
