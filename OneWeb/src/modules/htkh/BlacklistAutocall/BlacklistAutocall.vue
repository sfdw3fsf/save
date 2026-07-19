<template src="./BlacklistAutocall.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import moment from 'moment';
import api from './api'
import apiHelper from "./api.helper"
import XLSX from 'xlsx'
import InfoImportVue from './modals/InfoImport.vue';
export default {
    name: "BlacklistAutocall",
    components:{
        breadcrumb,
        ActionTop,
        moment,
        api,
        apiHelper,
        InfoImportVue
    },
    data(){
        return {
            header: {
                title: "Import Blacklist Autocall",
                list: [
                    {
                        name: "Import Blacklist Autocall", link: { name: "Ui.cards"}
                    },
                ]
            },
            actions: [
                {
                    id: "ghilai",
                    name: "Ghi lại",
                    active: false,
                    icon_class: "one-save"
                },
                {
                    id: "themtb",
                    name: "Thêm thuê bao",
                    active: true,
                    icon_class: "Icon one-file-plus"
                },
                {
                    id: "layds",
                    name: "Lấy DS",
                    active: false,
                    icon_class: "one-file-attach"
                },
                {
                    id: "laydsall",
                    name: "Lấy tất cả DS",
                    active: true,
                    icon_class: "one-file-attach"
                },
                {
                    id: "layfilemau",
                    name: "Lấy File mẫu",
                    active: true,
                    icon_class: "one-excel"
                }
            ],
            cboLoai: {
                value: "1",
                list: [
                    {
                        id: "1",
                        text: "Mã khách hàng"
                    },
                    {
                        id: "2",
                        text: "Mã thanh toán"
                    },
                    {
                        id: "3",
                        text: "Mã thuê bao"
                    },
                ]
            },
            cboSuDung: {
                value: "1",
                list: [
                {
                        id: "1",
                        text: "Còn sử dụng"
                    },
                    {
                        id: "0",
                        text: "Không còn sử dụng"
                    },
                ]
            },
            DsSauKhiImport: {
                columns: [
                    {
                        fieldName: "stt", headerText: "STT", width: "60", allowFiltering: true
                    },
                    {
                        fieldName: "MA", headerText: "Mã", width: "80",allowFiltering: true
                    },
                    {
                        fieldName: "LOAI", headerText: "Loại",allowFiltering: true
                    },
                    {
                        fieldName: "SUDUNG", headerText: "Sử dụng",allowFiltering: true
                    },
                    {
                        fieldName: "FILE_NAME", headerText: "Tên File",allowFiltering: true
                    },
                    {
                        fieldName: "NGUOI_CN", headerText: "Người cập nhật",allowFiltering: true
                    },
                    {
                        fieldName: "NGAY_CN", headerText: "Ngày cập nhật",allowFiltering: true
                    },
                ],
                data: []
            },
            txtNhapMa: {
                value: ""
            },
            tenFile: "",
            DataFileExcel: [],
            upload_id: "",
        }
    },
    watch:{
        
    },
    methods:{
        async importExcel(event) {
            this.$root.showLoading(true);
            try{
                const file = event.target.files ? event.target.files[0] : null
                if (file) {
    
                
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
                    this.DsSauKhiImport.data = []
                    this.DataFileExcel = [];
                    this.actions[0].active = false
                    return this.$toast.error('File không đúng cấu trúc: MA, LOAI, SUDUNG hoặc không đúng định dạng File Excel')
                    }
        
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
                        this.DsSauKhiImport.data = []
                        this.$toast.error("Không có dữ liệu trong File !")
                        this.actions[0].active = false
                        this.actions[2].active = false
                        this.tenFile = ""
                        return
                    } 
                    else{
                        let dem = 0
                        let loai = this.DataFileExcel[0].LOAI
                        for(const item of this.DataFileExcel){
                            if(item['LOAI'] == loai){
                                dem ++
                            }
                        }
                        if(dem != this.DataFileExcel.length){
                            this.$toast.error("Chỉ được nhập chung 1 loại mã ! (Cột LOAI trong file)")
                            this.actions[0].active = false
                            this.actions[2].active = false
                            this.tenFile = ""
                        }
                        else{
                            this.$toast.success("Lấy dữ liệu trong file thành công !")
                            this.actions[0].active = true

                        }
                    }
                    // if(this.DataFileExcel.length > 0){
                    //     this.Doc_File()
                    // }
                    
                }
                    reader.readAsBinaryString(file)
                } else {
                    return
                }
            }
            catch(e){
                console.log("Error",e);
                this.DsSauKhiImport.data = []
                this.DataFileExcel = [];
            }
            finally{
                this.$root.showLoading(false);
            }
        },

        // check xem đủ tên cột hay chưa
        checkColumnExcel(dataNameTable) {
            if (dataNameTable) {
                return (
                dataNameTable.includes('MA') &&
                dataNameTable.includes('LOAI') &&
                dataNameTable.includes('SUDUNG')
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
                isEmpty(item['MA']) || 
                isEmpty(item['LOAI']) ||
                isEmpty(item['SUDUNG'])
            )){
                item["KET_QUA"] = 0;
                // this.checkDataImport = false;
            }
            return item;
        },
        uploadFile() {
            this.$refs.fileInput.click();
        },
        onActionClick(action){
            switch(action.id){
                case "ghilai":
                    this.GhiLai()
                    break;
                // case "laytt":
                //     this.LayTT(this.upload_id)
                //     break;
                case "themtb":
                    this.ThemTB()
                    break;
                case "layds":
                    this.LayDS(this.upload_id)
                    break;
                case "laydsall":
                    this.LayDSALL()
                    break;
                case "layfilemau":
                    window.location.href = '/static/files/Mau_file_ImportBlackList.xlsx'
                    break;
                default:
                    break;
            }

        },
        async GhiLai(){
            this.$root.showLoading(true)
            try{
                if(this.$refs.fileInput.value == ""){
                    this.$toast.error("Bạn phải upload file")
                    this.$refs.fileInput.focus()
                }
                else{
                    this.upload_id = ""
                    let dataImport = this.DataFileExcel.map((e)=> ({
                        MA: e.MA,
                        LOAI: e.LOAI,
                        SUDUNG: e.SUDUNG
                    }))
                    dataImport = JSON.parse(JSON.stringify(dataImport))
                    let input = {
                        p_phanvung_id: this.$root.token.getPhanVungID(),
                        p_ds: dataImport,
                        p_loai: this.cboLoai.value,
                        p_filename: this.tenFile,
                        p_ma: this.txtNhapMa.value.trim(),
                        p_nguoi_cn: this.$root.token.getUserName(),
                        p_sudung: this.cboSuDung.value
                        // p_loai: "",
                        // p_filename: this.tenFile,
                        // p_ma: "",
                        // p_sudung: ""
                    }
                    const response = await api.ImportFile(this.axios,input)
                    let msg = apiHelper.getDataFromResponseApiReturnArray(response) || ""
                    if(msg.UPLOAD_ID != ""){
                        this.$toast.success("Import thành công")
                        this.upload_id = msg.UPLOAD_ID
                        this.actions[2].active = true
                    }
                    else{
                        let msgError = apiHelper.getSrtCodeError(response) || ""
                        this.$toast.error(msgError)
                        this.actions[2].active = false
                    }
                }
            }catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        async ThemTB(){
            this.$root.showLoading(true)
            try{
                this.tenFile = ""
                if(this.txtNhapMa.value.trim() == ""){
                    this.$refs.refNhapMa.focus()
                    this.$toast.error("Bạn phải nhập mã !")
                    return
                }
                let p_ds = {
                    MA: this.txtNhapMa.value.trim(),
                    LOAI: this.cboLoai.value,
                    SUDUNG: this.cboSuDung.value
                }
                let arr = []
                arr.push(p_ds)
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID(),
                    p_ds: arr,
                    p_loai: this.cboLoai.value,
                    p_filename: "",
                    p_ma: this.txtNhapMa.value.trim(),
                    p_nguoi_cn: this.$root.token.getUserName(),
                    p_sudung: this.cboSuDung.value
                }
                const response = await api.ImportFile(this.axios,input)
                let msg = apiHelper.getDataFromResponseApiReturnArray(response) || ""
                if(msg.UPLOAD_ID != ""){
                    this.$toast.success("Thêm thuê bao thành công")
                    this.upload_id = msg.UPLOAD_ID
                    this.actions[2].active = true
                }
                else{
                    let msgError = apiHelper.getSrtCodeError(response) || ""
                    this.$toast.error(msgError)
                    this.actions[2].active = false
                }
            }
            catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        async LayDS(upload_id){
            this.$root.showLoading(true)
            try{
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID(),
                    p_ds_para: {
                        "KIEU_DL": 2,
                        "UPLOAD_ID": upload_id,
                        "NGUOI_CN" : this.$root.token.getUserName()
                    }
                }
                const response = await api.LayTTAutoCall(this.axios,input)
                let data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                this.DsSauKhiImport.data = data.DS
                if(this.DsSauKhiImport.data.length > 0){
                    this.$toast.success("Lấy DS thành công")
                    for(const [index,item] of this.DsSauKhiImport.data.entries()){
                        item['stt'] = index + 1
                        if(item['LOAI'] == 1){
                            item['LOAI'] = "1 - Mã khách hàng"
                        }
                        else if(item['LOAI'] == 2){
                            item['LOAI'] = "2 - Mã thanh toán"
                        }
                        else if(item['LOAI'] == 3){
                            item['LOAI'] = "3 - Mã thuê bao"
                        }
    
                        if(item['SUDUNG'] == 1){
                            item['SUDUNG'] = "1 - Còn sử dụng"
                        }
                        else if(item['SUDUNG'] == 0){
                            item['SUDUNG'] = "0 - Không còn sử dụng"
                        }
                        item['NGUOI_CN'] = this.$root.token.getUserName()
                        item['FILE_NAME'] = this.tenFile
                        item['NGAY_CN'] = moment(new Date,"DD/MM/YYYY HH:mm:ss").format("DD/MM/YYYY HH:mm:ss")
                    }
                }
                else{
                    this.$toast.info("Không có dữ liệu")
                }
            }catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
        },
        async LayDSALL(){
            this.$root.showLoading(true)
            try{
                let input = {
                    p_phanvung_id: this.$root.token.getPhanVungID(),
                    p_ds_para: {
                        "KIEU_DL": 0,
                        "UPLOAD_ID": null,
                        "NGUOI_CN" : this.$root.token.getUserName()
                    }
                }
                const response = await api.LayTTAutoCall(this.axios,input)
                let data = apiHelper.getDataFromResponseApiReturnArray(response) || []
                this.DsSauKhiImport.data = data.DS
                if(this.DsSauKhiImport.data.length > 0){
                    this.$toast.success("Lấy DS thành công")
                    for(const [index,item] of this.DsSauKhiImport.data.entries()){
                        item['stt'] = index + 1
                        if(item['LOAI'] == 1){
                            item['LOAI'] = "1 - Mã khách hàng"
                        }
                        else if(item['LOAI'] == 2){
                            item['LOAI'] = "2 - Mã thanh toán"
                        }
                        else if(item['LOAI'] == 3){
                            item['LOAI'] = "3 - Mã thuê bao"
                        }
    
                        if(item['SUDUNG'] == 1){
                            item['SUDUNG'] = "1 - Còn sử dụng"
                        }
                        else if(item['SUDUNG'] == 0){
                            item['SUDUNG'] = "0 - Không còn sử dụng"
                        }
                    }
                }
                else{
                    this.$toast.info("Không có dữ liệu")
                }
            }catch(e){
                console.log("Error",e);
            }
            finally{
                this.$root.showLoading(false)
            }
        }
    }
}
</script>

<style scoped>

</style>