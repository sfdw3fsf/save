<template src="./index.template.html"></template>
<script>
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
export default {
  name: 'DauNoiKCv2',
  components: {
    'GraphVizControl1': () => import('../../components2/GraphVizControl1.vue'),
    'GraphVizControl2': () => import('../../components2/GraphVizControl2.vue')
  },
  props: {
    modalClass: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    options: {
      type: Array,
      default: () => []
    },
    scale: {
      type: String,
      default: 'lg',
      description: 'Modal size',
      validator (value) {
        let acceptedValues = ['', 'sm', 'md', 'lg', 'xl']
        return acceptedValues.indexOf(value) !== -1
      }
    },
    isBtnX: {
      type: Boolean,
      default: true }
  },
  data () {
    return {
      kieuDauNoi: null,
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
    async  kieuDauNoi (val) {
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
    async  onShown () {
      this.$emit('shown')
      console.log(13123123)
      this.$refs.GraphVizControl1 && this.$refs.GraphVizControl1.setNode(this.nodeDauNoi)
      this.$refs.GraphVizControl1 && await this.$refs.GraphVizControl1.loadData()
    },
    onClose () {
      this.$emit('close')
    },
    show (nodeDauNoi) {
      this.nodeDauNoi = nodeDauNoi
      this.kieuDauNoi = 0
      this.$refs['modal-container'].show()
    },
    close () {
      this.nodeDauNoi = {}
      this.kieuDauNoi = 0
      this.$refs['modal-container'].hide()
      this.$emit('close')
    }
  },
  async mounted () {
  }
}
</script>
<style scoped>
#dauNoi
{
  height: calc(100vh - 230px)
}
</style>
