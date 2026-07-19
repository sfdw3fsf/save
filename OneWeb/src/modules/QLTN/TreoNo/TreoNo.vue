<template src="./TreoNo.html"></template>
import {numeric, required} from 'vuelidate/lib/validators'
<script>
import Vue from "vue"
import { mapActions, mapGetters } from 'vuex'
import KyCuoc from '@/components/KyCuoc'
import Calculator from '@/components/Calculator'
import EventBus from '@/utils/eventBus'
import CommonsAPI from '../api/Commons'
import TreoNoAPI from '../api/TreoNo'
import DSThueBaoTreoNo from './components/DSThueBaoTreoNo'
import moment from 'moment'
import {DichVuVienThong} from '@/const/enums'
import { DialogUtility } from '@syncfusion/ej2-popups'
import TreoNoTuFile from './components/TreoNoTuFile'
import { maxLength, numeric, required } from "vuelidate/lib/validators";
import BssRequiredMarker from "@/components/BssRequiredMarker";

Vue.use(DialogUtility)
let DialogObj = undefined

export default{
  components : {
    BssRequiredMarker,
    appKyCuoc : KyCuoc,
    appDSThueBaoTreoNo: DSThueBaoTreoNo,
    appCalculator : Calculator,
    appTreoNoTuFile: TreoNoTuFile},
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)


    if (this.danhSachDichVuVienThongGetter === undefined || this.danhSachDichVuVienThongGetter.length === 0) {
        await this.getDanhSachDichVuVienThong();
    }
    this.danhSachDichVuVienThongGetter.forEach(item => {
        this.params.p_dichvu_options.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT })
    });



  },
  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
  },
  mounted () {
    this.onSearch()
  },
  computed: {
    ...mapGetters('qltnCommon', ['danhSachDichVuVienThongGetter']),
    is_added () {
      return this.isAdd
    }
  },
  data () {
    return {
      columns: [
            {fieldName: 'DICHVUVT_ID', visible: false},
            {fieldName: 'THUEBAO_ID', visible: false, isPrimaryKey: true},
            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true},
            {fieldName: 'TIENTREO', headerText: 'Tiền treo', allowFiltering: true, format:'N0', type: 'number'},
            {fieldName: 'KYHOADON', headerText: 'Kỳ hóa đơn', allowFiltering: true},
            {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true},
            {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LĐ', allowFiltering: true},
            {fieldName: 'NOIDUNG', headerText: 'Nội dung', allowFiltering: true},
            {fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true},
            {fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true},
            {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái TB', allowFiltering: true},
            ],
      selectionSettings: {checkboxOnly: true, type: 'Multiple'},
      params : {
        p_thuebao_id: null,
        p_somay_acc: null,
        p_dichvu_options: [],
        p_dichvu: DichVuVienThong.CO_DINH,
        p_kycuoc: null,
        p_ten_thuebao: null,
        p_trangthai_thuebao: null,
        p_diachi_thuebao: null,
        p_noidung: null,
        p_ghichu: null,
        p_tientreo: 0
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
      resultDSTreoNo:[],
      isCalculatorShow: false,
      isAdd: false
    }
  },
  methods : {
    ...mapActions('qltnCommon', ['getDanhSachDichVuVienThong']),
    async getDSTreoNo(params) {
      return await TreoNoAPI.getDSTreoNo(this.axios, params).then( (response) => {
        return response
      }).catch(error => {
        this.$toast.error('Thông báo lỗi: ' + error.data.message_detail)
      })
    },
    resetResult: function(){
      this.resultDSTreoNo = []
      this.pageinfo = {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0
      }
    },
    validateAddData (){
      this.$v.$touch();
      let errors = []
      if(this.params.p_somay_acc === null) {
        errors.push('Trường Mã thuê bao bắt buộc nhập.')
      }
      if(this.params.p_thuebao_id === null)
        errors.push('Không tìm thấy thông tin thuê bao.')

      if(this.params.p_tientreo === 0)
        errors.push('Bạn phải nhập tiền treo nợ > 0')

      if(this.params.p_kycuoc === null)
        errors.push('Trường Chu kỳ hóa đơn bắt buộc nhập.')

      if(this.params.p_noidung === null || this.params.p_noidung === '' || this.params.p_noidung.trim() === '')
        errors.push('Trường Nội dung bắt buộc nhập.')

      if(this.params.p_ghichu === null || this.params.p_ghichu === '' || this.params.p_ghichu.trim() === '')
        errors.push('Trường Ghi chú bắt buộc nhập.')

      if(errors.length > 0)
      {
        this.$root.toastError(errors.join('\r\n'))
        return true
      }
      return false
    },
    validateEditData (){
      let errors = []

      if(this.params.p_thuebao_id === null)
        errors.push('Không tìm thấy thông tin thuê bao.')

      if(this.params.p_tientreo === 0)
        errors.push('Bạn phải nhập tiền treo nợ > 0')

      if(this.params.p_kycuoc === null)
        errors.push('Trường Chu kỳ hóa đơn bắt buộc nhập.')
      /*
      if(this.params.p_noidung === null)
        errors.push('Trường Nội dung bắt buộc nhập.')
      */
      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },
    fetchDSTreoNo: function(params) {
      this.loading(true)
      this.getDSTreoNo(params)
      .then((response) => {
        if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0)
          {
            this.resultDSTreoNo = response.data.data.data
            this.pageinfo = {
              page : response.data.data.page, // base 1
              maxSize: response.data.data.maxSize,
              totalElement: response.data.data.totalElement,
              totalPages: response.data.data.totalPages,
              sort: response.data.data.sort,
              propertiesSort: response.data.data.propertiesSort,
              currentRow: 0
            }
            this.isAdd = false


          } else {
            this.$toast.error('Không tìm thấy dữ liệu thỏa điều kiện.')
            this.doAdd()
          }
      })
      .finally(() => {
        this.loading(false)
      })
    },
    onSearch() {
      this.resetResult()
      let postData = {
        pageNo : this.pageinfo.page,
        pageSize : this.pageinfo.maxSize,
        totalRow : 1
      }
      this.fetchDSTreoNo(postData)

    },
    onReloadDSTreoNo(){
      let postData = {
        pageNo : this.pageinfo.page,
        pageSize : this.pageinfo.maxSize,
        totalRow : 1
      }
      this.fetchDSTreoNo(postData)
    },
    getTBTreoNo : function() {
      if(this.params.p_somay_acc === null
        || this.params.p_somay_acc.trim() === ''){
          this.$toast.error('Trường Số máy / account bắt buộc nhập.')
          return false
      }

      this.loading(true)
      TreoNoAPI.getTBTreoNo(this.axios, {pMaTB: this.params.p_somay_acc})
      .then((response) => {

        if (response.data.error_code === 'BSS-00000000'
              && response.data.data.length > 0)
          {
            /*
            this.params.p_thuebao_id = response.data.data[0].THUEBAO_ID
            this.params.p_dichvu = response.data.data[0].DICHVUVT_ID
            this.params.p_ten_thuebao = response.data.data[0].TEN_TB
            this.params.p_trangthai_thuebao = response.data.data[0].TRANGTHAI_TB
            this.params.p_diachi_thuebao = response.data.data[0].DIACHI_LD
            this.params.p_noidung = response.data.data[0].NOIDUNG
            this.params.p_ghichu = response.data.data[0].GHICHU
            this.params.p_tientreo = response.data.data[0].TIENTREO
            */
            this.$toast.error('Thuê bao ' + this.params.p_somay_acc + ' tồn tại trong danh sách.')
            let index = this.$refs.gridDSTreoNo.$refs.grid.getRowIndexByPrimaryKey(response.data.data[0].THUEBAO_ID)
            if(index >= 0)
              this.$refs.gridDSTreoNo.setCurrentSelectedRow(index)
            return
          } else {
            CommonsAPI.getDanhBaTheoMaThueBao(this.axios, {maTB : this.params.p_somay_acc})
            .then(
              (response) => {
                console.log(response)
                if (response.data.error_code === 'BSS-00000000'
                    && response.data.data !== undefined
                    && response.data.data.length > 0)
                {
                  if (response.data.data !== undefined) {

                    if(response.data.data.length == 1)  // tìm thấy 1 account
                    {
                      this.params.p_thuebao_id = response.data.data[0].THUEBAO_ID
                      this.params.p_dichvu = response.data.data[0].DICHVUVT_ID
                      this.params.p_ten_thuebao = response.data.data[0].TEN_TB
                      this.params.p_trangthai_thuebao = response.data.data[0].TRANGTHAI_TB
                      this.params.p_diachi_thuebao = response.data.data[0].DIACHI_LD
                      this.params.p_noidung = ''
                      this.params.p_ghichu = ''
                      this.params.p_tientreo = 0
                    } else
                    {
                      this.$toast.error('Tìm thấy nhiều thông tin thuê bao ' + this.params.p_somay_acc)
                    }

                  } else {


                  }
                  // this.params.p_nhanvienthu_options = response.data.data
                  // this.params.p_nhanvienthu = this.params.p_nhanvienthu_options[0]

                } else {
                  this.$toast.error('Không tìm thấy thông tin thuê bao ' + this.params.p_somay_acc)
                  this.clearFormData()
                  this.$refs.p_somay_acc.focus()
                }
              }
            )
            .catch(error => {
              console.log(error)
            })

          }
      })
      .catch(error => {
        console.log(error)
        //this.$toast.error(error.data.message_detail)
      })
      .finally(() => {
        this.loading(false)
      })
    },
    doAdd() {
      this.clearFormData()
      this.isAdd = true
      //console.log(this.$refs['p_somay_acc'])
      this.$refs.p_somay_acc.focus()
    },
    doSave() {
      if(this.isAdd) // thêm mới
      {
        if(!this.validateAddData()) {
          let postData = {
            pKyCuoc : this.params.p_kycuoc,
            pThueBaoId : this.params.p_thuebao_id,
            pTienTreo : this.params.p_tientreo,
            pNoiDung : this.params.p_noidung !== null ? this.params.p_noidung.trim() : null,
            pGhiChu : this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
            pNguoiDungId: this.$auth.getNguoiDungID(),
            pNguoiCN : this.$auth.getUserName(),
            pMayCN : '-',
            pIPCN : '-'
          }

          this.loading(true)
          TreoNoAPI.addTBTreoNo(this.axios, postData).then(
            (response) => {
              if(response.data.error_code === 'BSS-00000000')
              {
                this.$toast.success('Thêm thuê bao treo nợ thành công')
                this.$v.$reset()
                this.clearFormData()
                this.onSearch()
              }
              else
                this.$toast.error(response.data.message)
            }
          )
          .catch(error => {
            console.log(error)
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {
            this.loading(false)
          })

        }
      } else {
        let postData = {
            pKyCuoc : this.params.p_kycuoc,
            pThueBaoId : this.params.p_thuebao_id,
            pTienTreo : this.params.p_tientreo,
            pNoiDung : this.params.p_noidung !== null ? this.params.p_noidung.trim() : null,
            pGhiChu : this.params.p_ghichu !== null ? this.params.p_ghichu.trim() : null,
            pNguoiDungId: this.$auth.getNguoiDungID(),
            pNguoiCN : this.$auth.getUserName(),
            pMayCN : '-',
            pIPCN : '-'
          }
        if(this.validateEditData())
        {
          this.loading(true)
          TreoNoAPI.updateTBTreoNo(this.axios, postData).then(
            (response) => {
              if(response.data.error_code === 'BSS-00000000')
              {
                this.$toast.success('Cập nhật thuê bao treo nợ thành công')
                this.clearFormData()
                this.pageinfo.page = 0
                this.onReloadDSTreoNo()
              }
              else
                this.$toast.error(response.data.message)
            }
          )
          .catch(error => {
            this.$toast.error(error.data.message_detail)
          })
          .finally(() => {
            this.loading(false)
          })
        }
      }
    },
    doCancel() {
      this.$v.$reset();
      this.clearFormData()
      this.isAdd = false
    },
    confirmDelete: function(){
      let selected = this.$refs.gridDSTreoNo.getSelectedRecords()
      let arrKeys = []
      selected.forEach((item) => {
        arrKeys.push(item.THUEBAO_ID)
      })
      let postData = {
        pDSThueBao : arrKeys.join(',')
      }
      try{
          TreoNoAPI.deleteTBTreoNo(this.axios, postData).then(
            (response) => {
              //console.log(response)
              if(response.data.error_code === 'BSS-00000000')
              {
                DialogObj.hide()
                this.$toast.success('Xóa yêu cầu thành công')
                this.clearFormData()
                this.onReloadDSTreoNo()
              }
              else
                this.$toast.error(response.data.message)
            }
          )
        } catch (error) {
          this.$toast.error(error)
      }
    },
    doDelete() {

      let selected = this.$refs.gridDSTreoNo.getSelectedRecords()

      if(selected === null || selected.length <= 0){
        this.$toast.error('Bạn chưa chọn thuê bao cần xóa.')
        return
      }



      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn có thật sự muốn xóa " + selected.length + " thuê bao đang được chọn không ?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete },
          cancelButton: {  text: 'Hủy'},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
      });

    },
    clearFormData () {

      this.params.p_thuebao_id = null
      this.params.p_somay_acc = null
      this.params.p_dichvu = DichVuVienThong.CO_DINH,
      this.params.p_ten_thuebao = null
      this.params.p_trangthai_thuebao = null
      this.params.p_diachi_thuebao = null
      this.params.p_noidung = null
      this.params.p_ghichu = null
      this.params.p_tientreo = 0
    },
    readFile() {
      this.$refs.refTreoNoTuFile.openDialog()
    },
    exportExcel() {
      let postData = {
        pPhanVungId : this.$auth.getPhanVungID(),
        pageNo : 0,
        pageSize : 100000
      }

      this.loading(true)
      this.getDSTreoNo(postData)
      .then((response) => {
        if (response.data.error_code === 'BSS-00000000'
              && response.data.data !== undefined
              && response.data.data.data !== undefined
              && response.data.data.data.length > 0)
        {
          let excelExportProperties = {
              dataSource: response.data.data.data
          };
          this.$refs.gridDSTreoNo.excelExport(excelExportProperties)
        } else {
          this.$toast.error('Không tìm thấy dữ liệu để xuất excel.')
        }
      })
      .catch(error => {

      })
      .finally(() => {
        this.loading(false)
      })
    },
    /* Event Handler */
    chukycuocChangeHandler(e) {
      if(e.p_kyhoadon !== null)
        this.params.p_kycuoc = moment(e.p_kyhoadon).format('YYYYMM') + '01'
      else
        this.params.p_kycuoc = null
    },
    pageChangeHandler(e) {
      //this.pageinfo.page = e
      this.pageinfo.page = e.pageIndex
      this.pageinfo.maxSize = e.pageSize
      this.onReloadDSTreoNo()
    },
    grid_DataBound(){
      //console.log(this.$refs.gridDSTreoNo.ej2Instances)
      for (var i = 0; i < this.$refs.gridDSTreoNo.ej2Instances.columns.length; i++) {
        console.log(this.$refs.gridDSTreoNo.ej2Instances.columns[i].field)
         if(this.$refs.gridDSTreoNo.ej2Instances.columns[i].field === "TIENTREO"){
            this.$refs.gridDSTreoNo.ej2Instances.columns[i].type="number";
            this.$refs.gridDSTreoNo.ej2Instances.columns[i].format="N0";
        }
      }
       this.$refs.gridDSTreoNo.ej2Instances.refreshColumns();
    },
    selectedRowChanged(e) {

      console.log(e)

      try{
        let dataRow = e

        this.params.p_thuebao_id = dataRow.THUEBAO_ID
        this.params.p_somay_acc = dataRow.MA_TB
        this.params.p_dichvu = dataRow.DICHVUVT_ID
        this.params.p_ten_thuebao = dataRow.TEN_TB
        this.params.p_trangthai_thuebao = dataRow.TRANGTHAI_TB
        this.params.p_diachi_thuebao = dataRow.DIACHI_LD
        this.params.p_noidung = dataRow.NOIDUNG
        this.params.p_ghichu = dataRow.GHICHU
        this.params.p_tientreo = dataRow.TIENTREO
        this.pageinfo.currentRow = index
      }catch (err) {

      }

      /*

      */
      this.isAdd = false
    },

    selectedRowChanged(args)
    {

    },

    recordClickHandler(args)
    {
      console.log(args)
      let e = args.rowData

      try{
        let dataRow = e

        this.params.p_thuebao_id = dataRow.THUEBAO_ID
        this.params.p_somay_acc = dataRow.MA_TB
        this.params.p_dichvu = dataRow.DICHVUVT_ID
        this.params.p_ten_thuebao = dataRow.TEN_TB
        this.params.p_trangthai_thuebao = dataRow.TRANGTHAI_TB
        this.params.p_diachi_thuebao = dataRow.DIACHI_LD
        this.params.p_noidung = dataRow.NOIDUNG
        this.params.p_ghichu = dataRow.GHICHU
        this.params.p_tientreo = dataRow.TIENTREO
        this.pageinfo.currentRow = args.rowIndex

        this.isAdd = false
        //alert(this.isAdd)
      }catch (err) {

      }
    },

    actionComplete(args)
    {
      //console.log(args)
      if(args.requestType === 'refresh')
      {
        if(this.resultDSTreoNo.length > 0)
        {
          let dataRow = this.resultDSTreoNo[0]

          this.params.p_thuebao_id = dataRow.THUEBAO_ID
          this.params.p_somay_acc = dataRow.MA_TB
          this.params.p_dichvu = dataRow.DICHVUVT_ID
          this.params.p_ten_thuebao = dataRow.TEN_TB
          this.params.p_trangthai_thuebao = dataRow.TRANGTHAI_TB
          this.params.p_diachi_thuebao = dataRow.DIACHI_LD
          this.params.p_noidung = dataRow.NOIDUNG
          this.params.p_ghichu = dataRow.GHICHU
          this.params.p_tientreo = dataRow.TIENTREO
          this.pageinfo.currentRow = 0
        }

      }
    },

    readFileHandler(e)
    {
      if(e !== null && e)
      {
        this.onSearch()
      }
    }

  },
  validations: {
      params: {
        p_somay_acc: { required },
        p_tientreo: {
          required,
          maxlength: maxLength(10),
          numeric,
          minValue: 1
        },
        p_noidung: {
          required,
          maxlength: maxLength(90),
        },
        p_ghichu: {
          required,
          maxlength: maxLength(90),
        }
      }

      /*cboHangSx: { required },
      cboLoaiTbi: { required },
      cboNhomcard: { required },
      soportInput: {
        required,
        maxlength: maxLength(4),
        numeric,
      },
      portBdInput: {
        required,
        maxlength: maxLength(1),
        numeric,
      },
      */
    },
}
</script>
