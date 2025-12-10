import { defineConfig } from 'vite'
import riot from 'rollup-plugin-riot'

/**
 * Vite Configuration for the Riot.js Expense Tracker Frontend
 * * Configures the development server, build settings, and integrates the Riot.js plugin.
 * Crucially, it sets up a proxy to forward API calls to the Spring Boot backend
 * running on port 8080 during development.
 */
export default defineConfig({
  // Project root directory, where index.html is located
  root: 'client', 
  
  // Plugins for handling Riot.js files
  plugins: [
    riot() // Integrates Riot.js compiler
  ],

  // Development Server Configuration
  server: {
    // Standard port for Vite apps
    port: 5173, 
    
    // Proxy API requests to the Spring Boot backend to avoid CORS issues in dev.
    // The backend's @CrossOrigin(origins = "http://localhost:5173") in ExpenseController 
    // will handle it for production/direct calls, but the proxy is best for dev.
    proxy: {
      // Proxy requests starting with '/api' (matching your controller's @RequestMapping)
      '/api': {
        target: 'http://localhost:8080', // Spring Boot default port
        changeOrigin: true, // Needed for virtual hosting sites
        secure: false, // For local development, assuming no SSL on backend
        // rewrite: (path) => path.replace(/^\/api/, '') // Not needed, keep '/api'
      }
    }
  },

  // Build Configuration
  build: {
    // Minify the output code using esbuild for fast performance
    minify: 'esbuild', 
    // Ensure compatibility with modern browsers
    target: 'esnext', 
    // Output directory for the build (relative to the project root, not 'client')
    outDir: '../dist' 
  }
})