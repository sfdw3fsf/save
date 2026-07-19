<template src="./TraCuuTheoLichSuThanhToan.html"></template>
<style scoped src="./TraCuuTheoLichSuThanhToan.scss"></style>
<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc'
import TraCuuAPI from '../api/TraCuuLichSuThanhToan'
import GachNoAPI from '../api/GachNoAPI'
import CommonAPI from '../api/Commons'
import EventBus from '@/utils/eventBus'
import { MultiSelectPlugin, CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { CheckBoxPlugin } from '@syncfusion/ej2-vue-buttons'
import KetQuaTraCuu from './components/KetQuaTraCuu'
import { Query } from '@syncfusion/ej2-data'
import { DialogUtility } from '@syncfusion/ej2-popups'
import PhieuGachNoTienMat from '../GachNo/components/PhieuGachNoTienMat'
import PopupYesNoCancel from '../components/PopupYesNoCancel/PopupYesNoCancel'
import FilterDialog from './components/FilterDialog'
import { currency } from '@/filters/currency'

// Vue.use(GridPlugin)

import {
  LOAI_DV,
  NHOM_HTTT
} from '@/const/enums'

Vue.use(DialogUtility)
Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)
let DialogObj

export default Vue.extend({
  components: {
    appKyCuoc: KyCuoc,
    appKetQuaTraCuu: KetQuaTraCuu,
    appPhieuGachNoTienMat: PhieuGachNoTienMat,
    appYesNoCancel: PopupYesNoCancel,
    FilterDialog
  },
  async created() {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)

    // check quyền di động
    if (this.$auth.getMaCCBS() !== null) this.params.p_quyen_dd = true

    const res = await Promise.all([
      this.loadThangTT(),
      this.loadNhomHTTT(),
      this.loadAllHTTT(),
      this.loadThuNgan(),
      this.loadNganHang(),
      this.getThamSoGachNo(),
      this.loadHTTTNganHang()])
    // console.log(res)
    if (res[0].length > 0) {
      let preSelected = []
      let count = 3
      res[0].forEach((item) => {
        if (count > 0) {
          preSelected.push(item.CHUKYNO)
          count--
        }
        this.params.p_thang_tt_options.push({
          id: item.CHUKYNO,
          text: item.THANGNO
        })
      })
      this.params.p_thang_tt = preSelected
    }
    this.params.p_httt_all_options = res[2]
    this.params.p_nhom_ht_options = res[1]
    if (res[1].length > 0) {
      this.params.p_nhom_ht = NHOM_HTTT.GACHNO_NGANHANG
      this.isFirstInit = true
      this.params.p_httt_options = this.loadHTT(NHOM_HTTT.GACHNO_NGANHANG)
    }
    // this.params.p_nganhang_options = res[4].filter(x => x.MA_GOM !== null)
    this.params.p_nganhang_options = res[4]
    this.params.p_thungan_options = res[3]
    this.params.p_quaythu_id = res[3][0].DONVI_ID
    await this.getDonViQuanLy()
    this.params.p_httt_gn_options = res[6]

    if (res[5].length > 0) {
      this.params.p_ngaygach_lui_thang = parseInt(res[5][0].NGAY_GL)
      this.params.p_ngaygach_tien = parseInt(res[5][0].NGAY_GT)
      this.params.p_ngayxoa_lui = parseInt(res[5][0].NGAY_XL)
      this.params.p_ngaygach_lui_thanght = parseInt(res[5][0].NGAY_GLT)
    }
    
    await this.getDSLoaiTien();
    await this.getThamSoMD();
  },
  mounted() {
    this.$refs.dsThanhToan.$refs.grid.ej2Instances.element.addEventListener(
      'mousedown',
      this.mousedown
    )
    this.$refs.dsThanhToan.$refs.grid.ej2Instances.element.addEventListener(
      'mouseup',
      this.mouseup
    )
  },
  destroyed() {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed: {
    tieuchi_label() {
      let label = []
      if (this.params.p_chk_thang_tt === '1') label.push('Tháng thanh toán')
      if (this.params.p_chk_nhom_ht === '1') label.push('Nhóm hình thức')
      if (this.params.p_chk_httt === '1') label.push('Hình thức TT')
      if (this.params.p_chk_kieuno === '1') label.push('Kiểu nợ')
      if (this.params.p_chk_tien_tu === '1') label.push('Tiền từ')
      if (this.params.p_chk_tien_den === '1') label.push('Tiền đến')
      if (label.length <= 0) return 'chưa chọn'
      else return label.join(',')
    },
    f_thang_tt_enabled() {
      return this.params.p_chk_thang_tt === '1'
    },
    f_nhom_ht_enabled() {
      return this.params.p_chk_nhom_ht === '1'
    },
    p_nhom_ht: {
      get() {
        return this.params.p_nhom_ht
      },
      set(value) {
        this.params.p_nhom_ht = value
      }
    },
    p_httt: {
      get() {
        return this.params.p_httt
      },
      set(value) {
        this.params.p_httt = value
      }
    },
    f_httt_enabled() {
      return this.params.p_chk_httt === '1'
    },
    p_kieuno: {
      get() {
        return this.params.p_kieuno
      },
      set(value) {
        this.params.p_kieuno = value
      }
    },
    f_tien_tu_enabled() {
      return this.params.p_chk_tien_tu === '1'
    },
    f_tien_den_enabled() {
      return this.params.p_chk_tien_den === '1'
    },
    f_phantrang_enabled() {
      return this.params.p_chk_phantrang === '1'
    },
    f_getKyCuocFromNTT() {
      return moment(this.params.p_ngay_tt).subtract(1, 'months').format('YYYYMM') + '01'
    },
    p_thungan: {
      get() {
        return this.params.p_thungan
      },
      set(value) {
        this.params.p_thungan = value
      }
    },
    p_nganhang: {
      get() {
        return this.params.p_nganhang
      },
      set(value) {
        this.params.p_nganhang = value
      }
    },
    p_ngay_tt: {
      get() {
        return this.params.p_ngay_tt
      },
      set(value) {
        this.params.p_ngay_tt = value
      }
    },
    p_ngay_nh: {
      get() {
        return this.params.p_ngay_nh
      },
      set(value) {
        this.params.p_ngay_nh = value
      }
    },
    f_gom_hd_enabled() {
      return this.params.p_chk_gom_hd === '1'
    },
    get_thungan() {
      if (this.params.p_thungan_options === null || this.params.p_thungan_options.length <= 0) return null
      let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
      if (ret.length > 0) return ret[0]
      return null
    },
    getColumns() {
      return this.columns.filter(x => x.visible === true).map(x => ({ fieldName: x.fieldName, headerText: x.headerText }))
    },
    PTAggregatesColumns() {
      return [
        {
          field: 'MA_TT',
          type: 'Custom',
          footerTemplate: this.firstColumnTemplate,
          customAggregateFn: this.countMaTT
        },
        {
          field: 'TONGNO_',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienNo
        },
        {
          field: 'NOPS_',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienPhatSinh
        },
        {
          field: 'NODK_',
          type: 'Custom',
          footerTemplate: this.customTemplate,
          customAggregateFn: this.sumTienDauKy
        }
      ]
    }
  },
  data() {
    return {
      p_loaitien_options:[],
      p_hienthi_loai_tien:false,
      p_loaitien:1,
      mouseStartIndex: -1,
      mouseEndIndex: -1,
      fieldsThuNgan: { text: 'TEN_NV', value: 'NHANVIEN_ID' },
      fieldsLoaiTien: { text: 'TENLOAI', value: 'LOAITIEN_ID' },
      isFirstInit: false,
      KEY_SOQUYEN: 'KEY_SOQUYEN',
      KEY_SERI: 'KEY_SERI',
      KEY_SOSERI: 'KEY_SOSERI',
      f_tieuchi_timkiem_open: true,
      f_thongtin_gachno_open: true,
      is_open: false,
      hideTable: true,
      filterOptions: [],
      pageinfo: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      columns: [
        { fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Left', allowFiltering: true, freeze: 'Left', visible: true, width: 120 },
        { fieldName: 'MA_TT_NEO', headerText: 'Mã TT Neo', textAlign: 'Left', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'MAGOM', headerText: 'Mã Gom', textAlign: 'Left', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'MATB_DD', headerText: 'Mã đại diện', textAlign: 'Left', allowFiltering: true, visible: true, width: 130 },
        { fieldName: 'TONGNO', headerText: 'Tiền nợ', textAlign: 'Right', visible: false, cssClass: 'grid-ls-tongno', width: 130},
        { fieldName: 'TONGNO_', headerText: 'Tiền nợ', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-tongno', width: 130, type:'number',format:"N0"},
        { fieldName: 'NOPS', headerText: 'Tiền phát sinh', textAlign: 'Right', allowFiltering: true, visible: false },
        { fieldName: 'NOPS_', headerText: 'Tiền phát sinh', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-phatsinh', width: 130, type:'number',format:"N0" },
        { fieldName: 'NODK', headerText: 'Tiền đầu kỳ', textAlign: 'Right', allowFiltering: true, visible: false },
        { fieldName: 'NODK_', headerText: 'Tiền đầu kỳ', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-dauky', width: 130, type:'number',format:"N0" },
        { fieldName: 'NGAY_TT', headerText: 'Ngày TT', textAlign: 'Right', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'SO_TB', headerText: 'Số TB', textAlign: 'Right', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'CHUNGTU', headerText: 'Chứng từ', textAlign: 'Right', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'NGANHANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'TEN_TT', headerText: 'Tên TT', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'HINHTHUC_TT', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'SDT_LH', headerText: 'SĐT LH', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'SDT_TT', headerText: 'SĐT TT', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_DL', headerText: 'Mã ĐL', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_IG', headerText: 'Mã IG', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_ST', headerText: 'MST', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'STK', headerText: 'Số tài khoản', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_TUYEN', headerText: 'Tuyến thu', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'USER_GN', headerText: 'User gạch', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'NHANVIEN_NV', headerText: 'Nhân viên NV', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DONVI_TO', headerText: 'Tổ', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DONVI_PHONG', headerText: 'Phòng', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'THANHTOAN_ID', headerText: '', visible: false },
        { fieldName: '_CHECK', headerText: '', visible: false }
      ],
      columns_USD: [
        { fieldName: 'MA_TT', headerText: 'Mã TT', textAlign: 'Left', allowFiltering: true, freeze: 'Left', visible: true, width: 120 },
        { fieldName: 'MA_TT_NEO', headerText: 'Mã TT Neo', textAlign: 'Left', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'MAGOM', headerText: 'Mã Gom', textAlign: 'Left', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'MATB_DD', headerText: 'Mã đại diện', textAlign: 'Left', allowFiltering: true, visible: true, width: 130 },
        { fieldName: 'TONGNO', headerText: 'Tiền nợ', textAlign: 'Right', visible: false, cssClass: 'grid-ls-tongno', width: 130 },
        { fieldName: 'TONGNO_', headerText: 'Tiền nợ', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-tongno', width: 130, type:'number',format:"N2" },
        { fieldName: 'NOPS', headerText: 'Tiền phát sinh', textAlign: 'Right', allowFiltering: true, visible: false },
        { fieldName: 'NOPS_', headerText: 'Tiền phát sinh', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-phatsinh', width: 130, type:'number',format:"N2" },
        { fieldName: 'NODK', headerText: 'Tiền đầu kỳ', textAlign: 'Right', allowFiltering: true, visible: false },
        { fieldName: 'NODK_', headerText: 'Tiền đầu kỳ', textAlign: 'Right', allowFiltering: true, visible: true, cssClass: 'grid-ls-dauky', width: 130 , type:'number',format:"N2"},
        { fieldName: 'NGAY_TT', headerText: 'Ngày TT', textAlign: 'Right', allowFiltering: true, visible: true, width: 100 },
        { fieldName: 'SO_TB', headerText: 'Số TB', textAlign: 'Right', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'CHUNGTU', headerText: 'Chứng từ', textAlign: 'Right', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'NGANHANG', headerText: 'Ngân hàng', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'TEN_NHOM', headerText: 'Tên nhóm', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'TEN_TT', headerText: 'Tên TT', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'HINHTHUC_TT', headerText: 'Hình thức TT', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'SDT_LH', headerText: 'SĐT LH', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'SDT_TT', headerText: 'SĐT TT', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_DL', headerText: 'Mã ĐL', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_IG', headerText: 'Mã IG', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_ST', headerText: 'MST', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'STK', headerText: 'Số tài khoản', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'MA_TUYEN', headerText: 'Tuyến thu', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'USER_GN', headerText: 'User gạch', textAlign: 'Left', allowFiltering: true, visible: true, width: 120 },
        { fieldName: 'NHANVIEN_NV', headerText: 'Nhân viên NV', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DONVI_TO', headerText: 'Tổ', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'DONVI_PHONG', headerText: 'Phòng', textAlign: 'Left', allowFiltering: true, visible: true },
        { fieldName: 'THANHTOAN_ID', headerText: '', visible: false },
        { fieldName: '_CHECK', headerText: '', visible: false }
      ],
      params: {
        nganhang: {
          id: '',
          text: '',
          ma_gom: ''
        },
        p_chk_dschon: '0',
        p_kycuoc: null,
        p_chk_thang_tt: '1',
        p_thang_tt_options: [],
        p_thang_tt: [],
        p_chk_nhom_ht: '1',
        p_nhom_ht_options: [],
        p_nhom_ht: null,
        p_chk_httt: '1',
        p_httt_all_options: [],
        p_httt_options: [],
        p_httt: [],
        p_kieuno_options: [{ id: 0, text: 'Tổng nợ' }, { id: 1, text: 'Phát sinh' }, { id: 2, text: 'Đầu kỳ' }],
        p_kieuno: 0,
        p_chk_tien_tu: '0',
        p_chk_tien_den: '0',
        p_tien_tu: 0,
        p_tien_den: 0,
        p_chk_phantrang: '0',
        p_phantrang: 0,
        p_thungan: null,
        p_thungan_options: [],
        p_nganhang: null,
        p_nganhang_options: [],
        p_ngay_tt: new Date(),
        p_ngay_nh: new Date(),
        p_chk_gom_hd: '0',
        p_gom_hd: null,
        p_chungtu: null,
        p_gachno_ghichu: null,
        p_tieuchi_gachno: '0',
        p_donviqlno_id: null,
        p_quaythu_id: null,
        /*
          p_httt_gn_options: [
            { id: 6, text : "Thu qua Ủy nhiệm thu"},
            { id: 16, text : "Thu qua Ủy nhiệm chi"},
            { id: 166, text : "UNT_tự động"},
            { id: 167, text : "UNC_tự động"},
            { id: 170, text : "UNT_theo file"},
            { id: 171, text : "UNC_theo file"},
            ],
          */
        p_httt_gn_options: [],
        p_httt_gn: null,
        p_ngaygach_tien: -1,
        p_ngaygach_lui_thang: -1, // Số lượng ngày được phép gạch lùi sang tháng trước
        p_ngayxoa_lui: -1, // Số lượng ngày được phép xóa
        p_ngaygach_lui_thanght: -1, // Số lượng ngày được phép gạch lùi trong tháng hiện tại
        p_quyen_dd: false,
        p_gachno_chuan: -1,
        p_kiemtra_mk: true,
        p_request_id: 0,
        p_seri: '',
        p_so_seri: 0,
        p_so_quyen: 0,
        timer: 0,
        value1: [],
        value2: [],
        value3: [],
        counterInterval: null,
        customTemplate: function () {
          return {
            template: Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
              data() {
                return { data: { data: {} } }
              }
            })
          }
        },
        firstColumnTemplate: function () {
          return {
            template: Vue.component('footerTemplate', {
              template: `<input type="text" class="form-control tright sum-record" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px; margin-left: -50px" />`,
              data() {
                return { data: { data: {} } }
              }
            })
          }
        }
      },
      configs: {
        thang_tt: {
          fields: { text: 'text', value: 'id' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        // p_httt: { text: 'HTTT', value: 'HTTT_ID' },
        httt: {
          fields: { text: 'HTTT', value: 'HTTT_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        },
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        p_gachno_nganhang: { text: 'TEN_NH', value: 'NGANHANG_ID' },
        fieldsNhomHTTT: { text: 'NHOM_HTTT', value: 'NHOM_HTTT_ID' },
        fieldsHTTTGN: { text: 'HINHTHUC', value: 'HTTT_ID' },
        htttTemplate: function () {
          return {
            template: Vue.component('itemTemplate', {
              template: `<span><span class='name'>{{data.HINHTHUC}}</span><span class='ghichu'>{{data.GHICHU}}</span></span>`,
              data() {
                return {
                  data: {}
                }
              }
            })
          }
        }
      },
      bk_results: [],
      results: [],
      totalItems: 0,
      keyDS: 0
    }
  },
  methods: {
    mouseup(e) {
      // console.log(e)
      if (
        e.target.classList.contains('e-rowcell')
      ) {
        let index = parseInt(e.target.getAttribute('Index'), 10)
        if (index < this.mouseStartIndex) {
          let t = this.mouseStartIndex
          this.mouseStartIndex = index
          this.mouseEndIndex = t
        } else this.mouseEndIndex = index

        let obj = this.$refs.dsThanhToan.$refs.grid.ej2Instances
        if (obj.currentViewData.length > 0) {
          let data = obj.currentViewData
          let selectedCurrentPage = []
          // console.log(this.mouseStartIndex, this.mouseEndIndex)
          data.forEach((val, idx) => {
            if (idx >= this.mouseStartIndex && idx <= this.mouseEndIndex) {
              let f = this.results.find(x => x.THANHTOAN_ID === val.THANHTOAN_ID && x.TEN_NHOM === val.TEN_NHOM)

              if (f._CHECK) f._CHECK = false
              else {
                f._CHECK = true
                selectedCurrentPage.push(idx)
              }
            } else {
              if (val._CHECK) {
                selectedCurrentPage.push(idx)
              }
            }
          })
          obj.selectRows(selectedCurrentPage)
          this.refreshAggPhieu()
        }
      }
    },
    mousedown(e) {
      // console.log(e)
      if (
        e.target.classList.contains('e-rowcell')
      ) {
        let index = parseInt(e.target.getAttribute('Index'), 10)
        this.mouseStartIndex = index
      }
    },
    countMaTT() {
      let selected = this.results.filter(x => x._CHECK === true).length
      return `${selected}/${this.results.length}`
    },
    sumTienDauKy() {
      const selected = this.results.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.NODK, 0)
      const total = this.results.reduce((a, b) => +a + +b.NODK, 0)
      if (this.p_loaitien==1) 
        return currency(selected) + '/' + currency(total)
      else
        return (selected) + '/' + (total)
    },
    sumTienNo() {
      const selected = this.results.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TONGNO, 0)
      const total = this.results.reduce((a, b) => +a + +b.TONGNO, 0)
      if (this.p_loaitien==1) 
        return currency(selected) + '/' + currency(total)
      else
        return (selected) + '/' + (total)
    },
    sumTienPhatSinh() {
      const selected = this.results.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.NOPS, 0)
      const total = this.results.reduce((a, b) => +a + +b.NOPS, 0)
      if (this.p_loaitien==1) 
        return currency(selected) + '/' + currency(total)
      else
        return (selected) + '/' + (total)
    },
    loadThongTinSeri() {
      let s = this.getDataFromStorage(this.KEY_SERI)
      this.params.p_seri = s === null ? '' : s
      s = this.getDataFromStorage(this.KEY_SOQUYEN)
      this.params.p_so_quyen = s === null ? 0 : s
      s = this.getDataFromStorage(this.KEY_SOSERI)
      this.params.p_so_seri = s === null ? 0 : s
      // console.log(this.params.p_seri)
    },
    async loadThangTT() {
      this.params.p_thang_tt_options = []
      this.params.p_thang_tt = []
      // let preSelected = []

      const res = await CommonAPI.getDSChuKyNo(this.axios)
      const data = await res.data

      return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      // .then((response) => {
      //   if (response.data.error_code === 'BSS-00000000' &&
      //         response.data.data !== undefined &&
      //         response.data.data.length > 0) {
      //     let count = 3
      //     response.data.data.forEach((item) => {
      //       if (count > 0) {
      //         preSelected.push(item.CHUKYNO)
      //         count--
      //       }
      //       this.params.p_thang_tt_options.push({
      //         id: item.CHUKYNO,
      //         text: item.THANGNO
      //       })
      //     })
      //   }
      // })
      // .catch(error => {
      //   console.log(error)
      // })
      // .finally(() => {
      //   this.params.p_thang_tt = [...preSelected]
      // })
    },
    async loadNhomHTTT() {
      try {
        const res = await CommonAPI.getNhomHinhThucThanhToan(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {

      }
      // CommonAPI.getNhomHinhThucThanhToan(this.axios)
      //   .then((response) => {
      //     if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined &&
      //           response.data.data.length > 0) {
      //       this.params.p_nhom_ht_options = response.data.data
      //       this.params.p_nhom_ht = NHOM_HTTT.GACHNO_NGANHANG
      //       // this.params.p_nhom_ht = response.data.data[0].NHOM_HTTT_ID
      //       // this.onChangeNhomHTTT( {value: response.data.data[0].NHOM_HTTT_ID})
      //     } else {
      //       this.params.p_nhom_ht_options = []
      //     }
      //   })
      //   .catch(error => {
      //     console.log(error)
      //   })
      //   .finally(() => {
      //   })
    },
    async loadAllHTTT() {
      try {
        const res = await CommonAPI.getHinhThucThanhToan(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {

      }
      // CommonAPI.getHinhThucThanhToan(this.axios)
      //   .then((response) => {
      //     if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined &&
      //           response.data.data.length > 0) {
      //       this.params.p_httt_all_options = response.data.data
      //     } else {
      //       this.params.p_httt_all_options = []
      //     }
      //   })
      //   .catch(error => {
      //     console.log(error)
      //   })
      //   .finally(() => {
      //   })
    },
    loadHTT(idNhom) {
      const res = this.params.p_httt_all_options.filter(x => x.NHOM_HTTT_ID === idNhom)
      // console.log(this.params.p_httt_all_options, typeof(idNhom))
      return res
    },
    async loadThuNgan() {
      try {
        const res = await CommonAPI.getDanhSachNhanVienThuNgan(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {

      }
      // CommonAPI.getDanhSachNhanVienThuNgan(this.axios)
      //   .then((response) => {
      //     if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined &&
      //           response.data.data.length > 0) {
      //       this.params.p_thungan_options = response.data.data
      //       this.params.p_thungan = response.data.data[0].NHANVIEN_ID
      //       this.params.p_quaythu_id = response.data.data[0].DONVI_ID
      //       this.getDonViQuanLy()
      //     } else {
      //       this.params.p_thungan_options = []
      //       this.params.p_thungan = null
      //     }
      //   })
      //   .catch(error => {
      //     console.log(error)
      //   })
      //   .finally(() => {
      //   })
    },
    async getDonViQuanLy() {
      // console.log('a')
      try {
        let postData = {
          pDonViId: this.params.p_quaythu_id,
          pDSLoaiDV: LOAI_DV.DONVIQL_TT + ',' + LOAI_DV.DONVIQL_TT_BUUDIEN
        }
        const res = await GachNoAPI.getDonViQuanLy(this.axios, postData)
        const data = await res.data
        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          this.params.p_donviqlno_id = data.data[0].DONVI_ID
        } else {

        }
      } catch (error) {

      }
      // GachNoAPI.getDonViQuanLy(this.axios, postData)
      //   .then(
      //     (response) => {
      //       /// console.log(response)
      //       if (response.data.error_code === 'BSS-00000000' &&
      //         response.data.data !== undefined) {
      //         this.params.p_donviqlno_id = response.data.data[0].DONVI_ID
      //         // this.params.p_donvi_quanly_ten = response.data.data[0].TEN_DV

      //         if (this.params.p_donvi_quanly_id === '0' &&
      //           this.params.p_nhom_httt !== NHOM_HTTT.DIEU_CHINH_NO) {
      //           DialogObj = DialogUtility.alert({
      //             title: 'Thông báo',
      //             content: "<div style='padding:10px'>User của bạn không được phép thao tác trong chức năng này</div>",
      //             okButton: { text: ' OK ', click: function () { this.$router.push('/') } },
      //             showCloseIcon: true,
      //             closeOnEscape: true,
      //             animationSettings: { effect: 'Zoom' }
      //           })
      //           return false
      //         }
      //       }
      //     }
      //   )
      //   .catch(error => {
      //     this.$toast.error(error.data.message_detail)
      //   })
    },
    async getThamSoGachNo() {
      try {
        const res = await CommonAPI.getThamSoGachNo(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {
        return []
      }
      // GachNoAPI.getThamSoGachNo(this.axios)
      //   .then(
      //     (response) => {
      //       // console.log(response)
      //       if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined) {
      //         this.params.p_ngaygach_lui_thang = parseInt(response.data.data[0].NGAY_GL)
      //         this.params.p_ngaygach_tien = parseInt(response.data.data[0].NGAY_GT)
      //         this.params.p_ngayxoa_lui = parseInt(response.data.data[0].NGAY_XL)
      //         this.params.p_ngaygach_lui_thanght = parseInt(response.data.data[0].NGAY_GLT)
      //       } else { this.$toast.error(response.data.message) }
      //     }
      //   )
      //   .catch(error => {
      //     this.$toast.error(error.data.message_detail)
      //   })
      //   .finally(() => {})
    },
    async loadNganHang() {
      try {
        const res = await CommonAPI.getDanhSachNganHang(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {

      }
      // CommonAPI.getDanhSachNganHang(this.axios)
      //   .then((response) => {
      //     if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined &&
      //           response.data.data.length > 0) {
      //       this.params.p_nganhang_options = response.data.data
      //       this.params.p_nganhang = response.data.data[0].NGANHANG_ID
      //     } else {
      //       this.params.p_nganhang_options = []
      //     }
      //   })
      //   .catch(error => {
      //     this.$toast.error(error.data.message_detail)
      //   })
      //   .finally(() => {})
    },
    async loadHTTTNganHang() {
      try {
        const res = await CommonAPI.getDSHTTTNganHang(this.axios)
        const data = await res.data
        return data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0 ? data.data : []
      } catch (error) {

      }
      // CommonAPI.getDSHTTTNganHang(this.axios)
      //   .then((response) => {
      //     if (response.data.error_code === 'BSS-00000000' &&
      //           response.data.data !== undefined &&
      //           response.data.data.length > 0) {
      //       this.params.p_httt_gn_options = response.data.data
      //       this.params.p_httt_gn = this.params.p_httt_gn_options[0].HTTT_ID
      //     } else {
      //       this.params.p_httt_gn_options = []
      //       this.params.p_httt_gn = null
      //     }
      //   })
      //   .catch(error => {
      //     this.$toast.error(error.data.message_detail)
      //   })
      //   .finally(() => {})
    },
    onChangeNhomHTTT(args) {
      this.params.p_httt = []
      if (args.value !== null) {
        this.params.p_httt_options = this.loadHTT(args.value)
        // this.params.p_httt = this.params.p_httt_options.map(x => x.HTTT_ID)
        if (this.isFirstInit) {
          this.params.p_httt = [166, 167, 170, 171, 11, 16]
          this.isFirstInit = false
          let obj = this.$refs.ref_httt.ej2Instances
          console.log(obj)
        }
      }
    },
    validateData: function () {
      let error = []
      if (this.params.p_kieuno === null) { error.push('Trường kiểu nợ bắt buộc nhập.') }
      if (this.p_kycuoc === null) { error.push('Vui lòng chọn chu kỳ hóa đơn.') }
      if (this.params.p_chk_thang_tt === '1' &&
        this.params.p_thang_tt === null) { error.push('Trường Tháng thanh toán bắt buộc nhập') }
      if (this.params.p_chk_nhom_ht === '1' &&
        this.params.p_nhom_ht === null) { error.push('Trường Nhóm hình thức bắt buộc nhập') }
      if (this.params.p_chk_httt === '1' &&
        this.params.p_httt === null) { error.push('Trường Hình thức thanh toán bắt buộc nhập') }
      if (this.params.p_chk_tien_tu === '1' &&
        this.params.p_tien_tu === null) { error.push('Trường Tiền từ bắt buộc nhập') }
      if (this.params.p_chk_tien_den === '1' &&
        this.params.p_tien_den === null) { error.push('Trường Tiền đến bắt buộc nhập') }
      if (this.params.p_chk_phantrang === '1' &&
        this.params.p_phantrang === null) { error.push('Trường Phân trang bắt buộc nhập') }

      if (error.length > 0) {
        this.$toast.error(error.join('<BR>'))
        return false
      }
      return true
    },
    validateGachNo: function () {
      let errors = []

      if (this.results.length <= 0) {
        this.$toast.error('Chưa có danh sách gạch nợ không thể gạch')
        return false
      }

      let selected = this.$refs.dsThanhToan.getSelectedRecords()

      if (selected.length <= 0) {
        this.$toast.error('Bạn chưa chọn thanh toán để gạch')
        return false
      }

      // selected.forEach((item) => {
      //   if (this.params.p_tieuchi_gachno === 1 && item.NOPS === 0) // gạch đầu kỳ
      //   {
      //     this.$toast.error('Tiền nợ phát sinh bằng 0 không thể gạch nợ')
      //   } else if (this.params.p_tieuchi_gachno === 2 && item.NODK === 0) {
      //     this.$toast.error('Tiền nợ đầu kỳ bằng 0 không thể gạch nợ')
      //   }
      // })

      if (this.params.p_nganhang === null) {
        errors.push('Bạn chưa chọn ngân hàng')
      }

      if (this.params.p_httt_gn === null) {
        errors.push('Bạn chưa chọn hình thức thanh toán')
      }

      if (this.params.p_ngay_nh > this.params.p_ngay_tt) {
        errors.push('Ngày ngân hàng không được lớn hơn ngày thanh toán')
      }

      if (this.params.p_thungan === null) {
        errors.push('User của bạn không được phép thao tác trong chức năng này')
      } else if (this.params.p_donviqlno_id === null || this.params.p_donviqlno_id === 0) {
        errors.push('User của bạn không được phép thao tác trong chức năng này')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      if (this.params.p_tieuchi_gachno === '2') {
        let f = selected.filter(x => x.NODK <= 0)
        if (f.length > 0) {
          this.$toast.error('Bạn không thể gạch ' + f.length + ' mã với tiền đầu kỳ <=0 (' + f.map(x => x.MA_TT).join(',') + ')')
          return false
        }
      } else if (this.params.p_tieuchi_gachno === '1') {
        let f = selected.filter(x => x.NOPS <= 0)
        if (f.length > 0) {
          this.$toast.error('Bạn không thể gạch ' + f.length + ' mã với tiền phát sinh <=0 (' + f.map(x => x.MA_TT).join(',') + ')')
          return false
        }
      }

      return true
    },
    onSearch: function () {
      this.is_open = false
      if (!this.validateData()) { return false }
      this.getLichSuThanhToan()
    },
    getLichSuThanhToan: function () {
      let arrMonths = this.params.p_thang_tt.map(function (item) {
        return parseInt(item, 10)
      })

      let arrHTTT = []
      if (this.params.p_httt !== null) {
        arrHTTT = this.params.p_httt.map(function (item) {
          return parseInt(item, 10)
        })
      }
      let postData = {
        denTien: this.params.p_chk_tien_den === '1' ? this.params.p_tien_den : null,
        tuTien: this.params.p_chk_tien_tu === '1' ? this.params.p_tien_tu : null,
        kieuNo: this.params.p_kieuno,
        kyCuoc: this.params.p_kycuoc,
        pLoaiTien:this.p_loaitien,
        nhomHtttId: this.params.p_chk_nhom_ht === '1' ? this.params.p_nhom_ht : null,
        dsHttt: arrHTTT.join(','),
        dsThang: arrMonths.join(','),
        page: this.pageinfo.page,
        size: this.pageinfo.maxSize
      }
      this.fetchData(postData)
    },
    fetchData: function (postData) {
      this.loading(true)

      TraCuuAPI.getTraCuuLichSuThanhToan(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.data !== undefined &&
              response.data.data.data.length > 0) {              
              let obj = response.data.data.data.filter(x => x.TONGNO > 0)
              // this.bk_results = [...response.data.data.data]
              this.results = obj.map(x => ({ ...x, _CHECK: false, TONGNO_: x.TONGNO, NOPS_: x.NOPS, NODK_: x.NODK }))
              this.bk_results = [...this.results]
              this.keyDS++
               
              // this.results = response.data.data.data
              this.pageinfo = {
                page: response.data.data.page,
                maxSize: response.data.data.maxSize,
                // totalElement: response.data.data.totalElement,
                totalElement: 1000,
                // totalPages: response.data.data.totalPages,
                totalPages: 10,
                sort: response.data.data.sort,
                propertiesSort: response.data.data.propertiesSort
              }
            } else {
              this.$toast.error('Không tìm thấy kết quả.')
              this.resetResult()
            }
          }
        )
        .catch(error => {
          console.log(error)
          // this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    confirmGachNhom() {
      let thungan = this.getThungan()

      let postData = {
        'pKyCuoc': this.params.p_kycuoc,
        'pChiPhi': 0,
        'pChungTu': (this.params.p_chungtu !== null ? '' + this.params.p_chungtu : '').trim(),
        'pCoTamThu': 1,
        'pDSTra': '', // JSON.stringify(dsTra),
        'pDonViQLNoId': this.params.p_donviqlno_id,
        'pGhiChu': ('' + this.params.p_gachno_ghichu).trim(),
        'pHTTTId': this.params.p_httt_gn,
        'pIPCN': '-',
        'pLanTra': 1,
        'pLoaiGach': 4,
        'pLoaiTienId': this.p_loaitien,
        'pMaNVTC': this.$auth.getMaNhanVien(),
        // "pMaTN": this.params.p_thungan, //this.$auth.getMaNhanVien(),
        'pMaTN': thungan.MA_NV, // this.$auth.getMaNhanVien(),
        'pMayCN': '-',
        'pNgayTT': moment(this.params.p_ngay_tt).format('DD/MM/YYYY'),
        'pNguoiDungId': this.$auth.getNguoiDungID(),
        'pQuayThuId': this.params.p_quaythu_id,
        'pNganHangId': this.params.p_nganhang,
        'pNgayNganHang': moment(this.params.p_ngay_nh).format('DD/MM/YYYY'),
        'pQuyen': '1',
        'pSeri': '1',
        'pSoSeri': '1',
        'pKieuTra': this.params.p_tieuchi_gachno == '1' ? '5' : this.params.p_tieuchi_gachno == '2' ? '0' : '1',
        'pThuNganId': this.params.p_thungan
      }

      let dsTra = []
      // let ds = this.$refs.dsThanhToan.getSelectedRecords()
      let ds = this.results.filter(x => x._CHECK === true)
      let seri = 1

      ds.forEach((item, index) => {
        let tientra = 0
        if (this.params.p_tieuchi_gachno === '1') tientra = item.NOPS
        else if (this.params.p_tieuchi_gachno === '2') tientra = item.NODK
        else tientra = item.TONGNO
        dsTra.push(
          {
            MA_TT: item.MA_TT,
            TIENTRA: tientra,
            SERI: item.SERI,
            QUYEN: item.QUYEN,
            SO_SERI: seri++// item.SOSERI
          }
        )
      })

      postData.pDSTra = dsTra

      this.loading(true)
      this.gachNoTienMatDanhSach(postData)
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.length > 0
            ) {
              this.$toast.error('Đã phát sinh lỗi khi gạch nợ.')
            }
            if (res.data.error_code === 'BSS-00000000') {
              // this.$toast.success('Gạch nợ thành công.')
              this.loading(true)
              this.$toast.success('Đã gửi yêu cầu gạch nợ thành công với ID: ' + res.data.data)
              this.params.p_request_id = res.data.data
              this.params.timer = 0

              this.countTimer()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
          this.loading(false)
        })
        .finally(() => {
          this.loading(false)
        })
    },

    confirmGachNo() {
      if (DialogObj !== undefined) DialogObj.hide()
      let loaigach = this.params.p_tieuchi_gachno === '0' ? 'tổng nợ' : this.params.p_tieuchi_gachno === '1' ? 'phát sinh' : 'đầu kỳ'
      let thongbao = 'Bạn có muốn gạch nợ ' + loaigach

      let tmp = this.results.filter(x => x._CHECK === true).map(x => x.TEN_NHOM)
      let group = [...new Set(tmp)]

      if (group.length > 1) {
        thongbao += ' và gạch nợ nhiều nhóm chuyền khoản không? '
      }
      let vm = this

      DialogObj = DialogUtility.confirm({
        title: 'Thông báo',
        content: '<div style="padding:10px">' + thongbao + '</div>',
        okButton: { text: 'Đồng ý', click: function () { DialogObj.hide(); vm.confirmGachNhom() } },
        cancelButton: { text: ' Hủy ' },
        showCloseIcon: true,
        closeOnEscape: true,
        animationSettings: { effect: 'Zoom' }
      })
    },
    onGachNo: function () {
      if (!this.validateGachNo()) { return false }
      let selected = this.results.filter(x => x._CHECK === true)
      let f = selected.filter(x => x.TONGNO <= 0)

      // console.log(selected)

      if (f.length > 0) {
        DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: '<div style="padding:10px">Trong danh sách có ' + f.length + ' mã đang có tổng nợ <0. Bạn có muốn tiếp tục gạch không? </div>',
          okButton: { text: 'Đồng ý', click: function () { DialogObj.hide(); this.confirmGachNo() } },
          cancelButton: { text: ' Hủy ' },
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
        })
      } else this.confirmGachNo()
    },

    getThungan() {
      if (this.params.p_thungan_options === null || this.params.p_thungan_options.length <= 0) { return null }
      let ret = this.params.p_thungan_options.filter(x => x.NHANVIEN_ID === this.params.p_thungan)
      if (ret.length > 0) { return ret[0] }
      return null
    },
    async gachNoTienMatDanhSach(postData) {
      try {
        const results = await GachNoAPI.gachNoDSTienMat(this.axios, postData)
        return results
      } catch (err) {
        this.$toast.error(err.data.message_detail)
      }
    },
    onPhieuTra: function () {
      if (this.params.p_thungan === null) {
        this.$toast.error('Bạn chưa chọn thu ngân. Hãy kiểm tra lại.')
        return false
      }
      this.$refs.refPhieuGachNoTienMat.openDialog()
    },
    onHelp: function () {
      this.$toast.error('Chức năng đang được phát triển. Vui lòng thử lại sau.')
      return false
    },
    exportExcel: function () {
      this.$refs.refYesNoCancel.openDialog()
    },
    // getThamSoGachNo () {
    //   try {
    //     GachNoAPI.getThamSoGachNo(this.axios).then(
    //       (response) => {
    //         // console.log(response)
    //         if (response.data.error_code === 'BSS-00000000' &&
    //               response.data.data !== undefined) {
    //           this.params.p_ngaygach_lui_thang = parseInt(response.data.data[0].NGAY_GL)
    //           this.params.p_ngaygach_tien = parseInt(response.data.data[0].NGAY_GT)
    //           this.params.p_ngayxoa_lui = parseInt(response.data.data[0].NGAY_XL)
    //           this.params.p_ngaygach_lui_thanght = parseInt(response.data.data[0].NGAY_GLT)
    //         } else { this.$toast.error(response.data.message) }
    //       }
    //     )
    //   } catch (error) {
    //     this.$toast.error(error)
    //   }
    // },
    resetResult() {
      this.results = []
      this.pageinfo = {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      }
    },
    onFilteringHTTT(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('HTTT', 'contains', e.text, true) : query
      e.updateData(this.params.p_httt_options, query)
    },
    onFilterNganHang(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NH', 'contains', e.text, true) : query
      e.updateData(this.params.p_nganhang_options, query)
    },
    chukycuocChangeHandler(e) {
      console.log('Kỳ cuooc chnage', e)
      if (e.p_chuky_no !== null) { this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no } else { this.params.p_kycuoc = null }
    },
    onFilteringNhomHTTT(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('NHOM_HTTT', 'contains', e.text, true) : query
      e.updateData(this.params.p_ht_options, query)
    },
    RowChanged(e) {

    },
    pageChangeHandler(e) {
      // console.log(e)
      // this.pageinfo.page = e

      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize
      this.getLichSuThanhToan()
    },
    yesHandler() {
      let arrMonths = this.params.p_thang_tt.map(function (item) {
        return parseInt(item, 10)
      })

      let arrHTTT = []
      if (this.params.p_httt !== null) {
        arrHTTT = this.params.p_httt.map(function (item) {
          return parseInt(item, 10)
        })
      }
      let postData = {
        denTien: this.params.p_chk_tien_den === '1' ? this.params.p_tien_den : null,
        tuTien: this.params.p_chk_tien_tu === '1' ? this.params.p_tien_tu : null,
        kieuNo: this.params.p_kieuno,
        kyCuoc: this.params.p_kycuoc,
        nhomHtttId: this.params.p_chk_nhom_ht,
        dsHttt: arrHTTT.join(','),
        dsThang: arrMonths.join(','),
        page: 0,
        size: 10000
      }

      this.loading(true)

      TraCuuAPI.getTraCuuLichSuThanhToan(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000' &&
              response.data.data !== undefined &&
              response.data.data.data !== undefined &&
              response.data.data.data.length > 0) {
              let r = response.data.data.data
              let excelExportProperties = {
                dataSource: r,
                fileName: 'TraCuuNoTheoLichSuThanhToan.xlsx'
              }
              this.$refs.dsThanhToan.excelExport(excelExportProperties)
            } else {
              this.$toast.error('Không tìm thấy dữ liệu đề xuất file.')
            }
          }
        )
        .catch(error => {
          // console.log(error)
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    noHandler() {
      let selectedRows = this.$refs.dsThanhToan.getSelectedRecords()

      if (selectedRows.length > 0) {
        let excelExportProperties = {
          dataSource: selectedRows,
          fileName: 'TraCuuNoTheoLichSuThanhToan.xlsx'
        }
        this.$refs.dsThanhToan.excelExport(excelExportProperties)
      } else { this.$toast.error('Không tìm thấy dữ liệu đề xuất file.') }
    },
    countTimer() {
      if (this.params.p_request_id > 0) {
        this.loading(true)
        this.params.counterInterval = setInterval(
          function () {
            if (this.params.p_request_id > 0) {
              this.params.timer += 1
              if (this.params.timer % 10 === 0) {
                this.checkDSDaGach(this.params.p_request_id)
              }
            }
          }.bind(this), 1000)
      }
    },
    // checkDSDaGach (id) {
    //   GachNoAPI.checkDSDaGach(this.axios, {pId: id})
    //     .then(
    //       (res) => {
    //         if (
    //           res.data.error_code === 'BSS-00000000' &&
    //             res.data.data !== undefined &&
    //             res.data.data.length > 0
    //         ) {
    //           if (res.data.data[0].TRANG_THAI === 2 || res.data.data[0].TRANG_THAI === 3) {
    //             this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')')
    //             this.params.p_request_id = 0
    //             clearInterval(this.params.counterInterval)
    //             this.loading(false)
    //           } else {
    //             this.this.$toast.success('Đã gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ')')
    //           }
    //         }
    //       }
    //     )
    // },
    checkDSDaGach(id) {
      GachNoAPI.checkDSDaGach(this.axios, { pId: id })
        .then(
          (res) => {
            if (res.data.error_code === 'BSS-00000000' &&
              res.data.data !== undefined &&
              res.data.data.length > 0) {
              let selected = this.results.filter(x => x._CHECK === true)
              let total = res.data.data[0].TONGSO_YEUCAU
              let success = res.data.data[0].TONGSO_THANHCONG
              let failed = res.data.data[0].TONGSO_LOI

              if (res.data.data[0].TRANG_THAI === 3) {
                if (res.data.data[0].TONGSO_THANHCONG === res.data.data[0].TONGSO_YEUCAU) {
                  let strMsg = `Gạch nợ thành công ID ${this.params.p_request_id}. Tổng yêu cầu : ${total}, tổng thành công : ${success}, tổng lỗi : ${failed}`
                  // this.$toast.success('Gạch nợ thành công. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  this.$toast.success(strMsg)

                } else {
                  // this.$toast.error('Gạch nợ đã phát sinh lỗi. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + ')')
                  let strMsg = `Gạch nợ ID ${this.params.p_request_id} đã phát sinh lỗi. Tổng yêu cầu : ${total}, tổng thành công : ${success}, tổng lỗi : ${failed}`
                  this.$toast.error(strMsg)
                  // this.dsErrors = []
                  let ds = []
                  if (res.data.data[0].DS_LOI !== null) {
                    res.data.data[0].DS_LOI.forEach((item, index) => {
                      ds.push({ MA_TT: item.MA_TT, THONGTINLOI: item.LOI })
                    })
                  }
                  // this.dsErrors = [...ds]
                }
                this.params.p_request_id = 0
                clearInterval(this.params.counterInterval)
                // let s = selected.map(x => x.THANHTOAN_ID)
                // let t = this.bk_results.filter(x => !s.includes(x.THANHTOAN_ID))
                // this.bk_results = [...t]
                // this.results = [...t]
                this.loadDSSauGachNo(selected)
                this.loading(false)
              } else if (res.data.data[0].TRANG_THAI === 2) {
                let strMsg = `Đã gạch xong ID ${this.params.p_request_id}. Tổng yêu cầu : ${total}, tổng thành công : ${success}, tổng lỗi : ${failed}`
                // this.$toast.error('Đã gạch xong. Tổng số thành công/ Tổng yêu cầu (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_MA_GACHNO + ') Tổng số lỗi ' + res.data.data[0].TONGSO_LOI)
                this.$toast.success(strMsg)
                // this.dsErrors = []
                let ds = []
                if (res.data.data[0].DS_LOI !== null) {
                  res.data.data[0].DS_LOI.forEach((item, index) => {
                    ds.push({ MA_TT: item.MA_TT, THONGTINLOI: item.LOI })
                  })
                }
                clearInterval(this.params.counterInterval)
                // let s = selected.map(x => x.THANHTOAN_ID)
                // let t = this.bk_results.filter(x => !s.includes(x.THANHTOAN_ID))
                // this.bk_results = [...t]
                // this.results = [...t]
                this.loadDSSauGachNo(selected)
                this.loading(false)
              } else {
                // const minutes = Math.floor(this.params.timer / 60)
                // const seconds = this.params.timer % 60
                // let strMsg = `Đang gạch nợ ID ${this.params.p_request_id}. Tổng yêu cầu ${total} (bản ghi). Thời gian đã thực hiện ${minutes} phút :${seconds} giây`
                // // this.$toast.success('Đã gạch (' + res.data.data[0].TONGSO_THANHCONG + '/' + res.data.data[0].TONGSO_YEUCAU + '). Thời gian ' + minutes + ':' + seconds)
                // this.$toast.success(strMsg)
              }
            }
          }
        )
        .catch(error => {
          if (error.data !== undefined) this.$toast.error('Đã phát sinh lỗi: ' + error.data.message_detail)
          else console.log(error)

          this.params.p_request_id = 0
          clearInterval(this.params.counterInterval)
          this.loading(false)
        })
    },
    loadDSSauGachNo(dsDaGach) {
      // clear
      this.params.p_chungtu = null
      this.params.p_gachno_ghichu = null

      let kieugach = this.params.p_tieuchi_gachno
      if (kieugach === '0') { // tổng nợ
        let s = dsDaGach.map(x => x.THANHTOAN_ID)
        let t = this.bk_results.filter(x => !s.includes(x.THANHTOAN_ID))
        this.bk_results = [...t]
        this.results = [...t]
      } else if (kieugach === '1') { // phát sinh
        let t = [...this.bk_results]
        dsDaGach.forEach(item => {
          // let f = t.filter(x => x.THANHTOAN_ID === item.THANHTOAN_ID)
          // if (f !== undefined) f.NOPS = 0
          let idx = t.findIndex(x => x.THANHTOAN_ID === item.THANHTOAN_ID && x.TEN_NHOM === item.TEN_NHOM)
          if (idx >= 0) {
            t[idx].NOPS = 0
            t[idx].TONGNO = t[idx].NODK
          }
          // console.log(t.filter(x => x.THANHTOAN_ID === item.THANHTOAN_ID))
        })
        this.bk_results = [...t]
        this.results = [...t]
      } else if (kieugach === '2') { // đầu kỳ
        let t = [...this.bk_results]
        dsDaGach.forEach(item => {
          let idx = t.findIndex(x => x.THANHTOAN_ID === item.THANHTOAN_ID && x.TEN_NHOM === item.TEN_NHOM)
          if (idx >= 0) {
            t[idx].NODK = 0
            t[idx].TONGNO = t[idx].NOPS
          }
        })
        this.bk_results = [...t]
        this.results = [...t]
      }
    },
    getDataFromStorage(key) {
      try {
        // console.log(localStorage.getItem(key))
        return localStorage.getItem(key)
      } catch (error) {
        // console.log(error)
        return null
      }
    },
    setDataToStorage(key, value) {
      try {
        localStorage.setItem(key, value)
        return true
      } catch (error) {
        return false
      }
    },
    openFilterDialog() {
      this.results = [...this.bk_results]
      this.keyDS++
      this.params.value1 = []
      this.params.value2 = []
      this.params.value3 = []
      this.$refs.refFilterDialog.openDialog()
    },
    onChangeCustomDropDown(id) {
      const isHIdden = $(id).is(':hidden')
      if (isHIdden) {
        $(id).show()
      } else {
        $(id).hide()
      }
    },
    onChangeNganHang(e) {
      if (e === null) return

      this.params.p_nganhang = e.NGANHANG_ID
      this.params.nganhang.id = e.NGANHANG_ID
      this.params.nganhang.text = e.TEN_NH
      this.params.nganhang.ma_gom = e.MA_GOM
      this.params.p_gom_hd = e.MA_GOM + (this.params.p_chungtu !== null ? this.params.p_chungtu : '').trim()
      $('#nganhang-pd').hide()
    },
    gomHDCheckChange(args) {
      // console.log(args)
      // if (args === '1') { // checked
      //   this.params.p_gom_hd = 'VT52'
      // } else {
      //   this.params.p_gom_hd = null
      // }
    },
    htttDataBound(args) {
      // if (this.isFirstInit) {
      //   this.isFirstInit = false
      //   console.log(this.$refs.ref_httt.ej2Instances.vueInstance)
      //   this.$refs.ref_httt.ej2Instances.vueInstance.selectedAll()
      // }
    },
    checkAll(f) {
      let temp = [...this.results]
      if (f) { // true
        let filter = this.$refs.dsThanhToan.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.dsThanhToan.filteredDataSource.map(x => x.THANHTOAN_ID)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i].THANHTOAN_ID)) {
              temp[i]._CHECK = true
            } /* else {
              temp[i]._CHECK = false
            } */
          }
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.results = [...temp]
    },
    checkBoxChange(args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        // for (let i = 0; i < this.results.length; i++) {
        //   this.results[i]._CHECK = args.checked
        // }
        this.checkAll(args.checked)
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowSelected(args) {
      // console.log('sel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.results.find(x => x.THANHTOAN_ID === args.data.THANHTOAN_ID && x.TEN_NHOM === args.data.TEN_NHOM)
        if (f !== undefined) f._CHECK = true
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowDeselected(args) {
      // console.log('desel', args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.results.find(x => x.THANHTOAN_ID === args.data.THANHTOAN_ID && x.TEN_NHOM === args.data.TEN_NHOM)
        if (f !== undefined) f._CHECK = false
        this.refreshAggPhieu()
      }
    },
    refreshAggPhieu() {
      this.$refs.dsThanhToan.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    dsPhieuDatabound(args) {
      // console.log('db')
      this.refreshGrid()
    },
    refreshGrid() {
      let obj = this.$refs.dsThanhToan.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    onFilterChange(data) {
      //  console.log(data)
      if (data === null) return
      if (data.length > 0) {
        let ret = []
        if (data[0] !== undefined) {
          ret = this.bk_results.map(x => x[data[0].key])
          let v1 = [...new Set(ret)]
          let aV1 = []
          v1.forEach(item => {
            aV1.push({ value: item })
          })
          this.params.value1 = [...aV1]
          // this.params.value1 = [...new Set(ret)]
          // console.log('valúe1', this.params.value1)
        }
        if (data[1] !== undefined) {
          ret = this.bk_results.filter(x => x[data[0].key] === data[0].value).map(x => x[data[1].key])
          // this.params.value2 = [...new Set(ret)]
          // console.log('valúe2', this.params.value2)
          let v2 = [...new Set(ret)]
          let aV2 = []
          v2.forEach(item => {
            aV2.push({ value: item })
          })
          this.params.value2 = [...aV2]
        }
      }
    },
    onAcceptFilterHandler(args) {
      // console.log(args)
      if (args !== null && args.length > 0) {
        let ret = this.bk_results.filter(x => x[args[0].key] === args[0].value)
        if (args[1] !== undefined) {
          let ret1 = ret.filter(x => x[args[1].key] === args[1].value)
          // console.log(ret1)
          ret = [...ret1]
        }
        this.results = [...ret]
        this.keyDS++
      } else {
        this.results = [...this.bk_results]
        this.keyDS++
      }
    },
    keyPressedHandler(args) {
      if (args.key === 'Insert') {
        this.checkAll(true)
      } else if (args.key === 'Delete') {
        this.checkAll(false)
      }
    },
    queryCellInfo(args) {
      // if (args.column.field === 'TONGNO') {
      //   args.cell.classList.add('grid-ls-tongno')
      // } else if (args.column.field === 'NOPS') {
      //   args.cell.classList.add('grid-ls-phatsinh')
      // } else if (args.column.field === 'NODK') {
      //   args.cell.classList.add('grid-ls-dauky')
      // }
    },
    onChangeThuNgan(args) {
      console.log(args)
      if (args === null) this.params.p_donviqlno_id = null
      this.getDonViQuanLy()
    },
    onChangeLoaiTien(e) {      
      //this.clearData();

    },
    getThamSoMD: async function()
    {
      CommonAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'GACHNO_LOAITIEN'})
            .then(res => {
              if(res.data.error_code === 'BSS-00000000'
                && res.data.data !== undefined
                && res.data.data.length > 0)
                {
                  this.p_hienthi_loai_tien=true
                }
            })
            .catch(error => {
              this.p_hienthi_loai_tien=false
            })
          },
    getDSLoaiTien () {
      try {
        GachNoAPI.getDSLoaiTien(this.axios, {pKyCuoc: "20240601"})
          .then(
            (response) => {
              if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
                this.p_loaitien_options = response.data.data
                this.p_loaitien = response.data.data[0].LOAITIEN_ID
              }
            }
          )
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
      } catch (error) {
        this.$toast.error(error)
      }
    },
    onChangeDSChon(args) {
      // let obj = this.$refs.dsThanhToan.$refs.grid.ej2Instances
      // console.log(obj)
      let filter = this.$refs.dsThanhToan.getfilterConditions()

      // console.log(filter, args)
      if (args === '1') { // check
        if (filter.length > 0) {
          this.filterOptions = []
          filter.forEach(item => {
            if (item.fieldName !== '_CHECK') {
              this.filterOptions.push(item)
              this.$refs.dsThanhToan.clearFilterCondition(item.fieldName)
            }
          })
        }
        if (this.results.filter(x => x._CHECK === true).length > 0) {
          this.$refs.dsThanhToan.setFilterCondition({ fieldName: '_CHECK', value: true })
        } else {
          this.filterOptions = []
          this.$refs.dsThanhToan.clearFilterConditions()
        }
      } else {
        console.log(this.filterOptions)
        if (this.filterOptions.length > 0) {
          this.filterOptions.forEach(item => {
            this.$refs.dsThanhToan.setFilterCondition(item)
          })
        }
        this.$refs.dsThanhToan.clearFilterCondition('_CHECK')
      }
    }
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  watch: {
    'params.p_chungtu': function (newVal, oldVal) {
      this.params.p_gom_hd = this.params.nganhang.ma_gom + ('' + newVal).trim()
    }
  }
})
</script>
