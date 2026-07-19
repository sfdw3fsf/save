

<template>
  <div class="select-chart">
    <div @click="selectChart('pie')" class="select-chart__item" :class="localValue == 'pie' ? 'select-chart__item--active' : ''" v-if="listChart.includes('pie')">
      <Icon icon="fa-solid:chart-pie" />
    </div>
    <div @click="selectChart('bar')" class="select-chart__item" :class="localValue == 'bar' ? 'select-chart__item--active' : ''" v-if="listChart.includes('bar')">
      <Icon icon="fa:bar-chart" />
    </div>
    <div @click="selectChart('list')" class="select-chart__item" :class="localValue == 'list' ? 'select-chart__item--active' : ''" v-if="listChart.includes('list')">
      <Icon icon="fluent:list-16-filled" />
    </div>
  </div>
</template>

<script>
import { Icon } from '@iconify/vue2'

export default {
  components: {
    Icon
  },
  props: {
    value: {
      type: String,
      default: 'pie'
    },
    listChart: {
      type: Array,
      default: () => ['pie', 'bar', 'list']
    },
  },
  data() {
    return {
      localValue: this.value
    }
  },
  watch: {
    value(newVal) {
      this.localValue = newVal;
    }
  },
  methods: {
    selectChart(chart) {
      this.localValue = chart;
      this.$emit('input', chart);
    }
  }
}
</script>


<style lang="scss" scoped>
.select-chart {
  display: flex;
  gap: 10px;
  &__item {
    width: 50px;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 6px;
    transform: translate(0,-10px);
    border: 1px solid #ccc;
    cursor: pointer;
    svg {
      width: 16px;
      height: 16px;
    }
    &--active {
      border: 2px solid #007bff;
      color: #007bff;
    }
    &:hover{
      background-color: #007bff;
      color: #fff;
    }
  }
}
</style>
