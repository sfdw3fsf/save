<template src="./DeclareLiquidateITServiceDHNV.html"></template>
<script>

import breadcrumb from '@/components/breadcrumb'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import API from '../SubscriberLiquidationHost/API'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import DichVuVienThong from '../SubscriberLiquidationHost/DichVuVienThong.json'
import TRANGTHAI_DONGBO from '../SubscriberLiquidationHost/TRANGTHAI_DONGBO.json'
import TrangThaiHD from '../SubscriberLiquidationHost/TrangThaiHD.json'
import LoaiHinhTB from '../SubscriberLiquidationHost/LoaiHinhTB.json'
import GiaoPhieuNV from '@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue'
import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue'
import {LOAI_DV, LoaiHopDong} from '../../../../const/enums'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import NHOM_KQXL from '../SubscriberLiquidationHost/NHOM_KQXL.json'
// import traphieu from '@/modules/contract/setup/ReturnInstallTicket/components/traphieu'
import frmTraPhieuTC from "../ReturnInstallTicket/frmTraPhieuTC.vue";
import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import DuAnHDTB from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB.vue'
import ThongTinHenKhachHangModal
  from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import DoKiemModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/DoKiem/DoKiemModal.vue'
import TCThongTinNo from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/TCThongTinNo/TCThongTinNo'
import SearchContractModal
  from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import CapVatTuTB from '@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'
import TraCuuThongSoKTModal from '../DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal'
// CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
// JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
import Vue from 'vue'
import moment from 'moment'
import ThayDoiLyDoHuy from '../ChangeServiceOMCVIPCM/popups/ThayDoiLyDoHuy/ThayDoiLyDoHuy'
import DONGBO_GPHONE from '../SubscriberLiquidationHost/DONGBO_GPHONE.json'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import DangNhapIVAN from '../SubscriberLiquidationHost/DangNhapIVAN.vue'
import CreateIssue from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue.vue'
import GiaoPhieuNet from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupGiaoPhieuNet/GiaoPhieuNet.vue'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';

