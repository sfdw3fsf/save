<template>
    <b-modal
        ref="popupDSTBLapKem"
        size="xl"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
    >
        <popupDSTBLapKem :dulieu="dulieu" @form-close="formClose"/>
    </b-modal>   
</template>
<script>
import popupDSTBLapKem from '../../InstallNewSubs/Popups/popupDSTBLapKem.vue'
export default {
    name:'DSTBLapKemModal',
    props:[ 'dulieu' ],
    components:{
        popupDSTBLapKem
    },
    methods:{
        showModal() {
            this.$refs["popupDSTBLapKem"].show()
        },
        hideModal() {
            this.$refs["popupDSTBLapKem"].hide()
        },
        formClose(value){
            this.$emit('form-close', value)
        }
    }
}
</script>