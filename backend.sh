#!/bin/bash
# Starts the Spring Boot backend on port 8080.

cd "$(dirname "$0")"

# Free the port if an old server is still running.
pid=$(lsof -ti tcp:8080)
if [ -n "$pid" ]; then
    echo "Port 8080 is busy, stopping the old server (pid $pid)"
    kill "$pid"
    sleep 2
fi

./mvnw spring-boot:run
