<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
import ModalTracnghiemExt from '../../../modal/TracNghiem'
export default {
  name: 'pane2',
  components: {
    ModalTracnghiemExt,
    'GraphVizControl1': () => import('../../../components2/GraphVizControl1.vue'),
    'GraphVizControl2': () => import('../../../components2/GraphVizControl2.vue')
  },
  data () {
    return {
      kieuDauNoi: 0,
      nodeDauNoi: {}
    }
  },
  computed: {
    graphID1 () {
      const uint32 = window.crypto.getRandomValues(new Uint32Array(1))[0]
      return 'graph-dohoa-oneBSS-' + uint32.toString(16)
    },
    graphID2 () {
      const uint32 = window.crypto.getRandomValues(new Uint32Array(1))[0]
      return 'graph-daunhanh-oneBSS-' + uint32.toString(16)
    }
  },
  watch: {
    async kieuDauNoi (val) {
      if (val === 0) {
        this.$refs.GraphVizControl1 && this.$refs.GraphVizControl1.setNode(this.nodeDauNoi)
        this.$refs.GraphVizControl1 && await this.$refs.GraphVizControl1.loadData()
      }
      if (val === 1) {
        this.$refs.GraphVizControl2 && this.$refs.GraphVizControl2.setNode(this.nodeDauNoi)
        this.$refs.GraphVizControl2 && await this.$refs.GraphVizControl2.loadData()
      }
    }
  },
  methods: {
    dauNoiNhanh () { this.kieuDauNoi = 1 },
    dauNoiDoHoa () { this.kieuDauNoi = 0 },
    hidePanelDauNoi () {
      EventBus.$emit('HIDE_PANE_DAUNOI', 'HIDE_PANE_DAUNOI')
    },
    async SHOW_TRAC_NGHIEM (node) {
      try {
        this.kieuDauNoi = -1
        var tracnghiem = {
          title: 'Chọn giao diện đấu nối',
          caption: 'Chọn 1 giao diện đấu nối',
          options: ['Giao diện đồ họa', 'Giao diện đấu nối nhanh'],
          data: null
        }
        var result = await this.$refs.ModalTracnghiemExt2.showWithSetChecked(tracnghiem, 0)
        console.log('🚀 ~ file: index.vue ~ line 30 ~ mounted ~ result', result)
        this.kieuDauNoi = result.option === null ? 0 : result.option
        this.nodeDauNoi = node
        console.log('🚀 ~ file: index.vue ~ line 52 ~ SHOW_TRAC_NGHIEM ~ this.nodeDauNoi', this.nodeDauNoi)
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 41 ~ mounted ~ error', error)
        this.kieuDauNoi = 0
        this.nodeDauNoi = node
      }
    }
  },
  async mounted () {
    EventBus.$on('SHOW_TRAC_NGHIEM', this.SHOW_TRAC_NGHIEM)
  }
}
</script>
<style scoped>
#dauNoi
{
  height: calc(100vh - 230px)
}
</style>
