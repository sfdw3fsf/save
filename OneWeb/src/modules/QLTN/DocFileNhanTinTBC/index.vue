<template src="./index.html"></template>
<style src="./styles.scss" scoped>
</style>

<script>
import API from "../api/ThietLapTBC";
import xlsx from "xlsx";

export default {
    data() {
        return {
            duongDan:"",
            idLoaiYC:"",
            dsLoaiYC:[],
            isTab:false,
            dsThueBao:[],
            dsLoi:[]
        }
    },
    watch:{

    },
    async created(){
        this.fetchDSLoaiTin();
    },
    mounted(){

    },
    methods:{
        DanhSach(){

        },
        async CapNhat(){
            if(this.dsThueBao.length <= 0){
                this.$root.toastError('Danh sách trống, không có dữ liệu để cập nhật!')
                return
            }
            if(!this.idLoaiYC){
                this.$root.toastError('Chưa chọn loại YC!')
                return
            }
            const data = {
                ds: this.dsThueBao,
                idNguoiDung: this.$auth.getNguoiDungID()
            }
            //const response = await API.getDSDanhMuc(this.axios, "LOAITIN_TBC");

        },
        XuatExcel(){
            if (this.dsLoi.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                this.isTab = true;
                return;
            }
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(this.dsLoi);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TT"); // sheetAName is name of Worksheet
                xlsx.writeFile(wb, "mau.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },
        async fetchDSLoaiTin() {
            const response = await API.getDSDanhMuc(this.axios, "LOAITIN_TBC");
            if (response.data.error_code === "BSS-00000000") {
                this.dsLoaiYC = response.data.data;
            }
        },
        onChangeFile(e){
            const files = e.target.files;
            this.duongDan = e.target.files[0].name;
            if (!files.length) {
                return;
            } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
                return this.$root.toastError("File excel không đúng định dạng!");
            }
            const fileReader = new FileReader();
            fileReader.onload = async (ev) => {
                this.dsLoi = []
                this.dsThueBao = []
                const data = ev.target.result;
                const XLSX = xlsx;
                const workbook = XLSX.read(data, {
                    type: "binary",
                });
                const wsname = workbook.SheetNames[0];
                const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
                const excellist = [];
                let dsDanhSach = [];

                for (var i = 0; i < ws.length; i++) {
                    excellist.push(ws[i]);
                    // dsDanhSach.push({
                    //     MA_TT: ws[i].MA_TT,
                    //     SO_DT: ws[i].SO_DT,
                    // });
                    let lyDo = ""
                    lyDo = !ws[i].MA_TT ? "Mã TT (MA_TT) không được để trống!" : !ws[i].SO_DT ? "Số ĐT (SO_DT) không được để trống!" : ""
                    if(lyDo) this.isTab = true
                }

                const arrayCol = Object.keys(excellist[0]);
                if (
                    arrayCol[0] !== "MA_TT" ||
                    arrayCol[1] !== "SO_DT"
                ) {
                    this.$refs.duongDan.focus();
                    this.$refs.duongDan.select();
                    this.$root.toastError("File không đúng định dạng. Yêu cầu các cột: MA_TT, SO_DT!");
                    return;
                }
                const dataRequest = {
                    ds: ws
                }
                // const response = await API.kiemTra_DL_TBC(this.axios,dataRequest)
                // if(response.data.error_code == 'BSS-00000000' && response.data.data.length > 0){
                //     dsDanhSach = response.data.data
                // }
                //handler danhsach loi va thue bao
                if(dsDanhSach.length > 0){
                    this.dsLoi = dsDanhSach
                    this.isTab = true
                } else {
                    this.dsThueBao = []
                    this.isTab = false
                }
                this.loading(false)
            }
            fileReader.readAsBinaryString(files[0])
        }
    },
    computed:{

    }
}
</script>


