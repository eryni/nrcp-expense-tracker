<template>
  <div class="summary-card">
    <div class="total-spent-header">
      <h2 class="title">Total Spent</h2>
      <p class="amount">$ {{ formatCurrency(summary.Total) }}</p>
    </div>

    <div class="summary-breakdown">
      <div v-for="(value, key) in periodSummaries" :key="key" class="period-stat">
        <p class="period-label">{{ key }}</p>
        <p :class="['period-value', getTrendClass(key)]">
          {{ formatCurrency(value) }}
          <span v-if="key === 'Day'" class="trend-text">decreased ↓</span>
          <span v-else-if="key === 'Week'" class="trend-text increased">increased ↑</span>
          <span v-else class="trend-text stable">stable</span>
        </p>
      </div>
    
    </div>
  </div>
</template>

<script>
/**
 * SummaryStats.vue
 * Displays key summary statistics in a prominent card layout.
 */
export default {
  props: {
    summary: {
      type: Object,
      required: true,
      default: () => ({ Day: 0, Week: 0, Month: 0, Year: 0, Total: 0 })
    }
  },
  computed: {
    periodSummaries() {
      const { Total, ...periods } = this.summary;
      return periods;
    }
  },
  methods: {
    formatCurrency(value) {
      return new Intl.NumberFormat('en-PH', {
        style: 'currency',
        currency: 'PHP',
        minimumFractionDigits: 2
      }).format(value);
    },
    getTrendClass(key) {
      if (key === 'Week') return 'trend-increased';
      if (key === 'Day') return 'trend-decreased';
      return 'trend-stable';
    }
  }
};
</script>

<style scoped>
/* Standardized Spacing (REM units, 8px grid) */
.summary-card {
  background-color: #2e2c49; 
  color: white;
  border-radius: 10px;
  padding: 1.5rem; /* Increased padding (24px) */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  margin-bottom: 2rem; /* Clearer spacing below the card */
}

.total-spent-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 0.75rem; /* 12px padding */
  margin-bottom: 1rem; /* 16px margin */
}

.title {
  margin: 0;
  font-size: 1.25rem;
  font-weight: 500;
}

.amount {
  margin: 0;
  font-size: 2.5rem; /* Larger for impact */
  font-weight: bold;
}

.summary-breakdown {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding-top: 0.5rem;
}

.period-stat {
  text-align: center;
  flex: 1;
  padding: 0 0.5rem; /* 8px horizontal padding for spacing */
}

.period-label {
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 0.5rem;
}

.period-value {
  font-size: 1.25rem;
  font-weight: bold;
  margin: 0;
}

/* Trend Styling */
.trend-decreased {
  color: var(--color-danger-light); 
}

.trend-increased {
  color: var(--color-success-dark); 
}

.trend-stable {
  color: var(--color-primary-accent); 
}

.trend-text {
  display: block;
  font-size: 0.75rem;
  font-weight: normal;
  opacity: 0.8;
  margin-top: 0.25rem;
}

/* Toggle Placeholder Styling */
.toggle-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-left: 1rem;
}

.toggle-btn {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
  border: none;
  padding: 0.3rem 0.6rem;
  font-size: 0.8rem;
  margin: 2px 0;
}

</style>