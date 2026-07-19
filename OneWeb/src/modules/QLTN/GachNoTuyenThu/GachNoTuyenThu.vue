<template src="./GachNoTuyenThu.html"></template>
<style scoped src="./GachNoTuyenThu.scss"></style>

import {numeric, required} from 'vuelidate/lib/validators'
<script>
import Vue from 'vue'
import moment from 'moment'
import { mapActions, mapState } from 'vuex'
import KyCuoc from '@/components/KyCuoc2'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import GachNoTuyenThuAPI from '../api/GachNoTuyenThu'
import TuyenThuAPI from '../api/TuyenThuAPI'
import { Query } from '@syncfusion/ej2-data'
import TimNhanVienThuCuoc from '../GachNo/components/TimNhanVienThuCuoc'
import { DialogUtility } from '@syncfusion/ej2-popups'
import TimThanhToan from './components/TimThanhToan'

import { required } from 'vuelidate/lib/validators'
import XLSX from 'xlsx'

import {
  NHOM_HTTT,
  LOAI_DV,
  DichVuVienThong
} from '@/const/enums'

Vue.use(DialogUtility)
let DialogObj

export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc,
    appTimNhanVienThuCuoc: TimNhanVienThuCuoc,
    appTimThanhToan: TimThanhToan
  },
  async created () {
    this.params.p_kycuoc = moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate() // localStorage.getItem('KEY_KYCUOC_HT') //localStorage.setItem('KEY_KYCUOC_HT', this.p_kyhoadon);
    // if (kycuocht === undefined || kycuocht === null) {
    //   await this.getKyHoaDonHienTai()
    //   if (this.kyHoaDonHienTai !== undefined) {
    //     this.params.p_kycuoc = moment(this.kyHoaDonHienTai.KYCUOC, 'YYYYMMDD').toDate()
    //     localStorage.setItem('KEY_KYCUOC_HT', this.params.p_kycuoc)
    //   }
    // }

    this.getQuyenGachDiDong()

    if (this.params.p_quyen_dd) {
      await this.getTSGachNoChuan()
    }

    try {
      this.loading(true)

      const res = await Promise.all([
        this.getThamSo('BATBUOC_NHAP_CHUNGTU_GACHNO'),
        this.getThamSo('GACHNO_TAMTHU'),
        this.getThamSo('HIENTHI_ALL'),
        this.getThamSoGachNo(),
        this.getDSDaiLy(),
        this.getDSThuQuy(),
        this.getDSHTTT()
      ])

      this.loading(false)

      if (res[0] === 1) this.params.p_BATBUOC_NHAP_CHUNGTU_GACHNO = 1
      if (res[1] === 1) this.params.p_gachtamthu = true
      if (res[2] === 1) this.params.p_hienthi_all = true
      if (res[3].length > 0) {
        this.params.p_ngaygach_lui_thang = (res[3][0].NGAY_GL !== undefined && res[3][0].NGAY_GL !== null) ? parseInt(res[3][0].NGAY_GL) : 0
        this.params.p_ngaygach_tien = (res[3][0].NGAY_GT !== undefined && res[3][0].NGAY_GT !== null) ? parseInt(res[3][0].NGAY_GL) : 0 // parseInt(res[3][0].NGAY_GT)
        this.params.p_ngayxoa_lui = (res[3][0].NGAY_XL !== undefined && res[3][0].NGAY_XL !== null) ? parseInt(res[3][0].NGAY_XL) : 0 // parseInt(res[3][0].NGAY_XL)
        this.params.p_ngaygach_lui_thanght = (res[3][0].NGAY_GLT !== undefined && res[3][0].NGAY_GLT !== null) ? parseInt(res[3][0].NGAY_GLT) : 0  // parseInt(res[3][0].NGAY_GLT)
      }
    } catch (error) {

    } finally {

    }
  },
  computed: {
    ...mapState('qltnCommon', [
      'kyHoaDonHienTai'
    ]),
    p_ngay_ttoan: {
      get () {
        return this.params.p_ngay_ttoan
      },
      set (value) {
        this.params.p_ngay_ttoan = value
      }
    },
    get_thungan () {
      let ret = this.params.p_thuquy_options.filter(x => x.NHANVIEN_ID === this.params.p_thuquy)
      if (ret.length > 0) return ret[0]
      return null
    }
  },
  data () {
    return {
      selectionOptions: { type: 'Multiple', checkboxOnly: true },
      format: {
        format: 'N0', useGrouping: true
      },
      hideTable: true,
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsNVTC: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
        fieldsLoaiTien: { text: 'TENLOAI', value: 'LOAITIEN_ID' },
        fieldsDichVu: { text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsThuQuy: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
        fieldsHTTT: { text: 'HTTT', value: 'HTTT_ID' },
        fieldsLuotTT: { text: 'LANTRA', value: 'LANTRA' },
        fieldsLuotGiao: { text: 'GIAOPHIEU_ID', value: 'GIAOPHIEU_ID' },
        columns: []
      },
      params: {
        p_quyen_dd: false,
        luotgiao: {
          id: '',
          text: ''
        },
        p_kycuoc: null,
        p_nvtc_options: [],
        p_nvtc: null,
        p_loaitien_options: [],
        p_loaitien: null,
        p_dichvu_options: [
          // {DICHVUVT_ID: 1, TEN_DVVT: 'Cố định, GPhone, Internet'},
          // {DICHVUVT_ID: DichVuVienThong.DI_DONG, TEN_DVVT: 'Di động'}
          {DICHVUVT_ID: DichVuVienThong.TATCA, TEN_DVVT: 'Tất cả'},
          {DICHVUVT_ID: DichVuVienThong.CO_DINH, TEN_DVVT: 'Cố định'},
          {DICHVUVT_ID: DichVuVienThong.DI_DONG, TEN_DVVT: 'Di động'},
        ],
        p_dichvu: DichVuVienThong.CO_DINH,
        p_dichvu_khacdidong: '1,3,4,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,50',
        p_thuquy_options: [],
        p_thuquy: null,
        p_httt_options: [],
        p_httt: null,
        p_ngay_ttoan: new Date(),
        p_chungtu: null,
        p_ghichu: null,
        p_luottt_options: [],
        p_luottt: null,
        p_luotgiao_options: [],
        // p_luotgiao: null,
        p_timkiem: null,
        p_so_phieu: 0,
        p_phieu_id: 0,
        p_phieu_id_bd: 0,
        p_phieu_id_kt: 0,
        p_ma_tn: null,
        p_donviqlno_id: 0,
        p_quaythu_id: 0,
        p_kieugach: 0, // 0: Gạch theo mã nhân viên thu cước, 1: Gạch theo tuyến thu
        p_ngaygach_lui_thang: 0,
        p_ngaygach_tien: 0,
        p_ngayxoa_lui: 0,
        p_ngaygach_lui_thanght: 0,
        p_BATBUOC_NHAP_CHUNGTU_GACHNO: -1,
        p_gachtamthu: false,
        p_hienthi_all: false,
        p_ma_nvtc: null
      },
      results: [],
      rowsSelected: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      uploadedMTT: []
    }
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getKyHoaDonHienTai'
    ]),
    getQuyenGachDiDong () {
      if (this.$auth.getMaCCBS() !== undefined && this.$auth.getMaCCBS() !== null) this.params.p_quyen_dd = true
      else this.params.p_quyen_dd = false
    },
    async getTSGachNoChuan () {
      await CommonsAPI.getDSThamSoMacDinhAdmin(this.axios, 'GACHNO_CHUAN')
        .then((res) => {
          if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
            let ts = parseInt(res.data.data[0].DD_TS)
            if (ts === 1) this.params.p_quyen_dd = true
            else if (ts === -1) {
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
        .finally(() => {})
    },
    clear () {
      this.params.p_ngay_ttoan = new Date()
      this.results = []
      this.rowsSelected = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0
      this.params.p_chungtu = ''
      this.params.p_ghichu = ''
      this.params.p_phieu_id = 0
      this.params.p_phieu_id_bd = 0
      this.params.p_phieu_id_kt = 0
      this.params.p_nvtc = -1
    },

    async getThamSo (tenTS) {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: tenTS})
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          return parseInt(data.data[0].TEN_TS)
        } else return 0
      } catch (err) {
        return 0
      }
    },

    async getDSDaiLy () {
      this.params.p_nvtc_options = []
      this.params.p_nvtc = null

      try {
        const res = await TuyenThuAPI.getDanhSachNVTC(this.axios, {pNguoiDungId: this.$auth.getNguoiDungID(), pageNo: 0, pageSize: 100})
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data.data !== undefined && data.data.data.length > 0) {
          this.params.p_nvtc_options = data.data.data
          this.params.p_nvtc = data.data.data[0].NHANVIEN_ID
        }
      } catch (error) {
        console.log(error)
      }
    },
    async getDSLoaiTien () {
      try {
        await GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: this.params.p_kycuoc}).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined) {
              this.params.p_loaitien_options = response.data.data
              this.params.p_loaitien = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    async getDSDichVu () {
      await CommonsAPI.getDanhSachDichVuVienThong(this.axios)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              let arr = []
              res.data.data.forEach((item) => {
                if (item.DICHVUVT_ID !== DichVuVienThong.DI_DONG)
                  arr.push(item.DICHVUVT_ID)
              })
              this.params.p_dichvu_khacdidong = arr.join(',')
            } else {
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },
    async getDSHTTT () {
      await CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: 1, nhomHTTT: NHOM_HTTT.GACHNO_DAILY})
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
              this.params.p_httt_options = res.data.data
              this.params.p_httt = res.data.data[0].HTTT_ID
            } else {
              this.params.p_httt_options = []
              this.params.p_httt = null
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
    },
    async getDonViQuanLy () {
      try {
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + ',' + LOAI_DV.DONVIQL_TT_BUUDIEN
        }

        const res = await GachNoAPI.getDonViQuanLy(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined) {
          this.params.p_donviqlno_id = data.data[0].DONVI_ID
          if (this.params.p_donviqlno_id === '0') {
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
        }
      } catch (error) {
        console.log(error)
      }
    },
    async getThamSoGachNo () {
      try {
        const res = await GachNoAPI.getThamSoGachNo(this.axios)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) return data.data
        else return []
      } catch (err) {
        console.log(err)
        return []
      }
      /*
      try{
          GachNoAPI.getThamSoGachNo(this.axios).then(
            (response) => {
              //console.log(response)
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
              {
                this.params.p_ngaygach_lui_thang = parseInt(response.data.data[0].NGAY_GL)
                this.params.p_ngaygach_tien = parseInt(response.data.data[0].NGAY_GT)
                this.params.p_ngayxoa_lui = parseInt(response.data.data[0].NGAY_XL)
                this.params.p_ngaygach_lui_thanght = parseInt(response.data.data[0].NGAY_GLT)
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          this.$toast.error(error)
      }
      */
    },
    async getDSThuQuy () {
      try {
        const res = await GachNoTuyenThuAPI.getDSThuQuy(this.axios)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined) {
          this.params.p_thuquy_options = data.data
          this.params.p_thuquy = data.data[0].NHANVIEN_ID

          this.params.p_quaythu_id = data.data[0].DONVI_ID
          await this.getDonViQuanLy()
        } else {
          this.params.p_thuquy_options = []
          this.params.p_thuquy = null
        }
      } catch (error) {
        console.log(error)
      }
    },
    async getDSLuotThanhToan (luotgiao) {
      try {
        let postData = {
          kyCuoc: this.params.p_kycuoc,
          phieuGiaoId: luotgiao
        }
        await GachNoTuyenThuAPI.getDSLuotTraTheoPhieuGiao(this.axios, postData).then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              this.params.p_luottt_options = res.data.data
              // this.params.p_luottt = res.data.data[0].TUYENTHU_ID
            } else {
              this.params.p_luottt_options = []
              this.params.p_luottt = null
            }
          }
        )
      } catch (error) {
        // this.$toast.error(error)
      }
    },
    async getDSLuotGiao (nvtcID) {
      let postData = {
        kyCuoc: this.params.p_kycuoc,
        nvTCId: nvtcID,
        kieu: 0,
        lanTaoId: null
      }
      this.params.p_luotgiao_options = []
      this.params.luotgiao.id = null
      this.params.luotgiao.text = null
      this.loading(true)
      await GachNoTuyenThuAPI.getDSLuotGiao(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
            res.data.data !== undefined &&
            res.data.data.length > 0) {
              this.params.p_luotgiao_options = res.data.data
              // this.params.p_luotgiao = res.data.data[0].TUYENTHU_ID
            } else {
              this.params.p_luotgiao_options = []
              // this.params.p_luotgiao = null
              this.results = []
              this.pageinfo.totalElement = 0
              this.pageinfo.page = 0
            }
          }
        )
        .catch(err => {
          this.$toast.error(err.data.message_detail)
        })
        .finally(() => {this.loading(false)})
    },
    getDSChiTietNoPS (postData) {
      this.loading(true)
      GachNoTuyenThuAPI.getDSChiTietNoPS(this.axios, postData)
        .then(
          (res) => {
            console.log(res)
          }
        )
        .finally(() => { this.loading(false) })
    },
    getDanhSach () {
      let errors = []
      if (this.params.luotgiao.id === null) {
        errors.push('Vui lòng chọn lượt giao')
      }
      if (this.params.p_dichvu === null) {
        errors.push('Vui lòng chọn dịch vụ')
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      let dichvuvt = ''
      let kieugach = 0
      let ds_tong = []
      let ds_tong_neo = null

      if (parseInt(this.params.p_dichvu) === DichVuVienThong.DI_DONG) {
        dichvuvt = this.params.p_dichvu
      } else if (parseInt(this.params.p_dichvu) !== DichVuVienThong.TATCA) {
        dichvuvt = DichVuVienThong.CO_DINH
      } else {
        dichvuvt = DichVuVienThong.TATCA
      }

      // this.getDSChiTietNoPS(postData)

      let postData = {
        kyCuoc: this.params.p_kycuoc,
        giaoPhieuId: this.params.luotgiao.id,
        dichVuVTId: dichvuvt
      }

      this.loading(true)
      GachNoTuyenThuAPI.getDSGachNoDaiLy(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              /*
                this.results = []
                let arr = []
                res.data.data.forEach((item, index) => {
                  let row = item
                  row.CHECKED = !1
                  if(row.TIENSETRA == row.TIEN_HD)
                    row.CHECKED = !0
                  arr.push(row)
                })
                //this.results = res.data.data
                this.results = [...arr]
                */
              // var gridObj = this.$refs.gridDSHoaDon.$refs.grid.ej2Instances
              // console.log(gridObj)

              this.results = res.data.data
              this.rowsSelected = []

              // var gridObj = this.$refs.gridDSHoaDon.ej2Instances
              // console.log(gridObj)

              this.pageinfo.totalElement = res.data.data.length
              // this.pageinfo.totalPages =
              this.pageinfo.page = 0
              // gridObj.dataSource = res.data.data
              // console.log(gridObj)
              // this.params.p_luotgiao = res.data.data[0].TUYENTHU_ID
            } else {
              this.results = []
              this.rowsSelected = []
              this.pageinfo.totalElement = 0
              this.pageinfo.page = 0
            }
          }
        )
        .finally(() => { this.loading(false) })
    },
    validateSaveDS: function () {
      // this.$v.$touch();

      let errors = []

      let selectedArr = this.$refs.gridDSHoaDon.getSelectedRecords()
      // console.log(selectedArr)
      if (selectedArr.length <= 0) {
        this.$toast.error('Hãy chọn phiếu để lưu!')
        return false
      }

      if (this.params.p_chungtu === null || (this.params.p_chungtu !== null && this.params.p_chungtu.trim() === '')) {
        errors.push('Trường chứng từ bắt buộc nhập')
      }
      if (this.params.p_ghichu === null || (this.params.p_ghichu !== null && this.params.p_ghichu.trim() === '')) {
        errors.push('Trường ghi chú bắt buộc nhập')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSave () {
      if (!this.validateSaveDS()) return

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn thật sự muốn lưu danh sách đang thực hiện không ?</div>",
        okButton: { text: 'Đồng ý', click: this.confirmSave },
        cancelButton: { text: 'Hủy' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },
    confirmSave () {
      let selectedArr = this.$refs.gridDSHoaDon.getSelectedRecords()
      console.log(selectedArr)
      let ds = []
      selectedArr.forEach((item, index) => {
        ds.push(
          {
            'maTT': item.MA_TT,
            'maTB': item.MA_TB,
            'thanhToanId': item.THANHTOAN_ID,
            'tienSeTra': item.TIENSETRA,
            'seri': item.SERI,
            'quyen': item.QUYEN,
            'soSeri': item.SO_SERI
          }
        )
      })
      let postData = {
        'kyCuoc': this.params.p_kycuoc,
        // "dichVuVTId": this.params.p_dichvu,
        'lanTra': 0,// this.params.p_luottt,
        'ghiChu': this.params.p_ghichu === null ? null : this.params.p_ghichu.trim(),
        'chungTu': this.params.p_chungtu,
        'ds': ds,
        'thuNganId': this.params.p_thuquy,
        'htttId': this.params.p_httt,
        'ngayTT': moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
      }
      this.loading(true)
      GachNoTuyenThuAPI.gachNoDSTuyenThu(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000') {
              this.$toast.success('Lưu danh sách thành công!')
              this.getDanhSach()
            }
          }
        )
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false); DialogObj.hide()})
    },
    validateData () {
      let errors = []
      if (this.params.p_chungtu === null || this.params.p_chungtu.trim() === '') {
        errors.push('Chứng từ bắt buộc nhập.')
      }
      if (this.params.p_ghichu === null || this.params.p_ghichu.trim() === '') {
        errors.push('Ghi chú bắt buộc nhập.')
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    doGachNo () {
      this.GachNo()
      /*
      if(this.params.p_dichvu !== DichVuVienThong.DI_DONG && this.params.p_dichvu !== DichVuVienThong.TATCA)
      {
        //
        this.GachNo()
      } else
      {
        if(this.$auth.getMaCCBS() !== null)
        {
          // GACHNO_DONGBO();
        } else
        {
          this.$toast.error('Bạn không có quyền gạch nợ di động không thể sử dụng chức năng')
          return false
        }
      }
      */
    },
    GachNo () {
      let luot_tt = 1

      if (this.params.p_luottt_options.length > 0) {
        // this.$toast.error('Chưa chọn lượt thanh toán')
        if (this.params.p_luottt === null)
          luot_tt = parseInt(this.params.p_luottt_options[this.params.p_luottt_options.length - 1].LANTRA) + 1
        else
          luot_tt = parseInt(this.params.p_luottt)
      }
      this.params.p_phieu_id = 0
      this.params.p_phieu_id_bd = 0
      this.params.p_phieu_id_kt = 0

      let selectedArr = this.$refs.gridDSHoaDon.getSelectedRecords()
      if (selectedArr.length <= 0) {
        this.$toast.error('Hãy chọn lượt thanh toán trong danh sách.')
        return false
      }

      if (!this.validateData()) return false
      let ds = []
      selectedArr.forEach((item, index) => {
        if (item.TIENSETRA !== 0) {
          ds.push(
            {
              'maTT': item.MA_TT,
              'maTB': item.MA_TB,
              'thanhToanId': item.THANHTOAN_ID,
              'tienSeTra': item.TIENSETRA,
              'seri': item.SERI,
              'quyen': item.QUYEN,
              'soSeri': item.SO_SERI,
              'dichVuVTId': this.params.p_dichvu !== DichVuVienThong.DI_DONG ? this.params.p_dichvu_khacdidong : '' + this.params.p_dichvu
            }
          )
        }
      })

      let postData = {
        'kyCuoc': this.params.p_kycuoc,
        // "dichVuVTId": this.params.p_dichvu,
        'lanTra': luot_tt,
        'ghiChu': this.params.p_ghichu === null ? null : this.params.p_ghichu.trim(),
        'chungTu': this.params.p_chungtu,
        'ds': ds,
        'thuNganId': this.params.p_thuquy,
        'htttId': this.params.p_httt,
        'ngayTT': moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')
      }

      if (postData.ds.length <= 0) {
        this.$toast.error('Hãy chọn hoá đơn có tiền trả > 0')
        return
      }
      this.loading(true)
      GachNoTuyenThuAPI.gachNoDSTuyenThu(this.axios, postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000') {
              this.$toast.success('Gạch nợ thành công!')
              this.getDanhSach()
            }
          }
        )
        .catch((error) => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    doDSPhieuThu: function () {
      // this.$refs.refPhieuGachNoTienMat.openDialog()
      // this.$router.push({ name: 'DanhSachPhieuDaGachNo' });
      let ret = this.params.p_thuquy_options.filter(x => x.NHANVIEN_ID === this.params.p_thuquy)
      let thungan = {}
      if (ret.length > 0) thungan = ret[0]

      // this.$router.replace({ name: "DanhSachPhieuDaGachNo", params: {}, query: {thungan_id: this.params.p_thungan, ma_thungan: thungan.MA_NV, ten_thungan: thungan.TEN_NV, ngay_tt: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')} })
      let routeData = this.$router.resolve({name: 'DanhSachPhieuDaGachNo', query: {thungan_id: this.params.p_thungan, ma_thungan: thungan.MA_NV, ten_thungan: thungan.TEN_NV, ngay_tt: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY')}})
      window.open(routeData.href, '_blank')
    },
    doSearch: function () {
      this.$refs.refTimThanhToan.openDialog()
    },
    doReadFile: function () {
      document.getElementById('gachno_upload_excel').click()
    },
    doCancel () {
      this.clear()
    },
    uploadFile: function (e) {
      let files = e.target.files
      let f = files[0]
      this.rowsSelected = []
      if (!files.length) {
        document.getElementById('gachno_upload_excel').value = null
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        document.getElementById('gachno_upload_excel').value = null
        return this.$root.toastWarning('File excel không đúng định dạng!')
      }
      var reader = new FileReader()
      const vm = this
      reader.onload = function (e) {
        var data = new Uint8Array(e.target.result)
        var workbook = XLSX.read(data, {type: 'array'})
        let sheetName = workbook.SheetNames[0]
        let worksheet = workbook.Sheets[sheetName]
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        if (exceldata.length <= 0) {
          vm.$toast.error('Không tìm thấy dữ liệu trong file')
          document.getElementById('gachno_upload_excel').value = null
          return
        }
        if (!exceldata[0].hasOwnProperty('MA_TT')) {
          vm.$toast.error('File không đúng định dạng. Phải có cột MA_TT!')
          document.getElementById('gachno_upload_excel').value = null
          return
        }
        // console.log(vm.$refs.gridDSHoaDon.$refs.grid)
        let gridObj = vm.$refs.gridDSHoaDon.$refs.grid
        let arrIndex = []
        let errors = []
        exceldata.forEach((item, index) => {

          vm.rowsSelected.push(item.MA_TT)

          let idx = gridObj.getRowIndexByPrimaryKey(item.MA_TT)
          // console.log(item.MA_TT, idx)
          if (idx >= 0) {
            let data = gridObj.getRowByIndex(idx)
            if (data !== null && data.CONNO !== data.TIEN_HD) errors.push(item.MA_TT + ' Không thỏa mãn')
            else arrIndex.push(idx)
          } else errors.push(item.MA_TT + ' Không thỏa mãn')
        })

        // console.log(arrIndex)

        gridObj.selectRows(arrIndex)
      }
      reader.readAsArrayBuffer(f)
      document.getElementById('gachno_upload_excel').value = null
    },
    exportFile () {
      // this.$toast.error('Chức năng xuất file module Admin đang thực hiện, Vui lòng quay lại sau.')
      this.exportExcel()
    },
    exportExcel: function () {
      if (this.results.length <= 0) {
        this.$toast.error('Không có dữ liệu để xuất Excel')
        return
      }
      let excelExportProperties = {
        dataSource: this.results,
        fileName: 'DSHoaDon.xlsx'
      }
      this.$refs.gridDSHoaDon.excelExport(excelExportProperties)
    },
    getSoPhieuTheoUser: function () {
      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pNhomHTTTId: 46,
        pNgayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        pNguoiDungId: this.$auth.getNguoiDungID()
      }
      try {
        GachNoAPI.getTongPhieuTheoUser(this.axios, postData)
          .then(
            (res) => {
              console.log(res)
              if (res.data.error_code === 'BSS-00000000' &&
             res.data.data !== undefined &&
             res.data.data.length > 0) {
                let tongphieu = 0
                try {
                  tongphieu = parseInt(res.data.data[0].TONGPHIEU)
                } catch (e) {}

                this.params.p_so_phieu = tongphieu + 1
              }
            }
          )
      } catch (err) {
        console.log(err)
      }
    },
    async getDanhSachNhanVienQLTheoDaiLy (dailyId) {
      try {
        const res = await CommonsAPI.getNhanVienQLTheoNguoiDung(this.axios, dailyId)
        return res
      } catch (err) {
        // console.log(err)
      }
    },
    dataBound (args) {
      let arr = []
      this.results.forEach((item, index) => {
        if (item.CONNO !== 0 && item.TIENSETRA === item.TIEN_HD) {
          arr.push(index)
        }
      })
      this.$refs.gridDSHoaDon.selectRows(arr)
      /*
      if (this.selIndex.length) {
          this.$refs.gridDSHoaDon.selectRows(this.selIndex);
          this.selIndex = [];
      }
      */
      // console.log(args)
    },
    async onChangeNVTC (e) {
      console.log(e)
      if (e === null) {
        this.params.p_ma_nvtc = null
        return
      }

      let nvtc = this.params.p_nvtc_options.filter(x => x.NHANVIEN_ID === this.params.p_nvtc)
      this.params.p_ma_nvtc = nvtc[0].MA_NV

      this.getDSLuotGiao(e.value)
      let res = await this.getDanhSachNhanVienQLTheoDaiLy(e.value)

      if (res.data.error_code === 'BSS-00000000' &&
        res.data.data !== undefined &&
        res.data.data.length > 0) {
        console.log(res)
        // v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
        // v_so_dt = res.data.data[0].SO_DT === null ? '' : res.data.data[0].SO_DT
      }
    },
    popupTimNVThuCuoc () {
      this.$refs.refTimNVThuCuoc.openDialog()
    },
    onChangeLuotGiao (e) {
      if (e === null)
        return

      this.params.luotgiao.id = e.GIAOPHIEU_ID
      this.params.luotgiao.text = e.GIAOPHIEU_ID
      $('#luotgiao-pd').hide()

      this.getDSLuotThanhToan(e.GIAOPHIEU_ID)
      this.getDanhSach()
    },
    onChangeDichVu (e) {
      this.getDanhSach()
    },
    onFilteringDichVu (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_DVVT', 'contains', e.text, true) : query
      e.updateData(this.params.p_dichvu_options, query)
    },
    onFilteringNVTC (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.params.p_nvtc_options, query)
    },
    onFilteringLoaiTien (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TENLOAI', 'contains', e.text, true) : query
      e.updateData(this.params.p_loaitien_options, query)
    },
    onFilteringThuQuy (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query
      e.updateData(this.params.p_thuquy_options, query)
    },
    onFilteringHTTT (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('HTTT', 'contains', e.text, true) : query
      e.updateData(this.params.p_httt_options, query)
    },
    onFilteringLuotTT (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('HINHTHUC', 'contains', e.text, true) : query
      e.updateData(this.params.p_luottt_options, query)
    },
    onFilteringLuotGiao (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('GIAOPHIEU_ID', 'contains', e.text, true) : query
      e.updateData(this.params.p_luotgiao_options, query)
    },
    chukycuocChangeHandler (e) {
      if (e.p_chuky_no !== null) {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
        this.getDSLoaiTien()
        this.getDSDaiLy()
        this.getSoPhieuTheoUser()
        //        this.getDSLuotTra()
      }
    },
    async chonNVTCHandler (e) {
      let ret = this.params.p_nvtc_options.find(x => x.NHANVIEN_ID === e.NHANVIEN_ID)

      if (ret !== undefined && ret !== null) this.params.p_nvtc = ret.NHANVIEN_ID
      else this.params.p_nvtc = this.params.p_nvtc_options[0].NHANVIEN_ID
    },

    RowChanged (e) {

    },
    actionComplete (args) {
      // console.log(args)
      if (args.requestType === 'refresh') {
        let gridObj = this.$refs.gridDSHoaDon.$refs.grid.ej2Instances

        gridObj.clearSelection()
        let arr = []
        console.log(gridObj.currentViewData)
        gridObj.currentViewData.forEach((item, index) => {
          // if (item.TIENSETRA === item.TIEN_HD) {
          //   arr.push(index)
          // }
          this.rowsSelected.forEach((value, index) => {
            if (item.TIENSETRA === item.TIEN_HD && item.MA_TT === value) arr.push(index)
          })
        })
        // console.log(arr)
        gridObj.selectRows(arr)
      }
    },
    queryCellInfo (args) {
      // console.log(args)
      // if (item.data.TIENSETRA !== item.data.TIEN_HD) {
      //   // args.
      // }
    },
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    searchThanhToanHandler (e) {
      let index
      let selected = this.$refs.gridDSHoaDon.$refs.grid.getSelectedRowIndexes()

      if (e.type === 1) {
        index = this.$refs.gridDSHoaDon.$refs.grid.getRowIndexByPrimaryKey(e.keyword)
        if (index < 0) {
          this.$toast.error('Không tìm thấy Mã TT trong danh sách.')
          return
        }
        if (!selected.includes(index)) {
          selected.push(index)
          this.$refs.gridDSHoaDon.$refs.grid.selectRows(selected)
        }
      } else {
        let arr = this.results.filter(x => x.MATB_DD == e.keyword)
        if (arr.length < 0) {
          this.$toast.error('Không tìm thấy Mã ĐD trong danh sách.')
          return
        }
        arr.forEach((item) => {
          index = this.$refs.gridDSHoaDon.$refs.grid.getRowIndexByPrimaryKey(item.MA_TT)
          if (!selected.includes(index)) {
            selected.push(index)
          }
        })
        this.$refs.gridDSHoaDon.$refs.grid.selectRows(selected)
      }
    }
  },
  validations: {
    params: {
      p_ghichu: { required },
      p_chugntu: { required }
    }
  }
})
</script>
