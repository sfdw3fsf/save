<template src="./index.html"></template>
<script>
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import {required, minValue, maxValue, minLength, maxLength} from 'vuelidate/lib/validators'
export default {
  components:{BssErrorMarker, BssRequiredMarker},
  name: "QLCapTD",
  props: {
    /***
     * case 1: cho phép INS: capObj:{...,không có CAP_ID}, taomoi:true , editable:false
     * case 2 cho phép UPD: capObj:{CAP_ID:123xx}, taomoi:false, editable:true
     * case 3: cho phép view: capObj:{CAP_ID:123xxx}, taomoi:false, editable:false
     */
    id: String,
    capObj: {
      type:Object,
      required: true
    },
    taomoi:Boolean,
    editable: Boolean,
  },
  data: function (){
    return {
      isShown: false,
      isDisableBtnGhiLai: true,

      taomoiCap: false,
      captd: {},

      category: {
        dinhdanh: 0,
        thuoctinh: 0,
        vitri: 0,
        thongtinbosung: 0
      },
      modalData: {
        NOI_SX: [],
        LOAI_CAP: [],
        KIEU_DC: [],
      },
      waterMark: 'Chọn ngày',
      dateVal: new Date(),
      dateFormat: 'dd/MM/yyyy'
    }
  },
  destroyed() {
    this.clearForm()
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.clearForm()
    },
    async onShownModal() {
      this.isShown = true
      try {
        this.loading(true)
        await this.initForm()

        this.captd = {...this.capObj}
        this.taomoiCap = this.taomoi
        if (this.taomoiCap === false  && this.editable === true){
          // case UPD
          this.statusBtn(false)
          await this.getThongTinCapTD(this.captd.CAPTD_ID)
        }
        else {
          if (this.taomoiCap === true && this.editable === false){
            // case INS
            this.statusBtn(false)
            // this.createCap()
            // await this.getThongTinTaoCap()
          }else if (this.taomoiCap === false && this.editable === false){
            // case VIEW
            this.statusBtn(true)
            await this.getThongTinCapTD(this.captd.CAPTD_ID)
          }

          this.captd = {...this.captd}
        }

      }catch (e){

      }finally {
        this.loading(false)
      }
    },
    async btnGhiLai(){
      if (this.isDisableBtnGhiLai) return
      try {
        this.loading(true)
        if (await this.validateData()) return
        if (this.taomoiCap){
          // INSERT]
          let object = {...this.captd}
          await this.$root.context.post("web-cabman/mang-truyen-dan/tao-captd",object)
            .then(res =>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.captd = res.data
                this.captd.NGAY_LD = new Date(this.captd.NGAY_LD)
                this.captd.NGAY_SD = new Date(this.captd.NGAY_SD)
                this.taomoiCap = false
                this.statusBtn(false)
                this.$root.toastSuccess("Tạo mới thành công")
              }
            }).catch(e => this.$root.toastError("Có lỗi xảy ra trong quá trình thêm mới cáp"))
        }else {
          // UPDATE
          this.captd.NGAY_LD = new Date(this.captd.NGAY_LD)
          this.captd.NGAY_SD = new Date(this.captd.NGAY_SD)
          await this.$root.context.post("web-cabman/mang-truyen-dan/capnhat-captd",this.captd)
            .then(res =>{
              if (res.error === "200" && res.error_code === "BSS-00000000"){
                this.$root.toastSuccess("Ghi lại thành công")
              }
            }).catch(e => this.$root.toastError("Có lỗi xảy ra trong quá trình cập nhật cáp truyền dẫn"))
        }
      }catch (e){
        console.log(e)
      }finally {
        this.loading(false)
      }
    },
    async getThongTinCapTD(CAPTD_ID){
      await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-captd",{capId:CAPTD_ID})
      .then(res =>{
        this.captd = res.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin cáp truyền dẫn"))
    },
    // async getThongTinTaoCap(){
    //   await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'LOP_MC', LOAI_ID:this.cap.LOPMC_ID})
    //   .then(res =>{
    //     if (res.data.length === 1)
    //       this.cap.LOP_MC = res.data[0].LOP_MC
    //     else this.cap.LOP_MC = ''
    //   }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin lớp mạng cáp"))
    //
    //   await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KIEU_CAP', LOAI_ID:this.cap.KIEUCAP_ID})
    //     .then(res =>{
    //       if (res.data.length === 1)
    //         this.cap.KIEU_CAP = res.data[0].KIEU_CAP
    //       else this.cap.KIEU_CAP = ''
    //     }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kiểu cáp"))
    //
    //   if (this.cap.CAPGOC_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'CAPGOC_KYHIEU', LOAI_ID:this.cap.CAPGOC_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.CAPGOC_KYHIEU = res.data[0].CAPGOC_KYHIEU
    //         else this.cap.CAPGOC_KYHIEU = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin cáp gốc"))
    //   }else this.cap.CAPGOC_KYHIEU = ''
    //
    //   if (this.cap.KETCUOI_N_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KETCUOI_N_KYHIEU', LOAI_ID:this.cap.KETCUOI_N_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.KETCUOI_N_KYHIEU = res.data[0].KETCUOI_N_KYHIEU
    //         else this.cap.KETCUOI_N_KYHIEU = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối nguồn"))
    //   }else this.cap.KETCUOI_N_KYHIEU = ''
    //
    //   if (this.cap.KETCUOI_D_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'KETCUOI_D_KYHIEU', LOAI_ID:this.cap.KETCUOI_D_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.KETCUOI_D_KYHIEU = res.data[0].KETCUOI_D_KYHIEU
    //         else this.cap.KETCUOI_D_KYHIEU = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin kết cuối đích"))
    //   }else this.cap.KETCUOI_D_KYHIEU = ''
    //
    //   if (this.cap.DONVI_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TRAMTBI', LOAI_ID:this.cap.DONVI_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.TEN_TRAMTBI = res.data[0].TEN_TRAMTBI
    //         else this.cap.TEN_TRAMTBI = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin trạm thiết bị"))
    //   }else this.cap.TEN_TRAMTBI = ''
    //
    //   if (this.cap.TOQL_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TOQUANLY', LOAI_ID:this.cap.TOQL_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.TEN_TOQUANLY = res.data[0].TEN_TOQUANLY
    //         else this.cap.TEN_TOQUANLY = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin tổ quản lý"))
    //   }else this.cap.TEN_TOQUANLY = ''
    //
    //   if (this.cap.DONVI_TD_ID !== -1){
    //     await this.$root.context.get("web-cabman/mang-truyen-dan/lay-thongtin-taocap",{LOAI:'TEN_TRAMTD', LOAI_ID:this.cap.DONVI_TD_ID})
    //       .then(res =>{
    //         if (res.data.length === 1)
    //           this.cap.TEN_TRAMTD = res.data[0].TEN_TRAMTD
    //         else this.cap.TEN_TRAMTD = ''
    //       }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin trạm truyền dẫn"))
    //   }else this.cap.TEN_TRAMTD = ''
    //
    //
    // },
    clearForm(){
      this.captd = {}

      this.modalData = {
        NOI_SX: [],
        LOAI_CAP: [],
        KIEU_DC: [],
      }
      this.category = {
        dinhdanh: 0,
        thuoctinh: 0,
        vitri: 0,
        thongtinbosung: 0
      }
    },
    createCap(){
      // this.cap = capObj
      this.captd.KIEUDC_ID = 1
      this.captd.LOAICAP_ID = 1
      this.captd.NGAY_LD = new Date()
      this.captd.NGAY_SD = new Date()
      this.captd.NOISX_ID = 1
    },
    statusBtn(btnGhiLai){
      this.isDisableBtnGhiLai = btnGhiLai
    },
    async initForm(){

      await this.$root.context.post(
        '/web-cabman/danhmuc/layDsNoiSX',
      ).then(rs =>{
        this.modalData.NOI_SX = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách nơi sản xuất"))

      await this.$root.context.get(
        '/web-cabman/mang-truyen-dan/layDsLoaiDayNhay',
      ).then(rs =>{
        this.modalData.LOAI_CAP = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại dây nhảy"))

      await this.$root.context.get(
        '/web-cabman/mang-truyen-dan/layDsKieuDC',
      ).then(rs =>{
        this.modalData.KIEU_DC = rs.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại đầu cắm"))

    },
    async validateData(){
      this.$v.$touch()
      let errorMessage = []
      if (!this.$v.captd.TEN_CAPTD.required) {
        errorMessage.push('Tên cáp truyền dẫn không được để trống')
      }
      if (!this.$v.captd.KYHIEU.required) {
        errorMessage.push('Ký hiệu không được để trống')
      }else {
        //kiem tra tồn tại kyhieu
        await this.$root.context.post("/web-cabman/mang-truyen-dan/kiemtra-unique-kyhieu-captd",{CAP_ID:this.taomoiCap ? null : this.captd.CAPTD_ID,KYHIEU:this.captd.KYHIEU})
          .then(res=>{
            if (res.data != null && res.data.result === false){
              errorMessage.push('Ký hiệu đã tồn tại')
            }
          }).catch(e => this.$root.toastError("Có lỗi xảy ra trong quá trình kiểm tra ký hiệu tồn tại"))
      }

      if (!this.$v.captd.DOITUONG_N_ID.required) {
        errorMessage.push('Đối tượng nguồn không được để trống')
      }

      if (!this.$v.captd.DOITUONG_D_ID.required) {
        errorMessage.push('Đối tượng đích không được để trống')
      }

      if (!this.$v.captd.TUYENTD_ID.required) {
        errorMessage.push('Tuyến truyền dẫn không được để trống')
      }

      if (!this.$v.captd.SODOIDAY.required) {
        errorMessage.push('Dung lượng không được để trống')
      }

      if (!this.$v.captd.SODOIDAY.minValue || !this.$v.captd.SODOIDAY.maxValue){
        errorMessage.push('Dung lượng phải nằm trong khoản từ 1 đến 99999')
      }

      if (!this.$v.captd.LOAICAP_ID.required) {
        errorMessage.push('Loại dây nhảy không được để trống')
      }

      if (!this.$v.captd.CHIEUDAI.required) {
        errorMessage.push('Chiều dài không được để trống')
      }

      if (!this.$v.captd.CHIEUDAI.minValue || !this.$v.captd.CHIEUDAI.maxValue){
        errorMessage.push('Chiều dài phải nằm trong khoản từ 1 đến 99999')
      }

      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    }
  },
  validations: {
    captd:
      {
        TEN_CAPTD: {
          required,
        },
        KYHIEU: {
          required,
        },
        DOITUONG_N_ID:{
          required,
        },
        DOITUONG_D_ID:{
          required,
        },
        TUYENTD_ID:{
          required,
        },
        SODOIDAY: {
          required,
          minValue: minValue(1),
          maxValue: maxValue(99999)
        },
        LOAICAP_ID: {
          required
        },
        CHIEUDAI:{
          required,
          minValue: minValue(1),
          maxValue: maxValue(99999)
        },
      }
  },
}
</script>

<style>
.disable-btn:hover {
  background-color: white !important;
}

.disable-btn a {
  color: #6c757d !important;
}

.disable-btn a:hover {
  cursor: default;
  color: #6c757d;
}
</style>
