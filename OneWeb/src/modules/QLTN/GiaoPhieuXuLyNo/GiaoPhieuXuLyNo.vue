<template src="./GiaoPhieuXuLyNo.html"></template>
<style src="./GiaoPhieuXuLyNo.scss"></style>
<script>
import Vue from "vue";
import { GridPlugin, Page, Edit } from "@syncfusion/ej2-vue-grids";
import Calculator from "@/components/Calculator";
import breadcrumb from "@/components/breadcrumb";
import xlsx from "xlsx";
import exportExcelCustom from "./utils/ExportExcel";
import {
  LoaiNhanVienXuLy,
  KieuNo,
  TrangThai,
  CharacterLogic,
  CharacterLogic2,
  TrangThaiHienTaiDaGiao,
  PhieuHenDaGan,
  ChieuKhoa,
  CBONhanVienXLN,
  CBOLanTaoDL,
  CollumnsPhieuChuaGiao,
  CollumnsDotLatSoLieu,
  CollumnsPhieuHenTra,
  CollumnsPhieuDaTra,
  ExcelImport,
} from "./common";
import {
  Loai_NV,
  QUYEN,
  KIEUNO,
  TRANGTHAI_XLN,
  KETQUA_XLN,
} from "../../../utils/Enum";
import moment from "moment";
import API from "../api/GiaoPhieuXuLyNo";
import KyCuoc from "@/components/KyCuoc";
import ComboboxGrid from "../components/ComboboxGrid";
import EventBus from "@/utils/eventBus";
import $ from "jquery";
import HoSoGoc from "./components/HoSoGoc/HoSoGoc.vue";
import InHoaDon from "../InHoaDon/index.vue";
import DanhSachThueBaoXLN from "./components/DanhSachThueBaoXLN/DanhSachThueBaoXLN.vue";
import InThongBao from "./components/InThongBao/InThongBao.vue";
//import frmLsNo from "../TaoDuLieuXuLyNo/components/frmLsNo.vue";
import frmLsNo from "./components/LichSuNo/LSNo.vue";
import ThuHoiVatTu from "./components/ThuHoiVatTu/ThuHoiVatTu.vue";
import DSLoiGPXLN from "./components/DSLoiGPXLN/DSLoiGPXLN.vue";
import GanNhanVienPhapLy from "./components/GanNhanVienPhapLy/GanNhanVien.vue";
import GanNhanVienTN from "./components/GanNhanVienThuNo/GanNhanVien.vue";

