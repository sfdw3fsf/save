<template src="./CompleteProfileChangeTSMyTV.html"></template>
<style src="./CompleteProfileChangeTSMyTV.scss"></style>

<script>
import CompleteProfileChangeTSMyTVAPI from './CompleteProfileChangeTSMyTVAPI'
import breadcrumb from '@/components/breadcrumb'
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue";
// import SearchContractModal from '../../setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import moment from 'moment'
import {LoaiHopDong, TrangThaiHD} from '../../../../utils/Enum'
import {DICHVU_VIENTHONG} from '../../../../constants'
import {KieuLapDat} from '../../setup/InstallNewSubs/Enums'
import {LoaiHinhTB} from '../../../../const/enums'
import ChuyenTo from '../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo'
import tonPhieu from '../../setup/Payment/components/TonPhieu'
import {DialogPlugin} from '@syncfusion/ej2-vue-popups'
import TraPhieu from '../../setup/ReturnInstallTicket/components/traphieu'
import GiaoPhieuNhanVien from '../../setup/DeclareLandline/popup/giaophieunhanvien'
import XLSX from 'xlsx'
import CapVatTuTB from './popups/CapVatTuTB'
import InBienBan from '../../../print/InBienBan/InBienBan'

export default {
  name: 'CompleteProfileChangeTSMyTV',
  data () {
    return {
      header: {
        title: 'Hoàn thiện hồ sơ thay đổi thông số MyTV',
        list: [
          // Lập hợp đồng/Đổi tốc độ - Loại hình thuê bao/Thay đổi thông số mytv
          {name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'}},
          {
            name: 'Loại hình thuê bao/Hoàn thiện hồ sơ thay đổi thông số MyTV',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      actions: [
        {
          id: 'btnLayTTMoi',
          name: 'Lấy TT (F5)',
          active: true,
          icon_class: 'one-file-attach',
          visible: true
        },
        {
          id: 'tsbtnCapNhat',
          name: 'Cập nhật (F9)',
          active: true,
          icon_class: 'one-reload1',
          visible: true
        },
        {
          id: 'tsbtnGiaoViec',
          name: 'Giao việc',
          active: true,
          icon_class: 'one-file-edit2',
          visible: true
        },
        {
          id: 'tsbtnTraPhieu',
          name: 'Trả phiếu',
          active: true,
          icon_class: 'one-file-back',
          visible: true
        },
        {
          id: 'tsbtnKichHoat',
          name: 'Kích hoạt',
          active: true,
          icon_class: 'one-file-arrow-up',
          visible: true
        },
        {
          id: 'tsbtnChuyenTo',
          name: 'Chuyển tổ',
          active: true,
          icon_class: 'one-change',
          visible: true
        },
        {
          id: 'tsbtnVatTu',
          name: 'Vật tư',
          active: true,
          icon_class: 'one-vt',
          visible: true
        },
        {
          id: 'btnHoanCong',
          name: 'Hoàn công',
          active: true,
          icon_class: 'one-file-check',
          visible: false
        },
        {
          id: 'tsbtnChuyenDB',
          name: 'Hoàn Thiện',
          active: true,
          icon_class: 'one-file-check',
          visible: true
        },
        {
          id: 'tsbtnInBB',
          name: 'Xuất phiếu',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnGiaoPhieu_Ton',
          name: 'Tồn phiếu',
          active: true,
          icon_class: 'one-calendar-number',
          visible: true
        }
      ],
      formThongTinPhieu: {
        txtMaGD: '',
        txtMaKH: '',
        txtTenKH: '',
        txtDiaChiKH: '',
        cboDichVuVT: [],
        dtpNgayHT: moment(new Date()).format('DD/MM/YYYY'),
        selectDichVuVT: null,
        cboLoaihinhTB: [],
        selectLoaiHinhTB: null,
        cboKieuLD: [],
        selectKieuLD: null,
        txtGhiChu: ''
      },
      formThongTinThueBao: {
        txtMaTB: '',
        txtTenTB: '',
        txtDiaChiLD: '',
        txtTrangBiCu: '',
        txtLoaiTbiCu: '',
        txtTrangBiMoi: '',
        txtLoaiTbiMoi: ''
      },
      formDsNhanVienTH: {
        dtpNgayGV: moment(new Date()).format('DD/MM/YYYY HH:mm tt'),
        cboNguoiGV: [],
        dtpNgayBG: moment(new Date()).format('DD/MM/YYYY HH:mm tt'),
        selectNguoiGV: null,
        gridGiaoviec: [],
        selectGiaoViec: null,
        chkNgayGV: false,
        chkNgayBG: false
      },
      gridDanhSachTB: [],
      waterMaskConfig: {
        waterMarkcbDichVuVT: 'Chọn dịch vụ VT',
        waterMarkcbLoaiHinhTB: 'Chọn loại hình thuê bao',
        waterMarkcbKieuYC: 'Chọn kiểu yêu cầu',
        waterMarkcboNguoiDV: 'Chọn người giao việc'
      },
      dsNhanVienThucHien: {
        headers: [
          {
            fieldName: 'ten_nv',
            headerText: 'Tên nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Số điện thoại',
            allowFiltering: true,
            allowSorting: false
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
            allowSorting: false
          }
        ],
        selected: 0,
        checked: []
      },
      dsGiaoPhieuNV: [],
      dsThueBao: {
        headers: [
          {
            fieldName: 'ma_tb',
            headerText: 'Account',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      // Khai bao
      tthd_id: 0,
      dichvuvt_id: 0,
      loaitb_id: 0,
      phieu_id: 0,
      hdtb_id: 0,
      huonggiao_id: 0,
      thuebao_id: 0,
      kt_thaydoi: false,
      nhanvien_tc_id: -1,
      congviec_th: '',
      dsIn: [],
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',
      luong_id: 0,
      disableBtnExcel: false,
      disableBox: {
        dtpNgayBG: false
      },
      showModalTraphieu: false,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
      },
      popupComponentName: '',
      modelTraPhieu: {
        maGd: '',
        loaihdId: 0,
        dichvuvtId: 0,
        donviId: 0,
        matb: '',
        nhanvienId: 0,
        ngaygiao: '',
        ngaytra: '',
        kieugoiId: 0
      },
      modelGiaoViec: {
        ngaygiao: '',
        nguoigv_id: 0,
        phieu_id: 0
      },
      target: '.main-wrapper',
      modelVatTu: {},
      modelIn: {},
      dulieuGanKVQLTB: {
        hdtb_id: 0,
        donvi_id: 0
      },
      modelSearchContract: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        ngay_kt: '',
        trangthai_hd: 0
      },
      maycn: '',
      ipcn: ''
    }
  },
  components: {
    breadcrumb,
    DataManager,
    ODataAdaptor,
    Predicate,
    Query,
    KDatePicker,
    SearchContractModal,
    ChuyenTo,
    tonPhieu,
    DialogPlugin,
    TraPhieu,
    GiaoPhieuNhanVien,
    XLSX,
    CapVatTuTB,
    InBienBan
  },
  watch: {
    'formThongTinPhieu.selectDichVuVT': async function (newValue, oldValue) {
      let value = newValue === null ? oldValue : newValue
      let dsLHTB = await this.CSS_LOAIHINH_TB()
      if (dsLHTB.length > 0) {
        this.formThongTinPhieu.cboLoaihinhTB = dsLHTB.filter(item => item.DICHVUVT_ID === value)
        this.formThongTinPhieu.selectLoaiHinhTB = LoaiHinhTB.INTERNET_MYTV
      }
      let dsKLieuLD = await this.LAY_DS_KIEU_LD(newValue, LoaiHopDong.THAYDOI_THONGSO_MYTV)
      if (dsKLieuLD.length > 0) {
        this.formThongTinPhieu.selectKieuLD = dsKLieuLD
        this.formThongTinPhieu.selectKieuLD = dsKLieuLD[0].kieuld_id
      }
    },
    'gridDanhSachTB': {
      handler: function (val) {
        this.disableBtnExcel = val.length <= 0
      },
      deep: true
    }
  },
  computed: {},
  mounted () {
    this.initData()
  },
  methods: {
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    setVisibleButton (id, status) {
      this.actions[this.actions.findIndex(item => item.id === id)].visible = status
    },
    showPopupSearchContract () {
      this.modelSearchContract.dichvu_id = 0
      this.modelSearchContract.loai_hd_id = Number(LoaiHopDong.THAYDOI_THONGSO_MYTV)
      this.modelSearchContract.trangthai_hd = Number(this.tthd_id)
      this.modelSearchContract.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      console.log(this.modelSearchContract)
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract (item) {
      this.formThongTinPhieu.txtMaGD = item.ma_gd
      this.$refs.txtMaGD.focus()
    },
    getTag () {
      try {
        if (
          this.$route.query.tag != null &&
          this.$route.query.tag !== '' &&
          this.$route.query.tag.length > 0
        ) {
          let tag = decodeURIComponent(this.$route.query.tag.toString())
          let arr = tag.split('+')
          this.tthd_id = Number.parseInt(arr[0])
          this.huonggiao_id = Number.parseInt(arr[1])
        } else {
          this.tthd_id = 0
          this.huonggiao_id = 0
        }
      } catch (error) {
        this.tthd_id = 0
      }
    },
    onFilteringLoaiHinhTB (e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('loaihinh_tb', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.formThongTinPhieu.cboLoaihinhTB, query)
    },
    onFilteringDichVuVT (e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TEN_DVVT', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.formThongTinPhieu.cboDichVuVT, query)
    },
    onFilteringKieuLD (e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('TEN_KIEULD', 'startswith', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.formThongTinPhieu.cboKieuLD, query)
    },
    onFilteringNguoiGV (e) {
      let query = new Query()
      let predicate
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('ten_nv', 'contains', e.text.trim(), true)
        // predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.formDsNhanVienTH.cboNguoiGV, query)
    },
    async initData () {
      this.getTag()
      this.maycn = await this.$root.token.getMachine()
      this.ipcn = await this.$root.token.getIP()
      this.setVisibleButton('tsbtnChuyenDB', this.tthd_id === TrangThaiHD.DA_THI_CONG_XONG)
      this.setVisibleButton('btnHoanCong', this.tthd_id === TrangThaiHD.DANG_THI_CONG)
      let donviid = this.$root.token.getDonViID()
      let dsNhanVien = await this.SP_LAY_DS_NHANVIEN_THEO_DONVIDONVI(donviid)
      if (dsNhanVien.length > 0) {
        this.formDsNhanVienTH.cboNguoiGV = dsNhanVien
        this.formDsNhanVienTH.selectNguoiGV = Number.parseInt(this.$root.token.getNhanVienID())
      }
      this.formDsNhanVienTH.dtpNgayGV = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.formDsNhanVienTH.dtpNgayBG = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

      await this.HienThiCBO_DichVU_VT()

      let dsKieuLD = await this.LAY_DS_KIEU_LD(LoaiHopDong.THAYDOI_THONGSO_MYTV, 61)
      if (dsKieuLD.length > 0) {
        this.formThongTinPhieu.cboKieuLD = dsKieuLD
      }

      this.formThongTinPhieu.dtpNgayHT = moment(new Date()).format('DD/MM/YYYY')
      await this.HienThiDanhSach()
    },
    async HienThiDanhSach () {
      this.loading(true)
      this.setActiveButton('tsbtnVatTu', false)
      this.luong_id = 0
      let dsQT = await this.SP_LAY_QUYTRINH_THEO_LOAIHD_DICHVU(LoaiHopDong.THAYDOI_THONGSO_MYTV, DICHVU_VIENTHONG.ADSL, TrangThaiHD.DANG_THI_CONG, 0)
      if (dsQT.length > 0) {
        let quytrinhId = dsQT[0].quytrinh_id
        let dsHG = await this.SP_LAY_HUONGGIAO_THEO_QUYTRINH(quytrinhId, TrangThaiHD.DANG_THI_CONG)
        if (dsHG.length > 0) {
          this.huonggiao_id = dsHG[0].huonggiao_id
          let dsPhieuHC = await this.LAY_DS_PHIEU_HC_119_HUYEN(TrangThaiHD.DANG_THI_CONG,
            Number.parseInt(this.$root.token.getNhanVienID()), 0, 0, LoaiHopDong.THAYDOI_THONGSO_MYTV,
            0, 1, this.huonggiao_id, 0, 0)
          if (dsPhieuHC.length > 0) {
            this.luong_id = dsHG[0].luong_id
            await this.HienThiGiaoDien(this.luong_id)
            this.gridDanhSachTB = dsPhieuHC
          } else {
            this.gridDanhSachTB = []
          }
        }
      }
      this.loading(false)
    },
    async HienThiGiaoDien (luongId) {
      this.setActiveButton('tsbtnVatTu', false)
      try {
        let code = 'GIAOVIEC,TUDONG_GIAOPHIEU_DUYET_CAPVT,TUDONG_GIAOPHIEU_HC,VAT_TU_MOI,VAT_TU,DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO,TAMTHU_VT'
        let dsThaoTac = await this.SP_LAY_DS_THAOTAC_THEO_LUONG_ID_CODE(luongId, code)
        // TODO cần check các nút khác
        if (dsThaoTac.length > 0) {
          for (let i = 0; i < dsThaoTac.length; i++) {
            let thaotacId = dsThaoTac[i].thaotac_id
            let enable = Number.parseInt(dsThaoTac[i].enable)
            let dtControl = await this.SP_LAY_CT_THAOTAC_CONTROL(thaotacId)
            if (dtControl.length > 0) {
              for (let j = 0; j < dtControl.length; j++) {
                let controlName = dtControl[j].control_name
                if (controlName.includes('tsbtn')) {
                  console.log(controlName)
                  if (enable === 1) {
                    this.setActiveButton(controlName, true)
                  } else {
                    this.setActiveButton(controlName, false)
                  }
                }
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không hiển thị được giao diện')
      }
    },
    selectNhanVienThucHien: function (i, item) {

    },
    selectThueBao: async function (i, item) {
      try {
        console.log(item)
        this.loading(true)
        this.dsThueBao.selectItem = Object.assign({}, item)
        this.hdtb_id = item.hdtb_id
        if (item.thuebao_id) {
          this.thuebao_id = Number.parseInt(item.thuebao_id)
        }
        this.dichvuvt_id = item.dichvuvt_id
        this.loaitb_id = item.loaitb_id
        this.formThongTinPhieu.selectKieuLD = item.kieuld_id
        this.phieu_id = Number.parseInt(item.phieu_id)
        this.formThongTinThueBao.txtDiaChiLD = item.diachi_ld
        this.formThongTinPhieu.txtMaGD = item.ma_gd
        this.formThongTinPhieu.txtMaKH = item.ma_kh
        this.formThongTinThueBao.txtMaTB = item.ma_tb
        this.formThongTinPhieu.txtTenKH = this.ChuanHoaTen(item.ten_kh)
        this.formThongTinThueBao.txtTenTB = this.ChuanHoaTen(item.ten_tb)
        this.formThongTinPhieu.txtDiaChiKH = this.ChuanHoaTen(item.diachi_kh)
        this.formThongTinPhieu.selectDichVuVT = item.dichvuvt_id
        this.formThongTinPhieu.txtGhiChu = item.ghichu
        let dsNhanVien = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, Number.parseInt(this.$root.token.getNhanVienID()), 2)
        this.HienThiDSNV(dsNhanVien)
        if (item.ngay_ht_gp) {
          this.disableBox.dtpNgayBG = false
          this.formDsNhanVienTH.dtpNgayBG = item.ngay_ht_gp
          this.formDsNhanVienTH.chkNgayBG = true
        } else {
          let phailam = await this.PHAILAM(this.luong_id, 'GIAOVIEC')
          if (!phailam) {
            this.formDsNhanVienTH.chkNgayGV = true
            this.disableBox.dtpNgayBG = false
          } else {
            this.formDsNhanVienTH.chkNgayGV = false
            this.disableBox.dtpNgayBG = true
          }
        }
        if (item.status != '5') {
          this.setActiveButton('tsbtnKichHoat', true)
          this.setActiveButton('tsbtnChuyenDB', false)
        } else {
          this.setActiveButton('tsbtnKichHoat', false)
          this.setActiveButton('tsbtnChuyenDB', true)
          this.setVisibleButton('tsbtnChuyenDB', true)
        }

        if (this.formThongTinPhieu.selectKieuLD === KieuLapDat.TD_TBI_MYTV) {
          let trangbi = await this.LAY_TT_TRANGBI_V2(this.thuebao_id, this.hdtb_id)
          if (trangbi != null) {
            this.formThongTinThueBao.txtTrangBiCu = trangbi.vreturn[0] ? trangbi.vreturn[0].tentrangbi : ''
            this.formThongTinThueBao.txtTrangBiMoi = trangbi.vreturn_hd[0].tentrangbi
            this.formThongTinThueBao.txtLoaiTbiCu = trangbi.vreturn[0] ? trangbi.vreturn[0].ten_tbi : ''
            this.formThongTinThueBao.txtLoaiTbiMoi = trangbi.vreturn_hd[0].ten_tbi
          } else {
            this.formThongTinThueBao.txtTrangBiMoi = ''
            this.formThongTinThueBao.txtLoaiTbiCu = ''
            this.formThongTinThueBao.txtLoaiTbiMoi = ''
            this.formThongTinThueBao.txtTrangBiCu = ''
          }
          let btnKichHoaiActive = this.actions[this.actions.findIndex(item => item.id === 'tsbtnKichHoat')].active
          this.setActiveButton('tsbtnTraPhieu', btnKichHoaiActive)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Không hiển thị được thông tin hợp đồng: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    HienThiDSNV (ds) {
      if (ds.length > 0) {
        this.formDsNhanVienTH.chkNgayGV = true
        this.formDsNhanVienTH.dtpNgayGV = ds[0].ngaygiao
        this.formDsNhanVienTH.cboNguoiGV.selectNguoiGV = ds[0].nhanvien_giao_id
        this.formDsNhanVienTH.gridGiaoviec = ds
        this.nhanvien_tc_id = ds[0].nhanvien_th_id
        this.congviec_th = ds[0].nhiemvu
      } else {
        this.formDsNhanVienTH.gridGiaoviec = []
        this.formDsNhanVienTH.chkNgayGV = true
        this.formDsNhanVienTH.dtpNgayGV = this.formatDate(new Date(), null)
        this.formDsNhanVienTH.selectNguoiGV = Number.parseInt(this.$root.token.getNhanVienID())
        this.nhanvien_tc_id = -1
        this.congviec_th = ''
      }
    },
    async SP_LAY_THONGTIN_THEO_HDTB (hdtbId) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.SP_LAY_THONGTIN_THEO_HDTB(this.axios, {
        hdtb_id: hdtbId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async CSS_LOAIHINH_TB () {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.CSS_LOAIHINH_TB(this.axios).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_TT_TRANGBI_V2 (vthuebaoId, vhdtbId) {
      let obj = null
      await CompleteProfileChangeTSMyTVAPI.LAY_TT_TRANGBI_V2(this.axios, {
        'vthuebao_id': vthuebaoId,
        'vhdtb_id': vhdtbId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          obj = response.data.data
        }
      })
      return obj
    },
    async PHAILAM (luongId, code) {
      let phailam = false
      await CompleteProfileChangeTSMyTVAPI.PHAILAM(this.axios, {
        'luong_id': luongId,
        'code': code
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          phailam = response.data.data === '1'
        }
      })
      return phailam
    },
    async LAY_DS_NHANVIEN_THEO_PHIEU_ID (phieuid, nhanvienid, kieuid) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
        'phieu_id': phieuid,
        'nhanvien_id': nhanvienid,
        'kieu_id': kieuid
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_DS_THAOTAC_THEO_LUONG_ID_CODE (luongId, code) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.SP_LAY_DS_THAOTAC_THEO_LUONG_ID_CODE(this.axios, {
        luong_id: luongId,
        code: code
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_CT_THAOTAC_CONTROL (thaotacId) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.SP_LAY_CT_THAOTAC_CONTROL(this.axios, {
        thaotac_id: thaotacId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_PHIEU_HC_119_HUYEN (tthdId, nhanvienId, magd, phieuId, loaihdId, caidatDv, kieuId, huonggiaoId, locNv, loaiId) {
      let ds = []
      const request = {
        'tthd_id': tthdId,
        'nhanvien_id': nhanvienId,
        'ma_gd': magd,
        'phieu_id': phieuId,
        'loaihd_id': loaihdId,
        'caidat_dv': caidatDv,
        'kieu_id': kieuId,
        'huonggiao_id': huonggiaoId,
        'loc_nv': locNv,
        'loai_id': loaiId
      }
      await CompleteProfileChangeTSMyTVAPI.LAY_DS_PHIEU_HC_119_HUYEN(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_HUONGGIAO_THEO_QUYTRINH (quytrinhId, tthdId) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.SP_LAY_HUONGGIAO_THEO_QUYTRINH(this.axios, quytrinhId, tthdId).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_QUYTRINH_THEO_LOAIHD_DICHVU (dsloaihdId, dsdichvuvtId, dstthdId, kieu) {
      let ds = []
      const request = {
        'dsloaihd_id': dsloaihdId,
        'dsdichvuvt_id': dsdichvuvtId,
        'dstthd_id': dstthdId,
        'kieu': kieu
      }
      await CompleteProfileChangeTSMyTVAPI.SP_LAY_QUYTRINH_THEO_LOAIHD_DICHVU(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_DS_NHANVIEN_THEO_DONVIDONVI (donviId) {
      let ds = []
      try {
        this.loading(true)
        await CompleteProfileChangeTSMyTVAPI.SP_LAY_DS_NHANVIEN_THEO_DONVIDONVI(this.axios, donviId).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        this.$root.$toast.error('Không load được danh sách nhân viên theo đơn vị ' + donviId)
      } finally {
        this.loading(false)
      }
      return ds
    },
    async HienThiCBO_DichVU_VT () {
      let nguoidungid = this.$root.token.getNguoiDungID()
      let dk = await this.LAY_DK_CHO_BANG(nguoidungid)
      if (dk !== null) {
        if (dk.includes('4')) {
          let dichuvuvts = await this.GET_DICHVU_VT()
          this.formThongTinPhieu.cboDichVuVT = dichuvuvts.filter(item => item.DICHVUVT_ID === 4)
          this.formThongTinPhieu.selectDichVuVT = 4
        }
      }
    },
    async GET_DICHVU_VT () {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.GET_DICHVU_VT(this.axios).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DK_CHO_BANG (nguoidungid) {
      let dk = null
      await CompleteProfileChangeTSMyTVAPI.LAY_DK_CHO_BANG(this.axios, 'DICHVU_VT', nguoidungid).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          dk = response.data.data
        }
      })
      return dk
    },
    async LAY_DS_KIEU_LD (loaihdid, loaitbid) {
      let ds = []
      await CompleteProfileChangeTSMyTVAPI.LAY_DS_KIEU_LD(this.axios, loaihdid, loaitbid).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data.length > 0) {
          ds = response.data.data
        }
      })
      return ds
    },
    async FN_HOANCONG_HTHSTHAYDOIMYTV (hdtbId, luongId, phieuId, donviId, donvidlId, nhanvienId, nguoiCn, mayCn, ipCn) {
      let vds = []
      let kq = false
      vds.push({
        'HDTB_ID': hdtbId,
        'LUONG_ID': luongId,
        'PHIEU_ID': phieuId,
        'DONVI_ID': donviId,
        'DONVI_DL_ID': donvidlId,
        'NHANVIEN_ID': nhanvienId,
        'NGUOI_CN': nguoiCn,
        'MAY_CN': mayCn,
        'IP_CN': ipCn
      })
      let request = {
        'vds': vds
      }
      await CompleteProfileChangeTSMyTVAPI.FN_HOANCONG_HTHSTHAYDOIMYTV(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kq = response.data.data === '1'
        }
      })
      return kq
    },
    async FN_HOANTHIEN_HTHSTHAYDOIMYTV (hdtbId, luongId, phieuId, huonggiaoId, donviId, nhanvienId, maycn, nguoicn, ipcn) {
      let result = {}
      let vds = []
      vds.push({
        'HDTB_ID': hdtbId,
        'LUONG_ID': luongId,
        'PHIEU_ID': phieuId,
        'HUONGGIAO_ID': huonggiaoId,
        'DONVI_ID': donviId,
        'NHANVIEN_ID': nhanvienId,
        'MAY_CN': maycn,
        'NGUOI_CN': nguoicn,
        'IP_CN': ipcn
      })
      let request = {
        'vds': vds
      }
      await CompleteProfileChangeTSMyTVAPI.FN_HOANTHIEN_HTHSTHAYDOIMYTV(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data === '1') {
            result.success = true
            result.message = 'Cập nhật dữ liệu vào viễn thông tỉnh thành công!'
          } else {
            result.success = false
            result.message = response.data.message
          }
        } else {
          result.success = false
          result.message = 'Xảy ra lỗi khi thực hiện hoàn thiện'
        }
      }).catch(error => {
        console.log(error)
        result.success = false
        result.message = 'Xảy ra lỗi khi thực hiện hoàn thiện ' + error.data.message
      })
      return result
    },
    async FN_CAPNHAT_HTHSTHAYDOIMYTV (hdtbId, luongId, phieuId, nhanvienId, ngayGV, ngayBG, nguoiGVId) {
      let result = {}
      let vds = []
      vds.push({
        'HDTB_ID': hdtbId,
        'LUONG_ID': luongId,
        'PHIEU_ID': phieuId,
        'NHANVIEN_ID': nhanvienId,
        'NGAY_GV': ngayGV,
        'NGAY_BG': ngayBG,
        'NGUOIGV_ID': nguoiGVId
      })
      let request = {
        'vds': vds
      }
      await CompleteProfileChangeTSMyTVAPI.FN_CAPNHAT_HTHSTHAYDOIMYTV(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data.data === '1') {
            if (response.data.data === '1') {
              result.success = true
              result.message = 'Cập nhật dữ liệu vào viễn thông tỉnh thành công!'
            } else {
              result.success = false
              result.message = response.data.message
            }
          } else {
            result.success = false
            result.message = 'Xảy ra lỗi khi thực hiện hoàn thiện'
          }
        }
      }).catch(error => {
        console.log(error)
        result.success = false
        result.message = 'Xảy ra lỗi khi thực hiện hoàn thiện'
      })
      return result
    },
    async KICHHOAT_MYTV (maTb, kieuLdId, hdtbId, vmp) {
      let request = {
        'ma_tb': maTb,
        'cb_kieuld': kieuLdId,
        'hdtb_id': hdtbId,
        'vmp': vmp
      }
      await CompleteProfileChangeTSMyTVAPI.KICHHOAT_MYTV(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$root.$toast.success('Kích hoạt thành công!')
        } else {
          this.$root.$toast.error('Có lỗi xảy ra trong quá trình cập nhật thông tin lên VASC: ' + response.data.message)
        }
      }).catch(error => {
        this.$root.$toast.error('Lỗi kích hoạt: ' + error.data.message)
      })
    },
    async KT_DuLieuNhap () {
      if (this.gridDanhSachTB.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu để cập nhật !')
        return false
      }
      if (this.formDsNhanVienTH.chkNgayGV === false) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc !')
        this.$refs.chkNgayGV.focus()
        return false
      }

      if (this.formDsNhanVienTH.selectNguoiGV === null) {
        this.$root.$toast.warning('Hãy nhập người giao việc !')
        this.$refs.cboNguoiGV.focus()
        return false
      }

      if (this.formDsNhanVienTH.chkNgayBG === false) {
        this.$root.$toast.warning('Hãy nhập ngày hoàn thành!')
        this.$refs.chkNgayBG.focus()
        return false
      }

      let phailam = await this.PHAILAM(this.luong_id, 'GIAOVIEC')
      if (phailam) {
        if (this.formDsNhanVienTH.gridGiaoviec.length <= 0) {
          this.$root.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      let dateNgayGV = this.getDate(this.formDsNhanVienTH.dtpNgayGV, null)
      let dateNgayHT = this.getDate(this.formDsNhanVienTH.dtpNgayBG, null)
      console.log(dateNgayGV)
      console.log(dateNgayHT)
      if (dateNgayGV > dateNgayHT) {
        this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }
      return true
    },
    getDate (strDate, format) {
      if (format) {
        return moment(strDate, format).toDate()
      } else {
        return moment(strDate, 'DD/MM/YYYY HH:mm:ss').toDate()
      }
    },
    formatDate (date, format) {
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format('DD/MM/YYYY HH:mm:ss')
      }
    },
    async HoanCong () {
      try {
        if (this.gridDanhSachTB.length <= 0) {
          this.$root.$toast.warning('Không có dữ liệu để thực hiện hoàn công')
          return
        }
        let check = await this.KT_DuLieuNhap()
        if (!check) {
          return
        }
        let kq = await this.FN_HOANCONG_HTHSTHAYDOIMYTV(this.hdtb_id, this.luong_id, this.phieu_id,
          Number.parseInt(this.$root.token.getDonViID()), 0,
          Number.parseInt(this.$root.token.getNhanVienID()),
          this.$root.token.getUserName(), 'ADMINTEST', 'ADMINIP')
        if (kq) {
          this.$root.$toast.success('Thực hiện hoàn công thành công')
        } else {
          this.$root.$toast.error('Thực hiện hoàn công thất bại')
        }
        await this.HienThiDanhSach()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện hoàn công')
      }
    },
    async HoanThien () {
      try {
        if (this.gridDanhSachTB.length > 0) {
          let vds = []
          vds.push({
            'HDTB_ID': this.hdtb_id,
            'LUONG_ID': this.luong_id,
            'PHIEU_ID': this.phieu_id,
            'HUONGGIAO_ID': this.huonggiao_id,
            'DONVI_ID': Number.parseInt(this.$root.token.getDonViID()),
            'NHANVIEN_ID': Number.parseInt(this.$root.token.getNhanVienID()),
            'MAY_CN': this.maycn,
            'NGUOI_CN': this.$root.token.getUserName(),
            'IP_CN': this.ipcn
          })
          let request = {
            'vds': vds
          }
          await CompleteProfileChangeTSMyTVAPI.FN_HOANTHIEN_HTHSTHAYDOIMYTV(this.axios, request).then(response => {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
              if (response.data.data == '1') {
                this.$root.$toast.success('Cập nhật dữ liệu vào viễn thông tỉnh thành công!')
                this.HienThiDanhSach()
              } else {
                this.$root.$toast.error(response.data.data)
              }
            } else {
              this.$root.$toast.error('Xảy ra lỗi khi thực hiện hoàn thiện ' + response.data.message)
            }
          }).catch(error => {
            console.log(error)
            this.$root.$toast.error('Có lỗi: ' + error.data.message)
          })
        } else {
          this.$root.$toast.warning('Không có thuê bao để hoàn thiện!')
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện hoàn thiện ' + error.message)
      }
    },
    async CapNhat () {
      try {
        let check = await this.KT_DuLieuNhap()
        if (!check) {
          return
        }
        if (!await this.PHAILAM(this.luong_id, 'GIAOVIEC')) {
          // Cập nhật thông tin giao việc nhân viên tự động
          await this.XOA_PHIEU(this.phieu_id)
          this.TaoDuLieu_NVTH()
          await this.INSERT_GIAOPHIEU_NV()
        }
        /* let result = await this.FN_CAPNHAT_HTHSTHAYDOIMYTV(this.hdtb_id, this.luong_id,
          this.phieu_id, Number.parseInt(this.$root.token.getNhanVienID()), this.formDsNhanVienTH.dtpNgayGV,
          this.formDsNhanVienTH.dtpNgayBG, this.formDsNhanVienTH.selectNguoiGV)
        console.log(result) */
        await this.SP_UPDATE_TT_GIAOVIEC(Number.parseInt(this.$root.token.getNhanVienID()), this.formDsNhanVienTH.dtpNgayGV, this.hdtb_id, this.phieu_id, this.formDsNhanVienTH.selectNguoiGV)
        /* if (result.success === true) {
          this.$root.$toast.success(result.message)
        } else {
          this.$root.$toast.warning(result.message)
        } */
        this.$root.$toast.success('Cập nhật dữ liệu thành công!')
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Xảy ra lỗi khi cập nhật')
      }
    },
    async SP_UPDATE_TT_GIAOVIEC (nhanvienid, ngaygiao, hdtbid, phieuid, nguoigvid) {
      await CompleteProfileChangeTSMyTVAPI.SP_UPDATE_TT_GIAOVIEC(this.axios, {
        'hdtb_id': hdtbid,
        'ngay_ht': ngaygiao,
        'nhanvien_gv_id': nguoigvid,
        'nhanvien_hc_id': nhanvienid,
        'phieu_id': phieuid
      }).then(response => {
        console.log(response)
      }).catch(error => {
        console.log(error)
      })
    },
    async UPDATE_ND_THUCHIEN (phieuid, noidungth) {
      await CompleteProfileChangeTSMyTVAPI.UPDATE_ND_THUCHIEN(this.axios, {
        'nd_thuchien': noidungth,
        'phieu_id': phieuid
      })
    },
    async XOA_PHIEU (phieuId) {
      await CompleteProfileChangeTSMyTVAPI.XOA_PHIEU(this.axios, {
        phieu_id: phieuId
      }).then(response => {
        console.log(response)
      })
    },
    async INSERT_GIAOPHIEU_NV () {
      await CompleteProfileChangeTSMyTVAPI.INSERT_GIAOPHIEU_NV(this.axios, {
        json_giaophieu_nv: JSON.stringify(this.dsGiaoPhieuNV)
      }).then(response => {
        console.log(response)
      }).catch(error => {
        console.log(error)
      })
    },
    TaoDuLieu_NVTH () {
      this.dsGiaoPhieuNV = []
      let row = {
        'PHIEU_ID': this.phieu_id,
        'NHANVIEN_TH_ID': this.$root.token.getNhanVienID(),
        'SO_DT': '',
        'NHIEMVU': 'Hoàn thiện hồ sơ',
        'NHANVIEN_GIAO_ID': this.$root.token.getNhanVienID(),
        'NGAYGIAO': this.formatDate(this.getDate(this.formDsNhanVienTH.dtpNgayGV), 'DD/MM/YYYY')
      }
      this.dsGiaoPhieuNV.push(row)
    },
    async KichHoat () {
      try {
        if (this.dsThueBao.selectItem === null) {
          this.$root.$toast.warning('Chưa chọn thuê bao')
          return
        }
        let matb = this.dsThueBao.selectItem.ma_tb
        let vmp = this.dsThueBao.selectItem.vmp
        console.log('++++++++++++++++++++++++ selected item ', this.dsThueBao.selectItem)
        console.log('++++++++++++++++++++++++ vmp ' + vmp)
        if (this.formThongTinPhieu.selectKieuLD != KieuLapDat.TD_TBI_MYTV) {
          if (vmp == '1') {
            this.$confirm('Bạn thật sự kích hoạt Mytv -> VMP không ?', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(async () => {
              await this.KICHHOAT_MYTV(matb, this.formThongTinPhieu.selectKieuLD, this.hdtb_id, vmp)
            })
          } else {
            this.$confirm('Bạn thật sự kích hoạt VMP -> Mytv không ?', 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
            }).then(async () => {
              await this.KICHHOAT_MYTV(matb, this.formThongTinPhieu.selectKieuLD, this.hdtb_id, vmp)
            })
          }
        } else {
          await this.KICHHOAT_MYTV(matb, this.formThongTinPhieu.selectKieuLD, this.hdtb_id, vmp)
        }
        await this.HienThiDanhSach()
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện kích hoạt: ' + e.data.message)
      }
    },
    showPopupChuyenTo () {
      try {
        if (this.dsThueBao.selectItem === null) {
          this.$root.$toast.warning('Chưa chọn thuê bao')
          return
        }
        this.$refs.popupChuyenTo.openDialog(this.phieu_id, this.hdtb_id)
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện chuyển tổ')
      }
    },
    showPopupTonPhieu () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      let arrPhieu = []
      this.gridDanhSachTB.forEach(item => {
        if (item.chon === '1') {
          arrPhieu.push(item.phieu_id)
        }
      })
      if (!arrPhieu.includes(this.phieu_id)) {
        arrPhieu.push(this.phieu_id)
      }
      console.log(this.tthd_id)
      this.$refs.popupTonPhieu.showPhieuTon(this.phieu_id, this.tthd_id, arrPhieu)
    },
    closePopupTonPhieu () {
      this.$refs.popupTonPhieu.$refs.dialogObjPhieuTon.hide()
    },
    async closePopupChuyenTo () {
      await this.HienThiDanhSach()
    },
    async showPopupTraPhieu () {
      try {
        if (this.phieu_id !== 0) {
          let ds = await this.SP_LAY_THONGTIN_THEO_HDTB(this.hdtb_id)
          if (ds.length > 0) {
            this.modelTraPhieu.dichvuvtId = ds[0].dichvuvt_id
            this.modelTraPhieu.kieugoiId = 3
            this.modelTraPhieu.maGd = this.formThongTinPhieu.txtMaGD.trim()
            this.modelTraPhieu.loaihdId = ds[0].loaihd_id
            this.modelTraPhieu.matb = this.formThongTinThueBao.txtMaTB.trim()
            this.modelTraPhieu.donviId = Number.parseInt(this.$root.token.getDonViID())
            this.modelTraPhieu.ngaytra = '0'
            this.modelTraPhieu.ngaygiao = '0'
            this.modelTraPhieu.nhanvienId = Number.parseInt(this.$root.token.getNhanVienID())
          } else {
            this.$root.$toast.warning('Danh sách thông tin theo hợp đồng thuê bao trống!')
          }
          this.showModalTraphieu = true
        } else {
          this.$root.$toast.warning('Bạn chưa chọn phiếu!')
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi mở modal trả phiếu!')
      }
    },
    GiaoViec () {
      if (this.gridDanhSachTB.length <= 0) {
        this.$root.$toast.warning('Chưa có danh thuê bao. Bạn hãy kiểm tra lại!')
        return
      }
      if (this.dsThueBao.selectItem === null) {
        this.$root.$toast.warning('Chưa chọn thuê bao')
        return
      }
      if (this.formDsNhanVienTH.cboNguoiGV.length <= 0 || this.formDsNhanVienTH.selectNguoiGV === null) {
        this.$root.$toast.warning('Hãy nhập nhân viên giao việc!')
        return
      }

      if (!this.formDsNhanVienTH.chkNgayGV) {
        this.$root.$toast.warning('Hãy nhập ngày giao việc!')
        return
      }

      if (this.formDsNhanVienTH.chkNgayBG) {
        let dateNgayGV = new Date(this.formDsNhanVienTH.dtpNgayGV)
        let dateNgayHT = new Date(this.formDsNhanVienTH.dtpNgayBG)

        if (dateNgayGV > dateNgayHT) {
          this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
          return
        }
      }
      this.modelGiaoViec.ngaygiao = this.formDsNhanVienTH.dtpNgayGV
      this.modelGiaoViec.phieu_id = this.phieu_id
      // TODO đợi anh Kỳ check lại
      this.modelGiaoViec.nguoigv_id = Number.parseInt(this.formDsNhanVienTH.selectNguoiGV)
      this.$refs.formgiaphieu.init()
      this.$refs.giaoPhieuNhanVien.show()
    },
    async giaoviec_success () {
      let dsNv = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.phieu_id, Number.parseInt(this.$root.token.getNhanVienID()), 2)
      await this.HienThiDSNV(dsNv)
    },
    XuatExcel () {
      if (this.gridDanhSachTB.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.gridDanhSachTB)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DSTB') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DSTB.xls')
      } else this.$root.$toast('Không có dữ liệu!')
    },
    openGanNVKVTB () {
      if (this.dsThueBao.selectItem == null) {
        this.$root.$toast.warning('Bạn chưa chọn phiếu')
        return
      }
      this.dulieuGanKVQLTB.hdtb_id = Number(this.hdtb_id)
      this.dulieuGanKVQLTB.donvi_id = 0
      this.popupComponent = () => import('../../setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao')
      this.popupComponentName = 'popupChonKhuVuc'
      console.log(this.dulieuGanKVQLTB)
      this.popupComponentData = this.dulieuGanKVQLTB
      this.Popup('popupComponents')
    },
    Popup (modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup (modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async tsbtnVatTuMoi_Click () {
      this.$refs.popupVatTuTB.openDialog()
      if (this.phieu_id !== 0) {
        let dt = await this.SP_LAY_THONGTIN_THEO_HDTB(this.hdtb_id)
        if (dt.length > 0) {
          this.modelVatTu.phieu_id = this.phieu_id
          this.modelVatTu.loaihd_id = dt[0].loaihd_id
          this.modelVatTu.hdtb_id = this.hdtb_id
          this.modelVatTu.thuebao_id = dt[0].thuebao_id
          this.modelVatTu.vdvvt_id = this.dichvuvt_id
          this.modelVatTu.loaitb_id = this.loaitb_id
          this.modelVatTu.vma_tbcur = this.formThongTinThueBao.txtMaTB.trim()
          this.modelVatTu.baohong_id = 0
          this.$refs.popupVatTuTB.openDialog()
        }
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu')
      }
    },
    tsbtnInBB_Click () {
      try {
        this.$refs.txtMaGD.focus()
        this.kt_thaydoi = false
        this.InPhieu()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error(error.message)
      }
    },
    async FN_MAP_ID (dk, idNeo, table) {
      let result = ''
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table
      }
      let data = this.GetData(await CompleteProfileChangeTSMyTVAPI.FN_MAP_ID(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    InPhieu () {
      try {
        let vhdkhid = this.fn_tt_hd_thuebao(this.hdtb_id)
        if (vhdkhid == '-1') {
          this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        if (this.dsThueBao.selectItem === null) {
          this.$root.$toast.warning('Hãy chọn phiếu!')
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid.push(this.dsThueBao.selectItem.hdkh_id)
        nhdtbid.push(this.dsThueBao.selectItem.hdtb_id)
        nphieuid.push(this.dsThueBao.selectItem.phieu_id)
        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.n_hdkh_id = nhdkhid.join(',')
        this.modelIn.n_hdtb_id = nhdtbid.join(',')
        this.modelIn.nvth_id = -1
        this.modelIn.huonggiao_id = this.huonggiao_id
        this.modelIn.tentram_tb = ''
        console.log(this.modelIn)
        this.$refs.popupInBB.showModal()
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },
    async fn_tt_hd_thuebao (hdtbid) {
      let req = { param: hdtbid, type: 1 }
      return this.GetData(await CompleteProfileChangeTSMyTVAPI.fn_tt_hd_thuebao(this.axios, req))
    },
    onActionClick (action) {
      if (action.id === 'btnLayTTMoi') {
        this.HienThiDanhSach()
      } else if (action.id === 'tsbtnCapNhat') {
        this.CapNhat()
      } else if (action.id === 'tsbtnGiaoViec') {
        this.GiaoViec()
      } else if (action.id === 'tsbtnTraPhieu') {
        this.showPopupTraPhieu()
      } else if (action.id === 'tsbtnKichHoat') {
        this.KichHoat()
      } else if (action.id === 'tsbtnChuyenTo') {
        this.showPopupChuyenTo()
      } else if (action.id === 'tsbtnVatTu') {
        this.tsbtnVatTuMoi_Click()
      } else if (action.id === 'tsbtnChuyenDB') {
        this.HoanThien()
      } else if (action.id === 'tsbtnInBB') {
        this.tsbtnInBB_Click()
      } else if (action.id === 'tsbtnGiaoPhieu_Ton') {
        this.showPopupTonPhieu()
      } else if (action.id === 'btnHoanCong') {
        this.HoanCong()
      }
    },
    ChuanHoaTen (ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXl = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXl = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXl = this.ChuyenChuHoa(strXl)
            if (tenNew !== '') {
              tenNew += ' ' + strXl
            } else tenNew = strXl
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXl = strCon
          strXl = this.ChuyenChuHoa(strXl)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXl
          } else {
            tenNew = strXl
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    }
  }
}
</script>
