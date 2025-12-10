// Base URL points to the proxy configured in vite.config.js for development,
// which directs requests to your Spring Boot backend (port 8080).
const API_BASE_URL = '/api/expenses';

/**
 * @function handleResponse
 * Handles the promise resolution and error checking for fetch responses.
 * @param {Response} response - The fetch response object.
 * @returns {Promise<any>} - The JSON body of the response.
 */
async function handleResponse(response) {
    if (!response.ok) {
      const errorBody = await response.json().catch(() => ({ message: 'Unknown server error.' }));
      
      if (response.status === 400 && errorBody.errors) {
          // FIX: Extract and join Spring Boot validation error messages
          const validationErrors = errorBody.errors.map(err => `${err.field}: ${err.defaultMessage}`).join('\n');
          throw new Error(`Validation Failed:\n${validationErrors}`);
      }

      throw new Error(errorBody.message || `HTTP error! Status: ${response.status}`);
    }
    // Handle 204 No Content for DELETE
    if (response.status === 204) return null; 
    return response.json();
}

/**
 * @namespace api
 * Centralized service for all expense-related API calls.
 */
export const api = {

    /**
     * Fetches all expenses from the backend.
     * @returns {Promise<Array>} List of expense objects.
     */
    async fetchAllExpenses() {
        const response = await fetch(API_BASE_URL);
        return handleResponse(response);
    },

    /**
     * Fetches summary statistics.
     * @returns {Promise<Object>} Summary map.
     */
    async fetchSummary() {
        const response = await fetch(`${API_BASE_URL}/summary`);
        return handleResponse(response);
    },

    /**
     * Creates a new expense record.
     * @param {Object} expenseData - The expense object to send.
     * @returns {Promise<Object>} The created expense object.
     */
    async createExpense(expenseData) {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(expenseData),
        });
        return handleResponse(response);
    },

    /**
     * Soft deletes an expense.
     * @param {number} id - The ID of the expense to delete.
     * @returns {Promise<void>}
     */
    async deleteExpense(id) {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE',
        });
        return handleResponse(response);
    }
};