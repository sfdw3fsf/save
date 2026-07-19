<template src='./index.html'></template>
<style src='./styles.scss' scoped></style>
<script>
import moment from "moment";
import API from "../api/KBTTBangKe";
import xlsx from "xlsx";
import Vue from 'vue'
import { currency } from '@/filters/currency'

export default {
    name: "KBTTBangKe",
    mounted(){
        this.fetchDsKyHoaDon()
    },
    computed:{},
    data(){
        return{
            pKyHoaDon: '',
            dsKyHoaDon:[],
            txtLanGui:"",
            pTuNgay: "",
            pDenNgay: "",
            cboTrangThai:0,
            cboTrangThaiPV:0,
            pNgayTao:"",
            pNgayHT:"",
            pNgayNhan:"",
            txtMsg_Gui_ID:"",
            txtMsg_Loi_ID:"",
            txtMsg_Nhan_ID:"",
            txtTongTien:0,
            dsBangKe:[],
            dsThanhToan:[],
            keyResetBangKe:0,
            keyResetThanhToan:0,
            showTTBangKe: false,
            showDSBangKe: false,
            customTemplate: function () {
                return {
                template: Vue.component('footerTemplate', {
                    template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="font-weight:bold;margin: 7px 0px 7px 0px;" />`,
                    data() { return { data: { data: {} } } }
                })
                }
            }
        }
    },
    methods:{
        aggCountTongTien(){
            let result = this.$refs.gridDsThanhToan.filteredDataSource || []
            let sumTongTien = result.reduce(function(acc,obj){
                return acc + obj.TONGTIEN
            }, 0)
            return currency(sumTongTien)
        },
        aggCountTongTra(){
            let result = this.$refs.gridDsThanhToan.filteredDataSource || []
            let sumTongTra = result.reduce(function(acc,obj){
                return acc + obj.TIENTRA
            }, 0)
            return currency(sumTongTra)
        },
        onClickTimKiem(){
            this.NAP_DS_BANGKE()
        },
        onClickXuatFile(){
            if (this.dsThanhToan.length <= 0) {
                this.$root.toastError("Không có dữ liệu để xuất Excel!");
                return;
            }
            let t = this.$refs.gridDsThanhToan.getSelectedRecords() || [];
            let dt_excel = []
            if(t.length > 0){
                dt_excel = t
            } else {
                dt_excel = this.dsThanhToan
            }
            this.$confirm("Bạn có muốn xuất excel chi tiết đến thuê bao không?", "Xác nhận", {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
            })
            .then(async () => {
                let dt_gachno = []
                if(dt_excel.length > 0){
                    dt_gachno = await this.getDsExcelPhieuGachNo(dt_excel);
                }
                this.XuatFileExcel(dt_gachno)
            })
            .catch(() => {
                this.XuatFileExcel(dt_excel)
            });
        },
        XuatFileExcel(data){
            if(data.length <= 0){
                this.$root.toastError("Không có dữ liệu để xuất Excel!");
                return;
            }
            this.loading(true);
            try {
                var dsach = xlsx.utils.json_to_sheet(data);
                var wb = xlsx.utils.book_new(); // make Workbook of Excel
                xlsx.utils.book_append_sheet(wb, dsach, "TB"); // sheetAName is name of Worksheet
                // export Excel file
                xlsx.writeFile(wb, "fileDS_TT.xlsx");
            } catch (error) {
            } finally {
                this.loading(false);
            }
        },
        async getDsExcelPhieuGachNo(dSach){
            let dsResult = []
            try {
                this.loading(true)
                const data = {
                    tinhthicong_id: this.$root.token.getPhanVungID(),
                    p_kyhoadon: this.pKyHoaDon,
                    p_ds: JSON.stringify(dSach)
                }
                const response = await API.getDanhSachCTExcelBangKe(this.axios, data);
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
        async NAP_DS_BANGKE(){
            const data = {
                tinhthicong_id: 21, //this.$root.token.getPhanVungID(),
                kyhoadon: this.pKyHoaDon,
            };
            this.loading(true);
            try {
                const response = await API.getDanhSachBangKe(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.keyResetBangKe += 1
                    this.dsBangKe = this.convertArrayData(response.data.data)
                } else {
                    this.dsBangKe = []
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
                this.dsBangKe = []
            } finally {
              if(this.dsBangKe.length <= 0) this.clearData()
              this.loading(false);
            }
        },
        async NAP_DS_THANHTOAN_BANGKE(bangke_id){
            this.dsThanhToan = []
            const data = {
                tinhthicong_id: 21, //this.$root.token.getPhanVungID(),
                bangke_id: bangke_id,
            };
            this.loading(true);
            try {
                const response = await API.getDanhSachCTBangKe(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.keyResetThanhToan += 1
                    this.dsThanhToan = this.convertArrayData(response.data.data)
                } else {
                    this.dsThanhToan = []
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
                this.dsThanhToan = []
            } finally {
              this.loading(false);
            }
        },
        async fetchDsKyHoaDon(){
            this.loading(true);
            try {
                const response = await API.getDanhSachChuKyNo(this.axios);
                if (response.data.error_code === "BSS-00000000") {
                    this.dsKyHoaDon = response.data.data
                    this.dsKyHoaDon = this.dsKyHoaDon.map((item=>({...item,HIENTHI:moment(item.CHUKYNO,'YYYYMMDD').format('MM/YYYY')})))
                    if(this.dsKyHoaDon.length > 0) this.pKyHoaDon = this.dsKyHoaDon[0].CHUKYNO
                } else {
                    this.dsKyHoaDon = []
                }
            } catch (error) {
                this.$root.toastError(error.data.message_detail);
                this.dsKyHoaDon = []
            } finally {
              this.loading(false);
            }
        },
        clearData(){
            this.txtLanGui = ""
            this.pTuNgay = ""
            this.pDenNgay = ""
            this.cboTrangThai = 0
            this.pNgayTao = ""
            this.pNgayHT = ""
            this.pNgayNhan = ""
            this.txtMsg_Gui_ID = ""
            this.txtMsg_Loi_ID = ""
            this.txtMsg_Nhan_ID = ""
            this.dsThanhToan = []
            this.txtTongTien = 0
            this.cboTrangThaiPV = 0
        },
        async NAP_DS_CHITIET_BANGKE(data){
            if(data){
                this.txtLanGui = data.THUTU
                this.pTuNgay = this.formatNgay(data.TUNGAY)
                this.pDenNgay = this.formatNgay(data.DENNGAY)
                this.cboTrangThai = data.TRANGTHAI_ID
                this.pNgayTao = this.formatNgay(data.NGAYTAO)
                this.pNgayHT = this.formatNgay(data.NGAY_HT)
                this.pNgayNhan = this.formatNgay(data.NGAY_NHAN)
                this.txtMsg_Gui_ID = data.MSG_GUI_ID
                this.txtMsg_Loi_ID = data.MSG_LOI_ID
                this.txtMsg_Nhan_ID = data.MSG_NHAN_ID
                this.txtTongTien = data.TONGTIEN
                this.cboTrangThaiPV = data.TRANGTHAI_PV_ID
                await this.NAP_DS_THANHTOAN_BANGKE(data.BANGKE_ID)
            } else {
                this.clearData()
            }
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
        },
        formatNgay(value){
            return value ? moment(value).format('DD/MM/YYYY') : ""
        }
    },
    created(){
    },
    watch:{
        pKyHoaDon:{
            handler: function(val){
                this.NAP_DS_BANGKE()
            }
        }
    }
}
</script>
