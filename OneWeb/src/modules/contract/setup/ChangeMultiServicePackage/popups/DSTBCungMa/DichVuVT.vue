<template>
  <div>
    <SelectExt v-model="cboDichVuVT.value" :disabled="false" placeholder="Chọn dịch vụ vt" :dataSource="cboDichVuVT.data"
               dataTextField="TEN_DVVT"
               dataValueField="DICHVUVT_ID"></SelectExt>
  </div>
</template>

<script>
import API from './API'

export default {
  name: 'DichVuVT',
  data () {
    return {
      data: {
      },
      cboDichVuVT: {
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
    'cboDichVuVT.value': function (newValue) {

    }
  },
  mounted () {
    this.GET_DICHVU_VT()
  },
  methods: {
    async GET_DICHVU_VT () {
      let res = await API.GET_DICHVU_VT(this.axios)
      if (res.data.error_code === 'BSS-00000000' && res.data.data) {
        this.cboDichVuVT.data = res.data.data
      }
    }
  }
}
</script>

<style scoped>

</style>
