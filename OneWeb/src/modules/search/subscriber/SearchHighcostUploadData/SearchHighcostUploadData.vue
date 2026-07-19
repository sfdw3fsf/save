<template src="./SearchHighcostUploadData.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./API";
import gridview from "@/components/Shared/gridview";
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";
import SelectExt from "@/components/Controls/SelectExt";
import DatePicker from "vue2-datepicker";
// import axios from "axios";

// CSS
import "@/assets/vendor/bootstrap-table/bootstrap-table.min.css";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css";
// JS
import "@/assets/vendor/jquery/split";
import "@/assets/vendor/bootstrap-table/bootstrap-table.min";
import "@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min";
//
export default {
  components: {
    breadcrumb,
    VueFlatPickr,
    gridview,
    API,
    FormSearch,
    SelectExt,
    DatePicker,
  },
  name: "SearchHighcostUploadData",
  data() {
    return {
      form: {
        tableType: [],
        displayType: [],
        dstinh: [],
        TuNgay: "",
        DenNgay: "",
        phone: "",
      },
      sLoaiBang: "",
      kieu: "",
      dsUploadCuocCao: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          { fieldName: "ROW_STT", headerText: "STT", allowFiltering: true },
          { fieldName: "SOMAY", headerText: "Số máy", allowFiltering: true },
          { fieldName: "TINH", headerText: "Mã tỉnh", allowFiltering: true },
          {
            fieldName: "NATL_GPRS",
            headerText: "NATL_GPRS",
            allowFiltering: true,
          },
          {
            fieldName: "NATIONAL",
            headerText: "NATIONAL",
            allowFiltering: true,
          },
          { fieldName: "INTL", headerText: "INTL", allowFiltering: true },
          {
            fieldName: "INTL_VND",
            headerText: "INTL_VND",
            allowFiltering: true,
          },
          { fieldName: "NGAYPS", headerText: "NGAYPS", allowFiltering: true },
          {
            fieldName: "TRANGTHAI",
            headerText: "TRANGTHAI",
            allowFiltering: true,
          },
        ],
      },
    };
  },
   mounted() {
    const current = new Date();
    const date =
      current.getDate() +
      "/" +
      (current.getMonth() + 1) +
      "/" +
      current.getFullYear();
    this.form.TuNgay = moment(date, "DD/MM/YYYY").format("DD/MM/YYYY");
    this.form.DenNgay = moment(date, "DD/MM/YYYY").format("DD/MM/YYYY");
  },
  methods: {
    btnRefresh() {
      this.dsUploadCuocCao.list = null;
      this.form.tableType = [];
      this.form.displayType = [];
      this.form.dstinh = [];
      this.form.TuNgay = "";
      this.form.DenNgay = "";
      this.form.phone = "";
    },
    btnSearchClicked() {
      this.Clear();
      var pdata = {};
      if (this.form.phone === "" || this.form.phone == null) {
        this.$toast.error("Bạn chưa nhập Số máy tìm kiếm");
      } else if (this.form.TuNgay > this.form.DenNgay) {
        this.$toast.error("Từ ngày phải nhỏ hơn đến ngày");
      } else if (this.form.TuNgay === "" || this.form.TuNgay == null) {
        this.$toast.error("Bạn chưa nhập Từ ngày");
      } else if (this.form.DenNgay === "" || this.form.DenNgay == null) {
        this.$toast.error("Bạn chưa nhập Đến ngày");
      } else if (typeof(this.form.displayType) === "object" || this.form.displayType == null) {
        this.$toast.error("Bạn chưa chọn kiểu hiển thị");
      } else if (typeof(this.form.tableType) === "object" || this.form.tableType == null) {
        this.$toast.error("Bạn chưa chọn Loại bảng");
      } else if (this.form.dstinh === "" || this.form.dstinh == null) {
        this.$toast.error("Bạn chưa chọn Tỉnh");
      } 
      else {
        pdata.so_tb = this.form.phone.trim();
        pdata.tungay = this.format_date(this.form.TuNgay);
        pdata.denngay = this.format_date(this.form.DenNgay);
        pdata.loaibang = this.form.tableType;
        pdata.kieu_hienthi = this.form.displayType;
        this.sLoaiBang = this.form.tableType;
        this.kieu = this.form.displayType;
        console.log(pdata,"pdata");
        // call api thông tin đăng ký
        API.danh_sach_cuoc_cao(this.axios, pdata).then((response) => {
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
            this.dsUploadCuocCao.list = response.data.data;
          } else {
            this.$toast.error("Không có dữ liệu hiện thị");
          }
        });
      }
    },
    Clear() {
      this.dsUploadCuocCao.list = null;
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format("DD/MM/YYYY");
      }
    },
    btnProcessing(loabang, kieu) {
      this.CallAPI(loabang, kieu);
    },
    btnProcessCancel(loabang, kieu) {
      this.CallAPI(loabang, kieu);
    },
    CallAPI(loabang, kieu) {
      debugger;
      let $thisData = this;
      $("table > tbody  > tr").each(function (data) {
        var $this = $(this);
        if (this.attributes.length > 0) {
          if ($(this).attr("aria-selected")) {
            var pdata = {};
            pdata.ngayps = $this.find("td:eq(9)").text();
            pdata.so_tb = $this.find("td:eq(3)").text();
            pdata.loaibang = loabang;
            pdata.kieu = kieu;
            console.log(pdata);
            API.danh_sach_cuoc_cao_xu_ly($thisData.axios, pdata).then(
              (response) => {
                if (response.data.message == "No Content") {
                  $thisData.$toast.error("Không có dữ liệu hiện thị");
                }
                if (
                  response &&
                  response.data &&
                  response.data.data &&
                  response.data.error &&
                  response.data.error_code === "BSS-00000000" &&
                  response.data.data
                ) {
                  $thisData.$toast.success("Xử lý thành công");
                } else {
                  $thisData.$toast.error("Cập nhật thất bại");
                }
              }
            );
          } else {
            $thisData.$toast.error("Chọn dòng cần xử lý");
          }
        }
      });
    },
  },
};
</script> 

