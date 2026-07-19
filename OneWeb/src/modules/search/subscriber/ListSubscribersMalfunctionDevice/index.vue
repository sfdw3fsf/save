<template src="./template.html"></template>
<style src="./style.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import XLSX from "xlsx";
import moment from "moment";
import api from "./api";
import enums from "./enums";
import dummy from "./dummy";
export default {
  components: { breadcrumb },
  name: "ListSubscribersMalfunctionDevice",
  props: {
    isPopup: {
      type: Boolean,
    },
  },
  mounted() {
    // this.clearData()
    // this.$refs.maKHGocInput.focus()
    // this.addKeyDownEvent()
    this.getComboboxData();
  },
  destroyed() {},
  computed: {
    // enableDeleteBnt() {
    //   return this.gridboxDSPTT.selected !== null
    // }
  },
  watch: {
    loading: function (val, oldval) {
      this.$root.showLoading(val);
    },
    openedPopup: function (val, oldval) {
      if (val) {
        this.removeKeyDownEvent();
      } else {
        this.addKeyDownEvent();
      }
    },
  },
  data() {
    return {
      loading: false,
      header: {
        title: "Danh sách thuê bao, thiết bị sự cố",
        list: [],
      },
      gridboxKQTK: {
        columns: [
          {
            fieldName: "ma_canhbao",
            headerText: "Mã cảnh báo",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "kieu_phieuxuat",
            headerText: "Kiểu phiếu xuất",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "loai_canhbao",
            headerText: "Loại cảnh báo",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "slg_canhbao",
            headerText: "Số phiếu cảnh báo",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "tinh",
            headerText: "Tỉnh",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "nguyennhan",
            headerText: "Nguyên nhân cảnh báo",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "thamso_tbi",
            headerText: "Tham số thiết bị",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "slg_thuebao",
            headerText: "Số lượng thuê bao",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ngay_bh",
            headerText: "Ngày giờ xuất phiếu",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "trangthai_bh",
            headerText: "Trạng thái xử lý phiếu",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ct_hong",
            headerText: "Chi tiết nguyên nhân hỏng",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ghichu_xl",
            headerText: "Ghi chú xử lý",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày giờ xử lý",
            allowFiltering: true,
            allowSorting: true,
          },
          // {
          //   fieldName: "NVKT xử lý",
          //   headerText: "NVKT xử lý",
          //   allowFiltering: true,
          //   allowSorting: true,
          // },
          // {
          //   fieldName: "Đơn vị xử lý",
          //   headerText: "Đơn vị xử lý",
          //   allowFiltering: true,
          //   allowSorting: true,
          // },
          {
            fieldName: "ngay_bh_kh",
            headerText: "Thời gian Khách hàng báo hỏng",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "tong_slg_bh",
            headerText: "Tổng số lần báo hỏng",
            allowFiltering: true,
            allowSorting: true,
          },
          {
            fieldName: "ngay_sd",
            headerText: "Ngày sử dụng",
            allowFiltering: true,
            allowSorting: true,
          },
        ],
        data: [],
        selected: null,
      },
      openedPopup: false,
      maKH: "",
      soTB: "",
      loaihinh: { data: [], selected: null },
      searchDate: {
        isEnabledFromDate: false,
        isEnabledToDate: false,
        fromDate: moment(new Date()).format("DD/MM/YYYY"),
        toDate: moment(new Date()).format("DD/MM/YYYY"),
        dateconfig: {
          altFormat: "d/m/Y",
          altInput: true,
          dateFormat: "d/m/Y",
          allowInput: true,
        },
      },
    };
  },
  methods: {
    addKeyDownEvent() {
      window.addEventListener("keydown", this.onF5KeyDown);
    },
    removeKeyDownEvent() {
      window.removeEventListener("keydown", this.onF5KeyDown);
    },
    onF5KeyDown(event) {
      if (event.keyCode === 116) {
        // event.preventDefault()
        // this.clearData()
      }
    },
    GetData(response) {
      console.log(response);
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    GetDataObj(response) {
      console.log(response);
      if (
        (response.data.error === 200 || response.data.error === "200") &&
        response.data.error_code === "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        throw new Error(response.data.message);
      }
    },
    async getComboboxData() {
      this.loading = true;
      try {
        this.loaihinh.selected = null;
        this.loaihinh.data = this.GetData(await api.sp_ds_loaihinh(this.axios));
      } catch (err) {
        console.error(err);
        // setTimeout(() => {
        //   this.getComboboxData()
        // }, 3000)
      } finally {
        this.loading = false;
      }
    },
    async search() {
      if (this.loaihinh.selected) {
        this.gridboxKQTK.data = [];
        this.gridboxKQTK.selected = null;
        this.loading = true;
        const params = {
          vtu_ngay: this.searchDate.fromDate,
          vden_ngay: this.searchDate.toDate,
          vloaitb_id: this.loaihinh.selected.loaitb_id,
        };
        const data = this.GetData(
          await api.sp_chitiet_suco_baohong_fms(this.axios, params)
        );
        // const data = [
        //   {
        //     TINH: "BPC",
        //     "Mã thuê bao": "hongphongpl1",
        //     "Số phiếu cảnh báo": null,
        //     "Loại cảnh báo": "Suy hao đường dây thuê bao > 30dB",
        //     "Kiểu phiếu xuất": "Báo hỏng thuê bao",
        //     "Mã cảnh báo": "ONT_ATTENUATION_30",
        //     "Nguyên nhân cảnh báo": "ONT_ATTENUATION_30 (Suy hao >= 30dBm)",
        //     "Tham số thiết bị": "10.77.53.55:1/1/2/13/21_ONT_ATTENUATION_30",
        //     "Số lượng thuê bao": 1,
        //     "Ngày giờ xuất phiếu": "2023-04-14T12:42:34",
        //     "Trạng thái xử lý phiếu": "Đã nghiệm thu",
        //     "Loại nguyên nhân hỏng":
        //       "Lỗi do thiết bị đầu cuối: ONT, STB, CPE_AON, Router, Home Gateway",
        //     "Chi tiết nguyên nhân hỏng": "Bị sét đánh",
        //     "Ghi chú xử lý": "| 14/04/2023 14:39: suy hao",
        //     "Ngày giờ xử lý": "2023-04-14T14:39:33",
        //     "Thời gian Khách hàng báo hỏng": null,
        //     "Tổng số lần báo hỏng": 9.0,
        //     "Thời gian hoàn công": "2020-11-09T12:07:37",
        //     "Ngày giờ FMs clear": null,
        //   },
        // ];
        if (data && data.length > 0) {
          this.gridboxKQTK.data = data;
          this.gridboxKQTK.selected = data[0];
        } else {
          this.$toast.error("Không tìm thấy kết quả theo yêu cầu !");
        }
        this.loading = false;
      } else {
        this.$toast.error("Bạn cần nhập loại hình TB!");
      }
    },
    selectedItemsChangedGridboxKQTK(items) {
      this.gridboxKQTK.selected = items;
    },
    clearData() {
      this.searchDate.fromDate = moment(new Date()).format("DD/MM/YYYY");
      this.searchDate.toDate = moment(new Date()).format("DD/MM/YYYY");
      this.gridboxKQTK = { data: [], selected: null };
      this.maKH = "";
      this.soTB = "";
    },
    TiemKiemBnt() {
      this.search();
    },
    XuatExcelBnt() {
      if (this.gridboxKQTK.data.length > 0) {
        let exportData = [];
        let exportHeader = this.gridboxKQTK.columns.map((item) => item.headerText);
        this.gridboxKQTK.data.forEach((row) => {
          const r = {};
          this.gridboxKQTK.columns.forEach((column) => {
            r[column.headerText] = row[column.fieldName] || "";
          });
          exportData.push(r);
        });

        let data = XLSX.utils.json_to_sheet(exportData, { header: exportHeader });
        let wb = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(wb, data, "TS");
        // export Excel file
        XLSX.writeFile(wb, "export.xlsx");
      } else {
        this.$toast.error("Không có dữ liệu để xuất");
      }
    },
  },
};
</script>
