<template src='./index.html'></template>
<style src='./styles.scss' scoped></style>
<script>
import moment from "moment";
import API from "../api/KBTraCuuKH";
import xlsx from "xlsx";

export default {
    name: "KBTraCuuKH",
    mounted(){
        this.TIMKIEM(0)
    },
    computed:{},
    data(){
        return {
            txtMaKH:"",
            txtTenKH:"",
            ngayADTT:"",
            txtMaKBNN:"",
            txtMaTKKT:"",
            txtNDKT:"",
            txtMaCapNS:"",
            txtDBHC:"",
            txtCTMT:"",
            txtNganhKT:"",
            txtMaChuong:"",
            txtNguon:"",
            txtMaDuPhong:"",
            txtGhiChu:"",
            txtTTBS1:"",
            txtTTBS2:"",
            txtTTBS3:"",
            txtTTBS4:"",
            txtTTBS5:"",
            txtMaDVSDNS:"",
            txtTenDVSDNS:"",
            txtMaNCC:"",
            txtTenNCC:"",
            txtTen_NH:"",
            txtSoHieuKB:"",
            txtDinhMuc:"",
            txtTrangThai:"",
            txtLoaiDV:"",
            dsKhachHang:[],
            dsThanhToan:[],
            txtMaTT: ""
        }
    },
    methods:{
        onClickTimKiem(){
           this.TIMKIEM(0)
        },
        onClickXuatFile(){
            if (this.dsKhachHang.length <= 0) {
                this.$root.toastError("Không có dữ liệu để xuất Excel!");
                return;
            }
            let ds_Excel = []
            this.$confirm("Bạn có muốn xuất chi tiết đến mã TT không?", "Xác nhận", {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
            })
            .then(async () => {
                ds_Excel = await this.getDsExcelKH();
                this.XuatFileExcel(ds_Excel)
            })
            .catch(() => {
                let t = this.$refs.gridDsKhachHang.getSelectedRecords() || [];
                if(t.length > 0){
                    ds_Excel = t
                } else {
                    ds_Excel = this.dsKhachHang
                }
                this.XuatFileExcel(ds_Excel)
            });
        },
        XuatFileExcel(data){
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(data);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "fileKH_UQTT.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },
        async getDsExcelKH(){
            let dsResult = []
            try {
                this.loading(true)
                const response = await API.getDanhSachExcelKH(this.axios, {tinhthicong_id: 21}); //this.$root.token.getPhanVungID()
                if (response.data.error_code === "BSS-00000000") {
                    dsResult = response.data.data
                } else {
                    dsResult = []
                }
            } catch (error) {
                dsResult = []
            } finally {
              this.loading(false)
              return dsResult
            }
        },
        async Nap_DS_ThanhToan(p_kbkh_id){
            const data = {
                tinhthicong_id: 21,//this.$root.token.getPhanVungID(),
                kbkh_id: p_kbkh_id,
            };
            this.loading(true);
            try {
                const response = await API.getDanhSachThanhToan(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.dsThanhToan = this.convertArrayData(response.data.data)
                } else {
                    this.$root.toastError("Không tìm thấy thông tin!");
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
            } finally {
              this.loading(false);
            }
        },
        onEnterMaKH(){
            if(this.txtMaKH.trim() != ""){
                this.TIMKIEM(this.txtMaKH)
            }
        },
        async TIMKIEM(maKH){
            this.clearData();
            this.dsKhachHang = []
            const data = {
                tinhthicong_id: 21,//this.$root.token.getPhanVungID(),
                ma_kh: maKH,
            };
            this.loading(true);
            try {
                const response = await API.getVer2DanhSachKhachHang(this.axios, data);
                // const response = await API.getDanhSachKhachHang(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.dsKhachHang = response.data.data
                    if(this.dsKhachHang.length > 0) await this.NAP_DS_CHITIET_KH(0,this.dsKhachHang[0])
                } else {
                    this.$root.toastError("Không tìm thấy thông tin!");
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
            } finally {
              this.loading(false);
            }
        },
        async NAP_DS_CHITIET_KH(index,data){
            if(data){
                this.txtMaKH = data.MA_KH
                this.txtTenKH = data.TEN_KH
                this.ngayADTT = data.NGAY_ADTT //? moment(data.NGAY_ADTT).format('DD/MM/YYYY') : ""
                this.txtMaKBNN = data.MA_KBNN
                this.txtMaTKKT = data.MA_TKKT
                this.txtNDKT = data.MA_NDKT
                this.txtMaCapNS = data.MA_CAPNS
                this.txtDBHC = data.MA_DBHC
                this.txtCTMT = data.MA_CTMT
                this.txtNganhKT = data.MA_NGANHKT
                this.txtMaChuong = data.MA_CHUONG
                this.txtNguon = data.MA_NGUON
                this.txtMaDuPhong = data.MA_DUPHONG
                this.txtGhiChu = data.GHICHU
                this.txtTTBS1 = data.TTBS_1
                this.txtTTBS2 = data.TTBS_2
                this.txtTTBS3 = data.TTBS_3
                this.txtTTBS4 = data.TTBS_4
                this.txtTTBS5 = data.TTBS_5
                this.txtMaDVSDNS = data.MA_DVSDNS
                this.txtTenDVSDNS = data.TEN_DVSDNS
                this.txtMaNCC = data.MA_NCC
                this.txtTenNCC = data.TEN_NCC
                this.txtTen_NH = data.TEN_NH
                this.txtSoHieuKB = data.SOHIEU_KB
                this.txtDinhMuc = data.DINHMUC
                this.txtTrangThai = data.TRANGTHAI
                this.txtLoaiDV = data.LOAI_DV
                this.txtMaTT = data.MA_TT
                // await this.Nap_DS_ThanhToan(data.KBKH_ID)
            } else {
                this.clearData()
            }
        },
        clearData(){
            this.txtMaKH = ""
            this.txtTenKH = ""
            this.ngayADTT = ""
            this.txtMaKBNN = ""
            this.txtMaTKKT = ""
            this.txtNDKT = ""
            this.txtMaCapNS = ""
            this.txtDBHC = ""
            this.txtCTMT = ""
            this.txtNganhKT = ""
            this.txtMaChuong = ""
            this.txtNguon = ""
            this.txtMaDuPhong = ""
            this.txtGhiChu = ""
            this.txtTTBS1 = ""
            this.txtTTBS2 = ""
            this.txtTTBS3 = ""
            this.txtTTBS4 = ""
            this.txtTTBS5 = ""
            this.txtMaDVSDNS = ""
            this.txtTenDVSDNS = ""
            this.txtMaNCC = ""
            this.txtTenNCC = ""
            this.txtTen_NH = ""
            this.txtSoHieuKB = ""
            this.txtDinhMuc = ""
            this.txtTrangThai = ""
            this.txtLoaiDV = ""
            this.txtMaTT = ""
            this.dsThanhToan = []
        },
        convertArrayData(data){
            let result = []
            result = data.map( function( item ){
            for(var key in item){
                item[ key.toUpperCase() ] = item[key];
                delete item[key];
            }
                return item;
            })
            return result
        }
    },
    created(){
    },
    watch:{

    }
}
</script>
