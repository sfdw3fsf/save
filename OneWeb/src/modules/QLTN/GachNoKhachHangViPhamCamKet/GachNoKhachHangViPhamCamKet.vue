<template src="./GachNoKhachHangViPhamCamKet.html"></template>

<script>
import Vue from 'vue'
import { maxLength, numeric, required } from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import KyCuoc from '@/components/KyCuoc'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import GachNoKHViPhamCamKetAPI from '../api/GachNoKHViPhamCamKet'
import { DialogUtility } from '@syncfusion/ej2-popups'
import { Query } from '@syncfusion/ej2-data'
// import TraCuuNoTongHopAPI from '../api/TraCuuNoTongHopAPI'
// import DanhSachPhieuTraHuy from './components/DanhSachPhieuTraHuy.vue'
import moment from 'moment'
import EventBus from '@/utils/eventBus'

import { TooltipPlugin } from '@syncfusion/ej2-vue-popups'
Vue.use(TooltipPlugin)

Vue.use(DialogUtility)
let DialogObj

const DIGITS = ['không', 'một', 'hai', 'ba', 'bốn', 'năm', 'sáu', 'bảy', 'tám', 'chín']
const UNITS = ['đồng', 'nghìn', 'triệu', 'tỷ']

export default{
  components: {
    BssRequiredMarker,
    appKyCuoc: KyCuoc
    // appDanhSachPhieuTraHuy: DanhSachPhieuTraHuy
  },
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    this.getDSThuNgan()
    this.getDSLoaiHD()

    this.config.f_hoadon_enabled = false
    this.config.f_hoadon_visible = true

    let f = await this.getThamSo('XUAT_HDDT_GN_VPCK')
    if (f) this.config.f_hoadon_enabled = true

    f = await this.getThamSo('HDDT_STATUSPAYMENT')
    if (f) this.config.f_status_payment = true

    // init UI
    this.config.f_themmoi_enabled = true
    this.config.f_gachno_enabled = false
    this.config.f_xacnhan_enabled = this.config.f_hoadon_enabled

    this.config.f_xoa_enabled = true
    this.config.f_huy_enabled = false
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    p_tong_tien: {
      get () {
        if (this.params.p_tien_thietbi !== null && this.params.p_tien_vipham !== null) { return this.params.p_tien_thietbi + this.params.p_tien_vipham } else { return null }
      },
      set (value) {
        return this.params.p_tong_tien = value
      }
    },
    p_ngay_ttoan: {
      get () {
        return this.params.p_ngay_ttoan
      },
      set (value) {
        return this.params.p_ngay_ttoan = value
      }
    }

  },
  data () {
    return {
      config: {
        f_themmoi_enabled: false,
        f_gachno_enabled: false,
        f_xoaphieu_enabled: false,
        f_huy_enabled: false,
        f_hoadon_enabled: false,
        f_hoadon_visible: false,
        f_xacnhan_enabled: false,

        f_ma_tb_readonly: false,
        f_ma_tt_readonly: false,
        f_tien_thietbi_readonly: false,
        f_tien_vipham_readonly: false,
        f_ghichu_readonly: false,
        f_status_payment: false
      },
      content: 'Loading..',

      pageinfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      params: {
        p_kycuoc: moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM') + '01',
        p_ma_tt: null,
        p_acc_somay: null,
        p_ten_tb: null,
        p_diachi_tb: null,
        p_thungan_options: [],
        p_thungan: null,
        p_ngay_ttoan: new Date(),
        p_lydo_thu: null,
        p_tien_thietbi: null,
        p_tien_vipham: null,
        p_tong_tien: null,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_mst: null,
        p_somay_dd: null,
        p_ma_nv: null,
        p_so_phieu: null,
        p_so_dienthoai: null,
        p_nv_capnhat: null,
        p_so_giayto: null,
        p_ngay_capnhat: null,
        p_loai_giayto: null,
        p_loai_hoadon_options: [],
        p_loai_hoadon: null,
        p_kyhieu_hoadon_options: [],
        p_kyhieu_hoadon: null,
        p_so_hoadon1: null,
        p_so_hoadon2: null,
        is_added: false,
        is_deleted: false
      },
      fieldsNVThuNgan: { text: 'TEN_NV', value: 'MA_NV' },
      fieldsDMLoaiHD: { text: 'TENTAT', value: 'LOAIHOADON_ID' },
      fieldsKyHieuHD: { text: 'SERI', value: 'SERI_ID' },
      dateConfig: {
        value: new Date(),
        format: 'dd/MM/y',
        max: new Date()
      },
      dsKHViPham: [],
      keyKHViPham: 0,
      selectedRow: null,
      f_changed: false
    }
  }, // end of data
  methods: {
    async getThamSo (maThamSo) {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: maThamSo})
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let t = parseInt(data.data[0].TEN_TS)
          if (t === 1) return true
        }
        return false
      } catch (error) {
        return false
      }
    },
    lockObject (val) {
      this.config.f_ma_tb_readonly = val
      this.config.f_ma_tt_readonly = val
      this.config.f_tien_tb_readonly = val
      this.config.f_tien_vipham_readonly = val
      this.config.f_ghichu_readonly = val
    },
    onChangeMaTT () {
      // console.log('change')
      if (!this.f_changed) { this.clearForm(1) }
      this.f_changed = true
    },
    onChangeMaTB () {
      // console.log('change')
      if (!this.f_changed) { this.clearForm(2) }
      this.f_changed = true
    },
    validateData: function () {
      let errors = []

      if (this.params.p_ten_tt === null || this.params.p_ten_tt.trim() === '') {
        this.$toast.error('Không tìm thấy thông tin thanh toán cần gạch nợ. Vui lòng tra cứu thông tin khách hàng trước.')
        return false
      }

      if (this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '') {
        errors.push('Trường mã thanh toán bắt buộc nhập')
      }
      if (this.params.p_thungan === null) {
        errors.push('Trường thu ngân bắt buộc nhập')
      }
      if (this.params.p_ngay_ttoan === null) {
        errors.push('Trường ngày TT bắt buộc nhập')
      } else {
        if (!moment(this.params.p_ngay_ttoan).isValid()) {
          errors.push('Trường ngày TT không hợp lệ (định dạng bắt buộc DD/MM/YYYY)')
        }
      }

      if (this.params.p_lydo_thu === null || this.params.p_lydo_thu.trim() === '') {
        errors.push('Trường lý do thu bắt buộc nhập')
      }
      /*
      if(this.params.p_tien_thietbi === null)
      {
        errors.push('Trường tiền thiết bị bắt buộc nhập')
      }
      if(this.params.p_tien_vipham === null)
      {
        errors.push('Trường tiền vi phạm bắt buộc nhập')
      }
*/

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    doPopupPhieuHuy: function () {
      this.$refs.refDanhSachPhieuTraHuy.openDialog()
    },
    doGachNo: function () {
      if (!this.validateData()) {
        return false
      }
      let postData = {
        'kyCuoc': this.params.p_kycuoc,
        'lyDoThu': this.params.p_lydo_thu.trim(),
        'maTB': this.params.p_acc_somay,
        'maTN': this.params.p_thungan,
        'maTT': this.params.p_ma_tt,
        'ngayThu': moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        'tienThietBi': this.params.p_tien_thietbi,
        'tienThu': this.params.p_tien_thietbi + this.params.p_tien_vipham,
        'tienViPham': this.params.p_tien_vipham
      }

      this.loading(true)

      GachNoKHViPhamCamKetAPI.gachNoKHViPhamCamKet(this.axios, postData)
        .then(
          (res) => {
          // console.log(res)
            if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0 &&
            res.data.data[0].CODE_ERROR === 'BCSS00000000') {
              let postData = {
                kyCuoc: this.params.p_kycuoc,
                maTN: this.params.p_thungan
                /// / pageSize, pageNo
              }
              this.$toast.success(res.data.data[0].CODE_MESSAGE)
              this.params.is_added = false
              this.clearForm()
              this.config.f_themmoi_enabled = true
              this.config.f_gachno_enabled = false
              this.config.f_xoa_enabled = true
              this.config.f_huy_enabled = false
              this.fetchDanhSachKH(postData)
            }
          }
        )
        .finally(() => {
          this.loading(false)
        })
    },

    doXuatHoaDon () {
      let msg = 'Bạn có muốn xuất hoá đơn thông tin sau không?\r\n'
      msg = msg + 'Mã TT:' + this.params.p_ma_tt + '\r\n'
      msg = msg + 'Tên khách hàng:' + this.params.p_ten_tt + '\r\n'
      msg = msg + 'Địa chỉ :' + this.params.p_diachi_tt + '\r\n'
      msg = msg + 'Mã số thuế:' + this.params.p_mst + '\r\n'
      msg = msg + 'Mã thuê bao:' + this.params.p_acc_somay + '\r\n'
      msg = msg + 'Nội dung:' + this.params.p_lydo_thu + '\r\n'
      msg = msg + 'Tổng tiền:' + this.params.p_tong_tien

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>" + msg + '</div>',
        okButton: { text: 'Đồng ý', click: this.confirmHDDT },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmHDDT () {
      DialogObj.hide()

      this.XuatHoaDonDienTu()
    },

    getDSThuNgan: function () {
      try {
        GachNoAPI.getDSThuNgan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].MA_NV
            } else {
              this.params.p_thungan_options = []
              this.params.p_thungan = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSLoaiHD: function () {
      try {
        GachNoKHViPhamCamKetAPI.getDSLoaiHoaDon(this.axios, 2).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.params.p_loai_hoadon_options = response.data.data
              this.params.p_loai_hoadon = response.data.data[0].LOAIHOADON_ID
            } else {
              this.params.p_loai_hoadon_options = []
              this.params.p_loai_hoadon = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    onChangeLoaiHD: function (args) {
      try {
        GachNoKHViPhamCamKetAPI.getDSKyHieuHoaDon(this.axios, { loaiHD: args.value, kieuDuLieu: 1 }).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_kyhieu_hoadon_options = response.data.data
              if (this.params.p_kyhieu_hoadon_options.length > 0) {
                this.params.p_kyhieu_hoadon = response.data.data[0].SERI_ID
              } else this.params.p_kyhieu_hoadon = null
            } else {
              this.params.p_kyhieu_hoadon_options = []
              this.params.p_kyhieu_hoadon = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    onThuNganChange: function (args) {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        maTN: args.value
      }
      this.fetchDanhSachKH(postData)
    },
    async getTTKhachHang (data) {
      try {
        const results = await GachNoKHViPhamCamKetAPI.getTTKhachHang(this.axios, data)
        return results
      } catch (err) {
        this.$toast.error(err.data.message_detail)
      }
    },

    async onSearch (kieu) {
      if (kieu === 1 && (this.params.p_ma_tt === undefined || this.params.p_ma_tt.trim() === '')) {
        this.$toast.error('Trường mã thanh toán bắt buộc nhập.')
        return false
      }
      if (kieu === 2 && (this.params.p_acc_somay === undefined || this.params.p_acc_somay.trim() === '')) {
        this.$toast.error('Trường thuê bao bắt buộc nhập.')
        return false
      }
      let postData = {
        maTT: kieu === 1 ? this.params.p_ma_tt.trim() : null,
        maTB: kieu === 2 ? this.params.p_acc_somay.trim() : null
      }

      // this.loading(true)

      await this.getTTKhachHang(postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
          res.data.data !== undefined &&
          res.data.data.length > 0) {
              this.params.p_ma_tt = res.data.data[0].MA_TT
              this.params.p_acc_somay = res.data.data[0].MA_TB
              this.params.p_ten_tb = res.data.data[0].TEN_TB
              this.params.p_diachi_tb = res.data.data[0].DIACHI_TB

              this.params.p_ten_tt = res.data.data[0].TEN_TT
              this.params.p_diachi_tt = res.data.data[0].DIACHI_TT
              this.params.p_mst = res.data.data[0].MST
              this.params.p_somay_dd = res.data.data[0].MATB_DD
              this.params.p_ma_nv = res.data.data[0].MANV_TC
              this.params.p_so_dienthoai = res.data.data[0].SO_DT
              this.params.p_so_giayto = res.data.data[0].SO_GT
              // this.params.p_ngay_capnhat = res.data.data[0].

              let httt = ''
              if (res.data.data[0].HTTT_ID === 0) { httt = 'Phiếu thu' } else if (res.data.data[0].HTTT_ID === 1) { httt = 'Phiếu phát' } else if (res.data.data[0].HTTT_ID === 2) { httt = 'Qua ngân hàng' }

              this.params.p_loai_giayto = httt

              if (this.selectedRow !== null) {
                this.params.p_tien_thietbi = this.selectedRow.TIENTHIETBI
                this.params.p_tien_vipham = this.selectedRow.TIENVIPHAM
                this.params.p_tong_tien = this.selectedRow.TIENTHU
                this.params.p_so_phieu = this.selectedRow.SOPHIEU
                this.params.p_ngay_capnhat = this.selectedRow.NGAYCN
                this.params.p_nv_capnhat = this.selectedRow.NHANVIENCN
                this.params.p_lydo_thu = this.selectedRow.LYDOTHU
                this.params.p_so_hoadon1 = this.selectedRow.SOHOADON
                this.params.p_so_hoadon2 = this.selectedRow.FKEY
                if (this.params.p_so_hoadon1 === null || this.params.p_so_hoadon1 === '') {
                  this.config.f_hoadon_enabled = true
                  this.config.f_xacnhan_enabled = false
                } else {
                  this.config.f_hoadon_enabled = false
                  this.config.f_xacnhan_enabled = true
                }
              }
            } else {
              this.$toast.error('Không tìm thấy thông tin khách hàng.')
            }
          }
        )
    },

    fetchDanhSachKH: function (postData) {
      this.loading(true)
      this.getDanhSachKHViPhamCamKet(postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
          res.data.data !== undefined &&
          res.data.data.length > 0) {
              this.dsKHViPham = res.data.data
              this.keyKHViPham++
              this.pageinfo.totalElement = res.data.data.length
              this.params.is_deleted = true
            } else {
              this.dsKHViPham = []
              this.pageinfo.totalElement = 0
              this.pageinfo.page = 0
              this.params.is_deleted = false
            }
          }
        )
        .finally(
          () => { this.loading(false) }
        )
    },

    async getDanhSachKHViPhamCamKet (postData) {
      try {
        const results = await GachNoKHViPhamCamKetAPI.getDSKHViPhamCamKet(this.axios, postData)
        return results
      } catch (err) {
        if (err.data.message_detail !== undefined) this.$toast.error(err.data.message_detail)
        else console.log(err)
      }
    },

    async XuatHoaDonDienTu () {
      this.loading(true)
      const f = await this.createCustomer()

      if (f) {
        let res_fkey = await this.axios.get('/web-hopdong/thanhtoan/sinh_fkey?kieu=1')
        let sinh_fkey = ''
        if (res_fkey.data.error_code === 'BSS-00000000') sinh_fkey = res_fkey.data.data
        // console.log(res_fkey.data.data)
        let tongDichVu = 0
        let tongThue = 0
        let tongTien = Number(this.params.p_tong_tien)
        let tongTienBangChu = this.docSo(tongTien)
        tongDichVu = Math.round(tongTien / 1.1)
        tongThue = tongTien - tongDichVu
        let invData = {
          Invoices: {
            Inv: {
              key: sinh_fkey,
              Invoice: {
                CusCode: ('' + this.params.p_ma_tt).trim(),
                CusName: this.params.p_ten_tt.trim(),
                CusAddress: this.params.p_diachi_tt.trim(),
                CusPhone: this.params.p_so_dienthoai !== null ? ('' + this.params.p_so_dienthoai).trim() : '',
                CusTaxCode: this.params.p_mst !== null ? ('' + this.params.p_mst).trim() : '',
                PaymentMethod: 'Tiền mặt/Chuyển khoản',
                KindOfService: '',
                Products: {
                  Product: {
                    ProdName: ('' + this.params.p_lydo_thu).trim(),
                    ProdUnit: '',
                    ProdQuantity: '',
                    ProdPrice: tongDichVu,
                    Amount: tongDichVu,
                    Total: tongDichVu,
                    VATRate: '10',
                    VATAmount: tongThue
                  }
                },
                Total: tongDichVu,
                VATRate: '10',
                VATAmount: tongThue,
                Amount: tongTien,
                AmountInWords: tongTienBangChu
              }
            }
          }
        }
        if (this.config.f_status_payment) invData.Invoices.Inv.Invoice.PaymentStatus = 1
        let xmlData = this.genXMLCustomer(invData)

        const inv = await this.importAndPublishInv(xmlData)
        if (inv !== false) {
          let result = inv.split(':')
          if (result[0] === 'OK') {
            let result1 = result[1].split(';')
            let InvoicePattern1 = result1[0]
            let result2 = result1[1].split('-')
            let SerialNo1 = result2[0]
            let result3 = result2[1].split('_')
            let InvoiceNo1 = result3[1]
            let seri = this.params.p_kyhieu_hoadon_options.find(x => x.SERI_ID === this.params.p_kyhieu_hoadon).SERI
            let updateHDDTData = {
              soPhieu: this.params.p_so_phieu,
              fKey: sinh_fkey,
              kyHieu: seri,
              mauHoaDon: this.params.p_loai_hoadon_options.find(x => x.LOAIHOADON_ID === this.params.p_loai_hoadon).TENTAT,
              soHoaDon: InvoiceNo1
            }
            let f = await this.updateHDDT(updateHDDTData)
            if (f) {
              this.$toast.success(`Xuất hóa đơn thành công: Ký hiệu: ${seri} Số hoá đơn: ${InvoiceNo1}`)
              let postData = {
                kyCuoc: this.params.p_kycuoc,
                maTN: this.params.p_thungan
              }

              this.fetchDanhSachKH(postData)
            }
          } else {
            this.$toast.error('Có lỗi trong quá trình xuất hóa đơn điện tử')
          }
        }
      }
      this.loading(false)
    },

    async createCustomer () {
      let obj = {
        Customers: {
          Customer: {
            Name: this.params.p_ten_tt.trim(),
            Code: ('' + this.params.p_ma_tt).trim(),
            TaxCode: this.params.p_mst !== null ? ('' + this.params.p_mst).trim() : '',
            Address: this.params.p_diachi_tt.trim(),
            BankAccountName: '',
            BankName: '',
            BankNumber: '',
            Email: '',
            Fax: '',
            Phone: this.params.p_so_dienthoai !== null ? ('' + this.params.p_so_dienthoai).trim() : '',
            ContactPerson: '',
            RepresentPerson: '',
            CusType: '0'
          }
        }
      }
      let xmlData = this.genXMLCustomer(obj)
      let postData = {
        xmlCusData: xmlData,
        convert: 0
      }
      const results = await GachNoKHViPhamCamKetAPI.updateCus(this.axios, postData)
      const data = await results.data

      if (data.errorCode === 0) return true
      else {
        this.$toast.error(data.faultString)
        return false
      }
    },

    async importAndPublishInv (xmlData) {
      try {
        let postData = {
          xmlInvData: xmlData,
          pattern: this.params.p_loai_hoadon_options.find(x => x.LOAIHOADON_ID === this.params.p_loai_hoadon).TENTAT,
          serial: this.params.p_kyhieu_hoadon_options.find(x => x.SERI_ID === this.params.p_kyhieu_hoadon).SERI,
          convert: 0
        }
        const res = await GachNoKHViPhamCamKetAPI.importAndPublishInv(this.axios, postData)
        const data = await res.data
        // console.log(data)
        if (data.errorCode === 0) return data.data
        else {
          this.$toast.error(data.faultString)
          return false
        }
      } catch (err) {
        return false
      }
    },

    doAdd: function () {
      this.clearForm()
      this.params.p_lydo_thu = 'Khách hàng bồi hoàn hợp đồng do vi phạm cam kết'
      this.config.f_themmoi_enabled = false
      this.config.f_gachno_enabled = true
      this.config.f_xoa_enabled = false
      this.config.f_huy_enabled = true
      this.config.f_hoadon_enabled = false

      this.lockObject(false)
      this.$refs.maTT.focus()
    },

    doHuy: function () {
      this.config.f_themmoi_enabled = true
      // panel1.Enabled = false;
      this.config.f_gachno_enabled = false
      this.config.f_xoa_enabled = true
      this.config.f_huy_enabled = false

      this.clearForm()
      this.loadKHFromGrid()
    },
    loadKHFromGrid () {
      this.clearForm(0)
      if (this.selectedRow !== null) {
        this.lockObject(true)
        this.params.p_acc_somay = this.selectedRow.SOMAY

        this.onSearch(2)
      }
    },
    async updateHDDT (postData) {
      try {
        const res = await GachNoKHViPhamCamKetAPI.updateHDDT(this.axios, postData)
        const data = await res.data
        if (data.error_code === 'BSS-00000000') return true
        return false
      } catch (err) {
        console.log(err)
        return false
      }
    },
    doXoaPhieu: function () {
      if (this.selectedRow === null) {
        this.$toast.error('Vui lòng chọn phiếu cần xóa')
        return false
      }

      if (this.selectedRow.FKEY !== null && this.selectedRow.FKEY !== '') {
        this.$toast.error('Phiếu này đã xuất hoá đơn.\r\nVì vậy không thể xoá phiếu.')
        return false
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có thật sự muốn xóa số phiếu " + this.selectedRow.SOPHIEU + ' này không ?</div>',
        okButton: { text: 'Đồng ý', click: this.confirmDelete },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmDelete: function () {
      this.loading(true)
      try {
        GachNoKHViPhamCamKetAPI.deleteGachNoViPham(this.axios, this.selectedRow.ID)
          .then(
            (res) => {
              if (res.data.error_code === 'BSS-00000000') {
                this.$toast.success('Đã xóa thành công')
                let postData = {
                  kyCuoc: this.params.p_kycuoc,
                  maTN: this.params.p_thungan
                /// / pageSize, pageNo
                }
                this.fetchDanhSachKH(postData)
              } else {
                this.$toast.error(res.data.message_detail)
              }
            }
          )
          .catch(error => {
            console.log(error)
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {
            DialogObj.hide()
            this.loading(false)
          })
      } catch (err) {
        console.log(err)
        this.$toast.error(err.data.message_detail)
      }
    },

    async confirmFKey (fkey, pattern, serial) {
      try {
        let postData = {
          pattern: pattern,
          serial: serial,
          fKey: fkey
        }
        const res = await GachNoKHViPhamCamKetAPI.confirmPaymentFkey(this.axios, postData)
        const data = await res.data
        if (data.errorCode === 0) return true
        else {
          this.$toast.error(data.faultString)
          return false
        }
      } catch (error) {
        return false
      }
    },
    async doXacNhanTT () {
      if (this.dsKHViPham.length <= 0) {
        this.$toast.error('Danh sách gạch nợ trống!')
        return
      }

      if (this.selectedRow === null) {
        this.$toast.error('Chưa chọn trong danh sách!')
        return
      }

      let fkey = this.selectedRow.FKEY
      let pattern = this.selectedRow.MAUHOADON
      let serial = this.selectedRow.KYHIEU

      if (fkey !== null && fkey !== '') {
        let res = await this.confirmFKey(fkey, pattern, serial)

        if (res) {
          this.$toast.success('Đã xác nhận hoá đơn ở trạng thái thanh toán.')
          let postData = {
            kyCuoc: this.params.p_kycuoc,
            maTN: this.params.p_thungan
          }
          this.fetchDanhSachKH(postData)
        }
      }
    },
    clearForm: function (type = 0) {
      if (type !== 1) { this.params.p_ma_tt = '' }
      if (type !== 2) { this.params.p_acc_somay = '' }
      this.params.p_ten_tb = ''
      this.params.p_diachi_tb = ''
      this.params.p_lydo_thu = ''
      this.params.p_tien_thietbi = null
      this.params.p_tien_vipham = null
      this.params.p_tong_tien = null

      this.params.p_ten_tt = ''
      this.params.p_diachi_tt = ''
      this.params.p_mst = ''
      this.params.p_somay_dd = ''
      this.params.p_ma_nv = ''
      this.params.p_so_phieu = ''
      this.params.p_so_dienthoai = ''
      this.params.p_nv_capnhat = ''
      this.params.p_so_giayto = ''
      this.params.p_ngay_capnhat = null
      this.params.p_loai_giayto = ''
      this.params.p_so_hoadon1 = ''
      this.params.p_so_hoadon2 = ''
    },
    onFilteringThuNgan (e) {
      let query = new Query()
      let key = e.text.trim()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', key, true) : query
      e.updateData(this.params.p_thungan_options, query)
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
        if (this.params.p_thungan !== null) {
          let postData = {
            kyCuoc: this.params.p_kycuoc,
            maTN: this.params.p_thungan
          }
          this.fetchDanhSachKH(postData)
        }
      } else { this.params.p_kycuoc = null }
    },
    selectedRowChanged (e) {
      console.log(e)
      if (e === null) {
        this.selectedRow = null
        this.clearForm(0)
        return
      }
      this.selectedRow = e
      this.loadKHFromGrid()
    },
    onBeforeRender: function (args) {
      let ret = this.params.p_thungan_options.filter(x => x.MA_NV === this.params.p_thungan)
      console.log(ret)
      if (ret.length > 0) { this.content = ret[0].TEN_NV } else { this.content = '-' }
    },
    genXMLCustomer (obj) {
      return this.OBJtoXML(obj)
    },
    OBJtoXML (obj) {
      let toXML = function (obj) {
        let xml = ''
        for (var prop in obj) {
          xml += obj[prop] instanceof Array ? '' : '<' + prop + '>'
          if (obj[prop] instanceof Array) {
            for (var array in obj[prop]) {
              xml += '<' + prop + '>'
              xml += toXML(new Object(obj[prop][array]))
              xml += '</' + prop + '>'
            }
          } else if (typeof obj[prop] === 'object') {
            xml += toXML(new Object(obj[prop]))
          } else {
            xml += obj[prop]
          }
          xml += obj[prop] instanceof Array ? '' : '</' + prop + '>'
        }
        xml = xml.replace(/<\/?[0-9]{1,}>/g, '')
        return xml
      }

      let xml = toXML(obj)

      return xml
    },
    leadingZero (num) {
      let sNum = num + ''
      let needZeroCount = sNum.length % 3
      if (needZeroCount !== 0) needZeroCount = 3 - needZeroCount
      // Thêm needZeroCount số 0 cho đủ
      sNum = '0'.repeat(needZeroCount) + sNum
      return sNum
    },
    readThree (a, b, c, readZeroHundred) {
      const output = []

      // Đọc phần trăm (a) trước
      if (a !== 0 || readZeroHundred) output.push(DIGITS[a], 'trăm') // Đọc là "a trăm"
      // Nối thêm phần sau (b, c)
      // Ở đây dùng spread syntax để nối output
      output.push(...this.readTwo(b, c, a !== 0))

      return output
    },
    readTwo (b, c, hasHundred) {
      const output = []
      switch (b) {
        case 0: {
          // Nếu có đọc hàng trăm (đọc rồi) và b = 0, c = 0
          // thì không đọc nữa
          if (hasHundred && c === 0) break
          if (hasHundred) output.push('lẻ') // ví dụ a05 đọc là "a lẻ năm"
          output.push(DIGITS[c])
          break
        }

        case 1: {
          // Trường hợp số hàng chục là 10
          output.push('mười')
          if (c === 5) output.push('lăm') // Mười lăm
          else if (c !== 0) { output.push(DIGITS[c]) }
          // Trường hợp c = 0 không xét vì đã đọc "mười" rồi
          break
        }

        default: {
          output.push(DIGITS[b], 'mươi') // b mươi
          if (c === 1) { output.push('mốt') }

          // Chỗ này đọc "tư" hay "bốn" thì các bạn điều chỉnh nhe
          // Mình sẽ luôn đọc là "tư" nhé
          else if (c === 4) { output.push('tư') } else if (c === 5) { output.push('lăm') } else if (c !== 0) { output.push(DIGITS[c]) }
          // Không đọc c = 0 vì đã đọc "b mươi" rồi
          break
        }
      }

      // Trả về mảng output, xem lại phần trước nhé
      return output
    },
    docSo (num) {
      let sNum = this.leadingZero(num)
      const output = []
      for (let i = 0; i < sNum.length / 3; i++) {
        let [a, b, c] = sNum.substr(i * 3, 3)
        a = parseInt(a)
        b = parseInt(b)
        c = parseInt(c)
        const isFirstGroup = i === 0
        output.push(...this.readThree(a, b, c, !isFirstGroup))
        output.push(UNITS[sNum.length / 3 - 1 - i])
      }
      const output1 = output.join(' ')
      const output2 = output1.charAt(0).toUpperCase() + output1.slice(1)
      console.log(output2)
      return output2
    }

  }
}
</script>
