<!--Thanh toán thành công : emit event : paymentSuccess-->
<!-- Tham khảo nút thanh toán ở module: src\modules\contract\setup\ChangeMegaWanConfig
 .html
 <ejs-dialog :enableResize='true'  :visible='false' ref='thanhtoanDialog' :position='position'
    :header='"THANH TOÁN HỢP ĐỒNG"' showCloseIcon='true' :target='targetTop' width='100%' :open="open_thanhToanPopUp">
    <keep-alive>
      <div class="popupComponentPayment">
        <component :is="userComponentModule"  ref='thanhToanPopUp' :ispopup="true" :magiaodich='txtMaGDTT'  v-bind="popupComponentAttr" v-on="popupComponentEvts" />
      </div>
    </keep-alive>
  </ejs-dialog>
  .vue
  tsbtnThanhToan_Click() {
      try {
        this.tsbtnThanhToan_Enabled=false
        this.loading(true)
        this.userComponentModule = thanhToan
        // goi su kien load lai ma giao dich
        this.txtMaGDTT= this.txtMaGD
        // tao component neu chua tao, show popup khi da goi xong ham khoi tao
        if(this.paymentloaded) this.$refs.thanhtoanDialog.show(true)
      } catch (err) {
        this.tsbtnThanhToan_Enabled= true
        console.log('tsbtnThanhToan_Click', err)
      } finally {
        this.tsbtnThanhToan_Enabled= true
        this.loading(false)
      }
    }
