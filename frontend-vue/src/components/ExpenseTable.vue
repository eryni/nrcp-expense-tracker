<template>
  <div class="table-wrapper">
    <div class="table-header-controls">
      <SearchBar v-model="filterText" />
    </div>

    <h3 class="details-title">Transaction Details</h3>
    <div class="scroll-container">
      <table>
        <thead>
          <tr>
            <th>Date Logged</th>
            <th>Item/Purpose</th>
            <th>Quantity</th>
            <th>Amount (PHP)</th>
            <th>Date Spent</th>
            <th>Currency</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(expense, index) in filteredExpenses" :key="expense.expenseId" :class="{ 'row-highlight': index % 2 !== 0 }">
            <td>{{ formatDate(expense.dateLogged) }}</td>
            <td>
              <span class="category-tag">{{ expense.category }}</span>
              {{ expense.purposeItem }}
            </td>
            <td>{{ expense.quantity }}</td>
            <td>{{ formatCurrency(expense.totalSpent) }}</td>
            <td>{{ formatDate(expense.datePurchased) }}</td>
            <td>{{ expense.currencySpent || 'PHP' }}</td>
            <td class="action-cells">
              <span class="action-icon edit-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" width="16" height="16"><path d="M17 3a2.828 2.828 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"></path></svg>
              </span>
              <span class="action-icon delete-icon" @click="$emit('delete-expense', expense.expenseId)">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" width="16" height="16"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import SearchBar from './SearchBar.vue'

/**
 * ExpenseTable.vue
 * Displays the filtered list of expenses and includes search functionality.
 */
export default {
  components: { SearchBar },
  props: ['expenses'],
  data() {
    return {
      filterText: '' // Bound via v-model from SearchBar
    };
  },
  computed: {
    filteredExpenses() {
      const filter = this.filterText.toLowerCase();
      return this.expenses.filter(exp =>
        exp.purposeItem.toLowerCase().includes(filter) ||
        exp.category.toLowerCase().includes(filter)
      );
    }
  },
  methods: {
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
    },
    formatCurrency(value) {
      return new Intl.NumberFormat('en-PH', {
        style: 'currency',
        currency: 'PHP',
        minimumFractionDigits: 2
      }).format(value);
    }
  }
};
</script>

<style scoped>
/* Standardized Spacing (REM units, 8px grid) */
.table-wrapper {
  background-color: var(--color-bg-white);
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  padding: 1.5rem; /* Increased padding (24px) */
}

.table-header-controls {
  display: flex;
  flex-direction: column;
  padding-bottom: 0.5rem; /* 8px padding */
}

.date-range-label {
  font-size: 0.85rem;
  color: #6c757d;
  margin: 0;
}

.details-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 1rem 0 0.5rem 0; /* Clearer separation from controls */
  padding-top: 0.5rem;
  border-top: 1px solid #e0e0e0;
}

.scroll-container {
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  min-width: 700px;
}

thead th {
  background: var(--color-bg-whiter); 
  color: var(--color-text-dark);
  padding: 0.75rem 1rem; /* 12px vertical, 16px horizontal */
  text-align: left;
  font-weight: 700;
  font-size: 0.9rem;
  border-bottom: 2px solid #e0e0e0; /* Stronger header divider */
}

/* Alternating Row Colors */
.row-highlight {
  background-color: var(--color-success-light); /* Light Green/DBF7EA for every other row */
}

tbody tr:hover {
  background-color: #f0f0f5; 
}

td {
  padding: 1rem; /* Generous vertical padding (16px) */
  border-bottom: 1px solid #e0e0e0;
  font-size: 0.9rem;
}

.action-icon {
  cursor: pointer;
  margin-right: 0.5rem;
  padding: 0.25rem;
}

.delete-icon {
  color: var(--color-danger-light); 
}

.category-tag {
  display: inline-block;
  background-color: var(--color-success-light); 
  color: var(--color-success-dark);
  font-size: 0.7rem;
  font-weight: 600;
  padding: 2px 6px;
  border-radius: 3px;
  margin-right: 0.5rem;
  min-width: 50px;
  text-align: center;
}
</style>