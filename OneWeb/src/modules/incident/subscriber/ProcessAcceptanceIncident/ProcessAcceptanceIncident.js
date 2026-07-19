import API from "./API.js";
import moment from "moment";
import XLSX from "xlsx"
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import {Predicate, Query} from "@syncfusion/ej2-data";
import NET_BAOHONG from "./enum/NET_BAOHONG"
import TRANGTHAI_BH from "./enum/TRANGTHAI_BH"
import NHOM_TT from "./enum/NHOM_TT"
import NHOMTON from "./enum/NHOMTON"
import {KENHTN_BAOHONG} from "./enum/KENHTN_BAOHONG";
import {DichVuVienThong, LoaiHinhTB, LOAIHONG} from "@/const/enums"
import DSPhieuBaoHong from "./components/DSPhieuBaoHong.vue";
import DSNhanVien from "./components/DSNhanVien.vue";
import GiaoPhieuNhanVien from '../HandleAcceptanceIncidentReports/Popups/GiaoPhieuNhanVien/GiaoPhieuNhanVien.vue';
import GiaoPhieuNhanVien_v2 from '../HandleAcceptanceIncidentReports/Popups/GiaoPhieuNhanVien_v2/GiaoPhieuNhanVien.vue';
import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import DoKiemModal from "@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue"
import ChuyenTheoDoi from "./components/PopUps/ChuyenTheoDoi";
import traphieu from "@/modules/contract/setup/ReturnInstallTicket/components/traphieu.vue";
import ThongTinHenKhachHangModal
  from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import BanDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo/components/BanDo.vue'
import VatTuBaoHong
  from '@/modules/incident/subscriber/HandleAcceptanceIncidentReports/Popups/VatTuBaoHong/VatTuBaoHong.vue'
