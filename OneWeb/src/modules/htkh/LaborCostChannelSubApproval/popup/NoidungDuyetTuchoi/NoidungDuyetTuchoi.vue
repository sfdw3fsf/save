
<template src="./NoidungDuyetTuchoi.html"></template>

<style scoped src="./NoidungDuyetTuchoi.scss"></style>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import api from "../../api/index.js";
import moment from 'moment'
export default {
    name: 'NoidungDuyetTuchoi',
    components: {
        ActionTop,
    },
    props: {
        title: {
            type: String,
            default: ""
        }, 
        labelInput: {
            type: String,
            default: ""
        }, 
        loai_xacnhan: {
            type: Number,
            default: 1
        }, 
    },
    data() {
        return {
            noidung: "",
            lydo: "",
            items: {},
            noidung: "",
            nguoiduyet:"",
        }
    },
    mounted() {
    },
    methods: {
        showModal() {
            this.$refs["NoidungDuyetTuchoiModal"].show()
        },
        hideModal() {
            this.$refs["NoidungDuyetTuchoiModal"].hide();
            this.lydo = "";
            this.noidung = ""
        },
        async handleShowModal() {
        },
         async Ghilai_click() {
            try {
                this.loading(true);
                this.$emit("xacnhan",this.noidung,this.loai_xacnhan);
                this.hideModal();
            } catch (error) {
                this.$toast.error('Lỗi nhập nội dung!');
                return false;
            } finally {
                this.loading(false);
            }
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
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        },
    }
}
</script>
