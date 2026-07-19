<template src="./TraCuuPhieuDaGiao.html"></template>
<style scoped src="./TraCuuPhieuDaGiao.scss"></style>
<script>

import Vue from 'vue'
import moment from 'moment'
import select2 from '@/components/Select2.vue'
import breadcrumb from '@/components/breadcrumb'
import DanhMucAPI from '../api/DanhMucAPI'
import TraCuuAPI from '../api/TraCuuAPI'
import KyCuoc from '@/components/KyCuoc.vue'
import DanhSachPhieuDaGiao from './components/DanhSachPhieuDaGiao.vue'
import EventBus from '@/utils/eventBus'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { export_json_to_excel } from "../TraCuuDichVu/Export2Excel"


Vue.use(DatePickerPlugin);

export default {
  components: { breadcrumb, appSelect2 : select2, appKyCuoc : KyCuoc, appDanhSachPhieuDaGiao : DanhSachPhieuDaGiao},
  name: 'TraCuuPhieuDaGiao',
  created () {
    EventBus.$on('pageChange', this.pageChangeHandler)
    EventBus.$on('rowActive', this.rowActiveHandler)
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)

    // init data
    try{
      DanhMucAPI.getDMBuocXuLyNo(this.axios).then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0) {
              this.params.p_buocxyly_no_options = response.data.data
              this.params.p_buoc_xuly_no = this.params.p_buocxyly_no_options[0].BUOCXLN_ID
          }
        }
      )
    } catch (error) {
      this.$toast.error(error)
    }
    this.params.p_loai_nhanvien = this.params.p_loai_nhanvien_options[0].ID
    this.onLoaiNhanvienChange()
  },
  mounted () {


  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$off('pageChange', this.pageChangeHandler)
    EventBus.$off('rowActive', this.rowActiveHandler)
  },
  computed: {
    chk_group (){
      return this.params.p_chk_group
    },
    p_chuky_no: {
      get() {
        return this.params.p_chuky_no
      },
      set(value) {
        return this.params.p_chuky_no = value
      }
    },
    p_tungay_ngaygiao: {
      get() {
        return this.params.p_tungay_ngaygiao
      },
      set(value) {
        return this.params.p_tungay_ngaygiao = value
      }
    },
    p_denngay_ngaygiao: {
      get() {
        return this.params.p_denngay_ngaygiao
      },
      set(value) {
        return this.params.p_denngay_ngaygiao = value
      }
    },
    f_ngaygiao_enabled () {
      return this.params.p_chk_ngaygiao === '1'
    },
    f_lantao_disabled () {
      return this.params.p_chk_lantao !== '1'
    },
    f_loainhanvien_disabled () {
      return this.params.p_chk_loai_nhanvien !== '1'
    },
    f_nhanvien_disabled () {
      return this.params.p_chk_loai_nhanvien !== '1' || this.params.p_chk_nhanvien !== '1'
    },
    f_luotgiao_disabled () {
      return this.params.p_chk_luotgiao !== '1'
    },
    p_loai_nhanvien: {
      get () {
        return this.params.p_loai_nhanvien
      },
      set(value) {
        this.params.p_loai_nhanvien = value
      }
    },
    p_nhanvien: {
      get () {
        return this.params.p_nhanvien
      },
      set(value) {
        this.params.p_nhanvien = value
      }
    },
    p_lantao_xyly_no: {
      get () {
        return this.params.p_lantao_xyly_no
      },
      set(value) {
        this.params.p_lantao_xyly_no = value
      }
    }
  },
  data () {
    return {
      header: {
        title: 'TRA CỨU PHIẾU ĐÃ GIAO XỬ LÝ NỢ',
        list: [
          { name: 'Quản lý thu nợ', link: { name: 'Ui.cards' } },
          { name: 'Tra cứu xử lý nợ', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu đã giao',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      groupOptions: {
        columns: ["NHANVIEN_XL"]
      },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        }
      },
      gridColumns: [
        {fieldName: 'NHANVIEN_XL', headerText: 'Nhân viên xử lý', allowFiltering: true, isGroupedColumn: true},
        {fieldName: 'MA_KHACHHANG', headerText: 'Mã khách hàng', allowFiltering: true},
        {fieldName: 'MATB_DD', headerText: 'Mã đại diện', allowFiltering: true},
        {fieldName: 'TEN_KH', headerText: 'Tên khách hàng', allowFiltering: true},
        {fieldName: 'DIACHI_TT', headerText: 'Địa chỉ thanh toán', allowFiltering: true},
        {fieldName: 'DIACHI_KH', headerText: 'Địa chỉ khách hàng', allowFiltering: true},
        {fieldName: 'TONG_NO', headerText: 'Nợ hiện tại', allowFiltering: true, type:'number', format:'N0'},
        {fieldName: 'SO_CV', headerText: 'Số công văn', allowFiltering: true},
        {fieldName: 'LAN_XLN', headerText: 'Lần XLN', allowFiltering: true},
      ],
      params: {
        p_chk_group: '1',
        p_kyhoadon: null,
        p_chuky_no: null,
        p_buocxyly_no_options: [],
        p_buoc_xuly_no: null,
        p_chk_ngaygiao: '1',
        p_tungay_ngaygiao: new Date(),
        p_denngay_ngaygiao: new Date(),
        p_chk_lantao: '1',
        p_lantao_xyly_no: null,
        p_lantao_xyly_no_options: [],
        p_chk_loai_nhanvien: '1',
        p_loai_nhanvien: null,
        p_loai_nhanvien_options: [
          {"ID" : "39", "TEN" : "Nhân viên thu nợ"},
          {"ID" : "34", "TEN" : "Nhân viên pháp lý"},
          {"ID" : "25", "TEN" : "Nhân viên thu cước"}
        ],
        p_chk_nhanvien: '1',
        p_nhanvien_options: [],
        p_nhanvien: null,
        p_chk_luotgiao: '1',
        p_luotgiao: null,
        p_luotgiao_options: []
      },
      pageinfo : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      },
      results : [],
      // dsNVXL : {},
      // resultNVXL: []
    }
  },
  methods: {
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    loadBuocXuLyNo: function(buoc_xu_ly_no) {
      DanhMucAPI.getLanTaoBuocXuLyNo(this.axios, buoc_xu_ly_no)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data.length > 0) {
              this.params.p_lantao_xyly_no_options = response.data.data.data
              this.params.p_lantao_xyly_no = this.params.p_lantao_xyly_no_options.LANTAO_ID
            } else {
              this.params.p_lantao_xyly_no_options = []
              this.params.p_lantao_xyly_no = null
            }
        }
      )
      .catch(function (error){
        // this.$toast.error(error)
      })
    },
    loadNhanVienTheoLoai: function(loai_nhanvien) {
      this.params.p_nhanvien_options = []
      this.params.p_nhanvien = null
      DanhMucAPI.getNhanVienTheoLoai(this.axios, loai_nhanvien)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000') {
            let items = []
            if (response.data.data !== undefined) {
              response.data.data.forEach( function (item) {
                items.push({ id : item.NHANVIEN_ID, text : item.TEN_HIENTHI })
              })
              this.params.p_nhanvien_options = items
              this.params.p_nhanvien = this.params.p_nhanvien_options[0].NHANVIEN_ID
            }
          }
        }
      )
      .catch(function() {

      })
      .finally(function() {

      })
    },
    onBuocXuLyNoChange: function() {
      this.loadBuocXuLyNo(this.params.p_buoc_xuly_no)
    },
    onLoaiNhanvienChange: function() {
      this.loadNhanVienTheoLoai(this.params.p_loai_nhanvien)
    },
    clearResult: function() {
      this.results = []
      // this.dsNVXL = {}
      this.pageinfo.totalPages = 0
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0
    },
    validateData: function() {
      let error = false
      if(this.params.p_kyhoadon === null){
        error = true
        this.$toast.error('Vui lòng chọn kỳ hóa đơn')
      }
      if(this.params.p_buoc_xuly_no === null){
        error = true
        this.$toast.error('Vui lòng chọn bước xử lý')
      }
      if(this.params.p_chk_lantao === '1'
        && this.params.p_lantao_xyly_no === null){
          error = true
          this.$toast.error('Vui lòng chọn lần tạo')
      }
      if(this.params.p_chk_ngaygiao === '1'){
        if(this.params.p_tungay_ngaygiao === null){
          this.$toast.error('Vui lòng chọn ngày giao')
          error = true
        }
        if(this.params.p_denngay_ngaygiao === null){
          this.$toast.error('Vui lòng chọn giao đến ngày')
          error = true
        }
        if(this.params.p_tungay_ngaygiao !== null
          && this.params.p_denngay_ngaygiao !== null
          && moment(this.params.p_tungay_ngaygiao).isAfter(this.params.p_denngay_ngaygiao)){
            this.$toast.error('Ngày giao từ ngày không thể sau đến ngày')
            error = true
        }
      }
      if(this.params.p_chk_loai_nhanvien === '1'
        && this.params.p_loai_nhanvien === null){
          error = true
          this.$toast.error('Vui lòng chọn loại nhân viên')
      }
      if(this.params.p_chk_loai_nhanvien === '1'
        && this.params.p_chk_nhanvien === '1'
        && this.params.p_nhanvien === null){
          error = true
          this.$toast.error('Vui lòng chọn nhân viên')
      }
      if(this.params.p_chk_luotgiao === '1'
        && this.params.p_luotgiao === null){
          error = true
          this.$toast.error('Vui lòng chọn lượt giao')
      }
      return error
    },
    async getResult(params) {
      return await TraCuuAPI.getTraCuuXuLyNoPhieuDaGiao(this.axios, params).then( (response) => {
        return response
      }).catch(error => {
        console.log(error)
      })
    },
    getFormSearchResult: function() {

      if(this.validateData())
        return false
      let postData = {
        kyCuoc : moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        buocXLNId: this.params.p_buoc_xuly_no,
        loaiNVId: (this.params.p_chk_loai_nhanvien === '1') ? this.params.p_loai_nhanvien : null,
        tuNgay: (this.params.p_chk_ngaygiao === '1') ? moment(this.params.p_tungay_ngaygiao).format('DD/MM/YYYY') : null,
        denNgay: (this.params.p_chk_ngaygiao === '1') ? moment(this.params.p_denngay_ngaygiao).format('DD/MM/YYYY') : null,
        lanTaoId: (this.params.p_chk_lantao === '1') ? this.params.p_chk_lantao : null,
        nhanVienXLId: (this.params.p_chk_loai_nhanvien === '1' && this.params.p_chk_nhanvien === '1') ? this.params.p_nhanvien : null,
        giaoPhieuId: (this.params.p_chk_luotgiao === '1') ? this.params.p_luotgiao : null,
        page : this.pageinfo.page,
        size : this.pageinfo.maxSize
      }

      this.loading(true)

      TraCuuAPI.getTraCuuXuLyNoPhieuDaGiao(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            this.results = response.data.data.data
            this.pageinfo = {
              page : response.data.data.page + 1, // base 1
              maxSize: response.data.data.maxSize,
              totalElement: response.data.data.totalElement,
              totalPages: response.data.data.totalPages,
              sort: response.data.data.sort,
              propertiesSort: response.data.data.propertiesSort
            }
            //this.loadDsNVXL()
          } else {
            this.clearResult()
          }
        }
      )
      .catch(error => {
        console.log(error)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    loadLuotGiao: function () {
      this.loading(true)

      let postData = {
        pNhanVienGiaoId : null, //(this.params.p_chk_nhanvien === '1' && this.params.p_nhanvien !== null) ? this.params.p_nhanvien : null,
        pNhanVienNhanId : (this.params.p_chk_nhanvien === '1' && this.params.p_nhanvien !== null) ? this.params.p_nhanvien : null,
        pBuocXuLyNoId : this.params.p_buoc_xuly_no,
        pLanTaoId : this.params.p_lantao_xyly_no,
        pGiaoTuNgay : (this.params.p_chk_ngaygiao === '1' && this.params.p_tungay_ngaygiao !== null) ?  moment(this.params.p_tungay_ngaygiao).format('DD/MM/YYYY') : null,
        pGiaoDenNgay : (this.params.p_chk_ngaygiao === '1' && this.params.p_denngay_ngaygiao !== null) ? moment(this.params.p_denngay_ngaygiao).format('DD/MM/YYYY') : null,
      }
      TraCuuAPI.getDanhSachLuotGiao(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.params.p_luotgiao_options = response.data.data
          } else {
            this.params.p_luotgiao_options = []
          }
        }
      )
      .catch(error => {
        this.params.p_luotgiao_options = []
        console.log(error)
      })
      .finally(() => {
        this.loading(false)
      })
    } ,
    /*
    loadDsNVXL: function() {
      this.dsNVXL = {}
      for(let i = 0; i < this.results.length; i++) {
        if(!this.dsNVXL.hasOwnProperty(this.results[i].NHANVIEN_XL)) {
          this.dsNVXL[this.results[i].NHANVIEN_XL] = []
        }
        this.dsNVXL[this.results[i].NHANVIEN_XL].push(this.results[i])
      }
      // console.log(this.dsNVXL)
    },
    */
    exportExcel: function() {
      let postData = {
        kyCuoc : moment(this.params.p_kyhoadon).format('YYYYMM') + this.params.p_chuky_no,
        buocXLNId: this.params.p_buoc_xuly_no,
        loaiNVId: (this.params.p_chk_loai_nhanvien === '1') ? this.params.p_loai_nhanvien : null,
        tuNgay: (this.params.p_chk_ngaygiao === '1') ? moment(this.params.p_tungay_ngaygiao).format('DD/MM/YYYY') : null,
        denNgay: (this.params.p_chk_ngaygiao === '1') ? moment(this.params.p_denngay_ngaygiao).format('DD/MM/YYYY') : null,
        lanTaoId: (this.params.p_chk_lantao === '1') ? this.params.p_chk_lantao : null,
        nhanVienXLId: (this.params.p_chk_loai_nhanvien === '1' && this.params.p_chk_nhanvien === '1') ? this.params.p_nhanvien : null,
        giaoPhieuId: (this.params.p_chk_luotgiao === '1') ? this.params.p_luotgiao : null,
        page : 0,
        size : 1000
      }
      let ds = []
      this.loading(true)
      this.getResult(postData)
      .then((response) => {
        if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            ds = response.data.data.data
          }
        //let excelExportProperties = {
            //dataSource: ds
        //};
        //this.$refs.dsPhieuDaGiao.excelExport(excelExportProperties)
        const tHeader = this.gridColumns.map((item) => item.headerText);
        const filterVal = this.gridColumns.map((item) => item.fieldName);
        const data = this.formatJson(filterVal, ds);
        export_json_to_excel({
          header: tHeader,
          data,
          filename: `DanhSachCuocPhatSinh`,
          sheetname: "export",
          bookType: "xlsx",
        });
      })
      .catch(error => {
        console.log(error)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    /*
      Event Handler
    */
    chukycuocChangeHandler(e) {
      this.params.p_kyhoadon = e.p_kyhoadon
      this.params.p_chuky_no = e.p_chuky_no
    },
    pageChangeHandler(e) {
      //this.pageinfo.page = e
      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize
      this.getFormSearchResult()
    },
    rowActiveHandler(e) {
      try{
        let index = parseInt(e)
        this.pageinfo.currentRow = index
      }catch (err) {

      }
    },

  },
  watch: {
    p_nhanvien : {
      handler: function(value) {
        this.loadLuotGiao()
      }
    },
    p_lantao_xyly_no : {
      handler: function(value) {
        this.loadLuotGiao()
      }
    },
    chk_group : {
      handler (newVal) {
        if(newVal === '0')
          this.$refs.dsPhieuDaGiao.$refs.grid.ungroupColumn('NHANVIEN_XL')
        else
          this.$refs.dsPhieuDaGiao.$refs.grid.groupColumn('NHANVIEN_XL')
      }
    },
  }
}
</script>
