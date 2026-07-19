<template src="./index.html"></template>
<script>
import {actionName, statePropertyName} from '../../../store/QuanLyBras'
import {mapActions, mapState} from 'vuex'
import {ipAddress,numeric, required} from 'vuelidate/lib/validators'

import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components: {BssRequiredMarker},
  name: 'Modal',
  props: ['modalId'],
  computed: {
    ...mapState('ecms/quanLyBras', statePropertyName)
  },
  destroyed () {
  },
  methods: {
    ...mapActions('ecms/quanLyBras', actionName),
    focusMyElement() {
      this.$refs.brasId.focus()
    },
    async onclickBrasChiTiet (event) {
      try {
        this.isCreatedBras = false
        await this.chiTietBrasAction(event)
        this.brasId = this.chiTietBras.BRAS_ID
        this.brasVcdId = this.chiTietBras.BRAS_VDC_ID
        this.tenBras = this.chiTietBras.TENBRAS
        this.ipBras = this.chiTietBras.IP
        this.soSlot = this.chiTietBras.SO_SLOT
        this.soPort = this.chiTietBras.SO_PORT
        this.setDisableBtn(false, false, false, false)
      } catch (err) {
        console.log(err)
      } finally {
      }
    },
    async SaveAs () {
      console.log(this.brasId)
      if(this.checkThongSoPort){
        this.used = 1
      }else {
        this.used = 0
      }
      if (this.isCreatedBras === true) {
        this.createBras()
      } else {
        this.updateBras()
      }
    }, async createBras () {
      let objAdd = {
        bras_id: this.brasId,
        bras_vdc_id: this.brasVcdId,
        ten_bras: this.tenBras,
        ip: this.ipBras,
        so_slot: this.soSlot,
        so_port: this.soPort,
        used: this.used
      }
      try {
        if(this.validateData(objAdd)){
          return
        }
        let response = await this.themMoi(objAdd)
        if (response.error_code === 'BSS-00000000') {
          this.$toast.success('Thêm mới thành công.')
          this.dsBras.unshift(this.brasNew)
          this.keyReset +=1
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (err) {
         console.log(err)
         window.myerror = err
        let message = err.response.data.message
        let message_detail = err.response.data.message_detail
        if(message_detail){
          this.$toast.error(message_detail)
          return
        }
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {

      }
    }, async updateBras () {
      let objUpd = {
        bras_id: this.brasId,
        bras_vdc_id: this.brasVcdId,
        ten_bras: this.tenBras,
        ip: this.ipBras,
        so_slot: this.soSlot,
        so_port: this.soPort,
        used: this.used
      }
      try {
        if(this.validateData(objUpd)){
          return
        }
        let response = await this.capNhat(objUpd)
        if (response.error_code === 'BSS-00000000') {
          this.$toast.success('Cập nhật thành công.')
          await this.layDsBrasAction()
        } else {
          this.$toast.error('Có lỗi xảy ra.')
        }
      } catch (err) {
        // console.log(err)
        // window.myerror = err
        let message = err.response.data.message
        if (message !== null) {
          this.$toast.error(message)
        }
      } finally {

      }
    },
  xoaBras: function () {
    if (!this.isDisableBtnXoa) {
      this.modalXoa = !this.modalXoa
    }
  },
  async xacNhanXoaBras() {
    try {
      var rs = await this.xoaAction(this.brasId)
      if (rs.data.result == true) {
        this.setDisableBtn(false, true, true, true)
        this.keyReset +=1
        this.$toast.success('Xóa thành công.')
        await this.layDsBrasAction()
        await this.onclickBrasChiTiet(this.dsBrasPagging[0].BRAS_ID)
      } else {
        this.$toast.error('Xóa thất bại.')
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
  },validateData(data){
      this.$v.$touch();
      let errorMessage = []
      window.mydata = data
      if (!data.bras_id.toString()) {
        errorMessage.push("Trường Bras Id bắt buộc nhập dữ liệu.")
      }
      if (!data.bras_vdc_id.toString()) {
        errorMessage.push("Trường Bras Vcd Id bắt buộc nhập dữ liệu.")
      }

      if (!data.ten_bras) {
        errorMessage.push("Tên bras bắt buộc nhập dữ liệu.")
      }

      if (!data.ip) {
        errorMessage.push("Trường Ip bắt buộc nhập dữ liệu.");
      }

      if(this.$v.soSlot.$error){
        if (!data.so_slot.toString().trim()) {
          errorMessage.push("Trường slot bắt buộc nhập dữ liệu.")
        }else {
          errorMessage.push("Trường số slot chỉ được phép nhập số.")
        }
      }

      if(this.$v.soPort.$error){
        if (!data.so_port.toString().trim()) {
          errorMessage.push("Trường port bắt buộc nhập dữ liệu.")
        }else {
          errorMessage.push("Trường số port chỉ được phép nhập số.")
        }
      }


      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    },
    async clearForm() {
      this.isCreatedBras = true
      this.setDisableBtn(false, false, false, true)
      this.$v.$reset();
      this.brasId = ''
      this.brasVcdId = ''
      this.tenBras = ''
      this.ipBras = ''
      this.soSlot = ''
      this.soPort = ''
      this.disableBrasId = false;
      this.disableBrasVcdId = false;

      this.itemCancel.brasId = ''
      this.itemCancel.brasVcdId = ''
      this.itemCancel.tenBras = ''
      this.itemCancel.ipBras= ''
      this.itemCancel.soSlot = ''
      this.itemCancel.soPort = ''
      await this.focus().then({
      })
      this.$refs.brasId.focus();
    },
    focus: async function(){
      this.disableBrasId = false;
    }, setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi
      this.isDisableBtnGhiLai = isGhilai
      this.isDisableBtnHuy = isHuy
      this.isDisableBtnXoa = isXoa
    },cancelFunc: function () {
    this.setDisableBtn(false,true,true,false)
      if(this.itemCancel){
         this.brasId= this.itemCancel.brasId
        this.brasVcdId = this.itemCancel.brasVcdId
        this.tenBras = this.itemCancel.tenBras
        this.ipBras = this.itemCancel.ipBras
        this.soSlot = this.itemCancel.soSlot
        this.soPort = this.itemCancel.soPort
      }
  },
    async grid1_selectedRowChanged(dataItem){
      console.log(dataItem);
      try {
        this.isCreatedBras = false
        if(dataItem){
          this.brasId = dataItem.BRAS_ID
          this.brasVcdId = dataItem.BRAS_VDC_ID
          this.tenBras = dataItem.TENBRAS
          this.ipBras = dataItem.IP
          this.soSlot = dataItem.SO_SLOT
          this.soPort = dataItem.SO_PORT

          this.disableBrasId = true;
          this.disableBrasVcdId = true;

          this.itemCancel.brasId = dataItem.BRAS_ID
          this.itemCancel.brasVcdId = dataItem.BRAS_VDC_ID
          this.itemCancel.tenBras = dataItem.TENBRAS
          this.itemCancel.ipBras = dataItem.IP
          this.itemCancel.soSlot = dataItem.SO_SLOT
          this.itemCancel.soPort = dataItem.SO_PORT
        }
        this.setDisableBtn(false, false, false, false)
      } catch (err) {
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
      let loading1 = this.layDsBrasAction()
      await loading1

      if (this.dsBras && this.dsBras.length > 0) {
        this.dsBrasPagging = this.dsBras.slice(0, this.papeSize)
      }
      if(this.dsBras){
        this.brasId = this.dsBras[0].BRAS_ID
        this.brasVcdId = this.dsBras[0].BRAS_VDC_ID
        this.tenBras = this.dsBras[0].TENBRAS
        this.ipBras = this.dsBras[0].IP
        this.soSlot = this.dsBras[0].SO_SLOT
        this.soPort = this.dsBras[0].SO_PORT
      }
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {
      dsBrasPagging: [],
      papeSize: 10, brasId: '', brasVcdId: '', tenBras: '', ipBras: '', soSlot: '', soPort: '',used :'',
      isDisableBtnNhapMoi: false,
      isDisableBtnGhiLai: true,
      isDisableBtnHuy: true,
      isDisableBtnXoa: true,
      isCreatedBras: true,
      disableBrasId: true,
      disableBrasVcdId: true,
      modalXoa: false,
      checkThongSoPort : false,
      keyReset :1,
      itemCancel:{
        brasId  :'',
        brasVcdId :'',
        tenBras :'',
        ipBras :'',
        soSlot :'',
        soPort :''
      }
    }
  },validations: {
    brasId: {
      required,
    }, brasVcdId: {
      required,
    }, tenBras: {
      required
    }, ipBras: {
      required,
      ipAddress
    }, soSlot: {
      required,
      numeric
    }, soPort: {
      required,
      numeric
    }
  }
}
</script>
<style>

.danh-sach-bras .e-gridcontent .e-content .e-table tbody .e-row .e-rowcell {
  cursor: pointer;
}
</style>
