<template src="./ChangeServiceOMCVIPCM.html">
</template>
<style scoped src="./ChangeServiceOMCVIPCM.css"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import ChangeServiceOMCVIPCMAPI from './ChangeServiceOMCVIPCMAPI'
import {LoaiHopDong, NHOM_KQXL, TRANGTHAI_DONGBO, TrangThaiHD} from '../../../../utils/Enum'
import {DichVuVienThong, LOAI_DV, LoaiHinhTB} from '../../../../const/enums'
import ExportDataModal from '../../profile/ExportData/ExportDataModal'
import ChuyenTo from '../../profile/CompleteProfileChangeAccount/Popups/ChuyenTo'
import TonPhieu from '../Payment/components/TonPhieu'
import ThongTinHenKhachHangModal from '../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal'
import Traphieu from '../ReturnInstallTicket/components/traphieu'
import Giaophieunhanvien from '../DeclareLandline/popup/giaophieunhanvien'
import VatTuThueBao from './popups/VatTuThueBao'
import DoiNeHNI from './popups/DoiNeHNI'
import KetQuaDoKiem from './popups/KetQuaDoKiem/KetQuaDoKiem'
import ThayDoiLyDoHuy from './popups/ThayDoiLyDoHuy/ThayDoiLyDoHuy'
import TCThongTinNo from './popups/TCThongTinNo/TCThongTinNo'
import DuAnHDTB from './popups/DuAnHDTB/DuAnHDTB'
import TraCuuThongSoKTModal from '../DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal'
import ComboboxGridExt from './components/ComboboxGrid/index'
import InBienBan from '../../../print/InBienBan/InBienBan'
import CapVatTuTB from '../../complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB'
import Vue from 'vue'
import {maxLength, minLength} from 'vuelidate/lib/validators'
import CreateIssue from '../../complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue'
import GiaoPhieuView from '@/modules/contract/setup/HandoverTicket/giao_phieu_view.vue';

