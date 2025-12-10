<template>
  <div class="search-bar-container">
    <div class="search-input-wrapper">
      <span class="search-icon">🔍</span> 
      <input
        type="text"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
        placeholder="Total Expenses | Search transaction details by description..."
        class="search-input"
      />
      <button v-if="modelValue" @click="$emit('update:modelValue', '')" class="clear-button">
        &times;
      </button>
    </div>
    
    <div class="filter-placeholder">
      <button class="filter-btn">Filter</button>
    </div>
  </div>
</template>

<script>
/**
 * SearchBar.vue
 * Component to implement the text search/filter requirement.
 * Uses v-model for two-way binding with the parent component (ExpenseTable).
 */
export default {
  props: {
    modelValue: {
      type: String,
      default: ''
    }
  },
  emits: ['update:modelValue']
}
</script>

<style scoped>
/* Standardized Spacing (REM units, 8px grid) */
.search-bar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 1.5rem 0 1rem 0; /* Clearer top/bottom spacing */
  padding: 0.75rem 1rem; /* Increased vertical padding (12px and 16px) */
  background-color: var(--color-bg-white);
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05); /* Lighter shadow */
}

.search-input-wrapper {
  display: flex;
  align-items: center;
  flex-grow: 1;
}

.search-icon {
  padding-right: 0.5rem; 
  color: #6c757d; 
}

.search-input {
  flex-grow: 1;
  border: none;
  padding: 0.5rem 0;
  font-size: 1rem; 
  outline: none;
}

.clear-button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.25rem;
  color: #6c757d;
  padding: 0 0.5rem;
  line-height: 1;
}

.filter-placeholder {
  padding-left: 1rem; 
}

.filter-btn {
  /* Inherits button styling from App.vue or global base styles */
  background: var(--color-primary-accent);
  padding: 0.4rem 0.8rem; /* Slightly smaller button for filtering */
  font-size: 0.8rem;
}

/* Responsive adjustments */
@media (max-width: 600px) {
  .search-bar-container {
    flex-wrap: wrap;
  }
  .filter-placeholder {
    width: 100%;
    margin-top: 0.5rem;
    text-align: right;
  }
}
</style>