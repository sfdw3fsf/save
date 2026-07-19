<template>
    <b-modal ref="popupSearchContract" size="xl" body-class="p-0" hide-header hide-footer>
        <div class="modal-content popup-box">
            <SearchContractModal @accept="acceptSearchContract" :kieu="1" :isPopup="1" ref="searchContractModal"
                :donviId="donviId" :nhanvienId="nhanvienId" :donviDLId="vdonvi_dl_id" :loaihd_id="loai_hd_id"
                :dichvuvt_id="dichvuvt_id" :tthd_id="trangthai_hd" />
        </div>
    </b-modal>
</template>
<script>


import SearchContractModal from "@/modules/search/subscriber/SearchContract/SearchContract.vue";

export default {
    name: 'SearchAccountModal',
    props: {
        donviId: {
            type: Number || String,
            default: 0
        },
        nhanvienId: {
            type: Number || String,
            default: 0
        },
        vdonvi_dl_id: {
            type: Number || String,
            default: 0
        },
        loai_hd_id: {
            type: Number || String,
            default: 0
        },
        dichvuvt_id: {
            type: Number || String,
            default: 0
        },
        trangthai_hd: {
            type: Number || String,
            default: 0
        }

    },
    components: {

        SearchContractModal
    },
    data() {
        return {

        }
    },
    methods: {
        acceptSearchContract(data) {
            if (!data) return;
            this.$emit('accept', data.thuebao)
            this.hideModal()

        },
        showModal() {
            this.$refs['popupSearchContract'].show()
        },
        hideModal() {
            this.$refs['popupSearchContract'].hide()
        },

    },
    mounted() {

    },

}
</script>

