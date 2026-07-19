<template src="./SearchPackagesPromotions.html"></template>

<script>
import breadcrumb from "@/components/breadcrumb";
import VueFlatPickr from "vue-flatpickr-component";
import API from "./API";
import gridview from "@/components/Shared/gridview";
import FormSearch from "./FormSearch/FormSearch";
import moment from "moment";
import SelectExt from "@/components/Controls/SelectExt";

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
  },
  name: "SearchPackagesPromotions",
  data() {
    return {
      form: {
        thangKM: 0,
        phone: "",
        maNhom: "Khuyễn mại",
      },
      thongtingoikm: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          {
            fieldName: "INDEX",
            headerText: "STT",
            allowFiltering: false,
            cssClass: "text-right",
            width: 70,
          },
          { fieldName: "MA_TB", headerText: "Số máy", allowFiltering: true },
          { fieldName: "MA_KH", headerText: "Mã KH", allowFiltering: true },
          { fieldName: "MA_TINH", headerText: "Mã tỉnh", allowFiltering: true },
          {
            fieldName: "CV_KHUYENMAI",
            headerText: "Công văn KM",
            allowFiltering: true,
          },
          {
            fieldName: "HINHTHUC_DK",
            headerText: "Hình thức KM",
            allowFiltering: true,
          },
          { fieldName: "NGAY_DK", headerText: "Ngày ĐK", allowFiltering: true },
          {
            fieldName: "NGAY_BD",
            headerText: "Ngày bắt đầu",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_KT",
            headerText: "Ngày kết thúc",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOI_DK",
            headerText: "Người CN",
            allowFiltering: true,
          },
          { fieldName: "CHANNEL", headerText: "Kênh", allowFiltering: true },
          {
            fieldName: "THANG",
            headerText: "Tháng hiệu lực",
            allowFiltering: true,
          },
        ],
      },
      lichsugoikm: {
        list: [],
        header: [],
        value: {},
        isEnabled: true,
        columns: [
          {
            fieldName: "INDEX",
            headerText: "STT",
            allowFiltering: false,
            cssClass: "text-right",
            width: 70,
          },
          { fieldName: "MA_TB", headerText: "Số máy", allowFiltering: true },
          { fieldName: "LOAI", headerText: "Trạng thái", allowFiltering: true },
          {
            fieldName: "TEN_HTKM",
            headerText: "Tên hình thức",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_CONGVAN",
            headerText: "Tên công văn",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_THUCHIEN",
            headerText: "Ngày ĐK",
            allowFiltering: true,
          },
          { fieldName: "NGAY_BD", headerText: "Ngày BĐ", allowFiltering: true },
          {
            fieldName: "TIME_END",
            headerText: "Ngày KT",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOI_CN",
            headerText: "Người TH",
            allowFiltering: true,
          },
          { fieldName: "KENH", headerText: "Kênh", allowFiltering: true },
        ],
      },
    };
  },
  methods: {
    onSubmitSearch() {
      this.CallAPI();
    },
    CallAPI() {
      var pdata = {};
      if (this.form.phone === "" || this.form.phone == null) {
        this.$toast.error("Bạn chưa nhập Số máy tìm kiếm");
      } else {
        pdata.so_tb = "84" + this.form.phone.trim();
        pdata.thang = this.form.thangKM;
      }
      console.log(pdata);
      // call api thông tin đăng ký
      API.thong_tin_dang_ky(this.axios, pdata).then((response) => {
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
          // this.thongtingoikm.list = response.data.data;
          let listDataTemp = [];
          let sothutu = 0;
          response.data.data.forEach(function (item) {
            sothutu++;
            listDataTemp.push({
              INDEX: sothutu,
              MA_TB: item.MA_TB,
              MA_KH: item.MA_KH,
              MA_TINH: item.MA_TINH,
              CV_KHUYENMAI: item.CV_KHUYENMAI,
              HINHTHUC_DK: item.HINHTHUC_DK,
              NGAY_DK: item.NGAY_DK,
              NGAY_BD: item.NGAY_BD,
              NGAY_KT: item.NGAY_KT,
              NGUOI_DK: item.NGUOI_DK,
              // CHANNEL: item.kenh,
              THANG: item.THANG,
            });
          });
          this.thongtingoikm.list = listDataTemp;
          this.loading(false);
        }
      });
      // call api lịch sử thông tin đăng ký
      API.lich_su_dang_ky_huy(this.axios, pdata).then((response) => {
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
          // this.lichsugoikm.list = response.data.data;
          let listDataTemp = [];
          let sothutu = 0;
          response.data.data.forEach(function (item) {
            sothutu++;
            listDataTemp.push({
              INDEX: sothutu,
              MA_TB: item.MA_TB,
              LOAI: item.LOAI,
              TEN_HTKM: item.TEN_HTKM,
              TEN_CONGVAN: item.TEN_CONGVAN,
              NGAY_THUCHIEN: item.NGAY_THUCHIEN,
              NGAY_BD: moment(item.NGAY_BD, "DD/MM/YYYY HH:mm:ss").format(
                "DD/MM/YYYY HH:mm:ss"
              ),
              TIME_END: item.TIME_END,
              NGUOI_CN: item.NGUOI_CN,
              KENH: item.KENH,
            });
          });
          this.lichsugoikm.list = listDataTemp;
          this.loading(false);
        }
      });
    },
    btnSearchClicked() {
      this.loading(true);
      this.Clear();
      this.CallAPI();
    },
    Clear() {
      this.lichsugoikm.list = null;
      this.thongtingoikm.list = null;
    },
    format_date(value) {
      if (value) {
        return moment(String(value)).format("DD/MM/YYYY");
      }
    },
  },
};
</script>
 <style src='./SearchPackagesPromotions.css'></style>