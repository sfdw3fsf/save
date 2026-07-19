<template>
    <DiagramDanhSachToaNha :id="idc" />
</template>
<script>
import DiagramDanhSachToaNha from './components/DiagramDanhSachToaNha/index.vue'

export default {
  name: 'SodoToanhaTrongIdc',
  components: {
    DiagramDanhSachToaNha
  },
  created() {
    this.idc = Number(this.$route.query.idc) || 1
  },
  data() {
    return {
      idc: null
    }
  },
  methods: {
  }
}
</script>