export default {
  name: 'ChangeServiceOMCVIPCM',
  components: {
    breadcrumb,
    KDatePicker,
    ExportDataModal,
    ChuyenTo,
    TonPhieu,
    ThongTinHenKhachHangModal,
    Traphieu,
    Giaophieunhanvien,
    VatTuThueBao,
    DoiNeHNI,
    KetQuaDoKiem,
    ThayDoiLyDoHuy,
    TCThongTinNo,
    DuAnHDTB,
    TraCuuThongSoKTModal,
    ComboboxGridExt,
    InBienBan,
    CapVatTuTB,
    CreateIssue,
    GiaoPhieuView
  },
  props: {
    Tag: {
      type: String,
      //required: true
    },
    _vquytrinh_id: {
      type: Number,
      //required: true
    }
  },
  data () {
    return {
      header: {
        title: 'OMC/Vip/Tổ CM- Thay đổi dịch vụ',
        list: [
          {name: 'Lập hợp đồng/Đổi tốc độ', link: {name: 'Ui.cards'}},
          {
            name: 'Loại hình thuê bao/OMC/Vip/Tổ CM- Thay đổi dịch vụ',
            link: {name: 'Ui.buttons'}
          }
        ]
      },
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus',
          visible: false
        },
        {
          id: 'btnLayTTMoi',
          name: 'Lấy TT',
          active: true,
          icon_class: 'one-file-attach',
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
          id: 'tsbtnDoiNe',
          name: 'Đổi Ne',
          active: true,
          icon_class: 'one-file-edit2',
          visible: false
        },
        {
          id: 'tsbtnTraPhieu',
          name: 'Trả phiếu',
          active: true,
          icon_class: 'one-file-back',
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
          visible: false
        },
        {
          id: 'tsbtnHuyKH',
          name: 'Hủy kích hoạt',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          visible: false
        },
        {
          id: 'tsbtnGiaoPhieu',
          name: 'Giao phiếu',
          active: true,
          icon_class: 'one-file-edit2',
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
          id: 'tsbtnXuatFile',
          name: 'Xuất file',
          active: true,
          icon_class: 'one-excel',
          visible: true
        },
        {
          id: 'tsbtnHoanCong',
          name: 'Hoàn công',
          active: false,
          icon_class: 'one-file-arrow-right1',
          visible: true
        },
        {
          id: 'tsbtnHoanThanh',
          name: 'Hoàn thành',
          active: false,
          icon_class: 'one-file-arrow-right1',
          visible: true
        },
        {
          id: 'btnInDS',
          name: 'Danh sách',
          active: true,
          icon_class: 'one-print',
          visible: true
        },
        {
          id: 'tsbtnThoaiTra',
          name: 'Thoái trả',
          active: true,
          icon_class: 'dropdown-icon  nc-icon-glyph arrows-2_push-next',
          visible: true
        },
        {
          id: 'tsbtnHoanThienHS',
          name: 'Hoàn thiện',
          active: false,
          icon_class: 'one-file-check',
          visible: true
        },
        {
          id: 'tsbtnHen',
          name: 'Hẹn LĐ',
          active: true,
          icon_class: 'one-calendar-number',
          visible: true
        },
        {
          id: 'tsbtnThongTinKT',
          name: 'Thông số KT',
          active: true,
          icon_class: 'dropdown-icon nc-icon-glyph ui-1_settings',
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
          id: 'tsbtnGiaoPhieu_Ton',
          name: 'Tồn phiếu',
          active: true,
          icon_class: 'dropdown-icon one-calendar-number',
          visible: true
        }
      ],
      actionsDropdown: [
        {
          id: 'tsbtnVatTuMoi',
          name: 'Vật tư CN',
          active: true,
          icon_class: 'one-vt',
          visible: true
        },
        {
          id: 'tsbtnTraCuuNo',
          name: 'Tra cứu nợ',
          active: true,
          icon_class: 'dropdown-icon ui-1_lock nc-icon-glyph',
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
          id: 'tsbtnDuAn',
          name: 'Dự án',
          active: true,
          icon_class: 'dropdown-icon one-clipart-edit',
          visible: true
        },
        {
          id: 'tsbtnKQ_DoKiem',
          name: 'Đo kiểm',
          active: true,
          icon_class: 'dropdown-icon nc-icon-glyph design_design',
          visible: true
        },
        {
          id: 'tsbtnCreatIssue',
          name: 'Gửi YC',
          active: true,
          icon_class: 'one-file-download',
          visible: true
        },
        {
          id: 'tsbtUpHS',
          name: 'Upload hồ sơ',
          active: true,
          icon_class: 'icon one-upload',
          visible: true
        }
      ],
      cboDichVuVT: {
        list: [],
        disable: false,
        value: 0
      },
      cboQuyTrinh: {
        list: [],
        disable: false,
        value: 0
      },
      cboNguoiGV: {
        list: [],
        disable: false,
        value: 0
      },
      cboNhomKQ: {
        list: [],
        disable: false,
        value: 0
      },
      cboLoaiDVTK: {
        list: [],
        disable: true,
        value: 0
      },
      cboDViTimKiem: {
        data: [],
        disable: true,
        textField: 'ten_dv',
        valueField: 'donvi_id',
        panelDataHeight: 'auto',
        headers: [
          {
            fieldName: 'ten_dv',
            headerText: 'Tên đơn vị',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectDViTimKiem: null
      },
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
      frmThongTinPhieuYc: {
        txtTenTB: '',
        txtDiaChiTB: '',
        txtDiaChiLD: '',
        txtHostLD: '',
        txtGhiChu: '',
        txtMaGD: '',
        txtMaTB: '',
        dtpNgayBG: this.formatDate(new Date(), null),
        dtpNgayGV: this.formatDate(new Date(), null),
        txtNeCu: '',
        txtNeMoi: '',
        lienhe: ''
      },
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      disableBox: {
        txtTenTB: false,
        txtDiaChiTB: false,
        txtDiaChiLD: false,
        txtHostLD: false,
        txtGhiChu: false,
        txtMaGD: false,
        txtMaTB: false,
        dtpNgayBG: true,
        dtpNgayGV: true,
        txtNeCu: false,
        txtNeMoi: false,
        txtNDGiao: false,
        txtThongTinTra: false,
        txtNoiDungTH: false
      },
      chkNgayGV: {
        checked: false,
        disable: false
      },
      chkNgayBG: {
        checked: false,
        disable: false
      },
      dgvNhanVien: {
        data: [],
        headers: [
          {
            fieldName: 'ten_nv',
            headerText: 'Tên nhân viên',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'so_dt',
            headerText: 'Điện thoại',
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
        ]
      },
      grvDVGT: {
        data: [],
        headers: [
          {
            fieldName: 'ten_dvgt',
            headerText: 'Tên dịch vụ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_dvgt',
            headerText: 'Mã DVGT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kieu',
            headerText: 'Kiểu yêu cầu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'noidung',
            headerText: 'Nội dung',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        selectItem: null,
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
      frmThongTinChung: {
        txtNDGiao: '',
        txtThongTinTra: '',
        txtNoiDungTH: ''
      },
      frmDanhSachTBTDDV: {
        loaiPhieu: 'rdoPhieuMoi'
      },
      chkTimKiem: {
        checked: false,
        disable: false
      },
      settingSelect2: {
        language: 'vi'
      },
      gview_DanhSach: {
        data: [],
        headers: [
          {
            fieldName: 'ngay_yc',
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
            visible: true
          },
          {
            fieldName: 'ma_hd',
            headerText: 'Mã HĐ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
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
            fieldName: 'so_dt',
            headerText: 'Điện thoại LH',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diemtinnhiem',
            headerText: 'Điểm tín nhiệm',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ngay_hh',
            headerText: 'Ngày hết hạn',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            template: function () {
              return {
                template: Vue.component('NgayHetHanTemplate', {
                  template: `<span>{{ data.ngay_hh }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'ma_kv',
            headerText: 'Mã khu vực',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'ten_kv',
            headerText: 'Tên khu vực',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'chitieu_tg',
            headerText: 'Chỉ tiêu TG',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ LĐ',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'ten_dcd',
            headerText: 'DV cài đặt',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'dv_dcd',
            headerText: 'DV chưa cài đặt',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: '',
            headerText: 'Trạng thái in',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'loaitb_id',
            headerText: 'Loại TBID',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'dv_giao',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_status',
            headerText: 'Kích hoạt',
            template: function () {
              return {
                template: Vue.component('KichHoatTemplate', {
                  template: `<span class="text-danger">{{ data.ten_status }}</span>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            },
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
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        selectionOptions: {
          checkboxMode: 'ResetOnRowClick',
          enableToggle: true
        }
      },
      dtThaotac: [],
      hdtb_id: 0,
      thuebao_id: 0,
      hdkh_id: 0,
      quyen_duoc_hen: 0,
      dichvuvt_id: 0,
      phieu_id: 0,
      loaitb_id: 0,
      async_status: false,
      status: 0,
      tb_mytv: '',
      tb_mega: '',
      tb_cd: '',
      madoicap: '',
      quytrinh_id: 0,
      tthd_id: 0,
      luong_id: 0,
      huonggiao_id: -1,
      dsloaihd_id: '',
      dsdichvuvt_id: '',
      vquytrinh_id: 0,
      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',
      kt_thaydoi: false,
      nhantin_nhanvien_diaban: 0,
      PHAINHAP_GHICHU_TTVT_XN_NO: -1,
      THAYDOIDV_NHIEUPHIEU: -1,
      dongbo_dadv: -1,
      cty_newline: false,
      load: false,
      visibleListCtl: {
        btnLyDoHuy: false,
        boxNeCu: true,
        boxNeMoi: false
      },
      nhanvienId: Number.parseInt(this.$root.token.getNhanVienID()),
      donviID: Number.parseInt(this.$root.token.getDonViID()),
      dsGiaoPhieuNV: [],
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
        phieu_id: 0,
        ma_tb: '',
        hdtb_id: 0
      },
      modelVatTu: {
        phieu_id: 0,
        loaihd_id: 0,
        hdtb_id: 0,
        thuebao_id: 0
      },
      modelVatTu2: {},
      modelDoiNe: {
        maTb: '',
        soneCu: '',
        hdtbId: 0
      },
      modelThayDoiLyDoHuy: {
        vkieu: 1,
        loaitb_id: 0,
        loaild_id: 0,
        hdtb_id: 0
      },
      modelIn: {},
      showModalTraphieu: false,
      maycn: '',
      ipcn: '',
      popupComponent: null,
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
      DS_CHUQUAN_THEO_QUYEN: null
    }
  },
  validations: {
    frmThongTinChung: {
      txtNoiDungTH: {
        minLength: minLength(0),
        maxLength: maxLength(1000)
      }
    }
  },
  created () {
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function (v) {
        v.removeAttribute('tabindex')
      })
    })
  },
  mounted () {
    this.frmChangeServiceOMCVIPCM_Load()
  },
  watch: {
    'cboDichVuVT.value': function (newValue) {
      this.cboDichVuVT_SelectedValueChanged(newValue)
    },
    'cboQuyTrinh.value': function (newValue) {
      this.cboQuyTrinh_SelectedValueChanged(newValue)
    },
    'cboNhomKQ.value': function (newValue) {
      this.cboNhomKQ_EditValueChanged(newValue)
    },
    'cboLoaiDVTK.value': function (newValue, oldValue) {
      if (Number.parseInt(newValue) === LOAI_DV.HOST) {
        if (this.cboDViTimKiem.data.length === 0) {
          this.SP_LAY_DS_DONVI_THEO_LOAIDV(newValue)
        }
      }
    },
    'cboDViTimKiem.selectDViTimKiem': function (newValue, oldValue) {
      if (newValue != null) {
        try {
          this.HienThiDanhSachHDTB_TK_LOAIDV(Number.parseInt(this.cboLoaiDVTK.value), newValue)
        } catch (error) {
          this.$root.$toast.error('Có lỗi: ', error)
        }
      }
    },
    'gview_DanhSach.data': {
      handler: function (val, oldVal) {
        try {
          if (val.length > 0) {
            let keys = Object.keys(val[0])
            if (keys.includes('ma_kv')) {
              let makvindex = this.gview_DanhSach.headers.findIndex(item => item.fieldName === 'ma_kv')
              if (makvindex >= 0) {
                let tenkvindex = this.gview_DanhSach.headers.findIndex(item => item.fieldName === 'ten_kv')

                if (tenkvindex >= 0) {
                  this.gview_DanhSach.headers[makvindex].visible = this.gview_DanhSach.headers[tenkvindex].visible
                }
              }
            } else {
              let makvindex = this.gview_DanhSach.headers.findIndex(item => item.fieldName === 'ma_kv')
              if (makvindex >= 0) {
                this.gview_DanhSach.headers[makvindex].visible = false
              }
            }
            if (keys.includes('ngay_yc')) {
              this.gview_DanhSach.data.forEach(value => {
                let ngayyc = moment(value.ngay_yc, 'DD/MM/YYYY hh:mm:ss').toDate()
                ngayyc.setDate(ngayyc.getDate() + 2)
                value.ngay_hh = moment(ngayyc).format('DD/MM/YYYY hh:mm:ss')
              })
            } else {
              let ngayhhindex = this.gview_DanhSach.headers.findIndex(item => item.fieldName === 'ngay_hh')
              if (ngayhhindex >= 0) {
                let ngayycindex = this.gview_DanhSach.headers.findIndex(item => item.fieldName === 'ngay_yc')

                if (ngayycindex >= 0) {
                  this.gview_DanhSach.headers[ngayhhindex].visible = this.gview_DanhSach.headers[ngayycindex].visible
                } else {
                  this.gview_DanhSach.headers[ngayhhindex].visible = false
                }
              }
            }
          }
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ', error.message)
        }
      },
      deep: true
    }
  },
  methods: {
    async HienThiCbo_KQ_XL (nhomKqId) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_NGUYENNHAN_NOCUOC(this.axios, {
        'vnhomkq_id': nhomKqId
      }))
      if (data.length > 0) {
        this.DmKetQuaXL.data = data
        this.DmKetQuaXL.selectKQXL = data[0].kqxl_id
      }
    },
    async frmChangeServiceOMCVIPCM_Load () {
      this.maycn = await this.$root.token.getMachine()
      this.ipcn = await this.$root.token.getIP()
      this.getTag()
      if (this.tthd_id === TrangThaiHD.DANG_THI_CONG) {
        this.visibleListCtl.btnLyDoHuy = true
      }
      await this.LAY_DS_THAMSO_MD()
      await this.GET_DICHVU_VT()
      await this.LAY_DS_NHANVIEN_THEO_DONVI()
      this.setVisibleButton('tsbtnDoiNe', false)
      if (this._vquytrinh_id) {
        this.cboQuyTrinh.value = this._vquytrinh_id
      }
      await this.HienThiDanhSach()
      this.load = true
    },
    cboQuyTrinh_SelectedValueChanged (value) {
      this.quytrinh_id = value
      this.DS_HUONGGIAO(this.tthd_id, value)
    },
    cboDichVuVT_SelectedValueChanged (value) {
      if (this.cboDichVuVT.list.length > 0) {
        this.dichvuvt_id = value
        this.visibleListCtl.boxNeCu = false
        this.visibleListCtl.boxNeMoi = false
        this.SP_LAY_DS_QUYTRINH_V2(this.dsloaihd_id, value, this.tthd_id, 0)

        if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
          if (this.$root.token.getMaTinh().toString().toLowerCase() === 'hni') {
            this.visibleListCtl.boxNeMoi = true
            this.visibleListCtl.boxNeCu = true
          }
          this.actions[this.actions.findIndex(item => item.id === 'tsbtnKichHoat')].name = 'Kích hoạt tổng đài'
        } else {
          this.actions[this.actions.findIndex(item => item.id === 'tsbtnKichHoat')].name = 'Kích hoạt'
        }
      }
    },
    cboNhomKQ_EditValueChanged (value) {
      this.HienThiCbo_KQ_XL(value)
      if (this.PHAILAM('PHAINHAP_GHICHU_TTVT_XN_NO')) {
        // Nếu KH tiếp tục đồng ý sử dụng thì phải trả phiếu lại
        if (value == NHOM_KQXL.KH_DONGY_TIEPTUC_SUDUNG) {
          this.setVisibleButton('tsbtnThoaiTra', true)
          this.setVisibleButton('tsbtnHoanCong', false)
          this.setVisibleButton('tsbtnKichHoat', false)
          this.setVisibleButton('tsbtnHen', false)
          this.setVisibleButton('tsbtnXuatFileKP', false)
          this.setVisibleButton('tsbtnXuatFileTL', false)
          this.setVisibleButton('btnInDS', false)
        } else {
          this.setVisibleButton('tsbtnThoaiTra', false)
          this.HienThiGiaoDien(this.luong_id)
          if (this.gview_DanhSach.selectItem !== null) {
            this.AnHienNut(this.gview_DanhSach.selectItem)

          }
        }
      } else {
        this.setVisibleButton('tsbtnThoaiTra', false)
        this.HienThiGiaoDien(this.luong_id)
        if (this.gview_DanhSach.selectItem !== null) {
          this.AnHienNut(this.gview_DanhSach.selectItem)
        }
      }

    },
    gview_DanhSach_SelectedItemsChanged (records) {
      this.gview_DanhSach.checked = records
    },
    gview_DanhSach_SelectRow (i, item) {
      this.gview_DanhSach.selectItem = item
    },
    async gview_DanhSach_selectedRowChanged (item) {
      try {
        this.loading(true)
        console.log(item)
        if (item != null) {
          this.gview_DanhSach.selectItem = item
          this.hdtb_id = Number.parseInt(item.hdtb_id)
          this.thuebao_id = Number.parseInt(item.thuebao_id)
          await this.HienThiDS_LyDoHuy(this.hdtb_id)
          this.phieu_id = Number.parseInt(item.phieu_id)
          await this.SP_HT_PHIEU_TRA(this.phieu_id)
          this.frmThongTinChung.txtNDGiao = item.nd_giao
          this.frmThongTinChung.txtNoiDungTH = item.nd_thuchien
          this.loaitb_id = Number.parseInt(item.loaitb_id)
          this.hdtb_id = Number.parseInt(item.hdtb_id)
          this.hdkh_id = Number.parseInt(item.hdkh_id)
          // Lấy thông tin liên hệ lắp đặt thuê bao
          await this.FN_LAY_THONGTIN_LH_LAPDAT(this.hdkh_id)
          this.frmThongTinPhieuYc.txtMaGD = item.ma_gd.trim()
          this.frmThongTinPhieuYc.txtMaTB = item.ma_tb.trim()
          this.frmThongTinPhieuYc.txtTenTB = this.ChuanHoaTen(item.ten_tb)
          this.frmThongTinPhieuYc.txtDiaChiTB = this.ChuanHoaTen(item.diachi_tb)
          this.frmThongTinPhieuYc.txtDiaChiLD = this.ChuanHoaTen(item.diachi_ld)
          this.frmThongTinPhieuYc.txtGhiChu = item.ghichu
          this.loaitb_id = Number.parseInt(item.loaitb_id)
          if (item.status !== '') {
            this.status = Number.parseInt(item.status)
          } else {
            this.status = -1
          }
          this.setActiveButton('tsbtnHuyKH', false)

          if (this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
            this.setActiveButton('tsbtnHuyKH', true)
          }
          await this.HienThiDVGT()
          await this.HienThiDSNV(this.phieu_id, this.nhanvienId)
          this.AnHienNut(item)
          this.frmThongTinPhieuYc.txtHostLD = ''

          if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
            if (this.PHAILAM('HIENTHI_HOST')) {
              // Nếu có tham số này thì hiển thị thông tin host lắp đặt, host vật lý
              await this.HIENTHI_HOST(this.hdtb_id, 0)
            }
            // Dùng cho HNI, đảo cực
            if (this.PHAILAM('DOINE')) {
              await this.SP_LAY_NE_MOI_THEO_HDTB_ID(this.hdtb_id)
            }
          }
        } else {
          this.clear_data()
          this.setActiveButton('tsbtnHoanThienHS', false)
          this.setActiveButton('tsbtnHoanCong', false)
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi:', error)
      } finally {
        this.loading(false)
      }
    },
    async SP_LAY_NE_MOI_THEO_HDTB_ID (hdtbid) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_NE_MOI_THEO_HDTB_ID(this.axios, {
        'p_hdtb_id': hdtbid
      }))
      if (data.length > 0) {
        this.frmThongTinPhieuYc.txtNeCu = data[0].ne
        this.frmThongTinPhieuYc.txtNeMoi = data[0].ne_moi
      }
    },
    async btnNDTH_Click () {
      try {
        if (this.phieu_id === 0) {
          return
        }
        let NoiDungTH = this.frmThongTinChung.txtNoiDungTH ? this.frmThongTinChung.txtNoiDungTH : ''
        let result = await this.UPDATE_ND_THUCHIEN(this.phieu_id, NoiDungTH)
        if (result) {
          this.$root.$toast.success('Cập nhật thành công')
          await this.HienThiDanhSach()
        } else {
          this.$refs.txtNoiDungTH.focus()
          this.$v.$touch()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi : ' + error.message)
      }
    },
    async SP_LAY_DS_DONVI_THEO_LOAIDV (loaidvid) {
      if (this.cboDViTimKiem.data.length === 0) {
        let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_DS_DONVI_THEO_LOAIDV(this.axios, {
          loaidv_id: loaidvid
        }))
        if (data.length > 0) {
          this.cboDViTimKiem.data = data
        }
      }
    },
    async HIENTHI_HOST (hdtbid, kieu) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_THONGTIN_HOST(this.axios, hdtbid, kieu))
      if (data.length > 0) {
        this.frmThongTinPhieuYc.txtHostLD = data[0].host_ld
      }
    },
    async HienThiDVGT () {
      this.grvDVGT.data = []
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_DANGKY_DVGT_V3(this.axios, {
        hdtb_id: this.hdtb_id
      }))
      if (data.length > 0) {
        this.grvDVGT.data = data
        this.setActiveButton('tsbtnHoanThienHS', true)
      } else {
        this.grvDVGT.data = []
        this.setActiveButton('tsbtnHoanThienHS', false)
      }
    },
    async HienThiDSNV (phieuId, nhanvienid) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
        'phieu_id': phieuId,
        'kieu_id': 2
      }))
      if (data.length > 0) {
        this.chkNgayGV.checked = true
        this.frmThongTinPhieuYc.dtpNgayGV = data[0].ngaygiao
        this.cboNguoiGV.value = Number.parseInt(data[0].nhanvien_giao_id)
        this.dgvNhanVien.data = data
      } else {
        this.dgvNhanVien.data = []
        this.chkNgayGV.checked = false
        this.frmThongTinPhieuYc.dtpNgayGV = this.formatDate(new Date(), null)
        this.cboNguoiGV.value = this.nhanvienId
      }
    },
    async HienThiDS_LyDoHuy (hdtbId) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_DS_LYDOHUY_HDTB(this.axios, {
        hdtb_id: hdtbId
      }))
      if (data.length > 0) {
        this.gridVLyDoHuyHDTB.data = data
      } else {
        this.gridVLyDoHuyHDTB.data = []
      }
    },
    async FN_LAY_THONGTIN_LH_LAPDAT (hdkhid) {
      let data = this.GetData(await ChangeServiceOMCVIPCMAPI.FN_LAY_THONGTIN_LH_LAPDAT(this.axios, {
        hdkh_id: hdkhid
      }))
      if (data) {
        this.frmThongTinPhieuYc.lienhe = 'Liên hệ:' + data
      } else {
        this.frmThongTinPhieuYc.lienhe = 'Liên hệ: '
      }
    },
    async SP_HT_PHIEU_TRA (phieuid) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_HT_PHIEU_TRA(this.axios, {
        phieu_id: phieuid
      }))
      if (data.length > 0) {
        this.frmThongTinChung.txtThongTinTra = data[0].nd_tra
      } else {
        this.frmThongTinChung.txtThongTinTra = ''
      }
    },
    onChange () {
      this.HienThiDanhSach()
    },
    chkTimKiem_CheckedChanged () {
      if (this.chkTimKiem.checked === true) {
        if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
          if (this.chkTimKiem.checked) {
            this.cboLoaiDVTK.disable = false
            this.cboDViTimKiem.disable = false
            if (this.cboLoaiDVTK.list.length == 0) {
              this.HT_LOAIDV_TIMKIEM()
            }
            if (this.cboDViTimKiem.data.length === 0 && this.cboDViTimKiem.selectDViTimKiem === null) {
              return
            }
            this.HienThiDanhSachHDTB_TK_LOAIDV(Number.parseInt(this.cboLoaiDVTK.value), this.cboDViTimKiem.selectDViTimKiem)
          } else {
            this.cboLoaiDVTK.disable = true
            this.cboDViTimKiem.disable = true
            this.HienThiDanhSach()
          }
        }
      } else {
        this.cboLoaiDVTK.disable = true
        this.cboDViTimKiem.disable = true
        this.HienThiDanhSach()
      }
    },
    async HienThiDanhSachHDTB_TK_LOAIDV (loaidvid, vdonvitkid) {
      try {
        this.loading(true)
        // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        let loaiid = 0
        if (this.frmDanhSachTBTDDV.loaiPhieu === 'rdoPhieuMoi') {
          loaiid = 0
        } else {
          loaiid = 1
        }
        this.gview_DanhSach.data = []
        this.gview_DanhSach.data = await this.LAY_HDTB_THAYDOI_DV_HC_THEO_DV(this.nhanvienId, '0', 0,
          this.tthd_id, this.huonggiao_id, this.dichvuvt_id, loaiid, loaidvid, vdonvitkid)
        if (this.gview_DanhSach.data.length > 0) {
          this.setActiveButton('tsbtnTraPhieu', true)
        } else {
          this.clear_data()
          this.gridVLyDoHuyHDTB.data = []
          this.dgvNhanVien.data = []
          this.setActiveButton('tsbtnHoanCong', false)
          this.setActiveButton('tsbtnKichHoat', false)
          this.setActiveButton('tsbtnGiaoPhieu', false)
          this.setActiveButton('tsbtnHoanThanh', false)
          this.setActiveButton('tsbtnTraPhieu', false)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error)
      } finally {
        this.loading(false)
      }
    },
    async HT_LOAIDV_TIMKIEM () {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_HT_LOAIDV_TIMKIEM(this.axios))
      if (data.length > 0) {
        this.cboLoaiDVTK.list = data.map(item => {
          return {
            id: item.loaidv_id,
            text: item.tendv
          }
        })
        this.cboLoaiDVTK.value = this.cboLoaiDVTK.list[0].id
      }
    },
    async getTag() {
      try {
        if (
          this.$route.query.tag != null &&
          this.$route.query.tag !== '' &&
          this.$route.query.tag.length > 0
        ) {
          let tag = decodeURIComponent(this.$route.query.tag.toString())
          this.get_tag_value(tag)
        } else if (this.Tag) {
          this.get_tag_value(this.Tag)
        } else {
          this.tthd_id = TrangThaiHD.DANG_THI_CONG
          this.dsloaihd_id = '1'
          this.dsdichvuvt_id = '1'
        }
        this.DS_CHUQUAN_THEO_QUYEN = await this.$root.token.getDsQuyenChuquan()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Tag truyền vào không hợp lệ')
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
        this.dsloaihd_id = '1'
        this.dsdichvuvt_id = '9'
      }
      /* console.log('tthd_id ', this.tthd_id)
      console.log('dsloaihd_id ', this.dsloaihd_id)
      console.log('dsdichvuvt_id ', this.dsdichvuvt_id) */
    },
    get_tag_value(tag) {
      let words = tag.split('+')
      if (words.length >= 1) {
        this.tthd_id = Number.parseInt(words[0])
      }
      if (words.length >= 2) {
        this.dsloaihd_id = words[1].replaceAll(';', ',')
      }
      if (words.length >= 3) {
        this.dsdichvuvt_id = words[2].replaceAll(';', ',')
      }
      if (words.length >= 4) {
        if (words[3] !== '') {
          this.cty_newline = true
        }
      }
      console.log('1259 - cty_newline')
      console.log(this.cty_newline)
    },
    async HienThiDanhSach () {
      try {
        this.loading(true)
        let vLoaiId = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
        if (this.frmDanhSachTBTDDV.loaiPhieu === 'rdoPhieuMoi') {
          vLoaiId = 0
        } else {
          vLoaiId = 1
        }
        let nhanvienId = Number.parseInt(this.$root.token.getNhanVienID())
        // let dsIn = await this.LAY_HDTB_THAYDOI_DV_HC(nhanvienId, '0', 0, this.tthd_id, this.huonggiao_id, this.dichvuvt_id, vLoaiId)
        let dsIn = []
        if (this.DS_CHUQUAN_THEO_QUYEN == null || this.DS_CHUQUAN_THEO_QUYEN.length == 0) {
          dsIn = (await ChangeServiceOMCVIPCMAPI.LAY_HDTB_THAYDOI_DV_HC(this.axios,{
            'nhanvien_id': nhanvienId,
            'ma_gd': '0',
            'hdtb_id': 0,
            'tthd_id': this.tthd_id,
            'huonggiao_id': this.huonggiao_id,
            'dichvuvt_id': this.dichvuvt_id,
            'loai_id': vLoaiId
          })).data.data
        } else {
          dsIn = (await ChangeServiceOMCVIPCMAPI.lay_hdtb_thaydoi_dv_hc_chuquan(this.axios, {
              "vphanvung_id": null,
              "vnhanvien_id": nhanvienId,
              "vma_gd": "0",
              "vhdtb_id": 0,
              "vtthd_id": this.tthd_id,
              "vhuonggiao_id": this.huonggiao_id,
              "vdichvuvt_id": this.dichvuvt_id,
              "vloai_id": vLoaiId,
              "vds_chuquan_theo_quyen": this.DS_CHUQUAN_THEO_QUYEN
          })).data.data
        }
        if (dsIn.length > 0) {
          this.gview_DanhSach.data = dsIn
          this.setActiveButton('tsbtnTraPhieu', true)
        } else {
          this.gview_DanhSach.data = []
          this.gview_DanhSach.selectItem = null
          this.clear_data()
          this.gridVLyDoHuyHDTB.data = []
          this.dgvNhanVien.data = []
          this.setActiveButton('tsbtnHoanCong', false)
          this.setActiveButton('tsbtnKichHoat', false)
          this.setActiveButton('tsbtnGiaoPhieu', false)
          this.setActiveButton('tsbtnHoanThanh', false)
          this.setActiveButton('tsbtnTraPhieu', false)
          this.cboNguoiGV.value = Number.parseInt(this.$root.token.getNhanVienID())
          this.loading(false)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi hiển thị danh sách ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async SP_LAY_DS_QUYTRINH_V2 (dsloaihdId, dichvuvtid, tthdid, kieu) {
      let request = {
        'dsloaihd_id': dsloaihdId,
        'dichvuvt_id': dichvuvtid,
        'tthd_id': tthdid,
        'kieu': kieu
      }

      this.cboQuyTrinh.list = []
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_DS_QUYTRINH_V2(this.axios, request))
      if (data.length > 0) {
        this.cboQuyTrinh.list = data.map(item => {
          return {
            id: item.quytrinh_id,
            text: item.quytrinh
          }
        })
        if (this.vquytrinh_id !== 0) {
          this.cboQuyTrinh.value = this.vquytrinh_id
        } else {
          this.cboQuyTrinh.value = this.cboQuyTrinh.list[0].id
        }
      } else {
        this.huonggiao_id = -1
        await this.HienThiDanhSach()
        this.cboQuyTrinh.list.push({
          id: 0,
          text: '--Không có quy trình--'
        })
      }
    },
    PHAILAM (code) {
      let filter = this.dtThaotac.filter(item => (item.enable == 1 && item.code == code))
      return filter.length > 0
    },
    async DS_HUONGGIAO (tthdId, quytrinhId) {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.DS_HUONGGIAO(this.axios, tthdId, quytrinhId))
      if (data.length > 0) {
        this.luong_id = data[0].luong_id
        this.huonggiao_id = data[0].huonggiao_id
        let tenhg = data[0].huonggiao
        this.header.title = tenhg.toUpperCase()
      } else {
        this.$root.$toast.warning('Không tìm thấy luồng quy trình nghiệp vụ này. Bạn hãy liên hệ Admin kiểm tra lại')
        this.huonggiao_id = -1
        // clear item chọn ở lưới để sang quy trình khác sẽ select bản ghi đầu tiên
        this.gview_DanhSach.selectItem = null
        await this.HienThiDanhSach()
        return
      }
      this.chkTimKiem.checked = false
      this.cboLoaiDVTK.disable = true
      this.cboDViTimKiem.disable = true

      this.HienThiGiaoDien(this.luong_id)

      if (this.load) {
        // clear item chọn ở lưới để sang quy trình khác sẽ select bản ghi đầu tiên
        this.gview_DanhSach.selectItem = null
        await this.HienThiDanhSach()
      }

      if (!this.PHAILAM('GIAOVIEC')) {
        this.chkNgayBG.checked = true
        this.chkNgayGV.checked = true
        let now = new Date()
        now.setSeconds(now.getSeconds() + 30)
        this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(now, null)
        this.frmThongTinPhieuYc.dtpNgayGV = this.formatDate(new Date(), null)
      }
      // Nhóm làm việc với phần xác minh thuê bao trước khia tạm dừng
      if (this.PHAILAM('XACMINH_TAMDUNG')) {
        // Combo nhóm KQ
        await this.HienThiCBO_NHOM_KQ()
      } else {
        this.setVisibleButton('tsbtnThoaiTra', false)
        this.HienThiGiaoDien(this.luong_id)
        if (this.gview_DanhSach.selectItem !== null) {
          this.AnHienNut(this.gview_DanhSach.selectItem)

        }
      }
    },
    async HienThiCBO_NHOM_KQ () {
      let dsNhomKQXL = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_NHOM_KQXL_THEO_HUONGGIAO(this.axios, {
        'phanvung_id': Number.parseInt(this.$root.token.getPhanVungID()),
        'vhuonggiao_id': this.huonggiao_id.toString()
      }))
      if (dsNhomKQXL.length > 0) {
        this.cboNhomKQ.list = dsNhomKQXL.map(item => {
          return {
            id: item.nhomkq_id,
            text: item.nhom_kq
          }
        })
      }
    },
    AnHienNut (row) {
      // Nếu phải kích hoạt tổng đài
      if (this.PHAILAM('KICHHOAT_TD')) {
        if (row.ngay_ht_gp) {
          this.disableBox.dtpNgayBG = false
          // this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(this.getDateNew(row.ngay_ht_gp, 'DD/MM/YYYY HH:mm:ss', null).toDate(), null)
          this.frmThongTinPhieuYc.dtpNgayBG = row.ngay_ht_gp
          this.chkNgayBG.checked = true
          let trangthaiDB = 0
          if (this.dichvuvt_id == DichVuVienThong.CO_DINH) {
            trangthaiDB = TRANGTHAI_DONGBO.DONGBO_SERVICE
          } else {
            trangthaiDB = TRANGTHAI_DONGBO.DONGBO_CM
          }
          if (this.status == trangthaiDB) {
            if (this.PHAILAM('GIAOPHIEU')) {
              this.setActiveButton('tsbtnGiaoPhieu', true)
              this.setActiveButton('tsbtnKichHoat', false)
            } else {
              this.setActiveButton('tsbtnHoanCong', true)
              this.setActiveButton('tsbtnKichHoat', false)
            }

            if (this.PHAILAM('HOANTHIENHOSO')) {
              this.setActiveButton('tsbtnHoanThienHS', true)
            } else {
              this.setActiveButton('tsbtnHoanThienHS', false)
            }
          } else {
            this.setActiveButton('tsbtnGiaoPhieu', false)
            this.setActiveButton('tsbtnKichHoat', true)
            this.setActiveButton('tsbtnHoanCong', false)
            this.setActiveButton('tsbtnHoanThienHS', false)
          }
        } else {
          this.chkNgayBG.checked = false
          this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(new Date(), null)
          this.setActiveButton('tsbtnGiaoPhieu', false)
          this.setActiveButton('tsbtnHoanCong', false)
          this.setActiveButton('tsbtnHoanThienHS', false)
          this.setActiveButton('tsbtnKichHoat', false)
        }
      } else { // Nếu không phải kích hoạt
        // Nếu phải giao phiếu
        if (this.PHAILAM('GIAOPHIEU')) {
          if (row.ngay_ht_gp) {
            this.disableBox.dtpNgayBG = false
            this.frmThongTinPhieuYc.dtpNgayBG = row.ngay_ht_gp
            this.chkNgayBG.checked = true
            this.setActiveButton('tsbtnGiaoPhieu', true)
            this.setActiveButton('tsbtnHoanCong', false)
            this.setActiveButton('tsbtnHoanThienHS', false)
            this.setActiveButton('tsbtnKichHoat', false)
          } else {
            this.chkNgayBG.checked = false
            this.disableBox.dtpNgayBG = true
            this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(new Date(), null)
            this.setActiveButton('tsbtnGiaoPhieu', false)
            this.setActiveButton('tsbtnHoanCong', false)
            this.setActiveButton('tsbtnHoanThienHS', false)
            this.setActiveButton('tsbtnKichHoat', false)
          }
        } else { // Nếu không phải giao phiếu
          // Nếu phải kích hoạt tổng đài
          if (this.PHAILAM('HOANCONG')) {
            if (row.ngay_ht_gp) {
              this.disableBox.dtpNgayBG = false
              this.frmThongTinPhieuYc.dtpNgayBG = row.ngay_ht_gp
              this.chkNgayBG.checked = true
              this.setActiveButton('tsbtnHoanCong', true)
            } else {
              this.chkNgayBG.checked = false
              this.disableBox.dtpNgayBG = true
              this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(new Date(), null)
              this.setActiveButton('tsbtnHoanCong', false)
            }
          }

          if (this.PHAILAM('HOANTHIENHOSO')) {
            if (row.ngay_ht_gp) {
              this.disableBox.dtpNgayBG = true
              this.frmThongTinPhieuYc.dtpNgayBG = row.ngay_ht_gp
              this.chkNgayBG.checked = true
              this.setActiveButton('tsbtnHoanThienHS', true)
            } else {
              this.chkNgayBG.checked = false
              this.disableBox.dtpNgayBG = true
              this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(new Date(), null)
              this.setActiveButton('tsbtnHoanThienHS', false)
            }
          }
        }
      }
    },
    HienThiGiaoDien (luongId) {
      this.setVisibleButton('tsbtnKQ_DoKiem', false)
      this.setVisibleButton('tsbtnDuAn', false)
      this.setVisibleButton('tsbtnHoanCong', false)
      this.setVisibleButton('tsbtnHoanThienHS', true)
      this.setVisibleButton('tsbtnGiaoPhieu', false)
      this.setVisibleButton('tsbtnHoanThanh', false)
      this.setVisibleButton('tsbtnGiaoViec', false)
      this.setVisibleButton('tsbtnKichHoat', false)
      this.setActiveButton('tsbtnKichHoat', false)
      this.setVisibleButton('tsbtnCreatIssue', false)
      this.SP_LAY_LUONG_THAOTAC(luongId)
    },
    async SP_LAY_LUONG_THAOTAC (luongId) {
      let dsThaoTac = this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_LUONG_THAOTAC(this.axios, {
        luong_id: luongId
      }))
      this.dtThaotac = dsThaoTac
      if (dsThaoTac.length > 0) {
        for (let i = 0; i < dsThaoTac.length; i++) {
          let thaotacId = dsThaoTac[i].thaotac_id
          let enable = dsThaoTac[i].enable
          let dsControl = await this.SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID(thaotacId)
          if (dsControl.length > 0) {
            let txtKeys = Object.keys(this.frmThongTinPhieuYc).filter(item => item.includes('txt'))
            txtKeys.concat(Object.keys(this.frmThongTinChung))
            let cboKeys = Object.keys(this.$data).filter(item => item.includes('cbo'))
            let btnKeys = this.actions.map(item => {
              return item.id
            })
            let moreBtnKey = this.actionsDropdown.map(item => {
              return item.id
            })
            btnKeys = btnKeys.concat(moreBtnKey)
            let controls = txtKeys.concat(cboKeys)
            for (let j = 0; j < dsControl.length; j++) {
              let controlName = dsControl[j].control_name
              let ctrl = controls.filter(item => item.toLocaleLowerCase() == controlName.toLocaleLowerCase())
              if (ctrl.length > 0) {
                if (controlName.toLowerCase().includes('txt')) {
                  this.disableBox[controlName] = enable !== 1
                } else if (controlName.toLowerCase().includes('cbo')) {
                  this.$data[controlName].disable = enable !== 1
                } else if (controlName.toLowerCase().includes('tsbtn')) {
                  if (enable === 1) {
                    this.setActiveButton(controlName, true)
                  } else {
                    this.setActiveButton(controlName, false)
                  }
                }
              } else {
                // ToolStripButton
                let toolStrip = btnKeys.filter(item => item.toLocaleLowerCase() == controlName.toLocaleLowerCase())
                if (toolStrip.length > 0) {
                  if (enable === 1) {
                    this.setVisibleButton(controlName, true)
                  } else {
                    this.setVisibleButton(controlName, false)
                  }
                }
              }
            }
          }
        }
      }
      // Ẩn hiện nút nếu là công ty newlife
      if (this.cty_newline) {
        this.setVisibleButton('tsbtnGiaoViec', false)
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
        this.setVisibleButton('tsbtnKichHoat', true)
        this.setActiveButton('tsbtnKichHoat', true)
        this.setVisibleButton('tsbtnGiaoPhieu_Ton', false)
        this.setVisibleButton('tsbtnInBB', false)
      }
    },
    XoaLyDoHuy (item) {
      if (item != null) {
        console.log(item)
        try {
          let kieu = item.kieu
          if (kieu === 1) {
            this.$root.$toast.warning('Bạn không được phép xóa lý do hủy của TTKD nhập.')
            return
          }
          this.$confirm('Bạn chắc chắn muốn xóa lý do hủy ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            let result = await this.DELETE_LYDOHUY_HDTB(this.hdtb_id, item.lydohuy_id)
            if (result) {
              await this.HienThiDS_LyDoHuy(this.hdtb_id)
            }
          })
        } catch (error) {
          this.$root.$toast.error('Có lỗi khi xóa lý do hủy ', error.message)
        }
      }
    },
    async DELETE_LYDOHUY_HDTB (hdtbid, lydohuyid) {
      let success = false
      let request = {
        'hdtb_id': hdtbid,
        'lydohuy_id': lydohuyid
      }
      await ChangeServiceOMCVIPCMAPI.DELETE_LYDOHUY_HDTB(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          success = true
          this.$root.$toast.success('Xóa lý do hủy thành công')
        } else {
          this.$root.$toast.error('Không xóa được lý do hủy')
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error(error.data.message)
      })
      return success
    },
    async OnMaGDEnter () {
      let loaiId = 0
      if (this.frmDanhSachTBTDDV.loaiPhieu === 'rdoPhieuMoi') {
        loaiId = 0
      } else {
        loaiId = 1
      }
      this.gview_DanhSach.data = await this.LAY_HDTB_THAYDOI_DV_HC(this.nhanvienId, this.frmThongTinPhieuYc.txtMaGD.trim(),
        0, this.tthd_id, this.luong_id, this.dichvuvt_id, loaiId)
    },
    async OnMaTBEnter () {
      let loaiId = 0
      if (this.frmDanhSachTBTDDV.loaiPhieu === 'rdoPhieuMoi') {
        loaiId = 0
      } else {
        loaiId = 1
      }
      this.gview_DanhSach.data = await this.LAY_HDTB_THAYDOI_DV_HC(this.nhanvienId, this.frmThongTinPhieuYc.txtMaGD.trim(),
        0, this.tthd_id, this.luong_id, this.dichvuvt_id, loaiId)
    },
    async SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID (thaotacId) {
      return this.GetDataList(await ChangeServiceOMCVIPCMAPI.SP_LAY_DULIEU_THAOTAC_CONTROL_THEO_THAOTAC_ID(this.axios, {
        'thaotac_id': thaotacId
      }))
    },
    async GET_DICHVU_VT () {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.GET_DICHVU_VT(this.axios))
      if (data.length > 0) {
        if (this.dsdichvuvt_id !== '') {
          let dsdv = this.dsdichvuvt_id.split(',')
          data.forEach(item => {
            if (dsdv.includes(item.DICHVUVT_ID.toString())) {
              this.cboDichVuVT.list.push({
                id: item.DICHVUVT_ID,
                text: item.TEN_DVVT
              })
            }
          })
        } else {
          this.cboDichVuVT.list = data.map(item => {
            return {
              id: item.DICHVUVT_ID,
              text: item.TEN_DVVT
            }
          })
        }
        this.cboDichVuVT.list = this.cboDichVuVT.list.sort((a, b) => parseInt(a.id) - parseInt(b.id))
        if (this.cboDichVuVT.list.length > 0) {
          this.cboDichVuVT.value = this.cboDichVuVT.list[0].id
        }
      }
    },
    async LAY_DS_THAMSO_MD () {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_THAMSO_MD(this.axios))
      if (data.length > 0) {
        let vTemp = 'PHAINHAP_GHICHU_TTVT_XN_NO'
        let det = data.filter(item => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.toString().trim() === '1') {
            this.PHAINHAP_GHICHU_TTVT_XN_NO = 1
          }
        }
        vTemp = 'DONGBO_GOI_DA_DV'
        det = data.filter(item => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.toString().trim() === '1') {
            this.dongbo_dadv = 1
          }
        }
        vTemp = 'THAYDOIDV_NHIEUPHIEU'
        det = data.filter(item => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.toString().trim() === '1') {
            this.THAYDOIDV_NHIEUPHIEU = 1
          }
        }
        vTemp = 'LHD_NHANTIN_DIABAN'
        det = data.filter(item => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.toString().trim() == '1') {
            this.nhantin_nhanvien_diaban = 1
          }
        }
      }
    },
    async LAY_DS_NV_GIAO_NHAN () {
      let donviId = this.$root.token.getDonViID()
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_NV_GIAO_NHAN(this.axios))
      if (data.length > 0) {
        let dsDonvi = data.filter(item => item.donvi_id.trim() === donviId.toString().trim())
        if (donviId.length > 0) {
          this.cboNguoiGV.list = dsDonvi.map(value => {
            return {
              id: value.nhanvien_id,
              text: value.ten_nv
            }
          })
          this.cboNguoiGV.value = this.$root.token.getNhanVienID().toString()
        }
      }
    },
    async LAY_DS_NHANVIEN_THEO_DONVI () {
      let donviId = this.$root.token.getDonViID()
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_NHANVIEN_THEO_DONVI(this.axios, {
        donvi_id: donviId
      }))
      if (data.length > 0) {
        this.cboNguoiGV.list = data.map(value => {
          return {
            id: value.nhanvien_id,
            text: value.ten_nv
          }
        })
        this.cboNguoiGV.value = this.$root.token.getNhanVienID().toString()
      } else {
        this.cboNguoiGV.list = []
      }
    },
    async LAY_HDTB_THAYDOI_DV_HC (nhanvienId, maGD, hdtbId, tthdId, huonggiaoId, dichvuVTId, loaiId) {
      const request = {
        'nhanvien_id': nhanvienId,
        'ma_gd': maGD,
        'hdtb_id': hdtbId,
        'tthd_id': tthdId,
        'huonggiao_id': huonggiaoId,
        'dichvuvt_id': dichvuVTId,
        'loai_id': loaiId
      }
      return this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_HDTB_THAYDOI_DV_HC(this.axios, request))
    },
    async LAY_HDTB_THAYDOI_DV_HC_THEO_DV (nhanvienid, magd, hdtbid, tthdid, huonggiaoid, dichvuvtid, loaiid, loaidvid, donvitkid) {
      const request = {
        'nhanvien_id': nhanvienid,
        'ma_gd': magd,
        'hdtb_id': hdtbid,
        'tthd_id': tthdid,
        'huonggiao_id': huonggiaoid,
        'dichvuvt_id': dichvuvtid,
        'loai_id': loaiid,
        'loaidv_id': loaidvid,
        'donvi_tk_id': donvitkid
      }
      return this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_HDTB_THAYDOI_DV_HC_THEO_DV(this.axios, request))
    },
    GetDataList: function (response) {
      console.log(response)
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async tsbtnChapNhan_Click () {
      if (this.THAYDOIDV_NHIEUPHIEU == 1) {
        if (this.gview_DanhSach.checked.length <= 0) {
           console.log('Cap_Nhat')
          await this.Cap_Nhat()
        } else {
          console.log('Cap_Nhat_v2')
          await this.Cap_Nhat_v2()
        }
      } else {
         console.log('Cap_Nhat')
        await this.Cap_Nhat()
      }
    },
    chkNgayBG_CheckedChanged () {
      this.disableBox.dtpNgayBG = this.chkNgayBG.checked !== true
    },
    chkNgayGV_CheckedChanged () {
      this.disableBox.dtpNgayGV = this.chkNgayGV.checked !== true
    },
    async Cap_Nhat () {
      try {
        let nhanviengiaoid = Number.parseInt(this.cboNguoiGV.value)
        let ngaygiao = this.frmThongTinPhieuYc.dtpNgayBG
        if (!this.KT_DuLieuNhap()) {
          return
        }
        if (!this.PHAILAM('GIAOVIEC')) {
          // Cập nhật thông tin giao việc nhân viên tự động
          await this.XOA_PHIEU(this.phieu_id)
          this.TaoDuLieu_NVTH()
          await this.INSERT_GIAOPHIEU_NV()
        }

        if (this.tthd_id === TrangThaiHD.DA_THI_CONG_XONG || this.tthd_id === TrangThaiHD.THANH_TOAN) {
          if (this.dgvNhanVien.data.length <= 0) {
            await this.XOA_PHIEU(this.phieu_id)
            this.TaoDuLieu_NVTH()
            await this.INSERT_GIAOPHIEU_NV()
          }
        }

        if (this.PHAINHAP_GHICHU_TTVT_XN_NO == 1 && this.frmThongTinChung.txtNoiDungTH == '' && this.tthd_id == TrangThaiHD.DANG_THI_CONG) {
          if (this.PHAILAM('PHAINHAP_GHICHU_TTVT_XN_NO')) {
            this.$root.$toast.warning('Hãy nhập nội dung thực hiện!')
            this.$refs.txtNoiDungTH.focus()
            return
          }
        }

        let NoiDungTH = this.frmThongTinChung.txtNoiDungTH ? this.frmThongTinChung.txtNoiDungTH : ''

        if (this.PHAILAM('XACMINH_TAMDUNG')) {
          await this.CAPNHAT_KETQUAXL_PHIEU_ID(this.phieu_id, this.DmKetQuaXL.selectKQXL === null ? 0 : this.DmKetQuaXL.selectKQXL)
        }
        let result = await this.UPDATE_ND_THUCHIEN(this.phieu_id, NoiDungTH)
        if (result) {
          await this.SP_UPDATE_TT_GIAOVIEC(this.nhanvienId, ngaygiao, this.hdtb_id, this.phieu_id, nhanviengiaoid)

        } else {
          this.$v.$touch()
          this.$refs.txtNoiDungTH.focus()
        }
        this.$root.$toast.success('Cập nhật dữ liệu thành công!')
        await this.HienThiDanhSach()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi cập nhật dữ liệu ', error)
      }
    },
    async Cap_Nhat_v2 () {
      try {
        if (this.gview_DanhSach.data.length <= 0) {
          this.$root.$toast.warning('Không có dữ liệu để cập nhật!')
          return
        }
        let nhanviengiaoid = Number.parseInt(this.cboNguoiGV.value)
        let ngaygiao = this.frmThongTinPhieuYc.dtpNgayBG
        for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
          let item = this.gview_DanhSach.checked[i]
          let phieuid = Number.parseInt(item.phieu_id)
          let hdtbid = Number.parseInt(item.hdtb_id)
          let ktdl = await this.KT_DuLieuNhap_v2(item)
          if (!ktdl) {
            return
          }
          if (!this.PHAILAM('GIAOVIEC')) {
            // Cập nhật thông tin giao việc nhân viên tự động
            await this.XOA_PHIEU(phieuid)
            this.TaoDuLieu_NVTH_v2(phieuid)
            await this.INSERT_GIAOPHIEU_NV()
          }
          if (this.tthd_id == TrangThaiHD.DA_THI_CONG_XONG || this.tthd_id == TrangThaiHD.THANH_TOAN) {
            if (this.dgvNhanVien.data.length <= 0) {
              await this.XOA_PHIEU(phieuid)
              this.TaoDuLieu_NVTH_v2(phieuid)
              await this.INSERT_GIAOPHIEU_NV()
            }
          }

          if (this.PHAINHAP_GHICHU_TTVT_XN_NO == 1 && this.frmThongTinChung.txtNoiDungTH == '' && this.tthd_id == TrangThaiHD.DANG_THI_CONG) {
            if (this.PHAILAM('PHAINHAP_GHICHU_TTVT_XN_NO')) {
              this.$root.$toast.warning('Hãy nhập nội dung thực hiện!')
              this.$refs.txtNoiDungTH.focus()
            }
          }

          let NoiDungTH = this.frmThongTinChung.txtNoiDungTH ? this.frmThongTinChung.txtNoiDungTH : ''

          if (this.PHAILAM('XACMINH_TAMDUNG')) {
            await this.CAPNHAT_KETQUAXL_PHIEU_ID(this.phieu_id, this.DmKetQuaXL.selectKQXL === null ? 0 : this.DmKetQuaXL.selectKQXL)
          }
          let result = await this.UPDATE_ND_THUCHIEN(this.phieu_id, NoiDungTH)
          if (result) {
            await this.SP_UPDATE_TT_GIAOVIEC(this.nhanvienId, ngaygiao, hdtbid, phieuid, nhanviengiaoid)
          } else {
            this.$refs.txtNoiDungTH.focus()
            this.$v.$touch()
          }
        }

        this.$root.$toast.success('Cập nhật dữ liệu thành công!')
        await this.HienThiDanhSach()
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ', error)
      }
    },
    TaoDuLieu_NVTH_v2 (phieuid) {
      this.dsGiaoPhieuNV = []
      let row = {
        'phieu_id': phieuid.toString(),
        'nhanvien_giao_id': this.$root.token.getNhanVienID().toString(),
        'ngaygiao': this.frmThongTinPhieuYc.dtpNgayGV,
        'nhanvien_th_id': this.$root.token.getNhanVienID().toString(),
        'nhiemvu': 'Hoàn thiện hồ sơ',
        'ghichu': '',
        'so_dt': '',
        'status': '',
        'nhiemvu_id': '',
        'kqxl_id': ''
      }
      this.dsGiaoPhieuNV.push(row)
    },
    selectRowsChon (args) {
      if (this.gview_DanhSach.selectItem != null) {
        let index = this.gview_DanhSach.data.findIndex(item => item.hdtb_id == this.gview_DanhSach.selectItem.hdtb_id)
        if (index >= 0) {
          this.$refs.gview_DanhSach.selectRow(index, true)
        }
      } else {
        this.$refs.gview_DanhSach.selectRow(0, true)
      }
    },
    async KT_DuLieuNhap_v2 (row) {
      let phieuid = row.phieu_id
      if (this.PHAILAM('GIAOVIEC') && !this.cty_newline) {
        let ds = await this.LAY_DS_NHANVIEN_THEO_PHIEU_ID(phieuid, 2)
        if (ds.length < 0) {
          this.$root.$toast.warning('Hãy nhập nhân viên thực hiện!')
          return false
        }
      }
      if (this.chkNgayGV.checked == false) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc !')
        this.$refs.chkNgayGV.focus()
        return false
      }
      if (this.chkNgayBG.checked == false) {
        this.$root.$toast.warning('Hãy chọn ngày thực hiện !')
        this.$refs.chkNgayBG.focus()
        return false
      }
      let ngaybg = this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).toDate().setHours(0, 0, 0, 0)
      let ngaygv = this.getDate(this.frmThongTinPhieuYc.dtpNgayGV).toDate().setHours(0, 0, 0, 0)
      if (ngaygv > ngaybg) {
        this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành')
        return false
      }
      let ngaySys = row.thangnam_sys
      let ngayTt = row.thangnam_tt
      let ngayHt = moment(ngaybg).format('DD/MM/YYYY')
      let dichvuId = Number.parseInt(row.dichvuvt_id)
      let loaihinhtbId = Number.parseInt(row.loaitb_id)
      if (loaihinhtbId == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC ||
        loaihinhtbId == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
        dichvuId == DichVuVienThong.MEGAWAN ||
        dichvuId == DichVuVienThong.TSL ||
        dichvuId == DichVuVienThong.METRONET ||
        dichvuId == DichVuVienThong.NOIBO_2DIEM ||
        dichvuId == DichVuVienThong.NOIBO_DADIEM ||
        dichvuId == DichVuVienThong.DICHVU_CNTT ||
        dichvuId == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        dichvuId == DichVuVienThong.TRUNGTAM_DULIEU ||
        dichvuId == DichVuVienThong.HOINGHI_TRUYENHINH) {
        if (Number.parseInt(this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).format('YYYYMM')) < Number.parseInt(moment(new Date()).format('YYYYMM'))) {
          this.$root.$toast.warning('Tháng hoàn thành không được nhỏ hơn tháng hiện tại !')
          return false
        }
        if (!this.KiemTraDK_HoanThanh(ngayHt, ngaySys)) {
          this.$root.$toast.warning('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
          return false
        }
      } else {
        if (!this.KiemTraDK_HoanThanh(ngayTt, ngayHt)) {
          this.$root.$toast.warning(' Thuê bao ' + this.gview_DanhSach.selectItem.ma_tb.toString().trim() + ' có ngày thanh toán : ' + ngayTt + ' \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán')
          return false
        }
        if (!this.KiemTraDK_HoanThanh(ngayHt, ngaySys)) {
          this.$root.$toast.warning('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
          return false
        }
      }
      return true
    },
    async LAY_DS_NHANVIEN_THEO_PHIEU_ID (phieuid, kieuid) {
      return this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_NHANVIEN_THEO_PHIEU_ID(this.axios, {
        'phieu_id': phieuid,
        'kieu_id': kieuid
      }))
    },
    async CAPNHAT_KETQUAXL_PHIEU_ID (phieuid, kqXuLyId) {
      await ChangeServiceOMCVIPCMAPI.CAPNHAT_KETQUAXL_PHIEU_ID(this.axios, {
        'phieu_id': phieuid,
        'kqxl_id': kqXuLyId,
        'may_cn': this.maycn,
        'ip_cn': this.ipcn
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
        } else {
          this.$root.$toast.error('Lỗi cập nhật kết quả xử lý phiếu: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Lỗi thực hiện cập nhật kết quả xử lý phiếu: ' + error.data.message_detail)
      })
    },
    TaoDuLieu_NVTH () {
      this.dsGiaoPhieuNV = []
      let row = {
        'phieu_id': this.phieu_id.toString(),
        'nhanvien_giao_id': this.$root.token.getNhanVienID().toString(),
        'ngaygiao': this.frmThongTinPhieuYc.dtpNgayGV,
        'nhanvien_th_id': this.$root.token.getNhanVienID().toString(),
        'nhiemvu': 'Hoàn thiện hồ sơ',
        'ghichu': '',
        'so_dt': '',
        'status': '',
        'nhiemvu_id': '',
        'kqxl_id': ''
      }
      this.dsGiaoPhieuNV.push(row)
    },
    async SP_UPDATE_TT_GIAOVIEC (nhanvienid, ngaygiao, hdtbid, phieuid, nguoigvid) {
      let result = false
      await ChangeServiceOMCVIPCMAPI.SP_UPDATE_TT_GIAOVIEC(this.axios, {
        'hdtb_id': hdtbid,
        'ngay_ht': ngaygiao,
        'nhanvien_gv_id': nguoigvid,
        'nhanvien_hc_id': nhanvienid,
        'phieu_id': phieuid
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
        } else {
          result = false
          this.$root.$toast.error('Cập nhật thông tin giao việc thất bại: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi cập nhật thông tin giao việc: ' + error.data.message_detail)
      })
      return result
    },
    async UPDATE_ND_THUCHIEN (phieuid, noidungth) {
      let result = false
      await ChangeServiceOMCVIPCMAPI.UPDATE_ND_THUCHIEN(this.axios, {
        'nd_thuchien': noidungth,
        'phieu_id': phieuid
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
        } else {
          result = false
          let message = response.data.message_detail
          if (message.includes('maximum')) {
            let str = message.substring(message.indexOf('(') + 1, message.lastIndexOf(')'))
            let strMax = str.split(',')[1]
            let max = strMax.split(':')[1]
            this.$root.$toast.error('Trường "Nội dung thực hiện" vượt quá độ dài cho phép là ' + max + ' ký tự')
          } else {
            this.$root.$toast.error('Cập nhật nội dung thực hiện thất bại: ' + response.data.message_detail)
          }
        }
      }).catch(error => {
        result = false
        this.$root.$toast.error('Có lỗi khi cập nhật nội dung thực hiện: ' + error.data.message)
      })
      return result
    },
    async XOA_PHIEU (phieuId) {
      await ChangeServiceOMCVIPCMAPI.XOA_PHIEU(this.axios, {
        phieu_id: phieuId
      }).then(response => {
        console.log(response)
      })
    },
    async INSERT_GIAOPHIEU_NV () {
      await ChangeServiceOMCVIPCMAPI.INSERT_GIAOPHIEU_NV(this.axios, {
        json_giaophieu_nv: JSON.stringify(this.dsGiaoPhieuNV)
      })
    },
    KT_DuLieuNhap () {
      if (this.gview_DanhSach.data.length <= 0) {
        this.$root.$toast.warning('Không có dữ liệu để cập nhật!')
        return false
      }
      if (this.PHAILAM('GIAOVIEC') && !this.cty_newline) {
        if (this.dgvNhanVien.data.length <= 0) {
          this.$root.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      if (this.chkNgayGV.checked === false) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc !')
        this.$refs.chkNgayGV.focus()
        return false
      }

      if (!this.frmThongTinPhieuYc.dtpNgayGV) {
        this.$root.$toast.warning('Hãy chọn ngày giao việc !')
        return false
      }

      if (this.chkNgayBG.checked === false) {
        this.$root.$toast.warning('Hãy chọn ngày thực hiện !')
        this.$refs.chkNgayBG.focus()
        return false
      }

      if (!this.frmThongTinPhieuYc.dtpNgayBG) {
        this.$root.$toast.warning('Hãy chọn ngày thực hiện !')
        return false
      }

      let ngaybg = this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).toDate().setHours(0, 0, 0, 0)
      let ngaygv = this.getDate(this.frmThongTinPhieuYc.dtpNgayGV).toDate().setHours(0, 0, 0, 0)
      if (ngaygv > ngaybg) {
        this.$root.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành')
        return false
      }
      // Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
      let ngaySys = this.gview_DanhSach.selectItem.thangnam_sys
      let ngayTt = this.gview_DanhSach.selectItem.thangnam_tt
      let ngayHt = this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).format('DD/MM/YYYY')
      let dichvuId = Number.parseInt(this.gview_DanhSach.selectItem.dichvuvt_id)
      let loaihinhtbId = Number.parseInt(this.gview_DanhSach.selectItem.loaitb_id)
      if (loaihinhtbId == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC ||
        loaihinhtbId == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
        dichvuId == DichVuVienThong.MEGAWAN ||
        dichvuId == DichVuVienThong.TSL ||
        dichvuId == DichVuVienThong.METRONET ||
        dichvuId == DichVuVienThong.NOIBO_2DIEM ||
        dichvuId == DichVuVienThong.NOIBO_DADIEM ||
        dichvuId == DichVuVienThong.DICHVU_CNTT ||
        dichvuId == DichVuVienThong.ANTOAN_BAOMAT_TT ||
        dichvuId == DichVuVienThong.TRUNGTAM_DULIEU ||
        dichvuId == DichVuVienThong.HOINGHI_TRUYENHINH) {
        if (Number.parseInt(this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).format('YYYYMM')) < Number.parseInt(moment(new Date()).format('YYYYMM'))) {
          this.$root.$toast.warning('Tháng hoàn thành không được nhỏ hơn tháng hiện tại !')
          return false
        }
        // Nếu ngày thanh toán > ngày hoàn thành
        if (!this.KiemTraDK_HoanThanh(ngayHt, ngaySys)) {
          this.$root.$toast.warning('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
          return false
        }
      } else {
        if (ngayTt!=null&&!this.KiemTraDK_HoanThanh(ngayTt, ngayHt)) {
          this.$root.$toast.warning(' Thuê bao ' + this.gview_DanhSach.selectItem.ma_tb.toString().trim() + ' có ngày thanh toán : ' + ngayTt + ' \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán')
          return false
        }
        if (!this.KiemTraDK_HoanThanh(ngayHt, ngaySys)) {
          this.$root.$toast.warning('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
          return false
        }
      }
      return true
    },
    KiemTraDK_HoanThanh (thangnamtt, thangnamsys) {
      let result = false
      // So sánh năm
      if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length - 6)) < Number.parseInt(thangnamsys.substr(6, thangnamsys.length - 6))) {
        // Nếu năm thanh toán < năm hoàn thành thì true;
        return true
      } else if (Number.parseInt(thangnamtt.substr(6, thangnamtt.length - 6)) === Number.parseInt(thangnamsys.substr(6, thangnamsys.length - 6))) {
        // Nêu năm = nhau thì kiểm tra tiếp
        // Nếu tháng tt < thang ht -> true
        if (Number.parseInt(thangnamtt.substr(3, 2)) < Number.parseInt(thangnamsys.substr(3, 2))) {
          result = true
        } else if (Number.parseInt(thangnamtt.substr(3, 2)) === Number.parseInt(thangnamsys.substr(3, 2))) {
          return Number.parseInt(thangnamtt.substr(0, 2)) <= Number.parseInt(thangnamsys.substr(0, 2))
        } else {
          result = false
        }
      } else {
        result = false
      }
      return result
    },
    async Hoan_Cong () {
      if (!this.KT_DuLieuNhap()) {
        return
      }
      if (this.dgvNhanVien.data.length <= 0 && !this.cty_newline) {
        this.$root.$toast.warning('Hãy nhập nhân viên thực hiện  !')
        return
      }

      if (!this.gview_DanhSach.selectItem.ngay_ht_gp) {
        this.$root.$toast.warning('Bạn phải cập nhật dữ liệu trước khi hoàn thành !')
        return
      }

      let request = {
        'thaydoidv_nhieuphieu': this.THAYDOIDV_NHIEUPHIEU,
        'hdtb_id': this.hdtb_id,
        'luong_id': this.luong_id,
        'loaitb_id': this.loaitb_id,
        'phieu_id': this.phieu_id,
        'thuebao_id': this.thuebao_id,
        'huonggiao_id': this.huonggiao_id,
        'kqxl_id': Number.parseInt(this.DmKetQuaXL.selectKQXL ? this.DmKetQuaXL.selectKQXL : 0),
        'ngay_bg': this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).format('DD/MM/YYYY'),
        'dongbo_dadv': this.dongbo_dadv,
        'dvvt_id': this.dichvuvt_id,
        'tthd_id': this.tthd_id,
        'ma_tb': this.frmThongTinPhieuYc.txtMaTB.trim(),
        'ten_tb': this.frmThongTinPhieuYc.txtTenTB.trim(),
        'diachi_ld': this.frmThongTinPhieuYc.txtDiaChiLD.trim(),
        'ds_hdtb': []
      }
      await ChangeServiceOMCVIPCMAPI.HOANCONG_HTHS(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          if (response.data.message) {
            this.$root.$toast.success(response.data.message)
          } else {
            this.$root.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
          }
          this.HienThiDanhSach()
        } else {
          this.$root.$toast.error('Hoàn công không thành công: ' + response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi hoàn công: ', error.data.message)
      })
    },
    async Hoan_Cong_v2 () {
      let check = await this.KT_DuLieuNhap_v2(this.gview_DanhSach.selectItem)
      if (!check) {
        return
      }
      let ds_hdtb = this.gview_DanhSach.checked.map(item => {
        item.chon = '1'
        return item
      })
      let request = {
        'thaydoidv_nhieuphieu': this.THAYDOIDV_NHIEUPHIEU,
        'hdtb_id': this.hdtb_id,
        'luong_id': this.luong_id,
        'loaitb_id': this.loaitb_id,
        'phieu_id': this.phieu_id,
        'thuebao_id': this.thuebao_id,
        'huonggiao_id': this.huonggiao_id,
        'kqxl_id': Number.parseInt(this.DmKetQuaXL.selectKQXL),
        'ngay_bg': this.getDate(this.frmThongTinPhieuYc.dtpNgayBG).format('DD/MM/YYYY'),
        'dongbo_dadv': this.dongbo_dadv,
        'dvvt_id': this.dichvuvt_id,
        'tthd_id': this.tthd_id,
        'ma_tb': this.frmThongTinPhieuYc.txtMaTB.trim(),
        'ten_tb': this.frmThongTinPhieuYc.txtTenTB.trim(),
        'diachi_ld': this.frmThongTinPhieuYc.txtDiaChiLD.trim(),
        'ds_hdtb': ds_hdtb
      }
      await ChangeServiceOMCVIPCMAPI.HOANCONG_HTHS(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          if (response.data.message) {
            this.$root.$toast.success(response.data.message)
          } else {
            this.$root.$toast.success('Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !')
          }
          this.HienThiDanhSach()
        } else {
          this.$root.$toast.error('Hoàn công không thành công: ' + response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi thực hiện hoàn công: ' + error.data.message)
      });
    },
    async btnHoanCong_Click () {
      this.$confirm('Bạn thật sự muốn hoàn công không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        let res = await ChangeServiceOMCVIPCMAPI.fn_kiemtra_ngay_tdtl(this.axios, {vhdtb_id: this.hdtb_id})
        if (res.data.data !== "OK") {
          this.$root.$toast.warning(res.data.mesage);
          return;
        }
        if (this.THAYDOIDV_NHIEUPHIEU === 1) {
          if (this.gview_DanhSach.checked.length <= 0) {
            this.Hoan_Cong()
          } else {
            this.Hoan_Cong_v2()
          }
        } else {
          this.Hoan_Cong()
        }
      })
    },
    tsbtnXuatFile_Click () {
      if (this.gview_DanhSach.data.length > 0) {
        this.$refs.exportDataModal.showModal()
      } else {
        this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
      }
    },
    tsbtnHen_Click () {
      if (this.hdkh_id !== 0) {
        this.quyen_duoc_hen = 1
        this.$refs.ThongTinHenKhachHangModal.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu')
      }
    },
    closePopupChuyenTo () {
      this.HienThiDanhSach()
    },
    async tsbtnChuyenTo_Click () {
      try {
        if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
          let dsphieu = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_DS_PHIEUVT_HDBH(this.axios, {
            'phieu_id': this.phieu_id,
            'baohong_id': 0,
            'hdtb_id': this.hdtb_id,
            'kieutbi': 0,
            'status': 0
          }))
          if (dsphieu.length > 0) {
            this.$root.$toast.warning('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
            return
          }
        }
        console.log(this.phieu_id, this.hdtb_id)
        this.$refs.popupChuyenTo.openDialog(this.phieu_id, this.hdtb_id)
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ', error)
      }
    },
    async tsbtnTraPhieu_Click () {
      if (this.PHAILAM('KICHHOAT_TD')) {
        if (this.status === TRANGTHAI_DONGBO.DONGBO_CM || this.status === TRANGTHAI_DONGBO.DONGBO_SERVICE) {
          this.$root.$toast.warning('Thuê bao đã kích hoạt, không thể trả phiếu')
          return
        }
      }
      this.modelTraPhieu.maGd = this.frmThongTinPhieuYc.txtMaGD.trim()
      this.modelTraPhieu.loaihdId = LoaiHopDong.THAY_DOI_DV
      this.modelTraPhieu.dichvuvtId = this.cboDichVuVT.value
      this.modelTraPhieu.nhanvienId = this.nhanvienId
      this.modelTraPhieu.matb = this.frmThongTinPhieuYc.txtMaTB.trim()
      this.modelTraPhieu.ngaytra = '0'
      this.modelTraPhieu.ngaygiao = '0'
      this.modelTraPhieu.kieugoiId = 3
      this.showModalTraphieu = true
    },
    async tsbtnChuyenDB_Click () {
      try {
        if (this.hdtb_id === 0) {
          this.$root.$toast.warning('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
          return
        }
        let ktJiraHTHS = this.PHAILAM('KIEMTRA_JIRA_HTHS') === true ? 1 : 0
        let creatIssue = this.PHAILAM('CREAT_ISSUE') === true ? 1 : 0
        let rakichhoat = this.PHAILAM('RA_KICH_HOAT') === true ? 1 : 0
        let capnhatngayhthc = this.PHAILAM('CAPNHAT_NGAYHT_HC') ? 1 : 0
        let capnhatngaykhhc = this.PHAILAM('CAPNHAT_NGAYKH_HC') ? 1 : 0
        let tudongthuhoikhihoancong = this.PHAILAM('TUDONG_THUHOI_KHI_HOANCONG') ? 1 : 0

        if(this.PHAILAM('KT_HOANCONG_HD_BANCHEO')){
          let kq_bc=await this.kiemtra_hoanthien_thoaitra_ban(this.hdtb_id, 1)
          if(kq_bc!='OK'){
            this.$toast.error(kq_bc)
            return
          }
        } 

        // TODO cần tích hợp với hệ thống ivan khi nào có update lại code ở đây
        let req = {
          'hdkh_id': this.hdkh_id,
          'hdtb_id': this.hdtb_id,
          'dichvuvt_id': Number(this.dichvuvt_id),
          'tthd_id': Number(this.tthd_id),
          'kiemtra_jira_hths': ktJiraHTHS,
          'loaitb_id': Number(this.loaitb_id),
          'creat_issue': creatIssue,
          'ra_kich_hoat': rakichhoat,
          'capnhat_ngayht_hc': capnhatngayhthc,
          'ngay_ht': this.frmThongTinPhieuYc.dtpNgayBG,
          'ngay_cn': this.formatDate(new Date(), 'YYYYMM'),
          'kq': 0,
          'phieu_id': this.phieu_id,
          'donvi_id': this.donviID,
          'nhanvien_id': this.nhanvienId,
          'ma_nd': this.$root.token.getUserName(),
          'may_cn': await this.$root.token.getMachine(),
          'ip_cn': await this.$root.token.getIP(),
          'capnhat_ngaykh_hc': capnhatngaykhhc,
          'tudong_thuhoi_khi_hoancong': tudongthuhoikhihoancong,
          'kq_tamdung_bhxh': 0,
          'kq_motamdung_bhxh': 0
        }
        let request = {'ds_param': JSON.stringify(req)}
        let result = null
        await ChangeServiceOMCVIPCMAPI.FN_HTHSTHAYDOIDV_CHUYENDB(this.axios, request).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            result = response.data.data
          } else {
            this.$root.$toast.error(response.data.message_detail)
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error(error.data.message_detail)
        })
        if (result) {
          let pjsreturns = JSON.parse(result.p_js_returns)
          if (result.kq === '0') {
            this.$root.$toast.error(pjsreturns.MESSAGE)
          } else {
            this.$root.$toast.success(pjsreturns.MESSAGE)
            this.gview_DanhSach.selectItem = null
            await this.HienThiDanhSach()
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      }
    },
    async tsbtnGiaoPhieu_Click () {
      let data = this.GetDataList(await ChangeServiceOMCVIPCMAPI.LAY_HUONGGIAO_THEO_LUONG_ID(this.axios, {
        'luong_id': this.luong_id
      }))
      if (data.length > 0) {
        this.giaoPhieuViewDialog.input = {
          vhuonggiao_id: Number.parseInt(data[0].huonggiao_id),
          vloai_hd: this.dsloaihd_id,
          vdichvuvt_id: this.cboDichVuVT.value,
          vma_gd: this.frmThongTinPhieuYc.txtMaGD.trim(),
          vquytrinh: this.quytrinh_id,
          vtrangthaiphieu: 1,
        };
        await this.GiaoPhieuView_OnClick()
        await this.HienThiDanhSach()
      } else {
        this.$root.$toast.warning('Chưa có hướng giao được gán !')
      }
    },
    async tsbtnGiaoViec_Click () {
      if (this.gview_DanhSach.data.length <= 0) {
        this.$root.$toast.warning('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
        return
      }
      if (this.cboNguoiGV.list.length <= 0) {
        this.$root.$toast.warning('Hãy nhập nhân viên giao việc!')
        return
      }
      if (!this.chkNgayGV.checked) {
        this.$root.$toast.warning('Hãy nhập ngày giao việc!')
        this.$refs.chkNgayGV.focus()
        return
      }
      this.modelGiaoViec.ngaygiao = this.frmThongTinPhieuYc.dtpNgayGV
      this.modelGiaoViec.nguoigv_id = Number.parseInt(this.cboNguoiGV.value)
      this.modelGiaoViec.phieu_id = this.phieu_id
      this.modelGiaoViec.hdtb_id = this.hdtb_id
      this.modelGiaoViec.ma_tb = this.frmThongTinPhieuYc.txtMaTB.trim()
      this.$refs.formgiaphieu.init()
      this.$refs.giaoPhieuNhanVien.show()
    },
    async giaoviec_success () {
      await this.HienThiDSNV(this.phieu_id, this.nhanvienId)
    },
    tsbtnGiaoPhieu_Ton_Click () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Hãy chọn phiếu trước để nhập lý do tồn!')
        return
      }
      let arrPhieu = []
      this.$refs.popupTonPhieu.showPhieuTon(this.phieu_id, this.tthd_id, arrPhieu)
    },
    closePopupTonPhieu () {
      this.$refs.popupTonPhieu.$refs.dialogObjPhieuTon.hide()
    },
    tsbtnVatTu_Click () {
      if (this.gview_DanhSach.selectItem !== null) {
        let inphieuid = Number.parseInt(this.gview_DanhSach.selectItem.phieu_id)
        let inhdtbid = Number.parseInt(this.gview_DanhSach.selectItem.hdtb_id)
        let inthuebaoid = Number.parseInt(this.gview_DanhSach.selectItem.thuebao_id)
        this.modelVatTu.hdtb_id = inhdtbid
        this.modelVatTu.phieu_id = inphieuid
        this.modelVatTu.loaihd_id = LoaiHopDong.THAY_DOI_DV
        this.modelVatTu.thuebao_id = inthuebaoid
        this.$refs.popupVatTuThueBao.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    tsbtnHoanThanh_Click () {
      if (this.gview_DanhSach.data.length <= 0) {
        this.$root.$toast.warning('Chưa có danh sách thuê bao. Bạn hãy kiểm tra lại!')
        return
      }

      if (!this.gview_DanhSach.selectItem.ngay_ht_gp) {
        this.$root.$toast.warning('Bạn phải cập nhật dữ liệu trước khi hoàn thành !')
        return
      }

      if (!this.KT_DuLieuNhap) {
        return
      }
      this.$confirm('Bạn thật sự muốn hoàn thành không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        if (this.phieu_id === 0) {
          this.$root.$toast.warning('Chưa chọn phiếu để thực hiện!')
          return
        }
        await ChangeServiceOMCVIPCMAPI.HOANTHANH_HTHS(this.axios, {
          'hdtb_id': this.hdtb_id,
          'luong_id': this.luong_id,
          'phieu_id': this.phieu_id
        }).then(response => {
          console.log(response)
        })
      })
    },
    tsbtnDoiNe_Click () {
      this.$refs.popupDoiNeHNI.showModal()
    },
    popupDoiNeClosed () {
      this.SP_LAY_NE_MOI_THEO_HDTB_ID(this.hdtb_id)
    },
    async tsbtnHuyKH_Click () {
      try {
        if (this.cboDichVuVT.value === DichVuVienThong.CO_DINH) {
          await ChangeServiceOMCVIPCMAPI.CAPNHAT_NGAYKH_NGAY_HT_V2(this.axios, {
            'vhdtb_id': this.hdtb_id,
            'vip_kh': this.ipcn,
            'vngay_ht': this.formatDate(new Date(), null),
            'vngay_kh': this.formatDate(new Date(), null),
            'vnguoi_kh': this.$root.token.getUserName(),
            'vstatus': TRANGTHAI_DONGBO.CHUADONGBO
          }).then(response => {
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
              this.$root.$toast.success('Hủy kích hoạt thành công')
              this.HienThiDanhSach()
            }
          })
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi hủy kích hoạt: ' + error)
      }
    },
    tsbtnKQ_DoKiem_Click () {
      if (this.hdtb_id !== 0) {
        this.$refs.popupKetQuaDoKiem.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },
    btnLyDoHuy_Click () {
      this.modelThayDoiLyDoHuy.hdtb_id = this.hdtb_id
      this.modelThayDoiLyDoHuy.loaitb_id = this.loaitb_id
      this.modelThayDoiLyDoHuy.loaild_id = 2
      this.modelThayDoiLyDoHuy.vkieu = 1
      this.$refs.popupThayDoiLyDoHuy.showModal()
    },
    CapNhatLyDoHuyThanhCong (args) {
      this.HienThiDS_LyDoHuy(this.hdtb_id)
    },
    async giaophieu_success () {
      this.gview_DanhSach.selectItem = null
      await this.HienThiDanhSach()
    },
    async KICHHOAT_HTHS (request) {
      let result = false
      await ChangeServiceOMCVIPCMAPI.KICHHOAT_HTHS(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
          if (response.data.message) {
            this.$root.$toast.success(response.data.message)
          } else {
            this.$root.$toast.success('Kích hoạt tổng đài thành công !')
          }
        } else {
          this.$root.$toast.error(response.data.message)
        }
      }).catch(error => {
        this.$root.$toast.error(error.data.message)
      })
      return result
    },
    async THOAITRA_XACMINH_HDTB (hdtbId, phieuId, kqxlId, mayCn, ipCn) {
      let result = ''
      let request = {
        'hdtb_id': hdtbId,
        'phieu_id': phieuId,
        'kqxl_id': kqxlId,
        'may_cn': mayCn,
        'ip_cn': ipCn
      }
      await ChangeServiceOMCVIPCMAPI.THOAITRA_XACMINH_HDTB(this.axios, request).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = response.data.data
        } else {
          this.$root.$toast.error('Lỗi khi thực hiện thoái trả: ' + response.data.message)
        }
      }).catch(error => {
        this.$root.$toast.error('Có lỗi: ' + error.data.message)
      })
      return result
    },
    KichHoat () {
      try {
        if (this.gview_DanhSach.data.length <= 0) {
          this.$root.$toast.warning('Không có dữ liệu để kích hoạt!')
          return
        }
        if (this.dgvNhanVien.data.length <= 0) {
          this.$root.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return
        }
        if (!this.gview_DanhSach.selectItem.ngay_ht_gp) {
          this.$root.$toast.warning('Bạn phải cập nhật ngày hoàn thành trước khi kích hoạt !')
          return
        }
        let titleConfirm = ''

        // mega cam
        if (this.cboDichVuVT.value == DichVuVienThong.MEGA_EYES) {
          titleConfirm = 'Bạn có chắc chắn muốn kích hoạt các dịch vụ này lên Visa/Vasc hay không?'
        }
        if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
          titleConfirm = 'Bạn có chắc chắn muốn kích hoạt các dịch vụ này lên Visa/Vasc hay không?'
        }
        if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
          titleConfirm = 'Bạn có chắc chắn muốn kích hoạt lên tổng đài hay không?'
        }

        if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
          titleConfirm = 'Bạn có chắn chắn muốn kích hoạt lên hệ thống IMS mới hay không?'
        }

        if (this.cboDichVuVT.value == DichVuVienThong.GPHONE) {
          titleConfirm = 'Bạn có chắc chắn muốn kích hoạt lên hệ thống gphone ?'
        }

        this.$confirm(titleConfirm, 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          let request = {
            'thaydoidv_nhieuphieu': this.THAYDOIDV_NHIEUPHIEU,
            'hdtb_id': this.hdtb_id,
            'thuebao_id': this.thuebao_id,
            'phieu_id': this.phieu_id,
            'dvvt_id': this.cboDichVuVT.value,
            'luong_id': this.luong_id,
            'loaitb_id': this.loaitb_id,
            'kqxl_id': this.DmKetQuaXL.selectKQXL ? this.DmKetQuaXL.selectKQXL : 0,
            'nhantin_nhanvien_diaban': this.nhantin_nhanvien_diaban,
            'ds_dvgt': this.grvDVGT.data,
            'ma_kh': this.gview_DanhSach.selectItem.ma_kh,
            'ma_tb': this.frmThongTinPhieuYc.txtMaTB,
            'ghichu': this.frmThongTinPhieuYc.txtGhiChu,
            'ds_hdtb': []
          }
          let result = await this.KICHHOAT_HTHS(request)
          console.log('Kết quả kích hoạt ', result)
          await this.HienThiDanhSach()
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi thực hiện kích hoạt: ' + error)
      }
    },
    async KichHoat_v2 () {
      try {
        if (this.gview_DanhSach.data.length <= 0) {
          this.$root.$toast.warning('Không có dữ liệu để kích hoạt!')
          return
        }
        if (this.dgvNhanVien.data.length <= 0) {
          this.$root.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return
        }
        for (let i = 0; i < this.gview_DanhSach.checked.length; i++) {
          let item = this.gview_DanhSach.checked[i]
          if (!item.ngay_ht_gp) {
            this.$root.$toast.warning('Bạn phải cập nhật ngày hoàn thành trước khi kích hoạt!')
            return
          }
        }
        let ds_hdtb = this.gview_DanhSach.checked.map(item => {
          item.chon = '1'
          return item
        })
        let request = {
          'thaydoidv_nhieuphieu': this.THAYDOIDV_NHIEUPHIEU,
          'hdtb_id': this.hdtb_id,
          'thuebao_id': this.thuebao_id,
          'phieu_id': this.phieu_id,
          'dvvt_id': this.cboDichVuVT.value,
          'luong_id': this.luong_id,
          'loaitb_id': this.loaitb_id,
          'kqxl_id': this.DmKetQuaXL.selectKQXL ? this.DmKetQuaXL.selectKQXL : 0,
          'nhantin_nhanvien_diaban': this.nhantin_nhanvien_diaban,
          'ds_dvgt': this.grvDVGT.data,
          'ma_kh': this.gview_DanhSach.selectItem.ma_kh,
          'ma_tb': this.frmThongTinPhieuYc.txtMaTB,
          'ghichu': this.frmThongTinPhieuYc.txtGhiChu,
          'ds_hdtb': ds_hdtb
        }
        let result = await this.KICHHOAT_HTHS(request)
        console.log('Kết quả kích hoạt ', result)
        await this.HienThiDanhSach()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện kích hoạt: ' + error)
      }
    },
    async tsbnKichHoat_Click () {
      if (!this.grvDVGT.data || this.grvDVGT.data.length == 0) {
        this.$toast.error('Không có danh sách dịch vụ đăng ký để kích hoạt!')
        return false
      }
      let res = await ChangeServiceOMCVIPCMAPI.fn_kiemtra_ngay_tdtl(this.axios, {vhdtb_id: this.hdtb_id})
      if (res.data.data !== "OK") {
        this.$root.$toast.warning(res.data.mesage);
        return;
      }
      if (this.THAYDOIDV_NHIEUPHIEU === 1) {
        if (this.gview_DanhSach.checked.length <= 0) {
          this.KichHoat()
        } else {
          this.KichHoat_v2()
        }
      } else {
        this.KichHoat()
      }
    },
    tsbtnDuAn_Click () {
      this.$refs.popupDuAnHDTB.showModal()
    },
    tsbtnThoaiTra_Click () {
      try {
        if (this.gview_DanhSach.data.length <= 0) {
          this.$root.$toast.warning('Danh sách phiếu trống. Không thực hiện. Bạn hãy kiểm tra lại !')
          return
        }
        if (this.gview_DanhSach.selectItem == null) {
          this.$root.$toast.warning('Hãy chọn phiếu !')
          return
        }

        if (this.PHAINHAP_GHICHU_TTVT_XN_NO == 1 &&
          this.frmThongTinChung.txtNoiDungTH == '' && this.tthd_id == TrangThaiHD.DANG_THI_CONG) {
          if (this.PHAILAM('PHAINHAP_GHICHU_TTVT_XN_NO')) {
            this.$root.$toast.warning('Hãy nhập nội dung thực hiện!')
            this.$refs.txtNoiDungTH.focus()
            return
          }
        }

        if (this.PHAILAM('KICHHOAT_TD')) {
          if (this.status == TRANGTHAI_DONGBO.DONGBO_CM || this.status == TRANGTHAI_DONGBO.DONGBO_SERVICE) {
            this.$root.$toast.warning('Thuê bao đã kích hoạt, không thể Thoái trả')
            return
          }
        }

        this.$confirm('Bạn thật sự muốn kết thúc phiếu không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          let s = await this.THOAITRA_XACMINH_HDTB(this.hdtb_id, this.phieu_id, this.DmKetQuaXL.selectKQXL, this.maycn, this.ipcn)
          if (s != '1') {
            return
          }
          let ngaygiao = this.frmThongTinPhieuYc.dtpNgayBG
          await this.SP_UPDATE_TT_GIAOVIEC(this.nhanvienId, ngaygiao, this.hdtb_id, this.phieu_id, Number.parseInt(this.cboNguoiGV.value))
          await this.UPDATE_ND_THUCHIEN(this.phieu_id, this.frmThongTinChung.txtNoiDungTH.trim())
          this.$root.$toast.success('Kết thúc phiếu thành công')
          await this.HienThiDanhSach()
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    tsbtnThongTinKT_Click () {
      if (this.thuebao_id !== 0) {
        this.$refs.traCuuThongSoKTModal.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu')
      }
    },
    btnInDS_Click () {
      this.$refs.txtMaTB.focus()
      if (this.gview_DanhSach.checked.length === 0) {
        this.$root.$toast.warning('Hãy chọn phiếu để In')
        return
      }
      this.$root.$toast.warning('Chức năng này đang phát triển. Khi nào có sẽ tích hợp')
    },
    tsbtnInBB_Click () {
      try {
        this.kt_thaydoi = false
        this.InPhieu()
        if (this.kt_thaydoi) {
          let matb = this.frmThongTinPhieuYc.txtMaTB.trim()
          this.HienThiDanhSach()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async InPhieu () {
      try {
        let vhdkhid = await this.fn_tt_hd_thuebao(this.hdtb_id)
        if (vhdkhid == '-1') {
          this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        if (this.gview_DanhSach.checked.length <= 0) {
          this.$root.$toast.warning('Hãy chọn phiếu!')
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = this.gview_DanhSach.checked.map((item) => item.hdkh_id)
        nhdtbid = this.gview_DanhSach.checked.map((item) => item.hdtb_id)
        nphieuid = this.gview_DanhSach.checked.map((item) => item.phieu_id)
        this.modelIn.n_phieu_id = nphieuid.join(',')
        this.modelIn.ma_gd = this.frmThongTinPhieuYc.txtMaGD.trim()
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
      return this.GetData(await ChangeServiceOMCVIPCMAPI.fn_tt_hd_thuebao(this.axios, req))
    },
    tsbtnCreatIssue_Click () {
      if (this.hdtb_id == 0) {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !')
        return
      }
      let vipgs = false
      if (this.PHAILAM('VIP_GIAMSAT_IT360')) {
        vipgs = true
      }
      let data = {
        phieu_id: this.phieu_id,
        loai: 1,
        vip_giamsat: vipgs,
        hdtb_id: this.hdtb_id
      }
      console.log('request create issues:', data)
      this.$refs.dialogCreateIssue.openDialog(data)
    },
    tsbtUpHS_Click () {
      if (this.gview_DanhSach.selectItem != null) {
        this.popupComponent = () => import('../../profile/UploadEProfile/UploadEProfile_content')
        this.$refs.hosodientu.show()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn thuê bao')
      }
    },
    customiseCell: function (args) {
      if (args.column.field === 'ngay_hh') {
        let ngayhh = moment(args.data.ngay_hh, 'DD/MM/YYYY hh:mm:sss').toDate()
        let now = new Date()
        if (now > ngayhh) {
          args.cell.setAttribute('style', 'background-color: red;')
        } else {
          let estimated = this.diff_hours(ngayhh, now)
          if (estimated < 3) {
            args.cell.setAttribute('style', 'background-color: #8FBDD3;')
          }
        }
      }
    },
    diff_hours (dt2, dt1) {
      let diff = (dt2.getTime() - dt1.getTime()) / 1000
      diff /= (60 * 60)
      return Math.abs(Math.round(diff))
    },
    tsbtnVatTuMoi_Click () {
      if (this.phieu_id !== 0) {
        this.modelVatTu2.phieu_id = this.phieu_id
        this.modelVatTu2.loaihd_id = LoaiHopDong.THAY_DOI_DV
        this.modelVatTu2.hdtb_id = this.hdtb_id
        this.modelVatTu2.thuebao_id = this.thuebao_id
        this.modelVatTu2.loaitb_id = this.loaitb_id
        this.modelVatTu2.baohong_id = 0
        this.$refs.popupVatTuTB.openDialog()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !!!')
      }
    },
    onActionClick (action) {
      if (action.id === 'btnLayTTMoi') {
        this.gview_DanhSach.selectItem = null
        this.HienThiDanhSach()
      } else if (action.id === 'tsbtnChapNhan') {
        this.tsbtnChapNhan_Click()
      } else if (action.id === 'tsbtnHoanCong') {
        this.btnHoanCong_Click()
      } else if (action.id === 'tsbtnXuatFile') {
        this.tsbtnXuatFile_Click()
      } else if (action.id === 'tsbtnHen') {
        this.tsbtnHen_Click()
      } else if (action.id === 'tsbtnChuyenTo') {
        this.tsbtnChuyenTo_Click()
      } else if (action.id === 'tsbtnTraPhieu') {
        this.tsbtnTraPhieu_Click()
      } else if (action.id === 'tsbtnHoanThienHS') {
        this.tsbtnChuyenDB_Click()
      } else if (action.id === 'tsbtnGiaoPhieu') {
        this.tsbtnGiaoPhieu_Click()
      } else if (action.id === 'tsbtnGiaoViec') {
        this.tsbtnGiaoViec_Click()
      } else if (action.id === 'tsbtnGiaoPhieu_Ton') {
        this.tsbtnGiaoPhieu_Ton_Click()
      } else if (action.id === 'tsbtnVatTuMoi') {
        this.tsbtnVatTuMoi_Click()
      } else if (action.id === 'tsbtnHoanThanh') {
        this.tsbtnHoanThanh_Click()
      } else if (action.id === 'tsbtnDoiNe') {
        this.tsbtnDoiNe_Click()
      } else if (action.id === 'tsbtnHuyKH') {
        this.tsbtnHuyKH_Click()
      } else if (action.id === 'tsbtnKQ_DoKiem') {
        this.tsbtnKQ_DoKiem_Click()
      } else if (action.id === 'tsbtnTraCuuNo') {
        this.$refs.popupTCThongTinNo.openDialog(this.frmThongTinPhieuYc.txtMaTB.trim())
      } else if (action.id === 'tsbtnKichHoat') {
        this.tsbnKichHoat_Click()
      } else if (action.id === 'tsbtnDuAn') {
        this.tsbtnDuAn_Click()
      } else if (action.id === 'tsbtnThoaiTra') {
        this.tsbtnThoaiTra_Click()
      } else if (action.id === 'tsbtnThongTinKT') {
        this.tsbtnThongTinKT_Click()
      } else if (action.id === 'btnInDS') {
        this.tsbtnInBB_Click()
      } else if (action.id === 'tsbtnInBB') {
        this.tsbtnInBB_Click()
      } else if (action.id === 'tsbtnCreatIssue') {
        this.tsbtnCreatIssue_Click()
      } else if (action.id === 'tsbtUpHS') {
        this.tsbtUpHS_Click()
      } else if (action.id === 'tsbtnVatTu') {
        this.tsbtnVatTu_Click()
      }
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
    clear_data () {
      this.$refs.gview_DanhSach.$refs.grid.clearSelection()
      this.gview_DanhSach.checked = []
      this.gview_DanhSach.selectItem = null
      this.frmThongTinPhieuYc.txtHostLD = ''
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.hdkh_id = 0
      this.phieu_id = 0
      this.frmThongTinPhieuYc.lienhe = 'Liên hệ: '
      this.frmThongTinPhieuYc.txtMaGD = ''
      this.frmThongTinPhieuYc.txtTenTB = ''
      this.frmThongTinChung.txtNDGiao = ''
      this.frmThongTinChung.txtThongTinTra = ''
      this.frmThongTinPhieuYc.txtMaTB = ''
      this.frmThongTinPhieuYc.txtGhiChu = ''
      this.frmThongTinChung.txtNoiDungTH = ''
      this.frmThongTinChung.txtThongTinTra = ''
      this.frmThongTinChung.txtNDGiao = ''
      this.setActiveButton('tsbtnHoanThienHS', false)
      this.frmThongTinPhieuYc.dtpNgayGV = this.formatDate(new Date(), null)
      this.frmThongTinPhieuYc.dtpNgayBG = this.formatDate(new Date(), null)
      this.chkNgayGV.checked = false
      this.chkNgayGV.checked = false
      this.grvDVGT.data = []
      this.frmThongTinPhieuYc.txtDiaChiLD = ''
      this.frmThongTinPhieuYc.txtDiaChiTB = ''
      this.$refs.txtMaTB.focus()
    },
    ChuanHoaTen (ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXL = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXL = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXL = this.ChuyenChuHoa(strXL)
            if (tenNew !== '') {
              tenNew += ' ' + strXL
            } else tenNew = strXL
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXL = strCon
          strXL = this.ChuyenChuHoa(strXL)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXL
          } else {
            tenNew = strXL
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
      return moment(dateStr, 'DD/MM/YYYY HH:mm:ss')
    },
    getDateNew (dateStr, format) {
      if (format) {
        return moment(dateStr, format)
      }

      return moment(dateStr, 'DD/MM/YYYY HH:mm:ss')
    },
    formatDate (date, format) {
      let defaultFormat = 'DD/MM/YYYY HH:mm:ss'
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format(defaultFormat)
      }
    },
    validationStatus: function (validation) {
      return typeof validation !== 'undefined' ? validation.$error : false
    },
    async GiaoPhieuView_OnClick() {
      this.giaoPhieuViewDialog.isVisiable = true
      this.$refs.GiaoPhieuView.show()
    },
    async GiaoPhieuView_OnClose() {
      this.giaoPhieuViewDialog.isVisiable = false
    },
    async kiemtra_hoanthien_thoaitra_ban(hdtb_ban, kieu){
        try{
            this.loading(true)
            let response = await ChangeServiceOMCVIPCMAPI.kiemtra_hoanthien_thoaitra_ban(this.axios, hdtb_ban, kieu)
            this.loading(false)
            if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                return response.data.data
            }else{
                if(response&&response.data&&response.data.message){
                  return response.data.message
                }else{
                  return 'Kiểm tra hoàn thiện thoái trả bán không thành công!'
                }
            }
        }catch(e){
          this.loading(false)
          if(e.data&&e.data.message){
            return e.data.message
          }else if(e.response&&e.response.data&&e.response.data.message){
            return e.response.data.message
          }else{
            return 'Kiểm tra hoàn thiện thoái trả bán không thành công!'
          }
        }
    },
  }
}
</script>
<style>
.modal .modal-hosodientu-size {
  max-width: 90% !important;
}
.traphieu .page-content {
  position: unset;
}

.traphieu-content {
  width: 1200px;
}

.disabled_color {
  color: gray !important;
}

.text-view {
  background-color: white !important;
}

.modal-dialog {
  max-width: 60vw;
}

.modal-body .page-content {
  position: inherit;
}

.main-wrapper-popup-chuyento .page-content {
  position: inherit !important;
}

</style>
