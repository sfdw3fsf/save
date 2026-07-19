<template src="./index.html">
</template>

<script>
export default {
    props: {
        id: String,
        data: {
            typeof: Object,
            default: function() {
                return {
                    thuebao_id: -1,
                    dichvuvt_id: 0,
                    ma_tb: null,
                    hdtb_id: -1,
                    madoicap: 0
                }
            }
        }
    },
    data: function() {
        return {
            ma_tb: null,
            thuebao_id: null,
            hdtb_id: null,
            dichvuvt_id: null,
            dsMaDoiCap: []
        }
    },
    methods: {
        showModal: async function() {
            try {
                this.loading(true);
                if(this.data.madoicap != 0) {
                    let rs = await this.$root.context.get(`web-quantri/thuebaocungdoicap/lay_tt_port_tb_cung_madoicap2?madoicap=${this.data.madoicap}`);
                    this.dsMaDoiCap = rs.data;
                }
                else {
                    let rs = await this.$root.context.post('web-quantri/thuebaocungdoicap/lay_tt_port_tb_cung_madoicap', {
                        hdtb_id: this.data.hdtb_id,
                        thuebao_id: this.data.thuebao_id,
                        dichvuvt_id: this.data.dichvuvt_id
                    });
                    this.dsMaDoiCap = rs.data;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        chapNhan: function() {
            if(this.dsMaDoiCap.length == 0) {
                this.$root.toastError('Không có dữ liệu!');
            }
            else {
                this.$emit('chapNhanTBCungDoiCap', {
                    ma_tb: this.ma_tb,
                    thuebao_id: this.thuebao_id,
                    hdtb_id: this.hdtb_id,
                    dichvuvt_id: this.dichvuvt_id
                });
                this.dsMaDoiCap = []
                this.$bvModal.hide(this.id);
            }
        },
        grid_selectedRowChanged: function(data) {
            if(data) {
                this.thuebao_id = data.thuebao_id;
                this.ma_tb = data.ma_tb;
                this.hdtb_id = data.hdtb_id;
                this.dichvuvt_id = data.dichvuvt_id;
            }
        }
    }
}
</script>
