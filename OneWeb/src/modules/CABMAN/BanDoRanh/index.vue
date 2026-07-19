<template src="./index.template.html"></template>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from './store'
import toGeoJSON from '@mapbox/togeojson'
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
const leftPaneContent = Vue.component('leftPaneContent', require('./leftPane').default)
const rightPaneContent = Vue.component('rightPaneContent', require('./rightPane').default)
export default {
  components: { leftPaneContent, rightPaneContent},
  name: 'BanDoRanh',
  computed: {
    ...mapState('cabman/BanDoRanh', statePropertyName),
    ...mapState('cabman', ['BanDoRanh']),
    ...mapGetters('cabman/BanDoRanh', getterName),
    isLuuRanhTram()
    {
      return false
    }
  },
  data () {
    return {
      leftPaneContent: function () {
        return { template: leftPaneContent }
      },
      rightPaneContent: function () {
        return { template: rightPaneContent }
      },
      modalShow: false
    }
  },
  methods: {
    ...mapActions('cabman/BanDoRanh', actionName),
    openFileKML(){
      document.getElementById('fileInput').click()
    },
    napLai(){},
    LuuRanhTram(){
      EventBus.$emit('LUU_RANH', 'LUU_RANH')
    },
    NapFileMapGoogle(e) {
      let s = e.target.files[0].name.toLowerCase()
      if (s.endsWith('.kmz')) {
        this.NapKMZ(e)
        return
      }
      if (s.endsWith('.kml')) {
        this.NapKML(e)
        return
      }
    },
    NapKMZ(e){
      let reader = new FileReader()
    },
    NapKML (e) {
      let reader = new FileReader()
      reader.onload = async (e) => {
        const data = e.target.result
        var parser = new DOMParser()
        var textXML = parser.parseFromString(data, 'text/xml')
        var response = toGeoJSON.kml(textXML)
        var sGeoJSON = this.mergeDuplicateFeatures(response)
        var json = []
        console.log('🚀 ~ reader.onload= ~ sGeoJSON:', sGeoJSON)
        if (sGeoJSON.type === 'FeatureCollection' && sGeoJSON.features.length > 0) {
          console.log('🚀 ~ reader.onload= ~ sGeoJSON.features:', sGeoJSON.features)
          try {
            this.$root.showLoading(true)
          for (let index = 0; index < sGeoJSON.features.length; index++) {
            var feature = sGeoJSON.features[index]
            var coordinates = feature.geometry.coordinates
            var day_toado = null
            var day_toado2 = null
            if (coordinates.length > 0) {
              day_toado = coordinates[0].map(x => x[0] + ',' + x[1]).join(';')
              day_toado2 = coordinates[0].map(x => x[0] + ',' + x[1]).join(',')
            }
            var properties = feature.properties
            var ten = properties.name
            var style_stroke = properties['stroke']
            var style_stroke_opacity = properties['stroke-opacity']
            var style_stroke_width = properties['stroke-width']
            var style_fill = properties['fill']
            var style_fill_opacity = properties['fill-opacity']
            json.push({
              ten,
              day_toado,
              style_stroke,
              style_stroke_opacity,
              style_stroke_width,
              style_fill,
              style_fill_opacity,
              day_toado2
            })
          }
          var myJsonString = JSON.stringify(json)
          var input = {
            p_ttvt_id: this.ttvt_id,
            p_json: myJsonString,
            p_nhanvien_id: this.$root.token.getNhanVienID()
          }
          let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat_ranh_tram', input)
          console.log('🚀 ~ reader.onload= ~ rs:', rs)
          EventBus.$emit('LOAD_RANHTRAM', null)
          this.$root.showLoading(false)
          } catch (error) {
            console.log("🚀 ~ file: index.vue:107 ~ reader.onload= ~ error:", error)
            this.$root.showLoading(false)
          }
        }
      }
      reader.readAsText(e.target.files[0])
    },
    mergeDuplicateFeatures (geoJson) {
      const featuresByName = {}
      const deduplicatedFeatures = []
      for (const feature of geoJson.features) {
        const previouslyEncounteredFeature = featuresByName[feature.properties.name]

        if (previouslyEncounteredFeature) {
          Object.assign(previouslyEncounteredFeature.properties, feature.properties)
        } else {
          featuresByName[feature.properties.name] = feature
          deduplicatedFeatures.push(feature)
        }
      }
      return {
        ...geoJson,
        features: deduplicatedFeatures
      }
    },
    isEmpty (inputObject) {
      return Object.keys(inputObject).length === 0
    },
  },
  watch: {
  },
  async created () {
    this.clearState()
    this.createApiManager()
    this.set_TabPageIndex(0)
  },
  async mounted () {
    let style = document.createElement('link');
    style.type = "text/css";
    style.rel = "stylesheet";
    style.href = 'https://maps.vnpt.vn/storage/vnptmaps/plugins/maps-draw/v1.1.0/vnptmaps-draw.css';
    document.head.appendChild(style);
  }
}
</script>
<style scoped src="./index.scss">
</style>
