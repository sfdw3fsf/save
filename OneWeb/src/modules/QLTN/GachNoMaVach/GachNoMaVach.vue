<template src="./GachNoMaVach.html"></template>
<script>
import Vue from 'vue'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import GachNoMaVachAPI from '../api/GachNoMaVachAPI'
import TuyenThuAPI from '../api/TuyenThuAPI'
import { Query } from '@syncfusion/ej2-data'
import { DialogUtility } from '@syncfusion/ej2-popups'
import PhieuGachNoTienMat from '../GachNo/components/PhieuGachNoTienMat'
import FormDSPhieuGachNoVNP from '../GachNoDiDong_PhieuThuTienMat/components/formDSPhieuGachNoVNP.vue'
import moment from 'moment'

import {
  KIEUNO,
  HTTT_NO,
  NHOM_HTTT,
  LOAI_DV
} from '@/const/enums'

Vue.use(DialogUtility)
let DialogObj

export default {
  components: {
    BssRequiredMarker,
    appPhieuGachNoTienMat: PhieuGachNoTienMat,
    FormDSPhieuGachNoVNP
  },
  async created() {
    this.params.p_ma_nvtc = this.$auth.getMaNhanVien()

    this.check_quyen_dd()
    if (this.params.p_quyen_dd) this.getTSGachNoChuan()

    this.loading(true)
    await Promise.all([
      this.getDVQL(),
      this.getDSThuNgan(),
      this.getHTTT(),
      this.getDSDaiLy()
    ])

    this.loading(false)

    this.getDS()
  },
  mounted() {

  },
  destroyed() {
  },
  computed: {
    p_kycuoc() {
      return moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no
    },
    chk_daily() {
      return this.params.p_chk_daily === '1'
    },
    p_tong_tien: {
      get() {
        return this.params.p_tien_thietbi + this.params.p_tien_vipham
      },
      set(value) {
        this.params.p_tong_tien = value
      }
    },
    p_ngay_ttoan: {
      get() {
        return this.params.p_ngay_ttoan
      },
      set(value) {
        this.params.p_ngay_ttoan = value
      }
    },
    p_ngay_buucuc: {
      get() {
        return this.params.p_ngay_buucuc
      },
      set(value) {
        this.params.p_ngay_buucuc = value
      }
    },
    p_ngay_nganhang: {
      get() {
        return this.params.p_ngay_nganhang
      },
      set(value) {
        this.params.p_ngay_nganhang = value
      }
    },
    get_thungan() {
      let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
      if (ret.length > 0) return ret[0]
      return null
    },

    chk_kieugach() {
      return this.params.p_kieugach
    }

  },
  data() {
    return {
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      sumTemplate: function () {
        return {
          template: Vue.component('sumTemplate', {
            template: `<span>Sum: {{data.Sum}}</span>`,
            data: function () { return { data: { data: {} } } }
          })
        }
      },
      columns: [

      ],
      aggregatesColumns: [
        { field: 'DAUKY', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGTIENTRA', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ],
      params: {
        p_nhom_httt: NHOM_HTTT.GACHNO_MAVACH,
        is_gachno: false,
        p_quyen_dd: false,
        p_gachno_chuan: -1,
        // p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',
        p_kyhoadon: moment(new Date()).subtract(1, 'months').startOf('month').toDate(),
        p_chuky_no_options: [{ CHUKY: '01' }],
        p_chuky_no: '01',
        p_kieugach: 0,
        p_tiencuoc: 0,
        p_tongtien: 0,
        p_tonghoadon: 0,
        p_ma_tt: null,
        p_acc_somay: null,
        p_ma_dd: null,
        p_dvql_options: [],
        p_dvql: null,
        p_thungan_options: [],
        p_thungan: null,
        p_ma_thungan: null,
        p_quaythu_id: null,
        p_ma_tn: null,
        p_ngay_ttoan: new Date(),
        p_ngay_buucuc: new Date(),
        p_ngay_nganhang: new Date(),
        p_httt_options: [],
        p_httt: null,
        p_httt_id_neo: null,
        p_chk_daily: '0',
        p_daily_options: [],
        p_daily: null,
        p_lydo_thu: null,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_ma_nvtc: null,
        p_tuyenthu: null,
        p_ghichu: null,
        p_chungtu: null,
        is_added: false,
        is_deleted: false,
        p_seri: null,
        p_so_quyen: null,
        p_so_seri: null,
        p_tmp_ma_tt: null,
        p_count: 0,
        p_loaigach: 1,
        p_ccbs: {
          dsma_kh: '',
          dskm_ck: '',
          dstientra: '',
          dssotb: '',
          dschukyno: '',
          dskieutra: '',
          ma_tt_neo: '',
          ma_tt_lk: ''
        }
      },
      fieldsDVQL: { text: 'TEN_HIENTHI', value: 'DONVI_ID' },
      fieldsNVThuNgan: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
      fieldsDMLoaiHD: { text: 'TENTAT', value: 'LOAIHOADON_ID' },
      fieldsKyHieuHD: { text: 'SERI', value: 'SERI_ID' },
      fieldsHTTT: { text: 'HINHTHUC', value: 'HTTT_ID' },
      fieldsDaiLy: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
      dateConfig: {
        value: new Date(),
        format: 'dd/MM/y',
        max: new Date()
      },
      results: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      selectedRow: null,
      keyDSKhachHang: 1,
      max_thang_dc: moment(new Date()).subtract(1, 'months').startOf('month').toDate()
    }
  }, // end of data
  methods: {
    getKyCuoc() {
      return moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no
    },
    async check_quyen_dd() {
      if (this.$auth.getMaCCBS() !== undefined && this.$auth.getMaCCBS() !== null) this.params.p_quyen_dd = true
      else this.params.p_quyen_dd = false
      /*
      this.params.p_quyen_dd = false
      let dd = 0
      if(DONGBO_NO_DD.DONGBO)
      {
        dd = 1
      }

      await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'LAY_QUYEN_GDD'})
      .then((res) => {
        if(res.data.error_code === 'BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
          {
            let ts = parseInt(res.data.data[0].DD_TS)
            if(ts === 1)
              this.params.p_quyen_dd = true
          }
      })
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {})
      */
    },
    async getTSGachNoChuan() {
      await CommonsAPI.getDSThamSoMacDinhAdmin(this.axios, 'GACHNO_CHUAN')
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
            let ts = parseInt(res.data.data[0].GIATRI)

            this.params.p_gachno_chuan = ts

            if (this.params.p_gachno_chuan === -1) {
              this.$toast.error('Không lấy được phương thức gạch di động')
            }
          } else {
            this.$toast.error('Không lấy được phương thức gạch di động')
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.$toast.error('Không lấy được phương thức gạch di động')
        })
        .finally(() => { })
    },
    HienThiDanhSach() {

    },
    validateData() {
      let errors = []

      /*
      if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '')
      {
        errors.push('Trường mã thanh toán bắt buộc nhập')
      }
      if(this.params.p_thungan === null)
      {
        errors.push('Trường thu ngân bắt buộc nhập')
      }
      if(this.params.p_ngay_ttoan === null)
      {
        errors.push('Trường ngày TT bắt buộc nhập')
      }
      */
      if (this.params.p_httt === null) {
        errors.push('Bạn chưa chọn hình thức thanh toán không thể gạch')
      }

      if (this.results.length <= 0) {
        errors.push('Bạn chưa chọn mã thanh toán')
      }
      /// check kỳ cước có bị khóa ko
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    doPopupPhieuHuy: function () {
      this.$refs.refDanhSachPhieuTraHuy.openDialog()
    },
    async gachno(postData) {
      try {
        const response = await GachNoMaVachAPI.gachNoMaVach(this.axios, postData)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.data.length > 0) {
          if (data.data[0].status === 3) return true
          else {
            this.$toast.error(data.data[0].error)
            return false
          }
        }
        return false
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      }
    },
    async confirmGachNo() {
      DialogObj.hide()

      let postData = {
        kyCuoc: this.getKyCuoc(),
        chuKyNo: null,
        maTT: '',
        thanhToanId: 0,
        loai: this.params.p_kieugach === '1' ? KIEUNO.NO_DAU_KY : KIEUNO.NO_PHAT_SINH,
        gachDD: 1,
        maTN: this.params.p_ma_thungan,
        maNVTC: this.params.p_ma_nvtc,
        ngayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        htttId: this.params.p_httt,
        donViThuId: this.params.p_dvql,
        pNguoiDungId: this.$auth.getNguoiDungID(),
        quayThuId: this.params.p_quaythu_id,
        thuNganId: this.params.p_thungan,
        seri: 0,
        quyen: 0,
        soSeri: 0,
        chungTu: this.params.p_chungtu !== null ? ('' + this.params.p_chungtu).trim() : null,
        ghiChu: this.params.p_ghichu !== null ? ('' + this.params.p_ghichu).trim() : null
      }

      // let result = false

      this.loading(true)

      if (this.params.p_quyen_dd) {

      }

      let promises = []

      this.results.forEach((item) => {
        postData.chuKyNo = item.CHUKYNO
        postData.maTT = item.MA_TT
        postData.thanhToanId = item.THANHTOAN_ID
        postData.seri = item.SERI
        postData.quyen = item.SOQUYEN
        postData.soSeri = item.SOSERI

        promises.push(this.gachno(postData))
      })

      try {
        this.loading(true)

        const ret = await Promise.all(promises)
        let ok = ret.filter(x => x === true).length

        if (ok !== 0) {
          this.$toast.success('Đã gạch thành công ' + ok + '/' + ret.length)
        }
        this.Clear()
        this.getDS()
      } catch (error) {

      } finally {
        this.loading(false)
      }
    },
    doGachNo: function () {
      if (!this.validateData()) {
        return false
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có chắc chắn muốn gạch nợ hay không ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmGachNo },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    confirmDelete() {
      let selectedRows = this.$refs.gridGachNoMaVach.getSelectedRecords()
      let postData = {
        ds: []
      }

      DialogObj.hide()

      if (selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn trong danh sách để xoá.')
        return
      }

      selectedRows.forEach(item => {
        postData.ds.push({
          'maTT': item.MA_TT,
          'chuKyNo': item.CHUKYNO// this.getKyCuoc()
        })
      })

      this.loading(true)
      GachNoMaVachAPI.xoaKhoiDanhSach(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000') {
            this.$toast.success('Xóa phiếu thành công')
            this.getDS()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    doDelete() {
      if (this.results.length <= 0) {
        this.$toast.error('Không có phiếu để xóa!')
        return
      }
      let selectedRows = this.$refs.gridGachNoMaVach.getSelectedRecords()

      if (selectedRows.length <= 0) {
        this.$toast.error('Bạn chưa chọn phiếu để xóa!')
        return
      }

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn thật sự muốn xóa danh sách mã thanh toán đã chọn không ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmDelete },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },

    async getDVQL() {
      try {
        await CommonsAPI.getDanhSachDonVi2(this.axios, { loaiDVId: LOAI_DV.DONVIQL_TT }).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.params.p_dvql_options = res.data.data
              // this.params.p_dvql = res.data.data[0].DONVI_ID
            } else {
              this.params.p_dvql_options = []
              this.params.p_dvql = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async getDSDaiLy() {
      this.params.p_nhanvienthu_options = []
      this.params.p_nhanvienthu = null

      TuyenThuAPI.getDanhSachNVTC(this.axios, { pNguoiDungId: this.$auth.getNguoiDungID(), pageNo: 0, pageSize: 100 })
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.data !== undefined &&
              response.data.data.data.length > 0) {
              // let items = []
              if (response.data.data !== undefined) {
                this.params.p_daily_options = response.data.data.data
                this.params.p_daily = response.data.data.data[0].NHANVIEN_ID
              }
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
        })
    },
    async getHTTT() {
      try {
        await GachNoMaVachAPI.getHTTTMaVach(this.axios).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.params.p_httt_options = res.data.data
              this.params.p_httt = HTTT_NO.DAILY_BC_TAINHA
            } else {
              this.params.p_httt_options = []
              this.params.p_httt = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    async getDSThuNgan() {
      try {
        await GachNoAPI.getDSThuNgan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].NHANVIEN_ID
              this.params.p_quaythu_id = response.data.data[0].DONVI_ID
              this.getDonViQuanLy()
            } else {
              this.params.p_thungan_options = []
              this.params.p_thungan = null
              this.params.p_dvql = null
            }
          }
        )
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    onThuNganChange: function (args) {
      let postData = {
        kyCuoc: this.getKyCuoc(),
        maTN: args.value
      }
      this.fetchDanhSachKH(postData)
    },
    async getThongTinTTTheoMaVach(postData) {
      try {
        const results = await GachNoMaVachAPI.getThongTinTTTheoMaVach(this.axios, postData)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      } catch (err) {
        this.$toast.error(err.data.message_detail)
        this.params.p_ma_tt = null
        return null
      }
    },

    async doSearch() {
      // this.getDS()
    },

    async getThongTinTT() {
      if (this.params.p_ma_tt === undefined || this.params.p_ma_tt.trim() === '') {
        this.$toast.error('Trường mã vạch bắt buộc nhập.')
        return false
      }

      let vkieuno = KIEUNO.NO_PHAT_SINH
      if (this.params.p_kieugach === '1') vkieuno = KIEUNO.NO_DAU_KY

      let postData = {
        htttId: this.params.p_httt,
        maVach: this.params.p_ma_tt.trim(),
        KyCuoc: this.getKyCuoc(),
        kieuTra: vkieuno
      }

      this.loading(true)

      const res = await this.getThongTinTTTheoMaVach(postData)
      this.loading(false)

      if (res !== null && res.length > 0) {
        // this.params.p_tmp_ma_tt = res[0].MA_TT
        // this.params.p_ten_tt = res[0].TEN_TT
        // this.params.p_diachi_tt = res[0].DIACHI_TT
        // this.params.p_ma_dd = res[0].MATB_DD
        // this.params.p_ma_nvtc = res[0].MA_NVTC
        // this.params.p_tuyenthu = res[0].MATB_DD

        // await this.getMaTuyen(res[0].TUYENTHU_ID)

        if (res[0].NHANVIEN_ID !== null) {
          if (this.$auth.getMaTinh() === 'HPG') {
            if (this.params.p_chk_daily === '1' && this.params.p_daily !== res[0].NHANVIEN_ID) {
              DialogObj = DialogUtility.confirm({
                title: 'Thông báo',
                content: "<div style='padding:10px'>Bạn đang đọc mã vạch của mã đường thu khác. Bạn có muốn tiếp tục ?</div>",
                okButton: { text: 'Đồng ý', click: this.appendList(res) },
                cancelButton: { text: 'Hủy' },
                showCloseIcon: true,
                closeOnEscape: true,
                animationSettings: { effect: 'Zoom' }
              })
            } else {
              this.appendList(res)
            }
          } else {
            this.appendList(res)
          }
        }

        this.Clear()
      } else if (res !== null && res.length === 0) {
        this.$toast.error('Không tìm thấy thông tin khách hàng.')
        this.params.p_ma_tt = null
      }
    },

    appendList(res) {
      this.results = [...res, ...this.results]
      this.pageinfo.totalElement = this.results.length
      this.pageinfo.page = 0

      this.params.p_tongtien = this.results.reduce((acc, curr) => {
        return Number(curr.TONGTIENTRA) + acc
      }, 0)

      this.params.p_tonghoadon = this.results.length
      if (this.params.p_chk_daily === '1' && this.params.p_daily !== res[0].NHANVIEN_ID) this.params.p_daily = res[0].NHANVIEN_ID
    },

    getDS() {
      let vkieuno = KIEUNO.NO_PHAT_SINH
      if (this.params.p_kieugach === '1') vkieuno = KIEUNO.NO_DAU_KY

      let postData = {
        kyCuoc: this.getKyCuoc(),
        maNVTC: this.params.p_ma_nvtc,
        kieuNo: vkieuno
      }

      this.fetchDanhSach(postData)
    },

    fetchDanhSach: function (postData) {
      this.loading(true)

      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0
      this.pageinfo.totalPages = 0
      this.params.p_tonghoadon = 0
      this.params.p_tongtien = 0

      this.getDanhSachGachMaVach(postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.keyDSKhachHang++
              this.results = res.data.data
              this.pageinfo.page = 0
              this.pageinfo.totalElement = res.data.data.length
              this.params.p_tonghoadon = res.data.data.length
              this.params.p_tongtien = this.results.reduce((acc, curr) => {
                return Number(curr.TONGTIENTRA) + acc
              }, 0)
              this.params.is_gachno = true
            } else {
              // this.params.p_ma_nvtc = null
              this.params.p_ma_dd = null
              this.params.p_tuyenthu = null
              this.params.p_ngay_buucuc = new Date()
              this.params.p_ngay_ttoan = new Date()
              this.params.p_ngay_nganhang = new Date()
              this.params.is_gachno = false
            }
          }
        )
        .catch(error => {
          if (error.data !== undefined) this.$toast.error(error.data.message_detail)
          else this.$toast.error(error)
        })
        .finally(
          () => { this.loading(false) }
        )
    },

    doListPhieuTra() {
      this.$refs.refPhieuGachNoTienMat.openDialog()
    },

    doListVNP() {
      this.$bvModal.show('modal-dsphieugachnovnp')
    },

    Clear() {
      // sma_tt = "";
      this.params.p_tien_cuoc = null
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
      this.params.p_ma_tt = null
      this.$refs.p_ma_tt.focus()
      // this.params.p_ma_nvtc = null
      this.params.p_ma_dd = null

      this.params.p_tuyenthu = null
      this.params.p_seri = null
      this.params.p_so_quyen = null
      this.params.p_so_seri = null
    },

    dataBoundHandler() {
      if (this.results.length <= 0) return

      let index = 0

      if (this.params.p_tmp_ma_tt !== null) {
        index = this.results.findIndex(x => x.MA_TT === this.params.p_tmp_ma_tt)
      }

      if (index > -1) { // tìm thấy trong dnah sách
        this.selectRow = this.results[index]
        let page = parseInt(index / this.pageinfo.maxSize)

        this.$refs.gridGachNoMaVach.$refs.pagination.gotoPage(page)
        let offset = index % this.pageinfo.maxSize
        // console.log(offset)
        this.$refs.gridGachNoMaVach.$refs.grid.selectRow(offset)

        this.pageinfo.currentRow = offset
        this.params.p_tmp_ma_tt = null
      }
    },

    async getDanhSachGachMaVach(postData) {
      try {
        const results = await GachNoMaVachAPI.getDSGachMaVach(this.axios, postData)
        return results
      } catch (err) {
        console.log(err.data.message_detail)
      }
    },

    async getDVQLTheoQuayThu(postData) {
      try {
        const results = await GachNoMaVachAPI.getDVQLTheoQuayThu(this.axios, postData)
        return results
      } catch (err) {
        console.log(err.data.message_detail)
      }
    },

    doAdd: function () {
      this.params.is_added = true
      this.params.is_deleted = false
      this.params.p_lydo_thu = 'Khách hàng bồi hoàn hợp đồng do vi phạm cam kết'
    },

    doHuy: function () {
      this.params.is_added = false
      this.clearForm()
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

    doExportExcel() {
      if (this.results.length <= 0) {
        this.$toast.error('Chưa có danh sách để xuất Excel')
        return
      }
      let excelExportProperties = {
        dataSource: this.results
      }
      this.$refs.gridGachNoMaVach.$refs.grid.excelExport(excelExportProperties)
    },
    getDonViQuanLy: function () {
      try {
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT
        }
        GachNoAPI.getDonViQuanLy(this.axios, postData).then(
          (response) => {
            /// console.log(response)
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_dvql = response.data.data[0].DONVI_ID

              if (this.params.p_dvql === '0' &&
                this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO) {
                DialogUtility.alert({
                  title: 'Thông báo',
                  content: "<div style='padding:10px'>User của bạn không được phép thao tác trong chức năng này</div>",
                  okButton: { text: ' OK ', click: function () { this.$router.push('/') } },
                  showCloseIcon: true,
                  closeOnEscape: true,
                  animationSettings: { effect: 'Zoom' }
                })
                return false
              }
            } else this.$toast.error(response.data.message)
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    onFilteringDVQL(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.params.p_dvql_options, query)
    },
    onFilteringHTTT(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('HINHTHUC', 'contains', e.text, true) : query
      e.updateData(this.params.p_httt_options, query)
    },
    onFilteringThuNgan(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query
      e.updateData(this.params.p_thungan_options, query)
    },
    onFilteringDaiLy(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query
      e.updateData(this.params.p_thungan_options, query)
    },
    selectedRowChanged(e) {
      // this.selectedRow = e
    },
    recordClickHandler(e) {
      // console.log(e)
      this.selectedRow = e.rowData
    },
    changeThuNgan(e) {
      let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === e.value)
      if (ret === null || ret.length <= 0) return
      this.params.p_ma_thungan = ret[0].MA_NV
      this.params.p_quaythu_id = ret[0].DONVI_ID
      this.getDonViQuanLy()
    },
    async onKyHoaDonChange(args) {
      if (args.value === null) {
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      await this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))
    },
    async updateChuKyHoaDon(pKyHoaDon) {
      // this.loading(true)
      this.p_chuky_no_options = []
      this.p_chuky_no = null

      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.length > 0) {
              this.params.p_chuky_no_options = response.data.data
              this.params.p_chuky_no = this.params.p_chuky_no_options[0].CHUKY
            }
          }
        )
        .catch(() => { })
        .finally(() => {
          // this.loading(false)
        })
    },
    async getHTTTNeo() {
      let postData = {
        'tenTruong': 'HTTT_ID_NEO',
        'schema': 'qltn',
        'tenBang': 'hinhthuc_tt_neo',
        'dieuKien': 'httt_id_vtt = ' + this.params.p_httt
      }
      await CommonsAPI.getMapID(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
            let kq = parseInt(res.data.data[0].KETQUA)
            this.params.p_httt_id_neo = kq
          } else {
            let f = this.params.p_httt_options.filter(x => x.HTTT_ID === this.params.p_httt)
            this.$toast.error('Hình thức thanh toán ' + f[0].HINHTHUA + ' chưa có trên VNP. Hãy kiểm tra lại')
            return false
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          return false
        })
        .finally(() => { })
    },
    async dongboCCBS() {
      if (this.params.p_gachno_chuan === 0) {

      }
    },
    async getMaTuyen(tuyenThuId) {
      let postData = {
        'tenTruong': 'MA_TUYEN',
        'schema': 'css',
        'tenBang': 'TUYENTHU',
        'dieuKien': 'tuyenthu_id = ' + tuyenThuId
      }
      await CommonsAPI.getMapID(this.axios, postData)
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
            let kq = res.data.data[0].KETQUA
            this.params.p_tuyenthu = kq
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          return false
        })
        .finally(() => { })
    }
  },

  watch: {

    chk_kieugach: function (newVal, oldVal) {
      this.getDS()
    }
  }

}
</script>
