<template src="./index.html">
</template>

<script>
export default {
    props: {
        modalId: String,
        data: {
            type: Object,
            default: function() {
                return {
                    DV_QHNS: null,
                    MACHUONG: null,
                    MN_KT: null,
                    MA_NDKT: null,
                    MN_NSNN: null,
                    TIEN_DM: null
                }
            }
        }
    },
    data: function() {
        return {
            txtDonViQHNS: null,
            txtMaChuong: null,
            txtMaNganhKT: null,
            txtMaNDKT: null,
            txtMaNguonNSNN: null,
            txtTienDM: null
        }
    },
    methods: {
        showModal: function() {
            this.txtDonViQHNS = this.data.DV_QHNS;
            this.txtMaChuong = this.data.MACHUONG;
            this.txtMaNganhKT = this.data.MN_KT;
            this.txtMaNDKT = this.data.MA_NDKT;
            this.txtMaNguonNSNN = this.data.MN_NSNN;
            this.txtTienDM = this.data.TIEN_DM;
        },
        chapNhan: function() {
            if(this.txtTienDM) {
                if(isNaN(this.txtTienDM)) {
                    this.$root.toastError("Dữ liệu nhập phải là kiểu số");
                    return;
                }
            }
            this.$emit('chapNhan', {
                DV_QHNS: this.txtDonViQHNS,
                MACHUONG: this.txtMaChuong,
                MN_KT: this.txtMaNganhKT,
                MA_NDKT: this.txtMaNDKT,
                MN_NSNN: this.txtMaNguonNSNN,
                TIEN_DM: this.txtTienDM
            })
            this.$bvModal.hide(this.modalId);
        }
    }
}
</script>
