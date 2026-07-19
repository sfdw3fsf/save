<template>
    <b-modal
        ref="popupSearchAccount"
        size="xl"
        body-class="modal-body p-0"
        scrollable
        hide-footer hide-header hide-header-Close
        >
        <SearchAccount :isPopup="true" @form-close="formClose" />
    </b-modal>
</template>
<script>
import SearchAccount from '@/modules/search/subscriber/SearchAccount/SearchAccount.vue'
export default {
    name:'SearchAccountModal_KH',
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
        }
    }
}
</script>
