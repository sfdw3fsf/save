<template src="./DocumentManagement.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
import XLSX from 'xlsx'
import ImportDocument from './components/ImportDocument/ImportDocument.vue';
import ExecuteDocument from './components/ExecuteDocument/ExecuteDocument.vue';
import ExecuteLabor from './components/ExecuteLabor/ExecuteLabor.vue';
import ExecuteCTMinus from './components/ExecuteCTMinus/ExecuteCTMinus.vue';
import ExecutePositiveVue from './components/ExecutePositive/ExecutePositive.vue';
import Report from './components/Report/Report.vue';
export default{
    name: "DocumentManagement",
    components:{
        breadcrumb,
        ActionTop,
        moment,
        api,
        apiHelper,
        ImportDocument,
        ExecuteDocument,
        ExecuteLabor,
        ExecuteCTMinus,
        ExecutePositiveVue,
        Report,
    },
    data(){
        return {
            currentTab: 1,
            seen1: true,
            seen2: false,
            seen3: false,
            seen4: false,
            seen5: false,
            seen6: false,
            header: {
                title: "Quản lý chứng từ",
                list: [
                    {
                        name: "Quản lý chứng từ", link: { name: "Ui.cards"}
                    },
                ]
            },
            actions1: [
                {
                    id: "laytt",
                    name: "Lấy TT",
                    active: false,
                    icon_class: "icon one-file-attach1"
                },
                {
                    id: "ghilai",
                    name: "Ghi lại",
                    active: false,
                    icon_class: "one-save"
                },
                {
                    id: "lfctmau",
                    name: "Lấy File CT mẫu",
                    active: true,
                    icon_class: "one-excel"
                },
                {
                    id: "cnvcs",
                    name: "Chuyển NVCS",
                    active: false,
                    icon_class: "one-file-check3"
                },
                {
                    id: "xcttk",
                    name: "Xem chi tiết thống kê",
                    active: false,
                    icon_class: "one-file-noti"
                },
            ],
            actions2: [

            ],
            actions3: [

            ],
            actions4: [

            ],
            actions5: [

            ],
            actions6: [

            ],
            DsCTTF: {
                columns: [
                    {
                        fieldName: "stt", headerText: "STT", textAlign:"center",width: "100"
                    },
                    {
                        fieldName: "ma_nh", headerText: "Mã ngân hàng", textAlign:"center",width: "100"
                    },
                    {
                        fieldName: "ngay_ct", headerText: "Ngày ngân hàng"
                    },
                    {
                        fieldName: "tien_ct", headerText: "Tiền ghi có"
                    },
                    {
                        fieldName: "nd_ct", headerText: "Nội dung chứng từ"
                    },
                    {
                        fieldName: "ten_trangthai", headerText: "Trạng thái kiểm tra"
                    },
                ],
                data: [],
            },
            DsCTIP: {
                columns: [
                    {
                        fieldName: "ct_id", headerText: "CT_ID", textAlign: "center", width: "100"
                    },
                    {
                        fieldName: "ma_ct", headerText: "Mã chứng từ"
                    },
                    {
                        fieldName: "ma_nh", headerText: "Mã ngân hàng"
                    },
                    {
                        fieldName: "ngay_ct", headerText: "Ngày ngân hàng"
                    },
                    {
                        fieldName: "tien_ct", headerText: "Tiền ghi có"
                    },
                    {
                        fieldName: "nd_ct_ch", headerText: "Nội dung chứng từ"
                    },
                    {
                        fieldName: "ten_trangthai", headerText: "Trạng thái chứng từ"
                    },
                ],
                data: [],
            },
            tenFile: "",
            // checkDataImport: false,
            DataFileExcel: [],
            upload_id: "",
        }
    },
    watch:{},
    methods: {
        // changeTab(tab){
        //     if(tab == 1){
        //         this.currentTab = tab
        //         this.seen1 = true
        //         this.seen2 = false
        //         this.seen3 = false
        //         this.seen4 = false
        //         this.seen5 = false
        //         this.seen6 = false
        //     }
        //     else if(tab == 2){
        //         this.currentTab = tab
        //         this.seen1 = false
        //         this.seen2 = true
        //         this.seen3 = false
        //         this.seen4 = false
        //         this.seen5 = false
        //         this.seen6 = false

        //     }
        //     else if(tab == 3){
        //         this.currentTab = tab
        //         this.seen1 = false
        //         this.seen2 = false
        //         this.seen3 = true
        //         this.seen4 = false
        //         this.seen5 = false
        //         this.seen6 = false

        //     }
        //     else if(tab == 4){
        //         this.currentTab = tab
        //         this.seen1 = false
        //         this.seen2 = false
        //         this.seen3 = false
        //         this.seen4 = true
        //         this.seen5 = false
        //         this.seen6 = false
        //     }
        //     else if(tab == 5){
        //         this.currentTab = tab
        //         this.seen1 = false
        //         this.seen2 = false
        //         this.seen3 = false
        //         this.seen4 = false
        //         this.seen5 = true
        //         this.seen6 = false
        //     }
        //     else if(tab == 6){
        //         this.currentTab = tab
        //         this.seen1 = false
        //         this.seen2 = false
        //         this.seen3 = false
        //         this.seen4 = false
        //         this.seen5 = false
        //         this.seen6 = true
        //     }
        // },
        onActionClick(action){
            switch(action.id){
                case "laytt":
                    this.LayTT()
                    break;
                case "ghilai":
                    this.GhiLai()
                    break;
                case "lfctmau":
                    this.LayFileMau()
                    break;
                case "cnvcs":
                    this.ChuyenNVCS()
                    break;
                case "xcttk":
                    this.XemCTThongKe()
                    break;
                default:
                    break;
            }
        },
        async importExcel(event) {
            const file = event.target.files ? event.target.files[0] : null
            if (file) {

            this.$root.showLoading(true);
            this.tenFile = file.name;
            const reader = new FileReader();

            reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })

            if (!this.checkColumnExcel(data[0])) {
                this.$root.showLoading(false)
                this.DsCTIP.data = []
                this.DsCTTF.data = []
                this.actions1[1].active = false
                return this.$toast.error('File không đúng cấu trúc: STT, MA_NH, NGAY_NH, TIEN_GHICO, ND_CT hoặc không đúng định dạng File Excel')
                }
    
                this.$root.showLoading(true)
                this.DataFileExcel = [];
                // this.checkDataImport = true;
                for (let index = 0; index < data.length - 1; index++) {
                    let r = {}
                    let keys = data[0]
                    let values = data[index + 1]
                    for (let i = 0; i < keys.length; i++) {
                        r[keys[i]] = values[i]
                        
                    }
                    const item  = this.checkDataExcel(r);
                    this.DataFileExcel.push(item);
                }
                
                //enable button Ghi lai
                if(this.DataFileExcel.length <= 0){
                    this.DsCTIP.data = []
                    this.DsCTTF.data = []
                    this.actions1[1].active = false
                    this.$toast.error("Không có dữ liệu trong File !")
                    this.$root.showLoading(false)
                    return
                } 
                if(this.DataFileExcel.length > 0){
                    this.Doc_File()
                }
                
                this.$root.showLoading(false)
            }
        

            reader.readAsBinaryString(file)
        } else {
            return
        }
        },

        // check xem đủ tên cột hay chưa
        checkColumnExcel(dataNameTable) {
            if (dataNameTable) {
                return (
                dataNameTable.includes('STT') &&
                dataNameTable.includes('MA_NH') &&
                dataNameTable.includes('NGAY_NH') &&
                dataNameTable.includes('TIEN_GHICO') &&
                dataNameTable.includes('ND_CT')
                )
            }
            return false
        },
        checkDataExcel(item) {
            const isEmpty = (val) => {
                return (val === undefined || val == null || val.length <= 0 || val === ""  ) ? true : false;
            }
            item["KET_QUA"] = 1;
            if ((
                isEmpty(item['STT']) || 
                isEmpty(item['MA_NH']) ||
                isEmpty(item['NGAY_NH']) ||
                isEmpty(item['TIEN_GHICO']) ||
                isEmpty(item['ND_CT'])
            )){
                item["KET_QUA"] = 0;
                // this.checkDataImport = false;
            }
            return item;
        },
        uploadFile() {
            this.$refs.fileInput.click();
        },
        async Doc_File(){
            this.loading = true
            try{
                if(this.DataFileExcel.length > 0){
                    this.DataFileExcel = this.DataFileExcel.map((e) => ({
                        ma_nh: e.MA_NH,
                        tien_ct: e.TIEN_GHICO,
                        ngay_ct: e.NGAY_NH,
                        noidung: e.ND_CT,

                    }))
                    let input = {
                        p_data : JSON.stringify(this.DataFileExcel)
                    }
                    const response = await api.Doc_File(this.axios,input)
                    let data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(data.includes("OK")){
                        this.upload_id = ""
                        this.$toast.success("Lấy thông tin upload file thành công!")
                        
                        data = data.split(":")
                        this.HienThiThongTinSauKhiImport(data[1])
                        this.upload_id = data[1]
                        
                    }
                    else{
                        this.$toast.info("Lấy thông tin upload file thất bại!")
                    }
                }
                else{
                    this.$toast.error("Chưa Upload File!")
                }
            }
            catch(e){
                
            }
            finally{
                this.loading = false
            }
        },
        async HienThiThongTinSauKhiImport(upload_id){
            this.loading = true
            try{
                let input = {
                    upload_id: upload_id
                }
                const response = await api.Get_DS_Doc_File(this.axios,input)
                this.DsCTTF.data = apiHelper.getDataFromResponseApiReturnArray(response) || []

                for(const item of this.DsCTTF.data){
                    if(item['trangthai'] != 1){
                        item['ten_trangthai'] = "NOK" + " - " + item['ten_trangthai']
                    }
                }
                let checkData = this.DsCTTF.data.filter((e) => ( e.trangthai == 1))
                if(checkData.length == this.DsCTTF.data.length){
                    this.actions1[1].active = true
                }
                else{
                    this.actions1[1].active = false
                }

            }
            catch(e){}
            finally{
                this.loading = false
            }
        },
        async GhiLai(){
            this.loading = true
            try{
                if(this.DsCTTF.data.length > 0){
                    let input = {
                        upload_id: this.upload_id
                    }
                    const response = await api.Upload_File(this.axios,input)
                    let data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                    if(data.includes("OK")){
                        this.$toast.success("Cập nhật chứng từ thành công !")
                        this.HienThiThongTinSauKhiUpload(this.upload_id)
                        this.upload_id = ""
                        this.tenFile = ""
                        this.actions1[1].active = false
                    }
                    else{
                        let msgError = apiHelper.getSrtCodeError(response)
                        this.$toast.error(msgError)
                    }
                }
                else{
                    this.$toast.error("Không có dữ liệu để xử lý!")
                }
            }
            catch(e){}
            finally{
                this.loading = false
            }

        },
        async HienThiThongTinSauKhiUpload(upload_file_id){
            this.loading = true
            try{
                let input = {
                    upload_id : upload_file_id
                }
                const response = await api.Get_DS_Upload_File(this.axios,input)
                this.DsCTIP.data = apiHelper.getDataFromResponseApiReturnArray(response) || []
            }
            catch(e){}
            finally{
                this.loading = false
            }
        },
        LayFileMau(){
            window.location.href = '/static/files/Mau_file_CT.xlsx'
        },
        LayTT(){
            this.$toast.success("Lấy thông tin")
        },
        ChuyenNVCS(){
            this.$toast.success("Chuyển NVCS")

        },
        XemCTThongKe(){
            this.$toast.success("Xem CT thống kê")
        }
    }
}
</script>

<style scoped>

</style>