<template src="./SearchCustomersSameNumber.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./API";
import gridview from "@/components/Shared/gridview";
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";

// CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
// JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
//
export default {
  components: { breadcrumb, VueFlatPickr, gridview, API, FormSearch },
  name: "SearchPackagesPromotions",
  data() {
    return {
      form: {
        thongtin_tracuu: "",
        chkGiayTo: "",
        chkMaCQ_KH: "",
      },
      thongtintracuu: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          {
            fieldName: "ROW_STT",
            headerText: "STT",
            allowFiltering: true,
            cssClass: "text-right",
          },
          {
            fieldName: "MA_CQ",
            headerText: "Mã cơ quan",
            allowFiltering: true,
          },
          {
            fieldName: "MA_KH",
            headerText: "Mã khách hàng",
            allowFiltering: true,
          },
          {
            fieldName: "SOMAY",
            headerText: "Mã thuê bao",
            allowFiltering: true,
          },
          { fieldName: "TEN_TT", headerText: "Họ tên", allowFiltering: true },
          {
            fieldName: "DIACHI_TT",
            headerText: "Địa chỉ",
            allowFiltering: true,
          },
          {
            fieldName: "MA_NV",
            headerText: "Mã nhân viên",
            allowFiltering: true,
          },
          { fieldName: "TONGNO", headerText: "Tổng nợ", allowFiltering: true },
          {
            fieldName: "SO_GT",
            headerText: "Số giấy tờ",
            allowFiltering: true,
          },
          {
            fieldName: "TRANGTHAI",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
        ],
      },
    };
  },
  mounted() {
    this.form.chkMaCQ_KH = "";
    this.form.chkGiayTo = "0";
  },
  methods: {
    onSubmitSearch() {
      this.btnSearchClicked();
    },
    btnRefresh() {
      this.thongtintracuu.list = null;
      this.form.thongtin_tracuu = "";
      this.form.chkMaCQ_KH = "";
      this.form.chkGiayTo = "0";
    },
    btnSearchClicked() {
      this.Clear();
      var pdata = {};
      if (
        this.form.thongtin_tracuu === "" ||
        this.form.thongtin_tracuu == null
      ) {
        this.$toast.error("Bạn chưa nhập thông tin tra cứu");
      } else {
        pdata.so_giayto = this.form.thongtin_tracuu;
        console.log(pdata);
        // call api khách hàng trùng giấy tờ
        API.thong_tin_tra_cuu(this.axios, pdata).then((response) => {
          if (response.data.message == "No Content") {
            this.$toast.error("Không có dữ liệu hiện thị");
          }
          if (
            response &&
            response.data &&
            response.data.data &&
            response.data.error &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data
          ) {
            // render gridview thông tin
            this.thongtintracuu.list = response.data.data;
          }
        });
      }
    },
    Clear() {
      this.thongtintracuu.list = null;
    },
  },
};
</script>