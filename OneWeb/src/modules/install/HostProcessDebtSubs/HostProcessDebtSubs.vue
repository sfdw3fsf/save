<template src="./HostProcessDebtSubs.html"> </template>
<script>
import { TrangThaiHD, LoaiHopDong, TRANGTHAI_DONGBO, DONGBO_TEST, LOAI_HOPDONG, SUCCESS_CODE } from "@/constants.js"
import API from "./HostProcessDebtSubsAPI"
import moment from "moment"
import DatePicker from "vue2-datepicker"
import { DichVuVienThong, CACHMO } from "@/const/enums/index.js"
import GiaoPhieuNhanVien from "./popup/GiaoPhieuNhanVien.vue"
import {
  VirtualScroll,
  Sort,
  Filter,
  Page,
  Selection,
  Resize
} from "@syncfusion/ej2-vue-grids"
import { DATE_FORMAT, KHOA_MO_MAY } from "@/constants"
import LoaiHinhTB from "@/const/enums/LoaiHinhTB.js"
import DONGBO_GPHONE from "@/const/enums/DONGBO_NO_DD.js"
import { parseDate, parseServerDateTime, toServerDateTime } from '@/utils/format.js'
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import LyDoHuy from '@/modules/contract/setup/Liquidate/components/DialogLyDoHuy.vue'
import ExportData from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import { CLIENT_DATE_TIME_FORMAT } from '../../../constants'
import { Query } from '@syncfusion/ej2-data'

