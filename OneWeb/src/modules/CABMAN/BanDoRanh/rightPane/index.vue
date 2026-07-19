<template src="./index.template.html"></template>
<script>
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
import BanDoVNPT from '../components/BanDoVNPT.vue'
import { mapActions, mapState, mapGetters } from 'vuex'
import { getterName, actionName, statePropertyName } from '../store'
Vue.use(SplitterPlugin)
export default {
  name: 'rightPaneContent',
  components: {BanDoVNPT},
  data () {
    return {
    }
  },
  computed: {
    ...mapState('cabman/BanDoRanh', statePropertyName),
    ...mapState('cabman', ['BanDoRanh']),
    ...mapGetters('cabman/BanDoRanh', getterName),
  },
  watch: {
  },
  methods: {
    ...mapActions('cabman/BanDoRanh', actionName),
    VE_RANH_TTVT2 (data) {
      this.$refs.mapRanhVNPT.VE_RANH_TTVT2(data)
    },
    VE_RANH_SET_ACTIVE(data) {
      this.$refs.mapRanhVNPT.SET_ACTIVE(data)
    },
    VE_RANH_SET_DEACTIVE(data) {
      this.$refs.mapRanhVNPT.SET_DEACTIVE(data)
    },
    VE_RANH_SET_VISBLE(data) {
      this.$refs.mapRanhVNPT.SET_VISBLE(data)
    },
    async LUU_RANH(){
      try {
        this.$root.showLoading(true)
        let polygonList = this.$refs.mapRanhVNPT.multiPolygonList.filter((p) => p.status==="NEW" && p.name !== null);
      if (polygonList.length>0 ) {
        let dataJson=polygonList.map((p) => {
          return {
            ten : p.name,
            day_toado : p.coordinate[0].map(x => x[0] + ',' + x[1]).join(';'),
            style_stroke: p.properties.STYLE_STROKE,
            style_stroke_opacity: p.properties.STYLE_STROKE_OPACITY,
            style_stroke_width: p.properties.STYLE_STROKE_WIDTH,
            style_fill: p.properties.STYLE_FILL,
            style_fill_opacity: p.properties.STYLE_FILL_OPACITY,
            day_toado2 : p.coordinate[0].map(x => x[0] + ',' + x[1]).join(','),
          }
        })
        if (dataJson.length>0) {
          var myJsonString = JSON.stringify(dataJson)
          var input = {
            p_ttvt_id: this.ttvt_id,
            p_json: myJsonString,
            p_nhanvien_id: this.$root.token.getNhanVienID()
          }
          let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat_ranh_tram', input)
          console.log("🚀 ~ file: index.vue:50 ~ LUU_RANH ~ rs:", rs)
        }
      }
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    }
  },
  async mounted () {
    EventBus.$on('VE_RANH_TTVT2', this.VE_RANH_TTVT2)
    EventBus.$on('VE_RANH_SET_ACTIVE', this.VE_RANH_SET_ACTIVE)
    EventBus.$on('VE_RANH_SET_DEACTIVE', this.VE_RANH_SET_DEACTIVE)
    EventBus.$on('VE_RANH_SET_VISBLE', this.VE_RANH_SET_VISBLE)
    EventBus.$on('LUU_RANH', this.LUU_RANH)
  },
  beforeDestroy () {
    this.VE_RANH_TTVT2 && EventBus.$off('VE_RANH_TTVT2', this.VE_RANH_TTVT2)
    this.VE_RANH_SET_ACTIVE && EventBus.$off('VE_RANH_SET_ACTIVE', this.VE_RANH_SET_ACTIVE)
    this.VE_RANH_SET_DEACTIVE && EventBus.$off('VE_RANH_SET_DEACTIVE', this.VE_RANH_SET_DEACTIVE)
    this.VE_RANH_SET_VISBLE && EventBus.$off('VE_RANH_SET_VISBLE', this.VE_RANH_SET_VISBLE)
    this.LUU_RANH && EventBus.$off('LUU_RANH', this.LUU_RANH)
  }
}
</script>
<style>
  .default-splitter .e-splitter.e-splitter-horizontal .e-pane.e-pane-horizontal, .e-splitter.e-splitter-vertical .e-pane.e-pane-horizontal
  {
      padding: 0px;
  }
</style>
