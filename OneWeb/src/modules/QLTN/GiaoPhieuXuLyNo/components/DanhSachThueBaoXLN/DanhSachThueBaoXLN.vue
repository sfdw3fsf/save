<template src="./DanhSachThueBaoXLN.html"></template>

<script>
import {
  GridKhachHangCollumns,
  GridLichSuTTCollumns,
  Grid_DsthuebaoCollumns,
  GridLichSuTBCollumns,
} from "./common";
import API from "../../../api/GiaoPhieuXuLyNo";
import { export_json_to_excel } from "../../../TraCuuDichVu/Export2Excel"
import Vue from 'vue'
export default {
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      cacmatb: "",
      cacchukyno: "",
      matb_hienthi: "",
      vsochukylchon: null,
      cacthangno: "",
      vkieutra: 0, //0: Trả theo mã thanh toán, 1: Trả theo mã thuê bao.
      vsoluong_tb: null, //Số lượng mã thuê bao trong mã thanh toán.
      vma_tt: "",
      vkyhoadon: "",
      vdichvuvt_id: null,
      vcac_matb: "",
      vkieu: 2, //Khách hàng
      dt_KH: [],
      dt_TB: [],
      dt_LS: [],
      dt_thuebao: [],
      textDSKH: "Danh sách thanh toán",
      //components:
      gridKhachHang: [],
      gridKhachHangOneSelectedChanged: {},
      gridKhachHangCollumn: GridKhachHangCollumns,
      gridLichSuTT: [],
      gridLichSuTTCollumn: GridLichSuTTCollumns,
      gridLichSuTT_Key: 1,
      grid_Dsthuebao: [],
      grid_DsthuebaoCollumn: Grid_DsthuebaoCollumns,
      grid_Dsthuebao_Key: 1,
      gridLichSuTB: [],
      gridLichSuTBCollumn: GridLichSuTBCollumns,
      gridLichSuTB_Key: 1,
      countTemplate: function() {
        return { template: Vue.component('countTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      },
      sumTemplate: function() {
        return { template: Vue.component('sumTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {return {data: {data: {}}};}
          })
        }
      }
    };
  },
  computed: {
    CountAggregatesColumns () {
      return [{ field: 'MA_XLN', type: 'Count', format: 'N0', footerTemplate: this.countTemplate }]
    },
    SumAggregatesColumns () {
      return [{ field: 'TONGNO', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }]
    },
  },
  methods: {
    dialogOpen() {
      this.gridKhachHang = this.dt_KH;
      if (this.vkieu == 1) {
        this.textDSKH = "Danh sách thanh toán";
        GridKhachHangCollumns[0].headerText = "Mã TT";
        GridKhachHangCollumns[1].headerText = "Tên thanh toán";
      } else {
        this.textDSKH = "Danh sách khách hàng";
        GridKhachHangCollumns[0].headerText = "Mã KH";
        GridKhachHangCollumns[1].headerText = "Tên khách hàng";
      }
      this.$refs.dlgDSTBXLN.show();
    },
    reset() {
      this.cacmatb = "";
      this.cacchukyno = "";
      this.matb_hienthi = "";
      this.vsochukylchon = null;
      this.cacthangno = "";
      this.vkieutra = 0; //0= Trả theo mã thanh toán; 1= Trả theo mã thuê bao.
      this.vsoluong_tb = null; //Số lượng mã thuê bao trong mã thanh toán.
      this.vma_tt = "";
      this.vkyhoadon = "";
      this.vdichvuvt_id = null;
      this.vcac_matb = "";
      this.vkieu = 2; //Khách hàng
      this.dt_KH = [];
      this.dt_TB = [];
      this.dt_LS = [];
      this.dt_thuebao = [];
    },
    dialogClose() {
      this.$refs.dlgDSTBXLN.hide();
      this.reset();
    },
    isNullEmpty(value) {
      return value === null || value === "";
    },
    exportExcel(column, dataSource) {
      const tHeader = column.map((item) => item.headerText);
      const filterVal = column.map((item) => item.fieldName);
      const data = this.formatJson(filterVal, dataSource);
      export_json_to_excel({
        header: tHeader,
        data,
        filename: 'export',
        sheetname: 'export',
        bookType: 'xlsx',
      });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    onExcelExport(type) {
      let column_temp = [];
      let data_temp = [];
      if (type === 1) {
        column_temp = this.gridKhachHangCollumn;
        data_temp = this.gridKhachHang;
      } else if (type === 2) {
        column_temp = this.gridLichSuTTCollumn;
        data_temp = this.gridLichSuTT;
      } else if (type === 3) {
        column_temp = this.grid_DsthuebaoCollumn;
        data_temp = this.grid_Dsthuebao;
      } else {
        column_temp = this.gridLichSuTBCollumn;
        data_temp = this.gridLichSuTB;
      }
      
      if (data_temp.length === 0)
        this.$toast.error('Không có dữ liệu để xuất Excel!');
      else
        this.exportExcel(column_temp, data_temp);
    },
    async selectedRowChangedKhachHang(value) {
      console.log(value);
      this.gridKhachHangOneSelectedChanged = value;
      if (!this.isNullEmpty(value)) {
        try {
          this.loading(true);
          let data = {
            pKieuXuLyId: this.vkieu,
            pMaXLN: value.MA_XLN,
            pKyCuoc: this.vkyhoadon,
          };
          let response = await API.getDSThueBaoXLN(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.grid_Dsthuebao = response.data.data;
            this.grid_Dsthuebao_Key++;
          }
        } catch (error) {
          this.$toast.error(error.data.message_detail);
        } finally {
          this.loading(false);
        }

        //ls
        try {
          this.loading(true);
          let data = {
            pKieuXuLyNo: this.vkieu,
            pMaXuLyNo: value.MA_XLN,
            pKyCuoc: this.vkyhoadon,
          };
          let response = await API.getThongTinLichSuNoThanhToan(
            this.axios,
            data
          );
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.gridLichSuTT = response.data.data;
            this.gridLichSuTT_Key++;
          }
        } catch (error) {
          this.$toast.error(error.data.message_detail);
        } finally {
          this.loading(false);
        }
      }
    },
    async selectedRowChangedDsthuebao(value) {
      console.log(value);
      if (!this.isNullEmpty(value)) {
        try {
          this.loading(true);
          let data = {
            pKieuXuLyId: this.vkieu,
            pMaXLN: this.gridKhachHangOneSelectedChanged.MA_XLN,
            pKyCuoc: this.vkyhoadon,
            pMaTB: value.MA_TB,
          };
          let response = await API.getThongTinNoThueBao(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.gridLichSuTB = response.data.data;
            this.gridLichSuTB_Key++;
          }
        } catch (error) {
          this.$toast.error(error.data.message_detail);
        } finally {
          this.loading(false);
        }
      }
    },
  },
};
</script>

<style src="./DanhSachThueBaoXLN.scss">
</style>