import TraCuuMDF from '../HandleAcceptanceIncidentReports/Popups/TraCuuMDF/TraCuuMDF.vue'
import ThayDoiTTBH from '../HandleAcceptanceIncidentReports/Popups/ThayDoiTTBH/ThayDoiTTBH.vue'
import TraPhieuBaoHong from '../HandleAcceptanceIncidentReports/Popups/TraPhieuBaoHong/TraPhieuBaoHong.vue'
import LichSu_HenBH from '../HandleAcceptanceIncidentReports/Popups/LichSu_HenBH/LichSu_HenBH.vue'
import daunoithuebao from "../../../CABMAN/tracuu-thuebao-theo-cap/tracuu-dandocap-theothuebao"
import NoiDungGiao from '../HandleAcceptanceIncidentReports/Popups/NoiDungGiao.vue';
import InBienBan from "../../../print/InBienBan/InBienBan.vue";
import CheckPort from "../../../search/subscriber/CheckPort";
import breadcrumb from '@/components/breadcrumb'
import frmHenKHV3 from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmHenKH_V3.vue'
import frmHenKHV2 from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmHenKH_V2.vue'
import frmNhacViecBH from '@/modules/incident/subscriber/SearchIncidentUpdateAppointment/components/frmNhacViecBH.vue'
import frmXemThongBaoSuCo from "@/modules/incident/subscriber/ReceiveIncident/popups/frmXemThongBaoSuCo/index.vue"
import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue"
import SERVER_IMAGES from '@/const/enums/SERVER_IMAGES'
import KhaoSatKH from '../HandleAcceptanceIncidentReports/Popups/KhaoSatKH/KhaoSatKH.vue';
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import collums from "./define/collums.js";
import PopupLoaiToanha from "@/modules/contract/setup/InstallInternet/popups/popup_Loaitoanha.vue"
// import Utils from './utils';
export default {
  provide: {
    multiselect: [CheckBoxSelection]
  },
  components: {
    DatePicker,
    DSPhieuBaoHong,
    DSNhanVien,
    GiaoPhieuNhanVien,
    GiaoPhieuNhanVien_v2,
    ChuyenTo,
    DoKiemModal,
    ChuyenTheoDoi,
    traphieu,
    ThongTinHenKhachHangModal,
    VatTuBaoHong,
    ModalChonToaDo,
    TraCuuMDF,
    ThayDoiTTBH,
    TraPhieuBaoHong,
    LichSu_HenBH,
    frmNhacViecBH,
    daunoithuebao,
    NoiDungGiao,
    InBienBan,
    CheckPort,
    frmXemThongBaoSuCo,
    breadcrumb,
    BanDo,
    frmHenKHV3,
    frmHenKHV2,
    CapVatTuTB,
    KhaoSatKH,
    XemBaoCao,
    PopupLoaiToanha
  },
  data() {
    return {
      Tag: 0,
      isShowDSBH: true,
      menuItems: [
        { text: 'Giữ phiếu', id: 'dsbhGiuPhieu', target: '.e-content', iconCss: 'icon one-key text-primary' },
        { text: 'Hủy giữ phiếu', id: 'dsbhHuyGiuPhieu', target: '.e-content', iconCss: 'icon one-cancel text-danger' },
      ],
      header: {
        title: 'NGHIỆM THU BÁO HỎNG',
        list: [
          { name: 'Báo hỏng', link: { name: 'Ui.cards' } },
          { name: 'Nghiệm thu báo hỏng', link: { name: 'Ui.cards' } }
        ]
      },
      ...collums,
      fields: {
        KQXL: { text: 'text', value: 'value' },
        NguoiGV: { text: 'ten_nv', value: 'nhanvien_id' },
        TramVT: { text: 'ten_dv', value: 'donvi_id' },
        DaGiaoViec: { text: 'text', value: 'value' },
        DichVuVT: { text: 'TEN_DVVT', value: 'DICHVUVT_ID' },
        NhomNN: { text: 'loaihong', value: 'loaihong_id' },
        QuyTrinh: { text: 'quytrinh', value: 'quytrinh_id' },
        TinhTrang: { text: 'tinhtrang', value: 'tinhtrang_id' }
      },
      kieugoi_id: 0,
      huonggiao_id: null,
      tag_TuDong_LayTT: 0,
      luong_id: 0,
      baohong_id: 0,
      thuebao_id: 0,
      ttbh_id: 0,
      trangthaibh_id: -1,
      vuser_alt: null,
      vpass_alt: null,
      vuser_xtest: null,
      vpass_xtest: null,
      /// Tham số báo tồn mạng lớp trên lên VNPT - NET
      BAOHONG_MANG_LOPTREN_NET: -1,
      CAPNHAT_XLSC_NHIEUPHIEU: -1,
      /// Tham số kiểm tra đo kiểm theo nguyên nhân -- với 1 vài nguyên nhân bất khả kháng sẽ bỏ qua không đo kiểm
      KIEMTRA_DOKIEM_NGUYENNHAN: -1,
      /// Tham số nghiệm thu hỏng chỉ chọn 1 nguyên nhân
      NGHIEMTHU_BH_1_NGUYENNHAN: -1,
      ts_capnhat_ont: 0,
      iServer_DK: 1,
      /// Kiểm tra KH đã có số điện thoại liên hệ chưa trước khi nghiệm thu BH
      KIEMTRA_LIENHE_NGHIEMTHU_BH: -1,
      /// Chỉ cho chọn nguyên nhân tồn thanh thải với thuê bao cắt chuyển - HNI
      GIAOPHIEU_BAOHONG_CATCHUYEN: -1,
      GIOIHAN_NGUYENNHAN_BAOHONG: -1,
      THONGBAO_SUCO_TTVT: -1,
      KHOAMO_TRAPHIEU_BH: -1,
      /// 1: load từ frmTraCuuPhieuGiaoDen_BH
      kieu_Load: -1,
      matb_tk: '',
      dichvt_tk_id: -1,
      quytrinh_tk_id: -1,
      nhomtt_id: -1,
      LOAITB_ID: 0,
      XACMINH_HEN_BAOHONG: -1,
      //Giữ phiếu để người khác ko tác động được
      CHOPHEP_GIUPHIEU: -1,
      NGHIEMTHU_BH_HEN_KH: -1,
      NGHIEMTHU_BH_TC_TIENTRINH_BH: -1,
      NVKT_BATBUOC_NHAP_LOAINHA: -1,
      NGHIEMTHU_BH_HTKT_SC_AN_TRAPHIEU: -1,
      KHONG_TRAPHIEU_DV_TIEPNHAN: -1,
      HT_TRACUU_TT_BH: -1,
      HIENTHI_NGUYENNHAN_THEO_PHIEUCHA: -1,
      /// Hiển thị màu của cột trên lưới theo giờ còn lại
      HT_MAU_THEO_GIO_CONLAI: -1,
      IS_USING_CABMAN_V2: false,
      PHIEU_ID: 0,
      dtThaoTac: [],
      KQXL: {
        value: 0,
        enabled: true
      },
      cboKQXL: [
        {
          value: 0,
          text: 'Đã xử lý xong'
        },
        {
          value: 1,
          text: 'Không xử lý được'
        },
        {
          value: 2,
          text: 'Đang chờ xử lý'
        }
      ],
      MaTB: '',
      TenTB: null,
      TrangThaiTB: null,
      GhiChuHong: null,
      chkNgayBG: { value: true, visible: true },
      GhiChuTH: null,
      NguoiBH: null,
      DienThoaiLH: null,
      LoaiTB: null,
      Dactrung: null,
      congviec_th: '',
      _nhomnn_id: 0,
      nhanvien_th_id: 0,
      nv_giao_id: 0,
      vma_tb: '',
      cboNhomTon: [],
      dtList_checked: [],
      NhomTon: { enabled: true },
      nhomton_id: 0,
      ds_lydoton_id: [],
      dtList_chon: [],
      cboDichVuVT: [],
      DichVuVT: { value: null, enabled: true },
      cboNguoiGV: [],
      NguoiGV: null,
      cboTramVT: [],
      TramVT: { value: null, enabled: true, text: 'Trạm VT', visible: true },
      cboDaGiaoViec: [],
      DaGiaoViec: { value: null, visible: true },
      cboTinhTrang: [],
      TinhTrang: { value: null, visible: true },
      dtpNgayBH: null,
      dtpNgayTH: null,
      dtpNgayXN: { value: null, enabled: false },
      dtpNgayGV: moment(new Date()).format('DD/MM/YYYY HH:mm A'),
      chkPhieuTra: { value: false, visible: true },
      chkNgayGV: { value: false, visible: true },
      dtpTuNgay: null,
      chkTuNgay: { value: false, visible: true },
      lblDenNgay: { visible: true },
      dtpDenNgay: null,
      kt_load: true,
      cboQuyTrinh: [],
      QuyTrinh: { value: null, visible: true, enabled: true },
      chkChoXL: { value: false, visible: true },
      DiaChiLD: { value: null, visible: true },
      cboNhomNN: [],
      NhomNN: { value: null, visible: true },
      tsbtnHoanCong: { visible: true, enabled: false },
      tsbtnTraPhieu: { visible: true, enabled: false, value: false },
      tsbtnChuyenTo: { visible: true, enabled: true },
      tsbtnBaoTon_BH: { visible: false, enabled: true },
      tsbtnVatTuMoi: { visible: true, enabled: true },
      tsbtnChuyenDoiTac: { visible: true, enabled: true },
      tsbtnKQ_DoKiem: { visible: true, enabled: true },
      tsbtnChuyenTTDH: { visible: false, enabled: true, text: 'Chuyển phiếu' },
      tsbtnKhaoSat: { visible: true, enabled: true },
      tsbtnGiaoViec: { visible: false, enabled: true },
      tsbtnGiaoViecNhieu: { visible: true, enabled: true },
      tsbtnCapNhat: { visible: true, enabled: true },
      tsbtnCapNhatNhieuPhieu: { visible: false, enabled: true },
      tsbtnInPhieuTC: { visible: true, enabled: true },
      tsbtnThayDoiTT_BH: { visible: true, enabled: true },
      tsbtnNghiemThu: { visible: true, enabled: true },
      tsbtnVatTu: { visible: false, enabled: true },
      btnGiaoPhieuTG: { visible: false, enabled: true },
      ctmGiuPhieu: { visible: false, enabled: true },
      tsbtnGianDo: { visible: true, enabled: true },
      tsbtnChuyenTheoDoi: { visible: true, enabled: true },
      tsbtnNghiemThuNhieuPhieu: { visible: false, enabled: true },
      tsbtnChuyenTTVT: { visible: true, enabled: true },
      tsbtnDoKiem: { visible: true, enabled: true },
      dtList: [],
      dtListOrigin: [],
      dtList_dang_chon: [],
      pnlNguyenNhan2: { visible: true },
      ds_lydoton: [],
      
      ds_lydoton_id: [],
      pnlTon1: { visible: true },
      ds_nguyennhan: [],
      ds_nguyennhan_id: [],
      ds_NhanVien: [],
      ds_thongtin_kt: [],
      soNgay: { value: null, visible: false },
      hangSx: { value: null, visible: false },
      ds_nn: [],
      ds_ton: [],
      ds_tt: [],
      ttnd_att_adsl: 0,
      ttnd_att_gpon: 0,
      ttnd_snr_adsl: 0,
      lblSuCoTTVT: { value: '', visible: false },
      map: {
        lat: null,
        lng: null
      },
      map_info: {
        dia_chi: '',
        title: ''
      },
      soNgayCTD: null,
      collumnsInvisible: [],
      inBB: {
        baohong: true,
        n_baohong_id: 0,
        n_phieu_id: 0,
        huonggiao_id: 0,
        nvth_id: 0
      },
      mapTypeId: 'ROADMAP',
      ngay_ht: moment(new Date()).format('DD/MM/YYYY HH:mm A'),
      frmNhacViecBHInput: {},
      frmHenKHInput: {},
      frmHenKHV2Input: {},
      modelCapVatTu: {},
      dsGiaTri: [],
      iSuCo: 0,
      CURRENT_HDTB_ID: 0,
      intervalSuCo: null,
      userInfo: {},
      checkPortDialog: {
        isVisiable: false
      },
      filterTemplate_: function () {
        return {
          template: Vue.component('filterTemplate', {
            template: `<div class="check-action">
                            <h1>aaaa</h1>
                        </div>`,
            data() {
              return { data: {} };
            },
          })
        }
      },
      colNguoiGiu_Visible: false,
      showGiuPhieu: false,
      showHuyGiuPhieu: false,
      btnVisible: {
        tsbtnPHKH: true,
        tsbtnKhoaPhieu: true,
        tsbtnChuyenTTDH: true,
        tsbtnChuyenMedia: true,
        tsbtnChuyenIT360: true,
        tsbtnChuyenDHIT: true,
        tsbtnChuyenVip: true,
        tsbtnChuyenOMCData: true,
        tsbtnLayPhieu: false,//false
        tsbtnBoQua: false,//false
        tsbtnGhiLai: true,//true
        tsbtnTiepNhan: true,//true
        tsbtnBaoHongAo: true,//true
        tsbtnNhapMoi: true,//true
        btnLayTTMoi: true,//true
        tsbtnCapNhatDoThu: true,
        tsmGiuPhieu: false,
        tsmHuyGiuPHieu: false,
        tsbtnGiaoPhieu: true,
        tsbtnXoaPhieu: true,
        tsbtnHTKT: true,
        tsbtnGiaoNhanh: true,
      },
      animationSettings: { effect: 'None' }
    }
  },
  async created() {
    const query = this.$route.query
    if (query.kieu_Load) {
      this.kieu_Load = query.kieu_Load
    }
    if (query.matb_tk) {
      this.matb_tk = query.matb_tk
    }
    if (query.dichvt_tk_id) {
      this.dichvt_tk_id = query.dichvt_tk_id
    }
    if (query.quytrinh_tk_id) {
      this.quytrinh_tk_id = query.quytrinh_tk_id
    }
    await this.getTTND()
    await this.handleRoute()
    this.frmNghiemThuBH_FormLoad();
    API.thongtin_nguoidung2().then((rs) => {
      this.userInfo = rs.data.data
    })
  },
  watch: {
    'header.title'() {
      this.header.list[1].name = this.header.title.charAt(0) + this.header.title.slice(1, this.header.title.length).toLowerCase()
    },
    'chkTuNgay.value'() {
      if (!this.chkTuNgay.value) {
        this.dtList = this.dtListOrigin
        return
      }
      this.LocPhieuTheoNgay()
    },
    'chkChoXL.value'() {
      this.HienThiDanhSach()
    },
    'chkPhieuTra.value'() {
      this.LocPhieuTheoLoaiPhieu()
    },
    cboTramVT() {
      this.cboTramVT.length && (this.TramVT.value = this.cboTramVT[0].donvi_id)
      !this.cboTramVT.length && (this.TramVT.value = null)
    },
    dtpTuNgay() {
      // dtpTuNgay_ValueChanged
      console.log(45435436)
      if (this.chkTuNgay.value) this.LocPhieuTheoNgay()
    },
    dtpDenNgay() {
      // dtpDenNgay_ValueChanged
      if (this.chkTuNgay.value) this.LocPhieuTheoNgay()
    }
  },
  methods: {
    showMap: async function () {
      if (this.isShowDSBH) await this.getThongTinBanDo()
      this.isShowDSBH = !this.isShowDSBH
    },
    tsbtnTTLoainha_Click() {
      console.log(this.dtList_dang_chon);
      if(this.dtList_dang_chon.length == 0){
        this.$toast.warning('Bạn chưa chọn phiếu báo hỏng!');
        return;
      }
      this.$refs.popupLoaiToanha.hdtb_id = this.CURRENT_HDTB_ID
      this.$refs.popupLoaiToanha.thuebao_id = this.thuebao_id
      this.$refs.popupLoaiToanha.type = 1
      this.$refs.popupLoaiToanha.NVKT_BATBUOC_NHAP_LOAINHA = this.NVKT_BATBUOC_NHAP_LOAINHA == 1 ? true : false
      this.$refs.popupLoaiToanha.showDialog()
    },
    getThongTinBanDo: async function () {
      try {
        this.$root.showLoading(true)
        var rs = await this.$root.context.get('/web-cabman/bando_matdo/thongtin_bando')
        if (rs.data != null && rs.data.length > 0) {
          this.map.lat = parseFloat(rs.data[0].C_LATITUS)
          this.map.lng = parseFloat(rs.data[0].C_LONGTITUS)
        }
      } catch (error) {
        this.$root.showLoading(false)
      } finally {
        this.$root.showLoading(false)
      }
    },
    changetypeMap() {
      if (this.mapTypeId === 'ROADMAP') {
        this.mapTypeId = 'SATELLITE'
      } else {
        this.mapTypeId = 'ROADMAP'
      }
    },
    onFilteringNguoiGV(e) {
      var query = new Query()
      let predicate
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('ten_nv', 'contains', e.text.trim(), true)
        predicate = predicate.or('nhanvien_id', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.cboNguoiGV, query)
    },
    onFilteringTramVT(e) {
      var query = new Query()
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        query.where(new Predicate('ten_dv', 'contains', e.text.trim(), true))
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.TrungTamVTList, query)
    },
    onFilteringNhomNN(e) {
      var query = new Query()
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        query.where(new Predicate('loaihong', 'contains', e.text.trim(), true))
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.cboNhomNN, query)
    },
    onFilterNhomTon(e) {
      var query = new Query()
      query = e.text !== '' ? query.where('NHOMTON', 'contains', e.text, true) : query
      e.updateData(this.ds_nhomton, query)
    },
    onFilteringTinhTrang(e) {
      var query = new Query()
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        query.where(new Predicate('tinhtrang', 'contains', e.text.trim(), true))
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.cboTinhTrang, query)
    },
    onFilteringDichVuVT(e) {
      var query = new Query()
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        query.where(new Predicate('TEN_DVVT', 'contains', e.text.trim(), true))
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.cboDichVuVT, query)
    },
    onFilterLyDoTon(e) {
      var query = new Query()
      query = e.text !== '' ? query.where('baohong_ton', 'contains', e.text, true) : query
      e.updateData(this.ds_lydoton, query)
    },
    onFilterNguyennhanHong(e) {
      var query = new Query()
      query = e.text !== '' ? query.where('ct_hong', 'contains', e.text, true) : query
      e.updateData(this.ds_nguyennhan, query)
    },
    async handleRoute() {
      const name = this.$route.name
      if (name == 'ProcessAcceptanceIncident' || name == 'ProcessIncident') {
        this.Tag = 4
      } else if (name == 'CheckResult') {
        this.Tag = 16
      } else if (name == 'HandleTransferIncidentReport') {
        this.Tag = 9
      } else if (name == 'AcceptanceIncident') {
        this.Tag = 3
      } else if (name == 'HandleIncedentReportsOMCDC' || name == 'HandleIncedentReports119OMC') {
        this.Tag = 8
      } else if (name == 'HandleIncedentReportsOMCNet') {
        this.Tag = 15
      } else if (name == 'AcceptanceIncedentReportsDTDC') {
        this.Tag = 18
      }
    },
    async getTTND() {
      try {
        let response = await API.CSS_TINH()
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          let result = response.data.data
          for (let i = 0; i < result.length; i++) {
            if (result[i].TINH_ID == this.$root.token.getPhanVungID()) {
              this.ttnd_att_adsl = result[i].ATT_ADSL
              this.ttnd_att_gpon = result[i].ATT_GPON
              this.ttnd_snr_adsl = result[i].SNR_ADSL
              break
            }
          }
        }
      } catch (e) {
        console.log(e)
      }
    },
    async frmNghiemThuBH_FormLoad() {
      try {
        if (this.$route.query.tag && this.$route.query.tag.toString() !== '') {
          this.Tag = this.$route.query.tag.toString().replaceAll(' ', '+')
        }
        if (this.kieugoi_id == 0) {
          if (this.Tag.toString() != '') {
            console.log('11')
            if (this.Tag.toString().indexOf('+') > 0) {
              console.log('12')
              let str = this.Tag.toString()
              let words = str.split('+')
              console.log(words)
              if (words.length >= 1) this.kieugoi_id = words[0]
              if (words.length >= 2) {
                this.huonggiao_id = words[1]
              }
              if (words.length >= 3) {
                this.tag_TuDong_LayTT = words[2]
              }
            } else this.kieugoi_id = this.Tag
          }
        } else {
          this.kieugoi_id = 0
        }
        this.HienThiGiaoDien(this.kieugoi_id)
      } catch (e) {
        this.kieugoi_id = 0
      }
      let ds_kt1 = await API.LAY_DS_THAMSO_MD()
      if (ds_kt1.length > 0) {
        let str_vtemp = 'BAOHONG_MANG_LOPTREN_NET'
        let det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.BAOHONG_MANG_LOPTREN_NET = 1
        }
        str_vtemp = 'CAPNHAT_XLSC_NHIEUPHIEU'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.CAPNHAT_XLSC_NHIEUPHIEU = 1
        }
        str_vtemp = 'KIEMTRA_DOKIEM_NGUYENNHAN'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.KIEMTRA_DOKIEM_NGUYENNHAN = 1
        }
        str_vtemp = 'CAPNHAT_THONGTIN_ONT'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.CAPNHAT_THONGTIN_ONT = 1
        }
        str_vtemp = 'NGHIEMTHU_BH_1_NGUYENNHAN'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.NGHIEMTHU_BH_1_NGUYENNHAN = 1
        }
        str_vtemp = 'GIAOPHIEU_BAOHONG_CATCHUYEN'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.GIAOPHIEU_BAOHONG_CATCHUYEN = 1
        }
        str_vtemp = 'NGHIEMTHU_BH_HTKT_SC_AN_TRAPHIEU'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.NGHIEMTHU_BH_HTKT_SC_AN_TRAPHIEU = 1
        }
        str_vtemp = 'KHONG_TRAPHIEU_DV_TIEPNHAN'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.KHONG_TRAPHIEU_DV_TIEPNHAN = 1
        }
        str_vtemp = 'HT_TRACUU_TT_BH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.HT_TRACUU_TT_BH = 1
        }
        str_vtemp = 'HIENTHI_NGUYENNHAN_THEO_PHIEUCHA'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.HIENTHI_NGUYENNHAN_THEO_PHIEUCHA = 1
        }
        str_vtemp = 'GIOIHAN_NGUYENNHAN_BAOHONG'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.GIOIHAN_NGUYENNHAN_BAOHONG = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'THONGBAO_SUCO_TTVT'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.THONGBAO_SUCO_TTVT = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'KHOAMO_TRAPHIEU_BH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.KHOAMO_TRAPHIEU_BH = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'HT_MAU_THEO_GIO_CONLAI'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.HT_MAU_THEO_GIO_CONLAI = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'XACMINH_HEN_BAOHONG'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.XACMINH_HEN_BAOHONG = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'KIEMTRA_LIENHE_NGHIEMTHU_BH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.KIEMTRA_LIENHE_NGHIEMTHU_BH = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'NGHIEMTHU_BH_HEN_KH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.NGHIEMTHU_BH_HEN_KH = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'NGHIEMTHU_BH_TC_TIENTRINH_BH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.NGHIEMTHU_BH_TC_TIENTRINH_BH = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'NVKT_BATBUOC_NHAP_LOAINHA'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          this.NVKT_BATBUOC_NHAP_LOAINHA = row['ten_ts'].toString().trim()
        }
        str_vtemp = 'GIUPHIEU_BH'
        det = ds_kt1.filter((x) => x['ma_ts'].toString() == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row['ten_ts'].toString().trim() == '1') this.CHOPHEP_GIUPHIEU = 1
          this.btnVisible.tsmGiuPhieu = true;//false
          this.btnVisible.tsmHuyGiuPHieu = true;//false
          this.colNguoiGiu_Visible = true;
        }
      }
      ds_kt1.forEach((item) => {
        if (item.ma_ts == 'USING_CABMAN_2.0') {
          this.IS_USING_CABMAN_V2 = true
        }
      })
      try {
        this.iServer_DK = await API.LAY_THAMSO_MACDINH()
      } catch (e) {
        this.iServer_DK = 1
      }
      this.vuser_alt = await API.THANH_LY_MAP('user_alt', parseInt(this.$root.token.getPhanVungID()), this.$root.token.getPhanVungID())
      if (this.vuser_alt == '-1') {
        this.vuser_alt = ''
      }
      this.vpass_alt = await API.THANH_LY_MAP('pass_alt', parseInt(this.$root.token.getPhanVungID()), this.$root.token.getPhanVungID())
      if (this.vpass_alt == '-1') {
        this.vpass_alt = ''
      }
      this.vuser_xtest = await API.THANH_LY_MAP('user_xtest', parseInt(this.$root.token.getPhanVungID()), this.$root.token.getPhanVungID())
      if (this.vuser_xtest == '-1') {
        this.vuser_xtest = ''
      }
      this.vpass_xtest = await API.THANH_LY_MAP('pass_xtest', parseInt(this.$root.token.getPhanVungID()), this.$root.token.getPhanVungID())
      if (this.vpass_xtest == '-1') {
        this.vpass_xtest = ''
      }
      this.cboKQXL_SelectedIndexChanged_1(null, null)
      //Hiển thị danh sách tình trạng
      this.KQXL.value = 0
      this.cboDichVuVT = await API.HT_DichVuVT_Combobox_BH()
      this.cboDichVuVT.length && (this.DichVuVT.value = this.cboDichVuVT[0].DICHVUVT_ID)
      this.cboNguoiGV = await API.Lay_ds_nhanvien_thuchien_theo_dv()
      this.NguoiGV = +this.$root.token.getNhanVienID()
      this.cboTramVT = await API.LayDSTramVT()
      this.cboTramVT.length && (this.TramVT.value = this.cboTramVT[0].donvi_id)
      this.cboDaGiaoViec = [
        {
          value: 0,
          text: '--Tất cả--'
        },
        {
          value: 1,
          text: 'Chưa giao việc'
        },
        {
          value: 2,
          text: 'Đã giao việc'
        }
      ]
      this.DaGiaoViec.value = 0
      this.dtpNgayBH = this.ngay_ht
      this.dtpDenNgay = moment(new Date()).format('DD/MM/YYYY')
      this.dtpNgayTH = this.ngay_ht
      this.dtpNgayXN.value = this.ngay_ht
      this.dtpTuNgay = moment(new Date())
        .add(-7, 'days')
        .format('DD/MM/YYYY')
      this.chkTuNgay.value = false
      this.kt_load = false
      if (this.kieugoi_id == NET_BAOHONG.TTBH_XL_XONG) {
        this.KQXL.value = 0
        this.KQXL.enabled = false
      }
      if (this.THONGBAO_SUCO_TTVT == 1) {
        this.lblSuCoTTVT.visible = true
        this.lblSuCoTTVT.value = ''
        this.ds_TBSC = await this.lay_ds_thongbao_suco_ttvt()
        if (this.ds_TBSC.length > 0) {
          if (this.intervalSuCo != null) {
            clearInterval(this.intervalSuCo)
          }
          this.iSuCo = 0
          this.intervalSuCo = setInterval(() => {
            if (this.iSuCo >= this.ds_TBSC.length) {
              this.iSuCo = 0
            }
            this.lblSuCoTTVT.value = this.ds_TBSC[this.iSuCo].lydo
            this.iSuCo += 1
          }, 5000)
        } else {
          this.lblSuCoTTVT.visible = false
        }
      } else this.lblSuCoTTVT.visible = false
      if (this.kieu_Load == -1)
        this.HienThiDanhSach();
      if (this.kieu_Load == 1) {
        this.DichVuVT.value = Number(this.dichvt_tk_id)
        await this.cboDichVuVT_SelectedValueChanged();
        this.QuyTrinh.value = this.quytrinh_tk_id
        this.HienThiDSTheoTB(this.matb_tk, this.dichvt_tk_id)
      }
      // if (HT_TRACUU_TT_BH == 1)
      //     tsbtnTCTienTrinhBH.visible = true;
      // else
      //     tsbtnTCTienTrinhBH.visible = false;
    },
    async lay_ds_thongbao_suco_ttvt() {
      let result = []
      try {
        let params = {
          loaids: 12, // DS_KHONG_XULY.THONGBAO_SUCO_TTVT = 12
          tungay: moment(new Date()).format('DD/MM/yyyy HH:mm:ss'),
          denngay: moment(new Date()).format('DD/MM/yyyy HH:mm:ss')
        }
        let response = await API.ay_ds_thongbao_suco_ttvt(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async cboKQXL_SelectedIndexChanged_1() {
      console.log('cboKQXL_SelectedIndexChanged_1')
      console.log(this.LOAITB_ID)
      this.tsbtnHoanCong.enabled = true
      if (this.KQXL.value == "0") {
        this.NhomNN.visible = true
        this.pnlNguyenNhan2.visible = true
        this.pnlTon1.visible = false
        this.NhomTon.visible = false
        this.dtThaoTac.map((item) => {
          if (item.control_name == 'tsbtnNghiemThu' && item.enable == "1") {
            this.tsbtnNghiemThu.enabled = true
          }
          if (item.control_name == 'tsbtnHoanCong' && item.enable == "0") {
            this.tsbtnHoanCong.enabled = false
          }
        })
        this.tsbtnBaoTon_BH.enabled = false
        if (this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT) this.tsbtnNghiemThu.enabled = true
      } else if (this.KQXL.value == "1") {
        this.tsbtnBaoTon_BH.enabled = false
        this.dtThaoTac.map((item) => {
          if (item.control_name == 'tsbtnNghiemThu') {
            this.tsbtnNghiemThu.enabled = false
          }
          if (item.control_name == 'tsbtnBaoTon_BH' && item.enable == 1) {
            if (this.trangthaibh_id != 8) this.tsbtnBaoTon_BH.enabled = true
          }
        })
        this.tsbtnHoanCong.enabled = false
        this.tsbtnNghiemThu.enabled = false
        this.NhomNN.visible = false
        this.pnlNguyenNhan2.visible = false
        this.pnlTon1.visible = true
        this.NhomTon.visible = true
        this.cboNhomTon = await API.Ht_NhomTon_Combobox()
        this.cboNhomTon.length && (this.nhomton_id = this.cboNhomTon[0].NHOMTON_ID)
      } else {
        this.dtThaoTac.map((item) => {
          if (item.control_name == 'tsbtnNghiemThu') {
            this.tsbtnNghiemThu.enabled = false
          }
        })
        this.NhomNN.visible = false
        this.pnlNguyenNhan2.visible = false
        this.pnlTon1.visible = true
        this.NhomTon.visible = true
        this.tsbtnBaoTon_BH.enabled = false
        this.tsbtnNghiemThu.enabled = false
        this.tsbtnHoanCong.enabled = false
        this.cboNhomTon = await API.Ht_NhomTon_Combobox()
        this.cboNhomTon.length && (this.nhomton_id = this.cboNhomTon[0].NHOMTON_ID)
      }
      if (this.$auth.getMaTinh() == 'HPG') this.tsbtnBaoTon_BH.enabled = this.KQXL.value == 1
    },
    cboTramVT_EditValueChanged() {
      if (this.kieugoi_id == TRANGTHAI_BH.DANG_GIAO_XULY) return
      if (!this.TramVT.value) return
      if (!this.kt_load) {
        if (String(this.TramVT.value) != '0') {
          let cbo_tendv = ''
          for (let i = 0; i < this.cboTramVT.length; i++) {
            if (this.tramvt_id == this.cboTramVT[i].donvi_id) {
              cbo_tendv = this.cboTramVT[i].ten_dv
              break
            }
          }
          let dtList = []
          for (let i = 0; i < this.dtList.length; i++) {
            if (this.dtList[i].ten_dv == cbo_tendv) {
              dtList.push(this.dtList[i])
            }
          }
          this.dtList = dtList
        }
      }
    },
    async HienThiDanhSach() {
      this.$root.showLoading(true)
      try {
        let dvg = 0,
          gv = 0
        if (this.TramVT.value != null) dvg = +this.TramVT.value
        gv = this.DaGiaoViec.value
        await this.AnHienControls()
        let vtungay = '0',
          vdenngay = '0'
        console.log('Có load không')
        console.log(this.kieugoi_id)
        if (this.kieugoi_id == TRANGTHAI_BH.DANG_GIAO_XULY || this.kieugoi_id == 9) {
          if (this.BAOHONG_MANG_LOPTREN_NET == 1) {
            this.dtList = await API.Lay_ds_phieu_hoancong_bh_v4({
              loaidvvt_id: this.DichVuVT.value || 0,
              ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
              nhanvien_id: this.$auth.getNhanVienID(),
              ma_tb: '0',
              huonggiao_id: this.huonggiao_id || 0,
              giaoviec: gv,
              tungay: vtungay,
              denngay: vdenngay
            })
          } else {
            this.dtList = await API.Lay_ds_phieu_hoancong_bh_v5({
              loaidvvt_id: this.DichVuVT.value || 0,
              ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
              nhanvien_id: this.$auth.getNhanVienID(),
              ma_tb: '0',
              huonggiao_id: this.huonggiao_id || 0,
              giaoviec: gv,
              tungay: vtungay,
              denngay: vdenngay
            })
          }
        } else if (this.kieugoi_id == 19) {
          this.dtList = await API.Lay_ds_phieu_baoduong_ttdh({
            loaidvvt_id: this.DichVuVT.value || 0,
            ttbh_id: 9,
            nhanvien_id: this.$auth.getNhanVienID(),
            ma_tb: '0',
            huonggiao_id: 1399,
            giaoviec: gv,
            tungay: vtungay,
            denngay: vdenngay
          })
        } else if (this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT) {
          this.dtList = await API.Lay_ds_phieu_theodoi_cho_ht({
            loaidvvt_id: this.DichVuVT.value || 0,
            ttbh_id: this.chkChoXL.value ? 7 : TRANGTHAI_BH.THEODOI_CHO_HT,
            nhanvien_id: this.$auth.getNhanVienID(),
            ma_tb: '0',
            huonggiao_id: this.huonggiao_id || 0,
            donvi_giao_id: dvg,
            tungay: vtungay,
            denngay: vdenngay
          })
        } else {
          console.log('Xuống đây là sai')
          console.log(this.kieugoi_id)
          this.dtList = await API.Lay_ds_phieu_hoancong_bh_v6({
            loaidvvt_id: this.DichVuVT.value || 0,
            ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
            nhanvien_id: this.$auth.getNhanVienID(),
            ma_tb: '0',
            huonggiao_id: this.huonggiao_id || 0,
            donvi_giao_id: dvg,
            tungay: vtungay,
            denngay: vdenngay
          })
        }
        this.dtListOrigin = JSON.parse(JSON.stringify(this.dtList))
        if (this.dtList.length > 0) {
          if (this.dtList[0].gio_conlai_t != undefined) {
            this.collumnsInvisible.push('gio_conlai')
          } else {
            this.collumnsInvisible.push('gio_conlai_t')
          }
          if (this.dtList[0].ngay_dkht == undefined) this.collumnsInvisible.push('ngay_dkht')
        }
        // this.LocPhieuTheoNgay();
        if (this.dtList.length) {
          //this.gridviewDanhSach_FocusedRowChanged(this.dtList[0])
          this.tsbtnInPhieuTC.visible = true
          this.dtpNgayTH = this.ngay_ht
        } else {
          this.Clear()
          this.tsbtnInPhieuTC.visible = false
        }
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      }
      this.$root.showLoading(false)
    },
    LocPhieuTheoNgay() {
      try {
        if (!this.chkTuNgay) {
          if (this.$auth.getMaTinh() == 'HNI') {
            this.dtList = this.dtListOrigin.filter((x) => x.ghichu.includes('VNPT-NET->800126') || x.ghichu.includes('OMC Data->800126'))
            return
          }
        }
        if (this.lblDenNgay.visible)
          this.dtList = this.dtListOrigin.filter((x) => {
            if (x.ngay_bh) {
              let ngay_bh = this.strDDMMYYYYHHMM_toDate(x.ngay_bh)
              if (ngay_bh >= this.strDDMMYYYY_toDate(this.dtpTuNgay) && ngay_bh <= this.strDDMMYYYY_toDate(this.dtpDenNgay)) {
                return x
              }
            }
          })
      } catch (error) {
        console.log(error)
      }
    },
    strDDMMYYYY_toDate(dtStr) {
      let parts_of_date = dtStr.split('/')
      return new Date(+parts_of_date[2], parts_of_date[1] - 1, +parts_of_date[0])
    },
    strDDMMYYYYHHMM_toDate(dtStr) {
      if (!dtStr) return null
      let dateParts = dtStr.split('/')
      let timeParts = dateParts[2].split(' ')[1].split(':')
      dateParts[2] = dateParts[2].split(' ')[0]
      return new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0], timeParts[0], timeParts[1], 0)
    },
    LocPhieuTheoLoaiPhieu() {
      if (this.chkPhieuTra.value) {
        this.dtList = this.dtListOrigin.filter((x) => x.nd_tra_con)
      }
    },
    async HienThiDSTheoTB(_matb, _dvvt_id) {
      let dvg = 0,
        gv = 0
      if (this.TramVT.value != null) dvg = +this.TramVT.value
      gv = this.DaGiaoViec.value
      await this.AnHienControls()
      let vtungay = '0',
        vdenngay = '0'
      console.log(this.kieugoi_id)
      if (this.kieugoi_id == TRANGTHAI_BH.DANG_GIAO_XULY || this.kieugoi_id == 9) {
        console.log('Lay_ds_phieu_hoancong_bh_v4')
        if (this.BAOHONG_MANG_LOPTREN_NET == 1) {
          this.dtList = await API.Lay_ds_phieu_hoancong_bh_v4({
            loaidvvt_id: _dvvt_id,
            ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
            nhanvien_id: this.$auth.getNhanVienID(),
            ma_tb: _matb,
            huonggiao_id: this.huonggiao_id || 0,
            giaoviec: gv,
            tungay: vtungay,
            denngay: vdenngay
          })
        } else {
          this.dtList = await API.Lay_ds_phieu_hoancong_bh_v5({
            loaidvvt_id: _dvvt_id,
            ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
            nhanvien_id: this.$auth.getNhanVienID(),
            ma_tb: _matb,
            huonggiao_id: this.huonggiao_id || 0,
            giaoviec: gv,
            tungay: vtungay,
            denngay: vdenngay
          })
        }
      } else if (this.kieugoi_id == 19) {
        this.dtList = await API.Lay_ds_phieu_baoduong_ttdh({
          loaidvvt_id: _dvvt_id,
          ttbh_id: 9,
          nhanvien_id: this.$auth.getNhanVienID(),
          ma_tb: _matb,
          huonggiao_id: 1399,
          giaoviec: gv,
          tungay: vtungay,
          denngay: vdenngay
        })
      } else {
        console.log('Lay_ds_phieu_hoancong_bh_v6')
        this.dtList = await API.Lay_ds_phieu_hoancong_bh_v6({
          loaidvvt_id: _dvvt_id,
          ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
          nhanvien_id: this.$auth.getNhanVienID(),
          ma_tb: _matb,
          huonggiao_id: this.huonggiao_id || 0,
          donvi_giao_id: dvg,
          tungay: vtungay,
          denngay: vdenngay
        })
      }
      if (this.dtList.length) {
        this.gridviewDanhSach_FocusedRowChanged(this.dtList[0])
        this.tsbtnInPhieuTC.visible = true
        this.dtpNgayTH = this.ngay_ht
      } else {
        this.Clear()
        this.tsbtnInPhieuTC.visible = false
      }
    },
    async cboDichVuVT_SelectedValueChanged() {
      if (!this.cboDichVuVT.length) return
      let matinh = this.$auth.getMaTinh()
      if (matinh == 'HNI') {
        if (this.DichVuVT.value == 9) {
          this.DiaChiLD.visible = false
        } else {
          this.DiaChiLD.visible = true
        }
      }
      this.HT_Nhom_NN(this.DichVuVT.value)
      await this.HienThiGiaoDien(this.kieugoi_id)
      console.log(this.DichVuVT.value)
      this.cboQuyTrinh = await API.Lay_ds_quytrinh_bh({
        dichvuvt_id: this.DichVuVT.value
      })
      if (!this.cboQuyTrinh.length) {
        this.cboQuyTrinh = [
          {
            quytrinh: '--Không có quy trình--',
            quytrinh_id: null
          }
        ]
      }
      await this.$nextTick()
      this.QuyTrinh.value = this.cboQuyTrinh[0].quytrinh_id
      this.setsize()
      if (this.dtThaoTac.length > 0 && this.PHAILAM('AN_CHUYEN_TTDH')) {
        this.tsbtnChuyenTTDH.visible = false
      }
    },
    setsize() {
      if ((Number(this.nhomnn_id) == 21 && Number(this.DichVuVT.value) == 4) || Number(this.kieugoi_id) == TRANGTHAI_BH.THEODOI_CHO_HT) {
        this.hangSx.visible = true
        if (Number(this.kieugoi_id) == TRANGTHAI_BH.THEODOI_CHO_HT) {
          this.soNgay.value.visible = true
          this.hangSx.visible = false
        } else {
          this.hangSx.visible = true
          this.soNgay.value.visible = false
        }
      } else {
        this.hangSx.visible = false
      }
    },
    selectRowsChon() {
      let index = []
      console.log(this.dtList_dang_chon)
      this.dtList_checked.map((item) => {
        index.push(this.dtList.findIndex(x => x.baohong_id === item.BAOHONG_ID))
      })
      console.log(index)
      this.$refs.dsbaohong.selectRows(index, true)
    },
    async cboQuyTrinh_SelectedValueChanged() {
      if (this.cboQuyTrinh.length) {
        if (!this.QuyTrinh.value) {
          //this.Clear()
          this.tsbtnInPhieuTC.visible = false
          this.dtList = []
          return
        }
        if (!this.kt_load) if (this.kieu_Load != 1) await this.HienThiDanhSach()
      }
    },
    Clear() {
      const ngay_cn = this.ngay_ht
      //this.LOAITB_ID = 0
      this.ds_NhanVien = []
      this.MaTB = ''
      this.dtpNgayBH = ngay_cn
      this.dtpNgayTH = ngay_cn
      this.TenTB = ''
      this.DiaChiLD.value = ''
      this.TrangThaiTB = ''
      this.GhiChuHong = ''
      this.cboTinhTrang = []
      this.NguoiGV = +this.$auth.getNhanVienID()
      this.chkNgayBG.value = true
      this.GhiChuTH = ''
      this.TinhTrang = { value: null, visible: true }
      this.cboNguyenNhan = []
      this.NguoiBH = ''
      this.DienThoaiLH = ''
      this.LoaiTB = ''
      this.ds_thongtin_kt = []
      this.ds_nn = []
      this.ds_ton = []
      this.ds_nguyennhan_id = []
      this.ds_lydoton_id = []
    },
    async Lay_thongtin_nghiemthu_bh(kieugoi_id, quytrinh_id, ttbh_id) {
      let res = [];
      try {
          let params = {
              kieugoi_id: kieugoi_id,
              quytrinh_id: quytrinh_id,
              ttbh_id: ttbh_id
          };
          let response = await API.lay_thongtin_nghiemthu_bh(
              this.axios,
              params
          );
          console.log(response);
          if (
              response &&
              response.data &&
              response.data.error_code === "BSS-00000000" &&
              response.data.data
          ) {
              res = response.data.data;
          }
      } catch (e) {
          console.log(e);
      }
      return res;
  },
    async AnHienControls() {
      try {
        let vttbh = this.kieugoi_id == 3 ? 4 : 3
        if (this.kieugoi_id == 9)
          vttbh = 9
        if (
          this.kieugoi_id == NET_BAOHONG.TTBH_DANG_XL ||
          this.kieugoi_id == NET_BAOHONG.TTBH_XL_XONG ||
          this.kieugoi_id == 8 ||
          this.kieugoi_id == 5 ||
          this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT
        )
          vttbh = this.kieugoi_id
        /* let table, result, where, groupby
        table = ' CSS.huonggiao a, CSS.huonggiao_ldv b'
        if (this.kieugoi_id != TRANGTHAI_BH.THEODOI_CHO_HT) {
          result = ' b.luong_id, a.huonggiao_id, a.huonggiao '
          where = '  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id '
        } else {
          result = ' -1 luong_id, a.huonggiao_id, a.huonggiao '
          where = '  a.quytrinh_id = b.quytrinh_id(+) and a.huonggiao_id = b.huonggiao_id(+) '
        }
        const pv_id = this.$auth.getPhanVungID()
        where += '  and a.quytrinh_id = ' + this.QuyTrinh.value + ' and  a.ttbh_id = ' + vttbh + ` and a.phanvung_id = ${pv_id} and b.phanvung_id = ${pv_id}`
        groupby = ''
        const DS = await API.Laythongtin_table({
          table,
          where,
          result,
          groupby
        })*/
        console.log(222222222222)
        let DS = await this.Lay_thongtin_nghiemthu_bh(this.kieugoi_id, this.QuyTrinh.value, vttbh);
        if (DS.length) {
          // FIX
          this.luong_id = +DS[0]['luong_id']
          this.huonggiao_id = +DS[0]['huonggiao_id']
          this.dtThaoTac = await API.lay_ds_thaotac_theo_luong_id({
            luong_id: this.luong_id
          })
          this.tsbtnThayDoiTT_BH.visible = false
          this.tsbtnVatTuMoi.visible = false
          this.tsbtnVatTu.visible = false
          this.tsbtnNghiemThu.visible = false
          this.tsbtnTraPhieu.visible = true
          this.tsbtnTraPhieu.enabled = true
          this.tsbtnChuyenTo.enabled = true
          this.tsbtnChuyenTTDH.visible = true
          this.tsbtnChuyenTTDH.enabled = true
          this.tsbtnChuyenTTVT.visible = true
          this.tsbtnChuyenTTVT.enabled = true
          this.btnGiaoPhieuTG.visible = false
          this.btnGiaoPhieuTG.enabled = false
          this.tsbtnChuyenTheoDoi.visible = false
          this.ctmGiuPhieu.enabled = true
          this.dtThaoTac.map((item) => {
            if (item.enable == '1') {
              if (item.control_name == 'tsbtnThayDoiTT_BH') {
                this.tsbtnThayDoiTT_BH.visible = true
                this.tsbtnThayDoiTT_BH.enabled = true
              } else if (item.control_name == 'tsbtnVatTuMoi') {
                this.tsbtnVatTuMoi.visible = true
                this.tsbtnVatTuMoi.enabled = true
              } else if (item.control_name == 'tsbtnNghiemThu') {
                this.tsbtnNghiemThu.visible = true
                this.tsbtnNghiemThu.enabled = true
              } else if (item.control_name == 'tsbtnHoanCong') {
                this.tsbtnHoanCong.visible = true
                this.tsbtnHoanCong.enabled = true
              } else if (item.control_name == 'tsbtnVatTu') {
                this.tsbtnVatTu.visible = true
                this.tsbtnVatTu.enabled = true
              } else if (item.control_name == 'tsbtnChuyenDoiTac') {
                this.tsbtnChuyenDoiTac.visible = true
                this.tsbtnChuyenDoiTac.enabled = true
              }
              // control_name
              if (vttbh == NET_BAOHONG.TTBH_XL_XONG || vttbh == 9) {
                if (item.control_name == 'tsbtnNghiemThu') {
                  if (item.enable == 1) {
                    this.tsbtnNghiemThu.visible = true
                    this.tsbtnNghiemThu.enabled = true
                  } else {
                    this.tsbtnNghiemThu.visible = false
                    this.tsbtnNghiemThu.enabled = false
                  }
                }
              }
              if (item.control_name == 'btnGiaoPhieuTG') {
                this.btnGiaoPhieuTG.visible = true
                this.btnGiaoPhieuTG.enabled = true
              }
              if (item.control_name == 'tsbtnChuyenTheoDoi') {
                this.tsbtnChuyenTheoDoi.visible = true
              }
            } else if (item.enable == '0') {
              if (item.control_name == 'tsbtnTraPhieu') {
                this.tsbtnTraPhieu.enabled = false
              }
              if (item.control_name == 'tsbtnChuyenTo') {
                this.tsbtnChuyenTo.enabled = false
              }
              if (item.control_name == 'tsbtnChuyenTTDH') {
                this.tsbtnChuyenTTDH.enabled = false
              }
              if (item.control_name == 'tsbtnChuyenTTVT') {
                this.tsbtnChuyenTTVT.enabled = false
              }
              if (item.control_name == 'ctmGiuPhieu') {
                this.ctmGiuPhieu.enabled = false
              }
            }
          })
          if (this.kieugoi_id == 19) this.tsbtnTraPhieu.enabled = true
          if (this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT) {
            this.tsbtnTraPhieu.visible = false
            this.tsbtnChuyenTo.visible = false
            this.tsbtnChuyenTTDH.visible = false
            this.tsbtnChuyenTTVT.visible = false
            this.tsbtnChuyenDoiTac.visible = false
            this.tsbtnChuyenTheoDoi.visible = false
            this.tsbtnHoanCong.visible = false
            this.tsbtnNghiemThu.visible = true
            this.tsbtnNghiemThu.enabled = true
          }
          if (this.CAPNHAT_XLSC_NHIEUPHIEU == 1) {
            if (this.tsbtnNghiemThu.visible) {
              this.tsbtnNghiemThuNhieuPhieu.visible = true
              this.tsbtnNghiemThu.visible = false
            } else {
              this.tsbtnNghiemThuNhieuPhieu.visible = false
              this.tsbtnNghiemThu.visible = false
            }
          }
        }
      } catch (error) { }
    },
    PHAILAM(CODE) {
      return this.dtThaoTac.some((item) => item.enable == 1 && item.code == CODE)
    },
    async HT_Nhom_NN(vdichvuvt_id) {
      if (this.kieugoi_id == NET_BAOHONG.TTBH_DANG_XL || this.kieugoi_id == NET_BAOHONG.TTBH_XL_XONG) {
        this.cboNhomNN = await API.Lay_ds_nhom_NN({
          kieugoi_id: this.kieugoi_id,
          loaihong_id: NET_BAOHONG.LOAI_HONG,
          dichvuvt_id: vdichvuvt_id
        })
      } else {
        this.cboNhomNN = await API.HT_NHOM_NN_V2({
          dichvuvt_id: vdichvuvt_id
        })
      }
      if (this.cboNhomNN.length) {
        this.NhomNN.value = this.cboNhomNN[0].loaihong_id;
      }
    },
    async Capnhat_trangthai_baohongbc(baohong_id, tinhtrang, nhomnn, nguyennhan){
      let capnhat_tt = await API.fn_capnhat_trangthai_baohongbc({
        p_baohong_id: baohong_id,
        p_tinhtrang: tinhtrang,
        p_nhomnn: nhomnn,
        p_nguyennhan: nguyennhan
      })
      let ketqua = capnhat_tt.data.data
      if(capnhat_tt.data.error == "200" &&capnhat_tt.data.error_code == "BSS-00000000"){
          console.log("Capnhat_trangthai_baohongbc thành công")
      }else{
        if (ketqua.result != 1) {
          let kt = JSON.parse(ketqua.js_returnds)
          if (kt.ERROR_CODE != 1) {
            this.$toast.error(kt.MESSAGE)
          }
          return
        }
      }
      console.log(capnhat_tt.data.data.result)
    },
    async Capnhat_trangthai_baohongbc_v2(baohong_id, tinhtrang, nhomnn, nguyennhan){
      let capnhat_tt = await API.fn_capnhat_trangthai_baohongbc_v2({
        p_js_baohong_id: baohong_id,
        p_tinhtrang: tinhtrang,
        p_nhomnn: nhomnn,
        p_nguyennhan: nguyennhan
      })
      let ketqua = capnhat_tt.data.data
      if(capnhat_tt.data.error == "200" &&capnhat_tt.data.error_code == "BSS-00000000"){
          console.log("Capnhat_trangthai_baohongbc thành công")
      }else{
        if (ketqua.result != 1) {
          let kt = JSON.parse(ketqua.js_returnds)
          if (kt.ERROR_CODE != 1) {
            this.$toast.error(kt.MESSAGE)
          }
          return
        }
      }
      console.log(capnhat_tt.data.data.result)
    },
    async cboNhomTon_Changed() {
      this.ds_lydoton = []
      this.ds_lydoton_id = []
      if (this.KQXL.value == 2) {
        this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
          baohong_id: 0,
          nhomton_id: Number(this.nhomton_id),
          ttxl_id: 1,
          loaitb_id: this.LOAITB_ID
        })
      } else {
        this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
          baohong_id: 0,
          nhomton_id: Number(this.nhomton_id),
          ttxl_id: 0,
          loaitb_id: this.LOAITB_ID
        })
      }
      for (let i = 0; i < this.ds_lydoton.length; i++) {
        if (this.ds_lydoton[i].chon == '1') {
          this.ds_lydoton_id.push(this.ds_lydoton[i].lydotonbh_id)
        }
      }
    },
    async HienThiGiaoDien(kieugoi_id) {
      this.ttbh_id = kieugoi_id == 3 ? 4 : 3
      if (kieugoi_id == 5) this.ttbh_id = 5
      if (kieugoi_id == 9) this.ttbh_id = 9
      if (kieugoi_id == 16) this.ttbh_id = 16
      if (kieugoi_id == 15) this.ttbh_id = 15
      this.header.title = 'ĐIỀU HÀNH XỬ LÝ SỰ CỐ'
      this.tsbtnTraPhieu.visible = false
      this.tsbtnBaoTon_BH.visible = false
      this.chkTuNgay.visible = false
      this.lblDenNgay.visible = false
      this.tsbtnChuyenTo.visible = true
      this.tsbtnKQ_DoKiem.visible = true
      this.tsbtnVatTuMoi.visible = false
      this.tsbtnChuyenTTDH.visible = false
      this.dtpNgayXN.enabled = false
      if (this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT) {
        this.header.title = 'THEO DÕI CHỜ HOÀN THIỆN'
        this.Text = 'TTĐH theo dõi báo hỏng'
      }
      if (this.kieugoi_id == TRANGTHAI_BH.DANG_GIAO_XULY) {
        // Trạm viễn thông
        console.log('TRẠM VT NGHIỆM THU BÁO HỎNG')
        this.TramVT.visible = false
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.tsbtnTraPhieu.visible = this.$auth.getMaTinh() != 'HPG'
        this.TramVT.text = 'Đã giao việc'
        this.tsbtnChuyenTo.visible = true
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        if (this.$auth.getMaTinh() != 'HPG') this.tsbtnBaoTon_BH.visible = true
        this.tsbtnNghiemThu.visible = true
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = true
        this.tsbtnInPhieuTC.visible = false
        this.tsbtnVatTuMoi.visible = true
        this.header.title = 'NGHIỆM THU BÁO HỎNG'
        this.tsbtnChuyenTTDH.visible = true
        this.tsbtnChuyenTTDH.text = 'Chuyển TTĐH'
        this.collumnsInvisible.push('ten_dv')
        this.chkChoXL.visible = true
        this.tsbtnKhaoSat.visible = false
      }
      if (this.kieugoi_id == 9) {
        console.log('TỔ KHAI THÁC CẬP NHẬT KẾT QUẢ MẠNG VT LỚP TRÊN')
        // Tổ khai thác
        this.TramVT.visible = false
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.tsbtnGiaoViecNhieu.visible = false
        this.tsbtnGiaoViec.visible = false
        this.TramVT.text = 'Đã giao việc'
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        if (this.$auth.getMaTinh() != 'HPG') this.tsbtnBaoTon_BH.visible = true
        this.tsbtnNghiemThu.visible = false
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = true
        this.tsbtnInPhieuTC.visible = false
        this.tsbtnChuyenTTDH.visible = true
        this.tsbtnChuyenTTDH.text = 'Chuyển NET / Media'
        this.header.title = 'CẬP NHẬT KẾT QUẢ MẠNG VT LỚP TRÊN'
        this.collumnsInvisible.push('ten_dv')
        this.chkChoXL.visible = true
        this.tsbtnKhaoSat.visible = false
      }
      if (this.kieugoi_id == 3) {
        console.log('NGHIỆM THU BÁO HỎNG')
        // Nghiệm thu 119
        this.TramVT.visible = true
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.TramVT.text = 'Trạm VT'
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        this.tsbtnBaoTon_BH.visible = true
        this.tsbtnNghiemThu.visible = true
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = false
        this.tsbtnInPhieuTC.visible = false
        this.tsbtnBaoTon_BH.visible = true
        this.header.title = 'NGHIỆM THU BÁO HỎNG'
        this.tsbtnKhaoSat.visible = true
        this.chkChoXL.visible = true
      }
      if (kieugoi_id == TRANGTHAI_BH.DATHICONG || kieugoi_id == TRANGTHAI_BH.CHO_XULY) {
        console.log('NGHIỆM THU BÁO HỎNG')
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnNghiemThu.visible = true
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = false
        this.tsbtnInPhieuTC.visible = false
        this.tsbtnBaoTon_BH.visible = true
        this.header.title = 'NGHIỆM THU BÁO HỎNG'
        this.chkChoXL.visible = true
        this.dtpNgayXN.enabled = true
      }
      if (kieugoi_id == NET_BAOHONG.TTBH_DANG_XL) {
        // Net xlsc
        console.log('NET CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN')
        this.TramVT.visible = false
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.TramVT.text = 'Đã giao việc'
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        this.tsbtnBaoTon_BH.visible = false
        this.tsbtnNghiemThu.visible = false
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = true
        this.tsbtnInPhieuTC.visible = false
        this.header.title = 'CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN'
        this.collumnsInvisible.push('ten_dv')
        this.chkChoXL.visible = true
        this.tsbtnKhaoSat.visible = false
      }
      if (this.kieugoi_id == NET_BAOHONG.TTBH_XL_XONG) {
        // TTĐH xlsc
        console.log('TTDH CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN')
        this.TramVT.visible = false
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.tsbtnGiaoViecNhieu.visible = false
        this.tsbtnGiaoViec.visible = false
        this.TramVT.text = 'Đã giao việc'
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        this.tsbtnBaoTon_BH.visible = false
        this.tsbtnNghiemThu.visible = false
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = true
        this.tsbtnInPhieuTC.visible = false
        this.header.title = 'TTĐH CẬP NHẬT KẾT QUẢ XỬ LÝ MẠNG VT LỚP TRÊN'
        this.tsbtnChuyenTTDH.text = 'Chuyển NET / Media'
        this.collumnsInvisible.push('ten_dv')
        this.chkChoXL.visible = true
        this.tsbtnKhaoSat.visible = false
      }
      if (this.kieugoi_id == 19) {
        //TTĐH xử lý bảo dưỡng
        this.TramVT.visible = false
        this.DaGiaoViec.visible = !this.TramVT.visible
        this.TramVT.text = 'Đã giao việc'
        this.chkTuNgay.visible = true
        this.lblDenNgay.visible = true
        this.tsbtnTraPhieu.visible = true
        this.tsbtnHoanThanh.visible = true
        if (this.$auth.getMaTinh() != 'HPG') this.tsbtnBaoTon_BH.visible = true
        this.tsbtnNghiemThu.visible = false
        this.tsbtnCapNhat.visible = true
        this.tsbtnHoanCong.visible = true
        this.tsbtnInPhieuTC.visible = false
        this.tsbtnChuyenTTDH.visible = false
        this.tsbtnChuyenTTDH.text = 'Chuyển NET / Media'
        this.header.title = 'TTĐH XỬ LÝ PHIẾU BẢO DƯỠNG'
        this.collumnsInvisible.push('ten_dv')
        this.chkChoXL.visible = true
        this.chkChoXL.value = true
        this.tsbtnKhaoSat.visible = false
      }
      if (this.CAPNHAT_XLSC_NHIEUPHIEU == 1) {
        this.tsbtnCapNhatNhieuPhieu.visible = true
        this.tsbtnCapNhat.visible = false
        this.tsbtnGiaoViecNhieu.visible = true
        this.tsbtnGiaoViec.visible = false
        if (this.tsbtnNghiemThu.visible) {
          this.tsbtnNghiemThuNhieuPhieu.visible = true
          this.tsbtnNghiemThu.visible = false
        } else {
          this.tsbtnNghiemThuNhieuPhieu.visible = false
          this.tsbtnNghiemThu.visible = false
        }
      }
    },
    async gridviewDanhSach_FocusedRowChanged(i, item) {
      console.log(item);
      try {
        if (!this.dtList.length) {
          this.Clear()
          return
        }
        this.ds_nguyennhan_id = [],
          this.ds_lydoton_id = []
        this.PHIEU_ID = +item.phieu_id
        this.trangthaibh_id = +item.ttbh_id
        this.CURRENT_HDTB_ID = +item.hdtb_id
        this.LOAITB_ID = item.loaitb_id
        this.LoaiTB = item.loaihinh_tb
        this.baohong_id = +item.baohong_id
        this.MaTB = item.ma_tb
        console.log(this.MaTB)
        this.vma_tb = this.MaTB
        this.dtpNgayBH = moment(item.ngay_bh, 'DD/MM/YYYY HH:mm').format('DD/MM/YYYY HH:mm A')
        //this.DichVuVT.value = item.dichvuvt_id
        let vloaitb_id = 0
        if (this.DichVuVT.value == DichVuVienThong.CO_DINH) vloaitb_id = LoaiHinhTB.DIENTHOAI_CD
        else vloaitb_id = +item.loaitb_id
        this.cboTinhTrang = await API.Lay_ds_TinhTrang({
          baohong_id: this.baohong_id,
          dichvuvt_id: this.DichVuVT.value,
          loaitb_id: vloaitb_id,
          kieu: 1
        })
        if (this.cboTinhTrang.length) {
          this.TinhTrang.value = this.cboTinhTrang[0].tinhtrang_id
        }
        if (this.NGHIEMTHU_BH_HTKT_SC_AN_TRAPHIEU == 1) {
          if (this.cboTinhTrang.length && this.cboTinhTrang[0].nhomtt_id != '') this.nhomtt_id = +this.cboTinhTrang[0].nhomtt_id
          if (this.nhomtt_id == NHOM_TT.KHACHHANG_YC || this.nhomtt_id == NHOM_TT.SUCO) {
            this.tsbtnTraPhieu.visible = false
            this.tsbtnChuyenTTDH.visible = false
          } else {
            await this.AnHienControls()
          }
        }
        this.TenTB = item.ten_tb || null
        this.DiaChiLD.value = item.diachi_ld || null
        this.TrangThaiTB = item.trangthai_tb || null
        this.GhiChuHong = item.ghichu_hong || null
        this.NguoiBH = item.nguoi_bh || null
        this.DienThoaiLH = item.dienthoai_lh || null
        this.Dactrung = item.dactrung || null
        this.GhiChuTH = item.ghichu_th || null
        if (item.nhanvien_th_id && item.nhanvien_th_id.toString().trim()) this.nv_giao_id = item.nhanvien_th_id
        else this.nv_giao_id = this.$auth.getNhanVienID()
        if (this.HIENTHI_NGUYENNHAN_THEO_PHIEUCHA == 1) {
          this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
            phieu_id: this.PHIEU_ID,
            nhomnn_id: 0,
            ttbh_id: this.kieugoi_id
          })
        } else {
          if (this.kieugoi_id != NET_BAOHONG.TTBH_DANG_XL && this.kieugoi_id != NET_BAOHONG.TTBH_XL_XONG) {
            this.ds_nguyennhan = await API.Lay_ds_nguyennhan({
              phieu_id: this.PHIEU_ID,
              nhomnn_id: 0,
              loaitb_id: this.kieugoi_id
            })
          }
          else
            this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
              phieu_id: this.PHIEU_ID,
              nhomnn_id: 0,
              ttbh_id: this.kieugoi_id
            })
        }
        if (this.ds_nguyennhan && this.ds_nguyennhan.length) {
          let dr = this.ds_nguyennhan[0]
          if (dr.hasOwnProperty('hang_sx')) {
            dr = this.ds_nguyennhan.find((x) => x.hang_sx != null)
            if (!dr) {
              dr = this.ds_nguyennhan[0]
            }
            this.hangSx.value = dr.hang_sx
          }
          let kt = true
          let nhomnn_id = 0;
          dr = this.ds_nguyennhan.find((x) => x.chon == 1)
          if (dr != null) {
            nhomnn_id = +dr['loaihong_id']
            this.NhomNN.value = dr['loaihong_id']
            if (this.HIENTHI_NGUYENNHAN_THEO_PHIEUCHA == 1) {
              this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
                phieu_id: this.PHIEU_ID,
                nhomnn_id: nhomnn_id,
                ttbh_id: this.kieugoi_id
              })
            } else {
              if (this.kieugoi_id != NET_BAOHONG.TTBH_DANG_XL && this.kieugoi_id != NET_BAOHONG.TTBH_XL_XONG)
                this.ds_nguyennhan = await API.Lay_ds_nguyennhan({
                  phieu_id: this.PHIEU_ID,
                  nhomnn_id: nhomnn_id,
                  loaitb_id: vloaitb_id
                })
              else
                this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
                  phieu_id: this.PHIEU_ID,
                  nhomnn_id: nhomnn_id,
                  ttbh_id: this.kieugoi_id
                })
            }
            if (this.ds_nguyennhan.find((x) => x.chon == 1)) {
              let nhomnn_id = this.ds_nguyennhan.find((x) => x.chon == 1).loaihong_id;
              this.NhomNN.value = this.cboNhomNN.find((x) => x.loaihong_id == nhomnn_id).loaihong_id;
              for (let i = 0; i < this.ds_nguyennhan.length; i++) {
                if (this.ds_nguyennhan[i].chon == '1') {
                  this.ds_nguyennhan_id.push(this.ds_nguyennhan[i].cthong_id)
                }
              }
            }
            kt = false
          }
          if (kt) {
            this.NhomNN.value = this.cboNhomNN[0].loaihong_id
            nhomnn_id = +this.NhomNN.value
            if (this.HIENTHI_NGUYENNHAN_THEO_PHIEUCHA == 1) {
              this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
                phieu_id: this.PHIEU_ID,
                nhomnn_id: nhomnn_id,
                ttbh_id: this.kieugoi_id
              })
            } else {
              if (this.kieugoi_id != NET_BAOHONG.TTBH_DANG_XL && this.kieugoi_id != NET_BAOHONG.TTBH_XL_XONG)
                this.ds_nguyennhan = await API.Lay_ds_nguyennhan({
                  phieu_id: this.PHIEU_ID,
                  nhomnn_id: nhomnn_id,
                  loaitb_id: vloaitb_id
                })
              else
                this.ds_nguyennhan = await API.Lay_ds_nguyennhan_baohong_v2({
                  phieu_id: this.PHIEU_ID,
                  nhomnn_id: nhomnn_id,
                  ttbh_id: this.kieugoi_id
                })
            }
            if (this.ds_nguyennhan.find((x) => x.chon == 1)) {
              let nhomnn_id = this.ds_nguyennhan.find((x) => x.chon == 1).loaihong_id;
              this.NhomNN.value = this.cboNhomNN.find((x) => x.loaihong_id == nhomnn_id).loaihong_id;
              for (let i = 0; i < this.ds_nguyennhan.length; i++) {
                if (this.ds_nguyennhan[i].chon == '1') {
                  this.ds_nguyennhan_id.push(this.ds_nguyennhan[i].cthong_id)
                }
              }
            }
            this.NhomNN.visible = true
            this.pnlNguyenNhan2.visible = true
            this.pnlTon1.visible = false
            this.NhomTon.visible = false
          }
        }
        this.KQXL.value = -1
        let ttbh_gp_id = item.ttbh_gp_id ? item.ttbh_gp_id.toString().trim() : 0
        if (ttbh_gp_id == TRANGTHAI_BH.BAOTON) {
          this.KQXL.value = 1
          this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
            baohong_id: this.baohong_id,
            nhomton_id: 0,
            ttxl_id: 1,
            loaitb_id: this.LOAITB_ID
          })
          if (this.ds_lydoton.length) {
            for (let lydo of this.ds_lydoton) {
              if (lydo.chon && +lydo.chon == 1) {
                this.NhomNN.visible = false
                this.pnlNguyenNhan2.visible = false
                this.pnlTon1.visible = true
                this.NhomTon.visible = true
                if (lydo['loai'].toString() == '2') {
                  this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
                    baohong_id: this.baohong_id,
                    nhomton_id: NHOMTON.KHAC,
                    ttxl_id: 1,
                    loaitb_id: this.LOAITB_ID
                  })
                } else {
                  let nhomtonbh_id = 0
                  nhomtonbh_id = Number(this.ds_lydoton[i].nhomton_id)
                  this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
                    baohong_id: this.baohong_id,
                    nhomton_id: nhomtonbh_id,
                    ttxl_id: 2,
                    loaitb_id: this.LOAITB_ID
                  })
                }
                break
              }
            }
          }
        } else if (ttbh_gp_id == TRANGTHAI_BH.CHO_XULY) {
          this.KQXL.value = 2
          this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
            baohong_id: this.baohong_id,
            nhomton_id: 0,
            ttxl_id: 2,
            loaitb_id: this.LOAITB_ID
          })
          if (this.ds_lydoton.length) {
            for (let lydo of this.ds_lydoton) {
              if (lydo.chon && +lydo.chon == 1) {
                this.NhomNN.visible = false
                this.pnlNguyenNhan2.visible = false
                this.pnlTon1.visible = true
                this.NhomTon.visible = true
                let nhomtonbh_id = 0
                nhomtonbh_id = Number(lydo.nhomton_id)
                if (lydo['loai'].toString() == '2')
                  this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
                    baohong_id: this.baohong_id,
                    nhomton_id: nhomtonbh_id,
                    ttxl_id: 1,
                    loaitb_id: this.LOAITB_ID
                  })
                else
                  this.ds_lydoton = await API.Lay_ds_lydoton_theo_lhtb({
                    baohong_id: this.baohong_id,
                    nhomton_id: nhomtonbh_id,
                    ttxl_id: 2,
                    loaitb_id: this.LOAITB_ID
                  })
                break
              }
            }
          }
        } else this.KQXL.value = 0
        // if (this.trangthaibh_id == TRANGTHAI_BH.CHO_XULY) {
        //   this.KQXL.value = 0
        //   this.KQXL.enabled = false
        // } else 
        this.KQXL.enabled = true
        if (this.trangthaibh_id == TRANGTHAI_BH.THEODOI_CHO_HT) {
          this.tsbtnNghiemThu.enabled = true
        }
        await this.$nextTick()
        if (item.ngay_th && item.ngay_th.toString().trim() != '') {
          this.chkNgayBG.value = true
          this.dtpNgayTH = item.ngay_th.toString().trim()
        } else {
          const ngay_cn = this.ngay_ht
          this.dtpNgayTH = ngay_cn
          this.tsbtnHoanCong.enabled = false
          this.dtpNgayTH = ngay_cn
        }
        this.thuebao_id = +item.thuebao_id.toString().trim()
        this.HT_THONGSO_KYTHUAT(this.thuebao_id, this.DichVuVT.value)
        this.ds_NhanVien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID({
          phieu_id: this.PHIEU_ID,
          kieu_id: 2
        })
        await this.$nextTick()
        this.HienThiDSNV()
        const r_ghichu = item.ghichu
        if ((r_ghichu && r_ghichu.includes('800126->VNPT-NET')) || r_ghichu.includes('800126->VNPT-Media')) {
          this.tsbtnHoanCong.visible = false
          this.tsbtnHoanCongNET.visible = true
          this.tsbtnHoanCongNET.enabled = this.tsbtnHoanCong.enabled
          this.tsbtnHoanCongOMC.visible = false
        } else if (r_ghichu && r_ghichu.includes('800126->OMC Data')) {
          this.tsbtnHoanCong.visible = false
          this.tsbtnHoanCongOMC.visible = true
          this.tsbtnHoanCongOMC.enabled = this.tsbtnHoanCong.enabled
          this.tsbtnHoanCongNET.visible = false
        } else {
          this.tsbtnHoanCong.visible = true
          this.tsbtnHoanCongNET.visible = false
          this.tsbtnHoanCongOMC.visible = false
        }
      } catch (error) { }
      if (this.ds_nguyennhan.length == 0) {
        this.cboNhomNN_Changed()
      }
    },
    async HT_THONGSO_KYTHUAT(in_thuebao_id) {
      this.ds_thongtin_kt = await API.Lay_ds_thongtin_kythuat({
        vthuebao_id: in_thuebao_id
      })
    },
    async HienThiDSNV() {
      const nhanvien_id = this.$auth.getNhanVienID()
      if (this.ds_NhanVien && this.ds_NhanVien.length) {
        this.chkNgayGV.value = true
        //this.dtpNgayGV = this.ds_NhanVien[0]['ngaygiao'] ? moment(this.ds_NhanVien[0]['ngaygiao'], 'DD-MM-YYYY HH:mm:ss').format('DD/MM/YYYY HH:mm A') : this.ngay_ht
        this.dtpNgayGV = this.ngay_ht
        //this.NguoiGV = this.ds_NhanVien[0]['nhanvien_giao_id']
        this.NguoiGV = nhanvien_id
        //if (!this.NguoiGV) this.NguoiGV = nhanvien_id
        this.nhanvien_th_id = this.ds_NhanVien[0]['nhanvien_th_id']
        this.congviec_th = this.ds_NhanVien[0]['nhiemvu']
      } else {
        this.dtpNgayGV = this.ngay_ht
        this.NguoiGV = nhanvien_id
        this.nhanvien_th_id = -1
        this.congviec_th = ''
      }
    },
    async tsbtnLayTTMoi_Click() {
      await this.HienThiDanhSach()
      if (this.THONGBAO_SUCO_TTVT == 1) {
        this.lblSuCoTTVT.visible = true
        this.lblSuCoTTVT.value = ''
        this.ds_TBSC = await this.lay_ds_thongbao_suco_ttvt()
        if (this.ds_TBSC.length > 0) {
          if (this.intervalSuCo != null) {
            clearInterval(this.intervalSuCo)
          }
          this.iSuCo = 0
          this.intervalSuCo = setInterval(() => {
            if (this.iSuCo >= this.ds_TBSC.length) {
              this.iSuCo = 0
            }
            this.lblSuCoTTVT.value = this.ds_TBSC[this.iSuCo].lydo
            this.iSuCo += 1
          }, 5000)
        } else {
          this.lblSuCoTTVT.visible = false
        }
      } else this.lblSuCoTTVT.visible = false
    },
    async cboNhomNN_Changed() {
      if (this.NhomNN.value == null || this.thuebao_id == 0) return
      this._nhomnn_id = Number(this.NhomNN.value)
      if (this.kieugoi_id != NET_BAOHONG.TTBH_DANG_XL && this.kieugoi_id != NET_BAOHONG.TTBH_XL_XONG)
        this.ds_nguyennhan = await API.Lay_ds_nguyennhan({
          phieu_id: 0,
          nhomnn_id: this._nhomnn_id,
          loaitb_id: this.LOAITB_ID
        })
      else
        this.ds_nguyennhan =
          (await API.Lay_ds_nguyennhan_baohong_v2({
            phieu_id: 0,
            nhomnn_id: this._nhomnn_id,
            ttbh_id: this.kieugoi_id
          })) || []
      for (let i = 0; i < this.ds_nguyennhan.length; i++) {
        if (this.ds_nguyennhan[i].chon == '1') {
          this.ds_nguyennhan_id.push(this.ds_nguyennhan[i].cthong_id)
        }
      }
      this.setsize()
      this.hangSx.value = ''
      if (this.DichVuVT.value == DichVuVienThong.ADSL && this._nhomnn_id == LOAIHONG.HONG_THIETBI_DAUCUOI && this.LOAITB_ID == LoaiHinhTB.INTERNET_FTTH) {
        var dtAdsl = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
        this.hangSx.value = dtAdsl.length > 0 && dtAdsl[0].hang_sx != undefined && dtAdsl[0].hang_sx != null && dtAdsl[0].hang_sx != '' ? dtAdsl[0].hang_sx : 'Không xác định'
      }
    },
    cboNguoiGV_EditValueChanged() {
      if (this.NguoiGV == null) return
      if (this.NguoiGV.toString() != '') this.nv_giao_id = +this.NguoiGV
      else this.nv_giao_id = this.$auth.getNhanVienID()
    },
    async txtMaTB_KeyPress(e) {
      try {
        this.loading(true)
        if (this.MaTB != '') {
          if (e.keyCode == 13) {
            if (this.ttbh_id != TRANGTHAI_BH.DATHICONG) {
              let dvg = 0,
                gv = 0
              if (this.TramVT.value != null) dvg = Number(this.TramVT.value)
              gv = Number(this.DaGiaoViec.value) + 1
              let vtungay = '0',
                vdenngay = '0'
              this.dtList = await API.Lay_ds_phieu_hoancong_bh_v4({
                loaidvvt_id: this.DichVuVT.value || 0,
                ttbh_id: this.chkChoXL.value ? 7 : this.ttbh_id || 0,
                nhanvien_id: this.$auth.getNhanVienID(),
                ma_tb: this.MaTB,
                huonggiao_id: this.huonggiao_id || 0,
                giaoviec: gv,
                tungay: vtungay,
                denngay: vdenngay
              })
            } else {
              let vtungay = '00/00/0000', vdenngay = '00/00/0000'
              this.dtList = await this.LAY_DS_PHIEU_HOANCONG_BH_119(this.DichVuVT.value, this.ttbh_id, this.$root.token.getNhanVienID(), this.MaTB.trim(), vtungay, vdenngay)
            }
            if (this.dtList.length == 0) {
              let vtmp = this.MaTB.trim()
              this.Clear()
              this.LocPhieuTheoNgay()
              this.MaTB = vtmp
            }
          }
        }
      } catch (e) {
        this.$toast.error(`${e}`)
      } finally {
        this.loading(false)
      }
    },
    async LAY_DS_PHIEU_HOANCONG_BH_119(dichvuvt_id, ttbh_id, nhanvien_id, ma_tb, vtungay, vdenngay) {
      let result = []
      try {
        let params = {
          loaidv_id: dichvuvt_id,
          ttbh_id: ttbh_id,
          nhanvien_id: nhanvien_id,
          ma_tb: ma_tb,
          tungay: vtungay,
          denngay: vdenngay
        }
        let response = await API.lay_ds_phieu_hoancong_bh_119(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    getCurrentSelectedItems(items) {
      this.dtList_dang_chon = items
      this.dtList_checked = items
      this.dtList_chon = this.dtList.map(({ chon, baohong_id, phieu_id, thuebao_id, ma_tb }) => ({ chon, baohong_id, phieu_id, thuebao_id, ma_tb }))
      items.map(item => {
        this.dtList_chon.map(x => {
          if (item.baohong_id == x.baohong_id) {
            x.chon = "1";
          }
        });
      });
    },
    async LAY_TOADO_LAPDAT(hdtb_id, thuebao_id) {
      let result = ''
      try {
        let params = {
          hdtb_id: hdtb_id,
          thuebao_id: thuebao_id,
          daucuoi_id: 2
        }
        let response = await API.lay_toado_lapdat(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async btnChonToaDo_Click() {
      let data = {
        lat: null,
        lng: null
      }
        ; (this.map_info.dia_chi = this.DiaChiLD.value), (this.map_info.title = this.DiaChiLD.value)
      try {
        this.loading(true)
        var ToadoLD = await this.LAY_TOADO_LAPDAT(-1, this.thuebao_id)
        var pair = ToadoLD.split(';')
        data.lat = Number(pair[0])
        data.lng = Number(pair[1])
        this.map = data
        this.$bvModal.show('ModalChonToaDo')
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async XacNhanToaDo(value) {
      if (this.map.lat == value.lat && this.map.lng == value.lng) return
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc muốn cập nhật toạ độ mới cho địa chỉ lắp đặt của thuê bao?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true)
              await this.capnhat_toado_thuebao_v2(this.thuebao_id, value.lat, value.lng)
            } catch (error) {
              this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
            } finally {
              this.loading(false)
            }
          }
        })
    },
    async capnhat_toado_thuebao_v2(thuebao_id, lat, lng) {
      try {
        let params = {
          kieu: 0,
          db_id: thuebao_id,
          lat: lat,
          lng: lng,
          update_tb_cung_cap: 1,
          force_update: 0,
          daucuoi_id: 0
        }
        let response = await API.capnhat_toado_thuebao_v2(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật tọa độ thành công!')
        } else {
          this.$toast.error(response.data.message)
        }
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      }
    },
    tsbtnGiaoViecNhieu_Click() {
      if (this.dtList_dang_chon.length <= 1) {
        this.tsbtnGiaoViec_Click()
      } else this.GiaoViecNhieuPhieu(this.dtList_dang_chon)
    },
    tsbtnGiaoViec_Click() {
      this.GiaoViec()
    },
    async GiaoViec() {
      if (this.dtList.length == 0) {
        return this.$toast.warning('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
      }
      if (!this.NguoiGV) {
        this.$toast.warning('Hãy nhập nhân viên giao việc!')
        // this.$refs.NguoiGV.focusIn();
        return
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        // chkNgayGV.Focus();
        return
      }
      if (this.CHOPHEP_GIUPHIEU == 1) {
        let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
        if (kqnt.result != 1) {
          try {
            let kt = JSON.parse(kqnt.js)
            if (kt.ERROR_CODE != 1) {
              this.$toast.error(kt.MESSAGE)
            }
          } catch (e) {
            this.$toast.error(kqnt)
          }
          return
        }
      }
      let vnhanvien_giao_id = 0
      let vngaygiao
      vnhanvien_giao_id = Number(this.NguoiGV)
      vngaygiao = moment(this.dtpNgayGV, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss')
      this.$refs.dlg_giaophieunhanvien.show()
      this.$refs.ppGiaoPhieuNhanVien.frmGiaoPhieuBHNV_Load(this.PHIEU_ID, this.$root.token.getDonViID(), vnhanvien_giao_id, vngaygiao, this.thuebao_id, this.MaTB.trim())
    },
    async GiaoViecNhieuPhieu(lstChon) {
      try {
        if (this.nv_giao_id == 0) {
          this.$toast.warning('Hãy nhập nhân viên giao việc!')
          return
        }
        if (!this.chkNgayGV.value) {
          this.$toast.warning('Hãy nhập ngày giao việc!')
          return
        }
        let vngaygiao = moment(this.dtpNgayGV, 'DD/MM/YYYY HH:mm A').format('DD/MM/YYYY HH:mm:ss')
        if (this.CHOPHEP_GIUPHIEU == 1) {
          let kqnt = await this.KIEMTRA_PHIEU('0', 0, 3)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        }
        this.$refs.dlg_giaophieunhanvien_v2.show()
        this.$refs.ppGiaoPhieuNhanVien_v2.frmGiaoPhieuBHNV_v2_Load(lstChon, Number(this.$root.token.getDonViID()), Number(this.nv_giao_id), vngaygiao)
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
      }
    },
    tsmGiuPhieu_Click() {
      this.HUY_GIUPHIEU(1);
    },
    tsmHuyGiuPHieu_Click() {
      this.HUY_GIUPHIEU(2);
    },
    async HUY_GIUPHIEU(kieu) {
      try {
        console.log(this.dtList)
        var dt = this.dtList
        if (dt == null || dt.length == 0) {
          this.$toast.warning('Không có danh sách phiếu không thể thực hiện!')
          return;
        }
        console.log(1)
        if (this.dtList_dang_chon.length == 0) {
          this.$toast.warning('Chưa chọn danh sách phiếu để giữ không thể thực hiện!')
          return;
        }
        console.log(2)
        if (this.dtList_dang_chon.length > 1) {
          this.$toast.warning('Bạn chỉ được chọn 1 phiếu để giữ.')
          return;
        }
        console.log(3)
        let dt_kt = this.dtList_dang_chon.map(item => {
          return {
            PHIEU_ID: item.phieu_id,
            BAOHONG_ID: item.baohong_id
          }
        });
        //Nếu có nhân viên giữ của 1 phiếu bất kỳ return
        let json_list = JSON.stringify(dt_kt);
        let kqnt = await this.KIEMTRA_PHIEU(json_list, 0, kieu)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        // if (!this.KIEMTRA_PHIEU(json_list, 0, kieu))
        //   return;
        if (kieu == 1) {
          if (dt.filter(x => x.NHANVIEN_GIU_ID == this.$auth.getNhanVienID()).length > 0) {
            this.$toast.warning("Bạn đã giữ phiếu. Không thể giữ thêm!");
            return;
          }
        }
        this.sp_capnhat_nhanvien_giuphieu(kieu, json_list)
        this.HienThiDanhSach();
      }
      catch (ex) {
        this.$toast.error('Có lỗi!' + ex)
      }
    },
    async sp_capnhat_nhanvien_giuphieu(kieu, json_list) {
      try {
        let params = {
          kieu: kieu,
          nhanvien_id: this.$auth.getNhanVienID(),
          json_danhsach: json_list,
          may_cn: this.$root.token.getNguoiDungID(),
          nguoi_cn: this.$root.token.getUserName(),
          ip_cn: await this.$root.token.getIP()
        }
        let response = await API.sp_capnhat_nhanvien_giuphieu(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success(kieu == 1 ? "Giữ phiếu thành công" : "Huỷ giữ phiếu thành công");
        } else {
          this.$toast.error(response.data.message)
        }
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      }
    },
    async KIEMTRA_PHIEU(ds_para, phieu_id, kieu) {
      let kqnt = null
      try {
        let params = {
          kieu: kieu,
          phieu_id: phieu_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
          ds_para: ds_para
        }
        let response = await API.fn_ntbh_kiemtra_phieu(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    cboDaGiaoViec_SelectedIndexChanged() {
      if (this.kieugoi_id == TRANGTHAI_BH.DANG_GIAO_XULY) if (!this.kt_load) this.HienThiDanhSach()
    },
    tsbtnChuyenTheoDoi_Click() {
      if (this.baohong_id == 0 || !this.dtList.length) {
        this.$toast.error('Không có dữ liệu để thực hiện!')
        return
      }
      this.$refs.chuyenTheoDoi.showDialog()
    },
    async selected_so_ngay() {
      if (!this.soNgayCTD) {
        this.soNgayCTD == '' && this.$toast.warning('Bạn chưa nhập số ngày!')
        this.soNgayCTD <= 0 && this.$toast.warning('Hãy nhập số ngày > 0')
        return
      }
      if (!this.GhiChuTH) {
        this.$toast.warning('Hãy nhập ghi chú!')
        return
      }
      let isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn chắc chắn muốn chuyển theo dõi?`, {
        title: 'Thông báo',
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy'
      })
      if (isConfirm) {
        // if (!this.CapNhat(true))
        //     return;
        await API.Giaophieu_chuyen_theodoi_v2({
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          nhanvien_id: this.$auth.getNhanVienID(),
          huonggiao_id: this.huonggiao_id,
          nguoi_cn: this.$auth.getNhanVienID(),
          may_cn: 'admin',
          ngay_cn: moment(this.$auth.getNgayCapNhat(),'DD/MM/YYYY').format('YYYYMMDD'),
          songay: this.soNgayCTD,
          ghichu: this.GhiChuTH
        });
        this.$toast.success('Cập nhật dữ liệu thành công!')
        this.$refs.chuyenTheoDoi.hide()
        this.HienThiDanhSach()
      }
    },
    async tsbtnChuyenTo_Click() {
      try {
        if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
          if (
            await API.LAY_DS_PHIEUVT_HDBH({
              phieu_id: this.PHIEU_ID,
              baohong_id: this.baohong_id,
              hdtb_id: 0,
              kieutbi: 0,
              status: 0
            }).length
          ) {
            this.$toast.warning('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }
        if (this.CHOPHEP_GIUPHIEU == 1) {
          let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        }
        this.$refs.popupChuyenTo.openDialog(this.PHIEU_ID, 0, this.baohong_id)
      } catch (ex) {
        this.$toast.error('Có lỗi!')
      }
    },
    tsbtnNghiemThuNhieuPhieu_Click() {
      if (this.ds_nguyennhan_id.length == 0) {
        this.$toast.warning('Hãy nhập nguyên nhân hỏng!')
        return;
      }
      if (this.dtList_dang_chon.length <= 1) {
        this.tsbtnNghiemThu_Click()
      } else this.HoanThienNhieuPhieu()
    },
    async tsbtnNghiemThu_Click() {
      if (this.ds_nguyennhan_id.length == 0) {
        this.$toast.warning('Hãy nhập nguyên nhân hỏng!')
        return;
      }
      try {
        this.loading(true)
        await this.HoanThien()
      } catch (e) {
        console.log(e)
        this.$toast.error('Xảy ra lỗi: ' + e)
      } finally {
        this.loading(false)
      }
    },
    async LAY_DS_DB_ADSL_THEO_TBID(thuebao_id) {
      let result = []
      try {
        let params = {
          thueBaoId: thuebao_id
        }
        let response = await API.LAY_DS_DB_ADSL_THEO_TBID(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    TaoDuLieu_tinhtrang(themmoi) {
      let ds_tt = [];
      if (this.cboTinhTrang.length > 0) {
        for (const tinhTrangId of this.cboTinhTrang) {
          ds_tt.push(tinhTrangId);
        }
      }
      this.ds_tt = [...ds_tt];
      return ds_tt;
    },
    async TaoDuLieu_nguyennhan() {
      this.ds_nn = []
      if (this.KQXL.value == 0) {
        let row = {}
        if (!this.ds_nguyennhan_id.length) return
        for (let i = 0; i < this.ds_nguyennhan_id.length; i++) {
          if (!this.ds_nguyennhan.length) return
          for (let j = 0; j < this.ds_nguyennhan.length; j++) {
            if (this.ds_nguyennhan[j].cthong_id == this.ds_nguyennhan_id[i]) {
              row = {}
              row.phieu_id = this.PHIEU_ID
              row.cthong_id = Number(this.ds_nguyennhan_id[i])
              if (this._nhomnn_id == LOAIHONG.HONG_THIETBI_DAUCUOI && this.DichVuVT.value == DichVuVienThong.ADSL) {
                let dtAdsl = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
                row.hang_sx = this.hangSx.value
                row.hardware_version = dtAdsl.length > 0 ? dtAdsl[0].hardware_version : ''
                row.firmware_version = dtAdsl.length > 0 ? dtAdsl[0].firmware_version : ''
              }
              this.ds_nn.push(row)
              break
            }
          }
        }
      }
    },
    TaoDuLieu_baoton() {
      this.ds_ton = []
      if (this.KQXL.value == 1 || this.KQXL.value == 2) {
        // Nếu xử lý được thì phải chọn nguyên nhân
        let row = {}
        for (let i = 0; i < this.ds_lydoton_id.length; i++) {
          for (let j = 0; j < this.ds_lydoton.length; j++) {
            if (this.ds_lydoton[j].lydotonbh_id == this.ds_lydoton_id[i]) {
              row = {}
              row.baohong_id = this.baohong_id
              row.ctton_id = Number(this.ds_lydoton_id[i])
              this.ds_ton.push(row)
              break
            }
          }
        }
      }
    },
    init_ds_cbonguyennhan() {
      let ds_cbonguyennhan = []
      let ds_nguyennhan_init = []
      if (!this.ds_nguyennhan || !this.ds_nguyennhan.length) return
      for (let i = 0; i < this.ds_nguyennhan.length; i++) {
        for (let j = 0; j < this.ds_nguyennhan_id.length; j++) {
          if (this.ds_nguyennhan[i].cthong_id == this.ds_nguyennhan_id[j]) {
            let temp = Object.assign({}, this.ds_nguyennhan[i])
            temp.checkstate = 1
            ds_cbonguyennhan.push(temp)
            ds_nguyennhan_init.push(this.ds_nguyennhan[i])
            break
          }
        }
      }
      let dokiem_para = {
        cbo_nguyennhan: JSON.stringify(ds_cbonguyennhan),
        ds_nguyennhan: JSON.stringify(ds_nguyennhan_init)
      }
      return dokiem_para
    },
    init_ds_lydoton() {
      let ds_cboLydoton = []
      if (!this.ds_lydoton.length) return
      for (let i = 0; i < this.ds_lydoton.length; i++) {
        if (!this.ds_lydoton_id.length) return
        let temp = Object.assign({}, this.ds_lydoton[i])
        let checkstate = 0
        for (let j = 0; j < this.ds_lydoton_id.length; j++) {
          if (this.ds_lydoton[i].lydotonbh_id == this.ds_lydoton_id[j].lydotonbh_id) {
            checkstate = 1
            break
          }
        }
        temp.checkstate = checkstate
        ds_cboLydoton.push(temp)
      }
      return ds_cboLydoton
    },
    async fn_ntbh_nghiemthu(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_nghiemthu(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    async Hoanthanh_bh_giaophieu_ph(tinhthicong_id,baohong_tinh_id) {
      let result = []
      try {
        let params = {
          baohong_tinh_id: baohong_tinh_id,
          tinhthicong_id:tinhthicong_id
        }
        let response = await API.hoanthanh_bh_giaophieu_ph(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async Hoanthanh_bh_giaophieu_ph_v2(baohong_tinh_id,js_baohong_id) {
      let result = []
      try {
        let params = {
          js_baohong_id:js_baohong_id,
          tinhthicong_id: baohong_tinh_id
        }
        let response = await API.hoanthanh_bh_giaophieu_ph_v2(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async HoanThien() {
      try {
        if (this.baohong_id == 0 || this.dtList.length <= 0) {
          this.$toast.error('Không có dữ liệu nghiệm thu!')
          return
        }
        if(this.NVKT_BATBUOC_NHAP_LOAINHA == 1){
          let data = await this.axios.get("web-hopdong/lapdatmoi/sp_nhom_loainha_lay_ds")
          if(this.thuebao_id == 0 ){
              data = await this.axios.get("web-hopdong/hopdong/sp_lay_diachi_thuebao_loainha", {
                params : {
                vdb_id : this.hdtb_id,
                vtype : 1
                }
              })
           }else{
                data = await this.axios.get("web-hopdong/hopdong/sp_lay_diachi_thuebao_loainha", {
                                      params : {
                                                vdb_id : this.thuebao_id,
                                                vtype : 2
                                            }
                })
            }
            let ttln = data.data.data
            if(ttln.length > 0 && ttln[0].loainha_id == null) {
              this.$toast.error('Vui lòng cập nhật thông tin loại nhà trước khi nghiệm thu!')
              return
            }
        }
        let ds_hoancong_para = 
          {
            baohong_id: this.baohong_id,
            phieu_id: this.PHIEU_ID,
            nghiemthu: this.PHAILAM('CHECK_GIAOPHIEU_199_NGHIEMTHU') ? 1 : 0,
            kt_tsl_tram_tg: this.PHAILAM('KT_TSL_TRAM_TG') ? '1' : '0',
            dichvuvt_id: this.DichVuVT.value,
            hdtb_id: -1,
            thuebao_id: this.thuebao_id,
            daucuoi_id: 0,
            bo_qua_dokiem: this.PHAILAM('BO_QUA_DOKIEM') ? '1' : '0',
            nguoi_cn: this.$root.token.getUserName(),
            may_cn: this.$root.token.getNguoiDungID(),
            ip_cn: '',
            nhanvien_id: this.$root.token.getNhanVienID(),
            att_adsl: this.ttnd_att_adsl,
            att_gpon: this.ttnd_att_gpon,
            snr_adsl: this.ttnd_snr_adsl,
            kqxl_id: this.KQXL.value,
            ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
            ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
            kieugoi_id: this.kieugoi_id,
            huonggiao_id: this.huonggiao_id,
            ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
          }
        
        this.ds_tt = []
        this.TaoDuLieu_tinhtrang()
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          phieu_id: this.PHIEU_ID,
          nhanvien_id: this.$root.token.getNhanVienID(),
          thuebao_id: this.thuebao_id,
          kqxl_id: this.KQXL.value,
          ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
          ma_tb: this.vma_tb,
          baohong_id: this.baohong_id,
          check_giaophieu_199_nghiemthu: this.PHAILAM('CHECK_GIAOPHIEU_199_NGHIEMTHU') ? '1' : '0',
          lam_hoac_boqua: -1,
          ds_hoancong_para: JSON.stringify(ds_hoancong_para),
          kt_tsl_nt: this.PHAILAM('KT_TSL_NT') ? '1' : '0',
          bo_qua_dokiem: this.PHAILAM('BO_QUA_DOKIEM') ? '1' : '0',
          dichvuvt_id: this.DichVuVT.value,
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip_cn: '',
          ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          att_adsl: this.ttnd_att_adsl,
          att_gpon: this.ttnd_att_gpon,
          snr_adsl: this.ttnd_snr_adsl,
          kt_tsl_tram_tg: this.PHAILAM('KT_TSL_TRAM_TG') ? '1' : '0',
          ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
          kiemtra_srgp: this.PHAILAM('KIEMTRA_SRGP') ? '1' : '0',
          dokiem_para: JSON.stringify(this.init_ds_cbonguyennhan()),
          hdtb_id: -1,
          kiemtra_sr_gpon: 0, // trong DB query lai
          bo_qua_capnhat_tb: this.PHAILAM('BO_QUA_CAPNHAT_TB') ? '1' : '0',
          kiemtra_lienhe_nghiemthu_bh: 0,
          kieugoi_id: this.kieugoi_id,
          ghichu_th: this.GhiChuTH,
          donvi_id: this.$root.token.getDonViID(),
          ma_nd: this.$root.token.getUserName(),
          nhomton_id: this.nhomton_id,
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_trangthai_bh: JSON.stringify(this.ds_tt),
          ds_nguyennhan_bh: JSON.stringify(this.changeKeysToUpper(this.ds_nn)),
          ds_ton_bh: JSON.stringify(this.ds_ton),
          ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
          nghiemthu: this.PHAILAM('CHECK_GIAOPHIEU_199_NGHIEMTHU') ? 1 : 0,
          ngay_ht: this.dtpNgayTH,
          ngay_xn: moment(this.dtpNgayXN.value, 'DD/MM/YYYY HH:mm A').format('YYYY-MM-DD'),
          tudong_giaophieu_duyet_capvt: this.PHAILAM('TUDONG_GIAOPHIEU_DUYET_CAPVT') ? '1' : '0',
          tudong_giaophieu_duyet_thuhoivt: this.PHAILAM('TUDONG_GIAOPHIEU_DUYET_THUHOIVT') ? '1' : '0',
          tudong_giaophieu_quyettoan_vt: this.PHAILAM('TUDONG_GIAOPHIEU_QUYETTOAN_VT') ? '1' : '0',
          tudong_giaophieu_yc_nhapcap: this.PHAILAM('TUDONG_GIAOPHIEU_YC_NHAPCAP') ? '1' : '0',
          gui_sms_khi_hoancong: this.PHAILAM('GUI_SMS_KHI_HOANCONG') ? '1' : '0',
          dienthoailh: this.DienThoaiLH
        }
        console.log(12345667)
        // P1: lam_hoac_boqua = -1, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
        this.Capnhat_trangthai_baohongbc(this.baohong_id,JSON.stringify(this.changeKeysToUpper(this.ds_tt)),this.NhomNN.value,JSON.stringify(this.changeKeysToUpper(this.ds_nn)));
        let kqnt = await this.fn_ntbh_nghiemthu(ds_param)
        let result = ''
        try {
          result = JSON.parse(kqnt.js)
        } catch (e) {
          this.$toast.error(kqnt)
          return
        }
         //#region Đẩy sang DB KHDN
        let dsTT_KHDN = await this.Lay_ds_phieu_phoihop_khdn(this.PHIEU_ID)
        if (dsTT_KHDN != null && dsTT_KHDN.length > 0) {
              console.log(2222222222)
              console.log(dsTT_KHDN[0])
             if (dsTT_KHDN[0].giao_vip.toString() != "0")// --> Cập nhật phiếu KHDN
               {
                 var dem = this.Hoanthanh_bh_giaophieu_ph(100,this.baohong_id)
                 if (dem == null || dem.toString() != "ok")
                 {
                   this.$toast.error("Lỗi đẩy dữ liệu KHDN! " + dem);
                   return;
                 }
             }
        }
         
        if (result.ERROR_CODE == 1) {
          // loi khi valid KIEMTRA_PHIEU
          this.$toast.success(result.MESSAGE)
          this.HienThiDanhSach();
          this.Clear()
          return
        }
        if (result.MESSAGE.startsWith('LAM_HOAC_BOQUA|')) {
          this.$bvModal
            .msgBoxConfirm(result.MESSAGE.replace('LAM_HOAC_BOQUA|', ''), {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => { 
              if (v) {
                // P2: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
                ds_param.lam_hoac_boqua = 1
                ds_param.kiemtra_lienhe_nghiemthu_bh = 0
                ds_param.nghiemthu = 0
               
                kqnt = await this.fn_ntbh_nghiemthu(ds_param)
                try {
                  result = JSON.parse(kqnt.js)
                } catch (e) {
                  await this.$toast.error(kqnt)
                  return
                }
                if (result.MESSAGE.includes('Bạn có muốn tiếp tục không')) {
                  this.$bvModal
                    .msgBoxConfirm(result.MESSAGE, {
                      title: '',
                      size: 'sm',
                      okTitle: 'Đồng ý',
                      cancelTitle: 'Hủy'
                    })
                    .then(async (v) => {
                      if (v) {
                        // P4: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 1, nghiemthu = 0
                        ds_param.lam_hoac_boqua = 1
                        ds_param.kiemtra_lienhe_nghiemthu_bh = 1
                        ds_param.nghiemthu = 0
                        kqnt = await this.fn_ntbh_nghiemthu(ds_param)
                        try {
                          result = JSON.parse(kqnt.js)
                        } catch (e) {
                          this.$toast.error(kqnt)
                          return
                        }
                        if (result.MESSAGE == 'Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng?') {
                          this.$bvModal
                            .msgBoxConfirm('Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng ?', {
                              title: '',
                              size: 'sm',
                              okTitle: 'Đồng ý',
                              cancelTitle: 'Hủy'
                            })
                            .then(async (v) => {
                              if (v) {
                                // P5: lam_hoac_boqua = 1, kiemtra_lienhe_nghiemthu_bh = 1, nghiemthu = 1
                                ds_param.lam_hoac_boqua = 1
                                ds_param.kiemtra_lienhe_nghiemthu_bh = 1
                                ds_param.nghiemthu = 1
                                kqnt = await this.fn_ntbh_nghiemthu(ds_param)
                                try {
                                  result = JSON.parse(kqnt.js)
                                } catch (e) {
                                  this.$toast.error(kqnt)
                                  return
                                }
                                if (result.ERROR_CODE == 1) {
                                  this.$toast.success(result.MESSAGE)
                                  await this.HienThiDanhSach()
                                } else {
                                  this.$toast.error(result.MESSAGE)
                                }
                              }
                            })
                        }
                      }
                    })
                }
              } else {
                // P3: lam_hoac_boqua = 0, kiemtra_lienhe_nghiemthu_bh = 0, nghiemthu = 0
                ds_param.lam_hoac_boqua = 0
                ds_param.kiemtra_lienhe_nghiemthu_bh = 0
                ds_param.nghiemthu = 0
                kqnt = await this.fn_ntbh_nghiemthu(ds_param)
                try {
                  result = JSON.parse(kqnt.js)
                  if (result.ERROR_CODE == 1) {
                    this.$toast.success(result.MESSAGE)
                    await this.HienThiDanhSach()
                  } else {
                    this.$toast.error(result.MESSAGE)
                  }
                } catch (e) {
                  this.$toast.error(kqnt)
                }
              }
            })
        } else {
          this.$toast.error(result.MESSAGE) // loi khi valid GIAOPHIEU_BAOHONG_CATCHUYEN
          return
        }
      } catch (ex) {
        this.$toast.error('' + ex.toString())
      }
    },
    async fn_ntbh_hoanthanh_nhieuphieu(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_hoanthanh_nhieuphieu(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    menuContextItems() {
      let menus = [...this.menuItems];
      let data = [];
      for (let menu of menus) {
        if (!this.btnVisible.tsmGiuPhieu && menu.id === 'dsbhGiuPhieu') {
          continue;
        }
        if (!this.btnVisible.tsmHuyGiuPHieu && menu.id === 'dsbhHuyGiuPhieu') {
          continue;
        }
        data.push(menu);
      }
      return data;
    },
    contextMenuClick(args) {
      if (args.item.id == "dsbhGiuPhieu") {
        this.HUY_GIUPHIEU(1);
      } else {
        this.HUY_GIUPHIEU(2);
      }
      //let data = args.rowInfo.rowData;
      console.log(args.rowInfo)
      //this.$emit(args.item.id, data);
    },
    async HoanThienNhieuPhieu() {
      try {
        this.loading(true)
        let ds_hoancong_para =
        {
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          nghiemthu: 0,
          kt_tsl_tram_tg: this.PHAILAM('KT_TSL_TRAM_TG') ? '1' : '0',
          dichvuvt_id: this.DichVuVT.value,
          hdtb_id: -1,
          thuebao_id: this.thuebao_id,
          daucuoi_id: 0,
          bo_qua_dokiem: this.PHAILAM('BO_QUA_DOKIEM') ? '1' : '0',
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip_cn: '',
          nhanvien_id: this.$root.token.getNhanVienID(),
          att_adsl: this.ttnd_att_adsl,
          att_gpon: this.ttnd_att_gpon,
          snr_adsl: this.ttnd_snr_adsl,
          kqxl_id: this.KQXL.value,
          ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
          ds_para_ktr_phieu: this.dtList_dang_chon ? JSON.stringify(this.changeKeysToUpper(this.dtList_dang_chon)) : [],
          kieugoi_id: this.kieugoi_id,
          huonggiao_id: this.huonggiao_id,
          ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        }
        this.ds_tt = []
        this.TaoDuLieu_tinhtrang()
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          nhanvien_id: this.$root.token.getNhanVienID(),
          nghiemthu: 0,
          js_dgvdanhsach: JSON.stringify(this.dtList_chon),
          kieugoi_id: this.kieugoi_id,
          kqxl_id: this.KQXL.value,
          ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
          check_giaophieu_199_nghiemthu: this.PHAILAM('CHECK_GIAOPHIEU_199_NGHIEMTHU') ? '1' : '0',
          lam_hoac_boqua: -1,
          kt_tsl_nt: this.PHAILAM('KT_TSL_NT') ? '1' : '0',
          bo_qua_dokiem: this.PHAILAM('BO_QUA_DOKIEM') ? '1' : '0',
          dichvuvt_id: this.DichVuVT.value,
          ds_hoancong_para: JSON.stringify(ds_hoancong_para),
          kiemtra_srgp: this.PHAILAM('KIEMTRA_SRGP') ? '1' : '0',
          tudong_giaophieu_duyet_capvt: this.PHAILAM('TUDONG_GIAOPHIEU_DUYET_CAPVT') ? '1' : '0',
          tudong_giaophieu_duyet_thuhoivt: this.PHAILAM('TUDONG_GIAOPHIEU_DUYET_THUHOIVT') ? '1' : '0',
          tudong_giaophieu_quyettoan_vt: this.PHAILAM('TUDONG_GIAOPHIEU_QUYETTOAN_VT') ? '1' : '0',
          tudong_giaophieu_yc_nhapcap: this.PHAILAM('TUDONG_GIAOPHIEU_YC_NHAPCAP') ? '1' : '0',
          gui_sms_khi_hoancong: this.PHAILAM('GUI_SMS_KHI_HOANCONG') ? '1' : '0',
          bo_qua_capnhat_tb: this.PHAILAM('BO_QUA_CAPNHAT_TB') ? '1' : '0',
          nguoi_cn: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip_cn: '',
          ghichu_th: this.GhiChuTH,
          ngay_ht: this.dtpNgayTH,
          ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          att_adsl: this.ttnd_att_adsl,
          att_gpon: this.ttnd_att_gpon,
          snr_adsl: this.ttnd_snr_adsl,
          kt_tsl_tram_tg: this.PHAILAM('KT_TSL_TRAM_TG') ? '1' : '0', //
          dokiem_para: JSON.stringify(this.init_ds_cbonguyennhan()),
          ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
          kiemtra_sr_gpon: '-1', // trong DB query lai, khong can truyen
          ma_nd: this.$root.token.getUserName(),
          donvi_id: this.$root.token.getDonViID(),
          dienthoailh: this.DienThoaiLH,
          nhomton_id: this.nhomton_id,
          ngay_xn: moment(this.dtpNgayXN.value, 'DD/MM/YYYY HH:mm A').format('YYYY-MM-DD'),
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_tinhtrang_bh: this.ds_tt ? JSON.stringify(this.changeKeysToUpper(this.ds_tt)) : [],
          ds_nguyennhan_bh: this.ds_nn ? JSON.stringify(this.changeKeysToUpper(this.ds_nn)) : [],
          ds_ton_bh: this.ds_ton ? JSON.stringify(this.changeKeysToUpper(this.ds_ton)) : [],
          ds_para_ktr_phieu: this.dtList_dang_chon ? JSON.stringify(this.changeKeysToUpper(this.dtList_dang_chon)) : []
        }
        // P1: nghiemthu = 0
        let result = ''
        this.$bvModal
          .msgBoxConfirm('Bạn chắc chắn muốn nghiệm thu kết quả báo hỏng?', {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              // P2: nghiemthu = 1, lam_hoac_boqua = -1
              ds_param.nghiemthu = 1
              ds_param.lam_hoac_boqua = -1
              //Cập nhật trạng thái nghiệm thu nhiều phiếu
              const jsonBaohong = []
              for (let i = 0; i < this.dtList_chon.length; i++) {
                let row = {};
                row.BAOHONG_ID = this.baohong_id;
                jsonBaohong.push(row);
              }
              console.log('111 Cập nhật phiếu KHDN')
              console.log(jsonBaohong)
              this.Capnhat_trangthai_baohongbc_v2(JSON.stringify(this.changeKeysToUpper(jsonBaohong)),JSON.stringify(this.changeKeysToUpper(this.ds_tt)),this.NhomNN.value,JSON.stringify(this.changeKeysToUpper(this.ds_nn)));
              let kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(ds_param)
              try {
                result = JSON.parse(kqnt.js)
              } catch (e) {
                this.$toast.error(kqnt)
                return
              }
               //#region Đẩy sang DB KHDN
              let dsTT_KHDN = await this.Lay_ds_phieu_phoihop_khdn(this.PHIEU_ID)
              if (dsTT_KHDN != null && dsTT_KHDN.length > 0) {
                  console.log(11111111111)
                  console.log(dsTT_KHDN[0])
                  if (dsTT_KHDN[0].giao_vip.toString() != "0")// --> Cập nhật phiếu KHDN
                    {
                      var dem = this.Hoanthanh_bh_giaophieu_ph_v2(100,JSON.stringify(jsonBaohong))
                      if (dem == null || dem.toString() != "ok")
                      {
                        this.$toast.error("Lỗi đẩy dữ liệu KHDN! " + dem);
                        return;
                      }
                  }
              }
              
              if (result.MESSAGE.startsWith('LAM_HOAC_BOQUA|')) {
                this.$bvModal
                  .msgBoxConfirm(result.MESSAGE.replace('LAM_HOAC_BOQUA|', ''), {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy'
                  })
                  .then(async (v) => {
                    if (v) {
                      // P4: nghiemthu = 1, lam_hoac_boqua = 1
                      ds_param.nghiemthu = 1
                      ds_param.lam_hoac_boqua = 1
                      kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(ds_param)
                    } else {
                      // P3: nghiemthu = 1, lam_hoac_boqua = 0
                      ds_param.nghiemthu = 1
                      ds_param.lam_hoac_boqua = 0
                      kqnt = await this.fn_ntbh_hoanthanh_nhieuphieu(ds_param)
                    }
                    try {
                      result = JSON.parse(kqnt.js)
                      if (result.ERROR_CODE == 1) {
                        this.$toast.success("Nghiệm thu báo hỏng thành công!")
                        this.HienThiDanhSach()
                        this.Clear()
                      } else {
                        this.$toast.error(result.MESSAGE)
                      }
                    } catch (e) {
                      this.$toast.error(kqnt)
                      return
                    }
                  })
              } else {
                try {
                  result = JSON.parse(kqnt.js)
                  if (result.ERROR_CODE == 1) {
                    this.$toast.success("Nghiệm thu báo hỏng thành công!")
                    this.HienThiDanhSach()
                    this.Clear()
                  } else {
                    this.$toast.error(result.MESSAGE)
                  }
                } catch (e) {
                  this.$toast.error(kqnt)
                  return
                }
              }
            }
          })
      } catch (ex) {
        this.$toast.error('' + ex.toString())
      } finally {
        this.loading(false)
      }
    },
    chkChoXL_CheckedChanged() {
      this.HienThiDanhSach()
    },
    tsbtnKQ_DoKiem_Click() {
      if (this.baohong_id != 0) {
        this.$refs.doKiem.showModal()
      } else this.$toast.error('Bạn chưa chọn phiếu!')
    },
    async tsbtnCapNhatKQ_Click() {
      if (this.dtList.length <= 0) {
        this.$toast.error('Không có dữ liệu để thực hiện !')
        return
      }
      if (this.KQXL.value == 0) {
        if (this.ds_nguyennhan_id.length == 0) {
          this.$toast.warning('Hãy nhập nguyên nhân hỏng!')
          return;
        }
      } else {
        if (this.ds_lydoton_id.length == 0) {
          this.$toast.warning('Hãy nhập lý do tồn!')
          return;
        }
      }
      try {
        this.loading(true)
        this.ds_tt = []
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          kieugoi_id: this.kieugoi_id,
          thuebao_id: this.thuebao_id,
          kqxl_id: this.KQXL.value,
          ma_tb: this.vma_tb,
          ds_lydoton: this.init_ds_lydoton() ? JSON.stringify(this.changeKeysToUpper(this.init_ds_lydoton())) : [],
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          ghichu_th: this.GhiChuTH,
          nhanvien_id: this.$root.token.getNhanVienID(),
          donvi_id: this.$root.token.getDonViID(),
          ma_nd: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip: await this.$root.token.getIP(),
          ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          nhomton_id: this.nhomton_id,
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_trangthai_bh: this.ds_tt ? JSON.stringify(this.changeKeysToUpper(this.ds_tt)) : [],
          ds_nguyennhan_bh: this.ds_nn ? JSON.stringify(this.changeKeysToUpper(this.ds_nn)) : [],
          ds_ton_bh: this.ds_ton ? JSON.stringify(this.changeKeysToUpper(this.ds_ton)) : [],
          ds_para_ktr_phieu: this.dtList_dang_chon ? JSON.stringify(this.changeKeysToUpper(this.dtList_dang_chon)) : []
        }
        let kqnt = await this.fn_ntbh_capnhatkq(ds_param)
        if (kqnt.result == 0) return
        this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
        await this.HienThiDanhSach();
        this.selectRowsChon()
      } catch (ex) {
        this.$toast.error(ex)
      } finally {
        this.loading(false)
      }
    },
    async fn_ntbh_capnhatkq(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_capnhatkq(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    async tsbtnCapNhatNhieuPhieu_Click() {
      if (!this.tsbtnCapNhatNhieuPhieu.visible) return
      if (this.dtList.length <= 0) {
        this.$toast.error('Không có dữ liệu để thực hiện !')
        return
      }
      try {
        if (this.dtList_dang_chon.length <= 1) {
          this.tsbtnCapNhatKQ_Click()
        } else {
          if (await this.CapNhatNhieuPhieu()) {
            await this.HienThiDanhSach()
            this.$toast.success('Cập nhật dữ liệu thành công!')
          }
        }
      } catch (ex) {
        this.$toast.error(ex)
      }
    },
    async CapNhatNhieuPhieu() {
      try {
        this.loading(true)
        if (this.KiemTra_CapNhatKQ(false)) {
          // ds_nn co vphieu_id, ds_ton co vbaohong_id rieng cho tung ban ghi trong ds chon
          // DB confirm khong can => truyen chung cho ca mang ds chon
          this.ds_tt = []
          await this.TaoDuLieu_nguyennhan(true)
          this.TaoDuLieu_baoton(true)
          let ds_param = {
            js_dschon: JSON.stringify(this.dtList_dang_chon),
            kieugoi_id: this.kieugoi_id,
            thuebao_id: this.thuebao_id,
            kqxl_id: this.KQXL.value,
            ma_tb: this.vma_tb,
            ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
            baohong_id: this.baohong_id,
            phieu_id: this.PHIEU_ID,
            ghichu_th: this.GhiChuTH,
            nhanvien_id: this.$root.token.getNhanVienID(),
            donvi_id: this.$root.token.getDonViID(),
            ma_nd: this.$root.token.getUserName(),
            may_cn: this.$root.token.getNguoiDungID(),
            ip: await this.$root.token.getIP(),
            ngay_cn: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            nhomton_id: this.nhomton_id,
            sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
            ds_trangthai_bh: this.ds_tt ? JSON.stringify(this.changeKeysToUpper(this.ds_tt)) : [],
            ds_nguyennhan_bh: this.ds_nn ? JSON.stringify(this.changeKeysToUpper(this.ds_nn)) : [],
            ds_ton_bh: this.ds_ton ? JSON.stringify(this.changeKeysToUpper(this.ds_ton)) : [],
            ds_para_ktr_phieu: this.dtList_dang_chon ? JSON.stringify(this.changeKeysToUpper(this.dtList_dang_chon)) : []
          }
          let kqnt = await this.fn_ntbh_capnhatkq_nhieuphieu(ds_param)
          try {
            let result = JSON.parse(kqnt.js)
            if (kqnt.result == 0) {
              if (result.ERROR_CODE == 0) {
                this.$toast.error(result.MESSAGE)
              }
              return false
            }
            return true
          } catch (e) {
            this.$toast.error(kqnt)
            return false
          }
        } else {
          return false
        }
      } catch (e) {
        console.log(e)
        return false
      } finally {
        this.loading(false)
      }
    },
    async fn_ntbh_capnhatkq_nhieuphieu(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_capnhatkq_nhieuphieu(params)
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    KiemTra_CapNhatKQ(nghiemthu) {
      if (!this.dtList.length) {
        this.$toast.warning('Không có thuê bao báo hỏng')
        return false
      }
      if (!this.PHAILAM('BO_QUA_GIAOVIEC') && this.kieugoi_id != TRANGTHAI_BH.THEODOI_CHO_HT) {
        if (!this.dtList.length && this.kieugoi_id != 3 && this.kieugoi_id != NET_BAOHONG.TTBH_XL_XONG && this.kieugoi_id != 9) {
          this.$toast.warning('Chưa giao việc cho nhân viên')
          return false
        }
      }
      if (this.kieugoi_id != 3 && this.kieugoi_id != TRANGTHAI_BH.THEODOI_CHO_HT) {
        if (this.KQXL.value == 0) {
          // kiểm tra nguyên nhân đã chọn
          if (!this.ds_nguyennhan_id.length) {
            this.$toast.warning('Hãy nhập nguyên nhân hỏng!')
            return false
          }
        } else {
          if (!this.ds_lydoton_id.length) {
            this.$toast.warning('Hãy nhập lý do tồn!')
            return false
          }
        }
      }
      if (this.ttbh_id == TRANGTHAI_BH.CHO_XULY && this.KQXL.value == 2) {
        this.$toast.warning("Không được chọn Kết quả xử lý là 'Đang chờ xử lý' đối với báo hỏng Chờ xử lý !")
        return false
      }
      if (this.kieugoi_id == TRANGTHAI_BH.THEODOI_CHO_HT && !nghiemthu) {
        if (!this.soNgay.value) {
          Message_Box.ShowWarning('Hãy nhập số ngày theo dõi')
          return false
        }
        if (!Number(this.soNgay.value)) {
          Message_Box.ShowWarning('Hãy nhập số ngày theo dõi định dạng số')
          return false
        }
        if (+this.soNgay.value <= 0) {
          Message_Box.ShowWarning('Hãy nhập số ngày theo dõi > 0')
          return false
        }
        if (this.GhiChuTH) {
          Message_Box.ShowWarning('Hãy nhập ghi chú theo dõi')
          return false
        }
      }
      if (this.dtpNgayXN.enabled) {
        if (moment(this.dtpNgayXN.value, 'DD/MM/YYYY HH:mm A') > new Date()) {
          this.$toast.error('Ngày xác nhận không thể lớn hơn ngày hiện tại!')
          return false
        }
      }
      return true
    },
    async tsbtnVatTuMoi_Click() {
      if (this.PHIEU_ID != 0) {
        if (this.CHOPHEP_GIUPHIEU == 1) {
          let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        }
        this.modelCapVatTu.phieu_id = this.PHIEU_ID
        this.modelCapVatTu.loaihd_id = 0
        this.modelCapVatTu.thuebao_id = this.thuebao_id
        this.modelCapVatTu.loaitb_id = this.LOAITB_ID
        this.modelCapVatTu.baohong_id = this.baohong_id
        this.$refs.popupVatTuTB.openDialog()
      } else {
        this.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    async btnGiaoPhieuTG_Click() {
      if (!this.btnGiaoPhieuTG.visible || !this.btnGiaoPhieuTG.enabled) return
      if (this.CHOPHEP_GIUPHIEU == 1) {
        let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
        if (kqnt.result != 1) {
          try {
            let kt = JSON.parse(kqnt.js)
            if (kt.ERROR_CODE != 1) {
              this.$toast.error(kt.MESSAGE)
            }
          } catch (e) {
            this.$toast.error(kqnt)
          }
          return
        }
      }
      this.$refs.dlg_giaophieutramtg.show()
    },
    tsbtnGianDo_Click() {
      if (this.IS_USING_CABMAN_V2) {
        // let thongTinDauNoi = {
        //     thueBaoId: this.thuebao_id,
        //     kieu: 0
        // }
        // this.$refs.daunoithuebao.setData(thongTinDauNoi)
        // this.$refs.daunoithuebao.show()
        this.$bvModal.show('dandocap')
      } else {
        this.$refs.dlg_tracuumdf.show()
        this.$refs.ppTraCuuMDF.frmTraCuuMDF_Load(this.MaTB.trim())
      }
    },
    async tsbtnThayDoiTT_Click() {
      try {
        if (this.baohong_id == 0 || this.dtList.length <= 0) {
          this.$toast.error('Không có dữ liệu để thực hiện !')
          return
        }
        if (this.CHOPHEP_GIUPHIEU == 1) {
          let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        }
        this.$refs.popupThayDoiTTBH.openDialog()
      } catch (ex) {
        this.$toast.error('Lỗi: ' + ex)
      }
    },
    tsbtnKhaoSat_Click() {
      if (this.baohong_id > 0) {
        this.$refs.dlg_khaosatkh.show()
        this.$refs.ppKhaoSatKH.init(this.baohong_id, null, null, null)
      }
    },
    async fn_ntbh_chuyenttvt(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_chuyenttvt(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    async tsbtnChuyenTTVT_Click() {
      if (!this.tsbtnChuyenTTVT.visible || !this.tsbtnChuyenTTVT.enabled) return
      try {
        if (this.baohong_id == 0 || this.dtList.length <= 0) {
          this.$toast.error('Không có dữ liệu để thực hiện!')
          return
        }
        this.loading(true)
        this.ds_tt = []
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          nhanvien_id: this.$root.token.getNhanVienID(),
          capnhat: 0,
          ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
          kieugoi_id: this.kieugoi_id,
          thuebao_id: this.thuebao_id,
          kqxl_id: this.KQXL.value,
          ma_tb: this.vma_tb,
          ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
          ghichu_th: this.GhiChuTH,
          donvi_id: this.$root.token.getDonViID(),
          ma_nd: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip: '',
          ngay_cn: new Date()
            .toISOString()
            .slice(0, 10)
            .replace(/-/g, ''),
          nhomton_id: this.nhomton_id,
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_trangthai_bh: JSON.stringify(this.ds_tt),
          ds_nguyennhan_bh: JSON.stringify(this.changeKeysToUpper(this.ds_nn)), 
          ds_ton_bh: JSON.stringify(this.ds_ton),
          huonggiao_id: this.huonggiao_id
        }
        let kqnt = await this.fn_ntbh_chuyenttvt(ds_param)
        let result = ''
        try {
          result = JSON.parse(kqnt.js)
        } catch (e) {
          this.$toast.error(kqnt)
          return
        }
        if (result.ERROR_CODE == 0) return //  valid kiem tra phieu false
        this.$bvModal
          .msgBoxConfirm('Bạn chắc chắn muốn chuyển phiếu?', {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              ds_param.capnhat = 1
              kqnt = await this.fn_ntbh_chuyenttvt(ds_param)
              try {
                result = JSON.parse(kqnt.js)
              } catch (e) {
                this.$toast.error(kqnt)
                return
              }
              if (result.ERROR_CODE == 1) {
                this.$toast.success('Cập nhật dữ liệu thành công!')
                await this.HienThiDanhSach()
              } else {
                this.$toast.error(result.MESSAGE)
              }
            }
          })
      } catch (ex) {
        this.$toast.error(ex)
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    tsbtnDoKiem_Click() {
      this.showPort(0)
    },
    showPort(vkieu) {
      try {
        if (this.dtList.length <= 0) {
          this.$toast.error('Bạn hãy nhập mã thuê bao !')
          return
        }
        this.checkPortOnClick()
      } catch (ex) {
        this.$toast.error(ex)
      }
    },
    tsbtnExcel_Click() {
      if (this.dtList.length == 0) {
        this.$toast.error('Không có dữ liệu để xuất Excel !')
        return
      }
      let worksheet = XLSX.utils.json_to_sheet(this.dtList)
      let workbook = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1')
      XLSX.writeFile(workbook, 'DS_phieubaohong.xlsx')
    },
    async fn_lay_ttbh_id_theo_phieu_id(phieu_id) {
      let result = []
      try {
        let params = {
          phieu_id: phieu_id
        }
        let response = await API.fn_lay_ttbh_id_theo_phieu_id(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async btnTraPhieu_Click() {
      if (!this.tsbtnTraPhieu.visible || !this.tsbtnTraPhieu.enabled) return
      try {
        this.loading(true)
        if (this.KHONG_TRAPHIEU_DV_TIEPNHAN == 1) {
          let dtKT = await this.fn_lay_ttbh_id_theo_phieu_id(this.PHIEU_ID)
          if (String(dtKT[0].ttbh_id) == '1') {
            this.$toast.error('Phiếu giao từ bộ phận tiếp nhận. Không thể trả phiếu!')
            return
          }
        }
        if (this.dtList.length <= 0) {
          this.$toast.error('Không có thuê bao báo hỏng!')
          return
        }
        if (this.kieugoi_id == 19) {
          this.$refs.ppTraPhieuBaoHong.initPopup(this.MaTB.trim(), '', this.kieugoi_id)
          this.$refs.dlg_traphieubaohong.show()
        } else {
          this.$refs.ppTraPhieuBaoHong.initPopup(this.MaTB.trim(), '')
          this.$refs.dlg_traphieubaohong.show()
        }
        await this.HienThiDanhSach()
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnBaoTon_Click() {
      if (!this.tsbtnBaoTon_BH.visible || !this.tsbtnBaoTon_BH.enabled) return
      try {
        if (this.baohong_id == 0 || this.dtList.length <= 0) {
          this.$toast.error('Không có dữ liệu để thực hiện báo tồn!')
          return
        }
        if (this.KQXL.value == null) {
          this.$toast.error('Hãy chọn kết quả xử lý')
          return
        }
        if (this.ds_lydoton_id.length == 0) {
          this.$toast.error('Hãy chọn lý do tồn')
          return
        }
        this.$bvModal
          .msgBoxConfirm('Bạn chắc chắn muốn báo tồn?', {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              try {
                this.loading(true)
                this.ds_tt = []
                await this.TaoDuLieu_nguyennhan(true)
                this.TaoDuLieu_baoton(true)
                let ds_param = {
                  kieugoi_id: this.kieugoi_id,
                  thuebao_id: this.thuebao_id,
                  kqxl_id: this.KQXL.value,
                  ma_tb: this.vma_tb,
                  ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
                  baohong_id: this.baohong_id,
                  phieu_id: this.PHIEU_ID,
                  ghichu_th: this.GhiChuTH,
                  nhanvien_id: this.$root.token.getNhanVienID(),
                  donvi_id: this.$root.token.getDonViID(),
                  ma_nd: this.$root.token.getUserName(),
                  may_cn: this.$root.token.getNguoiDungID(),
                  ip: '',
                  ngay_cn: new Date()
                    .toISOString()
                    .slice(0, 10)
                    .replace(/-/g, ''),
                  nhomton_id: this.nhomton_id,
                  sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
                  ds_trangthai_bh: JSON.stringify(this.ds_tt),
                  ds_nguyennhan_bh: JSON.stringify(this.changeKeysToUpper(this.ds_nn)),
                  ds_ton_bh: JSON.stringify(this.ds_ton),
                  ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon)
                }
                let kqnt = await this.fn_ntbh_baoton(ds_param)
                try {
                  let res = JSON.parse(kqnt.js)
                  if (kqnt.result == 0) {
                    if (res.ERROR_CODE == 0) {
                      this.$toast.error(res.MESSAGE)
                    }
                    return
                  }
                  if (res.ERROR_CODE == 1) {
                    this.$toast.success(res.MESSAGE)
                    await this.HienThiDanhSach()
                  } else {
                    this.$toast.error(res.MESSAGE)
                  }
                } catch (e) {
                  this.$toast.error(kqnt)
                }
              } catch (error) {
                this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
              } finally {
                this.loading(false)
              }
            }
          })
      } catch (ex) {
        console.log(ex)
        this.$toast.error(ex)
      }
    },
    async fn_ntbh_baoton(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_baoton(params)
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    tsbtnNhacViec_Click() {
      this.frmNhacViecBHInput.loaidv_nhan_id = '0'
      this.frmNhacViecBHInput.baohong_id = this.baohong_id
      this.frmNhacViecBHInput.kieugoi_id = this.kieugoi_id
      this.frmNhacViecBHInput.phieu_id = this.PHIEU_ID
      this.frmNhacViecBHInput.thuebao_id = this.thuebao_id
      this.frmNhacViecBHInput.kieu_nhac = 1 // //TTVT nhắc
      this.frmNhacViecBHInput.ma_tb = this.vma_tb
      this.$refs.dialogfrmNhacViecBH.openDialog()
    },
    async tsbtnHenKH_Click() {
      let Quyen_Duoc_hen = 0
      let dsQuyenDV = await API.LAY_DS_THAMSO_MACDINH_DV({
        donvi_id: this.$root.token.getDonViID(),
        kieu_id: 4
      })
      let det = dsQuyenDV.filter((x) => x['ma_ts'].toString() == 'TTKD_CAPNHAT_HEN' && x['donvi_id'] == this.$root.token.getDonViID())
      if (det.length > 0) {
        let row = det[0]
        if (row['ten_ts'].toString().trim() == '1') Quyen_Duoc_hen = 1
      }
      if (this.XACMINH_HEN_BAOHONG == -1) {
        this.frmHenKHInput.baohong_id = this.baohong_id
        this.frmHenKHInput.phieu_id = this.PHIEU_ID
        this.frmHenKHInput.Quyen_Duoc_hen = Quyen_Duoc_hen
        this.$refs.reffrmHenKH.openDialog()
      } else {
        this.frmHenKHV2Input.baohong_id = this.baohong_id
        this.frmHenKHV2Input.phieu_id = this.PHIEU_ID
        this.frmHenKHV2Input.Quyen_Duoc_hen = Quyen_Duoc_hen == 1 ? 2 : Quyen_Duoc_hen //Truyền = 2 là quyền hẹn TTVT
        this.$refs.reffrmHenKHV2.openDialog()
      }
    },
    CloseHenKH() {
      this.HienThiDanhSach();
    },
    async tsbtnLichSuHen_Click() {
      try {
        if (this.CHOPHEP_GIUPHIEU == 1) {
          let kqnt = await this.KIEMTRA_PHIEU('0', this.PHIEU_ID, 0)
          if (kqnt.result != 1) {
            try {
              let kt = JSON.parse(kqnt.js)
              if (kt.ERROR_CODE != 1) {
                this.$toast.error(kt.MESSAGE)
              }
            } catch (e) {
              this.$toast.error(kqnt)
            }
            return
          }
        }
        if (this.baohong_id == -1 || this.baohong_id == 0) return
        this.$refs.popupLichSu_HenBH.openDialog()
      } catch (ex) {
        this.$toast.error(ex)
      }
    },
    async tsbtnChuyenTTDH_Click() {
      if (!this.tsbtnChuyenTTDH.visible || !this.tsbtnChuyenTTDH.enabled) return
      try {
        if (this.baohong_id == 0 || this.dtList.length <= 0) {
          this.$toast.error('Không có dữ liệu để thực hiện!')
          return
        }
        if (this.KQXL.value == null) {
          this.$toast.error('Hãy chọn kết quả xử lý')
          return
        }
        if (this.ds_lydoton_id.length == 0) {
          this.$toast.error('Hãy chọn lý do tồn')
          return
        }
        this.loading(true)
        this.ds_tt = []
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          nhanvien_id: Number(this.$root.token.getNhanVienID()),
          ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
          da_nhap_nd: 0,
          kieugoi_id: this.kieugoi_id,
          thuebao_id: this.thuebao_id,
          kqxl_id: Number(this.KQXL.value),
          ma_tb: this.vma_tb,
          ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
          ghichu_th: this.GhiChuTH,
          donvi_id: Number(this.$root.token.getDonViID()),
          ma_nd: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip: '',
          ngay_cn: new Date()
            .toISOString()
            .slice(0, 10)
            .replace(/-/g, ''),
          nhomton_id: this.nhomton_id,
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_trangthai_bh: JSON.stringify(this.ds_tt),
          ds_nguyennhan_bh: JSON.stringify(this.changeKeysToUpper(this.ds_nn)),
          ds_ton_bh: JSON.stringify(this.ds_ton),
          huonggiao_id: this.huonggiao_id,
          noidung_giao: ''
        }
        console.log(this.KQXL.value)
        console.log(this.nhomton_id)
        let kqnt = await this.sp_ntbh_chuyenttdh(ds_param)
        try {
          let result = JSON.parse(kqnt.js)
          if (result.ERROR_CODE == 0) {
            if (result.MESSAGE != undefined && result.MESSAGE != '') {
              this.$toast.error(result.MESSAGE)
            }
            return
          }
          this.$refs.ppNoiDungGiao.init()
          this.$refs.dlg_ndgiao.show()
        } catch (e) {
          this.$toast.error(e)
        }
      } catch (ex) {
        this.$toast.error(ex.message)
        console.log(ex)
      } finally {
        this.loading(false)
      }
    },
    async dlg_ndgiao_giao_phieu(nd_giao) {
      console.log(111111111111)
      console.log(nd_giao)
      try {
        this.loading(true)
        this.ds_tt = []
        await this.TaoDuLieu_nguyennhan(true)
        this.TaoDuLieu_baoton(true)
        let ds_param = {
          baohong_id: this.baohong_id,
          phieu_id: this.PHIEU_ID,
          nhanvien_id: this.$root.token.getNhanVienID(),
          ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
          da_nhap_nd: 1,
          kieugoi_id: this.kieugoi_id,
          thuebao_id: this.thuebao_id,
          kqxl_id: this.KQXL.value,
          ma_tb: this.vma_tb,
          ds_lydoton: JSON.stringify(this.init_ds_lydoton()),
          ghichu_th: this.GhiChuTH,
          donvi_id: this.$root.token.getDonViID(),
          ma_nd: this.$root.token.getUserName(),
          may_cn: this.$root.token.getNguoiDungID(),
          ip: '',
          ngay_cn: new Date()
            .toISOString()
            .slice(0, 10)
            .replace(/-/g, ''),
          nhomton_id: this.nhomton_id,
          sonngay: this.soNgay.visible ? Number(this.soNgay.value) : 0,
          ds_trangthai_bh: JSON.stringify(this.ds_tt),
          ds_nguyennhan_bh: JSON.stringify(this.changeKeysToUpper(this.ds_nn)),
          ds_ton_bh: JSON.stringify(this.ds_ton),
          huonggiao_id: this.huonggiao_id,
          noidung_giao: nd_giao
        }
        console.log(ds_param)
        if(this.KQXL.value == 1 && this.nhomton_id == NHOMTON.MANG_VT_LOP_TREN){
          let kqnt = await this.sp_ntbh_chuyenttdh(ds_param)
          if (this.kieugoi_id == NET_BAOHONG.TTBH_XL_XONG || this.kieugoi_id == 9) {
            let dsTT_KHDN = await this.Lay_ds_phieu_phoihop_khdn(this.PHIEU_ID)
            if (dsTT_KHDN != null && dsTT_KHDN.length > 0) {
                let json_list_nn = JSON.stringify(this.changeKeysToUpper(dsTT_KHDN))
                let dem = await this.capnhat_giaophieu_ph(json_list_nn, this.LOAITB_ID == LoaiHinhTB.INTERNET_MYTV ? 2 : 1)
                if (dem == null || dem !== 'ok') {
                  this.$toast.error('Lỗi đẩy dữ liệu KHDN! ' + dem)
                  return
                }
            }
          }
          try {
            let result = JSON.parse(kqnt.js)
            if (result.ERROR_CODE != 1) {
              if (result.MESSAGE != undefined && result.MESSAGE != '') {
                this.$toast.error(result.MESSAGE)
              }
            } else {
              this.$toast.success('Cập nhật dữ liệu thành công!')
            }
            this.HienThiDanhSach()
          } catch (e) {
            this.$toast.error(e.message)
          }
        }
        
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      } finally {
        this.$refs.dlg_ndgiao.hide()
        this.loading(false)
      }
    },
    changeKeysToUpper(obj) {
      var key, upKey;
      for (key in obj) {
        if (obj.hasOwnProperty(key)) {
          upKey = key.toUpperCase();
          if (upKey !== key) {
            obj[upKey] = obj[key];
            delete (obj[key]);
          }
          // recurse
          if (typeof obj[upKey] === "object") {
            this.changeKeysToUpper(obj[upKey]);
          }
        }
      }
      return obj;
    },
    async sp_ntbh_chuyenttdh(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_para: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_chuyenttdh(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    getDataFromAPI(rs) {
      if (rs.data != null && rs.data.data != null && (typeof rs.data.data == 'number' || rs.data.data.length > 0)) {
        return rs.data.data
      }
      return null
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200'|| response.data.error === '0') {
          return response.data.data
      } else {
          console.log(response.data.error_code)
      }
      return []
  },
    async Lay_ds_phieu_phoihop_khdn(phieu_id) {
      try {
        let rs = await API.lay_ds_phieu_phoihop_khdn({
          phieu_id
        })
        let data = this.getDataFromAPI(rs)
        let format = 'YYYY-MM-DDT HH:mm:ss[Z]'
        if (data != null && data.length > 0) {
          data = data.map((obj) => {
            let tmp = Object.keys(obj).reduce((acc, key) => {
              acc[key.toUpperCase()] = obj[key]
              return acc
            }, {})
            tmp.HENSUATU = tmp.HENSUATU ? moment(tmp.HENSUATU, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            tmp.NGAYGIAO = tmp.NGAYGIAO ? moment(tmp.NGAYGIAO, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            tmp.HENSUADEN = tmp.HENSUADEN ? moment(tmp.HENSUADEN, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            tmp.NGAY_BH = tmp.NGAY_BH ? moment(tmp.NGAY_BH, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            tmp.NGAY_SD = tmp.NGAY_SD ? moment(tmp.NGAY_SD, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            tmp.THOIGIAN_CK = tmp.THOIGIAN_CK ? moment(tmp.THOIGIAN_CK, 'YYYY-MM-DD HH:mm:ss').format(format) : null
            return tmp
          })
          console.log(data)
          return data
        }
      } catch (e) {
        console.log(e)
      }
      return null
    },
    async capnhat_giaophieu_ph(vdstt_khdn, vkieu) {
      try {
        let rs = await API.capnhat_giaophieu_ph({
          vdstt_khdn,
          vkieu,
          tinhthicong_id: 100
        })
        return this.getDataFromAPI(rs)
      } catch (e) {
        console.log(e)
      }
      return null
    },
    async tsddbIn_Click() {
      try {
        if (!this.dtList.length) {
          this.$toast.error('Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! ')
          return
        }
        this.inBB.n_phieu_id = ''
        this.inBB.n_baohong_id = ''
        let isConfirm = await this.$bvModal.msgBoxConfirm(`Bạn có muốn in các phiếu thi công đã chọn không ?`, {
          title: 'Thông báo',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        if (isConfirm) {
          for (const item of this.dtList_dang_chon) {
            this.inBB.n_phieu_id += ',' + item.phieu_id
            this.inBB.n_baohong_id += ',' + item.baohong_id
          }
          if (this.inBB.n_phieu_id == '') {
            this.$toast.warning('Chưa có phiếu nào được chọn')
            return
          } else {
            this.inBB.n_phieu_id = this.inBB.n_phieu_id.substring(1)
            this.inBB.n_baohong_id = this.inBB.n_baohong_id.substring(1)
          }
        } else {
          this.inBB.n_phieu_id = this.PHIEU_ID.toString()
          this.inBB.n_baohong_id = this.baohong_id.toString()
        }
        if (this.inBB.n_phieu_id && this.inBB.n_baohong_id) {
          this.inBB.baohong = true
          this.inBB.huonggiao_id = this.huonggiao_id
          this.inBB.nvth_id = this.nhanvien_th_id
          this.$refs.popupInBB.showModal()
        }
      } catch (error) {
        this.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },
    lblSuCoTTVT_DoubleClick() {
      try {
        this.$refs.frmXemThongBaoSuCo.initData()
        this.$refs.frmXemThongBaoSuCo.showModal()
      } catch (e) {
        console.log(e)
      }
    },
    gridviewDanhSach_RowCellStyle(arg) {
      const { data, column } = arg
      const rgb = (color) => {
        return `rgb(${color})`
      }
      // From file design
      if (column.field == 'gio_conlai') {
        if (data.gio_conlai >= 0.5 && data.gio_conlai <= 1) {
          arg.cell.style.backgroundColor = 'Blue'
        }
        if (data.gio_conlai <= 0.5) {
          arg.cell.style.backgroundColor = 'Red'
        }
      }
      if (column.field == 'ma_tb') {
        if (data.gio_conlai >= 0.5 && data.gio_conlai <= 1) {
          arg.cell.style.backgroundColor = 'Blue'
        }
        if (data.gio_conlai <= 0.5) {
          arg.cell.style.backgroundColor = 'Red'
        }
      }
      if (column.field == 'goi_kt') {
        if (data.mau_ht) {
          let mau_ht = data.mau_ht.split(',') || []
          if (mau_ht.length == 3) {
            arg.cell.style.backgroundColor = rgb(mau_ht[0], mau_ht[1], mau_ht[2])
          } else {
            arg.cell.style.backgroundColor = rgb(255, 255, 255)
          }
        }
      }
      if (column.field == 'mucdo') {
        if (data.mau_ht_mucdo) {
          let mau_ht = data.mau_ht_mucdo.split(',') || []
          if (mau_ht.length == 3) {
            arg.cell.style.backgroundColor = rgb(mau_ht[0], mau_ht[1], mau_ht[2])
          } else {
            arg.cell.style.backgroundColor = rgb(255, 255, 255)
          }
        }
      }
      //nhapt Thêm màu cho phân loại khách hàng TEN_PLKH : 09/10/2017 (Update_date: Jun 17th 2022)
      if (column.field == 'ten_plkh') {
        if (data.mau_htpl) {
          let mau_ht_pl = data.mau_htpl.split(',') || []
          if (mau_ht_pl.length == 3) {
            arg.cell.style.backgroundColor = rgb(mau_ht_pl[0], mau_ht_pl[1], mau_ht_pl[2])
          } else {
            arg.cell.style.backgroundColor = rgb(255, 255, 255)
          }
        }
      }
      if (column.field == 'ma_tb') {
        if (data.hasOwnProperty('kenhtn_id')) {
          const kenhtn = data.kenhtn_id
          if (kenhtn) {
            if (kenhtn == KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO || kenhtn == KENHTN_BAOHONG.BH_LOICAP_QUANG) {
              arg.cell.style.backgroundColor = 'RoyalBlue'
            } else {
              arg.cell.style.backgroundColor = rgb(255, 255, 255)
              arg.cell.style.color = 'Black'
            }
            const GIO_CONLAI = data.gio_conlai
            if (kenhtn != KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO && kenhtn != KENHTN_BAOHONG.BH_XUAT_THEO_FILE) {
              if (GIO_CONLAI < 0) {
                arg.cell.style.backgroundColor = 'Red'
              } else if (GIO_CONLAI < 10) {
                arg.cell.style.backgroundColor = 'Aqua'
              }
            }
          }
        }
      }
      if (column.field == 'ten_tb') {
        if (data.hasOwnProperty('bh_lai')) {
          const bh_lai = data.bh_lai
          if (bh_lai == 1) arg.cell.style.backgroundColor = rgb(0, 191, 255)
          else {
            arg.cell.style.backgroundColor = rgb(255, 255, 255)
            arg.cell.style.color = 'Black'
          }
        }
      }
      if (this.HT_MAU_THEO_GIO_CONLAI == 1) {
        //DatabaseConstants.DB1 == "ADMIN_HCM"
        if (column.field == 'gio_conlai_t') {
          if (data.gio_conlai_t > 0 && data.gio_conlai < 1) {
            arg.cell.style.backgroundColor = 'Aqua'
          }
          if (data.gio_conlai_t < 0) {
            arg.cell.style.backgroundColor = 'Red'
          }
          if (data.dichvuvt_id == DichVuVienThong.TSL || data.dichvuvt_id == DichVuVienThong.MEGAWAN || data.dichvuvt_id == DichVuVienThong.METRONET) arg.cell.style.backgroundColor = 'Violet'
        }
        if (column.field == 'ma_tb') {
          if (data.gio_conlai > 0 && data.gio_conlai < 1) {
            arg.cell.style.backgroundColor = 'Aqua'
          }
          if (data.gio_conlai < 0) {
            arg.cell.style.backgroundColor = 'Red'
          }
          if (data.dichvuvt_id == DichVuVienThong.TSL || data.dichvuvt_id == DichVuVienThong.MEGAWAN || data.dichvuvt_id == DichVuVienThong.METRONET) arg.cell.style.backgroundColor = 'Violet'
        }
      }
    },
    async toolStripButton1_Click() {
      let helplink = SERVER_IMAGES.BASE_URL + 'hdsd/frmNghiemThuBH.html'
      let html = await this.getData(encodeURIComponent(helplink))
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH892.png').join(document.getElementById('frmNghiemThuBH892').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH1883.png').join(document.getElementById('frmNghiemThuBH1883').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH2028.png').join(document.getElementById('frmNghiemThuBH2028').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH3027.png').join(document.getElementById('frmNghiemThuBH3027').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH3729.png').join(document.getElementById('frmNghiemThuBH3729').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH4203.png').join(document.getElementById('frmNghiemThuBH4203').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH4436.png').join(document.getElementById('frmNghiemThuBH4436').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH4525.png').join(document.getElementById('frmNghiemThuBH4525').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH5549.png').join(document.getElementById('frmNghiemThuBH5549').src)
      html = html.split('frmNghiemThuBH.files/frmNghiemThuBH7147.png').join(document.getElementById('frmNghiemThuBH7147').src)
      $('#includedContent').html(html)
      this.$refs.dlg_trogiup.show()
    },
    async getData(url) {
      let result
      try {
        this.loading(true)
        let params = {
          url: url
        }
        let response = await API.get_data_by_url(params)
        if (response && response.data) {
          result = response.data
        }
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
      return result
    },
    async giaophieunv_success() {
      this.ds_NhanVien = await API.LAY_DS_NHANVIEN_THEO_PHIEU_ID({
        phieu_id: this.PHIEU_ID,
        kieu_id: 2
      })
      this.HienThiDSNV()
    },
    async tsbtnInPhieuTC_Click() {
      try {
        this.dsGiaTri = []
        let ma_tinh = this.$root.token.getMaTinh()
        if (this.dtList.length <= 0) {
          this.$toast.error('Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! ')
          return
        }
        if (ma_tinh == 'CSS_HCM') {
          this.dsGiaTri.push(this.baohong_id)
          this.$refs.XemBaoCao.XemNgay()
        } else {
          let vphieu_id = ''
          this.$bvModal
            .msgBoxConfirm('Bạn có muốn in các phiếu thi công đã chọn không?', {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            })
            .then(async (v) => {
              if (!v) {
                return
              }
              // Xử lý code ở đây
              if (this.dtList.length <= 0) {
                this.$toast.error('Chưa có danh sach thuê bao. Bạn hãy kiểm tra lại! ')
                return
              }
              console.log(11111111111)
              console.log(this.dtList_dang_chon)
              vphieu_id = this.dtList_dang_chon.map((x) => x.phieu_id).join()
              let donviId = this.$root.token.getDonViID()
              this.dsGiaTri.push(vphieu_id)
              this.dsGiaTri.push(donviId)
              console.log(33333333333)
              console.log(this.dsGiaTri)
              this.$refs.XemBaoCao.XemNgay()
            })
        }
      } catch (err) {
        this.$toast.error('Có lỗi : ' + err.message)
      }
    },
    async tsbtnHoanCong_Click() {
      let ds_param = {
        baohong_id: this.baohong_id,
        phieu_id: this.PHIEU_ID,
        nghiemthu: 0,
        kt_tsl_tram_tg: this.PHAILAM('KT_TSL_TRAM_TG') ? '1' : '0',
        dichvuvt_id: this.DichVuVT.value,
        hdtb_id: 0,
        thuebao_id: this.thuebao_id,
        daucuoi_id: 0,
        bo_qua_dokiem: this.PHAILAM('BO_QUA_DOKIEM') ? '1' : '0',
        nguoi_cn: this.$root.token.getUserName(),
        may_cn: this.$root.token.getNguoiDungID(),
        ip_cn: '',
        nhanvien_id: this.$root.token.getNhanVienID(),
        att_adsl: this.ttnd_att_adsl,
        att_gpon: this.ttnd_att_gpon,
        snr_adsl: this.ttnd_snr_adsl,
        kqxl_id: this.KQXL.value,
        ds_cbonguyennhan: JSON.stringify(this.init_ds_cbonguyennhan()),
        ds_para_ktr_phieu: JSON.stringify(this.dtList_dang_chon),
        kieugoi_id: this.kieugoi_id,
        huonggiao_id: this.huonggiao_id,
        ngay_cn: new Date()
          .toISOString()
          .slice(0, 10)
          .replace(/-/g, ''),
        ma_nd: this.$root.token.getUserName()
      }
      console.log('tsbtnHoanCong_Click_ds_param')
      console.log(ds_param)
      // P1: nghiemthu = 0
      try {
        this.loading(true)
        this.TaoDuLieu_tinhtrang()
        this.Capnhat_trangthai_baohongbc(this.baohong_id,JSON.stringify(this.changeKeysToUpper(this.ds_tt)),this.NhomNN.value,JSON.stringify(this.changeKeysToUpper(this.ds_nn)));
        let kqnt = await this.fn_ntbh_hoancong(ds_param)
        try {
          let kt = JSON.parse(kqnt.js)
          if (kt.ERROR_CODE != 1) {
            this.$toast.error(kt.MESSAGE)
            return
          }
        } catch (e) {
          this.$toast.error(kqnt)
          return
        }
      } finally {
        this.loading(false)
      }
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn cập nhật kết quả xử lý ?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            try {
              this.loading(true)
              ds_param.nghiemthu = 1
              let kqnt = await this.fn_ntbh_hoancong(ds_param)
              if (kqnt.result != 1) {
                try {
                  let kt = JSON.parse(kqnt.js)
                  if (kt.ERROR_CODE == 0) {
                    this.$toast.error(kt.MESSAGE)
                    return
                  }
                } catch (e) {
                  this.$toast.error(kqnt)
                  return
                }
              }
              this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
              await this.HienThiDanhSach()
            } catch (error) {
              this.$toast.error(error.message ? error.message : 'Đã có lỗi xảy ra')
            } finally {
              this.loading(false)
            }
          }
        })
    },
    async fn_ntbh_hoancong(ds_param) {
      let kqnt = null
      try {
        let params = {
          ds_param: JSON.stringify(ds_param)
        }
        let response = await API.fn_ntbh_hoancong(params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          kqnt = response.data.data
        } else {
          kqnt = response.data.message
        }
      } catch (e) {
        console.log(e)
        kqnt = e.data && e.data.message ? e.data.message : e.message
      }
      return kqnt
    },
    async checkPortOnClick() {
      this.checkPortDialog.isVisiable = true
      this.$refs.checkPort.show()
    },
    async checkPortOnClose() {
      this.checkPortDialog.isVisiable = false
    }
  }
}