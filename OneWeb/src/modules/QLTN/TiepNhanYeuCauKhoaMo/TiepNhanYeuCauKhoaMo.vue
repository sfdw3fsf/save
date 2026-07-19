<template src='./TiepNhanYeuCauKhoaMo.html'></template>
<style scoped src="./TiepNhanYeuCauKhoaMo.scss"></style>

<script>
import Vue from 'vue'
import moment from 'moment'
import KyCuoc from '@/components/KyCuoc'
import EventBus from '@/utils/eventBus'
import ThongTinThueBao from './components/ThongTinThueBao'
import DanhSachThueBao from './components/DanhSachThueBao'
import CommonsAPI from '../api/Commons'
import TraCuuAPI from '../api/TiepNhanYeuCauKhoaMo'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { DialogUtility } from '@syncfusion/ej2-popups'
import { Query } from '@syncfusion/ej2-data'

//import SearchAccount from '@/modules/search/subscriber/SearchAccount'

import {
    LOAI_DV
  } from "@/const/enums"

Vue.use(DatePickerPlugin)

Vue.use(DialogUtility)
let DialogObj = undefined

export default {
  components: { appKyCuoc : KyCuoc, appThongTinThueBao: ThongTinThueBao, appDanhSachThueBao: DanhSachThueBao},
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    // init dsnh sách đơn vị

    try{
      CommonsAPI.getDanhSachDonViTheoLoai(this.axios, LOAI_DV.XULY_YCKM).then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0) {
              this.params.p_donvi_options = response.data.data
              this.params.p_donvi = response.data.data[0].DONVI_ID
          }
        }
      )
    } catch (error) {

    }

    // this.getTTThueBao()
    this.loading(true)
    await this.getDSYeuCau()
    this.loading(false)
  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  mounted () {
    //this.$refs.btnDelete.classList.add('disabled')
  },
  computed : {
    p_ngay_yc: {
      get() {
        return this.params.p_ngay_yc
      },
      set(value) {
        return this.params.p_ngay_yc = value
      }
    },
    is_added () {
      return this.isAdd
    }
  },
  data () {
    return {
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentEvents: {
        'form-close':'popupClosed',
        'acceptChangeCTV':'popupClosed',
        'acceptChangeNGT':'popupClosed',
      },
      config: {
        dateConfig: {
          value: new Date(),
          format: 'dd/MM/yyyy',
          max: new Date()
        },
        fieldsDonVi : {text: 'TEN_DV', value: 'DONVI_ID' },
      },
      params: {
        p_kycuoc: null,
        p_ma_tb: null,
        p_ma_tt: null,
        p_yeucau_id: null,
        p_thuebao_id: null,
        p_trangthai_tb: '',
        p_trangthaitb_id: null,
        p_kieu_yc_options : [{ID: 0, TEXT : 'Mở chêm thuê bao'}, {ID: 1, TEXT : 'Chêm thuê bao'}],
        p_kieu_yc: 0,
        p_ngay_yc: new Date(),
        p_ten_tt: null,
        p_diachi_tt: null,
        p_diachi_lapdat: null,
        p_donvi: null,
        p_donvi_options: [],
        p_noidung_yc: null,
        p_thanhtoan_id: null,
        p_dichvuvt_id : null,
        p_loaitb_id : null
      },
      resultTTThueBao: [],
      totalItemsTTThueBao: 0,
      pageTTThueBao: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      resultDSThueBao: [],
      //totalItemsDSThueBao: 0,
      pageDSThueBao: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: -1
      },
      isAdd : false
    }
  },
  methods : {
    validateAddData (){
      let error = []

      if(this.params.p_thuebao_id === null) {
        error.push('Không tìm thấy thông tin thuê bao.')
      }
      if(this.params.p_ma_tb === null) {
        error.push('Trường Mã thuê bao bắt buộc nhập.')
      }

      if(this.params.p_ngay_yc === null)
        error.push('Trường Ngày yêu cầu bắt buộc nhập.')
      else if(!moment(this.params.p_ngay_yc, 'DD/MM/YYYY').isValid())
        error.push('Trường Ngày yêu cầu không đúng định dạng DD/MM/YYYY.')

      if(this.params.p_kieu_yc === undefined || this.params.p_kieu_yc === null)
        error.push('Trường Kiểu yêu cầu bắt buộc nhập.')
      if(this.params.p_donvi === null)
        error.push('Trường Đơn vị bắt buộc nhập.')
      if(this.params.p_noidung_yc === undefined
        || this.params.p_noidung_yc === null
        || this.params.p_noidung_yc === ''
        || this.params.p_noidung_yc.trim() === '')
        error.push('Trường Nội dung yêu cầu bắt buộc nhập')

      if(error.length > 0)
      {
        this.$toast.error(error.join('\r\n'))
        return true
      }
      return false
    },
    doSearch () {
      this.isAdd = false
      this.clearData()
      //this.$refs.btnDelete.classList.add('disabled')

      this.resultDSThueBao = []
      this.pageDSThueBao.page = 0
      this.pageDSThueBao.totalElement = 0

      this.resultTTThueBao = []
      this.pageTTThueBao.totalElement = 0
      this.pageTTThueBao.page = 0

      this.loading(true)

      this.getDSYeuCau()

      this.loading(false)
    },
    getDBThueBao (){
      if(this.params.p_ma_tb === null || this.params.p_ma_tb.trim() === '')
      {
        this.$toast.error('Hãy nhập thông tin thuê bao')
        this.$refs.p_ma_tb.focus()
        return false
      }

      this.getDanhBaTBTheoMa()

    },
    doAdd () {

      this.clearData()
      this.resultTTThueBao = []
      this.pageTTThueBao.page = 0
      this.pageTTThueBao.totalElement = 0


      this.isAdd = true
    },
    doCancel () {
      this.isAdd = false
      this.clearData()
    },
    doSave () {
      if(this.isAdd) // thêm mới
      {
        if(!this.validateAddData()) {
          let postData = {
            pThueBaoId : this.params.p_thuebao_id,
            pNoiDungYC : this.params.p_noidung_yc.trim(),
            pNgayYC : moment(this.params.p_ngay_yc).format('DD/MM/YYYY'),
            pKieuYC : this.params.p_kieu_yc,
            pDonViId : this.params.p_donvi,
            pMaND : this.$auth.getUserName(),
            pMayCN : '-'
          }
          try{
              TraCuuAPI.addYeuCauKhoaMo(this.axios, postData).then(
                (response) => {
                  //console.log(response)
                  if(response.data.error_code === 'BSS-00000000')
                  {
                    this.$toast.success('Nhập mới yêu cầu thành công')
                    this.clearData()
                    this.doSearch()
                  }
                  else
                    this.$toast.error(response.data.message)
                }
              )
            } catch (error) {
              this.$toast.error(error)
            }

        }
      } else {
        let primaryKey = this.params.p_yeucau_id
        let postData = {
            pPhanVungId : this.$auth.getPhanVungID(),
            pYeuCauId : this.params.p_yeucau_id,
            pThueBaoId : this.params.p_thuebao_id,
            pNoiDungYC : this.params.p_noidung_yc !== null ? this.params.p_noidung_yc.trim() : null,
            pNgayYC : moment(this.params.p_ngay_yc).format('DD/MM/YYYY'),
            pKieuYC : this.params.p_kieu_yc,
            pDonViId : this.params.p_donvi,
            pMaND : this.$auth.getUserName(),
            pMayCN : '-'
          }
          try{
              TraCuuAPI.updateYeuCauKhoaMo(this.axios, postData).then(
                (response) => {
                  //console.log(response)
                  if(response.data.error_code === 'BSS-00000000')
                  {
                    this.$toast.success('Cập nhật yêu cầu thành công')
                    this.clearData()
                    this.doSearch()
                    let index = this.$refs.gridDSYeuCau.$refs.grid.getRowIndexByPrimaryKey(primaryKey)
                    //console.log(index)
                    if(index >= 0)
                      //this.$refs.gridDSYeuCau.$refs.grid.selectRow(index)
                      this.pageDSThueBao.currentRow = index
                  }
                  else
                    this.$toast.error(response.data.message)
                }
              )
            } catch (error) {
              this.$toast.error(error)
            }

      }
    },
    doDelete () {
      if(this.params.p_yeucau_id === null){
        this.$toast.error('Không tìm thấy Thuê bao ID cần xóa. Vui lòng thử lại.')
        return
      }
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có thật sự muốn xóa yêu cầu này không ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete },
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
      });


    },
    confirmDelete() {
      try{
        TraCuuAPI.deleteYeuCauKhoaMo(this.axios, {pYeuCauId : this.params.p_yeucau_id}).then(
          (response) => {
            //console.log(response)
            if(response.data.error_code === 'BSS-00000000')
            {

              this.$toast.success('Xóa yêu cầu thành công')
              this.clearData()
              this.doSearch()
            }
            else
              this.$toast.error(response.data.message)
          }
        )
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally( () => {
          DialogObj.hide()
        })
      } catch (error) {
        this.$toast.error(error)
      }
    },
    doExportExcel () {
      if(this.resultDSThueBao.length <= 0) {

        this.$toast.warning('Không tìm thấy dữ liệu để xuất file excel.')
        return
      }
      let excelExportProperties = {
        dataSource: this.resultDSThueBao,
        fileName: 'DanhSachThueBaoYeuCau.xlsx'
      };
      this.$refs.gridDSYeuCau.excelExport(excelExportProperties)
    },
    clearData () {
      this.params.p_thanhtoan_id = null
      this.params.p_ma_tb = null
      this.params.p_trangthai_tb = null
      this.params.p_ma_tt_tb = null
      //this.params.p_kieu_yc = null
      this.params.p_ngay_yc = new Date(),
      this.params.p_ten_tt = null
      this.params.p_diachi_tt = null
      this.params.p_diachi_lapdat = null
      this.params.p_donvi = null
      this.params.p_noidung_yc = null
      this.params.p_yeucau_id = null
      this.params.p_thuebao_id = null
      this.params.p_dichvuvt_id = null
      this.params.p_loaitb_id = null
      this.params.p_trangthaitb_id = null
    },
    popupTraCuuDanhBa(){

      if(!this.isAdd)
        return
      //this.$toast.error('Chức năng thuộc phân hệ phát triển thuê bao. Hiện đang được phát triển.')

      this.popupComponent = ()=>import('@/modules/search/subscriber/SearchAccount');
      this.popupComponentName = "SearchAccount";
      // try {
      //   //this.popupComponent.data.isPopup = true;
      // } catch(ex) {
      //   console.log(ex);
      // }
      this.Popup('popupComponents');

      //this.$bvModal.show("modal-dsphieugachnovnp");
    },

    async popupClosed(val) {

      if(val !== undefined)
      {
        switch(this.popupComponentName) {
          case "SearchAccount":
            // if(val.ma_kh) this.khachhang.makh = val.ma_kh;
            if(val.ma_tb !== '') this.params.p_ma_tb = val.ma_tb;
            // if(this.khachhang.makh && this.khachhang.makh.trim()!="") this.MaKHChanged();
            this.getDBThueBao()
            break;
        }
      }
      

      this.ClosePopup('popupComponents');
    },
    getDanhBaTBTheoMa: function(){
      let postData = {
        donViDLId: null,
        maTB: this.params.p_ma_tb.trim()
      }
      this.resultTTThueBao = []
      this.pageTTThueBao.totalElement = 0
      this.pageTTThueBao.page = 0

      this.loading(true)

      TraCuuAPI.getDanhBaTBTheoMa(this.axios, postData)
      .then(
        (response) => {
          if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.length > 0)
          {
            this.setThongTinThueBao(response.data.data[0])
            this.getThongTinTBYCKhoaMay()
          } else {
            this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
            this.clearData()
          }
        }
      )
      .catch(error => {
        if(error.data !== undefined)
          this.$toast.error(error.data.message_detail)
        else
          console.log(error)
      })
      .finally(() => {this.loading(false)})
    },
    getThongTinTBYCKhoaMay : function () {
      let postData = {
        pKyCuoc : this.params.p_kycuoc,
        pThanhToanId : this.params.p_thanhtoan_id,
        pKieuYCId : 0//this.params.p_kieu_yc
      }
      TraCuuAPI.getThongTinTBYCKhoaMay(this.axios, postData)
        .then(
          (response) => {
            if (response.data.error_code === 'BSS-00000000'
                && response.data.data !== undefined
                && response.data.data.length > 0)
            {
              //console.log(response)
              this.resultTTThueBao = response.data.data
              this.pageTTThueBao.totalElement = response.data.data.length
              this.pageTTThueBao.page = 0
            } else {
              this.$toast.warning('Không có dữ liệu, vui lòng thử lại.')
              this.resultTTThueBao = []
              this.pageTTThueBao.totalElement = 0
              this.pageTTThueBao.page = 0
            }
          })
        .catch(error => {
          console.log(error)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    async getDSYeuCau() {

      try{
        let postData = {
          pKieuYC : this.params.p_kieu_yc
        }
        
        const res = await TraCuuAPI.getDanhSachYeuCauKhoaMo(this.axios, postData)
        const data = await res.data



        if (data.error_code === 'BSS-00000000'
            && data.data !== undefined
            && data.data.data !== undefined
            && data.data.data.length > 0)
        {
          this.resultDSThueBao = data.data.data
          //this.totalItemDSThueBao = response.data.data.totalElement
          this.pageDSThueBao.totalElement = data.data.data.length

        } else {
          //alert('Not found')
          //this.$toast.error('Không có dữ liệu, vui lòng thử lại.')
          this.isAdd = true
        }
        return true
      }catch(error){
        if(error.data !== undefined)
          this.$toast.error(error.data.message_detail)
          return false
      }finally{} 
      

    },
    setThongTinThueBao (data) {
      this.params.p_thanhtoan_id = data.THANHTOAN_ID
      this.params.p_ma_tb = data.MA_TB
      this.params.p_trangthai_tb = data.TRANGTHAI_TB
      this.params.p_ma_tt_tb = data.MA_TT
      this.params.p_kieu_yc = data.KIEU_YC_ID
      if(data.NGAY_YC !== undefined && data.NGAY_YC !== '')
        this.params.p_ngay_yc = moment(data.NGAY_YC, 'DD/MM/YYYY').toDate(),
      this.params.p_ten_tt = data.TEN_TT
      this.params.p_diachi_tt = data.DIACHI_TT
      this.params.p_diachi_lapdat = data.DIACHI_LD
      this.params.p_donvi = data.DONVI_ID
      this.params.p_noidung_yc = data.NOIDUNG_YC
      this.params.p_yeucau_id = data.YEUCAU_ID
      this.params.p_thuebao_id = data.THUEBAO_ID
      this.params.p_dichvuvt_id = data.DICHVUVT_ID
      this.params.p_loaitb_id = data.LOAITB_ID
      this.params.trangthaitb_id = data.TRANGTHAITB_ID
    },
    selectedRowChanged(args){
      //console.log(args)
      if(args !== null)
      {
        this.isAdd = false
        this.setThongTinThueBao(args)
        this.getThongTinTBYCKhoaMay()
      } else
      {
        this.clearData()
        this.resultTTThueBao = []
        this.pageTTThueBao.totalElement = 0
        this.pageTTThueBao.page = 0
      }
      
    },
    /* Event Handler */
    chukycuocChangeHandler(e) {
      if(e.p_kyhoadon !== null)
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + '01'
      else
        this.params.p_kycuoc = null
    },
    onFilteringDonVi(e){
      var query = new Query();
      query = (e.text !== '') ? query.where('TEN_DV', 'contains', e.text, true) : query;
      e.updateData(this.params.p_donvi_options, query);
    },
    actionComplete(e){
      console.log(e)
      if(e.requestType === 'refresh')
      {
        if(this.pageDSThueBao.currentRow >= 0)
        {
          this.$refs.gridDSYeuCau.$refs.grid.selectRow(this.pageDSThueBao.currentRow)
        }
      }
    },
    pageChangeHandler(e) {
      //this.pageinfo.page = e
      this.pageDSThueBao.page = e.pageIndex
      this.pageDSThueBao.maxSize = e.pageSize
      this.loading(true)
      this.getDSYeuCau()
      this.loading(false)
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    ttTBqueryCellInfo(args)
    {
      if(args.column.field === 'TONGNO')
      {
        args.cell.classList.add('red');
      }
    }
  }
}
</script>
