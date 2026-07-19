<template>
  <div class="bar_chart" v-if="isShow">
    <ejs-chart :style="{ height: height }" :primaryXAxis="primaryXAxis">
      <e-series-collection>
        <e-series :dataSource="seriesData"  :marker='marker' columnWidth="0.5" columnSpacing="0.5" type="Column" xName="label" yName="value" name="Gold" pointColorMapping="fill"> </e-series>
      </e-series-collection>
    </ejs-chart>
  </div>
</template>
<script>
import { ChartComponent, SeriesCollectionDirective, SeriesDirective, ColumnSeries, Category, DataLabel } from '@syncfusion/ej2-vue-charts'
const mainColors = ['#488AFA', '#EF9A1D', '#EF4838', '#FCED4A', '#51AF4E', '#9C9C9C', '#9A2DAC']
export default {
  name: 'App',
  components: {
    'ejs-chart': ChartComponent,
    'e-series-collection': SeriesCollectionDirective,
    'e-series': SeriesDirective
  },
  props: {
    dataSource: {
      type: Array,
      default: () => []
    },
    isShow: {
      type: Boolean,
      default: false
    },
    height: {
      type: String,
      default: '350px'
    }
  },
  data() {
    return {
      border: {
        color: 'black',
        width: 2
      },
      primaryXAxis: {
        valueType: 'Category'
      },
      marker: {
        //Data label for chart series
        dataLabel: { visible: true }
      },
      title: 'Olympic Medals'
    }
  },
  computed: {
    seriesData() {
      return this.dataSource.map((item, index) => {
        return {
          label: item.label,
          value: item.value,
          fill: mainColors[index]
        }
      })
    }
  },
  created() {
    // this.seriesData = this.dataSource
  },
  methods: {
    show() {
      this.isShow = true
    },
    hide() {
      this.isShow = false
    }
  },
  provide: {
    chart: [ColumnSeries, Category, DataLabel]
  }
}
</script>
<style></style>
