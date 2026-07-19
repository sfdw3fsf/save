
<template src="./SearchCustomers.html"></template>
<style scoped src="./SearchCustomers.scss"></style>
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
    name: 'SearchCustomers', 
    async created() {
    },
    data() {
        return {
            dateFormat: 'dd/MM/yyyy',
            formData: {
                thuebao_id: 0,
                dichvuvt_id: 0,
                ma_tb: ''
            },
            isDisabled: true,
            items: {
                ma_kh:"",
                ten_kh:"",
                phanloai: "",
                phanhang:"",
                phankhuc:"",
                mst: "",
                tiemnang:"",
                ruiro:""

            },
            header: {
                title: "Tra cứu khách hàng",
                list: [{
                        name: "Nghiệm vụ quản lý",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Kênh bán",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            dataPhanLoais: [],
            dataPhanHangs: [],
            dataPhanKhucs: [],
            dataDuBaoTiemNangs: [],
            dataDuBaoRuiRos: [],
            DsKhachhangs: [],
            taxId: ""
        }
    },
    mounted() {
        this.getDataPhanLoai();
        this.getDataPhanHang();
        this.getDataPhanKhuc();
        this.getDataDBTiemNang();
        this.getDataDBRuiRo();
    },
    methods: {
        async getDataPhanLoai(){
            this.dataPhanLoais = [{phanloai:"Loại 1",ten_phanloai:"Loại 1"},{phanloai:"Loại 2",ten_phanloai:"Loại 2"},{phanloai:"Loại 3",ten_phanloai:"Loại 3"}];
        },
        async getDataPhanHang(){
            this.dataPhanHangs = [
                {phanhang:"",ten_phanhang:"Chọn Hạng"},
                {phanhang:"Hạng 1",ten_phanhang:"Hạng 1"},
                {phanhang:"Hạng 2",ten_phanhang:"Hạng 2"},
                {phanhang:"Hạng 3",ten_phanhang:"Hạng 3"},
                {phanhang:"Hạng 4",ten_phanhang:"Hạng 4"},
                {phanhang:"Hạng 5",ten_phanhang:"Hạng 5"},
                {phanhang:"Hạng 6",ten_phanhang:"Hạng 6"}
            ];
        },
        async getDataPhanKhuc(){
            this.dataPhanKhucs = [
                {phankhuc:"",ten_phankhuc:"Chọn phân khúc"},
                {phankhuc:"Nhóm 1",ten_phankhuc:"Nhóm 1"},
                {phankhuc:"Nhóm 2",ten_phankhuc:"Nhóm 2"},
                {phankhuc:"Nhóm 3",ten_phankhuc:"Nhóm 3"}
            ];
        },
        async getDataDBTiemNang(){
             this.dataDuBaoTiemNangs = [
                {tiemnang:"",ten_tiemnang:"Chọn DB tiềm năng"},
                {tiemnang:"Cấp độ 1",ten_tiemnang:"Cấp độ 1"},
                {tiemnang:"Cấp độ 2",ten_tiemnang:"Cấp độ 2"},
                {tiemnang:"Cấp độ 3",ten_tiemnang:"Cấp độ 3"}
            ];
        },
        async getDataDBRuiRo(){
            this.dataDuBaoRuiRos = [
                {ruiro:"",ten_ruiro:"Chọn DB rủi ro"},
                {ruiro:"Cấp độ 1",ten_ruiro:"Cấp độ 1"},
                {ruiro:"Cấp độ 2",ten_ruiro:"Cấp độ 2"},
                {ruiro:"Cấp độ 3",ten_ruiro:"Cấp độ 3"}
            ];
        },

         async getDsKhachhang(){
            let input = {
                "customer_code": this.items.ma_kh,
                "name": this.items.ten_kh,
                "taxId": this.items.mst,
                "classification": this.items.phanloai,
                "rank": this.items.phanhang,
                "segment": this.items.phankhuc,
                "potential": this.items.tiemnang,
                "risk": this.items.ruiro
            }
            let res = await api.tracuu_khachhang(this.axios,input);
            console.log(res.data.data)
            this.DsKhachhangs = res.data.data;
            console.log(this.DsKhachhangs)
        },
        async txtMaKH_KeyPress(){
            this.getDsKhachhang();
        },
        async btnTimkiemClick(){
            this.getDsKhachhang();
        },
        async DsKhachhangSelected(args) {
             if (args != null) {
                console.log('args.data', args.data)
                this.taxId = args.data.taxId
                
            }
        },
        async btnExportExcel_Click() {
            let data_file = [];

            let header = {
                customerId: "Mã Khách hàng",
                customerName: "Tên khách hàng",
                AddressInfo: "Địa chỉ",
                taxId: "Mã số thuế",
                phoneNumber: "Điện thoại",
                career: "Ngành nghề"
                // classification: "Phân loại",
                // rank: "Phân hạng",
                // segment: "Phân khúc",
                // risk: "Mức độ rủi ro",
                // potential: "Mức độ tiềm năng",
                // relevantInfos: "Đầu mối liên hệ",
                // deciderInfos: "Nhân sự ra quyết định",
            };
            data_file.push(header);
            let nguoiquyetdinh = '';
            let daumoilienhe = '';
            for (let i = 0; i < this.DsKhachhangs.length; i++) {
                // console.log(this.DsKhachhangs[i].relevantInfos);
                // let dslienhe = this.DsKhachhangs[i].relevantInfos
                // dslienhe.forEach(lienhe => {
                //     daumoilienhe += `- ${lienhe.relevantName}, ${lienhe.department}, ${lienhe.dateOfBirth}, ${lienhe.phoneNumber}, ${lienhe.email} , ${lienhe.gender}`;
                //     if (dslienhe.length > 2) {
                //         daumoilienhe += '\n';
                //     } else {
                //         daumoilienhe += ' ';
                //     }
                // });
                // console.log(this.DsKhachhangs[i].deciderInfos);
                // let dsQuyetdinh = this.DsKhachhangs[i].deciderInfos
                // dsQuyetdinh.forEach(quyetdinh => {
                //     nguoiquyetdinh += `- ${quyetdinh.officerName}, ${quyetdinh.dateOfBirth}, ${quyetdinh.dateOfBirth}, ${quyetdinh.phoneNumber}, ${quyetdinh.email} , ${quyetdinh.position}`;
                //     if (dsQuyetdinh.length > 2) {
                //         nguoiquyetdinh += '\n';
                //     } else {
                //         nguoiquyetdinh += ' ';
                //     }
                // });
                data_file.push({
                    customerId: this.DsKhachhangs[i].customerId,
                    customerName: this.DsKhachhangs[i].customerName,
                    AddressInfo: this.DsKhachhangs[i].addressInfo,
                    taxId: this.DsKhachhangs[i].taxId,
                    phoneNumber: this.DsKhachhangs[i].phoneNumber,
                    career: this.DsKhachhangs[i].career
                    // classification: this.DsKhachhangs[i].classification,
                    // rank: this.DsKhachhangs[i].rank,
                    // segment: this.DsKhachhangs[i].segment,
                    // risk: this.DsKhachhangs[i].risk,
                    // potential: this.DsKhachhangs[i].potential,
                    // relevantInfos: daumoilienhe,
                    // deciderInfos: nguoiquyetdinh,
                });
            }

            let data = XLSX.utils.json_to_sheet(data_file, { skipHeader: true });
            let wb = XLSX.utils.book_new();
            XLSX.utils.book_append_sheet(wb, data, 'DsKhachhangGrid');
            XLSX.writeFile(wb, 'Danh sách khách hàng.xlsx');
        },
          async ChitietChanDungKH() {
            let path = "https://onebss.vnpt.vn/#/customer/ChiTiet?taxId="+this.taxId
            if (process.env.NODE_ENV == 'testing') {
                path = path.replace('https://onebss.vnpt.vn', 'https://dev-onebss.vnpt.vn')
            } 
             window.open(path, '_blank')
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