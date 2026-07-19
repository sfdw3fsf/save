<template src="./danhmucttd.html"></template>
<script>
import {required} from 'vuelidate/lib/validators'
import BssRequiredMarker from '@/components/BssRequiredMarker'

export default {
  components: {BssRequiredMarker},
  name: 'TuyenTruyenDan',
  props: {
    id: String
  },
  validations: {
    tuyenTruyenDan: {
      required
    }
  },
  data: function (){
    return {
      dsTuyenTruyenDan:[],
      dsLoaiTuyenTD:[],
      cboLoaiTuyenTD:null,
      tenTuyenInput:null,
      maTuyenInput:0,
      rowSelected:null,

      isDisableBtnNhapMoi:false,
      isDisableBtnSua:false,
      isDisableBtnGhiLai:false,
      isDisableBtnHuyBo:false,
      isDisableBtnXoa:false,
      isDisableTenTuyenInput:false,
      isDisableCboLoaiTuyenTD:false
    }
  },
  methods:{
    async onShownModal() {
      //chọn row đầu tiên khi mở popup
      try {
        this.loading(true)
        //status read only
        this.statusBtn(false, false, true, true, false, true, true)
        await this.getDsLoaiTruyenDan()
        await this.getDsTuyenTruyenDan()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    btnNhapMoi(){
      if(!this.isDisableBtnNhapMoi){
        //status nhap moi
        this.statusBtn(true,true,false,false,false,false,false)
        this.clearData()
        setTimeout(() => this.$refs['tenTuyenInput'].focus(), 100)
      }
    },
    btnSua(){
      if (this.isDisableBtnSua) return
      //status sua
      this.statusBtn(true,true,false,false,true,false,false)
      setTimeout(() => this.$refs['tenTuyenInput'].focus(), 100)
    },
    async btnGhiLai(){
      if (!this.isDisableBtnGhiLai){
        //status read only
        this.statusBtn(false,false,true,true,false,true,true)
        try {
          this.loading(true)
          if (this.maTuyenInput === 0){
            //THEM MOI
            let object = new Object()
            object.TUYEN_TD = this.tenTuyenInput
            object.LOAITTD_ID = this.cboLoaiTuyenTD
            if (this.validateData(object)) return
            await this.$root.context.post("/web-suco/mang_bangrong/them-tuyen-truyendan", object)
              .then(res=>{
                if (res.error === "200" && res.error_code === "BSS-00000000"){
                  console.log(res.data)
                  //this.dsTuyenTruyenDan.unshift(res.data)
                  this.getDsTuyenTruyenDan()
                  this.$root.toastSuccess("Thêm tuyến truyền dẫn thành công")
                }
              }).catch(e => this.$root.toastError("Có lỗi xảy ra khi thêm tuyến truyền dẫn"))
          }else {
            // CAP NHAT
            let object = new Object()
            object.TUYENTD_ID = this.maTuyenInput
            object.TUYEN_TD = this.tenTuyenInput
            object.LOAITTD_ID = this.cboLoaiTuyenTD
            if (this.validateData(object)) return
            await this.$root.context.post("/web-suco/mang_bangrong/capnhat-tuyen-truyendan", object)
              .then(res =>{
                if (res.error === "200" && res.error_code === "BSS-00000000"){
                  this.dsTuyenTruyenDan.forEach(item =>{
                    if (item.TUYENTD_ID === object.TUYENTD_ID){
                      item.TUYEN_TD = object.TUYEN_TD
                      item.LOAITTD_ID = object.LOAITTD_ID
                    }
                  })
                  this.dsTuyenTruyenDan = [...this.dsTuyenTruyenDan]
                  this.$root.toastSuccess("Cập nhật tuyến truyền dẫn thành công")
                }
              }).catch(e => this.$root.toastError("Có lỗi xảy ra khi cập nhật tuyến truyền dẫn"))
          }
        }catch (e){
          console.log(e)
        }finally {
          this.loading(false)
        }
      }
    },
    btnHuyBo(){
      if (!this.isDisableBtnHuyBo){
        this.statusBtn(false,false,true,true,false,true,true)
        this.dsTuyenTD_selectedRowChanged(this.rowSelected)
      }
    },
    async btnXoa(){
      if (!this.isDisableBtnXoa){
        this.statusBtn(false,false,true,true,false,true,true)
        this.$bvModal.msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?",
          {
            title: "Xác nhận hành động",
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy',
            centered: true,
            size: "sm"
          }).then((value) => {
          if (value){
            try {
              this.loading(true)
              if (this.rowSelected){
                this.$root.context.post("/web-suco/mang_bangrong/xoa-tuyen-truyendan", {TUYENTD_ID:this.rowSelected.TUYENTD_ID})
                  .then(res=>{
                    if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
                      // this.dsTuyenTruyenDan.forEach(function (item,index){
                      //   if (item.TUYENTD_ID === this.rowSelected.TUYENTD_ID){
                      //     this.dsTuyenTruyenDan.splice(index,1)
                      //   }
                      // })
                      // this.dsTuyenTruyenDan = [...this.dsTuyenTruyenDan]
                      this.getDsTuyenTruyenDan()
                      this.$root.toastSuccess("Xóa thành công.")
                    }
                  }).catch(e =>this.$root.toastError("Có lỗi xảy ra khi xóa."))
              }else this.$root.toastError("Bạn chưa chọn tuyến tryền dẫn.")
            }catch (e){
              console.log(e)
            }finally {
              this.loading(false)
            }
          }
          else{
            return
          }
        })
      }
    },
    dsTuyenTD_selectedRowChanged(item){
      if (item) {
        //status read only
        this.statusBtn(false,false,true,true,false,true,true)
        this.rowSelected = item
        this.maTuyenInput = item.TUYENTD_ID
        this.tenTuyenInput = item.TUYEN_TD
        this.cboLoaiTuyenTD = item.LOAITTD_ID
      }
    },
    async getDsTuyenTruyenDan(){
      this.loading(true)
      await this.$root.context.get(
        "/web-suco/mang_bangrong/ds-tuyen-truyendan",
      ).then(res =>{
        this.dsTuyenTruyenDan = res.data
        if (res.data.length > 0) {
          this.dsTuyenTD_selectedRowChanged(res.data[0])
        }
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    async getDsLoaiTruyenDan(){
      this.loading(true)

      await this.$root.context.get(
        "/web-suco/mang_bangrong/ds-loaituyen-td"
      ).then(res =>{
        this.dsLoaiTuyenTD = res.data
      }).catch(e=> {throw e}).finally(()=> this.loading(false))
    },
    hideModal: function () {
      this.$emit('btnReloadLoaiTuyen')
      this.$bvModal.hide(this.id)
    },
    clearData(){
      this.maTuyenInput = 0
      this.tenTuyenInput = null
      this.cboLoaiTuyenTD = null
    },
    validateData(data){
      this.$v.$touch();
      let errorMessage = []
      if (data.TUYEN_TD == null || data.TUYEN_TD == '') errorMessage.push("Tuyến truyền dẫn không được để trống.")
      if (data.LOAITTD_ID == null) errorMessage.push("Bạn chưa chọn loại tuyến truyền dẫn.")
      if (errorMessage.length > 0) {
        this.$root.toastError(errorMessage.join('\n'))
        return true
      }else {
        return false
      }
    },
    statusBtn(nhapMoi, sua, ghiLai, huyBo, xoa, tenTuyenInput, cboLoaiTuyenTD) {
      this.isDisableBtnNhapMoi = nhapMoi,
      this.isDisableBtnSua = sua,
      this.isDisableBtnGhiLai = ghiLai,
      this.isDisableBtnHuyBo = huyBo,
      this.isDisableBtnXoa = xoa
      this.isDisableTenTuyenInput = tenTuyenInput,
      this.isDisableCboLoaiTuyenTD = cboLoaiTuyenTD
    }
  }
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

