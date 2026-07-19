<template src="./LichSuPheDuyet.html"></template>
<style scoped src="./LichSuPheDuyet.scss"></style>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "@/modules/contract/setup/ChangeCustomerInfor/API.js";
export default {
    name:'LichSuPheDuyet',
    props: ['dulieu'],
    components:{
        ActionTop,
    },
    data(){
        return {
            DS_LichSu: []
        }
    },
    mounted() {
        console.log(this.dulieu)
        if (this.dulieu && this.dulieu.hdkh_id) {
            this.LayDS_LichSu(this.dulieu.hdkh_id)
        }
    },
    watch: {
        'dulieu.hdkh_id'(val) {
            if (val) {
                this.LayDS_LichSu(val)
            }
        }
    },
    methods:{
        showModal() {
            // this.$refs["LichSuPheDuyetModal"].show()
        },
        hideModal() {
            // this.$refs["LichSuPheDuyetModal"].hide()
            this.$emit("form-close", {})
        },
        async handleShowModal(){
            // this.LayDS_LichSu()              
        },
        async LayDS_LichSu(hdkh_id){
            this.loading(true)            
            let data = this.GetDataList(await api.sp_lay_lichsu_pheduyet(this.axios, hdkh_id));
            this.DS_LichSu = data
            console.log(this.DS_LichSu)
            this.loading(false)
        },
        GetDataList: function (response) {
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return []
        },
    }
}
</script>
