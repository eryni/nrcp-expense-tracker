<template>
  <div id="app">
    <header class="app-header">Expense Tracker</header>
    
    <SummaryStats v-if="summary.Total !== undefined" :summary="summary" />
    
    <ExpenseForm @add-expense="handleAddExpense" />
    
    <ExpenseTable :expenses="expenses" :loading="loading" @delete-expense="handleDeleteExpense" />

    <div v-if="error" class="global-error">
        {{ error }}
        <button @click="loadData">Retry Load</button>
    </div>
  </div>
</template>

<script>
import ExpenseForm from './components/ExpenseForm.vue'
import ExpenseTable from './components/ExpenseTable.vue'
import SummaryStats from './components/SummaryStats.vue'
import { api } from './utils/api' // <-- Using the correct 'utils' path

/**
 * App.vue
 * Root component that manages the state (expenses, summary) and persistence (API).
 */
export default {
  components: { ExpenseForm, ExpenseTable, SummaryStats },
  data() {
    return {
      expenses: [],
      // Initialize Total to undefined to safely use v-if
      summary: {}, 
      loading: false,
      error: null
    };
  },
  methods: {
    // Helper to calculate total spent based on quantity * pricePer (required for client-side calculation before submission)
    calculateTotal(expense) {
      return expense.quantity * expense.pricePer;
    },
    
    // --- API Data Fetching ---
    async loadData() {
      this.loading = true;
      this.error = null;
      try {
        const [expensesResponse, summaryResponse] = await Promise.all([
          api.fetchAllExpenses(),
          api.fetchSummary()
        ]);

        this.expenses = expensesResponse || []; 
        this.summary = summaryResponse || {}; 

      } catch (err) {
        console.error('API Load Error:', err);
        this.error = 'Failed to load data from backend. Check API connection.';
      } finally {
        this.loading = false;
      }
    },
    
    // --- Event Handlers (Now calling API) ---

    async handleAddExpense(expense) {
      // 1. AUGMENT DATA: Add required backend fields before submission
      const expenseToSend = {
        ...expense,
        loggedBy: 'temp_user@nrcp.com', // CRITICAL: Required by @Email validation in Expense model
        totalSpent: this.calculateTotal(expense), // Required by @NotNull validation
        currencySpent: 'PHP', // Required by @NotBlank validation
      };
      
      const initialLoading = this.loading;
      if (!initialLoading) this.loading = true; 
      
      try {
        await api.createExpense(expenseToSend);
        
        // Refresh all data after successful creation
        await this.loadData();
      } catch (err) {
        // This alert will show the detailed validation message from the server (after api.js fix)
        alert(`Error creating expense:\n${err.message}`); 
      } finally {
        if (!initialLoading) this.loading = false;
      }
    },
    
    async handleDeleteExpense(id) {
      if (!confirm('Are you sure you want to delete this expense?')) return;
      
      try {
        await api.deleteExpense(id);
        await this.loadData();
      } catch (err) {
        alert(`Error deleting expense: ${err.message}`);
      }
    }
  },
  mounted() {
    this.loadData();
  }
};
</script>

<style scoped>
/* (Styles remain the same) */
:root {
  --color-success-light: #DBF7EA;
  --color-success-dark: #239B6A;
  --color-primary-accent: #6159F4; 
  --color-danger-light: #FF3170;
  --color-text-dark: #201E3D;
  --color-bg-white: #F7F6FD; 
  --color-bg-whiter: #F9FAFF; 
  --spacing-md: 1.5rem;
}

#app {
  font-family: 'Inter', sans-serif;
  background-color: var(--color-bg-whiter);
  color: var(--color-text-dark);
  max-width: 1200px;
  margin: 0 auto;
  padding: var(--spacing-md);
  min-height: 100vh;
}

.app-header {
  font-size: 1.5rem;
  font-weight: 700;
  margin-bottom: var(--spacing-md);
  color: var(--color-text-dark);
}

.global-error {
    margin-top: 1rem;
    padding: 0.75rem;
    background: #ffcccc;
    color: #cc0000;
    border: 1px solid #cc0000;
    border-radius: 4px;
}
</style>