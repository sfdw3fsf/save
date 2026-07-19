<template src="./index.html">
</template>
<script>
import Vue from 'vue'
import { Group, Page, Sort, Filter, Aggregate, Resize, ContextMenu } from "@syncfusion/ej2-vue-grids";
import moment from 'moment'
import DateTimeLib from '@/utils/DateTimeLib'
import ccbsService from './ccbs-service'
import vascService from './vasc-service'
import frmDSTBCungMa from '../ThayDoiThongTinKhuyenMai/frmDSTBCungMa/frmDSTBCungMa.vue'
import ChonKhuyenMai from './Popups/popup-ChonKhuyenMai.vue'
import { Query } from '@syncfusion/ej2-data'
import { DropDownListPlugin } from '@syncfusion/ej2-vue-dropdowns'
import SearchContractModal from '@/modules/contract/setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
// import CongTacVien from '@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien'
Vue.use(DropDownListPlugin)
import { currency } from '@/filters/currency'
const NguoiGioiThieu = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
const ThongTinEmail = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue')
const ThongTinLienHe = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
const thanhToan = () => import('@/modules/contract/setup/Payment/Payment.vue')
const frmTuVanMyTV = () => import('@/modules/CSKH/TuvanMyTv/TuvanMyTv.vue')

export default {
  name: 'CapNhatThueBaoDatCoc',
  components: {
    frmDSTBCungMa,
    ChonKhuyenMai,
    SearchContractModal,
    SearchAccount,
    CongTacVien : () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien'),
    frmTuVanMyTV,
    NguoiGioiThieu,
    ThongTinEmail,
    ThongTinLienHe,
    ChonThueBao : () => import('./Popups/ChonThueBao')
  },
  data () {
    return {
      groupSettings: {
        showDropArea: false,
        showToggleButton: false,
        showUngroupButton: false,
        columns: ['TEN_GOI_DDV']
      },
      frmTuVanMyTVCom: null,
      frmTuVanMyTVEvents: {
        'onUpdate': this.onUpdate
      },
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      captionTemplate: '<div>${key}</div>',
      statusTemplate: function () {
        return {
          template: Vue.component('statusTemplate', {
            template: `<span :class="{'grid-status-0': data.TRANG_THAI === 0, 'grid-status-1': data.TRANG_THAI === 1, 'grid-status-2': data.TRANG_THAI === 2}">{{data.TRANG_THAI === 2 ? 'Hết hiệu lực' : data.TRANG_THAI === 0 ? 'Chưa đặt cọc' : 'Đang đặt cọc'}}</span>`,
            data: function () { return { data: {} } }
          })
        }
      },
      sumTemplate: function() {
        return {
            template: Vue.component('sumTemplate', {
              template: `<span style="color:red">Tổng: {{this.$options.filters.currency(data.Sum)}}</span>`,
              data () {
                return { data: { data: {} } }
              }
            })
          }
      },
      customTemplate: function () {
        return { template: Vue.component('footerTemplate', {
          template: `<span style="color:red">Tổng: {{this.$options.filters.currency(data.Custom)}}</span>`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      contextMenuItems: [
        /*{ text: 'Chọn tất cả', target: '.e-content', id: 'checkAll', iconCss: 'e-icons e-check' },
        { text: 'Bỏ chọn tất cả', target: '.e-content', id: 'uncheckAll' },
        { separator: true },
        */
        { text: 'Chọn tất cả gia hạn', target: '.e-content', id: 'checkGiaHan' },
        { text: 'Bỏ tất cả gia hạn', target: '.e-content', id: 'uncheckGiaHan' },
        { text: 'Thực hiện gia hạn', target: '.e-content', id: 'thuchienGiaHan' },
        { separator: true },
        { text: 'Chọn đặt cọc theo nhóm', target: '.e-content', id: 'checkDatCocNhom' },
        { text: 'Bỏ chọn theo nhóm', target: '.e-content', id: 'uncheckDatCocNhom' },
        { text: 'Thực hiện cọc mới', target: '.e-content', id: 'thuchienDatCoc' },
        /*
        { separator: true },
        { text: 'Chọn tất cả cọc mới', target: '.e-content', id: 'checkDatCoc' },
        { text: 'Bỏ chọn cọc mới', target: '.e-content', id: 'uncheckDatCoc' }
        */
      ],
      min_thang_dc: new Date(),
      itocdo_id: -1,
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
        MA_TAMTHU_CUOCNONG: 'CCBS_TAMTHU_CUOCNONG'
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
      tthd_id: 0,   // 1: mới tiếp nhận, 2: đã thanh toán
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
      ma_tb: null,
      ma_kh: null,
      ten_kh: null,
      diachi_kh: null,
      dtpNgayLHD: new Date(),
      khlon_id: null,
      ten_tb: null,
      diachi_ld: null,
      khachhang_id: null,
      hdkh_id: null,
      nhomtb_id: null,
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
      dsSoThang: [],
      dsCTKM: [],
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
      txtMaDA: null,
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
      ds_tb: [],
      cfg_ngay_cn : null,
      f_capnhat_from_grid: false,
      hideTable: true,
      ct_khuyenmai: {
        id: '',
        text: '',
        so_thang: ''
      },
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
        chkThoiHan: false
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
      dgvDatCoc: {
          o_ds: [],
          DataSource: [],
          EnabledSelectFirstRow: true
      },
      dsTBHD: [],
      tbHDSelected: null,
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
      },
      // button
      f_btn_xacnhan: false,
      f_btn_xoa: false,
      // OB
      ob_id : null,
      user_ipcc: null
    }
  },
  watch: {
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
        this.dtpThangKTMG.value = moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M')
        // if (ds[0].LOAI_BDKM.toString() === '2') {
        //   this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        // } else if (ds[0].LOAI_BDKM.toString() === '4') {
        //   this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        // } else if (ds[0].LOAI_BDKM.toString() === '1') {
        //   this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        // } else {
        //   this.dtpThangKTMG.value = DateTimeLib.toDateObject(moment(val).add(Number(ds[0].HUONG_KM) - 1, 'M'))
        // }
      }
    },
    'dtpThangBD.value' (val) {
      this.onChangedtpThangBD(val)
    },
    'dtpNgayBDDC.value' (val) {
      this.onChangedtpNgayBDDC(val)
    },
    'dtpNgayBD.value' (val) {
      this.onChangedtpNgayBD(val)
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
      // this.data.DS_CHITIETKM = []
      Vue.set(this.data, 'DS_CHITIETKM', [])
      this.loadTratruoc(null)
    },
    async 'dataSelected.NHOM_DATCOC_ID' (val) {
      Vue.set(this.data, 'DS_KHUYENMAI', [])
      if (val) {
        await this.HT_DS_KhuyenMai_Combobox(null)
      }
    },
    'dataSelected.DICHVUVT_ID' (val) {
      Vue.set(this.data, 'DS_LOAIHINH_TB', [])
      Vue.set(this.data, 'DS_TOCDO_INTERNET', [])
      if (val) {
        // console.log(this.THUEBAO)
        this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: val}).then(() => {
          // if (this.data.DS_LOAIHINH_TB.length > 0 && this.THUEBAO.loaitb_id) {
          //   this.dataSelected.LOAITB_ID = this.THUEBAO.loaitb_id
          // }
        })
        if ([4, 14, 15, 13, 16].indexOf(val) > -1) {
          this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: val}).then(() => {
            // if (this.data.DS_TOCDO_INTERNET.length > 0 && this.THUEBAO.tocdo_id) {
            //   this.dataSelected.TOCDO_ID = this.THUEBAO.tocdo_id
            // }
          })
        }
      }
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
    },
    f_tinhcuocngay () {
      //return this.data.THAMSO_MD.tinhcuoc_ngay && [13, 14, 15, 16].includes(this.dataSelected.DICHVUVT_ID)===false
      return true
    }
  },
  methods: {
    customSumCuocDC (data) {
      // console.log('customSumCuocDC', data)
      try {
        let selected = 0
        if (!!data) {
          if (!!data.result)
          {
            if (!!data.result.records)
              selected = data.result.records.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
            else {
              selected = data.result.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
            }
          }
        }
        return selected
      } catch (error) {
        console.log('customSumCuocDC', error)
      }
    },
    customSumTienThoai (data) {
      // console.log('customSumCuocDC', data)
      try {
        let selected = 0
        if (!!data) {
          if (!!data.result)
          {
            if (!!data.result.records)
              selected = data.result.records.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIENTHOAI, 0)
            else {
              selected = data.result.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIENTHOAI, 0)
            }
          }
        }
        return selected
      } catch (error) {
        console.log('customSumTienThoai', error)
      }
    },
    customSumThucTra (data) {
      // console.log('customSumCuocDC', data)
      try {
        let selected = 0
        if (!!data) {
          if (!!data.result)
          {
            if (!!data.result.records)
              selected = data.result.records.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.THUC_TRA, 0)
            else {
              selected = data.result.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.THUC_TRA, 0)
            }
          }
        }
        return selected
      } catch (error) {
        console.log('customSumThucTra', error)
      }
    },
    customAggCuocDC (data) {
      console.log('customAggCuocDC', data)
      try {
        if (!!data.items) {
          let selected = data.items.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
          return selected
        } else if (!!data) {

          // let selected = data.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.CUOC_DC, 0)
          // return selected
          return 0
        }
      } catch (error) {
        console.log('customAggCuocDC', error)
      }
    },
    customAggTienThoai (data) {
      try {
        if (!!data.items) {
          let selected = data.items.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.TIENTHOAI, 0)
          return selected
        } else if (!!data) {
          // let selected = data.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.TIENTHOAI, 0)
          // return selected
          return 0
        }
      } catch (error) {
        console.log('customAggTienThoai', error)
      }

    },
    customAggThucTra (data) {
      try {
        if (!!data.items) {
          let selected = data.items.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.THUC_TRA, 0)
          return selected
        } else if (!!data) {
          // let selected = data.filter(x => x !== undefined && x._CHECK === true).reduce((a, b) => +a + +b.THUC_TRA, 0)
          // return selected
          return 0
        }
      } catch (error) {
        console.log('customAggThucTra', error)
      }

    },
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
      if (ds === undefined || ds === null) return []
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
      this.Clear()
      // this.dgvDatCoc.DataSource = []
      // this.dgvDatCoc.o_ds = []
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
    async tsbtnNhapMoi_Click () {
      // this.dgvDatCoc.DataSource = []
      // this.THUEBAO = {}
      this.Clear()
      this.SetButton(1)
    },
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
        let chk = await this.XOA_HDKH_V2(this.hdkh_id, this.DATCOC_MOI)
        this.$root.showLoading(false)
        if (chk) {
          this.$root.$toast.success('Xóa hợp đồng khách hàng thành công!')
          // this.dgvDanhSach = []
          // this.dgvDatCoc.DataSource = []
          // this.CLearThongTinDatCoc()
          // this.THUEBAO = {}
          this.Clear()
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
        if (rs.data !== null) return true
        else {
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

    checkDupplicateKeys (data, keys) {
      const result = [...data.reduce((r, o) => {
        if (isNaN(o.used)) {
          o['used'] = 1
        }
        const key = this.genKey(keys, o)
        const item = r.get(key) || Object.assign({}, o, {
          used: 0
        })
        item.used += o.used

        return r.set(key, item)
      }, new Map()).values()]
      const f = result.filter(x => x.used > 1)
      return f
    },

    async getTienThoai (postData) {
      try {
        let tienThoaiDuocTinh = await this.layTienThoaiDatCoc_v4({
          rkm_id: postData.RKM_ID,
          thuebao_dc_id: postData.THUEBAO_DC_ID,
          thang_thoai: postData.THANG_THOAI,
          ngay_thoai: postData.NGAY_THOAI
        })
        return tienThoaiDuocTinh
      } catch (error) {
        console.log(error)
        return 0
      }
    },
    async tsbtnGhiLai2_Click() {
      if (this.dgvDatCoc.DataSource.length <= 0) {
        this.$toast.error('Không tìm thấy thuê bao cần đặt cọc.')
        return
      }
      let ds_tb = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true)


      if (ds_tb.length <= 0) {
        this.$toast.error('Vui lòng chọn thuê bao cần đặt cọc.')
        return
      }

      // let f_giahan = ds_tb.find(x => x.TRANG_THAI === 1) // gia hạn

      // if (f_giahan !== undefined) {
      //   this.$toast.error('Danh sách chọn đang có thuê bao cần gia hạn.')
      //   return
      // }

      if (this.isTest) {
        this.data.THAMSO_MD.nhap_CTV = 0
      }
      if (this.data.THAMSO_MD.nhap_CTV === 1) {
        if (!this.chkCTV || this.txtCTV === '') {
          this.$root.$toast.warning('Hãy chọn cộng tác viên')
          return false
        }
      }
      if (this.data.THAMSO_MD.nhap_NGT === 1) {
        if (!this.chkNguoiGT || this.txtNguoiGT === '') {
          this.$root.$toast.warning('Hãy chọn Người giới thiệu')
          return false
        }
      }

      let promises = []

      // if (this.enableCtrl.tsbtnNhapMoi === false) { // nhập mới
      if (this.dsTBHD.length <= 0) {  // nhập mới

        this.$root.showLoading(true)

        for(let i = 0; i < ds_tb.length; i++) {
          if (ds_tb[i].khuyenmai_id !== null) {
            promises.push(await this.validateNewData(ds_tb[i]), true)
          }
        }

        let ret = await Promise.all(promises)
        if (ret.find(x => x === false) !== undefined) {
          this.$toast.error('Đã phát sinh lỗi khi thêm mới hợp đồng đặt cọc.')
          this.$root.showLoading(false)
          return false
        }

        this.$root.showLoading(false)
      } else {
        this.$root.showLoading(true)

        for(let i = 0; i < ds_tb.length; i++) {
          // promises.push(await this.Kiemtra_Dulieu2(ds_tb[i], false))
          if (ds_tb[i].khuyenmai_id !== null) {
            if (ds_tb[i].hdkh_id === null) {
              promises.push(await this.Kiemtra_Dulieu2(ds_tb[i]), true)
            } else {
              promises.push(await this.Kiemtra_Dulieu2(ds_tb[i], false))
            }

          }
        }

        let ret = await Promise.all(promises)
        if (ret.find(x => x === false) !== undefined) {
          this.$toast.error('Đã phát sinh lỗi khi điều chỉnh hợp đồng đặt cọc.')
          this.$root.showLoading(false)
          return false
        }
      }

      try {
        this.hdkh_id = this.hdkh_id || 0
        let params = {
          hdkh_id: 0,
          loai: 1,
          khachhang_id: this.khachhang_id
        }
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
          })) != 'Yes') await this.xacnhanLuuHopDong()
        } else {
          await this.xacnhanLuuHopDong()
        }
      }catch (err) {
        console.log(err)
        await this.xacnhanLuuHopDong()
      }
    },

    async xacnhanLuuHopDong () {
      let selected = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true && x.TRANG_THAI === 2).map(x => moment(x.NGAY_BD_DC, 'DD/MM/YYYY').toDate())
      let ngay_bddc = moment(new Date(Math.min(...selected)))  // ngày KTDC sau cùng

      let firstOfMonth = moment().startOf('month')
      // console.log(selected, ngay_bddc.format('DD/MM/YYYY'), firstOfMonth.format('DD/MM/YYYY'))
      if (ngay_bddc.isBefore(firstOfMonth)) {
        let  msg = `Ngày bắt đầu đặt cọc kỳ mới không hợp lệ`
        this.$toast.error(msg)
        return false
      }
      await this.CapNhatNew()
      return true
    },


    async validateNewData(data) {
      let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(data.MA_TB, '0', data.DICHVUVT_ID, 32)
      if (KiemTraMaTBLapHopDongKhac) {
        return false
      }
      let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = false
      if (this.data.THAMSO_MD.trung_matb === 1) {
        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(data.MA_TB, '0', data.DICHVUVT_ID, data.LOAITB_ID, 32)
      } else {
        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(data.MA_TB, '0', data.DICHVUVT_ID, 32)
      }

      if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
        return false
      }

      let ret = await this.Kiemtra_Dulieu2(data, true)
      return ret
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
      // if (this.data.THAMSO_MD.trung_matb === 1) {
      //   if (this.data.THAMSO_MD.KiemTra_HopDongKhac === 1) {
      //     let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
      //     if (KiemTraMaTBLapHopDongKhac) return false
      //   } else {
      //     let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), parseInt(this.dataSelected.LOAITB_ID), 32)
      //     if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false
      //   }
      // } else {
      //   if (this.data.THAMSO_MD.KiemTra_HopDongKhac === 1) {
      //     let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
      //     if (KiemTraMaTBLapHopDongKhac) return false
      //   } else {
      //     let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(this.txtMaTB, '0', parseInt(this.dataSelected.DICHVUVT_ID), 32)
      //     if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) return false
      //   }
      // }
      // var vkt = await this.MAP_ID('PHIEUTT_ID', 'CSS', 'PHIEUTT_HD', 'HDKH_ID', this.hdkh_id)
      // if (vkt !== null) {
      //   this.$root.$toast.warning('Hợp đồng đã được thanh toán. Bạn không được thêm thuê bao!')
      //   return
      // }
      // var Kiemtra_Dulieu = await this.Kiemtra_Dulieu(true)
      // if (!Kiemtra_Dulieu) return false
      // let taodulieu = await this.TaoDuLieu2(true, true)
      // if (!taodulieu) {
      //   this.$root.$toast.error('Lỗi khởi tạo dữ liệu!!!')
      //   return
      // }
      // await this.ThemMoi_HopDong(null, this.dsHDTB, this.hdtb_dc, this.dsCTTHD, this.huonggiaotn_id)
      // if (!this.data.THAMSO_MD.tinhcuoc_ngay) { await this.CN_NGAY_BD(this.hdtb_id, this.dtpNgayBD.value === null ? DateTimeLib.toDateDisplay(new Date()) : DateTimeLib.toDateDisplay(this.dtpNgayBD.value), 1) }
      // await this.HienThiDanhSacHDTB()
      // this.inDex_load = 0
      // this.$root.$toast.success('Thêm mới dữ liệu thành công!')
    },
    dgvDanhSach_CellClick (args) {
      // console.log('🚀 ~ file: index.vue:751 ~ dgvDanhSach_CellClick ~ args:', args)
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
          return rs.data[0].result.toString() === '1'
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
        // if (this.dsTBHD.length <= 1) {
        //   this.$root.$toast.error('Không thể xoá thuê bao khi hợp đồng phải có ít nhất 1 thuê bao.')
        //   return false
        // }

        // if (!this.tbHDSelected) {
        //   this.$root.$toast.error('Chưa chọn thuê bao đặt cọc để xoá.')
        //   return false
        // }


        this.$root.showLoading(true)
        let selected = this.tbHDSelected
        let selectedTBs = []
        // // console.log(selected)
        if (selected.TTHD_ID > 1) {
          var trangthai_hd = await this.trangthai_hd(selected.TTHD_ID)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ' bạn không thể xóa!')
          this.$root.showLoading(false)
          return false
        }
        try {
          this.$root.showLoading(false)
          let confirm = null
          try {

            if (selected.TEN_GOI_DDV) {
              let ds_tb = this.dsTBHD.filter(x => x.TEN_GOI_DDV === selected.TEN_GOI_DDV)
              let ds = ds_tb.map(x => x.MA_TB).join(', ')
              selectedTBs = ds_tb.map(x => x.HDTB_ID)
              confirm = await this.$confirm(`Bạn thật sự muốn xóa các thuê bao ${ds} củng gói đa dịch vụ không ?`, 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
                type: 'warning'
              })
            } else {
              selectedTBs.push(selected.HDTB_ID)
              confirm = await this.$confirm(`Bạn thật sự muốn xóa thuê bao ${selected.MA_TB} không ?`, 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không',
                type: 'warning'
              })
            }

            // selectedTBs.push(selected.HDTB_ID)
            // confirm = await this.$confirm(`Bạn thật sự muốn xóa thuê bao ${selected.MA_TB} không ?`, 'Thông báo', {
            //   confirmButtonText: 'Có',
            //   cancelButtonText: 'Không',
            //   type: 'warning'
            // })
          } catch (error) {
            return false
          }
          this.$root.showLoading(true)


          if (selectedTBs.length === this.dsTBHD.length) {
            this.tsbtnXoa_Click()
            return
          }

          let promises = []

          for(let i = 0; i < selectedTBs.length; i++) {
            promises.push(await this.XOA_HDTB(selectedTBs[i]))
          }

          // let f = await this.XOA_HDTB(selected.TTHD_ID)
          let ret = await Promise.all(promises)

          if (ret.filter(x => x === true).length === promises.length) {
            this.txtMaGD_KeyPress()
          } else {
            this.$root.$toast.error('Có lỗi khi xóa thuê bao')
          }

        } catch (error) {
          this.$root.$toast.error('Có lỗi khi xóa thông tin: ' + error)
          this.$root.showLoading(false)
          return false
        }
        // await this.HienThiDanhSacHDTB()
        // this.inDex_load = 0
        // this.$root.$toast.success('Xóa thông tin thuê bao khỏi hợp đồng đặt cọc thành công')
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    async XOA_HDTB (hdtb_id) {
      try {
        await this.$root.context.post('/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId=' + hdtb_id)
        return true
      } catch (error) {
        return false
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
    async KichHoat_HoanThien_TamThuCuocNong () {
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
        let yyyy = s.substring(0, 4)
        let month = s.substring(4, 2)
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
        if (!this.data.THAMSO_MD.ts_khong_bb_thanhtoan_hths) {
          var KT_Trangthai_thanhtoan_hd = await this.KT_Trangthai_thanhtoan_hd(this.hdkh_id)
          if (!KT_Trangthai_thanhtoan_hd) {
            this.$root.$toast.warning('Hợp đồng đã thanh toán nhưng chưa thu được tiền không thể hoàn thiện!')
            this.$root.showLoading(false)
            return
          }
        }

        let OPEN_SYNC_VINA = await this.KIEMTRA_OPEN_SYNC_VINA('OPEN_SYNC_VINA')

        for (let row of this.dsTBHD) {
          if (row.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString()) {
            let kq = await this.KICHHOAT_HOANTHIEN_2731()
            if (kq !== '1' && kq !== '-1') {
              this.$root.showLoading(false)
              return
            }
          } else {
            if (OPEN_SYNC_VINA) {
              if (row.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString()) {
                if (row.NHOM_DATCOC_ID.toString() === this.DATCOC.DATCOC_TRATRUOC.toString() || row.NHOM_DATCOC_ID.toString() === this.DATCOC.CAMKET_CHONSO.toString()) {
                  var ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', row.KHUYENMAI_ID)
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
              } else if (row.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
                let ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', row.KHUYENMAI_ID)
                if (ma_km && ma_km.toString() === this.DATCOC.MA_TAMTHU_CUOCNONG.toString()) {
                  let kq = await this.KichHoat_HoanThien_TamThuCuocNong()
                  if (kq === false) {
                    this.$root.showLoading(false)
                    this.$root.$toast.error('Đăng ký không thành công !!!')
                    return
                  }
                }
              }
            }
          }
        }
        /*
        let f = this.dsTBHD.find(x => x.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString())
        // if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString()) {
        if (f !== undefined) {
          let kq = await this.KICHHOAT_HOANTHIEN_2731()
          if (kq !== '1' && kq !== '-1') {
            this.$root.showLoading(false)
            return
          }
        }
        let OPEN_SYNC_VINA = await this.KIEMTRA_OPEN_SYNC_VINA('OPEN_SYNC_VINA')
        console.log('🚀 ~ file: index.vue:857 ~ tsbtnCapNhatDB_Click ~ OPEN_SYNC_VINA:', OPEN_SYNC_VINA)
        if (OPEN_SYNC_VINA) {
          let f = this.dsTBHD.find(x => x.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString())
          // if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString()) {
          if (f !== undefined) {
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
            if (this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
              var ma_km = await this.MAP_ID('MA_KM', 'CSS', 'KHUYENMAI', 'KHUYENMAI_ID', this.dataSelected.KHUYENMAI_ID)
              if (ma_km && ma_km.toString() === this.DATCOC.MA_TAMTHU_CUOCNONG.toString()) {
                let kq = await this.KichHoat_HoanThien_TamThuCuocNong()
                if (kq === false) {
                  this.$root.showLoading(false)
                  this.$root.$toast.error('Đăng ký không thành công !!!')
                  return
                }
              }
            }
          }
        }
        */
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
        // this.dgvDanhSach = []
        // this.SetButton(0)
        this.tsbtnNhapMoi_Click()
        this.$root.showLoading(false)
      } catch (error) {
        console.log(error)
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
          // let ds = this.$refs.grid.getSelectedRecords()
          let ds = this.dsTBHD
          if (ds.length > 0) {
            let row = ds[0]
            let in_ma_tb = row.MA_TB
            // let ckb_km = await this.kiemtra_km_2731(1, this.dataSelected.CHITIETKM_ID, this.hdtb_id, in_ma_tb)
            let ckb_km = await this.kiemtra_km_2731(1, row.CHITIETKM_ID, row.HDTB_ID, in_ma_tb)
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
        let ds = await this.LAY_DS_THUEBAO_DATCOC_THEOGD(this.txtMaGD)
        if(ds.length <=0 ) {
          this.$toast.error('Không tìm thấy hợp đồng có mã giao dịch ' + this.txtMaGD)
          this.$root.showLoading(false)
          this.Clear()
          return
        }
        this.dgvDatCoc.DataSource = []
        this.dsTBHD = ds
        // this.dgvDatCoc.DataSource = ds.map(x => ({ ...x, _CHECK: true, _KEY:  this.makeKey()}))
        // this.dgvDatCoc.o_ds = [...this.dgvDatCoc.DataSource]
        // let ds = await this.LAY_DS_THUEBAO_DATCOC_THEOGD(this.txtMaGD)
        // for (let i = 0; i < ds.length ; i++) {
        //   ds[i].thang_bd = moment()
        // }
        // await this.HienThiDanhSachDatCoc()

        let f = this.dsTBHD.find(x => x.HDKH_ID !== null)
        if (f !== undefined) this.SetButton(3)
        // else this.SetButton(1)

        this.tthd_id = f.TTHD_ID
        this.hdkh_id = f.HDKH_ID
        this.khachhang_id = f.KHACHHANG_ID

        if (this.tthd_id === 1) {
          this.enableCtrl.tsbtnThanhToan = true
          this.enableCtrl.tsbtnCapNhatDB = false
        } else if (this.tthd_id === 2) {
          this.enableCtrl.tsbtnThanhToan = false
          this.enableCtrl.tsbtnCapNhatDB = true
        }

        // this.tbHDSelected = this.dsTBHD[0]
        // // check the first row
        // this.dgvDatCoc.DataSource[0]._CHECK = true
        // this.dsTBDatCocRecordClick({rowData: this.dgvDatCoc.DataSource[0]})
        this.dsHDRowSelected({data : this.dsTBHD[0]})

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
    async CapNhatNew () {
      try {
        this.$root.showLoading(true)
        // if (this.enableCtrl.tsbtnNhapMoi === false) {
        if (this.dsTBHD.length <= 0) {

          let result = await this.ThemMoi_HopDong_New()
          if (result) {
            // await this.HienThiDanhSacHDTB()
            this.inDex_load = 0
            // this.$root.$toast.success('Thêm mới dữ liệu thành công!')
            await this.txtMaGD_KeyPress()
          } else {
            this.$root.$toast.error('Lỗi thêm mới dữ liệu!')
          }
        } else {

          let result = await this.CapNhat_HopDong_New()
          if (result) {
            // await this.HienThiDanhSacHDTB()
            this.inDex_load = 0
            await this.txtMaGD_KeyPress()
            // this.$root.$toast.success('Cập nhật dữ liệu thành công!')
          } else {
            this.$root.$toast.error('Lỗi cập nhật dữ liệu!')
          }
        }
        // this.enableCtrl.tsbtnThanhToan = (this.tthd_id === 1)
        // this.enableCtrl.tsbtnCapNhatDB = (this.tthd_id === 2)
      } catch (error) {
        this.$root.$toast.error(error)
      }
      this.$root.showLoading(false)
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
            if (!this.data.THAMSO_MD.tinhcuoc_ngay) { await this.CN_NGAY_BD(this.hdtb_id, this.dtpNgayBD.value === null ? DateTimeLib.toDateDisplay(new Date()) : DateTimeLib.toDateDisplay(this.dtpNgayBD.value), 1) }
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

    taoDuLieuGiaHan(themmoi) {
      for (let i = 0; i < this.dgvDatCoc.DataSource.length; i++) {
        // chuản bị dữ liệu ngày BD, KT tháng BD, KT đặt cọc
        if (this.dgvDatCoc.DataSource[i]._CHECK) {
          if (themmoi) {
            let ngay_bd = moment(this.dgvDatCoc.DataSource[i].NGAY_KT_DC, 'DD/MM/YYYY').add(1, 'days').toDate()
            let ngay_kt = this.getNgayKTDC(ngay_bd, this.dgvDatCoc.DataSource[i].HUONG_DC)
            let thang_bd = moment(ngay_bd).format('YYYYMM')
            let thang_kt = moment(ngay_kt).format('YYYYMM')

            this.dgvDatCoc.DataSource[i].NGAY_BD_DC_MOI = moment(ngay_bd).format('DD/MM/YYYY')
            this.dgvDatCoc.DataSource[i].NGAY_KT_DC_MOI = moment(ngay_kt).format('DD/MM/YYYY')
            this.dgvDatCoc.DataSource[i].NGAY_BD_MOI = moment(ngay_bd).format('DD/MM/YYYY')
            this.dgvDatCoc.DataSource[i].NGAY_KT_MOI = moment(ngay_kt).format('DD/MM/YYYY')
            this.dgvDatCoc.DataSource[i].THANG_BD_MOI = thang_bd
            this.dgvDatCoc.DataSource[i].THANG_KT_MOI = thang_kt
            this.dgvDatCoc.DataSource[i].THANG_BD_MG_MOI = thang_bd
            this.dgvDatCoc.DataSource[i].THANG_KT_MG_MOI = thang_kt

            if (this.hdkh_id !== null) {
              this.dgvDatCoc.DataSource[i].HDKH_ID = this.hdkh_id
            }
          }
        }
      }
    },

    async GiaHan_HopDong_New () {
      try {
        let postData = []
        let selected = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true)

        console.log('selected', selected)

        for( let i = 0; i < selected.length; i++) {
          let item = selected[i]
          // if (item.khuyenmai_id === null) {
          //   continue
          // }
          let tien = (Math.round(parseFloat(item.CUOC_DC) / 1.1))
          let vat = (parseFloat(item.CUOC_DC) - tien)
          let dvvt_id = item.DICHVUVT_ID
          let chitiet_datcoc = {
                rkm_id: item.RKM_ID,
                nhom_datcoc_id: item.NHOM_DATCOC_ID,
                nhomtb_id: item.NHOMTB_ID,
                thang_bd: item.THANG_BD_MOI !== null ? item.THANG_BD_MOI : null,
                thang_kt: item.THANG_KT_MOI !== null ? item.THANG_KT_MOI : null,
                ngay_bddc: item.NGAY_BD_DC_MOI !== null ? item.NGAY_BD_DC_MOI : null,
                ngay_ktdc: item.NGAY_KT_DC_MOI !== null ? item.NGAY_KT_DC_MOI : null,
                ngay_bd: item.NGAY_BD_MOI !== null ? item.NGAY_BD_MOI : null,
                ngay_kt: item.NGAY_KT_MOI !== null ? item.NGAY_KT_MOI : null,
                thang_bd_mg: item.THANG_BD_MG_MOI !== null ? item.THANG_BD_MG_MOI : null,
                thang_kt_mg: item.THANG_KT_MG_MOI !== null ? item.THANG_KT_MG_MOI : null,
                ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : null,
                chitietkm_id: item.CHITIETKM_ID,
                khuyenmai_id: item.KHUYENMAI_ID,
                ghichu: item.GHICHU,
                cuoc_dc: item.CUOC_DC,
                tien_td : item.TIEN_TD,
                nhomtb_id: item.NHOMTB_ID
              }
          let chitiet_tienhd = {
            ct_tienhd_id: item.CT_TIENHD_ID,
            khoanmuctt_id : 11,
            tien : tien,
            vat : vat,
            id : 11,
            loai_id : 1
          }
          let thongtin_hd_cntt = {}
          if (dvvt_id === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
            dvvt_id === this.DichVuVienThong.DICHVU_CNTT ||
            dvvt_id === this.DichVuVienThong.HOINGHI_TRUYENHINH ||
            dvvt_id === this.DichVuVienThong.TRUNGTAM_DULIEU) {
              thongtin_hd_cntt = await this.Tao_HDTB_CNTT_New(item)
          }
          let ngay_bd = moment(item.THANG_KT, 'YYYYMM').add(1, 'M').startOf('month').toDate()
          let ngay_kt = this.getNgayKTDC(ngay_bd, item.HUONG_DC)

          let info = {
              "hdkh_id":  this.hdkh_id,
              ob_id:  this.ob_id,
              user_ipcc: this.user_ipcc,
              "khachhang_id": this.khachhang_id,
              "hdtb_id": item.HDTB_ID,
              "tthd_id": item.TTHD_ID,
              'ma_gd': this.txtMaGD ? this.txtMaGD.trim() : null,
              ma_duan: this.txtMaDA ? this.txtMaDA.trim() : null,
              "thuebao_id": item.THUEBAO_ID,
              "thuebao_dc_id": item.THUEBAO_DC_ID,
              "ghichu": item.GHICHU,
              "ctv_id": this.chkCTV && this.txtCTV !== '' ? this.ctv_id : null,
              "nhanviengt_id": this.chkNguoiGT && this.txtNguoiGT !== '' ? this.nguoigt_id : null,
              "donvi_id": this.$root.token.getDonViID(),
              "nhanvien_id": this.$root.token.getNhanVienID(),
              rkm_id: item.RKM_ID,
              nhom_datcoc_id: item.NHOM_DATCOC_ID,
              nhomtb_id: item.NHOMTB_ID,
              thang_bd: item.THANG_BD_MOI !== null ? item.THANG_BD_MOI : null,
              thang_kt: item.THANG_KT_MOI !== null ? item.THANG_KT_MOI : null,
              ngay_bddc: item.NGAY_BD_DC_MOI !== null ? item.NGAY_BD_DC_MOI : moment(ngay_bd).format('DD/MM/YYYY'),
              ngay_ktdc: item.NGAY_KT_DC_MOI !== null ? item.NGAY_KT_DC_MOI : moment(ngay_kt).format('DD/MM/YYYY'),
              ngay_bd: item.NGAY_BD_MOI !== null ? item.NGAY_BD_MOI : moment(ngay_bd).format('DD/MM/YYYY'),
              ngay_kt: item.NGAY_KT_MOI !== null ? item.NGAY_KT_MOI : moment(ngay_kt).format('DD/MM/YYYY'),
              thang_bd_mg: item.THANG_BD_MG_MOI !== null ? item.THANG_BD_MG_MOI : null,
              thang_kt_mg: item.THANG_KT_MG_MOI !== null ? item.THANG_KT_MG_MOI : null,
              ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : moment().format('DD/MM/YYYY'),
              chitietkm_id: item.CHITIETKM_ID,
              khuyenmai_id: item.KHUYENMAI_ID,
              ghichu: item.GHICHU,
              cuoc_dc: item.CUOC_DC,
              tien_td : item.TIEN_TD,
              trang_thai: item.TRANG_THAI, // 0: mới, 1: gia hạn, 2: huỷ và đặt mới
              "chitiet_datcoc": JSON.stringify(chitiet_datcoc),
              chitiet_tienhd : JSON.stringify(chitiet_tienhd),
              thongtin_hd_cntt : JSON.stringify(thongtin_hd_cntt)
          }

          postData.push(info)
        }

        console.log('🚀 ~ file: index.vue:1863 ~ Gia hạn hợp đồng ~ data:', postData)
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong-new', { dsData : JSON.stringify(postData)})
        if (rs.data === null) {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data.length > 0) {
          this.txtMaGD = rs.data[0].ma_gd.split(',')[0]
          this.$toast.success(rs.data[0].msg)
          return true
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', error)
        return false
      }
    },
    async ThemMoi_HopDong_New () {
      try {

        let postData = []
        let selected = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true)

        for( let i = 0; i < selected.length; i++) {
          let item = selected[i]
          let tien = (Math.round(parseFloat(item.CUOC_DC) / 1.1))
          let vat = (parseFloat(item.CUOC_DC) - tien)
          let dvvt_id = item.DICHVUVT_ID
          let chitiet_datcoc = {
                rkm_id: item.RKM_ID,
                nhom_datcoc_id: item.NHOM_DATCOC_ID,
                nhomtb_id: item.NHOMTB_ID,
                thang_bd: item.THANG_BD !== null ? item.THANG_BD : null,
                thang_kt: item.THANG_KT !== null ? item.THANG_KT : null,
                ngay_bddc: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
                ngay_ktdc: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
                ngay_bd: item.NGAY_BD !== null ? item.NGAY_BD : null,
                ngay_kt: item.NGAY_KT !== null ? item.NGAY_KT : null,
                thang_bd_mg: item.THANG_BD_MG !== null ? item.THANG_BD_MG : null,
                thang_kt_mg: item.THANG_KT_MG !== null ? item.THANG_KT_MG : null,
                ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : moment().format('DD/MM/YYYY'),
                chitietkm_id: item.CHITIETKM_ID,
                khuyenmai_id: item.KHUYENMAI_ID,
                ghichu: item.GHICHU,
                cuoc_dc: item.CUOC_DC,
                tien_td : item.TIEN_TD,
                thuebao_id: item.THUEBAO_ID,
                thuebao_dc_id: item.THUEBAO_DC_ID,
                trang_thai: item.TRANG_THAI // 0: mới, 1: gia hạn, 2: huỷ và đặt mới
              }
          let chitiet_tienhd = {
            ct_tienhd_id: item.CT_TIENHD_ID,
            khoanmuctt_id : 11,
            tien : tien,
            vat : vat,
            id : 11,
            loai_id : 1
          }
          let thongtin_hd_cntt = {}
          if (dvvt_id === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
            dvvt_id === this.DichVuVienThong.DICHVU_CNTT ||
            dvvt_id === this.DichVuVienThong.HOINGHI_TRUYENHINH ||
            dvvt_id === this.DichVuVienThong.TRUNGTAM_DULIEU) {
              thongtin_hd_cntt = await this.Tao_HDTB_CNTT_New(item)
          }
          let info = {
              hdkh_id:  this.hdkh_id === 0 ? null : this.hdkh_id,
              ob_id:  this.ob_id,
              user_ipcc: this.user_ipcc,
              khachhang_id: this.khachhang_id,
              hdtb_id: item.HDTB_ID,
              tthd_id: item.TTHD_ID,
              ma_gd: this.txtMaGD ? this.txtMaGD.trim() : null,
              thuebao_id: item.THUEBAO_ID,
              thuebao_dc_id: item.THUEBAO_DC_ID,
              ghichu: item.GHICHU,
              ma_duan: this.txtMaDA ? this.txtMaDA.trim() : null,
              ctv_id: this.chkCTV && this.txtCTV !== '' ? this.ctv_id : null,
              nhanviengt_id: this.chkNguoiGT && this.txtNguoiGT !== '' ? this.nguoigt_id : null,
              donvi_id: this.$root.token.getDonViID(),
              nhanvien_id: this.$root.token.getNhanVienID(),
              rkm_id: item.RKM_ID,
              nhom_datcoc_id: item.NHOM_DATCOC_ID,
              nhomtb_id: item.NHOMTB_ID,
              thang_bd: item.THANG_BD !== null ? item.THANG_BD : null,
              thang_kt: item.THANG_KT !== null ? item.THANG_KT : null,
              ngay_bddc: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
              ngay_ktdc: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
              ngay_bd: item.NGAY_BD !== null ? item.NGAY_BD : null,
              ngay_kt: item.NGAY_KT !== null ? item.NGAY_KT : null,
              thang_bd_mg: item.THANG_BD_MG !== null ? item.THANG_BD_MG : null,
              thang_kt_mg: item.THANG_KT_MG !== null ? item.THANG_KT_MG : null,
              ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : moment().format('DD/MM/YYYY'),
              chitietkm_id: item.CHITIETKM_ID,
              khuyenmai_id: item.KHUYENMAI_ID,
              ghichu: item.GHICHU,
              cuoc_dc: item.CUOC_DC,
              tien_td : item.TIEN_TD,
              trang_thai: item.TRANG_THAI, // 0: mới, 1: gia hạn, 2: huỷ và đặt mới
              chitiet_datcoc: JSON.stringify(chitiet_datcoc),
              chitiet_tienhd : JSON.stringify(chitiet_tienhd),
              thongtin_hd_cntt : JSON.stringify(thongtin_hd_cntt)
          }

          postData.push(info)
        }

        console.log('🚀 ~ file: index.vue:1797 ~ ThemMoi_HopDong_New ~ data:', postData)
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong-new', { dsData : JSON.stringify(postData)})
        if (rs.data === null) {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data.length > 0) {
          this.txtMaGD = rs.data[0].ma_gd.split(',')[0]
          this.$toast.success(rs.data[0].msg)
          return true
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', error)
        return false
      }
    },
    async CapNhat_HopDong_New () {
      try {

        let postData = []
        let selected = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true)

        for( let i = 0; i < selected.length; i++) {
          let item = selected[i]
          // if (item.khuyenmai_id === null) {
          //   continue
          // }
          let tien = (Math.round(parseFloat(item.CUOC_DC) / 1.1))
          let vat = (parseFloat(item.CUOC_DC) - tien)
          let dvvt_id = item.DICHVUVT_ID
          let chitiet_datcoc = {
              rkm_id: item.RKM_ID,
              nhom_datcoc_id: item.NHOM_DATCOC_ID,
              nhomtb_id: item.NHOMTB_ID,
              thang_bd: item.THANG_BD !== null ? item.THANG_BD : null,
              thang_kt: item.THANG_KT !== null ? item.THANG_KT : null,
              ngay_bddc: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
              ngay_ktdc: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
              ngay_bd: item.NGAY_BD !== null ? item.NGAY_BD : null,
              ngay_kt: item.NGAY_KT !== null ? item.NGAY_KT : null,
              thang_bd_mg: item.THANG_BD_MG !== null ? item.THANG_BD_MG : null,
              thang_kt_mg: item.THANG_KT_MG !== null ? item.THANG_KT_MG : null,
              ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : null,
              chitietkm_id: item.CHITIETKM_ID,
              khuyenmai_id: item.KHUYENMAI_ID,
              ghichu: item.GHICHU,
              cuoc_dc: item.CUOC_DC,
              tien_td : item.TIEN_TD,
              nhomtb_id: item.NHOMTB_ID
            }
          let chitiet_tienhd = {
            ct_tienhd_id: item.CT_TIENHD_ID,
            khoanmuctt_id : 11,
            tien : tien,
            vat : vat,
            id : 11,
            loai_id : 1
          }
          let thongtin_hd_cntt = {}
          if (dvvt_id === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
            dvvt_id === this.DichVuVienThong.DICHVU_CNTT ||
            dvvt_id === this.DichVuVienThong.HOINGHI_TRUYENHINH ||
            dvvt_id === this.DichVuVienThong.TRUNGTAM_DULIEU) {
              thongtin_hd_cntt = await this.Tao_HDTB_CNTT_New(item)
          }
          let info = {
              "hdkh_id":  this.dsTBHD[0].HDKH_ID,
              "khachhang_id": this.dsTBHD[0].KHACHHANG_ID,
              "hdtb_id": item.HDTB_ID,
              "tthd_id": item.TTHD_ID,
              'ma_gd': this.dsTBHD[0].MA_GD,
              "thuebao_id": item.THUEBAO_ID,
              "thuebao_dc_id": item.THUEBAO_DC_ID,
              "ghichu": item.GHICHU,
              ma_duan: this.txtMaDA ? this.txtMaDA.trim() : null,
              "ctv_id": this.chkCTV && this.txtCTV !== '' ? this.ctv_id : null,
              "nhanviengt_id": this.chkNguoiGT && this.txtNguoiGT !== '' ? this.nguoigt_id : null,
              "donvi_id": this.$root.token.getDonViID(),
              "nhanvien_id": this.$root.token.getNhanVienID(),
              rkm_id: item.RKM_ID,
              nhom_datcoc_id: item.NHOM_DATCOC_ID,
              nhomtb_id: item.NHOMTB_ID,
              thang_bd: item.THANG_BD !== null ? item.THANG_BD : null,
              thang_kt: item.THANG_KT !== null ? item.THANG_KT : null,
              ngay_bddc: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
              ngay_ktdc: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
              ngay_bd: item.NGAY_BD !== null ? item.NGAY_BD : null,
              ngay_kt: item.NGAY_KT !== null ? item.NGAY_KT : null,
              thang_bd_mg: item.THANG_BD_MG !== null ? item.THANG_BD_MG : null,
              thang_kt_mg: item.THANG_KT_MG !== null ? item.THANG_KT_MG : null,
              ngay_dk: item.NGAY_DK !== null ? item.NGAY_DK : moment().format('DD/MM/YYYY'),
              chitietkm_id: item.CHITIETKM_ID,
              khuyenmai_id: item.KHUYENMAI_ID,
              ghichu: item.GHICHU,
              cuoc_dc: item.CUOC_DC,
              tien_td : item.TIEN_TD,
              trang_thai: item.TRANG_THAI, // 0: mới, 1: gia hạn, 2: huỷ và đặt mới
              chitiet_datcoc: JSON.stringify(chitiet_datcoc),
              chitiet_tienhd : JSON.stringify(chitiet_tienhd),
              thongtin_hd_cntt : JSON.stringify(thongtin_hd_cntt)
          }

          postData.push(info)
        }

        console.log('🚀 ~ file: index.vue:1797 ~ ThemMoi_HopDong_New ~ data:', postData)
        let rs = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/themmoi-hopdong-new', { dsData : JSON.stringify(postData)})
        if (rs.data === null) {
          this.$root.$toast.error(rs.message)
          return false
        }
        if (rs.data.length > 0) {
          this.txtMaGD = rs.data[0].ma_gd.split(',')[0]
          this.$toast.success(rs.data[0].msg)
          return true
        } else { return false }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1435 ~ ThemMoi_HopDong_V1 ~ error:', error)
        return false
      }
    },
    async ThemMoi_HopDong_V1 (dsHDKH, dsHDTB, dsGOI, dsCTTHD, dsHDTB_CNTT, huonggiao_id) {
      let dsCNTT = await this.generateInsert(this.hdtb_id, dsHDTB_CNTT)
      let encodedData = this.encodeBase64(JSON.stringify(dsCNTT))
      // for (let i = 0; i < dsHDKH.length; i++) {
      //   dsHDKH[i].TEN_KH = null
      //   dsHDKH[i].DIACHI_KH = null
      // }
      // for (let i = 0; i < dsHDTB.length; i++) {
      //   dsHDTB[i].TEN_TB = null
      //   dsHDTB[i].DIACHI_LD = null
      //   dsHDTB[i].DIACHI_TB = null
      // }
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
    async TaoDuLieu2 (themmoi, KoTaoHDKH) {
      try {
        if (!KoTaoHDKH) { await this.TaoDuLieu_HDKH(themmoi) }
        if (this.loi_sinh_magd) return false
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
    async Tao_HDTB_CNTT_New (data) {
      try {
        let dt = await this.LAY_DS_DB_CNTT_THEO_TBID(data.THUEBAO_ID)
        // if (dt.length > 0) {
        //   this.CopyRow(dt[0], this.ItemList)
        // }
        for (let index = 0; index < dt.length; index++) {
          let e = dt[index]
          if (e.sFieldName === 'SO_THANG') {
              e.sValue = this.txtThoiHan
            }
            if (e.sFieldName === 'NGAY_BD') {
              e.sValue = DateTimeLib.toDateDisplayWithFormat(this.dtpTuNgay.value, 'YYYY-MM-DD')
            }
            if (e.sFieldName === 'NGAY_KT') {
              e.sValue = DateTimeLib.toDateDisplayWithFormat(this.dtpDenNgay.value, 'YYYY-MM-DD')
            }
        }
        return dt
      } catch (error) {
        console.log('🚀 ~ file: index.vue:1543 ~ Tao_HDTB_CNTT ~ error:', error)
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
    async TaoDuLieu_CT_TIENHD_New (themmoi, data, hdtb_id) {
      console.log('TaoDuLieu_CT_TIENHD_New', data)
      // this.dsCTTHD = []
      let row = {}
      row.HDTB_ID = hdtb_id
      if (themmoi === true) {
        row.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
      }
      row.KHOANMUCTT_ID = 11
      let tien = (Math.round(parseFloat(data.cuoc_dc) / 1.1))
      let vat = (parseFloat(data.cuoc_dc) - tien)
      row.TIEN = tien
      row.VAT = vat
      row.ID = 11
      row.LOAI_ID = 1
      return row
      // this.dsCTTHD.push(row)
      // if (this.txtUuDaiLanToa !== '' && this.txtUuDaiLanToa.toString() !== '0') {
      //   let rowUD = {}
      //   rowUD.HDTB_ID = hdtb_id
      //   if (themmoi === true) {
      //     rowUD.CT_TIENHD_ID = await this.GetKey('CT_TIENHD', 'CSS')
      //   }
      //   rowUD.KHOANMUCTT_ID = 8800
      //   let tienUD = Math.round(parseFloat(this.txtUuDaiLanToa))
      //   rowUD.TIEN = -1 * tienUD
      //   rowUD.VAT = 0
      //   rowUD.ID = 8800
      //   rowUD.LOAI_ID = 1
      //   this.dsCTTHD.push(rowUD)
      // }
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
    async TaoDuLieu_HDKH_New (themmoi, data) {
      let vmagd_temp = ''
      let rowHDKH = {}
      if (themmoi === true) {
        rowHDKH.HDKH_ID = await this.GetKey('HD_KHACHHANG', 'CSS')
        rowHDKH.NHOMTB_ID = await this.GetKey('NHOMTB', 'CSS')
        if (rowHDKH.HDKH_ID === null || rowHDKH.NHOMTB_ID === null) return null
      }
      if (themmoi === true) {
        if (this.data.THAMSO_MD.ts_sinhma_gd_theo_donvi) {
          vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), 31)
        } else {
          vmagd_temp = await this.Lay_Ma_GD(31)
        }
        if (vmagd_temp.includes('ERROR:') || vmagd_temp.includes('error:')) {
          this.$root.$toast.warning(vmagd_temp.split(':')[1])
          this.loi_sinh_magd = true
          return null
        }
        rowHDKH.MA_GD = vmagd_temp
        rowHDKH.NGAY_YC = moment().format('DD/MM/YYYY') // DateTimeLib.toDateDisplay(new Date())
      } else {
        rowHDKH.MA_GD = this.txtMaGD
        rowHDKH.NGAY_YC = moment(this.dtpNgayYeuCau.value).format('DD/MM/YYYY') // DateTimeLib.toDateDisplay(this.dtpNgayYeuCau.value)
      }
      rowHDKH.MA_KH = data.ma_kh
      if (data.khachhang_id > 0) {
        rowHDKH.KHACHHANG_ID = data.khachhang_id
      } else {
        rowHDKH.KHACHHANG_ID = null
      }
      rowHDKH.KHLON_ID = data.khlon_id
      rowHDKH.TEN_KH = data.ten_kh
      rowHDKH.DIACHI_KH = data.diachi_kh
      rowHDKH.NGAYLAP_HD = moment(this.dtpNgayLHD).format('DD/MM/YYYY') // DateTimeLib.toDateDisplay(this.dtpNgayLHD)
      rowHDKH.GHICHU = data.ghichu
      rowHDKH.DONVI_ID = this.$root.token.getDonViID()
      rowHDKH.NHANVIEN_ID = this.$root.token.getNhanVienID()
      rowHDKH.LOAIHD_ID = 31
      if (this.chkCTV && this.txtCTV !== '') {
        rowHDKH.CTV_ID = this.ctv_id
      } else rowHDKH.CTV_ID = null
      if (this.chkNguoiGT && this.txtNguoiGT !== '') {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      } else { rowHDKH.NHANVIENGT_ID = null }
      // this.dsHDKH.push(rowHDKH)
      return rowHDKH
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
        if (this.data.THAMSO_MD.ts_sinhma_gd_theo_donvi) { vmagd_temp = await this.Lay_Ma_GD_V2(this.$root.token.getDonViID(), 31) } else { vmagd_temp = await this.Lay_Ma_GD(31) }
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
    // async Kiemtra_Dulieu (themmoi) {
    //   if (themmoi) {
    //     if (this.kieu_dl === 0) {
    //       this.$root.$toast.warning('Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.')
    //       return false
    //     }
    //     if (this.thuebao_id <= 0) {
    //       this.$root.$toast.warning('Không có thông tin thuê bao!')
    //       this.$refs.txtMaTB.focus()
    //       return false
    //     }
    //     var SP_CHECK_TT_HDTB = await this.SP_CHECK_TT_HDTB(this.thuebao_id)
    //     if (SP_CHECK_TT_HDTB.length > 0) {
    //       this.$root.$toast.warning('Thuê bao đang có hợp đồng/ phụ lục: ' + SP_CHECK_TT_HDTB[0].ten_loaihd + ', mã giao dịch: ' + SP_CHECK_TT_HDTB[0].ma_gd + ' chưa hoàn thiện!')
    //       return false
    //     }
    //   }
    //   if (this.data.THAMSO_MD.phainhap_thoihan === true) {
    //     var tt = this.MAP_ID('TRATRUOC', 'CSS', 'LOAIHINH_TB', 'LOAITB_ID', this.dataSelected.LOAITB_ID)
    //     if (tt.toString() === '1') {
    //       if (this.chkThoiHan === false) {
    //         this.$root.$toast.warning('Bạn phải chọn thời hạn !')
    //         return false
    //       }
    //     }
    //   }
    //   if (this.thuebao_id <= 0) {
    //     this.$root.$toast.warning('Không có thông tin thuê bao!')
    //     this.$refs.txtMaTB.focus()
    //     return false
    //   }
    //   if (this.dataSelected.KHUYENMAI_ID === null) {
    //     this.$root.$toast.warning('Bạn hãy chọn khuyến mại !')
    //     return false
    //   }
    //   if (this.dataSelected.CHITIETKM_ID === null) {
    //     this.$root.$toast.warning('Bạn hãy chọn trả trước !')
    //     return false
    //   }
    //   if (this.txtMaTB === '') {
    //     this.$root.$toast.warning('Bạn chưa nhập số máy/account !')
    //     this.$refs.txtMaTB.focus()
    //     return false
    //   }
    //   if (this.chkThangMG) {
    //     if (this.txtSoThangMG === '') {
    //       this.$root.$toast.warning('Hãy nhập số tháng miễn giảm')
    //       return false
    //     }
    //   }
    //   if (this.txtSoThang === '') {
    //     this.$root.$toast.warning('Bạn chưa nhập số tháng phải đặt cọc')
    //     return false
    //   }

    //   if (this.txtCuocDC === '') {
    //     this.$root.$toast.warning('Bạn chưa nhập cước đặt cọc !')
    //     this.$refs.txtCuocDC.focus()
    //     return false
    //   }
    //   let ktdatcoc_khongdong = await this.KIEMTRA_DATCOC_CA(this.thuebao_id)
    //   if (ktdatcoc_khongdong !== 'ok') {
    //     if (parseFloat(this.txtCuocDC) <= 0) {
    //       this.$root.$toast.warning('Tiền đặt cọc phải lớn hơn 0')
    //       this.$refs.txtCuocDC.focus()
    //       return false
    //     }
    //   }
    //   if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.DIDONGTRASAU.toString() &&
    //              this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
    //     if (parseFloat(this.txtTienTB) < 0) {
    //       this.$root.$toast.warning('Tiền TB không được nhỏ hơn 0')
    //       return false
    //     }
    //     if (parseFloat(this.txtCuocDC) < parseFloat(this.txtTienTB)) {
    //       this.$root.$toast.warning('Cước ĐC không được nhỏ hơn Tiền TB !')
    //       this.$refs.txtCuocDC.focus()
    //       return false
    //     }
    //   }
    //   if (this.txtTienTD === '') {
    //     this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng!')
    //     this.$refs.txtTienTD.focus()
    //     return false
    //   }

    //   if (this.dataSelected.NHOM_DATCOC_ID.toString() === '1' || this.dataSelected.NHOM_DATCOC_ID.toString() === '4') {
    //     if (parseFloat(this.txtTienTD) <= 0) {
    //       this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng!')
    //       this.$refs.txtTienTD.focus()
    //       return false
    //     }
    //   }

    //   if (this.txtTyleTB !== '') {
    //     if (parseFloat(this.txtTyleTB) > 100 || parseFloat(this.txtTyleTB) < 0) {
    //       this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước thuê bao')
    //       return false
    //     }
    //   }

    //   if (this.txtTyleSD !== '') {
    //     if (parseFloat(this.txtTyleSD) > 100 || parseFloat(this.txtTyleSD) < 0) {
    //       this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước sử dụng')
    //       return false
    //     }
    //   }
    //   if (this.isTest) {
    //     this.data.THAMSO_MD.nhap_CTV = 0
    //   }
    //   if (this.data.THAMSO_MD.nhap_CTV === 1) {
    //     if (!this.chkCTV || this.txtCTV === '') {
    //       this.$root.$toast.warning('Hãy chọn cộng tác viên')
    //       return false
    //     }
    //   }
    //   if (this.data.THAMSO_MD.nhap_NGT === 1) {
    //     if (!this.chkNguoiGT || this.txtNguoiGT === '') {
    //       this.$root.$toast.warning('Hãy chọn Người giới thiệu')
    //       return false
    //     }
    //   }
    //   if (themmoi) {
    //     let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
    //     let ngay_cn = await this.getNgay_Cn()
    //     if (thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyyMM'))) {
    //       this.$root.$toast.warning('Khi thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại!')
    //       return false
    //     }
    //     if (this.thuebao_id !== 0) {
    //       let Kiemtra_loaitb_id_db_thuebao = await this.Kiemtra_loaitb_id_db_thuebao(this.thuebao_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID)
    //       if (!Kiemtra_loaitb_id_db_thuebao) {
    //         this.$root.$toast.warning('Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường.' + this.txtMaTB + '')
    //         return false
    //       }
    //     }

    //     if (this.dataSelected.DICHVUVT_ID === 15 || this.dataSelected.DICHVUVT_ID === 14 ||
    //             this.dataSelected.DICHVUVT_ID === 16 || this.dataSelected.DICHVUVT_ID === 13) {
    //       if (this.data.THAMSO_MD.check_chuahoa_cntt) {
    //         let check_chuahoa_cntt = await this.check_chuahoa_cntt(this.thuebao_id, 31)
    //         if (check_chuahoa_cntt) {
    //           this.$root.$toast.warning('Thuê bao CNTT/GTGT này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
    //           return false
    //         }
    //       }
    //     }
    //     if (this.data.THAMSO_MD.kiemtra_no_tratruoc === 1) {
    //       let tienno = await this.TraCuu_No_MaTB(this.txtMaTB)
    //       if (tienno > 0) {
    //         this.$root.$toast.warning('Thuê bao: ' + this.txtMaTB +
    //                     ' còn nợ: ' + parseFloat(tienno) +
    //                     ' vnđ.\nPhải thanh toán trước khi thực hiện!')
    //         return false
    //       }
    //     }
    //   } else {
    //     let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
    //     let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
    //     let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
    //     let in_loaitb_id = parseInt(CurrentRow.LOAITB_ID)
    //     let in_thang_bd = parseInt(CurrentRow.THANG_BD)
    //     let in_thang_kt = parseInt(CurrentRow.THANG_KT)
    //     if (in_thang_bd > 0) {
    //       let ngay_cn = await this.getNgay_Cn()
    //       if (in_thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'MM'))) {
    //         if (in_thang_bd !== thang_bd) {
    //           this.$root.$toast.warning('Tháng bắt đầu đang nhỏ hơn tháng hiện tại. Bạn không được thay đổi tháng bắt đầu!')
    //           return false
    //         }
    //       }
    //     }
    //     let in_user_cn = CurrentRow.NGUOI_CN
    //     let ma_nd = this.$auth.getUserName()
    //     if (in_user_cn.toString() !== ma_nd.toString()) {
    //       this.$root.$toast.warning('Bạn không được thay đổi thông tin do user ' + in_user_cn + ' cập nhật')
    //       return false
    //     }
    //     if (this.tthd_id > 1) {
    //       let trangthai_hd = await this.trangthai_hd(this.tthd_id)
    //       this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ', bạn không thể thay đổi!')
    //       return false
    //     }
    //   }
    //   var thangbdmoi = 0
    //   var thangktmoi = 0
    //   thangbdmoi = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
    //   thangktmoi = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'MM'))

    //   if (this.data.THAMSO_MD.tinhcuoc_ngay) {
    //     if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
    //       if (this.dtpNgayBDDC.value < moment(this.dtpThangBD.value).startOf('M') || this.dtpNgayBDDC.value > moment(this.dtpThangBD.value).endOf('M')) {
    //         this.$root.$toast.warning('Ngày bắt đầu đặt cọc phải nằm trong tháng bắt đầu đặt cọc!')
    //         return false
    //       }
    //       if (this.dtpNgayBD.value < moment(this.dtpThangBDMG.value).startOf('M') || this.dtpNgayBD.value > moment(this.dtpThangBDMG.value).endOf('M')) {
    //         this.$root.$toast.warning('Ngày bắt đầu miễn giảm phải nằm trong tháng bắt đầu miễn giảm!')
    //         return false
    //       }
    //     }
    //   }

    //   var ngaydbdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
    //   var ngayktdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)

    //   if (themmoi) {
    //     if (this.data.THAMSO_MD.tinhcuoc_ngay) {
    //       if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
    //         let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(this.thuebao_id, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, this.dataSelected.NHOM_DATCOC_ID)
    //         if (Kiemtra_tb_datcoc !== 'ok' && Kiemtra_tb_datcoc !== 'OK') {
    //           this.$root.$toast.warning(Kiemtra_tb_datcoc)
    //           return false
    //         }
    //       } else {
    //         let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //         if (!Kiemtra_tb_datcoc) {
    //           this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
    //           return false
    //         }
    //       }
    //     } else {
    //       let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //       if (!Kiemtra_tb_datcoc) {
    //         this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
    //         return false
    //       }
    //     }
    //     let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //     if (Kiemtra_datcoc_tyle_KM_v1) {
    //       this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
    //       return false
    //     }
    //     let dt = await this.SP_CHECK_TT_HDTB2(this.txtMaTB, this.dataSelected.LOAITB_ID)
    //     console.log('🚀 ~ file: index.vue:2000 ~ Kiemtra_Dulieu ~ dt:', dt)
    //     if (dt && dt.length > 0) {
    //       this.$root.$toast.warning('Thuê bao đã có hợp đồng: ' + dt[0].ten_loaihd + ', mã giao dịch: ' + dt[0].ma_gd + ' chưa hoàn thiện, bạn không thể lập hợp đồng!')
    //       return false
    //     }
    //   } else {
    //     let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
    //     let in_ma_tb = CurrentRow.MA_TB
    //     // let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
    //     // let in_thuebao_dc_id = parseInt(CurrentRow.THUEBAO_DC_ID)
    //     if (this.data.THAMSO_MD.tinhcuoc_ngay) {
    //       if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
    //         let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(this.thuebao_id, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, this.dataSelected.NHOM_DATCOC_ID)
    //         if (Kiemtra_tb_datcoc !== 'ok') {
    //           this.$root.$toast.warning(Kiemtra_tb_datcoc)
    //           return false
    //         }
    //       } else {
    //         let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //         if (!Kiemtra_tb_datcoc) {
    //           this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
    //           return false
    //         }
    //       }
    //     } else {
    //       let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(this.thuebao_dc_id, this.txtMaTB, this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //       if (!Kiemtra_tb_datcoc) {
    //         this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
    //         return false
    //       }
    //     }
    //     let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(this.thuebao_dc_id, this.txtMaTB.trim(), this.dataSelected.DICHVUVT_ID, this.dataSelected.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
    //     if (Kiemtra_datcoc_tyle_KM_v1) {
    //       this.$root.$toast.warning('Thuê bao đang còn thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
    //       return false
    //     }
    //     if (in_ma_tb.trim() !== this.txtMaTB.trim()) {
    //       this.$root.$toast.warning('Bạn không được thay đổi số máy/account')
    //       return false
    //     }
    //   }
    //   try {
    //     if (this.dataSelected.LOAITB_ID.toString() === this.LoaiHinhTB.INTERNET_MYTV.toString()) {
    //       let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
    //       let in_ma_tb = this.txtMaTB
    //       if (!themmoi) { in_ma_tb = CurrentRow.MA_TB }
    //       var ckb_km = await this.kiemtra_km_2731('0', this.dataSelected.CHITIETKM_ID, 0, in_ma_tb)
    //       if (ckb_km === null || ckb_km === '') {
    //         this.$root.$toast.warning('Không lấy được thông tin kiểm tra đặt cọc')
    //         return false
    //       }
    //       if (ckb_km.toString() !== '1' && ckb_km.toString() !== '-1') {
    //         this.$root.$toast.warning(ckb_km.toString())
    //         return false
    //       }
    //     }
    //   } catch (error) {
    //     this.$root.$toast.error('Có lỗi khi kiểm tra đặt cọc của thuê bao:' + error)
    //     return false
    //   }
    //   return true
    // },
    async Kiemtra_Dulieu2 (data, themmoi) {
      if (themmoi) {
        if (this.kieu_dl === 0) {
          this.$root.$toast.warning('Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.')
          return false
        }
        if (data.THUEBAO_ID === null || data.THUEBAO_ID <= 0) {
          this.$root.$toast.warning('Không có thông tin thuê bao!')
          return false
        }
        if (data.TRANG_THAI === 0) {
          let SP_CHECK_TT_HDTB = await this.SP_CHECK_TT_HDTB(data.THUEBAO_ID)
          if (SP_CHECK_TT_HDTB.length > 0) {
            this.$root.$toast.warning('Thuê bao đang có hợp đồng/ phụ lục: ' + SP_CHECK_TT_HDTB[0].ten_loaihd + ', mã giao dịch: ' + SP_CHECK_TT_HDTB[0].ma_gd + ' chưa hoàn thiện!')
            return false
          }
        }

      }
      // if (this.data.THAMSO_MD.phainhap_thoihan === true) {
      //   var tt = this.MAP_ID('TRATRUOC', 'CSS', 'LOAIHINH_TB', 'LOAITB_ID', data.loaitb_id)
      //   if (tt.toString() === '1') {
      //     if (this.chkThoiHan === false) {
      //       this.$root.$toast.warning('Bạn phải chọn thời hạn !')
      //       return false
      //     }
      //   }
      // }
      if (data.THUEBAO_ID === null || data.THUEBAO_ID <= 0) {
        this.$root.$toast.warning('Không có thông tin thuê bao!')
        return false
      }
      if (data.KHUYENMAI_ID === null) {
        this.$root.$toast.warning('Bạn hãy chọn khuyến mại cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      if (data.chitietkm_id === null) {
        this.$root.$toast.warning('Bạn hãy chọn trả trước cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      // if (this.chkThangMG) {
      //   if (this.txtSoThangMG === '') {
      //     this.$root.$toast.warning('Hãy nhập số tháng miễn giảm')
      //     return false
      //   }
      // }
      if (data.SOTHANG_DC === null || data.SOTHANG_DC === '') {
        this.$root.$toast.warning('Bạn chưa nhập số tháng phải đặt cọc cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }

      if (data.CUOC_DC === null || data.CUOC_DC === '') {
        this.$root.$toast.warning('Bạn chưa nhập cước đặt cọc cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      let ktdatcoc_khongdong = await this.KIEMTRA_DATCOC_CA(data.THUEBAO_ID)
      if (ktdatcoc_khongdong !== 'ok') {
        if (parseFloat(data.cuoc_dc) <= 0) {
          this.$root.$toast.warning('Tiền đặt cọc cho thuê bao ' + data.MA_TB + ' phải lớn hơn 0')
          return false
        }
      }
      // if (data.loaitb_id === this.LoaiHinhTB.DIDONGTRASAU &&
      //            this.dataSelected.NHOM_DATCOC_ID.toString() === this.DATCOC.NHOM_TAMTHUCUOCNONG.toString()) {
      //   if (parseFloat(data.txtTienTB) < 0) {
      //     this.$root.$toast.warning('Tiền TB không được nhỏ hơn 0')
      //     return false
      //   }
      //   if (parseFloat(this.txtCuocDC) < parseFloat(this.txtTienTB)) {
      //     this.$root.$toast.warning('Cước ĐC không được nhỏ hơn Tiền TB !')
      //     this.$refs.txtCuocDC.focus()
      //     return false
      //   }
      // }
      if (data.TIEN_TD === null || data.TIEN_TD === '') {
        this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng cho thuê bao ' + data.MA_TB )
        return false
      }

      if (data.NHOM_DATCOC_ID === 1 || data.NHOM_DATCOC_ID === 4) {
        if (parseFloat(data.tien_td) <= 0) {
          this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng cho thuê bao ' + data.MA_TB)
          return false
        }
      }

      // if (this.txtTyleTB !== '') {
      //   if (parseFloat(this.txtTyleTB) > 100 || parseFloat(this.txtTyleTB) < 0) {
      //     this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước thuê bao')
      //     return false
      //   }
      // }

      // if (this.txtTyleSD !== '') {
      //   if (parseFloat(this.txtTyleSD) > 100 || parseFloat(this.txtTyleSD) < 0) {
      //     this.$root.$toast.warning('Hãy kiểm tra lại tỷ lệ khuyến mại cước sử dụng')
      //     return false
      //   }
      // }
      if (themmoi) {
        let thang_bd = moment(data.THANG_BD, 'MM/YYYY').format('YYYYMM') //parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
        let ngay_cn = await this.getNgay_Cn()

         if (thang_bd < moment(ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('YYYYMM')) {
          this.$root.$toast.warning('Khi thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại!')
          return false
        }
        let Kiemtra_loaitb_id_db_thuebao = await this.Kiemtra_loaitb_id_db_thuebao(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID)
        if (!Kiemtra_loaitb_id_db_thuebao) {
          this.$root.$toast.warning('Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường.' + data.ma_tb + '')
          return false
        }
        if ([15, 14, 13, 16].includes(data.DICHVUVT_ID)) {
          if (this.data.THAMSO_MD.check_chuahoa_cntt) {
            let check_chuahoa_cntt = await this.check_chuahoa_cntt(data.THUEBAO_ID, 31)
            if (check_chuahoa_cntt) {
              this.$root.$toast.warning('Thuê bao ' + data.ma_tb + ' này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
              return false
            }
          }
        }
        if (this.data.THAMSO_MD.kiemtra_no_tratruoc === 1) {
          let tienno = await this.TraCuu_No_MaTB(data.MA_TB)
          if (tienno > 0) {
            this.$root.$toast.warning('Thuê bao: ' + data.MA_TB +
                        ' còn nợ: ' + parseFloat(tienno) +
                        ' vnđ.\nPhải thanh toán trước khi thực hiện!')
            return false
          }
        }
      } else {
        // let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
        let thang_bd = parseInt(moment(this.dtpThangBD.value).format('YYYYMM'))
        // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_dichvuvt_id = parseInt(data.DICHVUVT_ID)
        let in_loaitb_id = parseInt(data.LOAITB_ID)
        let in_thang_bd = parseInt(data.THANG_BD)
        let in_thang_kt = parseInt(data.THANG_KT)
        if (in_thang_bd > 0) {
          let ngay_cn = await this.getNgay_Cn()
          // if (in_thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'MM'))) {
          if (in_thang_bd < parseInt(moment(ngay_cn, 'DD/MM/YYYY HH:mm:ss').format('YYYYMM'))) {
            if (in_thang_bd !== thang_bd) {
              this.$root.$toast.warning('Tháng bắt đầu đang nhỏ hơn tháng hiện tại. Bạn không được thay đổi tháng bắt đầu!')
              return false
            }
          }
        }
        let in_user_cn = data.NGUOI_CN
        let ma_nd = this.$auth.getUserName()
        console.log(in_user_cn, ma_nd)
        if (in_user_cn.toString() !== ma_nd.toString()) {
          this.$root.$toast.warning('Bạn không được thay đổi thông tin do user ' + in_user_cn + ' cập nhật')
          return false
        }
        if (data.TTHD_ID > 1) {
          let trangthai_hd = await this.trangthai_hd(data.TTHD_ID)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ', bạn không thể thay đổi!')
          return false
        }
      }
      let thangbdmoi = 0
      let thangktmoi = 0
      thangbdmoi = moment(this.dtpThangBD.value).format('YYYYMM') // parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
      thangktmoi = moment(this.dtpThangKT.value).format('YYYYMM') // parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangKT.value, 'MM'))

      if (this.data.THAMSO_MD.tinhcuoc_ngay) {
        if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
          // let range = moment().range(moment(this.dtpThangBD.value).startOf('M'), moment(this.dtpThangBD.value).endOf('M'));
          // if (this.dtpNgayBDDC.value < moment(this.dtpThangBD.value).startOf('M') || this.dtpNgayBDDC.value > moment(this.dtpThangBD.value).endOf('M')) {
            // if (!range.contains(this.dtpNgayBDDC.value)) {
          let testDate = moment(this.dtpNgayBDDC.value)
          let startDate = moment(this.dtpThangBD.value).startOf('month')
          let endDate = moment(this.dtpThangBD.value).endOf('month')
          // console.log(testDate, startDate, endDate)
          if(!testDate.isBetween(startDate, endDate, 'days', true)) {
            this.$root.$toast.warning('Ngày bắt đầu đặt cọc phải nằm trong tháng bắt đầu đặt cọc!')

            return false
          }
          testDate = moment(this.dtpNgayBD.value)
          startDate = moment(this.dtpThangBDMG.value).startOf('month')
          endDate = moment(this.dtpThangBDMG.value).endOf('month')
          // if (this.dtpNgayBD.value < moment(this.dtpThangBDMG.value).startOf('M') || this.dtpNgayBD.value > moment(this.dtpThangBDMG.value).endOf('M')) {
          if(!testDate.isBetween(startDate, endDate, 'days', true)) {
            this.$root.$toast.warning('Ngày bắt đầu miễn giảm phải nằm trong tháng bắt đầu miễn giảm!')
            return false
          }
        }
      }

      var ngaydbdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
      var ngayktdcmoi = DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)

      if (themmoi) {
        if (this.data.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
            if (data.TRANG_THAI === 1) {
              let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(data.THUEBAO_ID, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, this.dataSelected.NHOM_DATCOC_ID)
              if (Kiemtra_tb_datcoc !== 'ok' && Kiemtra_tb_datcoc !== 'OK') {
                this.$root.$toast.warning(Kiemtra_tb_datcoc)
                return false
              }
            }
          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(data.THUEBAO_ID, data.LOAITB_ID, data.DICHVUVT_ID, thangbdmoi, thangktmoi, this.dataSelected.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        let dt = await this.SP_CHECK_TT_HDTB2(data.MA_TB, data.LOAITB_ID)
        console.log('🚀 ~ file: index.vue:2000 ~ Kiemtra_Dulieu ~ dt:', dt)
        if (dt && dt.length > 0) {
          this.$root.$toast.warning('Thuê bao đã có hợp đồng: ' + dt[0].ten_loaihd + ', mã giao dịch: ' + dt[0].ma_gd + ' chưa hoàn thiện, bạn không thể lập hợp đồng!')
          return false
        }
      } else {
        // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_ma_tb = data.MA_TB
        // let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
        // let in_thuebao_dc_id = parseInt(CurrentRow.THUEBAO_DC_ID)
        if (this.data.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
            if (data.TRANG_THAI === 0) {
              let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(data.THUEBAO_ID, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, data.NHOM_DATCOC_ID)
              if (Kiemtra_tb_datcoc !== 'ok') {
                this.$root.$toast.warning(Kiemtra_tb_datcoc)
                return false
              }
            }
          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Thuê bao đang còn thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        // if (in_ma_tb.trim() !== data.ma_tb.trim()) {
        //   this.$root.$toast.warning('Bạn không được thay đổi số máy/account')
        //   return false
        // }
      }
      try {
        if (data.LOAITB_ID === this.LoaiHinhTB.INTERNET_MYTV) {
          // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
          // let in_ma_tb = this.txtMaTB
          // if (!themmoi) { in_ma_tb = CurrentRow.MA_TB }
          if (data.TRANG_THAI === 0) {
            let ckb_km = await this.kiemtra_km_2731('0', data.CHITIETKM_ID, 0, data.MA_TB)
            if (ckb_km === null || ckb_km === '') {
              this.$root.$toast.warning('Không lấy được thông tin kiểm tra đặt cọc')
              return false
            }
            if (ckb_km.toString() !== '1' && ckb_km.toString() !== '-1') {
              this.$root.$toast.warning(ckb_km.toString())
              return false
            }
          }

        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi kiểm tra đặt cọc của thuê bao ' + data.ma_tb +  ':' + error)
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
      let result = 0
      try {
        if (this.THUEBAO.THUEBAO_ID !== 0) {
          let no_tb = await this.TRACUUNO_THUEBAO(this.THUEBAO.THUEBAO_ID).toString()
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
        let rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/kiemtra_ma_tb_lap_hopdong_khac', data)
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
    genKey (keys, item) {
      let key = ''
      let i = 0
      for (i = 0; i < keys.length - 1; i++) {
        key += item[keys[i]] + '-'
      }
      key += item[keys[i]]
      return key
    },
    checkDupplicateKeys (data, keys) {
      const result = [...data.reduce((r, o) => {
        if (isNaN(o.used)) {
          o['used'] = 1
        }
        const key = this.genKey(keys, o)
        const item = r.get(key) || Object.assign({}, o, {
          used: 0
        })
        item.used += o.used

        return r.set(key, item)
      }, new Map()).values()]
      console.log(data, result)
      const f = result.filter(x => x.used > 1)
      return f
    },
    async txtMaTB_KeyPress () {
      try {
        this.$root.showLoading(true)
        this.THUEBAO = {}
        if (this.txtMaTB) {

          if (this.data.THAMSO_MD.khong_load_ctv === 0) this.chkCTV = false
          this.chkNguoiGT = false
          let ds = await this.LAY_DS_THUEBAO_DATCOC(this.txtMaTB)
          if (!ds || ds.length <= 0) {
            this.$alert('Không tìm thấy thông tin về mã thuê bao ' + this.txtMaTB, 'Thông báo', {
              confirmButtonText: 'Đóng',
              callback: action => {
              }
            })
            return
          }
          this.ma_tb = this.txtMaTB
          let dsTBChuaLapHD = []

          if (this.dsTBHD.length <= 0) { //
            let f = ds.filter(x => x.HDKH_ID !== null)
            if (f.length > 0)
              this.dsTBHD = [...f]
            else
              dsTBChuaLapHD = [...ds]
          } else {
            let f = ds.filter(x => x.HDKH_ID === null)
            dsTBChuaLapHD = [...f]
          }

          let lstOK = this.xulyDSThueBaoDatCoc(dsTBChuaLapHD)
          // let lstOK = [...dsTBChuaLapHD]

          // console.log('lstOK', lstOK)

          this.dgvDatCoc.DataSource = [...lstOK.map(x => ({ ...x, _CHECK: false, _KEY:  this.makeKey()}))]
          this.dgvDatCoc.o_ds = [...this.dgvDatCoc.DataSource]

          if (this.dsTBHD.length > 0) {
            this.SetButton(3)
            this.dsHDRowSelected({data : this.dsTBHD[0]})
          } else {
            // tự chọn dòng đúng mã TB nhập vào
            let f = this.dgvDatCoc.DataSource.find(x => x.MA_TB === this.ma_tb)
            if (f !== undefined) {
              this.dsTBDatCocRecordClick({rowData: f})
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
          if (this.data.THAMSO_MD.phainhap_thoihan) {
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
        if (this.cfg_ngay_cn !== null)  return this.cfg_ngay_cn
        let rps = await this.$root.context.post('/web-hopdong/cap-nhat-thue-bao-dat-coc/sp_getsysdate')
        this.cfg_ngay_cn = moment(rps.data[0].ngay_cn).toDate()
        return this.cfg_ngay_cn
      } catch (error) {
        return null
      }
    },
    async cboChiTietKM_SelectedValueChanged () {
      if (this.Lock || !this.THUEBAO.THUEBAO_ID) return

      let selectedTB = this.THUEBAO
      let oSelectedTB = this.dgvDatCoc.o_ds.find(x => x._KEY === this.THUEBAO._KEY)

      console.log('THUEBAO', this.THUEBAO)
      let dnow = await this.getNgay_Cn()
      let month = moment(dnow).startOf('month')

      this.dtpThangBD.enabled = false
      this.dtpThangBDMG.enabled = false

      this.dtpThangBD.value = dnow
      this.dtpThangBDMG.value = dnow

      if (this.dataSelected.CHITIETKM_ID === null) return
      try {
        let item = this.data.DS_CHITIETKM.find(x => x.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
        if (item) {
          console.log('🚀 ~ file: index.vue:2421 ~ cboChiTietKM_SelectedValueChanged ~ item:', item, this.chitiet_km_theo_tbid)

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

          let ngay_ktdc = moment(oSelectedTB.NGAY_KT_DC, 'DD/MM/YYYY')
          if (this.dataSelected.NHOM_DATCOC_ID === 4) {
            ngay_ktdc = moment(this.getTheLastDate(this.dgvDatCoc.o_ds, 4, selectedTB.GOI_DDV_ID), 'DD/MM/YYYY')
          }
          let ngay_bddc_new = ngay_ktdc.add(1, 'days').toDate()
          let thang_bddc_new = '01/' + ngay_ktdc.add(1, 'days').format('MM/YYYY')

          if (item.LOAI_BDDC.toString() === '1') {
            // let f = await this.SP_CHECK_TT_HDTB(selectedTB.THUEBAO_ID)
            // console.log('Check TB đang nhận KM hiện tại', f)

            if (selectedTB.TRANG_THAI !== 0) {
              this.dtpThangBD.value = moment(thang_bddc_new, 'DD/MM/YYYY').toDate()
            } else {
              let m = DateTimeLib.toDateObject(moment(month).add(Number(item.THANG_BDDC), 'M'))
              this.dtpThangBD.value = m
            }
            this.dtpThangKT.value = DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.HUONG_DC), 'M').subtract(1, 'M'))
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID)) {
              this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')

            } else {
              this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')
            }
            this.f_capnhat_from_grid = false
          } else {
            this.dtpThangBD.enabled = true

            if (selectedTB.TRANG_THAI !== 0) {
              this.dtpThangBD.value = moment(thang_bddc_new,'DD/MM/YYYY').toDate()
            } else {
              this.dtpThangBD.value = DateTimeLib.toDateObject(month)
            }
            this.dtpThangKT.value = moment(this.dtpThangBD.value).startOf('month').add(Number(item.HUONG_DC) - 1, 'M').toDate() // DateTimeLib.toDateObject(moment(this.dtpThangBD.value).add(Number(item.HUONG_DC), 'M').subtract(1, 'M'))
            if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID)) {
              this.dtpNgayBDDC.value = DateTimeLib.toDateObjectWithFormat(moment(this.dtpThangBD.value).startOf('month'), 'DD/MM/YYYY')

            } else {
              // this.dtpNgayBDDC.value = this.dtpThangBD.value
              this.dtpNgayBDDC.value = ngay_bddc_new
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
            this.f_capnhat_from_grid = false
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
          /** Tính lại ngày KTDC */
          let ngay = moment(this.dtpNgayBDDC.value).add(parseInt(this.txtSoThang), 'M')
          let ngayktdc = DateTimeLib.toDateObject(ngay.subtract(1, 'd'))

          this.dtpNgayKTDC.value = ngayktdc
          this.dtpThangKT.value = this.dtpNgayKTDC.value

          if ([13, 14, 15, 16, 7, 8, 9].includes(this.dataSelected.DICHVUVT_ID) === false) {
            this.dtpNgayBD.value = this.dtpNgayBDDC.value
            this.dtpNgayKT.value = this.dtpNgayKTDC.value
            return
          }
          /** */
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
        let km = 0
        let loaitb = 0
        let vnhom_datcoc_id = 0

        this.dsSoThang = []
        this.dsCTKM = []

        this.dsSoThangDC = []
        this.txtSoThang = null
        this.dtpThangBD.value = new Date()
        this.dtpNgayBDDC.value = new Date()

        if (this.dataSelected.DICHVUVT_ID === null) return false
        if (this.dataSelected.KHUYENMAI_ID === null) return false
        if (this.dataSelected.LOAITB_ID === null) return false
        if (this.dataSelected.NHOM_DATCOC_ID === null) return false

        km = parseInt(this.dataSelected.KHUYENMAI_ID)
        loaitb = parseInt(this.dataSelected.LOAITB_ID)
        vnhom_datcoc_id = parseInt(this.dataSelected.NHOM_DATCOC_ID)
        if (this.data.THAMSO_MD.CHAN_TRATRUOC_CUOCNONG === 1) {
          if (vnhom_datcoc_id === 25 && loaitb === 21) {
            this.enableCtrl.tsbtnGhiLai = false
          } else { this.enableCtrl.tsbtnGhiLai = true }
        }
        // let ctkm = await this.lay_chitiet_km_datcoc(km, loaitb, this.vtocdotn_id, this.vmuccuoctn_id, vnhom_datcoc_id, this.THUEBAO.THUEBAO_ID)

        let ctkm = []
        let dsSoThang = []
        if (this.dataSelected.NHOM_DATCOC_ID  !== 4) {
          ctkm = await this.lay_chitiet_km_datcoc(km, loaitb, this.THUEBAO.TOCDO_ID, this.THUEBAO.MUCCUOC_ID, vnhom_datcoc_id, this.THUEBAO.THUEBAO_ID, this.THUEBAO.TRANGBI_ID)
          let ret = ctkm.map(x => x.HUONG_DC)
          let v1 = [...new Set(ret)]
          v1.sort(function(a, b){return a - b})
          let aV1 = []
          v1.forEach(item => {
            aV1.push({ VALUE: item })
          })
          this.dsSoThang = [...aV1]
          this.dsCTKM = [...ctkm]
        } else {
          let promises = []
          let ds_ddv = []
          if (this.THUEBAO.GOI_DDV_ID === null && this.THUEBAO.DICHVUVT_ID !== 2) {
            this.THUEBAO._CHECK = true
            ds_ddv.push(this.THUEBAO)
          } else {
            ds_ddv = this.dgvDatCoc.o_ds.filter(x => x._CHECK && x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID && x.DICHVUVT_ID !== 2)
          }

          for(const item of ds_ddv) {
            promises.push(await this.lay_chitiet_km_datcoc(
              km, item.LOAITB_ID, item.TOCDO_ID, item.MUCCUOC_ID, vnhom_datcoc_id, item.THUEBAO_ID, item.TRANGBI_ID
            ))
          }
          let ret = await Promise.all(promises)
          // console.log(ret)
          for(const item of ret) {
            ctkm = [...ctkm, ...item]
            let v1 = item.map(x => x.HUONG_DC)
            let v2 = [...new Set(v1)]
            v2.sort(function(a, b){return a - b})
            // console.log(v1, v2)
            dsSoThang.push(v2)
          }
          // console.log('dsSoThang', ctkm)
          let result = dsSoThang.shift().filter(function(v) {
              return dsSoThang.every(function(a) {
                  return a.indexOf(v) !== -1
              })
          })
          // console.log(result)
          // console.log('Dòng 3880 ', dsSoThang)
          if (result.length > 0) {
            let aV1 = []
            result.forEach(item => {
              aV1.push({ VALUE: item })
            })
            this.dsSoThang = [...aV1]
            this.dsCTKM = [...ctkm]
            console.log('this.dsCTKM', this.dsCTKM)
          }
        }



          if (this.THUEBAO.SOTHANG_DC !== null) {
            let f = this.dsSoThang.find(x => x.VALUE === this.THUEBAO.SOTHANG_DC)
            if (f) {
              this.txtSoThang = this.THUEBAO.SOTHANG_DC
            }

          }
      } catch (error) {
        Vue.set(this.data, 'DS_CHITIETKM', [])
        this.data.DS_CHITIETKM = []
        console.log('🚀 ~ file: index.vue ~ line 1177 ~ loadTratruoc ~ error', error)
      }
    },
    Clear () {
      // this.THUEBAO = {}
      this.txtThoiHan = 0
      this.txtMaGD = ''
      this.txtMaDA = ''
      this.txtMaTB = ''
      this.txtTenKH = ''
      this.txtDiaChiLD = ''
      this.txtGhiChu = ''
      this.thuebao_dc_id = 0
      this.thuebao_id = 0
      this.khachhang_id = 0
      this.hdkh_id = null
      this.tthd_id = null
      this.chkNguoiGT = false
      if (this.data.THAMSO_MD.khong_load_ctv === 0) { this.chkCTV = false }

      this.txtSoThang = 0
      this.txtTienTD = 0
      this.dtpThangBD.enabled = false

      this.txtTyleSD = 0
      this.txtTyleTB = 0
      this.txtTienTB = 0
      this.txtTienSD = 0
      this.txtCuocDC = 0
      this.txtSoThangMG = 0
      this.dsTBHD = []

      this.dgvDatCoc.DataSource = []
      this.dgvDatCoc.o_ds = []
      this.CLearThongTinDatCoc()
      this.THUEBAO = {}

    },
    SetButton (kieu) {
      this.enableCtrl.tsbtnNhapMoi = false
      this.enableCtrl.tsbtnGhiLai = false
      this.enableCtrl.tsbtnXoa = false
      this.enableCtrl.tsbtnHuyBo = false
      if (kieu === -1) { // Bat dau
        this.enableCtrl.tsbtnGhiLai = false
        this.enableCtrl.tsbtnHuyBo = true
      }
      if (kieu === 0) { // Bat dau
        this.$refs.txtMaTB.focus()
        this.enableCtrl.tsbtnNhapMoi = true
        // this.Clear()
      }
      if (kieu === 1) { // Them moi
        this.$refs.txtMaTB.focus()
        this.enableCtrl.tsbtnGhiLai = true
        this.enableCtrl.tsbtnHuyBo = true
        // this.Clear()
      }
      if (kieu === 2) { // Huy
        this.enableCtrl.tsbtnNhapMoi = true
        this.enableCtrl.tsbtnXoa = true
        // this.Clear()
      }
      if (kieu === 3) { // Edit
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
      vthuebao_id,
      vtrangbi_id) {
      try {
        let data = {
          vkhuyenmai_id: vkhuyenmai_id,
          vloaitb_id: vloaitb_id,
          vtocdotn_id: vtocdotn_id,
          vmuccuoctn_id: vmuccuoctn_id,
          vnhom_datcoc_id: vnhom_datcoc_id,
          vthuebao_id: vthuebao_id,
          vtrangbi_id: vtrangbi_id
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_chitiet_km_datcoc_v4', data
          // '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_chitiet_km_datcoc', data
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
    async HienThiThongTinThueBao (data) {
      // this.THUEBAO = {...data}
      this.dichvuvt_id = data.DICHVUVT_ID
      this.dataSelected.DICHVUVT_ID = data.DICHVUVT_ID

      this.thuebao_id = data.THUEBAO_ID
      await this.getDANHMUC({loai: 'DS_LOAIHINH_TB', option: this.dataSelected.DICHVUVT_ID})

      this.dataSelected.LOAITB_ID = data.LOAITB_ID

      await this.getDANHMUC({loai: 'DS_TOCDO_INTERNET', option: this.dataSelected.DICHVUVT_ID})
      this.txtMaTB = data.MA_TB
      if (this.dsTBHD.length > 0) { // đang có hợp đồng
        this.txtMaGD = this.dsTBHD[0].MA_GD
        this.txtMaDA = this.dsTBHD[0].MA_DUAN
        this.ma_kh = this.dsTBHD[0].MA_KH
        this.tthd_id = this.dsTBHD[0].TTHD_ID
        this.hdkh_id = this.dsTBHD[0].HDKH_ID
        this.khachhang_id = this.dsTBHD[0].KHACHHANG_ID
        this.txtCTV = this.dsTBHD[0].TEN_CTV !== null ? this.dsTBHD[0].TEN_CTV.trim() : null
        this.chkCTV = this.txtCTV !== null
        this.txtNguoiGT = this.dsTBHD[0].TENNV_GIOITHIEU !== null ? this.dsTBHD[0].TENNV_GIOITHIEU.trim() : null
        this.chkNguoiGT = this.txtNguoiGT !== null
      } else {
        this.ma_kh = data.MA_KH
        this.tthd_id = data.TTHD_ID
        this.hdkh_id = data.HDKH_ID
        this.khachhang_id = data.KHACHHANG_ID
        this.txtCTV = data.TEN_CTV !== null ? data.TEN_CTV.trim() : null
        this.chkCTV = this.txtCTV !== null
        this.txtNguoiGT = data.TENNV_GIOITHIEU !== null ? data.TENNV_GIOITHIEU.trim() : null
        this.chkNguoiGT = this.txtNguoiGT !== null
      }

      this.ten_kh = data.TEN_KH // this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_KH)
      this.diachi_kh = data.DIACHI_KH // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_KH.toString()) : null
      this.loaitb_id = data.LOAITB_ID
      this.donvi_id = data.DONVIQL_ID
      this.doituong_id = data.DOITUONG_ID
      this.khlon_id = data.KHLON_ID


      this.dtpNgayLHD = data.NGAYLAP_HD ? moment(data.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss') : new Date()
      console.log('NGAYLAP_HD', moment(data.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss'))

      // this.THUEBAO.THUEBAO_ID = data.thuebao_id
      // this.THUEBAO.TEN_TB = data.ten_tb // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_TB.toString()) : null
      // this.THUEBAO.DIACHI_LD = data.diachi_ld // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_TB.toString()) : null

      // this.THUEBAO.LOAITB_ID  = data.loaitb_id
      // this.THUEBAO.TOCDO_ID  = data.tocdo_id

      // this.dataSelected.KHUYENMAI_ID = data.KHUYENMAI_ID
      // this.dataSelected.CHITIETKM_ID = data.CHITIETKM_ID
      this.dataSelected.TOCDO_ID = data.TOCDO_ID

      // this.THUEBAO.TEN_GOI_DADV = data.ten_goi_ddv
      // this.THUEBAO.DOITUONG_ID = data.doituong_id

      if (data.TRANG_THAI === 2) {
        let ngay_ktdc = !data.NGAY_KT_DC_MOI ? moment(data.NGAY_KT_DC, 'DD/MM/YYYY') : moment(data.NGAY_KT_DC_MOI, 'DD/MM/YYYY')
        let ngay_bddc_new = ngay_ktdc.add(1, 'days')
        this.dtpThangBD.value = ngay_bddc_new.startOf('month').toDate()
        this.dtpNgayBDDC.value = ngay_bddc_new.toDate()

      }

      this.enableCtrl.tsbtnThanhToan = (this.tthd_id === 1)
      this.enableCtrl.tsbtnCapNhatDB = (this.tthd_id === 2)

    },
    async HienThiThongTinDatCoc (data) {
      // if (data.hdkh_id === null) {
      //   this.CLearThongTinDatCoc()
      // } else {


      // }
      this.chitiet_km_theo_tbid = await this.sp_lay_ds_chitiet_km_theo_tbid({vthuebao_id: data.THUEBAO_ID})
      if (data.NHOM_DATCOC_ID !== null) {
        this.dataSelected.NHOM_DATCOC_ID = data.NHOM_DATCOC_ID
      } else {
        this.txtSoThang = 0
        this.dtpNgayBDDC.value = new Date()
        this.dtpNgayKTDC.value = new Date()
        this.txtCuocDC = 0
        this.txtTienTD = 0
        this.txtGhiChu = null
      }

      if (data.DICHVUVT_ID === this.ADSL) {
        let contex = await this.MAP_ID('TOCDO_ID', 'CSS', 'DB_ADSL', 'THUEBAO_ID', data.THUEBAO_ID)
        if (contex !== null && contex.toString() !== '-1') {
          this.dataSelected.TOCDO_ID = contex
        }
      }
      if (data.KHUYENMAI_ID !== null) {
        this.dataSelected.KHUYENMAI_ID = data.KHUYENMAI_ID
      } else {
        this.dataSelected.KHUYENMAI_ID = null
      }

      console.log('HienThiThongTinDatCoc ~ HT_DS_KhuyenMai_Combobox')
      await this.HT_DS_KhuyenMai_Combobox(data.KHUYENMAI_ID)

      if (data.CHITIETKM_ID !== null) {
        await this.loadTratruoc(data.CHITIETKM_ID)
        this.dataSelected.CHITIETKM_ID = data.CHITIETKM_ID
        this.f_capnhat_from_grid = true // cập nhật ngày, tháng đặt cọc từ grid
        let f = this.data.DS_CHITIETKM.find(x => x.CHITIETKM_ID === data.CHITIETKM_ID)
        if (f !== undefined) {
          this.ct_khuyenmai.id = f.CHITIETKM_ID
          this.ct_khuyenmai.text = f.TEN_CTKM
          this.ct_khuyenmai.so_thang = f.HUONG_DC
        } else {
          this.ct_khuyenmai.id = null
          this.ct_khuyenmai.text = null
          this.ct_khuyenmai.so_thang = null
        }

      } else {
        this.dataSelected.CHITIETKM_ID = null
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
        this.ct_khuyenmai.so_thang = null
      }


      if (data.LOAITB_ID === this.LoaiHinhTB.DIDONGTRASAU.toString()
        && data.nhom_datcoc_id === this.DATCOC.NHOM_TAMTHUCUOCNONG) {
        await this.HIENTHI_TT_CUOC_TAMTHU()
        this.txtCuocDC = parseFloat(data.CUOC_DC)
      }
      // console.log(data.thang_bd, moment(this.dtpThangBD.value).format('YYYYMM'))
      // if (data.thang_bd === null) {

      // } else {
      //   if (moment(this.dtpThangBD.value).format('YYYYMM').toString() !== data.thang_bd.toString()){
      //     this.dtpThangBD.value = moment(data.thang_bd, 'YYYYMM').toDate()
      //   } else {
      //     this.onChangedtpThangBD(moment(data.thang_bd, 'YYYYMM').toDate())
      //   }
      // }

      this.txtGhiChu = data.GHICHU

    },

    async HienThiThongTinCNTT(data) {
      let ItemList = await this.Init_Properties(data.LOAITB_ID, 'HDTB_CNTT', '00000')
      let ds_hdtb_cntt = await this.Lay_ds_hd_adsl_theo_hdtbCNTT(data.HDTB_ID)
      console.log('🚀 ~ file: index.vue:3056 ~ dgvDanhSach_SelectionChanged ~ ds_hdtb_cntt:', ds_hdtb_cntt)
      if (!!ds_hdtb_cntt && ds_hdtb_cntt.length > 0) {
        if (ds_hdtb_cntt[0].so_thang !== '' && ds_hdtb_cntt[0].so_thang !== null) {
          // this.chkThoiHan = true
          // this.enableCtrl.chkThoiHan = true
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
          // this.chkThoiHan = false
          // this.enableCtrl.chkThoiHan = false
          this.txtThoiHan = '0'
          this.dtpTuNgay.value = new Date()
          this.dtpDenNgay.value = new Date()
        }
      }
    },

    async HienThiDanhSachDatCoc () {
      this.dgvDanhSach = []
      if (this.dgvDatCoc.DataSource !== null && this.dgvDatCoc.DataSource.length <= 0) {
        return
      }
      let ds = []
      for (const item of this.dgvDatCoc.DataSource) {
        if (item.THUEBAO_DC_ID !== null) {
          ds.push({
            THUEBAO_DC_ID: item.THUEBAO_DC_ID,
            MA_TB: item.MA_TB,
            TEN_TB: item.TEN_TB,
            LOAIHINH_TB: item.LOAIHINH_TB,
            SOTHANG_DC: item.SOTHANG_DC,
            THANG_BD: item.THANG_BD !== null ? moment(item.THANG_BD, 'YYYYMM').format('MM/YYYY') : null,
            THANG_KT: item.THANG_KT !== null ? moment(item.THANG_KT, 'YYYYMM').format('MM/YYYY') : null,
            NGAY_BD_DC: item.NGAY_BD_DC !== null ? item.NGAY_BD_DC : null,
            NGAY_KT_DC: item.NGAY_KT_DC !== null ? item.NGAY_KT_DC : null,
            CUOC_DC: item.CUOC_DC,
            TIEN_TD: item.TIEN_TD,
            NGUOI_CN: item.NGUOI_CN,
            TEN_GOI_DDV: item.TEN_GOI_DDV,
            TTHD_ID: item.TTHD_ID,
            HDTB_ID: item.HDTB_ID
          })
        }
      }
      this.dgvDanhSach = [...ds]
    },
    CLearThongTinDatCoc () {
      this.dataSelected.NHOM_DATCOC_ID = null // this.data.DS_NHOM_DATCOC.length > 0 ? this.data.DS_NHOM_DATCOC[0].NHOM_DATCOC_ID : null
      this.dataSelected.KHUYENMAI_ID = null
      this.dataSelected.CHITIETKM_ID = null
      this.dsSoThang = []
      this.txtSoThang = 0
      this.dtpNgayBDDC.value = new Date()
      this.dtpNgayKTDC.value = new Date()
      this.txtCuocDC = 0
      this.txtTienTD = 0
      this.txtGhiChu = null
      this.dtpThangBDMG.value = null
    },
    async HienThiThongTin(data) {



      // this.THUEBAO = this.data.DS_TT_DANHBA[0]

      // this.ten_tb = f_tb_macdinh.ten_tb // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].TEN_TB.toString()) : null
      // this.diachi_ld = f_tb_macdinh.diachi_ld // ? this.ChuanHoaTen(this.data.DS_TT_DANHBA[0].DIACHI_LD.toString()) : null



      await this.HT_DS_KhuyenMai_Combobox(null)
      if (this.dataSelected.DICHVUVT_ID === this.ADSL) {
        this.dataSelected.TOCDO_ID =  this.THUEBAO.TOCDO_ID
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
      this.hdtb_id = data.hdtb_id === null ? 0 : data.hdtb_id
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
          await this.HienThiDanhSacHDTB()
        } else {
          this.dgvDanhSach = []
          this.SetButton(0)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue:2971 ~ HienThiTTHopDongKH ~ error:', error)
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
        // let selected = this.$refs.dgvDatCoc.getSelectedRecords()
        if (!this.THUEBAO) return

        console.log('this.THUEBAO', this.THUEBAO)

        this.itocdo_id = this.THUEBAO.TOCDO_ID
        this.vmuccuoctn_id = this.THUEBAO.MUCCUOC_ID
        // var dt = await this.LOAD_TOCDO(this.dataSelected.DICHVUVT_ID, this.THUEBAO.THUEBAO_ID)
        // let dt = await this.LOAD_TOCDO(selected[0].dichvuvt_id, selected[0].thuebao_id)
        // console.log('🚀 ~ file: index.vue:2832 ~ HT_DS_KhuyenMai_Combobox ~ dt:', dt)
        // if (dt.length > 0) {
        //   this.itocdo_id = dt[0].ID
        //   let loaitb = selected[0].LOAITB_ID
        //   if (loaitb === this.INTERNET_ADSL || loaitb === this.INTERNET_FTTH || loaitb === this.WIFI_FIBER) {
        //     this.vmuccuoctn_id = dt[0].MUCCUOC_ID
        //   }
        //   if (selected[0].dichvuvt_id === this.HOINGHI_TRUYENHINH ||
        //       selected[0].dichvuvt_id === this.ANTOAN_BAOMAT_TT ||
        //       selected[0].dichvuvt_id === this.DICHVU_CNTT ||
        //       selected[0].dichvuvt_id === this.TRUNGTAM_DULIEU) {
        //     this.dataSelected.TOCDO_ID = dt[0].ID
        //   }
        //   this.vtocdotn_id = this.itocdo_id
        // }
        // var km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V1(this.dataSelected.LOAITB_ID, this.THUEBAO.DOITUONG_ID, this.itocdo_id, this.vmuccuoctn_id, this.dataSelected.NHOM_DATCOC_ID, this.THUEBAO.THUEBAO_ID)
        let km = []
        // console.log('this.THUEBAO', this.THUEBAO)
        if (this.THUEBAO.GOI_DDV_ID !== null && this.dataSelected.NHOM_DATCOC_ID === 4) {
          let ds = []
          if (this.THUEBAO.TRANG_THAI !== undefined)
            ds = [...this.dgvDatCoc.DataSource]
          else
            ds = [...this.dsTBHD]

          console.log('Dòng 4275', ds)
          let ds_cunggoi_dadv = ds.filter(x => x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID && x.DICHVUVT_ID !== 2 && x._CHECK)
          console.log(ds_cunggoi_dadv, this.THUEBAO)
          let postData = []
          for( let i = 0; i < ds_cunggoi_dadv.length; i++) {
            postData.push({
              loaitb_id: ds_cunggoi_dadv[i].LOAITB_ID,
              doituong_id: ds_cunggoi_dadv[i].DOITUONG_ID,
              tocdo_id: ds_cunggoi_dadv[i].TOCDO_ID,
              muccuoc_id: ds_cunggoi_dadv[i].MUCCUOC_ID,
              nhomdc_id: this.dataSelected.NHOM_DATCOC_ID,
              thuebao_id: ds_cunggoi_dadv[i].THUEBAO_ID,
              // trangbi_id: ds_cunggoi_dadv[i].TRANGBI_ID,
              goiddv_id: ds_cunggoi_dadv[i].GOI_DDV_ID
            })
          }
          km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2(postData)
        } else {
          let postData = [{
            loaitb_id: this.THUEBAO.LOAITB_ID,
            doituong_id: this.THUEBAO.DOITUONG_ID,
            tocdo_id: this.THUEBAO.TOCDO_ID,
            muccuoc_id: this.THUEBAO.MUCCUOC_ID,
            nhomdc_id: this.dataSelected.NHOM_DATCOC_ID,
            thuebao_id: this.THUEBAO.THUEBAO_ID,
            // trangbi_id: this.THUEBAO.TRANGBI_ID,
           // goiddv_id: this.THUEBAO.GOI_DDV_ID
           goiddv_id: null
          }]
          km = await this.LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2(postData)
        }



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
    async LAY_DS_KHUYENMAI_DATCOT_HIENHUU_V2 (postData) {
      try {
        let data = {
          p_data : JSON.stringify(postData)
        }
        var rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_khuyenmai_datcot_hienhuu_v2', data
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
        } else {
          Vue.set(this.data, 'DS_TT_DANHBA', [])
        }
      } catch (error) {
        Vue.set(this.data, 'DS_TT_DANHBA', [])
        console.log('HienThiTT_DanhBa', error)
      }
    },
    async LAY_DS_THUEBAO_DATCOC_THEOGD (ma_gd) {
      try {
        let postData = {
            "opt" : 0,
            "ma_gd" : ma_gd,
            "loai_hd" : 31,
            "kieuld_id": 550
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        // console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else {
          return []
        }

      } catch (error) {
        console.log(error)
        return []
      }
    },
    async LAY_DS_THUEBAO_DATCOC (ma_tb) {
      try {
        let postData = {
            "opt" : 1,
            "ma_tb" : ma_tb,
            "loai_hd" : 31,
            "kieuld_id": 550
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )

        console.log(rps)

        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else {
          return []
        }

      } catch (error) {
        console.log(error)
        return []
      }
    },
    async LAY_DS_THUEBAO_DATCOC_THEO_TBID (tb_id, goi_ddv_id) {
      try {
        let postData = {
            "opt" : 2,
            "thuebao_id" : tb_id,
            "goi_ddv_id" : goi_ddv_id
          }
        let data = {
          p_data: JSON.stringify(postData)
        }
        let rps = await this.$root.context.post(
          '/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_ds_thuebao_datcoc', data
        )


        if (rps.data && rps.data.length > 0) {
          return this.toUpperCases(rps.data)
        } else {
          return []
        }

      } catch (error) {
        console.log(error)
        return []
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
        } else {
          Vue.set(this.data, 'DS_HOPDONG', [])
          this.DS_HOPDONG = []
        }
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
          this.dataSelected.DICHVUVT_ID = this.data.DS_DICHVU_VT[0].DICHVUVT_ID
        } else {
          Vue.set(this.data, 'DS_DICHVU_VT', [])
        }
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
        // this[data.loai] = rs.data
      } catch (error) {
        Vue.set(this.data, data.loai, [])
        // this[data.loai] = []
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
    async dgvDatCoc_SelectionChanged (row, index) {
      // if (this.Lock) return
      console.log('🚀 ~ file: index.vue:4135 ~ dgvDatCoc_SelectionChanged ~ row:', row)
      this.$root.showLoading(true)
      try {
        if (this.dgvDatCoc.DataSource.length === 0) return false
        if (row === undefined) return false
        if (row) {

          await this.HienThiThongTinThueBao(row)
          await this.HienThiThongTinDatCoc(row)
          if (row.DICHVUVT_ID === this.DichVuVienThong.DICHVU_CNTT ||
              row.DICHVUVT_ID === this.DichVuVienThong.ANTOAN_BAOMAT_TT ||
              row.DICHVUVT_ID === this.DichVuVienThong.TRUNGTAM_DULIEU ||
              row.DICHVUVT_ID === this.DichVuVienThong.HOINGHI_TRUYENHINH) {
            await this.HienThiThongTinCNTT(row)
          }
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
    async validateGiaHanData(data) {
      let KiemTraMaTBLapHopDongKhac = await this.KiemTraMaTBLapHopDongKhac(data.MA_TB, '0', data.DICHVUVT_ID, 32)
      if (KiemTraMaTBLapHopDongKhac) {
        return false
      }
      let KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = false
      if (this.data.THAMSO_MD.trung_matb === 1) {
        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_1(data.MA_TB, '0', data.DICHVUVT_ID, data.LOAITB_ID, 32)
      } else {
        KIEMTRA_MA_TB_LAP_HOPDONG_KHAC = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(data.MA_TB, '0', data.DICHVUVT_ID, 32)
      }

      if (KIEMTRA_MA_TB_LAP_HOPDONG_KHAC) {
        return false
      }

      let ret = await this.Kiemtra_Dulieu_GiaHan(data, true)
      return ret
    },
    async Kiemtra_Dulieu_GiaHan (data, themmoi) {
      if (data.THUEBAO_ID === null || data.THUEBAO_ID <= 0) {
        this.$root.$toast.warning('Không có thông tin thuê bao!')
        return false
      }
      if (themmoi) {
        if (this.kieu_dl === 0) {
          this.$root.$toast.warning('Chỉ áp dụng đặt cọc cho thuê bao đang tồn tại trong danh bạ hoặc đang lắp đặt mới.')
          return false
        }

        var SP_CHECK_TT_HDTB = await this.SP_CHECK_TT_HDTB(data.THUEBAO_ID)
        if (SP_CHECK_TT_HDTB.length > 0) {
          this.$root.$toast.warning('Thuê bao đang có hợp đồng/ phụ lục: ' + SP_CHECK_TT_HDTB[0].ten_loaihd + ', mã giao dịch: ' + SP_CHECK_TT_HDTB[0].ma_gd + ' chưa hoàn thiện!')
          return false
        }
      }
      if (data.KHUYENMAI_ID === null) {
        this.$root.$toast.warning('Bạn hãy chọn khuyến mại cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      if (data.CHITIETKM_ID === null) {
        this.$root.$toast.warning('Bạn hãy chọn trả trước cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      // if (this.chkThangMG) {
      //   if (this.txtSoThangMG === '') {
      //     this.$root.$toast.warning('Hãy nhập số tháng miễn giảm')
      //     return false
      //   }
      // }
      if (data.SOTHANG_DC === null || data.SOTHANG_DC === '') {
        this.$root.$toast.warning('Bạn chưa nhập số tháng phải đặt cọc cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }

      if (data.CUOC_DC === null || data.CUOC_DC === '') {
        this.$root.$toast.warning('Bạn chưa nhập cước đặt cọc cho thuê bao ' + data.MA_TB +  ' !')
        return false
      }
      let ktdatcoc_khongdong = await this.KIEMTRA_DATCOC_CA(data.THUEBAO_ID)
      if (ktdatcoc_khongdong !== 'ok') {
        if (parseFloat(data.cuoc_dc) <= 0) {
          this.$root.$toast.warning('Tiền đặt cọc cho thuê bao ' + data.MA_TB + ' phải lớn hơn 0')
          return false
        }
      }
      if (data.TIEN_TD === null || data.TIEN_TD === '') {
        this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng cho thuê bao ' + data.MA_TB )
        return false
      }

      if (data.NHOM_DATCOC_ID === 1 || data.NHOM_DATCOC_ID === 4) {
        if (parseFloat(data.TIEN_TD) <= 0) {
          this.$root.$toast.warning('Hãy kiểm tra lại số tiền trừ dần /tháng cho thuê bao ' + data.MA_TB)
          return false
        }
      }

      // if (this.isTest) {
      //   this.data.THAMSO_MD.nhap_CTV = 0
      // }
      // if (this.data.THAMSO_MD.nhap_CTV === 1) {
      //   if (!this.chkCTV || this.txtCTV === '') {
      //     this.$root.$toast.warning('Hãy chọn cộng tác viên')
      //     return false
      //   }
      // }
      // if (this.data.THAMSO_MD.nhap_NGT === 1) {
      //   if (!this.chkNguoiGT || this.txtNguoiGT === '') {
      //     this.$root.$toast.warning('Hãy chọn Người giới thiệu')
      //     return false
      //   }
      // }

      let ngay_bd = null
      let ngay_kt = null
      let thang_bd = 0
      let thang_kt = 0

      if (themmoi) {
        ngay_bd = moment(data.NGAY_KT_DC, 'DD/MM/YYYY').add(1, 'days').toDate()
        ngay_kt = this.getNgayKTDC(ngay_bd, data.HUONG_DC)

        thang_bd = moment(ngay_bd).format('YYYYMM')
        thang_kt = moment(ngay_kt).format('YYYYMM')
        let ngay_cn = await this.getNgay_Cn()

        if (thang_bd < moment(ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('YYYYMM')) {
          this.$root.$toast.warning('Khi thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại!')
          return false
        }
        let Kiemtra_loaitb_id_db_thuebao = await this.Kiemtra_loaitb_id_db_thuebao(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID)
        if (!Kiemtra_loaitb_id_db_thuebao) {
          this.$root.$toast.warning('Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường.' + data.ma_tb + '')
          return false
        }
        if ([15, 14, 13, 16].includes(data.DICHVUVT_ID)) {
          if (this.data.THAMSO_MD.check_chuahoa_cntt) {
            let check_chuahoa_cntt = await this.check_chuahoa_cntt(data.THUEBAO_ID, 31)
            if (check_chuahoa_cntt) {
              this.$root.$toast.warning('Thuê bao ' + data.ma_tb + ' này cần chuẩn hóa dữ liệu.\n Bạn chỉ được thực hiện Tạm dừng, Thanh lý hoặc chuẩn hóa ở chức năng Đặt mới.')
              return false
            }
          }
        }
        if (this.data.THAMSO_MD.kiemtra_no_tratruoc === 1) {
          let tienno = await this.TraCuu_No_MaTB(data.MA_TB)
          if (tienno > 0) {
            this.$root.$toast.warning('Thuê bao: ' + data.MA_TB +
                        ' còn nợ: ' + parseFloat(tienno) +
                        ' vnđ.\nPhải thanh toán trước khi thực hiện!')
            return false
          }
        }
      } else {
        // let thang_bd = parseInt(DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(this.dtpThangBD.value, 'MM'))
        let thang_bd = parseInt(moment(this.dtpThangBD.value).format('YYYYMM'))
        // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_dichvuvt_id = parseInt(data.DICHVUVT_ID)
        let in_loaitb_id = parseInt(data.LOAITB_ID)
        let in_thang_bd = parseInt(data.THANG_BD)
        let in_thang_kt = parseInt(data.THANG_KT)
        if (in_thang_bd > 0) {
          let ngay_cn = await this.getNgay_Cn()
          // if (in_thang_bd < parseInt(DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'yyyy') + DateTimeLib.toDateDisplayWithFormat(ngay_cn, 'MM'))) {
          if (in_thang_bd < parseInt(moment(ngay_cn, 'DD/MM/YYYY HH:mm:ss').format('YYYYMM'))) {
            if (in_thang_bd !== thang_bd) {
              this.$root.$toast.warning('Tháng bắt đầu đang nhỏ hơn tháng hiện tại. Bạn không được thay đổi tháng bắt đầu!')
              return false
            }
          }
        }
        let in_user_cn = data.NGUOI_CN
        let ma_nd = this.$auth.getUserName()
        console.log(in_user_cn, ma_nd)
        if (in_user_cn.toString() !== ma_nd.toString()) {
          this.$root.$toast.warning('Bạn không được thay đổi thông tin do user ' + in_user_cn + ' cập nhật')
          return false
        }
        if (data.TTHD_ID > 1) {
          let trangthai_hd = await this.trangthai_hd(data.TTHD_ID)
          this.$root.$toast.warning('Hợp đồng ' + trangthai_hd + ', bạn không thể thay đổi!')
          return false
        }
      }
      let thangbdmoi = 0
      let thangktmoi = 0
      thangbdmoi = thang_bd
      thangktmoi = thang_kt

      if (this.data.THAMSO_MD.tinhcuoc_ngay) {
        if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
          let testDate = moment(this.dtpNgayBDDC.value)
          let startDate = moment(this.dtpThangBD.value).startOf('month')
          let endDate = moment(this.dtpThangBD.value).endOf('month')
          // console.log(testDate, startDate, endDate)
          if(!testDate.isBetween(startDate, endDate, 'days', true)) {
            this.$root.$toast.warning('Ngày bắt đầu đặt cọc phải nằm trong tháng bắt đầu đặt cọc!')

            return false
          }
          testDate = moment(this.dtpNgayBD.value)
          startDate = moment(this.dtpThangBDMG.value).startOf('month')
          endDate = moment(this.dtpThangBDMG.value).endOf('month')
          // if (this.dtpNgayBD.value < moment(this.dtpThangBDMG.value).startOf('M') || this.dtpNgayBD.value > moment(this.dtpThangBDMG.value).endOf('M')) {
          if(!testDate.isBetween(startDate, endDate, 'days', true)) {
            this.$root.$toast.warning('Ngày bắt đầu miễn giảm phải nằm trong tháng bắt đầu miễn giảm!')
            return false
          }
        }
      }

      let ngaydbdcmoi = moment(ngay_bd).format('DD/MM/YYYY') // DateTimeLib.toDateDisplay(this.dtpNgayBDDC.value)
      let ngayktdcmoi = moment(ngay_kt).format('DD/MM/YYYY') // DateTimeLib.toDateDisplay(this.dtpNgayKTDC.value)

      if (themmoi) {
        if (this.data.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
            if (data.TRANG_THAI === 0) {
              let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(data.THUEBAO_ID, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, data.NHOM_DATCOC_ID)
              if (Kiemtra_tb_datcoc !== 'ok' && Kiemtra_tb_datcoc !== 'OK') {
                this.$root.$toast.warning(Kiemtra_tb_datcoc)
                return false
              }
            }
          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(data.THUEBAO_ID, data.LOAITB_ID, data.DICHVUVT_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        let dt = await this.SP_CHECK_TT_HDTB2(data.MA_TB, data.LOAITB_ID)
        console.log('🚀 ~ file: index.vue:2000 ~ Kiemtra_Dulieu ~ dt:', dt)
        if (dt && dt.length > 0) {
          this.$root.$toast.warning('Thuê bao đã có hợp đồng: ' + dt[0].ten_loaihd + ', mã giao dịch: ' + dt[0].ma_gd + ' chưa hoàn thiện, bạn không thể lập hợp đồng!')
          return false
        }
      } else {
        // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
        let in_ma_tb = data.MA_TB
        // let in_dichvuvt_id = parseInt(CurrentRow.DICHVUVT_ID)
        // let in_thuebao_dc_id = parseInt(CurrentRow.THUEBAO_DC_ID)
        if (this.data.THAMSO_MD.tinhcuoc_ngay) {
          if ([13, 14, 15, 16, 7, 8, 9].includes(data.DICHVUVT_ID) === false) {
            if (data.TRANG_THAI === 0) {
              let Kiemtra_tb_datcoc = await this.SP_KT_THOIGIAN_DATCOC(data.THUEBAO_ID, thangbdmoi, thangktmoi, ngaydbdcmoi, ngayktdcmoi, data.NHOM_DATCOC_ID)
              if (Kiemtra_tb_datcoc !== 'ok') {
                this.$root.$toast.warning(Kiemtra_tb_datcoc)
                return false
              }
            }

          } else {
            let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
            if (!Kiemtra_tb_datcoc) {
              this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
              return false
            }
          }
        } else {
          let Kiemtra_tb_datcoc = await this.Kiemtra_tb_datcoc(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
          if (!Kiemtra_tb_datcoc) {
            this.$root.$toast.warning('Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!')
            return false
          }
        }
        let Kiemtra_datcoc_tyle_KM_v1 = await this.Kiemtra_datcoc_tyle_KM_v1(data.THUEBAO_DC_ID, data.MA_TB, data.DICHVUVT_ID, data.LOAITB_ID, thangbdmoi, thangktmoi, data.NHOM_DATCOC_ID)
        if (Kiemtra_datcoc_tyle_KM_v1) {
          this.$root.$toast.warning('Thuê bao đang còn thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!')
          return false
        }
        // if (in_ma_tb.trim() !== data.ma_tb.trim()) {
        //   this.$root.$toast.warning('Bạn không được thay đổi số máy/account')
        //   return false
        // }
      }
      try {
        if (data.LOAITB_ID === this.LoaiHinhTB.INTERNET_MYTV) {
          // let CurrentRow = this.$refs.grid.getSelectedRecords()[0]
          // let in_ma_tb = this.txtMaTB
          // if (!themmoi) { in_ma_tb = CurrentRow.MA_TB }
          if (data.TRANG_THAI === 0) {
            let ckb_km = await this.kiemtra_km_2731('0', data.CHITIETKM_ID, 0, data.MA_TB)
            if (ckb_km === null || ckb_km === '') {
              this.$root.$toast.warning('Không lấy được thông tin kiểm tra đặt cọc')
              return false
            }
            if (ckb_km.toString() !== '1' && ckb_km.toString() !== '-1') {
              this.$root.$toast.warning(ckb_km.toString())
              return false
            }
          }

        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi kiểm tra đặt cọc của thuê bao ' + data.ma_tb +  ':' + error)
        return false
      }
      return true
    },
    async thuchienGiaHan () {
      if (this.dgvDatCoc.DataSource.length <= 0) {
        this.$toast.error('Không tìm thấy thuê bao cần gia hạn.')
        return
      }

      let f = this.dgvDatCoc.DataSource.find(x => x._CHECK === true && x.TRANG_THAI === 2)

      if (f !== undefined) {
        this.$toast.error('Trả trước bạn tham gia đã hết hiệu lực, vui lòng chọn đặt cọc chính sách mới!')
        return
      }
      let ds_tb = this.dgvDatCoc.DataSource.filter(x => x._CHECK === true && x.TRANG_THAI === 1)

      if (ds_tb.length <= 0) {
        this.$toast.error('Vui lòng chọn thuê bao cần gia hạn.')
        return
      }

      if (this.isTest) {
        this.data.THAMSO_MD.nhap_CTV = 0
      }
      if (this.data.THAMSO_MD.nhap_CTV === 1) {
        if (!this.chkCTV || this.txtCTV === '') {
          this.$root.$toast.warning('Hãy chọn cộng tác viên')
          return false
        }
      }
      if (this.data.THAMSO_MD.nhap_NGT === 1) {
        if (!this.chkNguoiGT || this.txtNguoiGT === '') {
          this.$root.$toast.warning('Hãy chọn Người giới thiệu')
          return false
        }
      }

      this.taoDuLieuGiaHan(true)

      let promises = []
      this.$root.showLoading(true)

      for(let i = 0; i < ds_tb.length; i++) {
        if (ds_tb[i].KHUYENMAI_ID !== null) {
          promises.push(await this.validateGiaHanData(ds_tb[i]), true)
        }
      }
      let ret = await Promise.all(promises)
      if (ret.find(x => x === false) !== undefined) {
        this.$toast.error('Đã phát sinh lỗi khi lập hợp đồng gia hạn đặt cọc.')
        this.$root.showLoading(false)
        return false
      }

      let result = await this.GiaHan_HopDong_New()
      if (result) {
        this.inDex_load = 0
        await this.txtMaGD_KeyPress()
      } else {
        this.$root.$toast.error('Lỗi khi gia hạn đặt cọc.')
      }
      this.$root.showLoading(false)
    },
    async thucHienXacNhanDatCoc(data, thoai, ctkm) {
      let item = {...data}
      let tienthoai = 0
      let thuctra = Number(ctkm.DATCOC_CSD)
      if (!!thoai) {
        tienthoai = await this.getTienThoai({
            RKM_ID : data.RKM_ID,
            THUEBAO_DC_ID : data.THUEBAO_DC_ID,
            THANG_THOAI : thoai.thang_thoai,
            NGAY_THOAI : thoai.ngay_thoai
          })
        thuctra = thuctra - tienthoai
        if (thuctra < 0) {
          this.$toast.error('Số tiền thoái đang nhiều hơn tiền cọc mới. Vui lòng chọn chính sách khác.')
          return null
        }
      }
      item.SOTHANG_DC = Number(this.txtSoThang)
      item.NGAY_BD_DC = moment(this.dtpNgayBDDC.value).format('DD/MM/YYYY')
      item.NGAY_KT_DC = moment(this.dtpNgayKTDC.value).format('DD/MM/YYYY')
      item.THANG_BD = moment(this.dtpThangBD.value).format('YYYYMM')
      item.THANG_KT = moment(this.dtpThangKT.value).format('YYYYMM')
      item.NGAY_BD = moment(this.dtpNgayBD.value).format('DD/MM/YYYY')
      item.NGAY_KT = moment(this.dtpNgayKT.value).format('DD/MM/YYYY')
      item.THANG_BD_MG = moment(this.dtpThangBDMG.value).format('YYYYMM')
      item.THANG_KT_MG = moment(this.dtpThangKTMG.value).format('YYYYMM')
      item.CUOC_DC = ctkm.DATCOC_CSD
      item.TIEN_TD = ctkm.TIEN_TD
      item.TIENTHOAI = tienthoai
      item.THUC_TRA = ctkm.DATCOC_CSD - tienthoai
      item.NGUOI_CN = this.$auth.getUserName()
      item.KHUYENMAI_ID = this.dataSelected.KHUYENMAI_ID
      item.CHITIETKM_ID = ctkm.CHITIETKM_ID
      item.TEN_CHITIETKM = ctkm.CHITIETKM_ID + '-' + ctkm.TEN_CTKM
      item.NHOM_DATCOC_ID = this.dataSelected.NHOM_DATCOC_ID
      item.GHICHU = this.txtGhiChu
      item._CHECK = true

      return item
    },
    async xacNhanDatCoc() {
      this.$root.showLoading(true)

      let foundIndex = this.dgvDatCoc.DataSource.findIndex(x => x._KEY == this.THUEBAO._KEY)

      if (this.dataSelected.NHOM_DATCOC_ID === 4) {
        let ds_ddv = []

        let not_ds_ddv = []

        if (this.THUEBAO.GOI_DDV_ID === null && this.THUEBAO.DICHVUVT_ID !== 2) {
          this.THUEBAO._CHECK = true
          ds_ddv.push(this.THUEBAO)
          not_ds_ddv = this.dgvDatCoc.DataSource.filter(x => x._KEY !== this.THUEBAO._KEY)
        } else {
          ds_ddv = this.dgvDatCoc.o_ds.filter(x => x._CHECK && x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID && x.DICHVUVT_ID !== 2)
          not_ds_ddv = this.dgvDatCoc.DataSource.filter(
            x => x.GOI_DDV_ID !== this.THUEBAO.GOI_DDV_ID
              || (!x._CHECK && x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID)
            )

          console.log(ds_ddv, not_ds_ddv)
        }

        let ds = []

        for (const element of ds_ddv) {
          let item = {...element}
            // console.log('item', item.THUEBAO_ID)
            // let ret = await this.lay_chitiet_km_datcoc(this.dataSelected.KHUYENMAI_ID, item.LOAITB_ID, item.TOCDO_ID, item.MUCCUOC_ID, this.dataSelected.NHOM_DATCOC_ID, item.THUEBAO_ID, item.TRANGBI_ID)
            let ret = this.dsCTKM.filter(x => x.THUEBAO_ID === item.THUEBAO_ID)
            let ctkm = null

            console.log('dsCTKM', ret)

            if (!this.ct_khuyenmai.id || item._KEY !== this.THUEBAO._KEY) {
              // ctkm = ret.find(x => x.HUONG_DC === Number(this.txtSoThang))
              ctkm = this.getPromotionDetail(ret, Number(this.txtSoThang), item)
            } else {
              ctkm = ret.find(x => x.CHITIETKM_ID === this.ct_khuyenmai.id)
            }

            console.log(ctkm)
            if (item.TRANG_THAI !== 0) {
              let ngay_ktdc = moment(item.NGAY_KT_DC, 'DD/MM/YYYY')
              let ngay_thoai = moment(this.dtpNgayBDDC.value).format('YYYYMMDD')
              let thang_thoai = moment(ngay_thoai, 'YYYYMMDD').format('YYYYMM')
              if (ngay_ktdc.isSameOrAfter(moment(ngay_thoai, 'YYYYMMDD'))) {
                if (ctkm !== undefined) {
                let ret = await this.thucHienXacNhanDatCoc(item, {thang_thoai: thang_thoai, ngay_thoai: ngay_thoai}, ctkm)
                if (ret === null) return
                ret.TRANG_THAI = 2
                ds.push(ret)
              }
              } else {
                if (ctkm !== undefined) {
                  let ret = await this.thucHienXacNhanDatCoc(item, null, ctkm)
                  if (ret === null) return
                  ds.push(ret)
                }
              }
            } else {
              if (ctkm !== undefined) {
                let ret = await this.thucHienXacNhanDatCoc(item, null, ctkm)
                if (ret === null) return
                ds.push(ret)
              }
            }
        }

        for (let element of not_ds_ddv) {
          element._CHECK = false
          ds.push(element)
        }

        this.dgvDatCoc.DataSource = [...ds]

      } else {
        let ds = [...this.dgvDatCoc.DataSource]

        if (ds[foundIndex].TRANG_THAI !== 0) {
          let ngay_ktdc = moment(this.dgvDatCoc.o_ds[foundIndex].NGAY_KT_DC, 'DD/MM/YYYY')
          let ngay_thoai = moment(this.dtpNgayBDDC.value).format('YYYYMMDD')
          let thang_thoai = moment(ngay_thoai, 'YYYYMMDD').format('YYYYMM')
          if (ngay_ktdc.isSameOrAfter(moment(ngay_thoai, 'YYYYMMDD'))) {
            let ret = await this.thucHienXacNhanDatCoc(this.dgvDatCoc.o_ds[foundIndex],
              {thang_thoai: thang_thoai, ngay_thoai: ngay_thoai},
              {
                DATCOC_CSD: Number(this.txtCuocDC),
                TIEN_TD: Number(this.txtTienTD),
                TEN_CTKM: this.ct_khuyenmai.text,
                CHITIETKM_ID: this.dataSelected.CHITIETKM_ID
              })
            // console.log('ret', ret)
            if (ret === null) return
            ds[foundIndex] = ret
            this.dgvDatCoc.DataSource = [...ds]
            this.refreshGrid()
          } else {

            let ret = await this.thucHienXacNhanDatCoc(this.dgvDatCoc.o_ds[foundIndex],
              null,
              {
                DATCOC_CSD: Number(this.txtCuocDC),
                TIEN_TD: Number(this.txtTienTD),
                TEN_CTKM: this.ct_khuyenmai.text,
                CHITIETKM_ID: this.dataSelected.CHITIETKM_ID
              })
            // console.log('ret', ret)
            if (ret === null) return
            ds[foundIndex] = ret
            this.dgvDatCoc.DataSource = [...ds]
            this.refreshGrid()
          }
        } else {
          let ret = await this.thucHienXacNhanDatCoc(this.dgvDatCoc.o_ds[foundIndex],
              null,
              {
                DATCOC_CSD: Number(this.txtCuocDC),
                TIEN_TD: Number(this.txtTienTD),
                TEN_CTKM: this.ct_khuyenmai.text,
                CHITIETKM_ID: this.dataSelected.CHITIETKM_ID
              })
            // console.log('ret', ret)
            if (ret === null) return
            ds[foundIndex] = ret
            this.dgvDatCoc.DataSource = [...ds]
            this.refreshGrid()
        }
      }
      this.$root.showLoading(false)

    },
    async btnXacNhanTTDatCoc() {
      if (this.dgvDatCoc.DataSource.length <= 0)  return
      let foundIndex = this.dgvDatCoc.DataSource.findIndex(x => x._KEY == this.THUEBAO._KEY)

      let errors = []
      if (this.dataSelected.NHOM_DATCOC_ID === null) {
        errors.push('Chưa chọn Nhóm đặt cọc.')
      }
      if (this.dataSelected.KHUYENMAI_ID === null) {
        errors.push('Chưa chọn Trả trước.')
      }
      if (this.dataSelected.CHITIETKM_ID === null) {
        errors.push('Chưa chọn Chi tiết khuyến mãi.')
      }
      if (errors.length > 1) {
        this.$toast.error(errors.join('\r\n'))
        return
      }

      if (this.dataSelected.NHOM_DATCOC_ID === 4) { // nếu nhóm đa dịch vụ
        let f = this.dgvDatCoc.o_ds.filter(         // danh sách gốc -> chọn
          x => x.GOI_DDV_ID === this.THUEBAO.GOI_DDV_ID // cùng gói đang chọn
            && x.TRANG_THAI !== 0                       // không phải loại chưa đặt cọc
            && x._CHECK
            && x.DICHVUVT_ID !== 2)                     // loại bỏ di động
            .map(x => moment(x.NGAY_KT_DC, 'DD/MM/YYYY').toDate())  // lấy ds ngày đặc cọc sau cùng
        // console.log(new Date(Math.max(...f.map(e => moment(e.NGAY_KT_DC, 'DD/MMM/YYYY').toDate()))))
        let ngay_ktdc = moment(new Date(Math.max(...f)))  // ngày KTDC sau cùng
        // let ngay_ktdc = moment(d, 'DD/MM/YYYY')
        let ngay_thoai = moment(this.dtpNgayBDDC.value).format('YYYYMMDD')
        // let thang_thoai = moment(ngay_thoai, 'YYYYMMDD').format('YYYYMM')

        console.log('Dòng 5407', ngay_ktdc.format('DD/MM/YYYY'), ngay_thoai)
        if (ngay_ktdc.isSameOrAfter(moment(ngay_thoai, 'YYYYMMDD'))) {
          let  msg = `Ngày bắt đầu đặt cọc kỳ mới đang trước ngày kết thúc của kỳ cũ, chương trình sẽ tự động cân bằng tiền còn lại, bạn có muốn thực hiện không?`
          try {
            this.$confirm(
              msg,
              "Xác nhận thông tin đặt cọc",
              {
                confirmButtonText: "Đồng ý",
                cancelButtonText: "Không",
              }
            )
              .then(async () => {
                this.xacNhanDatCoc()
                return
              })
              .catch(async () => {
                console.log('Cancel')
                return
              })
          } catch (error) {
            return
          }
        } else {
          this.xacNhanDatCoc()
          return
        }

      } else {
        if (this.dgvDatCoc.o_ds[foundIndex].TRANG_THAI !== 0) {
          let ngay_ktdc = moment(this.dgvDatCoc.o_ds[foundIndex].NGAY_KT_DC, 'DD/MM/YYYY')
          let ngay_thoai = moment(this.dtpNgayBDDC.value).format('YYYYMMDD')
          // let thang_thoai = moment(ngay_thoai, 'YYYYMMDD').format('YYYYMM')
          if (ngay_ktdc.isSameOrAfter(moment(ngay_thoai, 'YYYYMMDD'))) {
            let  msg = `Ngày bắt đầu đặt cọc kỳ mới đang trước ngày kết thúc của kỳ cũ, chương trình sẽ tự động cân bằng tiền còn lại, bạn có muốn thực hiện không?`
            try {
              this.$confirm(
                msg,
                "Xác nhận thông tin đặt cọc",
                {
                  confirmButtonText: "Đồng ý",
                  cancelButtonText: "Không",
                }
              )
                .then(async () => {
                  this.xacNhanDatCoc()
                  return
                })
                .catch(async () => {
                  console.log('Cancel')
                  return
                })
            } catch (error) {
              return
            }
          } else {
            this.xacNhanDatCoc()
            return
          }
        } else
          this.xacNhanDatCoc()
      }
    },
    btnXoaTTDatCoc() {
      this.CLearThongTinDatCoc()
      // this.btnXacNhanTTDatCoc()
    },
    async acceptChonMaTBHandler (e) {
      // let ds = await this.LAY_DS_THUEBAO_DATCOC_THEO_TBID(e.THUEBAO_ID)
      let ds = await this.LAY_DS_THUEBAO_DATCOC_THEO_TBID(e.THUEBAO_ID, e.GOI_DDV_ID)
      if (this.dgvDanhSach.length > 0) {
        this.dgvDatCoc.DataSource = [...this.dgvDatCoc.DataSource, ...ds]
      } else {
        this.dgvDatCoc.DataSource = [...ds]
      }

      this.dgvDatCoc.o_ds = [...this.dgvDatCoc.DataSource]

      this.ma_tb = e.MA_TB
      if (this.ma_tb) {
        let f = this.dgvDatCoc.DataSource.find(x => x.MA_TB === this.ma_tb)
        f._CHECK = true
        let row = {rowData: f}
        // console.log(this.dgvDatCoc.DataSource, this.ma_tb)
        this.dsTBDatCocRecordClick(row)
      }

      let f = this.dgvDatCoc.DataSource.find(x => x.HDKH_ID !== null)
      if (f !== undefined)  this.SetButton(3)
      else this.SetButton(1)


    },
    onChangeCustomDropDown (id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onChangeChiTietKhuyenMai (e) {
      if (e === null) return

      this.dataSelected.CHITIETKM_ID = e.CHITIETKM_ID
      this.ct_khuyenmai.id = e.CHITIETKM_ID
      this.ct_khuyenmai.text = e.TEN_CTKM
      this.ct_khuyenmai.so_thang = e.HUONG_DC
      $('#ctkhuyenmai-pd').hide()
    },
    onChangedtpThangBD (val) {
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
          this.dtpThangBDMG.value = moment(new Date(val)).add(Number(ds[0].THANG_BDKM) + Number(ds[0].HUONG_DC) - 1, 'M')
          this.dtpThangKTMG.value = moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M')
        } else if (ds[0].LOAI_BDKM.toString() === '4') {
          this.dtpThangBDMG.value = moment(new Date(val)).add(Number(ds[0].THANG_BDKM), 'M')
          this.dtpThangKTMG.value = moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M')
        } else if (ds[0].LOAI_BDKM.toString() === '1') {
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.value = moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M')
        } else {
          this.dtpThangBDMG.value = val
          this.dtpThangKTMG.value = moment(this.dtpThangBDMG.value).add(Number(ds[0].HUONG_KM) - 1, 'M')
        }
      }
    },
    onChangedtpNgayBDDC (val) {
      if (this.Lock) return
      if (val === null) {
        val = new Date()
        this.dtpNgayBDDC.value = val
      }
      if (this.dataSelected.CHITIETKM_ID === null) {
        return
      }
      try {
        let item = this.data.DS_CHITIETKM.find(x => x.CHITIETKM_ID === this.dataSelected.CHITIETKM_ID)
        if (!item)  return
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
    onChangedtpNgayBD (val) {
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
    checkBoxChange (args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        this.checkAll(args.checked)
        this.refreshAggTB()
      }
    },
    checkAll (f) {
      let temp = [...this.dgvDatCoc.DataSource]
      if (f) { // true
        console.log(this.$refs.dgvDatCoc)
        let filter = this.$refs.dgvDatCoc.getFilteredRecords()
        if (filter !== undefined && filter.length > 0) {
          let checked = filter.map(x => x._KEY)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i]._KEY)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.dgvDatCoc.DataSource = [...temp]
    },
    checkGiaHan (f) {
      let temp = [...this.dgvDatCoc.DataSource]
      if (f) { // true
        let filter = this.$refs.dgvDatCoc.getFilteredRecords()
        if (filter !== undefined && filter.length > 0) {
          let checked = filter.map(x => x._KEY)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i]._KEY)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) {
            if (temp[i].TRANG_THAI === 1) temp[i]._CHECK = true
          }
        }
      } else {
        for (let i = 0; i < temp.length; i++) {
          if (temp[i].TRANG_THAI === 1) temp[i]._CHECK = false
        }
      }
      this.dgvDatCoc.DataSource = [...temp]
    },
    checkDatCoc (f) {
      let temp = [...this.dgvDatCoc.DataSource]
      if (f) { // true
        let filter = this.$refs.dgvDatCoc.getFilteredRecords()
        if (filter !== undefined && filter.length > 0) {
          let checked = filter.map(x => x._KEY)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i]._KEY)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) {
            if (temp[i].TRANG_THAI !== 1) temp[i]._CHECK = true
          }
        }
      } else {
        for (let i = 0; i < temp.length; i++) {
          if (temp[i].TRANG_THAI !== 1) temp[i]._CHECK = false
        }
      }
      this.dgvDatCoc.DataSource = [...temp]
    },
    checkDatCocNhom (f, args) {

      let row = args.rowInfo.rowData
      if (!row)  return
      let temp = [...this.dgvDatCoc.DataSource]
      if (f) {
        let filter = this.$refs.dgvDatCoc.getFilteredRecords()
        if (filter !== undefined && filter.length > 0) {
          let checked = filter.map(x => x._KEY)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i]._KEY)
                  // && temp[i].TRANG_THAI !== 1
                  && temp[i].GOI_DDV_ID === row.GOI_DDV_ID
                  && temp[i].DICHVUVT_ID !== 2) {
              temp[i]._CHECK = true
            }
          }
        } else {
          for (let i = 0; i < temp.length; i++) {
            if (temp[i].GOI_DDV_ID === row.GOI_DDV_ID
                  && temp[i].DICHVUVT_ID !== 2
                  // && temp[i].TRANG_THAI !== 1
                  ) {
              temp[i]._CHECK = true
            }
          }
        }
      } else {
        for (let i = 0; i < temp.length; i++) {
          if (temp[i].GOI_DDV_ID === row.GOI_DDV_ID
                && temp[i].DICHVUVT_ID !== 2
                // && temp[i].TRANG_THAI !== 1
                ) {
            temp[i]._CHECK = false
          }
        }
      }
      this.dgvDatCoc.DataSource = [...temp]
      this.dsHDRowSelected({data : row})
    },
    dsTBDatCocRowSelected (args) {
      console.log('sel', args)
      console.log('dsTBDatCocRowSelected')
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.dgvDatCoc.DataSource.find(x => x._KEY === args.data._KEY)
        f._CHECK = true
        // let goi_ddv_id = f.GOI_DDV_ID
        // if (!!goi_ddv_id) {
        //   let ds = [...this.dgvDatCoc.DataSource]
        //   console.log('dsTBDatCocRowSelected', ds)
        //   for (let i = 0; i < ds.length; i++) {
        //     // if(ds[i].GOI_DDV_ID === goi_ddv_id && ds[i].TRANG_THAI !== 1) ds[i]._CHECK = true
        //     if(ds[i].GOI_DDV_ID === goi_ddv_id) ds[i]._CHECK = true
        //   }
        //   this.dgvDatCoc.DataSource = [...ds]
        // } else {
        //   f._CHECK = true
        // }
        // if (f !== undefined) f._CHECK = true
        this.refreshAggTB()
      }

    },
    dsTBDatCocRowDeselected (args) {
      console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.dgvDatCoc.DataSource.find(x => x._KEY === args.data._KEY)
        // let goi_ddv_id = f.GOI_DDV_ID
        // if (!!goi_ddv_id) {
        //   let ds = [...this.dgvDatCoc.DataSource]
        //   // console.log(ds)
        //   for (let i = 0; i < ds.length; i++) {
        //     if(ds[i].GOI_DDV_ID === goi_ddv_id) ds[i]._CHECK = false
        //   }
        //   this.dgvDatCoc.DataSource = [...ds]
        // } else {
        //   f._CHECK = false
        // }
        if (!!f) f._CHECK = false
        console.log(this.dgvDatCoc)
        this.refreshAggTB()
      }
    },
    dsTBDatCocDatabound (args) {
      // console.log('db')
      this.refreshGrid()
    },
    refreshGrid () {
      let obj = this.$refs.dgvDatCoc.ej2Instances

      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData.records
        console.log(data)
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        console.log(selectedCurrentPage)
        obj.selectRows(selectedCurrentPage)
      }
    },
    refreshAggTB () {
      this.$refs.dgvDatCoc.ej2Instances.aggregateModule.refresh()
    },
    async dsTBDatCocRecordClick (row) {
      // let classList = [ ...row.target.classList ]
      // if (classList.includes('e-icons')) {
      //   return
      // }
      try {
        if (this.dgvDatCoc.DataSource.length === 0) return false
        if (row === undefined) return false
        if (row) {

          let f = this.dgvDatCoc.DataSource.find(x => x._KEY === row.rowData._KEY)
          console.log('🚀 ~ file: index.vue:4559 ~ dsTBDatCocRecordClick ~ row:', f)

          this.THUEBAO = f

          if (f.HDKH_ID !== undefined) {
            this.SetButton(3)
            this.txtMaGD = f.MA_GD
          } else this.SetButton(1)

          if (this.THUEBAO.DICHVUVT_ID === 2) {
            await this.HienThiThongTinThueBao(f)
          } else {
            this.loading(true)
            // await Promise.all[await this.HienThiThongTinThueBao(f), await this.HienThiThongTinDatCoc(f)]
            await this.HienThiThongTinThueBao(f)
            this.loading(false)
          }
          this.CLearThongTinDatCoc()
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 4573 ~ dsTBDatCocRecordClick ~ error', error)
      }
    },
    onChangeSoThangDC (e) {

      if(e.value !== null) {
        let ds = this.dsCTKM.filter(x => x.THUEBAO_ID === this.THUEBAO.THUEBAO_ID && x.HUONG_DC === e.value)
        Vue.set(this.data, 'DS_CHITIETKM', ds)
        if (ds.length > 0) {
          this.ct_khuyenmai.id = ds[0].CHITIETKM_ID
          this.ct_khuyenmai.text = ds[0].TEN_CTKM
          this.dataSelected.CHITIETKM_ID = ds[0].CHITIETKM_ID
        }
      } else {
        Vue.set(this.data, 'DS_CHITIETKM', this.dsCTKM)
        this.ct_khuyenmai.id = null
        this.ct_khuyenmai.text = null
        this.dataSelected.CHITIETKM_ID = null
      }
    },
    makeKey (length = 10) {
      let result = '';
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      const charactersLength = characters.length;
      let counter = 0;
      while (counter < length) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
        counter += 1;
      }
      return result;
    },
    async contextMenuClick (args) {
      // console.log(args)
      if (args.item.id === 'checkAll') {
        this.checkAll(true)
      } else if (args.item.id === 'uncheckAll') {
        this.checkAll(false)
      } else if (args.item.id === 'checkGiaHan') {
        this.checkGiaHan(true)
      } else if (args.item.id === 'uncheckGiaHan') {
        this.checkGiaHan(false)
      } else if (args.item.id === 'thuchienGiaHan') {
        await this.thuchienGiaHan()
      } else if (args.item.id === 'checkDatCoc') {
        this.checkDatCoc(true)
      } else if (args.item.id === 'uncheckDatCoc') {
        this.checkDatCoc(false)
      } else if (args.item.id === 'checkDatCocNhom') {
        this.checkDatCocNhom(true, args)
      } else if (args.item.id === 'uncheckDatCocNhom') {
        this.checkDatCocNhom(false, args)
      } else if (args.item.id === 'thuchienDatCoc') {
        this.tsbtnGhiLai2_Click()
      }
    },
    getNgayKTDC (ngay_bd, huong_dc) {
      let ngay = moment(ngay_bd).add(parseInt(huong_dc), 'M')
      let ngayktdc = moment(ngay).subtract(1, 'd').toDate()
      return ngayktdc
    },
    getNgayDC (params) {
      let res_obj = {
        NGAY_BD_DC : null,
        NGAY_KT_DC : null,
        THANG_BD_DC : null,
        THANG_KT_DC : null,
        NGAY_BD_MG : null,
        NGAY_KT_MG : null,
        THANG_BD_MG : null,
        THANG_KT_MG : null
      }
      let month = moment().startOf('month')
      res_obj.NGAY_BD_DC = moment(params.NGAY_BD, 'DD/MM/YYYY').add(parseInt(params.HUONG_DC), 'M').toDate()
      res_obj.NGAY_KT_DC = moment(res_obj.NGAY_BD_DC).subtract(1, 'd').toDate()
      res_obj.THANG_BD_DC = moment(res_obj.NGAY_BD_DC).format('YYYYMM')
      res_obj.THANG_KT_DC = moment(res_obj.NGAY_KT_DC).format('YYYYMM')

      if (params.LOAI_BDDC === 1) {

      }

      if ([13, 14, 15, 16, 7, 8, 9].includes(params.DICHVUVT_ID) === false) {
        res_obj.THANG_BD_MG = res_obj.THANG_BD_DC
        res_obj.THANG_KT_MG = res_obj.THANG_KT_DC
        res_obj.NGAY_BD_MG = res_obj.NGAY_BD_DC
        res_obj.NGAY_KT_MG = res_obj.NGAY_KT_DC
      } else {
        if (params.LOAI_BDKM === 1) {
          res_obj.THANG_BD_MG = moment(month).add(params.THANG_BDKM, 'M').format('YYYYMM')
          res_obj.THANG_KT_MG = moment(res_obj.THANG_BD_MG, 'YYYYMM').add(params.HUONG_MG, 'M').subtract(1, 'M')
        } else if (params.LOAI_BDKM === 2) {
          res_obj.THANG_BD_MG = moment(res_obj.THANG_BD_DC, 'YYYYMM').add(params.THANG_BDKM + params.HUONG_DC - 1, 'M').format('YYYYMM')
          res_obj.THANG_KT_MG = moment(res_obj.THANG_BD_MG, 'YYYYMM').add(params.HUONG_MG, 'M').subtract(1, 'M')
        } else if (params.LOAI_BDKM === 4) {
          res_obj.THANG_BD_MG = moment(res_obj.THANG_BD_DC, 'YYYYMM').add(params.THANG_BDKM, 'M')
          res_obj.THANG_KT_MG = moment(res_obj.THANG_BD_MG, 'YYYYMM').add(params.HUONG_KM - 1, 'M')
        } else {
          res_obj.THANG_BD_MG = res_obj.THANG_BD_DC
          res_obj.NGAY_KT_MG = moment(res_obj.THANG_BD_MG, 'YYYYMM').add(params.HUONG_KM - 1, 'M')
        }
      }
    },
    async dsHDRowSelected (args) {
      if (args !== null) {
        this.tbHDSelected = args.data
        this.THUEBAO = args.data
        this.loading(true)
        // await Promise.all[await this.HienThiThongTinThueBao(this.tbHDSelected), await this.HienThiThongTinDatCoc(this.tbHDSelected)]
        await this.HienThiThongTinThueBao(this.tbHDSelected)
        this.loading(false)
      } else {
        this.tbHDSelected = null
      }

      console.log(this.tbHDSelected)
    },
    async layTienThoaiDatCoc_v4 (data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/cap-nhat-thue-bao-dat-coc/lay_tienthoai_datcoc_v4`, data)
        if (response.data != null && response.data.length > 0) {
          return response.data[0].tienthoai
        }
        throw {response: { data: response}}
      } catch (err) {
        return 0
      }
    },
    queryCellInfo (args) {
      if (args.column.field === 'TRANG_THAI_TEXT') {
        if (args.data.TRANG_THAI === 0) {
          args.cell.classList.add('e-inactivecolor')
        } else if (args.data.TRANG_THAI === 1) {
          args.cell.classList.add('e-activecolor')
        } else if (args.data.TRANG_THAI === 2) {
          args.cell.classList.add('e-inactivecolor')
        }
      } else if (args.column.field === 'CUOC_DC' || args.column.field === 'TIENTHOAI' || args.column.field === 'THUC_TRA') {
        args.cell.classList.add('grid-tien')
      }
    },
    dsHDqueryCellInfo (args) {
      if (args.column.field === 'CUOC_DC' || args.column.field === 'TIENTHOAI' || args.column.field === 'THUC_TRA') {
        args.cell.classList.add('grid-tien')
      }
    },
    xulyDSThueBaoDatCoc (ds) {
      let tmp = ds.map(x => x.THUEBAO_ID)
      let group = [...new Set(tmp)]
      let ret = []
      console.log('group', group)

      for(const item of group) {

        let f = ds.filter(x => x.THUEBAO_ID === item && (x.NHOM_DATCOC_ID === 1 || x.NHOM_DATCOC_ID === 4) && x.NGAY_KT_DC !== null)
          .map(x => moment(x.NGAY_KT_DC, 'DD/MM/YYYY').toDate())

        let d = moment(new Date(Math.max(...f))).format('DD/MM/YYYY')
        let r = ds.filter(x =>
          (x.THUEBAO_ID === item && x.NHOM_DATCOC_ID !== 1 && x.NHOM_DATCOC_ID !== 4)
          || ((x.NHOM_DATCOC_ID === 1 || x.NHOM_DATCOC_ID === 4) && x.NGAY_KT_DC === d && x.THUEBAO_ID === item)
          )

        ret.push(...r)
      }
      return ret
    },
    getTheLastDate (ds, nhom_dc, goi_ddv_id) {
      if (!ds || ds.length === 0)  return null
      if (nhom_dc === 4) { // nhóm đa dịch vụ
        let f = ds.filter(x => x.GOI_DDV_ID === goi_ddv_id).map(x => moment(x.NGAY_KT_DC, 'DD/MM/YYYY').toDate())
        return moment(new Date(Math.max(...f))).format('DD/MM/YYYY')
      }
    },
    getPromotionDetail (dsKM, months, old) {
      if (!old) {
        let f = dsKM.filter(x => x.HUONG_DC === months).map(x => x.DATCOC_CSD)
        let min = Math.min(...f)
        return dsKM.find(x => x.DATCOC_CSD === min)
      } else {
        let cur = dsKM.find(x => x.DATCOC_CSD === old.CUOC_DC && x.HUONG_DC === months)
        if (cur !== undefined)  return cur
        let f = dsKM.filter(x => x.HUONG_DC === months).map(x => x.DATCOC_CSD)

        let min = Math.min(...f)
        console.log(old, f, min)
        return dsKM.find(x => x.DATCOC_CSD === min && x.HUONG_DC === months)
      }
    }
  },
  async created () {
    try {
      Vue.set(this.data, 'DS_CHITIETKM', [])
      this.$root.showLoading(true)
      await this.loadDanhMuc()

      this.min_thang_dc = moment().startOf('month').toDate()
      this.$root.showLoading(false)

      // if (this.$route.query.ma_gd !== undefined && this.$route.query.ma_gd !== null && this.$route.query.ma_gd !== '') {
      if (!!this.$route.query.ma_gd && this.$route.query.ma_gd.trim() !== '') {
        this.txtMaGD = this.$route.query.ma_gd.trim()
      } else if (!!this.$route.query.ma_tb && this.$route.query.ma_tb.trim() !== '') {
        this.txtMaTB = this.$route.query.ma_tb.trim()
      }
      if (!!this.$route.query.ob_id) {
        this.ob_id = this.$route.query.ob_id
      }
      if (!!this.$route.query.user_ipcc && this.$route.query.user_ipcc.trim() !== '') {
        this.user_ipcc = this.$route.query.user_ipcc.trim()
      }
    } catch (error) {
      console.log(error)
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
          this.data.THAMSO_MD.nhap_CTV = 1
        }
      }
      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'HIENTHI_IMG_DATCOC')
      if (chk.length > 0) {
        this.enableCtrl.tsbtnXemanh = true
      }
      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TRUNG_MATB')
      if (chk.length > 0) {
        this.data.THAMSO_MD.trung_matb = 1
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'CHECK_CHUANHOA_CNTT')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.check_chuahoa_cntt = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TINHCUOC_NGAY')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.tinhcuoc_ngay = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'CHAN_TRATRUOC_CUOCNONG')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.CHAN_TRATRUOC_CUOCNONG = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TS_KHONG_BATBUOC_TT_TRATRUOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.ts_khong_bb_thanhtoan_hths = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KIEMTRA_NO_TRATRUOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.kiemtra_no_tratruoc = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KIEMTRA_HD_KHAC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.KiemTra_HopDongKhac = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KHONG_LOAD_CTV')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.khong_load_ctv = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'PHAINHAP_THOIHAN')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.phainhap_thoihan = true
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'KHONG_HIENTHI_TRACUU_DB_TB_DATCOC')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.khong_ht_tracuudb = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'SINH_MA_GD_THEO_DV')
      if (chk.length > 0) {
        this.data.THAMSO_MD.ts_sinhma_gd_theo_donvi = true
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'MACDINH_CTV_NGUOIDUNG')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.MACDINH_CTV_NGUOIDUNG = 1
        }
      }

      chk = this.data.THAMSO_MD.filter(x => x.MA_TS === 'TS_DATCOC_NGUOIGT')
      if (chk.length > 0) {
        if (chk[0].TEN_TS === '1') {
          this.data.THAMSO_MD.nhap_NGT = 1
        }
      }
    }
    if (this.data.THAMSO_MD.MACDINH_CTV_NGUOIDUNG === 1) {
    }

    this.SetButton(-1)
    if (!!this.txtMaGD) {
      await this.txtMaGD_KeyPress()
    } else if (!!this.txtMaTB) await this.txtMaTB_KeyPress()
  },
  provide: {
    grid: [Group, Page, Sort, Filter, Aggregate, Resize, ContextMenu ]
  }
}
</script>
<style>
.el-message-box__content {
  color: red!important
}
.vattu_box {
  position: relative;
}

.vattu_table {
  position: absolute;
  z-index: 1000;
  max-height: 360px;
  background: aliceblue;
  overflow-y: scroll;
  min-width: 700px;
}
.displayNone {
  display: none;
}

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
.grid-status-2{
  background-color: #ffd7cc!important;
  color: red!important;
  font-weight: bold!important;
}
.grid-status-1{
  background-color: #ffd7cc!important;
  color: rgb(202, 133, 4)!important;
  font-weight: bold!important;
}
.grid-status-0{
  background-color: #ccffcc!important;
  color: green!important;
  font-weight: bold!important;
}
.grid-tien{
  color: rgb(252, 151, 0)!important;
  font-weight: bold!important;
}

</style>
