<template>
    <div class="box-form">
        <div class="legend-title">
            Thông tin chi tiết kết quả OB  <span class="current-kh">
               </span>
        </div>
        <DataGrid :dataSource="tbThongTinKQ.dataSources" :allowPaging="true" :columns="tbThongTinKQ.columns"
            :allowSorting="true" :enablePagingServer="false" panelDataHeight="150px" 
            :allowGrouping="true"
            />
    </div>
</template>

<script>
import moment from 'moment'
import api from '../../API';
import apiHelper from '../../api.helper';
import TDataGrid from './ThongTinTBKH/DataGrid'


export default {
    name: 'ThongTinKetQuaOB',
    components: {
        DataGrid: TDataGrid
    },
    props: {
        phanvung_id:{
            type: Number || String, 
            default: 0
        },
        khachhang_ids: {
            type:  String,
            require: true
        },
    },

    data() {
        return {
            tbThongTinKQ: {
                columns: [
                    { fieldName: 'MA_KH', headerText: "", isGroupedColumn: true, captionTemplate: this.captionTemplate(this)},
                    { fieldName: 'MA_TB', headerText: 'Mã TB', width: '100px' },
                    { fieldName: 'TEN_TB', headerText: 'Tên TB', width: '100px' },
                    { fieldName: 'MANV_CS', headerText: 'Mã NV CS', width: '100px' },
                    { fieldName: 'TENNV_CS', headerText: 'Tên NV CS', width: '100px' },
                    { fieldName: 'TEN_PBH', headerText: 'Phòng BH CS', width: '100px' },
                    { fieldName: 'TRANGTHAI_KN', headerText: 'TT Kết nối', width: '100px' },
                    { fieldName: 'KETQUA_OB', headerText: 'Kết quả OB', width: '100px' },
                    { fieldName: 'GHI_CHU', headerText: 'Ghi chú', width: '100px' },
                    { fieldName: 'NGAY_HEN', headerText: 'Ngày hẹn', width: '100px' },
                    { fieldName: 'NGAYHEN_TT', headerText: 'Ngày hẹn TT', width: '100px' },
                    { fieldName: 'NGAY_OB', headerText: 'Ngày OB', width: '100px' },
                ],
                dataSources: []
            },
        }
    },
    methods: {
        async getListResultOB(vphanvung_id, vkhachhang_id) {

            try {
                this.loading(true)
                // let response = await api.getResultOBDetails(this.axios, {vphanvung_id, vkhachhang_id});

                let response = await api.getListOBHistory(this.axios,vphanvung_id, vkhachhang_id);
                this.tbThongTinKQ.dataSources = apiHelper.getDataFromResponseApiReturnArray(response);
                console.log(this.tbThongTinKQ.dataSources);

            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false)
            }
        },
        captionTemplate: function(parent) {
            return function () {
                return {
                    template: {
                        template:  `<div>{{captionValue}}</div> `,
                        data: function () {
                            return { data: {} }
                        },
                        computed: {
         
                        captionValue: function(value) {
                            const {field, key} = value.data; 
                            if(field == 'MA_KH'){
                                const {items, count} = value.data;
                                const item = items[0];

                                return  `Mã KH: ${item.MA_KH} - ${item.TEN_KH} - ${item.SDT_KH} - ${item.DIACHI_KH}`;
                            }
                                
                        },
                        countOB : function(value){
                            const {field, key} = value.data; 
                            if(field == 'MA_KH'){
                                const {items, count} = value.data;
                                return count;
                            }
                        }
                  },
                  methods: {}
                }
                }
            }
        },

    },
    async mounted(){
        // this.getListResultOB(this.phanvung_id, this.khachhang_id);
    },


    watch: {
        //khachhang_id
        async khachhang_ids(val) {
            if(!val) return;
            await this.getListResultOB(this.phanvung_id, val);
        }  

    }

}
</script>
<style>

.custom-count-ob {
    color: red !important;
    font-weight: bold !important;
    font-size: 14px !important;
}
</style>