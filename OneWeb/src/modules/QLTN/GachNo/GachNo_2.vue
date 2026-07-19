<template src="./GachNo.html"></template>
<style scoped src="./GachNo.scss"></style>

<script>
import Vue from 'vue'
import KyCuoc2 from '@/components/KyCuoc2'
import EventBus from '@/utils/eventBus'
import Calculator from '@/components/Calculator'
import GachNoLe from './components/GachNoLe'
import GachNoTheoDanhSach from './components/GachNoTheoDanhSach'
import DieuChinh from './components/DieuChinh'
import GachNoAPI from '../api/GachNoAPI'
import CommonsAPI from '../api/Commons'
import { Query } from '@syncfusion/ej2-data'
import moment from 'moment'
import TraCuuThongTinThueBao from './components/TraCuuThongTinThueBao'
import CacKhoanNo from './components/CacKhoanNo'
import PhieuGachNoTienMat from './components/PhieuGachNoTienMat'
import TimNhanVienThuCuoc from './components/TimNhanVienThuCuoc'
import PhieuHuy from './components/PhieuHuy'
import DanhSachThueBao from './components/DanhSachThueBao'
import ChenNo from './components/ChenNo'
import downloadexcel from "vue-json-excel";
import PopupXacNhanXoaPhieu from '../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'
import GachNoTheoFile from './components/GachNoTheoFile'
import DSPhimTat from './components/DSPhimTat'
import DieuChinhNoFile from './components/DieuChinhNoFile'
import ChonThanhToan from './components/ChonThanhToan'
import ThongTinViVNPTPay from './components/ThongTinViVNPTPay'
import TraCuuDSThanhToan from './components/TraCuuDSThanhToan'
import XLAPI from '../api/XuLyPhieuThanhToanAPI.js'


import {previewPrint} from "@/utils/util"
import {cloneArray} from "@/utils/util"
import { currency } from '@/filters/currency'

import XLSX from 'xlsx'


import {
    NHOM_HTTT,
    KHOANMUCTT_NO,
    Loai_NV,
    HTTT_NO,
    LOAI_DV,
    HinhThucThanhToan,
    KIEUNO,
    THUNO_KEY
  } from "@/const/enums"
import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

