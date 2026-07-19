<template src="./QuanLyNhomCK.html"></template>
<style src="./QuanLyNhomCK.scss"></style>

<script>
import Vue from 'vue'
import moment from 'moment'
import NhomChuyenKhoanAPI from '../api/NhomChuyenKhoan.js'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import { Query } from '@syncfusion/ej2-data'
import Calculator from '@/components/Calculator'
import NhapNgayQuet from './components/NhapNgayQuet'

import XLSX from 'xlsx'

import { MultiSelectPlugin, CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { CheckBoxPlugin } from '@syncfusion/ej2-vue-buttons'

import { DialogUtility } from '@syncfusion/ej2-popups'

Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)
Vue.use(DialogUtility)
let DialogObj

export default Vue.extend({
  components: {
    appCalculator: Calculator,
    appNhapNgayQuet: NhapNgayQuet
  },
  computed: {
    chk_tungay () {
      return this.params.p_chk_tungay === '1'
    },
    chk_chonnhanh_dagan () {
      return this.params.p_chk_chonnhanh_dagan
    },
    chk_chonnhanh_chuagan () {
      return this.params.p_chk_chonnhanh_chuagan
    },
    p_ngay_tao: {
      get () {
        return this.params.p_ngay_tao
      },
      set (value) {
        this.params.p_ngay_tao = value
      }
    },
    p_den_ngay: {
      get () {
        return this.params.p_den_ngay
      },
      set (value) {
        this.params.p_den_ngay = value
      }
    },
    p_tu_ngay: {
      get () {
        return this.params.p_tu_ngay
      },
      set (value) {
        this.params.p_tu_ngay = value
      }
    },
    lblTieuChi () {
      let label = []
      if (this.params.p_chk_opt0 === '1' && this.params.p_opt0 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt0)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt1 === '1' && this.params.p_opt1 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt1)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt2 === '1' && this.params.p_opt2 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt2)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_opt3 === '1' && this.params.p_opt3 !== null) {
        let val = this.getNoiDungTieuChi(this.params.p_opt3)
        if (val !== null) { label.push(val.KIEU_TK) }
      }

      if (this.params.p_chk_thangno === '1') { label.push('Tìm tháng') }
      if (this.params.p_chk_khoanmuc === '1') { label.push('Tìm khoản mục') }
      if (this.params.p_chk_tutien === '1' && this.params.p_chk_dentien === '1') {
        label.push('Từ tiền')
        label.push('Đến tiền')
      }

      if (this.params.p_chk_nhom === '1') { label.push('Tìm tên nhóm') }

      if (this.params.p_chk_chungtu === '1') { label.push('Tìm chứng từ') }

      if (label.length <= 0) { return 'chưa chọn' } else { return label.join(',') }
    },
    chk_opt0 () {
      return this.params.p_chk_opt0 === '1'
    },
    chk_opt1 () {
      return this.params.p_chk_opt1 === '1'
    },
    chk_opt2 () {
      return this.params.p_chk_opt2 === '1'
    },
    chk_opt3 () {
      return this.params.p_chk_opt3 === '1'
    },
    chk_thangno () {
      return this.params.p_chk_thangno === '1'
    },
    chk_khoanmuc () {
      return this.params.p_chk_khoanmuc === '1'
    },
    chk_tutien () {
      return this.params.p_chk_tutien === '1'
    },
    chk_dentien () {
      return this.params.p_chk_dentien === '1'
    },
    chk_chungtu () {
      return this.params.p_chk_chungtu === '1'
    },
    chk_nhom () {
      return this.params.p_chk_nhom === '1'
    },
    p_ky_hd: {
      get () {
        return this.params.p_ky_hd
      },
      set (value) {
        this.params.p_ky_hd = value
      }
    },
    p_thang: {
      get () {
        return this.params.p_thang
      },
      set (value) {
        this.params.p_thang = value
      }
    }
  },
  async created () {
    // load data
    // this.getKyCuocHienHanh()

    this.loading(true)
    await Promise.all([
      this.getDSTieuChiTimKiem(),
      this.loadThangTT(),
      this.getDSKhoanMuc()
    ])
    this.loading(false)

    this.$refs.refMaNhom.focus()
  },
  data () {
    return {
      configs: {
        dsNhomCkColumns: [
          {fieldName: 'NHOMCK_ID', headerText: 'ID Nhóm', isPrimaryKey: true, headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true, width: 100, freeze: 'Left'},
          {fieldName: 'MA_NHOM', headerText: 'Mã nhóm', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 120, freeze: 'Left'},
          {fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 200, allowEditing: false},
          {fieldName: 'NGAY_TAO', headerText: 'Ngày tạo', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true, width: 120},
          {fieldName: 'STK', headerText: 'Số tài khoản', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 140, allowEditing: true},
          {fieldName: 'CHUNGTU', headerText: 'Chứng từ', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 140, allowEditing: false},
          {fieldName: 'GHICHU', headerText: 'Ghi chú', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 150, allowEditing: false}
        ],
        dsMaTTDaGan: [
          {fieldName: 'MA_TT', headerText: 'Mã Thanh toán', textAlign: 'Left', allowFiltering: true, width: 120, freeze: 'Left'},
          {fieldName: 'MATB_DD', headerText: 'Mã đại diện', textAlign: 'Left', allowFiltering: true, width: 120},
          {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', textAlign: 'Left', allowFiltering: true, width: 200},
          {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', textAlign: 'Left', allowFiltering: true, width: 200},
          {fieldName: 'MA_NV', headerText: 'Tuyến thu', textAlign: 'Left', allowFiltering: true, width: 120}
        ],
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        thang_tt: {
          fields: { text: 'text', value: 'id'},
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        khoanmuc: {
          fields: { text: 'TEN_KM', value: 'KHOANMUCTT_ID'},
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh',
          allowFiltering: true
        },
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsOpts: {text: 'KIEU_TK', value: 'KIEUTK_ID' },
        fieldsKhoanMuc: {text: 'TEN_KM', value: 'KHOANMUCTT_ID' },
        fieldsThangNo: {text: 'KIEU_TK', value: 'KIEUTK_ID' },
        fieldsKieuNo: {text: 'text', value: 'id' },
        fieldsSM_ACC: {text: 'MA_TB', value: 'MA_TB' },
        max_kyhoadon: moment(new Date()).subtract(1, 'months').endOf('month').toDate(),
        f_addnew_enabled: true,
        f_addnew_visible: true,
        f_update_enabled: true,
        f_update_visible: false,
        f_delete_enabled: true,
        f_delete_visible: false,
        f_cancel_enabled: true,
        f_cancel_visible: false,
        f_readfile_visible: false
      },
      params: {
        p_ma_nhom: null,
        p_ngay_tao: new Date(),
        p_so_tk: null,
        p_chung_tu: null,
        p_ghi_chu: null,
        p_chk_tungay: '1',
        p_tu_ngay: moment().subtract(7, 'days').startOf('day').toDate(),
        p_den_ngay: new Date(),
        p_chk_chonnhanh_dagan: '0',
        p_chk_chonnhanh_chuagan: '0',
        p_vnhom_id: 0,
        p_options: [],
        p_opt0: null,
        p_opt1: null,
        p_opt2: null,
        p_opt3: null,
        p_thangno: [],
        p_thangno_options: [],
        p_kieuno_options: [{ id: 0, text: 'Tổng nợ'}, { id: 1, text: 'Phát sinh'}, { id: 2, text: 'Đầu kỳ'}],
        p_kieuno: 0,
        p_khoanmuc_options: [],
        p_khoanmuc: [],
        p_chk_opt0: '0',
        p_chk_opt1: '0',
        p_chk_opt2: '0',
        p_chk_opt3: '0',
        p_chk_thangno: '0',
        p_chk_khoanmuc: '0',
        p_chk_tutien: '0',
        p_chk_dentien: '0',
        p_tieuchi0: null,
        p_tieuchi1: null,
        p_tieuchi2: null,
        p_tieuchi3: null,
        p_tutien: 0,
        p_dentien: 0,
        p_chungtu: null,
        p_chk_chungtu: '0',
        p_chk_nhom: '0',
        p_nhom: null,
        p_tab_kieugan: 1 // 1: gán theo tìm kiếm,  2: gán theo file
      },
      dsNhomCK: [],
      keyDSNhomCK: 0,
      dsMaTTDaGan: [],
      keyDSMaTTDaGan: 0,
      dsMaTTChuaGan: [],
      keyDSMaTTChuaGan: 0,
      dsMaTTChuaGanTheoFile: [],
      f_khoaform: false,
      is_show: false,
      isTuTienCalShow: false,
      isDenTienCalShow: false,
      f_show_khoanmuc: false,
      dsTaoNhomCK: [],
      dsTaoNhomCKError: [],
      nhomCKPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      dsDaGanPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      dsChuaGanPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
      dsChuaGanTheoFilePageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      }
    }
  }, // end of data
  methods: {
    TRA_CUU_MA_NHOM () // handler MaNhom Enter press
    {
      if (this.params.p_ma_nhom !== null && this.params.p_ma_nhom !== '' && this.params.p_ma_nhom.trim() !== '') {
        this.params.p_ma_nhom = this.params.p_ma_nhom.trim()
        this.params.p_chk_tungay = '1'
        this.TIMKIEM()
      }
    },
    doLayThongTin () // handler click Lay Thong tin button
    {
      this.TIMKIEM()
    },

    doSetDate () {
      // this.$toast.error('Đang dev')
      this.$refs.refNhapNgayQuet.openDialog()
    },

    TIMKIEM () {
      this.params.p_vnhom_id = 0
      let postData = {
        maNhom: this.params.p_ma_nhom,
        tuNgay: this.params.p_chk_tungay === '1' ? moment(this.params.p_tu_ngay).format('DD/MM/YYYY') : null,
        denNgay: this.params.p_chk_tungay === '1' ? moment(this.params.p_den_ngay).format('DD/MM/YYYY') : null
      }

      this.loading(true)

      this.dsNhomCK = []

      NhomChuyenKhoanAPI.thongtinNhomChuyenKhoan(this.axios, postData)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined &&
                response.data.data.length > 0
          ) {
            this.dsNhomCK = response.data.data
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    NAP_DS_CHITIET_TT (args) {
      this.params.p_vnhom_id = args.NHOMCK_ID

      this.HIENTHI_DS_DAGAN()
      this.params.p_ma_nhom = args.MA_NHOM
      this.params.p_ten_nhom = args.TEN_NHOM
      this.params.p_so_tk = args.STK
      this.params.p_chung_tu = args.CHUNGTU
      this.params.p_ghi_chu = args.GHICHU
      this.params.p_ngay_tao = moment(args.NGAYTAO, 'DD/MM/YYYY').toDate()

      this.f_khoaform = false

      this.configs.f_update_visible = true
      this.configs.f_update_enabled = true
      this.configs.f_cancel_visible = true
      this.configs.f_delete_visible = true
      this.configs.f_delete_enabled = true
      this.configs.f_addnew_enabled = false
    },

    XOA_FORM () {
      this.params.p_vnhom_id = 0
      this.params.p_ngay_tao = null
      this.params.p_ma_nhom = null
      this.params.p_ten_nhom = null
      this.params.p_so_tk = null
      this.params.p_chung_tu = null
      this.params.p_ghi_chu = null
      this.dsMaTTDaGan = []

      this.f_khoaform = false

      // Cập nhật nút
      /*
        btnCapNhat.Visible = false;
        btnHuyBo.Visible = false;
        btnXoaPhieu.Enabled = false;
      */
    },
    THAOTAC_NUT (kieu) {
      if (kieu === 1) {
        this.XOA_FORM()
        this.f_khoaform = false
        this.configs.f_cancel_enabled = true
        this.configs.f_cancel_visible = true
        this.configs.f_update_enabled = true
        this.configs.f_update_visible = true
        this.configs.f_addnew_enabled = false
      } else if (kieu === 3) {
        // Xóa form
        this.XOA_FORM()
        // Cập nhật nút

        this.configs.f_update_visible = false
        this.configs.f_cancel_visible = false
        // this.configs.f_delete_enabled  = false
        this.configs.f_delete_visible = false
        this.configs.f_addnew_enabled = true
        // grvNhomTT.FocusedRowHandle = GridControl.AutoFilterRowHandle;
      }
    },

    HIENTHI_DS_DAGAN () {
      this.dsMaTTDaGan = []

      this.loading(true)

      NhomChuyenKhoanAPI.getDSThanhToanDaGan(this.axios, this.params.p_vnhom_id)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0
          ) {
            this.dsMaTTDaGan = res.data.data
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    KIEMTRA_DULIEU () {
      let errors = []

      if (this.params.p_ten_nhom === null || this.params.p_ten_nhom === '' || this.params.p_ten_nhom.trim() === '') {
        errors.push('Bạn chưa nhập tên nhóm')
      }

      if (this.configs.f_addnew_enabled && this.params.p_vnhom_id === 0) {
        errors.push('Bạn chưa chọn nhóm để cập nhật')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },

    GAN_DS () {
      if (this.params.p_vnhom_id === 0) {
        this.$toast.error('Bạn chưa chọn nhóm thanh toán để thực hiện')
        return
      }

      if (this.dsMaTTChuaGan.length <= 0) {
        this.$toast.error('Không có dữ liệu thanh toán để chọn')
        return
      }

      let selectedRows = this.$refs.gridDSMaTTChuaGan.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thanh toán để gán vào nhóm')
        return
      }

      let postData = {
        'ds': [],
        'nhomId': this.params.p_vnhom_id
      }

      selectedRows.forEach((item) => {
        postData.ds.push({
          maTT: item.MA_TT,
          thanhToanId: item.THANHTOAN_ID
        })
      })

      // Cal API gán theo danh sách

      this.loading(true)

      NhomChuyenKhoanAPI.taoDSTheoNhom(this.axios, postData)
        .then((res) => {
          this.$toast.success('Gán danh sách thành công.')
          this.dsMaTTChuaGan = []
          this.HIENTHI_DS_DAGAN()
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    GAN_DS_FILE () {
      if (this.params.p_vnhom_id === 0) {
        this.$toast.error('Bạn chưa chọn nhóm thanh toán để thực hiện')
        return
      }

      if (this.dsMaTTChuaGanTheoFile.length <= 0) {
        this.$toast.error('Không lấy được dữ liệu danh sách thuê bao')
        return
      }

      let selectedRows = this.$refs.gridDSMaTTChuaGanTheoFile.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thanh toán để gán vào nhóm')
        return
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có chắc chắn thực hiện gán danh sách mã thanh toán vào nhóm không?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmAppendByFile },
        cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })

      // Cal API gán theo danh sách
    },

    confirmAppendByFile () {
      DialogObj.hide()

      let postData = {
        'ds': [],
        'nhomId': this.params.p_vnhom_id
      }

      let selectedRows = this.$refs.gridDSMaTTChuaGanTheoFile.getSelectedRecords()

      selectedRows.forEach((item) => {
        postData.ds.push({
          maTT: item.MA_TT,
          thanhToanId: item.THANHTOAN_ID
        })
      })

      // Cal API gán theo danh sách

      this.loading(true)

      NhomChuyenKhoanAPI.taoDSTheoNhom(this.axios, postData)
        .then((res) => {
          this.$toast.success('Gán danh sách thành công.')
          this.dsMaTTChuaGanTheoFile = []
          this.HIENTHI_DS_DAGAN()
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    GO_DS () {
      if (this.params.p_vnhom_id === 0) {
        this.$toast.error('Bạn chưa chọn nhóm thanh toán để thực hiện')
        return
      }

      if (this.dsMaTTDaGan.length <= 0) {
        this.$toast.error('Không có dữ liệu thanh toán để chọn')
        return
      }

      let selectedRows = this.$refs.gridDSMaTTDaGan.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn thanh toán để gỡ khỏi nhóm')
        return
      }

      let ds = selectedRows.map(x => x.THANHTOAN_ID)

      let postData = {
        'dsThanhToanId': selectedRows.map(x => x.THANHTOAN_ID).join(','),
        'nhomCkId': this.params.p_vnhom_id
      }

      console.log(postData)

      // Cal API gán theo danh sách

      this.loading(true)

      NhomChuyenKhoanAPI.xoaKhoiNhomChuyenKhoan(this.axios, postData)
        .then((res) => {
          this.$toast.success('Gỡ danh sách thành công.')
          this.HIENTHI_DS_DAGAN()
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    doLayTT () {
      this.TIMKIEM()
    },
    // thực hiện Thêm mới action button
    doAddNew () {
      this.THAOTAC_NUT(1)
    },
    // thực hiện Hủy bỏ action button
    doCancel () {
      this.THAOTAC_NUT(3)
    },
    // thực hiện Cập nhật action button
    doUpdate () {
      if (!this.KIEMTRA_DULIEU()) return

      if (this.params.p_vnhom_id === 0) {
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có muốn thêm mới nhóm này không ?</div>",
          okButton: { text: 'Đồng ý', click: this.confirmCreate },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      } else {
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có muốn thay đổi thông tin của nhóm " + this.params.p_ma_nhom + ' không ?</div>',
          okButton: { text: 'Đồng ý', click: this.confirmUpdate },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      }
    },

    // thực hiện Xóa bỏ action button
    doDelete () {
      if (this.params.p_vnhom_id === 0) {
        this.$toast.error('Bạn chưa chọn nhóm để xóa.')
        return false
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có muốn xóa nhóm này không ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmDelete },
        cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    doReadFile () {
      document.getElementById('gannhom_upload_excel').click()
    },

    doCreateByFile () {
      document.getElementById('taonhom_upload_excel').click()
    },

    confirmCreate () {
      DialogObj.hide()

      let postData = {
        'donViId': this.$auth.getDonViID(),
        'tenNhom': this.params.p_ten_nhom.trim(),
        'chungTu': this.params.p_chung_tu !== null ? this.params.p_chung_tu.trim() : null,
        'soTaiKhoan': this.params.p_so_tk !== null ? this.params.p_so_tk.trim() : null,
        'ngayTao': moment(this.params.p_ngay_tao).format('DD/MM/YYYY'),
        'ghiChu': this.params.p_ghi_chu !== null ? this.params.p_ghi_chu.trim() : null
      }

      this.loading(true)

      NhomChuyenKhoanAPI.taoNhomChuyenKhoan(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            this.$toast.success('Tạo nhóm thành công.')
            let m_ngaytao = moment(this.params.p_ngay_tao)
            let m_tungay = moment(this.params.p_tu_ngay)
            let m_denngay = moment(this.params.p_den_ngay)

            if (this.params.p_chk_tungay === '1') {
              if (m_ngaytao.isBefore(m_tungay) || m_ngaytao.isAfter(m_denngay)) {
                this.params.p_den_ngay = this.params.p_ngay_tao
                this.params.p_tu_ngay = m_ngaytao.subtract(7, 'days').startOf('day').toDate()
              }
            }

            this.TIMKIEM()
          }
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    confirmUpdate () {
      DialogObj.hide()

      let postData = {
        'nhomCKId': this.params.p_vnhom_id,
        'donViId': this.$auth.getDonViID(),
        'tenNhom': this.params.p_ten_nhom.trim(),
        'chungTu': this.params.p_chung_tu !== null ? this.params.p_chung_tu.trim() : null,
        'soTaiKhoan': this.params.p_so_tk !== null ? this.params.p_so_tk.trim() : null,
        'ngayTao': moment(this.params.p_ngay_tao).format('DD/MM/YYYY'),
        'ghiChu': this.params.p_ghi_chu !== null ? this.params.p_ghi_chu.trim() : null
      }

      this.loading(true)

      NhomChuyenKhoanAPI.taoNhomChuyenKhoan(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật nhóm thành công.')

            this.XOA_FORM()

            let m_ngaytao = moment(this.params.p_ngay_tao)
            let m_tungay = moment(this.params.p_tu_ngay)
            let m_denngay = moment(this.params.p_den_ngay)

            if (m_ngaytao.isBefore(m_tungay) || m_ngaytao.isAfter(m_denngay)) {
              this.params.p_den_ngay = this.params.p_ngay_tao
              this.params.p_tu_ngay = m_ngaytao.subtract(7, 'days').startOf('day').toDate()
            }

            this.TIMKIEM()
          }
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    confirmDelete () {
      DialogObj.hide()

      this.loading(true)

      NhomChuyenKhoanAPI.xoaNhomChuyenKhoan(this.axios, this.params.p_vnhom_id)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa nhóm thành công.')
            this.TIMKIEM()
          }
        })
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    /* Tìm kiếm trên theo tiêu chí tìm kiếm */
    doSearch () {
      if (!this.validateSearch()) {
        return false
      }

      let arrDieuKien = []
      let strDieuKien = ''
      let tieuchi = null

      if (this.params.p_chk_opt0 === '1') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt0)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi0))
        arrDieuKien.push({
          'ID': this.params.p_opt0,
          'GIATRI': this.params.p_tieuchi0
        })
      }

      if (this.params.p_chk_opt1 === '1') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt1)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi1))
        arrDieuKien.push({
          'ID': this.params.p_opt1,
          'GIATRI': this.params.p_tieuchi1
        })
      }

      if (this.params.p_chk_opt2 === '1') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt2)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi2))
        arrDieuKien.push({
          'ID': this.params.p_opt2,
          'GIATRI': this.params.p_tieuchi2
        })
      }

      if (this.params.p_chk_opt3 === '1') {
        // tieuchi = this.getNoiDungTieuChi(this.params.p_opt3)
        // arrDieuKien.push(tieuchi.DIEUKIEN.replace(':values', this.params.p_tieuchi3))
        arrDieuKien.push({
          'ID': this.params.p_opt3,
          'GIATRI': this.params.p_tieuchi3
        })
      }

      // if(arrDieuKien.length > 0)
      // strDieuKien = arrDieuKien.join(' and ')

      let vKyHD = ''

      if (this.params.p_chk_chungtu === '1') {
        vKyHD = '01' + moment(this.params.p_ky_hd).format('MMYYYY')
      }

      this.is_show = false

      let postData = {
        pDieuKien: arrDieuKien,
        pKieuNo: this.params.p_kieuno,
        pTuTien: this.params.p_chk_tutien === '1' ? this.params.p_tutien : null,
        pDenTien: this.params.p_chk_dentien === '1' ? this.params.p_dentien : null,
        pChungTu: this.params.p_chk_chungtu === '1' ? this.params.p_chungtu.trim() : null,
        pKyHD: vKyHD,
        pTimThang: this.params.p_chk_thangno === '1' ? 1 : 0,
        pDSThang: this.params.p_chk_thangno === '1' ? this.params.p_thangno.join(',') : null,
        pNhomCKId: this.params.p_nhomck_id,
        pTimKMTT: this.params.p_chk_khoanmuc === '1' ? 1 : 0,
        pDSKhoanMuc: this.params.p_chk_khoanmuc === '1' ? this.params.p_khoanmuc.join(',') : null
      }

      this.loading(true)
      this.dsMaTTChuaGan = []

      GachNoAPI.tracuuDSTTDieuChinhNo(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.keyDSMaTTChuaGan++
              this.dsMaTTChuaGan = response.data.data
            } else {
              this.$toast.error('Không tìm thấy dữ liệu.')
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    validateSearch () {
      let errors = []

      if (this.params.p_chk_opt0 === '1') {
        if (this.params.p_opt0 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 1.') } else if (this.params.p_tieuchi0 === null || this.params.p_tieuchi0 === '' || this.params.p_tieuchi0.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt0)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt1 === '1') {
        if (this.params.p_opt1 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 2.') } else if (this.params.p_tieuchi1 === null || this.params.p_tieuchi1 === '' || this.params.p_tieuchi1.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt1)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt2 === '1') {
        if (this.params.p_opt2 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 3.') } else if (this.params.p_tieuchi2 === null || this.params.p_tieuchi2 === '' || this.params.p_tieuchi2.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt2)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_opt3 === '1') {
        if (this.params.p_opt3 === null) { errors.push('Chưa chọn điều kiện tìm kiếm 4.') } else if (this.params.p_tieuchi3 === null || this.params.p_tieuchi3 === '' || this.params.p_tieuchi3.trim() === '') {
          let val = this.getNoiDungTieuChi(this.params.p_opt3)
          if (val !== null) { errors.push('Chưa nhập giá trị tìm kiếm cho ' + val.KIEU_TK + '.') }
        }
      }

      if (this.params.p_chk_thangno === '1' && this.params.p_thangno.length <= 0) { errors.push('Chưa chọn tháng nợ.') }

      if (this.params.p_chk_khoanmuc === '1' && this.params.p_khoanmuc.length <= 0) { errors.push('Chưa chọn khoản mục.') }

      if (this.params.p_chk_chungtu === '1' && (this.params.p_chungtu === null || this.params.p_chungtu.trim() === '')) { errors.push('Chưa nhập chứng từ.') }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    // thực hiện Gỡ action button
    doGo () {
      this.GO_DS()
    },
    // thực hiện Gán action button
    doGan () {
      if (this.params.p_tab_kieugan === 1) // gán danh sách
      {
        this.GAN_DS()
      } else // gán theo file
      {
        this.GAN_DS_FILE()
      }
    },

    async uploadFileTaoNhom (e) {
      this.dsTaoNhomCK = []
      this.dsTaoNhomCKError = []

      var files = e.target.files, f = files[0]
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastWarning('File excel không đúng định dạng!')
      }
      var reader = new FileReader()
      const vm = this
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, {type: 'array'})
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook);
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        console.log(exceldata)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('taonhom_upload_excel').value = null
          return false
        }

        if (!exceldata[0].hasOwnProperty('TEN_NHOM') ||
          !exceldata[0].hasOwnProperty('SO_TK') ||
          !exceldata[0].hasOwnProperty('CHUNGTU') ||
          !exceldata[0].hasOwnProperty('GHICHU')
        ) {
          vm.$toast.error('File gồm có 4 cột: TEN_NHOM, SO_TK, CHUNGTU, GHICHU')
          document.getElementById('taonhom_upload_excel').value = null
          return false
        }

        vm.dsTaoNhomCK = [...exceldata]

        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có chắc chắn muốn thêm danh sách nhóm không ?</div>",
          okButton: { text: 'Đồng ý', click: vm.confirmCreateDSNhom },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('taonhom_upload_excel').value = null
    },

    async uploadFileGanNhom (e) {
      this.dsMaTTChuaGanTheoFile = []
      var files = e.target.files, f = files[0]
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastWarning('File excel không đúng định dạng!')
      }
      var reader = new FileReader()
      const vm = this
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, {type: 'array'})
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        // console.log(workbook);
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)

        console.log(exceldata)

        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('gannhom_upload_excel').value = null
          return false
        }

        if (!exceldata[0].hasOwnProperty('MA_TT') ||
          !exceldata[0].hasOwnProperty('NHOMCK_ID')
        ) {
          vm.$toast.error('File gồm có 2 cột: MA_TT, NHOMCK_ID')
          document.getElementById('gannhom_upload_excel').value = null
          return false
        }

        vm.dsMaTTChuaGanTheoFile = [...exceldata]
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('gannhom_upload_excel').value = null
    },

    confirmCreateDSNhom () {
      DialogObj.hide()

      let count = 0

      let donvi_id = this.$auth.getDonViID()
      // goi API tao nhom

      this.dsTaoNhomCK.forEach((item) => {
        let postData = {
          'donViId': donvi_id,
          'tenNhom': item.TEN_NHOM.trim(),
          'chungTu': ('' + item.CHUNGTU).trim(),
          'soTaiKhoan': ('' + item.SO_TK).trim(),
          'ngayTao': moment().format('DD/MM/YYYY'),
          'ghiChu': item.GHICHU.trim()
        }
        let ret = this.taoNhomChuyenKhoan(postData)
        if (ret !== false) { count++ } else { this.dsTaoNhomCKError.push(item) }
      })

      if (this.dsTaoNhomCKError.length > 0) {
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Có " + this.dsTaoNhomCKError.length + ' bản ghi cập nhật thành công. Bạn có muốn xuất các nhóm bị lỗi không?</div>',
          okButton: { text: 'Đồng ý', click: vm.confirmDownload },
          cancelButton: { text: 'Hủy', click: function () { DialogObj.hide(); return false }},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      }
    },

    async taoNhomChuyenKhoan (postData) {
      try {
        const res = await NhomChuyenKhoanAPI.taoNhomChuyenKhoan(this.axios, postData)
        return await res.data.error_code
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      }
    },
    /*
    getKyCuocHienHanh: function(){
      if(this.configs.kycuoc_hienhanh !== null)
        return
      CommonsAPI.getKyCuocHienHanh(this.axios)
      .then(
        (res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            //this.configs.kycuoc_hienhanh = res.data.data[0].KYCUOC
            this.params.p_thang = moment(this.configs.kycuoc_hienhanh, 'YYYYMMDD').toDate()
          }
        }
      )
    },
    */

    getDSTieuChiTimKiem: function () {
      GachNoAPI.getTieuChiTimKiem(this.axios)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              // console.lo
              this.params.p_options = response.data.data
              this.params.p_opt0 = this.params.p_options[0].KIEUTK_ID
              this.params.p_opt1 = this.params.p_options[1].KIEUTK_ID
              this.params.p_opt2 = this.params.p_options[2].KIEUTK_ID
            }
          }
        )
        .catch(err => {
          this.$toast.error(err.data.message_detail)
        })
        .finally(() => {})
    },

    loadThangTT () {
      /*
      let months = [];
      let monthsRequired = 36

      for (let i = 0; i <= monthsRequired; i++) {
        months.push( {
            id : moment().add(0 - i - 1, 'months').format('YYYYMM') + '01',
            text : moment().add(0 - i - 1, 'months').format('MM/YYYY')
          })
      }

      this.params.p_thangno_options = months
      */
      CommonAPI.getDSChuKyNo(this.axios)
        .then((response) => {
          if (response.data.error_code === 'BSS-00000000' &&
                response.data.data !== undefined &&
                response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              this.params.p_thangno_options.push({
                id: item.CHUKYNO,
                text: item.THANGNO
              })
            })
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },

    getNoiDungTieuChi (id) {
      if (this.params.p_options === null || this.params.p_options.length === 0) { return null }
      let ret = this.params.p_options.filter(x => x.KIEUTK_ID === id)
      if (ret !== null && ret.length > 0) { return ret[0] } else { return null }
    },

    getDSKhoanMuc: function () {
      this.params.p_khoanmuc_options = []
      this.params.p_khoanmuc = []

      CommonsAPI.getDanhSachKhoanMucGachNo(this.axios)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
            response.data.data !== undefined) {
            /*
            response.data.data.forEach( (item) => {
              //if(item.KHOANMUCTT_ID !== 3130) //&& item.SUDUNG === 1)
              {
                this.params.p_khoanmuc_options.push({KHOANMUCTT_ID: item.KHOANMUCTT_ID, TEN_KM: item.TEN_KM})
              }
            })
            */
              this.params.p_khoanmuc_options = response.data.data
            // this.params.p_khoanmuc = KHOANMUCTT_NO.KHOAN_MUC_TAM_UNG
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },

    onKhoanMucFiltering: function (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_KM', 'contains', e.text, true) : query
      e.updateData(this.params.p_khoanmuc_options, query)
    },

    onNhomCKRowChange (args) {
      if (args === null) return
      this.NAP_DS_CHITIET_TT(args)
    },
    checkAllDaGan (val) {
      if (val === '1') // check
      {
        this.$refs.gridDSMaTTDaGan.$refs.grid.ej2Instances.selectRowsByRange(0, this.dsMaTTDaGan.length - 1)
      } else // uncheck
      {
        this.$refs.gridDSMaTTDaGan.$refs.grid.ej2Instances.clearSelection()
      }
    },
    checkAllChuaGan (val) {
      if (val === '1') // check
      {
        this.$refs.gridDSMaTTDaGan.$refs.grid.ej2Instances.selectRowsByRange(0, this.dsMaTTChuaGan.length - 1)
      } else // uncheck
      {
        this.$refs.gridDSMaTTChuaGan.$refs.grid.ej2Instances.clearSelection()
      }
    }
  }, // end of methods
  provide: {
    multiselect: [CheckBoxSelection]
  },
  watch: {
    /*
    chk_chonnhanh_dagan : function(new_val, old_val)
    {
      this.checkAllDaGan(new_val)
    },
    chk_chonnhanh_chuagan : function(new_val, old_val)
    {
      this.checkAllChuaGan(new_val)
    }
    */
  }
})
</script>
