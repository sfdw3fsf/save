<!-- eslint-disable camelcase -->
/* eslint-disable eqeqeq */
<template src="./SearchIncomeTicket.html"> </template>

<style src="./SearchIncomeTicket.scss"></style>

<script>
import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'

import showDiaChiLapDat from '@/modules/search/subscriber/SearchIncomeTicket/components/ShowDiaChiLapDat/ShowDiaChiLapDat.vue'
import pop_diachild from '@/modules/search/subscriber/SearchInstallDetail/popup/DiaChiLD.vue'
import popupLayDuLieuBC from '@/modules/contract/complete_profile/popups/popupLayDuLieuBC/LayDuLieuBC.vue'
// import SearchConstructionNoConstruction from '@/modules/search/subscriber/SearchConstructionNoConstruction'
// import SearchInstallDetail from '@/modules/search/subscriber/SearchInstallDetail'
// import updateProcessIncident from '@/modules/incident/subscriber/UpdateProcessIncident/UpdateProcessIncident.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import { L10n, loadCldr, setCulture, setCurrencyCode } from '@syncfusion/ej2-base'
import { DataUtil } from '@syncfusion/ej2-data'
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import XLSX from 'xlsx'
import moment from 'moment'
import api from './Api'
import Vue from 'vue'
import { DichVuVienThong, TrangThaiHD, LoaiHopDong, HuongGiao } from '@/const/enums/index.js'

// const searchInstallDetail = () => import('@/modules/search/subscriber/SearchInstallDetail')
// const searchConstructionNoConstruction = () => import('@/modules/search/subscriber/SearchConstructionNoConstruction')
// const declareLandline = () => import('@/modules/contract/setup/DeclareLandline')
const defaultModule = () => import('@/modules/search/subscriber/SearchIncomeTicket/Default.vue')
// const frmHoanCongDMAdslHuyen = () => import('@/modules/contract/setup/InstallInternet/index.vue')
setCulture('vi-VN')
setCurrencyCode('VND')
L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị',
      Item: 'Bản ghi',
      Items: 'Bản ghi',
      FilterbarTitle: '',
      GroupDropArea: 'Kéo thả tên cột lên vùng này để nhóm dữ liệu'
    }
  }
})

