<template src='./XacNhanYeuCauMoKhoa.html'></template>

<script>
import Vue from 'vue'
import { mapActions, mapGetters } from 'vuex'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import YeuCauKhoaMoAPI from '../api/XacNhanYeuCauMoKhoa'
import { Query } from '@syncfusion/ej2-data'

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

import {
    DichVuVienThong
  } from "@/const/enums"

export default Vue.extend({
  components : { appKyCuoc: KyCuoc},
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)

    if (this.danhSachDichVuVienThongGetter === undefined
      || this.danhSachDichVuVienThongGetter.length === 0) {
          await this.getDanhSachDichVuVienThong();
      }
      this.danhSachDichVuVienThongGetter.forEach(item => {
        this.params.p_dichvu_options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
      });
      this.params.p_dichvu = DichVuVienThong.CO_DINH
  },

  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  computed : {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    f_tungay_enabled () {
      return this.params.p_chk_tungay === '1'
    },
    f_denngay_enabled () {
      return this.params.p_chk_denngay === '1'
    },
    p_tungay :{
       get() {
        return this.params.p_tungay
      },
      set(value) {
        return this.params.p_tungay = value
      }
    },
    p_denngay :{
       get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    },
    f_ngaythuchien_enabled () {
      return this.params.p_chk_ngaythuchien === '1'
    },
    p_ngaythuchien :{
       get() {
        return this.params.p_ngaythuchien
      },
      set(value) {
        return this.params.p_ngaythuchien = value
      }
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
        fieldsKieuYC: {text: 'text', value: 'id' },
      },
      f_show_hoanthanh: false,
      params : {

        p_kycuoc : null,
        p_somay_acc : null,
        p_dichvu_options: [],
        p_dichvu: null,
        p_kieuyc_options : [ { id : 0, text : "Mở chêm thuê bao"}, { id : 1, text : "Chêm thuê bao"} ],
        p_kieuyc: 0,
        p_ten_tb: null,
        p_yeucau: null,
        p_chk_tungay: '1',
        p_tungay: new Date(),
        p_chk_denngay: '0',
        p_denngay: new Date(),
        p_chk_ngaythuchien: '0',
        p_ngaythuchien: new Date(),
        p_xacnhan: null,
        p_yeucau_id : null
      },
      results : [],
      pageinfo: {
        page: 0,
        pageSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null
      }
    }
  },
  methods : {
    ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),
    clearData(){
      this.params.p_thuebao_id = null
      this.params.p_somay_acc = null
      this.params.p_dichvu = null
      this.params.p_ten_tb = null
      this.params.p_ghichu = null
      this.params.p_yeucau_id = null
      this.params.p_yeucau = null
      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0

    },
    validateDataSearch : function() {
      let errors = []
      if(this.params.p_chk_tungay === '1'
        && this.params.p_tungay === null)
        errors.push('Trường từ ngày bắt buộc nhập.')
      if(this.params.p_chk_denngay === '1'
        && this.params.p_denngay === null)
        errors.push('Trường đến ngày bắt buộc nhập.')
      if(this.params.p_kieuyc === null)
        errors.push('Trường kiểu yêu cầu bắt buộc nhập.')

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    validateDataInsert : function (){
      let errors = []

      if(this.params.p_yeucau_id === null)
        errors.push('Vui lòng chọn yêu cầu để cập nhật.')

      if(this.params.p_chk_ngaythuchien === '1' && this.params.p_ngaythuchien === null)
        errors.push('Vui lòng chọn ngày thực hiện.')
      if(this.params.p_xacnhan === null
        || this.params.p_xacnhan.trim() === '')
        errors.push('Trường nội dung xác nhận bắt buộc nhập.')

      if(errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doSearch : function() {
      if(!this.validateDataSearch())
        return false
      let postData = {
        pKieuYC : this.params.p_kieuyc,
        pMaTB : this.params.p_somay_acc === null || this.params.p_somay_acc.trim() === '' ? null : this.params.p_somay_acc.trim(),
        pTuNgay : (this.params.p_chk_tungay === '1') ? moment(this.params.p_tungay).format('DD/MM/YYYY') : null,
        pDenNgay : (this.params.p_chk_denngay === '1') ? moment(this.params.p_denngay).format('DD/MM/YYYY') : null,
        pageNo: this.pageinfo.page + 1,
        pageSize: this.pageinfo.pageSize,
        totalRow: 1
      }

      this.loading(true)

      this.results = []
      this.pageinfo.page = 0
      this.pageinfo.totalElement = 0

      YeuCauKhoaMoAPI.getDSYeuCauTheoNgay(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            this.results = response.data.data.data
            this.pageinfo.totalElement = response.data.data.data.length

            if(this.p_yeucau_id !== null)
            {
              let index = this.$refs.gridDSYeuCau.$refs.grid.getRowIndexByPrimaryKey(this.p_yeucau_id)
              if(index >= 0)
                this.$refs.gridDSYeuCau.setCurrentSelectedRow(index)
            }

            //this.loadDsNVXL()
          } else
          {

          }
        }
      )
      .catch(error => {
        console.log(error)
        this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })

    },
    confirmInsert(){
      let postData = {
        pYeuCauId : this.params.p_yeucau_id,
        pNgayTH : this.params.p_chk_ngaythuchien === '1' ? moment(this.params.p_ngaythuchien).format('DD/MM/YYYY') : null,
        pNoiDungXN : this.params.p_xacnhan.trim()
      }
      this.loading(true)
      YeuCauKhoaMoAPI.updateThongTinYeuCau(this.axios, postData)
        .then(
          (response) => {
            //console.log(response)
            if(response.data.error_code === 'BSS-00000000')
            {
              this.$toast.success('Xác nhận yêu cầu thành công')
              // this.clearData()
              this.doSearch()
            }
            else
              this.$toast.error(response.data.message)
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          DialogObj.hide()
          this.loading(false)
        })
    },
    doInsert : function() {
      if(!this.validateDataInsert())
        return false

      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:30px'>Bạn thật sự muốn cập nhật dữ liệu không ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmInsert },
          cancelButton: {  text: 'Không đồng ý'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
          });

    },
    confirmFinish(){
      let postData = {
        pYeuCauId : this.params.p_yeucau_id,
        pTrangThai :0,
        pNguoiTHId : this.$auth.getNguoiDungID()
      }
      this.loading(true)
      YeuCauKhoaMoAPI.updateHoanThanhThongTinYeuCau(this.axios, postData).then(
          (response) => {
            //console.log(response)
            if(response.data.error_code === 'BSS-00000000')
            {
              this.$toast.success('Yêu cầu đã cập nhật hoàn thành.')
              // this.clearData()
              this.doSearch()
            }
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          DialogObj.hide()
          this.loading(false)
        })
    },
    doFinish : function() {

      if(this.params.p_yeucau_id === null) {
        this.$toast.error('Vui lòng chọn yêu cầu để hoàn thành dữ liệu.')
        return false
      }

      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:30px'>Bạn thật sự muốn cập nhật dữ liệu không ?</div>",
          okButton: {  text: ' Đồng ý ', click: this.confirmFinish },
          cancelButton: {  text: 'Không đồng ý'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
          });

    },
    exportExcel : function() {
      if(this.results.length <= 0)
      {
        this.$toast.error('Không có dữ liệu để xuất file.')
        return false
      }
      this.$refs.appDSThueBao.exportExcel(this.results)
    },
    /* Event Handler */
    chukycuocChangeHandler(e) {
      if(e.p_kyhoadon !== null)
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + '01'
      else
        this.params.p_kycuoc = null
    },
    selectedRowChanged(e) {
      if(e === null)
      {
        this.params.p_thuebao_id = null
        this.params.p_somay_acc = null
        this.params.p_dichvu = null
        this.params.p_ten_tb = null
        this.params.p_ghichu = null
        this.params.p_yeucau_id = null
        this.params.p_yeucau = null
        return
      }


      this.params.p_thuebao_id = e.THUEBAO_ID
      this.params.p_somay_acc = e.MA_TB
      this.params.p_dichvu = e.DICHVUVT_ID
      this.params.p_ten_tb = e.TEN_TB
      this.params.p_ghichu = e.NOIDUNG_YC
      this.params.p_yeucau_id = e.YEUCAU_ID
      this.params.p_yeucau = e.NOIDUNG_YC

    },
    pageChanged(e){

      if(this.pageinfo.page === e.pageIndex && this.pageinfo.pageSize === e.pageSize)
        return
      console.log(e)
      this.pageinfo.page = e.pageIndex
      this.pageinfo.pageSize = e.pageSize
      this.doSearch()
    },
    onFilteringKieuYC(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('text', 'contains', e.text, true) : query;
      e.updateData(this.params.p_kieuyc_options, query);
    },
    onKieuYCOnChange(args)
    {
      this.doSearch()
    }
  }
})
</script>
