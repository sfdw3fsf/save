<template>
    <b-modal
        ref="dlgTraCuuDanhBa"
        size="xl"
        body-class="p-0"
        scrollable
        hide-footer>
        <SearchAccount :isPopup="true" @form-close="formClose" />
    </b-modal>
</template>
<script>
import SearchAccount from '../../../search/subscriber/SearchAccount/SearchAccount.vue'
export default {
    name:'PopupTraCuuDanhBa',
    components:{
        SearchAccount
    },
    methods:{
        showModal() {
            this.$refs['dlgTraCuuDanhBa'].show();
        },
        hideModal() {
            this.$refs['dlgTraCuuDanhBa'].hide();
        },
        formClose(value){
            this.$emit('accept',value);
            this.hideModal();
        }
    }
}
</script>
