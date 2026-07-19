<template>
  <vue-card label="Thông tin Phân loại dịch vụ">
      <b-row>
        <b-col sm="12" cols="12">
          <vue-select v-model="formProps.dichVuVTId" :required="true" :labelWidth="'150'" :disable="this.formProps.dichVuVTId == null" :options="options.options_dsDichVuVT" id="dichVuVT" labelField="TEN_DVVT" valueField="DICHVUVT_ID" label="Dịch vụ viễn thông"></vue-select>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="12" cols="12">
          <vue-select v-model="formProps.loaiHinhTBId" :required="true" :labelWidth="'150'" :disable="this.formProps.loaiHinhTBId == null" :options="options.options_dsloaiHinhTB" id="loaiHinhTBId" labelField="LOAIHINH_TB" valueField="LOAITB_ID" label="Loại hình thuê bao"></vue-select>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="12" cols="12">
          <vue-input v-model="formProps.tenPhanLoai" :labelWidth="'150'" :required="true" label="Tên phân loại dịch vụ" :disable="this.formProps.tenPhanLoai == null" :checkbox="false" > </vue-input>
        </b-col>
      </b-row>
  </vue-card>
</template>
<script>
export default {
  components: {
  },
  props: ['formProps'],
  data: () => ({
    options: {
      options_dsDichVuVT: [],
      options_dsloaiHinhTB: [],
      data_dsloaiHinhTB: []
    }
  }),
  watch: {
    'formProps.dichVuVTId' (newValue, oldValue) {
      if (newValue != oldValue) {
        this.options.options_dsloaiHinhTB = this.options.data_dsloaiHinhTB.filter(x => x.DICHVUVT_ID == newValue)
        if (this.options.options_dsloaiHinhTB.length > 0) {
          this.formProps.theFirst_LoaiHinhTBId = this.options.options_dsloaiHinhTB[0].LOAITB_ID
          this.formProps.loaiHinhTBId = this.formProps.theFirst_LoaiHinhTBId
        } else {
          this.formProps.theFirst_LoaiHinhTBId = null
          this.formProps.loaiHinhTBId = null
        }
      }
    },
    'formProps.loaiHinhTBId' (newValue, oldValue) {
      if (newValue != oldValue) {
        this.$emit('filter-data-grid', newValue)
        // this.$emit('reset-form')
      }
    },
    formProps: {
      handler (newValue, oldValue) {
        this.$emit('update-form-pr', newValue)
      },
      deep: true
    }
  },
  computed: {},
  created () {
    this.$nextTick(() => {
      this.getDanhSachLoaiHinhTB()
    })
  },
  mounted () {},
  methods: {
    async getDanhSachLoaiHinhTB () {
      this.options.data_dsloaiHinhTB = await this.$store.dispatch('lhtbCommon/getDanhSachLoaiHinhTB')
      this.getDanhSachDichVuVT()
    },
    async getDanhSachDichVuVT () {
      const data = await this.$store.dispatch('dvvtCommon/getDanhSachDichVuVienThong')
      this.options.options_dsDichVuVT = data

      if (data.length > 0) {
        this.formProps.theFirst_DichVuVTId = data[0].DICHVUVT_ID
        this.$emit('load-data-grid', true)
      }
    }
  }
}
</script>
