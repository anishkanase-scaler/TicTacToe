#!/bin/bash
# Starts the React dev server on port 3000.

cd "$(dirname "$0")"

if command -v node >/dev/null 2>&1; then
    echo "Node is installed"
else
    echo "Node is NOT installed"
    exit 1
fi

if [ -d node_modules ]; then
    echo "node_modules are installed"
else
    echo "node_modules are NOT installed"
    npm install
fi

# Port comes from vite.config.js.
pid=$(lsof -ti tcp:3000)
if [ -n "$pid" ]; then
    echo "Port 3000 is busy, stopping the old server (pid $pid)"
    kill "$pid"
    sleep 1
fi

npm run dev
