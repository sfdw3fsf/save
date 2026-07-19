<template src="./template.html"></template>
<style src="./style.scss" type="scss"></style>
<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import {mockData} from './MockData'
import frmXacNhanXoaPhieu from './Popups/frmXacNhanXoaPhieu.vue'
  export default {
    components: {
      breadcrumb,
      frmXacNhanXoaPhieu
    },
    data() {
      return {
        dtThanhToan: new Date(),
        dtDSChiTra: []
      }
    },
    mounted() {
      this.getDSChiTra()
    },
    methods: {
      getDSChiTra(){
        this.dtDSChiTra = mockData
        console.log(this.dtDSChiTra)
      }
    },
  }
</script>

