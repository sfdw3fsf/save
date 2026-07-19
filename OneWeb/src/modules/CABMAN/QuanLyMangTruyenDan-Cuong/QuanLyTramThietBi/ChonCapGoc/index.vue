<template src="./index.html">
</template>

<script>
export default {
    props: {
        modalId: {
            type: String,
            default: "modal"
        }
    },
    data: function() {
        return {
            tttv_id: null,
            tokt_id: null,
            tram_id: null,
            nhomcap_id: 0,
            cap_id: -1,

            dsTTVT: [],
            dsTOKT: [],
            dsTRAM: [],
            dsNHOMCAP: [],
            dsCap: [],

            disableBtnChon: true
        }
    },
    methods: {
        showModalChonCapGoc: function() {
            this.tttv_id = null;
            this.tokt_id = null;
            this.tram_id = null;
            this.nhomcap_id = 0;
            this.cap_id = -1;
            this.dsNHOMCAP = [
                {
                    TEN_NHOMCAP: "Cáp đồng",
                    NHOMCAP_ID: 0,
                },
                {
                    TEN_NHOMCAP: "Cáp quang",
                    NHOMCAP_ID: 1,
                }
            ];
            this.NAP_DS_TTVT();
        },
        gridDsCap_selectedRowChanged: function(data) {
            this.cap_id = data.CAP_ID;
        },
        closeModal: function() {
            this.$bvModal.hide(this.modalId);
        },
        NAP_DS_TTVT: async function() {
            try {
                this.loading(true);
                let rs = await this.$root.context.get('/web-cabman/choncapgoc/ds-ttvt');
                this.dsTTVT = rs.data;
                this.tttv_id = this.dsTTVT.length > 0 ? this.dsTTVT[0].DONVI_ID : -1;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        NAP_DS_TOKT: async function(tttv_id) {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`/web-cabman/choncapgoc/ds-tokt-theo-ttvt/${tttv_id}`);
                this.dsTOKT = rs.data;
                this.tokt_id = this.dsTOKT.length > 0 ? this.dsTOKT[0].DONVI_ID : -1;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        NAP_DS_TRAM: async function(tokt_id) {
            try {
                this.loading(true);
                let rs = await this.$root.context.get(`/web-cabman/choncapgoc/ds-tram-theo-tokt/${tokt_id}`);
                this.dsTRAM = rs.data;
                this.tram_id = this.dsTRAM.length > 0 ? this.dsTRAM[0].DONVI_ID : -1;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        HT_DS_CAP: async function() {
            try {
                this.loading(true);
                if(this.tram_id && this.nhomcap_id) {
                    let rs = await this.$root.context.get(`/web-cabman/choncapgoc/ds-cap?tram_id=${this.tram_id}&nhomcap_id=${this.nhomcap_id}`);
                    this.dsCap = rs.data;
                    if(this.dsCap.length > 0) {
                        this.disableBtnChon = false;
                    }
                }
                else {
                    this.dsCap = [];
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            } 
        },
        chon: function() {
            if(this.dsCap.length == 0 || this.cap_id == -1) {
                this.$root.toastError("Chưa chọn cáp gốc.");
            }
            else {
                this.$emit('xacnhan', this.cap_id);
                this.$bvModal.hide(this.modalId);
            }
        },
        huy: function() {
            this.$bvModal.hide(this.modalId);
        }
    },
    mounted: function() {

    },
    watch: {
        tttv_id: function(val) {
            if(val > 0) {
                this.NAP_DS_TOKT(val);
            }
        },
        tokt_id: function(val) {
            if(val > 0) {
                this.NAP_DS_TRAM(val);
            }
        },
        tram_id: function() {
            this.HT_DS_CAP();
        },
        nhomcap_id: function() {
            this.HT_DS_CAP();
        }
    }
}
</script>
