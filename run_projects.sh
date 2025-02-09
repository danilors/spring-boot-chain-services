#!/bin/bash

# Define the project directories
PROJECT_DIRS=("./profile" "./address" "./occupation")

# Loop through each project directory
for PROJECT_DIR in "${PROJECT_DIRS[@]}"; do
  echo "Starting project in: $PROJECT_DIR"

  # Change to the project directory
  cd "$PROJECT_DIR" || { echo "Error: Could not change directory to $PROJECT_DIR"; exit 1; }

  # Run the Maven Spring command in the background
  mvn spring-boot:run &

  # Store the process ID (PID) of the background process
  PIDS+=($!)

  # Change back to the parent directory (optional, but good practice)
  cd .. || { echo "Error: Could not change back to parent directory"; exit 1; }

  echo "Project started in background (PID: ${PIDS[-1]})"

done

echo "All projects started."

# Optional: Wait for all background processes to finish (uncomment if needed)
#for PID in "${PIDS[@]}"; do
#  wait "$PID"
#  echo "Project with PID $PID finished."
#done

# Optional:  If you want to stop all the processes at once later, you can store the PIDs in a file.
# echo "${PIDS[@]}" > pids.txt

# Optional: Function to kill all the processes (useful if you don't use 'wait')
#kill_all_processes() {
#    for PID in "${PIDS[@]}"; do
#        kill "$PID" 2>/dev/null  # Suppress "no such process" errors
#    done
#    rm pids.txt #Remove the pids file
#}

# Trap Ctrl+C (interrupt signal) to gracefully kill the background processes
#trap "kill_all_processes; exit" INT
