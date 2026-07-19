<template src="./index.html"></template>
<script>
import {actionName, statePropertyName} from '../../../store/QuanLyModule'
import {mapActions, mapState} from 'vuex'
import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components: {BssRequiredMarker},
  name: 'Modal',
  props: ['modalId'],
  computed: {
    ...mapState('ecms/quanLyModule', statePropertyName)
  },
  destroyed () {
  },
  methods: {
    ...mapActions('ecms/quanLyModule', actionName),
    async SaveAs () {
      console.log(this.brasId)
      this.setDisableBtn(false, false, false, false)
      if (this.isCreatedModule === true) {
        await this.createModule()
      } else {
       await this.updateModule()
      }
    }, async createModule () {
      let objAdd = {
        loai_module: this.loaiModule,
        port_bd: this.portBd,
        so_port: this.soPort
      }
      try {
        if(this.validateData(objAdd)){
          return
        }
        let response = await this.themMoiModule(objAdd)
        if (response.error_code === 'BSS-00000000') {
          this.dsModule.unshift(this.moduleNew);
          this.keyReset +=1
          this.$toast.success('Thêm mới thành công.')
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (err) {
        let message = err.response.data.message
        if(err.response.data.message_detail){
          if(err.response.data.message_detail.includes('Loại module này đã tồn tại, không thế tạo.')){
            this.$toast.error('Trường loại module đã tồn tại, vui lòng nhập lại.')
          }else {
            this.$toast.error(err.response.data.message_detail)
          }
          return
        }
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {

      }
    }, async updateModule () {
      if(this.loaiModuleId == null){
        this.$toast.error('Loại module Id không tồn tại.')
        return
      }
      let objUpd = {
        loai_module_id: this.loaiModuleId,
        loai_module: this.loaiModule,
        port_bd: this.portBd,
        so_port: this.soPort
      }
      try {
        if(this.validateData(objUpd)){
          return
        }
        let response = await this.capNhatModule(objUpd)
        if (response.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật thành công.')
          await this.layDsModuleAction()
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (err) {
        let message = err.response.data.message
        if(err.response.data.message_detail){
          if(err.response.data.message_detail.includes('LoaiModule max length.')){
            this.$toast.error('Trường loại module dữ liệu vượt quá độ dài cho phép.')
            this.$refs.loaiModule.focus();
            this.$v.loaiModule.$touch();
          }else {
            this.$toast.error(err.response.data.message_detail)
          }
          return
        }
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {

      }
    },cancelFunc: function () {
      this.setDisableBtn(false,true,true,false)
      if(this.itemCancel){
        this.loaiModule = ""
        this.soPort = ""
        this.portBd = ""
      }
    },
  xoaModule: function () {
    if (!this.isDisableBtnXoa) {
      this.modalXoa = !this.modalXoa
    }
  },
  async xacNhanXoaModule() {
    try {
      var rs = await this.xoaModuleAction(this.loaiModuleId)
      if (rs.data.result == true) {
        this.setDisableBtn(false, true, true, true)
        this.$toast.success('Xóa thành công.')
        await this.layDsModuleAction()
        await this.onclickBrasChiTiet(this.dsBrasPagging[0].BRAS_ID)
      } else {
        this.$toast.error('Xóa thất bại.')
        this.layDsModuleAction()
      }
    } catch (err) {
      let message = err.response.data.message
      let message_detail = err.response.data.message_detail
      if(message_detail !== null){
        this.$toast.error(message_detail)
      } else if (message !== null) {
        this.$toast.error(message)
      }
    } finally {
      this.loading(false)
    }
  },
    clearForm () {
      this.$v.$reset();
      if(!this.isDisableBtnNhapMoi){
        this.isCreatedModule = true
        this.setDisableBtn(true, false, false, true)
        this.loaiModule = ''
        this.portBd = ''
        this.soPort = ''
        this.itemCancel.loaiModule = ''
        this.itemCancel.soPort = ''
        this.itemCancel.portBd = ''
        this.itemCancel.loaiModuleId = ''
      }
    },
      setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    },focusMyElement() {
      this.$refs.loaiModule.focus()
    },validateData(data){
      this.$v.$touch();
    let errorMessage = []

      if(this.$v.portBd.$error){
        this.$refs.portBd.focus()

        if (!data.port_bd.toString().trim()) {
          errorMessage.unshift("Trường port bắt đầu không được trống.")
        }else if(data.port_bd > 9){
          errorMessage.unshift("Trường port bắt đầu dữ liệu vượt quá độ dài cho phép.")
        }else {
          errorMessage.unshift("Trường port bắt đầu chỉ được phép nhập số.")
        }
      }

      if(this.$v.soPort.$error){
        this.$refs.soPort.focus()

        if (!data.so_port.toString().trim()) {
          errorMessage.unshift("Trường số port không được trống.")
        }else if(data.so_port > 999){
          errorMessage.unshift("Trường số port dữ liệu vượt quá độ dài cho phép.")
        }else {
          errorMessage.unshift("Trường Số Port chỉ được phép nhập số.")
        }
      }

      if(this.$v.loaiModule.$error){
        this.$refs.loaiModule.focus()

        if (!data.loai_module.toString()) {
          errorMessage.unshift("Trường loại module không được trống.")
          this.$refs.loaiModule.focus()
        } else  if(data.loai_module.length > 30){
          errorMessage.unshift("Trường loại module dữ liệu vượt quá độ dài cho phép.")
        }
      }

    if (errorMessage.length > 0) {
      this.$toast.error(errorMessage.join('\n'))
      return true
    }else {
      return false
    }
  },
    async grid1_selectedRowChanged(dataItem){
      console.log(dataItem);
      try {
        this.isCreatedModule = false
        this.setDisableBtn(false, false, false, false)
        if(dataItem){
          window.dataItem =dataItem
          this.loaiModule = dataItem.LOAI_MODULE
          this.soPort = dataItem.SO_PORT
          this.portBd = dataItem.PORT_BD
          this.loaiModuleId = dataItem.LOAIMODULE_ID
        //  dataItem = this.itemCancel
          this.itemCancel.loaiModule = dataItem.LOAI_MODULE
          this.itemCancel.soPort = dataItem.SO_PORT
          this.itemCancel.portBd = dataItem.PORT_BD
          this.itemCancel.loaiModuleId = dataItem.LOAIMODULE_ID
        }
      } catch (err) {
        window.err =err
        console.log(err)
      } finally {
      }
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  async mounted () {
    try {
      this.loading(true)
      let loading1 = this.layDsModuleAction()
      await loading1
       this.setDisableBtn(false, false, false, false)
      if (this.dsModule && this.dsModule.length > 0) {
        this.dsModulePagging = this.dsModule.slice(0, this.papeSize)
      }
      if(this.dsModule){
        this.loaiModule = this.dsModule[0].LOAI_MODULE
        this.soPort = this.dsModule[0].SO_PORT
        this.portBd = this.dsModule[0].PORT_BD
        this.loaiModuleId = this.dsModule[0].LOAIMODULE_ID
      }
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {keyReset: 1,
      dsModulePagging: [],
      papeSize: 10, loaiModule: '', soPort: '', portBd: '',loaiModuleId: '',
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: true,
      isDisableBtnXoa: true,
      isCreatedModule: true,
      modalXoa: false,
      itemCancel:{
        loaiModule: '',
        soPort:'',
        portBd: '',
        loaiModuleId: ''
      }
    }
  },validations: {
    soPort: {
      required,
      maxlength: maxLength(4),
      numeric
    }, portBd: {
      required,
      maxlength: maxLength(1),
      numeric
    }, loaiModule: {
      required,
      maxlength: maxLength(30),
    }
  }
}
</script>

<style scoped src="./index.scss">
</style>
