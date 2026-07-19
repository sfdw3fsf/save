<template>
    <b-modal
        ref="popupChangeCustomerContract"
        size="xl"
        body-class="modal-body p-0 full-screen-modal"
        scrollable
        hide-footer hide-header hide-header-Close
        >
        <ChangeCustomerInfo :propMaTB = "ma_tb" :row_id = "row_id" :propDichvuvt_id = "dichvuvt_id" :isPopup="true" @form-close="formClose" />
    </b-modal>
</template>
<script>
import ChangeCustomerInfo from '@/modules/contract/profile/ChangeCustomerInfo/index.vue'
export default {
    name:'ChangeCustomerInfoModal', 
    components:{
        ChangeCustomerInfo
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
            type: String,
            default: '' 
        },
        
     },
    methods:{
        showModal() {
            this.$refs['popupChangeCustomerContract'].show()
        },
        hideModal() {
            this.$refs['popupChangeCustomerContract'].hide()
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
</style>