<template src="./CompleteProfileChangeAccount.html"></template>
<style src="./CompleteProfileChangeAccount.scss"></style>
<script>
import DataGridC from '@/components/Shared/DataGrid/index.vue'
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import ChonNe from '@/modules/contract/setup/DeclareLandline/popup/NE.vue'
import breadcrumb from '@/components/breadcrumb'
import ChuyenTo from './Popups/ChuyenTo.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import GiaoPhieuNhanVien from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
// import Vue from 'vue'
import api from './API.js'
import TrangthaiSo from './Enum/TrangthaiSo.js'
import { TrangThaiHD, LOAI_DV, LoaiHopDong, DichVuVienThong, LoaiHinhTB, TRANGTHAI_DONGBO, KieuLapDat } from '@/const/enums/index.js'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';

export default {
  components: {
    breadcrumb,
    DataGridC,
    XLSX,
    moment,
    DatePicker,
    ChonNe,
    SearchContractModal,
    GiaoPhieuNhanVien,
    ChuyenTo,
    InBienBan,
    GiaoPhieuView,
    ExportDataModal
  },
  name: 'CompleteProfileChangeAccount',
  props: ['isPopup', 'p_Tag', '_vquytrinh_id'],
  data() {
    return {
      header: {
        title: '',
        list: []
      },
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      HdThueBaoId: 0,
      selIndex: [],
      showTimePanel: false,
      position: { X: 'center', Y: 'top' },
      DmDVVT: [],
      DichVuVtId: 0,
      DmQuyTrinh: [],
      QuyTrinhId: '',
      txtMaGD: '',
      txtKieuLD: '',
      DmLoaiHinhTB: [],
      LoaiHinhTBId: '',
      txtma_tb_moi: '',
      txtma_tb_moi_s1: '',
      txtMaTBcu: '',
      formTitle: 'HTHS - đổi số cố định/isdn',
      loaded: false,
      dtpNgayGV_Enabled: false,
      dtpNgayGV_checkbox: false,
      xuatPhieuShow: false,
      dtpNgayGV: {
        checkbox: false,
        val: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        Enabled: false
      },
      DsNhanVienGV: [],
      nhanVienGVId: '',
      dtpNgayBG: {
        checkbox: false,
        val: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        Enabled: false
      },
      dtpNgayBG_checkbox: false,
      dtpNgayBG_Enabled: false,
      txtTramNE: '',
      SoNE_checkboxAuto: false,
      SoNE_checkboxManual: false,
      txtSoNE: '',
      txtSoNECu: '',
      txtHostLD: '',
      txtHostVatLy: '',
      txtNoiDungGiao: '',
      txtTenThueBao: '',
      txtDiaChiTB: '',
      txtGhiChu: '',
      txtPassword: '',
      selectionOptions: {
        checkboxMode: 'ResetOnRowClick',
        enableToggle: true
      },
      DsNhanVienThucHien: {
        headers: [
          {
            fieldName: 'ten_nv',
            headerText: 'Tên nhân viên',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'so_dt',
            headerText: 'Số điện thoại',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          }
        ],
        data: []
      },
      DsThueBao: {
        headers: [
          {
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ TB',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tb_moi',
            headerText: 'Số máy/Acc mới',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'ma_tb_cu',
            headerText: 'Số máy/Acc cũ',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'ngay_ht',
            headerText: 'Ngày HT',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'in_status',
            headerText: 'Trạng thái in',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'ten_status',
            headerText: 'Kích hoạt tổng đài',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          }
        ],
        data: [],
        selectedItems: []
      },
      DmDvTk: [
        { id: 6, text: 'Host' },
        { id: 25, text: 'Trạm vệ tinh' }
      ],
      LoaiPhieuId: 0,
      fCheckSearch: false,
      cboLoaiDVTK: 6,
      DmDonVi: [],
      cboDViTimKiem: '',
      ThHdId: 0,
      DsDichVuVTId: 0,
      DsLoaiHDId: 1,
      HuongGiaoId: 0,
      huonggiao_id: 0,
      phieu_id: 0,
      LoaiDvId: 0,
      TenHuongGiao: '',
      LuongId: 0,
      selectedThueBao: {},
      Tag: '', // Fixed :IMS:9+14+1;11
      dvnhan_id: 0,
      ne_id: 0,
      nap_id: 0,
      tramvt_id: 0,
      tramtb_id: 0,
      dtThaoTac: [],
      PhieuId: 0,
      tsbtnGiaoViec: false,
      tsbtnGiaoViec_Enabled: false,
      tsbtnGiaoPhieu: true,
      tsbtnGiaoPhieu_Enabled: true,
      tsbtnKichHoat: false,
      tsbtnKichHoat_Enabled: true,
      tsbtnHoanCong: false,
      tsbtnHoanCong_Enabled: true,
      tsbtnChuyenDB_Enabled: true,
      btnChonNe_Enabled: true,
      btnChonNe_TuDong_Enabled: true,
      tsbtnHoanThienHS: false,
      tsbtnHoanThienHS_Enabled: true,
      tsbtnHoanThanh: false,
      tsbtnHoanThanh_Enabled: false,
      tsbtnChuyenTo: true,
      tsbtnChapNhan: true,
      tsbtnDuAn: false,
      btnInDS: true,
      btnPhieuTC: true,
      cboLoaiDVTK_Enabled: true,
      cboDViTimKiem_Enabled: true,
      cboLoaiHinhTB_Enabled: false,
      btnBienBanNT: true,
      showPassword: false,
      optionsVetinh: [
        {
          donvi_id: 0,
          ten_dv: ''
        }
      ],
      initDVVTArr: [],
      initLoaiHdArr: [],
      giaoPhieuNV: {
        nguoiGiao: '',
        PhieuId: 0,
        ngayGiao: new Date()
      },
      kt_thaydoi: false,
      nhanvien_tc_id: -1,
      congviec_th: '',
      modelIn: {},
      giaoPhieuViewDialog: {
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1,
        }
      },
      animationSettings: { effect: 'None' },
    }
  },
  watch: {
    dtpNgayBG_checkbox: function (val) {
      this.dtpNgayBG_Enabled = val
    },
    dtpNgayGV_checkbox: function (val) {
      this.dtpNgayGV_Enabled = val
    },
    DichVuVtId: function (val) {
      this.Loading = true
      Promise.all([this.lay_ds_quytrinh(val), this.lay_ds_loaihinh_tb(val)])
        .then((responses) => {
          // console.log(responses)
          this.Loading = true
        })
        .finally(() => {
          this.Loading = false
        })
      if (val == DichVuVienThong.IMS || val == DichVuVienThong.ADSL) {
        this.showPassword = true
      } else {
        this.showPassword = false
      }
    },
    cboLoaiDVTK: async function (val) {
      this.DmDonVi = []
      if (val == LOAI_DV.HOST) {
        await this.lay_ds_donvi_theo_loaidv()
      }
    },
    fCheckSearch: async function (val) {
      try {
        this.Loading = true
        if (val) {
          await this.lay_ds_donvi_theo_loaidv()
        } else {
          this.DmDonVi = []
        }

        if (this.DichVuVtId == DichVuVienThong.CO_DINH) {
          if (val) {
            if (this.cboDViTimKiem == null || this.cboDViTimKiem == '' || this.cboDViTimKiem == '0') return
            await this.HienThiDanhSachHDTB_TK_LOAIDV(this.cboLoaiDVTK, this.cboDViTimKiem)
          } else {
            this.cboLoaiDVTK_Enabled = false
            this.cboDViTimKiem_Enabled = false
            await this.HienThiDanhSach()
          }
        }
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    QuyTrinhId: async function (val) {
      try {
        if (!this.loaded) return
        this.Loading = true
        await this.QuyTrinh_Channged(val)
      } catch (err) {
        console.log('QuyTrinhId', err)
      } finally {
        this.Loading = false
      }
    },
    LoaiPhieuId: async function (val) {
      try {
        this.Loading = true
        await this.HienThiDanhSach()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    cboDViTimKiem: async function (val) {
      try {
        console.log('cboDViTimKiem-Watch:', val)
        this.Loading = true
        if (val == null || val == '' || val == '0') return
        await this.HienThiDanhSachHDTB_TK_LOAIDV(this.cboLoaiDVTK, val)
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    Loading: function (val) {
      this.$root.showLoading(val)
    }
  },
  computed: {
    iDichVuVtId: function () {
      return parseInt(this.DichVuVtId)
    }
  },
  async created() {
    this.giaoPhieuNV.nguoiGiao = this.$root.token.getNhanVienID()
    // 1
    if (this.$route.name.toLowerCase() == 'HostDoiSoCoDinhISDN'.toLowerCase()) {
      this.Tag = '9+14+1;11'
      this.formTitle = 'Host - Đổi số Cố định/ISDN'
    }
    // 2 [UR2.4.043] - Tổ Khai thác - Đổi số cố định/ISDN/IMS
    else if (this.$route.name.toLowerCase() == 'ChangeNumberLandline_KT'.toLowerCase()) {
      this.Tag = '9+14+1;11'
      this.formTitle = 'Tổ Khai thác - Đổi số cố định/ISDN/IMS'
    }
    // 3 [UR2.4.042] - Tổ KTVT - Đổi Account Internet
    else if (this.$route.name.toLowerCase() == 'DoiAccountInternet'.toLowerCase()) {
      this.Tag = '4+14+4'
      this.formTitle = 'Tổ KTVT - Đổi Account Internet'
    }
    // 4 [UR2.4.044] - Tổ KTVT - Đổi số cố định
    else if (this.$route.name.toLowerCase() == 'ChangeNumberLandline_KTVT'.toLowerCase()) {
      this.Tag = '4+14+1'
      this.formTitle = 'Tổ KTVT - Đổi số cố định'
    }
    // 5 :[UR2.6.019] - Hoàn thiện hồ sơ Đổi số / Account
    else if (this.$route.name.toLowerCase() == 'CompleteProfileChangeNumber'.toLowerCase()) {
      this.Tag = '5+14+1;4;11'
    } else {
    }
    // Bat neu truyen từ menu
    if (this.$route.query.tag != null && this.$route.query.tag != '' && this.$route.query.tag.length > 0) {
      console.log('Tag được gán từ query:', this.$route.query.tag)
      this.Tag = this.$route.query.tag
    }
    if (this.p_Tag != null && this.p_Tag != '' && this.p_Tag.length > 0) {
      this.Tag = this.p_Tag
    }
    if (this._vquytrinh_id != null && this.this._vquytrinh_id != '' && this._vquytrinh_id.length > 0) {
      this.quytrinh_id = this._vquytrinh_id
    }
    if (this.Tag != null && this.Tag.length > 0) {
      console.log('Tag', this.Tag)
      let arr = this.Tag.split('+')
      if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
      if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(',')
      if (arr.length >= 3) this.initDVVTArr = arr[2].split(';')
    } else {
      console.log('NoTag')
      if (this.$route.params.dsdichvuvt_id != null && this.$route.params.dsdichvuvt_id != '') {
        let dvvtIds = this.$route.params.dsdichvuvt_id
        this.initDVVTArr = dvvtIds.split(',')
      } else {
        this.initDVVTArr = [1]
      }
      if (this.$route.params.dsloaihd_id != null && this.$route.params.dsloaihd_id != '') {
        let dsLoaiHdIds = this.$route.params.dsloaihd_id
        this.initLoaiHdArr = dsLoaiHdIds.split(',')
      } else {
        this.initLoaiHdArr = [1]
      }
      if (this.$route.params.tthd_id != null && this.$route.params.tthd_id != '') {
        console.log('Gan trang thai hop dong tu query:', this.$route.params.tthd_id)
        this.ThHdId = parseInt(this.$route.params.tthd_id)
      } else {
        this.ThHdId = TrangThaiHD.DANG_THI_CONG // DANG_THI_CONG = 4;
      }
    }
    this.Loading = true
    await this.lay_ds_dvvt()
    Promise.all([this.lay_ds_quytrinh(this.DichVuVtId), this.lay_ds_loaihinh_tb(this.DichVuVtId), this.lay_ds_nhanvien_thuchien_theo_dv(), this.lay_ds_donvi_theo_loaidv()])
      .then((results) => {})
      .catch((errors) => {
        console.log(`Error in promises created :`, errors)
      })
      .finally((e) => {
        this.loaded = true
        this.Loading = false
      })
  },
  methods: {
    disabledRange: function (date) {
      return date > new Date()
    },
    Capitalize(input) {
      var capitalFirst = input.charAt(0).toUpperCase()
      var noCaseTail = input.slice(1, input.length)
      return capitalFirst + noCaseTail
    },
    lay_ds_phieu_giaoden_ims: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var input = {
        dichvuvt_id: DichVuVienThong.IMS,
        tthd_id: this.ThHdId,
        nhanvien_id: nhanvienId,
        ma_gd: '',
        phieu_id: 0,
        loaihd_id: LoaiHopDong.DOISO_TB,
        huonggiao_id: this.HuongGiaoId,
        loai_id: this.LoaiPhieuId
      }
      let data = this.GetData(await api.lay_ds_phieu_giaoden_ims(this.axios, input))
      if (data != null && data.length > 0) {
        // data.forEach(function (item) {
        //   if (item.ngay_ht_gp != null && item.ngay_ht_gp) {
        //     item.ngay_ht_gp = moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        //   }
        //   if (item.ngay_ht != null && item.ngay_ht) {
        //     item.ngay_ht = moment(item.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        //   }
        // })
      }
      this.DsThueBao.data = data
    },
    lay_ds_nhanvien_thuchien_theo_dv: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var input = { donvi_id: donviId }
      let data = this.GetData(await api.lay_ds_nhanvien_thuchien_theo_dv(this.axios, input))
      this.DsNhanVienGV = data.map((x) => ({
        id: x.nhanvien_id,
        text: x.ten_nv
      }))
      this.nhanVienGVId = nhanvienId
      return data
    },
    lay_ds_dvvt: async function () {
      var input = {}
      let data = this.GetData(await api.lay_ds_dvvt(this.axios, input))
      let arr = this.initDVVTArr
      this.DmDVVT = data.filter((x) => arr.filter((y) => y == x.DICHVUVT_ID).length > 0).map((x) => ({ id: x.DICHVUVT_ID, text: x.TEN_DVVT }))
      this.DichVuVtId = parseInt(this.DmDVVT[0].id)
      //await this.lay_ds_loaihinh_tb(this.DichVuVtId)
      return data
    },
    txtMaGD_KeyUp: async function () {
      try {
        if (this.Loading) return
        this.Loading = true
        await this.fn_hthsdoisothuebao_hienthidanhsach(0, this.txtMaGD)
        //await this.lay_ds_hopdong_huonggiao(this.txtMaGD)
        this.Loading = false
      } catch (ex) {
      } finally {
        this.Loading = false
      }
    },
    lay_ds_hopdong_huonggiao: async function (dichvuvt_id, matb) {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let v_loaiId = this.LoaiPhieuId
      // dtList = traCuuHD.LAY_DS_HOPDONG_HUONGGIAO(0, huonggiao_id, LoaiHopDong.DOISO_TB, tthd_id, (int)tt_nd.nhanvien_id, txtMaGD.Text, vloai_id).Tables[0];
      var input = {
        in_tthd_id: this.ThHdId,
        in_huonggiao_id: this.HuongGiaoId,
        in_dichvuvt_id: dichvuvt_id,
        in_loai_id: v_loaiId,
        in_loaihd_id: LoaiHopDong.DOISO_TB,
        in_ma_tb: matb
      }
      /* Test  */
      /*
      input = {
        in_tthd_id: 7,
        in_huonggiao_id: 361,
        in_dichvuvt_id: 1,
        in_loai_id: 0,
        in_loaihd_id: 2,
        in_ma_tb: '3728888'
      }
      */
      let data = this.GetData(await api.lay_ds_hopdong_huonggiao(this.axios, input))
      if (data != null && data.length > 0) {
        data.forEach(function (item) {
          if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
            item.ngay_ht_gp = moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          }
          if (item.ngay_ht != null && item.ngay_ht != '') {
            item.ngay_ht = moment(item.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          }
        })
      }
      if (data != null && data.length > 0) this.DsThueBao.data = this.toLowerArrayObject(data)
      else this.DsThueBao.data = []
      /* if (data == null || data.length <= 0) {
        this.ShowAlert(`Không tìm thấy thông tin theo mã: ${matb}`)
      } */
    },
    lay_luong_thaotac: async function () {
      var input = { luong_id: this.LuongId }
      let data = this.GetData(await api.lay_luong_thaotac(this.axios, input))
      if (data == null || data.length == 0) {
        console.log(`Không có cấu hình thao tác cho luồng dữ liệu ${this.LuongId}`)
        //this.ShowAlert(`Không có cấu hình thao tác cho luồng dữ liệu ${this.LuongId}`)
      }
      this.dtThaoTac = data
      // console.log('lay_luong_thaotac', data)
    },
    lay_thongtin_host: async function (hdtbId, kieu) {
      // alert('hdtbId')
      let input = { hdtbId: hdtbId, kieu: kieu }
      let data = this.GetData(await api.lay_thongtin_host(this.axios, input))
      return data
    },
    lay_ct_thaotac_control: async function (thaotacId) {
      var input = { thaotac_id: thaotacId }
      let data = this.GetData(await api.lay_ct_thaotac_control(this.axios, input))
      console.log('lay_ct_thaotac_control:', data)
      return data
    },
    lay_ds_loaihinh_tb_old: async function (dvvtId) {
      var input = {}
      let data = this.GetData(await api.lay_ds_loaihinh_tb(this.axios, input))
      // let dvvtId = this.DichVuVtId
      this.DmLoaiHinhTB = data
        .filter((x) => x.DICHVUVT_ID == dvvtId)
        .map((x) => ({
          id: x.LOAITB_ID,
          text: x.LOAIHINH_TB
        }))
      return data
    },
    lay_ds_loaihinh_tb: async function (dvvtId) {
      // var input = {}
      // let data = this.GetData(
      //   await api.lay_dulieu_bang_theo_dieukien(this.axios, {
      //     schema: 'css',
      //     table_name: 'loaihinh_tb',
      //     list_of_cols: 'loaihinh_tb,loaitb_id',
      //     where: `dichvuvt_id= ${dvvtId}`,
      //     order: ''
      //   })
      // )
      let data = this.GetData(
        await api.lay_ds_loaihinh_tb_theo_dvvt_id(this.axios, {
          dvvtId: dvvtId
        })
      )
      // let dvvtId = this.DichVuVtId
      this.DmLoaiHinhTB = data
        //.filter((x) => x.DICHVUVT_ID == dvvtId)
        .map((x) => ({
          id: x.loaitb_id,
          text: x.loaihinh_tb
        }))
      this.LoaiHinhTBId = this.DmLoaiHinhTB[0].id
      return data
    },
    DichVuVtId_Changed(val) {
      this.Loading = true
      Promise.all([this.lay_ds_quytrinh(val), this.lay_ds_loaihinh_tb(val)])
        .then((responses) => {
          // console.log(responses)
          this.Loading = true
        })
        .finally(() => {
          this.Loading = false
        })
      if (val == DichVuVienThong.IMS || val == DichVuVienThong.ADSL) {
        this.showPassword = true
      } else {
        this.showPassword = false
      }
    },
    lay_ds_quytrinh: async function (dichvuvtid) {
      let arr = this.initLoaiHdArr
      var input = {
        dsloaihd_id: arr.join(','), // this.DsLoaiHDId,
        dichvuvt_id: dichvuvtid, //this.DichVuVtId,
        tthd_id: this.ThHdId,
        kieu: 0
      }

      let data = this.GetData(await api.lay_ds_quytrinh(this.axios, input))
      console.log('lay_ds_quytrinh', 'input', input, 'data', data)
      if (data == null || data.length == 0) {
        this.DmQuyTrinh = []
        // this.ShowError(`Không có thông tin quy trình`)
        return
      }
      this.DmQuyTrinh = data.map((x) => ({
        id: x.quytrinh_id,
        text: x.quytrinh
      }))
      if (this.DmQuyTrinh.length > 0) {
        this.QuyTrinhId = this.DmQuyTrinh[0].id
        await this.QuyTrinh_Channged(this.QuyTrinhId)
        // if (!this.loaded) await this.QuyTrinh_Channged(this.QuyTrinhId)
      }
    },
    lay_ds_donvi_theo_loaidv: async function () {
      var input = {
        loaidv_id: LOAI_DV.HOST
      }
      let data = this.GetData(await api.lay_ds_donvi_theo_loaidv(this.axios, input))
      if (data != null) data.unshift({ donvi_id: 0, ten_dv: ' ' })
      this.DmDonVi = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
      return data
    },
    async cboDViTimKiem_EditValueChanged() {
      try {
        if (this.cboDViTimKiem == '' || this.cboDViTimKiem == '0') return
        await this.HienThiDanhSachHDTB_TK_LOAIDV(this.cboLoaiDVTK, this.cboDViTimKiem)
      } catch (err) {
        console.log('cboDViTimKiem_EditValueChanged', err)
        // Message_Box.ShowError("" + ex.ToString());
      }
    },
    lay_ds_ds_huonggiao: async function (quytrinh) {
      let quytrinh_id = this.QuyTrinhId
      if (quytrinh > 0) {
        quytrinh_id = quytrinh
      }
      var input = {
        quytrinh_id: quytrinh_id,
        dstthd_id: this.ThHdId
      }
      let data = this.GetData(await api.lay_ds_ds_huonggiao(this.axios, input))
      console.log('lay_ds_ds_huonggiao', 'QuyTrinhId', this.QuyTrinhId, 'data', data)
      return data
    },
    lay_ds_hdtb_dv: async function (hdtb_id, loaidv_id) {
      var input = { hdtb_id: hdtb_id, loaidv_id: loaidv_id }
      let data = this.GetData(await api.lay_ds_hdtb_dv(this.axios, input))
      return data
    },
    lay_ds_nhanvien_theo_phieu_id: async function (phieuId) {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var input = {
        kieu_id: 2,
        phieu_id: phieuId
      }
      // var input = { PHIEU_ID: phieuId, NHANVIEN_ID: nhanvienId }
      let data = this.GetData(await api.lay_ds_nhanvien_theo_phieu_id(this.axios, input))
      this.DsNhanVienThucHien.data = data
      return data
    },
    hoanthien_hs_thaydoi_matb: async function (hdtb_id) {
      var input = { hdtb_id: hdtb_id }
      let data = this.GetData(await api.hoanthien_hs_thaydoi_matb(this.axios, input))
    },
    capnhat_tt_phieu_gphone: async function () {
      var input = { hdtb_id: hdtb_id }
      let data = this.GetData(await api.capnhat_tt_phieu_gphone(this.axios, input))
    },
    lay_ds_thamso_md: async function (mathamso) {
      var input = {}
      let data = this.GetData(await api.lay_ds_thamso_md(this.axios, input))
      return data
    },
    lay_matb_neo_id_theo_hdtb: async function (hdtb_id) {
      var input = { hdtb_id: hdtb_id }
      let data = this.GetData(await api.lay_matb_neo_id_theo_hdtb(this.axios, input))
    },
    // lay_thongtin_host: async function (hdtb_id, kieuid) {
    //   var input = { hdtb_id: hdtb_id, kieu: kieuid }
    //   let data = this.GetData(await api.lay_thongtin_host(this.axios, input))
    // },
    get_db_thuebao_by_id: async function () {
      var input = { thuebao_id: thuebao_id }
      let data = this.GetData(await api.get_db_thuebao_by_id(this.axios, input))
      return data
    },
    kiemtra_tb_sd_dvgt: async function (thuebao_id) {
      var input = { thuebao_id: thuebao_id, dichvuvt_id: this.DichVuVtId }
      let data = this.GetData(await api.kiemtra_tb_sd_dvgt(this.axios, input))
      return data
    },
    kt_taodb_thang: async function (kyhoadon) {
      let input = { kyhoadon: kyhoadon }
      let data = this.GetData(await api.kt_taodb_thang(this.axios, input))
      return data
    },
    lay_huonggiao_theo_luong_id: async function (luongid) {
      let input = { luong_id: luongid }
      let data = this.GetData(await api.lay_huonggiao_theo_luong_id(this.axios, input))
      return data
    },
    update_tt_giaoviec: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = {
        nhanvien_hc_id: nhanvienId,
        ngay_ht: this.dtpNgayBG.val,
        hdtb_id: this.HdThueBaoId,
        phieu_id: this.PhieuId,
        nhanvien_gv_id: this.nhanVienGVId
      }
      let response = await api.update_tt_giaoviec(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') return true
        else {
          this.ShowError(`Lỗi cập nhật thông tin giao việc `)
          return false
        }
      } else {
        this.ShowError(`Lỗi cập nhật thông tin giao việc: ${response.data.error_code} -${response.data.message} `)
        console.log(response.data.error_code)
      }
    },
    capnhat_ngay_kichhoat: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let username = this.$root.token.getUserName()
      var input = {
        hdtb_id: this.HdThueBaoId,
        ngay_kh: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        status: 5,
        nguoi_kh: username,
        ip_kh: 'admin'
      }
      let data = this.GetData(await api.capnhat_ngay_kichhoat(this.axios, input))
      return data
    },
    // kt_taodb_thang: async function () {
    //   var input = { kyhoadon: '2234' }
    //   let data = this.GetData(await api.kt_taodb_thang(this.axios, input))
    // },
    tracuu_tb_co_dinh: async function (maThueBao) {
      var input = { maThueBao: maThueBao }
      let data = this.GetData(await api.tracuu_tb_co_dinh(this.axios, input))
      return data
    },
    capnhat_trangthai_ne: async function (ne_id, trangthaiso_id) {
      var input = { ne_id: ne_id, trangthaiso_id: trangthaiso_id }
      let data = this.GetData(await api.capnhat_trangthai_ne(this.axios, input))
      return data
    },
    lay_ne_cu_ne_moi: async function () {
      var input = { hdtb_id: this.HdThueBaoId /* 2122392 */ }
      let data = this.GetData(await api.lay_ne_cu_ne_moi(this.axios, input))
      return data
    },
    fn_kt_thuebao_sd_ne: async function (ne_id, ma_tb) {
      var input = { ne_id: ne_id, ma_tb: ma_tb }
      let data = this.GetData(await api.fn_kt_thuebao_sd_ne(this.axios, input))
      return data
    },
    delete_giaophieu_nv: async function (phieu_id) {
      var input = { phieu_id: phieu_id }
      let response = await api.delete_giaophieu_nv(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') return true
        else return false
      } else {
        console.log(response.data.error_code)
      }
    },
    insert_giaophieu_nv: async function (data) {
      var input = { json_giaophieu_nv: JSON.stringify(data) }
      let response = await api.insert_giaophieu_nv(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') return true
        else return false
      } else {
        console.log(response.data.error_code)
      }
    },
    lay_thongtin_ne_theo_tramvt: async function (tramvt_id) {
      let input = { tramvt_id: tramvt_id }
      let data = this.GetData(await api.lay_thongtin_ne_theo_tramvt(this.axios, input))
      return data
    },
    capnhat_sone: async function () {
      let donviId = this.$root.token.getDonViID()
      let nguoidungid = this.$root.token.getNguoiDungID()
      let input = {
        hdtb_id: this.HdThueBaoId,
        ma_tb: this.txtma_tb_moi,
        ne_id: this.ne_id,
        sone: this.txtSoNE,
        nap_id: 0,
        tramtb_id: this.tramtb_id
      }
      let response = await api.capnhat_sone(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        this.ShowError(response.data.error_code)
        return false
      }
    },
    select_selectThueBao: async function (i, item) {
      //this.$refs.gridDsThueBao.grid.selectRow(i)
      console.log('select_selectThueBao', item)
      this.selectedThueBao = item
      let hdtb_id = item.hdtb_id

      this.HdThueBaoId = hdtb_id
      let ne_id = 0
      let nap_id = 0

      let dichvuvt_id = item.dichvuvt_id
      this.DichVuVtId = parseInt(dichvuvt_id)
      let loaitb_id = item.loaitb_id
      this.txtMaGD = item.ma_gd
      this.txtMaTBcu = item.ma_tb_cu
      this.txtma_tb_moi_s1 = item.ma_tb
      this.txtma_tb_moi = item.ma_tb
      this.LoaiHinhTBId = loaitb_id
      this.txtKieuLD = item.ten_kieuld
      this.txtTenThueBao = item.ten_tb
      this.txtDiaChiTB = item.diachi_tb
      this.txtGhiChu = item.ghichu
      let tthd_id = item.tthd_id
      let thuebao_id = item.thuebao_id
      if (dichvuvt_id == DichVuVienThong.CO_DINH) {
        this.txtSoNECu = item.necu
        this.txtSoNE = item.nemoi
        if (item.ne_id != null && item.ne_id != '') {
          this.ne_id = item.ne_id
          let data = await this.MapNeId(item.ne_id)
          this.txtTramNE = data
        }
        let _tramId = await this.MapTramId(hdtb_id)
        if (_tramId == '-1') {
          if (item.kieuld_id != KieuLapDat.DOINHOM_ISDN) {
            if (loaitb_id == LoaiHinhTB.ISDN2B_CD || loaitb_id == LoaiHinhTB.ISDN2B_CQ) {
              let tb_cha_id = this.MapThueBaoChaId(thuebao_id)
              if (tb_cha_id == -1) {
                this.ShowError('Thuê bao chưa được gán trạm thiết bị, bạn hãy kiểm tra lại')
                return
              }
            }
          }
        } else {
          let dataTram = await this.lay_ds_hdtb_dv(hdtb_id, 5) // TRAM_VT
          if (dataTram != null && dataTram.length > 0) {
            this.tramvt_id = dataTram[0].donvi_id
          }
        }
      }
      this.txtHostLD = ''
      this.txtHostVatLy = ''
      try {
        if (this.PHAILAM('HIENTHI_HOST')) {
          // Nếu có tham số này thì hiển thị thông tin host lắp đặt, host vật lý
          var dt = await this.lay_thongtin_host(item.hdtb_id, 0)
          if (dt.length > 0) {
            this.txtHostLD = dt[0]['host_ld']
            this.txtHostVatLy = dt[0]['host_vl']
          }
        }
      } catch (ex) {
        console.log('this.PHAILAM(HIENTHI_HOST)', ex)
      }
      this.txtNoiDungGiao = item.nd_giao
      this.PhieuId = item.phieu_id
      this.phieu_id = item.phieu_id
      if (dichvuvt_id == DichVuVienThong.IMS) this.dvnhan_id = item.donvitb_id
      else this.dvnhan_id = item.donvi_id
      await this.lay_ds_nhanvien_theo_phieu_id(this.PhieuId)
      this.HienThiDSNV()
      let kt_status = item.status
      if (this.PHAILAM('CAPNHAT_NE')) {
        if (dichvuvt_id == DichVuVienThong.CO_DINH) {
          if (kt_status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
            // TRANGTHAI_DONGBO.DONGBO_SERVICE.ToString()
            this.btnChonNe_Enabled = false
            this.btnChonNe_TuDong_Enabled = false
          } else {
            this.btnChonNe_Enabled = true
            this.btnChonNe_TuDong_Enabled = true
          }
        }
      }
      //
      // Nếu phải kích hoạt tổng đài
      if (this.PHAILAM('KICHHOAT_TD')) {
        if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
          this.dtpNgayBG_Enabled = true
          this.dtpNgayBG.val = item.ngay_ht_gp
          this.dtpNgayBG_checkbox = true
          let trangthai_db = ''
          if (dichvuvt_id == DichVuVienThong.CO_DINH) trangthai_db = TRANGTHAI_DONGBO.DONGBO_SERVICE
          else trangthai_db = TRANGTHAI_DONGBO.DONGBO_CM

          if (kt_status == trangthai_db) {
            if (this.PHAILAM('GIAOPHIEU')) {
              this.tsbtnGiaoPhieu_Enabled = true
              this.tsbtnKichHoat_Enabled = false
            } else {
              this.tsbtnHoanCong_Enabled = true
              this.tsbtnKichHoat_Enabled = false
            }
          } else {
            this.tsbtnGiaoPhieu_Enabled = false
            this.tsbtnKichHoat_Enabled = true
            this.tsbtnHoanCong_Enabled = false
          }
        } else {
          if (!this.PHAILAM('GIAOVIEC')) {
            this.dtpNgayBG_checkbox = true
            this.dtpNgayBG_Enabled = true
          } else {
            this.dtpNgayBG_checkbox = false
            this.dtpNgayBG_Enabled = false
          }
          this.dtpNgayBG.val = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          this.tsbtnGiaoPhieu = false
          this.tsbtnHoanCong_Enabled = false
          this.tsbtnKichHoat_Enabled = false
        }
      }
      // else // Nếu không phải kích hoạt
      else {
        // Nếu phải giao phiếu
        if (this.PHAILAM('GIAOPHIEU')) {
          if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
            this.dtpNgayBG_Enabled = true
            this.dtpNgayBG.val = item.ngay_ht_gp
            this.dtpNgayBG_checkbox = true
            this.tsbtnGiaoPhieu = true
            this.tsbtnHoanCong_Enabled = false
            this.tsbtnKichHoat_Enabled = false
          } else {
            if (!this.PHAILAM('GIAOVIEC')) {
              this.dtpNgayBG_checkbox = true
              this.dtpNgayBG_Enabled = true
            } else {
              this.dtpNgayBG_checkbox = false
              this.dtpNgayBG_Enabled = false
            }
            this.dtpNgayBG.Value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
            this.tsbtnGiaoPhieu_Enabled = false
            this.tsbtnHoanCong_Enabled = false
            this.tsbtnKichHoat_Enabled = false
          }
        } // Nếu không phải giao phiếu
        else {
          // Nếu phải kích hoạt tổng đài
          if (this.PHAILAM('HOANCONG')) {
            if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
              this.dtpNgayBG_Enabled = true
              this.dtpNgayBG.val = item.ngay_ht_gp
              this.dtpNgayBG_checkbox = true
              this.tsbtnHoanCong_Enabled = true
            } else {
              if (!this.PHAILAM('GIAOVIEC')) {
                this.dtpNgayBG_checkbox = true
                this.dtpNgayBG_Enabled = true
              } else {
                this.dtpNgayBG_checkbox = false
                this.dtpNgayBG_Enabled = false
              }
              this.dtpNgayBG.val = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
              this.tsbtnHoanCong_Enabled = false
            }
          }
        }
      }
      //
      if (this.PHAILAM('HOANTHIENHOSO')) {
        if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
          this.dtpNgayBG_Enabled = true
          this.dtpNgayBG.val = item.ngay_ht_gp
          this.dtpNgayBG_checkbox = true
          this.tsbtnHoanThienHS_Enabled = true
        } else {
          if (!this.PHAILAM('GIAOVIEC')) {
            this.dtpNgayBG_checkbox = true
            this.dtpNgayBG_Enabled = true
          } else {
            this.dtpNgayBG_checkbox = false
            this.dtpNgayBG_Enabled = false
          }
          this.dtpNgayBG.val = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          this.tsbtnHoanThienHS_Enabled = false
        }
      }
    },
    HienThiDSNV: function () {
      if (this.DsNhanVienThucHien.data != null && this.DsNhanVienThucHien.data.length > 0) {
        this.dtpNgayGV_checkbox = true
        this.dtpNgayGV.val = this.DsNhanVienThucHien.data[0].ngaygiao
        this.nhanVienGVId = this.DsNhanVienThucHien.data[0].nhanvien_giao_id
      } else {
        if (!this.PHAILAM('GIAOVIEC')) this.dtpNgayGV_checkbox = true
        else this.dtpNgayGV_checkbox = false
        this.dtpNgayGV.val = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        let nhanvienId = this.$root.token.getNhanVienID()
        this.nhanVienGVId = nhanvienId
      }
    },
    PHAILAM: function (code) {
      code = code.trim().toUpperCase()
      if (this.dtThaoTac == null || this.dtThaoTac.length == 0) {
        console.log('Không có cấu hình thao tác phải làm !')
        return false
      }
      if (this.dtThaoTac.filter((x) => x.enable == '1' && x.code.toUpperCase() == code).length > 0) {
        console.log('PHAILAM:', code, true)
        return true
      }
      console.log('PHAILAM:', code, false)
      return false
    },
    MapNeId: async function (ne_id) {
      // let input = { id_neo: 'ten_dv', in_table: 'css.sone a, admin.donvi b', in_dk: `where a.donvi_id = b.donvi_id and a.ne_id =${ne_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_sone(this.axios, { param: ne_id, type: 1 }))
      return data
    },
    MapTramId: async function (hdtb_id) {
      // let input = { id_neo: 'tramtb_id', in_table: 'hdtb_cd', in_dk: `where hdtb_id = ${hdtb_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_hdtb_cd(this.axios, { param: hdtb_id, type: 1 }))
      return data
    },
    MapThueBaoId: async function (hdtb_id) {
      // let input = { id_neo: 'thuebao_id', in_table: 'hd_thuebao', in_dk: `where hdtb_id =${hdtb_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { param: hdtb_id, type: 5 }))
      return data
    },
    MapDonViId: async function (thanhtoan_id) {
      // let input = { id_neo: 'donvi_id', in_table: 'db_thanhtoan', in_dk: `where thanhtoan_id =${thanhtoan_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thanhtoan(this.axios, { param: thanhtoan_id, type: 1 }))
      return data
    },
    MapThanhToanId: async function (thuebao_id) {
      // let input = { id_neo: 'thanhtoan_id', in_table: 'db_thuebao', in_dk: `where thuebao_id =${thuebao_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thuebao(this.axios, { param: thuebao_id, type: 1 }))
      return data
    },
    MapHdkhId: async function (hdtb_id) {
      // let input = { id_neo: 'hdkh_id', in_table: 'hd_thuebao', in_dk: `where hdtb_id =${hdtb_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_hd_thuebao(this.axios, { param: hdtb_id, type: 1 }))
      return data
    },
    MapThueBaoChaId: async function (thuebao_id) {
      // let input = { id_neo: 'thuebao_cha_id', in_table: 'db_thuebao', in_dk: `where thuebao_id =${thuebao_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_db_thuebao(this.axios, { param: thuebao_id, type: 5 }))
      return data
    },
    MapHuongGiaoId: async function (huonggiao_id) {
      // let input = { id_neo: 'huonggiao', in_table: 'huonggiao', in_dk: `where huonggiao_id=${huonggiao_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_huonggiao(this.axios, { param: huonggiao_id, type: 2 }))
      return data
    },
    Map_MA_LHTB: async function (loaitb_id) {
      // let input = { id_neo: 'MA_LHTB', in_table: 'loaihinh_tb', in_dk: `where LOAITB_ID=${loaitb_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, { param: loaitb_id, type: 3 }))
      return data
    },
    Map_MA_LHTB_VDC: async function (loaitb_id) {
      // let input = { id_neo: 'MA_LHTB_VDC', in_table: 'loaihinh_tb', in_dk: `where LOAITB_ID=${loaitb_id}` }
      // let data = this.GetData(await api.map_id(this.axios, input))
      let data = this.GetData(await api.fn_tt_loaihinh_tb(this.axios, { param: loaitb_id, type: 4 }))
      return data
    },
    HienThiDanhSachHDTB_TK_LOAIDV: async function (vloaidv_id, vdonvi_tk_id) {
      try {
        await this.lay_ds_hopdong_huonggiao_theo_dv(vloaidv_id, vdonvi_tk_id)
      } catch (err) {
        console.log('HienThiDanhSachHDTB_TK_LOAIDV', err)
      } finally {
      }
    },
    HienThiDanhSach: async function () {
      try {
        let v_loaiId = this.LoaiPhieuId
        this.Clear()
        this.Loading = true
        await this.fn_hthsdoisothuebao_hienthidanhsach(this.DichVuVtId, '0')
        /*
      if (this.DichVuVtId == DichVuVienThong.IMS) {
        await this.lay_ds_phieu_giaoden_ims()
      } else {
        await this.lay_ds_hopdong_huonggiao(this.txtMaGD)
      }
      */
        // if (this.DsThueBao.data != null && this.DsThueBao.data.length > 0) {
        //   Vue.nextTick(() => {})
        // }
        if (this.DichVuVtId == DichVuVienThong.ADSL) {
          if (this.DsThueBao.data != null && this.DsThueBao.data.length > 0) {
            this.txtPassword = this.DsThueBao.data[0].MATKHAU
          }
        }

        if (this.DichVuVtId == DichVuVienThong.IMS) {
          if (this.DsThueBao.data != null && this.DsThueBao.data.length > 0) {
            this.txtPassword = this.DsThueBao.data[0].password
          }
        }
      } catch (err) {
        console.log(`HienThiDanhSach-Err`, err)
      } finally {
        this.Loading = false
      }
    },
    HienThiGiaoDien: async function (vluong_id) {
      this.tsbtnKQ_DoKiem = false
      this.tsbtnDuAn = false
      this.tsbtnHoanCong = false
      this.tsbtnHoanThienHS = false
      this.tsbtnGiaoPhieu = false
      this.tsbtnHoanThanh = false
      this.tsbtnGiaoPhieu = false
      this.tsbtnGiaoViec = false
      this.btnChonNe_Enabled = false
      this.btnChonNe_TuDong_Enabled = false

      this.tsbtnKichHoat = false
      this.tsbtnKichHoat_Enabled = false
      this.tsbtnHoanCong_Enabled = false
      console.log('HienThiGiaoDien:vluong_id', vluong_id)
      await this.lay_luong_thaotac(vluong_id)
      var self = this
      // let test = true
      // if (test) {
      //   self.tsbtnGiaoViec = true
      //   self.tsbtnKichHoat = true
      //   self.tsbtnHoanCong = true
      //   self.tsbtnGiaoPhieu = true
      //   self.tsbtnHoanThienHS = true
      //   self.tsbtnHoanThanh = true
      //   return
      // }
      for (const item of this.dtThaoTac) {
        console.log('thaotac_id', item.thaotac_id, item.code, item.enable)
        let dtControl = await self.lay_ct_thaotac_control(item.thaotac_id)
        console.log('thaotac_id:control', item.code, dtControl)
        if (dtControl != null && dtControl.length > 0) {
          // control_name
          if (item.enable == 1) {
            if (dtControl.filter((x) => x.control_name == 'tsbtnGiaoViec').length > 0) {
              self.tsbtnGiaoViec = true
              self.tsbtnGiaoViec_Enable = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnKichHoat'.toLowerCase()).length > 0) {
              self.tsbtnKichHoat = true
              self.tsbtnKichHoat_Enabled = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanCong'.toLowerCase()).length > 0) {
              self.tsbtnHoanCong = true
              self.tsbtnHoanCong_Enabled = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnGiaoPhieu'.toLowerCase()).length > 0) {
              self.tsbtnGiaoPhieu = true
              self.tsbtnGiaoPhieu_Enabled = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanThienHS'.toLowerCase()).length > 0) {
              self.tsbtnHoanThienHS = true
              self.tsbtnHoanThienHS_Enabled = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanThanh'.toLowerCase()).length > 0) {
              self.tsbtnHoanThanh = true
              self.tsbtnHoanThanh_Enabled = true
            } else {
            }
            let keys = Object.keys(self)
            for (const ctrlName of dtControl) {
              if (keys.filter((x) => x == ctrlName.control_name).length > 0) self[ctrlName.control_name] = true
            }
          }
        }
      }
      /*
      this.dtThaoTac.forEach((item) => {
        console.log('thaotac_id',item.thaotac_id, item.code, item.enable)
        let dtControl = self.lay_ct_thaotac_control(item.thaotac_id)
        console.log(item.code,dtControl)
        if (dtControl != null && dtControl.length > 0) {
          // control_name
          if (item.enable == 1) {
            if (dtControl.filter((x) => x.control_name == 'tsbtnGiaoViec').length > 0) {
              self.tsbtnGiaoViec = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnKichHoat'.toLowerCase()).length > 0) {
              self.tsbtnKichHoat = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanCong'.toLowerCase()).length > 0) {
              self.tsbtnHoanCong = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnGiaoPhieu'.toLowerCase()).length > 0) {
              self.tsbtnGiaoPhieu = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanThienHS'.toLowerCase()).length > 0) {
              self.tsbtnHoanThienHS = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHoanThanh'.toLowerCase()).length > 0) {
              self.tsbtnHoanThanh = true
            } else {
            }
          }
        }
      })
      */
    },
    HienThiGiaoDien_OLD: async function (vluong_id) {
      await this.lay_luong_thaotac(vluong_id)
      var self = this
      this.dtThaoTac.forEach((item) => {
        console.log('item1', item)
        let dtControl = self.lay_ct_thaotac_control(item.thaotac_id)
        if (dtControl != null && dtControl.length > 0) {
          // control_name
          if (item.enable == 1) {
            if (dtControl.filter((x) => x.control_name == 'tsbtnGiaoViec').length > 0) {
              self.tsbtnGiaoViec = true
            } else if (dtControl.filter((x) => x.control_name == 'tsbtnKichHoat').length > 0) {
              self.tsbtnKichHoat = true
            } else if (dtControl.filter((x) => x.control_name == 'tsbtnHoanCong').length > 0) {
              self.tsbtnHoanCong = true
            } else if (dtControl.filter((x) => x.control_name == 'tsbtnGiaoPhieu').length > 0) {
              self.tsbtnGiaoPhieu = true
            } else if (dtControl.filter((x) => x.control_name == 'tsbtnHoanThienHS').length > 0) {
              self.tsbtnHoanThienHS = true
            } else if (dtControl.filter((x) => x.control_name == 'tsbtnHoanThanh').length > 0) {
              self.tsbtnHoanThanh = true
            } else {
            }
          }
        }
      })
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code == 'BSS-00000000') return response.data.data
        return []
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    tsbtnChapNhan_Click: async function () {
      try {
        if (!this.KT_DuLieuNhap()) return
        this.Loading = true
        let giaoviec = this.PHAILAM('GIAOVIEC') ? 0 : 1
        let nhanvienId = this.$root.token.getNhanVienID()
        let giaoviec_nv_data = await this.TaoDuLieu_NVTH()
        let nhanvien_giao_id = this.nhanVienGVId
        let phieuId = this.PhieuId
        let hdtb_id = this.HdThueBaoId
        let ngaygiaoText = moment(this.dtpNgayBG.val, 'DD/MM/YYYY HH:mm:ss').format('YYYY-MM-DD HH:mm:ss')
        let ngaygiao = ngaygiaoText // moment(ngaygiaoText).local().format()
        // alert(this.dtpNgayBG.val + ':' + ngaygiao)
        let input = { giaoviec: giaoviec, phieu_id: phieuId, js_giaophieu_nv: JSON.stringify(giaoviec_nv_data), nhanvien_id: parseInt(nhanvienId), nhanvien_giao_id: nhanvien_giao_id, ngaygiao: ngaygiao, hdtb_id: hdtb_id }
        let result = await this.fn_hthsdoisothuebao_capnhat(input)
        if (result.returning == '1') {
          try {
            this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
            // let resultJson = JSON.parse(result.p_js_returnds)
            // this.ShowSuccess(resultJson.MESSAGE)
          } catch (err) {
            this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          }

          await this.HienThiDanhSach()
        } else this.ShowError(result.p_js_returnds)
        this.Loading = false
      } catch (ex) {
        this.ShowError(`${ex.message}`)
        this.Loading = false
      } finally {
        this.Loading = false
      }
      // await this.HienThiDanhSach()
    },
    tsbtnChapNhan_Click_Old: async function () {
      try {
        this.Loading = true
        await this.CapNhat()
        this.Loading = false
      } catch (ex) {
        this.ShowError(`${ex.message}`)
        this.Loading = false
      } finally {
        this.Loading = false
      }
      // await this.HienThiDanhSach()
    },
    gridThueBao_DataBound: function (args) {
      if (this.selIndex.length > 0) {
        this.$refs.gridDsThueBao.grid.selectRows(this.selIndex)
        this.selIndex = []
      }
    },
    gridThueBao_RowDataBound: async function (args) {
      if (args.data['ma_tb_moi'] == this.txtma_tb_moi_s1) {
        this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
        await this.select_selectThueBao(this.selIndex, args.data)
      }
      args.row.classList.add('below-30')
    },
    CapNhat: async function () {
      try {
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let vnhanvien_giao_id = 0
        let vngaygiao = ''
        vnhanvien_giao_id = this.nhanVienGVId
        vngaygiao = this.dtpNgayBG.val

        if (!this.KT_DuLieuNhap()) return
        if (!this.PHAILAM('GIAOVIEC')) {
          // objgiaophieu.Delete(phieu_id);
          await this.delete_giaophieu_nv(this.PhieuId)
          let data = await this.TaoDuLieu_NVTH()
          // objgiaophieu.Insert(dsnhanviengp);
          await this.insert_giaophieu_nv(data)
        }
        // if (dichvuvt_id == DichVuVienThong.CO_DINH)--minhnt sửa 31032015: khi chọn ne thì cập nhật trạng thái luôn
        // {
        //    if (PHAILAM("CAPNHAT_NE"))
        //        lapHD.CAPNHAT_SONE(hdtb_id, txtma_tb_moi.Text.Trim(), ne_id, txtNe.Text.Trim(), nap_id, tramtb_id); //Phải truyền tramtb_id vào đây
        // }
        // bangts.Update_tt_giaoviec((int)tt_nd.nhanvien_id, vngaygiao, hdtb_id, phieu_id, Convert.ToInt64(cboNguoiGV.SelectedValue));
        await this.update_tt_giaoviec()
        this.ShowSuccess('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
      } catch (ex) {
        this.ShowError('Có lỗi : ' + ex.message)
      }
    },
    TaoDuLieu_NVTH: async function () {
      var arr = []
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var data = {}
      data.PHIEU_ID = this.PhieuId
      data.NHANVIEN_TH_ID = nhanvienId
      data.SO_DT = ''
      if (this.HuongGiaoId != 0) {
        let huonggiao = await this.MapHuongGiaoId(this.HuongGiaoId)
        console.log('huonggiao', data)
        data.NHIEMVU = huonggiao
      } else {
        data.NHIEMVU = 'Hoàn thiện hồ sơ'
      }
      data.GHICHU = ''
      data.NHANVIEN_GIAO_ID = nhanvienId
      data.NGAYGIAO = this.dtpNgayGV.val
      arr.push(data)
      return arr
    },
    KT_DuLieuNhap: function () {
      if (this.DsThueBao.data.length <= 0) {
        this.ShowAlert('Không có dữ liệu để cập nhật !')
        return false
      }
      if (this.dtpNgayGV_checkbox == false) {
        this.ShowAlert('Hãy chọn ngày giao việc !')
        this.$refs.dtpNgayGV.focus()
        return false
      }
      if (this.PHAILAM('GIAOVIEC')) {
        if (this.DsNhanVienThucHien.data.length <= 0) {
          this.ShowAlert('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      if (!this.dtpNgayBG_checkbox) {
        this.ShowAlert('Hãy nhập ngày hoàn thành!')
        this.$refs.txtngayHT.focus()
        // chkNgayBG.Focus();
        return false
      }
      if (this.dtpNgayBG_checkbox && (this.dtpNgayBG.val == null || this.dtpNgayBG.val == '')) {
        this.ShowAlert('Hãy nhập ngày hoàn thành!')
        this.$refs.txtngayHT.focus()
        // chkNgayBG.Focus();
        return false
      }
      let dtpNgayGVInt = parseInt(moment(this.dtpNgayGV.val, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss'))
      let dtpNgayBGInt = parseInt(moment(this.dtpNgayBG.val, 'DD/MM/YYYY HH:mm:ss').format('YYYYMMDDHHmmss'))
      if (dtpNgayGVInt > dtpNgayBGInt) {
        this.ShowAlert('Ngày giao việc không được lớn hơn ngày hoàn thiện ')
        return false
      }
      return true
    },
    txtma_tb_moi_KeyUp: async function () {
      try {
        this.Loading = true
        await this.lay_ds_hopdong_huonggiao(0, this.txtma_tb_moi)
        this.Loading = false
      } catch (ex) {
        console.log('txtma_tb_moi_KeyUp-error', ex)
        this.Loading = false
      } finally {
        this.Loading = false
        this.SoNE_checkboxAuto = true
        this.SoNE_checkboxManual = false
      }
    },
    btnChonNe_TuDong_Click_New: async function () {
      try {
        // if (!this.SoNE_checkboxAuto) return
        this.Loading = true
        let data = await this.lay_ds_hdtb_dv(this.HdThueBaoId, 5) // LOAI_DV.TRAM_VT
        if (data != null && data.length > 0) {
          this.tramvt_id = data[0].donvi_id
        }
        let hdtb_id = this.HdThueBaoId
        let tramvt_id = this.tramvt_id
        let dichvu_id = this.DichVuVtId
        let capnhat_ne = this.PHAILAM('CAPNHAT_NE') ? 1 : 0
        let ma_tb = this.txtma_tb_moi
        let response = await this.fn_hthsdoisothuebao_chonne_tudong(hdtb_id, tramvt_id, dichvu_id, capnhat_ne, ma_tb)
        if (response.data.error === 200 || response.data.error === '200') {
          if (response.data.data.returning == '1') {
            let result = JSON.parse(response.data.data.p_js_returnds)
            let ne = result.DS_THONGTIN_NE
            if (result.DS_THONGTIN_NE != null && result.DS_THONGTIN_NE.length > 0) {
              this.txtSoNE = ne[0].sone
              this.ne_id = ne[0].ne_id
              this.tramtb_id = ne[0].vetinh_id
            }
          } else {
            let result = JSON.parse(response.data.data.p_js_returnds)
            this.ShowError(`${result.ERROR_CODE}-${result.MESSAGE}`)
          }
          console.log(response.data.data)
        } else {
          this.ShowError(`Không tìm thấy thông tin Ne!`)
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
        this.SoNE_checkboxAuto = true
        this.SoNE_checkboxManual = false
      }
    },
    btnChonNe_TuDong_Click: async function () {
      try {
        if (this.HdThueBaoId == null || this.HdThueBaoId == 0) return
        let data = await this.lay_ds_hdtb_dv(this.HdThueBaoId, 5) // LOAI_DV.TRAM_VT
        if (data != null && data.length > 0) {
          this.tramvt_id = data[0].donvi_id
        }
        let ne = await this.lay_thongtin_ne_theo_tramvt(this.tramvt_id)
        if (ne != null && ne.length > 0) {
          this.txtSoNE = ne[0].sone
          this.ne_id = ne[0].ne_id
          this.tramtb_id = ne[0].vetinh_id
          let s = await this.MapNeId(this.ne_id)
          if (s != -1) {
            this.txtTramNE = s
          }
          if (this.DichVuVtId == DichVuVienThong.CO_DINH) {
            if (this.PHAILAM('CAPNHAT_NE')) {
              await this.capnhat_sone()
              // lapHD.CAPNHAT_SONE(hdtb_id, txtma_tb_moi.Text.Trim(), ne_id, txtNe.Text.Trim(), 0, tramtb_id) //Phải truyền tramtb_id vào đây
            }
          }
        } else {
          this.ShowError('Không tìm thấy thông tin Ne!')
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.SoNE_checkboxAuto = true
        this.SoNE_checkboxManual = false
      }
    },
    btnChonNe_Click: async function () {
      try {
        this.btnChonNe_Enabled = false
        this.optionsVetinh = await this.ht_donvi_ne(this.tramvt_id)
        this.$refs.dialogObjChonNe.show()
      } catch (err) {
      } finally {
        this.SoNE_checkboxAuto = false
        this.SoNE_checkboxManual = true
      }
    },
    ht_donvi_ne: async function (donvi_id) {
      var input = { donvi_id: donvi_id, loaidv_id: 25 /* fixed */ }
      let data = this.GetData(await api.ht_donvi_ne(this.axios, input))
      return data
    },
    capnhat_ngayht: async function (hdtb_id, ngay_ht) {
      var input = {
        hdtb_id: hdtb_id,
        ngay_ht: moment(ngay_ht).format('DD/MM/YYYY HH:mm:ss')
      }
      let response = await api.capnhat_ngayht(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    fn_capnhat_ngayht: async function (hdtb_id, ngay_ht) {
      var input = {
        hdtb_id: hdtb_id,
        ngay_ht: moment(ngay_ht).format('DD/MM/YYYY HH:mm:ss')
      }
      let response = await api.fn_capnhat_ngayht(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    capnhat_tt_phieu_hc: async function (vhdtb_id, vttph_id) {
      var input = {
        vttph_id: vttph_id,
        vhdtb_id: vhdtb_id
      }
      let response = await api.capnhat_tt_phieu_hc(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    hoanthanh_hoancong: async function (vhdtb_id, vphieu_id, vttph_id) {
      var input = {
        vhdtb_id: vhdtb_id,
        vphieu_id: vphieu_id,
        vtthd_id: vttph_id,
        vnhanvien_id: 4234
      }
      let response = await api.capnhat_tt_phieu_hc(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    giaophieu_td_khi_hc: async function (vhdtb_id, vphieu_id, vhuonggiao_id, vdonvi_giao_id, vdonvi_nhan_id, vnhanvien_id) {
      var input = {
        vhdtb_id: vhdtb_id,
        vphieu_id: vphieu_id,
        vhuonggiao_id: vhuonggiao_id,
        vdonvi_giao_id: vdonvi_giao_id,
        vdonvi_nhan_id: vdonvi_nhan_id,
        vnhanvien_id: vnhanvien_id
      }
      let response = await api.capnhat_tt_phieu_hc(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    getDsVetinh: async function (donvi_id) {
      var input = { donvi_id: donvi_id, loaidv_id: 25 }
      let data = this.GetData(await api.getDsVetinh(this.axios, input))
      return data
    },
    lay_donvi_giaophieu: async function (hdtb_id, huonggiao_id) {
      var input = {
        vhdtb_id: hdtb_id,
        vhuonggiao_id: huonggiao_id
      }
      let data = this.GetData(await api.lay_donvi_giaophieu(this.axios, input))
      return data
    },
    fn_kiemtra_phieu_dagiao: async function (hdtb_id, huonggiao_id) {
      var input = {
        hdtb_id: hdtb_id,
        huonggiao_id: huonggiao_id
      }
      let response = await api.fn_kiemtra_phieu_dagiao(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        return true
      } else {
        return false
      }
    },
    lay_ds_phieuvt_hdbh: async function (phieu_id, hdtb_id) {
      let input = {
        baohong_id: 0,
        hdtb_id: hdtb_id,
        kieutbi: 0,
        phieu_id: phieu_id,
        status: 0
      }
      let data = this.GetData(await api.lay_ds_phieuvt_hdbh(this.axios, input))
      return data
    },
    Clear: function () {
      this.HdThueBaoId = 0
      this.txtMaGD = ''
      this.txtKieuLD = ''
      this.txtma_tb_moi = ''
      this.txtMaTBcu = ''
      this.txtTramNE = ''
      this.txtSoNECu = ''
      this.txtHostLD = ''
      this.txtHostVatLy = ''
      this.txtNoiDungGiao = ''
      this.txtTenThueBao = ''
      this.txtDiaChiTB = ''
      this.txtGhiChu = ''
      this.nhanVienGVId = this.$root.token.getNhanVienID()
      this.DsNhanVienThucHien.data = []
      this.DsThueBao.data = []
      this.tramvt_id = 0
      this.ne_id = 0
      this.nap_id = 0
    },
    ChangeNe: async function (data) {
      try {
        console.log('ChangeNe', data)
        this.$refs.dialogObjChonNe.hide()
        this.btnChonNe_Enabled = true
        if (this.ne_id != 0) {
          this.tramtb_id = data.tramtb_id
          this.ne_id = data.ne_id
          this.txtSoNE = data.sone
        } else {
          this.tramtb_id = data.tramtb_id
          this.nap_id = 0
          this.txtSoNE = ''
        }
        if (this.DichVuVtId == DichVuVienThong.CO_DINH) {
          if (this.PHAILAM('CAPNHAT_NE')) {
            // lapHD.CAPNHAT_SONE(hdtb_id, txtma_tb_moi.Text.Trim(), ne_id, txtNe.Text.Trim(), nap_id, tramtb_id) //Phải truyền tramtb_id vào đây
            await this.capnhat_sone()
          }
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    openDialogChonNe: function () {
      this.SoNE_checkboxManual = true
      this.SoNE_checkboxAuto = false
      this.btnChonNe_Enabled = false
    },
    closeDialogChonNe: function () {
      this.btnChonNe_Enabled = true
    },
    async InPhieu() {
      try {
        let vhdkhid = await this.MapHdkhId(this.HdThueBaoId)
        // alert(vhdkhid)
        if (vhdkhid == '-1') {
          this.$root.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        if (this.DsThueBao.selectedItems.length == 0) {
          this.$root.$toast.error('Hãy chọn phiếu!')
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = this.DsThueBao.selectedItems.map((item) => item.hdkh_id)
        nhdtbid = this.DsThueBao.selectedItems.map((item) => item.hdtb_id)
        nphieuid = this.DsThueBao.selectedItems.map((item) => item.phieu_id)
        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.ma_gd = this.txtMaGD
        this.modelIn.cv_thuchien = this.congviec_th
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        this.modelIn.nvth_id = this.nhanvien_tc_id
        this.modelIn.huonggiao_id = this.HuongGiaoId
        this.modelIn.tentram_tb = this.txtTramNE
        console.log('InPhieu', this.modelIn)
        this.$refs.popupInBB.showModal()
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },
    tsbtnGiaoViec_Click: async function () {
      if (this.DsThueBao.data.length <= 0) {
        this.ShowError('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
        return
      }
      if (this.nhanVienGVId == '') {
        this.ShowError('Hãy nhập nhân viên giao việc!')
        return
      }
      if (!this.dtpNgayGV_checkbox) {
        this.ShowError('Hãy nhập ngày giao việc !')
        this.$refs.dtpNgayGV.$el.focus()
        return
      }
      // Tham chieu sang form frnGiaoPhieuNV
      this.giaoPhieuNV.PhieuId = this.PhieuId
      this.$refs.dialogObjGiaoPhieuNV.show()
      // this.ShowSuccess(`Tham chieu sang form frnGiaoPhieuNV`)
    },
    async KT_DBThang_DaChot(thangsau) {
      let kq = ''
      // let kt_taobang = KT_TaoDB_Thang(thangsau);
      let kt_taobang = await this.kt_taodb_thang(thangsau)
      if (kt_taobang != 0) {
        kq = 'Danh bạ tháng đã được chốt, bạn không thể sửa ngày hoàn thành. Liên hệ Admin để xử lý !'
      } else {
        kq = '0'
      }
      return kq
    },
    btnKichhoat_Click: async function () {
      try {
        if (this.DsThueBao.data == null || this.DsThueBao.data.length == 0) {
          this.ShowError('Bạn chưa chọn số máy/ thuê bao thực hiện !')
          return
        }
        if (this.selectedThueBao != null && this.selectedThueBao.ngay_ht_gp == '') {
          this.ShowError('Bạn phải cập nhật ngày hoàn thành trước khi kích hoạt !')
          return
        }
        if (this.DsThueBao.data.length == 0) {
          this.ShowError('Chưa có thuê bao để kích hoạt ')
          return
        }
        if (this.DsNhanVienThucHien.data.length <= 0) {
          this.ShowError('Hãy nhập nhân viên thực hiện  !')
          return
        }
        if (this.DichVuVtId == DichVuVienThong.ADSL) {
          this.$bvModal
            .msgBoxConfirm(`Bạn có chắc chắn kích hoạt Account ${this.txtma_tb_moi} lên Visa hay không ?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              let result = await this.fn_hthsdoisothuebao_kichhoat()
              if (result.returning == '1') {
                this.ShowSuccess('Kích hoạt đổi Account trên Visa thành công!')
                await this.HienThiDanhSach()
              } else this.ShowError(result.p_js_returnds)
            })
        }
        //
        if (this.DichVuVtId == DichVuVienThong.CO_DINH) {
          this.$bvModal
            .msgBoxConfirm(`Bạn có chắc chắn kích hoạt Số máy ${this.txtma_tb_moi} hay không ?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              let result = await this.fn_hthsdoisothuebao_kichhoat()
              if (result.returning == '1') {
                this.ShowSuccess('Kích hoạt tổng đài thành công!')
                await this.HienThiDanhSach()
              } else this.ShowError(result.p_js_returnds)
            })
        }
        //
        if (this.DichVuVtId == DichVuVienThong.IMS) {
          this.$bvModal
            .msgBoxConfirm(`Bạn có chắc chắn kích hoạt đổi số : ${this.txtma_tb_moi}  lên VTN hay không? ?`, {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              let result = await this.fn_hthsdoisothuebao_kichhoat()
              if (result.returning == '1') {
                this.ShowSuccess('Kích hoạt đổi số thành công!')
                await this.HienThiDanhSach()
              } else this.ShowError(result.p_js_returnds)
              //
            })
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    btnKichhoat_Click_Old: async function () {
      if (this.selectedThueBao.ngay_ht_gp == '') {
        this.ShowError('Bạn phải cập nhật ngày hoàn thành trước khi kích hoạt !')
        return
      }
      if (this.DsThueBao.data.length == 0) {
        this.ShowError('Chưa có thuê bao để kích hoạt ')
        return
      }
      if (this.DsNhanVienThucHien.data.length <= 0) {
        this.ShowError('Hãy nhập nhân viên thực hiện  !')
        return
      }
      let loaitb_id = this.selectedThueBao.loaitb_id
      let isTest = true
      if (this.DichVuVtId == DichVuVienThong.ADSL) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có chắc chắn kích hoạt Account ${this.txtma_tb_moi} lên Visa hay không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }

            if (loaitb_id == LoaiHinhTB.INTERNET_FTTH || loaitb_id == LoaiHinhTB.INTERNET_ADSL || loaitb_id == LoaiHinhTB.WIFI_FIBER) {
              if (!isTest) {
                let thuebaoId = await this.MapThueBaoId(this.HdThueBaoId)
                let data = await this.kiemtra_tb_sd_dvgt(thuebaoId)
                if (data.length > 0) {
                  let thanhtoan_id = this.MapThanhToanId(thuebaoId)
                  let donvitt_id = this.MapThanhToanId(thuebaoId)
                  // Huy dich vu greenline
                  // Tương tác với WS , API
                  // API Xu ly
                  //
                  if (this.PHAILAM('CAPNHAT_NGAYHT_KH')) {
                    let date = new Date()
                    let nextMonth = moment(date).add(1, 'M')
                    let thangsau = moment(nextMonth).format('YYYYMM')
                    let dbt = await this.kt_taodb_thang(thangsau)
                    if (dbt != '0') {
                      this.ShowSuccess(dbt)
                      return
                    }
                    // lapHD.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn)
                    await this.capnhat_ngayht(this.HdThueBaoId, date)
                  }
                  //
                  let result = await this.capnhat_ngay_kichhoat()
                  this.ShowSuccess('Kích hoạt số máy ' + this.txtma_tb_moi + ' lên VTN thành công!')
                  await this.HienThiDanhSach()
                }
              } //
              else {
                if (this.PHAILAM('CAPNHAT_NGAYHT_KH')) {
                  let date = new Date()
                  let nextMonth = moment(date).add(1, 'M')
                  let thangsau = moment(nextMonth).format('YYYYMM')
                  let dbt = await this.kt_taodb_thang(thangsau)
                  if (dbt != '0') {
                    this.ShowSuccess(dbt)
                    return
                  }
                  // lapHD.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn)
                  await this.capnhat_ngayht(this.HdThueBaoId, date)
                }
                //
                let result = await this.capnhat_ngay_kichhoat()
                this.ShowSuccess('Kích hoạt số máy ' + this.txtma_tb_moi + ' lên VTN thành công!')
                await this.HienThiDanhSach()
              }
            }
          })
      }
      //
      if (this.DichVuVtId == DichVuVienThong.CO_DINH) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có chắc chắn kích hoạt Số máy ${this.txtma_tb_moi} hay không ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            let result = true
            let message = ''
            let serviceLoss = true //true: mất dịch vụ; false: không mất dịchvụ
            // Cap nhat trang thai NE
            result = await this.capnhat_trangthai_ne(this.ne_id, 3) // TrangthaiSo.TTSO_DACAP
            let dskt = await this.lay_ne_cu_ne_moi(this.HdThueBaoId)
            if (dskt != null && dskt.length > 0) {
              let ne_id_moi = dskt[0].ne_id_moi
              let ne_id_cu = dskt[0].ne_id_cu
              let ne_cu = dskt[0].ne_cu
              let ne_moi = dskt[0].ne_moi
              if (ne_id_moi != ne_id_cu) {
                let kt = await this.fn_kt_thuebao_sd_ne(ne_id_cu, this.txtMaTBcu)
                if (kt == false) {
                  let tt = TrangthaiSo.TTSO_DANGROI
                  let c2 = await this.capnhat_trangthai_ne(this.ne_id, 1) // TrangthaiSo.TTSO_DANGROI
                }
              }
              if (this.PHAILAM('CAPNHAT_NGAYHT_KH')) {
                let date = new Date()
                let nextMonth = moment(date).add(1, 'M')
                let thangsau = moment(nextMonth).format('YYYYMM')
                let dbt = await this.KT_DBThang_DaChot(thangsau)
                if (dbt != '0') {
                  this.ShowAlert(dbt)
                  return
                }
                // lapHD.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn)
                await this.capnhat_ngayht(this.HdThueBaoId, date)
              }
              let result = await this.capnhat_ngay_kichhoat()
              this.ShowSuccess('Kích hoạt tổng đài thành công!')
              await this.HienThiDanhSach()
            }
          })
      }
      //
      if (this.DichVuVtId == DichVuVienThong.IMS) {
        this.$bvModal
          .msgBoxConfirm(`Bạn có chắc chắn kích hoạt đổi số : ${this.txtma_tb_moi}  lên VTN hay không? ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            //
            if (!isTest) {
              let bDongBo = false
              let rsMsg = ''
              let so_cu = this.selectedThueBao['ma_tb_cu']
              let loaitb_id_vtn = ''
              if (loaitb_id == LoaiHinhTB.IMS_SIP || loaitb_id == LoaiHinhTB.IMS_POTS) {
                // loaitb_id_vtn = checkdata.MAP_ID("MA_LHTB", DatabaseConstants.DB2 + ".loaihinh_tb", "where LOAITB_ID = '" + loaitb_id + "'");
                loaitb_id_vtn = await this.Map_MA_LHTB(loaitb_id)
                // SIP/POST thì gọi service mới
                // bDongBo = imsServ.DoiSo(so_cu, txtma_tb_moi.Text.Trim(), txtMatKhau.Text, loaitb_id_vtn, "FixOfflineCharging", ref rsMsg);
                // Call lên API VTN
              } else {
                // loaitb_id_vtn = checkdata.MAP_ID("MA_LHTB_VDC", DatabaseConstants.DB2 + ".loaihinh_tb", "where LOAITB_ID = '" + loaitb_id + "'");
                loaitb_id_vtn = await this.Map_MA_LHTB_VDC(loaitb_id)
                // bDongBo = vtnServ.DoiSo(hdtb_id, thuebao_id, so_cu, loaitb_id_vtn);
              }
              if (bDongBo) {
                if (this.PHAILAM('CAPNHAT_NGAYHT_KH')) {
                  let date = new Date()
                  let nextMonth = moment(date).add(1, 'M')
                  let thangsau = moment(nextMonth).format('YYYYMM')
                  let dbt = await this.KT_DBThang_DaChot(thangsau)
                  if (dbt != '0') {
                    // this.ShowAlert.ShowTB(dbt);
                    return
                  }
                  await this.capnhat_ngayht(this.HdThueBaoId, date)
                  // let dbt = bangts.KT_DBThang_DaChot(thangsau)
                  // lapHD.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn);
                  let result = await this.capnhat_ngay_kichhoat()
                  this.ShowSuccess('Kích hoạt số máy! ' + this.txtma_tb_moi + ' lên VTN thành công!')
                  await this.HienThiDanhSach()
                }
              }
            } else {
              if (this.PHAILAM('CAPNHAT_NGAYHT_KH')) {
                let date = new Date()
                let nextMonth = moment(date).add(1, 'M')
                let thangsau = moment(nextMonth).format('YYYYMM')
                let dbt = await this.KT_DBThang_DaChot(thangsau)
                if (dbt != '0') {
                  // this.ShowAlert.ShowTB(dbt);
                  return
                }
                // lapHD.CAPNHAT_NGAYHT(hdtb_id, tt_nd.ngay_cn)
                await this.capnhat_ngayht(this.HdThueBaoId, date)
                let result = await this.capnhat_ngay_kichhoat()
                this.ShowSuccess('Kích hoạt đổi số thành công!')
                await this.HienThiDanhSach()
              }
            }
            //
          })
      }
    },
    btnHoanCong_Click: async function () {
      try {
        if (this.DsThueBao.data == null || this.DsThueBao.data.length == 0) {
          this.ShowError('Bạn chưa chọn số máy/ thuê bao thực hiện !')
          return
        }
        if (this.selectedThueBao.ngay_ht_gp == '') {
          this.ShowError('Bạn phải cập nhật ngày hoàn thành trước khi hoàn công !')
          return
        }
        if (this.dtpNgayGV_checkbox == false && this.DsNhanVienThucHien.data.length <= 0) {
          this.ShowError('Bạn phải cập nhật nhân viên thực hiện !')
          return
        }
        this.Loading = true
        let result = await this.fn_hthsdoisothuebao_hoancong()
        console.log('fn_hthsdoisothuebao_hoancong-result', result)
        if (result == null) {
          this.ShowError(`Có lỗi khi hoàn công !`)
          return
        }
        if (result.returning == '1') {
          this.ShowSuccess('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
          await this.HienThiDanhSach()
        } else {
          this.ShowError(result.p_js_returnds == null ? ' Có lỗi khi thực hiện hoàn công, Liên hệ admin xử lý !' : result.p_js_returnds)
        }
      } catch (err) {
        console.log(err)
      } finally {
        this.Loading = false
      }
    },
    btnHoanCong_Click_Old: async function () {
      console.log('btnHoanCong_Click')
      if (this.selectedThueBao.ngay_ht_gp == '') {
        this.ShowAlert('Bạn phải cập nhật ngày hoàn thành trước khi hoàn công !')
        return
      }
      if (this.dtpNgayGV_checkbox == false && this.DsNhanVienThucHien.data.length <= 0) {
        this.ShowAlert('Bạn phải cập nhật nhân viên thực hiện !')
        return
      }
      let ngay_sys = ''
      let ngay_tt = ''
      let ngay_ht = ''
      //
      ngay_sys = moment(new Date()).format('DD/MM/YYYY')
      ngay_tt = this.selectedThueBao.thangnam_tt
      ngay_ht = this.dtpNgayBG.val.substring(0, 10)
      let ngaybg = moment(this.dtpNgayBG.val, this.dateFormat)
      let nextMonth = moment(ngaybg).add(1, 'M')
      //

      //
      if (this.PHAILAM('TUDONG_HTHS')) {
        let kt_taobang = 0
        kt_taobang = await this.kt_taodb_thang(moment(ngaybg).format('YYYYMM'))
        if (kt_taobang == 0) {
          this.ShowError('Danh bạ tháng chưa được chốt. Liên hệ Admin để xử lý !')
          return
        }

        let thangsau = moment(nextMonth).format('YYYYMM')
        kt_taobang = await this.kt_taodb_thang(thangsau)
        if (kt_taobang != 0) {
          this.ShowError('Danh bạ tháng ' + moment(ngaybg).format('MM/YYYY') + ' đã được chốt. Liên hệ Admin để xử lý !')
          return
        }
      }
      //
      let hg_phieu_id = 0
      let donvi_nhan_id = 0
      let tthd_dich_id = 0
      let dsphieu = this.lay_donvi_giaophieu(this.HdThueBaoId, this.HuongGiaoId)
      if (dsphieu != null && dsphieu.length > 0) {
        hg_phieu_id = dsphieu[0]['huonggiao_id']
        donvi_nhan_id = dsphieu[0]['donvi_id']
        tthd_dich_id = dsphieu[0]['tthd_id']
      } else {
        this.ShowError('Chưa thiết lập đơn vị nhận phiếu. Liên hệ admin để xử lý !')
        return
      }
      if (!(await this.fn_kiemtra_phieu_dagiao(this.HdThueBaoId, hg_phieu_id))) {
        this.ShowAlert('Thuê bao ' + this.txtma_tb_moi + ' đã được giao đi. Bạn hãy kiểm tra lại')
        await this.HienThiDanhSach()
        return
      }
      //
      if (this.PHAILAM('CAPNHAT_NGAYHT_HC')) {
        let date = new Date()
        let thangsau = moment(date).add(1, 'M')
        let dbt = await this.kt_taodb_thang(moment(thangsau).format('YYYYMM'))
        if (dbt != '0') {
          this.ShowAlert(dbt)
          return
        }
        await this.capnhat_ngayht(this.HdThueBaoId, date)
      }
      //
      if (this.PHAILAM('CAPNHAT_NGAYKH_HC')) {
        let date = new Date()
        await this.fn_capnhat_ngayht(this.HdThueBaoId, date)
      }
      await this.capnhat_tt_phieu_hc(this.HdThueBaoId, 2)
      let nhanvienId = this.$root.token.getNhanVienID()
      await this.hoanthanh_hoancong(this.HdThueBaoId, this.PhieuId, tthd_dich_id, nhanvienId)
      let str = 'Hoàn công thành công !'
      let hdtb_id = this.HdThueBaoId
      let phieu_id = this.PhieuId
      let donvi_id = this.$root.token.getDonViID()
      let username = this.$root.token.getUserName()
      if (this.PHAILAM('TUDONG_HTHS')) {
        await this.giaophieu_td_khi_hc(hdtb_id, phieu_id, hg_phieu_id, donvi_id, donvi_id, nhanvienId, username, 'web')
        await this.hoanthien_hs_thaydoi_matb(hdtb_id)
        await this.capnhat_tt_phieu_gphone(hdtb_id)
        str = 'Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !'
      } else {
        await this.giaophieu_td_khi_hc(hdtb_id, phieu_id, hg_phieu_id, donvi_id, donvi_id, nhanvienId, username, 'web')
      }
      let dongbo_dadv = -1
      let dsThamSo = await this.lay_ds_thamso_md()
      if (dsThamSo != null && dsThamSo.filter((x) => x.ma_ts == mathamso).length > 0) {
        dongbo_dadv = dsThamSo.filter((x) => x.ma_ts == mathamso)[0].ten_ts
      }
      if (dongbo_dadv == 1) {
        let vthuebao_id = await this.MapThueBaoId(this.HdThueBaoId)
        let _dtGoiDD = await this.lay_matb_neo_id_theo_hdtb(vthuebao_id)
        if (_dtGoiDD != null && _dtGoiDD.length > 0) {
          let _dtTB = await this.get_db_thuebao_by_id(vthuebao_id)
          let vso_gt = ''
          let vma_tb = ''
          let vdiachi_tb = ''
          if (_dtTB != null && _dtTB.length > 0) {
            vso_gt = _dtTB[0]['so_gt']
            vma_tb = _dtTB[0]['ma_tb']
            vdiachi_tb = _dtTB[0]['diachi_tb']
          }
          /* ( Gọi đi đâu , đi đâu )
           WS_GOITICHHOP_VTN _ws = new WS_GOITICHHOP_VTN();
                        string _error = "";
                        string _result = _ws.DOI_ACC_CCBS(tt_nd.tentat_ccbs, _dtGoiDD.Rows[0]["goi_neo_id"], "84" + _dtGoiDD.Rows[0]["ma_tb"].ToString(), vma_tb, vdiachi_tb, vso_gt, "Đổi account fiber", tt_nd.ma_nd, ref _error);
                        if (_result != "0")
                        {
                            Message_Box.ShowError("có lỗi trong quá trình kích hoạt gói tích hợp trên vnp: " + _result);
                            return;
                        }
          */
        }
      }
      this.ShowSuccess(str)
      await this.HienThiDanhSach()
    },
    btnLayMaGD_Click: async function () {
      // this.$refs.dialogObjTraCuuHD.show()
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract(item) {
      this.txtMaGD = item.ma_gd
      if (this.txtMaGD && this.txtMaGD.length > 1) {
        this.txtMaGD_KeyUp()
      }
    },

    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
    handleRangeClose() {
      this.showTimeRangePanel = false
    },
    grid_selectedItemsChanged(items) {
      this.DsThueBao.selectedItems = items
    },
    btnInDS_Click: async function () {
      try {
        this.$refs.txtMaTBcu01.focus()
        let temp = ''
        let dem = this.DsThueBao.selectedItems == null ? 0 : this.DsThueBao.selectedItems.length
        if (dem == 0) {
          this.ShowError('Hãy chọn phiếu để In')
          return
        }
        temp = this.DsThueBao.selectedItems.map((x) => x.hdtb_id).join()
        //ProcessReport pr = new ProcessReport("CSS\\BienBanNT\\IN_DS_DOISO_DT_DAIVT.rpt");
        //pr.setParameter("hdtb_id", temp);
        //pr.ViewRepot();
      } catch (err) {
        //
      } finally {
      }
    },
    btnLayTTMoi_Click: async function () {
      try {
        this.Loading = true
        this.$refs.gridDsThueBao.clearFilterConditions()
        await this.HienThiDanhSach()
        this.Loading = false
      } catch (err) {
        this.ShowError(`${err.message}`)
        this.Loading = false
      } finally {
        this.Loading = false
      }
    },
    tsbtnChuyenTo_Click: async function () {
      this.$refs.popupChuyenTo.openDialog(this.PhieuId, this.HdThueBaoId)
      return
      try {
        if (this.DsThueBao.data == null || this.DsThueBao.data.length == 0) {
          this.ShowError(`Bạn chưa chọn thuê bao để chuyển !`)
          return
        }
        if (this.PhieuId == 0 && this.HdThueBaoId == 0) {
          this.ShowError(`Bạn chưa chọn thuê bao để chuyển !`)
          return
        }
        if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
          let bh = await this.lay_ds_phieuvt_hdbh(this.PhieuId, this.HdThueBaoId)
          if (bh != null && bh.length > 0) {
            this.ShowError('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }
        //
        // this.ShowSuccess(`Chuyển sang UR chuyển tổ`)
        if (this.PhieuId > 0) {
          /*
           WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
                f.hdtb_id = hdtb_id;
                f.baohong_id = 0;
                f.phieu_id = phieu_id;
          */
          console.log('tsbtnChuyenTo_Click', 'Phiếu', this.PhieuId, 'HDTBID', this.HdThueBaoId)
          console.log('PhieuId', this.PhieuId, 'HDTBID', this.HdThueBaoId)
          this.$refs.popupChuyenTo.openDialog(this.PhieuId, this.HdThueBaoId, 0)
        } else {
          this.ShowAlert('Bạn chưa chọn thuê bao để chuyển !')
          return
        }

        //
        // WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
        // f.hdtb_id = hdtb_id;
        // f.baohong_id = 0;
        // f.phieu_id = phieu_id;
        // f.ShowDialog();
        // if (f.Chapnhan)
        //     HienThiDanhSach(dichvuvt_id);
      } catch (ex) {
        this.ShowError(ex)
      }
    },
    tsbtnGiaoPhieu_Click: async function () {
      if (this.txtMaGD.length > 0) {
        let huonggiao = 0
        let data = await this.lay_huonggiao_theo_luong_id(this.LuongId)
        if (data != null && data.length > 0) {
          huonggiao = data[0].huonggiao_id
        } else {
          this.ShowError(`Chưa có hướng giao được gán !`)
          return
        }
        let quytrinh = this.QuyTrinhId
        this.$router.push({ path: 'HandoverTicket', query: { vma_gd: this.txtMaGD, vhuonggiao_id: huonggiao, vquytrinh: quytrinh, vdichvuvt_id: this.DichVuVtId, vloai_hd: 14, vtrangthaiphieu: 1 } })
      }
    },
    async lay_ds_hopdong_huonggiao_theo_dv(vloaidv_id, vdonvi_tk_id) {
      let donviId = this.$root.token.getDonViID()
      let phanvungId = parseInt(this.$root.token.getPhanVungID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      //let input = { nhanvien_id: nhanvienId, ma_gd: '0', hdtb_id: 0, tthd_id: this.ThHdId, in_huonggiao_id: this.HuongGiaoId, dichvuvt_id: this.DichVuVtId, loai_id: this.LoaiPhieuId, loaidv_id: parseInt(vloaidv_id), donvi_tk_id: parseInt(vdonvi_tk_id) }
      let input = {
        in_dichvuvt_id: this.DichVuVtId,
        in_donvi_tk_id: parseInt(vdonvi_tk_id),
        in_huonggiao_id: this.HuongGiaoId,
        in_loai_id: this.LoaiPhieuId,
        in_loaidv_id: parseInt(vloaidv_id),
        in_loaihd_id: LoaiHopDong.DOISO_TB,
        in_ma_tb: '0',
        in_nhanvien_id: nhanvienId,
        in_phanvung_id: phanvungId,
        in_tthd_id: this.ThHdId
      }
      let data = this.GetData(await api.lay_ds_hopdong_huonggiao_theo_dv(this.axios, input))
      this.DsThueBao.data = this.toLowerArrayObject(data)
      return data
    },
    async fn_hthsdoisothuebao_load(dichvu_id) {
      let donviId = this.$root.token.getDonViID()
      let dataInput = { dichvu_id: dichvu_id, donvi_id: donviId }
      let input = { p_ds_para: JSON.stringify(dataInput) }
      let data = this.GetData(await api.fn_hthsdoisothuebao_load(this.axios, input))
      let result = JSON.parse(data.p_js_returnds)
      console.log('fn_hthsdoisothuebao_load', result)
      return data
    },
    async fn_hthsdoisothuebao_hienthidanhsach(dichvu_id, ma_gd) {
      this.DsThueBao.data = []
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let phieu_id = 0 //this.PhieuId
      let ma_tb = '0'
      if (dichvu_id == 0 && ma_gd.length > 3) {
        ma_tb = ma_gd
      }
      // let ma_gd='0' // this.txtMaGD
      let dataInput = { dichvu_id: dichvu_id, donvi_id: donviId, tthd_id: this.ThHdId, nhanvien_id: nhanvienId, ma_gd: ma_gd, ma_tb: ma_tb, phieu_id: phieu_id, loaihd_id: LoaiHopDong.DOISO_TB, huonggiao_id: this.HuongGiaoId, loai_id: this.LoaiPhieuId }
      let input = { p_ds_para: JSON.stringify(dataInput) }
      // let data = this.GetData(await api.fn_hthsdoisothuebao_hienthidanhsach(this.axios, input))
      let response = await api.fn_hthsdoisothuebao_hienthidanhsach(this.axios, input)
      if (response.data.error === 200 || response.data.error === '200') {
        if (response.data.error_code != 'BSS-00000000') {
          this.ShowError(response.data.message)
          return
        }
      } else {
        this.ShowError(response.data.message)
        console.log(response.data.error_code)
        return
      }
      let data = response.data.data
      if (data.returning == 1) {
        let result = JSON.parse(data.p_js_returnds)
        let lowerData = this.toLowerArrayObject(result.DS_PHIEU)
        console.log('lowerData:', lowerData)
        // convert lai ngay thang ( bo tay voi api , tra ve dinh dang ngay khac nhau )
        if (dichvu_id != 11) {
          if (lowerData != null && lowerData.length > 0) {
            lowerData.forEach(function (item) {
              if (item.ngay_ht_gp != null && item.ngay_ht_gp != '') {
                item.ngay_ht_gp = moment(item.ngay_ht_gp, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
              }
              if (item.ngay_ht != null && item.ngay_ht != '') {
                item.ngay_ht = moment(item.ngay_ht, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
              }
            })
          }
        }
        //
        this.DsThueBao.data = lowerData
      } else {
        this.ShowError(`Có lỗi lấy danh sách phiếu ${data.p_js_returnds}`)
      }
      console.log('fn_hthsdoisothuebao_hienthidanhsach', data)
      return data
    },
    async fn_hthsdoisothuebao_capnhat(data) {
      let input = { p_ds_para: JSON.stringify(data) }
      let result = this.GetData(await api.fn_hthsdoisothuebao_capnhat(this.axios, input))
      return result
    },
    async fn_hthsdoisothuebao_hoancong() {
      let tudong_hths = this.PHAILAM('TUDONG_HTHS') ? 1 : 0
      let kyhoadon = moment(this.dtpNgayBG.val, 'DD/MM/YYYY HH:mm:ss').format('YYYYMM') + '01' // string (yyyyMM01 [dtpNgayBG.Value])
      let kyhoadon_thangsau = moment(this.dtpNgayBG.val, 'DD/MM/YYYY HH:mm:ss').add(1, 'M').format('YYYYMM') + '01'
      let hgid = this.HuongGiaoId
      let capnhat_ngayht_hc = this.PHAILAM('CAPNHAT_NGAYHT_HC') ? 1 : 0
      let nhanvienId = this.$root.token.getNhanVienID()
      let donviId = this.$root.token.getDonViID()
      let username = this.$root.token.getUserName()
      let capnhat_ngaykh_hc = this.PHAILAM('CAPNHAT_NGAYKH_HC') ? 1 : 0
      let test = {
        tudong_hths: 1,
        kyhoadon: '20220501',
        kyhoadon_thangsau: '20220601',
        huonggiao_id: 308,
        hdtb_id: 5977906,
        ma_tb_moi: '3999658',
        capnhat_ngayht_hc: 0,
        ngay_cn: '17/05/2022',
        nhanvien_id: '13431',
        phieu_id: 11848653,
        donvi_id: '186',
        ma_nd: 'test_app.hpg',
        may_cn: 'web',
        capnhat_ngaykh_hc: 0
      }
      let data = {
        tudong_hths: tudong_hths,
        kyhoadon: kyhoadon,
        kyhoadon_thangsau: kyhoadon_thangsau,
        huonggiao_id: hgid,
        hdtb_id: this.HdThueBaoId,
        ma_tb_moi: this.txtma_tb_moi,
        capnhat_ngayht_hc: capnhat_ngayht_hc,
        ngay_cn: moment().format('DD/MM/YYYY'), //moment().toISOString(),
        nhanvien_id: nhanvienId,
        phieu_id: this.phieu_id,
        donvi_id: donviId,
        ma_nd: username,
        may_cn: 'web',
        capnhat_ngaykh_hc: capnhat_ngaykh_hc
      }
      let input = { p_ds_para: JSON.stringify(data) }
      // {"tudong_hths":1,"kyhoadon":"20220401","kyhoadon_thangsau":"20220501","huonggiao_id":306,"hdtb_id":2085283,"ma_tb_moi":"3588555","capnhat_ngayht_hc":1,"ngay_cn":"2022-05-09T01:39:15.740Z","nhanvien_id":"13431","donvi_id":"186","ma_nd":"test_app.hpg","may_cn":"web","capnhat_ngaykh_hc":1}
      let response = await api.fn_hthsdoisothuebao_hoancong(this.axios, input)
      // let result = this.GetData(await api.fn_hthsdoisothuebao_hoancong(this.axios, input))
      if (response.data.error == 200 || response.data.error == '200') {
        if (response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          this.ShowError(`${response.data.error_code}- ${response.data.message}`)
          console.log('fn_hthsdoisothuebao_hoancong-error:', response.data)
          return response.data
        }
      } else {
        this.ShowError(`${response.data.error_code}- ${response.data.message}`)
        console.log('fn_hthsdoisothuebao_hoancong-error:', response.data)
      }
      return null
    },
    async fn_hthsdoisothuebao_kichhoat() {
      let dichvu_id = this.DichVuVtId
      //let loaitb_id = this.LoaiHinhTBId
      let hdtb_id = this.HdThueBaoId
      let ma_tb_moi = this.txtma_tb_moi
      let date = new Date()
      let nextMonth = moment(date).add(1, 'M')
      let thangsau = moment(nextMonth).format('YYYYMM')
      let capnhat_ngayht_kh = this.PHAILAM('CAPNHAT_NGAYHT_KH') ? 1 : 0
      let ma_nd = this.$root.token.getUserName()
      let loaitb_id = this.selectedThueBao.loaitb_id
      let loaihd_id = this.selectedThueBao.loaihd_id
      let v_thuebao_id = this.selectedThueBao.thuebao_id
      let loaitb_id_vtn = ''
      let bDongBo = true
      let capnhat_ngaykh_hc = this.PHAILAM('CAPNHAT_NGAYKH_HC') ? 1 : 0
      if (loaitb_id == LoaiHinhTB.IMS_SIP || loaitb_id == LoaiHinhTB.IMS_POTS) {
        // loaitb_id_vtn = checkdata.MAP_ID("MA_LHTB", DatabaseConstants.DB2 + ".loaihinh_tb", "where LOAITB_ID = '" + loaitb_id + "'");
        loaitb_id_vtn = await this.Map_MA_LHTB(loaitb_id)
        // SIP/POST thì gọi service mới
        // bDongBo = imsServ.DoiSo(so_cu, txtma_tb_moi.Text.Trim(), txtMatKhau.Text, loaitb_id_vtn, "FixOfflineCharging", ref rsMsg);
        // Call lên API VTN
      } else {
        // loaitb_id_vtn = checkdata.MAP_ID("MA_LHTB_VDC", DatabaseConstants.DB2 + ".loaihinh_tb", "where LOAITB_ID = '" + loaitb_id + "'");
        loaitb_id_vtn = await this.Map_MA_LHTB_VDC(loaitb_id)
      }

      let data = {
        dichvu_id: dichvu_id,
        loaitb_id: loaitb_id,
        thuebao_id: v_thuebao_id,
        hdtb_id: hdtb_id,
        ma_tb_moi: ma_tb_moi,
        ma_tb_cu: this.txtMaTBcu,
        ne_id: this.ne_id,
        kyhoadon_thangsau: thangsau,
        capnhat_ngayht_kh: capnhat_ngayht_kh,
        ngay_cn: moment().format('DD/MM/YYYY'),
        ma_nd: ma_nd,
        ip_cn: '127.0.0.1',
        loaitb_id_vtn: loaitb_id_vtn,
        loaihd_id: loaihd_id,
        dongbo: bDongBo ? 1 : 0,
        modifymailrasname: 0,
        capnhat_ngaykh_hc: capnhat_ngaykh_hc
      }
      let input = { p_ds_para: JSON.stringify(data) }
      this.loading(true)
      let result = this.GetData(await api.fn_hthsdoisothuebao_kichhoat(this.axios, input))
      this.loading(false)
      return result
    },
    async fn_hthsdoisothuebao_chonne_tudong(hdtb_id, tramvt_id, dichvu_id, capnhat_ne, ma_tb) {
      let data = { hdtb_id: hdtb_id, tramvt_id: tramvt_id, dichvu_id: dichvu_id, capnhat_ne: capnhat_ne, ma_tb: ma_tb }
      let input = { p_ds_para: JSON.stringify(data) }
      return await api.fn_hthsdoisothuebao_chonne_tudong(this.axios, input)
    },
    async tsbtnChuyenDB_Click() {
      try {
        if (this.DsThueBao.data == null || this.DsThueBao.data.length == 0) {
          this.ShowError('Bạn chưa chọn số máy/ thuê bao thực hiện !')
          return
        }
        if (this.DsThueBao.selectedItems == null || this.DsThueBao.selectedItems.length == 0) {
          this.ShowError('Bạn chưa chọn số máy/ thuê bao thực hiện !')
          return
        }
        this.Loading = true
        let result = await this.fn_hthsdoisothuebao_chuyendb()
        if (result.returning == '1') {
          this.ShowSuccess('Chuyển dữ liệu vào danh bạ tỉnh thành công!')
          await this.HienThiDanhSach()
        } else this.ShowError(result.p_js_returnds)
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    async fn_hthsdoisothuebao_chuyendb() {
      let data = { hdtb_id: this.HdThueBaoId }
      let input = { p_ds_para: JSON.stringify(data) }
      return this.GetData(await api.fn_hthsdoisothuebao_chuyendb(this.axios, input))
    },
    async tsbtnDuAn_Click() {
      try {
        //  frmDuAn_HDTB f_duan = new frmDuAn_HDTB();
        //         f_duan.vhdtb_id = hdtb_id;
        //         f_duan.ShowDialog();
        this.$refs.refdialogObjDuAn.show()
      } catch (err) {
      } finally {
      }
    },
    async btnBienBanNT_Click() {
      try {
        this.kt_thaydoi = false
        await this.InPhieu()
        if (this.kt_thaydoi) {
          let s1 = txtma_tb_moi.Text.Trim()
          await this.HienThiDanhSach() //nhapt thêm 18/06/2015
        }
      } catch (err) {}
    },
    async tsbtnInPhieuTC_Click() {
      try {
        this.ShowAlert(`Chờ tích hợp`)
      } catch (err) {
      } finally {
        this.Loading = false
      }
    },
    async QuyTrinh_Channged(val) {
      try {
        console.log('QuyTrinh_Channged:', val)
        this.Loading = true
        let data = await this.lay_ds_ds_huonggiao(val)
        if (data != null && data.length > 0) {
          this.HuongGiaoId = data[0].huonggiao_id
          this.huonggiao_id = data[0].huonggiao_id
          this.TenHuongGiao = data[0].huonggiao
          this.LuongId = data[0].luong_id
          this.formTitle = this.Capitalize(this.TenHuongGiao)
        } else {
          this.ShowError('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          console.log('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          this.HuongGiaoId = -1
          this.huonggiao_id = -1
          this.DsThueBao.data = []
          await this.HienThiDanhSach()
          return
        }
        await this.HienThiDanhSach()
        await this.HienThiGiaoDien(this.LuongId)
        if (!this.PHAILAM('GIAOVIEC')) {
          this.dtpNgayGV_checkbox = true
          this.dtpNgayBG_checkbox = true
          this.dtpNgayGV.val = moment().format('DD/MM/YYYY HH:mm:ss')
          this.dtpNgayBG.val = moment().add(30, 'seconds').format('DD/MM/YYYY HH:mm:ss')
        }
      } catch (ex) {
      } finally {
        this.Loading = false
      }
    },
    async btnXuatExcel_Click() {
      try {
        if (this.DsThueBao.data != null && this.DsThueBao.data.length > 0) {
          if (this.DsThueBao.data.length > 0) {
            this.$refs.exportDataModal.showModal()
          } else {
            this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
          }
        } else {
          this.$toast.error('Không có dữ liệu!')
        }
      } catch (err) {}
    },
    toLowerObject(obj) {
      let key = ''
      let keys = Object.keys(obj)
      var n = keys.length
      var newobj = {}
      while (n--) {
        key = keys[n]
        newobj[key.toLowerCase()] = obj[key]
      }
      return newobj
    },
    toLowerArrayObject(arr) {
      console.log('toLowerArrayObject', arr)
      let newArr = []
      let s = this
      arr.forEach(function (item) {
        newArr.push(s.toLowerObject(item))
      })
      return newArr
    },
    ShowAlert: function (msg) {
      if (msg == null) return
      this.$toast.error(msg)
      // this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      if (msg == null) return
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      if (msg == null) return
      this.$toast.error(msg)
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
  }
}
</script>
