<template>
  <b-modal
    ref="popupChiTietMuaThietBi"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <popupBuyDevices :dulieu='dulieu' @form-close="closeForm"/>
  </b-modal>
</template>
<script>
//Clone branch thangdm1
import popupBuyDevices from '../../InstallNewSubs/Popups/popupBuyDevices.vue'
export default {
    name:'ChiTietMuaThietBiModal',
    props:['dulieu'],
    components:{
        popupBuyDevices
    },
    data(){
        return {

        }
    },
    methods:{
        showModal() {
            this.$refs["popupChiTietMuaThietBi"].show()
        },
        hideModal() {
            this.$refs["popupChiTietMuaThietBi"].hide()
        },
        handleShowModal(){
            
        },
        closeForm(ds){
            this.$emit('form-close', ds)
            this.hideModal()
        }
    }
}
</script>