-->
<template src="./Payment.html"></template>
<style src="./Payment.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/select2/4.0.0/js/i18n/vi.js"></script>
<script></script>
<script>
import DataGridC from '@/components/Shared/DataGrid/index.vue'
import breadcrumb from '@/components/breadcrumb'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue'
import phieuTra from './components/PhieuTra.vue'
import tonPhieu from './components/TonPhieu.vue'
import selectPrintType from './components/SelectPrintType.vue'
import selectPrintPhieuChi from './components/SelectPrintPhieuChi.vue'
import uploadEProfile from '@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue'
import thongTinEmail from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'
// import popupTimKiemNhanVienThuCuoc from '@/modules/contract/setup/Payment/components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import inHopDong from '@/modules/print/InHopDong/InHopDong.vue'
import inPhieuThu from '@/modules/print/InPhieuThu/InPhieuThu.vue'
import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import moment from 'moment'
import PrintReport from '@/modules/print/InPhieuChi/PrintReport.vue'
import SelfManageEinvoice from '@/modules/Payment/SelfManageEinvoice/index.vue'
import DongBoLapMoiVina from '@/modules/contract/complete_profile/CompleteMobileInitProfile/DongBoLapMoiVina.vue'
// import eInvoice from '@/modules/Payment/eInvoice/index.vue'
import eInvoice from '@/modules/Payment/eInvoice/indexPayment.vue'
import eInvoice2 from '@/modules/Payment/eInvoice/index.vue'
import { TRANGTHAI_DONGBO } from '@/const/enums/index.js'
// import Vue from 'vue'
// import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
import { required, minLength, integer } from 'vuelidate/lib/validators'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
import { previewPrint } from '@/utils/util'
import PopupThongTinNgoaiTe from './components/PopupThongTinNgoaiTe/PopupThongTinNgoaiTe.vue'
export default {
  components: {
    breadcrumb,
    // gridView,
    // treegridView,
    moment,
    DatePicker,
    // DialogPlugin,
    phieuTra, // () => import('@/modules/contract/setup/Payment/components/PhieuTra.vue'),
    tonPhieu, // () => import('@/modules/contract/setup/Payment/components/TonPhieu.vue'),
    uploadEProfile, // : () => import('@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue'),
    popupTimKiemNhanVienThuCuoc: () => import('@/modules/contract/setup/Payment/components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc'),
    thongTinEmail, // () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue'),
    ThongTinLienHe, // () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'),
    DataGridC,
    // ToolbarPlugin,
    inHopDong, // () => import('@/modules/print/InHopDong/InHopDong.vue'),
    inPhieuThu, // () => import('@/modules/print/InPhieuThu/InPhieuThu.vue'),
    PrintReport,
    XemBaoCao,
    //HandoverTicketContent,
    SelfManageEinvoice,
    DongBoLapMoiVina,
    eInvoice,
    eInvoice2,
    selectPrintType,
    selectPrintPhieuChi,
    GiaoPhieuView,
    PopupThongTinNgoaiTe,
    frmThongTinTT: () => import('@/modules/contract/setup/ChangeService/popup/popThongTinTT_BoSung/index.vue'),
  },
  name: 'Payment',
  props: {
    tag: {
      type: String,
      default: ''
    },
    magiaodich: {
      type: String,
      default: ''
    },
    ispopup: {
      type: Boolean,
      default: false
    },
    autoload: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      header: {
        title: 'Thanh toán hợp đồng',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: ' Thanh toán hợp đồng',
            link: { name: 'Ui.cards' }
          }
        ]
      },
      state_tag: '',
      Loading: false,
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      vma_gd: '',
      OPEN_SYNC_VINA: false,
      OPEN_SYNC_VINA2: false,
      OPEN_SYNC_VINA_TRASAU: false,
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      selIndex: [],
      clearDataPhieuTra: false,
      settingSelect2: {
        language: 'vi'
      },
      paramHSDT: {
        type: 'MA_GD',
        value: ''
      },
      PayInfo: {
        phoneNumber: '',
        availableBalance: 0,
        name: ''
      },
      dsGiaTri: [],
      dsGiaTriHD: [],
      OpenBtn: true,
      showModal: false,
      kieuld_id: 0,
      PhieuId: 0,
      TrangThaiHdId: 2,
      showLienHe: true,
      in_bbn_tt: false,
      showEmail: true,
      dataReport: [],
      dtHDTB: [],
      loaihd_id: '',
      dsPhieuTTHD: [],
      phieutt_id_neo: '', ///TuấnNA thêm khai báo public biến phieutt_id_neo --- Ngày 01/10/2010
      modelIn: {
        hdkh_id: 0,
        ma_gd: '',
        ngaylap_hd: '',
        ngay_yc: ''
      },
      Fn: {
        InHD: false,
        GachNo: false,
        PhieuThu: false,
        PhieuBienNhan: false,
        PhieuTra: true,
        GiaoPhieu: false,
        TonPhieu: false,
        ChuyenPBH: false,
        XuatHDDT: false,
        DongBoVNP: false
      },
      params: {
        ts_hen_ld: 0,
        auto_port_kh: false,
        ts_hddt: 0,
        kiemtra_dk_datcoc: false,
        ts_checkbox_hddt: 0,
        ts_canhbao_phathanh_hddt: 0,
        bo_thanhtoan_hddt_dc: 0,
        vtt_hddt_hd: 0, // VTT có được xuất HDDT cho hợp đồng hay không ?
        vtt_hddt_hd_user: 0, // user có quyền xuất HDDT VTT không ?
        ts_kt_hddt_thanhtoan: false,
        kt_phathanh_hddt_thanhtoan: false,
        da_xuathd_dt: false,
        giaophieu_theo_hd: false,
        THANHTOAN_CHUYENKHOAN: false,
        tt78: false,
        them_extra: false,
        yeucau_caidat_acesspoint: false,
        hoi_sua_hddt: false,
        datcoc_goi_dadv: 0,
        datcoc_goi_tatca_tb: -1,
        hen_thanhtoan: 0
      },
      transferType: 0,
      kieugach: 0,
      dlgPhieuTon: false,
      tsbtnInHoaDon_Enabled: false,
      tsbtnInPhieuThu_Enabled: false,
      tsbtnPhieuBienNhan_Enabled: false,
      filterOptions: {
        enableCaseSensitivity: false,
        mode: 'Immediate',
        ignoreAccent: true
      },
      pageSettings: { pageSize: 5 },
      DsNguoiBaoLanh: {
        headers: [
          {
            fieldName: 'daily',
            headerText: 'Đại lý',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectItem: {},
        data: []
      },
      DsThueBao: {
        show_commands: false,
        commands: [
          { name: 'detail', title: 'TT thanh toán bổ sung', cssClass: 'pad3 mar5 lh14 icon -ap one-eye btn-plus btn-primary', iconCss: 'fa fa-times', width: 100 },
        ],
        headers: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
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
            headerText: 'Địa chỉ thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_hd',
            headerText: 'Trạng thái HĐ',
            allowFiltering: true,
            allowSorting: false,
            width: 90
          },
          {
            fieldName: 'tien_goc',
            headerText: 'Tiền gốc',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          },
          {
            fieldName: 'tien_thu',
            headerText: 'Tiền phải thu',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      ChuyenPBH: {
        DonVi: [],
        data: [],
        textField: 'TEN_DV',
        valueField: 'DONVI_ID',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'TEN_DV',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DONVI_ID',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'MA_DV',
            headerText: 'Mã đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        DonViId: '',
        NoiDung: ''
      },
      DsThueBaoPopUp: {
        headers: [
          {
            fieldName: 'MA_TB',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'PORT',
            headerText: 'Số ảo',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_DVVT',
            headerText: 'Dịch vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'PORT',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DIACHI_TB',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'DIACHI_TB',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TRANGTHAI_HD',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TOCDO',
            headerText: 'Tốc độ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_DT',
            headerText: 'Đối tượng',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      dialogDsCanTT: false,
      userComponentModule: null,
      DsThueBaoCanTT: {        
        headers: [
          {
            fieldName: 'ngay_yc',
            headerText: 'Ngày yêu cầu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_gd',
            headerText: 'Mã giao dịch',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_kh',
            headerText: 'Mã khách hàng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên khách hàng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tongtien',
            headerText: 'Tổng tiền',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0'
          },
          {
            fieldName: 'diachi_kh',
            headerText: 'Địa chỉ KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhanvien_lhd',
            headerText: 'Người lập HĐ/PL',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'donvi_lhd',
            headerText: 'Đơn vị YC',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selected: 0,
        loiNgayHenTT:"",
        checked: [],
        SelectItem: {},
        chkHddt: false,
        LoaiHD: {
          list: [],
          checked: false,
          val: 0
        },
        DichVu: {
          list: [],
          checked: false,
          val: 0
        },
        LoaiHinh: {
          list: [],
          checked: false,
          val: ''
        },
        TTHopDong: {
          list: [],
          checked: true,
          val: 1
        },
        txtMaGiaoDich: '',
        txtSoMay: ''
      },
      DsPhieuYC: {
        headers: [
          {
            fieldName: 'dvg',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nv_giao',
            headerText: 'Nhân viên giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_giao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_giao',
            headerText: 'Nội dung giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dvn',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_tra',
            headerText: 'Ngày trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_tra',
            headerText: 'Nội dung trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_ph',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selected: 0,
        selectItem: {},
        checked: []
      },
      DsKhoanMuc: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Left',
            width: 70
          },
          {
            fieldName: 'ten_kmtt',
            headerText: 'Khoản mục',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tien',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          },
          {
            fieldName: 'vat',
            headerText: 'Vat',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      DsKhoanMucCT: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Left',
            width: 70
          },
          {
            fieldName: 'ten',
            headerText: 'Chi tiết khoản mục',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tien_kmtt',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          },
          {
            fieldName: 'vat_kmtt',
            headerText: 'Vat',
            allowFiltering: true,
            allowSorting: false,
            type: 'Number',
            format: 'N0',
            width: 120
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      DsPhieuTT: [],
      KieuId: 1,
      VnPtPayTest: 1,
      HdKhId: 0,
      HdTbId: 0,
      CtvId: 0,
      KhachHangId: 0,
      PhieuTtId: '',
      PhieuTtIdIn: '',
      LoaiHDId: '',
      LoaiThueBaoId: '',
      TenThanhToan: '',
      txtMaGD: '',
      txtMaGDTT: '',
      txtLoaiHD: '',
      txtTenKH: '',
      txtTenKH2: '',
      txtDiaChiKH: '',
      txtDiaChiCT: '',
      txtDiaChiCT2: '',
      txtNguoiDaiDien: '',
      txtDienThoai: '',
      txtMaSoThue: '',
      txtSoTaiKhoan: '',
      txtNgayYeuCau: moment(new Date()).format('DD/MM/YYYY'),
      txtTongTienTra: 0,
      txtTienBangChu: '',
      txtGhiChu: '',
      hinhThucTTId: -1,
      kenhThuId: null,
      txtNguoiBL: '',
      trangThaiThanhToanId: 0,
      nganHangId: 0,
      chkNganHang: false,
      chkNgayhenTT: false,
      txtNgayNH: moment(new Date()).format('DD/MM/YYYY'),
      txtNgayhenTT: moment(new Date()).format('DD/MM/YYYY'),
      txtChungTu: '',
      chkHoaDonDienTu: false,
      chkLoaiHD: false,
      loaiHoaDonId: '',
      loaiHoaDonIdStatus: true,
      loaiSeriIdStatus: false,
      txtHoaDonStatus: true,
      txtPhieuThuHDStatus: false,
      thuNganIdStatus: false,
      txtTyGiaStatus: false,
      loaiTienIdStatus: false,
      txtNgayHDStatus: false,
      txtNgayTTStatus: false,
      txtThongTinPTStatus: false,
      txtChungTuStatus: false,
      nganHangIdStatus: false,
      txtNgayNHStatus: false,
      txtNgayhenTTStatus: false,
      txtNguoiBLStatus: false,
      btnNguoiBLStatus: false,
      chkHoaDonDienTuStatus: true,
      tsbtnHoanThienHS: '',
      tsbtnHoanThienHSStatus: true,
      hinhThucTTIdStatus: true,
      kenhThuIdtatus: true,
      chkHoaDonDienTuVisible: true,
      trangThaiThanhToanStatus: false,
      chkLoaiHDStatus: true,
      kieuhoadonId: 1,
      ob_id: '',
      DmLoaiHD: [],
      txtNgayHD: moment(new Date()).format('DD/MM/YYYY'),
      loaiSeriId: '',
      txtHoaDon: '',
      txtPhieuThuHD: '',
      loaiTienId: '',
      txtTyGia: 1,
      txtNgayTT: moment(new Date()).format('DD/MM/YYYY'),
      thuNganId: '',
      txtSoBNTT: '',
      txtThongTinPT: '',
      DmKenhThu: [],
      DmHinhThucTra: [],
      DmTrangThaiTT: [],
      DmNganHang: [],
      DmLoaiHoaDon: [],
      DmSeri: [],
      DmLoaiTien: [],
      DmThuNgan: [],
      DmDonVi: [],
      ThamSoMD: [],
      ds_tb_hddt: [],
      customerInfo: {
        txtTenKhachHang: ' ',
        vma_gd: '',
        txtMaSoThue: '',
        txtDiaChi: '',
        txtEmail: ' ',
        txtSoDienThoai: '',
        txtMaSoThue: ''
      },
      DsHopDong: {
        data: []
      },
      DsHopDongTT: {
        data: []
      },
      DsThanhToan: {
        data: []
      },
      LoaiHopDong: {
        KHONG_DUNG: 0,
        DAT_MOI: 1,
        CHUYEN_QUYEN: 2,
        DI_CHUYEN: 3,
        CHAMDUT_SD: 4,
        THAYDOI_THONGSO_MEGAWAN: 5,
        CHUYENDOI_LH: 6,
        THAY_DOI_DV: 7,
        THAY_DOI_TOCDO_ADSL: 8,
        THAY_DOI_SOMAY_TN: 9,
        TACHNHAP_TB: 10,
        THAY_TT_KHAC: 11,
        THAY_DOI_SO_SIM: 12,
        THUEBAO_TRATRUOC: 13,
        THAY_DAY_MAY: 13,
        VATTU_THUEBAO: 13,
        DOISO_TB: 14,
        BAN_THIETBI: 15,
        THAY_DOI_TOCDO_TSL: 16,
        THU_KHAC: 17,
        KHAO_SAT: 18,
        TDTT_DV_CNTT: 19,
        GHEP_MA_KHACHHANG: 20,
        KY_LAI_HD_GOC: 24,
        TAMDUNG_SD: 25,
        THAYDOI_THONGSO_MYTV: 25,
        TIEPNHAN_DATMOI: 26,
        DOIGOICUOC_DD: 27,
        TIEPNHAN_CDLH: 28,
        TIEPNHAN_DC: 29,
        KHOIPHUC_SD: 30,
        DANGKY_GOI_DADV: 27,
        THAYDOI_GOI_DADV: 28,
        DATCOC_MOI: 31,
        THAYDOI_DATCOC: 32,
        KHAOSATDATMOI: 33,
        KHAOSATCDLH: 34,
        KHAOSATDC: 35,
        TIEPNHANHD: 36,
        THUCUOC_THUHOIVT: 37,
        TUVAN_DV: 38, //phối hợp tư vấn dịch vụ giữa NVKT và TTKD
        THAYDOI_TT_DD: 100, // vuth them - thay doi thoong tin di dong
        DD_SIPTRUNKING: 40, // Taita thêm đăng ký dịch vụ SIPTRUNKING
        DD_HUYSTKING: 42, // Hủy dịch vụ Siptrunking di động
        DD_DKVCC: 43, //  Đăng ký dịch vụ VCC di động
        DD_HUYVCC: 44, //  Hủy dịch vụ VCC di động
        DD_TAMDUNGVCC: 45, //  Tạm dừng dịch vụ VCC di động
        DD_KICHVCC: 46, //   Kích hoạt lại dịch vụ VCC di động
        DD_CAPNHATGOI: 47, // Cập nhật gói VCC
        DD_XOAGOI: 48, // Xóa gói VCC
        DD_CAPNHATDATA: 49, // Thêm mới dịch vụ data
        DD_XOADATA: 50, // Xóa data VCC
        DD_HSSV: 101, // vuth update before 1509
        DD_DOANVIEN: 102, // vuth update before 1509
        DK_MULTISIM: 39, // vuth them 27/08/2020
        GIAHAN: 41 // vuth them 27/08/2020
      },
      DichVuVienThong: {
        TATCA: -1,
        CO_DINH: 1,
        DI_DONG: 2,
        ADSL: 4,
        DD_TRATRUOC: 5,
        EMAIL: 6,
        METRONET: 7,
        MEGAWAN: 8,
        TSL: 9,
        GPHONE: 10,
        IMS: 11,
        MEGA_EYES: 12,
        HOINGHI_TRUYENHINH: 13,
        ANTOAN_BAOMAT_TT: 14,
        DICHVU_CNTT: 15,
        TRUNGTAM_DULIEU: 16,
        HATANG_VIENTHONG: 19,
        IMS_DOITAC: 21,
        BRCD_DOITAC: 22,
        MGW_DOITAC: 23,
        TSL_DOITAC: 24
      },
      LoaiHinhTB: {
        DIENTHOAI_CD: 1,
        TRUNGKE_THUONG: 3,
        TRUNGKE_TUONGTU: 4,
        TRUNGKE_2M: 5, // Có số chính, số phụ
        PABX: 8,
        MAIL_SMD: 9,
        INTERNET: 10,
        INTERNET_1260: 10,
        MEGA: 11,
        INTERNET_ADSL: 11, //Hiếu bổ sung ngày 25/08/2010
        Web_Hosting: 12,
        EMAIL: 13,
        ISDN2B_CD: 14, //megawan cổng FE
        ISDN30B_CD: 15, //megawan cổng GE
        ISDN2B_CQ: 16,
        ISDN30B_CQ: 17,
        HTVC_FIBERVNN_ANALOG: 18,
        HTVC_FIBERVNN_DIGITAL: 19,
        DIDONGTRASAU: 20,
        DIDONGTRATRUOC: 21,
        LEASEDLINE: 22, //Hiếu bổ sung ngày 29/08/2010
        MEGAWAN_ADSL: 23,
        FSECURE: 24,
        MEGAWAN_SHDSL: 25,
        MEGAWAN_QUANG_FE: 26,
        METRONET_FE: 27,
        METRONET_GE: 28,
        MEGAWAN_QUANG_GE: 30,
        MEGACAMERA: 31,
        GPHONE: 32,
        GPHONE_TRATRUOC: 34,
        IOFFICE: 35,
        TD_1800: 38,
        VNPT_COLOCATION: 39,
        INTERNET_ADSL_TINH_KHAC: 57,
        INTERNET_FTTH: 58,
        INTERNET_FIBER_TINH_KHAC: 59,
        INTERNET_MYTV: 61, //B2C
        INTERNET_MYTV_B2B: 171,
        IMS_POTS: 62,
        IMS_SIP: 63,
        IMS_RGW: 64,
        INTERNET_SGTV: 65,
        CAMERA_HOCDUONG: 70,
        MEGAWAN_ADSL_2M: 74,
        METRONET_2FE: 75,
        MEGA_CONFERENCE: 76,
        IMS_SIPTRUNKING: 77,
        SSL: 78,
        WIFI_FIBER: 79, //Hiếu bổ sung ngày 10/01/2017
        VNPT_MEETING: 89,
        HNTH_NGN: 90,
        KTR_Leasedline_GE: 96,
        KTR_Leasedline_E1: 97,
        KTR_Leasedline_STM1: 98,
        KTR_Leasedline_nx64k: 99,
        KTR_TRUCTHONG_DONG: 100,
        KTR_TRUCTHONG_QUANG: 101,
        KTR_DOICAP_QUANG: 102,
        KTR_DOICAP_DONG: 103,
        KTR_Leasedline_FE: 104,
        KTR_Nail_up: 105,
        CDN: 112,
        CA: 116,
        CA_IVAN: 117,
        VN_EDU: 118,
        VNPT_PORTAL: 120,
        TTDL_HDDT: 122,
        TTDL_MAIL_OFFICE: 124,
        TD_1900: 127,
        BRANDNAME: 131,
        IVAN: 132,
        DEDICATED_SERVER: 139,
        TDTH_THUYENHINH: 146,
        VCC: 149,
        MGW_INTERNET_TRUCTIEP: 134,
        COSO_HATANG: 158,
        CAPQUANG_TRANG_TSL: 123,
        MEGAEYES_CLOUD_CAMERA: 83,
        WIFI_MESH: 210,
        INDOOR_CAM: 222,
        OUTDOOR_CAM: 224,
        TIVI_SAMSUNG: 266,
        IMS_DT: 228,
        BRCD_FBDT: 229,
        BRCD_THDT: 230,
        MGW_DT: 231,
        TSL_DT: 232,
        // nhóm dịch vụ CNTT
        ECABINET: 200,
        ELEARNING: 208,
        ORM: 160,
        VXP: 235,
        VNPT_HIS: 136,
        VNPT_HMIS: 156,
        BIENLAIDT: 175,
        VNPT_MCAP: 267,
        VNPT_HCSN: 202,
        VNPT_SMART_TOURIST: 223,
        VNPT_SMCC: 268,
        VEDU_LIB: 205,
        VNPT_SLL: 215,
        VNPT_LIS: 261
      },
      //LoaiHopDongId:0,      
      KieuLapDat: {
        DM_CODINH: 1,
        DM_CD_CO_SAN_ADSL: 152,
        DM_CD_CO_SAN_MYTV: 201,
        DM_CD_CO_SAN_MGW: 205,
        THAYDOI_TT_DICHVU_KHAC: 17,
        DM_CD_KEM_ADSL: 70,
        DM_CD_KEM_MYTV: 203,
        DM_CD_KEM_MGW: 204,
        DM_PABX: 242,
        DM_MGW_KEM_THOAI: 43,
        DM_MGW_KEM_ADSL: 44,
        DM_MGW_TREN_THOAI: 45,
        DM_MGW_TREN_ADSL: 46,
        DM_MGW_DOCLAP: 42,
        DM_TSL_LUONG: 48,
        DM_DIDONG: 2,
        DM_ADSL_CO_SAN_DT: 4,
        DM_ADSL_KEM_DT: 5,
        DM_ADSL_KHONG_SO: 6,
        TD_LOAITBI_DC: 912,
        TD_THAYTHE_STB: 7000,
        DC_DIENTHOAI: 8,
        DC_DIENTHOAI_KHACDC: 89,
        DC_ADSL_CO_SAN_DT_CUNG_DC: 31,
        DC_ADSL_CO_SAN_DT_KHAC_DC: 9,
        DC_ADSL_KEM_DT: 10,
        DC_ADSL_KHONG_SO: 11,
        DC_THOAI_DENDUONG_ADSL_COSAN: 207,
        DC_THOAI_DENDUONG_MYTV_COSAN: 209,
        DC_THOAI_DENDUONG_MEGAWAN_COSAN: 211,
        DC_THOAI_KEM_ADSL: 206,
        DC_THOAI_KEM_MYTV: 208,
        DC_MEGAWAN_KEM_THOAI: 52,
        DC_MEGAWAN_KEM_ADSL: 53,
        DC_MEGAWAN_DEN_THOAI_COSAN: 54,
        DC_MEGAWAN_DEN_ADSL_COSAN: 55,
        TACH_THUEBAO: 19,
        NHAP_THUEBAO: 167,
        HOAMANG_TRATRUOC: 169,
        DOISIM_TRATRUOC: 170,
        BAN_THIETBI: 49,
        DICHVU_KHAC: 17,
        THAYDOI_TT_THANHTOAN: 18,
        THAYDOI_TT_KHACHHANG: 21,
        STCD_DOI_DICHVU: 36,
        STADSL_DOI_TOCDO: 37,
        THAYDOI_TT_THUEBAO: 75,
        THAYDOI_TOCDO_ADSL: 24,
        THAYDOI_MUCCUOC_ADSL: 76,
        THAYDOI_TOCDO_MUCCUOC_ADSL: 77,
        THAYDOI_TOCDO_ADSL_TK: 13080,
        THAYDOI_MUCCUOC_ADSL_TK: 13081,
        THAYDOI_TOCDO_MUCCUOC_ADSL_TK: 13082,
        THAYDOI_TOCDOTHUC_ADSL: 655,
        THAYDOI_CONGNGHE_TOCDO_AON_GPON: 885,
        THAYDOI_CONGNGHE_TOCDO_GPON_AON: 886,
        THAYDOI_CONGNGHE_AON_GPON: 934,
        THAYDOI_CONGNGHE_GPON_AON: 935,
        KHAO_SAT_DICH_CHUYEN_ADSL: 81,
        KHAO_SAT_DICH_CHUYEN_CD: 79,
        KHAO_SAT_DICH_CHUYEN_GPHONE: 88,
        KHAO_SAT_DATMOI_ADSL: 80,
        KHAO_SAT_DATMOI_CD: 78,
        KHAO_SAT_DATMOI_GPHONE: 85,
        THANH_LY_DIDONG: 156,
        THANH_LY_INTERNET: 158,
        THANH_LY_CODINH: 20,
        THANH_LY_GPHONE: 84,
        THANH_LY_TSL: 59,
        THANH_LY_MEGAWAN: 58,
        THANH_LY_TELEX: 34,
        THANH_LY_EMAIL: 33,
        CHUYENDD_TRASAU_SANG_TRATRUOC: 68,
        CHUYENGP_TRATRUOC_SANG_TRASAU: 183,
        DM_ADSL_TREN_MYTV_COSAN: 181,
        DM_MYTV_TREN_ADSL_COSAN: 193,
        DM_MYTV_KEM_ADSL: 195,
        DM_MYTV_TREN_FTTH_COSAN: 194,
        DM_MYTV_KEM_FTTH: 196,
        DC_MYTV_KEM_MEGA: 12,
        DC_MEGA_KEM_MYTV: 13,
        DM_ADSL_KEM_MYTV: 202,
        TDLH_MEGA_FTTH: 182,
        CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_ADSL: 188,
        CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_SHDSL: 186,
        CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_ADSL: 187,
        CHUYENTU_MEGAWAN_CAPQUANG_SANG_MEGAWAN_SHDSL: 189,
        CHUYENTU_MEGAWAN_ADSL_SANG_MEGAWAN_CAPQUANG: 190,
        CHUYENTU_MEGAWAN_SDHSL_SANG_MEGAWAN_CAPQUANG: 191,
        CHUYENTU_METRONET_FE_SANG_METRONET_GE: 610,
        THUE_BAO_QUANG: 51,
        MYTV_KHONGSO_CAPDONG: 197,
        DOISO_CD_ISDN_SOMOI: 35,
        THAYDOI_KIEUTRA_MYTV: 270,
        CHUYENTU_CODINH_SANG_PABX: 244,
        CHUYENTU_PABX_SANG_CODINH: 245,
        CHUYENTU_FTTH_ADSL: 246,
        DOISO_CHINH_ISDN: 275,
        DOINHOM_ISDN: 100,
        CHUYENTU_CODINH_SANG_ISDN: 271,
        CHUYENTU_ISDN_SANG_CODINH: 272,
        CHUYENTU_30B_SANG_2B: 273,
        CHUYENTU_2B_SANG_30B: 274,
        DANGKY_GOI_DADV: 280,
        HUY_GOI_DADV: 281,
        XOA_TB_GOI_DADV: 282,
        THEM_TB_GOI_DADV: 283,
        THAYDOI_TT_KHUYENMAI: 312,
        THAYDOI_TT_LIENHE: 586,
        THAYDOI_TT_DV_CNTT: 863,
        GIAHAN_DV_CNTT: 13131,
        THAYDOI_DVGT_GP: 171,
        TAMDUNG_SD_GP: 101,
        MOTAMDUNG_SD_GP: 102,
        THAYDOI_DVGT_CD: 23,
        TAMDUNG_SD_CD: 103,
        MOTAMDUNG_SD_CD: 104,
        CHUYENDOIIMS_CD: 568,
        THAYDOI_DVGT_DD: 165,
        TAMDUNG_SD_DD: 79,
        MOTAMDUNG_SD_DD: 80,
        HUYGOI_HSSSV: 957,
        THAYDOI_DVGT_IMS: 227,
        TAMDUNG_SD_IMS: 109,
        MOTAMDUNG_SD_IMS: 110,
        CHUYENDOICD_IMS: 567,
        CHUYENDOICD_IMS_TREN_FIBER: 677,
        CDLH_CD_IMS_MEGAWAN: 703,
        CDLH_CD_IMS_FIBER_MOI: 719,
        CDLH_CD_IMS_MYTV: 701,
        THAYDOI_DVGT_MEGA: 105,
        TAMDUNG_SD_MEGA: 39,
        MOTAMDUNG_SD_MEGA: 96,
        THAYDOI_DVGT_FIBER: 106,
        TAMDUNG_SD_FIBER: 99,
        MOTAMDUNG_SD_FIBER: 100,
        TAMDUNG_SD_MYTV: 97,
        MOTAMDUNG_SD_MYTV: 98,
        CHUYEN_MYTV_THUONG_SANG_VMP: 608,
        CHUYEN_VMP_SANG_MYTV: 661,
        TAMDUNG_SD_SGTV: 111,
        MOTAMDUNG_SD_SGTV: 112,
        TAMDUNG_SD_TSL: 61,
        MOTAMDUNG_SD_TSL: 108,
        TAMDUNG_SD_MGWAN: 60,
        MOTAMDUNG_SD_MGWAN: 107,
        TAMDUNG_SD_METRONET: 833,
        MOTAMDUNG_SD_METRONET: 834,
        TAMDUNG_SD_KP_AO: 697,
        MOTAMDUNG_SD_KP_AO: 698,
        THUESO_DT_CU_SAU_DC: 540,
        THUESO_DT_CU_SAU_TL: 557,
        THUESO_DT_MOI: 541,
        THANHLY_THUESO_DT: 542,
        TAMDUNG_SD_EMAIL: 40,
        MOTAMDUNG_SD_EMAIL: 106,
        HA_TOCDO_DUYTRI: 14,
        NANG_TOCDO: 15,
        DATCOC_MOI: 550,
        THAYDOIGOICUOC_DATCOC: 551,
        TANGTHOIGIAN_DATCOC: 552,
        HUYDATCOC: 553,
        TACH_VLAN: 581,
        THUE_NGAN_HAN: 594,
        DATMOI_KENHPHU_AO: 595,
        KHAOSATDATMOI: 571,
        TL_KENHPHU_AO: 613,
        DM_TINH_KHAC: 649,
        DC_TINH_KHAC: 650,
        DOIGOI_FSECURE: 668,
        TDDV_IMS_BRG_KICHHOAT: 669,
        CQ_SUDUNG_IMS: 230,
        THAYDOI_MUCCUOC_TB: 692,
        DICHCHUYEN_KP_AO: 695,
        DM_DIDONG_C_P: 27,
        DM_DIDONG_CHUYEN_TINH: 217,
        DM_DIDONG_KHOI_TAO_LAI: 218,
        pYEUCAU_GOIGD: 'YEUCAU_GOIGD',
        THANHTOAN_SUDUNG: 686,
        THANHTOAN_TRA_TBI: 687,
        THANHTOAN_KO_TRA_TBI: 688,
        KO_THANHTOAN_CO_TRA_TBI: 689,
        KO_TT_KO_TRA_TBI: 690,
        KHAOSATCDLH: 618,
        DOIGOI_CNTT_GTGT: 806,
        HNI_DOI_GIA_CUOC: 850,
        HNI_DOI_TOCDO: 851,
        HNI_DOI_LOAIKENH: 852,
        HNI_DOI_HUONG_KN: 853,
        HNI_DOI_TOCDO_LOAIKENH: 854,
        HNI_DOI_TOCDO_HUONGKN: 855,
        HNI_LOAIKENH_HUONGKN: 856,
        HNI_TOCDO_LOAIKENH_HUONGKN: 857,
        HNI_DOI_WANIP_LAN_IP: 903,
        TD_TBI_MYTV: 875,
        TL_LUONG_ISDN: 877,
        TL_SOPHU_ISDN: 878,
        HNI_DOI_CONGNGHE_GPON: 921,
        TAMDUNG_SD_CD_TK: 13086,
        MOTAMDUNG_SD_CD_TK: 13087,
        TAMDUNG_SD_INTERNET_TK: 13088,
        MOTAMDUNG_SD_INTERNET_TK: 13089,
        TAMDUNG_SD_IMS_TK: 13090,
        MOTAMDUNG_SD_IMS_TK: 13091,
        TAMDUNG_SD_MGWAN_TK: 13092,
        MOTAMDUNG_SD_MGWAN_TK: 13093,
        TAMDUNG_SD_TSL_TK: 13098,
        MOTAMDUNG_SD_TSL_TK: 13099,
        TDTB_GOIDADV: 159,
        TDTB_NHOMDADV: 13125,
        TD3_GOIDADV: 13114,
        CHUANHOA_CNTT: 13130,
        TNYC_THAYDOI_TT: 13132,
        TNYC_CHUYENDICH: 13133,
        TNYC_THANHLY: 13134,
        TNYC_KHOIPHUC: 13135,
        TNYC_THAYDOI_TOCDO: 13136,
        TNYC_CD_LOAIHINH: 13137,
        TNYC_TD_DVGTGT: 13138,
        TNYC_GHEP_GOI_DADV: 13139,
        TNYC_TACHGHEP_MA: 13140,
        TNYC_KHOAMO_PAYTV: 13141,
        TNYC_KHAC: 13142,
        TNYC_TAMDUNG: 13144,
        TNYC_DOI_GOI_DADV: 13145,
        DM_CLOUDCAMERA_FTTH_CS: 11000,
        DM_CLOUDCAMERA_KEM_FTTH: 11001,
        CLOUDCAMERA_ADD_CAMERA: 13146,
        CLOUDCAMERA_TAMNGUNG_CAMERA: 11002,
        CLOUDCAMERA_KHOIPHUC_CAMERA: 11003,
        CLOUDCAMERA_DEL_CAMERA: 72,
        THAYDOI_TT_CAP: 13172,
        DK_MULTISIM: 13169,
        HUY_MULTISIM: 13170
      },
      TrangThaiHD: {
        MOI: 1,
        THANH_TOAN: 2,
        DIEUHANH_THI_CONG: 3
      },
      HinhThucTra: {
        TIEN_MAT: 1,
        TIEN_SEC: 2,
        UY_NHIEM_CHI: 4,
        UY_NHIEM_THU: 5,
        VI_VNPTPAY: 204,
        TT_CHUOITOANQUOC: 8,
        TT_SHOP_ONLINE: 9,
        KHONG_THU_TIEN: 208,
      },
      KENHTHU: {
        TAI_GIAO_DICH: 22,
        TAI_NHA: 21,
        BUU_CUC: 3,
        BAO_LANH: 4,
        CHUYEN_KHOAN: 5,
        KHONG_THU_TIEN: 6,
        THUCUNG_CPS: 7, //Trả 100% vào hóa đơn cước
        THU_THEO_GIAI_DOAN: 9,
        KENHBANHANG: 11,
        KENH_TGDD: 12,
        KENH_SHOP: 13,
        NOP_TIEN_SAU: 29,
        KHAC: 27,
      },
      TRANGTHAI_PORT: {
        CHUA_SD: 1,
        TAM_CAP: 2,
        DA_SD: 3,
        HONG: 4,
        DU_PHONG: 5,
        UPLINK: 7
      },
      KIEULAP: {
        LAP_KHONG_SO: 0,
        LAP_KEM: 1,
        LAP_LINE_SAN: 2,
        THAYDOI_TSO: 3,
        PORT_THEO_CAP: 4
      },
      target: '#app', //  .main-wrapper-payment
      target1: '#app .main-wrapper',
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      positioncenter: { X: 'center', Y: 'center' },
      xuat_hd: false,
      loaiHopDongId: 1,
      dichvuId: 1,
      params_thanhtoan: {
        capnhat_yc: 0,
        thanhtoan: 0,
        datcoc_goi_dadv: 0
      },
      QuyTrinhId: 0,
      // VNPT Pay
      walletId: '',
      walletAccountId: '',
      token: '',
      tsbtnXuaHD_Enabled: false,
      tsbtnDongBoVNP_Enabled: false,
      tsbtnInPhieu_HH_Enabled: true,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed
      },
      popupComponentName: '',
      p_cboBrasADSL: 0,
      p_cboDSLAM: 0,
      p_donv_tram_id: 0,
      timer: null,
      notFinished: false,
      tt_nd: {},
      tt: {},
      vphieu_id_neo: '',
      vphieu_id: '',
      call_newapi: false,
      giaoPhieuViewDialog: {
        animationSettings: { effect: 'None' },
        isVisiable: false,
        input: {
          vhuonggiao_id: null,
          vloai_hd: null,
          vdichvuvt_id: null,
          vma_gd: null,
          vquytrinh: null,
          vtrangthaiphieu: 1
        }
      },
      payment_pattern: '',
      payment_serial: '',
      flag: false,
      checkLoginPayment: true,
      cty_newline: false,
      chHDDTTGDaDichVu: false,
      enableChHDTGDDV: false
    }
  },
  beforeCreate() {},
  async created() {
    //xy ly tag
    try {
      let str = this.$route.query.tag
      let words = str.split('+')
      if (words.length >= 1) this.state_tag = words[0]
      if (words.length >= 2) {
        // Áp dụng cho user của đối tác
        if (words[1] == '1') this.cty_newline = true
      }
    } catch (e) {
      this.state_tag = 1
    }
    this.OPEN_SYNC_VINA = TRANGTHAI_DONGBO.OPEN_SYNC_VINA
    this.OPEN_SYNC_VINA_TRASAU = TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU
    console.log('Payment update:15/08/2023')
    //console.log('TRANGTHAI_DONGBO.OPEN_SYNC_VINA:created:', this.OPEN_SYNC_VINA)
    //console.log('TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU:created:', this.OPEN_SYNC_VINA_TRASAU)
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function(v) {
        v.removeAttribute('tabindex')
      })
    })
    this.$root.$on('bv::modal::hidden', (bvEvent, modalId) => {
      this.ValidateModal(bvEvent, modalId)
    })
    this.notFinished = true
    this.startTimer()
    this.loading(true)
    if (this.KieuId == 1 || this.KieuId == 2) {
      //this.kieuhoadonId = -1;
      this.kieuhoadonId = 1
    } else this.kieuhoadonId = 0
    try {
      Promise.all([this.getDmTrangThaiTT(), this.getDmHinhThucTra(), this.getDmNganHang(), this.getDmLoaiHoaDon(), this.getDmLoaiTien(), this.getDmThuNgan(), this.getDsLoaiHD(), this.getDsDVTT(), this.getDsTTHD(), this.getDongBoVina()])
        .then(async (values) => {
          await this.getDmKenhThu()
          //this.loading(false)
          //console.log(values);
        })
        .catch((error) => {
          //this.loading(false)
          console.log(`Error in promises ${error}`)
        })
        .finally((e) => {
          //this.loading(false)
        })
      // Check update dong bo vina
      //this.loading(false)
      let defaultVals = await this.getThamSoMacDinh()
      // console.log('defaultVals:', defaultVals)
      if (defaultVals && defaultVals.length > 0) {
        
        if (defaultVals.filter((x) => x.ma_ts == 'TS_HEN_LD').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'TS_HEN_LD')[0]
          if (valObj.ten_ts == 1) {
            this.params.ts_hen_ld = valObj.ten_ts
          } else {
            this.params.ts_hen_ld = 0
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'HEN_THANHTOAN').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'HEN_THANHTOAN')[0]
          if (valObj.ten_ts == 1) {
            this.params.hen_thanhtoan = valObj.ten_ts
          } else {
            this.params.hen_thanhtoan = 0
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'AUTO_PORT_KICHOAT').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'AUTO_PORT_KICHOAT')[0]
          if (valObj.ten_ts == 1) {
            this.params.auto_port_kh = valObj.ten_ts
          } else {
            this.params.auto_port_kh = 0
          }
        }
       
        if (defaultVals.filter((x) => x.ma_ts == 'KT_PHATHANH_HDDT_THANHTOAN').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'KT_PHATHANH_HDDT_THANHTOAN')[0]
          if (valObj.ten_ts == 1) {
            this.params.ts_kt_hddt_thanhtoan = true
          } else {
            this.params.ts_kt_hddt_thanhtoan = false
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT_HOI_SUA_NOIDUNG').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT_HOI_SUA_NOIDUNG')[0]
          if (valObj.ten_ts == 1) {
            this.params.hoi_sua_hddt = true
            //this.params.hoi_sua_hddt = false
          } else {
            this.params.hoi_sua_hddt = false
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'BO_THANHTOAN_HDDT_DC').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'BO_THANHTOAN_HDDT_DC')[0]
          if (valObj.ten_ts == 1) {
            this.params_thanhtoan.datcoc_goi_dadv = 1
            this.params.datcoc_goi_dadv = 1
          } else {
            this.params_thanhtoan.datcoc_goi_dadv = 0
            this.params.datcoc_goi_dadv = 0
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'GIAOPHIEU_THEO_HD').length > 0) {
          this.params.giaophieu_theo_hd = true
        } else {
          this.params.giaophieu_theo_hd = false
        }
        if (defaultVals.filter((x) => x.ma_ts == 'IN_BIENNHAN_TT').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'IN_BIENNHAN_TT')[0]
          if (valObj.ten_ts == 1) {
            this.in_bbn_tt = true
          } else {
            this.in_bbn_tt = false
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'HDDT_THONGTU_78').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'HDDT_THONGTU_78')[0]
          if (valObj.ten_ts == 1) {
            this.params.tt78 = true
          } else {
            this.params.tt78 = false
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'DATCOC_GOI_TATCA_TB').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'DATCOC_GOI_TATCA_TB')[0]
          if (valObj.ten_ts == 1) {
            this.params.datcoc_goi_tatca_tb = 1
          }
        }
        if (defaultVals.filter((x) => x.ma_ts == 'VTT_HDDT_HOPDONG').length > 0) {
          let valObj = defaultVals.filter((x) => x.ma_ts == 'VTT_HDDT_HOPDONG')[0]
          if (valObj.ten_ts == 1) {
            this.params.vtt_hddt_hd = valObj.ten_ts
            if (this.params.vtt_hddt_hd == 1) {
              // Cho api cap nhat lai tham so this.params.vtt_hddt_hd_user
              let tsnd = await this.getThamSoMacDinhNguoiDung('VTT_HDDT_HOPDONG')
              if (tsnd != null) {
                if (tsnd != '') {
                  try {
                    this.params.vtt_hddt_hd_user = parseInt(tsnd)
                  } catch (vtt_hddt_hd_user_err) {}
                }
              }
              //
            }
          } else {
            this.params.vtt_hddt_hd = 0
          }
        }
        console.log('this.params.vtt_hddt_hd_user', this.params.vtt_hddt_hd_user)
        if (this.params.vtt_hddt_hd_user == 1) {
          if (this.KieuId == 1 || this.KieuId == 2) {
            //this.kieuhoadonId = -1;
            this.kieuhoadonId = 1
            await this.getDmLoaiHoaDon()
          } else {
            this.kieuhoadonId = 0
            await this.getDmLoaiHoaDon()
          }
        } else {
          if (defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT').length > 0) {
            let a = defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT')[0]
            if (a.ten_ts == 1) {
              this.chkHoaDonDienTuVisible = true
              this.params.ts_hddt = 1
              if (this.KieuId == 1) {
                //ts_checkbox_hddt
                if (defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT_CHECK').length > 0) {
                  let valObj = defaultVals.filter((x) => x.ma_ts == 'THANHTOAN_HDDT_CHECK')[0]
                  if (valObj.ten_ts == 1) {
                    this.params.ts_checkbox_hddt = 1
                    this.chkHoaDonDienTu = true
                    //this.chkHoaDonDienTuVisible = true;
                  } else {
                    this.params.ts_checkbox_hddt = 0
                    this.chkHoaDonDienTu = false
                    //this.chkHoaDonDienTuVisible = false;
                  }
                }
                //
                if (defaultVals.filter((x) => x.ma_ts == 'CANHBAO_CHUA_PHAT_HANH_HDDT').length > 0) {
                  let valObj = defaultVals.filter((x) => x.ma_ts == 'CANHBAO_CHUA_PHAT_HANH_HDDT')[0]
                  if (valObj.ten_ts == 1) {
                    this.params.ts_canhbao_phathanh_hddt = valObj.ten_ts
                  } else {
                    this.params.ts_canhbao_phathanh_hddt = 0
                  }
                }
              }
            } else {
              this.chkHoaDonDienTu = false
              this.chkHoaDonDienTuVisible = false
            }
          } else {
            this.chkHoaDonDienTu = false
            this.chkHoaDonDienTuVisible = false
          }
        }
      }
      // console.log('params:', this.params)
      //this.loading(false)
      this.$emit('paymentcreated', true)
    } catch (ex) {
      console.log('created-err:', ex)
      this.$emit('paymentcreated', false)
      //this.loading(false)
    }
  },
  mounted: async function() {
    if (this.$route.query.ma_gd && this.$route.query.ma_gd !== '') {
            this.txtMaGD = this.$route.query.ma_gd.replaceAll(' ', '+');
            this.searchTheoMaGD()
    }
    try {
      if (this.magiaodich != null && this.magiaodich != '') {
        this.txtMaGD = this.magiaodich
      }
      if (this.tag) {
        this.KieuId = this.tag //HOTFIX thangdm1 2023-04-20 them xu ly tag khi truyen qua prop
        this.state_tag = this.tag
      }
      try {
        if (this.$route.query.kieu != null && this.$route.query.kieu && this.$route.query.kieu.length > 0) {
          this.KieuId = this.$route.query.kieu
        }
      } catch (ex) {}
      try {
        if (this.$route.query.newpayapi != null && this.$route.query.newpayapi && this.$route.query.newpayapi.length > 0) {
          if (this.$route.query.newpayapi == '1' || this.$route.query.newpayapi == 'true') this.call_newapi = true
          console.log('call_newapi_pay', this.call_newapi)
        }
      } catch (ex) {}
      // Lay gia tri ma giao dich
      if (this.$route.params.magd != null && this.$route.params.magd !== '' && this.$route.params.magd.length > 0) {
        this.txtMaGD = this.$route.params.magd
      } else if (this.$route.query.magd != null && this.$route.query.magd !== '' && this.$route.query.magd.length > 0) {
        this.txtMaGD = this.$route.query.magd
      } else if (this.pmagd != null && this.pmagd != '' && this.pmagd.length > 0) {
        this.txtMaGD = this.pmagd
      }
      // Lay gia tri mac dinh loai hop dong thanh toan
      console.log('this.$router.params', this.$router.params)
      try {
        if (this.$router.params != null && typeof this.$router.params.loaihdId != 'undefined' && this.$router.params.loaihdId != null && this.$router.params.loaihdId > 0) {
          this.loaiHopDongId = this.$router.params.loaihdId
        } else if (this.$router.query.loaihdId != null && this.$router.query.loaihdId > 0) {
          this.loaiHopDongId = this.$router.query.loaihdId
        } else {
          //this.loaiHopDongId = this.loaiHopDongId
        }
      } catch (ex) {}
      //Lay gia tri mac dinh dich vu Id
      try {
        if (this.$router.params != null && this.$router.params.dichvuId != null && this.$router.params.dichvuId > 0) {
          this.dichvuId = this.$router.params.dichvuId
        } else if (this.$router.query.dichvuId != null && this.$router.query.dichvuId > 0) {
          this.dichvuId = this.$router.query.dichvuId
        } else {
        }
      } catch (ex) {}
      //
      try {
        if (this.pkieuid != null && this.pkieuid > 0) {
          this.KieuId = this.pkieuid
        }
      } catch (ex) {}
      try {
        if (this.$route.query.ispopup != null && (this.$route.query.ispopup == '1' || this.$route.query.ispopup == 'true')) {
          this.ispopup = true
        }
      } catch (err) {}
      this.loading(true)
      this.HienThiDoiTuong()
      console.log('KieuId', this.KieuId, 'chkHoaDonDienTu:', this.chkHoaDonDienTu, 'chkLoaiHD', this.chkLoaiHD, ',cty_newline:', this.cty_newline)
    } catch (err) {
      console.log('🚀 ~ file: Payment.vue ~ line 957 ~ err', err)
    } finally {
      console.log('TRANGTHAI_DONGBO.OPEN_SYNC_VINA:mounted', this.OPEN_SYNC_VINA)
      console.log('TRANGTHAI_DONGBO.OPEN_SYNC_VINA_TRASAU:mounted', this.OPEN_SYNC_VINA_TRASAU)
      if (this.txtMaGD != '') {
        var self = this
        this.loading(true)
        await this.searchTheoMaGD()
        this.loading(false)
        this.notFinished = false
        /*
        this.$nextTick().then(async function () {
           this.loading(true)
          await self.searchTheoMaGD()
          this.loading(false)
        })
        */
      } else {
        this.notFinished = false
        this.loading(false)
      }
    }
  },
  watch: {
    chkLoaiHD: function(val, old) {
      try {
        this.HienThiDoiTuong()
        return
      } catch (err) {
        console.log('watch:chkLoaiHD', err)
        return
      }
    },
    loaiSeriId: async function(val) {
      try {
        if (!this.chkHoaDonDienTu) {
          try {
            this.loading(true)
            let data = await this.getSoHoaDon()
            this.loading(false)
            if (data != null && data.soseri != null) {
              this.txtHoaDon = data.soseri + 1
            } else {
              this.txtHoaDon = ''
            }
          } catch (ex) {
            this.loading(false)
          }
        }
      } catch (err) {
        console.log('watch:loaiSeriId', err)
      }
    },
    chkNganHang: function(val) {
      this.nganHangIdStatus = val
      this.txtNgayNHStatus = val
      this.txtChungTuStatus = val
      this.txtChungTu = ''
    },
     chkNgayhenTT: function(val) {
      this.txtNgayhenTTStatus = val
    },
    chkHoaDonDienTu: async function(val) {
      console.log("chkHoaDonDienTu:", val);
      try {
        if (this.KieuId == 1) {
          this.chkLoaiHD = val
          this.txtHoaDon = ''
        } else {
          if (val) this.chkLoaiHD = val
        }
        if (this.params.vtt_hddt_hd_user == '1') {
          if (val) {
            if (this.params.tt78) {
              this.kieuhoadonId = 5
            } else {
              this.kieuhoadonId = 3
            }
          } else {
            this.kieuhoadonId = -1
          }
          //this.kieuhoadonId = val ? 3 : -1;
        } else {
          this.kieuhoadonId = val ? 2 : 1
        }
        if (val && this.params.tt78 && this.params.vtt_hddt_hd_user != '1') {
          this.kieuhoadonId = 4
        }
        if (val) {
          await this.getDmLoaiHoaDon2()
        } else {
          await this.getDmLoaiHoaDon()
        }
        await this.getCboLoaiHd(this.loaiHoaDonId)
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 1083 ~ err', err)
      }
    },
    
    'DsThueBaoCanTT.DichVu.val': async function(val) {
      try {
        await this.getDsLoaiHinhTB(val)
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 1170 ~ err', err)
      }
    },
    loaiHoaDonId: async function(val) {
      if(!val) return;
      try {
       await this.getCboLoaiHd(val)
      } catch (err) {
        console.log('🚀 watch:loaiHoaDonId ~ err', err)
      }
    },
    magiaodich: function(val) {
      try {
        this.txtMaGD = val
        this.DsThueBao.data = []
        this.DsKhoanMuc.data = []
        this.DsKhoanMucCT.data = []
        this.clearData()
        if (this.autoload) {
          if (val != null && val != '') {
            console.log('call magiaodich', val)
            this.SetMaGd(val)
          } else {
            this.loading(false)
          }
        }
      } catch (err) {
        console.log('🚀 magiaodich ~ err', err)
      } finally {
        this.loading(false)
      }
    },
    Loading: function(val) {
      this.loading(val)
      // this.$root.showLoading(val)
    },
    loaihd_id: {
      async handler(val){
        if(!val) return;

        if ((val == 1 || val == 10) && this.$root.token.getPhanVungID() == 98 && this.state_tag == 1) {
          this.tsbThongTinNgoaiTe_Enabled = true
        } else {
          this.tsbThongTinNgoaiTe_Enabled = false
        }
        //update them 

        if(val == 31){
          this.chHDDTTGDaDichVu == true;
          this.enableChHDTGDDV = true;
          this.$refs.checkLoaiHDTGoiDaDichVu.checked = true;
        }else{
          this.enableChHDTGDDV = false;
          this.$refs.checkLoaiHDTGoiDaDichVu.checked = false;
          this.chHDDTTGDaDichVu == false;
        }
        
      },
      deep: true
    }

  },
  validations: {
    txtTenKH: {
      required,
      minLength: minLength(1)
    },
    txtPhieuThuHD: {
      integer
    },
    ChuyenPBH: {
      NoiDung: {
        required,
        minLength: minLength(1)
      }
    }
  },
  computed: {
    pmagd() {
      if (this.$route.params.magd != null) return this.$route.params.magd
      return ''
    },
    pkieuid() {
      if (this.$route.params.kieuid != null) return this.$route.params.kieuid
      return this.KieuId
    },
    HdKhIdInt() {
      if (this.HdKhId == null) return 0
      return parseInt(this.HdKhId)
    },
    KhachHangIdInt() {
      if (this.KhachHangId == null) return 0
      return parseInt(this.KhachHangId)
    },
  },
  methods: {

    disabledRange(date) {
      const currentDate = new Date();
      const thirtyDaysFromNow = new Date(currentDate);
      thirtyDaysFromNow.setDate(currentDate.getDate() + 30);
      return date <= currentDate || date > thirtyDaysFromNow;
    },
    disabledRange2: function(date) {
      var date1 = new Date()
      date1.setDate(date1.getDate() - 1)
      return date > new Date() || date < date1
    },
    getDmKenhThu: async function() {
      try {
        let data = []
        var input = {}
        if (!this.cty_newline) {
            console.log("this.KieuId",this.KieuId);
            
            input = { nghiepvu_id: this.KieuId == 1 ? 8 : 2, ht_tra_id: this.hinhThucTTId }
            data = this.GetData(await api.getDmKenhThuV3(this.axios, input))
            // debugger
        } else {
          data = this.GetData(await api.lay_ds_kenhthu_newlife(this.axios, input))
        }
        this.DmKenhThu = data?.map((x) => ({
          id: x?.kenhthu_id || x?.KENHTHU_ID || 0,
          text: x?.kenhthu || x?.KENHTHU || '',
          trangthai: Number(x?.trangthai || x?.TRANGTHAI || 0),
        })) || []
      } catch (e) {
        console.log('getDmKenhThu', e)
      }
    },
    getDongBoVina: async function() {
      let syncVina = await this.getAppConfig('OPEN_SYNC_VINA')
      if (syncVina != null && syncVina.dongbo == '1' && syncVina.keyvalue == 'TRUE') {
        this.OPEN_SYNC_VINA = true
        console.log('Set from AppConfig: OPEN_SYNC_VINA TRUE')
      } else {
        this.OPEN_SYNC_VINA = false
      }
      //
      let syncVinaTS = await this.getAppConfig('OPEN_SYNC_VINA_TRASAU')
      if (syncVinaTS != null && syncVinaTS.dongbo == '1' && syncVinaTS.keyvalue == 'TRUE') {
        this.OPEN_SYNC_VINA_TRASAU = true
        console.log('Set from AppConfig:  OPEN_SYNC_VINA_TRASAU TRUE')
      } else {
        this.OPEN_SYNC_VINA_TRASAU = false
      }
    },
    getDmKenhThuTGDD: async function() {
      var input = {}
      let data = this.GetData(await api.getDmKenhThuTGDD(this.axios, input))
      this.DmKenhThu = data?.filter((x) => x.kenhthu != null).map((x) => ({
          id: x.kenhthu_id,
          text: x.kenhthu == null ? '' : x.kenhthu,
          trangthai: x.trangthai
        })) || []
    },
    getDmTrangThaiTT: async function() {
      this.DmTrangThaiTT = []
      var input = {}
      let ds_trangthai = []
      if (!this.cty_newline) {
        ds_trangthai = this.GetData(await api.getDmTrangThaiTT(this.axios, input))
      } else {
        ds_trangthai = this.GetData(await api.lay_ds_trangthai_tt_newlife(this.axios, input))
      }
      this.DmTrangThaiTT = ds_trangthai?.map(e => ({...e, trangthai_id: Number(e.trangthai_id)})) || []
    },
    getDmHinhThucTra: async function() {
      let data = []
      var input = {}
      let arr = [3, 6, 8]
      try {
        if (!this.cty_newline) {
          // if (process.env.NODE_ENV == 'testing') {
            input = { nghiepvu_id: this.KieuId == 1 ? 1 : 2 }
            data = this.GetData(await api.getDmHinhThucTraV3(this.axios, input))
          // } else {
          //   data = this.GetData(await api.getDmHinhThucTra(this.axios, input))
          // }
          data = data.filter((x) => arr.filter((a) => a == (x?.ht_tra_id || x.HT_TRA_ID)).length == 0)
          this.DmHinhThucTra = data.map((x) => ({
            id: x?.ht_tra_id || x.HT_TRA_ID,
            text: x?.ht_tra || x?.HT_TRA || ''
          }))
          this.hinhThucTTId = this.DmHinhThucTra[0].id
        } else {
          data = this.GetData(await api.lay_ds_ht_tra_newlife(this.axios, input))
          this.DmHinhThucTra = data.map((x) => ({
            id: x?.ht_tra_id || x.HT_TRA_ID,
            text: x?.ht_tra || x?.HT_TRA || ''
          }))
          this.hinhThucTTId = this.DmHinhThucTra[0].id
        }
      } catch (ex) {
        this.DmHinhThucTra = []
        console.error(`Có lỗi lấy danh mục hình thức trả`, ex)
      }
    },
    getDmHinhThucTraTGDD: async function() {
      try {
        var input = {}
        let data = []
        // if (process.env.NODE_ENV == 'testing') {
          input = { nghiepvu_id: this.KieuId == 1 ? 1 : 2 }
          data = this.GetData(await api.getDmHinhThucTraV3(this.axios, input))
        // } else {
        //   data = this.GetData(await api.getDmHinhThucTra(this.axios, input))
        // }
        this.DmHinhThucTra = data
          .filter((x) => (x?.ht_tra_id || x.HT_TRA_ID) == 8)
          .map((x) => ({
            id: x?.ht_tra_id || x.HT_TRA_ID,
            text: x?.ht_tra || x?.HT_TRA || ''
          }))
        this.hinhThucTTId = this.DmHinhThucTra[0].id
      } catch (ex) {
        this.DmHinhThucTra = []
        console.error(`Có lỗi lấy danh mục hình thức trả TGDD`, ex)
        // this.ShowError(`Có lỗi lấy danh mục hình thức trả TGDD`)
      }
    },
    getDmNganHang: async function() {
      var input = {}
      let data = this.GetData(await api.getDmNganHang(this.axios, input))
      if (data && data.length > 0)
        this.DmNganHang = data.map((x) => ({
          id: x.NGANHANG_ID,
          text: x.TEN_NH == null ? ' ' : x.TEN_NH
        }))
    },
    getDmLoaiHoaDon: async function() {
      var input = { kieu: this.kieuhoadonId }
      let data = this.GetData(await api.getDmLoaiHoaDon(this.axios, input))
      if (data && data.length > 0)
        this.DmLoaiHoaDon = data.map((x) => ({
          id: x.loaihoadon_id,
          text: x.tentat == null ? ' ' : x.tentat
        }))
      if (this.DmLoaiHoaDon.length > 0) this.loaiHoaDonId = this.DmLoaiHoaDon[0].id
    },
    getDmLoaiHoaDon2: async function() {
      var input = { kieu: this.kieuhoadonId }
      let data = this.GetData(await api.getDmLoaiHoaDon2(this.axios, input))
      if (data && data.length > 0) {
        this.DmLoaiHoaDon = data.map((x) => ({
          id: x.loaihoadon_id,
          text: x.tentat == null ? ' ' : x.tentat
        }))
        if (this.DmLoaiHoaDon.length > 0) this.loaiHoaDonId = this.DmLoaiHoaDon[0].id
      }
    },
    RegRead: function(key, defaultval = '') {
      try {
        let val = localStorage.getItem(key)
        if (val == null) return defaultval
        return val
      } catch (ex) {
        console.log('RegRead', ex)
        return defaultval
      }
    },
    RegWrite: function(key, val) {
      try {
        localStorage.setItem(key, val)
      } catch (ex) {
        console.log('RegWrite', ex)
      }
    },
    getDmLoaiTien: async function() {
      var input = {}
      let data = this.GetData(await api.getDmLoaiTien(this.axios, input))
      this.DmLoaiTien = data.map((x) => ({
        id: x.LOAITIEN_ID,
        text: x.MA_LOAITIEN == null ? '' : x.MA_LOAITIEN
      }))
      this.loaiTienId = data[0].LOAITIEN_ID
    },
    getDmThuNgan: async function() {
      var nhanvien_id = this.$root.token.getNhanVienID()
      var loainv_id = 17 // Thu ngan
      var input = { loainv_id: loainv_id, nhanvien_id: nhanvien_id }
      let data = this.GetData(await api.getDmThuNgan(this.axios, input))
      this.DmThuNgan = data.map((x) => ({
        id: x.nhanvien_id,
        text: x.ten_nv == null ? '' : x.ten_nv
      }))
      if (this.DmThuNgan.length > 0) {
        this.thuNganId = nhanvien_id
      }
    },
    getSoHoaDon: async function() {
      let donviId = this.$root.token.getDonViID()
      let phanvung_id = this.$root.token.getPhanVungID()
      var input = {
        donvi_id: donviId,
        loaihoadon_id: this.loaiHoaDonId,
        phanvung_id: phanvung_id,
        seri_id: this.loaiSeriId
      }
      let data = this.GetData(await api.getSoHoaDon(this.axios, input))
      if (data && data.length > 0) {
        return data[0]
      }
      return null
    },
    getDmDonVi: async function() {
      var input = {}
      //this.DmKenhThu = this.GetData(await api.getDmDonVi(this.axios, input))
    },
    lay_huonggiao_quytrinh: async function(quytrinh_id, thutu_id) {
      try {
        var input = {
          quytrinh_id: quytrinh_id,
          thutu_id: thutu_id
        }
        let data = this.GetData(await api.lay_huonggiao_quytrinh(this.axios, input))
        return data
      } catch (err) {
        return 0
      }
    },
    lay_ds_huonggiao: async function() {
      var input = {
        vquyen_huonggiao: -1,
        vnhom_qt_id: 1, // DIEUHANH_THICONG
        vquytrinh_id: 0,
        vloaihd_id: 0,
        vdichvuvt_id: 0
      }
      let data = this.GetData(await api.lay_ds_huonggiao(this.axios, input))
      return data
    },
    sp_lay_ds_huonggiao: async function() {
      try {
        let nguoidung_id = this.$root.token.getNguoiDungID()
        var input = {
          nguoidung_id: nguoidung_id,
          nhom_qt_id: 1, // DIEUHANH_THICONG
          quytrinh_id: 0,
          loaihd_id: 0,
          dichvuvt_id: 0
        }
        let data = this.GetData(await api.sp_lay_ds_huonggiao(this.axios, input))
        return data
      } catch (err) {
        console.log('Lỗi lấy danh sách hướng giao', err)
        return []
      }
    },
    getThamSoMacDinh: async function() {
      var input = { kieu_id: -1 }
      let data = this.GetData(await api.getThamSoMacDinh(this.axios, input))
      //this.ThamSoMD=data
      return data
    },
    getThamSoMacDinhNguoiDung: async function(mats) {
      var nhanvien_id = this.$root.token.getNhanVienID()
      var input = { ma_ts: mats, nhanvien_id: nhanvien_id }
      let data = this.GetData(await api.getThamSoMacDinhNguoiDung(this.axios, input))
      return data
    },
    getAppConfig: async function(keyName) {
      var input = { keyname: keyName }
      let data = this.GetData(await api.getAppConfig(this.axios, input))
      return data
    },
    capnhat_status_hdtb: async function(hdtb_id, status) {
      try {
        var input = { hdtb_id: hdtb_id, status: status }
        let data = this.GetData(await api.capnhat_status_hdtb(this.axios, input))
        return data
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:2095 ~ capnhat_status_hdtb:function ~ err:', err)
      }
    },
    getCboLoaiHd: function(val) {
      // var nhanvien_id = this.$root.token.getNhanVienID()
      let check_hddt = this.chkHoaDonDienTu ? 1 : 0
      let loaihd_id = parseInt(val)
      let inputObj = {
        check_hddt: check_hddt,
        tag_form: this.state_tag,
        loaihd_id: loaihd_id
      }
      var input = { ds_para: JSON.stringify(inputObj) }
      api.getCboLoaiHd(this.axios, input).then((response) => {
        if (response.data.error_code == 'BSS-00000000') {
          let data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            let seris = check_hddt ? data.DS_SERI_DIENTU_V2 : data.DS_SERI_COMBOBOX_V2
            if (seris && seris.length > 0) {
              this.DmSeri = seris.map((x) => ({
                id: x.SERI_ID,
                text: x.SERI != null ? x.SERI : ''
              }))
        
              console.log('DmSeri', this.DmSeri)
              this.loaiSeriId = this.DmSeri[0].id
            } else {
              this.DmSeri = []
              this.loaiSeriId = 0
            }
          }
        }
      })
    },
    kiemTraKenhTiepNhan: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      let data = this.GetData(await api.kiemTraKenhTiepNhan(this.axios, input))
      //if (data != null && data == '2') {
      if (data != null && data == '1') {
        // HOTFIX thangdm1 2023-04-20 API nay tren form tra ra 2, nhung o API chi tra ve 0 / 1 -> so sanh 1
        return true
      }
      //this.ThamSoMD=data
      return false
    },
    kiemtra_phathanh_hddt_thanhtoan: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      let data = this.GetData(await api.kiemtra_phathanh_hddt_thanhtoan(this.axios, input))
      return data
    },
    getPhieuThanhToanThoaiTra: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      let data = this.GetData(await api.getPhieuThanhToanThoaiTra(this.axios, input))
      return data
    },
    fn_hienthi_nhap_loaitien_vnpti: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      let data = this.GetData(await api.fn_hienthi_nhap_loaitien_vnpti(this.axios, input))
      if (data == 'OK' || data == 'ok') this.DsThueBao.show_commands = true
      else this.DsThueBao.show_commands = false
    },
    clearText: function() {
      this.txtMaGD = ''
      this.clearText1()
    },
    clearText1: function() {
      this.txtChungTu = ''
      this.txtDiaChiCT = ''
      this.txtDiaChiKH = ''
      this.txtDienThoai = ''
      this.txtHoaDon = ''
      this.txtMaSoThue = ''
      this.txtSoMay = ''
      this.txtNguoiBL = ''
      this.txtThongTinTT = ''
      this.txtChungTu = ''
      this.txtTenKH = ''
      this.txtNguoiDaiDien = ''
      this.txtSoTaiKhoan = ''
      this.txtTongTienTra = 0
      this.txtTienBangChu = 'Không đồng'
      this.txtGhiChu = ''
      this.txtPhieuThuHD = ''
      this.txtSoBNTT = ''
      this.CtvId = ''
      //this.PhieuTtId = 0
    },
    clearData: function() {            
      this.DsThueBao.data = []
      this.DsThueBaoPopUp.data = []
      this.DsThueBaoCanTT.data = []
      this.DsPhieuYC.data = []
      this.DsKhoanMuc.data = []
      this.DsKhoanMucCT.data = []
      this.DsPhieuTT = []
      this.PhieuTtId = 0
      this.vphieu_id = 0
      this.kenhThuId = null
      // if (this.DmKenhThu != null && this.DmKenhThu.length > 0) this.kenhThuId = this.DmKenhThu[0].id
    },
    searchTheoMaGD: async function() {
      console.log('call searchTheoMaGD:MaGD', this.txtMaGD, ',')
      try {
        if (this.txtMaGD == '') {
          this.ShowAlert(`Bạn chưa nhập mã giao dịch để tìm kiếm thuê bao cần thanh toán !`)
          this.$refs.reftxtMaGD.focus()
          return
        }
        this.clearText1()
        this.clearData()
        this.loading(true)
        await this.getDsHopDongByMaGD()
        this.loading(true)
        await this.setCheckedThueBao()

    
        
      } catch (ex) {
        console.log('call searchTheoMaGD Error', ex)
        //  this.loading(true)
      } finally {
        this.loading(false)
      }
    },
    getDsHopDongByMaGD: async function() {
      console.log('call getDsHopDongByMaGD')
      if (this.txtMaGD == '') return
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let matinh = this.$root.token.getMaTinh()
      // sample
      var input = {
        ma_gd: this.txtMaGD,
        loaihd_id: 0,
        donvi_id: 0,
        kieu: 0,
        nhanvien_id: 0,
        donvi_dl_id: 0
      }
      if (this.KieuId == 1) {
        input.donvi_id = parseInt(donviId)
        input.kieu = 1
      } else {
        input.donvi_id = 0
        input.kieu = 2
      }
      let data = this.GetData(await api.getDsHopDongByMaGD(this.axios, input))
      if (this.KieuId == 1 && this.params.ts_checkbox_hddt == 1 && this.params.ts_hddt == 1) {
        this.chkHoaDonDienTu = true
        this.chkHoaDonDienTuStatus = true
        this.params.da_xuathd_dt = false
      } else {
        this.chkHoaDonDienTu = false
        this.chkHoaDonDienTuStatus = true
        this.params.da_xuathd_dt = false
      }
      if (data != null && data.length > 0) {
        console.log(`Tim thay hop dong`)
        if (parseInt(data[0].loaihd_id) == this.LoaiHopDong.CHUYENDOI_LH && parseInt(data[0].kieuld_id) == this.KieuLapDat.CHUYENDD_TRASAU_SANG_TRATRUOC) {
          this.ShowAlert(`Bạn không phải thanh toán cho hợp đồng chuyển đổi loại hình cho thuê bao di động từ Post->Card !`)
          this.loading(false)
          return
        }
        this.loaihd_id = data[0].loaihd_id
        this.params.mess_hddt_thanhtoan = ''
        this.params.kt_phathanh_hddt_thanhtoan = false
        this.DsHopDong.data = data
        this.vhdkh_id = data[0].hdkh_id
        this.HdKhId = data[0].hdkh_id
        this.HdTbId = data[0].hdtb_id
        this.kieuld_id =  data[0].kieuld_id
        this.CtvId = data[0].ctv_id
        this.KhachHangId = data[0].khachhang_id
        // Kiem tra neu la hop dong TGDD
        var hdtgdd = await this.getDsHopDongTGDD(this.HdKhId)
        if (hdtgdd && hdtgdd.length > 0 && this.HdKhId.length > 0) {
          console.log(`Gán lại kênh thu hợp đồng TGDD`)
          await this.getDmHinhThucTraTGDD()
          await this.getDmKenhThuTGDD()
        } else {
          await this.getDmHinhThucTra()
          await this.getDmKenhThu()
          console.log(`Không phải hợp đồng TGDD`)
        }
        //
        if (this.KieuId == 1) {
          let is_hd700 = await this.kiemTraKenhTiepNhan(this.HdKhId)
          if (is_hd700 == false) {
            let kt = this.DmKenhThu.filter((x) => x.id == this.kenhThuId)
            if (kt.length > 0) {
              this.trangThaiThanhToanId = Number(kt[0]?.trangthai || 0)
            }            
          } else {
            this.trangThaiThanhToanId = 0
          }
          if (this.params.ts_kt_hddt_thanhtoan) {
            let mess_hddt_thanhtoan = this.kiemtra_phathanh_hddt_thanhtoan(this.HdKhId)
            if (mess_hddt_thanhtoan != '1') this.params.kt_phathanh_hddt_thanhtoan = true
          }
        }
        // copy code form
        try {
          if (this.KieuId == 2) {
            if (data[0].kenhtn_id != null && data[0].kenhtn_id == 2 && matinh == 'HNI') {
              this.hinhThucTTIdStatus = true
              this.kenhThuIdtatus = true
            } else {
              this.hinhThucTTIdStatus = false
              this.kenhThuIdtatus = false
            }
          }
        } catch (exkenhtn) {
          console.log('🚀 ~ file: Payment.vue:2244 ~ exkenhtn~ ex:', exkenhtn)
        }
        this.txtMaGD = data[0].ma_gd
        this.vma_gd = data[0].ma_gd
        this.txtTenKH = data[0].ten_kh
        this.txtTenKH2 = data[0].ten_kh
        this.txtDiaChiKH = data[0].diachi_kh
        this.txtNguoiDaiDien = data[0].nguoi_dd
        this.txtDienThoai = data[0].so_dt
        try {
          if (data[0].ngay_yc != null && data[0].ngay_yc.length > 0) {
            //this.txtNgayYeuCau =data[0].ngay_yc // 2021-10-07 15:02:24.0
            this.txtNgayYeuCau = moment(data[0].ngay_yc.substr(0, 10), 'YYYY-MM-DD')
              .toDate()
              .format('DD-MM-YYYY')
          }
        } catch (ex) {}
        this.txtLoaiHD = data[0].ten_loaihd
        this.LoaiHDId = data[0].loaihd_id
        this.loaihd_id = data[0].loaihd_id
        this.LoaiThueBaoId = data[0].loaitb_id
        if (this.LoaiThueBaoId == this.LoaiHopDong.DATCOC_MOI) {
          this.transferType = 6 // gach no tra truoc
        } else {
          this.transferType = 14 // thanh toan HD
        }
        if (this.LoaiThueBaoId == this.LoaiHopDong.CHUYEN_QUYEN) {
          this.tsbtnHoanThienHS = 'Hoàn thiện'
          this.tsbtnHoanThienHSStatus = true
        } else if (this.LoaiThueBaoId == this.LoaiHopDong.THAY_DOI_TOCDO_ADSL) {
          this.tsbtnHoanThienHS = 'Đồng bộ'
          this.tsbtnHoanThienHSStatus = true
        } else this.tsbtnHoanThienHSStatus = false
        // Lay danh sach Hop dong khach hang theo mã khác hàng
        let hdkh = await this.getDsHopDongThanhToanTheoHDKHID(this.HdKhId)
        if (hdkh != null && hdkh.length > 0) {
          this.txtSoTaiKhoan = hdkh[0].stk
          this.txtMaSoThue = hdkh[0].mst
          this.txtDiaChiCT = hdkh[0].diachi_ct
          this.txtDiaChiCT2 = hdkh[0].diachi_ct
          this.TenThanhToan = hdkh[0].ten_tt
          if (hdkh[0].nganhang_id != null && hdkh[0].nganhang_id != '' && hdkh[0].nganhang_id != 0) {
            this.nganHangId = hdkh[0].nganhang_id
            this.chkNganHang = true
          } else {
            this.nganHangId = 0
            this.chkNganHang = false
          }
        } else {
          console.log(`getDsThanhToanTheoHDKHID`)
          let hdtt = await this.getDsThanhToanTheoHDKHID(this.HdKhId)
          if (hdtt && hdtt.length > 0) {
            this.txtSoTaiKhoan = hdtt[0].stk
            this.txtMaSoThue = hdtt[0].mst
            this.txtDiaChiCT = hdtt[0].diachi_ct
            this.txtDiaChiCT2 = hdtt[0].diachi_ct
            this.TenThanhToan = hdtt[0].ten_tt
          }
        }
        await this.HienThiDanhSachHDTB()
      } else {
        this.DsHopDong.data = []
        this.HdKhId = 0
        this.KhachHangId = 0
        this.PhieuTtId = 0
        //this.txtMaGD=""
        this.txtTenKH = ''
        this.txtLoaiHD = ''
        this.txtDiaChiKH = ''
        this.txtDiaChiCT = ''
        this.txtNguoiDaiDien = ''
        this.txtDienThoai = ''
        this.txtMaSoThue = ''
        this.txtSoTaiKhoan = ''
        this.txtNgayYeuCau = ''
        this.txtTongTienTra = 0
        this.txtTienBangChu = ''
        this.txtGhiChu = ''
        this.Fn.GachNo = false
        this.clearText1()
        await this.getDmHinhThucTra()
        await this.getDmKenhThu()
        this.ShowAlert(`Không tìm thấy thông tin Hợp đồng theo mã giao dịch ${this.txtMaGD}`)
        //await this.HienThiDanhSachHDTB()
      }
      if (this.KieuId == 1) {
        this.txtHoaDon = ''
        // hoangpkn : 05/07/2018. Thêm if (!chkHDDT.Checked)
        if (!this.chkHoaDonDienTu) {
          console.log(`Thieu API lay gia tri mac tinh cho loai hoa don,seri`)
          this.loaiHoaDonId = this.RegRead('loaihoadon_id', 0)
    
          this.loaiSeriId = this.RegRead('seri_hd_id', 0)
          // if (!string.IsNullOrEmpty(cm.RegRead("loaihoadon_id", "")))
          //     cboLoaiHD.SelectedValue = Convert.ToInt32(cm.RegRead("loaihoadon_id", ""));
          // else
          //     cboLoaiHD.SelectedValue = 0;
          // if (!string.IsNullOrEmpty(cm.RegRead("seri_hd_id", "")))
          //     cboSeri.SelectedValue = Convert.ToInt32(cm.RegRead("seri_hd_id", ""));
          // else
          //     cboSeri.SelectedValue = 0;
        }
      }
      if (this.KieuId == 2 || this.KieuId == 3) {
        console.log(`await this.getPhieuThanhToanThoaiTra(this.HdKhId)`, this.HdKhId)
        let ds_phieutt = await this.getPhieuThanhToanThoaiTra(this.HdKhId)
        this.DsPhieuTT = ds_phieutt
        if (ds_phieutt && ds_phieutt.length > 0) {
          this.PhieuTtId = ds_phieutt[0].phieutt_id
          this.PhieuTtIdIn = this.PhieuTtId
          this.hinhThucTTId = ds_phieutt[0].ht_tra_id ? Number(ds_phieutt[0].ht_tra_id) : null
          if (this.hinhThucTTId) await this.hinhThucTTIdChanged(this.hinhThucTTId)
          this.kenhThuId = ds_phieutt[0].kenhthu_id ? Number(ds_phieutt[0].kenhthu_id) : null
          this.txtNguoiBL = ds_phieutt[0].nguoi_bl
          this.trangThaiThanhToanId = ds_phieutt[0].trangthai ? Number(ds_phieutt[0].trangthai) : 0
          this.txtSoTaiKhoan = ds_phieutt[0].stk
          this.txtMaSoThue = ds_phieutt[0].mst
          this.txtChungTu = ds_phieutt[0].so_ct
          this.txtGhiChu = ds_phieutt[0].ghichu
          this.txtSoBNTT = ds_phieutt[0].soseri_bntt
          console.log('getDsHopDongByMaGD > hinhThucTTId, kenhThuId, trangThaiThanhToanId', this.hinhThucTTId, this.kenhThuId, this.trangThaiThanhToanId)
          if (ds_phieutt[0].hddt_fkey != null && ds_phieutt[0].hddt_fkey != '') {
            this.chkHoaDonDienTu = true
            this.chkHoaDonDienTuStatus = false
            this.params.da_xuathd_dt = true
            console.log('🚀 this.params.da_xuathd_dt:', this.params.da_xuathd_dt)
          }
          if (parseInt(this.trangThaiThanhToanId) == 1 || parseInt(this.trangThaiThanhToanId) == 2 || this.kenhThuId == this.KENHTHU.NOP_TIEN_SAU) {
            this.Fn.GachNo = false
            this.Fn.InHD = true
            this.tsbtnInHoaDon_Enabled = true
            this.Fn.PhieuThu = true
            this.tsbtnInPhieuThu_Enabled = true
          } else {
            this.Fn.GachNo = true
            this.Fn.InHD = true
            this.tsbtnInHoaDon_Enabled = true
            this.Fn.PhieuThu = true
            this.tsbtnInPhieuThu_Enabled = true
          }
          this.txtNgayTTStatus = true
          this.txtThongTinPTStatus = true
          if (ds_phieutt[0].nganhang_id != null && ds_phieutt[0].nganhang_id != '' && ds_phieutt[0].nganhang_id != '0') {
            this.nganHangId = ds_phieutt[0].nganhang_id
            this.chkNganHang = true
          } else {
            this.chkNganHang = false
            this.nganHangId = 0
          }
          if (ds_phieutt[0].ngay_nh != null && ds_phieutt[0].ngay_nh != '') {
            this.txtNgayNH = this.toDate(ds_phieutt[0].ngay_nh)
          } else {
            // this.txtNgayNH = moment(new Date().now).format("MM/DD/YYYY");
          }
          if (ds_phieutt[0].loaihoadon_id != null && ds_phieutt[0].loaihoadon_id != '') {
            this.loaiHoaDonId = ds_phieutt[0].loaihoadon_id
            this.chkHoaDonDienTuStatus = false
            this.chkLoaiHDStatus = false
            this.chkLoaiHD = true
            this.loaiHoaDonIdStatus = false
            this.loaiSeriIdStatus = false
            this.xuat_hd = true
            this.loaiSeriIdStatus = false
            this.loaiHoaDonIdStatus = false
            this.txtHoaDonStatus = false
            this.txtPhieuThuHDStatus = false
            this.thuNganIdStatus = false
            this.txtTyGiaStatus = false
            this.loaiTienIdStatus = false
            this.txtNgayHDStatus = false
            this.txtThongTinPTStatus = true
          } else {
            this.chkLoaiHDStatus = true
            this.chkLoaiHD = false
            this.chkHoaDonDienTuStatus = true
            this.loaiHoaDonId = 0
            this.loaiHoaDonIdStatus = false
            this.xuat_hd = false
          }
          //seri_id  
          if (ds_phieutt[0].seri_id != null && ds_phieutt[0].seri_id != '') {
            this.loaiSeriId = ds_phieutt[0].seri_id
            //this.XuatHoaDon = false;
          } else {
            this.loaiSeriId = 0
          }
          //soseri
          if (ds_phieutt[0] != null && ds_phieutt[0].soseri != '') {
            this.txtHoaDon = ds_phieutt[0].soseri
          } else {
            this.txtHoaDon = ''
          }
          //phieu thu
          if (ds_phieutt[0].ngay_hd != null && ds_phieutt[0].ngay_hd != '') {
            this.txtNgayHD = this.toDate(ds_phieutt[0].ngay_hd)
          } else {
            this.txtNgayHD = ''
          }
          this.txtPhieuThuHD = ds_phieutt[0].sophieu
          if (ds_phieutt[0].ngay_tt != null && ds_phieutt[0].ngay_tt != '') {
            this.txtNgayTTStatus = true
            this.txtNgayTT = this.toDate(ds_phieutt[0].ngay_tt)
            this.txtThongTinPTStatus = true
            this.txtThongTinPT = ds_phieutt[0].thongtin_pt
            this.params.kt_thutien_sautc = true
          } else {
            this.txtNgayTT = moment(new Date().now).format('DD/MM/YYYY')
            this.txtThongTinPT = ''
            if (parseInt(this.trangThaiThanhToanId) == 0) {
              this.txtNgayTTStatus = true
              this.txtThongTinPTStatus = true
            } else {
              this.txtNgayTTStatus = false
              this.txtThongTinPTStatus = false
            }
          }
        } else {
          this.$toast.error('Không lấy được TT phiếu đã thanh toán!')
          this.clearText1()
        }
      }
      // check ma mnp
      try {
        let result = await this.checkMnp(this.txtMaGD)
        if (result != null && result.length > 0 && Number(result[0].chuyenmang) == 1) {
          this.kenhThuId = this.KENHTHU.KHONG_THU_TIEN
        }
      } catch (ex) {
        console.log(`Error in check ma mnp ${ex}`)
      }
    },
    getDsHopDongThanhToanTheoHDKHID: async function(hdkhId) {
      //var input = { "hdkhId":1847659 }
      var input = { hdkhId: hdkhId }
      return this.GetData(await api.getDsHopDongThanhToanTheoHDKHID(this.axios, input))
    },
    getDsHopDongTGDD: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      return this.GetData(await api.getDsHopDongTGDD(this.axios, input))
    },
    getDsThanhToanTheoHDKHID: async function(hdkh_id) {
      var input = { hdkh_id: hdkh_id }
      return this.GetData(await api.getDsThanhToanTheoHDKHID(this.axios, input))
    },
    getTienHopDongTheoKhId: async function(hdkh_id) {
      //var input = { "hdkh_id":hdkh_id,"tthd_id":1}
      //var input={"hdkh_id":1847659,"tthd_id":1,"dungthu":0}
      let tthd_id = 0
      if (this.KieuId == 1) {
        tthd_id = 1
      }
      let dung_thu = 0
      var input = { hdkh_id: hdkh_id, tthd_id: tthd_id, dungthu: dung_thu }
      var data = this.GetData(await api.getTienHopDongTheoKhId(this.axios, input))
      return data
    },
    getTienKhuyenMaiTheoHdThueBao: async function(khuyenMaiId) {
      let arr = []
      this.DsKhoanMucCT.data = []
      if (this.DsThueBao.data.length > 0) {
        this.DsThueBao.data.forEach(function(item) {
          arr.push(item.hdtb_id)
        })
        //var input = {"dshdtb_id":[624938],"khoanmuctt_id":0} // { "khuyenMaiId":khuyenMaiId}
        var input = { dshdtb_id: arr, khoanmuctt_id: 0 } // { "khuyenMaiId":khuyenMaiId}
       // return this.GetData(await api.getTienKhuyenMaiTheoHdThueBao(this.axios, input)) Tú cmt
        // Tú thêm
       var khoiphuctl= this.GetData(await api.getTienKhuyenMaiTheoHdThueBao(this.axios, input))
       console.log("khoiphuctl 1223 ", khoiphuctl)
       var input2 = {     
        'loaitb_id':Number(this.LoaiThueBaoId) ,
        'hdtb_id':arr[0] ,
        'thuebao_id': 0,
        'kieu_id': 0 }
       var dichvuthem= this.GetData(await api.lay_ds_thuebao_dichvu(this.axios, input2))
       console.log("dichvuthem ", dichvuthem)
       if (dichvuthem.length>0 && dichvuthem!=null ){
        let filtereddichvuthem = dichvuthem.filter(x => x.dangky === '1')
        console.log("filtereddichvuthem ", filtereddichvuthem)
        let transformeddichvuthem = filtereddichvuthem.map(x => {

              return {
                  stt : "0", 
                  vat: x.vat_ld,
                  tien : x.tien,
                  khoanmuctt_id : x.dichvugt_id,
                  ten_kmtt : x.ten_dvgt
              };
          });
          console.log("transformeddichvuthem ", transformeddichvuthem)
          for (let i = 0; i < transformeddichvuthem.length; i++) {
            transformeddichvuthem[i].stt = i+2;
            transformeddichvuthem[i].vat = parseInt(transformeddichvuthem[i].vat);
            transformeddichvuthem[i].tien = parseInt(transformeddichvuthem[i].tien);
            transformeddichvuthem[i].khoanmuctt_id = parseInt(transformeddichvuthem[i].khoanmuctt_id);
          }
          transformeddichvuthem.forEach(x => {
            khoiphuctl.push(x);
          });
          console.log("data ", khoiphuctl)
          return khoiphuctl
        }
        else 
        return khoiphuctl
        // Tú hết thêm 

      }
      return []
    },
    getCTTienKhuyenMaiTheoHdThueBao: async function(khoanmuctt_id) {
      let arr = []
      if (this.DsThueBao.data.length > 0) {
        this.DsThueBao.data.forEach(function(item) {
          arr.push(item.hdtb_id)
        })
        // var input = {"dshdtb_id":[624938],"khoanmuctt_id":khoanmuctt_id} // { "khuyenMaiId":khuyenMaiId}
        var input = { dshdtb_id: arr, khoanmuctt_id: khoanmuctt_id } // { "khuyenMaiId":khuyenMaiId}
        return this.GetData(await api.getTienKhuyenMaiTheoHdThueBao(this.axios, input))
      }
      return []
    },
    select_selectThueBao: async function(i, item) {
      try {
        // this.Fn.GachNo = true
        if (this.DsThueBao.data.length == 1) {
          this.QuyTrinhId = item.quytrinh_id
          return
        }
        var grid = this.$refs.gridDsThueBao.grid
        var arrSelected = grid.getSelectedRecords()
        console.log('arrSelected', arrSelected)
        if (arrSelected.length > 0) {
          grid.clearSelection()
          grid.selectRow(i)
          this.DsKhoanMuc.data = []
          this.DsKhoanMucCT.data = []
        } else {
          var rows = grid.getRows()
          var arr = rows.map((x) => x.rowIndex)
          if (arr.filter((x) => x != i).length == 0) {
            this.Fn.GachNo = false
            this.DsKhoanMuc.data = []
            this.DsKhoanMucCT.data = []
          }
          grid.selectRows(arr.filter((x) => x != i))
          let data = await this.getTienKhuyenMaiTheoHdThueBao(0)
          if (data.length > 0) {
            if (parseInt(this.trangThaiThanhToanId) != 1) {
              this.Fn.GachNo = true
            }
            this.DsKhoanMuc.data = data
            await this.select_KhoanMuc(1, data[0])
          } else {
            this.Fn.GachNo = false
            this.DsKhoanMuc.data = []
            this.DsKhoanMucCT.data = []
          }
        }
        if (this.KieuId != 1 && this.kenhThuId == this.KENHTHU.NOP_TIEN_SAU) {
          this.Fn.GachNo = false
        }
        this.QuyTrinhId = item.quytrinh_id
      } catch (e) {
        console.log('select_selectThueBao', e)
      }
    },
    gridThueBao_DataBound(args) {
      // if (this.selIndex!=null && this.selIndex.length > 0) {
      //   this.$refs.gridDsThueBao.grid.selectRows(this.selIndex)
      //   this.selIndex = []
      // }
    },
    gridThueBao_RowDataBound(args) {
      try {
        this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 1905 ~ gridThueBao_RowDataBound ~ err', err)
      }
    },
    gridThueBao_ActionComplete(args) {
      try {
        this.setCheckedThueBao()
      } catch (err) {
        console.log('🚀gridThueBao_ActionComplete ~ err', err)
      }
    },
    gridCommand_DsThueBao: function (type, dataItem) {
      if (type == 'detail') {
        console.log('gridCommand_DsThueBao > dataItem', dataItem)
        this.$refs.frmThongTinTT.hdkh_id = dataItem['hdkh_id'] ? dataItem['hdkh_id'] : this.HdKhId
        this.$refs.frmThongTinTT.hdtb_id = dataItem['hdtb_id']
        this.$refs.frmThongTinTT.chophep_sua = 1
        this.$refs.frmThongTinTT.initData();
        this.$refs.frmThongTinTT.showModal();
      }
    },
    select_selectedItemsChangedDagan: async function(dataKeys) {
      this.DsThueBao.checked = dataKeys
      if (dataKeys.length > 0) {
        let data = await this.getTienKhuyenMaiTheoHdThueBao(0)
        if (data.length > 0) {
          if (this.KieuId == 1) {
            this.Fn.GachNo = true            
          } else {
            if (parseInt(this.trangThaiThanhToanId) != 1) {
              this.Fn.GachNo = true
            }
          }          
          this.DsKhoanMuc.data = data
          console.log("kiểm tra ", this.DsKhoanMuc.data)
          await this.select_KhoanMuc(1, data[0])
        } else {
          this.Fn.GachNo = false
          this.DsKhoanMuc.data = []
          this.DsKhoanMucCT.data = []
        }
      } else {
        this.Fn.GachNo = false
        this.DsKhoanMuc.data = []
        this.DsKhoanMucCT.data = []
      }
      if (this.KieuId != 1 && this.kenhThuId == this.KENHTHU.NOP_TIEN_SAU) {
        this.Fn.GachNo = false
      }
    },
    select_KhoanMuc: async function(i, item) {
      let data = await this.getCTTienKhuyenMaiTheoHdThueBao(item.khoanmuctt_id)
      this.DsKhoanMucCT.data = data
      // let gridInstance = this.$refs.gridDsKhoanMucCT.grid.ej2Instances
      // gridInstance.autoFitColumns(['ten'])
      // gridInstance.width = gridInstance.getContentTable().offsetWidth
    },
    HienThiDoiTuong: function() {
      if (this.chkLoaiHD) {
        if (parseInt(this.trangThaiThanhToanId) == 1) {
          // Da thu tien
          this.loaiHoaDonIdStatus = true
          this.chkLoaiHDStatus = true
          this.loaiSeriIdStatus = true
          this.txtPhieuThuHDStatus = true
          this.thuNganIdStatus = true
          this.txtTyGiaStatus = true
          this.loaiTienIdStatus = true
          this.txtNgayHDStatus = true
          if (this.KieuId == 1) {
            if ([this.HinhThucTra.UY_NHIEM_CHI, this.HinhThucTra.UY_NHIEM_THU].filter((x) => x == this.hinhThucTTId).length > 0) {
              this.txtChungTuStatus = true
            } else {
              this.txtChungTuStatus = false
            }
          } else {
            this.txtNgayTTStatus = true
            this.txtThongTinPTStatus = false
            this.txtThongTinTT = ''
            this.txtChungTuStatus = false
          }
        } else {
          // chua thu tien
          this.loaiHoaDonIdStatus = true
          this.loaiSeriIdStatus = true
          this.txtPhieuThuHDStatus = true
          this.chkLoaiHDStatus = true
          this.thuNganIdStatus = true
          this.txtTyGiaStatus = true
          this.loaiTienIdStatus = true
          this.txtNgayHDStatus = true
          if (this.KieuId == 1) {
            this.txtNgayTTStatus = true
            this.txtThongTinPTStatus = false
            this.txtThongTinTT = ''
            this.txtChungTuStatus = false
          } else {
            if ([this.HinhThucTra.UY_NHIEM_CHI, this.HinhThucTra.UY_NHIEM_THU].filter((x) => x == this.hinhThucTTId).length > 0) {
              this.txtChungTuStatus = true
            } else {
              this.txtChungTuStatus = false
            }
          }
        }
      }
      // Chua xuat hoa don
      else {
        this.chkHoaDonDienTu = false
        if (parseInt(this.trangThaiThanhToanId) == 1) {
          // Da thu tien
          this.chkLoaiHDStatus = false
          this.loaiHoaDonIdStatus = false
          this.loaiSeriIdStatus = false
          this.txtPhieuThuHDStatus = false
          this.thuNganIdStatus = false
          this.txtTyGiaStatus = false
          this.loaiTienIdStatus = false
          this.txtNgayHDStatus = false
          if (this.KieuId == 1) {
            this.txtNgayTTStatus = false
            this.txtThongTinPTStatus = false
            this.txtThongTinPT = ''
            if ([this.HinhThucTra.UY_NHIEM_CHI, this.HinhThucTra.UY_NHIEM_THU].filter((x) => x == this.hinhThucTTId).length > 0) {
              this.txtChungTuStatus = true
            } else {
              this.txtChungTuStatus = false
            }
          } else {
            this.txtNgayTTStatus = true
            this.txtThongTinPTStatus = true
            this.txtChungTuStatus = false
          }
        } else {
          // chua thu tien
          this.chkLoaiHDStatus = false
          this.loaiHoaDonIdStatus = false
          this.loaiSeriIdStatus = false
          this.txtPhieuThuHDStatus = false
          this.thuNganIdStatus = false
          this.txtTyGiaStatus = false
          this.loaiTienIdStatus = false
          this.txtNgayHDStatus = false
          if (this.KieuId == 1) {
            this.txtNgayTTStatus = false
            this.txtThongTinPTStatus = false
            this.txtThongTinPT = ''
            this.txtChungTuStatus = false
          } else {
            this.txtNgayTTStatus = true
            this.txtThongTinPTStatus = true
            this.txtThongTinPT = ''
            if ([this.HinhThucTra.UY_NHIEM_CHI, this.HinhThucTra.UY_NHIEM_THU].filter((x) => x == this.hinhThucTTId).length > 0) {
              this.txtChungTuStatus = true
            } else {
              this.txtChungTuStatus = false
            }
          }
        }
      }
    },
    HienThiDanhSachHDTB: async function() {
      console.log(`Call HienThiDanhSachHDTB`)
      var dtHDTB = await this.getTienHopDongTheoKhId(this.HdKhId)
      this.dtHDTB = dtHDTB
      let tongtien = 0
      let tien = 0
      let tien_km = 0
      let tongtienkh = 0
      if (dtHDTB && dtHDTB.length > 0) {
        await this.fn_hienthi_nhap_loaitien_vnpti(this.HdKhId)
        this.DsThueBao.data = dtHDTB
        dtHDTB.forEach(function(item) {
          if (item.trangthai_hd !== 'Thoái trả') {
            tien = parseInt(item.tien_goc)
            tien_km = parseInt(item.tien_kmai)
            tongtien += tien + tien_km
            tongtienkh += tongtien
          }
          item.chonTT = 1
        })
        this.HdTbId = dtHDTB[0].hdtb_id
        this.QuyTrinhId = dtHDTB[0].quytrinh_id
      } else {
        // this.txtTienBangChu = await this.MoneyToString
        this.ShowAlert(`Không tìm thấy dữ liệu tiền thanh toán theo mã thuê bao ! với mã GD: ${this.txtMaGD}`)
      }
      this.txtTongTienTra = tongtien
      let tienchu = await this.readMoney(tongtien)
      this.txtTienBangChu = tienchu
      // this.$refs.gridDsThueBao.grid.autoFitColumns(['ma_tb'])
      //let gridInstance = this.$refs.gridDsThueBao.grid.ej2Instances
      //gridInstance.width = gridInstance.getContentTable().offsetWidth
    },
    setCheckedThueBao: function() {
      // Set auto checked
      try {
        if (this.DsThueBao.data && this.DsThueBao.data.length > 0) {          
          var grid = this.$refs.gridDsThueBao.grid
          var rows = grid.getRows()
          if (rows) {
            var arr = rows.map((x) => x.rowIndex)
            this.$refs.gridDsThueBao.grid.selectRows(arr)
          }
          console.log('setCheckedThueBao', this.DsThueBao.data)
        }
      } catch (err) {
        console.log('setCheckedThueBao:Error', err)
      }
      //end
    },
    getPhieuIdByHdkhId: async function(hdkhid, huonggiaoId) {
      //var input = {"hdkh_id":1930037,"huonggiao_id":142}
      var input = { hdkh_id: hdkhid, huonggiao_id: huonggiaoId }
      return this.GetData(await api.getPhieuIdByHdkhId(this.axios, input))
    },
    lay_thongtin_ctv_thanhtoanhd: async function(hdkhid) {
      //var input = {"hdkh_id":1930037,"huonggiao_id":142}
      var input = { hdkhId: hdkhid }
      return this.GetData(await api.lay_thongtin_ctv_thanhtoanhd(this.axios, input))
    },
    sp_lay_dulieu_hoadon: async function(loaihoadon_id, seri_id, soseri) {
      var input = { loaihoadon_id: loaihoadon_id, seri_id: seri_id, soseri: soseri }
      return this.GetData(await api.sp_lay_dulieu_hoadon(this.axios, input))
    },
    lay_thang_no: async function() {
      try {
        var input = {}
        let chukyno = this.GetData(await api.lay_thang_no(this.axios, input))
        console.log('🚀 chukyno:', chukyno)
        return chukyno
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:2795 ~ lay_thang_no:function ~ err:', err)
        this.ShowError(`Có lỗi khi lấy chu kỳ nợ !`)
        return 0
      }
    },
    lay_chuky_nohoadon: async function() {
      try {
        var input = {}
        let chukyno = this.GetData(await api.lay_chuky_nohoadon(this.axios, input))
        console.log('🚀 lay_chuky_nohoadon:', chukyno)
        return '01' + chukyno.substr(4, 2) + chukyno.substr(0, 4)
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:2795 ~ lay_chuky_nohoadon:function ~ err:', err)
        this.ShowError(`Có lỗi khi lấy chu kỳ nợ !`)
        return 0
      }
    },
    checkMnp: async function(ma_gd) {
      try {
        var input = { ma_gd: ma_gd }
        return this.GetData(await api.checkMnp(this.axios, input))
      } catch (err) {
        console.log('~ checkMnp:function ~ err:', err)
        return null
      }
    },
    getDsNguoiBaoLanh: async function(hdtb_id) {
      try {
        var input = { hdtb_id: hdtb_id }
        //input = {"hdtb_id":1452966}
        this.DsNguoiBaoLanh.data = this.GetData(await api.getDsNguoiBaoLanh(this.axios, input))
      } catch (ex) {
        this.DsNguoiBaoLanh.data = []
      }
    },
    GetData: function(response) {
      if (response.data.error == 200 || response.data.error == '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    openDsCanTT: function() {},
    ShowNguoiBL: async function() {
      this.$refs.dlgTimKiemNVTC.openDialog()
      //let a= await  this.getDsNguoiBaoLanh()
      // this.$refs.dialogObjNguoiBL.show();
      // if(this.btnNguoiBLStatus){
      // }
    },
    SelectNguoiBaoLanhItem: function(i, item) {
      this.DsNguoiBaoLanh.selectItem = item
    },
    SelectNguoiBaoLanh: function(i, item) {
      this.txtNguoiBL = item.daily
      this.$refs.dialogObjNguoiBL.hide()
    },
    ChonNguoiBL: function() {
      this.$refs.dialogObjNguoiBL.hide()
      this.txtNguoiBL = this.DsNguoiBaoLanh.selectItem.daily
    },
    ShowAlert: function(message) {
      this.$toast.error(message)
    },
    ShowError: function(message) {
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      this.$toast.success(message)
    },
    btnLayMaGD_Click: async function() {
      try {
        this.loading(true)
        this.chkHoaDonDienTu = false
        await this.openDialogTT()
      } catch (err) {
        console.log('SearchPhieuTTShow:', err)
      } finally {
        this.loading(false)
        this.$refs.dialogObjDsCanTT.show()
      }
    },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace('.', ',')
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
    },
    async openDialogTT() {
      this.DsThueBaoCanTT.data = []
      this.DsThueBaoCanTT.txtMaGiaoDich = ''
      this.DsThueBaoCanTT.txtSoMay = ''
      this.DsThueBaoCanTT.TTHopDong.checked = true
      this.DsThueBaoCanTT.TTHopDong.val = 1
      this.DsThueBaoCanTT.LoaiHD.checked = false
      this.DsThueBaoCanTT.DichVu.checked = false
      this.DsThueBaoCanTT.LoaiHinh.checked = false
      await Promise.all([this.TimKiemTBCanThanhToan()])
        .then((r) => {})
        .catch((err) => {
          console.log('SearchPhieuTTShow:TimKiemTBCanThanhToan', err)
        })
        .finally(() => {
          // this.$refs.dialogObjDsCanTT.show()
        })
      if (!(this.loaiHopDongId > 0)) {
        this.DsThueBaoCanTT.LoaiHD.val = this.DsThueBaoCanTT.LoaiHD.list[0].id
      } else {
        this.DsThueBaoCanTT.LoaiHD.val = this.loaiHopDongId
      }
      if (this.dichvuId > 0) {
        this.DsThueBaoCanTT.DichVu.val = this.dichvuId
      } else {
        this.DsThueBaoCanTT.DichVu.val = this.DsThueBaoCanTT.DichVu.list[0].id
      }
      this.DsThueBaoCanTT.LoaiHinh.val = this.DsThueBaoCanTT.LoaiHinh.list[0].id
    },
    openDialog() {
      this.OpenBtn = false
      this.DsThueBaoCanTT.data = []
      this.DsThueBaoCanTT.txtMaGiaoDich = ''
      this.DsThueBaoCanTT.txtSoMay = ''
      this.DsThueBaoCanTT.TTHopDong.checked = true
      this.DsThueBaoCanTT.TTHopDong.val = 1
      this.DsThueBaoCanTT.LoaiHD.checked = false
      this.DsThueBaoCanTT.DichVu.checked = false
      this.DsThueBaoCanTT.LoaiHinh.checked = false
      if (!(this.loaiHopDongId > 0)) {
        this.DsThueBaoCanTT.LoaiHD.val = this.DsThueBaoCanTT.LoaiHD.list[0].id
      } else {
        this.DsThueBaoCanTT.LoaiHD.val = this.loaiHopDongId
      }
      if (this.dichvuId > 0) {
        this.DsThueBaoCanTT.DichVu.val = this.dichvuId
      } else {
        this.DsThueBaoCanTT.DichVu.val = this.DsThueBaoCanTT.DichVu.list[0].id
      }
      this.DsThueBaoCanTT.LoaiHinh.val = this.DsThueBaoCanTT.LoaiHinh.list[0].id
    },
    closeDialog() {
      // this.OpenBtn = true
    },
    open() {
      // this.$refs.p_ma_tt_tb.focus()
    },
    KiemTraDuLieu: async function() {
      this.$v.$touch()
      let hasFocus = false
      let errorMessage = []
      let phanvungId = this.$root.token.getPhanVungID()
      if (this.$v.$invalid) {
        if (!this.$v.txtTenKH.required) {
          errorMessage.push(`Tên khách hàng không được để trống !`)
        }
        if (!this.$v.txtTenKH.minLength) {
          errorMessage.push(`Tên khách hàng không được nhỏ hơn ${this.$v.txtTenKH.$params.minLength.min} ký tự`)
        }
        if (!this.kenhThuId) {
          errorMessage.push(`Bạn chưa chọn kênh thu !`)
          try {
            if (!hasFocus) {
              hasFocus = true
              this.select2FocusById('kenhThuId')
            }
          } catch (err) {
            console.log('🚀select2FocusById', err)
          }
        }
        if (this.chkHoaDonDienTu && this.chkHoaDonDienTuVisible) {
          if (this.kenhThuId == this.KENHTHU.KHONG_THU_TIEN || this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN) {
            errorMessage.push(`Không được xuất HĐĐT với kênh thu hoặc hình thức trả này ! `)
          }
          if (this.txtMaSoThue == '') {
            errorMessage.push(`Chưa có mã số thuế thanh toán. Bạn muốn tiếp tục ?`)
          }
          if (this.params.bo_thanhtoan_hddt_dc == 1 && this.LoaiHDId == this.LoaiHopDong.DATCOC_MOI) {
            errorMessage.push(`Không được xuất HĐĐT đối với phụ lục này !`)
          }
        }
        // Chech hoa don
        if (this.chkLoaiHDStatus && this.chkLoaiHD) {
          if (!(this.loaiHoaDonId > 0)) {
            errorMessage.push(`Bạn chưa chọn loại hóa đơn !`)
          }
          if (!(this.loaiSeriId > 0)) {
            errorMessage.push(`Bạn chưa chọn seri hóa đơn !`)
            try {
              if (!hasFocus) {
                hasFocus = true
                this.select2FocusById('loaiSeriId')
              }
            } catch (err) {
              console.log('🚀this.$refs.refloaiSeriId.focus()', err)
            }
          }
          if (this.txtHoaDon == '' && !this.chkHoaDonDienTu) {
            errorMessage.push(`Bạn chưa nhập số hóa đơn !`)
            try {
              if (!hasFocus) {
                hasFocus = true
                this.$refs.reftxtHoaDon.focus()
              }
            } catch (err) {}
          }
        }
        if (this.chkLoaiHD) {
          if (this.loaiSeriId == null || this.loaiSeriId == 0) {
            try {
              if (!hasFocus) {
                hasFocus = true
                this.select2FocusById('loaiSeriId')
              }
            } catch (err) {
              console.log('🚀this.$refs.refloaiSeriId.focus()', err)
            }
            errorMessage.push(`Bạn chưa chọn seri hóa đơn !`)
          }
        }
        // Kiem tra kenh thu khong thu tien
        if (!this.cty_newline && (this.kenhThuId == this.KENHTHU.KHONG_THU_TIEN || this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN)) {
          if (parseInt(this.txtTongTienTra) > 0) {
            errorMessage.push(`Hóa đơn khách hàng có khoản mục khác 0. bạn không được chọn hình thức trả Không thu tiền !`)
          }
        }
        
        // if (process.env.NODE_ENV == 'testing') {
          if (this.KieuId == 2) {
            if (Number(this.trangThaiThanhToanId) != 1) errorMessage.push('Trạng thái chưa thu tiền, bạn hãy chọn lại Hình thức trả / Kênh thu!')
          }
          //  Hợp đồng 700 - tag = 1 - is_hd700
          let is_hd700 = await this.kiemTraKenhTiepNhan(this.HdKhId)
          if (this.KieuId == 1 && is_hd700 && parseInt(this.txtTongTienTra) > 0) {
            if (this.kenhThuId != this.KENHTHU.NOP_TIEN_SAU) {
              errorMessage.push('Bạn phải chọn kênh thu: Nộp tiền sau!')
            }
          }
          
        if (parseInt(this.txtTongTienTra) == 0) {
          if (this.kenhThuId != this.KENHTHU.KHONG_THU_TIEN && this.hinhThucTTId != this.HinhThucTra.KHONG_THU_TIEN && this.kenhThuId != this.KENHTHU.KHAC) {          
            errorMessage.push(`Hóa đơn khách hàng tổng tiền = 0. Bạn phải chọn hình thức trả 'Không thu tiền' !`)
          }
        
          if (this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN && this.kenhThuId != this.KENHTHU.KHAC) {
            errorMessage.push(`Hóa đơn khách hàng tổng tiền = 0. Bạn phải chọn hình thức trả 'Không thu tiền' !`)
          }
        }
        // Kiem tra kenh thu cong tac vien
        if (this.kenhThuId == this.KENHTHU.KENHBANHANG) {
          //
          if (this.CtvId == '') {
            errorMessage.push(`Hợp đồng chưa được gán với CTV !`)
          }
        }
        if (this.hinhThucTTId == 2 && (this.kenhThuId == this.KENHTHU.BAO_LANH || this.kenhThuId == this.KENHTHU.NOP_TIEN_SAU)) {
          //
          if (this.txtNguoiBL == '') {
            errorMessage.push(`Bạn phải chọn tên người bảo lãnh khi chọn kênh thu: Bảo lãnh hoặc Nộp tiền sau !`)
            try {
              this.$refs.reftxtNguoiBL.focus()
            } catch (err) {}
          }
        }
        //        
        if (phanvungId == '26') {
          //VI_VNPTPAY
          if (this.hinhThucTTId == this.HinhThucTra.VI_VNPTPAY) {
            let data = await this.lay_thongtin_ctv_thanhtoanhd(this.HdKhId)
            if (data == null || data.length == 0) {
              errorMessage.push(`Hợp đồng chưa được gán với cộng tác viên !`)
            }
          }
          //
          if (this.chkHoaDonDienTu && this.chkLoaiHD && this.txtHoaDon != '') {
            // Kiem tra  so hoa don da ton tai
            let data = await this.sp_lay_dulieu_hoadon(this.loaiHoaDonId, this.loaiSeriId, this.txtHoaDon)
            if (data != null && data.length > 0) {
              let ten_dv = ''
              if (data[0]['donvi_cn'] != null && data[0]['donvi_cn'] != '') {
                ten_dv = data[0]['donvi_cn']
              } else {
                let dvId = data[0].donvi_id
                let datadv = this.GetData(await api.fn_tt_donvi(this.axios, { param: dvId, type: 2 }))
                if (datadv != null) {
                  ten_dv = datadv
                }
              }
              let ngay_cn = moment(data[0].ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
              errorMessage.push(`Hóa đơn đã được sử dụng. Mã giao dịch : ${data[0].ma_gd}. Người sử dụng: '${data[0].nguoi_cn}', đơn vị: ${ten_dv}, ngày: ${ngay_cn}`)
            }
          }
        }
        // Kiem tra ngan hang
        if (this.chkNganHang && this.nganHangIdStatus) {
          if (!(this.nganHangId > 0)) {
            errorMessage.push(`Bạn chưa chọn ngân hàng thanh toán !`)
            try {
              if (!hasFocus) {
                hasFocus = true
                this.select2FocusById('nganHangId')
              }
            } catch (err) {
              console.log('🚀this.$refs.refloaiSeriId.focus()', err)
            }
          }
        }
        //
        var grid = this.$refs.gridDsThueBao.grid
        var arrSelected = grid.getSelectedRecords()
        if (arrSelected.length <= 0) {
          errorMessage.push(`Bạn chưa chọn thuê bao cần thanh toán !`)
        }
        //
        if (errorMessage.length > 0) {
          this.$toast.error(errorMessage[0])
          // this.$toast.error(errorMessage.join('\n'))
        }
        console.log('KiemTraDuLieu', errorMessage)
        return errorMessage
      }
      console.log('KiemTraDuLieu', errorMessage)
      return errorMessage
    },
    InHD: function() {
      this.ShowAlert(`...`)
    },
    PhieuThu: function() {
      this.ShowAlert(`...`)
    },
    async tsbtnInPhieuThu_Click() {
      try {
        if (this.txtMaGD != '' && (this.txtMaGDTT == null || this.txtMaGDTT.length <= 3)) {
          this.txtMaGDTT = this.txtMaGD
        }
        if (this.txtMaGDTT == null || this.txtMaGDTT.length <= 3) {
          this.ShowError(`Chưa có mã giao dịch !`)
          return
        }
        this.$refs.refselectPrintType.openDialog(this.PhieuTtIdIn)
        //this.$refs.popupInPhieuThu.showModal()
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async InPhieuThu() {
      try {
        if (this.txtMaGD != '' && (this.txtMaGDTT == null || this.txtMaGDTT.length <= 3)) {
          this.txtMaGDTT = this.txtMaGD
        }
        if (this.txtMaGDTT == null || this.txtMaGDTT.length <= 3) {
          this.ShowError(`Chưa có mã giao dịch !`)
          return
        }
        try {
          this.$refs.popupInPhieuThu.ma_gd = this.txtMaGDTT
        } catch (err) {}
        this.$refs.popupInPhieuThu.showModal()
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    PhieuBienNhan: function() {
      // alert('1313')
      //let chkCheckAll = $('input.e-checkselectall')[0].prop("checked", true).trigger("change")
      // this.ShowAlert(`...`)
      //this.txtTongTienTra = 1213123
      //let tienId = document.getElementById('txtTongTienTra')
      //tienId.focus()
      // Vue.nextTick(function () {
      //   document.getElementById('txtTongTienTra').focus()
      // })
    },
    async tsbtnPhieuBienNhan_Click() {
      if (this.KieuId == 2 && this.HdKhIdInt)
        try {
          this.loading(true)
          await this.InBienNhan(this.txtMaGD)
        } catch (error) {
        } finally {
          this.loading(false)
        }
      else
        try {
          this.modelIn = {}
          // this.modelIn.ma_gd = this.txtMaGD
          // this.modelIn.vphieutt_id = this.PhieuTtId
          // this.modelIn.vloaihd_id = this.LoaiHDId
          // this.modelIn.hdkh_id = this.HdKhId
          // this.modelIn.hdtb_id = this.HdTbId
          // console.log('modelIn', this.modelIn)
          this.$refs.popupInHopDong.frmProps.hdkh_id = this.HdKhIdInt
          this.$refs.popupInHopDong.frmProps.hdtb_id = this.HdTbId
          this.$refs.popupInHopDong.hdkh_id = this.HdKhIdInt
          this.$refs.popupInHopDong.hdtb_id = this.HdTbId
          this.$refs.popupInHopDong.frmProps.txtMaGD = this.txtMaGDTT
          this.$refs.popupInHopDong.frmProps.seri_id = this.loaiSeriId
          this.$refs.popupInHopDong.frmProps.loaihoadon_id = this.loaiHoaDonId
          this.$refs.popupInHopDong.frmProps.hoadon = this.txtHoaDon
          console.log('tsbtnPhieuBienNhan_Click-input', this.$refs.popupInHopDong.frmProps)
          this.$refs.popupInHopDong.showModal()
        } catch (err) {
          console.log('tsbtnPhieuBienNhan_Click', err)
        }
    },
    async tsbtnPhieuBienNhan_Click_Old() {
      try {
        this.modelIn = {}
        // this.modelIn.ma_gd = this.txtMaGD
        // this.modelIn.vphieutt_id = this.PhieuTtId
        // this.modelIn.vloaihd_id = this.LoaiHDId
        // this.modelIn.hdkh_id = this.HdKhId
        // this.modelIn.hdtb_id = this.HdTbId
        // console.log('modelIn', this.modelIn)
        this.$refs.popupInHopDong.frmProps.hdkh_id = this.HdKhIdInt
        this.$refs.popupInHopDong.frmProps.hdtb_id = this.HdTbId
        this.$refs.popupInHopDong.hdkh_id = this.HdKhIdInt
        this.$refs.popupInHopDong.hdtb_id = this.HdTbId
        this.$refs.popupInHopDong.frmProps.txtMaGD = this.txtMaGDTT
        this.$refs.popupInHopDong.frmProps.seri_id = this.loaiSeriId
        this.$refs.popupInHopDong.frmProps.loaihoadon_id = this.loaiHoaDonId
        this.$refs.popupInHopDong.frmProps.hoadon = this.txtHoaDon
        console.log('tsbtnPhieuBienNhan_Click-input', this.$refs.popupInHopDong.frmProps)
      } catch (err) {
        console.log('tsbtnPhieuBienNhan_Click', err)
      } finally {
        this.$refs.popupInHopDong.showModal()
      }
    },
    async InHoaDon(vma_bc) {
      this.dsGiaTri.push(this.PhieuTtId)
      this.$refs.popupXemBaoCao.XemNgay()
    },
    PhieuTra: function() {
      // this.Fn.PhieuTra = false
      try {
        this.$refs.refdialogObjPhieuTra.txtMaGd = ''
      } catch (err) {
        console.log('PhieuTra-err', err)
      } finally {
        this.$refs.refdialogObjPhieuTra.openDialog()
      }
      // this.$refs.dialogObjPhieuTra.show()
      // this.ShowAlert(`Waiting API - UR2.0.002_Danh sach phieu thanh toan`)
    },
    phieuTraModalDlgOpen: function() {
      //this.$ref.phieuTraModalDlg.Clear()
      this.phieuTraModalDlg.DsPhieuTra.data = []
      this.clearDataPhieuTra = true
    },
    phieuTraModalDlgClose: function() {
      this.clearDataPhieuTra = false
      this.Fn.PhieuTra = true
    },
    GiaoPhieu: async function() {
      let loaihd = 1
      let trangthaiphieu = 0
      let huonggiao = 0
      let vhuonggiao_id = await this.lay_huonggiao_quytrinh(this.QuyTrinhId, 2)
      let dt_hg = await this.sp_lay_ds_huonggiao()
      let hg_id = dt_hg.filter((x) => x.huonggiao_id == vhuonggiao_id).length
      if (hg_id > 0) {
        if (vhuonggiao_id != null && vhuonggiao_id != 0) {
          huonggiao = vhuonggiao_id
        }
      }
      this.giaoPhieuViewDialog.input = {
        vhuonggiao_id: huonggiao,
        // vloai_hd:this.state.loaihd_id,
        // vdichvuvt_id:this.state.dichvuvt_id,
        // vma_gd: this.state.selectedPhieu.ma_gd,
        // vquytrinh: this.state.quytrinh_id,
        vtrangthaiphieu: 1
      }
      this.GiaoPhieuView_OnClick()
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    GiaoPhieu_New: async function() {
      try {
        if (this.params.giaophieu_theo_hd == true) {
        }
        let madv = 0
        // if (this.DsThueBao.data.length > 0) madv = this.DsThueBao.data[0].dichvuvt_id
        let loaihd = 1
        let trangthaiphieu = 0
        let huonggiao = 0
        let vhuonggiao_id = await this.lay_huonggiao_quytrinh(this.QuyTrinhId, 2)
        let dt_hg = await this.sp_lay_ds_huonggiao()
        let hg_id = dt_hg.filter((x) => x.huonggiao_id == vhuonggiao_id).length
        console.log('vhuonggiao_id:', vhuonggiao_id, 'hg_id:', hg_id)
        if (hg_id > 0) {
          if (vhuonggiao_id != null && vhuonggiao_id != 0) {
            huonggiao = vhuonggiao_id
          }
          //
          this.loading(true)
          try {
            this.popupComponentAttr = {
              isPopup: true,
              p_huonggiaoId: huonggiao,
              p_kieu: 1
            }
            this.userComponentModule = () => import('@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue')
            this.loading(true)
          } catch (err) {
            console.log('🚀 ~ file: Payment.vue ~ line 2356 ~ err:GiaoPhieu', err)
          } finally {
          }
        } else {
          console.log('Khong map huong giao:', 'vhuonggiao_id:', vhuonggiao_id, 'hg_id:', hg_id)
          try {
            this.popupComponentAttr = {
              isPopup: true
            }
            this.userComponentModule = () => import('@/modules/contract/setup/HandoverTicket/HandoverTicket_content.vue')
          } catch (err) {
            console.log('🚀 ~ file: Payment.vue ~ line 2383 ~ err:GiaoPhieu', err)
          } finally {
          }
        }
      } catch (err) {
        this.ShowError(err.mesage)
      } finally {
        this.loading(true)
        await this.$refs.refHandoverTicketDialog1.show(true)
        this.loading(false)
      }
      //this.ShowAlert(`Waiting API`)
    },
    onCloseGiaoPhieu() {
      console.log('onCloseGiaoPhieu')
    },
    TonPhieu: async function() {
      // this.$refs.dialogObjPhieuTon.showPhieuTon(11780527, 4)
      // return
      if (!(this.HdKhId > 0)) {
        this.ShowAlert(`Chưa có hợp đồng khách hàng !`)
        return
      }
      let data = await this.kiemTraKenhTiepNhan(this.HdKhId)
      console.log(data)
      if (data) {
        if (this.KieuId == 2) {
          let data = await this.getPhieuIdByHdkhId(this.HdKhId, 15000)
          let arrPhieu = []
          if (data.length <= 0) {
            this.ShowAlert(`Không tìm thấy thông tin phiếu giao`)
            return
          } else {
            let phieuId = data[0].phieu_id
            //this.PhieuId = phieuId
            this.TrangThaiHdId = 2
            data.forEach(
              (x) =>
                function(item) {
                  arrPhieu.push(item.phieu_id)
                }
            )
            this.$refs.dialogObjPhieuTon.showPhieuTon(phieuId, this.TrangThaiHdId, arrPhieu)
          }
        }
      } else {
        // Ko co thong bao
        this.ShowAlert(`Không phải kênh tiếp nhận 700`)
      }
    },
    phieuTonModalDlgClose: function() {
      this.$refs.dialogObjPhieuTon.closePhieuTon()
    },
    readMoney: async function(num) {
      try {
        if (num == '') {
          return 'Không đồng'
        }
        if (num == 0 || num == '0') {
          return 'Không đồng'
        }
        let input = { money: num }
        let response = await api.getMoneyToText(this.axios, input)
        if (response.data.error == '200' && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }
        return num
      } catch (ex) {
        return 'tiền chữ:' + num
      }
    },
    TimKiemTBCanThanhToan: async function() {
      try {
        this.loading(true)
        if (!this.DsThueBaoCanTT.chkHddt) {
          await this.getDshdCanThanhtoan()
        } else {
          await this.getDshdCanGiahanThanhtoan()
        }
      } catch (error) {
        console.log('TimKiemTBCanThanhToan', error)
      } finally {
        this.loading(false)
      }
    },
    TimKiemTBChapNhan: async function() {
      try {
        this.loading(true)
        if (this.DsThueBaoCanTT.SelectItem) {
          this.$refs.dialogObjDsCanTT.hide()
          this.txtMaGD = this.DsThueBaoCanTT.SelectItem.ma_gd
          await this.searchTheoMaGD(this.txtMaGD)
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 2500 ~ err', err)
      } finally {
        this.loading(false)
      }
    },
    // Popup danh sach hd can thanh toan
    getDsLoaiHD: async function() {
      var input = {}
      let response = await api.getDsLoaiHD(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.LoaiHD.list = data.map((x) => ({
          id: x.LOAIHD_ID,
          text: x.TEN_LOAIHD == null ? '' : x.TEN_LOAIHD
        }))
      } else {
        console.log(response.data.error_code)
      }
    },
    getDsDVTT: async function() {
      var input = {}
      let response = await api.getDsDVTT(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.DichVu.list = data.map((x) => ({
          id: x.DICHVUVT_ID,
          text: x.TEN_DVVT == null ? '' : x.TEN_DVVT
        }))
        await this.getDsLoaiHinhTB(this.DsThueBaoCanTT.DichVu.list[0].id)
      } else {
        console.log(response.data.error_code)
      }
    },
    getDsTTHD: async function() {
      var input = {}
      let response = await api.getDsTTHD(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.TTHopDong.list = data.map((x) => ({
          id: x.TTHD_ID,
          text: x.TRANGTHAI_HD == null ? '' : x.TRANGTHAI_HD
        }))
        this.DsThueBaoCanTT.TTHopDong.val = this.TrangThaiHD.MOI
      } else {
        console.log(response.data.error_code)
      }
    },
    getDsLoaiHinhTB: async function(dvvtId) {
      var input = {}
      let response = await api.getDsLoaiHinhTB(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.LoaiHinh.list = data
          .filter((x) => x.DICHVUVT_ID == dvvtId)
          .map((x) => ({
            id: x.LOAITB_ID,
            text: x.LOAIHINH_TB == null ? '' : x.LOAIHINH_TB
          }))
      } else {
        console.log(response.data.error_code)
      }
    },
    getDshdCanThanhtoan: async function() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var input = {
        loaihd_id: parseInt(this.DsThueBaoCanTT.LoaiHD.checked ? this.DsThueBaoCanTT.LoaiHD.val : 0),
        dichvuvt_id: parseInt(this.DsThueBaoCanTT.DichVu.checked ? this.DsThueBaoCanTT.DichVu.val : 0),
        loaitb_id: parseInt(this.DsThueBaoCanTT.LoaiHinh.checked ? this.DsThueBaoCanTT.LoaiHinh.val : 0),
        tthd_id: parseInt(this.DsThueBaoCanTT.TTHopDong.checked ? this.DsThueBaoCanTT.TTHopDong.val : 0),
        donvi_id: parseInt(donviId),
        nhanvien_id: parseInt(nhanvienId)
      }
      //input={"loaihd_id":1,"dichvuvt_id":4,"loaitb_id":58,"tthd_id":1,"donvi_id":900,"nhanvien_id":452}
      let response = await api.getDshdCanThanhtoan(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.data = data
        if (data != null && data.length > 0) {
          this.SelectDsThueBaoCanTT(0, data[0])
        }
      } else {
        console.log(response.data.error_code)
        this.DsThueBaoCanTT.data = []
      }
    },
    getDshdCanGiahanThanhtoan: async function() {
      //var input={"loaihd_id":1,"dichvuvt_id":4,"loaitb_id":58,"tthd_id":1,"donvi_id":900,"nhanvien_id":452}
      let donviId = parseInt(this.$root.token.getDonViID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      var input = {
        loaihd_id: parseInt(this.DsThueBaoCanTT.LoaiHD.checked ? this.DsThueBaoCanTT.LoaiHD.val : 0),
        dichvuvt_id: parseInt(this.DsThueBaoCanTT.DichVu.checked ? this.DsThueBaoCanTT.DichVu.val : 0),
        loaitb_id: parseInt(this.DsThueBaoCanTT.LoaiHinh.checked ? this.DsThueBaoCanTT.LoaiHinh.val : 0),
        tthd_id: parseInt(this.DsThueBaoCanTT.TTHopDong.checked ? this.DsThueBaoCanTT.TTHopDong.val : 0),
        donvi_id: donviId,
        nhanvien_id: nhanvienId
      }
      //input={"loaihd_id":1,"dichvuvt_id":4,"donvi_id":900,"nhanvien_id":452}
      // let data= this.getData(await api.getDshdCanGiahanThanhtoan(this.axios, input))
      // this.DsThueBaoCanTT.data = data
      let response = await api.getDshdCanGiahanThanhtoan(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        this.DsThueBaoCanTT.data = data
        if (data != null && data.length > 0) {
          this.SelectDsThueBaoCanTT(0, data[0])
        }
      } else {
        console.log(response.data.error_code)
        this.DsThueBaoCanTT.data = []
      }
    },
    SelectDsThueBaoCanTT: function(i, item) {
      try {
        this.DsThueBaoCanTT.SelectItem = item
        this.DsThueBaoCanTT.txtMaGiaoDich = item.ma_gd
        this.DsThueBaoCanTT.txtSoMay = item.ma_tb
      } catch (err) {
        this.ShowError(err.message)
      }
    },
    SelectDsThueBaoCanTTDbc: function(i, item) {
      this.DsThueBaoCanTT.SelectItem = item
      this.TimKiemTBChapNhan()
    },    
    thongso_vivnptpay_smp: async function() {
      var input = {}
      let response = await api.thongso_vivnptpay_smp(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        return data
      } else {
        return []
      }
    },
    thongtin_thucuoc: async function() {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      var input = { nhanvien_id: nhanvienId }
      let response = await api.thongtin_thucuoc(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        return data
      } else {
        return []
      }
    },
    async btnThongTinVi_Click() {
      try {
        this.loading(true)
        let response = await this.getBalance()
        console.log(response)
        if (response == null) return
        if (response != null && response.data.errorCode == 0 && response.data.data != null) {
          this.PayInfo = response.data.data
          this.$refs.refVNPTPayInfo.show()
        } else {
          this.ShowError(`${response.data.faultCode} ${response.data.faultString}`)
        }
      } catch (err) {
        this.ShowError(`${err.message}`)
      } finally {
        this.loading(false)
      }
    },
    closeVNPTPayInfo() {
      this.$refs.refVNPTPayInfo.hide()
    },
    getThongTinViVNPTPay: async function() {
      // this.ShowAlert(`Pedding `)
      var input = { ma_ts: 'SUDUNG_VI_VNPTPAY_V2' }
      let response = await api.getThongTinViVNPTPay(this.axios, input)
      if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
        let data = response.data.data
        if (data.length > 0) {
          let tt_vnptpayapi = await this.thongso_vivnptpay_smp()
          let tt_thucuoc = await this.thongtin_thucuoc()
          let sdtVi = ''
          if (this.VnPtPayTest == 1) {
            if (tt_vnptpayapi != null && tt_vnptpayapi.length > 0) sdtVi = tt_vnptpayapi[0].phoneNumber
          } else {
            if (tt_thucuoc != null && tt_thucuoc.length > 0) sdtVi = tt_thucuoc[0].SDT_VI
          }
        }
      } else {
        console.log(response.data.error_code)
        return null
      }
    },
    SetMaGd(magd) {
      this.txtMaGD = magd
      this.loading(true)
      this.DsThueBao.data = []
      this.DsKhoanMuc.data = []
      this.DsKhoanMucCT.data = []
      Promise.all([this.searchTheoMaGD()])
        .then(() => {})
        .catch((err) => {
          console.log('🚀 ~ file: Payment.vue ~ line 2492 ~ SetMaGd ~ err', err)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    // Chuyen to
    ShowChuyenPBH: function() {
      // this.HdKhId=128
      if (this.HdKhId > 0) {
        this.$refs.dialogObjDonViNhanPhieu.show()
        try {
          this.loading(true)
          Promise.all([
            this.getThongTinDonVi_CDV()
            //this.getDsPhieuHong_CDV()
          ])
          this.loading(false)
        } catch (ex) {
          this.loading(false)
        }
      } else {
        this.ShowAlert(`Chưa chọn hợp đồng khách hàng !`)
      }
    },
    getThongTinDonVi_CDV: async function() {
      var input = { ds_param: `{HDKH_ID: ${this.HdKhId}}` }
      let response = await api.getThongTinDonVi_CDV(this.axios, input)
      if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
        let data = response.data.data
        let abc = JSON.parse(data)
        let donVis = JSON.parse(abc.RESULT.HT_DONVI_CHUYEN)
        this.ChuyenPBH.data = donVis
        this.ChuyenPBH.DonVi = donVis.map((x) => ({
          id: x.DONVI_ID,
          text: x.TEN_DV == null ? '' : x.TEN_DV
        }))
        console.log(this.ChuyenPBH.DonVi)
        this.DsThueBaoPopUp.data = abc.RESULT.LAY_DS_HD_TB_THEO_HDKH_ID
        console.log(this.DsThueBaoPopUp.data)
        if (this.DsThueBaoPopUp.data.length > 0) {
          let item = this.DsThueBaoPopUp.data[0]
          let dataYC = await this.getDsPhieuHong_CDV(item.HDTB_ID, 15000)
          this.DsPhieuYC.data = dataYC
        }
      } else {
        console.log(response.data.error_code)
        return null
      }
    },
    getDsPhieuHong_CDV: async function(hdtb_id, huonggiao_id) {
      //var input = {"hdtb_id":2005881,"huonggiao_id":362}
      var input = { hdtb_id: hdtb_id, huonggiao_id: 15000 }
      let response = await api.getDsPhieuHong_CDV(this.axios, input)
      if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
        let data = response.data.data
        return data
      } else {
        console.log(response.data.error_code)
        return []
      }
    },
    DsThueBaoPopUp_Changed: async function(i, item) {
      try {
        this.loading(true)
        let data = await this.getDsPhieuHong_CDV(item.HDTB_ID)
        this.DsPhieuYC.data = data
        this.loading(false)
      } catch (ex) {
        console.log(ex)
        this.loading(false)
      }
    },
    DsPhieuYCPopUp_Changed: function(i, item) {
      this.DsPhieuYC.selectItem = item
    },
    updateDonViNhanPhieu: function() {
      if (this.ChuyenPBH.DonViId == vdonvi_nhan_id) {
        this.ShowAlert(`Đơn vị mới chuyển đi phải khác đơn vị hiện tại`)
        return
      }
      if (this.ChuyenPBH.NoiDung.length <= 0) {
        this.ShowAlert(`Hãy nhập nội dung`)
        this.$refs.chuyenPBHNoiDung.focus()
        return
      }
      if (this.DsPhieuYC.data == null || this.DsPhieuYC.data.length <= 0) {
        this.ShowAlert(`Chưa có thông tin phiếu để chuyển`)
        return
      }
      if (this.DsPhieuYC.selectItem == null) {
        this.DsPhieuYC.selectItem = this.DsPhieuYC.data[0]
      }
      let vdonvi_nhan_id = this.DsPhieuYC.selectItem.donvi_nhan_id
      let phieu_id = this.DsPhieuYC.selectItem.phieu_id
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      let userName = this.$root.token.getUserName()
      if (phieu_id == null) {
        this.ShowAlert(`Chưa có thông tin phiếu để chuyển`)
        return
      }
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn chuyển Phòng BH không ?', {
          title: '',
          size: 'sm',
          okTitle: 'Chuyển',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            //this.ShowAlert(`Bạn đã hủy thanh toán `)
            return
          }
          let input = {
            hdtb_id: this.HdTbId,
            phieu_id: phieu_id,
            donvi_nhan_id: this.ChuyenPBH.DonViId,
            donvi_id: donviId,
            nhanvien_id: nhanvienId,
            noidung: this.ChuyenPBH.NoiDung,
            may_cn: 'admin',
            nguoi_cn: userName
          }
          let response = await api.chuyenPhongBanHang(this.axios, input)
          if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
            let data = response.data.data
            this.ShowSuccess(`Thay đổi đơn vị thành công! `)
          } else {
            this.ShowError(`Có lỗi khi thay đổi đơn vi! `)
            console.log(response.data.error_code)
          }
          // Xử lý code ở đây
        })
    },
    ThanhToanThanhCong: async function(self, data) {
      if (data.result.toString() == '1') {
        self.ShowSuccess(`Thanh toán thành công `)
        self.txtMaGDTT = this.vma_gd
        self.customerInfo.txtTenKhachHang = this.txtTenKH2
        self.customerInfo.vma_gd = this.txtMaGDTT
        self.customerInfo.txtDiaChi = this.txtDiaChiCT2
        self.customerInfo.txtSoDienThoai = this.txtDienThoai
        self.customerInfo.txtMaSoThue = this.txtMaSoThue
        self.Fn.DongBoVNP = true
        self.tsbtnDongBoVNP_Enabled = true
        self.Fn.InHD = true
        self.Fn.PhieuBienNhan = true
        self.tsbtnInHoaDon_Enabled = true
        self.tsbtnInPhieuThu_Enabled = true
        self.tsbtnPhieuBienNhan_Enabled = true
        // cap nhat thong tin ccbs
        try {
          if (self.OPEN_SYNC_VINA && self.flag == true) {
            if (self.phieutt_id_neo > 0) await self.capnhat_phieu_id_neo(self.phieutt_id_neo, self.vphieu_id)
          }
        } catch (errCCBS) {
          console.log('ThanhToanThanhCong:function ~ errCCBS:', errCCBS)
        }
        //
        if (!self.chkHoaDonDienTu) {
          self.RegWrite('loaihoadon_id', this.loaiHoaDonId)
          self.RegWrite('seri_hd_id', this.loaiSeriId)
          if (this.txtHoaDon != null && this.txtHoaDon.length > 0) this.RegWrite('soseri_hd', parseInt(this.txtHoaDon) + 1)
        } else {
          if (this.chkLoaiHD == true) {
            if (self.KieuId == 1 && self.chkHoaDonDienTu == true) {
              if (self.params.hoi_sua_hddt) {
                self.$bvModal
                  .msgBoxConfirm('Bạn có muốn sửa nội dung hóa đơn hay không?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Sửa hóa đơn',
                    cancelTitle: 'Hủy'
                  })
                  .then(async (v) => {
                    if (!v) {
                      await self.XuatHDDT(self)
                      return
                    } else {
                      try {
                        self.loading(true)
                        self.$refs.refeInvoice2._ma_gd = self.txtMaGDTT
                        self.$refs.refeInvoice2.txtMaGD = self.txtMaGDTT
                        try {
                          await self.$refs.refeInvoice2.Xuat_HDDT_LOAD()
                        } catch (err1abc) {
                          console.log('🚀 ~ file: Payment.vue:3796 ~ .then ~ err1abc:', err1abc)
                        }
                        try {
                          await self.$refs.refeInvoice2.HienThiThongTin()
                        } catch (err2abc) {
                          console.log('🚀 ~ file: Payment.vue:3801 ~ .then ~ err2abc:', err2abc)
                        }
                      } catch (err) {
                        console.error(err)
                      } finally {
                        self.loading(false)
                        self.$refs.refeInvoiceDialog2.show()
                      }
                    }
                    //
                  })
              } else {
                await self.XuatHDDT(self)
              }
            }
            if (self.KieuId == 2 && self.params.da_xuathd_dt == false && self.chkHoaDonDienTu == true) {
              if (self.chkLoaiHD == true) {
                if (self.params.hoi_sua_hddt) {
                  self.$bvModal
                    .msgBoxConfirm('Bạn có muốn sửa nội dung hóa đơn hay không?', {
                      title: '',
                      size: 'sm',
                      okTitle: 'Sửa hóa đơn',
                      cancelTitle: 'Hủy'
                    })
                    .then(async (v) => {
                      if (!v) {
                        await self.XuatHDDT(self)
                        return
                      } else {
                        try {
                          self.loading(true)
                          self.$refs.refeInvoice2._ma_gd = self.txtMaGDTT
                          self.$refs.refeInvoice2.txtMaGD = self.txtMaGDTT
                          try {
                            await self.$refs.refeInvoice2.Xuat_HDDT_LOAD()
                          } catch (err1abc) {
                            console.log('🚀 ~ file: Payment.vue:3796 ~ .then ~ err1abc:', err1abc)
                          }
                          try {
                            await self.$refs.refeInvoice2.HienThiThongTin()
                          } catch (err2abc) {
                            console.log('🚀 ~ file: Payment.vue:3801 ~ .then ~ err2abc:', err2abc)
                          }
                        } catch (err) {
                          console.error(err)
                        } finally {
                          self.loading(false)
                          self.$refs.refeInvoiceDialog2.show()
                        }
                      }
                      //
                    })
                } else {
                  await self.XuatHDDT(self)
                }
              }
            }
          }
        }
        if (self.KieuId == 1) {
          if (self.kenhThuId == this.HinhThucTra.VI_VNPTPAY) {
            self.Fn.GiaoPhieu = false
          } else {
            self.Fn.GiaoPhieu = true
          }
          //
          if (self.params.kt_phathanh_hddt_thanhtoan == true) {
            self.Fn.XuatHDDT = true
            self.tsbtnXuaHD_Enabled = true
          } else {
            self.Fn.XuatHDDT = false
            self.tsbtnXuaHD_Enabled = false
          }
        } else {
          self.Fn.GiaoPhieu = false
        }
        self.clearData()
        self.clearText()
      }
    },
    SwitchPayment: async function(data) {
      console.log('SwitchPayment:', data)
      if (data == null) return
      var self = this
      let yc_capnhat = 'Hợp đồng đã bị quá giờ (quá 30 phút tính từ thời điểm yêu cầu), bạn có muốn cập nhật lại ngày yêu cầu trước khi thanh toán?'
      var detail = JSON.parse(data.detail)
      if (data.result.toString() == '1') {
        await this.ThanhToanThanhCong(self, data)
        this.$emit('paymentSuccess', true)
        return 1
      } else if (data.result.toString() == '0' && detail.MESSAGE == yc_capnhat && self.params_thanhtoan.capnhat_yc == 0) {
        this.$bvModal
          .msgBoxConfirm('Hợp đồng đã bị quá giờ (quá 30 phút tính từ thời điểm yêu cầu), bạn có muốn cập nhật lại ngày yêu cầu trước khi thanh toán?', {
            title: '',
            size: 'sm',
            okTitle: 'Thanh toán',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              self.ShowAlert(`Bạn đã hủy thanh toán `)
              // bFished = true
              return
            }
            //
            self.params_thanhtoan.capnhat_yc = 1
            self.params_thanhtoan.datcoc_goi_dadv = this.params.datcoc_goi_dadv
            self.loading(true)
            data = await self.fn_thanhtoan()
            self.loading(false)
            await self.SwitchPayment(data)
            //
          })
      } else if (data.result.toString() == '0' && detail.MESSAGE.indexOf('Bạn muốn tiếp tục thực hiện thanh toán') >= 0) {
        this.$bvModal
          //.msgBoxConfirm('Bạn có muốn thanh toán cho các thuê bao này không?', {
          .msgBoxConfirm(detail.MESSAGE, {
            title: '',
            size: 'sm',
            okTitle: 'Thanh toán',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              self.ShowAlert(`Bạn đã hủy thanh toán `)
              return
            }
            //
            self.params_thanhtoan.capnhat_yc = 1
            self.params_thanhtoan.thanhtoan = 0
            self.loading(true)
            data = await self.fn_thanhtoan()
            self.loading(false)
            await this.SwitchPayment(data)
          })
      } else if (data.result.toString() == '0' && detail.MESSAGE.indexOf('Bạn có muốn thanh toán cho các thuê bao này không') >= 0) {
        let phanvungId = this.$root.token.getPhanVungID()
        this.$bvModal
          .msgBoxConfirm('Bạn có muốn thanh toán cho các thuê bao này không?', {
            title: '',
            size: 'sm',
            okTitle: 'Thanh toán',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              self.ShowAlert(`Bạn đã hủy thanh toán `)
              return
            }
            self.params_thanhtoan.capnhat_yc = 1
            self.params_thanhtoan.thanhtoan = 1
            self.params_thanhtoan.datcoc_goi_dadv = this.params.datcoc_goi_dadv
            try {
              await this.KiemTra_AUTO_PORT_KICHOAT()
            } catch (errkh) {
              console.log('errkh:', errkh)
            }
            try {
              let resultTTVnp = await this.GachNoVinaPhone(this.txtMaGD)
              if (resultTTVnp == false) {
                return
              }
            } catch (err) {
              console.log('GachNoVinaPhone-err:', err)
            }
          
            if (this.hinhThucTTId != this.HinhThucTra.VI_VNPTPAY || phanvungId == 26) {
              this.loading(true)
              data = await this.fn_thanhtoan()
              this.loading(false)
              await this.SwitchPayment(data)
            } else {
              this.loading(true)
              let islogin = await this.loginVnptPay()
              if (islogin) {
                console.log('Lấy thông tin ví thành công ', this.hinhThucTTId, this.HinhThucTra.VI_VNPTPAY)
                let transferContent = ''
                if (this.hinhThucTTId == this.HinhThucTra.VI_VNPTPAY) {
                  console.log('this.hinhThucTTId == this.HinhThucTra.VI_VNPTPAY', this.hinhThucTTId, this.HinhThucTra.VI_VNPTPAY)
                  if (parseInt(this.trangThaiThanhToanId) == 1 && (this.kenhThuId != this.KENHTHU.KHONG_THU_TIEN && this.hinhThucTTId != this.HinhThucTra.KHONG_THU_TIEN)) {
                    // call ham ghep api
                    // let call_newapi = false
                    if (this.call_newapi) {
                      console.log(`Đã chuyển sang call api tích hợp:fn_thanhtoan_pay`, data)
                      data = await this.fn_thanhtoan_pay()
                      await this.SwitchPayment(data)
                    } else {
                      // Thuc hien doi api tu cho nay
                      console.log('trangThaiThanhToanId:1,', this.kenhThuId)
                      if (this.kenhThuId != this.KENHTHU.THU_THEO_GIAI_DOAN) {
                        let dtDichVu = await this.lay_thongtin_chitiet_khoanmuc(this.txtMaGD)
                        if (dtDichVu != null && dtDichVu.length > 0) {
                          let dv = dtDichVu.map((x) => ({
                            AMOUNT: x.tien_tt != null ? x.tien_tt : '',
                            DICHVUVTID: x.dichvuvt_id != null ? x.dichvuvt_id : '',
                            HOPDONGTHUEBAOID: x.hdtb_id != null ? x.hdtb_id : '',
                            KHOANMUCID: x.khoanmuctt_id != null ? x.khoanmuctt_id : '',
                            LOAITBID: x.loaitb_id != null ? x.loaitb_id : '',
                            TENDICHVU: x.tendichvu != null ? x.tendichvu : '',
                            TENKHOANMUC: x.khoanmuc_tt != null ? x.khoanmuc_tt : '',
                            THANHTOANID: this.PhieuTtId != null ? x.PhieuTtId : '',
                            LOAIHINHTB: x.loaihinh_tb != null ? x.loaihinh_tb : '',
                            DESCRIPTION: 'PTTB'
                          }))
                          transferContent = '' // JSON.stringify(dv)
                        }
                      }
                      console.log('transferContent', transferContent)
                      // Khoi tao giao dich
                      let response = await this.initTransaction(transferContent)
                      // Tam tru vi thanh cong
                      if (response.data.errorCode == '0') {
                        //let transactionId = response.data.data.transactionId
                        let transactionId = response.data.data
                        this.kq_debit_id = transactionId
                        this.kq_debit_code = '00'
                        data = await this.fn_thanhtoan()
                        if (data.result.toString() == '1') {
                          // Thanh toan db thanh cong
                          let response = await this.commitTransaction(transactionId)
                          if (response.data.errorCode != '0') {
                            this.ShowError(`Xác nhận trừ ví thất bại. ${response.data.faultString}. Cần vào danh sách pendding để xác nhận lại`)
                            this.loading(false)
                            return
                          }
                          await this.SwitchPayment(data)
                        } else {
                          // let response = await this.cancelTransaction(transactionId)
                          this.kq_debit_code = '01'
                          let response = await this.initTransactionCancel(transactionId)
                          if (response.data.errorCode != '0') {
                            this.loading(false)
                            this.ShowError(`Xác nhận trừ ví thất bại. ${response.data.faultString}. Cần vào danh sách pendding để xác nhận lại`)
                          }
                          this.ShowError(`Lỗi thanh toán trên ĐHSX: ${data.detail}`)
                          this.loading(false)
                          return
                        }
                      }
                      // Loi khoi tao giao dich vi pay
                      else {
                        this.ShowError(`Thanh toán qua ví thất bại: ${response.data.faultString} `)
                        this.loading(false)
                        return
                      }
                    }
                  } else {
                    console.log('Không chọn trạng thái thanh toán đã thu tiền, ko thanh toán = pay được !')
                    /* ** */
                    this.loading(true)
                    data = await this.fn_thanhtoan()
                    this.loading(false)
                    await this.SwitchPayment(data)
                    /****/
                  }
                }
                this.loading(false)
              } else {
                this.loading(false)
                this.ShowError(`Chưa có thông tin ví VNPT-PAY. Bạn phải lấy thông tin ví trước khi thanh toán qua ví .`)
                return
              }
              this.loading(false)
            }
          })
      } else if (data.result.toString() == '0' && detail.MESSAGE.indexOf('Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán') >= 0) {
        this.$bvModal
          .msgBoxConfirm('Hợp đồng chưa có thông tin liên hệ, bạn phải bổ sung thông tin liên hệ trước khi thanh toán !', {
            title: '',
            size: 'sm',
            okTitle: 'Cập nhật liên hệ',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              self.ShowAlert(`Bạn đã hủy thanh toán `)
              self.loading(false)
              return
            }
            this.lienHeOpen()
          })
        this.loading(false)
        return
      } else {
        if (data.detail != null) {
          var err = JSON.parse(data.detail)
          // self.ShowError(`Mã lỗi:${err.ERROR_CODE}:${err.MESSAGE}`)
          this.loading(false)
          self.ShowError(`${err.MESSAGE}`)
        } else {
          this.loading(false)
          self.ShowError(data)
        }
      }
    },
    async lay_thongtin_chitiet_khoanmuc(ma_gd) {
      try {
        let input = { magd: ma_gd }
        return this.GetData(await api.lay_thongtin_chitiet_khoanmuc(this.axios, input))
      } catch (err) {
        return []
      }
    },
    async insert_logsvnptpay(vma_tt, vnguoi_cn, vinput, vcode, vname, vdescription, vtransactionid, vkieu, vtransfertype, vphieu_id, vkyhoadon, vhdkh_id, matinh) {
      let input = { ma_gd: ma_gd }
      return this.GetData(await api.insert_logsvnptpay(this.axios, input))
    },
    async initTransaction(transferContent) {
      let username = this.$root.token.getUserName()
      let input = {
        collaboratorId: username,
        customerId: this.txtMaGD,
        sendWalletAccountId: this.walletAccountId,
        sendWalletId: this.walletId,
        transferAmount: this.txtTongTienTra,
        transferCode: '',
        transferContent: transferContent,
        transferType: this.transferType
      }
      console.log('initTransaction', input, this.token)
      let response = await api.initTransaction(this.axios, input, this.token)
      return response
    },
    async commitTransaction(transactionId) {
      let input = {
        confirmType: '1',
        phieuId: '', // this.PhieuTtId
        transferContent: 'Thanh toán hợp đồng thành công',
        transferId: transactionId,
        transferResult: '1',
        transferType: this.transferType
      }
      console.log('commitTransaction', input, this.token)
      let response = await api.commitTransaction(this.axios, input, this.token)
      return response
    },
    async initTransactionCancel(transactionId) {
      let input = {
        confirmType: '1',
        phieuId: '',
        transferContent: 'Thanh toán hợp đồng thất bại',
        transferId: transactionId,
        transferResult: '3',
        transferType: this.transferType
      }
      console.log('commitTransaction', input, this.token)
      let response = await api.commitTransaction(this.axios, input, this.token)
      return response
    },
    async cancelTransaction(transactionId) {
      let username = this.$root.token.getUserName()
      let input = {
        amount: 20000, //this.txtTongTienTra,
        cancelType: '1',
        description: 'Thanh toán hợp đồng thất bại',
        refTransId: transactionId,
        username: username
      }
      console.log('cancelTransaction', input, this.token)
      let response = await api.cancelTransaction(this.axios, input, this.token)
      return response
    },
    // ko dùng
    async ThanhToanVNPTPay() {
      if (this.hinhThucTTId == this.HinhThucTra.VI_VNPTPAY) {
        if (parseInt(this.trangThaiThanhToanId) == 1 && (this.kenhThuId != this.KENHTHU.KHONG_THU_TIEN && this.hinhThucTTId != this.HinhThucTra.KHONG_THU_TIEN)) {
          let transferContent = ''
          if (this.kenhThuId != this.KENHTHU.THU_THEO_GIAI_DOAN) {
            let dtDichVu = await this.lay_thongtin_chitiet_khoanmuc(this.txtMaGD)
            let dv = dtDichVu.map((x) => ({
              AMOUNT: x.tien_tt != null ? x.tien_tt : '',
              DICHVUVTID: x.dichvuvt_id != null ? x.dichvuvt_id : '',
              HOPDONGTHUEBAOID: x.hdtb_id != null ? x.hdtb_id : '',
              KHOANMUCID: x.khoanmuctt_id != null ? x.khoanmuctt_id : '',
              LOAITBID: x.loaitb_id != null ? x.loaitb_id : '',
              TENDICHVU: x.tendichvu != null ? x.tendichvu : '',
              TENKHOANMUC: x.khoanmuc_tt != null ? x.khoanmuc_tt : '',
              THANHTOANID: this.PhieuTtId != null ? x.PhieuTtId : '',
              LOAIHINHTB: x.loaihinh_tb != null ? x.loaihinh_tb : '',
              DESCRIPTION: 'PTTBWEB'
            }))
            transferContent = JSON.stringify(dv)
          }
          let response = await this.initTransaction(transferContent)
          if (response.data.errorCode == 0) {
          }
        }
      }
    },
    disabledRangePassDate(date) {
      const currentDate = new Date();
      const maxDate = new Date(); 
      maxDate.setDate(currentDate.getDate() + 75);
      const inputDate = moment(this.txtNgayhenTT, 'DD/MM/YYYY').toDate();
      console.log(currentDate)
      console.log(this.txtNgayhenTT)
      console.log(maxDate)
      console.log(inputDate)
      // So sánh ngày: Chỉ cho phép ngày nằm trong khoảng [sysdate, sysdate + 75]
      return date < currentDate.setHours(0, 0, 0, 0) || date > maxDate.setHours(0, 0, 0, 0);
    },
    // End Chuyen to
    tsbGachnothat_Click: async function() {
      var self = this
      console.log('OPEN_SYNC_VINA:', self.OPEN_SYNC_VINA)
      console.log('KieuId:', self.KieuId)
      this.params_thanhtoan.capnhat_yc = 0
      this.params_thanhtoan.thanhtoan = 0
      this.params_thanhtoan.datcoc_goi_dadv = 0
      if (this.txtMaGD == '') return
      self.checkLoginPayment = true
      await self.thongtin_nguoidung()
      await self.thongtin()
      if (self.checkLoginPayment == false) {
        self.ShowAlert(`Phiên đăng nhập đã hết hạn vui lòng đăng nhập lại !  `)
        return
      }
      if ((await this.KiemTraDuLieu()).length == 0) {
        this.$bvModal
          .msgBoxConfirm('Bạn xác nhận thanh toán hợp đồng này ?', {
            title: '',
            size: 'sm',
            okTitle: 'Thanh toán',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              self.ShowAlert(`Bạn đã hủy thanh toán `)
              return
            }
            //
            if (!this.chkHoaDonDienTu) {
              /*
              this.RegWrite('loaihoadon_id', this.loaiHoaDonId)
              this.RegWrite('seri_hd_id', this.loaiSeriId)
              if (this.txtHoaDon != null && this.txtHoaDon.length > 0) this.RegWrite('soseri_hd', parseInt(this.txtHoaDon) + 1)
              */
            }
            //
            console.log('params.datcoc_goi_tatca_tb:', this.params.datcoc_goi_tatca_tb, 'HdKhId:', this.HdKhId)
            if (this.params.datcoc_goi_tatca_tb == 1) {
              let kt = await this.kiemtra_datcoc_goi_dadv(this.HdKhId)
              console.log('kiemtra_datcoc_goi_dadv:result:', kt)
              if (kt != null && kt != 'ok' && kt != 'OK') {
                this.$bvModal
                  .msgBoxConfirm(kt + ' .Bạn muốn tiếp tục thực hiện thanh toán ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Thanh toán',
                    cancelTitle: 'Hủy'
                  })
                  .then(async (v) => {
                    if (!v) {
                      return
                    }
                    //
                    try {
                      self.loading(true)
                      await self.getKeyPhieuTTId()
                      var data = await self.fn_thanhtoan()
                      self.loading(false)
                      await self.SwitchPayment(data)
                      return
                    } catch (errthanhtoan) {
                      console.log('🚀 ~ file: Payment.vue  errthanhtoan', errthanhtoan)
                      self.loading(false)
                      self.ShowError(`Có lỗi: ${errthanhtoan}`)
                    }
                  })
              } else {
                //
                try {
                  self.loading(true)
                  if (this.KieuId == 1) await self.getKeyPhieuTTId()
                  var data = await self.fn_thanhtoan()
                  self.loading(false)
                  await self.SwitchPayment(data)
                  return
                } catch (errthanhtoan) {
                  console.log('🚀 ~ file: Payment.vue  errthanhtoan', errthanhtoan)
                  self.loading(false)
                  self.ShowError(`Có lỗi: ${errthanhtoan}`)
                }
              }
            } else {
              //
              try {
                self.loading(true)
                await self.getKeyPhieuTTId()
                var data = await self.fn_thanhtoan()
                self.loading(false)
                await self.SwitchPayment(data)
                return
              } catch (errthanhtoan) {
                console.log('🚀 ~ file: Payment.vue  errthanhtoan', errthanhtoan)
                this.loading(false)
                this.ShowError(`Có lỗi: ${errthanhtoan}`)
              }
            }
          })
      } else {
        console.log(`tsbGachnothat_Click-KiemTraDuLieu`)
      }
    },
    UpperObjectKeys(obj) {
      let a = obj
      for (const key in a) {
        if (a.hasOwnProperty(key)) {
          a[key.toUpperCase()] = a[key]
          delete a[key]
        }
      }
      obj = a
    },
    taoDuLieuPhieuTT: async function() {
      let donviId = parseInt(this.$root.token.getDonViID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      let tinhId = this.$root.token.getPhanVungID()
      let username = this.$root.token.getUserName()
      let may_cn = this.tt.mayCn
      let ip = this.tt.ipCn
      var row = {}
      var dsPhieuTTHD = []
      if (this.KieuId == 1) {
        row.PHIEUTT_ID = 0 //get key
      } else {
        if (this.KieuId == 2 || this.KieuId == 3) {
          if (this.DsPhieuTT != null && this.DsPhieuTT.length > 0) {
            try {
              Object.assign(row, this.DsPhieuTT[0])
              this.UpperObjectKeys(row)
            } catch (errassign) {
              console.log('🚀 ~ errassign', errassign)
            }
          }
        }
      }
      row.HDKH_ID = this.HdKhId
      row.PHIEUTT_ID = this.PhieuTtId
      row.MA_GD = this.txtMaGD //txtMaGD.Text; --minhnt sửa
      row.TEN_KH = this.txtTenKH
      row.DIACHI_KH = this.txtDiaChiKH
      if (this.KieuId == 1 || this.KieuId == 3) {
        if (parseInt(this.trangThaiThanhToanId) == 1 && (this.kenhThuId != this.KENHTHU.KHONG_THU_TIEN && this.hinhThucTTId != this.HinhThucTra.KHONG_THU_TIEN)) {
          row.THUNGAN_TT_ID = this.thuNganId
          row.NGAY_TT = this.txtNgayTT
          row.DONVI_TT_ID = donviId
        } else {
          // row.NGAY_TT = moment(new Date()).format('DD/MM/YYYY')
        }
        row.DONVI_ID = donviId
        row.NHANVIEN_ID = nhanvienId
        row.LAN_IN = 0
        row.TIEN = 0
        row.VAT = 0
        row.TYGIA = this.txtTyGia
        row.LOAITIEN_ID = this.loaiTienId
        row.HT_TRA_ID = this.hinhThucTTId
        row.MST = this.txtMaSoThue
        row.STK = this.txtSoTaiKhoan
        row.GHICHU = this.txtGhiChu
        row.TRANGTHAI = parseInt(this.trangThaiThanhToanId)
        row.KENHTHU_ID = this.kenhThuId // NHAPT 05/06/2015
        row.NGUOI_BL = this.txtNguoiBL
        if (this.chkLoaiHD) {
          row.LOAIHOADON_ID = this.loaiHoaDonId
          row.SERI_ID = this.loaiSeriId
          if (this.DmSeri.filter((x) => x.id == this.loaiSeriId) != null && this.DmSeri.filter((x) => x.id == this.loaiSeriId).length > 0) {
            row.SERI = this.DmSeri.filter((x) => x.id == this.loaiSeriId)[0].text
          } else {
            row.SERI = ''
          }
          row.NGAY_HD = this.txtNgayHD
          row.THUNGAN_HD_ID = this.thuNganId
          row.DONVI_HD_ID = donviId
          if (this.txtPhieuThuHD != '') row.SOPHIEU = parseInt(this.txtPhieuThuHD)
          row.SOSERI_BNTT = this.txtSoBNTT
          if (this.KieuId == 1) {
            if (this.chkHDDT == false && this.txtHoaDon != '') row.SOSERI = parseInt(this.txtHoaDon)
          } else {
            if (this.txtHoaDon != '') row.SOSERI = parseInt(this.txtHoaDon)
          }
        }
      } else if (this.KieuId == 2) {
        if (this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN || this.kenhThuId == this.KENHTHU.KHONG_THU_TIEN || this.kenhThuId == this.KENHTHU.THUCUNG_CPS) row.TRANGTHAI = 2
        else row.TRANGTHAI = 1
        row.LAN_IN = 0
        if (await this.kiemTraKenhTiepNhan(this.HdKhId)) {
          //Neu la 700
          row.NGAY_TT = this.txtNgayTT //dtpNgayTT.Value;
        } else if (this.xuat_hd == false) {
          row.LOAIHOADON_ID = this.loaiHoaDonId
          row.SERI_ID = this.loaiSeriId
          if (this.DmSeri != null && this.DmSeri.filter((x) => x.id == row.SERI_ID).length > 0) row.SERI = this.DmSeri.filter((x) => x.id == row.SERI_ID)[0].text
          row.NGAY_HD = this.txtNgayHD
          row.THUNGAN_HD_ID = this.thuNganId
          row.DONVI_HD_ID = donviId
          if (this.txtPhieuThuHD != '') row.SOPHIEU = parseInt(this.txtPhieuThuHD)
          if (this.txtHoaDon != '') row.SOSERI = parseInt(this.txtHoaDon)
        }
        row.GHICHU = this.txtGhiChu
        row.MST = this.txtMaSoThue
        row.STK = this.txtSoTaiKhoan
        // if (parseInt(this.trangThaiThanhToanId) == 0 || (parseInt(this.trangThaiThanhToanId) == 1 && this.params.kt_thutien_sautc == true)) {
        //   row.THONGTIN_PT = this.txtThongTinPT
        //   row.THUNGAN_TT_ID = parseInt(this.thuNganId)
        //   row.DONVI_TT_ID = donviId
        // }
        // if (process.env.NODE_ENV == 'testing') {
          row.THONGTIN_PT = this.txtThongTinPT
          row.THUNGAN_TT_ID = parseInt(this.thuNganId)
          row.DONVI_TT_ID = donviId
        // }
        if (parseInt(this.trangThaiThanhToanId) == 0) {
          row.NGAY_TT = this.txtNgayTT
        }
      }
      if (this.chkNganHang) {
        row.NGANHANG_ID = parseInt(this.nganHangId)
        row.NGAY_NH = this.txtNgayNH
        row.SO_CT = this.txtChungTu
      } else {
        row.SO_CT = ''
        row.NGAY_NH = ''
      }
      row.HT_TRA_ID = parseInt(this.hinhThucTTId)
      row.KENHTHU_ID = parseInt(this.kenhThuId) // NHAPT 05/06/2015
      row.NGUOI_CN = username
      row.NGAY_CN = moment(Date.now()).format('DD/MM/YYYY')
      row.MAY_CN = may_cn
      if (parseInt(this.hinhThucTTId) != this.HinhThucTra.VI_VNPTPAY) {
        row.UNGDUNG_ID = 10
      }
      dsPhieuTTHD.push(row)
      return dsPhieuTTHD
    },
    LayDanhSachTBTT: function() {
      let arr = []
      this.DsThueBao.checked.forEach(function(item) {
        var item = { hdtb_id: item.hdtb_id }
        //arr.push(item.hdtb_id)
        arr.push(item)
      })
      return arr
    },
    LayDanhSachTBTT_DiDong: function() {
      let self = this
      let arr = []
      let arr_ds_matb = []
      let arhdtb_dd = ''
      let tt_vina = false
      console.log('LayDanhSachTBTT_DiDong:', this.DsThueBao.data)
      let i = 0
      for (const item of self.DsThueBao.data) {
        console.log('this.DsThueBao.data.forEach ~ item:', 'item.status:', item.status, 'item.kieuld_id:', item.kieuld_id, 'item.loaitb_id:', item.loaitb_id, 'item:', item)
        debugger
        if (
          Number(item.dichvuvt_id) == self.DichVuVienThong.DI_DONG &&   //2
          Number(item.loaitb_id) == self.LoaiHinhTB.DIDONGTRASAU &&     //20
          item.kieuld_id != self.KieuLapDat.DM_DIDONG_CHUYEN_TINH && // #217 Không lấy thuê bao dd hòa mạng chuyển tỉnh
          item.status == 1
        ) {
          console.log('arr.push(item.hdtb_id)', item.hdtb_id)
          console.log('self.dtHDTB[i].hdtb_id', self.dtHDTB[i].hdtb_id)
          arr.push(self.dtHDTB[i].hdtb_id)
          // arr.push(item.hdtb_id)
          // arhdtb_dd = arhdtb_dd + item.hdtb_id + ","
          tt_vina = true
        } else if (item.dichvuvt_id == self.DichVuVienThong.DI_DONG && 
          item.loaitb_id == self.LoaiHinhTB.DIDONGTRASAU && 
          item.kieuld_id != self.KieuLapDat.DM_DIDONG_CHUYEN_TINH && 
          item.status == 0) {
          console.log('item.ma_tb', item.ma_tb)
          console.log('self.dtHDTB[i].ma_tb', self.dtHDTB[i].ma_tb)
          // arr_ds_matb.push(item.ma_tb)
          arr_ds_matb.push(self.dtHDTB[i].ma_tb)
          //ds_matb = ds_matb + item.ma_tb + ","
        } else {
          // arr_ds_matb.push(self.dtHDTB[i].ma_tb)
          console.log('Ko vao danh sach', item.ma_tb)
        }
        i = i + 1
      }
      // this.DsThueBao.data.forEach(function(item) {
      // })
      if (arr.length > 0) {
        arhdtb_dd = arr.join(',')
      } else {
        arhdtb_dd = arr_ds_matb.join(',')
      }
      return { arrString: arhdtb_dd, arr: arr, arr_ds_matb: arr_ds_matb, tt_vina: tt_vina }
    },
    getKeyPhieuTTId: async function() {
      if ((this.KieuId == 1 || this.KieuId == 2 || this.KieuId == 3) && this.PhieuTtId != null && this.PhieuTtId != '' && this.PhieuTtId > 0) {
        this.PhieuTtIdIn = this.PhieuTtId
        return this.PhieuTtId
      }
      var input = {
        keyname: 'PHIEUTT_HD',
        numblocksize: 1,
        numretry: 10
      }
      let response = await api.getKey(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        console.log('getKeyPhieuTTId ~ response.data.data', response.data.data)
        let data = response.data.data
        this.PhieuTtId = data
        this.PhieuTtIdIn = this.PhieuTtId
        return data
      } else {
        return 0
      }
    },
    toDate: function(val) {
      try {
        if (val != null && val.length >= 10) {
          let abc = val.substr(0, 4)
          if (val.indexOf('-') > 0) {
            if (abc.indexOf('-') > 0) {
              return moment(val.substr(0, 10), 'DD-MM-YYYY').format('DD/MM/YYYY')
            } else {
              return moment(val.substr(0, 10), 'YYYY-MM-DD').format('DD/MM/YYYY')
            }
          } else if (val.indexOf('/') > 0) {
            if (abc.indexOf('/') > 0) {
              return moment(val.substr(0, 10), 'DD/MM/YYYY').format('DD/MM/YYYY')
            } else {
              return moment(val.substr(0, 10), 'YYYY/MM/DD').format('DD/MM/YYYY')
            }
          } else {
            return val.substr(0, 10)
          }
        }
        return val
      } catch (ex) {
        console.log('toDate:', ex)
      }
      return ''
    },
    handleDatePickerChange(newValue){ 
        console.log(newValue);
        console.log(this.txtNgayTT)
    },

    fn_thanhtoan: async function() {
      if(this.params.hen_thanhtoan == 1){
          if( 
               (this.LoaiHDId == 17 && this.kieuld_id == 136 &&  !this.txtNgayhenTTStatus) 
            || (this.LoaiHDId == 32 && this.kieuld_id == 551 &&  !this.txtNgayhenTTStatus) 
            ||(this.LoaiHDId == 31 && this.kieuld_id == 550 &&  !this.txtNgayhenTTStatus) 
          )
          {
            this.$toast.warning("Vui lòng chọn ngày hẹn thanh toán!");
            return;
          }
      } 
      if(this.hinhThucTTId == 2 && this.kenhThuId == 29 && (this.txtNguoiBL == null || this.txtNguoiBL == "")){
          this.$toast.warning("Vui lòng chọn người bảo lãnh!");
          this.btnNguoiBLStatus = true;
          this.txtNguoiBLStatus = true 
          return;
      }
      let self = this
      let donviId = parseInt(this.$root.token.getDonViID()) 
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      let tinhId = parseInt(this.$root.token.getPhanVungID())
      let datcoc_goi_dadv = 1 
      let loaihd_id = this.LoaiHDId
      this.loaihd_id = this.LoaiHDId
      let loaitb_id = this.LoaiThueBaoId
      let phieutt_id = this.PhieuTtId == '' ? 0 : this.PhieuTtId
      let dsPhieuTTHD = await this.taoDuLieuPhieuTT()
      this.dsPhieuTTHD = dsPhieuTTHD
      let layDanhSachTBTT = this.LayDanhSachTBTT()
      let a = this.LayDanhSachTBTT_DiDong()
      let layDanhSachTBTT_DiDong = a.arrString
      let ds_matb = '' //  a.arr_ds_matb != null ? a.arr_ds_matb.join(',') : '' // a.arr.length;
      if (loaihd_id == self.LoaiHopDong.DAT_MOI || loaihd_id == self.LoaiHopDong.CHUYEN_QUYEN || loaihd_id == self.LoaiHopDong.TACHNHAP_TB) {
        ds_matb = a.arr_ds_matb != null ? a.arr_ds_matb.join(',') : '' // a.arr.length;
      }
      let open_sync_vina = this.OPEN_SYNC_VINA ? 1 : 0 //
      let cboDSLAM = 0 //
      let cboBrasADSL = 0 //
      let donv_tram_id = 0 //
      let hddt_fkey = ''
      let hddt_status = 0
      let key_parameter = ''
      let note = 0
      let da_xuat_hddt = this.params.da_xuathd_dt ? 1 : 0 //
      let soseri = '' //
      let nguoi_cn = this.$root.token.getUserName()
      let ngay_ht = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
      let ngay_cn = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
      let may_cn = this.tt.mayCn
      let ip = this.tt.ipCn
      if (phieutt_id == '' || phieutt_id == null || phieutt_id == 0) {
        phieutt_id = await this.getKeyPhieuTTId()
        this.PhieuTtId = phieutt_id
        this.vphieu_id = phieutt_id
      } else {
        this.vphieu_id = phieutt_id
      }
      try {
        dsPhieuTTHD.forEach(function(item) {
          item.PHIEUTT_ID = phieutt_id
        })
      } catch (errdsPhieuTTHD) {
        console.log('dsPhieuTTHD.forEach(function(item) ~ err:', errdsPhieuTTHD)
      }
      if (this.DsPhieuTT.length > 0) {
        hddt_fkey = this.DsPhieuTT[0].hddt_fkey
      }
      let seriText = ''
      if (this.chkLoaiHD && this.DmSeri != null && this.DmSeri.length > 0 && this.DmSeri.filter((x) => x.id == this.loaiSeriId).length > 0) {
        seriText = this.DmSeri.filter((x) => x.id == this.loaiSeriId)[0].text
        this.payment_serial = seriText
      }
      try {
        if (this.chkLoaiHD && this.DmLoaiHoaDon != null && this.DmLoaiHoaDon.length > 0 && this.DmLoaiHoaDon.filter((x) => x.id == this.loaiHoaDonId).length > 0) {
          let patternText = this.DmLoaiHoaDon.filter((x) => x.id == this.loaiHoaDonId)[0].text
          this.payment_pattern = patternText
        }
      } catch (errpatternText) {
        console.log('~ errpatternText:', errpatternText)
      }
      //var input = {'ds_param':`{\"hdkh_id\":${ this.HdKhId }}`}
      let js_danhsach_rowsStr = JSON.stringify(this.DsThueBao.data)
      let js_danhsach_rows = JSON.parse(js_danhsach_rowsStr)
      let js_phieutt_hdStr = JSON.stringify(dsPhieuTTHD)
      let js_phieutt_hd = JSON.parse(js_phieutt_hdStr)
      let js_hdtbStr = JSON.stringify(layDanhSachTBTT)
      let js_hdtb = JSON.parse(js_hdtbStr)
      let js_danhsach_filter_rowsStr = JSON.stringify(this.DsThueBao.checked)
      let js_danhsach_filter_rows = JSON.parse(js_danhsach_filter_rowsStr)
      let phieutt_id_neo = ''
      try {
        phieutt_id_neo = self.phieutt_id_neo
      } catch (err) {
        console.log('fn_thanhtoan:function ~ err:', err)
      }
      let input = {
        hdkh_id: this.parseInt2(this.HdKhId),
        capnhat_yc: this.params_thanhtoan.capnhat_yc,
        thanhtoan: this.params_thanhtoan.thanhtoan,
        loaihd_id: this.parseInt2(loaihd_id),
        tag_form: this.KieuId,
        ht_tra_id: this.parseInt2(this.hinhThucTTId),
        kenhthu_id: this.parseInt2(this.kenhThuId),
        donvi_id: donviId,
        nhanvien_id: nhanvienId,
        loaihoadon_id: this.chkLoaiHD ? this.parseInt2(this.loaiHoaDonId) : 0,
        seri_id: this.chkLoaiHD ? this.parseInt2(this.loaiSeriId) : 0,
        seri: this.chkLoaiHD ? seriText : '',
        soseri: this.chkLoaiHD ? this.txtHoaDon : '',
        sophieu: this.txtPhieuThuHD,
        phieutt_id_neo: phieutt_id_neo,
        cty_newline: this.cty_newline == true ? 1 : 0,
        ds_para_1: {
          xuat_hd: this.xuat_hd ? 1 : 0,
          check_loaihd: this.chkLoaiHD ? 1 : 0
        },
        ds_para_2: {
          hdtb_id: this.parseInt2(this.HdTbId),
          loaitb_id: this.parseInt2(loaitb_id),
          tinh_id: tinhId,
          datcoc_goi_dadv: this.params_thanhtoan.datcoc_goi_dadv
        },
        ds_para_3: {
          js_danhsach_rows: JSON.stringify(js_danhsach_rows), //  JSON.stringify(this.DsThueBao.data)
          js_danhsach_filter_rows: JSON.stringify(js_danhsach_filter_rows), // JSON.stringify(this.DsThueBao.checked)
          js_phieutt_hd: JSON.stringify(js_phieutt_hd), //JSON.stringify(dsPhieuTTHD)
          js_hdtb: JSON.stringify(js_hdtb), // JSON.stringify(layDanhSachTBTT)
          ma_gd: this.txtMaGD,
          phieutt_id: this.parseInt2(phieutt_id),
          phieutt_id_neo: phieutt_id_neo,
          may_cn: may_cn,
          nguoi_cn: nguoi_cn,
          ngay_cn: ngay_cn, //ngay_ht,
          ip_cn: ip,
          ngay_ht: '', //ngay_ht --> sửa do thanh toán ngày ht lỗi hoàn thiện
          nhanvien_ht_id: nhanvienId,
          phieu_id: this.PhieuId,
          ds_tbdd: layDanhSachTBTT_DiDong,
          open_sync_vina: open_sync_vina,
          check_loaihd: this.chkLoaiHD ? 1 : 0,
          check_xuat_hddt: this.chkHDDT ? 1 : 0,
          ds_matb: ds_matb,
          hinhthuc_tt: this.hinhThucTTId,          
          kenhthu_id: this.parseInt2(this.kenhThuId),
          da_xuat_hddt: da_xuat_hddt,
          trangthai_tt: this.parseInt2(this.trangThaiThanhToanId),
          bras_id: this.p_cboBrasADSL,
          dslam_id: this.p_cboDSLAM,
          donv_tram_id: this.p_donv_tram_id,
          kq_debit_id: 0,
          kieugach: this.kieugach,
          transfertype: this.transferType,
          kq_debit_code: this.kq_debit_code,
          ma_tt: this.txtMaGD,
          xuat_hddt_par: {
            hddt_fkey: hddt_fkey,
            soseri: soseri,
            hddt_status: hddt_status,
            key_parameter: key_parameter,
            note: note
          },
          ngay_hen: this.txtNgayhenTTStatus?this.txtNgayhenTT:null
        }
      }
      console.log('ds_param', input)
       const query = this.$route.query
   
      if (this.$route.query.ob_id && this.$route.query.ob_id !== '') {
            this.ob_id = this.$route.query.ob_id.replaceAll(' ', '+')
      }
      let response = await api.fn_thanhtoan(this.axios, {
        ds_param: JSON.stringify(input),
        hdkh_id:  this.parseInt2(this.HdKhId),
        phieutt_id: this.parseInt2(phieutt_id),
        ob_id: this.parseInt2(this.ob_id),
        trangthai: parseInt(this.trangThaiThanhToanId),
        loaihd_id: loaihd_id || 0,  //add theo yc cua a Luat 7/3/2024
      })
      if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
        let data = response.data.data
        console.log('Ket qua thanh toan:', data)
        return data
      } else {
        this.ShowError(`${response.data.error_code} -${response.data.message}`)
        console.log(response.data.error_code)
      }
    },
    parseInt2(input) {
      try {
        return parseInt(input)
      } catch (parseInt2) {
        console.log(' parseInt2:err', parseInt2)
        return 0
      }
    },
    fn_thanhtoan_pay: async function() {
      //this.HdKhId =1847659
      let self = this
      let donviId = parseInt(this.$root.token.getDonViID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      let tinhId = parseInt(this.$root.token.getPhanVungID())
      let datcoc_goi_dadv = 1
      let loaihd_id = this.LoaiHDId
      let loaitb_id = this.LoaiThueBaoId
      let phieutt_id = this.PhieuTtId == '' ? 0 : this.PhieuTtId
      let dsPhieuTTHD = await this.taoDuLieuPhieuTT()
      let layDanhSachTBTT = this.LayDanhSachTBTT()
      let a = this.LayDanhSachTBTT_DiDong()
      let layDanhSachTBTT_DiDong = a.arrString
      // let ds_matb = a.arrString // a.arr.length;
      let ds_matb = '' //  a.arr_ds_matb != null ? a.arr_ds_matb.join(',') : '' // a.arr.length;
      if (loaihd_id == self.LoaiHopDong.DAT_MOI || loaihd_id == self.LoaiHopDong.CHUYEN_QUYEN || loaihd_id == self.LoaiHopDong.TACHNHAP_TB) {
        ds_matb = a.arr_ds_matb != null ? a.arr_ds_matb.join(',') : '' // a.arr.length;
      }
      let open_sync_vina = this.OPEN_SYNC_VINA ? 1 : 0 //
      let cboDSLAM = 0 //
      let cboBrasADSL = 0 //
      let donv_tram_id = 0 //
      let hddt_fkey = ''
      let hddt_status = 0
      let key_parameter = ''
      let note = 0
      let da_xuat_hddt = this.params.da_xuathd_dt ? 1 : 0 //
      let soseri = '' //
      let nguoi_cn = this.$root.token.getUserName()
      let ngay_ht = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
      let ngay_cn = moment(Date.now()).format('DD/MM/YYYY HH:mm:ss')
      let may_cn = this.tt.mayCn
      let ip = this.tt.ipCn
      if (phieutt_id == 0) {
        phieutt_id = await this.getKeyPhieuTTId()
        this.PhieuTtId = phieutt_id
      }
      dsPhieuTTHD.forEach(function(item) {
        item.PHIEUTT_ID = phieutt_id
      })
      if (this.DsPhieuTT.length > 0) {
        hddt_fkey = this.DsPhieuTT[0].hddt_fkey
      }
      let seriText = ''
      try {
        if (this.chkLoaiHD && this.DmSeri != null && this.DmSeri.length > 0 && this.DmSeri.filter((x) => x.id == this.loaiSeriId).length > 0) {
          seriText = this.DmSeri.filter((x) => x.id == this.loaiSeriId)[0].text
        }
      } catch (errseriText) {
        console.log('🚀 ~ file: Payment.vue:4615 ~ fn_thanhtoan_pay:function ~ errseriText:', errseriText)
      }
      //var input = {'ds_param':`{\"hdkh_id\":${ this.HdKhId }}`}
      let js_danhsach_rowsStr = JSON.stringify(this.DsThueBao.data)
      let js_danhsach_rows = JSON.parse(js_danhsach_rowsStr)
      let js_phieutt_hdStr = JSON.stringify(dsPhieuTTHD)
      let js_phieutt_hd = JSON.parse(js_phieutt_hdStr)
      let js_hdtbStr = JSON.stringify(layDanhSachTBTT)
      let js_hdtb = JSON.parse(js_hdtbStr)
      let js_danhsach_filter_rowsStr = JSON.stringify(this.DsThueBao.checked)
      let js_danhsach_filter_rows = JSON.parse(js_danhsach_filter_rowsStr)
      let phieutt_id_neo = ''
      try {
        phieutt_id_neo = self.phieutt_id_neo
      } catch (err) {
        console.log('fn_thanhtoan:function ~ err:', err)
      }
      let input = {
        hdkh_id: this.parseInt2(this.HdKhId),
        capnhat_yc: this.params_thanhtoan.capnhat_yc,
        thanhtoan: this.params_thanhtoan.thanhtoan,
        loaihd_id: this.parseInt2(loaihd_id),
        tag_form: this.KieuId,
        ht_tra_id: this.parseInt2(this.hinhThucTTId),
        kenhthu_id: this.parseInt2(this.kenhThuId),
        donvi_id: donviId,
        nhanvien_id: nhanvienId,
        loaihoadon_id: this.chkLoaiHD ? this.parseInt2(this.loaiHoaDonId) : 0,
        seri_id: this.chkLoaiHD ? this.parseInt2(this.loaiSeriId) : 0,
        seri: this.chkLoaiHD ? seriText : '',
        soseri: this.chkLoaiHD ? this.txtHoaDon : '',
        sophieu: this.txtPhieuThuHD,
        phieutt_id_neo: phieutt_id_neo,
        cty_newline: this.cty_newline == true ? 1 : 0,
        ds_para_1: {
          xuat_hd: this.xuat_hd ? 1 : 0,
          check_loaihd: this.chkLoaiHD ? 1 : 0
        },
        ds_para_2: {
          hdtb_id: this.parseInt2(this.HdTbId),
          loaitb_id: this.parseInt2(loaitb_id),
          tinh_id: tinhId,
          datcoc_goi_dadv: this.params_thanhtoan.datcoc_goi_dadv
        },
        ds_para_3: {
          js_danhsach_rows: JSON.stringify(js_danhsach_rows), //  JSON.stringify(this.DsThueBao.data)
          js_danhsach_filter_rows: JSON.stringify(js_danhsach_filter_rows), // JSON.stringify(this.DsThueBao.checked)
          js_phieutt_hd: JSON.stringify(js_phieutt_hd), //JSON.stringify(dsPhieuTTHD)
          js_hdtb: JSON.stringify(js_hdtb), // JSON.stringify(layDanhSachTBTT)
          ma_gd: this.txtMaGD,
          phieutt_id: this.parseInt2(phieutt_id),
          phieutt_id_neo: phieutt_id_neo,
          may_cn: may_cn,
          nguoi_cn: nguoi_cn,
          ngay_cn: ngay_cn, //ngay_ht,
          ip_cn: ip,
          ngay_ht: '',//ngay_ht --> sửa do thanh toán ngày ht lỗi hoàn thiện
          nhanvien_ht_id: nhanvienId,
          phieu_id: this.PhieuId,
          ds_tbdd: layDanhSachTBTT_DiDong,
          open_sync_vina: open_sync_vina,
          check_loaihd: this.chkLoaiHD ? 1 : 0,
          check_xuat_hddt: this.chkHDDT ? 1 : 0,          
          ds_matb: ds_matb,
          hinhthuc_tt: this.hinhThucTTId,
          kenhthu_id: this.parseInt2(this.kenhThuId),
          da_xuat_hddt: da_xuat_hddt,
          trangthai_tt: this.parseInt2(this.trangThaiThanhToanId),
          bras_id: this.p_cboBrasADSL,
          dslam_id: this.p_cboDSLAM,
          donv_tram_id: this.p_donv_tram_id,
          kq_debit_id: 0,
          kieugach: this.kieugach,
          transfertype: this.transferType,
          kq_debit_code: this.kq_debit_code,
          ma_tt: this.txtMaGD,
          xuat_hddt_par: {
            hddt_fkey: hddt_fkey,
            soseri: soseri,
            hddt_status: hddt_status,
            key_parameter: key_parameter,
            note: note
          },
          kenhThuId: this.kenhThuId,
          txtMaGD: this.txtMaGD,
          walletAccountId: this.walletAccountId,
          walletId: this.walletId,
          txtTongTienTra: this.txtTongTienTra,
          transferType: this.transferType,
          phieuId: this.PhieuTtId,
          ngay_hen: this.txtNgayhenTT
        }
      } 
      console.log('ds_param fn_thanhtoan_pay:', input)
      let response = await api.fn_thanhtoan_pay(this.axios, {
        ds_param: JSON.stringify(input)
      })
      if ((response.data.error == 200 || response.data.error == '200') && response.data.error_code == 'BSS-00000000') {
        let data = response.data.data
        console.log('Ket qua thanh toan:', data)
        return data
      } else {
        this.ShowError(`${response.data.error_code} -${response.data.message}`)
        console.log(response.data.error_code)
      }
    },
    async HienThiTTHopDongKH() {
      try {
        await this.searchTheoMaGD()
      } catch (err) {
        console.log('HienThiTTHopDongKH:error', err)
      } finally {
        this.loading(false)
      }
    },
    // End Thanh toan
    ChuyenLinkUploadHoSoDienTu: function() {
      /*
      try
            {
                var f = new frmHoSo_KH();
                f.MdiParent = this.MdiParent;
                f.Tag = "1";
                f.txtMaGD.Text = txtMaGD.Text;
                if (txtMaGD.Text != "")
                    try
                    {
                        f.TIM_GD(null, 1);
                    }
                    catch
                    {
                    }
                f.Show();
            }
            catch (Exception ex)
            {
            }
      */
      try {
        // this.refuploadEProfile.isPopup = true
        this.paramHSDT.value = this.txtMaGD
        this.$refs.refuploadEProfile.txtMaGD_txtMa_TB = this.txtMaGD
        this.$refs.refuploadEProfile.getPropsData()
        // this.$refs.refuploadEProfile.txtMaGD_txtMa_TB_Enter()
        // this.refuploadEProfile.listbox.danhsach_thuebao_theo_makh.value.ma_tb = this.txtMaGiaoDich
        // this.refuploadEProfile.txtMaGD_txtMa_TB = this.txtMaGiaoDich
        //this.refuploadEProfile.Tag = 1
        //this.refuploadEProfile.txtMaGD_txtMa_TB_Enter()
      } catch (err) {
        console.log('ChuyenLinkUploadHoSoDienTu-Error', err)
      } finally {
        this.$refs.dialogObjHoSo.show(true)
      }
      //this.$router.push({ name: 'UploadEProfile', params: { MaGd: this.txtMaGD } })
    },
    getBalance: async function() {
      if (await this.loginVnptPay()) {
        let input = {}
        let response = await api.getBalance(this.axios, input, this.token)
        return response
      }
      return null
    },
    findVNPTPay(result, key, startText, endText) {
      let tmp = ''
      let i = result.indexOf(key) + startText.length
      let tmpData = result.split(startText)[1]
      tmp = tmpData.split(endText)[0]
      console.log()
      tmp = tmp.trim('tmp', tmp)
      return tmp
    },
    loginVnptPay: async function() {
      if (this.walletId != null && this.walletId != '' && this.walletAccountId != '' && this.token != '') {
        // da dang nhap co thong tin vi
        console.log('da dang nhap co thong tin vi:', this.walletId, this.walletAccountId, this.token)
        return true
      }
      let input = {}
      let response = await api.loginVnptPay(this.axios, input)
      console.log('loginVnptPay', response)
      if (response.data.errorCode == '0' || response.data.errorCode == 0) {
        if (response.data.data != null) {
          this.walletId = response.data.data.walletId
          this.walletAccountId = response.data.data.walletAccountId
          this.token = response.data.data.token
          console.log(`walletId:${this.walletId},walletAccountId:${this.walletAccountId},token:${this.token} `)
          if ((this.walletId == null || this.walletId == '') && response.data.data.url != null && response.data.data.url.length > 0) {
            //
            this.$bvModal
              .msgBoxConfirm('Vui lòng đăng nhập hệ thống VNPT Pay trước khi sử dụng tính năng này !', {
                title: '',
                size: 'sm',
                okTitle: 'Login VNPT Pay',
                cancelTitle: 'Hủy'
              })
              .then(async (v) => {
                if (!v) {
                  self.ShowAlert(`Bạn đã hủy đăng nhập VNPT Pay `)
                  return false
                }
                window.open(response.data.data.url, '_blank').focus()
                return false
              })
            //
          } else {
            return true
          }
        } else {
          this.walletId = ''
          this.walletAccountId = ''
          this.token = ''
          return false
        }
      } else {
        this.ShowError(`${response.data.faultCode} ${response.data.faultString}`)
        this.walletId = ''
        this.walletAccountId = ''
        this.token = ''
        return false
      }
    },
    getNVTCProcess: function(value) {
      console.log(value)
      //this.txtNguoiBaoLanh.Text = value.maNVTC + " - " + value.ten_nvtc;
      this.txtNguoiBL = `${value.maNVTC}-${value.tenNVTC}`
    },
    thongTinEmailOpen: async function() {
      
      setTimeout(()=>{
        this.$refs.dialogObjthongTinEmail.show()
      }, 500)
      
    },
    async lienHeOpen() {
      if (this.HdKhId > 0) {
        this.$refs.dialogObjthongtinlienhe.show()
        setTimeout(()=>{
          this.$refs.thongTinLienHePopUp.hdkh_id = this.HdKhIdInt
          this.$refs.thongTinLienHePopUp.khachhang_id = this.KhachHangIdInt
          this.$refs.thongTinLienHePopUp.txtTenLH = this.txtTenKH
          this.$refs.thongTinLienHePopUp.txtDiaChiLH = this.txtDiaChiKH
          let self=this
          Promise.all([this.$refs.thongTinLienHePopUp.getThongTinLienHe(), this.$refs.thongTinLienHePopUp.getThongTinMD_CG(), this.$refs.thongTinLienHePopUp.getThongTinMD_DG()])
          .then((value)=>{
            self.$refs.thongTinLienHePopUp.txtTenLH = this.txtTenKH
            self.$refs.thongTinLienHePopUp.txtDiaChiLH = this.txtDiaChiKH
          })
          .catch((err) => {
            console.log('Lỗi lấy thông tin liên hệ ', err)
          })
          
        }, 500)
      }
    },
    async get_hoadon_vt01(phieutt_id) {
      var input = {
        vphieu_id: phieutt_id
      }
      let response = await api.get_hoadon_vt01(this.axios, input)
      if (response.data.error == 200 || response.data.error == '200') {
        let data = response.data.data
        return data
      } else {
        return 0
      }
    },
    async tsbtnInHoaDon_Click() {
      try {
        console.log('🚀 ~ file: Payment.vue ~ line 3308 ~ tsbtnInHoaDon_Click ~ this.in_bbn_tt', this.in_bbn_tt)
        if (this.in_bbn_tt) {
          /*
          let nhanvienId = this.$root.token.getNhanVienID()
          this.dsGiaTriHD.push(this.PhieuTtIdIn)
          this.dsGiaTriHD.push('0')
          this.dsGiaTriHD.push(nhanvienId)
          this.$refs.refInHD.dsGiaTri = this.dsGiaTriHD
          this.$refs.refInHD.XemNgay(this.dsGiaTriHD)
          */
          this.$refs.refselectPrintPhieuChi.openDialog(this.PhieuTtIdIn)
        } else {
          let data = await this.get_hoadon_vt01(this.PhieuTtIdIn)
          this.dataReport = data.map((x) => ({ dong: x.DONG, noidung: x.NOIDUNG }))
          // this.$refs.printReport1.data = data.map((x) => ({ dong: x.DONG, noidung: x.NOIDUNG }))
          this.$refs.printReport.startPrint(this.dataReport)
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 3320 ~ tsbtnInHoaDon_Click ~ err', err)
      } finally {
      }
    },
    async tsbtnInHoaDon_Click_() {
      try {
        this.modelIn.hdkh_id = this.HdKhId
        this.modelIn.ma_gd = this.txtMaGDTT
        this.modelIn.ngaylap_hd = this.txtNgayHD
        this.modelIn.ngay_yc = this.txtNgayYeuCau
        console.log('modelIn', this.modelIn)
        this.$refs.popupInHopDong.showModal()
      } catch (err) {
        console.log('tsbtnInHoaDon_Click', err)
      } finally {
      }
    },
    async tsbtnXuaHD_Click() {
      console.log('tsbtnXuaHD_Click', 'vhdkh_id', this.vhdkh_id, 'i_ma_gd', this.vma_gd, 'linkForm', 1)
      try {
        this.loading(true)
        this.$refs.refSelfManageEinvoice.vhdkh_id = this.vhdkh_id
        this.$refs.refSelfManageEinvoice.i_ma_gd = this.vma_gd
        this.$refs.refSelfManageEinvoice.linkForm = 1
        await this.$refs.refSelfManageEinvoice.frmXuat_HoaDon_DienTu_Dong_Load()
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue ~ line 3492 ~ tsbtnXuaHD_Click ~ err', err)
      } finally {
        this.loading(false)
        this.$refs.refSelfManageEinvoiceDialog.show()
      }
    },
    async tsbtnDongBoVNP_Click() {
      try {
        this.loading(true)
        this.$refs.refDongBoLapMoiVina.isPopUp = true
        await this.$refs.refDongBoLapMoiVina.pageLoad()
      } catch (err) {
        console.log('🚀tsbtnDongBoVNP_Click', err)
      } finally {
        this.loading(false)
        this.$refs.refDongBoLapMoiVinaDialog.show()
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponentPayment')
        return
      }
      this.loading(true)
      try {
      } catch (err) {}
    },
    async ValidateModal(bvEvent, modalId) {
      switch (modalId) {
        case 'modal-tieunganh':
          // if((this.tieunganh.dsTNC1 && this.tieunganh.dsTNC1.length>0 && (!this.tieunganh.TNC1_ID || this.tieunganh.TNC1_ID<=0)) || (this.tieunganh.dsTNC2 && this.tieunganh.dsTNC2.length>0 && (!this.tieunganh.TNC2_ID || this.tieunganh.TNC2_ID<=0)) || (this.tieunganh.dsTNC3 && this.tieunganh.dsTNC3.length>0 && (!this.tieunganh.TNC3_ID || this.tieunganh.TNC3_ID<=0)) || (this.tieunganh.dsTNC4 && this.tieunganh.dsTNC4.length>0 && (!this.tieunganh.TNC4_ID || this.tieunganh.TNC4_ID<=0)))
          //   await this.$toast.error('Bạn chưa chọn chi tiết ngành nghề. Hãy kiểm tra lại!');
          return
        default:
          return
      }
    },
    tsbtnInPhieu_HH_Click() {
      this.dsGiaTri = []
      this.$refs.popupXemBaoCao.ma_bc = 'MAU_KYNHAN_HOAHONG'
      this.$refs.popupXemBaoCao.dsGiaTri = []
      this.$refs.popupXemBaoCao.dsGiaTri.push(this.PhieuTtId)
      this.$refs.popupXemBaoCao.XemNgay()
    },
    async XuatHDDT(self) {
      //let self = self
      if (self == null) self = this
      if (self.chkLoaiHD == true && self.chkHoaDonDienTu == true && self.params.da_xuathd_dt == false) {
        const _exits = await self.listInvByCus(self.txtMaGDTT)
        if (_exits == true) {
          console.log('Đã có HDDT với mã giao dịch:', self.txtMaGDTT, ':', _exits)
          //return true
        } else {
          console.log('Chưa tồn tại HDDT với mã giao dịch :', self.txtMaGDTT, ':', _exits)
        }
        try {
          self.loading(true)
          try {
            self.$refs.refeInvoice.Clean()
          } catch (errr1) {
            console.log('XuatHDDT ~ errr1:', errr1)
          }
          //doing
          // await self.lay_hopdong_tb_hddt(self.HdKhId, 1)
          await self.lay_hopdong_tb_hddt_tt78_v2(self.HdKhId, 1, Number(self.chHDDTTGDaDichVu))

          self.$refs.refeInvoice._ma_gd = self.txtMaGDTT
          self.$refs.refeInvoice.vma_gd = self.txtMaGDTT
          self.$refs.refeInvoice.txtMaGD = self.txtMaGDTT
          self.$refs.refeInvoice.vhdkh_id = self.HdKhId
          await self.$refs.refeInvoice.Xuat_HDDT_LOAD()
          self.$refs.refeInvoice.ddlThaoTac = 6
          try {
            await self.$refs.refeInvoice.HienThiThongTin()
          } catch (errHienThiThongTin) {
            console.log('await self.$refs.refeInvoice.HienThiThongTin() ~ err1:', errHienThiThongTin)
          }
          try {
            await self.$refs.refeInvoice.setCustomerInfo(self.customerInfo)
          } catch (errsetCustomerInfo) {
            //console.log("await self.$refs.refeInvoice.HienThiThongTin() ~ err1:", err)
          }
          self.$refs.refeInvoice.txtMaGD = self.txtMaGDTT
          self.$refs.refeInvoice.vma_gd = self.txtMaGDTT
          self.$refs.refeInvoice.vseri_id = self.seri_id
          self.$refs.refeInvoice.json_update.seri_id = this.seri_id
          try {
            self.$refs.refeInvoice.vtt_hddt_hd_user = self.params.vtt_hddt_hd_user
          } catch (err) {}
          try {
            self.$refs.refeInvoice.set_vtt_hddt_hd_user(self.params.vtt_hddt_hd_user)
          } catch (err) {}
          try {
            self.$refs.refeInvoice.set_seri_pattern(self.payment_serial, self.payment_pattern)
          } catch (err) {}
          let xuatHoaDonResult = await self.$refs.refeInvoice.CapNhat()
          console.log('Ket qua xuat hoa don:', xuatHoaDonResult)
          if (xuatHoaDonResult.result) {
            // this.hddt_status=1
            if (!xuatHoaDonResult.showMessage) self.ShowSuccess(xuatHoaDonResult.message)
          } else {
            console.log('Xuất hóa đơn lỗi :', xuatHoaDonResult)
            self.ShowError(xuatHoaDonResult.message)
          }
        } catch (err) {
          console.log(err)
        } finally {
          self.loading(false)
          //self.$refs.refeInvoiceDialog.show()
        }
      } else {
        console.log('Không xuất HDDT')
      }
    },
    async clsTest() {},
    async Test_Click() {
      let self = this
      try {
        this.loading(true)
        self.$refs.refeInvoice._ma_gd = this.vma_gd
        self.$refs.refeInvoice.txtMaGD = this.vma_gd
        await self.$refs.refeInvoice.Xuat_HDDT_LOAD()
        await self.$refs.refeInvoice.HienThiThongTin()
      } catch (err) {
        console.error(err)
      } finally {
        this.loading(false)
        self.$refs.refeInvoiceDialog.show()
      }
    },
    async AUTO_PICK_PORT(dr) {
      if (this.loaihd_id == this.LoaiHopDong.DAT_MOI || this.loaihd_id == this.LoaiHopDong.DI_CHUYEN) {
        if (dr['kieuld_id'] == '') {
          return
        }
        let kieuld_id = parseInt(dr['kieuld_id']) //1<->lap kem; 2<->lap tren duong co san
        let tt_port = 0
        let vport_id = 0
        let vvci_vpi_id = 0
        let vkieu = 0
        let vthamso = '-1'
        let matb_tn = ''
        let zma_tb = dr['ma_tb']
        let current_hdtb_id = parseInt(dr['hdtb_id'])
        let congnghe_id = parseInt(dr['congnghe_id'])
        let vloaitbid = parseInt(dr['loaitb_id'])
        let pass = dr['password']
        let malt = dr['ma_lt']
        let ip = dr['ip']
        let vtocdo_id = parseInt(dr['tocdo_id'])
        let ten_tb = dr['ten_tb']
        if (dr['matb_tn'] == '') {
          tt_port = 1 // TRANGTHAI_PORT.CHUA_SD;
        } else {
          // #region Nếu có mã tn thì làm
          matb_tn = dr['matb_tn']
          //Nếu lắp trên đường có sẵn -> kieu=2
          let vkieu_lap = parseInt(dr['kieu'])
          if (vkieu_lap == 2) {
            //#region nuế lắp line có sẵn và không sẵn
            this.vkieu = this.KIEULAP.LAP_LINE_SAN
            //if (bangts.KT_LAP_KEM_CO_SAN(kieuld_id, KIEULAP.LAP_LINE_SAN))
            if (await this.KT_LAP_KEM_CO_SAN(kieuld_id, this.KIEULAP.LAP_LINE_SAN)) {
              // Kiểm tra xem mã truy nhập đã được cấp port hay chưa, chưa cấp thì không được chọn port.
              // let ds = bangts.Lay_ds_port_theo_ma_tb(matb_tn, KIEULAP.LAP_LINE_SAN);
              let ds = await this.Lay_ds_port_theo_ma_tb(matb_tn, this.KIEULAP.LAP_LINE_SAN)
              if (ds == null && ds.length <= 0) {
                return
              }
              if (ds[0]['port_id'] == null || ds[0]['port_id'] == '') {
                return
              }
              vport_id = parseInt(ds[0]['port_id'])
              vvci_vpi_id = parseInt(ds[0]['vci_vpi_id'])
              tt_port = 0
            } else {
              vport_id = 0
              vvci_vpi_id = 0
              tt_port = this.TRANGTHAI_PORT.CHUA_SD
            }
            // #endregion
          }
          // #region lắp trên đường ko có sẵn
          else if (vkieu_lap == 1) {
            vkieu = 1 // KIEULAP.LAP_KEM;
            //if (bangts.KT_LAP_KEM_CO_SAN(kieuld_id, KIEULAP.LAP_KEM))
            if (await this.KT_LAP_KEM_CO_SAN(kieuld_id, 1 /* KIEULAP.LAP_KEM */)) {
              // Kiểm tra xem mã truy nhập đã được cấp port hay chưa, chưa cấp thì không được chọn port.
              // DataSet ds = new DataSet();
              //let ds = bangts.Lay_ds_port_theo_ma_tb(matb_tn, KIEULAP.LAP_KEM);
              let ds = await this.Lay_ds_port_theo_ma_tb(matb_tn, this.KIEULAP.LAP_KEM)
              if (ds == null || ds.length <= 0) {
                return
              }
              if (ds[0]['port_id'] == null || ds[0]['port_id'] == '') {
                return
              }
              vport_id = parseInt(ds[0]['port_id'])
              vvci_vpi_id = parseInt(ds[0]['vci_vpi_id'])
              tt_port = 0
            } else {
              vvci_vpi_id = 0
              vport_id = 0
              tt_port = this.TRANGTHAI_PORT.CHUA_SD
            }
          }
          // Lấy thông tin đơn vị phường phố
          let vdonvi_id = parseInt(dr['donv_tram_id'])
          if (vdonvi_id == -1) return
          this.p_donv_tram_id = vdonvi_id
          let diachi_id = -1
          if (loaihd_id == this.LoaiHopDong.DAT_MOI)
            //diachi_id = parseInt(checkdata.MAP_ID("diachild_id", DatabaseConstants.DB2 + ".diachi_hdtb", "where hdtb_id = " + CURRENT_HDTB_ID));
            diachi_id = parseInt(await this.map_diachild_id(current_hdtb_id))
          let phuongid = 0
          let phoid = 0
          let apid = 0
          let khuid = 0
          if (diachi_id != -1 && diachi_id != 0) {
            // DataSet ds_diachi = new DataSet();
            // let ds_diachi = bangts.Laythongtin_table(DatabaseConstants.DB2 + ".diachi", " diachi_id = " + diachi_id, "*", "");
            let ds_diachi = await this.Laythongtin_table_diachi(diachi_id)
            if (ds_diachi != null && ds_diachi.length > 0) {
              if (ds_diachi[0]['phuong_id'] != null && ds_diachi[0]['phuong_id'] != '') phuongid = parseInt(ds_diachi[0]['phuong_id'])
              if (ds_diachi[0]['pho_id'] != null && ds_diachi[0]['pho_id'] != '') phoid = parseInt(ds_diachi[0]['pho_id'])
              if (ds_diachi[0]['ap_id'] != null && ds_diachi[0]['ap_id'] != '') apid = parseInt(ds_diachi[0]['ap_id'])
              if (ds_diachi[0]['khu_id'] != null && ds_diachi[0]['khu_id'] != '') khuid = parseInt(ds_diachi[0]['khu_id'])
            }
          } else {
            return
          }
          //
          let ds_dslam = []
          if (matb_tn == '') ds_dslam = await this.Lay_DS_DsLam(vdonvi_id, 39 /*LOAI_DV.TRAM_PORT*/, phuongid, phoid, apid, khuid, vloaitbid, vthamso, congnghe_id)
          else ds_dslam = await this.Lay_dslam_theo_port_id(vport_id)
          let vport_xin_id = 0
          let cboBrasADSL = 0
          let cboDSLAM = 0
          let p_ = []
          if (ds_dslam != null && ds_dslam.length > 0) {
            // #region nếu có danh sahcs làm
            let k_tra = false
            for (let i = 0; i < ds_dslam.length; i++) {
              if (ds_dslam[i]['dslam_id'] != '') {
                let dslam_id = 0
                let ip_dslam_tinh = ''
                let txttram = ''
                let txtipdslam = ''
                let tramtb_id = parseInt(ds_dslam[i]['donvi_id'])
                dslam_id = parseInt(ds_dslam[i]['dslam_id'])
                ip_dslam_tinh = await this.map_ip(dslam_id) //obj.MAP_ID("ip", DatabaseConstants.DB2 + ".dslam", "where dslam_id = " + dslam_id);
                txttram = await this.map_ten_dv(tramtb_id) // checkdata.MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id = " + tramtb_id);
                if (ip_dslam_tinh == '-1') txtipdslam = ''
                else txtipdslam = ip_dslam_tinh
                let dsport = []
                dsport = await this.LAY_DS_PORT_TUDONG(dslam_id, vport_id, vvci_vpi_id, tt_port, vloaitbid)
                if (dsport != null && dsport.length > 0) {
                  let port_id = parseInt(dsport[0]['port_id']) //f.port_id;
                  let vci_vpi_id = parseInt(dsport[0]['vci_vpi_id']) //f.vci_vpi_id;
                  cboBrasADSL = parseInt(dsport[0]['bras_id'])
                  this.p_cboBrasADSL = cboBrasADSL
                  cboDSLAM = parseInt(dsport[0]['dslam_cha_id'])
                  this.p_cboDSLAM = cboDSLAM
                  vport_id = 0
                  vvci_vpi_id = 0
                  //Kiểm tra xem có sự thay đổi port hay không
                  let dsktradb = [] //new DataSet();
                  let dsktrahd = [] // new DataSet();
                  //#region Cập nhật port mới
                  //Kiểm tra port đang chọn
                  dsktradb = await this.ktra_dk_port(zma_tb, port_id, vci_vpi_id, 1)
                  dsktrahd = await this.ktra_dk_port(zma_tb, port_id, vci_vpi_id, 2)
                  if (dsktradb == null) dsktradb = []
                  if (dsktradb == null) dsktrahd = []
                  if (dsktrahd.length == 0 && dsktrahd.length == 0) {
                    await this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, this.TRANGTHAI_PORT.TAM_CAP)
                    await this.CapNhat_TrangThai_PortID(port_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_ds_matb(port_id, vci_vpi_id, zma_tb)
                    await this.Capnhat_port_vatly(port_id, this.TRANGTHAI_PORT.DA_SD)
                  } else if (dsktradb != null && dsktradb.length != 0 && dsktrahd.length == 0) {
                    let vds_matb = ''
                    for (let k = 0; k < dsktradb.length; k++) {
                      vds_matb = vds_matb + dsktradb[k]['ma_tb'] + '; '
                      if (vds_matb.Length > 3000) break
                    }
                    vds_matb = vds_matb + zma_tb
                    await this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_TrangThai_PortID(port_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb)
                    await this.Capnhat_port_vatly(port_id, this.TRANGTHAI_PORT.DA_SD)
                  } else if (dsktradb.length == 0 && dsktrahd.length != 0) {
                    let vds_matb = ''
                    for (let k = 0; i < dsktrahd.length; k++) {
                      vds_matb = vds_matb + dsktrahd[k]['ma_tb'] + '; '
                      if (vds_matb.Length > 3000) break
                    }
                    vds_matb = vds_matb + zma_tb
                    await this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, this.TRANGTHAI_PORT.TAM_CAP)
                    await this.CapNhat_TrangThai_PortID(port_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb)
                    await this.Capnhat_port_vatly(port_id, this.TRANGTHAI_PORT.DA_SD)
                  } else if (dsktradb.length != 0 && dsktrahd.length != 0) {
                    let vds_matb = ''
                    for (let k = 0; i < dsktradb.length; k++) {
                      vds_matb = vds_matb + dsktradb[k]['ma_tb'] + '; '
                      if (vds_matb.length > 3000) break
                    }
                    for (let k = 0; i < dsktrahd.length; k++) {
                      vds_matb = vds_matb + dsktrahd[k]['ma_tb'] + '; '
                      if (vds_matb.length > 3000) break
                    }
                    vds_matb = vds_matb + zma_tb
                    await this.CapNhat_TrangThai_Port(port_id, vci_vpi_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_TrangThai_PortID(port_id, this.TRANGTHAI_PORT.DA_SD)
                    await this.CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb)
                    await this.Capnhat_port_vatly(port_id, this.TRANGTHAI_PORT.DA_SD)
                  }
                  //#endregion
                  let dsdslam = []
                  dsdslam = await this.Lay_dslam_theo_port_id(port_id)
                  if (dsdslam != null && dsdslam.length > 0) {
                    tramtb_id = parseInt(dsdslam[0]['donvi_id'])
                    congnghe_id = parseInt(dsdslam[0]['congnghe_id'])
                  }
                  //Cập nhật thông số port vào bảng hdtb_ims
                  //hoancong.CAPNHAT_THONGTIN_PORT_INT(CURRENT_HDTB_ID, port_id, vci_vpi_id, parseInt(cboBrasADSL.SelectedValue),
                  //    parseInt(cboDSLAM.SelectedValue), txtMatKhauADSL.Text.Trim(), txtMaLT.Text.Trim(), txtIPADSL.Text.Trim(), tramtb_id);
                  await this.CAPNHAT_THONGTIN_PORT_INT_V2(current_hdtb_id, port_id, vci_vpi_id, parseInt(cboBrasADSL), parseInt(cboDSLAM), pass, malt, ip, tramtb_id, congnghe_id)
                  k_tra = true
                  p_ = dsport
                  vport_xin_id = port_id
                  //if (inDex_load != 0)
                  //{
                  //    if (gvDanhSach.DataRowCount == 1)
                  //        inDex_load = 0;
                  //    dtgDanhSach.CurrentCell = dtgDanhSach.Rows[inDex_load].Cells[0];
                  //    dtgDanhSach.CurrentRow.Selected = false;
                  //    dtgDanhSach.Rows[inDex_load].Selected = true;
                  //}
                  break
                }
              }
            }
            //#endregion
            if (k_tra == false) {
              return
            } else {
              let iptinh = false
              //let viptinh = checkdata.MAP_ID("iptinh", DatabaseConstants.DB2 + ".tocdo_adsl", "where tocdo_id ='" + dr["TOCDOTHUC_ID"].Trim() + "'");
              let viptinh = await this.map_iptinh(dr['tocdothuc_id'])
              if (viptinh == '1') {
                // hoangpkn : 16/05/2016
                // Thêm kiểm tra nếu đã được cấp port -> có bras -> mới cho hiển thị check port tự động hoặc chọn port
                if (vport_xin_id != null && vport_xin_id != '') {
                  iptinh = true
                  await this.CapNhat_IP_Tinh(current_hdtb_id, ip, '', '', 1) //IPV6: LAMNT TAM DE VALUE = ""
                }
              } else {
                iptinh = false
              }
              if (pass != '') await this.KichHoat(current_hdtb_id, vport_xin_id, pass, zma_tb, p_, vloaitbid, vtocdo_id, ten_tb, dr, cboBrasADSL, cboDSLAM, iptinh, matb_tn)
            }
          } else {
            return
          }
          // #endregion
          // #endregion
        }
      }
    },
    async Xuathoadon_VTT() {},
    async Xuathoadon() {},
    async KiemTra_AUTO_PORT_KICHOAT() {
      try {
        if (this.params.auto_port_kh == 1 && (this.loaihd_id == this.LoaiHopDong.DAT_MOI || this.loaihd_id == this.LoaiHopDong.DI_CHUYEN)) {
          if (this.DsThueBao.data != null && this.DsThueBao.data.length > 0) await this.AUTO_PICK_PORT(this.DsThueBao.data[0])
        }
      } catch (err) {
        console.log('KiemTra_AUTO_PORT_KICHOAT:err', err)
      }
    },
    async Laythongtin_table_diachi(diachi_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      let data = this.GetData(
        await api.lay_dulieu_bang_theo_dieukien(this.axios, {
          schema: 'css',
          table_name: 'diachi',
          list_of_cols: '*',
          where: `diachi_id= ${diachi_id}  and phanvung_id=${phanvung_id}`,
          order: ''
        })
      )
      return data
    },
    async thongtin_nguoidung() {
      try {
        if (this.tt_nd != null && this.tt_nd.nhanvien_id != null && this.tt_nd.nhanvien_id > 0) {
          return this.tt_nd
        }
        const res = await api.thongtin_nguoidung(this.axios, {})
        let userinfo = res.data.data ? res.data.data : {}
        console.log('🚀thongtin_nguoidung ~ a', userinfo)
        this.tt_nd = userinfo
        this.checkLoginPayment = true
        return userinfo
      } catch (err) {
        this.checkLoginPayment = false
        console.log('🚀 Thongtin_nguoidung ~ err', err)
        this.tt_nd = {}
        return {}
      }
    },
    async thongtin() {
      try {
        if (this.tt != null && this.tt.ipCn != null && this.tt.ipCn != '127.0.0.1') {
          return this.tt
        }
        const res = await api.thongtin(this.axios, {})
        let tt = res.data.data ? res.data.data : {}
        console.log('thongtin ~ a', tt)
        this.tt = tt
        this.checkLoginPayment = true
        return tt
      } catch (err) {
        this.checkLoginPayment = false
        console.log('🚀 thongtin ~ err', err)
        this.tt = { ipCn: '127.0.0.1', mayCn: 'web' }
        return this.tt
      }
    },
    async map_diachild_id(hdtb_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      let input = {
        id_neo: 'diachild_id',
        in_table: 'css.diachi_hdtb',
        in_dk: `where hdtb_id = ${hdtb_id} and phanvung_id=${phanvung_id}`
      }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async map_iptinh(tocdo_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      let input = {
        id_neo: 'iptinh',
        in_table: 'css.tocdo_adsl',
        in_dk: `where tocdo_id = ${tocdo_id} and phanvung_id=${phanvung_id}`
      }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async map_ip(dslam_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      let input = {
        id_neo: 'ip',
        in_table: 'css.dslam',
        in_dk: `where dslam_id = ${dslam_id} and phanvung_id=${phanvung_id}`
      }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async map_ten_dv(tramtb_id) {
      let phanvung_id = this.$root.token.getPhanVungID()
      let input = {
        id_neo: 'ten_dv',
        in_table: 'admin.donvi',
        in_dk: `where donvi_id = ${tramtb_id} and phanvung_id=${phanvung_id}`
      }
      let data = this.GetData(await api.map_id(this.axios, input))
      return data
    },
    async Lay_ds_port_theo_ma_tb(matb, kieuId) {
      let input = {
        p_ma_tb: matb,
        p_kieu_id: kieuId
      }
      let data = this.GetData(await api.fn_kt_lap_kem_co_san(this.axios, input))
      return data
    },
    select2FocusById(id) {
      try {
        $(`#${id}`).select2('focus')
      } catch (err) {
        console.log('🚀 select2FocusById ~ err', err)
      }
    },
    async KT_LAP_KEM_CO_SAN(kieuld_id, vkieu) {
      let input = {
        p_kieu: vkieu,
        p_kieuld_id: kieuld_id
      }
      let data = this.GetData(await api.fn_kt_lap_kem_co_san(this.axios, input))
      if (data != null && data > 0) return true
      return false
    },
    async kiemtra_datcoc_goi_dadv(hdkh_id) {
      try {
        let input = {
          hdkh_id: hdkh_id
        }
        let data = this.GetData(await api.kiemtra_datcoc_goi_dadv(this.axios, input))
        if (data == null) return 'ok'
        return data
      } catch (error) {
        console.log('kiemtra_datcoc_goi_dadv-err', error)
        return 'ok'
      }
    },
    async Lay_DS_DsLam(donvi_id, loaidv_id, phuong_id, pho_id, ap_id, khu_id, vloaitb_id, vthamso, vcongnghe_id) {
      let input = {
        p_donvi_id: donvi_id,
        p_loaidv_id: loaidv_id,
        p_phuong_id: phuong_id,
        p_pho_id: pho_id,
        p_ap_id: ap_id,
        p_khu_id: khu_id,
        p_loaitb_id: vloaitb_id,
        p_thamso: vthamso,
        p_congnghe_id: vcongnghe_id
      }
      let data = this.GetData(await api.Lay_DS_DsLam(this.axios, input))
      return data
    },
    async Lay_dslam_theo_port_id(vport_id) {
      let input = {
        port_id: vport_id
      }
      let data = this.GetData(await api.lay_dslam_theo_port_id(this.axios, input))
      return data
    },
    async lay_hopdong_tb_hddt(hdkh_id, kieu) {
      try {
        let input = {
          vhdkh_id: hdkh_id,
          vkieu: kieu
        }
        let data = this.GetData(await api.lay_hopdong_tb_hddt(this.axios, input))
        this.ds_tb_hddt = data
        return data
      } catch (err) {
        console.log('lay_hopdong_tb_hddt ~ err', err)
      }
    },

    async lay_hopdong_tb_hddt_tt78_v2(vhdkh_id,  vkieu,  vgopnhom ) {
      try {
        let data = this.GetData(await api.lay_hopdong_tb_hddt_tt78_v2(this.axios, { vhdkh_id,  vkieu,  vgopnhom}))
        console.log('lay_hopdong_tb_hddt_tt78_v2 ~ data', data) //;
        
        debugger

        this.ds_tb_hddt = data
        return data
      } catch (err) {
        console.log('lay_hopdong_tb_hddt ~ err', err)
      }
    },
    


    async LAY_DS_PORT_TUDONG(dslam_id, vport_id, vvci_vpi_id, tt_port, vloaitbid) {
      let input = {
        dslam_id: dslam_id,
        port_id: vport_id,
        //trangthai: trangThaiPort,
        loaitb_id: vloaitbid,
        trangthai: tt_port,
        vci_vpi_id: vvci_vpi_id
      }
      let data = this.GetData(await api.lay_dslam_theo_port_id(this.axios, input))
      return data
    },
    async ktra_dk_port(zma_tb, port_id, vci_vpi_id, vkieu) {},
    // may ham nay trong db da xu ly
    async CapNhat_TrangThai_Port(port_id, vci_vpi_id, trangthai) {},
    async CapNhat_TrangThai_PortID(port_id, trangthai) {},
    async CapNhat_ds_matb(port_id, vci_vpi_id, vds_matb) {},
    async Capnhat_port_vatly(port_id, trangthai) {},
    async CAPNHAT_THONGTIN_PORT_INT_V2() {},
    async CapNhat_IP_Tinh(hdtb_id, ip, ipv4_wan, ipv6_lan, statis_id) {},
    // end
    async KichHoat(current_hdtb_id, vport_xin_id, pass, zma_tb, p_, vloaitbid, vtocdo_id, ten_tb, dr, cboBrasADSL, cboDSLAM, iptinh, matb_tn) {},
    async KichHoat_HD_DatMoi_Visa(current_hdtb_id, zma_tb, pass, p, ten_tb, dr, cboBras, cboDSLAM, vloaitbid) {
      let input = {}
    },
    startTimer: function() {
      this.timer = setInterval(() => this.CheckLoading(), 100)
    },
    stopTimer: function() {
      clearInterval(this.timer)
      this.timer = null
    },
    CheckLoading() {
      // console.log('CheckLoading',this.notFinished)
      if (this.notFinished) {
        this.loading(true)
      } else {
        this.loading(false)
        this.stopTimer()
      }
    },
    async InBienNhan(magd) {
      const baseUrl = process.env.API
      await this.axios({
        url: `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/in-bien-nhan-thanh-toan`,
        method: 'POST',
        responseType: 'blob',
        data: {
          maGD: magd
        }
      })
        .then((response) => {
          previewPrint(response.data)
        })
        .catch(async (error) => {
          let errorString = JSON.parse(await error.response.data.text())
          this.$toast.error(errorString.message_detail)
        })
    },
    async selectprintType(type) {
      if (type == 2) {
        await this.InPhieuThu()
      }
    },
    async selectprintPhieuChi(type) {
      console.log('🚀 ~ file: Payment.vue:5738 ~ selectprintPhieuChi ~ type:', type)
      // let chukyno= '01072023' //await this.lay_chuky_nohoadon();
      let chukyno = '0' // await this.lay_chuky_nohoadon();
      let nhanvienId = this.$root.token.getNhanVienID()
      this.dsGiaTri = []
      this.dsGiaTri.push(this.PhieuTtId)
      this.dsGiaTri.push(chukyno)
      this.dsGiaTri.push(nhanvienId)
      this.dsGiaTri.push(type)
      this.$refs.refXemBaoCao.XemNgay()
    },
    async GachNoVinaPhone(ma_gd) {
      let self = this
      self.flag = false
      console.log('Call GachNoVinaPhone ~ ma_gd:', ma_gd, self.loaihd_id, self.LoaiHopDong.DAT_MOI, 'self.loaihd_id', self.loaihd_id)
      let passGachNo = false
     
      if (self.loaihd_id == self.LoaiHopDong.DAT_MOI) {
        const data = self.LayDanhSachTBTT_DiDong()
        console.log('LayDanhSachTBTT_DiDong:', data)
        if (data.arrString != '') {
        
          console.log("passGachNo", passGachNo)
          console.log(self.OPEN_SYNC_VINA);
   
          if (self.OPEN_SYNC_VINA || passGachNo) {
            console.log('LoaiHopDong.DAT_MOI: GachNo')
            //
            for (const item of self.DsThueBao.data) {
              console.log('GachNoVinaPhone-item', item)
              if (parseInt(item.dichvuvt_id) == self.DichVuVienThong.DI_DONG && parseInt(item.loaitb_id) == self.LoaiHinhTB.DIDONGTRASAU && item.status == '1') {
                let ma_tb = item['ma_tb']
                let so_phieu = await self.TraCuu_ThanhToan_TBLD_VinaPhone(ma_gd, ma_tb)
                console.log('self.TraCuu_ThanhToan_TBLD_VinaPhone ~ so_phieu:', so_phieu)
                if (so_phieu != '') {
                  let delRs = await self.Delete_ThanhToan_TBLD_VinaPhone(so_phieu, ma_gd)
                  if (delRs != 'OK' || delRs != 'ok') {
                    self.ShowError('Có lỗi khi xóa phiếu thanh toán của thuê bao ' + ma_tb + '.Chi tiết lỗi: ' + delRs)
                    return false
                  }
                }
              }
            }
            //
            console.log('self.checkMnp~ result:begin:')
            let result = await self.checkMnp(self.txtMaGD)
            console.log('self.checkMnp~ result:', 'self.txtMaGD', self.txtMaGD, 'result', result)
           
            if (result != null && result.length > 0 && result[0]['chuyenmang'] == '1') {
              self.phieutt_id_neo = '1'
            } else {
              let resultTT = await self.GachNo_ThanhToan_HDLD_VinaPhone(ma_gd)
              if (self.CheckIsNumber(resultTT.result)) {
                for (const item of self.dtHDTB) {
                  await this.capnhat_status_hdtb(item.hdtb_id, 2)
                }
                self.phieutt_id_neo = resultTT.result
                self.OPEN_SYNC_VINA2 = true
                self.flag = true
                return true
              } else {
                console.log('Có lỗi xảy ra trong quá trình thanh toán hợp đồng có mã giao dịch:  ' + ma_gd + ' trên VinaPhone !. Lỗi: ' + resultTT)
               
                self.ShowError('Có lỗi xảy ra trong quá trình thanh toán hợp đồng có mã giao dịch:  ' + ma_gd + ' trên VinaPhone !. Lỗi: ' + resultTT)
                return false
              }
            }
          }
        }
    
      } else if (self.loaihd_id == self.LoaiHopDong.CHUYEN_QUYEN || self.loaihd_id == self.LoaiHopDong.TACHNHAP_TB) {
        const data = self.LayDanhSachTBTT_DiDong()
        console.log('self.LayDanhSachTBTT_DiDong~ data:self.loaihd_id == self.LoaiHopDong.CHUYEN_QUYEN,TACHNHAP_TB', data)
        console.log('LayDanhSachTBTT_DiDong:', data, 'data.arrString:', data.arrString)
        if (data.arrString != '') {
          if (self.OPEN_SYNC_VINA || passGachNo) {
            if (data.arr_ds_matb != null && data.arr_ds_matb.length > 0) {
              self.ShowError('Hợp đồng có thuê bao: ' + data.arr_ds_matb.join(',') + ' chưa được đồng bộ lên Vinaphone!. Bạn không thể tiếp tục thanh toán !')
              return false
            }
            for (const item of self.DsThueBao.data) {
              if (parseInt(item.dichvuvt_id) == self.DichVuVienThong.DI_DONG && parseInt(item.loaitb_id) == self.LoaiHinhTB.DIDONGTRASAU && item.status == '1') {
                let ma_tb = item['ma_tb']
                let so_phieu = await self.TraCuu_ThanhToan_HDCQ_VinaPhone(ma_gd, ma_tb)
                console.log('TraCuu_ThanhToan_HDCQ_VinaPhone ~ so_phieu:', so_phieu)
                if (so_phieu != null && so_phieu != '') {
                  let delRs = await self.Delete_ThanhToan_HDCQ_VinaPhone(so_phieu, ma_gd)
                  if (delRs != 'OK' || delRs != 'ok') {
                    self.ShowError('Có lỗi khi xóa phiếu thanh toán của thuê bao ' + ma_tb + '.Chi tiết lỗi: ' + delRs)
                    return false
                  }
                }
              }
            }
            let _dstb = this.LayDanhSachSomay_DiDong()
            self.phieutt_id_neo = await self.Laphd_chuyenquyen_sudung_thanhtoan(ma_gd, _dstb)
            if (self.CheckIsNumber(self.phieutt_id_neo)) {
              //Nếu thành công
              // console.log('Update trang thai trong db')
              for (const item of self.dtHDTB) {
                await this.capnhat_status_hdtb(item.hdtb_id, 2)
              }
              self.OPEN_SYNC_VINA2 = true
              self.flag = true
              return true
            } else {
              self.ShowError(self.phieutt_id_neo)
              return false
            }
          }
        }
      }
    },
    check_ccbs() {
      let ma_nhanvien_ccbs = this.$root.token.getMaCCBS()
      if (ma_nhanvien_ccbs == null || ma_nhanvien_ccbs == '') {
        this.ShowError(`Thông tin người dùng chưa đồng bộ với dữ liệu VinaPhone. Bạn không thể tiếp tục cập nhật dữ liệu.`)
        return false
      }
      return true
    },
    async TraCuu_ThanhToan_TBLD_VinaPhone(ma_gd, ma_tb) {
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          //('@USERID@','@MA_TINH@','CCS_@MA_TINH@.','$ma_hd')
          let response = await api.ts_thuphi_hoamangmoi_layds_tb_ld(this.axios, { MA_TINH: tentat_ccbs, USERID: user_neo, ma_hd: ma_gd })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              let dsTB = response.data.data
              console.log('ts_thuphi_hoamangmoi_layds_tb_ld:dsTB:', dsTB)
              if (dsTB == null || dsTB.length == 0) {
                return ''
              }
              dsTB.forEach(function(item) {
                if (item.MA_TB == '84' + ma_tb && item.TRANGTHAI == '2') {
                  return item.PHIEU_ID
                }
                // if (item['MA_HD'] == ma_gd && item['NGAY_TT'] != null) {
                //   return item['PHIEU_ID']
                // }
              })
            }
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5018 ~ TraCuu_ThanhToan_TBLD_VinaPhone ~ err:', err)
      }
      return ''
    },
    async Delete_ThanhToan_TBLD_VinaPhone(so_phieu, ma_gd) {
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          let response = await api.ts_thuphi_hoamangmoi_thuphi_xoaphieu(this.axios, {
            ma_tinh: tentat_ccbs,
            phieu_id: so_phieu,
            ma_hd: ma_gd,
            userid: user_neo,
            kieu_huy: 0
          })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              if (response.data.data == ma_gd) return 'OK'
            }
            return response.data.data
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5045 ~ Delete_ThanhToan_TBLD_VinaPhone ~ err:', err)
      }
      return ''
    },
    CheckIsNumber(input) {
      try {
        if (isNaN(input)) {
          return false
        } else {
          return true
        }
      } catch (err) {
        return false
      }
      return false
    },
    async capnhat_phieu_id_neo(vphieu_id_neo, vphieu_id) {
      try {
        let input = { vphieu_id_neo: vphieu_id_neo, vphieu_id: vphieu_id }
        let data = this.GetData(await api.capnhat_phieu_id_neo(this.axios, input))
        return data
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5709 ~ capnhat_phieu_id_neo ~ err:', err)
        return err
      }
    },
    async GachNo_ThanhToan_HDLD_VinaPhone(ma_gd) {
      var self = this
      try {
        if (self.check_ccbs()) {
          const tt_nd = self.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          // ('@USERID@','@MA_TINH@','CCS_@MA_TINH@.','$ma_hd')
          let response = await api.ts_thuphi_hoamangmoi_layds_tb_ld(this.axios, {
            MA_TINH: tentat_ccbs,
            ma_hd: ma_gd,
            userid: user_neo
          })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              let dsTB = response.data.data
              let dsThuebaoID = []
              console.log('dsTB:CCBS', dsTB,'self.DsThueBao.data:',self.DsThueBao.data)
              if (dsTB != null && dsTB.length > 0) {
                let i = 0
                for (const item of dsTB) {
                  try {
                    console.log('🚀 ~ file: Payment.vue:5991 ~ GachNo_ThanhToan_HDLD_VinaPhone ~ item:', item)
                    let a = self.DsThueBao.data.filter((x) => x.dichvuvt_id == self.DichVuVienThong.DI_DONG && x.loaitb_id == self.LoaiHinhTB.DIDONGTRASAU && x.status == '1'
                    && (('84'+x.ma_tb)==item.MA_TB) )
                    console.log('🚀 ~ file: Payment.vue:5993 ~ GachNo_ThanhToan_HDLD_VinaPhone ~ a:', a)
                    if (a != null && a.length > 0) {
                      console.log('🚀 ~ file: Payment.vue:5993 ~ GachNo_ThanhToan_HDLD_VinaPhone ~ a1:', a)
                      //var ma_tb = '84' + self.dtHDTB[i]['ma_tb']
                      dsThuebaoID.push(item['THUEBAO_ID'])
                      //if (item['MA_TB'] == ma_tb) dsThuebaoID.push(item['THUEBAO_ID'])
                    }
                    else{
                      console.log('Khong tim duoc TB tren OneBss:', item,'One:',self.DsThueBao.data)
                    }
                  } catch (err) {
                    console.log('🚀 ~ file: Payment.vue:6001 ~ GachNo_ThanhToan_HDLD_VinaPhone ~ err:', err)
                  }
                  i = i + 1
                }
              }
              console.log('dsThuebaoID', dsThuebaoID)
              if (dsThuebaoID.length == 0) {
                return 'Thuê bao không tồn tại trên CCBS'
              }
              let ngay_tt = ''
              if (self.dsPhieuTTHD[0].ngay_tt != null) {
                ngay_tt = self.txtNgayTT
              } else {
                ngay_tt = self.txtNgayTT
              }
              let chungtu = ''
              try {
                if (self.dsPhieuTTHD[0]['SO_CT'] != null) {
                  chungtu = self.dsPhieuTTHD[0]['SO_CT']
                }
              } catch (err) {
                chungtu = ''
              }
              let input = {
                userid: user_neo,
                ma_tinh: tentat_ccbs,
                ma_hd: ma_gd,
                so_tb: dsThuebaoID.join(','),
                kieu_gach_no: 1,
                ngay_tt: ngay_tt,
                ngay_ngan_hang: ngay_tt,
                hinhthuc_tt: 1,
                ngan_hang_id: -1,
                so_sec: '',
                chungtu: chungtu,
                hopdong: '',
                nguoinhan_hni: ''
              }
              const responseTT = await api.ts_thuphi_hoamangmoi_thuphi_thanhtoan(this.axios, input)
              if (responseTT.data.error == 200 || responseTT.data.error == '200') {
                return responseTT.data.data
              } else {
                this.ShowError('Có lỗi: ' + responseTT.data.data)
                return responseTT.data.data
              }
            }
            return response.data.error_code + '|' + response.data.message
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:6051 ~ GachNo_ThanhToan_HDLD_VinaPhone ~ err:', err)
      }
      return ''
    },
    LayDanhSachSomay_DiDong() {
      let dichvuvt_id = this.DichVuVienThong.DI_DONG
      let loaitb_id = this.LoaiHinhTB.DIDONGTRASAU
      let ds = this.DsThueBao.data.filter((x) => x.dichvuvt_id == dichvuvt_id && x.loaitb_id == loaitb_id && x.status == '1')
      if (ds != null && ds.length > 0) {
        return ds.map((x) => '84' + x.ma_tb).join(',')
      }
      return ''
    },
    async listInvByCus(ma_gd) {
      let _exits = false
      try {
        let input = {
          cusCode: ma_gd,
          fromDate: '',
          toDate: ''
        }
        if (this.params.vtt_hddt_hd_user == 1) {
          let data = this.GetData(await api.listInvByCus_VTT(this.axios, input))
          if (data != null) {
            if (data.indexOf('fkey') > 0) {
              _exits = true
            }
          }
        } else {
          let data = this.GetData(await api.listInvByCus(this.axios, input))
          if (data != null) {
            if (data.indexOf('fkey') > 0) {
              _exits = true
            }
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5844 ~ listInvByCus ~ err:', err)
      }
      return _exits
    },
    async TraCuu_ThanhToan_HDCQ_VinaPhone(ma_gd, ma_tb) {
      console.log('TraCuu_ThanhToan_HDCQ_VinaPhone ~ ma_gd, ma_tb:', ma_gd, ma_tb)
      let phieu_id = ''
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          let response = await api.ts_laphd_chuyenquyen_sudung_cacphieutra(this.axios, {
            ma_tinh: tentat_ccbs,
            MA_TINH: tentat_ccbs,
            USERID: user_neo
          })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              let dsTB = response.data.data
              console.log('ts_laphd_chuyenquyen_sudung_cacphieutra:dsTB:', dsTB)
              if (dsTB == null || dsTB.length == 0) {
                return ''
              }
              dsTB.forEach(function(item) {
                if (item.MA_HD == ma_gd && item.NGAY_TT != null && item.NGAY_TT != '') {
                  console.log('item.MA_HD == ma_gd', item.MA_HD, ma_gd)
                  phieu_id = item.PHIEU_ID
                  return item.PHIEU_ID
                }
              })
              return phieu_id
            }
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5773 ~ TraCuu_ThanhToan_HDCQ_VinaPhone ~ err:', err)
        return phieu_id
      }
    },
    async Delete_ThanhToan_HDCQ_VinaPhone(PIPHIEU_ID, PSMAHD) {
      try {
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          // '@USERID@','@MA_TINH@','CCS_@MA_TINH@.','$ma_hd','$phieu_id','frmThanhToanHDLD','$ip','$kieu_xoa'
          let response = await api.ts_laphd_chuyenquyen_sudung_xoaphieu(this.axios, {
            USERID: user_neo,
            MA_TINH: tentat_ccbs,
            phieu_id: PIPHIEU_ID,
            ma_hd: PSMAHD,
            userid: user_neo,
            kieu_xoa: 0
          })
          if (response.data.error == 200 || response.data.error == '200') {
            if (response.data.error_code == 'BSS-00000000') {
              if (response.data.data == ma_gd) return 'OK'
            }
            return 'Có lỗi khi xóa phiếu thanh toán của thuê bao ' + PSMAHD + '. Chi tiết lỗi: ' + response.data.data
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5045 ~ Delete_ThanhToan_TBLD_VinaPhone ~ err:', err)
      }
      return ''
    },
    async Laphd_chuyenquyen_sudung_thanhtoan(PSMA_HD, PSSOMAY) {
      try {
        var self = this
        if (this.check_ccbs()) {
          const tt_nd = this.tt_nd
          let tentat_ccbs = tt_nd.tentat_ccbs
          let user_neo = tt_nd.user_neo
          let ngay_tt = ''
          if (self.dsPhieuTTHD[0].ngay_tt != null) {
            ngay_tt = this.txtNgayTT
          } else {
            ngay_tt = this.txtNgayTT
          }
          let chungtu = ''
          try {
            if (self.dsPhieuTTHD[0]['SO_CT'] != null) {
              chungtu = self.dsPhieuTTHD[0]['SO_CT']
            }
          } catch (err) {
            chungtu = ''
          }
          //('@USERID@','@MA_TINH@','CCS_@MA_TINH@.','$ma_hd','$so_tb','@IP@','$kieu_gach_no',to_date('$ngay_tt','dd/mm/yyyy'),to_date('$ngay_ngan_hang','dd/mm/yyyy'),'$httt_id','1','','$so_sec','$chungtu','','$nguoi_nhan_hni') }
          let input = {
            USERID: user_neo,
            MA_TINH: tentat_ccbs,
            ngay_tt: ngay_tt,
            ma_tinh: tentat_ccbs,
            ngay_ngan_hang: this.txtNgayNH,
            so_sec: '1',
            so_tb: PSSOMAY,
            kieu_gach_no: 1,
            nguoi_nhan_hni: '',
            ma_hd: PSMA_HD,
            userid: user_neo,
            httt_id: 1,
            chungtu: chungtu
          }
          const responseTT = await api.ts_laphd_chuyenquyen_sudung_thanhtoan(this.axios, input)
          if (responseTT.data.error == 200 || responseTT.data.error == '200') {
            return responseTT.data.data.result
          }
        }
      } catch (err) {
        console.log('🚀 ~ file: Payment.vue:5712 ~ Laphd_chuyenquyen_sudung_thanhtoan ~ err:', err)
        return 'Có lỗi xảy ra trong quá trình thanh toán hợp đồng có mã giao dịch:  ' + PSMA_HD + ' trên VinaPhone !. Lỗi: ' + err.Message
      }
    },
    async tsbtnUpDSFile_Click() {
      this.$router.push('/search/UploadListProfileFiles?vma_gd=' + this.txtMaGD)
    },
    ChuyenPbh_Changed(item) {
      console.log('🚀 ~ file: Payment.vue:6003 ~ ChuyenPbh_Changed ~ item:', item)
      if (item != null && item.DONVI_ID != null) {
        this.ChuyenPBH.DonViId = item.DONVI_ID
      }
    },
    async hinhThucTTIdChanged(val) {   
      try {
        this.loading(true)
        if (this.KieuId == 1 || this.KieuId == 3) {
          if (val == this.HinhThucTra.UY_NHIEM_CHI || val == this.HinhThucTra.UY_NHIEM_THU) {
            if (parseInt(this.trangThaiThanhToanId) == 0) {
              this.chkNganHang = true
              this.txtChungTuStatus = false
            } else {
              this.txtChungTuStatus = true
              this.chkNganHang = true
            }
          } else {
            this.txtChungTuStatus = false
            this.chkNganHang = false
          }
        }
        if (this.KieuId == 2) {
          if (this.hinhThucTTId == this.HinhThucTra.UY_NHIEM_CHI || this.hinhThucTTId == this.HinhThucTra.UY_NHIEM_THU) {
            if (parseInt(this.trangThaiThanhToanId) == 0) {
              this.chkNganHang = true
              this.txtChungTuStatus = true
            } else {
              this.txtChungTuStatus = false
              this.chkNganHang = true
            }
          } else {
            this.txtChungTuStatus = false
            this.chkNganHang = true
          }
        }
        // if (process.env.NODE_ENV == 'testing') {
          var hdtgdd = await this.getDsHopDongTGDD(this.HdKhId)
          if (hdtgdd && hdtgdd.length > 0 && this.HdKhId) {
            console.log(`Gán lại kênh thu hợp đồng TGDD`)
            await this.getDmKenhThuTGDD()
          } else {
            await this.getDmKenhThu()
            console.log(`Không phải hợp đồng TGDD`)
          }
          this.trangThaiThanhToanId = 0
          this.kenhThuId = null
          let is_hd700 = await this.kiemTraKenhTiepNhan(this.HdKhId)
          if (this.KieuId == 1 && is_hd700) {
            this.kenhThuId = this.KENHTHU.NOP_TIEN_SAU
          }
        // }
      } catch (err) {
        console.log('🚀 ~ watch:hinhThucTTId ~ err', err)
      } finally {
        this.loading(false)
      }
    },
    async kenhThuIdChanged(val) {
      let phanvungId = this.$root.token.getPhanVungID()
      console.log(val)
      //this.hinhThucTTId == 2 && this.kenhThuId == 29
      //update = cho HCM  theo comment sếp Hiếu
      if (this.state_tag == 1 && phanvungId == 28) {
        //mở trường nguoi_bl khi chọn kênh thu chuyển khoản cho HCM
        // nếu Kênh thu id = 5 (thu chuyển khoản) + phân vùng id =  28 (HCM) thì combo trạng thái = chưa thu tiền
        if (this.hinhThucTTId == 2 && val == 29) {
          this.btnNguoiBLStatus = true
          this.txtNguoiBLStatus = true
          this.trangThaiThanhToanId = 0
          this.HienThiDoiTuong()
          return
        }
      }
      try {
        if (this.hinhThucTTId == 2 && [this.KENHTHU.BAO_LANH, this.KENHTHU.NOP_TIEN_SAU].filter((x) => x == val).length > 0) {
          this.txtNguoiBLStatus = true
          this.btnNguoiBLStatus = true 
        } else {
          this.txtNguoiBLStatus = false
          this.btnNguoiBLStatus = false
        }
        try {
          if (this.KieuId == 1) {
            let is_hd700 = await this.kiemTraKenhTiepNhan(this.HdKhId)
            if (is_hd700 == false) {
              let kt = this.DmKenhThu.filter((x) => x.id == this.kenhThuId)
              if (kt.length > 0) {
                this.trangThaiThanhToanId = Number(kt[0]?.trangthai || 0)
              }
            } else {
              this.trangThaiThanhToanId = 0
            }
          } else {
            if (!this.HdKhId || this.kenhThuId == this.KENHTHU.NOP_TIEN_SAU) {
              this.Fn.GachNo = false
            } else {
              this.Fn.GachNo = true
            }
            
            let kt = this.DmKenhThu.filter((x) => x.id == this.kenhThuId)
            if (kt.length > 0) {
              this.trangThaiThanhToanId = Number(kt[0]?.trangthai || 0)
            }
          }
          console.log('kenhThuIdChanged > KieuId, is_hd700, kenhThuId, trangThaiThanhToanId, DmKenhThu', this.KieuId, is_hd700, this.kenhThuId, this.trangThaiThanhToanId, this.DmKenhThu)
        } catch (ex) {}
        if ([this.KENHTHU.TAI_GIAO_DICH, this.KENHTHU.BUU_CUC, this.KENHTHU.KHONG_THU_TIEN].filter((x) => x == val).length > 0 || this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN) {
          if (val == this.KENHTHU.KHONG_THU_TIEN || this.hinhThucTTId == this.HinhThucTra.KHONG_THU_TIEN) {
            this.chkLoaiHD = false
            this.chkHoaDonDienTu = false            
          } else {
            this.chkLoaiHD = true
          }
        } else {
          if (this.chkHoaDonDienTu) {
            this.chkLoaiHD = true
          } else {
            this.chkLoaiHD = false
          }
        }
        if (val == this.KENHTHU.BAO_LANH && (this.LoaiHDId == this.LoaiHopDong.DATCOC_MOI || this.LoaiHDId == this.LoaiHopDong.THAY_DOI_TOCDO_ADSL)) {
          // this.btnNguoiBLStatus = true ( bỏ theo recomment tester)
          if (this.HdTbId > 0) {
            await this.getDsNguoiBaoLanh(this.HdTbId)
            if (this.DsNguoiBaoLanh.data.length > 0) this.txtNguoiBL = this.DsNguoiBaoLanh.data[0].daily
          }
        } else {
          // this.btnNguoiBLStatus = false
        }
        this.HienThiDoiTuong()
      } catch (err) {
        console.log('🚀kenhThuId: ~ err', err)
      }
    },
    async getHopDongTBHDDTTT78V2({  vhdkh_id,  vkieu,  vgopnhom }){
      const result = await api.lay_hopdong_tb_hddt_tt78_v2(this.axios, {  vhdkh_id,  vkieu,  vgopnhom })
      return this.GetData(result)
    },


    disabledRangePassDate(date){
      const currentDate = new Date(moment().subtract(1, 'days'));
        const thirtyDaysFromNow = new Date(moment().add(30, 'days'));
        return date <= currentDate || date > thirtyDaysFromNow;
    },
    showPopupThongTinNgoaiTe(){
      this.$refs.refPopupThongTinNgoaiTe.hdkhId = this.HdKhId
      console.log('🚀 ~ file: Payment.vue:6003 ~ showPopupThongTinNgoaiTe ~ this.hdkhId', this.HdKhId)
      this.$refs.refPopupThongTinNgoaiTe.show()
    },
  }
}
</script>