<template src='./TraCuuNoKhachHang.html'></template>
<style src='./TraCuuNoKhachHang.scss' scoped></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import KyCuoc from "@/components/KyCuoc2.vue";
import moment from "moment";
import ModalChonDiaChi from "../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal";
import API from "../api/TraCuuNoKhachHang";

export default {
  components: { breadcrumb, appKyCuoc: KyCuoc, ModalChonDiaChi },
  name: "TraCuuNoKhachHang",
  mounted() {
    this.$refs.maKH.focus();
  },
  computed: {
    returnColumns() {
      if (this.selectedKieu === "1") {
        return this.columnsKH;
      } else if (this.selectedKieu === "2") {
        return this.columnsTT;
      } else return this.columnsTB;
    },
    labelMa() {
      if (this.selectedKieu === "1") {
        return "Mã KH";
      } else if (this.selectedKieu === "2") {
        return "Mã TT";
      } else return "Số máy/Acc";
    },
    labelTen() {
      if (this.selectedKieu === "1") {
        return "Tên KH";
      } else if (this.selectedKieu === "2") {
        return "Tên TT";
      } else return "Tên TB";
    },
    labelDiachi() {
      if (this.selectedKieu === "1") {
        return "Địa chỉ KH";
      } else if (this.selectedKieu === "2") {
        return "Địa chỉ TT";
      } else return "Địa chỉ TB";
    },
  },
  data() {
    return {
      header: {
        title: "TRA CỨU NỢ KHÁCH HÀNG",
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
            name: "Tra cứu khách hàng theo nợ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      selectedKieu: "1",
      selectedKieuNo: "1",
      maKH: "",
      tenKH: "",
      diaChiKH: "",
      tuTien: 0,
      denTien: 0,
      selectedTuTien: "=",
      columnsKH: [
        {
          fieldName: "KHACHHANG",
          headerText: "Khách hàng",
          allowFiltering: false,
          allowSorting: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
          width: 80,
          cssClass: "text-right",
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
          width: 200,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
        },
        {
          fieldName: "TIENNO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
          cssClass: "text-danger"
        },
      ],
      columnsTT: [
        {
          fieldName: "KHACHHANG",
          headerText: "Khách hàng",
          allowFiltering: false,
          allowSorting: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
          width: 80,
          cssClass: "text-right",
        },
        {
          fieldName: "MA_TB",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          width: 150,
        },
        {
          fieldName: "TEN_TB",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          width: 200,
        },
        {
          fieldName: "DIACHI_TB",
          headerText: "Địa chỉ thuê bao",
          allowFiltering: true,
        },
        {
          fieldName: "TIENNO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
          cssClass: "text-danger"
        },
      ],
      columnsTB: [
        {
          fieldName: "KHACHHANG",
          headerText: "Khách hàng",
          allowFiltering: false,
          allowSorting: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "STT",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
          width: 80,
          cssClass: "text-right",
        },
        {
          fieldName: "TENKHOANMUC",
          headerText: "Tên khoản mục",
          allowFiltering: true,
        },
        {
          fieldName: "TIENNO",
          headerText: "Tiền nợ",
          allowFiltering: true,
          type: "number",
          format: "N0",
          cssClass: "text-danger"
        },
      ],
      pKyCuoc: "",
      totalItems: 0,
      keyReset: 1,
      dsNoKH: [],
    };
  },
  methods: {
    fetchTraCuu() {
      if (this.pKyCuoc === "") {
        this.$root.toastError("Bạn chưa chọn chu kỳ!");
        return;
      }
      if (this.tuTien === "" || this.denTien === "") {
        this.$root.toastError("Bạn chưa nhập số tiền!");
        return;
      }
      if (this.maKH === "" && this.tenKH === "" && this.diaChiKH === "") {
        this.$root.toastError("Bạn phải nhập Mã hoặc Tên hoặc Địa chỉ !");
        return;
      }
      this.keyReset += 1;
      this.fetchDanhSachThongTinNo(0, 10);
    },
    async fetchDanhSachThongTinNo(page, size) {
      this.loading(true);
      let data = {
        pMaKhachHang: this.maKH,
        pTenKhachHang: this.tenKH,
        pDiaChi: this.diaChiKH,
        pKyCuoc: this.pKyCuoc,
        pKieu: this.selectedKieu,
        pTuTien: this.tuTien,
        pDenTien: this.selectedTuTien === "=" ? 0 : this.denTien,
        pCachTinh: this.selectedTuTien === "=" ? 1 : 2,
        pKieuNo: this.selectedKieuNo,
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      try {
        const response = await API.getDanhSachThongTinNo(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsNoKH = response.data.data.data;
          this.totalItems = response.data.data.totalElement;
        } else {
          this.dsNoKH = [];
          this.totalItems = 0;
          this.$root.toastError("Không tìm thấy dữ liệu thỏa điều kiện!");
          this.$refs.maKH.select();
        }
      } catch (error) {
        this.dsNoKH = [];
        this.totalItems = 0;
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    gridChanged(args) {
      this.fetchDanhSachThongTinNo(args.pageIndex, args.pageSize);
    },
    handleKyCuoc(e) {
      // this.pKyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      if (e.p_chuky_no !== null) {
        this.pKyCuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
      }
    },
    getDiaChi(data) {
    // console.log('object',data);
      this.diaChiKH = data.diachimoi || "";
    },
    enterMaKH(){
      if(this.maKH.trim() != "") this.fetchTraCuu()
    },
    enterTenKH(){
      if(this.tenKH.trim() != "") this.fetchTraCuu()
    },
    enterDiaChiKH(){
      if(this.diaChiKH.trim() != "") this.fetchTraCuu()
    }
  },
  created() {},
  watch: {
    selectedKieu: {
      handler: function (value) {
        this.dsNoKH = [];
        this.tuTien = 0;
        this.denTien = 0;
        this.maKH = "";
        this.tenKH = "";
        this.diaChiKH = "";
      },
    },
  },
};
</script>
