<template src='./SearchDigitizationBill.html'>

</template>

<script>
import api from './api'
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'

export default {
  data () {
    return {
      cboLoaiDL:{
        list: [],
        SelectedValue: null,
      },
      dsDuLieu:{
        list:[],
        cols: [
          {
            fieldName: "ma_tt",
            headerText: "Mã thanh toán",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "so_dt",
            headerText: "Số điện thoại",
            allowFilter: true,
            allowSorting:false,
          },
          {
            fieldName: "tongtien",
            headerText: "Tổng tiền",
            allowFilter: true,
            allowHtml : true,
            allowSorting:false
          },
          {
            fieldName: "no_dk",
            headerText: "Nợ đầu kỳ",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "no_ps",
            headerText: "Nợ phát sinh",
            allowFilter: true,
            allowSorting:false
          },
          {
            fieldName: "kyhoadon",
            headerText: "Kỳ hóa đơn",
            allowFilter: true,
            allowSorting:false,
          },

        ]
      }
    }
  },
  methods:{
    async frmDoiSoatTD10a_Load(){
      try{
        this.loading(true);
        let response = await api.lay_nguon_dl_doisoat_sohoa_thucuoc(this.axios,{});
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.cboLoaiDL.list = response.data.data
          if(this.cboLoaiDL.list.length > 0){
            this.cboLoaiDL.SelectedValue = this.cboLoaiDL.list[0].id;
          }
        }
      }
      catch (e){
        this.$toast.error(e)
      }finally {
        this.loading(false)
      }

    },
    async tsbtnTimKiem_Click(){
      try{
        this.loading(true);
        let response = await api.lay_dl_doisoat_sohoa_thucuoc(this.axios,{id:this.cboLoaiDL.SelectedValue});
        if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.dsDuLieu.list = response.data.data;
        }
      }
      catch (e){
        this.$toast.error(e)
      }finally{
      this.loading(false)
    }
    },
    numberWithCommas(n) {
      try{
        var parts = n.toString().split(".");
        return (parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +(parts[1] ? "." + parts[1] : ""));
      }
      catch(e){
        return n;
      }

    },
    queryCellInfo: function (args) {
      if (args.column.field === "tongtien"){
        var result = args.data.tongtien;
        var setVal = this.numberWithCommas(result);
        args.data.tongtien = setVal;
        args.cell.innerText = setVal;
      }
      if (args.column.field === "no_dk"){
        var result = args.data.tongtien;
        var setVal = this.numberWithCommas(result);
        args.data.tongtien = setVal;
        args.cell.innerText = setVal;
      }
      if (args.column.field === "no_ps"){
        var result = args.data.tongtien;
        var setVal = this.numberWithCommas(result);
        args.data.tongtien = setVal;
        args.cell.innerText = setVal;
      }
    },
    btnXuatExcel () {
      console.log(this.dsDuLieu.list);
      if (this.dsDuLieu.list.length <= 0) {
        this.$toast.error('Chưa có dữ liệu để xuất excel!')
      } else {
        let excelExportProperties = {
          fileName: 'Thông tin đối soát số hóa thu cước.xlsx',
          dataSource: this.dsDuLieu.list,
        }

        this.$refs.grid.excelExport(excelExportProperties)
      }
    },

  },
  mounted(){
    this.frmDoiSoatTD10a_Load();

  }
}
</script>

