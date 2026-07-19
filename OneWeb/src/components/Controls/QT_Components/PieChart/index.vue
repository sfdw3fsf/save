<template>
  <div id="app">
    <ejs-accumulationchart id="container" :title="title" :legendSettings='legendSettings'>
      <e-accumulation-series-collection>
        <e-accumulation-series :dataSource="seriesData" xName="x" yName="y" :dataLabel="dataLabel"> </e-accumulation-series>
      </e-accumulation-series-collection>
    </ejs-accumulationchart>
  </div>
</template>
<script>
import {  AccumulationChartComponent, AccumulationSeriesCollectionDirective, AccumulationSeriesDirective, PieSeries, AccumulationLegend, AccumulationDataLabel  } from "@syncfusion/ej2-vue-charts";

export default {
  name: 'App',
  components: {
    'ejs-accumulationchart': AccumulationChartComponent,
    'e-accumulation-series-collection': AccumulationSeriesCollectionDirective,
    'e-accumulation-series': AccumulationSeriesDirective
  },
  data() {
    return {
      seriesData: [
        { x: 'USA', y: 50 },
        { x: 'China', y: 40 },
        { x: 'Japan', y: 10 }
      ],
      title: 'Browser Market Shares in November 2023',
      dataLabel: {
        visible: true,
        name: 'x',
        position: 'Outside',
        font: {
          fontWeight: '600'
        }
      },
      legendSettings: {
        visible: true,
        position: 'Right'
      },
    }
  },
  provide: {
    accumulationchart: [PieSeries, AccumulationLegend, AccumulationDataLabel]
  }
}
</script>
<style>
#container {
  height: 350px;
}
</style>
