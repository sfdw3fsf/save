<template>
    <b-modal
        ref="popupThongTinLienHe"
        size="xl"
        body-class="p-0"
        hide-header
        hide-header-close
        hide-footer
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>THÔNG TIN LIÊN HỆ
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <ThongTinLienHe
                    ref="popupThongTinLienHe"
                    :khachhang_id="khachhang_id"
                    :hdkh_id="hdkh_id"
                />
                    <!-- :ten_kh="ten_kh"
                    :diachi_kh="diachi_kh" -->
            </div>
        </div>
    </b-modal>
</template>

<script>
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue';

export default {
    props: ['khachhang_id', 'hdkh_id', 'ten_kh', 'diachi_kh'],
    components: {
        ThongTinLienHe
    },
    methods: {
        showModal() {
            this.$refs.popupThongTinLienHe.show();
        },
        hideModal() {
            this.$refs.popupThongTinLienHe.hide();
        }
    }
}
</script>
