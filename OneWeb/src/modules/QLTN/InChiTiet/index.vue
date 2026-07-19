<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import API from "../api/InChiTietCuoc";
import xlsx from "xlsx";
import { previewPrint } from "../../../utils/util";

export default {
  components: {},
  data() {
    return {
      kyHoaDon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      pLoai: 0,
      txtChuGoi: "",
      txtBiGoi: "",
      checkCoDinh: true,
      checkMyTV: true,
      checkDiDong: true,
      checkInTatCa: false,
      dsCuocGoi: [],
      col1: [
        {
          fieldName: "NGAY_GOI",
          headerText: "Ngày gọi",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "CHUGOI",
          allowFiltering: true,
          isGroupedColumn: true,
          captionTemplate: "Chủ gọi: ${key}",
        },
        {
          fieldName: "BIGOI",
          headerText: "Bị gọi",
          allowFiltering: true,
        },
        {
          fieldName: "TENVUNG",
          headerText: "Tên vùng",
          allowFiltering: true,
        },
        {
          fieldName: "GIOBD",
          headerText: "Giờ bắt đầu",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "TG_GOI",
          headerText: "Thời gian/Lưu lượng",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "VAT",
          headerText: "Thuế GTGT",
          allowFiltering: true,
        },
        {
          fieldName: "TIEN",
          headerText: "Tiền",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
      ],
      col2: [
        {
          fieldName: "NGAY_GOI",
          headerText: "Ngày bắt đầu",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "MA_TB",
          allowFiltering: true,
          isGroupedColumn: true,
          captionTemplate: "Account: ${key}",
        },
        {
          fieldName: "GIOBD",
          headerText: "Giờ bắt đầu",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "TG_GOI",
          headerText: "Thời gian",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "BYTE_IN",
          headerText: "Download(MB)",
          allowFiltering: true,
        },
        {
          fieldName: "BYTE_OUT",
          headerText: "Upload(MB)",
          allowFiltering: true,
        },
        {
          fieldName: "Download(B)",
          headerText: "Download(B)",
          allowFiltering: true,
        },
        {
          fieldName: "Upload(B)",
          headerText: "Upload(B)",
          allowFiltering: true,
        },
      ],
      keyReset: 1,
    };
  },
  watch: {
    pLoai: {
      handler: function (value) {
        if (value == 0) {
          this.checkCoDinh = true;
          this.checkMyTV = true;
          this.checkDiDong = true;
          this.checkInTatCa = false;
        } else {
          this.checkCoDinh = false;
          this.checkMyTV = false;
          this.checkDiDong = false;
          this.checkInTatCa = false;
        }
        this.dsCuocGoi = [];
      },
    },
    checkDiDong: {
      handler: function (value) {
        if (!value) this.checkInTatCa = false;
      },
    },
  },
  created() {},
  mounted() {},
  methods: {
    async onEnterChuGoi() {
      if (this.txtChuGoi.trim() == "") {
        this.$root.toastError("Chưa nhập chủ gọi !");
        return;
      }
      const data = {
        kyCuoc: moment(this.kyHoaDon).format("YYYYMM") + "01",
        chuGoi: this.txtChuGoi.trim(),
        biGoi: this.txtBiGoi.trim(),
        hanhDong: 1, //0
        loai: this.pLoai,
        inTatCa: this.checkInTatCa ? 1 : null,
        coDinh: this.checkCoDinh ? 1 : null,
        diDong: this.checkDiDong ? 1 : null,
        myTV: this.checkMyTV ? 1 : null,
      };
      try {
        this.loading(true);
        const response = await API.getDanhSachCuocGoi(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.keyReset += 1;
          this.dsCuocGoi = await response.data.data;
        } else {
          this.dsCuocGoi = [];
          this.$root.toastError("Không có dữ liệu chi tiết cước!");
        }
      } catch (error) {
        this.dsCuocGoi = [];
        this.$root.toastError(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async XemChiTiet() {
      if (this.txtChuGoi.trim() == "") {
        this.$root.toastError("Chưa nhập chủ gọi !");
        return;
      }
      const data = {
        kyCuoc: moment(this.kyHoaDon).format("YYYYMM") + "01",
        chuGoi: this.txtChuGoi.trim(),
        biGoi: this.txtBiGoi.trim(),
        hanhDong: 1,
        loai: this.pLoai,
        inTatCa: this.checkInTatCa ? 1 : null,
        coDinh: this.checkCoDinh ? 1 : null,
        diDong: this.checkDiDong ? 1 : null,
        myTV: this.checkMyTV ? 1 : null,
      };
      try {
        this.loading(true);
        const baseUrl = process.env.API;

        await this.axios({
          url: `${baseUrl}/web-thuno/api/thu-no/in-hoa-don/in-chi-tiet-cuoc-goi-v3`,
          method: "POST",
          responseType: "blob",
          data: data,
        }).then((response) => {
        //   previewPrint(response.data);
            var blobURL = window.URL.createObjectURL(new Blob([response.data], { type: "application/pdf" }));
            var a = document.createElement("a");
            a.target = '_blank'
            a.href = blobURL
            a.click()
        });
      } catch (error) {
        this.$root.toastError("Báo cáo này chưa được thiết kế mẫu !");
      } finally {
        this.loading(false);
      }
    },
    XuatExcel() {
      if (this.dsCuocGoi.length <= 0) {
        this.$root.toastError("Không có dữ liệu để xuất Excel !");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsCuocGoi);
        var wb = xlsx.utils.book_new();
        xlsx.utils.book_append_sheet(wb, dsach, "DS");
        xlsx.writeFile(wb, "dscuocgoi.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
  },
  computed: {
    returnColumn() {
      if (this.pLoai == 1) return this.col2;
      return this.col1;
    },
  },
};
</script>
