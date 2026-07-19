<template src="./ChuyenPhieuLacHuong.html"></template>
<style src="./ChuyenPhieuLacHuong.scss" scoped></style>
<script>
import Vue from "vue";
import { mapActions, mapState } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import API from "../api/Commons";
import PopupTimKiemNhanVienThuCuoc from "../components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc";

export default {
  components: { breadcrumb, PopupTimKiemNhanVienThuCuoc },
  name: "ChuyenPhieuLacHuong",
  mounted() {
    // this.$refs.select.focus();
  },
  computed: {
    ...mapState("chuyenphieulachuong", [
      "danhsachNhanVien",
      "danhsachNhanVienDungHuong",
    ]),
    canChuyen_AggregatesColumns(){
      return [{field: "MA_TUYEN",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.canchuyenAggregate}];
    },
    duocChuyen_AggregatesColumns(){
      return [{field: "MA_TUYEN",type: "Custom",footerTemplate: this.customTemplate,customAggregateFn: this.duocchuyenAggregate}];
    }
  },
  data() {
    return {
      header: {
        title: "CHUYỂN PHIẾU LẠC HƯỚNG",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Chuyển phiếu lạc hướng",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Chuyển phiếu lạc hướng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fields: [
        {
          fieldName: "MA_TUYEN",
          headerText: "Mã tuyến",
          allowFiltering: true,
          // cellCssClass: "text-right",
        },
        {
          fieldName: "MA_TT",
          headerText: "Mã thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ thanh toán",
          allowFiltering: true,
        },
      ],
      selectAll: "1",
      NVThuCu: "",
      tuyenThuCu: "",
      checkThangCu: true,
      thangCu: new Date(),
      maTT: "",
      checkLoaiPhieu: true,
      loaiPhieu: "1",
      NVThuMoi: "",
      tuyenThuMoi: "0",
      checkThangMoi: true,
      thangMoi: new Date(),
      danhSachTuyenThuCu: [],
      danhSachTuyenThuMoi: [],
      maxDate: new Date(),
      dsHoaDonCu: [],
      dsHoaDonMoi: [],
      dsSelectCu: [],
      dsSelectMoi: [],
      kyCuoc:
        moment(new Date())
          .subtract(1, "months")
          .endOf("month")
          .format("YYYYMM") + "01",
      dsChuyenPhieu_tmp: [],
      totalItemsRight: 0,
      totalItemsLeft: 0,
      keyResetRight: 1,
      keyResetLeft: 1,
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" disabled style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },
  methods: {
    ...mapActions("chuyenphieulachuong", [
      "getDanhSachNVThuCuoc",
      "getDanhSachNVThuCuocDungHuong",
      "getDanhSachHoaDon",
      "chuyenPhieu",
      "clearData",
    ]),
    ...mapActions("phanchiatuyenthu", ["getDanhSachTuyenThuByNhanVien"]),
    Huy() {
      //   this.$router.push({ path: "/qltn" });
      this.clearForm();
    },
    clearForm() {
      this.dsHoaDonCu = [];
      this.dsHoaDonMoi = [];
      this.maTT = "";
      this.dsChuyenPhieu_tmp = [];
    },
    async fetchDataDSSai(page, size) {
      if (this.tuyenThuCu === "") {
        this.$root.toastError("Chưa chọn tuyến thu cũ!");
        return;
      }
      this.loading(true);
      const data = {
        loaiPhieu: this.checkLoaiPhieu ? this.loaiPhieu : 0, // ""
        kyCuoc: this.kyCuoc, // 20210101
        tuyenThuId: this.tuyenThuCu, // 260
        tuyenThuIdCu: this.selectAll == "1" ? 0 : this.tuyenThuMoi,
        thang: this.checkThangCu
          ? moment(this.thangCu).format("YYYYMM") + "01"
          : "",
        maTT: this.maTT,
        pageNum: null,
        pageSize: size,
        totalRow: 1,
      };
      const response = await this.getDanhSachHoaDon(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (
          response.data.data !== undefined &&
          response.data.data.data.length > 0
        ) {
          this.dsHoaDonCu = response.data.data.data;
          this.totalItemsLeft = response.data.data.totalElement;
        } else {
          this.$root.toastError("Không tìm thấy dữ liệu thỏa điều kiện!");
          this.dsHoaDonCu = [];
          this.totalItemsLeft = 0;
        }
      } else {
        this.loading(false);
        this.$root.toastError(response.message_detail);
        this.dsHoaDonCu = [];
        this.totalItemsLeft = 0;
      }
      return response;
    },
    canChuyen_gridChanged: async function (args) {
      this.fetchDataDSSai(args.pageIndex, args.pageSize);
    },
    async fetchDataDSDung(page, size) {
      if (this.tuyenThuMoi === "" || this.tuyenThuMoi === "0") {
        this.$root.toastError("Chưa chọn tuyến thu!");
        return;
      }
      this.loading(true);
      const data = {
        loaiPhieu: 0,
        kyCuoc: this.kyCuoc, // 20210101
        tuyenThuId: this.tuyenThuMoi, // 260
        tuyenThuIdCu: 0,
        thang: this.checkThangMoi
          ? moment(this.thangMoi).format("YYYYMM") + "01"
          : "",
        maTT: "",
        pageNum: null,
        pageSize: size,
        totalRow: 1,
      };
      const response = await this.getDanhSachHoaDon(data);
      if (response.data.error_code === "BSS-00000000") {
        this.loading(false);
        if (
          response.data.data !== undefined &&
          response.data.data.data.length > 0
        ) {
          this.dsHoaDonMoi = response.data.data.data;
          this.dsChuyenPhieu_tmp = response.data.data.data;
          this.totalItemsRight = response.data.data.totalElement;
        } else {
          this.$root.toastError("Không tìm thấy dữ liệu thỏa điều kiện!");
          this.dsHoaDonMoi = [];
          this.dsChuyenPhieu_tmp = [];
          this.totalItemsRight = 0;
        }
      } else {
        this.loading(false);
        this.$root.toastError(response.message_detail);
        this.dsHoaDonMoi = [];
        this.dsChuyenPhieu_tmp = [];
        this.totalItemsRight = 0;
      }
      return response;
    },
    duocChuyen_gridChanged: async function (args) {
      this.fetchDataDSDung(args.pageIndex, args.pageSize);
    },
    selectedItemsChangedCu(data) {
      if(JSON.stringify(this.dsSelectCu) != JSON.stringify(data)){
        this.dsSelectCu = data;
      } else {
        this.dsSelectCu = []
      }
      let dataGridObj = this.$refs.canChuyen_grid.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    selectedItemsChangedMoi(data) {
      if(JSON.stringify(this.dsSelectMoi) != JSON.stringify(data)){
        this.dsSelectMoi = data;
      } else {
        this.dsSelectMoi = []
      }
      let dataGridObj = this.$refs.duocChuyen_grid.$refs.grid.ej2Instances;
      dataGridObj.aggregateModule.refresh();
    },
    move() {
      if (this.kiemTra()) return;
      let arr = [];
      const dsSelected_canChuyen = this.$refs.canChuyen_grid.getSelectedRecords() || [];
      dsSelected_canChuyen.map((val) => {
        let idx = this.dsHoaDonCu.findIndex((v) => v.MA_TT == val.MA_TT);
        if (idx >= 0) {
          arr.push(this.dsHoaDonCu[idx]);
          this.dsHoaDonCu = this.dsHoaDonCu.filter((v) => v.MA_TT != val.MA_TT);
        }
      });
      this.dsHoaDonMoi = [...arr, ...this.dsHoaDonMoi];
      this.dsSelectMoi = [];
    },
    moveAll() {
      if (this.kiemTra()) return;
      this.dsHoaDonMoi = [...this.dsHoaDonCu, ...this.dsHoaDonMoi];
      this.dsHoaDonCu = [];
      this.dsSelectMoi = [];
    },
    unmove() {
      if (this.kiemTra()) return;
      let arr = [];
      const dsSelected_duocChuyen = this.$refs.duocChuyen_grid.getSelectedRecords() || [];
      dsSelected_duocChuyen.map((val) => {
        let idx = this.dsHoaDonMoi.findIndex((v) => v.MA_TT == val.MA_TT);
        if (idx >= 0) {
          arr.push(this.dsHoaDonMoi[idx]);
          this.dsHoaDonMoi = this.dsHoaDonMoi.filter((v) => v.MA_TT != val.MA_TT);
        }
      });
      this.dsHoaDonCu = [...arr, ...this.dsHoaDonCu];
      this.dsSelectCu = [];
    },
    unmoveAll() {
      if (this.kiemTra()) return;
      this.dsHoaDonCu = [...this.dsHoaDonMoi, ...this.dsHoaDonCu];
      this.dsHoaDonMoi = [];
      this.dsSelectCu = [];
    },
    GhiLai() {
      if (this.kiemTra()) return;
      if (this.dsHoaDonMoi.length === 0) {
        this.$root.toastError(
          "Không có phiếu lạc hướng. Bạn hãy kiểm tra lại!"
        );
        return;
      }
      this.$confirm(`Bạn có thật sự muốn cập nhật thông tin ?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Không đồng ý",
      })
        .then(async () => {
          //filter danh sách mới
          const dsChon = this.dsHoaDonMoi.filter((item) => !this.dsChuyenPhieu_tmp.includes(item));
          if (dsChon.length <= 0) {
            this.$root.toastError("Chưa có thanh toán mới chuyển đến. Hãy kiểm tra lại!");
            return;
          }
          const dsChuyenPhieu = dsChon.map((item) => item.MA_TT);
          const data = {
            dsChuyenPhieu,
            tuyenThuIdMoi: this.tuyenThuMoi,
            tuyenThuIdCu: this.tuyenThuCu,
            kyCuoc: this.kyCuoc,
          };
          this.loading(true);
          const response = await this.chuyenPhieu(data);
          if (response.data.error_code === "BSS-00000000") {
            this.loading(false);
            this.clearForm();
            this.$root.toastSuccess("Chuyển phiếu lạc hướng thành công!");
          } else {
            this.loading(false);
            this.$root.toastError(response.message_detail);
          }
          return response;
        })
        .catch(() => {});
    },
    PopupNhanVienCu() {
      this.$refs.dlgTimKiemNVTCCu.openDialog();
    },
    PopupNhanVienMoi() {
      this.$refs.dlgTimKiemNVTCMoi.openDialog();
    },
    async fetchKyCuoc() {
      const response = await API.getKyCuocHienHanh(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.kyCuoc = response.data.data[0].KYCUOC;
      }
    },
    getNVTCProcessCu(value) {
      this.NVThuCu = value.nhanVienTC_Id;
    },
    getNVTCProcessMoi(value) {
      this.NVThuMoi = value.nhanVienTC_Id;
    },
    onEnterMaTT() {
      this.keyResetLeft += 1;
      this.fetchDataDSSai(0, 10);
    },
    kiemTra() {
      if (this.NVThuCu === "") {
        this.$root.toastError("Chưa chọn nhân viên thu cũ!");
        return true;
      }
      if (this.tuyenThuCu === "") {
        this.$root.toastError("Chọn tuyến thu cũ !");
        return true;
      }
      if (this.NVThuMoi === "") {
        this.$root.toastError("Chưa chọn nhân viên thu mới!");
        return true;
      }
      if (this.tuyenThuMoi === "" || this.tuyenThuMoi == "0") {
        this.$root.toastError("Chọn tuyến thu mới !");
        return true;
      }
      if (
        this.tuyenThuCu == this.tuyenThuMoi &&
        this.NVThuCu == this.NVThuMoi
      ) {
        this.$root.toastError("Hướng mới không được trùng với hướng cũ !");
        return true;
      }
      return false;
    },
    canchuyenAggregate(){
      return `${this.dsSelectCu.length}/${this.dsHoaDonCu.length}`;
    },
    duocchuyenAggregate(){
      return `${this.dsSelectMoi.length}/${this.dsHoaDonMoi.length}`;
    }
  },
  created() {
    this.clearData();
    this.getDanhSachNVThuCuoc();
    this.getDanhSachNVThuCuocDungHuong();
    this.fetchKyCuoc();
  },
  watch: {
    NVThuCu: {
      handler: async function (val) {
        const response = await this.getDanhSachTuyenThuByNhanVien({
          pNhanVienId: val,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.danhSachTuyenThuCu = response.data.data;
          this.tuyenThuCu = response.data.data[0].TUYENTHU_ID;
        } else {
          this.danhSachTuyenThuCu = [];
          this.tuyenThuCu = "0";
        }
      },
    },
    NVThuMoi: {
      handler: async function (val) {
        const response = await this.getDanhSachTuyenThuByNhanVien({
          pNhanVienId: val,
        });
        if (response.data.error_code === "BSS-00000000") {
          if (response.data.data.length <= 0) {
            this.danhSachTuyenThuMoi = [];
            this.tuyenThuMoi = "0";
            return;
          }
          this.danhSachTuyenThuMoi = response.data.data;
          this.tuyenThuMoi = response.data.data[0].TUYENTHU_ID;
        }
      },
    },
    tuyenThuCu: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    checkLoaiPhieu: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    loaiPhieu: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    checkThangCu: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    thangCu: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    selectAll: {
      handler: function (val) {
        this.keyResetLeft += 1;
        this.fetchDataDSSai(0, 10);
      },
    },
    tuyenThuMoi: {
      handler: function (val) {
        this.keyResetRight += 1;
        this.fetchDataDSDung(0, 10);
      },
    },
    checkThangMoi: {
      handler: function (val) {
        this.keyResetRight += 1;
        this.fetchDataDSDung(0, 10);
      },
    },
    thangMoi: {
      handler: function (val) {
        this.keyResetRight += 1;
        this.fetchDataDSDung(0, 10);
      },
    },
  },
};
</script>
