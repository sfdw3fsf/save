<template src="./frmFormTb.html"></template>

<script>
import Vue from "vue";
import moment from "moment";
Vue.prototype.$eventHub = new Vue();

import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Page,
  Toolbar,
  GridPlugin,
} from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);
export default {
  props: ["dulieu"],
  provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      ExcelExport,
      Edit,
      Aggregate,
      Toolbar,
      Page,
    ],
  },
  props: ["dulieu"],

  data() {
    return {
      sumTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright redcolor"  v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;color:red !important" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
      checkboxTemplate: function () {
        return {
          template: Vue.component("checkboxTemplate", {
            template: `<input type="checkbox"    @change="onchange"  v-model="data.DANGKY ==1 ? true : false" />`,
            data() {
              return { data: { data: {} } };
            },
            methods: {
              onchange(value) {
                console.log(this.data);
                console.log([value.target.checked]);
                this.data.DANGKY = value.target.checked ? 1 : 0;
              },
            },
          }),
        };
      },
      aggregatesColumns: [
        {
          field: "TIEN",
          type: "custom",
          format: "N0",
          footerTemplate: this.sumTemplate,
          customAggregateFn: this.TinhTongAggr("VAT"),
        },
        // {
        //   field: "VAT",
        //   type: "Custom",
        //   format: "N0",
        //   footerTemplate: this.sumTemplate,
        //   customAggregateFn: this.TinhTongAggr("VAT"),
        // },
        // {
        //   field: "GIA_BLOCK_TIEP",
        //   type: "Custom",
        //   format: "N0",
        //   footerTemplate: this.sumTemplate,
        //   customAggregateFn: this.TinhTongAggr("GIA_BLOCK_TIEP"),
        // },
        // {
        //   field: "VAT_BLOCK_TIEP",
        //   type: "Custom",
        //   format: "N0",
        //   footerTemplate: this.sumTemplate,
        //   customAggregateFn: this.TinhTongAggr("VAT_BLOCK_TIEP"),
        // },
      ],

      gridDSTB: [
        {
          STT: 1,
          LOAITBI_ID: 276,
          MA_TBI: "CHECK<100,000",
          LOAI_TBI: "Số lượng mã xác thực tư 50,000-99,999",
          SOLUONG: 1,
          DANGKY: 1,
          TIEN: 120,
          TIEN_THUE: 0,
          VAT: 12,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 2,
          LOAITBI_ID: 410,
          MA_TBI: "SIM_D500",
          LOAI_TBI: "Phí mua SIM D500",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 30455,
          TIEN_THUE: 0,
          VAT: 3045,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 3,
          LOAITBI_ID: 413,
          MA_TBI: "V-WIFI_AP230",
          LOAI_TBI: "Dell Networking Aerohive AP230",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 11306818,
          TIEN_THUE: 0,
          VAT: 1130682,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 4,
          LOAITBI_ID: 422,
          MA_TBI: "V-WIFI_CONVER1G",
          LOAI_TBI: "Converter Quang 1G",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 1192727,
          TIEN_THUE: 0,
          VAT: 119273,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 5,
          LOAITBI_ID: 410,
          MA_TBI: "V-WIFI_UiAS-RM",
          LOAI_TBI: "Thiết bị Routerboard Mikrotik RB3011UiAS-RM",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 5397727,
          TIEN_THUE: 0,
          VAT: 539773,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 6,
          LOAITBI_ID: 413,
          MA_TBI: null,
          LOAI_TBI: "Cisco AP outdoor",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 0,
          TIEN_THUE: 0,
          VAT: 0,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 7,
          LOAITBI_ID: 422,
          MA_TBI: null,
          LOAI_TBI: "DrayTek Vigor2926",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 3318182,
          TIEN_THUE: 0,
          VAT: 331818,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
        {
          STT: 8,
          LOAITBI_ID: 276,
          MA_TBI: null,
          LOAI_TBI: "Router wifi TP-link TL-WR840N",
          SOLUONG: 1,
          DANGKY: 0,
          TIEN: 0,
          TIEN_THUE: 0,
          VAT: 0,
          TONG_TIEN: 0,
          TONG_THUE: 0,
          TIEN_KM: 0,
          VAT_KM: 0,
          TIEN_TRATRUOC: 0,
          VAT_TRATRUOC: 0,
          TIEN_TRAGOP: 0,
          VAT_TRAGOP: 0,
          SERIAL: null,
          TYLE_VAT: 10,
          TYLE_VAT_ID: 1,
          BLOCK_DAU: 0,
          BLOCK_TIEP: 0,
          GIA_BLOCK_TIEP: 0,
          VAT_BLOCK_TIEP: 0,
          SL_CHA: 0,
        },
      ],
    };
  },
  computed: {
    TCaggregatesColumns() {
      return [
        {
          field: "TIEN",
          type: "Custom",
          footerTemplate: this.sumTemplate,
          customAggregateFn: this.TinhTongAggr,
        },
      ];
    },
  },
  methods: {
    onRowSelected(data) {
      console.log(data);
      this.$refs.gridDs.refreshAggregates();
    },
    closeForm() {
      console.log(this.gridDSTB);
      //   console.log(this.$refs.gridDs.getSelectedRecords());
      //   this.$refs.gridDs.refreshAggregates();
    },
    // tienAggregateFn: function (d) {
    //   return this.TinhTongAggr(d, "TIEN");
    // },
    // vatAggregateFn: function (d) {
    //   return this.TinhTongAggr(d, "VAT");
    // },
    // giablkAggregateFn: function (d) {
    //   return this.TinhTongAggr(d, "GIA_BLOCK_TIEP");
    // },
    // vatblkAggregateFn: function (d) {
    //   return this.TinhTongAggr(d, "VAT_BLOCK_TIEP");
    // },
    formatPrice(value) {
      let val = (value / 1).toFixed(0).replace(".", ",");
      return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    },
    TinhTongAggr(e) {
      if (this.$refs.gridDs) {
        //let d = this.$refs.gridDs.getSelectedRecords();
        let sum = 0;
        for (const iterator of this.$refs.gridDs.getSelectedRecords()) {
          sum += iterator.TIEN;
        }
        console.log("sum", this.formatPrice(sum));
        return this.formatPrice(sum);
      }
      return 0;
      //   var fdata = d.result.filter((item) => item.dangky == 1);
      //   return fdata && fdata.length > 0
      //     ? fdata.map((x) => x[e]).reduce((a, b) => a + b)
      //     : 0;
    },
  },
  async mounted() {
    // this.loaitb_id = this.dulieu._loaitb_id ? this.dulieu._loaitb_id : 0;
    // this.GetDsTbiMua();
  },
};
</script>

<style src="./frmFormTb.scss">
</style>