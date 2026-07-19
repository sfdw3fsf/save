<template src="./Modal.html"></template>
<script>
    import axios from 'axios';
    export default {
        name: "Modal",
        props: ["modalId","data"],
        created: async function () {
        },
        watch: {
            range: function () {
                this.value = Math.round((this.range/this.data.chieuDai)*100)
                this.chieuDaiTrai = (Math.round(this.range * 10))/10
                this.chieuDaiPhai =  Math.round((this.chieuDai - this.chieuDaiTrai) * 10)/10
            }
        },
        methods: {
            showModal: async function () {
                try {
                    this.chieuDai = this.data.chieuDai
                    this.range = this.chieuDai/2
                    this.value = 50
                    this.chieuDaiTrai = this.range
                    this.chieuDaiPhai = this.range
                    this.beCapId = this.data.beCapId
                    this.doanCongId = this.data.doanCongId
                } catch (error) {
                    this.$toast.error("Có lỗi xảy ra.")
                } finally {
                    //this.$root.showLoading(false);
                }
            },
            xacNhan: async function () {
                try {
                    let data= {
                        beCapId: this.beCapId,
                        doanCongId: this.doanCongId,
                        chieuDaiA: this.chieuDaiTrai,
                        chieuDaiB: this.chieuDaiPhai
                    }
                    let rs = await this.$root.context.post(
                        "/web-cabman/BanDoTuyenCong/chen-be-cap",data
                    )
                    if(rs.error_code == 'BSS-00000000') {
                        this.$toast.success("Chèn bể thành công.")
                        this.$emit('xacNhan')
                        this.$bvModal.hide(this.modalId)
                    } else {
                        this.$toast.success("Chèn bể thất bại.")
                    }

                } catch (error) {
                    //console.log(error.response.data.message_detail)
                    this.$toast.success(error.response.data.message_detail)
                } finally {
                }
            },
        },
        data: function () {
            return {
                chieuDai:null,
                chieuDaiTrai:null,
                chieuDaiPhai:null,
                range:'',
                value: 50,
                beCapId: null,
                doanCongId: null
            };
        },
    };
</script>
<style>
  div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
  }
  .dashboard-header {
    z-index: 9 !important;
  }
  a.disabled {
    pointer-events: none;
    cursor: default;
  }
</style>
