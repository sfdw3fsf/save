<style scoped src="./TraCuuNoTongHop.scss"></style>
<template src="./TraCuuNoTongHop.html"></template>

<script>
import Vue from 'vue'
import { mapActions, mapState } from 'vuex'
import moment from 'moment'
import NhatKyThuNo from './components/NhatKyThuNo.vue'
import LichSuChuyenPhieu from './components/LichSuChuyenPhieu.vue'
import PrintPreview from './components/PrintPreview.vue'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import TraCuuNoTongHopAPI from '../api/TraCuuNoTongHopAPI'
import CommonsAPI from '../api/Commons'
import GachNoAPI from '../api/GachNoAPI'
import TraCuuThongTinThueBao from '../GachNo/components/TraCuuThongTinThueBao'
import DanhSachThueBao from '../GachNo/components/DanhSachThueBao'
import EventBus from '@/utils/eventBus'
import ChonThanhToan from '../GachNo/components/ChonThanhToan'
import { previewPrint, showError } from '@/utils/util'

Vue.use(DatePickerPlugin)

export default {
  components: {
    appLichSuChuyenPhieu: LichSuChuyenPhieu,
    appNhatKyThuNo: NhatKyThuNo,
    appPrintPreview: PrintPreview,
    appTraCuuThongTinThueBao: TraCuuThongTinThueBao,
    appDanhSachThueBao: DanhSachThueBao,
    ChonThanhToan
  },
  name: 'TraCuuNoTongHop',
  async created() {
    EventBus.$on('getTTTB', this.getTTTBHandler)

    await Promise.all([
      this.getKyHoaDonHienTai(),
      this.getTimTheoMaTT(),      
      
    ])

    if (this.kyHoaDonHienTai !== undefined) {
      this.params.p_kycuoc_hientai = this.kyHoaDonHienTai.KYCUOC // moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate()
    } else {
      this.params.p_kycuoc_hientai = moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMMDD')
    }
    if (this.$route.query.ma_tb !== undefined && this.$route.query.ma_tb !== null && this.$route.query.ma_tb !== '') {
      this.params.p_somay_acc = this.$route.query.ma_tb
    }
    if (this.params.p_somay_acc !== null) {
      this.params.p_search_option = '1'
      this.onSearch()
    }
    await this.getDSLoaiTien()
    await this.getThamSoMD()
  },
  destroyed() {
    EventBus.$off('getTTTB', this.getTTTBHandler)
  },

  mounted() {
  },
  computed: {
    ...mapState('qltnCommon', [
      'kyHoaDonHienTai'
    ]),
    f_kycuoc() {
      if (this.params.p_kycuoc_hientai !== undefined && this.params.p_kycuoc_hientai !== null) return this.params.p_kycuoc_hientai.toString()
      else return null
    },
    f_search_option: {
      get() {
        return this.params.p_search_option
      },
      set(value) {
        this.params.p_search_option = value
      }
    },
    p_tungay: {
      get() {
        return this.params.p_tungay
      },
      set(value) {
        this.params.p_tungay = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        this.params.p_denngay = value
      }
    }
  },
  data() {
    return {
      footerSum: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<span>{{data.Sum}}</span>`,
            data() { return { data: {} } }
          })
        }
      },
      footerCount: function () {
        return {
          template: Vue.component('countTemplate', {
            template: `<span>{{data.Count}}</span>`,
            data() { return { data: {} } }
          })
        }
      },
      columnsNoChiTiet: [
        { fieldName: 'TENKHOANMUC', headerTextAlign: 'Center', headerText: 'Tên khoản mục', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'TONGNO', headerTextAlign: 'Center', headerText: 'Tổng nợ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150, cssClass: 'tien-tra' },
        { fieldName: 'NOGOC', headerTextAlign: 'Center', headerText: 'Nợ gốc', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'THUE', headerTextAlign: 'Center', headerText: 'Thuế', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'HOAHONG', headerTextAlign: 'Center', headerText: 'Hoa hồng', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'VIETTAT', headerTextAlign: 'Center', headerText: 'Loại tiền', allowFiltering: true, width: 100 }
      ],
      columnsNoTongHop: [
        { fieldName: 'THANGNO', headerText: 'Tháng nợ', textAlign: 'Right', allowFiltering: true, width: 150 },
        { fieldName: 'NO_DK', headerText: 'Đầu kỳ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'NO_PS', headerText: 'Phát sinh', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'TONGTRA', headerText: 'Phát sinh có', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150 },
        { fieldName: 'CONNO', headerText: 'Cuối kỳ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150, cssClass: 'tien-tra' }
      ],
      columnsPhieuTT: [
        { fieldName: 'PHIEU_ID', visible: false, isPrimaryKey: true },
        { fieldName: 'KYTRA', headerText: 'Kỳ trả', textAlign: 'Right', allowFiltering: true, width: 150 },
        { fieldName: 'NGAY_TT', headerText: 'Ngày trả', textAlign: 'Right', allowFiltering: true, width: 130 },
        { fieldName: 'TONGTRA', headerText: 'Tiền trả', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N0', width: 150, cssClass: 'tien-tra' },
        { fieldName: 'NGUOI_CN', headerText: 'User gạch', textAlign: 'Left', allowFiltering: true, width: 120 },
        { fieldName: 'MA_TN', headerText: 'Thu ngân', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'HINHTHUC', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SERI', headerText: 'Seri', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'SOSERI', headerText: 'Số Seri', textAlign: 'Right', allowFiltering: true },
        { fieldName: 'QUYEN', headerText: 'Quyển', textAlign: 'Right', allowFiltering: true },
        { fieldName: 'MANV_TC', headerText: 'Nhân viên TC', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'NGAYGACH', headerText: 'Ngày gạch', textAlign: 'Right', allowFiltering: true, width: 120 },
        { fieldName: 'NGANHANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true },
        { fieldName: 'NGAYNGANHANG', headerText: 'Ngày ngân hàng', textAlign: 'Right', allowFiltering: true, width: 130 }
      ],
      columnsNoChiTiet_USD: [
        {fieldName: 'TENKHOANMUC', headerTextAlign: 'Center', headerText: 'Tên khoản mục', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'TONGNO', headerTextAlign: 'Center', headerText: 'Tổng nợ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'NOGOC', headerTextAlign: 'Center', headerText: 'Nợ gốc', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'THUE', headerTextAlign: 'Center', headerText: 'Thuế', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'HOAHONG', headerTextAlign: 'Center', headerText: 'Hoa hồng', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'VIETTAT', headerTextAlign: 'Center', headerText: 'Loại tiền', allowFiltering: true, width: 100}
      ],
      columnsNoTongHop_USD: [
        {fieldName: 'THANGNO', headerText: 'Tháng nợ', textAlign: 'Right', allowFiltering: true, width: 150},
        {fieldName: 'NO_DK', headerText: 'Đầu kỳ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'NO_PS', headerText: 'Phát sinh', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'TONG_TRA', headerText: 'Phát sinh có', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'CONNO', headerText: 'Cuối kỳ', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150}
      ],
      columnsPhieuTT_USD: [
        {fieldName: 'PHIEU_ID', visible: false, isPrimaryKey: true},
        {fieldName: 'KYTRA', headerText: 'Kỳ trả', textAlign: 'Right', allowFiltering: true, width: 150},
        {fieldName: 'NGAY_TT', headerText: 'Ngày trả', textAlign: 'Right', allowFiltering: true, width: 130},
        {fieldName: 'TONGTRA', headerText: 'Tiền trả', textAlign: 'Right', allowFiltering: true, type: 'number', format: 'N2', width: 150},
        {fieldName: 'NGUOI_CN', headerText: 'User gạch', textAlign: 'Left', allowFiltering: true, width: 120},
        {fieldName: 'MA_TN', headerText: 'Thu ngân', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'HINHTHUC', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'SERI', headerText: 'Seri', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'SOSERI', headerText: 'Số Seri', textAlign: 'Right', allowFiltering: true},
        {fieldName: 'QUYEN', headerText: 'Quyển', textAlign: 'Right', allowFiltering: true},
        {fieldName: 'MANV_TC', headerText: 'Nhân viên TC', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'NGAYGACH', headerText: 'Ngày gạch', textAlign: 'Right', allowFiltering: true, width: 120},
        {fieldName: 'NGANHANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true},
        {fieldName: 'NGAYNGANHANG', headerText: 'Ngày ngân hàng', textAlign: 'Right', allowFiltering: true, width: 130}
      ],
      config: {
        p_f_hienthi_no_thuebao: false,
        p_f_hienthi_no_thang: false,
        p_f_hienthi_tt_thuebao: false,
        p_f_hienthi_tt_thang: false,
        p_f_nodauky: false,
        p_f_nops: false,
        p_f_hienthi_matb: true,
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsLoaiTien: { text: 'TENLOAI', value: 'LOAITIEN_ID' },
        p_hienthi_loai_tien:false,
      },
      params: {
        p_search_option: '0',
        p_thanhtoan_id: null,
        p_ma_tt: null,
        p_somay_acc: null,
        p_ten_tt: 'Tên thanh toán - Địa chỉ thanh toán - Địa chỉ báo cước - (Địa chỉ chứng từ)',
        p_diachi_tt: 'Mã thanh toán - Nhân viên thu cước - Nhân viên quản lý - Đơn vị NVQL - Đợt giao',
        p_ten_somay_acc: '',
        p_tungay: moment(new Date()).subtract(3, 'months').toDate(),
        p_denngay: new Date(),
        p_kycuoc_hientai: 0,
        p_kycuoc: null,
        p_khd_tungay: null,
        p_khd_denngay: null,
        p_lbl_nohientai: 'Thông tin nợ hiện tại',
        p_vkieuno: -1,
        p_vdieukien: -1,
        p_loaitb_id: 0,
        p_dichvuvt_id: 0
      },
      resultNoTongHop: [],
      noTongHopPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyNoTongHop: 1,

      resultNoHienTai: [],
      noHienTaiPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyNoHienTai: 1,

      resultNoChiTiet: [],
      noChiTietPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyNoChiTiet: 1,
      // totalItemsNoChiTiet: 0,
      resultTToanChiTiet: [],
      phieuCTPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyPhieuCT: 1,
      resultDanhSachPhieuTToan: [],
      phieuTTPageInfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0
      },
      keyPhieuTT: 1,
      ds_thanhtoan: [],
      lbl_thongtin: 'TT thanh toán là thông tin mới nhất',
      lbl_thongtin2: null
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    async getTimTheoMaTT() {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, { pMaThamSo: 'TIMTHEO_MATT' })
        const data = await res.data

        let timMaTT = false

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let ts = parseInt(data.data[0].TEN_TS)
          if (ts === 1) {
            timMaTT = true
          }
        }

        if (timMaTT) {
          this.config.p_f_hienthi_matb = false
        }
      } catch (error) {
        console.log(error)
      }
    },
    async onChangeHienThiNoHienTai(type) {
      if (type === 1) {
        this.config.p_f_hienthi_no_thuebao = !this.config.p_f_hienthi_no_thuebao

        /*
        if(this.config.p_f_hienthi_no_thuebao)
        {
          if(!this.config.p_f_hienthi_no_thang)
            this.params.p_vdieukien = 1
          else
            this.params.p_vdieukien = 3
        }

        if(this.config.p_f_hienthi_no_thang && !this.config.p_f_hienthi_no_thuebao)
          this.params.p_vdieukien = 2

        if(this.config.p_f_nodauky)
        {
          if(this.resultNoTongHop.length > 0)
          {
            //let rowSelected = this.$refs.grdNoTongHop.getSelectedRecords()
            //let str_khd = rowSelected[0].KYHOADON
            //string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
            //in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);

          }

          if(!this.config.p_f_nops)
            this.params.p_vkieuno = 0
          else
            this.params.p_vkieuno = 1
        }
        if(this.config.p_f_nops)
        {
          if(this.resultNoTongHop.length > 0)
          {
            //let rowSelected = this.$refs.grdNoTongHop.getSelectedRecords()
            //let str_khd = rowSelected[0].KYHOADON
            //string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
            //in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);

          }

          if(!this.config.p_f_nodauky)
            this.params.p_vkieuno = 5
          else
            this.params.p_vkieuno = 1
        }
        */
      } else this.config.p_f_hienthi_no_thang = !this.config.p_f_hienthi_no_thang

      this.tinhtoanDKNoHienTai()

      // this.tracuuNoChiTiet()
      this.loading(true)
      await this.tracuuNoHienTai()
      this.loading(false)
    },
    async onChangeHienThiLoaiNo(type) {
      // console.log(type)
      if (type === 1) this.config.p_f_nodauky = !this.config.p_f_nodauky
      else this.config.p_f_nops = !this.config.p_f_nops

      if (type === 1) {
        if (this.config.p_f_nodauky) {
          this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        } else {
          if (!this.config.p_f_nops) this.params.p_lbl_nohientai = 'Thông tin nợ hiện tại'
          else this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        }
      } else {
        if (this.config.p_f_nops) this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        else {
          if (!this.config.p_f_nodauky) this.params.p_lbl_nohientai = 'Thông tin nợ hiện tại'
          else this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        }
      }
      this.loading(true)
      this.tinhtoanDkNoTongHop()
      await this.tracuuNoHienTai()
      this.loading(false)
    },
    tinhtoanDkNoTongHop() {
      this.params.p_vdieukien = 0
      this.params.p_vkieuno = 3
      if (this.config.p_f_hienthi_no_thuebao) {
        if (!this.config.p_f_hienthi_no_thang) {
          this.params.p_vdieukien = 1
        } else this.params.p_vdieukien = 3
      }
      if (this.config.p_f_hienthi_no_thang && !this.config.p_f_hienthi_no_thuebao) {
        this.params.p_vdieukien = 2
      }

      // string in_kyhoadon = chuky + vkyhoadon;
      if (this.config.p_f_nodauky) {
        if (this.resultNoTongHop.length > 0) {
          // string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
          // in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);
        }
        if (!this.config.p_f_nops) {
          this.params.p_vkieuno = 0
        } else this.params.p_vkieuno = 1
      }
      if (this.config.p_f_nops) {
        if (this.resultNoTongHop.length > 0) {
          // string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
          // in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);
        }
        if (!this.config.p_f_nodauky) {
          this.params.p_vkieuno = 5
        } else this.params.p_vkieuno = 1
      }
    },
    async onChangeHienThiPhieuTT(type) {
      if (type === 1) this.config.p_f_hienthi_tt_thuebao = !this.config.p_f_hienthi_tt_thuebao
      else this.config.p_f_hienthi_tt_thang = !this.config.p_f_hienthi_tt_thang

      this.tinhtoanDKTra()

      if (this.resultDanhSachPhieuTToan.length <= 0) return

      let selectedRow = this.$refs.grdDSTToan.getSelectedRecords()

      if (selectedRow.length <= 0) return

      let postData = {
        dichVuVTId: null,
        maTB: null,
        phieuId: selectedRow[0].PHIEU_ID,
        kieuView: this.params.p_vdieukien,
        kyCuoc: selectedRow[0].KYHOADON
      }
      this.loading(true)
      await this.chitietPhieuThanhToan(postData)
      this.loading(false)
    },
    popupTimThongTinTT: function () {
      // this.$toast.error('Chức năng chưa được phát triển')
      this.$refs.refTraCuuTTTB.openDialog()
    },
    popupDSThueBao: function () {
      this.$refs.refDanhSachThueBao.openDialog()
    },
    validateData: function () {
      let errors = []
      if (this.params.p_search_option === '0') {
        if (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '') {
          errors.push('Vui lòng nhập vào Mã Thanh toán')
        } else this.params.p_ma_tt = this.params.p_ma_tt.trim()
      } else {
        if (this.params.p_somay_acc === null || this.params.p_somay_acc.trim() === '') {
          errors.push('Vui lòng nhập vào Số máy / account')
        } else this.params.p_somay_acc = this.params.p_somay_acc.trim()
      }
      if (this.params.p_tungay === null || this.params.p_denngay === null) {
        errors.push('Vui lòng nhập vào Từ ngày, đến ngày')
      } else if (!moment(this.params.p_tungay).isValid()) {
        errors.push('Giá trị từ ngày không hợp lệ')
      } else if (!moment(this.params.p_denngay).isValid()) {
        errors.push('Giá trị đến ngày không hợp lệ')
      } else if (moment(this.params.p_tungay).isAfter(this.params.p_denngay)) {
        errors.push('Giá trị từ ngày vượt quá đến ngày')
      }

      // this.params.p_kycuoc = moment(this.params.p_tungay).format('YYYYMM') + '01'

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      this.params.p_khd_tungay = moment(this.params.p_tungay).subtract(1, 'months').startOf('month').format('YYYYMMDD')
      this.params.p_khd_denngay = moment(this.params.p_denngay).subtract(1, 'months').startOf('month').format('YYYYMMDD')

      if (this.params.p_khd_denngay > this.params.p_kycuoc_hientai) {
        this.params.p_khd_denngay = this.params.p_kycuoc_hientai
      }
      return true
    },
    clearForm: function () {
      this.params.p_ma_tt = null
      this.params.p_somay_acc = null
      this.params.p_ten_ma_t = 'Tên thanh toán - Địa chỉ thanh toán - Địa chỉ báo cước - (Địa chỉ chứng từ)'
      this.params.p_diachi_tt = 'Mã thanh toán - Nhân viên thu cước - Nhân viên quản lý - Đơn vị NVQL - Đợt giao'
    },
    clearData: function () {
      this.resultNoTongHop = []
      this.noTongHopPageInfo.totalElement = 0
      this.noTongHopPageInfo.page = 0

      this.resultNoHienTai = []
      this.noHienTaiPageInfo.totalElement = 0
      this.noHienTaiPageInfo.page = 0

      this.resultNoChiTiet = []
      this.noChiTietPageInfo.totalElement = 0
      this.noChiTietPageInfo.page = 0

      this.resultDanhSachPhieuTToan = []
      this.phieuTTPageInfo.page = 0
      this.phieuTTPageInfo.totalElement = 0
      // this.totalItemsDanhSachPhieuTToan = 0
      this.resultTToanChiTiet = []
      this.phieuCTPageInfo.page = 0
      this.phieuCTPageInfo.totalElement = 0
    },
    async tracuuNoTongHop() {
      let postData = {
        maTT: this.params.p_ma_tt,
        maTB: this.params.p_search_option === '1' ? this.params.p_somay_acc : null,
        kieu: 1,
        donViId: null,
        dichVuVTId: null,
        thanhToanId: this.params.p_thanhtoan_id,
        tuNgay: moment(this.params.p_tungay).format('DD/MM/YYYY'),
        denNgay: moment(this.params.p_denngay).format('DD/MM/YYYY'),
        pLoaiTien:this.params.p_loaitien
      }

      // this.loading(true)

      this.resultNoTongHop = []
      this.noTongHopPageInfo.totalElement = 0
      this.noTongHopPageInfo.page = 0

      TraCuuNoTongHopAPI.getTraCuuNoTongHop(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              // console.log(response.data.data)
              if (response.data.data[0].CODE_ERROR !== undefined) {
                this.$toast.error(response.data.data[0].MESSAGE_CODE)
              } else {
                this.resultNoTongHop = response.data.data
                this.noTongHopPageInfo.totalElement = response.data.data.length
                this.keyNoTongHop++
              }
            }
          }
        )
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          // this.loading(false)
        })
    },
    async tracuuNoHienTai() {
      let postData = {
        maTT: this.params.p_ma_tt,
        maTB: this.params.p_search_option === '1' ? this.params.p_somay_acc : null,
        kieuNo: this.params.p_vkieuno,
        kieuView: this.params.p_vdieukien,
        dichVuVTId: null,
        kyCuoc: this.params.p_kycuoc_hientai,
        pLoaiTien:this.params.p_loaitien
      }

      if (this.config.p_f_nodauky) {
        this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        if (this.resultNoTongHop.length > 0) {
          const selected = this.$refs.grdNoTongHop.$refs.grid.getSelectedRecords()
          postData.kyCuoc = selected[0].KYHOADON
        }
      } else {
        if (!this.config.p_f_nops) {
          this.params.p_lbl_nohientai = 'Thông tin nợ hiện tại'
        } else {
          this.params.p_lbl_nohientai = 'Thông tin tổng nợ'
        }
      }

      if (this.config.p_f_nops) {
        if (this.resultNoTongHop.length > 0) {
          const selected = this.$refs.grdNoTongHop.$refs.grid.getSelectedRecords()
          postData.kyCuoc = selected[0].KYHOADON
        }
      }

      // this.loading(true)

      this.resultNoHienTai = []
      this.noHienTaiPageInfo.totalElement = 0
      this.noHienTaiPageInfo.page = 0

      try {
        const res = await TraCuuNoTongHopAPI.getTraCuuNoChiTiet(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          this.resultNoHienTai = data.data
          this.noHienTaiPageInfo.totalElement = data.data.length
          this.keyNoHientai++
        }
      } catch (error) {
        console.log(error)
      }
    },
    async tracuuNoChiTiet() {
      let postData = {
        maTT: this.params.p_ma_tt,
        maTB: this.params.p_search_option === '1' ? this.params.p_somay_acc : null,
        kieuNo: this.params.p_vkieuno,
        kieuView: this.params.p_vdieukien,
        dichVuVTId: null,
        kyCuoc: this.params.p_kycuoc_hientai,
        pLoaiTien:this.params.p_loaitien
      }

      // this.loading(true)

      this.resultNoChiTiet = []
      this.noChiTietPageInfo.totalElement = 0
      this.noChiTietPageInfo.page = 0

      TraCuuNoTongHopAPI.getTraCuuNoChiTiet(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.resultNoChiTiet = response.data.data
              this.noChiTietPageInfo = response.data.data.length
              this.keyNoChiTiet++
            }
          }
        )
        .catch(error => {
          console.log(error.data.message_detail)
        })
        .finally(() => {
          // this.loading(false)
        })
    },
    async tracuuDanhSachThanhToan() {
      let postData = {
        maTT: this.params.p_ma_tt,
        // maTB: this.params.p_search_option === '1' ? this.params.p_somay_acc : null,        
        maTB: null,
        kieu: 2,
        donViId: null,
        dichVuVTId: null,
        tuNgay: moment(this.params.p_tungay).format('DD/MM/YYYY'),
        denNgay: moment(this.params.p_denngay).format('DD/MM/YYYY'),
        pLoaiTien:this.params.p_loaitien
      }

      // this.loading(true)

      this.resultDanhSachPhieuTToan = []
      this.phieuTTPageInfo.totalElement = 0
      this.phieuTTPageInfo.page = 0

      TraCuuNoTongHopAPI.getTraCuuNoTongHop(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.resultDanhSachPhieuTToan = response.data.data
              this.phieuTTPageInfo.totalElement = response.data.data.length
              this.keyPhieuTT++
            }
          }
        )
        .catch(error => {
          console.log(error.data.message_detail)
        })
        .finally(() => {
          // this.loading(false)
        })
    },
    async getThongTinThueBao() {
      try {
        const postData = {
          pMaTB: this.params.p_somay_acc,
          pkyCuoc: this.params.p_kycuoc_hientai
        }
        const res = await TraCuuNoTongHopAPI.getThongTinThueBao(this.axios, postData)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          if (this.$auth.getMaTinh() === 'HCM') {
            let info = JSON.parse(data.data[0].KETQUA)
            // console.log(info)
            this.lbl_thongtin = info.TITLE_1
            this.lbl_thongtin2 = info.TITLE_2
          } else {
            this.lbl_thongtin = data.data[0].KETQUA
          }
        } else {
          this.lbl_thongtin = ' TT thanh toán là thông tin mới nhất'
          this.lbl_thongtin2 = ''
        }
        // console.log(data)
      } catch (err) {
        console.log(err)
      }
    },
    async chitietPhieuThanhToan(postData) {
      // this.loading(true)

      this.resultTToanChiTiet = []
      this.phieuCTPageInfo.totalElement = 0
      this.phieuCTPageInfo.page = 0

      try {
        const res = await TraCuuNoTongHopAPI.getTraCuuChiTietThanhToan(this.axios, postData)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          this.resultTToanChiTiet = data.data
          this.phieuCTPageInfo.totalElement = data.data.length
          this.keyPhieuCT++
        }
      } catch (error) {
        console.log(error)
      }
    },
    async getPayment_Info_ForPay(paycode) {
      this.loading(true)
      try {
        const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, { pKyCuoc: this.params.p_kycuoc_hientai, pPaymentCode: paycode,pLoaiTien:this.params.p_loaitien })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        // this.$toast.error(err.data.message_detail)
        return []
      } finally {
        this.loading(false)
      }
    },
    async getThongTinTTTheoDBHienTai(paycode) {
      try {
        const results = await GachNoAPI.getThongTinThanhToan(this.axios, { kyCuoc: this.params.p_kycuoc_hientai, maTT: paycode, maTB: paycode })
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (err) {
        showError(err)
        return []
      }
    },
    async getTTNguoiDungTheoQuanLyTuyen(idQuanLy) {
      try {
        const results = await TraCuuNoTongHopAPI.getTTNguoiDungTheoQuanLy(this.axios, idQuanLy)
        return results
      } catch (err) {
        console.log(err)
      }
    },
    onPopUpLSChuyenPhieu: function () {
      if (this.params.p_ma_tt === null) {
        this.$toast.error('Trường Mã thanh toán bắt buộc nhập')
        return false
      }
      this.$refs.refLichSuChuyenPhieu.openDialog()
    },
    onPopUpNhatKy: function () {
      if (this.params.p_ma_tt === null) {
        this.$toast.error('Trường Mã thanh toán bắt buộc nhập')
        return false
      }
      this.$refs.refNhatKyThuNo.openDialog()
    },
    doInPhieuThu: function () {
      if (this.params.p_ma_tt === null) {
        this.$toast.error('Trường Mã thanh toán bắt buộc nhập')
        return false
      }

      let postData = {
        maTT: this.params.p_ma_tt,
        tuNgay: moment(this.params.p_tungay).format('DD/MM/YYYY'),
        denNgay: moment(this.params.p_denngay).format('DD/MM/YYYY'),
        kieu: 1
      }
      this.loading(true)
      try {
        const baseUrl = process.env.API
        // let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
        this.axios.post(baseUrl + '/web-thuno/api/thu-no/in-hoa-don/in-thong-tin-no', postData, { responseType: 'blob' })
          .then((response) => {
            previewPrint(response.data)
          })
          .finally(() => {
            this.loading(false)
          })
      } catch (err) {
        if (err.data !== undefined) this.$toast.error(err.data.message_detail)
        else console.log(err)
      }
    },
    onPopupPreview: function () {
      if (this.params.p_ma_tt === null) {
        this.$toast.error('Trường Mã thanh toán bắt buộc nhập')
        return false
      }
      this.$refs.refPrintPreview.openDialog()
    },
    async getDanhSachNhanVienQLTheoDaiLy(idDaiLy) {
      try {
        const res = await CommonsAPI.getNhanVienQLTheoNguoiDung(this.axios, idDaiLy)
        return res
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    async getTTNhanVienAM(id) {
      try {
        const res = await TraCuuNoTongHopAPI.getTTNhanVienAM(this.axios, id)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 && data.data[0].AM !== null ? data.data[0].AM : ''
      } catch (error) {
        if (error.data !== undefined) this.$toast.error(error.data.message_detail)
        else console.log(error)
        return ''
      }
    },
    async onSearch() {
      this.clearData()
      this.params.p_ten_ma_t = 'Tên thanh toán - Địa chỉ thanh toán - Địa chỉ báo cước - (Địa chỉ chứng từ)'
      this.params.p_diachi_tt = 'Mã thanh toán - Nhân viên thu cước - Nhân viên quản lý - Đơn vị NVQL - Đợt giao'
      this.lbl_thongtin = ' TT thanh toán là thông tin mới nhất'
      this.lbl_thongtin2 = ''

      if (!this.validateData()) { return false }

      // this.loading(true)

      let paycode = (this.params.p_search_option === '0') ? this.params.p_ma_tt : this.params.p_somay_acc

      try {
        this.loading(true)
        let res = await this.getPayment_Info_ForPay(paycode)

        this.loading(false)
        if (res.length >= 0) {
          if (res.length > 1) {
            this.ds_thanhtoan = res
            this.$refs.refChonThanhToan.openDialog()
          } else if (res.length === 1) {
            await this.showThongTinTT(res, 1)
          } else {
            // this.$toast.error('Không tìm thấy thông tin thanh toán')
            const dbTT = await this.getThongTinTTTheoDBHienTai(paycode)
            if (dbTT.length > 1) {
              this.ds_thanhtoan = dbTT
              this.$refs.refChonThanhToan.openDialog()
            } else if (dbTT.length === 1) {
              await this.showThongTinTT(dbTT, 1)
            }
          }
        } else {
          // this.$toast.error('Thông tin tìm kiếm không tồn tại!')
        }
      } catch (err) {
        showError(err)
        // const dbTT = await this.getThongTinTTTheoDBHienTai(paycode)
        // console.log(dbTT)
      } finally {
        // this.loading(false)
      }
    },
    async showThongTinTT(data, kieu) {
      // console.log(data)
      // Kieu = 1: daset truyền vào chỉ có 1 row
      // Kieu = 2: daset truyền vào có từ 2 row trở lên
      if (kieu === 1) {
        let dcBC = (data[0].DIACHI_BC !== null) ? data[0].DIACHI_BC : '-'
        let dcCT = (data[0].DIACHI_CT !== null) ? data[0].DIACHI_CT : '-'
        this.params.p_ma_tt = data[0].MA_TT
        this.params.p_thanhtoan_id = data[0].THANHTOAN_ID

        if (this.params.p_search_option === '0') {
          this.params.p_somay_acc = data[0].MATB_DD
        }

        this.params.thanhtoan_id = data[0].THANHTOAN_ID
        this.params.p_ten_tt = data[0].TEN_TT + ' - ' + data[0].DIACHI_TT + ' - (' + dcBC + ' - ' + dcCT + ' )'

        // let tuyenthu_id = data[0].
        // let vdaily = parseInt(data[0].DAILY_ID) // Convert.ToInt32(ds.Tables[0].Rows[0]["daily_id"].ToString().Trim());
        // let nv_quanly = ' - NV Quản lý: '

        let nv = await this.getThongTinNhanVien(data[0].MA_TUYENTHU)
        let nvtc = ' - NVTC: '
        if (nv !== null) {
          nvtc += nv.MA_NV + '_' + nv.TEN_NV + '_SĐT:' + nv.SO_DT
        }

        let dataAM = await this.getTTNhanVienAM(this.params.p_ma_tt)
        let nvAM = 'NV Quản lý: ' + dataAM
        if (data[0].MA_TT_NEO === null)
          this.params.p_diachi_tt = data[0].MA_TT + ' - ĐTLH: ' + data[0].SO_DTLH + ' - Tuyến thu: ' + data[0].MA_TUYENTHU + nvtc + '- Đợt giao: ' + data[0].DOTGIAO_ID + ' - ' + nvAM
        else
          this.params.p_diachi_tt = data[0].MA_TT + '- VNP:' + data[0].MA_TT_NEO + ' - ĐTLH: ' + data[0].SO_DTLH + ' - Tuyến thu: ' + data[0].MA_TUYENTHU + nvtc + '- Đợt giao: ' + data[0].DOTGIAO_ID + ' - ' + nvAM
      } else {

      }
      this.params.p_vdieukien = 0
      if (this.params.p_f_hienthi_no_thuebao) {
        if (!this.params.p_f_hienthi_no_thang) { this.params.p_vdieukien = 1 } else { this.params.p_vdieukien = 3 }
      }

      if (this.params.p_f_hienthi_no_thang && !this.params.p_f_hienthi_no_thuebao) { this.params.p_vdieukien = 2 }

      this.params.p_vkieuno = 3

      if (this.params.p_f_nodauky) {
        if (!this.params.p_f_nops) {
          this.params.p_vkieuno = 0
        } else { this.params.p_vkieuno = 1 }
      }

      if (this.params.p_f_nops) {
        if (!this.params.p_f_nodauky) {
          this.params.p_vkieuno = 5
        } else { this.params.p_vkieuno = 1 }
      }

      this.loading(true)
      await Promise.all([
        this.tinhtoanDKNoHienTai(),
        this.tracuuNoTongHop(),
        this.tracuuDanhSachThanhToan(),
        // this.tracuuNoChiTiet()
        // this.tracuuNoHienTai(),
        this.getThongTinThueBao()
      ])

      this.loading(false)
    },

    tinhtoanDKNoHienTai() {
      this.params.p_vdieukien = 0
      if (this.config.p_f_hienthi_no_thuebao) {
        if (!this.config.p_f_hienthi_no_thang) {
          this.params.p_vdieukien = 1
        } else { this.params.p_vdieukien = 3 }
      }
      if (this.config.p_f_hienthi_no_thang && !this.config.p_f_hienthi_no_thuebao) {
        this.params.p_vdieukien = 2
      }
      this.params.p_vkieuno = 3
      if (this.config.p_f_nodauky) {
        if (this.resultNoTongHop.length > 0) {
          //  string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
          //            in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);
        }
        if (!this.config.p_f_nops) {
          this.params.p_vkieuno = 0
        } else { this.params.p_vkieuno = 1 }
      }
      if (this.config.p_f_nops) {
        if (this.resultNoTongHop.length > 0) {
          //  string str_khd = gridView_NoTH.GetRowCellValue(gridView_NoTH.FocusedRowHandle, "KYHOADON").ToString().Trim();
          //            in_kyhoadon = str_khd.Substring(6, 2) + str_khd.Substring(4, 2) + str_khd.Substring(0, 4);
        }
        if (!this.config.p_f_nodauky) {
          this.params.p_vkieuno = 5
        } else { this.params.p_vkieuno = 1 }
      }
    },

    tinhtoanDKTra() {
      this.params.p_vdieukien = 0

      if (this.config.p_f_hienthi_tt_thuebao) {
        if (!this.config.p_f_hienthi_tt_thang) { this.params.p_vdieukien = 1 } else { this.params.p_vdieukien = 3 }
      }

      if (this.config.p_f_hienthi_tt_thang && !this.config.p_f_hienthi_tt_thuebao) {
        this.params.p_vdieukien = 2
      }
    },
    async getThongTinNhanVien(maNV) {
      try {
        if (maNV === null) return null
        const postData = {
          'tenId': 'MA_TUYEN',
          'ma': maNV
        }
        const res = await CommonsAPI.getIDTheoMa(this.axios, postData)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data[0] : null
      } catch (error) {
        console.log(error)
        return null
      } finally { }
    },
    async phieuTTRowChanged(e) {
      // console.log(this.resultDanhSachPhieuTToan[e])
      if (e === null) {
        this.resultTToanChiTiet = []
        this.phieuCTPageInfo.totalElement = 0
        this.phieuCTPageInfo.page = 0
        return
      }

      this.tinhtoanDKTra()

      let postData = {
        dichVuVTId: null,
        maTB: null,
        phieuId: e.PHIEU_ID,
        kieuView: this.params.p_vdieukien,
        kyCuoc: e.KYHOADON
      }
      this.loading(true)
      await this.chitietPhieuThanhToan(postData)
      this.loading(false)
    },
    async noTongHopRowChanged(e) {
      await this.tracuuNoHienTai()
    },
    getTTTBHandler(e) {
      this.params.p_ma_tt = e.MA_TT
    },
    acceptChonMaTTHandler(e) {
      let a = []
      a.push(e)
      // console.log(a)
      this.showThongTinTT(a, 1)
    },
    acceptChonMaTBHandler(e) {
      console.log(e)
      this.params.p_search_option = '1'
      this.params.p_somay_acc = e.MA_TB
      // this.params.p_loaitb_id = e.LOAITB_ID
      this.params.p_dichvuvt_id = e.DICHVUVT_ID
    },
    onChangeLoaiTien(e) {      
      this.clearData();

    },
    getThamSoMD: async function()
    {
    CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'GACHNO_LOAITIEN'})
            .then(res => {
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0)
                {
                  this.config.p_hienthi_loai_tien=true
                }
            })
            .catch(error => {
              this.config.p_hienthi_loai_tien=false
            })
          },
    getDSLoaiTien () {
      try {
        GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: this.params.p_kycuoc_hientai})
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                this.params.p_loaitien_options = response.data.data
                this.params.p_loaitien = response.data.data[0].LOAITIEN_ID
              }
            }
          )
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
      } catch (error) {
        this.$toast.error(error)
      }
    },
  },
  watch: {

  }
}
</script>