export default {
  props: {
    tag: {
      type: String,
      default: '9+7+1'
    },
    title: {
      type: String,
      default: 'Host - Khoá mở thuê bao nợ cước'
    },
    rootRouteTitle: {
      type: String,
      default: 'Thi công'
    }
  },
  mixins: [pagingAndFilter],
  components: {
    DatePicker,
    VirtualScroll,
    Sort,
    Selection,
    GiaoPhieuNhanVien,
    breadcrumb,
    LyDoHuy,
    ExportData
  },
  provide: {
    grid: [Filter, Page, Resize]
  },
  data () {
    return {
      headerBreadcrumb: {
        title: this.title,
        list: [
          { name: this.rootRouteTitle, active: true },
          { name: "Thay đổi, khóa mở, thanh lý", active: true },
          { name: this.title, active: true }
        ]
      },
      tlstpTop: [],
      btnLayTTMoi: { Visible: true, Enabled: true },
      tsbtnHoanCong: { Visible: false, Enabled: true },
      tsbtnKichHoat: { Visible: false, Enabled: true, ToolTipText: "" },
      tsbtnXuatFile: { Visible: false, Enabled: true },
      tsbtnHoanThienHS: { Visible: false, Enabled: true },
      tsbtnThoat: { Visible: false, Enabled: true },
      tsbtnGiaoPhieu: { Visible: false, Enabled: true },
      tsbtnGiaoViec: { Visible: false, Enabled: true },
      tsbtnChapNhan: { Visible: true, Enabled: true },
      tsbtnHoanThanh: { Visible: false, Enabled: true },
      btnTraPhieu: { Visible: false, Enabled: true },
      // tsbtnXuatFileTD: { Visible: false, Enabled: false },
      tsbnXoaHD: { Visible: false, Enabled: true },
      tsbtnXuatExcel: { Enabled: true, Visible: false },
      btnXuatExcel: { Enabled: true },
      btnXuatText: { Text: "" },
      checkAllDanhSach: { Checked: false },
      chkHCTheoFile: { Checked: false },
      rdoPhieuTra: { Checked: false },
      rdoPhieuMoi: { Checked: true },
      chkLoaihinhTB: { Checked: false },
      chkNgayGV: { Checked: false },
      chkNgayBG: { Checked: true },
      rdoGiao_TheoQD: { Text: "Giao theo QĐ", Enabled: true, Checked: true },
      rdoGiaoNgay: { Text: "Giao ngay", Enabled: true, Checked: false },
      chkLoiKH: { Checked: true },
      chkLoiHoanCong: { Checked: false },
      cboDichVuVT: { DataSource: [], EditValue: null },
      cboQuyTrinh: { DataSource: [], EditValue: null },
      cboLoaiHinhTB: { DataSource: [], EditValue: null, Enabled: false },
      cboKieuYC: { DataSource: [], EditValue: null },
      cboNguoiGV: { DataSource: [], EditValue: null },
      txtNoiDungTH: { Text: "" },
      txtMaTB: { Text: "" },
      dtpNgayBG: { Enabled: true, EditValue: "" },
      dtpNgayGV: { Enabled: false, EditValue: "" },
      // Grid using
      gridNhanVien: { DataSource: [], EditValue: null },
      gridDanhSach: { DataSource: [], EditValue: null, FocusedRowHandle: null },
      grdLyDoHuyHDTB: { DataSource: [], EditValue: null },
      lblThongBao: { Text: "" },
      btnLyDoHuy: { Visible: false },

      // ---------------------------------------------------------------
      tb_mytv: "",
      tb_mega: "",
      tb_cd: "",
      madoicap: "",
      dtList: [],
      hdtb_id: null,
      phieu_id: null,
      hdkh_id: null,
      tthd_id: 0,
      dichvuvt_id: null,
      huonggiao_id: 0,
      loaitb_id: 0,
      kieu_yc: null,
      dvnhan_id: 0,
      kt_tram: null,
      inDex_load: 0,
      vkieu_yc: -1,
      vdichvuvt_id: -1,
      quytrinh_id: 0,
      luong_id: 0,
      _vquytrinh_id: 0,
      nhantin_nhanvien_diaban: 0,
      _XacMinh_TheoYeuCau: 0, // 10-03-2020 AnhDt thêm
      _kieu_QuyTrinh: 0, // 0: Theo Yêu cầu / 1: Nợ cước
      flag: false,
      status: false,
      load: false,
      dsDanhSach: [],
      dstb: "",
      dtThaoTac: [],
      dtControl: [],
      dtDSHaTocDo: [],

      dsloaihd_id: "",
      dsdichvuvt_id: "",
      ds: [],
      kt_thamso_thuhoi: false,
      kt_tthd_kichhoat: false,
      // ------------------------------------------------
      momentFormat: {
        stringify: date => {
          return date ? moment(date).format("DD/MM/YYYY hh:mm:ss a") : ""
        },
        parse: value => {
          return value ? moment(value, "DD/MM/YYYY hh:mm:ss a").toDate() : null
        }
      },
      selectionOptions: { type: "Multiple", checkboxOnly: true },
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
      dsKMMAY: [],
      dsKM_temp: [],
      dsKM: [],
      dsMM: [],
      dsLSKM: [],
      dsHDKH: [],
      hdkh_id_huygoi_mytv: 0,
      hdtb_id_huygoi_mytv: 0,
      huonggiaotn_id: 0,
      quytrinh_id_2: 0,
      isDisabledActiveBtn: true,
      isDisabledRecoverBtn: true,
      gridDanhSachSelected: [],
      prevSelectedList: []
    }
  },
  computed: {
    tt_nd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$auth.getNguoiDungID(),
        USER_NEO: "",
        ma_nd: this.$auth.getUserName(),
        ip: this.$auth.getIP(),
        tentat_ccbs: this.$auth.getMaCCBS(),
        ma_tinh: this.$auth.getMaTinh(),
        may_cn: ""
      }
    },
    matinh () {
      return this.$root.token.getMaTinh()
    },
    gridNhanVienDataSource () {
      return this.gridNhanVien.DataSource
    },
    grdLyDoHuyHDTBDataSource () {
      return this.grdLyDoHuyHDTB.DataSource
    }
  },
  watch: {
    'gridDanhSach.DataSource': {
      handler: function handler (newValue, oldValue) {
        this.tsbtnXuatExcel.Enabled = newValue && newValue.length > 0
      },
      deep: true
    },
    'cboDichVuVT.EditValue': {
      handler: function () {
        this.cboDichVuVT_EditValueChanged()
      }
    },
    'cboQuyTrinh.EditValue': {
      handler: function () {
        this.cboQuyTrinh_EditValueChanged()
      }
    },
    'chkNgayGV.Checked': {
      handler: function () {
        this.chkNgayGV_CheckedChanged()
      }
    }
  },
  mounted () {
    window['app'] = this

    this.tlstpTop = [
      { btnLayTTMoi: this.btnLayTTMoi },
      // { tsbtnChapNhan: this.tsbtnChapNhan },
      { tsbtnGiaoViec: this.tsbtnGiaoViec },
      { tsbtnKichHoat: this.tsbtnKichHoat },
      { tsbtnGiaoPhieu: this.tsbtnGiaoPhieu },
      { tsbnXoaHD: this.tsbnXoaHD },
      { tsbtnHoanCong: this.tsbtnHoanCong },
      { tsbtnHoanThanh: this.tsbtnHoanThanh },
      { tsbtnHoanThienHS: this.tsbtnHoanThienHS },
      { btnTraPhieu: this.btnTraPhieu },
      // { tsbtnXuatFileTD: this.tsbtnXuatFileTD },
      { btnXuatExcel: this.btnXuatExcel },
      { btnXuatText: this.btnXuatText }
    ]
    this.frmHTHSKhoaMoMay_Load()
    window.APP = this
  },
  methods: {
    initSpliter () {
      window["Split"](["#boxLeft", "#boxRight"], {
        sizes: [33.3333, 66.666667],
        gutterSize: 16,
        onDragEnd: function (sizes) {}
      })
      window["Split"](["#boxLeft1", "#boxRight1"], {
        sizes: [60, 40],
        gutterSize: 16,
        onDragEnd: function (sizes) {}
      })
    },
    onFilteringDropDownList (e, dataSource, name) {
      var query = new Query()
      query =
        e.text !== "" ? query.where(name, "contains", e.text, true) : query
      e.updateData(dataSource, query)
    },
    onChangeRdoQD () {
      this.rdoGiao_TheoQD.Enabled = true
      this.rdoGiaoNgay.Enabled = false
    },
    onChangeRdo () {
      this.rdoGiao_TheoQD.Enabled = false
      this.rdoGiaoNgay.Enabled = true
    },
    async frmHTHSKhoaMoMay_Load () {
      try {
        let tag = this.tag
        if (this.$route.query.tag && this.$route.query.tag.length && this.$route.query.tag.length > 0) {
          tag = this.$route.query.tag.replaceAll(' ', '+')
        }

        if (tag) {
          let words = tag.split('+')
          if (words.length >= 1) { this.tthd_id = parseInt(words[0]) }
          if (words.length >= 2) {
            this.dsloaihd_id = words[1]
            this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',')
          }
          if (words.length >= 3) {
            this.dsdichvuvt_id = words[2]
            this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',')
          }
        } else {
          this.tthd_id = TrangThaiHD.DANG_THI_CONG
          this.dsloaihd_id = "1"
          this.dsdichvuvt_id = "1"
        }
      } catch (e) {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
        this.dsloaihd_id = "1"
        this.dsdichvuvt_id = "1"
      }

      this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
      this.dtpNgayGV.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)

      if (this.tthd_id == TrangThaiHD.DANG_THI_CONG) this.btnLyDoHuy.Visible = true
      this.HT_COMBO_KIEUYC()
      this.HT_COMBO_NGUOI_GV()

      if (this.vkieu_yc != -1) {
        this.cboKieuYC.EditValue = this.cboKieuYC.DataSource.find(
          item => item.KIEU_ID == this.vkieu_yc
        ).KIEU_ID
        this.kieu_yc = this.cboKieuYC.EditValue
      }
      this.HT_COMBO_DICHVUVT(this.dsdichvuvt_id)

      API.getDataFromSQL().then(ds_ts => {
        if (ds_ts.length > 0) {
          if (ds_ts[0].ten_ts) {
            this.rdoGiao_TheoQD.Text =
            "Giao theo QĐ (" + ds_ts[0].ten_ts + " ngày)"
            this.kt_thamso_thuhoi = true
          } else {
            this.rdoGiaoNgay.Checked = true
            this.rdoGiaoNgay.Enabled = false
            this.rdoGiao_TheoQD.Enabled = false
          }
        } else {
          this.rdoGiaoNgay.Checked = true
          this.rdoGiaoNgay.Enabled = false
          this.rdoGiao_TheoQD.Enabled = false
        }
      })

      this.load = true
      API.LAY_DS_THUEBAO_HATOCDO().then(rs => {
        this.dtDSHaTocDo = rs
      })

      if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG) {
        this.tsbtnHoanThienHS.Visible = true
        this.tsbtnHoanThienHS.Enabled = true
      }
      API.LAY_DS_THAMSO_MD().then(ds_kt1 => {
        if (ds_kt1.length > 0) {
          let str_vtemp = "LHD_NHANTIN_DIABAN"
          let det = ds_kt1.filter(x => x["ma_ts"] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.nhantin_nhanvien_diaban = 1 }
          }
          str_vtemp = "TS_KIEMTRA_TTHD_KICHHOAT"
          det = ds_kt1.filter(x => x["ma_ts"] == str_vtemp)
          if (det.length > 0) {
            let row = det[0]
            if (row["ten_ts"].trim() == "1") { this.kt_tthd_kichhoat = true }
          }
        }
      })

      API.LAY_DS_THAMSO_MD_MATS("LAY_QUYTRINH_PROC").then(dsTS => {
        if (dsTS && dsTS.length > 0) {
          this._XacMinh_TheoYeuCau = parseInt(dsTS[0].macdinh)
        }
      })

      // if (this._XacMinh_TheoYeuCau == 1) {
      //   ds = await API.Lay_DS_QuyTrinh(this.dsloaihd_id, dichvuvt_id, this.tthd_id, 1)
      // } else {
      //   ds = await API.Lay_DS_QuyTrinh(this.dsloaihd_id, dichvuvt_id, this.tthd_id, 1)
      // }
    },
    HT_COMBO_KIEUYC () {
      this.cboKieuYC.DataSource = [
        {
          KIEU_ID: 1,
          TENKIEU: "Khóa máy do nợ cước"
        },
        {
          KIEU_ID: 0,
          TENKIEU: "Mở máy nợ cước"
        }
      ]
      this.cboKieuYC.EditValue = this.cboKieuYC.DataSource[0].KIEU_ID
      this.kieu_yc = this.cboKieuYC.EditValue
    },
    async HT_COMBO_NGUOI_GV () {
      API.Lay_ds_nhanvien_thuchien_theo_dv(this.tt_nd.donvi_id).then(rs => {
        this.cboNguoiGV.DataSource = rs
        this.cboNguoiGV.EditValue = rs.find(item => item.nhanvien_id == this.tt_nd.nhanvien_id) ? this.tt_nd.nhanvien_id : null
      })
    },
    async HT_COMBO_DICHVUVT (str_dk) {
      str_dk = str_dk.split(',')
      const dt = (await API.dichvu_vienthong()).filter(item => {
        if (!str_dk) return false
        return str_dk.includes(item.ID)
      }
      )
      this.cboDichVuVT.DataSource = dt
      this.cboDichVuVT.EditValue = this.cboDichVuVT.DataSource[0].ID

      if (this.vdichvuvt_id != 0 && this.vdichvuvt_id != -1) {
        this.cboDichVuVT.EditValue = this.cboDichVuVT.DataSource.find(item => item.dichvuvt_id == this.vdichvuvt_id) ? this.vdichvuvt_id : null
        // this.cboDichVuVT_EditValueChanged()
      }

      this.dichvuvt_id = this.cboDichVuVT.EditValue

      API.Lay_DS_QuyTrinh(this.dsloaihd_id, this.dichvuvt_id, this.tthd_id, 1).then(ds => {
        if (ds.length > 0) {
          this.cboQuyTrinh.DataSource = ds
          this.cboQuyTrinh.EditValue = this.cboQuyTrinh.DataSource[0].quytrinh_id

          if (this._vquytrinh_id && this._vquytrinh_id != 0) {
            this.cboQuyTrinh.EditValue = this.cboQuyTrinh.DataSource.find(
              item => item.quytrinh_id == this._vquytrinh_id
            )
              ? this._vquytrinh_id
              : null
          }
        } else {
          this.tlstpTop.forEach(item => {
            item.Visible = false
          })
          this.tlstpTop[0].Visible = true
        }
      })

      // this.cboDichVuVT_EditValueChanged()
    },
    async cboDichVuVT_EditValueChanged () {
      this.btnXuatText.Text = "In phiếu"

      if (!this.cboDichVuVT.EditValue) return
      this.$refs.cbboxQuyTrinhRef.clear()
      this.dichvuvt_id = parseInt(this.cboDichVuVT.EditValue)
      if (this._XacMinh_TheoYeuCau == 1) {
        this.ds = await API.Lay_DS_QuyTrinh(this.dsloaihd_id, this.dichvuvt_id, this.tthd_id, 1)
      } else {
        this.ds = await API.Lay_DS_QuyTrinh(this.dsloaihd_id, this.dichvuvt_id, this.tthd_id, 1)
      }
      if (this.ds.length > 0) {
        this.cboQuyTrinh.DataSource = this.ds
        this.cboQuyTrinh.EditValue = this.cboQuyTrinh.DataSource[0].quytrinh_id
        if (this._vquytrinh_id && this._vquytrinh_id != 0) {
          this.cboQuyTrinh.EditValue = this.cboQuyTrinh.DataSource.find(
            item => item.quytrinh_id == this._vquytrinh_id
          )
            ? this._vquytrinh_id
            : null
        }
      } else {
        this.cboQuyTrinh.DataSource = []
        this.Clear()
      }

      switch (this.dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          this.tsbtnKichHoat.ToolTipText = "Kích hoạt tổng đài"
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Caption: "Kích hoạt tổng đài",
                Visible: true
              }
            }
          })
          break
        case DichVuVienThong.IMS:
          this.tsbtnKichHoat.ToolTipText = "Kích hoạt VTN"
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Caption: "Kích hoạt VTN",
                Visible: true
              }
            }
          })
          break
        case DichVuVienThong.MEGA_EYES:
          this.tsbtnKichHoat.ToolTipText = "Kích hoạt MEGAEYES"
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Caption: "Kích hoạt MEGAEYES",
                Visible: true
              }
            }
          })
          break
        case DichVuVienThong.GPHONE:
          this.tsbtnKichHoat.ToolTipText = "Kích hoạt Ccbs"
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Caption: "Kích hoạt Ccbs",
                Visible: true
              }
            }
          })
          break
        case DichVuVienThong.ADSL:
          this.tsbtnKichHoat.ToolTipText = "Kích hoạt Visa/Vasc"
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Caption: "Kích hoạt Visa/Vasc",
                Visible: true
              }
            }
          })
          break
        case DichVuVienThong.TSL:
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Visible: false
              }
            }
          })
          break
        case DichVuVienThong.MEGAWAN:
          this.gridDanhSach.DataSource = this.gridDanhSach.DataSource.map(item => {
            return {
              ...item,
              TEN_STATUS: {
                Visible: false
              }
            }
          })
          break
        case DichVuVienThong.DI_DONG:
          this.btnXuatText.Text = "Xuất file"
          break
      }
    },
    async cboQuyTrinh_EditValueChanged () {
      if (!this.cboQuyTrinh.EditValue) {
        this.Clear()
        return
      }

      this.loading(true)
      this.quytrinh_id = parseInt(this.cboQuyTrinh.EditValue)
      var dt = await API.HT_LoaiHinh_TB_QT_Combobox(this.quytrinh_id) || []
      this.cboLoaiHinhTB.DataSource = dt
      if (dt.length > 0) { this.cboLoaiHinhTB.EditValue = this.cboLoaiHinhTB.DataSource[0].loaitb_id }
      this.ds = await API.Laythongtin_table(this.quytrinh_id, this.tthd_id)
      if (this.ds && this.ds.length > 0) {
        this.luong_id = parseInt(this.ds[0]["luong_id"])
        this.huonggiao_id = parseInt(this.ds[0]["huonggiao_id"])
        let tenhg = this.ds[0]["huonggiao"]
        this.$set(this.headerBreadcrumb, 'title', tenhg.toUpperCase())
      } else {
        this.$toast.error(
          "Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại"
        )
        this.huonggiao_id = -1
        this.HienThiDanhSach2()
        this.loading(false)
        return
      }
      await this.HienThiGiaoDien(this.luong_id)
      if (this.load) {
        // Nếu check vào checkbox loại hình thì đã gọi hàm hiển thị rồi
        if (!this.chkLoaihinhTB.Checked) await this.HienThiDanhSach2()
      }
      this.loading(false)
    },
    Clear () {
      this.txtMaTB.Text = ""
      this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
      this.dtpNgayBG.Enabled = false
      this.chkNgayBG.Checked = false

      this.dtpNgayGV.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
      this.chkNgayGV.Checked = false
      this.dtpNgayGV.Enabled = false

      this.gridNhanVien.DataSource = []
      this.gridDanhSach.DataSource = []
    },
    async HienThiDanhSach2 () {
      if (!this.cboDichVuVT.EditValue) {
        return
      }

      let vloaitb_id = 0
      if (this.chkLoaihinhTB.Checked) { this.vloaitb_id = parseInt(this.cboLoaiHinhTB.EditValue) }
      let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.rdoPhieuMoi.Checked) vloai_id = 0
      else vloai_id = 1

      this.dsDanhSach = await API.LAY_DS_HDTB_KHOAMOMAY_HC(
        "0",
        this.tt_nd ? this.tt_nd.nhanvien_id : 0,
        this.dichvuvt_id,
        vloaitb_id,
        this.tthd_id,
        this.huonggiao_id,
        this.kieu_yc,
        vloai_id
      )
      this.dtList = this.dsDanhSach
      if (this.dtList.length > 0) {
        this.tsbtnXuatExcel.Enabled = true
        this.gridDanhSach.DataSource = this.dtList
        this.tsbtnXuatFile.Enabled = this.chkHCTheoFile.Checked
        if (this.load) {
          setTimeout(() => {
            this.gridviewDanhSach_FocusedRowChanged({ rowData: this.dtList[0] }, 0)
          }, 300)
        }
      } else {
        this.Clear()

        this.tsbtnXuatExcel.Enabled = false
        this.tsbtnXuatFile.Enabled = false
        this.tsbtnChapNhan.Visible = true
        this.tsbnXoaHD.Visible = false
        this.btnTraPhieu.Visible = false
      }
    },
    async HienThiGiaoDien (luong_id) {
      this.tsbtnHoanCong.Visible = false
      this.tsbtnHoanCong.Enabled = false
      this.tsbtnGiaoPhieu.Visible = false
      this.tsbtnGiaoPhieu.Enabled = false
      this.tsbtnKichHoat.Visible = false
      this.tsbtnKichHoat.Enabled = false
      this.tsbtnGiaoViec.Visible = false
      this.tsbtnGiaoViec.Enabled = false
      this.tsbtnHoanThienHS.Visible = false
      this.tsbtnHoanThienHS.Enabled = false
      this.tsbtnHoanThanh.Visible = false
      this.tsbtnHoanThanh.Enabled = false

      this.dtThaoTac = await API.lay_luong_thaotac(luong_id)
      const controls = Object.entries(this.$data).map(([key, val]) => key)

      for (let i = 0; i < this.dtThaoTac.length; i++) {
        const thaoTac = this.dtThaoTac[i]
        const dsControl = await API.lay_ct_thaotac_control(thaoTac["thaotac_id"])
        if (dsControl.length > 0) {
          const controlName = dsControl[0]["control_name"]
          const existsControl = controls.find(key => key.toLowerCase().includes(controlName.toLowerCase()))
          if (existsControl) {
            this[existsControl].Visible = true
            if (thaoTac["enable"] == 1) {
              this[existsControl].Enabled = true
            } else {
              this[existsControl].Enabled = false
            }
          }
        }
      }
    },
    async chkLoaihinhTB_CheckedChanged () {
      this.$root.showLoading(true)
      this.cboLoaiHinhTB.Enabled = this.chkLoaihinhTB.Checked
      await this.HienThiDanhSach2()
      this.$root.showLoading(false)
    },
    async cboLoaiHinhTB_EditValueChanged () {
      if (!this.cboLoaiHinhTB.EditValue) return
      this.loaitb_id = parseInt(this.cboLoaiHinhTB.EditValue)
      if (this.chkLoaihinhTB.Checked) {
        this.$root.showLoading(true)
        await this.HienThiDanhSach2()
        this.$root.showLoading(false)
      }
    },
    async txtMaTB_KeyPress (e) {
      if (e.key == "Enter") {
        this.loading(true)
        let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (this.rdoPhieuMoi.Checked) vloai_id = 0
        else vloai_id = 1
        this.dtList = await API.LAY_DS_HDTB_KHOAMOMAY_HC(
          this.txtMaTB.Text.trim(),
          this.tt_nd.nhanvien_id,
          this.dichvuvt_id,
          this.loaitb_id,
          this.tthd_id,
          this.huonggiao_id,
          this.kieu_yc,
          vloai_id
        )
        if (this.dtList.length > 0) {
          this.gridDanhSach.DataSource = this.dtList
          this.tsbtnHoanThienHS.Enabled = true
        }
        this.loading(false)
      }
    },
    async cboKieuYC_EditValueChanged () {
      if (this.cboKieuYC.EditValue == null) return
      this.$root.showLoading(true)
      this.kieu_yc = parseInt(this.cboKieuYC.EditValue)
      if (
        this.tthd_id == TrangThaiHD.THANH_TOAN &&
        this.dichvuvt_id == DichVuVienThong.CO_DINH
      ) {
        if (this.kieu_yc == 1) {
          this.tsbtnGiaoPhieu.Visible = false
          this.tsbtnHoanCong.Visible = true
        } else {
          this.tsbtnGiaoPhieu.Visible = true
          this.tsbtnHoanCong.Visible = false
          if (this.dichvuvt_id == DichVuVienThong.CO_DINH) { this.tsbtnHoanCong.Visible = true }
        }
      }
      if (
        this.tthd_id == TrangThaiHD.THANH_TOAN &&
        this.dichvuvt_id == DichVuVienThong.IMS
      ) {
        if (this.kieu_yc == 1) {
          this.tsbtnGiaoPhieu.Visible = false
          this.tsbtnHoanCong.Visible = true
        } else {
          this.tsbtnGiaoPhieu.Visible = true
          this.tsbtnHoanCong.Visible = false
        }
      }
      if (this.load) {
        if (
          this.PHAILAM(this.luong_id, "TUDONG_THUHOI_KHI_HOANCONG") &&
          this.kieu_yc == 1 &&
          this.kt_thamso_thuhoi
        ) {
          this.rdoGiao_TheoQD.Checked = true
          this.rdoGiao_TheoQD.Enabled = true
          this.rdoGiaoNgay.Checked = false
          this.rdoGiaoNgay.Enabled = true
        } else {
          this.rdoGiao_TheoQD.Checked = false
          this.rdoGiao_TheoQD.Enabled = false
          this.rdoGiaoNgay.Checked = false
          this.rdoGiaoNgay.Enabled = false
        }
        await this.HienThiDanhSach2()
      }
      this.$root.showLoading(false)
    },
    PHAILAM (luong_id, CODE) {
      let dr = this.dtThaoTac.filter(
        item => item.enable == 1 && item.code == CODE
      )

      return dr.length > 0
    },
    chkNgayGV_CheckedChanged () {
      this.dtpNgayGV.Enabled = this.chkNgayGV.Checked
    },
    chkNgayBG_CheckedChanged () {
      this.dtpNgayBG.Enabled = this.chkNgayBG.Checked
    },
    async tsbtnChapNhan_Click () {
      await this.Cap_Nhat()
      this.btnLayTTMoi_Click()
    },
    async btnLayTTMoi_Click () {
      this.loading(true)
      await this.HienThiDanhSach()
      try {
        this.dtList = this.dsDanhSach ? this.dsDanhSach : []
        if (this.dtList.length > 0) {
          this.tsbtnXuatExcel.Enabled = true
          this.gridDanhSach.DataSource = this.dtList
          // gridviewDanhSach.BestFitColumns();
          this.tsbtnXuatFile.Enabled = this.chkHCTheoFile.Checked

          setTimeout(() => {
            let index = 0
            if (this.prevSelectedList && this.prevSelectedList.length > 0) {
              index = this.dtList.findIndex(el => el.thuebao_id === this.prevSelectedList[0].thuebao_id) || 0
            }
            this.gridviewDanhSach_FocusedRowChanged({ rowData: this.dtList[index] }, index)
          }, 300)
        } else {
          this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
          this.dtpNgayGV.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
          this.chkNgayBG.Checked = false
          this.chkNgayGV.Checked = false
          this.dtpNgayGV.Enabled = false
          this.dtpNgayBG.Enabled = false
          this.gridNhanVien.DataSource = []
          this.gridDanhSach.DataSource = []
          this.tsbtnXuatExcel.Enabled = false
          this.tsbtnXuatFile.Enabled = false
          this.tsbtnChapNhan.Visible = true
          this.tsbnXoaHD.Visible = false
          this.btnTraPhieu.Visible = false
        }
      } catch (err) {
        this.$toast.error(err)
      }
      this.loading(false)
    },
    async Cap_Nhat () {
      // Kiểm tra dữ liệu trước khi Cập nhật
      let dt = this.gridDanhSach.DataSource
      if (dt.length <= 0) {
        this.$toast.error("Không có thuê bao trên lưới để cập nhật!")
        return
      }
      
      const lstChon = this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
        ...item,
        ngay_ht: parseDate(this.dtpNgayBG.EditValue) ,    //parseDate(item.ngay_ht), //thoains update : fix khi cap nhat hông chuyển sang Kích hoạt > không chuyển sang Hoàn công.
        ngay_ht_gp: parseDate(item.ngay_ht_gp) 
      }))
      this.prevSelectedList = [...lstChon]
      if (lstChon.length == 0) {
        this.$toast.error("Bạn chưa chọn thuê bao trên lưới để cập nhật!")
        return
      }
      let vnhanvien_giao_id = parseInt(this.cboNguoiGV.EditValue)
      let vngaygiao = toServerDateTime(this.dtpNgayBG.EditValue)

      let PHAIGIAOVIEC = false
      PHAIGIAOVIEC = this.PHAILAM(this.luong_id, "GIAOVIEC") // Nếu PHAIGIAOVIEC = false (Tự động giao việc) thì tạo dữ liệu bảng giaophieu_nv

      if (!this.KT_DuLieuNhap(lstChon)) {
        return
      }

      this.dsnhanviengp = []
      lstChon.forEach(item => {
        if (!PHAIGIAOVIEC) { this.TaoDuLieu_GIAOPHIEU_NV(parseInt(item["phieu_id"])) }
      })

      this.$root.showLoading(true)

  
      // new GiaoPhieu_NVFacade().CAPNHAT_GIAOVIEC(lstChon.CopyToDataTable(), vngaygiao, dsnhanviengp, tt_nd.nhanvien_id, parseInt(cboNguoiGV.EditValue), !PHAIGIAOVIEC);
      const rs = await API.capnhat(lstChon, vngaygiao, this.dsnhanviengp, this.tt_nd.nhanvien_id, vnhanvien_giao_id, !PHAIGIAOVIEC)

      if (rs.error_code == SUCCESS_CODE) {
        this.$toast.success("Cập nhật dữ liệu thành công !")
        await this.HienThiDanhSach()
      } else {
        this.$toast.error("Cập nhật dữ liệu thất bại ! " + rs.message)
      }

      this.$root.showLoading(false)
    },
    KT_DuLieuNhap (lst) {
      if (this.gridDanhSach.DataSource.length <= 0) {
        this.$toast.error("Không có dữ liệu để cập nhật!")
        return false
      }

      const mBGDate = moment(this.dtpNgayBG.EditValue, CLIENT_DATE_TIME_FORMAT)
      const mGVDate = moment(this.dtpNgayGV.EditValue, CLIENT_DATE_TIME_FORMAT)
      if (!this.chkNgayBG.Checked || !mBGDate.isValid()) {
        this.$toast.error("Hãy chọn ngày hoàn thành !")
        return false
      }

      if (!this.chkNgayGV.Checked || !mGVDate.isValid()) {
        this.$toast.error("Hãy chọn ngày giao việc !")
        return false
      }

      if (mGVDate.isValid()) {
        const isBefore = moment(mBGDate).isBefore(mGVDate, 'day')
        if (isBefore) {
          this.$toast.error("Ngày giao việc không được lớn hơn ngày hoàn thành!")
          return
        }
      }
      if (!this.cboNguoiGV.EditValue) {
        this.$toast.error("Hãy chọn người giao việc !")
        return false
      }
      if (this.PHAILAM(this.luong_id, "GIAOVIEC")) {
        if (this.gridNhanVien.DataSource.length <= 0) {
          this.$toast.error("Hãy nhập nhân viên thực hiện  !")
          return false
        }
      }
      // Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
      let ngay_sys
      let ngay_ht
      ngay_sys = this.tt_nd.ngay_cn
      ngay_ht = toServerDateTime(this.dtpNgayBG.EditValue)
      if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys)) {
        this.$toast.error(
          "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
        )
        return false
      }

      if (lst.length == 0) {
        this.$toast.error("Bạn chưa chọn thuê bao.")
        return false
      }

      return true
    },
    KiemTraDK_HoanThanh (thangnam_tt, thangnam_sys) {
      const d1 = moment(thangnam_tt, DATE_FORMAT)
      const d2 = moment(thangnam_sys, DATE_FORMAT)
      return d1.isSameOrBefore(d2, 'day')
    },
    TaoDuLieu_GIAOPHIEU_NV (vphieu_id) {
      const row = {
        PHIEU_ID: vphieu_id,
        NHANVIEN_TH_ID: this.tt_nd.nhanvien_id,
        NHIEMVU: this.cboKieuYC.DataSource.find(
          item => item.KIEU_ID == this.cboKieuYC.EditValue
        ).TENKIEU,
        NHIEMVU_ID: 7,
        GHICHU: this.cboKieuYC.DataSource.find(
          item => item.KIEU_ID == this.cboKieuYC.EditValue
        ).TENKIEU,
        NHANVIEN_GIAO_ID: parseInt(this.cboNguoiGV.EditValue),
        NGAYGIAO: toServerDateTime(this.dtpNgayGV.EditValue)
      }
      this.dsnhanviengp.push(row)
    },
    async HienThiDanhSach () {
      if (!this.cboDichVuVT.EditValue) return
      let vloaitb_id = 0
      if (this.chkLoaihinhTB.Checked) {
        vloaitb_id = parseInt(this.cboLoaiHinhTB.EditValue)
      }
      let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.rdoPhieuMoi.Checked) vloai_id = 0
      else vloai_id = 1
      if (this._XacMinh_TheoYeuCau == 1) {
        this.dsDanhSach = await API.LAY_DS_HDTB_KHOAMOMAY_HC(
          "0",
          this.tt_nd.nhanvien_id,
          this.dichvuvt_id,
          vloaitb_id,
          this.tthd_id,
          this.huonggiao_id,
          this.kieu_yc,
          vloai_id
        )
      } else {
        this.dsDanhSach = await API.LAY_DS_HDTB_KHOAMOMAY_HC(
          "0",
          this.tt_nd.nhanvien_id,
          this.dichvuvt_id,
          vloaitb_id,
          this.tthd_id,
          this.huonggiao_id,
          this.kieu_yc,
          vloai_id
        )
      }
      this.dtDSHaTocDo = await API.LAY_DS_THUEBAO_HATOCDO()
    },
    tsbtnGiaoViec_Click () {
      this.GiaoViec()
    },
    async GiaoViec () {
      if (this.gridDanhSach.DataSource.length <= 0) {
        this.$toast.error("Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ")
        return
      }
      if (!this.cboNguoiGV.EditValue) {
        this.$toast.error("Hãy nhập nhân viên giao việc!")
        return
      }
      if (!this.chkNgayGV.Checked) {
        this.$toast.error("Hãy nhập ngày giao việc!")
        return
      }
      let vnhanvien_giao_id = 0
      let vngaygiao = parseDate(this.dtpNgayGV.EditValue)
      vnhanvien_giao_id = parseInt(this.cboNguoiGV.EditValue)

      const gp = this.$refs.giaoPhieuNhanVienDlg
      gp.show(true, {
        phieu_id: this.phieu_id,
        nguoigv_id: vnhanvien_giao_id,
        ngaygiao: vngaygiao,
        ma_tb: this.txtMaTB.Text,
        hdtb_id: this.hdtb_id,
        huonggiao_id: this.huonggiao_id
      })
    },
    async formGiaoViec_Close () {
      this.loading(true)
      // Hiển thị danh sách nhân viên
      let ds = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID(
        this.phieu_id,
        this.tt_nd.nhanvien_id,
        2
      )
      this.HienThiDSNV(ds)
      this.loading(false)
    },
    HienThiDSNV (ds) {
      let myDataTables = ds
      if (myDataTables.length > 0) {
        this.chkNgayGV.Checked = true
        this.dtpNgayGV.Enabled = true
        this.dtpNgayGV.EditValue = parseServerDateTime(ds[0]["ngaygiao"])
        this.cboNguoiGV.EditValue = this.cboNguoiGV.DataSource.find(
          item => item.nhanvien_id == ds[0]["nhanvien_giao_id"]
        )
          ? +ds[0]["nhanvien_giao_id"]
          : null
      } else {
        this.chkNgayGV.Checked = false
        this.dtpNgayGV.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
        this.cboNguoiGV.EditValue = this.cboNguoiGV.DataSource.find(
          item => item.nhanvien_id == this.tt_nd.nhanvien_id
        )
          ? this.tt_nd.nhanvien_id
          : null
        // this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
      }
      this.gridNhanVien.DataSource = myDataTables
    },
    async rdoPhieuMoi_CheckedChanged () {
      this.rdoPhieuMoi.Checked = true
      this.rdoPhieuTra.Checked = false
      if (this.rdoPhieuMoi.Checked) {
        this.$root.showLoading(true)
        await this.HienThiDanhSach2() // -- minhnt sửa: 14/01/2015
        this.$root.showLoading(false)
      }
    },
    async rdoPhieuTra_CheckedChanged () {
      this.rdoPhieuTra.Checked = true
      this.rdoPhieuMoi.Checked = false
      if (this.rdoPhieuTra.Checked) {
        this.$root.showLoading(true)
        await this.HienThiDanhSach2() // -- minhnt sửa: 14/01/2015
        this.$root.showLoading(false)
      }
    },
    async btnNDTH_Click () {
      if (!this.phieu_id || this.phieu_id == 0) return
      await API.Update_ND_THUCHIEN(
        this.phieu_id,
        this.txtNoiDungTH.Text ? this.txtNoiDungTH.Text.trim() : ""
      )
      let vma_tb = this.gridDanhSach.FocusedRowHandle.ma_tb
      this.$toast.success("Cập nhật thành công!")
      await this.HienThiDanhSach2()

      for (let i = 0; i < this.gridDanhSach.DataSource.length; i++) {
        let s = ""
        let s1 = ""
        s = this.gridDanhSach.DataSource[i].ma_tb
        s1 = vma_tb
        if (s == s1) {
          this.gridDanhSach.FocusedRowHandle = this.gridDanhSach.DataSource[i]
          await this.gridviewDanhSach_FocusedRowChanged({ rowData: this.gridDanhSach.FocusedRowHandle }, i)
          return
        }
      }
    },
    // checkBoxChangeGridDanhSach () {
    //   this.isDisabledActiveBtn = this.$refs.gridDanhSach.getSelectedRecords().length == 0 || this.$refs.gridDanhSach.getSelectedRecords().filter(item => item.status == 7).length > 0
    //   this.isDisabledRecoverBtn = this.$refs.gridDanhSach.getSelectedRecords().length == 0 || this.$refs.gridDanhSach.getSelectedRecords().filter(item => item.status != 7).length > 0
    // },
    async gridviewDanhSach_FocusedRowChanged (e, selectedRowIndex) {
      // if (e && e.row) {
      //   const selectedRows = document.getElementsByClassName('selected-row__custom-color')
      //   for (const el of selectedRows) {
      //     el.classList.remove('selected-row__custom-color')
      //   }
      //   e.row.classList.add('selected-row__custom-color')
      // }

      if (selectedRowIndex != undefined) {
        this.$refs.gridDanhSach.selectRow(selectedRowIndex, false)
      }

      if (!e || !e.rowData) return
      const FocusedRowHandle = e.rowData

      this.gridDanhSach.FocusedRowHandle = FocusedRowHandle
      this.hdkh_id = FocusedRowHandle.hdkh_id
      this.hdtb_id = parseInt(FocusedRowHandle.hdtb_id)
      this.phieu_id = parseInt(FocusedRowHandle.phieu_id)
      this.dvnhan_id = parseInt(FocusedRowHandle.donvi_id)
      this.loaitb_id = parseInt(FocusedRowHandle.loaitb_id)
      this.txtNoiDungTH.Text = FocusedRowHandle.nd_thuchien
      this.txtMaTB.Text = FocusedRowHandle.ma_tb

      this.cboLoaiHinhTB.EditValue = this.loaitb_id

      if (FocusedRowHandle.status && FocusedRowHandle.status != "") { this.status = parseInt(FocusedRowHandle.status) } else this.status = -1
      if (
        FocusedRowHandle.ngay_ht_gp &&
          FocusedRowHandle.ngay_ht_gp != ""
      ) {
        this.chkNgayBG.Checked = true
        this.dtpNgayBG.EditValue = parseServerDateTime(FocusedRowHandle.ngay_ht_gp)
        this.dtpNgayBG.Enabled = true
      } else {
        this.chkNgayBG.Checked = false
        this.dtpNgayBG.Enabled = false
        this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
      }

      const dsNv = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, this.tt_nd.nhanvien_id, 2)
      this.HienThiDSNV(dsNv)
      this.tsbnXoaHD.Visible = false

      if (
        this.tthd_id == TrangThaiHD.MOI &&
          this.gridDanhSach.DataSource.length > 0
      ) {
        this.tsbnXoaHD.Visible = true
      }
      this.tsbtnKichHoat.Enabled = true
      // Nếu phải kích hoạt tổng đài
      if (this.PHAILAM(this.luong_id, "KICHHOAT_TD")) {
        if (this.gridNhanVien.DataSource.length > 0) {
          if (FocusedRowHandle.ngay_ht_gp && FocusedRowHandle.ngay_ht_gp != "") {
            this.dtpNgayBG.Enabled = true
            this.dtpNgayBG.EditValue = parseServerDateTime(FocusedRowHandle.ngay_ht_gp)
            this.chkNgayBG.Checked = true
            let trangthai_db = ""
            if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
              trangthai_db = TRANGTHAI_DONGBO.DONGBO_SERVICE
            } else {
              trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM
            }
            if (this.status == trangthai_db) {
              if (this.PHAILAM(this.luong_id, "GIAOPHIEU")) {
                this.tsbtnGiaoPhieu.Enabled = true
                this.tsbtnKichHoat.Enabled = false
              } else {
                this.tsbtnHoanCong.Enabled = true
                this.tsbtnKichHoat.Enabled = false
              }
              if (this.PHAILAM(this.luong_id, "CHECK_OMC")) {
                // Nếu có thao tác kiểm tra OMC thì -> làm
                try {
                  var kq = FocusedRowHandle.giao_omc
                  if (!kq) {
                    this.$toast.error(
                      "Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo"
                    )
                    return
                  } else if (kq == "2") {
                    this.tsbtnHoanCong.Enabled = true
                    this.tsbtnKichHoat.Enabled = false
                    this.tsbtnGiaoPhieu.Visible = false
                    this.tsbtnGiaoPhieu.Enabled = false
                  } else if (kq == "1") {
                    // Giaophieu
                    this.tsbtnGiaoPhieu.Visible = true
                    this.tsbtnGiaoPhieu.Enabled = true
                    this.tsbtnKichHoat.Enabled = false
                    this.tsbtnHoanCong.Enabled = false
                  } else {
                    this.$toast.error(kq)
                    return
                  }
                } catch (ex) {
                  this.$toast.error(
                    "Có lỗi khi gọi hàm kiểm thông tin thuê bao " + ex
                  )
                  return
                }
              }
            } else {
              // if (this.PHAILAM(this.luong_id, "CHECK_OMC")) {
              //   // Nếu có thao tác kiểm tra OMC thì -> làm
              //   try {
              //     let kq = FocusedRowHandle.giao_omc
              //     if (kq == null) {
              //       this.$toast.error(
              //         "Không lấy được thông tin kiểm tra giao phiếu cho bộ phận tiếp theo"
              //       )
              //       return
              //     } else if (kq == "2") {
              //       this.tsbtnHoanCong.Enabled = false
              //       this.tsbtnKichHoat.Enabled = true
              //       this.tsbtnGiaoPhieu.Visible = false
              //       this.tsbtnGiaoPhieu.Enabled = false
              //     } else if (kq == "1") {
              //       // Giaophieu
              //       this.tsbtnGiaoPhieu.Visible = true
              //       this.tsbtnGiaoPhieu.Enabled = true
              //       this.tsbtnKichHoat.Enabled = false
              //       this.tsbtnHoanCong.Enabled = false
              //     } else {
              //       this.$toast.error(kq)
              //       return
              //     }
              //   } catch (ex) {
              //     this.$toast.error(
              //       "Có lỗi khi gọi hàm kiểm thông tin thuê bao " + ex
              //     )
              //     return
              //   }
              // } else {
              //   this.tsbtnGiaoPhieu.Enabled = false
              //   this.tsbtnKichHoat.Enabled = true
              //   this.tsbtnHoanCong.Enabled = false
              // }
            }
          } else {
            this.chkNgayBG.Checked = false
            this.dtpNgayBG.Enabled = false
            this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
            this.tsbtnGiaoPhieu.Enabled = false
            this.tsbtnHoanCong.Enabled = false
            this.tsbtnKichHoat.Enabled = false
          }
        } else {
          this.tsbtnGiaoPhieu.Enabled = false
          this.tsbtnHoanCong.Enabled = false
          this.tsbtnKichHoat.Enabled = false
        }
      } else {
        // Nếu không phải kích hoạt
        // Nếu phải giao phiếu
        if (this.PHAILAM(this.luong_id, "GIAOPHIEU")) {
          if (this.gridNhanVien.DataSource.length > 0) {
            if (
              FocusedRowHandle.ngay_ht_gp &&
                FocusedRowHandle.ngay_ht_gp != ""
            ) {
              this.dtpNgayBG.Enabled = true
              this.dtpNgayBG.EditValue = parseServerDateTime(FocusedRowHandle.ngay_ht_gp)
              this.chkNgayBG.Checked = true
              this.tsbtnGiaoPhieu.Enabled = true
              this.tsbtnHoanCong.Enabled = false
              this.tsbtnKichHoat.Enabled = false
            } else {
              this.chkNgayBG.Checked = false
              this.dtpNgayBG.Enabled = false
              this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
              this.tsbtnGiaoPhieu.Enabled = false
              this.tsbtnHoanCong.Enabled = false
              this.tsbtnKichHoat.Enabled = false
            }
          } else {
            this.tsbtnGiaoPhieu.Enabled = false
            this.tsbtnHoanCong.Enabled = false
            this.tsbtnKichHoat.Enabled = false
          }
        } else {
          // Nếu không phải giao phiếu
          // Nếu phải kích hoạt tổng đài
          if (this.PHAILAM(this.luong_id, "HOANCONG")) {
            if (this.gridNhanVien.DataSource.length > 0) {
              if (
                FocusedRowHandle.ngay_ht_gp &&
                  FocusedRowHandle.ngay_ht_gp != ""
              ) {
                this.dtpNgayBG.Enabled = true
                this.dtpNgayBG.EditValue = parseServerDateTime(FocusedRowHandle.ngay_ht_gp)
                this.chkNgayBG.Checked = true
                this.tsbtnHoanCong.Enabled = true
              } else {
                this.chkNgayBG.Checked = false
                this.dtpNgayBG.Enabled = false
                this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
                this.tsbtnHoanCong.Enabled = false
              }
            } else {
              this.tsbtnGiaoPhieu.Enabled = false
              this.tsbtnHoanCong.Enabled = false
              this.tsbtnKichHoat.Enabled = false
            }
          }
        }
      }
      if (this.PHAILAM(this.luong_id, "HOANTHIENHOSO")) {
        if (this.gridNhanVien.DataSource.length > 0) {
          if (
            FocusedRowHandle.ngay_ht_gp &&
              FocusedRowHandle.ngay_ht_gp != ""
          ) {
            this.dtpNgayBG.Enabled = true
            this.dtpNgayBG.EditValue = parseServerDateTime(FocusedRowHandle.ngay_ht_gp)
            this.chkNgayBG.Checked = true
            this.tsbtnHoanThienHS.Enabled = true
          } else {
            this.chkNgayBG.Checked = false
            this.dtpNgayBG.Enabled = false
            this.dtpNgayBG.EditValue = parseServerDateTime(this.tt_nd.ngay_cn)
            this.tsbtnHoanThienHS.Enabled = false
          }
        } else {
          this.tsbtnGiaoPhieu.Enabled = false
          this.tsbtnHoanCong.Enabled = false
          this.tsbtnKichHoat.Enabled = false
        }
      }
      this.HienThiDS_LyDoHuy(this.hdtb_id)
    },
    async HienThiDS_LyDoHuy (vhdtb_id) {
      const dsLyDoHuy = await API.Lay_DS_LyDoHuy_HDTB(vhdtb_id)
      if (dsLyDoHuy.length > 0) {
        this.grdLyDoHuyHDTB.DataSource = dsLyDoHuy
      } else {
        this.grdLyDoHuyHDTB.DataSource = []
      }
    },
    tsbnKichHoat_Click () {
      if (!this.KT_DieuKien_TB(2)) return
      this.KichHoat()
    },
    KT_DieuKien_TB (kieu) {
      // let kt = true
      try {
        /// kieu : 1: Kiểm tra Xóa HĐ
        /// kieu : 2: Kiểm tra Kích hoạt
        /// kieu : 4: Kiểm tra Hoàn công
        if (kieu == 1) {
          // Xóa HĐ
        } else if (kieu == 2) {
          // Kích hoạt
          let lst = this.$refs.gridDanhSach.getSelectedRecords()
          const tb = lst.find(r => !r.ngay_ht_gp)
          if (tb) {
            this.$toast.error(
              "Thuê bao: " +
                tb.ma_tb +
                " chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!"
            )
            return false
          }
        } else if (kieu == 4) {
          // Hoàn công
          let lst = this.$refs.gridDanhSach.getSelectedRecords()
          if (lst.length <= 0) {
            this.$toast.error("Bạn chưa chọn thuê bao trên lưới!")
            return false
          }

          const tb = lst.find(r => !r.ngay_ht_gp)
          if (tb) {
            this.$toast.error(
              "Thuê bao: " +
                tb.ma_tb +
                " chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!"
            )
            return false
          }
          if (
            lst.filter(
              r =>
                r.status && r.status.trim() !=
                  TRANGTHAI_DONGBO.DONGBO_CM &&
                (parseInt(r.dichvuvt_id) == DichVuVienThong.ADSL ||
                  parseInt(r.dichvuvt_id) == DichVuVienThong.IMS)
            ).length > 0
          ) {
            let _l = lst.find(r => !r.ngay_ht_gp)
            if (_l) {
              this.$toast.error(
                "Thuê bao: " +
                  _l.ma_tb +
                  " chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!"
              )
              return false
            }
          }
        } else if (kieu == 5) {
          // Hoàn thiện HS
          let lst = this.$refs.gridDanhSach.getSelectedRecords()
          if (lst.filter(r => r.ngay_ht_gp == "").length > 0) {
            this.$toast.error(
              "Thuê bao: " +
                lst.find(r => r.ngay_ht_gp == "").ma_tb +
                " chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!"
            )
            return false
          }
        }
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex)
        return false
      }
      return true
    },
    async KichHoat () {
      if (this.gridNhanVien.DataSource.length <= 0) {
        this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!")
        return
      }

      if (this.gridDanhSach.DataSource.length <= 0) {
        this.$toast.error("Không có dữ liệu để kích hoạt!")
        return
      }

      let lst = this.$refs.gridDanhSach.getSelectedRecords()

      if (lst.length <= 0) {
        this.$toast.error("Bạn chưa chọn thuê bao trên lưới để kích hoạt !")
        return
      }

      let _l = lst.filter(r => (r["status"] == "5" || r["status"] == "7"))

      if (_l.length > 0) {
        this.$toast.error("Thuê bao " + _l[0]["ma_tb"] + " đã được kích hoạt")
        return
      }

      let _l2 = lst.filter(r => (r["tthd_id"] == "6" || r["tthd_id"] == "7"))

      if (_l2.length > 0) {
        this.$toast.error("Hợp đồng đã hoàn thiện. Hãy lấy lại thông tin trước khi kích hoạt!")
        return
      }

      const cf = await this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn kích hoạt các thuê bao này ?")
      if (!cf) {
        return
      }

      const selectedRows = this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
        ...item,
        ngay_ht_gp: parseDate(item.ngay_ht_gp),
        ngaygiao: parseDate(item.ngaygiao),
        ngay_ht: parseDate(item.ngay_ht),
        thangnam_tt: parseDate(item.thangnam_tt)
      }))

      this.loading(true)

      const result = await API.kichhoat(
        this.dichvuvt_id,
        this.luong_id,
        this.kt_tthd_kichhoat,
        this.chkLoiKH.Checked,
        this.kieu_yc,
        this.nhantin_nhanvien_diaban,
        this.loaitb_id,
        selectedRows
      )

      await this.HienThiDanhSach2()

      this.loading(false)

      if (result.error_code == SUCCESS_CODE) {
        this.$toast.success('Kích hoạt thành công')
        // this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";"
      } else {
        this.$toast.error(result.message)
      }
    },
    async KichHoat2 () {
      if (this.gridNhanVien.DataSource.length <= 0) {
        this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!")
        return
      }

      if (this.gridDanhSach.DataSource.length <= 0) {
        this.$toast.error("Không có dữ liệu để kích hoạt!")
        return
      }

      let lst = this.$refs.gridDanhSach.getSelectedRecords()

      if (lst.length <= 0) {
        this.$toast.error("Bạn chưa chọn thuê bao trên lưới để kích hoạt !")
        return
      }

      let _l = lst.filter(r => (r["status"] == "5" || r["status"] == "7"))

      if (_l.length > 0) {
        this.$toast.error("Thuê bao " + _l[0]["ma_tb"] + " đã được kích hoạt")
        return
      }

      let _l2 = lst.filter(r => (r["tthd_id"] == "6" || r["tthd_id"] == "7"))

      if (_l2.length > 0) {
        this.$toast.error("Hợp đồng đã hoàn thiện. Hãy lấy lại thông tin trước khi kích hoạt!")
        return
      }

      this.lblThongBao.Text = ""
      if (DONGBO_TEST.TEST == false) {
        // #region "Kich hoat len VTN"
        // TODO: contract/OperateLockIMS chua co data
        if (this.dichvuvt_id == DichVuVienThong.IMS) {
          if (confirm("Bạn có chắc chắn muốn kích hoạt các thuê bao IMS này ?")) {
            for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
              this.hdtb_id = parseInt(item.hdtb_id)
              this.loaitb_id = parseInt(item.loaitb_id)
              // hoangpkn : 29/10/2020
              // kiểm tra chặt cho trường hợp chưa load lại ds -- tthd_id tự = 7 khi gạch nợ. dẫn tới lệch trạng thái giữa danh bạ và backend
              let kt_tthd = true
              if (this.kt_tthd_kichhoat) {
                let k_tthd_id = parseInt(await API.fn_tt_hd_thuebao(this.hdtb_id, 2))
                if (k_tthd_id == TrangThaiHD.HOAN_THANH || k_tthd_id == TrangThaiHD.THOAITRA) {
                  kt_tthd = false
                  if (!this.chkLoiKH.Checked) {
                    this.$toast.error(item.ma_tb + " đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!")
                    this.HienThiDanhSach2()
                    return
                  }
                }
              }
              // end hoàng

              if (kt_tthd) {
                const result = await API.kichhoat(
                  this.dichvuvt_id,
                  this.luong_id,
                  this.kt_tthd_kichhoat,
                  this.chkLoiKH.Checked,
                  this.kieu_yc,
                  this.nhantin_nhanvien_diaban,
                  this.loaitb_id,
                  (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                    ...item,
                    ngay_ht_gp: parseDate(item.ngay_ht_gp),
                    ngaygiao: parseDate(item.ngaygiao),
                    ngay_ht: parseDate(item.ngay_ht),
                    thangnam_tt: parseDate(item.thangnam_tt)
                  })) : [])
                )

                if (result.error_code == SUCCESS_CODE) {
                  this.$toast.success('Kích hoạt thành công')
                  this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";"
                } else {
                  if (!this.chkLoiKH.Checked) {
                    this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                    this.HienThiDanhSach2()
                    return
                  }
                }
              }
              // end hoàng
            }

            this.$toast.error("Kích hoạt dịch vụ thành công lên VTN !")
            this.HienThiDanhSach2() // -- minhnt sửa: 14/01/2015
          }
        }
        // #endregion

        // #region "Kich hoat len VDC, VASC"
        if (this.dichvuvt_id == DichVuVienThong.ADSL || this.dichvuvt_id == DichVuVienThong.EMAIL) {
          if (confirm("Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?")) {
            try {
              for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
                this.hdtb_id = parseInt(item.hdtb_id)
                this.txtMaTB.Text = item.ma_tb
                this.loaitb_id = parseInt(item.loaitb_id)

                // hoangpkn : 29/10/2020
                // kiểm tra chặt cho trường hợp chưa load lại ds -- tthd_id tự = 7 khi gạch nợ. dẫn tới lệch trạng thái giữa danh bạ và backend
                let kt_tthd = true
                if (this.kt_tthd_kichhoat) {
                  let k_tthd_id = parseInt(await API.fn_tt_hd_thuebao(this.hdtb_id, 2))
                  if (k_tthd_id == TrangThaiHD.HOAN_THANH || k_tthd_id == TrangThaiHD.THOAITRA) {
                    kt_tthd = false
                    if (this.chkLoiKH.Checked == false) {
                      this.$toast.error(item.ma_tb + " đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!")
                      this.HienThiDanhSach2()
                      return
                    } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                  }
                }
                // end hoàng
                if (kt_tthd) {
                  // #region Kích hoạt visa
                  if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL ||
                    this.loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                    this.loaitb_id == LoaiHinhTB.WIFI_FIBER ||
                    this.loaitb_id == LoaiHinhTB.INTERNET_1260 ||
                    this.loaitb_id == LoaiHinhTB.EMAIL) {
                    const result = await API.kichhoat(
                      this.dichvuvt_id,
                      this.luong_id,
                      this.kt_tthd_kichhoat,
                      this.chkLoiKH.Checked,
                      this.kieu_yc,
                      this.nhantin_nhanvien_diaban,
                      this.loaitb_id,
                      (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                        ...item,
                        ngay_ht_gp: parseDate(item.ngay_ht_gp),
                        ngaygiao: parseDate(item.ngaygiao),
                        ngay_ht: parseDate(item.ngay_ht),
                        thangnam_tt: parseDate(item.thangnam_tt)
                      })) : [])
                    )

                    if (result.error_code == SUCCESS_CODE) {
                      this.$toast.success('Kích hoạt thành công')
                      this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";"
                    } else {
                      if (!this.chkLoiKH.Checked) {
                        this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                        this.HienThiDanhSach2()
                        return
                      } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                    }
                  } else if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B) {
                    const result = await API.kichhoat(
                      this.dichvuvt_id,
                      this.luong_id,
                      this.kt_tthd_kichhoat,
                      this.chkLoiKH.Checked,
                      this.kieu_yc,
                      this.nhantin_nhanvien_diaban,
                      this.loaitb_id,
                      (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                        ...item,
                        ngay_ht_gp: parseDate(item.ngay_ht_gp),
                        ngaygiao: parseDate(item.ngaygiao),
                        ngay_ht: parseDate(item.ngay_ht),
                        thangnam_tt: parseDate(item.thangnam_tt)
                      })) : [])
                    )

                    if (result.error_code == SUCCESS_CODE) {
                      this.$toast.success('Kích hoạt thành công')
                      this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";"
                    } else {
                      if (!this.chkLoiKH.Checked) {
                        this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                        this.HienThiDanhSach2()
                        return
                      } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                    }
                  }
                  // #endregion
                }
              }
              this.HienThiDanhSach2()
              this.$toast.error("Kích hoạt dịch vụ thành công!")
            } catch (ex) {
              this.$toast.error("Có lỗi: \n" + ex)
              return
            }
          }
        }
        // #endregion

        // #region Kích hoạt tổng đài
        if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
          if (confirm("Bạn có chắc chắn kích hoạt lên tổng đài hay không?")) {
            try {
              for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
                this.hdtb_id = parseInt(item.hdtb_id)

                // hoangpkn : 29/10/2020
                // kiểm tra chặt cho trường hợp chưa load lại ds -- tthd_id tự = 7 khi gạch nợ. dẫn tới lệch trạng thái giữa danh bạ và backend
                let kt_tthd = true
                if (this.kt_tthd_kichhoat) {
                  let k_tthd_id = parseInt(await API.fn_tt_hd_thuebao(this.hdtb_id, 2))
                  if (k_tthd_id == TrangThaiHD.HOAN_THANH || k_tthd_id == TrangThaiHD.THOAITRA) {
                    kt_tthd = false
                    if (this.chkLoiKH.Checked == false) {
                      this.$toast.error(item.ma_tb + " đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!")
                      this.HienThiDanhSach2()
                      return
                    } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                  }
                }
                // end hoàng

                if (kt_tthd) {
                  const result = await API.kichhoat(
                    this.dichvuvt_id,
                    this.luong_id,
                    this.kt_tthd_kichhoat,
                    this.chkLoiKH.Checked,
                    this.kieu_yc,
                    this.nhantin_nhanvien_diaban,
                    this.loaitb_id,
                    (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                      ...item,
                      ngay_ht_gp: parseDate(item.ngay_ht_gp),
                      ngaygiao: parseDate(item.ngaygiao),
                      ngay_ht: parseDate(item.ngay_ht),
                      thangnam_tt: parseDate(item.thangnam_tt)
                    })) : [])
                  )

                  if (result.error_code == SUCCESS_CODE) {
                    this.$toast.success('Kích hoạt thành công')
                  } else {
                    this.$toast.error(result.message)
                  }
                }
              }
              this.HienThiDanhSach2() // -- minhnt sửa: 14/01/2015
            } catch (ex) {
              this.$toast.error("Có lỗi: \n" + ex)
              return
            }
          }
        }
        // #endregion

        // #region Kích hoạt khóa /mở máy CCBS
        if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
          if (confirm("Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?")) {
            // TODO: chua tim ra this.tt_nd.USER_NEO
            // TODO: kiem tra USER_NEO
            if (this.tt_nd.USER_NEO == "") {
              this.$toast.error("Người dùng chưa được gán user tác động hệ thống Ccbs !")
              return
            }
            for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
              this.hdtb_id = parseInt(item.hdtb_id)

              // hoangpkn : 29/10/2020
              // kiểm tra chặt cho trường hợp chưa load lại ds -- tthd_id tự = 7 khi gạch nợ. dẫn tới lệch trạng thái giữa danh bạ và backend
              let kt_tthd = true
              if (this.kt_tthd_kichhoat) {
                let k_tthd_id = parseInt(await API.fn_tt_hd_thuebao(this.hdtb_id, 2))
                if (k_tthd_id == TrangThaiHD.HOAN_THANH || k_tthd_id == TrangThaiHD.THOAITRA) {
                  kt_tthd = false
                  if (this.chkLoiKH.Checked == false) {
                    this.$toast.error(item.ma_tb + " đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!")
                    this.HienThiDanhSach2()
                    return
                  } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                }
              }

              if (kt_tthd) {
                try {
                  const result = await API.kichhoat(
                    this.dichvuvt_id,
                    this.luong_id,
                    this.kt_tthd_kichhoat,
                    this.chkLoiKH.Checked,
                    this.kieu_yc,
                    this.nhantin_nhanvien_diaban,
                    this.loaitb_id,
                    (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                      ...item,
                      ngay_ht_gp: parseDate(item.ngay_ht_gp),
                      ngaygiao: parseDate(item.ngaygiao),
                      ngay_ht: parseDate(item.ngay_ht),
                      thangnam_tt: parseDate(item.thangnam_tt)
                    })) : [])
                  )

                  if (result.error_code == SUCCESS_CODE) {
                    this.$toast.success('Kích hoạt thành công')
                  } else {
                    if (this.chkLoiKH.Checked == false) {
                      this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                      this.HienThiDanhSach2()
                      return
                    }
                  }
                } catch (ex) {
                  if (this.chkLoiKH.Checked == false) {
                    this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + ex)
                    this.HienThiDanhSach2()
                    return
                  }
                }
              }
            }

            this.$toast.error("Kích hoạt dịch vụ thành công lên he thong CCBS !")
            this.HienThiDanhSach2()
          }
        }

        if (this.dichvuvt_id == DichVuVienThong.GPHONE) {
          if (DONGBO_GPHONE.DONGBO) {
            if (confirm("Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?")) {
              let userid = await API.fn_tt_nguoidung(this.tt_nd.nguoidung_id, 1)
              if (userid == "-1") {
                this.$toast.error("Người dùng chưa được gán user tác động hệ thống gphone.")
                return
              }
              for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
                this.hdtb_id = parseInt(item.hdtb_id)
                let kt_tthd = true
                if (this.kt_tthd_kichhoat) {
                  let k_tthd_id = parseInt(await API.fn_tt_hd_thuebao(this.hdtb_id, 2))
                  if (k_tthd_id == TrangThaiHD.HOAN_THANH || k_tthd_id == TrangThaiHD.THOAITRA) {
                    kt_tthd = false
                    if (this.chkLoiKH.Checked == false) {
                      this.$toast.error(item.ma_tb + " đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!")
                      this.HienThiDanhSach2()
                      return
                    } else { this.lblThongBao.Text = this.lblThongBao.Text + this.txtMaTB.Text + ";" }
                  }
                }
                if (kt_tthd) {
                  try {
                    const result = await API.kichhoat(
                      this.dichvuvt_id,
                      this.luong_id,
                      this.kt_tthd_kichhoat,
                      this.chkLoiKH.Checked,
                      this.kieu_yc,
                      this.nhantin_nhanvien_diaban,
                      this.loaitb_id,
                      (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                        ...item,
                        ngay_ht_gp: parseDate(item.ngay_ht_gp),
                        ngaygiao: parseDate(item.ngaygiao),
                        ngay_ht: parseDate(item.ngay_ht),
                        thangnam_tt: parseDate(item.thangnam_tt)
                      })) : [])
                    )

                    if (result.error_code == SUCCESS_CODE) {
                      this.$toast.success('Kích hoạt thành công')
                    } else {
                      if (this.chkLoiKH.Checked == false) {
                        this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                        this.HienThiDanhSach2()
                        return
                      }
                    }
                  } catch (ex) {
                    if (this.chkLoiKH.Checked == false) {
                      this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + ex)
                      this.HienThiDanhSach2()
                      return
                    }
                  }
                }
              }

              this.$toast.success("Kích hoạt dịch vụ thành công lên hệ thống gphone !")
              this.HienThiDanhSach2()
            }
          } else {
            for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
              this.hdtb_id = parseInt(item.hdtb_id)
              if (this.PHAILAM(this.luong_id, "CAPNHAT_NGAYHT_KH")) {
                await API.capnhat_ngayht(this.hdtb_id, this.tt_nd.ngay_cn)
              }
              if (this.PHAILAM(this.luong_id, "CAPNHAT_NGAYKH_KH")) {
                await API.sp_capnhat_ngaykh(this.hdtb_id, this.tt_nd.ngay_cn)
              }

              // TODO: Kiem tra API
              const result = await API.kichhoat(
                this.dichvuvt_id,
                this.luong_id,
                this.kt_tthd_kichhoat,
                this.chkLoiKH.Checked,
                this.kieu_yc,
                this.nhantin_nhanvien_diaban,
                this.loaitb_id,
                (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
                  ...item,
                  ngay_ht_gp: parseDate(item.ngay_ht_gp),
                  ngaygiao: parseDate(item.ngaygiao),
                  ngay_ht: parseDate(item.ngay_ht),
                  thangnam_tt: parseDate(item.thangnam_tt)
                })) : []))

              if (result.error_code == SUCCESS_CODE) {
                this.$toast.success('Kích hoạt thành công')
              } else {
                if (this.chkLoiKH.Checked == false) {
                  this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
                  this.HienThiDanhSach2()
                  return
                }
              }
            }
            this.HienThiDanhSach2()
          }
        }
      } else {
        for (let item of this.$refs.gridDanhSach.getSelectedRecords()) {
          this.hdtb_id = parseInt(item.hdtb_id)
          this.txtMaTB.Text = item.ma_tb

          let vthuebao_id = parseInt(item.thuebao_id)

          await this.NangTocDo(this.hdtb_id, vthuebao_id)
          const result = await API.kichhoat(
            this.dichvuvt_id,
            this.luong_id,
            this.kt_tthd_kichhoat,
            this.chkLoiKH.Checked,
            this.kieu_yc,
            this.nhantin_nhanvien_diaban,
            this.loaitb_id,
            (this.$refs.gridDanhSach.getSelectedRecords() && this.$refs.gridDanhSach.getSelectedRecords().length ? this.$refs.gridDanhSach.getSelectedRecords().map(item => ({
              ...item,
              ngay_ht_gp: parseDate(item.ngay_ht_gp),
              ngaygiao: parseDate(item.ngaygiao),
              ngay_ht: parseDate(item.ngay_ht),
              thangnam_tt: parseDate(item.thangnam_tt)
            })) : [])
          )

          if (result.error_code == SUCCESS_CODE) {
            this.$toast.success('Kích hoạt thành công')
          } else {
            if (this.chkLoiKH.Checked == false) {
              this.$toast.error("Lỗi thuê bao '" + item.ma_tb + "' : " + result.message)
              this.HienThiDanhSach2()
              return
            }
          }
        }

        if (this.dichvuvt_id == DichVuVienThong.IMS) {
          this.$toast.success("Kích hoạt dịch vụ thành công!")
        }
        if (this.loaitb_id == LoaiHinhTB.INTERNET_ADSL || this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.WIFI_FIBER) {
          this.$toast.success("Kích hoạt dịch vụ thành công!")
        }
        if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B) {
          this.$toast.success("Kích hoạt dịch vụ thành công!")
        }
        this.HienThiDanhSach2()
      }
      if (this.inDex_load != 0) {
        this.$refs.gridDanhSach.clearCellSelection()
        this.$refs.gridDanhSach.selectRow(this.inDex_load)
      }
    },
    tsbnXoaHD_Click () {
      this.Xoa_HD()
    },
    async Xoa_HD () {
      if (this.gridDanhSach.DataSource.length > 0) {
        // txtMaTB.Focus();
        this.$root.showLoading(true)
        if (!this.KT_DieuKien_TB(1)) return
        let lst_chon = this.$refs.gridDanhSach.getSelectedRecords()
        if (lst_chon.length <= 0) {
          this.$root.showLoading(false)
          this.$toast.error("Bạn chưa chọn thuê bao.")
          return
        }
        let _l = lst_chon.filter(r => r["tthd_id"] != "1")
        if (_l.length > 0) {
          this.$root.showLoading(false)
          this.$toast.error(
            "Trạng thái hợp đồng thuê bao " +
              _l[0]["ma_tb"] +
              " không thỏa mãn điều kiện"
          )
          return
        }
        _l = lst_chon.filter(
          r => r["status"] != "" && r["status"] != "0"
        )
        if (_l.length > 0) {
          this.$root.showLoading(false)
          this.$toast.error(
            "Thuê bao " +
              _l[0]["ma_tb"] +
              " đã được kích hoạt. Bạn không được xóa"
          )
          return
        }
        _l = lst_chon.filter(r => r["nguoi_cn"] != this.tt_nd.ma_nd)
        if (_l.length > 0) {
          this.$root.showLoading(false)
          this.$toast.error(
            "Bạn không được phép xóa " +
              _l[0]["ma_tb"] +
              " do user " +
              _l[0]["nguoi_cn"] +
              " cập nhật"
          )
          return
        }
        let _ds = lst_chon.map(i => ({ hdkh_id: i["hdkh_id"], loaihd_id: LoaiHopDong.THAY_DOI_DV }))
        const rs = await API.XOA_HDKH_V2(_ds)

        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Xóa hợp đồng trên dữ liệu thành công!")
        } else {
          this.$toast.error(rs.message)
        }

        // for (let i = 0; i < _ds.Tables.length; i++) {
        //   API.XOA_HDKH_V2(
        //     parseInt(_ds.Tables[i]["hdkh_id"]),
        //     LoaiHopDong.THAY_DOI_DV
        //   )
        // }
        this.$root.showLoading(false)

        this.HienThiDanhSach2()
      } else {
        this.$toast.error(
          "Danh sách hợp đồng trống, bạn không thể Xóa hợp đồng\nHãy kiểm tra lại!"
        )
      }
    },
    btnXuatExcel_Click () {
      // let lst = [...this.gridDanhSach.DataSource]
      // if (lst.length > 0) {
      //   let data = XLSX.utils.json_to_sheet(lst)
      //   let wb = XLSX.utils.book_new()
      //   XLSX.utils.book_append_sheet(wb, data, "data")
      //   XLSX.writeFile(wb, "xuat_file.xlsx")
      // } else this.$toast.error("Không có dữ liệu!")
      this.gridDanhSachSelected = this.$refs.gridDanhSach ? this.$refs.gridDanhSach.getSelectedRecords() : []
      this.$refs.frmExportData.showModal()
    },
    async NangTocDo (_vhdtb_id, _vthuebao_id) {
      const count = this.dtDSHaTocDo.filter(item => item.thuebao_id == _vthuebao_id).length

      if (count > 0) {
        // TODO: Kiem tra 2 API o duoi
        // xulytb.HOANTHIEN_HS_TDTD_DUYTRI(_vhdtb_id);

        await API.Capnhat_nangtocdo(_vthuebao_id)
      }
    },
    TaoDuLieu_huygoi_mytv (goi_id, nhomtb_id, thuebao_id) {
      this.TaoDuLieu_HDKH_huygoi_mytv(thuebao_id)

      // TODO: mo ra sau
      // TaoDuLieu_HDTB_huygoi_mytv(thuebao_id);
      // TaoDuLieu_HDTB_GOI_DADV_huygoi_mytv(goi_id, nhomtb_id);
    },
    async TaoDuLieu_HDKH_huygoi_mytv (thuebao_id) {

      // TODO: lam sau
      // this.dsHDKH = []
      // this.hdkh_id_huygoi_mytv = await API.GetKey("HD_KHACHHANG");
      // let khachhang_id = await API.map_id("khachhang_id", ".db_thuebao", "where thuebao_id ='" + thuebao_id + "'")

      // let rowHDKH = {
      //   HDKH_ID = this.hdkh_id_huygoi_mytv;
      //   MA_GD = laphd.Lay_Ma_GD(LoaiHopDong.THAYDOI_GOI_DADV);
      //   CopyRow(tcdanhba.LAY_DS_DBKH_THEO_KHID(khachhang_id).Tables[0].Rows[0], rowHDKH);
      //   KHACHHANG_ID = khachhang_id;
      //   NGAYLAP_HD = tt_nd.ngay_cn;
      //   NGAY_YC = tt_nd.ngay_cn;
      //   GHICHU = "Tự động xóa thuê bao khỏi gói đa dịch vụ";
      //   DONVI_ID = tt_nd.donvi_id;
      //   NHANVIEN_ID = tt_nd.nhanvien_id;
      //   LOAIHD_ID = LoaiHopDong.THAYDOI_GOI_DADV;
      //   NGAY_CN = tt_nd.ngay_cn;
      //   NGUOI_CN = tt_nd.ma_nd;
      //   MAY_CN = tt_nd.may_cn;
      //   IP_CN = tt_nd.ip;
      // }

      // this.dsHDKH.push(rowHDKH)
    },
    btnHoanCong_Click () {
      this.$refs.txtMaTB.focus()
      if (!this.KT_DieuKien_TB(4)) return
      this.HoanCong()
    },
    async KiemTraTrangThai_Vina (ma_tb, kieukhoa) {
      let fl = false
      try {
        var dt = await API.ts_tracuu_laytt_tb(this.tt_nd.ma_tinh, "84" + ma_tb, this.tt_nd.nguoidung_id, await this.tt_nd.ip)

        if (dt != null) {
          // TODO: check API
          var goidi = dt[0]["goi_di"]
          var goiden = dt[0]["goi_den"]
          if (this.kieu_yc == KHOA_MO_MAY.KHOAMAY) {
            if (kieukhoa == "1") {
              if (goidi == "0") { fl = true }
            } else if (kieukhoa == "0") {
              if (goidi == "0" && goiden == "0") { fl = true }
            }
          } else {
            switch (kieukhoa) {
              case "2":
                if (goidi == "1" && goiden == "1") { fl = true }
                break
              case "1":
                if (goidi == "1") { fl = true }
                break
            }
          }
        }
      } catch (e) {
        fl = false
      }
      return fl
    },
    TaoDuLieu (i, _dt) {
      try {
        this.dsKMMAY = []

        this.dsKMMAY.push({
          MA_TB: _dt[i]["ma_tb"],
          MA_TT: this.dsKM_temp[0]["ma_tt"],
          NGAYKHOA: this.tt_nd.ngay_cn,
          KIEUKHOA: this.dsKM_temp[0]["kieukhoa"],
          DICHVUVT_ID: this.dichvuvt_id,
          LOAITB_ID: this.loaitb_id,
          TRANGTHAI: this.kieu_yc,
          CHIEUKHOA: _dt[i]["chieukhoa"],
          LYDOKHOA: this.dsKM_temp[0]["lydokhoa"],
          CACHKHOA: this.dsKM_temp[0]["cachkhoa"],
          THUEBAO_ID: this.dsKM_temp[0]["thuebao_id"],
          DONVI_ID: this.dsKM_temp[0]["donvi_id"],
          NGUOI_CN: this.tt_nd.ma_nd,
          MAY_CN: this.tt_nd.may_cn
        })
      } catch (e) {

      }
    },
    TaoDuLieu_LSKM (i, dt, _dt) {
      this.dsLSKM = []
      let row = {
        MA_TB: _dt[i]["ma_tb"],
        MA_TT: dt[0]["ma_tt"],
        KIEUKHOA: this.dsKM[0]["kieukhoa"],
        DICHVUVT_ID: this.cboDichVuVT.EditValue,
        LOAITB_ID: this.loaitb_id,
        TRANGTHAI: this.kieu_yc,
        CHIEUMO: _dt[i]["chieukhoa"],
        MAYMO: this.tt_nd.may_cn,
        NGAYMO: this.tt_nd.ngay_cn,
        CHIEUKHOA: _dt[i]["chieukhoa"],
        NGUOIKHOA: dt[0]["nguoi_cn"],
        MAYKHOA: dt[0]["may_cn"],
        NGAYKHOA: dt[0]["ngaykhoa"],
        CACHKHOA: this.dsKM[0]["cachkhoa"],
        THUEBAO_ID: this.dsKM[0]["thuebao_id"]
      }

      if (this.kieu_yc == 1) { row.LYDOKHOA = _dt[i]["noidung"] } else { row.LYDOMO = _dt[i]["noidung"] }

      if (this.dsMM.length > 0) {
        row.CACHMO = this.dsMM[0]["cachmo"]
        row.LANMO = this.dsMM[0]["lanmo"]
      } else { row.CACHMO = CACHMO.NHANCONG }

      this.dsLSKM.push(row)
    },
    async HoanCong () {
      try {
        if (!toServerDateTime(this.dtpNgayBG.EditValue)) {
          this.$toast.error('Chưa chọn ngày HT')
          return
        }

        if (this.gridNhanVien.DataSource.length <= 0) {
          this.$toast.error("Bạn chưa cập nhật thông tin giao việc cho nhân viên!")
          return
        }
        if (!this.gridDanhSach.FocusedRowHandle.ngay_ht_gp) {
          this.$toast.error("Bạn phải cập nhật dữ liệu trước khi hoàn thành !")
          return
        }

        const ngay_bg = toServerDateTime(this.dtpNgayBG.EditValue)
        const ds_hdtb = this.$refs.gridDanhSach.getSelectedRecords()

        this.loading(true)

        const rs = await API.HOANTHANH_HOANCONG(this.luong_id,
          this.dichvuvt_id,
          this.loaitb_id,
          this.kieu_yc,
          this.huonggiao_id, ngay_bg,
          this.chkLoiHoanCong.Checked,
          this.kt_thamso_thuhoi,
          this.rdoGiao_TheoQD.Checked,
          this.rdoGiaoNgay.Checked,
          this._XacMinh_TheoYeuCau,
          ds_hdtb)

        this.HienThiDanhSach2()
        this.loading(false)

        if (rs.error_code == SUCCESS_CODE) {
          this.$toast.success("Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !")
        } else {
          this.$toast.error(rs.message)
        }
      } catch (e) {
        console.error(e)
        this.$toast.error("Có lổi xảy ra.")
        this.loading(false)
      }
    },
    tsbtnGiaoPhieu_Click () {
      this.GiaoPhieu()
    },
    btnLyDoHuy_Click () {
      this.$refs.frmLyDoHuy.show(true)
    },
    frmLyDoHuy_OnAccepted () {
      this.HienThiDS_LyDoHuy(this.hdtb_id)
    },
    async GiaoPhieu () {
      try {
        if (!this.hdtb_id || !this.hdkh_id || !this.loaitb_id || !this.luong_id) {
          this.$toast.error('Không thể giao phiếu thuê bao này')
          return
        }

        const quytrinhId = await API.fn_tt_hd_thuebao(this.hdtb_id, 3)
        const huongGiaoId = await API.fn_giaophieu_chamdutsd(this.hdtb_id, this.hdkh_id, this.loaitb_id, this.luong_id)
        if (huongGiaoId.result && +huongGiaoId.result != 0) {
          window.open(`/#/contract/HandoverTicket?vquytrinh=${quytrinhId}&vhuonggiao_id=${huongGiaoId.result}&vdichvuvt_id=${this.dichvuvt_id}&vloai_hd=${LOAI_HOPDONG.CHAMDUT_SD}`)
        } else { this.$toast.error("Chưa có hướng giao được gán !") }
        this.HienThiDanhSach2()
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex)
      }
    },
    formatColumnChieuKhoaMo (field, data, column) {
      if (data[field] == 1) return 'Một chiều'
      if (data[field] == 2) return 'Hai chiều'

      return ''
    }
  }
}
</script>
<style scoped>
.h-button{
  margin-left: 10px;
  border: 1px solid #0176ff;
}
</style>
<style>
#u24023 .e-grid td.e-active{
  background-color: #FFF9EB !important;
}
</style>
