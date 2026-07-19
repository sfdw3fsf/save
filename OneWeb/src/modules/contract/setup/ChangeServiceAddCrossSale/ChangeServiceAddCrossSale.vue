<template src="./ChangeServiceAddCrossSale.html"> </template>
<style src="./ChangeServiceAddCrossSale.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from '@/components/kylq_components/ActionTop'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import { DichVuVienThong, LoaiHinhTB, LoaiHopDong, NHOM_LNV, TrangThaiHD, TrangThaiTB } from '../../../../const/enums'
import API from './API'
import { DICHVU_VIENTHONG, TRANGTHAI_DONGBO, TRANGTHAI_TB_DVGT } from '../../../../constants'
import Vue from 'vue'
import { DUNG_THU, KHOANMUC_TT, KieuCaiDatDVGT, KieuLapDat } from '../InstallNewSubs/Enums'
// import SearchContractModal from '../ChangeInstallUnit/popup/SearchContract/SearchContractModal'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import { Virtualization, ListViewPlugin } from '@syncfusion/ej2-vue-lists'
import ChonTinhTLS from './popups/ChonTinhTLS'
import frmChonTinh from '../ChangeMegaWanConfigCrossSell/popups/frmChonTinh'
import DSTBCungMa from '../ChangeMultiServicePackage/popups/DSTBCungMa/DSTBCungMa'
import popupTraCuuThueBao from '../ChangeService/popup/popupTraCuuThueBao'
import frmCTTienKM from '../SeparationSubscriber/popups/frmCT_Tien_KM_V2'
import InHopDong from '../../../print/InHopDong/InHopDong'
import DichVuGiaTang from '../../../../const/enums/DichVuGiaTang'
import DangKyLyDoHuyBC from './popups/DangKyLyDoHuyBC'
import ThongTinHenKhachHangModal from '../SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal'
import ThongTinLienHe from '../ChangeContractOwner/popup/ThongTinLienHe'
import ThongTinEmail from '../ChangeContractOwner/popup/ThongTinEmail'
import ThongSoTSL from '../SetupChangeTSLSpeedCrossSale/Popups/frmThongSoTSL/frmThongSoTSL.vue'
import NguoiGT from '../SeparationSubscriber/popups/frmNguoiGT'
import TocDoMucCuoc from '../../../install/ChangeADSLSpeed/popups/TocDoMucCuoc/TocDoMucCuoc'
import ThongSoCoDinh from '../TransferRightsCrossSell/popups/ThongSoCoDinh/ThongSoCoDinh.vue'
import frmThongSoWan from '../ChangeMegaWanConfigCrossSell/popups/frmThongSoWan'
import TinhTC from './TinhTC'
import ThongSoThueBao from './ThongSoThueBao'
import frmTCDBTBKH from '../TransferRightsCrossSell/popups/frmTC_DBTB_KH/frmTC_DBTB_KH.vue'
import TKTBChuyenDich from './popups/TKTBChuyenDich'
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    listbox: {
      noRecordsTemplate: ''
    }
  }
})
Vue.use(ListViewPlugin)
export default {
  name: 'ChangeServiceAddCrossSale',
  components: {
    breadcrumb,
    ActionTop,
    KDatePicker,
    TKTBChuyenDich,
    SearchContractModal,
    ChonTinhTLS,
    frmChonTinh,
    DSTBCungMa,
    frmCTTienKM,
    InHopDong,
    DangKyLyDoHuyBC,
    ThongTinHenKhachHangModal,
    ThongTinLienHe,
    ThongTinEmail,
    ThongSoTSL,
    NguoiGT,
    TocDoMucCuoc,
    ThongSoCoDinh,
    frmThongSoWan,
    frmTCDBTBKH,
    popupTraCuuThueBao
  },
  data() {
    return {
      editSettings: { allowEditing: true },
      header: {
        title: 'THAY ĐỔI DỊCH VỤ (BÁN CHÉO)',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' },
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa HĐ',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'tsbtnGiaoPhieu',
          name: 'Giao phiếu',
          active: true,
          icon_class: 'one-file-edit2',
          visible: false
        },
        {
          id: 'tsbtnThanhToan',
          name: 'Thanh toán',
          active: true,
          icon_class: 'nc-icon-glyph shopping_credit-card'
        },
        {
          id: 'btnInHD',
          name: 'In PL',
          active: true,
          icon_class: 'icon one-print'
        },
        {
          id: 'tsbtnThemTB',
          name: 'Thêm TB',
          active: true,
          icon_class: 'one-mobile-plus'
        },
        {
          id: 'tsbtnXoaTB',
          name: 'Xóa TB',
          active: true,
          icon_class: 'one-phone-remove'
        },
        {
          id: 'tsbtnHen',
          name: 'Hẹn LĐ',
          active: true,
          icon_class: 'one-calendar-number',
          visible: true
        },
        {
          id: 'tsbtnThongTinLH',
          name: 'Liên hệ',
          active: true,
          icon_class: 'nc-icon-glyph business_contacts'
        },
        {
          id: 'tsbtnEmail',
          name: 'Email',
          active: true,
          icon_class: 'one-email'
        }
      ],
      formProps: {
        txtMaGD: '',
        txtMaHD: '',
        dtpNgayYeuCau: this.formatDate(new Date(), 'DD/MM/YYYY'),
        dtpNgayLapHD: this.formatDate(new Date(), 'DD/MM/YYYY'),
        txtMaHD_TTKD: '',
        txtMaTB: '',
        txtSoAo: '',
        txtHuongKN: '',
        txtTrangThaiTB: '',
        txtTenTB: '',
        txtDiaChiTB: '',
        txtDiaChiLD: '',
        txtDiaChiLD_Dau: '',
        txtDiaChiLD_Cuoi: '',
        txtGhiChu: '',
        txtMaERP: '',
        txtMaBSS: '',
        txtMaKH: '',
        dtpNgayHH_TD: this.formatDate(new Date(), 'DD/MM/YYYY'),
        txtTenKH: '',
        txtDiaChiKH: '',
        txtTongTien: 0,
        txtTongVat: 0,
        txtCTV: '',
        txtNguoiGT: '',
        txtTongTienHD: '',
        txtMaDuAn: ''
      },
      Kieuld_click: false,
      check_hdtb_bancheo: true,
      disabledBox: {
        dtpNgayHH_TD: false,
        txtMaHD_TTKD: false
      },
      btnEditLyDoHuy: {
        disabled: false
      },
      chkKHKT3: {
        disabled: false,
        checked: false
      },
      chkCTV: {
        checked: false,
        disabled: false
      },
      chkNguoiGT: {
        checked: false,
        disabled: false
      },
      chkNgay_HHTD: {
        checked: false,
        disabled: false
      },
      cboDichVuVT: {
        list: [],
        disable: false,
        value: 0
      },
      cboLoaihinhTB: {
        list: [],
        disable: false,
        value: 0
      },
      cboKieuLD: {
        list: [],
        disable: false,
        value: null
      },
      cboKieuLD_TC: {
        list: [],
        disable: false,
        value: 0
      },
      cboKieuLD_TC_TSL: {
        list: [],
        disable: false,
        value: 0
      },
      settingSelect2: {
        language: 'vi'
      },
      lvwThueBao: {
        headerTitle: 'Số máy/Acc',
        data: [],
        enableUi: true
      },
      lvLyDoHuy: {
        data: []
      },
      dgvDVGT: {
        data: [],
        headers: [
          {
            fieldName: 'ma_dvgt',
            headerText: 'Mã DV',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_dvgt',
            headerText: 'Tên dịch vụ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'cuoc_ld',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_ld',
            headerText: 'Vat',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sudung',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'kieu_yc',
            headerText: 'Yêu cầu',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('KIEUYCTEMP', {
                  template: `<span v-if="data.kieu_yc == '1'" class="icon one-check"></span><span v-else-if="data.kieu_yc == 0" class="icon one-cancel"></span>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'sua',
            headerText: 'Sửa',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('suatempt', {
                  template: `<input type="checkbox" v-model="data.sua" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('dangkytempt', {
                  template: `<input type="checkbox" v-model="data.dangky" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('huytempt', {
                  template: `<input type="checkbox" v-model="data.huy" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'tanglen',
            headerText: 'TANGLEN',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'cuoc_sd',
            headerText: 'Cước SD',
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
            fieldName: 'cuoc_kmld',
            headerText: 'CUOC_KMLD',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_kmld',
            headerText: 'VAT_KMLD',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sl_cha',
            headerText: 'SL_CHA',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_dau',
            headerText: 'BLOCK_DAU',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_tiep',
            headerText: 'BLOCK_TIEP',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'gia_block_tiep',
            headerText: 'GIA_BLOCK_TIEP',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_block_tiep',
            headerText: 'VAT_BLOCK_TIEP',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'he_so',
            headerText: 'HE_SO',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'mua_them',
            headerText: 'MUA_THEM',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        selectionOptions: { cellSelectionMode: 'Box', type: 'Single', mode: 'Cell' },
        cellSelected: null
      },
      dgvDVGTTC: {
        data: [],
        headers: [
          {
            fieldName: 'ma_dvgt',
            headerText: 'Mã DV',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_dvgt',
            headerText: 'Tên dịch vụ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'cuoc_ld',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_ld',
            headerText: 'Vat',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sudung',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'kieu_yc',
            headerText: 'Yêu cầu',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('KIEUYCTEMP2', {
                  template: `<span v-if="data.kieu_yc == '1'" class="icon one-check"></span><span v-else-if="data.kieu_yc == 0" class="icon one-cancel"></span>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'sua',
            headerText: 'Sửa',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('suatempt2', {
                  template: `<input type="checkbox" v-model="data.sua" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('dangkytempt2', {
                  template: `<input type="checkbox" v-model="data.dangky" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('huytempt2', {
                  template: `<input type="checkbox" v-model="data.huy" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'noidung_tc',
            headerText: 'Nội dung/Số lượng',
            allowEditing: true,
            editType: 'string',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
          },
          {
            fieldName: 'cuoc_sd',
            headerText: 'Cước SD',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_sd',
            headerText: 'Vat SD',
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
            fieldName: 'cuoc_kmld',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_kmld',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sl_cha',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_dau',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'gia_block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'he_so',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'mua_them',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        selectionOptions: { cellSelectionMode: 'Box', type: 'Single', mode: 'Cell' },
        cellSelected: null
      },
      dgvDVGTTC_TSL: {
        data: [],
        headers: [
          {
            fieldName: 'ma_dvgt',
            headerText: 'Mã DV',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'ten_dvgt',
            headerText: 'Tên dịch vụ',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'cuoc_ld',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_ld',
            headerText: 'Vat',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sudung',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'kieu_yc',
            headerText: 'Yêu cầu',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('KIEUYCTEMP3', {
                  template: `<span v-if="data.kieu_yc == '1'" class="icon one-check"></span><span v-else-if="data.kieu_yc == 0" class="icon one-cancel"></span>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'sua',
            headerText: 'Sửa',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('suatempt3', {
                  template: `<input type="checkbox" v-model="data.sua" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            allowFiltering: true,
            allowSorting: false,
            cssClass: 'text-center',
            visible: true,
            template: function() {
              return {
                template: Vue.component('dangkytempt3', {
                  template: `<input type="checkbox" v-model="data.dangky" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'huy',
            headerText: 'Hủy',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            cssClass: 'text-center',
            template: function() {
              return {
                template: Vue.component('huytempt3', {
                  template: `<input type="checkbox" v-model="data.huy" class="check">`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {}
                })
              }
            }
          },
          {
            fieldName: 'noidung_tc',
            headerText: 'Nội dung/Số lượng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'cuoc_sd',
            headerText: 'Cước SD',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_sd',
            headerText: 'Vat SD',
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
            fieldName: 'cuoc_kmld',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_kmld',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'sl_cha',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_dau',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'gia_block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'vat_block_tiep',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'he_so',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'mua_them',
            headerText: '',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        selectionOptions: { cellSelectionMode: 'Box', type: 'Single', mode: 'Cell' },
        cellSelected: null
      },
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      draggable: true,
      targetTop: '#app .main-wrapper',
      thongso_ban: ThongSoThueBao,
      thongso_tc: ThongSoThueBao,
      thongso_tc_dau: ThongSoThueBao,
      thongso_tc_cuoi: ThongSoThueBao,
      thuebao_tc_id: 0, // nếu là tsl thì đây là thuebao_tc_id dau
      thuebao_tc_id_tsl: 0, // nếu là tsl thì đây là thuebao_tc_id cuoi
      tinh_dau: TinhTC,
      tinh_cuoi: TinhTC,
      flagThongSoTSL: 0,
      tinh: TinhTC,
      ds_isdn: [],
      matb_isdn_chinh: '',
      loaisochinh: 0,
      isdn_sochinh: false,
      Kieuld_tc_id: 0,
      Kieuld_tc_id_tsl: 0,
      quytrinh_tc_tn: 0,
      huonggiao_tc_tn: 0,
      chi_giao_net: true,
      kenh_trang: true,
      kt_kenhphu_ao: false,
      dichvuvt_id: 0,
      doituong_id: 1,
      donvi_id: 0,
      hdkh_id: 0,
      quyen_duoc_hen: 0,
      hdkh_cha_id: 0,
      hdtb_id: 0,
      khachhang_id: 0,
      thuebao_id: 0,
      loaitb_id: 0,
      doitac_id: 0,
      vloaikenh_id: 0,
      jloaikenh_id: 0,
      dtienld: 0.0,
      dvatld: 0.0,
      dtien_dv: 0,
      dvat_dv: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      tien_dv: 0,
      vat_dv: 0,
      CT_TIENHD_ID: 0,
      trangbi_id: 3,
      linhvuc_id: 0,
      hdtb_id_cha: 0,
      thuebao_id_cha: 0,
      huonggiaotn_id: 0,
      tocdotn_id: 0,
      muccuoctn_id: 0,
      quytrinh_id: 0,
      luong_id: 0,
      quytrinh_tc_tn_dau: 0,
      huonggiao_tc_tn_dau: 0,
      quytrinh_tc_tn_cuoi: 0,
      huonggiao_tc_tn_cuoi: 0,
      tagForm: '',
      ts_khong_kt_nocuoc: 0,
      ts_ngay_hethan_tamdung: 0,
      ts_sinhma_gd_theo_donvi: false,
      loi_sinh_magd: false,
      ctv_id: 0,
      donvi_ctv_id: 0,
      loainv_ctv_id: 0,
      nguoigt_id: 0,
      donvi_nguoi_gt_id: 0,
      loainv_nguoi_gt_id: 0,
      dsTienKhoanMuc: [],
      dsHDTBDV: [],
      dsHDTDDV: [],
      dsTTTT: [],
      dsHDBC: [],
      dsCTTHD: [],
      dsHDTBBC: [],
      dsDKHUY: [],
      dsHDTB_ADSL: [],
      dsHDTB_CD: [],
      dsHDTB_GP: [],
      dsHDTB_TSL: [],
      dsHDTB_MGW: [],
      dsHDTB_DD: [],
      dsHDTB_IMS: [],
      dsHDTB: [],
      dsHDKH: [],
      dsDKHUY_TEMP: [],
      dsDKDVGTBC: [],
      dsHDBCADSL: [],
      dsHDBCCD: [],
      dsHDBCTSL: [],
      dsHDBCMGWAN: [],
      dsHDBCIMS: [],
      dsDK_HUYBC: [],
      dsBD_HUYBC: [],
      showTabHeader: false,
      modelSearchContract: {
        loai_hd_id: 0,
        dichvu_id: 0,
        ngay_yc: '',
        ngay_kt: '',
        donvi_id: 0,
        donvi_dl_id: 0,
        trangthai_hd: TrangThaiHD.MOI
      },
      nhanvienId: Number(this.$root.token.getNhanVienID()),
      donviId: Number(this.$root.token.getDonViID()),
      nguoicn: this.$root.token.getUserName(),
      phanvungId: Number(this.$root.token.getPhanVungID()),
      donviDLId: Number(this.$root.token.getDonViDuLieuID()),
      maycn: '',
      ipcn: '',
      btnDiemDau: {
        visible: false
      },
      btnDiemCuoi: {
        visible: false
      },
      showThongSoTc: false,
      dsHDTB_CNTT: [],
      ItemList: [],
      dsDBTBBC: [],
      dsDBTBBC_Temp: [],
      lineLabel7: 'Danh sách dịch vụ gia tăng thi công',
      lineLabel9: 'Danh sách dịch vụ gia tăng thi công điểm cuối',
      tabOneActive: true,
      tabTwoActive: false,
      dsDBTB: [],
      dsBD_LYDOHUY: [],
      dsThueBaoDichVu: {
        danhsach: [],
        danhsach_tinh: [],
        danhsach_tinh_tsl: []
      },
      lblCanhBaoVisible: false,
      lblCanhBao: 'Cảnh báo',
      modelInHD: {},
      dsTemp: [],
      dsHuy: [],
      flagNguoiGT: 0,
      ThongSoCoDinhModel: {
        ploaitb_ld: 0,
        pLoaihinhTB: 0,
        ploaitb: 0,
        thuebao_id_ban: 0,
        thuebao_id_thicong: 0,
        vloaihd: 0,
        tinhtc: {},
        khachhang_id: 0,
        hdkh_id: 0
      },
      TKTBChuyenDichProps: {
        khachhang_id: 0,
        ma_gd: '',
        text: '',
        title: ''
      }
    }
  },
  provide: {
    listview: [Virtualization]
  },
  watch: {
    'cboDichVuVT.value': async function(newValue) {
      await this.cboDichVuVT_SelectedValueChanged(newValue)
    },
    /*'cboLoaihinhTB.value': function (newValue) {
      this.cboLoaihinhTB_SelectedValueChanged(newValue)
    },*/
    'cboKieuLD_TC.value': function(newValue) {
      this.cboKieuLD_TC_SelectedValueChanged(newValue)
    },
    'cboKieuLD_TC_TSL.value': function(newValue) {
      this.cboKieuLD_TC_TSL_SelectedValueChanged(newValue)
    },
    // thuebao_tc_id: function(newValue){
    //   console.log("Thay đổi test: ", newValue);
    // }
  },
  async mounted() {
    await this.frmThayDoiDVBC_Load()
    await this.frmThayDoiDVBC_Activated()
  },
  methods: {
    async frmThayDoiDVBC_Load() {
      try {
        this.loading(true)
        this.getTag()
        this.maycn = await this.$root.token.getMachine()
        this.ipcn = await this.$root.token.getIP()
        this.setActiveActions(-1)
        this.dsTienKhoanMuc = await this.Lay_tienkhoanmuc_loaihd(LoaiHopDong.THAY_DOI_DV)
        await this.GET_DICHVU_VT()
        this.btnEditLyDoHuy.disabled = true
        this.chkNgay_HHTD.disabled = true
        this.chkNgay_HHTD.checked = false
        this.disabledBox.dtpNgayHH_TD = true
        // Lấy các tham số mặc đinh
        await this.LAY_DS_THAMSO_MD()
        this.setVisibleButton('tsbtnThemTB', false)
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async frmThayDoiDVBC_Activated() {
      if (this.formProps.txtMaGD) {
        let dsHd = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.txtMaGD.trim(), LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI)
        await this.HienThiTTHopDongKH(dsHd)
      }
      this.$refs.txtMaTB.focus()
    },
    async cboKieuLD_Click() {
      this.Kieuld_click = true
    },
    acceptSearchContract(item) {
      this.formProps.txtMaGD = item.ma_gd
      if (this.formProps.txtMaGD && this.formProps.txtMaGD.trim() !== '') {
        this.txtMaGD_KeyPress()
      }
    },
    async txtMaTB_KeyPress() {
      try {
        this.loading(true)
        let dt = []
        let matb = this.formProps.txtMaTB
        matb = this.clearTheSpace(this.formProps.txtMaTB)
        matb = matb.replace("'", '')
        this.formProps.txtMaTB = matb
        if (this.formProps.txtMaTB) {
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
            this.chi_giao_net = false
            dt = await this.LAY_TINHTC_ID_TSL(this.formProps.txtMaTB, this.dichvuvt_id)
            if (dt.length > 0) {
              let tinhdauid = dt.filter((item) => item.daucuoi_id == 1)[0].tinh_thicong
              let tinhcuoiid = dt.filter((item) => item.daucuoi_id == 2)[0].tinh_thicong
              this.tinh_dau = await this.GetTinhTC(tinhdauid)
              this.tinh_cuoi = await this.GetTinhTC(tinhcuoiid)
              this.thuebao_tc_id = Number(dt.filter((item) => item.daucuoi_id == 1)[0]['thuebao_kn_id'])
              this.thuebao_tc_id_tsl = Number(dt.filter((item) => item.daucuoi_id == 2)[0]['thuebao_kn_id'])
              if (this.tinh != null) {
                this.tinh.tinhthicong_id = 0
              }
              this.kenh_trang = false
            } else {
              this.kenh_trang = true
              let cfm = await this.$confirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !', 'Thông báo', {
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
              })
              if (cfm) {
                this.$refs.popupChonTinhTLS.showModal()
                return
                // TODO di chuyen den ham chon tinh
              } else {
                await this.Clear()
                return
              }
            }
          } else {
            this.tinh = await this.LAY_TINHTHICONG_THEO_MATB(this.formProps.txtMaTB)
            if (this.tinh.tinhthicong_id == 0) {
              this.kenh_trang = true
              try {
                if(this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET||this.dichvuvt_id == 26){
                  let confirm = await this.$confirm('Thuê bao không có trong danh bạ bán chéo. Bạn phải chọn tỉnh thi công !', 'Thông báo', {
                    confirmButtonText: 'Có',
                    cancelButtonText: 'Không'
                  })
                  if (confirm) {
                    this.$refs.popupChonTinh.showDialog()
                    return
                    // TODO di chuyen den ham chon tinh
                  } else {
                    await this.Clear()
                    return
                  }
                }
                else{
                  this.$root.$toast.error("Thuê bao chưa map bán chéo, không thể tiếp tục")
                  await this.Clear()
                  return
                }
              } catch (error) {
                return
              }
            } else {
              this.kenh_trang = false
              let dtb = await this.LAY_THUEBAO_KN_ID_THEO_MATB(this.formProps.txtMaTB)
              this.chi_giao_net = dtb.length > 0 && dtb[0].thuebao_kn_id == '-1'
            }
            if (!this.kenh_trang) {
              this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
              //console.log("thuebao_tc_id: ",this.thuebao_tc_id)
            } else {
              this.thuebao_tc_id = 0
            }
          }
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
            let data1 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_dau.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data1.length > 0) {
              this.cboKieuLD_TC.list = data1.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
            } else {
              this.cboKieuLD_TC.list = []
              this.cboKieuLD_TC.value = 0
            }
            let data2 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_cuoi.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data2.length > 0) {
              this.cboKieuLD_TC_TSL.list = data2.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              this.cboKieuLD_TC_TSL.value = this.cboKieuLD_TC_TSL.list[0].id
            } else {
              this.cboKieuLD_TC_TSL.list = []
              this.cboKieuLD_TC_TSL.value = 0
            }
          } else {
            let data = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data.length > 0) {
              this.cboKieuLD_TC.list = data.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
            } else {
              this.cboKieuLD_TC.list = []
              this.cboKieuLD_TC.value = 0
            }
            this.cboKieuLD_TC_TSL.list = []
          }
          let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.formProps.txtMaTB, LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI, this.dichvuvt_id, 0)
          if (ds.length > 0) {
            await this.HienThiTTHopDongKH(ds)
          } else {
            await this.HienThiTT_DanhBa(this.formProps.txtMaTB)
          }
        }
        if (this.cboKieuLD.value == KieuLapDat.TAMDUNG_SD_MEGA && this.formProps.txtTenTB) {
          await this.HienCanhBaoThietHai()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async btnEditLyDoHuy_Click() {
      try {
        if (this.dsTemp.length < 2) {
          let ds = await this.LAY_DS_DK_LYDOHUY(this.hdtb_id)
          this.dsTemp.push(ds)
          ds = await this.LAY_DS_DK_LYDOHUY(this.hdtb_id)
          this.dsTemp.push(ds)
        }
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          this.$refs.popupDangKyLyDoHuyBC.showModal(this.tinh_dau.tinhthicong_id, this.dsTemp, this.hdtb_id, true, 2, this.loaitb_id)
        } else {
          this.$refs.popupDangKyLyDoHuyBC.showModal(this.tinh.tinhthicong_id, this.dsTemp, this.hdtb_id, true, 2, this.loaitb_id)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async acceptDangKyLyDoHuyBC(dsDKHUY) {
      try {
        if (dsDKHUY.length > 0) {
          this.dsDKHUY_TEMP = dsDKHUY
          this.dsTemp = this.dsDKHUY_TEMP
          this.dsDKHUY = dsDKHUY
        }
        if (this.KT_DangKy_TD()) {
          this.btnEditLyDoHuy.disabled = false
        }
        await this.HienThiDS_LyDoHuy(this.dsDKHUY[0])
        this.dsHuy = await this.lay_ds_lydohuy(2, this.loaitb_id)
        if (this.dsHuy.length > 0) {
          for (let i = 0; i < this.dsHuy.length; i++) {
            for (let j = 0; j < this.dsDKHUY[0].length; j++) {
              if (this.dsHuy[i]['lydohuy_id'] == this.dsDKHUY[0][j]['lydohuy_id']) {
                this.dsHuy[i]['dangky'] = 1
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async lay_ds_lydohuy(loaildid, loaitbid) {
      return this.GetDataList(await API.lay_ds_lydohuy(this.axios, loaildid, loaitbid))
    },
    btnTien_Click() {
      try {
        this.$refs.frmCTTienKM.frmCT_Tien_KM(LoaiHopDong.THAY_DOI_DV, this.dsTienKhoanMuc)
      } catch (error) {
        console.log(error)
      }
    },
    frmCTTienKMCloseDialog(data) {
      if (data) {
        try {
          this.dsTienKhoanMuc = data
          if (this.dsTienKhoanMuc.length > 0) {
            for (let i = 0; i < this.dsTienKhoanMuc.length; i++) {
              let item = this.dsTienKhoanMuc[i]
              if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_TDDICHVU) {
                this.dtienld = parseFloat(item.tien)
                this.dvatld = parseFloat(item.vat)
              }
              if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
                this.dtienld = parseFloat(item.tien)
                this.dvatld = parseFloat(item.vat)
              }
              this.LayTongTien_HDTB()
            }
          }
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Co loi: ' + error.message)
        }
      }
    },
    async HienCanhBaoThietHai() {
      this.lblCanhBaoVisible = false
      let kt = await this.thamso_md_theo_ma('CANH_BAO_THIET_HAI_FIBER')
      if (kt) {
        try {
          if (this.loaitb_id == LoaiHinhTB.INTERNET_FTTH) {
            let smsCanhBaoThietHai = await this.LAY_ND_SMS_CANHBAO_THIETHAI(this.formProps.txtMaTB, 0, 1)
            if (smsCanhBaoThietHai) {
              this.lblCanhBao = smsCanhBaoThietHai.replace('CANH BAO THIET HAI: ', '')
              this.lblCanhBaoVisible = true
            }
          }
        } catch (error) {
          console.log(error)
        }
      }
    },
    async HienThiTT_DanhBa(matb) {
      try {
        this.lvLyDoHuy.data = []
        this.btnEditLyDoHuy.disabled = true
        this.chkNgay_HHTD.disabled = true
        this.chkNgay_HHTD.checked = false
        this.disabledBox.dtpNgayHH_TD = true
        this.dsBD_LYDOHUY = []
        this.dsDKHUY = []
        let data = await this.LAY_DANHBA_THEO_MATB(matb, this.dichvuvt_id, 0)
        if (data.length == 0) {
          this.$root.$toast.error('Không tìm thấy thuê bao tương ướng với dịch vụ này.')
          return
        }
        console.log('HienThiTT_DanhBa dichvuvt_id', this.dichvuvt_id)
        console.log('HienThiTT_DanhBa kenh_trang', this.kenh_trang)
        if (this.kenh_trang && (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25)) {
          this.HienThiNutTS_TSL(1)
          console.log('HienThiTT_DanhBa 1')
        } else {
          this.HienThiNutTS_TSL(0)
          console.log('HienThiTT_DanhBa 0')
        }
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          if (this.tinh_cuoi.tinhthicong_id > 0 && this.tinh_dau.tinhthicong_id > 0) {
            if (!this.kenh_trang) {
              this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh_dau.tinhthicong_id, this.formProps.txtMaTB)
              this.thuebao_tc_id_tsl = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh_cuoi.tinhthicong_id, this.formProps.txtMaTB)
              this.dsDBTBBC = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_dau.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.value, 0, 2)
              if (!this.dsDBTBBC || this.dsDBTBBC.length == 0) {
                this.$toast.error('Không lấy được Danh bạ của thuê bao ở tỉnh đầu!')
              }
              this.dsDBTBBC_Temp = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_cuoi.tinhthicong_id, this.thuebao_tc_id_tsl, this.cboDichVuVT.value, 0, 2)
              if (!this.dsDBTBBC_Temp || this.dsDBTBBC_Temp.length == 0) {
                this.$toast.error('Không lấy được Danh bạ của thuê bao ở tỉnh cuối!')
              }
              this.dsDBTB = data
            }
          } else {
            this.thuebao_tc_id = 0
            this.thuebao_tc_id_tsl = 0
            this.dsDBTBBC = []
            this.dsDBTB = data
          }
        } else {
          if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false) {
            data = await this.LAY_DANHBA_THEO_MATB_BC(matb, this.dichvuvt_id, 0)
          } else {
            data = await this.LAY_DANHBA_THEO_MATB(matb, this.dichvuvt_id, 0)
          }
          this.dsDBTB = data
        }
        if (data.length > 0) {
          if (data.length > 1 && this.dichvuvt_id != DichVuVienThong.TSL&&this.dichvuvt_id != 25) {
            this.$refs.popupDSTBCungMa.showModal(data)
            if (this.$refs.popupDSTBCungMa.getDialogResult()) {
              data = this.$refs.popupDSTBCungMa.getDanhSach()
            }
          }
          let kttblhdk = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(matb, '0', this.cboDichVuVT.value, LoaiHopDong.THAY_DOI_DV)
          if (kttblhdk) {
            return
          }
          // Kiểm tra thuê bao này đã ký lại hợp đồng hay chưa
          // Lam thêm ngày 26/08/2010
          // Hiếu zem lại do yêu cầu của tổ danh bạ - 14.10.2010
          if (Number(data[0]['ky_hd']) == 0) {
            this.$root.$toast.warning('Khách hàng có mã thuê bao: ' + this.formProps.txtMaTB + ' chưa ký lại hợp đồng gốc \nĐề nghị ký lại hợp đồng gốc')
            return
          }
          if (data[0]['trangthaitb_id'] == TrangThaiTB.THANHLY) {
            this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang ở trạng thái thanh lý \n Không thể thực hiện thay đổi dịch vụ cho thuê bao này!')
            return
          }
          if (data[0]['trangthaitb_id'] == TrangThaiTB.THANHLY_NO) {
            this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang ở trạng thái thanh lý nợ \n Không thể thực hiện thay đổi dịch vụ cho thuê bao này!')
            return
          }
          if (data[0]['trangthaitb_id'] == TrangThaiTB.DOISO) {
            this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang ở trạng thái đổi số \n Không thể thực hiện thay đổi dịch vụ cho thuê bao này!')
            return
          }
          if (Number(this.cboDichVuVT.value) == DichVuVienThong.ADSL && data[0]['trangthaitb_id'] == TrangThaiTB.KHOA_HAICHIEU_CUONGBUC) {
            this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang ở trạng thái khóa do nợ cước. \n Không thể thực hiện thay đổi dịch vụ cho thuê bao này!')
            return
          }
          this.formProps.txtMaKH = data[0]['ma_kh']
          this.formProps.txtMaHD = data[0]['ma_hd']
          this.khachhang_id = Number(data[0]['khachhang_id'])
          this.formProps.txtTenKH = this.standardizedName(data[0]['ten_kh'])
          this.formProps.txtDiaChiKH = this.standardizedName(data[0]['diachi_kh'])
          if (data[0].hasOwnProperty('ma_duan')) {
            this.formProps.txtMaDuAn = data[0].ma_duan
          }
          // Thông tin thuê bao
          this.thuebao_id = Number(data[0]['thuebao_id'])
          if (this.dichvuvt_id != DichVuVienThong.TSL&&this.dichvuvt_id != 25) {
            if (this.tinh.tinhthicong_id > 0 && this.kenh_trang == false) {
              this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
              this.dsDBTBBC = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh.tinhthicong_id, Number(this.thuebao_tc_id), Number(this.cboDichVuVT.value), 0, 2)
            } else {
              this.thuebao_tc_id = 0
              this.dsDBTBBC = []
            }
          }
          await this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
          this.formProps.txtTenTB = this.standardizedName(data[0]['ten_tb'])
          this.formProps.txtDiaChiTB = this.standardizedName(data[0]['diachi_tb'])
          this.formProps.txtDiaChiLD = this.standardizedName(data[0]['diachi_ld'])
          this.formProps.txtMaTB = data[0]['ma_tb']
          this.loaitb_id = Number(data[0]['loaitb_id'])
          this.cboLoaihinhTB.value = data[0]['loaitb_id']
          this.doituong_id = Number(data[0]['doituong_id'])
          this.donvi_id = Number(data[0]['donviql_id'])
          if (!data[0].ngaylap_hd) {
            this.formProps.dtpNgayLapHD = this.formatDate(new Date(), null)
          } else {
            this.formProps.dtpNgayLapHD = this.formatDate(this.parseDate(data[0].ngaylap_hd), null)
          }
          this.formProps.dtpNgayYeuCau = this.formatDate(new Date(), null)
          this.formProps.txtTrangThaiTB = data[0]['trangthai_tb']
          await this.NapThongTinDichVuThueBao()
          await this.HienThiTTDBMoRong(this.thuebao_id, this.dichvuvt_id)
          if ((this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 26||this.dichvuvt_id == 25) && this.doitac_id != 0) {
            let doitac = await this.fn_tt_doitac(this.doitac_id)
            this.$root.$toast.warning('Thuê bao này có  Hợp đồng/PLHĐ với đối tác ' + doitac)
          }
          let dsdc = await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id)
          this.HienThiTTDiaChi(dsdc, 3)
          this.thongso_ban = this.Reset_Thongso_Thuebao()
          this.thongso_tc = this.Reset_Thongso_Thuebao()
          console.log("data haha", this.dichvuvt_id, this.thuebao_id , typeof this.dichvuvt_id  )
          await this.gan_thongso_theodanhba(1, this.dichvuvt_id, this.thuebao_id)
          if (this.thuebao_tc_id != 0) {
            await this.gan_thongso_theodanhba(2, this.dichvuvt_id, this.thuebao_tc_id)
          }
          this.TinhTong_Tien_Dich_vu(this.dsThueBaoDichVu['danhsach'])
          this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
        } else {
          this.$root.$toast.error('Không tìm thấy thông tin về mã thuê bao : ' + this.formProps.txtMaTB + ' đối với dịch vụ ' + this.cboDichVuVT.list.filter((item) => item.id == this.cboDichVuVT.value)[0].text)
          await this.Clear()
        }
      } catch (error) {
        console.log(error)
      }
    },
    TinhTong_Tien_Dich_vu(ds) {
      let tiendv = 0
      let vatdv = 0
      for (let i = 0; i < ds.length; i++) {
        let item = ds[i]
        if (item.dangky == '1' || (item.sua == '1' && item.huy == '0')) {
          tiendv = tiendv + Number(item['cuoc_ld'])
          vatdv = vatdv + Number(item['vat_ld'])
        }
      }
    },
    async gan_thongso_theodanhba(vkieu, vdichvu, vthuebaoid) {
      let ds = []
      if (vdichvu == 1) {
        if (vkieu == 1) {
          ds = await this.LAY_DS_DB_CD_THEO_TBID(vthuebaoid)
          if (ds.length > 0) {
            this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
            this.thongso_ban.doituong_id = Number(this.dsDBTB[0]['doituong_id'])
            this.thongso_ban.muccuoctb_id = Number(this.dsDBTB[0]['mucuoctb_id'])
          }
        } else {
          ds = await this.LAY_DS_DB_CD_THEO_TBID_BC(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds.length > 0) {
            this.thongso_tc.trangbi_id = Number(ds[0]['trangbi_id'])
            if (this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
              this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
            }
          }
        }
      }
      if (vdichvu == 11) {
        if (vkieu == 1) {
          ds = await this.LAY_DS_DB_IMS_THEO_TBID(vthuebaoid)
          if (ds.length > 0) {
            this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
            this.thongso_ban.doituong_id = Number(this.dsDBTB[0]['doituong_id'])
            this.thongso_ban.muccuoctb_id = Number(this.dsDBTB[0]['mucuoctb_id'])
          }
        } else {
          ds = await this.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds.length > 0) {
            this.thongso_tc.trangbi_id = Number(ds[0]['trangbi_id'])
            if (this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
              this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
            }
          }
        }
      }
      if (vdichvu == 7 || vdichvu == 8 || vdichvu == 26) {
        if (vkieu == 1) {
          ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(vthuebaoid)
          if (ds.length > 0) {
            this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
            this.thongso_ban.tocdo_id = Number(ds[0]['tocdo_id'])
            if (ds[0]['tocdo_pir_id']) {
              this.thongso_ban.tocdo_pir_id = Number(ds[0]['tocdo_pir_id'])
            }
            if (ds[0]['tocdo_isp']) {
              this.thongso_ban.tocdo_isp = Number(ds[0]['tocdo_isp'])
            }
            if (ds[0]['tocdo_nix']) {
              this.thongso_ban.tocdo_nix = Number(ds[0]['tocdo_nix'])
            }
            this.thongso_ban.loaimd_id = Number(ds[0]['loaimd_id'])
            this.thongso_ban.loaikenh_id = Number(ds[0]['loaikenh_id'])
            this.thongso_ban.thoihan_id = Number(ds[0]['thoihan_id'])
            if (Object.keys(ds[0]).includes('muccuoc_id')) {
              if (ds[0]['muccuoc_id']) {
                this.thongso_ban.muccuoc_id = Number(ds[0]['muccuoc_id'])
              }
            }
            if (ds[0]['congnghe_id']) {
              this.thongso_ban.congnghe_id = Number(ds[0]['congnghe_id'])
            }
            this.thongso_ban.thietbidc_id = Number(ds[0]['thietbidc_id'])
            this.thongso_ban.doituong_id = Number(this.dsDBTB[0]['doituong_id'])
            this.thongso_ban.muccuoctb_id = Number(this.dsDBTB[0]['mucuoctb_id'])
          }
        } else {
          ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID_BC(this.tinh.tinhthicong_id, vthuebaoid)
          if (ds.length > 0) {
            this.thongso_tc.trangbi_id = Number(ds[0]['trangbi_id'])
            this.thongso_tc.tocdo_id = Number(ds[0]['tocdo_id'])
            if (ds[0]['tocdo_pir_id']) {
              this.thongso_tc.tocdo_pir_id = Number(ds[0]['tocdo_pir_id'])
            }
            if (ds[0]['tocdo_isp']) {
              this.thongso_tc.tocdo_isp = Number(ds[0]['tocdo_isp'])
            }
            if (ds[0]['tocdo_nix']) {
              this.thongso_tc.tocdo_nix = Number(ds[0]['tocdo_nix'])
            }
            this.thongso_tc.loaimd_id = Number(ds[0]['loaimd_id'])
            this.thongso_tc.loaikenh_id = Number(ds[0]['loaikenh_id'])
            this.thongso_tc.thoihan_id = Number(ds[0]['thoihan_id'])
            if (Object.keys(ds[0]).includes('muccuoc_id')) {
              if (ds[0]['muccuoc_id']) {
                this.thongso_tc.muccuoc_id = Number(ds[0]['muccuoc_id'])
              }
            }
            if (ds[0]['congnghe_id']) {
              this.thongso_tc.congnghe_id = Number(ds[0]['congnghe_id'])
            }
            this.thongso_tc.thietbidc_id = Number(ds[0]['thietbidc_id'])
            if (this.dsDBTBBC.length > 0) {
              this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
              this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
            }
          }
        }
      }
        // dich vu băng rộng
        if (vdichvu == 4) {
          if (vkieu == 1) {
            ds = await this.LAY_DS_DB_ADSL_THEO_TBID(vthuebaoid)
            if (ds.length > 0) {
              this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
              this.thongso_ban.tocdo_id = Number(ds[0]['tocdo_id'])
              this.thongso_ban.tocdothuc_id = Number(ds[0]['tocdo_id'])
              this.thongso_ban.muccuoc_id = Number(ds[0]['muccuoc_id'])
              this.thongso_ban.kieutra_id = Number(ds[0]['kieutra_id'])
              // thongso_ban.congnghe_id = Number(ds[0]["congnghe_id"])
              this.thongso_ban.thietbidc_id = Number(ds[0]['thietbidc_id'])
              this.thongso_ban.doituong_id = Number(this.dsDBTB[0]['doituong_id'])
              this.thongso_ban.muccuoctb_id = Number(this.dsDBTB[0]['mucuoctb_id'])
            }
          } else {
            ds = await this.LAY_DS_DB_ADSL_THEO_TBID_BC(this.tinh.tinhthicong_id, this.thuebao_id)
            if (ds.length > 0) {
              this.thongso_tc.trangbi_id = Number(ds[0]['trangbi_id'])
              this.thongso_tc.tocdo_id = Number(ds[0]['tocdo_id'])
              this.thongso_tc.tocdothuc_id = Number(ds[0]['tocdo_id'])
              this.thongso_tc.muccuoc_id = Number(ds[0]['muccuoc_id'])
              this.thongso_tc.kieutra_id = Number(ds[0]['kieutra_id'])
              // thongso_tc.congnghe_id = Number(ds[0]["congnghe_id"])
              this.thongso_tc.thietbidc_id = Number(ds[0]['thietbidc_id'])
              if (this.dsDBTBBC.length > 0) {
                this.thongso_tc.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
                this.thongso_tc.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
          }
        }
        // TSL
        if (vdichvu == 9 || vdichvu == 25) {
          if (vkieu == 1) {
            ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id)
            if (ds.length) {
              this.thongso_ban.trangbi_id = Number(ds[0]['trangbi_id'])
              this.thongso_ban.tocdo_id = Number(ds[0]['tocdo_id'])
              this.thongso_ban.loaikenh_id = Number(ds[0]['loaikenh_id'])
              this.thongso_ban.chuquan_id = Number(ds[0]['chuquan_id'])
              this.thongso_ban.thietbidc_id = Number(ds[0]['thietbidc_id'])
              this.thongso_ban.thietbidc_id_cuoi = Number(ds[1]['thietbidc_id'])
              this.thongso_ban.doituong_id = Number(this.dsDBTB[0]['doituong_id'])
              this.thongso_ban.muccuoctb_id = Number(this.dsDBTB[0]['mucuoctb_id'])
            }
          } else {
            ds = await this.LAY_DS_DB_TSL_THEO_TBID_BC(this.tinh_dau.tinhthicong_id, this.thuebao_id)
            if (ds.length) {
              this.thongso_tc_dau.trangbi_id = Number(ds[0]['trangbi_id'])
              this.thongso_tc_dau.tocdo_id = Number(ds[0]['tocdo_id'])
              this.thongso_tc_dau.loaikenh_id = Number(ds[0]['loaikenh_id'])
              this.thongso_tc_dau.chuquan_id = Number(ds[0]['chuquan_id'])
              this.thongso_tc_dau.thietbidc_id = Number(ds[0]['thietbidc_id'])
              this.thongso_tc_dau.thietbidc_id_cuoi = Number(ds[1]['thietbidc_id'])
              if (this.dsDBTBBC.length > 0) {
                this.thongso_tc_dau.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
                this.thongso_tc_dau.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
            ds = await this.LAY_DS_DB_TSL_THEO_TBID_BC(this.tinh_cuoi.tinhthicong_id, this.thuebao_id)
            if (ds.length) {
              this.thongso_tc_dau.trangbi_id = Number(ds[0]['trangbi_id'])
              this.thongso_tc_dau.tocdo_id = Number(ds[0]['tocdo_id'])
              this.thongso_tc_dau.loaikenh_id = Number(ds[0]['loaikenh_id'])
              this.thongso_tc_dau.chuquan_id = Number(ds[0]['chuquan_id'])
              this.thongso_tc_dau.thietbidc_id = Number(ds[0]['thietbidc_id'])
              this.thongso_tc_dau.thietbidc_id_cuoi = Number(ds[1]['thietbidc_id'])
              if (this.dsDBTBBC.length > 0) {
                this.thongso_tc_dau.doituong_id = Number(this.dsDBTBBC[0]['doituong_id'])
                this.thongso_tc_dau.muccuoctb_id = Number(this.dsDBTBBC[0]['mucuoctb_id'])
              }
            }
          }
        }
      
    },
    async HienThiTTDBMoRong(thuebaoid, dichvuvtid) {
      // Lấy trang bị _id từ bảng con
      this.formProps.txtMaHD_TTKD = ''
      this.formProps.txtSoAo = ''
      this.formProps.txtHuongKN = ''
      this.kt_kenhphu_ao = false
      this.doitac_id = 0
      let ds = []
      if (dichvuvtid == DichVuVienThong.CO_DINH) {
        ds = await this.LAY_DS_DB_CD_THEO_TBID(thuebaoid)
        if (ds.length > 0) {
          if (ds[0]['trangbi_id']) {
            this.trangbi_id = ds[0]['trangbi_id']
          }
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.IMS) {
        ds = await this.LAY_DS_DB_IMS_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.ADSL) {
        ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          this.formProps.txtSoAo = ds[0]['ma_lt']
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          if (ds[0]['tocdo_id']) this.tocdotn_id = Number(ds[0]['tocdo_id'])
          if (this.loaitb_id != LoaiHinhTB.INTERNET_MYTV && this.loaitb_id != LoaiHinhTB.INTERNET_SGTV && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_ANALOG && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_DIGITAL) {
            if (ds[0]['muccuoc_id']) {
              this.muccuoctn_id = Number(ds[0]['muccuoc_id'])
            } else {
              this.muccuoctn_id = 0
            }
          }
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.MEGA_EYES) {
        ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          this.formProps.txtSoAo = ds[0]['ma_lt']
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          if (ds[0]['tocdo_id']) this.tocdotn_id = Number(ds[0]['tocdo_id'])
          this.muccuoctn_id = 0
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.DI_DONG) {
        ds = await this.LAY_DS_DB_DD_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.MEGAWAN || dichvuvtid == DichVuVienThong.METRONET|| dichvuvtid == 26) {
        ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          this.jloaikenh_id = Number(ds[0]['loaikenh_id'])
          this.vloaikenh_id = Number(ds[0]['loaikenh_id'])
          this.formProps.txtSoAo = ds[0]['ma_lt']
          this.formProps.txtMaHD_TTKD = ds[0]['ma_pl']
          this.formProps.txtHuongKN = ds[0]['huong_kn']
          this.formProps.txtMaERP = ds[0]['id_erp']
          this.formProps.txtMaBSS = ds[0]['ma_bss']
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          if (ds[0]['tocdo_id']) this.tocdotn_id = Number(ds[0]['tocdo_id'])
          this.muccuoctn_id = 0
          this.kt_kenhphu_ao = ds[0]['kp_ao'] == 1
          if (ds[0]['doitac_id']) this.doitac_id = Number(ds[0]['doitac_id'])
        } else {
          this.trangbi_id = 0
        }
      } else if (dichvuvtid == DichVuVienThong.TSL||dichvuvtid == 25) {
        ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          this.vloaikenh_id = Number(ds[0]['loaikenh_id'])
          this.jloaikenh_id = Number(ds[0]['loaikenh_id'])
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
          if (ds[0]['tocdo_id']) this.tocdotn_id = Number(ds[0]['tocdo_id'])
          this.muccuoctn_id = 0
          this.formProps.txtMaHD_TTKD = ds[0]['ma_pl']
          if (ds[0]['doitac_id']) this.doitac_id = Number(ds[0]['doitac_id'])
          this.formProps.txtMaERP = ds[0]['id_erp']
          this.formProps.txtMaBSS = ds[0]['ma_bss']
        } else {
          this.trangbi_id = 0
        }
        let soaodau = ''
        let soaocuoi = ''
        for (let i = 0; i < ds.length; i++) {
          let item = ds[i]
          if (item.daucuoi_id == 1) {
            soaodau = item.ma_lt + ','
            // let donvidauid = await this.fn_tt_db_thuebao(this.thuebao_id, 6)
            // TODO check lai cboVetinhTSL_Dau
            this.formProps.txtDiaChiLD_Dau = item['diachi']
          } else {
            soaocuoi = item['ma_lt']
            this.formProps.txtDiaChiLD_Cuoi = item['diachi']
          }
        }
        this.formProps.txtSoAo = soaodau + soaocuoi
      } else if (dichvuvtid == DichVuVienThong.GPHONE) {
        ds = await this.LAY_DS_DB_GP_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          if (ds[0]['trangbi_id']) this.trangbi_id = Number(ds[0]['trangbi_id'])
        } else {
          this.trangbi_id = 0
        }
        this.tocdotn_id = 0
        this.muccuoctn_id = 0
      } else if (this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
        await this.Init_Properties(this.cboLoaihinhTB.value)
        ds = await this.LAY_DS_DB_CNTT_THEO_TBID(this.thuebao_id)
        if (ds.length > 0) {
          if (ds[0]['linhvuc_id']) {
            this.linhvuc_id = Number(ds[0]['linhvuc_id'])
          } else {
            this.linhvuc_id = 0
          }
          this.formProps.txtMaHD_TTKD = ds[0]['ma_pl']
          if (ds[0]['tocdo_id']) {
            this.tocdotn_id = Number(ds[0]['tocdo_id'])
          } else {
            this.tocdotn_id = 0
          }
          if (ds[0]['muccuoc_id']) {
            this.muccuoctn_id = Number(ds[0]['muccuoc_id'])
          } else {
            this.muccuoctn_id = 0
          }
        } else {
          this.linhvuc_id = 0
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
        }
        await this.NapThongTinDichVuThueBao()
      }
    },
    async fn_tt_doitac(doitacid) {
      return this.GetData(await API.fn_tt_doitac(this.axios, { param: doitacid, type: 1 }))
    },
    async fn_tt_db_thuebao(thuebaoid, type) {
      return this.GetData(await API.fn_tt_db_thuebao(this.axios, { param: thuebaoid, type: type }))
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_MEGAWAN_THEO_TBID(this.axios, thuebaoid))
    },
    async LAY_DS_DB_MEGAWAN_THEO_TBID_BC(tinhthicongid, thuebaoid) {
      let req = {
        thuebao_id: thuebaoid,
        tinhthicong_id: tinhthicongid
      }
      return this.GetDataList(await API.LAY_DS_DB_MEGAWAN_THEO_TBID_BC(this.axios, req))
    },
    async LAY_DS_DB_ADSL_THEO_TBID_BC(tinhthicongid, thuebaoid) {
      let req = {
        thuebao_id: thuebaoid,
        tinhthicong_id: tinhthicongid
      }
      return this.GetDataList(await API.LAY_DS_DB_ADSL_THEO_TBID_BC(this.axios, req))
    },
    async LAY_DS_DB_TSL_THEO_TBID_BC(tinhthicongid, thuebaoid) {
      let req = {
        thuebao_id: thuebaoid,
        tinhthicong_id: tinhthicongid
      }
      return this.GetDataList(await API.LAY_DS_DB_TSL_THEO_TBID_BC(this.axios, req))
    },
    async LAY_DS_DB_CNTT_THEO_TBID(thuebaoid) {
      let req = {
        thuebao_id: thuebaoid
      }
      return this.GetDataList(await API.LAY_DS_DB_CNTT_THEO_TBID(this.axios, req))
    },
    async LAY_DS_DB_ADSL_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_ADSL_THEO_TBID(this.axios, thuebaoid))
    },
    async LAY_DS_DB_TSL_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_TSL_THEO_TBID(this.axios, thuebaoid))
    },
    async LAY_DS_DB_GP_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_GP_THEO_TBID(this.axios, thuebaoid))
    },
    async LAY_DS_DB_DD_THEO_TBID(thuebaoid) {
      let data = {
        thuebao_id: thuebaoid
      }
      return this.GetDataList(await API.LAY_DS_DB_DD_THEO_TBID(this.axios, data))
    },
    async LAY_DS_DB_IMS_THEO_TBID(thuebaoid) {
      let data = {
        thuebao_id: thuebaoid
      }
      return this.GetDataList(await API.LAY_DS_DB_IMS_THEO_TBID(this.axios, data))
    },
    async lay_ds_db_ims_theo_tbid_bc(tinhthicongid, thuebaoid) {
      let req = {
        thuebao_id: thuebaoid,
        tinhthicong_id: tinhthicongid
      }
      return this.GetDataList(await API.lay_ds_db_ims_theo_tbid_bc(this.axios, req))
    },
    async LAY_DS_DB_CD_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_CD_THEO_TBID(this.axios, thuebaoid))
    },
    async LAY_DS_DB_CD_THEO_TBID_BC(tinhtcid, thuebaoid) {
      let req = {
        thuebao_id: thuebaoid,
        tinhthicong_id: tinhtcid
      }
      return this.GetDataList(await API.LAY_DS_DB_CD_THEO_TBID_BC(this.axios, req))
    },
    async SP_LAY_THUEBAOID_THICONG_THEO_MATB(tinhthicongid, matb) {
      let data = this.GetDataList(
        await API.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.axios, {
          vma_tb: matb,
          vtinhthicong: tinhthicongid
        })
      )
      if (data.length > 0) {
        return Number(data[0].thuebao_kn_id)
      } else {
        return 0
      }
    },
    async LAY_THUEBAO_KN_ID_THEO_MATB(matb) {
      return this.GetDataList(await API.LAY_THUEBAO_KN_ID_THEO_MATB(this.axios, matb))
    },
    async txtMaGD_KeyPress() {
      try {
        this.loading(true)
        if (this.formProps.txtMaGD) {
          this.$refs.txtMaGD.focus()
          let dsHD = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.formProps.txtMaGD.trim(), LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI)
          if (dsHD.length > 0) {
            await this.HienThiTTHopDongKH(dsHD)
          } else {
            this.$root.$toast.error('Không tìm thấy thông tin về hợp đồng có mã giao dịch: ' + this.formProps.txtMaGD + '. Bạn hãy kiểm tra lại')
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      } finally {
        this.loading(false)
      }
    },
    chkCTV_CheckedChanged() {
      if (this.chkCTV.checked) {
        let f = this.$refs.frmNguoiGT
        f.nhomlnv_id = NHOM_LNV.CONGTACVIEN
        f.donvi_id = this.donvi_ctv_id
        f.loainv_id = this.loainv_ctv_id
        f.ctv_id = this.ctv_id
        this.flagNguoiGT = 1
        f.ShowDialog()
      }
    },
    chkNguoiGT_CheckedChanged() {
      if (this.chkNguoiGT.checked) {
        let f = this.$refs.frmNguoiGT
        f.nhomlnv_id = NHOM_LNV.GIOITHIEU_HD
        f.donvi_id = this.donvi_nguoi_gt_id
        f.loainv_id = this.loainv_nguoi_gt_id
        f.ctv_id = this.nguoigt_id
        this.flagNguoiGT = 2
        f.ShowDialog()
      }
    },
    chonNhanvien(item) {
      if (!item) {
        return
      }
      if (this.flagNguoiGT == 1) {
        this.formProps.txtCTV = item.ten_ctv
        this.ctv_id = item.ctv_id
        this.loainv_ctv_id = item.loainv_id
        this.donvi_ctv_id = item.donvi_id
      } else {
        this.formProps.txtNguoiGT = item.ten_ctv
        this.nguoigt_id = item.ctv_id
        this.loainv_nguoi_gt_id = item.loainv_id
        this.donvi_nguoi_gt_id = item.donvi_id
      }
    },
    clearTheSpace(str) {
      let st1 = ''
      str = str.trim()
      while (str.length != 0) {
        str += ' '
        let i = str.indexOf(' ')
        st1 += str[0] + str.substring(1, i)
        str = st1.substring(i + 1).trim()
      }
      return st1.trim()
    },
    async HienThiTTHopDongKH(dsHD) {
      try {
        if (dsHD.length > 0) {
          let item = dsHD[0]
          this.hdkh_id = item.hdkh_id
          this.formProps.txtMaGD = item['ma_gd']
          this.formProps.txtMaHD = item['ma_hd']
          this.formProps.txtMaKH = item['ma_kh']
          if (item.khachhang_id) {
            this.khachhang_id = Number(item.khachhang_id)
          }
          this.formProps.txtTenKH = this.standardizedName(item['ten_kh'])
          this.formProps.txtDiaChiKH = this.standardizedName(item['diachi_kh'])
          this.formProps.dtpNgayLapHD = this.formatDate(new Date(item.ngaylap_hd))
          this.formProps.dtpNgayYeuCau = this.formatDate(new Date(item.ngay_cn))
          if (item.hasOwnProperty('ma_duan')) {
            this.formProps.txtMaDuAn = item.ma_duan
          }
          this.chkKHKT3.checked = item['kh_kt3'] == '1'
          if (item.ctv_id) {
            this.chkCTV.checked = true
            this.ctv_id = Number(item.ctv_id)
            this.formProps.txtCTV = await this.FN_MAP_ID('where nhanvien_id = ' + this.ctv_id, 'ten_nv', 'admin.nhanvien')
          } else {
            this.formProps.txtCTV = ''
            this.chkCTV.checked = false
          }
          if (item['nhanviengt_id']) {
            this.chkNguoiGT.checked = true
            this.nguoigt_id = Number(item['nhanviengt_id'])
            this.formProps.txtNguoiGT = await this.FN_MAP_ID('where nhanvien_id = ' + this.nguoigt_id, 'ten_nv', 'admin.nhanvien')
          } else {
            this.formProps.txtNguoiGT = ''
            this.chkNguoiGT.checked = false
          }
          this.setActiveActions(3)
          let dshdkhtemp = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(dshdkhtemp)
          await this.HienThiTTHopDongTB(dshdkhtemp)
        } else {
          this.setActiveActions(0)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async HienThiDanhSacHDTB(dshdkh) {
      let tongtienhd = 0
      let tongtientb = 0
      this.lvwThueBao.data = []
      if (dshdkh.length > 0) {
        for (let i = 0; i < dshdkh.length; i++) {
          let item = dshdkh[i]
          if (item.tthd_id == TrangThaiHD.MOI) {
            tongtientb = Number(item.tongtien)
            tongtienhd += tongtientb
            let listItem = {
              text: item.ma_tb,
              id: item.hdtb_id
            }
            tongtienhd += tongtientb
            this.lvwThueBao.data.push(listItem)
          }
        }
      }
      if (this.lvwThueBao.data.length <= 1) {
        this.setActiveButton('tsbtnXoaTB', false)
      } else {
        this.setActiveButton('tsbtnXoaTB', true)
      }
      let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(this.hdtb_id)
      await this.HienThiTTHopDongTB(ds)
      this.formProps.txtTongTienHD = tongtienhd
    },
    async HienThiTTHopDongTB(dshdkh) {
      try {
        if (dshdkh.length > 0) {
          let item = dshdkh[0]
          this.hdtb_id = Number(item.hdtb_id)
          if (item['thuebao_id']) {
            this.thuebao_id = Number(item['thuebao_id'])
          }
          this.formProps.txtMaTB = item['ma_tb']
          this.formProps.txtTenTB = this.standardizedName(item['ten_tb'])
          this.formProps.txtDiaChiLD = this.standardizedName(item['diachi_ld'])
          this.formProps.txtDiaChiTB = this.standardizedName(item['diachi_tb'])
          this.dichvuvt_id = Number(item['dichvuvt_id'])
          this.cboDichVuVT.value = this.dichvuvt_id
          this.formProps.txtMaHD_TTKD = ''
          this.formProps.txtSoAo = ''
          this.formProps.txtHuongKN = ''
          this.tocdotn_id = 0
          this.muccuoctn_id = 0
          this.dsDBTB = dshdkh
          let dsCon = []
          if (this.dichvuvt_id == DICHVU_VIENTHONG.ADSL) {
            dsCon = await this.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(this.hdtb_id)
            if (dsCon.length > 0) {
              this.formProps.txtSoAo = dsCon[0].ma_lt
            }
            if (dsCon.length > 0 && dsCon[0].tocdo_id) {
              this.tocdotn_id = Number(dsCon[0].tocdo_id)
            }
            if (this.loaitb_id != LoaiHinhTB.INTERNET_MYTV && this.loaitb_id != LoaiHinhTB.INTERNET_SGTV && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_ANALOG && this.loaitb_id != LoaiHinhTB.HTVC_FIBERVNN_DIGITAL) {
              if (dsCon.length > 0 && dsCon[0].muccuoc_id) {
                this.muccuoctn_id = Number(dsCon[0].muccuoc_id)
              }
            } else {
              this.muccuoctn_id = 0
            }
          }
          if (this.kenh_trang && (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25)) {
            this.HienThiNutTS_TSL(1)
          } else {
            this.HienThiNutTS_TSL(0)
          }
          if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET||this.dichvuvt_id==26) {
            this.kt_kenhphu_ao = false
            dsCon = await this.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(this.hdtb_id)
            if (dsCon.length > 0) {
              this.vloaikenh_id = Number(dsCon[0]['loaikenh_id'])
              this.jloaikenh_id = Number(dsCon[0]['loaikenh_id'])
              this.formProps.txtMaHD_TTKD = dsCon[0]['ma_pl']
              this.formProps.txtSoAo = dsCon[0]['ma_lt']
              this.formProps.txtHuongKN = dsCon[0]['huong_kn']
              this.formProps.txtMaERP = dsCon[0]['id_erp']
              this.formProps.txtMaBSS = dsCon[0]['ma_bss']
              if (dsCon[0].tocdo_id) {
                this.tocdotn_id = Number(dsCon[0].tocdo_id)
              }
              this.muccuoctn_id = 0
              this.kt_kenhphu_ao = dsCon[0]['kp_ao'] == '1'
            }
          }
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25) {
            dsCon = await this.LAY_DS_HDTB_TSL_THEO_HDTB_ID(this.hdtb_id)
            if (dsCon.length > 0) {
              this.formProps.txtMaHD_TTKD = dsCon[0]['ma_pl']
              this.formProps.vloaikenh_id = Number(dsCon[0]['loaikenh_id'])
              this.formProps.jloaikenh_id = Number(dsCon[0]['loaikenh_id'])
              this.formProps.txtMaERP = dsCon[0]['id_erp']
              this.formProps.txtMaBSS = dsCon[0]['ma_bss']
              let soaodau = ''
              let soaocuoi = ''
              for (let i = 0; i < dsCon.length; i++) {
                let item = dsCon[0]
                if (item['daucuoi_id'] == 1) {
                  // Nếu là điểm đầu
                  soaodau = item['ma_lt'] + ','
                } else {
                  soaocuoi = item['ma_lt']
                }
              }
              this.formProps.txtSoAo = soaodau + soaocuoi
              if (item['tocdo_id']) {
                this.tocdotn_id = Number(item['tocdo_id'])
              }
              this.muccuoctn_id = 0
            }
          } else if (this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH) {
            // Dich vu CNTT
            await this.Init_Properties(Number(this.cboLoaihinhTB.value))
            dsCon = await this.LAY_DS_HDTB_CNTT_THEO_HDTB_ID(this.hdtb_id)
            if (dsCon.length > 0) {
              if (dsCon[0].linhvuc_id) {
                this.linhvuc_id = Number(dsCon[0].linhvuc_id)
              } else {
                this.linhvuc_id = 0
              }
            } else {
              this.linhvuc_id = 0
            }
            this.tocdotn_id = 0
            this.muccuoctn_id = 0
          }
          let dsTrangThai = await this.LAY_DANHBA_THEO_MATB(this.formProps.txtMaTB.trim(), this.dichvuvt_id, 0)
          if (dsTrangThai.length > 0) {
            this.formProps.txtTrangThaiTB = dsTrangThai[0]['trangthai_tb']
          } else {
            this.formProps.txtTrangThaiTB = ''
          }
          this.loaitb_id = Number(dshdkh[0]['loaitb_id'])
          this.loaisochinh = 0
          if (dshdkh[0]['hdtb_cha_id']) {
            this.hdtb_id_cha = Number(dshdkh['hdtb_cha_id'])
            this.loaisochinh = 1
            this.isdn_sochinh = false
          }
          if (this.loaitb_id == LoaiHinhTB.PABX) {
            await this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
          }
          this.cboLoaihinhTB.value = dshdkh[0]['loaitb_id']
          this.cboKieuLD.value = dshdkh[0]['kieuld_id']
          await this.cboKieuLD_SelectedValueChanged()
          this.doituong_id = Number(dshdkh[0]['doituong_id'])
          this.donvi_id = Number(dshdkh[0]['donvi_id'])
          this.formProps.txtGhiChu = this.standardizedName(dshdkh[0]['ghichu'])
          if (!dshdkh[0].ngay_hh_td) {
            this.chkNgay_HHTD.checked = false
            this.formProps.dtpNgayHH_TD = this.formatDate(new Date(), null)
            this.disabledBox.dtpNgayHH_TD = true
          } else {
            this.chkNgay_HHTD.checked = true
            this.chkNgay_HHTD.disabled = false
            this.formProps.dtpNgayHH_TD = dshdkh[0].ngay_hh_td
            this.disabledBox.dtpNgayHH_TD = false
          }
          this.HienThiNutTS_TSL(0)
          if (this.thuebao_id > 0) {
            if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25) {
              let dt = await this.LAY_TINHTC_ID_TSL(this.formProps.txtMaTB, this.dichvuvt_id)
              if (dt.length > 0) {
                this.kenh_trang = false
                let tinhdauid = dt.filter((item) => item.daucuoi_id == 1)[0].tinh_thicong
                let tinhcuoiid = dt.filter((item) => item.daucuoi_id == 2)[0].tinh_thicong
                this.tinh_dau = await this.GetTinhTC(tinhdauid)
                this.tinh_cuoi = await this.GetTinhTC(tinhcuoiid)
                this.thuebao_tc_id = dt.filter((item) => item.daucuoi_id == 1)[0]['thuebao_kn_id']
                this.thuebao_tc_id_tsl = dt.filter((item) => item.daucuoi_id == 2)[0]['thuebao_kn_id']
                if (this.tinh != null) {
                  this.tinh.tinhthicong_id = 0
                }
              } else {
                this.HienThiNutTS_TSL(1)
                this.kenh_trang = true
                let dt = await this.GET_HDTB_TSL_BY_HDTB_ID(this.hdtb_id)
                if (dt.length > 0) {
                  try{
                    let tinhdauid = dt.filter((item) => item.daucuoi_id == 1)[0].tinh_id
                    let tinhcuoiid = dt.filter((item) => item.daucuoi_id == 2)[0].tinh_id
                    this.tinh_dau = await this.GetTinhTC(tinhdauid)
                    this.tinh_cuoi = await this.GetTinhTC(tinhcuoiid)
                  }
                  catch (e){
                    console.log(e);
                  }
                  this.thuebao_tc_id_tsl = 0
                  this.thuebao_tc_id = 0
                } else {
                  this.$root.$toast.error('Không lấy được tỉnh thi công TSL')
                }
              }
            } else {
              let tinh = await this.LAY_TINH_THEO_THUEBAOID(this.thuebao_id)
              if (tinh.length > 0) {
                this.tinh = await this.GetTinhTC(tinh[0].tinh_thicong)
              } else {
                this.tinh = {}
                this.tinh.tinhthicong_id = 0
              }
            }
            if (this.dichvuvt_id != DichVuVienThong.TSL&&this.dichvuvt_id!=25) {
              try {
                let dt = await this.LAY_KIEULD_HDBC_THEO_HDTB_ID(this.hdtb_id)
                if (dt.length > 0) {
                  this.cboKieuLD_TC.value = Number(dt[0].kieuld_id)
                  console.log('LAY_KIEULD_HDBC_THEO_HDTB_ID')
                }
              } catch (error) {
                console.log(error)
              }
              this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
              if (this.tinh.tinhthicong_id > 0) {
                this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
                this.dsDBTBBC = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh.tinhthicong_id, Number(this.thuebao_tc_id), Number(this.cboDichVuVT.value), 0, 2)
              } else {
                this.tinh = this.GetTinhTC(dshdkh[0].tinh_thicong)
                this.thuebao_tc_id = 0
                this.dsDBTBBC = []
              }
            } else {
              let dt = await this.lay_kieuld_daucuoi_hdbc_theo_hdtb_id(this.hdtb_id)
              if (dt.length > 0) {
                if (dt.filter((item) => item.daucuoi_id == 1).length > 0) {
                  this.cboKieuLD_TC.value = Number(dt.filter((item) => item.daucuoi_id == 1)[0].kieuld_id)
                }
                if (dt.filter((item) => item.daucuoi_id == 2).length > 0) {
                  this.cboKieuLD_TC_TSL.value = Number(dt.filter((item) => item.daucuoi_id == 2)[0].kieuld_id)
                }
              }
              if (this.tinh_cuoi.tinhthicong_id > 0 && !this.kenh_trang) {
                this.dsDBTBBC = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_dau.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.value, 0, 2)
                this.dsDBTBBC_Temp = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_cuoi.tinhthicong_id, this.thuebao_tc_id_tsl, this.cboDichVuVT.value, 0, 2)
              } else {
                this.dsDBTBBC = []
                this.dsDBTBBC_Temp = []
              }
            }
          }
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25) {
            let data1 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_dau.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data1.length > 0) {
              this.cboKieuLD_TC.list = data1.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
            } else {
              this.cboKieuLD_TC.list = []
              this.cboKieuLD_TC.value = 0
            }
            let data2 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_cuoi.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data2.length > 0) {
              this.cboKieuLD_TC_TSL.list = data2.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              this.cboKieuLD_TC_TSL.value = this.cboKieuLD_TC_TSL.list[0].id
            } else {
              this.cboKieuLD_TC_TSL.list = []
              this.cboKieuLD_TC_TSL.value = 0
            }
          } else {
            let data = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
            if (data.length > 0) {
              this.cboKieuLD_TC.list = data.map((item) => {
                return {
                  id: item.kieuld_id,
                  text: item.ten_kieuld
                }
              })
              //this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
            } else {
              this.cboKieuLD_TC.list = []
              this.cboKieuLD_TC.value = 0
            }
          }
          await this.NapThongTinDichVuThueBao()
          await this.LAY_DS_LYDOHUY()
          await this.HienThiDS_LyDoHuy(this.dsDKHUY)
          let dsDC = await this.LAY_DS_DIACHI_THEO_DBTBID(this.thuebao_id)
          this.HienThiTTDiaChi(dsDC, 3)
          this.dsTienKhoanMuc = await this.LAY_TIENHD_KM_THEO_HDTB(this.hdtb_id)
          for (let i = 0; i < this.dsTienKhoanMuc.length; i++) {
            let item = this.dsTienKhoanMuc[i]
            if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_TDDICHVU) {
              this.dtienld = parseFloat(item['tien'])
              this.dvatld = parseFloat(item['vat'])
            }
            if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
              this.dtienld = parseFloat(item['tien'])
              this.dvatld = parseFloat(item['vat'])
            }
          }
          this.LayTongTien_HDTB()
        }
      } catch (error) {
        console.log(error)
      }
    },
    HienThiTTDiaChi(dsDc, kieu) {
      if (dsDc.length > 0) {
        if (kieu == 3) {
          if (dsDc[0]['phuongld_id']) this.phuongld_id = Number(dsDc[0]['phuongld_id'])
          if (dsDc[0]['phold_id']) this.phold_id = Number(dsDc[0]['phold_id'])
          if (dsDc[0]['apld_id']) this.apld_id = Number(dsDc[0]['apld_id'])
          if (dsDc[0]['khuld_id']) this.khuld_id = Number(dsDc[0]['khuld_id'])
        }
      }
    },
    async LAY_TIENHD_KM_THEO_HDTB(hdtbid) {
      return this.GetDataList(
        await API.LAY_TIENHD_KM_THEO_HDTB(this.axios, {
          hdtb_id: hdtbid
        })
      )
    },
    async HienThiDS_LyDoHuy(ds) {
      this.lvLyDoHuy.data = []
      let dsHuy = []
      for (let i = 0; i < ds.length; i++) {
        let lydohuyid = ds[i].lydohuy_id ? ds[i].lydohuy_id : ds[i].LYDOHUY_ID
        let data = await this.TTCT_LyDoHuy(lydohuyid)
        let item = {
          id: lydohuyid,
          text: data[0].lydohuy
        }
        dsHuy.push(item)
      }
      this.lvLyDoHuy.data = dsHuy
    },
    async TTCT_LyDoHuy(lydohuyid) {
      return this.GetDataList(await API.TTCT_LyDoHuy(this.axios, lydohuyid))
    },
    async LAY_DS_LYDOHUY() {
      this.dsTemp = await this.LAY_DS_DK_LYDOHUY(this.hdtb_id)
      this.dsDKHUY = []
      for (let i = 0; i < this.dsTemp.length; i++) {
        let item = this.dsTemp[i]
        let row = {}
        row.HDTB_ID = Number(item['hdtb_id'])
        row.LYDOHUY_ID = Number(item['lydohuy_id'])
        row.NOIDUNG = item['noidung']
        row.NGAY_YC = item['ngay_yc']
        this.dsDKHUY.push(row)
      }
    },
    async LAY_TINHTHICONG_THEO_MATB(matb) {
      let tinh = TinhTC
      try {
        let dt = await this.LAY_TINH_THEO_MATB(matb)
        if (dt.length > 0) {
          tinh = await this.GetTinhTC(dt[0].tinh_thicong)
        } else {
          tinh.tinhthicong_id = 0
        }
      } catch (error) {
        console.log(error)
      }
      return tinh
    },
    async SP_HT_KIEULD_LOAITB_BANCHEO(tinhthicongid, loaiid, loaitb) {
      let req = { tinhthicong_id: tinhthicongid, loaihd: loaiid, loaitb: loaitb }
      return this.GetDataList(await API.SP_HT_KIEULD_LOAITB_BANCHEO(this.axios, req))
    },
    async lay_kieuld_daucuoi_hdbc_theo_hdtb_id() {
      return this.GetDataList(await API.lay_kieuld_daucuoi_hdbc_theo_hdtb_id(this.axios,this.hdtb_id))
    },
    async LAY_KIEULD_HDBC_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(await API.LAY_KIEULD_HDBC_THEO_HDTB_ID(this.axios, hdtbid))
    },
    async LAY_TINH_THEO_THUEBAOID(thuebaoid) {
      return this.GetDataList(await API.LAY_TINH_THEO_THUEBAOID(this.axios, thuebaoid))
    },
    async LAY_TINH_THEO_MATB(matb) {
      return this.GetDataList(await API.LAY_TINH_THEO_MATB(this.axios, matb))
    },
    async GET_HDTB_TSL_BY_HDTB_ID(hdtbid) {
      return this.GetDataList(await API.GET_HDTB_TSL_BY_HDTB_ID(this.axios, { vhdtb_id: hdtbid }))
    },
    Reset_Thongso_Thuebao() {
      let thongsotb = ThongSoThueBao
      thongsotb.trangbi_id = 0
      thongsotb.muccuoc_id = 0
      thongsotb.chuquan_id = 0
      thongsotb.congnghe_id = 0
      thongsotb.doituong_id = 0
      thongsotb.kieuld_id = 0
      thongsotb.kieutra_id = 0
      thongsotb.loaikenh_id = 0
      thongsotb.loaimd_id = 0
      thongsotb.muccuoctb_id = 0
      thongsotb.thietbidc_id = 0
      thongsotb.thoihan_id = 0
      thongsotb.tocdo_id = 0
      thongsotb.tocdo_isp = 0
      thongsotb.tocdo_pir_id = 0
      thongsotb.tocdothuc_id = 0
      thongsotb.trangbi_id = 0
      thongsotb.ma_doicap = ''
      thongsotb.ma_tn = ''
      return thongsotb
    },
    async closeChonTinh(tinh) {
      try {
        this.loading(true)
        this.tinh = tinh
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          let data1 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_dau.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data1.length > 0) {
            this.cboKieuLD_TC.list = data1.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
          } else {
            this.cboKieuLD_TC.list = []
            this.cboKieuLD_TC.value = 0
          }
          let data2 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_cuoi.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data2.length > 0) {
            this.cboKieuLD_TC_TSL.list = data2.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC_TSL.value = this.cboKieuLD_TC_TSL.list[0].id
          } else {
            this.cboKieuLD_TC_TSL.list = []
            this.cboKieuLD_TC_TSL.value = 0
          }
        } else {
          let data = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data.length > 0) {
            this.cboKieuLD_TC.list = data.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
          } else {
            this.cboKieuLD_TC.list = []
            this.cboKieuLD_TC.value = 0
          }
          this.cboKieuLD_TC_TSL.list = []
        }
        let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.formProps.txtMaTB, LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI, this.dichvuvt_id, 0)
        if (ds.length > 0) {
          await this.HienThiTTHopDongKH(ds)
        } else {
          await this.HienThiTT_DanhBa(this.formProps.txtMaTB)
        }
        if (this.cboKieuLD.value == KieuLapDat.TAMDUNG_SD_MEGA && this.formProps.txtTenTB) {
          await this.HienCanhBaoThietHai()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async chonTinhTLSAccept(tinhdau, tinhcuoi) {
      try {
        this.loading(true)
        this.tinh_dau = tinhdau
        this.tinh_cuoi = tinhcuoi
        this.thuebao_tc_id = 0
        this.thuebao_tc_id_tsl = 0
        if (this.tinh != null) this.tinh.tinhthicong_id = 0
        if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id==25) {
          let data1 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_dau.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data1.length > 0) {
            this.cboKieuLD_TC.list = data1.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
          } else {
            this.cboKieuLD_TC.list = []
            this.cboKieuLD_TC.value = 0
          }
          let data2 = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh_cuoi.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data2.length > 0) {
            this.cboKieuLD_TC_TSL.list = data2.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC_TSL.value = this.cboKieuLD_TC_TSL.list[0].id
          } else {
            this.cboKieuLD_TC_TSL.list = []
            this.cboKieuLD_TC_TSL.value = 0
          }
        } else {
          let data = await this.SP_HT_KIEULD_LOAITB_BANCHEO(this.tinh.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value)
          if (data.length > 0) {
            this.cboKieuLD_TC.list = data.map((item) => {
              return {
                id: item.kieuld_id,
                text: item.ten_kieuld
              }
            })
            this.cboKieuLD_TC.value = this.cboKieuLD_TC.list[0].id
          } else {
            this.cboKieuLD_TC.list = []
            this.cboKieuLD_TC.value = 0
          }
          this.cboKieuLD_TC_TSL.list = []
        }
        let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.formProps.txtMaTB, LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI, this.dichvuvt_id, 0)
        if (ds.length > 0) {
          await this.HienThiTTHopDongKH(ds)
        } else {
          await this.HienThiTT_DanhBa(this.formProps.txtMaTB)
        }
        if (this.cboKieuLD.value == KieuLapDat.TAMDUNG_SD_MEGA && this.formProps.txtTenTB) {
          await this.HienCanhBaoThietHai()
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    async GetTinhTC(tinhid) {
      let data = this.GetDataList(await API.TINH_THICONG(this.axios, { tinhthicong_id: tinhid }))
      let tinh = {}
      if (data.length > 0) {
        let item = data[0]
        tinh.user = item['ten_truycap']
        tinh.pass = item['mat_khau']
        tinh.nhanvien_id = Number(item['nhanvien_id'])
        tinh.donvi_id = Number(item['donvi_id'])
        tinh.matinh = item['matinh']
        tinh.TNS = item['tns_string']
        tinh.tinhthicong_id = Number(item['tinh_id'])
        tinh.mavung = item['ma_vung']
        tinh.ip_dll = item['ip_dll']
      } else {
        tinh.user = ''
        tinh.pass = ''
        tinh.nhanvien_id = 0
        tinh.donvi_id = 0
        tinh.matinh = ''
        tinh.TNS = ''
        tinh.tinhthicong_id = 0
        tinh.ip_dll = ''
      }
      return tinh
    },
    async LAY_DS_ISDN_THEO_THUEBAO_ID(thuebaoid) {
      try {
        this.isdn_sochinh = false
        this.matb_isdn_chinh = ''
        this.ds_isdn = this.GetDataList(
          await API.LAY_DS_ISDN_THEO_THUEBAO_ID(this.axios, {
            thuebao_id: thuebaoid
          })
        )
        for (let i = 0; i < this.ds_isdn.length; i++) {
          if (Number(this.ds_isdn[i]['thuebao_id']) == thuebaoid) {
            if (this.ds_isdn[i]['thuebao_cha_id'] == '') {
              this.isdn_sochinh = true
            }
          }
          if (this.ds_isdn[i]['thuebao_cha_id'] == '') {
            this.matb_isdn_chinh = this.ds_isdn[i]['ma_tb']
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async LAY_DANHBA_THEO_MATB(matb, dichvuvtid, indonvidlid) {
      const req = {
        in_dichvuvt_id: dichvuvtid,
        in_donvi_dl_id: indonvidlid,
        in_ma_tb: matb
      }
      return this.GetDataList(await API.LAY_DANHBA_THEO_MATB(this.axios, req))
    },
    async LAY_DANHBA_THEO_MATB_BC(matb, dichvuvtid, indonvidlid) {
      let req = {
        ma_tb: matb,
        dichvuvt_id: dichvuvtid,
        donvi_dl_id: indonvidlid,
        loaihd_bancheo: 1
      }
      return this.GetDataList(await API.LAY_DANHBA_THEO_MATB_BC(this.axios, req))
    },
    async LAY_DS_HDTB_CNTT_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(
        await API.LAY_DS_HDTB_CNTT_THEO_HDTB_ID(this.axios, {
          in_hdtb_id: hdtbid
        })
      )
    },
    async Init_Properties(vloaitbid) {
      try {
        let ds = await this.Lay_danhsach_thuoctinh_v2(vloaitbid)
        this.ItemList = [ds.length]
        if (this.ItemList.length <= 0) return
        for (let i = 0; i < ds.length; i++) {
          let row = ds[i]
          if (!this.ItemList[i]) this.ItemList[i] = {}
          this.ItemList[i].sFieldName = row['field_name']
          this.ItemList[i].sCaption = row['ten_hienthi']
          this.ItemList[i].iAtt = row['att']
          this.ItemList[i].iDataType = row['data_type']
          this.ItemList[i].sMaxValue = row['max_value']
          this.ItemList[i].sMinvalue = row['min_value']
          this.ItemList[i].sValue = row['field_value']
          this.ItemList[i].nFieldLength = row['field_length'] ? 0 : row['field_length']
          this.ItemList[i].bIsNotNull = row['field_nullable'] ? false : row['field_nullable'] == '0'
          this.ItemList[i].bIsUnique = row['field_unique'] ? false : row['field_unique'] != '0'
          this.ItemList[i].sLookUpTableName = row['parent_table']
          this.ItemList[i].sLookUpKeyField = row['parent_keyfield']
          this.ItemList[i].sLookUpValueField = row['parent_descfield']
          this.ItemList[i].strDepandField = row['depend_field']
          this.ItemList[i].StrSql = row['lookup_sql']
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async Lay_danhsach_thuoctinh_v2(vloaitbid) {
      return this.GetDataList(await API.Lay_danhsach_thuoctinh_v2(this.axios, { vloaitb_id: vloaitbid }))
    },
    async LAY_DS_HDTB_TSL_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(await API.LAY_DS_HDTB_TSL_THEO_HDTB_ID(this.axios, hdtbid))
    },
    async LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(
        await API.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID(this.axios, {
          in_hdtb_id: hdtbid
        })
      )
    },
    async LAY_TINHTC_ID_TSL(matb, dichvuvtid) {
      const req = {
        ma_tb: matb,
        dichvuvt_id: dichvuvtid,
        tinhthicong_id:this.$root.token.getPhanVungID()
      }
      return this.GetDataList(await API.LAY_TINHTC_ID_TSL(this.axios, req))
    },
    // TODO nguoc param
    async LAY_THUEBAOID_THICONG(tinhtcid, thuebaoid) {
      const req = {
        // 'tinhthicong_id': tinhtcid,
        // 'tinh_id': tinhtcid,
        // 'thuebao_id': thuebaoid
        vtinh_id: tinhtcid,
        vthuebao_id: thuebaoid
      }
      let data = this.GetDataList(await API.LAY_THUEBAOID_THICONG(this.axios, req))
      if (data && data.length > 0) {
        return Number(data[0].thuebao_id_thicong)
      } else return 0
    },
    async LAY_DANHBA_THEO_THUEBAOID(tinhthicongid, thuebaoid, dichvuvtid, donviid, loaihdbc) {
      const req = {
        tinhthicong_id: tinhthicongid,
        thuebao_id: thuebaoid,
        dichvuvt_id: dichvuvtid,
        donvi_id: donviid,
        loaihdbc: loaihdbc
      }
      return this.GetDataList(await API.LAY_DANHBA_THEO_THUEBAOID(this.axios, req))
    },
    async LAY_DS_DK_LYDOHUY(hdtbid) {
      const req = {
        in_hdtb_id: hdtbid
      }
      return this.GetDataList(await API.LAY_DS_DK_LYDOHUY(this.axios, req))
    },
    async LAY_DS_DIACHI_THEO_DBTBID(thuebaoid) {
      const req = { in_thuebao_id: thuebaoid }
      return this.GetDataList(await API.LAY_DS_DIACHI_THEO_DBTBID(this.axios, req))
    },
    async LAY_DS_DANGKY_DVK(hdtbid, loaiid) {
      let request = {
        hd_id: this.hdtb_id,
        loaiid: loaiid
      }
      return this.GetDataList(await API.LAY_DS_DANGKY_DVK(this.axios, request))
    },
    async LAY_DS_HOPDONG_THEO_MA_TB(matb, loaihdid, tthdid, dichvuvtid, loaitbid) {
      const request = {
        in_ma_tb: matb,
        in_loaihd_id: loaihdid,
        in_donvi_id: this.donviId,
        in_tthd_id: tthdid,
        in_nhanvien_id: this.nhanvienId,
        in_donvi_dl_id: 0,
        in_dichvuvt_id: dichvuvtid,
        in_loaitb_id: loaitbid
      }
      return this.GetDataList(await API.LAY_DS_HOPDONG_THEO_MA_TB(this.axios, request))
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(matb, magd, dichvuvtid, loaihdid) {
      let result = false
      await API.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.axios, {
        loaihd_id: loaihdid,
        ma_gd: magd,
        dichvuvt_id: dichvuvtid,
        ma_tb: matb
      }).then((response) => {
        if (response && response.data && response.data.error_code == '0' && response.data.data) {
          if (response.data.data.length > 0) {
            result = true
            let tenLoaiHd = response.data.data[0].ten_loaihd
            let maGd = response.data.data[0].ma_gd
            let nguoiCn = response.data.data[0].nguoi_cn
            let tenDV = response.data.data[0].ten_dv
            let ngayYc = response.data.data[0].ngay_yc
            this.$root.$toast.warning(`Số máy/Acc: ${matb} đang được lập bởi hợp đồng (phụ lục) ${tenLoaiHd} có mã giao dịch ${maGd} do User ${nguoiCn} thuộc đơn vị ${tenDV} thực hiện vào ngày ${ngayYc}`)
          }
        }
      })
      return result
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC_2(matb, magd, dichvuvtid, loaihdid) {
      return this.GetDataList(
        await API.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.axios, {
          loaihd_id: loaihdid,
          ma_gd: magd,
          dichvuvt_id: dichvuvtid,
          ma_tb: matb
        })
      )
    },
    btnDiemDau_Click() {
      try {
        if (this.tinh_dau.tinhthicong_id == 0) {
          this.$root.$toast.error('Chưa có thuê bao')
          return
        }
        let loaitbid = this.cboLoaihinhTB.value
        this.flagThongSoTSL = 1
        this.$refs.popupThongSoTSL.openDialog(this.tinh_dau, loaitbid, loaitbid, this.cboKieuLD.value, this.thongso_ban, this.thongso_tc_dau, LoaiHopDong.THAY_DOI_DV, this.kenh_trang)
      } catch (error) {
        console.log(error)
      }
    },
    chapnhanThongSoTSL(data) {
      if (data) {
        this.thongso_ban = data.thongso_Ban
        if (this.flagThongSoTSL == 1) {
          this.thongso_tc_dau = data.thongso_TC
          this.Kieuld_tc_id = this.thongso_tc_dau.kieuld_id
        } else {
          this.thongso_tc_cuoi = data.thongso_TC
          this.Kieuld_tc_id_tsl = this.thongso_tc_cuoi.kieuld_id
        }
      }
    },
    btnDiemCuoi_Click() {
      try {
        if (this.tinh_cuoi.tinhthicong_id == 0) {
          this.$root.$toast.error('Chưa có thuê bao')
          return
        }
        let loaitbid = this.cboLoaihinhTB.value
        this.flagThongSoTSL = 2
        this.$refs.popupThongSoTSL.openDialog(this.tinh_cuoi, loaitbid, loaitbid, this.cboKieuLD.value, this.thongso_ban, this.thongso_tc_cuoi, LoaiHopDong.THAY_DOI_DV, this.kenh_trang)
      } catch (error) {
        console.log(error)
      }
    },
    HienThiNutTS_TSL(kieu) {
      // 1 la hien thi. 0 la an
      if (kieu == 1) {
        this.showThongSoTc = true
        this.btnDiemDau.visible = true
        this.btnDiemCuoi.visible = true
      } else {
        this.showThongSoTc = false
        this.btnDiemDau.visible = false
        this.btnDiemCuoi.visible = false
      }
    },
    async LAY_DS_HDTB_ADSL_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(await API.LAY_DS_HDTB_ADSL_THEO_HDTB_ID(this.axios, hdtbid))
    },
    async LAY_DS_HOPDONG_TB_THEO_HDKH_ID(hdkhid) {
      return this.GetDataList(await API.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.axios, hdkhid))
    },
    async FN_MAP_ID(dk, idNeo, table) {
      let result = ''
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table
      }
      let data = this.GetData(await API.FN_MAP_ID(this.axios, req))
      if (data) {
        result = data
      }
      return result
    },
    async LAY_DS_HOPDONG_THEO_MA_GD(maGd, loaihdId, tthdId) {
      const request = {
        in_loaihd_id: loaihdId,
        in_ma_gd: maGd,
        in_tthd_id: tthdId,
        in_donvi_dl_id: 0
      }
      return this.GetDataList(await API.LAY_DS_HOPDONG_THEO_MA_GD(this.axios, request))
    },
    async cboDichVuVT_SelectedValueChanged(newValue) {
      try {
        this.loading(true)
        if (this.cboDichVuVT.list.length > 0) {
          if (this.cboDichVuVT.value == 0) {
            return
          }
          this.dichvuvt_id = Number(this.cboDichVuVT.value)
          await this.CSS_LOAIHINH_TB(this.dichvuvt_id)
          this.disabledBox.txtMaHD_TTKD = true
          if (newValue == DichVuVienThong.CO_DINH) {
            this.cboLoaihinhTB.value = LoaiHinhTB.DIENTHOAI_CD
          } else if (newValue == DichVuVienThong.ADSL) {
            this.cboLoaihinhTB.value = LoaiHinhTB.MEGA
          } else if (newValue == DichVuVienThong.TSL) {
            this.cboLoaihinhTB.value = LoaiHinhTB.LEASEDLINE
          } else if (newValue == DichVuVienThong.MEGAWAN) {
            this.cboLoaihinhTB.value = LoaiHinhTB.MEGAWAN_ADSL
          } else if (newValue == DichVuVienThong.DI_DONG) {
            this.cboLoaihinhTB.value = LoaiHinhTB.DIDONGTRASAU
          }
          if (newValue == DichVuVienThong.TSL||newValue==25) {
            this.showTabHeader = true
            this.lineLabel7 = 'Danh sách dịch vụ gia tăng thi công điểm đầu'
            this.lineLabel9 = 'Danh sách dịch vụ gia tăng thi công điểm cuối'
          } else {
            this.showTabHeader = false
            this.lineLabel7 = 'Danh sách dịch vụ gia tăng thi công'
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    CloseHienThiChonThongSo(data) {
      if (data) {
        this.thongso_ban = data.ThongsoBan
        this.thongso_tc = data.ThongsoTc
      }
    },
    acceptTocDoMucCuoc(data) {
      this.$refs.TocDoMucCuocDialog.hide()
      if (data) {
        this.thongso_ban = { ...data.thongso_ban }
        this.thongso_tc = { ...data.thongso_tc }
      }
    },
    capnhat_TSCD(data) {
      if (data) {
        this.thongso_ban = data.thongso_Ban
        this.thongso_tc = data.thongso_TC
      }
    },
    async lvwThueBao_SelectedIndexChanged(item) {
      if (item) {
        this.hdtb_id = Number(item.data.id)
        let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(this.hdtb_id)
        await this.HienThiTTHopDongTB(ds)
      }
    },
    async LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(hdtbid) {
      return this.GetDataList(await API.LAY_DS_HOPDONG_TB_THEO_HDTB_ID_BC(this.axios, hdtbid))
    },
    async cboKieuLD_TC_SelectedValueChanged(newValue) {
      this.Kieuld_tc_id = newValue
      await this.NapThongTinDichVuThueBao()
    },
    async cboKieuLD_TC_TSL_SelectedValueChanged(newValue) {
      this.Kieuld_tc_id_tsl = newValue
      await this.NapThongTinDichVuThueBao()
    },
    async cboKieuLD_SelectedValueChanged() {
      console.log('cboKieuLD_SelectedValueChanged', this.cboKieuLD.value)
      if (!this.formProps.txtMaTB) return
      if (this.cboKieuLD.list.length > 0 && !this.kenh_trang) {
        if (this.cboKieuLD.value) {
          await this.LayTienTheoKhoanMuc()
          await this.NapThongTinDichVuThueBao()
          this.lvLyDoHuy.data = []
          this.dsDKHUY = []
          this.btnEditLyDoHuy.disabled = true
          if (this.cboKieuLD.value == KieuLapDat.TAMDUNG_SD_MEGA && this.formProps.txtMaTB && this.formProps.txtTenTB) {
            await this.HienCanhBaoThietHai()
          }
        }
      }
    },
    async PopupThongSo(){
      await new Promise(r => setTimeout(r, 500))
      console.log('PopupThongSo', this.cboKieuLD.value)
      if (this.cboKieuLD.list.length > 0 && (this.kenh_trang || this.chi_giao_net)) {
        if (this.cboKieuLD.value) {
          if (this.tinh.tinhthicong_id > 0 && this.Kieuld_click && (this.kenh_trang || this.chi_giao_net)) {
            // Hien thi form thong so de chon ben ban va ben thi cong
            let loaitbid = this.cboLoaihinhTB.value
            let dichvuvt = this.cboDichVuVT.value
            if (dichvuvt == DichVuVienThong.CO_DINH || dichvuvt == DichVuVienThong.IMS) {
              this.ThongSoCoDinhModel.tinhtc = this.tinh
              this.ThongSoCoDinhModel.pLoaihinhTB = loaitbid
              this.ThongSoCoDinhModel.ploaitb_ld = loaitbid
              this.ThongSoCoDinhModel.ploaitb = loaitbid
              this.ThongSoCoDinhModel.thuebao_id_ban = 0
              this.ThongSoCoDinhModel.thuebao_id_thicong = 0
              this.ThongSoCoDinhModel.vloaihd = LoaiHopDong.THAY_DOI_DV
              this.ThongSoCoDinhModel.khachhang_id = 0
              this.ThongSoCoDinhModel.hdkh_id = this.hdkh_id
              await this.$refs.frmThongSoCoDinh.openDialog(this.thongso_ban, this.thongso_tc)
            }
            if (dichvuvt == DichVuVienThong.ADSL && this.Kieuld_click) {
              this.thongso_tc.kieuld_id = this.cboKieuLD.value
              await this.$refs.TocDoMucCuocDialog.$children[0].clearData()
              await this.$refs.TocDoMucCuocDialog.$children[0].loadData()
              this.$refs.TocDoMucCuocDialog.show()
            }
            if ((dichvuvt == DichVuVienThong.MEGAWAN || dichvuvt == DichVuVienThong.METRONET||dichvuvt == 26) && this.Kieuld_click) {
              this.$refs.frmThongSoWan.ThongsoBan = this.thongso_ban
              this.$refs.frmThongSoWan.ThongsoTc = this.thongso_tc
              this.$refs.frmThongSoWan.hdkh_id = this.hdkh_id
              await this.$refs.frmThongSoWan.openDialog1(this.tinh, this.loaitb_id, this.loaitb_id, LoaiHopDong.THAY_DOI_DV, this.dichvuvt_id, this.thongso_ban, this.thongso_tc)
            }
          }
        }
      }
    },
    async cboLoaihinhTB_SelectedValueChanged(newValue) {
      if (this.cboLoaihinhTB.list.length > 0) {
        if (this.cboLoaihinhTB.value) {
          await this.HT_Kieu_LD_BANCHEO_Combobox(this.cboLoaihinhTB.value)
          await this.NapThongTinDichVuThueBao()
          await this.cboKieuLD_Click()
        }
      }
    },
    async HT_Kieu_LD_BANCHEO_Combobox(newValue) {
      this.Kieuld_click = false
      let data = []
      if (newValue == 0) {
        data = await this.CSS_KIEU_LD()
        data = data.filter((item) => item.LOAIHD_ID == 7)
        if (data.length > 0) {
          data = this.normalize(data)
          this.cboKieuLD.list = data
          this.cboKieuLD.value = this.cboKieuLD.list[0].kieuld_id
        } else {
          this.cboKieuLD.list = []
          this.cboKieuLD.value = null
        }
      } else {
        data = await this.lay_ds_kieu_ld_bc(LoaiHopDong.THAY_DOI_DV, 2, newValue)
        if (data.length > 0) {
          this.cboKieuLD.list = data
          this.cboKieuLD.value = this.cboKieuLD.list[0].kieuld_id
        } else {
          this.cboKieuLD.list = []
          this.cboKieuLD.value = null
        }
      }
      await this.cboKieuLD_SelectedValueChanged()
      this.Kieuld_click = true
    },
    async lay_ds_kieu_ld_bc(vloaihdid, vkieu, vloaitbid) {
      let req = {
        vloaihd_id: vloaihdid,
        vkieu: vkieu,
        vloaitb_id: vloaitbid
      }
      return this.GetDataList(await API.lay_ds_kieu_ld_bc(this.axios, req))
    },
    async CSS_KIEU_LD() {
      return this.GetDataList(await API.CSS_KIEU_LD(this.axios))
    },
    async CSS_LOAIHINH_TB(dichvuVTId) {
      let data = this.GetDataList(await API.CSS_LOAIHINH_TB(this.axios))
      if (data.length > 0) {
        this.cboLoaihinhTB.list = data
          .filter((item) => item.DICHVUVT_ID == dichvuVTId)
          .map((item) => {
            return {
              id: item.LOAITB_ID,
              text: item.LOAIHINH_TB
            }
          })
        if (this.cboLoaihinhTB.list.length > 0) {
          this.cboLoaihinhTB.value = this.cboLoaihinhTB.list[0].id
        }
      } else {
        this.cboLoaihinhTB.list = []
        this.cboLoaihinhTB.value = 0
      }
    },
    async LAY_ND_SMS_CANHBAO_THIETHAI(matb, huydau, rutgon) {
      let req = {
        huydau: huydau,
        ma_tb: matb,
        rutgon: rutgon
      }
      return this.GetData(await API.LAY_ND_SMS_CANHBAO_THIETHAI(this.axios, req))
    },
    async thamso_md_theo_ma(mats) {
      let output = false
      let data = await this.GetData(await API.thamso_md_theo_ma(this.axios, mats))
      if (data == null || data.length == 0) return
      if (data.length > 0) {
        output = true
      }
      return output
    },
    async LAY_DS_THAMSO_MD() {
      let data = this.GetDataList(await API.LAY_DS_THAMSO_MD(this.axios))
      if (data.length > 0) {
        let vTemp = 'KHONG_KT_NO_THANHLY'
        let det = data.filter((item) => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.trim() === '1') {
            this.ts_khong_kt_nocuoc = 1
          }
        }
        vTemp = 'TS_NGAY_HETHAN_TAMDUNG'
        det = data.filter((item) => item.ma_ts === vTemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.trim() === '1') {
            this.ts_ngay_hethan_tamdung = Number(row.ten_ts)
          }
        }
        this.ts_sinhma_gd_theo_donvi = data.filter((item) => item.ma_ts == 'SINH_MA_GD_THEO_DV').length > 0
      }
    },
    async GET_DICHVU_VT() {
      let data = this.GetDataList(await API.GET_DICHVU_VT(this.axios))
      if (data.length > 0) {
        data = data.filter((item) => item.dichvuvt_id != 5)
        this.cboDichVuVT.list = data.map((item) => {
          return {
            id: item.DICHVUVT_ID,
            text: item.TEN_DVVT
          }
        })
        this.cboDichVuVT.list = this.cboDichVuVT.list.sort((a, b) => parseInt(a.id) - parseInt(b.id))
        if (this.cboDichVuVT.list.length > 0) {
          this.cboDichVuVT.value = this.cboDichVuVT.list[0].id
        }
      }
    },
    getTag() {
      if (this.$route.query.tag != null && this.$route.query.tag !== '' && this.$route.query.tag.length > 0) {
        this.tagForm = this.$route.query.tag
      }
      if (this.tagForm == '1') {
        this.header.title = 'THAY ĐỔI DỊCH VỤ GIA TĂNG'
      }
    },
    async btnLayMaGD_Click() {
      this.modelSearchContract.trangthai_hd = Number(TrangThaiHD.MOI)
      this.modelSearchContract.loai_hd_id = Number(LoaiHopDong.THAY_DOI_DV)
      this.modelSearchContract.dichvu_id = 0
      this.modelSearchContract.ngay_yc = moment(new Date()).format('DD/MM/YYYY')
      this.modelSearchContract.donvi_dl_id = this.donviDLId
      this.modelSearchContract.donvi_id = this.donviId
      this.modelSearchContract.nhanvien_id = this.nhanvienId
      console.log(this.modelSearchContract)
      this.$refs.popupSearchContract.showModal()
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
    },
    chapNhanTraCuuThueBao(val) {
      if (val) {
        if (val.MA_TB) {
          this.formProps.txtMaTB = val.MA_TB
        }
        if (val.DICHVUVT_ID != 0) {
          this.cboDichVuVT.value = val.DICHVUVT_ID
        }
      }
      this.$refs.txtMaTB.focus()
      this.HienThiTB()
    },
    async HienThiTB() {
      if (this.formProps.txtMaTB) {
        let ds = await this.LAY_DS_HOPDONG_THEO_MA_TB(this.formProps.txtMaTB, LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI, this.dichvuvt_id, 0)
        if (ds.length > 0) {
          await this.HienThiTTHopDongKH(ds)
        } else {
          await this.HienThiTT_DanhBa(this.formProps.txtMaTB)
        }
      }
    },
    async btnTKMaTB_Click() {
      if (this.formProps.txtMaTB) {
        if (this.khachhang_id != 0) {
          this.TKTBChuyenDichProps.khachhang_id = this.khachhang_id
        } else {
          let dskhcu = await this.LAY_BIENDONG_THEO_HDTB_ID(this.hdtb_id)
          if (dskhcu.length > 0) {
            this.khachhang_id = dskhcu[0].khachhang_id
            this.TKTBChuyenDichProps.khachhang_id = dskhcu[0].khachhang_id
          } else {
            this.$root.$toast.error('Không có thông tin về thuê bao: ' + this.formProps.txtMaTB + '.Kiểm tra lại mã thuê bao')
            this.$refs.txtMaTB.focus()
            return
          }
        }
        this.TKTBChuyenDichProps.ma_gd = this.formProps.txtMaGD
        this.TKTBChuyenDichProps.title = 'CHỌN MÃ THUÊ BAO THỰC HIỆN THAY ĐỔI DỊCH VỤ'
        this.TKTBChuyenDichProps.text = 'Danh sách thuê bao của khách hàng thay đổi dịch vụ'
        await this.$refs.TKTBChuyenDich.showDialog()
      } else {
        this.$root.$toast.error('Bạn chưa nhập mã thuê bao!')
      }
    },
    async closeTKTBChuyenDich(MaThueBao, dichvuvtid) {
      if (MaThueBao) {
        this.formProps.txtMaTB = MaThueBao
      }
      if (dichvuvtid != 0) {
        this.cboDichVuVT.value = dichvuvtid
      }
      this.$refs.txtMaTB.focus()
      await this.HienThiTB()
    },
    async LAY_BIENDONG_THEO_HDTB_ID(hdtbid) {
      return this.GetDataList(await API.LAY_BIENDONG_THEO_HDTB_ID(this.axios, hdtbid))
    },
    GetDataList: function(response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        console.log(response)
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function(response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? null : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        console.log(response)
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex((item) => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    setVisibleButton(id, status) {
      let index = this.actions.findIndex((item) => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }
    },
    setActiveActions(kieu) {
      this.setActiveButton('tsbtnNhapMoi', false)
      this.setActiveButton('tsbtnGhiLai', false)
      this.setActiveButton('tsbtnXoa', false)
      this.setActiveButton('tsbtnHuyBo', false)
      this.setActiveButton('tsbtnThemTB', false)
      this.setActiveButton('tsbtnXoaTB', false)
      this.setActiveButton('tsbtnThanhToan', false)
      this.setActiveButton('btnPhieuYC', false)
      // this.setActiveButton('btnInHD', false)
      if (kieu === -1) {
        // bat dau
        this.$refs.txtMaTB.focus()
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.chkCTV.checked = false
        this.chkNguoiGT.checked = false
        this.formProps.txtNguoiGT = ''
        this.formProps.txtCTV = ''
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
      } else if (kieu === 0) {
        // bắt đầu
        this.$refs.txtMaTB.focus()
        this.setActiveButton('tsbtnNhapMoi', true)
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
        this.Clear()
      } else if (kieu === 1) {
        // thêm mới
        this.$refs.txtMaTB.focus()
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.chkCTV.checked = false
        this.chkNguoiGT.checked = false
        this.formProps.txtCTV = ''
        this.thongso_ban = this.Reset_Thongso_Thuebao()
        this.thongso_tc = this.Reset_Thongso_Thuebao()
        this.Clear()
      } else if (kieu === 2) {
        // Huỷ
        this.$refs.txtMaGD.focus()
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.Clear()
      } else if (kieu === 3) {
        // edit
        this.$refs.txtMaGD.focus()
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.setActiveButton('tsbtnThemTB', true)
        this.setActiveButton('tsbtnThanhToan', true)
        this.setActiveButton('btnInHD', true)
        this.setActiveButton('btnPhieuYC', true)
      }
    },
    async Lay_tienkhoanmuc_loaihd(loaihd) {
      return this.GetDataList(await API.Lay_tienkhoanmuc_loaihd(this.axios, loaihd))
    },
    async tsbtnNhapMoi_Click() {
      this.dsTienKhoanMuc = await this.Lay_tienkhoanmuc_loaihd(LoaiHopDong.THAY_DOI_DV)
      this.setActiveActions(1)
    },
    tsbtnXoa_Click() {
      this.$confirm('Bạn thật sự muốn xóa hợp đồng không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      })
        .then(() => {
          this.XoaHDKH()
        })
        .catch((error) => {
          console.log(error)
        })
    },
    async XoaHDKH() {
      try {
        this.loading(true)
        let dstb = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
        if (dstb.filter((item) => item.tthd_id != 1).length > 0) {
          this.$root.$toast.warning('Hợp đồng đã có phiếu giao đi. Bạn không thể xóa hợp đồng !')
          return
        }
        if (dstb.filter((item) => item.status == 5).length > 0) {
          this.$root.$toast.warning('Hợp đồng đã có phiếu kích hoạt visa. Bạn không thể xóa hợp đồng !')
          return
        }
        await this.XOA_HDBC(this.hdkh_id, LoaiHopDong.THAY_DOI_DV)
        await this.XOA_HDKH_V2(this.hdkh_id, LoaiHopDong.THAY_DOI_DV)
        this.lvwThueBao.data = []
        this.setActiveActions(0)
        await this.Clear()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async XOA_HDKH_V2(hdkhid, loaihd) {
      let req = { hdkh_id: hdkhid, loaihd_id: loaihd }
      return this.GetData(await API.XOA_HDKH_V2(this.axios, req))
    },
    async XOA_HDBC(hdkhid, loaihdid) {
      let req = {
        vhdkhid: hdkhid,
        vloaihdid: loaihdid
      }
      return this.GetData(await API.XOA_HDBC(this.axios, req))
    },
    tsbtnGiaoPhieu_Click() {},
    tsbtnThanhToan_Click() {
      let routeData = this.$router.resolve({
        name: 'Payment',
        query: { magd: this.formProps.txtMaGD.trim() }
      })
      window.open(routeData.href, '_blank')
    },
    btnInHD_Click() {
      if (this.hdkh_id != 0) {
        this.modelInHD.ma_gd = this.formProps.txtMaGD
        this.modelInHD.ngay_lhd = this.formProps.dtpNgayLapHD
        this.modelInHD.ngay_yc = this.formProps.dtpNgayYeuCau
        this.modelInHD.hdkh_id = this.hdkh_id
        this.$refs.popupInHD.showModal()
      } else {
        this.$root.$toast.error('Chưa có thông tin hợp đồng!')
      }
    },
    tsbtnThemTB_Click() {
      this.ThemHDThueBao()
    },
    async ThemHDThueBao() {
      try {
        let ktdl = await this.KiemTraDL_ThueBao()
        if (!ktdl) return
        let ktdk = await this.KT_DK_HUY_DV()
        if (!ktdk) return
        let dset = await this.lay_huonggiao_tiepnhan_v2(LoaiHopDong.THAY_DOI_DV, this.loaitb_id, this.tocdotn_id, this.muccuoctn_id, this.cboKieuLD.value, this.vloaikenh_id, this.cboDichVuVT.value)
        let huonggiao_tn = 0
        if (dset.length <= 0) {
          this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          return
        }
        huonggiao_tn = dset[0].huonggiao_id
        this.quytrinh_id = dset[0].quytrinh_id
        let ktchondvgt = false
        for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
          let item = this.dsThueBaoDichVu['danhsach'][i]
          if (item['dangky'] == 1 && item['huy'] == 1) {
            ktchondvgt = false
            break
          }
        }
        if (ktchondvgt == false) {
          this.$root.$toast.warning('Bạn chưa đăng ký/hủy dịch vụ gia tăng nào. Không thể cập nhật thông tin')
          return
        }
        let kt = await this.KIEMTRA_HDTB_KHI_THEMMOI(this.formProps.txtMaTB, this.hdkh_id, this.dichvuvt_id, 0)
        if (kt) {
          this.$root.$toast.warning('Thuê bao đã tồn tại trong hợp đồng, không thêm mới được ')
          return
        }
        let kttblhdk = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.formProps.txtMaTB, '0', this.cboDichVuVT.value, LoaiHopDong.THAY_DOI_DV)
        if (kttblhdk) {
          return
        }
        kt = await this.KIEMTRA_DB_KHI_THEMMOI(this.formProps.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id)
        if (!kt) {
          this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không thêm mới được')
          return
        }
        if (!this.isdn_sochinh) {
          this.hdtb_id_cha = this.hdtb_id
        }
        kt = await this.kiemtra_tb_cung_makh_tb_pl(this.formProps.txtMaGD, this.thuebao_id)
        if (kt) {
          this.$refs.txtMaTB.focus()
          this.dsHDTB_ADSL = []
          this.dsHDTB_CD = []
          this.dsHDTB_GP = []
          this.dsHDTB_TSL = []
          this.dsHDTB_MGW = []
          this.dsHDTB_DD = []
          this.dsHDTB_IMS = []
          this.dsHDTB_CNTT = []
          if (Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN2B_CD || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN2B_CQ || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN30B_CD || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN30B_CQ) {
            await this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
            if (this.isdn_sochinh && this.KT_DangKy_TD()) {
              for (let i = 0; i < this.ds_isdn.length > 0; i++) {
                let kt = true
                let ds = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.ds_isdn[i].ma_tb, this.formProps.txtMaGD, this.dichvuvt_id, LoaiHopDong.THAY_DOI_DV)
                if (ds.length > 0) {
                  kt = false
                }
                if (!kt) {
                  this.$root.$toast.warning('Thuê bao: ' + this.ds_isdn[i]['ma_tb'] + ' đang được lập phụ lục khác và chưa hoàn thành!. Bạn không thể thực hiện tiếp. ')
                  return
                }
              }
              await this.TaoDuLieu_HDTB(true)
              await this.TaoDuLieu_LYDOHUY(true)
              await this.TaoDuLieu_HDTB_CD(true)
              this.TaoDuLieu_THONGTIN_TT(true)
              await this.TaoDuLieu_CT_TIENHD(true)
              await this.TaoDuLieu_HDTBDV(true)
              await this.TaoDuLieu_HDTDDV(true)
              await this.TaoDuLieuBC(true)
              await this.sp_thaydoidv_themmoi_hopdong(huonggiao_tn)
              if (this.tinh.tinhthicong_id > 0) {
                await this.fn_themmoi_hdbancheo_biendong()
              }
              let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
              await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
              await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
              for (let i = 0; i < this.ds_isdn.length; i++) {
                if (this.ds_isdn[i].trangthaitb_id == TrangThaiTB.THUONG && this.ds_isdn[i].thuebao_cha_id) {
                  /// do kiểm tra ở trên thuê bao ko lập phụ lục nào khác với phụ lục hiện tại
                  /// => kiểm tra tiếp thuê bao có đang thuộc phụ lục này ko
                  let ds = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.ds_isdn[i].ma_tb, '0', this.dichvuvt_id, LoaiHopDong.THAY_DOI_DV)
                  if (ds.length > 0 && ds[0].ma_gd == this.formProps.txtMaGD) {
                    // Thuê bao thuộc phụ lục => Không làm gì cả
                  }
                } else {
                  // Nếu không thuộc phụ lục thì thực hiện tiếp
                  await this.TaoDuLieu_HDTB_ISDN(true, this.ds_isdn[i].thuebao_id, i)
                  this.TaoDuLieu_THONGTIN_TT(true)
                  await this.TaoDuLieu_CT_TIENHD(true)
                  await this.TaoDuLieu_HDTDDV(true)
                  this.TaoDuLieu_LYDOHUY(true)
                  await this.TaoDuLieu_HDTB_CD_ISDN(true, this.ds_isdn[i].thuebao_id)
                  let req = {
                    vdsHDTB: JSON.stringify(this.dsHDTB),
                    vdsDKDVGT: JSON.stringify(this.dsHDTDDV),
                    vdsTTTT: JSON.stringify(this.dsTTTT),
                    vdsCTTHD: JSON.stringify(this.dsCTTHD),
                    vdsHDTB_CD: JSON.stringify(this.dsHDTB_CD),
                    vdsDK_HUY: JSON.stringify(this.dsDKHUY),
                    vhuonggiao_id: huonggiao_tn,
                    vdsHDTBDV: JSON.stringify([])
                  }
                  await this.fn_them_hd_dvgt_isdn_bancheo(huonggiao_tn, req)
                }
              }
            } else {
              await this.TaoDuLieu_HDTB(true)
              this.TaoDuLieu_LYDOHUY(true)
              await this.TaoDuLieu_HDTB_CD(true)
              this.TaoDuLieu_THONGTIN_TT(true)
              await this.TaoDuLieu_CT_TIENHD(true)
              await this.TaoDuLieu_HDTBDV(true)
              await this.TaoDuLieu_HDTDDV(true)
              await this.sp_thaydoidv_themmoi_hopdong(huonggiao_tn)
              let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
              await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
              await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
            }
          } else {
            // Không phải ISDN
            await this.TaoDuLieu_HDTB(true)
            await this.TaoDuLieu_HDTBDV(true)
            /// TuanNA: Thêm các hàm tạo dữ liệu các bảng hợp đồng con
            /// Ngày 01.06.2012
            this.TaoDuLieu_LYDOHUY(true)
            if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
              await this.TaoDuLieu_HDTB_ADSL(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
              await this.TaoDuLieu_HDTB_CD(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.GPHONE) {
              await this.TaoDuLieu_HDTB_GP(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.TSL||this.cboDichVuVT.value == 25) {
              await this.TaoDuLieu_HDTB_TSL(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.MEGAWAN||this.cboDichVuVT.value == 26) {
              await this.TaoDuLieu_HDTB_MGWAN(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
              await this.TaoDuLieu_HDTB_DD(true)
            }
            if (this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
              await this.TaoDuLieu_HDTB_CNTT(true)
            }
            this.TaoDuLieu_THONGTIN_TT(true)
            await this.TaoDuLieu_CT_TIENHD(true)
            await this.TaoDuLieu_HDTDDV(true)
            await this.sp_thaydoidv_themmoi_hopdong_v2_v2(huonggiao_tn)
            if (this.tinh.tinhthicong_id > 0) {
              await this.TaoDuLieuBC(true)
              await this.fn_themmoi_hdbancheo_biendong()
            }
            let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
            await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
            await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
          }
          let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(ds)
          this.$root.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
        } else {
          this.$root.$toast.error('Thuê bao không cùng mã khách hàng với thuê bao đã lập hợp đồng !')
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async TaoDuLieu_HDTB_CNTT(themmoi) {
      let ds = await this.LAY_DS_DB_CNTT_THEO_TBID(this.thuebao_id)
      this.dsHDTB_CNTT.push(this.capitalize(ds[0]))
    },
    async TaoDuLieu_HDTB_CD_ISDN(themmoi, thuebaoid) {
      this.dsHDTB_CD = []
      let rowHDTBCD = {}
      let ds = await this.LAY_DS_DB_CD_THEO_TBID(this.thuebao_id)
      rowHDTBCD = Object.assign(rowHDTBCD, this.capitalize(ds[0]))
      rowHDTBCD.HDTB_ID = this.hdtb_id
      this.dsHDTB_CD.push(rowHDTBCD)
    },
    async TaoDuLieu_HDTB_ISDN(themmoi, thuebaoid, index) {
      this.dsHDTB = []
      if (themmoi) {
        this.hdtb_id = await this.POST_GET_KEYS('HD_THUEBAO')
      }
      let rowHDTB = {}
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (thuebaoid > 0) rowHDTB.THUEBAO_ID = thuebaoid
      if (this.thuebao_id > 0) {
        rowHDTB.THUEBAO_CHA_ID = this.thuebao_id
      }
      rowHDTB.MA_TB = this.ds_isdn[index]['ma_tb'] // txtMaTB.Text
      rowHDTB.TEN_TB = this.StringToString(this.ds_isdn[index]['ten_tb']) // txtTenTB.Text
      rowHDTB.DIACHI_TB = this.StringToString(this.ds_isdn[index]['diachi_tb']) // txtDiaChiTB.Text
      rowHDTB.DIACHI_LD = this.StringToString(this.ds_isdn[index]['diachi_ld']) // txtDiaChiLD.Text
      rowHDTB.GHICHU = this.StringToString(this.ds_isdn[index]['ghichu']) // txtGhiChu.Text
      rowHDTB.KIEULD_ID = this.cboKieuLD.value
      rowHDTB.DOITUONG_ID = this.ds_isdn[index]['doituong_id'] // doituong_id;
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.DONVI_ID = this.donvi_id
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        rowHDTB.NGAY_HT = this.formProps.dtpNgayYeuCau
      } else {
        rowHDTB.NGAY_CN = this.formProps.dtpNgayYeuCau
      }
      rowHDTB.NGUOI_CN = this.$root.token.getUserName()
      rowHDTB.NGAY_CN = this.formatDate(new Date(), null)
      rowHDTB.MAY_CN = this.maycn
      if (this.dichvuvt_id == DichVuVienThong.ADSL || this.dichvuvt_id == DichVuVienThong.IMS) {
        rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO
      }
      this.dsHDTB.push(rowHDTB)
    },
    async sp_thaydoidv_themmoi_hopdong(huonggiaoid) {
      let req = {
        p_huonggiao_id: huonggiaoid,
        p_js_hd_thuebao: JSON.stringify(this.dsHDTB),
        p_js_HDTB_DV: JSON.stringify(this.dsHDTBDV),
        p_js_dangky_dvgt: JSON.stringify(this.dsHDTDDV),
        p_js_TTTT: JSON.stringify(this.dsTTTT),
        p_js_CTTHD: JSON.stringify(this.dsCTTHD),
        p_js_HDTB_CD: JSON.stringify(this.dsHDTB_CD),
        p_js_HDTB_ADSL: JSON.stringify(this.dsHDTB_ADSL),
        p_js_HDTB_GP: JSON.stringify(this.dsHDTB_GP),
        p_js_HDTB_MGW: JSON.stringify(this.dsHDTB_MGW),
        p_js_HDTB_TSL: JSON.stringify(this.dsHDTB_TSL),
        p_js_HDTB_DD: JSON.stringify(this.dsHDTB_DD),
        p_js_dangky_huy: JSON.stringify(this.dsDKHUY)
      }
      await API.sp_thaydoidv_themmoi_hopdong(this.axios, req)
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            console.log('sp_thaydoidv_themmoi_hopdong success')
          } else {
            this.$root.$toast.error('Lỗi thêm mới hợp đồng:', response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ', error.data.message)
        })
    },
    async TaoDuLieu_BD_LYDOHUY(bdtbid) {
      this.dsBD_LYDOHUY = []
      let ds = await this.LAY_DS_DB_LYDOHUY_THEO_TBID(this.thuebao_id)
      for (let i = 0; i < ds.length; i++) {
        let rowBDLYDOHUY = {}
        rowBDLYDOHUY.THUEBAO_ID = ds[i]['thuebao_id']
        rowBDLYDOHUY.BDTB_ID = bdtbid
        rowBDLYDOHUY.LYDOHUY_ID = ds[i]['lydohuy_id']
        rowBDLYDOHUY.NOIDUNG = ds[i]['noidung']
        if (ds[i]['ngay_dk'] && moment(ds[i]['ngay_dk']).isValid()) {
          rowBDLYDOHUY.NGAY_DK = moment(ds[i]['ngay_dk']).format('DD/MM/YYYY')
        }
        if (ds[i]['ngay_dk'] && moment(new Date(ds[i]['ngay_dk'], 'DD/MM/YYYY')).isValid()) {
          rowBDLYDOHUY.NGAY_DK = ds[i]['ngay_dk']
        }
        this.dsBD_LYDOHUY.push(rowBDLYDOHUY)
      }
    },
    async fn_tt_bd_thuebao(hdtbid) {
      let req = { param: hdtbid, type: 1 }
      return this.GetData(await API.fn_tt_bd_thuebao(this.axios, req))
    },
    async LAY_DS_DB_LYDOHUY_THEO_TBID(thuebaoid) {
      return this.GetDataList(await API.LAY_DS_DB_LYDOHUY_THEO_TBID(this.axios, thuebaoid))
    },
    async POST_GET_KEYS(keyname) {
      return this.GetData(
        await API.POST_GET_KEYS(this.axios, {
          keyname: keyname
        })
      )
    },
    async KT_THAOTAC(luongid, code) {
      let data = this.GetDataList(await API.KT_THAOTAC(this.axios, luongid, code))
      return data[0].sl > 0
    },
    async TaoDuLieu_HDKH(themmoi) {
      let vmagd_temp = ''
      this.dsHDKH = []
      if (themmoi) {
        this.hdkh_id = await this.POST_GET_KEYS('HD_KHACHHANG')
      }
      let rowHDKH = {}
      rowHDKH.HDKH_ID = this.hdkh_id
      if (themmoi) {
        if (this.ts_sinhma_gd_theo_donvi) {
          vmagd_temp = await this.SINH_MAGD_V2(LoaiHopDong.THAY_DOI_DV, this.donviId)
        } else {
          vmagd_temp = await this.SINH_MAGD(LoaiHopDong.THAY_DOI_DV)
        }
        if (!vmagd_temp) {
          this.loi_sinh_magd = true
        }
        this.formProps.txtMaGD = vmagd_temp
        rowHDKH.MA_GD = this.formProps.txtMaGD
        rowHDKH.MA_HD = this.formProps.txtMaHD
      } else {
        rowHDKH.MA_GD = this.formProps.txtMaGD
        rowHDKH.MA_HD = this.formProps.txtMaHD
      }
      rowHDKH.MA_KH = this.formProps.txtMaKH
      if (this.khachhang_id > 0) rowHDKH.KHACHHANG_ID = this.khachhang_id
      rowHDKH.TEN_KH = this.StringToString(this.formProps.txtTenKH)
      rowHDKH.DIACHI_KH = this.StringToString(this.formProps.txtDiaChiKH)
      rowHDKH.NGAYLAP_HD = this.formProps.dtpNgayLapHD
      rowHDKH.NGAY_YC = this.formProps.dtpNgayYeuCau
      rowHDKH.DONVI_ID = this.donviId
      rowHDKH.NHANVIEN_ID = this.nhanvienId
      rowHDKH.MA_DUAN = this.formProps.txtMaDuAn
      if (this.hdkh_cha_id > 0) {
        rowHDKH.HDKH_CHA_ID = this.hdkh_cha_id
      }
      if (this.khachhang_id > 0) {
        let khlid = await this.FN_TT_DB_KHACHHANG(this.khachhang_id)
        if (khlid && khlid != '-1') {
          rowHDKH.KHLON_ID = Number(khlid)
        } else {
          rowHDKH.KHLON_ID = 1
        }
      } else {
        rowHDKH.KHLON_ID = 1
      }
      rowHDKH.LOAIHD_ID = LoaiHopDong.THAY_DOI_DV
      if (this.chkKHKT3.checked) {
        rowHDKH.KH_KT3 = 1
      } else {
        rowHDKH.KH_KT3 = 0
      }
      if (this.chkCTV.checked && this.formProps.txtCTV) {
        rowHDKH.CTV_ID = this.ctv_id
      }
      if (this.chkNguoiGT.checked && this.formProps.txtNguoiGT) {
        rowHDKH.NHANVIENGT_ID = this.nguoigt_id
      }
      rowHDKH.NGUOI_CN = this.$root.token.getUserName()
      rowHDKH.NGAY_CN = this.formatDate(new Date(), null)
      rowHDKH.MAY_CN = this.maycn
      rowHDKH.IP_CN = this.ipcn
      this.dsHDKH.push(rowHDKH)
    },
    async FN_TT_DB_KHACHHANG(khachhangid) {
      let req = { param: khachhangid, type: 1 }
      return this.GetData(await API.FN_TT_DB_KHACHHANG(this.axios, req))
    },
    async SINH_MAGD_V2(loaihdid, donviid) {
      let req = {
        loaihd_id: loaihdid,
        donvi_id: donviid
      }
      return this.GetData(await API.SINH_MAGD_V2(this.axios, req))
    },
    async SINH_MAGD(loaihdid) {
      return this.GetData(await API.SINH_MAGD(this.axios, { loaihd_id: loaihdid }))
    },
    async TaoDuLieu_HDTB(themmoi) {
      this.dsHDTB = []
      if (themmoi) {
        this.hdtb_id = await this.POST_GET_KEYS('HD_THUEBAO')
      }
      const rowHDTB = {}
      rowHDTB.HDTB_ID = this.hdtb_id
      rowHDTB.HDKH_ID = this.hdkh_id
      if (this.thuebao_id > 0) {
        rowHDTB.THUEBAO_ID = this.thuebao_id
      }
      rowHDTB.MA_TB = this.formProps.txtMaTB
      rowHDTB.TEN_TB = this.StringToString(this.formProps.txtTenTB)
      rowHDTB.DIACHI_TB = this.StringToString(this.formProps.txtDiaChiTB)
      rowHDTB.DIACHI_LD = this.StringToString(this.formProps.txtDiaChiLD)
      rowHDTB.GHICHU = this.StringToString(this.formProps.txtGhiChu)
      rowHDTB.KIEULD_ID = this.cboKieuLD.value
      rowHDTB.DOITUONG_ID = this.doituong_id
      rowHDTB.TTHD_ID = TrangThaiHD.MOI
      rowHDTB.LOAITB_ID = this.loaitb_id
      rowHDTB.DICHVUVT_ID = this.dichvuvt_id
      rowHDTB.DONVI_ID = this.donvi_id
      if (this.dichvuvt_id == DichVuVienThong.DI_DONG) {
        rowHDTB.NGAY_HT = this.formProps.dtpNgayYeuCau
      } else {
        rowHDTB.NGAY_CN = this.formProps.dtpNgayYeuCau
      }
      rowHDTB.NGUOI_CN = this.$root.token.getUserName()
      rowHDTB.NGAY_CN = this.formatDate(new Date(), null)
      rowHDTB.MAY_CN = this.maycn
      rowHDTB.IP_CN = this.ipcn
      rowHDTB.QUYTRINH_ID = this.quytrinh_id
      if (this.dichvuvt_id == DichVuVienThong.ADSL || this.dichvuvt_id == DichVuVienThong.IMS || this.dichvuvt_id == DichVuVienThong.CO_DINH || this.dichvuvt_id == DichVuVienThong.DI_DONG || this.dichvuvt_id == DichVuVienThong.GPHONE) {
        rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO
      }
      let kttt = await this.KT_THAOTAC(this.luong_id, 'STATUS_TONGDAI_CD')
      if (kttt) {
        rowHDTB.STATUS = '0'
      }
      if (this.chkNgay_HHTD.checked) {
        rowHDTB.NGAY_HH_TD = this.formProps.dtpNgayHH_TD
      }
      if (this.loaitb_id == LoaiHinhTB.PABX) {
        if (this.loaisochinh == 0) {
          if (this.hdtb_id_cha > 0) {
            rowHDTB.HDTB_CHA_ID = this.hdtb_id_cha
          }
        }
      }
      this.dsHDTB.push(rowHDTB)
    },
    TaoDuLieu_LYDOHUY() {
      this.dsDKHUY = []
      if (!this.dsDKHUY_TEMP || !this.dsDKHUY_TEMP[0]) return
      for (let i = 0; i < this.dsDKHUY_TEMP[0].length; i++) {
        let rowDK_HUY = {}
        rowDK_HUY.HDTB_ID = this.hdtb_id
        rowDK_HUY.LYDOHUY_ID = this.dsDKHUY_TEMP[0][i]['lydohuy_id']
        rowDK_HUY.NOIDUNG = this.dsDKHUY_TEMP[0][i]['noidung']
        rowDK_HUY.NGAY_YC = this.dsDKHUY_TEMP[0][i]['ngay_yc']
        this.dsDKHUY.push(rowDK_HUY)
      }
    },
    async TaoDuLieu_HDTB_CD(themmoi) {
      this.dsHDTB_CD = []
      let rowHDTBCD = {}
      let ds = await this.LAY_DS_DB_CD_THEO_TBID(this.thuebao_id)
      rowHDTBCD = Object.assign(rowHDTBCD, this.capitalize(ds[0]))
      rowHDTBCD.HDTB_ID = this.hdtb_id
      this.dsHDTB_CD.push(rowHDTBCD)
    },
    TaoDuLieu_THONGTIN_TT (themmoi) {
    },
    async TaoDuLieu_CT_TIENHD (themmoi) {
      try {
        this.dsCTTHD = []
        let rowCTTHD = {}
        for (let i = 0; i < this.dsTienKhoanMuc.length; i++) {
          let item = this.dsTienKhoanMuc[i]
          if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
            for (let j = 0; j < this.dsThueBaoDichVu['danhsach'].length; j++) {
              let element = this.dsThueBaoDichVu['danhsach'][j]
              if (element.dangky == 1 && element.huy == 0) {
                rowCTTHD = {}
                rowCTTHD.HDTB_ID = this.hdtb_id
                if (themmoi) {
                  rowCTTHD.CT_TIENHD_ID = await this.POST_GET_KEYS('CT_TIENHD')
                }
                rowCTTHD.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DICHVU
                rowCTTHD.TIEN = element.cuoc_ld
                rowCTTHD.VAT = element.vat_ld
                rowCTTHD.ID = item.dichvugt_id
                rowCTTHD.LOAI_ID = 2
                this.dsCTTHD.push(rowCTTHD)
              }
            }
          } else {
            rowCTTHD = {}
            rowCTTHD.HDTB_ID = this.hdtb_id
            if (themmoi == true) {
              rowCTTHD.CT_TIENHD_ID = await this.POST_GET_KEYS('CT_TIENHD')
            }
            rowCTTHD.KHOANMUCTT_ID = this.dsTienKhoanMuc[i]['khoanmuctt_id']
            rowCTTHD.TIEN = this.dsTienKhoanMuc[i]['tien']
            rowCTTHD.VAT = this.dsTienKhoanMuc[i]['vat']
            rowCTTHD.ID = this.dsTienKhoanMuc[i]['khoanmuctt_id']
            rowCTTHD.LOAI_ID = 1
            this.dsCTTHD.push(rowCTTHD)
          }
        }
      } catch (e) {console.log('TaoDuLieu_CT_TIENHD', e)}
    },
    async TaoDuLieu_HDTBDV (themmoi) {
      try {
        this.dsHDTBDV = []
        let data = await this.lay_thongtin_dbtb_dv(this.thuebao_id)
        for (let i = 0; i < data.length; i++) {
          let row = {}
          row = Object.assign(row, this.capitalize(data[i]))
          row.HDTB_ID = this.hdtb_id
          this.dsHDTBDV.push(row)
        }
      } catch (e) {console.log('TaoDuLieu_HDTBDV', e)}
    },
    async TaoDuLieu_HDTDDV (themmoi) {
      try {
        this.dsHDTDDV = []
        console.log('khởi tạo TaoDuLieu_HDTDDV')
        let kieu_dk_dvgt = 0
        for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
          let item = this.dsThueBaoDichVu['danhsach'][i]
          console.log(item);
          if (item.dangky == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
          } else if (item.huy == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
          } else if (item.sua == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_SUA_DVGT
          } else {
            continue
          }
          let rowHDTDDV = {}
          rowHDTDDV.HDTB_ID = this.hdtb_id
          rowHDTDDV.NGAY_YC = this.formProps.dtpNgayYeuCau
          rowHDTDDV.DICHVUGT_ID = Number(item['dichvugt_id'])
          rowHDTDDV.NOIDUNG = item['noidung']
          rowHDTDDV.KIEU_YC = kieu_dk_dvgt
          rowHDTDDV.TTTH = 1
          if (item.cuoc_sd) {
            rowHDTDDV.CUOC_SD = item.cuoc_sd
          } else {
            rowHDTDDV.CUOC_SD = 0
          }
          if (item.vat_sd) {
            rowHDTDDV.VAT_SD = item.vat_sd
          } else {
            rowHDTDDV.VAT_SD = 0
          }
          console.log(this.dsHDTDDV);
          this.dsHDTDDV.push(rowHDTDDV)
        }
      } catch (e) {
        console.log('TaoDuLieu_HDTDDV', e)
      }
    },
    async TaoDuLieuBC (themmoi) {
      try {
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          this.dsHDBC = []
          this.dsHDTBBC = []
          await this.TaoDuLieu_HDBANCHEO_TSL(themmoi, 1)
          await this.TaoDuLieu_HDBANCHEO_TSL(themmoi, 2)
        } else {
          await this.TaoDuLieu_HDTBBC(themmoi)
        }
        this.TaoDuLieu_HDTDDVBC(themmoi)
        this.dsHDBCADSL = []
        this.dsHDBCCD = []
        this.dsHDBCTSL = []
        this.dsHDBCMGWAN = []
        this.dsHDBCIMS = []
        this.TaoDuLieu_LYDOHUY_BC(themmoi)
        if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
          await this.TaoDuLieu_HDTBBC_ADSL(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
          await this.TaoDuLieu_HDTBBC_CD(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.TSL||this.cboDichVuVT.value == 25) { // TaoDuLieu_HDTBBC_TSL(themmoi);
          // dsHDBC_TSL.Tables[0].TableName = "HDBC_TSL";
          this.dsHDBCTSL = []
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 1)
          await this.TaoDuLieu_HDTBBC_TSL(themmoi, 2)
          console.log('this.dsHDBCTSL', this.dsHDBCTSL)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.MEGAWAN ||
          this.cboDichVuVT.value == DichVuVienThong.METRONET||this.cboDichVuVT.value == 26) {
          await this.TaoDuLieu_HDTBBC_MGWAN(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
          await this.TaoDuLieu_HDTBBC_IMS(themmoi)
        }
      } catch (e) {console.log(e)}
    },
    async TaoDuLieu_HDTBBC_TSL(themmoi, vdaucuoi_id) {
      try{
        console.log(this.thuebao_id);
        let ds = []
        let dsdc = await this.LAY_DS_DB_TSL_THEO_TBID_2(this.thuebao_id)
        if (!this.kenh_trang) {
          if (vdaucuoi_id == 1) {
            ds = await this.LAY_DS_DB_TSL_THEO_TBID_BC(this.tinh_dau.tinhthicong_id, this.thuebao_tc_id)
          } else {
            ds = await this.LAY_DS_DB_TSL_THEO_TBID_BC(this.tinh_cuoi.tinhthicong_id, this.thuebao_tc_id_tsl)
          }
          //uppercase
          for(var i = 0; i < ds.length; i++){
            for (var key in ds[i]) {
              if(key.toUpperCase() !== key){
                ds[i][key.toUpperCase()] = ds[i][key];
                delete ds[i][key];
              }
            }
          }

        }


        let row_count = 0
        if (!this.kenh_trang) {
          row_count = ds.length
        } else {
          row_count = this.dsHDTB_TSL.length
        }
        for (let i = 0; i < row_count; i++) {
          let rowHDBCTSL = {}
          if (!this.kenh_trang) {
            rowHDBCTSL = ds[i]
          } else {
            rowHDBCTSL = this.dsHDTB_TSL[i]
            if (vdaucuoi_id == 1) {
              if (this.thongso_tc_dau.chuquan_id == 0) {
                rowHDBCTSL.CHUQUAN_ID = null
              } else {
                rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_dau.chuquan_id
              }
              if (this.thongso_tc_dau.loaikenh_id == 0) {
                rowHDBCTSL.LOAIKENH_ID = null
              } else {
                rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_dau.loaikenh_id
              }
              if (this.thongso_tc_dau.thietbidc_id == 0) {
                rowHDBCTSL.THIETBIDC_ID = null
              } else {
                rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_dau.thietbidc_id
              }
              if (this.thongso_tc_dau.thoihan_id == 0) {
                rowHDBCTSL.THOIHAN_ID = null
              } else {
                rowHDBCTSL.THOIHAN_ID = this.thongso_tc_dau.thoihan_id
              }
              if (this.thongso_tc_dau.tocdo_id == 0) {
                rowHDBCTSL.TOCDO_ID = null
              } else {
                rowHDBCTSL.TOCDO_ID = this.thongso_tc_dau.tocdo_id
              }
              if (this.thongso_tc_dau.trangbi_id == 0) {
                rowHDBCTSL.TRANGBI_ID = null
              } else {
                rowHDBCTSL.TRANGBI_ID = this.thongso_tc_dau.trangbi_id
              }
              rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_dau.chuquanld_id
            } else {
              if (this.thongso_tc_cuoi.chuquan_id == 0) {
                rowHDBCTSL.CHUQUAN_ID = null
              } else {
                rowHDBCTSL.CHUQUAN_ID = this.thongso_tc_cuoi.chuquan_id
              }
              if (this.thongso_tc_cuoi.loaikenh_id == 0) {
                rowHDBCTSL.LOAIKENH_ID = null
              } else {
                rowHDBCTSL.LOAIKENH_ID = this.thongso_tc_cuoi.loaikenh_id
              }
              if (this.thongso_tc_cuoi.thietbidc_id == 0) {
                rowHDBCTSL.THIETBIDC_ID = null
              } else {
                rowHDBCTSL.THIETBIDC_ID = this.thongso_tc_cuoi.thietbidc_id
              }
              if (this.thongso_tc_cuoi.thoihan_id == 0) {
                rowHDBCTSL.THOIHAN_ID = null
              } else {
                rowHDBCTSL.THOIHAN_ID = this.thongso_tc_cuoi.thoihan_id
              }
              if (this.thongso_tc_cuoi.tocdo_id == 0) {
                rowHDBCTSL.TOCDO_ID = null
              } else {
                rowHDBCTSL.TOCDO_ID = this.thongso_tc_cuoi.tocdo_id
              }
              if (this.thongso_tc_cuoi.trangbi_id == 0) {
                rowHDBCTSL.TRANGBI_ID = null
              } else {
                rowHDBCTSL.TRANGBI_ID = this.thongso_tc_cuoi.trangbi_id
              }
              rowHDBCTSL.CHUQUANLD_ID = this.thongso_tc_cuoi.chuquanld_id
            }
          }
          if (vdaucuoi_id == 1) {
            if (this.thongso_tc_dau.loaimd_id != 0) {
              rowHDBCTSL.LOAIMD_ID = this.thongso_tc_dau.loaimd_id
            } else {
              rowHDBCTSL.LOAIMD_ID = null
            }
          } else {
            if (this.thongso_tc_cuoi.loaimd_id != 0) {
              rowHDBCTSL.LOAIMD_ID = this.thongso_tc_cuoi.loaimd_id
            } else {
              rowHDBCTSL.LOAIMD_ID = null
            }
          }
          rowHDBCTSL.DIACHI_ID = dsdc.filter((item) => item.daucuoi_id == vdaucuoi_id)[0]['diachi_id']
          rowHDBCTSL.HDTB_ID = this.hdtb_id
          rowHDBCTSL.TINH_DC_ID = vdaucuoi_id
          if (rowHDBCTSL.DAUCUOI_ID == 1) {
            rowHDBCTSL.TINH_ID = this.tinh_dau.tinhthicong_id
          } else {
            rowHDBCTSL.TINH_ID = this.tinh_cuoi.tinhthicong_id
          }
          rowHDBCTSL.ID_ERP = this.formProps.txtMaERP
          rowHDBCTSL.MA_BSS = this.formProps.txtMaBSS
          this.dsHDBCTSL.push(rowHDBCTSL)
        }
      }
      catch (e){
        console.log(e);
      }
    },
    async LAY_DS_DB_TSL_THEO_TBID_2(thuebaoid) {
      console.log("thuebaoid: ",thuebaoid);
      return this.GetDataList(await API.LAY_DS_DB_TSL_THEO_TBID_2(this.axios, thuebaoid))
    },
    async TaoDuLieu_HDTBBC_IMS(themmoi) {
      let rowHDBCIMS = {}
      let ds = await this.lay_ds_db_ims_theo_tbid_bc(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      rowHDBCIMS = Object.assign(rowHDBCIMS, this.capitalize(ds[0]))
      rowHDBCIMS.HDTB_ID = this.hdtb_id
      this.dsHDBCIMS.push(rowHDBCIMS)
    },
    async TaoDuLieu_HDTBBC_MGWAN(themmoi) {
      this.dsHDBCMGWAN = []
      let rowHDBCMGWAN = {}
      if (!this.kenh_trang && !this.chi_giao_net) {
        let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID_BC(this.tinh.tinhthicong_id, this.thuebao_tc_id)
        rowHDBCMGWAN = Object.assign(rowHDBCMGWAN, this.capitalize(ds[0]))
      } else {
        rowHDBCMGWAN.MADOICAP = -1
        rowHDBCMGWAN.LOAIMD_ID = this.thongso_tc.loaimd_id
        if (this.thongso_tc.chuquan_id == 0) {
          rowHDBCMGWAN.CHUQUAN_ID = null
        } else {
          rowHDBCMGWAN.CHUQUAN_ID = this.thongso_tc.chuquan_id
        }
        if (this.thongso_tc.loaikenh_id == 0) {
          rowHDBCMGWAN.LOAIKENH_ID = null
        } else {
          rowHDBCMGWAN.LOAIKENH_ID = this.thongso_tc.loaikenh_id
        }
        if (this.thongso_tc.thietbidc_id == 0) {
          rowHDBCMGWAN.THIETBIDC_ID = null
        } else {
          rowHDBCMGWAN.THIETBIDC_ID = this.thongso_tc.thietbidc_id
        }
        if (this.thongso_tc.thoihan_id == 0) {
          rowHDBCMGWAN.THOIHAN_ID = null
        } else {
          rowHDBCMGWAN.THOIHAN_ID = this.thongso_tc.thoihan_id
        }
        rowHDBCMGWAN.TOCDO_ID = this.thongso_tc.tocdo_id
        if (this.thongso_tc.tocdo_isp == 0) {
          rowHDBCMGWAN.TOCDO_ISP = null
        } else {
          rowHDBCMGWAN.TOCDO_ISP = this.thongso_tc.tocdo_isp
        }
        if (this.thongso_tc.tocdo_nix == 0) {
          rowHDBCMGWAN.TOCDO_NIX = null
        } else {
          rowHDBCMGWAN.TOCDO_NIX = this.thongso_tc.tocdo_nix
        }
        if (this.thongso_tc.tocdo_pir_id == 0) {
          rowHDBCMGWAN.TOCDO_PIR_ID = null
        } else {
          rowHDBCMGWAN.TOCDO_PIR_ID = this.thongso_tc.tocdo_pir_id
        }
        if (this.thongso_tc.trangbi_id == 0) {
          rowHDBCMGWAN.TRANGBI_ID = null
        } else {
          rowHDBCMGWAN.TRANGBI_ID = this.thongso_tc.trangbi_id
        }
        rowHDBCMGWAN.CHUQUANLD_ID = this.thongso_tc.chuquanld_id
      }
      rowHDBCMGWAN.HDTB_ID = this.hdtb_id
      rowHDBCMGWAN.MA_PL = this.formProps.txtMaHD_TTKD
      rowHDBCMGWAN.ID_ERP = this.formProps.txtMaERP
      rowHDBCMGWAN.MA_BSS = this.formProps.txtMaBSS
      this.dsHDBCMGWAN.push(rowHDBCMGWAN)
    },
    TaoDuLieu_LYDOHUY_BC(themmoi) {
      try{
        this.dsDK_HUYBC = []
        if (!this.dsDKHUY_TEMP || this.dsDKHUY_TEMP.length < 2) {
          return
        }
        for (let i = 0; i < this.dsDKHUY_TEMP[1].length; i++) {
          let rowDK_HUY = {}
          let item = this.dsDKHUY_TEMP[1][i]
          rowDK_HUY.HDTB_ID = this.hdtb_id
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
            rowDK_HUY.LYDOHUY_ID = 1
            rowDK_HUY.NOIDUNG = 'test'
            rowDK_HUY.NGAY_YC = this.formatDate(new Date(), null)
          } else {
            rowDK_HUY.LYDOHUY_ID = item['lydohuy_id']
            rowDK_HUY.NOIDUNG = item['noidung']
            rowDK_HUY.NGAY_YC = item['ngay_yc']
          }
          this.dsDK_HUYBC.push(rowDK_HUY)
        }
      }
      catch (e){
        console.log(e);
      }
    },
    async TaoDuLieu_HDTB_TSL(themmoi) {
      this.dsHDTB_TSL = []
      let ds = await this.LAY_DS_DB_TSL_THEO_TBID(this.thuebao_id)
      for (let i = 0; i < ds.length; i++) {
        let rowHDTBTSL = {}
        rowHDTBTSL = Object.assign(rowHDTBTSL, this.capitalize(ds[i])) 
        rowHDTBTSL.HDTB_ID = this.hdtb_id
        rowHDTBTSL.MA_PL = this.formProps.txtMaHD_TTKD
        rowHDTBTSL.ID_ERP = this.formProps.txtMaERP
        rowHDTBTSL.MA_BSS = this.formProps.txtMaBSS
        this.dsHDTB_TSL.push(rowHDTBTSL)
      }
    },
    async TaoDuLieu_HDTB_GP(themmoi) {
      this.dsHDTB_GP = []
      let rowHDTBGP = {}
      let ds = await this.LAY_DS_DB_GP_THEO_TBID(this.thuebao_id)
      rowHDTBGP = Object.assign(rowHDTBGP, this.capitalize(ds[0]))
      rowHDTBGP.HDTB_ID = this.hdtb_id
      this.dsHDTB_GP.push(rowHDTBGP)
    },
    async TaoDuLieu_HDTBBC_ADSL(themmoi) {
      this.dsHDBCADSL = []
      let rowHDBCADSL = {}
      let ds = await this.LAY_DS_DB_ADSL_THEO_TBID_BC(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      rowHDBCADSL = Object.assign(rowHDBCADSL, this.capitalize(ds[0]))
      rowHDBCADSL.HDTB_ID = this.hdtb_id
      this.dsHDBCADSL.push(rowHDBCADSL)
    },
    async TaoDuLieu_HDTB_ADSL(themmoi) {
      this.dsHDTB_ADSL = []
      let rowHDTBADSL = {}
      let ds = await this.LAY_DS_DB_ADSL_THEO_TBID(this.thuebao_id)
      rowHDTBADSL = Object.assign(rowHDTBADSL, this.capitalize(ds[0]))
      rowHDTBADSL.HDTB_ID = this.hdtb_id
      this.dsHDTB_ADSL.push(rowHDTBADSL)
    },
    async TaoDuLieu_HDTBBC_CD(themmoi) {
      this.dsHDBCCD = []
      let rowHDBCCD = {}
      let ds = await this.LAY_DS_DB_CD_THEO_TBID_BC(this.tinh.tinhthicong_id, this.thuebao_tc_id)
      if (ds.length > 0) {
        rowHDBCCD = Object.assign(rowHDBCCD, this.capitalize(ds[0]))
      }
      rowHDBCCD.HDTB_ID = this.hdtb_id
      this.dsHDBCCD.push(rowHDBCCD)
    },
    TaoDuLieu_HDTDDVBC(themmoi) {
      console.log("Khởi tạo danh sách tỉnh");
      try{
        this.dsDKDVGTBC = []
        let kieu_dk_dvgt = 0
        for (let i = 0; i < this.dsThueBaoDichVu['danhsach_tinh'].length; i++) {
          let item = this.dsThueBaoDichVu['danhsach_tinh'][i]
          console.log(item);
          if (item.dangky == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
          } else if (item.huy == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
          } else if (item.sua == 1) {
            kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_SUA_DVGT
          } else {
            continue
          }
          let rowHDTDDVBC = {}
          rowHDTDDVBC.HDTB_ID = this.hdtb_id
          rowHDTDDVBC.NGAY_YC = this.formProps.dtpNgayYeuCau
          rowHDTDDVBC.DICHVUGT_ID = item.dichvugt_id
          rowHDTDDVBC.NOIDUNG = item.noidung
          rowHDTDDVBC.KIEU_YC = kieu_dk_dvgt
          rowHDTDDVBC.TTTH = 1
          if (item['cuoc_sd']) {
            rowHDTDDVBC.CUOC_SD = item.cuoc_sd
          } else {
            rowHDTDDVBC.CUOC_SD = 0
          }
          if (item['vat_sd']) {
            rowHDTDDVBC.VAT_SD = item.vat_sd
          } else {
            rowHDTDDVBC.VAT_SD = 0
          }
          console.log(this.dsDKDVGTBC);
          this.dsDKDVGTBC.push(rowHDTDDVBC)
        }
      }
      catch(e){
        console.log(e);
      }
    },
    async TaoDuLieu_HDTBBC(themmoi) {
      // lay khachhang id thue bao id cua ben ban
      // hop dong ban cheo
      try{
        this.dsHDBC = []
        let ds = []
        if (!this.kenh_trang) {
          ds = await this.LAY_DS_DB_THUEBAO_THEO_TBID(this.tinh.tinhthicong_id, this.thuebao_tc_id)
        }
        let rowHDBC = {}
        rowHDBC.HDTB_BAN = this.hdtb_id
        rowHDBC.TINH_THICONG = this.tinh.tinhthicong_id
        rowHDBC.TINH_BAN = this.$root.token.getPhanVungID()
        rowHDBC.TRANG_THAI = 0
        rowHDBC.LOAIHD_BANCHEO = 1
        rowHDBC.THUEBAO_ID_BAN = this.thuebao_id
        rowHDBC.THUEBAO_ID_THICONG = this.thuebao_tc_id
        if (!this.kenh_trang && ds.length > 0) {
          rowHDBC.THANHTOAN_ID_TC = Number(ds[0]['thanhtoan_id'])
          rowHDBC.KHACHHANG_ID_TC = Number(ds[0]['khachhang_id'])
        } else {
          rowHDBC.THANHTOAN_ID_TC = 0
          rowHDBC.KHACHHANG_ID_TC = 0
        }
        rowHDBC.THANHTOAN_ID_BAN = Number(await this.lay_thanhtoan_theo_thuebaoid(this.thuebao_id))
        rowHDBC.KHACHHANG_ID_BAN = this.khachhang_id
        this.dsHDBC.push(rowHDBC)
        this.dsHDTBBC = []
        let rowHDTBBC = {}
        console.log('TDL chi_giao_net', this.chi_giao_net)
        console.log('TDL kenh_trang', this.kenh_trang)

        if (!this.kenh_trang && !this.chi_giao_net) {
          let ds = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.value, 0, 2)
          if (ds.length > 0) {
            console.log("Check bán chéo");
            this.check_hdtb_bancheo = true;
            rowHDTBBC = Object.assign(rowHDTBBC, this.capitalize(ds[0]))
          }
          else{
            this.check_hdtb_bancheo = false;
          }
        } else {
          rowHDTBBC = Object.assign(rowHDTBBC, this.dsHDTB[0])
          rowHDTBBC.THUEBAO_ID = null
          rowHDTBBC.DONVI_ID = null
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_dau.muccuoctb_id
          rowHDTBBC.MUCUOC_TB = Number(await this.lay_cuoctb_theo_cuocid(this.tinh.tinhthicong_id, this.thongso_tc.muccuoctb_id))
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
        }
        rowHDTBBC.MA_TB = this.formProps.txtMaTB
        rowHDTBBC.HDTB_ID = this.hdtb_id
        rowHDTBBC.HDKH_ID = this.hdkh_id
        rowHDTBBC.TTHD_ID = 1
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn
        rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id
        rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
        if (ds.length > 0) {
          if(ds[0].ngay_sn != null && ds[0].ngay_sn != undefined && ds[0].ngay_sn != "")
            rowHDTBBC.NGAY_SN = ds[0].ngay_sn
          else
            rowHDTBBC.NGAY_SN = null;
        }
        else{
          rowHDTBBC.NGAY_SN = null;
        }
        rowHDTBBC.NGAY_CN = this.formatDate(new Date(), 'DD/MM/YYYY HH:mm:ss')
        rowHDTBBC.MAY_CN = this.maycn
        rowHDTBBC.IP_CN = this.ipcn
        rowHDTBBC.GHICHU = this.StringToString(this.formProps.txtGhiChu)
        if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 25) {
          let ds = await this.lay_donvi_dau_cuoi_theo_thuebaoid(this.tinh.tinhthicong_id, rowHDTBBC.THUEBAO_ID)
          rowHDTBBC.DONVI_DAU_ID = ds[0].donvi_dau_id
          rowHDTBBC.DONVI_CUOI_ID = ds[0].donvi_dau_id
        }
        if (
          this.dichvuvt_id == DichVuVienThong.DI_DONG ||
          this.dichvuvt_id == DichVuVienThong.ADSL ||
          this.dichvuvt_id == DichVuVienThong.IMS ||
          this.dichvuvt_id == DichVuVienThong.CO_DINH ||
          this.dichvuvt_id == DichVuVienThong.MEGAWAN ||
          this.dichvuvt_id == DichVuVienThong.TSL ||
          this.dichvuvt_id == DichVuVienThong.METRONET ||
          this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU ||
          this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT ||
          this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          this.dichvuvt_id == DichVuVienThong.GPHONE ||
          this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH||
          this.dichvuvt_id == 25 ||this.dichvuvt_id == 26
        ) {
          rowHDTBBC.STATUS = '0'
        }
        this.dsHDTBBC.push(rowHDTBBC)
      }
      catch(e){
        console.log(e);
      }
    },
    async lay_donvi_dau_cuoi_theo_thuebaoid(tinhid, thuebaoid) {
      let req = {
        tinhthicong_id: tinhid,
        thuebao_id: thuebaoid
      }
      return this.GetDataList(await API.lay_donvi_dau_cuoi_theo_thuebaoid(this.axios, req))
    },
    async LAY_DS_DB_THUEBAO_THEO_TBID(tinhid, thuebaoid) {
      let req = {
        tinhthicong_id: tinhid,
        thuebao_id: thuebaoid
      }
      return this.GetDataList(await API.LAY_DS_DB_THUEBAO_THEO_TBID(this.axios, req))
    },
    async lay_thanhtoan_theo_thuebaoid(thuebaoid) {
      return this.GetData(await API.lay_thanhtoan_theo_thuebaoid(this.axios, thuebaoid))
    },
    async TaoDuLieu_HDBANCHEO_TSL(themmoi, vdaucuoi_id) {
      let rowHDBBC = {}
      rowHDBBC.HDTB_BAN = this.hdtb_id
      if (vdaucuoi_id == 1) {
        rowHDBBC.TINH_THICONG = this.tinh_dau.tinhthicong_id
        rowHDBBC.THUEBAO_ID_THICONG = this.thuebao_tc_id
      } else {
        rowHDBBC.TINH_THICONG = this.tinh_cuoi.tinhthicong_id
        rowHDBBC.THUEBAO_ID_THICONG = this.thuebao_tc_id_tsl
      }
      rowHDBBC.DAUCUOI_ID = vdaucuoi_id
      rowHDBBC.TINH_BAN = this.$root.token.getPhanVungID()
      rowHDBBC.THUEBAO_ID_BAN = this.thuebao_id
      rowHDBBC.TRANG_THAI = 0
      rowHDBBC.LOAIHD_BANCHEO = 1
      if (this.kenh_trang) {
        if (this.khachhang_id != 0) rowHDBBC.KHACHHANG_ID_BAN = this.khachhang_id
        rowHDBBC.KHACHHANG_ID_TC = null
        rowHDBBC.THANHTOAN_ID_TC = null
      } else {
        if (this.khachhang_id != 0) {
          rowHDBBC.KHACHHANG_ID_BAN = this.khachhang_id
          let khachhangtcid = await this.LAY_KHACHHANGID_THICONG(this.tinh_dau.tinhthicong_id, this.khachhang_id)
          if (khachhangtcid == 0) rowHDBBC.KHACHHANG_ID_TC = null
          else {
            rowHDBBC.KHACHHANG_ID_TC = Number(khachhangtcid)
          }
        }
      }
      this.dsHDBC.push(rowHDBBC)
      let rowHDTBBC = {}
      if (vdaucuoi_id == 1) {
        if (!this.kenh_trang) {
          let ds = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_dau.tinhthicong_id, this.thuebao_tc_id, this.cboDichVuVT.value, 0, 2)
          rowHDTBBC = Object.assign(rowHDTBBC, this.capitalize(ds[0]))
        } else {
          rowHDTBBC = Object.assign(rowHDTBBC, this.dsHDTB[0])
          rowHDTBBC.THUEBAO_ID = null
          rowHDTBBC.DONVI_ID = null
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_dau.muccuoctb_id
          rowHDTBBC.MUCUOC_TB = Number(await this.lay_cuoctb_theo_cuocid(this.tinh_dau.tinhthicong_id, this.thongso_tc_dau.muccuoctb_id))
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
        }
        rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id
        // rowHDTBBC.QUAN_ID = quandau_id;
        // rowHDTBBC.PHUONG_ID = phuongdau_id;
        // rowHDTBBC.PHO_ID = phodau_id;
        // rowHDTBBC.AP_ID = apdau_id;
        // rowHDTBBC.KHU_ID = khudau_id;
        // rowHDTBBC.DACDIEM_ID = dacdiemd_id;
        // rowHDTBBC.DOITUONG_ID = thongso_tc_dau.doituong_id;
        // rowHDTBBC.MUCUOCTB_ID = thongso_tc_dau.muccuoctb_id;
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_dau
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_dau
        rowHDTBBC.TINH_ID = this.tinh_dau.tinhthicong_id
        rowHDTBBC.TINH_DC_ID = 1
        rowHDTBBC.HDTB_ID = this.hdtb_id
        rowHDTBBC.HDKH_ID = this.hdkh_id
        rowHDTBBC.TTHD_ID = 1
        rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
        rowHDTBBC.NGAY_CN = this.formatDate(new Date(), null)
        rowHDTBBC.MAY_CN = this.maycn
        rowHDTBBC.IP_CN = this.ipcn
        rowHDTBBC.GHICHU = this.StringToString(this.formProps.txtGhiChu)
      } else {
        if (!this.kenh_trang) {
          let ds = await this.LAY_DANHBA_THEO_THUEBAOID(this.tinh_cuoi.tinhthicong_id, this.thuebao_tc_id_tsl, this.cboDichVuVT.value, 0, 2)
          rowHDTBBC = Object.assign(rowHDTBBC, this.capitalize(ds[0]))
        } else {
          rowHDTBBC = Object.assign(rowHDTBBC, this.dsHDTB[0])
          rowHDTBBC.THUEBAO_ID = null
          rowHDTBBC.DONVI_ID = null
          rowHDTBBC.MUCUOCTB_ID = this.thongso_tc_cuoi.muccuoctb_id
          rowHDTBBC.MUCUOC_TB = Number(await this.lay_cuoctb_theo_cuocid(this.tinh_cuoi.tinhthicong_id, this.thongso_tc_cuoi.muccuoctb_id))
          rowHDTBBC.DOITUONG_ID = this.thongso_tc_dau.doituong_id
        }
        rowHDTBBC.KIEULD_ID = this.Kieuld_tc_id_tsl
        // rowHDTBBC.QUAN_ID = quancuoi_id;
        // rowHDTBBC.PHUONG_ID = phuongcuoi_id;
        // rowHDTBBC.PHO_ID = phocuoi_id;
        // rowHDTBBC.AP_ID = apcuoi_id;
        // rowHDTBBC.KHU_ID = khucuoi_id;
        // rowHDTBBC.DACDIEM_ID = dacdiemld_id;
        // rowHDTBBC.DOITUONG_ID = thongso_tc_cuoi.doituong_id;
        //  rowHDTBBC.MUCUOCTB_ID = thongso_tc_cuoi.muccuoctb_id;
        rowHDTBBC.QUYTRINH_ID = this.quytrinh_tc_tn_cuoi
        rowHDTBBC.HUONGGIAO_TN_ID = this.huonggiao_tc_tn_cuoi
        rowHDTBBC.TINH_ID = this.tinh_cuoi.tinhthicong_id
        rowHDTBBC.TINH_DC_ID = 2
        rowHDTBBC.HDTB_ID = this.hdtb_id
        rowHDTBBC.HDKH_ID = this.hdkh_id
        rowHDTBBC.TTHD_ID = 1
        rowHDTBBC.NGUOI_CN = this.$root.token.getUserName()
        rowHDTBBC.NGAY_CN = this.formatDate(new Date(), null)
        rowHDTBBC.MAY_CN = this.maycn
        rowHDTBBC.IP_CN = this.ipcn
        rowHDTBBC.GHICHU = this.StringToString(this.formProps.txtGhiChu)
      }
      this.dsHDTBBC.push(rowHDTBBC)
    },
    async lay_cuoctb_theo_cuocid(tinhid, muccuocid) {
      return this.GetData(
        await API.lay_cuoctb_theo_cuocid(this.axios, {
          muccuoctb_id: muccuocid,
          tinhthicong_id: tinhid
        })
      )
    },
    async LAY_KHACHHANGID_THICONG(tinhid, khachhangid) {
      let req = { khachhangbanid: khachhangid, tinhthicong_id: tinhid }
      return this.GetData(await API.LAY_KHACHHANGID_THICONG(this.axios, req))
    },
    async lay_thongtin_dbtb_dv(thuebaoid) {
      return this.GetDataList(await API.lay_thongtin_dbtb_dv(this.axios, thuebaoid))
    },
    StringToString(str) {
      if (str === undefined || str === null) {
        return
      }
      let letter
      let text11
      let text22 = ''
      text11 = str
      for (let i = 0; i < text11.length; i++) {
        letter = text11.substr(i, 1)
        if (letter === '\r') {
          text22 = text22 + '\r'
        } else if (letter === '\n') {
          text22 = text22 + '\n'
        } else {
          text22 = text22 + letter
        }
      }
      return text22
    },
    async KIEMTRA_HDTB_KHI_THEMMOI(maTb, hdkhId, dichVuVtId, loaiTbId) {
      let result = 0
      let req = {
        maTb: maTb,
        hdkhId: hdkhId,
        dichVuVtId: dichVuVtId,
        loaiTbId: loaiTbId
      }
      let data = this.GetData(await API.KIEMTRA_HDTB_KHI_THEMMOI(this.axios, req))
      if (data != null) {
        result = Number(data)
      }
      return result == 1
    },
    async KIEMTRA_DB_KHI_THEMMOI(matb, khachhangid, thuebaoid, dichvuvtid) {
      let req = {
        maTb: matb,
        khachHangId: khachhangid,
        thueBaoId: thuebaoid,
        dichVuVtId: dichvuvtid
      }
      let data = this.GetData(await API.KIEMTRA_DB_KHI_THEMMOI(this.axios, req))
      return data == 1
    },
    async kiemtra_tb_cung_makh_tb_pl(magd, thuebaoid) {
      let input = { ma_gd: magd, thuebao_id: thuebaoid }
      return this.GetData(await API.kiemtra_tb_cung_makh_tb_pl(this.axios, input)) == 1
    },
    async lay_huonggiao_tiepnhan_v2(loaihdid, loaitbid, tocdoid, muccuocid, kieuldid, loaikenhid, dichvuvtid) {
      let req = {
        loaihd_id: loaihdid,
        loaitb_id: loaitbid,
        tocdo_id: tocdoid,
        muccuoc_id: muccuocid,
        kieuld_id: kieuldid,
        loaikenh_id: loaikenhid,
        dichvuvt_id: dichvuvtid
      }
      return this.GetDataList(await API.lay_huonggiao_tiepnhan_v2(this.axios, req))
    },
    async KiemTraDL_ThueBao() {
      if (this.formProps.txtMaHD == '' && this.khachhang_id != 0) {
        this.$root.$toast.error('Hãy nhập mã hợp đồng !')
        this.$refs.txtMaHD.focus()
        return false
      }
      if (this.formProps.txtTenTB == '') {
        this.$root.$toast.error('Hãy nhập tên thuê bao !')
        this.$refs.txtTenTB.focus()
        return false
      }
      if (!this.cboKieuLD.value) {
        this.$root.$toast.error('Hãy chọn kiểu yêu cầu cho thuê bao này !')
        return false
      }
      let ktldh = true
      for (let i = 0; i < this.dgvDVGT.data.length; i++) {
        let item = this.dgvDVGT.data[i]
        if (item.dangky == '1') {
          if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_CODINH) {
              ktldh = false
              break
            }
          } else if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_INTERNET) {
              if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH || this.cboLoaihinhTB.value == LoaiHinhTB.WIFI_FIBER) {
                let kt = (await this.FN_KIEMTRA_DK_SD_GREENONLINE(this.thuebao_id, 0, 0)) == 1
                if (kt) {
                  for (let j = 0; j < this.dgvDVGT.data.length; j++) {
                    if (this.dgvDVGT.data[j]['dichvugt_id'] == DichVuGiaTang.GREEN_ONLINE && this.dgvDVGT.data[j]['huy'] != '1') {
                      this.$root.$toast.warning('Thuê bao đang sử dụng dịch vụ Green Online. Bạn phải đăng ký hủy dịch vụ Green Online !')
                      return false
                    }
                  }
                }
              }
            }
            ktldh = false
            break
          } else if (this.cboDichVuVT.value == DichVuVienThong.GPHONE) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_GPHONE) {
              ktldh = false
              break
            }
          } else if (this.cboDichVuVT.value == DichVuVienThong.MEGAWAN||this.cboDichVuVT.value == 26) {
            if (this.kt_kenhphu_ao == true) {
              if (this.cboKieuLD.value != KieuLapDat.TAMDUNG_SD_KP_AO) {
                this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + " là kênh phụ ảo. Bạn phải chọn kiểu lắp đặt là 'Tạm dừng Kênh phụ ảo' !")
                return false
              }
            } else {
              if (this.cboKieuLD.value == KieuLapDat.TAMDUNG_SD_KP_AO) {
                this.$root.$toast.warning("Bạn không được chọn kiểu lắp đặt 'Tạm dừng Kênh phụ ảo' cho thuê bao '" + this.formProps.txtMaTB + "' !")
                return false
              }
            }
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_MEGAWAN) {
              ktldh = false
              break
            }
          } else if (this.cboDichVuVT.value == DichVuVienThong.TSL||this.cboDichVuVT.value == 25) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_TSL) {
              ktldh = false
              break
            }
          } else if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_IMS) {
              ktldh = false
              break
            }
          } else if (this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
            if (item['dichvugt_id'] == DichVuGiaTang.TAMDUNG_CNTT) {
              ktldh = false
              break
            }
          }
        }
      }
      if (this.lvLyDoHuy.data.length == 0 && ktldh == false) {
        this.$root.$toast.error('Bạn chưa chọn lý do tạm dừng!')
        return false
      }
      let kq = await this.KIEMTRA_THAYDOI_GOI_DADV_2(this.thuebao_id, LoaiHopDong.THAY_DOI_DV)
      if (kq != '1') {
        this.$root.$toast.warning(kq)
        return false
      }
      if (this.loaitb_id == LoaiHinhTB.INTERNET_FTTH || this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
        if (this.doituong_id == DUNG_THU.DOITUONG_TB) {
          this.$root.$toast.error('Không thể lập hợp đồng với thuê bao thuộc đối tượng "Thuê bao dùng thử"!')
          return false
        }
      }
      return true
    },
    async KT_DK_HUY_DV() {
      let kt = true
      let mess = ''
      try {
        if (this.KT_KieuLD_TD() == 1) {
          if (!this.KT_DangKy_TD()) {
            mess = 'Kiểu yêu cầu là ' + this.getTextCbo(this.cboKieuLD) + ', bạn chỉ được chọn Đăng ký tạm dừng!'
            kt = false
          } else {
            if (this.ts_ngay_hethan_tamdung != 0) {
              if (this.chkNgay_HHTD.checked == false) {
                this.$root.$toast.warning('Bạn chưa chọn ngày hết hạn tạm dừng của khách hàng !')
                return false
              } else {
                let ngayhh = this.getDate(this.dtpNgayHH_TD)
                let now = new Date()
                if (ngayhh < now) {
                  this.$root.$toast.warning('Ngày hết hạn tạm dừng phải lớn hơn ngày hiện tại!')
                  return false
                }
                let diff = ngayhh - now
                if (diff > this.ts_ngay_hethan_tamdung) {
                  this.$root.$toast.warning('Ngày hết hạn tạm dừng không được quá ' + this.ts_ngay_hethan_tamdung + ' ngày so với ngày hiện tại!')
                  return false
                }
              }
            }
          }
        } else if (this.KT_KieuLD_TD() == 0) {
          if (!this.KT_Mo_TD()) {
            mess = 'Kiểu yêu cầu là' + this.getTextCbo(this.cboKieuLD) + ', bạn chỉ được chọn Hủy tạm dừng!'
            kt = false
          }
        } else {
          if (this.KT_Mo_TD() || this.KT_DangKy_TD()) {
            mess = 'Kiểu yêu cầu là ' + this.getTextCbo(this.cboKieuLD) + ', bạn chỉ được chọn Đng ký/Hủy dịch vụ gia tăng!'
            kt = false
          }
        }
        if (mess.length > 0) this.$root.$toast.warning(mess)
      } catch (e) {
        console.log(e)
      }
      return kt
    },
    KT_Mo_TD() {
      let kt = false
      try {
        for (let i = 0; i < this.dgvDVGT.data.length; i++) {
          if (this.dsThueBaoDichVu['danhsach'][i]['huy'] != '') {
            if (this.dsThueBaoDichVu['danhsach'][i]['huy'] == 1 && this.dsThueBaoDichVu['danhsach'][i]['trangthaitb_id'] == TrangThaiTB.TAMDUNG) {
              kt = true
              break
            }
          }
        }
      } catch (ex) {
        this.$root.$toast.error('Có lỗi: ' + ex.message)
      }
      return kt
    },
    KT_DangKy_TD() {
      let kt = false
      for (let i = 0; i < this.dgvDVGT.data.length; i++) {
        if (this.dsThueBaoDichVu['danhsach'][i]['dangky'] != '') {
          if (Number(this.dsThueBaoDichVu['danhsach'][i]['dangky']) == 1 && Number(this.dsThueBaoDichVu['danhsach'][i]['trangthaitb_id']) == TrangThaiTB.TAMDUNG) {
            kt = true
            break
          }
        }
      }
      return kt
    },
    KT_KieuLD_TD() {
      let kt = 0
      switch (this.dichvuvt_id) {
        case DichVuVienThong.CO_DINH:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_CD || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_CD_TK) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_CD || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_CD_TK) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.DI_DONG:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_DD) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_DD) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.MEGAWAN:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MGWAN || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_KP_AO) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MGWAN || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_KP_AO) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.METRONET:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_METRONET || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_KP_AO) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_METRONET || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_KP_AO) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case 26: //Kênh đa đỉem nội bộ, fix sau này điều chỉnh lại nghiệp vụ
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MGWAN || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_KP_AO) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MGWAN || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MGWAN_TK || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_KP_AO) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.ADSL:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_MEGA || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_INTERNET_TK) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_MEGA || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_INTERNET_TK) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.EMAIL:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_EMAIL) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_EMAIL) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.TSL:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_TSL || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_TSL_TK) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_TSL || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_TSL_TK) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case 25:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_TSL || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_TSL_TK) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_TSL || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_TSL_TK) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.GPHONE:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_GP) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_GP) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.IMS:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_IMS || Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_IMS_TK) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_IMS || Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_IMS_TK) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.MEGA_EYES:
          if (Number(this.cboKieuLD.value) == 126) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == 127) {
            kt = 0
          } else {
            kt = 2
          }
          break
        case DichVuVienThong.ANTOAN_BAOMAT_TT:
        case DichVuVienThong.DICHVU_CNTT:
        case DichVuVienThong.TRUNGTAM_DULIEU:
        case DichVuVienThong.HOINGHI_TRUYENHINH:
          if (Number(this.cboKieuLD.value) == 714) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == 715) {
            kt = 0
          } else {
            kt = 2
          }
          break
        default:
          if (Number(this.cboKieuLD.value) == KieuLapDat.TAMDUNG_SD_CD) {
            kt = 1
          } else if (Number(this.cboKieuLD.value) == KieuLapDat.MOTAMDUNG_SD_CD) {
            kt = 0
          } else {
            kt = 2
          }
          break
      }
      return kt
    },
    async FN_KIEMTRA_DK_SD_GREENONLINE(thuebaoid, kieuyc, kieu) {
      let req = { thuebao_id: thuebaoid, kieu_yc: kieuyc, kieu: kieu }
      return this.GetData(await API.FN_KIEMTRA_DK_SD_GREENONLINE(this.axios, req))
    },
    async KIEMTRA_THAYDOI_GOI_DADV_2(thuebaoid, loaihd) {
      return this.GetData(await API.KIEMTRA_THAYDOI_GOI_DADV_2(this.axios, this.thuebao_id, loaihd))
    },
    async XOA_HDTB(hdtbid) {
      return this.GetData(
        await API.XOA_HDTB(this.axios, {
          hdtb_id: hdtbid
        })
      )
    },
    async tsbtnXoaTB_Click() {
      this.$confirm('Bạn thật sự muốn xóa thuê bao không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        try {
          await this.XOA_HDTB(this.hdtb_id)
          let ds = await this.LAY_DS_HOPDONG_THEO_MA_GD(this.formProps.txtMaGD, LoaiHopDong.THAY_DOI_DV, TrangThaiHD.MOI)
          if (ds.length > 0) {
            await this.HienThiTTHopDongKH(ds)
          } else {
            this.$root.$toast.error('Không tìm thấy thông tin về hợp đồng có mã giao dịch: ' + this.formProps.txtMaGD + '. Bạn hãy kiểm tra lại')
          }
        } catch (error) {
          console.log(error)
        }
      })
    },
    tsbtnHen_Click() {
      if (this.hdkh_id !== 0) {
        this.quyen_duoc_hen = 1
        this.$refs.ThongTinHenKhachHangModal.showModal()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu')
      }
    },
    tsbtnThongTinLH_Click() {
      if (this.hdkh_id !== 0) {
        this.$refs.thongtinlienhe.show()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !')
      }
    },
    tsbtnEmail_Click() {
      if (this.hdkh_id !== 0) {
        this.$refs.thongtinemail.show()
      } else {
        this.$root.$toast.warning('Bạn chưa chọn phiếu !')
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        this.loading(true)
        if ((this.kenh_trang || this.chi_giao_net) && (this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc.muccuoctb_id == 0) && this.dichvuvt_id != DichVuVienThong.TSL && this.dichvuvt_id != 25 ) {
          if (this.kenh_trang) {
            this.$root.$toast.warning('Bạn chưa chọn thông số cho kênh trắng. Ấn vào kiểu yêu cầu để lấy thông tin')
          } else {
            this.$root.$toast.warning('Bạn chưa chọn thông số. Ấn vào kiểu yêu cầu để lấy thông tin')
          }
          return
        }
        if (this.kenh_trang && (this.thongso_ban.muccuoctb_id == 0 || this.thongso_tc_dau.muccuoctb_id == 0 || this.thongso_tc_cuoi.muccuoctb_id == 0) && (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id == 26)) {
          this.$root.$toast.warning('Bạn chưa chọn thông số cho kênh trắng. Ấn vào điểm đầu, điểm cuối để lấy thông tin')
          return
        }
        let kt = false
        for (let i = 0; i < this.dgvDVGT.data.length; i++) {
          if (this.dsThueBaoDichVu['danhsach'][i]['ma_dvgt'] == '1' || this.dsThueBaoDichVu['danhsach'][i]['huy'] == '1') {
            if (this.dsThueBaoDichVu['danhsach'][i]['trangthaitb_id'] == TrangThaiTB.TAMDUNG) {
              kt = true
              break
            }
          }
        }
        if (kt) {
          // Nếu tạm dừng hoặc khóa 2 chiều thì mới thực hiện
          let thanhtoanid = await this.FN_TT_DB_THUEBAO(this.thuebao_id)
          if (thanhtoanid != '-1') {
            let nott = await this.tracuu_no_thanhtoan(thanhtoanid)
            if (nott > 0) {
              if (this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id==25 ||this.dichvuvt_id==26) {
                if (this.ts_khong_kt_nocuoc != 1) {
                  let tienno = await this.TraCuu_No_MaTB(this.thuebao_id)
                  if (tienno > 0) {
                    this.$root.$toast.error('Thuê bao: ' + this.formProps.txtMaTB + ' còn nợ: ' + tienno + ' vnđ.\nPhải thanh toán trước khi thực hiện!')
                    return
                  }
                }
              } else {
                let tienno = await this.TraCuu_No_MaTB(this.thuebao_id)
                if (tienno > 0) {
                  this.$root.$toast.error('Thuê bao: ' + this.formProps.txtMaTB + ' còn nợ: ' + tienno + ' vnđ.\nPhải thanh toán trước khi thực hiện!')
                  return
                }
              }
            }
          }
          if (this.loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
            let dtktra = await this.KIEMTRA_TB_MYTV_CHINHPHU(this.formProps.txtMaTB, 2)
            if (dtktra.length > 0) {
              let dsTS = await this.lay_ds_thamso_md_mats('KHONG_KIEMTRA_MYTV_CHINHPHU')
              let khongkra = -1
              if (dsTS.length > 0) {
                khongkra = Number(dsTS[0].ten_ts)
              }
              // Cho phép làm tiếp nếu đồng ý
              if (khongkra == 1) {
                let accept = await this.$confirm('Thuê bao ' + this.formProps.txtMaTB + ' đang là thuê bao MyTV chính của thuê bao:' + dtktra[0].ma_tb_con, 'Thông báo', {
                  confirmButtonText: 'Có',
                  cancelButtonText: 'Không'
                })
                if (!accept) {
                  return
                }
              } else {
                this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang là thuê bao MyTV chính của thuê bao: ' + dtktra[0].ma_tb_con + '. Yêu cầu hủy chính phụ trước khi thực hiện!')
                return
              }
            }
          }
        }
        if (this.KT_DK_PAYTV()) {
          return
        }
        await this.CapNhat()
      } catch (error) {
        console.log(error)
        this.$toast.error('Có lỗi: ' + JSON.stringify(error))
      } finally {
        this.loading(false)
      }
    },
    async CapNhat() {
      try {
        let check1 = await this.KiemTraDL_KhachHang()
        if (!check1) return
        let check2 = await this.KiemTraDL_ThueBao()
        if (!check2) return
        let check3 = await this.KiemTraDangKyDichVu()
        if (!check3) return
        let check4 = await this.KiemTraDichVuDaDK()
        if (!check4) return
        let check5 = await this.KT_DK_HUY_DV()
        if (!check5) return
        this.$refs.txtMaTB.focus()
        if (!this.getButtonStatus('tsbtnNhapMoi')) {
          let dset = await this.lay_huonggiao_tiepnhan_v2(LoaiHopDong.THAY_DOI_DV, this.loaitb_id, this.tocdotn_id, this.muccuoctn_id, this.cboKieuLD.value, this.vloaikenh_id, this.cboDichVuVT.value)
          let huonggiaotn = 0
          this.luong_id = 0
          console.log('thien11111',dset)
          if (!dset || dset.length <= 0) {
            this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
            return
          }
          huonggiaotn = dset[0].huonggiao_id
          this.quytrinh_id = dset[0].quytrinh_id
          this.luong_id = dset[0].luong_id
          if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25) {
            // region TSL lay quy trinh huong giao
            let tocdo_tc_dau = 0
            let muccuoc_id_tc_dau = 0
            let tocdo_tc_cuoi = 0
            let muccuoc_id_tc_cuoi = 0
            if (!this.kenh_trang) {
              console.log('vào đây kaka',this.dsDBTBBC )
              console.log('vào đây kaka',this.dsDBTBBC_Temp )
              // TODO this.dsDBTBBC[0][0] =  this.dsDBTBBC[0]
              tocdo_tc_dau = this.dsDBTBBC[0]['tocdo_id']
              // TODO this.dsDBTBBC[1][0] =  this.dsDBTBBC_Temp[0]
              tocdo_tc_cuoi = this.dsDBTBBC_Temp[0]['tocdo_id']
            }
            // Lấy hướng giao, quy trình tại điểm đầu
            let rsqtdau = await this.layhuonggiao_tiepnhan(this.tinh_dau.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value,
              tocdo_tc_dau, muccuoc_id_tc_dau, this.Kieuld_tc_id, this.jloaikenh_id, this.dichvuvt_id)
            if (!rsqtdau || rsqtdau.length <= 0) {
              this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm đầu. Liên hệ Admin để xử lý')
              return
            }
            this.quytrinh_tc_tn_dau = rsqtdau[0]['quytrinh_id']
            this.huonggiao_tc_tn_dau = rsqtdau[0]['huonggiao_id']
            let rsqtcuoi = await this.layhuonggiao_tiepnhan(this.tinh_cuoi.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value,
              tocdo_tc_cuoi, muccuoc_id_tc_cuoi, this.Kieuld_tc_id_tsl, this.jloaikenh_id,this.dichvuvt_id)
            if (!rsqtcuoi || rsqtcuoi.length <= 0) {
              this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công điểm cuối. Liên hệ Admin để xử lý')
              return
            }
            this.quytrinh_tc_tn_cuoi = rsqtcuoi[0]['quytrinh_id']
            this.huonggiao_tc_tn_cuoi = rsqtcuoi[0]['huonggiao_id']
          } else {
            let tocdo_tc = 0
            let muccuoc_id_tc = 0
            if ((this.dichvuvt_id == DichVuVienThong.MEGAWAN || this.dichvuvt_id == DichVuVienThong.METRONET || this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id ==25||this.dichvuvt_id==26) && this.dsDBTBBC.length > 0 && this.kenh_trang == false) {
              if (!this.chi_giao_net) {
                tocdo_tc = this.dsDBTBBC[0]['tocdo_id']
              } else {
                tocdo_tc = this.thongso_tc.tocdo_id
              }
            } else if (this.dichvuvt_id == DichVuVienThong.ADSL) {
              tocdo_tc = this.dsDBTBBC[0]['tocdo_id']
              if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_ADSL || this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_FTTH) {
                muccuoc_id_tc = this.dsDBTBBC[0]['muccuoc_id']
              }
            }
            let rsqt = await this.layhuonggiao_tiepnhan(this.tinh.tinhthicong_id, LoaiHopDong.THAY_DOI_DV, this.cboLoaihinhTB.value,
              tocdo_tc, muccuoc_id_tc, this.Kieuld_tc_id, this.jloaikenh_id, this.dichvuvt_id)
            if (!rsqt || rsqt.length <= 0) {
              this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình ở tỉnh Thi Công. Liên hệ Admin để xử lý')
              return
            }
            this.quytrinh_tc_tn = rsqt[0]['quytrinh_id']
            this.huonggiao_tc_tn = rsqt[0]['huonggiao_id']
          }
          let check1 = await this.KIEMTRA_DB_KHI_THEMMOI(this.formProps.txtMaTB, this.khachhang_id, this.thuebao_id, this.dichvuvt_id)
          if (!check1) {
            this.$root.$toast.warning('Thuê bao không tồn tại trong danh bạ, không cập nhật được ')
            return
          }
          if (this.cboKieuLD.value == 39) {
            let dsTS1 = await this.lay_ds_thamso_md_mats('KIEMTRA_TTPH_TAMDUNG_ROIMANG')
            if (dsTS1.length > 0) {
              if (dsTS1[0].ten_ts == '1') {
                let dskt1 = await this.lay_ds_ttph_kh_tamdung_roimang(this.thuebao_id)
                if (dskt1.length <= 0) {
                  this.$root.$toast.warning('Thuê bao chưa nhập hoặc chưa hoàn thành thông tin phản hồi tạm ngừng/rời mạng theoCV80!')
                  return
                }
              }
            }
          }
          if (this.loaitb_id == LoaiHinhTB.PABX) {
            if (this.isdn_sochinh) {
              this.loaisochinh = 0
              for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
                // Nếu thuê bao Pabx là số chính và tạm ngừng theo y/c, khóa do nợ cước.
                // 38: Khóa cước, 31: Khóa gọi đi, 39: Tạm ngừng
                let item = this.dsThueBaoDichVu['danhsach'][i]
                if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH || item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH || item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH)) {
                  for (let j = 0; j < this.ds_isdn.length; j++) {
                    let jtem = this.ds_isdn[j]
                    let check = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(jtem.ma_tb, '0', this.cboDichVuVT.value, LoaiHopDong.THAY_DOI_DV)
                    if (check) {
                      return
                    }
                  }
                }
              }
            } else {
              this.loaisochinh = 1
            }
          }
          // Kiểm tra thuê bao có đang lập hợp đồng khác không
          // Lam thêm ngày 27/08/2010, tạm zem lại ngày 27/09/2010
          let check = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.formProps.txtMaTB, '0', this.cboDichVuVT.value, LoaiHopDong.THAY_DOI_DV)
          if (check) {
            this.$toast.error('Mã thuê bao đã lập hợp đồng khác!')
            return
          }
          /// TuanNA: Thêm code thêm mới hợp đồng thuê bao ISDN -- ngày 16.05.2013
          /// Nếu thuê bao đang tạm dừng là số chính ( thuebao_cha_id null)= > Tạm dừng cho tất cả các số phụ của nó
          if (Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN2B_CD || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN2B_CQ || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN30B_CD || Number(this.cboLoaihinhTB.value) == LoaiHinhTB.ISDN30B_CQ) {
            await this.LAY_DS_ISDN_THEO_THUEBAO_ID(this.thuebao_id)
            if (this.isdn_sochinh && this.KT_DangKy_TD()) {
              for (let i = 0; i < this.ds_isdn.length; i++) {
                /// Kiểm tra trong danh sách thuê bao thuộc số chính có thuê nào lập phụ lục khác không.
                /// Nếu có lập hợp đồng khác và chưa hoàn thiện => không cho làm tiếp
                let kt = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(this.ds_isdn[i].matb, '0', this.dichvuvt_id, LoaiHopDong.THAY_DOI_DV)
                if (kt) {
                  this.$root.$toast.warning('Thuê bao ISDN: ' + this.ds_isdn[i]['ma_tb'] + ' đang được lập phụ lục khác và chưa hoàn thành!. Bạn không thể thực hiện tiếp. ')
                  return
                }
              }
              console.log("Vào 1");
              await this.TaoDuLieu(true)
              await this.TaoDuLieuBC(true)
              if(!this.check_hdtb_bancheo){
                this.$root.$toast.error("không lấy được thuê bao tại tỉnh thi công - thuebao_id: " + this.thuebao_tc_id +"; tinh_id: " + this.tinh.tinhthicong_id);
                return;
              }
              if (this.loi_sinh_magd) {
                this.loi_sinh_magd = false
                return
              }
              await this.sp_thaydoidv_themmoi_hopdong_v2(huonggiaotn)
              if (this.tinh.tinhthicong_id > 0) {
                await this.fn_themmoi_hdbancheo_biendong_v2(huonggiaotn)
              }
              let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
              await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
              await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
              for (let i = 0; i < this.ds_isdn.length; i++) {
                let item = this.ds_isdn[i]
                if (item.trangthaitb_id == TrangThaiTB.THUONG && item.thuebao_cha_id) {
                  await this.TaoDuLieu_HDTB_ISDN(true, item.thuebao_id, i)
                  this.thuebao_id = item.thuebao_id
                  if (this.tinh.tinhthicong_id > 0) {
                    this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
                  }
                  await this.TaoDuLieu_HDTBDV(true)
                  this.TaoDuLieu_THONGTIN_TT(true)
                  await this.TaoDuLieu_CT_TIENHD(true)
                  await this.TaoDuLieu_HDTDDV(true)
                  this.TaoDuLieu_LYDOHUY(true)
                  await this.TaoDuLieu_HDTB_CD_ISDN(true, item.thuebao_id)
                  let req = {
                    vdsHDTB: JSON.stringify(this.dsHDTB),
                    vdsDKDVGT: JSON.stringify(this.dsHDTDDV),
                    vdsTTTT: JSON.stringify(this.dsTTTT),
                    vdsCTTHD: JSON.stringify(this.dsCTTHD),
                    vdsHDTB_CD: JSON.stringify(this.dsHDTB_CD),
                    vdsDK_HUY: JSON.stringify(this.dsDKHUY),
                    vhuonggiao_id: huonggiaotn,
                    vdsHDTBDV: JSON.stringify(this.dsHDTBDV)
                  }
                  console.log('fn_them_hd_dvgt_isdn_bancheo ', req)
                  await this.fn_them_hd_dvgt_isdn_bancheo(huonggiaotn, req)
                }
              }
            } else {
              console.log("Vào 2");
              await this.TaoDuLieu(true)
              await this.TaoDuLieuBC(true)
              if (this.loi_sinh_magd) {
                this.loi_sinh_magd = false
                return
              }
              await this.sp_thaydoidv_themmoi_hopdong_v2(huonggiaotn)
              if (this.tinh.tinhthicong_id > 0) {
                await this.fn_themmoi_hdbancheo_biendong(huonggiaotn)
              }
              /// TuanNA: thêm code thêm mới, cập nhật biến động lý do hủy
              /// Ngày 25.06.2012
              let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
              await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
              await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
            }
          } else {
            // không phải ISDN
            console.log("Vào 3");
            await this.TaoDuLieu(true)
            await this.TaoDuLieuBC(true)
            if (this.loi_sinh_magd) {
              this.loi_sinh_magd = false
              return
            }
            if(!this.check_hdtb_bancheo){
              this.$root.$toast.error("không lấy được thuê bao tại tỉnh thi công - thuebao_id: " + this.thuebao_tc_id +"; tinh_id: " + this.tinh.tinhthicong_id);
              return;
            }
            await this.sp_thaydoidv_themmoi_hopdong_v2(huonggiaotn)
            if (this.tinh.tinhthicong_id > 0 || (this.tinh_dau != null && this.tinh_dau.tinhthicong_id > 0)) {
              await this.fn_themmoi_hdbancheo_biendong_v2(huonggiaotn)
            }
            let vbdtbid = await this.fn_tt_bd_thuebao(this.hdtb_id)
            await this.TaoDuLieu_BD_LYDOHUY(vbdtbid)
            await this.sp_insert_bd_lydohuy(this.dsBD_LYDOHUY)
          }
          if (this.loaitb_id == LoaiHinhTB.PABX) {
            if (this.isdn_sochinh) {
              for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
                let item = this.dsThueBaoDichVu['danhsach'][i]
                // Nếu thuê bao Pabx là số chính và tạm ngừng theo y/c, khóa do nợ cước.
                // 38: Khóa cước, 31: Khóa gọi đi, 39: Tạm ngừng
                if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH || item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH || item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH)) {
                  this.hdtb_id_cha = this.hdtb_id
                  for (let j = 0; j < this.ds_isdn.length; j++) {
                    let jtem = this.ds_isdn[j]
                    if (jtem.thuebao_cha_id) {
                      this.thuebao_id = jtem.thuebao_id
                      if (this.tinh.tinhthicong_id > 0) {
                        this.thuebao_tc_id = await this.SP_LAY_THUEBAOID_THICONG_THEO_MATB(this.tinh.tinhthicong_id, this.formProps.txtMaTB)
                      }
                      this.formProps.txtMaTB = jtem['ma_tb']
                      this.formProps.txtDiaChiLD = jtem['diachi_ld']
                      this.doituong_id = jtem['doituong_id']
                      await this.ThemHDThueBao()
                    }
                  }
                }
              }
            }
          }
          let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(ds)
        } else {
          if (this.formProps.txtMaTB) {
            let check = await this.kiemtra_tb_cung_makh_tb_pl(this.formProps.txtMaGD, this.thuebao_id)
            if (!check) {
              this.$root.$toast.warning('Thuê bao này không cùng mã khách hàng đang thực hiện thay đổi dịch vụ')
              this.$refs.txtMaTB.focus()
            }
          }
          if (!this.KiemTraTBTrongHD(this.formProps.txtMaTB, this.lvwThueBao.data)) {
            this.$root.$toast.warning('Thuê bao có mã: ' + this.formProps.txtMaTB + ' không có trong HĐ/PL. Bạn không thể cập nhập thông tin,hãy kiểm tra lại!')
          }
          let dset = await this.lay_huonggiao_tiepnhan_v2(LoaiHopDong.THAY_DOI_DV, this.loaitb_id, this.tocdotn_id, this.muccuoctn_id, this.cboKieuLD.value, this.vloaikenh_id, this.cboDichVuVT.value)
          let huonggiaotn = 0
          this.luong_id = 0
          if (dset.length <= 0) {
            this.$root.$toast.warning('Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!')
          }
          huonggiaotn = dset[0].huonggiao_id
          this.quytrinh_id = dset[0].quytrinh_id
          this.luong_id = dset[0].luong_id
          console.log("Vào 4");
          await this.TaoDuLieu(false)
          await this.TaoDuLieuBC(false)

          if(!this.check_hdtb_bancheo){
            this.$root.$toast.error("không lấy được thuê bao tại tỉnh thi công - thuebao_id: " + this.thuebao_tc_id +"; tinh_id: " + this.tinh.tinhthicong_id);
            return;
          }
          await this.sp_thaydoidv_capnhat_hopdong_v2(huonggiaotn);
          if (this.tinh.tinhthicong_id > 0) {
            await this.fn_capnhat_hdbancheo_biendong_dvgt(huonggiaotn)
          }
          let ds = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(this.hdkh_id)
          await this.HienThiDanhSacHDTB(ds)
        }
        await this.NapThongTinDichVuThueBao()
        this.setActiveActions(3)
        this.$root.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công!')
      } catch (e) {
        console.log(e);
        this.$root.$toast.error(JSON.stringify(e))
      }
    },
    async sp_thaydoidv_capnhat_hopdong_v2(huonggiaotn){
      let params = {
        tinhthicong_id: this.tinh.tinhthicong_id,
        huonggiao_id: huonggiaotn,
        p_js_hdkh: JSON.stringify(this.dsHDKH),
        p_js_hdtb: JSON.stringify(this.dsHDTB),
        p_js_hdtb_dv: JSON.stringify(this.dsHDTBDV),
        p_js_dangky_dvgt: JSON.stringify(this.dsHDTDDV),
        p_js_tttt: JSON.stringify(this.dsTTTT),
        p_js_ctthd: JSON.stringify(this.dsCTTHD),
        p_js_dangky_huy: JSON.stringify(this.dsDKHUY),
        p_js_dangky_dvk: JSON.stringify([])
      }
      console.log('sp_thaydoidv_capnhat_hopdong_v2', params)
      await API.sp_thaydoidv_capnhat_hopdong_v2(this.axios, params )
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            if (response.data.data.returning != 1) {
              let message = JSON.parse(response.data.data.p_js_returnds)
              this.$root.$toast.error('Cập nhật hợp đồng thất bại: ' + message.MESSAGE)
            } else {
              this.$root.$toast.success('Cập nhật hợp đồng bán chéo biến động thành công')
            }
          } else {
            this.$root.$toast.error('Lỗi Cập nhật hợp đồng bán chéo biến động:' + response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ' + error.data.message)
        })
    },
    async fn_capnhat_hdbancheo_biendong_dvgt(){
      let params = {

        js_hdBANCHEO: JSON.stringify(this.dsHDBC),
        js_hdtbBANCHEO: JSON.stringify(this.dsHDTBBC),
        js_dsHDBCDVGT: JSON.stringify(this.dsDKDVGTBC),
      }
      console.log('fn_capnhat_hdbancheo_biendong_dvgt', params)
      await API.fn_capnhat_hdbancheo_biendong_dvgt(this.axios, { p_ds_para: JSON.stringify(params) })
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            if (response.data.data.returning != 1) {
              let message = JSON.parse(response.data.data.p_js_returnds)
              this.$root.$toast.error('Cập nhật thất bại: ' + message.MESSAGE)
            } else {
              this.$root.$toast.success('Cập nhật hợp đồng bán chéo thành công')
            }
          } else {
            this.$root.$toast.error('Lỗi Cập nhật hợp đồng bán chéo:' + response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ' + error.data.message)
        })
    },
    async fn_them_hd_dvgt_isdn_bancheo (huonggiaotn, req) {
      try {
        return this.GetData(await API.fn_them_hd_dvgt_isdn_bancheo(this.axios, req))
      } catch (e) {}
    },
    async sp_insert_bd_lydohuy(dsBDLYDOHUY) {
      let req = {
        p_js_bd_lydohuy: JSON.stringify(dsBDLYDOHUY)
      }
      await API.sp_insert_bd_lydohuy(this.axios, req)
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            console.log('sp_insert_bd_lydohuy response success')
          } else {
            console.log('sp_insert_bd_lydohuy response', response)
            this.$root.$toast.error('Lỗi: ' + response.data.message)
          }
        })
        .catch((error) => {
          console.log('sp_insert_bd_lydohuy error', error)
          this.$root.$toast.error('Có lỗi: ' + error)
        })
    },
    async fn_themmoi_hdbancheo_biendong() {
      let params = {
        HD_BANCHEO: this.dsHDBC,
        HDTB_BANCHEO: this.dsHDTBBC,
        HDBC_MGWAN: this.dsHDBCMGWAN,
        HDBC_ADSL: this.dsHDBCADSL,
        HDBC_CD: this.dsHDBCCD,
        DANGKY_DVGT: this.dsDKDVGTBC,
        HDBC_TSL: this.dsHDBCTSL,
        HDBC_IMS: this.dsHDBCIMS
      }
      return this.GetData(await API.fn_themmoi_hdbancheo_biendong(this.axios, { p_ds_para: JSON.stringify(params) }))
    },
    async fn_themmoi_hdbancheo_biendong_v2(huonggiaotn) {
      let params = {
        huonggiao_id: huonggiaotn,
        js_hdBANCHEO: this.dsHDBC,
        js_hdtbBANCHEO: this.dsHDTBBC,
        js_dsHDBCWAN: this.dsHDBCMGWAN,
        js_dsHDBCADSL: this.dsHDBCADSL,
        js_dsHDBCCD: this.dsHDBCCD,
        js_dsHDBCDVGT: this.dsDKDVGTBC,
        js_dsHDBCTSL: this.dsHDBCTSL,
        js_dsHDBCIMS: this.dsHDBCIMS,
        js_dsLYDOHUYBC: this.dsDK_HUYBC,
        js_dsBDLYDOHUYBC: this.dsBD_HUYBC
      }
      console.log('fn_themmoi_hdbancheo_biendong_v2', params)
      await API.fn_themmoi_hdbancheo_biendong_v2(this.axios, { p_ds_para: JSON.stringify(params) })
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            if (response.data.data.returning != 1) {
              let message = JSON.parse(response.data.data.p_js_returnds)
              this.$root.$toast.error('Thêm hợp đồng thất bại: ' + message.MESSAGE)
            } else {
              this.$root.$toast.success('Thêm mới hợp đồng bán chéo biến động thành công')
            }
          } else {
            this.$root.$toast.error('Lỗi thêm mới hợp đồng bán chéo biến động:' + response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ' + error.data.message)
        })
    },
    async lay_ds_ttph_kh_tamdung_roimang(vthuebaoid) {
      return this.GetDataList(await API.lay_ds_ttph_kh_tamdung_roimang(this.axios, vthuebaoid))
    },
    // thaydoidv.ThemMoi_HopDongTB_V2
    async sp_thaydoidv_themmoi_hopdong_v2_v2(huonggiaotn) {
      let req = {
        p_huonggiao_id: huonggiaotn,
        p_js_HDKH: JSON.stringify([]),
        p_js_HDTB: JSON.stringify(this.dsHDTB),
        p_js_HDTB_DV: JSON.stringify(this.dsHDTBDV),
        p_js_dangky_dvgt: JSON.stringify(this.dsHDTDDV),
        p_js_TTTT: JSON.stringify(this.dsTTTT),
        p_js_CTTHD: JSON.stringify(this.dsCTTHD),
        p_js_HDTB_CD: JSON.stringify(this.dsHDTB_CD),
        p_js_HDTB_ADSL: JSON.stringify(this.dsHDTB_ADSL),
        p_js_HDTB_IMS: JSON.stringify([]),
        p_js_HDTB_GP: JSON.stringify(this.dsHDTB_GP),
        p_js_HDTB_MGW: JSON.stringify(this.dsHDTB_MGW),
        p_js_HDTB_TSL: JSON.stringify(this.dsHDTB_TSL),
        p_js_HDTB_DD: JSON.stringify(this.dsHDTB_DD),
        p_js_HDTB_CNTT: JSON.stringify(this.dsHDTB_CNTT),
        p_js_dangky_huy: JSON.stringify(this.dsDKHUY),
        p_js_dangky_dvk: JSON.stringify([])
      }
      await API.sp_thaydoidv_themmoi_hopdong_v2(this.axios, req)
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Thêm mới hợp đồng thành công')
          } else {
            this.$root.$toast.error('Lỗi thêm mới hợp đồng:' + response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ', error.data.message)
        })
    },
    async sp_thaydoidv_themmoi_hopdong_v2(huonggiaotn) {
      let req = {
        p_huonggiao_id: huonggiaotn,
        p_js_HDKH: JSON.stringify(this.dsHDKH),
        p_js_HDTB: JSON.stringify(this.dsHDTB),
        p_js_HDTB_DV: JSON.stringify(this.dsHDTBDV),
        p_js_dangky_dvgt: JSON.stringify(this.dsHDTDDV),
        p_js_TTTT: JSON.stringify(this.dsTTTT),
        p_js_CTTHD: JSON.stringify(this.dsCTTHD),
        p_js_HDTB_CD: JSON.stringify(this.dsHDTB_CD),
        p_js_HDTB_ADSL: JSON.stringify(this.dsHDTB_ADSL),
        p_js_HDTB_IMS: JSON.stringify(this.dsHDTB_IMS),
        p_js_HDTB_GP: JSON.stringify(this.dsHDTB_GP),
        p_js_HDTB_MGW: JSON.stringify(this.dsHDTB_MGW),
        p_js_HDTB_TSL: JSON.stringify(this.dsHDTB_TSL),
        p_js_HDTB_DD: JSON.stringify(this.dsHDTB_DD),
        p_js_HDTB_CNTT: JSON.stringify(this.dsHDTB_CNTT),
        p_js_dangky_huy: JSON.stringify(this.dsDKHUY),
        p_js_dangky_dvk: JSON.stringify([])
      }
      await API.sp_thaydoidv_themmoi_hopdong_v2(this.axios, req)
        .then((response) => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Thêm mới hợp đồng thành công')
          } else {
            this.$root.$toast.error('Lỗi thêm mới hợp đồng:', response.data.message)
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ', error.data.message)
        })
    },
    KiemTraTBTrongHD(matb, lvwThueBao) {
      console.log(matb)
      console.log(lvwThueBao)
      let result = false
      for (let i = 0; i < lvwThueBao.length; i++) {
        if (matb.toUpperCase() === lvwThueBao[i].text.toUpperCase()) {
          result = true
          break
        } else {
          result = false
        }
      }
      return result
    },
    async TaoDuLieu (themmoi) {
      try {
        await this.TaoDuLieu_HDKH(themmoi)
        await this.TaoDuLieu_HDTB(themmoi)
        await this.TaoDuLieu_HDTBDV(true)
        // Hiếu bổ sung hàm tạo dữ liệu thông tin thanh toán 15/09/2010
        this.TaoDuLieu_THONGTIN_TT(themmoi)
        await this.TaoDuLieu_CT_TIENHD(true)
        await this.TaoDuLieu_HDTDDV(themmoi)
        /// TuanNA: Thêm các hàm tạo dữ liệu các bảng hợp đồng con
        /// Ngày 01.06.2012
        this.dsHDTB_ADSL = []
        this.dsHDTB_CD = []
        this.dsHDTB_GP = []
        this.dsHDTB_TSL = []
        this.dsHDTB_MGW = []
        this.dsHDTB_DD = []
        this.dsHDTB_IMS = []
        this.dsHDTB_CNTT = []
        this.TaoDuLieu_LYDOHUY(themmoi)
        if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
          await this.TaoDuLieu_HDTB_ADSL(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
          await this.TaoDuLieu_HDTB_CD(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.GPHONE) {
          await this.TaoDuLieu_HDTB_GP(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.TSL||this.cboDichVuVT.value == 25) {
          await this.TaoDuLieu_HDTB_TSL(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.MEGAWAN ||
          this.cboDichVuVT.value == DichVuVienThong.METRONET||this.cboDichVuVT.value == 26) {
          await this.TaoDuLieu_HDTB_MGWAN(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.DI_DONG) {
          await this.TaoDuLieu_HDTB_DD(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
          await this.TaoDuLieu_HDTB_IMS(themmoi)
        }
        if (this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT ||
          this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT ||
          this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU ||
          this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          await this.TaoDuLieu_HDTB_CNTT(themmoi)
        }
      } catch (e) {console.log('TaoDuLieu', e)}
    },
    async TaoDuLieu_HDTB_IMS(themmoi) {
      this.dsHDTB_IMS = []
      let rowHDTBIMS = {}
      let ds = await this.LAY_DS_DB_IMS_THEO_TBID(this.thuebao_id)
      rowHDTBIMS = Object.assign(rowHDTBIMS, this.capitalize(ds[0]))
      rowHDTBIMS.HDTB_ID = this.hdtb_id
      this.dsHDTB_IMS.push(rowHDTBIMS)
    },
    async TaoDuLieu_HDTB_DD(themmoi) {
      this.dsHDTB_DD = []
      let rowHDTBDD = {}
      let ds = await this.LAY_DS_DB_DD_THEO_TBID(this.thuebao_id)
      rowHDTBDD = Object.assign(rowHDTBDD, this.capitalize(ds[0]))
      rowHDTBDD.HDTB_ID = this.hdtb_id
      this.dsHDTB_DD.push(rowHDTBDD)
    },
    async TaoDuLieu_HDTB_MGWAN(themmoi) {
      this.dsHDTB_MGW = []
      let rowHDTBMGW = {}
      let ds = await this.LAY_DS_DB_MEGAWAN_THEO_TBID(this.thuebao_id)
      rowHDTBMGW = Object.assign(rowHDTBMGW, this.capitalize(ds[0]))
      rowHDTBMGW.HDTB_ID = this.hdtb_id
      rowHDTBMGW.MA_PL = this.formProps.txtMaHD_TTKD
      rowHDTBMGW.ID_ERP = this.formProps.txtMaERP
      rowHDTBMGW.MA_BSS = this.formProps.txtMaBSS
      this.dsHDTB_MGW.push(rowHDTBMGW)
    },
    async layhuonggiao_tiepnhan(tinhid, loaihdid, loaitbid, tocdoid, muccuocid, kieuldid, loaikenhid,dichvuvtid) {
      let data = {
        tinhthicong_id: tinhid,
        loaihd_id: loaihdid,
        loaitb_id: loaitbid,
        tocdo_id: tocdoid,
        muccuoc_id: muccuocid,
        kieuld_id: kieuldid,
        loaikenh_id: loaikenhid,
        dichvuvt_id: dichvuvtid,
      }
      let ds = []
      await API.post_layhuonggiao_tiepnhan_v4(this.axios, data)
        .then((response) => {
          console.log(response)
          if (response && response.data && response.data.error_code == 'BSS-00000000') {
            ds = response.data.data == null ? [] : response.data.data
          }
        })
        .catch((error) => {
          console.log(error)
          this.$root.$toast.error('Lỗi layhuonggiao_tiepnhan' + error.data.message)
        })
      return ds
    },
    getButtonStatus(id) {
      let index = this.actions.findIndex((item) => item.id == id)
      if (index >= 0) {
        return this.actions[index].active
      }
      return false
    },
    CheckIsNumber(value) {
      return !isNaN(parseInt(value)) && isFinite(value)
    },
    async KiemTraDL_KhachHang() {
      if (!this.formProps.txtTenKH) {
        this.$root.$toast.warning('Hãy nhập tên khách hàng !')
        this.$refs.txtTenKH.Focus()
        return false
      }
      if (!this.formProps.txtMaKH && this.khachhang_id != 0) {
        this.$root.$toast.warning('Kiểm tra lại mã khách hàng !')
        this.$refs.txtMaKH.Focus()
        return false
      }
      let ktchonDVGT = false
      for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
        let item = this.dsThueBaoDichVu['danhsach'][i]
        console.log('dsThueBaoDichVu', item)
        if (item.dangky == 1 || item.sua == 1 || item.huy == 1) {
          ktchonDVGT = true
          break
        }
      }
      if (!ktchonDVGT) {
        this.$root.$toast.warning('Bạn chưa đăng ký/hủy dịch vụ gia tăng nào. Không thể cập nhật thông tin')
        return false
      }
      if (this.dsThueBaoDichVu['danhsach_tinh'].length <= 0) {
        this.$root.$toast.warning('Không có thông tin dịch vụ gia tăng tỉnh thi công. Không thể cập nhật thông tin')
        return false
      }
      if ((this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id ==25) && this.dsThueBaoDichVu['danhsach_tinh_tsl'].length <= 0) {
        this.$root.$toast.warning('Không có thông tin dịch vụ gia tăng điểm cuối. Không thể cập nhật thông tin')
        return false
      }
      ktchonDVGT = false
      for (let i = 0; i < this.dsThueBaoDichVu['danhsach_tinh'].length; i++) {
        let item = this.dsThueBaoDichVu['danhsach_tinh'][i]
        console.log('dsThueBaoDichVu tinh', item)
        if (item.dangky == 1 || item.sua == 1 || item.huy == 1) {
          ktchonDVGT = true
          break
        }
      }
      if (!ktchonDVGT) {
        this.$root.$toast.warning('Bạn chưa đăng ký/hủy dịch vụ gia tăng tỉnh thi công. Không thể cập nhật thông tin')
        return false
      }
      if (this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id ==25) {
        ktchonDVGT = false
        for (let i = 0; i < this.dsThueBaoDichVu['danhsach_tinh_tsl'].length; i++) {
          let item = this.dsThueBaoDichVu['danhsach_tinh_tsl'][i]
          console.log('dsThueBaoDichVu danhsach_tinh_tsl', item)
          if (item.dangky == 1 || item.sua == 1 || item.huy == '1') {
            ktchonDVGT = true
            break
          }
        }
        if (!ktchonDVGT) {
          this.$root.$toast.warning('Bạn chưa đăng ký/hủy dịch vụ gia tăng tỉnh thi công. Không thể cập nhật thông tin')
          return false
        }
      }
      // Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
      if (this.formProps.txtMaKH) {
        let check = await this.KIEMTRA_MAKH_MTT(1, LoaiHopDong.THAY_DOI_DV, this.formProps.txtMaKH.trim())
        if (!check) {
          this.$root.$toast.warning('Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.txtMaKH.focus()
          return false
        }
      }
      if (this.formProps.txtMaGD) {
        let check = await this.KIEMTRA_MAKH_MTT(3, LoaiHopDong.THAY_DOI_DV, this.formProps.txtMaGD.trim())
        if (!check) {
          this.$root.$toast.warning('Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh. Bạn hãy kiểm tra lại!')
          this.$refs.txtMaKH.focus()
          return false
        }
      }
      return true
    },
    async KiemTraDichVuDaDK() {
      let dt = this.dsThueBaoDichVu['danhsach']
      console.log('thien2222',dt)
      for (let i = 0; i < dt.length; i++) {
        let item = dt[i]
        if (item.dangky == '1' || item.huy == 1) {
          let check = await this.KIEMTRADICHVUDADK(this.thuebao_id, item.dichvugt_id, Number(item.dangky))
          console.log('thien3333',check)
          if (check) {
            this.$root.$toast.warning('Dịch vụ ' + item['ten_dvgt'] + ' đã đăng ký và chưa hoàn thành !')
            return false
          }
        }
      }
      return true
    },
    async KIEMTRADICHVUDADK(thuebaoid, dichvugtid, kieu) {
      let req = { thuebao_id: thuebaoid, dichvugt_id: dichvugtid, kieu_id: kieu }
      let data = this.GetData(await API.KIEMTRADICHVUDADK(this.axios, req))
      return data == '1'
    },
    async KiemTraDangKyDichVu() {
      for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
        let item = this.dsThueBaoDichVu['danhsach'][k]
        if (item['dangky'] == '1') {
          let check = await this.KIEMTRA_DANGKY_DVGT(this.thuebao_id, item['dichvugt_id'])
          console.log("check: ", check);
          if (check) {
            this.$root.$toast.warning('Không đăng ký thêm dịch vụ đối với thuê bao này !')
            return false
          }
        }
        if ((item['dangky'] == '1' || item['sua'] == '1') && item['flag'] == '1' && item['noidung'] == '') {
          this.$root.$toast.warning('Bạn chưa nhập "' + item['thongbao'] + '" vào ô Nội dung cho dịch vụ "' + item['ten_dvgt'] + '!')
          this.$refs.dgvDVGT.selectRow(k, true)
          // TODO check
          // dgvDVGT.CurrentCell = dgvDVGT.Rows[i].Cells["noidung"];
          return false
        }
        if ((item['dangky'] == '1' || item['sua'] == '1') && item['kieu_dl'] == '1' && item['noidung'] != '' && item['noidung'] != undefined && item['noidung'] != null) {
          let s = item.noidung
          if (!this.CheckIsNumber(this.Unformat_tien(s))) {
            this.$root.$toast.warning('Nội dung của dịch vụ "' + item['ten_dvgt'] + '" phải là kiểu số !')
            this.$refs.dgvDVGT.selectRow(k, true)
            return false
          }
        }
      }
      if (this.cboDichVuVT.value == DichVuVienThong.GPHONE) {
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          // Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if ((item.dangky == '1' && item.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE) || item.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE || item.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE) {
            // Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
              // nếu đang sử dụng khóa 1 chiều theo yêu cầu
              let item1 = this.dsThueBaoDichVu['danhsach'][i]
              if (item1.sudung && item.sudung != null && item.sudung.toLowerCase() == 'sử dụng' && item1.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE) {
                this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang sử dụng dịch vụ khóa 1 chiều theo yêu cầu!. Bạn không thể đăng ký thêm dịch vụ tạm dừng hoặc khóa cước!')
                return false
              }
              // Nếu chọn đồng thời khóa cước hoặc khóa 1 chiều hoặc tạm dừng thì return
              // Đồng thời khóa 1 chiều và khóa cước
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE && item1.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && item1['dangky'] == '1') || (item.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE && item1['dangky'] == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa 1 chiều theo yêu cầu' và 'Khóa cước Gphone'")
                return false
              }
              // Đồng thời khóa cước & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước Gphone' và 'Tạm dừng sử dụng'")
                return false
              }
              // Đồng thời khóa 1 chiều & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE && item1.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa 1 chiều theo yêu cầu' và 'Tạm dừng sử dụng'")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_GPHONE && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'\n Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.KHOACUOC_GPHONE && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa cước'\n Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.KHOAMOTCHIEU_GPHONE && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa gọi đi'\n Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
            }
          }
        }
      } else if (this.cboDichVuVT.value == DichVuVienThong.CO_DINH) {
        // Xét đối với cố định
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          // Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          // 38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH || item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH || item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH)) {
            // Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
              // nếu đang sử dụng khóa 1 chiều theo yêu cầu
              let item1 = this.dsThueBaoDichVu['danhsach'][i]
              if (item1.sudung && item.sudung != null && item.sudung.toLowerCase() == 'sử dụng' && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH) {
                this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang sử dụng dịch vụ khóa gọi đi!. Bạn không thể đăng ký thêm dịch vụ tạm dừng hoặc khóa cước!')
                return false
              }
              // Nếu chọn đồng thời khóa cước hoặc khóa 1 chiều hoặc tạm dừng thì return
              // Đồng thời khóa 1 chiều và khóa cước
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH && item1['dangky'] == '1') || (item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH && item1['dangky'] == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Khóa cước'")
                return false
              }
              // Đồng thời khóa cước & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước' và 'Tạm dừng'")
                return false
              }
              // Đồng thời khóa 1 chiều & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Tạm dừng theo yêu cầu'")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_CODINH && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.KHOACUOC_CODINH && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa cước'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_CODINH && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa gọi đi'.Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
            }
          }
        }
      } else if (this.cboDichVuVT.value == DichVuVienThong.ADSL) {
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          // Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          // 38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.KHOACUOC_INTERNET || item.dichvugt_id == DichVuGiaTang.TAMDUNG_INTERNET)) {
            // Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
              let item1 = this.dsThueBaoDichVu['danhsach'][i]
              // Đồng thời khóa cước & tạm dừng
              if (item.dichvugt_id == DichVuGiaTang.KHOACUOC_INTERNET && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_INTERNET && item1['dangky'] == '1') {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước' và 'Tạm dừng'")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_INTERNET && item1.dichvugt_id != item.dichvugt_id && item1['dangky'] == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.KHOACUOC_INTERNET && item1.dichvugt_id != item.dichvugt_id && item1['dangky'] == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa cước'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
            }
          }
        }
      } else if (this.cboDichVuVT.value == DichVuVienThong.INTERNET_MYTV) {
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          // Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          // 38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.CB_FF_12 || item.dichvugt_id == DichVuGiaTang.CB_FF_24)) {
            // Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            let count = await this.kiem_tra_dvgt(this.thuebao_id, DichVuGiaTang.CB_FF_12, DichVuGiaTang.CB_FF_24)
            if (count > 0) {
              this.$root.$toast.warning("Thuê bao đã từng hủy khuyến mại 'Combo-Fafilm' Không thể đăng ký lại!")
              return false
            }
          }
        }
      } else if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          // Kiểm tra nếu thuê bao đang khóa 1 chiều thì không cho đăng ký tạm dựng hoặc khóa cước
          // 38: Khóa cước, 39: tạm dừng, 31: Khóa gọi đi
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if (item.dangky == '1' && (item.dichvugt_id == DichVuGiaTang.KHOACUOC_IMS || item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS || item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS)) {
            // Nếu đăng ký tạm dừng hoăc khóa cước hoặc khóa 1 chiều
            for (let i = 0; i < this.dsThueBaoDichVu['danhsach'].length; i++) {
              // nếu đang sử dụng khóa 1 chiều theo yêu cầu
              let item1 = this.dsThueBaoDichVu['danhsach'][i]
              console.log(111111111111)
              console.log(item1)
              if (item1.sudung && item.sudung != null && item.sudung.toLowerCase() == 'sử dụng' && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS) {
                this.$root.$toast.warning('Thuê bao ' + this.formProps.txtMaTB + ' đang sử dụng dịch vụ khóa gọi đi!. Bạn không thể đăng ký thêm dịch vụ tạm dừng hoặc khóa cước!')
                return false
              }
              // Nếu chọn đồng thời khóa cước hoặc khóa 1 chiều hoặc tạm dừng thì return
              // Đồng thời khóa 1 chiều và khóa cước
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_IMS && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS && item1['dangky'] == '1') || (item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_IMS && item1['dangky'] == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Khóa cước'")
                return false
              }
              // Đồng thời khóa cước & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.KHOACUOC_IMS && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dichvugt_id == DichVuGiaTang.KHOACUOC_IMS && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa cước' và 'Tạm dừng'")
                return false
              }
              // Đồng thời khóa 1 chiều & tạm dừng
              if ((item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS && item1.dangky == '1') || (item.dichvugt_id == DichVuGiaTang.KHOAGOIDI_IMS && item1.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dangky == '1')) {
                this.$root.$toast.warning("Bạn không được đăng ký đồng thời dịch vụ 'Khóa chiều gọi đi' và 'Tạm dừng theo yêu cầu'")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Tạm dừng'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa cước'. Bạn không được đăng ký các dịch vụ khác!")
                return false
              }
              /* // Nếu đã đăng ký tạm dừng, khóa cước, khóa gọi đi thì không cho phép đăng ký các dịch vụ khác
              if (item.dichvugt_id == DichVuGiaTang.TAMDUNG_IMS && item1.dichvugt_id != item.dichvugt_id && item1.dangky == '1') {
                this.$root.$toast.warning("Thuê bao đã đăng ký dịch vụ 'Khóa gọi đi'.Bạn không được đăng ký các dịch vụ khác!")
                return false
              } */
            }
          }
        }
      }
      return true
    },
    async kiem_tra_dvgt(thuebaoid, madvgt, madvgt1) {
      let req = { thuebao_id: thuebaoid, ma_dvgt: madvgt, ma_dvgt1: madvgt1 }
      return this.GetData(await API.kiem_tra_dvgt(this.axios, req))
    },
    async KIEMTRA_DANGKY_DVGT(thuebaoid, dichvugtid) {
      let req = { thuebao_id: thuebaoid, dichvugt_id: dichvugtid }
      let data = this.GetData(await API.KIEMTRA_DANGKY_DVGT(this.axios, req))
      return data == '1'
    },
    async KIEMTRA_MAKH_MTT(kieu, loaihdId, ma) {
      let req = {
        kieu: kieu,
        loaihd_id: loaihdId,
        ma_kh: ma
      }
      let data = this.GetData(await API.KIEMTRA_MAKH_MTT(this.axios, req))
      return data == '1'
    },
    KT_DK_PAYTV() {
      if (this.cboLoaihinhTB.value == LoaiHinhTB.INTERNET_MYTV) {
        let dt = this.dgvDVGT.data
        if (dt.length > 0) {
          let q = dt.filter((item) => item.dangky == '1' && item.ma_dvgt == 'PAYTV')
          if (q.length > 0) {
            q = q.filter((item) => item.noidung && !isNaN(parseInt(item.noidung)) && isFinite(item.noidung) && parseInt(item.noidung) >= 3)
            if (q.length == 0) {
              this.$root.$toast.warning('Thông tin đăng ký PAYTV trọn gói 20.000 không hợp lệ!')
              return true
            }
          }
        }
      }
      return false
    },
    async lay_ds_thamso_md_mats(mats) {
      return this.GetDataList(
        await API.lay_ds_thamso_md_mats(this.axios, {
          ma_ts: mats
        })
      )
    },
    async KIEMTRA_TB_MYTV_CHINHPHU(matb, kieu) {
      let req = { ma_tb: matb, kieu: kieu }
      return this.GetDataList(await API.KIEMTRA_TB_MYTV_CHINHPHU(this.axios, req))
    },
    async TraCuu_No_MaTB(thuebaoid) {
      let result = 0
      try {
        if (this.thuebao_id != 0) {
          result = await this.tracuuno_thuebao(thuebaoid)
        } else {
          this.$root.$toast.error('Chưa có thông tin về thuê bao!')
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi:' + error.message)
      }
      return result
    },
    async tracuu_no_thanhtoan(thanhtoanid) {
      return this.GetData(await API.tracuu_no_thanhtoan(this.axios, thanhtoanid))
    },
    async tracuuno_thuebao(thuebaoid) {
      return this.GetData(await API.tracuuno_thuebao(this.axios, thuebaoid))
    },
    async FN_TT_DB_THUEBAO(thuebaoid) {
      let req = { param: thuebaoid, type: 1 }
      return this.GetData(await API.FN_TT_DB_THUEBAO(this.axios, req))
    },
    onActionClick(action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.tsbtnNhapMoi_Click()
      } else if (action.id === 'tsbtnGhiLai') {
        this.tsbtnGhiLai_Click()
      } else if (action.id === 'tsbtnHuyBo') {
        this.setActiveActions(0)
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      } else if (action.id === 'tsbtnGiaoPhieu') {
        this.tsbtnGiaoPhieu_Click()
      } else if (action.id === 'tsbtnThanhToan') {
        this.tsbtnThanhToan_Click()
      } else if (action.id === 'btnInHD') {
        this.btnInHD_Click()
      } else if (action.id === 'tsbtnThemTB') {
        this.tsbtnThemTB_Click()
      } else if (action.id === 'tsbtnXoaTB') {
        this.tsbtnXoaTB_Click()
      } else if (action.id === 'tsbtnHen') {
        this.tsbtnHen_Click()
      } else if (action.id === 'tsbtnThongTinLH') {
        this.tsbtnThongTinLH_Click()
      } else if (action.id === 'tsbtnEmail') {
        this.tsbtnEmail_Click()
      }
    },
    async Clear() {
      this.kenh_trang = true
      this.chi_giao_net = true
      this.vloaikenh_id = 0
      this.jloaikenh_id =0
      this.chkCTV.checked = false
      this.chkNguoiGT.checked = false
      this.formProps.txtCTV = ''
      this.nguoigt_id = 0
      this.ctv_id = 0
      this.formProps.txtMaBSS = ''
      this.formProps.txtMaERP = ''
      this.formProps.txtSoAo = ''
      this.formProps.txtHuongKN = ''
      this.formProps.txtMaHD_TTKD = ''
      this.formProps.txtGhiChu = ''
      this.formProps.txtMaDuAn = ''
      this.hdtb_id_cha = 0
      this.trangbi_id = 0
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.lvwThueBao.data = []
      await this.NapThongTinDichVuThueBao()
      this.hdkh_id = 0
      this.formProps.txtMaGD = ''
      this.formProps.txtMaHD = ''
      this.formProps.txtMaKH = ''
      this.khachhang_id = 0
      this.formProps.txtTenKH = ''
      this.formProps.txtDiaChiKH = ''
      this.formProps.dtpNgayLapHD = this.formatDate(new Date(), 'DD/MM/YYYY')
      this.formProps.dtpNgayYeuCau = this.formatDate(new Date(), 'DD/MM/YYYY')
      this.formProps.dtpNgayHH_TD = this.formatDate(new Date(), 'DD/MM/YYYY')
      this.chkNgay_HHTD.disabled = true
      this.chkNgay_HHTD.checked = false
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.formProps.txtTenTB = ''
      this.formProps.txtDiaChiTB = ''
      // dichvuvt_id = DichVuVienThong.CO_DINH;
      // cboDichVuVT.SelectedValue = dichvuvt_id;
      this.dichvuvt_id = DichVuVienThong.CO_DINH
      this.cboDichVuVT.value = this.dichvuvt_id
      this.formProps.txtTongTienHD = '0'
      this.formProps.txtDiaChiLD = ''
      this.$refs.txtMaTB.focus()
      this.formProps.txtTrangThaiTB = ''
      this.tien_dv = 0
      this.vat_dv = 0
      this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
      await this.LayTienTheoKhoanMuc()
      this.doituong_id = 1
      this.lvLyDoHuy.data = []
      this.dsDKHUY = []
      this.dsHDTB_ADSL = []
      this.dsHDTB_CD = []
      this.dsHDTB_GP = []
      this.dsHDTB_TSL = []
      this.dsHDTB_MGW = []
      this.dsHDTB_DD = []
    },
    async LayTienTheoKhoanMuc() {
      try {
        let ds = await this.LAY_TIEN_KHOANMUC_LD(
          LoaiHopDong.THAY_DOI_DV,
          this.formProps.dtpNgayYeuCau,
          Number(this.cboDichVuVT.value),
          Number(this.cboLoaihinhTB.value),
          Number(this.cboKieuLD.value),
          this.phuongld_id,
          0,
          0,
          1,
          0,
          0,
          0,
          0,
          0,
          this.phold_id,
          this.apld_id,
          this.khuld_id,
          this.doituong_id,
          this.trangbi_id
        )
        if (ds.length > 0) {
          this.HienThiTienLapDat(ds)
          this.LayTongTien_HDTB()
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    HienThiTienLapDat(ds) {
      let ktlm = true
      if (Object.values(ds[0])[0] != '-1') {
        for (let i = 0; i < ds.length; i++) {
          let item = ds[i]
          if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
            this.dtienld = Number(item.tien)
            this.dvatld = Number(item.vat)
            ktlm = false
          }
        }
        if (ktlm) {
          this.dtienld = 0.0
          this.dvatld = 0.0
        }
      } else {
        this.dtienld = 0.0
        this.dvatld = 0.0
        this.$root.$toast.error('Thông tin giá chưa được cập nhật')
      }
      for (let j = 0; j < this.dsTienKhoanMuc; j++) {
        let item = this.dsTienKhoanMuc[j]
        if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
          this.dsTienKhoanMuc[j].tien = this.dtienld
          this.dsTienKhoanMuc[j].vat = this.dvatld
        }
      }
    },
    HienThiTienDichVu(tiendv, vatdv) {
      this.dtien_dv = tiendv
      this.dvat_dv = vatdv
      for (let i = 0; i < this.dsTienKhoanMuc.length; i++) {
        let item = this.dsTienKhoanMuc[i]
        if (item.khoanmuctt_id == KHOANMUC_TT.KMTT_DICHVU) {
          this.dsTienKhoanMuc[i].tien = this.dtien_dv
          this.dsTienKhoanMuc[i].vat = this.dvat_dv
        }
      }
      this.LayTongTien_HDTB()
    },
    LayTongTien_HDTB() {
      try {
        let dtongtien = 0.0
        let dtongvat = 0.0
        for (let i = 0; i < this.dsTienKhoanMuc.length; i++) {
          console.log(i)
          console.log(this.dsTienKhoanMuc[i])
          let item = this.dsTienKhoanMuc[i]
          dtongtien += parseFloat(item.tien)
          console.log(dtongtien)
          dtongvat += parseFloat(item.vat)
          console.log(dtongvat)
        }
        this.formProps.txtTongTien = dtongtien
        this.formProps.txtTongVat = dtongvat
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async NapThongTinDichVuThueBao() {
      console.log("Nạp thông tin");
      try {
        this.dsThueBaoDichVu = {
          danhsach: [],
          danhsach_tinh: [],
          danhsach_tinh_tsl: []
        }
        if (this.cboDichVuVT.value == DichVuVienThong.IMS) {
          if (this.tagForm == '1') {
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_TB_DVGT_THEO_TT_IMS(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, TRANGTHAI_TB_DVGT.CON_HIEU_LUC)
            if (this.tinh.tinhthicong_id > 0) {
              this.dsThueBaoDichVu['danhsach_tinh'] = await this.LAY_DS_TB_DVGT_THEO_TT_IMS(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, TRANGTHAI_TB_DVGT.CON_HIEU_LUC)
            }
          } else {
            console.log("IMS");
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_THUEBAO_DICHVU_IMS(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, Number(this.cboKieuLD.value))
            if (this.tinh.tinhthicong_id > 0) {
              console.log("IMS BC");
              this.dsThueBaoDichVu['danhsach_tinh'] = await this.LAY_DS_THUEBAO_DICHVU_IMS_BC(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, Number(this.cboKieuLD_TC.value), this.tinh.tinhthicong_id)
            }
            if (this.tinh != null && this.tinh.tinhthicong_id > 0) {
              let dtDVBC = await this.LAY_DS_THUEBAO_DICHVU_V2_BC(0, this.thuebao_tc_id, this.loaitb_id ? this.loaitb_id : 0, 0, Number(this.cboKieuLD_TC.value), this.tinh.tinhthicong_id)
              console.log("Vào đây 1: ", this.hdtb_id)
              if (this.hdtb_id != 0) {
                console.log("Xử lý code");
                let dsDVGTBC = await this.LAY_DS_DANGKY_DVGT_BC(this.hdtb_id)
                console.log("dsDVGTBC: ",dsDVGTBC);
                if (dsDVGTBC.length > 0 && dtDVBC.length > 0) {
                  for (let dv1 = 0; dv1 < dsDVGTBC.length; dv1++) {
                    let item1 = dsDVGTBC[dv1]
                    for (let dv2 = 0; dv2 < dtDVBC.length; dv2++) {
                      let item2 = dtDVBC[dv2]
                      if (item1.dichvugt_id == item2.dichvugt_id) {
                        if (item1.kieu_yc == 0) {
                          // Hủy
                          dtDVBC[dv2]['huy'] = 1
                        }
                        if (item1.kieu_yc == 1) {
                          // Đăng ký
                          dtDVBC[dv2]['dangky'] = 1
                        }
                        if (item1.kieu_yc == 2) {
                          dtDVBC[dv2]['sua'] = 1
                        }
                        dtDVBC[dv2]['noidung'] = item1.noidung
                      }
                    }
                  }
                }
              }
              this.dsThueBaoDichVu['danhsach_tinh'] = dtDVBC
            }
          }
        } else if (this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          if (this.cboLoaihinhTB.value == LoaiHinhTB.BRANDNAME) {
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_THUEBAO_DICHVU_LINHVUC(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), this.linhvuc_id)
          } else {
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_THUEBAO_DICHVU(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0)
          }
        } else {
          if (this.tagForm == '1') {
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_TB_DVGT_THEO_TRANGTHAI(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, TRANGTHAI_TB_DVGT.CON_HIEU_LUC)
            if (this.tinh.tinhthicong_id > 0) {
              this.dsThueBaoDichVu['danhsach_tinh'] = await this.LAY_DS_TB_DVGT_THEO_TRANGTHAI(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, TRANGTHAI_TB_DVGT.CON_HIEU_LUC)
            }
          } else if ((this.dichvuvt_id == DichVuVienThong.TSL||this.dichvuvt_id==25) && this.tinh_cuoi.tinhthicong_id != 0) {
            let dtDVBC = []
            let dtDVBCTSL = []
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_THUEBAO_DICHVU_V2(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, Number(this.cboKieuLD.value))
            dtDVBC = await this.LAY_DS_THUEBAO_DICHVU_V2_BC(0, this.thuebao_tc_id, this.loaitb_id ? this.loaitb_id : 0, 0, Number(this.cboKieuLD_TC.value), this.tinh_dau.tinhthicong_id)
            dtDVBCTSL = await this.LAY_DS_THUEBAO_DICHVU_V2_BC(0, this.thuebao_tc_id_tsl, this.loaitb_id ? this.loaitb_id : 0, 0, Number(this.cboKieuLD_TC.value), this.tinh_cuoi.tinhthicong_id)
            if (this.hdtb_id != 0) {
              let dsdvgtbc = await this.LAY_DS_DANGKY_DVGT_BC(this.hdtb_id)
              if (dsdvgtbc.length > 0 && dtDVBC.length > 0) {
                for (let dv1 = 0; dv1 < dsdvgtbc.length; dv1++) {
                  let item1 = dsdvgtbc[dv1]
                  for (let dv2 = 0; dv2 < dtDVBC.length; dv2++) {
                    let item2 = dtDVBC[dv2]
                    if (item1.dichvugt_id == item2.dichvugt_id) {
                      if (item1.kieu_yc == 0) {
                        // Hủy
                        dtDVBC[dv2]['huy'] = 1
                      }
                      if (item1.kieu_yc == 1) {
                        // Đăng ký
                        dtDVBC[dv2]['dangky'] = 1
                      }
                      if (item1.kieu_yc == 2) {
                        dtDVBC[dv2]['sua'] = 1
                      }
                      dtDVBC[dv2]['noidung'] = item1.noidung
                    }
                  }
                }
                for (let dv1 = 0; dv1 < dsdvgtbc.length; dv1++) {
                  let item1 = dsdvgtbc[dv1]
                  for (let dv2 = 0; dv2 < dtDVBCTSL.length; dv2++) {
                    let item2 = dtDVBCTSL[dv2]
                    if (item1.dichvugt_id == item2.dichvugt_id) {
                      if (item1.kieu_yc == 0) {
                        // Hủy
                        dtDVBCTSL[dv2]['huy'] = 1
                      }
                      if (item1.kieu_yc == 1) {
                        // Đăng ký
                        dtDVBCTSL[dv2]['dangky'] = 1
                      }
                      if (item1.kieu_yc == 2) {
                        dtDVBCTSL[dv2]['sua'] = 1
                      }
                      dtDVBCTSL[dv2]['noidung'] = item1.noidung
                    }
                  }
                }
              }
            }
            this.dsThueBaoDichVu['danhsach_tinh'] = dtDVBC
            this.dsThueBaoDichVu['danhsach_tinh_tsl'] = dtDVBCTSL
          }  else {
            this.dsThueBaoDichVu['danhsach'] = await this.LAY_DS_THUEBAO_DICHVU_V2(this.hdtb_id, this.thuebao_id, Number(this.cboLoaihinhTB.value), 0, Number(this.cboKieuLD.value))
            if (this.tinh != null && this.tinh.tinhthicong_id > 0) {
              let dtDVBC = await this.LAY_DS_THUEBAO_DICHVU_V2_BC(0, this.thuebao_tc_id, this.loaitb_id ? this.loaitb_id : 0, 0, Number(this.cboKieuLD_TC.value), this.tinh.tinhthicong_id)
              console.log("Vào đây 1: ", this.hdtb_id)
              if (this.hdtb_id != 0) {
                console.log("Xử lý code");
                let dsDVGTBC = await this.LAY_DS_DANGKY_DVGT_BC(this.hdtb_id)
                console.log("dsDVGTBC: ",dsDVGTBC);
                if (dsDVGTBC.length > 0 && dtDVBC.length > 0) {
                  for (let dv1 = 0; dv1 < dsDVGTBC.length; dv1++) {
                    let item1 = dsDVGTBC[dv1]
                    for (let dv2 = 0; dv2 < dtDVBC.length; dv2++) {
                      let item2 = dtDVBC[dv2]
                      if (item1.dichvugt_id == item2.dichvugt_id) {
                        if (item1.kieu_yc == 0) {
                          // Hủy
                          dtDVBC[dv2]['huy'] = 1
                        }
                        if (item1.kieu_yc == 1) {
                          // Đăng ký
                          dtDVBC[dv2]['dangky'] = 1
                        }
                        if (item1.kieu_yc == 2) {
                          dtDVBC[dv2]['sua'] = 1
                        }
                        dtDVBC[dv2]['noidung'] = item1.noidung
                      }
                    }
                  }
                }
              }
              this.dsThueBaoDichVu['danhsach_tinh'] = dtDVBC
            }
          }
        }
        this.dgvDVGT.data = this.dsThueBaoDichVu['danhsach']
        if (this.tinh != null && this.tinh.tinhthicong_id > 0) {
          this.dgvDVGTTC.data = this.dsThueBaoDichVu['danhsach_tinh']
        }
        if ((this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id==25) && this.tinh_cuoi.tinhthicong_id != 0) {
          this.dgvDVGTTC.data = this.dsThueBaoDichVu['danhsach_tinh']
          this.dgvDVGTTC_TSL.data = this.dsThueBaoDichVu['danhsach_tinh_tsl']
        }
        for (let k = 0; k < this.dsThueBaoDichVu['danhsach'].length; k++) {
          let item = this.dsThueBaoDichVu['danhsach'][k]
          if (item.DANGKY == 1 && item.trangthaitb_id == TrangThaiTB.TAMDUNG) {
            this.setActiveButton('btnEditLyDoHuy', true)
            this.chkNgay_HHTD.disabled = false
          } else {
            this.setActiveButton('btnEditLyDoHuy', false)
            this.chkNgay_HHTD.disabled = true
          }
        }
        /* this.setVisibleColumnByIdx(this.dgvDVGT, 8, false)
        this.setVisibleColumnByIdx(this.dgvDVGT, 9, false)
        this.setVisibleColumnByIdx(this.dgvDVGT, 16, false)
        this.setVisibleColumnByIdx(this.dgvDVGT, 17, false)
        this.setVisibleColumnByName(this.dgvDVGT, 'KIEU_DL', false)
        this.setVisibleColumnByName(this.dgvDVGT, 'TRANGTHAITB_ID', false)
        this.setVisibleColumnByName(this.dgvDVGT, 'NHOM_GOI', false)
        this.setVisibleColumnByName(this.dgvDVGT, 'GOI_VASC', false)
        this.setVisibleColumnByName(this.dgvDVGT, 'HDTB_ID', false)
        this.setVisibleColumnByName(this.dgvDVGT, 'STT', false) */
        if (this.tinh != null && this.tinh.tinhthicong_id > 0) {
          /* this.setVisibleColumnByIdx(this.dgvDVGTTC, 8, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 9, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 16, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 17, false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'KIEU_DL', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'TRANGTHAITB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'NHOM_GOI', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'GOI_VASC', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'HDTB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'STT', false) */
        } else if ((this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id==25) && this.tinh_cuoi.tinhthicong_id != 0) {

          // this.setVisibleColumnByIdx(this.dgvDVGTTC, 8, false)} 
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 9, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 16, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC, 17, false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'KIEU_DL', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'TRANGTHAITB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'NHOM_GOI', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'GOI_VASC', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'HDTB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC, 'STT', false)
          //
          // this.setVisibleColumnByIdx(this.dgvDVGTTC_TSL, 8, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC_TSL, 9, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC_TSL, 16, false)
          this.setVisibleColumnByIdx(this.dgvDVGTTC_TSL, 17, false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'KIEU_DL', false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'TRANGTHAITB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'NHOM_GOI', false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'GOI_VASC', false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'HDTB_ID', false)
          this.setVisibleColumnByName(this.dgvDVGTTC_TSL, 'STT', false)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async LAY_TIEN_KHOANMUC_LD(loaihdid, ngayyc, dichvuvtid, loaitbid, kieuldid, phuongldid, tocdoid, muccuocid, ltbadslid, ltbadslmoiid, ltbadslcuid, tocdomoiid, tocdocuid, khcd, pholdid, apldid, khuldid, doituongid, trangbiid) {
      const req = {
        loaihd_id: loaihdid,
        ngay_yc: ngayyc,
        dichvuvt_id: dichvuvtid,
        loaitb_id: loaitbid,
        kieuld_id: kieuldid,
        phuongld_id: phuongldid,
        tocdo_id: tocdoid,
        muccuoc_id: muccuocid,
        ltb_adsl_id: ltbadslid,
        ltb_adsl_moi_id: ltbadslmoiid,
        ltb_adsl_cu_id: ltbadslcuid,
        tocdo_moi_id: tocdomoiid,
        tocdo_cu_id: tocdocuid,
        kh_cd: khcd,
        apld_id: apldid,
        khuld_id: khuldid,
        doituong_id: doituongid,
        trangbi_id: trangbiid
      }
      return this.GetDataList(await API.LAY_TIEN_KHOANMUC_LD(this.axios, req))
    },
    async SP_LAY_DS_TIEN_KHOANMUC(vloaihdid) {
      return this.GetDataList(
        await API.SP_LAY_DS_TIEN_KHOANMUC(this.axios, {
          loaihd_id: vloaihdid
        })
      )
    },
    async LAY_DS_DANGKY_DVGT_BC(hdtbid) {
      const req = { hdtb_id: hdtbid }
      return this.GetDataList(await API.LAY_DS_DANGKY_DVGT_BC(this.axios, req))
    },
    async LAY_DS_THUEBAO_DICHVU_V2(hdtbid, thuebaoid, loaitbid, kieuid, kieuldid) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        kieuld_id: kieuldid
      }
      let data = this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU_V2(this.axios, req))
      if (data.length > 0) {
        data = data.map((item) => {
          return {
            ...item,
            dangky: Number(item.dangky),
            sua: Number(item.sua),
            huy: Number(item.huy),
            cuoc_ld: Number(item.cuoc_ld),
            cuoc_sd: Number(item.cuoc_sd),
            tien: Number(item.tien),
            vat_ld: Number(item.vat_ld),
            vat_sd: Number(item.vat_sd)
          }
        })
      }
      return data
    },
    async LAY_DS_THUEBAO_DICHVU_V2_BC(hdtbid, thuebaoid, loaitbid, kieuid, kieuldid, tinh) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        kieuld_id: kieuldid,
        tinhthicong_id: tinh
      }
      let data = this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU_V2_BC(this.axios, req))
      if (data.length > 0) {
        data = data.map((item) => {
          return {
            ...item,
            dangky: Number(item.dangky),
            sua: Number(item.sua),
            huy: Number(item.huy),
            cuoc_ld: Number(item.cuoc_ld),
            cuoc_sd: Number(item.cuoc_sd),
            tien: Number(item.tien),
            vat_ld: Number(item.vat_ld),
            vat_sd: Number(item.vat_sd)
          }
        })
      }
      return data
    },
    async LAY_DS_TB_DVGT_THEO_TRANGTHAI(hdtbid, thuebaoid, loaitbid, kieuid, trangthaitbid) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        trangthaitb_id: trangthaitbid
      }
      return this.GetDataList(await API.LAY_DS_TB_DVGT_THEO_TRANGTHAI(this.axios, req))
    },
    async LAY_DS_THUEBAO_DICHVU(hdtbid, thuebaoid, loaitbid, kieuid) {
      const req = { hdtbId: hdtbid, thueBaoId: thuebaoid, loaiTbId: loaitbid, kieuId: kieuid }
      return this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU(this.axios, req))
    },
    async LAY_DS_THUEBAO_DICHVU_LINHVUC(hdtbid, thuebaoid, loaitbid, linhvucid) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: 0,
        linhvuc_id: linhvucid
      }
      return this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU_LINHVUC(this.axios, req))
    },
    async LAY_DS_THUEBAO_DICHVU_IMS(hdtbid, thuebaoid, loaitbid, kieuid, vkieuldid) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        kieuld_id: vkieuldid
      }
      return this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU_IMS(this.axios, req))
    },
    async LAY_DS_THUEBAO_DICHVU_IMS_BC(hdtbid, thuebaoid, loaitbid, kieuid, vkieuldid, tinh) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        kieuld_id: vkieuldid,
        tinhthicong_id: tinh
      }
      return this.GetDataList(await API.LAY_DS_THUEBAO_DICHVU_IMS_BC(this.axios, req))
    },
    async LAY_DS_TB_DVGT_THEO_TT_IMS(hdtbid, thuebaoid, loaitbid, kieuid, vtrangthaitbid, vkieuldid) {
      const req = {
        hdtb_id: hdtbid,
        thuebao_id: thuebaoid,
        loaitb_id: loaitbid,
        kieu_id: kieuid,
        trangthaitb_id: vtrangthaitbid,
        kieuld_id: vkieuldid
      }
      let data = this.GetDataList(await API.LAY_DS_TB_DVGT_THEO_TT_IMS(this.axios, req))
      if (data.length > 0) {
        data.map((item) => {
          return {
            ...item,
            dangky: Number(item.dangky),
            sua: Number(item.sua),
            huy: Number(item.huy),
            cuoc_ld: Number(item.cuoc_ld),
            cuoc_sd: Number(item.cuoc_sd),
            tien: Number(item.tien),
            vat_ld: Number(item.vat_ld),
            vat_sd: Number(item.vat_sd)
          }
        })
      }
      return data
    },
    dgvDVGT_CellClick(args) {
      if (args) {
        this.dgvDVGT.cellSelected = args
        if (args.cellIndex.rowIndex >= 0) {
          let cellIndex = args.cellIndex.cellIndex - 1
          let columnName = this.dgvDVGT.headers[cellIndex].fieldName
          if (columnName == 'noidung' && this.dgvDVGT.data[args.cellIndex.rowIndex].ma_dvgt == 'D7' && this.dsThueBaoDichVu['danhsach']['dangky'] == 1) {
            this.$refs.frmTC_DBTB_KH.openDialog()
          }
        }
      }
    },
    setDBTB(data) {
      if (data) {
        this.dsThueBaoDichVu['danhsach'][this.dgvDVGT.cellSelected.cellIndex.rowIndex]['noidung'] = data
      }
    },
    async lay_ds_chitiet_km_theo_tbid(thuebaoid) {
      let req = { vthuebao_id: thuebaoid, vkieu: '2' }
      return this.GetDataList(await API.lay_ds_chitiet_km_theo_tbid(this.axios, req))
    },
    KT_Mo_TDTC() {
      let kt = false
      try {
        for (let i = 0; i < this.dgvDVGTTC.data.length; i++) {
          if (this.dsThueBaoDichVu['danhsach_tinh'][i].huy.toString() != '') {
            if (this.dsThueBaoDichVu['danhsach_tinh'][i].huy == 1 && this.dsThueBaoDichVu['danhsach_tinh'][i].trangthaitb_id == TrangThaiTB.TAMDUNG) {
              kt = true
              break
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
      return kt
    },
    KT_DangKy_TDTC() {
      let kt = false
      try {
        for (let i = 0; i < this.dgvDVGTTC.data.length; i++) {
          if (this.dsThueBaoDichVu['danhsach_tinh'][i].dangky) {
            if (this.dsThueBaoDichVu['danhsach_tinh'][i].dangky == 1 && this.dsThueBaoDichVu['danhsach_tinh'][i].trangthaitb_id == TrangThaiTB.TAMDUNG) {
              kt = true
              break
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
      return kt
    },
    async dgvDVGTTC_CellContentClick(args) {
      console.log('dgvDVGTTC_CellContentClick', args)
      try {
        let mess = ''
        if (args) {
          if (args.cellIndex.rowIndex >= 0) {
            this.dgvDVGTTC.cellSelected = args
            let rowData = args.data
            console.log('row data dgvDVGTTC_CellContentClick : ', rowData)
            let indexRowDsThueBaoDV = this.dsThueBaoDichVu['danhsach_tinh'].findIndex((item) => item.stt == rowData.stt)
            let indexRowData = this.dgvDVGTTC.data.findIndex((item) => item.stt == rowData.stt)
            console.log('indexRowDsThueBaoDV dgvDVGTTC: ', indexRowDsThueBaoDV)
            console.log('indexRowData dgvDVGTTC: ', indexRowDsThueBaoDV)
            let cellIndex = args.cellIndex.cellIndex - 1
            let columnName = this.dgvDVGTTC.headers[cellIndex].fieldName
            let rowSelectedData = this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV]
            console.log("rowSelectedData", rowSelectedData);
            if(rowData.noidung_tc != "")
              this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].noidung_tc = rowData.noidung_tc;
            if (columnName == 'dangky') {
              if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sudung) {
                console.log("Đã sử dụng");
                // Nếu đã sử dụng thì không thể đăng ký
                if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                }
              } else {
                // Nếu chưa sử dụng
                if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 1
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                }
              }
            }
            if (columnName == 'huy') {
              if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sudung) {
                if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua = 0
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 1
                  // Nếu ko chọn kiểu yc là mở TD mà chọn hủy TD
                  if (this.KT_KieuLD_TD() != 0 && this.KT_Mo_TDTC()) {
                    mess = 'Dịch vụ này chỉ áp dụng với kiểu yêu cầu Mở tạm dừng '
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                  } else if (this.KT_KieuLD_TD() == 0 && !(await this.KT_Mo_TDTC())) {
                    // Nếu chọn mở tạm dừng mà ko chọn hủy TD
                    console.log('bbb')
                    mess = 'Bạn đang chọn kiểu yêu cầu mở tạm dừng, chỉ được chọn hủy tạm dừng! '
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                  }
                  // Kieu LD là tạm dừng
                  if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TDTC()) {
                    // Nếu chọn tạm dừng mà chọn hủy DV khác
                    mess = 'Bạn đang chọn kiểu yêu cầu tạm dừng, chỉ được chọn đăng ký tạm dừng! '
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                  }
                  if (mess.length > 0) {
                    this.$root.$toast.warning(mess)
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                }
              } else {
                this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
              }
            }
            if (columnName == 'sua') {
              if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].trangthaitb_id == TrangThaiTB.THUONG) {
                this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua = 0
              } else {
                if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sudung) {
                  if (this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua == 0) {
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua = 1
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].huy = 0
                  } else {
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua = 0
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].sua = 0
                }
              }
            }
            if (columnName == 'noidung_tc') {
              console.log("Chả làm gì cả");
              return;
            }
            if (rowData.noidung_tc == '' && rowData.ma_dvgt_tc == 'D5' && this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky == 1) {
              let key = this.getRandomArbitrary(1, 9999)
              this.dgvDVGTTC.data[indexRowData].noidung_tc = key.toString().padStart(4, '0')
            }
            this.dgvDVGTTC.data = [...this.dgvDVGTTC.data]
            let lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'CB_FF_24') && item.dangky == 1)
            if (lth.length > 1) {
              this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
            } else {
              lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'FIM' || item.ma_dvgt == 'FAF') && item.dangky == 1)
              if (lth.length > 1) {
                this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
              } else {
                lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                if (lth > 1) {
                  this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                } else {
                  lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                  if (lth > 1) {
                    this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                  } else {
                    lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                    if (lth > 1) {
                      this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                    } else {
                      lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                      if (lth > 1) {
                        this.dsThueBaoDichVu['danhsach_tinh'][indexRowDsThueBaoDV].dangky = 0
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async dgvDVGTTC_TSL_CellContentClick(args) {
      console.log('dgvDVGTTC_TSL_CellContentClick', args)
      try {
        let mess = ''
        if (args) {
          if (args.cellIndex.rowIndex >= 0) {
            this.dgvDVGTTC.cellSelected = args
            let rowData = args.data
            console.log('row data dgvDVGTTC_TSL_CellContentClick : ', rowData)
            let indexRowDsThueBaoDV = this.dsThueBaoDichVu['danhsach_tinh_tsl'].findIndex((item) => item.stt == rowData.stt)
            let indexRowData = this.dgvDVGTTC.data.findIndex((item) => item.stt == rowData.stt)
            console.log('indexRowDsThueBaoDV dgvDVGTTC_TSL: ', indexRowDsThueBaoDV)
            console.log('indexRowData dgvDVGTTC_TSL: ', indexRowDsThueBaoDV)
            let cellIndex = args.cellIndex.cellIndex - 1
            let columnName = this.dgvDVGTTC.headers[cellIndex].fieldName
            let rowSelectedData = this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV]
            if(rowData.noidung_tc != "")
              this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].noidung_tc = rowData.noidung_tc;
            if (columnName == 'dangky') {
              if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sudung) {
                // Nếu đã sử dụng thì không thể đăng ký
                if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                }
              } else {
                // Nếu chưa sử dụng
                if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 1
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                }
              }
              console.log('this.dsThueBaoDichVu[danhsach_tinh_tsl]',this.dsThueBaoDichVu['danhsach_tinh_tsl'])
            }
            if (columnName == 'huy') {
              if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sudung) {
                if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy == 0) {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua = 0
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 1
                  // Nếu ko chọn kiểu yc là mở TD mà chọn hủy TD
                  if (this.KT_KieuLD_TD() != 0 && this.KT_Mo_TDTC()) {
                    mess = 'Dịch vụ này chỉ áp dụng với kiểu yêu cầu Mở tạm dừng '
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                  } else if (this.KT_KieuLD_TD() == 0 && !(await this.KT_Mo_TDTC())) {
                    // Nếu chọn mở tạm dừng mà ko chọn hủy TD
                    console.log('bbb')
                    mess = 'Bạn đang chọn kiểu yêu cầu mở tạm dừng, chỉ được chọn hủy tạm dừng! '
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                  }
                  // Kieu LD là tạm dừng
                  if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TDTC()) {
                    // Nếu chọn tạm dừng mà chọn hủy DV khác
                    mess = 'Bạn đang chọn kiểu yêu cầu tạm dừng, chỉ được chọn đăng ký tạm dừng! '
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                  }
                  if (mess.length > 0) {
                    this.$root.$toast.warning(mess)
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                }
              } else {
                this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
              }
            }
            if (columnName == 'sua') {
              if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].trangthaitb_id == TrangThaiTB.THUONG) {
                this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua = 0
              } else {
                if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sudung) {
                  if (this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua == 0) {
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua = 1
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].huy = 0
                  } else {
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua = 0
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].sua = 0
                }
              }
            }
            if (columnName == 'noidung_tc') {
              console.log("Chả làm gì cả");
              return;
            }
            if (rowData.noidung_tc == '' && rowData.ma_dvgt_tc == 'D5' && this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky == 1) {
              let key = this.getRandomArbitrary(1, 9999)
              this.dgvDVGTTC.data[indexRowData].noidung_tc = key.toString().padStart(4, '0')
            }
            this.dgvDVGTTC.data = [...this.dgvDVGTTC.data]
            let lth = this.dsThueBaoDichVu['danhsach_tinh_tsl'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'CB_FF_24') && item.dangky == 1)
            if (lth.length > 1) {
              this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
            } else {
              lth = this.dsThueBaoDichVu['danhsach_tinh_tsl'].filter((item) => (item.ma_dvgt == 'FIM' || item.ma_dvgt == 'FAF') && item.dangky == 1)
              if (lth.length > 1) {
                this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
              } else {
                lth = this.dsThueBaoDichVu['danhsach_tinh_tsl'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                if (lth > 1) {
                  this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                } else {
                  lth = this.dsThueBaoDichVu['danhsach_tinh_tsl'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                  if (lth > 1) {
                    this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                  } else {
                    lth = this.dsThueBaoDichVu['danhsach_tinh_tsl'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                    if (lth > 1) {
                      this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                    } else {
                      lth = this.dsThueBaoDichVu['danhsach_tinh'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                      if (lth > 1) {
                        this.dsThueBaoDichVu['danhsach_tinh_tsl'][indexRowDsThueBaoDV].dangky = 0
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async dgvDVGT_CellContentClick(args) {
      try {
        let message = ''
        if (args) {
          if (args.cellIndex.rowIndex >= 0) {
            this.dgvDVGT.cellSelected = args
            let rowData = args.data // this.dgvDVGT.data[args.cellIndex.rowIndex]
            console.log('row data: ', rowData)
            let indexRowDsThueBaoDV = this.dsThueBaoDichVu['danhsach'].findIndex((item) => item.stt == rowData.stt)
            let indexRowData = this.dgvDVGT.data.findIndex((item) => item.stt == rowData.stt)
            console.log('indexRowDsThueBaoDV: ', indexRowDsThueBaoDV)
            console.log('indexRowData: ', indexRowDsThueBaoDV)
            let cellIndex = args.cellIndex.cellIndex - 1
            let columnName = this.dgvDVGT.headers[cellIndex].fieldName
            console.log('column name: ', columnName)
            // Gop ham dgvDVGT_CellClick
            if (columnName == 'noidung' && rowData.ma_dvgt == 'D7' && this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV]['dangky'] == 1) {
              await this.$refs.frmTC_DBTB_KH.openDialog()
            }
            // Danmg ky
            if (columnName == 'dangky') {
              if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sudung) {
                // Nếu đã sử dụng thì không thể đăng ký
                if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky == 0) {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                }
              } else {
                if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky == 0) {
                  let ktdk = this.KT_DangKy_TD()
                  if (ktdk) {
                    let dskm = await this.lay_ds_chitiet_km_theo_tbid(this.thuebao_id)
                    if (dskm.length > 0) {
                      let tb = 'Thuê bao ' + this.formProps.txtMaTB
                      tb += ' đang hưởng khuyến mại : \n ' + dskm[0]['ten_km']
                      tb += ' \n    Có chi tiết khuyến mại : '
                      for (let i = 0; i < dskm.length; i++) {
                        tb += '\n     - ' + dskm[i]['ten_ctkm']
                      }
                      tb += '\nBạn không được thực hiện tiếp với thuê bao này!'
                      this.$root.$toast.warning(tb)
                      return
                    }
                  }
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 1
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  // Nếu ko chọn kiểu yc là TD mà đăng ký TD
                  if (this.KT_KieuLD_TD() != 1 && this.KT_DangKy_TD()) {
                    message = 'Dịch vụ này chỉ áp dụng với kiểu yêu cầu Tạm dừng '
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  } else if (this.KT_KieuLD_TD() == 1 && this.KT_DangKy_TD()) {
                    this.btnEditLyDoHuy.disabled = false
                    this.chkNgay_HHTD.disabled = false
                    this.chkNgay_HHTD.checked = false
                    this.disabledBox.dtpNgayHH_TD = true
                  } else if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TD()) {
                    message = 'Bạn đang chọn kiểu yêu cầu Tạm dừng, chỉ được chọn đăng ký dịch vụ tạm dừng! '
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  }
                  // Kieu LD la mở tạm dừng
                  if (this.KT_KieuLD_TD() == 0 && !this.KT_Mo_TD()) {
                    // Nếu ko chọn kiểu yc là mở TD mà không chọn hủy TD
                    message = 'Bạn đang chọn kiểu yêu cầu Mở tạm dừng, chỉ được chọn hủy tạm dừng! '
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  }
                  if (message.length > 0) {
                    this.$root.$toast.warning(message)
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  if (!this.KT_DangKy_TD()) {
                    this.btnEditLyDoHuy.disabled = true
                    this.dsBD_LYDOHUY = []
                    this.dsDKHUY = []
                    this.lvLyDoHuy.data = []
                    this.chkNgay_HHTD.disabled = true
                    this.chkNgay_HHTD.checked = false
                    this.disabledBox.dtpNgayHH_TD = true
                    this.formProps.dtpNgayHH_TD = this.formatDate(new Date(), null)
                  }
                }
              }
            }
            if (columnName == 'huy') {
              if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sudung) {
                if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy == 0) {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sua = 0
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 1
                  if (this.KT_KieuLD_TD() != 0 && this.KT_Mo_TD()) {
                    // Nếu ko chọn kiểu yc là mở TD mà chọn hủy TD
                    message = 'Dịch vụ này chỉ áp dụng với kiểu yêu cầu Mở tạm dừng '
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  } else if (this.KT_KieuLD_TD() == 0 && !this.KT_Mo_TD()) {
                    // Nếu chọn mở tạm dừng mà ko chọn hủy TD
                    message = 'Bạn đang chọn kiểu yêu cầu mở tạm dừng, chỉ được chọn hủy tạm dừng!'
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  }
                  // Kieu LD là tạm dừng
                  if (this.KT_KieuLD_TD() == 1 && !this.KT_DangKy_TD()) {
                    message = 'Bạn đang chọn kiểu yêu cầu tạm dừng, chỉ được chọn đăng ký tạm dừng!'
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  }
                  if (message.length > 0) {
                    this.$root.$toast.warning(message)
                  }
                  console.log(1111)
                } else {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  console.log(2222)
                }
              } else {
                this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                console.log(3333)
              }
            }
            if (columnName == 'sua') {
              // Hoàng PKN : 26/06/2018 ô check Sửa
              if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].trangthaitb_id != TrangThaiTB.THUONG) {
                this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sua = 0
              } else {
                if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sudung) {
                  if (this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sua == 0) {
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sua = 1
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].huy = 0
                  }
                } else {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].sua = 0
                }
              }
            }
            if (this.dgvDVGT.data[indexRowData].noidung == '' && this.dgvDVGT.data[indexRowData].ma_dvgt == 'D5' && this.dsThueBaoDichVu['danhsach'][indexRowData].dangky == 1) {
              let key = this.getRandomArbitrary(1, 9999)
              this.dgvDVGT.data[indexRowData].noidung = key.toString().padStart(4, '0')
            }
            this.dgvDVGT.data = [...this.dgvDVGT.data]
            this.TinhTong_Tien_Dich_vu(this.dsThueBaoDichVu['danhsach'])
            this.HienThiTienDichVu(this.tien_dv, this.vat_dv)
            let lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'CB_FF_24') && item.dangky == 1)
            if (lth.length > 1) {
              this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
            } else {
              lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'FIM' || item.ma_dvgt == 'FAF') && item.dangky == 1)
              if (lth.length > 1) {
                this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
              } else {
                lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                if (lth > 1) {
                  this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                } else {
                  lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FAF') && item.dangky == 1)
                  if (lth > 1) {
                    this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                  } else {
                    lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'CB_FF_24' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                    if (lth > 1) {
                      this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                    } else {
                      lth = this.dsThueBaoDichVu['danhsach'].filter((item) => (item.ma_dvgt == 'CB_FF_12' || item.ma_dvgt == 'FIM') && item.dangky == 1)
                      if (lth > 1) {
                        this.dsThueBaoDichVu['danhsach'][indexRowDsThueBaoDV].dangky = 0
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async dgvDVGT_CellContentDoubleClick(args) {
      if (args) {
        try {
          // TuấnNA thêm code hiển thị thông báo gói --- Ngày 06/11/2010
          let vdichvugtid = args.rowData['dichvugt_id']
          let mess = await this.LAY_THONGTIN_GOICODINH(vdichvugtid)
          if (mess != 'Gói : ') {
            if (args.cellIndex - 1 == 1) {
              this.$root.$toast.warning(mess)
            }
          }
          await this.dgvDVGT_CellContentClick(args)
        } catch (error) {
          this.$root.$toast.error('Có lỗi: ' + error.message)
        }
      }
    },
    async LAY_THONGTIN_GOICODINH(dichvugtid) {
      return this.GetData(await API.LAY_THONGTIN_GOICODINH(this.axios, { dichvugt_id: dichvugtid }))
    },
    tabCtDVGTActiveTab(tabIndex) {
      if (tabIndex == 0) {
        this.tabOneActive = true
        this.tabTwoActive = false
      } else {
        this.tabTwoActive = true
        this.tabOneActive = false
      }
    },
    standardizedName(ten) {
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
            strXL = this.convertToUpperCase(strXL)
            if (tenNew !== '') {
              tenNew += ' ' + strXL
            } else tenNew = strXL
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXL = strCon
          strXL = this.convertToUpperCase(strXL)
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
    convertToUpperCase(str) {
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
    getTextCbo(cbo) {
      return cbo.list.filter((item) => item.id == cbo.value)[0].text
    },
    getDate(dateStr) {
      return moment(dateStr, 'DD/MM/YYYY')
    },
    getDateNew(dateStr, format) {
      if (format) {
        return moment(dateStr, format)
      }
      return moment(dateStr, 'DD/MM/YYYY HH:mm:ss')
    },
    parseDate(dateStr) {
      let date = moment(dateStr)
      if (date.isValid()) {
        return moment(date).toDate()
      } else {
        return this.getDateNew(dateStr, null)
      }
    },
    formatDate(date, format) {
      let defaultFormat = 'DD/MM/YYYY'
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format(defaultFormat)
      }
    },
    setVisibleColumnByName(dataGrid, fieldName, status) {
      let index = dataGrid.headers.findIndex((item) => item.fieldName == fieldName)
      if (index > 0) {
        dataGrid.headers[index].visible = status
      }
    },
    setVisibleColumnByIdx(dataGrid, index, status) {
      if (index > 0) {
        let item = dataGrid.headers[index]
        if (item) {
          dataGrid.headers[index].visible = status
        }
      }
    },
    getRandomArbitrary(min, max) {
      return Math.random() * (max - min) + min
    },
    Unformat_tien(s) {
      try{
        console.log(s);
        s = s.toLowerCase()
        let ketqua = ''
        for (let i = 0; i < s.length; i++) {
          if (s[i] != ',' && s[i] != '.' && s[i] != ' ') {
            ketqua = ketqua + s[i]
          }
        }
        return ketqua.trim()
      }
      catch (e){
        console.log(e);
        return "";
      }
    },
    normalize(data) {
      for (let key in data) {
        if (key.toLowerCase() !== key) {
          data[key.toLowerCase()] = data[key]
          delete data[key]
        }
      }
      return data
    },
    capitalize(data) {
      for (let key in data) {
        if (key.toUpperCase() !== key) {
          data[key.toUpperCase()] = data[key]
          delete data[key]
        }
      }
      return data
    }
  }
}
</script>
<style>
@import '../../../../../node_modules/@syncfusion/ej2-base/styles/material.css';
@import '../../../../../node_modules/@syncfusion/ej2-buttons/styles/material.css';
@import '../../../../../node_modules/@syncfusion/ej2-vue-lists/styles/material.css';
.fw6 {
  font-weight: 600 !important;
}
.e-listview {
  border: 1px solid #cccccc;
}
.e-listview .e-list-item {
  border-bottom: 1px solid #cccccc;
  padding: 0 0 0 5px !important;
}
.e-listview .e-list-item.e-hover {
  background-color: #fff9eb;
  border-bottom: 1px solid #cccccc;
}
.e-listview .e-list-item.e-focused {
  background-color: rgb(0, 15, 100);
}
.e-listview .e-list-header {
  font-size: inherit;
  height: 40px;
  padding: 0 5px;
  background-color: #bae7ff !important;
}
.e-grid td.e-cellselectionbackground {
  background-color: #f9e1a9 !important;
}
.e-listview .e-list-item.e-active {
  background-color: #fff9eb;
}
.e-listview .e-list-item .e-text-content {
  font-size: 1rem !important;
}
.e-listview .e-list-item .e-text-content .e-list-text {
  color: #212529;
}
.list-lydohuy {
  height: 140px;
}
.list-lydohuy .e-list-parent .e-list-item.e-selected {
  background-color: #fff9eb;
}
.e-listbox-wrapper .e-list-item.e-selected,
.e-listbox-container .e-list-item.e-selected {
  color: #333 !important;
}
.btn:disabled {
  background: #f5f5f5;
  color: #666;
}
.tabs-dvgt {
  display: -webkit-box !important;
}
</style>
