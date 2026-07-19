<template>
    <b-modal
        ref="popupSearchPromotions"
        size="xl"
        body-class="p-0"
        hide-header
        hide-header-close
        @show="handleShowModal"
        hide-footer
    >
        <FrmChonKhuyenMai @form-close="closeForm" :dulieu="data"/>
    </b-modal>
</template>

<script>
import FrmChonKhuyenMai from '@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai.vue';

export default {
    props: ['data'],
    components: {
        FrmChonKhuyenMai
    },
    created() {
        this.initData();
    },
    methods: {
        initData()
        {
            console.log('aaa ', this.data);
        },
        showModal() {
            this.$refs['popupSearchPromotions'].show();
        },
        hideModal() {
            this.$refs['popupSearchPromotions'].hide();
        },
        handleShowModal() {

        },
        closeForm(e){
            console.log(e);
            this.$emit('dKM', e);
        }
    }
}
</script>

<style>
</style>