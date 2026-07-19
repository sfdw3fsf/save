<template src="./InThuXacNhanCongNo.html"></template>
<script>

import Vue from 'vue'
import moment from 'moment'
import select2 from '@/components/Select2.vue'
import CommonsAPI from '../api/Commons'
import InThuXacNhanCongNoAPI from '../api/InThuXacNhanCongNo'
import { Query } from '@syncfusion/ej2-data'
import EventBus from '@/utils/eventBus'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import PopupSapXep from '../components/PopupSapXep/PopupSapXep.vue'
import PopupChonNgayIn from '../components/PopupChonNgayIn/PopupChonNgayIn.vue'
import {previewPrint} from "@/utils/util"
import Calculator from "@/components/Calculator";

import {
    LOAI_DV,
  } from "@/const/enums"

Vue.use(DatePickerPlugin);

export default {
  components: { appSelect2 : select2,
    PopupSapXep,
    appPopupChonNgayIn: PopupChonNgayIn,
    Calculator
  },
  name: 'TraCuuPhieuDaGiao',
  async created () {
    // init data
    this.loadDSDonVi()
    this.loadDSHTTT()
    this.loadDSLoaiKH()
    this.getDSLanTao()
  },
  destroyed () {
  },
  computed : {
    f_donvi_enabled () {
      return this.params.p_chk_donvi === '1'
    },
    f_matt_enabled () {
      return this.params.p_chk_matt === '1'
    },
    f_httt_enabled () {
      return this.params.p_chk_httt === '1'
    },
    f_nhomkh_enabled () {
      return this.params.p_chk_nhomkh === '1'
    },
    f_nvql_enabled () {
      return this.params.p_chk_nvql === '1'
    },
    f_nvtc_enabled () {
      return this.params.p_chk_nvtc === '1'
    },
    f_phanloaikh_enabled () {
      return this.params.p_chk_phanloaikh === '1'
    },
    f_loaikh_enabled () {
      return this.params.p_chk_loaikh === '1'
    },
    f_tutien_enabled () {
      return this.params.chk_tuTien === '1'
    },
    f_dentien_enabled () {
      return this.params.chk_denTien === '1'
    },
    f_loaibo_enabled () {
      return this.params.p_chk_loaibo === '1'
    },
    f_tutien2_enabled () {
      return this.params.chk_tuTien2 === '1'
    },
    f_dentien2_enabled () {
      return this.params.chk_denTien2 === '1'
    },
  },
  data () {
    return {
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/y',
          max: new Date()
        },
        fieldsDonVi: {text: 'TEN_HIENTHI', value: 'DONVI_ID' },
        fieldsHTTT: {text: 'HTTT', value: 'HTTT_ID' },
        fieldsNhomKH: {text: 'TEXT', value: 'ID' },
        fieldsNVTC: {text: 'GHEPTUYEN', value: 'TUYENTHU_ID' },
        fieldsNVQL: {text: 'TEN_NV', value: 'NHANVIEN_ID' },
        fieldsPhanLoaiKH: {text: 'TEN_LOAIKH', value: 'LOAIKH_ID' },
        fieldsLoaiKH: {text: 'TEN_LOAIKH', value: 'LOAIKH_ID' },
        fieldsLanTao: {text: 'CHUKYNO', value: 'LANTAO_ID' },
      },
      params: {
        p_check: true,
        p_kyhoadon: null,
        p_chuky_no: null,
        p_chk_donvi: '1',
        p_donvi_options: [],
        p_chk_matt: '1',
        p_ma_tt: null,
        p_chk_httt: '1',
        p_httt_options: [],
        p_lantao_options: [],
        p_lantao: null,
        p_chk_nhomkh: '1',
        p_nhomkh_options: [
          { ID: "-1", TEXT: "Tất cả"},
          { ID: "0", TEXT: "Cá nhân"},
          { ID: "1", TEXT: "Doanh nghiệp"},
        ],
        p_nhomkh: "-1",
        p_chk_nvql: '0',
        p_nvql_options: [],
        p_nvql: null,
        p_chk_nvtc: '0',
        p_nvtc_options: [],
        p_nvtc: null,
        p_chk_phanloaikh: '0',
        p_phanloaikh_options: [],
        p_phanloaikh: null,
        p_chk_loaikh: '0',
        p_loaikh_options: [],
        p_loaikh: null,
        tuTien: 0,
        chk_tuTien: '0',
        denTien: 0,
        chk_denTien: '0',
        loaiBo:[],
        p_chk_loaibo: '0',
        tuTien2: 0,
        chk_tuTien2: '0',
        denTien2: 0,
        chk_denTien2: '0',
      },
      pageinfo : {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      },
      results : [],
      totalItems: 0,
      propertiesSort : [],
      dsLoaiBo: [
        { id: 1, text: "Thuê bao đã thanh lý" },
        { id: 2, text: "Thuê bao chêm 2 hướng" },
      ],
      isCalculatorShowTuTien: false,
      isCalculatorShowDenTien: false,
      isCalculatorShowTuTien2: false,
      isCalculatorShowDenTien2: false
    }
  },
  methods : {
    loadDSDonVi: function() {
      try{
        let postData = {
          loaiDVId: LOAI_DV.PHONG_BANHANG_KV,
          donViId: null
        }
        CommonsAPI.getDanhSachDonVi2(this.axios, postData).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_donvi_options = []
                this.params.p_donvi_options.push({DONVI_ID: -1, TEN_HIENTHI: "Tất cả", MA_DV: '', TEN_DV: 'Tất cả'})

                //this.params.p_donvi_options = response.data.data
                this.params.p_donvi_options = this.params.p_donvi_options.concat(response.data.data)
                this.params.p_donvi = this.params.p_donvi_options[0].DONVI_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    loadDSHTTT: function() {
      try{
        CommonsAPI.getHinhThucThanhToan(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_httt_options = response.data.data
                this.params.p_httt = response.data.data[0].HTTT_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDSLanTao: function() {
      try{
        InThuXacNhanCongNoAPI.getDSLanTao(this.axios).then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_lantao_options = response.data.data
                this.params.p_lantao = response.data.data[0].LANTAO_ID
                //this.params.p_httt = response.data.data[0].HTTT_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    getDanhSachNVTCTheoDonVi: function(idDonVi){
      try
      {
        this.params.p_nvtc_options = []
        this.params.p_nvtc = null
        CommonsAPI.getDanhSachNVTCTheoDonVi(this.axios, idDonVi)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                //console.log(response)
                response.data.data.forEach((item) => {
                  this.params.p_nvtc_options.push( {TUYENTHU_ID: item.NHANVIEN_ID, GHEPTUYEN: item.TEN_NV})
                })
                this.params.p_nvtc = this.params.p_nvtc_options[0].TUYENTHU_ID
            }
          }
        )
      } catch (error) {
        this.$toast.error(error)
      }
    },
    validateDataSearch(){
      let errors = []

      //if(this.params.p_)
      return true
    },
    doSearch: function(){
      if(!this.validateDataSearch()){
        return false
      }
      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0


      this.fetchData()
    },
    fetchData: function(){

      let postData = {
        donViId: this.params.p_chk_donvi === '1' ? this.params.p_donvi : null,
        nhanVienQLId: this.params.p_chk_nvql === '1' ? this.params.p_nvql : null,
        lanTaoId: this.params.p_lantao,
        tuyenThuId: this.params.p_chk_nvtc === '1' ? (this.params.p_nvtc !== -1 ? this.params.p_nvtc : null) : null,
        htttId: this.params.p_chk_httt === '1' ? this.params.p_httt : null,
        nhomKHId: this.params.p_chk_nhomkh === '1' ? (this.params.p_nhomkh !== -1 ? this.params.p_nhomkh : null) : null,
        phanLoaiKHId: this.params.p_chk_phanloaikh === '1' ? this.params.p_phanloaikh : null,
        loaiKHId: this.params.p_chk_loaikh === '1' ? this.params.p_loaikh : null,
        maTT: this.params.p_chk_matt === '1' ? this.params.p_ma_tt : null,
        chiTiet: 0,
        daGan: 0,
        pageNum: this.pageinfo.page + 1,
        pageSize: this.pageinfo.maxSize,
        totalRow: 1

      }

      this.loading(true)
      InThuXacNhanCongNoAPI.getDanhSachBaoCao(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
            && response.data.data !== undefined
            && response.data.data.data !== undefined
            && response.data.data.data.length > 0) {
              this.results = response.data.data.data
              this.pageinfo.totalElement = response.data.data.totalElement
            }
        }
      )
      .catch(error => { this.$toast.error(error.data.message_detail)})
      .finally( () => { this.loading(false)})
    },
    exportExcel: function(){


      let postData = {
        donViId: this.params.p_chk_donvi === '1' ? this.params.p_donvi : null,
        nhanVienQLId: this.params.p_chk_nvql === '1' ? this.params.p_nvql : null,
        lanTaoId: this.params.p_lantao,
        tuyenThuId: this.params.p_chk_nvtc === '1' ? (this.params.p_nvtc !== -1 ? this.params.p_nvtc : null) : null,
        htttId: this.params.p_chk_httt === '1' ? this.params.p_httt : null,
        nhomKHId: this.params.p_chk_nhomkh === '1' ? (this.params.p_nhomkh !== -1 ? this.params.p_nhomkh : null) : null,
        phanLoaiKHId: this.params.p_chk_phanloaikh === '1' ? this.params.p_phanloaikh : null,
        loaiKHId: this.params.p_chk_loaikh === '1' ? this.params.p_loaikh : null,
        maTT: this.params.p_chk_matt === '1' ? this.params.p_ma_tt : null,
        chiTiet: 0,
        daGan: 0,
        pageNum: 1,
        pageSize: 1000000
      }

      this.loading(true)
      InThuXacNhanCongNoAPI.getDanhSachBaoCao(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
            && response.data.data !== undefined)
            {
              let result = response.data.data
              if(this.propertiesSort.length > 0)
              {
                result.sort(this.dynamicSortMultiple(this.propertiesSort))
              }
              let excelExportProperties = {
                  dataSource: result
              };
              this.$refs.dsXacNhanCongNo.$refs.grid.excelExport(excelExportProperties)
            } else
            {
              this.$toast.error('Không tìm thấy dữ liệu để xuất file')
            }
        }
      )
      .catch(error => { this.$toast.error(error.data.message_detail)})
      .finally( () => { this.loading(false)})

    },
    doPrint()
    {
      let selected = this.$refs.dsXacNhanCongNo.getSelectedRecords()
      if(selected.length <= 0)
      {
        this.$toast.error('Chưa chọn khách hàng để in.­')
        return false
      }
      this.$refs.refChonNgayIn.openDialog()
      //this.$toast.error('Chức năng đang được phát triển.')
    },
    printHandler(e){
      //console.log(e)
      let ngay_in = moment(e.ngay_in)
      let strDate = "ngày " + ngay_in.format("DD") + " tháng " + ngay_in.format("MM") + " năm " + ngay_in.format("YYYY")
      let selected = this.$refs.dsXacNhanCongNo.getSelectedRecords()
      let postData = {
        ds: []
      }
      selected.forEach((item) => {
        postData.ds.push({
          TEN_TTKD: item.TENTINH,
          NGAY_THANG: strDate,
          THOIGIAN_1: "6 tháng cuối năm",
          THOIGIAN_2: item.DENNGAY,
          MA_KH: item.MA_TT,
          TEN_TT: item.TEN_TT,
          DIACHI_TT: item.DIACHI_TT,
          SM_DD: item.MATB_DD,
          DTLL: item.SO_DT,
          DIACHIGBC: item.DIACHI_BC,
          TONG_TIEN: item.NO_HT,
          TONGTIEN_CHITIET: item.CT_TONGNO,
          TONGTIEN_PHAITRA: item.TONGTRA,
          NGUOINHAN_TEN: item.TEN_NVQL,
          NGUOINHAN_DONVI: item.TEN_TOBH,
          NGUOINHAN_DIACHi: "",
          NGUOINHAN_DIENTHOAI: '',
          NGUOINHAN_FAX: '',
          NGUOINHAN_EMAIL: '',
          MAVACH: item.MAVACH
        })
      })

      //await InThuXacNhanCongNoAPI.xuatFileIn(this.axios, postData)
      this.loading(true)
      try{
        const baseUrl = process.env.API;
        //let postConfig = { headers: { 'X-Requested-With': 'XMLHttpRequest' }, responseType: 'blob', } as AxiosRequestConfig
        this.axios.post(baseUrl + "/web-thuno/api/thu-no/cong-no/xuat-file-in-thu-xac-nhan-cong-no", postData, {responseType: 'blob'})
        .then( (response) => {
          previewPrint(response.data)
        })
      }catch(err)
      {
        this.$toast.error(error.data.message_detail)
      }finally{
        this.loading(false)
      }
    },
    doReorder(){
      if(this.results.length > 0)
        //this.$refs.refSapXep.openDialog()
        this.$refs.dlgSapXep.openDialog();
      else
        this.$toast.error('Chưa có dữ liệu để sắp xếp')
    },
    createData(){
      let postData = {
        donViId: this.params.p_chk_donvi === '1' ? this.params.p_donvi : null,
        nhanVienQLId: this.params.p_chk_nvql === '1' ? this.params.p_nvql : null,
        lanTaoId: this.params.p_lantao,
        tuyenThuId: this.params.p_chk_nvtc === '1' ? (this.params.p_nvtc !== -1 ? this.params.p_nvtc : null) : null,
        htttId: this.params.p_chk_httt === '1' ? this.params.p_httt : null,
        nhomKHId: this.params.p_chk_nhomkh === '1' ? (this.params.p_nhomkh !== -1 ? this.params.p_nhomkh : null) : null,
        phanLoaiKHId: this.params.p_chk_phanloaikh === '1' ? this.params.p_phanloaikh : null,
        loaiKHId: this.params.p_chk_loaikh === '1' ? this.params.p_loaikh : null,
        maTT: this.params.p_chk_matt === '1' ? this.params.p_ma_tt : null,
        chiTiet: 0,
        daGan: -2,
      }

      this.loading(true)
      InThuXacNhanCongNoAPI.getDanhSachBaoCao(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Tạo dữ liệu in thành công!')
          }
        }
      )
      .finally( () => { this.loading(false)})
    },
    /*
      Event Handler
    */
    selectedRowChanged(e){

    },
    pageChangeHandler(e) {
      //this.pageinfo.page = e
      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize
      this.fetchData();
    },
    onFilteringDonVi(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query;
      e.updateData(this.params.p_donvi_options, query);
    },
    onFilteringLanTao(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('CHUKYNO', 'contains', e.text, true) : query;
      e.updateData(this.params.p_lantao_options, query);
    },
    onFilteringHTTT(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('HTTT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_httt_options, query);
    },
    onFilteringNhomKH(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEXT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nhomkh_options, query);
    },
    onFilteringNVQL(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_NV', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nvql_options, query);
    },
    onFilteringNVTC(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEXT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_nvtc_options, query);
    },
    onFilteringPhanLoaiKH(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_LOAIKH', 'contains', e.text, true) : query;
      e.updateData(this.params.p_phanloaikh_options, query);
    },
    onFilteringLoaiKH(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEXT', 'contains', e.text, true) : query;
      e.updateData(this.params.p_loaikh_options, query);
    },
    onChangeDonVi(e){

      this.params.p_nvql_options = []
      this.params.p_nvql = null

      this.params.p_nvtc_options = []
      this.params.p_nvtc = null

      console.log(e)

      CommonsAPI.getDanhSachNhanVienQuanLyTheoDonVi(this.axios, e.value)
      .then(
        (response) =>
        {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {

                this.params.p_nvql_options = response.data.data
                this.params.p_nvql = response.data.data[0].NHANVIEN_ID
            }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })

    },
    onChangeNVQL(e){
      this.params.p_nvtc_options = []
      this.params.p_nvtc = null

      if(e === null || e.value === null || e.value === -1)
        return

      CommonsAPI.getDanhSachNhanVienThuCuocTheoQuanLy(this.axios, e.value)
      .then(
        (response) =>
        {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_nvtc_options = response.data.data
                this.params.p_nvtc = response.data.data[0].TUYENTHU_ID
            }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
    },
    onChangeNhomKH(e){
      CommonsAPI.getDanhSachLoaiKhachHang(this.axios, e.value)
      .then(
        (response) =>
        {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_phanloaikh_options = response.data.data
                this.params.p_phanloaikh = response.data.data[0].LOAIKH_ID
            }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
    },
    loadDSLoaiKH(){
      CommonsAPI.getDanhSachLoaiKhachHang(this.axios)
      .then(
        (response) =>
        {
          if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0) {
                this.params.p_loaikh_options = response.data.data
                this.params.p_loaikh = response.data.data[0].LOAIKH_ID
            }
        }
      )
      .catch(error => {
        this.$toast.error(error.data.message_detail)
      })
    },
    onChangeLanTao(e)
    {

    },
    queryCellInfo(args)
    {
      // console.log(args.column.field)

      if(args.column.field === 'STT')
      {
        let stt = this.pageinfo.page*this.pageinfo.maxSize
        //args.cell.classList.add('grid-conno');
        console.log(args)
        args.cell.textContent = stt + 1
      }
    },
    rowDataBound(e)
    {
      //console.log(e)
      let stt = this.pageinfo.page*this.pageinfo.maxSize
      var data = this.$refs.dsXacNhanCongNo.$refs.grid.ej2Instances.getRowObjectFromUID(e.row.getAttribute("data-uid"))
      //console.log(data)
      e.row.getElementsByTagName("span")[3].innerText = stt + 1 + data.index;
    },
    dynamicSortMultiple(sortCmd) {
        return function (obj1, obj2) {
            let i = 0, result = 0, numberOfProperties = sortCmd.length;
            //try getting a different result from 0 (equal)
            //as long as we have extra properties to compare
            while(result === 0 && i < numberOfProperties) {
                result = sortCmd[i].SAPXEP_ID === '1' ? obj1[sortCmd[i].FIELD].localeCompare(obj2[sortCmd[i].FIELD])
                        : obj2[sortCmd[i].FIELD].localeCompare(obj1[sortCmd[i].FIELD]);
                i++;
            }
            return result;
        }
    },
    onSortProcess(items) {
      //console.log(items)
      if (items.length > 0) {
        this.propertiesSort = items
        this.results.sort(this.dynamicSortMultiple(items))
        this.results = [...this.results]
      };
    },
    doDSLoaiBo(){

    }
  }
}

</script>
