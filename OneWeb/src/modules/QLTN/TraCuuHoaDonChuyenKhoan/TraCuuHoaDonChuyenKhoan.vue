<template src="./TraCuuHoaDonChuyenKhoan.html"></template>
<style scoped src="./TraCuuHoaDonChuyenKhoan.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import xlsx from "xlsx";
import API from "../api/TraCuuHoaDonChuyenKhoan";

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc },
  name: "TraCuuHoaDonChuyenKhoan",
  mounted() {
    this.$refs.maTT.focus();
  },
  computed: {},
  data() {
    return {
      header: {
        title: "TRA CỨU BIÊN NHẬN THU TẠI NHÀ",
        list: [
          { name: "Tra cứu", link: { name: "Ui.cards" } },
          {
            name: "Quản lý thu nợ",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu thu nợ",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Tra cứu biên nhận/hóa đơn chuyển khoản",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fields: [
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
        },
        {
          fieldName: "SERI",
          headerText: "Seri",
          allowFiltering: true,
          cellCssClass: "text-right",
        },
        {
          fieldName: "SO_THUNG",
          headerText: "Số thùng",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "SO_SERI",
          headerText: "Số Seri",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "TIEN_NO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TIEN_HD",
          headerText: "Tiền hóa đơn",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
        {
          fieldName: "NHANVIEN_TC",
          headerText: "Đại lý",
          allowFiltering: true,
        },
        {
          fieldName: "TUYENTHU",
          headerText: "Tuyến đường",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_GIAO",
          headerText: "Ngày giao",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "LANGIAO",
          headerText: "Lần giao",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "NGAY_HEN",
          headerText: "Ngày hẹn",
          allowFiltering: true,
          cssClass: "text-right",
        },
      ],
      fieldsHoaDon: [
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
        },
        {
          fieldName: "SERI",
          headerText: "Seri",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "SO_THUNG",
          headerText: "Số thùng",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "SO_SERI",
          headerText: "Số Seri",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "TIEN_NO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TIEN_HD",
          headerText: "Tiền hóa đơn",
          allowFiltering: true,
          type: "number",
          format: "N0",
        },
        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
        {
          fieldName: "NHANVIEN",
          headerText: "Đại lý",
          allowFiltering: true,
        },
        {
          fieldName: "TUYENTHU",
          headerText: "Tuyến đường",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_GIAO",
          headerText: "Ngày giao",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "LANGIAO",
          headerText: "Lần giao",
          allowFiltering: true,
          cssClass: "text-right",
        },
        {
          fieldName: "NGAY_HEN",
          headerText: "Ngày hẹn",
          allowFiltering: true,
          cssClass: "text-right",
        },
      ],
      maTT: "",
      soThung: "",
      seri: "",
      soSeri: 0,
      selectTab: 0,
      totalItemsBienNhan: 0,
      totalItemsHoaDon: 0,
      resetBienNhan: 1,
      resetHoaDon: 1,
      dsBienNhan: [],
      dsHoaDon: [],
      kyCuoc: "",
    };
  },
  methods: {
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    changeHeader(type) {
      let newTitle =
        type === 0
          ? "TRA CỨU BIÊN NHẬN THU TẠI NHÀ"
          : "TRA CỨU HÓA ĐƠN CHUYỂN KHOẢN";
      this.selectTab = type;
      this.header.title = newTitle;
    },
    fetchTraCuu() {
      if(this.maTT.trim() == "" && this.soSeri.toString().trim() == ""){
        this.$root.toastError("Hãy nhập thông tin tìm kiếm");
        this.$refs.maTT.focus();
        return;
      }
      if ((this.soThung && !this.soSeri) || (this.seri && !this.soSeri)) {
        this.$root.toastError("Bạn chưa nhập số Seri");
        return;
      }
      if (this.selectTab === 0) {
        this.resetBienNhan += 1;
        this.fetchDanhSachBienNhan(0, 10);
      } else {
        this.resetHoaDon += 1;
        this.fetchDanhSachHoaDonChuyenKhoan(0, 10);
      }
    },
    async fetchDanhSachBienNhan(page, size) {
      this.loading(true);
      let data = {
        kyCuoc: this.kyCuoc,
        maTT: this.maTT.trim(),
        seri: this.seri || 0,
        soSeri: this.soSeri,
        soThung: this.soThung || 0,
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      try {
        const response = await API.getDanhSachBienNhanThuTaiNha(
          this.axios,
          data
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsBienNhan = response.data.data.data;
          this.totalItemsBienNhan = response.data.data.totalElement;
        } else {
          this.dsBienNhan = [];
          this.totalItemsBienNhan = 0;
          this.$root.toastError("Không tìm thấy thông tin tra cứu!");
        }
      } catch (error) {
        this.dsBienNhan = [];
        this.totalItemsBienNhan = 0;
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    biennhan_gridChanged(args) {
      this.fetchDanhSachBienNhan(args.pageIndex, args.pageSize);
    },
    async fetchDanhSachHoaDonChuyenKhoan(page, size) {
      this.loading(true);
      let data = {
        kyCuoc: this.kyCuoc,
        maTT: this.maTT.trim(),
        seri: this.seri || 0,
        soSeri: this.soSeri,
        soThung: this.soThung || 0,
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      try {
        const response = await API.getDanhSachHoaDonChuyenKhoan(
          this.axios,
          data
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsHoaDon = response.data.data.data;
          this.totalItemsHoaDon = response.data.data.totalElement;
        } else {
          this.dsHoaDon = [];
          this.totalItemsHoaDon = 0;
          this.$root.toastError("Không tìm thấy thông tin tra cứu!");
        }
      } catch (error) {
        this.dsHoaDon = [];
        this.totalItemsHoaDon = 0;
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    hoadon_gridChanged(args) {
      this.fetchDanhSachHoaDonChuyenKhoan(args.pageIndex, args.pageSize);
    },
    XuatFile() {
      let result = [];
      if (this.selectTab === 0) {
        result = this.dsBienNhan;
      } else {
        result = this.dsHoaDon;
      }
      if (result.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(result);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    entermaTT(){
      if(this.maTT.trim() != "") this.fetchTraCuu()
    },
    enterSoThung(){
      if(this.soThung.toString().trim() != "") this.fetchTraCuu()
    },
    enterSeri(){
      if(this.seri.toString().trim() != "") this.fetchTraCuu()
    },
    enterSoSeri(){
      if(this.soSeri.toString().trim() != "") this.fetchTraCuu()
    }
  },
  created() {
  },
};
</script>
