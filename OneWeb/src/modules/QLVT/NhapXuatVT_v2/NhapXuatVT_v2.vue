<template src="./NhapXuatVT_v2.html"></template>
<script>
/**@see WinUI.WinUIQuanLyVatTu.frmNhapXuatVT_v2*/
import { mapActions, mapState, mapGetters, mapMutations } from 'vuex'
import moment from 'moment'
import XML_JS from 'xml-js'
import Vue from 'vue'
import { enableRipple, createElement } from '@syncfusion/ej2-base'
import { ContextMenuPlugin } from '@syncfusion/ej2-vue-navigations'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import { DateTimePickerPlugin, DateTimePicker } from '@syncfusion/ej2-vue-calendars'
Vue.use(DateTimePickerPlugin)
Vue.use(ContextMenuPlugin)
Vue.use(SplitterPlugin)
import api from './api'
import xlsx from 'xlsx'
import { currency } from '@/filters/currency'
import { previewPrint } from '@/utils/util'
import { Query, Predicate } from '@syncfusion/ej2-data'
import { FORM_CONFIG, DS_QUYEN_VT, LOAI_KHO, MUCDICH_VT, LOAIPHIEU_DENGHI_VATTU } from './const'
import { DDataGrid, DDatePicker } from './components/common'
import { GridChungTuStatusTemplate, GridVatTuChatLuongTemplate, GridVatTuDongTbTemplate, GridVatTuHeThongTemplate, GridVatTuLoaiKhoTemplate, GridVatTuMaKhoTDTemplate, BtnTbTemplate, GridVatTuBtnXoaTemplate, GridThietBiBtnXoaTemplate } from './components'
import { onebssIdIsValid, convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault, generateComponentKey } from './helpers'
import DateTimeHelper from './helpers/dateHelper'

export default {
  components: {
    DDataGrid,
    DDatePicker,
    frmGanSeri: () => import('./components/frmGanSeri'),
    frmChungTu_TBao: () => import('./components/frmChungTu_TBao'),
    frmDoiSoat: () => import('./components/frmDoiSoat'),
    frmNhapCap: () => import('@/modules/QLVT/frmNhapCap'),
    frmVatTuTrongKho: () => import('./components/frmVatTuTrongKho'),
    frmDonHangTT: () => import('./components/frmDonHangTT'),
    frmDSVatTu: () => import('./components/frmDSVatTu'),
    frmTaoSerial: () => import('./components/frmTaoSerial'),
    frmNhapText: () => import('./components/frmNhapText'),
    frmNhapText_v2: () => import('./components/frmNhapText_v2'),
    ChonVatTuCTCT: () => import('@/modules/QLVT/ChonVatTuCTCT'),
    DoiSoatSerial: () => import('@/modules/QLVT/DoiSoatSerial'),
    frmNhapDieuChinh: () => import('./components/frmNhapDieuChinh'),
    frmGanSeri_v2: () => import('./components/frmGanSeri_v2'),
    frmLaySerial: () => import('./components/frmLaySerial'),
    frmLoaiKhoMaKho: () => import('./components/frmLoaiKhoMaKho'),
    frmMa_PDA: () => import('./components/frmMa_PDA'),
    frmXemMauIn: () => import('./components/frmXemMauIn'),
    frmNoiDungTT: () => import('./components/frmNoiDungTT'),
    QLTS: () => import('@/modules/QLVT/QLTS'),
    frmExcelNghiepVu: () => import('./components/frmExcelNghiepVu'),
    frmSuaCT: () => import('./components/frmSuaCT'),
    frmVatTuTrongKhoV2: () => import('@/modules/QLVT/frmVatTuTrongKhoV2'),
    frmGiaoPhieu: () => import('@/modules/QLVT/frmGiaoPhieu/frmGiaoPhieu.vue'),
    frmChonTBi_CT: () => import('./components/frmChonTBi_CT'),
    frmGanSeri_Tbi: () => import('@/modules/QLVT/frmGanSeri_Tbi/frmGanSeri_Tbi.vue'),
    SPDA: () => import('@/modules/QLVT/SPDA'),
    frmTraCuuTTDongBo: () => import('./components/frmTraCuuTTDongBo'),
    InBienBan: () => import('@/modules/print/InBienBan/InBienBan'),
    frmNhanPhieuLuanChuyen: () => import('@/modules/QLVT/NhanPhieuLuanChuyen'),
    frmChonDeNghi: () => import('./components/frmChonDeNghi')
  },
  name: 'NhapXuatVT_v2',
  props: {
    // tag: {
    //   type: [String, Number],
    //   default: null,
    //   required: false
    // },
    vinput_chungtu_id: {
      type: [String, Number],
      default: null,
      required: false
    }
  },
  async mounted() {
    this.tag = ['1', '2', '3', '4'].includes(this.$route.query.Tag) ? this.$route.query.Tag : '1'
    //DAT
    await this.frmNhapXuatVT_v2_Load()
    this.listMaKhoTD = await this.callApiWrapper(api.sp_lay_ds_makhotd, {})
    this.cboChatLuong.list = await this.callApiWrapper(api.danh_sach_vat_tu_ccdc_chat_luong, {})
  },
  computed: {
    soph() {
      return this.gridChungTu.value ? (this.gridChungTu.value.SOPHIEU != null ? this.gridChungTu.value.SOPHIEU : '') : ''
    },
    ma_pda_chungtu() {
      return this.gridChungTu.value ? (this.gridChungTu.value.MA_PDA != null ? this.gridChungTu.value.MA_PDA : '') : ''
    },
    nghiepvu_id_chungtu() {
      return this.gridChungTu.value ? (this.gridChungTu.value.NGHIEPVU_ID != null ? this.gridChungTu.value.NGHIEPVU_ID : '-1') : '-1'
    },
    ma_dn_luoi() {
      return this.gridChungTu.value ? (this.gridChungTu.value.MA_DN != null ? this.gridChungTu.value.MA_DN : '') : ''
    },
    btnSerialMenus() {
      return this.cmenu_serial.filter((e) => {
        if (e.id == 'tao_serial' && this.tsbtnSinhSr.enabled) return true
        if (e.id == 'in_serial' && this.tsbtnSinhSr.visible) return true
      })
    },
    cboTuKhoObject() {
      return this.cboTuKho.list && this.cboTuKho.value ? this.cboTuKho.list.find((e) => e.khoId == this.cboTuKho.value) : null
    },
    cboDenKhoObject() {
      return this.cboDenKho.list && this.cboDenKho.value ? this.cboDenKho.list.find((e) => e.khoId == this.cboDenKho.value) : null
    },
    calculateMoreMenu() {
      // return this.menuMoreItems.map((e) => {
      //   if (e.id == 'in_phieu') {
      //     e.items = e.items.filter((e) => {
      //       if (e.id == 'phieu_nhap' && this.tsbtnPhieuNhap.enabled) return true
      //       if (e.id == 'phieu_xuat' && this.tsbtnPhieuXuat.enabled) return true
      //       if (e.id == 'phieu_chuyen' && this.tsbtnPhieuChuyen.enabled) return true
      //       if (e.id == 'chi_tiet_sr' && this.tsbtnChiTietSR.enabled) return true
      //       if (e.id == 'phieu_tam_ung_vt' && this.tstPhieuTUVT.enabled) return true
      //       if (e.id == 'bbgn_nhap_kho' && this.tsbtnBBGNNhap.enabled) return true
      //       if (e.id == 'bbgn_xuat_kho' && this.tsbtnBBGNXuat.enabled) return true
      //       if (e.id == 'phieu_xuat_dvtc' && this.tsbtnInXuatDVTC.enabled) return true
      //       if (e.id == 'in_tem_serial' && this.tsbtnInTemSR.enabled) return true
      //       if (e.id == 'phieu_nhap_kho_khong_thue' && this.tsbtnInPhieuNKKT.enabled) return true
      //       if (e.id == 'phieu_giao_hoan_tra_vt' && this.tsbtnPhieuGiaoHoanTraVT.enabled) return true
      //       return false
      //     })
      //   }
      //   if(e.id == 'sua_ct') {
      //     if(this.tsbtnSuaCT.visible) {
      //       return e
      //     } else {
      //       return null
      //     }
      //   }
      //   return e
      // })

      return this.menuMoreItems.filter((e) => {
        if (e.id == 'in_phieu') {
          e.items = e.items.filter((e) => {
            if (e.id == 'phieu_nhap' && this.tsbtnPhieuNhap.enabled) return true
            if (e.id == 'phieu_xuat' && this.tsbtnPhieuXuat.enabled) return true
            if (e.id == 'phieu_chuyen' && this.tsbtnPhieuChuyen.enabled) return true
            if (e.id == 'chi_tiet_sr' && this.tsbtnChiTietSR.enabled) return true
            if (e.id == 'phieu_tam_ung_vt' && this.tstPhieuTUVT.enabled) return true
            if (e.id == 'bbgn_nhap_kho' && this.tsbtnBBGNNhap.enabled) return true
            if (e.id == 'bbgn_xuat_kho' && this.tsbtnBBGNXuat.enabled) return true
            if (e.id == 'phieu_xuat_dvtc' && this.tsbtnInXuatDVTC.enabled) return true
            if (e.id == 'in_tem_serial' && this.tsbtnInTemSR.enabled) return true
            if (e.id == 'phieu_nhap_kho_khong_thue' && this.tsbtnInPhieuNKKT.enabled) return true
            if (e.id == 'phieu_giao_hoan_tra_vt' && this.tsbtnPhieuGiaoHoanTraVT.enabled) return true
            return false
          })
        }
        if (e.id == 'sua_ct') {
          return this.tsbtnSuaCT.visible
        }
        return true
      })
    },
    gridThietBiAggregates() {
      return [
        { field: 'SOLUONG', type: 'Custom', footerTemplate: this.gridThietBi.aggregates.soluong_template, customAggregateFn: this.gridThietBi_SOLUONG_AggregateFn },
        { field: 'TIEN', type: 'Custom', footerTemplate: this.gridThietBi.aggregates.tien_template, customAggregateFn: this.gridThietBi_TIEN_AggregateFn }
      ]
    }
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'NHẬP VẬT TƯ MUA LẺ',
        list: [
          { name: 'Quản lý vật tư', link: { name: 'Ui.cards' } },
          { name: 'Nhập/ xuất/ điều chuyển vật tư', link: { name: 'Ui.buttons' } }
        ]
      },
      menuMoreItems: [
        { id: 'hoan_thanh', child_text: 'Hoàn thành', child_icon: 'one-save1' },
        {
          id: 'in_phieu',
          child_text: 'In phiếu',
          child_icon: 'one-print',
          items: [
            { id: 'phieu_nhap', child_text: 'Phiếu nhập', child_icon: 'one-print' },
            { id: 'phieu_xuat', child_text: 'Phiếu xuất', child_icon: 'one-print' },
            { id: 'phieu_chuyen', child_text: 'Phiếu chuyển', child_icon: 'one-print' },
            { id: 'chi_tiet_sr', child_text: 'Chi tiết SR', child_icon: 'one-print' },
            { id: 'phieu_tam_ung_vt', child_text: 'Phiếu tạm ứng VT', child_icon: 'one-print' },
            { id: 'bbgn_nhap_kho', child_text: 'BBGN Nhập kho', child_icon: 'one-print' },
            { id: 'bbgn_xuat_kho', child_text: 'BBGN Xuất kho', child_icon: 'one-print' },
            { id: 'phieu_xuat_dvtc', child_text: 'Phiếu xuất ĐVTC', child_icon: 'one-print' },
            { id: 'in_tem_serial', child_text: 'In tem Serial', child_icon: 'one-print' },
            { id: 'phieu_nhap_kho_khong_thue', child_text: 'Phiếu nhập kho (không thuế)', child_icon: 'one-print' },
            { id: 'phieu_giao_hoan_tra_vt', child_text: 'Phiếu giao hoàn trả vật tư', child_icon: 'one-print' }
          ]
        },
        { id: 'sua_ct', child_text: 'Sửa CT', child_icon: 'one-file-edit3' },
        {
          id: 'vt_excel',
          child_text: 'VT Excel',
          child_icon: 'one-excel',
          items: [
            { id: 'dc_loai_kho', child_text: 'Điều chỉnh loại kho', child_icon: 'one-excel' },
            { id: 'dc_chat_luong', child_text: 'Điều chỉnh chất lượng', child_icon: 'one-excel' },
            { id: 'dc_ghi_chu', child_text: 'Điều chỉnh ghi chú', child_icon: 'one-excel' },
            { id: 'xuat_ims', child_text: 'Xuất IMS', child_icon: 'one-excel' }
          ]
        },
        { id: 'gui_sms', child_text: 'Gửi SMS', child_icon: 'one-user-mess' }
      ],
      cmenu_serial: [
        { text: 'Tạo serial', id: 'tao_serial' },
        { text: 'In serial', id: 'in_serial' }
      ],
      URL: 'http://10.70.52.164:8081/FILES/',
      vloai: 1,
      chitietCTData_insert: [],
      chitietCTData_update: [],
      chungtuData: [],
      chungtu_id: -0,
      chungtu_tbiData: [],
      ctct_id: null,
      dsQuyen: [],
      sohoa_qlvt: false,
      dtChungTu: [],
      dtTB: [],
      dtThietBi: [],
      dtVatTu: [],
      dtLuuVT: [],
      dtCopyCT: [],
      dtCopyCTCT: [],
      dtVatTuXuat: [],
      bbbgiao: [],
      duan_ims_clone: [],
      dieuchuyen_clone: [],
      khotu_id: 0,
      kieugoi_id: null,
      copyct: false,
      kieunhap: null,
      slohang: '',
      svattu_id: '',
      t_kho: '',
      t_kho_id: 0,
      ten_khotu: '',
      vchungtu_id: 0,
      vttnx_id: 0,
      dtDonHang: [],
      dtDulieu: [],
      dtThemVT: [],
      dtVT_LE: [],
      file: '',
      ttph_id: null,
      ngay_dh: null,
      ds_md: [],
      dtct: [],
      dtchitiet_ct: [],
      dt_dh_ct: [],
      dt_chia_sl: [],
      dt_temp: [],
      dt_file: [],
      dc_ct_noibo: false,
      dc_ct_ttvt: false,
      dc_ct_ldscdp: false,
      batbuoc_chst: false,
      up_nguoc_idchitiet_qlts: false,
      bqlda_nguonvon_dautu: false, // HCM
      bqlda_nguonvon_khac: false, // HCM
      tudong_hoanthanh: false,
      xacnhan_in_chungtu: false, //dùng cho NET
      xoa_ngaygui_theo_user: false, //dùng cho NET
      chon_kieu_qlts: false, //dùng cho NET
      gan_huy_duan: false, //dùng cho NET
      //SPDA
      grcFileCV: { list: [] },
      gridBienBanIMS: [],
      grcMa_PDA: [],
      tag: '',
      Tag: '1',
      kiemtra_quyen: false,
      boimau: false,
      qr_code: false,
      chonvattu_lohang: false,
      tenNguoiNhan: '',
      maBC: '',
      isQLTS: false,
      isVatTu: true,
      isChonVT: true,
      kieuVTExcel: 0,
      status: 0,
      kieu_td: 0,
      kho_nhan_goc: 0,
      vma_md: '',
      vtdk: [],
      ds_Gan: [],
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: []
      },
      vchungtu_id_temp: 0,
      // loaict_id: null,
      sophieu_temp: '',
      editSettings: { allowEditing: true },
      gridVatTuPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      gridVatTu: {
        header: [
          // { width: 70, headerText: 'Xóa', allowFiltering: false, template: GridVatTuBtnXoaTemplate(this), freeze: 'Left' },
          { width: 70, headerText: 'Xóa', allowFiltering: false, template: GridVatTuBtnXoaTemplate(this) },
          // { width: 100, fieldName: 'ID_NHAPGOC', headerText: 'IDG', allowFiltering: true, freeze: 'Left' },
          { width: 100, fieldName: 'ID_NHAPGOC', headerText: 'IDG', allowFiltering: true },
          // { width: 100, fieldName: 'CTCT_ID', headerText: 'CTCT', allowFiltering: true, freeze: 'Left' },
          { width: 100, fieldName: 'CTCT_ID', headerText: 'CTCT', allowFiltering: true },
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { width: 150, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { width: 350, fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true, format: 'N2', type: 'number', allowEditing: true, editType: 'numericedit' },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', allowFiltering: true, format: 'N2', type: 'number', allowEditing: false, editType: 'numericedit' },
          { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, format: 'N2', type: 'number', allowEditing: true, editType: 'numericedit' },
          { width: 350, fieldName: 'LOAIKHO', headerText: 'Loại kho', allowFiltering: true, template: GridVatTuLoaiKhoTemplate(this), visible: true },
          { fieldName: 'MAKHOTD', headerText: 'Mã kho TD', allowFiltering: true, template: GridVatTuMaKhoTDTemplate(this), visible: true },
          { fieldName: 'TK_KHO', headerText: 'TK Kho', allowFiltering: true },
          { fieldName: 'TK_DOIUNG', headerText: 'TK Đối ứng', allowFiltering: true },
          { fieldName: 'TY_LE', headerText: 'Tỷ lệ thu hồi (%)', allowFiltering: true, visible: false },
          { fieldName: 'MATHETS', headerText: 'Thẻ TS', allowFiltering: true, type: 'String', allowEditing: true, editType: 'DefaultEdit' },
          { fieldName: 'SOTHANG_PB', headerText: 'Số tháng PB', textAlign: 'Right', type: 'String', allowEditing: false, editType: 'DefaultEdit' },
          { fieldName: 'LOAI_TBI', headerText: 'Loại thiết bị' },
          // { fieldName: 'HAN_BH', headerText: 'Hạn BH', allowFiltering: true, type: 'date', format: { type: 'data', format:'dd/MM/yyyy' }, allowEditing: false, },
          { fieldName: 'HAN_BH', headerText: 'Hạn BH', allowFiltering: true, type: 'String' },
          { fieldName: 'MA_HD', headerText: 'Mã HĐ' },
          { fieldName: 'MA_TH', headerText: 'Đợt TH' },
          { fieldName: 'MATHUNG', headerText: 'Mã Thùng' },
          { fieldName: 'TONGSL', headerText: 'Tổng SL' },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', type: 'number', type: 'String', allowEditing: true, editType: 'DefaultEdit' },
          { fieldName: '', headerText: 'Dự án' },
          { fieldName: 'GHICHU_HD', headerText: 'Ghi chú HĐ' },
          { fieldName: 'TEN_CONGTRINH', headerText: 'Tên CT' },
          { fieldName: '', headerText: 'Cuộn cáp', template: this.CuonCapTemplate, textAlign: 'Center' },
          { fieldName: 'DS_DVI_TC', headerText: 'Đơn vị TC' },
          { fieldName: 'NGUON_GOC', headerText: 'Nguồn gốc', allowEditing: true },
          // { fieldName: 'DONGTBI_ID', headerText: 'Dòng Tbi', template: this.gridVatTuCboDongTB(this) },
          // { fieldName: 'HETHONG_ID', headerText: 'Hệ thống', template: this.gridVatTuCboHeThong(this) },
          { fieldName: 'CHATLUONG_ID', headerText: 'Chất lượng', template: GridVatTuChatLuongTemplate(this) },
          // { width: 100, fieldName: 'CTCT_XUAT_ID', headerText: 'CTCT xuất', freeze: 'Right' }
          { width: 100, fieldName: 'CTCT_XUAT_ID', headerText: 'CTCT xuất' }
          // { fieldName: 'VATTU_ID', headerText: '', visible: false, isPrimaryKey: true }
        ],
        list: [],
        value: null
      },
      grcVT: {
        header: [
          { fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true, allowSorting: true, freeze: 'Left' },
          { fieldName: 'MA_VT', headerText: 'Mã vật tư', width: 250, allowFiltering: true, allowSorting: true, textAlign: 'Right' },
          { fieldName: 'TEN_VT', headerText: 'Tên vật tư', width: 650, allowFiltering: true, allowSorting: true }
        ],
        list: [],
        selected: []
      },
      tsbtnNhapMoi: { enabled: true, visible: true },
      tsbtnGhiLai: { enabled: true, visible: true },
      tsbtnXoa: { enabled: true, visible: true },
      tsbtnHuyBo: { enabled: true, visible: true },
      tsbtnHoanThanh: { enabled: true, visible: true },
      tstbtnDonHang: { enabled: true, visible: true },
      tsbtnThemVT: { enabled: true, visible: true },
      tsbtnSinhSr: { enabled: true, visible: true },
      tsbtnCopyCT: { enabled: true, visible: true },
      tsbtnDongBo: { enabled: true, visible: true },
      tsbtnUpPXK: { enabled: true, visible: true },
      tsbtnTraLai: { enabled: true, visible: true },
      tsbtnGiaoPhieu: { enabled: true, visible: true },
      tsbtnPhieuNhap: { enabled: true, visible: true },
      tsbtnPhieuGiaoHoanTraVT: { enabled: true, visible: true },
      tsbtnChiTietSR: { enabled: true, visible: true },
      tsbtnPhieuXuat: { enabled: true, visible: true },
      tsbtnPhieuChuyen: { enabled: true, visible: true },
      tstPhieuTUVT: { enabled: true, visible: true },
      tsbtnBBGNNhap: { enabled: true, visible: true },
      tsbtnBBGNXuat: { enabled: true, visible: true },
      tsbtnInXuatDVTC: { enabled: true, visible: true },
      tsbtnInTemSR: { enabled: true, visible: true },
      tsbtnInPhieuNKKT: { enabled: true, visible: true },
      tsbtnSuaCT: { enabled: true, visible: true },
      tsbtnExcelLoaiKho: { enabled: true, visible: true },
      tsbtnExcelChatLuong: { enabled: true, visible: true },
      tsbtnExcelGhiChu: { enabled: true, visible: true },
      tsbtnPhieuChuyen: { enabled: true, visible: true },
      tsbtnSMS: { enabled: true, visible: true },
      tsbtnDongBoQLTS: { enabled: true, visible: true },
      btnTaoTbi: { enabled: true, visible: true },

      ThongTinHDDA: { enabled: true, visible: true },
      xTabTongHop: { enabled: true, visible: false },
      layoutMaPhieu: { enabled: true, visible: true },
      layoutControlDuAn: { enabled: true, visible: true },
      layoutControlDieuChinh: { enabled: true, visible: true },
      layoutControlBBBG: { enabled: true, visible: true },
      layoutControlDS_Vattu: { enabled: true, visible: true },
      layoutNhapExDG: { enabled: true, visible: true },
      layoutMauDG: { enabled: true, visible: true },
      btnXoaHetVT: { enabled: true, visible: true },
      simpleButton1: { enabled: true, visible: true },
      colSL_CTBI: { enabled: true, visible: true },
      colTyLe: { enabled: true, visible: true },
      colDelFile: { enabled: true, visible: true },
      colMaKhoTD: { enabled: true, visible: true },
      colLoaiKho: { enabled: true, visible: true },
      btnSeri_ChiaSL: { enabled: true, visible: true },

      ckbSendSMS: { enabled: true, value: false },
      ckbDuAn: { enabled: true, value: false },

      txtPhieuDeNghi: { enabled: true, value: '' },
      txtSoPhieu: { enabled: true, value: '' },
      txtNoiDung: { enabled: true, value: '' },
      txtLenhVanBan: { enabled: true, value: '' },
      txtGhiChuHD: { enabled: true, value: '' },
      txtQuetSerial: { enabled: true, value: '' },
      lblSoLanXemBC: { enabled: true, value: null },

      cboLoaiChungTu: { enabled: true, value: null, list: [] },
      cboKieuPhieu: { enabled: true, value: null, list: [] },
      cboMucDich: { enabled: true, value: null, list: [] },
      cboNghiepVu: { enabled: true, value: null, list: [] },
      cboCongTrinh: {
        enabled: true,
        header: [
          { width: '130px', fieldName: 'SOHIEU_CT', headerText: 'Số hiệu CT', isPrimaryKey: true, allowFiltering: true },
          { width: '400px', fieldName: 'TEN_CT', headerText: 'Tên CT', allowFiltering: true }
        ],
        value: null,
        list: []
      },
      cboLoaiKho: { enabled: true, value: null, list: [] },
      cboHeThong: { enabled: true, value: null, list: [] },
      cboChatLuong: { enabled: true, value: null, list: [] },
      dtpNgayDK: { enabled: false, value: null },
      txtPhieuLuanChuyen: { enabled: false, value: null, id: null },
      dtpTuNgay: { enabled: false, value: null },
      dtpDenNgay: { enabled: false, value: null },
      dtpNgayGuiTT: { enabled: false, value: null },
      dtpNgayNhanTT: { enabled: false, value: null },
      dtpNgayChuyenKT: { enabled: false, value: null },
      dtpNgayKy: { enabled: false, value: null },
      dtpNgayVanBan: { enabled: false, value: null },
      dsThamSo: { list: [] },
      cboKho: { list: [] },
      cboTuKho: { enabled: true, value: null, list: [] },
      cboDenKho: { enabled: true, value: null, list: [], buttons: [{ visible: false }, { visible: false }] },
      ckbTuKho: { enabled: true, value: null },
      ckbDenKho: { enabled: true, value: null },
      ckb_nd: { enabled: true, value: null },
      cboTinh: { list: [] },
      cboMa_PDA: { value: null },
      cboHopDong: {
        enabled: true,
        header: [
          { width: '250px', fieldName: 'maHd', headerText: 'Mã HD', isPrimaryKey: true, allowFiltering: true },
          { width: '600px', fieldName: 'noiDung', headerText: 'Nội dung', allowFiltering: true },
          { width: '150px', fieldName: 'ngayYc', headerText: 'Ngày YC', allowFiltering: true }
        ],
        value: null,
        list: []
      },
      cboVatTu: { enabled: true, value: null, list: [] },
      cbo_DuAn: { enabled: true, value: null, list: [] },
      chkAll_Phieu: { value: false },
      gridChungTu: {
        header: [
          // { fieldName: '', headerText: '', template: this.iconStatus(), cssClass: 'text-center', width: '70', freeze: 'Left', allowFiltering: false, allowSorting: false },
          // { fieldName: '', headerText: '', allowSorting: false, allowFiltering: false, cssClass: 'text-center', allowEditing: false, width: 43, headerTemplate: colCheckboxAllChungTu, template: colCheckboxChungTu, freeze: 'Left' },

          { width: 40, headerText: '', template: GridChungTuStatusTemplate(this), allowFiltering: true, allowSorting: false, freeze: 'Left' },
          { width: 160, fieldName: 'SOPHIEU', headerText: 'Số phiếu', textAlign: 'Left', allowFiltering: true, allowSorting: false, freeze: 'Left' },
          { width: 100, fieldName: 'LOAI_CT', headerText: 'Loại CT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { width: 100, fieldName: 'TRANGTHAI', headerText: 'Trạng thái', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'SOPHIEUCHA', headerText: 'Số phiếu cha', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'TRANGTHAI_DN', headerText: 'Tình trạng ký số', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_CT', headerText: 'Ngày yêu cầu', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYNHAN_TT', headerText: 'Ngày nhận', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYGUI_TT', headerText: 'Ngày xuất', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_HT', headerText: 'Ngày HT', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'TIEN', headerText: 'Tiền', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_GIAO', headerText: 'Từ kho', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'KHO_NHAN', headerText: 'Đến kho', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MUCDICH', headerText: 'Mục đích', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'ND_GIAO', headerText: 'Nội dung', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_CN', headerText: 'Người lập', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_TH', headerText: 'Người TH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_HT', headerText: 'Người HT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_CTKT', headerText: 'Người CTKT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          //TODO: tag=3 moi hien
          { fieldName: 'MAPHIEU_DK', headerText: 'Mã phiếu', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_VB', headerText: 'Ngày VB', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'SO_VB', headerText: 'Lệnh VB', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'PHIEU_KT_ID', headerText: 'Phiếu KT', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAYCT_KT', headerText: 'Ngày Chuyển', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGAY_XD', headerText: 'Ngày duyệt', textAlign: 'Right', allowFiltering: true, allowSorting: true },
          { fieldName: 'NGUOI_XD', headerText: 'Người duyệt', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_DH', headerText: 'Mã ĐH', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_HD', headerText: 'Mã HD', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'LOAIPHIEU', headerText: 'Kiểu phiếu', textAlign: 'Left', allowFiltering: true, allowSorting: true, allowEditing: true },
          { fieldName: 'DONVI_GIAO', headerText: 'Đơn vị giao', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'DONVI_NHAN', headerText: 'Đơn vị nhận', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_HD_PO', headerText: 'Mã HDPO', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'MA_PDA', headerText: 'Mã PDA', textAlign: 'Left', allowFiltering: true, allowSorting: true },
          { fieldName: 'CHUNGTU_ID', headerText: '', visible: false, isPrimaryKey: true },
          { width: '80', fieldName: 'TT_IMS', headerText: 'IMS', textAlign: 'Left', allowFiltering: true, allowSorting: true, freeze: 'Right' },
          { width: '80', fieldName: 'DSTB', headerText: 'DS TB', textAlign: 'Left', allowFiltering: true, allowSorting: true, template: BtnTbTemplate(this), freeze: 'Right' }
        ],
        contextMenuItems: [
          { text: 'Tra cứu TT đồng bộ', target: '.e-content', id: 'tra_cuu_tt_dong_bo' },
          { text: 'Danh sách thuê bao', target: '.e-content', id: 'danh_sach_thue_bao' }
        ],
        list: [],
        value: null,
        oldValue: null,
        oldSoPhieu: null,
        selected: [],
        page: 0,
        maxSize: 10
      },
      gridThietBi: {
        header: [
          { width: '80', fieldName: 'btnXoaVT', headerText: 'Xóa', allowFiltering: false, template: GridThietBiBtnXoaTemplate(this) },
          { width: '250', fieldName: 'LOHANG', headerText: 'Lô hàng', allowFiltering: true },
          { width: '250', fieldName: 'MATHUNG', headerText: 'Mã thùng', allowFiltering: true, type: 'String', allowEditing: true, editType: 'DefaultEdit' },
          { width: '200', fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { width: '350', fieldName: 'TEN_VT', headerText: 'Tên vật tư', allowFiltering: true },
          { width: '250', fieldName: 'MA_TBI', headerText: 'Mã thiết bị', allowFiltering: true },
          { width: '250', fieldName: 'SERIAL', headerText: 'Serial', allowFiltering: true },
          { fieldName: 'DVI_TINH', headerText: 'ĐVT', allowFiltering: true },
          { fieldName: 'SOLUONG', headerText: 'Số lượng', allowFiltering: true, allowEditing: true, format: 'N2', type: 'number', editType: 'numericedit' },
          { fieldName: 'DONGIA', headerText: 'Đơn giá', allowFiltering: true, format: 'N2', type: 'number' },
          { fieldName: 'TIEN', headerText: 'Thành tiền', allowFiltering: true, format: 'N2', type: 'number' },
          { fieldName: 'GHICHU', headerText: 'Ghi chú', textAlign: 'Right', allowFiltering: true, allowEditing: true, type: 'String' },
          { fieldName: 'SERIAL_GP', headerText: 'Serial GP', allowFiltering: true },
          { fieldName: 'MATBI_GOC', headerText: 'Mã TB Gốc', allowFiltering: true, allowEditing: true },
          { fieldName: 'THIETBI_ID', headerText: '', visible: false, isPrimaryKey: true }
        ],
        list: [],
        value: null,
        selected: [],
        aggregates: {
          soluong_template(parent) {
            return {
              template: Vue.component('sumTemplate', {
                template: `<span>Tổng: {{data.Custom}}</span>`,
                data() {
                  return { data: {} }
                }
              })
            }
          },
          tien_template: function () {
            return {
              template: Vue.component('maxTemplate', {
                template: `<span>Tổng: {{data.Custom}}</span>`,
                data() {
                  return { data: {} }
                }
              })
            }
          }
        }
      },
      gridVTGroup: {
        header: [
          { allowEditing: false, fieldName: 'MA_VT', headerText: 'Mã vật tư', allowFiltering: true },
          { allowEditing: false, fieldName: 'SL_TON', headerText: 'SL Tồn', allowFiltering: true },
          { allowEditing: true, fieldName: 'SL_XUAT', headerText: 'SL Xuất', allowFiltering: true },
          { allowEditing: false, fieldName: 'SL_VATTU', headerText: 'SL giới hạn', allowFiltering: true }
        ],
        list: [],
        selected: [],
        value: null
      },
      listMaKhoTD: [], // dùng để load combo makhotd trong grid vật tư
      spda: {
        cboHD_XuatIMS: null,
        cboMa_DV_QL: null,
        cboMa_DV_SD: null,
        grcMa_PDA: null,
        cboIMS_CSHT: null,
        ContractNumber: null,
        MA_PDA: null
      },
      frmNhapText: { visible: false, input: {} },
      frmSuaCT: { visible: false, input: {} },
      frmGanSeri_v2: { visible: false, input: {} },
      frmDSVatTu: { visible: false, input: {} },
      frmMa_PDA: { visible: false, input: {} },
      frmDonHangTT: { visible: false, input: {} },
      frmChonTBi_CT: { visible: false, input: {} },
      frmChungTu_TBao: { visible: false, input: {} },
      frmNhapDieuChinh: { visible: false, input: {} },
      frmTaoSerial: { visible: false, input: {} },
      frmLaySerial: { visible: false, input: {} },
      frmGanSeri: { visible: false, input: {} },
      frmLoaiKhoMaKho: { visible: false, input: {} },
      frmVatTuTrongKho: { visible: false, input: {} },
      frmVatTuTrongKhoV2: { visible: false, input: {} },
      frmDoiSoat: { visible: false, input: {} },
      frmTraCuuTTDongBo: { visible: false, input: {} },
      frmNhapText_v2: { visible: false, input: {} },
      InBienBan: { visible: false, input: {} },
      frmXemMauIn: { visible: false, input: {} },
      frmNhanPhieuLuanChuyen: { visible: false, input: {} },
      frmChonDeNghi: { visible: false, input: {} },
      ejsDialog: {
        header: 'Tiêu đề',
        name: null,
        visible: false,
        config: {
          width: '95%',
          height: 'auto',
          minHeight: '150',
          enableResize: true,
          allowDragging: true,
          position: { X: 'center', Y: 'center' },
          // position: { X: 420, Y: 14 },
          animationSettings: { effect: 'Fade' }
        }
      }
    }
  },
  /**
   * Main methods
   */
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    //TODO: tối ưu chạy 1 vòng lặp
    checkThamSoMacDinh(ds, str_vtemp, callback) {
      let det = ds.filter((e) => e.maTS == str_vtemp)
      det.length > 0 ? (det[0].tenTS == '1' ? callback() : false) : false
    },
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    /**
     * @see frmNhapXuatVT_v2_Load
     */
    async frmNhapXuatVT_v2_Load() {
      // layoutDeNghi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Never
      this.dsThamSo.list = await this.callApiWrapper(api.ds_tham_so_mac_dinh, { maTS: 'DHSX_SOHOA_QLVT' })
      if (this.dsThamSo.list != null && this.dsThamSo.list[0].length > 0 && this.dsThamSo.list[0].TEN_TS == '1') {
        this.sohoa_qlvt = true
        this.tsbtnGiaoPhieu.visible = true
        // layoutDeNghi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      }
      this.AN_GROUP(true)
      // let diachi = this.GetType().FullName
      // if (Tag != null || Tag != null)
      // {
      //     tag = Tag
      //     diachi = diachi + "," + tag
      // }
      this.dtpDenNgay.value = this.$root.token.getNgayCapNhat()
      this.dtpTuNgay.value = moment(new Date().setDate(new Date().getDate() - 7)).format('DD/MM/YYYY')
      await this.Load_quyen()
      this.dtpNgayDK.value = moment().format(FORM_CONFIG.DEFAULT_DATE_FORMAT)
      // #region  Lấy TS mặc định
      let ds = await this.callApiWrapper(api.tham_so_mac_dinh, { kieu_id: 0 })
      if (ds.length > 0) {
        this.checkThamSoMacDinh(ds, 'KIEMTRA_QUYENXN', () => {
          this.kiemtra_quyen = true
        })
        this.checkThamSoMacDinh(ds, 'MAU_CHUNGTU_QLVT', () => {
          this.boimau = true
          if (!this.boimau) {
            // colDonGia.AppearanceCell.ForeColor = Color.Blue
            // colSOLUONG.AppearanceCell.ForeColor = Color.Black
            // gridVatTu.OptionsView.EnableAppearanceEvenRow = true
            // gridVatTu.OptionsView.EnableAppearanceOddRow = true
          }
        })
        this.checkThamSoMacDinh(ds, 'QR_CODE', () => {
          this.qr_code = true
        })
        this.checkThamSoMacDinh(ds, 'CHONLOHANG_VATTU', () => {
          this.chonvattu_lohang = true
        })
        this.checkThamSoMacDinh(ds, 'TUDONG_HT_NGAYNHAN', () => {
          this.tudong_hoanthanh = true
        })
        this.checkThamSoMacDinh(ds, 'XAC_NHAN_IN_CHUNGTU', () => {
          this.xacnhan_in_chungtu = true
        })
        this.checkThamSoMacDinh(ds, 'XOA_NGAYGUI_THEO_USER', () => {
          this.xoa_ngaygui_theo_user = true
        })
        this.checkThamSoMacDinh(ds, 'CHON_KIEU_QLTS', () => {
          this.chon_kieu_qlts = true
        })
        this.checkThamSoMacDinh(ds, 'UP_NGUOC_ID_CHITIET_QLTS', () => {
          this.up_nguoc_idchitiet_qlts = true
        })
        this.checkThamSoMacDinh(ds, 'GAN_HUY_DUAN', () => {
          this.tudong_ht_xuat_hoantra = true
        })
        this.checkThamSoMacDinh(ds, 'TUDONG_HT_XUAT_HOANTRA', () => {
          this.xoa_ngaygui_theo_user = true
        })
        this.checkThamSoMacDinh(ds, 'BATBUOC_CHST', () => {
          this.batbuoc_chst = true
        })
        this.checkThamSoMacDinh(ds, 'DOI_MUCDICH_CHUNGTU', () => {
          this.doi_mucdich_chungtu = true
        })
      }
      await this.Load_Cbo()
      await this.Load_ds_chungtu()
      if (this.$root.token.getMaTinh() == 'NET') {
        //     colChatluong_id.visibleIndex = 15
        //     colDongTBI.visibleIndex = 16
        //     colHeThongID.visibleIndex = 17
      }

      // try
      // {
      //     let kq = DanhMuc.GET_VALUE_FUNC("{?DB12}.lay_ten_menu", "vten_diachi", diachi, "vthongtin", this.GetType().Name)
      //     if (kq != "-1")
      //     {
      //         label7.value = this.value = kq.ToUpper()
      //     }
      // }
      // catch
      // {

      // }

      // if (tag == "3") // xuất
      // {
      //     colSoVB2.visible = true
      //     colNgayVB2.visible = true
      //     colMaphieuDK.visible = true
      // }

      // if (xacnhan_in_chungtu)
      //     lblSoLanXemBC.visible = false

      // #region Ẩn hiện cột
      // let show_hide_col = ChungTu.GET_DATA_PROC(@"{?DB12}.show_cot_hienthi", "vkieu", 1)
      // if (show_hide_col.Columns.length > 0)
      // {
      //     if (!show_hide_col.Columns.includes("TIEPNHAN"))
      //         layoutControlItem38.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Never
      //     gridColumn68.visible = show_hide_col.Columns.includes("DONVI_GIAO")        //grid chungtu
      //     colInPhieuCheck.visible = show_hide_col.Columns.includes("DAIN_PHIEU") //grid chungtu
      //     gridColumn69.visible = show_hide_col.Columns.includes("DONVI_NHAN")        //grid chungtu
      //     gridColumn83.visible = show_hide_col.Columns.includes("NGAY_XD")           //grid chungtu
      //     gridColumn84.visible = show_hide_col.Columns.includes("NGUOI_XD")          //grid chungtu
      //     gridColumn70.visible = show_hide_col.Columns.includes("PN")       //grid vật tư
      //     colDongTBI.visible = show_hide_col.Columns.includes("DONGTBI")          //grid vật tư
      //     colHeThongID.visible = show_hide_col.Columns.includes("HETHONGID")     //grid vật tư
      // }
    },
    /**
     * @see Load_quyen
     */
    async Load_quyen() {
      this.dsQuyen = await this.callApiWrapper(api.ds_quyen_nd, { nguoidung_id: this.$root.token.getNguoiDungID() })
    },
    /**
     * @see: Loc_Kho
     */
    async Loc_Kho() {
      this.cboTuKho.list = this.cboTuKho.list.filter((a) => a.trangThai == '1' && a.loaiKhoId && a.loaiKhoId != LOAI_KHO.KHO_NV)
      if (this.ckbTuKho.value) {
        if (this.cboTuKho.list.length && this.cboTuKho.list[0].hasOwnProperty('quyenKg')) {
          this.cboTuKho.list = this.cboTuKho.list.filter((b) => b.quyenKg == '1')
        }
      }
      this.cboDenKho.list = this.cboDenKho.list.filter((a) => a.trangThai == '1' && a.loaiKhoId && a.loaiKhoId != LOAI_KHO.KHO_NV)
      if (this.ckbDenKho.value) {
        if (this.cboDenKho.list.length && this.cboDenKho.list[0].hasOwnProperty('quyenKn')) {
          this.cboDenKho.list = this.cboDenKho.list.filter((b) => b.quyenKg == '1')
        }
      }
    },
    /**
     * @see: private void setTag(DevExpress.XtraEditors.GridLookUpEdit cbo)
     */
    setTag(cboName) {
      switch (this.tag) {
        case '1':
          return 1
        case '2':
          return 99
        case '3':
          return 2
        case '4':
          return 3
        default:
          this[cboName].enabled = true
          return ''
      }
    },
    /**
     * @see Load_Cbo()
     */
    async Load_Cbo() {
      this.cboLoaiChungTu.list = await this.callApiWrapper(api.danh_muc_loai_chung_tu, { tag: this.tag })
      this.cboLoaiChungTu.value = null
      this.cboLoaiChungTu.enabled = false
      this.cboKho.list = await this.callApiWrapper(api.ds_kho_nhap_xuat, { nhanvien_id: this.$root.token.getNhanVienID() })
      this.cboTuKho.list = this.cboKho.list
      this.cboDenKho.list = this.cboKho.list
      this.cboMucDich.list = await this.callApiWrapper(api.ds_muc_dich_nhap_xuat, { nhommd_id: 3 })
      this.ds_md = this.cboMucDich.list
      this.cboCongTrinh.list = await this.callApiWrapper(api.ds_cong_trinh, {
        kieuId: 1,
        nhanVienId: this.$root.token.getNhanVienID(),
        maNguoiDung: await this.$root.token.getUserName()
      })
      await this.Loc_Kho()
      this.ngay_dh = DateTimeHelper.MinValue()
      this.cboLoaiChungTu.value = this.setTag('cboLoaiChungTu')
      this.cboTinhTrang = await this.callApiWrapper(api.tinh_trang_thiet_bi, {})
      this.cboChatLuongID = await this.callApiWrapper(api.chat_luong, {})
      if (this.tag == '66' && this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)) {
        this.cboNghiepVu.list = await this.callApiWrapper(api.ds_nghiep_vu, { nhomMdId: [21] })
      } else {
        this.cboNghiepVu.list = await this.callApiWrapper(api.ds_nghiep_vu, { nhomMdId: [20, 21] })
      }
      // this.cboTinh.list = await this.callApiWrapper(api.ds_tinh, {})
    },
    async get_mamd(cbo) {
      //√
      let d = await this.callApiWrapper(api.ds_ten_tat_theo_muc_dich, { mucdich_id: cbo ? cbo : 0 })
      if (d.length && d[0] != null) return d[0]
      return ''
    },
    async Load_ds_chungtu() {
      console.log('Load_ds_chungtu')
      //thu set input tai day ==> wtf
      if (this.vinput_chungtu_id && this.vchungtu_id == 0) {
        this.vchungtu_id = this.vinput_chungtu_id
      } else if (this.$route.query.vinput_chungtu_id && this.vchungtu_id == 0) {
        this.vchungtu_id = this.$route.query.vinput_chungtu_id
      }
      //luu lai item hien tai
      if (this.gridChungTu.value != null) {
        this.gridChungTu.oldValue = this.gridChungTu.value
      }
      //√
      try {
        if (this.kieugoi_id == 1) {
          this.dtChungTu = await this.callApiWrapper(api.ds_chung_tu_theo_loai, {
            tatCa: this.chkAll_Phieu.value ? 1 : 0,
            donViId: this.$root.token.getDonViID(),
            nhanVienId: this.$root.token.getNhanVienID(),
            tuNgay: getDateStringOrDefault(this.dtpTuNgay.value, null, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT),
            denNgay: getDateStringOrDefault(this.dtpDenNgay.value, null, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT),
            loai: this.tag,
            pageNum: this.gridChungTu.page,
            pageSize: this.gridChungTu.maxSize
          })
          let chungTuTaoMoi = this.dtChungTu.find((e) => e.SOPHIEU == this.gridChungTu.oldSoPhieu)
          if (chungTuTaoMoi) this.gridChungTu.oldValue = chungTuTaoMoi
          this.gridChungTu.list = this.dtChungTu
          if (this.dtChungTu.length > 0) {
            if (this.gridChungTu.list != null) this.SetButton(3)
            else this.SetButton(1)
          } else this.SetButton(1)
        } else {
          this.dtChungTu = await this.callApiWrapper(api.ds_chung_tu_theo_loai, {
            tatCa: this.chkAll_Phieu.value ? 1 : 0,
            donViId: this.$root.token.getDonViID(),
            nhanVienId: this.$root.token.getNhanVienID(),
            tuNgay: getDateStringOrDefault(this.dtpTuNgay.value, null, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT),
            denNgay: getDateStringOrDefault(this.dtpDenNgay.value, null, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT),
            loai: this.tag,
            pageNum: this.gridChungTu.page,
            pageSize: this.gridChungTu.maxSize
          })
          let chungTuTaoMoi = this.dtChungTu.find((e) => e.SOPHIEU == this.gridChungTu.oldSoPhieu)
          if (chungTuTaoMoi) this.gridChungTu.oldValue = chungTuTaoMoi
          this.gridChungTu.list = this.dtChungTu
          if (this.dtChungTu.length > 0) {
            if (this.gridChungTu.list != null) this.SetButton(3)
            else this.SetButton(1)
          } else this.SetButton(1)
        }
        if (this.ckb_nd.value) {
          if (this.dtChungTu.length > 0) {
            let username = await this.$root.token.getUserName()
            let q = this.dtChungTu.filter((e) => e.NGUOI_CN == username)
            if (q.length > 0) {
              this.gridChungTu.list = q
              if (this.dtChungTu.length > 0) this.SetButton(3)
              else this.SetButton(1)
            } else {
              this.gridChungTu.list = []
              this.SetButton(1)
            }
          }
        }
      } catch (e) {
        this.$toast.error('Có lỗi xảy ra ' + e)
      }
    },
    /**
     * @see Load_ds_vattu
     */
    async Load_ds_vattu() {
      this.dtVatTu = await this.callApiWrapper(api.ds_vat_tu_chung_tu_hcm, { chungtu_id: this.vchungtu_id })
      this.dtLuuVT = this.dtVatTu.map((e) => e)
      //  = await this.callApiWrapper(api.ds_vat_tu_chung_tu_v1, { chungtu_id: this.vchungtu_id })
      console.log('Load_ds_vattu')
      this.dtVatTuXuat = this.dtLuuVT
      if (this.dtVatTu.length > 0 && this.dtVatTu[0].TY_LE != null && this.dtVatTu[0].MATHETS != null) {
        let mucdich_id = this.cboMucDich.value
        let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (d.length > 0) {
          if (d[0][0] == '1.3') {
            this.colTyLe.visible = true
          } else {
            this.colTyLe.visible = false
          }
        }
      }
      if (this.dtVatTu.length > 0 && this.dtVatTu[0].MAKHOTD && this.dtVatTu[0].LOAIKHO) {
        this.colMaKhoTD.visible = true
        this.colLoaiKho.visible = true
      } else {
        this.colMaKhoTD.visible = false
        this.colLoaiKho.visible = false
      }
      //test performance
      // let faker = []
      // for(let i = 0; i<1000;i++) {
      //   faker = [...faker, ...this.dtVatTu]
      // }
      // this.gridVatTu.list = faker
      this.gridVatTu.list = this.dtVatTu
    },
    /**
     * @see LAY_DS_VTCT_SERIAL
     */
    async LAY_DS_VTCT_SERIAL() {
      console.log('LAY_DS_VTCT_SERIAL')
      let ds = await this.callApiWrapper(api.ds_vattu_chung_tu_serial, { chungtu_id: this.vchungtu_id })
      if (ds.length != 0 && this.tsbtnNhapMoi.enabled && this.tsbtnGhiLai.enabled) {
        this.grcVT.list = []
        this.grcVT.list = ds
        if (this.ttph_id == 4) this.cboVatTu.enabled = true
        else this.cboVatTu.enabled = false
      } else if (this.tsbtnNhapMoi.enabled == false || (ds.length == 0 && this.tsbtnNhapMoi.enabled && this.tsbtnGhiLai.enabled)) {
        this.cboVatTu.value = null
      }
    },
    /**
     * @see Load_ds_thietbi
     */
    async Load_ds_thietbi() {
      console.log('Load_ds_thietbi')
      this.dtThietBi = (await this.callApiWrapper(api.lay_ds_thietbi_chungtu, { chungtu_id: this.vchungtu_id })).data
      this.gridThietBi.list = this.dtThietBi
    },
    /**
     * @see gridviewChungTu_FocusedRowChanged(object sender, FocusedRowChangedEventArgs e)
     */
    async gridviewChungTu_FocusedRowChanged(e) {
      console.log('gridviewChungTu_FocusedRowChanged')
      console.log(e)
      //check if
      let row = Array.isArray(e.data)
      console.log(`row.CHUNGTU_ID: ${row.CHUNGTU_ID}, this.vchungtu_id: ${this.vchungtu_id}, not load: ' + ${row.CHUNGTU_ID == this.vchungtu_id}`)
      if (row.CHUNGTU_ID == this.vchungtu_id) return
      // if (this.gridChungTu.value != null && e.CHUNGTU_ID == this.gridChungTu.value.CHUNGTU_ID) return
      // this.gridChungTu.value = e
      this.gridChungTu.value = e.data
      this.t_kho_id = 0
      this.t_kho = ''
      this.ngay_dh = DateTimeHelper.MinValue()
      this.tsbtnDongBoQLTS.visible = false
      let index = this.gridChungTu.list.findIndex((x) => x.CHUNGTU_ID == this.gridChungTu.value.CHUNGTU_ID)
      if (index >= 0) {
        this.gridviewChungTu_FocusedRowChangedByIndex(index)
        // this.cboKieuPhieu_EditValueChanged()
        if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10' && this.vma_md == 'N.DCDG' && this.dsQuyen.includes(2517)) {
          this.cboDenKho.buttons[1].visible = true
          // colDonGia.OptionsColumn.AllowEdit = true
        } else {
          this.cboDenKho.buttons[1].visible = false
          // colDonGia.OptionsColumn.AllowEdit = false
        }
      } else {
        this.setTag(this.cboLoaiChungTu.value)
        this.tsbtnTraLai.visible = false
        this.SetButton(1)
        this.Clear()
      }
    },
    /**
     * @see gridviewChungTu_FocusedRowChanged(let index)
     */
    async gridviewChungTu_FocusedRowChangedByIndex(index) {
      console.log('gridviewChungTu_FocusedRowChangedByIndex')
      console.log(index)
      // try
      // {
      this.tsbtnTraLai.visible = false
      this.kho_nhan_goc = 0
      this.vchungtu_id = this.gridChungTu.list[index].CHUNGTU_ID
      this.txtPhieuDeNghi.value = this.ma_dn_luoi
      // this.dtLuuVT = await this.callApiWrapper(api.ds_vat_tu_chung_tu_v1, { chungtu_id: this.vchungtu_id })
      // this.dtVatTuXuat = this.dtLuuVT
      this.ttph_id = this.gridChungTu.list[index].TTPH_ID
      this.kieunhap = 0
      this.txtSoPhieu.value = this.gridChungTu.list[index].SOPHIEU
      if (this.txtSoPhieu.value != null && this.txtSoPhieu.value.startsWith('QT')) {
        this.tsbtnDongBoQLTS.visible = true
      }
      this.txtNoiDung.value = this.gridChungTu.list[index].ND_GIAO
      this.kieu_td = this.gridChungTu.list[index].KIEU
      // Ai có quyền 2501 mới hiển thị
      if (this.dsQuyen.includes(2501) /*&& ttph_id == 4*/) this.tsbtnSuaCT.visible = true
      else if (this.dsQuyen.includes(2507)) {
        //Thuộc TTVT và có quyền 2507 thÏ hiển thị sửa ct ở form nhập lẻ
        if (this.Tag == '2' && this.ttnd.donvi_dl_id != 46) this.tsbtnSuaCT.visible = true
        else this.tsbtnSuaCT.visible = false
      } else this.tsbtnSuaCT.visible = false
      this.cboCongTrinh.value = null
      if (this.gridChungTu.list[index].CONGTRINH_ID != null) {
        this.cboCongTrinh.value = this.gridChungTu.list[index].CONGTRINH_ID
        setTimeout(() => {
          this.cboCongTrinh.value = this.gridChungTu.list[index].CONGTRINH_ID
        }, 1000)
      }
      if (this.gridChungTu.list[index].LOAI == '1') {
        this.cboLoaiChungTu.value = this.gridChungTu.list[index].LOAICT_ID
      } else {
        this.cboLoaiChungTu.value = 99
      }
      this.cboKieuPhieu.value = this.gridChungTu.list[index].LOAIPHIEU_ID
      this.cboMucDich.value = this.gridChungTu.list[index].MUCDICH_ID
      this.txtPhieuLuanChuyen.value = this.gridChungTu.list[index].LENH_CHUYEN
      this.txtPhieuLuanChuyen.id = this.gridChungTu.list[index].LENHCHUYEN_ID
      let d_s = await this.callApiWrapper(api.thong_tin_ngay_don_hang, { chungtu_id: this.vchungtu_id })
      if (d_s.length > 0) {
        this.ngay_dh = d_s[0]
      }
      //TODO: check lai nut tra phieu
      let d_sngay = await this.callApiWrapper(api.thong_tin_ngay_chung_tu, { chungtu_id: this.vchungtu_id })
      if (d_sngay.length > 0) {
        this.dtpNgayGuiTT.value = d_sngay[0].ngayGuiTT != null ? (this.dtpNgayGuiTT.value = d_sngay[0].ngayGuiTT.replaceAll('-', '/')) : null
        this.dtpNgayNhanTT.value = null
        if (!isNullOrEmpty(d_sngay[0].ngayNhanTT)) {
          if (this.kiemtra_quyen) {
            this.tsbtnTraLai.visible = true
          }
          this.dtpNgayNhanTT.value = d_sngay[0].ngayNhanTT.replaceAll('-', '/')
        }
        this.dtpNgayVanBan.value = d_sngay[0].ngayVb != null ? (this.dtpNgayVanBan.value = d_sngay[0].ngayVb.replaceAll('-', '/')) : null
        this.dtpNgayChuyenKT.value = d_sngay[0].ngayCtKt != null ? d_sngay[0].ngayCtKt.replaceAll('-', '/') : null
        this.dtpNgayKy.value = d_sngay[0].ngayXd != null ? (this.dtpNgayKy.value = d_sngay[0].ngayXd.replaceAll('-', '/')) : null
      }
      this.vma_md = ''
      this.dc_ct_noibo = false
      this.dc_ct_ttvt = false
      this.dc_ct_ldscdp = false
      this.bqlda_nguonvon_dautu = false
      this.bqlda_nguonvon_khac = false
      let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
      if (d.length > 0) this.vma_md = d[0]
      if (this.vma_md == '1.NBH') {
        // splitContainer1.Panel2Collapsed = false
      } else if (this.vma_md == 'DC_CT' && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
        this.dc_ct_noibo = true
        // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      } else if (this.vma_md == 'DC_CT_TTVT' && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
        this.dc_ct_ttvt = true
        // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      } else if ((this.vma_md == 'DC_CTDP' || this.vma_md == 'DC_LDSC') && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
        this.dc_ct_ldscdp = true
        // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      } else if (this.vma_md == 'DC_ĐT.XDCB' && this.cboLoaiChungTu.value == '2') {
        this.vbqlda_nguonvon_dautu = true
        // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      } else if (this.vma_md == 'DC_SCL.DDTS' && this.cboLoaiChungTu.value == '2') {
        this.vbqlda_nguonvon_khac = true
        // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      } else {
        // splitContainer1.Panel2Collapsed = true
      }
      // this.cboTuKho.value -= cboTuKho_EditValueChanged
      this.cboTuKho.value = this.gridChungTu.list[index].KHO_GIAO_ID
      // cboTuKho.EditValueChanged += cboTuKho_EditValueChanged
      if (this.gridChungTu.list[index].STATUS != null && this.gridChungTu.list[index].STATUS == '1') this.status = 1
      else this.status = 0
      this.t_kho_id = this.gridChungTu.list[index].KHO_GIAO_ID
      this.t_kho = this.gridChungTu.list[index].KHO_GIAO
      // cboDenKho.EditValueChanged -= cboDenKho_EditValueChanged
      this.cboDenKho.value = this.gridChungTu.list[index].KHO_NHAN_ID
      if (this.gridChungTu.list[index].KHO_NHAN_ID != null && this.gridChungTu.list[index].KHO_NHAN_ID != null) {
        this.kho_nhan_goc = this.gridChungTu.list[index].KHO_NHAN_ID
      }
      // cboDenKho.EditValueChanged += cboDenKho_EditValueChanged
      this.dtpNgayDK.value = this.gridChungTu.list[index].NGAY_CT
      //TODO check lai
      let kt_kieu = await this.callApiWrapper(api.ds_kieu_chung_tu, { chungtu_id: this.vchungtu_id })
      // gridVatTu.Columns.SOLUONG.OptionsColumn.AllowEdit = true
      if (this.kt_kieu > 0) {
        // this.gridVatTu.Columns.SOLUONG.OptionsColumn.AllowEdit = false
      }
      // grcFileCV.list = []
      let file_cv = this.gridChungTu.list[index].SL_VANBAN
      if (file_cv != '0') {
        let sfile_data = await this.callApiWrapper(api.ds_van_ban_ct, { chungtu_id: this.vchungtu_id })
        if (sfile_data.length > 0) this.file_temp = sfile_data[0].FILE_CV
        if (sfile_data != null && sfile_data.length > 0) {
          let dt_z = []
          // dt_z = sfile_data.map((e) => {
          //   return e
          // })
          for (let dr of sfile_data) {
            let sfile = dr.FILE_CV
            let words
            words = sfile.split('|')
            for (let i of words) {
              if (!isNullOrEmpty(i)) {
                let t = i
                let tenfile = i.split('/')
                let t_t = tenfile[tenfile.length - 1]
                dt_z.push({ nameFile: t_t, linkFile: i })
              }
            }
          }
          this.grcFileCV.list = dt_z
        } else {
          this.grcFileCV.list = []
        }
      } else {
        this.grcFileCV.list = []
      }
      let kt_ct_cha = await this.callApiWrapper(api.kiem_tra_chung_tu_cha, { chungtu_id: this.vchungtu_id })
      if (this.vma_md == 'X.HTVT' && kt_ct_cha.length > 0) this.colDelFile.visible = false
      else this.colDelFile.visible = true
      await this.Load_ds_vattu()
      await this.Load_ds_thietbi()
      this.txtNoiDung.value = this.gridChungTu.list[index].ND_GIAO
      this.txtLenhVanBan.value = this.gridChungTu.list[index].SO_VB
      this.cboNghiepVu.value = this.nghiepvu_id_chungtu
      this.cboMa_PDA.value = this.ma_pda_chungtu
      // layoutMauDG.visible = nghiepvu_id_chungtu == "288" ? DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      //     : DevExpress.XtraLayout.Utils.LayoutVisibility.Never
      // layoutNhapExDG.visible = nghiepvu_id_chungtu == "288" ? DevExpress.XtraLayout.Utils.LayoutVisibility.Always
      //     : DevExpress.XtraLayout.Utils.LayoutVisibility .Never
      this.spda.cboIMS_CSHT = this.gridChungTu.list[index].MA_CSHT
      this.spda.cboMa_DV_SD = this.gridChungTu.list[index].MA_DV_SD
      this.spda.cboMa_DV_QL = this.gridChungTu.list[index].MA_DV_QL
      this.spda.cboHD_XuatIMS = this.gridChungTu.list[index].CONTRACT_INFO_ID
      this.SetButton(3)
      await this.LAY_DS_VTCT_SERIAL()
      if (this.tsbtnNhapMoi.enabled && this.cboLoaiChungTu.value == 99 && this.ttph_id != 4) {
        this.AN_GROUP(false)
      } else {
        this.AN_GROUP(true)
      }
      //TODO
      let ketqua = await this.callApiWrapper(api.kiem_tra_ct_dieu_chinh, {
        chungTuId: this.vchungtu_id,
        kieu: 1,
        tuNgay: getDateStringOrDefault(this.dtpNgayGuiTT.value),
        denNgay: getDateStringOrDefault(this.dtpNgayNhanTT.value)
      })
      let res = ketqua
      if (res != '1' && res != '0' && res != '2' && res != '3') {
        this.$toast.warning(`${res}`)
        return
      }
      if (res == '3' || res == '2') {
        this.cboTuKho.enabled = true
        this.tsbtnThemVT.enabled = false
      }
      if (!this.xacnhan_in_chungtu) {
        if (this.gridChungTu.list[index].SOLAN_IN == null || this.gridChungTu.list[index].SOLAN_IN.length == 0) this.lblSoLanXemBC.value = 'Số lần xem BC: 0'
        else this.lblSoLanXemBC.value = 'Số lần xem BC: ' + this.gridChungTu.list[index].SOLAN_IN
      }
      this.xTabTongHop.visible = false
      this.colSL_CTBI.visible = false
      this.tsbtnUpPXK.visible = false
      if (this.chonvattu_lohang) {
        let ketquaz = await this.callApiWrapper(api.kiem_tra_chi_tiet_vat_tu, { chungtu_id: this.vchungtu_id })
        if (ketquaz[0][0] != '0') {
          this.xTabTongHop.visible = true
          this.colSL_CTBI.visible = true
          this.NAP_DS_VT_GROUP()
          if ((this.dsQuyen.includes(DS_QUYEN_VT.HOANTHIEN_CHUNGTU) || this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)) && this.vma_md == MUCDICH_VT.CHUYEN_CP) {
            this.tsbtnUpPXK.visible = true
          }
        }
      }
      // #region NET
      if (this.gridChungTu.list[index].NGUOI_TIEPNHAN != '' && this.gridChungTu.list[index].NGUOI_TIEPNHAN == (await this.$root.token.getUserName()) && this.dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)) {
        // colTKDU.OptionsColumn.AllowEdit = true
      } else {
        // colTKDU.OptionsColumn.AllowEdit = false
      }
      // #endregion
      // #region HCM
      if ((await this.$root.token.getMaTinh()) == 'HCM') {
        if (this.gridChungTu.list[index].NGUOI_CN == (await this.$root.token.getUserName()) && this.dsQuyen.includes(2507)) {
          // colTKDU.OptionsColumn.AllowEdit = true
        } else {
          // colTKDU.OptionsColumn.AllowEdit = false
        }
      }
      // #endregion
      // catch (e)
      // {
      //     this.$toast.error(e)
      // }
    },
    async gridViewVatTu_ValidateRow(arg) {
      console.log('gridViewVatTu_ValidateRow')
      let gridViewVatTu_EditRow = this.gridVatTu.list.find((a) => a.ID_GEN == arg.rowData.ID_GEN)
      let soluong = gridViewVatTu_EditRow.SOLUONG
      let dongia = gridViewVatTu_EditRow.DONGIA
      let tien = gridViewVatTu_EditRow.TIEN
      console.log(gridViewVatTu_EditRow)

      if (arg.columnName == 'SOLUONG') {
        soluong = arg.value
      } else if (arg.columnName == 'DONGIA') {
        dongia = arg.value
      } else if (arg.columnName == 'TIEN') {
        tien = arg.value
      } else if (arg.columnName == 'GHICHU') {
        gridViewVatTu_EditRow.GHICHU = arg.value
        if (![null, 0].includes(gridViewVatTu_EditRow.CHUNGTU_ID)) {
          await await this.callApiWrapper(api.sp_capnhat_cacmuc_chitiet_ct, {
            vchungtu_id: gridViewVatTu_EditRow.CHUNGTU_ID,
            vctct_id: gridViewVatTu_EditRow.CTCT_ID,
            vkieu: 0,
            vdata: JSON.stringify([{ GHICHU: gridViewVatTu_EditRow.GHICHU }])
          })
        }
      } else if (arg.columnName == 'MATHETS') {
        gridViewVatTu_EditRow.MATHETS = arg.value
      }

      let lohang = gridViewVatTu_EditRow.LOHANG
      let ctct_id = gridViewVatTu_EditRow.CTCT_ID
      let vattu_id = gridViewVatTu_EditRow.VATTU_ID
      let kho_id = this.cboTuKho.value

      if (arg.columnName == 'SOLUONG') {
        if (arg.value <= 0) {
          this.$toast.error('Số lượng cấp phải lớn hơn 0!')
          // this.$refs.gridVatTu.grid.closeEdit()
          this.$refs.gridVatTu.grid.refreshColumns()
          return
        }
      }

      let s = await this.callApiWrapper(api.kiem_tra_ton_kho, {
        khoId: kho_id,
        soLuong: soluong,
        vatTuId: vattu_id,
        loHang: lohang,
        ctctId: ctct_id,
        ngayCT: getDateStringOrDefault(this.dtpNgayDK.value, null, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT)
      })
      if (s != '1') {
        this.$toast.error('Lỗi: Xuất quá lượng tồn kho của vật tư!')
        this.$refs.gridVatTu.grid.refreshColumns()
        return
      }

      let tien_goc = soluong * dongia

      if (arg.columnName == 'TIEN') {
        if (this.$auth.getMaTinh() == 'BTE') {
          if (Math.abs(tien - tien_goc) > 10 && !lohang.startsWith('DK')) {
            this.$toast.error('Bạn không thể sửa quá số tiền so với số tiền gốc 10 đồng !')
            this.$refs.gridVatTu.grid.refreshColumns()
            return
          }
        } else if (Math.abs(tien - tien_goc) > 10) {
          // đang lỗi ko refresh grid đc do freeze column
          this.$refs.gridVatTu.grid.refreshColumns()
          // this.$refs.gridVatTu.grid.refresh()
          this.$toast.error('Bạn không thể sửa quá số tiền so với số tiền gốc 10 đồng !')
          return
        }
      }

      if (arg.columnName == 'SOLUONG') {
        tien = arg.value * dongia // arg.value new edited value
        gridViewVatTu_EditRow.TIEN = tien
        gridViewVatTu_EditRow.SOLUONG = arg.value //
      }
      if (arg.columnName == 'TIEN') {
        gridViewVatTu_EditRow.TIEN = arg.value
      }
      this.$refs.gridVatTu.grid.refreshColumns()
    },
    /**
     * @see NAP_DS_VT_GROUP
     */
    async NAP_DS_VT_GROUP() {
      this.gridVTGroup.list = await this.callApiWrapper(api.ds_vat_tu_nhap_xuat, { chungtu_id: this.chungTuId })
      // this.enabled.gridVatTu.SOLUONG = false
    },
    /**
     * @see: private void SetButton(let kieu)
     */
    async SetButton(kieu) {
      this.copyct = false
      this.tsbtnNhapMoi.enabled = false
      this.tsbtnGhiLai.enabled = false
      this.tsbtnXoa.enabled = false
      this.tsbtnHuyBo.enabled = false
      this.tsbtnHoanThanh.enabled = false
      this.tstbtnDonHang.enabled = false
      this.tsbtnThemVT.enabled = false
      this.tsbtnSinhSr.enabled = false
      this.tsbtnCopyCT.enabled = false
      this.tsbtnDongBo.enabled = false

      if (kieu == -1) {
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
      }
      if (kieu == 0) {
        // txtSoPhieu.Focus()
        this.tsbtnNhapMoi.enabled = true
        this.Clear()
      }
      if (kieu == 1) {
        this.vchungtu_id = 0
        // txtSoPhieu.Focus()
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
        this.ngay_dh = DateTimeHelper.MinValue()
        this.tstbtnDonHang.enabled = true
        this.tsbtnThemVT.enabled = true
        this.Clear()
        this.dtVatTu = []
        this.dtThietBi = []
        this.ttph_id = 1
      }
      if (kieu == 2) {
        this.tsbtnNhapMoi.enabled = true
        this.tsbtnXoa.enabled = true
        this.Clear()
      }
      if (kieu == 3) {
        this.tsbtnNhapMoi.enabled = true
        this.tsbtnXoa.enabled = true
        this.tsbtnGhiLai.enabled = true
        this.tsbtnHuyBo.enabled = true
        this.tsbtnThemVT.enabled = this.ttph_id != 4
        this.tstbtnDonHang.enabled = this.cboLoaiChungTu.value == '1' && this.ttph_id != 4
        this.tsbtnHoanThanh.enabled = this.ttph_id != 4
        this.tsbtnSinhSr.enabled = true
        this.tsbtnCopyCT.enabled = true
        this.tsbtnDongBo.enabled = true
        this.tsbtnUpPXK.visible = false
      }
      switch (this.cboLoaiChungTu.value) {
        case 1:
          this.tstbtnDonHang.enabled = true
          this.tsbtnPhieuNhap.enabled = true
          this.tsbtnPhieuGiaoHoanTraVT.enabled = true
          this.tsbtnBBGNNhap.enabled = true
          break
        case 2:
          this.tsbtnPhieuXuat.enabled = true
          this.tstbtnDonHang.enabled = false
          this.tsbtnBBGNXuat.enabled = true
          break
        case 3:
          this.tsbtnPhieuChuyen.enabled = true
          this.tstPhieuTUVT.enabled = true
          this.tstbtnDonHang.enabled = false
          this.tsbtnInXuatDVTC.enabled = true
          break
        case 99:
          this.tsbtnBBGNNhap.enabled = true
          this.tstbtnDonHang.enabled = false
          this.tsbtnPhieuNhap.enabled = true
          this.tsbtnPhieuGiaoHoanTraVT.enabled = true
          break
        default:
          break
      }
      // Load quyền
      this.tsbtnNhapMoi.visible = false
      this.tsbtnXoa.visible = false
      this.tsbtnGhiLai.visible = false
      this.tsbtnHuyBo.visible = false
      this.tsbtnThemVT.visible = false
      this.tstbtnDonHang.visible = false
      this.tsbtnHoanThanh.visible = false
      this.tsbtnSinhSr.visible = false
      this.tsbtnCopyCT.visible = false
      this.tsbtnDongBo.visible = false // quyen dong bo
      this.tsbtnUpPXK.visible = false
      this.btnXoaHetVT.visible = false
      this.simpleButton1.visible = false
      if (
        this.dsQuyen.includes(DS_QUYEN_VT.CAPNHAT_CHUNGTU) ||
        this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)
        //|| dsQuyen.includes(0) // quyền quản trị hệ thống
      ) {
        this.tsbtnNhapMoi.visible = true
        this.tsbtnXoa.visible = true
        this.tsbtnGhiLai.visible = true
        this.tsbtnHuyBo.visible = true
        this.tsbtnThemVT.visible = true
        this.tstbtnDonHang.visible = true
        this.tsbtnCopyCT.visible = true
        this.tsbtnDongBo.visible = true
        //gridVatTu.Columns.colXoaVT.visible = true
        this.btnXoaHetVT.visible = true
      }
      if (
        this.dsQuyen.includes(DS_QUYEN_VT.HOANTHIEN_CHUNGTU) ||
        this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)
        //|| dsQuyen.includes(0) // quyền quản trị hệ thống
      ) {
        this.tsbtnHoanThanh.visible = true
        this.tsbtnSinhSr.visible = true
        this.simpleButton1.visible = true
      }
      //TODO: tam them
      this.cboDenKho.visible = true
    },
    /**
     * @see: private void Clear()
     */
    async Clear() {
      this.xTabTongHop.visible = false
      this.colSL_CTBI.visible = false
      this.ds_Gan = []
      this.dc_ct_noibo = false
      this.dc_ct_ttvt = false
      this.dc_ct_ldscdp = false
      this.bqlda_nguonvon_dautu = false
      this.bqlda_nguonvon_khac = false
      this.txtSoPhieu.value = ''
      this.cboMucDich.value = null
      this.cboTuKho.value = null
      this.cboDenKho.value = null
      this.txtNoiDung.value = ''
      this.dtpNgayDK.value = moment().format(FORM_CONFIG.DEFAULT_DATE_FORMAT)
      this.gridVatTu.list = []
      this.gridThietBi.list = []
      this.dtLuuVT = []
      this.copyct = false
      this.dtpNgayGuiTT.value = null
      this.dtpNgayNhanTT.value = null
      this.dtpNgayChuyenKT.value = null
      this.dtpNgayKy.value = null
      this.vma_md = ''
      this.txtLenhVanBan.value = ''
      this.dtpNgayVanBan.value = null
      this.cboKieuPhieu.value = null
      this.cboCongTrinh.value = null
      this.grcFileCV.list = []
      this.cboMa_PDA.value = ''
      this.cboNghiepVu.value = null
      this.txtPhieuDeNghi.value = ''
      this.spda = {}
      //TODO: sửa lại cho ngon đoạn chọn value này
      this.gridChungTu.value = null
      this.txtPhieuLuanChuyen.value = null
      this.txtPhieuLuanChuyen.id = null
      // layoutbtnTBi.visible = DevExpress.XtraLayout.Utils.LayoutVisibility.Never
    },
    /**
     * @see: private void tsbtnGiaoPhieu_Click(object sender, EventArgs e)
     */
    async tsbtnGiaoPhieu_Click() {
      if (this.vchungtu_id == 0) {
        this.$toast.error('Bạn phải chọn chứng từ để xóa')
        return
      }
      if (!(await this.KiemTraNguoiCN())) {
        this.$toast.error('Chỉ có người tạo phiếu mới có thể giao đề nghị')
        return
      }
      let denghi_luoi_id = this.gridChungTu.value == null ? -1 : this.gridChungTu.value.DENGHI_ID
      if (denghi_luoi_id > 0) {
        let qt = await this.callApiWrapper(api.lay_quytrinh_huonggiao_theo_denghi_id, { denghi_id: denghi_luoi_id })
        if (qt.length <= 0) {
          this.$toast.warning('Quy trình chưa được thiết lập')
        } else {
          let quytrinh_id = qt[0].quytrinh_id
          let huonggiao_id = qt[0].huonggiao_id
          let d = await this.callApiWrapper(api.sp_lay_huonggiao_tieptheo, { huonggiao_id: huonggiao_id })
          if (d.length > 0) {
            let huonggiao_tieptheo_id = d[0].huonggiao_id
            let frm = this.$refs.frmGiaoPhieu
            frm.frmGiaoPhieuHG(huonggiao_tieptheo_id)
            frm.parentForm = 'NhapXuatVT2'
            frm.openDialog()
          }
        }
      }
    },
    async closeGiaoPhieu() {
      try {
        await this.Load_ds_chungtu()
        // if (this.gridChungTu.length > 0){
        //   await this.FocusGridViewRow('gridviewChungTu', "CHUNGTU_ID", this.gridChungTu[0].CHUNGTU_ID)
        // }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
      }
    },
    /**
     * @see: private void CapNhatChiTiet_CT(bool themmoi)
     */
    async CapNhatChiTiet_CT(themmoi) {
      if (this.kieunhap == 0 && this.kieugoi_id != 2) {
        let json = this.dtVatTu
        if (this.xTabTongHop.visible == true && this.chonvattu_lohang) {
          json = this.dtVatTu
          let dt_group = this.gridVTGroup.list
          let json_lohang = '0'
          if (this.ds_Gan != null && this.ds_Gan.length > 0) {
            let ds_gan_copy = this.ds_Gan
            for (let i = this.ds_gan_copy.length - 1; i >= 0; i--) {
              // if (ds_gan_copy.Columns[i] != "VATTU_ID" && ds_gan_copy.Columns[i] != "LOHANG")
              //     ds_gan_copy.Columns.Remove(ds_gan_copy.Columns[i])
            }
            json_lohang = this.ds_gan_copy
          }
          let dt_group_copy = this.dt_group
          for (let i = dt_group_copy.Columns.length - 1; i >= 0; i--) {
            // if (dt_group_copy.Columns[i] != "VATTU_ID" && dt_group_copy.Columns[i] != "SL_XUAT")
            //     dt_group_copy.Columns.Remove(dt_group_copy.Columns[i])
          }
          let json_vattu = dt_group_copy
          let action = await this.callApiWrapper(api.cap_nhat_chung_tu_v2, {
            data: this.dtVatTu,
            chungTuId: this.vchungtu_id,
            kieuNhap: 0,
            kieuGoiId: 1,
            themMoi: themmoi,
            dataDs: dt_group_copy,
            dataLoHang: ds_gan_copy
          })
          if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
          else this.$toast.error(`${action}`)
        } else {
          if (this.dsQuyen.includes(2510) && this.vma_md == '2.XCP' && this.cboLoaiChungTu.value == '2') {
            let action = await this.cap_nhat_chung_tu_nhap({
              data: this.dtVatTu,
              chungTuId: this.vchungtu_id,
              kieuNhap: 10,
              kieuGoiId: 1,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          } else {
            let action = await this.cap_nhat_chung_tu_nhap({
              data: this.dtVatTu,
              chungTuId: this.vchungtu_id,
              kieuNhap: 0,
              kieuGoiId: 1,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          }
        }
      }
      if (this.kieunhap == 1 && this.kieugoi_id != 2) {
        this.dt_dh_ct = []
        let keys = []
        // for(const i = 0; i<this.dtDonHang.length; i++) {
        //   let key1 = await this.callApiWrapper(api.get_key, {
        //     keyName: 'CTCT_ID',
        //     numBlockSize: 1,
        //     numRetry: 10
        //   })
        //   keys.push(key1)
        // }

        const forLoop = async (_) => {
          for (let i = 0; index < this.dtDonHang.length; i++) {
            let key1 = await this.callApiWrapper(api.get_key, {
              keyName: 'CTCT_ID',
              numBlockSize: 1,
              numRetry: 10
            })
            keys.push(key1)
          }
        }
        for (let i = 0; i < this.dtDonHang.length; i++) {
          //TODO: toi uu sau
          let ctct_id = await this.callApiWrapper(api.get_key, {
            keyName: 'CTCT_ID',
            numBlockSize: 1,
            numRetry: 10
          })
          let dr = {}
          let item = this.dtDonHang[i]
          dr.CTCT_ID = ctct_id
          dr.CTDH_ID = item.CTDH_ID
          dr.MA_VT = item.MA_VT
          dr.LOHANG = item.LOHANG
          dr.SOLUONG = item.SOLUONG
          dr.DONGIA = item.DONGIA
          dr.TIEN = item.TIEN
          dr.SOTHANG_PB = item.SOTHANG_PB
          dr.NGUOI_CN = await this.$root.token.getUserName()
          dr.MAY_CN = await this.$root.token.getMachine()
          dr.IP_CN = await this.$root.token.getIP()
          dr.LOAIKHO = item.LOAIKHO
          dr.MAKHOTD = item.MAKHOTD
          dr.CHATLUONG_ID = item.CHATLUONG_ID
          this.dt_dh_ct.push(dr)
        }
        let json = JSON.stringify(this.dt_dh_ct)
        let action = await this.callApiWrapper(api.fn_capnhat_chungtu, {
          p_data: json,
          p_chungtu_id: this.vchungtu_id,
          p_kieu_nhap: 1,
          p_kieugoi_id: 1,
          p_themmoi: themmoi ? 1 : 0
        })
        if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
        else this.$toast.error(`${action}`)
      }
      if (this.kieunhap == 2 && (this.kieugoi_id != 2 || this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac)) {
        // Nhập từ kho
        if (this.copyct == false) {
          let json = this.dtThemVT
          if (this.xTabTongHop.visible == true && this.chonvattu_lohang) {
            let dt_group = this.gridVTGroup.list
            let ds_gan_copy = this.ds_Gan
            // for (let i = ds_gan_copy.length - 1 i >= 0 i--) {
            //   if (ds_gan_copy.Columns[i] != 'VATTU_ID' && ds_gan_copy.Columns[i] != 'LOHANG') ds_gan_copy.Columns.Remove(ds_gan_copy.Columns[i])
            // }
            let json_lohang = this.ds_gan_copy
            let dt_group_copy = this.dt_group
            // for (let i = dt_group_copy.length - 1 i >= 0 i--) {
            //   if (dt_group_copy.Columns[i] != 'VATTU_ID' && dt_group_copy.Columns[i] != 'SL_XUAT') dt_group_copy.Columns.Remove(dt_group_copy.Columns[i])
            // }
            let json_vattu = this.dt_group_copy
            let action = await this.callApiWrapper(api.cap_nhat_chung_tu_v2, {
              data: this.dtThemVT,
              chungTuId: this.vchungtu_id,
              kieuNhap: 2,
              kieuGoiId: 1,
              themMoi: themmoi ? 1 : 0,
              dataDs: dt_group_copy,
              dataLoHang: ds_gan_copy
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          } else {
            let action = await this.cap_nhat_chung_tu_nhap({
              data: this.dtThemVT,
              chungTuId: this.vchungtu_id,
              kieuNhap: 2,
              kieuGoiId: 1,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          }
        } else {
          let json = this.dtCopyCTCT
          let action = await this.cap_nhat_chung_tu_nhap({
            data: this.dtThemVT,
            chungTuId: this.vchungtu_id,
            kieuNhap: 2,
            kieuGoiId: 1,
            themMoi: themmoi ? 1 : 0
          })
          if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
          else this.$toast.error(`${action}`)
        }
      }
      if (this.kieugoi_id == 2) {
        // Nhập lẻ
        if (this.kieunhap == 3) {
          let t = this.ds_md.filter((a) => a.mucDichId == this.cboMucDich.value)
          if (t.length > 0 && (t[0].maMd == 'N-X.DCMSL' || t[0].maMd == 'N.DCDG' || t[0].maMd == 'SC_BH_DONGIA' || t[0].maMd == 'N.TTCL') && this.cboLoaiChungTu.value == 99 && (await this.get_mamd(this.cboKieuPhieu.value)) == '10') {
            let ztemp = []
            if (t[0].maMd == 'N-X.DCMSL') {
              ztemp = this.dtVT_LE
            } else {
              ztemp = this.dt_temp
            }
            let s = this.gridVatTu.list
            let json = ztemp
            let action = await this.cap_nhat_chung_tu_nhap({
              data: ztemp,
              chungTuId: this.vchungtu_id,
              kieuNhap: 99,
              kieuGoiId: 2,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!!')
            else this.$toast.error(`${action}`)
          } else {
            this.dtchitiet_ct = []
            let dr
            for (let j = 0; j < this.dtVT_LE.length; j++) {
              let i = this.dtVT_LE[j]
              dr = {}
              dr.CTCT_ID = i.CTCT_ID
              dr.VATTU_ID = i.VATTU_ID
              dr.SOLUONG = i.SOLUONG
              dr.DONGIA = i.DONGIA
              dr.TIEN = i.TIEN
              dr.NGUOI_CN = await this.$root.token.getUserName()
              dr.MAY_CN = await this.$root.token.getMachine()
              dr.IP_CN = await this.$root.token.getIP()
              dr.SOTHANG_PB = i.sothang_pb == '' || i.sothang_pb == null ? 0 : i.sothang_pb
              dr.HAN_BH = i.HAN_BH == '' || i.HAN_BH == null ? '' : i.HAN_BH
              dr.MAKHOTD = i.MAKHOTD
              dr.LOAIKHO = i.LOAIKHO
              dr.MATHUNG = i.MATHUNG
              dr.MATHETS = i.MATHETS
              dr.GHICHU = i.GHICHU
              dr.TY_LE = i.TY_LE
              dr.CHATLUONG = i.CHATLUONG
              dr.NGUON_GOC = i.NGUON_GOC
              dr.DONGTBI_ID = i.DONGTBI_ID
              dr.HETHONG_ID = i.HETHONG_ID
              dr.CHATLUONG_ID = i.CHATLUONG_ID
              dr.TINHTRANG = i.TINHTRANG
              this.dtchitiet_ct.push(dr)
            }
            let json = this.dtchitiet_ct
            let action = await this.cap_nhat_chung_tu_nhap({
              data: this.dtchitiet_ct,
              chungTuId: this.vchungtu_id,
              kieuNhap: 3,
              kieuGoiId: 2,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          }
        } else {
          let t = this.ds_md.filter((a) => a.mucDichId == this.cboMucDich.value)
          if (t.length > 0 && ['N.DCDG', 'N.TTCL', 'SC_BH_DONGIA', 'DC_ĐT.XDCB', 'DC_SCL.DDTS', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(t[0].maMd) && this.cboLoaiChungTu.value == 99 && (await this.get_mamd(this.cboKieuPhieu.value)) == '10') {
            let dtchitiet = this.gridVatTu.list
            let json = dtchitiet
            let action = await this.cap_nhat_chung_tu_nhap({
              data: dtchitiet,
              chungTuId: this.vchungtu_id,
              kieuNhap: 99,
              kieuGoiId: 2,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          } else {
            let dstb = this.gridVatTu.list
            if (dstb.length > 0) {
              for (let j = 0; j < dstb.length; j++) {
                if (dstb[j].LOAITBI_ID != '2') this.dtVatTu[j].MATHUNG = dstb[j].MATHUNG
              }
            }
            let json = this.dtVatTu
            let action = await this.cap_nhat_chung_tu_nhap({
              data: this.dtVatTu,
              chungTuId: this.vchungtu_id,
              kieuNhap: -1,
              kieuGoiId: 2,
              themMoi: themmoi ? 1 : 0
            })
            if (action == '1') this.$toast.success('Cập nhật chứng từ thành công!')
            else this.$toast.error(`${action}`)
          }
        }
        this.gridChungTu.oldSoPhieu = this.txtSoPhieu.value
      }
    },
    /**
     * @see: private void tsbtnNhapMoi_Click(object sender, EventArgs e)
     */
    async tsbtnNhapMoi_Click() {
      this.SetButton(1)
      this.cboLoaiChungTu.value = this.setTag(this.tag)
    },
    /**
     * @see: private void CapNhat_CT(let kieu, bool themmoi, bool traphieu, out let kq)
     */
    async CapNhat_CT(kieu, themmoi, traphieu) {
      console.log(`CapNhat_CT kieu ${kieu} | themmoi ${themmoi} | traphieu ${traphieu}`)
      let kq = ''
      let loaict_id = null
      let xuat_hoantra = false
      if (this.ds_md != null && this.ds_md.length > 0) {
        let t = this.ds_md.filter((a) => a.mucDichId == this.cboMucDich.value)
        if (t.length > 0 && (t[0].maMd == 'X.HTVT' || t[0].maMd == 'X.TDVT')) {
          if (this.cboLoaiChungTu.value == '2' || this.cboLoaiChungTu.value == '3') {
            xuat_hoantra = true
          }
        }
      }
      console.log('themmoi')
      console.log(themmoi)
      if (themmoi) {
        this.vchungtu_id = await this.callApiWrapper(api.get_key, {
          keyName: 'CHUNGTU_ID',
          numBlockSize: 1,
          numRetry: 10
        })
        if (this.kieugoi_id == 1) {
          let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
            khoGiaoId: this.cboTuKho.value,
            khoNhanId: this.cboDenKho.value,
            loaiCtId: this.cboLoaiChungTu.value,
            namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
            donViNdId: this.$root.token.getDonViID(),
            loaiPhieuId: this.cboKieuPhieu.value,
            mucDichId: this.cboMucDich.value
          })
          console.log('api.sinh_so_phieu_vt_v5 - 1854')
          console.log(kq)
          if (kq == 'error') {
            this.$toast.error('Có lỗi sinh số phiều!')
            return
          } else {
            this.txtSoPhieu.value = kq[0]
            loaict_id = 1
          }
          loaict_id = this.cboLoaiChungTu.value
        } else {
          let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
            khoGiaoId: this.cboTuKho.value,
            khoNhanId: this.cboDenKho.value,
            loaiCtId: 1,
            namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
            donViNdId: this.$root.token.getDonViID(),
            loaiPhieuId: this.cboKieuPhieu.value,
            mucDichId: this.cboMucDich.value
          })
          console.log('api.sinh_so_phieu_vt_v5  - 1874')
          console.log(kq)
          if (kq == 'error') {
            this.$toast.error('Có lỗi sinh số phiều!')
            return
          } else {
            this.txtSoPhieu.value = kq[0]
            loaict_id = 1
          }
        }
      } else {
        if (moment(this.gridChungTu.value.NGAY_CT, FORM_CONFIG.dateFormat).year() != moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year()) {
          console.log('moment-if')
          if (this.kieugoi_id == 1) {
            let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
              khoGiaoId: this.cboTuKho.value,
              khoNhanId: this.cboDenKho.value,
              loaiCtId: this.cboLoaiChungTu.value,
              namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
              donViNdId: this.$root.token.getDonViID(),
              loaiPhieuId: this.cboKieuPhieu.value,
              mucDichId: this.cboMucDich.value
            })
            if (kq == 'error') {
              this.$toast.error('Có lỗi sinh số phiều!')
              return
            } else {
              this.txtSoPhieu.value = kq[0]
            }
          } else {
            let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
              khoGiaoId: this.cboTuKho.value,
              khoNhanId: this.cboDenKho.value,
              loaiCtId: 1,
              namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
              donViNdId: this.$root.token.getDonViID(),
              loaiPhieuId: this.cboKieuPhieu.value,
              mucDichId: this.cboMucDich.value
            })
            if (kq == 'error') {
              this.$toast.error('Có lỗi sinh số phiều!')
              return
            } else {
              this.txtSoPhieu.value = kq[0]
            }
          }
        } else {
          console.log('moment-else')
          /* Nếu sửa kho đến thÏ sinh lại số phiếu
           */
          console.log(xuat_hoantra)
          if (!xuat_hoantra) {
            if (this.gridChungTu.value.KHO_NHAN_ID != this.cboDenKho.value) {
              if (this.kieugoi_id == 1) {
                let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
                  khoGiaoId: this.cboTuKho.value,
                  khoNhanId: this.cboDenKho.value,
                  loaiCtId: this.cboLoaiChungTu.value,
                  namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
                  donViNdId: this.$root.token.getDonViID(),
                  loaiPhieuId: this.cboKieuPhieu.value,
                  mucDichId: this.cboMucDich.value
                })
                if (kq == 'error') {
                  this.$toast.error('Có lỗi sinh số phiều!')
                  return
                } else {
                  this.txtSoPhieu.value = kq[0]
                }
              } else {
                let kq = await this.callApiWrapper(api.sinh_so_phieu_vt_v5, {
                  khoGiaoId: this.cboTuKho.value,
                  khoNhanId: this.cboDenKho.value,
                  loaiCtId: 1,
                  namCt: moment(this.dtpNgayDK.value, FORM_CONFIG.dateFormat).year(),
                  donViNdId: this.$root.token.getDonViID(),
                  loaiPhieuId: this.cboKieuPhieu.value,
                  mucDichId: this.cboMucDich.value
                })
                if (kq == 'error') {
                  this.$toast.error('Có lỗi sinh số phiều!')
                  return
                } else {
                  this.txtSoPhieu.value = kq[0]
                }
              }
            }
          }
        }
        if (this.kieugoi_id == 2) {
          loaict_id = 1
        } else {
          loaict_id = this.cboLoaiChungTu.value
        }
      }
      let dr = {}
      this.dtct = []
      dr.CHUNGTU_ID = this.vchungtu_id
      dr.LOAICT_ID = loaict_id
      dr.SOPHIEU = this.txtSoPhieu.value
      dr.KHO_GIAO_ID = this.cboTuKho.value
      if (!xuat_hoantra) dr.KHO_NHAN_ID = this.cboDenKho.value
      else dr.KHO_NHAN_ID = null
      if (this.cboNghiepVu.value != null) {
        if (this.cboNghiepVu.value != '-1') dr.NGHIEPVU_ID = this.cboNghiepVu.value
        else dr.NGHIEPVU_ID = null
        if (this.cboNghiepVu.value == '292' || this.cboNghiepVu.value == '293' || this.cboNghiepVu.value == '294') {
          if (this.spda.cboMa_DV_SD != null) dr.MA_DV_SD = this.spda.cboMa_DV_SD
          else dr.MA_DV_SD = null
          if (this.spda.cboIMS_CSHT != '') dr.MA_CSHT = this.spda.cboIMS_CSHT
          else dr.MA_CSHT = null
          if (this.spda.cboMa_DV_QL == null) dr.MA_DV_QL = null
          else dr.MA_DV_QL = this.spda.cboMa_DV_QL
          if (this.spda.cboHD_XuatIMS == null) {
            dr.MA_HD_PO = null
            dr.CONTRACT_INFO_ID = null
          } else {
            dr.MA_HD_PO = this.spda.ContractNumber
            dr.CONTRACT_INFO_ID = this.spda.cboHD_XuatIMS
          }
        }
      } else {
        dr.NGHIEPVU_ID = null
      }
      if (this.cboMa_PDA.value != '') {
        dr.MA_PDA = this.cboMa_PDA.value
      } else {
        dr.MA_PDA = ''
      }
      dr.ND_GIAO = this.txtNoiDung.value
      dr.MUCDICH_ID = this.cboMucDich.value
      dr.NGAYGUI_TT = getDateStringOrDefault(this.dtpNgayGuiTT.value, null, FORM_CONFIG.CAP_NHAT_CT_THEO_KIEU_DATE_FORMAT)
      dr.NGAYNHAN_TT = getDateStringOrDefault(this.dtpNgayNhanTT.value, null, FORM_CONFIG.CAP_NHAT_CT_THEO_KIEU_DATE_FORMAT)
      dr.NGAY_VB = getDateStringOrDefault(this.dtpNgayVanBan.value, null, FORM_CONFIG.CAP_NHAT_CT_THEO_KIEU_DATE_FORMAT)
      dr.SO_VB = this.txtLenhVanBan.value
      dr.LOAIPHIEU_ID = this.cboKieuPhieu.value
      dr.NHANVIEN_GIAO_ID = this.$root.token.getNhanVienID()
      dr.DONVI_NHAN_ID = this.$root.token.getDonViID()
      dr.DONVI_GIAO_ID = this.$root.token.getDonViID()
      dr.NGUOI_CN = await this.$root.token.getUserName()
      if (traphieu) {
        dr.NGUOI_CN = this.gridVatTu.value != null ? (this.gridVatTu.value.NGUOI_CN != null ? this.gridVatTu.value.NGUOI_CN : '') : ''
        dr.NHANVIEN_GIAO_ID = this.gridVatTu.value != null ? (this.gridVatTu.value.NHANVIEN_GIAO_ID != null ? this.gridVatTu.value.NHANVIEN_GIAO_ID : this.$root.token.getNhanVienID()) : this.$root.token.getNhanVienID()
        dr.DONVI_NHAN_ID = this.gridVatTu.value != null ? (this.gridVatTu.value.DONVI_NHAN_ID != null ? this.gridVatTu.value.DONVI_NHAN_ID : this.$root.token.getDonViID()) : this.$root.token.getDonViID()
        dr.DONVI_GIAO_ID = this.gridVatTu.value != null ? (this.gridVatTu.value.DONVI_GIAO_ID != null ? this.gridVatTu.value.DONVI_GIAO_ID : this.$root.token.getDonViID()) : this.$root.token.getDonViID()
      }
      // if (dtpNgayDK.DateTime.Hour == 0 && dtpNgayDK.DateTime.Minute == 0)
      // {
      //     this.dtpNgayDK.DateTime = dtpNgayDK.DateTime.AddMinutes(1)
      // }
      if (this.cboCongTrinh.value != null) {
        dr.CONGTRINH_ID = this.cboCongTrinh.value
      } else {
        dr.CONGTRINH_ID = null
      }
      dr.NGAY_CT = getDateStringOrDefault(this.dtpNgayDK.value, null, FORM_CONFIG.CAP_NHAT_CT_THEO_KIEU_DATE_FORMAT)
      dr.TTPH_ID = 1
      dr.LOAI = this.vloai
      dr.NGAY_CN = this.$root.token.getNgayCapNhat()
      dr.IP_CN = await this.$root.token.getIP()
      dr.MAY_CN = await this.$root.token.getMachine()
      if (traphieu) {
        if (this.tsbtnNhapMoi.enabled) {
          let result = await this.callApiWrapper(api.kiem_tra_ct_dieu_chinh, {
            chungTuId: this.vchungtu_id,
            kieu: 1,
            tuNgay: getDateStringOrDefault(this.dtpNgayGuiTT.value),
            denNgay: getDateStringOrDefault(this.dtpNgayNhanTT.value)
          })
          let res = result
          if (res != '1' && res != '0' && res != '2' && res != '3') {
            this.$toast.warning(`${res}`)
            return
          }
          if (res == '2') {
            let sp = await this.callApiWrapper(api.ds_chung_tu_cha_theo_chung_tu, { chungtu_id: this.vchungtu_id })
            if (sp.length <= 0) {
              this.$toast.error('Không tìm thấy chứng từ xuất điều chỉnh! Hãy xem lại!')
              return
            }
            dr.CHUNGTU_CHA_ID = sp[0]
          }
        }
      } else {
        if (this.tsbtnNhapMoi.enabled) {
          let result = await this.callApiWrapper(api.kiem_tra_ct_dieu_chinh, {
            chungTuId: this.vchungtu_id,
            kieu: 1,
            tuNgay: getDateStringOrDefault(this.dtpNgayGuiTT.value),
            denNgay: getDateStringOrDefault(this.dtpNgayNhanTT.value)
          })
          let res = result
          if (res != '1' && res != '0' && res != '2' && res != '3') {
            this.$toast.warning(`${res}`)
            return
          }
          if (res == '2') {
            let sp = await this.callApiWrapper(api.ds_chung_tu_cha_theo_chung_tu, { chungTuId: this.vchungtu_id })
            if (sp.length <= 0) {
              this.$toast.error('Không tÏm thấy chứng từ xuất điều chỉnh! Hãy xem lại!')
              return
            }
            dr.CHUNGTU_CHA_ID = sp[0]
          }
        } else {
          let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
          let kieuphieu = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboKieuPhieu.value })
          let kieuphieu_tinh = (await this.$root.token.getMaTinh()) == 'HCM' ? 'PN10' : 'PNDC'

          let l = this.cboLoaiChungTu.value
          if (d.length <= 0 && kieuphieu.length <= 0) {
            this.$toast.error('Xem lại kiểu phiếu và mục đích !')
            return
          }
          if (['N.DCDG', 'N.TTCL', 'SC_BH_DONGIA', 'DC_ĐT.XDCB', 'DC_SCL.DDTS', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(d[0]) && l == 99 && kieuphieu[0] == kieuphieu_tinh) {
            let sp = await this.callApiWrapper(api.ds_chung_tu_theo_so_phieu, { so_phieu: this.sophieu_temp })

            if (sp.length <= 0) {
              this.$toast.error('Không tìm thấy chứng từ xuất điều chỉnh! Hãy xem lại!')
              return
            }
            dr.CHUNGTU_CHA_ID = sp[0]
          }
        }
      }
      dr.LUANCHUYEN_ID = this.txtPhieuLuanChuyen.id
      this.dtct.push(dr)
      console.log('cap_nhat_ct_theo_kieu')
      console.log(this.dtct)
      let func = await this.callApiWrapper(api.cap_nhat_ct_theo_kieu, {
        data: this.dtct,
        kieu: kieu
      })
      kq = func
      return kq
    },
    /**
     * @see: private void GHILAI_CT(let vztag)
     */
    async GHILAI_CT(vztag) {
      // try {
      let dtp = this.dtpNgayDK.value
      if (!this.tsbtnNhapMoi.enabled) {
        if (!(await this.KiemTraDuLieu(true, false, vztag))) return
        this.dtpDenNgay.value = dtp
        this.dtpTuNgay.value = moment(new Date().setDate(new Date().getDate() - 7)).format('DD/MM/YYYY')
        if (this.kiemtra_quyen) {
          if (!(await this.KIEMTRA_QUYEN_XN(5, this.cboTuKho.value))) return
        }
        if (!this.KiemTra_NhapLeDieuChinh(false)) return
        if (!(await this.KiemTra_LoaiKho_MaKho())) return
        if (!confirm(`Bạn có chắc chắn muốn ghi lại`)) return
        let kq = ''
        kq = await this.CapNhat_CT(1, true, false)
        if (kq == '1') await this.CapNhatChiTiet_CT(true)
        else this.$toast.error(`Có lỗi khi tạo chứng từ: ${kq}`)
      } else {
        if (this.ttph_id == 4) {
          this.$toast.warning('Chứng từ đã hoàn thành không thể ghi lại!')
          return
        }
        if (!(await this.KiemTraDuLieu(false, false, vztag))) return
        if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, vztag))) return
        if (this.kiemtra_quyen) {
          if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) return
        }
        if (!(await this.KiemTra_NhapLeDieuChinh(false))) return
        if (!(await this.KiemTra_LoaiKho_MaKho())) return
        let msg_add = this.dsQuyen.includes(2510) && this.vma_md == '2.XCP' ? '\nPhiếu xuất 2XCP chỉ cho phép thay đổi kiểu phiếu, nội dung, kho đến, số lượng đặt ~' : ''
        if (!confirm(`Bạn có chắc chắn muốn ghi lại ${msg_add}`)) return
        let kq = await this.CapNhat_CT(2, false, false)
        let k = await this.callApiWrapper(api.xoa_chung_tu_bao_hanh, {
          kieu: 2,
          chungTuId: this.vchungtu_id,
          chungTuCtId: 0,
          thietBiId: 0
        })
        if (k != '1') {
          this.$toast.warning(`${k}`)
          return
        }
        if (this.ttph_id == 4) {
          let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
          if (s != '1') {
            this.$toast.warning(`${s}`)
            return
          }
          if (this.ckbSendSMS.value) {
            try {
              let kq_ = await this.callApiWrapper(api.send_sms_chung_tu, {
                tuDong: 2,
                chungTuId: this.vchungtu_id
              })
            } catch (e) {
              this.$toast.error(`Có lỗi khi send smsm tự động ${ex}`)
            }
          }
          s = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
            chungTuId: this.vchungtu_id,
            nguoiCn: await this.$root.token.getUserName(),
            mayCn: await this.$root.token.getMachine(),
            ipCn: await this.$root.token.getIP()
          })
          if (s != '1') {
            this.$toast.warning(`Có lỗi trong quá trình xóa phiếu! ${s}`)
            return
          }
        }
        if (this.kieugoi_id == 2) {
          console.log('this.cboMucDich.value')
          console.log(this.cboMucDich.value)
          let mucdich_id = this.cboMucDich.value
          let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: mucdich_id })
        }
        //ctct_id_xuat
        if (kq == '1') await this.CapNhatChiTiet_CT(false)
        else this.$toast.warning(`Có lỗi xảy ra:  ${kq}`)
      }
      let vchungtu_tmp_id = this.vchungtu_id
      if (this.vtdk.length > 0) {
        await this.CAPNHAT_DANGKY(this.vchungtu_tmp_id)
      }
      await this.Load_ds_chungtu()
      this.copyct = false
      // } catch (e) {
      //   this.$toast.error(e)
      // }
    },
    /**
     * @see: private void tsbtnGhiLai_Click(object sender, EventArgs e)
     */
    async tsbtnGhiLai_Click() {
      if (this.dsQuyen.includes(2510)) await this.GHILAI_CT('GHILAI_NUTGHILAI')
      else await this.GHILAI_CT('GHILAI')
    },
    /**
     * @see: private void CAPNHAT_DANGKY(let chungtu)
     */
    async CAPNHAT_DANGKY(chungtu) {
      let q = vtdk[0].DANGKY_ID
      await this.callApiWrapper(api.cap_nhat_dang_ky, { chungtu_id: this.vchungtu_id, dangky_id: q })
      let xdoc = []
      this.vtdk.forEach((a) => xdoc.push({ VTDK_ID: a.VTDK_ID, SL_DUYET: a.SL_DUYET }))
      await this.callApiWrapper(api.cap_nhat_vat_tu_dang_ky, {
        dataDangky: JSON.letify(xdoc)
      })
    },
    /**
     * @see: private void tsbtnHuyBo_Click(object sender, EventArgs e)
     */
    async tsbtnHuyBo_Click() {
      this.SetButton(0)
      this.cboLoaiChungTu.value = this.setTag('cboLoaiChungTu')
    },
    /**
     * @see: private bool xoa_con_hoan_tra(let chungtu_id)
     */
    async xoa_con_hoan_tra(chungtu_id) {
      let md = await this.callApiWrapper(api.trang_thai_phieu_tu_chung_tu_id, { chungtu_id: chungtu_id })
      if (md.length <= 0) {
        this.$toast.warning('Có lỗi khi xóa phiếu hoàn trả xuất!')
        return false
      }
      if (md[0][0] == 4) {
        let sss = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: chungtu_id })
        if (sss != '1') {
          this.$toast.warning(`. + chungtu_id +  ${sss}`)
          return false
        }
      }
      let sx = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
        chungTuId: chungtu_id,
        nguoiCn: await this.$root.token.getUserName(),
        mayCn: await this.$root.token.getMachine(),
        ipCn: await this.$root.token.getIP()
      })
      if (sx != '1') {
        this.$toast.warning(`Có lỗi trong quá trình xóa phiếu cha! ${sx} `)
        return false
      }
      await this.callApiWrapper(api.xoa_tat_ca_chi_tiet_chung_tu, { chungtu_id: chungtu_id })
      let t = await this.callApiWrapper(api.xoa_thong_tin_ct, { chungtu_id: chungtu_id })
      return true
    },
    /**
     * @see:private void xoa_phieu_hoan_tra(out let result)
     */
    async xoa_phieu_hoan_tra() {
      let result = ''
      let mucdich_id = this.gridVatTu.value != null ? this.gridVatTu.value.MUCDICH_ID : 0
      let loaict_id = this.gridVatTu.value != null ? this.gridVatTu.value.LOAICT_ID : 0
      if (mucdich_id != 234 && mucdich_id != 278) {
        result = '1'
        return result
      }
      if ((mucdich_id == 234 || mucdich_id == 278) && (loaict_id == 2 || loaict_id == 3)) {
        result = '1'
        return result
      }
      let json = [{ CHUNGTU_ID: this.vchungtu_id }]
      let json_list = json
      let kieu_th = this.$root.token.getMaTinh() == 'NET' ? 5 : 3
      let ketqua = await this.callApiWrapper(api.giao_phieu_hoan_tra, {
        kieuPhieu: 2,
        ttPhId: '',
        khoDenId: '',
        tagFrm: 2,
        kieuTh: kieu_th,
        ghiChu: '',
        donViDenId: 0,
        ngayVb: '',
        soVb: '',
        fileCv: '',
        ds: json_list,
        dsCt: '0',
        dsTbi: '0',
        nguoiCn: await this.$root.token.getUserName(),
        mayCn: this.$root.token.getUserName(),
        ipCn: await this.$root.token.getIP()
      })
      if (ketqua.includes('ORA')) {
        this.$toast.warning(`Lỗi khi xóa phiếu xuất cặp với phiếu nhập hoàn trả: ${ketqua}`)
        result = '0'
        return result
      } else if (ketqua.includes('chungtu_id')) {
        let split = ketqua.split('-')
        let chungtu = split[0]
        result = chungtu
        return result
      } else {
        this.$toast.warning(`${ketqua}`)
        result = '0'
        return result
      }
    },
    /**
     * @see: private void tsbtnXoa_Click(object sender, EventArgs e)
     */
    async tsbtnXoa_Click() {
      // try {
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn phải chọn chứng từ để xóa')
        return
      }
      if (!(await this.KiemTraHanChotCT())) {
        return
      }
      let str = ''
      let d = await this.callApiWrapper(api.so_phieu_theo_chung_tu_id, { chungtu_id: this.vchungtu_id })
      if (d.length > 0) str = this.soph
      let msg = `Bạn có chắc chắn muốn xóa chứng từ ${str} không?`
      if (!confirm(msg)) return
      this.ejsDialog.name = 'frmNhapText'
      this.ejsDialog.header = 'Danh sách kiểu xóa chứng từ'
      this.$refs.ejsDialog.show()
    },
    async frmNhapText_onBtnXacNhanClicked(data) {
      this.$refs.ejsDialog.hide()
      let frm = data
      if (data.vkieu_luachon) {
        // try{
        let kq = await this.callApiWrapper(api.xoa_chungtu_nhapxuatv2, {
          p_chungtu_id: this.vchungtu_id,

          p_kieu_xoa: frm.vkieu_luachon,
          p_sendsms: this.ckbSendSMS.value ? 1 : 0,
          p_nguoidung_id: await this.$root.token.getNguoiDungID(),
          p_may_cn: await this.$root.token.getMachine(),
          p_ip_cn: await this.$root.token.getIP(),
          p_ttph_id: this.ttph_id
          // donvi_fmis: this.chondvi_fmis ? this.params.p_DonViFMIS : ''
        })
        if (kq == 'error') return
        let message = 'Xóa chứng từ thành công!'
        if (frm.vkieu_luachon == '2') {
          message = 'Xóa đồng bộ kế toán thành công!'
        } else if (frm.vkieu_luachon == '3') {
          message = 'Xóa đồng IMS thành công!'
        }
        this.$toast.success(`${message}`)
        await this.Load_ds_chungtu()
      }
    },
    frmNhapText_v2_onBtnXacNhanClicked(data) {
      console.log('frmNhapText_v2_onBtnXacNhanClicked')
      console.log(data)
      this.$refs.ejsDialog.hide()
    },
    /**
     * @see: private bool KiemTraDieuPhoi()
     */
    async KiemTraDieuPhoi() {
      let dieuphoi = await this.callApiWrapper(api.ds_nhom_nd_theo_ma, { ma_nd: await this.$root.token.getUserName() })
      if (dieuphoi.LENGTH > 0) {
        if (dieuphoi[0] == '104' && this.ttph_id == 4) return true
      }
      return false
    },
    /**
     * @see: private bool KiemTraNguoiCN()
     */
    async KiemTraNguoiCN() {
      return this.gridChungTu.value.NGUOI_CN == (await this.$root.token.getUserName())
    },

    /**
     * @see: private bool KiemTraDuLieu(bool themmoi, bool traphieu, let tag)
     */
    async KiemTraDuLieu(themmoi, traphieu, tag) {
      if (this.Tag == '66' && this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO) && tag != 'THUHOITS') {
        this.$toast.warning('Không thể sử dụng chức năng ghi lại. Hãy chọn combo nghiệp vụ và thao tác !')
        return false
      }
      if (!themmoi) {
        if (this.nghiepvu_id_chungtu != '' && ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') || this.nghiepvu_id_chungtu == '288' || this.nghiepvu_id_chungtu == '286' || this.nghiepvu_id_chungtu == '282')) {
          this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
          return false
        }
        if (tag != 'TRALAI') {
          if (!isNullOrEmpty(this.gridChungTu.value.NGAY_XD)) {
            this.$toast.warning('Đã có ngày ký, không thể sửa chứng từ !')
            return false
          }
        }
        if (!this.KiemTraNguoiCN() && !traphieu) {
          if (this.chonvattu_lohang && this.xTabTongHop.visible && tag == 'UPSL' && (this.dsQuyen.includes(DS_QUYEN_VT.HOANTHIEN_CHUNGTU) || this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO))) {
          } else {
            this.$toast.warning('Bạn không được sửa chứng từ của người khác!')
            // this.cboLoaiChungTu.Focus()
            return false
          }
        }
        let s = await this.callApiWrapper(api.kiem_tra_cap_nhat_chung_tu, { chungtu_id: this.vchungtu_id })
        if (s != '1') {
          this.$toast.warning(`${s}`)
          return false
        }
        if (this.chonvattu_lohang && this.xTabTongHop.visible) {
          let dt_thietbi_group = this.gridThietBi.list
          if (dt_thietbi_group != null && dt_thietbi_group.length > 0) {
            for (dr of dt_thietbi_group) {
              if (dr.LOAITBI_ID == '2' || dr.LOAITBI_ID == '3') continue
              let dr_rowvt = this.dtVatTu.filter((a) => a.CTCT_ID == dr.CTCT_ID)
              if (dr_rowvt.length > 0) {
                dr_rowvt[0].SOLUONG = dr.SOLUONG
                dr_rowvt[0].TIEN = dr.SOLUONG * dr.DONGIA
                dr_rowvt[0].VAT = Math.round((parseInt(dr.TIEN) * 10) / 100)
              }
            }
          }
          let dt_kiemtra = this.dtVatTu
          // for (let i = dt_kiemtra.Columns.length - 1 i >= 0 i--)
          // {
          //     if (dt_kiemtra[i] != "VATTU_ID" && dt_kiemtra.Columns[i] != "LOHANG" && dt_kiemtra.Columns[i] != "SOLUONG"
          //          && dt_kiemtra.Columns[i] != "CTCT_ID")
          //         dt_kiemtra.Columns.Remove(dt_kiemtra.Columns[i])
          // }
          let json = dt_kiemtra
          if (!(await this.KiemTraChiTiet_VT(json))) return false
        }
      }
      if (!themmoi && this.doi_mucdich_chungtu) {
        let mdid = this.gridChungTu.value.MUCDICH_ID
        if (mdid != this.cboMucDich.value)
          if (this.gridVatTu.list.length > 0) {
            this.$toast.warning('Chứng từ đã có vật tư, không thể đổi sang mục đích khác !')
            return false
          }
      }
      // moment(date1, format).isBefore(moment(date2, format))
      if (themmoi && this.ngay_dh != DateTimeHelper.MinValue()) {
        if (DateTimeHelper.ToDate(this.ngay_dh) > DateTimeHelper.ToDate(this.dtpNgayDK.value)) {
          this.$toast.warning('Ngày chứng từ phải lớn hơn ngày yÍu cầu nhập kho (' + this.ngay_dh + ')')
          return false
        }
      }
      if (this.ttph_id == 4 && !themmoi) {
        let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
        if (s != '1') {
          this.$toast.warning(`${s}`)
          // cboLoaiChungTu.Focus()
          return false
        }
        if (this.cboDenKho.value != this.kho_nhan_goc) {
          this.$toast.warning('Bạn ghi lại phải giữ nguyÍn đúng kho đến (trạng thái DD có thể sửa kho đến)')
          this.cboDenKho.value = this.kho_nhan_goc
          return false
        }
      }
      if (this.cboLoaiChungTu.value == null) {
        this.$toast.warning('Bạn chưa chọn loại chứng từ!')
        // cboLoaiChungTu.Focus()
        return false
      }
      if (this.cboMucDich.value == null) {
        this.$toast.warning('Bạn chưa chọn Mục đích!')
        // cboMucDich.Focus()
        return false
      }
      if (this.cboTuKho.value == null) {
        this.$toast.warning('Bạn chưa chọn từ kho!')
        // cboTuKho.Focus()
        return false
      }
      if (!(await this.KT_ChungTu_Psis())) return false
      if (themmoi) {
        this.dc_ct_noibo = false
        this.dc_ct_ttvt = false
        this.dc_ct_ldscdp = false
        this.bqlda_nguonvon_dautu = false
        this.bqlda_nguonvon_khac = false
      } else {
        let y = this.ds_md.filter((a) => a.nhomMdId == this.cboMucDich.value)
        if (y.length > 0 && y[0].maMd != vma_md) {
          this.dc_ct_noibo = false
        }
      }
      let xuat_hoantra = false
      if (this.ds_md != null && this.ds_md.length > 0) {
        let t = this.ds_md.filter((a) => a.mucDichId == this.cboMucDich.value)
        if (t.length > 0 && (t[0].maMd == 'X.HTVT' || t[0].maMd == 'X.TDVT')) {
          if (this.cboLoaiChungTu.value == '2' || this.cboLoaiChungTu.value == '3') {
            xuat_hoantra = true
          }
        }
        if (t.length > 0 && t[0].maMd == 'DC_CT') {
          // if (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2') {
          this.dc_ct_noibo = true
          this.dc_ct_ttvt = false
          this.dc_ct_ldscdp = false
          // }
        }
        if (t.length > 0 && t[0].maMd == 'DC_CT_TTVT') {
          // if (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2') {
          this.dc_ct_ttvt = true
          this.dc_ct_noibo = false
          this.dc_ct_ldscdp = false
          // }
        }
        if (t.length > 0 && (t[0].maMd == 'DC_LDSC' || t[0].maMd == 'DC_CTDP')) {
          // if (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2') {
          this.dc_ct_ttvt = false
          this.dc_ct_noibo = false
          this.dc_ct_ldscdp = true
          // }
        }
        let kphieu = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboKieuPhieu.value })
        if (t.length > 0 && t[0].maMd == 'DC_ĐT.XDCB') {
          if (this.cboLoaiChungTu.value == '2' && kphieu == 'PX10') {
            this.bqlda_nguonvon_dautu = true
            this.bqlda_nguonvon_khac = false
          }
        }
        if (t.length > 0 && t[0].maMd == 'DC_SCL.DDTS' && kphieu == 'PX10') {
          if (this.cboLoaiChungTu.value == '2') {
            this.bqlda_nguonvon_dautu = false
            this.bqlda_nguonvon_khac = true
          }
        }
      }
      if (!(await this.KiemTra_XuatHoanTra(true))) return false
      if (this.cboDenKho.value == null && !xuat_hoantra) {
        this.$toast.warning('Bạn chưa chọn đến kho!')
        // cboDenKho.Focus()
        return false
      }
      if (xuat_hoantra) {
        //wtf ảo ma
        if (onebssIdIsValid(this.cboDenKho.value)) {
          this.$toast.warning('Xuất hoàn trả vật tư bạn không được chọn kho!')
          // cboDenKho.Focus()
          this.cboDenKho.value = null
          return false
        }
      }
      if (!xuat_hoantra && this.dc_ct_noibo && this.dc_ct_ldscdp && this.cboTuKho.value == this.cboDenKho.value) {
        this.$toast.warning('Kho từ và kho đến không thể giống nhau!')
        // cboTuKho.Focus()
        return false
      }
      if (this.dc_ct_noibo || this.dc_ct_ldscdp || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac) {
        //NOTE: Sửa luồng xuất công trình nội bộ
        // if ((this.dc_ct_noibo || this.dc_ct_ldscdp) && this.cboTuKho.value != this.cboDenKho.value) {
        //   this.$toast.warning('Kho từ và kho đến phải giống nhau!')
        //   // cboTuKho.Focus()
        //   return false
        // }
        let dt = this.cboTuKho.list
        if (dt.filter((a) => a.khoId == this.cboTuKho.value && a.loaiKhoId == '14').length == 0 && this.cboLoaiChungTu.value == 2) {
          this.$toast.error('Chỉ áp dụng mục đích này cho loại kho tổ trưởng')
          return false
        }
        if ((this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac || this.dc_ct_noibo || this.dc_ct_ldscdp || this.dc_ct_ttvt) && this.cboLoaiChungTu.value == 2) {
          let ton_khooo = await this.callApiWrapper(api.kiem_tra_kho_den_ton_kho, { kho_id: this.cboDenKho.value })
          if (ton_khooo == null || ton_khooo.length == 0) {
            this.$toast.warning('Kho đến phải là kho không có tồn !')
            return false
          }
        }
        if (this.dc_ct_noibo || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac || this.dc_ct_ldscdp) {
          if (isNullOrEmpty(this.cboCongTrinh.value)) {
            this.$toast.warning('Chưa chọn công trình')
            return false
          }
        }
        // if (this.dc_ct_ldscdp) {
        //   if (this.cboCongTrinh.value != null && this.cboCongTrinh.value != null) {
        //     this.$toast.warning('Bạn không được phép chọn công trình')
        //     return false
        //   }
        // }
      }
      if (this.dc_ct_ttvt) {
        if (this.cboCongTrinh.value != null && this.cboCongTrinh.value != null) {
          this.$toast.warning('Với chứng từ chuyển công trình cho TTVT bạn không thể chọn công trình')
          return false
        }
      }
      if (!(await this.KiemTraHanChotCT())) return false
      if (this.$root.token.getMaTinh() == 'NET') {
        if (this.cboLoaiChungTu.value == '99') {
          if (['', null, undefined].includes(this.txtNoiDung.value)) {
            this.$toast.warning('Nội dung chứng từ không được để trống !')
            // txtNoiDung.Focus()
            return false
          }
        }
        if (this.cboLoaiChungTu.value == '3') {
          let kt_cungung = await this.callApiWrapper(api.fn_kiemtra_loaidv_kho, { vtukho_id: this.cboTuKho.value, vdenkho_id: this.cboDenKho.value, vmucdich_id: this.cboMucDich.value })
          if (kt_cungung != '1') {
            this.$toast.warning(kt_cungung)
            return false
          }
        }
      }
      if (this.txtNoiDung.value != null && this.txtNoiDung.value.length > 500) {
        this.$toast.warning('Nội dung nhập quá dài (<500 ký tự) !')
        return false
      }
      if (tag != 'GHILAI_PDA' && tag != 'BBBG' && tag != 'THUHOITS') {
        if (isNullOrEmpty(this.gridVatTu.list)) {
          this.$toast.warning('Bạn cần phải chọn danh sách vật tư cho chứng từ!')
          return false
        }
      }
      if (this.tsbtnNhapMoi.enabled) {
        if (this.soph.startsWith('QT')) {
          this.$toast.warning('Chứng từ quyết toán không thể sửa!')
          return false
        }
        let KIEMTRA_SUATH = await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })
        if (KIEMTRA_SUATH == 'ko') {
          this.$toast.warning('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
          return false
        }
      }
      if (this.cboLoaiChungTu.value == 2) {
        if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10') {
          let s = await this.callApiWrapper(api.lay_ds_ton_kho, { kho_id: this.cboDenKho.value })
          if (s == '1' && !this.bqlda_nguonvon_khac && !this.bqlda_nguonvon_dautu) {
            this.$toast.warning('Bạn phải chọn kho đến không có tồn kho!')
            return false
          }
        }
      }
      return true
    },
    /**
     * @see: private let area_chuanhoa(let tentat)
     */
    area_chuanhoa() {
      let area = ''
      if (this.$root.token.getMaTinh() == 'BCN') {
        area = 'BKN'
      } else if (this.$root.token.getMaTinh() == 'VTU') {
        area = 'BRA'
      } else if (this.$root.token.getMaTinh() == 'HAG') {
        area = 'HGI'
      } else if (this.$root.token.getMaTinh() == 'DAN') {
        area = 'DNO'
      } else {
        area = this.$root.token.getMaTinh()
      }
      return area
    },
    /**
     * @see: private bool KiemTra_NhapLeDieuChinh(bool traphieu)
     */
    async KiemTra_NhapLeDieuChinh(traphieu) {
      if (traphieu == false) {
        if (this.cboLoaiChungTu.value == 99) {
          if (this.tsbtnNhapMoi.enabled) {
            let ketqua = await this.callApiWrapper(api.kiem_tra_ct_dieu_chinh, {
              chungTuId: this.vchungtu_id,
              kieu: 1,
              tuNgay: getDateStringOrDefault(this.dtpNgayGuiTT.value),
              denNgay: getDateStringOrDefault(this.dtpNgayNhanTT.value)
            })
            let res = ketqua
            if (res != '1' && res != '0' && res != '2' && res != '3') {
              this.$toast.warning(`${res}`)
              return false
            }
            if (res == '2') {
              this.$toast.warning('Chứng từ nhập điều chỉnh đơn giá đã hoàn thiện! Bạn không thế chỉnh sửa!')
              return false
            }
          }
        }
      }
      return true
    },
    /**
     * @see: private bool KiemTra_XuatHoanTra(bool enable_lk_mk)
     */
    async KiemTra_XuatHoanTra(enable_lk_mk) {
      let loaict = this.gridChungTu.value ? this.gridChungTu.value.LOAICT_ID : 0
      let ttphid = this.gridChungTu.value ? this.gridChungTu.value.TTPH_ID : 0
      let mucdichid = this.gridChungTu.value ? this.gridChungTu.value.MUCDICH_ID : 0
      let md = await this.callApiWrapper(api.ds_chung_tu_theo_so_phieu_gop, { so_phieu_gop: this.gridChungTu.value ? this.gridChungTu.value.SOPHIEUGOP : 0 })

      let ko_vt = await this.callApiWrapper(api.ds_chung_tu_theo_chung_tu_cha, { chungtucha_id: this.vchungtu_id })
      if (this.tsbtnNhapMoi.enabled) {
        if ((loaict == 2 || loaict == 3) && (mucdichid == 234 || mucdichid == 278) && ko_vt.length > 0) {
          let s = ''
          for (item of ko_vt) {
            if (item.LOAICT_ID == '2' || item.LOAICT_ID == '3') {
              s += ',. + item.SOPHIEU + '
            }
          }
          this.$toast.warning(`Chứng từ xuất hoàn trả đã chia thành phiếu xuất:   ${s.sublet(1)}! Bạn không thể chỉnh sửa/xóa phiếu này!`)
          return false
        }
        if (this.ttph_id == 4 && (loaict == 2 || loaict == 3) && md.length > 0) {
          this.$toast.warning(`Chứng từ xuất hoàn trả đi kèm với phiếu nhập  ${md[0].SOPHIEU}! Bạn không thể chỉnh sửa/xóa phiếu náy!`)
          return false
        }
        if (loaict == 1 && md.length > 0 && enable_lk_mk == false) {
          this.$toast.warning('Chứng từ nhập hoàn trả đi kèm với chứng từ xuất hoàn trả! Bạn không thể thêm VT/ chỉnh sửa chứng từ!')
          return false
        }
      }
      return true
    },
    //Gộp hàm Đạt - M.Cường 22/06/2023
    // async KiemTra_XuatHoanTra(enable_lk_mk) {
    //   let kq = await this.callApiWrapper(api.fn_kiemtra_xuathoantra, {
    //     vchungtu_id: this.vchungtu_id,
    //     venable_lk_mk: enable_lk_mk ? 1 : 0,
    //     tsbtnnhapmoi: this.tsbtnNhapMoi.enabled ? 1 : 0
    //   })
    // },
    /**
     * @see: private bool KiemTra_LoaiKho_MaKho()
     */
    async KiemTra_LoaiKho_MaKho() {
      if (this.cboKieuPhieu.value == null || this.cboKieuPhieu.value == '') {
        this.$toast.warning('Bạn phải chọn kiểu phiếu! ')
        return false
      }
      if (this.cboLoaiChungTu.value == '2' || this.cboLoaiChungTu.value == '3') return true
      let lkmk = this.gridVatTu.list
      if (lkmk.length <= 0) return false
      let loaimakho = lkmk
      for (let i = loaimakho.length - 1; i >= 0; i--) {
        // if (loaimakho.Columns[i] != "LOAIKHO" && loaimakho.Columns[i] != "MAKHOTD"
        //     && loaimakho.Columns[i] != "CTCT_ID" && loaimakho.Columns[i] != "CHATLUONG_ID"
        //     && loaimakho.Columns[i] != "DONGTBI_ID"
        //     && loaimakho.Columns[i] != "HETHONG_ID")
        //     loaimakho.Remove(loaimakho.Columns[i])
      }
      let json = loaimakho
      let kq = await this.callApiWrapper(api.kiem_tra_loai_ma_kho, {
        dataDs: loaimakho,
        kieu: 0
      })
      if (kq == 'loaikho') {
        this.$toast.warning('Không thể ghi lại. Bạn phải chọn hết loại kho ở danh sách vật tư trước!')
        return false
      }
      if (kq == 'makhotd') {
        this.$toast.warning('Không thể ghi lại. Bạn phải chọn hết m„ kho ở danh sách vật tư trước!')
        return false
      }
      if (kq == 'chatluong_id') {
        this.$toast.warning('Không thể ghi lại. Bạn phải chọn hết chất lượng ở danh sách vật tư trước!')
        return false
      }
      if (kq == 'dongtbi_id') {
        this.$toast.warning('Không thể ghi lại. Bạn phải chọn hết dÚng thiết bị ở danh sách vật tư trước!')
        return false
      }
      if (kq == 'hethong_id') {
        this.$toast.warning('Không thể ghi lại. Bạn phải chọn hết hệ thống ở danh sách vật tư trước!')
        return false
      }
      return true
    },
    /**
     * @see: private void TaoDuLieu_Tbi()
     */
    async TaoDuLieu_Tbi() {
      let chungtu_tbiData = []
      for (item of this.dtThietBi) {
        let ri = {}
        let vthietbi_id = 0
        if (item.THIETBI_ID != '0') vthietbi_id = item.THIETBI_ID
        else
          vthietbi_id = await this.callApiWrapper(api.get_key, {
            keyName: 'THIETBI_ID',
            numBlockSize: 1,
            numRetry: 10
          })
        ri.THIETBI_ID = vthietbi_ID
        ri.SERIAL = item.SERIAL
        ri.MA_TBI = item.MA_TBI
        ri.DONGIA = item.DONGIA
        let vctct_id = 0
        let lohang = item.LOHANG
        let vattu_id = item.vattu_ID
        let a = this.chitietCTData_insert.filter((p) => p.LOHANG == lohang && p.VATTU_ID == vattu_id)
        if (a.length > 0) vctct_id = a[0].CTCT_ID
        else {
          let b = this.chitietCTData_update.filter((p) => p.LOHANG == lohang && p.VATTU_ID == vattu_id)
          if (b.length > 0) vctct_id = b[0].CTCT_ID
        }
        ri.CTCT_ID = vctct_ID
        chungtu_tbiData.push(ri)
      }
    },
    async KiemTraHanChotCT(ngay_ct, ngay) {
      if ((ngay_ct = null && ngay == null)) {
        /**
         * @see: private bool KiemTraHanChotCT(DateTime ngay_ct, object ngay)
         */
        let vkho_nhan_id = 0
        let vkho_giao_id = 0
        if (this.cboTuKho.value != null) vkho_giao_id = this.cboTuKho.value
        if (this.cboDenKho.value != null && cboDenKho.value != null) vkho_nhan_id = this.cboDenKho.value
        if (ngay == null) {
          let yo = await this.callApiWrapper(api.kiem_tra_han_chung_tu_v2, {
            chungTuId: this.vchungtu_id,
            khoGiaoId: vkho_giao_id,
            khoNhanId: vkho_nhan_id,
            ngayCt: ''
          })
          if (yo != '1') {
            this.$toast.warning('Đã quá hạn chứng từ!')
            return false
          }
        } else {
          let s = await this.callApiWrapper(api.kiem_tra_han_chung_tu, {
            chungTuId: 0,
            khoGiaoId: vkho_giao_id,
            khoNhanId: vkho_nhan_id,
            ngayCt: moment(ngay_ct, 'DD/MM/YYYY HH:mm').format('DD/MM/YYYY')
          })
          if (s != '1') {
            this.$toast.warning('Đã quá hạn chứng từ!')
            return false
          }
        }
        return true
      } else {
        /**
         * @see: private bool KiemTraHanChotCT()
         */
        let vkho_nhan_id = 0
        let vkho_giao_id = 0
        let ngay_ct = this.dtpNgayDK.value
        if (this.cboTuKho.value != null) vkho_giao_id = this.cboTuKho.value
        if (this.cboDenKho.value != null) vkho_nhan_id = this.cboDenKho.value
        let s = await this.callApiWrapper(api.kiem_tra_han_chung_tu, {
          chungTuId: this.vchungtu_id,
          khoGiaoId: vkho_giao_id,
          khoNhanId: vkho_nhan_id,
          ngayCt: moment(ngay_ct, 'DD/MM/YYYY HH:mm').format('DD/MM/YYYY')
        })
        if (s != '1') {
          this.$toast.warning('Đã quá hạn sửa chứng từ!')
          return false
        }
        return true
      }
    },
    /**
     * @see: private bool KiemTraChiTiet_VT(let vds)
     */
    async KiemTraChiTiet_VT(vds) {
      try {
        let kt = await this.callApiWrapper(api.kiem_tra_chi_tiet_vt, { dsVatTu: vds, chungTuId: this.vchungtu_id })
        if (kt != '1') {
          this.$toast.warning(`${kt}`)
          return false
        }
        return true
      } catch (e) {
        this.$toast.warning(`Có lỗi khi kiểm tra chi tiết vật tư ${e}`)
        return false
      }
    },
    async onClickChonVatTu() {
      this.$bvModal.hide('popupLuaChonVatTu')
      await this.callFrmVatTuTrongKho_V1_V2()
    },
    async callFrmVatTuTrongKho_V1_V2() {
      if (this.isVatTu) {
        // CHọn vật tư lô hàng
        if (this.xTabTongHop.visible) {
          this.dtLuuVT = []
        }
        let mamd = ''
        let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (d.length) {
          mamd = d[0]
        }
        if (mamd != 'CCDC.TL') {
          this.vtdk = []
          // let f = this.$refs.frmVatTuTrongKho
          // f.Visible.tsbtnXuatExcelTbi = false
          this.frmVatTuTrongKho.input.vkho_id = parseInt(this.cboTuKho.value)
          this.frmVatTuTrongKho.input.tt = 0
          try {
            let a = await this.callApiWrapper(api.ds_loai_kho_theo_kho, { kho_id: this.cboTuKho.value })
            if (a.length && a[0] && a[0] == LOAI_KHO.KHO_TOTRUONG) {
              this.frmVatTuTrongKho.input.tt = 1
            }
          } catch (error) {
            this.frmVatTuTrongKho.input.tt = 0
          }
          this.frmVatTuTrongKho.input.kieu = 1
          let dtLuuVT_temp = this.dtLuuVT
          console.log('2832 - this.dtLuuVT')
          console.log(this.dtLuuVT)
          if (!this.tsbtnNhapMoi.enabled) dtLuuVT_temp = dtLuuVT_temp.filter((e) => e.KHO_ID == this.cboTuKho.value)
          this.frmVatTuTrongKho.input.dtLuuVT = []
          this.frmVatTuTrongKho.input.dtLuuVT = dtLuuVT_temp
          // f.dtLuuVT = this.dtLuuVT

          this.ten_khotu = this.cboTuKho.value ? this.cboTuKho.value.TEN_KHO : ''
          this.frmVatTuTrongKho.input.vngay_ct = this.dtpNgayDK.value
          if (!this.tsbtnNhapMoi.enabled) {
            this.frmVatTuTrongKho.input.vchungtu_id = 0
          } else {
            this.frmVatTuTrongKho.input.vchungtu_id = this.vchungtu_id
          }
          this.frmVatTuTrongKho.input.vkho_den_id = 0
          if (this.cboDenKho.value != null && this.cboDenKho.value) {
            this.frmVatTuTrongKho.input.vkho_den_id = parseInt(this.cboDenKho.value)
          }
          this.isChonVT = true // dùng để xác định trả về giữa Lấy chứng từ và ChonVT
          this.ejsDialog.name = 'frmVatTuTrongKho'
          this.ejsDialog.header = 'Danh sách vật tư tồn kho'
          this.$refs.ejsDialog.show()
        }
      } else {
        this.frmVatTuTrongKhoV2.input.vkho_id = parseInt(this.cboTuKho.value)
        this.frmVatTuTrongKhoV2.input.vngay_ct = getDateStringOrDefault(this.dtpNgayDK.value)
        if (!this.tsbtnNhapMoi.enabled) {
          this.frmVatTuTrongKhoV2.input.vchungtu_id = 0
        } else {
          this.frmVatTuTrongKhoV2.input.vchungtu_id = this.vchungtu_id
        }
        this.ejsDialog.name = 'frmVatTuTrongKhoV2'
        this.ejsDialog.header = 'Danh sách vật tư'
        this.$refs.ejsDialog.show()
      }
    },
    /**
     * @see: private void tsbtnThemVT_Click(object sender, EventArgs e)
     */
    async tsbtnThemVT_Click() {
      // try {
      // if (!this.KT_ChungTu_Psis()) return;
      if (this.tag == '66' && this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)) {
        this.$toast.warning('Không thể sử dụng chức năng thêm vật tư. Hãy chọn combo nghiệp vụ và thao tác !')
        return
      }
      if (!isNullOrEmpty(this.cboMa_PDA.value)) {
        if (this.cboNghiepVu.value != '292' && this.cboNghiepVu.value != '293' && this.cboNghiepVu.value != '294') {
          this.$toast.warning('Bạn không được chọn vật tư với thông tin từ dự án/ số phiếu')
          return
        }
      }
      if (this.cboMucDich.value == null || this.cboMucDich.value == null || this.cboMucDich.value == '') {
        this.$toast.warning('Chưa chọn mục đích !')
        return
      }
      if (!this.tsbtnNhapMoi.enabled) {
        let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (d.length > 0) this.vma_md = d[0]
      }
      if (this.vma_md == MUCDICH_VT.MUASAM) {
        this.$toast.warning('Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư')
        return
      }
      if (this.vma_md == MUCDICH_VT.CHUYEN_CP) {
        this.$toast.warning('Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn vật tư')
        return
      }
      if (!(await this.kiemtra_thaydoi_ctct())) return
      let test = await this.KiemTra_XuatHoanTra(false)
      if (!this.KiemTra_XuatHoanTra(false)) return
      //xu li kho chon vt
      let vtKho = this.dtLuuVT.filter((e) => e.khoid == this.cboTuKho.value)
      if (this.kieugoi_id == 1) {
        let kieu = -1
        if (this.chonvattu_lohang) {
          if (this.vma_md == 'DC_CT' && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
            kieu = -1
          } else if (this.vma_md == 'DC_CT_TTVT' && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
            kieu = -1
          } else if ((this.vma_md == 'DC_CTDP' || this.vma_md == 'DC_LDSC') && (this.cboLoaiChungTu.value == '3' || this.cboLoaiChungTu.value == '2')) {
            kieu = -1
          } else if ((this.vma_md == 'X.HTVT' || this.vma_md == 'X.TDVT' || this.vma_md == 'N.DCDG' || this.vma_md == 'N.TTCL' || this.vma_md == 'N-X.DCMSL' || this.vma_md == 'SC_BH_DONGIA') && this.cboLoaiChungTu.value == '2') {
            this.kieu = -1
          } else {
            // let frm = new frmNhapText("Bạn Hãy lựa chọn kiểu chọn vật tư", 1)
            // frm.value = "Danh sách kiểu xóa chứng từ"
            // frm.rdioCKb.Properties.Items[0].Description = "Vật tư - lÙ h‡ng"
            // frm.rdioCKb.Properties.Items[1].Description = "Vật tư"
            // let kq = frm.ShowDialog()
            // switch (kq)
            // {
            //     case DialogResult.Cancel:
            //         return
            //         break

            //     case DialogResult.OK:
            //         kieu = frm.vkieu_luachon)
            //         break
            // }
            this.frmNhapTextDialogOnOpen()
          }
        }
        if (kieu == -1 || kieu == 1) {
          if (this.xTabTongHop.visible) this.dtLuuVT = []
          let mamd = ''
          let mucdich_id = this.cboMucDich.value
          let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
          if (d.length > 0) mamd = d[0]
          if (mamd == 'CCDC.TL') {
          } else await this.callFrmVatTuTrongKho_V1_V2()
        } else {
          await this.callFrmVatTuTrongKho_V1_V2()
        }
      } else {
        let mucdich_id = this.cboMucDich.value == null ? 0 : this.cboMucDich.value
        let zvma_md = ''
        let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: mucdich_id })
        if (d.length) zvma_md = d[0]
        this.frmDSVatTu.visible = true
        this.frmDSVatTu.input = {
          thamso_sinhlohang: true,
          ma_md: zvma_md,
          kieu: 1,
          dtLuuVT: this.dtLuuVT,
          quyen: ['HCM', 'NET'].includes(this.$root.token.getMaTinh()) ? true : false
        }
        this.ejsDialog.name = 'frmDSVatTu'
        this.ejsDialog.header = 'Danh sách vật tư'
        this.$refs.ejsDialog.show()
      }
      // } catch (e) {
      //   this.$toast.error(e)
      // }
    },
    /**
     * @see: private bool KT_ChungTu_Psis()
     */
    async KT_ChungTu_Psis() {
      let khoden_ID
      if (this.cboDenKho.value == null) this.khoden_id = 0
      else this.khoden_id = this.cboDenKho.value
      let kt_chungtu_psis = await this.callApiWrapper(api.fn_kt_thaotac_chungtu_psis, {
        p_chungtu_id: this.vchungtu_id,
        p_themmoi: this.tsbtnNhapMoi.enabled ? 1 : 0,
        p_mucdich_id: this.cboMucDich.value,
        p_kho_giao_id: this.cboTuKho.value,
        p_kho_nhan_id: this.khoden_id,
        p_loaict_id: this.cboLoaiChungTu.value
      })
      if (kt_chungtu_psis != '1') {
        this.$toast.warning(`${kt_chungtu_psis}`)
        return false
      }
      return true
    },
    async XacNhan_frmVatTuTrongKho() {
      if (this.isChonVT) await this.XacNhan_frmVatTuTrongKho_ChonVT()
      else await this.XacNhan_frmVatTuTrongKho_LayChungTu()
    },
    async XacNhan_frmVatTuTrongKho_V2() {
      let f = this.$refs.frmVatTuTrongKhoV2
      if (f.xacnhan) {
        this.xTabTongHop.visible = true
        // xtraTabControl1.SelectedTabPage = xTabTongHop
        this.gridVTGroup.list = f.grvVTGroup
        this.ds_Gan = f.grvDaGan
        this.dtLuuVT = f.dsVatTu
        for (let item of f.dsVatTu) {
          let r = {}
          r.TATCA = 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.MA_VT = item.MA_VT
          r.SOLUONG = 0
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI
          r.LOAITBI_ID = item.LOAITBI_ID
          r.KHO_ID = item.KHO_ID
          r.MAY_CN = 'quy-test' //ttnd.may_cn
          r.NGUOI_CN = await this.$root.token.getUserName()
          r.IP_CN = await this.$root.token.getIP() //ttnd.ip
          r.TY_LE = item.TY_LE
          r.MAKHOTD = item.MAKHOTD
          r.LOAIKHO = item.LOAIKHO
          r.MATHETS = item.MATHETS
          r.MATHUNG = item.MATHUNG
          r.GHICHU_HD = item.GHICHU
          r.CHATLUONG = item.CHATLUONG
          r.NGUON_GOC = item.NGUON_GOC
          r.CHATLUONG_ID = ''
          r.DONGTBI_ID = ''
          r.HETHONG_ID = ''
          this.dtThemVT.push(r)
        }
        this.gridVatTu.list = this.dtThemVT
        this.khotu_id = f.vkho_ID
        this.kieunhap = 2
        // this.enabled.gridVatTu.SOLUONG = true
      }
    },
    /**
     * @see: let tao_lohang(let prefix, let lohang)
     */
    async tao_lohang(prefix, lohang) {
      let d = await this.callApiWrapper(api.ds_prefix_lo_hang, { lo_hang: prefix })
      if (d.length) {
        return d[0][0] == '1' ? prefix.trim() + lohang : prefix.trim()
      }
      return lohang
    },
    /**
     * @see:  private void tstbtnDonHang_Click(object sender, EventArgs e)
     */
    async tstbtnDonHang_Click() {
      if (this.vma_md == MUCDICH_VT.MUASAM) {
        this.$toast.error('Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng')
        return
      }
      if (this.vma_md == MUCDICH_VT.CHUYEN_CP) {
        this.$toast.error('Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép chọn từ đơn hàng')
        return
      }
      if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYENLAYVT_DH)) {
        this.$toast.error('Bạn không có quyền lấy vật tư từ đơn hàng')
        return
      }
      // try {
      this.ejsDialog.name = 'frmDonHangTT'
      this.ejsDialog.header = 'Đơn hàng'
      this.$refs.ejsDialog.show()
      // } catch (e) {
      //   this.$toast.error(`Đã xãy ra lỗi : ${e}`)
      // }
    },
    async gridVatTuBtnXoaClicked(row) {
      if (this.copyct == false) {
        if (this.tsbtnNhapMoi.enabled) {
          if (!this.KiemTraNguoiCN()) {
            this.$toast.warning('Bạn không được sửa chứng từ của người khác!')
            // cboLoaiChungTu.Focus()
            return
          }
          if (this.xTabTongHop.visible && this.chonvattu_lohang) {
            this.$toast.warning('Với chứng từ chọn số lượng bạn không được phép xóa')
            return
          }
        }
        if (!(await this.KT_ChungTu_Psis()))
          //Chừng từ điều chỉnh psis tạo tự động không cho sửa thủ công
          return
        if (this.tsbtnNhapMoi.enabled) {
          if (this.nghiepvu_id_chungtu != '' && ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') || this.nghiepvu_id_chungtu == '288' || this.nghiepvu_id_chungtu == '286' || this.nghiepvu_id_chungtu == '282')) {
            this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
            return
          }
          if (this.soph.startsWith('QT')) {
            this.$toast.warning('Chứng từ quyết toán không thể sửa!')
            return
          }
          if ((await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })).includes('ko')) {
            this.$toast.warning('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
            return
          }
          let z = await this.callApiWrapper(api.kiem_tra_cap_nhat_chung_tu, { chungtu_id: this.vchungtu_id })
          if (z != '1') {
            this.$toast.warning(z)
            return
          }
          if (!isNullOrEmpty(this.gridChungTu.value.NGAY_XD)) {
            this.$toast.warning('Đã có ngày ký, không thể sửa chứng từ !')
            return
          }
        }
        if (!confirm('Bạn có chắc chán muốn xóa vật tư của chứng từ này!')) return
        if (!this.tsbtnNhapMoi.enabled) {
          this.gridVatTu.list = this.gridVatTu.list.filter((e) => e.ID_GEN != row.ID_GEN)
          this.dtLuuVT = this.gridVatTu.list.map((e) => e)
          return
        }
        if (!this.dsQuyen.includes(DS_QUYEN_VT.CAPNHAT_CHUNGTU) && !this.dsQuyen.includes(DS_QUYEN_VT.THUKHO_TAISANBANGIAO)) {
          this.$toast.warning('Bạn không có quyền thực hiện chức năng này!')
          return
        }
        if (this.kiemtra_quyen) {
          if (this.$root.token.getMaTinh() == 'NET') {
            let ds_dt = await this.callApiWrapper(api.ds_ngay_nhan_thanhtoan_chungtu, { chungtu_id: this.vchungtu_id })
            if (ds_dt == null || ds_dt.length == 0) {
              this.$toast.warning('không có dữ liệu ngày nhận không thể xóa')
              return
            } else {
              if (ds_dt[0].NGAYNHAN_TT != null && ds_dt[0].NGAYNHAN_TT != null && ds_dt[0].NGAYNHAN_TT != '') {
                this.$toast.warning('Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị')
                return
              }
            }
            //-----------------------------------------------------------------------------------------------
          }
          if (!(await this.kiemtra_thaydoi_ctct())) {
            return
          }
          if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) {
            return
          }
        }
        if (!(await this.KiemTraHanChotCT())) {
          return
        }
        if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, 'GHILAI'))) {
          return
        }
        if (!(await this.KiemTra_XuatHoanTra(false))) return
        if (!(await this.KiemTra_NhapLeDieuChinh(false))) {
          return
        }
        if (this.ttph_id == 4) {
          let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
          if (s != '1') {
            this.$toast.warning(s)
            return
          }
          s = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
            chungTuId: this.vchungtu_id,
            nguoiCn: await this.$root.token.getUserName(),
            mayCn: this.$root.token.getUserName(),
            ipCn: await this.$root.token.getIP()
          })
          if (s != '1') {
            this.$toast.warning('Có lỗi trong quá trình xóa phiếu! ' + s)
            return
          }
        }
        let id = row.CTCT_ID
        let k = await this.callApiWrapper(api.xoa_chung_tu_bao_hanh, {
          kieu: 2,
          chungTuId: this.vchungtu_id,
          chungTuCtId: id,
          thietBiId: 0
        })
        if (k != '1') {
          this.$toast.warning(k)
          return
        }
        this.gridVatTu.list = this.gridVatTu.list.filter((e) => e.ID_GEN != row.ID_GEN)
        console.log('3178 - this.gridVatTu.list')
        console.log(this.gridVatTu.list)
        if (id > 0) {
          await this.callApiWrapper(api.xoa_chi_tiet_chung_tu, { ctct_id: id })
          await this.Load_ds_vattu()
          await this.Load_ds_thietbi()
        }
        if (this.tsbtnNhapMoi.enabled) {
          let ctemp_id = this.vchungtu_id
          //datnt: Gọi cái này bị xóa hết vt đang chon dở ==> hỏi a Cường Tỏi
          await this.Load_ds_chungtu()
          // ChucNang.FocusGridViewRow(gridChungTu, "CHUNGTU_ID", ctemp_id);
        }
      } else {
        if (!(await this.KiemTra_XuatHoanTra(false))) return
      }
    },
    /**
     * @see: private void btnXoaVT_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async btnXoaVT_ButtonClick(item) {
      if (!confirm('Bạn có chắc chắn muốn xóa vật tư của chứng từ này!')) return
      this.gridVatTu.list = this.gridVatTu.list.filter((e) => e.CTCT_ID != this.gridVatTu.value.CTCT_ID)
      let id = this.gridVatTu.value.CTCT_ID
      if (id > 0) await this.callApiWrapper(api.xoa_chi_tiet_chung_tu, { ctct_id: item.CTCT_ID })
    },
    /**
     * @see: private void txtExcelTbiMau_Click(object sender, EventArgs e)
     */
    async txtExcelTbiMau_Click() {
      let dt = []
      if (this.vchungtu_id > 0 && this.dtVatTu.length > 0) {
        let a = this.dtVatTu.filter((p) => p.LOAITBI_ID == '2')
        for (let item of a) {
          let sl = item.SOLUONG
          for (let i = 0; i < sl; i++) {
            let r = {}
            r.lohang = item.LOHANG
            r.ma_vt = item.MA_VT
            r.ten_vt = item.TEN_VT
            r.dvi_tinh = item.DVI_TINH
            r.ma_tbi = ''
            r.serial = ''
            r.serial_gp = ''
            r.soluong = 1
            r.dongia = item.DONGIA
            r.ghichu = ''
            r.mathung = ''
            r.mac = ''
            dt.push(r)
          }
        }
        if (this.nghiepvu_id_chungtu == '288' && this.ma_pda_chungtu != '') {
          let j_list = dt
          let loc_kq_ims = await this.callApiWrapper(api.lay_ds_serial_null_ims, {
            chungTuId: this.vchungtu_id,
            data: dt,
            kieu: 0
          })
          dt = loc_kq_ims
        }
      }
      if (dt.length == 0) dt.push({ lohang: '', ma_vt: '', ten_vt: '', dvi_tinh: '', ma_tbi: '', serial: '', serial_gp: '', soluong: 0, dongia: 0, ghichu: '', mathung: '', mac: '' })
      dt = convertToUpcaseKey(dt)
      let data1 = xlsx.utils.json_to_sheet(dt)
      let wb = xlsx.utils.book_new()
      xlsx.utils.book_append_sheet(wb, data1, 'ThietBi')
      xlsx.writeFile(wb, 'fileNhapTbi.xlsx')
    },
    // async txtExcelTbiMau_Click() {
    //   let dt = []
    //   if (this.vchungtu_id > 0 && this.dtVatTu.length) {
    //     let a = this.dtVatTu.filter((p) => p.LOAITBI_ID == '2')
    //     for (let item of a) {
    //       let sl = parseInt(item.SOLUONG)
    //       for (let i = 0; i < sl; i++) {
    //         let r = {
    //           LOHANG: item.LOHANG,
    //           MA_VT: item.MA_VT,
    //           MA_TBI: '',
    //           SERIAL: '',
    //           SOLUONG: 1,
    //           DONGIA: parseInt(item.DONGIA),
    //           GHICHU: '',
    //           SERIAL_GP: ''
    //         }
    //         if (this.diachi_mac) {
    //           r.MAC = ''
    //         }
    //         if (this.b_mathung) {
    //           r.MATHUNG = ''
    //         }
    //         dt.push(r)
    //       }
    //     }
    //     if (this.nghiepvu_id_chungtu == '288' && this.ma_pda_chungtu != '') {
    //       let loc_kq_ims = await this.layDSSerialNullIMS({
    //         chungTuId: this.vchungtu_id,
    //         data: dt,
    //         kieu: 0
    //       })
    //       dt = loc_kq_ims
    //     }
    //   }
    // let data1 = xlsx.utils.json_to_sheet(dt)
    // let wb = xlsx.utils.book_new() // make Workbook of Excel
    // xlsx.utils.book_append_sheet(wb, data1, 'ThietBi') // sheetAName is name of Worksheet
    // // export Excel file
    // xlsx.writeFile(wb, 'fileNhapTbi.xlsx')
    // },
    /**
     * @see: private void btnExcelTbi_Click(object sender, EventArgs e)
     */
    async btnExcelTbi_Click() {
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!')
        return
      }

      if (!(await this.KT_ChungTu_Psis()))
        //Chừng từ điều chỉnh psis tạo tự động không cho sửa thủ công
        return

      if (this.tsbtnNhapMoi.enabled) {
        if (
          this.nghiepvu_id_chungtu != '' &&
          ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') ||
            //|| nghiepvu_id_chungtu == "288"
            this.nghiepvu_id_chungtu == '286' ||
            this.nghiepvu_id_chungtu == '282')
        ) {
          this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
          return
        }
        if (this.soph.startsWith('QT')) {
          this.$toast.warning('Chứng từ quyết toán không thể sửa!')
          return
        }
        let KiemTraThuHoi = await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })
        if (KiemTraThuHoi.lengh > 0 && KiemTraThuHoi[0] == 'ko') {
          this.$toast.error('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
          return
        }
        if (this.kiemtra_quyen) {
          let ds_dt = await this.callApiWrapper(api.ds_ngay_nhan_thanhtoan_chungtu, { chungtu_id: this.vchungtu_id })
          if (ds_dt.length == 0) {
            this.$toast.error('Không có dữ liệu ngày nhận không thể xóa')
            return
          } else {
            if (ds_dt[0] != null && ds_dt[0] != '') {
              this.$toast.error('Đã có dữ liệu ngày nhận kho bạn không thể nhập thiết bị')
              return
            }
          }
          // let ds_dt = ChungTu.GET_DATA_SQL("Select ngaynhan_tt from {?DB12}.chungtu where chungtu_id=" + vchungtu_id);
          // if (ds_dt == null || ds_dt.Rows.Count == 0)
          // {
          //     this.$toast.warning("Không có dữ liệu ngày nhận không thể xóa");
          //     return;
          // }
          // else
          // {
          //     if (ds_dt.Rows[0]["NGAYNHAN_TT"] != null && ds_dt.Rows[0]["NGAYNHAN_TT"] != DBNull.Value && ds_dt.Rows[0]["NGAYNHAN_TT"] != "")
          //     {
          //         this.$toast.warning("Đã có dữ liệu ngày nhận kho bạn không thể nhập thiết bị");
          //         return;
          //     }
          // }
          if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) {
            return
          }
        }
      }
      if (this.dtVatTu.length == 0) {
        this.$toast.error('Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!')
        return
      }
      this.$refs.fileInputExcelTbi.click() // --> UploadExcelTbi
    },
    UploadExcelTbi(e) {
      // try {
      const files = e.target.files
      if (!files.length) {
        return
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError('File excel không đúng định dạng!')
      }
      const fileReader = new FileReader()
      fileReader.onload = async (ev) => {
        const data = ev.target.result
        const XLSX = xlsx
        const workbook = XLSX.read(data, {
          type: 'binary'
        })
        const wsname = workbook.SheetNames[0]
        const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])
        console.log('ws')
        console.log(ws)
        if (ws.length == 0) {
          this.$toast.error('Không có dữ liệu đầu vào')
          return
        }
        if (this.nghiepvu_id_chungtu == '288' && this.ma_pda_chungtu != '') {
          let kiemTra = await this.callApiWrapper(api.cap_nhat_serial_ims, {
            chungTuId: this.vchungtu_id,
            data: ws,
            kieu: 0
          })
          if (kiemTra == '1') {
            await this.Load_ds_thietbi()
          } else {
            this.$toast.error(`${kiemTra}`)
          }
        } else {
          this.CapNhatExcelTbi_v2(ws)
        }
        if (this.chonvattu_lohang && this.xTabTongHop.visible) {
          await this.Load_ds_vattu()
          await this.NAP_DS_VT_GROUP()
        }
      }
      fileReader.readAsBinaryString(files[0])
      // } catch (e) {
      //   this.$toast.error('Đã có lỗi xảy ra khi upload file!')
      // } finally {
      // }
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error('Chưa chọn file nào!')
        return false
      }
      if (this.arrayImportFile.filexcel.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        this.$toast.error('File không phải định dạng xlsx hoặc xls!')
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]

      let json_list = []
      try {
        this.$root.showLoading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = xlsx.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = xlsx.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers = data.shift() // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            let result = data.map(function (a) {
              let jsonRow = {}
              a.forEach(function (cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue
              })
              return jsonRow
            })
            console.log('onChange')
            console.log(result)
            await this.CapNhatExcelTbi_v2(JSON.parse(JSON.letify(result)))
            if (this.chonvattu_lohang && this.xTabTongHop.visible) {
              await this.Load_ds_vattu()
              await this.NAP_DS_VT_GROUP()
            }
          }
          reader.readAsBinarylet(this.arrayImportFile.filexcel)
        }
      } catch (error) {
        this.$toast.error(`Có lỗi xảy ra: ${error}`)
        return
      } finally {
        this.$root.showLoading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    async CapNhatExcelTbi_v2(dt) {
      if (this.qr_code) {
        let row_length = ''
        let dem = 0
        for (let i = 0; i < dt.length; i++) {
          let seri_file = dt[i].SERIAL.trim()
          let seri_generate = seri_file.split('|')
          if (seri_generate.length > 1 && seri_generate.length != 3) {
            row_length += ', ' + (i + 2)
            dem++
          }
        }
        if (dem > 0) {
          this.$toast.error(`Có ${dem} dòng dữ liệu định dạng QR không đúng:\nDòng ${row_length.sublet(2)}`)
          return
        }
        //Sau khi kiểm tra định dạng QR Code xong
        for (let j of dt) {
          let seri_file = j.SERIAL.trim()
          let seri_generate = seri_file.Split('|')
          if (seri_generate.length > 1 && seri_generate.length == 3) {
            j.SERIAL = seri_generate[2]
          }
        }
      }
      let data = []
      for (let item of dt) {
        data.push({
          loHang: item.LOHANG == null ? '' : item.LOHANG,
          maVatTu: item.MA_VT == null ? '' : item.MA_VT,
          maThietBi: item.MA_TBI == null ? '' : item.MA_TBI,
          serial: item.SERIAL == null ? '' : item.SERIAL,
          serialGp: item.SERIAL_GP == null ? '' : item.SERIAL_GP,
          soLuong: 1,
          ghiChu: item.GHICHU == null ? '' : item.GHICHU,
          maThung: item.MATHUNG == null ? '' : item.MATHUNG
        })
      }
      // await this.CapNhatSerialV2_1(data)
      let dt1 = await this.callApiWrapper(api.cap_nhat_serial_v2, {
        listSerialInfo: data,
        chungTuId: this.vchungtu_id,
        nguoiCn: await this.$root.token.getUserName(),
        mayCn: await this.$root.token.getUserName(),
        ipCn: ''
      })
      if (dt1.length > 0) {
        this.$toast.error(`Có ${dt1.length} bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!`)
        await this.SaveXlsAndOpenFile('fileSaiDuLieu.xlsx', 'sheet1', dt1)
      } else {
        await this.Load_ds_thietbi()
      }
    },
    async SaveXlsAndOpenFile(fileName, sheetName, datta) {
      let data1 = xlsx.utils.json_to_sheet(datta)
      let wb = xlsx.utils.book_new()
      xlsx.utils.book_append_sheet(wb, data1, sheetName)
      xlsx.writeFile(wb, fileName)
    },
    async CapNhatExcelTbi(dt) {
      // try {
      //Kiểm tra dữ liệu thiết bị
      let dtSai = []
      let dt1 = await this.getDSThietBiChungTu({ chungTuId: 0 })
      for (let item of dt) {
        let ghichu = ''
        let vattu_id = 0
        let dvitinh_id = 0
        let vctct_id = 0
        let vthietbi_id = 0
        let vthietbi_cha_id = 0
        let ma_vt = item.MA_VT.trim()
        let lohang = item.LOHANG.trim()
        let ma_tbi = item.MA_TBI.trim()
        let serial = item.SERIAL.trim()
        let dvi_tinh = item.DVI_TINH.trim()
        let soluong = 0
        let dongia = 0
        if (item.SOLUONG) {
          soluong = item.SOLUONG
        }
        //Kiểm tra mã vật tư có tồn tại trong db không
        let dtkt = await this.KiemTraMaVatTu({ maVt: ma_vt })
        if (!dtkt.length) {
          ghichu = 'Mã vật tư không tồn tại '
        } else {
          vattu_id = parseInt(dtkt[0].vatTuID)
          ten_vt = dtkt[0].tenVatTu
        }
        // Kiểm tra đơn vị tính
        dtkt = await this.KiemTraDonViTinh({ donViTinh: dvi_tinh.toUpperCase().trim() })
        if (!dtkt.length) {
          ghichu = 'Đơn vị tính không tồn tại '
        } else {
          dvitinh_id = dtkt[0].dviTinhID
          dvi_tinh = dtkt[0].dviTinh
        }
        //Kiểm tra mã vật tư, lô hàng có tồn tại trong chứng từ không
        dtkt = await this.KiemTraTonVatTuCT({
          chungTuId: this.vchungtu_id,
          vatTuId: vattu_id,
          loHang: lohang
        })
        if (!dtkt.length) {
          ghichu = 'Vật tư và lô hàng không tồn tại trong chứng từ '
        } else {
          vctct_id = dtkt[0].chiTietCtID
          dongia = dtkt[0].donGia
        }
        if (ghichu) {
          let rsai = []
          for (let i = 0; i < dt.length; i++) {
            rsai[i] = item[i]
          }
          // rsai.GHICHU_LOI = ghichu
          dtSai.push(...rsai)
        } else {
          let r = {}
          r.CTCT_ID = vctct_ID
          r.LOHANG = lohang
          r.VATTU_ID = vattu_ID
          r.DVI_TINH = dvi_tinh
          r.MA_TBI = ma_tbi
          r.SERIAL = serial
          r.SOLUONG = soluong
          r.DONGIA = dongia
          dt1.push(r)
        }
      }
      for (let table of dt1) {
        await this.CapNhatChungTuThietBi({
          ctctId: table.CTCT_ID,
          thietBiId: table.THIETBI_ID || 0,
          thietBiChaId: table.THIETBI_CHA_ID || 0,
          maThietBi: table.MA_TBI,
          serial: table.SERIAL,
          soLuong: table.SOLUONG,
          nguoiCn: await this.$root.token.getUserName(),
          mayCn: 'quy-test', //ttnd.may_cn
          ipCn: '10.59.90.123' //ttnd.ip
        })
      }
      await this.Load_ds_thietbi()
      if (dtSai.length) {
        this.$toast.error(`Có ${dtSai.length} bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!`)
        await this.SaveXlsAndOpenFile('fileSaiDuLieuThietBi.xlsx', 'sheet1', dtSai)
      }

      // } catch (error) {
      //   this.$toast.error("File excel không đúng định dạng!: ")
      // }
    },
    async btnFileExelMauVT_Click() {
      let dt = [
        {
          MA_DH: '',
          LOHANG: '',
          MA_VT: '',
          MA_TBI: '',
          SERIAL: '',
          SOLUONG: 0,
          DONGIA: 0,
          SOTHANG_PB: 0
        }
      ]
      await this.SaveXlsAndOpenFile('fileNhapVtu.xlsx', 'ThietBi', dt)
    },
    // async btnCapNhatExcelVtu(dt) {
    //   // try {
    //   //Kiểm tra dữ liệu thiết bị
    //   let dtSai = []
    //   for (let item of dt) {
    //     let ghichu = ''
    //     let vattu_id = 0
    //     let dvitinh_id = 0
    //     let vctct_id = 0
    //     let thietbi_id = 0
    //     if (item.hasOwnProperty('THIETBI_ID')) {
    //       thietbi_id = parseInt(item.THIETBI_ID)
    //     }
    //     let ma_vt = item.MA_VT.trim()
    //     let lohang = item.LOHANG.trim()
    //     let ma_tbi = item.MA_TBI.trim()
    //     let serial = item.SERIAL.trim()
    //     let dvi_tinh = item.DVI_TINH.trim()
    //     let soluong = 0
    //     let dongia = 0
    //     if (item.SOLUONG) {
    //       soluong = parseInt(item.SOLUONG)
    //     }
    //     if (item.DONGIA) {
    //       soluong = parseInt(item.DONGIA)
    //     }
    //     //Kiểm tra mã vật tư có tồn tại trong db không
    //     let dtkt = await this.KiemTraMaVatTu({ maVt: ma_vt })
    //     if (!dtkt.length) {
    //       ghichu = 'Mã vật tư không tồn tại '
    //     } else {
    //       vattu_id = parseInt(dtkt[0].vatTuID)
    //       ten_vt = dtkt[0].tenVatTu
    //     }
    //     // Kiểm tra đơn vị tính
    //     dtkt = await this.KiemTraDonViTinh({ donViTinh: dvi_tinh.toUpperCase().trim() })
    //     if (!dtkt.length) {
    //       ghichu = 'Đơn vị tính không tồn tại '
    //     } else {
    //       dvitinh_id = dtkt[0].dviTinhID
    //       dvi_tinh = dtkt[0].dviTinh
    //     }
    //     if (ghichu) {
    //       let rsai = []
    //       for (let i = 0 i < dt.length i++) {
    //         rsai[i] = item[i]
    //       }
    //       // rsai.GHICHU = ghichu
    //       dtSai.push(...rsai)
    //     } else {
    //       //Nhập thiết bị
    //       if (!this.dtThietBi.length) {
    //         this.dtThietBi = await this.getDSThietBiChungTu({ chungTuId: 0 })
    //       }
    //       let r = {}
    //       r.CTCT_ID = vctct_ID
    //       r.THIETBI_ID = thietbi_ID
    //       r.LOHANG = lohang
    //       r.VATTU_ID = vattu_ID
    //       r.DVI_TINH = dvi_tinh
    //       r.MA_TBI = ma_tbi
    //       r.SERIAL = serial
    //       r.SOLUONG = soluong
    //       r.DONGIA = dongia
    //       this.dtThietBi.push(r)
    //     }
    //   }
    //   // Cập nhật vật tư
    //     if (this.dtVatTu.length) {
    //       this.dtVatTu = getDSTraCuuVatTuChungTu({ chungTuId: 0 })
    //     }
    //     let r = {}
    //     r.CTCT_ID = 0
    //     r.LOHANG = item.LOHANG
    //     r.VATTU_ID = item.VATTU_ID
    //     r.SOLUONG = item.SOLUONG
    //     r.DONGIA = item.DONGIA
    //     r.TIEN = parseInt(item.DONGIA) * parseInt(item.SOLUONG)
    //     r.VAT = parseInt(item.DONGIA) * parseInt(item.SOLUONG) * 0.1
    //     r.MA_VT = item.MA_VT
    //     r.DVI_TINH = item.DVI_TINH
    //     this.dtVatTu.push(r)
    //   }

    //   if (this.dtSai.length) {
    //     this.$toast.error('Có ' + dtSai.length + ' bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!')
    //     await this.SaveXlsAndOpenFile('fileSaiDuLieuThietBi.xlsx', 'sheet1', dtSai)
    //   }
    //   this.gridVatTu.list = this.dtVatTu
    //   this.gridThietBi = this.dtThietBi

    //   // } catch (error) {
    //   //   this.$toast.error("File excel không đúng định dạng!:")
    //   // }
    // },
    async btnNhapExcelVT_Click() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]

      let json_list = []
      try {
        this.$root.showLoading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = xlsx.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = xlsx.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers = data.shift() // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            let result = data.map(function (a) {
              let jsonRow = {}
              a.forEach(function (cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue
              })
              return jsonRow
            })

            await this.btnCapNhatExcelVtu(result)
          }
          reader.readAsBinarylet(this.arrayImportFile.filexcel)
        }
      } catch (error) {
        this.$toast.error(`Có lỗi xảy ra: ${error}`)
        return
      } finally {
        this.$root.showLoading(false)
      }
      this.arrayImportFile.filexcel = null
    },
    /**
     * @see: private void btnXoaHetVT_Click(object sender, EventArgs e)
     */
    async btnXoaHetVT_Click() {
      if (this.gridVatTu.list == null || this.gridVatTu.length == 0) {
        this.$toast.warning('Không có dữ liệu vật tư để xóa')
        return
      }
      if (!(await this.KT_ChungTu_Psis())) return
      if (this.tsbtnNhapMoi.enabled) {
        if (this.nghiepvu_id_chungtu != '' && ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') || this.nghiepvu_id_chungtu == '288' || this.nghiepvu_id_chungtu == '286' || this.nghiepvu_id_chungtu == '282')) {
          this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
          return
        }
        if (this.gridChungTu.value.NGAY_XD != '' && this.gridChungTu.value.NGAY_XD != null) {
          this.$toast.warning('Đã có ngày ký, không thể sửa chứng từ !')
          return
        }
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          this.$toast.warning('Với chứng từ chọn số lượng bạn không được phép xóa')
          return
        }
        if (!(await this.KiemTraNguoiCN())) {
          this.$toast.warning('Bạn không được sửa chứng từ của người khác!')
          return
        }
        if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, 'GHILAI'))) return
        if (!(await this.KiemTraHanChotCT())) return
        if (!(await this.KiemTra_XuatHoanTra(false))) return
        if (!(await this.KiemTra_NhapLeDieuChinh(false))) return
      }
      if (this.tsbtnNhapMoi.enabled) {
        if (this.kiemtra_quyen) {
          if ((await this.$root.token.getMaTinh()) == 'NET') {
            //   {
            //     //Không hiểu sao code cũ lại zem đoạn kiểm tra này, NET Đã có ngày nhận vẫn xóa chi tiết dc nÍn thÍm tạm tentat
            //     let ds_dt = ChungTu.GET_DATA_SQL("Select ngaynhan_tt from {?DB12}.chungtu where chungtu_id=" + vchungtu_id)
            //     if (ds_dt == null || ds_dt.Count == 0)
            //     {
            //         this.$toast.warning("Không có dữ liệu ngày nhận không thể xóa")
            //         return
            //     }
            //     else
            //     {
            //         if (ds_dt[0].NGAYNHAN_TT != null && ds_dt[0].NGAYNHAN_TT != DBNull.Value && ds_dt[0].NGAYNHAN_TT != "")
            //         {
            //             this.$toast.warning("Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị")
            //             return
            //         }
            //     }
            //     //-----------------------------------------------------------------------------------------------------
          }
          if (!(await this.kiemtra_thaydoi_ctct())) return
          if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) return
        }
        if (this.soph.startsWith('QT')) {
          this.$toast.warning('Chứng từ quyết toán không thể sửa!')
          return
        }
        let KIEMTRA_SUATH = await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })
        if (KIEMTRA_SUATH == 'ko') {
          this.$toast.warning('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
          return
        }
        let s = await this.callApiWrapper(api.kiem_tra_cap_nhat_chung_tu, { chungtu_id: this.vchungtu_id })
        if (s != '1') {
          this.$toast.warning(`${s}`)
          return
        }
      }
      if (!confirm('Bạn có chắc chán muốn xóa hết vật tư của chứng từ này!')) return
      if (!this.tsbtnNhapMoi.enabled) {
        this.gridVatTu.list = []
        return
      }
      if (this.ttph_id == 4) {
        let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
        if (s != '1') {
          this.$toast.warning(`${s}`)
          return
        }
        s = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
          chungTuId: this.vchungtu_id,
          nguoiCn: await this.$root.token.getUserName(),
          mayCn: await this.$root.token.getMachine(),
          ipCn: await this.$root.token.getIP()
        })
        if (s != '1') {
          this.$toast.warning('có lỗi trong quá trình xóa phiếu!')
          return
        }
      }
      let k = await this.callApiWrapper(api.xoa_chung_tu_bao_hanh, {
        kieu: 2,
        chungTuId: this.vchungtu_id,
        chungTuCtId: 0,
        thietBiId: 0
      })

      if (k != '1') {
        this.$toast.warning(`${k}`)
        return
      }
      // let id = Convert.ToInt64(.gridChungTu.GetRowCellValue(gridVatTu.FocusedRowHandle, "CTCT_ID"))
      this.dtVatTu = []
      this.dtThietBi = []
      await this.callApiWrapper(api.xoa_tat_ca_chi_tiet_chung_tu, { chungtu_id: this.vchungtu_id })
      let cttemp_id = this.vchungtu_id
      await this.Load_ds_chungtu()
      // .gridChungTu.ClearColumnsFilter()
      // .gridChungTu.FocusedRowHandle = GridControl.AutoFilterRowHandle
      // ChucNang.FocusGridViewRow(.gridChungTu, "CHUNGTU_ID", cttemp_id)
    },
    /**
     * @see: private bool KIEMTRA_THAYDOI(let vct_id, let tag)
     */
    async KIEMTRA_THAYDOI(vct_id, tag) {
      // try {
      let xttph = await this.callApiWrapper(api.kiem_tra_thay_doi, { chungtu_id: vct_id })
      if (xttph == null || xttph.length == 0) {
        this.$toast.warning('Không lấy được thông tin trạng thái phiếu!')
        return false
      }
      if (xttph.length > 0) {
        if (this.ttph_id != xttph[0].ttphId) {
          this.$toast.warning('Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
          return false
        }
        if (xttph.length > 0) {
          if (this.ttph_id != xttph[0].ttphId) {
            this.$toast.warning('Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin')
            return false
          }
          if (tag == 'GHILAI' || tag == 'UPSL') {
            if (xttph[0].maMd == MUCDICH_VT.MUASAM) {
              this.$toast.warning('Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa')
              return false
            }
            if (xttph[0].maMd == MUCDICH_VT.CHUYEN_CP && tag == 'GHILAI') {
              this.$toast.warning('Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép sửa')
              return false
            }
          }
          if (tag == 'GHILAI_NUTGHILAI') {
            if (xttph[0].maMd == MUCDICH_VT.MUASAM) {
              this.$toast.warning('Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép sửa')
              return false
            }
          }
          return true
        }
        return true
      }
      return true
      // } catch (e) {
      //   this.$toast.warning('Có lỗi khi lấy thông tin trạng thái phiếu')
      //   return false
      // }
    },
    /**
     * @see:  private bool KiemTraHanChotCT_V2()
     */
    async KiemTraHanChotCT_V2() {
      let vkho_nhan_id = 0
      let vkho_giao_id = 0
      let ngay_ct = this.dtpNgayDK.value

      if (this.cboTuKho.value) {
        vkho_giao_id = this.cboTuKho.value
      }
      if (this.cboDenKho.value) {
        vkho_nhan_id = this.cboDenKho.value
      }

      let s = await this.callApiWrapper(api.kiem_tra_han_chung_tu, {
        chungTuId: this.vchungtu_id,
        khoGiaoId: vkho_giao_id,
        khoNhanId: vkho_nhan_id,
        ngayCt: ngay_ct
      })
      if (s != '1') {
        this.$toast.error(`Đã quá hạn sửa chứng từ ${this.soph}!`)
        return false
      }
      return true
    },
    /**
     * @see: private void tsbtnHoanThanh_Click(object sender, EventArgs e)
     */
    async tsbtnHoanThanh_Click() {
      let kq = await this.callApiWrapper(api.hoanthien_chungtu_frmnhapxuatv2, {
        p_chungtu_id: this.vchungtu_id,
        p_sendsms: this.ckbSendSMS.value ? 1 : 0,
        p_ngay_ct: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
        p_nguoidung_id: await this.$root.token.getNguoiDungID(),
        p_may_cn: await this.$root.token.getMachine(),
        p_ip_cn: await this.$root.token.getIP(),
        p_ttph_id: this.ttph_id
      })
      if (kq.length && kq[0].CODE == 1) {
        this.$toast.success(kq[0].MSG)
        if (this.up_nguoc_idchitiet_qlts) {
          let error_api = ''
          let table_ = await this.callApiWrapper(api.kiem_tra_chi_tiet_qlts, { chungtu_id: this.vchungtu_id })
          if (table_.length > 0) {
            // for (let i = 0; i < table_.length; i++) {
            //   table_.Columns[i].ColumnName = table_.Columns[i].ColumnName.ToLower()
            // }
            let json_api = table_
            let kq_api = await this.callApiWrapper(api.update_asset_scan_info, { list: json_api })
            if (kq_api.ToUpper().includes('FALSE') || kq_api.ToUpper().includes('DOCTYPE')) {
              // bangts.closeLoading(this)

              // kq_api = kq_api.Replace("\\\\", "\\").Replace("\\\"", "\"")
              // kq_api = kq_api.Replace("\"{", "{").Replace("}\"", "}")
              //                 Root_UPDATE_TS myDeserializedClass = JsonConvert.DeserializeObject < Root_UPDATE_TS > (kq_api)
              // let notifi = myDeserializedClass.message

              // this.$toast.error("Cập nhật lÍn QLTS không thành công: " + notifi)
              return
            }
          }
        }
      }
    },
    async btnDoiSoatVT_Click() {
      if (!this.tsbtnNhapMoi.enabled) {
        this.$toast.warning('Đang ở trạng thái nhập mới không thể mở form!')
        return
      }
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn chưa chọn chứng từ !')
        return
      }
      if (this.ttph_id == 4) {
        this.$toast.warning('Chừng từ đã hoàn thiện, không thể sử dụng chức năng này !')
        return
      }
      this.frmDoiSoat.input.vchungtu_id = this.vchungtu_id
      this.ejsDialog.name = 'frmDoiSoat'
      this.ejsDialog.header = 'Đối soát'
      this.$refs.ejsDialog.show()
    },
    /**
     * @see: private void simpleButton1_Click(object sender, EventArgs e)
     */
    async simpleButton1_Click() {
      // try {
      if (!(await this.KT_ChungTu_Psis()))
        //Chừng từ điều chỉnh psis tạo tự động không cho sửa thủ công
        return
      if (this.tsbtnNhapMoi.enabled) {
        if (this.nghiepvu_id_chungtu != '' && ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') || this.nghiepvu_id_chungtu == '288' || this.nghiepvu_id_chungtu == '286' || this.nghiepvu_id_chungtu == '282')) {
          this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
          return
        }
        if (!isNullOrEmpty(this.gridChungTu.value.NGAY_XD)) {
          this.$toast.warning('Đã có ngày ký, không thể sửa chứng từ !')
          return
        }
        if (!(await this.KiemTra_XuatHoanTra(false))) return
        if (!(await this.KiemTra_NhapLeDieuChinh(false))) return
        if (this.kiemtra_quyen) {
          if (!(await this.kiemtra_thaydoi_ctct())) {
            return
          }
        }
      }
      if (!confirm('Bạn có chắc chán muốn xóa hết thiết bị của chứng từ này')) return
      if (!(await this.KiemTraHanChotCT())) {
        return
      }
      if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, ''))) {
        return
      }
      let KIEMTRA_SUATH = await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })
      if (KIEMTRA_SUATH == 'ko') {
        this.$toast.warning('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
        return
      }
      if (this.kiemtra_quyen) {
        let ds_dt = await this.callApiWrapper(api.ds_ngay_nhan_thanhtoan_chungtu, { chungtu_id: this.vchungtu_id })
        if (ds_dt == null || ds_dt.length == 0) {
          this.$toast.warning('Không có dữ liệu ngày nhận không thể xóa')
          return
        } else {
          if (ds_dt.NGAYNHAN_TT != null && ds_dt.NGAYNHAN_TT != '') {
            this.$toast.warning('Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị')
            return
          }
        }
        if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) {
          return
        }
      }
      if (this.ttph_id == 4) {
        let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
        if (s != '1') {
          this.$toast.warning(s)
          return
        }
        s = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
          chungTuId: this.vchungtu_id,
          nguoiCn: await this.$root.token.getUserName(),
          mayCn: await this.$root.token.getMachine(),
          ipCn: await this.$root.token.getIP()
        })
        if (s != '1') {
          this.$toast.warning('Có lỗi trong quá trình xóa phiếu!' + s)
          return
        }
      }
      let k = await this.callApiWrapper(api.xoa_chung_tu_bao_hanh, {
        kieu: 3,
        chungTuId: this.vchungtu_id,
        chungTuCtId: 0,
        thietBiId: 0
      })
      if (k != '1') {
        this.$toast.warning(k)
        return
      }
      this.dtThietBi = []
      await this.callApiWrapper(api.xoa_tat_ca_chung_tu_thiet_bi, { chungtu_id: this.vchungtu_id })
      // CHUANHOA_CT_TBI(0, 0) //bosung
      let cttemp_id = this.vchungtu_id
      await this.Load_ds_chungtu()
      this.gridChungTu.value.CHUNGTU_ID = cttemp_id
      // } catch (ex) {
      //   this.$toast.warning(`${ex}`)
      // }
    },
    async btnXuatExcelVT_Click() {
      await this.SaveXlsAndOpenFile('VatTu-' + this.txtSoPhieu.value.replace('/', '-') + '.xlsx', 'sheet1', this.dtVatTu)
    },
    async simpleButton2_Click() {
      await this.SaveXlsAndOpenFile('ThietBi-' + this.txtSoPhieu.value.replace('/', '-') + '.xls', 'sheet1', this.dtThietBi)
    },
    async tsbtnSinhSerial_Click() {
      if (!this.gridVatTu.value) {
        return
      }
      this.frmTaoSerial.input.vctct_id = this.gridVatTu.value.CTCT_ID
      this.ejsDialog.name = 'frmTaoSerial'
      this.ejsDialog.header = 'Tạo Serial'
      this.$refs.ejsDialog.show()
    },
    cboDonViNhan_EditValueChanged() {},
    async Update_SoLanIn() {
      if (this.xacnhan_in_chungtu) {
        if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)) {
          return
        }
        if (!confirm(`Bạn có xác nhận đã in chứng từ không ?`)) return
        await this.CapNhatSoLanInCT({ chungTuId: this.vchungtu_id, maNd: await this.$root.token.getUserName() })
        this.$toast.success('Xác nhận in thành công !!!')
        await this.Load_ds_chungtu()
      } else {
        let res = await this.callApiWrapper(api.cap_nhat_so_lan_in, { chungtu_id: this.vchungtu_id })
        if (res != '1') {
          this.$toast.error(`Có lỗi khi cập nhật số lần xem báo cáo: ${res}`)
        }
      }
    },
    async tsbtnInPhieu_Click(id) {
      let maBc = null
      switch (id) {
        case 'phieu_nhap':
        case 'phieu_xuat':
          if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
            this.$toast.error('Không có dữ liệu in phiếu')
            return
          }
          break
        case 'phieu_xuat':
          break
      }
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'XDVTC'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async xu_ly_in_phieu(maBC) {
      console.log('xu_ly_in_phieu')
      console.log('HTVTTT')
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu chi tiết!')
        return
      }
      let report_chungtu_id = this.vchungtu_id
      if (maBC == 'HTVTTT') {
        report_chungtu_id = this.$refs.gridChungTu.grid
          .getSelectedRecords()
          .map((e) => e.CHUNGTU_ID)
          .join(', ')
      }

      let kq = await this.callApiWrapper(api.sp_lay_baocao_qlvt_bip, { p_ma_bc: maBC })
      let cauLenh = kq.length > 0 ? kq[0].CAULENH : null
      this.loading(true)
      let kqRpPrs = (await api.get_report_params(this.axios, { path: cauLenh })).data.listOfParamNameValues.item
      kqRpPrs = kqRpPrs.map((e) => {
        if (e.name == 'username') e.value = this.$root.token.getUserName()
        if (['P_PHANVUNG_ID', 'p_phanvung_id', 'v_phanvung_id'].includes(e.name)) e.value = this.$root.token.getPhanVungID()
        if (e.name == 'p_chungtu_id') e.value = report_chungtu_id
        if (e.name == 'v_chungtu_id') e.value = report_chungtu_id
        if (e.name == 'vchungtu_id') e.value = report_chungtu_id
        if (e.name == 'p_nhanvien') e.value = this.$root.token.getNhanVienID()
        if (e.name == 'vnhanvien') e.value = this.$root.token.getNhanVienID()
        if (e.name == 'p_donvi') e.value = this.$root.token.getDonViID()
        if (e.name == 'vdonvi') e.value = this.$root.token.getDonViID()

        return e
      })

      let kqReport = await api.report_run(this.axios, {
        report: cauLenh,
        type: 'pdf',
        file_name: 'file.pdf',
        items: kqRpPrs
      })

      let pdf = null
      if (kqReport.data) {
        try {
          pdf = this.base64ToArrayBuffer(kqReport.data)
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + kqReport.data)
        }
      } else {
        this.$root.$toast.error('Biên bản từ hệ thống BIP trả về rỗng. Hãy thử lại!')
      }

      this.frmXemMauIn.input.pdf = pdf
      this.ejsDialog.name = 'frmXemMauIn'
      this.ejsDialog.header = 'Xem mẫu in'
      this.$refs.ejsDialog.show()
      await this.Update_SoLanIn()
      this.loading(false)
      // this.InBienBan.input.chungtu_id = this.vchungtu_id
      // this.InBienBan.input.path = cauLenh
      // this.$refs.popupInBB.showModal()
      // this.loading(true)
      // await this.axios({
      //   url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
      //   method: 'POST',
      //   responseType: 'blob',
      //   data: {
      //     chungTuId: this.vchungtu_id,
      //     nhanVien: '',
      //     donVi: '',
      //     maBc: 'CT_SRVT'
      //   }
      // })
      //   .then(async (response) => {
      //     previewPrint(response.data)
      //     await this.Update_SoLanIn()
      //   })
      //   .finally(() => {
      //     this.loading(false)
      //   })
    },
    async in_phieu(print_id, data) {
      switch (print_id) {
        case 'phieu_nhap':
        case 'phieu_giao_hoan_tra_vt':
          if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
            this.$toast.error('Không có dữ liệu in phiếu')
            return
          }
          break
      }

      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: data
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async tsbtnPhieuNhap_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'PNK'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async tsbtnPhieuXuat_Click() {
      console.log('tsbtnPhieuXuat_Click')
      if (this.$root.token.getPhanVungID() == '54' || this.$root.token.getMaTinh() == 'TGG') {
        this.tenNguoiNhan = ''
        this.maBC = 'PXK'
        // this.$bvModal.show('popupTenNguoiNhan')
        this.frmNhapText_v2.input.vkieu_nhap = 'nhap_text'
        this.frmNhapText_v2.input.tieuDe = 'Nhập tên người nhận'
        this.ejsDialog.name = 'frmNhapText_v2'
        this.ejsDialog.header = 'Tên người nhận'
        this.$refs.ejsDialog.show()
      } else {
        if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
          this.$toast.error('Không có dữ liệu in phiếu')
          return
        }
        if (this.$root.token.getPhanVungID() == '54' || this.$root.token.getMaTinh() == 'TGG') {
          this.tenNguoiNhan = ''
          this.maBC = 'PXK'
          this.$bvModal.show('popupTenNguoiNhan')
        } else {
          this.loading(true)
          await this.axios({
            url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
            method: 'POST',
            responseType: 'blob',
            data: {
              chungTuId: this.vchungtu_id,
              nhanVien: '',
              donVi: '',
              maBc: 'PXK'
            }
          })
            .then(async (response) => {
              previewPrint(response.data)
              await this.Update_SoLanIn()
            })
            .finally(() => {
              this.loading(false)
            })
        }
      }
    },
    async txtQuetSerial_KeyPress() {
      if (this.tsbtnNhapMoi.enabled) {
        if (this.nghiepvu_id_chungtu != '' && this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') {
          this.$toast.error('Chứng từ tạo từ dự án IMS không được điều chỉnh')
          return
        }
      }
      let seri = this.txtQuetSerial.value.trim().toUpperCase()
      let sl = seri.split('|')
      if (this.qr_code && sl.length > 1 && sl.length != 3) {
        this.$toast.error('Dữ liệu định dạng QR không đúng')
        return
      }
      if (this.qr_code && sl.length > 1 && sl.length == 3) {
        seri = seri.split('|')[2]
      }
      let vkho_id = parseInt(this.cboTuKho.value)
      let dt = await this.callApiWrapper(api.capnhat_quetserial, {
        p_chungtu_id: this.vchungtu_id,
        p_nguoidung_id: await this.$root.token.getNguoiDungID(),
        p_may_cn: await this.$root.token.getMachine(),
        p_ip_cn: await this.$root.token.getIP(),
        p_serial: seri.toUpperCase(),
        p_tsbtnNhapMoi: this.tsbtnNhapMoi.enabled ? 1 : 0
      })
      if (dt != 'error' && dt) {
        await this.Load_ds_thietbi()
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          await this.Load_ds_vattu()
        }
      }
      this.txtQuetSerial.value = ''
    },
    async repositoryItemButtonEdit3_ButtonClick() {
      if (!confirm('Bạn có chắc chán muốn xóa thiết bị này!')) return
      // this.$refs.gridThietBi.deleteRecord(this.gridThietBi.value);
      let id = this.gridThietBi.value.THIETBI_ID
      let ct_id = this.gridThietBi.value.CTCT_ID
      if (id > 0) {
        await this.XoaChungTuThietBiTheoTBI({ ctctId: ct_id, thietBiId: id })
      }
    },
    async KhoiTaoNoiDung() {
      if (this.tsbtnNhapMoi.enabled) {
        return
      }
      let nd = ''
      if (this.kieunhap == 1) {
        this.txtNoiDung.value = ''
      } else {
        // DHSX đang comment
        this.txtNoiDung.value = nd
      }
    },
    async KiemTraKho() {
      if (!this.tsbtnNhapMoi.enabled && !this.cboTuKho.value) {
        k_goc = parseInt(this.cboTuKho.value)
        if (this.$refs.gridVatTu.getDataRows().length) {
          if (this.cboTuKho.value != this.khotu_id) {
            if (!confirm(`Nếu bạn thay đổi sang kho ${this.$refs.cboTuKho.TEN_KHO} danh sách vật tư đã chọn sẽ được xóa`)) {
              this.dtThemVT = []
              this.gridVatTu.list = []
            } else {
              this.cboTuKho.value = k_goc
            }
          }
        }
      }
      if (this.tsbtnNhapMoi.enabled && this.tsbtnGhiLai.enabled) {
        if (this.$refs.gridVatTu.getDataRows().length) {
          if (this.cboTuKho.value != this.t_kho_id && this.t_kho_id != 0) {
            if (!confirm(`Nếu bạn thay đổi sang kho ${this.$refs.cboTuKho.TEN_KHO} danh sách vật tư đã chọn sẽ được xóa`)) {
              this.dtThemVT = []
              this.gridVatTu.list = []
            } else {
              this.cboTuKho.value = this.t_kho_id
            }
          }
        }
      }
    },
    async cboTuKho_EditValueChanged() {
      await this.KhoiTaoNoiDung()
    },
    async cboDenKho_EditValueChanged() {
      await this.KhoiTaoNoiDung()
    },
    async XacNhan_frmNhapCap() {
      let frm = this.$refs.frmNhapCap
      if (frm.xacnhan) {
        let kq = await this.TachCuonCap({
          kieu: 1,
          khoId: this.cboTuKho.value,
          khoNhanId: this.cboDenKho.value,
          ctctId: frm.vctct_id,
          nguoiCN: await this.$root.token.getUserName(),
          mayCN: await this.$root.token.getUserName(),
          ipCN: '10.59.90.123',
          data: JSON.letify(frm.data)
        })
        if (kq != '1') {
          this.$toast.error(`${kq}`)
        }
        if (this.tsbtnNhapMoi.enabled) {
          let ctemp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
          // await this.FocusGridViewRow('gridVatTu', "CHUNGTU_ID", ctemp_id)
        }
      }
    },
    async XacNhan_frmGanSeri() {
      let frm = this.$refs.frmGanSeri
      if (frm.xacnhan && frm.functionName == 'gridVatTu_ShownEditor') {
        if (this.tsbtnNhapMoi.enabled) {
          let ctemp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
        }
      }
      if (frm.xacnhan && frm.functionName == 'btnFilter_Click') {
        await this.Load_ds_thietbi()
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          await this.Load_ds_vattu()
          await this.NAP_DS_VT_GROUP()
        }
      }
    },
    async gridViewThietBi_ShownEditor(row) {
      // Xoa ThietBi
      if (!confirm('Bạn có chắc chán muốn xóa thiết bị này!')) return
      let kq = await this.callApiWrapper(api.xoa_thietbi_nhapxuatv2, {
        p_chungtu_id: this.vchungtu_id,
        p_ngay_ct: getDateStringOrDefault(this.dtpNgayDK.value, null, FORM_CONFIG.DEFAULT_DATE_FORMAT),
        p_nguoidung_id: await this.$root.token.getNguoiDungID(),
        p_serial: row.SERIAL,
        p_may_cn: await this.$root.token.getMachine(),
        p_ip_cn: await this.$root.token.getIP(),
        p_tsbtnNhapMoi: this.tsbtnNhapMoi.enabled ? 1 : 0,
        p_ttph_id: this.ttph_id,
        p_thietbi_id: row.THIETBI_ID,
        p_ctct_id: row.CTCT_ID
      })
      if (kq != null && kq.length > 0 && kq[0].CODE == 1) {
        await this.Load_ds_thietbi()
        this.$toast.success(`${kq[0].MSG}`)
      } else {
        // this.$toast.warning(kq[0] != null ? `${kq[0].MSG}` : 'Thất bại')
        return
      }
      //   let ctemp_id = this.vchungtu_id
      // await this.Load_ds_chungtu()
    },
    async CHUANHOA_CT_TBI(ctct_id, thietbi_id) {
      if (this.ctct_id > 0 && this.thietbi_id > 0) {
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          let kiemtra = await this.callApiWrapper(api.kiem_tra_chi_tiet_vat_tu, { chungtu_id: this.vchungtu_id })
          if (kiemtra != 0) {
            let thucthi = await this.callApiWrapper(api.cap_nhat_so_luong_chi_tiet_vat_tu, {
              chungTuId: this.vchungtu_id,
              ds: '0',
              ctct_id: ctct_id,
              kieu: 0
            })
          }
        }
      } else {
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          let kiemtra = await this.callApiWrapper(api.kiem_tra_chi_tiet_vat_tu, { chungtu_id: this.vchungtu_id })
          if (kiemtra != 0) {
            let thucthi = await this.callApiWrapper(api.cap_nhat_so_luong_chi_tiet_vat_tu, {
              chungTuId: this.vchungtu_id,
              ds: '0',
              ctct_id: 0,
              kieu: 0
            })
          }
        }
      }
    },
    async tsbtnCopyCT_Click() {
      // try {
      if (this.vma_md == MUCDICH_VT.MUASAM) {
        this.$toast.error('Chứng từ mua sắm tập trung chỉ có thể xóa hoặc hoàn thành không được phép cóp')
        return
      }
      if (this.vma_md == MUCDICH_VT.CHUYEN_CP) {
        this.$toast.error('Chứng từ cấp phát chỉ có thể xóa hoặc hoàn thành không được phép cóp')
        return
      }
      let cp_chungtu_id = this.vchungtu_id
      this.Clear()
      this.SetButton(1)
      this.dtCopyCT = await this.callApiWrapper(api.copy_chung_tu_v2, { chungtu_id: cp_chungtu_id, kieu: 1 })
      this.dtCopyCTCT = await this.callApiWrapper(api.copy_chung_tu_v2, { chungtu_id: cp_chungtu_id, kieu: 2 })
      if (this.dtCopyCT.length) {
        await this.load_copy_ct(this.dtCopyCT)
      }
      if (this.dtCopyCTCT.length) {
        this.dtVatTu = this.dtCopyCTCT
        this.gridVatTu.list = this.dtVatTu
      }
      this.copyct = true

      // } catch (error) {
      //   this.$toast.error("Có lỗi:")
      // }
    },
    async load_copy_ct(dt) {
      for (let r of dt) {
        if (r.LOAI == '2') {
          this.cboLoaiChungTu.value = 99
        } else {
          this.cboLoaiChungTu.value = parseInt(r.LOAICT_ID)
        }

        if (r.LOAICT_ID == '99') {
          this.kieunhap = 3
        } else {
          this.kieunhap = 2
        }
        this.cboMucDich.value = parseInt(r.MUCDICH_ID)
        if (r.LOAICT_ID == '2') {
          this.cboTuKho.value = parseInt(r.KHO_GIAO_ID)
          this.cboDenKho.value = parseInt(r.KHO_NHAN_ID)
        } else {
          // điều kiện như có như không - Hiền hồ
          this.cboTuKho.value = parseInt(r.KHO_GIAO_ID)
          this.cboDenKho.value = parseInt(r.KHO_NHAN_ID)
        }
        this.txtNoiDung.value = r.ND_GIAO
      }
    },
    async tsbtnPhieuChuyen_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      if (this.$root.token.getPhanVungID() == '54' || this.$root.token.getMaTinh() == 'TGG') {
        this.tenNguoiNhan = ''
        this.maBC = 'PCK'
        this.$bvModal.show('popupTenNguoiNhan')
      } else {
        this.loading(true)
        await this.axios({
          url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
          method: 'POST',
          responseType: 'blob',
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: '',
            donVi: '',
            maBc: 'PCK'
          }
        })
          .then(async (response) => {
            previewPrint(response.data)
            await this.Update_SoLanIn()
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    async btnXuatTonTbi_Click() {
      if (!this.dtVatTu.length) {
        this.$toast.error('Chứng từ được tạo chưa có vật tư!')
        return
      }
      let dt = await this.getDSTonKhoTheoCT({ chungTuId: this.vchungtu_id })
      await this.SaveXlsAndOpenFile('Thietbi-' + this.vchungtu_id + '.xlsx', 'sheet1', dt)
    },
    /**
     * @see: private void btnTimKiem_Click(object sender, EventArgs e)
     */
    async btnTimKiem_Click() {
      this.$refs.gridChungTu.grid.clearFiltering()
      await this.LamMoi()
    },
    /**
     * @see: void LamMoi()
     */
    async LamMoi() {
      await this.Load_ds_chungtu()
    },

    async XacNhan_frmGanSeri_V2() {
      await this.Load_ds_thietbi()
      if (this.xTabTongHop.visible && this.chonvattu_lohang) {
        await this.Load_ds_vattu()
        await this.NAP_DS_VT_GROUP()
      }
    },
    async ckbCTCT_CheckedChanged() {
      // grvVT.CloseEditor()
    },
    async toolStripButton1_Click() {
      // DHSX chua co action
    },
    async vbtnRefresh_MouseClick() {
      await this.Load_ds_chungtu()
    },
    /**
     * @see: private void tsbtnDongBo_Click(object sender, EventArgs e)
     */
    // async tsbtnDongBo_Click() {
    //   // try {
    //   this.loi_excel = []
    //   let dt = this.gridChungTu.list
    //   if (dt == null || dt.length == 0) {
    //     this.$toast.warning('Không có thông tin chứng từ để đồng bộ dữ liệu tập đoàn!')
    //     return
    //   }
    //   let kt = this.$refs.gridChungTu.grid.getSelectedRecords()
    //   if (kt.length == 0) {
    //     this.$toast.warning('Bạn chưa chọn chứng từ để đồng bộ!')
    //     return
    //   }
    //   if (!confirm(`Bạn có chắc chắn muốn đồng bộ ${kt.length} chứng từ lên hệ thống FMIS không?`)) return
    //   //     let frm = new frmDangTai(DONGBO_KT_DS, kt.CopyToDataTable());
    //   //     frm.TopMost = false;
    //   //     frm.SetText("Đang đồng bộ ...", "...");
    //   //     frm.ShowCounter = true;
    //   //     frm.btnExit.enabled=false;

    //   //     switch (frm.ShowDialog(this))
    //   //     {
    //   //         case DialogResult.OK:

    //   //             if (frm.Output == "1")
    //   //             {

    //   //                 Message_Box.ShowTB("Đã đồng bộ thành công "+(kt.Length-loi_excel.Rows.Count) +"/"+kt.Length);
    //   //             }
    //   //             else
    //   //             {
    //   //                 this.$toast.warning(frm.Output);
    //   //             }

    //   //             // Bỏ tÌch ischeck khi show thÙng báo
    //   //             bool coloi = false;
    //   //             if (loi_excel.Rows.Count > 0)
    //   //                 coloi = true;
    //   //             foreach (DataRow dr in kt)
    //   //             {
    //   //                 dr.ISCHECK = "0";
    //   //                 if (coloi)
    //   //                 {
    //   //                     if (loi_excel.Select("CHUNGTU_ID=" + dr.CHUNGTU_ID + " ").Length == 0)
    //   //                     {
    //   //                         dr.STATUS = "1";
    //   //                     }
    //   //                 }
    //   //                 else
    //   //                 {
    //   //                     dr.STATUS = "1";
    //   //                 }
    //   //             }
    //   //             dt.AcceptChanges();
    //   //             gridChungTu.RefreshData();
    //   //             if (coloi)
    //   //             {
    //   //                 if (DialogResult.No == Message_Box.ShowQuestion("có " + loi_excel.Rows.Count + " chứng từ lỗi. Bạn có muốn xuất excel không?"))
    //   //                 {
    //   //                     return;
    //   //                 }
    //   //                 ChucNang.SaveXLS("LoiDongBoFMIS.xls", "sheet1", "", loi_excel);
    //   //             }

    //   //             break;

    //   //         case DialogResult.Abort:
    //   //             Clear();
    //   //             Message_Box.ShowError(frm.Error.Message);

    //   //             break;
    //   //     }
    //   // }
    //   // catch (ex)
    //   // {
    //   //     this.$toast.warning("có lỗi khi đồng bộ FMIS " + ex);
    //   // }
    // },
    async tsbtnDongBo_Click() {
      // try {
      if (this.gridChungTu.list.length == 0) {
        this.$toast.error('Không có thông tin chứng từ để đồng bộ dữ liệu tập đoàn')
        return
      }
      let gridChungTuSelected = this.$refs.gridChungTu.grid.getSelectedRecords()
      if (gridChungTuSelected.length == 0) {
        this.$toast.error('Bạn chưa chọn chứng từ để đồng bộ')
        return
      }
      if (!confirm(`Bạn có chắc chắn muốn đồng bộ ${gridChungTuSelected.length} chứng từ lên hệ thống FMIS không?`)) return
      let loi_excel = []
      // let DSChungTuChecked = this.gridChungTu.list.filter(a=>this.listChungTuChecked.includes(a.CHUNGTU_ID))

      for (let e of gridChungTuSelected) {
        if (this.$root.token.getMaTinh() == 'HNI') {
          let trangThai = await this.callApiWrapper(api.kiem_tra_thay_doi, { chungtu_id: e.CHUNGTU_ID })
          if (trangThai.length == 0 || trangThai[0].ttphId != '4') {
            loi_excel.push({
              CHUNGTU_ID: e.CHUNGTU_ID,
              SOPHIEU: e.SOPHIEU,
              LYDO: 'Chứng từ chưa hoàn thành, không thể đồng bộ.'
            })
            continue
          }
        }

        if ((await this.$root.token.getUserName()) == '' || (await this.$root.token.getUserName()) == null) {
          loi_excel.push({
            CHUNGTU_ID: e.CHUNGTU_ID,
            SOPHIEU: e.SOPHIEU,
            LYDO: 'Lỗi không lấy được người dùng, liên hệ admin xử lý (FE)'
          })
          continue
        }

        let db_kt = await this.callApiWrapper(api.ktdb_fmis_taikhoan, {
          vchungtu_id: e.CHUNGTU_ID,
          // tenDv: this.chondvi_fmis ? this.params.p_DonViFMIS : '',
          vnguoi_cn: await this.$root.token.getUserName()
        })

        if (db_kt != 'ok') {
          loi_excel.push({
            CHUNGTU_ID: e.CHUNGTU_ID,
            SOPHIEU: e.SOPHIEU,
            LYDO: db_kt
          })
        }
      }

      if (loi_excel.length == 0) {
        this.$toast.success('Đồng bộ thành công ' + gridChungTuSelected.length + '/' + gridChungTuSelected.length + ' phiếu')
      } else {
        this.$toast.error('Có ' + loi_excel.length + ' chứng từ lỗi')
        await this.SaveXlsAndOpenFile('LoiDongBoFIMS.xlsx', 'sheet1', loi_excel)
        return
      }
      await this.btnTimKiem_Click()
      // } catch (e) {
      //   this.$toast.error(e.data.message)
      //   return
      // } finally {
      // }
    },
    /**
     * @see: private void tsbtnDongBoIMS_Click(object sender, EventArgs e)
     */
    async tsbtnDongBoIMS_Click() {
      await this.DONGBO_IMS_DS()
    },
    /**
     * @see: private void DONGBO_IMS_DS() && private void DONGBO_IMS_DS(frmDangTai sender, params object[] parameters)
     */
    async DONGBO_IMS_DS() {
      // try {
      let refGridChungTu = this.$refs.gridChungTu
      this.loi_excel = []
      let dt_ctu = this.gridChungTu.list
      if (dt_ctu == null || dt_ctu.length == 0) {
        this.$toast.warning('không có dữ liệu chứng từ')
        return
      }
      let t = this.$refs.gridChungTu.grid.getSelectedRecords()
      if (t.length == 0) {
        this.$toast.warning('Bạn chưa chọn chứng từ cần đồng bộ')
        return
      }
      let dem = 0
      let DSChungTuChecked = this.gridChungTu.list.filter((e) => refGridChungTu.grid.getSelectedRecords().filter((x) => x.CHUNGTU_ID == e.CHUNGTU_ID).length > 0)
      let loi_excel = []
      for (const dr of DSChungTuChecked) {
        let ketqua = ''
        let check = await this.callApiWrapper(api.kiem_tra_chung_tu_dong_bo, { chungtu_id: dr.CHUNGTU_ID })
        if (check.length == 0) {
          ketqua += 'Không tìm thấy chứng từ ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        let ma_md = check[0].ma_md
        let ma_pda = check[0].ma_pda
        let ma_csht = check[0].ma_csht
        let ma_dv_ql = check[0].ma_dv_ql
        let ma_dv_sd = check[0].ma_dv_sd
        let ma_hd_po = check[0].ma_hd_po
        let contract_info_id = check[0].contract_info_id
        let ngay_ct = check[0].ngay_ct
        if (ma_md != 'NV.XUATIMS' && ma_md != 'NV.ACAP' && ma_md != 'NV.CHUYENNGUON') {
          ketqua += 'Bạn chưa chọn đúng nghiệp vụ ! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        if (ma_pda == null || ma_pda == '') {
          ketqua += 'Chưa có mã dự án! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        if (ma_md != 'NV.CHUYENNGUON' && this.batbuoc_chst && (ma_csht == null || ma_csht == '')) {
          ketqua += 'Chưa có mã CSHT! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        if (ma_dv_sd == null || ma_dv_sd == '') {
          ketqua += 'Chưa có mã đơn vị sử dụng! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        if (ma_dv_ql == null || ma_dv_ql == '') {
          ketqua += 'Chưa có mã đơn vị quản lý! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        if (ma_md != 'NV.ACAP') {
          if (ma_hd_po == null || ma_hd_po == '') {
            ketqua += 'Chưa có mã hợp đồng! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue
          }
          if (contract_info_id == null || contract_info_id == '') {
            ketqua += 'Chưa có contract_info_id ! Liên hệ với Admin ERP để xử lý ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
            loi_excel.push({
              CHUNGTU_ID: dr.CHUNGTU_ID,
              SOPHIEU: dr.SOPHIEU,
              LYDO: ketqua
            })
            continue
          }
        }
        let chitiet_vattu = await this.callApiWrapper(api.kiem_tra_dong_bo_ims, { chungtu_id: this.vchungtu_id })
        if (chitiet_vattu.length == 0) {
          ketqua += 'Không có chi tiết vật tư thiết bị! Không thể đồng bộ lên IMS ! (' + dr.CHUNGTU_ID + ' - ' + dr.SOPHIEU + ') \r\n'
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: ketqua
          })
          continue
        }
        let i_mode = ma_md == 'NV.XUATIMS' || ma_md == 'NV.CHUYENNGUON' ? '1' : '0'
        let dongbo = await this.callApiWrapper(api.xu_ly_dong_bo_ims, {
          projectCode: ma_pda,
          contractNumber: contract_info_id,
          soPhieu: this.soph,
          ngayCt: ngay_ct,
          maDvQl: ma_dv_ql,
          maDvSd: ma_dv_sd,
          maCsHt: ma_csht,
          iMode: i_mode,
          chiTietVatTu: chitiet_vattu
        })
        if (dongbo == 'Thực hiện thành công') {
          let up_trangthai = await this.callApiWrapper(api.cap_nhat_bbbg_ims, {
            chungTuId: dr.CHUNGTU_ID,
            kieu: 0
          })
          dem += 1
        } else {
          loi_excel.push({
            CHUNGTU_ID: dr.CHUNGTU_ID,
            SOPHIEU: dr.SOPHIEU,
            LYDO: dongbo
          })
        }
      }
      if (dem == DSChungTuChecked.length) {
        this.$toast.success(`Đồng bộ thành công ${dem}/${dem} phiếu`)
      } else {
        this.$toast.error(`Có ${loi_excel.length} chứng từ lỗi`)
        await this.SaveXlsAndOpenFile('LoiDongBoIMS.xlsx', 'sheet1', loi_excel)
        return
      }
      await this.btnTimKiem_Click()
      // } catch (ex) {
      //   this.$toast.error(`có lỗi khi đồng bộ ${ex}`)
      // }
    },
    async selected_gridVatTuRow(data) {
      if (data) this.gridVatTu.value = data
    },
    async tsbtnSinhSr_Click() {
      if (!this.gridVatTu.value) {
        this.$toast.warning('Vui lòng chọn vật tư!')
        return
      }
      this.frmTaoSerial.input.vctct_id = this.gridVatTu.value.CTCT_ID
      this.ejsDialog.name = 'frmTaoSerial'
      this.ejsDialog.header = 'Tạo Serial'
      this.$refs.ejsDialog.show()
    },
    async toolStripMenuItem3_Click() {
      if (!this.gridThietBi.list.length) {
        this.$toast.error('Chứng từ này ko có thiết bị')
        return
      }
      let ds = this.gridThietBi.list
      let q = ds.filter((a) => a.SERIAL != null && a.SERIAL)
      if (q.length) {
        this.frmTaoSerial.input.kieu = 1
        this.frmTaoSerial.input.dsCT_ChungTu = q
        this.ejsDialog.name = 'frmTaoSerial'
        this.ejsDialog.header = 'Tạo Serial'
        this.$refs.ejsDialog.show()
      }
    },
    async btnTB_ButtonClick() {
      let frm = this.$refs.frmChungTu_TBao

      if (parseInt(this.gridChungTu.value.SPBH) > 0) {
        this.frmChungTu_TBao.input.kieu = 1
      }
      this.frmChungTu_TBao.input.chungtu_id = this.vchungtu_id
      this.ejsDialog.name = 'frmChungTu_TBao'
      this.ejsDialog.header = 'Danh sách thuê bao'
      this.$refs.ejsDialog.show()
    },
    /**
     * @see: private void chkAll_Phieu_CheckedChanged(object sender, EventArgs e)
     */
    async chkAll_Phieu_CheckedChanged() {
      await this.LamMoi()
    },
    async gridviewChungTu_CustomRowCellEdit() {
      // if (e.Column.Name == "DSTB" && e.RowHandle >= 0)
      // {
      //     if (gridVatTu.GetRowCellValue(e.RochkAll_Phieu_CheckedChangedwHandle, "SPVT")) > 0 ||
      //         gridVatTu.GetRowCellValue(e.RowHandle, "SPBH")) > 0)
      //         e.RepositoryItem = btnTB
      //     else
      //         e.RepositoryItem = emptyEditor
      // }
    },
    async tstPhieuTUVT_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }

      if (this.$root.token.getPhanVungID() == '54' || this.$root.token.getMaTinh() == 'TGG') {
        this.tenNguoiNhan = ''
        this.maBC = 'PTU'
        this.$bvModal.show('popupTenNguoiNhan')
      } else {
        this.loading(true)
        await this.axios({
          url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
          method: 'POST',
          responseType: 'blob',
          data: {
            chungTuId: this.vchungtu_id,
            nhanVien: '',
            donVi: '',
            maBc: 'PTU'
          }
        })
          .then(async (response) => {
            previewPrint(response.data)
            await this.Update_SoLanIn()
          })
          .finally(() => {
            this.loading(false)
          })
      }
    },
    async openFrmQLTS() {
      let qlts = this.$refs.frmQLTS
      let dt_ctct = this.gridVatTu
      let loai_serial = dt_ctct.filter((a) => a.LOAITBI_ID == '2')
      if (!loai_serial.length) {
        this.$toast.error('Không có thiết bị là loại có serial !')
        return
      }
      let sl_con_ = await this.getTongSoLuongChuaGan({ chungTuId: this.vchungtu_id })
      if (sl_con_ && sl_con_ != '') {
        qlts.sl_cothe_gan = parseInt(sl_con_)
      }
      qlts.id_chungtu = this.vchungtu_id
      qlts.Tag = '1'
      qlts.openQLTS()
    },
    async closeGanSeri_Tbi() {
      let f = this.$refs.GanSeri_Tbi
      if (f.xacnhan) {
        if (this.nghiepvu_id_chungtu == '288' && this.ma_pda_chungtu != '') {
          let go = await this.callApiWrapper(api.cap_nhat_serial_ims, {
            chungTuId: this.vchungtu_id,
            data: f.kq,
            kieu: 1
          })
          if (go == '1') {
            await this.Load_ds_thietbi()
          } else {
            this.$toast.error(`${go}`)
          }
        } else {
          await this.CapNhatExcelTbi_v2(f.kq)
        }
      }
    },
    async onClickChonQLTS() {
      this.$bvModal.hide('popupLuaChonQLTS')
      if (this.isQLTS) {
        await this.openFrmQLTS()
      } else {
        await this.openFrmGanSeriTbi()
      }
    },
    /**
     * @see: private void btnTaoTbi_Click(object sender, EventArgs e)
     */
    async btnTaoTbi_Click() {
      try {
        if (this.vchungtu_id == 0) {
          this.$toast.warning('Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!')
          return
        }
        if (!(await this.KT_ChungTu_Psis())) return

        if (this.tsbtnNhapMoi.enabled) {
          if (this.nghiepvu_id_chungtu != '' && ((this.nghiepvu_id_chungtu == '280' && this.ma_pda_chungtu != '') || this.nghiepvu_id_chungtu == '286' || this.nghiepvu_id_chungtu == '282')) {
            this.$toast.warning('Chứng từ tạo từ dự án IMS/QLTS không được sửa')
            return
          }
          if (this.soph.startsWith('QT')) {
            this.$toast.warning('Chứng từ quyết toán không thể sửa!')
            return
          }
          let kt_suath = await this.callApiWrapper(api.kiem_tra_sua_thu_hoi, { chungtu_id: this.vchungtu_id })
          if (kt_suath == 'ko') {
            this.$toast.warning('Chứng từ thu hồi nhập kho tổ trưởng không thể sửa')
            return
          }
          let s = await this.callApiWrapper(api.kiem_tra_cap_nhat_chung_tu, { chungtu_id: this.vchungtu_id })
          if (s != '1') {
            this.$toast.warning(s)
            return
          }
          if (this.ttph_id == 4) {
            this.$toast.warning('Chứng từ đã hoàn thành không thể tạo thiết bị')
            return
          }
        }
        if (this.dtVatTu.length == 0) {
          this.$toast.warning('Bạn chưa nhập vật tư chứng từ! Hãy nhập vật tư chứng từ và ghi lại, trước khi nhập thiết bị!')
          return
        }
        let kieu = ''
        if (this.chon_kieu_qlts && this.nghiepvu_id_chungtu != '288') {
          // let frm = new frmNhapText("Bạn Hãy lựa chọn kiểu nhập serial", 1)
          // frm.value = "Danh sách kiểu nhập serial"
          // frm.rdioCKb.Properties.Items[0].Description = "BÏnh thường"
          // frm.rdioCKb.Properties.Items[1].Description = "QLTS"
          // let kq = frm.ShowDialog()
          // switch (kq)
          // {
          //     case DialogResult.Cancel:
          //         return
          //         break
          //     case DialogResult.OK:
          //         kieu = frm.vkieu_luachon
          //         break
          // }
        }

        if (kieu != '' && kieu != '1') {
          // frmQLTS qlts = new frmQLTS()
          // let dt_ctct = this.gridVatTu.list
          // let loai_serial = dt_ctct.Select("LOAITBI_ID=2")
          // if (loai_serial.length== 0)
          // {
          //     this.$toast.warning("Không có thiết bị là loại có serial !")
          //     return
          // }

          // let sl_con_ = ChungTu.GET_DATA_SQL(@"select sum(sl_chuagan) sl from (
          //         SELECT a.ctct_id,a.lohang, a.soluong,
          //         a.soluong - (select nvl(sum(sl_ton),0) from {?DB12}.chungtu_tbi z where
          //                             z.ctct_id = a.ctct_id) sl_chuagan
          //         FROM {?DB12}.chitiet_ct a, {?DB12}.vattu b
          //         where a.vattu_id=b.vattu_id and a.chungtu_id=:vchungtu_id and b.loaitbi_id = 2)",
          //       "vchungtu_id", vchungtu_id)
          if (sl_con_.length > 0) {
            if (sl_con_[0][0] != '') qlts.sl_cothe_gan = sl_con_[0][0]
          }
          qlts.id_chungtu = vchungtu_id
          qlts.Tag = '1'
          qlts.ShowDialog()
          if (qlts.xacnhan) {
            this.Load_ds_vattu()
            this.Load_ds_thietbi()
          }
        } else {
          let ds_gan = []
          if (this.tsbtnNhapMoi.enabled) {
            if (this.kiemtra_quyen) {
              if (!(await this.KIEMTRA_QUYEN_XN(4, this.t_kho_id))) {
                return
              }
            }
            if (this.gridThietBi.list != null) {
              let d_s = this.gridThietBi.list
              let q = d_s.filter((l) => l.SERIAL != null)
              if (q.length != 0) {
                ds_gan = q
              }
            }
            let mucdich_id = this.cboMucDich.value
            let vma_md = ''
            let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: mucdich_id })
            if (d.length > 0) this.vma_md = d[0]
            if (this.gridVatTu == null || this.gridVatTu.length == 0) {
              this.$toast.warning('Danh sách vật tư chưa có. Không thể tạo được thiết bị')
              return
            }
            let dt = []
            if (this.vchungtu_id > 0 && this.dtVatTu.length > 0) {
              let a = this.dtVatTu.filter((p) => p.LOAITBI_ID == '2')
              for (let item of a) {
                let sl = item.SOLUONG
                for (let i = 0; i < sl; i++) {
                  let r = {}
                  r.LOHANG = item.LOHANG
                  r.MA_VT = item.MA_VT
                  r.TEN_VT = item.TEN_VT
                  r.DVI_TINH = item.DVI_TINH
                  r.SERIAL = ''
                  r.SOLUONG = 1
                  r.DONGIA = item.DONGIA
                  dt.push(r)
                }
              }
              for (let d_ of dt) {
                for (let d_2 of ds_gan) {
                  if (d_.SERIAL == '' && d_2.LOHANG == d_.LOHANG && d_2.MA_VT == d_.MA_VT && d_2.DONGIA == d_.DONGIA) {
                    d_.SERIAL = d_2.SERIAL
                    d_.MATHUNG = d_2.MATHUNG
                    ds_gan = ds_gan.filter((e) => e.SERIAL != d_.SERIAL)
                    break
                  }
                }
              }
            }
            let frm = this.$refs.GanSeri_Tbi
            if (this.nghiepvu_id_chungtu == '288' && this.ma_pda_chungtu != '') {
              let loc_kq_ims = await this.callApiWrapper(api.lay_ds_serial_null_ims, {
                chungTuId: this.vchungtu_id,
                data: dt,
                kieu: 0
              })
              frm.dt = loc_kq_ims
              frm.dt_luu = loc_kq_ims
            } else {
              frm.dt = dt
              frm.dt_luu = dt
            }
            frm.kq = []
            frm.open()
          }
        }
      } catch (e) {
        this.$toast.error(`Lỗi ${e}`)
      }
    },
    async XacNhan_frmQLTS() {
      let qlts = this.$refs.frmQLTS
      if (qlts.xacnhan) {
        await this.Load_ds_vattu()
        await this.Load_ds_thietbi()
      }
    },
    /**
     * @see: private void cboTuKho_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async cboTuKho_ButtonClick(buttonTag) {
      // try {
      if (buttonTag == 'BH') {
        if (this.cboMucDich.value == null) {
          this.$toast.warning('Bạn phải chọn mục đích')
          return
        }
        let mucdich = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (mucdich[0] == 'N.VTPSIS') {
          if (this.tsbtnNhapMoi.enabled) {
            if (this.ttph_id == 4) {
              this.$toast.warning('Chứng từ đã hoàn thiện, không thể chỉnh sửa!')
              return
            }
            if (!(await this.KiemTraNguoiCN())) {
              this.$toast.warning(`Bạn không được xóa chứng từ của ${this.gridChungTu.value.NGUOI_CN}!`)
              return
            }
          }
          // let f = this.$refs.frmNhapDieuChinh
          this.frmNhapDieuChinh.input.tag = 4
          this.frmNhapDieuChinh.input.vma_md = mucdich[0]
          if (this.tsbtnNhapMoi.enabled) {
            this.frmNhapDieuChinh.input.vchungtu_luu_tam = this.vchungtu_id
            this.frmNhapDieuChinh.input.vchungtu_id = this.gridChungTu.value.CHUNGTU_CHA_ID
          }
          // f.openNhapDieuChinh()
          this.ejsDialog.name = 'frmNhapDieuChinh'
          this.ejsDialog.header = 'Nhập điều chỉnh'
          this.$refs.ejsDialog.show()
        }
        ////////////////////////////////////////////////////////////////
        else if (this.cboLoaiChungTu.value == 99 && ((await this.get_mamd(this.cboKieuPhieu.value)) == '10' || (await this.get_mamd(this.cboKieuPhieu.value)) == '23')) {
          if (!this.tsbtnNhapMoi.enabled && (mucdich[0] == 'N-X.DCMSL' || mucdich[0] == 'N.VT')) {
            this.$toast.warning('Bạn phải có chứng từ nhập rồi mới có thể chọn để gán chứng từ xuất')
            return
          }
          // let f = this.$refs.frmNhapDieuChinh
          if (mucdich.length > 0) {
            if (mucdich[0] == 'N-X.DCMSL' || mucdich[0] == 'N.VT') {
              this.frmNhapDieuChinh.input.tag = 1
              this.frmNhapDieuChinh.input.vchungtu_gan = this.vchungtu_id
            } else if (mucdich[0] == 'N.DCDG' || mucdich[0] == 'N.TTCL' || mucdich[0] == 'SC_BH_DONGIA') {
              this.frmNhapDieuChinh.input.tag = 2
              this.frmNhapDieuChinh.input.vchungtu_id = this.tsbtnNhapMoi.enabled ? this.vchungtu_id_temp : 0
              this.frmNhapDieuChinh.input.vchungtu_id_v2 = this.tsbtnNhapMoi.enabled ? this.vchungtu_id_temp : 0
            } else if (['DC_ĐT.XDCB', 'DC_SCL.DDTS', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(mucdich[0])) {
              this.frmNhapDieuChinh.input.tag = 3
            } else {
              this.$toast.warning('Bạn chưa chọn đúng mục đích chứng từ')
              return
            }
            this.frmNhapDieuChinh.input.vma_md = mucdich[0]
            // f.vma_md = mucdich[0]
          }
          // f.dt = this.dt_chia_sl
          // f.kho_nhan = this.cboTuKho.value
          // f.kho_giao = this.cboDenKho.value
          // f.openNhapDieuChinh()
          //test
          this.frmNhapDieuChinh.input.dt = this.dt_chia_sl
          this.frmNhapDieuChinh.input.kho_nhan = this.cboTuKho.value
          this.frmNhapDieuChinh.input.kho_giao = this.cboDenKho.value
          this.ejsDialog.name = 'frmNhapDieuChinh'
          this.ejsDialog.header = 'Nhập điều chỉnh'
          this.$refs.ejsDialog.show()
        } else {
          if (this.cboMucDich.value == null) {
            this.$toast.warning('Bạn phải chọn mục đích!')
            return
          }
          let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
          if (d.length > 0)
            if (d[0] == '1.NBH' && this.dsQuyen.includes(99)) {
              await this.LAY_CHUNGTU()
            } else {
              this.$toast.warning('Bạn phải chọn mục đích là 1.NBH hoặc bạn không có quyền thiết bị bảo hành')
              return
            }
        }
      }
      // } catch (e) {
      //   this.$toast.warning('Lỗi h‡m chọn từ đơn vị ' + e)
      // }
    },
    async LAY_CHUNGTU() {
      try {
        let f = this.$refs.frmVatTuTrongKho
        f.vkho_id = parseInt(this.cboTuKho.value)
        f.kieu = 2
        f.vngay_ct = this.dtpNgayDK.value
        f.dtLuuVT = this.dtLuuVT
        this.ten_khotu = this.$refs.cboTuKho.getDataByValue(this.cboTuKho.value).TEN_KHO
        f.vngay_ct = this.dtpNgayDK.value
        if (!this.tsbtnNhapMoi.enabled) {
          this.vchungtu_id = 0
        } else {
          f.vchungtu_id = this.vchungtu_id
        }
        this.isChonVT = false // dùng để xác định trả về giữa Lấy chứng từ và ChonVT
        f.ShowDialog()
      } catch (error) {
        this.$toast.error('Lỗi lấy chứng từ và vật tư')
      }
    },
    async XacNhan_frmVatTuTrongKho_LayChungTu() {
      let f = this.$refs.frmVatTuTrongKho

      if (f.XacNhan == false) {
        return
      }
      this.dtLuuVT = f.dt
      let dtThemVT_v2 = []
      for (let item of f.dt) {
        let r = {}
        r.TATCA = f.checkAll ? 1 : 0
        r.CTCT_ID = 0
        r.LOHANG = item.LOHANG
        r.VATTU_ID = parseInt(item.VATTU_ID)
        r.MA_VT = item.MA_VT
        r.SOLUONG = item.SL_XUAT
        r.DONGIA = parseInt(item.DONGIA)
        r.TIEN = parseInt(item.SL_XUAT) * parseInt(item.DONGIA)
        r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
        r.LOAI_TBI = item.LOAI_TBI
        r.LOAITBI_ID = item.LOAITBI_ID
        r.HAN_BH = ''
        r.TY_LE = item.TY_LE === undefined || item.TY_LE == null ? '' : item.TY_LE
        r.MAKHOTD = item.MAKHOTD === undefined || item.MAKHOTD == null ? '' : item.MAKHOTD
        r.LOAIKHO = item.LOAIKHO === undefined || item.LOAIKHO == null ? '' : item.LOAIKHO
        r.MATHETS = item.MATHETS === undefined || item.MATHETS == null ? '' : item.MATHETS
        dtThemVT_v2.push(r)
      }
      for (let dr of dtThemVT_v2) {
        dr.CTCT_ID = 0
      }
      this.gridVatTu.list = dtThemVT_v2
      this.dtVatTu = dtThemVT_v2
      this.khotu_id = f.vkho_ID
      this.cboTuKho.value = this.khotu_ID
      // this.enabled.gridVatTu.SOLUONG = true
      this.kieunhap = 4
      this.kieugoi_id = 2
    },
    /**
     * @see: private void ckb_nd_CheckedChanged(object sender, EventArgs e)
     */
    async ckb_nd_CheckedChanged() {
      await this.LamMoi()
    },
    async tsbtnChiTietSR_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu chi tiết!')
        return
      }

      let kq = await this.callApiWrapper(api.sp_lay_baocao_qlvt_bip, { p_ma_bc: 'CT_SRVT' })
      let cauLenh = kq.length > 0 ? kq[0].CAULENH : null
      this.loading(true)
      let kqRpPrs = (await api.get_report_params(this.axios, { path: cauLenh })).data.listOfParamNameValues.item
      kqRpPrs = kqRpPrs.map((e) => {
        if (e.name == 'p_phanvung_id') e.value = this.$root.token.getPhanVungID()
        if (e.name == 'p_chungtu_id') e.value = this.vchungtu_id
        if (e.name == 'p_nhanvien') e.value = this.$root.token.getNhanVienID()
        if (e.name == 'p_donvi') e.value = this.$root.token.getDonViID()
        return e
      })

      let kqReport = await api.report_run(this.axios, {
        report: cauLenh,
        type: 'pdf',
        file_name: 'file.pdf',
        items: kqRpPrs
      })

      let pdf = null
      if (kqReport.data) {
        try {
          pdf = this.base64ToArrayBuffer(kqReport.data)
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Xảy ra lỗi khi lấy dữ liệu biên bản in: ' + kqReport.data)
        }
      } else {
        this.$root.$toast.error('Biên bản từ hệ thống BIP trả về rỗng. Hãy thử lại!')
      }
      this.loading(false)
      this.frmXemMauIn.input.pdf = pdf
      this.ejsDialog.name = 'frmXemMauIn'
      this.ejsDialog.header = 'Xem mẫu in'
      this.$refs.ejsDialog.show()
      // this.InBienBan.input.chungtu_id = this.vchungtu_id
      // this.InBienBan.input.path = cauLenh
      // this.$refs.popupInBB.showModal()
      // this.loading(true)
      // await this.axios({
      //   url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
      //   method: 'POST',
      //   responseType: 'blob',
      //   data: {
      //     chungTuId: this.vchungtu_id,
      //     nhanVien: '',
      //     donVi: '',
      //     maBc: 'CT_SRVT'
      //   }
      // })
      //   .then(async (response) => {
      //     previewPrint(response.data)
      //     await this.Update_SoLanIn()
      //   })
      //   .finally(() => {
      //     this.loading(false)
      //   })
    },
    base64ToArrayBuffer(base64) {
      let binaryString = window.atob(base64)
      let len = binaryString.length
      let bytes = new Uint8Array(len)
      for (let i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i)
      }
      return bytes.buffer
    },
    /**
     * @see: private void tsbtnSMS_Click(object sender, EventArgs e)
     */
    async tsbtnSMS_Click() {
      try {
        if (this.gridChungTu.list.length == null || this.gridChungTu.list.length == 0) {
          this.$toast.warning('Không có danh sách chứng từ để send sms')
          return
        }
        if (this.gridChungTu.value == null) {
          this.$toast.warning('Bạn chưa chọn chứng từ cần send sms')
          return
        }
        if (!confirm('Bạn có muốn send sms cho người quản lý kho nhận của chứng từ ' + this.txtSoPhieu.value + ' không?')) return
        let ct_id = this.gridChungTu.value.CHUNGTU_ID
        let kq = await this.callApiWrapper(api.send_sms_chung_tu, {
          tuDong: 0,
          chungTuId: ct_id,
          vmay_cn: await this.$root.token.getMachine(),
          vnguoi_cn: await this.$root.token.getUserName(),
          ip_cn: await this.$root.token.getIP()
        })

        if (kq == null) {
          this.$toast.warning('Send sms thất bại')
          return
        }
        if (kq == 'OK') {
          this.$toast.success('Send sms thành công')
        } else {
          this.$toast.warning(`Send sms thất bại ${kq}`)
        }
      } catch (e) {
        this.$toast.error(`Có lỗi khi send sms ${e}`)
      }
    },
    async tsbtnInPhieu_Click() {},
    /**
     * @see: private bool KIEMTRA_QUYEN_XN(let kieu, let kho_id)
     */
    async KIEMTRA_QUYEN_XN(kieu, kho_id) {
      // try {
      let dt = await await this.callApiWrapper(api.kiem_tra_quyen_xn, { nhanvien_id: await this.$root.token.getNhanVienID(), kho_id: kho_id })
      if (dt == null || dt.length == 0) {
        this.$toast.warning('Không lấy được thông tin quyền xác nhận trên kho ' + this.cboTuKhoObject?.tenKho)
        return false
      }
      if (kieu == 1) {
        if (dt[0].quyenKg == '0') {
          this.$toast.warning('Bạn không có quyền xác nhận ngày gửi trên kho ' + this.cboTuKhoObject?.tenKho)
          return false
        }
      }
      if (kieu == 2) {
        if (dt[0].quyenKn == '0') {
          this.$toast.warning('Bạn không có quyền xác nhận ngày nhận trên kho ' + this.cboDenKhoObject?.tenKho)
          return false
        }
      }
      if (kieu == 3) {
        if (dt[0].quyenKn == '0') {
          this.$toast.warning('Bạn không có quyền xác nhận trên kho ' + this.cboDenKhoObject?.tenKho + '.Bạn không thể hoàn công.')
          return false
        }
      }
      if (kieu == 4) {
        if (dt[0].quyenKg == '0') {
          this.$toast.warning('Bạn không có quyền tạo thiết bị trên kho ' + this.cboTuKhoObject?.tenKho + '.Bạn không thể hoàn công.')
          return false
        }
      }
      if (kieu == 5) {
        if (dt[0].quyenKg == '0') {
          this.$toast.warning('Bạn không có quyền xác nhận trên kho ' + this.cboTuKhoObject?.tenKho + '.Bạn không thể xóa/sửa chứng từ.')
          return false
        }
      }
      return true
      // } catch (e) {
      //   this.$toast.warning('Có lỗi khi kiểm tra quyền xác nhận ' + e)
      //   return false
      // }
    },
    /**
     * @see: private bool chan_ngay_nhangui()
     */
    async chan_ngay_nhangui() {
      return this.dsQuyen.includes(2502) ? false : true
    },
    /**
     * @see: private void dtpNgayGuiTT_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async dtpNgayGuiTT_ButtonClick(buttonTag) {
      // try {
      if (buttonTag == '2') {
        if (!this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Chỉ được phép cập nhật ngày gửi khi đã có chứng từ')
          return
        }
        let kq = await this.callApiWrapper(api.capnhat_ngay_gui, {
          p_chungtu_id: this.vchungtu_id,
          p_ngaygui: getDateStringOrDefault(this.dtpNgayGuiTT.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          p_nguoidung_id: await this.$root.token.getNguoiDungID(),
          p_ttph_id: this.ttph_id
        })
        if (kq.length && kq[0].CODE == 1) {
          this.$toast.success(`${kq[0].MSG}`)
        } else {
          return
        }
      } else if (buttonTag == 'ALL') {
        {
          this.$toast.warning('Chỉ được phép cập nhật ngày gửi khi đã có chứng từ')
          return
        }
        let dt_ctu = this.gridChungTu.list
        if (dt_ctu == null || dt_ctu.length == 0) {
          this.$toast.warning('Không có dữ liệu chứng từ')
          return
        }
        let t = this.gridChungTu.selected
        if (t.length == 0) {
          this.$toast.warning('Bạn chưa chọn chứng từ cần gửi')
          return
        }
        let ketqua = ''
        let dem = 0
        for (let i = 0; i < t.length; i++) {
          let dr = t[i]
          // try {
          let v_k_giao = dr.KHO_GIAO_ID != null && dr.KHO_GIAO_ID != '' ? 0 : dr.KHO_GIAO_ID
          let v_k_nhan = dr.KHO_NHAN_ID != null && dr.KHO_NHAN_ID != '' ? 0 : dr.KHO_NHAN_ID
          let kq = await this.callApiWrapper(api.capnhat_ngay_gui, {
            p_chungtu_id: dr.CHUNGTU_ID,
            p_ngaygui: getDateStringOrDefault(this.dtpNgayGuiTT.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
            p_nguoidung_id: await this.$root.token.getNguoiDungID(),
            p_ttph_id: dr.TTPH_ID
          })
          if (kq.length && kq[0].CODE == 1) {
            dem += 1
          } else {
          }
          //kiem tra dem o day
          // } else {
          //   ketqua += dr.SOPHIEU + kiemtra_ct + ' \r\n'
          //   continue
          // }
          // } catch (e) {
          //   this.$toast.error(`Lỗi ngày gửi: ${e}`);
          // }
        }
        ketqua += '\r\nCập nhật ngày gửi thành công: ' + dem + '/' + t.length
        this.$toast.warning(`${ketqua}`)
      }
      await this.Load_ds_chungtu() //chuyển load ra ngoài load cho cả 2 trường hợp cho lành
      // } catch (e) {
      //   this.$toast.error('Cập nhật ngày gửi thất bại ' + e)
      // }
    },
    /**
     * @see: private void dtpNgayNhanTT_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async dtpNgayNhanTT_ButtonClick(buttonTag) {
      // try {
      if (buttonTag == '2') {
        if (!this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Chỉ được phép cập nhật ngày nhận khi đã có chứng từ!')
          return
        }
        let kq = await this.callApiWrapper(api.capnhat_ngay_nhan, {
          p_SendSMS: this.ckbSendSMS.value ? 1 : 0,
          p_ngay_ct: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          p_chungtu_id: this.vchungtu_id,
          p_ngaynhan: getDateStringOrDefault(this.dtpNgayNhanTT.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          p_nguoidung_id: await this.$root.token.getNguoiDungID(),
          p_may_cn: await this.$root.token.getMachine(),
          p_ip_cn: await this.$root.token.getIP(),
          p_ttph_id: this.ttph_id
        })
        if (kq.length && kq[0].CODE == 1) {
          this.$toast.success(`${kq[0].MSG}`)
        } else {
          return
        }
      } else if (buttonTag == 'ALL') {
        if (!this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Chỉ được phép cập nhật ngày nhận khi đã có chứng từ!')
          return
        }
        let dt_ctu = this.gridChungTu.list
        if (dt_ctu == null || dt_ctu.length == 0) {
          this.$toast.warning('Không có dữ liệu chứng từ')
          return
        }
        let t = this.gridChungTu.selected
        if (t.length == 0) {
          this.$toast.warning('Bạn chưa chọn chứng từ cần nhận!')
          return
        }
        let ketqua = ''
        let dem = 0
        for (let i = 0; i < t.length; i++) {
          let dr = t[i]
          let kq = await this.callApiWrapper(api.capnhat_ngay_nhan, {
            p_SendSMS: this.ckbSendSMS.value ? 1 : 0,
            p_ngay_ct: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
            p_chungtu_id: dr.CHUNGTU_ID,
            p_ngaynhan: getDateStringOrDefault(this.dtpNgayNhanTT.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
            p_nguoidung_id: await this.$root.token.getNguoiDungID(),
            p_may_cn: await this.$root.token.getMachine(),
            p_ip_cn: await this.$root.token.getIP(),
            p_ttph_id: this.ttph_id
          })
          if (kq.length && kq[0].CODE == 1) {
            this.$toast.success(`${kq[0].MSG}`)
          } else {
            return
          }
          // } catch (ex) {
          //   this.$toast.error(`Lỗi ngày nhận: ${ex}`);
          // }
        }
        ketqua += '\r\nCập nhật ngày nhận thành công: ' + dem + '/' + t.length
        this.$toast.success(`${ketqua}`)
      }
      await this.Load_ds_chungtu() //cũng làm như ngày xuất
      // } catch (e) {
      //   this.$toast.error('Cập nhật ngày nhận thất bại ' + e)
      // }
    },
    /**
     * @see: private void tsbtnTraLai_Click(object sender, EventArgs e)
     */
    async tsbtnTraLai_Click() {
      // try
      // {
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn phải chọn chứng từ để xóa')
        return
      }
      if (!(await this.KiemTraHanChotCT())) return
      if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, ''))) return
      if (this.kiemtra_quyen) {
        if (!(await this.KIEMTRA_QUYEN_XN(2, this.kho_nhan_goc))) return
        if (this.ttph_id != 4) {
          this.$toast.warning('Chứng từ chưa được hoàn thiện không thể trả phiếu.')
          return
        }
        // try {
        if (!(await this.KiemTraDuLieu(false, true, 'TRALAI'))) return
        if (!(await this.KiemTra_NhapLeDieuChinh(true))) return
        if (!confirm(`Bạn có chắc chắn muốn trả phiếu không!`)) return
        if (this.ttph_id == 4) {
          let s = await this.callApiWrapper(api.kiem_tra_xoa_phieu_hoan_thanh, { chungtu_id: this.vchungtu_id })
          if (s != '1') {
            this.$toast.warning(`${s}`)
            return
          }
          if (this.ckbSendSMS.value) {
            try {
              await this.callApiWrapper(api.send_sms_chung_tu, {
                tuDong: 2,
                chungTuId: this.vchungtu_id
              })
            } catch (ex) {
              this.$toast.error(`Có lỗi khi send sms tự động ${ex}`)
            }
          }
          s = await this.callApiWrapper(api.xoa_phieu_da_hoan_thanh, {
            chungTuId: this.vchungtu_id,
            nguoiCn: await this.$root.token.getUserName(),
            mayCn: await this.$root.token.getMachine(),
            ipCn: await this.$root.token.getIP()
          })
          if (s != '1') {
            this.$toast.warning(`Có lỗi trong quá trình xóa phiếu: ${e}!`)
            return
          }
        }
        let k = await this.callApiWrapper(api.xoa_chung_tu_bao_hanh, {
          kieu: 2,
          chungTuId: this.vchungtu_id,
          chungTuCtId: 0,
          thietBiId: 0
        })
        if (k != '1') {
          this.$toast.warning(`${k}`)
          return
        }
        let kq = await this.CapNhat_CT(2, false, true)
        try {
          await this.callApiWrapper(api.cap_nhat_ngay_nhan_ct, {
            ngayNhanTT: '',
            nguoiTh: '',
            chungTuId: this.vchungtu_id
          })
        } catch (ex) {
          this.$toast.warning(`Có lỗi khi cập ngật ngày nhận ${ex}`)
          return
        }
        await this.CapNhatChiTiet_CT(false)
        let vchungtu_tmp_id = this.vchungtu_id
        if (this.vtdk.length > 0) {
          await this.CAPNHAT_DANGKY(vchungtu_tmp_id)
        }
        await this.Load_ds_chungtu()
        this.copyct = false
        // }
        // catch (ex) {
        //   this.$toast.error(ex)
        // }
      }
      // }
      // catch (ex)
      // {
      //     this.$toast.warning("Thực hiện trả phiếu thất bại " + ex)
      // }
    },
    async ckbTuKho_CheckedChanged() {
      await this.Loc_Kho()
    },
    async ckbDenKho_CheckedChanged() {
      await this.Loc_Kho()
    },
    async btnDelete_VTDK_Click() {
      await this.AN_GROUP(true)
    },
    async AN_GROUP(mo) {
      if (mo) {
        this.ThongTinHDDA.visible = false
      } else {
        this.ThongTinHDDA.visible = true

        if (this.cboHopDong.list.length == 0) {
          this.cboHopDong.list = await this.callApiWrapper(api.ds_hop_dong_mua_sam, {})
          this.cbo_DuAn.list = await this.callApiWrapper(api.ds_du_an, { chkDuAn: this.ckbDuAn.value ? 1 : 0 })
          this.cbo_DuAn.value = this.cbo_DuAn.list.length > 0 ? this.cbo_DuAn.list[0].DUAN_ID : null
        }
        this.txtGhiChuHD.value = ''
      }
    },
    async splitterItem1_DoubleClick() {
      if (this.cboLoaiChungTu.value) {
        if (this.cboLoaiChungTu.value == '99') {
          await this.AN_GROUP(false)
        }
      }
    },
    async ckbDuAn_CheckedChanged() {
      if (this.ckbDuAn.value) {
        let da = await this.getDSDuAnV2({ chkDuAn: this.ckbDuAn.value ? 1 : 0 })
        this.cbo_DuAn = da
      } else {
        let da = await this.getDSDuAnV2({ chkDuAn: this.ckbDuAn.value ? 1 : 0 })
        this.cbo_DuAn = da
      }
    },
    /**
     * @see: private bool KIEMTRA(let kieu)
     */
    async KIEMTRA(kieu) {
      // try {
      if (!this.tsbtnNhapMoi.enabled) {
        this.$toast.error('Bạn chỉ có thể gán gỡ khi đang ở trạng thái cập nhật chứng từ')
        return false
      }
      if (!this.gridVatTu.list.length) {
        this.$toast.error('Không có thông tin chi tiết vật tư')
        return false
      }
      let ds = this.gridVatTu.list
      if (ds.length == 0) {
        this.$toast.error('Không có thông tin chi tiết vật tư')
        return false
      }
      if (this.gridVatTu.selected.length == 0) {
        this.$toast.error('Bạn chưa chọn vật tư cần gán')
        return false
      }
      if (kieu == 1) {
        if (this.cboHopDong.value == null) {
          this.$toast.error('Bạn chưa chọn thông tin hợp đồng')
          return false
        }
        if (this.cbo_DuAn.value == null) {
          this.$toast.error('Bạn chưa chọn thông tin dự án')
          return false
        }
      }
      return true
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra gán/ gỡ HĐ ")
      // }
    },
    /**
     * @see: private void btnGan_Click(object sender, EventArgs e)
     */
    async btnGan_Click() {
      // try {
      if (!(await this.KIEMTRA(1))) return
      let ds = this.gridVatTu.list
      let t = this.gridVatTu.selected
      if (this.gan_huy_duan) {
        for (let i of t) {
          if (i.DUAN != '') {
            this.$toast.error(`Vật tư lô hàng ${i.LOHANG} đã có mã dự án. Không thế gán đè !`)
            return
          }
        }
      }
      let json_list = JSON.stringify(t)
      await this.callApiWrapper(api.cap_nhat_thong_tin_ctct, {
        hdmsId: this.cboHopDong.value,
        duAnId: this.cbo_DuAn.value,
        ghiChu: this.txtGhiChuHD.value,
        data: json_list
      })
      if (this.qr_code) {
        let z = await this.callApiWrapper(api.sinh_qr_code, {
          ds: json_list,
          duAnId: this.cbo_DuAn.value,
          kieu: 0
        })
      }
      this.$toast.success('Cập nhập thông tin thành công')
      await this.Load_ds_vattu()
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi gán Hợp đồng dự án ")
      // }
    },
    /**
     * @see: private void btnGo_Click(object sender, EventArgs e)
     */
    async btnGo_Click() {
      // try {
      if (!(await this.KIEMTRA(0))) return
      let ds = this.gridVatTu.list
      let t = this.gridVatTu.selected
      if (this.gan_huy_duan) {
        for (let i of t) {
          let check = await this.callApiWrapper(api.kiem_tra_huy_gan_ma_duan, { soHieuCt: i.DUAN })
          if (check.length) {
            this.$toast.error(`Lô hàng: ${i.LOHANG} - Mã dự án tự động đồng bộ trên qlts. Không thể hủy gán!`)
            return
          }
        }
      }
      let json_list = JSON.stringify(t)
      await this.callApiWrapper(api.xoa_thong_tin_du_an, { data: json_list })
      if (this.qr_code) {
        let z = await this.callApiWrapper(api.sinh_qr_code, {
          ds: json_list,
          duAnId: '',
          kieu: 1
        })
      }
      this.$toast.success('Xóa thông tin hợp đồng dự án thành công')
      await this.Load_ds_vattu()
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi gỡ Hợp đồng dự án ")
      // }
    },
    async btnRef_Click() {
      if (this.ckbDuAn.value) {
        let da = this.getDSDuAnV2({ chkDuAn: this.ckbDuAn.value ? 1 : 0 })
        this.cbo_DuAn = da
      } else {
        let da = this.getDSDuAnV2({ chkDuAn: this.ckbDuAn.value ? 1 : 0 })
        this.cbo_DuAn = da
      }
    },
    //cboKieuPhieu_EditValueChanged
    async cboKieuPhieu_EditValueChanged(e) {
      console.log('cboKieuPhieu_EditValueChanged')
      console.log(this.cboKieuPhieu.value)
      if (this.cboKieuPhieu.value == null) {
        return
      }
      /**
       *  https://cntt.vnpt.vn/browse/BSS-99431
       *  Người dùng thường xuyên chọn sai mục đích nên set defaut kiếu phiếu hoàn trả
       *  thì mục đích mặc định "X.HTVT.TTVT - hoàn trả VT hư hỏng
       */
      if (this.cboKieuPhieu.value == 10102) {
        this.cboMucDich.value = 167
      }
      this.cboLoaiKho.list = await this.callApiWrapper(api.ds_loai_kho_td, { mucdich_id: this.cboKieuPhieu.value })
      if (!this.tsbtnNhapMoi.enabled) {
        let s = await this.callApiWrapper(api.loai_chung_tu_theo_muc_dich, { mucdich_id: this.cboKieuPhieu.value })
        if ((await this.get_mamd(this.cboKieuPhieu.value)) == '4' && s[0] == '1') {
          this.colTyLe.visible = true
        } else {
          this.colTyLe.visible = false
        }
        if (this.cboLoaiChungTu.value == '99') {
          let wtf = await this.get_mamd(this.cboKieuPhieu.value)
          if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10') {
            this.btnSeri_ChiaSL.enabled = true
          } else {
            this.cboDenKho.enabled = false
            this.cboTuKho.enabled = false
            this.btnSeri_ChiaSL.enabled = false
          }
        } else {
          this.btnSeri_ChiaSL.enabled = false
        }
      } else {
        if (this.cboLoaiChungTu.value == '99') {
          if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10' && this.vma_md != 'N.VT' && this.vma_md != 'N-X.DCMSL') {
            this.btnTaoTbi.enabled = false
            this.btnSeri_ChiaSL.enabled = true
          } else {
            this.btnTaoTbi.enabled = true
            this.btnSeri_ChiaSL.enabled = false
          }
        } else {
          this.btnSeri_ChiaSL.enabled = false
        }
      }
    },
    /**
     * @see: private void btnSeri_ChiaSL_Click(object sender, EventArgs e)
     */
    async btnSeri_ChiaSL_Click() {
      let t = this.ds_md.filter((a) => a.mucDichId == this.cboMucDich.value)
      if (t.length > 0 && (t[0].maMd == 'N.DCDG' || t[0].maMd == 'N.TTCL' || t[0].maMd == 'SC_BH_DONGIA' || t[0].maMd == 'NV_DAUTU_BQLDA' || t[0].maMd == 'NV_KHAC_BQLDA')) {
        this.frmGanSeri_v2.input = {
          sophieu: this.soph,
          vchungtu_id: this.vchungtu_id,
          vma_md: t[0].maMd,
          dt_vt: this.gridVatTu.list
        }
        this.ejsDialog.name = 'frmGanSeri_v2'
        this.ejsDialog.header = 'Gán Seri'
        this.$refs.ejsDialog.show()

        // this.frmGanSeri_v2.visible = true
        // this.$refs.frmGanSeri_v2.show()
      } else {
        this.$toast.error('Không đúng mục đích. Bạn hãy kiểm tra lại')
      }
    },
    async frmGanSeri_v2_onBtnXacNhanClicked(data) {
      let f = data
      if (f.xacnhan) {
        this.$refs.ejsDialog.hide()
        await this.Load_ds_thietbi()
      }
    },
    async tsbtnBBGNNhap_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'BBGNNK'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async tsbtnBBGNXuat_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'BBGNXK'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    /**
     * @see: private void btnChonTamUng_Click(object sender, EventArgs e)
     */
    async btnChonTamUng_Click() {
      console.log('btnChonTamUng_Click')
      // console.log(this.dc_ct_noibo)
      // console.log(this.dc_ct_ttvt)
      // console.log(this.dc_ct_ldscdp)
      // console.log(this.bqlda_nguonvon_dautu)
      // console.log(this.bqlda_nguonvon_khac)

      if (this.tsbtnNhapMoi.enabled && (this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp || this.bqlda_nguonvon_dautu || this.bqlda_nguonvon_khac)) {
        // 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'
        if (this.dc_ct_noibo || this.bqlda_nguonvon_khac || this.bqlda_nguonvon_dautu) {
          this.frmChonTBi_CT.input.vcongtrinh_id = this.cboCongTrinh.value
        }
        if (this.bqlda_nguonvon_khac || this.bqlda_nguonvon_dautu) {
          if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10') {
            this.frmChonTBi_CT.input.bqlda = true
          }
        }
        this.frmChonTBi_CT.input.vchungtu_id = this.vchungtu_id
        this.frmChonTBi_CT.input.vkho_id = parseInt(this.cboTuKho.value)
        this.ejsDialog.name = 'frmChonTBi_CT'
        this.ejsDialog.header = 'Công trình'
        this.$refs.ejsDialog.show()
      }
    },
    async XacNhan_frmChonTBi_CT() {
      let frm = this.$refs.frmChonTBi_CT
      if (value) {
        let nd = await this.callApiWrapper(api.noi_dung_giao_ct, { chungTuId: this.vchungtu_id })
        if (nd != null) {
          this.txtNoiDung.value = nd
        }
        await this.Load_ds_thietbi()
      }
    },
    /**
     * @see: private void dtpNgayChuyenKT_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async dtpNgayChuyenKT_ButtonClick(buttonTag) {
      if (buttonTag == '2') {
        if (this.gridVatTu.list.lenght == 0) {
          this.$toast.warning('Trong chứng từ chưa có kËm vật tư nÍn không thể hoàn thiện chứng từ.')
          return
        }
        let ngayChuyen = getDateStringOrDefault(this.dtpNgayChuyenKT.value)
        let z = await this.callApiWrapper(api.cap_nhat_ngay_chuyen, { chungtu_id: this.vchungtu_id, ngay_chuyen: ngayChuyen })
        let a = z
        if (a) {
          this.$toast.success('Cập nhật ngày chuyển thành công')
          this.gridChungTu.value.NGAYCT_KT = this.dtpNgayChuyenKT.value
          this.gridChungTu.value.NGUOI_CTKT = await this.$root.token.getUserName()
        } else {
          this.$toast.warning('Cập nhật ngày chuyển thất bại ' + z)
        }
      }
    },
    async tsbtnSuaCT_Click() {
      if (this.gridChungTu.value == null) {
        this.$toast.error('Chưa chọn chứng từ từ danh sách!')
        return
      }
      this.frmSuaCT.input = {
        chuaht_or_khac_2xcp: this.ttph_id == 4 || this.vma_md == '2.XCP' ? false : true,
        dtChungTu: this.dtChungTu,
        kieuphieu: this.gridChungTu.value.LOAIPHIEU_ID,
        vchungtu_id: this.vchungtu_id,
        loaict_id: this.cboLoaiChungTu.value == '99' ? 1 : this.cboLoaiChungTu.value,
        vma_md: this.vma_md
      }
      this.ejsDialog.name = 'frmSuaCT'
      this.ejsDialog.header = 'Sửa chứng từ'
      this.$refs.ejsDialog.show()
    },
    async XacNhan_frmSuaCT(value) {
      this.$refs.ejsDialog.hide()
      if (value.xacnhan || value.load_file_again) {
        await this.Load_ds_chungtu()
      }
    },
    /**
     * @see: private void dtpNgayDK_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async dtpNgayDK_ButtonClick(buttonTag) {
      try {
        if (buttonTag == null || buttonTag != 'Save') return
        if (this.vchungtu_id <= 0 || !this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Không có chứng từ không thể cập nhật')
          return
        }
        // if (!this.KiemTraNguoiCN()) {
        //   let ng_tiepnhan = this.gridChungTu.value.NGUOI_TIEPNHAN
        //   if (ng_tiepnhan != (await this.$root.token.getUserName())) {
        //     this.$toast.warning('Chỉ có người lập chứng từ mới có thể cập nhật ngày chứng từ')
        //     return
        //   }
        // }
        let ngayCt = getDateStringOrDefault(this.dtpNgayDK.value, null, 'DD/MM/YYYY HH:mm:ss')
        let kq = await this.callApiWrapper(api.xu_ly_cap_nhat_ngay_chung_tu, { chungtu_id: this.vchungtu_id, ngay_ct: ngayCt })
        if (kq != null && kq.length > 0 && kq[0].CODE == 1) {
          await this.Load_ds_chungtu()
          this.$toast.success(`${kq[0].MSG}`)
        } else {
          this.$toast.warning(kq[0] != null ? `${kq[0].MSG}` : 'Thất bại')
        }
      } catch (ex) {
        this.$toast.warning(`Có lỗi ${ex}`)
      }
    },
    /**
     * @see: private void cboMucDich_EditValueChanged(object sender, EventArgs e)
     */
    async cboMucDich_EditValueChanged(e) {
      console.log('cboMucDich_EditValueChanged')
      console.log(this.cboMucDich.value)
      if (isNullOrEmpty(this.cboLoaiChungTu.value)) return
      if (isNullOrEmpty(this.cboMucDich.value)) return
      let mdcheck = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
      if (this.cboLoaiChungTu.value == '99') {
        if (!this.tsbtnNhapMoi.enabled && ((['N.DCDG', 'N.TTCL', 'SC_BH_DONGIA', 'DC_ĐT.XDCB', 'DC_SCL.DDTS', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(mdcheck[0]) && this.cboKieuPhieu.value == '10119') || mdcheck[0] == 'N.VTPSIS')) {
          this.cboTuKho.enabled = false
          this.cboDenKho.enabled = false
          this.tsbtnThemVT.enabled = false
        } else {
          this.cboTuKho.enabled = true
          this.cboDenKho.enabled = true
          this.tsbtnThemVT.enabled = true
        }
      }
      if (mdcheck[0] == 'X.HTVT.TTVT' && this.cboLoaiChungTu.value != 1) {
        this.cboMucDich.value = null
        this.$toast.info('Xuất hoàn trả TTVT chỉ được chọn loại chừng từ nhập!')
      }
    },
    /**
     * @see: private bool kiemtra_thaydoi_ctct()
     */
    async kiemtra_thaydoi_ctct() {
      let kt = await this.callApiWrapper(api.kiem_tra_thay_doi_ctct, { chungtu_id: this.vchungtu_id, them_moi: this.tsbtnNhapMoi.enabled ? 1 : 0 })
      let result = kt[0]
      if (result != '1') {
        this.$toast.error(`${result}`)
        return false
      }
      return true
    },
    async tsbtnChonCTCT_Click() {
      if (this.vchungtu_id == 0) {
        this.$toast.error('Bạn chưa nhập chứng từ! Hãy nhập thông tin chứng từ và ghi lại, trước khi nhập thiết bị!')
        return
      }
      if (this.tsbtnNhapMoi.enabled) {
        let f = this.$refs.frmChonVatTuCTCT
        f.vchungtu_id = this.vchungtu_id
        f.openChonVatTuCTCT()
      }
    },
    async btnEditLKMK_Click() {
      if (this.ttph_id == '4') {
        this.$toast.error('Trạng thái phiếu đã hoàn thành!')
        return
      }
      this.frmLoaiKhoMaKho.input = {
        v_tag: 2,
        vkieuphieu_id: this.cboKieuPhieu.value,
        vloai_ct: parseInt(this.cboLoaiChungTu.value),
        vdt_dasua: this.gridVatTu.list
      }
      this.ejsDialog.name = 'frmLoaiKhoMaKho'
      this.ejsDialog.header = 'Chọn loại/mã kho'
      this.$refs.ejsDialog.show()
    },
    async frmLoaiKhoMaKho_onBtnXacNhanOnClicked(listVatTu) {
      console.log('frmLoaiKhoMaKho_onBtnXacNhanOnClicked')
      console.log(listVatTu)
      this.$refs.ejsDialog.hide()
      if (this.tsbtnNhapMoi.enabled && this.nghiepvu_id_chungtu != '' && (this.nghiepvu_id_chungtu == '280' || this.nghiepvu_id_chungtu == '288') && this.ma_pda_chungtu != '') {
        for (let e of listVatTu) {
          if (e.LOAIKHO != '') await this.callApiWrapper(api.cap_nhat_loai_kho_td, { loaikhotd_id: e.LOAIKHO, ctct_id: e.CTCT_ID })
          if (e.MAKHOTD != '') await this.callApiWrapper(api.cap_nhat_kho_td, { khotd_id: e.MAKHOTD, ctct_id: e.CTCT_ID })
        }
        await this.Load_ds_vattu()
      } else {
        this.gridVatTu.list = listVatTu
      }
    },
    async tsbtnExcelLoaiKho_Click() {
      await this.dieuchinh_vt_excel(1)
    },
    async tsbtnExcelChatLuong_Click() {
      await this.dieuchinh_vt_excel(2)
    },
    async tsbtnExcelGhiChu_Click() {
      await this.dieuchinh_vt_excel(3)
    },
    //wtf
    async dieuchinh_vt_excel(kieu) {
      if (this.vchungtu_id <= 0) {
        this.$toast.error('Chưa chọn chứng từ!!')
        return
      }
      if (this.gridVatTu.list.length == 0) {
        this.$toast.error('Không có vật tư!')
        return
      }
      if (!(await this.KiemTra_XuatHoanTra(false))) {
        return
      }
      if (!(await this.KiemTra_NhapLeDieuChinh(false))) {
        return
      }
      if (this.ttph_id == 4) {
        this.$toast.error('Chứng từ đã hoàn thiện ! Không thể dùng chức năng này !')
        return
      }
      this.kieuVTExcel = kieu
      // this.$refs.fileInputExcel.click()
      document.getElementById('fileVTExcel').click()
    },
    async VTExcelExcuted() {
      this.arrayImportFile.filexcel = this.$refs.fileVTExcel.$refs.input.files[0]

      let json_list = []
      // try {
      if (this.checkfile()) {
        let tencot
        const reader = new FileReader()
        reader.onload = async (e) => {
          /* Parse data */
          const bstr = e.target.result
          const wb = xlsx.read(bstr, { type: 'binary' })
          /* Get first worksheet */
          const wsname = wb.SheetNames[0]
          const ws = wb.Sheets[wsname]
          /* Convert array of arrays */
          const data = xlsx.utils.sheet_to_json(ws, { header: 1 })
          // retrieve headers (i.e. remove first row)
          let headers = data.shift() // bỏ dòng header đầu tiên trong data

          // then build the json for each row
          let result = data.map(function (a) {
            let jsonRow = {}
            a.forEach(function (cellValue, cellIndex) {
              jsonRow[headers[cellIndex]] = cellValue
            })
            headers.forEach((b) => {
              if (!Object.keys(jsonRow).includes(b)) {
                jsonRow[b] = ''
              }
            })
            return jsonRow
          })
          // --------------- --------------
          if (result.length == 0) {
            this.$toast.error('File không có dữ liệu')
            return
          } else {
            if (this.kieuVTExcel == 1) {
              if (!('CTCT_ID' in result[0] && 'LOAI_KHO_MOI' in result[0])) {
                this.$toast.error('Cấu trúc file phải có 2 cột CTCT_ID và LOAI_KHO_MOI!')
                return
              }
            }
            if (this.kieuVTExcel == 3) {
              if (!('CTCT_ID' in result[0] && 'CHAT_LUONG_MOI' in result[0])) {
                this.$toast.error('Cấu trúc file phải có 2 cột CTCT_ID và CHAT_LUONG_MOI!')
                return
              }
            }
            if (this.kieuVTExcel == 3) {
              if (!('CTCT_ID' in result[0] && 'GHI_CHU_MOI' in result[0])) {
                this.$toast.error('Cấu trúc file phải có 2 cột CTCT_ID và GHI_CHU_MOI!')
                return
              }
            }
          }

          let kq_kt = await this.callApiWrapper(api.kiem_tra_vt_doc_file, {
            data: result,
            chungTuId: this.vchungtu_id,
            kieu: this.kieuVTExcel
          })

          if (kq_kt == 1) {
            this.$toast.success('Đã cập nhật ghi chú thành công!')
            await this.Load_ds_vattu()
          } else {
            this.$toast.error(kq_kt.toString())
            return
          }
        }
        reader.readAsBinaryString(this.arrayImportFile.filexcel)
      }
      // } catch (error)  {
      //   this.$toast.error("Có lỗi xảy ra: " + error)
      //   return
      // } finally {

      // }
      this.$refs.fileVTExcel.reset()
      this.arrayImportFile.filexcel = null
    },
    UploadExcel(e) {
      try {
        const files = e.target.files
        if (!files.length) {
          return
        } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
          return this.$root.toastError('File excel không đúng định dạng!')
        }

        const fileReader = new FileReader()

        fileReader.onload = async (ev) => {
          const data = ev.target.result
          const XLSX = xlsx
          const workbook = XLSX.read(data, {
            type: 'binary'
          })
          const wsname = workbook.SheetNames[0]
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])

          if (ws.length == 0) {
            this.$toast.error('Không có dữ liệu đầu vào')
            return
          }
          if (!('CTCT_ID' in ws[0])) {
            this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : CTCT_ID')
            return
          }
          await this.KiemTraDuLieu_Excel(ws, this.kieuVTExcel)
        }
        fileReader.readAsBinarylet(files[0])
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra khi upload file!')
      }
    },
    async KiemTraDuLieu_Excel(dt, kieu) {
      this.loading(true)
      if (dt.length && Object.keys(dt[0]).length != 2) {
        this.$root.showLoading(false)
        this.$toast.error('Số lượng cột phải = 2')
        // await this.TaiFileMau(kieu)
        return
      }
      if (!dt[0].hasOwnProperty('CTCT_ID')) {
        this.$root.showLoading(false)
        this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : CTCT_ID')
        // await this.TaiFileMau(kieu)
        return
      }
      if (kieu == 1) {
        if (!dt[0].hasOwnProperty('LOAI_KHO_MOI')) {
          this.$root.showLoading(false)
          this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : LOAI_KHO_MOI')
          // await this.TaiFileMau(kieu)
          return
        }
      } else if (kieu == 2) {
        if (!dt[0].hasOwnProperty('CHAT_LUONG_MOI')) {
          this.$root.showLoading(false)
          this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : CHAT_LUONG_MOI')
          // await this.TaiFileMau(kieu)
          return
        }
      } else {
        if (!dt[0].hasOwnProperty('GHI_CHU_MOI')) {
          this.$root.showLoading(false)
          this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : GHI_CHU_MOI')
          // await this.TaiFileMau(kieu)
          return
        }
      }

      let kq_kt = await this.KiemTraVatTuDocfile({
        data: dt,
        chungTuId: this.vchungtu_id,
        kieu: kieu
      })
      if (kq_kt.includes('OK~')) {
        let tet = kieu == 1 ? 'loại kho' : kieu == 2 ? 'chất lượng' : 'ghi chú'
        this.$toast.error('Đã cập nhật ' + tet + ' các phiếu: ' + kq_kt.Split('~')[1])
        await this.Load_ds_vattu()
      } else {
        this.$toast.error(`${kq_kt}`)
        return
      }
    },
    async TaiFileMau(kieu) {
      if (!confirm('Bạn có muốn tải file mẫu về không ?')) return
      if (value) {
        if (kieu == 1) {
          // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/LOAIKHO.xlsx"
        } else if (kieu == 2) {
          // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/CHATLUONG.xlsx"
        } else {
          // f.fURL = "http://10.70.52.164:8081/FILES/HCM/VATTU/GHICHU.xlsx"
        }

        //     const data = await this.callApiWrapper(api.downloadFile, value)
        // if (data) {
        //   console.log('data')
        //   console.log(data.body)
        //   const filePath = value.split('/')
        //   const linkSource = `data:application/octet-stream;base64,${data.body.data}`
        //   const downloadLink = document.createElement('a')
        //   const fileName = filePath[filePath.length - 1]
        //   downloadLink.href = linkSource
        //   downloadLink.download = fileName
        //   downloadLink.click()
      }
    },
    // async gridVTGroup_DataSourceChanged() {
    //   if (this.xTabTongHop.visible) {
    //     this.ReadOnly.cboLoaiChungTu.list = this.gridVTGroup.list.length != 0
    //     this.ReadOnly.cboTuKho = this.gridVTGroup.list.length != 0
    //   } else {
    //     this.ReadOnly.cboLoaiChungTu.list = false
    //     this.ReadOnly.cboTuKho = false
    //   }
    // },
    /**
     * @see: private void tsbtnUpPXK_Click(object sender, EventArgs e)
     */
    async tsbtnUpPXK_Click() {
      await this.GHILAI_CT('UPSL')
    },
    async btnDowload_ButtonClick() {
      await DOWLOAD_OPEN(false)
    },
    async btnXem_ButtonClick() {
      await this.DOWLOAD_OPEN(true)
    },
    async DOWLOAD_OPEN(xemFile) {
      //...
    },
    /**
     * @see:
     */
    async dtpNgayKy_ButtonClick(buttonTag) {
      // try {
      if (buttonTag == '2') {
        if (this.gridVatTu.list.length == 0) {
          this.$toast.warning('Trong chứng từ chưa có kèm vật tư nên không thể ký chứng từ.')
          return
        }
        if (!this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Chứng từ phải được ghi lại mới có thể cập nhật ngày ký')
          return
        }
        if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)) {
          this.$toast.warning('Bạn phải có quyền xét duyệt chứng từ mới có thể thực hiện')
          return
        }
        let result = await this.callApiWrapper(api.xu_ly_cap_nhat_ngay_ky, {
          chungtu_id: this.vchungtu_id,
          ngay_ky: getDateStringOrDefault(this.dtpNgayKy.value)
        })
      } else if (buttonTag == 'ALL') {
        if (!this.tsbtnNhapMoi.enabled) {
          this.$toast.warning('Chứng từ phải được ghi lại mới có thể cập nhật ngày ký')
          return
        }
        if (!this.dsQuyen.includes(2520)) {
          this.$toast.warning('Bạn không có quyền thực hiện chức năng này. Liên hệ với admin thêm Quyền cập nhật ngày ký nhiều chứng từ !')
          return
        }
        if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)) {
          this.$toast.warning('Bạn phải có quyền xét duyệt chứng từ mới có thể thực hiện')
          return
        }
        let dt_ctu = this.gridChungTu.list
        if (dt_ctu == null || dt_ctu.length == 0) {
          this.$toast.warning('Không có dữ liệu chứng từ')
          return
        }
        let t = this.gridChungTu.selected
        if (t.length == 0) {
          this.$toast.warning('Bạn chưa chọn chứng từ cần ký')
          return
        }
        let ketqua = ''
        let dem = 0
        for (let dr of t) {
          let result = await this.callApiWrapper(api.xu_ly_cap_nhat_ngay_ky, {
            chungtu_id: dr.CHUNGTU_ID,
            ngay_ky: getDateStringOrDefault(this.dtpNgayKy.value)
          })
          if (result == 'OK') dem++
        }
        ketqua += '\r\nCập nhật ngày ký thành công: ' + dem + '/' + t.length
        this.$toast.success(ketqua)
        await this.Load_ds_chungtu()
      }
      // }
      // catch (ex) {
      //   this.$toast.warning("Cập nhật ngày ký thất bại " + ex);
      // }
    },
    /**
     * @see: private void btnTiepNhanPhieu_Click(object sender, EventArgs e)
     */
    async btnTiepNhanPhieu_Click() {
      try {
        if (this.gridChungTu.list.length == 0) {
          this.$toast.warning('không có dữ liệu chứng từ')
          return
        }
        if (this.vchungtu_id <= 0) {
          this.$toast.warning('Chứng từ không tồn tại !')
          return
        }
        if (this.gridChungTu.value == null) {
          this.$toast.warning('H„y click vào dÚng chứng từ !')
          return
        }
        if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYET_XETDUYET)) {
          this.$toast.warning('Bạn phải có quyền xét duyệt chứng từ mới có thể thực hiện')
          return
        }
        let xttph = await this.callApiWrapper(api.trang_thai_phieu_v2, { chungtu_id: this.vchungtu_id })
        if (xttph == null || xttph.length == 0) {
          this.$toast.warning('không lấy được thÙng tin trạng thái phiếu')
          return
        }
        if (xttph.length > 0) {
          if (xttph[0].TTPH_ID == '4') {
            this.$toast.warning('Bạn không được tiếp nhận khi chứng từ Đã hoàn thành')
            return
          }
          if (xttph[0].NGAY_XD != '') {
            this.$toast.warning('Chứng từ ' + this.soph + ' Đã có ngày ký! không thế tiếp nhận chứng từ !')
            return
          }
          if (xttph[0].NGUOI_TIEPNHAN != '') {
            if (xttph[0].NGUOI_TIEPNHAN != (await this.$root.token.getUserName())) {
              this.$toast.warning('Chứng từ ' + soph + ' Đã được tiếp nhận bởi user ' + xttph[0].NGUOI_TIEPNHAN + ' ! Bạn không thế tác động vào chứng từ !')
              return
            }
          }
        }
        /* D˘ng tạm trường sophieu_in để lưu người tiếp nhận
           - Nếu có thay đổi chỗ lứu thÏ phải sửa đoạn này
         *  + sửa trạng thái hiển thị chứng từ [... theoloai]
            + sửa dÚng update gần cuối h‡m hoanthien_chungtu, line ~~ 520 ~ 600*/
        let z = await this.callApiWrapper(api.cap_nhat_so_phieu_in, { chungtu_id: this.vchungtu_id, so_phieu_in: await this.$root.token.getUserName() })
        let a = z
        if (z) {
          let ki = cboLoaiChungTu.value == '2' ? 1 : 2
          let kiemtra_ct = await this.kiem_tra_xac_nhan_ct({
            kieu: ki,
            chungTuId: this.vchungtu_id,
            ngayXuat: '',
            ngayNhan: '',
            nguoiCn: await this.$root.token.getUserName()
          })
          let vchungtu_tmp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
          // gridChungTu.ClearColumnsFilter();
          // gridChungTu.FocusedRowHandle = GridControl.AutoFilterRowHandle;
          // ChucNang.FocusGridViewRow(gridChungTu, "CHUNGTU_ID", vchungtu_tmp_id);
          this.$toast.success('Tiếp nhận chứng từ thành công! Bạn có thể sửa tài khoản đối ứng !' + '\nĐể hoàn thành tiếp nhận, hãy cập nhật ngày ký !')
        } else {
          this.$toast.warning(`Tiếp nhận chứng từ thất bại: ${z}`)
        }
      } catch (ex) {
        this.$toast.warning(`Tiếp nhận chứng từ có lỗi: ${ex}`)
      }
    },
    async tsbtnInXuatDVTC_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'XDVTC'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },

    DS_VT_THUHOI_CheckBox: function (dataItem) {
      this.DS_VT_THUHOI_Checked = dataItem
    },
    DieuChinhVT_CheckBox: function (dataItem) {
      this.DSDieuChinhVT_Checked = dataItem
    },
    async btnCapNhatVattu_ThuHoi_Click() {
      console.log('nxv2 btnCapNhatVattu_ThuHoi_Click')
      // try {
      //   if (this.cboNghiepVu.value == null) {
      //     return
      //   }
      //   if (this.cboNghiepVu.value == '286' && kieugoi_id == 2) {
      //     if (this.tsbtnNhapMoi.enabled) {
      //       this.$toast.warning('Bạn chưa nhập mới không thể chọn mã csht =!')
      //       return
      //     }
      //     if (this.gridDS_VT_THUHOI.list == null) {
      //       this.$toast.warning('Không có dữ liệu vật tư tài sản!')
      //       // v_btnRef.Focus();
      //       return
      //     }
      //     if (this.gridDS_VT_THUHOI.list.length == 0) {
      //       this.$toast.warning('Không tìm thấy vật tư tài sản!')
      //       return
      //     }

      //     if (!KiemTraDuLieu(true, false, 'THUHOITS')) return

      //     // gridviewDS_VT_THUHOI.CloseEditor();
      //     // let ischeck = gridDS_VT_THUHOI.list;
      //     // ischeck.AcceptChanges();
      //     // let group_ =  ischeck.Select("ISCHECK = 's1'");
      //     let group_ = this.$refs.gridDS_VT_THUHOI.getSelectedRecords()

      //     if (group_.length == 0) {
      //       this.$toast.warning('Bạn chưa chọn dÚng vật tư n‡o !')
      //       return
      //     }

      //     if (!confirm(`Ban có chắc chắn muốn chọn ${group_.length} vật tư của phiếu ${cboMa_PDA.value} không?`)) return

      //     let loc_dachon = group_.map((e) => e)
      //     let js_ = JSON.stringify(loc_dachon)
      //     let loc_csht = await this.kiemTraLocCSHT({
      //       data: loc_dachon,
      //       khoId: this.cboDenKho.value
      //     })
      //     if (loc_csht.length > 0) {
      //       if (!confirm(`Kho ${cboDenKho.value} đang có mã csht khác ${loc_csht[0]}. Bạn có muốn tiếp tục không?`)) return
      //     }

      //     if (kieugoi_id == 2) {
      //       let t_ctct = []
      //       let t_ctbi = []
      //       for (let dr of loc_dachon) {
      //         if (dr.SERIAL != '') {
      //           let z = t_ctct.Where((l) => l.MA_VT == dr.MA_VT && l.DONGIA == dr.DONGIA).FirstOrDefault()
      //           if (z != null) {
      //             z.SL_NHAP = z.SL_NHAP + Convert.ToDecimal(dr.SOLUONG)
      //           } else {
      //             t_ctct.push({
      //               MA_VT: dr.MA_VT,
      //               DONGIA: Convert.ToDecimal(dr.DONGIA),
      //               SL_NHAP: Convert.ToDecimal(dr.SOLUONG),
      //               VMA_PDA: dr.MADUAN
      //             })
      //           }
      //           t_ctbi.push({
      //             MA_VT: dr.MA_VT,
      //             SOLUONG: 1,
      //             ID_CHITIET: dr.IDCHITIET,
      //             SERIAL: dr.SERIAL
      //           })
      //         } else {
      //           t_ctct.push({
      //             MA_VT: dr.MA_VT,
      //             DONGIA: Convert.ToDecimal(dr.DONGIA),
      //             SL_NHAP: Convert.ToDecimal(dr.SOLUONG),
      //             VMA_PDA: dr.MADUAN
      //           })

      //           t_ctbi.push({
      //             MA_VT: dr.MA_VT,
      //             SOLUONG: -1,
      //             ID_CHITIET: dr.IDCHITIET,
      //             SERIAL: ''
      //           })
      //         }
      //       }

      //       let json_ds_vt = JSON.stringify(t_ctct)
      //       let json_ds_tbi = JSON.stringify(t_ctbi)

      //       let ketqua_ = ChungTu.GET_VALUE_FUNC(
      //         '{?DB12}.sinh_chungtu_qlts',
      //         'vkieu',
      //         2,
      //         'vkho_nhan_id',
      //         cboDenKho.value,
      //         'vngay_ct',
      //         dtpNgayDK.DateTime,
      //         'vnd_giao',
      //         txtNoiDung.Text,
      //         'vnghiepvu_id',
      //         cboNghiepVu.value,
      //         'vma_pda',
      //         cboMa_PDA.Text,
      //         'vds_vt',
      //         json_ds_vt,
      //         'vds_chitiet',
      //         json_ds_tbi,
      //         'vmay_cn',
      //         ttnd.may_cn,
      //         'vip_cn',
      //         ttnd.ip,
      //         'vkho_giao_id',
      //         cboTuKho.value,
      //         'vloaict_id',
      //         1,
      //         'vdonvi_giao_id',
      //         ttnd.donvi_id,
      //         'vdonvi_nhan_id',
      //         ttnd.donvi_id,
      //         'vloaiphieu_id',
      //         cboKieuPhieu.value,
      //         'vnguoi_cn',
      //         ttnd.ma_nd,
      //         'vmucdich_id',
      //         cboMucDich.value
      //       )
      //       if (ketqua_.StartsWith('OK')) {
      //         this.$toast.success('Tạo chứng từ thành công')
      //         let vchungtu_tmp_id = Convert.ToInt64(ketqua_.Split('-')[1])
      //         cboTuKho.EditValueChanged -= cboTuKho_EditValueChanged
      //         Load_ds_chungtu()
      //         gridviewChungTu.ClearColumnsFilter()
      //         gridviewChungTu.FocusedRowHandle = GridControl.AutoFilterRowHandle
      //         ChucNang.FocusGridViewRow(gridviewChungTu, 'CHUNGTU_ID', vchungtu_tmp_id)
      //         cboTuKho.EditValueChanged += cboTuKho_EditValueChanged
      //       } else {
      //         this.$toast.error('Tạo chứng từ thất bại ' + ketqua_)
      //       }
      //     }
      //   }
      // } catch (ex) {
      //   this.$toast.warning('Thu hồi tài sản: ' + ex)
      // }
    },
    // async btnCapNhatVattu_ThuHoi_Click() {
    //   try {
    //     if (this.cboNghiepVu.value == null || this.cboNghiepVu.value == '') {
    //       this.$toast.error('Chưa chọn nghiệp vụ')
    //       return
    //     }
    //     if (this.cboNghiepVu.value == '286' && this.kieugoi_id == 2) {
    //       if (this.tsbtnNhapMoi.enabled) {
    //         this.$toast.error('Bạn chưa nhập mới không thể chọn mã csht !')
    //         return
    //       }
    //       if (this.gridDS_VT_THUHOI.length == 0) {
    //         this.$toast.error('Không có dữ liệu vật tư tài sản !')
    //         return
    //       }
    //       if (this.DS_VT_THUHOI_Checked.length == 0) {
    //         this.$toast.error('Bạn chưa chọn dòng vật tư nào !')
    //         return
    //       }
    //       if (!(await this.KiemTraDuLieu(true, false, 'THUHOITS'))) {
    //         return
    //       }
    //       let loc_csht = await this.kiemTraLocCSHT({
    //         data: this.DS_VT_THUHOI_Checked,
    //         khoId: this.cboDenKho.value
    //       })
    //       if (loc_csht.length > 0) {
    //         if (!confirm(`Kho ${this.cboDenKho.value} đang có mã csht khác ${loc_csht[0].MA_CSHT}. Bạn có muốn tiếp tục không? `)) return
    //       }
    //       let t_ctct = []
    //       let t_ctbi = []
    //       for (let dr of this.DS_VT_THUHOI_Checked) {
    //         if (dr.SERIAL != '') {
    //           let z = t_ctct.filter((l) => l.MA_VT == dr.MA_VT && l.DONGIA == dr.DONGIA)
    //           if (z.length > 0) {
    //             z[0].SL_NHAP = parseInt(z[0].SL_NHAP) + parseInt(dr.SOLUONG)
    //           } else {
    //             t_ctct.push({
    //               MA_VT: dr.MA_VT,
    //               DONGIA: parseInt(dr.DONGIA),
    //               SL_NHAP: parseInt(dr.SOLUONG),
    //               VMA_PDA: dr.MADUAN
    //             })
    //           }
    //           t_ctbi.push({
    //             MA_VT: dr.MA_VT,
    //             SOLUONG: -1,
    //             ID_CHITIET: dr.IDCHITIET,
    //             SERIAL: dr.SERIAL
    //           })
    //         } else {
    //           t_ctct.push({
    //             MA_VT: dr.MA_VT,
    //             DONGIA: parseInt(dr.DONGIA),
    //             SL_NHAP: parseInt(dr.SOLUONG),
    //             VMA_PDA: dr.MADUAN
    //           })
    //           t_ctbi.push({
    //             MA_VT: dr.MA_VT,
    //             SOLUONG: -1,
    //             ID_CHITIET: dr.IDCHITIET,
    //             SERIAL: ''
    //           })
    //         }
    //       }
    //       let _ketqua = await this.SinhChungTuIMSBienBan({
    //         kieu: 2,
    //         khoNhanId: this.cboDenKho.value,
    //         ngayCt: getDateStringOrDefault(this.dtpNgayDK.value, 'DD/MM/YYYY hh:mm:ss'),
    //         ndGiao: this.txtNoiDung.value,
    //         nghiepVuId: this.cboNghiepVu.value,
    //         maPda: row.ProjectCode,
    //         maHd: null,
    //         maPo: null,
    //         bbBg: null,
    //         dsVt: ketqua_,
    //         dsChiTiet: [{}],
    //         nguoiCn: await this.$root.token.getUserName(),
    //         mayCn: await this.$root.token.getMachine(),
    //         ipCn: await this.$root.token.getIP(),
    //         khoGiaoId: this.cboTuKho.value,
    //         loaiCtId: 1,
    //         donViGiaoId: this.$root.token.getDonViID(),
    //         donViNhanId: this.$root.token.getDonViID(),
    //         loaiPhieuId: this.cboKieuPhieu.value,
    //         mucDichId: this.cboMucDich.value
    //       })
    //       if (_ketqua.startsWith('OK')) {
    //         this.$toast.success('Tạo chứng từ thành công')
    //         let vchungtu_tmp_id = parseInt(_ketqua.Split('-')[1])
    //         await this.Load_ds_chungtu()
    //       } else {
    //         this.$toast.error(`Tạo chứng từ thất bại: ${_ketqua}`)
    //       }
    //     }
    //   } catch (e) {
    //     this.$toast.error(e.data.message)
    //   }
    // },
    /**
     * @see: private void cboMa_PDA_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async cboMa_PDA_ButtonClick(buttonTag) {
      if (buttonTag != null && buttonTag == '1') {
        //TODO reset spda
        // this.cboMa_PDA.value = null
        // this.spda = {}
      }
    },
    async cboMa_PDA_DoubleClick() {
      if (this.cboNghiepVu.value == null || this.cboNghiepVu.value == 0) {
        this.$toast.warning('Chưa chọn nghiệp vụ!')
        return
      }
      if (!this.OpenCboMa_PDA) {
        this.$toast.warning('Nghiệp vụ này không có mã dự án!')
        return
      }
      this.spda.MA_PDA = this.gridChungTu.value ? this.gridChungTu.value.MA_PDA : null
      this.frmMa_PDA.input = {
        vnghiepvu_id: this.cboNghiepVu.value,
        kieu_phieu_id: this.cboKieuPhieu.value,
        mucdich_id: this.cboMucDich.value,
        tu_kho_id: this.cboTuKho.value,
        den_kho_id: this.cboDenKho.value,
        ma_pda: this.cboMa_PDA.value,
        kieugoi_id: this.kieugoi_id,
        vinput_spda: this.spda,
        vma_md: this.vma_md
      }
      this.ejsDialog.name = 'frmMa_PDA'
      this.ejsDialog.header = 'Dự án'
      this.$refs.ejsDialog.show()
    },
    /**
     * @see: private void tsbtnInTemSR_Click(object sender, EventArgs e)
     */
    tsbtnInTemSR_Click() {
      if (!this.gridThietBi.list.length) {
        this.$toast.error('Không có dòng thiết bị nào để in !')
        return
      }
      let dz = this.gridVatTu.list
      let loai_serial = dz.filter((a) => a.LOAITBI_ID == '2')
      if (loai_serial.length == 0) {
        this.$toast.error('Không có thiết bị là loại có serial !')
        return
      }
      let f = this.$refs.frmQLTS
      f.id_chungtu = this.vchungtu_id
      f.Tag = '3'
      f.openQLTS()
    },
    async tsbtnInPhieuNKKT_Click() {
      if (this.vchungtu_id == 0 || this.vchungtu_id == null) {
        this.$toast.error('Không có dữ liệu in phiếu')
        return
      }
      this.loading(true)
      await this.axios({
        url: `${process.env.API}/web-qlvt/api/bao-cao/bao-cao-nhap-kho?extend=pdf`,
        method: 'POST',
        responseType: 'blob',
        data: {
          chungTuId: this.vchungtu_id,
          nhanVien: '',
          donVi: '',
          maBc: 'PNKKT'
        }
      })
        .then(async (response) => {
          previewPrint(response.data)
          await this.Update_SoLanIn()
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async editGridChungTu(arg) {},
    async editGridThietBi(arg) {
      let gridThietBi = this.$refs.gridThietBi
      let changes = gridThietBi.getBatchChanges()
      if (changes.changedRecords.length > 0) {
        if (arg.columnName == 'SOLUONG' && changes.changedRecords[0].SOLUONG < 0) {
          this.$toast.error('Đơn giá không được âm!')
          this.$refs.gridThietBi.grid.refreshColumns()
          return
        } else if (arg.columnName == 'GHICHU') {
          await this.callApiWrapper(api.capnhat_gridviewthietbi, {
            p_phanvung_id: null,
            p_chungtu_id: changes.changedRecords[0].CHUNGTU_ID,
            p_fieldname: 'GHICHU',
            p_nguoidung_id: await this.$root.token.getNguoiDungID(),
            p_may_cn: await this.$root.token.getMachine(),
            p_ip_cn: await this.$root.token.getIP(),
            p_ctct_id: changes.changedRecords[0].CTCT_ID,
            p_thietbi_id: changes.changedRecords[0].THIETBI_ID,
            p_e_value: changes.changedRecords[0].GHICHU,
            p_tsbtnnhapmoi: this.tsbtnNhapMoi.enabled ? 1 : 0
          })
        } else if (arg.columnName == 'MATHUNG') {
          await this.callApiWrapper(api.capnhat_gridviewthietbi, {
            p_phanvung_id: null,
            p_chungtu_id: changes.changedRecords[0].CHUNGTU_ID,
            p_fieldname: 'MATHUNG',
            p_nguoidung_id: await this.$root.token.getNguoiDungID(),
            p_may_cn: await this.$root.token.getMachine(),
            p_ip_cn: await this.$root.token.getIP(),
            p_ctct_id: changes.changedRecords[0].CTCT_ID,
            p_thietbi_id: changes.changedRecords[0].THIETBI_ID,
            p_e_value: changes.changedRecords[0].MATHUNG,
            p_tsbtnnhapmoi: this.tsbtnNhapMoi.enabled ? 1 : 0
          })
        }
        gridThietBi.batchUpdate(changes)
        gridThietBi.grid.refreshColumns()
      }
    },
    async btnChon_PDA_ButtonClick(entity) {
      let row = entity
      // try {
      if (this.cboNghiepVu.value == null || this.cboNghiepVu.value == '') {
        this.$toast.error('Chưa chọn nghiệp vụ!')
        return
      }
      if (this.cboNghiepVu.value == '280') {
        if (this.tsbtnNhapMoi.enabled) {
          this.$toast.error('Bạn chưa nhập mới không thể chọn dự án!')
          return
        }
        if (!(await this.KiemTraDuLieu(true, false, 'GHILAI_PDA'))) return
        if (!confirm(`Ban có chắc chắn muốn chọn mã dự án ${row.ProjectCode} không?`)) return
        if (this.kieugoi_id == 2) {
          this.cboMa_PDA.value = row.ProjectCode
          let dt_temp = await this.callApiWrapper(api.ds_assset_by_project, { ma_duan: this.cboMa_PDA.value })

          // không có dữ liệu trả về đúng chuẩn nên nhắm mắt code
          if (dt_temp != null && dt_temp != '') {
            let t_ct = {
              VNGAY_CT: getDateStringOrDefault(this.params.p_dtpNgayDKdtpNgayDK, 'DD/MM/YYYY hh:mm:ss'),
              VND_GIAO: this.txtNoiDung.value,
              VMUCDICH_ID: this.cboMucDich.value,
              VKIEUPHIEU_ID: this.cboKieuPhieu.value
            }
            let to_js = []
            for (const e of dt_temp) {
              to_js.push({
                AssetCodeType: e.AssetCodeType,
                CodeAcceptance: e.CodeAcceptance
              })
            }
            let ketqua_kt = await this.callApiWrapper(api.ds_vat_tu_theo_nvu, {
              kieu: 2,
              ds: to_js,
              maPda: null,
              tenPda: null,
              pdaId: null
            })
            if (ketqua_kt != '1') {
              this.$toast.error(`${ketqua_kt}`)
              return
            }

            let t_ctct = []
            let t_ctbi = []
            for (let dr of dt_temp) {
              if (dr.SerialNumberOffice != '') {
                let z = t_ctct.filter((l) => l.MA_VT == dr.CodeAcceptance && l.DONGIA != dr.PriceForeign)
                if (z.length > 0) {
                  z[0].SL_NHAP = z[0].SL_NHAP + parseInt(dr.AssetNumber)
                } else {
                  t_ctct.push({
                    MA_VT: dr.CodeAcceptance,
                    DONGIA: parseInt(dr.PriceForeign),
                    SL_NHAP: parseInt(dr.AssetNumber),
                    VMA_PDA: row.ProjectCode,
                    VTEN_PDA: row.ProjectName,
                    VPDA_ID: row.ProjectID
                  })
                }

                t_ctbi.push({
                  MA_VT: dr.CodeAcceptance,
                  SOLUONG: 1,
                  ID_CHITIET: dr.AssetCode,
                  SERIAL: dr.SerialNumberOffice,
                  MA_NHOM: row.AssetCodeType
                })
              } else {
                t_ctct.push({
                  MA_VT: dr.CodeAcceptance,
                  DONGIA: parseInt(dr.PriceForeign),
                  SL_NHAP: parseInt(dr.AssetNumber),
                  VMA_PDA: row.ProjectCode,
                  VTEN_PDA: row.ProjectName,
                  VPDA_ID: row.ProjectID
                })

                t_ctbi.push({
                  MA_VT: dr.CodeAcceptance,
                  SOLUONG: -1,
                  ID_CHITIET: dr.AssetCode,
                  SERIAL: '',
                  MA_NHOM: row.AssetCodeType
                })
              }

              let ketqua_ = await this.callApiWrapper(api.sinh_ct_qlts, {
                kieu: 1,
                khoNhanId: this.cboDenKho.value,
                ngayCt: getDateStringOrDefault(this.dtpNgayDK.value, 'DD/MM/YYYY hh:mm:ss'),
                ndGiao: this.txtNoiDung.value,
                nghiepVuId: this.cboNghiepVu.value,
                maPda: row.ProjectCode,
                dsVt: t_ctct,
                dsChiTiet: t_ctbi,
                nguoiCn: await this.$root.token.getUserName(),
                mayCn: '',
                ipCn: '',
                khoGiaoId: this.cboTuKho.value,
                loaiCtId: 1,
                donViGiaoId: this.$root.token.getDonViID(),
                donViNhanId: this.$root.token.getDonViID(),
                loaiPhieuId: this.cboKieuPhieu.value,
                mucDichId: cboMucDich.value
              })
              if (ketqua_.startsWith('OK')) {
                this.$toast.success('Tạo chứng từ thành công')
                let vchungtu_tmp_id = parseInt(ketqua_.Split('-')[1])
                await this.Load_ds_chungtu()
              } else {
                this.$toast.error(`Tạo chứng từ thất bại ${ketqua_}`)
              }
            }
          } else {
            this.$toast.error('Không có dữ liệu tài sản ngắn hạn!')
          }
        } else if ((await this.get_mamd(this.cboKieuPhieu.value)) == '10' && this.cboLoaiChungTu.value == '2') {
          this.cboMa_PDA.value = row.ProjectCode
          let dt_temp = await this.callApiWrapper(api.ds_assset_by_project, { ma_duan: this.cboMa_PDA.value })

          // không có dữ liệu trả về đúng chuẩn nên nhắm mắt code
          if (dt_temp != null && dt_temp != '') {
            let ketqua_ = await this.callApiWrapper(api.kiem_tra_don_gia_ims, {
              kieu: 0,
              data: dt_temp,
              khoId: this.cboTuKho.value
            })
            if (ketqua_.length == 0) {
              this.$toast.error(`${this.cboMa_PDA.value} không có vật tư nào thay đổi đơn giá, không thể tạo chứng từ xuất điều chỉnh đơn giá !`)
              return
            }
            let dtVatTu = await this.callApiWrapper(api.ds_vat_tu_ton_kho, {
              tagForm: 0,
              khoId: this.cboTuKho.value,
              ngayCt: getDateStringOrDefault(this.dtpNgayDK.value),
              chungTuId: 0,
              dsVatTuTon: ketqua_.join(',')
            })
            if (dtVatTu.length == 0) {
              this.$toast.error(`${this.cboMa_PDA.value} không tồn vật tư khác đơn giá ở kho ${this.cboTuKho.value}`)
              return
            }
            let _ketqua = await this.callApiWrapper(api.sinh_ct_ims_bbg, {
              kieu: 2,
              khoNhanId: this.cboDenKho.value,
              ngayCt: getDateStringOrDefault(this.dtpNgayDK.value),
              ndGiao: this.txtNoiDung.value,
              nghiepVuId: this.cboNghiepVu.value,
              maPda: row.ProjectCode,
              maHd: null,
              maPo: null,
              bbBg: null,
              dsVt: ketqua_,
              dsChiTiet: [{}],
              nguoiCn: await this.$root.token.getUserName(),
              mayCn: await this.$root.token.getMachine(),
              ipCn: await this.$root.token.getIP(),
              khoGiaoId: this.cboTuKho.value,
              loaiCtId: 1,
              donViGiaoId: this.$root.token.getDonViID(),
              donViNhanId: this.$root.token.getDonViID(),
              loaiPhieuId: this.cboKieuPhieu.value,
              mucDichId: this.cboMucDich.value
            })
            if (_ketqua.startsWith('OK')) {
              this.$toast.success('Tạo chứng từ thành công')
              let vchungtu_tmp_id = parseInt(_ketqua.Split('-')[1])
              await this.Load_ds_chungtu()
            } else {
              this.$toast.error(`Tạo chứng từ thất bại ${_ketqua}`)
            }
          }
        }
      } else if (this.cboNghiepVu.value == '292' || this.cboNghiepVu.value == '293' || this.cboNghiepVu.value == '294') {
        this.cboMa_PDA.value = row.ProjectCode
        let new_chst = ''
        if (this.cboNghiepVu.value != '294') {
          if (this.batbuoc_chst) {
            let p_TuKho = this.cboTuKho.value == null || this.cboTuKho.value == '' ? 0 : this.cboTuKho.value // tránh truyền null lên url
            let find_csht = await this.callApiWrapper(api.ma_co_so_ha_tang, { kho_id: p_TuKho })
            if (find_csht == null || find_csht.length == 0) {
              this.$toast.error('Từ kho phải là kho có CSHT, hãy kiểm tra lại !')
              return
            }
            new_chst = find_csht
          }
        }
        let dt_hdpo = await this.callApiWrapper(api.contract_info_by_project_code, {
          maDuan: this.cboMa_PDA.value,
          maCsht: new_chst
        })
        if (dt_hdpo != null && dt_hdpo.length) {
          let data = JSON.parse(dt_hdpo)
          if (data.NewDataSet && data.NewDataSet.Table) {
            let temp = []
            if (data.NewDataSet.Table) {
              if (Array.isArray(data.NewDataSet.Table)) temp = data.NewDataSet.Table
              else temp = [data.NewDataSet.Table]
            }
            this.$refs.frmMa_PDA_view.cboHD_XuatIMS.list = temp
          } else {
            this.$toast.info('Không có thông tin hợp đồng - PO')
            this.$refs.frmMa_PDA_view.cboHD_XuatIMS.list = []
          }
        }
        this.$refs.frmMa_PDA_view.cboHD_XuatIMS.list.unshift({
          PO: '',
          ContractName: '',
          SignOffDate: '',
          ContractInfoID: '',
          ParentContractInfo: '',
          ContractNumber: ''
        })
      }
      // } catch (e) {
      //   this.$toast.error(`${e.data.message}`)
      // }
    },
    /**
     * @see: private void btnNhapExDG_Click(object sender, EventArgs e)
     */
    async btnNhapExDG_Click() {
      try {
        if (this.vchungtu_id == 0) {
          this.$toast.warning('Không tìm thấy chứng từ!')
          return
        }
        if (this.ttph_id == 4) {
          this.$toast.warning('Chứng từ đã hoàn thành, không thể dùng chức năng này!')
          return
        }
        this.$refs['ref-exdg-file'].$el.querySelector('input').click()
      } catch (ex) {
        this.$toast.warning(`Lỗi nhập phân bổ: + ${ex}`)
        return
      }
    },
    async btnNhapExDG_OnChange() {
      let fileChooser = this.$refs['ref-exdg-file']
      let choosedFiles = fileChooser.$el.querySelector('input').files
      if (choosedFiles.length == 0) {
        fileChooser.reset()
        return
      }
      choosedFiles = [...choosedFiles]
      console.log(choosedFiles)
      fileChooser.reset()
      let theFile = choosedFiles[0]
      // try {
      // this.loading(true)
      const reader = new FileReader()
      reader.onload = async (e) => {
        /* Parse data */
        const bstr = e.target.result
        const wb = xlsx.read(bstr, { type: 'binary' })
        /* Get first worksheet */
        const wsname = wb.SheetNames[0]
        const ws = wb.Sheets[wsname]
        /* Convert array of arrays */
        const data = xlsx.utils.sheet_to_json(ws, { header: 1 })
        // retrieve headers (i.e. remove first row)
        let headers = data.shift() // bỏ dòng header đầu tiên trong data
        // then build the json for each row
        let result = data.map(function (a) {
          let jsonRow = {}
          a.forEach(function (cellValue, cellIndex) {
            jsonRow[headers[cellIndex]] = cellValue
          })
          headers.forEach((b) => {
            if (!Object.keys(jsonRow).includes(b)) {
              jsonRow[b] = ''
            }
          })
          return jsonRow
        })

        console.log(result, ' = result')
        if (result.length == 0) {
          this.$toast.error('File không có dữ liệu')
          return
        } else {
          let dulieu_doc = result.filter((val, idx) => {
            return idx > 2 && idx != result.length - 1
          })
          if (dulieu_doc.lenght == 0) {
            this.$toast.warning('không có dòng nào, hãy kiểm tra lại dữ liệu excel!')
            return
          }
          let jsss = JSON.stringify(dulieu_doc)
          let kq = await this.callApiWrapper(api.fn_update_tien_ngoaite, {
            p_phanvung_id: null,
            p_data: jsss,
            p_chungtu_id: this.vchungtu_id,
            p_nguoi_cn: await this.$root.token.getUserName(),
            p_may_cn: await this.$root.token.getMachine(),
            p_ip_cn: await this.$root.token.getIP()
          })
          if (kq != '1') {
            this.$toast.warning(kq)
            return
          } else {
            this.$toast.success('Cập nhật đơn giá thành công!')
            await this.Load_ds_vattu()
            await this.Load_ds_thietbi()
          }
        }
      }
      reader.readAsBinaryString(theFile)
      // } catch (error) {
      //   this.$toast.error('Có lỗi xảy ra khi upload file!')
      // } finally {
      //   this.$refs.fileUploadDSVT.reset()
      //   this.loading(false)
      // }

      // let fdg = new OpenFileDialog
      // {
      //     RestoreDirectory = false,
      //     Multiselect = false,
      //     Filter = @"Excel Files(*.XLS;*.XLSX)|*.XLS;*.XLSX",
      //     FilterIndex = 1,
      //     Title = @"File excel"
      // };
      // if (fdg.ShowDialog() != DialogResult.OK) return;

      // let path = fdg.FileNames[0];
      // let exten = Path.GetExtension(path);
      // let dulieu_doc = bangts.DocFile_Excel(exten, path, "PhanBoDonGiaChiTiet", 2, 0, true);

      // if (dulieu_doc.Rows.Count == 0)
      // {
      //     this.$toast.warning("không có dÚng n‡o, h„y kiểm tra lại dữ liệu excel !");
      //     return;
      // }
      // let jsss = JsonConvert.SerializeObject(dulieu_doc);
      // let kq = ChungTu.GET_VALUE_FUNC("{?DB12}.update_tien_ngoaite", "vdata", jsss, "vchungtu_id", vchungtu_id,
      //     "vnguoi_cn", ttnd.ma_nd, "vmay_cn", ttnd.may_cn, "vip_cn", ttnd.ip);
      // let kq = await this.callApiWrapper(api.fn_update_tien_ngoaite, {
      //   p_phanvung_id: null,
      //   p_data: '',
      //   p_chungtu_id: 1,
      //   p_nguoi_cn: '',
      //   p_may_cn: '',
      //   p_ip_cn: ''
      // })

      // if (kq != "1")
      // {
      //     this.$toast.warning(kq);
      //     return;
      // }
      // else
      // {
      //     Message_Box.ShowTB("Cập nhật đơn giá thành công !");
      //     Load_ds_vattu();
      //     Load_ds_thietbi();
      // }
    },
    btnMauDG_Click() {
      const downloadLink = document.createElement('a')
      downloadLink.href = 'http://10.70.52.164:8081/FILES/NET/VATTU/FN FILE ÁP GIÁ CHUYEN_KHONGXOA.xls'
      downloadLink.download = 'FN FILE ÁP GIÁ CHUYEN_KHONGXOA.xls'
      downloadLink.click()
    },
    /**
     * @see: private int chuyendoi_loaict_loaiphieu_denghi()
     */
    async chuyendoi_loaict_loaiphieu_denghi() {
      let vloaiphieu_id = 0

      let kq = await api.fn_chuyen_loaict_loaiphieu(this.axios, {
        p_phanvung_id: null,
        p_loaict_id: this.cboLoaiChungTu.value,
        p_mucdich_id: this.cboMucDich.value
      })
      if (kq.data.error_code == 'BSS-00000000') {
        vloaiphieu_id = kq.data.data
      } else {
      }
      // switch (this.cboLoaiChungTu.value) {
      //   case 1:
      //     vloaiphieu_id = LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_NHAPKHO_TT
      //     break
      //   case 2:
      //     vloaiphieu_id = LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_XUATKHO
      //     break
      //   case 3:
      //     vloaiphieu_id = LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_CHUYENKHO
      //     break
      //   case 99:
      //     vloaiphieu_id = LOAIPHIEU_DENGHI_VATTU.PHIEU_DENGHI_MUALE
      //     break
      //   default:
      //     break
      // }
      return vloaiphieu_id
    },
    /**
     * @see: private void TAO_PHIEU_DENGHI()
     */
    async TAO_PHIEU_DENGHI() {
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn phải chọn chứng từ!')
        return
      }
      if (!this.KiemTraNguoiCN()) {
        this.$toast.warning('Chỉ có người tạo phiếu mới có thể lập đề nghị')
        return
      }

      let kq = await this.callApiWrapper(api.sp_lay_ds_loaict_loaiphieu, {
        p_phanvung_id: this.$root.token.getPhanVungID(),
        p_loaict_id: this.cboLoaiChungTu.value,
        p_kieuphieu: this.cboKieuPhieu.value,
        p_mucdich_id: this.cboMucDich.value
      })
      if (kq && kq.length > 1) {
        this.frmChonDeNghi.input = {
          vdsLoaiPhieu: kq
        }
        this.ejsDialog.name = 'frmChonDeNghi'
        this.ejsDialog.header = 'Chọn đề nghị'
        this.$refs.ejsDialog.show()
      } else {
        if (!confirm(`Bạn có muốn tạo phiếu đề nghị không?`)) return
        await this.frmChonDeNghi_tsbtnXacNhan_Clicked({ loaiPhieuId: kq[0].LOAIPHIEU_ID })
      }
      return
    },
    async frmChonDeNghi_tsbtnXacNhan_Clicked(data) {
      console.log('frmChonDeNghi_tsbtnXacNhan_Clicked')
      console.log(data)
      this.$refs.ejsDialog.hide()
      let kq = await this.callApiWrapper(api.cap_nhat_de_nghi_ct, {
        noiDungDeNghi: this.txtNoiDung.value,
        khoGiaoId: this.cboTuKho.value,
        khoNhanId: this.cboDenKho.value,
        ttDnId: 1,
        ghiChu: this.txtNoiDung.value,
        loaiPhieuId: data.loaiPhieuId,
        dsDeNghiCt: [{}],
        chungTuId: this.vchungtu_id
      })
      if (kq != null && kq.length > 0 && kq[0].result == 'OK') {
        this.txtPhieuDeNghi.value = kq[0].maDn
        let deNghiId = kq[0].deNghiId
        let chungTu = this.gridChungTu.list.find((e) => e.CHUNGTU_ID == this.vchungtu_id)
        if (chungTu != null) {
          chungTu.DENGHI_ID = deNghiId
          chungTu.MA_DN = this.txtPhieuDeNghi.value
        }
        this.$toast.success('Tạo đề nghị thành công')
      } else {
        this.$toast.warning(`${kq[0].result}`)
      }
    },
    /**
     * @see: private void XOA_PHIEU_DENGHI()
     */
    async XOA_PHIEU_DENGHI() {
      if (this.vchungtu_id == 0) {
        this.$toast.warning('Bạn phải chọn chứng từ để xóa')
        return
      }
      if (!this.dsQuyen.includes(DS_QUYEN_VT.QUYEN_XOA_KYSO) && !this.KiemTraNguoiCN()) {
        this.$toast.warning('Chỉ có người tạo phiếu mới có thể xóa phiếu đề nghị')
        return
      }
      // if (denghi_luoi_id <= 0) {
      //   this.$toast.warning("không có đề nghị không thể xóa");
      //   return;
      // }
      try {
        if (!confirm(`Bạn có muốn xóa dữ liệu phiếu đề nghị không?`)) return
        let data = {
          deNghiId: this.gridChungTu.value != null ? this.gridChungTu.value.DENGHI_ID : -1,
          chungTuId: this.vchungtu_id
        }
        let result = await this.callApiWrapper(api.xoa_phieu_de_nghi, data)
        if (result == 'OK') {
          this.$toast.success('Xóa dữ liệu đề nghị thành công !')
          this.txtPhieuDeNghi.value = ''
          for (const i in this.gridChungTu.list) {
            if (this.gridChungTu.list[i].CHUNGTU_ID == this.vchungtu_id) {
              this.gridChungTu.list[i].DENGHI_ID = -1
              this.gridChungTu.list[i].MA_DN = ''
            }
          }
        } else {
          this.$toast.warning('Thất bại!')
        }
      } catch (ex) {
        this.$toast.error(`Có lỗi trong quá trình xóa ${ex}`)
      }
    },
    openSaveNoiDung() {
      this.$bvModal.show('popupSaveNoiDung')
    },
    async onClickGhiLaiNoiDung() {
      this.$bvModal.hide('popupSaveNoiDung')
      if (this.tsbtnNhapMoi.enabled && this.dsQuyen.includes(2514)) {
        let sua_nd = await this.CapNhatNoiDungGiaoSuaCT({
          chungTuId: this.vchungtu_id,
          ndGiao: this.txtNoiDung.value
        })
        this.$toast.success('Sửa nội dung chứng từ thành công !')
      }
    },
    chooseFile() {
      if (this.tsbtnNhapMoi.enabled == false) {
        this.$toast.error('Bạn phải ghi lại chứng từ trước !')
        return
      }
      if (this.vchungtu_id == 0) {
        this.$toast.error('Không tìm thấy chứng từ !')
        return
      }
      if (this.ttph_id == 4) {
        this.$toast.error('Chứng từ đã hoàn thiện ! Không thể thêm file VB !')
        return
      }
      this.$refs['ref-choose-file'].$el.querySelector('input').click()
    },
    async onChangeFile() {
      console.log('onChangeFile')
      let fileChooser = this.$refs['ref-choose-file']
      let choosedFiles = fileChooser.$el.querySelector('input').files
      if (choosedFiles.length == 0) {
        fileChooser.reset()
        return
      }
      choosedFiles = [...choosedFiles]
      console.log(choosedFiles)
      fileChooser.reset()
      //check file doan nay
      let extensionSupported = ['.docx', '.doc', '.pdf']
      let fileNames = choosedFiles.map((e) => e.name.substring(e.name.lastIndexOf('.'), e.name.length))
      console.log('fileNames')
      console.log(fileNames)
      let ktFile = fileNames.filter((e) => extensionSupported.includes(e)).length == fileNames.length
      console.log('ktFile')
      console.log(ktFile)
      if (!ktFile) {
        this.$toast.warning(`Chương trình chỉ hỗ trợ các file: ${extensionSupported.join(', ')}`)
        return
      }
      let formData = new FormData()
      for (let i = 0; i < choosedFiles.length; i++) {
        let item = choosedFiles[i]
        formData.append('file', item)
      }
      let paths = await this.callApiWrapper(api.uploadFile, formData)

      if (paths.length == 0 || paths == 'error') {
        // this.$toast.error('Upload file thất bại!')
        return
      }
      for (let i = 0; i < paths.length; i++) {
        if (paths[i].status) {
          let urlSplit = paths[i].message.relativeUrl.split('/')
          this.grcFileCV.list.push({
            linkFile: paths[i].message.relativeUrl,
            nameFile: urlSplit[urlSplit.length - 1]
          })
        }
      }
      let listFile = []
      this.grcFileCV.list.forEach((e) => {
        listFile.push(e.linkFile)
      })
      let DSMucDich = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.gridChungTu.value.MUCDICH_ID })
      let ketQua = await this.callApiWrapper(api.cap_nhat_ct_van_ban, {
        loaiChungTu: this.cboLoaiChungTu.value,
        mdSpecial: DSMucDich[0],
        chungTuId: this.vchungtu_id,
        result: listFile.join('|'),
        nguoiCn: '',
        mayCn: '',
        ipCn: ''
      })
      if (ketQua == '1') {
        this.$toast.success('Upload file thành công!')
        await this.Load_ds_chungtu()
      } else {
        this.$toast.error('Upload file không thành công!')
      }
    },

    async downloadFile(item) {
      console.log('downloadFile')
      console.log(item)
      let checkFile = await api.checkFileExist(this.axios, { fileSource: item.linkFile })
      // await this.callApiWrapper(api.checkFileExist, { fileSource: item.linkFile })
      console.log('api.checkFileExist')
      console.log(checkFile)
      let linkFile = ''
      if (checkFile.data.message == 'Success' && checkFile.data.data) {
        let getLink = await this.callApiWrapper(api.getPresignedUrl, { fileSource: item.linkFile })
        linkFile = getLink.name
        console.log('api.getPresignedUrl')
        console.log(getLink)
      } else {
        linkFile = this.URL + item.linkFile
      }
      console.log(linkFile)
      linkFile = await this.xuLyLayLinkFile(linkFile)
      const downloadLink = document.createElement('a')
      downloadLink.href = linkFile
      const filePath = item.linkFile.split('/')
      const fileName = filePath[filePath.length - 1]
      // const linkSource = `data:application/octet-stream;base64,${data.body.data}`
      downloadLink.download = fileName
      downloadLink.target = '_blank'
      // downloadLink.href = linkSource
      downloadLink.click()
      console.log('linkFile')
      console.log(linkFile)
    },
    // async onDownloadFileHandler(item) {
    //   console.log(item)
    //   console.log(item)
    //   let checkFile = await this.callApiWrapper(api.checkFileExist, { fileSource: item.linkFile })
    //   console.log('7400 - checkFile')
    //   console.log(checkFile)
    //   if (checkFile.message == 'Success') {
    //     if (!checkFile.data.data) {
    //       this.$toast.error('File không còn tồn tại!')
    //       return
    //     }
    //   }
    //   await this.onClickDownFile(item.linkFile)
    // },
    async onClickDownFile(value) {
      console.log('onClickDownFile')
      console.log(value)
      if (value) {
        const data = await this.callApiWrapper(api.downloadFile, value, { showError: false })
        if (data) {
          console.log('data')
          console.log(data.body)
          if (!data.body) {
            this.$toast.warning('File có lỗi hoặc không tồn tại!')
            return
          }
          const filePath = value.split('/')
          const linkSource = `data:application/octet-stream;base64,${data.body.data}`
          const downloadLink = document.createElement('a')
          const fileName = filePath[filePath.length - 1]
          downloadLink.href = linkSource
          downloadLink.download = fileName
          downloadLink.click()
        }
      }
    },
    async onClickDeleteFile(item) {
      console.log('onClickDeleteFile')
      console.log(item)
      if (item) {
        let deleteFile = await this.callApiWrapper(api.deleteFile, { fileSource: item.linkFile })
        console.log(deleteFile)
        if (!deleteFile.status) {
          this.$toast.error(`${deleteFile.message}`)
          return
        }
        let listFile = []
        this.grcFileCV.list.forEach((e) => {
          if (item.linkFile != e.linkFile) {
            listFile.push(e.linkFile)
          }
        })
        let ketQua = await this.callApiWrapper(api.cap_nhat_chung_tu_vb, {
          fileVb: listFile.join('|'),
          chungTuId: this.vchungtu_id
        })
        if (ketQua == '1') {
          this.$toast.success('Xóa file thành công')
          await this.Load_ds_chungtu()
        } else {
          this.$toast.error('Có lỗi xảy ra, xóa file không thành công!')
        }
      }
    },
    async onClickViewFile(item) {
      console.log('onClickViewFile')
      console.log(item)
      if (item) {
        // let checkFile = await this.callApiWrapper(api.checkFileExist, { fileSource: item.linkFile })
        // console.log('api.checkFileExist')
        // console.log(checkFile)
        // if (!checkFile.data) {
        //   this.$toast.error('File không còn tồn tại!')
        //   return
        // }
        let kq = await this.callApiWrapper(api.getPresignedUrl, { fileSource: item.linkFile })
        console.log('api.getPresignedUrl')
        console.log(kq)
        let linkFile = kq?.name
        if (linkFile) {
          linkFile = await this.xuLyLayLinkFile(linkFile)
          // this.axios.get(`${kq?.name}`).then((res) => {
          //   console.log('kq?.name')
          //   console.log(res)
          // })
          console.log('outputLinkFile')
          console.log(linkFile)
          const downloadLink = document.createElement('a')
          downloadLink.href = decodeURI(linkFile)
          // 'http://10.70.52.164:8081/FILES/HCM/VATTU/230720-CV-3285-DAKTXD.pdf'
          const filePath = linkFile.split('/')
          const fileName = filePath[filePath.length - 1]
          downloadLink.download = fileName
          downloadLink.target = '_blank'
          downloadLink.click()
        }
      }
    },
    async xuLyLayLinkFile(linkFile) {
      console.log('xuLyLayLinkFile')
      console.log(linkFile)
      let outputLinkFile = linkFile
      if (linkFile.includes('WEB-QLVT')) {
      } else {
        outputLinkFile = linkFile.substring(linkFile.indexOf('http', 2), linkFile.indexOf('?')).replace(linkFile.substring(linkFile.indexOf('http', 2), linkFile.indexOf('FILES')), 'http://10.70.52.164:8081/')
      }
      return outputLinkFile
    },
    /**
     * @see: private void tsbtnDongBoQLTS_Click(object sender, EventArgs e)
     */
    async tsbtnDongBoQLTS_Click() {
      if (this.gridChungTu.value == null || this.vchungtu_id == 0) {
        this.$toast.warning('Chưa chọn chứng từ không thể đồng bộ')
        return
      }
      // let f = new frmDongBo_CCDC();
      // f.chungtu_id = vchungtu_id;
      // f.sophieu = txtSoPhieu.Text;
      // f.ngayxuat = dtpNgayDK.DateTime;
      // if (gridChungTu.GetFocusedRowCellValue("CCDC_QLTS_ID") != null &&
      //     gridChungTu.GetFocusedRowCellValue("CCDC_QLTS_ID") != DBNull.Value &&
      //     gridChungTu.GetFocusedRowCellValue("CCDC_QLTS_ID") != "")
      // {
      //     f.ct_dongbo = false;
      // }
      // else
      // {
      //     f.ct_dongbo = true;
      // }
      // f.ShowDialog();
      // try
      // {
      //     if (f.dongbo_qlts)
      //     {
      //         gridChungTu.SetRowCellValue(gridChungTu.FocusedRowHandle, "CCDC_QLTS_ID", f.p_ccdc_qlts_id);
      //         gridChungTu.SetRowCellValue(gridChungTu.FocusedRowHandle, "CCDC_QLTS", "Đã đồng bộ");
      //     }
      // }
      // catch
      // {
      // }
    },
    /**
     * @see: private void btnCapNhatDieuChinh_Click(object sender, EventArgs e)
     */
    async btnCapNhatDieuChinh_Click() {
      if (this.cboLocDCDonVi.value == '0') {
        this.$toast.warning('Bạn chưa chọn điều kiện lọc đơn vị !')
        return
      }
      if (this.cboLocDCLoaiCT.value == null) {
        this.$toast.warning('Bạn chưa lọc dữ liệu vật tư điều chuyển !')
        return
      }
      if (this.cboKieuPhieu.value == null) {
        this.$toast.warning('Bạn chưa chọn kiểu phiếu !')
        return
      }
      if (this.cboMucDich.value == null) {
        this.$toast.warning('Bạn chưa chọn mục đích !')
        return
      }
      if (this.cboTuKho.value == null) {
        this.$toast.warning('Bạn chưa chọn từ kho !')
        return
      }
      if (this.cboDenKho.value == null) {
        this.$toast.warning('Bạn chưa chọn đến kho !')
        return
      }
      if (this.cboTuKho.value == this.cboDenKho.value) {
        this.$toast.warning('Kho từ không được giống kho đến !')
        return
      }
      let locdv = this.cboLocDCDonVi.value
      let loc_ct = this.cboLocDCLoaiCT.value
      let c_kt_tukho = await this.callApiWrapper(api.kiem_tra_ton_kho_den_kho, {
        kho_id: this.cboTuKho.value,
        kieu: 0
      })

      let c_kt_denkho = await this.callApiWrapper(api.kiem_tra_ton_kho_den_kho, {
        kho_id: this.cboDenKho.value,
        kieu: 0
      })
      // let c_kt_tukho = ChungTu.GET_DATA_SQL("select 1 from {?DB12}.kho where kho_id = :vid and tonkho = 0", "vid",
      //         Convert.ToInt32(cboTuKho.value));
      // let c_kt_denkho = ChungTu.GET_DATA_SQL("select 1 from {?DB12}.kho where kho_id = :vid and tonkho = 1", "vid",
      //         Convert.ToInt32(cboDenKho.value));
      if (loc_ct == '1' || loc_ct == '2' || loc_ct == '3') {
        if (c_kt_tukho.lenght > 0) {
          this.$toast.warning('Bạn phải chọn kho từ là kho có tồn !')
          return
        }
        if (c_kt_denkho.lenght == 0) {
          this.$toast.warning('Bạn phải chọn kho đến là kho có tồn !')
          return
        }
      }
      if (loc_ct == '99') {
        if (c_kt_tukho.lenght == 0) {
          this.$toast.warning('Nhập lẻ, bạn phải chọn kho từ là kho không có tồn !')
          return
        }
        if (c_kt_denkho.lenght == 0) {
          this.$toast.warning('Nhập lẻ, bạn phải chọn kho đến là kho có tồn !')
          return
        }
      }
      let luoi = this.gridDieuChinhVT.list
      if (luoi == null) {
        this.$toast.warning('không có dữ liệu vật tư !')
        return
      }
      if (luoi.length == 0) {
        this.$toast.warning('không có dÚng dữ liệu vật tư n‡o !')
        return
      }
      if (this.gridDieuChinhVT.selected == 0) {
        this.$toast.warning('Bạn chưa tÌch chọn dÚng vật tư n‡o !')
        return
        let vattu_ok = []
        if (loc_ct == '2' || loc_ct == '3' || loc_ct == '1') {
          let __js = this.gridDieuChinhVT.selected
          let k___ = await this.callApiWrapper(api.kiem_tra_don_gia_ims, {
            kieu: 1,
            data: this.DSDieuChinhVT_Checked,
            khoId: this.cboTuKho.value
          })
          if (k___.length == 0) {
            this.$toast.warning('Vật tư Đã chọn không có tồn trÍn ĐHSX !')
            return
          }
          let ds_vattu_ton = []
          for (let e of k___) {
            ds_vattu_ton.push(e.VATTU_ID)
          }
          let dt_ton_kho = await this.callApiWrapper(api.ds_vat_tu_ton_kho, {
            tagForm: 0,
            khoId: this.cboTuKho.value,
            ngayCt: getDateStringOrDefault(this.dtpNgayDK.value),
            chungTuId: 0,
            dsVatTuTon: ds_vattu_ton.join(',')
          })
          if (dt_ton_kho.length == 0) {
            this.$toast.warning(`${cboMa_PDA.Text} không tồn vật tư ở kho ${cboTuKho.Text}`)
            return
          }
          vattu_ok = k___
        } else {
          vattu_ok = check_dong
        }
        let js_ = check_dong
        let loc_csht = await this.callApiWrapper(api.kiem_tra_loc_csht, {
          data: this.DSDieuChinhVT_Checked,
          khoId: this.cboDenKho.value
        })
        if (!confirm('Kho ' + this.cboDenKho.value + ' đang có mã csht khác ' + loc_csht[0] + '. Bạn có muốn tiếp tục không?')) return
        let ketqua_ = ''
        if (loc_ct == '99') {
          let t_ctct = []
          let t_ctbi = []
          for (let dr of vattu_ok) {
            if (dr.SOLUONG == 0) continue
            if (dr.SERIAL != '') {
              let z = t_ctct.filter((a) => a.MA_VT == dr.MAVTHH && a.DONGIA == dr.DONGIA)
              if (z != null) {
                z.SL_NHAP = z.SL_NHAP + dr.SOLUONG
              } else {
                t_ctct.push({
                  MA_VT: dr.MAVTHH,
                  DONGIA: dr.DONGIA,
                  SL_NHAP: dr.SOLUONG,
                  VMA_PDA: dr.MADUAN
                })
              }
              t_ctbi.push({
                MA_VT: dr.MAVTHH,
                SOLUONG: 1,
                ID_CHITIET: dr.IDCHITIET,
                SERIAL: dr.SERIAL
              })
            } else {
              t_ctct.push({
                MA_VT: dr.MAVTHH,
                DONGIA: dr.DONGIA,
                SL_NHAP: dr.SOLUONG,
                VMA_PDA: dr.MADUAN
              })
              t_ctbi.push({
                MA_VT: dr.MAVTHH,
                SOLUONG: dr.SOLUONG,
                ID_CHITIET: dr.IDCHITIET,
                SERIAL: ''
              })
            }
          }
          let json_ds_vt = t_ctct
          let json_ds_tbi = t_ctbi
          let ketqua_ = await this.callApiWrapper(api.sinh_dieu_chuyen_qlt, {
            kieu: this.params.p_LocDCDonVi,
            khoNhanId: this.cboDenKho.value,
            ngayCt: getDateStringOrDefault(this.dtpNgayDK.value),
            ndGiao: 'Điều chuyển QLTS',
            nghiepVuId: this.cboNghiepVu.value,
            maPda: this.cboMa_PDA.value,
            dsVt: t_ctct,
            dsChiTiet: t_ctbi,
            nguoiCn: await this.$root.token.getUserName(),
            mayCn: '',
            ipCn: '',
            khoGiaoId: this.cboTuKho.value,
            loaiCtId: this.params.p_LocDCLoaiCT,
            donViGiaoId: this.$root.token.getDonViID(),
            donViNhanId: this.$root.token.getDonViID(),
            loaiPhieuId: this.cboKieuPhieu.value,
            mucDichId: this.cboMucDich.value
          })
          ketqua_ = result
        } else {
          let json_thuong = vattu_ok
          let ketqua_ = await this.callApiWrapper(api.sinh_dieu_chuyen_qlt, {
            kieu: this.params.p_LocDCDonVi,
            khoNhanId: this.cboDenKho.value,
            ngayCt: getDateStringOrDefault(this.dtpNgayDK.value),
            ndGiao: 'Điều chuyển QLTS',
            nghiepVuId: this.cboNghiepVu.value,
            maPda: this.cboMa_PDA.value,
            dsVt: vattu_ok,
            dsChiTiet: [{}],
            nguoiCn: await this.$root.token.getUserName(),
            mayCn: '',
            ipCn: '',
            khoGiaoId: this.cboTuKho.value,
            loaiCtId: this.params.p_LocDCLoaiCT,
            donViGiaoId: this.$root.token.getDonViID(),
            donViNhanId: this.$root.token.getDonViID(),
            loaiPhieuId: this.cboKieuPhieu.value,
            mucDichId: this.cboMucDich.value
          })
          ketqua_ = result
        }
        if (ketqua_.includes('OK')) {
          this.$toast.success('Tạo chứng từ thành công')
          if (this.cboLoaiChungTu.value == loc_ct) {
            let vchungtu_tmp_id = ketqua_.split('-')[1]
            await this.Load_ds_chungtu()
            // ChucNang.FocusGridViewRow(gridChungTu, "CHUNGTU_ID", vchungtu_tmp_id);
          } else {
            // let frm = new frmNhapXuatVT_v2();
            // frm.Tag = loc_ct == "1" ? 1 : (loc_ct == "99" ? 2 : Convert.ToInt32(loc_ct) + 1);
            // frm.ShowDialog(this);
          }
        } else {
          this.$toast.error(`Tạo chứng từ thất bại ${ketqua_}`)
        }
      }
    },
    /**
     * @see: private void txtPhieuDeNghi_ButtonClick(object sender, ButtonPressedEventArgs e)
     */
    async txtPhieuDeNghi_ButtonClick(buttonTag) {
      if (buttonTag == '0') await this.TAO_PHIEU_DENGHI()
      else if (buttonTag == '-1') await this.XOA_PHIEU_DENGHI()
      else if (buttonTag == '1') {
        if (this.gridChungTu.value == null) {
          this.$toast.warning('Chưa chọn chứng từ!')
          return
        }
        const routeData = this.$router.resolve({ name: 'TraCuuDeNghi', query: { so_phieu: `${this.txtSoPhieu.value}`, phieu_de_nghi: `${this.txtPhieuDeNghi.value}`, loaidn_id: `${this.gridChungTu.value.LOAIDN_ID}` } })
        window.open(routeData.href, '_blank')
      }
    },
    setLaiGridVatTu(val) {
      this.gridVatTu.list = val
    },
    contextMenuOnClicked(targetId) {
      document.getElementById(targetId).ej2_instances[0].open(event.clientY, event.clientX)
    },
    openFrmLaySerial() {
      this.frmLaySerial.input.v_grvVT = this.grcVT.list
      this.ejsDialog.name = 'frmLaySerial'
      this.ejsDialog.header = 'Danh sách vật tư'
      this.$refs.ejsDialog.show()
    },
    async contextMenuSelected(menu) {
      switch (menu.item.id) {
        case 'hoan_thanh':
          this.tsbtnHoanThanh_Click()
          break
        case 'phieu_nhap':
          // await this.xu_ly_in_phieu('PNK')
          await this.tsbtnPhieuNhap_Click()
          break
        case 'phieu_xuat':
          this.tsbtnPhieuXuat_Click()
          break
        case 'phieu_chuyen':
          this.tsbtnPhieuChuyen_Click()
          break
        case 'chi_tiet_sr':
          this.tsbtnChiTietSR_Click()
          break
        case 'phieu_tam_ung_vt':
          this.tstPhieuTUVT_Click()
          break
        case 'bbgn_nhap_kho':
          this.tsbtnBBGNNhap_Click()
          break
        case 'bbgn_xuat_kho':
          this.tsbtnBBGNXuat_Click()
          break
        case 'phieu_xuat_dvtc':
          this.tsbtnInXuatDVTC_Click()
          break
        case 'in_tem_serial':
          this.tsbtnInTemSR_Click()
          break
        case 'phieu_nhap_kho_khong_thue':
          this.tsbtnInPhieuNKKT_Click()
          break
        case 'phieu_giao_hoan_tra_vt':
          await this.xu_ly_in_phieu('HTVTTT')
          break
        case 'sua_ct':
          this.tsbtnSuaCT_Click()
          break
        case 'dc_loai_kho':
          this.tsbtnExcelLoaiKho_Click()
          break
        case 'dc_chat_luong':
          this.tsbtnExcelChatLuong_Click()
          break
        case 'dc_ghi_chu':
          this.tsbtnExcelGhiChu_Click()
          break
        case 'xuat_ims':
          this.tsbtnExcelXuatIMS_Click()
          break
        case 'gui_sms':
          this.tsbtnSMS_Click()
          break
        case 'tao_serial':
          this.tsbtnSinhSr_Click()
          break
        case 'in_serial':
          break
      }
    },
    onBeforeMoreItemRender: function (args) {
      let container = createElement('div', { className: 'drop-container' })
      if (args.item.items != null) {
      }
      if (args.item.child_icon) {
        let icon = createElement('span', { className: `icon ${args.item.child_icon} drop-icon` })
        icon.style.color = args.item.items.length > 0 ? '#C0C0C0' : '#3277F5'
        icon.style.margin = '0 10px 0 0'
        container.appendChild(icon)
      }
      let text = createElement('span')
      text.style.color = args.item.items.length > 0 ? '#C0C0C0' : '#3277F5'
      text.textContent = args.item.child_text
      container.style.padding = '0px 5px !important'
      container.appendChild(text)
      args.element.appendChild(container)
      args.element.style.height = '35px'
      args.element.style.borderBottom = '.5px solid #3277F5'
      args.element.style.display = 'flex'
      args.element.style.justifyContent = 'flex-start'
      args.element.style.alignItems = 'center'
    },
    async kiem_tra_xac_nhan_ct(data) {
      let kq = await this.callApiWrapper(api.kiem_tra_xac_nhan_ct, data)
      if (kq != null && kq.length > 0 && (kq[0] == 'OK' || kq[0] == '1')) {
        return '1'
      } else return kq[0]
    },
    gridChungTuSelectedItemsChanged(e) {
      this.gridChungTu.selected = e
    },
    gridVatTuSelectedItemsChanged(e) {
      this.gridVatTu.selected = e
    },
    gridVatTuDataBound(e) {
      if (this.xTabTongHop.visible == true) {
        if (this.kieugoi_id != 2) {
          this.cboLoaiChungTu.enabled = this.gridVatTu.list.length == 0
          this.cboTuKho.enabled = this.gridVatTu.list.length == 0
        } else {
          this.cboTuKho.enabled = false
          this.cboLoaiChungTu.enabled = false
        }
      }
    },
    gridChungTuActionBegin(e) {},
    gridChungTuDataSourceChanged(e) {},
    gridChungTuActionComplete(e) {
      //NOTE CHECK LAI
      console.log('gridChungTuActionComplete')
      console.log(e)
      console.log(this.vchungtu_id)
      console.log(this.gridChungTu.list)
      if (e.requestType == 'refresh' && this.gridChungTu.list.length > 0) {
        if (this.vchungtu_id == 0) {
          this.$refs.gridChungTu.selectRow(0, false)
          return
        }
        if (this.gridChungTu.list.length > 0) {
          try {
            const index = this.gridChungTu.list.findIndex((item) => item.CHUNGTU_ID == this.vchungtu_id)

            if (index == -1) {
              this.$refs.gridChungTu.selectRecord(0)
            } else {
              const toPage = parseInt(index / this.$refs.gridChungTu.pageSize)
              const toRow = index % this.$refs.gridChungTu.pageSize
              this.$refs.gridChungTu.selectRecord(index)
            }
            // this.vchungtu_id = 0
            return
          } catch (e) {}
        }
      }
      if (e.requestType == 'paging') {
      }
    },

    async frmDSVatTu_onBtnHoanThanhClicked(f) {
      console.log('frmDSVatTu_onBtnHoanThanhClicked')
      console.log(f)
      this.$refs.ejsDialog.hide()
      if (f.XacNhan) {
        this.dtLuuVT = f.dt
        this.dtVT_LE = []
        let lohang = ''
        let zvma_md = ''
        let d = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (d.length) {
          zvma_md = d[0]
        }
        let seq_LH = await this.callApiWrapper(api.get_key, {
          keyName: 'LOHANG',
          numBlockSize: 1,
          numRetry: 10
        })
        if (zvma_md == 'N.DK') {
          lohang = 'DK' + seq_LH
        } else if (zvma_md == 'N.KDV') {
          lohang = 'KDV' + seq_LH
        } else {
          lohang = seq_LH
        }

        for (let item of f.dt) {
          let r = {}
          let lohang2 = ''
          let lohang_temp = ''
          let q = f.dt.filter((a) => a.VATTU_ID == item.VATTU_ID && (a.LOHANG == item.LOHANG || !a.LOHANG)).length
          if (q > 1) {
            lohang2 = await this.callApiWrapper(api.get_key, {
              keyName: 'LOHANG',
              numBlockSize: 1,
              numRetry: 10
            })
          }
          lohang_temp = lohang
          if (lohang2 != '') {
            lohang_temp = lohang2
          }
          if (item.LOHANG.trim() && zvma_md != 'N.DK' && zvma_md != 'N.KDV') {
            r.LOHANG = await this.tao_lohang(item.LOHANG, lohang_temp)
          } else {
            r.LOHANG = lohang_temp
          }
          r.LOHANG = 0
          r.CTCT_ID = 0
          r.TEN_VT = item.TEN_VT
          r.MA_VT = item.MA_VT
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.SOLUONG = item.SOLUONG
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.TIEN)

          r.LOAI_TBI = item.LOAI_TBI
          r.LOAITBI_ID = item.LOAITBI_ID
          r.GHICHU = item.GHICHU
          r.TY_LE = ''
          // r.CHATLUONG = item.CHATLUONG
          // r.NGUON_GOC = item.NGUON_GOC
          // r.CHATLUONG_ID = item.CHATLUONG_ID
          // r.HETHONG_ID = item.HETHONG_ID
          // r.DONGTBI_ID = item.DONGTBI_ID
          r.CHATLUONG = r.CHATLUONG
          r.NGUON_GOC = item.NGUON_GOC === undefined || item.NGUON_GOC == null ? '' : item.NGUON_GOC
          // r.CHATLUONG_ID = item.CHATLUONG_ID_TEMP === undefined || item.CHATLUONG_ID_TEMP == null ? '' : item.CHATLUONG_ID
          r.CHATLUONG_ID = item.CHATLUONG_ID
          r.DONGTBI_ID = item.DONGTBI_ID_TEMP === undefined || item.DONGTBI_ID_TEMP == null ? '' : item.DONGTBI_ID
          r.HETHONG_ID = item.HETHONG_ID_TEMP === undefined || item.HETHONG_ID_TEMP == null ? '' : item.HETHONG_ID

          r.TINHTRANG = ''
          if (item.HAN_BH) {
            r.HAN_BH = item.HAN_BH //dd/MM/yyyy
          }
          if (this.dtLuuVT.length && this.dtLuuVT[0].hasOwnProperty('MATHUNG') && this.dtLuuVT[0].hasOwnProperty('MAKHOTD') && this.dtLuuVT[0].hasOwnProperty('LOAIKHO')) {
            r.MATHUNG = item.MATHUNG
            r.MAKHOTD = item.MAKHOTD
            r.LOAIKHO = item.LOAIKHO
          }
          if (this.dtLuuVT.length && this.dtLuuVT[0].hasOwnProperty('MATHETS')) {
            r.MATHETS = item.MATHETS
          }
          this.dtVT_LE.push(r)
        }
        this.gridVatTu.list = this.dtVT_LE
        this.kieunhap = 3 //Nhap lẻ
      }
    },
    //spda function
    async cboNghiepVu_EditValueChanged() {
      if (this.cboNghiepVu.value == null) {
        return
      }
      let nghiepvu_ = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboNghiepVu.value })
      if (!nghiepvu_.length) {
        return
      }
      // this.cboMa_PDA.value = null
      // this.spda = {}
      let ma_nghiepvu = nghiepvu_[0]
      if (ma_nghiepvu == 'NV.BBBGIMS') {
        this.OpenCboMa_PDA = true
      } else if (ma_nghiepvu == 'NV.DUAN') {
        this.OpenCboMa_PDA = true
      } else if (ma_nghiepvu == 'NV.XUATIMS' || ma_nghiepvu == 'NV.ACAP' || ma_nghiepvu == 'NV.CHUYENNGUON') {
        this.OpenCboMa_PDA = true
      } else if (ma_nghiepvu == 'NV.THUHOI' || ma_nghiepvu == 'NV.THAYTHE') {
        this.OpenCboMa_PDA = true
      } else if (ma_nghiepvu == 'NV.DIEUCHUYEN') {
        this.OpenCboMa_PDA = true
      } else {
        this.OpenCboMa_PDA = false
      }
    },
    cboMaPhieu_EditValueChanged() {
      this.params.p_Ma_PDA = this.params.p_MaPhieu
    },
    async cboMaPhieu_ButtonClick() {
      try {
        if (this.cboMucDich.value == null || this.cboMucDich.value == '') {
          this.$toast.error('Chưa chọn mục đích!')
          return
        }
        let data = await this.getDSPhieuNV(this.cboMucDich.value)
        if (data == 'null') {
          this.cboMaPhieu = []
        } else {
          this.cboMaPhieu = JSON.parse(data)
        }
      } catch (e) {
        this.cboMaPhieu = []
        this.$toast.error(e.data.message)
      } finally {
      }
    },
    //
    async frmDonHangtt_onBtnXacNhanClicked(data) {
      console.log('frmDonHangtt_onBtnXacNhanClicked')
      console.log(data)
      this.$refs.ejsDialog.hide()
      let f = data
      if (f.XacNhan) {
        this.dtDonHang = []
        let lohang = await this.callApiWrapper(api.get_key, {
          keyName: 'LOHANG',
          numBlockSize: 1,
          numRetry: 10
        })
        for (let item of f.dt) {
          let r = {}
          r.CTCT_ID = 0
          r.CTDH_ID = parseInt(item.CTDH_ID)
          r.LOHANG = lohang
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.TEN_VT = item.TEN_VT
          r.MA_VT = item.MA_VT
          r.SOLUONG = [undefined, null, ''].includes(item.SL_XUAT) ? 0 : parseFloat(item.SL_XUAT)
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = r.SOLUONG * parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI
          r.LOAITBI_ID = item.LOAITBI_ID
          r.LOAIKHO = ''
          r.MAKHOTD = ''
          r.TY_LE = ''
          r.MATHUNG = ''
          r.MATHETS = ''
          r.CHATLUONG_ID = ''
          r.DONGTBI_ID = ''
          r.HETHONG_ID = ''
          this.dtDonHang.push(r)
        }
        this.gridVatTu.list = this.dtDonHang
        this.cboTuKho.value = f.kho_id
        this.kieunhap = 1 //Nhap tu don hang
        // this.enabled.gridVatTu.SOLUONG = false
        this.ngay_dh = f.ngay_nhap
        this.dtpNgayDK.value = DateTimeHelper.NgayCN()
        this.txtNoiDung.value = ''
        //gridvattu_datasource_change
      }
    },
    grcMa_PDA_onDuAnClicked(data) {
      this.btnChon_PDA_ButtonClick(data)
    },
    async grcMa_PDA_onBtnXacNhanClickedListener(data) {
      console.log('grcMa_PDA_onBtnXacNhanClickedListener')
      console.log(data)
      this.spda = data
      if (this.vma_md == '2.XCP') {
        let kq = await this.callApiWrapper(api.xacnhan_tt_xuat_ims, {
          p_data: JSON.stringify({
            CHUNGTU_ID: this.vchungtu_id,
            NGHIEPVU_ID: this.cboNghiepVu.value,
            MA_PDA: this.cboMa_PDA.value,
            MA_CSHT: this.spda.cboIMS_CSHT,
            MA_DV_SD: this.spda.cboMa_DV_SD,
            MA_DV_QL: this.spda.cboMa_DV_QL,
            MA_HD_PO: this.spda.ContractNumber,
            CONTRACT_INFO_ID: this.spda.cboHD_XuatIMS,
            NGUOI_CN: await this.$root.token.getUserName(),
            IP_CN: await this.$root.token.getIP(),
            MAY_CN: await this.$root.token.getMachine()
          })
        })
        if (kq.length && kq[0].CODE == 1) {
          this.$toast.success(kq[0].MSG)
          await this.Load_ds_chungtu()
        } else {
          this.$toast.warning(kq[0] != null ? `${kq[0].MSG}` : 'Thất bại')
        }
      } else {
        console.log(this.spda)
      }
      this.$refs.ejsDialog.hide()
    },
    async gridChungTuContextMenuClick(e) {
      let row = e.rowInfo.rowData
      switch (e.item.id) {
        case 'tra_cuu_tt_dong_bo':
          let data = e.rowInfo.rowData
          this.frmTraCuuTTDongBo.input.sophieu = data.SOPHIEU
          this.frmTraCuuTTDongBo.input.vchungtu_id = data.CHUNGTU_ID
          this.ejsDialog.name = 'frmTraCuuTTDongBo'
          this.ejsDialog.header = 'Tra cứu trạng thái đồng bộ'
          this.$refs.ejsDialog.show()
          break
        case 'danh_sach_thue_bao':
          await this.btnTB_ButtonClick(row)
          break
      }
    },
    gridChungTuContextMenuOpen(args) {
      let row = args.rowInfo.rowData
      console.log(row)
      if (row['SPVT'] > 0 || row['SPBH'] > 0) {
        args.element.ej2_instances[0].showItems(['Danh sách thuê bao'], false)
      } else {
        args.element.ej2_instances[0].hideItems(['Danh sách thuê bao'], false)
      }
    },
    gridBienBanIMS_OnChonBBBG(data) {
      this.btn_chonBBBG_ButtonClick(data)
    },
    async btn_chonBBBG_ButtonClick(data) {
      let { row, gridBienBanIMS, bbbgiao, cboDuanIMS, cboHDPOIMS } = data
      // try {
      if (this.tsbtnNhapMoi.enabled) {
        this.$toast.error('Bạn chưa nhập mới không thể chọn BBBG !')
        return
      }
      if (!(await this.KiemTraDuLieu(true, false, 'BBBG'))) {
        return
      }
      let ma_bbbg = row.AcceptanceGeneralName
      if (!confirm(`Ban có chắc chắn muốn chọn biên bản bàn giao ${ma_bbbg} không? `)) return
      if (bbbgiao.length == 0) {
        this.$toast.error('Không có dữ liệu BBBG (row)')
        return
      }
      let loc_dl = gridBienBanIMS.filter((a) => a.AcceptanceGeneralName == ma_bbbg)
      if (loc_dl.length > 0 && this.kieugoi_id == 2) {
        let project = cboDuanIMS.list.filter((a) => a.ProjectID == cboDuanIMS.value)
        let ketqua_kt = await this.callApiWrapper(api.ds_vat_tu_theo_nvu, {
          kieu: 1,
          ds: loc_dl,
          maPda: project.length > 0 ? project[0].ProjectCode : null,
          tenPda: project.length > 0 ? project[0].ProjectName : null,
          pdaId: project.length > 0 ? project[0].ProjectID : null
        })
        if (ketqua_kt && ketqua_kt.kq != 1) {
          this.$toast.error(`${ketqua_kt.kq}`)
          return
        }
        let Object_PO = cboHDPOIMS.list.find((a) => a.ContractInfoID == cboHDPOIMS.value)
        let pushData = {
          kieu: 1,
          khoNhanId: this.cboDenKho.value,
          ngayCt: getDateStringOrDefault(this.dtpNgayDK.value, FORM_CONFIG.DEFAULT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
          ndGiao: this.txtNoiDung.value,
          nghiepVuId: this.cboNghiepVu.value,
          maPda: project[0].ProjectCode,
          maHd: Object_PO.ContractNumber,
          contractInfoId: cboHDPOIMS.value,
          maPo: Object_PO.PO,
          bbBg: ma_bbbg,
          dsVt: JSON.parse(ketqua_kt.dsChiTiet),
          dsChiTiet: JSON.parse(ketqua_kt.dsCtTbi),
          nguoiCn: await this.$root.token.getUserName(),
          mayCn: await this.$root.token.getMachine(),
          ipCn: await this.$root.token.getIP(),
          khoGiaoId: this.cboTuKho.value,
          loaiCtId: 1,
          donViGiaoId: this.$root.token.getDonViID(),
          donViNhanId: this.$root.token.getDonViID(),
          loaiPhieuId: this.cboKieuPhieu.value,
          mucDichId: this.cboMucDich.value
        }
        let _ketqua = await this.callApiWrapper(api.sinh_ct_ims_bbg, pushData)
        if (_ketqua.startsWith('OK')) {
          this.$toast.success('Tạo chứng từ thành công')
          let vchungtu_tmp_id = parseInt(_ketqua.split('-')[1])
          await this.Load_ds_chungtu()
        } else {
          this.$toast.error(`Tạo chứng từ thất bại ${_ketqua}`)
        }
      } else {
        this.$toast.error(`Không tìm thấy biên bản bàn giao: ${ma_bbbg}`)
      }
      // } catch (e) {
      //   this.$toast.error(`${e.data.message}`)
      // } finally {
      // }
    },
    async frmChonTBi_CT_onBtnHoanCongClicked(data) {
      this.$refs.ejsDialog.hide()
      await this.Load_ds_thietbi()
    },
    async cap_nhat_chung_tu_nhap(data) {
      let action = await this.callApiWrapper(api.fn_capnhat_chungtu, {
        p_data: JSON.stringify(data.data),
        p_chungtu_id: data.chungTuId,
        p_kieu_nhap: data.kieuNhap,
        p_kieugoi_id: data.kieuGoiId,
        p_themmoi: data.themMoi
      })
      return action
    },
    async frmNhapDieuChinh_onBtnXacNhanClicked(data) {
      this.$refs.ejsDialog.hide()
      let f = data
      if (f.xacnhan && f.tag == 4) {
        await this.Load_ds_chungtu()
        this.$toast.success('Tạo chứng từ điều chỉnh thành công !')
      } else if (f.xacnhan) {
        let mucdich = await this.callApiWrapper(api.ds_ma_muc_dich_theo_muc_dich, { mucdich_id: this.cboMucDich.value })
        if (mucdich.length > 0 && ['N.DCDG', 'N.TTCL', 'SC_BH_DONGIA', 'NV_DAUTU_BQLDA', 'NV_KHAC_BQLDA', 'DC_CT', 'DC_CT_TTVT', 'DC_CTDP', 'DC_LDSC'].includes(mucdich[0])) {
          this.cboTuKho.value = f.kho_nhan
          this.cboDenKho.value = f.kho_giao
          this.cboCongTrinh.value = f.vcongtrinh_chon
          this.dt_chia_sl = f.dt
          this.vchungtu_id_temp = f.vchungtu_id
          this.sophieu_temp = f.sophieu
          this.cboCongTrinh.value = f.congtrinh_id
          let dt_temp = []
          for (let r of this.dt_chia_sl) {
            let i = {}
            i.CTCT_ID = r.CTCT_ID
            i.LOHANG = ''
            i.LOHANG_CHA = r.lohang_cha
            i.MA_VT = r.MA_VT
            i.TEN_VT = r.TEN_VT
            i.SOLUONG = r.SOLUONG
            i.DONGIA = r.DONGIA
            i.TIEN = r.TIEN
            i.NGUOI_CN = await this.$root.token.getUserName()
            i.IP_CN = await this.$root.token.getIP()
            i.MAY_CN = await this.$root.token.getMachine()
            i.LOAI_TBI = r.LOAI_TBI
            i.DVI_TINH = r.DVI_TINH
            i.MAKHOTD = r.MAKHOTD
            i.LOAIKHO = r.LOAIKHO
            i.LOAITBI_ID = r.LOAITBI_ID
            if (r.TINHTRANG != null) i.TINHTRANG = parseInt(r.TINHTRANG)
            i.CHATLUONG_ID = r.CHATLUONG_ID
            i.HETHONG_ID = r.HETHONG_ID
            i.DONGTBI_ID = r.DONGTBI_ID
            i.VATTU_ID = parseInt(r.VATTU_ID)
            dt_temp.push(i)
          }
          this.gridVatTu.list = dt_temp
          if (['DC_ĐT.XDCB', 'DC_SCL.DDTS'].includes(mucdich[0])) {
            let congtrinh_value = await this.callApiWrapper(api.ds_cong_trinh_theo_chung_tu, { chungtu_id: this.vchungtu_id_temp })
            if (congtrinh_value.length > 0) {
              if (congtrinh_value[0] != '' && congtrinh_value[0] != null) {
                this.cboCongTrinh.value = congtrinh_value[0]
              }
            }
          }
        } else if (mucdich[0] == 'N-X.DCMSL' || mucdich[0] == 'N.VT') {
          let id_temp = this.vchungtu_id
          await this.Load_ds_chungtu()
        }
      }
    },
    async tsbtnExcelXuatIMS_Click() {
      if (this.cboLoaiChungTu.value == null) {
        this.$toast.warning('Bạn chưa chọn loại chứng từ!')
        return
      }
      this.ejsDialog.name = 'frmExcelNghiepVu'
      this.ejsDialog.header = 'Excel'
      this.$refs.ejsDialog.show()
    },
    async frmExcelNghiepVu_afterXacNhan(data) {
      this.$refs.ejsDialog.hide()
      if (frm.xacnhan) await this.Load_ds_chungtu()
    },
    async frmLaySerial_onBtnLaySerialClicked(data) {
      let { grvVTSelectedItems } = data
      if (this.dc_ct_noibo || this.dc_ct_ttvt || this.dc_ct_ldscdp) {
        this.$toast.error('Điều chuyển nội bộ không thể chọn serial ở đây')
        return
      }
      if (!(await this.KiemTra_XuatHoanTra(false))) {
        return
      }
      if (this.kiemtra_quyen) {
        let ds_dt = await this.callApiWrapper(api.ds_ngay_nhan_thanhtoan_chungtu, { chungtu_id: this.vchungtu_id })
        if (ds_dt.length == 0) {
          this.$toast.error('Không có dữ liệu ngày nhận không thể  lấy serial')
          return
        } else {
          if (ds_dt[0] != null && ds_dt[0] != '') {
            this.$toast.error('Đã có dữ liệu ngày nhận kho bạn không thể lấy serial')
            return
          }
        }
        if (!(await this.KIEMTRA_QUYEN_XN(5, this.t_kho_id))) {
          return
        }
      }

      let vt = ''
      let lh = ''
      let dem = grvVTSelectedItems.length
      for (let e of grvVTSelectedItems) {
        vt += e.VATTU_ID + ','
        lh += e.LOHANG.replaceAll("'", "''") + ','
      }
      vt = vt.slice(0, -1) // loại bỏ dấu phẩy cuối công
      lh = lh.slice(0, -1)
      // vt = this.$refs.grvVT.selectedValues.join(",")
      // lh = this.grcVT.filter(a=>this.$refs.grvVT.selectedValues.includes(a.VATTU_ID)).map(b=>b.LOHANG || "").join("','")
      if (dem > 0) {
        // if (vt.length > 1){
        //   vt = vt.trimEnd(", ")//.ToCharArray()
        //   lh = lh.trimEnd(", ")//.ToArray()
        // }
        this.svattu_id = vt
        this.slohang = lh
        this.$refs.ejsDialog.hide()

        setTimeout(() => {
          this.frmGanSeri.input.tag = '0'
          this.frmGanSeri.input.functionName = 'btnFilter_Click'
          this.frmGanSeri.input.vlohang = this.slohang
          this.frmGanSeri.input.vvattu_id = this.svattu_id
          this.frmGanSeri.input.vkho_id = this.t_kho_id
          this.frmGanSeri.input.vchungtu_id = this.vchungtu_id
          this.ejsDialog.name = 'frmGanSeri'
          this.ejsDialog.header = 'Gán Serial vật tư'
          this.$refs.ejsDialog.show()
        }, 1000)

        // XacNhan_frmGanSeri_V2
      }
    },
    async frmGanSeri_onBtnGhiLaiClicked(data) {
      this.$refs.ejsDialog.hide()
      let frm = data
      if (frm.xacnhan && frm.functionName == 'gridVatTu_ShownEditor') {
        if (this.tsbtnNhapMoi.enabled) {
          let ctemp_id = this.vchungtu_id
          await this.Load_ds_chungtu()
        }
      }
      if (frm.xacnhan && frm.functionName == 'btnFilter_Click') {
        await this.Load_ds_thietbi()
        if (this.xTabTongHop.visible && this.chonvattu_lohang) {
          await this.Load_ds_vattu()
          await this.NAP_DS_VT_GROUP()
        }
      }
    },
    async frmVatTuTrongKho_onBtnXacNhanClicked(data) {
      let f = data
      this.$refs.ejsDialog.hide()
      setTimeout(async () => {
        if (this.isChonVT) {
          if (f.XacNhan) {
            this.xTabTongHop.visible = false
            this.ds_Gan = []
            this.dtThemVT = []
            if (!this.tsbtnNhapMoi.enabled) {
              this.gridVTGroup.list = []
            }
            this.dtLuuVT = f.dt
            for (let item of f.dt) {
              let r = {}
              r.TATCA = f.checkAll ? 1 : 0
              r.CTCT_ID = 0
              r.LOHANG = item.LOHANG
              r.VATTU_ID = item.VATTU_ID
              r.MA_VT = item.MA_VT
              r.TEN_VT = item.TEN_VT
              r.SOLUONG = item.SL_XUAT
              r.DONGIA = item.DONGIA
              r.TIEN = item.SL_XUAT * item.DONGIA
              r.SOTHANG_PB = item.SOTHANG_PB
              r.LOAI_TBI = item.LOAI_TBI
              r.LOAITBI_ID = item.LOAITBI_ID
              r.KHO_ID = item.KHO_ID
              r.MAY_CN = 'quy-test' //ttnd.may_cn
              r.NGUOI_CN = await this.$root.token.getUserName()
              r.IP_CN = await this.$root.token.getIP() //ttnd.ip
              r.TY_LE = item.TY_LE
              r.MAKHOTD = item.MAKHOTD
              r.LOAIKHO = item.LOAIKHO
              r.MATHETS = item.MATHETS
              r.MATHUNG = item.MATHUNG
              r.GHICHU_HD = item.GHICHU
              r.NGUON_GOC = item.NGUON_GOC === undefined || item.NGUON_GOC == null ? '' : item.NGUON_GOC
              r.CHATLUONG = item.CHATLUONG
              r.CHATLUONG_ID = item.CHATLUONG_ID
              r.DONGTBI_ID = item.DONGTBI_ID_TEMP === undefined || item.DONGTBI_ID_TEMP == null ? '' : item.DONGTBI_ID_TEMP
              r.HETHONG_ID = item.HETHONG_ID_TEMP === undefined || item.HETHONG_ID_TEMP == null ? '' : item.HETHONG_ID_TEMP

              if (f.dt.length && f.dt[0].hasOwnProperty('MA_DUAN')) {
                if (item.MA_DUAN) {
                  da = item.MA_DUAN.split('-')
                  r.DUAN = da[0].trimEnd()
                }
              }
              this.dtThemVT.push(r)
            }
            this.gridVatTu.list = this.dtThemVT
            this.khotu_id = f.vkho_id
            // this.enabled.gridVatTu.SOLUONG = true
            this.kieunhap = 2
            if (f.coDS) {
              this.vtdk = f.ds_dangky
            }
          }
        } else {
          if (f.XacNhan == false) {
            return
          }
          this.dtLuuVT = f.dt
          let dtThemVT_v2 = []
          for (let item of f.dt) {
            let r = {}
            r.TATCA = f.checkAll ? 1 : 0
            r.CTCT_ID = 0
            r.LOHANG = item.LOHANG
            r.VATTU_ID = parseInt(item.VATTU_ID)
            r.MA_VT = item.MA_VT
            r.SOLUONG = item.SL_XUAT
            r.DONGIA = parseInt(item.DONGIA)
            r.TIEN = parseInt(item.SL_XUAT) * parseInt(item.DONGIA)
            r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
            r.LOAI_TBI = item.LOAI_TBI
            r.LOAITBI_ID = item.LOAITBI_ID
            r.HAN_BH = ''
            r.TY_LE = item.TY_LE === undefined || item.TY_LE == null ? '' : item.TY_LE
            r.MAKHOTD = item.MAKHOTD === undefined || item.MAKHOTD == null ? '' : item.MAKHOTD
            r.LOAIKHO = item.LOAIKHO === undefined || item.LOAIKHO == null ? '' : item.LOAIKHO
            r.MATHETS = item.MATHETS === undefined || item.MATHETS == null ? '' : item.MATHETS
            dtThemVT_v2.push(r)
          }
          for (let dr of dtThemVT_v2) {
            dr.CTCT_ID = 0
          }
          this.gridVatTu.list = dtThemVT_v2
          this.dtVatTu = dtThemVT_v2
          this.khotu_id = f.vkho_ID
          this.cboTuKho.value = this.khotu_ID
          // this.enabled.gridVatTu.SOLUONG = true
          this.kieunhap = 4
          this.kieugoi_id = 2
        }
      }, 500)
    },
    async XacNhan_frmVatTuTrongKho_ChonVT() {
      let f = this.$refs.frmVatTuTrongKho
      if (f.XacNhan) {
        this.xTabTongHop.visible = false
        this.ds_Gan = []
        this.dtThemVT = []
        if (!this.tsbtnNhapMoi.enabled) {
          this.gridVTGroup.list = []
        }
        this.dtLuuVT = f.dt
        for (let item of f.dt) {
          let r = {}
          r.TATCA = f.checkAll ? 1 : 0
          r.CTCT_ID = 0
          r.LOHANG = item.LOHANG
          r.VATTU_ID = parseInt(item.VATTU_ID)
          r.MA_VT = item.MA_VT
          r.TEN_VT = item.TEN_VT
          r.SOLUONG = item.SL_XUAT
          r.DONGIA = parseInt(item.DONGIA)
          r.TIEN = parseInt(item.SL_XUAT) * parseInt(item.DONGIA)
          r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
          r.LOAI_TBI = item.LOAI_TBI
          r.LOAITBI_ID = item.LOAITBI_ID
          r.KHO_ID = item.KHO_ID
          r.MAY_CN = 'quy-test' //ttnd.may_cn
          r.NGUOI_CN = await this.$root.token.getUserName()
          r.IP_CN = await this.$root.token.getIP() //ttnd.ip
          r.TY_LE = item.TY_LE
          r.MAKHOTD = item.MAKHOTD
          r.LOAIKHO = item.LOAIKHO
          r.MATHETS = item.MATHETS
          r.MATHUNG = item.MATHUNG
          r.GHICHU_HD = item.GHICHU
          r.NGUON_GOC = item.NGUON_GOC === undefined || item.NGUON_GOC == null ? '' : item.NGUON_GOC
          r.CHATLUONG = item.CHATLUONG
          r.CHATLUONG_ID = item.CHATLUONG_ID
          r.DONGTBI_ID = item.DONGTBI_ID_TEMP === undefined || item.DONGTBI_ID_TEMP == null ? '' : item.DONGTBI_ID_TEMP
          r.HETHONG_ID = item.HETHONG_ID_TEMP === undefined || item.HETHONG_ID_TEMP == null ? '' : item.HETHONG_ID_TEMP

          if (f.dt.length && f.dt[0].hasOwnProperty('MA_DUAN')) {
            if (item.MA_DUAN) {
              da = item.MA_DUAN.split('-')
              r.DUAN = da[0].trimEnd()
            }
          }
          this.dtThemVT.push(r)
        }
        this.gridVatTu.list = this.dtThemVT
        this.khotu_id = f.vkho_id
        // this.enabled.gridVatTu.SOLUONG = true
        this.kieunhap = 2
        if (f.coDS) {
          this.vtdk = f.ds_dangky
        }
      }
    },
    async frmVatTuTrongKhoV2_onBtnXacNhanClicked(data) {},
    async onTabNoiDungClicked() {
      console.log('onTabNoiDungClicked')
      this.gridVatTu.list = this.gridVatTu.list
      // console.log(onTabNoiDungClicked)
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        height: 'auto',
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      if (this.ejsDialog.name == 'frmNhanPhieuLuanChuyen') {
        // this.frmNhanPhieuLuanChuyen_OnClosed()
      }
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    gridThietBi_SOLUONG_AggregateFn() {
      let sum = this.gridVatTu.list.reduce((a, curr) => a + curr.SOLUONG, 0)
      return currency(sum, '', 3)
    },
    gridThietBi_TIEN_AggregateFn() {
      let sum = this.gridVatTu.list.reduce((a, curr) => a + curr.TIEN, 0)
      return currency(sum, '', 3)
    },
    async tsbtnPhieuLuanChuyen_Clicked() {
      console.log('tsbtnPhieuLuanChuyen_Clicked')
      if (this.cboMucDich.value != 2264) {
        // X.LUANCHUYEN - Luân chuyển vật tư các tỉnh
        this.$toast.error('Phải chọn mục đích X.LUANCHUYEN để dùng!')
        return
      }
      // let f = this.$refs.frmNhanPhieuLuanChuyen;
      // f.loaiChungTuId = this.params.p_LoaiChungTu
      // await f.LoadForm()
      // this.$refs.popup_frmPhieuLuanChuyen.show()
      ;``
      // this.frmNhanPhieuLuanChuyen.input =
      this.ejsDialog.name = 'frmNhanPhieuLuanChuyen'
      this.ejsDialog.header = 'Phiếu luân chuyển'

      this.$refs.ejsDialog.show()
      setTimeout(async () => {
        let frmNhanPhieuLuanChuyen = this.$refs.frmNhanPhieuLuanChuyen
        console.log(frmNhanPhieuLuanChuyen)
        console.log(this.cboLoaiChungTu.value)

        frmNhanPhieuLuanChuyen.loaiChungTuId = this.cboLoaiChungTu.value
        await frmNhanPhieuLuanChuyen.LoadForm()
      }, 1000)
    },
    CloseLuanChuyen() {
      console.log('CloseLuanChuyen')
      // this.$refs.popup_frmPhieuLuanChuyen.hide()
      let f = this.$refs.frmNhanPhieuLuanChuyen
      if (f.xacnhan) {
        this.txtPhieuLuanChuyen.value = f.params.maDN
        this.txtPhieuLuanChuyen.id = f.lenhchuyen_id
      }
      this.$refs.ejsDialog.hide()
    }
  },
  watch: {
    'gridVatTu.list'(val) {
      // console.log('watcher this.gridVatTu.list')
      // console.log(val)
      if (val.filter((e) => e.ID_GEN == null || e.ID_GEN == '').length) {
        this.gridVatTu.list = this.gridVatTu.list.map((e) => {
          e.ID_GEN = this.uuidv4()
          return e
        })
      }
    },
    'gridChungTu.list'(val) {
      console.log('watcher this.gridChungTu.list')
      console.log(val)
    },
    'cboCongTrinh.value'(val) {},
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    // 'cboKho.list'(val) {
    //   this.Loc_Kho();
    // },
    // 'ckbTuKho.value'(val) {
    //   this.Loc_Kho();
    // },
    // 'ckbDenKho.value'(val) {
    //   this.Loc_Kho();
    // },
    /**
    //  * @see: private void cboNghiepVu_EditValueChanged(object sender, EventArgs e)
    //  * @description Xử lý bên trong spda_view
    //  */
    async 'cboNghiepVu.value'(val) {
      if (this.cboNghiepVu.value != -1 || this.cboNghiepVu.value != null) await this.cboNghiepVu_EditValueChanged()
    },
    /**
     * @see cboLoaiChungTu_EditValueChanged
     */
    async 'cboLoaiChungTu.value'(val) {
      if (val == null) return
      if (val == 99) {
        this.vloai = 2
        this.kieugoi_id = 2
        this.cboTuKho.list = await this.callApiWrapper(api.kho_vat_tu_le, { nhanvien_id: await this.$root.token.getNhanVienID(), donvi_id: await this.$root.token.getDonViID() })
        this.cboKieuPhieu.list = await this.callApiWrapper(api.lay_ds_kieu_phieu, { loaict_id: 1 })
        // this.AllowEdit.colMaThung = true
      } else {
        this.vloai = 1
        this.kieugoi_id = 1
        this.cboTuKho.list = await this.callApiWrapper(api.ds_kho_nhap_xuat, { nhanvien_id: await this.$root.token.getNhanVienID() })
        this.cboKieuPhieu.list = await this.callApiWrapper(api.lay_ds_kieu_phieu, { loaict_id: this.cboLoaiChungTu.value })
        // this.AllowEdit.colMaThung = false
      }
      await this.Loc_Kho()
      this.tstbtnDonHang.enabled = false
      this.tsbtnPhieuNhap.enabled = false
      this.tsbtnPhieuGiaoHoanTraVT.enabled = false
      this.tsbtnPhieuXuat.enabled = false
      this.tsbtnPhieuChuyen.enabled = false
      this.tstPhieuTUVT.enabled = false
      this.tsbtnBBGNNhap.enabled = false
      this.tsbtnBBGNXuat.enabled = false
      this.tsbtnInXuatDVTC.enabled = false
      switch (val) {
        case 1:
          this.tstbtnDonHang.enabled = true
          this.tsbtnPhieuNhap.enabled = true
          this.tsbtnPhieuGiaoHoanTraVT.enabled = true
          this.tsbtnBBGNNhap.enabled = true
          break
        case 2:
          this.tsbtnPhieuXuat.enabled = true
          this.tstbtnDonHang.enabled = false
          this.tsbtnBBGNXuat.enabled = true
          break
        case 3:
          this.tsbtnPhieuChuyen.enabled = true
          this.tstPhieuTUVT.enabled = true
          this.tstbtnDonHang.enabled = false
          this.tsbtnInXuatDVTC.enabled = true
          break
        case 99:
          this.tsbtnBBGNNhap.enabled = true
          this.tsbtnPhieuNhap.enabled = true
          this.tsbtnPhieuGiaoHoanTraVT.enabled = true
          break
        default:
          break
      }
    },
    /**
     * @see: cboKieuPhieu_EditValueChanged
     */
    async 'cboKieuPhieu.value'(val) {
      this.cboKieuPhieu_EditValueChanged()
      this.setLaiGridVatTu(this.gridVatTu.list)
    }
  }
}
</script>
<style scoped src="./NhapXuatVT_v2.scss"></style>
