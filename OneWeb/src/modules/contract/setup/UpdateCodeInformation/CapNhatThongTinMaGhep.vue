<template>
  <div>
    <UpdateCodeInformation ref="UpdateCodeInformation" :inputData="model"/>
    <a href="#" @click="showPopupUpdateCodeInformation" v-show="false">Show modal</a>
  </div>
</template>

<script>
import UpdateCodeInformation from './UpdateCodeInformation'

export default {
  name: 'CapNhatThongTinMaGhep',
  components: {
    UpdateCodeInformation
  },
  data () {
    return {
      model: {
        'khachhang_id': 0,
        'fmig_id': 0,
        'tucthi': 0,
        'kyhoadon': '',
        'vma_kh': '',
        'ds_MaIG': [],
        'ds_GanMIG': []
      }
    }
  },
  mounted () {
    this.$refs.UpdateCodeInformation.showDialog()
  },
  methods: {
    showPopupUpdateCodeInformation () {
      this.$refs.UpdateCodeInformation.showDialog()
    }
  }
}
</script>

<style scoped>

</style>
