<template>
  <div>
    <SelectExt v-model="cbLoaiHinh.value" :disabled="false" placeholder="Chọn kiểu loại hình thuê bao" :dataSource="cbLoaiHinh.data"
               dataTextField="LOAIHINH_TB"
               dataValueField="LOAITB_ID"></SelectExt>
  </div>
</template>

<script>
import API from './API'

export default {
  name: 'LoaiHinhTB',
  data () {
    return {
      data: {
      },
      cbLoaiHinh: {
        data: [],
        value: 0
      },
      selected: null
    }
  },
  computed: {
    parent: function () {
      return this.$parent.$parent.$parent
    }
  },
  watch: {
    'cbLoaiHinh.value': function (newValue) {

    }
  },
  mounted () {
    this.CSS_LOAIHINH_TB()
  },
  methods: {
    async CSS_LOAIHINH_TB () {
      let res = await API.CSS_LOAIHINH_TB(this.axios)
      if (res.data.error_code === 'BSS-00000000' && res.data.data) {
        this.cbLoaiHinh.data = res.data.data
      }
    }
  }
}
</script>

<style scoped>

</style>