export default {
  components: {
    DatePicker,
    breadcrumb,
    API,
    DichVuVienThong,
    TRANGTHAI_DONGBO,
    TrangThaiHD,
    LoaiHinhTB,
    GiaoPhieuNV,
    VatTuThueBao,
    ExportDataModal,
    NHOM_KQXL,
    // traphieu,
    frmTraPhieuTC,
    ChuyenTo,
    DuAnHDTB,
    ThongTinHenKhachHangModal,
    DoKiemModal,
    TCThongTinNo,
    SearchContractModal,
    CapVatTuTB,
    TraCuuThongSoKTModal,
    KDatePicker,
    ThayDoiLyDoHuy,
    InBienBan,
    DangNhapIVAN,
    CreateIssue,
    GiaoPhieuNet,
    GiaoPhieuView
  },
  name: 'SubscriberLiquidationHost',
  data () {
    return {
      // headTitle: 'Host - thanh lý thuê số điện thoại',
      header: {
        title: 'Host - thanh lý thuê số điện thoại',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } },
        ]
      },
      actions: [
        {
          id: 'btnLayTTMoi',
          name: 'Lấy TT',
          active: true,
          icon_class: 'one-file-attach',
          visible: true
        },
        {
          id: 'tsbtnChapNhan',
          name: 'Cập nhật',
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
          id: 'tsbtnKichHoat',
          name: 'Kích hoạt',
          active: false,
          icon_class: 'one-file-arrow-up',
          visible: true
        },
        {
          id: 'tsbtnGiaoPhieu',
          name: 'Giao phiếu',
          active: false,
          icon_class: 'one-file-arrow-right1',
          visible: true
        },
        {
          id: 'tsbtnGiaoPhoiHop',
          name: 'Giao P/H',
          active: false,
          icon_class: 'one-file-arrow-right1',
          visible: true
        },
        {
          id: 'tsbtnHoanCong',
          name: 'Hoàn công',
          active: false,
          icon_class: 'nc-icon-glyph one-hoancong',
          visible: true
        },
        {
          id: 'tsbtnHoanThanh',
          name: 'Hoàn thành',
          active: false,
          icon_class: 'one-file-arrow-right1',
          visible: false
        },
        {
          id: 'tsbtnVatTu',
          name: 'Vật tư',
          active: true,
          icon_class: 'one-vt',
          visible: true
        },
        {
          id: 'tsbtnVatTuMoi',
          name: 'Vật tư',
          active: true,
          icon_class: 'one-vt',
          visible: false
        },
        {
          id: 'tsbtnHoanThienHS',
          name: 'Hoàn thiện',
          active: false,
          icon_class: 'one-file-check',
          visible: true
        },
        {
          id: 'tsbtnXuatFileTL',
          name: 'Xuất file',
          active: true,
          icon_class: 'one-print',
          visible: false
        },
        {
          id: 'btnInDS',
          name: 'In phiếu',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnXuatExcel',
          name: 'Xuất file',
          active: true,
          icon_class: 'one-xlsx-import',
          visible: true
        },
        {
          id: 'tsbtnThoaiTra',
          name: 'Thoái trả',
          active: true,
          icon_class: 'one-change',
          visible: true
        },
        {
          id: 'btnTraPhieu',
          name: 'Trả phiếu',
          active: true,
          icon_class: 'one-file-back',
          visible: true
        },
        {
          id: 'tsbtnTinh_GiaoNET_NT',
          name: 'Giao Net',
          active: true,
          icon_class: 'icon one-file-arrow-right1',
          visible: false
        },
        {
          id: 'tsbtnChuyenTo',
          name: ' Chuyển tổ',
          active: true,
          icon_class: 'one-change',
          visible: true
        },
        {
          id: 'tsbtnDuAn',
          name: 'Dự án',
          active: true,
          icon_class: 'dropdown-icon one-clipart-edit',
          visible: true
        },
        {
          id: 'tsbtnHen',
          name: ' Hẹn LĐ',
          active: true,
          icon_class: 'one-calendar-number',
          visible: true
        },
        {
          id: 'tsbtnThongTinKT',
          name: 'Thông số KT',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_settings',
          visible: true
        },
        {
          id: 'tsbtnHuyKH',
          name: 'Hủy kích hoạt',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          visible: true
        },
        {
          id: 'tsbtnKQ_DoKiem',
          name: 'Đo kiểm',
          active: true,
          icon_class: 'dropdown-icon nc-icon-glyph design_design',
          visible: true
        },     
      ],
      actionsDropdown: [
        {
          id: 'tsbtnTraCuuNo',
          name: 'Tra cứu nợ',
          active: true,
          icon_class: 'dropdown-icon ui-1_lock nc-icon-glyph',
          visible: true
        }, 
        {
          id: 'tsbtnCreatIssue',
          name: 'Gửi YC',
          active: true,
          icon_class: 'one-file-download',
          visible: true
        },
      ],
      dateconfig: {
        altFormat: 'DD/MM/YYYY HH:mm:ss',
        altInput: true,
        dateFormat: 'DD/MM/YYYY HH:mm:ss',
        allowInput: true,
        type: 'datetime'
      },
      ngay_tao: '',
      tag: '9+4+1;11',
      // GIA TRI MAC DINH TRUYEN VAO FORM KHI LOAD
      tthd_id: TrangThaiHD.DANG_THI_CONG,
      dsloaihd_id: '1',
      dsdichvuvt_id: '1',
      lay_phieu_con: false,

      phieu_moi: '0',
      dtThaoTac: [],
      // HET CAC GIA TRI MAC DINH
      nhanvien_id: '',
      luong_id: '',
      huonggiao_id: 0,
      hdtb_id: 0,
      hdkh_id: 0,
      formProps: {
        txtMaGD: '',
        txtMaTB: '',
        dtpNgayGV: this.formateDate(new Date(), null),
        dtpNgayBG: this.formateDate(new Date(), null),
        txtHostLD: '',
        txtHostVL: '',
        txtTramTB: '',
        txtNDGiao: '',
        txtThongTinTra: '',
        txtNoiDungTH: '',
        txtGhiChu: '',
        txtTenTB: '',
        txtDiaChiLD: '',
        dtpNgayYCHT: this.formateDate(new Date(), null),
        lblLienHe: '',
        lblThietBi: 'Thiết bị sử dụng'
      },
      disabledBox: {
        txtMaGD: false,
        txtMaTB: false,
        dtpNgayGV: false,
        dtpNgayBG: false,
        txtHostLD: true,
        txtHostVL: true,
        txtTramTB: true,
        txtNDGiao: true,
        txtThongTinTra: true,
        txtNoiDungTH: false,
        txtGhiChu: true,
        txtTenTB: true,
        txtDiaChiLD: true,
        dtpNgayYCHT: false
      },
      chkNgayGV: {
        checked: false,
        disabled: false
      },
      chkNgayBG: {
        checked: false,
        disabled: false
      },
      chkNgayHT: {
        checked: false,
        disabled: true
      },
      chkTimKiem: {
        checked: false,
        disabled: false
      },
      listbox: {
        cboDichVuVT: {
          list: [],
          value: 0,
          disabled: false
        },
        cboQuyTrinh: {
          list: [],
          value: 0,
          disabled: false
        },
        cboNguoiGV: {
          list: [],
          value: '',
          disabled: false
        },
        cboLoaiDVTK: {
          list: [],
          value: '',
          disabled: false
        },
        cboNhomKQ: {
          list: [],
          value: '',
        },
        cboKetQuaXL: {
          list: [],
          value: '',
        },
      },
      LisTLoaiDVTK: [{'id': 6, 'text': 'Host'}, {'id': 25, 'text': 'Trạm vệ tinh'}],
      DmKetQuaXL: {
        data: [],
        disabled: false,
        textField: 'ketqua_xl',
        valueField: 'kqxl_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'ketqua_xl',
            headerText: 'KQXL_ID',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ketqua_xl',
            headerText: 'KQXL',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectKQXL: null
      },
      DmNhomKQ: {
        data: [],
        disabled: false,
        textField: 'nhomkq_id',
        valueField: 'nhom_kq',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'nhomkq_id',
            headerText: 'NHOMKQ_ID',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhom_kq',
            headerText: 'Nhóm KQ',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectNhomKQ: null
      },
      DmDViTimKiem: {
        data: [],
        disabled: false,
        textField: 'ten_dv',
        valueField: 'donvi_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'donvi_id',
            headerText: 'Mã DV',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectDViTimKiem: null
      },
      dgvNhanVien: {
        list: [],
        header: [
          {
            fieldName: 'ten_nv',
            headerText: 'Tên nhân viên'
          },
          {
            fieldName: 'so_dt',
            headerText: 'Điện thoại'
          },
          {
            fieldName: 'nhiemvu',
            headerText: 'Nhiệm vụ'
          },
          {
            name: 'ghichu',
            headerText: 'Ghi chú'
          }
        ],
        selectNhanVien: null
      },
      dgvDS_ThietBi: {
        list: [],
        header: [
          {
            fieldName: 'ngay_sd',
            headerText: 'Ngày SD'
          },
          {
            fieldName: 'loai_tbi',
            headerText: 'Loại thiết bị'
          },
          {
            fieldName: 'donvi_tbi',
            headerText: 'Đơn vị'
          },
          {
            fieldName: 'kieu_tbi',
            headerText: 'Kiểu'
          },
          {
            fieldName: 'sl',
            headerText: 'SL'
          },
          {
            fieldName: 'tentrangbi',
            headerText: 'Trang bị'
          },
          {
            fieldName: 'seri',
            headerText: 'Seri'
          }
        ],
        selectThietBi: null
      },
      gview_DanhSach: {
        list: [],
        isCheckAll: false,
        clickOnGrid: false,
        hdtbChecked: [],
        header: [
          {fieldName: '', headerText: '', visible: true,  headerAlign: 'Center', textAlign: 'Center', allowFiltering: false, width: '70', 
            headerTemplate: () => {
                return {
                    template: Vue.component("action-check-all", {
                        template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.gview_DanhSach.isCheckAll" class="check" 
                                        @change="onChangeProcessed(data)" />
                                    <span class="name"></span>
                                </div>
                            `,
                        data: function () {
                            return { data: {} };
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent
                            },
                        },
                        methods: {
                            onChangeProcessed(value) {
                                this.parent.clickOnGrid = false
                            },
                        }
                    }),
                };
            },
            template: function () {
              return {
                template: Vue.component('action-check', {
                  template: `
                        <div class="check-action">
                          <input 
                            type="checkbox" 
                            class="check"
                            v-model="parent.gview_DanhSach.hdtbChecked"
                            :value="data.hdtb_id"/>
                          <span class="name"></span>
                        </div>
                      `,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                })
              }
            }
          },
          {
            fieldName: 'ma_hd',
            headerText: 'Mã HĐ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ngay_yc_convert',
            headerText: 'Ngày YC',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ngaygiao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ma_gd',
            headerText: 'Mã GD',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ma_lt',
            headerText: 'Số ảo',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_kv',
            headerText: 'Khu vực',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ngay_hh',
            headerText: 'Ngày hết hạn',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'so_dt',
            headerText: 'Điện thoại LH',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'dtn',
            headerText: 'Điểm tín nhiệm',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ma_kv',
            headerText: 'Mã khu vực',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'dv_nhan',
            headerText: 'ĐV nhận',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'gcol_Printed',
            headerText: 'Trạng thái in',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'lydohuy',
            headerText: 'Lý do hủy',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_status',
            headerText: 'Kích hoạt',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diem_chia',
            headerText: 'Điểm chia',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'dv_giao',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ LĐ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selectedItem: null,
        checked: []
      },
      gridVLyDoHuyHDTB: {
        data: [],
        headers: [
          {
            fieldName: 'lydohuy',
            headerText: 'Lý do hủy',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kieu_huy',
            headerText: 'Kiểu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lydohuy_id',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'Center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('xoaLyDoHuyTemplate', {
                  template: `<a class="btn btn-huylydo lh14" @click="onClickXoaLyDoHuy"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                  <span class="icon nc-icon-glyph ui-1_simple-remove"></span></a>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onClickXoaLyDoHuy () {
                      this.parent.XoaLyDoHuy(this.data)
                    }
                  }
                })
              }
            }
          }
        ],
        selected: 0,
        selectItem: null,
        checked: []
      },
      dsphieuGV: [],
      gpnv_phieu_id: null,
      gpnv_nguoigv_id: null,
      gpnv_ngaygiao: null,
      gpnv_ma_tb: null,
      gpnv_hdtb_id: null,
      gpnv_huonggiao_id: null,
      gpnv_dsphieuGV: [],
      phieu_id: 0,
      thuebao_id: 0,
      loaitb_id: 0,
      dichvuvt_id: 0,
      license: '',
      cochu: '',
      tb_mytv: '',
      tb_mega: '',
      tb_cd: '',
      madoicap: '',
      goi_id: 0,
      kt_status: '',
      quytrinh_id: 0,
      vquytrinh_id: 0,
      matb_link: '',
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',
      modelVatTu: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      PHAINHAP_GHICHU_TTVT_XN_NO: -1,
      huygoi_hoancong_tl: -1,
      nhantin_nhanvien_diaban: 0,
      XacMinh_TheoYeuCau: 0,
      is_using_service_siptrink: false,
      showModalTraphieu: false,
      tptc_ma_gd: null,
      tptc_ma_tb: null,
      tptc_loaihd_id: null,
      tptc_dichvuvt_id: null,
      tptc_ngaygiao: null,
      tptc_ngaytra: null,
      tptc_kieu: null,
      quyenduoc_hen: 0,
      modelCapVatTu: {},
      nhanvienId: Number.parseInt(this.$root.token.getNhanVienID()),
      donviID: Number.parseInt(this.$root.token.getDonViID()),
      ktra_load: true,
      ims1200k: 0,
      modelThayDoiLyDoHuy: {
        vkieu: 1,
        loaitb_id: 0,
        loaild_id: 0,
        hdtb_id: 0
      },
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
      modelIn: {},
      cty_newline: false,
      showPanelNV: true,
      showPanelTBi: true,
      showPanelLydo: true,

      modelGiaoPH: {},
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
  mounted () {
    // Split(['#boxLeft', '#boxRight'], {
    //         sizes: [50, 50],
    //         gutterSize: 16,
    //         onDragEnd: function (sizes) {
    //         },
    //     });

    // Split(['#boxLeft1', '#boxRight1'], {
    //     sizes: [50, 50],
    //     gutterSize: 16,
    //     onDragEnd: function (sizes) {
    //     },
    // });
    // get tag
    this.getTag()
    this.sp_ht_dichvuvt_combobox(this.dsdichvuvt_id)
    this.ktra_load = true
    this.sp_lay_ds_nhanvien_theo_donvi_id(this.donviID)
    this.loadThamSoMD()
  },
  watch: {
    "gview_DanhSach.isCheckAll": {
        handler: function (value) {
            if (value) {
                this.gview_DanhSach.list.forEach((f) => {
                    f.checked = true;
                });
                this.gview_DanhSach.hdtbChecked = this.gview_DanhSach.list.map(
                    (f) => f.hdtb_id
                );

                this.gview_DanhSach.checked = []
                this.gview_DanhSach.hdtbChecked.forEach(item => {
                  let oFind = this.gview_DanhSach.list.find(ix => ix.hdtb_id == item)
                  if (oFind != undefined && oFind != null) {
                    this.gview_DanhSach.checked.push(oFind)
                  }
                })
                return;
            } else {
                if (!this.clickOnGrid) {
                    this.gview_DanhSach.list.forEach((f) => {
                        f.checked = false;
                    });
                    this.gview_DanhSach.hdtbChecked = [];
                    this.gview_DanhSach.checked = [];
                    return;
                }
            }
        },
        deep: true,
    },
    "gview_DanhSach.hdtbChecked": {
        handler: function (value) {
            this.clickOnGrid = true
            this.gview_DanhSach.isCheckAll = value.length > 0 && value.length === this.gview_DanhSach.list.length;

            this.gview_DanhSach.checked = []
            value.forEach(item => {
              let oFind = this.gview_DanhSach.list.find(ix => ix.hdtb_id == item)
              if (oFind != undefined && oFind != null) {
                this.gview_DanhSach.checked.push(oFind)
              }
            })
        },
        deep: true,
    },

    'DmNhomKQ.selectNhomKQ': function (newValue) {
      this.cboNhomKQ_EditValueChanged(newValue)
    },
    'listbox.cboLoaiDVTK.value': function (newValue) {
      this.cboLoaiDVTK_SelectedValueChanged(newValue)
    },
    'DmDViTimKiem.selectDViTimKiem': function (newValue) {
      this.cboDViTimKiem_EditValueChanged(newValue)
    },
    'listbox.cboNhomKQ.value': function (newValue) {
      this.cboNhomKQ_EditValueChanged(newValue)
    },
  },
  methods: {
    cboDViTimKiem_EditValueChanged (newValue) {
      try {
        if (this.DmDViTimKiem.selectDViTimKiem) {
          this.HienThiDanhSachHDTB_TK_LOAIDV(this.listbox.cboLoaiDVTK.value, newValue)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.message)
      }
    },
    rdoPhieuMoi_CheckedChanged () {
      this.HienThiDanhSach()
    },
    XoaLyDoHuy (item) {
      if (item != null) {
        console.log(item)
        try {
          let kieu = item.kieu
          if (Number(kieu) == 1) {
            this.$toast.error('Bạn không được phép xóa lý do hủy của TTKD nhập.')
            return
          }
          this.$confirm('Bạn chắc chắn muốn xóa lý do hủy ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            let request = {
              'hdtb_id': this.hdtb_id,
              'lydohuy_id': item.lydohuy_id
            }
            await API.DELETE_LYDOHUY_HDTB(this.axios, request)
            await this.HienThiDS_LyDoHuy(this.hdtb_id)
          })
        } catch (error) {
          console.log(error)
          this.$toast.error('Có lỗi xảy ra khi xóa lý do hủy')
        }
      }
    },
    btnLyDoHuy_Click () {
      this.modelThayDoiLyDoHuy.hdtb_id = this.hdtb_id
      this.modelThayDoiLyDoHuy.loaitb_id = this.loaitb_id
      this.modelThayDoiLyDoHuy.loaild_id = 1
      this.modelThayDoiLyDoHuy.vkieu = 1
      this.$refs.popupThayDoiLyDoHuy.showModal()
    },
    CapNhatLyDoHuyThanhCong (args) {
      this.HienThiDS_LyDoHuy(this.hdtb_id)
    },
    async sp_lay_ds_nhanvien_theo_donvi_id (donviId) {
      let data = {
        'donvi_id': donviId
      }
      let dsnhanvien = this.GetDataList(await API.sp_lay_ds_nhanvien_theo_donvi_id(this.axios, data))
      if (dsnhanvien.length > 0) {
        this.listbox.cboNguoiGV.list = dsnhanvien.map(x => {
          return {id: x['nhanvien_id'], text: x['ten_nv']}
        })
        this.listbox.cboNguoiGV.value = dsnhanvien[0].nhanvien_id
      }
    },
    async sp_ht_dichvuvt_combobox (dsdichvuvtid) {
      let data = {
        'dichvu_vt_id': dsdichvuvtid
      }

      let dsDv = this.GetDataList(await API.sp_ht_dichvuvt_combobox(this.axios, data))
      if (dsDv.length > 0) {
        this.listbox.cboDichVuVT.list = dsDv.map(x => {
          return {id: x['dichvuvt_id'], text: x['ten_dvvt']}
        })
        this.listbox.cboDichVuVT.value = this.listbox.cboDichVuVT.list[0].id
        this.cboDichVuVT_SelectedValueChanged()
      }
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
          if (arr.length >= 1) {
            this.tthd_id = Number.parseInt(arr[0])
          }
          if (arr.length >= 2) {
            this.dsloaihd_id = arr[1].replaceAll(';', ',')
          }
          if (arr.length >= 3) {
            this.dsdichvuvt_id = arr[2].replaceAll(';', ',')
          }
          if (arr.length >= 4) {
            if (arr[3] === '1') {
              this.lay_phieu_con = true
            }
          }
          if (arr.length >= 5) { // Áp dụng cho user của đối tác
            if (arr[4] != "") {
              this.cty_newline = true;
            }     
          }
        } else {
          this.tthd_id = TrangThaiHD.DANG_THI_CONG
          this.dsloaihd_id = '1'
          this.dsdichvuvt_id = '1'
        }
      } catch (error) {
        this.$toast.error('Tag truyền vào không hợp lệ')
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
        this.dsloaihd_id = '1'
        this.dsdichvuvt_id = '9'
      }
      /* console.log('tthd_id ', this.tthd_id)
      console.log('dsloaihd_id ', this.dsloaihd_id)
      console.log('dsdichvuvt_id ', this.dsdichvuvt_id) */
    },
    async loadThamSoMD () {
      let dsKt1 = await this.LAY_DS_THAMSO_MD()
      for (let i = 0; i < dsKt1.length; i++) {
        if (dsKt1[i].ma_ts === 'PHAINHAP_GHICHU_TTVT_XN_NO' && dsKt1[i].ten_ts === '1') {
          this.PHAINHAP_GHICHU_TTVT_XN_NO = 1
        }
        if (dsKt1[i].ma_ts === 'HUYGOI_KHI_HOANCONG_TL' && dsKt1[i].ten_ts === '1') {
          this.huygoi_hoancong_tl = 1
        }
        if (dsKt1[i].ma_ts === 'LHD_NHANTIN_DIABAN' && dsKt1[i].ten_ts === '1') {
          this.nhantin_nhanvien_diaban = 1
        }
        if (dsKt1[i].ma_ts === 'LAY_QUYTRINH_PROC' && dsKt1[i].ten_ts === '1') {
          this.XacMinh_TheoYeuCau = 1
        }
        if (dsKt1[i].ma_ts === 'IMS1200K') {
          if (dsKt1[i].ten_ts === '1') {
            this.ims1200k = 1
          }
          if (dsKt1[i].ten_ts === '2') {
            this.ims1200k = 2
          }
        }
        if (dsKt1[i].ma_ts == 'USING_SERVICE_SIPTRINK_NEW') {
          this.is_using_service_siptrink = true
        }
      }
    },
    cboDichVuVT_SelectedValueChanged: function () {
      this.dichvuvt_id = Number.parseInt(this.listbox.cboDichVuVT.value)
      let data = {
        'dsloaihd_id': this.dsloaihd_id,
        'dichvuvt_id': this.listbox.cboDichVuVT.value,
        'tthd_id': this.tthd_id,
        'kieu': 0
      }

      API.sp_lay_ds_quytrinh_v2(this.axios, data).then(response => {
        if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          this.listbox.cboQuyTrinh.list = response.data.data.map(x => {
            return {id: x['quytrinh_id'], text: x['quytrinh']}
          })
        } else {
          this.listbox.cboQuyTrinh.list = []
        }

        if (this.listbox.cboQuyTrinh.list.length > 0) {
          this.listbox.cboQuyTrinh.value = this.listbox.cboQuyTrinh.list[0].id
          this.cboQuyTrinh_SelectedValueChanged()
        }
      })
    },

    cboQuyTrinh_SelectedValueChanged: async function () {
      try {
        this.loading(true)
        this.quytrinh_id = this.listbox.cboQuyTrinh.value
        let data = {
          'quytrinh_id': this.listbox.cboQuyTrinh.value,
          'tthd_id': this.tthd_id
        }
        let dsQuyTrinh = this.GetDataList(await API.sp_lay_huonggiao_theo_quytrinh(this.axios, data))
        if (dsQuyTrinh.length > 0) {
          this.luong_id = dsQuyTrinh[0].luong_id
          this.huonggiao_id = dsQuyTrinh[0].huonggiao_id
          this.header.title = dsQuyTrinh[0].huonggiao.toUpperCase()
        } else {
          this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này. Bạn hãy liên hệ Admin kiểm tra lại')
          this.huonggiao_id = -1
          await this.HienThiDanhSach()
        }
        this.chkTimKiem.checked = false
        await this.HienThiGiaoDien(this.luong_id)
        await this.HienThiDanhSach()
        if (this.PHAILAM('GIAOVIEC')) {
          this.chkNgayBG.Checked = true
          this.chkNgayGV.Checked = true
          let t = new Date()
          t.setSeconds(t.getSeconds() + 30)
          this.formProps.dtpNgayGV = this.formateDate(t, null)
        }
        // Nhóm làm việc với phần xác minh thuê bao trước khi tạm dừng
        if (this.PHAILAM('XACMINH_TAMDUNG')) {
          let data = this.GetDataList(await API.lay_nhom_kqxl_theo_huonggiao(this.axios, {
            vhuonggiao_id: this.huonggiao_id
          }))
          if (data.length > 0) {
            this.DmNhomKQ.data = data
            this.DmNhomKQ.selectNhomKQ = data[0].nhomkq_id

            this.listbox.cboNhomKQ.list = data.map((x)=>({id:x.nhomkq_id, text:x.nhom_kq}))
            this.listbox.cboNhomKQ.value = data[0].nhomkq_id
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi: ' + error)
      } finally {
        this.loading(false)
      }
    },
    PHAILAM (code) {
      let filter = this.dtThaoTac.filter(item => (item.enable === 1 && item.code === code))
      return filter.length > 0
    },
    async HienThiGiaoDien (luongid) {
      console.log("HienThiGiaoDien")
      this.setVisibleButton('tsbtnKQ_DoKiem', false)
      this.setVisibleButton('tsbtnDuAn', false)
      this.setVisibleButton('tsbtnHoanCong', false)
      this.setVisibleButton('tsbtnHoanThienHS', false)
      this.setVisibleButton('tsbtnGiaoPhieu', false)
      this.setVisibleButton('tsbtnHoanThanh', false)
      this.setVisibleButton('tsbtnGiaoViec', false)     
      this.setVisibleButton('tsbtnKichHoat', false)
      this.setActiveButton('tsbtnKichHoat', false)
      this.setVisibleButton('tsbtnVatTu', false)
      this.setVisibleButton('tsbtnVatTuMoi', false)
      this.setVisibleButton('tsbtnChuyenTo', false)
      this.setVisibleButton('tsbtnCreatIssue', false)
      this.setVisibleButton('tsbtnTinh_GiaoNET_NT', false)
      try {
        let dsThaoTac = this.GetDataList(await API.sp_lay_ds_thaotac(this.axios, {
          'luong_id': luongid
        }))
        this.dtThaoTac = dsThaoTac
        if (dsThaoTac.length > 0) {
          for (let i = 0; i < dsThaoTac.length; i++) {
            let thaotacId = dsThaoTac[i].thaotac_id
            let enable = dsThaoTac[i].enable
            let dsControl = await this.SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID(thaotacId)
            if (dsControl.length > 0) {
              for (let j = 0; j < dsControl.length; j++) {
                let controlName = dsControl[j].control_name
                if (controlName.includes('txt')) {
                  this.disabledBox[controlName] = enable !== 1
                } else if (controlName.includes('cbo')) {
                  if (this.$data[controlName] != undefined) {
                    this.$data[controlName].disabled = enable !== 1
                  }    
                } else if (controlName.includes('tsbtn')) {
                  if (enable === 1) {
                    console.log("enable " + controlName)
                    this.setActiveButton(controlName, true)
                  } else {
                    this.setActiveButton(controlName, false)
                  }
                }
                // ToolStripButton
                if (enable === 1) {
                  this.setVisibleButton(controlName, true)
                } else {
                  this.setVisibleButton(controlName, false)
                }
              }
            }
          }
        }
        // Ẩn hiện nút nếu là công ty newlife
        console.log("HienThiGiaoDien cty_newline = " + this.cty_newline)
        if (this.cty_newline) {
          this.setVisibleButton('tsbtnGiaoViec', false)
          this.setVisibleButton('tsbtnGiaoPhoiHop', false)
          this.setVisibleButton('tsbtnGiaoPhieu', false)
          this.setVisibleButton('tsbtnHuyKH', false)
          this.setVisibleButton('tsbtnKQ_DoKiem', false)
          this.setVisibleButton('tsbtnHen', false)
          this.setVisibleButton('tsbtnDuAn', false)
          this.setVisibleButton('tsbtnCreatIssue', false)
          this.setVisibleButton('tsbtnChuyenTo', false)
          this.setVisibleButton('tsbtnHoanThanh', false)
          this.setVisibleButton('tsbtnThoaiTra', false)
          this.setVisibleButton('tsbtnTraCuuNo', false)
          this.setVisibleButton('tsbtnVatTu', false)
          this.setVisibleButton('btnInDS', false)
          this.setActiveButton('tsbtnKichHoat', true)
          this.setVisibleButton('tsbtnKichHoat', true)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi khi hiển thị giao diện:', error)
      }
    },
    async SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID (thaotacId) {
      return this.GetDataList(await API.SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID(this.axios, {
        'thaotac_id': thaotacId
      }))
    },
    cboNhomKQ_EditValueChanged: async function (newValue) {
      this.loading(true)
      let req = {
        'vnhomkq_id': newValue
      }
      let data = this.GetDataList(await API.lay_ds_nguyennhan_nocuoc(this.axios, req))
      if (data.length > 0) {
        this.DmKetQuaXL.data = data
        this.DmKetQuaXL.selectKQXL = data[0].kqxl_id

        this.listbox.cboKetQuaXL.list = data.map((x)=>({id:x.kqxl_id, text:x.ketqua_xl}))
        this.listbox.cboKetQuaXL.value = data[0].kqxl_id
      }

      console.log("cboNhomKQ_EditValueChanged newValue = " + newValue)
      if (newValue == NHOM_KQXL.KH_DONGY_TIEPTUC_SUDUNG) {
        this.setVisibleButton('tsbtnThoaiTra', true)
        this.setVisibleButton('tsbtnHoanCong', false)
        this.setVisibleButton('tsbtnKichHoat', false)
        this.setVisibleButton('tsbtnHen', false)
        this.setVisibleButton('tsbtnXuatFileTL', false)
        this.setVisibleButton('btnInDS', false)
      } else {
        this.setVisibleButton('tsbtnThoaiTra', false)
        await this.HienThiGiaoDien(this.luong_id)
      }
      this.loading(false)
    },

    cboLoaiDVTK_SelectedValueChanged: async function (newValue) {
      try {
        if (this.listbox.cboLoaiDVTK.value === LOAI_DV.HOST) {
          if (this.DmDViTimKiem.data.length === 0) {
            let data = this.GetDataList(await API.sp_lay_ds_donvi_theo_loaidv(this.axios, {
              loaidv_id: newValue
            }))
            if (data.length > 0) {
              this.DmDViTimKiem.data = data
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi:', error)
      }
    },

    chkTimKiem_CheckedChanged: async function () {
      if (this.chkTimKiem.checked === true) {
        if (this.listbox.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
          if (this.listbox.cboLoaiDVTK.list.length === 0) {
            this.listbox.cboLoaiDVTK.list = this.LisTLoaiDVTK
            this.listbox.cboLoaiDVTK.value = this.LisTLoaiDVTK[0].id
          }
          try {
            if (this.DmDViTimKiem.selectDViTimKiem != null) {
              await this.HienThiDanhSachHDTB_TK_LOAIDV(this.listbox.cboLoaiDVTK.value, this.DmDViTimKiem.selectDViTimKiem)
            }
          } catch (error) {
            this.$toast.error('Có lỗi: ' + error)
          }
        }
      } else {
        await this.HienThiDanhSach()
      }
    },
    async HienThiDanhSachHDTB_TK_LOAIDV (loaidvid, donvitkid) {
      try {
        this.loading(true)
        let req = {
          'in_dichvuvt_id': this.listbox.cboDichVuVT.value,
          'in_huonggiao_id': this.huonggiao_id,
          'in_loaihd_id': LoaiHopDong.CHAMDUT_SD,
          'in_tthd_id': this.tthd_id,
          'in_nhanvien_id': this.nhanvienId,
          'in_ma_tb': this.formProps.txtMaTB,
          'in_loai_id': this.phieu_moi,
          'in_loaidv_id': loaidvid,
          'in_donvi_tk_id': donvitkid
        }
        let data = this.GetDataList(await API.lay_ds_hopdong_huonggiao_theo_dv(this.axios, req))
        this.gview_DanhSach.list = data
        if (data.length === 0) {
          this.Clear()
          this.setActiveButton('tsbtnHoanCong', false)
          this.setActiveButton('tsbtnGiaoPhieu', false)
          this.setActiveButton('tsbtnHoanThienHS', false)
          this.setActiveButton('tsbtnHoanThanh', false)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi:', error)
        this.loading(false)
        alert('close loading')
      } finally {
        this.loading(false)
      }
    },
    HienThiDanhSach: async function () {
      try {
        this.loading(true)
        if (this.ktra_load === true) {
          let req = {
            // TODO khong can truyen bancheo vi trong ham db da xu ly
            'dichvuvt_id': this.listbox.cboDichVuVT.value,
            'lay_phieu_con': this.lay_phieu_con ? 1 : 0,
            'kieu': this.phieu_moi,
            'tthd_id': this.tthd_id,
            'huonggiao_id': this.huonggiao_id,
            'nhanvien_id': this.nhanvienId,
            'loai_id': this.phieu_moi
          }
          let data = this.GetDataList(await API.sp_lay_ds_thuebao_thanhly(this.axios, req))
          if (data.length > 0) {
            data.forEach((item) => {
              item.ngay_yc_convert = item.ngay_yc
              item.ngaygiao_convert = item.ngaygiao
              try {            
                if (item.ngay_yc != undefined && item.ngay_yc != "") {
                  item.ngay_yc_convert = moment(item.ngay_yc, 'YYYY-MM-DD HH:mm:ss').format("DD/MM/YYYY HH:mm:ss")
                }          
              } catch (e) {}         
            })
          }

          // TODO hien thi diem tin nhiem
          this.gview_DanhSach.selectedItem = null
          this.gview_DanhSach.list = data
          if (data.length === 0) {
            this.Clear()
            this.setActiveButton('tsbtnHoanCong', false)
            this.setActiveButton('tsbtnGiaoPhieu', false)
            this.setActiveButton('tsbtnHoanThienHS', false)
            this.setActiveButton('tsbtnHoanThanh', false)
          }
        }

        // this.setActiveButton('tsbtnHoanThienHS', true)
        // this.setVisibleButton('tsbtnHoanThienHS', true)
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi khi hiển thị danh sách: ' + error.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    async btnNDTH_Click () {
      try {
        if (this.phieu_id === 0) {
          return
        }
        let ndth = (this.formProps.txtNoiDungTH && this.formProps.txtNoiDungTH != null) ? this.formProps.txtNoiDungTH.trim() : ""
        await this.UPDATE_ND_THUCHIEN(this.phieu_id, ndth)
        this.$toast.success('Cập nhật thành công!')
        let matb = this.formProps.txtMaTB.trim()
        await this.HienThiDanhSach()
        let index = this.gview_DanhSach.list.findIndex(item => item.ma_tb === matb)
        if (index >= 0) {
          this.$refs.dsphieu.selectRow(index, true)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi : ', error)
      }
    },
    async UPDATE_ND_THUCHIEN (phieuid, noidungth) {
      await API.UPDATE_ND_THUCHIEN(this.axios, {'nd_thuchien': noidungth, 'phieu_id': phieuid})
    },
    async fn_tt_donvi(type, param) {
      let result = ''
      const req = {
        type: type,
        param: param
      }
      let data = this.GetData(await API.fn_tt_donvi(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async fn_tt_giaophieu_nv(type, param) {
      let result = ''
      const req = {
        type: type,
        param: param
      }
      let data = this.GetData(await API.fn_tt_giaophieu_nv(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async fn_tt_giaophieu(type, param, param1) {
      let result = ''
      const req = {
        type: type,
        param: param,
        param1: param1
      }
      let data = this.GetData(await API.fn_tt_giaophieu(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async fn_tt_hd_thuebao(type, param) {
      let result = ''
      const req = {
        type: type,
        param: param
      }
      let data = this.GetData(await API.fn_tt_hd_thuebao(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async sp_lay_ds_thuebao_thanhly (data) {
      this.gview_DanhSach.selectedItem = null
      this.gview_DanhSach.checked = []
      return this.GetDataList(await API.sp_lay_ds_thuebao_thanhly(this.axios, data))
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    gview_DanhSach_FocusedRowChanged: async function (item) {
      try {
        this.loading(true)
        if (item != null) {
          console.log(item)
          console.log(this.dtThaoTac)
          this.gview_DanhSach.selectedItem = item
          this.hdkh_id = item.hdkh_id
          this.hdtb_id = item.hdtb_id
          this.phieu_id = Number.parseInt(item.phieu_id)
          // Lấy thông tin liên hệ lắp đặt thuê bao
          let ttlh = await this.fn_lay_thongtin_lh_lapdat(this.hdkh_id)
          this.formProps.lblLienHe = 'Liên hệ: ' + (ttlh === null ? '' : ttlh)
          let dstra = await this.sp_ht_phieu_tra(this.phieu_id)
          if (dstra.length > 0) {
            this.formProps.txtThongTinTra = dstra[0].nd_tra
          } else {
            this.formProps.txtThongTinTra = ''
          }
          this.chkNgayHT.checked = false
          this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
          this.dichvuvt_id = item.dichvuvt_id
          this.listbox.cboDichVuVT.value = item.dichvuvt_id
          if (this.dichvuvt_id === DichVuVienThong.CO_DINH || this.dichvuvt_id === DichVuVienThong.ADSL ||
            this.dichvuvt_id === DichVuVienThong.BRCD_DOITAC || this.dichvuvt_id === DichVuVienThong.IMS_DOITAC ||
            this.dichvuvt_id === DichVuVienThong.IMS || this.dichvuvt_id === DichVuVienThong.MEGA_EYES) {
            if (item.tramtb_id != undefined && item.tramtb_id != null && item.tramtb_id != '') {
              this.formProps.txtTramTB = await this.fn_tt_donvi(2, item.tramtb_id)
            } else {
              this.formProps.txtTramTB = ''
            }
            this.chkNgayHT.checked = false
            this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
          } else {
            this.formProps.txtTramTB = ''
            if (this.dichvuvt_id === DichVuVienThong.MEGAWAN || this.dichvuvt_id === DichVuVienThong.METRONET ||
              this.dichvuvt_id === DichVuVienThong.MGW_DOITAC) {
              let dsmg = await this.lay_ds_hdtb_mgwan_theo_hdtb_id(this.hdtb_id)
              if (dsmg.length > 0) {
                if (dsmg[0].ngay_ycht) {
                  this.formProps.dtpNgayYCHT = this.formateDate(new Date(dsmg[0].ngay_ycht), null)
                  this.chkNgayHT.checked = true
                } else {
                  this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
                  this.chkNgayHT.checked = false
                }
              } else {
                this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
                this.chkNgayHT.checked = false
              }
            } else if (this.dichvuvt_id === DichVuVienThong.TSL || this.dichvuvt_id === DichVuVienThong.TSL_DOITAC) {
              let dstls = await this.lay_ds_hdtb_tsl_theo_hdtb_id(this.hdtb_id)
              if (dstls.length > 0) {
                if (dstls[0].ngay_ycht) {
                  this.formProps.dtpNgayYCHT = this.formateDate(new Date(dstls[0].ngay_ycht), null)
                  this.chkNgayHT.checked = true
                } else {
                  this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
                  this.chkNgayHT.checked = false
                }
              } else {
                this.formProps.dtpNgayYCHT = this.formateDate(new Date(), null)
                this.chkNgayHT.checked = false
              }
            }
          }
          this.formProps.txtMaGD = item.ma_gd
          this.formProps.txtMaTB = item.ma_tb
          this.formProps.txtTenTB = this.ChuanHoaTen(item.ten_tb)
          this.formProps.txtDiaChiLD = item.diachi_ld
          this.formProps.txtNDGiao = item['nd_giao']
          this.formProps.txtNoiDungTH = item['nd_thuchien']
          this.formProps.txtGhiChu = item['ghichu']
          this.loaitb_id = Number.parseInt(item['loaitb_id'])
          this.thuebao_id = Number.parseInt(item['thuebao_id'])
          // Thêm thiết bị thuê bao đang sử dụng
          let dsThietBi = await this.sp_lay_thietbi_sudung(this.thuebao_id)
          if (dsThietBi.length > 0) {
            this.dgvDS_ThietBi.list = dsThietBi
            this.formProps.lblThietBi = 'Thiết bị sử dụng - Số lượng:' + dsThietBi.length
          } else {
            this.dgvDS_ThietBi.list = []
            this.formProps.lblThietBi = 'Thiết bị sử dụng - Số lượng: 0'
          }
          this.kt_status = item.status
          if (this.PHAILAM('KICHHOAT_TD')) {
            if (item.ngay_ht_gp) {
              this.disabledBox.dtpNgayBG = false
              this.formProps.dtpNgayBG = this.formateDate(new Date(item.ngay_ht_gp), null)
              this.chkNgayBG.checked = true
              let trangthaidb = ''
              if (this.dichvuvt_id === DichVuVienThong.ADSL || this.dichvuvt_id === DichVuVienThong.IMS ||
                this.dichvuvt_id === DichVuVienThong.MEGAWAN || this.dichvuvt_id === DichVuVienThong.TSL ||
                this.dichvuvt_id === DichVuVienThong.METRONET ||
                this.dichvuvt_id === DichVuVienThong.MEGA_EYES || this.dichvuvt_id === DichVuVienThong.ANTOAN_BAOMAT_TT ||
                this.dichvuvt_id === DichVuVienThong.GPHONE) {
                trangthaidb = TRANGTHAI_DONGBO.DONGBO_CM.toString()
              } else {
                trangthaidb = TRANGTHAI_DONGBO.DONGBO_SERVICE.toString()
              }
              if (this.kt_status === trangthaidb) {
                if (this.PHAILAM('GIAOPHIEU')) {
                  this.setActiveButton('tsbtnGiaoPhieu', true)
                  this.setActiveButton('tsbtnKichHoat', false)
                } else {
                  this.setActiveButton('tsbtnGiaoPhieu', false)
                  if (this.PHAILAM('HOANTHANH_PHIEU')) {
                    this.setActiveButton('tsbtnHoanCong', false)
                    this.setActiveButton('tsbtnHoanThanh', true)
                  } else {
                    this.setActiveButton('tsbtnHoanCong', true)
                    this.setActiveButton('tsbtnHoanThanh', false)
                  }
                  this.setActiveButton('tsbtnKichHoat', false)
                }
              } else {
                this.setActiveButton('tsbtnGiaoPhieu', false)
                this.setActiveButton('tsbtnKichHoat', true)
                this.setActiveButton('tsbtnHoanCong', false)
              }
            } else {
              this.chkNgayBG.checked = !this.PHAILAM('GIAOVIEC')
              let t = new Date()
              t.setSeconds(t.getSeconds() + 30)
              this.formProps.dtpNgayBG = this.formateDate(t, null)
              this.setActiveButton('tsbtnGiaoPhieu', false)
              this.setActiveButton('tsbtnHoanCong', false)
              this.setActiveButton('tsbtnKichHoat', false)
            }
          } else { // Nếu không phải kích hoạt
            if (this.PHAILAM('GIAOPHIEU')) {
              if (item.ngay_ht_gp) {
                this.chkNgayBG.checked = true
                this.formProps.dtpNgayBG = this.formateDate(new Date(item.ngay_ht_gp), null)
                this.setActiveButton('tsbtnGiaoPhieu', true)
                this.setActiveButton('tsbtnGiaoPhoiHop', true)
                this.setActiveButton('tsbtnHoanCong', false)
                this.setActiveButton('tsbtnKichHoat', false)
              } else {
                this.chkNgayBG.checked = !this.PHAILAM('GIAOVIEC')
                let t = new Date()
                t.setSeconds(t.getSeconds() + 30)
                this.formProps.dtpNgayBG = this.formateDate(t, null)
                this.setActiveButton('tsbtnGiaoPhieu', false)
                this.setActiveButton('tsbtnGiaoPhoiHop', false)
                this.setActiveButton('tsbtnHoanCong', false)
                this.setActiveButton('tsbtnKichHoat', false)
              }
            } else { // Nếu không phải giao phiếu
              this.setActiveButton('tsbtnGiaoPhieu', false)
              // Nếu phải kích hoạt tổng đài
              if (this.PHAILAM('HOANCONG')) {
                if (item.ngay_ht_gp) {
                  this.chkNgayBG.checked = true
                  this.formProps.dtpNgayBG = this.formateDate(new Date(item.ngay_ht_gp), null)
                  this.setActiveButton('tsbtnHoanCong', true)
                } else {
                  this.chkNgayBG.checked = !this.PHAILAM('GIAOVIEC')
                  let t = new Date()
                  t.setSeconds(t.getSeconds() + 30)
                  this.formProps.dtpNgayBG = this.formateDate(t, null)
                  this.setActiveButton('tsbtnHoanCong', false)
                }
              } else if (this.PHAILAM('HOANTHANH_PHIEU')) {
                if (item.ngay_ht_gp) {
                  this.chkNgayBG.checked = true
                  this.formProps.dtpNgayBG = this.formateDate(new Date(item.ngay_ht_gp), null)
                  this.setActiveButton('tsbtnHoanThanh', true)
                } else {
                  this.chkNgayBG.checked = !this.PHAILAM('GIAOVIEC')
                  let t = new Date()
                  t.setSeconds(t.getSeconds() + 30)
                  this.formProps.dtpNgayBG = this.formateDate(t, null)
                  this.setActiveButton('tsbtnHoanThanh', false)
                }
              }
            }
          }
          this.setVisibleButton('tsbtnHoanThanh', this.lay_phieu_con)
          this.setActiveButton('tsbtnHoanThanh', this.lay_phieu_con)
          this.setVisibleButton('tsbtnGiaoPhieu', !this.lay_phieu_con)
          if (this.PHAILAM('HOANTHIENHOSO')) {
            if (item.ngay_ht_gp) {
              this.chkNgayBG.checked = true
              this.formProps.dtpNgayBG = this.formateDate(new Date(item.ngay_ht_gp), null)
              this.setActiveButton('tsbtnHoanThienHS', true)
            } else {
              this.chkNgayBG.checked = !this.PHAILAM('GIAOVIEC')
              let t = new Date()
              t.setSeconds(t.getSeconds() + 30)
              this.formProps.dtpNgayBG = this.formateDate(t, null)
              this.setActiveButton('tsbtnHoanThienHS', false)
            }
          }
          this.setActiveButton('tsbtnTinh_GiaoNET_NT', this.PHAILAM('TINH_CHUYEN_NET_KENH_NT'))

          if ((this.loaitb_id === LoaiHinhTB.INTERNET_ADSL || this.loaitb_id === LoaiHinhTB.INTERNET_FTTH || this.loaitb_id === LoaiHinhTB.WIFI_FIBER ||
              this.dichvuvt_id === DichVuVienThong.MEGAWAN || this.dichvuvt_id === DichVuVienThong.TSL || this.dichvuvt_id === DichVuVienThong.IMS ||
              this.dichvuvt_id === DichVuVienThong.CO_DINH || this.dichvuvt_id === DichVuVienThong.GPHONE || this.dichvuvt_id === DichVuVienThong.METRONET) &&
            (this.kt_status === TRANGTHAI_DONGBO.DONGBO_CM.toString() || this.kt_status === TRANGTHAI_DONGBO.DONGBO_SERVICE.toString()) &&
            this.PHAILAM('tsbtnHuyKH')) {
            this.setActiveButton('tsbtnHuyKH', true)
          } else {
            this.setActiveButton('tsbtnHuyKH', false)
          }
          // Hiển thị thông tin host
          if (this.PHAILAM('HIENTHI_HOST')) {
            let tthost = await this.lay_thongtin_host(this.hdtb_id, 0)
            if (tthost.length > 0) {
              this.formProps.txtHostVL = tthost[0].host_vl
              this.formProps.txtHostLD = tthost[0].host_ld
            } else {
              this.formProps.txtHostVL = ''
              this.formProps.txtHostLD = ''
            }
          }
          // Hiển thị danh sách nhân viên
          let dsNv = await this.lay_ds_nhanvien_theo_phieu_id(this.phieu_id, this.nhanvienId, 2)
          this.HienThiDSNV(dsNv)
          await this.HienThiDS_LyDoHuy(this.hdtb_id)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi: ', error.message)
      } finally {
        this.loading(false)
      }
    },
    async HienThiDS_LyDoHuy (hdtbid) {
      try {
        let dsLyDo = await this.sp_lay_ds_lydohuy_hdtb(hdtbid)
        if (dsLyDo.length > 0) {
          this.gridVLyDoHuyHDTB.data = dsLyDo
        } else {
          this.gridVLyDoHuyHDTB.data = []
        }
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi: ', error.message)
      }
    },
    HienThiDSNV (dsNv) {
      if (dsNv.length > 0) {
        this.chkNgayGV.checked = true
        this.formProps.dtpNgayGV = this.formateDate(this.getDate(dsNv[0].ngaygiao).toDate(), null)
        this.listbox.cboNguoiGV.value = dsNv[0].nhanvien_giao_id
        this.dgvNhanVien.list = dsNv
      } else {
        this.dgvNhanVien.list = []
        this.chkNgayGV.checked = this.PHAILAM('GIAOVIEC')
        this.formProps.dtpNgayGV = this.formateDate(new Date(), null)
        this.listbox.cboNguoiGV.value = this.nhanvienId
      }
    },
    async sp_lay_ds_lydohuy_hdtb (hdtbid) {
      let req = {
        'hdtb_id': hdtbid
      }
      return this.GetDataList(await API.sp_lay_ds_lydohuy_hdtb(this.axios, req))
    },
    async lay_ds_nhanvien_theo_phieu_id (phieuid, nhanvienid, kieu) {
      let req = {
        'phieu_id': phieuid,
        'nhanvien_id': nhanvienid,
        'kieu_id': kieu
      }
      return this.GetDataList(await API.lay_ds_nhanvien_theo_phieu_id(this.axios, req))
    },
    async lay_thongtin_host (hdtbid, kieu) {
      let req = {
        'vhdtb_id': hdtbid,
        'vkieu': kieu
      }
      return this.GetDataList(await API.lay_thongtin_host(this.axios, req))
    },
    async sp_lay_thietbi_sudung (thuebaoid) {
      let req = {
        'thuebao_id': thuebaoid
      }
      return this.GetDataList(await API.sp_lay_thietbi_sudung(this.axios, req))
    },
    async lay_ds_hdtb_tsl_theo_hdtb_id (hdtbId) {
      let req = {
        'in_hdtb_id': hdtbId
      }
      return this.GetDataList(await API.lay_ds_hdtb_tsl_theo_hdtb_id(this.axios, req))
    },
    async lay_ds_hdtb_mgwan_theo_hdtb_id (hdtbid) {
      let req = {
        'in_hdtb_id': hdtbid
      }
      return this.GetDataList(await API.lay_ds_hdtb_mgwan_theo_hdtb_id(this.axios, req))
    },
    async sp_ht_phieu_tra (phieuid) {
      let req = {
        'phieu_id': phieuid
      }
      return this.GetDataList(await API.sp_ht_phieu_tra(this.axios, req))
    },
    async fn_lay_thongtin_lh_lapdat (hdkhId) {
      return this.GetData(await API.fn_lay_thongtin_lh_lapdat(this.axios, hdkhId))
    },
    onSelectedItemsChanged: function (obj) {
      this.gview_DanhSach.checked = obj
    },
    KT_DuLieuNhap(dr) {
        if (this.chkNgayGV.checked == false) {
            this.$toast.error("Hãy chọn ngày giao việc !");
            return false;
        }

        if (this.PHAILAM("GIAOVIEC") && !this.cty_newline) {
            if (this.dgvNhanVien.list.length <= 0) {
                this.$toast.error("Hãy nhập nhân viên thực hiện  !");
                return false;
            }
        }

        if (!this.chkNgayBG.checked) {
            this.$toast.error("Hãy nhập ngày hoàn thành!");
            return false;
        }

        if (moment(this.getDate(this.formProps.dtpNgayBG)) < moment(this.getDate(this.formProps.dtpNgayGV))) {
            this.$toast.error("Ngày giao việc không được lớn hơn ngày hoàn thành!");
            return false;
        }

        //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
        let ngay_sys;
        let ngay_ht;
        ngay_sys = String(dr.thangnam_sy);
        ngay_ht = moment(this.getDate(this.formProps.dtpNgayBG)).format("DD/MM/YYYY");
        if (!this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys)) {
            this.$toast.error("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại");
            return false;
        }

        let ngay_gv = "";
        ngay_gv = moment(this.getDate(this.formProps.dtpNgayGV)).format("DD/MM/YYYY");
        if (!this.KiemTraDK_HoanThanh(ngay_gv, ngay_sys)) {
            this.$toast.error("Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại");
            return false;
        }
        //end

        return true;
    },
    tsbtnChapNhan_Click: async function () {
      if (this.gview_DanhSach.checked == null || this.gview_DanhSach.checked.length == 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để Cập nhật!')
        return
      }
      if (this.formProps.txtNoiDungTH != undefined && this.formProps.txtNoiDungTH != null && this.formProps.txtNoiDungTH.length > 4000) {
        this.$toast.error('Nội dung TH không được vượt quá 4000 ký tự')
        $("#txtNoiDungTH").focus()
        return
      }
      if (!await this.KT_DieuKien_TB(6)) {
        return
      }

      let kqCapNhat = true
      for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
          let item = this.gview_DanhSach.checked[i]
          if (!this.KT_DuLieuNhap(item)) return;

          let data = {
            'phieu_id': item.phieu_id,
            'hdtb_id': item.hdtb_id,
            'nd_thuchien': this.formProps.txtNoiDungTH,
            'nhanvien_th': this.nhanvienId,
            'nhanvien_gv': this.listbox.cboNguoiGV.value,
            'p_ngayht': moment(this.getDate(this.formProps.dtpNgayBG)).format("DD/MM/YYYY HH:mm:ss"),
            'p_ngaygv': moment(this.getDate(this.formProps.dtpNgayGV)).format("DD/MM/YYYY HH:mm:ss"),
            'p_ngayycht': moment(this.getDate(this.formProps.dtpNgayYCHT)).format("DD/MM/YYYY HH:mm:ss"),
            'p_cty_newline': this.cty_newline ? "1" : "0",
            'p_luong_id': this.luong_id,
            'p_ngayht_chk': this.chkNgayHT.checked ? "1" : "0"
          }

          try {
            console.log(this.$root.token.getPhanVungID())
            let response = await API.sp_capnhat_thanhly(this.axios, data)
            console.log(response)
            if (response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                if (response.data.data != "0") {
                    this.$toast.error(response.data.data)
                    kqCapNhat = false
                    break;
                }
            } else {
                this.$toast.error('Có lỗi : ' + response.data.message_detail)
                kqCapNhat = false
                break;
            }
          } catch (error) {
              this.$toast.error('Cập nhật thất bại: ' + error.data.message_detail)
              kqCapNhat = false
              break;
          }
      }

      if (kqCapNhat) {
          await this.HienThiDanhSach()
          this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
          if (this.PHAILAM('HOANTHIENHOSO')) {
              if (this.gview_DanhSach.selectedItem.ngay_ht_gp) {
                  this.setActiveButton('tsbtnHoanThienHS', true)
              } else {
                  this.setActiveButton('tsbtnHoanThienHS', false)
              }
          }
          
      } 
    },
    gview_DanhSach_actionComplete() {
      if (this.gview_DanhSach.list.length > 0) {
          let index = this.gview_DanhSach.list.findIndex(item => item.ma_tb === this.formProps.txtMaTB.trim())
          if (index >= 0) {
              this.$refs.dsphieu.$refs.grid.selectRow(index)
          } else {
              this.$refs.dsphieu.$refs.grid.selectRow(0)
          }
      }
    },
    async KT_DieuKien_TB (kieu) {
      let kt = true
      try {
        // Xóa HĐ
        if (kieu === 1) {
          /// Tất cả các thuê bao chưa có ngay_ht
          for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
            let item = this.gview_DanhSach.checked[i]
            if (item.ngay_ht) {
              this.$toast.error('Thuê bao: ' + item.ma_tb + ' đã cập nhật thông tin.\nBạn không thể Xóa hợp đồng này!')
              kt = false
              break
            }
          }
        } else if (kieu === 2) { // Kích hoạt
          /// Tất cả các thuê bao phải có ngay_ht
          for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
            let item = this.gview_DanhSach.checked[i]
            if (item.ngay_ht_gp === '') {
              this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!')
              kt = false
              break
            }
          }
        } else if (kieu === 3) { // Kích hoạt E10/ DSLaM
          /// Tất cả các thuê bao phải có status = 5
          for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
            let item = this.gview_DanhSach.checked[i]
            if (Number(item.loaitb_id) === LoaiHinhTB.INTERNET_ADSL ||
              Number(item.loaitb_id) === LoaiHinhTB.INTERNET_FTTH ||
              Number(item.loaitb_id) === LoaiHinhTB.WIFI_FIBER) {
              if (Number(item.status) !== TRANGTHAI_DONGBO.DONGBO_CM) {
                this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa kích hoạt Visa.\nBạn không thể Kích hoạt Service DSLam thuê bao này!')
                kt = false
                break
              }
            } else if (Number(item.loaitb_id) !== LoaiHinhTB.INTERNET_MYTV) {
              if (item.ngay_ht === '') {
                this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật thông tin.\nBạn không thể Kích hoạt Service E10 thuê bao này!')
                kt = false
                break
              }
            }
          }
        } else if (kieu === 4) {
          /// Tất cả các thuê bao phải có status = 7 : Đã kích hoạt E10
          for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
            let item = this.gview_DanhSach.checked[i]
            if (this.dichvuvt_id === DichVuVienThong.ADSL || this.dichvuvt_id === DichVuVienThong.IMS) {
              if (Number(item.loaitb_id) !== LoaiHinhTB.INTERNET_ADSL_TINH_KHAC &&
                Number(item.loaitb_id) !== LoaiHinhTB.INTERNET_FIBER_TINH_KHAC &&
                Number(item.loaitb_id) !== LoaiHinhTB.HTVC_FIBERVNN_ANALOG &&
                Number(item.loaitb_id) !== LoaiHinhTB.HTVC_FIBERVNN_DIGITAL) {
                if (Number(item.status) !== TRANGTHAI_DONGBO.DONGBO_CM) {
                  this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa kích hoạt .\nBạn không thể Hoàn công thuê bao này!')
                  kt = false
                  break
                }
              }

              if (item.ngay_ht_gp === '') {
                this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật thông tin.\nBạn không thể Hoàn công thuê bao này!')
                kt = false
                break
              }
            } else {
              if (item.ngay_ht_gp === '') {
                this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật thông tin.\nBạn không thể Hoàn công thuê bao này!')
                kt = false
                break
              }
            }
          }
        } else if (kieu === 5) { // Hoàn thiện HS
          /// Tất cả các thuê bao phải cập nhật thông tin ( ngay_ht_gp not null)
          for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
            let item = this.gview_DanhSach.checked[i]
            if (item.ngay_ht_gp === '') {
              this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật thông tin.\nBạn không thể Hoàn thiện hồ sơ thuê bao này!')
              kt = false
              break
            }
          }
        } else if (kieu === 6) { // Cập nhật
          /// Tất cả các thuê bao phải cập nhật thông tin ( ngay_ht_gp not null)
          if (this.PHAILAM('GIAOVIEC')) {
            for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
              let item = this.gview_DanhSach.checked[i]
              let ktm = await this.fn_tt_giaophieu_nv(1, item.phieu_id)
              if (ktm == "") {
                this.$toast.error('Thuê bao: ' + item.ma_tb + ' chưa cập nhật giao việc.\\nBạn không thể Cập nhật thông tin!')
                kt = false
                break
              }
            }
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
        console.log(ex)
      }

      return kt
    },

    async tsbnKichHoat_Click () {
      let tDB = await this.get_app_config("DONGBO_TEST")
      if (tDB.dongbo == "0") {
        this.$toast.error('Bạn không có quyền kích hoạt. Vui lòng liên hệ admin để được xử lý')
        return
      }

      if (this.gview_DanhSach.checked.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để kích hoạt')
      }
      if (!await this.KT_DieuKien_TB(2)) return

      let strmessage = ""
      if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.ADSL) {
          if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV_B2B)
              strmessage = "Bạn có chắc chắn hủy Account lên Vasc hay không?";
          else if (this.loaitb_id == LoaiHinhTB.INTERNET_SGTV)
              strmessage = "Bạn có chắc chắn hủy Account lên SaiGonTV hay không?";
          else
              strmessage = "Bạn có chắc chắn hủy Account lên Visa hay không?";
      } else if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.CO_DINH) {
          strmessage = "Bạn có chắc chắn kích hoạt danh sách thuê bao lên tổng đài hay không?"
      } else if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.GPHONE) {
          strmessage = "Bạn có chắc chắn kích hoạt Số máy " + this.formProps.txtMaTB.trim() + " lên tổng đài hay không?"
      } else if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.IMS) {
          strmessage = "Bạn có chắc chắn hủy thuê bao : " + this.formProps.txtMaTB.trim() + " lên VTN hay không?"
      } else if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.ANTOAN_BAOMAT_TT) {
          strmessage = "Bạn có chắc chắn hủy Account FSecure hay không ?"
      } else if (Number(this.listbox.cboDichVuVT.value) == DichVuVienThong.MEGA_EYES) {
          strmessage = "Bạn có chắc chắn hủy Account Mega Camera hay không ?"
      }

      if (strmessage != "") {
          this.$confirm(strmessage, 'Thông báo', {
              confirmButtonText: 'Có',
              cancelButtonText: 'Không'
          }).then(async () => {
              await this.fn_kichhoat_thanhly_v2()
              await this.HienThiDanhSach();
          })
      } else {
            await this.fn_kichhoat_thanhly_v2()
            await this.HienThiDanhSach();
      }
    },

    async fn_kichhoat_thanhly_v2 () {
      let params = {
          'loaitb_id': this.loaitb_id,
          'dichvuvt_id': this.dichvuvt_id,
          'dtList_chon': this.gview_DanhSach.checked,
          'dgvNhanVien': this.dgvNhanVien.list,
          'capnhat_ngayht_kh': this.PHAILAM('CAPNHAT_NGAYHT_KH'),
          'capnhat_ngaykh_kh': this.PHAILAM('CAPNHAT_NGAYKH_KH'),
          'dongbo_gphone_dongbo': DONGBO_GPHONE.DONGBO,
          'is_using_service_siptrink': this.is_using_service_siptrink,
          'chkNgayHT': this.chkNgayHT.checked,
          'dtpNgayYCHT': moment(this.getDate(this.formProps.dtpNgayYCHT)).format("DD/MM/YYYY"),
          'nhantin_nhanvien_diaban': this.nhantin_nhanvien_diaban == 1 ? true : false,
          '_XacMinh_TheoYeuCau': this.XacMinh_TheoYeuCau == 1 ? true : false,     
          'hdtb_id': this.hdtb_id  
      }

      await API.fn_kichhoat_thanhly_v2(this.axios, params).then(response => {
        console.log(response)
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          this.$toast.success("Kích hoạt thành công !")
        } else {
          if (response.data && response.data != "" && response.data.message) {
            this.$toast.error(response.data.message)
          }  
        }
      }).catch(error => {
        this.$toast.error(error && error.data ? error.data.message : error)
      })
    },

    async tsbtnGiaoPhieu_Click () {
      let lstHg = this.GetDataList(await API.LAY_HUONGGIAO_THEO_LUONG_ID(this.axios, {
        'luong_id': this.luong_id
      }))
      if (lstHg.length > 0) {
        let huonggiaoid = Number(lstHg[0].huonggiao_id)
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: huonggiaoid,
          vloai_hd: this.dsloaihd_id,
          vdichvuvt_id: 0,
          vma_gd: this.formProps.txtMaGD,
          vquytrinh: this.listbox.cboQuyTrinh.value,
          vtrangthaiphieu: 1,
        };
        this.GiaoPhieuView_OnClick()
      } else {
        this.$toast.error('Chưa có hướng giao được gán !')
      }
      await this.HienThiDanhSach()
    },

    async tsbtnGiaoPhoiHop_Click () {
      if (this.gview_DanhSach.checked.length <= 0) return
      let donvi_nhan_id = 0;

      let dtTemp = await this.fn_tt_giaophieu(7, this.phieu_id, "")
      donvi_nhan_id = Number(dtTemp[0].donvi_nhan_id);
      console.log("donvi_nhan_id = " + donvi_nhan_id)

      this.modelGiaoPH = {
          phieu_cha_id: this.phieu_id,
          phieu_tinh_id: 0,
          donvi_ql_id: donvi_nhan_id,
          vtinh_id: 100
      }
      this.$refs.popupGiaoPhieuNet.openDialog(this.modelGiaoPH)
    },

    tsbtnGiaoViec_Click () {
      if (this.gview_DanhSach.checked == null || this.gview_DanhSach.checked.length == 0) {
        this.$toast.error('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại! ')
        return
      }

      if (this.listbox.cboNguoiGV.list.length <= 0 || this.listbox.cboNguoiGV.value === '') {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }

      if (!this.chkNgayGV.checked) {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }

      this.dsphieuGV = []
      for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
        let item = this.gview_DanhSach.checked[i]
        this.dsphieuGV.push(String(item.phieu_id))
      }
      // đổi cách truyền phieu_id sang danhSachPhieu
      if (this.dsphieuGV.length === 0) {
        this.$toast.error('Bạn chưa chọn phiếu để giao việc.')
        return
      }

      this.gpnv_phieu_id = this.phieu_id
      this.gpnv_nguoigv_id = Number(this.listbox.cboNguoiGV.value)
      this.gpnv_ngaygiao = this.getDate(this.formProps.dtpNgayGV).format('DD/MM/YYYY HH:mm:ss')
      this.gpnv_ma_tb = this.formProps.txtMaTB.trim()
      this.gpnv_hdtb_id = this.hdtb_id
      this.gpnv_huonggiao_id = this.huonggiao_id
      this.gpnv_dsphieuGV = this.dsphieuGV // nhapt them
      this.$refs.formgiaphieu.init()
      this.$refs.giaophieunhanvien.show()
    },
    async giaoviec_success () {
      // Hiển thị danh sách nhân viên
      let dsNv = await this.lay_ds_nhanvien_theo_phieu_id(this.phieu_id, this.nhanvienId, 2)
      this.HienThiDSNV(dsNv)
    },
    tsbtnVatTu_Click() {
      if (this.phieu_id !== 0) {
          this.modelVatTu.phieu_id = this.phieu_id;
          this.modelVatTu.loaihd_id = LoaiHopDong.CHAMDUT_SD
          this.modelVatTu.hdtb_id = this.hdtb_id;
          this.modelVatTu.thuebao_id = this.thuebao_id;
          this.$refs.popupVatTuThueBao.openDialog()
      } else {
         this.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnVatTuMoi_Click () {
      if (this.phieu_id !== 0) {
        this.modelCapVatTu.phieu_id = this.phieu_id
        this.modelCapVatTu.loaihd_id = LoaiHopDong.CHAMDUT_SD
        this.modelCapVatTu.hdtb_id = this.hdtb_id
        this.modelCapVatTu.thuebao_id = this.thuebao_id
        this.modelCapVatTu.loaitb_id = this.loaitb_id
        this.modelCapVatTu.baohong_id = 0
        this.$refs.popupVatTuTB.openDialog()
      } else {
        this.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnXuatFileTL_Click () {

    },
    tsbtnXuatExcel_Click () {
      try {
        if (this.gview_DanhSach.list.length <= 0) {
          this.$toast.error('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
          return
        }

        this.$refs.exportDataModal.showModal()
      } catch (ex) {
        this.$toast.error('Có lỗi : ' + ex)
      }
    },
    tsbtnThoaiTra_Click () {
      if (this.gview_DanhSach.list.length <= 0) {
        this.$toast.error('Bạn phải chọn phiếu !')
        return
      }
      
      try {
        if (this.PHAINHAP_GHICHU_TTVT_XN_NO === 1 && this.formProps.txtNoiDungTH !== '' && this.tthd_id == TrangThaiHD.DANG_THI_CONG) {
          if (this.PHAILAM('PHAINHAP_GHICHU_TTVT_XN_NO')) {
            this.$toast.error('Hãy nhập nội dung thực hiện!')
            this.$refs.txtNoiDungTH.focus()
            return
          }
        }

        if (this.PHAILAM('KICHHOAT_TD')) {
          if (this.kt_status == String(TRANGTHAI_DONGBO.DONGBO_CM) || this.kt_status == String(TRANGTHAI_DONGBO.DONGBO_SERVICE)) {
            this.$toast.error('Thuê bao đã kích hoạt, không thể Thoái trả')
            return
          }
        }

        this.$confirm('Bạn thật sự muốn kết thúc phiếu không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          let s = await this.thoaitra_xacminh_hdtb(this.hdtb_id, this.phieu_id, this.DmKetQuaXL.selectKQXL === null ? 0 : this.DmKetQuaXL.selectKQXL)
          if (s !== '1') {
            this.$toast.error(s)
            return
          }
          this.$toast.success('Kết thúc phiếu thành công')
          await this.HienThiDanhSach()
        })
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    async LAY_DS_THAMSO_MD () {
      let result = []
      try {
        let params = {
            "p_param": "",
            "p_type": 1
        }
        let response = await API.sp_tt_thamso_md(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      } catch (e) {
        console.log(e)
      }
      return result
    },
    async thoaitra_xacminh_hdtb (vhdtbid, vphieuid, vkqxlid) {
      let params = {
        'vhdtb_id': vhdtbid,
        'vphieu_id': vphieuid,
        'vkqxl_id': vkqxlid
      }
      return this.GetData(await API.thoaitra_xacminh_hdtb(this.axios, params))
    },
    async btnTraPhieu_Click () {
      if (this.PHAILAM('KICHHOAT_TD')) {
        if (this.status === TRANGTHAI_DONGBO.DONGBO_CM || this.status === TRANGTHAI_DONGBO.DONGBO_SERVICE) {
          this.$toast.error('Thuê bao đã kích hoạt, không thể trả phiếu')
          return
        }
      }

      this.$refs.frmTraPhieuTC.frmTraPhieuTC(
        this.formProps.txtMaTB.trim(),
        LoaiHopDong.CHAMDUT_SD,
        this.listbox.cboDichVuVT.value,
        this.$root.token.getDonViID(),
        this.formProps.txtMaTB.trim(),
        "0",
        "0",
        3
      );
      this.$refs.popupFrmTraPhieuTC.show();

      // this.modelTraPhieu.maGd = this.formProps.txtMaTB.trim()
      // this.modelTraPhieu.loaihdId = LoaiHopDong.CHAMDUT_SD
      // this.modelTraPhieu.dichvuvtId = this.listbox.cboDichVuVT.value
      // this.modelTraPhieu.nhanvienId = this.nhanvienId
      // this.modelTraPhieu.ma_tb = this.formProps.txtMaTB.trim()
      // this.modelTraPhieu.ngaytra = '0'
      // this.modelTraPhieu.ngaygiao = '0'
      // this.modelTraPhieu.kieugoiId = 3
      // this.showModalTraphieu = true
    },
    traphieu_success() {

    },
    async tptc_update_success (val) {
      if (val) {
        let vma_tb = this.formProps.txtMaTB.trim()
        await this.HienThiDanhSach()

        // Tìm kiếm row vừa cập nhật
        for (let i = 0; i < this.gview_DanhSach.list.length; i++) {
          if (vma_tb == this.gview_DanhSach.list[i].ma_tb) {
            this.$refs.dsphieu.$refs.grid.selectRow(i)
            break
          }
        }
      }
    },
    async LAY_DS_PHIEUVT_HDBH (phieuid, baohongid, hdtbid, kieutbi, status) {
      let params = {
        'phieu_id': phieuid,
        'baohong_id': baohongid,
        'hdtb_id': hdtbid,
        'kieutbi': kieutbi,
        'status': status
      }
      return this.GetDataList
    },
    async tsbtnChuyenTo_Click () {
      try {
        if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
          if (await this.LAY_DS_PHIEUVT_HDBH(this.phieu_id, 0, this.hdtb_id, 0, 0).length > 0) {
            this.$toast.error('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }

        this.$refs.popupChuyenTo.openDialog(this.phieu_id, this.hdtb_id)
      } catch (ex) {
        this.$toast.error(ex)
      }
    },
    closeChuyenTo () {
      this.HienThiDanhSach()
    },
    tsbtnDuAn_Click () {
      this.$refs.popupDuAnHDTB.showModal()
    },
    tsbtnHen_Click () {
      if (this.hdkh_id !== 0) {
        this.quyenduoc_hen = 1
        this.$refs.thongTinHenKhachHangModal.showModal()
      }
    },
    tsbtnThongTinKT_Click () {
      if (this.thuebao_id !== 0) {
        this.$refs.traCuuThongSoKTModal.showModal()
      }
    },
    tsbtnKQ_DoKiem_Click () {
      if (this.hdtb_id !== 0) {
        this.$refs.doKiem.showModal()
      } else {
        this.$toast.error('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnTraCuuNo_Click () {
      this.$refs.popupTCThongTinNo.openDialog(this.formProps.txtMaTB.trim())
    },
    async fn_huykichhoat_thanhly (status, hdtbid, ngayht, dichvuvtid, lchkngayht, luongid, loaitb_id, ma_tb) {
      let result = ''
      try {
        let params = {
          'status': status,
          'hdtb_id': hdtbid,
          'ngayht': ngayht,
          'dichvuvt_id': dichvuvtid,
          'l_chkngayht': lchkngayht,
          'luong_id': luongid,
          'loaitb_id': loaitb_id,
          'ma_tb': ma_tb
        }
        let response = await API.fn_huykichhoat_thanhly(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        } else {
          result = response.data.message
        }
      } catch (e) {
        console.log(e)
        result = e.message
      }
      return result
    },
    async fn_huykichhoat_thanhly_test(hdtbid) {
      let result = ''
      try {
        let params = {
          'hdtb_id': hdtbid,
          'capnhat_ngayht_kh': this.PHAILAM('CAPNHAT_NGAYHT_KH'),
          'capnhat_ngaykh_kh': this.PHAILAM('CAPNHAT_NGAYKH_KH')
        }
        let response = await API.fn_huykichhoat_thanhly_test(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        } else {
          result = response.data.message
        }
      } catch (e) {
        console.log(e)
        result = e.message
      }
      return result
    },
    async tsbtnHuyKH_Click () {
      if (this.gview_DanhSach.checked == null || this.gview_DanhSach.checked.length == 0) {
        this.$toast.error('Hãy chọn thuê bao để thực hiện hủy kích hoạt thanh lý!')
        return
      }
      this.loaitb_id = this.gview_DanhSach.selectedItem.loaitb_id
      this.hdtb_id = this.gview_DanhSach.selectedItem.hdtb_id

      let tDB = await this.get_app_config("DONGBO_TEST")
      if (tDB.dongbo == "0") {
        this.$toast.error('Bạn không có quyền hủy kích hoạt. Vui lòng liên hệ admin để được xử lý')
        return
      }

      if (tDB.keyvalue == "FALSE") {
        if (this.gview_DanhSach.checked.length === 0) {
          this.$toast.error('Hãy chọn phiếu để hủy kích hoạt thanh lý!')
          return
        }
        this.$confirm('Bạn có chắc chắn hủy kích hoạt thanh lý Account không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(() => {
          try {
            this.gview_DanhSach.checked.forEach(async (item) => {
              let res = await this.fn_huykichhoat_thanhly(TRANGTHAI_DONGBO.CHUADONGBO, item.hdtb_id, this.getDate(this.formProps.dtpNgayBG).format('DD/MM/YYYY'),
                this.listbox.cboDichVuVT.value, this.chkNgayHT.checked ? '1' : '0', this.luong_id, item.loaitb_id, this.gview_DanhSach.selectedItem.ma_tb)
              if (res !== '1') {
                this.$toast.error(res)
              } else {
                this.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!")
                await this.HienThiDanhSach()
              }
            })
          } catch (error) {
            this.$toast.error(error)
            console.log(error)
          }
        })
      } else {
        let res = await this.fn_huykichhoat_thanhly_test(this.hdtb_id)
        if (res !== '1') {
          this.$toast.error(res)
        } else {
          this.$toast.success("Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!")
          await this.HienThiDanhSach()
        }
      }
    },
    async fn_nut_giaonet (hdtbid, donviid, nhanvienid, kieu) {
      try {
        let params = {
          'hdtb_id': hdtbid,
          'donvi_id': donviid,
          'nhanvien_id': nhanvienid,
          'kieu': kieu
        }
        await API.fn_nut_giaonet(this.axios, params).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$toast.success(response.data.message)
          }
        }).catch(error => {
          this.$toast.error(error.data.message)
        })
      } catch (e) {
        console.log(e)
        this.$toast.error('Có lỗi khi chuyển tỉnh: ' + e.message)
      }
    },
    async tsbtnTinh_GiaoNET_NT_Click () {
      try {
        this.loading(true)
        await this.fn_nut_giaonet(this.hdtb_id, this.$root.token.getDonViID(), this.$root.token.getNhanVienID(), 1)
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async tsbtnHoanThanh_Click () {
      try {
        this.loading(true)
        // button này chỉ dành cho TSL
        if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == DichVuVienThong.TSL_DOITAC) {
          if (this.gview_DanhSach.selectedItem === null) {
            this.$toast.error('Chưa có thuê bao được lựa chọn. Bạn hãy kiểm tra lại! ')
            return
          }
        }
        // Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
        let ngayyc = this.gview_DanhSach.selectedItem
        let ngaytt = this.formateDate(new Date(ngayyc), 'DD/MM/YYYY')
        let ngayht = this.getDate(this.formProps.dtpNgayBG).format('DD/MM/YYYY')
        if (!this.gview_DanhSach.selectedItem.ngay_ht_gp) {
          this.$toast.error('Hãy cập nhật ngày hoàn thành!')
          return
        }
        let params = {
          'ngayht': ngayht,
          'ngaytt': ngaytt,
          'phieu_id': this.phieu_id,
          'huonggiao_id': this.huonggiao_id,
          'hdtb_id': this.hdtb_id,
          'ma_nd': this.$root.token.getUserName(),
          'lay_phieu_con': this.lay_phieu_con
        }
        await API.sp_hoanthanh_thanhly(this.axios, params).then(response => {
          console.log(response)
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$toast.success(response.data.message_detail)
          } else {
            this.$toast.error(response.data.message_detail)
          }
        }).catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        await this.HienThiDanhSach()
      } catch (e) {
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    KiemTraDK_HoanThanh (thangnamtt, thangnamsys) {
      let result = false
      // So sánh năm
      if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length)) < Number.parseInt(thangnamsys.substr(6, thangnamsys.length))) {
        // Nếu năm thanh toán < năm hoàn thành thì true;
        return true
      } else if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length)) === Number.parseInt(thangnamsys.substr(6, thangnamsys.length))) {
        // Nêu năm = nhau thì kiểm tra tiếp
        // Nếu tháng tt < thang ht -> true
        if (Number.parseInt(thangnamtt.substr(3, 5)) < Number.parseInt(thangnamsys.substr(3, 5))) {
          result = true
        } else if (Number.parseInt(thangnamtt.substr(3, 5)) === Number.parseInt(thangnamsys.substr(3, 5))) {
          if (Number.parseInt(thangnamtt.substr(0, 2)) <= Number.parseInt(thangnamsys.substr(0, 2))) {
            result = true
          } else {
            result = false
          }
        } else {
          result = false
        }
      } else {
        result = false
      }
      return result
    },
    async fn_hoancong_thanhly (luongid, phieuid, ngayht, kqxlid, huonggiaoid, vattubtn, thuhoi, noidungthuhoi, listchon) {
      let params = {
        'luong_id': luongid,
        'phieu_id': phieuid,
        'ngayht': ngayht,
        'kqxl_id': kqxlid,
        'huonggiao_id': huonggiaoid,
        'vattu_btn': vattubtn,
        'thuhoi': thuhoi,
        'noidung_thuhoi': noidungthuhoi,
        'list_chon': JSON.stringify(listchon)
      }
      await API.fn_hoancong_thanhly(this.axios, params).then(async response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (String(response.data.data) == "1" || String(response.data.data) == "Cập nhật dữ liệu thành công!") {
            this.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
            if (this.PHAILAM('TUDONG_HTHS') && this.PHAILAM('KT_HOANCONG_HD_BANCHEO')) {
              try {
                let ds = await this.hoancong_thicong_2(this.hdtb_id)
                if (ds.length > 0 && ds[0].hdtb_ban && Number(ds[0].hdtb_ban) > 0) { // bên thi công hoàn công update dữ liệu bên bán
                    let tinh = await this.tinh_thicong(Number(ds[0].tinhban))
                    let tinhtc_id = Number(ds[0].tinhtc)

                    await this.hoancong_thicong_v3(tinh.tinhthicong_id, Number(ds[0].hdtb_ban), Number(ds[0].khachhang_tc_id), 
                        Number(ds[0].thanhtoan_tc_id), Number(ds[0].thuebao_tc_id), tinhtc_id)
                } else { //bên bán hoàn thiện
                    let ds2 = await this.sp_lay_thongtin_bancheo(this.hdtb_id)
                    if (ds2.length != 0) { //anhnt thêm điều kiện thuebao_id_thicong != -1 cho chỉ giao net
                        if (String(ds2[0].loaihd_id) == String(LoaiHopDong.DAT_MOI) && 
                              String(ds2[0].thuebao_id_thicong) != "-1") {
                            if (String(ds2[0].dichvuvt_id) != String(DichVuVienThong.TSL)) {
                                let tinh = await this.tinh_thicong(Number(ds2[0].tinh_thicong))
                                await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[0].thuebao_id_thicong, 
                                    ds2[0].thuebao_id_ban, ds2[0].tinh_ban)
                            } else {
                                for (let k = 0; k < ds2.length; k++) {
                                    let tinh = await this.tinh_thicong(Number(ds2[k].tinh_thicong))
                                    await this.sp_them_db_bancheo_v2(tinh.tinhthicong_id, ds2[k].thuebao_id_thicong, 
                                        ds2[k].thuebao_id_ban, ds2[k].tinh_ban)
                                }
                            }
                        }
                    }
                }
              } catch (ex) {
                  await this.log_action("frmHTHSChamDutSD", 
                      "hdtb_id=" + this.hdtb_id + ", ngay_cn = " + moment(new Date()).format('DD/MM/YYYY HH:mm:ss') + ", Err : " + ex,
                      "Hoàn công thanh lý")
              }
            }
          } else {
            this.$toast.error(response.data.data)
          }    
        } else {
          this.$toast.error(response.data.message_detail)
        }
      }).catch(error => {
        this.$toast.error(error.data.message_detail)
      })

      await this.HienThiDanhSach()
    },
    async btnHoanCong_Click () {
      if (this.gview_DanhSach.checked.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để hoàn công')
        return
      }

      if (!await this.KT_DieuKien_TB(4)) return
      this.$confirm('Bạn thật sự muốn hoàn công không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        // 1 thu hoi 2 ko

        let pThuhoi = 0
        let list = this.gview_DanhSach.checked.filter(item => item.loaitb_id.toString() === LoaiHinhTB.INTERNET_MYTV.toString() &&
          !this.PHAILAM('TUDONG_THUHOI_KHI_HOANCONG'))
        if (list.length > 0) {
          await this.$confirm('Bạn có thu hồi được thiết bị kèm serial không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(() => {
            pThuhoi = 1
          }).catch(() => {
            pThuhoi = 2
          })

          if (pThuhoi === 0) {
            this.$toast.error('Bạn không chọn đúng câu trả lời Y/N')
            return
          }
        }
        try {
          this.loading(true)
          await this.fn_hoancong_thanhly(this.luong_id, this.phieu_id, this.getDate(this.formProps.dtpNgayBG).format('DD/MM/YYYY'), this.DmKetQuaXL.selectKQXL === null ? 0 : this.DmKetQuaXL.selectKQXL,
            this.huonggiao_id, this.tsbtnVatTu ? 0 : 1, pThuhoi, this.formProps.txtNoiDungTH, this.gview_DanhSach.checked)
        } catch (e) {
          console.log(e)
          this.$toast.error(e)
        } finally {
          this.loading(false)
        }
      })
    },
    async fn_hoanthien_thanhly (luongid, ddout, ngayht, listChon) {
      let result = ''
      try {
        let params = {
          'luong_id': luongid,
          'ddout': ddout,
          'ngayht': ngayht,
          'list_chon': JSON.stringify(listChon)
        }
        let response = await API.fn_hoanthien_thanhly(this.axios, params)
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        } else {
          result = response.data.message
        }
      } catch (e) {
        console.log(e)
        result = e.message
      }
      return result
    },
    async MakeRequestAPI_DONGBO_BACKEND(hdtb_id) {
      let result = "Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api_theo_command)"
      try {
          let params = {
            "hdtb_id": hdtb_id,
            "command": "DONGBO_IVAN"
          }
          let response = await API.lay_dulieu_cho_api_theo_command(this.axios, params)
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
              if (response.data.data != null && response.data.data.length > 0) {
                  let req = response.data.data[0]
                  let params_ivan = {
                      'dhsxkd_loai_thue_bao_id': req.dhsxkd_loai_thue_bao_id,
                      'dhsxkd_ma_khach_hang': req.dhsxkd_ma_khach_hang,
                      'dhsxkd_ma_thue_bao': req.dhsxkd_ma_thue_bao,
                      'dhsxkd_ma_tinh': req.dhsxkd_ma_tinh,
                      'ma_don_vi': req.ma_don_vi,
                      'ma_so_thue': req.ma_so_thue,
                  }
                  let response_ivan = await API.ivan_dongbo(this.axios, params_ivan)
                  if (response_ivan && response_ivan.data && response_ivan.data.errorCode == 0 
                                    && response_ivan.data.data && response_ivan.data.data.status == 200) {
                      result = "OK";
                  } else {
                      result = response_ivan.data.faultString
                  }
              }
          }    
      } catch (e) {
          console.log(e)
          result = e.message
      }
      return result
    },
    async MakeRequest_ThanhLy_BHXH(hdtb_id) {
      let result = "Có lỗi xảy ra trong quá trình kích hoạt. Liên hệ admin để được hỗ trợ. (func: lay_dulieu_cho_api)"
      try {
          let params = {
            "hdtb_id": hdtb_id
          }
          let response = await API.lay_dulieu_cho_api(this.axios, params)
          if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
              if (response.data.data != null && response.data.data.length > 0) {
                  let req = response.data.data[0]
                  let params_ivan = {
                      "dhsxkd_am_chuc_vu": req.dhsxkd_am_chuc_vu,
                      "dhsxkd_am_email": req.dhsxkd_am_email,
                      "dhsxkd_am_ho_ten": req.dhsxkd_am_ho_ten,
                      "dhsxkd_am_ma": req.dhsxkd_am_ma,
                      "dhsxkd_am_phong_ban": req.dhsxkd_am_phong_ban,
                      "dhsxkd_am_sdt": req.dhsxkd_am_sdt,
                      "dhsxkd_am_ten_ttkd": req.dhsxkd_am_ten_ttkd,
                      "dhsxkd_loai_thue_bao_id": req.dhsxkd_loai_thue_bao_id,
                      "dhsxkd_ma_khach_hang": req.dhsxkd_ma_khach_hang,
                      "dhsxkd_ma_thue_bao": req.dhsxkd_ma_thue_bao,
                      "dhsxkd_ma_tinh": req.dhsxkd_ma_tinh,
                      "ly_do_ngung_su_dung": req.ly_do_ngung_su_dung,
                      "ma_don_vi": req.ma_don_vi,
                      "ma_tinh": req.ma_tinh,
                      "ten_dang_nhap_am_backend": req.ten_dang_nhap_am_backend
                  }
                  let response_ivan = await API.ivan_thanhly_dv(this.axios, params_ivan)
                  if (response_ivan && response_ivan.data && response_ivan.data.errorCode == 0 
                          && response_ivan.data.data && response_ivan.data.data.status == 200) {
                      result = "OK";
                  } else {
                      result = response_ivan.data.faultString
                  }
              }
          }
      } catch (e) {
          console.log(e)
          result = e.message
      }
      return result
    },
    async logInIVAN_success() {
      this.gview_DanhSach.checked.forEach(async (dr) => {
        let hdtb_id = Number(dr.hdtb_id);
        let kq_db = await this.MakeRequestAPI_DONGBO_BACKEND(String(hdtb_id));
        if (kq_db != "OK") {
            this.$toast.error(kq_db);
            return;
        }
        var kq = await this.MakeRequest_ThanhLy_BHXH(Number(hdtb_id));
        if (kq != "OK")
          return;
      })

      await this.tsbtnChuyenDB_Click_1()
    },
    async tsbtnChuyenDB_Click() {
      if (this.gview_DanhSach.checked.length <= 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để hoàn công')
        return
      }

      // IVAN
      if (Number(this.loaitb_id) == LoaiHinhTB.IVAN && this.PHAILAM("RA_KICH_HOAT")) {
          this.$refs.ppDangNhapIVAN.openDialog()
      } else {
          await this.tsbtnChuyenDB_Click_1()
      }
    },
    async tsbtnChuyenDB_Click_1 () {
      try {
        this.loading(true)
        let result = await this.fn_hoanthien_thanhly(this.luong_id, 1, this.formProps.dtpNgayBG, this.gview_DanhSach.checked)
        if (result.includes('thành công') && !result.includes('không thành công')) {
          this.$toast.success(result)
          await this.HienThiDanhSach()
        } else {
          this.$toast.error(result)
        }
      } catch (e) {
        console.log(e)
        this.$toast.error(e)
      } finally {
        this.loading(false)
      }
    },
    async txtMaGD_KeyPress () {
      console.log("txtMaGD_KeyPress: " + this.formProps.txtMaGD)
      try {
        if (this.formProps.txtMaGD !== '') {
          let req = {
            'dichvuvt_id': this.listbox.cboDichVuVT.value,
            // "bancheo": 0,
            'lay_phieu_con': this.lay_phieu_con ? 1 : 0,
            'kieu': this.phieu_moi,
            'tthd_id': this.tthd_id,
            'huonggiao_id': this.huonggiao_id,
            'nhanvien_id': this.nhanvienId,
            'loai_id': this.phieu_moi
          }
          let data = await this.sp_lay_ds_thuebao_thanhly(req)
          if (data.length > 0) {
            let filter = data.filter(item => item.ma_gd.trim().toLowerCase().includes(this.formProps.txtMaGD.trim().toLowerCase()))
            this.gview_DanhSach.list = filter
            if (filter.length === 0) {
              this.Clear()
            }
          } else {
              this.Clear()
          }
        }
      } catch (error) {
        this.Clear()
        console.log(error)
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    async txtMaTB_KeyPress () {
      try {
        if (this.formProps.txtMaTB !== '') {
          let req = {
            'dichvuvt_id': this.listbox.cboDichVuVT.value,
            // "bancheo": 0,
            'lay_phieu_con': this.lay_phieu_con ? 1 : 0,
            'kieu': Number.parseInt(this.phieu_moi),
            'tthd_id': this.tthd_id,
            'huonggiao_id': this.huonggiao_id,
            'nhanvien_id': this.nhanvienId,
            'loai_id': Number.parseInt(this.phieu_moi)
          }
          let data = await this.sp_lay_ds_thuebao_thanhly(req)
          if (data.length > 0) {
            let filter = data.filter(item => item.ma_tb.toLowerCase().includes(this.formProps.txtMaTB.trim().toLowerCase()))
            this.gview_DanhSach.list = filter
            if (filter.length === 0) {
              this.Clear()
            }
          } else {
              this.Clear()
          }
        }
      } catch (error) {
        this.Clear()
        console.log(error)
        this.$toast.error('Có lỗi: ' + error)
      }
    },
    btnLayTTMoi_Click () {
      this.HienThiDanhSach()
    },
    onActionClick (action) {
      if (action.id === 'btnLayTTMoi') {
        this.btnLayTTMoi_Click()
      } else if (action.id === 'tsbtnChapNhan') {
        this.tsbtnChapNhan_Click()
      } else if (action.id === 'tsbtnGiaoViec') {
        this.tsbtnGiaoViec_Click()
      } else if (action.id === 'tsbtnKichHoat') {
        this.tsbnKichHoat_Click()
      } else if (action.id === 'tsbtnGiaoPhieu') {
        this.tsbtnGiaoPhieu_Click()
      } else if (action.id === 'tsbtnGiaoPhoiHop') {
        this.tsbtnGiaoPhoiHop_Click()
      } else if (action.id === 'tsbtnHoanCong') {
        this.btnHoanCong_Click()
      } else if (action.id === 'tsbtnHoanThanh') {
        this.tsbtnHoanThanh_Click()
      } else if (action.id === 'tsbtnVatTu') {
        this.tsbtnVatTu_Click()
      } else if (action.id === 'tsbtnVatTuMoi') {
        this.tsbtnVatTuMoi_Click()
      } else if (action.id === 'tsbtnThoaiTra') {
        this.tsbtnThoaiTra_Click()
      } else if (action.id === 'btnTraPhieu') {
        this.btnTraPhieu_Click()
      } else if (action.id === 'tsbtnChuyenTo') {
        this.tsbtnChuyenTo_Click()
      } else if (action.id === 'tsbtnDuAn') {
        this.tsbtnDuAn_Click()
      } else if (action.id === 'tsbtnHen') {
        this.tsbtnHen_Click()
      } else if (action.id === 'tsbtnHuyKH') {
        this.tsbtnHuyKH_Click()
      } else if (action.id === 'tsbtnKQ_DoKiem') {
        this.tsbtnKQ_DoKiem_Click()
      } else if (action.id === 'tsbtnTraCuuNo') {
        this.tsbtnTraCuuNo_Click()
      } else if (action.id === 'tsbtnThongTinKT') {
        this.tsbtnThongTinKT_Click()
      } else if (action.id === 'tsbtnTinh_GiaoNET_NT') {
        this.tsbtnTinh_GiaoNET_NT_Click()
      } else if (action.id === 'tsbtnHoanThienHS') {
        this.tsbtnChuyenDB_Click()
      } else if (action.id === 'btnInDS') {
        this.btnInDS_Click()
      } else if (action.id === 'tsbtnXuatExcel') {
        this.tsbtnXuatExcel_Click()
      } else if (action.id === 'tsbtnCreatIssue') {
        this.tsbtnCreatIssue_Click()
      }
    },
    showPopupSearchContract () {
      this.$refs.searchContractModal.clearData();
      this.$refs.searchContractModal.loadPopup();
      this.$refs.popupFrmTraCuuHopDong.show();
      // this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract (item) {
      console.log(item)
      this.formProps.txtMaGD = item.hopdong.ma_gd ? item.hopdong.ma_gd : "";
      this.$refs.popupFrmTraCuuHopDong.hide();
      this.txtMaGD_KeyPress()
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
      let index1 = this.actionsDropdown.findIndex(item => item.id === id)
      if (index1 >= 0) {
        this.actionsDropdown[index1].active = status
      }
    },
    setVisibleButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }
      let index1 = this.actionsDropdown.findIndex(item => item.id === id)
      if (index1 >= 0) {
        this.actionsDropdown[index1].visible = status
      }
    },
    Clear () {
      this.formProps.txtHostLD = ''
      this.formProps.txtHostVL = ''
      this.phieu_id = 0
      this.hdtb_id = 0
      this.hdkh_id = 0
      this.formProps.txtMaGD = ''
      this.formProps.txtMaTB = ''
      this.formProps.txtTenTB = ''
      this.formProps.txtDiaChiLD = ''
      this.dgvNhanVien.list = []
      this.dgvDS_ThietBi.list = []
      this.listbox.cboNguoiGV.value = this.nhanvienId
      this.chkNgayBG.checked = false
      this.chkNgayGV.checked = false
      this.formProps.dtpNgayBG = this.formateDate(new Date(), null)
      this.formProps.dtpNgayGV = this.formateDate(new Date(), null)
      this.formProps.txtNDGiao = ''
      this.formProps.txtThongTinTra = ''
      this.setActiveButton('tsbtnHoanThienHS', false)
      this.setActiveButton('tsbtnHoanCong', false)
      this.setActiveButton('tsbtnKichHoat', false)
      this.setActiveButton('tsbtnGiaoPhieu', false)
      this.$refs.txtMaTB.focus()
      this.formProps.txtTramTB = ''
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
    },
    ChuyenChuHoa (str) {
      let kq = ''
      let regex = /[A-Z][A-Z]/g
      if (regex.test(str)) kq = str.toUpperCase()
      else {
        if (str[0] === '(') {
          kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
        } else {
          kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
        }
      }
      return kq
    },
    getDate (dateStr) {
      return moment(dateStr, 'DD/MM/YYYY hh:mm A')
    },
    formateDate (date, format) {
      let defaultFormat = 'DD/MM/YYYY hh:mm A'
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format(defaultFormat)
      }
    },
    async btnInDS_Click() {
        let vhdkh_id = await this.fn_tt_hd_thuebao(1, this.hdtb_id)
        if (vhdkh_id == "-1") {
            this.$toast.error("Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!");
            return;
        }

        if (this.gview_DanhSach.checked.length == 0) {
            this.$toast.error("Hãy chọn phiếu");
            return;
        }

        let n_hdkh_id = "", n_hdtb_id = "", n_phieu_id = "";
        for (let k = 0; k < this.gview_DanhSach.checked.length; k++) {
            n_hdkh_id += String(this.gview_DanhSach.checked[k].hdkh_id) + ","
            n_hdtb_id += String(this.gview_DanhSach.checked[k].hdtb_id) + ","
            n_phieu_id += String(this.gview_DanhSach.checked[k].phieu_id) + ","
        }
        
        if (n_hdkh_id != "" && n_phieu_id != "" && n_hdtb_id != "") {
            this.modelIn.n_phieu_id = n_phieu_id.substring(0, n_phieu_id.length - 1)
            this.modelIn.n_hdkh_id = n_hdkh_id.substring(0, n_hdkh_id.length - 1)
            this.modelIn.n_hdtb_id = n_hdtb_id.substring(0, n_hdtb_id.length - 1)
            this.modelIn.nvth_id = -1
            this.modelIn.huonggiao_id = this.huonggiao_id
            this.modelIn.tentram_tb = this.formProps.txtTramTB
            this.modelIn.cv_thuchien = ""
            console.log(this.modelIn)
            this.$refs.popupInBB.showModal()
        }
    },
    gview_DanhSach_queryCellInfo(args) {
        if (args.column.field === 'ngay_hh') {
            let dhh = moment(args.cell.innerHTML, 'DD/MM/YYYY HH:mm:ss')
            if (dhh < new Date()) {
                args.cell.setAttribute('style', 'background-color:red;');
            } 
        }
    },
    tsbtnCreatIssue_Click() {
        let vip_gs = false;
        if (this.PHAILAM("VIP_GIAMSAT_IT360"))
            vip_gs = true;

        var data = {
            phieu_id: this.phieu_id,
            loai: 1, 
            vip_giamsat: vip_gs,
            hdtb_id: this.hdtb_id
        }
        console.log(data)
        this.$refs.dialogCreateIssue.openDialog(data);
    },
    giaophieu_success() {
      this.HienThiDanhSach()
    },
    async log_action(vaction_name, vkey_parameter, vnote) {
        try {
            let params = {
                "vaction_name": vaction_name,
                "vkey_parameter": vkey_parameter,
                "vnote": vnote
            }
            let response = await API.log_action(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },

    async sp_them_db_bancheo_v2(tinhthicong_id, thuebao_id, thuebao_kn_id, tinh_kn_id) {
        try {
            let params = {
                "tinhthicong_id": tinhthicong_id,
                "thuebao_id": thuebao_id,
                "thuebao_kn_id": thuebao_kn_id,
                "tinh_kn_id": tinh_kn_id
            }
            let response = await API.sp_them_db_bancheo_v2(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },

    async sp_lay_thongtin_bancheo(vhdtb_id) {
        let result = []
        try {
            let params = {
                "vhdtb_id": vhdtb_id
            }
            let response = await API.sp_lay_thongtin_bancheo(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },

    async hoancong_thicong_v3(tinhthicong_id, vhdtb_ban, vkhid_tc, vttid_tc, vtbid_tc, vtinh_tc) {
        try {
            let params = {
                "tinhthicong_id": tinhthicong_id,
                "vhdtb_ban": vhdtb_ban,
                "vkhid_tc": vkhid_tc,
                "vttid_tc": vttid_tc,
                "vtbid_tc": vtbid_tc,
                "vtinh_tc": vtinh_tc
            }
            let response = await API.hoancong_thicong_v3(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                
            } else {
              this.$toast.error(response.data.message)
            }      
        } catch (e) {
            this.$toast.error(e)
            console.log(e)
        }
    },

    async lay_thongtin_tinh(tinh_id) {
        let res = []
        try {
            let params = {
                "tinh_id": tinh_id
            }
            let response = await API.lay_thongtin_tinh(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                res = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return res
    },

    async tinh_thicong(tinh_id) {
        let tinh = {}
        let rs = await this.lay_thongtin_tinh(tinh_id)
        if (rs.length > 0) {
            let dr = rs[0];
            tinh.user = dr.ten_truycap;
            tinh.pass = dr.mat_khau;
            tinh.nhanvien_id = Number(dr.nhanvien_id);
            tinh.donvi_id = Number(dr.donvi_id);
            tinh.matinh = dr.matinh;
            tinh.TNS = dr.tns_string;
            tinh.tinhthicong_id = Number(dr.tinh_id);
            tinh.mavung = dr.ma_vung;
            tinh.ip_dll = dr.ip_dll;
        } else {
            tinh.user = "";
            tinh.pass = "";
            tinh.nhanvien_id = 0;
            tinh.donvi_id = 0;
            tinh.matinh = "";
            tinh.TNS = "";
            tinh.tinhthicong_id = 0;
            tinh.ip_dll = "";

        }
        return tinh;
    },

    async hoancong_thicong_2(hdtb_id) {
        let result = []
        try {
            let params = {
                "hdtb_id": hdtb_id
            }
            let response = await API.hoancong_thicong_2(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
    },

    async get_app_config(keyname) {
        let result = []
        try {
            let params = {
                "keyname": keyname
            }
            let response = await API.get_app_config(this.axios, params);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data
            }            
        } catch (e) {
            console.log(e)
        }
        return result
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
<style>
.traphieu .page-content {
  position: unset;
}

.traphieu-content {
  width: 1200px;
}

.disabled_color {
  color: grey !important;
}

.mx-input:disabled {
  color: #444040 !important;
}
</style>
