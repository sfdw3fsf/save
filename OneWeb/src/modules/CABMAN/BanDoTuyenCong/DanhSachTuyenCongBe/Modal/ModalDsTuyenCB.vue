<template src="./ModalDsTuyenCB.html"></template>
<script>
export default {
    name: "ModalDsTuyenCB",
    props: ["modalId"],
    computed: {},
    created: async function () {
        await this.getTTVT();
    },
    watch:{
        isTuyen: function(val){
            if(val)
                this.onChangeToQL()
        }
    },
    methods: {
        focusMyElement: async function () {
            try{
                this.$root.showLoading(true)
                await this.getTTVT();
            }catch(er){}
            finally{
                this.$root.showLoading(false)
            }
        },
        getTTVT: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-ttvt-theo-nhanvien"
                );
                this.dsTTVT = rs.data;
            } catch (error) {
            } finally {
            }
        },
        getToQL: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-to-kythuat-theo-nhanvien/" +
                    this.dataSelected.ttvt
                );
                this.dsToQL = rs.data;
            } catch (error) {
            } finally {
            }
        },
        getdsTuyenCong: async function () {
            try {
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/ds-tuyen-cong/" +
                    this.dataSelected.toql
                );
                this.dsTuyenCong = rs.data
            } catch (error) {
            } finally {
            }
        },
        onChangeTTVT: async function () {
            await this.getToQL();
        },
        onChangeToQL: async function () {
            await this.getdsTuyenCong();
        },
        grid_selectedRowChanged: async function(dataItem){
            this.tuyenId= dataItem.TUYENCB_ID;
            this.tuyenSelected =  dataItem;
        },
        timKiem:async function(){
            try {

                this.$root.showLoading(true)
                let data={
                    "tuKhoa":this.querys
                }
                let rs = await this.$root.context.get(
                    "/web-cabman/BanDoTuyenCong/tra-cuu-tuyen-cong-be/", data
                );
                this.dsTuyenCong = rs.data
            } catch (error) {
            } finally {
                this.$root.showLoading(false)
            }
        },
        onChangeTuyen:async function(){

        },
        chonTuyen:async function(){
            this.$emit('xacnhan',this.tuyenSelected);
            this.$bvModal.hide(this.modalId)
        },

        huybo:async function(){
            this.tuyenId = null
        }

    },
    data:function() {
        return {
            dsTTVT: [],
            dsToQL: [],
            dsTuyenCong:[],
            dataSelected:{
                ttvt:0,
                toql:null,
                tuyen_id:null
            },
            isTuyen:false,
            querys:"",
            tuyenId:0,
            tuyenSelected:null
        };
    }
};
</script>
<style>
div#popupDsCot___BV_modal_outer_{
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
