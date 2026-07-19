<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import API from "../api/DaiLyNopNganHang";
import CommonsAPI from "@/modules/QLTN/api/Commons";
import PopupDocFile from "./components/popup-docfile.vue";
import PopupTimKiemNhanVienThuCuoc from '../components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc'
import {previewPrint} from "../../../utils/util";

export default {
  components: { breadcrumb, PopupDocFile, PopupTimKiemNhanVienThuCuoc },
  data() {
    return {
      header: {
        title: "ĐẠI LÝ NỘP TIỀN NGÂN HÀNG",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Đại lý nộp ngân hàng",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      waterMark: "Chọn kỳ hóa đơn",
      kyHoaDon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      max_kyhoadon: moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .toDate(),
      kyCuoc: "",
      chuKy: [],
      chuKySelected: null,
      form: {
        maDaiLy: "",
        tenDaily: "",
        diaChi: "",
        idNganHang: "",
        idKieuNop: "",
        idThuQuy: "",
        soTien: 0,
        ngayNop: new Date(),
        lyDo: "",
      },
      dsNganHang: [],
      dsKieuNop: [],
      dsThuQuy: [],
      dsDaiLyNganHang: [],
      isType: 1, //1: Thêm mới, 2: Cập nhật
      thongTinTemp: null,
      isAddNew: true,
      isSaving: false,
      thongTinDaiLy: null,
      quaythu_id: "0",
      ma_tn: "",
      keyReset: 1
    };
  },
  validations: {},
  watch: {
    "form.idThuQuy": {
      handler: async function (value) {
        const objThuNgan = this.dsThuQuy.find(
          (item) => item.NHANVIEN_ID == value
        );
        if (objThuNgan) {
          this.quaythu_id = objThuNgan.DONVI_ID.toString();
          this.ma_tn = objThuNgan.MA_NV;
        }
      },
    },
  },
  created() {
    this.fetchDSNganHang();
    this.fetchDSKieuNop();
    this.fetchDSThuQuy();
  },
  mounted() {
    this.updateChuKyHoaDon(moment(this.kyHoaDon).format("YYYYMM"));
    this.$refs.maDaiLy.focus();
  },
  methods: {
    onKyHoaDonChange: function (args) {
      if (args.value === null) {
        this.$root.toastError("Vui lòng chọn Kỳ hóa đơn");
        return;
      }
      this.updateChuKyHoaDon(moment(args.value).format("YYYYMM"));
    },
    async updateChuKyHoaDon(pKyHoaDon) {
      try {
        this.loading(true);
        let response = await CommonsAPI.getChuKyNoTheoKyCuoc(
          this.axios,
          pKyHoaDon
        );
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.chuKy = response.data.data;
          this.chuKySelected = this.chuKy[0].CHUKY;
        } else {
          this.chuKy = [];
          this.chuKySelected = null;
        }
        this.kyCuoc =
          moment(this.kyHoaDon).format("YYYYMM") + this.chuKySelected;
      } catch (error) {
        // console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async fetchDSNganHang() {
      const response = await API.getDanhSachNganHang(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsNganHang = response.data.data;
      }
    },
    async fetchDSKieuNop() {
      const response = await API.getDanhSachKieuNop(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsKieuNop = response.data.data;
        this.form.idKieuNop = response.data.data[0].KIEUNOP_ID
      }
    },
    async fetchDSThuQuy() {
      const response = await API.getDanhSachThuQuy(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsThuQuy = response.data.data;
        this.form.idThuQuy = response.data.data[0].NHANVIEN_ID;
      }
    },
    async fetchDaiLyNganHang() {
      this.keyReset += 1;
      const params = {
        kyCuoc: this.kyCuoc, //20210101
        maNVTC: this.form.maDaiLy || "0", //ABI3
        quayThuId: this.quaythu_id, //974
      };
      this.loading(true);
      try {
        const response = await API.getDanhSachDaiLyNganHang(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          const dsReult = response.data.data
          this.dsDaiLyNganHang = dsReult;
          this.thongTinTemp = response.data.data[0];
          this.isType = 2;
          this.isAddNew = false;
        } else {
          this.isType = 1;
          this.$root.toastError(`Không tìm thấy dữ liệu. Mời thử lại`);
          this.dsDaiLyNganHang = [];
          this.thongTinTemp = null;
          this.resetThongTin();
        }
      } catch (error) {
        this.isType = 2;
        this.$root.toastError(error.data.message_detail);
        this.dsDaiLyNganHang = [];
        this.thongTinTemp = null;
        this.resetThongTin();
      } finally {
        this.loading(false);
      }
    },
    async checkMaDaiLy () {
      try {
        const response = await API.getThongTinDaiLy(
          this.axios,
          this.form.maDaiLy
        );
        const data = await response.data

        if (data.error_code === "BSS-00000000") {
          return true
        }
      } catch (error) {
        return false
      }
    },
    async LoadDaiLy () {
      // if (this.form.maDaiLy == "") {
      //   this.$root.toastError(`Bạn chưa chọn đại lý nộp tiền!`);
      //   this.$refs.maDaiLy.focus();
      //   return;
      // }
      this.loading(true);
      try {
        const response = await API.getThongTinDaiLy(
          this.axios,
          this.form.maDaiLy
        );
        const data = await response.data

        if (data.error_code === "BSS-00000000") {
          this.thongTinDaiLy = data.data[0];
          this.form.maDaiLy = this.thongTinDaiLy.MA_NV;
          this.form.tenDaily = this.thongTinDaiLy.TEN_NV;
          this.form.diaChi = this.thongTinDaiLy.DIACHI_NV;
          this.fetchDaiLyNganHang();
        }
      } catch (error) {
        this.thongTinDaiLy = null;
        // this.form.maDaiLy = "";
        this.form.tenDaily = "";
        this.form.diaChi = "";
        // this.$root.toastError(error.data.message_detail);
        this.$toast.error('Không tìm thấy mã đại lý.')
      } finally {
        this.loading(false);
      }
    },
    nganhang_selectedRowChanged(data) {
      if (data) {
        this.fillThongTin(data);
        this.thongTinTemp = data;
        this.isType = 2;
        this.isSaving = false
      } else {
        this.thongTinTemp = null;
        this.resetThongTin();
      }
    },
    fillThongTin(data) {
      this.form.maDaiLy = data.MANV_TC;
      this.form.tenDaily = data.TEN_NV;
      this.form.diaChi = data.DIACHI_NV;
      this.form.idKieuNop = data.KIEUNOP_ID;
      this.form.idNganHang = data.NGANHANG_ID;
      this.form.soTien = data.TIENNOP;
      this.form.ngayNop = moment(data.NGAYNOP,"DD/MM/YYYY").toDate();
      this.form.lyDo = data.GHICHU;
    },
    resetThongTin() {
      // this.form.maDaiLy = "";
      // this.form.tenDaily = "";
      // this.form.diaChi = "";
      this.form.idKieuNop = "";
      this.form.idNganHang = "";
      this.form.soTien = "";
      this.form.ngayNop = new Date();
      this.form.lyDo = "";
    },
    async fetchXoaDaiLy() {
      const id = this.thongTinTemp.NOPNH_ID;
      this.loading(true);
      try {
        const response = await API.xoaDaiLyNganHang(this.axios, id);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(`Xóa đăng ký nộp tiền ngân hàng cho đại lý thành công!`);
          this.fetchDaiLyNganHang();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async TaoMoi() {
      const data = {
        nopNganHangId: null,
        soPhieu: null,
        maNVTC: this.form.maDaiLy,
        nganHangId: this.form.idNganHang,
        tienNop: this.form.soTien,
        ngayNop: moment(this.form.ngayNop).isValid() ? moment(this.form.ngayNop).format("DD/MM/YYYY") : this.form.ngayNop,
        kyHoaDon: this.kyCuoc,
        maTN: this.ma_tn,
        ghiChu: this.form.lyDo,
        donViId: this.quaythu_id,
        kieuNopId: this.form.idKieuNop,
      };
      this.loading(true);
      try {
        const response = await API.themDaiLyNganHang(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(
            `Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!`
          );
          this.isAddNew = false;
          this.fetchDaiLyNganHang();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async CapNhat() {
      const data = {
        nopNganHangId: this.thongTinTemp.NOPNH_ID,
        soPhieu: this.thongTinTemp.SOPHIEU,
        maNVTC: this.form.maDaiLy,
        nganHangId: this.form.idNganHang,
        tienNop: this.form.soTien,
        ngayNop: moment(this.form.ngayNop).isValid() ? moment(this.form.ngayNop).format("DD/MM/YYYY") : this.form.ngayNop,
        kyHoaDon: this.kyCuoc,
        maTN: this.ma_tn,
        ghiChu: this.form.lyDo,
        donViId: this.quaythu_id,
        kieuNopId: this.form.idKieuNop,
      };
      this.loading(true);
      try {
        const response = await API.capNhatDaiLyNganHang(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(
            `Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!`
          );
          this.isAddNew = false;
          this.fetchDaiLyNganHang();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async KiemTraDuLieu() {
      if (this.form.idNganHang == "" || this.form.idNganHang == "0") {
        this.$root.toastError(`Hãy chọn ngân hàng!`);
        return false;
      }
      if (this.form.maDaiLy == "") {
        this.$root.toastError(`Bạn chưa chọn đại lý nộp tiền!`);
        this.$refs.maDaiLy.focus();
        return false;
      } else {
        const f = await this.checkMaDaiLy()
        if (!f) {
          this.$toast.error('Mã đại lý không tồn tại')
          this.$refs.maDaiLy.focus();
          return false
        }
      }
      if (this.form.tenDaily == "") {
        this.$root.toastError(
          `Hãy nhập Mã đại lý sau đó nhấn Enter - hoặc tìm chính xác thông tin Đại lý trước khi ghi lại!`
        );
        this.$refs.tenDaily.focus();
        return false;
      }
      if (this.form.soTien == "" || this.form.soTien == "0") {
        this.$root.toastError(`Bạn chưa nhập số tiền nộp!`);
        // this.$refs.soTien.focus();
        return false;
      }
      if (moment(new Date()).diff(this.form.ngayNop, "DD/MM/YYYY") < 0) {
        this.$root.toastError("Ngày nộp không được lớn hơn ngày hiện tại!");
        this.$refs.ngayNop.show();
        return false;
      }
      return true;
    },
    LayDS() {
      this.fetchDaiLyNganHang();
    },
    NhapMoi() {
      this.$refs.maDaiLy.focus();
      this.resetThongTin();
      this.isType = 1;
      this.isSaving = false;
      this.isAddNew = true
    },
    GhiLai() {
      if (!this.KiemTraDuLieu()) return;
      if (this.isType === 1) {
        this.TaoMoi();
      } else {
        this.CapNhat();
      }
    },
    Huy() {
      if (this.isType === 1) {
        this.resetThongTin();
      } else {
        this.fillThongTin(this.thongTinTemp);
      }
      this.isAddNew = false;
      this.isSaving = true;
    },
    Xoa() {
      if (!this.thongTinTemp) {
        this.$root.toastError(`Chọn đại lý ngân hàng!`);
        return;
      }
      this.$confirm(`Bạn thật sự muốn xóa đăng ký này ?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.fetchXoaDaiLy();
        })
        .catch(() => {});
    },
    PhieuThu() {
      if (!this.thongTinTemp) {
        this.$root.toastError(`Không có dữ liệu để in phiếu !`);
        return;
      }
      this.InPhieuThu();
    },
    async InPhieuThu(){
      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url:
            `${baseUrl}/web-thuno/api/thu-no/quan-ly-dai-ly/in-phieu-thu-dai-ly-nop-ngan-hang`,
          method: "POST",
          responseType: "blob",
          data: {
              kyCuoc: this.kyCuoc,
              maNVTC: this.form.maDaiLy || "0",
              quayThuId: this.quaythu_id,
              id: this.thongTinTemp.NOPNH_ID
          }
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình in phiếu thu nộp tiền ngân hàng cho đại lý !");
      } finally {
        this.loading(false);
      }
    },
    DocFile() {
      if (this.form.idNganHang == "" || this.form.idNganHang == "0") {
        this.$root.toastError(`Hãy chọn ngân hàng!`);
        return false;
      }
    //   if (this.form.maDaiLy == "") {
    //     this.$root.toastError(`Bạn chưa chọn đại lý nộp tiền!`);
    //     this.$refs.maDaiLy.focus();
    //     return false;
    //   }
      this.$bvModal.show("modal-docfile");
    },
    sendData(data) {
      if (data.msgReturn === "OK") {
        this.fetchDaiLyNganHang();
        this.$bvModal.hide("modal-docfile");
      }
    },
    openFormNVTC() {
      this.$refs.dlgTimKiemNVTC.openDialog();
    },
    getNVTCProcess(value) {
      this.form.maDaiLy = value.maNVTC;
      this.LoadDaiLy()
    },
  },
  computed: {},
};
</script>
