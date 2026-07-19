<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import Vue from "vue";
import API from "../api/XuLyTBKhoaNoCuoc";
import KyCuoc from "../components/KyCuoc.vue";
import { mapActions, mapState } from "vuex";
import moment from "moment";
import PopUpMoMay from "./momay";

export default {
  components: { appKyCuoc: KyCuoc, PopUpMoMay },
  data() {
    return {
      dsYeuCau: [],
      dsThueBaoChuaXL: [],
      dsThueBaoDaXL: [],
      idDichVu: "",
      soMay: "",
      idLoaiKH: 1,
      idLoaiHinh: "",
      trangThaiTB: "",
      idDoiTuong: "",
      tenTB: "",
      diaChiTT: "",
      diaChiBC: "",
      idTrangThaiTT: "",
      noiDung: "",
      dsThongTinNo: [],
      enableHoanThanh: true,
      kyCuoc: "",
      kyCuocView: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      selectedTab: 0,
      vphieu_id: 0,
      vthuebao_id: 0,
      dsDoiTuong: [],
      dsTrangThaiTT: [],
      vhuonggiao_id: 0,
      selectedKieuLD: null,
      ma_tt: "",
      sumTemplate: function () {
        return {
          template: Vue.component("sumTemplate", {
            template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {
              return { data: { data: {} } };
            },
          }),
        };
      },
      TextTemplate: function () {
        return {
          template: Vue.component("sumTemplate", {
            template: `<input type="text" class="form-control" value="Tổng cộng" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data: function () {
              return { data: { data: {} } };
            },
          }),
        };
      },
      keyReset: 1,
    };
  },
  watch: {
    idDichVu: {
      handler: async function (val) {
        if (val) {
          await this.getDanhSachLoaiHinhThueBao(val);
          if (this.danhSachLoaiHinhThueBao.length > 0) {
            // this.idLoaiHinh = this.danhSachLoaiHinhThueBao[0].LOAITB_ID;
          }
        }
      },
    },
  },
  created() {
    this.initForm();
    this.getDSKieuLD();
    this.getDichVu();
    this.getLoaiKH();
    this.getDoiTuong();
    this.getTrangThaiTT();
  },
  mounted() {},
  methods: {
    ...mapActions("qltnCommon", [
      "getDanhSachDichVuVienThong",
      "getDanhSachLoaiHinhThueBao",
      "getDanhSachLoaiKhachHang",
    ]),
    initForm() {
      if (this.$route.query && this.$route.query.HUONGGIAO_ID) {
        this.vhuonggiao_id = this.$route.query.HUONGGIAO_ID;
      }
    },
    LayTT() {
      this.getDSKieuLD();
    },
    async HoanThanh() {
      if (this.dsThueBaoChuaXL.length <= 0) {
        this.$root.toastError("Không có thuê bao để hoàn thành !");
        return;
      }
      if (this.noiDung.trim() == "") {
        this.$root.toastError("Bạn phải nhập nội dung thực hiện !");
        return;
      }
      if (this.idTrangThaiTT == "") {
        this.$root.toastError("Bạn chưa chọn trạng thái thanh toán !");
        return;
      }
      if (this.idTrangThaiTT == 7) {
        // đã thu tiền
        const tongno = await this.kiemTraNoTB();
        if (tongno > 0) {
          this.$root.toastError(
            `Thuê bao ${this.soMay} đang nợ ${tongno}. Bạn hãy chọn trạng thái thu tiền khác!`
          );
          return;
        }
      }
      this.handlerHoanThanh();
    },
    async kiemTraNoTB() {
      try {
        const response = await API.traCuuNoTB(this.axios, this.vthuebao_id);
        if (response.data.error_code === "BSS-00000000") {
          return response.data.data.RESULT;
        }
      } catch (error) {
        return 0;
      }
    },
    async handlerHoanThanh() {
      try {
        this.loading(true);
        const data = {
          phieuId: this.vphieu_id,
          nhanVienTHId: this.$auth.getNhanVienID(),
          ngayTH: moment(new Date()).format("DD/MM/YYYY"),
          noiDungTH: this.noiDung.trim(),
          kqxlId: this.idTrangThaiTT,
        };
        const response = await API.capNhatPhieuXLKhoaMay(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Hoàn thành phiếu thành công !");
          await this.getDSKieuLD();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    ThanhLy() {
      this.$router.push({
        name: "TaoDanhSachThanhLyThueBaoTamDung",
      });
    },
    MoMay() {
      //   this.$router.push({
      //     name: "MoMayTheoTungSoMayAcc",
      //     params: { txtMaTb: this.soMay },
      //   });
      this.$bvModal.show("modal-momay-somay");
    },
    handleKyHoaDon(e) {
      if (this.kyCuoc == "")
        this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    async getDichVu() {
      await this.getDanhSachDichVuVienThong();
      if (this.danhSachDichVuVienThong.length > 0) {
        this.idDichVu = 1;
      }
    },
    async getLoaiKH() {
      await this.getDanhSachLoaiKhachHang();
    },
    async getDoiTuong() {
      const response = await API.getDanhMucDoiTuong(this.axios);
      if (response.data.error_code == "BSS-00000000") {
        this.dsDoiTuong = response.data.data;
        this.idDoiTuong = this.dsDoiTuong[0].DOITUONG_ID;
      }
    },
    async getTrangThaiTT() {
      const params = {
        kieu: 1,
        nhomConId: 2,
      };
      const response = await API.getDsTrangThaiTT(this.axios, params);
      if (response.data.error_code == "BSS-00000000") {
        this.dsTrangThaiTT = response.data.data;
      }
    },
    async getDSKieuLD() {
      try {
        this.loading(true);
        const params = {
          huongGiaoId: this.vhuonggiao_id,
          nhanVienId: this.$auth.getNhanVienID(),
        };
        const response = await API.getDsKieuLDKhoaMay(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsYeuCau = response.data.data;
        } else {
          this.dsYeuCau = [];
          //   this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsYeuCau = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async hienThiDanhSach(vtab) {
      const vkieuld_id = this.selectedKieuLD.KIEULD_ID;
      let result = [];
      try {
        this.loading(true);
        const params = {
          huongGiaoId: this.vhuonggiao_id,
          ttphId: vtab == 1 ? 1 : 4,
          donViIdL: this.$auth.getDonViID(),
          nhanVienId: this.$auth.getNhanVienID(),
          kieuLDId: vkieuld_id,
        };
        const response = await API.getDsXlKhoaMayThuHoi(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          result = response.data.data;
        } else {
          result = [];
          this.cleanData();
        }
      } catch (error) {
        result = [];
        this.cleanData();
        this.$root.toastError(error.data.message_detail);
      } finally {
        vtab == 1
          ? (this.dsThueBaoChuaXL = result)
          : (this.dsThueBaoDaXL = result);
        this.loading(false);
      }
    },
    kieuLD_selectedRowChanged(data) {
      if (data) {
        this.selectedKieuLD = data;
        if (this.selectedTab == 0) {
          this.hienThiDanhSach(1);
        } else {
          this.hienThiDanhSach(2);
        }
      } else {
        this.selectedKieuLD = null;
      }
    },
    thuebao_selectedRowChanged(data) {
      if (data) {
        this.vphieu_id = data.PHIEU_ID;
        this.vthuebao_id = data.THUEBAO_ID;
        this.soMay = data.MA_TB;
        this.tenTB = data.TEN_TB;
        this.diaChiTT = data.DIACHI_TT;
        this.diaChiBC = data.DIACHI_BC;
        this.trangThaiTB = data.TRANGTHAI_TB;
        this.idDichVu = data.DICHVUVT_ID;
        this.idLoaiHinh = data.LOAITB_ID;
        this.idDoiTuong = data.DOITUONG_ID;
        this.idLoaiKH = data.LOAIKH_ID;
        this.ma_tt = data.MA_TT;
        this.idTrangThaiTT = "";
        this.noiDung = "";
        this.hienThiTTNo(1);
      } else {
        this.cleanData();
      }
    },
    async hienThiTTNo(kieu) {
      try {
        this.loading(true);
        const params = {
          kyCuoc: this.kyCuoc, //20211001
          dsMaTT: this.ma_tt,
          dsMaTB: this.soMay.trim(),
          dsChuKyNo: null,
        };
        const response = await API.getDsChiTietNo(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.keyReset += 1;
          this.dsThongTinNo = response.data.data;
        } else {
          this.dsThongTinNo = [];
          //   this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsThongTinNo = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    onChangeTab(index) {
      this.selectedTab = index;
      if (index == 0) {
        this.enableHoanThanh = true;
        this.hienThiDanhSach(1);
      } else {
        this.enableHoanThanh = false;
        this.hienThiDanhSach(2);
      }
    },
    cleanData() {
      this.vphieu_id = 0;
      this.vthuebao_id = 0;
      this.ma_tt = "";
      this.noiDung = "";
      this.soMay = "";
      this.tenTB = "";
      this.diaChiTT = "";
      this.diaChiBC = "";
      this.trangThaiTB = "";
      this.idDichVu = "";
      this.idLoaiHinh = "";
      this.idTrangThaiTT = "";
      this.dsThongTinNo = [];
    },
    async hienThiChiTietNoTheoKhoanMuc(kieu) {},
  },
  computed: {
    ...mapState("qltnCommon", [
      "danhSachDichVuVienThong",
      "danhSachLoaiHinhThueBao",
      "danhSachLoaiKH",
    ]),
    aggregatesColumns() {
      return [
        {
          field: "TENKHOANMUC",
          type: "Sum",
          footerTemplate: this.TextTemplate,
        },
        {
          field: "TONGNO",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "NO_DK",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "NO_PS",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "CON_NO",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "TONG_TRA",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "DIEU_CHINH",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
      ];
    },
  },
};
</script>
