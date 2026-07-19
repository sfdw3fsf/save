<template>
    <b-modal
        ref="popupFrmThayDoiTsKTR"
        size="xl"
        body-class="modal-body p-0 full-screen-modal"
        scrollable
        hide-footer
        >
        <FrmThayDoiTsKTR :isPopup="true" :ma_tb = "ma_tb" :row_id="row_id" :propDichvuvt_id = "dichvuvt_id" @form-close="formClose" />
    </b-modal>
</template>
<script>
import FrmThayDoiTsKTR from '@/modules/contract/setup/ChangeTSLInfor/FrmThayDoiTsKTR'
export default {
    name:'FrmThayDoiTsKTRModal',
    components:{
        FrmThayDoiTsKTR 
    },
     props:{
        ma_tb:{
            type:String,
            default:'' 
        },
        dichvuvt_id:{
            type:Number,
            default: 0 
        },
        row_id:{
          type:String,
          default:''
        }
     },
    methods:{ 
        showModal() {
            setTimeout(()=>{
                this.$refs['popupFrmThayDoiTsKTR'].show()
            }, 500)
            
        },
        hideModal() {
            this.$refs['popupFrmThayDoiTsKTR'].hide()
        },
        formClose(value){
            this.$emit('form-close',value) 
            this.hideModal() 
        } 
    }
}
</script>
<style scoped>
.full-screen-modal {
    max-width: 1340px !important;
}