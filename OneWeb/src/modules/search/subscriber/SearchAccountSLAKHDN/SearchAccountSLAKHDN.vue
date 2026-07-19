<template src="./SearchAccountSLAKHDN.html"></template>
<style scoped src="./SearchAccountSLAKHDN.scss"></style>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./api";
import gridview from "@/components/Shared/gridview";
import DataGridNV from "./components/DataGrid";

//CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
//JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";


export default {
  components: { breadcrumb, VueFlatPickr, gridview, API,DataGridNV},
  name: "SearchAccountSLAKHDN",
  created() {},

  mounted() {
    this.init();
    this.$nextTick(() => this.$refs.focus.focus())
  },

  watch: {
  },

  data() {
    return {

      header: {
        title: 'TRA CỨU THÔNG TIN TÀI KHOẢN TRÊN HỆ THỐNG SLA',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu Account SLA KHDN',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      isActice: false,
      txtMaKH:'',
       ds_taikhoan: {
        header: [
          {
            headerText: "code",
            fieldName: "code",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "name",
            fieldName: "name",
            allowSorting: false,
            width: "150",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "address",
            fieldName: "address",
            allowSorting: false,
            width: "180",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "mobile",
            fieldName: "mobile",
            allowSorting: false,
            width: "40",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "username",
            fieldName: "username",
            allowSorting: false,
            width: "40",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "email",
            fieldName: "email",
            allowSorting: false,
            width: "70",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "emailsended",
            fieldName: "emailsended",
            allowSorting: false,
            width: "40",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "sms",
            fieldName: "sms",
            allowSorting: false,
            width: "40",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },

       ds_thuebao: {
        header: [
          {
            headerText: "account",
            fieldName: "account",
            allowSorting: false,
            width: "90",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "bandwidth",
            fieldName: "bandwidth",
            allowSorting: false,
            width: "70",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "servicespec",
            fieldName: "servicespec",
            allowSorting: false,
            width: "70",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "servicetype",
            fieldName: "servicetype",
            allowSorting: false,
            width: "100",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "ip",
            fieldName: "ip",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "system",
            fieldName: "system",
            allowSorting: false,
            width: "70",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "rack",
            fieldName: "rack",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "shelf",
            fieldName: "shelf",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "slot",
            fieldName: "slot",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "port",
            fieldName: "port",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "vpi",
            fieldName: "vpi",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "vci",
            fieldName: "vci",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "slid",
            fieldName: "slid",
            allowSorting: false,
            width: "50",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "devicetype",
            fieldName: "devicetype",
            allowSorting: false,
            width: "80",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "techtype",
            fieldName: "techtype",
            allowSorting: false,
            width: "60",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
          {
            headerText: "vnptcode",
            fieldName: "vnptcode",
            allowSorting: false,
            width: "70",
            textAlign: "left",
            autoFit: "true",
            allowFiltering: true,
          },
        ],
        list: [],
      },


     };
  },

  methods: {
    async init() {
    },

    async Timkiem_Click()
    { 
     this.loading(true)
      this.ds_taikhoan.list = []
      this.ds_thuebao.list = []

      if (this.txtMaKH == "" )
      {
        this.$toast.warning("Bạn chưa nhập thông tin khách hàng!");
        this.$nextTick(() => this.$refs.focus.focus());
        return
      }

      var body_acc =
      {
          "Key": "TRA_CUU_ACC",
          "Condition": this.txtMaKH.toString()
      }
      var response_acc = await API.TraCuu(this.axios, body_acc)
      if (
        response_acc.data.errorCode == 0 &&
          response_acc.data.data && response_acc.data.data.data.length > 0) {
        this.loading(false)
        this.ds_taikhoan.list = response_acc.data.data.data
      }

      var body_tb =
      {
          "Key": "TRA_CUU_TB",
          "Condition": this.txtMaKH.toString()
      }
      var response_tt = await API.TraCuu(this.axios, body_tb)
      if (
        response_tt.data.errorCode == 0 &&
          response_tt.data.data && response_tt.data.data.data.length > 0) {
        this.loading(false)
        this.ds_thuebao.list = response_tt.data.data.data
      }
      this.loading(false)
    },

    onTabTK_Clicked() {
      this.isActice = false;
    },
    onTabTB_Clicked() {
      this.isActice = true;
    },
  },
};
</script>