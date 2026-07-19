
<template src="./LaborCostChannelMgmt.html"></template>
<style scoped src="./LaborCostChannelMgmt.scss"></style>
<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
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
        appCombobox: ComboboxGrid
    },
    name: 'LaborCostChannelMgmt', 
    async created() {
    },
    data() {
        return {
            dateFormat: 'dd/MM/yyyy',
            isDisabled: true,
            header: {
                title: "IMPORT THEO FILE - THUÊ BAO KÊNH TRUYỀN TÍNH CƯỚC NHÂN CÔNG",
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
            dt_error: [],
            file: null,
            duongDan:"",
            dsThueBao: [],
            dt_khonghople: [],
            dt_hople: [],
            tsbtnChuyenDuyet: {
                Enabled: true
            },
            ma_tap: "",
            columns_ds_dulieuhople: [],
            columns_ds_dulieukhonghople: [],
            tt_nd:{
                ngay_cn:this.$auth.getNgayCapNhat(),
                tentat:"",
                donvi_id: this.$auth.getDonViID(),
                nhanvien_id: this.$auth.getNhanVienID(),
                may_cn: this.$auth.getUserName(),
                ma_nd: this.$auth.getUserName(),
                ip: this.$auth.getUserName(),
            },
        }
    },
    mounted() {
    },
    methods: {
        async ChuyenduyetClick(){
            this.loading(true);
            let rs = await api.sp_chuyen_duyet_tinhcuoc_nhancong(this.axios, {
                "p_ma_tap": this.ma_tap,
                "p_donvi_duyet_id": this.tt_nd.donvi_id
            });
            console.log(rs.data)
            if(rs.data.error_code == 'BSS-00000000' && rs.data.error == 200){
                this.$root.$toast.success("Chuyển duyệt thành công!");
            }else{
                this.$toast.error("Có lỗi : " + rs.data.message);
            }
            this.loading(false);
        },
        async load_ds() { 
            this.loading(true);
            let data = this.GetData(await api.Lay_ds_thaythe_file(this.axios, {
                "vkieu": 0,
                "in_hdkh_id": 0
            }));
             this.loading(false);
            this.dsThueBao = data;
        },
        duongDan_click: function (args) {
            this.$refs.fileInput.click();
        },
        taiFileMau() {
            this.$confirm('Bạn có muốn tải file mẫu về không ?', 'Thông báo', {
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ',
                type: 'info'
            }).then(() => {
                window.open("/static/files/MauFile_Kenhtruyen_Tinhcuoc_Nhancong.xlsx");
            })
        },
        onChangeFile(event) {
            this.file = event.target.files[0]
            console.log(this.file)
            this.duongDan = this.file.name
            this.readFileExcel()
        },
        checkfile: function () {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
                this.$toast.error('File upload không đúng định dạng')
                return false
            }
            return true
        },
        kiemTraDuLieu: function (columns) {
            if (columns == null) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            return true
        },
        DsThuebaoSelected(){

        },
        async Kiemtratongquan() {
            this.dt_error = []
            try {
                let kt = await api.fn_import_thuebao_tinhcuoc_nhancong(this.axios, {
                    "p_json_data": JSON.stringify(this.danhsach_excel),
                    "p_xacnhan": 0
                });
                let kq = JSON.parse(kt.data.data)
                let key = Object.keys(this.danhsach_excel[0])
                    let ds_dulieu_khonghople = []
                    let ds_dulieu_hople = []
                for (let i = 0; i < key.length; i++)
                {  
                        ds_dulieu_khonghople.push(
                        {
                            fieldName: key[i],
                            headerText: key[i],
                            allowFiltering: true,
                            allowSorting: true,
                        });
                        ds_dulieu_hople.push(
                        {
                            fieldName: key[i],
                            headerText: key[i],
                            allowFiltering: true,
                            allowSorting: true,
                        });
                }
                this.columns_ds_dulieuhople = ds_dulieu_khonghople
                this.columns_ds_dulieukhonghople = ds_dulieu_hople
                if (kq.ERROR_CODE == 0) {
                    this.columns_ds_dulieukhonghople.push(
                    {
                            fieldName: 'STATUS',
                            headerText: 'STATUS',
                            allowFiltering: true,
                            allowSorting: true,
                    });
                    
                    this.dt_khonghople = kq.RESULTS.filter(item => item.STATUS !== 'OK');
                    this.$toast.error(kq.MESSAGE)
                }else{
                    this.dt_hople = kq.RESULTS.filter(item => item.STATUS == 'OK');
                    this.dt_khonghople = kq.RESULTS.filter(item => item.STATUS !== 'OK');
                    if(this.dt_khonghople.length > 0){
                        this.$toast.warning("Có " + this.dt_khonghople.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!");
                        return;
                    }
                }
            } catch (err) {
                this.$toast.error("Có lỗi : " + err);
            }
        },
        async tsbtnGhiLai_Click() {
            this.dt_error = []
            try {
                let rs = await api.fn_import_thuebao_tinhcuoc_nhancong(this.axios, {
                    "p_json_data": JSON.stringify(this.danhsach_excel),
                    "p_xacnhan": 1
                });
                let kq = JSON.parse(rs.data.data)
                if (kq.ERROR_CODE == 0) {
                    this.dt_error = kq.RESULTS.filter(item => item.STATUS !== 'OK');
                    this.$toast.warning("Có " + this.dt_error.length + " bản ghi sai dữ liệu! Bạn hãy kiểm tra trong file!");
                    this.XuatExcel();
                } else {
                    this.$root.$toast.success("Import file thành công!");
                    this.ma_tap = kq.MESSAGE
                } 
            } catch (err) {
                this.$toast.error("Có lỗi : " + err);
            }
        },
        XuatExcel() {
            if (this.dt_error.length <= 0) {
                this.$root.toastError("Không có dữ liệu lỗi để xuất file!");
                return;
            }
            this.loading(true);
            try {
                var dsach = XLSX.utils.json_to_sheet(this.dt_error);
                var wb = XLSX.utils.book_new();
                XLSX.utils.book_append_sheet(wb, dsach, "DS"); 
                XLSX.writeFile(wb, "dsLoi.xlsx");
            } catch (error) {} finally {
                this.loading(false);
            }
        },
        async readFileExcel() {
            this.danhsach_excel = []
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, {
                            type: "binary"
                        })
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        const data = XLSX.utils.sheet_to_json(ws, {
                            header: 1
                        });
                        const columns = data[0];
                        if (this.kiemTraDuLieu(columns)) {
                            if (columns.length > 0) {
                                const jsonData = data.slice(1).map(row => {
                                    let obj = {};
                                    columns.forEach((key, index) => {
                                        obj[key] = row[index];
                                    });
                                    return obj;
                                });
                                
                                this.danhsach_excel = jsonData;
                                this.Kiemtratongquan();
                                console.log(this.danhsach_excel);
                            } else {
                                this.$toast.error('Danh sách trống!')
                            }
                        }
                    }
                    reader.readAsBinaryString(this.file)
                }
                this.loading(false)
            } catch (error) {
                this.loading(false)
                this.$toast.error('Có lỗi xảy ra khi đọc file')
            }
        },
        btnExportExcelHople_Click(){
            this.loading(true);
            try {
                var dsach = XLSX.utils.json_to_sheet(this.dt_hople);
                var wb = XLSX.utils.book_new();
                XLSX.utils.book_append_sheet(wb, dsach, "DS"); 
                XLSX.writeFile(wb, "dt_hople.xlsx");
            } catch (error) {} finally {
                this.loading(false);
            }
        },
        btnExportExcelKhonghople_Click(){
                var dsach = XLSX.utils.json_to_sheet(this.dt_khonghople);
                var wb = XLSX.utils.book_new();
                XLSX.utils.book_append_sheet(wb, dsach, "DS"); 
                XLSX.writeFile(wb, "dt_khonghople.xlsx");
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