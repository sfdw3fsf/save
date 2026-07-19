<template>
    <b-modal
        ref="popupDSTBCungDoiCap"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
    >
        <popupDSTBCungDoiCap :dulieu="dulieu" @form-close="formClose"/>
    </b-modal>
</template>
<script>
import popupDSTBCungDoiCap from '../../InstallNewSubs/Popups/popupDSTBCungDoiCap.vue'
export default {
    name:'DSTBCungDoiCapModal',
    props:[ 'dulieu' ],
    components:{
        popupDSTBCungDoiCap
    },
    methods:{
        showModal() {
            this.$refs["popupDSTBCungDoiCap"].show()
        },
        hideModal() {
            this.$refs["popupDSTBCungDoiCap"].hide()
        },
        formClose(value){
            this.$emit('form-close', value)
        }
    }
}
</script>