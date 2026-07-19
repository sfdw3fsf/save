<template src="./ModalChuanHoaThongTinThanhToan.html"></template>
<script>

import {minLength, maxLength, numeric, required} from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import Enum from '../../../ThayDoiThongTinHopDong/Enum'
import ModalChonDiaChi from '../../popupChonDiaChi'
import PopupTimKiemNhanVienThuCuoc from '../../PopupTimKiemNhanVienThuCuoc'
import Vue from 'vue'
import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import PopupDonViQL from '@/modules/EXTS/HOPDONG/ChuanHoaDLKhachHang/ChuanHoaThongTinThanhToan/Modal/popupDonViQL.vue'
Vue.use(DialogPlugin)

export default {
  name: 'ModalChuanHoaThongTinThanhToan',
  props: ['modalId', 'data'],
  components: {BssErrorMarker, BssRequiredMarker, ModalChonDiaChi, PopupTimKiemNhanVienThuCuoc, PopupDonViQL},
  data: function () {
    return {
      dsHinhThucThanhToan: null,
      dsNganHang: null,
      dsDonVi: null,
      ma_tt: null,
      ten_tt: null,
      donvi_id: null,
      stk: null,
      chu_tk: null,
      mst: null,
      ghichu: null,
      httt_id: null,
      nganhang_id: null,
      so_dt: null,
      email: null,
      manv_tc: null,
      ma_tuyen: null,
      tuyenthu_id: null,
      diachi_ct: null,
      matb_dd: null,
      diachi_tt: null,
      diachi_bc: null,

      kt_hdkh_id: false,
      kt_kylai: false,

      diachi_tt_moi: null,
      khutt_id: null,
      aptt_id: null,
      phott_id: null,
      dacdiemtt_id: null,
      phuongtt_id: null,
      quantt_id: null,
      tinhtt_id: null,
      sonhatt: null,

      diachi_ct_moi: null,
      tinhct_id: null,
      quanct_id: null,
      phuongct_id: null,
      dacdiemct_id: null,
      phoct_id: null,
      apct_id: null,
      khuct_id: null,
      sonha_ct: null,

      diachi_bc_moi: null,
      tinhbc_id: null,
      quanbc_id: null,
      phuongbc_id: null,
      dacdiembc_id: null,
      phobc_id: null,
      apbc_id: null,
      khubc_id: null,
      sonha_bc: null,

      DV_QHNS: null,
      MACHUONG: null,
      MN_KT: null,
      MA_NDKT: null,
      MN_NSNN: null,
      TIEN_DM: null,

      ten_tt_moi: null,
      matb_dd_moi: null,
      httt_id_moi: null,
      nganhang_id_moi: null,
      chu_tk_moi: null,
      donvi_id_moi: null,
      stk_moi: null,
      mst_moi: null,
      ghichu_moi: null,
      so_dt_moi: null,
      email_moi: null,
      manv_tc_moi: null,
      ma_tuyen_moi: null,

      DONVIQL_HTTT: 0,
      TUYENTHU_HTTT: 0,

      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',

      dataDiaChiTT: null,
      dataDiaChiBC: null,
      isDisableNganHang: true,
      dataSend: {},
      dataXacNhan: {}
    }
  },
  validations: {
    httt_id_moi: {required},
    diachi_tt_moi: {required},
    ten_tt_moi: {required},
    manv_tc_moi: {required}
  },

  created: async function () {
  },
  watch: {
    httt_id_moi: function () {
      this.isDisableNganHang = this.httt_id_moi != Enum.HinhThucThanhToan.UYNHIEM_THU
    }
  },
  computed: {

  },
  methods: {
    focusMyElement: function () {},
    showModal: async function () {
      this.$v.$reset()
      this.dataXacNhan = this.data
      await this.getHinhThucTT()
      await this.getNganHang()
      await this.getDonVi()
      this.ma_tt = this.data.ma_tt
      this.ten_tt = this.data.ten_tt
      this.ten_tt_moi = this.data.ten_tt
      this.thanhtoan_id = this.data.thanhtoan_id
      this.donvi_id = this.data.donvi_id
      this.donvi_id_moi = this.data.donvi_id
      this.stk = this.data.stk
      this.stk_moi = this.data.stk
      this.chu_tk = this.data.chu_tk
      this.chu_tk_moi = this.data.chu_tk
      this.mst = this.data.mst
      this.mst_moi = this.data.mst
      this.ghichu = this.data.ghichu
      this.ghichu_moi = this.data.ghichu
      this.httt_id = this.data.httt_id
      this.httt_id_moi = this.data.httt_id
      this.nganhang_id = this.data.nganhang_id
      this.nganhang_id_moi = this.data.nganhang_id
      this.so_dt = this.data.so_dt
      this.so_dt_moi = this.data.so_dt
      this.email = this.data.email
      this.email_moi = this.data.email
      this.manv_tc = this.data.manv_tc
      this.manv_tc_moi = this.data.manv_tc
      this.ma_tuyen = this.data.ma_tuyen
      this.ma_tuyen_moi = this.data.ma_tuyen
      this.diachi_ct = this.data.diachi_ct
      this.diachi_ct_moi = this.data.diachi_ct
      this.matb_dd = this.data.matb_dd
      this.matb_dd_moi = this.data.matb_dd

      // Địa chỉ thanh toán
      this.diachi_tt = this.data.diachi_tt
      this.diachi_tt_moi = this.data.diachi_tt
      this.khutt_id = this.data.khutt_id
      this.aptt_id = this.data.aptt_id
      this.phott_id = this.data.phott_id
      this.dacdiemtt_id = this.data.dacdiemtt_id
      this.phuongtt_id = this.data.phuongtt_id
      this.quantt_id = this.data.quantt_id
      this.tinhtt_id = this.data.tinhtt_id
      this.sonhatt = this.data.sonhatt
      // Địa chỉ chứng từ
      this.diachi_ct = this.data.diachi_ct
      this.tinhct_id = this.data.tinhct_id
      this.quanct_id = this.data.quanct_id
      this.phuongct_id = this.data.phuongct_id
      this.sonha_ct = this.data.sonha_ct
      // Địa chỉ bc
      this.diachi_bc = this.data.diachi_bc
      this.diachi_bc_moi = this.data.diachi_bc
      this.tinhbc_id = this.data.tinhbc_id
      this.quanbc_id = this.data.quanbc_id
      this.phuongbc_id = this.data.phuongbc_id
      this.dacdiembc_id = this.data.dacdiembc_id
      this.phobc_id = this.data.phobc_id
      this.apbc_id = this.data.apbc_id
      this.khubc_id = this.data.khubc_id
      this.sonha_bc = this.data.sonha_bc
      this.tuyenthu_id = this.data.tuyenthu_id

      this.kt_hdkh_id = this.data.kt_hdkh_id
      this.kt_kylai = this.data.kt_kylai

      this.DV_QHNS = this.data.DV_QHNS
      this.MACHUONG = this.data.MACHUONG
      this.MN_KT = this.data.MN_KT
      this.MA_NDKT = this.data.MA_NDKT
      this.MN_NSNN = this.data.MN_NSNN
      this.TIEN_DM = this.data.TIEN_DM
      await this.getDsThamSoMacDinh()
    },

    getDsThamSoMacDinh: async function () {
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_ds_thamso_macdinh')
      if (res.data.length > 0 && res.data.find(x => x.ma_ts == 'DONVIQL_HTTT') != null && res.data.find(x => x.ma_ts == 'DONVIQL_HTTT').ten_ts == 1) {
        this.DONVIQL_HTTT = 1
      }

      if (res.data.length > 0 && res.data.find(x => x.ma_ts == 'TUYENTHU_HTTT') != null && res.data.find(x => x.ma_ts == 'TUYENTHU_HTTT').ten_ts == 1) {
        this.DONVIQL_HTTT = 1
      }
    },

    getHinhThucTT: async function () {
      let data = {
        table: 'HINHTHUC_TT',
        bangChaId: null
      }
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
      this.dsHinhThucThanhToan = res.data
    },

    getNganHang: async function () {
      let data = {
        table: 'NGANHANG',
        bangChaId: null
      }
      let res = await this.$root.context.get('/web-hopdong/ThayDoiHopDong/lay_danhmuc', data)
      this.dsNganHang = res.data
    },

    getDonVi: async function () {
      let data = {
        loaiDVId: Enum.LOAI_DV.DONVIQL_TT
      }
      let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_donvi_theo_loaidv', data)
      this.dsDonVi = res.data
    },

    chapNhanClick: async function () {
      if (!await this.kiemTraDuLieu()) {
        return
      }
      this.dataXacNhan.ma_tt = this.ma_tt
      this.dataXacNhan.ten_tt = this.ten_tt_moi != null ? this.ten_tt_moi.trim() : this.ten_tt_moi
      this.dataXacNhan.thanhtoan_id = this.thanhtoan_id
      this.dataXacNhan.donvi_id = this.donvi_id_moi
      this.dataXacNhan.stk = this.stk_moi != null ? this.stk_moi.trim() : this.stk_moi
      this.dataXacNhan.chu_tk = this.chu_tk_moi != null ? this.chu_tk_moi.trim() : this.chu_tk_moi
      this.dataXacNhan.mst = this.mst_moi != null ? this.mst_moi.trim() : this.mst_moi
      this.dataXacNhan.ghichu = this.ghichu_moi != null ? this.ghichu_moi.trim() : this.ghichu_moi
      this.dataXacNhan.httt_id = this.httt_id_moi
      this.dataXacNhan.nganhang_id = this.nganhang_id_moi
      this.dataXacNhan.so_dt = this.so_dt_moi
      this.dataXacNhan.email = this.email_moi
      this.dataXacNhan.manv_tc = this.manv_tc_moi
      this.dataXacNhan.ma_tuyen = this.ma_tuyen_moi
      this.dataXacNhan.diachi_ct = this.diachi_ct_moi
      this.dataXacNhan.matb_dd = this.matb_dd_moi != null ? this.matb_dd_moi.trim() : this.matb_dd_moi
      this.dataXacNhan.dv_qhns = this.DV_QHNS
      this.dataXacNhan.machuong = this.MACHUONG
      this.dataXacNhan.mn_kt = this.MN_KT
      this.dataXacNhan.ma_ndkt = this.MA_NDKT
      this.dataXacNhan.ma_nsnn = this.MN_NSNN
      this.dataXacNhan.tien_dm = this.TIEN_DM
      this.dataXacNhan.diachi_tt = this.diachi_tt_moi
      this.dataXacNhan.diachi_bc = this.diachi_bc_moi
      let dataDiaChi = {
        khu_id: this.khutt_id,
        ap_id: this.aptt_id,
        pho_id: this.phott_id,
        dacdiem_id: this.dacdiemtt_id,
        phuong_id: this.phuongtt_id,
        quan_id: this.quantt_id,
        tinh_id: this.tinhtt_id,
        sonha: this.sonhatt,
        tinhbc_id: this.tinhbc_id,
        quanbc_id: this.quanbc_id,
        phuongbc_id: this.phuongbc_id,
        dacdiembc_id: this.dacdiembc_id,
        phobc_id: this.phobc_id,
        apbc_id: this.apbc_id,
        khubc_id: this.khubc_id,
        sonha_bc: this.sonha_bc,
        tinhct_id: null,
        quanct_id: null,
        phuongct_id: null
      }
      this.dataXacNhan.khu_id = this.khutt_id
      this.dataXacNhan.ap_id = this.aptt_id
      this.dataXacNhan.pho_id = Number(this.phott_id)
      this.dataXacNhan.dacdiem_id = this.dacdiemtt_id
      this.dataXacNhan.phuong_id = this.phuongtt_id
      this.dataXacNhan.quan_id = this.quantt_id
      this.dataXacNhan.tinh_id = this.tinhtt_id
      this.dataXacNhan.sonha = this.sonhatt
      //
      this.dataXacNhan.khutt_id = this.khutt_id
      this.dataXacNhan.aptt_id = this.aptt_id
      this.dataXacNhan.phott_id = Number(this.phott_id)
      this.dataXacNhan.dacdiemtt_id = this.dacdiemtt_id
      this.dataXacNhan.phuongtt_id = this.phuongtt_id
      this.dataXacNhan.quantt_id = this.quantt_id
      this.dataXacNhan.tinhtt_id = this.tinhtt_id
      this.dataXacNhan.sonhatt = this.sonhatt
      // Địa chỉ chứng từ
      this.dataXacNhan.tinhct_id = this.tinhct_id
      this.dataXacNhan.quanct_id = this.quanct_id
      this.dataXacNhan.phuongct_id = this.phuongct_id
      this.dataXacNhan.phoct_id = this.phoct_id
      this.dataXacNhan.sonha_ct = this.sonha_ct
      // Địa chỉ bc
      this.dataXacNhan.tinhbc_id = this.tinhbc_id
      this.dataXacNhan.quanbc_id = this.quanbc_id
      this.dataXacNhan.phuongbc_id = this.phuongbc_id
      this.dataXacNhan.dacdiembc_id = this.dacdiembc_id
      this.dataXacNhan.phobc_id = this.phobc_id
      this.dataXacNhan.apbc_id = this.apbc_id
      this.dataXacNhan.khubc_id = this.khubc_id
      this.dataXacNhan.sonha_bc = this.sonha_bc

      this.dataXacNhan.dl_diachi = JSON.stringify(dataDiaChi)
      this.$emit('xacnhan', this.dataXacNhan)
      this.$bvModal.hide('popupChuanHoaThanhToan')
    },

    kiemTraDuLieu: async function () {
      this.$v.ten_tt_moi.$touch()
      this.$v.manv_tc_moi.$touch()
      this.$v.diachi_tt_moi.$touch()

      let errorMessage = []
      if (this.ten_tt_moi == null || this.ten_tt_moi == '') {
        errorMessage.push('Bạn chưa nhập tên thanh toán')
      }

      if (this.diachi_tt_moi == null || this.diachi_tt_moi == '') {
        errorMessage.push('Bạn chưa chuẩn hóa Địa chỉ thanh toán')
      }

      if (this.diachi_ct_moi == null || this.diachi_ct_moi == '') {
        errorMessage.push('Bạn chưa chuẩn hóa Địa chỉ chứng từ')
      }

      if (this.manv_tc_moi == null || this.manv_tc_moi == '') {
        errorMessage.push('Bạn chưa nhập mã nhân viên thu cước')
      }

      if (this.kt_hdkh_id == false) {
        errorMessage.push('Bạn hãy chọn lại mã nhân viên thu cước để chuẩn hóa dữ liệu')
      }

      if (this.matb_dd_moi == null || this.matb_dd_moi == '') {
        errorMessage.push('Bạn chưa nhập mã thuê bao đại diện để chuẩn hóa dữ liệu')
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return false
      } else {
        return true
      }
    },

    diaChiThanhToanClick: async function () {
      this.dataSend = {}
      this.dataSend.tieuDe = 'Địa chỉ thanh toán'
      this.dataSend.tinh_id = this.tinhtt_id
      this.dataSend.quan_id = this.quantt_id
      this.dataSend.phuong_id = this.phuongtt_id
      this.dataSend.pho_id = this.phott_id
      this.dataSend.ap_id = this.aptt_id
      this.dataSend.khu_id = this.khutt_id
      this.dataSend.dacdiem_id = this.dacdiemtt_id
      this.dataSend.so_nha = this.sonhatt
      this.dataSend.diachi = this.diachi_tt_moi
      this.$bvModal.show('popupDiaChiTT')
    },
    diaChiChungTuClick: async function () {
      this.dataSend = {}
      this.dataSend.tieuDe = 'Địa chỉ chứng từ'
      this.dataSend.tinh_id = this.tinhtt_id
      this.dataSend.quan_id = this.quantt_id
      this.dataSend.phuong_id = this.phuongtt_id
      this.dataSend.pho_id = this.phott_id
      this.dataSend.ap_id = this.aptt_id
      this.dataSend.khu_id = this.khutt_id
      this.dataSend.dacdiem_id = this.dacdiemtt_id
      this.dataSend.so_nha = this.sonhatt
      this.dataSend.diachi = this.diachi_tt_moi
      this.$bvModal.show('popupDiaChiCT')
    },
    diaChiBaoCuocClick: async function () {
      this.dataSend = {}
      this.dataSend.tieuDe = 'Địa chỉ báo cước'
      this.dataSend.tinh_id = this.tinhbc_id
      this.dataSend.quan_id = this.quanbc_id
      this.dataSend.phuong_id = this.phuongbc_id
      this.dataSend.pho_id = this.phobc_id
      this.dataSend.ap_id = this.apbc_id
      this.dataSend.khu_id = this.khubc_id
      this.dataSend.dacdiem_id = this.dacdiembc_id
      this.dataSend.so_nha = this.sonha_bc
      this.dataSend.diachi = this.diachi_bc_moi
      this.$bvModal.show('popupDiaChiBC')
    },
    maNVTCClick: async function () {
      this.$refs.dlgTimKiemNVTC.openDialog()
    },
    getNVTCProcess (value) {
      this.manv_tc_moi = value.maNVTC
      this.tuyenthu_id = value.rowData.TUYENTHU_ID
      this.ma_tuyen_moi = value.rowData.MA_TUYENTHU
    },

    xacNhanDonViQL: async function (data) {
      this.donvi_id_moi = data.donvi_id// de tam
      if (this.dataDiaChiTT) {
        this.ganDiaChiTT()
      }
      if (this.dataDiaChiBC) {
        this.ganDiaChiBC()
      }
    },

    ganDiaChiTT: async function () {
      if (this.donvi_id_moi != this.donvi_id) {
        this.$toast.error('Bạn không được thay đổi đơn vị quản lý địa chỉ thanh toán')
        return
      }

      this.diachi_tt_moi = this.dataDiaChiTT.diachimoi
      this.tinhtt_id = this.dataDiaChiTT.tinh_id
      this.quantt_id = this.dataDiaChiTT.quan_id
      this.phuongtt_id = this.dataDiaChiTT.phuong_id
      this.phott_id = this.dataDiaChiTT.pho_id
      this.aptt_id = this.dataDiaChiTT.ap_id
      this.khutt_id = this.dataDiaChiTT.khu_id
      this.dacdiemtt_id = this.dataDiaChiTT.dacdiem_id
      this.sonhatt = this.dataDiaChiTT.so_nha
    },

    ganDiaChiBC: async function () {
      if (this.donvi_id_moi != this.donvi_id) {
        this.$toast.error('Bạn không được thay đổi đơn vị quản lý địa chỉ thanh toán')
        return
      }

      this.diachi_bc_moi = this.dataDiaChiBC.diachimoi
      this.tinhbc_id = this.dataDiaChiBC.tinh_id
      this.quanbc_id = this.dataDiaChiBC.quan_id
      this.phuongbc_id = this.dataDiaChiBC.phuong_id
      this.phobc_id = this.dataDiaChiBC.pho_id
      this.apbc_id = this.dataDiaChiBC.ap_id
      this.khubc_id = this.dataDiaChiBC.khu_id
      this.dacdiembc_id = this.dataDiaChiBC.dacdiem_id
      this.sonha_bc = this.dataDiaChiBC.so_nha
    },

    getDiaChiTT: async function (data) {
      this.dataDiaChiTT = data
      let dataInput = {
        phuongId: data.phuong_id,
        phoId: data.pho_id ? data.pho_id : 0,
        apId: data.ap_id ? data.ap_id : 0,
        khuId: data.khu_id ? data.khu_id : 0,
        kieu: 1,
        htttId: this.httt_id_moi,
        donviql_httt: this.DONVIQL_HTTT
      }
      let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_ds_donvi_quanly', dataInput)
      if (res.data.length <= 0) { return this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý') } else if (res.data.length == 1) {
        this.donvi_id_moi = res.data[0].donvi_id
        this.ganDiaChiTT()
      } else {
        this.dataSend = {}
        this.dataSend.ds = res.data
        this.dataSend.donvi_index_id = this.donvi_id_moi
        this.$bvModal.show('popupDonViQL')
      }

      let obj = {
        ap_id: this.aptt_id,
        pho_id: this.phott_id,
        khu_id: this.khutt_id,
        phuong_id: this.phuongtt_id,
        donvi_ql_id: this.donvi_id_moi
      }

      this.LoadNVThuCuoc(obj)
    },
    getDiaChiCT: async function (data) {
      this.diachi_ct_moi = data.diachimoi
      this.tinhct_id = data.tinh_id
      this.quanct_id = data.quan_id
      this.phuongct_id = data.phuong_id
      this.phoct_id = data.pho_id
      this.apct_id = data.ap_id
      this.khuct_id = data.khu_id
      this.dacdiemct_id = data.dacdiem_id
      this.sonha_ct = data.so_nha
    },
    getDiaChiBC: async function (data) {
      this.dataDiaChiBC = data
      let dataInput = {
        phuongId: data.phuong_id,
        phoId: data.pho_id ? data.pho_id : 0,
        apId: data.ap_id ? data.ap_id : 0,
        khuId: data.khu_id ? data.khu_id : 0,
        kieu: 1,
        htttId: this.httt_id_moi,
        donviql_httt: this.DONVIQL_HTTT
      }
      let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_ds_donvi_quanly', dataInput)
      if (res.data.length <= 0) {
        this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý')
        return
      } else if (res.data.length == 1) {
        this.donvi_id_moi = res.data[0].donvi_id
        this.ganDiaChiBC()
      } else {
        this.dataSend = {}
        this.dataSend.ds = res.data // de tam doi erp xong form doi lai ten bien sau
        this.$bvModal.show('popupDonViQL')
      }

        this.LoadNVThuCuoc(obj)
    },
    getDiaChiCT: async function(data){
        this.diachi_ct_moi = data.diachimoi
        this.tinhct_id = data.tinh_id
        this.quanct_id = data.quan_id
        this.phuongct_id = data.phuong_id
        this.phoct_id = data.pho_id
        this.apct_id = data.ap_id
        this.khuct_id = data.khu_id
        this.dacdiemct_id = data.dacdiem_id
        this.sonha_ct = data.so_nha
    },
    getDiaChiBC: async function(data){
        this.dataDiaChiBC = data
        let dataInput = {
            phuongId: data.phuong_id,
            phoId: data.pho_id ? data.pho_id : 0,
            apId: data.ap_id ? data.ap_id : 0,
            khuId: data.khu_id ? data.khu_id : 0,
            kieu: 1,
            htttId: this.httt_id_moi,
            donviql_httt: this.DONVIQL_HTTT
        }
        let res = await this.$root.context.get('/web-hopdong/ChuanHoaDuLieu/lay_ds_donvi_quanly', dataInput)
        if (res.data.length <= 0) {
            this.$toast.error('Địa danh này chưa được gán đơn vị quản lý địa chỉ thanh toán. Hãy liên hệ với Admin để xử lý')
            return
        } else if (res.data.length == 1) {
            this.donvi_id_moi = res.data[0].donvi_id
            this.ganDiaChiBC()
        } else {
            this.dataSend = {}
            this.dataSend.ds = res.data // de tam doi erp xong form doi lai ten bien sau
            this.$bvModal.show('popupDonViQL')
        }
      let obj = {
        ap_id: this.apbc_id,
        pho_id: this.phobc_id,
        khu_id: this.khubc_id,
        phuong_id: this.phuongbc_id,
        donvi_ql_id: this.donvi_id_moi
      }

      this.LoadNVThuCuoc(obj)
    },

    LoadNVThuCuoc: async function (data) {
      let dataInput = {
        'vap_id': data.ap_id,
        'vdacdiem_id': 0,
        'vdonvi_ql_id': data.donvi_ql_id ? data.donvi_ql_id : 0,
        'vkhu_id': data.khu_id,
        'vpho_id': data.pho_id,
        'vphuong_id': data.phuong_id
      }
      if (this.httt_id_moi != null && this.TUYENTHU_HTTT == 1) {
        let res = await this.$root.context.post('/web-hopdong/lapdatmoi/ht_nvtc_theodc_v2', dataInput)
        if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
          // TODO popup chon nhan vien thu cuoc frmTraCuuNVTC
          // Sau do lay du lieu.
          if (res.data.data.length > 0) {
            let obj = res.data.data[0] // BUG lay tam mac dinh nhan vien dau tien
            this.kt_hdkh_id = true
            this.manv_tc_moi = obj.manv_tc
            this.tuyenthu_id = obj.tuyenthu_id
            this.ma_tuyen_moi = obj.ma_tuyen
          }
        }
      } else {
        let res = await this.$root.context.post('/web-hopdong/lapdatmoi/ht_nvtc_theodc', dataInput)
        if (res && res.data && res.data.data && res.data.error_code && res.data.error_code === 'BSS-00000000') {
          // TODO popup chon nhan vien thu cuoc frmTraCuuNVTC
          // Sau do lay du lieu.
          if (res.data.data.length > 0) {
            let obj = res.data.data[0] // BUG lay tam mac dinh nhan vien dau tien
            this.kt_hdkh_id = true
            this.manv_tc_moi = obj.manv_tc
            this.tuyenthu_id = obj.tuyenthu_id
            this.ma_tuyen_moi = obj.ma_tuyen
          }
        }
      }
    }

  },
  mounted: function () {
  }
}
</script>
<style>
  a.disabled {
    pointer-events: none;
    cursor: default;
  }
</style>
