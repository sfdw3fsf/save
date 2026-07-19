<template src="./index.html"></template>
<script>
import ChonDS from './ChonDS'
import {maxLength, required} from "vuelidate/lib/validators";
import BssRequiredMarker from '@/components/BssRequiredMarker'
export default {
  components:{ChonDS, BssRequiredMarker},
  name: "QuanLyCapReNhanh",
  props: {
    midspanObject: {
      type: Object,
      required: false
    },
    id: String
  },
  data: function (){
    return {
      key: 1,
      isShown: false,
      midspan: {
        MIDSPAN_ID:null,
        CAP_ID:null,
        KETCUOI_N_ID:null,
        KETCUOI_D_ID:null,
        KYHIEU_KETCUOI_N:null,
        KYHIEU_KETCUOI_D:null,
        DONVI_ID:null,
        DONVI_TD_ID:null,
        TEN_DV:null,
        TEN_DV_TD:null,
        LOPMC_ID:null,
        LOP_MC:null,
        KIEUDC_ID:null,
        KIEUCAP_ID:null,
        KIEU_CAP:null,
        KYHIEU:"",
        CHIEUDAI:null,
        DS_SOI:""
      },
      isInsert:Boolean,
      dsKieuDc:[],
    }
  },
  methods: {
    async btnGhiLai(){
      try {
        this.loading(true)
        if (this.isInsert){
          // kiem tra du lieu
          if (this.validateData(this.midspan)) return
          // tao mid span
          await this.$root.context.post("/web-cabman/mid-span/them-midspan", this.midspan)
            .then(res=>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.$toast.success("Thêm midspan thành công")
                this.$emit('napGD')
                this.onHiddenModal()
              }
            })
            .catch(e => this.$toast.error("Có lỗi xảy ra trong quá trình thêm midspan"))
        }else {
          //kiem tra du lieu
          if (this.validateData(this.midspan)) return
          //ghi lai
          await this.$root.context.post("/web-cabman/mid-span/capnhat-midspan", this.midspan)
            .then(res=>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.$toast.success("Cập nhật midspan thành công")
                this.$emit('napGD')
                this.onHiddenModal()
              }
            })
            .catch(e => this.$toast.error("Có lỗi xảy ra trong quá trình cập nhật midspan"))
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        await this.getDsKieuDc()
        if (this.midspanObject.MIDSPAN_ID){
          this.isInsert = false
          this.midspan = {...this.midspanObject}
          await this.getThongTinMidSpan(this.midspanObject.MIDSPAN_ID)
        }else {
          this.isInsert = true
          this.midspan = {...this.midspanObject}
          this.midspan.KIEUDC_ID = 1
          if (this.midspan.DONVI_ID){
            if (this.midspan.DONVI_TD_ID) this.midspan.LOPMC_ID = 4
            else this.midspan.LOPMC_ID = 1
          }else {
            if (this.midspan.DONVI_TD_ID) this.midspan.LOPMC_ID = 3
            else this.midspan.LOPMC_ID = 2
          }
          await this.getTenLopMangCap(this.midspan.LOPMC_ID)
          await this.getTenKieuCap(this.midspan.KIEUCAP_ID)
          if (this.midspan.DONVI_ID) await this.getTenTramThietBi(this.midspan.DONVI_ID,"NGUON")
          if (this.midspan.DONVI_TD_ID) await this.getTenTramThietBi(this.midspan.DONVI_TD_ID,"DICH")
          if (this.midspan.KETCUOI_N_ID) await this.getTenKetCuoi(this.midspan.KETCUOI_N_ID, "NGUON")
          if (this.midspan.KETCUOI_D_ID) await this.getTenKetCuoi(this.midspan.KETCUOI_D_ID, "DICH")

        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    btnPopupChonDS(){
      this.$bvModal.show('popupChonDS')
    },
    async getThongTinMidSpan(midspanId){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-thongtin-midspan",{midspanId:midspanId}
      ).then(res =>{
        if (res.data !== null) this.midspan = res.data
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải thông tin cáp midspan")
      })
    },
    async getDsKieuDc(){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-ds-kieu-dc",
      ).then(res =>{
        this.dsKieuDc = res.data
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải danh sách kiểu đi cáp")
      })
    },
    async getTenLopMangCap(lopmcId){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-ten-lop-mc",{lopmcId:lopmcId}
      ).then(res =>{
        if (res.data) this.midspan.LOP_MC = res.data.LOP_MC
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải lớp mạng cáp")
      })
    },
    async getTenKieuCap(kieucapId){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-ten-kieucap",{kieucapId:kieucapId}
      ).then(res =>{
        if (res.data) this.midspan.KIEU_CAP = res.data.KIEU_CAP
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải kiểu cáp")
      })
    },
    async getTenTramThietBi(donviId, nguondich){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-ten-tram-thietbi",{donviId:donviId}
      ).then(res =>{
        if (res.data && nguondich === "NGUON") this.midspan.TEN_DV = res.data.TEN_DV
        if (res.data && nguondich === "DICH") this.midspan.TEN_DV_TD = res.data.TEN_DV
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải kiểu cáp")
      })
    },
    async getTenKetCuoi(ketcuoiId, nguondich){
      await this.$root.context.get(
        "/web-cabman/mid-span/lay-ten-ketcuoi",{ketcuoiId:ketcuoiId}
      ).then(res =>{
        if (res.data && nguondich === "NGUON") this.midspan.KYHIEU_KETCUOI_N = res.data.KYHIEU
        if (res.data && nguondich === "DICH") this.midspan.KYHIEU_KETCUOI_D = res.data.KYHIEU
      }).catch(e=> {
        this.$toast.error("Có lỗi xảy ra khi tải ký hiệu kết cuối")
      })
    },
  validateData(data){
    this.$v.$touch()
    let errorMessage = []
    if (this.$v.midspan.KYHIEU.$error) {
      if (!data.KYHIEU.toString()){
        this.$refs.kyhieu.focus()
        errorMessage.push("Trường Ký hiệu không được trống")
      }
      if (data.KYHIEU.toString().length > 70){
        this.$refs.kyhieu.focus()
        errorMessage.push("Trường Ký hiệu có độ dài không nằm trong khoảng từ 1 tới 70")
      }
    }
    if (this.$v.midspan.LOP_MC.$error){
      if (!data.LOPMC_ID){
        this.$refs.lopmcId.focus()
        errorMessage.push("Trường Lớp mạng cáp không được trống")
      }
    }
    if (!data.KIEUDC_ID){
      this.$refs.kieudcId.focus()
      errorMessage.push("Trường lớp Kiểu đi cáp không được trống")
    }
    if (this.$v.midspan.KIEU_CAP.$error){
      if (!data.KIEUCAP_ID)
        this.$refs.kieucapId.focus()
      errorMessage.push("Trường Kiểu cáp không được trống")
    }
    if (!data.DS_SOI){
      this.$refs.dsSoi.focus()
      errorMessage.push("Trường Danh sách sợi không được trống")
    }
    if (errorMessage.length > 0) {
      this.$toast.error(errorMessage.join('\n'))
      return true
    }else {
      this.$v.$reset()
      return false
    }
  }

  },
  validations: {
    midspan:{
      KYHIEU: {
        maxLength:maxLength(70),
        required
      },
      LOP_MC: required,
      KIEUDC_ID: required,
      KIEU_CAP: required,
      DS_SOI: required,
    },
  },
}
</script>

<style scoped>

</style>
