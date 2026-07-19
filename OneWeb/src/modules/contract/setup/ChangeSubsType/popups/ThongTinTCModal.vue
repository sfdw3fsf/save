<template>
    <b-modal
        ref="popupThongTinTC"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
    >
        <popupThongTinTC :dulieu="dulieu" @form-close="formClose"/>
    </b-modal>   
</template>
<script>
import popupThongTinTC from '../../InstallNewSubs/Popups/popupThongTinTC.vue'
export default {
    name:'ThongTinTCModal',
    props:[ 'dulieu' ],
    components:{
        popupThongTinTC
    },
    methods:{
        showModal() {
            this.$refs["popupThongTinTC"].show()
        },
        hideModal() {
            this.$refs["popupThongTinTC"].hide()
        },
        formClose(value){
            this.$emit('form-close', value)
            this.hideModal()
        }
    }
}
</script>