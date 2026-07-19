<template src="./index.html">
</template>
<style scoped src="./index.scss"></style>
<script>
import Vue from 'vue'
import moment from 'moment'
import DateTimeLib from '@/utils/DateTimeLib'
import ccbsService from './ccbs-service'
import vascService from './vasc-service'
import frmDSTBCungMa from '../ThayDoiThongTinKhuyenMai/frmDSTBCungMa/frmDSTBCungMa.vue'
import ChonKhuyenMai from './Popups/popup-ChonKhuyenMai.vue'
import { Query } from '@syncfusion/ej2-data'
import { DropDownListPlugin } from '@syncfusion/ej2-vue-dropdowns'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import CongTacVien from '@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien'
Vue.use(DropDownListPlugin)
const NguoiGioiThieu = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
const ThongTinEmail = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue')
const ThongTinLienHe = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
const frmTuVanMyTV = () => import('@/modules/CSKH/TuvanMyTv/TuvanMyTv.vue')
// const frnGiaoPhieuNV = () => import('@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue')
import frnGiaoPhieuNV from '@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue'
export default {
  name: 'CapNhatThueBaoDatCoc',
  components: {
    frmDSTBCungMa,
    ChonKhuyenMai,
    SearchContractModal,
    SearchAccount,
    CongTacVien,
    frmTuVanMyTV,
    NguoiGioiThieu,
    ThongTinEmail,
    ThongTinLienHe,
    frnGiaoPhieuNV
  },
  data () {
    return {
      frmTuVanMyTVCom: null,
      frmTuVanMyTVEvents: {
        'onUpdate': this.onUpdate
      },
      isShowPhiTTN: false,
      itocdo_id: -1,
      ob_id:  null,
      isTest: false,
      chitiet_km_theo_tbid: null,
      strict: true,
      showClearButton: false,
      userComponentModule: null,
      txtMaGDTT: '',
      popupComponentEvts: {
        paymentcreated: this.paymentcreated
      },
      ItemList: [],
      Lock: false,
      LoaiHopDong: {DATCOC_MOI: 31},
      TRANGTHAI_DONGBO: {DONGBO_CM: 5},
      LoaiHinhTB: {
        DIDONGTRASAU: 20,
        INTERNET_MYTV: 61
      },
      DATCOC: {
        DATCOC_TRATRUOC: 1,
        CAMKET_CHONSO: 10,
        MA_DATCOC_TRATRUOC: 'CCBS_TRATRUOC_CUOC_TB',
        MA_DATCOC_GOICUOC: 'CCBS_TRATRUOC_GOICUOC',
        MA_CAMKET_CHONSO: 'CCBS_CAMKET_CHONSO',
        NHOM_TAMTHUCUOCNONG: 25,
        MA_TAMTHU_CUOCNONG: 'CCBS_TAMTHU_CUOCNONG',
        NHOM_DATCOC_KYQUY:3,    
        NHOM_TAMTHUCUOCNONG_ROAMING: 5,    
      },
      OPEN_SYNC_VINA: false,
      popupChonKhuyenMaiData: {},
      DATCOC_MOI: 31,
      userInfo: {},
      DichVuVienThong: {
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19
      },
      tthd_id: 0,
      inDex_load: 0,
      dsHDTB_CNTT: [],
      CT_TIENHD_ID: null,
      dsCTTHD: [],
      rkm_id: null,
      hdtb_dc: [],
      huonggiaotn_id: null,
      quytrinh_id: null,
      dichvuvt_id: null,
      nguoigt_id: null,
      donvi_nguoi_gt_id: null,
      loainv_nguoi_gt_id: null,
      ctv_id: null,
      donvi_ctv_id: null,
      loainv_ctv_id: null,
      dgvDanhSach: [],
      loaitb_id: null,
      ma_kh: null,
      ten_kh: null,
      diachi_kh: null,
      dtpNgayLHD: new Date(),
      khlon_id: null,
      ten_tb: null,
      diachi_ld: null,
      so_dt_kh: null,
      khachhang_id: null,
      hdkh_id: null,
      nhomtb_id: null,
      phieu_id: null,
      dsHDKH: [],
      loi_sinh_magd: false,
      thuebao_dc_id: 0,
      thuebao_id: 0,
      data: {},
      kieu_dl: 1,
      dataSelected: {
        DICHVUVT_ID: null,
        LOAITB_ID: null,
        TOCDO_ID: null,
        NHOM_DATCOC_ID: null,
        KHUYENMAI_ID: null,
        CHITIETKM_ID: null
      },
      txtMaGD: null,
      txtSoThang: null,
      chkThoiHan: false,
      txtThoiHan: 0,
      txtTienTD: null,
      txtSoThangMG: null,
      txtTyleSD: 0,
      txtTienTB: 0,
      txtNguoiGT: null,
      txtTienSD: 0,
      txtGhiChu: null,
      txtTyleTB: 0,
      txtMaTB: null,
      txtCuocDC: null,
      txtUuDaiLanToa: null,
      chkCTV: false,
      txtCTV: null,
      chkNguoiGT: false,
      chkThangMG: false,
      MOI: 1,
      ADSL: 4,
      DICHVU_CNTT: 15,
      ANTOAN_BAOMAT_TT: 14,
      TRUNGTAM_DULIEU: 16,
      HOINGHI_TRUYENHINH: 13,
      INTERNET_ADSL: 11,
      INTERNET_FTTH: 58,
      WIFI_FIBER: 79,
      hdtb_id: 0,
      THUEBAO: {},
      vmuccuoctn_id: 0,
      vtocdotn_id: 0,
      chkPhiTTN: false,
      khoanmucPhiTTN : {},
      phiTTN: 0,
      tienHD: 0,
      tienVATHD: 0,
      tongTienHD: 0,
      dsLoaiDV: [],
      enableCtrl: {
        tsbtnNhapMoi: false,
        tsbtnGhiLai: false,
        tsbtnXoa: false,
        tsbtnHuyBo: false,
        tsbtnThanhToan: false,
        tsbtnThemTB: false,
        tsbtnXoaTB: false,
        tsbtnCapNhatDB: false,
        tsbtnInPhieuThu: false,
        tsbtnXemanh: false,
        txtSoThangMG: false,
        txtSoThang: false,
        txtCuocDC: false,
        txtTienTD: false,
        txtTyleSD: false,
        txtTyleTB: false,
        txtTienTB: false,
        txtTienSD: false,
        txtThoiHan: false,
        chkThoiHan: false,
        chkCTV: false
      },
      dtpNgayBD: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      dtpNgayKT: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpNgayBDDC: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      dtpNgayKTDC: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpTuNgay: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpDenNgay: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: false
      },
      dtpThangBD: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpThangKT: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpThangBDMG: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpThangKTMG: {
        value: new Date(),
        dateFormat: 'MM/yyyy',
        enabled: false
      },
      dtpNgayYeuCau: {
        value: new Date(),
        dateFormat: 'dd/MM/yyyy',
        enabled: true
      },
      THAMSO_MD:
      {
        nhap_CTV: 0,
        trung_matb: 0,
        check_chuahoa_cntt: false,
        ts_khong_bb_thanhtoan_hths: false,
        kiemtra_no_tratruoc: 0,
        KiemTra_HopDongKhac: 0,
        khong_load_ctv: 0,
        phainhap_thoihan: false,
        khong_ht_tracuudb: 0,
        ts_sinhma_gd_theo_donvi: false,
        MACDINH_CTV_NGUOIDUNG: 0,
        nhap_NGT: 0,
        CHAN_TRATRUOC_CUOCNONG: 0,
        tinhcuoc_ngay: false
      }

    }
  },
  watch: {
    chkPhiTTN (val) {
      if (!val) {
        this.tienTTN = 0
      } 
      this.tinhTongTienHD()
    },
    phiTTN (val) {
      this.tinhTongTienHD()
    },
    chkCTV (val) {
      if (val) {

      } else {
        this.ctv_id = 0
        this.txtCTV = null
      }
    },
    chkNguoiGT (val) {
      if (val) {

      } else {
        this.nguoigt_id = 0
        this.txtNguoiGT = null
      }
    },
    chkThoiHan (val) {
      this.dtpTuNgay.enabled = val
      this.dtpDenNgay.enabled = val
      this.enableCtrl.txtThoiHan = val
      if (val) {
        if (this.txtThoiHan !== null) {
          let _sothang = parseInt(this.txtThoiHan)
          this.dtpDenNgay.value = DateTimeLib.toDateObject(moment(this.dtpTuNgay.value).add(_sothang, 'M'))
        }
      }
    },
    'dtpThangBDMG.value' (val) {
      if (this.Lock) return
      if (val === null) {
        val = new Date()
        this.dtpThangBDMG.value = val
      }
      if (this.dataSelected.CHITIETKM_ID === null) return
      if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) return
      let ds = this.data.DS_CHITIETKM.filter(x => x.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
      if (ds.length > 0) {
        if (ds[0].LOAI_BDKM.toString() === '2') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else if (ds[0].LOAI_BDKM.toString() === '4') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else if (ds[0].LOAI_BDKM.toString() === '1') {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else {
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        }
      }
    },
    'dtpThangBD.value' (val) {
      if (this.Lock) return
      if (val === null) {
        val = new Date()
        this.dtpThangBD.value = val
      }
      if (this.dataSelected.CHITIETKM_ID === null) return
      let ds = this.data.DS_CHITIETKM.filter(x => x.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
      if (ds.length > 0) {
        this.dtpThangKT.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_DC), 'M').subtract(1, 'M'))
        if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
          this.dtpThangBDMG.enabled = false
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.enabled = false
          this.dtpThangKTMG.value = this.dtpThangKT.value
          return
        }
        if (ds[0].LOAI_BDKM.toString() === '2') {
          this.dtpThangBDMG.value = DateTimeLib.toDateObject(moment(new Date(val)).add(Number(ds[0].THANG_BDKM) + Number(ds[0].HUONG_DC) - 1, 'M'))
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else if (ds[0].LOAI_BDKM.toString() === '4') {
          this.dtpThangBDMG.value = DateTimeLib.toDateObject(moment(new Date(val)).add(Number(ds[0].THANG_BDKM), 'M'))
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else if (ds[0].LOAI_BDKM.toString() === '1') {
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        } else {
          this.dtpThangBDMG.enabled = true
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        }
      }
    },
    'dtpNgayBDDC.value' (val) {
      if (this.Lock) return
      if (val === null) {
        val = new Date()
        this.dtpNgayBDDC.value = val
      }
      if (this.dataSelected.CHITIETKM_ID === null) {
        return
      }
      try {
        var item = this.data.DS_CHITIETKM.find(item => item.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
        let ngay = moment(val).add(parseInt(item.HUONG_DC), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKTDC.value = ngayktdc
        this.dtpThangKT.value = this.dtpNgayKTDC.value

        if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
          this.dtpNgayBD.value = val
          this.dtpNgayKT.value = this.dtpNgayKTDC.value
          return
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:277 ~ error:', error)
      }
    },
    'dtpNgayBD.value' (val) {
      if (this.Lock) return
      if (this.dataSelected.CHITIETKM_ID === null) return
      try {
        var item = this.data.DS_CHITIETKM.find(item => item.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
        let ngay = moment(val).add(parseInt(item.HUONG_KM), 'M')
        let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))
        this.dtpNgayKT.value = ngayktdc
        this.dtpThangKTMG.value = this.dtpNgayKT.value
      } catch (error) {
        console.log('🚀 ~ file: index.vue:277 ~ error:', error)
      }
    },
    'dataSelected.LOAITB_ID' (val) {
      if (val === null) return
      this.cboLoaiHinhTB_SelectedValueChanged()
    },
    'dataSelected.CHITIETKM_ID' (val) {
      if (this.Lock) return
      this.cboChiTietKM_SelectedValueChanged()
    },
    'dataSelected.KHUYENMAI_ID' (val) {
      if (this.Lock) return
      Vue.set(this.data, 'DS_CHITIETKM', [])
      this.loadTratruoc(null)
    },
    async 'dataSelected.NHOM_DATCOC_ID' (val) {
      Vue.set(this.data, 'DS_KHUYENMAI', [])
      if (val) await this.HT_DS_KhuyenMai_Combobox(null)
    },
    'dataSelected.DICHVUVT_ID' (val) {
      Vue.set(this.data, 'DS_LOAIHINH_TB', [])
      Vue.set(this.data, 'DS_TOCDO_INTERNET', [])
      if (val) {
        this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: val}).then(() => {
          // if (this.data.DS_LOAIHINH_TB.length > 0) {
          //   this.dataSelected.LOAITB_ID = this.data.DS_LOAIHINH_TB[0].LOAITB_ID
          // }
        })
        if ([4, 14, 15, 13, 16].indexOf(val) > -1) {
          this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: val}).then(() => {
            // if (this.data.DS_TOCDO_INTERNET.length > 0) {
            //   this.dataSelected.TOCDO_ID = this.data.DS_TOCDO_INTERNET[0].TOCDO_ID
            // }
          })
        }
      }
    },
    txtCuocDC(val) {
      let tien = (Math.round(parseFloat(val) / 1.1))
      let vat = (parseFloat(val) - tien)
      this.tienHD = tien
      this.tienVATHD = vat
      this.tinhTongTienHD()
      console.log(val)
    }
  },
  computed: {
    minValNgayBDDC () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')
    },
    maxValNgayBDDC () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).endOf('month'), 'DD/MM/YYYY')
    },
    minValNgayBDMG () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBDMG.value).startOf('month'), 'DD/MM/YYYY')
    },
    maxValNgayBDMG () {
      return DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBDMG.value).endOf('month'), 'DD/MM/YYYY')
    }
  },
  methods: {
    async txtCuocDC_Leave () {
      if (this.txtCuocDC == null || this.txtCuocDC === '') {
        return
      }
      this.txtUuDaiLanToa = '0'
      var dsUDLT = await this.Lay_tien_uudai_lantoa_theo_tb(this.thuebao_id)
      if (dsUDLT.length > 0) {
        if (this.txtCuocDC !== 0 && this.txtCuocDC !== null) {
          var uudai = parseFloat(dsUDLT[0].TON_CK)
          var cuocdc = parseFloat(this.txtCuocDC)
          if (cuocdc <= uudai) {
            this.txtUuDaiLanToa = cuocdc.toString()
          } else {
            this.txtUuDaiLanToa = uudai.toString()
          }
        }
      }
    },
    async txtCuocDC_TextChanged ($event) {
      let val = this.txtCuocDC
      try {
        if (val == null || val === '') {
          this.txtTienTD = 0
          return
        }
        if (this.txtSoThang !== null && this.txtSoThang !== 0 && this.txtSoThang !== '' && this.txtSoThang !== '0') {
          let tien_td = 0
          tien_td = Math.floor(parseFloat(this.txtCuocDC) / parseInt(this.txtSoThang))
          this.txtTienTD = tien_td.toString()
        }
        if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString() && this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
          if (this.txtSoThang === null || this.txtSoThang === 0 || this.txtSoThang === '' || this.txtSoThang === '0') {
            await this.HIENTHI_TT_CUOC_TAMTHU()
          }
        }
        this.txtUuDaiLanToa = '0'
        var dsUDLT = await this.Lay_tien_uudai_lantoa_theo_tb(this.thuebao_id)
        if (dsUDLT.length > 0) {
          if (this.txtCuocDC !== 0 && this.txtCuocDC !== null) {
            var uudai = parseFloat(dsUDLT[0].TON_CK)
            var cuocdc = parseFloat(this.txtCuocDC)
            if (cuocdc <= uudai) {
              this.txtUuDaiLanToa = cuocdc.toString()
            } else {
              this.txtUuDaiLanToa = uudai.toString()
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:211 ~ txtCuocDC ~ error', error)
      }
    },
    onlyNumber ($event) {
      if (!/\d/.test(event.key)) return event.preventDefault()
    },
    toLowerCases (ds) {
      if (ds === undefined) return []
      if (ds.length === 0) return []
      let dt = ds.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toLowerCase()] = item[key])
        return newItem
      })
      return dt
    },
    toUpperCases (ds) {
      if (ds === undefined) return []
      if (ds.length === 0) return []
      let dt = ds.map(item => {
        let newItem = {}
        Object.keys(item).forEach(key => newItem[key.toUpperCase()] = item[key])
        return newItem
      })
      return dt
    },
    btnChon_KhuyenMai_Click () {
      if (this.data.DS_KHUYENMAI === undefined) return
      if (this.data.DS_KHUYENMAI.length === 0) return
      let dt = this.toLowerCases(this.data.DS_KHUYENMAI)
      this.popupChonKhuyenMaiData = {
        loai: 0,
        dt: dt
      }
      this.$refs['ref-chon-khuyen-mai'].show()
    },
    txtThoiHan_Leave () {
      if (this.chkThoiHan) {
        if (this.txtThoiHan !== null) {
          let _sothang = parseInt(this.txtThoiHan)
          this.dtpDenNgay.value = DateTimeLib.toDateObject(moment(this.dtpTuNgay.value).add(_sothang, 'M'))
        }
      }
    },
    onChangeCheckNguoiGt (event) {
      if (event.target.checked) {
        this.$refs.popupNguoiGioiThieu.show()
      }
    },
    onChangeCheckCtv (event) {
      if (event.target.checked) {
        this.$refs.popupCongTacVien.show()
      }
    },
    xacNhanCongTacVien (val) {
      this.txtCTV = val.ten_ht
      this.ctv_id = val.nhanvien_id
      this.$refs.popupCongTacVien.hide()
    },
    xacNhanNguoiGioiThieu (val) {
      this.txtNguoiGT = val.ten_ht
      this.nguoigt_id = val.nhanvien_id
      this.$refs.popupNguoiGioiThieu.hide()
    },
    async  acceptSearchAccount (item) {
      try {
        if (item && item.ma_tb) {
          this.txtMaTB = item.ma_tb
          this.$refs.txtMaTB.focus()
          await this.txtMaTB_KeyPress()
        }
      } catch (error) {
      } finally {
        this.$refs.popupSearchAccount.hide()
      }
    },
    acceptSearchContract (item) {
      this.txtMaGD = item.ma_gd
      this.$refs.txtMaGD.focus()
      this.txtMaGD_KeyPress()
    },
    onChonKhuyenMai (data) {
      if (!!data && !!data.khuyenmai.khuyenmai_id) {
        this.dataSelected.KHUYENMAI_ID = data.khuyenmai.khuyenmai_id
      }
      this.$bvModal.hide('ref-chon-khuyen-mai')
    },
    async tsbtnXemanh_Click () {
      var anh = await this.MAP_ID('MOTA', 'CSS', 'GIAOPHIEU', 'HDTB_ID', this.hdtb_id)
      if (anh !== null) {
        alert('Tham chieu den frmXemAnhBH')
      }
    },
    async tsbtnHuyBo_Click () {
      this.SetButton(0)
    },
    tsbtnEmail_Click () {
      if (this.hdkh_id !== 0) {
        this.$refs['ref-thongtin-email'].show()
      }
    },
    tsbtnThongTinLH_Click () {
      if (this.hdkh_id !== 0) {
        this.$refs['ref-thongtin-lienhe'].show()
      }
    },
    async tsbtnNhapMoi_Click () { this.SetButton(1) },
    async tsbtnXoa_Click () {
      try {
        this.$root.showLoading(true)
        console.log('🚀 ~ file: index.vue:583 ~ tsbtnXoa_Click ~ this.tthd_id:', this.tthd_id)
        if (this.tthd_id > 1) {
          var trangthai_hd = await this.trangthai_hd(this.tthd_id)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' bạn không thể xóa!')
          this.$root.showLoading(false)
          return false
        }
        var chk = await this.XOA_HDKH_V2(this.hdkh_id, this.DATCOC_MOI)
        this.$root.showLoading(false)
        if (chk) {
          this.$root.$toast.success('Xóa hợp đồng khách hàng thành công!')
          this.dgvDanhSach = []
          this.SetButton(0)
        } else { this.$root.$toast.error('Lỗi xóa hợp đồng khách hàng') }
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async XOA_HDKH_V2 (hdkh_id, loaihd_id) {
      try {
        let data = {hdkh_id}
        let rs = await this.$root.context.post('/web-hopdong/ThayDoiDatCoc/xoa_hdkh', data)
        console.log('🚀 ~ file: index.vue:605 ~ XOA_HDKH_V2 ~ rs.data:', rs.data)
        if (rs.error_code === 'BSS-00000000') {
          return true
        } else {
          this.$toast.error(rs.message)
          return false
        }
        
      } catch (error) {
        console.log('🚀 ~ file: index.vue:607 ~ XOA_HDKH_V2 ~ error:', error)
        return false
      }
    },
    onUpdate (result) {
      console.log('aa:', result)
      if (this.frmTuVanMyTVCom && this.$refs.dialogfrmTuVanMyTV) { this.$refs.dialogfrmTuVanMyTV.hideDialog() }
      this.CapNhat()
    },

    async tsbtnGhiLai_Click () {
      this.$root.showLoading(true)
      
      try {
        if (this.enableCtrl.tsbtnNhapMoi === false) {
           
          if (this.THAMSO_MD.trung_matb === 1) {
            if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
              let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
              if (KiemTraMaTBLapHopDongKhac) {
                this.$root.showLoading(false)
                return false
              }
            } else {
              let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), parseInt(this.dataSelected.LOAITB_ID), 32)
              if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
                this.$root.showLoading(false)
                return false
              }
            }
          } else {
            if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
              let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
              if (KiemTraMaTBLapHopDongKhac) {
                this.$root.showLoading(false)
                return false
              }
            } else {
              let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
              if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
                this.$root.showLoading(false)
                return false
              }
            }
          }
          let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(true)
          if (!Kiemtra_Dulieu) {
            this.$root.showLoading(false)
            return false
          }
        } else {
          let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(false)
          if (!Kiemtra_Dulieu) {
            this.$root.showLoading(false)
            return false
          }
        }

        // Bổ sung phần kiểm tra nợ Thuê bao

        let checkTongNo = await this.checkTongNoTB()

        if (checkTongNo && Number(checkTongNo) > 0) {
          if ((await this.$confirm(`Mã thuê bao ${this.txtMaTB} đang còn nợ ${this.$options.filters.numeral(checkTongNo)} đồng`, 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            return 'Yes'
          })) != 'Yes') {
            return false
          }            
        }

        // Bổ sung phần tư vấn
        
        this.hdkh_id = this.hdkh_id || 0
        let params = {
          hdkh_id: 0,
          loai: 1,
          khachhang_id: this.khachhang_id
        }
        try {
          let tuvan = await ccbsService.get_kiemtra_tuvan_mytv(this.axios, {params: params})
            .then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { return response.data.data == 1 } else return false
            })
            .catch(() => {
              this.$root.showLoading(false)
              return false
            })
          if (tuvan) {
            this.$root.showLoading(false)
            if ((await this.$confirm('Khách hàng chưa sử dụng MyTV / WiFi Mesh / Camera, đề nghị Anh/Chị tư vấn mời khách hàng sử dụng dịch vụ MyTV / WiFi Mesh / Camera', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(async () => {
              this.frmTuVanMyTVCom = frmTuVanMyTV
              await this.frmTuVanMyTVCom().then(x => {
              })
              this.$nextTick(() => {
                let data = {
                  khachhang_id: this.khachhang_id,
                  loai: 1
                }
                this.$refs.dialogfrmTuVanMyTV.openDialog(data)
              })
              return 'Yes'
            })) != 'Yes') await this.CapNhat()
          } else {
            await this.CapNhat()
          }
        } catch (error) {
          console.log("Lỗi:", error)
          // nếu có lỗi trong quá trình tư vấn vẫn tiếp tục quy trình quy lại
          await this.CapNhat()
        }
        
      } catch (ex) {
        console.log("Lỗi:",ex);
      }
      // Kết thúc phần tư vấn
      this.$root.showLoading(false)
    },
    
    async tsbtnGhiLai_Click1 () {
      this.$root.showLoading(true)
      // Bổ sung phần tư vấn
      try {
        if (this.enableCtrl.tsbtnNhapMoi === false) {  // nhập mới
        
          
          if (checkTongNo && checkTongNo > 0) {
            if ((await this.$confirm(`Mã thuê bao ${this.txtMaTB} đang còn nợ ${this.$options.filters.numeral(checkTongNo)} đồng`, 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(async () => {
              if (this.THAMSO_MD.trung_matb === 1) {
                if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
                  let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
                  if (KiemTraMaTBLapHopDongKhac) {
                    this.$root.showLoading(false)
                    return false
                  }
                } else {
                  let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), parseInt(this.dataSelected.LOAITB_ID), 32)
                  if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
                    this.$root.showLoading(false)
                    return false
                  }
                }
              } else {
                if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
                  let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
                  if (KiemTraMaTBLapHopDongKhac) {
                    this.$root.showLoading(false)
                    return false
                  }
                } else {
                  let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
                  if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
                    this.$root.showLoading(false)
                    return false
                  }
                }
              }
              let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(true)
              if (!Kiemtra_Dulieu) {
                this.$root.showLoading(false)
                return false
              }
              return 'Yes'
            })) != 'Yes') {
              // await this.CapNhat()
            }            
          }
          
          
        } else {
          
          let Kiemtra_Dulieu = await this.Kiemtra_Dulieu(false)
          if (!Kiemtra_Dulieu) {
            this.$root.showLoading(false)
            return false
          }
        }
        this.hdkh_id = this.hdkh_id || 0
        let params = {
          hdkh_id: 0,
          loai: 1,
          khachhang_id: this.khachhang_id
        }
        var tuvan = await ccbsService.get_kiemtra_tuvan_mytv(this.axios, {params: params})
          .then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') { return response.data.data == 1 } else return false
          })
          .catch(() => {
            this.$root.showLoading(false)
            return false
          })
        if (tuvan) {
          this.$root.showLoading(false)
          if ((await this.$confirm('Khách hàng chưa sử dụng MyTV / WiFi Mesh / Camera, đề nghị Anh/Chị tư vấn mời khách hàng sử dụng dịch vụ MyTV / WiFi Mesh / Camera', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            this.frmTuVanMyTVCom = frmTuVanMyTV
            await this.frmTuVanMyTVCom().then(x => {
            })
            this.$nextTick(() => {
              let data = {
                khachhang_id: this.khachhang_id,
                loai: 1
              }
              this.$refs.dialogfrmTuVanMyTV.openDialog(data)
            })
            return 'Yes'
          })) != 'Yes') await this.CapNhat()
        } else {
          await this.CapNhat()
        }
      } catch (ex) {
        await this.CapNhat()
      }
      // Kết thúc phần tư vấn
      this.$root.showLoading(false)
    },
    async tsbtnThemTB_Click () {
      try {
        this.$root.showLoading(true)
        await this.ThemHDThueBao()
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async ThemHDThueBao () {
      if (this.THAMSO_MD.trung_matb === 1) {
        if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
          let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
          if (KiemTraMaTBLapHopDongKhac) return false
        } else {
          let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), parseInt(this.dataSelected.LOAITB_ID), 32)
          if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false
        }
      } else {
        if (this.THAMSO_MD.KiemTra_HopDongKhac === 1) {
          let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
          if (KiemTraMaTBLapHopDongKhac) return false
        } else {
          let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
          if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false
        }
      }
      var vkt = await this.MAP_ID('PHIEUTT_ID', 'CSS', 'PHIEUTT_HD', 'HDKH_ID', this.hdkh_id)
      if (vkt !== null) {
        this.$root.$toast.warning('Hợp đồng đã được thanh toán. Bạn không được thêm thuê bao!')
        return
      }
      var Kiemtra_Dulieu = await this.Kiemtra_Dulieu(true)
      if (!Kiemtra_Dulieu) return false
      let taodulieu = await this.TaoDuLieu2(true, true)
      if (!taodulieu) {
        this.$root.$toast.error('Lỗi khởi tạo dữ liệu!!!')
        return
      }
      let result = await this.ThemMoi_HopDong(null, this.dsHDTB, this.hdtb_dc, this.dsCTTHD, this.huonggiaotn_id)
      if (result) {
        if (!this.THAMSO_MD.tinhcuoc_ngay) { await this.CN_NGAY_BD(this.hdtb_id, this.dtpNgayBD.value === null ? DateTimeLib.toDateDisplay(new Date()) : DateTimeLib.toDateDisplay(this.dtpNgayBD.value), 1) }
        await this.HienThiDanhSacHDTB()
        this.inDex_load = 0
        this.$root.$toast.success('Thêm mới dữ liệu thành công!')
      }
      
    },
    dgvDanhSach_CellClick (args) {
      console.log('🚀 ~ file: index.vue:751 ~ dgvDanhSach_CellClick ~ args:', args)
    },
    async ThemMoi_HopDong (dsHDKH, dsHDTB, dsGOI, dsCTTHD, huonggiao_id) {
      try {
        var hdtb_id = parseInt(this.dsHDTB[0].HDTB_ID)

        for (let i = 0; i < dsHDTB.length; i++) {
          dsHDTB[i].TEN_TB = null
          dsHDTB[i].DIACHI_LD = null
          dsHDTB[i].DIACHI_TB = null
        }

        let data = {
          dsHDTB: JSON.stringify(dsHDTB),
          dsGOI: JSON.stringify(dsGOI),
          dsCTTHD: JSON.stringify(dsCTTHD),
          HDTB_ID: hdtb_id,
          HUONGGIAO_ID: huonggiao_id
        }
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong', data)
        if (rs.data.length > 0) {
          if(rs.data[0].result.toString() === '1')
            return true
          else {
            this.$root.$toast.error(rs.data[0].result)
            return false  
          }
        } else {
          this.$root.$toast.error(rs.data[0].result)
          return false
        }
      } catch (error) {
        return false
      }
    },
    async tsbtnXoaTB_Click () {
      try {
        this.$root.showLoading(true)
        if (this.tthd_id > 1) {
          var trangthai_hd = await this.trangthai_hd(this.tthd_id)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' bạn không thể xóa!')
          this.$root.showLoading(false)
          return false
        }
        try {
          this.$root.showLoading(false)
          let confirm = null
          try {
            confirm = await this.$confirm(`Bạn thật sự muốn xóa dữ liệu không ?`, 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không',
              type: 'warning'
            })
          } catch (error) {
            return false
          }
          this.$root.showLoading(true)
          await this.XOA_HDTB(this.hdtb_id)
        } catch (error) {
          this.$root.$toast.error('Có lỗi khi xóa thông tin: ' + error)
          this.$root.showLoading(false)
          return false
        }
        await this.HienThiDanhSacHDTB()
        this.inDex_load = 0
        this.$root.$toast.success('Xóa thông tin thuê bao khỏi hợp đồng đặt cọc thành công')
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async XOA_HDTB (hdtb_id) {
      try {
        await this.$root.context.post('/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId=' + hdtb_id)
      } catch (error) {
      }
    },
    async tsbtnThanhToan_Click () {
      if (this.hdkh_id === null) return
      if (this.tthd_id > 1) {
        var trangthai_hd = await this.trangthai_hd(this.tthd_id)
        this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' !')
        return false
      }
      var LAY_DS_THAMSO_MD_MATS = await this.LAY_DS_THAMSO_MD_MATS('KIEMTRA_GOI')
      if (LAY_DS_THAMSO_MD_MATS.length > 0) {
        if (LAY_DS_THAMSO_MD_MATS[0].TEN_TS.toString() === '1') {
          var _dtDK_Goi = await this.KiemTra_DK_GOIDADV(this.hdkh_id)
          if (_dtDK_Goi.length > 0) {
            if (_dtDK_Goi[0].KQ.toString() !== '1') {
              this.$root.$toast.warning(_dtDK_Goi[0].KQ.toString())
              return false
            }
          }
        }
      }
      if (this.txtMaGD !== '') {
        this.loading(true)
        this.userComponentModule = thanhToan
        this.txtMaGDTT = this.txtMaGD
      } else {
        this.ShowError('Bạn chưa nhập mã giao dịch!')
        this.$refs.txtMaGD.focus()
      }
    },
    open_thanhToanPopUp () {
      this.loading(false)
    },
    paymentcreated () {
      this.$refs.thanhtoanDialog.show(true)
    },
    async close_thanhToanPopUp () {
      this.userComponentModule = null
      if (this.txtMaGD) {
        await this.txtMaGD_KeyPress()
      }
    },
    async KiemTra_DK_GOIDADV (hdkh_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/kiemtra-dk-goidadv?hdkh_id=' + hdkh_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LAY_DS_THAMSO_MD_MATS (mats) {
      try {
        let rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/lay-ds-thamso-md-mats?mats=' + mats)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async lay_thongtin_nguoidung () {
      try {
        let rs = await this.$root.context.post('/quantri/user/thongtin_nguoidung2', {})
        return rs.data
      } catch (error) {
        return null
      }
    },
    async trangthai_hd (tthd_id) {
      var kq = await this.lay_trangthai_hd(tthd_id)
      return kq
    },
    async lay_trangthai_hd (tthd_id) {
      var tthd = await this.MAP_ID('TRANGTHAI_HD', 'CSS', 'TRANGTHAI_HD', 'TTHD_ID', tthd_id)
      return tthd === null ? '' : tthd
    },
    async KichHoat_HoanThien_TraTruocDiDong () {
      let ktra = ''
      let chuky_id_neo = await this.MAP_ID('CHUKY_ID_NEO', 'CSS', 'CT_KHUYENMAI', 'CHITIETKM_ID', this.dataSelected.CHITIETKM_ID)
      let sotb = '84' + this.txtMaTB
      let tien_dongtruoc = this.txtCuocDC
      let user_neo = this.userInfo.user_neo
      ktra = await this.ts_dongtruoc_cuoctb_dangky(sotb, tien_dongtruoc, chuky_id_neo, user_neo)
      if (ktra === '1') {
        await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM)
      } else {
        ktra = '0'
      }
      return ktra
    },
    async CAPNHAT_STATUS_HDTB (hdtb_id, status) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat_status_hdtb', {hdtb_id, status})
      } catch (error) {
        return null
      }
    },
    async ts_dongtruoc_cuoctb_dangky (data) {
      try {
        let rs = await this.$root.context.post('/ccbs/executor/ts_dongtruoc_cuoctb_dangky', data)
        return '1'
      } catch (error) {
        return null
      }
    },
    async KichHoat_HoanThien_GoicuocDiDong () {
      let ktra = ''
      let package_id = ''
      let congvan_id = ''
      let package_id_src = ''
      if (this.data.DS_CHITIETKM.length > 0 && this.dataSelected.CHITIETKM_ID) {
        var ds_ts = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'CHITIET_KM', option: this.dataSelected.CHITIETKM_ID })
        var ds_ts_goi = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'CHITIET_KM_GOI', option: this.dataSelected.CHITIETKM_ID })
        if (ds_ts && ds_ts.data.length > 0) {
          package_id = ds_ts.data[0].CHUKY_ID_NEO
          congvan_id = ds_ts.data[0].CV_VNP_ID
        }
        if (ds_ts_goi && ds_ts_goi.data.length > 0) {
          package_id_src == ds_ts_goi.data[0].GOI_VNP_ID
        }
        let so_tb = '84' + this.txtMaTB
        let user_neo = this.userInfo.user_neo
        let ghichu = this.txtGhiChu
        let vma_tinh = this.$auth.getMaTinh()
        var dt = await ccbsService.ts_prepayment_dangky(so_tb, package_id, package_id_src, congvan_id, ghichu, vma_tinh, user_neo, this)
        if (!!dt && dt.length > 0) {
          this.$root.$toast.success('Đăng ký trả trước gói cước thành công!')
          await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM)
          ktra = dt[0][0]
        }
        return ktra
      }
    },
    async ts_tttien_ckchonso_checktb (ma_tinh, so_tb, userid) {
      let rs = {
        IssSuccessed: '',
        Message: ''
      }
      try {
        var response = await ccbsService.ts_tttien_ckchonso_checktb(ma_tinh, so_tb, userid, this)
        let rsObj = response.data
        rs.IssSuccessed = rsObj.ResultDesc === 'Success' && rsObj.Result === '1'
        if (!rs.IssSuccessed) {
          if (rsObj.Result === '2') { rs.Message = 'Thuê bao không tồn tại' } else if (rsObj.Result === '3') { rs.Message = 'Thuê bao không thuộc tỉnh quản lý' } else if (rsObj.Result == '4') { rs.Message = 'Thuê bao khởi tạo tài khoản không được phép đăng ký' } else if (rsObj.Result == '5') { rs.Message = 'Thuê bao không là trả sau' } else if (rsObj.Result == '6') { rs.Message = 'Thuê bao nằm thuộc kiểu số không được thay đổi cam kết' } else if (rsObj.Result == '7') { rs.Message = 'Thuê bao thuộc kho số toàn quốc không được phép thay đổi cam kết' } else if (rsObj.Result == '8') { rs.Message = 'Thuê bao không có cam kết' } else if (rsObj.Result == '9') { rs.Message = 'Thuê bao 088 không được phép thay đổi cam kết!' } else { rs.Message = rsObj.Result }
        }
      } catch (error) {
        rs.IssSuccessed = false
        rs.Message = error.toString()
      }
      return rs
    },
    async ts_tttien_ckchonso_dangky (ma_tinh, psmuc_chietkhau, ghi_chu, so_tb, pskieuso_id, userid) {
      let rs = {
        IssSuccessed: '',
        Message: ''
      }
      try {
        var response = await ccbsService.ts_tttien_ckchonso_dangky(ma_tinh, psmuc_chietkhau, ghi_chu, so_tb, pskieuso_id, userid, this)
        let rsObj = response.data
        rs.IssSuccessed = rsObj.ResultDesc === 'Success' && rsObj.Result === '1'
        if (!rs.IssSuccessed) {
          if (rsObj.Result === '2') { rs.Message = 'Thuê bao không tồn tại' } else if (rsObj.Result === '3') { rs.Message = 'Thuê bao không thuộc tỉnh quản lý' } else if (rsObj.Result == '4') { rs.Message = 'Thuê bao khởi tạo tài khoản không được phép đăng ký' } else if (rsObj.Result == '5') { rs.Message = 'Thuê bao không là trả sau' } else { rs.Message = rsObj.Result }
        }
      } catch (error) {
        rs.IssSuccessed = false
        rs.Message = error.toString()
      }
      return rs
    },

    async KichHoat_HoanThien_Camketchonso () {
      let ktra = ''
      let so_tb = '84' + this.txtMaTB
      let userid = this.userInfo.user_neo
      let ghichu = this.txtGhiChu
      let ma_tinh = this.$auth.getMaTinh()
      var dt = await this.ts_tttien_ckchonso_checktb(ma_tinh, so_tb, userid, this)
      if (dt.IssSuccessed) {
        let dt1 = await ccbsService.ts_tttien_ckchonso_laykieuso(so_tb, this)
        if (!!dt1 && dt1.length > 0) {
          try {
            if (dt1[0].KIEUSO_ID !== null && dt1[0].ID === null) {
              let ccbs_ck = await ccbsService.ts_tttien_ckchonso_dangky(ma_tinh, dt1[0].PERCENT_BONUS, ghichu, so_tb, dt1[0].KIEUSO_ID, userid, this)
              if (ccbs_ck.IssSuccessed) {
                ktra = '1'
                this.$root.$toast.success('Đăng ký trả trước cam kết chọn số thành công!')
                await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM)
              } else {
                this.$root.$toast.warning(ccbs_ck.Message)
              }
            }
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 663 ~ KichHoat_HoanThien_Camketchonso ~ error', error)
          }
        } else {
          this.$root.$toast.error('Số thuê bao không thõa mãn điều kiện trả trước cam kết chọn số!')
        }
      } else {
        this.$root.$toast.error('Số thuê bao không thõa mãn điều kiện trả trước cam kết chọn số!')
      }
      return ktra
    },
    async lay_tt_khachhang (data) {
      let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCocHuy/lay_tt_khachhang`, data)
      return response.data
    },
    async KichHoat_HoanThien_TamThuCuocNong (htttId) {
      try {
        let ktra = false
        let sotb = '84' + this.txtMaTB
        let tien_dongtruoc = this.txtCuocDC
        let ma_tinh = this.userInfo.tentat
        let ghi_chu = this.txtGhiChu
        let ma_tn = this.$root.token.getUserName()
        let user_neo = this.userInfo.user_neo
        let ma_tinh_cu = '0'
        let httt_id = htttId
        let ngaynhap = DateTimeLib.toDateDisplay(this.dtpNgayYeuCau.value)
        let s = await this.layKyCuocHienHanh()
        let yyyy = s.substring(0, 4)
        let month = s.substring(4, 6)
        let chukyno = month + yyyy
        let phieu_id = ''
        let ds_tt = await this.lay_tt_khachhang({
          khachhang_id: this.khachhang_id,
          ma_tb: this.txtMaTB
        })
        let ma_kh = ds_tt[0]['ma_tt_neo']
        console.log('🚀 ~ file: index.vue:1044 ~ KichHoat_HoanThien_TamThuCuocNong ~ ma_kh:', ma_kh)
        let result = await ccbsService.ts_tamthu_cuocnong_them_phieu(tien_dongtruoc, ma_tinh, ma_kh, ghi_chu, phieu_id, ma_tn, '1', ma_tinh_cu, sotb, chukyno, httt_id, ngaynhap, '', this)
        console.log('🚀 ~ file: index.vue:1046 ~ KichHoat_HoanThien_TamThuCuocNong ~ result:', result)
        if (result.IssSuccessed) {
          ktra = true
          await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM)
        } else {
          ktra = false
        }
        return ktra
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1062 ~ KichHoat_HoanThien_TamThuCuocNong ~ error:', error)
        return false
      }
    },
    async KichHoat_HoanThien_ThuKyQuy () {
      try {
        let ktra = false
        let sotb = '84' + this.txtMaTB
        let tien_dongtruoc = this.txtCuocDC
        let ma_tinh = this.userInfo.tentat
        let ghi_chu = this.txtGhiChu
        let ma_tn = this.$root.token.getUserName()
        let user_neo = this.userInfo.user_neo
        let ma_tinh_cu = '0'
        let httt_id = '18'
        let ngaynhap = DateTimeLib.toDateDisplay(this.dtpNgayYeuCau.value)
        let s = await this.layKyCuocHienHanh()
        
        let month = s.substring(4, 6)
        let yyyy = s.substring(0, 4)
        
        let chukyno = month+yyyy;        
        let phieu_id = ''
        let ds_tt = await this.lay_tt_khachhang({
          khachhang_id: this.khachhang_id,
          ma_tb: this.txtMaTB
        })
        let ma_kh = ds_tt[0]['ma_tt_neo']
        console.log('🚀 ~ file: index.vue:1044 ~ KichHoat_HoanThien_ThuKyQuy ~ ma_kh:', ma_kh)
        let result = await ccbsService.qltn_kyquy_themmoi_kyquy(ma_kh,sotb,tien_dongtruoc,ghi_chu,chukyno,1,ngaynhap,2,ma_tn,user_neo,ma_tn, this)
        console.log('🚀 ~ file: index.vue:1046 ~ KichHoat_HoanThien_ThuKyQuy ~ result:', result)
        if (result.IssSuccessed) {
          ktra = true
          await this.CAPNHAT_STATUS_HDTB(this.hdtb_id, this.TRANGTHAI_DONGBO.DONGBO_CM)
        } else {
          ktra = false
        }
        return ktra
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1062 ~ KichHoat_HoanThien_ThuKyQuy ~ error:', error)
        return false
      }
    },
    async KIEMTRA_OPEN_SYNC_VINA (keyname) {
      try {
        let kq = await this.$root.context.get('web-hopdong/hoanthienhschuyenquyen/kiemtra_open_sync_vina', {keyname: keyname})
        if (kq.data && kq.data.length > 0) {
          return kq.data[0].KEY_VALUE
        } else {
          return false
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1072 ~ KIEMTRA_OPEN_SYNC_VINA ~ error:', error)
        return false
      }
    },
    async tsbtnCapNhatDB_Click () {
      try {
        this.$root.showLoading(true)
        if (this.tthd_id !== 2) {
          var trangthai_hd = await this.trangthai_hd(this.tthd_id)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' không thể hoàn thiện!')
          this.$root.showLoading(false)
          return
        }
        if (!this.THAMSO_MD.ts_khong_bb_thanhtoan_hths) {
          var KT_Trangthai_thanhtoan_hd = await this.KT_Trangthai_thanhtoan_hd(this.hdkh_id)
          if (!KT_Trangthai_thanhtoan_hd) {
            this.$root.$toast.warning('Hợp đồng đã thanh toán nhưng chưa thu được tiền không thể hoàn thiện!')
            this.$root.showLoading(false)
            return
          }
        }
        if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString()) {
          var kq = await this.KICHHOAT_HOANTHIEN_2731()
          if (kq !== '1' && kq !== '-1') {
            this.$root.showLoading(false)
            return
          }
        }
        let OPEN_SYNC_VINA = await this.KIEMTRA_OPEN_SYNC_VINA('OPEN_SYNC_VINA')
        console.log('🚀 ~ file: index.vue:857 ~ tsbtnCapNhatDB_Click ~ OPEN_SYNC_VINA:', OPEN_SYNC_VINA)
        if (OPEN_SYNC_VINA) {
          if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString()) {
            if (this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.DATCOC_TRATRUOC.toString() || this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.CAMKET_CHONSO.toString()) {
              var ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', this.dataSelected.KHUYENMAI_ID)
              if (ma_km && ma_km.toString() === this.DATCOC.MA_DATCOC_TRATRUOC.toString()) {
                let ktra = await this.KichHoat_HoanThien_TraTruocDiDong()
                if (ktra && ktra.toString() !== '1') {
                  this.$root.showLoading(false)
                  this.$root.$toast.error('Đăng ký không thành công, chi tiết: ' + ktra + ' !!!')
                  return
                }
              }
              if (ma_km && ma_km.toString() === this.DATCOC.MA_DATCOC_GOICUOC.toString()) {
                let ktra = await this.KichHoat_HoanThien_GoicuocDiDong()
                if (ktra && ktra.toString() !== '1') {
                  this.$root.showLoading(false)
                  this.$root.$toast.error('Đăng ký không thành công, chi tiết: ' + ktra + ' !!!')
                  return
                }
              }
              if (ma_km && ma_km.toString() === this.DATCOC.MA_CAMKET_CHONSO.toString()) {
                let ktra = await this.KichHoat_HoanThien_Camketchonso()
                if (ktra && ktra.toString() !== '1') {
                  this.$root.showLoading(false)
                  return
                }
              }
            }            
            if (this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()||this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG_ROAMING.toString()) {
              var ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', this.dataSelected.KHUYENMAI_ID)
              if (ma_km && ma_km.toString() === this.DATCOC.MA_TAMTHU_CUOCNONG.toString()) {
                let httId=this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()?'18':'29';
                let kq = await this.KichHoat_HoanThien_TamThuCuocNong(httId)
                if (kq === false) {
                  this.$root.showLoading(false)
                  this.$root.$toast.error('Đăng ký không thành công !!!')
                  return
                }
              }
            }
            if (this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_DATCOC_KYQUY.toString()) {
              //var ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', this.dataSelected.KHUYENMAI_ID)
              //if (ma_km && ma_km.toString() === this.DATCOC.NHOM_DATCOC_KYQUY.toString()) {
                let kq = await this.KichHoat_HoanThien_ThuKyQuy()
                if (kq === false) {
                  this.$root.showLoading(false)
                  this.$root.$toast.error('Đăng ký không thành công !!!')
                  return
                }
              //}
            }
          }
        }
        let HOANTHIEN_HS_DATCOC_MOI = await this.HOANTHIEN_HS_DATCOC_MOI(this.hdkh_id)
        if (!HOANTHIEN_HS_DATCOC_MOI) {
          this.$root.$toast.error('Lỗi hoàn thiện hồ sơ đặt cọc mới !!!')
          this.SetButton(0)
          this.$root.showLoading(false)
          return
        }
        await this.Capnhat_tt_home_lantoa(this.hdkh_id)
        await this.CN_TT_GDVP_VNP(this.hdtb_id)
        await this.CN_NGAY_BD(this.hdkh_id, '', 2)
        this.$root.$toast.success('Cập nhật dữ liệu thành công!')
        this.dgvDanhSach = []
        this.SetButton(0)
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
        this.$root.showLoading(false)
      }
      this.$root.showLoading(false)
    },
    async CN_TT_GDVP_VNP (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/cn-tt-gdvp-vnp?hdkh_id=' + hdkh_id)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async HOANTHIEN_HS_DATCOC_MOI (hdkh_id) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/hoanthien-hs-datcoc-moi?hdkh_id=' + hdkh_id)
        return true
      } catch (error) {
        return false
      }
    },
    async Capnhat_tt_home_lantoa (hdkh_id_datcoc) {
      try {
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/capnhat-tt-home-lantoa?hdkh_id_datcoc=' + hdkh_id_datcoc)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async kiemtra_km_2731 (kieu, ctkm_id, hdtb_id, ma_tb) {
      try {
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_km_2731', {kieu, ctkm_id, hdtb_id, ma_tb})
        return rs.data.result
      } catch (error) {
        return null
      }
    },
    async KICHHOAT_HOANTHIEN_2731 () {
      try {
        try {
          let ds = this.$refs.grid.getSelectedRecords()
          if (ds.length > 0) {
            let row = ds[0]
            let in_ma_tb = row.MA_TB
            let ckb_km = await this.kiemtra_km_2731(1, this.dataSelected.CHITIETKM_ID, this.hdtb_id, in_ma_tb)
            if (ckb_km === null || ckb_km === '') {
              this.$root.$toast.warning('Không lấy được thông tin kiểm tra đặt cọc')
              return 'Không lấy được thông tin kiểm tra đặt cọc'
            }
            if (ckb_km.toString() === '1' || ckb_km.toString() === '-1') {
              return ckb_km.toString()
            }
            if (ckb_km.toString() !== '1' && ckb_km.toString() !== '-1' && !ckb_km.toString().includes('OK-') && !ckb_km.toString().includes('NOTHING-')) {
              this.$root.$toast.warning('Lỗi khi kiểm tra ' + ckb_km)
              return ckb_km.toString()
            }
            if (ckb_km.toString().includes('OK-') || ckb_km.toString().includes('NOTHING-')) // Nếu khác stb type
            {
              let number
              let fiber_tv = ''
              let prgram_vasc = ''
              let user_name = in_ma_tb// tài khoản Mytv
              let stb_serial = ''
              let stb_type = ''
              let ngay_cn = await this.getNgay_Cn()
              let date = DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyyMMdd')
              let program_ = '2731'
              let note = 'KM sau BUY'
              if (stb_serial == 'NULL') { stb_serial = '' } else { stb_serial = ckb_km.toString().split('-')[2].toString() }
              stb_type = ckb_km.toString().split('-')[1].toString()
              fiber_tv = ckb_km.toString().split('-')[3].toString()
              let loi = await this.kichHoat(user_name, stb_serial, fiber_tv, stb_type)
              if (loi == 'OK' || loi == 'BOQUA') {
                try {
                  let keyParameter = 'ma_tb=' + user_name + ',program=2731' + ', ngaynt=' + date + ',KQ=OK'
                  await this.logAction('frmTBDatCoc', keyParameter, ' Ln:6312:Kich hoạt cập nhật dl dưới tỉnh thành công')
                  return '1'
                } catch (Exception) {
                }
                return '1'
              }
              return '-2'
            }
            return '-2'
          }
        } catch (error) {
          this.$root.$toast.warning('Có lỗi khi kiểm tra đặt cọc của thuê bao ' + error)
          return 'Có lỗi khi kiểm tra đặt cọc của thuê bao ' + error
        }
      } catch (error) {
        return '[ERROR] Có lỗi ' + error
      }
    },
    async hoanCongStb2515 (vuser_name, stb_serial, stb_type, date, note, visfirst) {
      // HOÀN CÔNG STB lên VASC
      try {
        let _result_STB = vascService.STB_PROM_Active(this, vuser_name, stb_serial, stb_type, date, 'BUY->KM2731', visfirst, '2731')

        if (_result_STB.code == 1) {
          // Log thành công
          let keyParameter = 'ma_tb=' + vuser_name + ',stb_serial=' + stb_serial + ',date=' + date + ',stb_type=' + stb_type + ',2731,KQ=1'
          await this.logAction('frmTBDatCoc', keyParameter, 'Ln:1770: Hoàn công và lưu dl dưới tỉnh thành công')
          return 1
        } else {
          let keyParameter = 'ma_tb=' + vuser_name + ',stb_serial=' + stb_serial + ',date=' + date + ',stb_type=' + stb_type + ',KQ=' + _result_STB.code + 'HDTB_ID=' + this.hdtb_id
          await this.logAction('frmTBDatCoc', keyParameter, ' Ln:1824: hoàn công thất bại')
          return 0
        }
      } catch (err) {
        // Log thất bại
        let keyParameter = 'ma_tb=' + vuser_name + ',stb_serial=' + stb_serial + ',date=' + date + ',stb_type=' + stb_type + ',KQ=0'
        await this.logAction('frmTBDatCoc', keyParameter, `Ln:1842: Hoàn công thất bại ${err}`)
        this.$root.toastError('Hoàn công STB tham gia chương trình thất bại ')
        return 0
      }
    },
    async logAction (action_name, key_parameter, note) {
      let ip = 'oneBSS'
      let data = {
        user_name: this.$root.token.getUserName(),
        computer_name: ip,
        action_name: action_name,
        key_parameter: key_parameter,
        server_name: ip,
        note: note
      }
      let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/log_action`, data)
      return response.data.KQ
    },
    async kichHoat (user_name, stb_serial, fiber_tv, stb_type) {
      try {
        let ngay_cn = await this.getNgay_Cn()
        let date = DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyyMMdd')
        let result = await vascService.STB_PROM_Register(this, user_name, stb_serial, fiber_tv, stb_type, '2731', 'BUY->KM2731')
        if (result.message) {
          this.$root.toastError(result.message)
        }
        let _re = parseInt(result.code)
        if (_re == 1) {
          let keyParameter = 'ma_tb=' + user_name + ',stb_serial=' + stb_serial + ',fiber_mytv=' + fiber_tv + ',stb_type=' + stb_type + ',2731,KQ=' + _re
          await this.logAction('frmTBDatCoc', keyParameter, ' Ln:6312:Kich hoạt cập nhật dl dưới tỉnh thành công')
          let k_ac = await this.hoanCongStb2515(user_name, stb_serial, stb_type, date, 'BUY->KM2731', true)
          if (k_ac == 1) {
            return 'OK'
          } else if (k_ac == -4) {
            return 'BOQUA'
          }
          return -1
        } else if (_re == -4) {
          let k_ac = await this.hoanCongStb2515(user_name, stb_serial, stb_type, date, 'BUY->KM2731', true)
          if (k_ac == 1) {
            return 'OK'
          } else if (k_ac == -4) {
            return 'BOQUA'
          } else {
            return 'BOQUA'
          }
        } else {
          let keyParameter = 'ma_tb=' + user_name + ',stb_serial=' + stb_serial + ',fiber_mytv=' + fiber_tv + ',stb_type=' + stb_type + ',2731,KQ=' + _re
          await this.logAction('frmTBDatCoc', keyParameter, ' Ln:6284: Bỏ qua kích hoạt')
          return 'BOQUA'
        }
      } catch (err) {
        let keyParameter = 'ma_tb=' + user_name + ',stb_serial=' + stb_serial + ',fiber_mytv=' + fiber_tv + ',stb_type=' + stb_type + ',2731,KQ=0'
        await this.logAction('frmTBDatCoc', keyParameter, ' Ln:6378: Lỗi ' + err)
        return 'BOQUA'
      }
    },
    async txtMaGD_KeyPress () {
      if (this.txtMaGD !== '') {
        this.$root.showLoading(true)
        var LAY_DS_HOPDONG_THEO_MA_GD = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.txtMaGD, this.LoaiHopDong.DATCOC_MOI, 0, 0)
        await this.HienThiTTHopDongKH(LAY_DS_HOPDONG_THEO_MA_GD)
        this.$root.showLoading(false)
      }
    },
    async LAY_DS_HOPDONG_THEO_MA_GD (ma_gd, loaihd_id, tthd_id, donvi_dl_id) {
      try {
        let data = {
          ma_gd, loaihd_id, tthd_id, donvi_dl_id
        }
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/lay-ds-hopdong-theo-ma-gd', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async KT_Trangthai_thanhtoan_hd (hdkh_id) {
      try {
        let data = {
          LOAI: 'PHIEUTT_HD',
          KHOA: 'HDKH_ID',
          GIATRI: hdkh_id
        }
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
        if (rs.data.length === 0) return false
        for (let index = 0; index < rs.data.length; index++) {
          const e = rs.data[index]
          if (e.TRANGTHAI === null || e.TRANGTHAI.toString() === '' || e.TRANGTHAI.toString() === '0') {
            return false
          }
        }
        return true
      } catch (error) {
        return false
      }
    },
    async generateInsert (hdtb_id, dsHDTB_CNTT) {
      try {
        if (dsHDTB_CNTT.length === 0) return []
        let ds = []
        let row = {}
        row.HDTB_ID = hdtb_id
        for (let index = 0; index < dsHDTB_CNTT.length; index++) {
          const e = dsHDTB_CNTT[index]
          if (e.sFieldName !== 'MA_TB') {
            if (e.iDataType === 2) {
              if (e.iAtt === 9) {
                // row[e.field_name] = e.sValue !== undefined ? e.sValue : null
                row = {...row, [e.sFieldName]: e.sValue !== undefined ? e.sValue : null }
              } else {
                row = {...row, [e.sFieldName]: e.sValue !== undefined && e.sValue !== null ? e.sValue.substring(0, 10) : null }
                // row[e.field_name] = e.sValue !== undefined && e.sValue !== null ? e.sValue.substring(0, 10) : null
              }
            } else {
              // row[e.field_name] = e.sValue !== undefined ? e.sValue : null
              row = {...row, [e.sFieldName]: e.sValue !== undefined ? e.sValue : null }
            }
          }
        }
        ds.push(row)
        return ds
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1910 ~ generateInsert ~ error', error)
        return []
      }
    },
    async filtering_nhomdatcoc (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NHOM', 'contains', e.text, true) : query
      e.updateData(this.data.DS_NHOM_DATCOC, query)
    },
    async filtering_tratruoc (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_KM', 'contains', e.text, true) : query
      e.updateData(this.data.DS_KHUYENMAI, query)
    },
    filtering_chitiet (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_CTKM', 'contains', e.text, true) : query
      e.updateData(this.data.DS_CHITIETKM, query)
    },
    async CapNhat () {
      try {
        this.$root.showLoading(true)
        if (this.enableCtrl.tsbtnNhapMoi === false) {
          let chk = await this.TaoDuLieu(true)
          if (!chk) {
            this.$root.$toast.error('Lỗi khởi tạo dữ liệu!!!')
          }
          if (this.loi_sinh_magd) {
            this.loi_sinh_magd = false
            this.$root.showLoading(false)
            return false
          }
          let result = await this.ThemMoi_HopDong_V1(this.dsHDKH, this.dsHDTB, this.hdtb_dc, this.dsCTTHD, this.dsHDTB_CNTT, this.huonggiaotn_id)
          if (result) {
            await this.HienThiDanhSacHDTB()
            this.inDex_load = 0
            this.$root.$toast.success('Thêm mới dữ liệu thành công!')
          } else {
            this.$root.$toast.error('Lỗi thêm mới dữ liệu!')
          }
        } else {
          let chk = await this.TaoDuLieu(false)
          if (!chk) {
            this.$root.$toast.error('Lỗi khởi tạo dữ liệu!!!')
          }
          let result = await this.CapNhat_HopDong_V1(this.dsHDKH, this.dsHDTB, this.hdtb_dc, this.dsCTTHD, this.dsHDTB_CNTT)
          if (result) {
            if (!this.THAMSO_MD.tinhcuoc_ngay) { await this.CN_NGAY_BD(this.hdtb_id, this.dtpNgayBD.value === null ? DateTimeLib.toDateDisplay(new Date()) : DateTimeLib.toDateDisplay(this.dtpNgayBD.value), 1) }
            await this.HienThiDanhSacHDTB()
            if (this.inDex_load !== 0) {
              // dgvDanhSach.CurrentCell = dgvDanhSach.Rows[inDex_load].Cells[0];
              // dgvDanhSach.CurrentRow.Selected = false;
              // dgvDanhSach.Rows[inDex_load].Selected = true;
            }
            this.$root.$toast.success('Cập nhật dữ liệu thành công!')
          } else {
            this.$root.$toast.error('Lỗi cập nhật dữ liệu!')
          }
        }
        this.enableCtrl.tsbtnThanhToan = (this.tthd_id === 1)
        this.enableCtrl.tsbtnCapNhatDB = (this.tthd_id === 2)
      } catch (error) {
        this.$root.$toast.error(error)
      }
      this.$root.showLoading(false)
    },
    async CapNhat_HopDong_V1 (dsHDKH, dsHDTB, dsGOI, dsCTTHD, dsHDTB_CNTT) {
      try {
        let dsCNTT = await this.generateInsert(this.hdtb_id, dsHDTB_CNTT)
        let encodedData = this.encodeBase64(JSON.stringify(dsCNTT))
        let hdtb_id = parseFloat(this.dsHDTB[0].HDTB_ID)
        for (let i = 0; i < dsHDKH.length; i++) {
          dsHDKH[i].TEN_KH = null
          dsHDKH[i].DIACHI_KH = null
        }
        for (let i = 0; i < dsHDTB.length; i++) {
          dsHDTB[i].TEN_TB = null
          dsHDTB[i].DIACHI_LD = null
          dsHDTB[i].DIACHI_TB = null
        }
        let data = {
          dsHDKH: JSON.stringify(dsHDKH),
          dsHDTB: JSON.stringify(dsHDTB),
          dsGOI: JSON.stringify(dsGOI),
          dsCTTHD: JSON.stringify(dsCTTHD),
          dsHDTB_CNTT: encodedData,
          hdtb_id: hdtb_id
        }
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/capnhat-hopdong-v4', data)
        if (rs.data === null) {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data.length > 0) {
          if (rs.data[0].result === 1) return true
          else return false
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 689 ~ CapNhat_HopDong_V1 ~ error', error)
        return false
      }
    },
    async HienThiDanhSacHDTB () {
      try {
        var dtList = await this.Lay_DS_HDTB_THEO_HDKH(this.hdkh_id)
        console.log('🚀 ~ file: index.vue ~ line 262 ~ HienThiDanhSacHDTB ~ dtList', dtList)
        if (dtList.length > 0) {
          this.dgvDanhSach = dtList
          let f_ttn = dtList.find(x => x.TIEN_TTN !== 0)
          if (f_ttn !== undefined) {
            this.chkPhiTTN = true
            this.phiTTN = f_ttn.TIEN_TTN
          }
        } else {
          this.dgvDanhSach = []
          this.SetButton(1)
        }
        if (dtList.length > 0) {
          this.enableCtrl.tsbtnThemTB = true
        }
        if (dtList.length > 1) {
          this.enableCtrl.tsbtnXoaTB = true
        }
      } catch (error) {

      }
    },
    async Lay_DS_HDTB_THEO_HDKH (hdkh_id) {
      try {
        let rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/lay-ds-hdtb-theo-hdkh?hdkh_id=' + hdkh_id)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 285 ~ Lay_DS_HDTB_THEO_HDKH ~ error', error)
        return []
      }
    },
    async CN_NGAY_BD (vhdtb_id, vngay_bd, vloai) {
      try {
        var data = {
          VHDTB_ID: vhdtb_id,
          VNGAY_BD: vngay_bd,
          VLOAI: vloai
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/cn_ngay_bd', data
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    encodeBase64 (data) {
      return Buffer.from(data).toString('base64')
    },
    async ThemMoi_HopDong_V1 (dsHDKH, dsHDTB, dsGOI, dsCTTHD, dsHDTB_CNTT, huonggiao_id) {
      let dsCNTT = await this.generateInsert(this.hdtb_id, dsHDTB_CNTT)
      let encodedData = this.encodeBase64(JSON.stringify(dsCNTT))
      for (let i = 0; i < dsHDKH.length; i++) {
        dsHDKH[i].TEN_KH = null
        dsHDKH[i].DIACHI_KH = null
      }
      for (let i = 0; i < dsHDTB.length; i++) {
        dsHDTB[i].TEN_TB = null
        dsHDTB[i].DIACHI_LD = null
        dsHDTB[i].DIACHI_TB = null
      }
      try {
        let data = {
          dsHDKH: JSON.stringify(dsHDKH),
          dsHDTB: JSON.stringify(dsHDTB),
          dsGOI: JSON.stringify(dsGOI),
          dsCTTHD: JSON.stringify(dsCTTHD),
          dsHDTB_CNTT: encodedData,
          hdtb_id: parseFloat(this.dsHDTB[0].HDTB_ID),
          huonggiao_id: huonggiao_id
        }
        console.log('🚀 ~ file: index.vue:1424 ~ ThemMoi_HopDong_V1 ~ data:', data)
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong-v4', data)
        if (rs.data === null) {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data.length > 0) {
          if (rs.data[0].result.toString() === '1') return true
          else {
            this.$root.$toast.error(rs.data[0].result)
            return false
          }
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', error)
        return false
      }
    },
    async objLHD_Insert (hdtb_id, dsHDTB_CNTT) {},
    async objTien_Insert (dsCTTHD) {},
    async objHDKH_Insert (dsHDKH) {},
    async objHDTB_Insert (dsHDTB) {},
    async objGOi_Insert (dsGOI) {},
    async TaoDuLieu (themmoi) {
      let chk = await this.TaoDuLieu2(themmoi, false)
      return chk
    },
    async TaoDuLieu2 (themmoi, KoTaoHDKH) {
      try {
        if (!KoTaoHDKH) { await this.TaoDuLieu_HDKH(themmoi) }
        if (this.loi_sinh_magd) return false
        if (!this.dichvuvt_id || this.dichvuvt_id === 0) {
          console.log('Dịch vụ viễn thông = ', this.dichvuvt_id)
          return false;
        }
        await this.TaoDuLieu_HDTB(themmoi)
        await this.TaoDuLieu_HDTB_DATCOC(themmoi)
        await this.TaoDuLieu_CT_TIENHD(themmoi)
        if (this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.DICHVU_CNTT ||
                this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.HOINGHI_TRUYENHINH ||
                this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.TRUNGTAM_DULIEU) { await this.Tao_HDTB_CNTT(themmoi) }
        return true
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1233 ~ TaoDuLieu2 ~ error', error)
        return false
      }
    },
    CopyRow (drSource, drTarget) {
      try {
        for (let index = 0; index < drTarget.length; index++) {
          const e = drTarget[index]
          if (e.sFieldName === 'MA_TB') {
            continue
          }
          try {
            let col = e.sFieldName.toLowerCase()
            e.sValue = drSource[col]
          } catch (error) {
            console.log('🚀 ~ file: index.vue ~ line 1301 ~ CopyRow ~ error', error)
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1301 ~ CopyRow ~ error', error)
      }
    },
    async Tao_HDTB_CNTT (themmoi) {
      try {
        var dt = await this.LAY_DS_DB_CNTT_THEO_TBID(this.thuebao_id)
        if (dt.length > 0) {
          this.CopyRow(dt[0], this.ItemList)
        }
        for (let index = 0; index < this.ItemList.length; index++) {
          const e = this.ItemList[index]
          if (this.chkThoiHan) {
            if (e.sFieldName === 'SO_THANG') {
              e.sValue = parseInt(this.txtThoiHan)
            }
            if (e.sFieldName === 'NGAY_BD') {
              e.sValue = DateTimeLib.toDateDisplayWithFormat(this.dtpTuNgay.value, 'YYYY-MM-DD')
            }
            if (e.sFieldName === 'NGAY_KT') {
              e.sValue = DateTimeLib.toDateDisplayWithFormat(this.dtpDenNgay.value, 'YYYY-MM-DD')
            }
          }
        }
        this.dsHDTB_CNTT = [...this.ItemList]
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1543 ~ Tao_HDTB_CNTT ~ error:', error)
      }
    },
    async LAY_DS_DB_CNTT_THEO_TBID (in_thuebao_id) {
      try {
        var data = {in_thuebao_id}
        var rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_db_cntt_theo_tbid', data)
        return rs.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1510 ~ LAY_DS_DB_CNTT_THEO_TBID ~ error:', error)
        return []
      }
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      this.dsCTTHD = []
      var row = {}
      row.HDTB_ID = this.hdtb_id
      if (themmoi === true) {
        this.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
      }
      row.CT_TIENHD_ID = this.CT_TIENHD_ID
      row.KHOANMUCTT_ID = 11
      var tien
      var vat
      tien = (Math.round(parseFloat(this.txtCuocDC) / 1.1))
      vat = (parseFloat(this.txtCuocDC) - tien)
      row.TIEN = tien
      row.VAT = vat
      row.ID = 11
      row.LOAI_ID = 1
      this.dsCTTHD.push(row)
      if (this.txtUuDaiLanToa !== '' && this.txtUuDaiLanToa.toString() !== '0') {
        var rowUD = {}
        rowUD.HDTB_ID = this.hdtb_id
        if (themmoi === true) {
          this.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
        }
        rowUD.CT_TIENHD_ID = this.CT_TIENHD_ID
        rowUD.KHOANMUCTT_ID = 8800
        var tienUD
        tienUD = Math.round(parseFloat(this.txtUuDaiLanToa))
        rowUD.TIEN = -1 * tienUD
        rowUD.VAT = 0
        rowUD.ID = 8800
        rowUD.LOAI_ID = 1
        this.dsCTTHD.push(rowUD)
      }
      console.log(this.chkPhiTTN, this.khoanmucPhiTTN.TYLE_VAT)
      if (this.chkPhiTTN && this.khoanmucPhiTTN.TYLE_VAT !== undefined) {
        let tienTTN = (Math.round(parseFloat(this.phiTTN) / (1 + this.khoanmucPhiTTN.TYLE_VAT/100)))
        let vatTTN = (parseFloat(this.phiTTN) - tienTTN)
        let rowHD = {
          CT_TIENHD_ID: 0,
          KHOANMUCTT_ID:  3421,
          HDTB_ID: this.hdtb_id,
          TIEN: tienTTN,
          VAT: vatTTN,
          ID: 3421,
          LOAI_ID: 1
        }
        if (themmoi) {
          rowHD.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
        } else {
          let f = this.dgvDanhSach.find(x => x.TTN_ID !== null)
          if (f !== undefined) {
            rowHD.CT_TIENHD_ID = f.TTN_ID
          } else {
            rowHD.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
          }
        }
        
        this.dsCTTHD.push(rowHD)
      }
      console.log(this.dsCTTHD)
    },
    async TaoDuLieu_HDTB_DATCOC (themmoi) {
      this.hdtb_dc = []
      var rowGOI = {}
      if (themmoi === true) {
        this.thuebao_dc_id = await this.GetKey('THUEBAO_DC_ID', 'CSS')
        this.rkm_id = await this.GetKey('RKM_ID', 'CSS')
      }
      rowGOI.RKM_ID = this.rkm_id
      rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id
      rowGOI.HDTB_ID = this.hdtb_id
      rowGOI.NHOM_DATCOC_ID = this.dataSelected.NHOM_DATCOC_ID

      rowGOI.THANG_BD = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
      var thang_kt = 0
      thang_kt = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'MM'))
      rowGOI.THANG_KT = thang_kt
      var thang_bd_mg = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBDMG.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBDMG.value, 'MM'))
      var thang_kt_mg = 0
      thang_kt_mg = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangKTMG.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangKTMG.value, 'MM'))
      if (this.txtSoThangMG.toString() === '0') {
        thang_bd_mg = 0
        thang_kt_mg = 0
      }
      // Cuoc ngay
      rowGOI.NGAY_BDDC = DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
      rowGOI.NGAY_KTDC = DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)

      rowGOI.NGAY_BD = DateTimeLib.toDateDisplay(this.dtpNgayBD.value)
      rowGOI.NGAY_KT = DateTimeLib.toDateDisplay(this.dtpNgayKT.value)
      // Cuoc ngay

      rowGOI.THANG_BD_MG = thang_bd_mg
      rowGOI.THANG_KT_MG = thang_kt_mg
      rowGOI.NGAY_DK = DateTimeLib.toDateDisplay(this.dtpNgayYeuCau.value)
      rowGOI.CHITIETKM_ID = this.dataSelected.CHITIETKM_ID
      rowGOI.KHUYENMAI_ID = this.dataSelected.KHUYENMAI_ID
      rowGOI.GHICHU = this.txtGhiChu
      rowGOI.CUOC_DC = parseFloat(this.txtCuocDC)
      rowGOI.TIEN_TD = parseFloat(this.txtTienTD)

      rowGOI.TYLE_TB = parseFloat(this.txtTyleTB)
      rowGOI.TYLE_SD = parseFloat(this.txtTyleSD)
      rowGOI.TIEN_TB = parseFloat(this.txtTienTB)
      rowGOI.TIEN_SD = parseFloat(this.txtTienSD)

      this.hdtb_dc.push(rowGOI)
    },
    async TaoDuLieu_HDTB (themmoi) {
      this.dsHDTB = []
      if (themmoi === true) { this.hdtb_id = await this.GetKey('HD_THUEBAO', 'CSS') }
      var rowHDTB = {}
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) { rowHDTB.THUEBAO_ID = this.thuebao_id } else { rowHDTB.THUEBAO_ID = null }
      rowHDTB.MA_TB = this.txtMaTB
      rowHDTB.TEN_TB = this.ten_tb
      rowHDTB.DIACHI_LD = this.diachi_ld
      rowHDTB.DIACHI_TB = this.diachi_ld
      rowHDTB.KIEULD_ID = 550
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = 1
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.DONVI_ID = this.donvi_id
      var dset = await this.LayHuongGiao_TiepNhan_v2(31, 550, this.dichvuvt_id)
      if (dset.length > 0) {
        this.huonggiaotn_id = parseInt(dset[0].huonggiao_id)
        this.quytrinh_id = parseInt(dset[0].quytrinh_id)
      }
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      this.dsHDTB.push(rowHDTB)
    },
    async LayHuongGiao_TiepNhan (loaihd_id, kieuld_id) {
      try {
        var data = {
          VLOAIHD_ID: loaihd_id,
          VKIEULD_ID: kieuld_id
        }
        var rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/hddc_layhuonggiao_tiepnhan', data)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async LayHuongGiao_TiepNhan_v2 (loaihd_id, kieuld_id, dichvuvt_id) {
      try {
        var data = {
          VLOAIHD_ID: loaihd_id,
          VKIEULD_ID: kieuld_id,
          VDICHVUVT_ID: dichvuvt_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/hddc_layhuonggiao_tiepnhan_v2', data
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async TaoDuLieu_HDKH (themmoi) {
      var vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi === true) {
        this.hdkh_id = await this.GetKey('HD_KHACHHANG', 'CSS')
        this.nhomtb_id = await this.GetKey('NHOMTB', 'CSS')
      }
      var rowHDKH = {}
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi === true) {
        if (this.THAMSO_MD.ts_sinhma_gd_theo_donvi) { vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), 31) } else { vmagd_temp = await this.Lay_Ma_GD(31) }
        if (vmagd_temp.includes('ERROR:') || vmagd_temp.includes('error:')) {
          this.$root.$toast.warning(vmagd_temp.split(':')[1])
          this.loi_sinh_magd = true
        }
        this.txtMaGD = vmagd_temp
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.NGAY_YC = DateTimeLib.toDateDisplay(new Date())
      } else {
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.NGAY_YC = DateTimeLib.toDateDisplay(this.dtpNgayYeuCau.value)
      }
      rowHDKH.MA_KH = this.ma_kh
      if (this.khachhang_id > 0) { rowHDKH.KHACHHANG_ID = this.khachhang_id } else { rowHDKH.KHACHHANG_ID = null }
      rowHDKH.KHLON_ID = this.khlon_id
      rowHDKH.TEN_KH = this.ten_kh
      rowHDKH.DIACHI_KH = this.diachi_kh
      rowHDKH.NGAYLAP_HD = DateTimeLib.toDateDisplay(this.dtpNgayLHD)
      rowHDKH.GHICHU = this.txtGhiChu
      rowHDKH.DONVI_ID = this.$root.token.getDonViID()
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
      rowHDKH.LOAIHD_ID = 31
      rowHDKH.OB_ID = this.ob_id
      if (this.chkCTV && this.txtCTV !== '') {
        rowHDKH.CTV_ID = this.ctv_id
      } else rowHDKH.CTV_ID = null
      if (this.chkNguoiGT && this.txtNguoiGT !== '') {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      } else { rowHDKH.NHANVIENGT_ID = null }
      this.dsHDKH.push(rowHDKH)
    },
    async Lay_Ma_GD (VLOAIHD_ID) {
      try {
        var data = {VLOAIHD_ID}
        var rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sinh_magd', data)
        if (rs.data.length > 0) {
          return rs.data[0].kq
        } else { return null }
      } catch (error) {
        return null
      }
    },
    async Lay_Ma_GD_V2 (vdonvi_id, loaihd_id) {
      try {
        var data = {
          VDONVI_ID: vdonvi_id,
          VLOAIHD_ID: loaihd_id
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/sinh_magd_v2', data
        )
        if (rs.data.length > 0) {
          return rs.data[0].kq
        } else { return null }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1707 ~ Lay_Ma_GD_V2 ~ error:', error)
        return null
      }
    },
    async GetKey (Key, SCHEMA) {
      try {
        var data = {
          VKEYNAME: Key,
          VKEY_SCHEMA: SCHEMA
        }
        var rs = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/get_keys', data
        )
        if (rs.data.length > 0) {
          return rs.data[0].key
        } else { return null }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1724 ~ GetKey ~ error:', error)
        return null
      }
    },
    async Kiemtra_Dulieu (themmoi) {
      if (themmoi) {
        if (this.kieu_dl === 0) {
          this.$root.$toast.warning('Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.')
          return false
        }
        if (this.thuebao_id <= 0) {
          this.$root.$toast.warning('Không có thông tin thuê bao!')
          this.$refs.txtMaTB.focus()
          return false
        }
        var SP_CHECK_TT_HDTB = await this.SP_CHECK_TT_HDTB(this.thuebao_id)
        if (SP_CHECK_TT_HDTB.length > 0) {
          this.$root.$toast.warning('Thuê bao đang có hợp đồng/ phụ lục: ' + SP_CHECK_TT_HDTB[0].ten_loaihd + ', mã giao dịch: ' + SP_CHECK_TT_HDTB[0].ma_gd + ' chưa hoàn thiện!')
          return false
        }
      }
      if (this.THAMSO_MD.phainhap_thoihan === true) {
        var tt = this.MAP_ID('TRATRUOC', 'CSS', 'LOAIHINH_TB', 'LOAITB_ID', this.dataSelected.LOAITB_ID)
        if (tt.toString() === '1') {
          if (this.chkThoiHan === false) {
            this.$root.$toast.warning('Bạn phải chọn thời hạn !')
            return false
          }
        }
      }
      if (this.thuebao_id <= 0) {
        this.$root.$toast.warning('Không có thông tin thuê bao!')
        this.$refs.txtMaTB.focus()
        return false
      }
      if (this.dataSelected.KHUYENMAI_ID === null) {
        this.$root.$toast.warning('Bạn hãy chọn khuyến mại !')
        return false
      }
      if (this.dataSelected.CHITIETKM_ID === null) {
        this.$root.$toast.warning('Bạn hãy chọn trả trước !')
        return false
      }
      if (this.txtMaTB === '') {
        this.$root.$toast.warning('Bạn chưa nhập số máy/account !')
        this.$refs.txtMaTB.focus()
        return false
      }
      if (this.chkThangMG) {
        if (this.txtSoThangMG === '') {
          this.$root.$toast.warning('Hãy nhập số tháng miễn giảm')
          return false
        }
      }
      if (this.txtSoThang === '') {
        this.$root.$toast.warning('Bạn chưa nhập số tháng phải đặt cọc')
        return false
      }

      if (this.txtCuocDC === '') {
        this.$root.$toast.warning('Bạn chưa nhập cước đặt cọc !')
        this.$refs.txtCuocDC.focus()
        return false
      }
      let ktdatcoc_khongdong = await this.KIEMTRA_DATCOC_CA(this.thuebao_id)
      if (ktdatcoc_khongdong !== 'ok') {
        if (parseFloat(this.txtCuocDC) <= 0) {
          this.$root.$toast.warning('Tiền đặt cọc phải lớn hơn 0')
          this.$refs.txtCuocDC.focus()
          return false
        }
      }
      if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString() &&
                 this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
        if (parseFloat(this.txtTienTB) < 0) {
          this.$root.$toast.warning('Tiền TB không được nhỏ hơn 0')
          return false
        }
        if (parseFloat(this.txtCuocDC) < parseFloat(this.txtTienTB)) {
          this.$root.$toast.warning('Cước ĐC không được nhỏ hơn Tiền TB !')
          this.$refs.txtCuocDC.focus()
          return false
        }
      }
      if (this.txtTienTD === '') {
        this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng!')
        this.$refs.txtTienTD.focus()
        return false
      }

      if (this.dataSelected.NHOM_DATCOC_ID.toString() === '1' || this.dataSelected.NHOM_DATCOC_ID.toString() === '4') {
        if (parseFloat(this.txtTienTD) <= 0) {
          this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng!')
          this.$refs.txtTienTD.focus()
          return false
        }
      }

      if (this.txtTyleTB !== '') {
        if (parseFloat(this.txtTyleTB) > 100 || parseFloat(this.txtTyleTB) < 0) {
          this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước thuê bao')
          return false
        }
      }

      if (this.txtTyleSD !== '') {
        if (parseFloat(this.txtTyleSD) > 100 || parseFloat(this.txtTyleSD) < 0) {
          this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước sử dụng')
          return false
        }
      }
      if (this.isTest) {
        this.THAMSO_MD.nhap_CTV = 0
      }
      if (this.THAMSO_MD.nhap_CTV === 1) {
        if (!this.chkCTV || this.txtCTV === '') {
          this.$root.$toast.warning('Hãy chọn cộng tác viên')
          return false
        }
      }
      if (this.THAMSO_MD.nhap_NGT === 1) {
        if (!this.chkNguoiGT || this.txtNguoiGT === '') {
          this.$root.$toast.warning('Hãy chọn Người giới thiệu')
          return false
        }
      }
      if (themmoi) {
        let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
        let ngay_cn = await this.getNgay_Cn()
        if (thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyyMM'))) {
          this.$root.$toast.warning('Khi thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại!')
          return false
        }
        if (this.thuebao_id !== 0) {
          let Kiemtra_loaitb_id_db_thuebao = await this.Kiemtra_loaitb_id_db_thuebao(this.thuebao_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID)
          if (!Kiemtra_loaitb_id_db_thuebao) {
            this.$root.$toast.warning('Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường.' + this.txtMaTB + '')
            return false
          }
        }

        if (this.dataSelected.DICHVUVT_ID === 15 || this.dataSelected.DICHVUVT_ID === 14 ||
                this.dataSelected.DICHVUVT_ID === 16 || this.dataSelected.DICHVUVT_ID === 13) {
          if (this.THAMSO_MD.check_chuahoa_cntt) {
            let check_chuahoa_cntt = await this.check_chuahoa_cntt(this.thuebao_id, 31)
            if (check_chuahoa_cntt) {
              this.$root.$toast.warning('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
              return false
            }
          }
        }
        if (this.THAMSO_MD.kiemtra_no_tratruoc === 1) {
          let tienno = await this.TraCuu_No_MaTB(this.txtMaTB)
          if (tienno > 0) {
            this.$root.$toast.warning('Thuê bao: ' + this.txtMaTB +
                        ' còn nợ: ' + parseFloat(tienno) +
                        ' vnđ.\nPhải thanh toán trước khi thực hiện!')
            return false
          }
        }
      } else {
        let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
        let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
        let in_loaitb_id = parseInt(CurrentRow.LOAITB_ID)
        let in_thang_bd = parseInt(CurrentRow.THANG_BD)
        let in_thang_kt = parseInt(CurrentRow.THANG_KT)
        if (in_thang_bd > 0) {
          let ngay_cn = await this.getNgay_Cn()
          if (in_thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'MM'))) {
            if (in_thang_bd !== thang_bd) {
              this.$root.$toast.warning('Tháng bắt đầu đang nhỏ hơn tháng hiện tại. Bạn không được thay đổi tháng bắt đầu!')
              return false
            }
          }
        }
        let in_user_cn = CurrentRow.NGUOI_CN
        let ma_nd = this.$auth.getUserName()
        if (!this.enableCtrl.chkCTV && in_user_cn.toString() !== ma_nd.toString()) {
          this.$root.$toast.warning('Bạn không được thay đổi thông tin do user ' + in_user_cn + ' cập nhật')
          return false
        }

        if (this.tthd_id > 1) {
          let trangthai_hd = await this.trangthai_hd(this.tthd_id)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ', bạn không thể thay đổi!')
          return false
        }
      }
      var thangbdmoi = 0
      var thangktmoi = 0
      thangbdmoi = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
      thangktmoi = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'MM'))

      if (this.THAMSO_MD.tinhcuoc_ngay) {
        if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
          if (this.dtpNgayBDDC.value < moment(this.dtpThangBD.value).startOf('M') || this.dtpNgayBDDC.value > moment(this.dtpThangBD.value).endOf('M')) {
            this.$root.$toast.warning('Ngày bắt đầu đặt cọc phải nằm trong tháng bắt đầu đặt cọc!')
            return false
          }
          if (this.dtpNgayBD.value < moment(this.dtpThangBDMG.value).startOf('M') || this.dtpNgayBD.value > moment(this.dtpThangBDMG.value).endOf('M')) {
            this.$root.$toast.warning('Ngày bắt đầu miễn giảm phải nằm trong tháng bắt đầu miễn giảm!')
            return false
          }
        }
      }

      var ngaydbdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
      var ngayktdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)

      if (themmoi) {
        if (this.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
            let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(this.thuebao_id, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, this.dataSelected.NHOM_DATCOC_ID)
            if (Kiemtra_tb_datcoc !== 'ok' && Kiemtra_tb_datcoc !== 'OK') {
              this.$root.$toast.warning(Kiemtra_tb_datcoc)
              return false
            }
          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        let dt = await this.SP_CHECK_TT_HDTB2(this.txtMaTB, this.dataSelected.LOAITB_ID)
        console.log('🚀 ~ file: index.vue:2000 ~ Kiemtra_Dulieu ~ dt:', dt)
        if (dt && dt.length > 0) {
          this.$root.$toast.warning('Thuê bao đã có hợp đồng: ' + dt[0].ten_loaihd + ', mã giao dịch: ' + dt[0].ma_gd + ' chưa hoàn thiện, bạn không thể lập hợp đồng!')
          return false
        }
      } else {
        let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_ma_tb = CurrentRow.MA_TB
        // let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
        // let in_thuebao_dc_id = parseInt(CurrentRow.THUEBAO_DC_ID)
        if (this.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
            let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(this.thuebao_id, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, this.dataSelected.NHOM_DATCOC_ID)
            if (Kiemtra_tb_datcoc !== 'ok') {
              this.$root.$toast.warning(Kiemtra_tb_datcoc)
              return false
            }
          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(this.thuebao_dc_id, this.txtMaTB.trim(), this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Thuê bao đang còn thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        if (in_ma_tb.trim() !== this.txtMaTB.trim()) {
          this.$root.$toast.warning('Bạn không được thay đổi số máy/account')
          return false
        }
      }
      try {
        if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString()) {
          let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
          let in_ma_tb = this.txtMaTB
          if (!themmoi) { in_ma_tb = CurrentRow.MA_TB }
          var ckb_km = await this.kiemtra_km_2731('0', this.dataSelected.CHITIETKM_ID, 0, in_ma_tb)
          if (ckb_km === null || ckb_km === '') {
            this.$root.$toast.warning('Không lấy được thông tin kiểm tra đặt cọc')
            return false
          }
          if (ckb_km.toString() !== '1' && ckb_km.toString() !== '-1') {
            this.$root.$toast.warning(ckb_km.toString())
            return false
          }
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi kiểm tra đặt cọc của thuê bao:' + error)
        return false
      }
      return true
    },
    async check_chuahoa_cntt (thuebao_id, loaihd_id) {
      try {
        var data = {
          thuebao_id,
          loaihd_id
        }
        var rps = await this.$root.context.post('/web-hopdong/chuyendich/check_chuahoa_cntt', data)
        return rps.data.toString() === '1'
      } catch (error) {
        return false
      }
    },
    async KIEMTRA_DATCOC_CA (thuebao_id) {
      try {
        var data = {
          P_THUEBAO: thuebao_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_datcoc_ca', data)
        if (rps.data && rps.data.length > 0) {
          return rps.data[0].ketqua
        } else {
          return 'no'
        }
      } catch (error) {
        return 'no'
      }
    },
    async SP_CHECK_TT_HDTB2 (ma_tb, loaitb_id) {
      try {
        var data = {
          VMA_TB: ma_tb,
          VLOAITB_ID: loaitb_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_check_tt_hdtb2', data)
        return rps.data
      } catch (error) {
        return []
      }
    },
    async Kiemtra_tb_datcoc (thuebao_dc_id, vma_tb, vdichvuvt_id, vloaitb_id, vthang_bd, vthang_kt, vnhom_datcoc_id) {
      try {
        let data = {
          P_THUEBAO_DC_ID: thuebao_dc_id,
          P_VMA_TB: vma_tb,
          P_VDICHVUVT_ID: vdichvuvt_id,
          P_VLOAITB_ID: vloaitb_id,
          P_VTHANG_BD: vthang_bd,
          P_VTHANG_KT: vthang_kt,
          P_VNHOM_DATCOC_ID: vnhom_datcoc_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_tb_datcoc', data)
        if (rps.data.length > 0) {
          if (rps.data[0].ketqua.toString() === '1') return true
          else return false
        } else return false
      } catch (error) {
        return false
      }
    },
    async SP_KT_THOIGIAN_DATCOC (V_THUEBAO_ID, V_THANG_BDDC, V_THANG_KTDC, V_NGAY_BDDC, V_NGAY_KTDC, V_NHOM_DATCOC_ID) {
      try {
        let data = {V_THUEBAO_ID, V_NHOM_DATCOC_ID, V_THANG_BDDC, V_THANG_KTDC, V_NGAY_BDDC, V_NGAY_KTDC}
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_kt_thoigian_datcoc', data)
        if (rps.data && rps.data.length > 0) {
          return rps.data[0].kq
        } else return 'ok'
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1985 ~ SP_KT_THOIGIAN_DATCOC ~ error:', error)
        return 'ok'
      }
    },
    async Kiemtra_datcoc_tyle_KM_v1 (thuebao_dc_id, vma_tb, vdichvuvt_id, vloaitb_id, vthang_bd, vthang_kt, vnhom_datcoc_id) {
      try {
        let data = {
          P_THUEBAO_DC_ID: thuebao_dc_id,
          P_VMA_TB: vma_tb,
          P_VDICHVUVT_ID: vdichvuvt_id,
          P_VLOAITB_ID: vloaitb_id,
          P_VTHANG_BD: vthang_bd,
          P_VTHANG_KT: vthang_kt,
          P_VNHOM_DATCOC_ID: vnhom_datcoc_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_datcoc_tyle_km_v1', data)
        if (rps.data.length > 0) {
          if (rps.data[0].ketqua.toString() === '1') return true
          else return false
        } else return true
      } catch (error) {
        return true
      }
    },
    async Months_between (vthang_bd, vthang_kt) {
      try {
        var rps = await this.$root.context.get('/web-cabman/ban-do-mang-cap/months-between?thang_bd=' + vthang_bd + '&thang_kt=' + vthang_kt)
        if (rps.data.length > 0) {
          return parseInt(rps.data[0].SOTHANG)
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async Add_months (vthang, vsothang) {
      try {
        var rps = await this.$root.context.get('/web-cabman/ban-do-mang-cap/add-months?thang=' + vthang + '&sothang=' + vsothang)
        if (rps.data.length > 0) {
          return parseInt(rps.data[0].THANG_KT)
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async TraCuu_No_MaTB (ma_tb) {
      var result = 0
      try {
        if (this.thuebao_id !== 0) {
          var no_tb = await this.TRACUUNO_THUEBAO(this.thuebao_id).toString()
          result = parseFloat(no_tb)
        } else {
          this.$root.$toast.warning('Chưa có thông tin về thuê bao!')
        }
      } catch (error) {

      }
      return result
    },
    async TRACUUNO_THUEBAO (thuebao_id) {
      try {
        var data = {
          vthuebao_id: thuebao_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/tracuuno_thuebao', data)
        if (rps.data.length > 0) {
          return rps.data[0].no_thuebao
        } else return 0
      } catch (error) {
        return 0
      }
    },
    async Kiemtra_loaitb_id_db_thuebao (thuebao_id, vma_tb, vdichvuvt_id, vloaitb_id) {
      try {
        var data = {
          VTHUEBAO_ID: thuebao_id,
          VMA_TB: vma_tb,
          VDICHVUVT_ID: vdichvuvt_id,
          VLOAITB_ID: vloaitb_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_loaitb_id_db_thuebao', data)
        if (rps.data.length > 0) {
          if (parseInt(rps.data[0].sl) > 0) return true
          else return false
        } else return false
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2157 ~ Kiemtra_loaitb_id_db_thuebao ~ error:', error)
        return false
      }
    },
    async SP_CHECK_TT_HDTB (thuebao_id) {
      try {
        var data = {
          VTHUEBAO_ID: thuebao_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_check_tt_hdtb', data)
        return rps.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2169 ~ SP_CHECK_TT_HDTB ~ error:', error)
        return []
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_v2 (ma_tb, ma_gd, vdichvuvt_id, vloaitb_id, vloaihd_id) {
      try {
        var data = {
          vma_tb: ma_tb,
          vma_gd: ma_gd,
          vdichvuvt_id: vdichvuvt_id,
          vloaitb_id: vloaitb_id,
          vloaihd_id: vloaihd_id,
          vkieuld_id: 0
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_ma_tb_lap_hopdong_khac_v2', data)
        return rps.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 224 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_VTT ~ error', error)
        return []
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1 (ma_tb, ma_gd, vdichvuvt_id, vloaitb_id, vloaihd_id) {
      var result = false
      try {
        if (ma_tb === null) return result
        var ds = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_v2(ma_tb, ma_gd, vdichvuvt_id, vloaitb_id, vloaihd_id)
        if (ds.length > 0) {
          result = true
          var message = 'Số máy/Acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + ds[0].ten_loaihd + ' có mã giao dịch ' + ds[0].ma_gd + ' do User ' + ds[0].nguoi_cn + ' thuộc đơn vị ' + ds[0].ten_dv + ' thực hiện vào ngày ' + DateTimeLib.toDateDisplay(ds[0].ngay_yc)
          this.$root.$toast.warning(message)
        }
        return result
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2203 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1 ~ error:', error)
        return result
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2 (ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
      var result = false
      try {
        if (ma_tb === null) return result
        var ds = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id)
        if (ds.length > 0) {
          result = true
          var message = 'Số máy/Acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + ds[0].ten_loaihd + ' có mã giao dịch ' + ds[0].ma_gd + ' do User ' + ds[0].nguoi_cn + ' thuộc đơn vị ' + ds[0].ten_dv + ' thực hiện vào ngày ' + DateTimeLib.toDateDisplay(ds[0].ngay_yc)
          this.$root.$toast.warning(message)
        }
        return result
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2218 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2 ~ error:', error)
        return result
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC (ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
      try {
        var data = {
          vma_tb: ma_tb,
          vma_gd: ma_gd,
          vdichvuvt_id: vdichvuvt_id,
          vloaihd_id: vloaihd_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_ma_tb_lap_hopdong_khac', data)
        return rps.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 224 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_VTT ~ error', error)
        return []
      }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_VTT (ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
      try {
        var data = {
          vma_tb: ma_tb,
          vma_gd: ma_gd,
          vdichvuvt_id: vdichvuvt_id,
          vloaihd_id: vloaihd_id
        }
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_ma_tb_lap_hopdong_khac_vtt', data)
        return rps.data
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 224 ~ KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_VTT ~ error', error)
        return []
      }
    },
    async KiemTraMaTBLapHopDongKhac (ma_tb, ma_gd, vdichvuvt_id, vloaihd_id) {
      var result = false
      try {
        if (ma_tb === null) return result
        var ds = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_VTT(ma_tb, ma_gd, vdichvuvt_id, vloaihd_id)
        if (ds.length > 0) {
          result = true
          var message = 'Số máy/acc: ' + ma_tb + ' đang được lập bởi hợp đồng (phụ lục) ' + ds[0].ten_loaihd + ' có mã giao dịch ' + ds[0].ma_gd + ' do User ' + ds[0].nguoi_cn + ' thuộc đơn vị ' + ds[0].ten_dv + ' thực hiện vào ngày ' + DateTimeLib.toDateDisplay(ds[0].ngay_yc)
          this.$root.$toast.warning(message)
        }
        return result
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1600 ~ KiemTraMaTBLapHopDongKhac ~ error', error)
        return result
      }
    },
    ChuanHoaTen: function (ten) {
      if (ten != null) {
        let convertToArray = ten.toLowerCase().split(' ')
        let result = convertToArray.map(function (val) {
          return val.replace(val.charAt(0), val.charAt(0).toUpperCase())
        })
        return result.join(' ')
      } else {
        return ''
      }
    },
    async txtMaTB_KeyPress () {
      try {
        this.$root.showLoading(true)
        this.THUEBAO = {}
        if (this.txtMaTB) {
          if (this.THAMSO_MD.khong_load_ctv === 0) this.chkCTV = false
          this.chkNguoiGT = false
          await this.LAY_DS_HOPDONG_THEO_MA_TB(this.txtMaTB, this.DATCOC_MOI, this.$root.token.getDonViID(), this.MOI, this.$root.token.getNhanVienID(), 0, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID)
          console.log('🚀 ~ file: index.vue:2443 ~ txtMaTB_KeyPress ~ this.data.DS_HOPDONG:', this.data.DS_HOPDONG)
          if (this.data.DS_HOPDONG.length > 0) {
            await this.HienThiTTHopDongKH(this.data.DS_HOPDONG)
          } else {
            this.dichvuvt_id = parseInt(this.dataSelected.DICHVUVT_ID)
            await this.HienThiTT_DanhBa(this.txtMaTB, this.dichvuvt_id)
            if (this.data.DS_TT_DANHBA.length > 0) {
              if (this.data.DS_TT_DANHBA.length > 1) {
                this.$root.showLoading(false)
                let kq = await this.$refs['frmDSTBCungMa'].show(this.toLowerCases(this.data.DS_TT_DANHBA))
                if (kq.ok === true && kq.data) {
                  let data = []
                  data.push(kq.data)
                  Vue.set(this.data, 'DS_TT_DANHBA', this.toUpperCases(data))
                }
                this.$root.showLoading(true)
              }
              this.thuebao_id = parseFloat(this.data.DS_TT_DANHBA[0].THUEBAO_ID)
              this.chitiet_km_theo_tbid = await this.sp_lay_ds_chitiet_km_theo_tbid({vthuebao_id: this.thuebao_id})
              this.ma_kh = this.data.DS_TT_DANHBA[0].MA_KH.toString()
              this.dtpNgayLHD = this.data.DS_TT_DANHBA[0].NGAYLAP_HD !== '' ? DateTimeLib.toDateObjectWithFormat(this.data.DS_TT_DANHBA[0].NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss') : new Date()
              this.khachhang_id = parseFloat(this.data.DS_TT_DANHBA[0].KHACHHANG_ID)
              this.THUEBAO = this.data.DS_TT_DANHBA[0]
              this.ten_kh = this.data.DS_TT_DANHBA[0].TEN_KH // this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_KH)
              this.diachi_kh = this.data.DS_TT_DANHBA[0].DIACHI_KH // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_KH.toString()) : null
              this.THUEBAO.TEN_TB = this.data.DS_TT_DANHBA[0].TEN_TB // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_TB.toString()) : null
              this.ten_tb = this.data.DS_TT_DANHBA[0].TEN_TB // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_TB.toString()) : null
              this.diachi_ld = this.data.DS_TT_DANHBA[0].DIACHI_LD // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_LD.toString()) : null
              this.THUEBAO.DIACHI_LD = this.data.DS_TT_DANHBA[0].DIACHI_TB // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_TB.toString()) : null
              this.loaitb_id = parseFloat(this.data.DS_TT_DANHBA[0].LOAITB_ID)
              this.dataSelected.LOAITB_ID = parseFloat(this.THUEBAO.LOAITB_ID)
              this.donvi_id = parseFloat(this.data.DS_TT_DANHBA[0].DONVIQL_ID)
              this.doituong_id = parseFloat(this.data.DS_TT_DANHBA[0].DOITUONG_ID)
              this.khlon_id = parseFloat(this.data.DS_TT_DANHBA[0].KHLON_ID)
              this.so_dt_kh = this.data.DS_TT_DANHBA[0].SO_DT
              await this.HT_DS_KhuyenMai_Combobox(null)
              if (this.dataSelected.DICHVUVT_ID === this.ADSL) {
                this.dataSelected.TOCDO_ID = this.THUEBAO.TOCDO_ID
              }
              if (this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.DICHVU_CNTT || this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.ANTOAN_BAOMAT_TT || this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.TRUNGTAM_DULIEU || this.dataSelected.DICHVUVT_ID === this.DichVuVienThong.HOINGHI_TRUYENHINH) {
                this.ItemList = await this.Init_Properties(this.loaitb_id, 'HDTB_CNTT', '00000')
              }
              this.txtUuDaiLanToa = '0'
              var dsUDLT = await this.Lay_tien_uudai_lantoa_theo_tb(this.thuebao_id)
              if (dsUDLT.length > 0) {
                if (this.txtCuocDC !== 0 && this.txtCuocDC !== null) {
                  var uudai = parseFloat(dsUDLT[0].TON_CK)
                  var cuocdc = parseFloat(this.txtCuocDC)
                  if (cuocdc <= uudai) {
                    this.txtUuDaiLanToa = cuocdc.toString()
                  } else {
                    this.txtUuDaiLanToa = uudai.toString()
                  }
                }
              }
              this.hdtb_id = 0
            } else {
              this.$root.showLoading(false)
              var eelement = this.data.DS_DICHVU_VT.find(element => element.DICHVUVT_ID.toString() === this.dataSelected.DICHVUVT_ID.toString())
              if (eelement !== undefined) {
                this.$alert('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB + ' đối với dịch vụ ' + eelement.TEN_DVVT, 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  callback: action => {
                  }
                })
              } else {
                
                this.$alert('Không tìm thấy thông tin dịch vụ viễn thông có ID ' + this.dataSelected.DICHVUVT_ID, 'Thông báo', {
                  confirmButtonText: 'Đóng',
                  callback: action => {
                    this.dataSelected.DICHVUVT_ID = null
                  }
                })
              }
              
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 2088 ~ txtMaTB_KeyPress ~ error', error)
      } finally {
        this.$root.showLoading(false)
      }
    },
    async MAP_ID (P_MAP, P_SCHEMA, P_TABLENAME, P_PRIMARY, P_VALUE) {
      try {
        let data = {
          LOAI: P_TABLENAME,
          KHOA: P_PRIMARY,
          GIATRI: P_VALUE
        }
        let rs = await this.$root.context.post('/web-cabman/ban-do-mang-cap/thong-tin-chi-tiet', data)
        if (rs.data.length > 0) {
          var obj = rs.data[0]
          return obj[P_MAP]
        } else return null
      } catch (error) {
        return null
      }
    },
    async cboLoaiHinhTB_SelectedValueChanged () {
      try {
        if (this.dataSelected.LOAITB_ID) {
          await this.loadTratruoc(null)
          this.chkThoiHan = false
          this.enableCtrl.chkThoiHan = false
          if (this.THAMSO_MD.phainhap_thoihan) {
            var _t = await this.MAP_ID('TRATRUOC', 'CSS', 'LOAIHINH_TB', 'LOAITB_ID', this.dataSelected.LOAITB_ID)
            var tt = parseInt(_t)
            if (tt === 1) {
              this.chkThoiHan = true
              this.enableCtrl.chkThoiHan = true
            }
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1697 ~ cboLoaiHinhTB_SelectedValueChanged ~ error', error)
      }
    },
    async getNgay_Cn () {
      try {
        var rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_getsysdate')
        return moment(rps.data[0].ngay_cn).toDate()
      } catch (error) {
        return null
      }
    },
    async cboChiTietKM_SelectedValueChanged () {
      if (this.Lock) return
      var dnow = await this.getNgay_Cn()
      var month = moment(dnow).startOf('month')

      this.dtpThangBD.enabled = false
      this.dtpThangBDMG.enabled = false

      this.dtpThangBD.value = dnow
      this.dtpThangBDMG.value = dnow

      
      if (this.dataSelected.CHITIETKM_ID === null) return
      try {
        var item = this.data.DS_CHITIETKM.find(item => item.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
        console.log('🚀 ~ file: index.vue:2421 ~ cboChiTietKM_SelectedValueChanged ~ item:', item)
        if (item) {
          this.dtpThangBD.enabled = false
          this.enableCtrl.txtSoThang = false
          this.enableCtrl.txtCuocDC = false
          this.enableCtrl.txtTienTD = false

          this.dtpThangBDMG.enabled = false
          this.enableCtrl.txtSoThangMG = false

          this.enableCtrl.txtTyleSD = false
          this.enableCtrl.txtTyleTB = false
          this.enableCtrl.txtTienTB = false
          this.enableCtrl.txtTienSD = false

          this.txtSoThang = item.HUONG_DC.toString()

          this.txtCuocDC = item.DATCOC_CSD.toString()
          await this.txtCuocDC_TextChanged()

          this.txtTienTD = item.TIEN_TD.toString()
          this.txtSoThangMG = item.HUONG_KM.toString()
          this.txtTienTB = item.TIEN_TB.toString()
          this.txtTienSD = item.TIEN_SD.toString()
          this.txtTyleSD = item.TYLE_SD.toString()
          this.txtTyleTB = item.TYLE_TB.toString()

          /* ndthanh.dng sửa theo JIRA BSS-111554 */
          if (item.KIEU_KM === 1) {
            this.enableCtrl.txtTyleSD = true
            this.enableCtrl.txtTyleTB = true
            this.enableCtrl.txtTienTB = true
            this.enableCtrl.txtTienSD = true
          } else {

          }

          if (item.LOAI_BDDC.toString() === '1') {
            let m = DateTimeLib.toDateObject(moment(month).add(Number(item.THANG_BDDC), 'M'))
            this.dtpThangBD.value = m
            this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.HUONG_DC), 'M').subtract(1, 'M'))
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID)) {
              this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')
            } else {
              if (Number(item.THANG_BDDC) > 0) {
                this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')
              } else {
                this.dtpNgayBDDC.value = dnow
              }
            }
          } else {
            this.dtpThangBD.enabled = true
            this.dtpThangBD.value = DateTimeLib.toDateObject(month)
            console.log('this.dtpThangBD.value', this.dtpThangBD.value)
            this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.HUONG_DC), 'M').subtract(1, 'M'))
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID)) {
              this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')
              console.log('this.dtpNgayBDDC.value', this.dtpNgayBDDC.value)
            } else {
              this.dtpNgayBDDC.value = dnow
              console.log('chitiet_km_theo_tbid', this.chitiet_km_theo_tbid)
              if (this.chitiet_km_theo_tbid !== null) {
                let ngay_bddc = null
                let ngay_ktdc = null
                if (this.chitiet_km_theo_tbid.ngay_ktdc === null) {
                  ngay_ktdc = DateTimeLib.toDateObjectWithFormat(moment('01/' + this.chitiet_km_theo_tbid.thang_ktdc, 'DD/MM/YYYY').endOf('month'), 'DD/MM/YYYY')
                } else {
                  ngay_ktdc = this.chitiet_km_theo_tbid.ngay_ktdc
                }
                ngay_bddc = moment(ngay_ktdc, 'DD/MM/YYYY').add(1, 'days')
                this.dtpNgayBDDC.value = DateTimeLib.toDateObject(ngay_bddc)
                this.dtpThangBD.value = DateTimeLib.toDateObject('01/' + DateTimeLib.toDateDisplayWithFormat(this.dtpNgayBDDC.value, 'MM/YYYY'))
              }
            }
          }
          if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
            this.dtpThangBDMG.value = this.dtpThangBD.value
            this.dtpThangKTMG.value = this.dtpThangKT.value
            this.dtpNgayBD.value = this.dtpNgayBDDC.value
            this.dtpNgayKT.value = this.dtpNgayKTDC.value
            this.dtpThangBDMG.enabled = false
            this.dtpThangKTMG.enabled = false
            this.dtpNgayBD.enabled = false
            this.dtpNgayKT.enabled = false
          } else {
            
            if (item.LOAI_BDKM.toString() === '1') {
              let m = DateTimeLib.toDateObject(moment(month).add(Number(item.THANG_BDKM), 'M'))
              this.dtpThangBDMG.value = m
              this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(item.HUONG_MG), 'M').subtract(1, 'M'))
            } else if (item.LOAI_BDKM.toString() === '2') {
              let m = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.THANG_BDKM) + Number(item.HUONG_DC) - 1, 'M'))
              this.dtpThangBDMG.value = m
              this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(item.HUONG_MG), 'M').subtract(1, 'M'))
            } else if (item.LOAI_BDKM.toString() === '4') {
              let m = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.THANG_BDKM), 'M'))
              this.dtpThangBDMG.value = m
              this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(item.HUONG_KM) - 1, 'M'))
            } else {
              this.dtpThangBDMG.enabled = true
              this.dtpThangBDMG.value = this.dtpThangBD.value
              this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(this.dtpThangBDMG.value).add(Number(item.HUONG_KM) - 1, 'M'))
            }
          }
          this.enableCtrl.txtCuocDC = item.KIEU_TRATRUOC.toString() === '1'
          if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString() && this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
            await this.HIENTHI_TT_CUOC_TAMTHU()
          }
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2508 ~ cboChiTietKM_SelectedValueChanged ~ error:', error)
      }
    },
    async loadTratruoc (P_CHITIETKM_ID) {
      try {
        var km = 0
        var loaitb = 0
        var vnhom_datcoc_id = 0

        if (this.dataSelected.DICHVUVT_ID === null) return false
        if (this.dataSelected.KHUYENMAI_ID === null) return false
        if (this.dataSelected.LOAITB_ID === null) return false
        if (this.dataSelected.NHOM_DATCOC_ID === null) return false

        km = parseInt(this.dataSelected.KHUYENMAI_ID)
        loaitb = parseInt(this.dataSelected.LOAITB_ID)
        vnhom_datcoc_id = parseInt(this.dataSelected.NHOM_DATCOC_ID)
        if (this.THAMSO_MD.CHAN_TRATRUOC_CUOCNONG === 1) {
          if (vnhom_datcoc_id === 25 && loaitb === 21) {
            this.enableCtrl.tsbtnGhiLai = false
          } else { this.enableCtrl.tsbtnGhiLai = true }
        }
        var ctkm = await this.lay_chitiet_km_datcoc(km, loaitb, this.vtocdotn_id, this.vmuccuoctn_id, vnhom_datcoc_id, this.THUEBAO.THUEBAO_ID)
        Vue.set(this.data, 'DS_CHITIETKM', ctkm)
        if (this.data.DS_CHITIETKM.length > 0) {
          // this.dataSelected.CHITIETKM_ID = P_CHITIETKM_ID == null ? this.data.DS_CHITIETKM[0].CHITIETKM_ID : P_CHITIETKM_ID
          this.dataSelected.CHITIETKM_ID = P_CHITIETKM_ID
        } else {
          this.dataSelected.CHITIETKM_ID = null
        }
      } catch (error) {
        this.dataSelected.CHITIETKM_ID = null
        Vue.set(this.data, 'DS_CHITIETKM', [])
        console.log('🚀 ~ file: index.vue ~ line 1177 ~ loadTratruoc ~ error', error)
      }
    },
    Clear () {
      this.THUEBAO = {}
      this.txtThoiHan = 0
      this.txtMaGD = ''
      this.txtMaTB = ''
      this.txtTenKH = ''
      this.txtDiaChiLD = ''
      this.txtGhiChu = ''
      this.thuebao_dc_id = 0
      this.thuebao_id = 0
      this.khachhang_id = 0
      this.chkNguoiGT = false
      if (this.THAMSO_MD.khong_load_ctv === 0) { this.chkCTV = false }

      this.txtSoThang = 0
      this.txtTienTD = 0
      this.dtpThangBD.enabled = false

      this.tienHD = 0
      this.tienVATHD = 0
      this.tongTienHD = 0

      this.txtTyleSD = 0
      this.txtTyleTB = 0
      this.txtTienTB = 0
      this.txtTienSD = 0
      this.txtCuocDC = 0
      this.txtSoThangMG = 0
      this.dgvDanhSach = []
    },
    SetButton (kieu) {
      this.enableCtrl.tsbtnNhapMoi = false
      this.enableCtrl.tsbtnGhiLai = false
      this.enableCtrl.tsbtnXoa = false
      this.enableCtrl.tsbtnHuyBo = false
      
      if (kieu === -1)// Bat dau
      {
        this.enableCtrl.tsbtnGhiLai = true
        this.enableCtrl.tsbtnHuyBo = true
        this.enableCtrl.chkCTV = true
      }
      if (kieu === 0)// Bat dau
      {
        this.$refs.txtMaTB.focus()
        this.enableCtrl.tsbtnNhapMoi = true
        this.enableCtrl.chkCTV = true
        this.Clear()
      }
      if (kieu === 1)// Them moi
      {
        this.$refs.txtMaTB.focus()
        this.enableCtrl.tsbtnGhiLai = true
        this.enableCtrl.tsbtnHuyBo = true
        this.enableCtrl.chkCTV = true
        this.Clear()
      }
      if (kieu === 2)// Huy
      {
        this.enableCtrl.tsbtnNhapMoi = true
        this.enableCtrl.tsbtnXoa = true
        this.enableCtrl.chkCTV = true
        this.Clear()
      }
      if (kieu === 3)// Edit
      {
        this.enableCtrl.tsbtnNhapMoi = true
        this.enableCtrl.tsbtnXoa = true
        this.enableCtrl.tsbtnGhiLai = true
        this.enableCtrl.tsbtnHuyBo = true
      }

    },
    async lay_chitiet_km_datcoc (vkhuyenmai_id,
      vloaitb_id,
      vtocdotn_id,
      vmuccuoctn_id,
      vnhom_datcoc_id,
      vthuebao_id) {
      try {
        var data = {
          vkhuyenmai_id: vkhuyenmai_id,
          vloaitb_id: vloaitb_id,
          vtocdotn_id: vtocdotn_id,
          vmuccuoctn_id: vmuccuoctn_id,
          vnhom_datcoc_id: vnhom_datcoc_id,
          vthuebao_id: vthuebao_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_chitiet_km_datcoc', data
        )
        return this.toUpperCases(rps.data)
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2625 ~ error:', error)
        return []
      }
    },
    async btnLayMaGD_Click () {
      this.$refs.popupSearchContract.showModal()
    },
    async btnTKMaTB_Click () {
      this.$refs['popupSearchAccount'].show()
    },
    async HienThiTTHopDongKH (ds) {
      try {
        if (ds.length > 0) {
          var row = ds[0]
          this.chitiet_km_theo_tbid = await this.sp_lay_ds_chitiet_km_theo_tbid({
            vthuebao_id: row.THUEBAO_ID
          })
          this.hdkh_id = parseInt(row.HDKH_ID)
          this.txtMaGD = row.MA_GD
          this.ma_kh = row.MA_KH
          if (row.KHACHHANG_ID !== null) { this.khachhang_id = parseInt(row.KHACHHANG_ID) }
          this.khlon_id = parseInt(row.KHLON_ID)
          this.THUEBAO = row
          this.THUEBAO.TEN_TB = this.ChuanHoaTen(row.TEN_KH)
          this.THUEBAO.DIACHI_LD = this.ChuanHoaTen(row.DIACHI_KH)
          this.ten_kh = this.THUEBAO.TEN_TB
          this.diachi_kh = this.THUEBAO.DIACHI_LD
          if (row.NGAY_YC !== null) {
            this.dtpNgayYeuCau.value = DateTimeLib.toDateObject(DateTimeLib.toDateTimeDisplay(row.NGAY_YC))
          }
          this.txtGhiChu = this.ChuanHoaTen(row.GHICHU)
          if (row.CTV_ID !== null) {
            this.chkCTV = true
            this.ctv_id = parseInt(row.CTV_ID)
            this.txtCTV = await this.MAP_ID('TEN_NV', 'ADMIN', 'NHANVIEN', 'NHANVIEN_ID', this.ctv_id)
          } else {
            this.chkCTV = false
            this.txtCTV = null
          }

          

          if (row.NHANVIENGT_ID !== null) {
            this.chkNguoiGT = true
            this.nguoigt_id = parseInt(row.NHANVIENGT_ID)
            this.txtNguoiGT = await this.MAP_ID('TEN_NV', 'ADMIN', 'NHANVIEN', 'NHANVIEN_ID', this.nguoigt_id)
          } else {
            this.chkNguoiGT = false
            this.txtNguoiGT = null
          }
          if (this.dichvuvt_id === this.DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id === this.DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id === this.DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id == this.DichVuVienThong.HOINGHI_TRUYENHINH) {
            this.ItemList = await this.Init_Properties(this.loaitb_id, 'HDTB_CNTT', '00000')
          }
          this.SetButton(3)
          this.enableCtrl.chkCTV = this.checkEnableCTV(row.NHANVIEN_ID)
          await this.HienThiDanhSacHDTB()
        } else {
          this.dgvDanhSach = []
          this.SetButton(0)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2441 ~ HienThiTTHopDongKH ~ error:', error)
        this.$root.$toast.error('Có lỗi: ' + error)
      }
    },
    async Lay_tien_uudai_lantoa_theo_tb (thuebao_id) {
      try {
        var data = {
          VTHUEBAO_ID: thuebao_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_tien_uudai_lantoa_theo_tb', data
        )
        return this.toUpperCases(rps.data)
      } catch (error) {
        console.log('Lay_tien_uudai_lantoa_theo_tb', error)
        return []
      }
    },
    async HT_DS_KhuyenMai_Combobox (P_KHUYENMAI_ID) {
      try {
        this.itocdo_id = -1
        this.vmuccuoctn_id = 0
        var dt = await this.LOAD_TOCDO(this.dataSelected.DICHVUVT_ID, this.THUEBAO.THUEBAO_ID)
        console.log('🚀 ~ file: index.vue:2832 ~ HT_DS_KhuyenMai_Combobox ~ dt:', dt)
        if (dt.length > 0) {
          this.itocdo_id = dt[0].ID
          var loaitb = this.dataSelected.LOAITB_ID
          if (loaitb === this.INTERNET_ADSL || loaitb === this.INTERNET_FTTH || loaitb === this.WIFI_FIBER) {
            this.vmuccuoctn_id = dt[0].MUCCUOC_ID
          }
          if (this.dataSelected.DICHVUVT_ID === this.HOINGHI_TRUYENHINH || this.dataSelected.DICHVUVT_ID === this.ANTOAN_BAOMAT_TT || this.dataSelected.DICHVUVT_ID === this.DICHVU_CNTT || this.dataSelected.DICHVUVT_ID === this.TRUNGTAM_DULIEU) {
            this.dataSelected.TOCDO_ID = dt[0].ID
          }
          this.vtocdotn_id = this.itocdo_id
        }
        var km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V1(this.dataSelected.LOAITB_ID, this.THUEBAO.DOITUONG_ID, this.itocdo_id, this.vmuccuoctn_id, this.dataSelected.NHOM_DATCOC_ID, this.THUEBAO.THUEBAO_ID)
        if (km.length > 0) {
          Vue.set(this.data, 'DS_KHUYENMAI', km)
          this.dataSelected.KHUYENMAI_ID = P_KHUYENMAI_ID
        } else {
          Vue.set(this.data, 'DS_KHUYENMAI', [])
          this.dataSelected.KHUYENMAI_ID = null
        }
      } catch (error) {
        console.log('HT_DS_KhuyenMai_Combobox', error)
      }
    },
    async LAY_DS_KHUYENMAI_DATCOT_HIENHUU (loaitb_id, doituong_id, itocdo_id, vmuccuoctn_id, nhomdatcot_id, thuebao_id) {
      try {
        var data = {
          vloaitb_id: loaitb_id,
          vdoituong_id: doituong_id,
          vtocdo_id: itocdo_id,
          vmuccuoc_id: vmuccuoctn_id,
          vnhomdc_id: nhomdatcot_id,
          vthuebao_id: thuebao_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_khuyenmai_datcot_hienhuu', data
        )
        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { return [] }
      } catch (error) {
        console.log('LAY_DS_KHUYENMAI_DATCOT_HIENHUU', error)
        return []
      }
    },
    async LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V1 (loaitb_id, doituong_id, itocdo_id, vmuccuoctn_id, nhomdatcot_id, thuebao_id) {
      try {
        var data = {
          vloaitb_id: loaitb_id,
          vdoituong_id: doituong_id,
          vtocdo_id: itocdo_id,
          vmuccuoc_id: vmuccuoctn_id,
          vnhomdc_id: nhomdatcot_id,
          vthuebao_id: thuebao_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_khuyenmai_datcot_hienhuu_v1', data
        )
        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { return [] }
      } catch (error) {
        console.log('LAY_DS_KHUYENMAI_DATCOT_HIENHUU', error)
        return []
      }
    },
    async LOAD_TOCDO (dichvuvt_id, thuebao_id) {
      try {
        var data = {
          VDICHVUVT_ID: dichvuvt_id,
          VTHUEBAO_ID: thuebao_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_load_tocdo_dichvuvt', data
        )
        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else { return [] }
      } catch (error) {
        return []
      }
    },
    async HienThiTT_DanhBa (ma_tb, dichvuvt_id) {
      try {
        var data = {
          in_ma_tb: ma_tb,
          in_dichvuvt_id: dichvuvt_id,
          in_donvi_dl_id: 0
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_danhba_theo_matb', data
        )
        if (rps.data && rps.data.length > 0) {
          Vue.set(this.data, 'DS_TT_DANHBA', this.toUpperCases(rps.data))
        } else { Vue.set(this.data, 'DS_TT_DANHBA', []) }
      } catch (error) {
        Vue.set(this.data, 'DS_TT_DANHBA', [])
        console.log('HienThiTT_DanhBa', error)
      }
    },
    async LAY_DS_HOPDONG_THEO_MA_TB (ma_tb, loaihd_id, donvi_id, tthd_id, nhanvien_id, donvi_dl_id, dichvuvt_id, loaitb_id) {
      try {
        var data = {
          in_ma_tb: ma_tb,
          in_loaihd_id: loaihd_id,
          in_donvi_id: donvi_id,
          in_tthd_id: tthd_id,
          in_nhanvien_id: nhanvien_id,
          in_donvi_dl_id: donvi_dl_id,
          in_dichvuvt_id: dichvuvt_id,
          in_loaitb_id: loaitb_id
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_hopdong_theo_ma_tb', data
        )
        if (rps.data && rps.data.length > 0) {
          Vue.set(this.data, 'DS_HOPDONG', this.toUpperCases(rps.data))
        } else { Vue.set(this.data, 'DS_HOPDONG', []) }
      } catch (error) {
        Vue.set(this.data, 'DS_HOPDONG', [])
        console.log('LAY_DS_HOPDONG_THEO_MA_TB', error)
      }
    },
    async loadDanhMuc () {
      await Promise.all([
        this.getDS_DICHVUVT(),
        this.getDANHMUC({loai: 'DS_NHOM_DATCOC', option: ''}).then(() => {
          this.dataSelected.NHOM_DATCOC_ID = this.data.DS_NHOM_DATCOC.length > 0 ? this.data.DS_NHOM_DATCOC[0].NHOM_DATCOC_ID : null
        })
      ])
    },
    async getDS_DICHVUVT () {
      try {
        var data = {VNHANVIEN_ID: this.$root.token.getNhanVienID()}
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_ds_dichvuvt', data
        )
        if (rps.data && rps.data.length > 0) {
          Vue.set(this.data, 'DS_DICHVU_VT', this.toUpperCases(rps.data))
          if (!this.dataSelected.DICHVUVT_ID) this.dataSelected.DICHVUVT_ID = this.data.DS_DICHVU_VT[0].DICHVUVT_ID
        } else { Vue.set(this.data, 'DS_DICHVU_VT', []) }
      } catch (error) {
        Vue.set(this.data, 'DS_DICHVU_VT', [])
        this.dataSelected.DICHVUVT_ID = null
        console.log('getDS_DICHVUVT', error)
      }
    },
    async get_LAY_DS_THAMSO_MD (kieu_id) {
      await this.getDANHMUC({loai: 'THAMSO_MD', option: kieu_id})
    },
    async getDANHMUC (data) {
      try {
        var rs = null
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/danh-muc', data
        )
        Vue.set(this.data, data.loai, rs.data)
      } catch (error) {
        Vue.set(this.data, data.loai, [])
        console.log('getDANHMUC', error)
      }
      return null
    },
    async Lay_ds_hd_adsl_theo_hdtbCNTT (in_hdtb_id) {
      try {
        var rs = await this.$root.context.post('/web-hopdong/thaydoitocdoadsl/lay_ds_hd_adsl_theo_hdtbcntt', {in_hdtb_id})
        return rs.data
      } catch (error) {
      }
    },
    async Init_Properties (loaiTbId, tenBang, optList) {
      try {
        var data = {loaiTbId, tenBang, optList}
        var rs = await this.$root.context.post('/web-hopdong/hopdong/lay_ds_thuoctinh_bang', data)
        var ItemList = []
        if (rs.data.length > 0) {
          for (let index = 0; index < rs.data.length; index++) {
            const e = rs.data[index]
            let row = {}
            row.sFieldName = e.field_name
            row.sCaption = e.ten_hienthi
            row.iAtt = parseInt(e.att)
            row.iDataType = parseInt(e.data_type)
            row.sMaxValue = e.max_value
            row.sMinvalue = e.min_value
            row.sValue = e.field_value
            row.nFieldLength = e.field_length === null ? 0 : e.field_length
            row.bIsNotNull = e.bIsNotNull === null ? false : (e.bIsNotNull === 0)
            row.bIsUnique = e.bIsUnique === null ? false : (e.bIsUnique !== 0)
            row.sLookUpTableName = e.parent_table
            row.sLookUpKeyField = e.parent_keyfield
            row.sLookUpValueField = e.parent_descfield
            row.strDepandField = e.depend_field
            row.StrSql = e.lookup_sql
            ItemList.push(row)
          }
        }
        return ItemList
      } catch (error) {
        return []
      }
    },
    async dgvDanhSach_SelectionChanged (row, index) {
      if (this.Lock) return
      console.log('🚀 ~ file: index.vue:2762 ~ dgvDanhSach_SelectionChanged ~ row:', row)
      this.$root.showLoading(true)
      try {
        if (this.dgvDanhSach.length === 0) return false
        if (row === undefined) return false
        if (row) {
          this.CT_TIENHD_ID = parseInt(row.CT_TIENHD_ID)
          this.hdtb_id = parseInt(row.HDTB_ID)
          this.thuebao_dc_id = parseInt(row.THUEBAO_DC_ID)
          this.dataSelected.DICHVUVT_ID = parseInt(row.DICHVUVT_ID)
          await this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: this.dataSelected.DICHVUVT_ID})
          await this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: this.dataSelected.DICHVUVT_ID})
          this.dataSelected.LOAITB_ID = parseInt(row.LOAITB_ID)
          this.txtMaTB = row.MA_TB
          this.txtTenKH = row.TEN_TB
          this.ten_tb = row.TEN_TB
          this.txtDiaChiLD = row.DIACHI_LD
          this.diachi_ld = row.DIACHI_LD
          this.loaitb_id = parseInt(row.LOAITB_ID)
          this.dichvuvt_id = parseInt(row.DICHVUVT_ID)
          this.doituong_id = parseInt(row.DOITUONG_ID)
          this.hdtb_id = parseInt(row.HDTB_ID)
          this.thuebao_id = parseInt(row.THUEBAO_ID)
          this.tthd_id = parseInt(row.TTHD_ID)
          this.rkm_id = parseInt(row.RKM_ID)
          this.phieu_id = row.PHIEU_ID
          this.huonggiaotn_id = row.HUONGGIAO_ID

          console.log('this.dichvuvt_id', this.dichvuvt_id)

          if (this.dataSelected.DICHVUVT_ID === this.ADSL) {
            let contex = await this.MAP_ID('TOCDO_ID', 'CSS', 'DB_ADSL', 'THUEBAO_ID', this.thuebao_id)
            if (contex !== null && contex.toString() !== '-1') {
              this.dataSelected.TOCDO_ID = contex
            }
          }

          this.txtCuocDC = parseFloat(row.CUOC_DC)
          this.txtGhiChu = row.GHICHU
          this.txtTyleTB = row.TYLE_TB.toString()
          this.txtTyleSD = row.TYLE_SD.toString()
          this.txtTienTB = row.TIEN_TB.toString()
          this.txtTienSD = row.TIEN_SD.toString()

          this.Lock = true

          if (row.NHOM_DATCOC_ID !== null) {
            this.dataSelected.NHOM_DATCOC_ID = row.NHOM_DATCOC_ID
          } else {
            this.dataSelected.NHOM_DATCOC_ID = null
          }

          await this.HT_DS_KhuyenMai_Combobox(row.KHUYENMAI_ID)

          await this.loadTratruoc(row.CHITIETKM_ID)

          if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString() && this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
            await this.HIENTHI_TT_CUOC_TAMTHU()
            this.txtCuocDC = parseFloat(row.CUOC_DC)
          }

          if (row.THANG_BD.toString() === '0') {
            this.dtpThangBD.enabled = false
            this.txtSoThang = '0'
            this.txtTienTD = '0'
          } else {
            this.dtpThangBD.enabled = true
            this.dtpThangBD.value = DateTimeLib.toDateObjectWithFormat('01/' + row.THANG_BDSHOW, 'DD/MM/YYYY')
            this.dtpThangKT.value = DateTimeLib.toDateObjectWithFormat('01/' + row.THANG_KTSHOW, 'DD/MM/YYYY')
            this.txtSoThang = row.HUONG_DC.toString() // sothang.toString()
            this.txtTienTD = parseFloat(row.TIEN_TD)

            this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat('01/' + row.NGAY_BDDC, 'DD/MM/YYYY')
            this.dtpNgayKTDC.value = DateTimeLib.toDateObjectWithFormat('01/' + row.NGAY_KTDC, 'DD/MM/YYYY')
          }

          if (row.THANG_BD_MG.toString() === '0') {
            this.chkThangMG = false
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === true) {
              this.dtpThangBDMG.enabled = true
            } else {
              this.dtpThangBDMG.enabled = false
            }
            this.txtSoThangMG = '0'
          } else {
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === true) {
              this.chkThangMG = true
              this.dtpThangBDMG.enabled = true
            } else {
              this.chkThangMG = true
              this.dtpThangBDMG.enabled = false
            }
            this.dtpThangBDMG.value = DateTimeLib.toDateObjectWithFormat('01/' + row.THANG_BDMG_SHOW, 'DD/MM/YYYY')
            this.dtpThangKTMG.value = DateTimeLib.toDateObjectWithFormat('01/' + row.THANG_KTMG_SHOW, 'DD/MM/YYYY')
            this.dtpNgayBD.value = DateTimeLib.toDateObjectWithFormat(row.NGAY_BD, 'DD/MM/YYYY')
            this.dtpNgayKT.value = DateTimeLib.toDateObjectWithFormat(row.NGAY_KT, 'DD/MM/YYYY')
            this.txtSoThangMG = row.HUONG_KM.toString() // sothangmg.toString()
          }

          // Thực hiện thông tin mở rộng cho dịch vụ CNTT
          if (this.dichvuvt_id === this.DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
                        this.dichvuvt_id === this.DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id === this.DichVuVienThong.HOINGHI_TRUYENHINH) {
            this.ItemList = await this.Init_Properties(this.loaitb_id, 'HDTB_CNTT', '00000')
            var ds_hdtb_cntt = await this.Lay_ds_hd_adsl_theo_hdtbCNTT(this.hdtb_id)
            console.log('🚀 ~ file: index.vue:3056 ~ dgvDanhSach_SelectionChanged ~ ds_hdtb_cntt:', ds_hdtb_cntt)
            if (!!ds_hdtb_cntt && ds_hdtb_cntt.length > 0) {
              if (ds_hdtb_cntt[0].so_thang !== '' && ds_hdtb_cntt[0].so_thang !== null) {
                this.chkThoiHan = true
                this.enableCtrl.chkThoiHan = true
                this.txtThoiHan = ds_hdtb_cntt[0].so_thang
                if (ds_hdtb_cntt[0].ngay_bd !== null && ds_hdtb_cntt[0].ngay_bd !== '') {
                  this.dtpTuNgay.value = new Date(ds_hdtb_cntt[0].ngay_bd)
                } else {
                  this.dtpTuNgay.value = new Date()
                }
                if (ds_hdtb_cntt[0].ngay_kt !== null && ds_hdtb_cntt[0].ngay_kt !== '') {
                  this.dtpDenNgay.value = new Date(ds_hdtb_cntt[0].ngay_kt)
                } else {
                  this.dtpDenNgay.value = new Date()
                }
              } else {
                this.chkThoiHan = false
                this.enableCtrl.chkThoiHan = false
                this.txtThoiHan = '0'
                this.dtpTuNgay.value = new Date()
                this.dtpDenNgay.value = new Date()
              }
            }
          }

          if (row.NGAY_BD !== null) {
            this.dtpNgayBD.value = DateTimeLib.toDateObject(row.NGAY_BD)
          }

          this.dtpNgayBDDC.value = DateTimeLib.toDateObject(row.NGAY_BDDC)
          this.dtpNgayKTDC.value = DateTimeLib.toDateObject(row.NGAY_KTDC)

          this.txtUuDaiLanToa = '0'
          var dsUDLT = await this.Lay_tt_uudai_lantoa_theo_hdtb(this.hdtb_id)
          if (dsUDLT.length > 0) {
            var uudai = parseFloat(dsUDLT[0].TIEN)
            this.txtUuDaiLanToa = uudai.toString()
          }

          this.enableCtrl.tsbtnThanhToan = (this.tthd_id === 1)
          this.enableCtrl.tsbtnCapNhatDB = (this.tthd_id === 2)

          this.SetButton(3)
          this.Lock = false
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 1737 ~ dgvDanhSach_SelectionChanged ~ error', error)
      }
      this.$root.showLoading(false)
    },
    async layThongTinNguoiDung () {
      try {
        let rs = await this.$root.context.get('/web-hopdong/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung')
        return !rs.data ? null : rs.data[0]
      } catch (error) {
        return null
      }
    },
    async thongtin_nguoidung () {
      try {
        this.loading(true)
        let response = await this.$root.context.post('/quantri/user/thongtin_nguoidung2', {})
        console.log('🚀 ~ file: index.vue:2801 ~ response', response)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async layKyCuocHienHanh () {
      let response = await this.$root.context.get(`/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh`)
      return response.data[0].KYCUOC + ''
    },
    async sp_lay_ds_chitiet_km_theo_tbid (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_lay_ds_chitiet_km_theo_tbid`, data)
        if (response.data && response.data.length > 0) {
          return response.data[0]
        } else {
          return null
        }
      } catch (error) {
        return null
      }
    },
    async HIENTHI_TT_CUOC_TAMTHU () {
      if (this.txtMaTB === '') return false
      try {
        console.log('🚀 ~ file: index.vue:2801 ~ HIENTHI_TT_CUOC_TAMTHU ~ this.userInfo', this.userInfo)
        var sotb = '84' + this.txtMaTB
        var ma_tinh = this.userInfo.tentat
        var user_neo = this.userInfo.user_neo
        var ma_tinh_cu = '0'

        let s = await this.layKyCuocHienHanh() // ex: 20210401
        let yyyy = s.substr(0, 4)
        let month = s.substr(4, 2)
        let chukyno = month + yyyy

        var ds_tt = await this.get_ma_tt_neo(this.khachhang_id, this.txtMaTB)
        if (ds_tt.length > 0) {
          var ma_kh = ds_tt[0].MA_TT_NEO
          var dt = await ccbsService.ts_tamthu_cuocnong_tt_no(ma_tinh, ma_tinh_cu, ma_kh, sotb, chukyno, user_neo, '1', this)
          console.log('🚀 ~ file: index.vue:2827 ~ HIENTHI_TT_CUOC_TAMTHU ~ dt:', dt)
          if (dt.length === 0) {
            this.$root.$toast.warning('Không có dữ liệu CCBS trả về !')
            this.txtCuocDC = '0'
            return false
          }
          this.txtCuocDC = parseFloat(dt[0].PHAITRA) > 0 ? parseFloat(dt[0].PHAITRA) : '0'
          this.txtTienSD = parseFloat(dt[0].CUOCNONG) > 0 ? parseFloat(dt[0].CUOCNONG) : '0'
          this.txtTienTB = parseFloat(dt[0].PHAITRA) > 0 ? parseFloat(dt[0].PHAITRA) : '0'
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2844 ~ HIENTHI_TT_CUOC_TAMTHU ~ error:', error)
        this.$root.$toast.warning('Không có dữ liệu CCBS trả về !')
        this.txtCuocDC = '0'
        return false
      }
    },
    async get_ma_tt_neo (khachhang_id, ma_tb) {
      try {
        var rs = null
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/get-ma-tt-neo?khachhang_id=' + khachhang_id + '&ma_tb=' + ma_tb
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async Lay_tt_uudai_lantoa_theo_hdtb (hdtb_id) {
      try {
        var rs = null
        rs = await this.$root.context.get(
          '/web-cabman/ban-do-mang-cap/lay-tt-uudai-lantoa-theo-hdtb?hdtb_id=' + hdtb_id
        )
        return rs.data
      } catch (error) {
        return []
      }
    },
    async checkTongNoTB() {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=' + this.thuebao_id
        )
        if (rs !== undefined && rs.error_code === 'BSS-00000000' && rs.data !== null) return rs.data
        else return false
      } catch (er) {
        return false
      }
    },
    async tsbtnGiaoViec_Click () {     
      console.log('tsbtnGiaoViec_Click', this.phieu_id) 
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
        this.phieu_id,
        this.$root.token.getDonViID(),
        this.$root.token.getNhanVienID(),
        moment().format('DD/MM/YYYY'),
        this.huonggiao_id
      );
      this.$refs.frnGiaoPhieuNV.vma_tb = this.txtMaTB
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiaotn_id
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load();
        this.$refs.popupfrnGiaoPhieuNV.show();
      });
    },
    async giaoviec_success() {
      // let rs_ds = await api.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
      //   phieu_id: this.phieu_id,
      //   nhanvien_id: this.$root.token.getNhanVienID(),
      //   kieu_id: 2,
      // });
      // this.ds = rs_ds.data ? rs_ds.data.data : [];
      // this.HienThiDSNV(this.ds);
    },

    tinhTongTienHD () {
      this.tongTienHD = (this.chkPhiTTN ? this.phiTTN  : 0) + this.tienHD + this.tienVATHD
    },
    async getThongTinPhiTTN() {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_thongtin_khoanmuctt/3421'
        )
        if (rs !== undefined && rs.error_code === 'BSS-00000000' && rs.data !== null) return this.toUpperCases(rs.data)
        else return []
      } catch (er) {
        return []
      }
    },
    async listLoaiDonvi() {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_loaidv/' + this.$root.token.getDonViID()
        )
        if (rs !== undefined && rs.error_code === 'BSS-00000000' && rs.data !== null) return this.toUpperCases(rs.data)
        else return []
      } catch (er) {
        return []
      }
    },
    checkEnableCTV (nhanvien_id) {
      let vMaTinh = this.$auth.getMaTinh()
      let vNhanVienID = Number(this.$auth.getNhanVienID())
      if (vMaTinh === 'HCM') {
        if (vNhanVienID === nhanvien_id)  return true
        if (this.dsLoaiDV.includes(557))  return true
        if (this.dsLoaiDV.includes(53))  return true
        return false
      } else  return true
    }
  },
  async created () {
    try {
      this.$root.showLoading(true)
      
      await this.loadDanhMuc()
      this.$root.showLoading(false)

      if (this.$route.query.ma_tb !== undefined && this.$route.query.ma_tb !== null) {
        this.txtMaTB = this.$route.query.ma_tb
      }
      if (this.$route.query.dichvuvt_id !== undefined && this.$route.query.dichvuvt_id !== null) {
        this.dataSelected.DICHVUVT_ID = Number(this.$route.query.dichvuvt_id)
      }
      if (this.$route.query.ob_id !== undefined && this.$route.query.ob_id !== null) {
        this.ob_id = this.$route.query.ob_id
      }

      let promises = []
      promises.push(this.getThongTinPhiTTN())
      promises.push(this.listLoaiDonvi())

      let ret = await Promise.all(promises)

      if (ret[0].length > 0) {
        this.khoanmucPhiTTN.MA_KMTT = ret[0][0].MA_KMT
        this.khoanmucPhiTTN.TEN_MKMTT = ret[0][0].TEN_MKMTT
        this.khoanmucPhiTTN.TYLE_VAT = ret[0][0].TYLE_VAT
        this.isShowPhiTTN = true  
      }

      console.log('listLoaiDonvi', ret[1])
      if (ret[1] && ret[1].length > 0) {
        this.dsLoaiDV = ret[1].map(x => x.LOAIDV_ID)
      }

      // let rsPhiTTN = await this.getThongTinPhiTTN()
      // if (rsPhiTTN.length > 0) {
      //   this.khoanmucPhiTTN.MA_KMTT = rsPhiTTN[0].MA_KMT
      //   this.khoanmucPhiTTN.TEN_MKMTT = rsPhiTTN[0].TEN_MKMTT
      //   this.khoanmucPhiTTN.TYLE_VAT = rsPhiTTN[0].TYLE_VAT
      //   this.isShowPhiTTN = true
      // }
      
    } catch (error) {
      this.$root.showLoading(false)
    }
  },
  async mounted () {
    this.userInfo = await this.layThongTinNguoiDung()
    await this.get_LAY_DS_THAMSO_MD(0)
    if (this.data.THAMSO_MD.length > 0) {
      var chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'NHAP_CTV')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.nhap_CTV = 1
        }
      }
      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'HIENTHI_IMG_DATCOC')
      if (chk.length > 0) {
        this.enableCtrl.tsbtnXemanh = true
      }
      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TRUNG_MATB')
      if (chk.length > 0) {
        this.THAMSO_MD.trung_matb = 1
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'CHECK_CHUANHOA_CNTT')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.check_chuahoa_cntt = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TINHCUOC_NGAY')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.tinhcuoc_ngay = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'CHAN_TRATRUOC_CUOCNONG')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.CHAN_TRATRUOC_CUOCNONG = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TS_KHONG_BATBUOC_TT_TRATRUOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.ts_khong_bb_thanhtoan_hths = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KIEMTRA_NO_TRATRUOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.kiemtra_no_tratruoc = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KIEMTRA_HD_KHAC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.KiemTra_HopDongKhac = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KHONG_LOAD_CTV')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.khong_load_ctv = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'PHAINHAP_THOIHAN')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.phainhap_thoihan = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KHONG_HIENTHI_TRACUU_DB_TB_DATCOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.khong_ht_tracuudb = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'SINH_MA_GD_THEO_DV')
      if (chk.length > 0) {
        this.THAMSO_MD.ts_sinhma_gd_theo_donvi = true
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'MACDINH_CTV_NGUOIDUNG')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.MACDINH_CTV_NGUOIDUNG = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TS_DATCOC_NGUOIGT')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.THAMSO_MD.nhap_NGT = 1
        }
      }

      
      
    }
    if (this.THAMSO_MD.MACDINH_CTV_NGUOIDUNG === 1) {
    }
    try {
      // console.log('🚀 ~ file: index.vue:3333 ~ mounted ~ this.$route.query:', this.$route.query)
      // let ma_Tb = this.$route.query.ma_Tb
      // this.txtMaTB = ma_Tb
      // let loaitb_id = Number(this.$route.query.loaitb_id)
      // var rs = await this.$root.context.get('/web-cabman/ban-do-mang-cap/danh-muc', { loai: 'LOAITB_ID', option: loaitb_id })
      // if (rs.data && rs.data.length > 0) {
      //   this.txtMaTB = ma_Tb
      //   this.dataSelected.DICHVUVT_ID = rs.data[0].DICHVUVT_ID
      //   await this.txtMaTB_KeyPress()
      // }
      
    } catch (error) {
      console.log('🚀 ~ file: index.vue:3448 ~ mounted ~ error:', error)
    }
    
    this.SetButton(-1)
    if (!!this.txtMaTB) await this.txtMaTB_KeyPress()
    
  }
}
</script>
<style>
.disabled-btn {
    pointer-events:none;
    opacity:0.5;
    background: #ccc;
}

.disable-btn:hover {
    background-color: white !important;
}

.disable-btn a {
    color: #6c757d !important;
}

.disable-btn a:hover {
    cursor: default;
    color: #6c757d;
}

a.disable-btn {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
}

.frm-capnhat-datcoc.modal-body .page-content
{
    position: inherit !important;
}
.popupComponentPayment .page-content
{
    padding: 8px 16px 16px 16px !important;
    top: 90px !important;
    bottom: 40px !important;
}
</style>
