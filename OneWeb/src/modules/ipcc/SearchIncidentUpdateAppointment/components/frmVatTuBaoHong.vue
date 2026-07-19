<template></template>
<script>
import api from '../Api'
import frmApi from './PopUpApi.js'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
export default {
  name: 'frmVatTuBaoHong',
  components: {
    moment,
    DatePicker,
    api,
    frmApi,
    ExportDataModal
  },
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      typeFormat: 'datetime',
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      hdtb_id: 0,
      baohong_id: 0
    }
  },
  created() {},
  methods: {
    lay_ds_trangbi: async function () {
      var input = {}
      return this.GetData(await frmApi.lay_ds_trangbi(this.axios, input))
    },
    sp_lay_ds_kieu_tbi: async function () {
      var input = {}
      return this.GetData(await frmApi.sp_lay_ds_kieu_tbi(this.axios, input))
    },
    sp_lay_ds_donvi_tbi: async function () {
      var input = {}
      return this.GetData(await frmApi.sp_lay_ds_donvi_tbi(this.axios, input))
    },
    sp_lay_ds_tinhtrang_tbi: async function () {
      var input = {}
      return this.GetData(await frmApi.sp_lay_ds_tinhtrang_tbi(this.axios, input))
    },
    sp_lay_ds_loaitbi_baohong: async function () {
      var input = {}
      return this.GetData(await frmApi.sp_lay_ds_loaitbi_baohong(this.axios, input))
    },
    lay_ds_vattu_baohong: async function () {
      var input = { baohong_id: this.baohong_id }
      return this.GetData(await frmApi.lay_ds_vattu_baohong(this.axios, input))
    },
    sp_lay_ds_vattu_dbtb: async function () {
      var input = { thuebao_id: 430661 }
      return this.GetData(await frmApi.sp_lay_ds_vattu_dbtb(this.axios, input))
    },
    sp_giaophieutbi_baohong_ins: async function () {
      var input = {
        json_giaophieutbi:
          '[{"phieu_id":"1", "ngay_th":"01/01/2021 00:00:00", "loaitbi_id":"1", "kieutbi_id":"1","sl":"1", "trangbi_id":"1", "seri":"1", "nguoidung_id":"1", "nhanvien_id":"1","donvi_id":"1", "nguoi_cn":"1", "may_cn":"1", "ip_cn":"1", "ngay_cn":"01/01/2021 00:00:00","ghichu":"1", "vattu_id":"1", "tinhtrangtbi_id":"1"}}]'
      }
      return this.GetData(await frmApi.sp_giaophieutbi_baohong_ins(this.axios, input))
    },
    sp_capnhat_sua_vattu_bh: async function () {
      var input = {
        json_giaophieutbi:
          '[{"phieu_id":"1", "ngay_th":"01/01/2021 00:00:00", "loaitbi_id":"1", "kieutbi_id":"1","sl":"1", "trangbi_id":"1", "seri":"1", "nguoidung_id":"1", "nhanvien_id":"1","donvi_id":"1", "nguoi_cn":"1", "may_cn":"1", "ip_cn":"1", "ngay_cn":"01/01/2021 00:00:00","ghichu":"1", "vattu_id":"1", "tinhtrangtbi_id":"1"}}]',
        thuebao_id: 514691,
        nguoi_cn: 'x',
        may_cn: 'x',
        ip_cn: 'x'
      }
      return this.GetData(await frmApi.sp_capnhat_sua_vattu_bh(this.axios, input))
    },
    sp_sua_giaophieu_tbi_upd: async function () {
      var input = {
        json_giaophieutbi:
          '[{"phieu_id":"1", "ngay_th":"01/01/2021 00:00:00", "loaitbi_id":"1", "kieutbi_id":"1","sl":"1", "trangbi_id":"1", "seri":"1", "nguoidung_id":"1", "nhanvien_id":"1","donvi_id":"1", "nguoi_cn":"1", "may_cn":"1", "ip_cn":"1", "ngay_cn":"01/01/2021 00:00:00","ghichu":"1", "vattu_id":"1", "tinhtrangtbi_id":"1"}}]'
      }
      return this.GetData(await frmApi.sp_sua_giaophieu_tbi_upd(this.axios, input))
    },
    sp_xoa_gptbi_theo_rvt_del: async function () {
      var input = { rvt_id: 1 }
      return this.GetData(await frmApi.sp_xoa_gptbi_theo_rvt_del(this.axios, input))
    },
    xoa_cap_vattu_bh: async function () {
      var input = { rvt_id: 1, thuebao_id: 430661 }
      return this.GetData(await frmApi.lay_ds_trangbi(this.axios, input))
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>