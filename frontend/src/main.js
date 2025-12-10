import { component, mount } from 'riot'
// Imports the main application component
import App from './App.riot' 

import { expenseStore } from './stores/expenseStore'
import { dateUtils } from './utils/dateUtils'

// Define the root DOM element where the application will be injected
const ROOT_ELEMENT = document.getElementById('app')

if (ROOT_ELEMENT) {
    /**
     * @function setupAndMountApp
     * Initializes global objects and mounts the main application component.
     */
    const setupAndMountApp = () => {
        // Prepare global objects to be available to all components through Riot's context.
        // This is a pattern for dependency injection in Riot.js.
        const appGlobals = {
            // Expose the expense data store and its actions
            expenseStore, 
            // Expose utility functions for date formatting, etc.
            dateUtils,     
            // Expose the API base URL to easily construct fetch requests
            API_BASE_URL: '/api/expenses' 
        }

        // Mount the main App component to the root element.
        // The `appGlobals` object is passed as the third argument, making it 
        // accessible as `this.root.globals` within any mounted component.
        mount(ROOT_ELEMENT, appGlobals, App)
        console.log('Riot.js Application Mounted Successfully.')
    }

    // Start the application setup
    setupAndMountApp()

} else {
    // Failsafe if the root element is not found
    console.error('Error: Root element with id="app" not found in the DOM.')
}