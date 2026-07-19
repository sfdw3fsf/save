<template>
    <b-modal
        ref="popupSearchAccount"
        size="xl"
        body-class="p-0"
        no-close-on-backdrop
        hide-header-close
        hide-header
        scrollable
        hide-footer>
        <SearchAccount ref="searchAccount" :isPopup="true" @form-close="formClose" />
    </b-modal>
</template>
<script>
import SearchAccount from '../../../../search/subscriber/SearchAccount/SearchAccount.vue'
export default {
    name:'SearchAccountModal',
    components:{
        SearchAccount
    },
    methods:{
        showModal() {
            this.$refs['popupSearchAccount'].show()
        },
        hideModal() {
            this.$refs['popupSearchAccount'].hide()
        },
        formClose(value){
            this.$emit('form-close',value)
            this.hideModal()
        },
        ThemDieuKienTraCuu(tieuchi_id, value){
            try{
                this.$refs.searchAccount.listbox.dk_tracuu.dk1.value=tieuchi_id
                this.$refs.searchAccount.listbox.dk_tracuu.dk1.value_text=value
            }catch(e){

            }
        }
    }
}
</script>
