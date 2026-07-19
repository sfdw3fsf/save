<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import KyCuoc from "../components/KyCuoc.vue";
import API from "../api/GachNoDiDongTienMat";
import FormDSPhieuGachNoVNP from "./components/formDSPhieuGachNoVNP.vue";
import FormDSTraCuuTTTT from "./components/formTraCuuTTTT.vue";

export default {
  components: {
    breadcrumb,
    appKyCuoc: KyCuoc,
    FormDSPhieuGachNoVNP,
    FormDSTraCuuTTTT,
  },
  data() {
    return {
      header: {
        title:
          "THANH TOÁN CƯỚC SỬ DỤNG DỊCH VỤ DI ĐỘNG - GHÉP PHIẾU THU TIỀN MẶT",
        list: [
          { name: "Gạch nợ", link: { name: "Ui.cards" } },
          {
            name: "Gạch nợ di động - đồng bộ phiếu trả tiền mặt",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      kyCuoc: "",
      typeDongBo: "1",
      form: {
        dsThuQuy: [],
        idThuQuy: "",
        chungTu: "",
        maTT: "",
        soMay: "",
        dsUserNeo: [],
        idUSerNeo: "",
        checkDonViQL: false,
        dsDonViQL: [],
        idDonVi: "",
        dsNhom: [],
        idNhom: "",
        dsNhanVien: [],
        idNhanVien: "",
        ghiChu: "",
        checkTuNgay: false,
        tuNgay: new Date(),
        checkDenNgay: false,
        denNgay: new Date(),
      },
      dsPhieuThanhToanVNP: [],
      dsChiTietThanhToan: [],
      quaythu_id: "0",
      ma_tn: "",
      htttIdNeo: 1,
      tongSoPhieu: 0,
      maThueBaoTT: "",
      date_ht: moment(new Date()).subtract(1, "months").toDate(),
      date_chon: "",
    };
  },
  validations: {},
  watch: {
    "form.idThuQuy": {
      handler: async function (value) {
        const objThuNgan = this.form.dsThuQuy.find(
          (item) => item.NHANVIEN_ID == value
        );
        if (objThuNgan) {
          this.quaythu_id = objThuNgan.DONVI_ID;
          this.ma_tn = objThuNgan.MA_NV;
        }
      },
    },
    "form.idDonVi": {
      handler: async function (value) {
        this.fetchDSNhanVienCTV(value, this.form.idNhom);
      },
    },
    "form.idNhom": {
      handler: async function (value) {
        if (value) {
          this.fetchDSNhanVienCTV(this.form.idDonVi, value);
        }
      },
    },
    typeDongBo: {
      handler: async function (value) {
        this.resetData();
        this.fetchLayDanhSach_VNP();
      },
    },
  },
  created() {
    this.fetchDSThuQuy();
    this.fetchDSUserNeo();
    this.fetchDSNhomCTV();
    this.fetchDSDonViCTV();
  },
  mounted() {
    this.$refs.maTT.focus();
  },
  methods: {
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      this.date_chon = e.p_kyhoadon;
    },
    async fetchDSThuQuy() {
      const response = await API.getDanhSachThuQuy(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.form.dsThuQuy = response.data.data;
        this.form.idThuQuy = response.data.data[0].NHANVIEN_ID;
      }
    },
    async fetchDSUserNeo() {
      const response = await API.getDanhSachUserNeo(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.form.dsUserNeo = response.data.data;
        this.form.idUSerNeo = response.data.data[0].USER_VNP;
      } else {
        this.$root.toastError(
          "Người dùng: chưa được gán user NEO. Không thể thực hiện chức năng này!"
        );
      }
    },
    async fetchDSNhomCTV() {
      const response = await API.getDanhSachNhomCTV(this.axios, 6); // 6: CTV
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.form.dsNhom = response.data.data;
        // this.form.idNhom = response.data.data[0].LOAINV_ID;
      }
    },
    async fetchDSDonViCTV() {
      const params = {
        loaiDVId: "2,3,30,100",
      };
      const response = await API.getDanhSachDonViCTV(this.axios, params);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.form.dsDonViQL = response.data.data;
        // this.form.idDonVi = response.data.data[0].DONVI_ID;
      }
    },
    async fetchDSNhanVienCTV(donViId, loaidNVId = "") {
      //   this.loading(true);
      const params = {
        donViId: donViId,
        loaiNVId: loaidNVId,
      };
      const response = await API.getDanhSachNhanVienCTV(this.axios, params);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length > 0
      ) {
        this.form.dsNhanVien = response.data.data;
        // this.form.idNhanVien = response.data.data[0].NHANVIEN_ID;
      } else {
        this.form.dsNhanVien = [];
        this.form.idNhanVien = "";
      }
      this.loading(false);
    },
    async fetchLayDanhSach_VNP() {
      if (this.date_chon < moment(this.date_ht).subtract(1, "months")) {
        this.$root.toastError(
          "Bạn không thể lấy dữ liệu kỳ nhỏ hơn 1 kỳ tính từ kỳ hiện tại!"
        );
        return;
      }
      try {
        this.loading(true);
        const params = {
          kyCuoc: this.kyCuoc,
          maTT: this.form.maTT, //"HPGDD00057088"
          maTB: this.form.soMay, //"912511059"
          userNeo: this.form.idUSerNeo, //"nhbinh_hpg"
          htttIdNeo: this.htttIdNeo,
          flags: this.typeDongBo,
          tuNgay: this.form.checkTuNgay
            ? moment(this.form.tuNgay).format("DD/MM/YYYY")
            : "",
          denNgay: this.form.checkDenNgay
            ? moment(this.form.denNgay).format("DD/MM/YYYY")
            : "",
        };
        const response = await API.getDanhSachPhieuThanhToan(
          this.axios,
          params
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsPhieuThanhToanVNP = response.data.data;
          this.tongSoPhieu = response.data.data.length;
          this.fetchThanhToanThueBao(response.data.data[0]);
        } else {
          this.$root.toastError("Không có dữ liệu, mời thử lại!");
          this.resetData();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchGachNo() {
      const dsResult = this.$refs.gridPhieuThanhToan.getSelectedRecords() || [];
      const dsRequest = dsResult.map((item) => ({
        htttIdNeo: this.htttIdNeo,
        phieuIdNeo: item.PHIEU_ID,
        maTTNeo: item.MA_KH,
        ngayTTNeo: item.NGAY_TT,
        soPhieuNeo: item.SOPHIEU,
      }));
      try {
        this.loading(true);
        const data = {
          kyCuoc: this.kyCuoc,
          dsDongBo: dsRequest,
          kieuDongBo: this.typeDongBo,
          chungTu: this.form.chungTu,
          htttIdNeo: this.htttIdNeo,
          quayThuId: this.quaythu_id,
          userNeo: this.form.idUSerNeo,
          ghiChu: this.form.ghiChu,
        };
        const response = await API.gachNoDongBo(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Thành công");
          this.fetchLayDanhSach_VNP();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchThanhToanThueBao(data) {
      try {
        this.loading(true);
        const params = {
          kyCuoc: this.kyCuoc,
          phieuIdNeo: data.PHIEU_ID,
          userNeo: data.NGUOI_GACH,
          kieuDongBo: this.typeDongBo,
        };
        const response = await API.getDanhSachThueBao(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsChiTietThanhToan = response.data.data;
        } else {
          this.dsChiTietThanhToan = [];
        }
      } catch (error) {
        this.dsChiTietThanhToan = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    phieuTT_selectedRowChanged(index, data) {
      if (data) {
        this.fetchThanhToanThueBao(data);
      }
    },
    thuebao_selectedRowChanged(data) {
      if (data) {
        this.maThueBaoTT = data.SOMAY;
      }
    },
    onClickNhapMoi() {
      this.form.maTT = "";
      this.form.soMay = "";
      this.$refs.maTT.focus();
    },
    onClickLayDL() {
      this.fetchLayDanhSach_VNP();
    },
    onClickDongBo() {
      if (this.form.idThuQuy == "") {
        this.$root.toastError("Bạn hãy chọn thu ngân trước khi gạch nợ!");
        return;
      }
      // if(this.form.chungTu.trim() == ""){
      //     this.$root.toastError("Hãy nhập chứng từ!");
      //     return;
      // }
      const selected = this.$refs.gridPhieuThanhToan.getSelectedRecords();
      if (selected == null || selected.length <= 0) {
        this.$root.toastError("Hãy chọn phiếu để thực hiện gạch nợ!");
        return;
      }
      this.$confirm(`Bạn thật sự muốn đồng bộ dữ liệu không ?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Không đồng ý",
      })
        .then(() => {
          this.fetchGachNo();
        })
        .catch(() => {});
    },
    onClickPhieuTra() {
      this.$bvModal.show("modal-dsphieugachnovnp");
    },
    resetData() {
      this.dsPhieuThanhToanVNP = [];
      this.dsChiTietThanhToan = [];
      this.tongSoPhieu = 0;
      this.form.maTT = "";
      this.form.soMay = "";
    },
    thuebao_doubleclicked(index, data) {
      this.$bvModal.show("modal-thongtinTT");
    },
  },
  computed: {},
};
</script>
