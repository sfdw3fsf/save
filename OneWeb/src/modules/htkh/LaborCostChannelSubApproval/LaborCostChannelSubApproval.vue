
<template src="./LaborCostChannelSubApproval.html"></template>
<style scoped src="./LaborCostChannelSubApproval.scss"></style>
<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import HosoFile from "./popup/HosoFile/HosoFile.vue";
import NoidungDuyetTuchoi from "./popup/NoidungDuyetTuchoi/NoidungDuyetTuchoi.vue";
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import XLSX from "xlsx";
import moment from 'moment'
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        KRequiredMarker,
        HosoFile,
        NoidungDuyetTuchoi
    },
    name: 'LaborCostChannelSubApproval', 
    async created() {
    },
    data() {
        return {
            dateFormat: 'dd/MM/yyyy',
            isDisabled: true,
            items: {
                donvi_ql:0,
                ma_tap:"",
                donvi_bh: 0,
                ma_kh:""

            },
            header: {
                title: "Phê duyệt dữ liệu thuê bao kênh truyền tính cước nhân công",
                list: [{
                        name: "Nghiệm vụ quản lý",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Kênh truyền",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            DsThuebaos: [],
            dataDonviQLs: [],
            dataDonviBHs: [],
            taxId: "",
            title:"",
            labelInput:"",
            loai_xacnhan:0,
            ds_tcnc_id: ""
        }
    },
    mounted() {
        this.getDsDonvi();
    },
    methods: {
        async btnTracuuClick(){
            this.getDsThuebao();
        },
        async Pheduyet_click(){
            //Kiểm tra file VB đã được up chưa..nếu rồi mới cho phê duyệt
            let input1 = {
                    p_ma_tap: this.items.ma_tap
            }
            let data = this.GetDataList(await api.sp_lay_ds_file_tinhcuoc_nhancong(this.axios,input1));
            if(data.length == 0){
                this.$root.$toast.error('Vui lòng upload file văn bản đã được phê duyệt')
                return;
            }
            this.$refs.NoidungDuyetTuchoiModal.showModal();
            this.title = "Nhập nội dung phê duyệt"
            this.labelInput = "Nội dung"
            this.loai_xacnhan = 1 // Phê duyệt
        },
        Tuchoi_click(){
            this.$refs.NoidungDuyetTuchoiModal.showModal();
            this.title = "Nhập lý do từ chối"
            this.labelInput = "Lý do"
            this.loai_xacnhan = 0 // Từ chối
        },
        async Pheduyet(noidung,loai_xacnhan){
            
            let input =  { "p_ds_tcnc_id":this.ds_tcnc_id, "p_duyet": loai_xacnhan==1?1:0, "p_noidung_duyet":noidung }
            let rs = await api.sp_setduyet_tinhcuoc_nhancong(this.axios,input);
            if(rs.data.error_code == 'BSS-00000000' && rs.data.error == 200){
                if(loai_xacnhan==1){
                    this.$root.$toast.success("Phê duyệt thành công!");
                }else{
                    this.$root.$toast.success("Từ chối thành công!");
                }
                this.getDsThuebao();
            }else{
                this.$toast.error("Có lỗi : " + rs.data.message);
            }
        },
        async XuatDL_click() {
            let input =  { "p_ma_tap":this.items.ma_tap, "p_trangthai_id": 3 }
            let rs = await api.sp_chuyen_trangthai_tinhcuoc_nhancong(this.axios,input);
            if(rs.data.error_code !== 'BSS-00000000' && rs.data.error !== 200){
                this.$toast.error(rs.data.message_detail);
                return;
            }
            let data = XLSX.utils.json_to_sheet(this.DsThuebaos, { skipHeader: false });
            let wb = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(wb, data, 'DsThuebaoGrid');
            XLSX.writeFile(wb, 'Danh sách thuê bao.xlsx');
        },
        async UploadVB_Click() {
            this.$refs.HosoFileModal.showModal();
        },
        txtMatap_KeyPress(){
            this.getDsThuebao();
        },
        async DsThuebaoSelected(args) {
            this.ds_tcnc_id = ""
             if (args != null) {
                console.log('args.data', args.data)
                const selectedRows = this.$refs.DsThuebaoGrid.getSelectedRecords();
                const idArray = selectedRows.map(row => row.tcnc_id);
                const idString = idArray.join(',');
                this.ds_tcnc_id = idString
                this.items.ma_tap = selectedRows[0].ma_tap
                console.log(this.items.ma_tap)
            }
        },
        async getDsDonvi(){
            let input =  {
                "vdonvi_id":0
            }
            let res = await api.lay_ds_pbh_tvdt(this.axios,input);
            this.dataDonviQLs = res.data.data;
            this.dataDonviQLs.push({
                donvi_id: 0,ten_dv:"0-Tất cả"
            })
            //this.items.donvi_ql = res.data.data[0].donvi_id
            this.dataDonviBHs = res.data.data;
        },
         async getDsThuebao(){
            this.loading(true);
            let param =  {
                "DONVI_ID":this.items.donvi_ql,
                "DONVI_BH_ID":this.items.donvi_bh,
                "MA_TAP":this.items.ma_tap,
                "MA_KH":this.items.ma_kh
            }
            let input = {
                "p_options": JSON.stringify(param),
            }
            let res = await api.sp_lay_ds_duyet_tinhcuoc_nhancong(this.axios,input);
            this.DsThuebaos = res.data.data;
            this.loading(false);
        },
        async txtMaKH_KeyPress(){
            this.getDsThuebao();
        },
        GetDataList: function (response) {
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
        }
    }
}
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
}
.pd-l0{
    padding-left: 0px !important;
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>