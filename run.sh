#!/bin/bash
# Starts the backend and the frontend together.
# Press Ctrl+C to stop both.

# Always work from the project root, no matter where this is run from.
cd "$(dirname "$0")"

chmod +x backend.sh frontend/frontend.sh

# When this script stops, stop both servers too.
trap 'kill 0' EXIT

# Both need to run at the same time, so send them to the background.
./backend.sh &
(cd frontend && ./frontend.sh) &

# Keep this script alive while the servers run.
wait
