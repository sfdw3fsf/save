<template src='./frmChungTu_TBao.html'></template>
<script>
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import frmChungTu_TBaoAPI from  '../api/frmChungTu_TBaoAPI'
import select2 from '@/components/Select2.vue'
import { Freeze, Group, Page, Filter, Sort, Resize, ForeignKey} from "@syncfusion/ej2-vue-grids";
import { L10n } from '@syncfusion/ej2-base'
import XLSX from "xlsx";
import {previewPrint} from "@/utils/util"

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: {XLSX, breadcrumb, appSelect2: select2 },
  name: "frmChungTu_TBao",
  mounted() {
      console.log('123123123')
  },
  provide: {
    grid: [ Freeze, Group, Page,Filter,Sort,Resize, ForeignKey ]
  },
  computed: {
    
  },
  data() {
    return {
        chungtu_id: '',
        kieu: 0,

        gridDanhSach: [],
        gridDanhSach_temp: [],
        grcCot: [],
        ds_cot: [],

        params: {
            p_ckbChungTu: false,
        },

        Text: {
            grpThietBi: ''
        },
        gridDanhSachColumns: [
            {fieldName: 'STT', headerText: 'STT', textAlign: 'Left', visible: true, allowFiltering: true,width: '70',freeze: 'Left'},
            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', visible: true, allowFiltering: true, freeze: 'Left'},
            {fieldName: 'MA_LT', headerText: 'Số ảo', textAlign: 'Left', visible: true, allowFiltering: true},
            {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', textAlign: 'Left', visible: true, allowFiltering: true},
            {fieldName: 'LOAI_HD', headerText: 'Loại HĐ', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'TEN_KIEULD', headerText: 'Kiểu yêu cầu', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'TEN_VT', headerText: 'Tên VT', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', visible: true, allowFiltering: true },
            {fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', visible: true, allowFiltering: true },
            {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'NGAY_CAP', headerText: 'Ngày cấp', textAlign: 'Right', visible: true, allowFiltering: true },
            {fieldName: 'MUCDICH', headerText: 'Mục đích cấp', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'LYDO_CAP', headerText: 'Lý do cấp', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', visible: true, allowFiltering: true },
            {fieldName: 'NGAY_HT', headerText: 'Ngày HT', textAlign: 'Right', visible: true, allowFiltering: true },
            {fieldName: 'TEN_KHO', headerText: 'Kho cấp', textAlign: 'Left', visible: true, allowFiltering: true,freeze: 'Right' },
        ],
        
        gridDanhSachPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
         grcCotPage : {
            page: 0,
            maxSize: 20,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },

        Visible: {
            Panel2Collapsed: true,
            tsbtnBBBG: true,
        },
        Enabled: {
            tsbtnBBBG: false
        },
        hdkh_id: 0, // ưpdate code moi tu dhsx
        ts_xem_bienban_bangiao: false,


    };
  },
  methods: {
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },

    gridDanhSachPageHandler(e) {
      this.gridDanhSachPage.page = e.pageIndex
      this.gridDanhSachPage.maxSize = e.pageSize
    },
    grcCotPageHandler(e) {
      this.grcCotPage.page = e.pageIndex
      this.grcCotPage.maxSize = e.pageSize
    },
    async btnTimKiem_Click(){
        // try {
            await this.Lay_DanhSachTB()
        // } catch (error) {
            // this.$toast.error("Có lỗi hàm lấy danh sách thuê bao ")
        // }
    },
    async Lay_DanhSachTB(){
        if( this.kieu ==0 ){
            this.gridDanhSachColumns= [
                {fieldName: 'STT', headerText: 'STT', textAlign: 'Left', visible: true, allowFiltering: true,width: '70',freeze: 'Left'},
                {fieldName: 'MA_TB', headerText: 'Số máy/Acc', textAlign: 'Left', visible: true, allowFiltering: true, freeze: 'Left'},
                {fieldName: 'MA_LT', headerText: 'Số ảo', textAlign: 'Left', visible: true, allowFiltering: true},
                {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', textAlign: 'Left', visible: true, allowFiltering: true},
                {fieldName: 'LOAI_HD', headerText: 'Loại HĐ', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'TEN_KIEULD', headerText: 'Kiểu yêu cầu', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'TEN_VT', headerText: 'Tên VT', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'DVI_TINH', headerText: 'ĐVT', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'SOLUONG', headerText: 'Số lượng', textAlign: 'Right', visible: true, allowFiltering: true },
                {fieldName: 'DONGIA', headerText: 'Đơn giá', textAlign: 'Right', visible: true, allowFiltering: true },
                {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'NGAY_CAP', headerText: 'Ngày cấp', textAlign: 'Right', visible: true, allowFiltering: true },
                {fieldName: 'MUCDICH', headerText: 'Mục đích cấp', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'LYDO_CAP', headerText: 'Lý do cấp', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'LOHANG', headerText: 'Lô hàng', textAlign: 'Left', visible: true, allowFiltering: true },
                {fieldName: 'NGAY_HT', headerText: 'Ngày HT', textAlign: 'Right', visible: true, allowFiltering: true },
                {fieldName: 'TEN_KHO', headerText: 'Kho cấp', textAlign: 'Left', visible: true, allowFiltering: true,freeze: 'Right' },
            ]
            this.gridDanhSach = []
            await frmChungTu_TBaoAPI.getDSThueBaoChungTu(this.axios, {chungTuId: this.chungtu_id})
            .then(
                (response) => {
                  this.gridDanhSach = response.data.data || []
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })
        }
        else{
            let dem = 7
            let k = 0
            this.gridDanhSachColumns = []
            this.gridDanhSachColumns.push({fieldName: 'tenDV', headerText: 'Tên đơn vị', textAlign: 'Left', allowFiltering: true })
            this.gridDanhSachColumns.push({fieldName: 'srDoi', headerText: 'Serial đổi', textAlign: 'Left', allowFiltering: true })
            this.gridDanhSachColumns.push({fieldName: 'srBh', headerText: 'Serial bảo hành', textAlign: 'Left', allowFiltering: true })
            this.gridDanhSachColumns.push({fieldName: 'maVT', headerText: 'Mã VT', textAlign: 'Left', allowFiltering: true })
            this.gridDanhSachColumns.push({fieldName: 'tenVT', headerText: 'Tên VT', textAlign: 'Left', allowFiltering: true })
            this.gridDanhSachColumns.push({fieldName: 'loHang', headerText: 'Lô hàng', textAlign: 'Left', allowFiltering: true })
            await frmChungTu_TBaoAPI.getDSThueBaoChungTuV2(this.axios, {chungTuId: this.chungtu_id})
            .then(
                (response) => {
                  this.gridDanhSach = response.data.data || []
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })
        }
        this.ds_cot = []
        let indexArray = []
        if (this.gridDanhSach.length){
            if(this.$auth.getMaTinh() == 'HPG'){
                let tinh = ["MA_TB", "STT","TEN_TB","DIACHI_LD","TEN_KIEULD","TEN_VT","SOLUONG","TEN_KHO","NGAY_HT"]
                this.gridDanhSachColumns.forEach((a,index)=>{
                    if (tinh.includes(a.fieldName) && !this.ds_cot.map(b=>b.TEN_FIELD).includes(a.fieldName)){
                        this.ds_cot.push({
                            ISCHECK: true,
                            TEN_COT: a.headerText,
                            TEN_FIELD: a.fieldName,
                        })
                        indexArray.push(index)
                        a.visible = true
                        
                    }
                    else if (!this.ds_cot.map(b=>b.TEN_FIELD).includes(a.fieldName)){
                        this.ds_cot.push({
                            ISCHECK: false,
                            TEN_COT: a.headerText,
                            TEN_FIELD: a.fieldName,
                        })
                        a.visible = false
                    }
                })

            }
            else{
                this.gridDanhSachColumns.forEach(a=>this.ds_cot.push({
                    ISCHECK: false,
                    TEN_COT: a.headerText,
                    TEN_FIELD: a.fieldName,
                }))
                
            }
        }
        this.grcCot = this.ds_cot
        this.gridDanhSach_temp = this.gridDanhSach
        indexArray.length ? this.$refs.grvCot.grid.selectRows(indexArray,true) : ''
        this.$refs.gridviewDanhSach.grid.refreshColumns()
    },
    async frmChungTu_TBao_Load(){
        // try {
            
            this.$root.showLoading(true)
            if (this.kieu == 1){
                this.Text.grpThietBi = "Danh sách thiết bị bảo hành"
            }
            var kq = "";
            await frmChungTu_TBaoAPI.CHECK_IS_EXIST(this.axios, {maThamSo: "TS_XEM_BIENBAN_BANGIAO"})
            .then(
                (response) => {
                  kq = response.data.data || []
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })
            if (kq == "1"){
                this.Visible.tsbtnBBBG = true
                this.ts_xem_bienban_bangiao = true
            }
            else{
                this.Visible.tsbtnBBBG = false
                this.ts_xem_bienban_bangiao = false
            }

            await this.Lay_DanhSachTB()
            this.$root.showLoading(false)
        // } catch (error) {
        //     this.$toast.error("Có lỗi hàm lấy danh sách thuê bao ")
        // }
    },
    async tsbtnXuatExcel_Click(){
        let exportData = []
        let exportCols = this.$refs.grvCot.getSelectedRecords()
        
        this.gridDanhSach.forEach(a=>{
            let row = {}
            exportCols.forEach(c=>{
                row[c.TEN_COT] = a[c.TEN_FIELD]
            })
            !!row ? exportData.push(row) : ''
        })
        if (exportData.length == 0){
            exportData.push(exportCols.map(b=>b.TEN_COT))
        }
        await this.HT_DLG_XUAT_EXCEL(exportData, "DSTbi")
    },
    async ckbChungTu_CheckedChanged(){
        
        if (this.params.p_ckbChungTu){
            this.gridDanhSach = this.gridDanhSach.filter(a=>a.SERIAL.toString() != '')
        }
        else{
            this.gridDanhSach = this.gridDanhSach_temp
        }
    },
    async IsValidFileName(){

    },
    async lnkXuatExcel_ProgressChanged(){

    },
    async HT_DLG_XUAT_EXCEL(dt,filename){
        let data1 = XLSX.utils.json_to_sheet(dt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data1, filename); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, filename + ".xlsx");
    },
    async repositoryItemCheckEdit1_CheckedChanged(args){
        // debugger
        if (args){
            var visibleFields = this.$refs.grvCot.getSelectedRecords().map(a=>a.TEN_FIELD)
            this.gridDanhSachColumns.forEach(b=>{
                if (visibleFields.includes(b.fieldName)){
                    b.visible = true
                }
                else{
                    b.visible = false
                }
            })
        }
    },
    async Close(){            
        
        this.$parent.hide()
    },
    async ShowDialog(){
        this.$parent.show()
    },
    // update code moi dhsx
    async tsbtnBBBG_Click(){
        this.$root.showLoading(true);
        let res = ''
        await frmChungTu_TBaoAPI.xemBaoCao(this.axios, {hdKhId: this.hdkh_id})
            .then(
                (response) => {
                  res = response
                }
            )
            .catch(function() {
                
            })
            .finally(function() {

            })

        this.$root.showLoading(false);
        res ? previewPrint(res.data) : ''
    },
    async gridviewDanhSach_FocusedRowChanged(row){
        if (this.ts_xem_bienban_bangiao){
            if (row){
                this.hdkh_id = row.HDKH_ID || 0
                this.Enabled.tsbtnBBBG = this.hdkh_id.toString() != '0'
            }
            else{
                this.hdkh_id = 0;
                this.Enabled.tsbtnBBBG = false
                return
            }
        }
    }

  },
created: async function() {
    
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
    .disabled a {
        pointer-events: none;
        cursor: default;
        color: #6c757d !important;
    }

    .disabled a:hover {
        cursor: default;
        color: #6c757d;
    }
</style>