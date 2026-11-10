import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    // In dev the page is on :3000 but the API is on :8080.
    // This forwards /api calls to the backend so they look same-origin.
    proxy: {
      '/api': 'http://localhost:8080',
    },
  },
})