export default Vue.extend ({
  components : {
    appKyCuoc2: KyCuoc2,
    appCalculator: Calculator,
    downloadexcel,
    tabGachNoLe: GachNoLe,
    tabGachNoTheoDanhSach: GachNoTheoDanhSach,
    appTraCuuThongTinThueBao: TraCuuThongTinThueBao,
    appCacKhoanNo: CacKhoanNo,
    appPhieuGachNoTienMat: PhieuGachNoTienMat,
    appTimNhanVienThuCuoc: TimNhanVienThuCuoc,
    appPhieuHuy: PhieuHuy,
    appDanhSachThueBao: DanhSachThueBao,
    appChenNo: ChenNo,
    tabDieuChinh: DieuChinh,
    //appDoiNgayThanhToan: DoiNgayThanhToan,
    appGachNoTheoFile: GachNoTheoFile,
    appDSPhimTat: DSPhimTat,
    appDieuChinhNoFile: DieuChinhNoFile,
    PopupXacNhanXoaPhieu,
    ChonThanhToan,
    TraCuuDSThanhToan,
    ThongTinViVNPTPay
    //appPrintPreview: PrintPreview
  },
  created () {
    //EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$on('getTTTB', this.getTTTBHandler)
    EventBus.$on('acceptChuKyNo', this.acceptChuKyNoHandler)
    EventBus.$on('tabGachLeKieuGach', this.gachLeKieuGachHandler)
    //EventBus.$on('chonNVTC', this.chonNVTCHandler)
    //EventBus.$on('changeDSGachNoLe', this.updateDatasourceGachNoLe)
    EventBus.$on('changeDSDieuChinh', this.updateDatasourceDieuChinh)

    if(this.$route.query.tamthu !== undefined && this.$route.query.tamthu !== null && !isNaN(parseInt(this.$route.query.tamthu)))
    {
      let tamthu = parseInt(this.$route.query.tamthu)
      if(tamthu >= 0 && tamthu < 2)
        this.params.p_tamthu = tamthu
    }

    if(this.$route.query.canbang !== undefined && this.$route.query.canbang !== null && !isNaN(parseInt(this.$route.query.canbang)))
    {
      let canbang = parseInt(this.$route.query.canbang)
      if(canbang === 1)
        this.params.p_dieuchinh_canbang = canbang
    }

    if(this.$route.params.nhom  !== undefined
      && this.$route.params.nhom !== null
      && this.$route.params.nhom !== '')
    {
      try{
        this.params.p_nhom_httt = parseInt(this.$route.params.nhom)
      } catch(e) {
        this.$toast.error('Không xác định được chức năng. Vui lòng thử lại.')
        return false
      }
    }

    this.initUI()
  },
  destroyed () {
    //EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$off('getTTTB', this.getTTTBHandler)
    EventBus.$off('acceptChuKyNo', this.acceptChuKyNoHandler)
    EventBus.$off('tabGachLeKieuGach', this.gachLeKieuGachHandler)
    //EventBus.$off('chonNVTC', this.chonNVTCHandler)
    //EventBus.$off('changeDSGachNoLe', this.updateDatasourceGachNoLe)
    EventBus.$off('changeDSDieuChinh', this.updateDatasourceDieuChinh)
  },
  mounted (){

  },
  computed : {
    f_show_dieuchinh() {
      return this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
    },
    f_show_khoanmuc () {
      if(
        this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG
        || this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
        || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA
        || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA
        || this.params.p_nhom_httt === NHOM_HTTT.THANHLY_NO
      )
        return false
      return true
    },
    f_show_chiphi () {
      if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
        return true
      return false
    },
    f_show_chungtu () {
      if(this.params.p_nhom_httt === NHOM_HTTT.THANHLY_NO)
        return false
      return true
      /*
      if(
        this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY
        || this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
        || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TT
      )
      */
    },
    f_show_ngay_tt() {
      if(
        this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY
        || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TT
        || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC
      )
        return true
      return false
    },
    f_show_ngay_dc() {
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return true
      return false
    },
    f_enable_seri(){
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return false
      return true
    },
    f_enable_soquyen(){
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return false
      return true
    },
    f_enable_soseri(){
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return false
      return true
    },
    f_enable_tienno(){
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return false
      return true
    },
    f_enable_tiendieuchinh(){
      if(
        this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
      )
        return false
      return true
    },
    p_ngay_ttoan : {
      get() {
        return this.params.p_ngay_ttoan
      },
      set(value) {
        return this.params.p_ngay_ttoan = value
      }
    },
    p_ngay_nganhang : {
      get() {
        return this.params.p_ngay_nganhang
      },
      set(value) {
        return this.params.p_ngay_nganhang = value
      }
    },
    p_ngay_dchinh : {
      get() {
        return this.params.p_ngay_dchinh
      },
      set(value) {
        return this.params.p_ngay_dchinh = value
      }
    },
    p_thang_dchinh: {
      get() {
        return this.params.p_thang_dchinh
      },
      set(value) {
        return this.params.p_thang_dchinh = value
      }
    },
    p_chuky_dchinh: {
      get() {
        return this.params.p_chuky_dchinh
      },
      set(value) {
        return this.params.p_chuky_dchinh = value
      }
    },
    get_thungan () {
      if(this.params.p_thungan_options === null || this.params.p_thungan_options.length <= 0)
        return null
      let ret = this.params.p_thungan_options.filter(x => x.MA_NV === this.params.p_thungan)
      if(ret.length > 0)
        return ret[0]
      return null
    },
  },
  data() {
    return {
      // dynamic dialog config

      dlg:  {
        enableResize: true,
        allowDragging: true,
        visible: false,
        animationSettings: { effect: 'Zoom' },
        header: '',
        showCloseIcon: true,
        width: '95%',
        target: '.main-wrapper',
        popupComponent: null
      },

      config: {
        f_gach_dd_loaitt_mtt: true,
        f_gach_dd_loaitt_ckn: true,
        f_gach_dd_loaitt_matb: true,
        htttTemplate: function() {
          return {template: Vue.component("itemTemplate", {
            template: `<span title='{{data.HINHTHUC}}'><span class='name'>{{data.HINHTHUC}}</span><span class='ghichu'>{{data.GHICHU}}</span></span>`,
            data() {
              return {
                data: {}
              };
            }
          })};
        },
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          maxDate: moment().endOf('month').toDate(),
          minDate: moment().startOf('month').toDate()
        },
        fieldsDonVi : {text: 'TEN_DV', value: 'DONVI_ID' },
        fieldsLoaiKH : {text: 'TEN_LOAIKH', value: 'LOAIKH_ID' },
        fieldsDichVu: {text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        fieldsLoaihinhTB: {text: 'TEN_LOAIHINH', value: 'LOAITB_ID' },
        fieldsNVThuNgan: {text: 'TEN_NV', value: 'MA_NV' },
        fieldsLoaiTien: {text: 'TENLOAI', value: 'LOAITIEN_ID' },
        fieldsNganHang: {text: 'TEN_NH', value: 'NGANHANG_ID' },
        fieldsHTTT: {text: 'HINHTHUC', value: 'HTTT_ID' },
        fieldsSM_ACC: {value: 'MA_TB' },
        fieldsKhoanMuc: {text: 'TEN_KM', value: 'KHOANMUCTT_ID' },
        //fieldsLuotTT: {text: 'MA_TB', value: 'MA_TB' },
        isTienTraCalShow : false,
        isTongTienCalShow: false,
        isLuotTTCalShow: false,
        isChiPhiCalShow: false,
        isHidePopupHTTT: true,
        max_kyhoadon: moment(new Date()).subtract(1,'months').startOf('month').toDate()
      },
      params: {
        p_nhom_httt: NHOM_HTTT.GACHNO_TAIQUAY, // default là gạch nợ tại quầy
        p_loaigach: 2, // 2 gạch tháng nợ, 1: khoản mục, 3: dịch vụ, 4: gạch danh sách, 5: điều chỉnh,
        p_kieugach: 0, // 0: gạch hiện tại, 1: gạch lùi
        p_kieutra: 0, // 0: trả theo mã thanh toán, 1: trả theo thuê bao
        p_tamthu: 1,  // 1: có tạm thu, 0: không tạm thu
        p_ngay_cn: moment().format('DD/MM/YYYY'), //this.$auth.getNgayCapNhat(), // ngày người dùng được phép cập nhật
        p_ngay_hientai: null,
        p_gach_danhsach: false,
        p_kt : false,
        p_kytamthu: null,
        p_tamthu_hientai: false,
        p_in_khigachno: 0,  // in khi gạch nợ
        p_phieu_id: 0,
        p_phieu_id_bd : 0,
        p_phieu_id_kt : 0,
        p_intKieuthanhtoan: 1,  // 1: Thanh toán theo mã thanh toán,0: Thanh toán theo thuê bao
        p_dieuchinh_canbang: 0, // 0 các nhóm hình thức gạch bình thường.
        p_tab_kieugach: 1, // default là gạch lẻ, 2: gạch danh sách, 3 : điều chỉnh nợ
        p_lbl_thongtinthanhtoan: '',
        p_kycuoc: moment(new Date()).subtract(1,'months').endOf('month').format('YYYYMM') + '01',

        p_kyhoadon: null, //=strgstrLastestMMyyyy bên code VB//Định dạng MMyyyy
        p_khd: null, //Định dạng yyyyMM
        p_chuky: '', //Chu kỳ tính cước của 1 tháng : 01,02

        p_gachno_chuan: -1, // -1: disable, 0: MaTT, 1: Matt & CKN, 2: MaTB
        p_nganhang_neo: [],
        p_httt_id_neo: null,
        p_chk_ma_tt: '0',
        p_ma_tt: '',
        p_thanhtoan_id: 0,
        p_acc_somay: '',
        p_acc_somay_options: [],
        p_chk_ma_dd: '0',
        p_ma_dd: null,
        //p_luot_tt_options: [],
        p_luot_tt: 0,
        p_ten_tt: null,
        p_diachi_tt: null,
        p_diachi_baocuoc: null,
        p_maso_thue: null,
        p_taikhoan: null,
        p_daily: null,
        p_ghichu: null,
        p_chungtu: 0,

        p_matinh_thuho: -1,
        p_matinh_thuho_options: [],

        p_ngaybatdau_gach: null,
        p_ngayketthuc_gach: null,
        p_soseri_ds : 0,

        p_ngaygach_lui_thang : -1, //Số lượng ngày được phép gạch lùi sang tháng trước
        p_ngaygach_lui_thanght : -1, //Số lượng ngày được phép gạch lùi trong tháng hiện tại
        p_ngaygach_tien : -1, //Số lượng ngày được phép gạch tiến
        p_ngayxoa_lui : -1, //Số lượng ngày được phép xóa

        p_thungan_options: [],
        p_thungan: null, // default chọn nhân viên mặc định
        p_manv_tc : null,
        p_quaythu_id: null,
        p_donvi_quanly_id: null,
        p_donvi_quanly_ten: null,
        p_loaitien_options: [],
        p_loaitien: null,
        p_tonggach: 0,
        p_tongphieu: 0,
        p_seri: null,
        p_so_seri: 0,
        p_so_quyen: 0,
        p_tien_tra: 0,
        p_tongtien: 0,
        p_so_phieu: 0,
        p_tongtra: 0,
        p_ngay_ttoan: new Date(),
        p_ngay_nganhang: new Date(),
        p_nganhang_options: [],
        p_nganhang: null,
        p_ngay_dchinh: new Date(),
        p_thang_dchinh: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
        p_chuky_dchinh_options : [{CHUKY: '01'}],
        p_chuky_dchinh : '01',
        p_lydo_dchinh: null,
        p_httt_options: [],
        p_httt: null,
        p_httt_text: '',
        p_tinhthanh_options: [],
        p_tinhthanh: null,
        p_gachdauky: false,
        p_gachphatsinh: false,
        p_vsochukylchon: -1,
        p_chiphi: 0,
        p_tien_no: 0,
        p_tien_dchinh: 0,
        p_khoanmuc: null,
        p_khoanmuc_options: [],

        p_hoadon_insan: 1,
        p_quyen_dd: false,
        p_db_datcoc: 0,  // đặt cọc
        p_ds_tongno: [],

        p_soluong_tb: 0,
        p_strMathuebao : "",
        p_vcacchukyno: [],
        p_vcacmatb : [],
        p_vthangno : [],
        // dữ liệu tạm
        ds_thangno_tmp: [],
        ds_ctno_tam: [],
        ds_dichvu_tmp: [],
        ds_dieuchinh_tmp: [],
        ds_khoanmuc_tmp: [],
        p_vrkm_id: null,
        p_vtien_vikh: null,
        xoaPhieuParams: {
          tongTien: 0,
          tongPhieu: 1
        },
        timer: 0,
        p_request_id: 0,
        counterInterval: null,
        f_tinhtientra: true,  // ko tính lại, true tính lại
        p_BATBUOC_NHAP_CHUNGTU_GACHNO: 0,
        p_GACH_CHUYENKHOAN_SERI: 0, //0: Gạch nợ chuyển khoản không co seri,1: Gạch nợ chuyển khoản có seri.
        p_nvtc : {
          donvi_id: null,
          ma_nvtc: null
        },
        //Bổ sung mới 15/12/2021 (BSS)
        p_in_bbn_tt : false,
        p_ma_tiento_dv : "",
      },
      resultGachLeThangNo: [],
      resultGachLeKhoanMuc: [],
      resultGachLeDichVu: [],
      dsGachNoDanhSach: [],
      //dsGachNoDanhSachTotals: 0,
      dsChuKyNo: [],
      resultDieuChinh: [],
      dsErrors: [],
      //totalErrorItems: 0,
      headerPhieuGachNo: '',
      ds_thanhtoan: [],
      dsPhieuCanXoa: [],
      dsPhieuCanIn: [],
      showDlg: false,
      infoViVNPTPay: {
        availableBalance: 0,
        name: '',
        WalletId: null,
        phoneNumber: null
      }
    }
  },
  methods : {
    thungan () {
      if(this.params.p_thungan_options === null || this.params.p_thungan_options.length <= 0)
        return null
      let ret = this.params.p_thungan_options.filter(x => x.MA_NV === this.params.p_thungan)
      if(ret.length > 0)
        return ret[0]
      return null
    },
    f_show_tab: function(tab) {
      if(tab === 1) // tab gạch lẻ
      {
        return true
      } else if (tab === 2) // tab gạch danh sách
      {
        if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC)
          return false
        return true
      } else  // tab điều chỉnh
      {
        if(this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
          return true
        return false
      }
    },
    async getThamSo()
    {
      this.loading(true)
      const res = await Promise.all([
        this.getThamSoGachNo(),
        this.getQuyenGachDiDong(),
        this.getIN_BIENNHAN_TT()
      ])
      this.loading(false)
    },
    async initUI()
    {

      this.params.p_tab_kieugach = 1 // gạch lẻ
      this.params.p_loaigach = 2  // gạch tháng

      this.params.p_ngay_hientai = this.$auth.getNgayCapNhat()
      this.params.p_ngay_ttoan = moment(this.params.p_ngay_hientai, 'DD/MM/YYYY').toDate()
      this.params.p_ngay_nganhang = moment(this.params.p_ngay_hientai, 'DD/MM/YYYY').toDate()


      await this.getThamSo()

      if(this.params.p_quyen_dd)
      {
        await this.getTSGachNoChuan()
      }
      
      switch (this.params.p_nhom_httt)
      {
        case NHOM_HTTT.GACHNO_TAIQUAY:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán gạch nợ tiền mặt"
          break;
        case NHOM_HTTT.GACHNO_NGANHANG:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán gạch nợ chuyển khoản"
          break;
        case NHOM_HTTT.GACHNO_DAILY:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán gạch nợ đại lý"
          break;
        case NHOM_HTTT.DIEU_CHINH_NO:
          this.params.p_tab_kieugach = 3
          this.params.p_loaigach = 5  // gạch điều  chỉnh
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán điều chỉnh nợ"
          break;
        case NHOM_HTTT.THOAITRA:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán thoái trả"
          break;
        case NHOM_HTTT.GACHNO_TT:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán trực tiếp"
          break;
        case NHOM_HTTT.GACHNO_DATCOC:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán"
          this.params.p_db_datcoc = 1
          //this.$refs.p_so_seri.style.color= '#0000ff';
          break;
        case NHOM_HTTT.THANHLY_NO:
          //this.headerPhieuGachNo = "Danh sách các phiếu thanh toán trực tiếp"
          break;
        case NHOM_HTTT.HOANTRA:
          this.headerPhieuGachNo = "Danh sách các phiếu thanh toán hoàn trả"
          break;
      }

      // init cbo hình thức thanh toán
      if(this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_TT)
      {
        this.getDSHTTT(this.params.p_nhom_httt, 0)
      } else {

      }
      // init Ngân hàng
      if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG
        || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA
        || this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO
        || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA
        )
      {
        this.getDSNganHang()
      }
      // init Khoản mục

      if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC)
      {
        this.getDSKhoanMuc()
      }

      // init Thu ngân
      this.getDSThuNgan()

      // init Seri, số quyển, số seri
      this.loadThongTinSeri()
    },

    async getMapID(postData)
    {
      try
      {
        const res = await CommonsAPI.getMapID(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' 
              && data.data !== undefined 
              && data.data.length > 0 ? data.data[0].KETQUA : null
      }catch(err)
      {
        return null
      }
      
    },

    getQuyenGachDiDong()
    {
      /*
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
      if(this.$auth.getMaCCBS() !== undefined && this.$auth.getMaCCBS() !== null)
        this.params.p_quyen_dd = true
      else
        this.params.p_quyen_dd = false
    },
    async getTSGachNoChuan()
    {
      await CommonsAPI.getDSThamSoMacDinhAdmin(this.axios, 'GACHNO_CHUAN')
      .then((res) => {
        if(res.data.error_code === 'BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
          {
            //console.log(res)
            let ts = parseInt(res.data.data[0].GIATRI)

            if(ts !== 2)
            {
              // rdioCKb.Properties.Items[1].Enabled = false;
              this.config.f_gach_dd_loaitt_ckn = false
            }
            if(ts === -1)
            {
              this.$toast.error('Không lấy được phương thức gạch di động')
              return
            }

            this.params.p_gachno_chuan = ts
            
          }
        else
        {
          this.$toast.error('Không lấy được phương thức gạch di động')
        }
      })
      .catch(error => {
        this.$toast.error(error.data.message_detail)
        this.$toast.error('Không lấy được phương thức gạch di động')
      })
      .finally(() => {})
    },
    async getIN_BIENNHAN_TT()
    {
      await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'IN_BIENNHAN_TT'})
      .then((res) => {
        if(res.data.error_code === 'BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
          {
            
            let ts = parseInt(res.data.data[0].TEN_TS)
            if(ts === 1 && (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA))
            {
              this.params.p_in_bbn_tt = true
              this.getMaTienToDonVi()
            }
              
          }
      })
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {})
    },

    async getThamSoViVNPTPay()
    {
      try
      {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'SUDUNG_VI_VNPTPAY_V2'})
        const data = await res.data

        return data.error_code === 'BSS-00000000' && res.data.data !== undefined ? res.data.data : null
      }catch(error)
      {
        return null
      }
      
    },

    async getMaTienToDonVi()
    {
      /*
      let postData = {
        "tenTruong":"TIENTO",
        "schema":"admin",
        "tenBang":"donvi",
        "dieuKien":"donvi_id = " + this.$auth.getDonViID()
      }
      */
      let postData = {
        "tenId": "donvi_id",
        "ma": this.$auth.getDonViID()
      }
      await CommonsAPI.getMapID2(this.axios, postData)
      .then((res) => {
        if(res.data.error_code === 'BSS-00000000'
          && res.data.data !== undefined
          && res.data.data.length > 0)
          {
            console.log(res)
            /*
            let ma_tiento_dv = res.data.data[0].KETQUA + '/BN1'
            this.params.p_seri = ma_tiento_dv
             /*
             ma_tiento_dv = ckdata.MAP_ID("tiento", DatabaseConstants.DB1 + ".donvi", "where donvi_id=" + tt_nd.donvi_id + "") + "/BN1";
            if (ma_tiento_dv != "-1")
            {
                this.params.p_seri = ma_tiento_dv;
            }
              */             
          }
      })
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {})
    },

    async loadThongTinSeriTheoMaTT(p_ma_tt)
    {
      /*
      let postData1 = {
        "tenTruong":"SERI",
        "schema":"QLTN",
        "tenBang":"hoadon",
        "dieuKien":"ma_tt = '" + p_ma_tt + "'"
      }
      let postData2 = {
        "tenTruong":"SO_THUNG",
        "schema":"QLTN",
        "tenBang":"hoadon",
        "dieuKien":"ma_tt = '" + p_ma_tt + "'"
      }
      let postData3 = {
        "tenTruong":"SO_SERI",
        "schema":"QLTN",
        "tenBang":"hoadon",
        "dieuKien":"ma_tt = '" + p_ma_tt + "'"
      }
      const res = await Promise.all([
        this.getMapID(postData1),
        this.getMapID(postData2),
        this.getMapID(postData3),
      ])

      console.log(res)
      */
      try
      {
        const res = await GachNoAPI.getThongTinSeriMaTT(this.axios, p_ma_tt)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data[0] : null
      }catch(err)
      {
        return null
      }
    },

    loadThongTinSeri(){
      let s = this.getDataFromStorage(THUNO_KEY.KEY_SERI)
      this.params.p_seri = s === null ? '' : s
      s = this.getDataFromStorage(THUNO_KEY.KEY_SOQUYEN)
      this.params.p_so_quyen = s === null ? 0 : s
      s = this.getDataFromStorage(THUNO_KEY.KEY_SOSERI)
      this.params.p_so_seri = s === null ? 1 : parseInt(s)
      //console.log(this.params.p_seri)
    },
    setThongTinSeri(data)
    {
      this.setDataToStorage(THUNO_KEY.KEY_SERI, data.p_seri)
      this.setDataToStorage(THUNO_KEY.KEY_SOQUYEN, data.p_so_quyen)
      this.setDataToStorage(THUNO_KEY.KEY_SOSERI, data.p_so_seri)
    },
    // get thông tin thuê bao
    async getThongTinThueBao()
    {
      this.params.ds_dichvu_tmp = []
      this.params.ds_thangno_tmp = []
      this.params.ds_khoanmuc_tmp = []
      this.params.p_phieu_id = 0
      this.params.p_intKieuthanhtoan = 1
      this.params.p_kieutra = 1 // trả theo thuê bao


      this.params.p_vcacmatb = []
      this.params.p_vthangno = []
      this.params.p_vcacchukyno = []

      if(this.params.p_acc_somay === null || ('' + this.params.p_acc_somay).trim() === '')
      {
        this.$toast.error('Bạn chưa nhập vào số máy/ account.')
        return
      }
      this.params.p_phieu_id = 0
      this.params.p_intKieuthanhtoan = 0
      let strmatb = ""
      strmatb = this.params.p_acc_somay.trim()
      if (this.params.p_loaitien === null)
      {
        this.$toast.error("Không có dữ liệu về loại tiền. Bạn hãy kiểm tra lại!")
        return;
      }
      this.params.p_acc_somay = this.params.p_acc_somay.toLowerCase().trim()
      this.params.p_gachdauky = false
      this.params.p_gachphatsinh = false
      this.params.p_vsochukylchon = -1

      this.params.p_strMathuebao = this.params.p_acc_somay

      if (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_TT)
      {
          this.params.p_matinh_thuho = "";
          /*
          if (cboTinhThanh.EditValue == null || cboTinhThanh.EditValue == DBNull.Value)
          {
              Message_Box.ShowWarning("Bạn phải chọn mã tỉnh để tìm kiếm");
              return;
          }

          if (cboTinhThanh.Text == "")
          {
              Message_Box.ShowWarning("Bạn phải chọn mã tỉnh để tìm kiếm");
              return;
          }
          matinh_thuho = cboTinhThanh.EditValue.ToString();
          if (matinh_thuho == matinh_hientai)
          {
              Message_Box.ShowWarning("Bạn không thể thu hộ tại tỉnh " + cboTinhThanh.Text + " của bạn");
              return;
          }
        */
      }
      let response
      if (this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_DATCOC)
      {
        this.loading(true)
        response = await this.getPayment_Info_ForPay(this.params.p_acc_somay);
        this.loading(false)
      }
      else
      {
        this.loading(true)
        response = await this.getPayment_Info_ForPay2(this.params.p_acc_somay);
        //response = await this.getPayment_Info_ForPay(this.params.p_acc_somay);
        this.loading(false)
      }

      if(response.length > 1) // nếu có nhiều ma thanh toán
      {
        this.ds_thanhtoan = response
        this.$refs.refChonThanhToan.openDialog()
        return
      } else if(response.length === 0) // nếu ko tìm thấy mã thanh toán
      {
        const res = await this.getThongTinTTTheoDBHienTai()
        if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined)
          {
            if(res.data.data.length <= 0)
            {
              this.$toast.error('Không tìm thấy thông tin thanh toán')
              return false
            } else {
              this.showThongTinTT(res.data.data, 1)
              if(this.params.p_loaigach === 4) // gạch theo dnah sách
              {
                //Nếu là gạch chuyển khoản thì chỉ lấy những mã thanh toán đăng ký hình thức ủy nhiệm thu, chi.
                //Nếu có mã in ghép thì lấy ra tất cả các mã thanh toán trong mã in ghép đó.
                let v_kieugach = "";
                //region Kiểm tra ngày thanh toán với ngày hiện tại
                let date2 = this.params.p_ngay_ttoan
                let date1 = moment(this.params.p_ngay_cn, 'DD/MM/YYYY').toDate()
                let v_kyhoadon_lui = moment(this.params.p_ngay_tt).subtract(1, 'months').format('MMYYYY') //dtpNgayTT.DateTime.AddMonths(-1).Month.ToString().PadLeft(2, '0') + dtpNgayTT.DateTime.AddMonths(-1).Year.ToString();
                var k_ = date2.diff(date1, 'days')
                if (moment(date2, 'YYYY') === moment(date1, 'YYYY')
                  && moment(date2, 'MM') === moment(date1, 'MM'))
                {
                  if (k_ > 0)//Nếu gạch tiến
                  {
                      //Không cho phép gạch tiến
                      this.$toast.error("Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!")
                      return false
                  }
                  if (k_ < 0) //Nếu gạch lùi
                  {
                      if (this.params.p_ngaygach_lui_thanght > -1)//Tham số ngày gạch lùi tháng hiện tại
                      {
                          if (this.params.p_ngaygach_lui_thanght < Math.abs(k_))
                          {
                            this.$toast.error("Bạn không được phép lùi " + Math.abs(k_) + " ngày trong tháng " + moment(this.params.p_ngay_hientai).format('MM') + " ")
                              return;
                          }
                      }

                  }
                  v_kieugach = "0"
                } else //Nếu khác tháng hoặc năm
                {
                  if (this.params.p_ngaygach_lui_thang === -1)
                  {
                    this.$toast.error("Ngày cho phép gạch lùi chưa được thiết lập ko thể gạch lùi");
                    return;
                  }
                  if (k_ > 0) //Nếu gạch tiến
                  {
                      //Không cho phép gạch tiến
                    this.$toast.error("Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!");
                    return;
                  }
                  if (k_ < 0)//Nếu gạch lùi
                  {
                    /*
                    let maxDayOfMonth = new DateTime(date1.Year, date1.Month, ngaygach_lui_thang);// Lấy ra ngày cho phép gạch đến của tháng
                    var thangtt = maxDayOfMonth.AddMonths(-1);
                    if (date1 > maxDayOfMonth)
                    {
                        Message_Box.ShowTB("Bạn không được phép gạch lùi. Ngày hiện tại không thuộc khoảng thời gian cho phép gạch lùi");
                        return;
                    }
                    // nếu phù hợp thì kiểm tra xem ngày tt có thuộc tháng hiện -1 ko
                    var firstMonth = new DateTime(thangtt.Year, thangtt.Month, 1);
                    var lastMonth = new DateTime(thangtt.Year, thangtt.Month, DateTime.DaysInMonth(thangtt.Year, thangtt.Month));
                    if (date2 < firstMonth || date2 > lastMonth)
                    {

                        Message_Box.ShowTB("Bạn không được phép gạch với ngày thanh toán khác tháng " + thangtt.Month);
                        return;
                    }
                    */
                  }// end of gạch lùi
                  v_kieugach = "1"
                }
                this.loadGachTheoDanhSach(res.data.data)
              } else
              {
                if(this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_DATCOC)
                  this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, this.params.p_kieutra)
                else
                  this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, 0)
              }

            }

          }
      } else // nếu tìm thấy 1 mã thanh toán
      {
        this.showThongTinTT(response, 1)
        if(this.params.p_loaigach === 4) // gạch theo danh sách
        {
          this.loadGachTheoDanhSach(response)
        } else
        {
          if(this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_DATCOC)
            this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, this.params.p_kieutra)
          else
            this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, 0)
        }
      }
    },
    // get thông tin thanh toán khi nhấn enter vào ô mã thanh toán
    async getThongTinTT()
    {
      this.params.ds_dichvu_tmp = []
      this.params.ds_thangno_tmp = []
      this.params.ds_khoanmuc_tmp = []
      this.params.p_phieu_id = 0
      this.params.p_intKieuthanhtoan = 1
      this.params.p_acc_somay_options = []
      this.params.p_acc_somay = ''
      this.params.p_kieutra = 0

      /*intKieuthanhtoan = 1;
      string tk = TimKiemMaVach();
      if (tk != "1")
      {
          Message_Box.ShowWarning(tk);
          return;
      }
      string strmatt = "";
      strmatt = txtMaTT.Text.Trim();
      lblGach.Text = "";
      */
      let tk = await this.TimKiemMaVach()
      if(tk !== 1){
        return
      }

      this.params.p_vcacmatb = []
      this.params.p_vthangno = []
      this.params.p_vcacchukyno = []

      if(this.params.p_ma_tt === null
        || this.params.p_ma_tt.trim() === '')
      {
        this.$toast.error('Bạn phải chọn mã thanh toán')
        return false;
      }
      this.params.p_ma_tt = this.params.p_ma_tt.trim()
      if(this.params.p_nhom_httt == NHOM_HTTT.GACHNO_TT)
      {
        if (this.params.p_matinh_thuho === null)
        {
          this.$toast.error("Bạn phải chọn mã tỉnh để tìm kiếm")
          return
        }

        if(this.params.p_matinh_thuho === this.$auth.getTinhThanhId()){
          let tinhthuho = this.getTinhThuHo()
          this.$toast.error("Bạn không thể thu hộ tại tỉnh " + tinhthuho.TEN_TINHTHANH + " của bạn")
          return
        }
      }

      if(this.params.p_loaitien === null){
        this.$toast.error("Không có dữ liệu về loại tiền. Bạn hãy kiểm tra lại!")
        return false
      }

      this.params.p_lbl_thongtinthanhtoan = "... Đang tiến hành gạch nợ theo mã thanh toán"
      this.params.p_gachdauky = false;
      this.params.p_gachphatsinh = false;
      this.params.vsochukylchon = -1;
      //Tìm kiếm thông tin thanh toán
      if (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_TT)
      {
        // p_tcthuno = CrossRegionSupport.ActivateType<TraCuuThuNoFacade>(matinh_thuho);
      }
      let ret = this.params.p_httt_options.filter(x => x.HTTT_ID === this.params.p_httt)
      this.params.p_lbl_thongtinthanhtoan = "... Đang tiến hành gạch nợ theo mã thanh toán - HTTT " + ret.length > 0 ? ret[0].HTTT : ''

      this.params.p_vrkm_id = 0;
      this.params.p_vtien_vikh = 0;

      this.loading(true)

      const response = await this.getPayment_Info_ForPay(this.params.p_ma_tt)
      this.params.p_so_seri = this.params.p_so_seri + 1

      this.loading(false)

      if(response.length > 1) // nếu có nhiều ma thanh toán
      {
        this.ds_thanhtoan = response
        this.$refs.refChonThanhToan.openDialog()
      } else if(response.length === 0) // nếu ko tìm thấy mã thanh toán
      {
        const res = await this.getThongTinTTTheoDBHienTai()
        if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined)
          {
            if(res.data.data.length <= 0)
            {
              this.$toast.error('Không tìm thấy thông tin thanh toán')
              return false
            } else {
              this.showThongTinTT(res.data.data, 1)
              if(this.params.p_loaigach === 4) // gạch theo dnah sách
              {
                //Nếu là gạch chuyển khoản thì chỉ lấy những mã thanh toán đăng ký hình thức ủy nhiệm thu, chi.
                //Nếu có mã in ghép thì lấy ra tất cả các mã thanh toán trong mã in ghép đó.
                let v_kieugach = "";
                //region Kiểm tra ngày thanh toán với ngày hiện tại
                let date2 = this.params.p_ngay_ttoan
                let date1 = moment(this.params.p_ngay_cn, 'DD/MM/YYYY').toDate()
                let v_kyhoadon_lui = moment(this.params.p_ngay_tt).subtract(1, 'months').format('MMYYYY') //dtpNgayTT.DateTime.AddMonths(-1).Month.ToString().PadLeft(2, '0') + dtpNgayTT.DateTime.AddMonths(-1).Year.ToString();
                var k_ = date2.diff(date1, 'days')
                if (moment(date2, 'YYYY') === moment(date1, 'YYYY')
                  && moment(date2, 'MM') === moment(date1, 'MM'))
                {
                  if (k_ > 0)//Nếu gạch tiến
                  {
                      //Không cho phép gạch tiến
                      this.$toast.error("Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!")
                      return false
                  }
                  if (k_ < 0) //Nếu gạch lùi
                  {
                      if (this.params.p_ngaygach_lui_thanght > -1)//Tham số ngày gạch lùi tháng hiện tại
                      {
                          if (this.params.p_ngaygach_lui_thanght < Math.abs(k_))
                          {
                            this.$toast.error("Bạn không được phép lùi " + Math.abs(k_) + " ngày trong tháng " + moment(this.params.p_ngay_hientai).format('MM') + " ")
                              return;
                          }
                      }

                  }
                  v_kieugach = "0"
                } else //Nếu khác tháng hoặc năm
                {
                  if (this.params.p_ngaygach_lui_thang === -1)
                  {
                    this.$toast.error("Ngày cho phép gạch lùi chưa được thiết lập ko thể gạch lùi");
                    return;
                  }
                  if (k_ > 0) //Nếu gạch tiến
                  {
                      //Không cho phép gạch tiến
                    this.$toast.error("Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!");
                    return;
                  }
                  if (k_ < 0)//Nếu gạch lùi
                  {
                    /*
                    let maxDayOfMonth = new DateTime(date1.Year, date1.Month, ngaygach_lui_thang);// Lấy ra ngày cho phép gạch đến của tháng
                    var thangtt = maxDayOfMonth.AddMonths(-1);
                    if (date1 > maxDayOfMonth)
                    {
                        Message_Box.ShowTB("Bạn không được phép gạch lùi. Ngày hiện tại không thuộc khoảng thời gian cho phép gạch lùi");
                        return;
                    }
                    // nếu phù hợp thì kiểm tra xem ngày tt có thuộc tháng hiện -1 ko
                    var firstMonth = new DateTime(thangtt.Year, thangtt.Month, 1);
                    var lastMonth = new DateTime(thangtt.Year, thangtt.Month, DateTime.DaysInMonth(thangtt.Year, thangtt.Month));
                    if (date2 < firstMonth || date2 > lastMonth)
                    {

                        Message_Box.ShowTB("Bạn không được phép gạch với ngày thanh toán khác tháng " + thangtt.Month);
                        return;
                    }
                    */
                  }// end of gạch lùi
                  v_kieugach = "1"
                }
                this.loadGachTheoDanhSach(res.data.data)
              } else
              {
                if (this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_DATCOC)
                  this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, this.params.p_kieutra)
                else
                  this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, 0)
              }

            }

          }
      } else // nếu tìm thấy 1 mã thanh toán
      {
        console.log(response)
        this.showThongTinTT(response, 1)
        
        if(this.params.p_loaigach === 4) // gạch theo danh sách
        {
          this.loadGachTheoDanhSach(response)
        } else
        {
          if (this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_DATCOC)
            this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, this.params.p_kieutra)
          else
            this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, 0)
        }
        
      }



      //
      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {

      } else {

      }

    },

    async getTongNoTheoKyHoaDon(pKyCuoc, pMaTT, pKieu) {

      this.loading(true)
      let postData = {
        pKyCuoc: pKyCuoc,
        pMaTT: pMaTT,
        pMaTB: pKieu === 1 ? this.params.p_acc_somay : null
      }
      await GachNoAPI.getTongNoTheoKyHoaDon(this.axios, postData)
        .then((res) => {  
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            res.data.data.forEach(item => {
              if(this.params.p_acc_somay_options.length <= 0 || !this.params.p_acc_somay_options.includes(item.MA_TB))
                this.params.p_acc_somay_options.push(item.MA_TB)
            });
            if(res.data.data.length > 1)
            {
              this.params.p_ds_tongno = res.data.data
              this.$refs.refCacKhoanNo.openDialog()
            } else
            {
              this.params.p_vcacmatb = []
              this.params.vcacmatb = []

              if (this.params.p_kieutra === 1)  // trả theo thuê bao
              {
                this.params.p_vcacmatb.push(res.data.data[0].MA_TB)
                this.params.vcacmatb.push(res.data.data[0].CHUKYNO)
              }
              this.loadChiTietNo()
            }
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
        
      
    },
    async showThongTinTT(info, kieu){
      //Kieu = 1: daset truyền vào chỉ có 1 row
      //Kieu = 2: daset truyền vào có từ 2 row trở lên

      let vkhachhang_id = "0"
      let vdaily = "0"
      let nv_quanly = " - NV Quản lý "
      //this.params.p_ma_tt = info[0].MA_TT


      if(info.length > 0) {
        this.params.p_soluong_tb = 1;
        if (this.params.p_kieutra === 0) //Nếu trả theo mã thanh toán thì lấy số lượng thuê bao sang fom bên kia
          this.params.p_soluong_tb = this.params.p_acc_somay_options.length

        if(kieu === 1)
        {
          this.params.p_thanhtoan_id = parseInt(info[0].THANHTOAN_ID)
          this.params.p_ma_tt = info[0].MA_TT

          vkhachhang_id = info[0].KHACHHANG_ID
          this.params.p_ten_tt = info[0].TEN_TT
          this.params.p_diachi_tt = info[0].DIACHI_TT
          this.params.p_diachi_baocuoc = info[0].DIACHI_BC
          this.params.p_maso_thue = info[0].MST
          this.params.p_taikhoan = info[0].STK
          this.params.p_manv_tc = info[0].MANV_TC
          this.params.p_ma_dd = info[0].MATB_DD

          if(this.params.p_loaigach === 2)
            this.getDSTBTheoMaTT()

          let ret = this.params.p_httt_options.filter(x => x.HTTT_ID === this.params.p_httt)
          this.params.p_lbl_thongtinthanhtoan = "... Đang tiến hành gạch nợ theo mã thanh toán - HTTT " + ret.length > 0 ? ret[0].HTTT : ''

          //Lấy seri theo mã thanh toán nếu là gạch nợ đại lý
          if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
          {
            if (this.params.p_hoadon_insan === 1) //Nếu định nghĩa lấy dữ liệu từ dataport gửi
            {
              const res = await this.loadThongTinSeriTheoMaTT(this.params.p_ma_tt)
              if(res !== null)
              {
                this.params.p_seri = res.SERI
                this.params.p_so_quyen = res.SO_THUNG
                this.params.p_so_seri = res.SO_SERI
              }
                
            }
            else
            {
              this.loadThongTinSeri()
            }
          }
          vdaily = parseInt(info[0].DAILY_ID)  // Convert.ToInt32(ds.Tables[0].Rows[0]["daily_id"].ToString().Trim());
          let v_ten_nvql = ''
          let v_so_dt = ''
          let res = await this.getDanhSachNhanVienQLTheoDaiLy(vdaily)
          if(res.data.error_code === 'BSS-00000000' && res.data.data !== undefined && res.data.data.length > 0)
          {
            v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
            v_so_dt = res.data.data[0].SO_DT === null ? '' : res.data.data[0].SO_DT
          }
          //console.log(info)
          this.params.p_daily = "Tuyến thu: " + info[0].MA_TUYENTHU + " - Đại lý: " + info[0].MANV_TC + nv_quanly + ': ' + v_ten_nvql + " - " + v_so_dt;
          this.params.p_nvtc.donvi_id = info[0].DONVI_ID
          this.params.p_nvtc.ma_nvtc = info[0].MANV_TC
        } else {  // else kieu = 2


          for(let i = 0; i < info.length; i++)
          {
            //if (ds.Tables[0].Rows[i]["ma_tt"].ToString().Trim().ToUpper() == ma_tt.ToUpper())
            if(info[i].MA_TT === this.params.p_ma_tt)
            {
              this.params.p_thanhtoan_id = parseInt(info[i].THANHTOAN_ID)
              vkhachhang_id = info[i].KHACHHANG_ID
              this.params.p_ten_tt = info[i].TEN_TT
              this.params.p_diachi_tt = info[i].DIACHI_TT
              this.params.p_diachi_baocuoc = info[i].DIACHI_BC
              this.params.p_maso_thue = info[i].MST
              this.params.p_taikhoan = info[i].STK
              this.params.p_manv_tc = info[i].MANV_TC
              this.params.p_ma_dd = info[i].MATB_DD

              this.getDSTBTheoMaTT()


              vdaily = parseInt(info[i].DAILY_ID)  // Convert.ToInt32(ds.Tables[0].Rows[0]["daily_id"].ToString().Trim());
              let v_ten_nvql = ''
              let v_so_dt = ''
              let res = await this.getDanhSachNhanVienQLTheoDaiLy(vdaily)
              /*
                .then(
                  (res) => {
                    if(res.data.error_code === 'BSS-00000000'
                      && res.data.data !== undefined
                      && res.data.data.length > 0)
                    {
                      v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
                      v_so_dt = res.data.data[0].SO_DT
                    }
                    console.log(info)
                    this.params.p_daily = "Tuyến thu: " + info[0].MA_TUYENTHU + " - Đại lý: " + info[0].MANV_TC + " - " + '' + v_ten_nvql + " - " + v_so_dt;
                  }
                )
              */
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0)
              {
                v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
                v_so_dt = res.data.data[0].SO_DT === null ? '' : res.data.data[0].SO_DT
              }
              //console.log(info)
              this.params.p_daily = "Tuyến thu: " + info[i].MA_TUYENTHU + " - Đại lý: " + info[i].MANV_TC + nv_quanly + ': ' + v_ten_nvql + " - " + v_so_dt;
              this.params.p_nvtc.donvi_id = info[0].DONVI_ID
              this.params.p_nvtc.ma_nvtc = info[0].MANV_TC


              //Lấy seri theo mã thanh toán nếu là gạch nợ đại lý
              if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
              {
                  if (this.params.p_hoadon_insan === 1) //Nếu định nghĩa lấy dữ liệu từ dataport gửi
                  {
                    const res = await this.loadThongTinSeriTheoMaTT(this.params.p_ma_tt)
                    if(res !== null)
                    {
                      this.params.p_seri = res.SERI
                      this.params.p_so_quyen = res.SO_THUNG
                      this.params.p_so_seri = res.SO_SERI
                    }
                  }
                  else
                  {
                    this.loadThongTinSeri()
                  }
              }
            }
          }

        } // end of kieu === 1
        if(this.params.p_nhom_httt === NHOM_HTTT.DIEUCHINH_NO)
        {

        }
      } else {
        this.Clear()
      }

    },
    async loadDSThanhToanTheoMIG(postData)
    {
      try{
        const res = await GachNoAPI.getDSThanhToanMIG(this.axios, postData)
        const data = await res.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : []
      }catch(error){
        if(error.data !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error.data)
        return []
      }finally{} 
    },
    async loadGachTheoDanhSach(ds){
      if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
      {
        this.params.p_gach_danhsach = false
        this.dsGachNoDanhSach = []

        let promises = []

        let dsGachNo = []

        ds.forEach( (value, index) => {
          let vmig_id = value.MIG_ID === null ? '' : value.MIG_ID
          if(vmig_id !== '')
          {
            let vhttt_id = '' + value.HTTT_ID
            if(vhttt_id !== '')
            {
              vhttt_id = parseInt(vhttt_id)
              if(vhttt_id === HinhThucThanhToan.UYNHIEM_CHI || vhttt_id === HinhThucThanhToan.UYNHIEM_THU)
              {
                let postData = {
                  kyCuoc: this.params.p_kycuoc,
                  migId: vmig_id,
                  htttId: vhttt_id
                }

                promises.push(this.loadDSThanhToanTheoMIG(postData))

              }
            }
          }
        })

        this.loading(true)

        const res = await Promise.all(promises)

        this.loading(false)
        let so_seri = parseInt(this.params.p_so_seri)

        if(res.length > 0)
          res.forEach(item => {
            //console.log(item)
            item.forEach(x => {

              so_seri = so_seri + 1

              x.TIENTRA = x.TONGNO
              x.CONLAI = 0
              x.TAMTHU = 0
              x.SERI = this.params.p_seri
              x.QUYEN = this.params.p_so_quyen,
              x.SO_SERI = so_seri
              x._CHECK = x.TONGNO !== 0 ? true : false
              dsGachNo.push(x)
            })
          })

        this.params.p_so_seri = so_seri

        this.setThongTinSeri({
          p_seri: this.params.p_seri, 
          p_so_quyen: this.params.p_so_quyen,
          p_so_seri: this.params.p_so_seri
        })

        this.dsGachNoDanhSach = cloneArray(dsGachNo)
        this.tinhTongTienTra()
      } else
      {
        //let found = this.dsGachNoDanhSach.filter(function(el){return el.MA_TT === ds[0].MA_TT})
        let ret = this.dsGachNoDanhSach.filter(x => x.MA_TT === this.params.p_ma_tt)
        //console.log(ret)
        if(ret !== null && ret.length > 0)
        {
          this.$toast.error("Đã tồn tại mã thanh toán " + this.params.p_ma_tt + ". Bạn không thể thêm vào danh sách")
          return false
        }
        let so_seri = parseInt(this.params.p_so_seri)
        let arr = []
        ds.forEach( (item, index) => {
          so_seri = so_seri + index + 1
          let data = {
            THANHTOAN_ID: item.THANHTOAN_ID,
            MA_TT: item.MA_TT,
            MATB_DD: item.MATB_DD,
            TEN_TT: item.TEN_TT,
            TONGNO: item.TONGNO,
            TIENTRA: item.TONGNO,
            TAMTHU: item.TAMTHU,
            CONLAI: 0,
            SERI: this.params.p_seri,
            QUYEN: this.params.p_so_quyen,
            SO_SERI: so_seri,
            CHUNG_TU: this.params.p_chungtu,
            GHI_CHU: this.params.p_ghichu,
            _CHECK: item.TONGNO !== 0 ? true : false
          }
          arr.push(data)
          this.params.p_so_seri = so_seri
        })
        this.dsGachNoDanhSach = [...this.dsGachNoDanhSach, ...arr]
        this.tinhTongTienTra()

      } //
    },
    // popup form tìm kiếm thông tin thanh toán
    popupTimThongTinTT: function(){
      // this.$toast.error('Chức năng chưa được phát triển')
      if( (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_NGANHANG || this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO) && this.params.p_tab_kieugach === 2) // gạch danh sách

        this.$refs.refTraCuuDSThanhToan.openDialog()
      else
        this.$refs.refTraCuuTTTB.openDialog()
    },
    popupDSThueBao: function()
    {
      this.$refs.refDanhSachThueBao.openDialog()
    },
    // onchange khi chọn danh sách Acc/Số máy
    onChangeAccSoMay: function(args){

    },
    popupDSPhieuThanhToan: function(){
      this.$refs.refPhieuGachNoTienMat.openDialog()
    },

    popupDSPhieuThanhToanTheoNgay: function(){
      //this.$refs.refPhieuThanhToanTheoNgay.openDialog()
      //this.$router.replace({ name: "TraCuuPhieuTraTheoNgay", params: {} })
      //this.$router.push('TraCuuPhieuTraTheoNgay')
      let routeData = this.$router.resolve({name: 'TraCuuPhieuTraTheoNgay'/*, query: {data: ""}*/});
      window.open(routeData.href, '_blank');
    },
    popupTimNVThuCuoc: function(){
      this.$refs.refTimNVThuCuoc.openDialog()
      //this.$toast.error('Chức năng chưa được phát triển')
    },
    popupDSPhieuHuy: function(){
      this.$refs.refPhieuHuy.openDialog()
    },
    popupDSThueBao: function(){
      this.$refs.refDanhSachThueBao.openDialog()
    },
    popupChenNo: function(){
      this.$refs.refChenNo.openDialog()
    },
    openDSPhimTat: function(){
      this.$refs.refDSPhimTat.openDialog()
    },
    popupDanhSachPhieuThu: function(){
      this.$refs.refDanhSachPhieuThu.openDialog()
    },
    /* LOAD DANH MỤC */
    getThongTinChuKyNo: function(){

      if(this.params.p_kycuoc === null)
      {
        this.params.p_ngaybatdau_gach = null
        this.params.p_ngayketthuc_gach = null
        return
      }
      try{
          CommonsAPI.getThongTinChuKyNo(this.axios, this.params.p_kycuoc).then(
            (response) => {
              //console.log(response)
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0)
              {
                this.params.p_ngaybatdau_gach = response.data.data[0].NGAY_BD
                this.params.p_ngayketthuc_gach = response.data.data[0].NGAY_KT
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          //this.$toast.error(error)
          //nsole.log(error)
      }
    },
    getThamSoGachNo: function(){

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
    },
    getDonViQuanLy: function(){

      try{
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + "," + LOAI_DV.DONVIQL_TT_BUUDIEN
        }
          GachNoAPI.getDonViQuanLy(this.axios, postData).then(
            (response) => {
              ///console.log(response)
              if(response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined)
              {
                this.params.p_donvi_quanly_id = response.data.data[0].DONVI_ID
                this.params.p_donvi_quanly_ten = response.data.data[0].TEN_DV

                if(this.params.p_donvi_quanly_id === '0'
                  && this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO)
                  {
                    DialogUtility.alert({
                      title: 'Thông báo',
                      content: "<div style='padding:10px'>User của bạn không được phép thao tác trong chức năng này</div>",
                      okButton: {  text: ' OK ', click: function(){ this.$router.push('/')} },
                      showCloseIcon: true,
                      closeOnEscape: true,
                      animationSettings: { effect: 'Zoom' }
                      });
                    return false
                  }
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          this.$toast.error(error)
      }
    },
    getDSThuNgan: function(){

      GachNoAPI.getDSThuNgan(this.axios)
        .then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              this.params.p_thungan_options = response.data.data
              this.params.p_thungan = response.data.data[0].MA_NV
              this.params.p_quaythu_id = response.data.data[0].DONVI_ID
              this.getDonViQuanLy()
            }
          }
        )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {

      })
    },
    getDSLoaiTien: function(){

      try{
        GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: this.params.p_kycuoc}).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              this.params.p_loaitien_options = response.data.data
              this.params.p_loaitien = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    getDSNganHang: function(){
      try{
        CommonsAPI.getDSNganHangGachNo(this.axios).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              this.params.p_nganhang_options = response.data.data
              // this.params.p_nganhang = response.data.data[0].LOAITIEN_ID
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    getDSHTTT: function(nhom, kieu){
      try{
        //CommonsAPI.getDanhSachHinhThucThanhToan(this.axios, {kieu: kieu, nhomHTTT: nhom}).then(
          GachNoAPI.getDanhSachHTTT(this.axios, {kieu: kieu, nhomHTTT: nhom}).then(

          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
            {
              this.params.p_httt_options = response.data.data
              this.params.p_httt = response.data.data[0].HTTT_ID
              this.params.p_httt_text = response.data.data[0].HINHTHUC
            }
          }
        )
      } catch (error)
      {
        this.$toast.error(error)
      }
    },
    async getPayment_Info_ForPay(paycode) {
      try{
        const results = await GachNoAPI.getPayment_Info_ForPay(this.axios, {pKyCuoc: this.params.p_kycuoc, pPaymentCode: paycode});
        const data = await results.data
        console.log(data)
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        console.log(err.data.message_detail)
        return []
      }
    },
    async getPayment_Info_ForPay2(paycode) {
      try{
        const results = await GachNoAPI.getPayment_Info_ForPay2(this.axios, {kyCuoc : this.params.p_kycuoc, maTT: paycode});
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        console.log(err)
        return []
      }
    },
    async getThongTinTTTheoDBHienTai() {
      try{
        const results = await GachNoAPI.getTTThanhhToanTheoDBHienTai(this.axios, {pKyCuoc: this.params.p_kycuoc, pMaTB: this.params.p_ma_tt});
        return results
      }catch(err) {
        //console.log(err)
      }
    },
    async getDSTBTheoMaTT(){

      if(this.params.p_ds_tongno.length > 0)
      {
        this.params.p_ds_tongno.forEach(item => {
          if(this.params.p_acc_somay_options.length <= 0 || !this.params.p_acc_somay_options.includes(item.MA_TB))
            this.params.p_acc_somay_options.push(item.MA_TB)
        });
      } else
      {
        this.getTongNoTheoKyHoaDon(this.params.p_kycuoc, this.params.p_ma_tt, this.params.p_kieutra)
      }
      /*
      const response = await this.getTongNoTheoKyHoaDon()

      this.params.p_acc_somay_options = []

      if(response.data.error_code === 'BSS-00000000'
          && response.data.data !== undefined)
        {
          response.data.data.forEach(item => {
            //this.params.p_acc_somay_options.push({ THUEBAO_ID: item.THUEBAO_ID, MA_TB: item.MA_TB })
            if(this.params.p_acc_somay_options.length <= 0 || !this.params.p_acc_somay_options.includes(item.MA_TB))
              this.params.p_acc_somay_options.push(item.MA_TB)
          });
        }
      */
    },
    async getChiTietNoTheoChuKyNoHoanTra(postData) {
      this.loading(true)
      try{
        const results = await GachNoAPI.getChiTietNoTheoChuKyNoHoanTra(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        return []
      }finally{
        this.loading(false)
      }
    },
    async getChiTietNoTheoChuKyNo1(postData) {
      this.loading(true)
      try{
        const results = await GachNoAPI.getChiTietNoTheoChuKyNo1(this.axios, postData)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        //console.log(err)
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        return []
      }finally{
        this.loading(false)
      }
    },
    async getChiTietNoTheoChuKyNo2(postData) {
      this.loading(true)
      try{
        const results = await GachNoAPI.getChiTietNoTheoChuKyNo2(this.axios, postData)
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        //console.log(err)
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        return []
      }finally{
        this.loading(false)
      }
    },
    async getChiTietNoTheoKhoanMuc(postData) {
      this.loading(true)
      try{
        //console.log(data)
        const results = await GachNoAPI.getChiTietNoTheoKhoanMuc1(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        //console.log(err)
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        return []
      }finally{
        this.loading(false)
      }
    },
    async getChiTietNoTheoDichVu(postData) {
      this.loading(true)
      try{
        //console.log(data)
        const results = await GachNoAPI.getChiTietNoTheoDichVu1(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      }catch(err) {
        //console.log(err)
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        return []
      }finally{
        this.loading(false)
      }
    },
    async getChiTietNoKMDieuChinh(data) {
      try{
        const results = await GachNoAPI.getChiTietNoKMDieuChinh(this.axios, data);
        return results
      }catch(err) {
        //console.log(err)
      }
    },
    getDSKhoanMuc: function() {
      this.params.p_khoanmuc_options = []
      this.params.p_khoanmuc = null
      try{
        CommonsAPI.getDanhSachKhoanMucGachNo(this.axios).then(
          (response) => {
            if(response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined)
            {
              /*
              response.data.data.forEach( (item) => {
                //if(item.KHOANMUCTT_ID !== 3130) //&& item.SUDUNG === 1)
                {
                  this.params.p_khoanmuc_options.push({KHOANMUCTT_ID: item.KHOANMUCTT_ID, TEN_KM: item.TEN_KM})
                }
              })
              */
              this.params.p_khoanmuc_options = response.data.data
              this.params.p_khoanmuc = KHOANMUCTT_NO.KHOAN_MUC_TAM_UNG
            }
          }
        )

      }catch(err) {
        //console.log(err)
      }
    },
    clearResult: function(){
      this.resultGachLeThangNo = []
      this.totalItemsThangNo = 0
      this.resultGachLeDichVu = []
      this.resultGachLeKhoanMuc = []
      this.resultDieuChinh = []
      this.dsGachNoDanhSach = []
      //this.dsGachNoDanhSachTotals = 0
      this.dsChuKyNo = []
      this.dsErrors = []
      this.ds_thanhtoan = []
      this.dsPhieuCanXoa = []
      this.dsPhieuCanIn = []
    },
    Clear: function(){
      try{
        this.params.p_ma_tt = null
        this.params.p_phieu_id = 0
        this.params.p_luot_tt = 1
        this.params.p_diachi_baocuoc = null
        this.params.p_maso_thue = null
        this.params.p_taikhoan = null
        this.params.p_daily = null
        this.params.p_tien_tra = 0
        this.params.p_tien_no = 0
        this.params.p_tien_dchinh = 0
        // soseri_ds = 0;
        // vcacchukyno = "";
        if(this.params.p_tab_kieugach === 1)
        {

        } else if(this.params.p_tab_kieugach === 2)
          this.params.p_loaigach =  4 //Gạch nợ theo danh sách
        else
          this.params.p_loaigach =  5 //Điều chỉnh nợ
        this.params.p_matinh_thuho = null
        this.params.p_tien_tra = 0
        this.params.p_ma_dd = null

        this.params.p_thanhtoan_id = null
        this.params.p_diachi_tt = null
        // strMathuebao = "";
        this.params.p_acc_somay = null
        this.params.p_acc_somay_options = []

        this.resultGachLeDichVu = []
        this.resultGachLeKhoanMuc = []
        this.resultGachLeThangNo = []
        //intChukyno = 0;
        //gridDieuChinh.DataSource = null;
        // ds_dieuchinh_tmp = null;
        //ds_dieuchinh = null;
        this.params.p_ten_tt = null
        //ds_ctno = null;
        ///txtFloating.Text = "";
        //txtFloating.Visible = false;
        this.$refs.frmgachno_p_ma_tt.focus()
        //ds_thangno = null;
        this.params.ds_thangno_tmp = [];
        //ds_dichvu = null;
        this.params.ds_dichvu_tmp = [];
        this.params.ds_dieuchinh_tmp = []

        if (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_DAILY
          || this.params.p_nhom_httt == NHOM_HTTT.GACHNO_NGANHANG)
        {
            if (this.params.p_hoadon_insan === 1) //Nếu định nghĩa lấy dữ liệu từ dataport gửi
            {
              this.params.p_seri = null
              this.params.p_so_quyen = null
              this.params.p_so_seri = null
            }
        }
        else
        {
          txtSeri.Text = "";
          txtQuyen.Text = "";
          txtSoSeri.Text = "";
        }

        // phieu_id = 0;
        // soseri_ds = 0;

      } catch(err) {

      }
    },


    checkDataGachNo: function(){
      let errors = []

      if(
        this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO
      )
      {
        if(this.params.p_seri === null || ('' + this.params.p_seri).trim() === '')
          errors.push('Seri không được để trống')
        if(this.params.p_so_seri === null || ('' + this.params.p_so_seri).trim() === '')
          errors.push('Số seri không được để trống')
        if(this.params.p_so_quyen === null || ('' + this.params.p_so_quyen).trim() === '')
          errors.push('Số quyển không được để trống')
      }

      // kiểm tra ngày gạch lùi
      // Kỳ hóa đơn luôn = Tháng ngày thanh toán -1
      let kyhoadon_lui = moment(this.params.p_ngay_ttoan).subtract(1,'months').startOf('month').format('YYYYMMDD')
      //khd_lui = dtpNgayTT.DateTime.AddMonths(-1).Year.ToString() + dtpNgayTT.DateTime.AddMonths(-1).Month.ToString().PadLeft(2, '0');

      //Kiểm tra xem có được phép gạch nợ không
      /*
      if (!bangts.Kiemtra_gachno(khd + cboChuKy.Text))
      {
          Message_Box.ShowWarning("Kỳ cước " + khd + " hiện tại đang bị khóa. Hãy liên hệ Admin để được xử lý ");
          return false;
      }
      */
      /*
      if (kyhoadon_lui != this.params.p_kycuoc)
      {
          if (!bangts.Kiemtra_gachno(khd_lui + cboChuKy.Text))
          {
              Message_Box.ShowWarning("Kỳ cước " + kyhoadon_lui + " hiện tại đang bị khóa. Hãy liên hệ Admin để được xử lý ");
              return false;
          }
      }
      */

      if(this.params.p_thungan === null)
      {
        errors.push('Bạn hãy chọn thu ngân !')
      }
      if(this.params.p_nhom_httt !== NHOM_HTTT.GACHNO_TT && this.params.p_quaythu_id === null)
        errors.push('Đơn vị của user chưa được định nghĩa quầy thu. Hãy liên hệ Admin trước khi gạch nợ.')

      if(this.params.p_httt === null)
        errors.push('Không có hình thức thanh toán. Hãy kiểm tra lại!')

      // #region Kiểm tra ngày thanh toán với ngày hiện tại
      if(this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO)
      {



      }




      /*if (!KIEMTRA_QUYENGACH(kieugach, "GACHNO")) return false;

      if (kieugach == "1" && nhom_httt_id == NHOM_HTTT.GACHNO_DATCOC)
      {
          Message_Box.ShowTB("Bạn không được phép gạch điều chỉnh tra trước vào kỳ khác hiện tại ");
          return false;
      }

      */

      //#endregion


      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },

    checkNgayGach()
    {
      let date2 = moment(this.params.p_ngay_ttoan)
      let date1 = moment()
      let k = date2.diff(date1, 'days')

      if(date2.year() === date1.year() && date2.month() === date1.month())
      {
        if(k > 0) // gạch tiến
        {
          this.$toast.error('Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!')
          return false
        }
        if(k < 0)
        {
          if(this.params.ngaygach_lui_thanght > -1
            && this.params.ngaygach_lui_thanght < Math.abs(k))
          {
            this.$toast.error('Bạn không được phép lùi ' + Math.abs(k) + ' ngày trong tháng ' + date1.month())
            return false
          }
        }
        this.params.p_kieugach = 0
      } else  // nếu khác tháng hoặc năm
      {
        if (this.params.p_ngaygach_lui_thang === -1)
        {
          this.$toast.error('Ngày cho phép gạch lùi chưa được thiết lập ko thể gạch lùi')
          return false
        }
        if (k > 0) //Nếu gạch tiến
        {
          this.$toast.error('Bạn không được gạch tiến (Ngày thanh toán không thể lớn hơn ngày hiện tại)!')
          return false
        }
        if (k < 0)//Nếu gạch lùi
        {
          /*
          let maxDayOfMonth = moment
            var maxDayOfMonth = new DateTime(date1.Year, date1.Month, ngaygach_lui_thang);// Lấy ra ngày cho phép gạch đến của tháng
            var thangtt = maxDayOfMonth.AddMonths(-1);
            if (date1 > maxDayOfMonth)
            {
                Message_Box.ShowTB("Bạn không được phép gạch lùi. Ngày hiện tại không thuộc khoảng thời gian cho phép gạch lùi");
                return false;
            }
            // nếu phù hợp thì kiểm tra xem ngày tt có thuộc tháng hiện -1 ko
            var firstMonth = new DateTime(thangtt.Year, thangtt.Month, 1);
            var lastMonth = new DateTime(thangtt.Year, thangtt.Month, DateTime.DaysInMonth(thangtt.Year, thangtt.Month));
            if (date2 < firstMonth || date2 > lastMonth)
            {

                Message_Box.ShowTB("Bạn không được phép gạch với ngày thanh toán khác tháng " + thangtt.Month);
                return false;
            }
          */
        }
        this.params.p_kieugach = 1
      }
      return true
    },

    doGachNo: function(){

      let p_tentinh = "Hải Phòng"
      let mau_idhd = "0"

      this.$refs.gachno_tientra.focusIn()

      if(!this.checkDataGachNo())
        return false

      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TT)
      {
        DialogObj = DialogUtility.confirm({
            title: 'Thông báo',
            content: "<div style='padding:10px'>Bạn có muốn gạch nợ thu hộ  " + p_tentinh + " cho tỉnh " + params.p_matinh_thuho +  " không ?</div>",
            okButton: {  text: 'Đồng ý'/*, click: this.confirmDelete */},
            cancelButton: {  text: 'Hủy', click: function(){ DialogObj.hide(); return false}},
            showCloseIcon: true,
            closeOnEscape: true,
            animationSettings: { effect: 'Zoom' }
        });
      }

      let ds_matt = "";
      let dsPhieuTraID = "";
      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY
          || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY
          || this.params.p_nhom_httt === NHOM_HTTT.THOAITRA
          || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA
          )
      {
          if (this.params.p_in_khigachno === 1)
          {
            ds_matt = Lay_DS_MaTT();
          }
      }


      if ((this.params.p_nhom_httt === NHOM_HTTT.THOAITRA || this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
        && this.params.p_loaigach !== 4)
      {
        if (ds_matt !== "")
        {
          /*
            try
            {
                string sql_tientra = ""; // tiền trả trước khi gạch nợ thoái trả
                sql_tientra +=
                    " Select  sum(ct.tragoc - ct.chihoahong + ct.trathue) tongtien, sum(ct.tragoc) tragoc, sum(ct.trathue) trathue ";
                sql_tientra += " from " + DatabaseConstants.DB3 + ".bangphieutra_" + chuky + kyhoadon + " ba, " +
                                DatabaseConstants.DB3 + ".ct_tra_" + chuky + kyhoadon + " ct ";
                sql_tientra += " where ba.phieu_id = ct.phieu_id and ba.ma_tt = " + ds_matt;
                sql_tientra += " Group by ba.ma_tt";
                ds_no_PC = new BangThamSo().GetSQL(sql_tientra, "");
                dsThongTinKhachHang_PC = new TraCuuThuNoFacade().Lay_tt_no_theo_matt(chuky + kyhoadon, ds_matt);
            }
            catch (Exception ex)
            {
                Message_Box.ShowError("" + ex.ToString());
            }
          */
        }
      }

      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TT)
      {
        if(this.params.p_tien_tra === 0)
        {
          this.$toast.error("Tiền trả phải lớn hơn hoặc nhỏ hơn 0")
          this.$refs.gachno_tientra.focusIn()
          return false
        }
      }

      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY
        || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
      {
        if (this.params.p_tien_tra <= 0)
        {
          this.$toast.error("Tiền trả phải lớn hơn 0. Bạn hãy kiểm tra lại!")
          this.$refs.gachno_tientra.focusIn()
          return false
        }
      }
      else if (this.params.p_nhom_httt == NHOM_HTTT.THOAITRA || this.params.p_nhom_httt == NHOM_HTTT.HOANTRA)
      {
        //alert(this.params.p_tien_tra)
        if (this.params.p_tien_tra >= 0)
        {
          this.$toast.error("Tiền thoái trả phải là số âm. Bạn hãy kiểm tra lại!")
          this.$refs.gachno_tientra.focusIn()
          return false
        }
      }

      if (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_DATCOC)
      {
        if(this.params.p_intKieuthanhtoan === 1)  // thanh toán theo MA_TT
        {
          this.$toast.error("Bạn hãy thực hiện gạch điều chình đặt cọc trả trước bằng cách gõ thông tin vào ô mã thuê bao!")
          this.$refs.p_acc_somay.focusIn()
          return
        }

        if(this.params.p_kieugach === 1)  // gạch lùi
        {
          this.$toast.error("Bạn không được phép gạch điều chỉnh tra trước vào kỳ khác hiện tại ")
          return false
        }
      }

      /*
      this.params.p_tamthu = parseInt(this.params.p_kieugach === 0 ? this.params.p_kycuoc : ) Convert.ToInt64(kieugach == "0" ?
      kyhoadon.Substring(2, 4) + kyhoadon.Substring(0, 2) + chuky
      : kyhoadon_lui.Substring(2, 4) + kyhoadon_lui.Substring(0, 2) + chuky);
      */



      if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY)
        this.doGachTaiQuay()
      else if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY)
        this.doGachDaiLy()
      else if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
        this.doGachChuyenKhoan()
      else if(this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
        this.doGachDieuChinhNo()
      else if(this.params.p_nhom_httt === NHOM_HTTT.THOAITRA)
        this.doGachThoaiTra()
      else if(this.params.p_nhom_httt === NHOM_HTTT.THANHLY_NO)
        this.doGachThanhLyNo()
      else if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TT)
        this.doGachNoTrucTiep()
      else if(this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC)
        this.doGachNoDatCoc()
      else if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
        this.doGachNoHoanTra()
    },
    async doGachTaiQuay() {
      let thungan = this.thungan()
      //console.log(thungan)
      let postData = {
          "pKyCuoc": this.params.p_kycuoc,
          "pChiPhi": this.params.p_chiphi,
          "pChungTu": this.params.p_chungtu,
          "pCoTamThu": 1,
          "pDSTra": [],
          "pDonViQLNoId": this.params.p_donvi_quanly_id,
          "pGhiChu": this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '' ? this.params.p_ghichu.trim() : null,
          "pHTTTId": this.params.p_httt,
          "pIPCN": "-",
          "pLanTra": this.params.p_luot_tt,
          "pLoaiGach": this.params.p_loaigach,
          "pLoaiTienId": this.params.p_loaitien,
          "pMaNVTC": this.$auth.getMaNhanVien(),
          "pMaTN": this.params.p_thungan,
          "pMaTT": this.params.p_ma_tt,
          "pMayCN": "-",
          "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
          "pNguoiDungId": this.$auth.getNguoiDungID(),
          "pQuayThuId": this.params.p_quaythu_id,
          "pNganHangId": null,
          "pNgayNganHang": null,
          "pQuyen": this.params.p_so_quyen,
          "pSeri": this.params.p_seri,
          "pSoSeri": this.params.p_so_seri,
          "pThuNganId": thungan.NHANVIEN_ID
        }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGSETRADK, TIENTRA_PS: item.TONGSETRAPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }


        postData.pDSTra = dsTra
        

        this.loading(true)
        const res = await this.gachNoLe(postData)


        if(res !== null)
        {
          if(res[0].status === 3)
          {
            if(this.params.p_httt === 180)  // Gạch qua ví
            {
              this.thanhtoanViVNPTPay(this.params.p_tien_tra)
            }
            this.$toast.success('Gạch nợ thành công.')
          } else
          {
            this.$toast.error('Lỗi đồng bộ CCBS: ' + res[0].error)
          }

          this.loading(false)
          
          

          //this.Clear()
          this.params.p_phieu_id = res[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1

          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()

          this.setThongTinSeri({
              p_seri: this.params.p_seri, 
              p_so_quyen: this.params.p_so_quyen,
              p_so_seri: this.params.p_so_seri
            })
        }

        this.loading(false)

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        //let ds = this.getDSGachNo(this.params.p_loaigach)
        //let ds = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()
        let ds = this.dsGachNoDanhSach.filter(x => x._CHECK === true)

        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn trong danh sách để gạch nợ')
        }
        let dsTra = []
        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,
            }
          )
        })

        postData.pDSTra = dsTra

        this.params.p_seri = postData.pDSTra[postData.pDSTra.length - 1].SERI
        this.params.p_so_quyen = postData.pDSTra[postData.pDSTra.length - 1].QUYEN
        this.params.p_so_seri = parseInt(postData.pDSTra[postData.pDSTra.length - 1].SO_SERI) + 1

        this.loading(true)
        this.gachNoTienMatDanhSach(postData)
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'){
                //this.$toast.success('Gạch nợ thành công.')
                this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
                this.params.p_request_id = res.data.data
                this.params.p_phieu_id = 0

                this.setThongTinSeri({
                  p_seri: this.params.p_seri, 
                  p_so_quyen: this.params.p_so_quyen,
                  p_so_seri: this.params.p_so_seri
                })

                this.dsGachNoDanhSach = []
                //this.getSoPhieuTheoUser()
                this.params.timer = 0
                this.countTimer()
              }
            }
          )
          .catch(error => {
            //this.$toast.error(error.data.message_detail)
            console.log(error)
            this.loading(false)
          })
          .finally( () => {
            
          })

      }
    },
    async doGachDaiLy(){

      let thungan = this.thungan()
      let postData = {
          "pKyCuoc": this.params.p_kycuoc,
          "pChiPhi": this.params.p_chiphi,
          "pChungTu": null,
          "pCoTamThu": 1,
          "pDSTra": [],
          "pDonViQLNoId": this.params.p_donvi_quanly_id,
          "pGhiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
          "pHTTTId": this.params.p_httt,
          "pIPCN": "-",
          "pLanTra": this.params.p_luot_tt,
          "pLoaiGach": this.params.p_loaigach,
          "pLoaiTienId": this.params.p_loaitien,
          "pMaNVTC": this.$auth.getMaNhanVien(),
          "pMaTN": this.params.p_thungan,
          "pMaTT": this.params.p_ma_tt,
          "pMayCN": "-",
          "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
          "pNguoiDungId": this.$auth.getNguoiDungID(),
          "pQuayThuId": this.params.p_quaythu_id,
          "pQuyen": this.params.p_so_quyen,
          "pSeri": this.params.p_seri,
          "pSoSeri": this.params.p_so_seri,
          "pThuNganId": thungan.NHANVIEN_ID
        }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGNODK, TIENTRA_PS: item.NOGOCPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }
        //alert('a')
        postData.pDSTra = dsTra

        this.loading(true)
        const res = await this.gachNoLeDaiLy(postData)
        this.loading(false)

        if(res !== null)
        {
          if(res[0].status === 3)
          {
            this.$toast.success('Gạch nợ thành công.')
          } else
          {
            this.$toast.error('Lỗi đồng bộ CCBS: ' + res[0].error)
          }

          this.params.p_phieu_id = res[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []

          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        let ds = this.getDSGachNo(this.params.p_loaigach)
        let dsTra = []

        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,

            }
          )
        })

        postData.pDSTra = dsTra

        this.loading(true)
        this.gachNoTienMatDanhSach(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Gạch nợ thành công.')
              this.params.p_phieu_id = 0
              this.dsGachNoDanhSach = []
              this.getSoPhieuTheoUser()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally( () => {
          this.loading(false)
        })
      }
    },
    validateDieuChinhNo()
    {
      let errors = []
      /*
      if(('' + this.params.p_seri).trim() === '')
      {
        errors.push('Chưa nhập Seri')
      }
      if(('' + this.params.p_so_quyen).trim() === '')
      {
        errors.push('Chưa nhập số quyển')
      }
      if(('' + this.params.p_so_seri).trim() === '')
      {
        errors.push('Chưa nhập số seri')
      }
      */
      if(this.params.p_dieuchinh_canbang === 0)
      {
        if(this.params.p_lydo_dchinh === null || this.params.p_lydo_dchinh.trim() === '')
        {   
          errors.push('Hãy nhập ghi chú lý do điều chỉnh!')
        }
      }
      

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    async doGachDieuChinhNo()
    {
      if(!this.validateDieuChinhNo())
        return false
      let thungan = this.thungan()
      let postData = {
        "pKyCuoc": this.params.p_kycuoc,
        "pChukyNoDC": moment(this.params.p_thang_dchinh).format('YYYYMM') + '01',
        "pMaTT": this.params.p_ma_tt,
        "pMaTB": this.params.p_acc_somay,
        "pCoTamThu": this.params.p_tamthu,
        "pDCCanBang": this.params.p_dieuchinh_canbang,
        "pLoaiGach": this.params.p_loaigach,
        "pNgayDC": moment(this.params.p_ngay_dchinh).format('DD/MM/YYYY'),
        "pNgayTT": moment(this.params.p_ngay_dchinh).format('DD/MM/YYYY'),
        "pLoaiTienId": this.params.p_loaitien,
        "pHTTTId": this.params.p_httt,
        "pLanTra": this.params.p_luot_tt,
        "pSeri": '1',
        "pQuyen": 1,
        "pSoSeri": 1,
        "pGhiChu": this.params.p_lydo_dchinh.trim(),
        "pChungTu": this.params.p_chungtu,
        "pChiPhi": this.params.p_chiphi,
        "pMaNVTC": this.$auth.getMaNhanVien(),
        "pMaTN": this.params.p_thungan,
        "pDonViQLNoId": this.params.p_donvi_quanly_id,
        "pQuayThuId": this.params.p_quaythu_id,
        "pNguoiDungId": this.$auth.getNguoiDungID(),
        "pThuNganId": thungan.NHANVIEN_ID,
        "pNganHangId": this.params.p_nganhang,
        "pNgayNganHang": this.params.p_ngay_nganhang !== null ? moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY') : null,
        "pMayCN": "-",
        "pIPCN": "-",
        "pDSDC": ''
        }
      let dsDC = []
      let ds = this.getDSGachNo(this.params.p_loaigach)
      if(ds.length <= 0)
      {
        this.$toast.error('Hiện tại điều chỉnh nợ không có số liệu.')
        return false
      }

      if(this.params.p_loaigach === 5)  // điều chỉnh nợ
      {
        let vchukytra = moment(this.params.p_thang_dchinh).format('YYYYMM') + this.params.p_chuky_dchinh
        let in_kieutra = 0
        if('' + vchukytra === '' + this.params.p_kycuoc)
          in_kieutra = KIEUNO.NO_PHAT_SINH
        ds.forEach( (item, index) =>
        {
          let in_kmtt_id = item.KHOANMUCTT_ID
          let in_tragoc = item.DC_NOGOC
          let in_chihoahong = item.DC_HOAHONG
          let in_trathue = item.DC_THUE

          dsDC.push(
            {
              KHOANMUCTT_ID: in_kmtt_id,
              DC_GOC: in_tragoc,
              DC_THUE: in_trathue,
              DC_HOAHONG: in_chihoahong,
            }
          )

        })
        postData.pDSDC = dsDC
        this.loading(true)
        this.dieuchinhNo(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Điều chỉnh thành công.')
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              this.loadChiTietNo()
              this.getSoPhieuTheoUser()
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
      } else if(this.params.p_loaigach === 4) // gạch điều chỉnh danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        let ds = this.getDSGachNo(this.params.p_loaigach)
        let dsTra = []

        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              THANGTRA: item.THANGTRA,
              SERI: '1',
              QUYEN: '1',
              SO_SERI: '1',

            }
          )
        })

        postData.pDSTra = dsTra
        
        this.loading(true)
        this.dieuchinhNoDS(postData)
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'){
                //this.$toast.success('Gạch nợ thành công.')
                this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
                this.params.p_request_id = res.data.data
                this.params.p_phieu_id = 0


                this.dsGachNoDanhSach = []
                this.getSoPhieuTheoUser()
                this.params.timer = 0
                this.countTimer()
              }
            }
          )
          .catch(error => {
            //this.$toast.error(error.data.message_detail)
            console.log(error)
            this.loading(false)
          })
          .finally( () => {
            
          })
        /*

        this.loading(true)
        this.dieuchinhNoDS(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Gạch nợ thành công.')
              this.params.p_phieu_id = 0
              this.dsGachNoDanhSach = []
              this.getSoPhieuTheoUser()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally( () => {
          this.loading(false)
        })
        */
      } else if(this.params.p_tab_kieugach === 1)
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGNODK, TIENTRA_PS: item.NOGOCPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }

        if(this.params.p_dieuchinh_canbang === 0)
        {
          if(this.params.p_tien_dchinh === 0)
          {
            this.$toast.error('Số tiền điều chỉnh phải khác 0. Bạn hãy kiểm tra lại!')
            this.$refs.p_tien_dchinh.focus()
            return
          }
        }


        postData.pDSTra = JSON.stringify(dsTra)

        this.loading(true)
        this.dieuchinhNoLe(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Điều chỉnh thành công.')
              //this.Clear()
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              //this.params.p_so_seri = this.params.p_so_seri + 1
              this.params.p_vcacchukyno = []
              this.loadChiTietNo()
              this.getSoPhieuTheoUser()
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
      }


    },
    async doGachChuyenKhoan()
    {

      let errors = []
      if(this.params.p_nganhang === null)
      {
        errors.push('Chưa chọn ngân hàng để thanh toán.')
      }

      if(this.params.p_chungtu === null || this.params.p_chungtu === '' || this.params.p_chungtu.toString().trim() === '')
      {
        errors.push('Chưa nhập chứng từ.')
      }

      if(this.params.p_ngay_nganhang === null)
      {
        errors.push('Chưa nhập ngày ngân hàng.')
      }

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      let thungan = this.thungan()
      let postData = {
        "pKyCuoc": this.params.p_kycuoc,
        "pChiPhi": this.params.p_chiphi,
        "pChungTu":  this.params.p_chungtu,
        "pCoTamThu": 1,
        "pDSTra": '',//JSON.stringify(dsTra),
        "pDonViQLNoId": this.params.p_donvi_quanly_id,
        "pGhiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
        "pHTTTId": this.params.p_httt,
        "pIPCN": "-",
        "pLanTra": 1,
        "pLoaiGach": this.params.p_loaigach,
        "pLoaiTienId": this.params.p_loaitien,
        "pMaNVTC": this.$auth.getMaNhanVien(),
        "pMaTN": this.params.p_thungan,
        "pMaTT": this.params.p_ma_tt,
        "pMayCN": "-",
        "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        "pNguoiDungId": this.$auth.getNguoiDungID(),
        "pQuayThuId": this.params.p_quaythu_id,
        "pNganHangId": this.params.p_nganhang,
        "pNgayNganHang": moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY'),
        "pQuyen": this.params.p_so_quyen,
        "pSeri": this.params.p_seri,
        "pSoSeri": this.params.p_so_seri,
        "pThuNganId": thungan.NHANVIEN_ID
      }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGNODK, TIENTRA_PS: item.NOGOCPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }
        postData.pDSTra = JSON.stringify(dsTra)

        this.loading(true)
        const res = await this.gachNoLeNganHang(postData)
        this.loading(false)

        if(res !== null)
        {
          if(res[0].status === 3)
          {
            this.$toast.success('Gạch nợ thành công.')
          } else
          {
            this.$toast.error('Lỗi đồng bộ CCBS: ' + res[0].error)
          }
          this.params.p_phieu_id = res[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        let ds = this.getDSGachNo(this.params.p_loaigach)
        //let ds = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()

        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn trong danh sách để gạch nợ')
        }
        let dsTra = []
        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,
            }
          )
        })

        postData.pDSTra = dsTra

        this.loading(true)
        this.gachNoTienMatDanhSach(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              //this.$toast.success('Gạch nợ thành công.')
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
              this.params.p_request_id = res.data.data
              this.params.p_phieu_id = 0

              this.dsGachNoDanhSach = []
              this.getSoPhieuTheoUser()
              this.params.timer = 0
              this.countTimer()
            }
          }
        )
        .catch(error => {
          this.loading(false)
          this.$toast.error(error.data.message_detail)
          
        })
        .finally( () => {
          //this.loading(false)
        })
      }
    },
    validateThoaiTra(){
      let errors = []

      if(this.params.p_httt === HTTT_NO.THOAITRA_CHUYENKHOAN)
      {
        if(this.params.p_nganhang === null)
        {
          errors.push('Bạn hãy chọn ngân hàng trước khi thoái trả!')
        }

        if(this.params.p_chungtu === null || this.params.p_chungtu === '' || this.params.p_chungtu.toString().trim() === '')
        {
          errors.push('Chưa nhập chứng từ.')
        }

        if(this.params.p_ngay_nganhang === null)
        {
          errors.push('Chưa nhập ngày ngân hàng.')
        }

        if(this.params.p_taikhoan === null || this.params.p_taikhoan === '' || this.params.p_taikhoan.trim() === '')
        {
          errors.push('Chưa nhập tài khoản.')
        }
      }

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    async doGachThoaiTra(){


      if(!this.validateThoaiTra())
        return false



      let thungan = this.thungan()
      let postData = {
        "pChiPhi": this.params.p_chiphi,
        "pChungTu":  this.params.p_chungtu,
        "pCoTamThu": this.params.p_tamthu,
        "pDSTra": '',//JSON.stringify(dsTra),
        "pDonViQLNoId": this.params.p_donvi_quanly_id,
        "pGhiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
        "pHTTTId": this.params.p_httt,
        "pIPCN": "-",
        "pLanTra": 1,
        "pLoaiGach": this.params.p_loaigach,
        "pLoaiTienId": this.params.p_loaitien,
        "pMaNVTC": thungan.MA_NV,
        "pMaTN": this.params.p_thungan,
        "pMaTT": this.params.p_ma_tt,
        "pMayCN": "-",
        "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        "pNguoiDungId": this.$auth.getNguoiDungID(),
        "pQuayThuId": this.params.p_quaythu_id,
        "pNganHangId": this.params.p_nganhang,
        "pNgayNganHang": moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY'),
        "pQuyen": this.params.p_so_quyen,
        "pSeri": this.params.p_seri,
        "pSoSeri": this.params.p_so_seri,
        "pThuNganId": thungan.NHANVIEN_ID
      }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGNODK, TIENTRA_PS: item.NOGOCPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }

        /*
          DataSet ds_no_PC = new DataSet();
            DataSet dsThongTinKhachHang_PC = new DataSet();
            if (nhom_httt_id == NHOM_HTTT.THOAITRA && loaigach != 4)
            {
                if (ds_matt != "")
                {
                    try
                    {
                        string sql_tientra = ""; // tiền trả trước khi gạch nợ thoái trả
                        sql_tientra +=
                            " Select  sum(ct.tragoc - ct.chihoahong + ct.trathue) tongtien, sum(ct.tragoc) tragoc, sum(ct.trathue) trathue ";
                        sql_tientra += " from " + DatabaseConstants.DB3 + ".bangphieutra_" + chuky + kyhoadon + " ba, " +
                                       DatabaseConstants.DB3 + ".ct_tra_" + chuky + kyhoadon + " ct ";
                        sql_tientra += " where ba.phieu_id = ct.phieu_id and ba.ma_tt = " + ds_matt;
                        sql_tientra += " Group by ba.ma_tt";
                        ds_no_PC = new BangThamSo().GetSQL(sql_tientra, "");
                        dsThongTinKhachHang_PC = new TraCuuThuNoFacade().Lay_tt_no_theo_matt(chuky + kyhoadon, ds_matt);
                    }
                    catch (Exception ex)
                    {
                        Message_Box.ShowError("" + ex.ToString());
                    }
                }
            }
        */
        postData.pDSTra = JSON.stringify(dsTra)

        this.loading(true)
        /*
        this.gachNoLeThoaiTra(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Gạch nợ thành công.')
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
              this.params.p_vcacchukyno = []
              this.loadChiTietNo()
              //this.setTh
              this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
              this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
              this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
              ///InPhieuChi(ds_matt, chuky + kyhoadon, ds_no_PC, dsThongTinKhachHang_PC);
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
        */

        const res = await this.gachNoLeThoaiTra(postData)
        if(res !== null)
        {
          if(res[0].status === 3)
          {
            this.$toast.success('Gạch nợ thành công.')
          } else
          {
            this.$toast.error('Lỗi đồng bộ CCBS: ' + res[0].error)
          }
          this.params.p_phieu_id = res[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        //let ds = this.getDSGachNo(this.params.p_loaigach)
        let ds = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()

        if(this.params.p_httt === HTTT_NO.THOAITRA)
        {
          let errors = ds.filter(x => x.TIENTRA > 0)
          if(errors.length > 0)
          {
            let ma_tt = errors.map(function(x){ return x.MA_TT} )
            this.$toast.error('Các mã TT sau có tiền trả > 0 : ' + ma_tt.join(','))
            return false
          }
        }

        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn trong danh sách để gạch nợ')
        }
        let dsTra = []
        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,
            }
          )
        })

        postData.pDSTra = dsTra

        this.loading(true)
        this.gachNoTienMatDanhSach(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              //this.$toast.success('Gạch nợ thành công.')
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
              this.params.p_request_id = res.data.data
              this.params.p_phieu_id = 0

              this.dsGachNoDanhSach = []
              this.getSoPhieuTheoUser()
              this.params.timer = 0
              this.countTimer()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        })
        .finally( () => {
          //this.loading(false)
        })

      }
    },
    async doGachThanhLyNo()
    {
      let res = await this.KiemTra_ThanhLyNo()
      let bbthuhoi = -1;

      if(res.data.error_code === 'BSS-00000000'
        && res.data.data !== undefined
        && res.data.data.length > 0)
        {
          bbthuhoi = parseInt(res.data.data[0].TEN_TS)
          if (bbthuhoi === 1)
          {
            /*
              BangThamSo bangts = new BangThamSo();

              string str = "Select 1 from " + DatabaseConstants.DB2 + ".db_thuebao where thanhtoan_id=" + thanhtoan_id
                            + " and trangthaitb_id not in (7,8,9)";
              DataSet ds = bangts.GetSQL(str, "");

              if (ds != null && ds.Tables[0].Rows.Count > 0)
              {
                  Message_Box.ShowTB("Đang có thuê bao cùng mã thanh toán: " + txtTenTT.Text +
                                      " chưa thu hồi. Không thể thanh lý!");
                  return false;
              }
            */
          }

          if(this.params.p_ghichu === null || ('' + this.params.p_ghichu).trim() === '')
          {
            this.$toast.error('Hãy nhập ghi chú thanh lý nợ!')
            return false
          }
        }
      
      let thungan = this.thungan()
      let postData = {
          "chiPhi": this.params.p_chiphi,
          "chungTu":  this.params.p_chungtu,
          "coTamThu": 1,
          "dsTra": [],
          "donViQLNoId": this.params.p_donvi_quanly_id,
          "ghiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
          "htttId": this.params.p_httt,
          "pIPCN": "-",
          "lanTra": 1,
          "loaiGach": this.params.p_loaigach,
          "loaiTienId": this.params.p_loaitien,
          "maNVTC": this.$auth.getMaNhanVien(),
          "pMaTN": this.params.p_thungan,
          "maTT": this.params.p_ma_tt,
          "pMayCN": "-",
          "ngayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
          "pNguoiDungId": this.$auth.getNguoiDungID(),
          "quayThuId": this.params.p_quaythu_id,
          "nganHangId": null,
          "ngayNganHang": null,
          "quyen": this.params.p_so_quyen,
          "seri": this.params.p_seri,
          "soSeri": this.params.p_so_seri,
          "thuNganId": thungan.NHANVIEN_ID
        }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { chuKyNo: item.CHUKYNO, tienTra: item.CONNO, dichVuVTId: item.DICHVUVT_ID, maTB: item.MA_TB, tienTraDK: null, tienTraPS: null})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { tienTra: item.TONGSEPHAITRA, dichVuVTId: item.DICHVUVT_ID, maTB: item.MA_TB, tienTraDK: item.TONGSETRADK, tienTraPS: item.TONGSETRAPS})
            //dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGSETRADK, TIENTRA_PS: item.TONGSETRAPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { tienTra: item.TIENTRA, dichVuVTId: item.DICHVUVT_ID, maTB: item.MA_TB, tienTraDK: null, tienTraPS: null})
            //dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }


        postData.dsTra = dsTra

        this.loading(true)
        const res = await this.gachNoLeThanhLy(postData)
        this.loading(false)

        if(res !== null)
        {
          this.$toast.success('Gạch nợ thành công.')
          //this.Clear()
          this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }
        /*
        this.gachNoLeThanhLy(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Gạch nợ thành công.')
              //this.Clear()
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
              //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
              this.params.p_vcacchukyno = []
              this.loadChiTietNo()
              this.getSoPhieuTheoUser()
              this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
              this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
              this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
        */

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        //let ds = this.getDSGachNo(this.params.p_loaigach)
        let ds = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()

        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn trong danh sách để gạch nợ')
        }
        let dsTra = []
        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,
            }
          )
        })

        postData.pDSTra = dsTra

        this.loading(true)
        this.gachNoThanhLyDanhSach(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              //this.$toast.success('Gạch nợ thành công.')
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
              this.params.p_request_id = res.data.data
              this.params.p_phieu_id = 0

              this.dsGachNoDanhSach = []
              this.getSoPhieuTheoUser()
              this.params.timer = 0
              this.countTimer()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        })
        .finally( () => {
          //this.loading(false)
        })

      }
    },
    doGachNoTrucTiep: function(){

    },
    async doGachNoDatCoc(){
      let postData = {}
      let thungan = this.thungan()
      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        }
        postData = {
          "pKMId": this.params.p_vrkm_id,
          "pTienVIKH": this.params.p_vtien_vikh,
          "pTienTra": this.params.p_tien_tra,
          "pChiPhi": this.params.p_chiphi,
          "pChungTu":  this.params.p_chungtu,
          "pCoTamThu": 1,
          "pDSTra": JSON.stringify(dsTra),
          "pDonViQLNoId": this.params.p_donvi_quanly_id,
          "pGhiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
          "pHTTTId": this.params.p_httt,
          "pIPCN": "-",
          "pLanTra": 1,
          "pLoaiGach": this.params.p_loaigach,
          "pLoaiTienId": this.params.p_loaitien,
          "pMaNVTC": this.$auth.getMaNhanVien(),
          "pMaTN": this.params.p_thungan,
          "pMaTT": this.params.p_ma_tt,
          "pMayCN": "-",
          "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
          "pNguoiDungId": this.$auth.getNguoiDungID(),
          "pQuayThuId": this.params.p_quaythu_id,
          "pNganHangId": this.params.p_nganhang,
          "pNgayNganHang": moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY'),
          "pQuyen": this.params.p_so_quyen,
          "pSeri": this.params.p_seri,
          "pSoSeri": this.params.p_so_seri,
          "pThuNganId": thungan.NHANVIEN_ID
        }

        this.loading(true)
        const res = await this.gachNoLeDatCoc(postData)

        this.loading(false)

        if(res !== null)
        {
          this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
          this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }

        /*
        this.gachNoLeDatCoc(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              this.params.xoaPhieuParams.tongTien = this.params.p_tien_tra
              //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
              this.params.p_vcacchukyno = []
              this.loadChiTietNo()
              this.getSoPhieuTheoUser()
              this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
              this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
              this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
        */

      }
    },
    async doGachNoHoanTra()
    {
      if(this.params.p_httt === HTTT_NO.THOAITRA_CHUYENKHOAN)
      {
        if(this.params.p_nganhang === null)
        {
          errors.push('Bạn hãy chọn ngân hàng trước khi hoàn trả!')
        }

        if(this.params.p_chungtu === null || this.params.p_chungtu === '' || this.params.p_chungtu.toString().trim() === '')
        {
          errors.push('Chưa nhập chứng từ.')
        }

        if(this.params.p_ngay_nganhang === null)
        {
          errors.push('Chưa nhập ngày ngân hàng.')
        }

        if(this.params.p_taikhoan === null || this.params.p_taikhoan === '' || this.params.p_taikhoan.trim() === '')
        {
          errors.push('Chưa nhập tài khoản.')
        }
      }
      let thungan = this.thungan()
      let postData = {
        "pDCCanBang": 0,
        "pChiPhi": this.params.p_chiphi,
        "pChungTu":  this.params.p_chungtu,
        "pCoTamThu": 1,
        "pDSTra": [],//JSON.stringify(dsTra),
        "pDonViQLNoId": this.params.p_donvi_quanly_id,
        "pGhiChu":  (this.params.p_ghichu !== null && this.params.p_ghichu.trim() !== '') ? this.params.p_ghichu.trim() : null,
        "pHTTTId": this.params.p_httt,
        "pIPCN": "-",
        "pLanTra": 1,
        "pLoaiGach": this.params.p_loaigach,
        "pLoaiTienId": this.params.p_loaitien,
        "pMaNVTC": this.params.p_thungan,
        "pMaTN": this.$auth.getMaNhanVien(),
        "pMaTT": this.params.p_ma_tt,
        "pMayCN": "-",
        "pNgayTT": moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        "pNguoiDungId": this.$auth.getNguoiDungID(),
        "pQuayThuId": this.params.p_quaythu_id,
        "pNganHangId": this.params.p_nganhang,
        "pNgayNganHang": moment(this.params.p_ngay_nganhang).format('DD/MM/YYYY'),
        "pQuyen": this.params.p_so_quyen,
        "pSeri": this.params.p_seri,
        "pSoSeri": this.params.p_so_seri,
        "pThuNganId": thungan.NHANVIEN_ID
      }

      if(this.params.p_tab_kieugach === 1) // gạch lẻ
      {
        let dsTra = []
        let ds = this.getDSGachNo(this.params.p_loaigach)

        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '' || this.params.p_thanhtoan_id === '0')
        {
          this.$toast.error('Không có thông tin về khách hàng')
        }
        if(this.params.p_loaigach === 2)  // gạch theo tháng nợ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu tháng nợ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { CHUKYNO: item.CHUKYNO, TIENTRA: item.CONNO, DICHVUVT_ID: item.DICHVUVT_ID, MA_TB: item.MA_TB})
          })
        } else if (this.params.p_loaigach === 1) // khoản mục
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo khoản mục.')
            return false
          }
          //console.log(ds)
          ds.forEach( (item, index) => {
            dsTra.push( { KHOANMUCTT_ID: item.KHOANMUCTT_ID, MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA_DK: item.TONGNODK, TIENTRA_PS: item.NOGOCPS})
          })
        } else if (this.params.p_loaigach === 3) // dịch vụ
        {
          if(ds.length <= 0)
          {
            this.$toast.error('Hiện tại không có số liệu nợ theo dịch vụ.')
            return false
          }
          ds.forEach( (item, index) => {
            dsTra.push( { MA_TB: item.MA_TB, DICHVUVT_ID: item.DICHVUVT_ID, TIENTRA: item.TIENTRA})
          })
        }
        postData.pDSTra = dsTra//JSON.stringify(dsTra)

        this.loading(true)
        const res = await this.gachNoLeHoanTraTamThu(postData)
        this.loading(false)

        if(res !== null)
        {
          this.$toast.success('Gạch nợ thành công.')
          this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
          //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
          this.params.p_vcacchukyno = []
          this.loadChiTietNo()
          this.getSoPhieuTheoUser()
          this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
          this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
          this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
        }
        /*
        this.gachNoLeHoanTraTamThu(postData).then(
          (res) => {
            if(res.data.error_code === 'BSS-00000000'){
              this.$toast.success('Gạch nợ thành công.')
              this.params.p_phieu_id = res.data.data[0].PHIEUTRA_ID
              //this.params.p_so_seri = parseInt(this.params.p_so_seri) + 1
              this.params.p_vcacchukyno = []
              this.loadChiTietNo()
              this.getSoPhieuTheoUser()
              this.setDataToStorage(this.KEY_SERI, this.params.p_seri)
              this.setDataToStorage(this.KEY_SOQUYEN, this.params.p_so_quyen)
              this.setDataToStorage(this.KEY_SOSERI, this.params.p_so_seri)
              ///InPhieuChi(ds_matt, chuky + kyhoadon, ds_no_PC, dsThongTinKhachHang_PC);
            }
          }
        )
        .finally( () => {
          this.loading(false)
        })
        */

      } else  // gạch danh sách
      {
        this.params.p_phieu_id_bd = 0
        this.params.p_phieu_id_kt = 0
        this.params.p_phieu_id = 0

        //let ds = this.getDSGachNo(this.params.p_loaigach)
        //let ds = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()
        let ds = this.dsGachNoDanhSach.filter(x => x._CHECK === true)

        if(ds.length <= 0)
        {
          this.$toast.error('Chưa chọn trong danh sách để gạch nợ')
        }
        let dsTra = []
        ds.forEach( (item, index) => {
          dsTra.push(
            {
              MA_TT: item.MA_TT,
              TIENTRA: item.TIENTRA,
              SERI: item.SERI,
              QUYEN: item.QUYEN,
              SO_SERI: item.SO_SERI,
            }
          )
        })

        postData.pDSTra = dsTra
        postData.kyCuoc = this.params.p_kycuoc

        this.params.p_seri = postData.pDSTra[postData.pDSTra.length - 1].SERI
        this.params.p_so_quyen = postData.pDSTra[postData.pDSTra.length - 1].QUYEN
        this.params.p_so_seri = parseInt(postData.pDSTra[postData.pDSTra.length - 1].SO_SERI) + 1

        this.loading(true)
        this.gachNoDSHoanTraTamThu(postData)
          .then(
            (res) => {
              if(res.data.error_code === 'BSS-00000000'){
                //this.$toast.success('Gạch nợ thành công.')
                this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
                this.params.p_request_id = res.data.data
                this.params.p_phieu_id = 0

                this.setThongTinSeri({
                  p_seri: this.params.p_seri, 
                  p_so_quyen: this.params.p_so_quyen,
                  p_so_seri: this.params.p_so_seri
                })

                this.dsGachNoDanhSach = []
                this.getSoPhieuTheoUser()
                this.params.timer = 0
                this.countTimer()
              }
            }
          )
          .catch(error => {
            //this.$toast.error(error.data.message_detail)
            console.log(error)
            this.loading(false)
          })
          .finally( () => {
            
          })
      }
    },

    async thanhtoanViVNPTPay(amount)
    {
      const login = await this.getViVNPTPayToken()      
      console.log(login)
      if(login === null)  return
      try{
        const baseUrl = process.env.API;
        const headers = {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.$auth.getToken(),
          'WalletToken': login.token
        }
        //let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
        let postData = {
          "collaboratorId": "",
          "customerId": this.params.p_ma_tt,
          "sendWalletAccountId": login.walletAccountId,
          "sendWalletId": login.walletId,
          "transferAmount": amount,
          "transferCode": "",
          "transferContent": "",
          "transferType": 15  //
        }
        const response = await this.axios.post(baseUrl + "/tichhop/vnptpay/initTransaction", postData, { headers: headers})
        const data = await response.data
        console.log(data)
        
      }catch(err)
      {
        //this.$toast.error(error.data.message_detail)
        console.log(err)
        return null
      }
    },

    async gachnoTrucTiepCCBS(postData)
    {
      try{
        const res = await GachNoAPI.gachNoTrucTiepCCBS(this.axios, postData)
        const data = await res.data
        if(data.error_code === 'BSS-00000000')
        return data.data.phieu_id
      }catch(error){
        if(error.data !== undefined && error.data.message_detail !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          this.$toast.error(error)
        return null
      }finally{} 
    },
    doDieuChinh: function(){
      this.doGachDieuChinhNo()
    },
    doDieuChinhFile: function(){
      this.$refs.refDieuChinhNoFile.openDialog()
    },
    doHuy: function(){
      this.Clear()
    },
    doXoaPhieu: function(){
      if(this.params.p_phieu_id === 0)
      {
        this.$toast.error("Không có phiếu để xóa!")
        return false
      }

      // kiểm tra gạch lùi
      if(this.params.p_kieugach === 0)
      {

      } else
      {

      }
      // kiểm tra quyền xóa

      ///



      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: "<div style='padding:10px'>Bạn có thật sự muốn xóa phiếu thanh toán này không ?</div>",
        okButton: {  text: 'Đồng ý', click: this.confirmDeletePhieu },
        cancelButton: {  text: 'Hủy'},
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      });


      /*

      this.dsPhieuCanXoa = []
      this.dsPhieuCanXoa.push( {PHIEU_ID: this.params.p_phieu_id, SOTIENTRA: this.params.xoaPhieuParams.tongTien})

      this.$refs.refXoaPhieuTT.openDialog();
      */

    },
    confirmDeletePhieu()
    {
      DialogObj.hide()
      let apiBody = {
        pKyCuoc: this.params.p_kycuoc,
        pMayHuy: '-',
        pNguoiHuy: this.$auth.getUserName(),
        pPhieuId: this.params.p_phieu_id,
        pPassword: 'ONEBSS'
      }


      this.loading(true)

      XLAPI.deletePhieuThanhToan(this.axios, apiBody)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Xóa phiếu thành công.')
            this.params.p_phieu_id = 0
            this.loadChiTietNo()
            this.getSoPhieuTheoUser()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async deletePhieuThuHandler(flag)
    {
      if(flag)  // xóa thành công
      {
        this.params.p_phieu_id = 0
        this.loadChiTietNo()
        this.getSoPhieuTheoUser()
      }
    },


    readFileHandler()
    {
      this.doDocFile()
    },

    clearGachDSHandler()
    {
      this.dsGachNoDanhSach = []
      this.params.p_tien_no = 0
      this.params.p_tien_tra = 0
    },

    exportFileDSHandler()
    {
      this.doXuatFile()
    },

    createGroupCKHandler()
    {



    },


    gachDSRecordClickHandler(data)
    {

      this.params.p_ma_tt = data.MA_TT
      this.params.p_ma_dd = data.MATB_DD
      this.params.p_ten_tt = data.TEN_TT
      this.params.p_diachi_tt = data.DIACHI_TT
      this.params.p_diachi_baocuoc = data.DIACHI_BC
      this.params.p_daily = "Tuyến thu: " + data.MA_TUYEN + " - Đại lý: " + data.MANV_TC + " - " + data.TEN_NV
      //this.params.p_thungan = data.MANV_TC
      this.p_acc_somay = null
    },
    /*
    async deletePhieuProcess(args) {
        let phieuCount = 0;
        this.loading(true);
        try {
          let apiBody = {
            pKyCuoc: this.params.p_kycuoc,
            pMayHuy: '-',
            pNguoiHuy: this.$auth.getUserName(),
            pPhieuId: this.params.p_phieu_id,
            pPassword: args.password
          };

          let result = await this.deletePhieuThu(apiBody);
          if (result) {
            this.$toast.success('Xóa phiếu thanh toán thành công!');
            this.params.p_phieu_id = 0
            this.loadChiTietNo()
            this.getSoPhieuTheoUser()
          }
          else {
            this.$toast.error('Không thể xóa phiếu thanh toán có ID:' + this.params.p_phieu_id);
          }
        }
        catch (error) {
          console.log(error);
        }
        finally {
          this.loading(false);
        }
      },
    async deletePhieuThu(apiBody) {
      try {
        let response = await XLAPI.deletePhieuThanhToanMK(this.axios, apiBody);
        return response.data.error_code === 'BSS-00000000' ? true : false;
      }
      catch (error) {
        //console.log(error);
        this.$toast.error(error.data.message_detail)
        return false;
      }
      finally {}
    },
    */
    doPhieuTra: function(){
      this.popupDSPhieuThanhToan()
    },
    doPhieuHuy: function(){
      this.popupDSPhieuHuy()
    },
    doDocFile: function(){
      document.getElementById("gachno_upload_excel").click()
      //this.$refs.refGachNoTheoFile.openDialog()
    },

    checkDuplicateMaTT(arr)
    {
      let ret = []
      if(arr.length <= 0) return []
      for(let i = 0; i < arr.length-1; i++)
        for(let j = i + 1; j < arr.length; j++)
          if(arr[i].MA_TT === arr[j].MA_TT)
          {
            ret.push({
              MA_TT: arr[j].MA_TT,
              THONGTINLOI: 'Mã Thanh toán bị trùng lặp'
            })
          }
      return ret
    },

    trimUploadData(data, kieu)
    {
      if(data.length <= 0)
        return []
      let ret = []
      data.forEach((item) => {
        item.MA_TT = ('' + item.MA_TT).trim()

        if(kieu === 2)
        {
          item.TIEN = ('' + item.TIEN).trim()
          item.GHICHU = item.GHICHU !== undefined ? ('' + item.GHICHU).trim() : ''
          item.CHUNGTU = item.CHUNGTU !== undefined ? ('' + item.CHUNGTU).trim() : ''
        } else if(kieu === 3)
        {
          item.TIEN = ('' + item.TIEN).trim()
          item.GHICHU = item.GHICHU !== undefined ? ('' + item.GHICHU).trim() : ''
          item.CHUNGTU = item.CHUNGTU !== undefined ? ('' + item.CHUNGTU).trim() : ''
          item.THANGTRA = item.THANGTRA !== undefined ? ('' + item.THANGTRA).trim() : null
        } else if(kieu === 4)
        {
          item.TIEN = ('' + item.TIEN).trim()
          item.GHICHU = item.GHICHU !== undefined ? ('' + item.GHICHU).trim() : ''
          item.CHUNGTU = item.CHUNGTU !== undefined ? ('' + item.CHUNGTU).trim() : ''
          item.THANGTRA = item.THANGTRA !== undefined ? ('' + item.THANGTRA).trim() : null
          item.KHOANMUCTT_ID = item.KHOANMUCTT_ID !== undefined ? ('' + item.KHOANMUCTT_ID).trim() : null
        }

        ret.push(item)
      })

      return ret
    },
    async uploadFile(e){
      if(this.f_enable_seri)
      {
        if(this.params.p_seri === null || this.params.p_seri === '')
        {
          this.$toast.error('Chưa nhập Seri')
          document.getElementById('gachno_upload_excel').value= null;
          return false
        }
      }
      
      let vkieu = 1; // 1: Gạch theo Mã TT, 2: MA_TT, TIEN, GHICHU, 3: MA_TT, TIEN, TRANGTRA, GHICHU
      var files = e.target.files, f = files[0];
      if (!files.length) {
          return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return  this.$root.toastWarning("File excel không đúng định dạng!");
      }
      var reader = new FileReader();
      const vm = this
      reader.onload = function(e) {
        var data = new Uint8Array(e.target.result);
        var workbook = XLSX.read(data, {type: 'array'});
        let sheetName = workbook.SheetNames[0]
        /* DO SOMETHING WITH workbook HERE */
        //console.log(workbook);
        let worksheet = workbook.Sheets[sheetName];
        let exceldata = XLSX.utils.sheet_to_json(worksheet)
        let exceldata1 = XLSX.utils.sheet_to_json(worksheet, { header: 1 })

        //console.log(exceldata)

        if(exceldata.length <= 0)
        {
          vm.$toast.error('Không tìm thấy dữ liệu trong file đã upload.')
          document.getElementById('gachno_upload_excel').value= null;
          return false
        }

        if(vm.params.p_nhom_httt === NHOM_HTTT.GACHNO_NGANHANG)
        {
          /*
          if(!exceldata[0].hasOwnProperty('MA_TT')
            || !exceldata[0].hasOwnProperty('TIEN')
            || !exceldata[0].hasOwnProperty('GHICHU')
            || !exceldata[0].hasOwnProperty('CHUNGTU')
          )
          */
         if(!exceldata1[0].includes('MA_TT')
          || !exceldata1[0].includes('TIEN')
          || !exceldata1[0].includes('GHICHU')
          || !exceldata1[0].includes('CHUNGTU')
          )
          {
            vm.$toast.error('File gồm có 4 cột: MA_TT, TIEN, GHICHU, CHUNGTU')
            document.getElementById('gachno_upload_excel').value= null;
            return false
          }
        } else
        {
          //fdg.Title = "Cấu trúc file gồm sheet1: { MA_TT } hoặc { MA_TT, TIEN, GHICHU,CHUNGTU } hoặc { MA_TT, TIEN, THANGTRA (yyyyMMck), GHICHU,CHUNGTU }";



          let columnCount = exceldata1[0].length//Object.keys(exceldata[0]).length
          if(columnCount === 1)
          {
            if(!exceldata1[0].includes('MA_TT'))
            {
              vm.$toast.error('File gồm phải có cột MA_TT')
              document.getElementById('gachno_upload_excel').value= null;
              return false
            }

          } else if(columnCount === 4)
          {
            /*
            if(!exceldata[0].hasOwnProperty('MA_TT')
              || !exceldata[0].hasOwnProperty('TIEN')
              || !exceldata[0].hasOwnProperty('GHICHU')
              || !exceldata[0].hasOwnProperty('CHUNGTU')
            )
            */
           if(!exceldata1[0].includes('MA_TT')
              || !exceldata1[0].includes('TIEN')
              || !exceldata1[0].includes('GHICHU')
              || !exceldata1[0].includes('CHUNGTU')
              )
            {
              vm.$toast.error('File gồm có 4 cột: MA_TT, TIEN, GHICHU, CHUNGTU')
              document.getElementById('gachno_upload_excel').value= null;
              return false
            }
            vkieu  =2
          } else if(columnCount === 5)
          {
            /*
            if(!exceldata[0].hasOwnProperty('MA_TT')
                || !exceldata[0].hasOwnProperty('TIEN')
                || !exceldata[0].hasOwnProperty('THANGTRA')
                || !exceldata[0].hasOwnProperty('GHICHU')
                || !exceldata[0].hasOwnProperty('CHUNGTU')
              )
            */
            if(!exceldata1[0].includes('MA_TT')
              || !exceldata1[0].includes('TIEN')
              || !exceldata1[0].includes('GHICHU')
              || !exceldata1[0].includes('CHUNGTU')
              || !exceldata1[0].includes('THANGTRA')
              )
              {
                vm.$toast.error('File gồm có 5 cột: MA_TT, TIEN, THANGTRA (yyyyMMck), GHICHU, CHUNGTU')
                document.getElementById('gachno_upload_excel').value= null;
                return false
              }
            vkieu  = 3
          } else if(columnCount === 6)
          {
            /*
            if(!exceldata[0].hasOwnProperty('MA_TT')
                || !exceldata[0].hasOwnProperty('TIEN')
                || !exceldata[0].hasOwnProperty('THANGTRA')
                || !exceldata[0].hasOwnProperty('GHICHU')
                || !exceldata[0].hasOwnProperty('CHUNGTU')
                || !exceldata[0].hasOwnProperty('KHOANMUCTT_ID')
              )
            */
            if(!exceldata1[0].includes('MA_TT')
              || !exceldata1[0].includes('TIEN')
              || !exceldata1[0].includes('GHICHU')
              || !exceldata1[0].includes('CHUNGTU')
              || !exceldata1[0].includes('THANGTRA')
              || !exceldata1[0].includes('KHOANMUCTT_ID')
              )
              {
                vm.$toast.error('File gồm có 6 cột: MA_TT, TIEN, THANGTRA (yyyyMMck), GHICHU, KHOANMUCTT_ID')
                document.getElementById('gachno_upload_excel').value= null;
                return false
              }
            vkieu  = 4
          } else
          {
            vm.$toast.error("Cấu trúc file gồm sheet1: { MA_TT } hoặc { MA_TT, TIEN, GHICHU, CHUNGTU } hoặc { MA_TT, TIEN, THANGTRA (yyyyMMck), GHICHU, CHUNGTU} hoặc { MA_TT, TIEN, THANGTRA (yyyyMMck), GHICHU, CHUNGTU, KHOANMUCTT_ID}")
            document.getElementById('gachno_upload_excel').value= null;
            return false
          }

          if(vkieu === 4 && vm.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO)
          {
            vm.$toast.error("Chỉ có nhóm hình thức điều chỉnh nợ mới đc thao tác")
            document.getElementById('gachno_upload_excel').value= null;
            return
          }

          if(vkieu === 4)
          {
            //if (!dsQuyen.Contains(QUYEN_GACH.GACHFILE_KMTT))
            let gridObj = vm.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.$refs.grid.ej2Instances
            gridObj.showColumns(['THANGTRA', 'KHOANMUCTT_ID'])
          }
        } // end of HTTT

        // trim data

        //console.log(exceldata)

        let successData = vm.trimUploadData(exceldata, vkieu)
        //console.log(successData)

        let errorData = vm.checkDuplicateMaTT(successData)
        if(errorData.length > 0)
        {
          //vm.dsErrors = [...errorData]
          vm.dsErrors = cloneArray(errorData)
          vm.$toast.error('Đã phát sinh lỗi khi tải file. Xem chi tiết trong danh sách lỗi.')
          return false
        }
        let postData = vm.checkUploadFileData(vkieu, successData)

        if(postData === null)
        {
          vm.$toast.error('Đã phát sinh lỗi khi tải file. Xem chi tiết trong danh sách lỗi.')
          return
        }
        let arr = []
        if(postData.pDS.length > 0)
        {
          vm.loading(true)
          vm.doCheckTongNoTuFile(postData)
          .then(
            (res) => {
                if(res.data.error_code === 'BSS-00000000')
                {
                  //console.log(res.data)
                  //if(res.data.data.DANH_SACH.length > 0)
                  if(res.data.data.LOI_CHI_TIET === null)
                  {
                    arr = res.data.data.DANH_SACH
                    let dataExcel = []
                    let seri = 0

                    try{
                      seri = parseInt(vm.params.p_so_seri)
                    } catch(err) {
                      vm.$toast.error('Số seri không hợp lệ.')
                      return
                    }

                    arr.forEach( (item, index) => {
                      //let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
                      let ret = successData.filter(x => x.MA_TT === item.MA_TT)
                      //console.log(ret)
                      let thangtra = null
                      let ghichu = ''
                      let chungtu = null
                      let kmtt_id = null
                      let tientra = 0
                      if(ret !== null && ret.length > 0)
                      {
                        thangtra = ret[0].THANGTRA !== undefined && ret[0].THANGTRA !== null ? ret[0].THANGTRA : null
                        ghichu = ret[0].GHICHU !== undefined && ret[0].GHICHU !== null ? ret[0].GHICHU : null
                        chungtu = ret[0].CHUNGTU !== undefined && ret[0].CHUNGTU !== null ? ret[0].CHUNGTU : null
                        kmtt_id = ret[0].KHOANMUCTT_ID !== undefined && ret[0].KHOANMUCTT_ID !== null ? ret[0].KHOANMUCTT_ID : null
                        tientra = ret[0].TIEN !== undefined && ret[0].TIEN !== null ? parseInt(ret[0].TIEN) : item.CONNO
                      }
                      let checked = true
                      if(vm.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO && vm.params.p_dieuchinh_canbang === 0 && tientra === 0)
                      {
                        checked = false
                      }
                      if(vm.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO && item.CONNO === tientra && item.CONNO !== 0)
                        checked = false
                      
                      dataExcel.push(
                        {
                          "THANHTOAN_ID": item.THANHTOAN_ID,
                          "MA_TT": item.MA_TT,
                          "MATB_DD": item.MATB_DD !== undefined ? item.MATB_DD : null,
                          "TEN_TT": item.TEN_TT !== undefined ? item.TEN_TT : null,
                          "TONGNO": item.CONNO,
                          "TAMTHU": item.TAMTHU < 0 ? 0 - item.TAMTHU : item.TAMTHU,
                          "TIENTRA": tientra,
                          "CONLAI": item.CONNO - tientra,
                          "THANGTRA": thangtra,
                          "GHI_CHU": ghichu,
                          "CHUNG_TU": chungtu,
                          "CLKHOANMUC": kmtt_id,
                          "SERI": vm.f_enable_seri ? vm.params.p_seri : '1',
                          "QUYEN": vm.params.p_so_quyen,
                          "SO_SERI": seri + index,
                          "_CHECK": checked
                        }
                      )
                      vm.params.p_so_seri = seri + index
                    })

                    //console.log(dataExcel)
                    vm.dsGachNoDanhSach = dataExcel//[...exceldata]
                    vm.dsGachNoDanhSach = cloneArray(dataExcel)
                    //vm.dsGachNoDanhSachTotals = vm.dsGachNoDanhSach.length
                    vm.tinhTongTienTra()
                  }
                  else
                  {

                    let error = res.data.data.LOI_CHI_TIET

                    if(error !== null || error !== '')
                    {
                      let errors = JSON.parse(error)
                      console.log(errors)
                      vm.dsErrors = []
                      errors.forEach((item) => {
                        vm.dsErrors.push({MA_TT: item.MA_TT, THONGTINLOI: item.ERROR})
                      })
                    }
                    vm.$toast.error('Đã phát sinh lỗi');
                    return null
                  }
                }
            }
          )
          .catch(error => {
            vm.$toast.error(error.data.message_detail)
          })
          .finally(() => {
            vm.loading(false)
          })

        }


      };
      reader.readAsArrayBuffer(f);
      document.getElementById('gachno_upload_excel').value= null;
    },
    compare: function( a, b ) {
      if ( a.MA_TT < b.MA_TT ){
        return -1;
      }
      if ( a.MA_TT > b.MA_TT ){
        return 1;
      }
      return 0;
    },
    checkUploadFileData(kieu, data){
      let errors = []
      this.dsErrors = []

      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pDS: []
      }

      data.forEach((item, index) => {
        let f = true
        if(kieu !== 1)
        {
          try{
            let tien = parseInt(item.TIEN)
            //alert(tien)
            if (isNaN(tien))
            {
              errors.push({MA_TT: item.MA_TT, THONGTINLOI: 'Tiền không hợp lệ'})
              f = false
            }
          }catch(err){
            //errors.push( 'MA_TT ' + item.MA_TT + ' tiền không hợp lệ')
            errors.push({MA_TT: item.MA_TT, THONGTINLOI: 'Tiền không hợp lệ'})
            f = false
          }
        }

        if(kieu === 3 || kieu === 4)
        {
          if(!moment(item.THANGTRA, 'YYYYMMDD').isValid())
          {
            //errors.push( 'MA_TT ' + item.MA_TT + ' có tháng trả không hợp lệ')
            errors.push({MA_TT: item.MA_TT, THONGTINLOI: 'Tháng trả không hợp lệ'})
            f = false
          }

        }
        if(f)
        {
          postData.pDS.push({MA_TT: item.MA_TT})
        }
      })

      if(errors.length > 0)
      {
        //vm.$toast.error(errors.join('\r\n'))
        //this.dsErrors = [...errors]
        this.dsErrors = cloneArray(errors)
        return null
      }


      return postData;

    },
    async doCheckTongNoTuFile(postData){
      try{
        const res = await GachNoAPI.getTongNoTuFile(this.axios, postData)
        return res
      }
      catch(error)
      {
        this.$toast.error(error.data.message_detail)
      }

    },
    doXuatFile: function(){
      this.$refs.appGachNoDanhSach.exportExcel(this.params.dsGachNoDanhSach)
    },
    gachNoTheoFile: function(){
      if (this.params.p_httt_options.length <= 0)
      {
        this.$toast.error("Không có thông tin về hình thức thanh toán");
        return;
      }
      if (this.params.p_thungan_options.length <= 0)
      {
        this.$toast.error("Không có thông tin về thu ngân");
        return;
      }
      if (this.params.p_loaitien_options.length <= 0)
      {
        this.$toast.error("Không có thông tin về loại tiền");
        return;
      }
      this.$refs.refGachNoTheoFile.openDialog()
    },
    doChenNo: function(){
      this.popupChenNo()
      //this.dlg.header = 'Test'
      //this.$refs.dlgExt.show()
      /*
      const AsyncComp = defineAsyncComponent(() =>
        import('@/modules/QLTN/GachNo/components/ChenNo')
      )
      AsyncComp.then(() => {
        this.showDlg = true
        //this.dlg.popupComponent = ()=>import('@/modules/QLTN/GachNo/components/ChenNo')
        console.log(this.$refs.popupComponent)
        console.log(this.$refs.dlgChenNo)
        //this.$refs.popupComponent.$refs.dlgChenNo.openDialog()
      })
      */

    },
    InPhieuChi(postData)
    {
      this.loading(true)
      try{
        const baseUrl = process.env.API;
        //let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
        this.axios.post(baseUrl + "/web-thuno/api/thu-no/in-hoa-don/in-bien-lai-thanh-toan", postData, {responseType: 'blob'})
        .then( (response) => {
          previewPrint(response.data)
        })
        .finally(() => {
          this.loading(false)
        })
      }catch(err)
      {
        this.$toast.error(error.data.message_detail)
      }
    },
    InBienNhan(postData)
    {
      this.loading(true)
      try{
        const baseUrl = process.env.API;
        this.axios.post(baseUrl + "/web-thuno/api/thu-no/gach-no/bien-nhan", postData, {responseType: 'blob'})
        .then( (response) => {
          previewPrint(response.data)
        })
        .finally(() => {
          this.loading(false)
        })
      }catch(err)
      {
        this.$toast.error(error.data.message_detail)
      }
    },
    doInPhieuThu: function(){
      //this.$toast.error('Chức năng in phiếu thu đang được phát triển')
      if(this.params.p_phieu_id === null || this.params.p_phieu_id === 0)
      {
        this.$toast.error('Không có phiếu thu để in.')
        return
      }

      this.dsPhieuCanIn = []
      this.dsPhieuCanIn.push(this.params.p_phieu_id)
      //this.$refs.refPrintPreview.openDialog()
      let postData = {
        kieu: 1,
        kyCuoc: this.params.p_kycuoc,
        dsPhieuId : this.dsPhieuCanIn
      }

      if (this.params.p_nhom_httt === NHOM_HTTT.THOAITRA &&  this.params.p_loaigach !== 4)
      {
        if (!this.params.p_in_bbn_tt)
        {
          postData.kieu = 2
          this.InPhieuChi(postData)
        }
        else
        {
          postData.kieu = 1
          this.InPhieuChi(postData)
        }
          
      }

      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY || this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DAILY)
      {
          if (this.params.p_nhom_httt == NHOM_HTTT.GACHNO_DAILY || !this.params.p_in_bbn_tt )
          {
            let data = {
              kyCuoc: this.params.p_kycuoc,
              dsPhieu : this.dsPhieuCanIn
            }
            this.InBienNhan(data)
          }
          if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_TAIQUAY && this.params.p_in_bbn_tt)
          {
            postData.kieu = 1
            this.InPhieuChi(postData)
            /*
              frmXemBaoCao frmBC = new frmXemBaoCao("PHIEU_BNTT",
                new[]
              {
                  dsPhieuTraID = dsPhieuTraID.Substring(0, dsPhieuTraID.Length - 1), ////vphieu_id
                  chuky + kyhoadon,
                  tt_nd.nhanvien_id.ToString()
              });
              frmBC.XemNgay();
            */
          }
      }

      
      
    },
    async  getViVNPTPayToken(postData)
    {

      try
      {
        const res = await GachNoAPI.getViVNPTPayToken(this.axios)
        const data= await res.data
        //console.log(data)
        if(data.errorCode === 0)
        {
          return data.data
        } else
        {
          this.$toast.error(data.faultString)
          return null
        }
        
      }catch(error)
      {
        console.log(error)
        return null
      }
    },
    async getBalanceViVNPTPay(token)
    {
      /*
      try
      {
        const res = await GachNoAPI.getBalanceViVNPTPay(this.axios)
        const data = await res.data
        console.log(data)
      }catch(error)
      {

      }
      */
      try{
        const baseUrl = process.env.API;
        const headers = {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + this.$auth.getToken(),
          'WalletToken': token
        }
        //let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
        const response = await this.axios.post(baseUrl + "/tichhop/vnptpay/getBalance", null, { headers: headers})
        const data = await response.data
        console.log(data)
        if(data.errorCode === 0)
          return data.data
        else
        {
          this.$toast.error(data.faultString)
          return null
        }
      }catch(err)
      {
        this.$toast.error(error.data.message_detail)
        return null
      }
    },
    async doXemThongTinViVNPTPay()
    {

      const ts = await this.getThamSoViVNPTPay()

      if(ts === null || ts.length <= 0)
      {
        this.$toast.error('Chức năng này chỉ hỗ trợ thanh toán qua ví VNPTPAY tập trung APIGW!')
        return
      }

      this.loading(true)

      const login = await this.getViVNPTPayToken()

      if(login !== null)
      {
        const info = await this.getBalanceViVNPTPay(login.token)
        this.loading(false)

        this.infoViVNPTPay = info
        this.$refs.refThongTinViVNPTPay.openDialog()
      }  else
      {
        this.loading(false)
      }
      
    },
    updateTongTienTraTienDC(total)
    {
      if(this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
      {
        this.params.p_tien_no = total
        this.params.p_tien_dchinh = total
      } else
      {
        this.params.p_tien_no = total
        this.params.p_tien_tra = total
      }
    },
    tinhTongTienTra: function(){
      /*
      if(this.params.p_tab_kieugach === 1 || this.params.p_tab_kieugach === 2)
      {
        let total = this.getTongNo(this.params.p_loaigach)
        this.params.p_tien_no = total
        this.params.p_tien_tra = total
      } else if (this.params.p_tab_kieugach === 3)
      {
        let total = this.getTongNo(this.params.p_loaigach)
        this.params.p_tien_dchinh = total
      }
      */
      let total = 0
      if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
        total = this.getTamThu(this.params.p_loaigach)
      else
        total = this.getTongNo(this.params.p_loaigach)
      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC && total > this.params.p_vtien_vikh && this.params.p_vtien_vikh > 0)
      {
          total = this.params.p_vtien_vikh;
      }
      this.updateTongTienTraTienDC(total)
      //return total
    },
    /* HANDLER */
    onFilteringLuotTT(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('HINHTHUC', 'contains', e.text, true) : query;
      e.updateData(this.params.p_luot_tt_options, query);
    },
    onFilteringThuNgan(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query;
      e.updateData(this.params.p_thungan_options, query);
    },
    onFilteringNganHang(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_NH', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nganhang_options, query);
    },
    chukycuocChangeHandler(e) {
      //console.log(e)
      if(e.p_chuky_no !== null)
      {
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
        this.getThongTinChuKyNo()
        this.getDSLoaiTien()
        this.getSoPhieuTheoUser()
      }
      else
        this.params.p_kycuoc = null
    },
    onThangDChinhChange: function(args) {
      if(args.value === null){
        return
      }
      CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, moment(args.value).format('YYYYMM'))
      .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_chuky_dchinh_options = response.data.data
                this.params.p_chuky_dchinh = response.data.data[0].CHUKY
            }else
            {
              this.params.p_chuky_dchinh_options = [{CHUKY: '01'}]
              this.params.p_chuky_dchinh = '01'
            }
          }
        )
      .catch (function (err) {
        //console.log(err);
        this.params.p_chuky_dchinh_options = [{CHUKY: '01'}]
        this.params.p_chuky_dchinh = '01'
      })
      .finally(() => {
        this.loading(false)
      })
    },
    tabGachNoClick(tab){
      //this.Clear()
      //this.clearResult()

      this.params.p_tab_kieugach = tab
      if(tab === 1) // gạch lẻ
      {
        this.params.p_loaigach = this.$refs.tabGachNoLe.getLoaigachNo()
      }
      else if(tab === 3)  // điều chỉnh
        this.params.p_loaigach = 5
      else if(tab === 2)  // gạch danh sách
      {
        this.params.p_loaigach = 4
        this.tinhTongTienTra()
      }

      /*
      if(tab === 3 && this.resultDieuChinh.length <= 0)
      {
        let dsKC = []
        this.params.p_vthangno.forEach( (value) => {
          let a = value.split(',')
          dsKC.push({chukyno: a[0], ma_tb: a[1]})
        })
        let postDCData = {
            pDanhSach: dsKC,
            pKieu: 2,
            pKyCuoc: this.params.p_kycuoc,
            pMaTT: this.params.p_ma_tt,
            pLoaiTien: 1
        }
        this.getChiTietNoKMDieuChinh(postDCData).then(
          (response) => {
            this.resultDieuChinh = response.data.data
            this.tinhTongNoTabDieuChinh()
          }
        )
      }
      else
        this.tinhTongTienTra()
      */
     this.loadChiTietNo()
    },
    getTTTBHandler(e) {
      this.params.p_ma_tt = e.MA_TT
      this.$refs.frmgachno_p_ma_tt.focus()
    },
    onSomayAccChange(args)
    {
      if (args.e && args.e.type === 'keydown' && args.e.keyCode === 13)
      {
        //args.e.preventDefault();
        this.getThongTinThueBao()
      }

    },

    async loadChiTietNo(){
      if(this.params.p_loaigach === 4)
        return
      let ds = {
        ds_chukyno: this.params.p_vcacchukyno.length > 0 ? this.params.p_vcacchukyno.join(',') : '',
        ds_ma_tb: this.params.p_vcacmatb.length > 0 ? this.params.p_vcacmatb.join(',') : ''
      }
      
      let postData = {
        pDanhSach: null,
        pKieu: this.params.p_kieutra,
        pKyCuoc: this.params.p_kycuoc,
        pMaTT: this.params.p_ma_tt,
        pLoaiTien: 1
     }
      
     //console.log(postData)
     //alert(this.params.p_loaigach)
     if(this.params.p_loaigach === 1)
     {
       postData.pDanhSach = ds
       const response = await this.getChiTietNoTheoKhoanMuc(postData)
       
       if(response.length > 0)
       {
        
        let arr = []
          response.forEach(item => {
            let x = {...item}
            if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
            {
              if(x.KHOANMUCTT_ID === 3130)
              {
                arr.push(x)  
              }
            } else
              arr.push(x)  
            /*
            if(x.KHOANMUCTT_ID === 3130)
            {
              x.TONGSEPHAITRA = 0
              x.TONGSETRA = 0
              x.TONGSETRAPS = 0
              
              if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
              {
                x.TONG_TAMTHU = 0 - x.TONGTHUCTRA
                x.TONGSEPHAITRA = 0 - x.TONGTHUCTRA
              }
              else
              {
                x.TONG_TAMTHU = x.TONGTHUCTRA
                //x.TONGSEPHAITRA = 0 - x.TONGSEPHAITRA
              }
              
              //else if (x.TONG_TAMTHU < 0)
  //                x.TONG_TAMTHU = 0 - x.TONGTHUCTRA
              arr.push(x)
            }else if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
            {
              
                if(x.TONG_TAMTHU !== 0)
                {
                  x.TONGSEPHAITRA = x.TONG_TAMTHU
                  arr.push(x)  
                }
              

            } else
              arr.push(x)
            */
            //arr.push(x)
          })
        

        //this.params.ds_khoanmuc_tmp = cloneArray(response)
        //this.resultGachLeKhoanMuc = cloneArray(response)


        this.params.ds_khoanmuc_tmp = cloneArray(arr)
        this.resultGachLeKhoanMuc = cloneArray(arr)

        this.tinhTongTienTra()  
       }
       
     } else if(this.params.p_loaigach === 2)
     {
      if(ds.ds_chukyno === '')
      {
        
        postData.pKieu = 1
        let response = []
        if(this.params.p_nhom_httt !== NHOM_HTTT.HOANTRA)
        {
          postData.pDanhSach = ds
          response = await this.getChiTietNoTheoChuKyNo1(postData)
        }
        else
        {
          postData.pDanhSach = []
          postData.pDanhSach.push({
            chukyno: '',
            ma_tb: ''
          })
          response = await this.getChiTietNoTheoChuKyNoHoanTra(postData)
          console.log(response)
        }

        if(response.length > 0)
        {
          let arr = []
          response.forEach(item => {
            let x = {...item}
            if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
            {
              if(x.TAMTHU !== 0)
              {
                x.CONNO = x.TAMTHU
              } 
              arr.push(x)                              
            } else
            { 
              if(x.TAMTHU < 0)
              {
                x.TAMTHU = 0 - x.TAMTHU
                //x.DATRA = x.TAMTHU
              }                
              arr.push(x)  
            }            
          })
          this.params.ds_thangno_tmp = cloneArray(arr)
          this.resultGachLeThangNo = cloneArray(arr)
          
        }
      }
      else
      {
        postData.pDanhSach = []
        postData.pKieu = 2
        this.params.p_vthangno.forEach((item) => {
          let t = item.split(',')
          postData.pDanhSach.push({
            chukyno: t[0],
            ma_tb: t[1]
          })
        })

        let response = []
        if(this.params.p_nhom_httt !== NHOM_HTTT.HOANTRA)
          response = await this.getChiTietNoTheoChuKyNo2(postData)
        else
          response = await this.getChiTietNoTheoChuKyNoHoanTra(postData)
        
        if(response.length > 0)
        {
          let arr = []
          response.forEach(item => {
            let x = {...item}
            if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
            {
              if(x.TAMTHU !== 0)
              {
                x.CONNO = x.TAMTHU
                arr.push(x)                    
              }
            } else 
            { 
              if(x.TAMTHU < 0)
              {
                x.TAMTHU = 0 - x.TAMTHU
                x.DATRA = x.TAMTHU
              }
              arr.push(x)  
            }
          })
          this.params.ds_thangno_tmp = cloneArray(arr)
          this.resultGachLeThangNo = cloneArray(arr)
          
        }
      }
       
      this.tinhTongTienTra()
       
     } else if(this.params.p_loaigach === 3)
     {
       postData.pDanhSach = ds
       const response = await this.getChiTietNoTheoDichVu(postData)
       let arr = []

      response.forEach(item => {
          let x = {...item}
          if(this.params.p_nhom_httt === NHOM_HTTT.HOANTRA)
          {
            if(x.TAMTHU !== 0)
            {
              x.CONNO = x.TAMTHU
              arr.push(x)                    
            }
          } else 
          { 
            if(x.TAMTHU < 0)
              x.TAMTHU = 0 - x.TAMTHU
            arr.push(x)  
          }
        })
      this.params.ds_dichvu_tmp = cloneArray(arr)
      this.resultGachLeDichVu = cloneArray(arr)

      this.tinhTongTienTra()
     } else if(this.params.p_loaigach === 5)
     {
       let dsKC = []
        this.params.p_vthangno.forEach( (value) => {
          let a = value.split(',')
          dsKC.push({chukyno: a[0], ma_tb: a[1]})
        })
        let postDCData = {
            pDanhSach: dsKC,
            pKieu: 2,
            pKyCuoc: this.params.p_kycuoc,
            pMaTT: this.params.p_ma_tt,
            pLoaiTien: 1
        }
        this.getChiTietNoKMDieuChinh(postDCData).then(
          (response) => {
            this.resultDieuChinh = response.data.data
            //this.params.ds_dieuchinh_tmp = [...this.resultDieuChinh]
            //this.params.ds_dieuchinh_tmp = this.resultDieuChinh.map(o => ({...o}))
            this.params.ds_dieuchinh_tmp = cloneArray(this.resultDieuChinh)
            this.tinhTongNoTabDieuChinh()
          }
        )
     } else if(this.params.p_loaigach === 4)
     {

     }
      /*
      this.getChiTietNoTheoChuKyNo(postData).then(
        (response) => {
          this.resultGachLeThangNo = response.data.data
          //this.params.ds_thangno_tmp = [...this.resultGachLeThangNo]
          //this.params.ds_thangno_tmp = JSON.parse(JSON.stringify(this.resultGachLeThangNo))
          //this.params.ds_thangno_tmp = response.data.data
          //this.totalItemsThangNo = response.data.data.length
          this.params.ds_thangno_tmp = this.resultGachLeThangNo.map(o => ({...o}))
        }
      ).then( () => {
        this.tinhTongTienTra()
      })

      this.getChiTietNoTheoDichVu(postData).then(
        (response) => {
          this.resultGachLeDichVu = response.data.data
          //this.params.ds_dichvu_tmp = [...this.resultGachLeDichVu]
          this.params.ds_dichvu_tmp = this.resultGachLeDichVu.map(o => ({...o}))
          this.tinhTongTienTra()
        }
      )
      if(this.params.p_tab_kieugach === 3) // đang chọn tab điều chỉnh
      {
        let dsKC = []
        this.params.p_vthangno.forEach( (value) => {
          let a = value.split(',')
          dsKC.push({chukyno: a[0], ma_tb: a[1]})
        })
        let postDCData = {
            pDanhSach: dsKC,
            pKieu: 2,
            pKyCuoc: this.params.p_kycuoc,
            pMaTT: this.params.p_ma_tt,
            pLoaiTien: 1
        }
        this.getChiTietNoKMDieuChinh(postDCData).then(
          (response) => {
            this.resultDieuChinh = response.data.data
            //this.params.ds_dieuchinh_tmp = [...this.resultDieuChinh]
            this.params.ds_dieuchinh_tmp = this.resultDieuChinh.map(o => ({...o}))
            this.tinhTongNoTabDieuChinh()
          }
        )
      }
      */
    },
    acceptChuKyNoHandler(e) {
      console.log(e)

      if (e.matb_hienthi !== "")
      {
        this.params.p_strMathuebao = e.matb_hienthi;
        this.params.p_vcacmatb = e.cacmatb;
        this.params.p_vcacchukyno = e.cacchukyno;
        this.params.p_acc_somay = e.matb_hienthi
        //cboThuebao.Text = obj.matb_hienthi;
        this.params.p_vsochukylchon = e.vsochukylchon
        this.params.p_vthangno = e.cacthangno;

          //kt = true;
      }
      else
        this.params.p_vcacchukyno = []

      if (this.params.p_nhom_httt === NHOM_HTTT.GACHNO_DATCOC)
      {
        this.params.p_vrkm_id = e.ds[0].RKM_ID;
        this.params.p_vtien_vikh = e.ds[0].TIEN_VI
      }

      if(e.ds_chukyno !== '')
      {

      }

      this.loadChiTietNo()

    },

    gachLeKieuGachHandler(index){
      this.params.p_loaigach = index
      this.loadChiTietNo()
      //this.tinhTongTienTra()
    },

    tongConNoHandler: function(args){
      this.params.p_tien_no = args
      this.params.p_tien_tra = args
    },
    updateDatasourceGachNoLe: function(args){
      //console.log(this.params.p_loaigach, args)
      this.params.f_tinhtientra = false
      let tongno = 0
      let tongtra = 0
      if(this.params.p_loaigach === 2) //
      {
        /*
        var item = this.resultGachLeThangNo.find(x => x.CHUKYNO === args.CHUKYNO && x.MA_TB === args.MA_TB);
        if (item) {
          item.CONNO = args.CONNO
        }
        /*
        let gridObj = this.$refs.tabGachNoLe.$refs.gridThangNo.$refs.grid.ej2Instances
        gridObj.aggregateModule.refresh(item);
        */
       let arr = []
        for(let i = 0; i < this.resultGachLeThangNo.length; i++)
        {
          if(this.resultGachLeThangNo[i].THANGNO === args.THANGNO
            && this.resultGachLeThangNo[i].MA_TB === args.MA_TB)
          {
            arr.push(args)
          }
          else
            arr.push(this.resultGachLeThangNo[i])
            //this.resultGachLeThangNo[i].CONNO = args.CONNO
        }
        //console.log(arr)
        //this.resultGachLeThangNo = [...this.resultGachLeThangNo]
        //this.resultGachLeThangNo = [...arr]
        this.resultGachLeThangNo = arr
        this.resultGachLeThangNo.forEach( (x) => {
          tongno += x.CONNO
          tongtra += x.CONNO
        })
      } else if(this.params.p_loaigach === 1)
      {
        let arr = []
        for(let i = 0; i < this.resultGachLeKhoanMuc.length; i++)
        {
          if(this.resultGachLeKhoanMuc[i].KHOANMUCTT_ID === args.KHOANMUCTT_ID
            && this.resultGachLeKhoanMuc[i].MA_TB === args.MA_TB
          )
          {
            //console.log(args.KHOANMUCTT_ID)
            this.resultGachLeKhoanMuc[i].TONGSETRAPS = args.TONGSETRAPS
            //this.resultGachLeKhoanMuc[i].TONGSETRADK = args.TONGSETRADK
            this.resultGachLeKhoanMuc[i].TONGSEPHAITRA = args.TONGSETRAPS + args.TONGSETRADK
          }
          arr.push(this.resultGachLeKhoanMuc[i])

        }

        this.resultGachLeKhoanMuc = arr//[...this.resultGachLeKhoanMuc]
        this.resultGachLeKhoanMuc.forEach( (x) => {
          tongno += x.TONGSEPHAITRA// + x.NOGOCPS
          tongtra += x.TONGSEPHAITRA// + x.NOGOCPS
        })
      } else if(this.params.p_loaigach === 3)
      {
        /*
        let item = this.resultGachLeDichVu.find(x => x.DICHVUVT_ID === args.DICHVUVT_ID);
        if (item) {
          item.TIENTRA = args.TIENTRA
        }
        */
        let arr = []
        for(let i = 0; i < this.resultGachLeDichVu.length; i++)
        {
          if(this.resultGachLeDichVu[i].DICHVUVT_ID === args.DICHVUVT_ID)
          {
            //this.resultGachLeDichVu[i].TIENTRA = args.TIENTRA
            arr.push(args)
          } else
            arr.push(this.resultGachLeDichVu[i])
          //console.log(arr)
        }
        //console.log(this.$refs.tabGachNoLe.$refs)
        /*
        let gridObj = this.$refs.tabGachNoLe.$refs.gridDichVu.$refs.grid.ej2Instances;
        gridObj.aggregateModule.refresh(item);
        */
        this.resultGachLeDichVu = arr//[...this.resultGachLeDichVu]
        this.resultGachLeDichVu.forEach( (x) => {
          tongno += x.TIENTRA
          tongtra += x.TIENTRA
        })
      } else if(this.params.p_loaigach === 4) // gạch danh sách
      {
        if(args !== null)
        {
          let arr = []
        
          for(let i = 0; i < this.dsGachNoDanhSach.length; i++)
          {
            let item = {...this.dsGachNoDanhSach[i]}
            if(this.dsGachNoDanhSach[i].MA_TT === args.MA_TT)
            {
              //this.resultGachLeDichVu[i].TIENTRA = args.TIENTRA
              if (args.TONGNO <= args.TIENTRA)
              {
                item.CONLAI = 0
                item.TAMTHU = args.TIENTRA - args.TONGNO
              }
              else
              {
                item.CONLAI = args.TONGNO - args.TIENTRA
              }
            }
            arr.push(item)
            
            //console.log(arr)
          }
          this.dsGachNoDanhSach = cloneArray(arr)
        }
        
        
        //console.log(this.$refs.tabGachNoLe.$refs)
        /*
        let gridObj = this.$refs.tabGachNoLe.$refs.gridDichVu.$refs.grid.ej2Instances;
        gridObj.aggregateModule.refresh(item);
        */
        this.dsGachNoDanhSach.forEach( (x) => {
          if(x._CHECK)
          {
            tongno += x.TONGNO
            tongtra += x.TIENTRA
          }
          
        })
        console.log(tongno, tongtra)
        /*
        let arr = []
        for(let i = 0; i < this.dsGachNoDanhSach.length; i++)
        {
          if(this.dsGachNoDanhSach[i].MA_TT === args.MA_TT)
          {
            this.dsGachNoDanhSach[i].TIENTRA = args.TIENTRA
            this.dsGachNoDanhSach[i].SERI = args.SERI
            this.dsGachNoDanhSach[i].QUYEN = args.QUYEN
            this.dsGachNoDanhSach[i].SO_SERI = args.SO_SERI
            this.dsGachNoDanhSach[i].GHI_CHU = args.GHI_CHU

            arr.push(args)
          }

        }
        this.dsGachNoDanhSach = arr//[...this.dsGachNoDanhSach]
        */
/*
        let selected = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()
        if(selected.length > 0)
        {
          selected.forEach( (x) => {
            tongno += x.TIENTRA
            tongtra += x.TIENTRA
          })
        }
*/
        //console.log(this.$refs.tabGachNoLe.$refs)
        /*
        let gridObj = this.$refs.tabGachNoLe.$refs.gridDichVu.$refs.grid.ej2Instances;
        gridObj.aggregateModule.refresh(item);
        */

      }
      /*
      if(this.params.p_tab_kieugach !== 3)
      {
        this.params.p_tien_no = tongno
        this.params.p_tien_tra = tongtra
      } else
      {
        this.params.p_tien_dchinh = tongtra
      }
      */
     if(this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
     {
       this.params.p_tien_dchinh = tongtra
     } else
     {
        this.params.p_tien_no = tongno
        this.params.p_tien_tra = tongtra
     }

    },
    updateDatasourceDieuChinh: function(args){
      //console.log(args)
      //let tongno = 0
      //let tongtra = 0
      let tong_dc = 0

      for(let i = 0; i < this.resultDieuChinh.length; i++)
      {
        if(this.resultDieuChinh[i].KHOANMUCTT_ID === args.KHOANMUCTT_ID)
        {
          this.resultDieuChinh[i].DC_NOGOC = args.DC_NOGOC
          this.resultDieuChinh[i].DC_THUE = args.DC_THUE
          this.resultDieuChinh[i].DC_HOAHONG = args.DC_HOAHONG
        }
        this.resultDieuChinh[i].DC_TONG = this.resultDieuChinh[i].DC_NOGOC + this.resultDieuChinh[i].DC_THUE  +this.resultDieuChinh[i].DC_HOAHONG

      }
      this.resultDieuChinh = [...this.resultDieuChinh]
      this.resultDieuChinh.forEach( (x) => {
        //tongno += x.DC_NOGOC + x.DC_THUE + x.DC_HOAHONG
        //tongtra += x.DC_NOGOC + x.DC_THUE + x.DC_HOAHONG
        tong_dc += x.DC_TONG
      })

      //this.params.p_tien_no = tongno
      ///this.params.p_tien_tra = tongtra
      this.params.p_tien_dchinh = tong_dc
    },

    tinhTongNoTabDieuChinh: function(){
      let totalNo = 0
      let totalDC = 0
      this.resultDieuChinh.forEach( (item, index) => {
        totalNo += parseInt(item.TONGNO)
        totalDC += parseInt(item.DC_TONG)
      })
      this.params.p_tien_no = totalNo
      this.params.p_tien_dchinh = totalDC
    },

    async gachNoLe(postData){
      try{
        const results = await GachNoAPI.gachNoLeTienMat(this.axios, postData);
        const data = await results.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        //console.log(err)
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },
    async gachNoTienMatDanhSach(postData){
      try{
        const results = await GachNoAPI.gachNoDSTienMat(this.axios, postData);
        return results
      }catch(err) {
        this.$toast.error(err.data.message_detail)
      }
    },
    async gachNoLeDaiLy(postData){
      try{
        const results = await GachNoAPI.gachNoLeDaiLy(this.axios, postData);
        const data = await results.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },
    async dieuchinhNo(postData){
      try{
        const results = await GachNoAPI.dieuchinhNo(this.axios, postData);
        return results
      }catch(err) {
        //console.log(err)
        this.$toast.error(err.data.message_detail)
      }
    },
    async dieuchinhNoLe(postData){
      try{
        const results = await GachNoAPI.dieuchinhNoLe(this.axios, postData);
        return results
      }catch(err) {
        //console.log(err)
        this.$toast.error(err.data.message_detail)
      }
    },
    async dieuchinhNoDS(postData){
      try{
        const results = await GachNoAPI.dieuchinhNoDanhSach(this.axios, postData);
        return results
      }catch(err) {
        this.$toast.error(err.data.message_detail)
      }
    },
    /*
    async dieuchinhNoDS(postData){
      try{
        const results = await GachNoAPI.dieuchinhNoDanhSach(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },
    */
    async gachNoLeNganHang(postData){
      try{
        const results = await GachNoAPI.gachNoLeNganHang(this.axios, postData);
        const data = await results.data

        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },

    async gachNoLeDatCoc(postData){
      try{
        const results = await GachNoAPI.gachNoLeDatCoc(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },


    async gachNoLeThoaiTra(postData){
      try{
        const results = await GachNoAPI.gachNoLeThoaiTra(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },

    async gachNoDSThoaiTra(postData){
      try{
        const results = await GachNoAPI.gachNoDSThoaiTra(this.axios, postData);
        return results
      }catch(err) {
        //console.log(err)
        this.$toast.error(err.data.message_detail)
      }
    },

    async gachNoLeHoanTraTamThu(postData){
      try{
        const results = await GachNoAPI.gachNoLeHoanTraTamThu(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },
    async gachNoDSHoanTraTamThu(postData){
      try{
        const results = await GachNoAPI.gachNoDSHoanTraTamThu(this.axios, postData);
        return results
      }catch(err) {
        //console.log(err)
        this.$toast.error(err.data.message_detail)
      }
    },

    async gachNoLeThanhLy(postData){
      try{
        const results = await GachNoAPI.gachNoLeThanhLy(this.axios, postData);
        const data = await results.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data : null
      }catch(err) {
        if(err.data !== undefined && err.data.message_detail !== undefined)
          this.$toast.error(err.data.message_detail)
        else
          this.$toast.error(err)
        return null
      }
    },

    async gachNoThanhLyDanhSach(postData){
      try{
        const results = await GachNoAPI.gachNoThanhLyDanhSach(this.axios, postData);
        return results
      }catch(err) {
        this.$toast.error(err.data.message_detail)
      }
    },

    getSoPhieuTheoUser: function(){
      let postData = {
        pKyCuoc: this.params.p_kycuoc,
        pNhomHTTTId: this.params.p_nhom_httt,
        pNgayTT: moment(this.params.p_ngay_ttoan).format('DD/MM/YYYY'),
        pNguoiDungId: this.$auth.getNguoiDungID()
      }
      try{
        GachNoAPI.getTongPhieuTheoUser(this.axios, postData)
        .then(
          (res) => {
            //console.log(res)
            if(res.data.error_code === "BSS-00000000"
             && res.data.data !== undefined
             && res.data.data.length > 0)
             {
               this.params.p_tongtra = parseInt(res.data.data[0].TONGTRA)
               this.params.p_tongtien = parseInt(res.data.data[0].TONGTRA)
               this.params.p_tongphieu = parseInt(res.data.data[0].TONGPHIEU)
               this.params.p_so_phieu = this.params.p_tongphieu
             }
          }
        )
      } catch(err) {
        //console.log(err)
      }

    },

    async getDanhSachNhanVienQLTheoDaiLy(daily_id){
      try{
        const res = await CommonsAPI.getNhanVienQLTheoNguoiDung(this.axios, daily_id)
        return res
      } catch(err) {
        //console.log(err)
      }
    },
    getDSGachNo: function(type){
      if(type === 1)
        return this.resultGachLeKhoanMuc
      else if(type === 2)
        return this.resultGachLeThangNo
      else if(type === 3)
        return this.resultGachLeDichVu
      else if(type === 5)
        return this.resultDieuChinh
      else if(type === 4)
      {
        //return this.dsGachNoDanhSach
        let ds = this.dsGachNoDanhSach.filter(x => x._CHECK === true)
        return ds
      }
        
      return []
    },
    getTamThu(loai)
    {
      let tongConNo = 0
      if(loai === 2) {// tháng
        this.resultGachLeThangNo.forEach( (item, index) => {
          tongConNo += parseInt(item.TAMTHU)
        })
      } else if(loai === 1) // khoản mục
      {
        this.resultGachLeKhoanMuc.forEach( (item, index) => {
          if(item.KHOANMUCTT_ID === 3130)
            tongConNo += parseInt(item.TONGSEPHAITRA)//parseInt(item.NOGOCPS) + parseInt(item.TONGNODK)
        })
      } else if(loai === 3) // dịch vụ
      {
        this.resultGachLeDichVu.forEach( (item, index) => {
          tongConNo += parseInt(item.TAMTHU)
        })
      } else if (loai === 4)  // gạch danh sách
      {
        this.dsGachNoDanhSach.forEach((item) => {
          if(item._CHECK)
          {
            tongConNo += item.TAMTHU
          }
        })
      }
      return tongConNo
    },
    getTongNo: function(loai) {
      let tongConNo = 0
      if(loai === 2) {// tháng
        this.resultGachLeThangNo.forEach( (item, index) => {
          tongConNo += parseInt(item.CONNO)
        })
      } else if(loai === 1) // khoản mục
      {
        this.resultGachLeKhoanMuc.forEach( (item, index) => {
          //if(item.KHOANMUCTT_ID)
          tongConNo += parseInt(item.TONGSEPHAITRA)//parseInt(item.NOGOCPS) + parseInt(item.TONGNODK)
        })
      } else if(loai === 3) // dịch vụ
      {
        this.resultGachLeDichVu.forEach( (item, index) => {
          tongConNo += parseInt(item.TIENTRA)
        })
      } else if (loai === 4)  // gạch danh sách
      {
        //let selected = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()
        //console.log(selected)
        /*
        if(selected.length > 0)
          selected.forEach( (item, index) => {
            tongConNo += item.TIENTRA
          })
        */
        this.dsGachNoDanhSach.forEach((item) => {
          if(item._CHECK)
          {
            tongConNo += item.TIENTRA
          }
        })
      } else if(loai === 5) // điều chỉnh
      {
        this.resultDieuChinh.forEach( (item, index) => {
          tongConNo += parseInt(item.DC_TONG)
        })
      }
      return tongConNo
    },
    async chonNVTCHandler(e){
      console.log(e)
      let vdaily = e.NHANVIEN_ID

      this.params.p_daily = e.MANV_TC;
      let nv_quanly = " - NV Quản lý: ";
      let v_ten_nvql = ''
      let v_so_dt = ''
      let res = await this.getDanhSachNhanVienQLTheoDaiLy(vdaily)

      if(res.data.error_code === 'BSS-00000000'
        && res.data.data !== undefined
        && res.data.data.length > 0)
      {
        v_ten_nvql = res.data.data[0].MA_NV + '-' + res.data.data[0].TEN_NV
        v_so_dt = res.data.data[0].SO_DT === null ? '' : res.data.data[0].SO_DT
      }

     this.params.p_daily = "Tuyến thu: " + e.MA_TUYENTHU + " - Đại lý: " + e.MANV_TC + nv_quanly + ': ' + v_ten_nvql + " - " + v_so_dt;
    },
    onTienTraChange(e){

      console.log(e)

      if(!this.params.f_tinhtientra)
        return

      
      let value = 0

      try{
        value = parseInt(e.value)
      }catch(err)
      {
        return false
      }
      //console.log(value)
      this.tinhTienTra(value)

    },
    tinhTienTra(tongtientra)
    {
      let ds = cloneArray(this.getDSGachNo(this.params.p_loaigach)) //[...this.getDSGachNo(this.params.p_loaigach)]
      
      let tongtra = 0
      let vtongno = tongtientra
      let kiemtra = true
      let tmp = 0

      if(this.params.p_loaigach === 2) // gạch theo tháng
      {
        for (let i = 0; i < ds.length; i++)
        {
            tongtra = this.params.ds_thangno_tmp[i].CONNO;
            //Nếu tiền của row đầu tiên < tiền tổng cộng (thangno) => Fill hết tiền vào row đó
            // Các row còn lại reset về 0
            if (vtongno === 0 || !kiemtra)
            {
                //reset các row còn lại về 0
                ds[i].CONNO = 0
            }
            else
            {
                if (tongtra >= vtongno)
                {
                    ds[i].CONNO = vtongno
                    tmp = i;
                    kiemtra = false;
                    vtongno = 0;
                }
                else
                {
                    //Trường hợp vtongno > tiền của row hiện tại
                    ds[i].CONNO = this.params.ds_thangno_tmp[i].CONNO
                    vtongno = vtongno - tongtra;
                }
            }
        }
        //Nếu duyệt hết gridview vẫn còn tiền tổng nợ, thì add tiền vào row đầu tiên
        if (vtongno != 0)
            ds[0].CONNO = ds[0].CONNO + vtongno;
        //this.resultGachLeThangNo = ds.slice(0)
        this.resultGachLeThangNo = [...ds]
      } else if(this.params.p_loaigach === 1) // gạch theo khoản mục
      {
        for (let i = 0; i < ds.length; i++)
        {
          tongtra = this.params.ds_khoanmuc_tmp[i].TONGSEPHAITRA;
          if (vtongno === 0 || !kiemtra)
          {
              //reset các row còn lại về 0
              ds[i].TONGSEPHAITRA = 0
              //ds[i].TONGSETRAPS = this.params.ds_khoanmuc_tmp[i].TONGSETRAPS
          } else
          {
            if (tongtra >= vtongno)
            {
                ds[i].TONGSEPHAITRA = vtongno
                tmp = i;
                kiemtra = false;
                vtongno = 0;
            } else
            {
                //Trường hợp vtongno > tiền của row hiện tại
                ds[i].TONGSEPHAITRA = this.params.ds_khoanmuc_tmp[i].TONGSEPHAITRA
                
                vtongno = vtongno - tongtra;
            }
          }
        }
        /*
        for (let i = 0; i < ds.length; i++)
        {
            tongtra = this.params.ds_khoanmuc_tmp[i].TONGSEPHAITRA;
            //tongtra = this.params.ds_khoanmuc_tmp[i].TONGSETRADK + this.params.ds_khoanmuc_tmp[i].TONGSETRAPS
            //Nếu tiền của row đầu tiên < tiền tổng cộng (thangno) => Fill hết tiền vào row đó
            // Các row còn lại reset về 0
            if (vtongno === 0 || !kiemtra)
            {
                //reset các row còn lại về 0
                //ds[i].TONGSEPHAITRA = 0
                //ds[i].TONGSETRAPS = this.params.ds_khoanmuc_tmp[i].TONGSETRAPS
            }
            else
            {
                if (tongtra >= vtongno)
                {
                    ds[i].TONGSEPHAITRA = vtongno
                    tmp = i;
                    kiemtra = false;
                    vtongno = 0;
                }
                else
                {
                    //Trường hợp vtongno > tiền của row hiện tại
                    ds[i].TONGSEPHAITRA = this.params.ds_khoanmuc_tmp[i].TONGSEPHAITRA
                    
                    vtongno = vtongno - tongtra;
                }
            }
        }
        //Nếu duyệt hết gridview vẫn còn tiền tổng nợ, thì add tiền vào row đầu tiên
        if (vtongno != 0)
        {
          //ds[ds.length-1].TONGSEPHAITRA = ds[ds.length-1].TONGSEPHAITRA + vtongno;
          ds[ds.length-1].TONGSETRAPS = ds[ds.length-1].TONGSETRAPS + vtongno
          ds[ds.length-1].TONGSEPHAITRA = ds[ds.length-1].TONGSEPHAITRA + vtongno
          
          /*
          ds.push({
            KHOANMUCTT_ID: 25
            TIENTRA_DK: 0
            TIENTRA_PS: 0
          })
          */
         /*
         let f = ds.filter(x => x.KHOANMUCTT_ID === 3130)
         if(f.length <= 0)
         {
           ds.push({
             BCCDK: 0,
              BCCPS: 0,
              CONGDC: 0,
              CONGTRA: 0,
              CONGTRAQD: 0,
              DATRADK: 0,
              DATRAPS: 0,
              DONVI_TT_ID: ds[0].DONVI_TT_ID,
              DVQLDK: 0,
              DVQLPS: ds[0].DVQLPS,
              HHONGNODK: 0,
              HHONGNOPS: 0,
              KHOANMUCTT_ID: 3130,
              LOAITIEN_ID: ds[0].LOAITIEN_ID,
              NOGOCDK: 0,
              NOGOCPS: 0,
              NOTHUEDK: 0,
              NOTHUEPS: 0,
              SECHIHHDK: 0,
              SECHIHHPS: 0,
              SETRADK: 0,
              SETRAPS: 0,
              SETRATHUEDK: 0,
              SETRATHUEPS: 0,
              TENKHOANMUC: "Cước tạm thu",
              TONGDACHIHH: 0,
              TONGDACHIHHQD: 0,
              TONGDADCHH: 0,
              TONGNODK: 0,
              TONGNOHH: 0,
              TONGNOPS: 0,
              TONGNOTHUCTE: 0,
              TONGSECHIHH: 0,
              TONGSEPHAITRA: 0,
              TONGSETRA: 0,
              TONGSETRADK: 0,
              TONGSETRAPS: vtongno,
              TONGTHUCDC: 0,
              TONGTHUCTRA: 0,
              TONGTHUCTRAQD: 0,
              TONG_TAMTHU: vtongno,
           })
           
         }
          
        }
        */
        
        //ds[ds.length - 1].TONGSEPHAITRA = vtongno

        console.log(ds)

        this.resultGachLeKhoanMuc = cloneArray(ds)//ds.slice(0)
      } else if(this.params.p_loaigach === 3) // gạch theo dịch vụ
      {
        for (let i = 0; i < ds.length; i++)
        {
            tongtra = this.params.ds_dichvu_tmp[i].TIENTRA;
            //Nếu tiền của row đầu tiên < tiền tổng cộng (thangno) => Fill hết tiền vào row đó
            // Các row còn lại reset về 0
            if (vtongno === 0 || !kiemtra)
            {
                //reset các row còn lại về 0
                ds[i].TIENTRA = 0
            }
            else
            {
                if (tongtra >= vtongno)
                {
                    ds[i].TIENTRA = vtongno
                    tmp = i;
                    kiemtra = false;
                    vtongno = 0;
                }
                else
                {
                    //Trường hợp vtongno > tiền của row hiện tại
                    ds[i].TIENTRA = this.params.ds_dichvu_tmp[i].TIENTRA
                    vtongno = vtongno - tongtra;
                }
            }
        }
        //Nếu duyệt hết gridview vẫn còn tiền tổng nợ, thì add tiền vào row đầu tiên
        if (vtongno != 0)
            ds[0].TIENTRA = ds[0].TIENTRA + vtongno;
        this.resultGachLeDichVu = ds.slice(0)
      }
    },
    onNgayTTChange(e)
    {
      this.getSoPhieuTheoUser()
    },
    getTinhThuHo(){
      let ret = this.params.p_matinh_thuho_options.filter(x => x.TINHTHANH_ID === this.params.p_matinh_thuho)
      if(ret.length > 0)
        return ret[0]
      return null
    },
    Lay_DS_MaTT(){
      let dsma_tt = "";
      if (this.params.p_loaigach === 4)
      {
          let selected = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.getSelectedRecords()
          let ds = []
          if(selected.length <= 0)
          {
            this.$toast.error('Chưa chọn trong danh sách gạch nợ.')
            return ""
          }
          selected.forEach((item) => {
            ds.push("'" + item.MA_TT + "'")
          })
          dsma_tt = ds.join(',')
      }
      else
      {
          dsma_tt = "'" + this.params.p_ma_tt + "'";
      }
      return dsma_tt;
    },
    countTimer() {
      if(this.params.p_request_id > 0) {
        this.params.counterInterval =  setInterval(
          function()
          {
            if(this.params.p_request_id > 0)
            {
              this.params.timer += 1
              if(this.params.timer%10 === 0)
              {
                this.checkDSDaGach(this.params.p_request_id)
              }
            }
          }.bind(this), 1000);

      }
    },
    checkDSDaGach(id){
      GachNoAPI.checkDSDaGach(this.axios, {pId: id})
        .then(
          (res) => {
            if(
              res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0
            )
            {

              if(res.data.data[0].TRANG_THAI === 3)
              {
                if(res.data.data[0].TONGSO_THANHCONG === res.data.data[0].TONGSO_YEUCAU)
                  this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG  + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                else
                {
                  this.$toast.error('Gạch nợ đã phát sinh lỗi. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG  + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  this.dsErrors = []
                  let ds = []
                  res.data.data[0].DS_LOI.forEach((item, index) => {
                    ds.push({ MA_TT: item.MA_TT, THONGTINLOI: item.LOI})
                  })

                  this.dsErrors = [...ds]
                }
                  
                //this.thong_bao = 'Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG  + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')'
                this.params.p_request_id = 0
                clearInterval( this.params.counterInterval )
                this.getSoPhieuTheoUser()
                this.loading(false)
                //clearInterval(this.params.refreshIntervalId)
                //this.params.refreshIntervalId = null
              } else if(res.data.data[0].TRANG_THAI === 2)
              {
                /*
                this.$toast.error('Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG  + '/' + res.data.data[0].TONGSO_MA_GACHNO + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI)
                this.getDSDaGach(this.params.p_request_id)
                */
                clearInterval( this.params.counterInterval )
                this.getSoPhieuTheoUser()
                this.loading(false)
              }
              else
              {
                this.$toast.success('Đã gạch (' + res.data.data[0].TONGSO_THANHCONG  + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
              }
            }
          }
        )
    },
    getDSDaGach(id){
      GachNoAPI.getDSDaGach(this.axios, {pId: id})
        .then(
          (res) => {
            if(
              res.data.error_code === 'BSS-00000000'
              && res.data.data !== undefined
              && res.data.data.length > 0
            )
            {
              this.dsErrors = []
              let ds = []
              res.data.data.forEach((item, index) => {
                ds.push({ MA_TT: item.MA_TT, THONGTINLOI: item.ERROR})
              })

              this.dsErrors = [...ds]
            }
          }
        )
    },
    acceptChonMaThanhToan(e)
    {
      //console.log(e)
      this.params.p_ma_tt = e.MA_TT
      this.showThongTinTT(this.ds_thanhtoan, 2)

      if(this.params.p_loaigach === 4) // gạch theo danh sách
      {
        let ds = this.ds_thanhtoan.filter(x => x.MA_TT === e.MA_TT)
        //console.log(ds)

        this.loadGachTheoDanhSach(ds)
      } else
      {
        //this.params.p_ds_tongno = response.data.data
        this.$refs.refCacKhoanNo.openDialog()
      }

    },
    onTraCuuDSThanhToanHandler(e)
    {
      console.log(e)
      this.NAP_DS(e.ds_chon, e.dschonthang, e.dsthang, e.dschonkm, e.dskmtt)
    },
    NAP_DS(ds_chon, dsthang, thang, ds_km, khoanmuc_tt)
    {
      let objGrid = this.$refs.appGachNoDanhSach.$refs.gridGachDanhSach.$refs.grid.ej2Instances
      let columns_ds = objGrid.columns
      //console.log(columns_ds)
      //console.log(ds_chon, dsthang, thang, ds_km, khoanmuc_tt)
      let ds_file = [...this.dsGachNoDanhSach]
      if(ds_file.length > 0)
      {
        //#region ẩn hiện cột tháng trả
        let f = columns_ds.filter(x => x.foreignKeyField === 'THANGTRA')
        if (f.length > 0 && dsthang === 0)
        {
          this.$toast.error("Bạn chưa chọn tìm theo tháng không thể gạch nợ")
          return;
        }
        else if (dsthang === 1 && f.length <= 0)
        {
          this.$toast.error("Bạn không thể gạch theo tháng nếu danh sách dưới chưa đọc file theo tháng")
          return;
        }
        if (dsthang === 1 && f.length > 0)
        {
          let t = ds_chon.filter(x => x.THANGTRA === null)
          if (t.length > 0)
          {
            this.$toast.error("Bạn chưa chọn tìm theo tháng không thể gạch nợ");
            return;
          }
          t = ds_file.filter(x => x.THANGTRA === null)
          if (t.length > 0)
          {
            this.$toast.error("Danh sách chưa có mã chưa có tháng trả bạn không thể chọn gạch theo tháng");
            return;

          }
        }
        if (dsthang === 1)
          columns_ds.filter(x => x.foreignKeyField === 'THANGTRA').forEach(y => y.visible = true)
        else
          columns_ds.filter(x => x.foreignKeyField === 'THANGTRA').forEach(y => y.visible = false)
        //#endregion
        //#region Ẩn hiện cột khoản mục
        f = columns_ds.filter(x => x.foreignKeyField === 'KHOANMUCTT_ID')
        /*
        if (f.length > 0 && !ds_km && CLKHOANMUC.Visible)
        {
            Message_Box.ShowWarning("Bạn chưa chọn tìm theo khoản mục không thể gạch nợ");
            return;
        }
        else if (ds_km && !columns_ds.Contains("KHOANMUCTT_ID") && !CLKHOANMUC.Visible)
        {
            Message_Box.ShowWarning("Bạn không thể gạch theo khoản mục nếu danh sách dưới chưa đọc file theo khoản mục");
            return;
        }
        if (ds_km && columns_ds.Contains("KHOANMUCTT_ID"))
        {
            if (ds_chon.Select("KHOANMUCTT_ID is null").Length > 0)
            {
                Message_Box.ShowWarning("Bạn chưa chọn tìm theo khoản mục không thể gạch nợ");
                return;
            }
            if (ds_file.Select("KHOANMUCTT_ID is null").Length > 0)
            {
                Message_Box.ShowWarning("Danh sách chưa có mã chưa có khoản mục bạn không thể chọn gạch theo khoản mục");
                return;

            }
        }
        if (ds_km && nhom_httt_id == NHOM_HTTT.DIEU_CHINH_NO)
            CLKHOANMUC.Visible = true;
        else
            CLKHOANMUC.Visible = false;
        #endregion
        */

        
      } // end of ds_file.length > 0
      else
      {
        
        if (dsthang === 1)
        {
          let f = ds_chon.filter(x => x.THANGTRA === null)
          if(f.length > 0)
          {
            this.$toast.error('Bạn chưa chọn tìm theo tháng không thể gạch nợ')
            return
          }
          columns_ds.filter(x => x.foreignKeyField === 'THANGTRA').forEach(y => y.visible = true)
        }
        else 
          columns_ds.filter(x => x.foreignKeyField === 'THANGTRA').forEach(y => y.visible = false)

        if (ds_km === 1 && this.params.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
        {
          let f = ds_chon.filter(x => x.KHOANMUCTT_ID === null)
          if(f.length > 0)
          {
            this.$toast.error('Bạn chưa chọn tìm theo khoản mục không thể gạch nợ')
            return
          }
          //columns_ds.filter(x => x.foreignKeyField === 'CLKHOANMUC').forEach(y => y.visible = true)
        }
        //else
          //columns_ds.filter(x => x.foreignKeyField === 'CLKHOANMUC').forEach(y => y.visible = false)
        
      }

      let f = ds_chon.filter(x => x.KHOANMUCTT_ID !== null)

      if (f.length > 0 && ds_km === 0)
      {
        this.$toast.error("Bạn đang không chọn tìm kiếm theo khoản mục nên không thể thực hiện");
        return;
      }
      /*
      if (!dsQuyen.Contains(QUYEN_GACH.GACHFILE_KMTT) && ds_chon.Select("KHOANMUCTT_ID is not null").Length > 0 && ds_km)
      {
          Message_Box.ShowWarning("Bạn không có quyền gạch nợ điều chỉnh nợ theo khoản mục");
          return;
      }
      */
      //#region ADD danh sách lưới
      if (this.dsGachNoDanhSach !== null && this.dsGachNoDanhSach.length > 0)
      {
          if (dsthang === 1)
          {
            let f = columns_ds.filter(x => x.foreignKeyField === 'THANGTRA')
            if (f.length <= 0 && !f[0].visible)
            {
              columns_ds.filter(x => x.foreignKeyField === 'THANGTRA').forEach(y => y.visible = true)
            }
          }
          if (ds_km === 1 && this.p_nhom_httt === NHOM_HTTT.DIEU_CHINH_NO)
          {
            let f = columns_ds.filter(x => x.foreignKeyField === 'KHOANMUCTT_ID')
            if (f.length <= 0 && !f[0].visible)
            {
              columns_ds.filter(x => x.foreignKeyField === 'KHOANMUCTT_ID').forEach(y => y.visible = true)
            }
          }
          ds_chon.forEach(item => {
            if(dsthang === 0)
            {
              let t = this.dsGachNoDanhSach.filter(x => x.MA_TT === item.MA_TT)
              if(t.length > 0)
              {
                this.dsErrors.push({
                  MA_TT: item.MA_TT,
                  LOI: "Trùng mã thanh toán"
                })
              }
            } else if (dsthang === 1 && ds_km === 0)
            {
              let t = this.dsGachNoDanhSach.filter(x => x.MA_TT === item.MA_TT && x.THANGTRA === item.THANGTRA)
              if (t.length > 0)
              {
                this.dsErrors.push({
                  MA_TT: item.MA_TT,
                  LOI: "Trùng mã thanh toán và tháng trả"
                })
              }
            } else if(dsthang === 1 && ds_km === 1)
            {
              let t = this.dsGachNoDanhSach.filter(x => x.MA_TT === item.MA_TT && x.THANGTRA === item.THANGTRA && x.KHOANMUCTT_ID === item.KHOANMUCTT_ID)
              if (t.length > 0)
              {
                this.dsErrors.push({
                  MA_TT: item.MA_TT,
                  LOI: "Trùng mã thanh toán và tháng trả và khoản mục"
                })
              }
            }
          })
          
      }
      else
      {
        /*
          var dt_copy = ds_chon.Copy();
          dt_copy.Columns.Remove("ISCHECK");
          dt_copy.Columns.Remove("STK");
          dt_copy.Columns.Remove("HINHTHUC_TT");
          dt_copy.Columns.Remove("DIACHI_CT");
          dt_copy.Columns.Remove("CHUKYNO");
          dt_copy.Columns.Remove("NODK");
          dt_copy.Columns.Remove("NOPS");
          dt_copy.Columns.Add("CHON", typeof(bool));
          dt_copy.Columns.Add("TAMTHU_GOC", typeof(decimal));
          for (int i = 0; i < dt_copy.Rows.Count; i++)
          {
              if (dt_copy.Rows[i]["TONGNO"].ToString().Trim() == dt_copy.Rows[i]["TIENTRA"].ToString().Trim()
                    && dt_copy.Rows[i]["TONGNO"].ToString().Trim() == "0")
                  dt_copy.Rows[i]["CHON"] = false;
              else
                  dt_copy.Rows[i]["CHON"] = true;
              dt_copy.Rows[i]["TAMTHU_GOC"] = dt_copy.Rows[i]["TAMTHU"];

          }

          ds_Gachds = new DataSet();
          dt_copy.TableName = "danhsach";
          ds_Gachds.Tables.Add(dt_copy);

          grid_GachDS.DataSource = ds_Gachds.Tables[0];
          gridview_GachDS.BestFitColumns();
        */
        //this.dsGachNoDanhSach = [...ds_chon]
        let arr = []
        ds_chon.forEach(item => {
          arr.push({
            _CHECK: true,
            MA_TT: item.MA_TT,
            MATB_DD: item.MATB_DD,
            TEN_TT: item.TEN_TT,
            TONGNO: item.TONGNO,
            TIENTRA: item.TIENTRA,
            TAMTHU: item.TAMTHU,
            CONLAI: item.CONLAI,
            SERI: item.SERI,
            QUYEN: item.QUYEN,
            SO_SERI: item.SO_SERI,
            CHUNG_TU: '',
            GHI_CHU: item.GHI_CHU,
            THANGTRA: null,
            KHOANMUCTT_ID: item.KHOANMUCTT_ID,
            DIACHI_TT: item.DIACHI_TT,
            DIACHI_BC: item.DIACHI_BC,
            MA_TUYEN: item.MA_TUYEN,
            MANV_TC: item.MANV_TC,
            TEN_NV: item.TEN_NV
          })
        })
        console.log(arr)
        this.dsGachNoDanhSach = [...arr]
        this.updateDatasourceGachNoLe(null)
      }

      objGrid.refreshColumns()
    },

    async TimKiemMaVach(){
      if(this.params.p_chk_ma_tt === '1') // mã vạch
      {
        if(this.params.p_ma_tt === null || this.params.p_ma_tt.trim() === '')
        {
          this.$toast.error('Chưa nhập mã vạch.')
          return
        }
        try{
          this.loading(true)
            await GachNoAPI.getThanhToanTheoMaVach(this.axios, this.params.p_ma_tt)
              .then((res) => {
                if(res.data.error_code === 'BSS-00000000'
                  && res.data.data !== undefined
                  && res.data.data.length > 0)
                {
                  this.params.p_ma_tt = res.data.data[0].MA_TT
                  return 1
                } else
                {
                  this.$toast.error("Không tìm thấy mã thanh toán từ mã vạch " + this.params.p_ma_tt + " (" + this.params.p_kycuoc + ")")
                  return 0
                }
              })
              .finally(() => {
                this.loading(false)
              })
        }catch(error)
        {
          console.log(error)
          this.$toast.error(error.data.message_detail)
          this.loading(false)
          return 0
        }
      }
      return 1
    },
    closeGachTheoFileHandler(args)
    {
      this.loadThongTinSeri()
      this.getSoPhieuTheoUser()
    },
    gachDSelectRecordHandler(args)
    {
      //this.dsGachNoDanhSach.find(x => x.MA_TT === args.MA_TT)._CHECK = !args._CHECK
      let arr = []
      this.dsGachNoDanhSach.forEach((item) => {
        let x = {...item}
        if(item.MA_TT === args.MA_TT)
          x._CHECK = !args._CHECK
        arr.push(x)
      })
      this.dsGachNoDanhSach = [...arr]

      this.updateDatasourceGachNoLe(args)


    },
    getDataFromStorage(key)
    {
      try{
        //console.log(localStorage.getItem(key))
        return localStorage.getItem(key)

      }catch(error)
      {
        //console.log(error)
        return null
      }

    },
    setDataToStorage(key, value)
    {
      try {
        localStorage.setItem(key, value);
        return true;
      } catch (error) {
        return false;
      }
    },
    async KiemTra_ThanhLyNo()
    {
      try
      {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'BATBUOC_THUHOI_TLYNO'})
        return res
      } catch(error)
      {
        return null
      }
      
    },
    loadChiTietNoTamThu()
    {
      if(this.params.p_loaigach === 1)  // khoản nợ
      {

      }else if(this.params.p_loaigach === 2)  // tháng nợ
      {

      } else if(this.params.p_loaigach === 3)  // dịch vụ
      {

      } else if(this.params.p_loaigach === 4)  // khoản nợ)
      {

      }
    },
    async getMaTTNeo(ma_tt)
    {
      let postData = {
        "kyCuoc": this.params.p_kycuoc,
        "ma": ma_tt
      }
      try{
        const res = await GachNoAPI.getMaTTNeo(this.axios, postData)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined ? data.data.chiTiet[0] : null
      }catch(error){
        this.$toast.error(error.data.message_detail)
        return null
      }
      finally{}
    },
    async getHTTTNeo()
    {
      /*
      let postData = {
        "tenTruong":"HTTT_ID_NEO",
        "schema":"qltn",
        "tenBang":"hinhthuc_tt_neo",
        "dieuKien":"httt_id_vtt = " + this.params.p_httt
      }
      */
      let postData = {
        "tenId": 'httt_id_vtt',
        "ma": this.params.p_httt
      }

      const res = await CommonsAPI.getMapID2(this.axios, postData)
      const data = await res.data

      return  data.error_code === 'BSS-00000000'
            && data.data !== undefined
            && data.data.length > 0 
            ? data.data[0].KETQUA : null
    },
    phieuGachNoCloseHandler(flag)
    {
      if(flag)
      {
        this.getSoPhieuTheoUser()
      }
    },
    onChangeCustomDropDown(id) {
      const isHIdden = $(id).is(":hidden");
      if (isHIdden) {
        $(id).show();
      } else {
        $(id).hide();
      }
    },
    onSelectHTTT: async function (value) {
      if (value) {
        this.params.p_httt = value.HTTT_ID;
        this.params.p_httt_text = value.HINHTHUC;
        $("#httt-popup").hide();

      } else {
        this.params.p_httt = null
        this.params.p_httt_text = ""
      }
    },
  },
  watch : {
    /*
    '$route.path': function(val, oldVal){
      console.log(this.$route.params.id);
      this.initUI();
    }
    */
  }
})
</script>
<style>
.displayNone {
  display: none;
}

</style>
