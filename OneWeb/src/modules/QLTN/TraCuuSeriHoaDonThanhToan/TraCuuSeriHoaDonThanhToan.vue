<template src='./TraCuuSeriHoaDonThanhToan.html'></template>
<style src='./TraCuuSeriHoaDonThanhToan.scss' scoped></style>
<script>
import { mapActions, mapState } from "vuex";
import KyCuoc from "../components/KyCuoc.vue";
import moment from "moment";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";
import SearchAccountModal from "./SearchAccountModal.vue";

export default {
  components: {
    appKyCuoc: KyCuoc,
    appModalXuatDuLieu: ModalXuatDuLieu,
    SearchAccountModal,
  },
  name: "TraCuuSeriHoaDonThanhToan",
  mounted() {
    // this.$refs.maTT.focus();
  },
  computed: {
    ...mapState("tracuuserihoadonthanhtoan", ["thongTinTT"]),
    dsThuocTinh() {
      return this.fields.map((x) => x.fieldName);
    },
  },
  data() {
    return {
      fields: [
        {
          fieldName: "MA_NVTC",
          headerText: "Đại lý",
          allowFiltering: false,
          isGroupedColumn: true,
        },
        {
          fieldName: "KY_CUOC",
          headerText: "Kỳ hóa đơn",
          allowFiltering: true,
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã TT",
          allowFiltering: true,
        },
        {
          fieldName: "SERI",
          headerText: "Seri",
          allowFiltering: true,
          cssClass: "text-primary"
        },
        {
          fieldName: "SOQUYEN",
          headerText: "Số thùng",
          allowFiltering: true,
          cssClass: "font-weight-bold",
        },
        {
          fieldName: "SOSERI",
          headerText: "Số Seri",
          allowFiltering: true,
          cssClass: "text-so-seri",
        },
        {
          fieldName: "TONG",
          headerText: "Nợ phát sinh",
          allowFiltering: true,
          type: "number",
          format: "N0",
          cssClass: "text-danger"
        },
      ],
      maTT: "",
      tenTT: "",
      maDD: "",
      diaChiTT: "",
      maThanhToan: "",
      tuThang: moment(new Date()).subtract(4, "months").endOf("month").toDate(),
      denThang: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      pTuThang: "",
      pDenThang: "",
      totalItems: 0,
      danhSachHoaDon: [],
      keyReset: 1,
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper"
    };
  },
  methods: {
    ...mapActions("tracuuserihoadonthanhtoan", [
      "getDanhSachSeriHoaDon",
      "getThongTinTTByTT",
      "getThongTinTTByTB",
      "getThongTinTTByKH",
      "clearData",
    ]),
    openDialog() {
      this.$refs.dlgTraCuuSeriHoaDon.show();
    },
    async fetchTimKiem() {
      if (this.maTT.trim() === "") {
        this.$refs.maTT.focus();
        this.$root.toastError("Bạn chưa nhập thông tin tìm kiếm!");
        return;
      }
      this.loading(true);
      const res1 = await this.getThongTinTTByTT(this.maTT);
      if (res1.data.data.length !== 0) {
        this.fillThongTinTT();
      } else {
        const res2 = await this.getThongTinTTByTB(this.maTT);
        if (res2.data.data.length !== 0) {
          this.fillThongTinTT();
        } else {
          const res3 = await this.getThongTinTTByKH(this.maTT);
          if (res3.data.data.length !== 0) {
            this.fillThongTinTT();
          } else {
            this.loading(false);
            this.$refs.maTT.focus();
            this.$root.toastError("Không có thông tin thanh toán!");
            this.$refs.maTT.select();
            this.resetThongTinTT();
          }
        }
      }
    },
    fillThongTinTT() {
      if (this.thongTinTT) {
        this.tenTT = this.thongTinTT.TEN_TT;
        this.diaChiTT = this.thongTinTT.DIACHI_TT;
        this.maDD = this.thongTinTT.MATB_DD;
        this.maThanhToan = this.thongTinTT.MA_TT;
        this.maTT = this.thongTinTT.MA_TT;
        this.keyReset += 1;
        this.fetchDanhSach(0, 10);
      }
    },
    resetThongTinTT() {
      this.tenTT = "";
      this.diaChiTT = "";
      this.maDD = "";
      this.maThanhToan = "";
      this.danhSachHoaDon = [];
    },
    async fetchDanhSach(page, size) {
      this.loading(true);
      let data = {
        maTT: this.maThanhToan,
        tuThang: this.pTuThang,
        denThang: this.pDenThang,
        pageNum: page + 1,
        pageSize: size,
        totalRow: 1,
      };
      const response = await this.getDanhSachSeriHoaDon(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (
          response.data.data !== undefined &&
          response.data.data.data.length > 0
        ) {
          this.danhSachHoaDon = response.data.data.data;
          this.totalItems = response.data.data.totalElement;
        } else {
          this.danhSachHoaDon = [];
          this.totalItems = 0;
          this.$root.toastError("Không tìm thấy dữ liệu thỏa điều kiện!");
        }
      } else {
        this.loading(false);
        this.danhSachHoaDon = [];
        this.totalItems = 0;
        this.$root.toastError(response.data.message);
      }
      return response;
    },
    gridChanged(args) {
      this.fetchDanhSach(args.pageIndex, args.pageSize);
    },
    fetchNhapmoi() {
      this.maTT = "";
      this.maDD = "";
      this.tenTT = "";
      this.diaChiTT = "";
      this.maThanhToan = "";
      this.danhSachHoaDon = [];
    //   this.$refs.maTT.focus();
      this.clearData();
    },
    traCuuDanhBa() {
      this.$refs.traCuuDanhBaModal.showModal();
    },
    formCloseSearchAccount(data) {
      if(data) {
        this.maTT = data.ma_kh || "";
        this.fetchTimKiem();
      }
    },
    handleTuThang(e) {
      this.pTuThang = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    handleDenThang(e) {
      this.pDenThang = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    openForm() {
      this.fetchNhapmoi();
    },
    XuatFile() {
      if (this.danhSachHoaDon.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất File!");
        return;
      }
      this.$bvModal.show("modal-xuat-dulieu-hoadon-thanhtoan");
    },
  },
  created() {},
};
</script>
