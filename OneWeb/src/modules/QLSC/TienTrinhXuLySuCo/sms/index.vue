<template src="./index.html"></template>
<script>
import {required} from "vuelidate/lib/validators";
import { MultiSelectComponent } from "@syncfusion/ej2-vue-dropdowns";

export default {
  provide: {
    multiselect: [MultiSelectComponent]
  },
  name: "sms",
  props: {
    //modalShowSMS: Boolean,
    TTObject: {
      type: Object,
      required: true
    },
    id: String
  },
  validations: {
    sms: {
      required
    }
  },
  data: function (){
    return {
      dsSdt: [],
      dsNhanVienNhanTin:[],
      dsSmsDaGui :[],
      cboSdt:[],
      noiDungInput:'',
      maxLengthNoiDung:0,

      boxMode:'Box',
      waterMarkSms:'Số điện thoại',
      fieldMultiSelect:{value:'SO_DT', text: 'SO_DT'},
      isShown: false,
      key: 1
    }
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        await this.getDsSdt()
        if (this.TTObject.SUCO_ID) await this.getDsNhienVienNhanTin(this.TTObject.SUCO_ID)
        if (this.TTObject.TIENTRINHSC_ID) await this.getDsSmsDaGui(this.TTObject.TIENTRINHSC_ID)
        //khoi tao maxLengthNoiDung
        this.maxLengthNoiDung = 160 - ("SC TTSC " + this.TTObject.MA_SC.length + " " + this.$auth.getUserName().length + ": ").length
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async btnGuiSms(){
      if (this.cboSdt.length === 0){
        this.$root.toastError("Bạn chưa chọn số điện thoại")
        return
      }
      let sdtStr = this.cboSdt.toString()
      sdtStr = sdtStr.replace(",",";")
      const regex = new RegExp('^((?:09[14]|088|012[34579])\\d{7})(;(?:09[14]|088|012[34579])\\d{7})*$'); //regex sdt vinaphone
      if (!regex.test(sdtStr)){
        this.$root.toastError("Danh sách số điện thoại nhập vào không đúng")
        return
      }
      if (this.noiDungInput.length === 0){
        this.$root.toastError("Bạn chưa nhập nội dung tin nhắn")
        return
      }
      try {
        this.loading(true)
        let object = new Object()
        object.tientrinhscId = this.TTObject.TIENTRINHSC_ID
        object.dsSodt = sdtStr
        object.maSc = this.TTObject.MA_SC
        object.noidung = this.noiDungInput
        await this.$root.context.post("/web-suco/TienTrinhSuCo/tientrinh-sms", object)
        .then(res =>{
          if (res.error === "200"){
            let check = res.data.filter(item => item.status === 1)
            if (check.length > 0){
              this.$toast.success("Đã gửi tin nhắn thành công")
              this.cboSdt = []
              this.noiDungInput = ''
              this.getDsSmsDaGui(this.TTObject.TIENTRINHSC_ID)
            }else {
              this.$root.toastError("Gửi tin nhắn thất bại")
            }
          }
        })
      }catch (e){
        this.$root.toastError("Có lỗi xảy ra khi gửi tin nhắn")
      }
      finally {
        this.loading(false)
      }
    },
    dsNV_selectedRowChanged(row){
      if (row && row.SO_DT){
        let checkDs = this.dsSdt.filter(item => item.SO_DT === row.SO_DT)
        if (checkDs.length === 0){
          this.dsSdt.push({SO_DT:row.SO_DT})
        }
        if (!this.cboSdt.includes(row.SO_DT)){
          this.cboSdt.push(row.SO_DT)
        }
      }
      this.dsSdt = [...this.dsSdt]
    },
    dsSms_selectedRowChanged(args){
      console.log(args)
      let item = args.data
      if (item){
        let isHasDsSdt = this.dsSdt.filter(i => i.SO_DT === item.SO_DT).length > 0
        if (!isHasDsSdt){
          this.dsSdt.push({SO_DT:item.SO_DT})
          this.dsSdt = [...this.dsSdt]
        }
        this.cboSdt = [item.SO_DT]
        if (!item.NOIDUNG){
          let i = item.NOIDUNG + ""
          this.noiDungInput = i.split(":")[1]
        }else {
          this.noiDungInput = item.NOIDUNG.split(":")[1]
        }
      }else {
        this.cboSdt = []
        this.noiDungInput = ''
      }
    },
    hideModal: function () {
      this.$emit('hideModal')
      // this.$emit('reloadTT')
    },
    async getDsSdt(){
      this.loading(true)
      await this.$root.context.get(
        "/web-suco/TienTrinhSuCo/ds-sdt"
      ).then(res =>{
        this.dsSdt = res.data
      }).catch(e=> this.$root.toastError("Lỗi nạp đề xuất số điện thoại\r\n"+e)).finally(()=> this.loading(false))
    },
    async getDsNhienVienNhanTin(suCoId){
      this.loading(true)
      await this.$root.context.get(
        "/web-suco/TienTrinhSuCo/ds-nhanvien-nhantin", {suCoId:suCoId}
      ).then(res =>{
        this.dsNhanVienNhanTin = res.data
      }).catch(e=> this.$root.toastError("Lỗi nạp danh sách nhân viên nhắn tin\r\n"+ e)).finally(()=> this.loading(false))
    },
    async getDsSmsDaGui(tienTrinhScId){
      this.loading(true)
      await this.$root.context.get(
        "/web-suco/TienTrinhSuCo/ds-sms-dagui", {tienTrinhScId:tienTrinhScId}
      ).then(res =>{
        this.dsSmsDaGui = res.data
      }).catch(e=> this.$root.toastError("Lỗi nạp danh sách tin nhắn đã gửi\r\n"+e)).finally(()=> this.loading(false))
    }
  }
}
</script>

<style>
@media (min-width: 1200px){
  .modal-xl {
    max-width: 80% !important;
  }
}
</style>
