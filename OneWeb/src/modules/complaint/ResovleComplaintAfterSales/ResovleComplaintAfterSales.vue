<template src="./ResovleComplaintAfterSales.html"></template>
<style scoped src="./ResovleComplaintAfterSales.scss"></style>
<script>
import KhieuNaiApi from './API/KhieuNaiApi'
// import gridView from './components/gridview.vue'
// import breadcrumb from '@/components/breadcrumb'
//  popupLayDuLieuBC from '@/modules/contract/complete_profile/popups/popupLayDuLieuBC/LayDuLieuBC.vue'
// import monthSelectPlugin from 'flatpickr/dist/plugins/monthSelect/index.js'
// import 'flatpickr/dist/plugins/monthSelect/style.css'
// import bssFlatPicker from '@/components/BssDatePicker'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import XLSX from 'xlsx'
// import XemBaoCao from '@/modules/print/XemBaoCao/XemBaoCao.vue'
import XemBaoCao from '@/modules/complaint/ResovleComplaintAfterSales/components/XemBaoCao/XemBaoCao.vue'
// import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
// import frmHoSoKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'

export default {
  components: {
    // breadcrumb,
    // gridView,
    moment,
    DatePicker,
    // popupLayDuLieuBC,
    XLSX,
    XemBaoCao, // () => import('@/modules/print/XemBaoCao/XemBaoCao.vue'),
    ExportDataModal: () => import('@/modules/contract/profile/ExportData/ExportDataModal.vue'),
    frmHoSoKhieuNai: () => import('@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'),
    KDataGrid,
    frmTraPhieuKN_SLL: () => import('./popups/frmTraPhieuKN_SLL.vue'),
  },
  prop: ['p_tag'],
  name: 'ResovleComplaintAfterSales',
  created() {
    this.loaded = false
    this.notFinished = true
    this.startTimer()
    this.PhanVungId = this.$root.token.getPhanVungID()
    if (this.$route.name.toLowerCase().trim() == 'ResovleComplaintAfterSales'.toLowerCase()) {
      // [UR5.1.007] - Tổ SBH - Xử lý khiếu nại
      this.Tag = '2+2257'
      // this.labelFunctionTitle = 'Tổ SBH - Xử lý khiếu nại'
    } else if (this.$route.name.toLowerCase().trim() == 'SolveComplain_NVC'.toLowerCase()) {
      // [UR5.1.008] -Phòng nghiệp vụ - Xử lý khiếu nại
      this.Tag = '2+2260'
      // this.labelFunctionTitle = 'Phòng nghiệp vụ - Xử lý khiếu nại'
    } else if (this.$route.name.toLowerCase().trim() == 'SolveComplain_TNH'.toLowerCase()) {
      // '[UR5.1.009] -Tổ thu ngân hàng - Xử lý khiếu nại'
      this.Tag = '2+2264'
      // this.labelFunctionTitle = 'Tổ thu ngân hàng - Xử lý khiếu nại'
    } else if (this.$route.name.toLowerCase().trim() == 'SolveComplain_KD'.toLowerCase()) {
      // [UR5.1.010] -Tổ Kinh Doanh - Xử lý khiếu nại
      this.Tag = '2+2268'
      // this.labelFunctionTitle = 'Tổ Kinh Doanh - Xử lý khiếu nại'
    }
    this.labelFunctionTitleMenu = this.labelFunctionTitle
    if (this.p_tag != null && this.p_tag != '') {
      this.Tag = this.p_tag
    }
    // Lay gia trị tag truyen tu url
    if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
      this.Tag = this.$route.query.tag
    }
    // Gan vao theo tham so cua nghiep vu cua form
    if (this.Tag != null && this.Tag.length > 0) {
      let arr = this.Tag.split('+')
      if (arr.length >= 1) {
        this.dsttkn_id = arr[0].replace(';', ',')
        this.TtKhieuNaiId = this.dsttkn_id
      }
      if (arr.length >= 2) {
        this.dshuonggiao_id = arr[1]
        this.dshuonggiao_id = this.dshuonggiao_id.replace(';', ',')
        this.HuongGiaoId = this.dshuonggiao_id
      }
    } else {
      this.dsttkn_id = '1'
      this.dshuonggiao_id = '1'
    }
    this.tabIndex = 0
    this.PhanVungId = this.$root.token.getPhanVungID()
    this.DsPhieuKhoa.From = moment().format('MM/YYYY')
    this.DsPhieuKhoa.To = moment().format('MM/YYYY')
    let nguoiDungID = this.$root.token.getNguoiDungID()
    this.loading(true)
    Promise.all([this.laythangno(), this.GetDmQuyTrinhKhieuNai(), this.GetDmKetQuaXuLy(), this.GetDmKetQuaLoi(), this.GetDmNhanVienXuLy(), this.KiemTraQuyenNguoiDung(nguoiDungID, this.TOTRUONG_XLKN)])
      .then((responses) => {
        // console.log(responses)
        // this.loading(true)
      })
      .catch((err) => {
        console.log('created-promise-err-', err)
      })
      .finally(() => {
        this.loading(false)
        this.notFinished = false
      })
  },
  mounted() {
    this.notFinished = false
    this.stopTimer()
  },
  computed: {
    getContextMenuItems() {
      if (this.DanhSachPhieus.datalist.length > 0) {
        return this.contextMenuItems
      }
      return null
    },
    getContextMenuItems_choxl() {
      if (this.DsPhieuChoXuLy.datalist.length > 0) {
        return this.contextMenuItems_choxl
      }
      return null
    }
  },
  data() {
    return {
      header: {},
      // header: {
      //   title: 'GIAO PHIẾU XỬ LÝ KHIẾU NẠI (ĐẠI LÝ)',
      //   list: [
      //     { name: 'Xử lý khiếu nại', link: { name: 'Ui.cards' } },
      //     {
      //       name: 'Lắp đặt mới',
      //       link: { name: 'Ui.buttons' }
      //     }
      //   ]
      // },
      monthConfig: {
        plugins: [
          // eslint-disable-next-line new-cap
          // new monthSelectPlugin({
          //   altFormat: 'm/Y',
          //   shorthand: true,
          //   dateFormat: 'Ym'
          // })
        ]
      },
      labelFunctionTitle: 'ĐẠI LÝ - XỬ LÝ KHIẾU NẠI',
      labelFunctionTitleMenu: 'ĐẠI LÝ - XỬ LÝ KHIẾU NẠI',
      quytrinh_id: 0,
      luong_id: 0,
      dsttkn_id: '',
      dshuonggiao_id: '',
      khieunai_id: 0,
      phieukn_id: 0,
      thuebao_id: 0,
      donvikn_id: 0,
      kt_totruong: false,
      thoigian_xlkn: 0,
      THEM_KHDB_KHI_GIAOPHIEU_KN: -1,
      width100: '100%',
      dateFormat: 'DD/MM/YYYY',
      dateFormatFull: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatDateTime: 'datetime',
      typeFormatMonth: 'month',
      currentIndex: 0,
      currentSaveIndex: 0,
      currentSavePageIndex: 0,
      currentSave: false,
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      Loading: false,
      tabIndex: 0,
      selectedRows: [],
      selIndex: [],
      selectedPhieuChoXuLyRows: [],
      selectedPhieuKhoaRows: [],
      selectedPhieuBaoTonRows: [],
      QuyTrinhKnId: 0,
      ChuDeKnId: 0,
      // Danh sach phieu duoc chon
      TrangThaiPhieu: {
        ChuaXuly: 1,
        GiaoXuLy: 2,
        KhoaPhieu: 3,
        BaoTon: 4
      },
      TtPhieuId: 0,
      TtKhieuNaiId: 0,
      HuongGiaoId: '2257',
      PhanVungId: 26,
      QuyTrinhId: 1,
      PhieuIds: [],
      MonthFormat: {
        wrap: true, // set wrap to true only when using 'input-group'
        altFormat: 'm/Y',
        altInput: true,
        dateFormat: 'Y-m'
      },
      // Danh muc
      DmChuDe: [],
      DmQuyTrinhKN: [],
      DmKetQuaXuLy: [],
      DmKetQuaLoi: [],
      DmNhanVienXuLy: [],
      ChuDenKhieuNai: {
        KHONG_NHAN_DUOC_GBC: 25 // Khong de lại giay bao cuoc
      },
      DanhSachChuDe: {
        headers: [
          { fieldName: 'stt', headerText: 'STT', width: '70px' },
          { fieldName: 'tenchude', headerText: 'Chủ đề', width: '120px' },
          { fieldName: 'sl_chua_xl', headerText: 'Chưa XL', width: '90px' },
          { fieldName: 'sl_cho_xl', headerText: 'Chờ XL', width: '90px' }
        ],
        data: [],
        selectedItem: {},
        selectedItems: []
      },
      animationSettings: { effect: 'Zoom' },
      selectionOptions: {
        type: 'Multiple',
        checkBoxOnly: true
      },
      position: { X: 'center', Y: 'top' },
      dgvChiTiet: {
        headers: [
          { fieldName: 'huonggiao', headerText: '', isGroupedColumn: true, captionTemplate: '${key}' },
          { fieldName: 'dvg', headerText: 'Đơn vị giao' },
          { fieldName: 'nv_giao', headerText: 'Nhân viên giao' },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao' },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao' },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận' },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả' },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu' },
          { fieldName: 'nv_th', headerText: 'Nhân viên TH' },
          { fieldName: 'ngay_th', headerText: 'Ngày TH' },
          { fieldName: 'ghichu_th', headerText: 'Nội dung TH' },
          { fieldName: 'ghichu_th', headerText: 'Ghi chú TH' },
          { fieldName: 'sodt_nvgiao', headerText: 'ĐTNV giao' },
          { fieldName: 'sodt_nvth', headerText: 'ĐTNV thực hiện' },
          { fieldName: 'ten_nguoi_cn', headerText: 'Người CN' },
          { fieldName: 'ngay_cn_phieu', headerText: 'Ngày CN' }
        ],
        datalist: []
      },
      // Data
      DanhSachPhieus: {
        headers: [
          { fieldName: 'huonggiao', headerText: '', isGroupedColumn: true, captionTemplate: '${key}' },
          { fieldName: 'dvg', headerText: 'Đơn vị giao' },
          { fieldName: 'nv_giao', headerText: 'Nhân viên giao' },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao' },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao' },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận' },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả' },
          { fieldName: 'nd_tra', headerText: 'Nội dung trả' },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu' },
          { fieldName: 'nv_th', headerText: 'Nhân viên TH' },
          { fieldName: 'ngay_th', headerText: 'Ngày TH' },
          { fieldName: 'ghichu_th', headerText: 'Nội dung TH' },
          { fieldName: 'ghichu_th', headerText: 'Ghi chú TH' },
          { fieldName: 'sodt_nvgiao', headerText: 'ĐTNV giao' },
          { fieldName: 'sodt_nvth', headerText: 'ĐTNV thực hiện' },
          { fieldName: 'ten_nguoi_cn', headerText: 'Người CN' },
          { fieldName: 'ngay_cn_phieu', headerText: 'Ngày CN' }
        ],
        headers2: [
          // {
          //     fieldName:'ischeck',
          //     headerText:'',
          //     allowFiltering:false,
          //     width:120,
          //     textAlign: "center",
          //     template: this.columnTemplateCheckBox(this),
          //     headerTemplate:this.columnTemplateCheckBoxHeader(this)
          // },
          { fieldName: 'ngay_tn', headerText: 'Ngày KN' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'loai', headerText: 'Loại' },
          { fieldName: 'tongno', headerText: 'Tổng nợ', type: 'Number', format: 'N0' },
          { fieldName: 'no_ps', headerText: 'Nợ phát sinh', type: 'Number', format: 'N0' },
          { fieldName: 'no_dk', headerText: 'Nợ đầu kỳ', type: 'Number', format: 'N0' },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái TB' },
          { fieldName: 'lydo_tra', headerText: 'Lý do' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo' },
          { fieldName: 'nv_giao', headerText: 'Người YC' },
          { fieldName: 'donvi_tn', headerText: 'Đơn vị YC' },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'diachi_bc', headerText: 'Đia chỉ báo cước' },
          { fieldName: 'ten_dl', headerText: 'Đại lý' },
          { fieldName: 'nvtc', headerText: 'Mã NVTC' },
          { fieldName: 'tentuyen', headerText: 'Tuyến đường' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
          { fieldName: 'nguoi_kn', headerText: 'Người KN' },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại LH' },
          { fieldName: 'ky_tt', headerText: 'Kỳ TT' },
          { fieldName: 'tientra', headerText: 'Tiền trả' },
          { fieldName: 'hentra', headerText: 'Hẹn trả' },
          { fieldName: 'goicuoc', headerText: 'Gói cước' },
          { fieldName: 'ma_plkh', headerText: 'Mã PLKH' },
          { fieldName: 'dienthoai', headerText: 'SĐT Nhắc nợ' },
          { fieldName: 'ghichu_tra', headerText: 'Ghi chú' },
          { fieldName: 'nguoi_xl', headerText: 'Người XL' }
        ],
        datalist: [],
        selectedItems: []
      },
      DsPhieuChoXuLy: {
        headers: [
          // {
          //   fieldName:'ischeck',
          //   headerText:'',
          //   allowFiltering:false,
          //   width:120,
          //   textAlign: "center",
          //   template: this.columnTemplateCheckBox2(this),
          //   headerTemplate:this.columnTemplateCheckBoxHeader2(this)
          // },         
          { fieldName: 'ngay_tn', headerText: 'Ngày KN' },
          { fieldName: 'ketqua_xl', headerText: 'Kết quả XL' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'tongno', headerText: 'Tổng nợ', type: 'Number', format: 'N0' },
          { fieldName: 'no_ps', headerText: 'Nợ phát sinh', type: 'Number', format: 'N0' },
          { fieldName: 'no_dk', headerText: 'Nợ đầu kỳ', type: 'Number', format: 'N0' },
          { fieldName: 'trangthai_tb', headerText: 'Trạng thái TB' },
          { fieldName: 'lydo_tra', headerText: 'Lý do' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo' },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ TB' },
          { fieldName: 'nv_giao', headerText: 'Người YC' },
          { fieldName: 'donvi_tn', headerText: 'Đơn vị YC' },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'diachi_bc', headerText: 'Đia chỉ báo cước' },
          { fieldName: 'ten_dl', headerText: 'Đại lý' },
          { fieldName: 'nvtc', headerText: 'Mã NVTC' },
          { fieldName: 'tentuyen', headerText: 'Tuyến đường' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
          { fieldName: 'noidung_gq', headerText: 'Nội dung GQ' },
          { fieldName: 'nd_ton', headerText: 'Nội dung tồn' },
          { fieldName: 'ketqua_kn', headerText: 'Kết quả KN' },
          { fieldName: 'nguoi_kn', headerText: 'Người KN' },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại LH' },
          { fieldName: 'ky_tt', headerText: 'Kỳ TT' },
          { fieldName: 'tientra', headerText: 'Tiền trả' },
          { fieldName: 'hentra', headerText: 'Hẹn trả' },
          { fieldName: 'goicuoc', headerText: 'Gói cước' },
          { fieldName: 'ma_plkh', headerText: 'Mã PLKH' },
          { fieldName: 'dienthoai', headerText: 'SĐT Nhắc nợ' },
          { fieldName: 'ghichu_tra', headerText: 'Ghi chú' },
          { fieldName: 'nguoi_xl', headerText: 'Người XL' }
        ],
        datalist: [],
        selectedItems: []
      },
      DsPhieuKhoa: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'ngay_gq', headerText: 'Ngày giải quyết' },
          { fieldName: 'nhanvien_gq', headerText: 'Người giải quyết' },
          { fieldName: 'noidung_gq', headerText: 'Nội dung GQ' },
          { fieldName: 'ngay_kn', headerText: 'Ngày KN' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
          { fieldName: 'nv_giao', headerText: 'Người YC' },
          { fieldName: 'donvi_tn', headerText: 'Đơn vị YC' },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo' },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại LH' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao' },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao' },
          { fieldName: 'chudekn_id', headerText: 'Chủ đề khiếu nại ID' },
          { fieldName: 'diachi_bc', headerText: 'Đia chỉ báo cước' },
          { fieldName: 'ten_dl', headerText: 'Đại lý' },
          { fieldName: 'nvtc', headerText: 'Mã NVTC' },
          { fieldName: 'tentuyen', headerText: 'Tuyến đường' },
          { fieldName: 'ketqua_kn', headerText: 'Kết quả lỗi' },
          { fieldName: 'nguoi_kn', headerText: 'Người KN' },
          { fieldName: 'ky_tt', headerText: 'Kỳ TT' },
          { fieldName: 'tientra', headerText: 'Tiền trả' },
          { fieldName: 'hentra', headerText: 'Hẹn trả' },
          { fieldName: 'goicuoc', headerText: 'Gói cước' },
          { fieldName: 'ma_plhd', headerText: 'Mã PLKH' },
          { fieldName: 'dienthoai', headerText: 'SĐT Nhắc nợ' },
          { fieldName: 'lydo_tra', headerText: 'Lý do' },
          { fieldName: 'ghichu_tra', headerText: 'Ghi chú' },
          { fieldName: 'nguoi_xl', headerText: 'Người XL' }
        ],
        From: moment(new Date()).format('YYYYMM'),
        To: moment(new Date()).format('YYYYMM'),
        datalist: [],
        selectedItems: []
      },
      DsPhieuBaoTon: {
        headers: [
          { fieldName: 'ngay_tn', headerText: 'Ngày KN' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc' },
          { fieldName: 'ten_dvvt', headerText: 'Dịch vụ' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao' },
          { fieldName: 'diachi_tb', headerText: 'Địa chỉ thuê bao' },
          { fieldName: 'ma_tt', headerText: 'Mã thanh toán' },
          { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo' },
          { fieldName: 'ten_tt', headerText: 'Tên thanh toán' },
          { fieldName: 'diachi_tt', headerText: 'Địa chỉ thanh toán' },
          { fieldName: 'diachi_bc', headerText: 'Đia chỉ báo cước' },
          { fieldName: 'ten_dl', headerText: 'Đại lý' },
          { fieldName: 'tentuyen', headerText: 'Tuyến đường' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao' },
          { fieldName: 'nv_giao', headerText: 'Người YC' },
          { fieldName: 'donvi_tn', headerText: 'Đơn vị YC' },
          { fieldName: 'nd_khieunai', headerText: 'Nội dung khiếu nại' },
          { fieldName: 'nd_ton', headerText: 'Nội dung tồn' },
          { fieldName: 'ngay_gq', headerText: 'Ngày báo tồn' },
          { fieldName: 'nhanvien_gq', headerText: 'Người báo tồn' },
          { fieldName: 'nguoi_kn', headerText: 'Người KN' },
          { fieldName: 'dienthoai_lh', headerText: 'Điện thoại LH' },
          { fieldName: 'nvtc', headerText: 'Mã NVTC' },
          { fieldName: 'ky_tt', headerText: 'Kỳ TT' },
          { fieldName: 'tientra', headerText: 'Tiền trả' },
          { fieldName: 'hentra', headerText: 'Hẹn trả' },
          { fieldName: 'goicuoc', headerText: 'Gói cước' },
          { fieldName: 'ma_plhd', headerText: 'Mã PLKH' },
          { fieldName: 'dienthoai', headerText: 'SĐT Nhắc nợ' },
          { fieldName: 'lydo_tra', headerText: 'Lý do' },
          { fieldName: 'ghichu_tra', headerText: 'Ghi chú' },
          { fieldName: 'nguoi_xl', headerText: 'Người XL' }
        ],
        datalist: [],
        selectedItems: []
      },
      DanhSachThueBao: [],
      UiButton: {
        showGiaoXuLy: true,
        showGhiLai: false,
        showHuyGiao: false,
        showHoSo: true,
        showKhoaPhieu: false,
        showBaoTon: false,
        showXuatFile: true,
        showDanhSach: true,
        showInPhieu: false
      },
      NHOM_QT: {
        DIEUHANH_THICONG: 1,
        BAOHONG: 2,
        GIAIQUYET_KN: 3,
        BAOHANH_TBI: 4,
        SLSC_CNTT: 5,
        LUANCHUYEN_HOSO: 6,
        QUANLY_VATTU: 7,
        CAITAO_BAODUONG: 8
      },
      TRANGTHAI_PH: {
        MOI: 1,
        DAGIAO: 2,
        DATRALAI: 3,
        DATHUCHIEN: 4
      },
      TRANGTHAI_KHIEU_NAI: {
        KHIEU_NAI_MOI: 1,
        DA_GIAO_XU_LY: 2,
        PB_XACMINH_KN: 3,
        DA_XACMINH_KN: 4,
        XU_LY_XONG: 5,
        KHIEU_NAI_HOAN_THANH: 6,
        KHIEU_NAI_TON: 7,
        KHAOSAT_KN: 9,
        PNV_XLKN: 10,
        PBH_XLKN: 11,
        TTKT_XLKN: 12 // TTKT - Xử lý khiếu nại
      },
      txtDonViGiao: '',
      txtNoiDungGiao: '',
      txtKyTT: '',
      dtpNgayHen: '',
      txtNoiDungKN: '',
      txtTienTra: '',
      txtGoiCuoc: '',
      cboKetQua_XL: '',
      dtpNgayXL: '',
      txtNguyenNhan: '',
      cboLoi: '',
      cboNvTH: '',
      txtTienGC: '',
      txtNoiDungXL: '',
      txtNoiDungXL_Ton: '',
      lblNDXuLy: 'ND trả lời <span style="color: #dc3545"> (*)</span>', //<span style="color: #dc3545"> (*)</span>
      kyhoadon: '',
      chuky: '',
      dtpNgayXL_Enabled: false,
      dtpNgayHen_Enabled: false,
      tsbtnGhiLai: true,
      tsbtnTon: true,
      tsbtnKhoaPhieu: true,
      tsbtnGhiLai_Enable: true,
      tsbtnTon_Enabled: true,
      tsbtnKhoaPhieu_Enabled: true,
      tsbtnGiaoXL: true,
      tsbtnGiaoXL_Enabled: true,
      tsbtnHuyGiao: true,
      tsbtnHuyGiao_Enabled: false,
      tsbtnHSKN: true,
      tsbtnHSKN_Enabled: true,
      tsbtnXuatExcel: true,
      tsbtnXuatExcel_Enabled: true,
      tsbtnXem: true,
      tsbtnXem_Enabled: true,
      tsbtnInPhieu: true,
      tsbtnInPhieu_Enabled: true,
      dtThaoTac: [],
      ma_bc: 'PHIEU_TB_TT',
      dsGiaTri: [],
      PHIEU_TB_TT: 'PHIEU_TB_TT',
      PHIEU_KYNHAN_KN: 'KYNHAN_KN',
      inMauMoi: -1,
      exportData: [],
      TOTRUONG_XLKN: 60,
      loaded: false,
      txtNoiDungXL_MaxLength: 1000,
      timer: null,
      notFinished: false,
      contextMenuItems:[
          {
              id: 'giaoxuly',
              text: 'Giao xử lý',
              target: '.e-content',
          },
          {
              id: 'traphieu',
              text: 'Trả phiếu',
              target: '.e-content',
          },
      ],
      contextMenuItems_choxl:[
          {
              id: 'traphieu',
              text: 'Trả phiếu',
              target: '.e-content',
          },
      ],
      isCheckedHeader2:false,
      isCheckedHeader3:false,
      isTriggerChangeChuaXuLy:true
    }
  },
  watch: {
    tabIndex: async function(val) {
      // try {
      //   this.loading(true)
      //   await this.HT_DS_KHIEUNAI(val)
      // } catch (err) {
      //   this.loading(false)
      // } finally {
      //   this.loading(false)
      // }
    },
    QuyTrinhKnId: async function(val) {
      return val
      /*
      try {
        this.loading(true)
        this.quytrinh_id = val
        // await this.GetDmChuDe(val)
        let data = await this.lay_ds_huong_giao(val)
        if (data != null && data.length > 0) {
          this.luong_id = data[0]['luong_id']
          let tenhg = data[0]['huonggiao']
          this.labelFunctionTitle = tenhg.toUpperCase()
          this.labelFunctionTitleMenu = tenhg
        } else {
          this.ShowError('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          await this.HT_DS_CHUDE_KN(val)
          this.loaded = true
          return
        }
        // await this.HienThiGiaoDien()
        // await this.HT_DS_CHUDE_KN(val)
        if (this.loaded) {
          console.log(`QuyTrinhKnId:loaded`)
          await Promise.all([this.HienThiGiaoDien(), this.HT_DS_CHUDE_KN(val)])
        } else {
          console.log(`QuyTrinhKnId:setTimeout`)
          setTimeout(() => this.HT_DS_CHUDE_KN(val), 0)
          this.loaded = true
        }
      } catch (err) {
        console.log('QuyTrinhKnId', err)
      } finally {
        this.loading(false)
      }
      */
    },
    cboKetQua_XL: function(val) {
      // if (!this.loaded) return
      // alert('123')
      return
    },
    Loading: function(val) {
      // if (!this.loaded) return
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetDmQuyTrinhKhieuNai: async function() {
      let response = await KhieuNaiApi.getDmQuyTrinhKhieuNais(this.axios, {
        huong_giao_id: this.HuongGiaoId,
        nhom_qt_id: this.NHOM_QT.GIAIQUYET_KN,
        phan_vung_id: this.PhanVungId,
        ttkn_id: this.TtKhieuNaiId
      })
      let quytrinhs = []
      let data = this.GetData(response)
      let dmquytrinh = data.map((x) => ({ id: x.quytrinh_id, text: x.quytrinh == null ? '' : x.quytrinh }))
      dmquytrinh.forEach(function(item) {
        if (quytrinhs.filter((x) => x.id == item.id).length == 0) {
          quytrinhs.push(item)
        }
      })
      this.DmQuyTrinhKN = quytrinhs
      if (quytrinhs.length > 0) {
        this.QuyTrinhKnId = this.DmQuyTrinhKN[0].id
        this.quytrinh_id = this.QuyTrinhKnId
        await this.QuyTrinhKnChanged(this.DmQuyTrinhKN[0].id)
        // await this.GetDmChuDe(this.QuyTrinhKnId)
      }
    },
    // async onTabChange(){
    //   try {
    //     this.loading(true)
    //     await this.HT_DS_KHIEUNAI(val)
    //   } catch (err) {
    //   } finally {
    //     this.loading(false)
    //   }
    // },
    async changeTab(tab) {
      this.tabIndex = tab
      try {
        this.loading(true)
        await this.HT_DS_KHIEUNAI(this.tabIndex)
      } catch (err) {
      } finally {
        this.loading(false)
      }
    },
    async HT_DS_KHIEUNAI(tabIndex) {
      console.log('HT_DS_KHIEUNAI:tabIndex', tabIndex)
      this.Clear()
      this.inMauMoi = 0
      this.tsbtnInPhieu_Enabled = false
      this.UiButton.showInPhieu = false
      let vchudekn_id = this.DanhSachChuDe.selectedItem.chudekn_id
      if (tabIndex == 0) {
        this.UiButton.showGiaoXuLy = true
        this.UiButton.showGhiLai = false
        this.UiButton.showHuyGiao = false
        this.UiButton.showHoSo = true
        this.UiButton.showKhoaPhieu = false
        this.UiButton.showBaoTon = false
        this.UiButton.showXuatFile = true
        this.UiButton.showDanhSach = true

        if (vchudekn_id == this.ChuDenKhieuNai.KHONG_NHAN_DUOC_GBC) {
          this.UiButton.showInPhieu = true
          this.tsbtnInPhieu_Enabled = true
        }
        let dsKtra = await this.kiemtra_chudekn_knbs(vchudekn_id)
        if (dsKtra == 1) {
          this.inMauMoi = 1
          this.tsbtnInPhieu_Enabled = true
          this.UiButton.showInPhieu = true
        }
        // Co xu ly dau ma enable nhỉ ?
        this.UiButton.showInPhieu = false
        // Chua xu ly

        /*
        this.tsbtnGhiLai_Enabled = false
        this.tsbtnTon_Enabled = false
        this.tsbtnKhoaPhieu_Enabled = false
        this.tsbtnGiaoXL_Enabled = true
        this.tsbtnHuyGiao_Enabled = false
        */
        // Default Id
        this.cboKetQua_XL = 1
      } else if (tabIndex == 1) {
        if (vchudekn_id == this.ChuDenKhieuNai.KHONG_NHAN_DUOC_GBC) {
          this.UiButton.showInPhieu = true
          this.tsbtnInPhieu_Enabled = true
        }
        let dsKtra = await this.kiemtra_chudekn_knbs(vchudekn_id)
        if (dsKtra == 1) {
          this.inMauMoi = 1
          this.tsbtnInPhieu_Enabled = true
          this.UiButton.showInPhieu = true
        }
        // Cho xu ly
        this.UiButton.showGiaoXuLy = false
        this.UiButton.showGhiLai = true
        this.UiButton.showHuyGiao = true
        this.UiButton.showHoSo = true
        this.UiButton.showKhoaPhieu = true
        // alert(this.cboKetQua_XL)
        if (this.cboKetQua_XL == '1' || this.cboKetQua_XL == 1) {
          this.UiButton.showBaoTon = false
        } else {
          this.UiButton.showBaoTon = true
        }

        this.UiButton.showXuatFile = true
        this.UiButton.showDanhSach = true
      } else if (tabIndex == 2) {
        // if (vchudekn_id == this.ChuDenKhieuNai.KHONG_NHAN_DUOC_GBC) {
        //   this.UiButton.showInPhieu = true
        //   this.tsbtnInPhieu_Enabled = true
        // }
        // Khoa phieu
        this.UiButton.showGiaoXuLy = false
        this.UiButton.showGhiLai = true
        this.UiButton.showHuyGiao = false
        this.UiButton.showHoSo = true
        this.UiButton.showKhoaPhieu = false
        this.UiButton.showBaoTon = false
        this.UiButton.showXuatFile = true
        this.UiButton.showDanhSach = true
        // this.UiButton.showInPhieu = false
      } else if (tabIndex == 3) {
        // Bao ton
        this.UiButton.showGiaoXuLy = false
        this.UiButton.showGhiLai = false
        this.UiButton.showHuyGiao = false
        this.UiButton.showHoSo = true
        this.UiButton.showKhoaPhieu = false
        this.UiButton.showBaoTon = false
        this.UiButton.showXuatFile = true
        this.UiButton.showDanhSach = true
        this.UiButton.showInPhieu = false
      } else {
        console.log('')
      }
      try {
        this.loading(true)
        await this.GetDanhSachPhieus(tabIndex)
        this.loading(false)
      } catch (err) {
        this.loading(false)
      } finally {
        this.loading(false)
      }

      /*
      Promise.all([this.GetDanhSachPhieus(tabIndex)])
        .then((responses) => {
          //
        })
        .finally((x) => {
          this.loading(false)
        })
      */
    },
    async HienThiGiaoDien() {
      this.tsbtnGhiLai = true
      this.tsbtnTon = false
      this.UiButton.showBaoTon = false
      this.tsbtnKhoaPhieu = false
      let self = this
      await this.lay_luong_thaotac(this.luong_id)
      for (const item of this.dtThaoTac) {
        console.log('thaotac_id', item.thaotac_id, item.code, item.enable)
        let dtControl = await self.lay_ct_thaotac_control(item.thaotac_id)
        console.log('thaotac_id:control', item.code, dtControl)
        if (dtControl != null && dtControl.length > 0) {
          // control_name
          if (item.enable == 1) {
            if (dtControl.filter((x) => x.control_name == 'tsbtnGhiLai').length > 0) {
              self.tsbtnGhiLai = true
              self.tsbtnGhiLai_Enable = true
              self.UiButton.showGhiLai = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnGiaoXL'.toLowerCase()).length > 0) {
              self.tsbtnGiaoXL = true
              self.tsbtnGiaoXL_Enabled = true
              self.UiButton.showGiaoXuLy = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHuyGiao'.toLowerCase()).length > 0) {
              self.tsbtnHuyGiao = true
              self.tsbtnHuyGiao_Enabled = true
              self.UiButton.showHuyGiao = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnHSKN'.toLowerCase()).length > 0) {
              self.tsbtnHSKN = true
              self.tsbtnHSKN_Enabled = true
              self.UiButton.showHoSo = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnKhoaPhieu'.toLowerCase()).length > 0) {
              self.tsbtnKhoaPhieu = true
              self.tsbtnKhoaPhieu_Enabled = true
              self.UiButton.showKhoaPhieu = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnTon'.toLowerCase()).length > 0) {
              self.tsbtnTon = true
              self.tsbtnTon_Enabled = true
              self.UiButton.showBaoTon = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnXuatExcel'.toLowerCase()).length > 0) {
              self.tsbtnXuatExcel = true
              self.tsbtnXuatExcel_Enabled = true
              self.UiButton.showXuatFile = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnXem'.toLowerCase()).length > 0) {
              self.tsbtnXem = true
              self.tsbtnXem_Enabled = true
              self.UiButton.showDanhSach = true
            } else if (dtControl.filter((x) => x.control_name.toLowerCase() == 'tsbtnInPhieu'.toLowerCase()).length > 0) {
              self.tsbtnInPhieu = true
              self.tsbtnInPhieu_Enabled = true
              self.UiButton.showInPhieu = true
            } else {
            }
            let keys = Object.keys(self)
            for (const ctrlName of dtControl) {
              if (keys.filter((x) => x == ctrlName.control_name).length > 0) self[ctrlName.control_name] = true
            }
          }
        }
      }
    },
    lay_luong_thaotac: async function() {
      var input = { luong_id: this.luong_id }
      let data = this.GetData(await KhieuNaiApi.lay_luong_thaotac(this.axios, input))
      if (data == null || data.length == 0) {
        console.log(`Không có cấu hình thao tác cho luồng dữ liệu ${this.LuongId}`)
      }
      this.dtThaoTac = data
      // console.log('lay_luong_thaotac', data)
    },
    lay_ct_thaotac_control: async function(thaotacId) {
      var input = { thaotac_id: thaotacId }
      let data = this.GetData(await KhieuNaiApi.lay_ct_thaotac_control(this.axios, input))
      console.log('lay_ct_thaotac_control:', data)
      return data
    },
    async Lay_HG_GQKN() {},
    GetDmChuDe: async function(quytrinhId) {
      await this.laythangno()
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoidungId = this.$root.token.getNguoiDungID()
      let response = await KhieuNaiApi.getDmChuDeKhieuNais(this.axios, {
        huong_giao_id: this.HuongGiaoId,
        ky_hoadon: this.kyhoadon,
        nguoidung_id: nguoidungId,
        nhanvien_id: nhanvienId,
        phan_vung_id: this.PhanVungId,
        quytrinh_id: quytrinhId
      })

      this.DmChuDe = this.GetData(response)
      let tmpChuDe = []
      if (this.DmChuDe != null && this.DmChuDe.length > 0) {
        this.DmChuDe.forEach(function(item) {
          if (tmpChuDe.filter((x) => x.chudekn_id == item.chudekn_id).length == 0) tmpChuDe.push(item)
        })
        let ii = 1
        tmpChuDe.forEach(function(item) {
          item.stt = ii
          ii++
        })
      }
      this.DanhSachChuDe.data = tmpChuDe.sort((a, b) => (a.stt > b.stt ? 1 : b.stt > a.stt ? -1 : 0))
      if (this.ChuDeKnId > 0) {
        let chude = this.DanhSachChuDe.data.filter((x) => x.chudekn_id == this.ChuDeKnId)
        if (chude != null && chude.length > 0) {
          await this.ChonChuDe(0, chude[0])
        }
      } else {
        this.ChuDeKnId = this.DanhSachChuDe.data[0].chudekn_id
        if (this.DanhSachChuDe.data != null && this.DanhSachChuDe.data.length > 0) {
          await this.ChonChuDe(0, this.DanhSachChuDe.data[0])
        }
      }
    },
    GetDmKetQuaXuLy: async function() {
      let response = await KhieuNaiApi.getDmKetQuaXuLys(this.axios)
      this.DmKetQuaXuLy = this.GetData(response)
      // this.DmKetQuaXuLy.unshift({kq_id:0 , ketqua:'Chọn kết quả xử lý'})
      if (this.DmKetQuaXuLy != null && this.DmKetQuaXuLy.length > 0) this.cboKetQua_XL = this.DmKetQuaXuLy[0].kq_id
    },
    async kiemtra_chudekn_knbs() {
      try {
        let chudekn_id = this.ChuDeKnId
        // let input = { id_neo: 'chudekn_id', in_table: 'qltn.chude_kn', in_dk: `where  chudekn_id = ${chudekn_id} and chudekn_id in (-1)` }
        // let data = this.GetData(await KhieuNaiApi.map_id(this.axios, input))
        let data = this.GetData(await KhieuNaiApi.fn_kiemtra_chudekn_knbs(this.axios, { chudekn_id: chudekn_id }))
        if (data != -1 && data > 0) return 1
        return 0
      } catch (err) {
        return 0
      }
      // let response = await KhieuNaiApi.kiemtra_chudekn_knbs(this.axios, {
      //   vchudekn_id: chudekn_id
      // })
      // let data = this.GetData(response)
      // console.log('kiemtra_chudekn_knbs', data)
      // return 0
    },
    GetDmKetQuaLoi: async function() {
      let response = await KhieuNaiApi.getDmKetQuaLois(this.axios, {
        loai_id: 1
      })
      this.DmKetQuaLoi = this.GetData(response)
      if (this.DmKetQuaLoi != null && this.DmKetQuaLoi.length > 0) this.cboLoi = this.DmKetQuaLoi[0].ketquakn_id
    },
    GetDmNhanVienXuLy: async function() {
      // console.log('1')
      let donviId = this.$root.token.getDonViID()
      let response = await KhieuNaiApi.getDmNhanVienXuLys(this.axios, {
        id: donviId
      })
      let nhanvienId = this.$root.token.getNhanVienID()
      let username = this.$root.token.getUserName()
      this.DmNhanVienXuLy = this.GetData(response)
      // let tt_nd = await this.$root.token.getThongTinNguoiDung()
      // console.log('tt_nd', tt_nd)
      if (this.DmNhanVienXuLy != null && this.DmNhanVienXuLy.filter((x) => x.nhanvien_id == nhanvienId).length == 0) {
        let nhanvien = { nhanvien_id: nhanvienId, ten_nv: username }
        this.DmNhanVienXuLy.push(nhanvien)
      }
      this.cboNvTH = nhanvienId
    },
    GetChiTietPhieu: async function(khieunaiid) {
      if (khieunaiid) {
        let phanvungId = this.$root.token.getPhanVungID()
        // khieunaiid = 27959
        let response = await KhieuNaiApi.getChiTietPhieu(this.axios, {
          phan_vung_id: phanvungId, // auto theo token
          khieunai_id: khieunaiid
        })
        this.PhieuKhieuNai = this.GetData(response)
        this.dgvChiTiet.data = this.PhieuKhieuNai
        if (this.PhieuKhieuNai != null) {
          let donvigiaoId = await this.MapDonViGiaoId(khieunaiid)
          if (donvigiaoId != null && donvigiaoId != -1) {
            let tendonviGiao = await this.MapDonVi(donvigiaoId)
            if (tendonviGiao != null) {
              this.PhieuKhieuNai.donvi_giao = tendonviGiao
            }
          }
        }
        this.BoXungKhieuNai = this.GetData(await KhieuNaiApi.getBoXungKhieuNai(this.axios, { khieunai_id: khieunaiid }))
      }      
    },
    // Remove
    // MapDonViGiaoId_Old: async function (phieuknid) {
    //   let input = { id_neo: 'donvi_giao_id', in_table: 'qltn.giaophieu_kn', in_dk: `where  phieukn_id = ${phieuknid}` }
    //   let data = this.GetData(await KhieuNaiApi.map_id(this.axios, input))
    //   return data
    // },
    MapDonViGiaoId: async function(phieuknid) {
      let input = { type: 1, param: phieuknid }
      let data = this.GetData(await KhieuNaiApi.fn_tt_giaophieu_kn(this.axios, input))
      return data
    },
    // MapDonVi_Old: async function (donviId) {
    //   let input = { id_neo: 'ten_dv', in_table: 'admin.donvi', in_dk: `where  donvi_id = ${donviId}` }
    //   let data = this.GetData(await KhieuNaiApi.map_id(this.axios, input))
    //   return data
    // },
    MapDonVi: async function(donviId) {
      let input = { type: 2, param: donviId }
      let data = this.GetData(await KhieuNaiApi.fn_tt_donvi(this.axios, input))
      return data
    },
    async laythangno() {
      if (this.kyhoadon != '') return
      let input = {}
      let data = this.GetData(await KhieuNaiApi.laythangno(this.axios, input))
      // alert(data.result)
      console.log('laythangno', data.result)
      this.kyhoadon = data.result
      // alert(this.kyhoadon)
      return data.result
    },
    KiemTraQuyenNguoiDung: async function(nguoidungId, quyenId) {
      try {
        // let input = {
        //   id_neo: 'count(1)',
        //   in_table: 'admin.ds_quyen_nd',
        //   in_dk: `where  nguoidung_id = ${nguoidungId} and  quyen_id = ${quyenId}`
        // }
        // let data = this.GetData(await KhieuNaiApi.map_id(this.axios, input))
        let data = this.GetData(await KhieuNaiApi.fn_tt_ds_quyen_nd(this.axios, { type: 1, param: nguoidungId, param1: quyenId }))
        if (data > 0) {
          this.kt_totruong = true
          return true
        }
        this.kt_totruong = false
        return false
      } catch (err) {
        console.log('Lỗi KiemTraQuyenNguoiDung', err)
        this.kt_totruong = false
        return false
      }
    },
    gridChoXuLy_PageChanged(args) {
      console.log('gridChoXuLy_PageChanged', args)
      this.currentSavePageIndex = args.pageIndex
      if (!this.currentSave) this.currentSaveIndex = 0
    },
    gridKhoaPhieu_PageChanged(args) {
      console.log('gridKhoaPhieu_PageChanged', args)
      this.currentSavePageIndex = args.pageIndex
      if (!this.currentSave) this.currentSaveIndex = 0
    },
    // gridChoXuLy_RowDeselected(args) {
    //   console.log('gridChoXuLy_RowDeselected', args)
    //   if (this.currentIndex == args.rowIndex) {
    //     console.log('ClearAll')
    //     this.ClearAll()
    //   }
    // },
    async gridChoXuLy_RowSelected(args) {
      if (args) {
        this.currentIndex = args.rowIndex
        await this.gridChoXuLy_RowClicked(args.rowIndex, args.data)
        if (!args.isHeaderCheckboxClicked && args.isInteracted) {
          // this.updateDSKhoaSelected(args.data)
          console.log('gridChoXuLy_RowSelected', args)
          if(Array.isArray(args.data)){
            for(let i=0;i<args.data.length;i++){
              const index = this.DsPhieuChoXuLy.datalist.findIndex(x => x.khieunai_id == args.data[i].khieunai_id)
              this.DsPhieuChoXuLy.datalist[index].ischeck='1'
            }
          }else {
            const index = this.DsPhieuChoXuLy.datalist.findIndex(x => x.khieunai_id == args.data.khieunai_id)
            this.DsPhieuChoXuLy.datalist[index].ischeck='1'
          }
        }
      }    
    },
    gridKhoaPhieu_ActionComplete(args) {
      try {
        this.$refs.gridKhoaPhieu.pageIndex = this.currentSavePageIndex
        this.$refs.gridKhoaPhieu.reloadCurrentPage()
      } catch (err) {
        console.log('🚀 ~ file: ResovleComplaintAfterSales.vue ~ line 962 ~ gridChoXuLy_gridKhoaPhieu ~ err', err)
      }

      if (this.currentSaveIndex > 0) this.$refs.gridKhoaPhieu.selectRow(this.currentSaveIndex, true)
      this.currentSave = false
    },
    gridChoXuLy_ActionComplete(args) {
      try {
        this.$refs.gridChoXuLy.pageIndex = this.currentSavePageIndex
        this.$refs.gridChoXuLy.reloadCurrentPage()
      } catch (err) {
        console.log('🚀 ~ file: ResovleComplaintAfterSales.vue ~ line 962 ~ gridChoXuLy_ActionComplete ~ err', err)
      }

      if (this.currentSaveIndex > 0) this.$refs.gridChoXuLy.selectRow(this.currentSaveIndex, true)
      this.currentSave = false
    },
    gridChoXuLy_PaginationCommandClick(command, args) {
      console.log('paginationCommandClick', command, args)
    },
    gridChoXuLy_CellSelected(args) {
      console.log('gridChoXuLy_CellSelected', args)
    },
    gridChoXuLy_RowClicked: async function(i, item) {
      this.currentIndex = i
      let selectedItems = this.$refs.gridChoXuLy.grid.getSelectedRecords()
      console.log('gridChoXuLy_RowClicked', i, item, 'selectedItems:', selectedItems)
      if (item) {
        this.khieunai_id = item.khieunai_id
        this.phieukn_id = item.phieukn_id
        this.thuebao_id = item.thuebao_id
        this.txtNoiDungGiao = item.nd_giao
        this.txtNguyenNhan = item.nguyennhan_kn
        this.txtNoiDungKN = item.nd_khieunai
        this.donvikn_id = item.donvi_id

        if (item.noidung_gq != null && item.noidung_gq != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[0].kq_id
          this.txtNoiDungXL = item.noidung_gq
          this.lblNDXuLy = 'ND trả lời <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton != null && item.nd_ton != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[1].kq_id
          this.txtNoiDungXL_Ton = item.nd_ton
          this.lblNDXuLy = 'ND tồn <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton == '' && item.noidung_gq == '') {
          this.txtNoiDungXL = ''
          this.txtNoiDungXL_Ton = ''
        }
        if (item.tien_gc != null && item.tien_gc != '') {
          this.txtTienGC = item.tien_gc
        } else {
          this.txtTienGC = 0
        }
        let donvigiaoId = await this.MapDonViGiaoId(item.khieunai_id)
        if (donvigiaoId != null && donvigiaoId != -1) {
          let tendonviGiao = await this.MapDonVi(donvigiaoId)
          if (tendonviGiao != null) {
            this.txtDonViGiao = tendonviGiao
          }
        }

        if (item.nhanvien_th_id != null && item.nhanvien_th_id != '') {
          this.cboNvTH = item.nhanvien_th_id
        } else if (item.nhanvien_gq_id != null && item.nhanvien_gq_id != '') {
          this.cboNvTH = item.nhanvien_gq_id
        } else {
          let nhanvienId = this.$root.token.getNhanVienID()
          this.cboNvTH = nhanvienId
        }
        if (item.ngay_gq != null && item.ngay_gq != '') {
          this.dtpNgayXL = moment(item.ngay_gq, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        } else {
          this.dtpNgayXL = moment().format(this.dateFormatFull)
        }
        if (item.ketquakn_id != null && item.ketquakn_id != '') {
          this.cboLoi = item.ketquakn_id
        } else {
          this.cboLoi = this.DmKetQuaLoi[0].ketquakn_id
        }
        await this.Hienthi_Khieunai_BS(item.khieunai_id)
      }      
    },
    async gridChuaXuLy_RowSelected(args) {
      await this.ChangeItemChuaXuLy(args.rowIndex, args.data)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) {
        // this.updateDSKhoaSelected(args.data)
        console.log('gridChuaXuLy_RowSelected', args)
        if(Array.isArray(args.data)){
          for(let i=0;i<args.data.length;i++){
            const index = this.DanhSachPhieus.datalist.findIndex(x => x.khieunai_id == args.data[i].khieunai_id)
            this.DanhSachPhieus.datalist[index].ischeck='1'
          }
          
        }else {
          const index = this.DanhSachPhieus.datalist.findIndex(x => x.khieunai_id == args.data.khieunai_id)
          this.DanhSachPhieus.datalist[index].ischeck='1'
          
        }
        
      }
    },
    ChangeItemChuaXuLy: async function (i, data) {      
      var item = ''
      if (data instanceof Array) item = data[0]
      else item = data
      console.log('ChangeItemChuaXuLy', item)
      if (item) {
        this.khieunai_id = item.khieunai_id
        this.phieukn_id = item.phieukn_id
        this.thuebao_id = item.thuebao_id
        this.txtNoiDungGiao = item.nd_giao
        this.txtNguyenNhan = item.nguyennhan_kn
        this.txtNoiDungKN = item.nd_khieunai
        this.donvikn_id = item.donvi_id
        let donvigiaoId = await this.MapDonViGiaoId(item.khieunai_id)
        if (donvigiaoId != null && donvigiaoId != -1) {
          let tendonviGiao = await this.MapDonVi(donvigiaoId)
          if (tendonviGiao != null) {
            this.txtDonViGiao = tendonviGiao
          }
        }
        await this.Hienthi_Khieunai_BS(item.khieunai_id)
        if (item.noidung_gq != null && item.noidung_gq != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[0].kq_id
          this.txtNoiDungXL = item.noidung_gq
          this.lblNDXuLy = 'ND trả lời <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton != null && item.nd_ton != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[1].kq_id
          this.txtNoiDungXL = item.nd_ton
          this.txtNoiDungXL_Ton = item.nd_ton
          this.lblNDXuLy = 'ND tồn <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton == '' && item.noidung_gq == '') {
          this.txtNoiDungXL = ''
          this.txtNoiDungXL_Ton = ''
        }
        if (item.tien_gc != null && item.tien_gc != '') {
          this.txtTienGC = item.tien_gc
        } else {
          this.txtTienGC = 0
        }
        if (item.nhanvien_th_id != null && item.nhanvien_th_id != '') {
          this.cboNvTH = item.nhanvien_th_id
        } else if (item.nhanvien_gq_id != null && item.nhanvien_gq_id != '') {
          this.cboNvTH = item.nhanvien_gq_id
        } else {
          let nhanvienId = this.$root.token.getNhanVienID()
          this.cboNvTH = nhanvienId
        }
        if (item.ngay_gq != null && item.ngay_gq != '') {
          this.dtpNgayXL = moment(item.ngay_gq, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        } else {
          this.dtpNgayXL = moment().format(this.dateFormatFull)
        }
        if (item.ketquakn_id != null && item.ketquakn_id != '') {
          this.cboLoi = item.ketquakn_id
        } else {
          this.cboLoi = this.DmKetQuaLoi[0].ketquakn_id
        }
        //
        await this.GetChiTietPhieu(this.khieunai_id)
      }      
    },
    async gridKhoaPhieu_RowSelected(args) {
      this.currentIndex = args.rowIndex
      // await this.ChangeItemKhoaPhieu(args.rowIndex, args.data)
      await this.gridKhoaPhieu_RowClicked(args.rowIndex, args.data)
    },
    gridKhoaPhieu_RowClicked: async function(i, item) {
      console.log('gridKhoaPhieu_RowClicked', item)
      this.currentIndex = i
      if (item) {
        this.khieunai_id = item.khieunai_id
        this.phieukn_id = item.phieukn_id
        this.thuebao_id = item.thuebao_id
        this.txtNoiDungGiao = item.nd_giao
        this.txtNguyenNhan = item.nguyennhan_kn
        this.txtNoiDungKN = item.nd_khieunai
        this.donvikn_id = item.donvi_id
        let donvigiaoId = await this.MapDonViGiaoId(item.khieunai_id)
        if (donvigiaoId != null && donvigiaoId != -1) {
          let tendonviGiao = await this.MapDonVi(donvigiaoId)
          if (tendonviGiao != null) {
            this.txtDonViGiao = tendonviGiao
          }
        }
        await this.Hienthi_Khieunai_BS(item.khieunai_id)
        if (item.noidung_gq != null && item.noidung_gq != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[0].kq_id
          this.txtNoiDungXL = item.noidung_gq
          this.lblNDXuLy = 'ND trả lời <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton != null && item.nd_ton != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[1].kq_id
          this.txtNoiDungXL = item.nd_ton
          this.lblNDXuLy = 'ND tồn <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton == '' && item.noidung_gq == '') {
          this.txtNoiDungXL = ''
          this.txtNoiDungXL_Ton = ''
        }
        if (item.tien_gc != null && item.tien_gc != '') {
          this.txtTienGC = item.tien_gc
        } else {
          this.txtTienGC = 0
        }
        if (item.nhanvien_th_id != null && item.nhanvien_th_id != '') {
          this.cboNvTH = item.nhanvien_th_id
        } else if (item.nhanvien_gq_id != null && item.nhanvien_gq_id != '') {
          this.cboNvTH = item.nhanvien_gq_id
        } else {
          let nhanvienId = this.$root.token.getNhanVienID()
          this.cboNvTH = nhanvienId
        }
        if (item.ngay_gq != null && item.ngay_gq != '') {
          this.dtpNgayXL = moment(item.ngay_gq, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        } else {
          this.dtpNgayXL = moment().format(this.dateFormatFull)
        }
        if (item.ketquakn_id != null && item.ketquakn_id != '') {
          this.cboLoi = item.ketquakn_id
        } else {
          this.cboLoi = this.DmKetQuaLoi[0].ketquakn_id
        }
      }      
    },
    async gridBaoTon_RowSelected(args) {
      await this.ChangeItemBaoTon(args.rowIndex, args.data)
    },
    ChangeItemBaoTon: async function (i, data) {
      var item = ''
      if (data instanceof Array) item = data[0]
      else item = data
      console.log('ChangeItemBaoTon', item)
      if (item) {
        this.khieunai_id = item.khieunai_id
        this.phieukn_id = item.phieukn_id
        this.thuebao_id = item.thuebao_id
        this.txtNoiDungGiao = item.nd_giao
        this.txtNguyenNhan = item.nguyennhan_kn
        this.txtNoiDungKN = item.nd_khieunai
        this.donvikn_id = item.donvi_id
        let donvigiaoId = await this.MapDonViGiaoId(item.khieunai_id)
        if (donvigiaoId != null && donvigiaoId != -1) {
          let tendonviGiao = await this.MapDonVi(donvigiaoId)
          if (tendonviGiao != null) {
            this.txtDonViGiao = tendonviGiao
          }
        }
        await this.Hienthi_Khieunai_BS(item.khieunai_id)
        if (item.noidung_gq != null && item.noidung_gq != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[0].kq_id
          this.txtNoiDungXL = item.noidung_gq
          this.txtNoiDungXL_Ton = ''
          this.lblNDXuLy = 'ND trả lời <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton != null && item.nd_ton != '') {
          this.cboKetQua_XL = this.DmKetQuaXuLy[1].kq_id
          this.txtNoiDungXL = item.nd_ton
          this.txtNoiDungXL_Ton = item.nd_ton
          this.lblNDXuLy = 'ND tồn <span style="color: #dc3545"> (*)</span>'
        }
        if (item.nd_ton == '' && item.noidung_gq == '') {
          this.txtNoiDungXL = ''
          this.txtNoiDungXL_Ton = ''
        }
        if (item.tien_gc != null && item.tien_gc != '') {
          this.txtTienGC = item.tien_gc
        } else {
          this.txtTienGC = 0
        }
        if (item.nhanvien_th_id != null && item.nhanvien_th_id != '') {
          this.cboNvTH = item.nhanvien_th_id
        } else if (item.nhanvien_gq_id != null && item.nhanvien_gq_id != '') {
          this.cboNvTH = item.nhanvien_gq_id
        } else {
          let nhanvienId = this.$root.token.getNhanVienID()
          this.cboNvTH = nhanvienId
        }
        if (item.ngay_gq != null && item.ngay_gq != '') {
          this.dtpNgayXL = moment(item.ngay_gq, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
        } else {
          this.dtpNgayXL = moment().format(this.dateFormatFull)
        }
        if (item.ketquakn_id != null && item.ketquakn_id != '') {
          this.cboLoi = item.ketquakn_id
        } else {
          this.cboLoi = this.DmKetQuaLoi[0].ketquakn_id
        }
      }      
    },
    async HT_DS_CHUDE_KN(val) {
      await this.GetDmChuDe(val)
    },
    async Hienthi_Khieunai_BS(khieunai_id) {
      try {
        let data = this.GetData(await KhieuNaiApi.getBoXungKhieuNai(this.axios, { khieunai_id: khieunai_id }))
        if (data != null && data.length > 0) {
          this.txtKyTT = data[0].ky_tt
          if (data[0].tientra != null && data[0].tientra != '') {
            this.txtTienTra = data[0].tientra
          }
          if (data[0].hentra != null && data[0].hentra != '') {
            this.dtpNgayHen = moment(data[0].hentra, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss')
          }
          this.txtGoiCuoc = data[0].goicuoc
        }
      } catch (err) {
        console.log('Hienthi_Khieunai_BS err', err)
      }
    },
    Clear() {
      this.txtTienGC = '0'
      this.txtNoiDungXL = ''
      this.txtNoiDungXL_Ton = ''
      this.dtpNgayXL = moment().format(this.dateFormatFull)
      this.txtNguyenNhan = ''
      this.txtNoiDungGiao = ''
      this.donvikn_id = -1
      this.txtDonViGiao = ''
      this.txtNoiDungKN = ''
    },
    ClearAll() {
      this.Clear()
      this.txtNoiDungXL = ''
      this.txtNoiDungXL_Ton = ''
    },
    async Xuat_Excel(input, namefile) {
      return new Promise(async (resolve, reject) => {
        try {
          this.loading(true)
          let response = await this.axios.post(
            '/web-tracuu/khieunai/ds_xuly_khieunai',
            input,
            {responseType: 'arraybuffer'}
          )

          if (response.headers && response.headers['content-type'] == "text/plain") {
            const reader = new FileReader();
            reader.addEventListener('loadend', (e) => {
              const text = e.srcElement.result;
              this.$toast.error(text)
              return resolve(false)
            });
            let blb = new Blob([response.data], {type: 'text/plain'})
            reader.readAsText(blb);
            return
          }

          if(response.data && response.data.size == 0) {
            this.$toast.error("Không có dữ liệu. Vui lòng kiểm tra lại")
            return resolve(false)
          }

          const link = document.createElement('a')
          link.href = window.URL.createObjectURL(new Blob([response.data]))
          link.setAttribute('download', (namefile + ".xlsx"))
          document.body.appendChild(link)
          link.click()
        } catch(err) {
          console.log(err)
          return resolve(false)
        } finally {
          this.loading(false)
        }

        return resolve(true)
      })
    },
    GetDanhSachPhieus: async function(tab) {
      await this.laythangno()
      if (tab == null || tab == undefined) tab = 0
      console.log('GetDanhSachPhieus:', tab)
      let tuthang = 0
      let denthang = 0
      // this.tabIndex = val
      if (tab == 0) {
        this.TtPhieuId = this.TRANGTHAI_PH.MOI
        this.TtKhieuNaiId = this.dsttkn_id
      } else if (tab == 1) {
        this.TtPhieuId = this.TRANGTHAI_PH.DAGIAO // 2
        this.TtKhieuNaiId = this.dsttkn_id
      } else if (tab == 2) {
        // Khoa phieu
        this.TtPhieuId = 0
        this.TtKhieuNaiId = this.TRANGTHAI_KHIEU_NAI.KHIEU_NAI_HOAN_THANH // 6 // Khieu nai hoan thanh
        tuthang = this.DsPhieuKhoa.From
        denthang = this.DsPhieuKhoa.To
      } else if (tab == 3) {
        this.TtPhieuId = 0
        this.TtKhieuNaiId = this.TRANGTHAI_KHIEU_NAI.KHIEU_NAI_TON // 7 // Khieu nai ton
      } else {
        console.log('')
      }
      let nhanvienId = this.$root.token.getNhanVienID()
      let nguoidungId = this.$root.token.getNguoiDungID()
      var input = {
        chudekn_id: this.ChuDeKnId,
        huonggiao_id: this.HuongGiaoId,
        kyhoadon: this.kyhoadon,
        nguoidung_id: nguoidungId,
        nhanvien_id: nhanvienId,
        phanvung_id: this.PhanVungId,
        quytrinh_id: this.QuyTrinhKnId,
        ttkn_id: this.TtKhieuNaiId,
        ttph_id: this.TtPhieuId,
        tuthang: tuthang
        //,denthang: denthang
      }
      let response = await KhieuNaiApi.getDanhSachPhieus(this.axios, input)
      let data = this.GetData(response)      
      if (data && data.length > 20000) {
        this.$bvModal
        .msgBoxConfirm(`Danh sách phiếu quá nhiều: ${data.length}, bạn có muốn xuất file excel trực tiếp?`, {
          title: 'Thông báo',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            var name = 'ds_excel'
            if (this.tabIndex == 0) {
              name = 'phieu-chua-xu-ly'
            } else if (this.tabIndex == 1) {
              name = 'phieu-cho-xu-ly'
            } else if (this.tabIndex == 2) {
              name = 'phieu-khoa'
            } else if (this.tabIndex == 3) {
              name = 'phieu-bao-ton'
            }
            var exported = await this.Xuat_Excel(input, name)
            if (exported) {
              this.$toast.success('Xuất file thành công!')
            } else {
              this.$toast.error('Xuất file thất bại!')
            }
          } else {
            if (this.tabIndex == 0) {
              try {
                data.forEach(function(item) {
                  if (item.tongno != null) item.tongno = parseInt(item.tongno)
                  if (item.no_ps != null) item.no_ps = parseInt(item.no_ps)
                  if (item.no_dk != null) item.no_dk = parseInt(item.no_dk)
                })
              } catch (err) {}
              this.isCheckedHeader2=false
              this.DanhSachPhieus.datalist = data.map(x=>{
                x.ischeck='0'
                return x
              })
              this.DanhSachPhieus.selectedItems = []
            } else if (this.tabIndex == 1) {
              try {
                data.forEach(function(item) {
                  if (item.tongno != null) item.tongno = parseInt(item.tongno)
                  if (item.no_ps != null) item.no_ps = parseInt(item.no_ps)
                  if (item.no_dk != null) item.no_dk = parseInt(item.no_dk)
                })
              } catch (err) {}  
              this.isCheckedHeader3=false
              this.DsPhieuChoXuLy.datalist = data.map(x=>{
                x.ischeck='0'
                return x
              })
              this.DsPhieuChoXuLy.selectedItems = []
            } else if (this.tabIndex == 2) {
              this.DsPhieuKhoa.datalist = data
              this.DsPhieuKhoa.selectedItems = []
            } else if (this.tabIndex == 3) {
              this.DsPhieuBaoTon.datalist = data
              this.DsPhieuBaoTon.selectedItems = []
            } else {
            }
          }
        })
        .catch((e) => {
          console.log(e)
        })
      } else {
        if (this.tabIndex == 0) {
          try {
            data.forEach(function(item) {
              if (item.tongno != null) item.tongno = parseInt(item.tongno)
              if (item.no_ps != null) item.no_ps = parseInt(item.no_ps)
              if (item.no_dk != null) item.no_dk = parseInt(item.no_dk)
            })
          } catch (err) {}
          this.isCheckedHeader2=false
          this.DanhSachPhieus.datalist = data.map(x=>{
            x.ischeck='0'
            return x
          })
          this.DanhSachPhieus.selectedItems = []
        } else if (this.tabIndex == 1) {
          try {
            data.forEach(function(item) {
              if (item.tongno != null) item.tongno = parseInt(item.tongno)
              if (item.no_ps != null) item.no_ps = parseInt(item.no_ps)
              if (item.no_dk != null) item.no_dk = parseInt(item.no_dk)
            })
          } catch (err) {}  
          this.isCheckedHeader3=false
          this.DsPhieuChoXuLy.datalist = data.map(x=>{
            x.ischeck='0'
            return x
          })
          this.DsPhieuChoXuLy.selectedItems = []
        } else if (this.tabIndex == 2) {
          this.DsPhieuKhoa.datalist = data
          this.DsPhieuKhoa.selectedItems = []
        } else if (this.tabIndex == 3) {
          this.DsPhieuBaoTon.datalist = data
          this.DsPhieuBaoTon.selectedItems = []
        } else {
        }
      } 
    },
    GetDanhSachPhieuTons: async function() {
      console.log('GetDanhSachPhieuTons:')
      var inputData = [
        {
          khieunai_id: 0,
          nd_ton: 'string',
          nguyennhan: 'string',
          nhanvien_th_id: 0,
          phieukn_id: 0
        }
      ]
      let response = await KhieuNaiApi.getPhieuBaoTons(this.axios, inputData)
      this.DanhSachPhieuTons.datalist = this.GetData(response)
      // if (response.data.error === 'BSS-00000000') {
      //   this.DanhSachPhieuTons.datalist = response.data.data
      // }
    },
    GetThueBaoTheoKyHoaDon: async function(thuebao_id) {
      await this.laythangno()
      let phanvungId = this.$root.token.getPhanVungID()
      let response = await KhieuNaiApi.getDanhSachThueBaoTheoKyHoaDon(this.axios, {
        kyhoadon: this.kyhoadon,
        phan_vung_id: phanvungId,
        thuebao_id: thuebao_id
      })
      if (response.data.error === 'BSS-00000000') {
        this.DanhSachThueBao = response.data.data
      }
    },
    capNhatKhieuNai: async function () {
      let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
      this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      //this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      console.log('capNhatKhieuNai', this.DsPhieuChoXuLy.selectedItems)
      //this.DsPhieuChoXuLy.selectedItems = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      if (!this.cboKetQua_XL || this.cboKetQua_XL.toString().trim() == '') {
        this.ShowError('Chưa cập nhật kết quả xử lý !')
        return
      }
      if (!this.cboLoi || this.cboLoi.toString().trim() == '') {
        this.ShowError('Chưa cập nhật kết quả lỗi !')
        return
      }
      if (this.cboKetQua_XL == 1) {
        if (!this.txtNoiDungXL || this.txtNoiDungXL.toString().trim() == '') {
          this.ShowError('Chưa cập nhật nội dung trả lời !')
          return
        }
      } else {
        if (!this.txtNoiDungXL_Ton || this.txtNoiDungXL_Ton.toString().trim() == '') {
          this.ShowError('Chưa cập nhật nội dung trả lời !')
          return
        }
      }

      let tien_gc = 0
      let vat_gc = 0
      let ngay_gq = this.dtpNgayXL // moment(this.dtpNgayXL, this.dateFormatFull).toISOString()
      // let ngay_gq = moment().toISOString()
      var data = {
        ketquakn_id: this.cboKetQua_XL, // nhập khi dùng [capnhat_ketqua_khieunai]
        khieunai_id: this.khieunai_id,
        kqloi_id: 0, // nhập khi dùng [capnhat_khieunai_kem_loi]
        nd_ton: this.txtNoiDungXL_Ton,
        ngay_gq: ngay_gq,
        nguyennhan: this.txtNguyenNhan,
        nhanvien_gq_id: this.cboNvTH, // mã nv giải quyết kn
        noidung_gq: this.txtNoiDungXL, // nội dung
        phanvung_id: this.PhanVungId, // auto theo token
        tien_gc: tien_gc,
        vat_gc: vat_gc
      }
      this.loading(true)
      var self = this
      let arr = []
      if (this.tabIndex == 1) {
        // Cho xu ly
        if (this.cboKetQua_XL == 1) {
          if (this.DsPhieuChoXuLy.selectedItems != null && this.DsPhieuChoXuLy.selectedItems.length > 0) {
            this.DsPhieuChoXuLy.selectedItems.forEach(function(item) {
              data.kqloi_id = self.cboLoi
              data.ketquakn_id = self.cboKetQua_XL
              data.khieunai_id = item.khieunai_id
              if (self.txtTienGC != '0') {
                data.vat_gc = parseFloat(self.txtTienGC) / 10
                data.tien_gc = parseFloat(self.txtTienGC) - data.vat_gc
              }
              arr.push(data)
            })
            let response = await KhieuNaiApi.capnhatKhieuNaiKemLoi(self.axios, arr)
            self.Loading = false
            if (response.data.error_code == 'BSS-00000000') {
              self.ShowError('Cập nhật thành công !')
            } else {
              self.ShowError('Có lỗi xảy ra:' + response.data.message)
            }
          } else {
            this.ShowError(`Bạn chưa chọn khiếu nại để Cập nhật!`)
          }
        } else if (this.cboKetQua_XL == 2) {
          arr = []
          if (this.DsPhieuChoXuLy.selectedItems != null && this.DsPhieuChoXuLy.selectedItems.length > 0) {
            this.DsPhieuChoXuLy.selectedItems.forEach(async function(item) {
              data.kqloi_id = 0 // self.cboLoi
              data.ketquakn_id = self.cboKetQua_XL
              data.khieunai_id = item.khieunai_id
              data.vat_gc = 0
              data.tien_gc = 0
              arr.push(data)
            })
            let response = await KhieuNaiApi.capnhatKhieuNai(self.axios, arr)
            self.Loading = false
            if (response.data.error_code == 'BSS-00000000') {
              self.ShowSuccess('Cập nhật thành công !')
            } else {
              self.ShowError('Có lỗi xảy ra:' + response.data.message)
            }
          } else {
            this.ShowError(`Bạn chưa chọn khiếu nại để Cập nhật!`)
          }
        }
      } else if (this.tabIndex == 2) {
        arr = []
        if (this.DsPhieuKhoa.selectedItems != null && this.DsPhieuKhoa.selectedItems.length > 0) {
          if (this.DsPhieuChoXuLy.selectedItems != null && this.DsPhieuChoXuLy.selectedItems.length > 0) {
            self = this
            this.DsPhieuKhoa.selectedItems.forEach(async function(item) {
              data.kqloi_id = self.cboLoi
              data.ketquakn_id = self.cboKetQua_XL
              data.khieunai_id = item.khieunai_id
              data.vat_gc = 0
              data.tien_gc = 0
              arr.push(data)
            })
            let response = await KhieuNaiApi.capnhatKetQuaKhieuNai(self.axios, arr)
            self.Loading = false
            if (response.data.error_code == 'BSS-00000000') {
              self.ShowError('Cập nhật thành công !')
            } else {
              self.ShowError('Có lỗi xảy ra:' + response.data.message)
            }
          } else {
            this.ShowError(`Bạn chưa chọn khiếu nại để Cập nhật!`)
          }
        }
      } else {
      }
      this.loading(false)
    },
    capnhatTrangThaiKhieuNai: async function() {
      if (!this.cboKetQua_XL || this.cboKetQua_XL.toString().trim() == '') {
        this.ShowError('Chưa cập nhật kết quả xử lý !')
        return
      }
      if (!this.cboLoi || this.cboLoi.toString().trim() == '') {
        this.ShowError('Chưa cập nhật kết quả lỗi !')
        return
      }
      let userName = this.$root.token.getUserName()
      let response = await KhieuNaiApi.capnhatTrangThaiKhieuNai(this.axios, {
        may_cn: 'web', // auto theo token
        nguoi_cn: userName, // auto theo token
        phanvung_id: this.PhanVungId, // auto theo token
        phieukn_id: this.khieunai_id,
        ttph_id: this.ttkn_id
      })
      if (response.data.error_code === 'BSS-00000000') {
        this.ShowError('Cập nhật thành công !')
      } else {
        this.ShowError('Có lỗi xảy ra:' + response.data.message)
      }
    },

    ChonChuDe: async function(i, subject) {
      console.log('ChonChuDe', i, subject)
      if (subject != null && subject.chudekn_id > 0) {
        this.ChuDeKnId = subject.chudekn_id
        this.$refs.gridChoXuLy.pageIndex = 0
        this.$refs.gridKhoaPhieu.pageIndex = 0
        this.currentSaveIndex = 0
        this.DanhSachChuDe.selectedItem = subject
        try {
          this.loading(true)
          this.Reset()
          await this.HT_DS_KHIEUNAI(this.tabIndex)
        } catch (err) {
          console.log('ChuDeKnId', err)
        } finally {
          this.loading(false)
        }
      }
    },
    gridChuDe_DataBound: function(args) {
      if (this.selIndex.length > 0) {
        this.$refs.gridChuDe.grid.selectRows(this.selIndex)
        this.selIndex = []
      }
    },
    gridChuDe_RowDataBound(args) {
      if (args.data['chudekn_id'] == this.ChuDeKnId) {
        this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
      }
    },
    Reset() {
      this.DsPhieuChoXuLy.data = []
      this.DsPhieuBaoTon.data = []
      this.DsPhieuKhoa.data = []
      this.DanhSachPhieus.data = []
      this.Clear()
    },
    GiaoXuLy: async function () {
      let dsFilter= this.$refs.gridChuaXuLy.getDatafiltered()
      this.DanhSachPhieus.selectedItems=this.DanhSachPhieus.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      //this.DanhSachPhieus.selectedItems=this.DanhSachPhieus.datalist.filter(x=>x.ischeck=='1')
      console.log('GiaoXuLy', this.DanhSachPhieus.selectedItems)
      if (this.DanhSachPhieus.selectedItems.length <= 0) {
        this.ShowError('Bạn chưa chọn phiếu xử lý ')
        return
      }
      let phieuIds = []
      this.DanhSachPhieus.selectedItems.forEach(async function(item) {
        phieuIds.push(item.phieukn_id.toString())
      })
      this.loading(true)
      let response = await KhieuNaiApi.capNhatGiaoXuLy(this.axios, {
        nhanvien_giaiquyet_id: parseInt(this.cboNvTH),
        phieukn_ids: phieuIds
      })
      this.loading(false)
      if (response.data.error_code === 'BSS-00000000') {
        this.ShowSuccess('Cập nhật thành công !')
        // this.tabIndex = 1
      } else {
        this.ShowError('Có lỗi xảy ra:' + response.data.message)
      }
      this.loading(false)
    },
    async tsbtnGiaoXL_Click() {
      try {
        this.loading(true)
        await this.fn_daily_xlkn_giaoxl('list')
        this.loading(false)
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err.message}`)
      }
    },
    async tsbtnGiaoXLAll_Click() {
      try {
        this.loading(true)
        await this.fn_daily_xlkn_giaoxl('all')
        this.loading(false)
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err.message}`)
      }
    },
    async fn_daily_xlkn_giaoxl(type) {
      console.log('fn_daily_xlkn_giaoxl kt_totruong', this.kt_totruong)
      // return 
      let phieus = []
      if (type == 'all') {
        if (this.DanhSachPhieus.datalist && this.DanhSachPhieus.datalist.length > 10000) {
          this.$toast.info('Số lượng phiếu quá nhiều, chương trình sẽ xử lý mỗi lần 10000 phiếu')
        }
        phieus = this.DanhSachPhieus.datalist.filter((item, index) => index < 10000)
      } else {
        let dsFilter = this.$refs.gridChuaXuLy.getDatafiltered()
        this.DanhSachPhieus.selectedItems=this.DanhSachPhieus.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
        phieus = this.DanhSachPhieus.selectedItems
      }
      
      if (phieus == null || phieus.length == 0) {
        this.ShowError('Bạn chưa chọn khiếu nại để giao xử lý !')
        return
      }
      if (!this.kt_totruong) {
        let tbs = phieus.filter((x) => x.kieu != '0')
        if (tbs != null && tbs.length > 0) {
          let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
          this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
          return
        }
      }
      let nhanvienId = this.$root.token.getNhanVienID()
      let ip = '127.0.0.1'
      let ma_nd = this.$root.token.getUserName()
      let may_cn = 'web'
      let js_gridviewkhieunai = phieus.map((x) => ({
        CHON: 1,
        KHIEUNAI_ID: x.khieunai_id,
        PHIEUKN_ID: x.phieukn_id
      }))
      let ds_para = {
        nhanvien_id: parseInt(nhanvienId),
        nhanvien_th_id: parseInt(this.cboNvTH),
        ip: ip,
        ma_nd: ma_nd,
        may_cn: may_cn,
        js_gridviewkhieunai: js_gridviewkhieunai,
        // selected_tab:this.tabIndex,
        // kqxl_id:this.cboKetQua_XL,
        // ngay_xl:moment(this.dtpNgayXL, 'DD/MM/YYYY HH:mm:ss').toISOString(),
        // noidung_xl:this.cboKetQua_XL==1?this.txtNoiDungXL.trim():this.txtNoiDungXL_Ton.trim(),
        // tien_gc:this.txtTienGC.trim(),
        // vat_gc:'0',
        // loi_id:this.cboLoi,
        // js_ds3:
      }
      let input = { ds_para: JSON.stringify(ds_para) }
      let response = await KhieuNaiApi.fn_daily_xlkn_giaoxl(this.axios, input)
      if (response.data.error_code === 'BSS-00000000') {
        this.ShowSuccess('Giao xử lý khiếu nại thành công!')
        // this.tabIndex = 1
        await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
      } else {
        this.ShowError('Có lỗi xảy ra:' + response.data.message)
      }
    },
    async tsbtnGhiLai_Click() {
      let i = this.currentIndex
      try {
        if (!this.Kiemtra_dulieu()) return
        this.loading(true)
        this.currentSave = true
        this.currentSaveIndex = i
        console.log('tsbtnGhiLai_Click-currentSaveIndex', this.currentSaveIndex)
        await this.fn_daily_xlkn_capnhat('list')
        this.loading(true)
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err.message}`)
      } finally {
        this.currentSaveIndex = i
        this.loading(false)
      }
    },
    async tsbtnGhiLaiAll_Click() {
      let i = this.currentIndex
      try {
        if (!this.Kiemtra_dulieu()) return
        this.loading(true)
        this.currentSave = true
        this.currentSaveIndex = i
        console.log('tsbtnGhiLai_Click-currentSaveIndex', this.currentSaveIndex)
        await this.fn_daily_xlkn_capnhat('all')
        this.loading(true)
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err.message}`)
      } finally {
        this.currentSaveIndex = i
        this.loading(false)
      }
    },
    Kiemtra_dulieu() {
      if (this.txtNguyenNhan != '' && this.txtNguyenNhan != null && this.txtNguyenNhan.length > 1000) {
        this.ShowError(`Nguyên nhân lỗi nhập quá độ dài cho phép .`)
        this.$refs.reftxtNguyenNhan.focus()
        return false
      }
      if (this.cboKetQua_XL == 1) {
        if (!this.txtNoiDungXL || this.txtNoiDungXL.toString().trim() == '') {
          this.ShowError('Bạn chưa nhập nội dung xử lý khiếu nại !')
          this.$refs.reftxtNoiDungXL.focus()
          return false
        }
        // if (this.txtTienGC == '') {
        //   this.ShowError('Bạn chưa nhập tiền giảm cước !')
        //   return false
        // }
      } else if (this.cboKetQua_XL == 2) {
        if (!this.txtNoiDungXL_Ton || this.txtNoiDungXL_Ton.toString().trim() == '') {
          this.ShowError('Bạn chưa nhập nội dung tồn !')
          this.$refs.reftxtNoiDungXL_Ton.focus()
          return false
        }
      }
      // if (this.txtNoiDungXL != '' && this.txtNoiDungXL != null && this.txtNoiDungXL.length > this.txtNoiDungXL_MaxLength) {
      //   if (this.cboKetQua_XL == '1') {
      //     this.ShowError(`Nội dung xử lý nhập quá độ dài cho phép .`)
      //   } else {
      //     this.ShowError(`Nội dung tồn nhập quá độ dài cho phép .`)
      //   }
      //   this.$refs.reftxtNoiDungXL.focus()
      //   return false
      // }

      return true
    },
    async fn_daily_xlkn_capnhat(type) {
      var ds_choxuly = []
      var ds_phieukhoa = []
      if (type == 'all') {
        ds_choxuly = this.DsPhieuChoXuLy.datalist
        ds_phieukhoa = this.DsPhieuKhoa.datalist.map((x) => ({ CHON: 1, KHIEUNAI_ID: x.khieunai_id }))
      } else {
        let dsFilter_choxl = this.$refs.gridChoXuLy.getDatafiltered()
        this.DsPhieuChoXuLy.selectedItems = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1' && dsFilter_choxl.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
        ds_choxuly = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1' && dsFilter_choxl.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
        
        ds_phieukhoa = this.DsPhieuKhoa.selectedItems.map((x) => ({ CHON: 1, KHIEUNAI_ID: x.khieunai_id }))
      }
      
      let js_gridview_choxl = ds_choxuly
      let js_ds3 = ds_phieukhoa
      let ds_para = {
        selected_tab: this.tabIndex,
        kqxl_id: this.cboKetQua_XL,
        ngay_xl: moment(this.dtpNgayXL, 'DD/MM/YYYY HH:mm:ss').toISOString(),
        nhanvien_th_id: this.cboNvTH,
        noidung_xl: this.txtNoiDungXL,
        nguyennhan: this.txtNguyenNhan,
        tien_gc: this.cboKetQua_XL == '1' ? parseFloat(this.txtTienGC) - parseFloat(this.txtTienGC) / 10 : 0,
        vat_gc: this.cboKetQua_XL == '1' ? parseFloat(this.txtTienGC) / 10 : 0,
        loi_id: this.cboLoi,
        js_gridview_choxl: this.tabIndex == 1 ? js_gridview_choxl.map((x) => ({ CHON: 1, KHIEUNAI_ID: x.khieunai_id })) : [],
        js_ds3: this.tabIndex == 2 ? js_ds3 : []
      }
      let input = { ds_para: JSON.stringify(ds_para) }
      let response = await KhieuNaiApi.fn_daily_xlkn_capnhat(this.axios, input)
      if (response.data.error_code === 'BSS-00000000') {
        if (response.data.data.ret == 1) {
          this.ShowSuccess('Cập nhật thành công !')
          try {
            await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
            this.cboKetQuaXL_Change(this.cboKetQua_XL)
            if (this.tabIndex == 2) {
              //  this.$refs.gridKhoaPhieu.selectRow(this.currentIndex, true)
            } else {
              // this.$refs.gridChoXuLy.selectRow(this.currentIndex, true)
            }
          } catch (err) {
            console.log('gridKhoaPhieu-gridChoXuLy', err)
          }

          return true
        } else {
          this.ShowError(response.data.data.js_return)
          return false
        }
      } else {
        this.ShowError('Có lỗi xảy ra:' + response.data.message)
        return false
      }
    },
    async fn_daily_xlkn_huygiao() {
      let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
      this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      
      let selectedItems = this.DsPhieuChoXuLy.selectedItems
      if (selectedItems == null || selectedItems.length == 0) {
        this.ShowError(`Bạn chưa chọn khiếu nại để hủy giao !`)
        return
      }
      if (!this.kt_totruong) {
        let tbs = this.DsPhieuChoXuLy.selectedItems.filter((x) => x.kieu != '0')
        if (tbs != null && tbs.length > 0) {
          let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
          this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
          return
        }
      }
      let phieus = selectedItems.map((x) => ({ CHON: 1, KIEU: x.kieu, PHIEUKN_ID: x.phieukn_id, MA_TB: x.ma_tb }))
      let ds_param = { js_gridview_choxl: phieus, totruong: this.kt_totruong ? 1 : 0 }
      let input = { ds_para: JSON.stringify(ds_param) }
      let response = await KhieuNaiApi.fn_daily_xlkn_huygiao(this.axios, input)
      if (response.data.error_code === 'BSS-00000000') {
        //this.tabIndex = 1
        if (response.data.data.ret == 1) {
          this.ShowSuccess('Hủy giao phiếu khiếu nại thành công! ')
          this.DsPhieuChoXuLy.selectedItems = []
          return true
        } else {
          this.ShowError(response.data.data.js_return)
        }
        return false
      } else {
        this.ShowError('Có lỗi xảy ra:' + response.data.message)
      }
      return false
    },
    async tsbtnHuyGiao_Click() {
      try {
        this.loading(true)
        if (await this.fn_daily_xlkn_huygiao()) {
          // await this.GetDanhSachPhieus(1)
          await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
        }
        this.loading(false)
      } catch (err) {
        this.loading(false)
        this.ShowError(`${err.message}`)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnKhoaPhieu_Click() {
      try {
        let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
        this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
        let may_cn = 'web'
        let ngay_cn = moment().format('DD/MM/YYYY')
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let ma_nd = this.$root.token.getUserName()
        let phieus = this.DsPhieuChoXuLy.selectedItems
        if (phieus == null || phieus.length == 0) {
          this.ShowError('Bạn chưa chọn khiếu nại để khóa phiếu !')
          return
        }
        if (!this.kt_totruong) {
          let tbs = this.DsPhieuChoXuLy.selectedItems.filter((x) => x.kieu != '0')
          if (tbs != null && tbs.length > 0) {
            let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
            this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
            return
          }
        }
        let phieuChuaCoNDGQ = phieus.filter((x) => x.noidung_gq == '' || x.noidung_gq == null)
        if (phieuChuaCoNDGQ != null && phieuChuaCoNDGQ.length > 0) {
          let snoidung_gqs = phieuChuaCoNDGQ.map((x) => x.ma_tb).join(',')
          this.ShowError(`Khiếu nại của thuê bao: ${snoidung_gqs}  chưa được cập nhật nội dung giải quyết! `)
          return
        }
        // var ds_phieu_xly_khongduoc = phieus.filter(e => (!e['ketqua_xl'] || Number(e['ketqua_xl']) == 2))
        // if (ds_phieu_xly_khongduoc && ds_phieu_xly_khongduoc.length > 0) {
        //   this.$toast.info('Danh sách phiếu chứa phiếu chưa cập nhật kết quả xử lý và phiếu có kết quả xử lý: 2 - không xử lý được, chương trình sẽ bỏ qua không khóa những phiếu này!')
        // }
        // var ds_phieu_xly_duoc = phieus.filter(e => (Number(e['ketqua_xl']) == 1))
        this.loading(true)
        let khoaphieu = {
          js_gridview_choxl: phieus.map((x) => ({
            CHON: 1,
            KHIEUNAI_ID: x.khieunai_id,
            PHIEUKN_ID: x.phieukn_id,
            NGUYENNHAN_KN: x.nguyennhan_kn,
            NOIDUNG_GQ: x.noidung_gq == null ? '' : x.noidung_gq,
            NHANVIEN_TH_ID: x.nhanvien_th_id == null ? x.nhanvien_gq_id : x.nhanvien_th_id
          })),
          quytrinh_id: parseInt(this.QuyTrinhKnId),
          ma_nd: ma_nd,
          may_cn: may_cn,
          ngay_cn: moment().toISOString(),
          donvi_id: parseInt(donviId),
          nhanvien_id: parseInt(nhanvienId)
        }

        let input = { ds_para: JSON.stringify(khoaphieu) }
        let response = await KhieuNaiApi.fn_daily_xlkn_khoaphieu(this.axios, input)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data.ret == 1) {
            this.ShowSuccess('Khóa phiếu thành công!')
            await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
            return true
          } else {
            this.ShowError(response.data.data.js_return)
            return false
          }
        } else {
          this.ShowError('Có lỗi xảy ra:' + response.data.message)
        }
        return false
      } catch (err) {
        this.ShowError(`Có lỗi : ${err.message}`)
        this.loading(false)
      } finally {
        this.loading(false)
      }
      return false
    },
    tsbtnKhoaPhieuAll_Click: async function () {
      try {
        let may_cn = 'web'
        let ngay_cn = moment().format('DD/MM/YYYY')
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let ma_nd = this.$root.token.getUserName()
        if (this.DsPhieuChoXuLy.datalist && this.DsPhieuChoXuLy.datalist.length > 10000) {
          this.$toast.info('Số lượng phiếu quá nhiều, hệ thống sẽ xử lý mỗi lần 10000 phiếu!')
        }
        let phieus = this.DsPhieuChoXuLy.datalist.filter((item, index) => index < 10000)
        if (phieus == null || phieus.length == 0) {
          this.ShowError('Bạn chưa chọn khiếu nại để khóa phiếu !')
          return
        }
        if (!this.kt_totruong) {
          let tbs = phieus.filter((x) => x.kieu != '0')
          if (tbs != null && tbs.length > 0) {
            let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
            this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
            return
          }
        }
        let phieuChuaCoNDGQ = phieus.filter((x) => x.noidung_gq == '' || x.noidung_gq == null)
        if (phieuChuaCoNDGQ != null && phieuChuaCoNDGQ.length > 0) {
          let snoidung_gqs = phieuChuaCoNDGQ.map((x) => x.ma_tb).join(',')
          this.ShowError(`Khiếu nại của thuê bao: ${snoidung_gqs}  chưa được cập nhật nội dung giải quyết! `)
          return
        }
        // var ds_phieu_xly_khongduoc = phieus.filter(e => (!e['ketqua_xl'] || Number(e['ketqua_xl']) == 2))
        // if (ds_phieu_xly_khongduoc && ds_phieu_xly_khongduoc.length > 0) {
        //   this.$toast.info('Danh sách phiếu chứa phiếu chưa cập nhật kết quả xử lý và phiếu có kết quả xử lý: 2 - không xử lý được, chương trình sẽ bỏ qua không khóa những phiếu này!')
        // }
        // var ds_phieu_xly_duoc = phieus.filter(e => (Number(e['ketqua_xl']) == 1))
        this.loading(true)
        let khoaphieu = {
          js_gridview_choxl: phieus.map((x) => ({
            CHON: 1,
            KHIEUNAI_ID: x.khieunai_id,
            PHIEUKN_ID: x.phieukn_id,
            NGUYENNHAN_KN: x.nguyennhan_kn,
            NOIDUNG_GQ: x.noidung_gq == null ? '' : x.noidung_gq,
            NHANVIEN_TH_ID: x.nhanvien_th_id == null ? x.nhanvien_gq_id : x.nhanvien_th_id
          })),
          quytrinh_id: parseInt(this.QuyTrinhKnId),
          ma_nd: ma_nd,
          may_cn: may_cn,
          ngay_cn: moment().toISOString(),
          donvi_id: parseInt(donviId),
          nhanvien_id: parseInt(nhanvienId)
        }

        let input = { ds_para: JSON.stringify(khoaphieu) }
        let response = await KhieuNaiApi.fn_daily_xlkn_khoaphieu(this.axios, input)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data.ret == 1) {
            this.ShowSuccess('Khóa phiếu thành công!')
            await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
            return true
          } else {
            this.ShowError(response.data.data.js_return)
            return false
          }
        } else {
          this.ShowError('Có lỗi xảy ra:' + response.data.message)
        }
        return false
      } catch (err) {
        this.ShowError(`Có lỗi : ${err.message}`)
        this.loading(false)
      } finally {
        this.loading(false)
      }
      return false
    },
    tsbtnKhoaPhieuAll_Click: async function () {
      try {
        let may_cn = 'web'
        let ngay_cn = moment().format('DD/MM/YYYY')
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let ma_nd = this.$root.token.getUserName()
        if (this.DsPhieuChoXuLy.datalist && this.DsPhieuChoXuLy.datalist.length > 10000) {
          this.$toast.info('Số lượng phiếu quá nhiều, hệ thống sẽ xử lý mỗi lần 10000 phiếu!')
        }
        let phieus = this.DsPhieuChoXuLy.datalist.filter((item, index) => index < 10000)
        if (phieus == null || phieus.length == 0) {
          this.ShowError('Bạn chưa chọn khiếu nại để khóa phiếu !')
          return
        }
        if (!this.kt_totruong) {
          let tbs = phieus.filter((x) => x.kieu != '0')
          if (tbs != null && tbs.length > 0) {
            let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
            this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
            return
          }
        }
        let phieuChuaCoNDGQ = phieus.filter((x) => x.noidung_gq == '' || x.noidung_gq == null)
        if (phieuChuaCoNDGQ != null && phieuChuaCoNDGQ.length > 0) {
          let snoidung_gqs = phieuChuaCoNDGQ.map((x) => x.ma_tb).join(',')
          this.ShowError(`Khiếu nại của thuê bao: ${snoidung_gqs}  chưa được cập nhật nội dung giải quyết! `)
          return
        }
        this.loading(true)
        let khoaphieu = {
          js_gridview_choxl: phieus.map((x) => ({
            CHON: 1,
            KHIEUNAI_ID: x.khieunai_id,
            PHIEUKN_ID: x.phieukn_id,
            NGUYENNHAN_KN: x.nguyennhan_kn,
            NOIDUNG_GQ: x.noidung_gq == null ? '' : x.noidung_gq,
            NHANVIEN_TH_ID: x.nhanvien_th_id == null ? x.nhanvien_gq_id : x.nhanvien_th_id
          })),
          quytrinh_id: parseInt(this.QuyTrinhKnId),
          ma_nd: ma_nd,
          may_cn: may_cn,
          ngay_cn: moment().toISOString(),
          donvi_id: parseInt(donviId),
          nhanvien_id: parseInt(nhanvienId)
        }

        let input = { ds_para: JSON.stringify(khoaphieu) }
        let response = await KhieuNaiApi.fn_daily_xlkn_khoaphieu(this.axios, input)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data.ret == 1) {
            this.ShowSuccess('Khóa phiếu thành công!')
            await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
            return true
          } else {
            this.ShowError(response.data.data.js_return)
            return false
          }
        } else {
          this.ShowError('Có lỗi xảy ra:' + response.data.message)
        }
        return false
      } catch (err) {
        this.ShowError(`Có lỗi : ${err.message}`)
        this.loading(false)
      } finally {
        this.loading(false)
      }
      return false
    },
    async tsbtnTon_Click() {
      try {
        //this.DsPhieuChoXuLy.selectedItems = this.$refs.gridChoXuLy.getSelectedRecords()
        //this.DsPhieuChoXuLy.selectedItems = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
        let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
      this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      //this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
        let may_cn = 'web'
        let ngay_cn = moment().format('DD/MM/YYYY')
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        let ma_nd = this.$root.token.getUserName()
        let phieus = this.DsPhieuChoXuLy.selectedItems
        if (phieus == null || phieus.length == 0) {
          this.ShowError('Bạn chưa chọn khiếu nại để báo tồn !')
          return
        }
        let phieuChuaCoNDTon = phieus.filter((x) => x.nd_ton == '' || x.nd_ton == null)
        if (phieuChuaCoNDTon != null && phieuChuaCoNDTon.length > 0) {
          let snoidung_tons = phieuChuaCoNDTon.map((x) => x.ma_tb).join(',')
          this.ShowError(`Khiếu nại của thuê bao:  ${snoidung_tons}  chưa được cập nhật nội dung tồn! `)
          return
        }
        if (!this.kt_totruong) {
          let tbs = this.DsPhieuChoXuLy.selectedItems.filter((x) => x.kieu != '0')
          if (tbs != null && tbs.length > 0) {
            let skhieunai_kts = tbs.map((x) => x.ma_tb).join()
            this.ShowError(`Thuê bao : ${skhieunai_kts} đã vượt quá thời hạn xử lý khiếu nại. Bạn không được quyền thao tác !`)
            return
          }
        }

        this.loading(true)
        let khoaphieu = {
          js_gridview_choxl: phieus.map((x) => ({ CHON: 1, KHIEUNAI_ID: x.khieunai_id, PHIEUKN_ID: x.phieukn_id, NGUYENNHAN_KN: x.nguyennhan_kn, ND_TON: x.nd_ton, NHANVIEN_TH_ID: x.nhanvien_th_id == null ? x.nhanvien_gq_id : x.nhanvien_th_id })),
          quytrinh_id: parseInt(this.QuyTrinhKnId),
          ma_nd: ma_nd,
          may_cn: may_cn,
          ngay_cn: ngay_cn,
          donvi_id: parseInt(donviId),
          nhanvien_id: parseInt(nhanvienId)
        }
        this.loading(true)
        let input = { ds_para: JSON.stringify(khoaphieu) }
        let response = await KhieuNaiApi.fn_daily_xlkn_ton(this.axios, input)
        if (response.data.error_code === 'BSS-00000000') {
          if (response.data.data.ret == 1) {
            this.ShowSuccess('Báo tồn khiếu nại thành công!')
            await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
            return true
          } else {
            this.ShowError(response.data.data.js_return)
            return false
          }
        } else {
          this.ShowError('Có lỗi xảy ra:' + response.data.message)
        }
        this.loading(false)
      } catch (err) {
        this.loading(false)
        this.loading(false)
        this.ShowError(`${err.message}`)
      } finally {
        this.loading(false)
      }
      return false
    },
    async tsbtnHSKN_Click() {
      // Hiển thị “Quản lý hồ sơ khiếu nại” – (Tham chiếu UR4.1.005_Mục 1.1.7)
      // this.ShowError(`Hiển thị Quản lý hồ sơ khiếu nại (Tham chiếu UR4.1.005_Mục 1.1.7) , chưa thực hiện`)
      this.$refs.frmHoSoKhieuNai.khieunai_id = this.khieunai_id
      this.$refs.frmHoSoKhieuNai.show()
    },
    async tsbtnInPhieu_Click() {
      if (this.DsPhieuChoXuLy.datalist == null || this.DsPhieuChoXuLy.datalist == 0) {
        this.ShowError('Bạn chưa có DS khiếu nại để in!')
        return
      }
      //this.DsPhieuChoXuLy.selectedItems = this.$refs.gridChoXuLy.getSelectedRecords()
      //this.DsPhieuChoXuLy.selectedItems = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
      this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      //this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      let items = this.DsPhieuChoXuLy.selectedItems
      if (items == null || items.length == 0) {
        this.ShowError('Bạn chưa chọn DS khiếu nại để in!')
        return
      }
      this.dsGiaTri = []
      let vdskn_id = items.map((x) => x.khieunai_id).join(',')
      let vkycuoc = this.kyhoadon
      this.dsGiaTri.push(vkycuoc)
      this.dsGiaTri.push(vdskn_id)
      if (this.inMauMoi == '1') {
        console.log('this.Xuat_BC(this.PHIEU_TB_TT)')
        this.Xuat_BC(this.PHIEU_TB_TT, this.dsGiaTri)
      } else {
        console.log('this.PHIEU_KYNHAN_KN')
        this.Xuat_BC(this.PHIEU_KYNHAN_KN, this.dsGiaTri)
      }
    },
    async tsbtnInPhieuTest_Click() {
      this.dsGiaTri = []
      //this.DsPhieuChoXuLy.selectedItems = this.$refs.gridChoXuLy.getSelectedRecords()
      //this.DsPhieuChoXuLy.selectedItems = this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      let dsFilter= this.$refs.gridChoXuLy.getDatafiltered()
      this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1'&&dsFilter.findIndex(i=>i.khieunai_id==x.khieunai_id)>-1)
      //this.DsPhieuChoXuLy.selectedItems=this.DsPhieuChoXuLy.datalist.filter(x=>x.ischeck=='1')
      let items = this.DsPhieuChoXuLy.selectedItems
      //let vdskn_id = items.map((x) => x.khieunai_id).join(',')
      let vdskn_id = '6000183'
      let vkycuoc = this.kyhoadon
      this.dsGiaTri.push(vkycuoc)
      this.dsGiaTri.push(vdskn_id)
      if (this.inMauMoi == '1') {
        console.log('this.Xuat_BC(this.PHIEU_TB_TT)')
        this.Xuat_BC(this.PHIEU_TB_TT, this.dsGiaTri)
      } else {
        console.log('this.PHIEU_KYNHAN_KN')
        this.dsGiaTri.push()
        this.Xuat_BC(this.PHIEU_KYNHAN_KN, this.dsGiaTri)
      }
    },
    Xuat_BC(mabc, data) {
      console.log('🚀 ~ file: ResovleComplaintAfterSales.vue ~ line 1805 ~ Xuat_BC ~ mabc, data', mabc, data)
      try {
        this.ma_bc = mabc
        this.$refs.refXemBaoCao.ma_bc = mabc
        this.$refs.refXemBaoCao.dsGiaTri = data
      } catch (err) {
        console.log('🚀 ~ file: ResovleComplaintAfterSales.vue ~ line 1810 ~ Xuat_BC ~ err', err)
      } finally {
        try {
          this.$refs.refXemBaoCao.XemNgay()
        } catch (err2) {
          this.ShowError(`${err2}`)
        }
      }
    },
    // KhoaPhieuSelectDate
    async btnChon_Click() {
      try {
        this.loading(true)
        await this.GetDanhSachPhieus(2)
        let to = moment('01/' + this.DsPhieuKhoa.To, 'DD/MM/YYYY').add(1, 'M')
        // 2022-03-24 17:00:00
        this.DsPhieuKhoa.datalist = this.DsPhieuKhoa.datalist.filter((x) => moment(x.ngay_gq, 'YYYY-MM-DD HH:mm:ss') <= to)
        this.loading(false)
      } catch (err) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnXuatExcel_Click() {
      try {
        let data = []
        if (this.tabIndex == 0) {
          data = this.DanhSachPhieus.datalist
        } else if (this.tabIndex == 1) {
          data = this.DsPhieuChoXuLy.datalist
        } else if (this.tabIndex == 2) {
          data = this.DsPhieuKhoa.datalist
        } else if (this.tabIndex == 3) {
          data = this.DsPhieuBaoTon.datalist
        }
        if (data == null || data.length == 0) {
          this.ShowError('Không có dữ liệu để xuất Excel !')
          return
        }
        this.exportData = data
        this.$refs.exportDataModal.showModal()
        // this.$refs.dialogLayDuLieuBC.openDialog(data)
      } catch (err) {
        this.ShowError(`${err.message}`)
      }
    },
    async tsbtnXem_Click() {
      try {
        this.loading(true)
        await this.GetDanhSachPhieus(this.tabIndex)
        this.loading(false)
      } catch (err) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    async btn_ExportExcel_Click(type) {
      try {
        this.loading(true)
        this.$toast.info('Đang thực hiện xuất file!')
        switch (type) {
          case "gridChuaXuLy":
            if (this.DanhSachPhieus.datalist != null && this.DanhSachPhieus.datalist.length > 0) {
              let headers = this.DanhSachPhieus.headers2
              let newData = []
              for (const item of this.DanhSachPhieus.datalist) {
                let newItem = {}
                for (const header of headers) {
                  newItem[header.headerText] = item[header.fieldName]
                }
                newData.push(newItem)
              }
              // let data = XLSX.utils.json_to_sheet(this.DanhSachPhieus.datalist)
              let data = XLSX.utils.json_to_sheet(newData)
              let wb = XLSX.utils.book_new() // make Workbook of Excel
              XLSX.utils.book_append_sheet(wb, data, 'phieu-chua-xu-ly') // sheetAName is name of Worksheet
              // export Excel file
              XLSX.writeFile(wb, 'Phieu-Chua-Xu-Ly.xlsx')
            } else {
              this.ShowError('Không có dữ liệu!')
            }
            break;

          case "gridChoXuLy":
            if (this.DsPhieuChoXuLy.datalist != null && this.DsPhieuChoXuLy.datalist.length > 0) {
              let headers = this.DsPhieuChoXuLy.headers
              let newData = []
              for (const item of this.DsPhieuChoXuLy.datalist) {
                let newItem = {}
                for (const header of headers) {
                  newItem[header.headerText] = item[header.fieldName]
                }
                newData.push(newItem)
              }
              // let data = XLSX.utils.json_to_sheet(this.DsPhieuChoXuLy.datalist)
              let data = XLSX.utils.json_to_sheet(newData)
              let wb = XLSX.utils.book_new() // make Workbook of Excel
              XLSX.utils.book_append_sheet(wb, data, 'phieu-cho-xu-ly') // sheetAName is name of Worksheet
              // export Excel file
              XLSX.writeFile(wb, 'Phieu-Cho-Xu-Ly.xlsx')
            } else {
              this.ShowError('Không có dữ liệu!')
            }
            break;
        
          case "gridKhoaPhieu":
            if (this.DsPhieuKhoa.datalist != null && this.DsPhieuKhoa.datalist.length > 0) {
              let headers = this.DsPhieuKhoa.headers
              let newData = []
              for (const item of this.DsPhieuKhoa.datalist) {
                let newItem = {}
                for (const header of headers) {
                  newItem[header.headerText] = item[header.fieldName]
                }
                newData.push(newItem)
              }
              // let data = XLSX.utils.json_to_sheet(this.DsPhieuKhoa.datalist)
              let data = XLSX.utils.json_to_sheet(newData)
              let wb = XLSX.utils.book_new() // make Workbook of Excel
              XLSX.utils.book_append_sheet(wb, data, 'phieu-khoa') // sheetAName is name of Worksheet
              // export Excel file
              XLSX.writeFile(wb, 'Phieu-Khoa.xlsx')
            } else {
              this.ShowError('Không có dữ liệu!')
            }
            break;

          case "gridBaoTon":
            if (this.DsPhieuBaoTon.datalist != null && this.DsPhieuBaoTon.datalist.length > 0) {
              let headers = this.DsPhieuBaoTon.headers
              let newData = []
              for (const item of this.DsPhieuBaoTon.datalist) {
                let newItem = {}
                for (const header of headers) {
                  newItem[header.headerText] = item[header.fieldName]
                }
                newData.push(newItem)
              }
              // let data = XLSX.utils.json_to_sheet(this.DsPhieuBaoTon.datalist)
              let data = XLSX.utils.json_to_sheet(newData)
              let wb = XLSX.utils.book_new() // make Workbook of Excel
              XLSX.utils.book_append_sheet(wb, data, 'phieu-bao-ton') // sheetAName is name of Worksheet
              // export Excel file
              XLSX.writeFile(wb, 'Phieu-Bao-Ton.xlsx')
            } else {
              this.ShowError('Không có dữ liệu!')
            }
            break;
        }
      } catch (e) {
        console.log('btn_ExportExcel_Click', e)
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_huong_giao(quytrinhid) {
      // let data = this.GetData(
      //   await KhieuNaiApi.lay_dulieu_bang_theo_dieukien(this.axios, {
      //     schema: 'css',
      //     table_name: 'huonggiao a, css.huonggiao_ldv b',
      //     list_of_cols: 'b.luong_id, a.huonggiao_id, a.huonggiao',
      //     where: `and a.quytrinh_id = ${quytrinhid}  and  a.ttkn_id in (${this.dsttkn_id})`,
      //     order: ''
      //   })
      // )
      let data = null
      // try {
      //   let phanvungID = this.$root.token.getPhanVungID()
      //   data = this.GetData(
      //     await KhieuNaiApi.lay_thongtin_table(this.axios, {
      //       table: ' css.huonggiao a, css.huonggiao_ldv b',
      //       result: ' b.luong_id, a.huonggiao_id, a.huonggiao',
      //       where: `  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id  and a.phanvung_id = ${phanvungID}  and a.quytrinh_id = ${quytrinhid}  and  a.ttkn_id in (${this.dsttkn_id})`
      //     })
      //   )
      // } catch (err) {}
      if (data == null || data.length == 0) {
        console.log('get huong giao v2')
        data = this.GetData(
          await KhieuNaiApi.lay_ds_huong_giao(this.axios, {
            quytrinhid: quytrinhid,
            ttknId: this.TtKhieuNaiId
          })
        )
      }
      return data
    },
    checkBoxChuaXuLyChange(args){
      if (args.target.className=='e-checkselectall') { // click check/uncheck all
        console.log('checkBoxChuaXuLyChange', args)
        let dsFilter = this.$refs.gridChuaXuLy.filteredDataSource || []

        console.log('checkBoxChuaXuLyChange dsFilter', dsFilter)

        for(let i=0;i<dsFilter.length;i++){
          const index=this.DanhSachPhieus.datalist.findIndex(x=>x.khieunai_id==dsFilter[i].khieunai_id)
          if(index>-1){
            if(args.checked){
              this.DanhSachPhieus.datalist[index].ischeck='1'
            }else{
              this.DanhSachPhieus.datalist[index].ischeck='0'
            }
            
          }else{
            this.DanhSachPhieus.datalist[index].ischeck='0'
          }
        }
        console.log('checkBoxChuaXuLyChange',  this.DanhSachPhieus.datalist)
      }
    },
    dsChuaXLRowDeselected(args){
      console.log('dsChuaXLRowDeselected', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        // this.updateDSKhoaSelected(args.data)
        console.log('dsChuaXLRowDeselected', args)
        const index = this.DanhSachPhieus.datalist.findIndex(x => x.khieunai_id == args.data.khieunai_id)
        this.DanhSachPhieus.datalist[index].ischeck='0'
      }
    },
    dataBoundChuaXuLy(args){
      console.log('dataBoundChuaXuLy', args)
      let obj = this.$refs.gridChuaXuLy.$refs.grid.ej2Instances
      // console.log(obj)
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value.ischeck=='1') {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    selectedItemsChanged_ChuaXuLy (selectedItems) {
      this.DanhSachPhieus.selectedItems = selectedItems
    },
    selectedItemsChanged_ChoXuLy: function (selectedItems) {
      this.DsPhieuChoXuLy.selectedItems = selectedItems
    },
    dsChoXLRowDeselected(args){
      console.log('dsChoXLRowDeselected', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        // this.updateDSKhoaSelected(args.data)
        console.log('dsChoXLRowDeselected', args)
        const index = this.DsPhieuChoXuLy.datalist.findIndex(x => x.khieunai_id == args.data.khieunai_id)
        this.DsPhieuChoXuLy.datalist[index].ischeck='0'
      }
      if (this.currentIndex == args.rowIndex) { 
        console.log('ClearAll') 
        this.ClearAll() 
      }
    },
    checkBoxChoXuLyChange(args){
      if (args.target.className=='e-checkselectall') { // click check/uncheck all
        console.log('checkBoxChoXuLyChange', args)
        let dsFilter = this.$refs.gridChoXuLy.filteredDataSource || []

        console.log('checkBoxChoXuLyChange dsFilter', dsFilter)

        for(let i=0;i<dsFilter.length;i++){
          const index=this.DsPhieuChoXuLy.datalist.findIndex(x=>x.khieunai_id==dsFilter[i].khieunai_id)
          if(index>-1){
            if(args.checked){
              this.DsPhieuChoXuLy.datalist[index].ischeck='1'
            }else{
              this.DsPhieuChoXuLy.datalist[index].ischeck='0'
            }
            
          }else{
            this.DsPhieuChoXuLy.datalist[index].ischeck='0'
          }
        }
        console.log('checkBoxChoXuLyChange',  this.DsPhieuChoXuLy.datalist)
      }
    },  
    dataBoundChoXuLy(args){
      console.log('dataBoundChoXuLy', args)
      let obj = this.$refs.gridChoXuLy.$refs.grid.ej2Instances
      // console.log(obj)
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value.ischeck=='1') {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    selectedItemsChanged_KhoaPhieu: function(selectedItems) {
      this.DsPhieuKhoa.selectedItems = selectedItems
    },
    selectedItemsChanged_BaoTon: function(selectedItems) {
      this.DsPhieuBaoTon.selectedItems = selectedItems
    },
    cboKetQuaXL_Change(val) {
      console.log('cboKetQua_XL_Change', val)
      if (this.tabIndex == '1' || this.tabIndex == 1) {
        if (val == '1' || val == 1) {
          this.UiButton.showKhoaPhieu = true
          this.UiButton.showBaoTon = false
          this.lblNDXuLy = 'ND trả lời <span style="color: #dc3545"> (*)</span>'
          this.txtNoiDungXL_MaxLength = 1000
          this.txtNoiDungXL = this.txtNoiDungXL_Ton
        } else {
          this.UiButton.showKhoaPhieu = false
          this.UiButton.showBaoTon = true
          this.lblNDXuLy = 'ND tồn <span style="color: #dc3545"> (*)</span>'
          this.txtNoiDungXL_MaxLength = 300
          this.txtNoiDungXL_Ton = this.txtNoiDungXL
          if (this.txtNoiDungXL_Ton.length > 300) {
            this.txtNoiDungXL_Ton = this.txtNoiDungXL_Ton.substring(0, 300)
          }
        }
      } else {
        this.UiButton.showKhoaPhieu = false
        this.UiButton.showBaoTon = false
      }
      if (val == '1' || val == 1) {
        this.txtNoiDungXL_MaxLength = 1000
        this.txtNoiDungXL = this.txtNoiDungXL_Ton
      } else {
        this.txtNoiDungXL_MaxLength = 300
        this.txtNoiDungXL_Ton = this.txtNoiDungXL
        if (this.txtNoiDungXL_Ton.length > 300) {
          this.txtNoiDungXL_Ton = this.txtNoiDungXL_Ton.substring(0, 300)
        }
      }
    },
    KhoaPhieuSelectDate: function() {
      // this.AlertMsg(`Từ tháng: ${this.DsPhieuKhoa.From} đến tháng: ${this.DsPhieuKhoa.To}`)
    },
    AlertMsg: function(msg) {
      this.$toast.error(msg)
      // alert(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
      // alert(msg)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
      // alert(msg)
    },
    ShowAlert: function(msg) {
      this.$toast.alert(msg)
      // alert(msg)
    },
    TabChanged: function(event) {
      // alert(` Tab changed ${this.tabIndex}`)
      console.log(event)
    },
    MonthValidate: function(val) {
      if (this.DsPhieuKhoa.From > this.DsPhieuKhoa.To) {
        this.ShowAlert('Dữ liệu từ tháng không được lớn hơn đến tháng')
        this.DsPhieuKhoa.From = this.DsPhieuKhoa.To
      }
    },
    ChangeKetQuaXuLy: function(event) {
      if (this.tabIndex === 1) {
        // this.UiButton.showKhoaPhieu = this.KetQuaXuLy.ketquakn_id === 1
        // this.UiButton.showBaoTon = this.KetQuaXuLy.ketquakn_id === 2
      }
    },
    actionCompleteChuaXuLy(args){
      const ds= this.$refs.gridChuaXuLy.getDatafiltered()
      console.log('actionCompleteChuaXuLy', ds)
    },
    actionCompleteChoXuLy(){
      const ds= this.$refs.gridChoXuLy.getDatafiltered()
      console.log('actionCompleteChuaXuLy', ds)
    },
    async QuyTrinhKnChanged(val) {
      try {
        this.loading(true)
        this.quytrinh_id = val
        let data = await this.lay_ds_huong_giao(val)
        if (data != null && data.length > 0) {
          this.luong_id = data[0]['luong_id']
          let tenhg = data[0]['huonggiao']
          this.labelFunctionTitle = tenhg.toUpperCase()
          this.labelFunctionTitleMenu = tenhg
        } else {
          this.ShowError('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
          await this.HT_DS_CHUDE_KN(val)
          this.loaded = true
          return
        }
        await Promise.all([this.HienThiGiaoDien(), this.HT_DS_CHUDE_KN(val)])
        // if (this.loaded) {
        //   console.log(`QuyTrinhKnId:loaded`)
        //   await Promise.all([this.HienThiGiaoDien(), this.HT_DS_CHUDE_KN(val)])
        // } else {
        //   console.log(`QuyTrinhKnId:setTimeout`)
        //   setTimeout(() => this.HT_DS_CHUDE_KN(val), 0)
        //   this.loaded = true
        // }
      } catch (err) {
        console.log('QuyTrinhKnId', err)
      } finally {
        this.loading(false)
      }
    },
    startTimer: function() {
      try {
        console.log('Start Timer')
        this.timer = setInterval(() => this.CheckLoading(), 100)
      } catch (err) {
        console.log('🚀 startTimer~ err', err)
      }
    },
    stopTimer: function() {
      try {
        clearInterval(this.timer)
        this.timer = null
      } catch (err) {
        this.timer = null
      }
    },
    TestABC() {
      let a = { selected_tab: 1, kqxl_id: 1, ngay_xl: '2022-08-16T02:52:01.000Z', nhanvien_th_id: 4586, noidung_xl: '23424', nguyennhan: '2342', tien_gc: 0, vat_gc: 0, loi_id: 1, js_gridview_choxl: [{ CHON: 1, KHIEUNAI_ID: 6000200 }], js_ds3: [] }
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
    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    toolbarClick: function (args) {
      if (args.item.id === 'Grid_excelexport') { // 'Grid_excelexport' -> Grid component id + _ + toolbar item name
        this.$refs.grid.excelExport();
      }
    },
    async contextMenu_Click(args) {
      console.log('contextMenuClick: ', args)
      if (args.item.id == 'giaoxuly') {
        this.tsbtnGiaoXL_Click()
      } else if (args.item.id == 'traphieu') {
        var ds = []
        //frmTraPhieuKN_SLL chưa có khảo sát
        if (!this.DanhSachPhieus.selectedItems || this.DanhSachPhieus.selectedItems.length <= 0) {
          this.$toast.error('Bạn chưa chọn phiếu để trả!')
          return
        }
        for (var item of this.DanhSachPhieus.selectedItems) {
          ds.push(item)
        }
        this.$refs.frmTraPhieuKN_SLL['dtKhieuNai']['data'] = ds
        this.$refs.frmTraPhieuKN_SLL.show()
        return
      }
    },
    async contextMenuChoXL_Click(args) {
      console.log('contextMenuChoXLClick: ', args)
      if (args.item.id == 'traphieu') {
        var ds = []
        //frmTraPhieuKN_SLL chưa có khảo sát
        if (!this.DsPhieuChoXuLy.selectedItems || this.DsPhieuChoXuLy.selectedItems.length <= 0) {
          this.$toast.error('Bạn chưa chọn phiếu để trả!')
          return
        }
        for (var item of this.DsPhieuChoXuLy.selectedItems) {
          ds.push(item)
        }
        this.$refs.frmTraPhieuKN_SLL['dtKhieuNai']['data'] = ds
        this.$refs.frmTraPhieuKN_SLL.show()
        return
      }
    },
    onSuccess_frmTraPhieuKN_SLL: async function (result) {
      if (result == 'success') await this.HT_DS_CHUDE_KN(this.QuyTrinhKnId)
    },
    changeCheckCXL(item, checked){
      console.log('changeCheckCXL item check', item, checked)
      const index=this.DanhSachPhieus.datalist.findIndex(x=>x.khieunai_id==item.khieunai_id)
      if(index>-1){
          if(checked){
            this.DanhSachPhieus.datalist[index].ischeck='1'
          }else{
            this.DanhSachPhieus.datalist[index].ischeck='0'
          }
          console.log('changeCheckCXL1', this.DanhSachPhieus.datalist[index])
      }
      // if(this.DanhSachPhieus.datalist.length==this.DanhSachPhieus.datalist.filter(x=>x.ischeck=='1').length){
      //   this.isCheckedHeader2=true
      // }else{
      //   this.isCheckedHeader2=false
      // }
    },
    changeCheckHeaderCXL(checked){
      if (this.DanhSachPhieus.datalist && this.DanhSachPhieus.datalist.length >= 50000) {
        this.$bvModal
          .msgBoxConfirm('Danh sách phiếu quá nhiều, bạn có muốn thực hiện ở nút hành động tất cả để xử lý mà không cần chọn tất cả?', {
            title: 'Thông báo',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              this.$toast.info('Bạn hãy ấn nút hành động tất cả để thực hiện!')
              return
            } else {
              if(checked){
                for(let i=0;i<this.DanhSachPhieus.datalist.length;i++){
                    this.DanhSachPhieus.datalist[i].ischeck='1'
                }
              }else{
                for(let i=0;i<this.DanhSachPhieus.datalist.length;i++){
                    this.DanhSachPhieus.datalist[i].ischeck='0'
                }
              }
              this.DanhSachPhieus.datalist= this.DanhSachPhieus.datalist.concat([])
            }
          })
          .catch((e) => {
            console.log(e)
          })
      } else {
        if(checked){
          for(let i=0;i<this.DanhSachPhieus.datalist.length;i++){
              this.DanhSachPhieus.datalist[i].ischeck='1'
          }
        }else{
          for(let i=0;i<this.DanhSachPhieus.datalist.length;i++){
              this.DanhSachPhieus.datalist[i].ischeck='0'
          }
        }
        this.DanhSachPhieus.datalist= this.DanhSachPhieus.datalist.concat([])
      }
    },
    columnTemplateCheckBox(parent) {
        return function() {
            return { 
                template: {
                    template: `
                      <div class="check-action">
                        <ejs-checkbox :value="isChecked" @change="changeCheck">
                        </ejs-checkbox>
                      </div>
                    `,
                    data() {
                        return {
                          data: {},
                          parent:parent
                        }
                    },
                    computed: {
                        isChecked(){
                          return this.data.ischeck=='1'
                        }
                    },
                    methods:{
                        changeCheck(value){
                            this.parent.changeCheckCXL(this.data, value.checked)
                            
                        }
                    }
                }
            }
        }
    },
    columnTemplateCheckBoxHeader(parent){
        return function() {
            return { 
                template: {
                    template: `
                      <div class="check-action">
                        <ejs-checkbox id="checkAllHeader2" :value="parent.isCheckedHeader2"  @change="changeCheck">
                        </ejs-checkbox>
                      </div>
                    `,
                    data() {
                        return {
                          parent:parent,
                          data:{}
                        }
                    },
                    computed: {
                        
                    },
                    methods:{
                        changeCheck(value){
                          this.isChecked=value.checked
                            console.log('lang thang', this.data)
                            this.parent.changeCheckHeaderCXL(value.checked)
                            this.parent.isCheckedHeader2=value.checked
                        }
                    }
                }
            }
        }
    },
    changeCheckCXL2(item, checked){
      const index=this.DsPhieuChoXuLy.datalist.findIndex(x=>x.khieunai_id==item.khieunai_id)
      if(index>-1){
        if(checked){
          this.DsPhieuChoXuLy.datalist[index].ischeck='1'
        }else{
          this.DsPhieuChoXuLy.datalist[index].ischeck='0'    

        }
      }
    },
    changeCheckHeaderCXL2(checked){
      if (this.DsPhieuChoXuLy.datalist && this.DsPhieuChoXuLy.datalist.length >= 50000) {
        this.$bvModal
          .msgBoxConfirm('Danh sách phiếu quá nhiều, bạn có muốn thực hiện ở nút hành động tất cả để xử lý mà không cần chọn tất cả?', {
            title: 'Thông báo',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (v) {
              this.$toast.info('Bạn hãy ấn nút hành động tất cả để thực hiện!')
              return
            } else {
              if(checked){
                for(let i=0;i<this.DsPhieuChoXuLy.datalist.length;i++){
                    this.DsPhieuChoXuLy.datalist[i].ischeck='1'
                }
              }else{
                for(let i=0;i<this.DsPhieuChoXuLy.datalist.length;i++){
                    this.DsPhieuChoXuLy.datalist[i].ischeck='0'
                }
              }
              this.DsPhieuChoXuLy.datalist= this.DsPhieuChoXuLy.datalist.concat([])
            }
          })
          .catch((e) => {
            console.log(e)
          })
      } else {
        if(checked){
          for(let i=0;i<this.DsPhieuChoXuLy.datalist.length;i++){
              this.DsPhieuChoXuLy.datalist[i].ischeck='1'
          }
        }else{
          for(let i=0;i<this.DsPhieuChoXuLy.datalist.length;i++){
              this.DsPhieuChoXuLy.datalist[i].ischeck='0'
          }
        }
        this.DsPhieuChoXuLy.datalist= this.DsPhieuChoXuLy.datalist.concat([])
      }
    },
    columnTemplateCheckBox2(parent) {
        return function() {
            return { 
                template: {
                    template: `
                      <div class="check-action">
                        <ejs-checkbox :value="isChecked" @change="changeCheck">
                        </ejs-checkbox>
                      </div>
                    `,
                    data() {
                        return {
                          data: {},
                          parent:parent
                        }
                    },
                    computed: {
                        isChecked(){
                          return this.data.ischeck=='1'
                        }
                    },
                    methods:{
                        changeCheck(value){
                          this.parent.changeCheckCXL2(this.data, value.checked) 

                        }
                    }
                }
            }
        }
    },
    columnTemplateCheckBoxHeader2(parent){
      return function() {
        return { 
          template: {
            template: `
              <div class="check-action">
                <ejs-checkbox :value="parent.isCheckedHeader3"  @change="changeCheck">
                </ejs-checkbox>
              </div>
            `,
            data() {
              return {
                parent: parent,
                isChecked: false,
                data:{}
              }
            },
            computed: {
                
            },
            methods:{
              changeCheck(value){
                this.isChecked = value.checked
                this.parent.changeCheckHeaderCXL2(value.checked)
                this.parent.isCheckedHeader3 = value.checked
              }
            }
          }
        }
      }
    }
  }
}
</script>