Vue.use(GridPlugin);
export default {
  components: {
    breadcrumb,
    appKyCuoc: KyCuoc,
    appCombobox: ComboboxGrid,
    appHoSoGoc: HoSoGoc,
    appInHoaDon: InHoaDon,
    appDanhSachThueBaoXLN: DanhSachThueBaoXLN,
    appInThongBao: InThongBao,
    appLsNo: frmLsNo,
    appThuHoiVatTu: ThuHoiVatTu,
    Calculator: Calculator,
    appDSLoiGPXLN: DSLoiGPXLN,
    appGanNhanVienPhapLy: GanNhanVienPhapLy,
    appGanNhanVienTN: GanNhanVienTN,
  },
  provide: {
    grid: [Page, Edit],
  },
  data() {
    return {
      childItemTreeSeleted: "",
      rowSelectedDefault: NaN,
      kyCuoc: "",
      cboLoaiNVXL: LoaiNhanVienXuLy,
      cboLoaiNVXLValue: "39",
      cboNhanVienXLN: [],
      cboNhanVienXLNFields: CBONhanVienXLN,
      cboNhanVienXLNValue: "",
      cboNhanVienXLNEnabled: false,
      cboLanTaoDuLieu: [],
      cboLanTaoDuLieuFields: CBOLanTaoDL,
      cboLanTaoDuLieuValue: "",
      chkNhanVienXLN: false,
      chkLanTao: false,
      kt_load: true,
      loainv_xl_id: null,
      nhanvienxln_id: 0,
      vkieuxl_id: null,
      dtNhanVienXLN: [],

      txtSoLuong: "",
      txtMaTB: "",
      tvXuLyNo: [],
      buocxln_cha_id: 0,
      dtQuyTrinhXLN: [],
      kt_thuoctinh: false,
      vbuocxln_ht_id: null,
      vbuocxln_id_luu: null,
      ds_nvtc_id: "",
      ds_nhanvien_id: "",
      phieu_id: null,
      sophieu: null,
      donviqlno_id: null,
      Ngay_BD_Gach: "",
      Ngay_KT_Gach: "",
      so_seri: 0,
      vtotruong: 0, //1: là tổ trưởng, 0: là nhân viên thường
      thanhtoan_id: 0,
      tsbtnGanNVVisibled: false,
      tsbtnGiaoPhieuVisibled: false,
      tsbtnHuyGiaoVisibled: false,
      tsbtnInVisibled: false,
      tstbnKetThucVisibled: false,
      tsbtnCapNhatVisibled: false,
      tsbtnChuyenBuocVisibled: false,
      tsbtnHuyCNVisibled: false,
      tsbtnExcelVisibled: false,
      tsbtnSuaDCVisibled: false,
      tsbtnInHDVisibled: false,
      tsbtnSuaTienVisibled: false,
      tsbtnHoSoGocVisibled: false,
      tsbtnLichSuNoVisibled: false,
      tsbtnDSThueBaoVisibled: false,
      tabSelected: "tabPhieuChuaGiao",
      ktDocFile: false,
      dtThuocTinh: [],
      dsControl: [],
      dsControlProp: {},
      ds_thtbi: [],
      giatri: [],
      //PhieuChuaGiao
      cboDonviQL: [],
      cboDonviQLValue: "",
      dtpNgayGiao: new Date(),
      chkHanTra: false,
      dtpHantra: new Date(),
      chkBuocKhac: false,
      cboBuocKhac: [],
      cboBuocKhacValue: "",
      cboTrangThai: [],
      cboTrangThaiValue: "",
      txtLanBXL: "1",
      txtLanBXLEnabled: false,
      chkLayChuyenBuoc: false,
      cboBuocChuyen: [],
      cboBuocChuyenValue: "",
      chkTuNgayChuyen: false,
      dtpChuyenTuNgay: new Date(),
      dtpChuyenDenNgay: new Date(),
      cboTrangThaiHT: [],
      cboTrangThaiHTValue: "",
      chkLoaiKH: false,
      cboLoaiKH: [],
      cboLoaiKHValue: "",
      cboKieuNo: [],
      cboKieuNoValue: "",
      txtSoLanXL: "",
      txtSoLanXLEnabled: false,
      chkTutien: false,
      cboTudau: CharacterLogic,
      cboTudauValue: ">",
      txtTutien: "0",
      chkDentien: false,
      cboDendau: CharacterLogic2,
      cboDendauValue: "<",
      txtDentien: "0",
      chkChieuKhoa: false,
      cboChieuKhoa: ChieuKhoa,
      cboChieuKhoaValue: "2",
      chkTuThang: false,
      cboTudau_TH: CharacterLogic,
      cboTudau_THValue: ">",
      txtTuThang: "",
      dtpTuNgay: new Date(),
      chkDenThang: false,
      cboDendau_TH: CharacterLogic2,
      cboDendau_THValue: "<",
      txtDenThang: "",
      dtpDenNgay: new Date(),
      txtGhiChu: "",
      gridPhieuChuaGiao: [],
      gridPhieuChuaGiaoSelected: [],
      gridPhieuChuaGiaoSelectedRow: [],
      collumnPhieuChuaGiao: CollumnsPhieuChuaGiao,
      gridDotLaySoLieu: [],
      collumnDotLaySoLieu: CollumnsDotLatSoLieu,
      //
      //Phieu hen tra
      chkLuotGiaoHT: false,
      cboLuotGiaoPhieuHenTra: [],
      cboLuotGiaoPhieuHenTraValue: "",
      chkNgayHenTra: false,
      dtpNgayHenTra: new Date(),
      gridPhieuHenTra: [],
      gridPhieuHenTraSelected: [],
      gridPhieuHenTraSelectedRow: [],
      collumnPhieuHenTra: CollumnsPhieuHenTra,
      //
      //Phieu da giao
      chkTuNgayGiao: false,
      dtpTuNgayGiao: new Date(),
      dtpDenNgayGiao: new Date(),
      chkLuotGiao: false,
      cboLuotGiaoPhieuDaGiao: [],
      cboLuotGiaoPhieuDaGiaoValue: "",
      chkLoaiKHDG: false,
      cboLoaiKHDG: [],
      cboLoaiKHDGValue: "",
      cboKieuNoDG: KieuNo,
      cboKieuNoDGValue: "1",
      cboTrangThaiXLDG: TrangThaiHienTaiDaGiao,
      cboTrangThaiXLDGValue: "0",
      chkChuyenBuoc: false,
      cboChuyenBuoc: [],
      cboChuyenBuocValue: "",
      cboDVQLDG: [],
      cboDVQLDGValue: "",
      chkPhieuHenDG: false,
      cboPhieuHenDG: PhieuHenDaGan,
      cboPhieuHenDGValue: "",
      chkTutienDG: false,
      cboTudauDG: CharacterLogic,
      cboTudauDGValue: ">",
      txtTutienDG: "0",
      chkTuThangDG: false,
      cboTudau_THDG: CharacterLogic,
      cboTudau_THDGValue: ">",
      txtTuThangDG: "0",
      chkDentienDG: false,
      cboDendauDG: CharacterLogic2,
      cboDendauDGValue: "<",
      txtDentienDG: "0",
      chkDenThangDG: false,
      cboDendau_THDG: CharacterLogic2,
      cboDendau_THDGValue: "<",
      txtDenThangDG: "0",

      cboKetQuaXL: [],
      cboKetQuaXLValue: "",
      cboLyDoXL: [],
      cboLyDoXLValue: "",
      dtpNgayXLDG: new Date(),
      dtpNgayHenDG: new Date(),
      dtpNgayHenDGEnabled: false,
      txtSeri: "",
      txtQuyen: "",
      txtSoSeri: NaN,
      txtGhiChuXLN: "",

      collumnPhieuDaGiao: CollumnsPhieuDaTra,

      btnExcelEnabled: true,
      btnExcelVisibled: true,
      txtDiaChiChange: "",
      gridPhieuDaGiao: [],
      gridPhieuDaGiaoSelected: [],
      gridPhieuDaGiaoSelectedRow: null,

      //
      //list top action
      tsbtnCapNhatText: "Cập nhật",
      tsbtnSuaTienEnabled: true,
      tabPPhieuDaGiao: "tabPhieuDaGiao",
      tabPPhieuHenTra: "tabPhieuHenTra",
      tabPPhieuChuaGiao: "tabPhieuChuaGiao",
      //
      selectedXLN: "",
      sumTemplate: function () {
        return {
          template: Vue.component("sumTemplate", {
            template: `<span>Sum: {{data.Sum}}</span>`,
            data: function () {
              return { data: { data: {} } };
            },
          }),
        };
      },
      aggregatesColumns: [
        {
          field: "SOLUONG",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "SL_CHUAGIAO",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "SL_CHUAXL",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "SL_DAXL",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
        {
          field: "SL_KETTHUC",
          type: "Sum",
          format: "N0",
          footerTemplate: this.sumTemplate,
        },
      ],
    };
  },
  watch: {
    async cboLoaiNVXLValue() {
      this.funcFollowcboLoaiNVXLValue();
    },
    async cboLanTaoDuLieuValue() {
      this.funcCboLanTaoDuLieuChanged();
    },
    async chkLanTao() {
      let in_lantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          in_lantao_id = this.cboLanTaoDuLieuValue;
          let itemDTSelect = this.cboLanTaoDuLieu.filter(
            (item) => item.LANTAO_ID == this.cboLanTaoDuLieuValue
          )[0];
          if (itemDTSelect != null)
            this.txtSoLuong = itemDTSelect == null ? "" : itemDTSelect.SOLUONG;
        } else {
          if (this.tabSelected == this.tabPPhieuDaGiao)
            this.cboLuotGiaoPhieuDaGiao = [];
          if (this.tabSelected == this.tabPPhieuHenTra)
            this.cboLuotGiaoPhieuDaGiao = [];
          return;
        }
      } else {
        this.txtSoLuong = "";
      }
      this.funcCboLanTaoDuLieuChanged();
    },
    dtpTuNgayGiao: async function (vl, vk) {
      if (!this.kt_load) {
        try {
          let in_lantao_id = null;
          if (this.chkLanTao) {
            if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
              in_lantao_id = this.cboLanTaoDuLieuValue;
            }
          }
          if (!this.chkNhanVienXLN) {
            this.HienThiLuotGiao(null, in_lantao_id);
          } else {
            this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
          }
        } catch (error) {
          console.log(error);
        }
      }
    },
    async cboNhanVienXLNValue() {
      if (this.cboNhanVienXLNValue != null) {
        if (this.chkNhanVienXLN) {
          this.nhanvienxln_id = this.cboNhanVienXLNValue;
          let vlantao_id = null;
          if (this.chkLanTao) {
            if (this.cboLanTaoDuLieuValue != null) {
              this.vlantao_id = this.cboLanTaoDuLieuValue;
            }
          }
          this.HienThiLuotGiao(this.nhanvienxln_id, vlantao_id);
        }
      }
    },
    chkBuocKhac() {
      if (this.cboTrangThaiValue == "1") {
        if (this.chkBuocKhac) {
          this.txtLanBXLEnabled = true;
        } else {
          this.txtLanBXLEnabled = false;
        }
      } else {
        this.txtLanBXLEnabled = false;
      }
    },
    cboTrangThaiHTValue() {
      if (this.cboTrangThaiHTValue == "1") {
        this.txtSoLanXLEnabled = true;
      } else this.txtSoLanXLEnabled = false;
    },
    async cboKetQuaXLValue() {
      try {
        console.log(this.cboKetQuaXLValue);
        this.dtpNgayHenDGEnabled = false;
        this.dtpNgayHenDGEnabled =
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_TUTHANHTOAN;
        this.chkNgayHenTra =
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_TUTHANHTOAN;
        this.chkNgayHenTra =
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_TUTHANHTOAN;

        this.btnExcelEnabled =
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHONGTHUDUOC;
        this.btnExcelVisibled =
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHONGTHUDUOC;
        //Nếu là hẹn trả thì nối nút gạch nợ lên
        if (
          this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_HENTRA ||
          this.cboKetQuaXLValue == TRANGTHAI_XLN.DAXULY ||
          this.cboKetQuaXLValue == TRANGTHAI_XLN.HENTRA_CHUA_TT ||
          this.cboKetQuaXLValue == TRANGTHAI_XLN.XULYNO_HOIDONG
        ) {
          if (
            this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_HENTRA ||
            this.cboKetQuaXLValue == TRANGTHAI_XLN.HENTRA_CHUA_TT
          ) {
            this.dtpNgayHenDGEnabled = true;
          }
          if (this.vkieuxl_id == 1) this.tsbtnCapNhatText = "Cập nhật - GN";
          this.tsbtnSuaTienEnabled = true;
        } else {
          this.tsbtnCapNhatText = "Cập nhật";
          this.tsbtnSuaTienEnabled = false;
        }

        try {
          this.loading(true);
          let data = {
            pTrangThaiXLNId: this.cboKetQuaXLValue,
          };
          let response = await API.getLyDoXuLyDaGiao(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data.length > 0) {
              this.cboLyDoXL = response.data.data;
              this.cboLyDoXLValue = this.cboLyDoXL[0].NOIDUNG_XLN_ID;
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.response.data.message_detail);
        } finally {
          this.loading(false);
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async cboTrangThaiXLDGValue() {
      if (this.isNullEmpty(this.cboTrangThaiXLDGValue)) return;
      this.HienThiDanhSachPhieuDaGiao(); //minhnt sửa: chuyển dùng backgroundworker 16/01/2015
      this.tsbtnHuyGiaoVisibled = this.cboTrangThaiXLDGValue == "0";
      this.tsbtnHuyCNVisibled = this.cboTrangThaiXLDGValue == "1";
      this.tsbtnCapNhatVisibled = this.cboTrangThaiXLDGValue == "0";
      this.tsbtnSuaTienVisibled = this.cboTrangThaiXLDGValue == "0";
      this.tsbtnChuyenBuocVisibled = this.cboTrangThaiXLDGValue == "1";
      if (this.tabSelected == "tabPhieuChuaGiao") {
        this.tstbnKetThucVisibled = true;
      }
      if (this.tabSelected == "tabPhieuDaGiao") {
        this.tstbnKetThucVisibled = this.cboTrangThaiXLDGValue == "1";
      }
    },
    chkNhanVienXLN() {
      let in_lantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          in_lantao_id = this.cboLanTaoDuLieuValue;
        }
      }
      if (!this.chkNhanVienXLN) {
        this.HienThiLuotGiao(null, in_lantao_id);
      } else {
        this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
      }
    },
    cboTrangThaiValue() {
      if (this.cboTrangThaiValue == "1") {
        if (this.chkBuocKhac) this.txtLanBXLEnabled = true;
        else this.txtLanBXLEnabled = false;
      } else this.txtLanBXLEnabled = false;
    },
    chkLayChuyenBuoc() {
      if (this.chkLayChuyenBuoc) {
      } else {
        this.chkTuNgayChuyen = false;
      }
    },
    async chkTuNgayGiao() {
      let in_lantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          in_lantao_id = this.cboLanTaoDuLieuValue;
        }
      }

      if (!this.chkNhanVienXLN) {
        this.HienThiLuotGiao(null, in_lantao_id);
      } else {
        this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
      }
    },
    dtpDenNgayGiao() {
      if (!this.kt_load) {
        try {
          let in_lantao_id = null;
          if (this.chkLanTao) {
            if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
              in_lantao_id = this.cboLanTaoDuLieuValue;
            }
          }
          if (!this.chkNhanVienXLN) {
            this.HienThiLuotGiao(null, in_lantao_id);
          } else {
            this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
          }
        } catch (error) {
          console.log(error);
        }
      }
    },
  },
  methods: {
    init: async function () {
      $(".tree-plus a").click(function (e) {
        if (e.target !== this) return;
        $(this).parent().toggleClass("open");
      });
    },
    formatDate(value) {
      return moment(value).format("DD/MM/yyyy");
    },
    onEnterTXTMaTB: async function () {
      if (this.isNullEmpty(this.txtMaTB)) {
        this.$toast.error("Hãy nhập mã xử lý nợ để tìm kiếm!");
        return;
      } else {
        try {
          if (this.tabSelected == this.tabPPhieuDaGiao) {
            if (this.isNullEmpty(this.kyCuoc)) {
              this.$toast.error("Hãy chọn kỳ hóa đơn!");
              return;
            }
            let vkieuxl_id = 1;
            let response = await API.getKieuXuLyId(this.axios, {
              pBuocXLNHTId: this.vbuocxln_ht_id,
            });
            let _kieuxl_id = response.data.data[0].KIEUXL_ID;
            if (_kieuxl_id != "-1") {
              vkieuxl_id = _kieuxl_id;
            }
            this.gridPhieuDaGiao = [];
            try {
              this.loading(true);
              let data = {
                pKyCuoc: this.kyCuoc,
                pKieuXLId: vkieuxl_id,
                pBuocXLNId: this.vbuocxln_ht_id,
                pMaXLN: this.txtMaTB,
              };
              let response = await API.getDSPhieuDaGiaoTheoMaXLN(
                this.axios,
                data
              );
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
              ) {
                if (response.data.data.length > 0) {
                  this.gridPhieuDaGiao = response.data.data;
                }
              }
            } catch (error) {
              console.log(error);
              this.$toast.error(error.response.data.message_detail);
            } finally {
              this.loading(false);
            }

            response = await API.getDSThuocTinhBBXLN(this.axios, {});
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
            ) {
              this.dtThuocTinh = response.data.data;
            }
          }
        } catch (error) {}
      }
    },
    isNullEmpty(value) {
      return value === null || value === "";
    },
    async funcFollowcboLoaiNVXLValue() {
      try {
        if (this.cboLoaiNVXLValue >= 0) {
          if (this.isNullEmpty(this.cboLoaiNVXLValue)) return;
          if (!this.kt_load) {
            if (this.cboLoaiNVXLValue == Loai_NV.NHANVIEN_PHAPLY) {
              this.loainv_xl_id = this.cboLoaiNVXLValue;
            } else {
              if (this.vkieuxl_id != 1) {
                this.loainv_xl_id = this.cboLoaiNVXLValue;
              } else {
                this.loainv_xl_id = Loai_NV.NHANVIEN_TC;
              }
            }
            try {
              this.loading(true);
              let data = {
                pLoaiNVXLId: this.loainv_xl_id,
              };
              let response = await API.getNhanVienXLN(this.axios, data);
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
              ) {
                this.dtNhanVienXLN = response.data.data;
                this.cboNhanVienXLN = this.dtNhanVienXLN.filter(
                  (item) => item.LOAINV_ID == this.loainv_xl_id
                );
              }
            } catch (error) {
              console.log(error);
              this.$toast.error(error.response.data.message_detail);
            } finally {
              this.loading(false);
            }
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    showKieuNoDG() {
      return this.chkDentienDG || this.chkTutienDG;
    },

    async funcDSNhanVienXuLy(loaiNVXLNId) {
      try {
        let data = {
          pDSLoaiNVXLId: loaiNVXLNId,
        };
        let res = [];
        let response = await API.getDSNhanVienXLN(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
      }
    },
    chukycuocChangeHandler(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    HT_COMBO_KIEUNO() {
      this.cboKieuNo = KieuNo;
      this.cboKieuNoValue = 1;
    },
    async HT_COMBO_LOAI_KH() {
      try {
        let res = [];
        let response = await API.getDSLoaiKhachHang(this.axios);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
      }
    },
    async HT_COMBO_DONVI(nhanVienId) {
      try {
        let data = {
          // pNhanVienId: nhanVienId, //donViDLId,
        };
        let res = [];
        let response = await API.getDSDonViQuanLy(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    HT_COMBO_TRANGTHAI() {
      this.cboTrangThai = TrangThai;
      this.cboTrangThaiValue = TrangThai[1].TRANGTHAI_ID;
      this.cboTrangThaiHT = TrangThai;
      this.cboTrangThaiHTValue = TrangThai[0].TRANGTHAI_ID;
    },
    async getQuyTrinhNguoiDung() {
      try {
        let data = {
          pTenBang: "QUYTRINH_XLN",
          pBuocXLNChaId: this.buocxln_cha_id,
        };
        let res = [];
        let response = await API.getDSQuyTrinhNguoiDung(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
      }
    },
    async funcDSDonViQLTheoQuayThu(donViId) {
      try {
        let data = {
          pDonViId: donViId,
        };
        let res = [];
        let response = await API.getDonViQLTheoQuayThu(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async funcGetThongTinChuKyNo(kyCuoc) {
      try {
        if (this.kyCuoc == undefined || this.isNullEmpty(this.kyCuoc)) {
          this.kyCuoc = this.kyCuocHienHanhLocal;
        }
        let res = [];
        let response = await API.getThongTinChuKyNo(this.axios, {
          pKyCuoc: this.kyCuoc,
        });
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          if (response.data.data.length > 0) {
            response.data.data.forEach((item) => {
              res.push(item);
            });
          }
        }
        return res;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async selectedTab(key) {
      this.tsbtnGanNVVisibled = false;
      this.tsbtnGiaoPhieuVisibled = false;
      this.tsbtnHuyGiaoVisibled = false;
      this.tsbtnInVisibled = false;
      this.tstbnKetThucVisibled = false;
      this.tsbtnCapNhatVisibled = false;
      this.tsbtnChuyenBuocVisibled = false;
      this.tsbtnHuyCNVisibled = false;
      this.tsbtnExcelVisibled = false;
      this.tsbtnSuaDCVisibled = false;
      this.tsbtnInHDVisibled = false;
      this.tsbtnSuaTienVisibled = false;
      this.tsbtnHoSoGocVisibled = false;
      this.tsbtnLichSuNoVisibled = false;
      this.tsbtnDSThueBaoVisibled = false;
      switch (key) {
        case "tabPhieuHenTra":
          this.tabSelected = "tabPhieuHenTra";
          this.tsbtnInVisibled = true;
          this.tsbtnExcelVisibled = true;
          this.tsbtnInHDVisibled = false;
          this.gridPhieuHenTra = [];
          this.tsbtnLichSuNoVisibled = true;
          // this.tsbtnDSThueBaoVisibled = true;
          break;
        case "tabPhieuDaGiao":
          this.tabSelected = "tabPhieuDaGiao";
          this.tsbtnHuyGiaoVisibled = true;
          this.tsbtnInVisibled = true;
          this.tstbnKetThucVisibled = true;
          this.tsbtnChuyenBuocVisibled = true;
          this.tsbtnHuyCNVisibled = true;
          this.tsbtnCapNhatVisibled = true;
          this.tsbtnExcelVisibled = true;
          this.tsbtnSuaDCVisibled = true;
          this.tsbtnInHDVisibled = true;
          this.tsbtnSuaTienVisibled = true;
          this.tsbtnHoSoGocVisibled = true;
          this.tsbtnDSThueBaoVisibled = true;
          this.tsbtnLichSuNoVisibled = true;
          this.gridPhieuDaGiao = [];
          if (!this.vbuocxln_ht_id)
            return this.$toast.error("Vui lòng chọn trình tự xử lý nợ!");
          if (!this.chkNhanVienXLN) {
            let vlantao_id = null;
            if (this.chkLanTao) {
              if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
                vlantao_id = this.cboLanTaoDuLieuValue;
              }
            }
            if (!this.kt_load) {
              this.HienThiLuotGiao(null, vlantao_id);
            }
          } else {
            let vlantao_id = null;
            if (this.chkLanTao) {
              if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
                vlantao_id = this.cboLanTaoDuLieuValue;
              }
            }
            if (!this.kt_load) {
              this.HienThiLuotGiao(this.cboNhanVienXLNValue, vlantao_id);
            }
          }
          this.cboTrangThaiXLDGValue = 0;
          this.cboKieuNoDGValue = 0;
          let response = await API.getKieuXuLyId(this.axios, {
            pBuocXLNHTId: this.vbuocxln_ht_id,
          });
          let _kieuxl_id = response.data.data[0].KIEUXL_ID;
          if (_kieuxl_id == "-1") {
            this.$toast.error(
              "Bước xử lý nợ chưa được gán kiểu xử lý. Liên hệ Admin để xử lý!"
            );
            return;
          }
          this.vkieuxl_id = _kieuxl_id;
          this.HT_COMBO_CHUYENBUOC_XLN(this.vkieuxl_id, this.vbuocxln_ht_id);
          break;
        case "tabPhieuChuaGiao":
          this.tabSelected = "tabPhieuChuaGiao";
          this.tsbtnGanNVVisibled = true;
          this.tsbtnGiaoPhieuVisibled = true;
          this.tstbnKetThucVisibled = false;
          this.tsbtnExcelVisibled = true;
          this.tsbtnLichSuNoVisibled = true;
          this.tsbtnDSThueBaoVisibled = false;
          this.gridPhieuChuaGiao = [];
          this.gridDotLaySoLieu = [];
          this.funcCboLanTaoDuLieuChanged();
          break;
      }
    },
    async funcKiemTraQuyenNguoiDung(QuyenId) {
      try {
        let data = {
          pQuyenId: QuyenId,
        };
        let response = await API.getQuyenNguoiDung(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          return response.data.data;
        }
        return null;
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },

    async tvFocusChanged(value) {
      if (value != null) {
        this.childItemTreeSeleted = value.BUOCXLN_ID;
        this.vbuocxln_ht_id = value.BUOCXLN_ID;
        console.log(this.vbuocxln_ht_id);
        if (this.vbuocxln_id_luu == 1) {
          this.vbuocxln_id_luu = this.vbuocxln_ht_id;
        } else if (this.vbuocxln_id_luu != this.vbuocxln_ht_id) {
          this.Clear_Grid();
        }
        let data = {
          pBuocXLNHTId: this.vbuocxln_ht_id,
        };
        let response = await API.getDSTrangThaiXuLyNo(this.axios, data);
        this.cboKetQuaXL = response.data.data;
        if (this.cboKetQuaXL.length > 0)
          this.cboKetQuaXLValue = this.cboKetQuaXL[0].TRANGTHAI_XLN_ID;

        response = await API.getKieuXuLyId(this.axios, {
          pBuocXLNHTId: this.vbuocxln_ht_id,
        });
        if (response.data.data.length <= 0) {
          this.$toast.error(
            "Bước xử lý nợ chưa được gán kiểu xử lý. Liên hệ Admin để xử lý!"
          );
          return;
        }

        this.vkieuxl_id = response.data.data[0].KIEUXL_ID;
        if (this.vkieuxl_id == 3) {
          this.tsbtnDSThueBaoVisibled = false;
        } else {
          if (this.tabSelected == this.tabPPhieuDaGiao)
            this.tsbtnDSThueBaoVisibled = true;
        }
        this.HT_LanTaoDL_CG(this.vkieuxl_id);
        this.HT_COMBO_BUOC_XLN(this.vkieuxl_id, this.vbuocxln_ht_id);
        this.HT_COMBO_BUOC_CHUYEN(this.vkieuxl_id, this.vbuocxln_ht_id);
        this.HT_COMBO_CHUYENBUOC_XLN(this.vkieuxl_id, this.vbuocxln_ht_id);
        this.LoadDynamicControls();
        if (value.LOAINV_ID != null) {
          this.loainv_xl_id = value.LOAINV_ID;
        } else {
          this.loainv_xl_id = null;
        }
        if (this.loainv_xl_id == Loai_NV.NHANVIEN_PHAPLY) {
          this.cboLoaiNVXLValue = "34";
          try {
            this.loading(true);
            let data = {
              pLoaiNVXLId: this.loainv_xl_id,
            };
            let response = await API.getNhanVienXLN(this.axios, data);
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
            ) {
              this.dtNhanVienXLN = response.data.data;
              this.cboNhanVienXLN = this.dtNhanVienXLN.filter(
                (item) => item.LOAINV_ID == this.loainv_xl_id
              );
            }
          } catch (error) {
            console.log(error);
            this.$toast.error(error.response.data.message_detail);
          } finally {
            this.loading(false);
          }
        } else {
          this.cboLoaiNVXLValue = "39";
          try {
            this.loading(true);
            let data = {
              pLoaiNVXLId: this.loainv_xl_id,
            };
            let response = await API.getNhanVienXLN(this.axios, data);
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
            ) {
              this.dtNhanVienXLN = response.data.data;
              this.cboNhanVienXLN = this.dtNhanVienXLN.filter(
                (item) => item.LOAINV_ID == this.loainv_xl_id
              );
            }
          } catch (error) {
            console.log(error);
            this.$toast.error(error.response.data.message_detail);
          } finally {
            this.loading(false);
          }
        }
        if (this.cboLoaiNVXLValue == Loai_NV.NHANVIEN_PHAPLY) {
          this.loainv_xl_id = this.cboLoaiNVXLValue;
        }
        // this.funcCboLanTaoDuLieuChanged();
      }
    },
    Clear_Grid() {
      this.gridPhieuChuaGiao = [];
      this.gridPhieuHenTra = [];
      this.gridPhieuDaGiao = [];
      this.gridDotLaySoLieu = [];
    },
    async HT_LanTaoDL_CG(kieuxl_id) {
      try {
        let data = {
          pKieuXuLyId: kieuxl_id,
        };
        let response = await API.getLanTaoXLN(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboLanTaoDuLieu = response.data.data;
          this.cboLanTaoDuLieuValue = this.cboLanTaoDuLieu[0].LANTAO_ID;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async HT_COMBO_BUOC_XLN(kieuxl_id, buocxln_id) {
      try {
        let data = {
          pKieuXuLyNoId: kieuxl_id,
          pBuocXuLyNoId: buocxln_id,
        };
        let response = await API.getDSBuocKhac(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboBuocKhac = response.data.data;
          if (this.cboBuocKhac.length > 0)
            this.cboBuocKhacValue = this.cboBuocKhac[0].BUOCXLN_ID;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async HT_COMBO_BUOC_CHUYEN(kieuxl_id, buocxln_id) {
      try {
        let data = {
          pKieuXuLyNoId: kieuxl_id,
          pBuocXuLyNoId: buocxln_id,
        };
        let response = await API.getDSBuocChuyenXLN(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboBuocChuyen = response.data.data;
          if (this.cboBuocChuyen.length > 0)
            this.cboBuocChuyenValue = this.cboBuocChuyen[0].BUOCXLN_ID;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async HT_COMBO_CHUYENBUOC_XLN(kieuxl_id, buocxln_id) {
      try {
        let data = {
          pKieuXuLyNoId: kieuxl_id,
          pBuocXuLyNoId: buocxln_id,
        };
        let response = await API.getDSChuyenBuocXLN(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.cboChuyenBuoc = response.data.data;
          if (this.cboChuyenBuoc.length > 0)
            this.cboChuyenBuocValue = this.cboChuyenBuoc[0].BUOCXLN_ID;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    async LoadDynamicControls() {
      try {
        let data = {
          pBuocXLNHTId: this.vbuocxln_ht_id,
        };
        let response = await API.getThuocTinhBB(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.dsControl = response.data.data;
          if (this.dsControl) {
            this.dsControl.forEach((e) => {
              if (e.LOAI_ID == 3) {
                this.dsControlProp[e.THUOCTINH_BB] = new Date();
              } else if (e.LOAI_ID == 2) {
                this.dsControlProp[e.THUOCTINH_BB] = "";
              }
            });
          }
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      }
    },
    nhanvienXLN_selectedChanged(value) {
      if (value != null) this.cboNhanVienXLNValue = value.NHANVIEN_ID;
    },
    LanTaoDuLieu_selectedChanged(value) {
      if (value != null) this.cboLanTaoDuLieuValue = value.LANTAO_ID;
    },
    async funcCboLanTaoDuLieuChanged() {
      switch (this.tabSelected) {
        case "tabPhieuChuaGiao":
          if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
            this.gridPhieuChuaGiao = [];
            let itemDTSelect = this.cboLanTaoDuLieu.filter(
              (item) => item.LANTAO_ID == this.cboLanTaoDuLieuValue
            )[0];
            if (itemDTSelect != null)
              this.txtSoLuong =
                itemDTSelect == null ? "" : itemDTSelect.SOLUONG;
            await this.HT_DotLaySoLieu();
          }
          break;
        case "tabPhieuHenTra":
          var in_lantao_id = null;
          if (this.chkLanTao) {
            if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
              in_lantao_id = this.cboLanTaoDuLieuValue;
            }
          }
          if (!this.chkNhanVienXLN) {
            await this.HienThiLuotGiao(null, in_lantao_id);
          } else {
            await this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
          }
          break;
        case "tabPhieuDaGiao":
          var in_lantao_id = null;
          if (this.chkLanTao) {
            if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
              in_lantao_id = this.cboLanTaoDuLieuValue;
            }
          }
          if (!this.chkNhanVienXLN) {
            await this.HienThiLuotGiao(null, in_lantao_id);
          } else {
            await this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
          }
          break;
      }
    },
    async HT_DotLaySoLieu() {
      let lantao_id = null;
      this.gridDotLaySoLieu = [];
      if (this.chkLanTao) {
        lantao_id = this.cboLanTaoDuLieuValue;
      }
      try {
        this.loading(true);
        let data = {
          pLoaiNVXLId: this.loainv_xl_id,
          pBuocXLNHTId: this.vbuocxln_ht_id,
          pKieuXLId: this.vkieuxl_id,
          pLanTaoId: lantao_id,
        };
        let response = await API.getDSDotLaySoLieu(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.gridDotLaySoLieu = response.data.data;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error("Lỗi đợt lấy số liệu: " + error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async HienThiLuotGiao(nhanvienxln_id, lantao_id) {
      try {
        if (!this.vbuocxln_ht_id)
          return this.$toast.error("Vui lòng chọn trình tự xử lý nợ!");
        this.gridPhieuDaGiao = [];
        let vtungay_giao = "",
          vdenngay_giao = "";
        if (this.chkTuNgayGiao) {
          vtungay_giao = moment(this.dtpTuNgayGiao).format("DD/MM/yyyy");
          vdenngay_giao = moment(this.dtpDenNgayGiao).format("DD/MM/yyyy");
        }
        if (this.vtotruong == 1) {
          let data = {
            pNhanVienGiaoId: this.$auth.getNhanVienID(),
            pNhanVienNhanId: nhanvienxln_id,
            pBuocXuLyNoId: this.vbuocxln_ht_id,
            pLanTaoId: lantao_id,
            pGiaoTuNgay: vtungay_giao,
            pGiaoDenNgay: vdenngay_giao,
          };
          let response = await API.getDSLuotGiaoXLN(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.cboLuotGiaoPhieuHenTra = response.data.data;
            this.cboLuotGiaoPhieuDaGiao = response.data.data;
            if (this.cboLuotGiaoPhieuHenTra.length > 0) {
              this.cboLuotGiaoPhieuHenTraValue =
                this.cboLuotGiaoPhieuHenTra[0].GIAOPHIEU_ID;
              this.cboLuotGiaoPhieuDaGiaoValue =
                this.cboLuotGiaoPhieuDaGiao[0].GIAOPHIEU_ID;
            }
          }
        } else {
          let data = {
            pNhanVienGiaoId: null,
            pNhanVienNhanId: this.$auth.getNhanVienID(),
            pBuocXuLyNoId: this.vbuocxln_ht_id,
            pLanTaoId: lantao_id,
            pGiaoTuNgay: vtungay_giao,
            pGiaoDenNgay: vdenngay_giao,
          };
          let response = await API.getDSLuotGiaoXLN(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.cboLuotGiaoPhieuHenTra = response.data.data;
            this.cboLuotGiaoPhieuDaGiao = response.data.data;
            if (this.cboLuotGiaoPhieuHenTra.length > 0) {
              this.cboLuotGiaoPhieuHenTraValue =
                this.cboLuotGiaoPhieuHenTra[0].GIAOPHIEU_ID;
              this.cboLuotGiaoPhieuDaGiaoValue =
                this.cboLuotGiaoPhieuDaGiao[0].GIAOPHIEU_ID;
            }
          }
        }
      } catch (error) {
        console.log(error);
        this.cboLuotGiaoPhieuHenTra = [];
        this.cboLuotGiaoPhieuDaGiao = [];
        this.$toast.error(error.data.message_detail);
      }
    },
    async HienThiDanhSachPhieuChuaGiao() {
      if (this.chkNhanVienXLN) {
        if (this.isNullEmpty(this.cboNhanVienXLNValue)) {
          this.$toast.error("Chưa chọn nhân viên xử lý nợ!");
          return;
        }
      } else {
        if (this.vtotruong == 0) {
          if (this.cboNhanVienXLN.length <= 0) {
            this.$toast.error(
              "Không có nhân viên xử lý, hãy liên hệ admin để gán nhân viên xử lý!"
            );
            return;
          }
        }
      }
      if (this.vbuocxln_ht_id == 1) {
        this.$toast.error("Chưa chọn bước xử lý nợ!");
        return;
      }
      if (this.isNullEmpty(this.kyCuoc)) {
        this.$toast.error("Không có chu kỳ tính cước ! Bạn hãy xem lại");
        return;
      }
      let vkieuno = this.cboKieuNoValue;
      let vloaikh_id;
      if (this.chkLoaiKH) {
        vloaikh_id = this.cboLoaiKHValue;
      } else {
        vloaikh_id = null;
      }
      let vlantao_id = null;
      if (this.chkLanTao) {
        vlantao_id = this.cboLanTaoDuLieuValue;
      }

      let vtudau1 = "";
      if (this.chkTutien) {
        vtudau1 = this.cboTudauValue;
      } else {
        vtudau1 = "";
      }
      let vdendau1 = "";
      if (this.chkDentien) {
        vdendau1 = this.cboDendauValue;
      } else {
        vdendau1 = "";
      }
      let vtutien = null;
      let vdentien = null;
      if (this.txtTutien != "" && this.txtTutien != null) {
        vtutien = this.txtTutien;
      }
      if (this.txtDentien != "" && this.txtDentien != null) {
        vdentien = this.txtDentien;
      }
      //thang
      let vtudau2 = "";
      if (this.chkTuThang) {
        vtudau2 = this.cboTudau_THValue;
      } else vtudau2 = "";
      let vdendau2 = "";
      if (this.chkDenThang) {
        vdendau2 = this.cboDendau_THValue;
      } else vdendau2 = "";

      let vtuthangno = null;
      if (this.txtTuThang) vtuthangno = this.txtTuThang;
      let vdenthangno = null;
      if (this.txtDenThang) vdenthangno = this.txtDenThang;
      let vdonvi_id = this.cboDonviQLValue;

      let vbuocxln_id = null;
      let vtrangthai_bxl = 2; //Tất cả
      if (this.chkBuocKhac) {
        vbuocxln_id = this.cboBuocKhacValue;
        vtrangthai_bxl = this.cboTrangThaiValue;
      }
      //Bước hiện tại
      // trạng thái hiện tại
      let vtrangthai_ht = this.cboTrangThaiHTValue;

      let vnhanvienxl_id = null;
      if (this.chkNhanVienXLN) {
        if (
          this.cboNhanVienXLNValue == null ||
          this.cboNhanVienXLNValue == ""
        ) {
          this.$toast.error("Bạn chưa chọn nhân viên xử lý nợ!");
          return;
        }
        vnhanvienxl_id = this.cboNhanVienXLNValue;
      }
      // else {
      //     if (this.cboNhanVienXLN != [] && this.cboNhanVienXLN != null) {
      //         this.cboNhanVienXLN.forEach((element, index) => {
      //             if (index == 0) {
      //                 vnhanvienxl_id = element.NHANVIEN_ID;
      //             } else {
      //                 vnhanvienxl_id += "," + element.NHANVIEN_ID;
      //             }
      //         });
      //     }
      // }
      let vtungay = "",
        vdenngay = "";
      if (this.chkChieuKhoa) {
        vtungay = this.formatDate(this.dtpTuNgay);
        vdenngay = this.formatDate(this.dtpDenNgay);
      }
      let vsolan_xl = null;
      if (vtrangthai_ht == 1) {
        if (this.txtSoLanXL == null || this.txtSoLanXL == "") {
          this.$toast.error("Chưa nhập số lần xử lý!");
          return;
        }
        vsolan_xl = this.txtSoLanXL;
      } else {
        vsolan_xl = null;
      }
      this.ds_nvtc_id = "";
      let vsolan_bxl = null;
      if (this.cboTrangThaiValue == "1") {
        if (this.isNullEmpty(this.txtLanBXL)) {
          this.$toast.error("Chưa nhập số lần bước xử lý!");
          return;
        }
        vsolan_bxl = this.txtLanBXL;
      }
      let vtungay_chuyen = "";
      let vdenngay_chuyen = "";
      let vbuocxln_id_next = null;
      if (this.chkLayChuyenBuoc) {
        vbuocxln_id_next = this.cboBuocChuyenValue;
        if (this.chkChuyenBuoc) {
          vtungay_chuyen = this.formatDate(this.dtpChuyenTuNgay);
          vdenngay_chuyen = this.formatDate(this.dtpChuyenDenNgay);
        } else {
          vtungay_chuyen = "";
          vdenngay_chuyen = "";
        }
      }
      this.gridPhieuChuaGiao = [];
      try {
        this.loading(true);
        let data = {
          pKyCuoc: this.kyCuoc,
          pKieuNo: vkieuno,
          pLoaiKHId: vloaikh_id,
          pLanTaoId: vlantao_id,
          pTuDau1: vtudau1,
          pTuTien: vtutien,
          pDenDau1: vdendau1,
          pDenTien: vdentien,
          pTuDau2: vtudau2,
          pTuThangNo: vtuthangno,
          pDenDau2: vdendau2,
          pDenThangNo: vdenthangno,
          pDonViId: vdonvi_id,
          pChieuKhoa: this.chkChieuKhoa ? this.cboChieuKhoaValue : null,
          pTuNgay: vtungay,
          pDenNgay: vdenngay,
          pBuocXLNId: vbuocxln_id,
          pTrangThaiBuocXL: vtrangthai_bxl,
          pBuocXLNHTId: this.vbuocxln_ht_id,
          pTrangThaiHT: vtrangthai_ht,
          pSoLanXL: vsolan_xl,
          pNhanVienXLId: vnhanvienxl_id,
          pSoLanBuocXL: vsolan_bxl,
          pBuocXLNIDNext: vbuocxln_id_next,
          pLoaiNVId: this.cboLoaiNVXLValue,
          pTuNgayChuyen: vtungay_chuyen,
          pDenNgayChuyen: vdenngay_chuyen,
          pToTruong: this.vtotruong,
        };

        let response = await API.getDSPhieuChuaGiao(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.gridPhieuChuaGiao = response.data.data;
          this.gridPhieuChuaGiao_CustomDrawGroupRow();
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async HienThiDanhSachPhieuHenTra() {
      if (this.chkNhanVienXLN) {
        console.log(this.cboNhanVienXLNValue);
        if (this.isNullEmpty(this.cboNhanVienXLNValue)) {
          this.$toast.error("Chưa chọn nhân viên xử lý nợ !");
          return;
        }
      }
      let vngayhen = "";
      if (this.chkNgayHenTra) {
        vngayhen = this.formatDate(this.dtpNgayHenTra);
      }
      let vgiaophieu_id = null;
      if (this.chkLuotGiaoHT) {
        if (!this.isNullEmpty(this.cboLuotGiaoPhieuHenTraValue)) {
          vgiaophieu_id = this.cboLuotGiaoPhieuHenTraValue;
        }
      }
      let vnhanvienxl_id = "";
      if (this.chkNhanVienXLN) {
        if (this.isNullEmpty(this.cboNhanVienXLNValue)) {
          this.$toast.error("Bạn chưa chọn nhân viên xử lý nợ!");
          return;
        }
        vnhanvienxl_id = this.cboNhanVienXLNValue;
      } else {
        if (this.cboNhanVienXLN != [] && this.cboNhanVienXLN != null) {
          this.cboNhanVienXLN.forEach((element, index) => {
            if (index == 0) {
              vnhanvienxl_id = element.NHANVIEN_ID;
            } else {
              vnhanvienxl_id += "," + element.NHANVIEN_ID;
            }
          });
        }
      }
      let vlantao_hentra_id = "";
      if (this.chkLuotGiao) {
        if (this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          this.$toast.error("Hãy chọn lần tạo dữ liệu!");
          return;
        }
        vlantao_hentra_id = this.cboLanTaoDuLieuValue;
      } else vlantao_hentra_id = "";
      this.gridPhieuHenTra = [];
      try {
        this.loading(true);
        let data = {
          pKyCuoc: this.kyCuoc,
          pNhanVienTCId: vnhanvienxl_id,
          pBuocXLNId: this.vbuocxln_ht_id,
          pNgayHen: vngayhen,
          pGiaoPhieuId: vgiaophieu_id,
          pLanTaoId: vlantao_hentra_id,
          pageNo: 0,
          pageSize: 1000,
        };
        let response = await API.getDSPhieuHenTra(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.gridPhieuHenTra = response.data.data;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async HienThiDanhSachPhieuDaGiao() {
      if (this.vbuocxln_ht_id == 1) {
        return;
      }
      if (this.isNullEmpty(this.kyCuoc)) {
        this.$toast.error("Bạn chưa chọn kỳ hóa đơn!");
        return;
      }
      if (this.chkNhanVienXLN) {
        if (this.isNullEmpty(this.cboNhanVienXLNValue)) {
          this.$toast.error("Chưa chọn nhân viên xử lý nợ !");
          return;
        }
      }
      let vgiaophieudg_id = null;
      if (this.chkLuotGiao) {
        if (this.isNullEmpty(this.cboLuotGiaoPhieuDaGiaoValue)) {
          this.$toast.error("Hãy chọn lượt giao!");
          return;
        }
        vgiaophieudg_id = this.cboLuotGiaoPhieuDaGiaoValue;
      }
      //them
      let vkieunodg = KIEUNO.TONG_NO;
      if (this.showKieuNoDG()) vkieunodg = this.cboKieuNoDGValue;
      let vloaikhdg_id;
      if (this.chkLoaiKHDG) {
        vloaikhdg_id = this.cboLoaiKHDGValue;
      } else vloaikhdg_id = null;

      let vlantaodg_id;
      if (this.chkLanTao) {
        if (this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          this.$toast.error("Hãy chọn lần tạo dữ liệu!");
          return;
        }
        vlantaodg_id = this.cboLanTaoDuLieuValue;
      } else vlantaodg_id = null;

      let vtudau1dg = "";
      if (this.chkTutienDG) vtudau1dg = this.cboTudauDGValue;
      else vtudau1dg = "";
      let vdendau1dg = "";
      if (this.chkDentienDG) vdendau1dg = this.cboDendauDGValue;
      else vdendau1dg = "";
      let vtutiendg = null;
      let vdentiendg = null;
      if (!this.isNullEmpty(this.txtTutienDG)) vtutiendg = this.txtTutienDG;
      if (!this.isNullEmpty(this.txtDentienDG)) vdentiendg = this.txtDentienDG;

      //thang
      let vtudau2dg = "";
      if (this.chkTuThangDG) vtudau2dg = this.cboTudau_THDGValue;
      else vtudau2dg = "";
      let vdendau2dg = "";
      if (this.chkDenThangDG) vdendau2dg = this.cboDendau_THDGValue;
      else vdendau2dg = "";
      let vtuthangnodg = null;
      if (!this.isNullEmpty(this.txtTuThangDG))
        vtuthangnodg = this.txtTuThangDG;
      let vdenthangnodg = null;
      if (!this.isNullEmpty(this.txtDenThangDG))
        vdenthangnodg = this.txtDenThangDG;

      let vdonvidg_id = this.cboDVQLDGValue;

      //Bước hiện tại
      // trạng thái hiện tại
      let vtrangthai_ht = this.cboTrangThaiXLDGValue;
      let vnhanvienxl_id = "";
      if (this.chkNhanVienXLN) {
        if (this.isNullEmpty(this.cboNhanVienXLNValue)) {
          this.$toast.error("Bạn chưa chọn nhân viên xử lý nợ!");
          return;
        }
        vnhanvienxl_id = this.cboNhanVienXLNValue;
      } else if (this.cboNhanVienXLN != [] && this.cboNhanVienXLN != null) {
        this.cboNhanVienXLN.forEach((element, index) => {
          if (index == 0) {
            vnhanvienxl_id = element.NHANVIEN_ID;
          } else {
            vnhanvienxl_id += "," + element.NHANVIEN_ID;
          }
        });
      }
      let vkieuxl_id = 1;
      let _kieuxl_id;
      let response = await API.getKieuXuLyId(this.axios, {
        pBuocXLNHTId: this.vbuocxln_ht_id,
      });
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data !== undefined
      ) {
        _kieuxl_id = response.data.data[0].KIEUXL_ID;
      }
      if (_kieuxl_id != "-1") {
        vkieuxl_id = _kieuxl_id;
      }
      let vphieuhen = null;
      if (this.chkPhieuHenDG) {
        if (!this.isNullEmpty(this.cboPhieuHenDGValue)) {
          vphieuhen = this.cboPhieuHenDGValue;
        }
      }
      let vtungay_giao = null;
      let vdenngay_giao = null;
      if (this.chkTuNgayGiao) {
        vtungay_giao = this.formatDate(this.dtpTuNgayGiao);
        vdenngay_giao = this.formatDate(this.dtpDenNgayGiao);
      }
      this.gridPhieuDaGiao = [];

      try {
        this.loading(true);
        let data = {
          pKyCuoc: this.kyCuoc,
          pKieuXLId: vkieuxl_id,
          pBuocXLNId: this.vbuocxln_ht_id,
          pNhanVienXLId: vnhanvienxl_id,
          pGiaoPhieuId: vgiaophieudg_id,
          pTrangThai: vtrangthai_ht,
          pDonViId: vdonvidg_id,
          pLoaiKHId: vloaikhdg_id,
          pKieuNo: vkieunodg,
          pPhieuHen: vphieuhen,
          pTuDau1: vtudau1dg,
          pDenDau1: vdendau1dg,
          pTuTien: vtutiendg,
          pDenTien: vdentiendg,
          pTuDau2: vtudau2dg,
          pTuThangNo: vtuthangnodg,
          pDenDau2: vdendau2dg,
          pDenThangNo: vdenthangnodg,
          pNhanVienId: this.$auth.getNhanVienID(),
          pLanTaoId: vlantaodg_id,
          pTuNgayGiao: vtungay_giao,
          pDenNgayGiao: vdenngay_giao,
        };
        let response = await API.getDSPhieuDaGiao(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.gridPhieuDaGiao = response.data.data;
          this.gridviewPhieuDaGiao_CustomDrawGroupRow();
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.loading(false);
      }
      response = await API.getDSThuocTinhBBXLN(this.axios, {});
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data !== undefined
      ) {
        this.dtThuocTinh = response.data.data;
      }
    },
    gridPhieuChuaGiao_CustomDrawGroupRow() {
      this.gridPhieuChuaGiao.forEach((item) => {
        if (!this.ds_nvtc_id.includes(item.NVTC_ID.toString())) {
          this.ds_nvtc_id += item.NVTC_ID.toString() + ",";
        }
      });
    },
    gridviewPhieuDaGiao_CustomDrawGroupRow() {
      this.gridPhieuDaGiao.forEach((item) => {
        if (!this.ds_nhanvien_id.includes(item.NHANVIEN_ID.toString())) {
          this.ds_nhanvien_id += item.NHANVIEN_ID.toString() + ",";
        }
      });
    },
    async btnXem() {
      try {
        this.ktDocFile = false;
        switch (this.tabSelected) {
          case "tabPhieuChuaGiao":
            this.HienThiDanhSachPhieuChuaGiao();
            break;
          case "tabPhieuHenTra":
            this.HienThiDanhSachPhieuHenTra();
            break;
          case "tabPhieuDaGiao":
            this.HienThiDanhSachPhieuDaGiao();
            break;
        }
      } catch (error) {}
    },
    selectedItemsChangedPhieuChuaGiao(value) {
      this.gridPhieuChuaGiaoSelected = value;
    },
    selectedRowChangedPhieuChuaGiao(value) {
      this.gridPhieuChuaGiaoSelectedRow = value;
    },
    selectedItemsChangedPhieuHenTra(value) {
      this.gridPhieuHenTraSelected = value;
    },
    selectedRowChangedPhieuHenTra(value) {
      this.gridPhieuHenTraSelectedRow = value;
    },
    selectedItemsChangedPhieuDaGiao(value) {
      this.gridPhieuDaGiaoSelected = value;
    },
    selectedRowChangedPhieuDG(value) {
      this.gridPhieuDaGiaoSelectedRow = value;
      if (value) {
        if (!this.isNullEmpty(value.TRANGTHAI_XLN_ID)) {
          this.cboKetQuaXLValue = value.TRANGTHAI_XLN_ID;
        }
        if (!this.isNullEmpty(value.NOIDUNG_XLN_ID)) {
          this.cboLyDoXLValue = value.NOIDUNG_XLN_ID;
        }
        this.txtGhiChuXLN = value.GHICHU;
        if (value.NGAYHEN) {
          this.dtpNgayHenDG = moment(value.NGAYHEN, "DD/MM/YYYY").toDate();
        } else {
          this.dtpNgayHenDG = new Date();
        }
        this.KT_ThuocTinh();
        this.HT_ThuocTinh(value.BIENBAN_ID);
      }
    },
    async GiaoPhieuXLN_ChuyenBuoc() {
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Hãy chọn phiếu để giao!");
        return;
      }
      let vtongtien_hd = null;
      let vsoluong_hd = null;
      let vxulyno_id = null;
      let vbuocxln_id = this.vbuocxln_ht_id;
      let vnhanvien_id = null;
      let vma_xln = "";
      let vma_tt = "";
      let vmatb_dd = "";
      let vten_kh = "";
      let vdiachi_kh = "";
      let vbienban_id = null;
      let vlantao_id = null;
      let vmst = "";
      if (this.ds_nhanvien_id.length > 0) {
        this.ds_nhanvien_id = this.ds_nhanvien_id.substring(
          0,
          this.ds_nhanvien_id.length - 1
        );
      }
      let _nhanvien_id = this.ds_nhanvien_id.split(",");
      for (const strnhanvien_id of _nhanvien_id) {
        vtongtien_hd = null;
        vsoluong_hd = null;
        let dtTemp = [];
        lstPhieuDaGiao.forEach((item) => {
          if (strnhanvien_id === item.NHANVIEN_ID.toString()) {
            vxulyno_id = item.XULYNO_ID;
            vma_xln = item.MA_XLN;
            vma_tt = item.MA_KH;
            vmatb_dd = item.MATB_DD;
            vten_kh = item.TEN_KH;
            vdiachi_kh = item.DIACHI_KH;
            vnhanvien_id = item.NHANVIEN_ID;
            vmst = null;
            vlantao_id = item.LANTAO_ID;

            dtTemp.push({
              xulyno_id: vxulyno_id,
              buocxln_id: vbuocxln_id,
              nhanvien_id: vnhanvien_id,
              ma_xln: vma_xln,
              matb_dd: vmatb_dd,
              ten_kh: vten_kh,
              diachi_kh: vdiachi_kh,
              mst: vmst,
              lantao_id: vlantao_id,
              ma_tt: vma_tt,
            });
            if (!this.isNullEmpty(item.TONGNO.toString()))
              vtongtien_hd += parseFloat(item.TONGNO);
            vsoluong_hd++;
          }
        });
        if (dtTemp.length > 0) {
          try {
            this.loading(true);
            let data = {
              pGiaoPhieuId: null,
              pLanTaoId: dtTemp[0].lantao_id,
              pNgayGiao: this.formatDate(this.dtpNgayGiao),
              pSoLuongGiao: vsoluong_hd,
              pTongTienGiao: vtongtien_hd,
              pNguoiGiaoId: this.$auth.getNhanVienID(),
              pKieuGiao: 1,
              pKyCuoc: this.kyCuoc,
              pNguoiNhanId: strnhanvien_id,
              pGiaoVet: 3,
              pGhiChu: this.txtGhiChu,
              pMayCN: null,
              pThongTinCT: dtTemp,
            };
            let response = await API.putGiaoPhieuXuLyNo(this.axios, data);
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
            ) {
            }
          } catch (error) {
            console.log(error);
            this.$toast.error(error.data.message_detail);
          } finally {
            this.loading(false);
          }
        }
      }
      this.HienThiDanhSachPhieuChuaGiao();
      let in_lantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          in_lantao_id = this.cboLanTaoDuLieuValue;
        }
      }
      this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
    },
    async btnGiaoPhieu() {
      let lstPhieuChuaGiao = [];
      //   this.gridPhieuChuaGiaoSelected.forEach((element) => {
      //     this.gridPhieuChuaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuChuaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuChuaGiao = this.$refs.gridPhieuChuaGiao.getSelectedRecords();
      if (lstPhieuChuaGiao.length <= 0) {
        this.$toast.error("Hãy chọn phiếu để giao!");
        return;
      }
      if (lstPhieuChuaGiao.filter((item) => item.NVTC_ID == "").length > 0) {
        let temp = lstPhieuChuaGiao.filter((item) => item.NVTC_ID == "");
        this.$toast.error(
          "Mã " + temp[0].MA_XLN + " chưa được gán nhân viên. Hãy kiểm tra lại!"
        );
        return;
      }
      let vtongtien_hd = null;
      let vsoluong_hd = null;
      let vxulyno_id = null;
      let vbuocxln_id = this.vbuocxln_ht_id;
      let vnhanvien_id = null;
      let vma_xln = "";
      let vma_tt = "";
      let vmatb_dd = "";
      let vten_kh = "";
      let vdiachi_kh = "";
      let vbienban_id = null;
      let vlantao_id = null;
      let vmst = "";
      if (this.ds_nvtc_id.Length > 0) {
        this.ds_nvtc_id = this.ds_nvtc_id.substring(
          0,
          this.ds_nvtc_id.length - 1
        );
      }
      let _nvtc_id = this.ds_nvtc_id.split(",");
      for (const strnvtc_id of _nvtc_id) {
        vtongtien_hd = null;
        vsoluong_hd = null;
        let dtTemp = [];
        lstPhieuChuaGiao.forEach((item) => {
          if (strnvtc_id === item.NVTC_ID.toString()) {
            vxulyno_id = item.XULYNO_ID;
            vma_xln = item.MA_XLN;
            vma_tt = item.MA_TT;
            vmatb_dd = item.MATB_DD;
            vten_kh = item.TEN_TT;
            vdiachi_kh = item.DIACHI_TT;
            vnhanvien_id = item.NVTC_ID;
            vmst = item.MST;
            vlantao_id = item.LANTAO_ID;

            dtTemp.push({
              xulyno_id: vxulyno_id,
              buocxln_id: vbuocxln_id,
              nhanvien_id: vnhanvien_id,
              ma_xln: vma_xln,
              matb_dd: vmatb_dd,
              ten_kh: vten_kh,
              diachi_kh: vdiachi_kh,
              mst: vmst,
              lantao_id: vlantao_id,
              ma_tt: vma_tt,
            });
            if (!this.isNullEmpty(item.TONGNO.toString()))
              vtongtien_hd += parseFloat(item.TONGNO);
            vsoluong_hd++;
          }
        });
        if (dtTemp.length > 0) {
          try {
            this.loading(true);
            let data = {
              pGiaoPhieuId: null,
              pLanTaoId: dtTemp[0].lantao_id,
              pNgayGiao: this.formatDate(this.dtpNgayGiao),
              pSoLuongGiao: vsoluong_hd,
              pTongTienGiao: vtongtien_hd,
              pNguoiGiaoId: this.$auth.getNhanVienID(),
              pKieuGiao: 1,
              pKyCuoc: this.kyCuoc,
              pNguoiNhanId: strnvtc_id,
              pGiaoVet: 3,
              pGhiChu: this.txtGhiChu,
              pMayCN: null,
              pThongTinCT: dtTemp,
            };
            let response = await API.putGiaoPhieuXuLyNo(this.axios, data);
            if (
              response.data.error_code === "BSS-00000000" &&
              response.data.data !== undefined
            ) {
              this.$toast.success("Giao phiếu thành công!");
            }
          } catch (error) {
            console.log(error);
            this.$toast.error(error.data.message_detail);
          } finally {
            this.loading(false);
          }
        }
      }
      this.HienThiDanhSachPhieuChuaGiao();
      let in_lantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          in_lantao_id = this.cboLanTaoDuLieuValue;
        }
      }
      this.HienThiLuotGiao(this.cboNhanVienXLNValue, in_lantao_id);
    },
    async btnHuyGiao() {
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Hãy chọn phiếu để hủy !");
        return;
      }
      let lstPhieuHuyGiao = lstPhieuDaGiao.filter(
        (item) => item.TRANGTHAI.toString() == "0"
      );
      for (const item of lstPhieuHuyGiao) {
        try {
          let data = {
            pGiaoPhieuId: item.GIAOPHIEU_ID,
            pBienBanId: item.BIENBAN_ID,
            pTienHuy: item.TONG_GIAO,
          };
          let response = await API.putHuyGiaoPhieuXuLyNo(this.axios, data);
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            this.$toast.success(`Hủy phiếu ${item.MA_XLN} thành công!`);
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message_detail);
        }
      }
      let vlantao_id = null;
      if (this.chkLanTao) {
        if (!this.isNullEmpty(this.cboLanTaoDuLieuValue)) {
          vlantao_id = this.cboLanTaoDuLieuValue;
        }
      }
      this.HienThiLuotGiao(this.cboNhanVienXLNValue, vlantao_id);
      this.HienThiDanhSachPhieuDaGiao();
    },
    async KetThuc_ChuaGiao() {
      let dem = 0;
      let lstPhieuChuaGiao = [];
      //   this.gridPhieuChuaGiaoSelected.forEach((element) => {
      //     this.gridPhieuChuaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuChuaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuChuaGiao = this.$refs.gridPhieuChuaGiao.getSelectedRecords();
      if (lstPhieuChuaGiao.length <= 0) {
        this.$toast.error(
          "Bạn chưa chọn danh sách trên lưới để kết thúc xử lý!"
        );
        return;
      }
      let lstXLNId = lstPhieuChuaGiao.map((item) => item.XULYNO_ID);
      for (const item of lstXLNId) {
        try {
          let response = await API.getKiemTraKetThucXLN(this.axios, {
            pXuLyNoId: item,
          });
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data == "ok") {
              await API.postKetThucXLN(this.axios, {
                pXuLyNoId: item,
              });
            } else {
              this.$toast.error(response.data.data);
              return;
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message_detail);
        }
      }
      this.$toast.success("Cập nhật dữ liệu thành công!");
      this.HienThiDanhSachPhieuChuaGiao();
    },
    async KetThuc() {
      let dem = 0;
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error(
          "Bạn chưa chọn danh sách trên lưới để kết thúc xử lý!"
        );
        return;
      }
      let lstXLNId = lstPhieuDaGiao.map((item) => item.XULYNO_ID);
      for (const item of lstXLNId) {
        try {
          let response = await API.getKiemTraKetThucXLN(this.axios, {
            pXuLyNoId: item,
          });
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined
          ) {
            if (response.data.data == "ok") {
              await API.postKetThucXLN(this.axios, {
                pXuLyNoId: item,
              });
            } else {
              this.$toast.error(response.data.data);
              return;
            }
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message_detail);
        }
      }
      this.$toast.success("Cập nhật dữ liệu thành công!");
      this.HienThiDanhSachPhieuDaGiao();
    },
    async btnKetThuc() {
      if (this.tabSelected == this.tabPPhieuChuaGiao) {
        this.KetThuc_ChuaGiao();
      } else if (this.tabSelected == this.tabPPhieuDaGiao) {
        this.KetThuc();
      }
    },
    KiemTraDK_HoanThanh(thangnam_tt, thangnam_sys) {
      let result = false;
      if (
        parseInt(thangnam_tt.substring(6, thangnam_tt.length)) <
        parseInt(thangnam_sys.substring(6, thangnam_sys.length))
      )
        //So sánh năm
        return true;
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (
        parseInt(thangnam_tt.substring(6, thangnam_tt.length)) ==
        parseInt(thangnam_sys.substring(6, thangnam_sys.length))
      ) {
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (
          parseInt(thangnam_tt.substring(3, 5)) <
          parseInt(thangnam_sys.substring(3, 5))
        )
          result = true;
        else if (
          parseInt(thangnam_tt.substring(3, 5)) ==
          parseInt(thangnam_sys.substring(3, 5))
        )
          if (
            parseInt(thangnam_tt.substring(0, 2)) <=
            parseInt(thangnam_sys.substring(0, 2))
          )
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            return true;
          else return false;
        else result = false; //tháng tt lớn hơn thang ht
      } else result = false;

      return result;
    },
    Kiemtra_DL() {
      if (this.isNullEmpty(this.cboKetQuaXLValue)) {
        this.$toast.error("Bạn chưa chọn kết quả xử lý!");
        return false;
      }

      if (this.isNullEmpty(this.cboLyDoXLValue)) {
        this.$toast.error("Bạn chưa chọn Lý do xử lý!");
        return false;
      }
      let listKiemTra = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         listKiemTra.push(item);
      //         return;
      //       }
      //     });
      //   });
      listKiemTra = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (this.cboKetQuaXLValue == KETQUA_XLN.THANHTOAN_HET) {
        if (listKiemTra.filter((item) => item.TONGNO == 0).length > 0) {
          this.$toast.error(
            "Bạn đang chọn kết quả : " +
              this.funcGetTextcboKetQuaXL(this.cboKetQuaXLValue) +
              ". Mã xử lý nợ: " +
              listKiemTra.find((item) => item.TONGNO == 0).MA_XLN.toString() +
              " có tổng nợ bằng 0! bạn không thể cập nhật"
          );
          return false;
        }
      }
      let lstTongNoAm = listKiemTra.filter((item) => item.TONGNO <= 0);
      if (
        lstTongNoAm.length > 0 &&
        this.cboKetQuaXLValue != KETQUA_XLN.KHACHHANG_TU_THANHTOAN
      ) {
        this.$toast.error(
          "Khách hàng có mã: " +
            lstTongNoAm[0].MA_XLN.toString() +
            " có tổng nợ = " +
            lstTongNoAm[0].TONGNO.toString() +
            " Bạn phải chọn kết quả xử lý: Khách hàng tự đi thanh toán"
        );
        return false;
      }
      if (this.cboKetQuaXLValue == KETQUA_XLN.KHACHHANG_TU_THANHTOAN) {
        // tự thanh toán
        if (listKiemTra.filter((r) => r.TONGNO > 0).length > 0) {
          this.$toast.error(
            "Bạn đang chọn kết quả : " +
              this.funcGetTextcboKetQuaXL(this.cboKetQuaXLValue) +
              ".\nMã xử lý nợ: " +
              listKiemTra.filter((r) => r.TONGNO != 0)[0].MA_XLN.toString() +
              " có tổng nợ khác 0! bạn không thể cập nhật"
          );
          return false;
        }
      }
      if (!this.ktDocFile && this.cboKetQuaXLValue == KETQUA_XLN.KHONGTHUDUOC) {
        // tự thanh toán
        if (this.txtGhiChuXLN.trim() == "") {
          this.$toast.error(
            "Bạn đang chọn kết quả : " +
              this.funcGetTextcboKetQuaXL(this.cboKetQuaXLValue) +
              ". Phải ghi chú không được để trống! "
          );
          return false;
        }
      }
      return true;
    },
    funcGetTextcboKetQuaXL(value) {
      let res = this.cboKetQuaXL.find((item) => item.TRANGTHAI_XLN_ID == value);
      if (!this.isNullEmpty(res)) return res.TRANGTHAI_XLN;
    },
    isNumeric(str) {
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    funcKiemTraCapNhatDaGiao() {
      if (this.isNullEmpty(this.txtSeri)) {
        this.$toast.error("Hãy nhập nội dung Seri!");
        return;
      }
      if (this.isNullEmpty(this.txtQuyen)) {
        this.$toast.error("Hãy nhập nội dung Quyển!");
        return;
      }
      if (this.isNullEmpty(this.txtSoSeri)) {
        this.$toast.error("Hãy nhập nội dung số Seri!");
        return;
      }
      if (!this.isNumeric(this.txtSoSeri)) {
        this.$toast.error("Dữ liệu số seri phải là kiểu số");
        return;
      }
    },
    async btnCapNhat() {
      if (
        !this.KiemTraDK_HoanThanh(
          this.formatDate(new Date()),
          this.formatDate(this.dtpNgayHenDG)
        )
      ) {
        this.$toast.error(
          "Bạn không được phép chọn ngày hẹn trả nhỏ hơn ngày hiện tại!"
        );
        return;
      }
      if (this.gridPhieuDaGiao.length <= 0) {
        this.$toast.error("Chưa có dữ liệu chi tiết xử lý nợ!");
        return;
      }
      //   let lstPhieuDaGiao = this.gridPhieuDaGiao.filter((e) =>
      //     this.gridPhieuDaGiaoSelected.includes(e.MA_XLN)
      //   );
      let lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();

      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Bạn chưa chọn danh sách trên lưới để cập nhật!");
        return;
      }
      let dsbienban_id = lstPhieuDaGiao.map((item) => item.BIENBAN_ID);
      if (!this.Kiemtra_DL()) return;
      await this.$bvModal
        .msgBoxConfirm("Bạn có chắc chắn cập nhật dữ liệu không?", {
          title: "Thông báo",
          size: "lg",
          buttonSize: "lg",
          okVariant: "primary",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          hideHeaderClose: false,
          centered: true,
          "modal-class": ["f18", "text-center"],
        })
        .then(async (value) => {
          if (value) {
            if (
              this.cboKetQuaXLValue == TRANGTHAI_XLN.KHACHHANG_HENTRA ||
              this.cboKetQuaXLValue == TRANGTHAI_XLN.DAXULY ||
              this.cboKetQuaXLValue == TRANGTHAI_XLN.XULYNO_HOIDONG
            ) {
              if (this.vkieuxl_id == 1) {
                this.funcKiemTraCapNhatDaGiao();
                this.so_seri = Number.parseFloat(this.txtSoSeri);
                let lstWhereNo = lstPhieuDaGiao.filter(
                  (e) => e.NO_HENTRA == 0 && e.TONGNO != 0
                );
                if (lstWhereNo.length > 0) {
                  this.$toast.error(
                    `Tiền trả mã  ${lstWhereNo
                      .map((e) => e.MA_XLN)
                      .toString()}  không thỏa mãn điều kiện`
                  );
                  return;
                }
              }
            }
            this.KT_ThuocTinh();

            let lstDSBienBan = [];
            let lstDSThuocTinhBB = [];
            let lstDSThuHoiThietBi = [];
            for (const key in this.dsControl) {
              lstDSThuocTinhBB.push({
                thuoctinhbb_id: this.dsControl[key].THUOCTINHBB_ID,
                giatri: this.dsControlProp[this.dsControl[key].THUOCTINH_BB],
              });
            }
            for (const item of this.ds_thtbi) {
              lstDSThuHoiThietBi.push({
                thuebao_id: item.THUEBAO_ID,
                ma_tb: item.MA_TB,
                trangthaitb_id: item.TRANGTHAITB_ID,
                loaitbi_id: item.LOAITBI_ID,
                sl: item.SL,
                ngay_th: moment(new Date()).format("DD/MM/YYYY"),
                nhanvien_id: item.NHANVIEN_ID,
                ketqua_id: item.KETQUA_ID,
                ghichu: item.GHICHU,
              });
            }
            for (const r of lstPhieuDaGiao) {
              lstDSBienBan.push({
                bienban_id: r.BIENBAN_ID,
                no_hentra: r.NO_HENTRA,
                ma_nv: r.MA_NV,
                so_seri: this.txtSoSeri,
                ds_thuoctinhbb: lstDSThuocTinhBB,
                ds_thuhoi_tbi: lstDSThuHoiThietBi,
              });
            }
            let data = {
              pKyCuoc: this.kyCuoc,
              pDonViQuanLyNoId: this.cboDVQLDGValue,
              pTrangThaiXLN: this.cboKetQuaXLValue,
              pNoiDungXLNId: this.cboLyDoXLValue,
              pNgayXLN: this.formatDate(this.dtpNgayXLDG),
              pNgayHen: this.formatDate(this.dtpNgayHenDG),
              pDSBienBan: lstDSBienBan,
              pSeri: this.txtSeri,
              pQuyen: this.txtQuyen,
              pGhiChu: this.txtGhiChuXLN,
            };
            try {
              this.loading(true);
              let response = await API.postCapNhatPhieu(this.axios, data);
              if (
                response.data.error_code === "BSS-00000000" &&
                response.data.data !== undefined
              ) {
                this.HienThiDanhSachPhieuDaGiao();
                this.$toast.success("Cập nhật dữ liệu thành công!");
              }
            } catch (error) {
              console.log(error);
              this.$toast.error(error.data.message_detail);
            } finally {
              this.loading(false);
            }
          }
        })
        .catch((err) => {
          this.loading(false);
        });
    },
    async btnChuyenBuoc() {
      if (this.cboTrangThaiXLDGValue == 1) {
        let lstPhieuDaGiao = [];
        lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
        let dsbienban = lstPhieuDaGiao.filter(
          (item) => item.TRANGTHAI_XLN_ID == TRANGTHAI_XLN.KHONGTHUDUOC
        );
        let dsbienban_id = [];
        dsbienban.forEach((element) => {
          dsbienban_id.push(element.BIENBAN_ID);
        });
        if (dsbienban_id.length <= 0) {
          this.$toast.error(
            'Bạn chưa chọn danh sách trên lưới. Hoặc trạng thái XLN không phải là "Không thu được"!'
          );
          return;
        }
        if (!this.chkChuyenBuoc) {
          this.$toast.error("Hãy chọn bước để chuyển sang bước khác!.");
          return;
        }
        await this.$bvModal
          .msgBoxConfirm(
            'Chỉ chuyển được các XLN có trạng thái "Không thu được". Bạn thật sự muốn chuyển dữ liệu sang bước khác không?',
            {
              title: "Thông báo",
              size: "lg",
              buttonSize: "lg",
              okVariant: "primary",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy bỏ",
              hideHeaderClose: false,
              centered: true,
              "modal-class": ["f18", "text-center"],
            }
          )
          .then(async (value) => {
            if (value) {
              this.loading(true);
              let data = {
                pDSBienBanId: dsbienban_id.join(),
                pBuocXLNIdNext: this.cboChuyenBuocValue,
                pNguoiChuyenId: this.$auth.getNhanVienID(),
              };
              const response = await API.postChuyenBuocXLN(
                this.axios,
                data
              ).catch((err) => {
                this.$toast.error(err.data.message_detail);
              });
              if (response.data.error_code === "BSS-00000000") {
                this.loading(false);
                this.GiaoPhieuXLN_ChuyenBuoc();
                this.$toast.success(`Cập nhật dữ liệu thành công!`);
              }
            }
          })
          .catch((err) => {
            this.loading(false);
          });
      } else {
        this.$toast.warning(
          "TT hiện thời phải là: Đã xử lý thì mới được chuyển bước!"
        );
      }
    },
    async btnHuyKQ() {
      await this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn hủy kết quả cập nhật không ?", {
          title: "Thông báo",
          size: "lg",
          buttonSize: "lg",
          okVariant: "primary",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          hideHeaderClose: false,
          centered: true,
          "modal-class": ["f18", "text-center"],
        })
        .then(async (value) => {
          if (value) {
            let lstPhieuDaGiao = [];
            // this.gridPhieuDaGiaoSelected.forEach((element) => {
            //   this.gridPhieuDaGiao.forEach((item) => {
            //     if (item.MA_XLN == element) {
            //       lstPhieuDaGiao.push(item);
            //       return;
            //     }
            //   });
            // });
            lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
            if (lstPhieuDaGiao.length <= 0) {
              this.$toast.error("Bạn chưa chọn danh sách trên lưới để Hủy!");
              return;
            }

            let dsBienBanId = "";
            lstPhieuDaGiao.forEach((item) => {
              dsBienBanId += item.BIENBAN_ID + ",";
            });
            this.loading(true);
            let data = {
              pKyCuoc: this.kyCuoc,
              pDSBienBanId: dsBienBanId.substring(0, dsBienBanId.length - 1),
              pMayHuy: "",
            };
            const response = await API.postHuyKetQuaXLN(this.axios, data).catch(
              (err) => {
                this.$toast.error(err.data.data);
              }
            );
            if (response.data.error_code === "BSS-00000000") {
              this.loading(false);
              this.$toast.success(`Hủy kết quả cập nhật thành công!`);
              this.HienThiDanhSachPhieuDaGiao();
            }
          }
        })
        .catch((err) => {
          this.loading(false);
        });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map((v) =>
        filterVal.map((j) => {
          return v[j];
        })
      );
    },
    async btnExportExcel() {
      switch (this.tabSelected) {
        case this.tabPPhieuChuaGiao:
          if (this.gridPhieuChuaGiao.length == 0)
            return this.$toast.error("Không có dữ liệu để xuất Excel");
          exportExcelCustom(
            [...this.collumnPhieuChuaGiao].slice(1),
            this.gridPhieuChuaGiao,
            "PhieuChuaGiao",
            "export"
          );

          break;
        case this.tabPPhieuHenTra:
          if (this.gridPhieuHenTra.length == 0)
            return this.$toast.error("Không có dữ liệu để xuất Excel");

          exportExcelCustom(
            [...this.collumnPhieuHenTra],
            this.gridPhieuHenTra,
            "PhieuHenTra",
            "export"
          );
          break;
        case this.tabPPhieuDaGiao:
          if (this.gridPhieuDaGiao.length == 0)
            return this.$toast.error("Không có dữ liệu để xuất Excel");
          exportExcelCustom(
            [...this.collumnPhieuDaGiao],
            this.gridPhieuDaGiao,
            "PhieuDaGiao",
            "export"
          );
          break;
      }
    },

    async updateDC(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
      if (this.isNullEmpty(this.txtDiaChiChange)) {
        this.$toast.error("Bạn chưa nhập thông tin thay đổi!");
        return;
      }
      if (this.txtDiaChiChange.length > 2000) {
        this.$toast.error("Bạn nhập thông tin quá dài!");
        return;
      }
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Bạn chưa chọn danh sách trên lưới!");
        return;
      }
      if (lstPhieuDaGiao.length > 1) {
        this.$toast.error(
          "Bạn chọn nhiều hơn một phiếu. Vui lòng chọn một phiếu!"
        );
        return;
      }
      let _bienban_id = lstPhieuDaGiao[0].BIENBAN_ID;
      let _xulyno_id = lstPhieuDaGiao[0].XULYNO_ID;
      try {
        this.loading(true);
        let data = {
          pXuLyNoId: _xulyno_id,
          pBienBanId: _bienban_id,
          pDiaChiMoi: this.txtDiaChiChange,
        };
        let response = await API.postCapNhatDiaChi(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.$toast.success("Cập nhật dữ liệu thành công!");
          this.HienThiDanhSachPhieuDaGiao();
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async btnSuaDC() {
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Bạn chưa chọn danh sách trên lưới!");
        return;
      }
      if (lstPhieuDaGiao.length > 1) {
        this.$toast.error(
          "Bạn chọn nhiều hơn một phiếu. Vui lòng chọn một phiếu!"
        );
        return;
      }
      let value = lstPhieuDaGiao[0].DIACHI_KH;
      // if (value.includes(",")) {
      //   value = value.replaceAll(",", ".");
      // }
      this.txtDiaChiChange = value;
      this.$bvModal.show("modal-prevent-closing");
    },
    async btnSuaTien() {
      let lstPhieuDaGiao = [];
      //   this.gridPhieuDaGiaoSelected.forEach((element) => {
      //     this.gridPhieuDaGiao.forEach((item) => {
      //       if (item.MA_XLN == element) {
      //         lstPhieuDaGiao.push(item);
      //         return;
      //       }
      //     });
      //   });
      lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      if (lstPhieuDaGiao.length <= 0) {
        this.$toast.error("Bạn chưa chọn danh sách trên lưới!");
        return;
      }

      await this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn sửa tiền trả ?", {
          title: "Thông báo",
          size: "lg",
          buttonSize: "lg",
          okVariant: "primary",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy bỏ",
          hideHeaderClose: false,
          centered: true,
          "modal-class": ["f18", "text-center"],
        })
        .then(async (value) => {
          if (value) {
            this.loading(true);
            let response = null;
            for (const item of lstPhieuDaGiao) {
              let data = {
                pBienBanId: item.BIENBAN_ID,
                pTienTra: item.NO_HENTRA,
                pMaTT: item.MA_XLN,
                pXuLyNoId: item.XULYNO_ID,
              };
              response = await API.postCapNhatTienNhacNo(
                this.axios,
                data
              ).catch((err) => {
                this.loading(false);
                this.$toast.error(err.data.message_detail);
              });
            }
            if (response.data.error_code === "BSS-00000000") {
              this.loading(false);
              this.$toast.success(`Cập nhật dữ liệu thành công!`);
            }
          }
        })
        .catch((err) => {
          this.loading(false);
        });
    },
    async btnGanNV() {
      if (this.isNullEmpty(this.cboLoaiNVXLValue)) {
        this.$toast.error("Hãy chọn loại nhân viên xử lý");
        return;
      }
      if (this.gridPhieuChuaGiao.length <= 0) {
        this.$toast.error("Chưa có dữ liệu để gán nhân viên");
        return;
      }
      let dtPhieuD = [];
      let dtPhieuFile = [];
      this.gridPhieuChuaGiaoSelected.forEach((element) => {
        this.gridPhieuChuaGiao.forEach((item) => {
          if (item.MA_XLN == element) {
            dtPhieuD.push({
              MA_XLN: item.MA_XLN,
              TEN_KH: item.TEN_KH,
              DIACHI_KH: item.DIACHI_KH,
              XULYNO_ID: item.XULYNO_ID,
              MANV_TC: item.MANV_TC,
              NHANVIEN_ID: this.isNullEmpty(item.NVTC_ID) ? 0 : item.NVTC_ID,
            });
            return;
          } else {
            dtPhieuFile.push({
              MA_XLN: item.MA_XLN,
              TEN_KH: item.TEN_KH,
              DIACHI_KH: item.DIACHI_KH,
              XULYNO_ID: item.XULYNO_ID,
              MANV_TC: item.MANV_TC,
              NHANVIEN_ID: this.isNullEmpty(item.NVTC_ID) ? 0 : item.NVTC_ID,
            });
            return;
          }
        });
      });
      if (this.cboLoaiNVXLValue == Loai_NV.NHANVIEN_PHAPLY) {
        this.$refs.dlgGanNhanVienPL.dtDotLay = dtPhieuD;
        this.$refs.dlgGanNhanVienPL.loainvxl_id = this.loainv_xl_id;
        this.$refs.dlgGanNhanVienPL.openDialog();
      } else {
        this.$refs.dlgGanNhanVienTN.dtDotLay = dtPhieuD;
        this.$refs.dlgGanNhanVienTN.dtPhieuFile = dtPhieuFile;
        this.$refs.dlgGanNhanVienTN.loainvxl_id = this.loainv_xl_id;
        this.$refs.dlgGanNhanVienTN.openDialog();
      }
    },
    dlgGanNhanVienPLClosed() {
      this.HienThiDanhSachPhieuChuaGiao();
      this.HT_DotLaySoLieu();
    },
    dlgGanNhanVienTNClosed() {
      this.HienThiDanhSachPhieuChuaGiao();
      this.HT_DotLaySoLieu();
    },
    btnHoSoGoc() {
      if (this.gridPhieuDaGiao.length <= 0)
        return this.$toast.error("Danh sách đang trống!");
      if (this.isNullEmpty(this.gridPhieuDaGiaoSelectedRow))
        return this.$toast.error("Bạn chưa chọn phiếu!");
      this.$refs.dlgHoSoGoc.vma_kh = this.gridPhieuDaGiaoSelectedRow.MA_XLN;
      this.$refs.dlgHoSoGoc.xulyno_id =
        this.gridPhieuDaGiaoSelectedRow.XULYNO_ID;
      this.$refs.dlgHoSoGoc.buoc_xln_id = this.vbuocxln_ht_id;
      this.$refs.dlgHoSoGoc.dialogOpen();
    },
    btnInHD() {
      //   let lstPhieuDaGiao = this.gridPhieuDaGiao.filter((e) =>
      //     this.gridPhieuDaGiaoSelected.includes(e.MA_XLN)
      //   );
      let lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
      let arrayMaTT = lstPhieuDaGiao.map((item) => item.MA_XLN);
      if (arrayMaTT.length <= 0) {
        this.$toast.error("Bạn chưa chọn danh sách để in!");
        return;
      }
      this.$refs.dlgIHD.vdsma_tt = arrayMaTT.join(",");
      this.$refs.dlgIHD.tag = "";
      this.$refs.dlgIHD.openDialog();
    },
    btnThueBao() {
      this.$refs.dlgDSTBXLN.dt_KH = this.gridPhieuDaGiao;
      this.$refs.dlgDSTBXLN.vkyhoadon = this.kyCuoc;
      this.$refs.dlgDSTBXLN.vkieu = this.vkieuxl_id;
      this.$refs.dlgDSTBXLN.dialogOpen();
    },
    btnHoSo() {
      switch (this.tabSelected) {
        case this.tabPPhieuChuaGiao:
          if (this.gridPhieuChuaGiao.length <= 0)
            return this.$toast.error("Chưa có dữ liệu để in hồ sơ!");
          this.$refs.dlgInThongBao.vkyhoadon = this.kyCuoc;
          this.$refs.dlgInThongBao.vkieutao_dl = this.vkieuxl_id;
          this.$refs.dlgInThongBao.vbuocxln_id = this.vbuocxln_ht_id;
          this.$refs.dlgInThongBao.vtrangthai_xl = 1;
          let lstPhieuChuaGiao = [];
          //   this.gridPhieuChuaGiaoSelected.forEach((element) => {
          //     this.gridPhieuChuaGiao.forEach((item) => {
          //       if (item.MA_XLN == element) {
          //         lstPhieuChuaGiao.push(item);
          //         return;
          //       }
          //     });
          //   });
          lstPhieuChuaGiao = this.$refs.gridPhieuChuaGiao.getSelectedRecords();
          if (lstPhieuChuaGiao.length <= 0)
            return this.$toast.error("Bạn chưa chọn khách hàng để in!");
          let dataCG = [];
          lstPhieuChuaGiao.forEach((item) => {
            dataCG.push({
              MA_XLN: item.MA_XLN,
              MATB_DD: item.MATB_DD,
              TEN_TT: item.TEN_TT,
              DIACHI_TT: item.DIACHI_TT,
              TONGNO: item.TONGNO,
              XULYNO_ID: item.XULYNO_ID,
              MANV_TC: item.MANV_TC,
              BIENBAN_ID: "",
            });
          });
          this.$refs.dlgInThongBao.gridPhieuChuaGiao = dataCG;
          break;
        case this.tabPPhieuHenTra:
          if (this.gridPhieuHenTra.length <= 0)
            return this.$toast.error("Chưa có dữ liệu để in hồ sơ!");
          this.$refs.dlgInThongBao.vkyhoadon = this.kyCuoc;
          this.$refs.dlgInThongBao.vkieutao_dl = this.vkieuxl_id;
          this.$refs.dlgInThongBao.vbuocxln_id = this.vbuocxln_ht_id;
          this.$refs.dlgInThongBao.vtrangthai_xl = 2;
          let lstPhieuHenTra = [];
          //   this.gridPhieuHenTraSelected.forEach((element) => {
          //     this.gridPhieuHenTra.forEach((item) => {
          //       if (item.MA_XLN == element) {
          //         lstPhieuHenTra.push(item);
          //         return;
          //       }
          //     });
          //   });
          lstPhieuHenTra = this.$refs.gridPhieuHenTra.getSelectedRecords();
          if (lstPhieuHenTra.length <= 0)
            return this.$toast.error("Bạn chưa chọn khách hàng để in!");
          let dataHT = [];
          lstPhieuHenTra.forEach((item) => {
            dataHT.push({
              MA_XLN: item.MA_KH,
              MATB_DD: "",
              TEN_TT: item.TEN_KH,
              DIACHI_TT: item.DIACHI_KH,
              TONGNO: item.TONG_NO,
              XULYNO_ID: item.XULYNO_ID,
              MANV_TC: item.MANV_TC,
              BIENBAN_ID: "",
            });
          });
          this.$refs.dlgInThongBao.gridPhieuChuaGiao = dataHT;
          break;
        case this.tabPPhieuDaGiao:
          if (this.gridPhieuDaGiao.length <= 0)
            return this.$toast.error("Chưa có dữ liệu để in hồ sơ!");
          this.$refs.dlgInThongBao.vkyhoadon = this.kyCuoc;
          this.$refs.dlgInThongBao.vkieutao_dl = this.vkieuxl_id;
          this.$refs.dlgInThongBao.vbuocxln_id = this.vbuocxln_ht_id;
          this.$refs.dlgInThongBao.vtrangthai_xl = 3;
          let lstPhieuDaGiao = [];
          //   this.gridPhieuDaGiaoSelected.forEach((element) => {
          //     this.gridPhieuDaGiao.forEach((item) => {
          //       if (item.MA_XLN == element) {
          //         lstPhieuDaGiao.push(item);
          //         return;
          //       }
          //     });
          //   });
          lstPhieuDaGiao = this.$refs.gridPhieuDaGiao.getSelectedRecords();
          if (lstPhieuDaGiao.length <= 0)
            return this.$toast.error("Bạn chưa chọn khách hàng để in!");
          let dataDG = [];
          lstPhieuDaGiao.forEach((item) => {
            dataDG.push({
              MA_XLN: item.MA_XLN,
              MATB_DD: item.MATB_DD,
              TEN_TT: item.TEN_KH,
              DIACHI_TT: item.DIACHI_KH,
              TONGNO: item.TONGNO,
              XULYNO_ID: item.XULYNO_ID,
              MANV_TC: item.MANV_TC,
              BIENBAN_ID: item.BIENBAN_ID,
            });
          });
          this.$refs.dlgInThongBao.gridPhieuChuaGiao = dataDG;
          break;
      }
      this.$refs.dlgInThongBao.dialogOpen();
    },
    async btnLSNo() {
      switch (this.tabSelected) {
        case this.tabPPhieuChuaGiao:
          this.selectedXLN = this.gridPhieuChuaGiaoSelectedRow.MA_XLN;
          console.log(this.selectedXLN);
          break;
        case this.tabPPhieuHenTra:
          this.selectedXLN = this.gridPhieuHenTraSelectedRow.MA_XLN;
          console.log(this.selectedXLN);
          break;
        case this.tabPPhieuDaGiao:
          this.selectedXLN = this.gridPhieuDaGiaoSelectedRow.MA_XLN;
          console.log(this.selectedXLN);
          break;
      }
      await this.$bvModal.show("modal-lichsuno");
    },
    gridPhieuDaGiaoCommand(arg) {
      let element = arg.rowData;
      let bienban_id = null;
      bienban_id = element.BIENBAN_ID;
      this.$refs.dlgThuHoiVatTuGP.bienban_id = bienban_id;
      this.$refs.dlgThuHoiVatTuGP.ds_thtbi = this.ds_thtbi;
      this.$refs.dlgThuHoiVatTuGP.openDialog();
    },
    // async rowDoubleClickedPhieuDaGiao(...value) {
    //   // if (!this.isNullEmpty(value)) {
    //   //   let elementSelected = value[1];
    //   //   console.log(elementSelected);
    //   //   let bienban_id = null;
    //   //   bienban_id = elementSelected.BIENBAN_ID;
    //   //   this.$refs.dlgThuHoiVatTuGP.bienban_id = bienban_id;
    //   //   this.$refs.dlgThuHoiVatTuGP.ds_thtbi = this.ds_thtbi;
    //   //   this.$refs.dlgThuHoiVatTuGP.openDialog();
    //   // }
    // },
    DeleteRow(bienban_id) {
      try {
        for (let index = 0; index < this.ds_thtbi; index++) {
          if (this.ds_thtbi[index].BIENBAN_ID == bienban_id) {
            this.ds_thtbi.splice(index, 1);
          }
          index = -1;
        }
      } catch (error) {
        this.$toast.error(error);
      }
    },
    AddRow(data) {
      for (const item of data) {
        this.ds_thtbi.push({
          BIENBAN_ID: item.BIENBAN_ID,
          THUEBAO_ID: item.THUEBAO_ID,
          MA_TB: item.MA_TB,
          TRANGTHAITB_ID: item.TRANGTHAITB_ID,
          LOAITBI_ID: item.LOAITBI_ID,
          SL: item.SL,
          NGAY_TH: item.NGAY_TH,
          NHANVIEN_ID: item.NHANVIEN_ID,
          KETQUA_ID: item.KETQUA_ID,
          GHICHU: item.GHICHU,
        });
      }
    },
    confirmEventTHVT(...value) {
      if (value[0]) {
        if (!this.isNullEmpty(value[1])) {
          if (this.ds_thtbi.length <= 0) {
            this.ds_thtbi = value[1];
          } else {
            this.DeleteRow(value[2]);
            this.AddRow(value[1]);
          }
        }
      }
    },
    cellSavedPhieuChuaGiao(arg) {
      let KEY = arg.rowData.MA_XLN;
      if (arg.columnName == "NO_LECH") {
        if (arg.value) {
          this.gridPhieuChuaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_LECH = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "NO_GIAO") {
        if (arg.value) {
          this.gridPhieuChuaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_GIAO = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "TT_HS") {
        if (arg.value) {
          this.gridPhieuChuaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.TT_HS = arg.value;
              return;
            }
          });
        }
      }
    },
    cellSavedPhieuHenTra(arg) {
      let KEY = arg.rowData.MA_XLN;
      if (arg.columnName == "TONG_NO") {
        if (arg.value) {
          this.gridPhieuHenTra.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.TONG_NO = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "NO_LECH") {
        if (arg.value) {
          this.gridPhieuHenTra.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_LECH = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "NO_HENTRA") {
        if (arg.value) {
          this.gridPhieuHenTra.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_HENTRA = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "TT_HS") {
        if (arg.value) {
          this.gridPhieuHenTra.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.TT_HS = arg.value;
              return;
            }
          });
        }
      }
    },
    cellSavedPhieuDaGiao(arg) {
      let KEY = arg.rowData.MA_XLN;
      if (arg.columnName == "NO_LECH") {
        if (arg.value) {
          this.gridPhieuDaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_LECH = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "NO_HENTRA") {
        if (arg.value) {
          this.gridPhieuDaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.NO_HENTRA = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "TT_HS") {
        if (arg.value) {
          this.gridPhieuDaGiao.forEach((element) => {
            if (element.MA_XLN == KEY) {
              element.TT_HS = arg.value;
              return;
            }
          });
        }
      }
    },
    cellSavedDotLaySoLieu(arg) {
      let KEY = arg.rowData.STT;
      if (arg.columnName == "SL_CHUAGIAO") {
        if (arg.value) {
          this.gridDotLaySoLieu.forEach((element) => {
            if (element.STT == KEY) {
              element.SL_CHUAGIAO = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "SL_CHUAXL") {
        if (arg.value) {
          this.gridDotLaySoLieu.forEach((element) => {
            if (element.STT == KEY) {
              element.SL_CHUAXL = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "SL_DAXL") {
        if (arg.value) {
          this.gridDotLaySoLieu.forEach((element) => {
            if (element.STT == KEY) {
              element.SL_DAXL = arg.value;
              return;
            }
          });
        }
      } else if (arg.columnName == "SL_KETTHUC") {
        if (arg.value) {
          this.gridDotLaySoLieu.forEach((element) => {
            if (element.STT == KEY) {
              element.SL_KETTHUC = arg.value;
              return;
            }
          });
        }
      }
    },
    async btnExcelKQXL(e) {
      // this.$refs.dlgLoiGPXLN.openDialog();
      // if (this.gridPhieuDaGiao.length <= 0) {
      //   this.$toast.error("Danh sách phiếu đã giao không được để trống!");
      //   return;
      // }
      this.ktDocFile = true;
      const files = e.target.files;
      if (!files.length) {
        return;
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        return this.$toast.error("File excel không đúng định dạng!");
      }
      let dataExcel = [];
      const fileReader = new FileReader();
      fileReader.onload = async (ev) => {
        try {
          const data = ev.target.result;
          const workbook = xlsx.read(data, { type: "binary" });
          const wsname = workbook.SheetNames[0];
          const ws = xlsx.utils.sheet_to_json(workbook.Sheets[wsname]);

          //Validate input file:
          if (Object.keys(ws[0]).length <= 1) {
            this.$toast.error("File excel không đúng định dạng!");
            return;
          }
          for (const iterator of ExcelImport) {
            if (!ws[0].hasOwnProperty(iterator.fieldName)) {
              this.$toast.error(
                "Tên cột trong file không đúng định dạng: " + iterator.fieldName
              );
              return;
            }
          }

          if (ws.length > 0) {
            ws.forEach((item) => {
              dataExcel.push(item);
            });
          }
        } catch (e) {
          console.log(e);
          return this.$toast.error("Kiểm tra dữ liệu file đầu vào!");
        }
        if (dataExcel.length > 0) {
          this.$toast.success(`Có ${dataExcel.length} thành phần từ file`);
          this.showGPXLN(dataExcel);
        }
      };
      fileReader.readAsBinaryString(files[0]);
    },
    showGPXLN(dataExcel) {
      let ds = [...dataExcel];
      let dsLoi = [];
      let k = 1;
      let lstLydo = this.cboLyDoXL.map((item) => {
        return item.NOIDUNG_XLN_ID;
      });

      for (let index = 0; index < dataExcel.length; index++) {
        let ma_xln = dataExcel[index].MA_XLN;
        let lydodl_id = dataExcel[index].LYDO_ID;
        let ghichu = dataExcel[index].GHICHU;
        if (this.isNullEmpty(ma_xln)) {
          dsLoi.push({
            STT: k,
            MA_XLN: ma_xln,
            LYDO_ID: lydodl_id,
            GHICHU: ghichu,
            TRANGTHAI: "Thiếu Mã XLN!",
          });
          k++;

          continue;
        }
        if (this.isNullEmpty(lydodl_id)) {
          dsLoi.push({
            STT: k,
            MA_XLN: ma_xln,
            LYDO_ID: lydodl_id,
            GHICHU: ghichu,
            TRANGTHAI: "Thiếu Lý do XL!",
          });
          k++;
          dataExcel.splice(index, 1);
          index--;
          continue;
        }
        if (this.isNullEmpty(ghichu)) {
          dsLoi.push({
            STT: k,
            MA_XLN: ma_xln,
            LYDO_ID: lydodl_id,
            GHICHU: ghichu,
            TRANGTHAI: "Thiếu Ghi chú!",
          });
          k++;
          dataExcel.splice(index, 1);
          index--;
          continue;
        }
        //Kiểm tra lý do xử lý ID
        if (!lstLydo.includes(parseInt(lydodl_id))) {
          dsLoi.push({
            STT: k,
            MA_XLN: ma_xln,
            LYDO_ID: lydodl_id,
            GHICHU: ghichu,
            TRANGTHAI: "Lý do xử lý ID không đúng!",
          });
          k++;
          dataExcel.splice(index, 1);
          index--;
          continue;
        }
      }
      this.$refs.dlgLoiGPXLN.dtLoi = dsLoi;
      this.CheckMaTT(ds);
      this.$refs.dlgLoiGPXLN.dtDS_DaGiao = this.gridPhieuDaGiao;
      this.$refs.dlgLoiGPXLN.openDialog();
    },
    CheckMaTT(ds) {
      if (this.gridPhieuDaGiao.length <= 0) {
        return;
      }
      let kt = false;
      let lst = [...this.gridPhieuDaGiao];
      console.log("lst", lst);
      console.log("ds", ds);
      lst.forEach((item) => {
        let filter = ds.filter((e) => e.MA_XLN == item.MA_XLN);
        if (filter.length > 0) {
          item.CHON = "s1";
          item.NOIDUNG_XLN_ID = filter[0].LYDO_ID;
          item.GHICHU = filter[0].GHICHU;
          kt = true;
        } else {
          item.CHON = "s0";
          item.NOIDUNG_XLN_ID = null;
          item.GHICHU = "";
        }
      });
      this.gridPhieuDaGiao = lst;
      if (!kt) {
        this.$toast.error("File không có Mã XLN đã giao!");
      }
    },
    KT_ThuocTinh() {
      this.giatri = [];
      for (const item of this.dsControl) {
        if (item.LOAI_ID == 3) {
          this.giatri.push(
            this.formatDate(this.dsControlProp[item.THUOCTINH_BB])
          );
        } else if (item.LOAI_ID == 2) {
          this.giatri.push(this.dsControlProp[item.THUOCTINH_BB]);
        }
        this.kt_thuoctinh = true;
      }
    },
    HT_ThuocTinh(bienban_id) {
      if (this.kt_thuoctinh) {
        // kiểm tra nếu có thuộc tính => đẩy dữ liệu vào bảng thực hiện
        let lstThuocTinh = this.dtThuocTinh.filter(
          (e) => e.BIENBAN_ID == bienban_id
        );
        console.log("lstThuocTinh", lstThuocTinh);
        this.dsControl.forEach((control) => {
          let lst = lstThuocTinh
            .filter((e) => e.THUOCTINHBB_ID == control.THUOCTINHBB_ID)
            .map((e) => e.GIATRI);
          if (lst.length > 0) {
            if (item.LOAI_ID == 3) {
              try {
                var day = moment(lst[0], "DD/MM/YYYY");
                this.dsControlProp[control.THUOCTINH_BB] = day.toDate();
                this.setValueForDatePicker(control.THUOCTINH_BB, day.toDate());
              } catch (error) {
                console.log(error);
              }
            } else if (item.LOAI_ID == 2) {
              try {
                this.dsControlProp[control.THUOCTINH_BB] = lst[0];
              } catch (error) {
                console.log(error);
              }
            }
          }
        });
      }
    },
    setValueForDatePicker(property, date) {
      this.$refs[property][0].value = date;
    },
  },

  computed: {
    kyCuocHienHanhLocal() {
      return `${moment(new Date())
        .subtract(1, "months")
        .endOf("month")
        .format("YYYYMM")}${"01"}`;
    },
  },
  created() {
    EventBus.$on("chukycuocChange", this.chukycuocChangeHandler);
  },
  async mounted() {
    this.init();
    this.HT_COMBO_KIEUNO();
    this.cboLoaiKH = await (
      await this.HT_COMBO_LOAI_KH()
    ).sort(function (a, b) {
      return a.LOAIKH_ID - b.LOAIKH_ID;
    });
    this.cboLoaiKHValue = this.cboLoaiKH[0].LOAIKH_ID;
    this.cboLoaiKHDG = this.cboLoaiKH;
    this.cboLoaiKHDGValue = this.cboLoaiKHDG[0].LOAIKH_ID;
    this.cboDonviQL = await this.HT_COMBO_DONVI(this.$auth.getNhanVienID());
    if (this.cboDonviQL.length > 0) {
      this.cboDVQLDG = this.cboDonviQL;
      this.cboDonviQLValue = this.cboDonviQL[0].DONVI_ID;
      this.cboDVQLDGValue = this.cboDonviQL[0].DONVI_ID;
    }

    this.HT_COMBO_TRANGTHAI();
    this.dtQuyTrinhXLN = await this.getQuyTrinhNguoiDung();
    if (this.dtQuyTrinhXLN.length <= 0) {
      await this.$bvModal.msgBoxOk(
        "Bạn chưa gán quy trình xử lý nợ cho nhân viên này!",
        {
          title: "Thông báo",
          size: "lg",
          buttonSize: "lg",
          okVariant: "danger",
          okTitle: "Đồng ý",
          hideHeaderClose: false,
          centered: true,
          "modal-class": ["f18", "text-center"],
        }
      );
    }
    let arrLoaiNVId = this.dtQuyTrinhXLN
      .map((item) => {
        if (item.LOAINV_ID != null && item.LOAINV_ID != "") {
          return item.LOAINV_ID;
        } else {
          return null;
        }
      })
      .filter(function (e) {
        return e != null;
      });
    console.log(arrLoaiNVId.join());
    this.dtNhanVienXLN = await this.funcDSNhanVienXuLy(arrLoaiNVId.join());
    this.donviqlno_id = await this.funcDSDonViQLTheoQuayThu(
      this.$auth.getDonViID()
    );
    let data = await this.funcGetThongTinChuKyNo(this.kyCuoc);
    if (data.length > 0) {
      this.Ngay_BD_Gach = data[0].NGAY_BD;
      this.Ngay_KT_Gach = data[0].NGAY_KT;
    }
    this.selectedTab("tabPhieuChuaGiao");
    let isQuyen = await this.funcKiemTraQuyenNguoiDung(QUYEN.TOTRUONG_XLN);
    if (isQuyen) this.vtotruong = 1;
    this.kt_load = false;
  },

  destroyed() {
    EventBus.$off("chukycuocChange", this.chukycuocChangeHandler);
  },
};
</script>
