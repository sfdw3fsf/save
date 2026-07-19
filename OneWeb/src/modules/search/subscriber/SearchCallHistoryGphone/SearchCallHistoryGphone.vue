<template src="./SearchCallHistoryGphone.html"></template>
<style scoped src="./SearchCallHistoryGphone.scss"></style>

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
  name: "SearchCallHistoryGphone",
  created() {},

  mounted() { 
    this.$nextTick(() => this.$refs.focus.focus());
  },

  watch: {
  },

  data() {
    return {

      header: {
        title: 'TRA CỨU LỊCH SỬ GỌI GPHONE',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu lịch sử Gphone',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      txtSoMay: "",

      // Chưa map fieldName do API tra cứu chưa trả về được giá trị, đang chờ update API.
      ds_lichsu_gphone: {
      header: [
        {
          headerText: "STT",
          fieldName: "",
          allowSorting: false,
          width: "60",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Thời gian bắt đầu",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Thời gian kết thúc",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Called",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Gọi được/Không",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Call Zone",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Called Zone",
          fieldName: "",
          allowSorting: false,
          width: "100",
          textAlign: "left",
          autoFit: "true",
          allowFiltering: true,
        },
        {
          headerText: "Cell ID",
          fieldName: "",
          allowSorting: false,
          width: "100",
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
    async Timkiem_Click()
    {
      this.ds_lichsu_gphone.list = []
      if (this.txtSoMay == "")
      {
        this.$toast.warning("Bạn chưa nhập số máy tìm kiếm!");
        this.$nextTick(() => this.$refs.focus.focus());
        return
      }

      var body = {
        "so_tb": "84" + this.txtSoMay
      }
      // Tra cứu dữ liệu
      this.loading(true);
      var data_gphone = await API.gphone_tracuu_tt_call_hist(this.axios, body);
      if (
          data_gphone.data.error_code == "BSS-00000000" &&
          data_gphone.data.data.length > 0
      ) {
        this.loading(false);
        this.ds_lichsu_gphone.list = data_gphone.data.data
      }
      else {
        this.loading(false);
        this.$toast.warning("Không tìm thấy thông tin của số máy "+ this.txtSoMay);
      }
    }
  },
};
</script>