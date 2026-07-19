<template>
    <b-modal
        ref="popupThongTinEmail"
        size="xl"
        title="Thông tin Email"
        no-close-on-backdrop
        hide-footer
        body-class="p-0"
    >
        <ThongTinEmail :hdkh_id="hdkh_id" :khachhang_id="khachhang_id"/>
    </b-modal>
</template>
<script>
//clone: https://scm.devops.vnpt.vn/erp.ptpm1.onebss/OneWeb/-/tree/develop/src/modules/contract/setup/ChangeContractOwner
import ThongTinEmail from '../../ChangeContractOwner/popup/ThongTinEmail.vue'
export default {
    name:'EmailModal',
    props: {
        hdkh_id:{
            type:Number,
            default: 0
        },
        khachhang_id:{
            type:Number,
            default:0
        },        
    },
    components:{
        ThongTinEmail
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs["popupThongTinEmail"].show()
        },
        hideModal() {
            this.$refs["popupThongTinEmail"].hide()
        }
    }
}
</script>