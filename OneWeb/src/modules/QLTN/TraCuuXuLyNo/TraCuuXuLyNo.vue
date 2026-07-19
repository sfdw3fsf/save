<template src="./TraCuuXuLyNo.html"></template>
<style src="./TraCuuXuLyNo.scss" scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import xlsx from "xlsx";
import TraCuuThongTinThueBao from "../GachNo/components/TraCuuThongTinThueBao";
import EventBus from "@/utils/eventBus";
import API from "../api/TraCuuXuLyNo";

export default {
  components: { breadcrumb, TraCuuThongTinThueBao },
  name: "TraCuuXuLyNo",
  mounted() {
    this.$refs.maXLN.focus();
  },
  computed: {
  },
  data() {
    return {
      header: {
        title: "TRA CỨU XỬ LÝ NỢ",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Quản lý thu nợ",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu xử lý nợ",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu xử lý nợ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fields: [
        {
          fieldName: "MA_XLN",
          headerText: "Khách hàng",
          allowFiltering: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "BUOC_XLN",
          headerText: "Bước XLN",
          allowFiltering: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "DIACHI_KH",
          headerText: "Địa chỉ KH",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã ĐD",
          allowFiltering: true,
        },
        {
          fieldName: "LAN_XLN",
          headerText: "Lần XL",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
        {
          fieldName: "TRANGTHAI_XLN",
          headerText: "Kết quả XLN",
          allowFiltering: true,
        },
        {
          fieldName: "NOIDUNG_XLN",
          headerText: "Lý do XLN",
          allowFiltering: true,
        },
        {
          fieldName: "LANTAO",
          headerText: "Lần tạo",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_CN",
          headerText: "Ngày CN",
          allowFiltering: true,
        },
        {
          fieldName: "KQ_THUHOI",
          headerText: "KQ thu hồi",
          allowFiltering: true,
        },
        {
          fieldName: "NHANVIEN_XL",
          headerText: "Nhân viên XL",
          allowFiltering: true,
        },
        {
          fieldName: "NGAYCN_TH",
          headerText: "Ngày thu hồi",
          allowFiltering: true,
        },
        {
          fieldName: "BUOC_XLN_NEXT",
          headerText: "Bước chuyển",
          allowFiltering: true,
        },
        {
          fieldName: "GHICHU",
          headerText: "Ghi chú",
          allowFiltering: true,
        },
      ],
      maXLN: "",
      kieuXL: 1,
      checkKieuXL: false,
      totalItems: 0,
      danhSachXLN: [],
    };
  },
  methods: {
    timKiem() {
      if (this.maXLN.trim() == "") {
        this.$root.toastError("Bạn chưa nhập mã thanh toán tìm kiếm");
        return;
      }
      this.fetchData(0, 10);
    },
    async fetchData(page, size) {
      this.loading(true);
      let data = {
        maXLN: this.maXLN.trim(),
        kieuXL: this.checkKieuXL ? this.kieuXL : "",
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      try {
        const response = await API.getDanhSachXuLyNo(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.danhSachXLN = response.data.data.data;
          this.totalItems = response.data.data.totalElement;
        } else {
          this.danhSachXLN = [];
          this.totalItems = 0;
          this.$root.toastError("Không tìm thấy dữ liệu thỏa điều kiện!");
        }
      } catch (error) {
        this.danhSachXLN = [];
        this.totalItems = 0;
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    gridChanged(args) {
      this.fetchData(args.pageIndex, args.pageSize);
    },
    async importExcel(e) {
      this.maXLN = "";
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastError("File excel không đúng định dạng!");
      }
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const XLSX = xlsx;
          const workbook = XLSX.read(data, {
            type: "binary",
          });
          const wsname = workbook.SheetNames[0];
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]);
          const excellist = [];
          let dsMaXLN = [];
          // Edit data
          for (var i = 0; i < ws.length; i++) {
            excellist.push(ws[i]);
            dsMaXLN.push(ws[i].MA_XLN);
          }

          var propCount = 0;
          if (excellist && excellist.length > 0) {
            for (var prop in excellist[0]) {
              if (excellist[0].hasOwnProperty(prop)) propCount++;
            }
          }

          if (propCount != 1) {
            this.$root.toastError(
              "Số lượng cột phải = 1 cột, hiện đang có " + propCount + " cột!"
            );
            return;
          }

          if (!excellist[0].hasOwnProperty("MA_XLN")) {
            this.$root.toastError(
              "Không tìm thấy cột mã xử lý nợ(MA_XLN) trong file Excel!"
            );
            return;
          }
          let result = dsMaXLN.toString();
          this.maXLN = result;
          this.timKiem();
        } catch (e) {
          return alert("Read failure!" + e);
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    traCuuThongTinThueBao() {
      this.$refs.dlgTraCuuTTTB.openDialog();
    },
    getTTTBHandler(e) {
      this.maXLN = e.MA_TT;
      this.$refs.maXLN.focus();
    },
    enterMaXLN(){
      if(this.maXLN.trim() != "") this.timKiem();
    }
  },
  created() {
    EventBus.$on("getTTTB", this.getTTTBHandler);
  },
  destroyed() {
    EventBus.$off("getTTTB", this.getTTTBHandler);
  },
};
</script>
