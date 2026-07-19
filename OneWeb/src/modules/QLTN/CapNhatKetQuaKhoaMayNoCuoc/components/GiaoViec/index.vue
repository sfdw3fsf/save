<template src="./index.html"></template>

<script>
import Vue from "vue";
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from "@syncfusion/ej2-dropdowns";
import moment from "moment";
MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);
export default {
  provide: {
    multiselect: [CheckBoxSelection],
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      dsNhanVienGV: {
        ds: [
          {
            TEN_DV: "sdfsd",
            MA_NV: "adsfa",
            TEN_NV: "dsfdsf",
            SO_DT: 2343242342,
            NVS: [
              { NHIEMVU_ID: 1, NHIEMVU: "thuc wdfdwrf 1" },
              { NHIEMVU_ID: 2, NHIEMVU: "thuccsdf 2" },
              { NHIEMVU_ID: 3, NHIEMVU: "thuc sxcfsdfsd 3" },
            ],
            KEY: 0,
            NSSSelect: [],
          },
        ],
      },
      dsNhanVienDaGiaoNV: {
        ds: [],
      },
      ds: [],
      vphieu: null,
      vdonvi: null,
      vnhanvienid: null,
      vnoidung: null,
      vghichu: null,
      vnhanvien_giao_id: null,
      vngaygiao: moment(new Date()).format("DD/MM/yyyy"),
      kt_load: true,
      vma_tb: "",
      vhdtb_id: 0,
      dsphieuGV: [],
      vloaihd: 0,
      vkieugiao: 0, // Hoangpkn : 03/06/2017 .0 : giao việc bình thường, 1: giao việc thu hồi vật tư
    };
  },
  methods: {
    dialogOpen() {
      this.$refs.dlgGiaoViecx.show();
      this.$toast.warning(
        "Chức năng giao việc qua '[UR2.4.007] - Tổ KTVT - Thi công Internet' đang pending"
      );
    },
    loadForm(){
         
    },
    formatDate(value) {
      return moment(value).format("DD/MM/yyyy");
    },
    dialogClose() {},
    btnGhiLai() {
      console.log(this.dsNhanVienGV.ds);
    },
    getColumnTemplateNhiemVu(parent) {
      return function () {
        return {
          template: {
            components: {},
            template: `
                           <ejs-multiselect
                            mode="CheckBox"
                            id="multiselect"
                            selectAllText="Chọn tất cả"
                            unSelectAllText="Bỏ chọn tất cả"                           
                            locale="vi-VN"
                            :dataSource="data.NVS"
                            :allowFiltering="true"
                            :fields="{value:'NHIEMVU_ID', text: 'NHIEMVU'}"
                            :showSelectAll="true"
                            :showDropDownIcon="true"
                            :change="valueChange"
                            :value="SelectedMutil"
                        ></ejs-multiselect>
                        `,
            data() {
              return {
                parent: parent, //this
                data: {}, //rowData
                SelectedMutil: [],
              };
            },
            computed: {},
            mounted() {
              console.log(this.parent);
              console.log(this.parent.dsNhanVienGV);
              console.log(this.data);
            },
            methods: {
              valueChange(e) {
                this.SelectedMutil = e.value;
              },
            },
            watch: {
              SelectedMutil(newVal) {
                let obj = this.parent.dsNhanVienGV.ds.find(
                  (item) => item.KEY == this.data.KEY
                );
                obj.NSSSelect = this.SelectedMutil;
                console.log(obj);
              },
            },
          },
        };
      };
    },
  },
  mounted() {},
};
</script>

<style>
</style>