export default {
  components: {
    breadcrumb,
    gridView,
    bssFlatPicker,
    showDiaChiLapDat,
    DatePicker,
    moment,
    XLSX,
    popupLayDuLieuBC,
    // SearchConstructionNoConstruction,
    // SearchInstallDetail,
    // updateProcessIncident,
    pop_diachild,
    ExportDataModal,
    defaultModule
  },
  name: 'SearchIncomeTicket',
  created() {
    this.IsLoaded = false
    this.Loading = true
    if (this.$route.name.toLowerCase() == 'searchincometicket'.toLowerCase()) {
      this.formTitle = 'Tra cứu phiếu giao đến'
    } else if (this.$route.name.toLowerCase() == 'InstallTicketStatistics'.toLowerCase()) {
      this.formTitle = 'Thống kê phiếu thi công'
    } else {
      this.formTitle = 'Tra cứu phiếu giao đến'
    }
    Promise.all([this.GetDMLoaiHD(), this.GetDmLDichVuVTs(), this.GetDmTTPhieus(), this.GetDmKieuKhoaMo(), this.GetDmChieuKhoaMay(), this.GetDmTrangThaiHD(), this.GetDsTTPhieus()])
      .then((responses) => {})
      .catch((err) => {
        console.log('Promise.all', err)
      })
      .finally(() => {
        this.Loading = false
      })
  },
  mounted() {
    this.fInput.TuNgay = moment().format('DD/MM/YYYY')
    this.fInput.DenNgay = moment().format('DD/MM/YYYY')
    this.fInput.UseLHD = this.$root.token.getUserName()
    this.$refs.refgridview_DsPhieu.grid.sortSettings = this.sortSettings
    Promise.all([this.GetDmKieuYeuCaus()])
      .then((responses) => {})
      .catch((err) => {
        console.log('Promise.all', err)
      })
      .finally(() => {
        console.log('Promise.all mounted')
      })
    this.IsLoaded = true
  },
  watch: {
    'fCheck.LoaiDvVTId': function(val) {
      if (val) {
      } else {
        this.fCheck.KieuYeuCauId = false
      }
    },
    Loading: function(val, oldval) {
      this.$root.showLoading(val)
    }
  },
  computed: {
    TongSoPhieu() {
      if (this.gridview_DsPhieu.data == null) return 0
      return this.gridview_DsPhieu.data.length
    },
    TongSoPhieuMoi() {
      if (this.gridview_DsPhieu.data == null) return 0
      return this.gridview_DsPhieu.data.filter(function(x) {
        return x.ttph_id === '1'
      }).length
    },
    TongSoPhieuThucHien() {
      if (this.gridview_DsPhieu.data == null) return 0
      return this.gridview_DsPhieu.data.filter(function(x) {
        return x.ttph_id === '2' || x.ttph_id === '4' || x.ttph_id === '5' || x.ttph_id === '6'
      }).length
    },
    TongSoPhieuTraLai() {
      if (this.gridview_DsPhieu.data == null) return 0
      return this.gridview_DsPhieu.data.filter(function(x) {
        return x.ttph_id === '3'
      }).length
    },
    userComponent() {
      return defaultModule
    }
  },
  data() {
    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      userComponentModule: defaultModule,
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed
      },
      popupComponentName: '',

      header: {
        title: 'Tra cứu',
        list: [
          { name: ' Phát triển thuê bao', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu giao đến',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      fConfig: {
        formatDate: 'DD/MM/YYYY'
      },
      dateFormat1: { type: 'date', format: 'yyyy-MM-dd' },
      dateParams: { params: { format: 'dd-MM-yyyy' } },
      Loading: false,
      target: '#app .main-wrapper', // .main-wrappertcabc
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'top', Y: 'top' },
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      formTitle: 'Tra cứu phiếu giao đến',
      fInput: {
        PhieuId: 0,
        LoaiHdId: 0,
        LoaiDvVTId: 0,
        KieuYeuCauId: 0,
        TtPhieuId: 0,
        TtId: 0,
        KieuKhoaMoId: 0,
        KieuKhoaMayId: 0,
        TtHdId: 0,
        TuNgay: '',
        DenNgay: '',
        MaThueBao: '',
        SoAo: '',
        TenThueBao: '',
        DiaChiLd: '',
        DiaChiLd_Dau: '',
        DiaChiLd_Cuoi: '',
        UseLHD: '',
        HenLap: []
      },
      fCheck: {
        LoaiHdId: false,
        LoaiDvVTId: false,
        KieuYeuCauId: false,
        TtPhieuId: true,
        TtId: false,
        KieuKhoaMoId: false,
        KieuKhoaMayId: false,
        TuNgay: false
      },
      CurrentRow: {},
      DmLoaiHd: [],
      DmLoaiDichVu: [],
      DmKieuYeuCau: [],
      DmTTPhieu: [],
      DsTTPhieu: [],
      DmKieuKhoaMo: [],
      DmChieuKhoaMay: [],
      DmTrangThaiHD: [],
      DsHenLap: [],
      modalTitle: '',
      HenLap: {
        headers: [
          { name: 'donvi', text: 'Đơn vị hẹn', value: '' },
          { name: 'nguoihen', text: 'Người hẹn', value: '' },
          { name: 'tungay', text: 'Từ ngày', value: '' },
          { name: 'denngay', text: 'Đến ngày', value: '' }
        ],
        headers2: [
          { fieldName: 'donvi', headerText: 'Đơn vị hẹn' },
          { fieldName: 'nguoihen', headerText: 'Người hẹn' },
          { fieldName: 'tungay', headerText: 'Từ ngày' },
          { fieldName: 'denngay', headerText: 'Đến ngày' }
        ],
        width: '200px',
        data: []
      },
      sortSettings: {
        columns: [
          { field: 'ngaygiao10', direction: 'Descending' }
          // { field: 'ngaygiaonumber', direction: 'Descending' }
        ]
      },
      gridview_DsPhieu: {
        headers: [
          {
            fieldName: 'ngaygiao10',
            headerText: 'Ngày giao',
            isGroupedColumn: true,
            allowSorting: false,
            captionTemplate: function() {
              return {
                template: Vue.component('CaptionTemplate', {
                  template: `<div>{{captionValue}} {{captionCount}}</div>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    },
                    captionValue: function(value) {
                      return moment(value.data.key, 'YYYYMMDD').format('DD/MM/YYYY')
                    },
                    captionCount: function(value) {
                      // console.log('captionCount', this.parent)
                      let moduleapp = this.parent
                      let data = moduleapp.gridview_DsPhieu.data
                      // console.log('captionCount-data', data)
                      let count = data.filter((x) => x.ngaygiao10 == value.data.key).length
                      return ` - ${count} Bản ghi`
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', value: '', width: 100 },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', value: '' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', value: '' },
          { fieldName: 'ma_gd', headerText: 'Mã GD', value: '' },
          { fieldName: 'ten_nv', headerText: 'Người CN', value: '' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', value: '' },
          { fieldName: 'huonggiao', headerText: 'Hướng giao', value: '' },
          { fieldName: 'ma_lt', headerText: 'Số ảo', value: '' },
          { fieldName: 'goi_kt', headerText: 'Gói KT', value: '' },
          { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', value: '' },
          { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', value: '' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', value: '' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', value: '' },
          { fieldName: 'so_dt', headerText: 'Số ĐT', value: '' },
          { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', value: '' },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', value: '' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', value: '' },
          { fieldName: 'ngaytra', headerText: 'Ngày trả', value: '' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', value: '' },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', value: '' },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HD', value: '' },
          { fieldName: 'giohen_tu', headerText: 'Hẹn từ', value: '' },
          { fieldName: 'giohen_den', headerText: 'Hẹn đến', value: '' },
          { fieldName: 'lydotra', headerText: 'Lý do trả', value: '' },
          { fieldName: 'ten_kv', headerText: 'Khu vực', value: '' },
          { fieldName: 'goicuoc', headerText: 'Gói cước', value: '' },
          { fieldName: 'lydo_ton', headerText: 'Lý do tồn', value: '' },
          { fieldName: 'ngaybao_ton', headerText: 'Ngày báo tồn', value: '' },
          { fieldName: 'ngay_xl', headerText: 'Ngày xử lý', value: '' },
          { fieldName: 'nguyennhan_tl', headerText: 'Nguyên nhân thanh lý', value: '' },
          { fieldName: 'hostld_moi', headerText: 'Host LĐ mới', value: '' },
          { fieldName: 'hostld_cu', headerText: 'Host LĐ cũ', value: '' },
          { fieldName: 'host_vl', headerText: 'Host VL', value: '' },
          { fieldName: 'gckh', headerText: 'Ghi chú KH', value: '' }
        ],
        headers2: [
          {
            fieldName: 'ngaygiao50',
            headerText: 'Ngày giao',
            isGroupedColumn: true,
            allowSorting: false,
            format: { type: 'date', format: 'dd/MM/yyyy' },
            captionTemplate: function() {
              return {
                template: Vue.component('ACaptionTemplate', {
                  template: `<div>{{dataFormat}} {{captionCount}}</div>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    },
                    dataFormat(value) {
                      return moment(value.data.key).format('DD/MM/YYYY')
                    },
                    captionCount: function(value) {
                      // console.log('captionCount', this.parent)
                      let count = value.data.count
                      return ` - ${count} Bản ghi`
                    }
                  },
                  methods: {}
                })
              }
            },
            template: function() {
              return {
                template: Vue.component('BCaptionTemplate', {
                  template: `<div>{{dataFormat}} {{captionCount}}</div>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    },
                    dataFormat(value) {
                      return moment(value.data.key).format('DD/MM/YYYY')
                    },
                    captionCount: function(value) {
                      // console.log('captionCount', this.parent)
                      let count = value.data.count
                      return ` - ${count} Bản ghi`
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', value: '', width: 100 },
          { fieldName: 'ten_kieuld', headerText: 'Kiểu YC', value: '' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', value: '' },
          { fieldName: 'ma_gd', headerText: 'Mã GD', value: '' },
          { fieldName: 'ten_nv', headerText: 'Người CN', value: '' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', value: '' },
          { fieldName: 'huonggiao', headerText: 'Hướng giao', value: '' },
          { fieldName: 'ma_lt', headerText: 'Số ảo', value: '' },
          { fieldName: 'goi_kt', headerText: 'Gói KT', value: '' },
          { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', value: '' },
          { fieldName: 'gio_conlai', headerText: 'Giờ còn lại', value: '' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', value: '' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', value: '' },
          { fieldName: 'so_dt', headerText: 'Số ĐT', value: '' },
          { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', value: '' },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', value: '' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả', value: '' },
          { fieldName: 'ngaytra', headerText: 'Ngày trả', value: '' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', value: '' },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', value: '' },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái HD', value: '' },
          { fieldName: 'giohen_tu', headerText: 'Hẹn từ', value: '' },
          { fieldName: 'giohen_den', headerText: 'Hẹn đến', value: '' },
          { fieldName: 'lydotra', headerText: 'Lý do trả', value: '' },
          { fieldName: 'ten_kv', headerText: 'Khu vực', value: '' },
          { fieldName: 'goicuoc', headerText: 'Gói cước', value: '' },
          { fieldName: 'lydo_ton', headerText: 'Lý do tồn', value: '' },
          { fieldName: 'ngaybao_ton', headerText: 'Ngày báo tồn', value: '' },
          { fieldName: 'ngay_xl', headerText: 'Ngày xử lý', value: '' },
          { fieldName: 'nguyennhan_tl', headerText: 'Nguyên nhân thanh lý', value: '' },
          { fieldName: 'hostld_moi', headerText: 'Host LĐ mới', value: '' },
          { fieldName: 'hostld_cu', headerText: 'Host LĐ cũ', value: '' },
          { fieldName: 'host_vl', headerText: 'Host VL', value: '' },
          { fieldName: 'gckh', headerText: 'Ghi chú KH', value: '' }
        ],
        data: [],
        data2: [],
        dataExcel: []
        // groupSettings: { columns: ['ngaygiao10'] },
      },
      diachi_ld: '',
      dichvu_vt: '',
      loai_hd: '',
      ma_gd: '',
      hdtb_id: 0,
      acc_moi: '',
      ma_tb: '',
      dichvu_id: 0,
      showFilter: true,
      allowPaging: true,
      IsXuatFileExcel2: true,
      IsExportExcel: false,
      IsLoaded: false,
      kieu: ''
    }
  },
  methods: {
    GetDMLoaiHD: async function() {
      if (this.DmLoaiHd != null && this.DmLoaiHd.length > 0) {
        return
      }
      var input = {}
      let data = this.GetData(await api.getDmLoaiHds(this.axios, input))
      this.DmLoaiHd = data.map((x) => ({ id: x.loaihd_id, text: x.ten_loaihd == null ? '' : x.ten_loaihd })) // this.GetData(await api.getDmLoaiHds(this.axios, input))
      if (this.DmLoaiHd != null && this.DmLoaiHd.length > 0) {
        this.fInput.LoaiHdId = this.DmLoaiHd[0].id
      }
    },
    GetDmLDichVuVTs: async function() {
      if (this.DmLoaiDichVu != null && this.DmLoaiDichVu.length > 0) {
        return
      }
      var input = {}
      let data = this.GetData(await api.getDmLDichVuVTs(this.axios, input))
      this.DmLoaiDichVu = data.map((x) => ({ id: x.dichvuvt_id, text: x.ten_dvvt == null ? '' : x.ten_dvvt })) // this.GetData(await api.getDmLDichVuVTs(this.axios, input))
      if (this.DmLoaiDichVu != null && this.DmLoaiDichVu.length > 0) {
        this.fInput.LoaiDvVTId = this.DmLoaiDichVu[0].id
      }
    },
    GetDmKieuYeuCaus: async function() {
      var input = {
        dichvuvt_id: this.fInput.LoaiDvVTId,
        loaihd_id: this.fInput.LoaiHdId
      }
      let data = this.GetData(await api.getDmKieuYeuCaus(this.axios, input))
      if (data == null || data.length == 0) this.DmKieuYeuCau = []
      this.DmKieuYeuCau = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld == null ? '' : x.ten_kieuld })) // this.GetData(await api.getDmKieuYeuCaus(this.axios, input))
      if (this.DmKieuYeuCau != null && this.DmKieuYeuCau.length > 0) {
        this.fInput.KieuYeuCauId = this.DmKieuYeuCau[0].id
      }
    },
    GetDmTTPhieus: async function() {
      var input = {}
      let data = this.GetData(await api.getDmTTPhieus(this.axios, input))
      this.DmTTPhieu = data.map((x) => ({ id: x.ttph_id, text: x.trangthai_ph == null ? '' : x.trangthai_ph })) // this.GetData(await api.getDmTTPhieus(this.axios, input))
      if (this.DmTTPhieu != null && this.DmTTPhieu.length > 0) {
        this.fInput.TtPhieuId = this.DmTTPhieu[0].id
      }
    },
    GetDsTTPhieus: async function() {
      var input = {}
      let data = this.GetData(await api.getDsTTPhieus(this.axios, input))
      this.DsTTPhieu = data.map((x) => ({ id: x.trangthai_id, text: x.trangthai == null ? '' : x.trangthai })) // this.GetData(await api.getDsTTPhieus(this.axios, input))
    },
    GetDmKieuKhoaMo: async function() {
      var input = {}
      let data = this.GetData(await api.getDmKieuKhoaMo(this.axios, input))
      this.DmKieuKhoaMo = data.map((x) => ({ id: x.kieukm_id, text: x.kieu_km == null ? '' : x.kieu_km })) // this.GetData(await api.getDmKieuKhoaMo(this.axios, input))
      if (this.DmKieuKhoaMo != null && this.DmKieuKhoaMo.length > 0) {
        this.fInput.KieuKhoaMoId = this.DmKieuKhoaMo[0].id
      }
    },
    GetDmChieuKhoaMay: async function() {
      var input = {}
      let data = this.GetData(await api.getDmChieuKhoaMay(this.axios, input))
      this.DmChieuKhoaMay = data.map((x) => ({ id: x.chieukm_id, text: x.chieu_km == null ? '' : x.chieu_km })) // this.GetData(await api.getDmChieuKhoaMay(this.axios, input))
      if (this.DmChieuKhoaMay != null && this.DmChieuKhoaMay.length > 0) {
        this.fInput.KieuKhoaMayId = this.DmChieuKhoaMay[0].id
      }
    },
    GetDmTrangThaiHD: async function() {
      var input = {}
      let data = this.GetData(await api.getDmTrangThaiHD(this.axios, input))
      this.DmTrangThaiHD = data.map((x) => ({ id: x.tthd_id, text: x.trangthai_hd == null ? '' : x.trangthai_hd })) // this.GetData(await api.getDmTrangThaiHD(this.axios, input))
      if (this.DmTrangThaiHD != null && this.DmTrangThaiHD.length > 0) {
        this.fInput.TtHdId = this.DmTrangThaiHD[0].id
      }
    },
    kiemtra_dvgt_khoamomay: async function(vhdtb_id) {
      var input = { vhdtb_id: vhdtb_id }
      let data = this.GetData(await api.kiemtra_dvgt_khoamomay(this.axios, input))
      if (data != null && data.length > 0) return true
      return false
    },
    GetDanhSachHenLap: async function(hdtb_id) {
      try {
        var input = {
          vhdtb_id: hdtb_id
        }
        this.HenLap.data = this.GetData(await api.getDanhSachHenLap(this.axios, input))
      } catch (err) {
        console.log('GetDanhSachHenLap:err', err)
      }
    },
    gridview_DsPhieu_SelectRow: async function(item) {
      if (item == null) return
      try {
        // console.log('gridview_DsPhieu_SelectRow item', item)
        this.CurrentRow = item
        this.fInput.MaThueBao = item.ma_tb
        this.fInput.SoAo = item.ma_lt
        this.fInput.TenThueBao = item.ten_tb
        this.fInput.DiaChiLd = item.diachi_ld
        this.fInput.PhieuId = item.phieu_id

        await this.GetDanhSachHenLap(item.hdtb_id)
        if (item.dichvuvt_id == DichVuVienThong.TSL) {
          // let hdtbTsl = this.GetData(await api.getDanhSachHdtbTSL(this.axios, { vhdtb_id: item.hdtb_id }))
          this.Loading = true
          let hdtbTsl = this.GetData(await api.lay_ds_hopdong_thuebao_tls(this.axios, { hdtbId: item.hdtb_id }))
          if (hdtbTsl[0].daucuoi_id == 1) {
            this.fInput.DiaChiLd_Dau = hdtbTsl[0].diachi
            this.fInput.DiaChiLd_Cuoi = hdtbTsl[1].diachi
          } else {
            this.fInput.DiaChiLd_Dau = hdtbTsl[1].diachi
            this.fInput.DiaChiLd_Cuoi = hdtbTsl[0].diachi
          }
        } else {
          this.fInput.DiaChiLd_Dau = ''
          this.fInput.DiaChiLd_Cuoi = ''
        }
      } catch (err) {
        console.log('gridview_DsPhieu_SelectRow', err)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnTimKiem_Click() {
      try {
        await this.HienThiDS()
      } catch (err) {
        console.log(err)
      } finally {
        this.Loading = false
      }
    },
    async tsbtnPhieuChuaTH_Click_Old() {
      try {
        this.$refs.generalDialog.show(true)
      } catch (ex) {
        console.log('tsbtnPhieuChuaTH_Click', ex)
      }
    },
    async tsbtnPhieuChuaTH_Click() {
      try {
        // this.loading(true)
        this.modalTitle = 'Tra cứu phiếu TC chưa thực hiện'
        this.userComponentModule = () => import('@/modules/search/subscriber/SearchConstructionNoConstruction')
        this.$refs.generalDialog.show(true)
      } catch (ex) {
        console.log('tsbtnPhieuChuaTH_Click', ex)
      } finally {
        // this.loading(false)
      }
    },
    async tsbtnCTThiCong_Click_Old() {
      try {
        this.$refs.generalDialog.show(true)
      } catch (err) {
        console.log('tsbtnCTThiCong_Click', err)
        alert(err)
      }
    },
    async tsbtnCTThiCong_Click() {
      try {
        // this.loading(true)
        this.modalTitle = 'Tra cứu chi tiết thi công'
        this.userComponentModule = () => import('@/modules/search/subscriber/SearchInstallDetail')
        this.$refs.generalDialog.show(true)
      } catch (err) {
        console.log('tsbtnCTThiCong_Click', err)
      } finally {
        // this.loading(false)
      }
    },
    async tsbtnTCTienTrinhBH_Click() {
      if (this.fInput.MaThueBao == '') {
        this.ShowError(`Bạn chưa chọn phiếu !`)
        return
      }
      try {
        this.$refs.refUpdateProcessIncident.data.txtMaTB = this.fInput.MaThueBao
      } catch (err) {
        console.log('tsbtnTCTienTrinhBH_Click', err)
      } finally {
        this.$refs.dialogObjTcTienTrinhXLBH.show()
      }
    },
    show_diachild2: function() {
      if (this.CurrentRow == null) return
      if (this.gridview_DsPhieu.data == null || this.gridview_DsPhieu.data.length == 0) {
        this.ShowError(`Bạn chưa chọn phiếu !`)
        return
      }
      if (this.CurrentRow.ma_gd == null || this.CurrentRow.ma_gd == '') {
        this.ShowError(`Bạn chưa chọn phiếu !`)
        return
      }
      this.ma_gd = this.CurrentRow.ma_gd
      this.loai_hd = this.CurrentRow.ten_loaihd
      this.hdtb_id = this.CurrentRow.hdtb_id
      this.dichvu_id = this.CurrentRow.dichvuvt_id
      this.ma_tb = this.CurrentRow.ma_tb
      try {
        this.$refs.dialogObjShowDiaChiLapDat.openDialog(this.dichvu_id, this.hdtb_id, this.ma_tb, this.ma_gd, this.loai_hd)
      } catch (err) {
        console.log('show_diachild2', err)
      } finally {
      }
    },
    to_date(val) {
      return 'abc'
    },
    show_diachild: function() {
      if (this.CurrentRow == null) return
      var data = {
        hdtb_id: this.CurrentRow.hdtb_id,
        dichvu_id: this.CurrentRow.dichvuvt_id
        // ma_gd: this.CurrentRow.ma_gd,
        // ma_tb: this.CurrentRow.ma_tb,
        // loai_hd: this.CurrentRow.ten_loaihd
      }
      this.ma_gd = this.CurrentRow.ma_gd
      this.loai_hd = this.CurrentRow.ten_loaihd
      this.hdtb_id = this.CurrentRow.hdtb_id
      this.dichvu_vt = this.CurrentRow.dichvuvt_id
      this.acc_moi = this.CurrentRow.ma_tb
      console.log(data)
      api.sp_lay_tt_show_diachi_ld(this.axios, data).then((response) => {
        console.log(response)
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.diachi_ld = response.data.data
        } else {
          this.diachi_ld = ''
        }
      })
      /*
            try
                  {
                      if (gridview_DsPhieu.RowCount <= 0)
                          return;

                      int index = gridview_DsPhieu.FocusedRowHandle);
                      if (index < 0)
                          return;
                      if (item.HDTB_ID != "")
                      {
                          frmShow_DiaChi_LD f = new frmShow_DiaChi_LD();
                          f.hdtb_id = Convert.ToInt64(item.HDTB_ID;
                          f.ma_gd = item.MA_GD;
                          f.ma_tb = item.MA_TB;
                          f.loai_hd = item.TEN_LOAIHD;
                          f.dichvuvt_id = item.dichvuvt_id;
                          f.ShowDialog();
                      }
                  }
                  catch (Exception ex)
                  {
                      Message_Box.ShowError("Có lỗi : " + ex);
                  }
            */
    },
    async txtMaTB_KeyPress() {
      await this.HienThiDS()
    },
    HienThiDS: async function() {
      let nhanvienId = this.$root.token.getNhanVienID()
      let tungay_default = moment()
        .subtract(30, 'days')
        .format('DD/MM/YYYY')
      var input = {
        chieu_km: this.fCheck.KieuKhoaMayId ? this.fInput.KieuKhoaMayId : 0,
        denngay: this.fCheck.TuNgay ? this.fInput.DenNgay : 0,
        dichvuvt_id: this.fCheck.LoaiDvVTId ? this.fInput.LoaiDvVTId : 0,
        kieu_km: this.fCheck.KieuKhoaMoId ? this.fInput.KieuKhoaMoId : 0, // Khóa mở ( ko phải khuyến mại)
        kieuld_id: this.fCheck.KieuYeuCauId ? this.fInput.KieuYeuCauId : 0,
        loaihd_id: this.fCheck.LoaiHdId ? this.fInput.LoaiHdId : 0,
        ma_tb: this.fInput.MaThueBao,
        nhanvien_id: nhanvienId,
        nguoi_cn: this.fCheck.UseLHD ? this.fInput.UseLHD : null,
        trangthai_id: this.fInput.TtId,
        tthd_id: this.fCheck.TtHdId ? this.fInput.TtHdId : 0,
        ttph_id: this.fCheck.TtPhieuId ? this.fInput.TtPhieuId : 0,
        tungay: this.fCheck.TuNgay ? this.fInput.TuNgay : 0
      }
      /*
            input = {
              chieu_km: 0,
              denngay: moment().format('DD/MM/YYYY'),
              dichvuvt_id: 0,
              kieu_km: 0,
              kieuld_id: 0,
              loaihd_id: 0,
              ma_tb: '',
              trangthai_id: 0,
              tthd_id: 0,
              ttph_id: 0,
              nhanvien_id: 452,
              tungay: '01/06/2020'
            }
            */
      this.Loading = true
      try {
        let data = this.GetData(await api.Search(this.axios, input))
        data.forEach(function(item) {
          if (item.ngaygiao != null && item.ngaygiao != '') {
            item.ngaygiao20 = moment(item.ngaygiao.substring(0, 10), 'DD/MM/YYYY').format('DD/MM/YYYY')
            item.ngaygiao30 = moment(item.ngaygiao.substring(0, 10), 'DD/MM/YYYY').format('YYYY-MM-DD')
            item.ngaygiao50 = moment(item.ngaygiao.substring(0, 10), 'DD/MM/YYYY')
            item.ngaygiao10 = parseInt(moment(item.ngaygiao.substring(0, 10), 'DD/MM/YYYY').format('YYYYMMDD'))
            item.ngaygiaonumber = parseInt(moment(item.ngaygiao, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss'))
          } else {
            item.ngaygiao10 = ''
            item.ngaygiaonumber = 0
            item.ngaygiao20 = moment().format('DD/MM/YYYY')
            item.ngaygiao30 = moment().format('YYYY-MM-DD')
            item.ngaygiao50 = moment()
          }
          return 0
        })
        data.sort(function(a, b) {
          let a1 = parseInt(moment(a.ngaygiao10, 'DD/MM/YYYY').format('YYYYMMDD'))
          let a2 = a.ngaygiaonumber
          let b1 = parseInt(moment(b.ngaygiao10, 'DD/MM/YYYY').format('YYYYMMDD'))
          let b2 = b.ngaygiaonumber
          return b2 - a2
        })

        this.gridview_DsPhieu.data = data.slice(0)
        if (!this.IsXuatFileExcel2) {
          this.gridview_DsPhieu.data2 = DataUtil.parse.parseJson(JSON.stringify(data.slice(0)))
          this.$refs.refgridview_DsPhieuExcel.grid.sortColumn('ngaygiao50', 'Descending', false)
        }
        this.$refs.refgridview_DsPhieu.grid.sortColumn('ngaygiao10', 'Descending', false)
        this.Loading = false
      } catch (ex) {
        console.log('HienThiDS_Error', ex)
        this.Loading = false
      }
    },
    grid_queryCellInfo(args) {
      if (args.column.field == 'ngaygiao10') {
        args.value = moment(args.data.ngaygiao10, 'YYYYMMDD').format('DD/MM/YYYY')
      }
    },
    grid_created(args) {
      this.Loading = false
    },
    grid_pageChanged() {
      this.$refs.refgridview_DsPhieu.grid.sortColumn('ngaygiao10', 'Descending', false)
    },
    grid_Load(e) {
      this.Loading = true
      // this.columns[0].format = { type: 'date', format: 'dd/MM/yyyy' }
      //this.getColumnByField('ngaygiao50').format = { format: 'dd/MM/yyyy', type: 'date', skeleton: 'dd/MM/yyyy' }
    },
    GetData: function(response) {
      if (response.data.error == 200 || response.data.error == '200') {
        return response.data.data
      } else {
        if (response.data.data) return response.data.data
      }
      return []
    },
    // ShowDiaChiLapDat: function () {
    //   console.log(this.CurrentRow)
    //   if (this.CurrentRow !== null && this.CurrentRow.ma_gd != null && this.CurrentRow.ma_gd !== undefined) {
    //     this.popupComponent = ShowDiaChiLapDat
    //     ShowDiaChiLapDat.data.isPopup = true
    //     ShowDiaChiLapDat.data.Phieu = this.CurrentRow
    //     ShowDiaChiLapDat.data.fInput.ma_gd = this.CurrentRow.ma_gd
    //     ShowDiaChiLapDat.data.fInput.hdtb_id = this.CurrentRow.hdtb_id
    //     ShowDiaChiLapDat.data.fInput.ma_tb = this.CurrentRow.ma_tb
    //     ShowDiaChiLapDat.data.fInput.loai_hd = this.CurrentRow.loai_hd
    //     ShowDiaChiLapDat.data.fInput.dichvuvt_id = this.CurrentRow.dichvuvt_id
    //     ShowDiaChiLapDat.data.DmLoaiDichVu = this.DmLoaiDichVu
    //     this.Popup('popupComponents')
    //   } else {
    //     alert('Bạn chưa chọn phiếu yêu cầu !, tham chiếu URD 2.8.004 chưa có')
    //   }
    // },
    exportFile: function() {
      if (this.gridview_DsPhieu.data != null && this.gridview_DsPhieu.data.length > 0) {
        this.$refs.dialogLayDuLieuBC.openDialog(this.gridview_DsPhieu.data)
      } else {
        this.$toast.error('Không có dữ liệu!')
      }
    },
    DiaChiLapDatClosed: function() {
      this.ClosePopup('popupComponents')
    },
    PhieuChuaTH: function() {
      alert(' Tham chiếu URD khác chưa có !')
    },
    PhieuChuaTC: function() {
      alert(' Tham chiếu URD khác chưa có !')
    },
    XuatFile: function() {
      try {
        if (this.gridview_DsPhieu.data != null && this.gridview_DsPhieu.data.length > 0) {
          // this.$refs.dialogLayDuLieuBC.openDialog(this.gridview_DsPhieu.data)
          if (this.gridview_DsPhieu.data.length > 0) {
            // remap data
            // let newData = []
            // let keys = Object.keys(this.gridview_DsPhieu.data[0])
            // let headers = this.gridview_DsPhieu.headers
            // for (const item of this.gridview_DsPhieu.data) {
            //   let newItem = {}
            //   for (const header of this.gridview_DsPhieu.headers) {
            //     newItem[header.headerText] = item[header.fieldName]
            //   }
            //   let keyNotMap = keys.filter((x) => headers.filter((h) => h.fieldName == x).length == 0)
            //   for (var key of keyNotMap) {
            //     newItem[key] = item[key]
            //   }
            //   newData.push(newItem)
            // }
            // this.gridview_DsPhieu.dataExcel = newData
            this.ConvertNameDataExcel()
            this.$refs.exportDataModal.showModal()
          } else {
            this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
          }
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {}
    },
    ConvertNameDataExcel() {
      if (this.gridview_DsPhieu.data.length > 0) {
        // remap data
        this.gridview_DsPhieu.dataExcel = []
        let newData = []
        let keys = Object.keys(this.gridview_DsPhieu.data[0])
        let headers = this.gridview_DsPhieu.headers
        for (const item of this.gridview_DsPhieu.data) {
          let newItem = {}
          for (const header of headers) {
            newItem[header.headerText] = item[header.fieldName]
          }
          /* Truong ko co trong danh sach luoi
                    let keyNotMap = keys.filter((x) => headers.filter((h) => h.fieldName == x).length == 0)
                    for (var key of keyNotMap) {
                      newItem[key] = item[key]
                    }
                    */
          newData.push(newItem)
        }
        this.gridview_DsPhieu.dataExcel = newData
      }
    },
    XuatFileExcel: function() {
      try {
        if (this.gridview_DsPhieu.data != null && this.gridview_DsPhieu.data.length > 0) {
          this.$refs.refgridview_DsPhieuExcel.grid.excelExport()
          this.Loading = false
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {
        this.Loading = false
      }
    },
    XuatFileExcel2: function() {
      try {
        this.IsExportExcel = false
        if (this.gridview_DsPhieu.data != null && this.gridview_DsPhieu.data.length > 0) {
          this.Loading = true
          this.gridview_DsPhieu.data2 = this.gridview_DsPhieu.data2 = DataUtil.parse.parseJson(JSON.stringify(this.gridview_DsPhieu.data.slice(0)))
          this.$refs.refgridview_DsPhieuExcel.grid.sortColumn('ngaygiao50', 'Descending', false)
          this.IsExportExcel = true
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {
        this.Loading = false
      }
    },
    refgridview_DsPhieuExcel_ActionComplete(args) {
      if (this.IsXuatFileExcel2 && this.IsExportExcel) {
        this.Loading = true
        this.$refs.refgridview_DsPhieuExcel.grid.excelExport()
        this.Loading = false
        this.IsExportExcel = false
      }
      this.Loading = false
    },
    ThiCongTienTrinh: function() {
      alert('Tham chiếu URD khác chưa có ! ')
    },
    handleComponentMounted(){
      console.log('handleComponentMounted')
    },
    DblClickTree: async function(i, item) {
      console.log('DblClickTree', item, 'tthd_id:', item.tthd_id, 'loaihd_id:', item.loaihd_id, 'dichvuvt_id:', item.dichvuvt_id, 'huonggiao_id:', item.huonggiao_id)
      try {
        if (item == null) return
        let index = 1
        let Tag_frm = '' // tthd+dvvt+loaihd
        this.popupComponent = () => import('./NoComponent')
        this.popupComponentName = ''
        this.popupComponentData = {}
        if (Number(item.tthd_id) != Number(TrangThaiHD.HOAN_THANH) && Number(item.tthd_id) != Number(TrangThaiHD.MOI) && Number(item.tthd_id) != Number(TrangThaiHD.THANH_TOAN) && Number(item.tthd_id) != Number(TrangThaiHD.THOAITRA)) {
          Tag_frm += item.tthd_id + '+' + item.loaihd_id + '+' + item.dichvuvt_id
          if (Number(item.loaihd_id) == Number(LoaiHopDong.DAT_MOI) || Number(item.loaihd_id) == Number(LoaiHopDong.DI_CHUYEN) || Number(item.loaihd_id) == Number(LoaiHopDong.CHUYENDOI_LH) || Number(item.loaihd_id) == Number(LoaiHopDong.KHOIPHUC_SD)) {
            console.log('pass:item.loaihd_id == LoaiHopDong.DAT_MOI || item.loaihd_id == LoaiHopDong.DI_CHUYEN || item.loaihd_id == LoaiHopDong.CHUYENDOI_LH || item.loaihd_id == LoaiHopDong.KHOIPHUC_SD')
            if (Number(item.huonggiao_id) != Number(HuongGiao.HOST_THUHOICONG) && Number(item.huonggiao_id) != Number(HuongGiao.TRAMVT_THUHOICONG)) {
              console.log('pass:(item.huonggiao_id != HuongGiao.HOST_THUHOICONG && item.huonggiao_id != HuongGiao.TRAMVT_THUHOICONG')
              if (Number(item.dichvuvt_id) == Number(DichVuVienThong.ADSL) || Number(item.dichvuvt_id) == Number(DichVuVienThong.MEGA_EYES)) {
                // this.popupComponent = () => import('./NoComponent')
                // this.popupComponentName = 'frmHoanCongDMAdsl_Huyen'
                /* frmHoanCongDMAdsl_Huyen obj = new frmHoanCongDMAdsl_Huyen();
                                obj.Tag = Tag_frm;
                                obj.matb_link = item.MA_TB;
                                obj._vloaihd_id = item.loaihd_id;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.ADSL || item.dichvuvt_id == DichVuVienThong.MEGA_EYES')
                try {
                  console.log('DblClickTree:frmHoanCongDMAdsl_Huyen:InstallInternet')
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    matb_link: item.ma_tb,
                    _Tag: Tag_frm,
                    _matb_link: item.ma_tb,
                    _vloaihd_id: Number(item.loaihd_id),
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/InstallInternet/index.vue')
                  this.modalTitle = 'Hoàn công Internet'
                } catch (err) {
                  console.log('frmHoanCongDMAdsl_Huyen-err', err)
                } finally {
                  this.$refs.generalDialog.show(true)
                  // await this.userComponentModule().then((x) => {})
                  // this.$nextTick(() => {
                  //   this.$refs.generalDialog.show(true)
                  // })
                }
              } else if (Number(item.dichvuvt_id) == Number(DichVuVienThong.CO_DINH)) {
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.CO_DINH')
                try {
                  console.log('DblClickTree:frmHoanCongCoDinh:DeclareLandline')
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    matb_link: item.ma_tb,
                    _Tag: Tag_frm,
                    _matb_link: item.ma_tb,
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/DeclareLandline/index.vue')
                  this.modalTitle = 'Hoàn công cố định'
                } catch (err) {
                  console.log('frmHoanCongCoDinh-err', err)
                } finally {
                  console.log('frmHoanCongCoDinh-show')
                  this.$refs.generalDialog.show(true)
                }
                /*  frmHoanCongCoDinh obj = new frmHoanCongCoDinh();
                                obj.Tag = Tag_frm;
                                obj.matb_link = item.MA_TB;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                // obj.txtMaTB_KeyPress(obj.txtMaTB, new KeyPressEventArgs(Convert.ToChar(Keys.Enter)));
              } else if (Number(item.dichvuvt_id) == Number(DichVuVienThong.GPHONE)) {
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.GPHONE')
                try {
                  var arr = ''
                  var tthd_id = 0
                  var dsloaihd_id = []
                  var dsdichvuvt_id = []
                  if (Tag_frm && Tag_frm.length > 0) {
                    arr = Tag_frm.split('+')
                    if (arr && arr.length >= 1) tthd_id = Number(arr[0])
                    if (arr && arr.length >= 2) dsloaihd_id = arr[1].split(';')
                    if (arr && arr.length >= 3) dsdichvuvt_id = arr[2].split(';')
                  }

                  console.log('DblClickTree:frmHoanCongGPhone:InstallGphone_KTVT')
                  this.popupComponentName = 'InstallGphone_KTVT'
                  this.popupComponentAttr = {
                    isPopup: true,
                    dulieu: {
                      tthd_id: tthd_id,
                      dsloaihd_id: dsloaihd_id,
                      dsdichvuvt_id: dsdichvuvt_id,
                      _vquytrinh_id: Number(item.quytrinh_id)
                    }
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/InstallGphone_KTVT/InstallGphone_KTVT.vue')
                  this.modalTitle = 'Hoàn công GPhone'

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmHoanCongGPhone~ err', err)
                }
                /*  frmHoanCongGPhone obj = new frmHoanCongGPhone();
                                obj.Tag = Tag_frm;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
              } else if (Number(item.dichvuvt_id) == Number(DichVuVienThong.IMS)) {
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.IMS')
                /*   frmHoanCongIMS obj = new frmHoanCongIMS();
                                obj.Tag = Tag_frm;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmHoanCongIMS:DeclareIMS')
                  this.popupComponentName = 'DeclareIMS'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    matb_link: item.ma_tb,
                    _Tag: Tag_frm,
                    _matb_link: item.ma_tb,
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.modalTitle = 'Hoàn công IMS'
                  this.userComponentModule = () => import('@/modules/contract/setup/DeclareIMS/index.js')

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmHoanCongIMS~ err', err)
                }
              } else if (Number(item.dichvuvt_id) == Number(DichVuVienThong.TSL)) {
                /* frmHoanCongTSL obj = new frmHoanCongTSL();
                                obj.Tag = Tag_frm;
                                obj.matb_link = item.MA_TB;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.TSL')
                try {
                  console.log('DblClickTree:frmHoanCongTSL:DeclareMPLS')
                  this.popupComponentName = 'DeclareMPLS'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    matb_link: item.ma_tb,
                    _Tag: Tag_frm,
                    _matb_link: item.ma_tb,
                    _vquytrinh_id: Number(item.quytrinh_id),
                    quytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/DeclareMPLS/index.js')
                  this.modalTitle = 'Hoàn công TSL'

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmHoanCongTSL~ err', err)
                }
              } else if (Number(item.dichvuvt_id) == Number(DichVuVienThong.MEGAWAN) || Number(item.dichvuvt_id) == Number(DichVuVienThong.METRONET)) {
                /*  frmHoanCongMegaWan obj = new frmHoanCongMegaWan();
                                obj.Tag = Tag_frm;
                                obj.matb_link = item.MA_TB;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                console.log('DblClickTree:item.dichvuvt_id == DichVuVienThong.MEGAWAN || item.dichvuvt_id == DichVuVienThong.METRONET')
                try {
                  console.log('DblClickTree:frmHoanCongMegaWan:DeclareMegawan')
                  this.popupComponentName = 'DeclareMegawan'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    matb_link: item.ma_tb,
                    _Tag: Tag_frm,
                    _matb_link: item.ma_tb,
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/DeclareMegawan/DeclareMegawan.vue')
                  this.modalTitle = 'HOÀN CÔNG MEGAWAN'

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmHoanCongMegaWan~ err', err)
                }
              }
            } else {
              Tag_frm = item.tthd_id + '+' + item.huonggiao_id
              /*  frmThuHoiCong obj = new frmThuHoiCong();
                            obj.Tag = Tag_frm;
                            //obj.dvvt_id = item.dichvuvt_id;
                            obj.MdiParent = this.ParentForm;
                            obj.Show(); */
              try {
                console.log('DblClickTree:frmThuHoiCong:RecallUnusedPort')
                this.popupComponentName = 'RecallUnusedPort'
                this.popupComponentAttr = {
                  isPopup: true,
                  Tag: Tag_frm,
                  tag: Tag_frm,
                  dvvt_id: Number(item.dichvuvt_id),
                  matb_link: item.ma_tb,
                  tagPopup: Tag_frm,
                  dvvtPopup: Number(item.dichvuvt_id),
                  matbPopup: item.ma_tb
                  // _vquytrinh_id: item.quytrinh_id
                }
                this.userComponentModule = () => import('@/modules/install/RecallUnusedPort/RecallUnusedPort.vue')
                this.modalTitle = 'Thu hồi cổng không sử dụng'

                this.$nextTick(() => {
                  this.$refs.generalDialog.show(true)
                })
              } catch (err) {
                console.log('frmThuHoiCong~ err', err)
              }
            }
          } else if (Number(item.loaihd_id) == Number(LoaiHopDong.CHAMDUT_SD)) {
            console.log('DblClickTree:tem.loaihd_id == LoaiHopDong.CHAMDUT_SD')
            /* frmHTHSChamDutSD obj = new frmHTHSChamDutSD();
                        obj.Tag = Tag_frm;
                        obj.matb_link = item.MA_TB;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            try {
              console.log('DblClickTree:frmHTHSChamDutSD:SubscriberLiquidationHost')
              this.popupComponentName = 'SubscriberLiquidationHost'
              this.popupComponentAttr = {
                isPopup: true,
                Tag: Tag_frm,
                tag: Tag_frm,
                matb_link: item.ma_tb,
                _Tag: Tag_frm,
                _vquytrinh_id: Number(item.quytrinh_id)
              }
              this.userComponentModule = () => import('@/modules/contract/setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue')
              this.modalTitle = 'HTHS - Thanh lý thuê số điện thoại'

              this.$nextTick(() => {
                this.$refs.generalDialog.show(true)
              })
            } catch (err) {
              console.log('frmHTHSChamDutSD~ err', err)
            }
          } else if (Number(item.loaihd_id) == Number(LoaiHopDong.THAY_DAY_MAY)) {
            console.log('DblClickTree:frmHoanCongThayDay: ')
            /*  frmHoanCongThayDay obj = new frmHoanCongThayDay();
                        obj.Tag = Tag_frm;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            try {
              console.log('DblClickTree:frmHoanCongThayDay:InstallReplaceRecallWifi')
              this.popupComponentName = 'InstallReplaceRecallWifi'
              this.popupComponentAttr = {
                isPopup: true,
                Tag: Tag_frm,
                tag: Tag_frm,
                matb_link: item.ma_tb,
                _Tag: Tag_frm,
                _vquytrinh_id: Number(item.quytrinh_id)
              }
              this.userComponentModule = () => import('@/modules/contract/setup/InstallReplaceRecallWifi/InstallReplaceRecallWifi.vue')
              this.modalTitle = 'Cài đặt Wifi - Thay thế - Thu hồi vật tư'

              this.$nextTick(() => {
                this.$refs.generalDialog.show(true)
              })
            } catch (err) {
              console.log('frmHoanCongThayDay~ err', err)
            }
          } else if (Number(item.loaihd_id) == LoaiHopDong.THAY_DOI_DV) {
            let _vhdtb_id = item.hdtb_id
            if (!(await this.kiemtra_dvgt_khoamomay(_vhdtb_id))) {
              // true => khóa mở máy
              /*  frmHTHSThayDoiDV obj = new frmHTHSThayDoiDV();
                            obj.Tag = Tag_frm;
                            obj._vquytrinh_id = item.QUYTRINH_ID;
                            obj.MdiParent = this.ParentForm;
                            obj.Show(); */
              try {
                console.log('DblClickTree:frmHTHSThayDoiDV:ChangeServiceOMCVIPCM')
                this.popupComponentName = 'ChangeServiceOMCVIPCM'
                this.popupComponentAttr = {
                  isPopup: true,
                  Tag: Tag_frm,
                  tag: Tag_frm,
                  matb_link: item.ma_tb,
                  _Tag: Tag_frm,
                  _vquytrinh_id: Number(item.quytrinh_id)
                }
                this.userComponentModule = () => import('@/modules/contract/setup/ChangeServiceOMCVIPCM/index.js')
                this.modalTitle = 'Hoàn thiện hồ sơ Thay đổi dịch vụ'
              } catch (err) {
                console.log('frmHTHSThayDoiDV~ err', err)
              } finally {
                this.$refs.generalDialog.show(true)
                
              }
            } else {
              if (Number(item.tthd_id) == TrangThaiHD.TTVT_XACMINH_NO) {
                Tag_frm = ''
                Tag_frm += item.tthd_id + '+' + item.huonggiao_id
                /* frmCapNhat_KQXL_NoCuoc obj = new frmCapNhat_KQXL_NoCuoc();
                                obj.Tag = Tag_frm;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmCapNhat_KQXL_NoCuoc:CapNhatKetQuaKhoaMayNoCuoc')
                  this.popupComponentName = 'frmCapNhat_KQXL_NoCuoc'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm
                    //_vquytrinh_id: item.quytrinh_id
                  }
                  this.userComponentModule = () => import('@/modules/QLTN/CapNhatKetQuaKhoaMayNoCuoc/index.js')

                  this.$nextTick(() => {
                    this.$route.query.tag = Tag_frm
                    this.$refs.dialogComponent.tag = Tag_frm
                    this.$refs.generalDialog.show(true)
                    this.modalTitle = this.$refs.dialogComponent && this.$refs.dialogComponent.header ? this.$refs.dialogComponent.header.title : ''
                  })
                } catch (err) {
                  console.log('frmCapNhat_KQXL_NoCuoc~ err', err)
                } finally {
                  this.$refs.generalDialog.show(true)
                }
              } else {
                /*  frmHTHSKhoaMoMay obj = new frmHTHSKhoaMoMay();
                                obj.Tag = Tag_frm;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmHTHSKhoaMoMay:TiepNhanThongTinKhoaMoNoCuoc')
                  this.popupComponentName = 'frmHTHSKhoaMoMay'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/QLTN/TiepNhanThongTinKhoaMoNoCuoc/index.js')

                  this.$nextTick(() => {
                    this.$route.params.tag = Tag_frm
                    this.$refs.generalDialog.show(true)
                    this.$refs.dialogComponent.pyc_Params.quyTrinh_Selected = item.quytrinh_id
                    this.modalTitle = this.$refs.dialogComponent && this.$refs.dialogComponent.header ? this.$refs.dialogComponent.header.title : ''
                  })
                } catch (err) {
                  console.log('frmHTHSKhoaMoMay~ err', err)
                }
              }
            }
          } else if (Number(item.loaihd_id) == LoaiHopDong.DOISO_TB) {
            /*  frmHTHSDoiSoThueBao obj = new frmHTHSDoiSoThueBao();
                        obj.Tag = Tag_frm;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            try {
              console.log('DblClickTree:frmHTHSDoiSoThueBao:CompleteProfileChangeAccount')
              this.popupComponentName = 'CompleteProfileChangeAccount'
              this.popupComponentAttr = {
                isPopup: true,
                p_Tag: Tag_frm,
                tag: Tag_frm,
                _vquytrinh_id: Number(item.quytrinh_id)
              }
              this.userComponentModule = () => import('@/modules/contract/profile/CompleteProfileChangeAccount/CompleteProfileChangeAccount.vue')
              this.modalTitle = 'HOÀN THIỆN HỒ SƠ ĐỔI SỐ / ACCOUNT'
            } catch (err) {
              console.log('frmHTHSDoiSoThueBao~ err', err)
            } finally {
              this.$refs.generalDialog.show(true)
            }
          } else if (Number(item.loaihd_id) == LoaiHopDong.THAY_DOI_TOCDO_ADSL) {
            /* frmHTHSThayDoiTocDo obj = new frmHTHSThayDoiTocDo();
                        obj.Tag = Tag_frm;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            // try {
            //   console.log('DblClickTree:frmHTHSThayDoiTocDo:DeclareServiceSpeedITSecure')
            //   this.popupComponentName = 'DeclareServiceSpeedITSecure'
            //   this.popupComponentAttr = {
            //     isPopup: true,
            //     Tag: Tag_frm,
            //     tag: Tag_frm,
            //     _vquytrinh_id: Number(item.quytrinh_id)
            //   }
            //   this.userComponentModule = () => import('@/modules/contract/setup/DeclareServiceSpeedITSecure/index.js')
            //   this.modalTitle = 'HTHS - THAY ĐỔI TỐC ĐỘ ADSL'
            // } catch (err) {
            //   console.log('frmHTHSThayDoiTocDo~ err', err)
            // } finally {
            //   this.$refs.generalDialog.show(true)
            // }

            const routeData = this.$router.resolve({name: 'DeclareServiceSpeedITSecure', query: {tag: Tag_frm, quytrinh:item.quytrinh_id}});
            window.open(routeData.href, '_blank');
          } else if (Number(item.loaihd_id) == LoaiHopDong.THAY_DOI_TOCDO_TSL) {
            /* frmThayDoiTocDo_TSL obj = new frmThayDoiTocDo_TSL();
                        obj.Tag = Tag_frm;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            try {
              console.log('DblClickTree:frmThayDoiTocDo_TSL:SetupChangeTSLSpeed')
              this.popupComponentName = 'SetupChangeTSLSpeed'
              this.popupComponentAttr = {
                isPopup: true,
                Tag: Tag_frm,
                tag: Tag_frm,
                _vquytrinh_id: Number(item.quytrinh_id)
              }
              this.userComponentModule = () => import('@/modules/contract/setup/SetupChangeTSLSpeed/index.vue')
              this.modalTitle = 'THAY ĐỔI TỐC ĐỘ TSL'
            } catch (err) {
              console.log('frmThayDoiTocDo_TSL~ err', err)
            } finally {
              this.$refs.generalDialog.show(true)
            }
          } else if (Number(item.loaihd_id) == LoaiHopDong.THAYDOI_THONGSO_MEGAWAN) {
            /* frmHTHSThayDoiThongSoWan obj = new frmHTHSThayDoiThongSoWan();
                        obj.Tag = Tag_frm;
                        obj._vquytrinh_id = item.QUYTRINH_ID;
                        obj.MdiParent = this.ParentForm;
                        obj.Show(); */
            try {
              console.log('DblClickTree:frmHTHSThayDoiThongSoWan:ChangeParameterMegawan')
              this.popupComponentName = 'ChangeParameterMegawan'
              this.popupComponentAttr = {
                isPopup: true,
                Tag: Tag_frm,
                tag: Tag_frm,
                _vquytrinh_id: Number(item.quytrinh_id)
              }
              this.userComponentModule = () => import('@/modules/contract/setup/ChangeParameterMegawan/index.vue')
              this.modalTitle = 'HTHS - THAY ĐỔI THÔNG SỐ MEGAWAN'
            } catch (err) {
              console.log('frmHTHSThayDoiThongSoWan~ err', err)
            } finally {
              this.$refs.generalDialog.show(true)
            }
          } else {
            // CuongNM 29/5/2020 thêm link sang form Hoàn thiện HS/Hoàn thiện hồ sơ thay đổi TS MY TV
            if (item.huonggiao_id && item.huonggiao_id.toString() == '788') {
              /* frmHTHSThayDoiThongSoMyTV obj = new frmHTHSThayDoiThongSoMyTV();
                            obj.Tag = "5+20";
                            obj.MdiParent = this.ParentForm;
                            obj.Show(); */
              // try {
              //   console.log('DblClickTree:frmHTHSThayDoiThongSoMyTV:CompleteProfileChangeTSMyTV:')
              //   this.popupComponentName = 'CompleteProfileChangeTSMyTV'
              //   this.popupComponentAttr = {
              //     isPopup: true,
              //     Tag: '5+20',
              //     tag: '5+20'
              //   }
              //   this.userComponentModule = () => import('@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/CompleteProfileChangeTSMyTV.vue')
              //   this.modalTitle = ' Hoàn thiện hồ sơ thay đổi TS MyTV'
              // } catch (err) {
              //   console.log('frmHTHSThayDoiThongSoMyTV~ err', err)
              // } finally {
              //   this.$refs.generalDialog.show(true)
              // }
              //Sửa theo yêu cầu chị Phương
              try { 
                this.popupComponentName = 'DividePointChangeMyTVConfig'
                this.popupComponentAttr = {
                  isPopup: true,
                  Tag: '4+788',
                  tag: '4+788'
                }
                this.userComponentModule = () => import('@/modules/install/DividePointChangeMyTVConfig/index.vue')
                this.modalTitle = 'THAY ĐỔI THÔNG SỐ MYTV'
              } catch (err) {
                console.log('frmHTHSThayDoiThongSoMyTV~ err', err)
              } finally {
                this.$refs.generalDialog.show(true)
              }

            }
          }
        } else {
          if (Number(item.loaihd_id) == LoaiHopDong.THAY_DOI_DV && item.huonggiao_id != HuongGiao.TRAMVT_THUHOICONG) {
            //CuongNM 29/5/2020 - Thêm link hướng giao 244 ==> Cập nhật KQTC/  Cài đặt Wifi/thay thế/ thu hồi VT
            if (item.huonggiao_id && item.huonggiao_id.toString() == '244') {
              Tag_frm += '4+13+1;4;8;9;10;11;12'
              /* frmHoanCongThayDay obj = new frmHoanCongThayDay();
                            obj.Tag = Tag_frm;
                            obj.MdiParent = this.ParentForm;
                            obj.Show(); */
              try {
                console.log('DblClickTree:frmHoanCongThayDay:InstallReplaceRecallWifi:')
                this.popupComponentName = 'InstallReplaceRecallWifi'
                this.popupComponentAttr = {
                  isPopup: true,
                  Tag: Tag_frm,
                  tag: Tag_frm,
                  _vquytrinh_id: Number(item.quytrinh_i)
                }
                this.userComponentModule = () => import('@/modules/contract/setup/InstallReplaceRecallWifi/InstallReplaceRecallWifi.vue')
                this.modalTitle = 'Cài đặt Wifi - Thay thế - Thu hồi vật tư'

                this.$nextTick(() => {
                  this.$refs.generalDialog.show(true)
                })
              } catch (err) {
                console.log('frmHoanCongThayDay~ err', err)
              }
            } else {
              Tag_frm += item.tthd_id + '+' + item.loaihd_id + '+' + item.dichvuvt_id
              let _vhdtb_id = item.HDTB_ID
              if (!(await this.kiemtra_dvgt_khoamomay(_vhdtb_id))) {
                // true => khóa mở máy
                /*  frmHTHSThayDoiDV obj = new frmHTHSThayDoiDV();
                                obj.Tag = Tag_frm;
                                obj._vquytrinh_id = item.QUYTRINH_ID;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmHTHSThayDoiDV:ChangeServiceOMCVIPCM:')
                  this.popupComponentName = 'ChangeServiceOMCVIPCM'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    _vquytrinh_id: Number(item.quytrinh_id)
                  }
                  this.userComponentModule = () => import('@/modules/contract/setup/ChangeServiceOMCVIPCM/index.js')
                  this.modalTitle = 'Hoàn thiện hồ sơ Thay đổi dịch vụ'
                } catch (err) {
                  console.log('frmHTHSThayDoiDV~ err', err)
                } finally {
                  this.$refs.generalDialog.show(true)
                }
              } else {
                if (Number(item.tthd_id) == TrangThaiHD.TTVT_XACMINH_NO) {
                  Tag_frm = ''
                  Tag_frm += item.tthd_id + '+' + item.huonggiao_id

                  /* frmCapNhat_KQXL_NoCuoc obj = new frmCapNhat_KQXL_NoCuoc();
                                    obj.Tag = Tag_frm;
                                    obj.MdiParent = this.ParentForm;
                                    obj.Show(); */
                  try {
                    console.log('DblClickTree:frmCapNhat_KQXL_NoCuoc:CapNhatKetQuaKhoaMayNoCuoc')
                    this.popupComponentName = 'frmCapNhat_KQXL_NoCuoc'
                    this.popupComponentAttr = {
                      isPopup: true,
                      Tag: Tag_frm,
                      tag: Tag_frm
                    }
                    this.userComponentModule = () => import('@/modules/QLTN/CapNhatKetQuaKhoaMayNoCuoc/index.js')

                    this.$nextTick(() => {
                      this.$route.query.tag = Tag_frm
                      this.$refs.dialogComponent.tag = Tag_frm
                      this.$refs.generalDialog.show(true)
                      this.modalTitle = this.$refs.dialogComponent && this.$refs.dialogComponent.header ? this.$refs.dialogComponent.header.title : ''
                    })
                  } catch (err) {
                    console.log('frmCapNhat_KQXL_NoCuoc~ err', err)
                  }
                } else {
                  /* frmHTHSKhoaMoMay obj = new frmHTHSKhoaMoMay();
                                    obj.Tag = Tag_frm;
                                    obj._vquytrinh_id = item.QUYTRINH_ID;
                                    obj.MdiParent = this.ParentForm;
                                    obj.Show(); */
                  try {
                    console.log('DblClickTree:frmHTHSKhoaMoMay:TiepNhanThongTinKhoaMoNoCuoc')
                    this.popupComponentName = 'frmHTHSKhoaMoMay'
                    this.popupComponentAttr = {
                      isPopup: true,
                      Tag: Tag_frm,
                      tag: Tag_frm,
                      _vquytrinh_id: Number(item.quytrinh_id)
                    }
                    this.userComponentModule = () => import('@/modules/QLTN/TiepNhanThongTinKhoaMoNoCuoc/index.js')

                    this.$nextTick(() => {
                      this.$route.params.tag = Tag_frm
                      this.$refs.generalDialog.show(true)
                      this.$refs.dialogComponent.pyc_Params.quyTrinh_Selected = item.quytrinh_id
                      this.modalTitle = this.$refs.dialogComponent && this.$refs.dialogComponent.header ? this.$refs.dialogComponent.header.title : ''
                    })
                  } catch (err) {
                    console.log('frmHTHSKhoaMoMay~ err', err)
                  }
                }
              }
            }
          } else {
            Tag_frm += item.tthd_id + '+' + item.loaihd_id + '+' + item.dichvuvt_id
            if (item.huonggiao_id != HuongGiao.HOST_THUHOICONG && item.huonggiao_id != HuongGiao.TRAMVT_THUHOICONG) {
              // #region CuongNM - 29/5/2020 - thêm link đến form Cấp vật tư cho thuê bao
              if (item.huonggiao_id == HuongGiao.DUYET_CAP) {
                /*  frmCapVatTu_TB obj = new frmCapVatTu_TB();
                                obj.Tag = 1;
                                obj.ben_ngoai = 2;
                                obj.tb_benngoai = txtMaTB.Text;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmCapVatTu_TB:CapVatTuThueBao')
                  this.popupComponentName = 'frmCapVatTu_TB'
                  let params = {
                    kieugoi_id: 1
                  }
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: '1',
                    tag: '1',
                    ben_ngoai: 1,
                    tb_benngoai: item.ma_tb,
                    soMaySelected: item.ma_tb,
                    params: params
                    // _vquytrinh_id: item.quytrinh_id
                  }
                  this.userComponentModule = () => import('@/modules/QLVT/CapVatTuThueBao/CapVatTuThueBao.vue')
                  this.modalTitle = 'Cấp vật tư cho thuê bao (TT)'

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmCapVatTu_TB~ err', err)
                }
              }
              // #endregion
            } else {
              if (item.huonggiao_id == HuongGiao.HOST_THUHOICONG) {
                Tag_frm = TrangThaiHD.KHAIBAO_TONGDAI + '+' + item.huonggiao_id

                /* frmThuHoiCong obj = new frmThuHoiCong();
                                obj.Tag = Tag_frm;
                                obj.dvvt_id = item.dichvuvt_id;
                                obj.MdiParent = this.ParentForm;
                                obj.Show(); */
                try {
                  console.log('DblClickTree:frmThuHoiCong:RecallUnusedPort')
                  this.popupComponentName = 'RecallUnusedPort'
                  this.popupComponentAttr = {
                    isPopup: true,
                    Tag: Tag_frm,
                    tag: Tag_frm,
                    dvvt_id: Number(item.dichvuvt_id),
                    matb_link: item.ma_tb,
                    tagPopup: Tag_frm,
                    dvvtPopup: Number(item.dichvuvt_id),
                    matbPopup: item.ma_tb
                  }
                  this.userComponentModule = () => import('@/modules/install/RecallUnusedPort/RecallUnusedPort.vue')
                  this.modalTitle = 'Thu hồi cổng không sử dụng'

                  this.$nextTick(() => {
                    this.$refs.generalDialog.show(true)
                  })
                } catch (err) {
                  console.log('frmThuHoiCong~ err', err)
                }
              } else {
                //CuongNM - 29/5/2020 - them QLVT_BDH
                // let tinh = ['QLVT_HPG', 'QLVT_TVH', 'QLVT_TGG', 'QLVT_BLU', 'QLVT_BDH']
                let tinh = ['HPG', 'TVH', 'TGG', 'BLU', 'BDH']
                let db12 = await this.$root.token.getMaTinh()
                if (tinh.filter((x) => x == db12.toUpperCase()).length > 0) {
                  let kieu = 1
                  if (item.huonggiao_id == HuongGiao.DUYET_TH && item.TTPH_ID && item.TTPH_ID.toString() == '1') kieu = 2
                  if (item.huonggiao_id == HuongGiao.TRAMVT_THUHOICONG && item.TTPH_ID && item.TTPH_ID.toString() == '1') kieu = 1
                  /*   var f = new frmThuHoiVatTu_TB();
                                    f.MdiParent = this.ParentForm;
                                    f.matb = txtMaTB.Text;
                                    f.kieu_goi = kieu;
                                    f.Show(); */
                  try {
                    console.log('DblClickTree:frmThuHoiVatTu_TB:ThuHoiVatTuThueBao')
                    this.popupComponentName = 'ThuHoiVatTuThueBao'
                    this.popupComponentAttr = {
                      isPopup: true,
                      matb: item.ma_tb,
                      kieu_goi: Number(kieu)
                      // _vquytrinh_id: item.quytrinh_id
                    }
                    this.userComponentModule = () => import('@/modules/QLVT/ThuHoiVatTuThueBao/index.js')
                    this.modalTitle = 'Thu hồi vật tư thuê bao'

                    this.$nextTick(() => {
                      this.$refs.generalDialog.kieu_goi = Number(kieu)
                      this.$refs.generalDialog.show(true)
                      this.$refs.generalDialog.HIENTHI_DANHSACH_THUEBAO(item.ma_tb)
                    })
                  } catch (err) {
                    console.log('frmThuHoiVatTu_TB~ err', err)
                  }
                } else {
                  Tag_frm = TrangThaiHD.DANG_THI_CONG + '+' + item.huonggiao_id
                  /* frmThuHoiCong obj = new frmThuHoiCong();
                                    obj.Tag = Tag_frm;
                                    obj.matb_link = txtMaTB.Text;
                                    obj._loaihd = dsIn.Tables[0].Rows[index]["LOAIHD_ID"]);
                                    //NHAPT 08082017
                                    obj.dvvt_id = item.dichvuvt_id;
                                    obj.MdiParent = this.ParentForm;
                                    obj.Show(); */
                  try {
                    console.log('DblClickTree:frmThuHoiCong:RecallUnusedPort')
                    this.popupComponentName = 'RecallUnusedPort'
                    this.popupComponentAttr = {
                      isPopup: true,
                      Tag: Tag_frm,
                      tag: Tag_frm,
                      dvvt_id: Number(item.dichvuvt_id),
                      matb_link: item.ma_tb,
                      tagPopup: Tag_frm,
                      dvvtPopup: Number(item.dichvuvt_id),
                      matbPopup: item.ma_tb
                    }
                    this.userComponentModule = () => import('@/modules/install/RecallUnusedPort/RecallUnusedPort.vue')
                    this.modalTitle = 'Thu hồi cổng không sử dụng'

                    this.$nextTick(() => {
                      this.$refs.generalDialog.show(true)
                    })
                  } catch (err) {
                    console.log('frmThuHoiCong~ err', err)
                  }
                }
              }
            }
          }
        }
        console.log(this.popupComponentAttr)
        // this.popupComponent = () => import('./Popups/popupGanKhuVucQuanLyThueBao')
        // if (this.popupComponentName != '' && this.popupComponentName.length > 0) {
        // this.Popup('popupComponents')
        // }
      } catch (err) {
        console.log(err)
        // Message_Box.ShowError("" + ex);
      } finally {
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponents')
        return
      }
      try {
      } catch (err) {
        console.log(err)
      } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    ShowAlert: function(msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
