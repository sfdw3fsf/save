<template src="./AccessPlanInformationLookup.html">
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"

export default {
    name: "AccessPlanInformationLookup",
    components: {
        breadcrumb,
        ActionTop,
        moment,
        api,
        apiHelper,
    },
    data(){
        return {
            header: {
                title: "Thông tin kế hoạch tiếp cận",
                list: [
                    {
                        name: "Thông tin kế hoạch tiếp cận", link: { name: "Ui.cards"}
                    },
                ]
            },
            actions: [
                {
                    id: "tracuu",
                    name: "Tra cứu",
                    active: true,
                    icon_class: "one-search"
                },
                {
                    id: "xuatfile",
                    name: "Xuất File",
                    active: true,
                    icon_class: "one-download"
                }
            ],
            cboLoaiNV: {
                text: "Loại nhiệm vụ",
                list: [],
                value: ""
            },
            txtTenCT: {
                text: "Tên chương trình",
                value: ""
            },
            dpTuNgay: {
                text: "Từ ngày",
                value: moment(new Date(),"DD/MM/yyyy").format("DD/MM/yyyy")
            },
            dpDenNgay: {
                text: "Đến ngày",
                value: moment(new Date(),"DD/MM/yyyy").format("DD/MM/yyyy")
            },
            DataLichTCKH:{
                columns: [
                    {
                        fieldName: "1", headerText: "Ngày tiếp cận", allowFiltering: true
                    },
                    {
                        fieldName: "2", headerText: "Mã nhiệm vụ", allowFiltering: true
                    },
                    {
                        fieldName: "3", headerText: "Mã KH", allowFiltering: true
                    },
                    {
                        fieldName: "4", headerText: "Tên KH", allowFiltering: true
                    },
                    {
                        fieldName: "5", headerText: "Địa chỉ KH", allowFiltering: true
                    },
                    {
                        fieldName: "6", headerText: "Người liên hệ", allowFiltering: true
                    },
                    {
                        fieldName: "7", headerText: "SĐT", allowFiltering: true
                    },
                    {
                        fieldName: "8", headerText: "Email LH", allowFiltering: true
                    },
                    {
                        fieldName: "9", headerText: "HT tiếp cận", allowFiltering: true
                    },
                    {
                        fieldName: "10", headerText: "Dịch vụ", allowFiltering: true
                    },
                    {
                        fieldName: "11", headerText: "Loại hình", allowFiltering: true
                    },
                    {
                        fieldName: "12", headerText: "Vật phẩm CS", allowFiltering: true
                    },
                    {
                        fieldName: "13", headerText: "Nội dung tiếp cận", allowFiltering: true
                    },
                ],
                data: [
                    {
                        "1": "1", "2": "2"
                    },
                    {
                        "1": "2"
                    },
                    {
                        "1": "3"
                    }
                ],
            }
        }
    },
    watch: {

    },
    methods: {
        onActionClick(action){
            switch(action.id){
                case "tracuu":
                    this.TraCuu()
                    break;
                case "xuatfile":
                    this.XuatFile()
                    break;
                default:
                    break;
            }
        },
        TraCuu(){
        },
        XuatFile(){
            let data = this.DataLichTCKH.data
            let infoExport = {
                fileName: "Lịch tiếp cận khách hàng.xlsx",
                dataSource: data
            }
            this.$refs.refDataLichTCKH.excelExport(infoExport)
        },
    }
}
</script>

<style scoped>

</style>