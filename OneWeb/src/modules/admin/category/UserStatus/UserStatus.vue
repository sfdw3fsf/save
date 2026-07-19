<template src="./UserStatus.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import moment from "moment";
import api from "./api/index.js";
import DataGridCustom from "./components/DataGrid";
import collums from "./define/collums.js";

export default {
    name:'UserStatus',
    components:{
        breadcrumb,
        DataGridCustom
    },
    data() {
        return {
            header: {
                title: 'Trạng thái người dùng',
                list: [
                    { name: 'Quản trị hệ thống', link: { name: 'Ui.cards' } },
                    { name: 'Trạng thái người dùng', link: { name: 'Ui.cards' } },
                ]
            },
            ...collums,
            ngay_ht: moment(new Date()).format("DD/MM/yyyy"),
            ngay_dang_nhap: null,
            dtList: [],
            dtListDetail: [],
        }
    },
    computed: {

    },
    methods: {
        async init() {
            try {
                this.loading(true);
                this.ngay_dang_nhap = this.ngay_ht;
                $("#chb_dangsudunght").prop("checked", true);
                await this.get_session_list();
            } catch (e) {
                this.$toast.error("Khởi tạo thất bại " + e);
            } finally {
                this.loading(false);
            }        
        },
        async reload() {
            try {
                this.loading(true);
                await this.get_session_list();
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false);
            }
        },
        async get_session_list() {
            this.dtList = [];
            try {
                let chk = document.getElementById("chb_dangsudunght");
                const params = {
                    vngayth: this.ngay_dang_nhap,
                    vtrangthai: chk.checked ? 1 : 0
                }
                let response = await api.ur_005_get_session_list(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.dtList = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
        },
        async get_session_detail(vsession_id) {
            this.dtListDetail = [];
            try {
                const params = {
                    vsession_id: vsession_id
                }
                let response = await api.ur_004_get_session_detail(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.dtListDetail = response.data.data;
                }       
            } catch (e) {
                console.log(e);
            }
        },
        async gridList_rowSelected(data) {
            try {
                this.loading(true);
                let row = data.data;
                if (row.session_id) {
                    await this.get_session_detail(row.session_id);
                } else {
                    this.dtListDetail = [];
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }     
        }
    },
    mounted() {
        this.init();
    },
}
</script>