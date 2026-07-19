<template src="./template.html"></template>

<script>
import api from "./api";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
export default {
  components: { breadcrumb },
  name: "PopupLichSuThayDoiDuLieu",
  props: {},
  data() {
    return {
      gridboxLichSu: {
        data: [],
        selected: null,
        columns: [
          {
            fieldName: "tenchuquan",
            headerText: "Đối thủ",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_trang_thai",
            headerText: "Trạng thái",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "noidung",
            headerText: "Nội dung",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "giaiphap",
            headerText: "Giải pháp",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ten_kv",
            headerText: "Khu vực",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "thaotac",
            headerText: "Thao tác",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "nguoi_cn",
            headerText: "Người CN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "may_cn",
            headerText: "Máy CN",
            allowFiltering: true,
            allowSorting: false,
          },
          {
            fieldName: "ngay_cn",
            headerText: "Thời gian CN",
            allowFiltering: true,
            allowSorting: false,
            format: { type: "date", format: "dd/MM/yyyy HH:mm:ss" },
            type: "date",
          },
        ],
      },
    };
  },
  computed: {},
  methods: {
    GetData(response) {
      console.log(response);
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
      }
      return [];
    },
    showModal(khuvuc_id, chuquan_id) {
      this.loadData(khuvuc_id, chuquan_id).then(() => {
        this.$refs.modalWarpper.show();
      });
    },
    async loadData(khuvuc_id, chuquan_id) {
      this.loading(true);
      this.gridboxLichSu.data = [];
      try {
        this.gridboxLichSu.data = this.GetData(
          await api.sp_lay_log_chitiet_doithu_canhtranh(this.axios, {
            vkhuvuc_id: khuvuc_id,
            vchuquan_id: chuquan_id,
          })
        );
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
