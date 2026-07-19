<template>
  <div class="pie_chart">
    <ejs-accumulationchart :style="{ height: height }" v-if="isShow" enableSmartLabels="enableSmartLabels">
      <e-accumulation-series-collection>
        <e-accumulation-series :dataSource="seriesData" pointColorMapping="fill" xName="label" yName="value" innerRadius="50%" :dataLabel="datalabel"> </e-accumulation-series>
      </e-accumulation-series-collection>
    </ejs-accumulationchart>
  </div>
</template>
<script>
import { AccumulationChartComponent, AccumulationSeriesCollectionDirective, AccumulationSeriesDirective, PieSeries, AccumulationDataLabel } from '@syncfusion/ej2-vue-charts'

export default {
  name: 'App',
  components: {
    'ejs-accumulationchart': AccumulationChartComponent,
    'e-accumulation-series-collection': AccumulationSeriesCollectionDirective,
    'e-accumulation-series': AccumulationSeriesDirective
  },
  props: {
    dataSource: {
      type: Array,
      default: () => []
    },
    isShow: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // seriesData: data
      totalValue: 0,
      datalabel: { visible: true, name: 'text', position: 'Inside', template: '<div style="color:black">${point.text}</div>' },
      mainColors: ['#488AFA', '#EF9A1D', '#EF4838', '#FCED4A', '#51AF4E', '#9C9C9C', '#9A2DAC', '#14A6D1'],
      timeUpdate: 0,
      height: '300px'
    }
  },
  created() {
    this.totalValue = this.dataSource.reduce((acc, item) => acc + item.value, 0)
  },
  watch: {
    dataSource: {
      handler: function(val) {
        this.totalValue = val.reduce((acc, item) => acc + item.value, 0)
        if (this.timeUpdate === 0) {
          this.refreshData()
          this.timeUpdate = 1
          setTimeout(() => {
            this.refreshData()
          }, 200)
        } else {
          this.refreshData()
        }
      },
      deep: true
    },
    pageWidth: {
      handler: function(val) {
        if (val <= 1400) {
          this.height = '250px'
        } else {
          this.height = '300px'
        }
      },
      immediate: true
    }
  },
  computed: {
    seriesData() {
      return this.dataSource
        .map((item, index) => {
          return {
            label: item.label,
            value: (item.value / this.totalValue) * 100,
            text: `${item.label} - ${item.value}`,
            fill: this.mainColors[index]
          }
        })
        .filter((item) => item.value > 0)
    },
    pageWidth() {
      return window.innerWidth
    }
  },
  methods: {
    refreshData() {
      this.isShow = false
      setTimeout(() => {
        this.isShow = true
      }, 100)
    }
  },
  provide: {
    accumulationchart: [PieSeries, AccumulationDataLabel]
  }
}
</script>
<style></style>
