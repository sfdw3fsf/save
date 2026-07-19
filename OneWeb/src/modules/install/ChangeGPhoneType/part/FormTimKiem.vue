<template>
  <vue-card label="Thông tin phiếu yêu cầu">
      <b-row>
        <b-col sm="6" cols="12">
          <vue-select v-model="form_temp.dichvuvt_id" :options="options.options_dsdv" labelField="ten_dvvt" valueField="dichvuvt_id" label="Dịch vụ VT"></vue-select>
        </b-col>
        <b-col sm="3" cols="12">
          <vue-input v-model="form_temp.ma_kh" label="Mã khách hàng"></vue-input>
        </b-col>
        <b-col sm="3" cols="12">
          <vue-date v-model="form_temp.ngay_yc" label="Ngày yêu cầu"></vue-date>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-input v-model="form_temp.ma_gd" label="Mã giao dịch"></vue-input>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-input v-model="form_temp.ten_kh" label="Tên khách hàng"></vue-input>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-input v-model="form_temp.ma_tb" label="Số máy/Acc"></vue-input>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-input v-model="form_temp.diachi_kh" label="Địa chỉ KH"></vue-input>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-date v-model="form_temp.ngay_ht" label="Ngày thực hiện"></vue-date>
        </b-col>
        <b-col sm="6" cols="12">
          <vue-input v-model="form_temp.ten_tb" label="Tên thuê bao"></vue-input>
        </b-col>
      </b-row>
    </vue-card>
</template>
<script>
import api from '../api'
export default {
  data: () => ({
    options: {
      options_dsdv: []
    },
    form_temp: {
      dichvuvt_id: '10',
      hdtb_id: '',
      ma_gd: '',
      ma_tb: '',
      ma_kh: '',
      ten_kh: '',
      diachi_kh: '',
      ten_tb: '',
      loaitb_id: '',
      phieu_id: '',
      ngay_th: ''
    }
  }),
  computed: {
    vmodel: {
      get () {
        return this.value
      },
      set (key, value) {
        this.$emit('input', this.form_temp)
      }
    }
  },
  props: {
    value: Object
  },
  created () {
    this.$nextTick(() => {
      this.form_temp = this.value
      this.getDanhSachDichVu()
    })
  },
  methods: {
    getDanhSachDichVu () {
      api.getDanhSachDichVu(this.axios, {
        p_phanvung_id: 26,
        p_nguoidung_id: 1
      }).then(response => {
        this.options.options_dsdv = response.data.data
        this.form_temp.dichVuVTId = this.options.options_dsdv[0] ? this.options.options_dsdv[0].dichvuvt_id : null
      })
    },
  }
}
</script>
