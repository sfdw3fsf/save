<template src="./ListBuilding.html"></template>
<style src="./ListBuilding.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import gridview from '@/components/Shared/gridview'
//import gridview from "./components/DataGrid";
import DatePicker from 'vue2-datepicker'
import API from './API'
import { DataManager, Query, ODataAdaptor, Predicate } from '@syncfusion/ej2-data'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue } from 'vuelidate/lib/validators'
import ModalChonDiaChi from './components/ModalChonDiaChi.vue'
import moment from 'moment'
import AssignBuildingToIMSProject from './components'
// Modal
import TraCuuTBToaNha from './modal/TraCuuTBToaNha/index.vue'
import TraCuuKCToaNha from './modal/TraCuuKCToaNha/index.vue'
// import { Icon } from '@iconify/vue2'
import LichSuModal from './components/ModalLichSu.vue'
import nhacc from './popup/nhacc.vue';
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    API,
    DataManager,
    Query,
    ODataAdaptor,
    Predicate,
    gridview,
    ModalChonDiaChi,
    BssErrorMarker,
    BssRequiredMarker,
    DatePicker,
    AssignBuildingToIMSProject,
    TraCuuTBToaNha,
    TraCuuKCToaNha,
    nhacc,
    // Icon,
    LichSuModal
  },
  name: 'ListBuilding',
  validations: {
    diachiTB:
    {
      DIACHI: {
        required
      },
    },
  },
  mounted: async function () {
    this.thoigianccdv = moment(new Date()).format('DD/MM/YYYY')
    this.ngay_bg_canho = moment(new Date()).format('DD/MM/YYYY')
    this.ma_tinh = this.$root.token.getMaTinh()
    if (this.ma_tinh == "HCM") {
      this.listbox.ds_toanha.header = this.headerHCM
      this.disabledQuyMo = true;
    }
    else {
      this.listbox.ds_toanha.header = this.headerTinh
      this.disabledQuyMo = false;
    }
    this.SetButton(0)
    await this.fetchAPI();
    await this.GetDS()
    await this.DvQuanLyChange()
    await this.GetDSToaNha()
  },
  data() {
    return {
      ttda: false,
      goiCuocToiThieu: false,
      TTHT: false,
      nhaMangKhac: false,
      p_duan_id: '',
      p_toanha_id: '',
      dateconfig: {
        altFormat: 'd/m/Y H:i:S',
        altInput: true,
        dateFormat: 'd/m/Y H:i:S',
        allowInput: true,
      },
      dateFormat: 'DD/MM/YYYY',
      ngay_ht: new Date(),
      header: {
        title: 'GÁN THUỘC TÍNH VẬT TƯ',
        list: [
          { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
          {
            name: 'Cập nhật đơn giá theo công văn 3883',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isNhapMoi: false,
      isGhiLai: false,
      isHuy: false,
      isXoa: false,
      isCapNhat: false,
      isCachTim: false,
      isTimTheoDuAn: false,
      isTimTheoDoiTac: false,
      headerHCM: [
        { fieldName: 'ma_duan', headerText: 'Mã dự án', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_duan', headerText: 'Dự án', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ma_toanha', headerText: 'Mã tòa nhà', allowFiltering: true, width: 100 },
        { fieldName: 'ten_toanha', headerText: 'Tên tòa nhà', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_quan', headerText: 'Quận/Huyện', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_phuong', headerText: 'Phường/Xã', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'diachi', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false, width: 200 },
        {
          fieldName: 'ten_loaitn',
          headerText: 'Loại tòa nhà',
          allowFiltering: true,
          allowSorting: false,
          width: 200
        },
        { fieldName: 'loai_dtda', headerText: 'Đối tượng', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'ten_chudtu', headerText: 'Chủ Đầu tư', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'ten_dt', headerText: 'Đối tác', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'sdt_bql', headerText: 'SĐT ban QL', allowFiltering: true, width: 120 },
        { fieldName: 'nguoidd_bql', headerText: 'Người đại diện', allowFiltering: true, width: 120 },
        { fieldName: 'email_bql', headerText: 'Email NĐD', allowFiltering: true, width: 120 },
        { fieldName: 'chucvu_bql', headerText: 'Chức vụ NĐD', allowFiltering: true, width: 120 },
        { fieldName: 'quy_mo', headerText: 'Quy mô', allowFiltering: true, width: 120 },
        { fieldName: 'dientich', headerText: 'Diện tích', allowFiltering: true, width: 120 },
        { fieldName: 'sl_tang', headerText: 'SL tầng', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho', headerText: 'SL căn hộ', allowFiltering: true, width: 120 },
        { fieldName: 'sl_vp', headerText: 'SL văn phòng', allowFiltering: true, width: 120 },
        { fieldName: 'sl_block', headerText: 'SL Block', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_hopdong', headerText: 'Quy mô HĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_lapday', headerText: 'Quy mô LĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_thucte', headerText: 'Quy mô TT HĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_vnpt_n1', headerText: 'TB Fiber Dự kiến (N1)', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_vnpt_n2', headerText: 'TB Fiber Dự kiến (N2)', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_vnpt', headerText: 'TB Fiber Dự kiến (N3)', allowFiltering: true, width: 120 },
        { fieldName: 'loaicap', headerText: 'Loại cáp', allowFiltering: true, width: 120 },
        { fieldName: 'doanhthu_dukien_n1', headerText: 'D.Thu dự kiến (N1)', allowFiltering: true, width: 120 },
        { fieldName: 'doanhthu_dukien_n2', headerText: 'D.Thu dự kiến (N2)', allowFiltering: true, width: 120 },
        { fieldName: 'doanhthu_dukien', headerText: 'D.Thu dự kiến (N3)', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho_bg', headerText: 'SL căn hộ BG', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho_sd', headerText: 'SL căn hộ SD', allowFiltering: true, width: 120 },
        { fieldName: 'dv_tiepthi', headerText: 'Đơn vị tiếp thị', allowFiltering: true, width: 120 },
        { fieldName: 'dv_quanly', headerText: 'Đơn vị quản lý', allowFiltering: true, width: 120 },
        { fieldName: 'dv_doivt', headerText: 'Đội VT', allowFiltering: true, width: 120 },
        { fieldName: 'nv_kythuat', headerText: 'NV Kỹ thuật', allowFiltering: true, width: 120 },
        { fieldName: 'nv_banhang', headerText: 'NV Bán hàng', allowFiltering: true, width: 120 },
        { fieldName: 'nv_cskh', headerText: 'NV CSKH', allowFiltering: true, width: 120 },
        { fieldName: 'tiendo', headerText: 'Tiến độ', allowFiltering: true, width: 120 },
        { fieldName: 'ten_vt', headerText: 'Tên tắt', allowFiltering: true, width: 120 },
        { fieldName: 'phamvi_dtu_mang', headerText: 'Phạm vi đầu tư mạng', allowFiltering: true, width: 120 },
        { fieldName: 'thuchien_pcdt', headerText: 'Phân chia doanh thu', allowFiltering: true, width: 120 },
        { fieldName: 'lydo_pcdt', headerText: 'Lý do PCDT', allowFiltering: true, width: 120 },
        { fieldName: 'sansang_pttb', headerText: 'Sẵn sàng PTTB', allowFiltering: true, width: 120 },
        { fieldName: 'lydo_chua_ss', headerText: 'Lý do chưa sẵn sàng', allowFiltering: true, width: 120 },
        { fieldName: 'tg_ccdv', headerText: 'Thời điểm CCDV', allowFiltering: true, width: 120 },
        { fieldName: 'goi_cuoc', headerText: 'Gói cước', allowFiltering: true, width: 120 },
        { fieldName: 'khuyenmai', headerText: 'Khuyến mại', allowFiltering: true, width: 120 },
        { fieldName: 'ma_ibs', headerText: 'Mã IBS', allowFiltering: true, width: 120 },
        { fieldName: 'von_dautu', headerText: 'Vốn đầu tư', allowFiltering: true, width: 120 },
        { fieldName: 'hientrang', headerText: 'Hiện trạng', allowFiltering: true, width: 120 },
        { fieldName: 'ten_tinhtrang', headerText: 'Tình trạng', allowFiltering: true, width: 120 },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, width: 120 },
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, width: 120 },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, width: 120 },
        {
          fieldName: 'doanhthu_dukien',
          headerText: 'Doanh thu dự kiến',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        { fieldName: 'ten_quymo', headerText: 'Quy mô', allowFiltering: true, width: 100 },
        {
          fieldName: 'dientich',
          headerText: 'Diện tích',
          allowFiltering: true,
          type: 'Number',
          format: 'N0',
          width: 100
        },
        {
          fieldName: 'sl_tang',
          headerText: 'SL tầng',
          allowFiltering: true,
          type: 'Number',
          format: 'N0',
          width: 100
        },
        { fieldName: 'donvi_qlvh', headerText: 'Đơn vị QLVH', allowFiltering: true, width: 100 },
        { fieldName: 'mohinh_ccdv', headerText: 'Mô hình CCDV', allowFiltering: true, width: 100 },
        { fieldName: 'olt_xhh', headerText: 'OTL XHH', type: 'Number', allowFiltering: true, width: 100 },
        { fieldName: 'qltn_bmis', headerText: 'QLTN BMIS', type: 'Number', allowFiltering: true, width: 100 },
        { fieldName: 'ctv_banhang', headerText: 'CTV BH', allowFiltering: true, width: 100 },
        { fieldName: 'ttda_giao_dlbh', headerText: 'TTDA giao cho ĐLBH', allowFiltering: true, width: 100 },
        { fieldName: 'goicuoc_toithieu', headerText: 'Gói cước tối thiểu', allowFiltering: true, width: 100 },
        { fieldName: 'tinhtrang_hatang', headerText: 'Tình trạng hạ tầng', allowFiltering: true, width: 100 },
        { fieldName: 'nhamang_khac', headerText: 'Nhà mạng khác', allowFiltering: true, width: 100 },
      ],
      headerTinh: [
        { fieldName: 'ma_duan', headerText: 'Mã dự án', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_duan', headerText: 'Dự án', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ma_toanha', headerText: 'Mã tòa nhà', allowFiltering: true, width: 100 },
        { fieldName: 'ten_toanha', headerText: 'Tên tòa nhà', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_quan', headerText: 'Quận/Huyện', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'ten_phuong', headerText: 'Phường/Xã', allowFiltering: true, allowSorting: false, width: 200 },
        { fieldName: 'diachi', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false, width: 200 },
        {
          fieldName: 'ten_loaitn',
          headerText: 'Loại tòa nhà',
          allowFiltering: true,
          allowSorting: false,
          width: 200
        },
        { fieldName: 'loai_dtda', headerText: 'Đối tượng', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'ten_chudtu', headerText: 'Chủ Đầu tư', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'ten_dt', headerText: 'Đối tác', allowFiltering: true, allowSorting: false, width: 250 },
        { fieldName: 'sdt_bql', headerText: 'SĐT ban QL', allowFiltering: true, width: 120 },
        { fieldName: 'nguoidd_bql', headerText: 'Người đại diện', allowFiltering: true, width: 120 },
        { fieldName: 'email_bql', headerText: 'Email NĐD', allowFiltering: true, width: 120 },
        { fieldName: 'chucvu_bql', headerText: 'Chức vụ NĐD', allowFiltering: true, width: 120 },
        { fieldName: 'quy_mo', headerText: 'Quy mô', allowFiltering: true, width: 120 },
        { fieldName: 'dientich', headerText: 'Diện tích', allowFiltering: true, width: 120 },
        { fieldName: 'sl_tang', headerText: 'SL tầng', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho', headerText: 'SL căn hộ', allowFiltering: true, width: 120 },
        { fieldName: 'sl_vp', headerText: 'SL văn phòng', allowFiltering: true, width: 120 },
        { fieldName: 'sl_block', headerText: 'SL Block', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_hopdong', headerText: 'Quy mô HĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_lapday', headerText: 'Quy mô LĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_thucte', headerText: 'Quy mô TT HĐ', allowFiltering: true, width: 120 },
        { fieldName: 'quymo_vnpt', headerText: 'TB Fiber Dự kiến', allowFiltering: true, width: 120 },
        { fieldName: 'loaicap', headerText: 'Loại cáp', allowFiltering: true, width: 120 },
        { fieldName: 'doanhthu_dukien', headerText: 'D.Thu dự kiến', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho_bg', headerText: 'SL căn hộ BG', allowFiltering: true, width: 120 },
        { fieldName: 'sl_canho_sd', headerText: 'SL căn hộ SD', allowFiltering: true, width: 120 },
        //Chưa xong
        { fieldName: 'dv_tiepthi', headerText: 'Đơn vị tiếp thị', allowFiltering: true, width: 120 },
        { fieldName: 'dv_quanly', headerText: 'Đơn vị quản lý', allowFiltering: true, width: 120 },
        { fieldName: 'dv_doivt', headerText: 'Đội VT', allowFiltering: true, width: 120 },
        { fieldName: 'nv_kythuat', headerText: 'NV Kỹ thuật', allowFiltering: true, width: 120 },
        { fieldName: 'nv_banhang', headerText: 'NV Bán hàng', allowFiltering: true, width: 120 },
        { fieldName: 'nv_cskh', headerText: 'NV CSKH', allowFiltering: true, width: 120 },
        //end
        { fieldName: 'tiendo', headerText: 'Tiến độ', allowFiltering: true, width: 120 },
        { fieldName: 'ten_vt', headerText: 'Tên tắt', allowFiltering: true, width: 120 },
        { fieldName: 'phamvi_dtu_mang', headerText: 'Phạm vi đầu tư mạng', allowFiltering: true, width: 120 },
        { fieldName: 'thuchien_pcdt', headerText: 'Phân chia doanh thu', allowFiltering: true, width: 120 },
        { fieldName: 'lydo_pcdt', headerText: 'Lý do PCDT', allowFiltering: true, width: 120 },
        { fieldName: 'sansang_pttb', headerText: 'Sẵn sàng PTTB', allowFiltering: true, width: 120 },
        { fieldName: 'lydo_chua_ss', headerText: 'Lý do chưa sẵn sàng', allowFiltering: true, width: 120 },
        { fieldName: 'tg_ccdv', headerText: 'Thời điểm CCDV', allowFiltering: true, width: 120 },
        { fieldName: 'goi_cuoc', headerText: 'Gói cước', allowFiltering: true, width: 120 },
        { fieldName: 'khuyenmai', headerText: 'Khuyến mại', allowFiltering: true, width: 120 },
        { fieldName: 'ma_ibs', headerText: 'Mã IBS', allowFiltering: true, width: 120 },
        { fieldName: 'von_dautu', headerText: 'Vốn đầu tư', allowFiltering: true, width: 120 },
        { fieldName: 'hientrang', headerText: 'Hiện trạng', allowFiltering: true, width: 120 },
        { fieldName: 'ten_tinhtrang', headerText: 'Tình trạng', allowFiltering: true, width: 120 },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true, width: 120 },
        { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true, width: 120 },
        { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true, width: 120 },
        {
          fieldName: 'doanhthu_dukien',
          headerText: 'Doanh thu dự kiến',
          allowFiltering: true,
          allowSorting: false,
          width: 140
        },
        { fieldName: 'ten_quymo', headerText: 'Quy mô', allowFiltering: true, width: 100 },
        {
          fieldName: 'dientich',
          headerText: 'Diện tích',
          allowFiltering: true,
          type: 'Number',
          format: 'N0',
          width: 100
        },
        {
          fieldName: 'sl_tang',
          headerText: 'SL tầng',
          allowFiltering: true,
          type: 'Number',
          format: 'N0',
          width: 100
        },
        { fieldName: 'donvi_qlvh', headerText: 'Đơn vị QLVH', allowFiltering: true, width: 100 },
        { fieldName: 'mohinh_ccdv', headerText: 'Mô hình CCDV', allowFiltering: true, width: 100 },
        { fieldName: 'olt_xhh', headerText: 'OTL XHH', type: 'Number', allowFiltering: true, width: 100 },
        { fieldName: 'qltn_bmis', headerText: 'QLTN BMIS', type: 'Number', allowFiltering: true, width: 100 },
        { fieldName: 'ctv_banhang', headerText: 'CTV BH', allowFiltering: true, width: 100 },
      ],
      listbox: {
        ds_toanha: {
          list: [],
          header: [],
          value: {},
          checked: []
        },
        //hieubh.hdg bổ sung 27/03/2023
        nvbanhang: {
          list: [],
          value: {},
        },
        nvkythuat: {
          list: [],
          value: {},
        },
        nvcskh: {
          list: [],
          value: {},
        },
        //end bổ sung
        duan: {
          list: [],
          value: {},
        },
        htdautu: {
          list: [],
          value: {},
        },
        loaitoanha: {
          list: [],
          value: {},
        },
        loaicap: {
          list: [],
          value: {},
        },
        dvtiepthi: {
          list: [],
          value: {},
        },
        dvquanly: {
          list: [],
          value: {},
        },
        doivt: {
          list: [],
          value: {},
        },
        phamvi: {
          list: [],
          value: {},
        },
        hientrang: {
          list: [],
          value: {},
        },
        phanchiadt: {
          list: [],
          value: {},
        },
        duan_timkiem: {
          list: [],
          value: {},
        },
        doitac: {
          list: [],
          value: {},
        },
        doitac_timkiem: {
          list: [],
          value: {},
        },
        quymo: {
          list: [{ id: 1, text: 'Lớn' },
          { id: 2, text: 'Vừa' },
          { id: 3, text: 'Nhỏ' },
          ],
          value: {},
        },
        sansangpttb: {
          list: [{ id: 0, text: 'Chưa sẵn sàng PTTB' },
          { id: 1, text: 'Đã sẵn sàng PTTB' },
          ],
          value: {},
        },
        mhccdv: {
          list: [],
          value: {},
        },
        thongtinQC: {
          list: [
          ],
          value: {},
        },
        cpHH: {
          list: [
            { id: 1, text: '>= 500K' },
            { id: 2, text: '<500K và >= 400K' },
            { id: 3, text: '<400K và >= 300K' },
            { id: 4, text: '<300K và >= 200K' },
            { id: 5, text: '<200K và >= 100K' },
            { id: 6, text: 'Không có' },
          ],
          value: {},
        },
        tinhtrang: {
          list: [{ id: -1, text: '' },
          { id: 1, text: 'Sử dụng' },
          { id: 0, text: 'Hủy' },
          ],
          value: {},
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      //Hieubh.hdg bổ sung 27/03/2023
      ctv_bh: '',
      sl_block: 0,
      doanhthu_dukien_n1: 0,
      doanhthu_dukien_n2: 0,
      doanhthu_dukien: 0,
      quymo_hopdong: 0,
      sl_canho_sd: 0,
      quymo_vnpt_n1: 0,
      quymo_vnpt_n2: 0,
      quymo_vnpt: 0,
      quymo_thucte: 0,
      quymo_lapday: 0,
      sl_canho_bg: 0,
      ma_ibs: '',
      chkVonDT: {
        checked: false,
      },
      chkBMIS: {
        checked: false,
      },
      chkOLT: {
        checked: false,
      },
      checkONT: false,
      dvQLVH: '',
      sl_shophouse: 0,
      ngay_bg_canho: "",
      ma_tinh: '',
      //End bổ sung
      dongia: '0',
      slfiber: '0',
      quytienluong: '0',
      mucchi: '0',
      mucthuhoi: '0',
      khokhan: '',
      dexuat: '',
      dongia_id: '',
      index: 0,
      success_code: 'BSS-00000000',
      TRAM_CAP_PORT: 504,
      matoanha: '',
      tentoanha: '',
      nguoidaidien: '',
      sdtbql: '',
      chucvu: '',
      email: '',
      dientich: '',
      sltang: '',
      slvp: 0,
      slcanho: 0,
      //dthudukien: "",
      tiendo: '',
      lydopcdt: '',
      lydochuass: '',
      goicuoc: '',
      khuyenmai: '',
      ten_vt: '',
      ghichu: '',
      toanhaid: 0,
      popupComponent: null,
      popupComponentData: null,
      popupTitle: '',
      popupComponentName: '',
      thoigianccdv: new Date(),
      diachiTB: {
        DIACHI: null,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DAC_DIEM_ID: 0,
        SONHA: 0,
        KINHDO: 0,
        VIDO: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
        DIACHI_ID: 0,
      },
      vds: {
        PHANVUNG_ID: null,
        DUAN_ID: null,
        MA_DUAN: null,
        TEN_DUAN: null,
        DIACHI: null,
        CHUDTU_ID: 0,
        PHUONG_ID: 0,
        QUAN_ID: 0,
        GHICHU: '',
        NGAY_CN: '',
        MAY_CN: '',
        NGUOI_CN: '',
        IP_CN: '',
        TTDA_ID: 0,
        NGAY_TK: '',
        NGAY_HT: '',
        TTVT_ID: 0,
        NGUOI_CCTT: '',
        HTHT_ID: 0,
        MA_CT: '',
        NGAY_NT: '',
        SOHIEU_CT: 0,
        SOQD_DT: '',
        SOQD_QT: '',
        DIACHI_ID: 0,
      },
      building: {
        TIENDO: '',
        SONHA: '',
        DV_QUANLY: '',
        KHU_ID: null,
        NGAY_CN: '',
        DIENTICH: 0,
        MAY_CN: '',
        TEN_LOAITN: '',
        NGUOIDD_BQL: '',
        TEN_VT: '',
        LOAICAP: '',
        QUAN_ID: '',
        TEN_QUAN: '',
        DTDA_ID: null,
        AP_ID: '',
        SDT_BQL: '',
        DONVI_QUANLY: '',
        SL_TANG: '',
        HIENTRANG_ID: '',
        PHANVUNG_ID: '',
        GHICHU: '',
        VI_DO: '',
        TEN_PHUONG: '',
        THUCHIEN_PCDT: '',
        DOI_VT: '',
        QUY_MO: '',
        DIACHI: '',
        STT: 0,
        LOAI_CAP: '',
        KINH_DO: '',
        DONVI_TIEPTHI: '',
        LOAI_DTDA: '',
        EMAIL_BQL: '',
        TG_CCDV: '',
        PHUONG_ID: null,
        NGUOI_CN: '',
        TOANHA_ID: null,
        TEN_DT: '',
        GOI_CUOC: '',
        TEN_QUYMO: '',
        MA_TOANHA: '',
        TEN_CHUDTU: '',
        LYDO_CHUA_SS: '',
        TINHTRANG: '',
        DUAN_ID: null,
        LYDO_PCDT: '',
        KHUYENMAI: '',
        DOITAC_ID: null,
        PHAMVI_DTU_MANG: '',
        DV_TIEPTHI: '',
        SL_VP: '',
        PHO_ID: null,
        CHUCVU_BQL: '',
        SL_CANHO: '',
        LOAITN_ID: null,
        TEN_TOANHA: '',
        DACDIEM_ID: null,
        CHUQUAN_ID: null,
        IP_CN: '',
        SANSANG_PTTB: '',
        DOANHTHU_DUKIEN: '',
        disabledQuyMo: true,
        checkNhaMang: false
      },
    }
  },
  computed: {
    NhapMoi: {
      get() { return this.isNhapMoi; },
      set(value) {
        this.isNhapMoi = value;
      }
    },
    GhiLai: {
      get() { return this.isGhiLai; },
      set(value) {
        this.isGhiLai = value;
      }
    },
    Huy: {
      get() { return this.isHuy; },
      set(value) {
        this.isHuy = value;
      }
    },
    Xoa: {
      get() { return this.isXoa; },
      set(value) {
        this.isXoa = value;
      }
    },
    timtheoduan: {
      get() { return this.isTimTheoDuAn; },
      set(value) {
        this.isTimTheoDuAn = value;
        if (this.isTimTheoDuAn) {
          this.listbox.ds_toanha.list = this.listbox.ds_toanha.list.filter(x => x.duan_id == this.listbox.duan_timkiem.value.id);
        }
        else {
          this.GetDSToaNha();
        }
      }
    },
    timtheodoitac: {
      get() { return this.isTimTheoDoiTac; },
      set(value) {
        this.isTimTheoDoiTac = value;
        if (this.isTimTheoDoiTac) {
          this.listbox.ds_toanha.list = this.listbox.ds_toanha.list.filter(x => x.doitac_id == this.listbox.doitac_timkiem.value.id);
        }
        else {
          this.GetDSToaNha();
        }
      }
    },
  },
  watch: {
    slvp: function (val) {
      this.setValueQuyMoHD();
    },
    slcanho: function (val) {
      this.setValueQuyMoHD();
    },
    sl_shophouse: function (val) {
      this.setValueQuyMoHD();
    },
  },
  methods: {
    fetchAPI: async function () {
      this.listbox.thongtinQC.list = this.GetData(await API.tt_quangcao_toanha(this.axios, {})).map(x => ({
        id: x.tt_quangcao_id,
        text: x.tt_quangcao
      }))
      this.listbox.cpHH.list = this.GetData(await API.cp_hoahong_toanha(this.axios, {})).map(x => ({
        id: x.cp_hoahong_id,
        text: x.cp_hoahong
      }))
    },
    openFormLichSu() {
      this.$refs.LichSuModal.txtMaToaNha = this.matoanha;
      this.$refs.LichSuModal.showModal()
    },
    ShowAlert(mesage, val) {
      if (val == 0) {
        this.$toast.success(mesage)
      } else if (val == 1) {
        this.$toast.warning(mesage)
      } else if (val == 2) {
        this.$toast.error(mesage)
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    setValueQuyMoHD() {
      if (this.ma_tinh == 'HCM') {
        this.quymo_hopdong = this.slvp + this.sl_shophouse + this.slcanho;
      }
    },
    SetButton: async function (kieu) {
      this.isNhapMoi = false
      this.isGhiLai = false
      this.isHuy = false
      this.isXoa = false
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true
        this.isHuy = true
        this.isXoa = true
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true
        this.isGhiLai = true
        this.isXoa = true
        await this.Clear()
        this.$refs.grid.setCurrentSelectedRow(this.index)
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true
        this.isHuy = true
        this.Clear()
        /* this.$nextTick(function () {
          document.getElementById('thangcapnhat').focus()
        }) */
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true
        this.isXoa = true
        this.Clear()
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true
        this.isXoa = true
        this.isGhiLai = true
        this.isHuy = true
      }
    },
    getDiaChiTB: function (data) {
      this.getDiaChi(data, 'diachiTB')
    },
    getDiaChi: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DAC_DIEM_ID = data.dacdiem_id
      this[datatype].SONHA = data.so_nha
      this[datatype].KINHDO = data.lat
      this[datatype].VIDO = data.lng
      this.vds.PHUONG_ID = data.phuong_id
      this.vds.QUAN_ID = data.quan_id
      this.vds.DIACHI = data.diachimoi
      this.diachiTB.DIACHI = data.diachimoi
      this.diachiTB.TINH_ID = data.tinh_id
      this.diachiTB.QUAN_ID = data.quan_id
      this.diachiTB.PHUONG_ID = data.phuong_id
      this.diachiTB.PHO_ID = data.pho_id
      this.diachiTB.AP_ID = data.ap_id
      this.diachiTB.KHU_ID = data.khu_id
      this.diachiTB.DAC_DIEM_ID = data.dacdiem_id
      this.diachiTB.SONHA = data.so_nha
      this.diachiTB.KINHDO = data.lat
      this.diachiTB.VIDO = data.lng
    },
    Clear() {
      this.matoanha = ''
      this.tentoanha = ''
      /*  this.listbox.duan.value.id = obj.duan_id;
       this.listbox.htdautu.value.id = obj.dtda_id;
       this.listbox.doitac.value.id = obj.doitac_id;
       this.listbox.loaitoanha.value.id = obj.loaitn_id; */
      this.nguoidaidien = ''
      this.sdtbql = ''
      this.chucvu = ''
      this.email = ''
      /*  this.listbox.quymo.value.id = obj.quy_mo;
       this.listbox.loaicap.value.id = obj.loai_cap; */
      this.dientich = ''
      this.sltang = ''
      this.slvp = ''
      this.slcanho = ''
      //this.dthudukien = "";
      /* this.listbox.dvtiepthi.value.id = obj.donvi_tiepthi;
      this.listbox.dvquanly.value.id = obj.donvi_quanly;
      this.listbox.doivt.value.id = obj.doi_vt;
      this.listbox.chuquan.value.id = obj.chuquan_id; */
      this.tiendo = ''
      /* this.listbox.phamvi.value.id = obj.phamvi_dtu_mang;
      this.listbox.hientrang.value.id = obj.tinhtrang;
      this.listbox.phanchiadt.value.id = obj.thuchien_pcdt; */
      this.lydopcdt = ''
      //this.listbox.sansangpttb.value.id = obj.sansang_pttb;
      this.lydochuass = ''
      this.thoigianccdv = ''
      //this.listbox.tinhtrang.value.id = obj.tinhtrang;
      this.goicuoc = ''
      this.khuyenmai = ''
      this.ten_vt = ''
      this.ghichu = ''
      this.sl_block = 0
      this.doanhthu_dukien_n1 = 0
      this.doanhthu_dukien_n2 = 0
      this.doanhthu_dukien = 0
      this.quymo_hopdong = 0
      this.sl_canho_sd = 0
      this.quymo_vnpt_n1 = 0
      this.quymo_vnpt_n2 = 0
      this.quymo_vnpt = 0
      this.quymo_thucte = 0
      this.quymo_lapday = 0
      this.sl_canho_bg = 0
      this.sl_shophouse = 0
      this.ma_ibs = ''
      this.diachiTB.DIACHI = ''
      this.diachiTB.PHUONG_ID = 0
      this.diachiTB.QUAN_ID = 0
      this.listbox.dvquanly.value.id = 0
      this.listbox.dvtiepthi.value.id = 0
      this.listbox.doivt.value.id = 0
      this.ctv_bh = ''
      this.chkOLT.Checked = false
      this.chkBMIS.Checked = false
      this.chkVonDT.Checked = false
      this.dvQLVH = ''
      this.listbox.mhccdv.value.id = 0
      //this.$refs['matoanha'].focus()
    },
    InitMonthYear() {
      var currentyear = (new Date()).getFullYear()
      var currentmonth = (new Date()).getMonth()
      for (var i = currentyear - 5; i <= currentyear + 5; i++) {
        this.listbox.nam.list.push(i)
      }
      this.listbox.nam.value.id = currentyear
      this.listbox.thang.value.id = currentmonth
    },
    btnNhapMoi() {
      this.SetButton(1)
    },
    btnHuy() {
      this.SetButton(0)
    },
    queryCellInfo(args) {
      switch (args.column.field) {
        case 'doanhthu_dukien_n1':
          args.cell.innerText = this.numberWithCommas(args.data.doanhthu_dukien_n1)
          break;
        case 'doanhthu_dukien_n2':
          args.cell.innerText = this.numberWithCommas(args.data.doanhthu_dukien_n2)
          break;
        case 'doanhthu_dukien':
          args.cell.innerText = this.numberWithCommas(args.data.doanhthu_dukien)
          break;
      }
    },
    numberWithCommas(n) {
      try {
        let parts = n.toString().split(".");
        return (parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") + (parts[1] ? "." + parts[1] : ""));
      } catch (e) {
        return n;
      }
    },
    GetDS: async function () {
      //get ds dự án
      var input = {}
      this.loading(true)
      var datatem = []
      datatem = this.GetData(await API.sp_lay_tt_combobox_dm_toanha(this.axios, input))
      console.log(datatem)
      this.listbox.duan.list = datatem.dn.map(x => ({ id: x.duan_id, text: x.ma_duan + ' - ' + x.ten_duan, ma_duan: x.ma_duan }))
      this.listbox.duan_timkiem.list = this.listbox.duan.list
      if (this.listbox.duan.list.length > 0) {
        this.listbox.duan.value.id = this.listbox.duan.list[0].id
        this.listbox.duan_timkiem.value.id = this.listbox.duan_timkiem.list[0].id
      }
      this.listbox.dvtiepthi.list = datatem.dv_tiepthi.map(x => ({
        id: x.donvi_id,
        text: x.ten_dv
      })).filter(x => x.text != null)
      if (this.listbox.dvtiepthi.list.length > 0) {
        this.listbox.dvtiepthi.value.id = this.listbox.dvtiepthi.list[0].id
      }
      this.listbox.dvquanly.list = datatem.dv_quanly.map(x => ({
        id: x.donvi_id,
        text: x.ten_dv
      })).filter(x => x.text != null)
      if (this.listbox.dvquanly.list.length > 0) {
        this.listbox.dvquanly.value.id = this.listbox.dvquanly.list[0].id
      }
      this.loading(false)
      //get ds đối tác
      var input = {}
      this.loading(true)
      this.listbox.doitac.list = this.GetData(await API.QLTN_DOITAC(this.axios, input)).map(x => ({
        id: x.doitac_id,
        text: x.ten_dt
      }))
      this.listbox.doitac_timkiem.list = this.listbox.doitac.list
      if (this.listbox.doitac.list.length > 0) {
        this.listbox.doitac.value.id = this.listbox.doitac.list[0].id
        this.listbox.doitac_timkiem.value.id = this.listbox.doitac_timkiem.list[0].id
      }
      this.loading(false)
      //get ds tòa nhà
      var input = {}
      this.loading(true)
      this.listbox.loaitoanha.list = this.GetData(await API.CSS_LOAI_TOANHA(this.axios, input)).map(x => ({
        id: x.LOAITN_ID,
        text: x.TEN_LOAITN
      }))
      if (this.listbox.loaitoanha.list.length > 0) {
        this.listbox.loaitoanha.value.id = this.listbox.loaitoanha.list[0].id
      }
      this.loading(false)
      //get ds loại cáp tòa nhà
      var input = {}
      this.loading(true)
      this.listbox.loaicap.list = this.GetData(await API.CSS_LOAICAP_TOANHA(this.axios, input)).map(x => ({
        id: x.LOAICAP_ID,
        text: x.LOAICAP
      }))
      if (this.listbox.loaicap.list.length > 0) {
        this.listbox.loaicap.value.id = this.listbox.loaicap.list[0].id
      }
      this.loading(false)
      //get ds phạm vi đầu tư mạng
      var input = {}
      this.loading(true)
      this.listbox.phamvi.list = this.GetData(await API.CSS_PHAMVI_DTU_MANG(this.axios, input)).map(x => ({
        id: x.PVDT_ID,
        text: x.PHAMVI
      }))
      if (this.listbox.phamvi.list.length > 0) {
        this.listbox.phamvi.value.id = this.listbox.phamvi.list[0].id
      }
      this.loading(false)
      //get ds phân chia doanh thu
      var input = {}
      this.loading(true)
      this.listbox.phanchiadt.list = this.GetData(await API.CSS_TN_PHANCHIA_DT(this.axios, input)).map(x => ({
        id: x.PCDT_ID,
        text: x.TEN_PCDT
      }))
      if (this.listbox.phanchiadt.list.length > 0) {
        this.listbox.phanchiadt.value.id = this.listbox.phanchiadt.list[0].id
      }
      this.loading(false)
      //get ds đối tượng dự án
      var input = {}
      this.loading(true)
      this.listbox.htdautu.list = this.GetData(await API.CSS_DOITUONG_DUAN(this.axios, input)).map(x => ({
        id: x.DTDA_ID,
        text: x.LOAI_DTDA
      }))
      //this.listbox.duan_timkiem.list = this.listbox.duan.list
      if (this.listbox.htdautu.list.length > 0) {
        this.listbox.htdautu.value.id = this.listbox.htdautu.list[0].id
        // this.listbox.duan_timkiem.value.id = this.listbox.duan_timkiem.list[0].id;
      }
      this.loading(false)
      //get ds hiện trạng
      var input = {}
      this.loading(true)
      console.log(this.GetData(await API.CSS_HIENTRANG_TOANHA(this.axios, input)))
      this.listbox.hientrang.list = this.GetData(await API.CSS_HIENTRANG_TOANHA(this.axios, input)).map(x => ({
        id: x.hientrang_id,
        text: x.hientrang
      })).filter(x => x.text != null)
      if (this.listbox.hientrang.list.length > 0) {
        this.listbox.hientrang.value.id = this.listbox.hientrang.list[0].id
      }
      this.loading(false)
      //get ds chủ quản
      var input = {}
      this.loading(true)
      // this.listbox.chuquan.list = this.GetData(await API.CSS_CHUQUAN(this.axios, input)).map(x=>({id:x.CHUQUAN_ID,text:x.TENCHUQUAN}));
      // if (this.listbox.chuquan.list.length>0)
      // {
      //   this.listbox.chuquan.value.id = this.listbox.chuquan.list[0].id;
      // }
      this.loading(false)
      this.listbox.quymo.value.id = this.listbox.quymo.list[0].id
      this.listbox.tinhtrang.value.id = this.listbox.tinhtrang.list[0].id
      this.listbox.sansangpttb.value.id = this.listbox.sansangpttb.list[0].id
      this.listbox.mhccdv.list = this.GetData(await API.lay_loai_tt_duan(this.axios, {
        nghiepvu: "DANHMUC_MOHINH_TOANHA",
        ds_para: '',
      })).map(x => ({
        id: x.mohinh_id,
        text: x.ten_mohinh
      }))
      if (this.listbox.mhccdv.list.length > 0) {
        this.listbox.mhccdv.value.id = this.listbox.mhccdv.list[0].id
      }
    },
    DvQuanLyChange: async function () {
      //Load đội VT
      var input = {}
      input.donvi_id = this.listbox.dvquanly.value.id
      input.loaidv_id = this.TRAM_CAP_PORT
      //console.log(input);
      try {
        this.loading(true)
        let response = await API.sp_lay_ds_doi_vt_by_dvi(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          this.listbox.doivt.list = response.data.data.map(x => ({
            id: x.donvi_id,
            text: x.ten_dv
          })).filter(x => x.text != null)
          if (this.listbox.doivt.list.length > 0) {
            this.listbox.doivt.value.id = this.listbox.doivt.list[0].id
          }
        } else {
          console.log(response.data.error_code)
        }
        // this.listbox.doivt.list = this.GetData(await API.sp_lay_ds_doi_vt_by_dvi(this.axios, input)).map(x => ({
        //   id: x.donvi_id,
        //   text: x.ten_dv
        // }))
        this.loading(false)
      } catch (e) {
        this.loading(false)
        console.log(e)
      }
      try {
        var input1 = {
          'vkieu': 2,
          'vpbh_id': this.listbox.dvtiepthi.value.id,
          'vttvt_id': this.listbox.dvquanly.value.id,
          'vdoivt_id': this.listbox.doivt.value.id,
          'vnhanvien_id': this.$root.token.getNhanVienID(),
        }
        this.loading(true)
        this.listbox.nvkythuat.list = this.GetData(await API.lay_tt_nhanvien_toanha(this.axios, input1))
        this.listbox.nvkythuat.value.id = this.listbox.nvkythuat.list[0].nhanvien_id
        this.loading(false)
      } catch (e) {
        this.loading(false)
      }
    },
    GetDSToaNha: async function () {
      var input = {}
      //input.nhanvien_id = this.$root.token.getNhanVienID();
      if (this.isTimTheoDuAn) {
        input.duan_id = this.listbox.duan_timkiem.value.id
      } else {
        input.duan_id = 0
      }
      if (this.isTimTheoDoiTac) {
        input.doitac_id = this.listbox.doitac_timkiem.value.id
      } else {
        input.doitac_id = 0
      }
      input.page_num = 1
      input.page_sz = 1000
      input.total_row = 0
      //console.log(input);
      this.loading(true)
      if (this.ma_tinh == "HCM") {
        this.listbox.ds_toanha.header = this.headerHCM
      }
      else {
        this.listbox.ds_toanha.header = this.headerTinh
      }
      await API.sp_lay_ds_toanha(this.axios, input).then((response) => {
        this.loading(false)
        if (response.data.error_code && response.data.error_code === this.success_code) {
          this.listbox.ds_toanha.list = response.data.data.out
          //console.log(this.listbox.ds_toanha.list);
        }
      }
      )
    },
    btnLayTT() {
      this.GetDSToaNha()
    },
    DuAnChange() {
      if (this.timtheoduan) {
        this.listbox.ds_toanha.list = this.listbox.ds_toanha.list.filter(x => x.duan_id == this.listbox.duan_timkiem.value.id)
      }
    },
    DoiTacChange() {
      if (this.timtheodoitac) {
        this.listbox.ds_toanha.list = this.listbox.ds_toanha.list.filter(x => x.doitac_id == this.listbox.doitac_timkiem.value.id)
      }
    },
    rowClicked: async function (idx, obj) {
      if (!obj) {
        return
      }
      // this.p_duan_id = obj.duan_id
      // this.p_toanha_id = obj.toanha_id
      // this.index = idx
      // this.matoanha = obj.ma_toanha
      // this.tentoanha = obj.ten_toanha
      // this.listbox.duan.value.id = obj.duan_id
      // this.listbox.htdautu.value.id = obj.dtda_id
      // this.listbox.doitac.value.id = obj.doitac_id
      // this.listbox.loaitoanha.value.id = obj.loaitn_id
      // this.nguoidaidien = obj.nguoidd_bql
      // this.sdtbql = obj.sdt_bql
      // this.chucvu = obj.chucvu_bql
      // this.email = obj.email_bql
      // this.listbox.quymo.value.id = obj.quy_mo
      // this.listbox.loaicap.value.id = obj.loai_cap
      // this.dientich = obj.dientich
      // this.sltang = obj.sl_tang
      // this.slvp = obj.sl_vp
      // this.slcanho = obj.sl_canho
      // //this.dthudukien = obj.doanhthu_dukien;
      // this.listbox.dvtiepthi.value.id = obj.donvi_tiepthi
      // this.listbox.dvquanly.value.id = obj.donvi_quanly
      // await this.DvQuanLyChange()
      // this.listbox.doivt.value.id = obj.doi_vt
      // //this.listbox.chuquan.value.id = obj.chuquan_id;
      // this.tiendo = obj.tiendo
      // this.listbox.phamvi.value.id = obj.phamvi_dtu_mang
      // this.listbox.hientrang.value.id = obj.tinhtrang
      // this.listbox.phanchiadt.value.id = obj.thuchien_pcdt
      // this.lydopcdt = obj.lydo_pcdt
      // this.listbox.sansangpttb.value.id = obj.sansang_pttb
      // this.lydochuass = obj.lydo_chua_ss
      // this.thoigianccdv = moment(obj.tg_ccdv).format('DD/MM/YYYY HH:mm:ss')
      // this.listbox.tinhtrang.value.id = obj.tinhtrang
      // this.goicuoc = obj.goi_cuoc
      // this.khuyenmai = obj.khuyenmai
      // this.ten_vt = ''
      // this.ghichu = obj.dexuat
      // this.toanhaid = obj.toanha_id
      //
      // //hieubh.hdg bổ sung thông tin Địa chỉ
      // this.quan_id
      // this.diachiTB.DIACHI = obj.diachi
    },
    gridChanged: async function (obj) {
      if (!obj) {
        return
      }
      console.log(obj);
      this.Clear();
      this.p_duan_id = obj.duan_id
      this.p_toanha_id = obj.toanha_id
      this.matoanha = obj.ma_toanha
      this.tentoanha = obj.ten_toanha == null ? '' : obj.ten_toanha
      this.listbox.duan.value.id = obj.duan_id
      this.listbox.htdautu.value.id = obj.dtda_id
      this.listbox.doitac.value.id = obj.doitac_id
      this.listbox.loaitoanha.value.id = obj.loaitn_id
      this.nguoidaidien = obj.nguoidd_bql == null ? '' : obj.nguoidd_bql
      this.sdtbql = obj.sdt_bql == null ? '' : obj.sdt_bql
      this.chucvu = obj.chucvu_bql == null ? '' : obj.chucvu_bql
      this.email = obj.email_bql == null ? '' : obj.email_bql
      this.listbox.quymo.value.id = obj.quy_mo
      this.listbox.loaicap.value.id = obj.loai_cap
      this.dientich = obj.dientich == null ? 0 : obj.dientich
      this.sltang = obj.sl_tang == null ? 0 : obj.sl_tang
      this.slvp = obj.sl_vp == null ? 0 : obj.sl_vp
      this.slcanho = obj.sl_canho == null ? 0 : obj.sl_canho
      this.doanhthu_dukien = obj.doanhthu_dukien == null ? 0 : obj.doanhthu_dukien
      this.listbox.dvtiepthi.value.id = obj.donvi_tiepthi
      this.listbox.dvquanly.value.id = obj.donvi_quanly
      this.listbox.hientrang.value.id = obj.hientrang_id
      await this.DvQuanLyChange()
      await this.cboDVTiepThi_EditValueChanged()
      this.listbox.doivt.value.id = obj.doi_vt
      //this.listbox.chuquan.value.id = obj.chuquan_id;
      this.tiendo = obj.tiendo == null ? '' : obj.tiendo
      this.listbox.phamvi.value.id = obj.phamvi_dtu_mang
      this.listbox.phanchiadt.value.id = obj.thuchien_pcdt
      this.lydopcdt = obj.lydo_pcdt == null ? '' : obj.lydo_pcdt
      this.listbox.sansangpttb.value.id = obj.sansang_pttb
      this.lydochuass = obj.lydo_chua_ss == null ? '' : obj.lydo_chua_ss
      this.thoigianccdv = moment(obj.tg_ccdv).format('DD/MM/YYYY HH:mm:ss')
      this.listbox.tinhtrang.value.id = obj.tinhtrang
      this.goicuoc = obj.goi_cuoc == null ? '' : obj.goi_cuoc
      this.khuyenmai = obj.khuyenmai == null ? '' : obj.khuyenmai
      this.ten_vt = obj.ten_vt == null ? '' : obj.ten_vt
      this.ghichu = obj.ghichu == null ? '' : obj.ghichu
      this.toanhaid = obj.toanha_id
      this.diachiTB.DIACHI = obj.diachi
      this.diachiTB.AP_ID = obj.ap_id == null ? 0 : obj.ap_id
      this.diachiTB.QUAN_ID = obj.quan_id == null ? 0 : obj.quan_id
      this.diachiTB.PHUONG_ID = obj.phuong_id == null ? 0 : obj.phuong_id
      this.diachiTB.PHO_ID = obj.pho_id == null ? 0 : obj.pho_id
      this.diachiTB.KHU_ID = obj.khu_id == null ? 0 : obj.khu_id
      this.diachiTB.DAC_DIEM_ID = obj.dacdiem_id == null ? 0 : obj.dacdiem_id
      this.diachiTB.SONHA = obj.sonha == null ? '' : obj.sonha
      this.diachiTB.KINH_DO = obj.kinh_do == null ? 0 : obj.kinh_do
      this.diachiTB.VI_DO = obj.vi_do == null ? 0 : obj.vi_do
      this.ma_ibs = obj.ma_ibs == null ? '' : obj.ma_ibs
      this.sl_block = obj.sl_block == null ? 0 : obj.sl_block
      this.sl_shophouse = obj.sl_shophouse == null ? 0 : obj.sl_shophouse
      this.quymo_thucte = obj.quymo_thucte == null ? 0 : obj.quymo_thucte
      this.sl_canho_sd = obj.sl_canho_sd == null ? 0 : obj.sl_canho_sd
      this.sl_canho_bg = obj.sl_canho_bg == null ? 0 : obj.sl_canho_bg
      this.listbox.nvbanhang.value.id = obj.nvbh_id
      this.listbox.nvkythuat.value.id = obj.nvkt_id
      this.listbox.nvcskh.value.id = obj.cskh_id
      this.quymo_lapday = obj.quymo_lapday == null ? 0 : obj.quymo_lapday
      this.quymo_vnpt = obj.quymo_vnpt == null ? 0 : obj.quymo_vnpt
      this.quymo_vnpt_n1 = obj.quymo_vnpt_n1 == null ? 0 : obj.quymo_vnpt_n1
      this.quymo_vnpt_n2 = obj.quymo_vnpt_n2 == null ? 0 : obj.quymo_vnpt_n2
      this.doanhthu_dukien_n1 = obj.doanhthu_dukien_n1 == null ? 0 : obj.doanhthu_dukien_n1
      this.doanhthu_dukien_n2 = obj.doanhthu_dukien_n2 == null ? 0 : obj.doanhthu_dukien_n2
      this.quymo_hopdong = obj.quymo_hopdong == null ? 0 : obj.quymo_hopdong
      this.ngay_bg_canho = moment(obj.ngay_bg_canho).format('DD/MM/YYYY HH:mm:ss')
      this.ctv_bh = obj.ctv_banhang
      this.chkVonDT.Checked = obj.von_dautu == 1 ? true : false
      this.chkOLT.Checked = obj.olt_xhh == 1 ? true : false
      this.chkBMIS.Checked = obj.qltn_bmis == 1 ? true : false
      this.dvQLVH = obj.donvi_qlvh
      this.listbox.mhccdv.value.id = obj.mohinh_ccdv
      this.SetButton(3);
      if (obj.ttda_giao_dlbh != null) {
        this.ttda = obj.ttda_giao_dlbh == 1 ? true : false
      }
      if (obj.goicuoc_toithieu != null) {
        this.goiCuocToiThieu = obj.goicuoc_toithieu == 1 ? true : false
      }
      if (obj.tinhtrang_hatang != null) {
        this.TTHT = obj.tinhtrang_hatang == 1 ? true : false
      }
      if (obj.nhamang_khac != null) {
        this.nhaMangKhac = obj.nhamang_khac == 1 ? true : false
      }
      if (obj.tt_quangcao_id != null) {
        this.listbox.thongtinQC.value.id = obj.tt_quangcao_id
      }
      if (obj.cp_hoahong_id != null) {
        this.listbox.cpHH.value.id = obj.cp_hoahong_id
      }



    },
    async Kiemtra_DL(Them_Moi) {
      let input = "chucNang=TOANHA&" + "kieu=" + (Them_Moi ? 1 : 2) + "&id=" + this.toanhaid + "&ttvtId=" + this.listbox.dvquanly.value.id
      console.log(input)
      let response1 = await API.kiemtra_quyen_toanha(this.axios, input)
      if (response1.data.error_code && response1.data.error_code === this.success_code) {
        if (response1.data.data != 'OK') {
          this.$toast.warning(response1.data.data)
          return false
        }
      }
      if (this.tentoanha == '') {
        this.$toast.error('Hãy nhập Tên tòa nhà!')
        //this.$refs['tentoanha'].focus()
        return false
      }
      if (this.ma_tinh == 'HCM' && (this.listbox.mhccdv.value.id == null || this.listbox.mhccdv.value.id == -1 || this.listbox.mhccdv.value.id == 0)) {
        this.$toast.error('Bạn chưa chọn Mô hình cung cấp dịch vụ!')
        return false
      }
      if (this.ma_tinh == 'HNI' && (this.listbox.mhccdv.value.id == null || this.listbox.mhccdv.value.id == -1 || this.listbox.mhccdv.value.id == 0)) {
        this.$toast.error('Bạn chưa chọn Mô hình cung cấp dịch vụ!')
        return false
      }
      if (this.listbox.duan.value.id == null || this.listbox.duan.value.id == 0 || this.listbox.duan.value.id == undefined) {
        this.$toast.error('Bạn chưa chọn Dự án!')
        return false
      }
      if (this.listbox.htdautu.value.id == null || this.listbox.htdautu.value.id == undefined || this.listbox.htdautu.list == 0) {
        this.$toast.error('Bạn chưa chọn Hình thức đầu tư')
        return false
      }
      if (this.listbox.doitac.value.id == null || this.listbox.doitac.value.id == undefined)//TN tiềm năng
      {
        this.$toast.error('Bạn chưa chọn Đối tác')
        return false
      }
      if (this.listbox.loaitoanha.value.id == null || this.listbox.loaitoanha.value.id == undefined || this.listbox.loaitoanha.length == 0) {
        this.$toast.error('Bạn chưa chọn Loại tòa nhà')
        return false
      }
      if (this.diachiTB.QUAN_ID == 0 || this.diachiTB.PHUONG_ID == 0 || this.diachiTB.DIACHI == '' || this.diachiTB.QUAN_ID == null || this.diachiTB.PHUONG_ID == null) {
        this.$toast.error('Bạn chưa chọn Địa chỉ (Quận/Huyện, Phường/Xã)')
        return false
      }
      if (this.listbox.quymo.value.id == null || this.listbox.quymo.value.id == undefined || this.listbox.quymo.list.length == 0) {
        this.$toast.error('Bạn chưa chọn Quy mô tòa nhà')
        return false
      }
      if (this.listbox.loaicap.value.id == null || this.listbox.loaicap.value.id == undefined || this.listbox.loaicap.list == 0) {
        this.$toast.error('Bạn chưa chọn Loại cáp')
        return false
      }
      if (this.ma_tinh == 'HCM') {
        let kiemtra_toanha = false
        if (Them_Moi == true) {
          // string vmatn = btd.GET_VALUE_FUNC("{?DB2}.quanly_toanha.lay_ma_toanha", "vduan_id", Convert.ToInt32(this.listbox.duan.value.id)).ToString();
          // if (Convert.ToInt32(vmatn.Substring(2, 2)) < 20)
          //   kiemtra_toanha = true;
          let du_an = this.listbox.duan.list.filter(x => x.id == this.listbox.duan.value.id);
          if (du_an[0].ma_duan.substring(2, 4) >= 23) {
            kiemtra_toanha = true;
          }
        } else {
          if ((this.matoanha.substring(2, 4)) >= 23) {
            kiemtra_toanha = true
          }
        }
        if (kiemtra_toanha) {
          if (this.quymo_vnpt_n1 == '' || this.quymo_vnpt_n1 == '0') {
            this.$toast.error('Quy mô VNPT (Năm 1) phải > 0')
            //this.$refs['quymo_vnpt_n1'].focus();
            return false
          }
          if (this.quymo_vnpt_n2 == '' || this.quymo_vnpt_n2 == '0') {
            this.$toast.error('Quy mô VNPT (Năm 2) phải > 0')
            //this.$refs['quymo_vnpt_n2'].focus();
            return false
          }
          if (this.quymo_vnpt == '' || this.quymo_vnpt == '0') {
            this.$toast.error('Quy mô VNPT (Năm 3) phải > 0')
            //this.$refs['quymo_vnpt'].focus()
            return false
          }
          if (this.doanhthu_dukien_n1 == '' || this.doanhthu_dukien_n1 == '0') {
            this.$toast.error('Doanh thu dự kiến (Năm 1) phải > 0')
            //this.$refs['doanhthu_dukien_n1'].focus()
            return false
          }
          if (this.doanhthu_dukien_n2 == '' || this.doanhthu_dukien_n2 == '0') {
            this.$toast.error('Doanh thu dự kiến (Năm 2) phải > 0')
            //this.$refs['doanhthu_dukien_n2'].focus()
            return false
          }
          if (this.doanhthu_dukien == '' || this.doanhthu_dukien == '0') {
            this.$toast.error('Doanh thu dự kiến (Năm 3) phải > 0')
            //this.$refs['doanhthu_dukien'].focus()
            return false
          }
        }
      }
      if (this.listbox.dvtiepthi.value.id == 0 || this.listbox.dvtiepthi.value.id == undefined || this.listbox.dvtiepthi.length == 0) {
        this.$toast.error('Bạn chưa chọn Đơn vị tiếp thị')
        return false
      }
      if (this.listbox.dvquanly.value.id == 0 || this.listbox.dvquanly.value.id == undefined || this.listbox.dvquanly.list.length == 0) {
        this.$toast.error('Bạn chưa chọn Đơn vị quản lý')
        return false
      }
      if (this.listbox.doivt.value.id == 0 || this.listbox.doivt.value.id == undefined || this.listbox.doivt.list.length == 0) {
        this.$toast.error('Bạn chưa chọn Đội viễn thông')
        return false
      }
      if (this.listbox.phamvi.value.id == null || this.listbox.phamvi.value.id == undefined || this.listbox.phamvi.list.length == 0) {
        this.$toast.error('Bạn chưa chọn Phạm vi đầu tư mạng')
        return false
      }
      if (this.tentoanha.length > 500) {
        this.$toast.error('Tên tòa nhà không được lớn hơn 500 ký tự')
        //this.$refs['tentoanha'].focus()
        return false
      }
      if (this.ten_vt.length > 200) {
        this.$toast.error('Tên tắt không được lớn hơn 200 ký tự')
        //this.$refs['ten_vt'].focus()
        return false
      }
      if (this.goicuoc.length > 200) {
        this.$toast.error('Gói cước không được lớn hơn 200 ký tự')
        //this.$refs['goicuoc'].focus()
        return false
      }
      if (this.khuyenmai.length > 200) {
        this.$toast.error('Khuyến mại không được lớn hơn 200 ký tự')
        //this.$refs['khuyenmai'].focus()
        return false
      }
      if (this.ghichu.length > 500) {
        this.$toast.error('Ghi chú không được lớn hơn 500 ký tự')
        //this.$refs['ghichu'].focus()
        return false
      }
      if (this.lydochuass.length > 500) {
        this.$toast.error('Lý do chưa sẵn sàng không được lớn hơn 500 ký tự')
        //this.$refs['lydochuass'].focus()
        return false
      }
      if (this.sdtbql.Length > 11) {
        this.$toast.error('Số điện thoại không được quá 11 ký tự')
        //this.$refs['sdtbql'].focus()
        return false
      }
      if (this.sdtbql.Length < 7) {
        this.$toast.error('Số điện thoại không được ít hơn 7 ký tự')
        //this.$refs['sdtbql'].focus()
        return false
      }
      if (this.tiendo.length > 200) {
        this.$toast.error('Tiến độ không được lớn hơn 200 ký tự')
        //this.$refs['tiendo'].focus()
        return false
      }
      if (this.sltang == undefined || this.sltang.length > 5) {
        this.$toast.error('Giá trị số tầng không hợp lệ')
        //this.$refs['sltang'].focus()
        return false
      }
      if (this.dientich == undefined || this.dientich.length > 8) {
        this.$toast.error('Giá trị diện tích không hợp lệ')
        //this.$refs['sltang'].focus()
        return false
      }
      if (this.slvp < 0 || this.slvp > 99999) {
        this.$toast.error('Giá trị số lượng văn phòng không hợp lệ')
        //this.$refs['slvp'].focus()
        return false
      }
      if (this.slcanho < 0 || this.slcanho > 99999) {
        this.$toast.error('Giá trị số lượng căn hộ không hợp lệ')
        //this.$refs['slcanho'].focus()
        return false
      }
      if (this.sl_shophouse < 0 || this.sl_shophouse > 99999) {
        this.$toast.error('Giá trị số lượng Shophouse không hợp lệ')
        //this.$refs['slcanho'].focus()
        return false
      }
      if (this.ma_tinh == 'HCM' && (this.quymo_hopdong <= 0 || this.quymo_hopdong > 99999)) {
        this.$toast.error('Số lượng Quy mô Hợp đồn không hợp lệ')
        //this.$refs['slcanho'].focus()
        return false
      }
      return true
    },

    btnGhiLai: async function () {
      var data = {}
      data.TIENDO = this.tiendo
      data.SONHA = this.diachiTB.SONHA
      data.DV_QUANLY = this.listbox.dvquanly.value.id
      data.KHU_ID = this.diachiTB.KHU_ID
      data.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      data.DIENTICH = this.dientich
      data.MAY_CN = this.$root.token.getMachine()
      //data.TEN_LOAITN=
      data.NGUOIDD_BQL = this.nguoidaidien
      data.LOAICAP = this.listbox.loaicap.value.id
      data.QUAN_ID = this.diachiTB.QUAN_ID
      //data.TEN_QUAN=
      data.DTDA_ID = this.listbox.htdautu.value.id
      data.AP_ID = this.diachiTB.AP_ID
      data.SDT_BQL = this.sdtbql
      data.DONVI_QUANLY = this.listbox.dvquanly.value.id
      data.SL_TANG = this.sltang
      data.HIENTRANG_ID = this.listbox.hientrang.value.id
      //data.PHANVUNG_ID=
      data.GHICHU = this.ghichu
      data.VI_DO = this.diachiTB.VIDO
      //data.TEN_PHUONG=
      data.THUCHIEN_PCDT = this.listbox.phanchiadt.value.id
      data.DOI_VT = this.listbox.doivt.value.id
      data.QUY_MO = this.listbox.quymo.value.id
      data.DIACHI = this.diachiTB.DIACHI
      //data.TEN_QUAN=
      //data.STT=
      data.LOAI_CAP = this.listbox.loaicap.value.id
      data.KINH_DO = this.diachiTB.KINHDO
      data.DONVI_TIEPTHI = this.listbox.dvtiepthi.value.id
      //data.LOAI_DTDA=
      data.EMAIL_BQL = this.email
      data.TG_CCDV = this.thoigianccdv.substring(0, 10)
      data.PHUONG_ID = this.diachiTB.PHUONG_ID
      data.NGUOI_CN = this.$root.token.getUserName()
      //data.TOANHA_ID:null,
      //data.TEN_DT=
      data.GOI_CUOC = this.goicuoc
      //data.TEN_QUYMO=
      data.MA_TOANHA = this.matoanha
      //data.TEN_CHUDTU=
      data.LYDO_CHUA_SS = this.lydochuass
      data.TINHTRANG = this.listbox.tinhtrang.value.id
      data.DUAN_ID = this.listbox.duan.value.id
      data.LYDO_PCDT = this.lydopcdt
      data.KHUYENMAI = this.khuyenmai
      data.DOITAC_ID = this.listbox.doitac.value.id
      data.PHAMVI_DTU_MANG = this.listbox.phamvi.value.id
      data.DV_TIEPTHI = this.listbox.dvtiepthi.value.id
      data.SL_VP = this.slvp
      data.PHO_ID = this.diachiTB.PHO_ID
      data.CHUCVU_BQL = this.chucvu
      data.SL_CANHO = this.slcanho
      data.LOAITN_ID = this.listbox.loaitoanha.value.id
      data.TEN_TOANHA = this.tentoanha
      //data.DACDIEM_ID:null,
      //data.CHUQUAN_ID = this.listbox.chuquan.value.id;
      data.IP_CN = this.$root.token.getIP()
      data.SANSANG_PTTB = this.listbox.sansangpttb.value.id
      //data.DOANHTHU_DUKIEN= this.dthudukien;
      data.TOANHA_ID = this.toanhaid
      //hieubh.hdg bổ sung thêm ngày 27/03/2023
      data.TEN_VT = this.ten_vt
      data.SL_BLOCK = this.sl_block
      data.QUYMO_HOPDONG = this.quymo_hopdong
      data.QUYMO_THUCTE = this.quymo_thucte
      data.SL_CANHO_SD = this.sl_canho_sd
      data.SL_CANHO_BG = this.sl_canho_bg
      data.NVBH_ID = this.listbox.nvbanhang.value.id
      data.NVKT_ID = this.listbox.nvkythuat.value.id
      data.CSKH_ID = this.listbox.nvcskh.value.id
      data.MA_IBS = this.ma_ibs
      data.QUYMO_LAPDAY = this.quymo_lapday
      data.QUYMO_VNPT = this.quymo_vnpt
      data.DOANHTHU_DUKIEN = this.doanhthu_dukien
      if (this.ma_tinh == 'HCM') {
        data.QUYMO_VNPT_N1 = this.quymo_vnpt_n1
        data.QUYMO_VNPT_N2 = this.quymo_vnpt_n2
        data.DOANHTHU_DUKIEN_N1 = this.doanhthu_dukien_n1
        data.DOANHTHU_DUKIEN_N2 = this.doanhthu_dukien_n2
      }
      data.SL_SHOPHOUSE = this.sl_shophouse
      if (this.ngay_bg_canho != undefined && this.ngay_bg_canho != "") {
        data.NGAY_BG_CANHO = this.ngay_bg_canho.substring(0, 10)
      }
      else {
        data.NGAY_BG_CANHO = "";
      }
      data.VON_DAUTU = this.chkVonDT.Checked == true ? 1 : 0
      data.OLT_XHH = this.chkOLT.Checked == true ? 1 : 0
      data.QLTN_BMIS = this.chkBMIS.Checked == true ? 1 : 0
      data.DONVI_QLVH = this.dvQLVH
      data.MOHINH_CCDV = this.listbox.mhccdv.value.id
      data.CTV_BANHANG = this.ctv_bh
      data.TTDA_GIAO_DLBH = this.ttda == true ? 1 : 0
      data.GOICUOC_TOITHIEU = this.goiCuocToiThieu == true ? 1 : 0
      data.TINHTRANG_HATANG = this.TTHT == true ? 1 : 0
      data.TT_QUANGCAO_ID = this.listbox.thongtinQC.value.id
      data.CP_HOAHONG_ID = this.listbox.cpHH.value.id
      data.NHAMANG_KHAC = this.nhaMangKhac == true ? 1 : 0
      data.ONT_XHH = this.checkONT == true ? 1 : 0
      //END bổ sung

      if (this.isNhapMoi) //sửa
      {
        let result = await this.Kiemtra_DL(false)
        if (!result) return
        var input = {}
        input.check_new = 0 //Cập nhật
        input.duan_id = this.listbox.duan.value.id
        input.data = JSON.stringify(data)
        this.loading(true)
        await API.sp_ghilai_toanha(this.axios, input).then((response) => {
          this.loading(false)
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert('Cập nhật tòa nhà thành công!', 0)
            this.GetDSToaNha()
          } else {
            this.ShowAlert('Lỗi khi cập nhật tòa nhà!', 2)
          }
        }
        )
      } else //thêm mới
      {
        let result = await this.Kiemtra_DL(true)
        if (!result) return
        var input = {}
        input.check_new = 1 //thêm mới
        input.duan_id = this.listbox.duan.value.id
        input.data = JSON.stringify(data)
        this.loading(true)
        await API.sp_ghilai_toanha(this.axios, input).then((response) => {
          this.loading(false)
          if (response.data.error_code && response.data.error_code === this.success_code) {
            this.ShowAlert('Thêm mới tòa nhà thành công!', 0)
            this.GetDSToaNha()
          } else {
            this.ShowAlert('Lỗi khi thêm mới tòa nhà!', 2)
          }
        }
        )
      }
    },
    btnXoa: async function () {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá tòa nhà này không?', {
        title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
      }).then(async v => {
        if (v) {
          var input = {}
          input.id = this.toanhaid
          //console.log(input);
          this.loading(true)
          await API.sp_del_toanha_by_id(this.axios, input).then((response) => {
            this.loading(false)
            if (response.data.error_code && response.data.error_code === this.success_code) {
              this.ShowAlert('Xóa tòa nhà thành công!', 0)
              this.index = 0
              this.GetDSToaNha()
            } else {
              this.ShowAlert(response.data.message, 2)
            }
          }
          )
        }
      })
    },
    btnGanIMS() {
      this.popupComponent = () => import('./components/AssignBuildingToIMSProject.vue')
      this.popupComponentName = 'AssignBuildingToIMSProject'
      this.popupTitle = 'Gán danh mục toà nhà với dự án IMS'
      //console.log(this.TenDA);
      this.popupComponentData = { vToaNhaId: this.toanhaid, vTenToaNha: this.tentoanha }
      this.$bvModal.show('popupComponents')
    },
    popupClosed(val) {
      //console.log(val);
      this.$bvModal.hide('popupComponents')
    },
    btnXuatExcel() {
      console.log(this.listbox.ds_toanha.list)
      if (this.listbox.ds_toanha.list.length <= 0) {
        this.$toast.error('Chưa có dữ liệu để xuất excel!')
      } else {
        // let header = {fieldName: 'doanhthu_dukien_n1', headerText: 'Doanh thu dự kiến N1', allowFiltering: true, allowSorting: false, width: 200};
        // this.listbox.ds_toanha.header.push(header)
        let excelExportProperties = {
          fileName: 'danh-muc-toa-nha.xlsx',
          dataSource: this.listbox.ds_toanha.list,
        }
        this.$refs.grid.excelExport(excelExportProperties)
      }
    },
    // Lấy TT thuê bao
    tsbtnTT_TB() {
      this.$refs.tbToaNha.openPopup()
    },
    //hieubh bổ sung 27/03/2023
    async cboDVTiepThi_EditValueChanged() {
      try {
        var input = {
          'vkieu': 1,
          'vpbh_id': this.listbox.dvtiepthi.value.id,
          'vttvt_id': this.listbox.dvquanly.value.id,
          'vdoivt_id': this.listbox.doivt.value.id,
          'vnhanvien_id': this.$root.token.getNhanVienID(),
        }
        //this.loading(true)
        let response = await API.lay_tt_nhanvien_toanha(this.axios, input)
        if (response.data.error === 200 || response.data.error === '200') {
          this.listbox.nvbanhang.list = response.data.data
          //   .map(x => ({
          //   id: x.nhanvien_id,
          //   text: x.ten_nv
          // })).filter(x => x.text != null)
          if (this.listbox.nvbanhang.list.length > 0) {
            this.listbox.nvbanhang.value.id = this.listbox.nvbanhang.list[0].nhanvien_id
          }
        } else {
          console.log(response.data.error_code)
        }
        this.loading(false)
      } catch (e) {
        console.log(e)
        this.loading(false)
      }
      try {
        var input1 = {
          'vkieu': 3,
          'vpbh_id': this.listbox.dvtiepthi.value.id,
          'vttvt_id': this.listbox.dvquanly.value.id,
          'vdoivt_id': this.listbox.doivt.value.id,
          'vnhanvien_id': this.$root.token.getNhanVienID(),
        }
        this.loading(true)
        this.listbox.nvcskh.list = this.GetData(await API.lay_tt_nhanvien_toanha(this.axios, input1))
        //   .map(x => ({
        //   id: x.nhanvien_id,
        //   text: x.ten_nv,
        // })).filter(x => x.text != null)
        this.listbox.nvcskh.value.id = this.listbox.nvcskh.list[0].nhanvien_id
        this.loading(false)
      } catch (e) {
        this.loading(false)
      }
    },
    async cboDuAn_EditValueChanged() {
      let response = await API.lay_ds_diachi_duan(this.axios, { "vduan_id": this.listbox.duan.value.id });
      if (response.data.error_code && response.data.error_code === this.success_code) {
        let result = response.data.data;
        if (result.length > 0) {
          if (result[0].quan_id != null && result[0].quan_id != "") {
            this.diachiTB.QUAN_ID = result[0].quan_id;
          }
          if (result[0].phuong_id != null && result[0].phuong_id != "") {
            this.diachiTB.PHUONG_ID = result[0].phuong_id
          }
          if (result[0].dacdiem_id != null && result[0].dacdiem_id != "") {
            this.diachiTB.DAC_DIEM_ID = result[0].dacdiem_id
          }
          if (result[0].pho_id != null && result[0].pho_id != "") {
            this.diachiTB.PHO_ID = result[0].pho_id
          }
          if (result[0].ap_id != null && result[0].ap_id != "") {
            this.diachiTB.AP_ID = result[0].ap_id
          }
          if (result[0].khu_id != null && result[0].khu_id != "") {
            this.diachiTB.KHU_ID = result[0].khu_id
          }
          this.diachiTB.SONHA = result[0].sonha
          this.diachiTB.DIACHI = result[0].diachi
          if (result[0].vido != null && result[0].vido != "") {
            this.diachiTB.VI_DO = result[0].vido
          }
          if (result[0].kinhdo != null && result[0].khu_id != "") {
            this.diachiTB.KINH_DO = result[0].kinhdo
          }
          if (this.ma_tinh == "HCM") {
            this.tentoanha = result[0].ten_duan
          }
        }
      }
    },
    check() {
      if (this.nhaMangKhac == true) {
        this.checkNhaMang = true
      } else {
        this.checkNhaMang = false
      }

    },
    chiTietNhaMang() {
      this.$refs['nhacc'].toanha_id = this.p_toanha_id == null ? "" : this.p_toanha_id
      this.$refs['nhacc'].showModal()
    }
  },
}
</script>