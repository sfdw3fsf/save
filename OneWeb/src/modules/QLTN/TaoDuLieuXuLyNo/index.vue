<template src="./index.template.html"></template>
<style src="./style.scss" scoped>
</style>
<script>
import moment from "moment";
import KyCuoc from "../components/KyCuoc.vue";
import { mapActions, mapState } from "vuex";
import xlsx from "xlsx";
import API from "../api/TaoDuLieuXuLyNo";
import ComboboxGrid from "../components/ComboboxGrid";
import PopupLichSuNo from "./components/frmLsNo.vue";
import PopupChiTietNoNhieuThueBao from "./components/frmChiTietNoNhieuThueBao.vue";
import PopupChiTietNoMotThueBao from "./components/frmChiTietNoMotThueBao.vue";
import PopupChiTietLanTaoDuLieu from "./components/frmChiTietLanTaoDuLieu.vue";
import DocFileByMaTT from "./components/frmDocFileByMaTT.vue";
import DocFileByMaTB from "./components/frmDocFileByMaTB.vue";
import APICommon from "../api/Commons";
import ModalXuatDuLieu from "../../ECMS/TraCuuSplitterTheoPortOLT/ModalXuatDuLieu/index.vue";

export default {
  components: {
    appKyCuoc: KyCuoc,
    ComboboxGrid,
    PopupLichSuNo,
    PopupChiTietNoNhieuThueBao,
    PopupChiTietNoMotThueBao,
    PopupChiTietLanTaoDuLieu,
    DocFileByMaTT,
    DocFileByMaTB,
    appModalXuatDuLieu: ModalXuatDuLieu
  },
  filters: {
    currency: function (value) {
      let valNum = parseInt(value.toString().replaceAll(",", ""));
      if (valNum >= 0) return valNum.toLocaleString("en-En");
      return value;
    }
  },
  data() {
    return {
      header: {
        title: "TẠO DỮ LIỆU XỬ LÝ NỢ",
        list: [
          { name: "Xử lý nợ", link: { name: "Ui.cards" } },
          {
            name: "Tạo dữ liệu xử lý nợ",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      kyCuoc: "",
      kyCuocView: moment(new Date()).subtract(1, "months").endOf("month").toDate(),
      form: {
        idDonVi: "",
        idNhanVienQL: "",
        checkLoaiKH: false,
        idLoaiKH: [],
        idQuan: [],
        checkNguoiGiao: false,
        idNguoiGiao: "",
        idKieuXuLy: "0",
        checkTuTien: false,
        selectedTuTien: ">",
        tuTien: 0,
        idKieuNo: "1",
        checkDenTien: false,
        selectedDenTien: "<",
        denTien: 0,
        checkLanTao: false,
        idLanTao: "",
        checkNguoiNhan: false,
        idNguoiNhan: [],
        checkTuThang: false,
        selectedTuThang: ">",
        tuThang: 0,
        checkDenThang: false,
        selectedDenThang: "<",
        denThang: 0,
        checkBuocXL: false,
        idBuocXL: "",
        checkNamNo: false,
        idNamNo: "",
        ghiChu: "",
        checkChieuKhoa: true,
        idChieuKhoa: "2",
        tuNgay: new Date(),
        denNgay: new Date(),
        idTrangThai: "3",
        idKetQuaXL: "",
      },
      dsKhachHang: [],
      dsDotSoLieu: [],
      dsNhanVienQL: [],
      dsBuocXuLy: [],
      dsNguoiGiao: [],
      dsNguoiNhan: [],
      dsLanTao: [],
      dsNamNo: [],
      dsKetQuaXL: [],
      maTT: "",
      waterMark: "Chọn ngày",
      dateFormat: "dd/MM/yyyy",
      labelMa: "Mã TT",
      fieldsDotSoLieu: [
        {
          fieldName: "LANTAO_ID",
          headerText: "ID",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "NGAY_TAO",
          headerText: "Ngày tạo",
          allowFiltering: true,
          cssClass: 'text-right',
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người tạo",
          allowFiltering: true,
        },
        {
          fieldName: "KIEUTAO",
          headerText: "Kiểu tạo",
          allowFiltering: true,
          width: 120,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "TONGTIEN",
          headerText: "Tông tiền",
          allowFiltering: true,
          type: 'number',
          format: 'N0',
          cssClass: 'text-danger'
        },
        {
          fieldName: "TU_NGAYKHOA",
          headerText: "Từ ngày khóa",
          allowFiltering: true,
        },
        {
          fieldName: "DEN_NGAYKHOA",
          headerText: "Đến ngày khóa",
          allowFiltering: true,
        },
        {
          fieldName: "TU_TIEN",
          headerText: "Từ tiền",
          allowFiltering: true,
          type: 'number',
          format: 'N0'
        },
        {
          fieldName: "DEN_TIEN",
          headerText: "Đến tiền",
          allowFiltering: true,
          type: 'number',
          format: 'N0'
        },
        {
          fieldName: "NAMNO",
          headerText: "Năm nợ",
          allowFiltering: true,
          width: 100,
        },
      ],
      fieldsKH: [
        {
          fieldName: "MA_XLN",
          headerText: "Mã XLN",
          allowFiltering: true,
        },
        {
          fieldName: "SOLUONG_TT",
          headerText: "Số lượng MTT",
          allowFiltering: true,
        },
        {
          fieldName: "MA_KH",
          headerText: "Mã KH",
          allowFiltering: true,
        },
        {
          fieldName: "MATB_DD",
          headerText: "Mã đại diện",
          allowFiltering: true,
        },
        {
          fieldName: "TT_HS",
          headerText: "Tình trạng HS",
          allowFiltering: true,
        },
        {
          fieldName: "TONGNO",
          headerText: "Tổng nợ",
          allowFiltering: true,
          type: 'number',
          format: 'N0',
          cssClass: 'text-danger'
        },
        {
          fieldName: "THANGNO",
          headerText: "Tháng nợ",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ khách hàng",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ báo cước",
          allowFiltering: true,
        },
        {
          fieldName: "NODK",
          headerText: "Nợ đầu kỳ",
          allowFiltering: true,
          type: 'number',
          format: 'N0',
          cssClass: 'text-danger'
        },
        {
          fieldName: "NOPS",
          headerText: "Nợ phát sinh",
          allowFiltering: true,
          type: 'number',
          format: 'N0',
          cssClass: 'text-primary'
        },
        {
          fieldName: "SO_THANGNO",
          headerText: "Số tháng nợ",
          allowFiltering: true,
        },
        {
          fieldName: "DS_THANGNO",
          headerText: "Danh sách tháng nợ",
          allowFiltering: true,
        },
        {
          fieldName: "KIEU_NO",
          headerText: "Kiểu nợ",
          allowFiltering: true,
        },
        {
          fieldName: "MANV_TC",
          headerText: "Nhân viên XL",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_KHOA",
          headerText: "Ngày khóa",
          allowFiltering: true,
        },
        {
          fieldName: "TRANGTHAI_XL",
          headerText: "Trạng thái",
          allowFiltering: true,
        },
        {
          fieldName: "MANV_TC",
          headerText: "Mã NVTC",
          allowFiltering: true,
        },
        {
          fieldName: "MANV_QL",
          headerText: "MÃ NVQL",
          allowFiltering: true,
        },
        {
          fieldName: "TENNV_QL",
          headerText: "Tên NVQL",
          allowFiltering: true,
        },
        {
          fieldName: "MA_TUYEN",
          headerText: "Mã tuyến",
          allowFiltering: true,
        },
        {
          fieldName: "TENTUYEN",
          headerText: "Tên tuyến",
          allowFiltering: true,
        },
      ],
      dsSelected: [],
      objLanTao: null,
      objKhachHang: null,
      dsLichSuNo: [],
      selectedXLN: "",
      totalElement: 0,
      danhsachDonVi: []
    };
  },
  validations: {},
  watch: {
    "form.idDonVi": {
      handler: async function (value) {
        this.loading(true);
        const response = await API.getDsNhanVienQL(this.axios, value);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsNhanVienQL = response.data.data;
          this.form.idNhanVienQL = response.data.data[0].NHANVIEN_ID;
        } else {
          this.dsNhanVienQL = [];
          this.form.idNhanVienQL = "";
        }
        this.loading(false);
      },
    },
    "form.idKieuXuLy": {
      handler: async function (value) {
        if (value == "3") {
          this.labelMa = "Số máy/Acc";
          this.form.idBuocXL = "";
          this.dsBuocXuLy = [];
          this.disabledByKH(false);
        } else if (value == "2") {
          this.labelMa = "Mã KH";
          const response = await API.getDanhSachBuocXLN(this.axios, value);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data.length > 0
          ) {
            this.form.idBuocXL = response.data.data[0].BUOCXLN_ID;
            this.dsBuocXuLy = response.data.data;
          } else {
            this.form.idBuocXL = "";
            this.dsBuocXuLy = [];
          }
          this.disabledByKH(true);
          this.form.checkNamNo = true;
        } else {
          this.labelMa = "Mã TT";
          this.form.idBuocXL = "";
          this.dsBuocXuLy = [];
          this.disabledByKH(false);
        }
        this.dsKhachHang = [];
      },
      // immediate:true
    },
    "form.idBuocXL": {
      handler: async function (value) {
        if (value == "") {
          this.form.idNguoiGiao = "";
          this.dsNguoiGiao = [];
          return;
        }
        this.loading(true);
        const response = await API.getDsNguoiGiao(this.axios, value);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.form.idNguoiGiao = response.data.data[0].NHANVIEN_ID;
          this.dsNguoiGiao = response.data.data;
        } else {
          this.form.idNguoiGiao = "";
          this.dsNguoiGiao = [];
        }
        this.loading(false);
      },
    },
    "form.idNguoiGiao": {
      handler: async function (value) {
        if (value == "") {
          this.dsNguoiNhan = [];
          this.form.idNguoiNhan = [];
          return;
        }
        const params = {
          buocXLNId: this.form.idBuocXL,
          nhanVienId: value,
        };
        this.loading(true);
        const response = await API.getDsNguoiNhan(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsNguoiNhan = response.data.data;
          this.form.idNguoiNhan = response.data.data.map(
            (item) => item.NHANVIEN_ID
          );
        } else {
          this.dsNguoiNhan = [];
          this.form.idNguoiNhan = [];
        }
        this.loading(false);
      },
    },
    // "form.idLanTao": {
    //   handler: async function (value) {
    //     const params = {
    //       kyCuoc: 20210301,
    //       lanTaoId: value,
    //     };
    //     const response = await API.getDsDotLayDuLieu(this.axios, params);
    //     if (response.data.error_code === "BSS-00000000") {
    //       this.dsDotSoLieu = response.data.data;
    //     }
    //   },
    // },
    dsKhachHang: {
      handler: function (value) {
        this.totalElement = value.length;
      },
    },
  },
  created() {
    this.fetchDSDonVi();
    this.fetchLoaiKH();
    this.fetchQuan();
    this.fetchLanTao();
    this.fetchNamNo();
    this.fetchKetQuaXL();
    this.fetchKyCuoc();
    this.form.idKieuXuLy = "1";
  },
  mounted() {
    this.$refs.ghiChu.focus();
  },
  methods: {
    ...mapActions("qltnCommon", [
      "getDanhSachLoaiKhachHang",
      "getDanhMucQuanHuyen",
      "getDanhSachDonVi",
    ]),
    disabledByKH(active) {
      this.form.checkNguoiGiao = active;
      this.form.checkLanTao = active;
      this.form.checkNguoiNhan = active;
      this.form.checkBuocXL = active;
    },
    handleKyHoaDon(e) {
      if (this.kyCuoc == "") {
        this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
      }
    },
    async HT_COMBO_DONVI(nhanVienId) {
      let res = [];
      try {
        let data = {
          // pNhanVienId: nhanVienId, //donViDLId,
        };
        let response = await API.getDSDonViQuanLy(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            res = response.data.data
          }
        }
      } catch (error) {
        // this.$toast.error(error.data.message_detail);
        res = []
      } finally {
        return res
      }
    },
    async fetchDSDonVi() {
      //this.getDanhSachDonVi();
      this.danhsachDonVi = await this.HT_COMBO_DONVI(this.$auth.getNhanVienID());
      if (this.danhsachDonVi.length > 0) {
        this.form.idDonVi = this.danhsachDonVi[0].DONVI_ID;
      }
    },
    fetchLoaiKH() {
      this.getDanhSachLoaiKhachHang();
      if (this.danhSachLoaiKH.length > 0) {
        this.form.idLoaiKH = this.danhSachLoaiKH.map((item) => item.LOAIKH_ID);
      }
    },
    fetchQuan() {
      if (
        this.danhMucQuanHuyen === undefined ||
        this.danhMucQuanHuyen.length === 0
      ) {
        this.getDanhMucQuanHuyen();
      }
      if (this.danhMucQuanHuyen.length > 0) {
        this.form.idQuan = this.danhMucQuanHuyen.map((item) => item.QUAN_ID);
      }
    },
    async fetchLanTao() {
      const response = await API.getDanhSachLanTao(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.form.idLanTao = response.data.data[0].LANTAO_ID;
        this.dsLanTao = response.data.data;
      }
    },
    async fetchNamNo() {
      const response = await API.getDanhSachNamNo(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.form.idNamNo = response.data.data[0].NAM;
        this.dsNamNo = response.data.data;
      }
    },
    async fetchKetQuaXL() {
      const response = await API.getDsKetQuaXL(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.form.idKetQuaXL = response.data.data[0].TRANGTHAI_XLN_ID;
        this.dsKetQuaXL = response.data.data;
      }
    },
    HienThiDanhSach_MaTT() {
      if (this.maTT.trim() == "") {
        this.$root.toastError("Bạn chưa nhập mã tìm kiếm!");
        return;
      }
      this.fetchDanhSachKH(this.maTT.trim())
    },
    async fetchDanhSachKH(dsma_xln) {
      const params = {
        kyCuoc: this.kyCuoc, //20210101
        dsMaXLN: dsma_xln, //HPG-06-506987
        kieuTao: this.form.idKieuXuLy,
      };
      this.loading(true);
      try {
        const response = await API.getDsKhachHang(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKhachHang = response.data.data;
        } else {
          this.dsKhachHang = [];
          this.$root.toastError("Không tìm thấy dữ liêu, mời thử lại!");
        }
      } catch (error) {
        this.dsKhachHang = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchTaoXLN() {
      const dsSelected_KH = this.$refs.danhsachKH.getSelectedRecords() || [];

      const soluong = dsSelected_KH.length;
      //   const dsResult = this.dsKhachHang.filter((item) =>
      //     this.dsSelected.includes(item.MA_XLN)
      //   );
      const dsResult = dsSelected_KH;
      const tongtien = dsResult.reduce((total, item) => {
        return total + parseInt(item.TONGNO);
      }, 0);
      const dsXLN = dsResult.map((item) => ({
        xuLyNoId: null,
        lanTaoId: null,
        maXLN: item.MA_XLN,
        maTBDD: item.MATB_DD,
        tenKH: item.TEN_TT,
        diaChiKH: item.DIACHI_TT,
        trangThai: item.TRANGTHAI_XL == "OK" ? 1 : 0,
        ngayKT: null,
        nhanVienId: null,
        xuLyNoChaId: null,
        ngayKhoa: item.NGAY_KHOA,
        tongNo: item.TONGNO,
        soCV: item.SO_CV,
        nhanVienPLId: item.NHANVIEN_PL_ID,
        diaChiBC: item.DIACHI_BC,
        maTT: item.MA_TT,
        ttHS: item.TT_HS,
      }));
      const data = {
        lanTaoId: null, //523414
        ngayTao: moment(new Date()).format("DD/MM/YYYY"), //moment(new Date()).format("DD/MM/YYYY") "01/02/2021"
        soLuong: soluong, //10
        tongTien: tongtien,
        donViId: this.form.idDonVi,
        loaiKHId: this.form.checkLoaiKH ? this.form.idLoaiKH : null,
        kieuNo: this.form.idKieuNo,
        tuTien: this.form.checkTuTien ? this.form.tuTien.toString().replaceAll(",", "") : null,
        denTien: this.form.checkDenTien ? this.form.denTien.toString().replaceAll(",", "") : null,
        tuThangNo: this.form.checkTuThang ? this.form.tuThang : null,
        denThangNo: this.form.checkDenThang ? this.form.denThang : null,
        tuNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.tuNgay).format("DD/MM/YYYY")
            : null,
        denNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.denNgay).format("DD/MM/YYYY")
            : null,
        kyHoaDon: this.kyCuoc,
        ghiChu: this.form.ghiChu,
        buocXLNId: this.form.checkBuocXL ? this.form.idBuocXL : null,
        trangThaiId: this.form.idKieuXuLy == "2" ? this.form.idTrangThai : null,
        kieuXLId: this.form.idKieuXuLy,
        lanConId: null,
        trangThaiXLNId:
          this.form.idKieuXuLy == "2" ? this.form.idKetQuaXL : null,
        namNo: this.form.checkNamNo ? this.form.idNamNo : null,
        xuLyNo: dsXLN,
      };
      this.loading(true);
      try {
        const response = await API.taoDuLieuXuLyNo(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(response.data.message);
          this.fetchLanTao();
        } else {
          this.$root.toastError("Lỗi. Bạn hãy kiểm tra lại!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKHByThanhToan() {
      const data = {
        kyCuoc: this.kyCuoc, //20210301
        donViId: this.form.idDonVi, //null
        kieuNo: this.form.idKieuNo, //1
        dsLoaiKH: this.form.checkLoaiKH ? this.form.idLoaiKH : null,
        tuDau1: this.form.checkTuTien ? this.form.selectedTuTien : null,
        tuTien: this.form.checkTuTien ? this.form.tuTien.toString().replaceAll(",", "") : null,
        denDau1: this.form.checkDenTien ? this.form.selectedDenTien : null,
        denTien: this.form.checkDenTien ? this.form.denTien.toString().replaceAll(",", "") : null,
        tuDau2: this.form.checkTuThang ? this.form.selectedTuThang : null,
        tuThangNo: this.form.checkTuThang ? this.form.tuThang : null,
        denDau2: this.form.checkDenThang ? this.form.selectedDenThang : null,
        denThangNo: this.form.checkDenThang ? this.form.denThang : null,
        chieuKhoa: this.form.checkChieuKhoa ? this.form.idChieuKhoa : null,
        tuNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.tuNgay).format("DD/MM/YYYY")
            : null, //null
        denNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.denNgay).format("DD/MM/YYYY")
            : null, //null
        dsMaTT: null,
        dsQuan: this.form.idQuan,
        nam: this.form.checkNamNo ? this.form.idNamNo : null,
        dsQuay: null,
        dsKhuVuc: null,
      };
      this.loading(true);
      try {
        const response = await API.getDsKhachHangByThanhToan(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsKhachHang = response.data.data.data;
        } else {
          this.dsKhachHang = [];
          this.$root.toastError(
            "Không tìm thấy dữ liệu. Bạn hãy kiểm tra lại!"
          );
        }
      } catch (error) {
        this.dsKhachHang = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKHByKhachHang() {
      const data = {
        kyCuoc: this.kyCuoc, //20210301
        donViId: this.form.idDonVi, //null
        kieuNo: this.form.idKieuNo, //1
        dsLoaiKH: this.form.checkLoaiKH ? this.form.idLoaiKH : null,
        tuDau1: this.form.checkTuTien ? this.form.selectedTuTien : null,
        tuTien: this.form.checkTuTien ? this.form.tuTien.toString().replaceAll(",", "") : null,
        denDau1: this.form.checkDenTien ? this.form.selectedDenTien : null,
        denTien: this.form.checkDenTien ? this.form.denTien.toString().replaceAll(",", "") : null,
        tuDau2: this.form.checkTuThang ? this.form.selectedTuThang : null,
        tuThangNo: this.form.checkTuThang ? this.form.tuThang : null,
        denDau2: this.form.checkDenThang ? this.form.selectedDenThang : null,
        denThangNo: this.form.checkDenThang ? this.form.denThang : null,
        chieuKhoa: this.form.checkChieuKhoa ? this.form.idChieuKhoa : null,
        tuNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.tuNgay).format("DD/MM/YYYY")
            : null, //null
        denNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.denNgay).format("DD/MM/YYYY")
            : null, //null
        dsMaTT: null,
        buocXLNId: this.form.checkBuocXL ? this.form.idBuocXL : null,
        trangThaiId: this.form.idKieuXuLy == "2" ? this.form.idTrangThai : null,
        lanTaoId: this.form.checkLanTao ? this.form.idLanTao : null,
        dsQuan: this.form.idQuan,
        trangThaiXLNId:
          this.form.idKieuXuLy == "2" ? this.form.idKetQuaXL : null,
        nam: this.form.checkNamNo ? this.form.idNamNo : null,
        nguoiGiaoId: this.form.checkNguoiGiao ? this.form.idNguoiGiao : null,
        dsNguoiNhanId: this.form.checkNguoiNhan ? this.form.idNguoiNhan : null,
        dsQuay: null,
        dsKhuVuc: null,
      };
      this.loading(true);
      try {
        const response = await API.getDsKhachHangByKhachHang(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKhachHang = response.data.data;
        } else {
          this.dsKhachHang = [];
          this.$root.toastError(
            "Không tìm thấy dữ liệu. Bạn hãy kiểm tra lại!"
          );
        }
      } catch (error) {
        this.dsKhachHang = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchKHByThueBao() {
      const data = {
        kyCuoc: this.kyCuoc, //20210301
        donViId: this.form.idDonVi, //null
        kieuNo: this.form.idKieuNo, //1
        dsLoaiKH: this.form.checkLoaiKH ? this.form.idLoaiKH : null,
        tuDau1: this.form.checkTuTien ? this.form.selectedTuTien : null,
        tuTien: this.form.checkTuTien ? this.form.tuTien.toString().replaceAll(",", "") : null,
        denDau1: this.form.checkDenTien ? this.form.selectedDenTien : null,
        denTien: this.form.checkDenTien ? this.form.denTien.toString().replaceAll(",", "") : null,
        tuDau2: this.form.checkTuThang ? this.form.selectedTuThang : null,
        tuThangNo: this.form.checkTuThang ? this.form.tuThang : null,
        denDau2: this.form.checkDenThang ? this.form.selectedDenThang : null,
        denThangNo: this.form.checkDenThang ? this.form.denThang : null,
        chieuKhoa: this.form.checkChieuKhoa ? this.form.idChieuKhoa : null,
        tuNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.tuNgay).format("DD/MM/YYYY")
            : null, //null
        denNgayKhoa:
          (this.form.idChieuKhoa == "1" || this.form.idChieuKhoa == "2") &&
            this.form.checkChieuKhoa
            ? moment(this.form.denNgay).format("DD/MM/YYYY")
            : null, //null
        dsMaTT: null,
        buocXLNId: null,
        trangThaiId: null,
        lanTaoId: null,
        dsQuan: this.form.idQuan,
        trangThaiXLNId: null,
        nam: this.form.checkNamNo ? this.form.idNamNo : null,
        dsQuay: null,
        dsKhuVuc: null,
      };
      this.loading(true);
      try {
        const response = await API.getDsKhachHangByThueBao(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsKhachHang = response.data.data;
        } else {
          this.dsKhachHang = [];
          this.$root.toastError(
            "Không tìm thấy dữ liệu. Bạn hãy kiểm tra lại!"
          );
        }
      } catch (error) {
        this.dsKhachHang = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    lantao_selectedChanged(dataItem) {
      if (dataItem) {
        this.form.idLanTao = dataItem.LANTAO_ID;
      }
    },
    KH_selectedItemsChanged(data) {
      this.dsSelected = data;
    },
    KH_selectedRowChanged(index, data) {
      if (data) {
        this.objKhachHang = data;
        this.selectedXLN = data.MA_XLN;
      }
    },
    async KH_onRowDoubleClicked(index, data) {
      this.objKhachHang = await data;
      this.$bvModal.show("modal-chitietnomotthuebao");
    },
    dotSoLieu_selectedRowChanged(data) {
      if (data) {
        this.objLanTao = data;
      }
    },
    async dotSoLieu_onRowDoubleClicked(index, data) {
      this.objLanTao = await data;
      this.$bvModal.show("modal-chitietlantaodulieu");
    },
    async sendData(data) {
      this.selectedXLN = await data.MA_XLN;
      await this.$bvModal.show("modal-lichsuno");
    },
    async fetchXoaLanTao(id) {
      this.loading(true);
      try {
        const response = await API.xoaLanTaoById(this.axios, id);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa lần tạo thành công!");
          this.fetchLanTao();
        } else {
          this.$root.toastError("Lỗi. Bạn hãy kiểm tra lại!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    TaoDS() {
      if (this.form.checkLoaiKH && this.form.idLoaiKH.length <= 0) {
        this.$root.toastError("Bạn chưa chọn Loại KH!");
        return;
      }
      if (this.form.checkNguoiGiao && this.form.idNguoiGiao == "") {
        this.$root.toastError("Bạn chưa chọn người giao!");
        return;
      }
      if (this.form.checkNguoiNhan && this.form.idNguoiNhan.length == 0) {
        this.$root.toastError("Bạn chưa chọn người nhận!");
        return;
      }
      if (moment(this.form.denNgay).diff(this.form.tuNgay, "DD/MM/YYYY") < 0) {
        this.$root.toastError("Từ ngày phải nhỏ hơn hoặc bằng Đến ngày!");
        return;
      }
      const type = this.form.idKieuXuLy;
      if (type == "1") {
        this.fetchKHByThanhToan();
      } else if (type == "2") {
        this.fetchKHByKhachHang();
      } else {
        this.fetchKHByThueBao();
      }
      this.maTT = "";
    },
    CapNhat() {
      if (!this.form.idDonVi) {
        this.$root.toastError("Chưa chọn đơn vị quản lý!");
        return;
      }
      const dsSelected_KH = this.$refs.danhsachKH.getSelectedRecords() || [];
      if (dsSelected_KH.length <= 0) {
        this.$root.toastError("Chưa chọn khách hàng xử lý nợ!");
        return;
      }
      this.fetchTaoXLN();
    },
    DocFile() {
      if (this.form.idKieuXuLy == '3') {
        this.$bvModal.show("modal-maTBdocfile");
      } else {
        this.$bvModal.show("modal-maTTdocfile");
      }
    },
    XuatFile() {
      if (this.dsKhachHang.length <= 0) {
        this.$root.toastError("Chưa có danh sách để xuất Excel!");
        return;
      }
      this.loading(true);
      try {
        var dsach = xlsx.utils.json_to_sheet(this.dsKhachHang);
        var wb = xlsx.utils.book_new(); // make Workbook of Excel
        xlsx.utils.book_append_sheet(wb, dsach, "DS"); // sheetAName is name of Worksheet
        xlsx.writeFile(wb, "mau.xlsx");
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    XuatFile2() {
      this.$bvModal.show("modal-xuat-dulieu");
    },
    Xoa() {
      if (this.objLanTao == null) {
        this.$root.toastError("Chưa chọn dữ liệu!");
        return;
      }
      if (this.objLanTao.TRANGTHAI_XLN_ID == "2") {
        this.$confirm(
          `Lần tạo này đang xử lý, bạn không thể xóa!`,
          "Thông báo",
          {
            confirmButtonText: "Đồng ý",
          }
        )
          .then(() => { })
          .catch(() => { });
      } else {
        this.$confirm(
          `Bạn thật sự muốn xóa lần tạo dữ liệu ${this.objLanTao.LANTAO_ID} không?`,
          "Thông báo",
          {
            confirmButtonText: "Đồng ý",
            cancelButtonText: "Hủy",
          }
        )
          .then(() => {
            this.fetchXoaLanTao(this.objLanTao.LANTAO_ID);
          })
          .catch(() => { });
      }
    },
    LSNo() {
      if (this.selectedXLN == "") {
        this.$root.toastError("Chưa chọn khách hàng!");
        return;
      }
      this.$bvModal.show("modal-lichsuno");
    },
    ChiTietNo() {
      const dsSelected_KH = this.$refs.danhsachKH.getSelectedRecords() || [];
      if (dsSelected_KH.length <= 0) {
        this.$root.toastError("Chưa chọn khách hàng xử lý nợ!");
        return;
      }
      this.$bvModal.show("modal-chitietnonhieuthuebao");
    },
    chapNhan(data) {
      this.fetchDanhSachKH(data)
    },
    async fetchKyCuoc() {
      const response = await APICommon.getKyCuocHienHanh(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.kyCuoc = '' + response.data.data[0].KYCUOC;
        this.kyCuocView = moment(response.data.data[0].KYCUOC, "YYYYMMDD").toDate()
      }
    },
  },
  computed: {
    ...mapState("qltnCommon", [
      "danhSachLoaiKH",
      "danhMucQuanHuyen",
    ]),
    dsThuocTinh() {
      return this.fieldsKH.map((x) => x.fieldName);
    },
  },
};
</script>
