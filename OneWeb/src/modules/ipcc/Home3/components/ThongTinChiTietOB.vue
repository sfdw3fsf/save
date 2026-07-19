<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin chi tiết kết quả OB của khách hàng <span class="current-kh">
                {{ ten_kh }}</span>
        </div>
        <DataGrid :dataSource="tbThongTinKQ.dataSources" :allowPaging="true" :columns="tbThongTinKQ.columns"
            :allowSorting="true" :enablePagingServer="false" />
    </div>
</template>

<script>
import moment from 'moment'
import API from '../API.js'
export default {
    name: 'ThongTinChiTietOB',
    props: {
        phanvung_id:{
            type: Number || String,
            default: 0
        },
        khachhang_id: {
            type: Number || String,
            default: 0,
            require: true
        },
        ma_kh: {
            type: String,
            default: ''
        },
        ten_kh: {
            type: String,
            default: ''
        },

    },

    data() {
        return {
            tbThongTinKQ: {
                columns: [
                    { fieldName: 'ma_tb', headerText: 'Mã TB', width: '100px' },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', width: '100px' },
                    { fieldName: 'manv_cs', headerText: 'Mã NV CS', width: '100px' },
                    { fieldName: 'tennv_cs', headerText: 'Tên NV CS', width: '100px' },
                    { fieldName: 'pbh_cs', headerText: 'Phòng BH CS', width: '100px' },
                    { fieldName: 'trangthai_kn', headerText: 'TT Kết nối', width: '100px' },
                    { fieldName: 'ketqua_ob', headerText: 'Kết quả OB', width: '100px' },
                    { fieldName: 'ghi_chu', headerText: 'Ghi chú', width: '100px' },
                    { fieldName: 'ngay_hen', headerText: 'Ngày hẹn', width: '100px' },
                    { fieldName: 'ngay_hen_tt', headerText: 'Ngày hẹn TT', width: '100px' },
                    { fieldName: 'ngay_ob', headerText: 'Ngày OB', width: '100px' },
                ],
                dataSources: []
            },
        }
    },
    methods: {
        async getListResultOB(vphanvung_id, vkhachhang_id) {

            try {
                this.loading(true)

                let response = await API.getResultOBDetails(this.axios, {vphanvung_id, vkhachhang_id});
                this.tbThongTinKQ.dataSources = this.getDataFromResponseApiReturnArray(response);
                console.log(this.tbThongTinKQ.dataSources);

            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false)
            }
        },
        getDataFromResponseApiReturnArray(response) {
          if (!response) return []
          if (response && response.data && response.data.error_code === 'BSS-00000000') return response.data.data || []
          return []
        }

    },
    async mounted(){
        // this.getListResultOB(this.phanvung_id, this.khachhang_id);
    },


    watch: {
        //khachhang_id
        async khachhang_id(val) {
            if(!val) return;
            await this.getListResultOB(this.phanvung_id, val);
        }



    }

}
</script>
