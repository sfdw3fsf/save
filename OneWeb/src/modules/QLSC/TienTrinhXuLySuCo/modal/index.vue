<template src="./index.html"></template>
<script>
import {required} from 'vuelidate/lib/validators'
export default {
  components: {},
  name: 'Modal',
  props: {
    id: String,
    TTObject: {
      type: Object,
      required: true
    }
  },
  validations: {
    tienTrinhSuCo: {
      required
    }
  },
  data: function () {
    return {
      isShown: false,
      key: 1,
      tienTrinh: {}
    }
  },
  created() {
    // this.tienTrinh = this.TTObject
  },
  watch:{
    // "tienTrinh.NOIDUNG": function (newNoiDung){
    //   this.noidungInput = newNoiDung
    // }
  },
  methods:{
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.$v.$reset()
      this.isShown = true
      this.tienTrinh = this.TTObject
    },
    // hideModal: function () {
    //   this.$emit('hideModal')
    //   this.$emit('reloadTT')
    // },
    async btnGhiLai(){
      try {
        this.loading(true)
        if (this.tienTrinh.TIENTRINHSC_ID == null){
          //insert tien trinh
          let object = new Object
          object.suCoId = this.tienTrinh.SUCO_ID
          object.noiDung = this.tienTrinh.NOIDUNG
          if (this.validateData(object,"INSERT")) return
          await this.$root.context.post("/web-suco/TienTrinhSuCo/ThemTienTrinh",object)
            .then(res=>{
              if (res.error === "200"){
                this.$toast.success("Thêm tiến trình thành công")
                this.$emit('reloadTT')
                this.$v.$reset()
              }
            }).catch(e =>{throw e})
        }else if (this.tienTrinh.TIENTRINHSC_ID != null){
          //update tien trinh
          let object = new Object()
          object.tienTrinhSCId = this.tienTrinh.TIENTRINHSC_ID
          object.noiDung = this.tienTrinh.NOIDUNG
          if (this.validateData(object,"UPDATE")) return
          await this.$root.context.post("/web-suco/TienTrinhSuCo/CapNhatTienTrinh", object)
          .then(res=>{
            if (res.error === "200"){
              this.$toast.success("Cập nhật tiến trình thành công")
              this.$emit('reloadTT')
              this.$v.$reset()
            }
          }).catch(e =>this.$toast.error(e.response.message_detail))
        }
      }catch (e){
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    validateData(data, key){
      this.$v.$touch();
      let errorMessage = []
      if (key === "INSERT"){
        if (data.suCoId == null) errorMessage.push("Sự cố id rỗng")
      }else {
        if (data.tienTrinhSCId == null) errorMessage.push("Tiến trình sự cố id rỗng")
      }
      if (data.noiDung == null || data.noiDung === '') errorMessage.push("Nội dung không được để trống")
      if (data.noiDung.length > 200) errorMessage.push("Ký tự nhập vào tối đa 200 ký tự")
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    },
  }
}
</script>
