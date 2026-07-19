<template src='./SubscriptionLiquidationCrossSell.html'></template>
<style scoped src='./SubscriptionLiquidationCrossSell.scss'></style>
<script>
import {
  DichVuVienThong,
  LOAI_DV,
  TrangThaiTB,
  LoaiHinhTB,
  KieuLapDat
} from "@/const/enums/index.js"
import {
  TRANGTHAI_DONGBO,
  TrangThaiHD,
  LoaiHopDong,
  SUCCESS_CODE,
  DICHVU_VIENTHONG
} from '@/constants.js'
import DialogSearchAccount from '@/modules/contract/setup/Liquidate/components/DialogSearchAccount.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import api from './SubscriptionLiquidationCrossSell.API'
import * as moment from 'moment'
import {formatDate, formatCurrency, convertDateKeysFromObject} from '@/utils/format'
import { loadComboBoxItems } from "@/utils/controls"
import cmUlt from "@/utils/commonFunction"
import DialogLyDoHuy from '@/modules/contract/setup/Liquidate/components/DialogLyDoHuy.vue'
import PopupLyDoHuy from './components/PopupLyDoHuy.vue'
import { DATE_FORMAT } from '../../../../constants'
import DialogInHoSo from '@/modules/contract/setup/Liquidate/components/InHoSo.vue'
import DialogTraCuuThueBao from '@/modules/contract/setup/Liquidate/components/popupTraCuuThueBao.vue'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import breadcrumb from '@/components/breadcrumb.vue'
import DialogChonTinh from './components/PopupChonTinh.vue'
import ChonTinhTSLModal from './components/ChonTinhTSLModal.vue'
import ThongSoTSL from '../SetupChangeTSLSpeedCrossSale/Popups/frmThongSoTSL/frmThongSoTSL.vue'
import DatePicker from 'vue2-datepicker'
import DialogEmail from '@/modules/contract/setup/Liquidate/components/DialogEmail.vue'
import DialogThongSoWan from '@/modules/contract/setup/ChangeMegaWanConfigCrossSell/popups/frmThongSoWan.vue'
import Vue from "vue"
import { on } from "@/utils/event"
import ThongSoThueBao from './ThongSoThueBao'
import listBoxItem from "../Liquidate/components/LyDoHuyListBoxItemTemplate.vue"
export default {
  components: {
    breadcrumb,
    DialogSearchAccount,
    SearchContractModal,
    DialogLyDoHuy,
    PopupLyDoHuy,
    ThongTinHenKhachHangModal,
    VatTuThueBao,
    DialogInHoSo,
    DialogTraCuuThueBao,
    DialogChonTinh,
    ChonTinhTSLModal,
    DatePicker,
    ThongSoTSL,
    DialogEmail,
    DialogThongSoWan
  },
  mixins: [pagingAndFilter],
  name: "SubscriptionLiquidationCrossSell",
  computed: {
    tt_nd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$root.token.getNguoiDungID(),
        USER_NEO: "",
        ma_nd: this.$auth.getMaNhanVien(),
        may_cn: "",
        ip: "",
        tinh_id: this.$auth.getPhanVungID()
      }
    },
    tlstpTop () {
      return {
        Items: {
          tsbtnNhapMoi: this.tsbtnVatTuMoi,
          tsbtnGhiLai: this.tsbtnVatTuMoi,
          tsbtnHuyBo: this.tsbtnVatTuMoi,
          tsbtnXoa: this.tsbtnVatTuMoi,
          tsbtnThoat: this.tsbtnVatTuMoi,
          tsbtnThemTB: this.tsbtnVatTuMoi,
          tsbtnXoaTB: this.tsbtnVatTuMoi,
          tsbtnGiaoPhieu: this.tsbtnVatTuMoi,
          tsbtnCapNhatDB: this.tsbtnVatTuMoi,
          tsbtnHen: this.tsbtnVatTuMoi,
          tsbtnThongTinLH: this.tsbtnVatTuMoi,
          tsbtnEmail: this.tsbtnVatTuMoi,
          tsbtnVatTu: this.tsbtnVatTuMoi,
          tsbtnKichHoat: this.tsbtnVatTuMoi,
          tsbtnVatTuMoi: this.tsbtnVatTuMoi
        }
      }
    },
    cboDichVuVTText () {
      return this.cboDichVuVT.Items.find(item => item.dichvuvt_id === this.cboDichVuVT.SelectedValue).ten_dvvt
    }
  },
  mounted () {
    on('LyDoHuyItemDelete', this.onLydoHuyItemDelete)
    this.frmChamDutSDBC_Load()
    api.getListSubscriptionType().then(rs => {
      this.dsLoaiHinhTBCached = rs
      this.cboDichVuVT_SelectedValueChanged()
    })
    this.$auth.getMachine().then(rs => { this.tt_nd.may_cn = rs })
    this.$auth.getIP().then(rs => { this.tt_nd.ip = rs })
  },
  data: () => ({
    header: {
      title: "chấm dứt sử dụng (bán chéo)",
      list: [
        { name: "Bán chéo", active: true },
        { name: "Chấm dứt sử dụng (bán chéo)" || "Thanh lý thuê bao", active: true }
      ]
    },
    config: [
      {
        fieldName: 'stt',
        headerText: 'Stt',
        allowFiltering: true,
        width: 80
      },
      {
        fieldName: 'hdtb_id',
        headerText: 'Id',
        isPrimaryKey: true,
        visible: false,
        allowFiltering: true
      },
      {
        fieldName: 'ma_tb',
        headerText: 'Số máy/Acc',
        allowFiltering: true,
        width: 200
      },
      {
        fieldName: 'ten_tb',
        headerText: 'Tên thuê bao',
        allowFiltering: true
      },
      {
        fieldName: 'diachi_tb',
        headerText: 'Địa chỉ thuê bao',
        allowFiltering: true
      },
      {
        fieldName: 'dichvuvt_id',
        headerText: 'dvID',
        visible: false,
        allowFiltering: true
      },
      {
        fieldName: 'trangthai_dongbo',
        headerText: 'Trạng thái đồng bộ',
        allowFiltering: true
      }
    ],
    tsbtnNhapMoi: { Enabled: true, Visible: true },
    tsbtnGhiLai: { Enabled: true, Visible: true },
    tsbtnHuyBo: { Enabled: true, Visible: true },
    tsbtnXoa: { Enabled: true, Visible: true },
    tsbtnThoat: { Enabled: true, Visible: true },
    txtGhiChu: { Text: '', Enabled: true },
    lvwThueBao: { Items: [] },
    tsbtnThemTB: { Enabled: true, Visible: true },
    tsbtnXoaTB: { Enabled: true, Visible: true },
    tsbtnGiaoPhieu: { Enabled: true, Visible: true },
    tsbnGuiCatMo: { Enabled: true, Visible: true },
    dtpNgayLapHD: { Value: null },
    txtMaHD: { Text: '', Enabled: true },
    dtpNgayYeuCau: { Value: null },
    txtMaGD: { Text: '', Enabled: true },
    txtMaTB: { Text: '', Enabled: true },
    txtDiaChiTB: { Text: '', Enabled: true },
    txtTenTB: { Text: '', Enabled: true },
    cboKieuLD: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    cboDichVuVT: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    txtDiaChiKH: { Text: '', Enabled: true },
    txtTenKH: { Text: '', Enabled: true },
    txtMaKH: { Text: '', Enabled: true },
    lvLyDoHuy: { Items: [] },
    txtDiaChiLD: { Text: '', Enabled: true },
    cboVeTinh: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    txtDiaChiTT: { Text: '', Enabled: true },
    cboTocDoADSL: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    cboLoaihinhTB: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    tsbtnCapNhatDB: { Enabled: true, Visible: true },
    tsbnHoanThienDB: { Enabled: true, Visible: true },
    btnPhieuYC: { Enabled: true },
    inPLToolStripMenuItem: { Enabled: true },
    btnInPL: { Enabled: true },
    tsbtnHen: { Enabled: true, Visible: true },
    tsbtnThongTinLH: { Enabled: true, Visible: true },
    tsbtnEmail: { Enabled: true, Visible: true },
    tsbtnVatTu: { Enabled: true, Visible: true },
    txtSoAo: { Text: '', Enabled: true },
    txtHuongKN: { Text: '', Enabled: true },
    txtSoAo_Cuoi: { Text: '', Enabled: true },
    cboVeTinh_Cuoi: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    txtDiaChiLD_Cuoi: { Text: '', Enabled: true },
    txtSoAo_Dau: { Text: '', Enabled: true },
    cboVeTinh_Dau: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    txtDiaChiLD_Dau: { Text: '', Enabled: true },
    txtMaHD_TTKD: { Text: '', Enabled: true },
    txtSiteID: { Text: '', Enabled: true },
    tsbtnKichHoat: { Enabled: true, Visible: true },
    dtpNgayCV: { Value: null },
    txtCongVan: { Text: '', Enabled: false },
    chkCongVan: { Checked: false, Enabled: true },
    dtpNgayHT: { Value: null },
    chkNgayHT: { Checked: false, Enabled: true },
    chkThuHoi: { Checked: false, Enabled: true },
    cboGoiCuocDD: { Items: [], SelectedValue: null, Enabled: true, Text: "" },
    txtTienNo: { EditValue: '' },
    btnCuocnong: { Enabled: true, Visible: true },
    txtCuocnong: { Text: '' },
    tsbtnVatTuMoi: { Enabled: true, Visible: true },
    btnDiemCuoi: { Visible: true },
    btnDiemDau: { Visible: true },
    txtMaBSS: { Text: '', Enabled: true },
    txtMaERP: { Text: '', Enabled: true },
    // ------
    Kieuld_tc_id: 0,
    kieuld_dc: 0,
    kieuld_dau: 0,
    kieuld_cuoi: 0,
    Kieuld_click: false,
    thuebao_tc_id: 0,
    dichvuvt_id: 0,
    doituong_id: 0,
    trangthaitb_id: 0,
    hdkh_id: 0,
    hdtb_id: 0,
    donvi_id: 0,
    khachhang_id: 0,
    thuebao_id: 0,
    kiemtra:true,
    loaitb_id: 0,
    ma_gd_neo: "",
    kieu_tl: "1",
    goicuoc_id: 0,
    doituong_id_neo: 0,
    simcard: "",
    so_gt: "",
    so_dt: "",
    flagThongSoTSL: 0,
    tocdo_id: 0,
    muccuoc_id: 0,
    quytrinh_id: 0,
    doitac_id: 0,
    linhvuc_id: 0,
    h_thuebao_cha_id: 0,
    quytrinh_tc_tn: 0,
    huonggiao_tc_tn: 0,
    vloaikenh_id: 0,
    quytrinh_tc_tn_dau: 0,
    huonggiao_tc_tn_dau: 0,
    quytrinh_tc_tn_cuoi: 0,
    huonggiao_tc_tn_cuoi: 0,
    dsHDKH: [],
    dsHDTB: [],
    dsHDTB_DD: [],
    dsHDTT: [],
    dsTTTT: [],
    dsDKHUY: [],
    dsHDTB_ADSL: [],
    dsHDTB_CD: [],
    dsHDTB_IMS: [],
    dsHDTB_GP: [],
    dsHDTB_TSL: [],
    dsHDTB_MGW: [],
    dsHDTB_CNTT: [],
    ItemList: [],
    dsTIEN_HDTB_TC: [],
    dsHuy: [],
    dsTemp: [],
    dsBD_LYDOHUY: [],
    dsDKHUYBC: [],
    dsDKHUY_TEMP: [],
    isdn_sochinh: false,
    matb_isdn_chinh: "",
    ds_isdn: [],
    hdtb_id_cha: 0,
    dsHDTBDV: [],
    ds_cd: [],
    kt_kenhphu_ao: false,
    ts_khong_kt_nocuoc: 0,
    chktudong_thuhoiVT: 0,
    kt_qt_kieuld_isdn: false,
    ts_sinhma_gd_theo_donvi: false,
    loi_sinh_magd: false,
    dtThaoTac: [],
    luong_id: 0,
    huygoi_hoancong_tl: -1,
    dsHDTBBC: [],
    dsHDBC_ADSL: [],
    dsHDBC_CD: [],
    dsHDBC_IMS: [],
    dsHDBC_MGW: [],
    dsHDBC_TSL: [],
    dsHDBC: [],
    dsDK_DVGT: [],
    showThongSoTc: false,
    kenh_trang: true,
    btnDiemDau: {
        visible: false
    },
    btnDiemCuoi: {
        visible: false
    },
    chi_giao_net: true,
    momentFormat: {
      stringify: (date) => {
        return date ? moment(date).format('DD/MM/YYYY') : ''
      },
      parse: (value) => {
        return value ? moment(value, 'DD/MM/YYYY').toDate() : null
      }
    },
    pageSettings: { pageSize: 5 },
    dsDBTB: [],
    tinh: {},
    tinh_dau: {},
    tinh_cuoi: {},
    dsDBTBBC: [],
    thongso_ban: ThongSoThueBao,
    thongso_tc: ThongSoThueBao,
    thongso_tc_dau: ThongSoThueBao,
    thongso_tc_cuoi: ThongSoThueBao,
    dsLoaiHinhTBCached: [],
    dlgVatTuInput: {},
    inHoSoParams: {},
    LOAI_HOP_DONG: LoaiHopDong,
    thueBaoTonTai: false,
    datatemplate: function () {
      return { template: listBoxItem }
    },
    isFormValid: true,
    vkenh_toantrinh: 0
  }),
  methods: {
    onLydoHuyItemDelete (id) {
      this.lvLyDoHuy.Items = this.lvLyDoHuy.Items.filter(item => item.lydohuy_id !== id)
    },
    async frmChamDutSDBC_Load () {
      try {
        this.dtpNgayYeuCau.Value = this.tt_nd.ngay_cn
        this.dtpNgayLapHD.Value = this.tt_nd.ngay_cn
        this.dtpNgayCV.Value = this.tt_nd.ngay_cn
        this.dtpNgayHT.value = this.tt_nd.ngay_cn
        this.SetButton(-1)
        this.txtCuocnong.Text = '0'
        let dvvt = DichVuVienThong.CO_DINH
        dvvt += "," + DichVuVienThong.ADSL
        dvvt += "," + DichVuVienThong.METRONET
        dvvt += "," + DichVuVienThong.MEGAWAN
        dvvt += "," + DichVuVienThong.TSL
        dvvt += "," + DichVuVienThong.IMS
        dvvt += "," + 25
        dvvt += "," + 26
        // api.sp_ht_dichvuvt_combobox(dvvt).then(dv => {
        api.getListTelecomService(dvvt).then(dv => {
          loadComboBoxItems(this.cboDichVuVT, dv, 'dichvuvt_id', 0)
        })
        api.layDsTocDoADSL().then(rs => {
          loadComboBoxItems(this.cboTocDoADSL, rs, 'tocdo_id', 0)
        })
        api.sp_lay_dulieu_theo_dieukien_phanvung().then(rs => {
          loadComboBoxItems(this.cboGoiCuocDD, rs, 'goicuoc_id', 0)
        })
        await api.HT_TatCa_VeTinh_Combobox(LOAI_DV.TRAM_VT).then(dsTramVT => {
          loadComboBoxItems(this.cboVeTinh, dsTramVT, 'donvi_id', 0)
          loadComboBoxItems(this.cboVeTinh_Dau, dsTramVT, 'donvi_id', 0)
          loadComboBoxItems(this.cboVeTinh_Cuoi, dsTramVT, 'donvi_id', 0)
        })
        this.tsbnGuiCatMo.Visible = false
        this.tsbtnKichHoat.Enabled = false
        let ds = await api.lay_ds_thamso_md(-1)
        if (ds && ds.length > 0) {
          let str_vtemp = "KHONG_KT_NO_THANHLY"
          let det = ds.filter(x => x["ma_ts"] == str_vtemp)
          if (det && det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.ts_khong_kt_nocuoc = 1 }
          }
          str_vtemp = "CHECK_THUHOI_VATTU"
          det = ds.filter(x => x["ma_ts"] == str_vtemp)
          if (det && det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.chktudong_thuhoiVT = 1 }
          }
          str_vtemp = "HUYGOI_KHI_HOANCONG_TL"
          det = ds.filter(x => x["ma_ts"] == str_vtemp)
          if (det && det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.huygoi_hoancong_tl = 1 }
          }
          str_vtemp = "KT_QT_KIEULD_ISDN"
          det = ds.filter(x => x["ma_ts"] == str_vtemp)
          if (det && det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.kt_qt_kieuld_isdn = true }
          }
          this.ts_sinhma_gd_theo_donvi = ds.filter(x => x["ma_ts"] == "SINH_MA_GD_THEO_DV").length > 0
        }
        if (this.chktudong_thuhoiVT == 1) { this.chkThuHoi.Checked = true }
      } catch (exp) {
        this.$toast.error("Lỗi: " + exp)
        // new LogError().GetError(new log4net.Core.LoggingEvent(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType, log.Logger.Repository, log.Logger.Name, log4net.Core.Level.Info, exp.Message, exp), true);
      }
    },
    HienThiNutTS_TSL (kieu) {
      // 1 la hien thi. 0 la an
      if (kieu == 1) {
        this.showThongSoTc = true
        this.btnDiemDau.visible = true
        this.btnDiemCuoi.visible = true
      } else {
        this.showThongSoTc = false
        this.btnDiemDau.visible = false
        this.btnDiemCuoi.visible = false
      }
    },
    SetButton (kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false
      this.tsbtnThemTB.Enabled = false
      this.tsbtnXoaTB.Enabled = false
      this.tsbtnGiaoPhieu.Enabled = false
      this.tsbnHoanThienDB.Enabled = false
      this.btnPhieuYC.Enabled = false
      if (kieu == -1) { // Bat dau
        this.$refs.txtMaTB.focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
      }
      if (kieu == 0) { // Bat dau
        this.$refs.txtMaTB.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
        this.Clear()
      }
      if (kieu == 1) { // Them moi
        this.$refs.txtMaTB.focus()
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
        this.Clear()
      }
      if (kieu == 2) { // Huy
        this.$refs.txtMaGD.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.Clear()
      }
      if (kieu == 3) { // Edit
        this.$refs.txtMaGD.focus()
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.tsbtnXoaTB.Enabled = true
        this.tsbtnThemTB.Enabled = true
        this.btnPhieuYC.Enabled = true
        this.tsbtnGiaoPhieu.Enabled = false
        if (this.lvwThueBao.Items.length > 1) { this.tsbtnXoaTB.Enabled = true } else { this.tsbtnXoaTB.Enabled = false }
        if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.DI_DONG) this.tsbtnGiaoPhieu.Enabled = false
      }
    },
    Clear () {
      try {
        this.kenh_trang = true
        this.chi_giao_net = true
        if (this.hdtb_id != 0) {
          if (this.dsTemp.length > 0) { this.dsTemp = [] }
        }
        this.vloaikenh_id = 0
        this.h_thuebao_cha_id = 0
        this.chkThuHoi.Checked = false
        this.txtCongVan.Text = ""
        this.chkCongVan.Checked = false
        this.chkNgayHT.Checked = false
        this.hdkh_id = 0
        this.txtSiteID.Text = ""
        this.txtMaHD_TTKD.Text = ""
        this.txtMaGD.Text = ""
        this.txtMaHD.Text = ""
        this.txtMaKH.Text = ""
        this.khachhang_id = 0
        this.txtTenKH.Text = ""
        this.txtDiaChiKH.Text = ""
        this.txtDiaChiLD_Dau.Text = ""
        this.txtDiaChiLD_Cuoi.Text = ""
        this.txtSoAo_Dau.Text = ""
        this.txtSoAo_Cuoi.Text = ""
        this.dtpNgayYeuCau.Value = this.tt_nd.ngay_cn
        this.txtMaBSS.Text = ""
        this.txtMaERP.Text = ""
        this.hdtb_id = 0
        this.thuebao_id = 0
        this.txtTenTB.Text = ""
        this.txtDiaChiTB.Text = ""
        this.txtDiaChiLD.Text = ""
        this.lvLyDoHuy.Items = []
        this.$refs.txtMaTB.focus()
        this.txtDiaChiTT.Text = ""
        this.txtGhiChu.Text = ""
        this.hdtb_id_cha = 0
        this.txtHuongKN.Text = ""
        this.txtSoAo.Text = ""
        this.so_dt = ""
        this.so_gt = ""
      } catch (ex) {
        this.$toast.error("Có lỗi:" + ex)
        console.error(ex)
      }
    },
    tsbtnNhapMoi_Click () {
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
      this.SetButton(1)
    },
    async tsbtnGhiLai_Click () {
      if(this.isFormValid == true){
        if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET|| this.dichvuvt_id == 26) {
          if ((this.kenh_trang 
              || this.chi_giao_net) && ((this.thongso_ban && this.thongso_ban.muccuoctb_id == 0) 
              || (this.thongso_tc && this.thongso_tc.muccuoctb_id == 0)) && this.dichvuvt_id != DichVuVienThong.TSL && this.dichvuvt_id != 25) {
            if (this.kenh_trang) {
              this.$toast.error("Bạn chưa chọn thông số cho kênh trắng. Ấn vào kiểu yêu cầu để lấy thông tin")
            } else {
              this.$toast.error("Bạn chưa chọn thông số. Ấn vào kiểu yêu cầu để lấy thông tin")
            }
            return
          }
          if (this.kenh_trang && ((this.thongso_ban && this.thongso_ban.muccuoctb_id == 0) 
              || (this.thongso_tc_dau && this.thongso_tc_dau.muccuoctb_id == 0) 
              || (this.thongso_tc_cuoi && this.thongso_tc_cuoi.muccuoctb_id == 0)) && this.dichvuvt_id == DichVuVienThong.TSL && this.dichvuvt_id != 25) {
             this.$toast.error("Bạn chưa chọn thông số cho kênh trắng. Ấn vào điểm đầu, điểm cuối để lấy thông tin")
            return
          }
        }
        if (!this.lvLyDoHuy.Items || this.lvLyDoHuy.Items.length == 0) {
          this.$toast.error("Bạn chưa chọn lý do hủy!")
          return
        }
        if (this.chkThuHoi.Checked == false) {
          const cf = await this.$bvModal.msgBoxConfirm("Bạn chắc chắn không thu hồi thiết bị của khách hàng tại điểm giao dịch?", {
            okTitle: 'Có',
            cancelTitle: 'Không'
          })
          if (!cf) return
        }
        await this.CapNhat()
      }
    },
    async tsbtnXoa_Click () {
      const cf = await this.$bvModal.msgBoxConfirm("Bạn có chắc muốn xóa hợp đồng không ?", {
        okTitle: 'Có',
        cancelTitle: 'Không'
      })
      if (!cf) return
      this.loading(true)
      const ds_tb = await api.lay_ds_hdtb_theo_hdkh(this.hdkh_id)
      if (ds_tb && ds_tb.length == this.lvwThueBao.Items.length) { await this.XoaHDKH() } else { await this.XoaHDTB() }
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
      this.loading(false)
    },
    tsbtnHen_Click () {
      this.$refs.dlgThongTinHenKhachHang.showModal()
    },
    async lvwThueBao_SelectedIndexChanged () {
      const selectedItems = this.$refs.lvwThueBao.$refs.grid.getSelectedRecords()
      if (selectedItems.length > 0) {
        this.hdtb_id = selectedItems[0]['hdtb_id']
        this.dichvuvt_id = selectedItems[0]['dichvuvt_id']
        this.tsbnGuiCatMo.Enabled = false
        this.HienThiTTHopDongTB(await api.lay_ds_hopdong_tb_theo_hdtb_id(this.hdtb_id))
        // khong co nut tsbtnCapNhatDB tren UI
        // this.GDV_HOANTHIEN(this.quytrinh_id).then(rs => {
        //   if (rs) {
        //     this.tsbtnCapNhatDB.Visible = true
        //   } else { this.tsbtnCapNhatDB.Visible = false }
        // })
        // khong co nut tsbtnCapNhatDB tren UI
        // if (this.dichvuvt_id == DichVuVienThong.GPHONE) { this.tsbtnCapNhatDB.Enabled = true } else { this.tsbtnCapNhatDB.Enabled = false }
        if ((this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL || this.cboDichVuVT.SelectedValue == DichVuVienThong.DI_DONG) &&
                        this.PHAILAM_KICHHOAT(this.quytrinh_id)) {
          this.tsbtnKichHoat.Enabled = true
        } else { this.tsbtnKichHoat.Enabled = false }
      }
    },
    async XoaHDKH () {
      const ds_tb = await api.lay_ds_hdtb_theo_hdkh(this.hdkh_id)
      if (ds_tb.find(r => r['tthd_id'] != 1)) {
        this.$toast.error("Hợp đồng đã có phiếu giao đi. Bạn không thể xóa hợp đồng !")
        return
      }
      if (ds_tb.find(r => r['status'] == 5)) {
        this.$toast.error("Hợp đồng đã có phiếu kích hoạt visa. Bạn không thể xóa hợp đồng !")
        return
      }
      const rsXoa = await api.xoa_hdkh_v2(this.hdkh_id, LoaiHopDong.CHAMDUT_SD)
      if (rsXoa.error_code != SUCCESS_CODE) {
        this.$toast.error(rsXoa.message)
        return
      }
      this.SetButton(0)
      this.$toast.success("Xóa dữ liệu hợp đồng khách hàng thành công !")
    },
    async XoaHDTB () {
      const rsXoa = await api.xoa_hdtb(this.hdtb_id)
      if (rsXoa.error_code != SUCCESS_CODE) {
        this.$toast.error(rsXoa.message)
        return
      }
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        const ds = await api.lay_ds_hd_tbdd_theo_hdkh_id(this.hdkh_id)
        await this.HienThiDanhSacHDTB(ds)
      } else {
        const ds = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TrangThaiHD.MOI)
        await this.HienThiDanhSacHDTB(ds)
      }
      if (this.lvwThueBao.Items.length > 0) {
        this.hdtb_id = parseInt(this.lvwThueBao.Items[0]['hdtb_id'])
        const ds = await api.LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(this.hdtb_id)
        this.HienThiTTHopDongTB(ds)
      }
      this.$toast.success("Xóa dữ liệu hợp đồng thuê bao thành công !")
    },
    async KiemTraDL_KhachHang () {
      if (this.txtTenKH.Text == "") {
        this.$toast.error("Hãy nhập tên khách hàng !")
        this.$refs.txtTenKH.focus()
        return false
      }
      if (this.txtMaKH.Text == "" && this.khachhang_id != 0) {
        this.$toast.error("Kiểm tra lại mã khách hàng !")
        this.$refs.txtMaKH.focus()
        return false
      }
      if (this.txtMaGD.Text != "") {
        const data = await api.Kiemtra_makh_mtt(this.txtMaGD.Text, 3, LoaiHopDong.CHAMDUT_SD)
        if (!data) {
          this.$toast.error("Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!")
          this.$refs.txtMaGD.focus()
          return false
        }
      }
      return true
    },
    async KiemTraDL_ThueBao () {
      if (this.txtMaHD.Text == "" && this.khachhang_id != 0) {
        this.$toast.error("Hãy nhập mã hợp đồng !")
        this.txtMaHD.focus()
        return false
      }
      if (this.txtTenTB.Text == "") {
        this.$toast.error("Hãy nhập tên thuê bao !")
        this.txtTenTB.focus()
        return false
      }
      if (this.cboKieuLD.SelectedValue == null) {
        this.$toast.error("Hãy chọn kiểu yêu cầu cho thuê bao này !")
        return false
      }
      let dskt = await api.LAY_DANHBA_THEO_MATB(this.txtMaTB.Text, this.dichvuvt_id, 0)
      if (!dskt) {
        this.$toast.error("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB.Text + " đối với dịch vụ " + this.cboDichVuVTText)
        return false
      }
      let kiemTraThemMoi = await api.KIEMTRA_DB_KHI_THEMMOI(this.txtMaTB.Text, 0, this.thuebao_id, this.dichvuvt_id)
      if (!kiemTraThemMoi) {
        this.$toast.error("Thuê bao không tồn tại trong danh bạ (hoặc chưa Enter tại ô Mã TB). Không thể cập nhật!")
        return false
      }
      if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET|| this.dichvuvt_id == 26) {
        if (this.kt_kenhphu_ao == true) {
          if (this.cboKieuLD.SelectedValue != KieuLapDat.TL_KENHPHU_AO) {
            this.$toast.error("Thuê bao " + this.txtMaTB.Text + " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Thanh lý kênh phụ ảo' !")
            return false
          }
        } else {
          if (this.cboKieuLD.SelectedValue == KieuLapDat.TL_KENHPHU_AO) {
            this.$toast.error("Bạn không được chọn kiểu lắp đặt 'Thanh lý kênh phụ ảo' cho thuê bao '" + this.txtMaTB.Text + "' !")
            return false
          }
        }
      }
      if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
        this.ds_cd = await api.LAY_DS_DB_CD_THEO_TBID(this.thuebao_id)
        if (this.ds_cd && this.ds_cd.length > 0) {
          if (this.ds_cd[0]["tg_thueso"]) {
            //const nhomkieu_ld = api.map_id("nhomkieu_ld", "kieu_ld", "where kieuld_id = " + this.cboKieuLD.SelectedValue)
            let input= {
              type:2,
              param: this.cboKieuLD.SelectedValue
            }
            let nhomkieu_ld = this.GetData(await api.fn_tt_kieu_ld(this.axios,input));
            if (nhomkieu_ld == "0") {
              this.$toast.error("Bạn chọn sai kiểu lắp đặt để thanh lý thuê bao thuê số !")
              this.$refs.cboKieuLD.focusIn() // focus()
              return false
            }
          } else {
            //const nhomkieu_ld = api.map_id("nhomkieu_ld", "kieu_ld", "where kieuld_id = " + this.cboKieuLD.SelectedValue)
            let input= {
              type:2,
              param: this.cboKieuLD.SelectedValue
            }
            let nhomkieu_ld = this.GetData(await api.fn_tt_kieu_ld(this.axios,input));
            if (nhomkieu_ld == "1") {
              this.$toast.error("Bạn không được chọn kiểu lắp đặt thanh lý thuê số cho thuê bao này !")
              this.$refs.cboKieuLD.focusIn() // focus()
              return false
            }
          }
        }
        // if (this.kt_qt_kieuld_isdn) {
        //   if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN2B_CD || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN2B_CQ ||
        //       this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN30B_CD || this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN30B_CQ) {
        //     if (this.h_thuebao_cha_id == 0) {
        //       if (this.cboKieuLD.SelectedValue != KieuLapDat.TL_LUONG_ISDN) {
        //         this.$toast.error("Số chính. Bạn phải chọn kiểu yêu cầu Thanh lý luồng ISDN!")
        //         return false
        //       }
        //     } else {
        //       if (this.cboKieuLD.SelectedValue != KieuLapDat.TL_SOPHU_ISDN) {
        //         this.$toast.error("Số phụ. Bạn phải chọn kiểu yêu cầu Thanh lý số phụ ISDN!")
        //         return false
        //       }
        //     }
        //   }
        // }
      }
      if (this.lvLyDoHuy.Items.length == 0) {
        this.$toast.error("Bạn chưa chọn lý do hủy!")
        return false
      }
      // TODO wait api
      // const rs = await api.KIEMTRA_THAYDOI_GOI_DADV_2(this.thuebao_id, LoaiHopDong.CHAMDUT_SD)
      // if (rs != "1") {
      //   this.$toast.error(rs)
      //   return false
      // }
      let vthanhtoan_id = ""
      if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
        if (this.ds_cd && this.ds_cd.length > 0) {
          if (this.ds_cd[0]["tg_thueso"] == "") {
            //vthanhtoan_id = api.map_id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id)
            let input= {
              type:1,
              param: this.thuebao_id
            }
            vthanhtoan_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
            if (vthanhtoan_id != "-1") {
              // TODO wait api
              // let no_tt = new TraCuuChungFacade().TRACUUNO_THANHTOAN(parseInt(vthanhtoan_id))
              // if (no_tt > 0) {
              //   let tongno = parseInt(new TraCuuChungFacade().TRACUUNO_THUEBAO(this.thuebao_id))
              //   if (tongno > 0) {
              //     this.$toast.error("Thuê bao " + txtMaTB.Text + " đang nợ " + tongno + " .Bạn không thể thanh lý thuê bao này!")
              //     return false
              //   }
              // }
            }
          }
        }
      } else {
        //vthanhtoan_id = api.map_id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id)
        let input= {
          type:1,
          param: this.thuebao_id
        }
        vthanhtoan_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
        if (vthanhtoan_id != "-1") {
          if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL|| this.dichvuvt_id ==25 || this.dichvuvt_id == 26 || this.dichvuvt_id == DichVuVienThong.METRONET ||
                        this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
            if (this.ts_khong_kt_nocuoc == 1) { return true }
          }
          // TODO await api
          // let no_tt = new TraCuuChungFacade().TRACUUNO_THANHTOAN(+vthanhtoan_id)
          // if (no_tt > 0) {
          //   let tongno = new TraCuuChungFacade().TRACUUNO_THUEBAO(this.thuebao_id)
          //   if (+tongno > 0) {
          //     this.$toast.error("Thuê bao " + this.txtMaTB.Text + " đang nợ " + tongno + " .Bạn không thể thanh lý thuê bao này!")
          //     return false
          //   }
          // }
        }
      }
      return true
    },
    async TaoDuLieu (themmoi) {
      await this.TaoDuLieu_HDKH(themmoi)
      await this.TaoDuLieu_HDTB(themmoi)
      this.TaoDuLieu_LYDOHUY()
      this.dsHDTB_CD = []
      this.dsHDTB_ADSL = []
      this.dsHDTB_TSL = []
      this.dsHDTB_MGW = []
      this.dsHDTB_GP = []
      this.dsHDTB_IMS = []
      this.dsHDTB_CNTT = []
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.DI_DONG) { await this.TaoDuLieu_HDTB_DD(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.MEGA_EYES ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.EMAIL ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.HATANG_VIENTHONG) { await this.TaoDuLieu_HDTB_ADSL(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.CO_DINH) { await this.TaoDuLieu_HDTB_CD(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.GPHONE) { await this.TaoDuLieu_HDTB_GP(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.TSL|| parseInt(this.cboDichVuVT.SelectedValue) == 25) { await this.TaoDuLieu_HDTB_TSL(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.MEGAWAN ||parseInt(this.cboDichVuVT.SelectedValue) == 26) { await this.TaoDuLieu_HDTB_MGWAN(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.METRONET) { await this.TaoDuLieu_HDTB_MGWAN(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.IMS) { await this.TaoDuLieu_HDTB_IMS(themmoi) }
      if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.TRUNGTAM_DULIEU ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.DICHVU_CNTT ||
                this.cboDichVuVT.SelectedValue == DichVuVienThong.HOINGHI_TRUYENHINH) { await this.TaoDuLieu_HDTB_CNTT(themmoi) }
      await this.TaoDuLieu_HDTBDV(themmoi)
    },
    async TaoDuLieu_HDKH (themmoi) {
      let vmagd_temp = ""
      this.dsHDKH = []
      if (themmoi == true) { this.hdkh_id = await api.get_keys("HD_KHACHHANG") }
      let rowHDKH = {}
      // cmUlt.CopyRow(tcdanhba.LAY_DS_DBKH_THEO_KHID(khachhang_id).Tables[0].Rows[0], rowHDKH);
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi == true) {
        if (this.ts_sinhma_gd_theo_donvi) { vmagd_temp = await api.sinh_magd_v2(this.tt_nd.donvi_id, LoaiHopDong.CHAMDUT_SD) } else { vmagd_temp = await api.sinh_magd(LoaiHopDong.CHAMDUT_SD) }
        if (vmagd_temp.includes("ERROR:")) {
          this.$toast.error(vmagd_temp.Split(':')[1])
          this.loi_sinh_magd = true
        }
        this.txtMaGD.Text = vmagd_temp
        rowHDKH.MA_GD = this.txtMaGD.Text
        rowHDKH.MA_HD = this.txtMaHD.Text
      } else {
        rowHDKH.MA_GD = this.txtMaGD.Text
        rowHDKH.MA_HD = this.txtMaHD.Text
      }
      rowHDKH.MA_KH = this.txtMaKH.Text
      if (this.khachhang_id > 0) { rowHDKH.KHACHHANG_ID = this.khachhang_id }
      rowHDKH.TEN_KH = this.txtTenKH.Text
      rowHDKH.DIACHI_KH = this.txtDiaChiKH.Text
      rowHDKH.SO_DT = this.so_dt
      rowHDKH.SO_GT = this.so_gt
      rowHDKH.DONVI_ID = this.tt_nd.donvi_id
      rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id
      if (this.khachhang_id > 0) {
        //const khl_id = await api.map_id("khlon_id", "db_khachhang", "where khachhang_id = " + this.khachhang_id)
        let input= {
          type:1,
          param: this.khachhang_id
        }
        let khl_id = this.GetData(await api.fn_tt_db_khachhang(this.axios,input));
        if (khl_id && khl_id != "-1") { rowHDKH.KHLON_ID = parseInt(khl_id) } else { rowHDKH.KHLON_ID = 1 }
      } else { rowHDKH.KHLON_ID = 1 }
      rowHDKH.LOAIHD_ID = LoaiHopDong.CHAMDUT_SD
      rowHDKH.NGUOI_CN = this.tt_nd.ma_nd
      rowHDKH.NGAY_CN = this.tt_nd.ngay_cn
      rowHDKH.MAY_CN = this.tt_nd.may_cn
      rowHDKH.IP_CN = this.tt_nd.ip
      rowHDKH.NGAY_YC = this.dtpNgayYeuCau.Value
      rowHDKH.NGAYLAP_HD = formatDate(this.dtpNgayLapHD.Value)
      this.dsHDKH.push(rowHDKH)
    },
    async TaoDuLieu_HDTB (themmoi) {
      this.dsHDTB = []
      if (themmoi == true) { this.hdtb_id = await api.get_keys("HD_THUEBAO") }
      let rowHDTB = {}
      if (this.dsDBTB && this.dsDBTB.length > 0) {
        // rowHDTB = {...this.dsDBTB[0]}
        // for (const key in this.dsDBTB[0]) {
        //   if (Object.hasOwnProperty.call(this.dsDBTB[0], key)) {
        //     rowHDTB[key.toUpperCase()] = this.dsDBTB[0][key]
        //   }
        // }
        cmUlt.CopyRowAndUppercase(this.dsDBTB[0], rowHDTB)
        rowHDTB = convertDateKeysFromObject(rowHDTB)
      }
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) { rowHDTB.THUEBAO_ID = this.thuebao_id }
      rowHDTB.TEN_TB = this.txtTenTB.Text
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.GHICHU = this.txtGhiChu.Text
      rowHDTB.KIEULD_ID = parseInt(this.cboKieuLD.SelectedValue)
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.DONVI_ID = this.tt_nd.donvi_id
      rowHDTB.NGUOI_CN = this.tt_nd.ma_nd
      rowHDTB.NGAY_CN = this.tt_nd.ngay_cn
      rowHDTB.MAY_CN = this.tt_nd.may_cn
      rowHDTB.IP_CN = this.tt_nd.ip
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25) {
        let input_d= {
          type:6,
          param: this.thuebao_id
        }
        let donvi_dau_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input_d));
        //let donvi_dau_id = parseInt(await api.map_id("donvi_dau_id", "db_thuebao", "where thuebao_id = " + this.thuebao_id))
        rowHDTB.DONVI_DAU_ID = parseInt(donvi_dau_id)
        let input_c= {
          type:7,
          param: this.thuebao_id
        }
        let donvi_cuoi_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input_c));
        //let donvi_cuoi_id = parseInt(await api.map_id("donvi_cuoi_id", "db_thuebao", "where thuebao_id = " + this.thuebao_id))
        rowHDTB.DONVI_CUOI_ID = parseInt(donvi_cuoi_id)
      }
      if (this.loaitb_id == LoaiHinhTB.PABX) {
        if (this.isdn_sochinh) {
          if (this.hdtb_id_cha > 0) { rowHDTB.HDTB_CHA_ID = this.hdtb_id_cha }
        }
      }
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.ADSL ||
                this.dichvuvt_id == DichVuVienThong.IMS || this.dichvuvt_id == DichVuVienThong.CO_DINH ||
                this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25 || this.dichvuvt_id == 26||
                this.dichvuvt_id == DichVuVienThong.METRONET || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
                this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id == DichVuVienThong.GPHONE || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        rowHDTB.STATUS = "0"
      }
      this.dsHDTB.push(rowHDTB)
    },
    TaoDuLieu_LYDOHUY () {
      // this.dsDKHUY = this.dsDKHUY_TEMP.map(r => ({...r, HDTB_ID: this.hdtb_id}))
      this.dsDKHUY = []
      const allowKeys = ['NGAY_YC', 'LYDOHUY_ID', 'NOIDUNG', 'HDTB_ID']
      for (const row of this.dsDKHUY_TEMP) {
        const rowCopy = {}
        for (const key in row) {
          if (!allowKeys.includes(key.toUpperCase())) {
            continue
          }
          if (Object.hasOwnProperty.call(row, key)) {
            rowCopy[key.toUpperCase()] = row[key]
          }
        }
        rowCopy.HDTB_ID = this.hdtb_id
        rowCopy.NGAY_YC = moment().format(DATE_FORMAT)
        this.dsDKHUY.push(rowCopy)
      }
    },
    TaoDuLieu_HDTB_DD () {
      this.dsHDTB_DD = []
      const rowHDTBD_DD = {}
      rowHDTBD_DD.HDTB_ID = this.hdtb_id
      rowHDTBD_DD.MA_GD_NEO = this.ma_gd_neo
      rowHDTBD_DD.SIMCARD = this.simcard
      rowHDTBD_DD.GOICUOC_ID = this.goicuoc_id
      rowHDTBD_DD.DOITUONG_ID_NEO = this.doituong_id_neo
      rowHDTBD_DD.KIEU_TL = this.kieu_tl
      this.dsHDTB_DD.push(rowHDTBD_DD)
    },
    async TaoDuLieu_HDTB_ADSL () {
      this.dsHDTB_ADSL = []
      let rowHDTB_ADSL = {}
      const dsAdsl = await api.lay_ds_danhba_adsl(this.thuebao_id)
      if (dsAdsl && dsAdsl.length > 0) {
        cmUlt.CopyRowAndUppercase(dsAdsl[0], rowHDTB_ADSL)
      }
      rowHDTB_ADSL.HDTB_ID = this.hdtb_id
      this.dsHDTB_ADSL.push(rowHDTB_ADSL)
    },
    async TaoDuLieu_HDTB_CD (themmoi) {
      this.dsHDTB_CD = []
      let rowHDTB_CD = {}
      const dsDbCd = await api.lay_ds_db_cd_theo_tbid(this.thuebao_id)
      if (dsDbCd && dsDbCd.length > 0) {
        cmUlt.CopyRowAndUppercase(dsDbCd[0], rowHDTB_CD)
      }
      rowHDTB_CD.HDTB_ID = this.hdtb_id
      this.dsHDTB_CD.push(rowHDTB_CD)
    },
    async TaoDuLieu_HDTB_GP () {
      this.dsHDTB_GP = []
      let rowHDTB_GP = {}
      const dsGP = await api.lay_ds_db_gp_theo_tbid(this.thuebao_id)
      if (dsGP && dsGP.length > 0) {
        cmUlt.CopyRowAndUppercase(dsGP[0], rowHDTB_GP)
      }
      rowHDTB_GP.HDTB_ID = this.hdtb_id
      this.dsHDTB_GP.push(rowHDTB_GP)
    },
    async TaoDuLieu_HDTB_TSL () {
      this.dsHDTB_TSL = []
      let rowHDTB_TSL = {}
      const dt = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id,this.$root.token.getPhanVungID())
      for (let i = 0; i < dt.length; i++) {
        const row = dt[i]
        rowHDTB_TSL = {}
        cmUlt.CopyRowAndUppercase(row, rowHDTB_TSL)
        rowHDTB_TSL.HDTB_ID = this.hdtb_id
        rowHDTB_TSL.MA_PL = this.txtMaHD_TTKD.Text
        rowHDTB_TSL.TINH_DC_ID = rowHDTB_TSL.DAUCUOI_ID
        rowHDTB_TSL.ID_ERP = this.txtMaERP.Text
        rowHDTB_TSL.MA_BSS = this.txtMaBSS.Text
        if (this.chkCongVan.Checked) {
          rowHDTB_TSL.MA_CV = this.txtCongVan.Text
        }
        rowHDTB_TSL.NGAY_CV = this.dtpNgayCV.Value
        if (this.chkNgayHT.Checked) { rowHDTB_TSL.NGAY_YCHT = this.dtpNgayHT.Value }
        this.dsHDTB_TSL.push(rowHDTB_TSL)
        console.log('TaoDuLieu_HDTB_TSL')
        console.log(this.dsHDTB_TSL)
      }
    },
    async TaoDuLieu_HDTB_MGWAN () {
      this.dsHDTB_MGW = []
      let rowHDTB_MGW = {}
      const dsMGW = await api.lay_ds_db_megawan_theo_tbid(this.thuebao_id)
      if (dsMGW && dsMGW.length > 0) {
        //rowHDTB_MGW = dsMGW[0]
        cmUlt.CopyRowAndUppercase(dsMGW[0], rowHDTB_MGW)
      }
      rowHDTB_MGW.HDTB_ID = this.hdtb_id
      rowHDTB_MGW.MA_PL = this.txtMaHD_TTKD.Text
      rowHDTB_MGW.MA_BSS = this.txtMaBSS.Text
      rowHDTB_MGW.ID_ERP = this.txtMaERP.Text
      if (this.chkCongVan.Checked) {
        rowHDTB_MGW.MA_CV = this.txtCongVan.Text.Trim()
      }
      rowHDTB_MGW.NGAY_CV = this.dtpNgayHT.Value == null ? null : moment(this.dtpNgayHT.Value, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      if (this.chkNgayHT.Checked) { rowHDTB_MGW.NGAY_YCHT = this.dtpNgayHT.Value }
      this.dsHDTB_MGW.push(rowHDTB_MGW)
    },
    async TaoDuLieu_HDTB_IMS (themmoi) {
      this.dsHDTB_IMS = []
      let rowHDTB_IMS = {} 
      const dsIMS = await api.lay_ds_danhba_ims(this.thuebao_id)
      if (dsIMS && dsIMS.length > 0) {
        cmUlt.CopyRowAndUppercase(dsIMS[0], rowHDTB_IMS)
      }
      rowHDTB_IMS.HDTB_ID = this.hdtb_id
      this.dsHDTB_IMS.push(rowHDTB_IMS)
    },
    async TaoDuLieu_HDTB_CNTT () {
      const dsCntt = await api.lay_ds_danhba_cntt(this.thuebao_id)
      if (dsCntt && dsCntt.length > 0) {
        this.ItemList = []
        cmUlt.CopyRowAndUppercase(dsCntt[0], this.ItemList) 
      }
      this.dsHDTB_CNTT = this.ItemList
    },
    async TaoDuLieu_HDTBDV (themmoi) {
      this.dsHDTBDV = []
      const dtTemp = await api.lay_ds_donvi_theo_tbid(this.thuebao_id) || []
      for (let i = 0; i < dtTemp.length; i++) {
        const row = {}
        row.HDTB_ID = this.hdtb_id
        row.LOAIDV_ID = parseInt(dtTemp[i]["loaidv_id"])
        if (row.LOAIDV_ID == LOAI_DV.TTKD_NGHIEMTHU) { row.DONVI_ID = this.tt_nd.donvi_id } else { row.DONVI_ID = parseInt(dtTemp[i]["donvi_id"]) }
        row.KIEUDV_ID = parseInt(dtTemp[i]["kieudv_id"])
        this.dsHDTBDV.push(row)
      }
    },
    async TaoDuLieu_HDTB_ISDN (themmoi, vthuebao_id, index_tb) {
      this.dsHDTB = []
      if (themmoi) { this.hdtb_id = await api.get_keys("HD_THUEBAO") }
      let rowHDTB = {}
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (vthuebao_id > 0) { rowHDTB.THUEBAO_ID = vthuebao_id }
      if (this.thuebao_id > 0) { rowHDTB.THUEBAO_CHA_ID = this.thuebao_id }
      rowHDTB.MA_TB = this.ds_isdn[index_tb]["ma_tb"]
      rowHDTB.TEN_TB = this.ds_isdn[index_tb]["ten_tb"]
      rowHDTB.DIACHI_TB = this.ds_isdn[index_tb]["diachi_tb"]
      rowHDTB.DIACHI_LD = this.ds_isdn[index_tb]["diachi_ld"]
      rowHDTB.GHICHU = this.ds_isdn[index_tb]["ghichu"]
      rowHDTB.KIEULD_ID = parseInt(this.cboKieuLD.SelectedValue)
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.DONVI_ID = this.donvi_id
      rowHDTB.NGUOI_CN = this.tt_nd.this.ma_nd
      rowHDTB.NGAY_CN = this.tt_nd.this.ngay_cn
      rowHDTB.MAY_CN = this.tt_nd.this.may_cn
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.ADSL ||
                this.dichvuvt_id == DichVuVienThong.IMS || this.dichvuvt_id == DichVuVienThong.CO_DINH) {
        rowHDTB.STATUS = "0"
      }
      this.dsHDTB.push(rowHDTB)
    },
    TaoDuLieu_LYDOHUY_ISDN () {
      // this.dsDKHUY = []
      // for (let i = 0; i < dsDKHUY.length; i++)
      // {
      //     LYDOHUY_HDTB_DATA.LYDOHUY_HDTBRow rowDK_HUY = dsDKHUY.LYDOHUY_HDTB.NewLYDOHUY_HDTBRow();
      //     rowDK_HUY.HDTB_ID = hdtb_id;
      //     rowDK_HUY.LYDOHUY_ID = Convert.ToInt32(dsDKHUY.Tables[0].Rows[i]["lydohuy_id"].ToString());
      //     rowDK_HUY.NOIDUNG = dsDKHUY.Tables[0].Rows[i]["noidung"].ToString();
      //     rowDK_HUY.NGAY_YC = Convert.ToDateTime(dsDKHUY.Tables[0].Rows[i]["ngay_yc"].ToString());
      //     dsDKHUY.LYDOHUY_HDTB.AddLYDOHUY_HDTBRow(rowDK_HUY);
      // }
    },
    async TaoDuLieu_HDTB_CD_ISDN (themmoi, vthuebao_id) {
      this.dsHDTB_CD = []
      let rowHDTB_CD = {}
      const dsDBCD = await api.LAY_DS_DB_CD_THEO_TBID(vthuebao_id)
      if (dsDBCD && dsDBCD.length > 0) {
        cmUlt.CopyRowAndUppercase(dsDBCD[0], rowHDTB_CD)
      }
      rowHDTB_CD.HDTB_ID = this.hdtb_id
      this.dsHDTB_CD.push(rowHDTB_CD)
    },
    async TaoDuLieuBC (themmoi) {
      if (this.tinh.tinhthicong_id != 0 || this.kenh_trang) {
        if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25) {
          this.dsHDBC = []
          this.dsHDTBBC = []
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 1)
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 2)
        } else {
          await this.TaoDuLieu_HDTBBC(themmoi)
        }
        this.TaoDuLieu_LYDOHUYBC()
        this.dsHDBC_ADSL = []
        this.dsHDBC_CD = []
        this.dsHDBC_TSL = []
        this.dsHDBC_IMS = []
        this.dsHDBC_MGW = []
        this.dsHDTB_CNTT = []
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.MEGA_EYES ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.EMAIL ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.HATANG_VIENTHONG) { await this.TaoDuLieu_HDBC_ADSL(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.CO_DINH) { await this.TaoDuLieu_HDBC_CD(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.GPHONE) { await this.TaoDuLieu_HDTB_GP(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.TSL||this.cboDichVuVT.SelectedValue == 25) {
          this.dsHDBC_TSL = []
          await this.TaoDuLieu_HDBC_TSL(themmoi, 1)
          await this.TaoDuLieu_HDBC_TSL(themmoi, 2)
        }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.MEGAWAN||this.cboDichVuVT.SelectedValue == 26) { await this.TaoDuLieu_HDBC_MGWAN(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.METRONET) { await this.TaoDuLieu_HDBC_MGWAN(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.IMS) { await this.TaoDuLieu_HDBC_IMS(themmoi) }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.TRUNGTAM_DULIEU ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.DICHVU_CNTT ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.HOINGHI_TRUYENHINH) { await this.TaoDuLieu_HDTB_CNTT(themmoi) }
      }
    },
    async TaoDuLieu_HDTBBC_TSL (themmoi, vdaucuoi_id) {
      let ds = []
      let rowHDBC = {}
      let tinh_dau_id = 0
      let tinh_cuoi_id = 0
      let thuebao_id_dau = 0
      let thuebao_id_cuoi = 0
      if (!this.kenh_trang) {
        let dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
        tinh_dau_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["tinh_thicong"])
        tinh_cuoi_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["tinh_thicong"])
        thuebao_id_dau = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 1)["thuebao_kn_id"])
        thuebao_id_cuoi = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == 2)["thuebao_kn_id"])
        this.tinh_dau = await api.tinh_thicong(tinh_dau_id)
        this.tinh_cuoi = await api.tinh_thicong(tinh_cuoi_id)
        if (vdaucuoi_id == 1) {
          ds = await api.lay_ds_db_thuebao_theo_tbid(this.tinh_dau.tinhthicong_id, thuebao_id_dau)
          rowHDBC.TINH_THICONG = parseInt(this.tinh_dau.tinhthicong_id)
          rowHDBC.THUEBAO_ID_THICONG = thuebao_id_dau
        } else {
          ds = await api.lay_ds_db_thuebao_theo_tbid(this.tinh_cuoi.tinhthicong_id, thuebao_id_cuoi)
          rowHDBC.TINH_THICONG = parseInt(this.tinh_cuoi.tinhthicong_id)
          rowHDBC.THUEBAO_ID_THICONG = thuebao_id_cuoi
        }
      } else {
        if (vdaucuoi_id == 1) {
          rowHDBC.TINH_THICONG = parseInt(this.tinh_dau.tinhthicong_id)
          rowHDBC.THUEBAO_ID_THICONG = 0
        } else {
          rowHDBC.TINH_THICONG = parseInt(this.tinh_cuoi.tinhthicong_id)
          rowHDBC.THUEBAO_ID_THICONG = 0
        }
      }
      rowHDBC.DAUCUOI_ID = vdaucuoi_id
      
      rowHDBC.HDTB_BAN = this.hdtb_id
      rowHDBC.TINH_BAN = this.tt_nd.tinh_id
      if (this.vkenh_toantrinh == 1)
      {
          rowHDBC.TRANG_THAI = 1;
          rowHDBC.THUEBAO_ID_THICONG = -1;
          rowHDBC.HDTB_THICONG = -1;
      }
      else{
          rowHDBC.TRANG_THAI = 0;
      }
      rowHDBC.LOAIHD_BANCHEO = 1
      rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
      if (this.kenh_trang) {
        rowHDBC.KHACHHANG_ID = null
        rowHDBC.THANHTOAN_ID = null
      } else {
        rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]["thanhtoan_id"])
        rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]["khachhang_id"])
      }
      let input= {
        type:1,
        param: this.thuebao_id
      }
      let mapId = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
      //const mapId = await api.map_id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id)
      rowHDBC.THANHTOAN_ID_BAN = parseInt(mapId)
      rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
      this.dsHDBC.push(rowHDBC)
      // hop dong thue bao ban cheo
      let rowHDTBBC = {}
      if (!this.kenh_trang) {
        console.log(4444444444)
        if (vdaucuoi_id.toString() == '1') {
          console.log(1)
          cmUlt.CopyRowAndUppercase(await api.bancheo_lay_danhba_theo_thuebaoid(tinh_dau_id,thuebao_id_dau,this.dichvuvt_id, 0, 2), rowHDTBBC)
          rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        } else {
          console.log(2) 
          cmUlt.CopyRowAndUppercase(await api.bancheo_lay_danhba_theo_thuebaoid(tinh_cuoi_id,thuebao_id_cuoi, this.dichvuvt_id, 0, 2), rowHDTBBC)
          rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        }
      } else {
         console.log(55555555)
        if (vdaucuoi_id.toString() == '1') {
          cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_dau.muccuoctb_id
          try {
            const muccuocTb = await api.fn_tt_muccuoc_tb(this.$root.token.getPhanVungID(), this.thongso_tc_dau.muccuoctb_id)
            rowHDTBBC.MUCUOC_TB = parseInt(muccuocTb)
          } catch (error) {
          }
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
          rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        } else {
          cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_cuoi.muccuoctb_id
          try {
            const muccuocTb = await api.fn_tt_muccuoc_tb(this.$root.token.getPhanVungID(),this.thongso_tc_cuoi.muccuoctb_id)
            rowHDTBBC.MUCUOC_TB = parseInt(muccuocTb)
          } catch (error) {
          }
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_cuoi.doituong_id
          rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        }
        rowHDTBBC.THUEBAO_ID = null
        rowHDTBBC.DONVI_ID = null
      }
      console.log(rowHDTBBC)
      rowHDTBBC.HDTB_ID = this.hdtb_id
      rowHDTBBC.HDKH_ID = this.hdkh_id
      rowHDTBBC.TTHD_ID = 1
      if (vdaucuoi_id == 1) {
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_dau
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_dau
        rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        rowHDTBBC.KIEULD_ID = this.kieuld_dau
      }
      if (vdaucuoi_id == 2) {
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_cuoi
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_cuoi
        rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        rowHDTBBC.KIEULD_ID = this.kieuld_cuoi
      }
      rowHDTBBC.NGUOI_CN = this.tt_nd.ma_nd
      rowHDTBBC.NGAY_CN = this.tt_nd.ngay_cn
      rowHDTBBC.MAY_CN = this.tt_nd.may_cn
      rowHDTBBC.IP_CN = this.tt_nd.ip
      rowHDTBBC.GHICHU = this.txtGhiChu.Text
      rowHDTBBC.TINH_DC_ID = vdaucuoi_id
      if ((this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25) && !this.kenh_trang) {
        try {
          let input= {
            type:6,
            param: rowHDTBBC.THUEBAO_ID
          }
          let donvi_dau_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
          //const donvi_dau_id = parseInt(await api.map_id("donvi_dau_id", "db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_DAU_ID = parseInt(donvi_dau_id)
        } catch (error) {
        }
        try {
           let input= {
            type:7,
            param: rowHDTBBC.THUEBAO_ID
          }
          let donvi_cuoi_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
          //const donvi_cuoi_id = parseInt(await api.map_id("donvi_cuoi_id","db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
          rowHDTBBC.DONVI_CUOI_ID = parseInt(donvi_cuoi_id)
        } catch (error) {
        }
      }
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.ADSL ||
          this.dichvuvt_id == DichVuVienThong.IMS || this.dichvuvt_id == DichVuVienThong.CO_DINH ||
          this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25 || this.dichvuvt_id == 26 ||
          this.dichvuvt_id == DichVuVienThong.METRONET || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
          this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          this.dichvuvt_id == DichVuVienThong.GPHONE || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        rowHDTBBC.STATUS = "0"
      }
      console.log(111111111111)
      console.log(this.dsHDTBBC)
      this.dsHDTBBC.push(rowHDTBBC)
    },
    async TaoDuLieu_HDTBBC (themmoi) {
      this.dsHDBC = []
      let ds = []
      if (!this.kenh_trang && !this.chi_giao_net) { ds = await api.lay_ds_db_tsl_theo_tbid_bc(this.thuebao_tc_id,this.tinh.tinhthicong_id) || [] }
      let rowHDBC = {}
      rowHDBC.HDTB_BAN = this.hdtb_id
      rowHDBC.TINH_THICONG = this.tinh.tinhthicong_id
      rowHDBC.TINH_BAN = this.tt_nd.tinh_id

      if(this.vkenh_toantrinh == 1){
        rowHDBC.TRANG_THAI = 1;
        rowHDBC.THUEBAO_ID_THICONG = -1;
        rowHDBC.HDTB_THICONG = -1;
      }
      else{
        rowHDBC.TRANG_THAI = 0;
      }

      rowHDBC.LOAIHD_BANCHEO = 1
      rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
      rowHDBC.THUEBAO_ID_THICONG = this.thuebao_tc_id

      if (!this.kenh_trang && !this.chi_giao_net && ds.length > 0) {
        rowHDBC.THANHTOAN_ID_TC = parseInt(ds[0]["thanhtoan_id"])
        rowHDBC.KHACHHANG_ID_TC = parseInt(ds[0]["khachhang_id"])
      } else {
        rowHDBC.THANHTOAN_ID_TC = 0
        rowHDBC.KHACHHANG_ID_TC = 0
      }
      let input= {
          type:1,
          param: this.thuebao_id
      }
      let thanhtoan_id_ban = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
      rowHDBC.THANHTOAN_ID_BAN = parseInt(thanhtoan_id_ban) //parseInt(await api.map_id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id))
      rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
      this.dsHDBC.push(rowHDBC)
      this.dsHDTBBC = []
      let rowHDTBBC = {}
      if (!this.kenh_trang && !this.chi_giao_net) {
        const ds = await api.bancheo_lay_danhba_theo_thuebaoid(this.tinh.tinhthicong_id, parseInt(this.thuebao_tc_id), parseInt(this.cboDichVuVT.SelectedValue), 0, 2)
        if (Object.keys(ds).length !== 0) {
          cmUlt.CopyRowAndUppercase(ds, rowHDTBBC)
          this.isFormValid = true
        } else {
          this.$toast.error("Không lấy được thông tin thuê bao tỉnh thi công, liên hệ admin để xử lý.")
          this.isFormValid = false
        }
        
      } else {
        cmUlt.CopyRowAndUppercase(this.dsHDTB[0], rowHDTBBC)
        rowHDTBBC.MUCUOCTB_ID = this.thongso_tc.muccuoctb_id
        rowHDTBBC.MUCUOC_TB = parseInt(await api.fn_tt_muccuoc_tb(this.tinh.tinhthicong_id,this.thongso_tc.muccuoctb_id))
        rowHDTBBC.DOITUONG_ID = this.thongso_tc.doituong_id
        rowHDTBBC.MA_TB = this.txtMaTB.Text
      }
      rowHDTBBC.NGAYLAP_HD = moment(rowHDTBBC.NGAYLAP_HD, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      rowHDTBBC.NGAY_SN_TB = moment(rowHDTBBC.NGAY_SN_TB, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      rowHDTBBC.NGAY_SN = moment(rowHDTBBC.NGAY_SN, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      rowHDTBBC.NGAY_SD = moment(rowHDTBBC.NGAY_SD, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      rowHDTBBC.NGAYCAP = moment(rowHDTBBC.NGAYCAP, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss');
      rowHDTBBC.HDTB_ID = this.hdtb_id
      rowHDTBBC.HDKH_ID = this.hdkh_id
      rowHDTBBC.TTHD_ID = 1
      rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
      rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
      rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id
      rowHDTBBC.NGUOI_CN = this.tt_nd.ma_nd
      rowHDTBBC.NGAY_CN = moment(this.tt_nd.ngay_cn, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
      rowHDTBBC.MAY_CN = this.tt_nd.may_cn
      rowHDTBBC.IP_CN = this.tt_nd.ip
      rowHDTBBC.GHICHU = this.txtGhiChu.Text
      if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25) {
        let donvi_dau_id = parseInt(await api.map_id(this.tinh.tinhthicong_id, "donvi_dau_id","css.db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
        rowHDTBBC.DONVI_DAU_ID = donvi_dau_id
        let donvi_cuoi_id = parseInt(await api.map_id(this.tinh.tinhthicong_id, "donvi_cuoi_id","css.db_thuebao", "where thuebao_id = " + rowHDTBBC.THUEBAO_ID))
        rowHDTBBC.DONVI_CUOI_ID = donvi_cuoi_id
      }
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.ADSL ||
                this.dichvuvt_id == DichVuVienThong.IMS || this.dichvuvt_id == DichVuVienThong.CO_DINH ||
                this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25 || this.dichvuvt_id == 26 ||
                this.dichvuvt_id == DichVuVienThong.METRONET || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
                this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id == DichVuVienThong.GPHONE || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        rowHDTBBC.STATUS = "0"
      }
      console.log(333333)
      console.log(rowHDTBBC)
      this.dsHDTBBC.push(rowHDTBBC)
      console.log(this.dsHDTBBC)
    },
    TaoDuLieu_LYDOHUYBC () {
      this.dsDKHUYBC = []
      for (const row of this.dsDKHUY_TEMP) {
        const rowCopy = {}
        for (const key in row) {
          if (Object.hasOwnProperty.call(row, key)) {
            rowCopy[key.toUpperCase()] = row[key]
          }
        }
        rowCopy.HDTB_ID = this.hdtb_id
        rowCopy.NGAY_YC = moment(this.tt_nd.ngay_cn, 'DD/MM/YYYY').format('DD/MM/YYYY HH:mm:ss')
        this.dsDKHUYBC.push(rowCopy)
      }
    },
    async TaoDuLieu_HDBC_ADSL (themmoi) {
      this.dsHDBC_ADSL = []
      let rowHDBC_ADSL = {}
      const ds = await api.lay_ds_db_adsl_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds && ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_ADSL)
        rowHDBC_ADSL.HDTB_ID = this.hdtb_id
        this.dsHDBC_ADSL.push(rowHDBC_ADSL)
      }
    },
    async TaoDuLieu_HDBC_CD (themmoi) {
      this.dsHDBC_CD = []
      let rowHDBC_CD = {}
      const ds = await api.lay_ds_db_cd_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds && ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_CD)
        rowHDBC_CD.HDTB_ID = this.hdtb_id
        this.dsHDBC_CD.push(rowHDBC_CD)
      }
    },
    async TaoDuLieu_HDBC_TSL (themmoi, vdaucuoi_id) {      
      let tinh_id=0
      let thuebao_tc_id=0
      let tinh={}
      if (!this.kenh_trang){
          //region Kenh thuong          
          let dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.dichvuvt_id)
          tinh_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == vdaucuoi_id)["tinh_thicong"])          
          thuebao_tc_id = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == vdaucuoi_id)["thuebao_kn_id"])
          tinh = await api.tinh_thicong(tinh_id)
          let rowHDBC_TSL = {}          
          let dt_ban = await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id,this.$root.token.getPhanVungID())
          let dt_tc= await api.lay_ds_db_tsl_theo_tbid_bc(thuebao_tc_id,tinh.tinhthicong_id)
          console.log(dt_tc)
          if (dt_tc.length>0){
            for (let i=0;i< dt_tc.length;i++ )
            {
                rowHDBC_TSL = {}
                cmUlt.CopyRowAndUppercase(dt_tc[i], rowHDBC_TSL)
                rowHDBC_TSL.HDTB_ID = this.hdtb_id
                rowHDBC_TSL.MA_PL = this.txtMaHD.Text.trim()
                rowHDBC_TSL.TINH_DC_ID = vdaucuoi_id
                rowHDBC_TSL.TINH_ID = parseInt(dtTinh_id_tsl.find(r => r["daucuoi_id"] == dt_tc[i].daucuoi_id)["tinh_thicong"])
                
                rowHDBC_TSL.NGAY_YCHT = this.tt_nd.ngay_cn
                rowHDBC_TSL.DIACHI_ID = parseInt(dt_ban.find(r => r["daucuoi_id"] == vdaucuoi_id)["diachi_id"])
                
                this.dsHDBC_TSL.push(rowHDBC_TSL)
                
            }
          }
          //endregion
      }
      else
      {
          //region kenh trang
          let row_count = 0;
          row_count = this.dsHDTB_TSL.length
          let dt_diachi= await api.lay_ds_db_tsl_theo_tbid(this.thuebao_id,this.$root.token.getPhanVungID())
          
          for (let i = 0; i < row_count; i++)
          {
              let rowHDBCTSL = {}
             
              cmUlt.CopyRowAndUppercase(this.dsHDTB_TSL[i], rowHDBCTSL);
              if (vdaucuoi_id == 1)
              {
                  rowHDBCTSL.LOAIMD_ID = this.thongso_tc_dau.loaimd_id;
                  rowHDBCTSL.DAUCUOI_ID == 1
                  if (this.thongso_tc_dau.chuquan_id == 0)
                      rowHDBCTSL.CHUQUAN_ID = null
                  else
                      rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_dau.chuquan_id
                  if (this.thongso_tc_dau.loaikenh_id == 0)
                      rowHDBCTSL.LOAIKENH_ID = null
                  else
                      rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_dau.loaikenh_id
                  if (this.thongso_tc_dau.thietbidc_id == 0)
                      rowHDBCTSL.THIETBIDC_ID = null
                  else
                      rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_dau.thietbidc_id
                  if (this.thongso_tc_dau.tocdo_id == 0)
                      rowHDBCTSL.TOCDO_ID = null
                  else
                      rowHDBCTSL.TOCDO_ID = this.thongso_tc_dau.tocdo_id
                  if (this.thongso_tc_dau.trangbi_id == 0)
                      rowHDBCTSL.TRANGBI_ID = null
                  else
                      rowHDBCTSL.TRANGBI_ID = this.thongso_tc_dau.trangbi_id
                  rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_dau.chuquanld_id
              }
              else
              {
                  rowHDBCTSL.LOAIMD_ID = this.thongso_tc_cuoi.loaimd_id
                  rowHDBCTSL.DAUCUOI_ID == 2
                  if (this.thongso_tc_cuoi.chuquan_id == 0)
                      rowHDBCTSL.CHUQUAN_ID  = null
                  else
                      rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_cuoi.chuquan_id
                  if (this.thongso_tc_cuoi.loaikenh_id == 0)
                      rowHDBCTSL.LOAIKENH_ID = null
                  else
                      rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_cuoi.loaikenh_id
                  if (this.thongso_tc_cuoi.thietbidc_id == 0)
                      rowHDBCTSL.THIETBIDC_ID = null
                  else
                      rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_cuoi.thietbidc_id
                  
                  if (this.thongso_tc_cuoi.tocdo_id == 0)
                      rowHDBCTSL.TOCDO_ID = null
                  else
                      rowHDBCTSL.TOCDO_ID = this.thongso_tc_cuoi.tocdo_id
                  if (this.thongso_tc_cuoi.trangbi_id == 0)
                      rowHDBCTSL.TRANGBI_ID = null
                  else
                      rowHDBCTSL.TRANGBI_ID = this.thongso_tc_cuoi.trangbi_id
                  rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_cuoi.chuquanld_id
              }
              if (vdaucuoi_id == 1)
                  if (this.thongso_tc_dau.loaimd_id != 0)
                      rowHDBCTSL.LOAIMD_ID = this.thongso_tc_dau.loaimd_id
                  else
                      rowHDBCTSL.LOAIMD_ID = null
              else
                  if (this.thongso_tc_cuoi.loaimd_id != 0)
                      rowHDBCTSL.LOAIMD_ID = this.thongso_tc_cuoi.loaimd_id
                  else
                      rowHDBCTSL.LOAIMD_ID = null
              rowHDBCTSL.DIACHI_ID = parseInt(dt_diachi.find(r => r["daucuoi_id"] == vdaucuoi_id).diachi_id)
              //Convert.ToInt64(dt_diachi.Select("daucuoi_id = " + vdaucuoi_id)[0]["diachi_id"].ToString());
              rowHDBCTSL.HDTB_ID = this.hdtb_id
              rowHDBCTSL.TINH_DC_ID = vdaucuoi_id
              if (vdaucuoi_id == 1)
                  rowHDBCTSL.TINH_ID = this.tinh_dau.tinhthicong_id
              else
                  rowHDBCTSL.TINH_ID = this.tinh_cuoi.tinhthicong_id
              this.dsHDBC_TSL.push(rowHDBCTSL)              
          }
          
      }
    },
    async TaoDuLieu_HDBC_MGWAN (themmoi) {
      this.dsHDBC_MGW = []
      let rowHDBC_MGW = {}
      console.log(555555555555)
      console.log(this.kenh_trang)
      console.log(this.chi_giao_net)
      if (!this.kenh_trang && !this.chi_giao_net) {
        const ds = await api.lay_ds_db_megawan_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
        if (ds && ds.length > 0) {
          cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_MGW)
          rowHDBC_MGW.NGAY_CV = rowHDBC_MGW.NGAY_CV?moment(rowHDBC_MGW.NGAY_CV, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):null;
        }
      } else {
        rowHDBC_MGW.MADOICAP = -1
        rowHDBC_MGW.LOAIMD_ID = this.thongso_tc.loaimd_id
        if (this.thongso_tc.chuquan_id == 0) { rowHDBC_MGW.CHUQUAN_ID = null } else { rowHDBC_MGW.CHUQUAN_ID = this.thongso_tc.chuquan_id }
        if (this.thongso_tc.loaikenh_id == 0) { rowHDBC_MGW.LOAIKENH_ID = null } else { rowHDBC_MGW.LOAIKENH_ID = this.thongso_tc.loaikenh_id }
        if (this.thongso_tc.thietbidc_id == 0) { rowHDBC_MGW.THIETBIDC_ID = null } else { rowHDBC_MGW.THIETBIDC_ID = this.thongso_tc.thietbidc_id }
        if (this.thongso_tc.thoihan_id == 0) { rowHDBC_MGW.THOIHAN_ID = null } else { rowHDBC_MGW.THOIHAN_ID = this.thongso_tc.thoihan_id }
        if (this.thongso_tc.tocdo_id == 0) { rowHDBC_MGW.TOCDO_ID = null } else { rowHDBC_MGW.TOCDO_ID = this.thongso_tc.tocdo_id }
        if (this.thongso_tc.tocdo_isp == 0) { rowHDBC_MGW.TOCDO_ISP = null } else { rowHDBC_MGW.TOCDO_ISP = parseInt(this.thongso_tc.tocdo_isp) }
        if (this.thongso_tc.tocdo_nix == 0) { rowHDBC_MGW.TOCDO_NIX = null } else { rowHDBC_MGW.TOCDO_NIX = parseInt(this.thongso_tc.tocdo_nix) }
        if (this.thongso_tc.tocdo_pir_id == 0) { rowHDBC_MGW.TOCDO_PIR_ID = null } else { rowHDBC_MGW.TOCDO_PIR_ID = parseInt(this.thongso_tc.tocdo_pir_id) }
        if (this.thongso_tc.trangbi_id == 0) { rowHDBC_MGW.TRANGBI_ID = null } else { rowHDBC_MGW.TRANGBI_ID = this.thongso_tc.trangbi_id }
        rowHDBC_MGW.CHUQUANLD_ID = this.thongso_tc.chuquanld_id
      }
      rowHDBC_MGW.HDTB_ID = this.hdtb_id
      rowHDBC_MGW.MA_PL = this.txtMaHD_TTKD.Text
      rowHDBC_MGW.ID_ERP = this.txtMaERP.Text
      rowHDBC_MGW.MA_BSS = this.txtMaBSS.Text
      if (this.chkNgayHT.Checked) { rowHDBC_MGW.NGAY_YCHT = this.dtpNgayHT.Value }
      this.dsHDBC_MGW.push(rowHDBC_MGW)
    },
    async TaoDuLieu_HDBC_IMS (themmoi) {
      this.dsHDBC_IMS = []
      let rowHDBC_IMS = {}
      const ds = await api.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds && ds.length > 0) {
        cmUlt.CopyRowAndUppercase(ds[0], rowHDBC_IMS)
      }
      rowHDBC_IMS.HDTB_ID = this.hdtb_id
      this.dsHDBC_IMS.push(rowHDBC_IMS)
    },
    TaoDuLieu_BD_LYDOHUY (bdtb_id) {
      // dsBD_LYDOHUY = new BD_LYDOHUY_DATA();
      // DataSet ds = new DataSet();
      // ds = new TraCuuDanhBaFacade().LAY_DS_DB_LYDOHUY_THEO_TBID(thuebao_id);
      // for (int i = 0; i < ds.Tables[0].Rows.Count; i++)
      // {
      //     BD_LYDOHUY_DATA.BD_LYDOHUYRow rowBD_LYDOHUY = dsBD_LYDOHUY.BD_LYDOHUY.NewBD_LYDOHUYRow();
      //     rowBD_LYDOHUY.THUEBAO_ID = Convert.ToInt32(ds.Tables[0].Rows[i]["thuebao_id"].toString());
      //     rowBD_LYDOHUY.BDTB_ID = bdtb_id;
      //     rowBD_LYDOHUY.LYDOHUY_ID = Convert.ToInt32(ds.Tables[0].Rows[i]["lydohuy_id"].toString());
      //     rowBD_LYDOHUY.NOIDUNG = ds.Tables[0].Rows[i]["noidung"].toString();
      //     rowBD_LYDOHUY.NGAY_DK = Convert.ToDateTime(ds.Tables[0].Rows[i]["ngay_dk"].toString());
      //     dsBD_LYDOHUY.BD_LYDOHUY.AddBD_LYDOHUYRow(rowBD_LYDOHUY);
      // }
    },
    async ThemHDThueBao () {
      if (this.chkThuHoi.Checked == false) {
        const cf = await this.$bvModal.msgBoxConfirm("Bạn chắc chắn không thu hồi thiết bị của khách hàng tại điểm giao dịch?", {
          okTitle: 'Có',
          cancelTitle: 'Không'
        })
        if (!cf) return false
      }
      const kthd = await api.kiemtra_hdtb_khi_themmoi({
        "dichVuVtId": this.dichvuvt_id,
        "hdkhId": this.hdkh_id,
        "loaiTbId": this.loaitb_id,
        "maTb": this.txtMaTB.Text,
        "thuebaoId": this.thuebao_id
      })
      if (kthd != 0) {
        this.$toast.error("Thuê bao đã tồn tại trong hợp đồng, không thêm mới được !")
        return false
      }
      const rsKt = await api.kiemtra_capnhat_chamdutsd_bancheo({
        "MA_GD": this.txtMaGD.Text,
        "MA_GD_NEO": this.ma_gd_neo,
        "MA_TB": this.txtMaTB.Text,
        "THUEBAO_ID": this.thuebao_id,
        "LUONG_ID": this.luong_id,
        "NHANVIEN_ID": this.tt_nd.nhanvien_id,
        "DONVI_ID": this.tt_nd.donvi_id,
        "NGUOI_CN": this.tt_nd.ma_nd,
        "MAY_CN": '', // tt_nd.may_cn
        "IP_CN": '', // tt_nd.ip
        "LOAITB_ID": this.loaitb_id,
        "LOAITB_ID_CBO": parseInt(this.cboLoaihinhTB.SelectedValue),
        "DICHVUVT_ID": this.dichvuvt_id,
        "DICHVUVT_ID_CBO": parseInt(this.cboDichVuVT.SelectedValue),
        "DOITUONG_ID": this.doituong_id,
        "LOAIHD_ID": this.loaihd_id,
        "HDTB_ID": this.hdtb_id,
        "HDKH_ID": this.hdkh_id,
        "KT_KENHPHU_AO": this.kt_kenhphu_ao,
        "KIEULD_ID": parseInt(this.cboKieuLD.SelectedValue),
        "H_THUEBAO_CHA_ID": this.h_thuebao_cha_id,
        "NHAPMOI": this.tsbtnNhapMoi.Enabled ? 1 : 0,
        "MUCCUOC_ID": this.muccuoc_id,
        "TOCDO_ID": this.tocdo_id,
        "CHKTHUHOI": this.chkThuHoi.Checked ? 1 : 0,
        "CKBKHONG_TH": this.chkThuHoi.Checked ? 0 : 1,
        "CHKCONGVAN": this.chkCongVan.Checked ? 1 : 0,
        "CHKNGAYHT": this.chkNgayHT.Checked ? 1 : 0,
        "MA_PL": this.txtMaHD_TTKD.Text,
        "MA_BSS": this.txtMaBSS.Text,
        "ID_ERP": this.txtMaERP.Text,
        "MA_CV": this.txtCongVan.Text,
        "NGAY_CV": this.dtpNgayCV.Value,
        "NGAY_YCHT": this.dtpNgayHT.Value,
        "KHACHHANG_ID": this.khachhang_id
      })
      if (rsKt.data != 1) {
        this.$toast.error(rsKt.data)
        return false
      }
      const rsLapHDKhac = await api.WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.txtMaTB.Text, this.txtMaGD.Text, parseInt(this.cboDichVuVT.SelectedValue), LoaiHopDong.CHAMDUT_SD)
      if (rsLapHDKhac && typeof rsLapHDKhac === 'object') {
        this.$toast.error(`Thuê bao ${rsLapHDKhac.ma_tb} đã lập hợp đồng khác, tên hợp đồng: ${rsLapHDKhac.ten_loaihd}`)
        return false
      }
      let rsCungMaKH = 0
      try {
        rsCungMaKH = await api.kiemtra_tb_cung_makh_tb_pl(this.txtMaGD.Text, this.thuebao_id)
      } catch (error) {
        console.error(error)
      }
      if (rsCungMaKH == 1) {
        let vmc_id = 0
        if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                             this.loaitb_id == LoaiHinhTB.WIFI_FIBER || this.loaitb_id == LoaiHinhTB.INTERNET_1260) { vmc_id = this.muccuoc_id }
        let dset = []
        this.vkenh_toantrinh = await this.lay_ds_kenhtoantrinh_theoTB(this.thuebao_id)
        if(this.vkenh_toantrinh == 1){
          // this.vloaikenh_id = LOAIKENH.NOIHAT 
          this.vloaikenh_id = 8 // LOAIKENH.NOIHAT
        }
        // dset = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.CHAMDUT_SD, loaitb_id, tocdo_id, vmc_id, Convert.ToInt32(cboKieuLD.SelectedValue));
        dset = await api.layhuonggiao_tiepnhan_v2(LoaiHopDong.CHAMDUT_SD, this.loaitb_id, this.tocdo_id, vmc_id, parseInt(this.cboKieuLD.SelectedValue), this.vloaikenh_id, parseInt(this.cboDichVuVT.SelectedValue))
        let huonggiao_tn = 0
        this.quytrinh_id = 0
        if (!dset || dset.length <= 0) {
          this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
          return false
        }
        huonggiao_tn = parseInt(dset[0]["huonggiao_id"])
        this.quytrinh_id = parseInt(dset[0]["quytrinh_id"])
              // BAN CHEO, API XU LY
        let tocdo_tc = 0
        let muccuoc_id_tc = 0
        if (this.dichvuvt_id != DichVuVienThong.CO_DINH && this.dichvuvt_id != DichVuVienThong.IMS &&
                    this.dsDBTBBC && this.dsDBTBBC.length > 0 && this.kenh_trang == false) {
          if (!this.chi_giao_net) {
            tocdo_tc = this.dsDBTBBC[0]["tocdo_id"]
          } else {
            tocdo_tc = this.thongso_tc.tocdo_id
          }
          if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
            this.loaitb_id == LoaiHinhTB.WIFI_FIBER || this.loaitb_id == LoaiHinhTB.INTERNET_1260 ||
            this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
            muccuoc_id_tc = this.dsDBTBBC[0]["muccuoc_id"]
          }
        }
        let rs_qt = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh.tinhthicong_id, LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue), tocdo_tc, muccuoc_id_tc, this.Kieuld_tc_id,this.vloaikenh_id,this.dichvuvt_id) //this.cboKieuLD.SelectedValue)
        if (!rs_qt || rs_qt.length <= 0) {
            this.loading(false)
            this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý")
            return
        }
        this.quytrinh_tc_tn = rs_qt[0]["quytrinh_id"]
        this.huonggiao_tc_tn = rs_qt[0]["huonggiao_id"]        
        /// Nếu thuê bao đang tạm dừng là số chính ( thuebao_cha_id null)= > Tạm dừng cho tất cả các số phụ của nó
        if (parseInt(this.cboLoaihinhTB.SelectedValue) == LoaiHinhTB.ISDN2B_CD ||
                            parseInt(this.cboLoaihinhTB.SelectedValue) == LoaiHinhTB.ISDN2B_CQ ||
                            parseInt(this.cboLoaihinhTB.SelectedValue) == LoaiHinhTB.ISDN30B_CD ||
                            parseInt(this.cboLoaihinhTB.SelectedValue) == LoaiHinhTB.ISDN30B_CQ) {
          this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
          if (this.isdn_sochinh) {
            this.ds_isdn = this.ds_isdn || []
            for (let i = 0; i < this.ds_isdn.length; i++) {
              /// Kiểm tra trong danh sách thuê bao thuộc số chính có thuê nào lập phụ lục khác phụ lục này không.
              ///     Nếu có lập hợp đồng khác và chưa hoàn thiện => không cho làm
              let kt = true
              let ds = await api.kiemtra_ma_tb_lap_hopdong_khac(this.ds_isdn[i]["ma_tb"], this.txtMaGD.Text, this.dichvuvt_id, LoaiHopDong.CHAMDUT_SD)
              if (ds && ds.length > 0) {
                kt = false
              }
              if (!kt) {
                this.$toast.error("Thuê bao: " + this.ds_isdn[i]["ma_tb"] +
                                                        " đang được lập phụ lục khác và chưa hoàn thành!\n Bạn không thể thực hiện tiếp. ")
                return false
              }
            }
            this.loading(true)
            await this.TaoDuLieu_HDTB(true)
            await this.TaoDuLieu_HDTBDV(true)
            await this.TaoDuLieu_LYDOHUY()
            this.dsHDTB_CD = []
            await this.TaoDuLieu_HDTB_CD(true)
            this.TaoDuLieuBC(true)
            await api.themhdthuebao_chamdutsd_bancheo(this.dsHDTB, this.dsHDTB_DD, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL,
              this.dsHDTB_IMS, this.dsDKHUY, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn)
            // BAN CHEO API XU LY
            if (this.tinh.tinhthicong_id > 0){
                await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn)
            } 
            await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
            this.ds_isdn = this.ds_isdn || []
            for (let i = 0; i < this.ds_isdn.length; i++) {
              let ds = []
              if (parseInt(this.ds_isdn[i]["trangthaitb_id"]) == TrangThaiTB.THUONG &&
                                        this.ds_isdn[i]["thuebao_cha_id"] != "") {
                /// do kiểm tra ở trên thuê bao ko lập phụ lục nào khác với phụ lục hiện tại
                /// => kiểm tra tiếp thuê bao có đang thuộc phụ lục này ko
                ds = await api.kiemtra_ma_tb_lap_hopdong_khac(this.ds_isdn[i]["ma_tb"],
                  "0", this.dichvuvt_id, LoaiHopDong.CHAMDUT_SD) || []
                if (ds.length > 0 && ds[0]["ma_gd"] == this.txtMaGD.Text) {
                  // Thuê bao thuộc phụ lục => Không làm gì cả
                } else { // Nếu không thuộc phụ lục thì thực hiện tiếp
                  await this.TaoDuLieu_HDTB_ISDN(true, parseInt(this.ds_isdn[i]["thuebao_id"]), i)
                  await this.TaoDuLieu_LYDOHUY()
                  await this.TaoDuLieu_HDTB_CD_ISDN(true, parseInt(this.ds_isdn[i]["thuebao_id"]))
                  await this.TaoDuLieu_HDTBDV(true)
                  await api.capnhat_idsn_chamdutsd_bancheo(this.dsHDTB, this.dsDKHUY, this.dsHDTB_CD, this.dsHDTBDV, huonggiao_tn)
                  await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
                }
              }
            }
            this.loading(false)
          } else {
            this.loading(true)
            await this.TaoDuLieu_HDTB(true)
            await this.TaoDuLieu_LYDOHUY()
            await this.TaoDuLieu_HDTB_CD(true)
            await this.TaoDuLieu_HDTBDV(true)
            await this.TaoDuLieuBC(true)
            await api.themhdthuebao_chamdutsd_bancheo(this.dsHDTB, this.dsHDTB_DD, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL,
              this.dsHDTB_IMS, this.dsDKHUY, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn)
            if (this.tinh.tinhthicong_id > 0){
                await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn)
            }
            await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
            this.loading(false)
          }
        } else {
          this.loading(true)
          await this.TaoDuLieu_HDTB(true)
          await this.TaoDuLieu_LYDOHUY()
          this.dsHDTB_ADSL = []
          this.dsHDTB_CD = []
          this.dsHDTB_GP = []
          this.dsHDTB_TSL = []
          this.dsHDTB_MGW = []
          this.dsHDTB_DD = []
          this.dsHDTB_IMS = []
          this.dsHDTB_CNTT = []
          await this.TaoDuLieuBC(true)
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.ADSL || parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.MEGA_EYES) { await this.TaoDuLieu_HDTB_ADSL(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.CO_DINH) { await this.TaoDuLieu_HDTB_CD(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.GPHONE) { await this.TaoDuLieu_HDTB_GP(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.TSL||parseInt(this.cboDichVuVT.SelectedValue) == 25) { await this.TaoDuLieu_HDTB_TSL(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.MEGAWAN||parseInt(this.cboDichVuVT.SelectedValue) == 26) { await this.TaoDuLieu_HDTB_MGWAN(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.METRONET) { await this.TaoDuLieu_HDTB_MGWAN(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.DI_DONG) { await this.TaoDuLieu_HDTB_DD(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.IMS) { await this.TaoDuLieu_HDTB_IMS(true) }
          if (parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                                parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.TRUNGTAM_DULIEU ||
                                parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.DICHVU_CNTT ||
                                parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.HOINGHI_TRUYENHINH) { await this.TaoDuLieu_HDTB_CNTT(true) }
          await this.TaoDuLieu_HDTBDV(true)
          await api.themhdthuebao_chamdutsd_bancheo(this.dsHDTB, this.dsHDTB_DD, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL,
            this.dsHDTB_IMS, this.dsDKHUY, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn)
          if (this.tinh.tinhthicong_id > 0){
              await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn);
          }
                
          // bỏ qua, bán chéo ko gọi CCBS webservice
          // if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA && parseInt(cboDichVuVT.SelectedValue) == DichVuVienThong.DI_DONG)
          // {
          //     let ds_thuebao = this.thuebao_id.toString()
          //     let kq;
          //     let _ds = await api.lay_tt_tb_dd(ds_thuebao)
          //     if (_ds && _ds.length > 0)
          //     {
          //         CCBSFacade ccbs = new CCBSFacade();
          //         kq = ccbs.TS_LAPHD_THANHLY(tt_nd.USER_NEO, txtMaGD.Text.Trim(), "", dtpNgayYeuCau.Value.ToString("dd/MM/yyyy"),
          //             _ds.Tables[0].Rows[0]["ma_tb"].ToString(), _ds.Tables[0].Rows[0]["ma_tt_neo"].ToString(),
          //             _ds.Tables[0].Rows[0]["lydohuy"].ToString(), _ds.Tables[0].Rows[0]["diachi_ct"].ToString(), _ds.Tables[0].Rows[0]["diachi_ct"].ToString()
          //             , _ds.Tables[0].Rows[0]["ten_tt"].ToString(), _ds.Tables[0].Rows[0]["ten_tb"].ToString(), _ds.Tables[0].Rows[0]["diachi_ld"].ToString(), tt_nd.tentat_ccbs);
          //         if (kq != "OK")
          //         {
          //             Message_Box.ShowTB(kq);
          //             return;
          //         }
          //         Message_Box.ShowTB("Cập nhật thông tin ccbs thành công");
          //     }
          // }
          await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
          if ((parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.DI_DONG) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.ADSL) &&
                                (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.IMS) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.CO_DINH)) {
            await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.KHONGPHAI_DB)
          }
          this.loading(false)
        }
      } else {
        this.$toast.error("Thuê bao không cùng mã khách hàng với thuê bao đã thanh lý !")
        return false
      }
      return true
    },
    async CapNhat () {
      try {
        // let KiemTraDL_KhachHang = await this.KiemTraDL_KhachHang()
        // let KiemTraDL_ThueBao = await this.KiemTraDL_ThueBao()
        // if (!KiemTraDL_KhachHang) return
        // if (!KiemTraDL_ThueBao) return
        const rsKt = await api.kiemtra_capnhat_chamdutsd_bancheo({
          "MA_GD": this.txtMaGD.Text,
          "MA_GD_NEO": this.ma_gd_neo,
          "MA_TB": this.txtMaTB.Text,
          "THUEBAO_ID": this.thuebao_id,
          "LUONG_ID": this.luong_id,
          "NHANVIEN_ID": this.tt_nd.nhanvien_id,
          "DONVI_ID": this.tt_nd.donvi_id,
          "NGUOI_CN": this.tt_nd.ma_nd,
          "MAY_CN": '', // tt_nd.may_cn
          "IP_CN": '', // tt_nd.ip
          "LOAITB_ID": this.loaitb_id,
          "LOAITB_ID_CBO": parseInt(this.cboLoaihinhTB.SelectedValue),
          "DICHVUVT_ID": this.dichvuvt_id,
          "DICHVUVT_ID_CBO": parseInt(this.cboDichVuVT.SelectedValue),
          "DOITUONG_ID": this.doituong_id,
          "LOAIHD_ID": this.loaihd_id,
          "HDTB_ID": this.hdtb_id,
          "HDKH_ID": this.hdkh_id,
          "KT_KENHPHU_AO": this.kt_kenhphu_ao,
          "KIEULD_ID": parseInt(this.cboKieuLD.SelectedValue),
          "H_THUEBAO_CHA_ID": this.h_thuebao_cha_id,
          "NHAPMOI": this.tsbtnNhapMoi.Enabled ? 1 : 0,
          "MUCCUOC_ID": this.muccuoc_id,
          "TOCDO_ID": this.tocdo_id,
          "CHKTHUHOI": this.chkThuHoi.Checked ? 1 : 0,
          "CKBKHONG_TH": this.chkThuHoi.Checked ? 0 : 1,
          "CHKCONGVAN": this.chkCongVan.Checked ? 1 : 0,
          "CHKNGAYHT": this.chkNgayHT.Checked ? 1 : 0,
          "MA_PL": this.txtMaHD_TTKD.Text,
          "MA_BSS": this.txtMaBSS.Text,
          "ID_ERP": this.txtMaERP.Text,
          "MA_CV": this.txtCongVan.Text,
          "NGAY_CV": this.dtpNgayCV.Value,
          "NGAY_YCHT": this.dtpNgayHT.Value,
          "KHACHHANG_ID": this.khachhang_id
        })
        if (rsKt.data != 1) {
          this.$toast.error(rsKt.data)
          return
        }
        if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
          let dtKtra = api.Kiemtra_tb_mytv_chinhphu(this.txtMaTB.Text, 1)
          if (dtKtra && dtKtra.length > 0) {
            let dsTS = await api.LAY_DS_THAMSO_MD_MATS("KHONG_KIEMTRA_MYTV_CHINHPHU")
            let khong_ktra = -1
            if (dsTS && dsTS.length > 0) {
              khong_ktra = dsTS[0]["ten_ts"]
            }
            if (khong_ktra == 1) {
              const cf = await this.$bvModal.msgBoxConfirm("Thuê bao " + this.txtMaTB.Text + " đang là thuê bao MyTV chính của thuê bao: " +
                     dtKtra[0]['ma_tb_con'] + ". Bạn có đồng ý tiếp tục?", {
                okTitle: 'Có',
                cancelTitle: 'Không'
              })
              if (!cf) return
            } else {
              this.$toast.error("Thuê bao " + this.txtMaTB.Text + " đang là thuê bao MyTV chính của thuê bao: " +
                    dtKtra[0]['ma_tb_con'] + ". Yêu cầu hủy chính phụ trước khi thực hiện!")
              return
            }
          }
        }
        const dsKtra2 = await api.lay_ngay_ktck_theo_thuebao_id(this.thuebao_id)
        if (dsKtra2 && dsKtra2.length > 0) {
          if (parseInt(dsKtra2[0]['sn']) > 0) {
            const cf = await this.$bvModal.msgBoxConfirm("Thuê bao " + this.txtMaTB.Text + " đang có ngày kết thúc cam kết (" +
                        dsKtra2[0]['ngayktck'] + "). Bạn có đồng ý tiếp tục?", {
              okTitle: 'Có',
              cancelTitle: 'Không'
            })
            if (!cf) return
          }
        }
        this.loading(true)
        if (this.tsbtnNhapMoi.Enabled == false) {
          let dsTS1 = await api.LAY_DS_THAMSO_MD_MATS("KIEMTRA_TTPH_TAMDUNG_ROIMANG")
          if (dsTS1 && dsTS1.length > 0) {
            if (dsTS1[0]["ten_ts"] == 1) {
              var dskt1 = await api.lay_ds_ttph_kh_tamdung_roimang(this.thuebao_id)
              if (dskt1 && dskt1.length <= 0) {
                this.loading(false)
                this.$toast.error("Thuê bao chưa nhập hoặc chưa hoàn thành thông tin phản hồi tạm ngừng/rời mạng theoCV80!")
                return
              }
            }
          }
          let rs = await api.WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.txtMaTB.Text, this.ma_gd_neo, parseInt(this.cboDichVuVT.SelectedValue), LoaiHopDong.CHAMDUT_SD)
          if (rs) {
            this.loading(false)
            this.$toast.error(`Thuê bao ${rs.ma_tb} đã lập hợp đồng khác, tên hợp đồng: ${rs.ten_loaihd}`)
            return
          } else {
            let rs1 = await api.WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.txtMaTB.Text, "0", parseInt(this.cboDichVuVT.SelectedValue), LoaiHopDong.CHAMDUT_SD)
            if (rs1) {
              this.loading(false)
              this.$toast.error(`Thuê bao ${rs1.ma_tb} đã lập hợp đồng khác, tên hợp đồng: ${rs1.ten_loaihd}`)
              return
            }
            let vmc_id = 0
            if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                  this.loaitb_id == LoaiHinhTB.WIFI_FIBER ||
                this.loaitb_id == LoaiHinhTB.INTERNET_1260 || this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
              vmc_id = this.muccuoc_id
            }
            this.vkenh_toantrinh = await this.lay_ds_kenhtoantrinh_theoTB(this.thuebao_id)
            if(this.vkenh_toantrinh == 1){
              // this.vloaikenh_id = LOAIKENH.NOIHAT 
              this.vloaikenh_id = 8 // LOAIKENH.NOIHAT
            }
            
            let dset = await api.layhuonggiao_tiepnhan_v2(LoaiHopDong.CHAMDUT_SD, this.loaitb_id, this.tocdo_id, vmc_id, this.cboKieuLD.SelectedValue, this.vloaikenh_id, this.cboDichVuVT.SelectedValue)
            let huonggiao_tn = 0
            this.quytrinh_id = 0
            if (!dset || dset.length <= 0) {
              this.loading(false)
              this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
              return
            }
            huonggiao_tn = dset[0]["huonggiao_id"]
            this.quytrinh_id = dset[0]["quytrinh_id"]
            if (this.dichvuvt_id == DichVuVienThong.TSL ||this.dichvuvt_id == 25) {
              // BAN CHEO, API XU LY
              let tocdo_tc_dau = 0
              let muccuoc_id_tc_dau = 0
              let tocdo_tc_cuoi = 0
              let muccuoc_id_tc_cuoi = 0
              if (!this.kenh_trang) {
                tocdo_tc_dau = this.dsDBTBBC[0]["tocdo_id"]
                tocdo_tc_cuoi = this.dsDBTBBC[1]["tocdo_id"]
                muccuoc_id_tc_dau = 0
                muccuoc_id_tc_cuoi = 0
              }
              let rs_qt_dau = []
              if (this.kenh_trang) {
                console.log(555555555555) 
                console.log(this.thongso_tc_dau)
                rs_qt_dau = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh_dau.tinhthicong_id,LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue),
                  parseInt(this.thongso_tc_dau.tocdo_id), parseInt(this.thongso_tc_dau.muccuoc_id), this.thongso_tc_dau.kieuld_id,this.vloaikenh_id,this.dichvuvt_id)
              } else {
                rs_qt_dau = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh_dau.tinhthicong_id,LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue),
                  tocdo_tc_dau, muccuoc_id_tc_dau, this.kieuld_dau,this.vloaikenh_id,this.dichvuvt_id)
              }
              if (!rs_qt_dau || rs_qt_dau.length <= 0) {
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm đầu. Liên hệ Admin để xử lý")
                return
              }
              this.quytrinh_tc_tn_dau = rs_qt_dau[0]["quytrinh_id"]
              this.huonggiao_tc_tn_dau = rs_qt_dau[0]["huonggiao_id"]
              // Lấy hướng giao, quy trình tại điểm cuối
              let rs_qt_cuoi = []
              if (this.kenh_trang) {
                rs_qt_cuoi = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh_cuoi.tinhthicong_id,LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue),
                  parseInt(this.thongso_tc_cuoi.tocdo_id), parseInt(this.thongso_tc_cuoi.muccuoc_id), this.thongso_tc_cuoi.kieuld_id,this.vloaikenh_id,this.dichvuvt_id)
              } else {
                rs_qt_cuoi = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh_cuoi.tinhthicong_id,LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue),
                  tocdo_tc_cuoi, muccuoc_id_tc_dau, this.kieuld_cuoi,this.vloaikenh_id,this.dichvuvt_id)
              }
              if (!rs_qt_cuoi || rs_qt_cuoi.length <= 0) {
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm cuối. Liên hệ Admin để xử lý")
                return
              }
              this.quytrinh_tc_tn_cuoi = rs_qt_cuoi[0]["quytrinh_id"]
              this.huonggiao_tc_tn_cuoi = rs_qt_cuoi[0]["huonggiao_id"]
            } else {
              // BAN CHEO, API XU LY
              let tocdo_tc = 0
              let muccuoc_id_tc = 0
              if (this.dichvuvt_id != DichVuVienThong.CO_DINH && this.dichvuvt_id != DichVuVienThong.IMS  && this.kenh_trang == false) {
                if (!this.chi_giao_net) {
                  tocdo_tc = this.dsDBTBBC[0]["tocdo_id"]
                } else {
                  tocdo_tc = this.thongso_tc.tocdo_id
                }
                if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                        this.loaitb_id == LoaiHinhTB.WIFI_FIBER || this.loaitb_id == LoaiHinhTB.INTERNET_1260 ||
                        this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
                  muccuoc_id_tc = this.dsDBTBBC[0]["muccuoc_id"]
                }
              }
              let rs_qt = await api.layhuonggiao_tiepnhan_bc_v4(this.tinh.tinhthicong_id, LoaiHopDong.CHAMDUT_SD, parseInt(this.cboLoaihinhTB.SelectedValue), tocdo_tc, muccuoc_id_tc, this.Kieuld_tc_id,this.vloaikenh_id,this.dichvuvt_id) //this.cboKieuLD.SelectedValue)
              if (!rs_qt || rs_qt.length <= 0) {
                this.loading(false)
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý")
                return
              }
              this.quytrinh_tc_tn = rs_qt[0]["quytrinh_id"]
              this.huonggiao_tc_tn = rs_qt[0]["huonggiao_id"]
            }
            if (this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN2B_CD ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN2B_CQ ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN30B_CD ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.ISDN30B_CQ ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.TRUNGKE_2M ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.TRUNGKE_THUONG ||
                this.cboLoaihinhTB.SelectedValue == LoaiHinhTB.TRUNGKE_TUONGTU) {
              this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
              if (this.isdn_sochinh) {
                this.ds_isdn = this.ds_isdn || []
                for (let i = 0; i < this.ds_isdn.length; i++) {
                  let kt = true
                  let ds = []
                  ds = await api.WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.ds_isdn[i]["ma_tb"], "0", this.dichvuvt_id, LoaiHopDong.CHAMDUT_SD)
                  if (ds && ds.length > 0) {
                    kt = false
                  }
                  if (!kt) {
                    this.loading(false)
                    this.$toast.error("Thuê bao ISDN: " + this.ds_isdn[i]["ma_tb"] +
                                        " đang được lập phụ lục khác và chưa hoàn thành!\n Bạn không thể thực hiện tiếp. ")
                    return
                  }
                }
                await this.TaoDuLieu(true)
                await this.TaoDuLieuBC(true)
                if (this.loi_sinh_magd) {
                  this.loi_sinh_magd = false
                  this.loading(false)
                  return
                }
                let result = await api.capnhat_chamdutsd_bancheo(0, this.dsHDKH, this.dsHDTB, this.dsHDTB_DD, this.dsDKHUY, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL, this.dsHDTB_IMS, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn,
                  this.tinh.tinhthicong_id, parseInt(this.cboLoaihinhTB.SelectedValue), this.kenh_trang?1:0, this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY)
                if(result.error_code !== "BSS-00000000"){
                  this.$toast.error(result.message_detail)
                  this.loading(false)
                  return;
                }
                // ban cheo, api xu ly
                //if (this.tinh.tinhthicong_id > 0)
                //{
                //    await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn);
                //}
                await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
                this.ds_isdn = this.ds_isdn || []
                for (let i = 0; i < this.ds_isdn.length; i++) {
                  if (this.ds_isdn[i]["trangthaitb_id"] == TrangThaiTB.THUONG &&
                        this.ds_isdn[i]["thuebao_cha_id"]) {
                    await this.TaoDuLieu_HDTB_ISDN(true, parseInt(this.ds_isdn[i]["thuebao_id"]), i)
                    await this.TaoDuLieu_LYDOHUY_ISDN()
                    await this.TaoDuLieu_HDTB_CD_ISDN(true, parseInt(this.ds_isdn[i]["thuebao_id"]))
                    await this.TaoDuLieu_HDTBDV(true)
                    await api.capnhat_idsn_chamdutsd_bancheo(this.dsHDTB, this.dsDKHUY, this.dsHDTB_CD, this.dsHDTBDV, huonggiao_tn)
                    await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
                  }
                }
              } else {
                await this.TaoDuLieu(true)
                await this.TaoDuLieuBC(true)
                if (this.loi_sinh_magd) {
                  this.loi_sinh_magd = false
                  this.loading(false)
                  return
                }
               let result = await api.capnhat_chamdutsd_bancheo(0, this.dsHDKH, this.dsHDTB, this.dsHDTB_DD, this.dsDKHUY, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL, this.dsHDTB_IMS, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn,
                  this.tinh.tinhthicong_id, parseInt(this.cboLoaihinhTB.SelectedValue), this.kenh_trang?1:0, this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY)
                if(result.error_code !== "BSS-00000000"){
                  this.$toast.error(result.message_detail)
                  this.loading(false)
                  return;
                }
                //if (this.tinh.tinhthicong_id > 0)
                //    await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn);
                await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
              }
            } else {
              await this.TaoDuLieu(true)
              await this.TaoDuLieuBC(true)
              if (this.loi_sinh_magd) {
                this.loi_sinh_magd = false
                this.loading(false)
                return
              }
              let result = await api.capnhat_chamdutsd_bancheo(0, this.dsHDKH, this.dsHDTB, this.dsHDTB_DD, this.dsDKHUY, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL, this.dsHDTB_IMS, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn,
                this.tinh.tinhthicong_id, parseInt(this.cboLoaihinhTB.SelectedValue), this.kenh_trang?1:0, this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY)
                if(result.error_code !== "BSS-00000000"){
                  this.$toast.error(result.message_detail)
                  this.loading(false)
                  return;
                }
              // ban cheo, api xu ly
              //if (this.tinh.tinhthicong_id > 0 || this.kenh_trang) {
              ///    await api.themmoi_HDBANCHEO_BIENDONG_V2(this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY,huonggiao_tn);
              //}
              if (TRANGTHAI_DONGBO.OPEN_SYNC_VINA) {
                let _q = this.dsHDTB.filter(r => r['dichvuvt_id'] == DichVuVienThong.DI_DONG).map(r => r["thuebao_id"])
                if (_q.length > 0) {
                  let ds_thuebao = _q.reduce((prev, cur) => prev + ',' + cur)
                  let _ds = await api.lay_tt_tb_dd(ds_thuebao)
                  if (_ds && _ds.length > 0) {
                    for (let i = 0; i < _ds.length; i++) {
                      await api.capnhat_status_hdtb(parseInt(_ds[i]["hdtb_id"]), TRANGTHAI_DONGBO.DONGBO_LHD)
                    }
                    this.$toast.error("Cập nhật thông tin ccbs thành công")
                  }
                }
              }
              await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
              if ((parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.DI_DONG) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.ADSL) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.IMS) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.CO_DINH) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.MEGAWAN) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.TSL) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != 25) && (parseInt(this.cboDichVuVT.SelectedValue) != 26) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.METRONET) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.ANTOAN_BAOMAT_TT) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.DICHVU_CNTT) &&
                    (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.TRUNGTAM_DULIEU) && (parseInt(this.cboDichVuVT.SelectedValue) != DichVuVienThong.HOINGHI_TRUYENHINH)) {
                await api.capnhat_status_hdtb(this.hdtb_id, TRANGTHAI_DONGBO.DONGBO_LHD)
              }
              if (this.loaitb_id == LoaiHinhTB.PABX) {
                if (this.isdn_sochinh) {
                  this.hdtb_id_cha = this.hdtb_id
                  this.ds_isdn = this.ds_isdn || []
                  for (let i = 0; i < this.ds_isdn.length; i++) {
                    let dr = this.ds_isdn[i]
                    if (dr["thuebao_cha_id"]) {
                      this.thuebao_id = parseInt(dr["thuebao_id"])
                      this.txtMaTB.Text = dr["ma_tb"]
                      this.txtDiaChiLD.Text = dr["diachi_ld"]
                      this.doituong_id = parseInt(dr["doituong_id"])
                      if (this.tinh.tinhthicong_id > 0) {
                        // TODO
                        // this.dsDKHUY_TEMP = new frmDangKyLyDoHuyBC(tinh, dsDKHUY_TEMP, hdtb_id, true, 1, loaitb_id).dsDK_HUY;
                      } else {
                        // TODO
                        // this.dsDKHUY_TEMP = new frmDangKyLyDoHuy(dsDKHUY_TEMP, hdtb_id, true, 1, loaitb_id).dsDK_HUY;
                      }
                      await this.ThemHDThueBao()
                    }
                  }
                }
              }
            }
            this.lvwThueBao = {
              ...this.lvwThueBao,
              Items: []
            }
            let dsHD = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TrangThaiHD.MOI)
            this.HienThiDanhSacHDTB(dsHD)
            this.LAY_DS_LYDOHUY()
          }
        } else {
          const dtTemp = await api.sp_lay_tt_hdtb_theo_hdkh(this.hdkh_id)
          if (!dtTemp) {
            this.loading(false)
            this.$toast("Thuê bao không tồn tại trong hợp đồng, không thể cập nhật. Bạn phải Thêm thuê bao!")
            return
          }
          const dr = dtTemp.filter(r => r['ma_tb'] == this.txtMaTB.Text && r['dichvuvt_id'] == this.dichvuvt_id)
          if (dr.length <= 0) {
            this.loading(false)
            this.$toast("Thuê bao không tồn tại trong hợp đồng, không thể cập nhật. Bạn phải Thêm thuê bao!")
            return
          }
          if (this.chkThuHoi.Checked == false) {
            // const phieu_id = await api.map_id("min(phieu_id)", "giaophieu", "where hdtb_id = " + this.hdtb_id)
            let phieu_id = 0;
            let input= {
                            type:1,
                            param: this.hdtb_id
                      }
            phieu_id = this.GetData(await api.fn_tt_giaophieu(this.axios,input));
            const ds_vt = await api.sp_giaophieu_tbi(phieu_id)
            if (ds_vt.length > 0) {
              this.loading(false)
              this.$toast.error("Bạn phải chọn 'Thu hồi thiết bị tại Giao dịch' do đã nhập Vật tư.\n Nếu muốn không muốn chọn 'Thu hồi thiết bị tại Giao dịch' thì hãy xóa vật tư trước !")
              return
            }
          }
          const rsLapHDKhac = await api.WinUICommon_KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.txtMaTB.Text, this.txtMaGD.Text, parseInt(this.cboDichVuVT.SelectedValue), LoaiHopDong.CHAMDUT_SD)
          if (rsLapHDKhac && rsLapHDKhac.length > 0) {
            this.loading(false)
            this.$toast.error("Thuê bao " + this.txtMaTB.Text + " đang lập hợp đồng khác!.")
            return
          }
          await this.TaoDuLieu(false)
          // await this.TaoDuLieuBC(false)
          let vmc_id = 0
          if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                  this.loaitb_id == LoaiHinhTB.WIFI_FIBER ||
                this.loaitb_id == LoaiHinhTB.INTERNET_1260 || this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
            vmc_id = this.muccuoc_id
          }
          let dset = await api.layhuonggiao_tiepnhan_v2(LoaiHopDong.CHAMDUT_SD, this.loaitb_id, this.tocdo_id, vmc_id, this.cboKieuLD.SelectedValue, this.vloaikenh_id, this.cboDichVuVT.SelectedValue)
          let huonggiao_tn = 0
          this.quytrinh_id = 0
          if (!dset || dset.length <= 0) {
            this.loading(false)
            this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
            return
          }
          huonggiao_tn = dset[0]["huonggiao_id"]
          this.quytrinh_id = dset[0]["quytrinh_id"]
          let result = await api.capnhat_chamdutsd_bancheo(1, this.dsHDKH, this.dsHDTB, this.dsHDTB_DD, this.dsDKHUY, this.dsHDTB_ADSL, this.dsHDTB_CD, this.dsHDTB_GP, this.dsHDTB_MGW, this.dsHDTB_TSL, this.dsHDTB_IMS, this.dsHDTB_CNTT, this.dsHDTBDV, huonggiao_tn,
            this.tinh.tinhthicong_id, parseInt(this.cboLoaihinhTB.SelectedValue), this.kenh_trang?1:0, this.dsHDBC, this.dsHDTBBC, this.dsHDBC_MGW, this.dsHDBC_ADSL, this.dsHDBC_CD, this.dsDK_DVGT, this.dsHDBC_TSL, this.dsHDBC_IMS, this.dsDKHUYBC, this.dsBD_LYDOHUY)
           if(result.error_code !== "BSS-00000000"){
                  this.$toast.error(result.message_detail)
                  this.loading(false)
                  return;
                }
          await api.capnhat_giaophieu_bd_lydohuy_chamdutsd_bancheo(this.tt_nd.nhanvien_id, this.thuebao_id, this.hdtb_id, this.chkThuHoi.Checked ? 1 : 0, parseInt(this.cboDichVuVT.SelectedValue))
          this.lvwThueBao = {
            ...this.lvwThueBao,
            Items: []
          }
          this.HienThiDanhSacHDTB(await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TrangThaiHD.MOI))
          this.LAY_DS_LYDOHUY().then(() => {
            this.HienThiDS_LyDoHuy(this.dsDKHUY_TEMP)
          })
        }
        this.SetButton(3)
        if (this.lvwThueBao.Items.length >= 1) {
          if (this.dichvuvt_id == DichVuVienThong.GPHONE) { this.tsbtnCapNhatDB.Enabled = true } else { this.tsbtnCapNhatDB.Enabled = false }
        }
        if ((parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.ADSL || parseInt(this.cboDichVuVT.SelectedValue) == DichVuVienThong.DI_DONG) &&
                  this.PHAILAM_KICHHOAT(this.quytrinh_id)) {
          this.tsbtnKichHoat.Enabled = true
        } else { this.tsbtnKichHoat.Enabled = false }
        this.loading(false)
        this.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!")
      } catch (exp) {
        this.loading(false)
        this.$toast.error("Có lỗi trong quá trình cập nhật hợp đồng thanh lý: \n" + exp.message)
        console.error(exp)
      }
      this.loading(false)
    },
    GetData: function (response) {
            if (response.data.error === 200 || response.data.error === '200'|| response.data.error === '0') {
                return response.data.data
            } else {
                console.log(response.data.error_code)
            }
            return []
    },
    tsbtnHuyBo_Click () {
      this.SetButton(0)
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
    },
    btnInPL_Click () {
      if (!this.hdkh_id || this.hdkh_id == 0) {
        this.$toast.warning('Không có thông tin hợp đồng để in')
        return
      }
      this.inHoSoParams = {
        hdkh_id: this.hdkh_id,
        loaihd_id: LoaiHopDong.CHAMDUT_SD,
        phieu_id: 123,
        phanvung_id: this.$auth.getPhanVungID(),
        ma_gd: this.txtMaGD.Text
      }
      this.$refs.dlgInHopDong.showModal()
    },
    downloadPDF () {
    },
    upHS () {
    },
    async tsbtnGiaoPhieu_Click () {
      if (this.lvwThueBao.Items.length == 0) {
        return
      }
      const quytrinhId = await api.map_id('quytrinh_id', 'hd_thuebao', 'where hdtb_id = ' + this.hdtb_id)
       let input= {
          type:3,
          param: this.hdtb_id
      }
      let hdtb_id = this.GetData(await api.fn_tt_hd_thuebao(this.axios,input));
      let data = {
        'hdtb_id': this.hdtb_id,
        'hdkh_id': this.hdkh_id,
        'loaitb_id': this.loaitb_id,
        'luong_id': this.luong_id
      }
      const huongGiaoId = await api.fn_giaophieu_chamdutsd(data)
      if (huongGiaoId.result != 0) {
        window.open(`/#/contract/HandoverTicket?vquytrinh=${quytrinhId}&vhuonggiao_id=${huongGiaoId.result}&vdichvuvt_id=${this.dichvuvt_id}&vloai_hd=${LoaiHopDong.CHAMDUT_SD}`)
      }
    },
    async tsbtnThemTB_Click () {
      if (this.lvwThueBao.Items.length > 0) {
        for (let i = 0; i < this.lvwThueBao.Items.length; i++) {
          if (this.lvwThueBao.Items[0]['dichvuvt_id'] == DichVuVienThong.DI_DONG) {
            if (this.cboDichVuVT.SelectedValue != DichVuVienThong.DI_DONG) {
              this.$toast.error("Hợp đồng thanh lý chỉ được phép bao gồm các thuê bao di động !")
              return
            }
          } else {
            if (this.cboDichVuVT.SelectedValue == DichVuVienThong.DI_DONG) {
              this.$toast.error("Bạn không được phép thanh lý thuê bao di động trên cùng hợp đồng với thuê bao khác !")
              return
            }
          }
        }
      }
      const rsThemTB = await this.ThemHDThueBao()
      if (!rsThemTB) {
        return
      }
      this.$toast.success("Thêm thuê bao thành công")
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        const ds = await api.lay_ds_hd_tbdd_theo_hdkh_id(this.hdkh_id)
        await this.HienThiDanhSacHDTB(ds)
      } else {
        const ds = await api.lay_ds_hdtb_theo_hdkh_tthd(this.hdkh_id, TrangThaiHD.MOI)
        await this.HienThiDanhSacHDTB(ds)
      }
      this.$refs.lvwThueBao.$refs.grid.selectRow(this.lvwThueBao.Items.length - 1, false)
    },
    async tsbtnXoaTB_Click () {
      if (!this.txtMaTB.Text) {
        this.$toast.error("Bạn phải chọn thuê bao cần xóa trên lưới dữ liệu !")
        return
      }
      const cf = await this.$bvModal.msgBoxConfirm("Bạn có chắc muốn xóa hợp đồng không ?", {
        okTitle: 'Có',
        cancelTitle: 'Không'
      })
      if (!cf) return
      this.loading(true)
      // const ds = await api.lay_thongtin_table('hd_thuebao a', `a.hdtb_id=${parseInt(this.hdtb_id)} and (a.tthd_id!=1 or a.status in (5,7))`, 'a.*', '')
      // if (ds && ds.length > 0) {
      //   this.$toast.error("Thuê bao không ở trạng thái mới (hoặc thuê bao đã kích hoạt). Không được xóa!")
      //   return
      // }
      await this.XoaHDTB()
      this.loading(false)
    },
    async tsbtnVatTu_Click () {
      if (this.hdkh_id == 0) {
        this.$toast.error("Bạn hãy tạo phụ lục trước khi nhập vật tư")
        return
      }
      if (!this.chkThuHoi.Checked) {
        this.$toast.error("Bạn phải cập nhật chọn 'Thu hồi thiết bị tại Giao dịch' trước khi nhập vật tư !")
        return
      }
      // const ds_ttphieu = await api.lay_thongtin_phieu_tn(this.hdtb_id)
      // if (ds_ttphieu.length > 0) {
      //   if (ds_ttphieu[0]["kqxl_id"] == "3") {
      //   } else {
      //     this.$toast.error("Bạn phải cập nhật chọn 'Thu hồi thiết bị tại Giao dịch' trước khi nhập vật tư !")
      //     return
      //   }
      // }
      //const phieu_id = parseInt(await api.map_id("min(phieu_id)", "giaophieu", "where hdtb_id =" + this.hdtb_id))
      let input= {
          type:1,
          param: this.hdtb_id
      }
      let phieu_id = this.GetData(await api.fn_tt_giaophieu(this.axios,input));
      this.dlgVatTuInput = {
        phieu_id: phieu_id,
        loaihd_id: LoaiHopDong.CHAMDUT_SD,
        hdtb_id: this.hdtb_id,
        thuebao_id: this.thuebao_id
      }
      this.$refs.dlgVatTuThueBao.showModal()
    },
    btnEmailClick () {
      this.$refs['dlgEmail'].showModal(this.hdkh_id, this.khachhang_id)
    },
    async btnCuocnong_Click () {
      this.loading(true)
      this.txtCuocnong.Text = ''
      // this.thuebao_id = 2994427
      if (this.thuebao_id) {
        const date = moment().format('YYYYMM') + '01'
        const rs = await api.tinh_cuoc_nong(this.thuebao_id, date)
        this.txtCuocnong.Text = formatCurrency(rs, 0, 3, '.', '')
      } else {
        this.$toast.warning('Không có thuê bao để tìm cước nóng')
      }
      this.loading(false)
    },
    cboDichVuVT_SelectedValueChanged () {
      if (this.cboDichVuVT.Items.length > 0) {
        if (!this.cboDichVuVT.SelectedValue) return
        this.dichvuvt_id = this.cboDichVuVT.SelectedValue
        const dsLoaiHinhTB = this.dsLoaiHinhTBCached.filter(i => i.DICHVUVT_ID == this.dichvuvt_id)
        loadComboBoxItems(this.cboLoaihinhTB, dsLoaiHinhTB, 'LOAITB_ID', 0)
        this.tsbtnGiaoPhieu.Visible = true
        this.tsbtnCapNhatDB.Visible = false
        // if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        //   this.$refs.splitContainer2.Visible = true
        // } else {
        //   this.$refs.splitContainer2.Visible = false
        // }
        if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == 25|| this.dichvuvt_id == 26) {
          this.chkNgayHT.Enabled = true
          this.chkCongVan.Enabled = true
        } else {
          this.chkNgayHT.Enabled = false
          this.chkCongVan.Enabled = false
          this.txtCongVan.Text = ""
        }
      }
    },
    async cboLoaihinhTB_SelectedValueChanged () {
      if (this.cboLoaihinhTB.Items.length > 0) {
        if (!this.cboLoaihinhTB.SelectedValue) return
        this.loaitb_id = this.cboLoaihinhTB.SelectedValue
        // bangts.HT_Kieu_LD_THANHLY_TINHKHAC_Combobox(cboKieuLD, this.loaitb_id, LoaiHopDong.CHAMDUT_SD)
        api.lay_ds_kieu_lapdat(this.loaitb_id, LoaiHopDong.CHAMDUT_SD).then(rs => {
          loadComboBoxItems(this.cboKieuLD, rs, 'kieuld_id', 0)
        })
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.ANTOAN_BAOMAT_TT ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.TRUNGTAM_DULIEU ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.DICHVU_CNTT ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.HOINGHI_TRUYENHINH) {
          // api.lay_dulieu_theo_dieukien("tocdo_adsl", "tocdo_id", "thuonghieu as toc_do", "loaitb_id = " + this.loaitb_id, "iptinh").then(rs => {
          //   this.cboTocDoADSL.Items = rs.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id) }))
          //   this.cboTocDoADSL.SelectedValue = rs[0]["tocdo_id"] || ''
          // })
          //FIX ATT
          api.lay_dulieu_theo_dieukien_tocdo_adsl(this.loaitb_id).then(rs => {
            this.cboTocDoADSL.Items = rs.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id) }))
            this.cboTocDoADSL.SelectedValue = rs[0]["tocdo_id"] || ''
          })
        } else if (this.cboDichVuVT.SelectedValue == DichVuVienThong.MEGAWAN ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.METRONET ||
                    this.cboDichVuVT.SelectedValue == DichVuVienThong.TSL||
                    this.cboDichVuVT.SelectedValue ==25||
                    this.cboDichVuVT.SelectedValue ==26) {
          // api.lay_dulieu_theo_dieukien("tocdo_kenh", "tocdo_id", "tocdo||' '||donvi toc_do", "", "tocdo").then(rs => {
          //   this.cboTocDoADSL.Items = rs.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id) }))
          //   this.cboTocDoADSL.SelectedValue = rs[0]["tocdo_id"] || ''
          // })
          //FIX ATT
          api.lay_dulieu_theo_dieukien_toc_do_kenh().then(rs => {
            this.cboTocDoADSL.Items = rs.map(r => ({ ...r, tocdo_id: parseInt(r.tocdo_id) }))
            this.cboTocDoADSL.SelectedValue = rs[0]["tocdo_id"] || ''
          })
        }
      }
    },
    cboKieuLD_Focus () {
      console.log(5555555)
      this.Kieuld_click = true
    },
    cboKieuLD_SelectedValueChanged () {
    },
    //  async PopupThongSo(){
    //         console.log('PopupThongSo kaakak')
    //         if(this.listbox.kieu_ld.list && this.listbox.kieu_ld.list.length > 0)
    //         {
    //             if(this.listbox.kieu_ld.value*1 == 1) return;
    //             if(!this.kt_load)
    //             {
    //                 await this.LayTienTheoKhoanMuc();
    //             }
    //             var loaitb_id = this.listbox.loaihinh_tb.value*1;
    //             var loaitb_id_moi = this.listbox.loaihinh_tb_moi.value*1;
                
    //             var dichvu_vt = this.listbox.dichvu_vt.value*1;
    //             if((dichvu_vt == DichVuVienThong.CO_DINH || dichvu_vt == DichVuVienThong.IMS) && this.tinh.tinhthicong_id != 0)
    //             {
    //                 this.khachhang_id = this.khachhang.khachhang_tc_id;
    //                 this.hdkh_id = this.khachhang.hdkh_id;
    //                 this.$refs.thongSoCoDinhModal.showModal(this.tinh.tinhthicong_id,loaitb_id, loaitb_id_moi, 0, 0, LoaiHopDong.CHUYENDOI_LH)
    //             }              
    //         }
    //     },
   async PopupThongSo () {
      if (this.cboKieuLD.Items.length > 0) { 
        if (!this.cboKieuLD.SelectedValue) return
         console.log(33333)
          console.log(this.tinh)
          console.log(this.tinh.tinhthicong_id)
          console.log(this.Kieuld_click)
          console.log(this.kenh_trang)
          console.log(this.chi_giao_net)
        if (this.tinh && this.tinh.tinhthicong_id > 0 && (this.kenh_trang || this.chi_giao_net)) {
          let loaitb_id = parseInt(this.cboLoaihinhTB.SelectedValue)
          let dichvu_vt = parseInt(this.cboDichVuVT.SelectedValue)
            console.log(444444)
          if ((dichvu_vt == DichVuVienThong.MEGAWAN || dichvu_vt == DichVuVienThong.METRONET|| dichvu_vt == 26)) {
            this.$refs.dlgThongSoWan.openDialog1(this.tinh, loaitb_id, loaitb_id, LoaiHopDong.CHAMDUT_SD, dichvu_vt, this.thongso_ban, this.thongso_tc)
          }
          if (dichvu_vt == DichVuVienThong.TSL||dichvu_vt == 25) {
            this.$toast.warning("Chọn thông số thi công cho kênh trắng tại 2 điểm bằng n") 
          }
        }
      }
    },
     
    dlgThongSoWan_close ({ Capnhat, ThongsoBan, ThongsoTc }) {
      if (Capnhat) {
        console.log(1111111111111)
        this.thongso_ban = ThongsoBan
        console.log(this.thongso_ban)
        this.thongso_tc = ThongsoTc
         console.log(this.thongso_tc)
      }
    },
    btnSearchAclengthClick () {
    },
    btnLayDSTBClick () {
      if (!this.txtMaTB.Text || this.txtMaTB.Text.trim() == "") {
        this.$toast.error("Bạn chưa nhập mã thuê bao!")
        return
      }
      if (!this.thueBaoTonTai) {
        this.$toast.error(`Chưa có thông tin về khách hàng thanh lý có mã thuê bao ${this.txtMaTB.Text}. Kiểm tra lại!`)
        return
      }
      this.$refs.dlgTraCuuThueBao.showModal(this.khachhang_id, this.dichvuvt_id, this.txtMaGD.Text, this.showErrorDSTBC)
    },
    btnEditLyDoHuy_Click () {
      this.$refs.dlgHuy.layDsKieuLapDat()
      this.$refs.dlgHuy.show(true)
    },
    ckbThuHoiChange () {
    },
    ckbKhongThuHoiChange () {
    },
    async txtMaGD_KeyPress (e) {
      if (e.charCode !== 13 || !this.txtMaGD.Text) {
        return
      }
      this.lvwThueBao = {
        ...this.lvwThueBao,
        Items: []
      }
      this.loading(true)
      const rs = await api.lay_ds_hopdong_theo_ma_gd(this.txtMaGD.Text)
      if (rs.error_code != SUCCESS_CODE) {
        this.$toast.error(rs.message)
      } else {
        await this.HienThiTTHopDongKH(rs.data)
      }
      this.loading(false)
    },
    async HienThiTTHopDongKH (ds) {
      try {
        this.txtCuocnong.Text = ""
        if (!ds || !ds.length) {
          this.lvwThueBao = {
            ...this.lvwThueBao,
            Items: []
          }
          this.SetButton(0)
          return
        }
        if (ds[0]["status"] == TRANGTHAI_DONGBO.DONGBO_CM || ds[0]["status"] == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
          this.$toast.error("Thuê bao đã được kích hoạt !")
          this.Clear()
          return
        }
        this.hdkh_id = parseInt(ds[0]["hdkh_id"])
        this.txtMaGD.Text = ds[0]["ma_gd"]
        this.txtMaHD.Text = ds[0]["ma_hd"]
        this.txtMaKH.Text = ds[0]["ma_kh"]
        this.so_gt = ds[0]["so_gt"]
        this.so_dt = ds[0]["so_dt"]
        if (ds[0]["khachhang_id"]) {
          this.khachhang_id = parseInt(ds[0]["khachhang_id"])
        }
        this.txtTenKH.Text = ds[0]["ten_kh"]
        this.txtDiaChiKH.Text = ds[0]["diachi_kh"]
        this.dtpNgayLapHD.Value = formatDate(ds[0].ngaylap_hd)
        if (ds[0]["ngay_yc"]) {
          this.dtpNgayYeuCau.Value = formatDate(ds[0].ngay_yc)
        }
        this.SetButton(3)
        const dsHD = await api.lay_ds_hopdong_tb_theo_hdkh_id(this.hdkh_id) || []
        this.HienThiDanhSacHDTB(dsHD)
        this.HienThiTTHopDongTB(dsHD)
        this.tsbnHoanThienDB.Enabled = false
      } catch (exp) {
        this.$toast.error(exp)
      }
    },
    async HienThiDanhSacHDTB (ds) {
      let myDataTables = [...this.lvwThueBao.Items]
      if (ds.length > 0) {
        for (let i = 0; i < ds.length; i++) {
          if (ds[i]["tthd_id"] == TrangThaiHD.MOI && ds[i]["status"] != TRANGTHAI_DONGBO.DONGBO_CM) {
            let myRow = ds[i]
            let lvItem = {
              stt: i + 1,
              hdtb_id: myRow["hdtb_id"],
              ma_tb: myRow["ma_tb"],
              ten_tb: myRow["ten_tb"],
              diachi_tb: myRow["diachi_tb"],
              dichvuvt_id: myRow["dichvuvt_id"],
              trangthai_dongbo: myRow["trangthai_dongbo"],
              Selected: false
            }
            myDataTables.push(lvItem)
          }
        }
        this.lvwThueBao.Items = myDataTables
        this.lvwThueBao.Items[0].Selected = true
        this.lvwThueBao = {
          ...this.lvwThueBao,
          Items: this.lvwThueBao.Items
        }
        this.$refs.lvwThueBao.$refs.grid.selectRow(this.lvwThueBao.Items.length - 1, false)
      }
      if (this.lvwThueBao.Items.length <= 1) {
        if (myDataTables.length == 0) { this.tsbtnThemTB.Enabled = false }
        let ds_count = []
        ds_count = await api.lay_sl_hd(this.hdkh_id)
        if (ds_count && ds_count.length > 0) {
          if (parseInt(ds_count[0]["sl"]) > 1) {
            this.tsbtnXoaTB.Enabled = true
          } else {
            this.tsbtnXoaTB.Enabled = false
          }
        } else { this.tsbtnXoaTB.Enabled = false }
      } else {
        this.tsbtnXoaTB.Enabled = true
        this.tsbtnThemTB.Enabled = true
      }
    },
    async HienThiGiaoDien (luongID) {
      // tsbtnVatTuMoi.Visible = false
      this.dtThaoTac = await api.lay_ds_thaotac_theo_luong_id_code(luongID, 'VAT_TU_MOI,VAT_TU,DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO,TAMTHU_VT')
      if (this.dtThaoTac.length > 0) {
        for (let i = 0; i < this.dtThaoTac.length; i++) {
          const dtControl = await api.lay_ct_thaotac_control(this.dtThaoTac[i].thaotac_id)
          if (dtControl && dtControl.length > 0) {
            for (let j = 0; j < dtControl.length; j++) {
              if (this.dtThaoTac[i].enable == '1') {
                // this.$refs[dtControl[j].control_name].setAttribute('disabled', false)
                // if (this.$refs[dtControl[j].control_name].classList.contains('disabled')) {
                //   this.$refs[dtControl[j].control_name].classList.remove('disabled')
                // }
                if (this[dtControl[j].control_name] && this[dtControl[j].control_name].hasOwnProperty('Enabled')) {
                  this[dtControl[j].control_name]['Enabled'] = true
                }
              } else {
                // this.$refs[dtControl[j].control_name].setAttribute('disabled', true)
                // if (!this.$refs[dtControl[j].control_name].classList.contains('disabled')) {
                //   this.$refs[dtControl[j].control_name].classList.add('disabled')
                // }
                if (this[dtControl[j].control_name] && this[dtControl[j].control_name].hasOwnProperty('Enabled')) {
                  this[dtControl[j].control_name]['Enabled'] = false
                }
              }
            }
          }
        }
      }
    },
    async LAY_DS_LYDOHUY () {
      this.dsDKHUY_TEMP = await api.lay_ds_dk_lydohuy(this.hdtb_id)
    },
    async HienThiDS_LyDoHuy (ds) {
      const viewList = (ds || []).reduce((list, current) => {
        if (!list.find(el => el.lydohuy_id === current.lydohuy_id)) {
          list.push(current)
        }
        return list
      }, [])
      this.lvLyDoHuy.Items = [...viewList]
    },
    btnDiemDau_Click () {
      try {
        if (this.tinh_dau.tinhthicong_id == 0) { 
          this.$root.$toast.error('Chưa có thuê bao')
          return
        }
        let loaitbid = this.cboLoaihinhTB.SelectedValue
        this.flagThongSoTSL = 1
        this.$refs.popupThongSoTSL.openDialog(this.tinh_dau, loaitbid, loaitbid, this.cboKieuLD.SelectedValue, this.thongso_ban, this.thongso_tc_dau, LoaiHopDong.CHAMDUT_SD, this.kenh_trang)
      } catch (error) {
        console.log(error)
      }
    },
    chapnhanThongSoTSL (data) {
      console.log("TEsT",data);
      if (data) {
        this.thongso_ban = data.thongso_Ban
        if (this.flagThongSoTSL == 1) {
          this.thongso_tc_dau = data.thongso_TC
          this.Kieuld_tc_id = this.thongso_tc_dau.kieuld_id
          this.kieuld_dau = this.thongso_tc_dau.kieuld_id
        } else {
          this.thongso_tc_cuoi = data.thongso_TC
          // this.Kieuld_tc_id_tsl = this.thongso_tc_cuoi.kieuld_id
          this.Kieuld_tc_id = this.thongso_tc_cuoi.kieuld_id
          this.kieuld_cuoi = this.thongso_tc_cuoi.kieuld_id
        }
      }
    },
    btnDiemCuoi_Click () {
      try {
        if (this.tinh_cuoi.tinhthicong_id == 0) {
          this.$root.$toast.error('Chưa có thuê bao')
          return
        }
        let loaitbid = this.cboLoaihinhTB.SelectedValue
        this.flagThongSoTSL = 2
        this.$refs.popupThongSoTSL.openDialog(this.tinh_cuoi, loaitbid, loaitbid, this.cboKieuLD.SelectedValue, this.thongso_ban, this.thongso_tc_cuoi, LoaiHopDong.CHAMDUT_SD, this.kenh_trang)
      } catch (error) {
        console.log(error)
      }
    },
    formatJson (data) {
      let output = {}
      Object.keys(data).forEach(el => {
        if (el.indexOf('_')) {
          output[el.replace(el.substring(el.indexOf('_'), el.length), el.substring(el.indexOf('_'), el.length).toLowerCase())] = data[el]
        } else {
          output[el] = data[el]
        }
      })
      return output
    },
    async Gan_thongso_bancheo_hdtb_tsl (vhdtb) {
      const ds = await api.lay_thongso_thuebao_hdbc(vhdtb)
      if (ds && ds.length > 0) {
        this.thongso_ban = {}
        for (let i = 0; i < ds.length; i++) {
          const drTHONGSO = ds[i]
          this.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
          this.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
          this.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
          this.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
          this.thongso_ban.chuquanld_id_cuoi = drTHONGSO.CHUQUANLD_BAN_CUOI
          this.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
          this.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
          this.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
          this.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
          this.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN
          this.thongso_ban.ma_tn = drTHONGSO.MATN_BAN
          this.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
          this.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
          this.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
          this.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
          this.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
          this.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
          this.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
          this.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN
          this.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
          this.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
          this.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN
          // Thông số thi công
          if (ds[i]["tinh_dc_id"] == "1") { // Nếu row của tỉnh đầu
            this.thongso_tc_dau.kieuld_id = drTHONGSO.KIEULD_TC
            this.thongso_tc_dau.doituong_id = drTHONGSO.DOITUONG_TC
            this.thongso_tc_dau.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.thongso_tc_dau.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.thongso_tc_dau.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.thongso_tc_dau.kieutra_id = drTHONGSO.KIEUTRA_TC
            this.thongso_tc_dau.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.thongso_tc_dau.loaimd_id = drTHONGSO.LOAIMD_TC
            this.thongso_tc_dau.ma_doicap = drTHONGSO.MADOICAP_TC
            this.thongso_tc_dau.ma_tn = drTHONGSO.MATN_TC
            this.thongso_tc_dau.muccuoc_id = drTHONGSO.MUCCUOC_TC
            this.thongso_tc_dau.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC
            this.thongso_tc_dau.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.thongso_tc_dau.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            this.thongso_tc_dau.thoihan_id = drTHONGSO.THOIHAN_TC
            this.thongso_tc_dau.tocdo_id = drTHONGSO.TOCDO_TC
            this.thongso_tc_dau.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.thongso_tc_dau.tocdo_nix = drTHONGSO.TOCDONIX_TC
            this.thongso_tc_dau.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            this.thongso_tc_dau.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.thongso_tc_dau.trangbi_id = drTHONGSO.TRANGBI_TC
          } else {
            this.thongso_tc_cuoi.kieuld_id = drTHONGSO.KIEULD_TC
            this.thongso_tc_cuoi.doituong_id = drTHONGSO.DOITUONG_TC
            this.thongso_tc_cuoi.chuquan_id = drTHONGSO.CHUQUAN_TC
            this.thongso_tc_cuoi.chuquanld_id = drTHONGSO.CHUQUANLD_TC
            this.thongso_tc_cuoi.congnghe_id = drTHONGSO.CONGNGHE_TC
            this.thongso_tc_cuoi.kieutra_id = drTHONGSO.KIEUTRA_TC
            this.thongso_tc_cuoi.loaikenh_id = drTHONGSO.LOAIKENH_TC
            this.thongso_tc_cuoi.loaimd_id = drTHONGSO.LOAIMD_TC
            this.thongso_tc_cuoi.ma_doicap = drTHONGSO.MADOICAP_TC
            this.thongso_tc_cuoi.ma_tn = drTHONGSO.MATN_TC
            this.thongso_tc_cuoi.muccuoc_id = drTHONGSO.MUCCUOC_TC
            this.thongso_tc_cuoi.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC
            this.thongso_tc_cuoi.thietbidc_id = drTHONGSO.THIETBIDAU_TC
            this.thongso_tc_cuoi.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
            this.thongso_tc_cuoi.thoihan_id = drTHONGSO.THOIHAN_TC
            this.thongso_tc_cuoi.tocdo_id = drTHONGSO.TOCDO_TC
            this.thongso_tc_cuoi.tocdo_isp = drTHONGSO.TOCDOISP_TC
            this.thongso_tc_cuoi.tocdo_nix = drTHONGSO.TOCDONIX_TC
            this.thongso_tc_cuoi.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
            this.thongso_tc_cuoi.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
            this.thongso_tc_cuoi.trangbi_id = drTHONGSO.TRANGBI_TC
          }
        }
      }
    },
    async Gan_thongso_bancheo_hdtb (vhdtb) {
      const ds = await api.lay_thongso_thuebao_hdbc(vhdtb) || []
      if (ds && ds.length > 0) {
        const drTHONGSO = ds[0]
        const dvvt_id = ds[0]["DICHVUVT_ID"]
        if (dvvt_id != DichVuVienThong.HOINGHI_TRUYENHINH &&
                    dvvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT &&
                    dvvt_id != DichVuVienThong.DICHVU_CNTT &&
                    dvvt_id != DichVuVienThong.TRUNGTAM_DULIEU) {
          this.thongso_ban.chuquanld_id = drTHONGSO.CHUQUANLD_BAN
          this.thongso_ban.congnghe_id = drTHONGSO.CONGNGHE_BAN
          this.thongso_ban.loaikenh_id = drTHONGSO.LOAIKENH_BAN
          this.thongso_ban.loaimd_id = drTHONGSO.LOAIMD_BAN
          this.thongso_ban.ma_doicap = drTHONGSO.MADOICAP_BAN
          this.thongso_ban.chuquan_id = drTHONGSO.CHUQUAN_BAN
          this.thongso_ban.ma_tn = drTHONGSO.MATN_BAN
          this.thongso_ban.thietbidc_id = drTHONGSO.THIETBIDAU_BAN
          this.thongso_ban.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_BAN
          this.thongso_ban.thoihan_id = drTHONGSO.THOIHAN_BAN
          this.thongso_ban.tocdo_id = drTHONGSO.TOCDO_BAN
          this.thongso_ban.tocdo_isp = drTHONGSO.TOCDOISP_BAN
          this.thongso_ban.tocdo_nix = drTHONGSO.TOCDONIX_BAN
          this.thongso_ban.tocdo_pir_id = drTHONGSO.TOCDOPIR_BAN
          this.thongso_ban.phanloai_id = drTHONGSO.PHANLOAI_ID
          this.thongso_ban.phantach_id = drTHONGSO.PHANTACH_TD
          this.thongso_ban.tocdothuc_id = drTHONGSO.TOCDOTHUC_BAN
          this.thongso_ban.trangbi_id = drTHONGSO.TRANGBI_BAN
          this.thongso_tc.chuquan_id = drTHONGSO.CHUQUAN_TC
          this.thongso_tc.chuquanld_id = drTHONGSO.CHUQUANLD_TC
          this.thongso_tc.congnghe_id = drTHONGSO.CONGNGHE_TC
          this.thongso_tc.loaikenh_id = drTHONGSO.LOAIKENH_TC
          this.thongso_tc.loaimd_id = drTHONGSO.LOAIMD_TC
          this.thongso_tc.ma_doicap = drTHONGSO.MADOICAP_TC
          this.thongso_tc.ma_tn = drTHONGSO.MATN_TC
          this.thongso_tc.thietbidc_id = drTHONGSO.THIETBIDAU_TC
          this.thongso_tc.thietbidc_id_cuoi = drTHONGSO.THIETBICUOI_TC
          this.thongso_tc.thoihan_id = drTHONGSO.THOIHAN_TC
          this.thongso_tc.tocdo_id = drTHONGSO.TOCDO_TC
          this.thongso_tc.tocdo_isp = drTHONGSO.TOCDOISP_TC
          this.thongso_tc.tocdo_nix = drTHONGSO.TOCDONIX_TC
          this.thongso_tc.tocdo_pir_id = drTHONGSO.TOCDOPIR_TC
          this.thongso_tc.tocdothuc_id = drTHONGSO.TOCDOTHUC_TC
          this.thongso_tc.trangbi_id = drTHONGSO.TRANGBI_TC
        }
        this.thongso_ban.muccuoc_id = drTHONGSO.MUCCUOC_BAN
        this.thongso_ban.muccuoctb_id = drTHONGSO.MUCCUOCTB_BAN
        this.thongso_ban.kieuld_id = drTHONGSO.KIEULD_BAN
        this.thongso_ban.doituong_id = drTHONGSO.DOITUONG_BAN
        this.thongso_tc.kieuld_id = drTHONGSO.KIEULD_TC
        this.thongso_tc.doituong_id = drTHONGSO.DOITUONG_TC
        this.thongso_tc.muccuoc_id = drTHONGSO.MUCCUOC_TC
        this.thongso_tc.muccuoctb_id = drTHONGSO.MUCCUOCTB_TC
        this.thongso_tc.kieutra_id = drTHONGSO.KIEUTRA_TC
        this.thongso_ban.kieutra_id = drTHONGSO.KIEUTRA_BAN
        if (dvvt_id == DichVuVienThong.MEGAWAN || dvvt_id == DichVuVienThong.METRONET || dvvt_id == DichVuVienThong.TSL|| dvvt_id == 25|| dvvt_id == 26) {
          // gan tskt rieng cho kenh qt
          if (drTHONGSO.MHKN_ID) { this.thongso_ban.mhkn_id = drTHONGSO.MHKN_ID }
          if (drTHONGSO.GDKN_ID) { this.thongso_ban.gdkn_id = drTHONGSO.GDKN_ID }
          if (drTHONGSO.YC_BAOVE) { this.thongso_ban.yc_baove = drTHONGSO.YC_BAOVE }
          if (drTHONGSO.TOCDO_POP) { this.thongso_ban.tocdo_pop = drTHONGSO.TOCDO_POP }
          if (drTHONGSO.GDKN_POP_ID) { this.thongso_ban.gdkn_pop_id = drTHONGSO.GDKN_POP_ID }
          if (drTHONGSO.CAUHINH_DT_ID) { this.thongso_ban.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID }
          if (drTHONGSO.KIEU_DUPLEX_ID) { this.thongso_ban.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID }
          if (drTHONGSO.KIEUKN_ID) { this.thongso_ban.kieukn_id = drTHONGSO.KIEUKN_ID }
          this.thongso_ban.mtu_size = drTHONGSO.MTU_SIZE
          this.thongso_ban.auto_nego = drTHONGSO.AUTO_NEGO
          this.thongso_ban.sncp = drTHONGSO.SNCP
          this.thongso_ban.cap_chinh = drTHONGSO.CAP_CHINH
          this.thongso_ban.cap_duphong = drTHONGSO.CAP_DUPHONG
          this.thongso_ban.diem_pop = drTHONGSO.DIEM_POP
          this.thongso_ban.wan_ip = drTHONGSO.WAN_IP
          this.thongso_ban.lan_ip = drTHONGSO.LAN_IP
          this.thongso_ban.ce_ip = drTHONGSO.CE_IP
          this.thongso_ban.tt_ip = drTHONGSO.TT_IP
          this.thongso_ban.sl_ip = drTHONGSO.SL_IP
          this.thongso_ban.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI
          this.thongso_ban.so_as = drTHONGSO.SO_AS
          this.thongso_ban.hqos = drTHONGSO.HQOS
          this.thongso_ban.cos = drTHONGSO.COS
          this.thongso_ban.svlan = drTHONGSO.SVLAN
          this.thongso_ban.cvlan = drTHONGSO.CVLAN
          this.thongso_ban.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT
          this.thongso_ban.tt_port = drTHONGSO.TT_PORT
          if (drTHONGSO.MHKN_ID) { this.thongso_tc.mhkn_id = drTHONGSO.MHKN_ID }
          if (drTHONGSO.GDKN_ID) { this.thongso_tc.gdkn_id = drTHONGSO.GDKN_ID }
          if (drTHONGSO.YC_BAOVE) { this.thongso_tc.yc_baove = drTHONGSO.YC_BAOVE }
          if (drTHONGSO.TOCDO_POP) { this.thongso_tc.tocdo_pop = drTHONGSO.TOCDO_POP }
          if (drTHONGSO.GDKN_POP_ID) { this.thongso_tc.gdkn_pop_id = drTHONGSO.GDKN_POP_ID }
          if (drTHONGSO.CAUHINH_DT_ID) { this.thongso_tc.cauhinh_dt_id = drTHONGSO.CAUHINH_DT_ID }
          if (drTHONGSO.KIEU_DUPLEX_ID) { this.thongso_tc.kieu_duplex_id = drTHONGSO.KIEU_DUPLEX_ID }
          if (drTHONGSO.KIEUKN_ID) { this.thongso_tc.kieukn_id = drTHONGSO.KIEUKN_ID }
          this.thongso_tc.mtu_size = drTHONGSO.MTU_SIZE
          this.thongso_tc.auto_nego = drTHONGSO.AUTO_NEGO
          this.thongso_tc.sncp = drTHONGSO.SNCP
          this.thongso_tc.cap_chinh = drTHONGSO.CAP_CHINH
          this.thongso_tc.cap_duphong = drTHONGSO.CAP_DUPHONG
          this.thongso_tc.diem_pop = drTHONGSO.DIEM_POP
          this.thongso_tc.wan_ip = drTHONGSO.WAN_IP
          this.thongso_tc.lan_ip = drTHONGSO.LAN_IP
          this.thongso_tc.ce_ip = drTHONGSO.CE_IP
          this.thongso_tc.tt_ip = drTHONGSO.TT_IP
          this.thongso_tc.sl_ip = drTHONGSO.SL_IP
          this.thongso_tc.ten_trunk_nni = drTHONGSO.TEN_TRUNK_NNI
          this.thongso_tc.so_as = drTHONGSO.SO_AS
          this.thongso_tc.hqos = drTHONGSO.HQOS
          this.thongso_tc.cos = drTHONGSO.COS
          this.thongso_tc.svlan = drTHONGSO.SVLAN
          this.thongso_tc.cvlan = drTHONGSO.CVLAN
          this.thongso_tc.vlan_transparent = drTHONGSO.VLAN_TRANSPARENT
          this.thongso_tc.tt_port = drTHONGSO.TT_PORT
          this.thongso_ban.loai_ip = await api.lay_ds_loaiip_theo_hdtb_id(vhdtb)
          this.thongso_tc.loai_ip = this.thongso_ban.loai_ip
          this.thongso_ban.kieu_nghiemthu = await api.lay_ds_kieunt_theo_hdtb_id(vhdtb)
          this.thongso_tc.kieu_nghiemthu = this.thongso_ban.kieu_nghiemthu
        }
      }
    },
    async HienThiTTHopDongTB (ds) {
      this.txtCuocnong.Text = ""
      if (!ds || !ds.length) return
      this.dsDBTB = ds
      this.hdtb_id = parseInt(ds[0]["hdtb_id"])
      if (!this.kenh_trang) {
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          await this.Gan_thongso_bancheo_hdtb_tsl(this.hdtb_id)
        } else {
          await this.Gan_thongso_bancheo_hdtb(this.hdtb_id)
        }
      }
      if (ds[0]["thuebao_id"]) { this.thuebao_id = parseInt(ds[0]["thuebao_id"]) }
      if (!this.kenh_trang) {
        this.tinh = await api.lay_tinh_theo_thuebaoid(this.thuebao_id)
      }
      if (this.tinh && this.tinh.tinhthicong_id > 0) {
        this.thuebao_tc_id = await api.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
      } else {
        this.tinh = await api.tinh_thicong(parseInt(ds[0]["tinh_thicong"]))
        this.thuebao_tc_id = 0
      }
      let ds_tb = await api.lay_ds_dbtb_theo_tbid(this.thuebao_id)
      let ds_tt = []
      if (ds_tb && ds_tb.length > 0) {
        ds_tt = await api.lay_ds_dbtt_theo_ttid(ds_tb[0]["thanhtoan_id"])
      }
      if (ds_tt && ds_tt.length > 0) { this.txtDiaChiTT.Text = ds_tt[0]["diachi_tt"] } else { this.txtDiaChiTT.Text = "" }
      this.txtMaTB.Text = ds[0]["ma_tb"]
      this.dichvuvt_id = parseInt(ds[0]["dichvuvt_id"])
      this.quytrinh_id = parseInt(ds[0]["quytrinh_id"])
      if (ds[0]["thuebao_cha_id"]) { this.h_thuebao_cha_id = ds[0]["thuebao_cha_id"] } else { this.h_thuebao_cha_id = 0 }
      this.tsbtnVatTu.Visible = true
      this.tsbtnVatTuMoi.Visible = false
      try {
        this.luong_id = 0
        var ds_t = await api.ds_huonggiao(this.quytrinh_id, ds[0].tthd_id)
        if (ds_t && ds_t.length) {
          this.luong_id = parseInt(ds_t[0]["luong_id"])
        }
        this.HienThiGiaoDien(this.luong_id)
      } catch (e) {
        this.$toast.error(e)
      }
      this.cboDichVuVT.SelectedValue = this.dichvuvt_id
      this.loaitb_id = parseInt(ds[0]["loaitb_id"])
      this.cboLoaihinhTB.SelectedValue = this.loaitb_id
      this.txtTenTB.Text = ds[0]["ten_tb"]
      this.txtDiaChiTB.Text = ds[0]["diachi_tb"]
      this.txtDiaChiLD.Text = ds[0]["diachi_ld"]
      this.txtGhiChu.Text = ds[0]["ghichu"]
      this.LAY_DS_LYDOHUY().then(() => {
        this.HienThiDS_LyDoHuy(this.dsDKHUY_TEMP)
      })
      if ((this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) && this.kenh_trang == true) {
        this.HienThiNutTS_TSL(1)
      } else {
        this.HienThiNutTS_TSL(0)
      }
      this.cboKieuLD.SelectedValue = ds[0]["kieuld_id"]
      this.doituong_id = parseInt(ds[0]["doituong_id"])
      this.donvi_id = parseInt(ds[0]["donvi_id"])
      this.HienThiTTMoRong(this.hdtb_id, this.dichvuvt_id)
      if (this.dichvuvt_id != DichVuVienThong.TSL||this.dichvuvt_id != 25) {
        this.cboVeTinh_Dau.Text = ""
        this.cboVeTinh_Cuoi.Text = ""
        let dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.hdtb_id, 2, LOAI_DV.TRAM_VT, 2)
        if (dstc && dstc.length > 0) { this.cboVeTinh.SelectedValue = parseInt(dstc[0]["donvi_id"]) }
      } else {
        this.cboVeTinh.Text = ""
        let dstc = []
        dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.hdtb_id, 2, LOAI_DV.TRAM_VT, 1)
        if (dstc && dstc.length > 0) { this.cboVeTinh_Dau.SelectedValue = parseInt(dstc[0]["donvi_id"]) }
        dstc = []
        dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.hdtb_id, 2, LOAI_DV.TRAM_VT, 2)
        if (dstc && dstc.length > 0) { this.cboVeTinh_Cuoi.SelectedValue = parseInt(dstc[0]["donvi_id"]) }
      }
      let ds_ttphieu = await api.lay_thongtin_phieu_tn(this.$auth.getPhanVungID(), this.hdtb_id)
      if (ds_ttphieu && ds_ttphieu.length > 0) {
        if (ds_ttphieu[0]["kqxl_id"] == "3") {
          this.chkThuHoi.Checked = true
        } else {
          this.chkThuHoi.Checked = false
        }
      } else {
        this.chkThuHoi.Checked = false
      }
      if ((this.cboDichVuVT.SelectedValue == DichVuVienThong.ADSL || this.cboDichVuVT.SelectedValue == DichVuVienThong.DI_DONG) &&
                        this.PHAILAM_KICHHOAT(this.quytrinh_id)) {
        this.tsbtnKichHoat.Enabled = true
      } else { this.tsbtnKichHoat.Enabled = false }
    },
    async PHAILAM_KICHHOAT (quytrinh_id) {
      // const table = "css.huonggiao_ldv a, css.luong_thaotac b, css.thaotac c"
      // const where = "a.luong_id=b.luong_id and a.thutu_id=1 and b.thaotac_id=c.thaotac_id and a.quytrinh_id=" + quytrinh_id
      // const result = "c.*, b.enable"
      // const groupby = ""
      // const dt = await api.lay_thongtin_table(table, where, result, groupby)
      const dt = await api.lay_ds_thaotac_theo_quytring_id(quytrinh_id)
      const dr = dt.find(r => r.enable == 1 && r.code == 'KICHHOAT_TD')
      return dr != undefined
    },
    async GDV_HOANTHIEN (quytrinh_id) {
      const dt = await api.getDataSQL({
        "table": "huonggiao_ldv a, luong_thaotac b, thaotac c",
        "where": "a.luong_id=b.luong_id and a.thutu_id=1 and b.thaotac_id=c.thaotac_id and a.quytrinh_id=" + quytrinh_id,
        "result": "c.*, b.enable",
        "groupby": ""
      })
      const dr = dt.find(r => r['enable'] == 1 && r['CODE'] == 'HOANTHIENHOSO')
      if (dr && dr.length > 0) { return true }
      return false
    },
    btnLayMaGD_Click () {
      this.$refs['dlgSearchContract'].showModal()
    },
    async onAcceptSearchContract (e) {
      this.txtMaGD.Text = e.ma_gd
      if (this.txtMaGD.Text) {
        this.lvwThueBao = {
          ...this.lvwThueBao,
          Items: []
        }
        const rs = await api.lay_ds_hopdong_theo_ma_gd(this.txtMaGD.Text)
        this.HienThiTTHopDongKH(rs.data)
      }
    },
    async HienThiTTMoRong (hdtb_id, dichvuvt_id) {
      let ds = []
      this.txtCuocnong.Text = ""
      this.txtDiaChiLD_Dau.Text = ""
      this.txtDiaChiLD_Cuoi.Text = ""
      this.txtSoAo_Dau.Text = ""
      this.txtSoAo_Cuoi.Text = ""
      this.tocdo_id = 0
      this.muccuoc_id = 0
      this.txtSoAo.Text = ""
      this.txtHuongKN.Text = ""
      this.txtSiteID.Text = ""
      this.txtMaHD_TTKD.Text = ""
      this.kt_kenhphu_ao = false
      this.chkCongVan.Enabled = false
      this.chkCongVan.Checked = false
      this.txtCongVan.Text = ""
      this.dtpNgayCV.Value = this.tt_nd.ngay_cn
      this.chkNgayHT.Enabled = false
      this.chkNgayHT.Checked = false
      this.dtpNgayHT.Enabled = false
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          ds = await api.lay_ds_hopdong_thuebao_codinh(hdtb_id)
          break
        case DichVuVienThong.ADSL:
          ds = await api.lay_ds_hopdong_thuebao_adsl(hdtb_id)
          break
        case DichVuVienThong.EMAIL:
          ds = await api.lay_ds_hopdong_thuebao_adsl(hdtb_id)
          break
        case DichVuVienThong.MEGA_EYES:
          ds = await api.lay_ds_hopdong_thuebao_adsl(hdtb_id)
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case 26:
          ds = await api.lay_ds_hopdong_thuebao_mgwan(hdtb_id)
          break
        case DichVuVienThong.TSL:
        case 25:
          ds = await api.lay_ds_hdtb_tsl_theo_hdtb_id(hdtb_id)
          break
        case DichVuVienThong.GPHONE:
          ds = await api.lay_ds_hopdong_thuebao_gp(hdtb_id)
          break
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          ds = await api.lay_ds_hopdong_thuebao_cntt(hdtb_id)
          break
        default:
          ds = await api.lay_ds_hopdong_thuebao_codinh(hdtb_id)
          break
      }
      if (!ds || !ds.length) {
        return
      }
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          break
        case DichVuVienThong.ADSL:
          if (ds[0]["tocdo_id"]) { this.cboTocDoADSL.SelectedValue = ds[0]["tocdo_id"] } else { this.cboTocDoADSL.Text = "" }
          this.tocdo_id = parseInt(ds[0]["tocdo_id"])
          this.muccuoc_id = parseInt(ds[0]["muccuoc_id"])
          this.txtSoAo.Text = ds[0]["ma_lt"]
          break
        case DichVuVienThong.EMAIL:
          if (ds[0]["tocdo_id"] && ds[0]["tocdo_id"]) { this.cboTocDoADSL.SelectedValue = ds[0]["tocdo_id"] } else { this.cboTocDoADSL.Text = "" }
          this.tocdo_id = parseInt(ds[0]["tocdo_id"])
          this.muccuoc_id = parseInt(ds[0]["muccuoc_id"])
          this.txtSoAo.Text = ds[0]["ma_lt"]
          break
        case DichVuVienThong.MEGA_EYES:
          if (ds[0]["tramtb_id"] && ds[0]["tramtb_id"]) { this.cboVeTinh.SelectedValue = parseInt(ds[0]["tramtb_id"]) }
          if (ds[0]["tocdo_id"] && ds[0]["tocdo_id"]) { this.cboTocDoADSL.SelectedValue = ds[0]["tocdo_id"] } else { this.cboTocDoADSL.Text = "" }
          this.tocdo_id = parseInt(ds[0]["tocdo_id"])
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case 26:
          this.vloaikenh_id = parseInt(ds[0]["loaikenh_id"])
          this.tocdo_id = parseInt(ds[0]["tocdo_id"])
          this.muccuoc_id = 0
          this.txtSoAo.Text = ds[0]["ma_lt"]
          // txtHuongKN.Text = ds[0]["huong_kn"];
          if (ds[0]["huong_kn"] && ds[0]["huong_kn"] != "") { this.txtHuongKN.Text = await api.lay_text_huongketnoi(ds[0]['huong_kn']) } else { this.txtHuongKN.Text = "" }
          this.txtMaHD_TTKD.Text = ds[0]["ma_pl"]
          this.txtSiteID.Text = ds[0]["site_id"]
          this.txtMaBSS.Text = ds[0]["ma_bss"]
          this.txtMaERP.Text = ds[0]["id_erp"]
          if (ds[0]["kp_ao"] && ds[0]["kp_ao"].trim() == "1") { this.kt_kenhphu_ao = true } else { this.kt_kenhphu_ao = false }
          this.chkCongVan.Enabled = true
          if (ds[0]["ma_cv"] && ds[0]["ma_cv"] != "") {
            this.chkCongVan.Checked = true
            this.txtCongVan.Text = ds[0]["ma_cv"]
            this.dtpNgayCV.Value = formatDate(ds[0]['ngay_cv'])
          }
          this.chkNgayHT.Enabled = true
          if (ds[0]["ngay_ycht"] && ds[0]["ngay_ycht"] != "") {
            this.chkNgayHT.Checked = true
            this.dtpNgayHT.Value = formatDate(ds[0]["ngay_ycht"])
          }
          break
        case DichVuVienThong.TSL:
        case 25:
          this.vloaikenh_id = parseInt(ds[0]["loaikenh_id"])
          this.txtMaHD_TTKD.Text = ds[0]["ma_pl"]
          this.tocdo_id = parseInt(ds[0]["tocdo_id"])
          this.muccuoc_id = 0
          if (ds[0]["daucuoi_id"] && ds[0]["daucuoi_id"] == "1") {
            this.txtDiaChiLD_Dau.Text = ds[0]["diachi"]
            this.txtSoAo_Dau.Text = ds[0]["ma_lt"]
            this.txtDiaChiLD_Cuoi.Text = ds[1]["diachi"]
            this.txtSoAo_Cuoi.Text = ds[1]["ma_lt"]
          } else {
            this.txtDiaChiLD_Dau.Text = ds[1]["diachi"]
            this.txtSoAo_Dau.Text = ds[1]["ma_lt"]
            this.txtDiaChiLD_Cuoi.Text = ds[0]["diachi"]
            this.txtSoAo_Cuoi.Text = ds[0]["ma_lt"]
          }
          this.chkCongVan.Enabled = true
          this.chkNgayHT.Enabled = true
          if (ds[0]["ma_cv"] && ds[0]["ma_cv"] != "") {
            this.chkCongVan.Checked = true
            this.txtCongVan.Text = ds[0]["ma_cv"]
            this.dtpNgayCV.Value = formatDate(ds[0]["ngay_cv"])
          }
          if (ds[0]["ngay_ycht"] && ds[0]["ngay_ycht"] != "") {
            this.chkNgayHT.Checked = true
            this.dtpNgayHT.Value = formatDate(ds[0]["ngay_ycht"])
          }
          this.txtMaBSS.Text = ds[0]["ma_bss"]
          this.txtMaERP.Text = ds[0]["id_erp"]
          break
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          this.Init_Properties(this.loaitb_id, "HDTB_CNTT", "00000")
          if (ds && ds.length > 0) {
            if (ds[0]["linhvuc_id"] && ds[0]["linhvuc_id"]) { this.linhvuc_id = parseInt(ds[0]["linhvuc_id"].trim()) }
            if (ds[0]["tocdo_id"] && ds[0]["tocdo_id"]) { this.tocdo_id = parseInt(ds[0]["tocdo_id"].trim()) }
            if (ds[0]["muccuoc_id"] && ds[0]["muccuoc_id"]) { this.muccuoc_id = parseInt(ds[0]["muccuoc_id"].trim()) }
          } else { this.linhvuc_id = 0 }
          break
      }
    },
    chkCongVan_CheckedChanged () {
      if (this.chkCongVan.Checked) {
        this.txtCongVan.Enabled = true
        this.dtpNgayCV.Enabled = true
      } else {
        this.txtCongVan.Enabled = false
        this.dtpNgayCV.Enabled = false
        this.txtCongVan.Text = ""
        this.dtpNgayCV.Value = this.tt_nd.ngay_cn
      }
    },
    bntLayMaKh_Click () {
      this.$refs.dlgSearchAccount.show(true)
    },
    tinhThiCong (ds) {
      let tinh = {
        user: '',
        pass: '',
        nhanvien_id: 0,
        donvi_id: 0,
        matinh: '',
        TNS: '',
        tinhthicong_id: 0,
        ip_dll: ''
      }
      if (ds.length > 0) {
        tinh.user = ds[0].ten_truycap
        tinh.pass = ds[0].mat_khau
        tinh.nhanvien_id = ds[0].nhanvien_id
        tinh.donvi_id = ds[0].donvi_id
        tinh.matinh = ds[0].matinh
        tinh.TNS = ds[0].tns_string
        tinh.tinhthicong_id = ds[0].tinh_id
        tinh.mavung = ds[0].ma_vung
        tinh.ip_dll = ds[0].ip_dll
      }
      return tinh
    },
    async getTtTinhThiCong (tinhId) {
      try {
        this.loading(true)
        let url = `/web-hopdong/chontinh/lay-tt-tinh-thicong?tinhId=${tinhId}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data !== undefined) {
          return response.data
        } return []
      } catch (error) {
        console.log(error)
        return []
      } finally {
        this.loading(false)
      }
    },
    async acceptChonTinhTC_TSL(value){
            let dsDau = []
            let dsCuoi = []
            if (value.tinh_dau != null) {
              dsDau = await this.getTtTinhThiCong(value.tinh_dau)
            }
            if (value.tinh_cuoi != null) {
              dsCuoi = await this.getTtTinhThiCong(value.tinh_cuoi)
            }
            this.tinh_dau = this.tinhThiCong(dsDau)
            this.tinh_cuoi = this.tinhThiCong(dsCuoi)
            console.log(111111111111)
            console.log(this.tinh_dau)
            console.log(111111111111)
            console.log(this.tinh_cuoi)
    },
    async txtMaTB_KeyPress (e) {
      if (e.charCode !== 13 || !this.txtMaTB.Text) {
        return
      }
      try {
        this.tinh = await api.lay_tinh_theo_matb(this.txtMaTB.Text)    
        if (this.tinh.tinhthicong_id == 0) { 
          if (this.dichvuvt_id != DichVuVienThong.METRONET &&
          this.dichvuvt_id != DichVuVienThong.MEGAWAN &&
          this.dichvuvt_id != DichVuVienThong.TSL&&this.dichvuvt_id != 25&&this.dichvuvt_id != 26)
          {
            this.$toast.error("Thuê bao chưa map bán chéo tại tỉnh thi công.");
            this.Clear();
            return;
          }
          this.kenh_trang = true
          this.chi_giao_net = false
          const cf = await this.$bvModal.msgBoxConfirm("Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !")
          if (cf) {
            this.kenh_trang = true
            this.chi_giao_net = false
            if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25)
            {
              this.$refs.chonTinhTSL.showModal()
            }
            else
              {
                const rsTinh = await this.$refs.dlgChonTinh.showModal()
                if (rsTinh) {
                  this.tinh = { tinhthicong_id: rsTinh }
                } else {
                  this.Clear()
                  return
                }
              }
          } else {
            this.Clear()
            return
          }
        } else {
          this.kenh_trang = false
          let dtb = await api.lay_thuebao_kn_id_theo_matb(this.txtMaTB.Text)
          if (dtb && dtb.length > 0 && dtb[0]["thuebao_kn_id"] && dtb[0]["thuebao_kn_id"] == "-1") {
            this.chi_giao_net = true
            if (this.cboDichVuVT.SelectedValue == DichVuVienThong.TSL||this.cboDichVuVT.SelectedValue ==25) {
              const dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
              if (!dtTinh_id_tsl || dtTinh_id_tsl.length == 0) {
                this.$toast.error("Không tìm thấy thuê bao với dịch vụ TSL")
                return
              }
              const tinhDau = dtTinh_id_tsl.find(item => item.daucuoi_id == 1)
              const tinhCuoi = dtTinh_id_tsl.find(item => item.daucuoi_id == 2)
              let tinh_dau_id = parseInt(tinhDau ? tinhDau.tinh_thicong : 0)
              let tinh_cuoi_id = parseInt(tinhCuoi ? tinhCuoi.tinh_thicong : 0)
              this.tinh_dau = await api.lay_tinh_thi_cong(tinh_dau_id)
              this.tinh_cuoi = await api.lay_tinh_thi_cong(tinh_cuoi_id)
            }
          } else {
            this.chi_giao_net = false
            if (this.cboDichVuVT.SelectedValue == DichVuVienThong.TSL||this.cboDichVuVT.SelectedValue == 25) {
              const dtTinh_id_tsl = await api.lay_tinhtc_id_tsl(this.txtMaTB.Text, this.cboDichVuVT.SelectedValue)
              if (!dtTinh_id_tsl || dtTinh_id_tsl.length == 0) {
                this.$toast.error("Không tìm thấy thuê bao với dịch vụ TSL")
                return
              }
              const tinhDau = dtTinh_id_tsl.find(item => item.daucuoi_id == 1)
              const tinhCuoi = dtTinh_id_tsl.find(item => item.daucuoi_id == 2)
              let tinh_dau_id = parseInt(tinhDau ? tinhDau.tinh_thicong : 0)
              let tinh_cuoi_id = parseInt(tinhCuoi ? tinhCuoi.tinh_thicong : 0)
              this.tinh_dau = await api.lay_tinh_thi_cong(tinh_dau_id)
              this.tinh_cuoi = await api.lay_tinh_thi_cong(tinh_cuoi_id)
            }
          }
        }
        this.loading(true)
        if (!this.KiemTra_TonTai_HD(this.txtMaTB.Text)) {
          let ds = await api.lay_ds_hopdong_theo_ma_tb(this.txtMaTB.Text, LoaiHopDong.CHAMDUT_SD, +this.tt_nd.donvi_id, TrangThaiHD.MOI, this.tt_nd.nhanvien_id, 0, this.dichvuvt_id, 0)
          if (ds && ds.length > 0) {
            if (this.lvwThueBao.Items.length > 0) {
              this.lvwThueBao = {
                ...this.lvwThueBao,
                Items: []
              }
            }
            this.thuebao_id = parseInt(ds[0]["thuebao_id"])
            if (this.tinh && this.tinh.tinhthicong_id > 0) {
              this.thuebao_tc_id = await api.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
            }
            console.log("HienThiTTHopDongKH")
            await this.HienThiTTHopDongKH(ds)
          } else {
            console.log("HienThiTT_DanhBa")
            await this.HienThiTT_DanhBa(this.txtMaTB.Text)
            this.hdtb_id = 0
          }
        }
      } catch (ex) {
        let errMsg = 'Không xác định'
        if (ex.data) {
          errMsg = ex.data.message
        } else if (ex.message) {
          errMsg = ex.message
        } else if (ex.statusText) {
          errMsg = ex.statusText
        }
        this.$toast.error("Có lỗi: " + errMsg)
        console.error(ex)
      } finally {
        this.loading(false)
      }
    },
    KiemTra_TonTai_HD (matb) {
      const item = this.lvwThueBao.Items.find(i => i['ma_tb'] == matb && i['dichvuvt_id'] == this.cboDichVuVT.SelectedValue)
      return !!item
    },
    async HienThiTT_DanhBa (ma_tb) {
      this.txtCuocnong.Text = ""
      let ds = []
      if (this.tinh && this.tinh.tinhthicong_id > 0 && this.kenh_trang == false) {
        ds = await api.lay_danhba_theo_matb(ma_tb, this.dichvuvt_id, 0)
      } else {
        ds = await api.lay_danhba_theo_matb(ma_tb, this.dichvuvt_id, 0)
      }
      this.dsDBTB = ds
      if (ds && ds.length > 0) {
        if (ds.length > 1) {
          // TODO: mo Form
          // frmDSTBCungMa f = new frmDSTBCungMa();
          // f.ds = ds;
          // if (f.ShowDialog() == DialogResult.OK)
          //     ds = f.ds;
        }
        const dsLapHdKhac = await api.kiemtra_ma_tb_lap_hopdong_khac(ma_tb, "0", +this.cboDichVuVT.SelectedValue, LoaiHopDong.CHAMDUT_SD)
        if (dsLapHdKhac && dsLapHdKhac.length > 0) {
          this.$toast.warning(`Thuê bao ${dsLapHdKhac[0]['ma_tb']} đã lập hợp đồng khác. Tên hợp đồng: ${dsLapHdKhac[0]['ten_loaihd']}`)
          return
        }
        if (ds[0]["trangthaitb_id"] == TrangThaiTB.THANHLY ||
              ds[0]["trangthaitb_id"] == TrangThaiTB.THANHLY_NO ||
              ds[0]["trangthaitb_id"] == TrangThaiTB.DOISO) {
          this.$toast.error("Thuê bao có mã: " + ds[0]["ma_tb"] +
                                  " đang ở trạng thái: " + ds[0]["trangthai_tb"] +
                                  "\nBạn không thể thực hiện thanh lý thuê bao này")
          return
        }
        let ds_km = []
        ds_km = await api.lay_ds_chitiet_km_theo_tbid(ds[0]["thuebao_id"], 5)
        if (ds_km && ds_km.length > 0) {
          // let tb = "Thuê bao " + ds[0]["ma_tb"]
          // tb += " đang hưởng khuyến mại : \n " + ds_km[0]["ten_km"]
          // tb += " \n    Có chi tiết khuyến mại : "
          // for (let i = 0; i < ds_km.length; i++) {
          //   tb += "\n     - " + ds_km[i]["ten_ctkm"]
          // }
          // tb += "\nBạn không được thực hiện tiếp với thuê bao này!"
          // this.$toast.error(tb)
          // return
          let tb = 'Thuê bao ' + this.thuebao_id
            tb += ' đang hưởng khuyến mại : \n ' + ds_km[0].ten_km
            tb += ' \n    Có chi tiết khuyến mại : '
            for (let i = 0; i < ds_km.length; i++) {
              tb += '\n     - ' + ds_km[i]['ten_ctkm']
            }
            tb += '\nBạn có muốn tiếp tục thực hiện?'
            this.$alert(tb, 'Thông báo', {
              dangerouslyUseHTMLString: true,
              showCancelButton: true,
              confirmButtonText: 'Đồng ý',
              cancelButtonText: 'Hủy bỏ',
              showCloseButton: true,
              type: 'info'
            })
              .then(async (v) => {
                if (!v) {
                  return
                }
              })
              .catch(() => {
                this.setButton(1)
                this.kiemtra = false
              })
        }
        this.thuebao_id = parseInt(ds[0]["thuebao_id"])
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id ==25) {
          if (this.tinh_cuoi && this.tinh_cuoi.tinhthicong_id > 0 && this.tinh_dau.tinhthicong_id > 0) {
            if (!this.kenh_trang) {
              this.thuebao_tc_id = await api.lay_thuebaoid_thicong(this.tinh_dau.tinhthicong_id, ds[0]["thuebao_id"])
              const thuebao_tc_id_tsl = await api.lay_thuebaoid_thicong(this.tinh_cuoi.tinhthicong_id, ds[0]["thuebao_id"])
              this.dsDBTBBC[0] = await api.bancheo_lay_danhba_theo_thuebaoid(this.tinh_dau.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.SelectedValue, 0, 2)
              this.dsDBTBBC[1] = await api.bancheo_lay_danhba_theo_thuebaoid(this.tinh_cuoi.tinhthicong_id, thuebao_tc_id_tsl, this.cboDichVuVT.SelectedValue, 0, 2)
              this.dsDBTB = ds
            } else {
              this.thuebao_tc_id = 0
              this.dsDBTBBC = []
            }
          }
        } else if (this.tinh && this.tinh.tinhthicong_id > 0 && this.kenh_trang == false && this.chi_giao_net == false) {
          this.thuebao_tc_id = await api.lay_thuebaoid_thicong(this.tinh.tinhthicong_id, this.thuebao_id)
          const ds = await api.bancheo_lay_danhba_theo_thuebaoid(this.tinh.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.SelectedValue, 0, 2)
          console.log(343434343434)
          console.log(ds)
          if (Object.keys(ds).length !== 0) {
            this.dsDBTBBC[0] = ds
            this.isFormValid = true
          } else {
            this.$toast.error("Không lấy được thông tin thuê bao tỉnh thi công, liên hệ admin để xử lý.")
            this.isFormValid = false
          }
        } else {
          this.thuebao_tc_id = 0
          this.dsDBTBBC = []
        }
        if (ds[0] && ds[0].thuebao_cha_id) {
          this.h_thuebao_cha_id = ds[0]["thuebao_cha_id"]
        } else {
          this.h_thuebao_cha_id = 0
        }
        this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
        // #region "Load thông tin danh bạ vào form
        // Thông tin khách hàng
        this.txtMaKH.Text = ds[0]["ma_kh"]
        this.txtMaHD.Text = ds[0]["ma_hd"]
        this.khachhang_id = parseInt(ds[0]["khachhang_id"])
        this.txtTenKH.Text = ds[0]["ten_kh"]
        this.txtDiaChiKH.Text = ds[0]["diachi_kh"]
        this.txtDiaChiTT.Text = ds[0]["diachi_tt"]
        this.dtpNgayLapHD.Value = ds[0]["ngaylap_hd"]
        this.so_dt = ds[0]["so_dt"]
        this.so_gt = ds[0]["so_gt"]
        // Thông tin thuê bao
        this.txtMaTB.Text = ds[0]["ma_tb"]
        this.txtTenTB.Text = ds[0]["ten_tb"]
        this.txtDiaChiTB.Text = ds[0]["diachi_tb"]
        this.txtDiaChiLD.Text = ds[0]["diachi_ld"]
        this.loaitb_id = parseInt(ds[0]["loaitb_id"])
        this.donvi_id = parseInt(ds[0]["donviql_id"])
        this.doituong_id = parseInt(ds[0]["doituong_id"])
        this.cboLoaihinhTB.SelectedValue = ds[0]["loaitb_id"]
        try {
          //let vthanhtoan_id = await api.map_id("thanhtoan_id", "db_thuebao", "where thuebao_id =" + this.thuebao_id)
          let input= {
              type:1,
              param: this.thuebao_id
            }
          let  vthanhtoan_id = this.GetData(await api.fn_tt_db_thuebao(this.axios,input));
          if (vthanhtoan_id != "-1") {
            let no_tt = await api.tracuu_no_thanhtoan(vthanhtoan_id)
            this.txtTienNo.EditValue = "0"
            if (no_tt > 0) {
              if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL|| this.dichvuvt_id == 25 ||this.dichvuvt_id == 26 ||
                          this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.METRONET ||
                          this.loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || this.loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
                if (this.ts_khong_kt_nocuoc != 1) {
                  let tienno = this.TraCuu_No_MaTB()
                  this.txtTienNo.Text = formatCurrency(tienno)
                  if (tienno > 0) {
                    this.$toast.error("Thuê bao: " + this.txtMaTB.Text +
                                  " còn nợ: " + formatCurrency(tienno) +
                                  " vnđ.\nPhải thanh toán trước khi thực hiện!")
                    return
                  }
                }
              } else {
                let tienno = this.TraCuu_No_MaTB()
                if (tienno > 0) {
                  this.$toast.error("Thuê bao: " + this.txtMaTB.Text +
                              " còn nợ: " + formatCurrency(tienno) +
                              " vnđ.\nPhải thanh toán trước khi thực hiện!")
                  return
                }
              }
            }
          }
        } catch (e) {
        }
        if (this.cboDichVuVT.SelectedValue == DichVuVienThong.DI_DONG) {
          this.goicuoc_id = parseInt(ds[0]["goicuoc_id"])
          let tmp = ""
          tmp = ds[0]["doituong_id_neo"]
          if (!tmp || !tmp.trim()) { this.doituong_id_neo = 1 } else { this.doituong_id_neo = ds[0]["doituong_id_neo"] }
          this.simcard = ds[0]["simcard"]
        }
        this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id)
        if ((this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL|| this.dichvuvt_id == 25|| this.dichvuvt_id == 26 || this.dichvuvt_id == DichVuVienThong.METRONET) && this.doitac_id != 0) {
          //let str_doitac = await api.map_id("ten_dt", "doitac", "where doitac_id = " + this.doitac_id)
          let input= {
              type:1,
              param: this.doitac_id
            }
          let  str_doitac = this.GetData(await api.fn_tt_doitac(this.axios,input));
          this.$toast.error("Thuê bao này có  Hợp đồng/PLHĐ với đối tác " + str_doitac)
        }
        this.cboVeTinh_Dau.Text = ""
        this.cboVeTinh_Cuoi.Text = ""
        if (this.dichvuvt_id != DichVuVienThong.TSL&&this.dichvuvt_id != 25) {
          this.cboVeTinh_Dau.Text = ""
          this.cboVeTinh_Cuoi.Text = "" 
          let dstc = []
          dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.thuebao_id, 1, LOAI_DV.TRAM_VT, 2)
          if (dstc && dstc.length > 0) { this.cboVeTinh.SelectedValue = parseInt(dstc[0]["donvi_id"]) }
        } else {
          this.cboVeTinh.Text = ""
          let dstc = []
          dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.hdtb_id, 1, LOAI_DV.TRAM_VT, 1)
          if (dstc && dstc.length > 0) { this.cboVeTinh_Dau.SelectedValue = dstc[0]["donvi_id"] }
          dstc = []
          dstc = await api.Lay_DonVi_ThiCong(this.$auth.getPhanVungID(), this.hdtb_id, 1, LOAI_DV.TRAM_VT, 2)
          if (dstc && dstc.length > 0) { this.cboVeTinh_Cuoi.SelectedValue = dstc[0]["donvi_id"] }
        }
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
        this.gan_thongso_theodanhba(1, this.dichvuvt_id, this.thuebao_id)
        if (this.thuebao_tc_id > 0) { this.gan_thongso_theodanhba(2, this.dichvuvt_id, this.thuebao_tc_id) }
        // khong co btnDiemDau, btnDiemCuoi de hien thi nen comment doan nay
        if (this.thuebao_tc_id == 0 && (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25)) { this.HienThiNutTS_TSL(1) } else { this.HienThiNutTS_TSL(0) }
        // TODO: wait API
        // if (new TraCuuDanhBaFacade().CANHBAO_TB_KHUYENMAI(thuebao_id) == 1)
        // {
        //     this.$toast.error("Thuê bao này đang trong thời gian khuyến mãi !");
        // }
        // Lay danh sach thue bao cung ma doi cap de hien thi ghi chucn
        let ghichu = ""
        if (this.cboDichVuVT.SelectedValue != DichVuVienThong.DI_DONG && this.cboDichVuVT.SelectedValue != DichVuVienThong.GPHONE &&
              this.cboDichVuVT.SelectedValue != DichVuVienThong.DICHVU_CNTT) {
          let dsTB = await api.lay_ds_tb_cung_madoicap(this.thuebao_id, 1)
          if (dsTB && dsTB.length > 1) {
            ghichu += "Thuê bao " + this.txtMaTB.Text + " đang nằm trên cùng đôi cáp với các thuê bao: "
            for (let i = 0; i < dsTB.length; i++) {
              if (dsTB[i]["ma_tb"] == this.txtMaTB.Text) { continue }
              ghichu += dsTB[i]["ma_tb"] + ","
            }
            ghichu = ghichu.substring(0, ghichu.length - 1) + ".\n\n"
          }
        }
        this.txtGhiChu.Text = ghichu
        this.lvLyDoHuy.Items = []
        this.dsTemp = []
        this.hdtb_id = 0
        this.thueBaoTonTai = true
        // TODO: them canh bao thiet hai vao UI
        // //KieuNV|26/05/2017: Hiện thông báo cảnh báo thiệt hại
        // lblCanhBao.Visible = false;
        // if (new ThamSo_MDFacade().IS_EXIST("CANH_BAO_THIET_HAI_FIBER"))
        // {
        //     try
        //     {
        //         if (loaitb_id == LoaiHinhTB.INTERNET_FTTH)
        //         {
        //             var smsCanhBaoThietHai = new TraCuuChungFacade().LAY_ND_SMS_CANHBAO_THIETHAI(txtMaTB.Text.trim(), false, true);
        //             if (!string.IsNullOrEmpty(smsCanhBaoThietHai))
        //             {
        //                 lblCanhBao.Text = smsCanhBaoThietHai.Replace("CANH BAO THIET HAI: ", "");
        //                 lblCanhBao.Visible = true;
        //             }
        //             else
        //             {
        //                 lblCanhBao.Visible = false;
        //             }
        //         }
        //         else
        //         {
        //             lblCanhBao.Visible = false;
        //         }
        //     }
        //     catch (e)
        //     {
        //     }
        // }
      } else {
        this.$toast.error("Không tìm thấy thông tin về mã thuê bao " + this.txtMaTB.Text + " đối với dịch vụ " + this.cboDichVuVT.Items.find(item => item.dichvuvt_id === this.cboDichVuVT.SelectedValue).ten_dvvt)
        this.thueBaoTonTai = false
      }
    },
    async LAY_DS_ISDN_THEO_THUEBAO_ID (vthuebao_id) {
      try {
        this.isdn_sochinh = false
        this.matb_isdn_chinh = ""
        this.ds_isdn = await api.lay_ds_isdn_theo_thuebao_id(vthuebao_id) || []
        for (let i = 0; i < this.ds_isdn.length; i++) {
          if (this.ds_isdn[i]["thuebao_id"] == vthuebao_id) {
            if (!this.ds_isdn[i]["thuebao_cha_id"]) {
              // TODO: Không tìm thấy lblTB_ISDN label trên UI
              // lblTB_ISDN.Text = "Số PABX chính";
              this.isdn_sochinh = true
            } else {
              // TODO: Không tìm thấy lblTB_ISDN label trên UI
              // lblTB_ISDN.Text = "Số PABX phụ";
            }
          }
          if (!this.ds_isdn[i]["thuebao_cha_id"]) {
            this.matb_isdn_chinh = this.ds_isdn[i]["ma_tb"]
          }
        }
      } catch (ex) {
        this.$toast.error("Có lỗi:" + ex)
        console.error(ex)
      }
    },
    async TraCuu_No_MaTB () {
      let result = 0
      try {
        if (this.thuebao_id != 0) {
          result = await api.tracuu_no_thuebao(this.thuebao_id)
        } else {
          this.$toast.error("Chưa có thông tin về thuê bao!")
        }
      } catch (ex) {
        this.$toast.error("Có lỗi:" + ex)
        console.error(ex)
      }
      return result
    },
    async HienThiTTDBMoRong (thuebao_id, dichvuvt_id) {
      let ds = []
      this.txtCuocnong.Text = ""// Hungvm
      this.txtDiaChiLD_Dau.Text = ""
      this.txtDiaChiLD_Cuoi.Text = ""
      this.txtSoAo_Dau.Text = ""
      this.txtSoAo_Cuoi.Text = ""
      this.tocdo_id = 0
      this.muccuoc_id = 0
      this.txtSoAo.Text = ""
      this.txtHuongKN.Text = ""
      this.doitac_id = 0
      this.txtMaHD_TTKD.Text = ""
      this.txtSiteID.Text = ""
      this.kt_kenhphu_ao = false
      this.chkCongVan.Enabled = false
      this.chkCongVan.Checked = false
      this.txtCongVan.Text = ""
      this.dtpNgayCV.Value = this.tt_nd.ngay_cn
      this.chkNgayHT.Enabled = false
      this.chkNgayHT.Checked = false
      this.dtpNgayHT.Enabled = false
      switch (dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          ds = await api.lay_ds_db_cd_theo_tbid(thuebao_id)
          break
        case DichVuVienThong.TSL:
        case 25:
          ds = await api.lay_ds_db_tsl_theo_tbid(thuebao_id, this.$auth.getPhanVungID())
          break
        case DichVuVienThong.ADSL:
          ds = await api.lay_ds_danhba_adsl(thuebao_id)
          break
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          ds = await api.lay_ds_db_cntt_theo_tbid(thuebao_id)
          break
        case DichVuVienThong.EMAIL:
          ds = await api.lay_ds_danhba_adsl(thuebao_id)
          break
        case DichVuVienThong.MEGAWAN:
        case DichVuVienThong.METRONET:
        case 26:
          ds = await api.lay_ds_db_megawan_theo_tbid(thuebao_id)
          break
        case DichVuVienThong.GPHONE:
          ds = await api.lay_ds_db_gp_theo_tbid(thuebao_id)
          break
        case DichVuVienThong.MEGA_EYES:
          ds = await api.LAY_DS_DB_MEGAEYES_THEO_TBID(thuebao_id)
          break
        case 50:
          ds = await api.lay_ds_db_cntt_theo_tbid(thuebao_id)
          break
        default:
          ds = await api.lay_ds_db_cd_theo_tbid(thuebao_id)
          break
      }
      if (ds && ds.length > 0) {
        switch (dichvuvt_id) {
          case DichVuVienThong.CO_DINH:
            if (ds[0]["tg_thueso"]) { this.cboKieuLD.SelectedValue = KieuLapDat.THANHLY_THUESO_DT } else { this.cboKieuLD.SelectedValue = KieuLapDat.THANH_LY_CODINH }
            break
          case DichVuVienThong.ADSL:
            if (ds[0]["tocdo_id"]) {
              this.tocdo_id = ds[0]["tocdo_id"]
              this.cboTocDoADSL.SelectedValue = this.tocdo_id
            } else { this.cboTocDoADSL.Text = "" }
            if (ds[0]["muccuoc_id"]) { this.muccuoc_id = ds[0]["muccuoc_id"] }
            this.txtSoAo.Text = ds[0]["ma_lt"]
            break
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            this.Init_Properties(this.loaitb_id, "HDTB_CNTT", "00000")
            if (ds && ds.length > 0) {
              if (ds[0]["linhvuc_id"]) { this.linhvuc_id = ds[0]["linhvuc_id"] }
              if (ds[0]["tocdo_id"]) { this.tocdo_id = ds[0]["tocdo_id"] }
              if (ds[0]["muccuoc_id"]) { this.muccuoc_id = ds[0]["muccuoc_id"] }
            } else { this.linhvuc_id = 0 }
            break
          case DichVuVienThong.EMAIL:
            if (ds[0]["tocdo_id"]) {
              this.tocdo_id = ds[0]["tocdo_id"]
              this.cboTocDoADSL.SelectedValue = this.tocdo_id
            } else { this.cboTocDoADSL.Text = "" }
            if (ds[0]["muccuoc_id"]) { this.muccuoc_id = ds[0]["muccuoc_id"] }
            this.txtSoAo.Text = ds[0]["ma_lt"]
            break
          case DichVuVienThong.MEGA_EYES:
            if (ds[0]["tramtb_id"]) { this.cboVeTinh.SelectedValue = parseInt(ds[0]["tramtb_id"]) }
            if (ds[0]["tocdo_id"]) {
              this.tocdo_id = ds[0]["tocdo_id"]
              this.cboTocDoADSL.SelectedValue = this.tocdo_id
            } else { this.cboTocDoADSL.Text = "" }
            break
          case DichVuVienThong.MEGAWAN:
          case DichVuVienThong.METRONET:
          case 26:
            if (ds[0]["tocdo_id"]) {
              this.tocdo_id = ds[0]["tocdo_id"]
              this.cboTocDoADSL.SelectedValue = this.tocdo_id
            }
            this.txtSoAo.Text = ds[0]["ma_lt"]
            if (ds[0]["huong_kn"] != "") { this.txtHuongKN.Text = await api.lay_text_huongketnoi(ds[0]["huong_kn"]) } else { this.txtHuongKN.Text = "" }
            this.txtMaHD_TTKD.Text = ds[0]["ma_pl"]
            this.txtSiteID.Text = ds[0]["site_id"]
            this.txtMaBSS.Text = ds[0]["ma_bss"]
            this.txtMaERP.Text = ds[0]["id_erp"]
            if (ds[0]["doitac_id"] != "") { this.doitac_id = ds[0]["doitac_id"] }
            if (ds[0]["kp_ao"] == "1") { this.kt_kenhphu_ao = true } else { this.kt_kenhphu_ao = false }
            this.chkCongVan.Enabled = true
            this.dtpNgayCV.Value = this.tt_nd.ngay_cn
            this.chkNgayHT.Enabled = true
            this.dtpNgayHT.Value = this.tt_nd.ngay_cn
            this.vloaikenh_id = ds[0]["loaikenh_id"]
            break
          case DichVuVienThong.TSL:
          case 25:
            this.txtMaHD_TTKD.Text = ds[0]["ma_pl"]
            this.vloaikenh_id = ds[0]["loaikenh_id"]
            if (ds[0]["tocdo_id"]) {
              this.tocdo_id = ds[0]["tocdo_id"]
              this.cboTocDoADSL.SelectedValue = this.tocdo_id
            }
            if (ds[0]["doitac_id"] != "") { this.doitac_id = ds[0]["doitac_id"] }
            if (+ds[0]["daucuoi_id"] == 1) {
              this.txtDiaChiLD_Dau.Text = ds[0]["diachi"]
              this.txtSoAo_Dau.Text = ds[0]["ma_lt"]
              this.txtDiaChiLD_Cuoi.Text = ds[1]["diachi"]
              this.txtSoAo_Cuoi.Text = ds[1]["ma_lt"]
            } else {
              this.txtDiaChiLD_Dau.Text = ds[1]["diachi"]
              this.txtSoAo_Dau.Text = ds[1]["ma_lt"]
              this.txtDiaChiLD_Cuoi.Text = ds[0]["diachi"]
              this.txtSoAo_Cuoi.Text = ds[0]["ma_lt"]
            }
            this.txtMaBSS.Text = ds[0]["ma_bss"]
            this.txtMaERP.Text = ds[0]["id_erp"]
            this.chkCongVan.Enabled = true
            this.chkNgayHT.Enabled = true
            this.dtpNgayCV.Value = this.tt_nd.ngay_cn
            this.dtpNgayHT.Value = this.tt_nd.ngay_cn
            break
          case 50: // Dich vu CNTT
            this.Init_Properties(this.loaitb_id, "HDTB_CNTT", "00000")
            if (ds && ds.length > 0) {
              if (ds[0]["linhvuc_id"]) { this.linhvuc_id = ds[0]["linhvuc_id"] }
            } else { this.linhvuc_id = 0 }
            this.tocdo_id = 0
            break
        }
      }
    },
    async Init_Properties (vloaitb_id, table_name, opt_list) {
      try {
        let ds = []
        ds = await api.lay_danhsach_thuoctinh_v2(vloaitb_id, table_name, opt_list)
        this.ItemList = []
        if (!ds || ds.length == 0) { return }
        for (let i = 0; i < ds.length; i++) {
          this.ItemList.push({
            sFieldName: ds[i].field_name,
            sCaption: ds[i].ten_hienthi,
            iAtt: ds[i].att,
            iDataType: ds[i].data_type,
            sMaxValue: ds[i].max_value,
            sMinvalue: ds[i].min_value,
            sValue: ds[i].field_value,
            nFieldLength: ds[i].field_length,
            bIsNotNull: ds[i].field_nullable,
            bIsUnique: ds[i].field_unique,
            sLookUpTableName: ds[i].parent_table,
            sLookUpKeyField: ds[i].parent_keyfield,
            sLookUpValueField: ds[i].parent_descfield,
            strDepandField: ds[i].depend_field,
            StrSql: ds[i].lookup_sql
          })
        }
      } catch (ex) {
        this.$toast.error("Có lỗi:" + ex)
        console.error(ex)
      }
    },
    Reset_Thongso_Thuebao () {
      return {
        trangbi_id: 0,
        muccuoc_id: 0,
        chuquan_id: 0,
        congnghe_id: 0,
        doituong_id: 0,
        kieuld_id: 0,
        kieutra_id: 0,
        loaikenh_id: 0,
        loaimd_id: 0,
        muccuoctb_id: 0,
        thietbidc_id: 0,
        thoihan_id: 0,
        tocdo_id: 0,
        tocdo_isp: 0,
        tocdo_pir_id: 0,
        tocdothuc_id: 0,
        ma_doicap: "",
        ma_tn: ""
      }
    },
    async gan_thongso_theodanhba (vkieu, vdichvu, vthuebaoid) {
      let ds = []
      if (vdichvu == 1) {
        if (vkieu == 1) {
          ds = await api.lay_ds_db_cd_theo_tbid(vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
            this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
            this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
          }
        } else {
          ds = await api.lay_ds_db_cd_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
            if (this.dsDBTBBC && this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
              this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
            }
          }
        }
      }
      // dichvu vu co dinh
      if (vdichvu == 11) {
        if (vkieu == 1) {
          ds = await api.lay_ds_danhba_ims(vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
            this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
            this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
          }
        } else {
          ds = await api.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
            if (this.dsDBTBBC && this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
              this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
            }
          }
        }
      }
      // dich vu megwan metronet
      if (vdichvu == 7 || vdichvu == 8|| vdichvu == 26) {
        if (vkieu == 1) {
          let exc = ""
          try {
            ds = await api.lay_ds_db_megawan_theo_tbid(vthuebaoid)
            if (ds && ds.length > 0) {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]
              if (ds[0]["tocdo_pir_id"] != null && ds[0]["tocdo_pir_id"] != "") { this.thongso_ban.tocdo_pir_id = ds[0]["tocdo_pir_id"] }
              if (ds[0]["tocdo_isp"] != null && ds[0]["tocdo_isp"] != "") { this.thongso_ban.tocdo_isp = ds[0]["tocdo_isp"] }
              if (ds[0]["tocdo_nix"] != null && ds[0]["tocdo_nix"] != "") { this.thongso_ban.tocdo_nix = ds[0]["tocdo_nix"] }
              exc = "loaimd_id"
              this.thongso_ban.loaimd_id = ds[0]["loaimd_id"]
              exc = "loaikenh_id"
              this.thongso_ban.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "thoihan_id"
              this.thongso_ban.thoihan_id = ds[0]["thoihan_id"]
              exc = "muccuoc_id"
              if (ds.find(item => typeof item.muccuoc_id != "undefined")) {
                if (ds[0]["muccuoc_id"] != null && ds[0]["muccuoc_id"] != "") { this.thongso_ban.muccuoc_id = ds[0]["muccuoc_id"] }
              }
              exc = "congnghe_id"
              if (ds[0]["congnghe_id"] != null && ds[0]["congnghe_id"] != "") { this.thongso_ban.congnghe_id = ds[0]["congnghe_id"] }
              exc = "thietbidc_id"
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "doituong_id"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "mucuoctb_id"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
            }
          } catch (e) {
            this.$toast.error("Có lỗi trong việc lấy dữ liệu tại trường: " + exc.toUpperCase())
          }
        } else {
          ds = await api.lay_ds_db_megawan_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
              exc = "TOCDO_ID"
              this.thongso_tc.tocdo_id = ds[0]["tocdo_id"]
              if (ds[0]["tocdo_pir_id"] != null && ds[0]["tocdo_pir_id"] != "") { this.thongso_tc.tocdo_pir_id = ds[0]["tocdo_pir_id"] }
              if (ds[0]["tocdo_isp"] != null && ds[0]["tocdo_isp"] != "") { this.thongso_tc.tocdo_isp = ds[0]["tocdo_isp"] }
              if (ds[0]["tocdo_nix"] != null && ds[0]["tocdo_nix"] != "") { this.thongso_tc.tocdo_nix = ds[0]["tocdo_nix"] }
              exc = "LOAIMD_ID"
              this.thongso_tc.loaimd_id = ds[0]["loaimd_id"]
              exc = "LOAIKENH_ID"
              this.thongso_tc.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "THOIHAN_ID"
              this.thongso_tc.thoihan_id = ds[0]["thoihan_id"]
              exc = "MUCCUOC_ID"
              if (ds.find(item => typeof item.muccuoc_id != "undefined")) {
                if (ds[0]["muccuoc_id"] != null && ds[0]["muccuoc_id"] != "") { this.thongso_tc.muccuoc_id = ds[0]["muccuoc_id"] }
              }
              exc = "CONGNGHE_ID"
              if (ds[0]["congnghe_id"] != null && ds[0]["congnghe_id"] != "") { this.thongso_tc.congnghe_id = ds[0]["congnghe_id"] }
              exc = "THIETBIDC_ID"
              this.thongso_tc.thietbidc_id = ds[0]["thietbidc_id"]
              if (this.dsDBTBBC && this.dsDBTBBC.length > 0) {
                exc = "DOITUONG_ID"
                this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
                exc = "MUCUOCTB_ID"
                this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
            // TODO: kiem tra lai tinh
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại trường: " + exc)
            }
          }
        }
      }
      // dich vu băng rộng
      if (vdichvu == 4) {
        if (vkieu == 1) {
          ds = await api.lay_ds_danhba_adsl(vthuebaoid)
          ds = []
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]
              exc = "tocdo_id"
              this.thongso_ban.tocdothuc_id = ds[0]["tocdo_id"]
              exc = "muccuoc_id"
              this.thongso_ban.muccuoc_id = ds[0]["muccuoc_id"]
              exc = "kieutra_id"
              this.thongso_ban.kieutra_id = ds[0]["kieutra_id"]
              exc = "thietbidc_id"
              // this.thongso_ban.congnghe_id = Convert.ToInt32(ds[0]["congnghe_id"].ToString());
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "doituong_id"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "mucuoctb_id"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
            } catch (e) {
            // TODO: kiem tra lai tt_nd.tentat
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }
        } else {
          ds = await api.lay_ds_db_adsl_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
              exc = "tocdo_id"
              this.thongso_tc.tocdo_id = ds[0]["tocdo_id"]
              exc = "tocdo_id"
              this.thongso_tc.tocdothuc_id = ds[0]["tocdo_id"]
              exc = "muccuoc_id"
              this.thongso_tc.muccuoc_id = ds[0]["muccuoc_id"]
              exc = "kieutra_id"
              this.thongso_tc.kieutra_id = ds[0]["kieutra_id"]
              exc = "thietbidc_id"
              this.thongso_tc.thietbidc_id = ds[0]["thietbidc_id"]
              if (this.dsDBTBBC && this.dsDBTBBC.length > 0) {
                exc = "doituong_id"
                this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
                exc = "mucuoctb_id"
                this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại " + (this.tinh && this.tinh.matinh ? this.tinh.matinh : "") + " ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }
        }
      }
      // dich vu TSL
      if (vdichvu == 9 || vdichvu == 25) {
        if (vkieu == 1) {
          ds = await api.lay_ds_db_tsl_theo_tbid(vthuebaoid,this.$root.token.getPhanVungID())
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_ban.trangbi_id = ds[0]["trangbi_id"]
              exc = "TOCDO_ID"
              this.thongso_ban.tocdo_id = ds[0]["tocdo_id"]
              exc = "LOAIKENH_ID"
              this.thongso_ban.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "THIETBIDC_ID"
              this.thongso_ban.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "THIETBIDC_ID"
              this.thongso_ban.thietbidc_id_cuoi = ds[1]["thietbidc_id"]
              exc = "DOITUONG_ID"
              this.thongso_ban.doituong_id = this.dsDBTB[0]["doituong_id"]
              exc = "MUCUOCTB_ID"
              this.thongso_ban.muccuoctb_id = this.dsDBTB[0]["mucuoctb_id"]
            } catch (e) {
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại " + (this.tinh && this.tinh.matinh ? this.tinh.matinh : "") + " ID-ERR: " + vthuebaoid + " trường: " + exc.toUpperCase())
            }
          }
        } else {
          ds = await api.lay_ds_db_tsl_theo_tbid_bc(vthuebaoid,this.tinh.tinhthicong_id)
          if (ds && ds.length > 0) {
            let exc = ""
            try {
              exc = "TRANGBI_ID"
              this.thongso_tc.trangbi_id = ds[0]["trangbi_id"]
              exc = "TOCDO_ID"
              this.thongso_tc.tocdo_id = ds[0]["tocdo_id"]
              exc = "LOAIKENH_ID"
              this.thongso_tc.loaikenh_id = ds[0]["loaikenh_id"]
              exc = "THIETBIDC_ID"
              this.thongso_tc.thietbidc_id = ds[0]["thietbidc_id"]
              exc = "THIETBIDC_ID"
              this.thongso_tc.thietbidc_id_cuoi = ds[1]["thietbidc_id"]
              if (this.dsDBTBBC && this.dsDBTBBC.length > 0) {
                exc = "DOITUONG_ID"
                this.thongso_tc.doituong_id = this.dsDBTBBC[0]["doituong_id"]
                exc = "MUCUOCTB_ID"
                this.thongso_tc.muccuoctb_id = this.dsDBTBBC[0]["mucuoctb_id"]
              }
            } catch (e) {
              this.$toast.error("Có lỗi trong việc lấy dữ liệu tại trường: " + exc.toUpperCase())
            }
          }
        }
      }
    },
    async lay_ds_kenhtoantrinh_theoTB(thuebaoid){
      try{
        let input = {
          p_thuebao_id: thuebaoid
        }
        const response = await api.sp_lay_ds_kenhtoantrinh_tb(this.axios,input)
        
        if (response.data.error_code === 'BSS-00000000') {
          if(response.data.data[0].kenh_toantrinh != null){
            return response.data.data[0].kenh_toantrinh
          }
          else{
            return 0
          }
        } return 0
      }
      catch(e){
        console.log("e",e)
      }
    },
    onDialogLyDoHuyAccepted (data, kieuld) {
      // let lsTmp = []
      // for (const item of data) {
      //   if (!this.dsDKHUY_TEMP.find(i => i.lydohuy_id === item.lydohuy_id)) {
      //     lsTmp.push(item)
      //   }
      // }
      // this.dsDKHUY_TEMP = [...this.dsDKHUY_TEMP, ...lsTmp]
      if(this.dichvuvt_id == DichVuVienThong.TSL){
        if(!this.kenh_trang){ //không là kênh trắng
            this.kieuld_dau = kieuld.kieuld_tc_id_dau
            this.kieuld_cuoi = kieuld.kieuld_tc_id_cuoi
        }
        
        if (data && data.length > 0) {
          this.dsDKHUY_TEMP = [data[0]]
        }
        this.HienThiDS_LyDoHuy(this.dsDKHUY_TEMP)
      }
      else{
        this.Kieuld_tc_id = kieuld.kieuld_tc_id
        if (data && data.length > 0) {
          this.dsDKHUY_TEMP = [data[0]]
        }
        this.HienThiDS_LyDoHuy(this.dsDKHUY_TEMP)
      }
    },
    onSelectCustomer (e) {
      if (!e) return
      this.txtMaTB.Text = e.ma_tb
      this.$refs.txtMaTB.focus()
      this.txtMaTB_KeyPress({charCode: 13})
    },
    onTraCuuAccept (data) {
      if (data) {
        this.txtMaTB.Text = data.ma_tb
        this.$refs.txtMaTB.focus()
        this.txtMaTB_KeyPress({charCode: 13})
      }
    },
    showErrorDSTBC () {
      this.$toast.error("Chưa có thông tin về khách hàng thanh lý có thuê bao: " + this.txtMaTB.Text + ". Kiểm tra lại !")
    }
  }
}
</script>
<style>
#u212012 .e-grid td.e-active{
  background-color: #FFF9EB !important;
  }
  .list-lydohuy{
    height: 140px;
  }
  .list-lydohuy .e-list-parent .e-list-item.e-selected{
    background-color: #bae7ff;
  }
  .row-container {
    display: flex;
  }
  .row-text {
    flex-grow: 1;
    display: flex;
    align-items: center;
  }
  .row-remove-icon {
    width: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: large;
    font-weight: bold;
    color: #898989 !important;
  }
</style>
<style scoped>
  .bold{
    font-weight: bold;
  }
  input[type='text']{
    font-weight: bold;
  }
</style>