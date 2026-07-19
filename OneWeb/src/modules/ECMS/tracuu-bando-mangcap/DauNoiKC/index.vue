<template src="./index.html">
</template>

<script>
export default {
  name: 'DauNoiKC',
  components: {
    'GraphVizControl1': () => import('../../../CABMAN/BanDoMangCap/components2/GraphVizControl1.vue')
  },
  props: {
    id: String,
    ketcuoi_id: Number
  },
  computed: {
    graphID1 () {
      const uint32 = window.crypto.getRandomValues(new Uint32Array(1))[0]
      return 'graph-dohoa-oneBSS-' + uint32.toString(16)
    }
  },
  methods: {
    onModalHidden () {
      this.$bvModal.hide(this.id)
    },
    async focusMyElement () {
      let node = {}
      if (this.ketcuoi_id) {
        console.log('🚀 ~ file: index.vue:27 ~ focusMyElement ~ this.ketcuoi_id:', this.ketcuoi_id)
        node.KETCUOI_ID = 'KETCUOI_' + this.ketcuoi_id
        this.$refs.GraphVizControl1 && this.$refs.GraphVizControl1.setNode(node)
        this.$refs.GraphVizControl1 && this.$refs.GraphVizControl1.viewOnly(true)
        this.$refs.GraphVizControl1 && await this.$refs.GraphVizControl1.loadData()
      }
    }
  }
}
</script>

<style scoped>

</style>
