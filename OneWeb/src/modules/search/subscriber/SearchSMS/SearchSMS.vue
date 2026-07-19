<template src="./SearchSMS.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./API";
import gridview from "@/components/Shared/gridview";
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";
import SelectExt from "@/components/Controls/SelectExt";
import DatePicker from "vue2-datepicker";
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
  name: "SearchSMS",
  data() {
    return {
      cbodauSo: {
        DataSource: [],
        value: null,
      },
      form: {
        TuNgay: "",
        DenNgay: "",
        DATA_REMAIN: 0,
      },
      selected: "1", // value of desired selection
      nguoidungnt: {
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
            fieldName: "MA_TB",
            headerText: "Mã thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "SHORT_MESSAGE",
            headerText: "Nội dung sms",
            allowFiltering: true,
          },
          { fieldName: "NGAY", headerText: "Ngày", allowFiltering: true },
          { fieldName: "DAUSO", headerText: "Đầu số", allowFiltering: true },
        ],
      },
      hethongnt: {
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
            fieldName: "MA_TB",
            headerText: "Mã thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "SHORT_MESSAGE",
            headerText: "Nội dung sms",
            allowFiltering: true,
          },
          { fieldName: "NGAY", headerText: "Ngày", allowFiltering: true },
          { fieldName: "DAUSO", headerText: "Đầu số", allowFiltering: true },
        ],
      },
    };
  },
  mounted() {
    this.GetDauSo();
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
    GetDauSo() {
      this.cbodauSo.DataSource = [];
      this.cbodauSo.value = null;
      API.dau_so(this.axios, {}).then((response) => {
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
          this.cbodauSo.DataSource = response.data.data;
          this.cbodauSo.value = this.cbodauSo.DataSource[0]["id"];
        }
      });
    },
    onSubmitSearch() {
      this.btnSearchClicked();
    },
    btnRefresh() {
      this.form.phone = "";
      this.cbodauSo.value = this.cbodauSo.DataSource[0]["id"];
      this.form.TuNgay = "";
      this.form.DenNgay = "";
      this.nguoidungnt.list = null;
      this.hethongnt.list = null;
    },
    btnSearchClicked() {
      this.Clear();
      var pdata = {};
      if (this.form.phone === "" || this.form.phone == null) {
        this.$toast.error("Bạn chưa nhập Số máy tìm kiếm");
      } else {
        pdata.so_tb = "84" + this.form.phone.trim();
      }
      //lấy dung lượng data
      API.nhom_taikhoan(this.axios, pdata).then((response) => {
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
          for (var i = 0; i < response.data.data.length; i++) {
            if (response.data.data[i].GROUP_NAME == "Data") {
              this.form.DATA_REMAIN = response.data.data[i].REMAIN;
            } else {
              this.form.DATA_REMAIN = 0;
            }
          }
        }
      });
      pdata.dauso = this.cbodauSo.value;
      pdata.date_from = this.format_date(this.form.TuNgay);
      pdata.date_to = this.format_date(this.form.DenNgay);
      //pdata.loai_tn = "1"; //1 người dùng; 2 hệ thống
      console.log(pdata);
      let _dauso = this.cbodauSo.value;
      if (
        _dauso == "888" ||
        _dauso == "9123" ||
        _dauso == "1414" ||
        _dauso == "9889"
      ) {
        pdata.loai_tn = "1";
        API.danhmuc_tracuu_sms_888_9123_1414_9889(this.axios, pdata).then(
          (response) => {
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
              this.nguoidungnt.list = response.data.data;
            } else {
              this.$toast.error("Không có dữ liệu người dùng nhắn tin hiển thị");
              this.nguoidungnt.list = null;
            }
          }
        );
        pdata.loai_tn = "2";
        API.danhmuc_tracuu_sms_888_9123_1414_9889(this.axios, pdata).then(
          (response) => {
            if (response.data.message == "No Content") {
              this.$toast.error("Không có dữ liệu hệ thống nhắn tin hiển thị");
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
              this.hethongnt.list = response.data.data;
            } else {
              this.$toast.error("Không có dữ liệu hệ thống nhắn tin hiển thị");
              this.hethongnt.list = null;
            }
          }
        );
      } else if (_dauso == "900" || _dauso == "USSD") {
        pdata.loai_tn = "1";
        //call api
        API.danhmuc_tracuu_sms_900_USSD(this.axios, pdata).then((response) => {
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
            this.nguoidungnt.list = response.data.data;
          } else {
            this.$toast.error("Không có dữ liệu người dùng nhắn tin hiển thị");
            this.nguoidungnt.list = null;
          }
        });
        pdata.loai_tn = "2";
        API.danhmuc_tracuu_sms_900_USSD(this.axios, pdata).then((response) => {
          if (response.data.message == "No Content") {
            this.$toast.error("Không có dữ liệu hệ thống nhắn tin hiển thị");
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
            this.hethongnt.list = response.data.data;
          } else {
            this.$toast.error("Không có dữ liệu hệ thống nhắn tin hiển thị");
            this.hethongnt.list = null;
          }
        });
      }
    },
    Clear() {
      this.nguoidungnt.list = null;
      this.hethongnt.list = null;
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format("DD/MM/YYYY");
      }
    },
  },
};
</script>
