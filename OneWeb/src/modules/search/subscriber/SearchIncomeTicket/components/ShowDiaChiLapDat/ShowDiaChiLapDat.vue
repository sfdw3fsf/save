<template src="./ShowDiaChiLapDat.html"></template>
<script>
import api from '@/modules/search/subscriber/SearchIncomeTicket/Api.js'
import { DichVuVienThong } from '@/const/enums/index.js'
export default {
  name: 'ShowDiaChiLapDat',
  components: { api },
  data() {
    return {
      target: '#app .main-wrapper', // .main-wrappertcabc
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      isPopup: false,
      dichvuvt_id: 0,
      hdtb_id: 0,
      ma_tb: '',
      ma_gd: '',
      loai_hd: '',
      DmLoaiDichVu: [],
      txtMaGD: '',
      txtLoaiHD: '',
      cboDichVuVT: '',
      txtMa_TB: '',
      txtSoDT_Dau: '',
      txtSoDT_Cuoi: '',
      txtTocDo: '',
      txtDC_Cu: '',
      txtToTC_Cu: '',
      txtDC_Moi: '',
      txtToTC_Moi: '',
      txtDC_Dau_Cu: '',
      txtToTC_Dau_Cu: '',
      txtDC_Dau_Moi: '',
      txtToTC_Dau_Moi: '',
      txtDC_Cuoi_Cu: '',
      txtToTC_Cuoi_Cu: '',
      txtDC_Cuoi_Moi: '',
      txtToTC_Cuoi_Moi: ''
    }
  },
  watch: {
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  created() {
    Promise.all([this.GetDmLDichVuVTs()])
      .then((responses) => {})
      .catch((err) => {
        console.log('Promise.all', err)
      })
      .finally(() => {
        this.Loading = false
      })
  },
  methods: {
    async Lay_DiaChi_LD(hdtb_id, dichvuvt_id, kieu) {
      var input = {
        hdtb_id: hdtb_id,
        dichvu_id: dichvuvt_id,
        kieu: kieu
      }
      return this.GetData(await api.sp_lay_tt_show_diachi_ld(this.axios, input))
    },
    closeForm() {
      this.$refs.refDialogObjShowDiaChiLapDat.hide()
      this.$emit('closeFormDC', true)
    },
    async Lay_DonVi_ThiCong_HDTB(hdtb_id, dichvuvt_id, kieu) {
      try {
        var input = {
          hdtb_id: hdtb_id,
          dichvu_id: dichvuvt_id,
          kieu: kieu
        }
        return this.GetData(await api.sp_lay_donvi_thicong_hdtb(this.axios, input))
      } catch (err) {
        return null
      }
    },
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtb_id) {
      return await this.lay_ds_hopdong_thuebao_mgwan(hdtb_id)
    },
    fn_lay_dienthoai_lienhe: async function (hdtbIid) {
      try {
        let input = { hdtbIid: hdtbIid }
        let data = this.GetData(await api.fn_lay_dienthoai_lienhe(this.axios, input))
        return data
      } catch (err) {
        return null
      }
    },
    lay_ds_hopdong_thuebao_mgwan: async function (hdtbIid) {
      let input = { hdtbIid: hdtbIid }
      let data = this.GetData(await api.lay_ds_hopdong_thuebao_mgwan(this.axios, input))
      return data
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtb_id) {
      return await this.lay_ds_hopdong_thuebao_tls(hdtb_id)
    },
    lay_ds_hopdong_thuebao_tls: async function (hdtbIid) {
      let input = { hdtbIid: hdtbIid }
      let data = this.GetData(await api.lay_ds_hopdong_thuebao_tls(this.axios, input))
      return data
    },
    async map_tocdokenh(tocdo_id) {
      // let input = {
      //   id_neo: "tocdo || ' ' || donvi",
      //   in_table: 'css.tocdo_kenh',
      //   in_dk: `where tocdo_id = ${tocdo_id}`
      // }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_tocdo_kenh(this.axios, { param: tocdo_id, type: 1 }))
      return data
    },
    async openDialog(dichvuvt_id, hdtb_id, ma_tb, ma_gd, loai_hd) {
      //console.log('dichvuvt_id, hdtb_id, ma_tb, ma_gd, loai_hd', dichvuvt_id, hdtb_id, ma_tb, ma_gd, loai_hd)
      try {
        this.Loading = true
        this.dichvuvt_id = dichvuvt_id
        this.hdtb_id = hdtb_id
        this.ma_tb = ma_tb
        this.ma_gd = ma_gd
        this.loai_hd = loai_hd
        this.txtLoaiHD = this.loai_hd
        this.txtMaGD = ma_gd
        this.cboDichVuVT = this.dichvuvt_id
        this.txtMa_TB = ma_tb
        // địa chỉ
        let ds = await this.Lay_DiaChi_LD(this.hdtb_id, this.dichvuvt_id, 0)
        // console.log('ds',ds)
        if (ds != null) {
          this.txtToTC_Moi = ds['totc_moi'] == null ? '' : ds['totc_moi']
          this.txtDC_Cu = ds['dc_cu'] == null ? '' : ds['dc_cu']
          this.txtDC_Moi = ds['dc_moi'] == null ? '' : ds['dc_moi']
          this.txtDC_Cuoi_Cu = ds['dc_cuoi_cu'] == null ? '' : ds['dc_cuoi_cu']
          this.txtDC_Cuoi_Moi = ds['dc_cuoi_moi'] == null ? '' : ds['dc_cuoi_moi']
          this.txtDC_Dau_Cu = ds['dc_cuoi_cu'] == null ? '' : ds['dc_cuoi_cu']
          this.txtDC_Dau_Moi = ds['dc_dau_moi'] == null ? '' : ds['dc_dau_moi']
          this.txtTocDo = ds['tocdo'] == null ? '' : ds['tocdo']
          this.txtSoDT_Dau = ds['sdt_dau'] == null ? '' : ds['sdt_dau']
          this.txtSoDT_Cuoi = ds['sdt_cuoi'] == null ? '' : ds['sdt_cuoi']
          this.txtToTC_Dau_Moi = ds['totc_dau_moi'] == null ? '' : ds['totc_dau_moi']
          this.txtToTC_Dau_Cu = ds['totc_dau_cu'] == null ? '' : ds['totc_dau_cu']
          this.txtToTC_Cuoi_Moi = ds['totc_cuoi_moi'] == null ? '' : ds['totc_cuoi_moi']
          this.txtToTC_Cuoi_Cu = ds['totc_cuoi_cu'] == null ? '' : ds['totc_cuoi_cu']
          this.txtToTC_Cu = ds['totc_cu'] == null ? '' : ds['totc_cu']
        } else {
          this.txtDC_Cu = ''
          this.txtDC_Moi = ''
          this.txtDC_Cuoi_Cu = ''
          this.txtDC_Cuoi_Moi = ''
          this.txtDC_Dau_Cu = ''
          this.txtDC_Dau_Moi = ''
          this.txtTocDo = ''
          this.txtSoDT_Dau = ''
          this.txtSoDT_Cuoi = ''
          this.txtToTC_Dau_Moi = ''
          this.txtToTC_Dau_Cu = ''
          this.txtToTC_Cuoi_Moi = ''
          this.txtToTC_Cuoi_Cu = ''
          this.txtToTC_Moi = ''
          this.txtToTC_Cu = ''
        }
      } catch (err) {
        console.log('frmShow_DiaChi_LD_Load', err)
      } finally {
        this.Loading = false
        this.$refs.refDialogObjShowDiaChiLapDat.show()
      }
    },
    GetDmLDichVuVTs: async function () {
      var input = {}
      let data = this.GetData(await api.getDmLDichVuVTs(this.axios, input))
      this.DmLoaiDichVu = data
    },
    GetData: function (response) {
      if (response.data.error == 200 || response.data.error == '200') {
        return response.data.data
      } else {
        if (response.data.data) return response.data.data
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
