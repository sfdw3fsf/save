<template>
    <b-modal
        ref="popupHuongGiao"
        size="xl"
        title="Hướng giao"
        modal-class="huonggiao"
        header-class="popup-header"
        content-class="huonggiao-content"
        body-class="none-padding huonggiao-body"
        no-close-on-backdrop
        hide-footer
    >
        <IntersectionDirection :tk_dichvu_id="tk_dichvu_id" :tk_loaihd_id="tk_loaihd_id" :tk_quytrinh_id="tk_quytrinh_id" :isPopup="true"/>
    </b-modal>   
</template>
<script>
import IntersectionDirection from './IntersectionDirection.vue'
export default {
    name:'IntersectionDirectionModal',
    props:{
        tk_dichvu_id:{
            type:Number,
            default:0
        },
        tk_loaihd_id:{
            type:Number,
            default:0
        },
        tk_quytrinh_id:{
            type:Number,
            default:0
        },
    },
    components:{
        IntersectionDirection
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs['popupHuongGiao'].show()
        },
        hideModal() {
            this.$refs['popupHuongGiao'].hide()
        }
    }
}
</script>
<style>
.huonggiao-body .page-content {
  position: unset !important;
  background: white !important;
}
.huonggiao-body .main-wrapper {
    position: unset !important;
}
</style>