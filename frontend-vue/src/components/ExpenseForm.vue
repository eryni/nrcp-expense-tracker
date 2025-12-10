<template>
  <div class="form-wrapper">
    <form @submit.prevent="addExpense" class="expense-form">
      <select v-model="newExpense.category" required class="form-control">
        <option disabled value="">Select Category</option>
        <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
      </select>

      <select v-model="newExpense.purposeItem" required class="form-control">
        <option disabled value="">Select Purpose/Item</option>
        <option v-for="p in purposes" :key="p" :value="p">{{ p }}</option>
      </select>

      <input type="number" v-model.number="newExpense.quantity" placeholder="Quantity (Units)" min="1" required class="form-control" />
      
      <input type="number" v-model.number="newExpense.pricePer" placeholder="Price/Unit (PHP)" step="0.01" min="0.01" required class="form-control" />

      <input type="date" v-model="newExpense.datePurchased" required class="form-control" />

      <button type="submit" class="add-button">
        Add Expense
      </button>
    </form>
  </div>
</template>

<script>
/**
 * ExpenseForm.vue
 * Handles input and submission of new expense records.
 */
export default {
  data() {
    return {
      newExpense: {
        category: '',
        purposeItem: '',
        quantity: 1,
        pricePer: 0.01,
        datePurchased: this.getTodayDate()
      },
      categories: ['Food', 'Health Care', 'Insurance', 'Shopping', 'Transport', 'Others'],
      purposes: ['Daily Meal', 'Vitamins', 'Car Insurance', 'Office Supplies', 'Train Ticket', 'Misc']
    };
  },
  methods: {
    getTodayDate() {
      return new Date().toISOString().split('T')[0];
    },
    addExpense() {
      this.$emit('add-expense', { ...this.newExpense });
      this.newExpense = { 
        category: '', 
        purposeItem: '', 
        quantity: 1, 
        pricePer: 0.01, 
        datePurchased: this.getTodayDate() 
      };
    }
  }
};
</script>

<style scoped>
/* Standardized Spacing (REM units, 8px grid) */
.form-wrapper {
  background-color: var(--color-bg-white);
  padding: 1.5rem; /* Increased padding for clarity (24px) */
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05); /* Clearer separation */
  margin-bottom: 2rem; /* Increased spacing before the table */
}

.expense-form {
  display: flex;
  gap: 1rem; /* Increased gap between form elements (16px) */
  flex-wrap: wrap;
  justify-content: space-between;
}

.form-control {
  padding: 0.75rem; /* 12px vertical padding */
  border-radius: 6px;
  border: 1px solid #e0e0e0;
  min-width: 150px;
  flex-grow: 1;
  font-size: 1rem;
}

.add-button {
  flex-grow: 1;
  max-width: 200px;
  font-weight: 700;
  text-transform: uppercase;
  padding: 0.75rem 1.5rem;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .expense-form {
    flex-direction: column;
    align-items: stretch;
    gap: 0.75rem;
  }
  .form-control, .add-button {
    min-width: 100%;
    max-width: 100%;
  }
}
</style>