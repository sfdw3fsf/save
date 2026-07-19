<template src="./index.template.html"></template>
<script>
import geojsonData from '../quanly-tainguyen/MapComponent/region.json'
import { createMapOptions, loadRegionLayers, calculateBounds } from './mapUtil'

const TOKEN = 'VNPT-f76bf568-b093-45cf-9761-f6339d3e7d9a'

export default {
  name: 'PageMapIDC',
  data() {
    return {
      mapVNPT: null,
      provinceLayers: []
    }
  },
  mounted() {
    this.initMap()
  },
  beforeDestroy() {
    if (this.mapVNPT) {
      this.provinceLayers.forEach((layer) => {
        if (layer && layer.remove) {
          layer.remove()
        }
      })
      this.mapVNPT.remove()
    }
  },
  methods: {
    initMap() {
      const options = createMapOptions()
      
      // Tính center từ bounds và điều chỉnh lên trên một chút
      const bounds = calculateBounds(geojsonData)
      const center = [
        (bounds[0][0] + bounds[1][0]) / 2,
        (bounds[0][1] + bounds[1][1]) / 2 + 0.6
      ]
      
      options.center = center
      options.zoom = 5
      
      this.mapVNPT = VnptMap.initMap('mapVNPT', TOKEN, options)

      this.mapVNPT.on('load', () => {
        loadRegionLayers(this.mapVNPT, geojsonData, this.provinceLayers)
      })
    }
  }
}
</script>
