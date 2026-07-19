<template>
  <div class="text-center position-relative d-inline-block">
    <!-- Vòng tròn Syncfusion -->
    <ejs-accumulationchart
      v-if="percentage > 0"
      :background="'transparent'"
      enableSmartLabels="false"
      :tooltip="false"
      :legendSettings="{ visible: false }"
      :margin="{ top: 0, bottom: 0 }"
      style="height: 180px; width: 180px;"
    >
      <e-accumulation-series-collection>
        <e-accumulation-series
          :dataSource="[
            { x: 'Used', y: percentageForDisplay },
            { x: 'Remaining', y: 100 - percentageForDisplay }
          ]"
          xName="x"
          yName="y"
          innerRadius="85%"
          radius="100%"
          :startAngle="0"
          :endAngle="360"
          :explode="false"
          :dataLabel="{ visible: false }"
          :palettes="[color, '#edf0f2']"
        />
      </e-accumulation-series-collection>
    </ejs-accumulationchart>

     <!-- Vòng tròn static khi percentage = 0 -->
    <div v-else class="empty-circle" style="height: 180px; width: 180px;">
      <svg width="180" height="180" viewBox="0 0 180 180">
        <circle
          cx="90"
          cy="90"
          r="76.5"
          fill="none"
          stroke="#edf0f2"
          stroke-width="27"
        />
      </svg>
    </div>

    <!-- Text phần trăm nằm giữa -->
    <div class="position-absolute top-50 start-50 translate-middle text-center" style="pointer-events: none;">
      <h4 class="fw-bold mb-0">{{ Math.round(percentage) }}%</h4>
    </div>

    <!-- Label bên dưới -->
    <p class="text-muted small mt-2">{{ label }}</p>
  </div>
</template>

<script>
import {
  AccumulationChartComponent,
  AccumulationSeriesCollectionDirective,
  AccumulationSeriesDirective,
  PieSeries,
  AccumulationLegend,
  AccumulationDataLabel,
  AccumulationTooltip
} from '@syncfusion/ej2-vue-charts'

export default {
  name: 'PieProgressCard',
  components: {
    'ejs-accumulationchart': AccumulationChartComponent,
    'e-accumulation-series-collection': AccumulationSeriesCollectionDirective,
    'e-accumulation-series': AccumulationSeriesDirective
  },
  props: {
    used: { type: Number, default: 0 },
    total: { type: Number, default: 100 },
    label: { type: String, default: 'Progress' },
    color: { type: String, default: '#3b82f6' }
  },
  computed: {
    percentage() {
      if (this.total === 0) return 0
      return (this.used / this.total) * 100
    },
    percentageForDisplay () {
      return this.percentage > 100 ? 100 : this.percentage
    }
  },
  provide: {
    accumulationchart: [PieSeries, AccumulationTooltip, AccumulationDataLabel, AccumulationLegend]
  }
}
</script>

<style scoped>

.text-center {
  position: relative;
  display: inline-block;
}
</style>
