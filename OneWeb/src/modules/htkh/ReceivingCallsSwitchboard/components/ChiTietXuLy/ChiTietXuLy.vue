<template src="./ChiTietXuLy.html"></template>
<style scoped src="./ChiTietXuLy.scss"></style>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../ReceivingCallsSwitchboardApi.js";
export default {
    name:'ChiTietXuLy',
    props:{ },
    components:{
        ActionTop,
    },
    data(){
        return {
        startDate: '',
        endDate: '',
        data_id: '',
        DS_XuLy:[],
        }
    },
    methods:{
        showModal() {
            this.$refs["ChiTietXuLyModal"].show()
        },
        hideModal() {
            this.$refs["ChiTietXuLyModal"].hide()
        },
        async handleShowModal(){
            this.LayDS_XuLy_CSKH()              
        },
        async LayDS_XuLy_CSKH(){
            this.loading(true)
            var input = {
                ds_nhanvien: [ '-1' ],
                startDate: this.startDate,
                endDate: this.endDate,
                data_id: this.data_id
            }
            let data = this.GetDataList(await api.sp_LayDS_XuLy_CSKH(this.axios,input));
            this.DS_XuLy = data
            console.log(this.DS_XuLy)
            this.loading(false)
        },
        GetDataList: function (response) {
            console.log(response)
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
            }
            return []
        },
    }
}
</script>
