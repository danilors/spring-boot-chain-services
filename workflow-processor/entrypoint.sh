#!/bin/sh

# Start the log agent in the background
java -jar /logAgent.jar &

# Execute the main application in the foreground
exec /app