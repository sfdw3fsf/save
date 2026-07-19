<template src='./ReceiveProfile.html'>
</template>
<style scoped src='./ReceiveProfile.scss'></style>
<script>
import gridview from '@/components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import input3 from '@/components/form/VueInput.vue'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'
import * as moment from 'moment'
import api from './ReceiveProfileAPI'
import { Loai_NV } from '@/const/enums/index.js'
import { DATE_FORMAT } from "@/constants"
import * as controlUtil from "@/utils/controls"
import EditButton from "./components/EditButton.vue"
import { parseDate } from '@/utils/format.js'
import {
  Filter,
  Page,
  Resize
} from "@syncfusion/ej2-vue-grids"
import DeleteTemplate from './components/DeleteTemplate.vue'
import ConfirmTemplate from './components/ConfirmTemplate.vue'
import DeleteTemplateCustomerAppointment from './components/DeleteCustomerAppointment.vue'
import GroupSeenAndDowloadTemplate from './components/GroupSeenAndDowload.vue'
import { Query } from '@syncfusion/ej2-data'
import { SUCCESS_CODE } from '../../../../constants'

export default {
  props: {
    tag: {
      type: String,
      default: '2'
    }
  },
  components: {
    gridview,
    select3,
    input3,
    DatePicker,
    EditButton,
    DeleteTemplate
  },
  name: 'ReceiveProfile',
  provide: {
    grid: [Filter, Page, Resize]
  },
  data: () => {
    return {
      // deleteTemplate: DeleteTemplate,
      deleteTemplate: function () {
        return {
          template: DeleteTemplate
        }
      },
      deleteTemplateCustomerAppointment: function () {
        return {
          template: DeleteTemplateCustomerAppointment
        }
      },
      confirmTemplate: () => ({ template: ConfirmTemplate }),
      groupSeenAndDowloadTemplate: function () {
        return {
          template: GroupSeenAndDowloadTemplate
        }
      },
      cboTrangThai: { DataSource: [], EditValue: null },
      cboNhomFile: { DataSource: [], EditValue: null },
      CboLoaiFile: { DataSource: [], EditValue: null },
      cboLyDoTon: { DataSource: [], EditValue: null },
      cboNVPhat: { DataSource: [], EditValue: null, Enabled: true },
      cboNguoiDungDL: { DataSource: [], EditValue: null },
      cbxTuNgay: { Checked: true },
      txtMaGD: { Text: '' },
      dtpDenNgay: { EditValue: null },
      dtpTuNgay: { EditValue: null },
      dtpHenDenNgay: { EditValue: null },
      dtpNgayHenTu: { EditValue: null },
      gridDanhSach: { DataSource: [], GetFocusedRowCellValue: null },
      grcFileHS: { DataSource: [], GetFocusedRowCellValue: null },
      grcDS_GIAOVIEC: { DataSource: [], GetFocusedRowCellValue: null },
      grcDS_HENKH: { DataSource: [], GetFocusedRowCellValue: null },
      // grcDSDK: { DataSource: [], GetFocusedRowCellValue: null },
      btnXemFile: { Visible: true, Enabled: true },
      btnDowLoad: { Visible: true, Enabled: true },
      btnDel_GV: { Visible: true, Enabled: true },
      btnDelHen: { Visible: true, Enabled: true },
      txtDuongDan: { Text: '', Enabled: true },
      txtGhiChu: { Text: '', Enabled: true },
      txtNDGiao: { Text: '', Enabled: true },
      txtNDHen: { Text: '', Enabled: true },
      tsbtnXacNhan: { Visible: true, Enabled: true },
      btnXoaFile: { Visible: true, Enabled: true },
      btnHuyBo: { Visible: true, Enabled: true },
      tsbtnHoanCong: { Visible: true, Enabled: true },
      btnHuyPhieu: { Visible: true, Enabled: true },
      btnHenKH: { Enabled: true },
      btnGiaoViec: { Enabled: true },
      btnNhapMoi: { Enabled: true, Visible: true },
      labelControl1: { Text: '' },
      // --------------------------------------------------
      dtHSThueBao: [],
      ds_thuebao: [],
      dtIcon: [],
      sDuongDanDinhKem_Local: "",
      sDuongDanDinhKemFTP: "",
      title: "QUẢN LÝ CẤP PHÁT BỘ HỒ SƠ",
      tag_frm: "",
      ds_quyen: [],
      quyen_dl: 0,
      tsbtnThemFile: { Visible: true },
      nv_ql_am: [],
      // --------------------------------------------------
      momentFormat: {
        stringify: (date) => {
          return date ? moment(date).format(DATE_FORMAT) : ''
        },
        parse: (value) => {
          return value ? moment(value, DATE_FORMAT).toDate() : null
        }
      },
      selectionOptions: { type: 'Multiple', checkboxMode: 'ResetOnRowClick', enableToggle: false },
      pageSettings: { pageSize: 5 }
    }
  },
  computed: {
    vnhomfile_id () {
      return !this.cboNhomFile.EditValue ? -1 : parseInt(this.cboNhomFile.EditValue)
    },
    vlydoton_tc () {
      return !this.cboLyDoTon.EditValue ? -1 : parseInt(this.cboLyDoTon.EditValue)
    },
    vma_gd () {
      return !this.gridDanhSach.GetFocusedRowCellValue ? '' : this.gridDanhSach.GetFocusedRowCellValue["ma_gd"]
    },
    vhdkh_luoi_id () {
      return !this.gridDanhSach.GetFocusedRowCellValue ? -1 : this.gridDanhSach.GetFocusedRowCellValue["hdkh_id"]
    },
    vphieu_luoi_id () {
      return !this.gridDanhSach.GetFocusedRowCellValue ? -1 : this.gridDanhSach.GetFocusedRowCellValue["phieu_id"]
    },
    vbohs_id () {
      return !this.gridDanhSach.GetFocusedRowCellValue ? -1 : this.gridDanhSach.GetFocusedRowCellValue["bohs_id"]
    },
    vdonvi_id_tt () {
      return !this.gridDanhSach.GetFocusedRowCellValue ? -1 : this.gridDanhSach.GetFocusedRowCellValue["donvi_id_tt"]
    },
    vrid_hen () {
      return this.$refs.grcDS_HENKHRef.getSelectedRecords().length == 0 ? -1 : parseInt(this.$refs.grcDS_HENKHRef.getSelectedRecords()[0]["rid"])
    },
    vnhanth_id_luoigv () {
      return this.$refs.grcDS_GIAOVIECRef.getSelectedRecords().length == 0 ? -1 : parseInt(this.$refs.grcDS_GIAOVIECRef.getSelectedRecords()[0]["nhanvien_th_id"])
    },
    vloaifile_id () {
      return !this.CboLoaiFile.EditValue ? -1 : parseInt(this.dtpTuNgay.EditValue)
    },
    vtk_tungay () {
      return !this.dtpTuNgay.EditValue ? '' : this.dtpTuNgay.EditValue
    },
    vtk_denngay () {
      return !this.dtpDenNgay.EditValue ? '' : this.dtpDenNgay.EditValue
    },
    vname () {
      return this.$refs.grcFileHSRef.getSelectedRecords().length == 0 ? 'temp' : this.$refs.grcFileHSRef.getSelectedRecords()[0]["name"]
    },
    vurl_luoi () {
      return this.$refs.grcFileHSRef.getSelectedRecords().length == 0 ? '' : this.$refs.grcFileHSRef.getSelectedRecords()[0]["url"]
    },
    ttnd () {
      return {
        nhanvien_id: this.$auth.getNhanVienID(),
        donvi_id: this.$auth.getDonViID(),
        ngay_cn: this.$auth.getNgayCapNhat(),
        nguoidung_id: this.$auth.getNguoiDungID()
      }
    }
  },
  mounted () {
    this.initSliter()
    this.frmCapPhatBHS_Load()
    window.app = this
  },
  methods: {
    initSliter () {
      window['Split'](['#boxLeft', '#boxRight'], {
        sizes: [40, 60],
        gutterSize: 16,
        onDragEnd: function (sizes) {

        }
      })
      window['Split'](['#boxTop', '#boxBottom'], {
        sizes: [65, 35],
        direction: 'vertical'

      })
    },
    cboNguoiDungDLFiltering (args) {
      var query = new Query()
      query = (args.text) ? query.where("ten_nv", "contains", args.text, true) : query
      args.updateData(this.cboNguoiDungDL.DataSource, query)
    },
    cboNVPhatFiltering (args) {
      var query = new Query()
      query = (args.text) ? query.where("ten_nv", "contains", args.text, true) : query
      args.updateData(this.cboNVPhat.DataSource, query)
    },
    async frmCapPhatBHS_Load () {
      this.dtpDenNgay.EditValue = parseDate(this.ttnd.ngay_cn)
      this.dtpTuNgay.EditValue = moment(this.ttnd.ngay_cn, DATE_FORMAT).subtract(14, 'days').format(DATE_FORMAT)

      this.tag_frm = this.tag
      if (this.$route.query.tag) {
        this.tag_frm = this.$route.query.tag
      }

      if (this.tag_frm == "1") {
        this.title = "TIẾP NHẬN HỒ SƠ PHÁT KHÁCH HÀNG"
      }

      this.THIETLAP_QUYEN()
      this.LOAD_COMBO()
      this.KHOAMO_FORM(false)
    },
    async THIETLAP_QUYEN () {
      let lnv = await api.checkPermission()
      if (lnv && lnv.length != 0) {
        this.ds_quyen = lnv.map(p => parseInt(p["loainv_id"]))
        this.btnNhapMoi.Visible = false
        this.btnXoaFile.Visible = false
        this.tsbtnXacNhan.Visible = false
        this.tsbtnHoanCong.Visible = false
        this.btnGiaoViec.Enabled = false
        this.btnHenKH.Enabled = false
        if (this.ds_quyen.includes(Loai_NV.NHANVIEN_QL_AM) && this.tag_frm == "1") {
          this.quyen_dl = 1
          this.tsbtnXacNhan.Visible = true
          this.btnGiaoViec.Enabled = true
        }
        if (this.ds_quyen.includes(Loai_NV.NHANVIEN_QLDL) && this.tag_frm == "2") {
          this.quyen_dl = 2
          this.btnHenKH.Enabled = true
          this.btnNhapMoi.Visible = true
          this.btnXoaFile.Visible = true
          this.tsbtnXacNhan.Visible = true
          this.tsbtnHoanCong.Visible = true
          this.btnGiaoViec.Enabled = true
        }
      } else {
        this.quyen_dl = 0
        this.$toast.error("Bạn không có quyền vào chức năng này ")
        this.$router.replace("/admin/home")
      }
    },
    async LOAD_COMBO () {
      this.dtpNgayHenTu.EditValue = parseDate(this.ttnd.ngay_cn)
      this.dtpHenDenNgay.EditValue = parseDate(this.ttnd.ngay_cn)

      api.getStatus().then(rs => {
        controlUtil.loadComboBoxItems(this.cboTrangThai, rs, 'TRANGTHAI_ID', 0)
      })

      api.getGroupFile().then(rs => {
        controlUtil.loadComboBoxItems(this.cboNhomFile, rs, 'NHOMFILE_ID', 0)
      })

      api.getAgency(this.ttnd.nguoidung_id).then(rs => {
        this.nv_ql_am = rs
        controlUtil.loadComboBoxItems(this.cboNguoiDungDL, rs, 'nhanvien_id', 0)
      })

      api.getTypeFile().then(rs => {
        controlUtil.loadComboBoxItems(this.CboLoaiFile, rs, 'LOAIFILE_ID', 0)
      })

      api.getReason().then(rs => {
        controlUtil.loadComboBoxItems(this.cboLyDoTon, rs, 'LYDOTONTC_ID', 0)
      })

      // TODO: Revise ICON
      // bts.HT_DS_GRIDLOOKUP(cboLyDoTon, dt_1yton, "LYDOTON_TC", "LYDOTONTC_ID", true, 1, 400)
      // dtIcon = tdan.GET_DATA_SQL(@"  SELECT b.icon_img ,a.loaihs_id
      //  ROM {?DB1}.loai_hs_icon a,{?DB1}.icon b where a.icon_id=b.icon_id ")
      // if (dtIcon.Rows.Count > 0)
      // {
      //     for (int i = 0 i < dtIcon.Rows.Count i++)
      //     {
      //         byte[] _imageData
      //         if (dtIcon.Rows[i]["icon_img"] != DBNull.Value)
      //         {
      //             _imageData = null
      //             _imageData = new byte[((byte[])dtIcon.Rows[i]["icon_img"]).Length]
      //             _imageData = (byte[])dtIcon.Rows[i]["icon_img"]
      //             MemoryStream ms = new MemoryStream(_imageData)
      //             Image img = Image.FromStream(ms)
      //             img1.Images.Add(dtIcon.Rows[i]["LOAIHS_ID"], img)
      //         }
      //     }
      // }
    },
    KHOAMO_FORM (mo) {
      this.CboLoaiFile.Enabled = mo
      this.txtDuongDan.Enabled = mo
      this.txtGhiChu.Enabled = mo
    },
    cboTrangThai_EditValueChanged () {
      this.THAOTAC_NUT(3)
      if (this.cboTrangThai.EditValue == "1") {
        if (this.quyen_dl == 1) {
          this.tsbtnXacNhan.Visible = true
          this.btnGiaoViec.Enabled = true
        } else if (this.quyen_dl == 2) {
          this.tsbtnHoanCong.Visible = true
          this.tsbtnXacNhan.Visible = true
          this.btnXoaFile.Visible = true
          this.btnNhapMoi.Visible = true
          this.btnGiaoViec.Enabled = true
          this.btnHenKH.Enabled = true
        } else {
          this.btnNhapMoi.Visible = false
          this.btnXoaFile.Visible = false
          this.tsbtnXacNhan.Visible = false
          this.tsbtnHoanCong.Visible = false
          this.btnGiaoViec.Enabled = false
          this.btnHenKH.Enabled = false
        }
        // btnHuyPhieu.Visible = false
      } else {
        this.cbxTuNgay.Checked = true
        this.tsbtnHoanCong.Visible = false
        this.tsbtnXacNhan.Visible = false
        this.btnXoaFile.Visible = false
        this.btnNhapMoi.Visible = false
        this.btnGiaoViec.Enabled = false
        this.btnHenKH.Enabled = false
        // btnHuyPhieu.Visible = true
      }

      this.TIMKIEM("", 0)
    },
    THAOTAC_NUT (kieu) {
      this.tsbtnThemFile.Visible = false
      this.btnHuyBo.Visible = false
      if (kieu == 1) {
        this.KHOAMO_FORM(true)
        // Cập nhật nút
        this.btnNhapMoi.Enabled = false
        this.tsbtnThemFile.Visible = true
        this.btnHuyBo.Visible = true
        // Chọn control nhập
        // txtMaGD.Focus()
        this.$refs.txtMaGD.focus()
      } else if (kieu == 3) {
        // Xóa form
        this.KHOAMO_FORM(false)
        // Cập nhật nút
        this.btnNhapMoi.Enabled = true
      }
    },
    async TIMKIEM (ma_gd, thongbao) {
      try {
        this.$refs.txtMaGD.focus()
        this.CLEAR()
        this.gridDanhSach.DataSource = []
        if (!ma_gd) { ma_gd = "0" }

        let tuNgay = this.dtpTuNgay.EditValue
        let denNgay = this.dtpDenNgay.EditValue

        this.loading(true)

        const data = await api.getInfoFiles(
          ma_gd,
          0,
          0,
          this.cbxTuNgay.Checked ? 1 : 0,
          tuNgay,
          denNgay,
          this.cboTrangThai.EditValue,
          this.quyen_dl
        )

        if (!data || data.length == 0) {
          if (thongbao == 1) { this.$toast.error("Không tìm thấy thông tin phiếu giao đến") }
        } else {
          this.gridDanhSach.DataSource = data
        }

        setTimeout(() => {
          this.$refs.gridDanhSachRef.selectRow(0, false)
        }, 200)

        this.loading(false)
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lổi xảy ra')
      }
    },
    CLEAR () {
      this.grcFileHS.DataSource = []
      this.CboLoaiFile.EditValue = -1
      this.txtGhiChu.Text = ""
      this.txtDuongDan.Text = ""
      this.grcDS_GIAOVIEC.DataSource = []
      this.cboLyDoTon.EditValue = -1
      this.dtpNgayHenTu.EditValue = parseDate(this.ttnd.ngay_cn)
      this.dtpHenDenNgay.EditValue = parseDate(this.ttnd.ngay_cn)
    },
    async cboNguoiDungDL_EditValueChanged () {
      this.cboNVPhat.DataSource = []
      if (this.quyen_dl == 1) { // Nếu là quản lý AM
        this.cboNVPhat.Enabled = false
      } else if (this.quyen_dl == 2) {
        this.cboNVPhat.Enabled = true
      }
      if (this.cboNguoiDungDL.DataSource.length > 0) {
        const selectedNd = this.cboNguoiDungDL.DataSource.find(item => item.nhanvien_id == this.cboNguoiDungDL.EditValue)
        if (selectedNd) {
          api.getEmp(selectedNd.donvi_id).then(dt_ddl => {
            controlUtil.loadComboBoxItems(this.cboNVPhat, dt_ddl, 'nhanvien_id', 0)
          })
        }
      }
    },
    async btnGiaoViec_Click () {
      try {
        var ds = this.gridDanhSach.DataSource
        if (ds.length == 0) {
          this.$toast.error("Không có danh sách để giao việc")
          return
        }
        var t = this.$refs.gridDanhSachRef.getSelectedRecords()
        // .filter(r => r.ischeck == 1)
        if (t.length == 0) {
          this.$toast.error("Bạn chưa chọn danh sách. Không thể giao việc")
          return
        }

        if (this.txtNDGiao.Text == "") {
          this.$toast.error("Bạn chưa điền nội dung giao")
          return
        }

        if (this.quyen_dl == 0) {
          this.$toast.error("Bạn không có quyền thao tác ")
          return
        }

        this.loading(true)

        let nv_th_id = 0
        switch (this.quyen_dl) {
          case 1:
            nv_th_id = this.cboNguoiDungDL.EditValue
            break
          case 2:
            nv_th_id = this.cboNVPhat.EditValue
            break
        }

        const p_ds = t.map(r => ({ PHIEU_ID: r.phieu_id }))

        const data = await api.assignWork(
          p_ds,
          this.txtNDGiao.Text,
          nv_th_id,
          this.quyen_dl
        )
        if (data.error_code != SUCCESS_CODE) {
          this.$toast.error(data.message)
          return
        }

        this.HT_DS_GIAOVIEC()

        this.$toast.success("Giao việc thành công ")
      } catch (ex) {
        console.error(ex)
        this.$toast.error('Có lỗi xảy ra')
      } finally {
        this.loading(false)
      }
    },
    btnLayTT_Click () {
      this.TIMKIEM("", 1)
    },
    btnNhapMoi_Click () {
      this.THAOTAC_NUT(1)
    },
    btnHuyBo_Click () {
      this.THAOTAC_NUT(3)
    },
    tsbtnXacNhan_Click () {
      this.XACNHAN_DS()
    },
    tsbtnHoanCong_Click () {},
    btnHuyPhieu_Click () {},
    async HT_DS_GIAOVIEC () {
      try {
        this.grcDS_GIAOVIEC.DataSource = []
        var ds_gv = await api.getAssignList(this.vphieu_luoi_id)
        if (ds_gv && ds_gv.length > 0) {
          this.grcDS_GIAOVIEC.DataSource = ds_gv
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi lấy thông tin giao việc")
      }
    },
    gridDanhSach_FocusedRowChanged (e) {
      this.gridDanhSach.GetFocusedRowCellValue = e.data

      if (e.data) {
        this.CLEAR()
        this.NAP_DS_LUOI()
      } else {
        this.CLEAR()
      }
    },
    NAP_DS_LUOI () {
      this.NAP_DS_HOPDONGTB(this.vhdkh_luoi_id)
      this.LOADFILE_HS()
      this.LOC_COMBO()
      this.HT_DS_GIAOVIEC()
      this.HT_DS_HENKH()
    },
    LOC_COMBO () {
      this.txtNDGiao.Text = ""
      this.txtNDHen.Text = ""
      var loc = this.nv_ql_am.filter(item => item.donvi_id == this.vdonvi_id_tt)
      if (loc.length > 0) {
        this.cboNguoiDungDL.DataSource = loc
      } else {
        this.cboNguoiDungDL.DataSource = []
      }
    },
    async NAP_DS_HOPDONGTB (hdkh_id) {
      try {
        this.CLEAR()
        this.THAOTAC_NUT(3)
        var myDataTables = await api.lay_ds_hd_tb_theo_hdkh_id(hdkh_id)
        if (myDataTables && myDataTables.length > 0) {
          var ds = myDataTables.filter(item => item.hdkh_id != 0)
          if (ds.length == 0) {
            this.ds_thuebao = [...myDataTables]
          } else { this.ds_thuebao = [...ds] }
        } else {
          this.ds_thuebao = []
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi lấy thông tin hợp đồng")
      }
    },
    async LOADFILE_HS () {
      try {
        this.grcFileHS.DataSource = []
        if (this.vbohs_id == -1) {
          return
        }

        var ds = await api.getInfoFileDetail(this.vbohs_id)
        // .filter(item => item.kieu == 3)

        this.grcFileHS.DataSource = ds
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi lấy danh sách file theo hồ sơ ")
      }
    },
    grcFileHS_FocusedRowChanged (e) {
      this.grcFileHS.GetFocusedRowCellValue = e.rowData
    },
    grcDS_GIAOVIEC_FocusedRowChanged (e) {
      this.grcDS_GIAOVIEC.GetFocusedRowCellValue = e.rowData
    },
    grcDS_HENKH_FocusedRowChanged (e) {
      this.grcDS_HENKH.GetFocusedRowCellValue = e.rowData
    },
    async btnHenKH_Click () {
      try {
        const ngayHenTu = moment(this.dtpNgayHenTu.EditValue, DATE_FORMAT)
        const ngayHenDen = moment(this.dtpHenDenNgay.EditValue, DATE_FORMAT)
        if (!ngayHenTu.isValid() || !ngayHenDen.isValid()) {
          this.$toast.error("Bạn chưa chọn ngày hẹn")
          return
        }
        if (ngayHenDen.isBefore(ngayHenTu, 'day')) {
          this.$toast.error("Ngày hẹn từ phải nhỏ hơn hoặc bằng ngày hẹn đến")
          return
        }
        if (this.vphieu_luoi_id == -1) {
          this.$toast.error("Bạn chưa chọn phiếu để thực hiện hẹn với khách hàng")
          return
        }
        if (this.vlydoton_tc == -1) {
          this.$toast.error("Bạn chưa chọn lý do tồn hẹn với khách hàng")
          return
        }
        if (!this.txtNDHen.Text) {
          this.$toast.error("Bạn chưa nhập nội dung hẹn")
          return
        }

        this.loading(true)
        const rs = await api.setAppointment(
          this.cboLyDoTon.EditValue,
          this.vbohs_id,
          this.txtNDHen.Text,
          this.dtpHenDenNgay.EditValue + ' 00:00:00',
          this.dtpNgayHenTu.EditValue + ' 00:00:00',
          this.vphieu_luoi_id
        )
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
          return
        }
        this.$toast.success("Hẹn với KH thành công " + this.vma_gd)
        this.txtNDHen.Text = ""
        this.cboLyDoTon.EditValue = -1
        this.HT_DS_HENKH()
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi hẹn với KH")
      } finally {
        this.loading(false)
      }
    },
    async HT_DS_HENKH () {
      try {
        this.grcDS_HENKH.DataSource = []
        const ds_gv = await api.getAppointmentList()
        if (ds_gv && ds_gv.length > 0) {
          this.grcDS_HENKH.DataSource = ds_gv
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi lấy thông tin giao việc")
      }
    },
    async XACNHAN_DS () {
      try {
        if (!this.KIEMTRA_PHIEU(0)) return

        const cf = await this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xác nhận danh sách bộ hồ sơ không ?")
        if (!cf) {
          return
        }

        this.loading(true)

        const selectedRecords = this.$refs.gridDanhSachRef.getSelectedRecords()
        const selectedList = selectedRecords.map(item => ({
          "bhs_id": item.bohs_id,
          "dvgiao_id": item.donvi_giao_id,
          "dvnext_id": item.dvnext_id,
          "dvtt_id": item.dvtt_id,
          "lhs_id": item.lohs_id,
          "malhs": item.malo_hs,
          "nv_nhan": item.nv_nhan,
          "phieu_id": item.phieu_id,
          "phieu_id_cha": item.phieu_cha_id,
          "tthu_id": item.tuyenthutt_id
        }))

        const huonggiao_ht = selectedRecords[selectedRecords.length - 1].huonggiao_id

        const rs = await api.xacnhan_bohoso(
          selectedList,
          huonggiao_ht,
          2,
          "",
          this.quyen_dl
        )

        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
        } else {
          this.$toast.success('Thực hiện xác nhận thành công')
          await this.TIMKIEM("", 0)
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi thực hiện xác nhận")
      } finally {
        this.loading(false)
      }
    },
    KIEMTRA_PHIEU (kieu) {
      try {
        var dt = this.gridDanhSach.DataSource

        if (dt.length == 0) {
          this.$toast.error("Không có danh sách phiếu để thực hiện")
          return false
        }
        const selectedRecords = this.$refs.gridDanhSachRef.getSelectedRecords()
        if (selectedRecords.length == 0) {
          this.$toast.error("Bạn chưa chọn phiếu để thực hiện")
          return false
        }

        if (selectedRecords.filter(item => item.ischeck != 1).length > 0 && kieu == 1) {
          this.$toast.error("Trong danh sách có phiếu chưa được xác nhận bạn không thể thực hiện")
          return false
        }

        if (kieu != 1) return true
        return true
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi kiểm tra danh sách phiếu")
        return false
      }
    },
    btnDel_GV_ButtonClick (data) {
      try {
        console.log("btnDel_GV_ButtonClick", data)
        if (this.vphieu_luoi_id == -1) {
          this.$toast.error("Bạn chưa chọn phiếu để xóa giao việc")
          return
        }
        var kt = null
        // TODO: Wait api
        // tdan.GET_DATA_SQL("select count(*) from {?DB1}.giaophieu_bo_hs where phieu_id=" + vphieu_luoi_id +
        //                   " and ttph_id<>1")
        if (kt && kt[0] != "0") {
          this.$toast.error("Trạng thái phiếu đã bị thay đổi bạn không thể xóa giao việc")
          return
        }

        let kieu = 0
        switch (this.quyen_dl) {
          case 1:
            kieu = 1
            break
          case 2:
            kieu = 2
            break
        }

        if (data.kieu != kieu) { // kieu.ToString(CultureInfo.InvariantCulture)
          this.$toast.error("Bạn không được xóa kiểu giao khác")
          return
        }
        if (kieu == 1) {
          var kt_tt = null
          // TODO: Wait api
          // tdan.GET_DATA_SQL("select count(*) from {?DB1}.giaophieu_bhs_nv where phieu_id=" + vphieu_luoi_id +
          //                   " and kieu=2");
          if (kt_tt && kt_tt[0] != "0") {
            this.$toast.error("Đã có phiếu giao việc cho bộ phận khác bạn không thể xóa")
            return
          }
        }

        try {
          // TODO: Wait api
          // tdan.EXECUTE_SQL(
          //     @"Delete {?DB1}.giaophieu_bhs_nv where phieu_id=" + vphieu_luoi_id + " and kieu=" + kieu + " and nhanvien_th_id=" + vnhanth_id_luoigv + "");
          this.$toast.error("Xóa giao việc thành công cho mã GD " + this.vma_gd)
          this.HT_DS_GIAOVIEC()
        } catch (ex) {
          console.error(ex)
          this.$toast.error("Có lỗi khi giao việc")
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi giao việc")
      }
    },
    async btnDelHen_ButtonClick (data) {
      try {
        console.log("btnDelHen_ButtonClick", data)
        if (this.vphieu_luoi_id == -1) {
          this.$toast.error("Bạn chưa chọn phiếu để xóa hẹn")
          return
        }
        var kt = null
        kt = await api.fn_lay_soluong_phieu_bohs(this.vphieu_luoi_id)
        if (kt && kt[0] != "0") {
          this.$toast.error("Trạng thái phiếu đã bị thay đổi bạn không thể xóa hẹn ")
        }

        if (this.vrid_hen == -1) {
          return
        }
        try {
          // TODO: wait api
          // tdan.EXECUTE_SQL(
          //     @"Delete {?DB1}.bohs_hen where rid=" + this.vrid_hen + " ");

          this.$toast.error("Xóa hẹn khách hàng thành của mã GD: " + this.vma_gd)
          this.HT_DS_HENKH()
        } catch (ex) {
          console.error(ex)
          this.$toast.error("Có lỗi khi xóa hẹn")
        }
      } catch (ex) {
        console.error(ex)
        this.$toast.error("Có lỗi khi xóa hẹn")
      }
    },
    btnXemFile_ButtonClick (data) {
      console.log('btnXemFile_ButtonClick', data)
    },
    btnDowLoad_ButtonClick (e, data) {
      this.sDuongDanDinhKemFTP = this.vurl_luoi
      this.DOWLOAD_OPEN(data, false)
    },
    DOWLOAD_OPEN (data, xemFile) {
      if (!xemFile) {
        if (data && data.url && data.name) {
          var link = document.createElement("a")
          link.download = data.name
          link.href = data.url
          link.target = 'blank'
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
        }
      }
    }
  }
}
</script>
