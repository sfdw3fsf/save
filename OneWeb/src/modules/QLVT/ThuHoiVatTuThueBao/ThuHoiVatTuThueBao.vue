<template src='./ThuHoiVatTuThueBao.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import ThuHoiVatTuThueBaoAPI from "../api/ThuHoiVatTuThueBaoAPI";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/thuhoivattuthuebao";
import select2 from "@/components/Select2.vue";
// import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { Query } from "@syncfusion/ej2-data";
// Vue.use(DropDownListPlugin);
import {
  Freeze,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
  ForeignKey,
} from "@syncfusion/ej2-vue-grids";
import { L10n } from "@syncfusion/ej2-base";
import frmNhapText from "../frmNhapText";
import ExportExcelCustom from "./utils/ExportExcel.js";
import { gridTBColumns, dsLoi } from "./common.js";
import frmGiaoPhieu_Ton from "../../contract/setup/Payment/components/TonPhieu.vue";
import frnGiaoPhieuNV from "@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue";
import InBienBan from "@/modules/print/InBienBan/InBienBan.vue";
import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from "@syncfusion/ej2-popups";

Vue.use(TooltipPlugin);
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});
var DialogResultObj = {
  None: 0,
  OK: 1,
  Cancel: 2,
  Abort: 3,
  Retry: 4,
  Ignore: 5,
  Yes: 6,
  No: 7,
};

export default {
  components: {
    frmNhapText,
    breadcrumb,
    appSelect2: select2,
    frmGiaoPhieu_Ton,
    frnGiaoPhieuNV,
    InBienBan,
  },
  name: "ThuHoiVatTuThueBao",
  mounted() {},
  provide: {
    grid: [Freeze, Group, Page, Filter, Sort, Resize, ForeignKey],
  },
  computed: {
    ...mapState("thuhoivattuthuebao", statePropertyName),
    ...mapGetters("thuhoivattuthuebao", getterName),
    vloaihd_id: {
      get() {
        return parseInt(this.params.p_LoaiHD);
      },
    },
    vdichvuvt_id: {
      get() {
        return parseInt(this.params.p_DichVuVT);
      },
    },
    vtu_ngay: {
      get() {
        return this.params.p_cbxTuNgay
          ? this.dateToString(this.params.p_dtpTuNgay, "DD/MM/YYYY")
          : "";
      },
    },
    vden_ngay: {
      get() {
        return this.params.p_cbxTuNgay
          ? this.dateToString(this.params.p_dtpDenNgay, "DD/MM/YYYY")
          : "";
      },
    },
    vphieu_id: {
      get() {
        return this.phieu_id_temp;
      },
      set(value) {
        return (this.phieu_id_temp = value);
      },
    },
    vphieu_cha_id: {
      get() {
        return this.phieu_cha_id_temp;
      },
      set(value) {
        return (this.phieu_cha_id_temp = value);
      },
    },
    vthuebao_id: {
      get() {
        return (
          (this.gridViewThueBao_FocusedRow &&
            parseInt(this.gridViewThueBao_FocusedRow.THUEBAO_ID)) ||
          0
        );
      },
    },
    vphieuvt_id: {
      get() {
        return (
          (this.grvPhieuVT_FocusedRow &&
            parseInt(this.grvPhieuVT_FocusedRow.PHIEUVT_ID)) ||
          0
        );
      },
    },
    vhdtb_id: {
      get() {
        return this.hdtb_id_temp;
      },
      set(value) {
        return (this.hdtb_id_temp = value);
      },
    },
    vserial: {
      get() {
        return this.params.p_txtSerial;
      },
    },
    vmucdich_id: {
      get() {
        return parseInt(this.params.p_LyDoCapTB);
      },
    },
  },
  data() {
    return {
      tooltip: Tooltip,
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      isKeySearch: false,
      isThuHoiRunning: false,
      paramsInBB: {},
      params: {
        p_ckbTKTinhtrangVT: false,
        p_TKTinhTrangVT: "",
        p_chkPhieuTon: false,

        p_LoaiHD: "",
        p_TrangThaiTH: "",
        p_DichVuVT: "",
        p_cbxTuNgay: true,
        p_dtpTuNgay: new Date(new Date().getTime() - 90 * 24 * 60 * 60 * 1000),
        p_dtpDenNgay: new Date(),
        p_ckbChonTK: false,
        p_txtSoThueBao: "",
        p_txtSoAo: "",
        p_txtTenTB: "",
        p_txtKieuLD: "",
        p_txtDiaChiLD: "",
        p_KieuTH: "",

        p_DVQL: "",
        p_DonVI: "",
        p_txtNoiDung: "",

        p_KetQuaXL: "",
        p_LyDoTon: "",
        p_txtGhiChuTon: "",
        p_LyDoCapTB: "",
        p_chkPhanHoi: false,
        p_PhanHoi: "",
        p_txtGhiChuHC: "",
        p_LyDoHuy: "",

        p_VatTu: "",
        p_LyDoCap: "",
        p_txtSerial: "",
        p_txtSoLuong: 1,
        p_TrangBi: "",
        p_TinhTrangTbi: "",
        p_txtGhiChu: "",
        p_rdioCKb: "2",
        p_lblThuHoi: "",
        p_idChung: "",
      },
      cboTKTinhTrangVT: [],
      cboLoaiHD: [],
      cboTrangThaiTH: [],
      cboDichVuVT: [],
      cboKieuTH: [],

      gridGiaoviec: [],
      cboDVQL: [],
      cboDonVI: [],

      cboKetQuaXL: [],
      cboLyDoTon: [],
      cboLyDoCapTB: [],
      cboPhanHoi: [],
      cboLyDoHuy: [],

      cboVatTu: [],
      CboLyDoCap: [],
      cboTrangBi: [],
      cboTinhTrangTbi: [],

      gridThueBao: [],
      grcPhieuVT: [],
      grvPhieuVT_FocusedRow: "",
      gridVatTu: [],
      // grcSudungVT: [],
      grcSuDungVT: [],

      tt_ten_vt: "",
      thuebao_id: "",
      dt_vattu: [],
      ds_lydoTC: [],
      dslh_md: [],
      ds_error: [],
      dt_kt_canhbao: [],
      vsdvt_id: 0,
      loaihd_id: "",
      vkieu_km: 0,
      kieu_goi: 0,
      matb: "",
      phieu_id_temp: "",
      phieu_cha_id_temp: "",
      hdtb_id_temp: "",
      check_serial: "",
      vma_tb_cur: "",
      vloaitb_id: "",
      phvt_id: "",
      sl_goc: "",
      vloaitbi_id: 0,
      canhbao_sl_thuhoi_vt: false,
      gridViewThueBao_FocusedRow: null,
      check_serial_gp: false,
      ma_tb_cur: "",

      Visible: {
        colSua: false,
        layoutControlGroup4: false,
        colSR_GP: false,
      },
      Enabled: {
        chkPhanHoi: true,
        chkPhieuTon: false,

        cboTKTinhTrangVT: false,
        cboPhanHoi: false,
        cboVatTu: false,
        cboLyDoCapTB: false,
        cboTinhTrangTbi: false,
        cboLyDoTon: false,
        cboLyDoHuy: false,

        btnThuHoi: false,
        btnHoanCong: false,
        btnNhapMoi: false,

        tsbtnTon: false,
        txtGhiChuHC: false,
        txtGhiChuTon: false,
        txtSerial: true,
        txtSoLuong: true,

        dtpTuNgay: false,
        dtpDenNgay: false,
        TT_THUEBAO_TAG: true,
        TT_VATTU_TAG: true,
      },
      Text: {
        ckbChonTK: "Số máy/Acc",
        label3: "Lý do tồn",
        lblThongTinVatTuThuHoi: "Thông tin vật tư thu hồi",
      },

      LoaiHopDong: {
        KHONG_DUNG: 0,
        DAT_MOI: 1,
        CHUYEN_QUYEN: 2,
        DI_CHUYEN: 3,
        CHAMDUT_SD: 4,
        THAYDOI_THONGSO_MEGAWAN: 5,
        CHUYENDOI_LH: 6,
        THAY_DOI_DV: 7,
        THAY_DOI_TOCDO_ADSL: 8,
        THAY_DOI_SOMAY_TN: 9,
        TACHNHAP_TB: 10,
        THAY_TT_KHAC: 11,
        THAY_DOI_SO_SIM: 12,
        THUEBAO_TRATRUOC: 13,
        THAY_DAY_MAY: 13,
        VATTU_THUEBAO: 13,
        DOISO_TB: 14,
        BAN_THIETBI: 15,
        THAY_DOI_TOCDO_TSL: 16,
        THU_KHAC: 17,
        KHAO_SAT: 18,
        TDTT_DV_CNTT: 19,
        GHEP_MA_KHACHHANG: 20,
        KY_LAI_HD_GOC: 24,
        TAMDUNG_SD: 25,
        THAYDOI_THONGSO_MYTV: 25,
        TIEPNHAN_DATMOI: 26,
        DOIGOICUOC_DD: 27,
        TIEPNHAN_CDLH: 28,
        TIEPNHAN_DC: 29,
        KHOIPHUC_SD: 30,

        DANGKY_GOI_DADV: 27,
        THAYDOI_GOI_DADV: 28,

        DATCOC_MOI: 31,
        THAYDOI_DATCOC: 32,

        KHAOSATDATMOI: 33,
        KHAOSATCDLH: 34,
        KHAOSATDC: 35,
        TIEPNHANHD: 36,
        THUCUOC_THUHOIVT: 37,
        TUVAN_DV: 38, //phối hợp tư vấn dịch vụ giữa NVKT và TTKD
        THAYDOI_TT_DD: 100, // vuth them - thay doi thoong tin di dong

        DD_SIPTRUNKING: 40, // Taita thêm đăng ký dịch vụ SIPTRUNKING
        DD_HUYSTKING: 42, // Hủy dịch vụ Siptrunking di động
        DD_DKVCC: 43, //  Đăng ký dịch vụ VCC di động
        DD_HUYVCC: 44, //  Hủy dịch vụ VCC di động
        DD_TAMDUNGVCC: 45, //  Tạm dừng dịch vụ VCC di động
        DD_KICHVCC: 46, //   Kích hoạt lại dịch vụ VCC di động
        DD_CAPNHATGOI: 47, // Cập nhật gói VCC
        DD_XOAGOI: 48, // Xóa gói VCC
        DD_CAPNHATDATA: 49, // Thêm mới dịch vụ data
        DD_XOADATA: 50, // Xóa data VCC

        DD_HSSV: 101, // vuth update before 1509
        DD_DOANVIEN: 102, // vuth update before 1509
        DK_MULTISIM: 39, // vuth them 27/08/2020
        GIAHAN: 41, // vuth them 27/08/2020
      },
      LOAI_DV: {
        DONVIQL_LD: 3,
        VETINH: 5,
      },
      LoaiHinhTB: {
        INTERNET_MYTV: 61,
      },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      templateOptions: {
        create: function (args) {
          if (["STT", "btnXoaVT", "btnEditTbi"].includes(args.column.field)) {
            return '<span class="fa input-icon-right"></span>';
          }

          return (
            '<div class="input-icon-right"><input id="' +
            args.column.field +
            '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>'
          );
        },
        write: function (args) {},
      },
      cRowSelected: function () {
        return {
          template: Vue.component("columntemplate", {
            template: '<span class="fa"></span>',
          }), // end of template
        };
      },
      editSettings: { allowEditing: true },
      selectionSettings: { type: "Single", checkboxOnly: true },
      XoaVTTemplate: function () {
        return {
          template: Vue.component("XoaVTTemplate", {
            template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoaVT_ButtonClick">
                                  <span class="-ap icon-close"></span>
                              </button>`,
            data: function () {
              return { data: {} };
            },
            methods: {
              async btnXoaVT_ButtonClick() {
                await this.$parent.$parent.$parent.$parent.$parent.btnXoaPhieu_PhieuVT_ButtonClick(
                  this.data
                );
              },
            },
          }),
        };
      },
      rowCheckedTB: [],
      CheckboxTB: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"                                    
                                    v-model="parent.rowCheckedTB"
                                    :value="data.HDTB_ID"/>
                                <span class="name"></span>
                            </div>
                        `,
            data() {
              return {
                data: {},
              };
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      checkAllTB: false,
      CheckboxAllTB: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox" v-model="parent.checkAllTB" class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      gridThueBaoPage: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      gridGiaoviecPage: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      toggleInfo: true,

      grcSuDungVTPage: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      grcPhieuVTPage: {
        page: 1,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },

      grcSuDungVT_Columns: [
        {
          fieldName: "btnThuHoi_SuDungVT",
          headerText: "Thu hồi",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
          freeze: "Left",
          template: function () {
            return {
              template: Vue.component("btnThuHoi_SuDungVTTemplate", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnThuHoi_SuDungVT_ButtonClick">
                                  <span class="fa fa-angle-up"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                methods: {
                  async btnThuHoi_SuDungVT_ButtonClick() {
                    await this.$parent.$parent.$parent.$parent.$parent.btnThuHoi_SuDungVT_ButtonClick(
                      this.data
                    );
                  },
                },
              }),
            };
          },
          width: "70",
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên thiết bị",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
          freeze: "Left",
        },
        {
          fieldName: "SERIAL",
          headerText: "Serial",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "SERIAL_GP",
          headerText: "Serial Gp",
          textAlign: "Left",
          allowFiltering: true,
          visible: false,
        },
        {
          fieldName: "MA_TBI",
          headerText: "Mã thiết bị",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          textAlign: "Right",
          allowFiltering: true,
          visible: true,
        },
        {
          fieldName: "DVI_TINH",
          headerText: "Đơn vị tính",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
        },
        {
          fieldName: "TRANGBI",
          headerText: "Trang bị",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
        },
        {
          fieldName: "TT_VATTU",
          headerText: "Tình trạng",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
        },
        {
          fieldName: "colSua",
          headerText: "Sửa",
          textAlign: "Left",
          allowFiltering: true,
          visible: true,
          template: function () {
            return {
              template: Vue.component("btnEdit_Template", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnEdit" @click="btnEdit_ButtonClick">
                                  <span class="fa fa-pencil-square"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                computed: {
                  btnEdit: {
                    get() {
                      if (
                        (!this.data["PHIEUVT_ID"] ||
                          this.data["PHIEUVT_ID"] == "0") &&
                        this.data["SERIAL"]
                      ) {
                        return true;
                      }
                      return false; // true ->test
                    },
                  },
                },
                methods: {
                  async btnEdit_ButtonClick() {
                    await this.$parent.$parent.$parent.$parent.$parent.btnEdit_ButtonClick(
                      this.data
                    );
                  },
                },
              }),
            };
          },
          freeze: "Right",
          width: "70",
        },
      ],
      paramThuHoiSTB2515: {
        user_name: "", // tài khoản Mytv
        stb_serial: "",
        stb_serial_new: "",
        stb_type: "",
        date: "",
        note: "",
        isback: false,
        resue: false,
        notback_reason: "",
        not_resuse_dame: false,
        not_resue_reason: "",
        tinhtrang: "",
        k: "",
      },
      rdioShow_PhieuThuHoi: false
    };
  },
  methods: {
    ...mapActions("thuhoivattuthuebao", actionName),
    ...mapMutations("thuhoivattuthuebao", mutationName),

    // chuyển Date thành type string
    dateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    cboVatTuFiltering(e) {
      if (this.cboVatTu.length) {
        var searchData = this.cboVatTu;
        var query = new Query();
        query =
          e.text != ""
            ? query.where("tenVatTu", "contains", e.text, true)
            : query;
        e.updateData(searchData, query);
      }
    },
    async gridThueBaoPageHandler(e) {
      this.gridThueBaoPage.page = e.pageIndex + 1;
      this.gridThueBaoPage.maxSize = e.pageSize;
      if (!this.isKeySearch) await this.HIENTHI_DANHSACH_THUEBAO("");
      this.isKeySearch = false;
    },
    gridGiaoviecPageHandler(e) {
      this.gridGiaoviecPage.page = e.pageIndex;
      this.gridGiaoviecPage.maxSize = e.pageSize;
    },
    grcSuDungVTPageHandler(e) {
      this.grcSuDungVTPage.page = e.pageIndex;
      this.grcSuDungVTPage.maxSize = e.pageSize;
    },
    grcPhieuVTPageHandler(e) {
      this.grcPhieuVTPage.page = e.pageIndex;
      this.grcPhieuVTPage.maxSize = e.pageSize;
    },

    async NAP_COMBOBOX() {
      if (this.$auth.getMaTinh() == "VPC" || this.$auth.getMaTinh() == "HYN") {
        this.Visible.colSua = false;
        this.grcSuDungVT_Columns.find(
          (a) => a.fieldName == "colSua"
        ).visible = false;
      }
      this.cboPhanHoi = await this.getDSPhanHoiKH({
        loaiTbId: 0,
        loaiHdId: this.LoaiHopDong.THUCUOC_THUHOIVT,
      });
      this.params.p_PhanHoi = this.setFirstValue(this.cboPhanHoi, "KIEULD_ID");
      // trạng thái TH
      this.cboTrangThaiTH = [
        { ID: "0", TT_DIEU: "Tất cả" },
        { ID: "1", TT_DIEU: "Thu hồi được" },
        { ID: "2", TT_DIEU: "Không thu hồi được" },
      ];
      this.params.p_TrangThaiTH = this.setFirstValue(this.cboTrangThaiTH, "ID");
      // Kiểu thu hồi
      this.cboKieuTH = [
        { ID: "0", TT_DIEU: "Tất cả" },
        { ID: "1", TT_DIEU: "Thu hồi tại giao dịch" },
        { ID: "2", TT_DIEU: "Bắt buộc thu hồi" },
        { ID: "3", TT_DIEU: "Không bắt buộc thu hồi" },
      ];
      this.params.p_KieuTH = this.setFirstValue(this.cboKieuTH, "ID");
      // Nạp combobox Loại HĐ/PL
      this.cboLoaiHD = await this.getDSLoaiHopDong({});
      this.params.p_LoaiHD = this.setFirstValue(this.cboLoaiHD, "loaiHdId");
      // Nạp combobox Dịch vụ viễn thông
      this.cboDichVuVT = await this.getDSDVVienThong({});
      this.params.p_DichVuVT = this.setFirstValue(
        this.cboDichVuVT,
        "dichVuVTId"
      );
      // Nạp combobox Kết quả thu hồi
      this.cboKetQuaXL = [
        { KQ_ID: "0", KETQUA: "" },
        { KQ_ID: "1", KETQUA: "Thu hồi được" },
        { KQ_ID: "3", KETQUA: "Không thu hồi được" },
      ];
      this.params.p_KetQuaXL = this.setFirstValue(this.cboKetQuaXL, "KQ_ID");
      // Nạp combobox Lý do thu hồi TBĐC
      this.cboLyDoCapTB = await this.getDSMucdichLyDoThuHoi({ nhomMdId: 7 });
      this.params.p_LyDoCapTB = this.setFirstValue(
        this.cboLyDoCapTB,
        "MUCDICH_ID"
      );
      // Nạp combobox tình trạng thiết bị, tình trạng phụ kiện
      this.cboTinhTrangTbi = await this.getDSTinhTrangTbi({});
      this.params.p_TinhTrangTbi = this.setFirstValue(
        this.cboTinhTrangTbi,
        "tinhTrangTBiId"
      );
      // Nạp combobox vật tư
      this.dt_vattu = await this.getDSThietBiVatTuThuHoi({});
      this.cboVatTu = this.dt_vattu;
      this.params.p_VatTu = this.setFirstValue(this.cboVatTu, "vatTuId");
      // Nạp combobox trang bị vật tư
      this.ds_ktbi = await this.getDSKieuTrangBi();
      this.cboTrangBi = this.ds_ktbi;
      this.params.p_TrangBi = this.setFirstValue(this.cboTrangBi, "trangBiId");
      // Nạp CboLyDoCap
      this.CboLyDoCap = await this.getDSLyDoThuHoi({ nhomMdIdArr: [7, 8] });
      this.params.p_LyDoCap = this.setFirstValue(this.CboLyDoCap, "mucDichId");
      // Nạp combo  ly do huỷ
      this.cboLyDoHuy = await this.getDSLyDoHuy({});
      this.params.p_LyDoHuy = this.setFirstValue(this.cboLyDoHuy, "lyDoHuyId");
      // Nap combo donvi ql
      this.cboDVQL = await this.getDSDonViQuanLy({
        loaiDviId: [this.LOAI_DV.DONVIQL_LD],
      });
      this.params.p_DVQL = this.setFirstValue(this.cboDVQL, "donViId");
      await this.cboDVQL_EditValueChanged();

      this.ds_lydoTC = await this.getDSMucdichLyDoThuHoi({ nhomMdId: 6 });
      this.dslh_md = await this.getDSMucDichLoaiHopDong({});
      // tabbedControlGroup1.SelectedTabPage = layoutControlGroup3
      if (this.$auth.getMaTinh() != "HNI" && this.$auth.getMaTinh() != "HCM") {
        this.Visible.layoutControlGroup4 = false;
      }
      // Lấy TS mặc định
      var ds = await this.getDSThamSoMacDinh({ kieuId: 0 });
      if (ds.length) {
        let str_vtemp = "KIEMTRA_KYTU_SERIAL";
        let det = ds.filter((a) => a.maTS == str_vtemp);
        if (det.length) {
          let row = det[0];
          if (row.tenTS != "0") {
            this.check_serial = row.tenTS;
          }
        }

        str_vtemp = "KIEMTRA_TH_SERIAL_GP";
        det = ds.filter((a) => a.maTS == str_vtemp);
        if (det.length) {
          let row = det[0];
          if (row.tenTS != "0") {
            this.check_serial_gp = true;
          }
        }

        str_vtemp = "CANHBAO_SL_THUHOI_VT_MENUTHUHOI";
        det = ds.filter((a) => a.maTS == str_vtemp);
        if (det.length) {
          let row = det[0];
          if (row.tenTS == "1") {
            this.canhbao_sl_thuhoi_vt = true;
          }
        }
      }

      this.cboTKTinhTrangVT = await this.getDSTinhTrangTbi({});
      this.params.p_TKTinhTrangVT = this.setFirstValue(
        this.cboTKTinhTrangVT,
        "tinhTrangTBiId"
      );
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    async HIENTHI_DANHSACH_THUEBAO(strma_tb) {
      try {
        this.$root.loading(true);
        this.gridGiaoviec = [];
        this.grcPhieuVT = [];
        if (this.params.p_ckbChonTK) {
          let rk = this.params.p_rdioCKb;
          // try {
          if (this.params.p_txtSoThueBao) {
            let ztb = await this.getDSMaTBMaHGThuHoi({
              serial: this.params.p_txtSoThueBao,
            });
            if (ztb.length) {
              strma_tb = ztb[0].MA_TB;
              if (ztb.some((a) => a.MA_HG == "DUYET_THUHOIVT")) {
                if (ztb[0].NGAYTRA_CON == null) {
                  this.params.p_rdioCKb = "2";
                } else this.params.p_rdioCKb = "3";
              } else {
                this.params.p_rdioCKb = "1";
              }
              // await xy()
            }
          }
          // } catch (error) {
          //   strma_tb = this.params.p_txtSoThueBao
          //   this.params.p_rdioCKb = rk
          // }
        }

        let vton = 0;
        this.params.p_chkPhieuTon ? (vton = 1) : "";
        if (this.params.p_KieuTH == "0") {
          let res = await this.getDSThueBaoThuHoiVT_V1({
            nhomId: 1,
            dichVuVtId: this.vdichvuvt_id,
            loaiHdId: this.vloaihd_id,
            maTb: strma_tb,
            tuNgay: this.vtu_ngay,
            denNgay: this.vden_ngay,
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: parseInt(this.params.p_rdioCKb),
            ton: vton,
            tkVt: this.params.p_ckbTKTinhtrangVT ? 1 : 0,
            tinhTrangVt: parseInt(this.params.p_TKTinhTrangVT),
            // pageNum: this.gridThueBaoPage.page,
            // pageSize: this.gridThueBaoPage.maxSize,
            // totalRow: 1,
          });
          await this.Setting_gridThueBaoPage(res);
        } else {
          let res = await this.getDSThueBaoThuHoiVT_V2({
            nhomId: 1,
            dichVuVtId: this.vdichvuvt_id,
            loaiHdId: this.vloaihd_id,
            maTb: strma_tb,
            tuNgay: this.vtu_ngay,
            denNgay: this.vden_ngay,
            nhanVienId: this.$auth.getNhanVienID(),
            kieu: parseInt(this.params.p_rdioCKb),
            ton: vton,
            kieuTh: parseInt(this.params.p_KieuTH),
            // pageNum: this.gridThueBaoPage.page,
            // pageSize: this.gridThueBaoPage.maxSize,
            // totalRow: 1,
          });
          await this.Setting_gridThueBaoPage(res);
        }
        await this.LOC_DS_THUHOI();
        this.$root.showLoading(false);
        if (this.gridThueBao.length) {
        } else {
          await this.KHOAMO_TT_THUEBAO(false);
          await this.KHOAMO_TT_VATTU(false);
          this.grcSuDungVT = [];
          this.grcPhieuVT = [];
          this.gridVatTu = [];
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async Setting_gridThueBaoPage(res) {
      if (Object.keys(res).length) {
        this.gridThueBao = res.data;
        this.rowCheckedTB = [];
        this.checkAllTB = false;
        //  this.gridThueBaoPage.page = res.page;
        //    this.gridThueBaoPage.maxSize = res.maxSize;
        // this.gridThueBaoPage.totalElement = res.totalElement;
        //  this.gridThueBaoPage.totalPages = res.totalPages;
      }
    },
    async Load_GridThueBao() {},
    async KHOAMO_TT_THUEBAO(value) {
      this.Enabled.TT_THUEBAO_TAG = value;
    },
    async HIENTHI_TT_THUEBAO(row) {
      this.params.p_chkPhanHoi = false;
      this.Enabled.chkPhanHoi = false;
      this.Enabled.cboPhanHoi = false;
      this.vma_tb_cur = "";
      this.vloaitb_id = "";
      if (!this.params.p_ckbChonTK) {
        this.params.p_txtSoThueBao = row.MA_TB;
      }
      this.params.p_txtTenTB = row.TEN_TB;
      this.params.p_txtDiaChiLD = row.DIACHI_LD;
      this.params.p_txtKieuLD = row.TEN_KIEULD;
      this.params.p_txtSoAo = row.MA_LT;
      this.vma_tb_cur = row.MA_TB;
      this.thuebao_id = row.THUEBAO_ID;
      this.vsdvt_id = 0;
      this.loaihd_id = row.LOAIHD_ID;
      this.vloaitb_id = row.LOAITB_ID;
      this.params.p_lblThuHoi = "";
      let ds_thuhoi = await this.KiemTraThietBiThuHoi({
        hdTbId: this.vhdtb_id,
      });
      this.dt_kt_canhbao = ds_thuhoi;

      if (ds_thuhoi.length) {
        if (ds_thuhoi[0].kqXlId && ds_thuhoi[0].kqXlId == "3") {
          this.params.p_lblThuHoi = "Đã thu hồi thiết bị tại giao dịch !";
        }
      }

      this.vkieu_km = 0;
      if (this.loaihd_id == this.LoaiHopDong.THAY_DOI_DV) {
        let ds_quytrinh = await this.getDSKieuYeuCau({ hdTbId: this.vhdtb_id });
        if (ds_quytrinh.length) {
          if (ds_quytrinh[0].kieuYc == "1") {
            this.vkieu_km = 1;
            this.Enabled.chkPhanHoi = true;
            this.params.p_chkPhanHoi = false;
            this.Enabled.cboPhanHoi = false;
          }
        }
      } else {
        this.Enabled.chkPhanHoi = false;
        this.params.p_chkPhanHoi = false;
        this.Enabled.cboPhanHoi = false;
      }
      await this.NAP_DS_SUDUNG_VT();
      await this.NAP_DS_PHIEU_VT();
    },
    async NAP_DS_SUDUNG_VT() {
      try {
        this.$root.loading(true);
        var dt = await this.getDSSuDungVatTuTBao({
          thueBaoId: this.vthuebao_id,
        });
        if (dt.length && dt[0].hasOwnProperty("SERIAL_GP")) {
          this.Visible.colSR_GP = true;
          this.grcSuDungVT_Columns.find(
            (e) => e.fieldName == "SERIAL_GP"
          ).visible = true;
        }
        this.grcSuDungVT = dt;
      } catch (error) {
        this.$toast.error(
          "Lỗi khi lấy danh sách vật tư đã cấp cho thuê bao\r\n"
        );
      } finally {
        this.$root.loading(false);
      }
    },
    async KHOAMO_TT_VATTU(mo) {
      this.Enabled.txtSerial = mo;
      this.params.p_txtSerial = "";
      this.Enabled.txtSoLuong = mo;
      this.params.p_txtSoLuong = "";
      this.Enabled.btnThuHoi = mo;
      this.Enabled.cboVatTu = this.vsdvt_id == 0;
      this.params.p_VatTu = "";
      this.$refs.cboVatTu.clear();
      if (
        (this.params.p_KetQuaXL == "1" && this.grcPhieuVT.length) ||
        this.params.p_KetQuaXL == "3"
      ) {
        this.Enabled.btnHoanCong = true;
        this.Enabled.txtGhiChuHC = true;
      } else {
        this.Enabled.btnHoanCong = false;
        this.Enabled.txtGhiChuHC = false;
      }
      this.Enabled.cboLyDoCapTB = this.grcPhieuVT.length > 0;
      this.Enabled.cboTinhTrangTbi = true;
      this.Enabled.btnNhapMoi = true;
    },
    async CHON_SUDUNG_VT(row) {
      try {
        this.params.p_txtSerial = row.SERIAL;
        this.params.p_VatTu = parseInt(row.VATTU_ID);
        this.params.p_TrangBi = parseInt(row.TRANGBI_ID);
        // cboTinhTrangTbi.ItemIndex = -1;
        this.params.p_txtSoLuong = row.SOLUONG;
        this.params.p_txtGhiChu = "";
        this.Enabled.btnNhapMoi = false;
        this.vsdvt_id = parseInt(row.SDVT_ID) || 0;
      } catch (error) {
        console.log(error);
      }
    },
    async NAP_DS_PHIEU_VT() {
      try {
        this.$root.loading(true);
        if (this.params.p_rdioCKb == "1") {
          this.grcPhieuVT = await this.getDSPhieuThuHoiVTTB({
            phieuId: this.vphieu_id,
            hdTbId: this.vhdtb_id,
          });
          this.gridVatTu = await this.getDSVatTuGiaoDichThuHoi({
            hdTbId: this.vhdtb_id,
          });
        } else {
          this.grcPhieuVT = await this.getDSPhieuThuHoiVTTB({
            phieuId: this.vphieu_cha_id,
            hdTbId: this.vhdtb_id,
          });
          this.gridVatTu = await this.getDSVatTuGiaoDichThuHoi({
            hdTbId: this.vhdtb_id,
          });
        }
        if (!this.grcPhieuVT.length) {
          await this.KHOAMO_TT_VATTU(false);
        }
      } catch (error) {
        this.$toast.error(
          "Lỗi khi lấy danh sách vật tư đã cấp cho thuê bao\r\n" + error.message
        );
      } finally {
        this.$root.loading(false);
      }
    },
    async CHON_PHIEU_VT(row) {
      try {
        this.params.p_txtSerial = row.SERIAL;
        this.params.p_VatTu = parseInt(row.VATTU_ID);
        this.params.p_txtGhiChu = row.GHICHU;
        this.params.p_txtSoLuong = row.SOLUONG;
        this.sl_goc = parseInt(row.SOLUONG);
        this.phvt_id = parseInt(row.PHIEUVT_ID);
        this.params.p_TinhTrangTbi = row.TINHTRANGTBI_ID;
      } catch (error) {
        console.log(error);
      }
    },
    async KiemTraSerial(serial) {
      // Kiem tra serial thay đổi có trùng không
      if (serial == this.params.p_txtSerial) {
        this.$toast.error("Serial thay đổi trùng với serial thu hồi ");
        return -1;
      }
      // Lấy danh sách thiết bị đã cấp theo serial mới
      let dt_ckb = await this.getDSThietBiDaCapTheoSerialMoi({
        serial: serial,
      });
      if (dt_ckb.length) {
        this.$toast.error(
          "Serial " +
            serial +
            " đã được cấp vào ngày " +
            dt_ckb[0].NgayCap +
            " bởi user " +
            dt_ckb[0].NguoiCn
        );
        return -1;
      }
      //Kiểm tra xem serial mới này đã có thuê bao nào dùng chưa
      dt_ckb = await this.KiemTraSerialMoiTBaoSuDung({
        serial: serial,
      });
      if (dt_ckb.length) {
        this.$toast.error(
          "Serial " +
            serial +
            " đã được sử dụng bởi thuê bao " +
            dt_ckb[0]["maThueBao"] +
            ". Bạn hãy điền serial khác"
        );
        return -1;
      }
      //Lấy trong ds thiet bị
      let dt_ = await this.getDSThietBiTheoSerial({
        serial: serial,
      });
      if (dt_.length && dt_[0]["slTon"] == "1" && dt_[0]["tonKho"] == "1") {
        this.$toast.error("Serial này đang ở kho " + dt_[0]["maTenKho"]);
        return -1;
      }
      if (
        (dt_.length > 0 && dt_[0]["slTon"] == "0" && dt_[0]["tonKho"] == "1") ||
        (dt_.length > 0 && dt_[0]["tonKho"] == "0")
      ) {
        return parseInt(dt_[0]["thietBiId"]);
      } else if (
        dt_.Count > 0 &&
        dt_[0]["slTon"] == "1" &&
        dt_[0]["tonKho"] == "1"
      ) {
        this.$toast.error("Serial này đang ở kho " + dt_[0]["maTenKho"]);
        return -1;
      } else {
        return 0;
      }
    },
    //ko sử dụng
    async CAPNHAT() {
      // try {
      let sd = 0;
      // Kiểm tra dữ liệu
      var vthietbi_id = 0;
      var vt_id = 0;
      var s_ri = "";
      let vkieu = 3; // 1: Có trong bảng sử dụng vật tư, 2: Có trong bảng thiết bị, ko có trong sử dụng, 3: thiết bị mới

      if (this.vthuebao_id <= 0) {
        this.$toast.error("Bạn phải chọn thuê bao");
        return;
      }
      if (!this.params.p_TinhTrangTbi) {
        this.$toast.error("Hãy chọn tình trạng thiết bị");
        this.$refs.cboTinhTrangTbi.focusIn();
        return;
      }

      if (!parseInt(this.params.p_txtSoLuong)) {
        this.$toast.error("Hãy nhập số lượng thiết bị");
        this.$refs.txtSoLuong.focus();
        return;
      }

      if (!this.params.p_VatTu) {
        this.$toast.error("Hãy nhập tên thiết bị");
        this.$refs.cboVatTu.focusIn();
        return;
      }

      if (this.params.p_txtSoLuong.match(/[\d]+/g)) {
        this.$toast.error("Số lượng thiết bị nhập vào không đúng");
        this.$refs.txtSoLuong.focus();
        return;
      }
      // Kiểm tra nếu loại thiết bị là seri
      var r = this.dt_vattu.filter((l) => l.vatTuId == this.params.p_VatTu);
      if (r.length) {
        if (r["loaiThietBiId"] == "2") {
          if (!this.params.p_txtSerial) {
            this.$toast.error(
              "Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị"
            );
            this.$refs.txtSerial.focus();
            return;
          }

          if (this.params.p_txtSerial) {
            if (parseInt(this.params.p_txtSoLuong) != 1) {
              this.$toast.error("Vật tư này có seri, số lượng phải nhập = 1");
              this.$refs.txtSoLuong.focus();
              return;
            }
          }
          let dt_temp = [];
          if (!this.Enabled.btnNhapMoi) {
            dt_temp = await this.getDSThietBiDaCapTheoSerialMoi({
              serial: this.params.p_txtSerial,
            });
            if (dt_temp.length) {
              this.$toast.error(
                "Serial " +
                  this.params.p_txtSerial +
                  " đã được cấp vào ngày " +
                  dt_temp[0]["NgayCap"] +
                  " bởi user " +
                  dt_temp[0]["NguoiCn"]
              );
              return;
            }

            // Kiểm tra trong bảng sử dụng xem có thuê bao nào đang sử dụng hay ko
            dt_temp = await this.KiemTraSerialMoiTBaoSuDung({
              serial: this.params.p_txtSerial,
            });
            if (dt_temp.length) {
              s_ri = this.params.p_txtSerial;
              //  thue bao khac nhau
              if (parseInt(dt_temp[0]["thietBiId"]) != this.thuebao_id) {
                if (parseInt(dt_temp[0]["phieuVatTuId"]) != 0) {
                  // Kiemes tra duoc phep sua khong
                  this.$toast.error(
                    "Serial " +
                      this.params.p_txtSerial +
                      " đã được sử dụng bởi thuê bao " +
                      dt_temp[0]["maThueBao"] +
                      ". Bạn không có quyền được thu hồi"
                  );
                  return;
                }
                await this.$bvModal
                  .msgBoxConfirm(
                    "Serial " +
                      this.params.p_txtSerial +
                      " đã được sử dụng bởi thuê bao " +
                      dt_temp[0]["maThueBao"] +
                      ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
                    {
                      okVariant: "success",
                      okTitle: "Đồng ý",
                      cancelTitle: "Hủy",
                      centered: true,
                    }
                  )
                  .then(async (value) => {
                    if (value) {
                      this.vsdvt_id = 0;
                      let ds = await this.getDSVatTu({});
                      // var frm = new frmNhapText("Serial thu hồi: " + txtSerial.Text + ". Nhập serial mới nếu serial thu hồi sai", "Tên vật tư", ds);
                      // frm.cboVatTu.EditValue = cboVatTu.EditValue;
                      // frm.Text = "Đổi serial";
                      // frm.btnXacNhan.Text = "Đổi";
                      // frm.btnHuyBo.Text = "Không đổi";
                      // if (frm.ShowDialog() == DialogResult.OK)// nếu thay đổi
                      // else
                      // Nếu không thay đổi serial
                    }
                  })
                  .catch((err) => {
                    // An error occurred
                  });
              }

              // Neu dung thue bao
              let dt_tbi = await this.getDSThietBiTheoSerial({
                serial: this.params.p_txtSerial,
              });
              if (
                dt_tbi.length > 0 &&
                dt_tbi[0]["slTon"] == "1" &&
                dt_tbi[0]["tonKho"] == "1"
              ) {
                this.$toast.error(
                  "Serial này đang ở kho " + dt_tbi[0]["maTenKho"] + ""
                );
                return;
              }
              sd = parseInt(dt_temp[0]["suDungVtId"]);
              vkieu = 1;
            } else {
              dt_temp = await this.getDSThietBiTheoSerial({
                serial: this.params.p_txtSerial,
              });
              if (
                (dt_temp.length > 0 &&
                  dt_temp[0]["slTon"] == "0" &&
                  dt_temp[0]["tonKho"] == "1") ||
                (dt_temp.length > 0 && dt_temp[0]["tonKho"] == "0")
              ) {
                //neuton tại trong thiet bi
                vkieu = 2;
                vthietbi_id = parseInt(dt_temp[0]["thietBiId"]);
              } else if (
                dt_temp.length > 0 &&
                dt_temp[0]["slTon"] == "1" &&
                dt_temp[0]["tonKho"] == "1"
              ) {
                this.$toast.error(
                  "Serial này đang ở kho " + dt_temp[0]["maTenKho"] + ""
                );
                return;
              } else {
                vkieu = 3;
                await this.$bvModal
                  .msgBoxConfirm(
                    "Không tồn tại serial trong hệ thống. Bạn có muốn tiếp tục?",
                    {
                      okVariant: "success",
                      okTitle: "Đồng ý",
                      cancelTitle: "Hủy",
                      centered: true,
                    }
                  )
                  .then(async (value) => {
                    if (!value) {
                      return;
                    }
                  })
                  .catch((err) => {
                    // An error occurred
                  });
              }
            }
          }
        } else {
          if (this.params.p_txtSerial) {
            this.$toast.error(
              "Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial"
            );
            this.$refs.txtSerial.focus();
            // txtSerial.SelectAll();
            return;
          }
        }
      }
      if (this.Enabled.btnNhapMoi) {
        if (this.vphieuvt_id == 0) {
          this.$toast.error("Chưa có thông tin về phiếu vật tư! ");
          return;
        }
      }

      let boqua = false;
      var z = await this.getDSThaoTacTheoCode({ code: "BQ_GV_THUHOI" });
      if (z.length) {
        boqua = true;
      }
      if (!this.gridGiaoviec.length && boqua == false) {
        this.$toast.error("Bạn chưa giao việc cho nhân viên!");
        return;
      }

      async function Final() {
        let pchung_id = this.vphieu_cha_id;
        if (this.params.p_rdioCKb == "1") {
          pchung_id = this.vphieu_id;
        }
        let sdchung;
        if (sd != 0) {
          sdchung = sd;
        } else {
          sdchung = this.vsdvt_id;
        }
        let sr_chung;
        if (s_ri != "") {
          sr_chung = s_ri;
        } else {
          sr_chung = this.params.p_txtSerial;
        }
        let vtchung_id;
        if (vt_id != 0) {
          vtchung_id = vt_id;
        } else {
          vtchung_id = parseInt(this.params.p_VatTu);
        }
        if (!this.Enabled.btnNhapMoi) {
          await this.CapNhatPhieuVatTuThuHoi({
            sdVtId: sdchung,
            insert: 1,
            phieuVtId: 0,
            hdTbId: this.vhdtb_id,
            baoHongId: 0,
            phieuId: pchung_id,
            thueBaoId: this.thuebao_id,
            donViId: this.$auth.getDonViID(),
            nhanVienId: this.$auth.getNhanVienID(),
            nguoiCn: this.$auth.getUserName(),
            mayCn: this.$auth.getUserName(), //tt_nd.may_cn
            ipCn: "10.59.90.123", //tt_nd.ip
            tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
            serial: sr_chung,
            ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
            vatTuId: vtchung_id,
            thietBiId: vthietbi_id,
            soLuong: parseInt(this.params.p_txtSoLuong),
            lyDoCapId: parseInt(this.params.p_LyDoCapTB),
            lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
            trangBiId: parseInt(this.params.p_TrangBi),
            kieu: vkieu,
            ghiChu: this.params.p_txtGhiChu,
          });
        } else {
          await this.CapNhatPhieuVatTuThuHoi({
            sdVtId: sdchung,
            insert: 2,
            phieuVtId: this.vphieuvt_id,
            hdTbId: this.vhdtb_id,
            baoHongId: 0,
            phieuId: pchung_id,
            thueBaoId: this.thuebao_id,
            donViId: this.$auth.getDonViID(),
            nhanVienId: this.$auth.getNhanVienID(),
            tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
            serial: this.params.p_txtSerial,
            ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
            vatTuId: vtchung_id,
            thietBiId: vthietbi_id,
            soLuong: parseInt(this.params.p_txtSoLuong),
            lyDoCapId: parseInt(this.params.p_LyDoCapTB),
            lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
            trangBiId: parseInt(this.params.p_TrangBi),
            kieu: vkieu,
            ghiChu: this.params.p_txtGhiChu,
          });
        }
        await this.NAP_DS_PHIEU_VT();
      }

      // } catch (error) {
      //   this.$toast.error("Lỗi khi thu hồi vật tư ")
      // }
    },
    async KIEMTRA_STB_2515(vkieu, vhdtb_id) {
      let user_name = this.vma_tb_cur;
      if (user_name == "") {
        this.$toast.error(
          "Không lấy được acc mytv của hợp đồng (" + vhdtb_id + ")"
        );
        return "0";
      }
      var kq = await this.KiemTraSeTopBox({
        kieu: vkieu,
        hdTbId: vhdtb_id,
        serial: "",
      });
      if (!kq.length) {
        this.$toast.error("Không lấy được kết quả kiểm tra STB");
        return "0";
      } else {
        // THU HỒI
        let kqStr = kq[0];
        if (vkieu == "3") {
          //Nếu bat dau = OK
          if (!kqStr.startsWith("OK")) {
            if (kqStr == "-2") {
              return "-2";
            } else {
              this.$toast.error(kqStr);
              return kqStr;
            }
          } else {
            return kqStr;
          }
        } else if (vkieu == "5") {
          //Nếu bat dau = OK
          if (!kqStr.startsWith("OK")) {
            if (kqStr == "-2") {
              return "-2";
            } else {
              this.$toast.error(kqStr);
              return kqStr;
            }
          } else {
            return kqStr;
          }
        } else {
          return "Lỗi ngoại lệ";
        }
      }
    },
    async THUHOI_STB_2515(boqua, vuser_name, kq, kieu, lydo) {
      // THU HỒI STB lên VASC ( CUONG 13/06/2018)
      if (boqua == "1") {
        this.paramThuHoiSTB2515.user_name = vuser_name; // tài khoản Mytv
        this.paramThuHoiSTB2515.stb_serial = kq.split("-")[2];
        this.paramThuHoiSTB2515.stb_serial_new = "";
        this.paramThuHoiSTB2515.stb_type = kq.split("-")[1];
        this.paramThuHoiSTB2515.date = this.dateToString(
          new Date(),
          "yyyyMMDD"
        );
        this.paramThuHoiSTB2515.note = this.params.p_txtGhiChuHC;
        this.paramThuHoiSTB2515.isback = true;
        this.paramThuHoiSTB2515.resue = true;
        this.paramThuHoiSTB2515.notback_reason = "";
        this.paramThuHoiSTB2515.not_resuse_dame = true;
        this.paramThuHoiSTB2515.not_resue_reason = "";
        this.paramThuHoiSTB2515.tinhtrang = "";
        //Khong thu hoi duoc thi = false
        if (kieu == "5") {
          this.paramThuHoiSTB2515.isback = false;
          this.paramThuHoiSTB2515.notback_reason = lydo;
          this.paramThuHoiSTB2515.resue = false;
          this.paramThuHoiSTB2515.not_resuse_dame = false;
          this.paramThuHoiSTB2515.not_resue_reason = "";
        }
        //Thu hoi duoc
        if (kieu == "3") {
          this.paramThuHoiSTB2515.isback = true;
          this.paramThuHoiSTB2515.tinhtrang = kq.split("-")[3];
          this.paramThuHoiSTB2515.notback_reason = "";
          // tai sudung duoc
          if (tinhtrang == "1") {
            this.paramThuHoiSTB2515.resue = true;
            this.paramThuHoiSTB2515.not_resuse_dame = false;
          } else {
            this.paramThuHoiSTB2515.resue = false; // Không tái sử dụng đc
            var f_rm = this.$refs.frmNhapText;
            await f_rm.Load_frmNhapText();
            // f_rm.fields = {value:'vatTuId', text: 'tenVatTu'}
            await f_rm.frmNhapText("Nhập lý do vì sao không tái sử dụng được");
            f_rm.Text.btnXacNhan = "Lý do khác";
            f_rm.Text.btnHuyBo = "Do bị hỏng";
            f_rm.Text.btnHuyThat = "Hủy";
            f_rm.Visible.btnHuyThat = true;
            f_rm.functionName = "lyDoKhongTaiSD";
            this.$refs.popup_frmNhapText.show();
          }
        }
        //pending chờ service
      } else {
        await this.CapNhatLogVASC({
          userName: this.$auth.getUserName(),
          resultStb: kq,
          boqua: boqua,
          note: "Ln:1288: Bỏ qua không gọi VASC",
          ngayCn: this.dateToString(new Date()),
        });
        return "1";
      }
    },
    async finalThuHoi_STB_2515(
      user_name,
      stb_serial,
      stb_serial_new,
      stb_type,
      date,
      note,
      isback,
      notback_reason,
      resue,
      not_resuse_dame,
      not_resue_reason
    ) {
      try {
        let result_STB = await this.ThuHoiSeTopBox({
          hdTbId: this.vhdtb_id,
          userName: user_name,
          stbSerial: stb_serial,
          stbSerialNew: stb_serial_new,
          isBack: isback,
          resue: resue,
          notBackReason: notback_reason,
          notResueDame: not_resuse_dame,
          notResueReason: not_resue_reason,
          stbType: stb_type,
          note: note,
          ngayCn: date,
        });
        if (_result_STB == 1) {
          var kq_update = await this.CapNhatTrangThaiDongBoSeTopBox({
            hdTbId: this.vhdtb_id,
          });
          // Kiểm tra xem có cập nhật được không
          if (parseInt(kq_update)) {
            this.$toast.success(
              "Hoàn công " +
                user_name +
                " tham gia chương trình trên VASC thành công"
            );
            //Log thành công
            await this.CapNhatLogVASC({
              userName: this.$auth.getUserName(),
              stbSerial: stb_serial,
              stbSerialNew: stb_serial_new,
              isBack: isback,
              resue: resue,
              notBackReason: notback_reason,
              notResueDame: not_resuse_dame,
              notResueReason: not_resue_reason,
              stbType: stb_type,
              resultStb: _result_STB,
              note: " Ln:1227:Thu hồi và lưu dl dưới tỉnh thành công",
              ngayCn: this.dateToString(new Date()),
            });
          } else {
            //Log thất bại
            await this.CapNhatLogVASC({
              userName: this.$auth.getUserName(),
              stbSerial: stb_serial,
              stbSerialNew: stb_serial_new,
              isBack: isback,
              resue: resue,
              notBackReason: notback_reason,
              notResueDame: not_resuse_dame,
              notResueReason: not_resue_reason,
              stbType: stb_type,
              resultStb: _result_STB,
              note: " Ln:1227:Thu hồi STB trên VASC Thành công nhưng lưu CSDL dưới tỉnh thất bại",
              ngayCn: this.dateToString(new Date()),
            });
            this.$toast.error(
              "Thu hồi STB tham gia chương trình thành công.Có lỗi khi lưu vào dưới tỉnh. Mã lỗi (" +
                this.vhdtb_id +
                ")." +
                ".Bạn hãy liên hệ admin mã lỗi để có thể tiếp tục hoàn công"
            );
            return "0";
          }
        } else {
          await this.CapNhatLogVASC({
            userName: this.$auth.getUserName(),
            stbSerial: stb_serial,
            stbSerialNew: stb_serial_new,
            isBack: isback,
            resue: resue,
            notBackReason: notback_reason,
            notResueDame: not_resuse_dame,
            notResueReason: not_resue_reason,
            stbType: stb_type,
            resultStb: _result_STB,
            note: " Ln:1288: Bỏ qua không gọi VASC",
            ngayCn: this.dateToString(new Date()),
          });
          this.$toast.error("Thu hồi STB tham gia chương trình thất bại ");
        }
      } catch (error) {
        await this.CapNhatLogVASC({
          userName: this.$auth.getUserName(),
          stbSerial: stb_serial,
          stbSerialNew: stb_serial_new,
          isBack: isback,
          resue: resue,
          notBackReason: notback_reason,
          notResueDame: not_resuse_dame,
          notResueReason: not_resue_reason,
          stbType: stb_type,
          resultStb: _result_STB,
          note:
            "Thu hồi STB trên VASC Thành công nhưng lưu CSDL dưới tỉnh thất bại " +
            error.data.message,
          ngayCn: this.dateToString(new Date()),
        });
        this.$toast.error(
          "Thu hồi STB tham gia chương trình thành công.Có lỗi khi lưu vào dưới tỉnh. Mã lỗi (" +
            this.vhdtb_id +
            ")." +
            ".Bạn hãy liên hệ admin mã lỗi để có thể tiếp tục hoàn công"
        );
        return "0";
      }
      return "1";
    },
    async HOANCONG() {
      // try {
      let boqua = false;
      let z = await this.getDSThaoTacTheoCode({ code: "BQ_GV_THUHOI" });
      if (z.length) {
        boqua = true;
      }
      if (!this.gridGiaoviec.length && boqua == false) {
        return this.$toast.error("Bạn chưa giao việc cho nhân viên!");
      }
      if (!parseInt(this.params.p_KetQuaXL)) {
        return this.$toast.error("Bạn chưa chọn kết quả xử lý !");
      }
      // bổ sung biến xử lý bất đồng bộ
      var isDoing_FinalHoanCong = true;
      // cảnh báo nếu thiết bị đã thu hồi tại GDV, nhưng lại chọn kết quả là ko thu hồi được
      if (this.dt_kt_canhbao.length) {
        if (
          this.dt_kt_canhbao[0]["kqXlId"] &&
          this.dt_kt_canhbao[0]["kqXlId"] == "3"
        ) {
          if (this.params.p_KetQuaXL == "3") {
            let isConfirm = await this.$bvModal.msgBoxConfirm(
              "Bạn có muốn chắn chắn không thu hồi được thiết bị đối với thiết bị tại giao dịch viên không ?",
              {
                okVariant: "success",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              }
            );
            if (!isConfirm) {
              return;
            }
          }
        }
      }
      await this.Final_HoanCong();
    },
    async Final_HoanCong() {
      try {
        let vkieuld_id = 0;
        let str_kqxl = "";

        if (this.params.p_PhanHoi != null && this.params.p_PhanHoi) {
          if (this.vkieu_km == 1) {
            if (!this.params.p_chkPhanHoi && this.$auth.getMaTinh() != "HNI") {
              this.$toast.error("Bạn chưa chọn phản hồi của khách hàng !");
              this.$refs.chkPhanHoi.focus();
              return;
            }
          }
          if (this.params.p_chkPhanHoi) {
            vkieuld_id = parseInt(this.params.p_PhanHoi);
          }
        }
        // không thu hồi được thì phải có lý do, ghi chú
        if (this.params.p_KetQuaXL == "3") {
          if (!this.params.p_LyDoTon || this.params.p_LyDoTon == "0") {
            this.$toast.error("Bạn chưa nhập lý do tồn!");
            this.$refs.cboLyDoTon.focusIn();
            return;
          }

          if (!this.params.p_txtGhiChuTon) {
            this.$toast.error("Bạn chưa nhập Ghi chú tồn !");
            this.$refs.txtGhiChuTon.focus();
            return;
          }

          await this.TaoDL_Ton_Khong_ThuHoi_Duoc(true);
        }
        if (this.canhbao_sl_thuhoi_vt) {
          let kt_thvt = this.getKiemTraCanhBaoSLThuHoi({
            hdTbId: this.vhdtb_id,
            baoHongId: 0,
          });
          if (kt_thvt != "1") {
            let isConfirm = await this.$bvModal.msgBoxConfirm(kt_thvt, {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            });
            if (!isConfirm) return;
          }
        }
        // THu hồi từ kỹ thauatj -> tổ trưởng
        if (this.params.p_rdioCKb == "1") {
          let k = 1;
          // không thu hồi được
          if (this.params.p_KetQuaXL == "3") {
            k = 2;
          }
          //Khong thu hoi ma co vat tu
          if (k == 2 && this.grcPhieuVT.length > 0) {
            this.$toast.error("Bạn hãy thực hiện xóa danh sách vật tư thu hồi");
            return;
          }
          if (k == 2 && this.params.p_txtGhiChuHC == "") {
            this.$toast.error("Bạn phải nhập nội dung thực hiện");
            return;
          }
          if (k == 1 && this.grcPhieuVT.length == 0) {
            this.$toast.error("Bạn phải nhập vật tư thu hồi");
            return;
          }

          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
              this.params.p_txtSoAo +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (isConfirm) {
            let kq = "";
            let boqua_ = "";
            let boham = "0";
            // if (this.vloaitb_id == this.LoaiHinhTB.INTERNET_MYTV) {
            //   // Kiểm tra trước khi hoàn công
            //   // Không thu hồi được
            //   if (k == 2) {
            //     kq = await this.KIEMTRA_STB_2515("5", this.vhdtb_id);
            //   } else {
            //     kq = await this.KIEMTRA_STB_2515("3", this.vhdtb_id);
            //   }
            //   // trường hợp ko lấy đc vât tư sẽ bỏ qua
            //   if (kq.startsWith("OKNOTOK")) {
            //     boqua_ = "1";
            //     boham = "1";
            //   }
            //   // Bắt đầu bằng chữ OK-stb_tye-serial lấy đc
            //   else if (kq.startsWith("OK") && !kq.includes("NOTOK")) {
            //     // gọi hoàn công
            //     boqua_ = "1";
            //   }
            //   // Nếu không phải in (1,2,3,4)
            //   else {
            //     // -1 là bỏ qua
            //     if (kq == "-2") {
            //       boqua_ = "0";
            //       this.$toast.warning(
            //         "Phiếu có trạng thái -2 không thể hoàn công!"
            //       );
            //     } else {
            //       return;
            //     }
            //   }
            //   // không thu hồi
            //   if (k == 2 && boqua_ == "1") {
            //     if (boham == "0") {
            //       this.paramThuHoiSTB2515.k = k;
            //       let k_ = await this.THUHOI_STB_2515(
            //         boqua_,
            //         this.vma_tb_cur,
            //         kq,
            //         "5",
            //         this.params.p_LyDoTon
            //       );
            //       // if (k_ != "1") {
            //       //   return;
            //       // }
            //     }
            //   } else if (k != 2 && boqua_ == "1") {
            //     if (boham == "0") {
            //       this.paramThuHoiSTB2515.k = k;
            //       let k_ = await this.THUHOI_STB_2515(
            //         boqua_,
            //         this.vma_tb_cur,
            //         kq,
            //         "3",
            //         this.params.p_LyDoTon
            //       );
            //       // if (k_ != "1") {
            //       //   return;
            //       // }
            //     }
            //   }
            // } else
            await this.thuHoiIsConfirm(k);
            // await this.GiaoPhieuTapDoanDuyetTH({
            //   thuHoi: k,
            //   hdTbId: this.vhdtb_id,
            //   baoHongId: 0,
            //   phieuId: this.vphieu_id,
            //   donViGiaoId: this.$auth.getDonViID(),
            //   nhanVienId: this.$auth.getNhanVienID(),
            //   ndThucHien: this.params.p_txtGhiChuHC,
            //   nguoiCn: this.$auth.getUserName(),
            //   mayCn: this.$auth.getUserName(), //tt_nd.may_cn
            // });

            // var ds_qt = await this.getDSQuyTrinhTheoMa();

            // if (ds_qt.length) {
            //   if (
            //     ds_qt[0]["trangThai"] &&
            //     ds_qt[0]["trangThai"] == "1"
            //   ) {
            //     var ds_pid = await this.getIDPhieuTheoHDTB({
            //       hdTbId: this.vhdtb_id,
            //     });

            //     if (ds_pid.length == 1) {
            //       await this.HoanCongThuHoiVT_V1({
            //         phieuId: ds_pid[0],
            //         phieuChaId: this.vphieu_id,
            //         nhanVienId: this.$auth.getNhanVienID(),
            //         ngayTh: this.dateToString(
            //           new Date(),
            //           "DD/MM/YYYY HH:mm:ss"
            //         ),
            //         ndThucHien: this.params.p_txtGhiChuHC,
            //         hdTbId: this.vhdtb_id,
            //         mucDichId: this.vmucdich_id,
            //         kieuKm: this.vkieu_km,
            //         kieuLdId: vkieuld_id,
            //         ngayCn: this.dateToString(
            //           new Date(),
            //           "DD/MM/YYYY HH:mm:ss"
            //         ),
            //       });
            //     }
            //   }
            // }
            // // không thu hồi được insert vào bảng giaophieu_ton
            // if (this.params.p_KetQuaXL == "3") {
            //   await this.Func_TaoGiaoPhieuTon(this.dston);

            //   if (
            //     this.params.p_LyDoHuy != "-1" &&
            //     this.params.p_LyDoHuy
            //   ) {
            //     await this.CapNhatLyDoHuy({
            //       phieuId: this.vphieu_id,
            //       lydoHuyId: this.params.p_LyDoHuy,
            //     });
            //   }
            // }
            // this.$toast.success(
            //   "Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " +
            //     this.params.p_txtSoAo
            // );
            // await this.HIENTHI_DANHSACH_THUEBAO("");
          }
        } else {
          var k = 1;
          // không thu hồi được
          if (this.params.p_KetQuaXL == "3") {
            k = 2;
          }
          // nếu ko cần thu hồi
          if (this.params.p_rdioCKb == "3" && k == 2) {
            //Khong thu hoi ma co vat tu
            if (k == 2 && this.grcPhieuVT.length > 0) {
              this.$toast.error(
                "Bạn hãy thực hiện xóa danh sách vật tư thu hồi"
              );
              return;
            }
            if (k == 2 && this.params.p_txtGhiChuHC == "") {
              this.$toast.error("Bạn phải nhập nội dung thực hiện");
              return;
            }
            await this.$bvModal
              .msgBoxConfirm(
                "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
                  this.params.p_txtSoAo +
                  " không?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              )
              .then(async (value) => {
                if (value) {
                  await this.HoanCongThuHoiVT_V1({
                    phieuId: this.vphieu_id,
                    phieuChaId: this.vphieu_cha_id,
                    nhanVienId: this.$auth.getNhanVienID(),
                    ngayTh: this.dateToString(
                      new Date(),
                      "DD/MM/YYYY HH:mm:ss"
                    ),
                    ndThucHien: this.params.p_txtGhiChuHC,
                    hdTbId: this.vhdtb_id,
                    mucDichId: this.vmucdich_id,
                    kieuKm: 0,
                    kieuLdId: 0,
                    ngayCn: this.dateToString(
                      new Date(),
                      "DD/MM/YYYY HH:mm:ss"
                    ),
                    nguoiCn: this.$auth.getUserName(),
                  });
                  // không thu hồi được insert vào bảng giaophieu_ton
                  if (this.params.p_KetQuaXL == "3") {
                    await this.Func_TaoGiaoPhieuTon(this.dston);

                    await this.CapNhatKQXuLyKhongTH({
                      phieuId: this.vphieu_id,
                      lydoHuyId: this.params.p_LyDoHuy,
                    });
                  }

                  this.$toast.success(
                    "Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " +
                      this.params.p_txtSoAo
                  );
                  await this.HIENTHI_DANHSACH_THUEBAO("");
                }
                return;
              })
              .catch((err) => {
                console.log(err);
              });
          }

          // nếu ko cần thu hồi
          if (k == 2) {
            //Khong thu hoi ma co vat tu
            if (k == 2 && this.grcPhieuVT.length > 0) {
              this.$toast.error(
                "Bạn hãy thực hiện xóa danh sách vật tư thu hồi"
              );
              return;
            }
            if (k == 2 && this.params.p_txtGhiChuHC == "") {
              this.$toast.error("Bạn phải nhập nội dung thực hiện");
              return;
            }
            let isConfirm = await this.$bvModal.msgBoxConfirm(
              "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
                this.params.p_txtSoAo +
                " không?",
              {
                okVariant: "success",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              }
            );
            if (isConfirm) {
              await this.HoanCongThuHoiVT_V1({
                phieuId: this.vphieu_id,
                phieuChaId: this.vphieu_cha_id,
                nhanVienId: this.$auth.getNhanVienID(),
                ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                ndThucHien: this.params.p_txtGhiChuHC,
                hdTbId: this.vhdtb_id,
                mucDichId: this.vmucdich_id,
                kieuKm: this.vkieu_km,
                kieuLdId: vkieuld_id,
                ngayCn: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                nguoiCn: this.$auth.getUserName(),
              });
              // không thu hồi được insert vào bảng giaophieu_ton
              if (this.params.p_KetQuaXL == "3") {
                await this.Func_TaoGiaoPhieuTon(this.dston);

                await this.CapNhatKQXuLyKhongTH({
                  phieuId: this.vphieu_id,
                  lydoHuyId: this.params.p_LyDoHuy,
                });
              }
              this.$toast.success(
                "Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " +
                  this.params.p_txtSoAo
              );
              await this.HIENTHI_DANHSACH_THUEBAO("");
            }
            return;
          }

          if (this.grcPhieuVT.length == 0) {
            return this.$toast.error("Bạn phải nhập vật tư thu hồi");
          }

          if (!this.params.p_LyDoCapTB) {
            return this.$toast.error("Hãy chọn lý do thu hồi vật tư");
          }
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
              this.params.p_txtSoAo +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (isConfirm) {
            await this.HoanCongThuHoiVT_V1({
              phieuId: this.vphieu_id,
              phieuChaId: this.vphieu_cha_id,
              nhanVienId: this.$auth.getNhanVienID(),
              ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
              ndThucHien: this.params.p_txtGhiChuHC,
              hdTbId: this.vhdtb_id,
              mucDichId: this.vmucdich_id,
              kieuKm: this.vkieu_km,
              kieuLdId: vkieuld_id,
              ngayCn: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
              nguoiCn: this.$auth.getUserName(),
            });
            // hoangpkn : 26/06/2017. Thêm đoạn này cho chắc nhưng chắc ko insert giaophieu_ton ở chỗ này
            // không thu hồi được insert vào bảng giaophieu_ton
            if (this.params.p_KetQuaXL == "3") {
              await this.Func_TaoGiaoPhieuTon(this.dston);

              await this.CapNhatKQXuLyKhongTH({
                phieuId: this.vphieu_id,
                lydoHuyId: this.params.p_LyDoHuy,
              });
            }
            // thu hồi được
            if (this.params.p_KetQuaXL == "1") {
              await this.CapNhatKQXyLyTH({ phieuId: this.vphieu_id });
            }
            // end hoàng
            this.$toast.success(
              "Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " +
                this.params.p_txtSoAo
            );
            await this.HIENTHI_DANHSACH_THUEBAO("");
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
      }
    },
    async thuHoiIsConfirm(k) {
      await this.GiaoPhieuTapDoanDuyetTH({
        thuHoi: k,
        hdTbId: this.vhdtb_id,
        baoHongId: 0,
        phieuId: this.vphieu_id,
        donViGiaoId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        ndThucHien: this.params.p_txtGhiChuHC,
        nguoiCn: this.$auth.getUserName(),
        mayCn: this.$auth.getUserName(), //tt_nd.may_cn
      });

      var ds_qt = await this.getDSQuyTrinhTheoMa();

      if (ds_qt.length) {
        if (ds_qt[0]["trangThai"] && ds_qt[0]["trangThai"] == "1") {
          var ds_pid = await this.getIDPhieuTheoHDTB({
            hdTbId: this.vhdtb_id,
          });

          if (ds_pid.length == 1) {
            await this.HoanCongThuHoiVT_V1({
              phieuId: ds_pid[0],
              phieuChaId: this.vphieu_id,
              nhanVienId: this.$auth.getNhanVienID(),
              ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
              ndThucHien: this.params.p_txtGhiChuHC,
              hdTbId: this.vhdtb_id,
              mucDichId: this.vmucdich_id,
              kieuKm: this.vkieu_km,
              kieuLdId: vkieuld_id,
              ngayCn: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
            });
          }
        }
      }
      // không thu hồi được insert vào bảng giaophieu_ton
      if (this.params.p_KetQuaXL == "3") {
        await this.Func_TaoGiaoPhieuTon(this.dston);

        if (this.params.p_LyDoHuy != "-1" && this.params.p_LyDoHuy) {
          await this.CapNhatLyDoHuy({
            phieuId: this.vphieu_id,
            lydoHuyId: this.params.p_LyDoHuy,
          });
        }
      }
      this.$toast.success(
        "Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " +
          this.params.p_txtSoAo
      );
      await this.HIENTHI_DANHSACH_THUEBAO("");
    },
    async NhanTin() {
      let index = this.gridViewThueBao_FocusedRow;
      let sms_noidung = "";
      let kt_sdt = false;
      let ds_nv = [];
      ds_nv = await this.getDSNhanVienChamSocThueBao({
        thueBaoId: this.vthuebao_id,
        kieu: 2,
      });
      if (ds_nv.length) {
        for (let i = 0; i < ds_nv.length; i++) {
          if (ds_nv[i].SO_DT != "") {
            kt_sdt = true;
            soao = this.gridViewThueBao_FocusedRow.MA_LT;
            if (soao != "") {
              soao = "(" + soao + ")";
            }
            sms_noidung =
              this.gridViewThueBao_FocusedRow.MA_TB +
              soao +
              "; " +
              this.gridViewThueBao_FocusedRow["TEN_TB"] +
              "; " +
              this.gridViewThueBao_FocusedRow["DIACHI_TT"] +
              "; " +
              this.gridViewThueBao_FocusedRow["SO_DT"] +
              "; " +
              this.gridViewThueBao_FocusedRow["LOAIHINH_TB"] +
              "; " +
              this.gridViewThueBao_FocusedRow["MA_TUYEN"];
            // SMSService.SendSMS(ds_nv.Tables[0].Rows[i]["so_dt"], sms_noidung)
          }
        }
        if (kt_sdt == false) {
          ds_nv = await this.getDSNhanVienChamSocThueBao({
            thueBaoId: this.vthuebao_id,
            kieu: 1,
          });
          if (ds_nv.length) {
            for (let i = 0; i < ds_nv.length; i++) {
              if (ds_nv[i]["SO_DT"] != "") {
                kt_sdt = true;
                soao = this.gridViewThueBao_FocusedRow.MA_LT;
                if (soao != "") {
                  soao = "(" + soao + ")";
                }
                sms_noidung =
                  this.gridViewThueBao_FocusedRow["MA_TB"] +
                  soao +
                  "; " +
                  this.gridViewThueBao_FocusedRow["TEN_TB"] +
                  "; " +
                  this.gridViewThueBao_FocusedRow["DIACHI_TT"] +
                  "; " +
                  this.gridViewThueBao_FocusedRow["SO_DT"] +
                  "; " +
                  this.gridViewThueBao_FocusedRow["LOAIHINH_TB"] +
                  "; " +
                  this.gridViewThueBao_FocusedRow["MA_TUYEN"];
                // SMSService.SendSMS(ds_nv.Tables[0].Rows[i]["so_dt"], sms_noidung)
              }
            }
          }
        }
      } else {
        // nhân viên KD vùng
        ds_nv = await this.getDSNhanVienChamSocThueBao({
          thueBaoId: this.vthuebao_id,
          kieu: 1,
        });
        if (ds_nv.length) {
          for (let i = 0; i < ds_nv.length; i++) {
            if (ds_nv[i]["SO_DT"] != "") {
              kt_sdt = true;
              soao = this.gridViewThueBao_FocusedRow.MA_LT;
              if (soao != "") {
                soao = "(" + soao + ")";
              }
              sms_noidung =
                this.gridViewThueBao_FocusedRow["MA_TB"] +
                soao +
                "; " +
                this.gridViewThueBao_FocusedRow["TEN_TB"] +
                "; " +
                this.gridViewThueBao_FocusedRow["DIACHI_TT"] +
                "; " +
                this.gridViewThueBao_FocusedRow["SO_DT"] +
                "; " +
                this.gridViewThueBao_FocusedRow["LOAIHINH_TB"] +
                "; " +
                this.gridViewThueBao_FocusedRow["MA_TUYEN"];
              // SMSService.SendSMS(ds_nv.Tables[0].Rows[i]["so_dt"], sms_noidung)
            }
          }
        }
      }
    },
    async XOAPHIEU() {
      try {
        this.$root.loading(true);
        let sdvt_xoa = this.grvPhieuVT_FocusedRow.SDVT_ID;
        let s = await this.CapNhatThuHoiVatTu_V2({
          serial: "",
          sdVtId: sdvt_xoa,
          kieu: 2,
          phieuVtId: this.vphieuvt_id,
        });
        if (s != "1") {
          this.$toast.error("Có lỗi khi xoá vật tư: " + s);
        } else {
          this.$toast.success("Xóa vật tư thành công!");
        }
        this.vsdvt_id = 0;
        this.grcPhieuVT = [];
        this.gridVatTu = [];
        this.$refs.txtSerial.focus();
        await this.KHOAMO_TT_VATTU(true);
        this.Enabled.cboTinhTrangTbi = true;
        this.Enabled.btnNhapMoi = false;
        await this.NAP_DS_PHIEU_VT();
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async TRACUU_VATTU(silent) {
      var dt = await this.getThongTinVatTuTheoSerial({ serial: this.vserial });
      if (dt.length) {
        this.tt_ten_vt = parseInt(dt[0]["VATTU_ID"]);
        this.params.p_VatTu = this.tt_ten_vt;
        this.Enabled.cboVatTu = false;
        this.params.p_txtSoLuong = 1;
        return;
      }
      this.tt_ten_vt = 0;
      if (!silent) {
        if (!this.Enabled.cboVatTu) {
          this.params.p_VatTu = "";
          this.Enabled.cboVatTu = true;
          this.$refs.cboVatTu.focusIn();
        }
      }
    },
    async frmThuHoiVatTu_Load() {
      try {
        this.loading(true);
        await this.NAP_COMBOBOX();
        let kq_maTS = await this.$root.context.get("/web-qlvt/api/thu-hoi-vt-thue-bao/vattu-thuhoi-type45")
        console.log("kq_maTS = ",kq_maTS);
        if(kq_maTS.data == "1"){
          this.rdioShow_PhieuThuHoi = true;
        } else {
          this.rdioShow_PhieuThuHoi = false;
        }
        if (this.kieu_goi != 0) {
          this.params.p_rdioCKb = this.kieu_goi;
        }
        if (this.$auth.getMaTinh() == "HNI") {
          this.params.p_lblGhiChu = "Serial thực tế";
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async btnTimKiem_Click() {
      await this.HIENTHI_DANHSACH_THUEBAO(
        this.params.p_txtSoThueBao ? this.params.p_txtSoThueBao : ""
      );
    },
    async btnThuHoi_Click() {
      try {
        if (this.isThuHoiRunning) {
          return;
        }
        this.isThuHoiRunning = true;
        await this.ThuHoiVatTu_V2();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.isThuHoiRunning = false;
        this.loading(false);
      }
    },
    async hoancong_v2() {
      // try {
      let error = "";
      let boqua = false;
      var z = await this.getDSThaoTacTheoCode({ code: "BQ_GV_THUHOI" });
      if (z.length) {
        boqua = true;
      }
      if (this.gridGiaoviec.DataRowCount == 0 && boqua == false) {
        error =
          "Bạn chưa giao việc cho nhân viên của thuê bao: " + this.vma_tb_cur;
        this.ds_error.push(error);
      }
      if (this.params.p_KetQuaXL == "0") {
        error = "Bạn chưa chọn kết quả xử lý !";
        this.ds_error.push(error);
      }
      let vkieuld_id = 0;
      let str_kqxl = "";
      if (this.params.p_PhanHoi) {
        if (vkieu_km == 1) {
          if (
            this.params.p_chkPhanHoi == false &&
            this.$auth.getMaTinh() != "HNI"
          ) {
            error = "Bạn chưa chọn phản hồi của khách hàng !";
            this.ds_error.push(error);
          }
        }
        if (this.params.p_chkPhanHoi) {
          vkieuld_id = parseInt(this.params.p_PhanHoi);
        }
      }
      // không thu hồi được thì phải có lý do, ghi chú
      if (this.params.p_KetQuaXL == "3") {
        if (this.params.p_LyDoTon == "0") {
          error = "Bạn chưa nhập lý do tồn!";
          this.ds_error.push(error);
        }
        if (this.params.p_txtGhiChuTon == "") {
          error = "Bạn chưa nhập Ghi chú tồn !";
          this.ds_error.push(error);
        }
        await this.TaoDL_Ton_Khong_ThuHoi_Duoc(true);
      }
      let k = 1;
      // không thu hồi được
      if (this.params.p_KetQuaXL == "3") {
        k = 2;
      }
      // nếu ko cần thu hồi
      if (k == 2) {
        //Khong thu hoi ma co vat tu
        if (k == 2 && this.grcPhieuVT.length > 0) {
          error =
            "Bạn hãy thực hiện xóa danh sách vật tư thu hồi của thuê bao: " +
            this.vma_tb_cur;
          this.ds_error.push(error);
        }
        if (k == 2 && this.params.p_txtGhiChuHC == "") {
          error = "Bạn phải nhập nội dung thực hiện";
          this.ds_error.push(error);
        }
        if (this.ds_error.length) {
          return;
        }
        await this.HoanCongThuHoiVT_V1({
          phieuId: this.vphieu_id,
          phieuChaId: this.vphieu_cha_id,
          nhanVienId: this.$auth.getNhanVienID(),
          ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
          ndThucHien: this.params.p_txtGhiChuHC,
          hdTbId: this.vhdtb_id,
          mucDichId: this.vmucdich_id,
          kieuKm: this.vkieu_km,
          kieuLdId: vkieuld_id,
          ngayCn: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
          nguoiCn: this.$auth.getUserName(),
          mayCn: this.$auth.getUserName(), //tt_nd.may_cn
          ipCn: "10.59.90.123", //tt_nd.ip
        });
        // không thu hồi được insert vào bảng giaophieu_ton
        if (this.params.p_KetQuaXL == "3") {
          await this.Func_TaoGiaoPhieuTon(this.dston);

          await this.CapNhatKQXuLyKhongTH({
            phieuId: this.vphieu_id,
            lydoHuyId: this.params.p_LyDoHuy,
          });
        }
        await this.HIENTHI_DANHSACH_THUEBAO("");
        return;
      }

      if (this.grcPhieuVT.length) {
        error = "Bạn phải nhập vật tư thu hồi của thuê bao: " + this.vma_tb_cur;
        this.ds_error.push(error);
      }
      if (!this.params.p_LyDoCapTB) {
        error = "Hãy chọn lý do thu hồi vật tư";
        this.ds_error.push(error);
      }
      if (this.ds_error.length) {
        return;
      }
      await this.HoanCongThuHoiVT_V1({
        phieuId: this.vphieu_id,
        phieuChaId: this.vphieu_cha_id,
        nhanVienId: this.$auth.getNhanVienID(),
        ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
        ndThucHien: this.params.p_txtGhiChuHC,
        hdTbId: this.vhdtb_id,
        mucDichId: this.vmucdich_id,
        kieuKm: this.vkieu_km,
        kieuLdId: vkieuld_id,
        ngayCn: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
        nguoiCn: this.$auth.getUserName(),
        mayCn: this.$auth.getUserName(), //tt_nd.may_cn
        ipCn: "10.59.90.123", //tt_nd.ip
      });
      // không thu hồi được insert vào bảng giaophieu_ton
      if (this.params.p_KetQuaXL == "3") {
        await this.Func_TaoGiaoPhieuTon(this.dston);
        await this.CapNhatKQXuLyKhongTH({
          phieuId: this.vphieu_id,
          lydoHuyId: this.params.p_LyDoHuy,
        });
      }
      // thu hồi được
      if (this.params.p_KetQuaXL == "1") {
        await this.CapNhatKQXyLyTH({ phieuId: this.vphieu_id });
        if (this.params.p_txtGhiChuTon) {
          await this.CapNhatGhiChuTon({
            phieuId: this.vphieu_id,
            ghiChu: this.params.p_txtGhiChuTon,
          });
        }
      }
      await this.HIENTHI_DANHSACH_THUEBAO("");
      // } catch (error) {
      //   this.$toast.error("Lỗi khi hoàn công thu hồi vật tư ")
      // }
    },
    async btnHoanCong_Click() {
      try{
        if (this.$auth.getMaTinh() == "HNI") {       
          if (this.params.p_rdioCKb == "2") {
            this.ds_error = [];
            let table = this.gridThueBao;
            if (table.length) {              
              let check = this.gridThueBao.filter((e) =>
                this.rowCheckedTB.includes(e.HDTB_ID)
              );
              if (check.length) {
                let checkList = check;
                let loaihd_temp = checkList[0]["LOAIHD_ID"];
                await this.$bvModal
                  .msgBoxConfirm(
                    "Bạn có thực sự muốn hoàn công thu hồi vật tư cho các thuê bao đã chọn không?",
                    {
                      okVariant: "success",
                      okTitle: "Đồng ý",
                      cancelTitle: "Hủy",
                      centered: true,
                    }
                  )
                  .then(async (value) => {
                    if (value) {
                      let data = {
                        ds_phieu: check,
                        ghichuhc: this.params.p_txtGhiChuHC,
                        ghichuton: this.params.p_txtGhiChuTon,
                        ketquaxl: this.params.p_KetQuaXL,
                        kieuld_id: parseInt(this.params.p_PhanHoi),
                        ldhuy_id: this.params.p_LyDoHuy,
                        lydo_th_id: this.params.p_LyDoCapTB,
                        lydoton_id: this.params.p_LyDoTon,
                        mucdich_id: this.vmucdich_id,
                      };

                      //Check form HNi

                      this.loading(true);
                      this.$root.context
                        .post(
                          "/web-qlvt/api/thu-hoi-vt-thue-bao/ht-thuhoi-vt-phieuchoduyet-hni",
                          data
                        )
                        .then(async (res) => {
                          console.log("ht-thuhoi-vt-phieuchoduyet-hni = ", res);
                          if (res.error_code === "BSS-00000000") {
                            this.$root.toastSuccess("Cập nhật thành công!");
                            await this.HIENTHI_DANHSACH_THUEBAO();
                            this.closeDialog();
                          }
                        })
                        .catch((e) =>
                          this.$root.toastError(e.response.data.message)
                        )
                        .finally(() => this.loading(false));
                    }
                  })
                  .catch((err) => {
                    // An error occurred
                  });
              } else {
                this.$toast.error("Bạn chưa tích dòng nào!");
              }
            }
          } else {
            await this.HOANCONG_V2(); 
          }
        } else {    
          await this.HOANCONG_V2();   // dùng hàm mới cho tất cả các tỉnh thay thế this.HOANCONG, trừ HNI dùng riêng  
          //   await this.HOANCONG_HCM();
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async HOANCONG_V2(){
      try{
        let boqua = false;
        let z = await this.getDSThaoTacTheoCode({ code: "BQ_GV_THUHOI" });
        if (z.length) {
          boqua = true;
        }
        if (!this.gridGiaoviec.length && boqua == false) {
          return this.$toast.error("Bạn chưa giao việc cho nhân viên!");
        }
        if (!parseInt(this.params.p_KetQuaXL)) {
          return this.$toast.error("Bạn chưa chọn kết quả xử lý !");
        }
      
        if (this.dt_kt_canhbao.length) {
          if (
            this.dt_kt_canhbao[0]["kqXlId"] &&
            this.dt_kt_canhbao[0]["kqXlId"] == "3"
          ) {
            if (this.params.p_KetQuaXL == "3") {
              let isConfirm = await this.$bvModal.msgBoxConfirm(
                "Bạn có muốn chắn chắn không thu hồi được thiết bị đối với thiết bị tại giao dịch viên không ?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              );
              if (!isConfirm) {
                return;
              }
            }
          }
        }

        let vkieuld_id = 0;
        let str_kqxl = "";

        if (this.params.p_PhanHoi != null && this.params.p_PhanHoi) {
          if (this.vkieu_km == 1) {
            if (!this.params.p_chkPhanHoi && this.$auth.getMaTinh() != "HNI") {
              this.$toast.error("Bạn chưa chọn phản hồi của khách hàng !");
              this.$refs.chkPhanHoi.focus();
              return;
            }
          }
          if (this.params.p_chkPhanHoi) {
            vkieuld_id = parseInt(this.params.p_PhanHoi);
          }
        }
        // không thu hồi được thì phải có lý do, ghi chú
        if (this.params.p_KetQuaXL == "3") {
          if (!this.params.p_LyDoTon || this.params.p_LyDoTon == "0") {
            this.$toast.error("Bạn chưa nhập lý do tồn!");
            this.$refs.cboLyDoTon.focusIn();
            return;
          }

          if (!this.params.p_txtGhiChuTon) {
            this.$toast.error("Bạn chưa nhập Ghi chú tồn !");
            this.$refs.txtGhiChuTon.focus();
            return;
          }
          
        }
        if (this.canhbao_sl_thuhoi_vt) {
          let kt_thvt = this.getKiemTraCanhBaoSLThuHoi({
            hdTbId: this.vhdtb_id,
            baoHongId: 0,
          });
          if (kt_thvt != "1") {
            let isConfirm = await this.$bvModal.msgBoxConfirm(kt_thvt, {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            });
            if (!isConfirm) return;
          }
        }
        
        let k = this.params.p_KetQuaXL == "3" ? 2 : 1
  
        if (this.params.p_rdioCKb == "1") { // PHIẾU MỚI
          
          //Khong thu hoi ma co vat tu
          // if (k == 2 && this.grcPhieuVT.length > 0) {
          //   this.$toast.error("Bạn hãy thực hiện xóa danh sách vật tư thu hồi");
          //   return;
          // }
          if (k == 2 && this.params.p_txtGhiChuHC == "") {
            this.$toast.error("Bạn phải nhập nội dung thực hiện");
            return;
          }
          if (k == 1 && this.grcPhieuVT.length == 0) {
            this.$toast.error("Bạn phải nhập vật tư thu hồi");
            return;
          }

          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
              this.params.p_txtSoAo +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) {
            return
          }
          this.loading(true)
          let ketQua = await this.HoanCongThuHoiVTTB({
            loaiphieu: this.params.p_rdioCKb,
            phieu_id: this.vphieu_id,
            ketquaxl: this.params.p_KetQuaXL,
            nd_thuchien: this.params.p_txtGhiChuHC,
            mucdich_id: this.params.p_LyDoCapTB,
            kieu_km: this.vkieu_km,
            kieuld_id: vkieuld_id,
            lydoton_id: this.params.p_LyDoTon,
            ghichu_ton: this.params.p_txtGhiChuTon,
            lydo_huy: this.params.p_LyDoHuy,
          })
          this.$toast.success("Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " + this.params.p_txtSoAo);
          await this.HIENTHI_DANHSACH_THUEBAO("");
          this.grcSuDungVT = []
        }else if(this.params.p_rdioCKb == "2" || this.params.p_rdioCKb == "4" || this.params.p_rdioCKb == "5"){ // PHIẾU CHỜ DUYỆT

          if(k==2){          
            if (this.params.p_txtGhiChuHC == "") {
              this.$toast.error("Bạn phải nhập nội dung thực hiện");
              return;
            }          
          }else{              
            if (!this.params.p_LyDoCapTB) {
              return this.$toast.error("Hãy chọn lý do thu hồi vật tư");
            }
            
          }
          // if (this.grcPhieuVT.length > 0) {
          //   this.$toast.error(
          //     "Bạn hãy thực hiện xóa danh sách vật tư thu hồi"
          //   );
          //   return;
          // }
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao " +
              this.params.p_txtSoAo +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) {
            return
          }
          this.loading(true)
          let ketQua = await this.HoanCongThuHoiVTTB({
            loaiphieu: this.params.p_rdioCKb,
            phieu_id: this.vphieu_id,
            ketquaxl: this.params.p_KetQuaXL,
            nd_thuchien: this.params.p_txtGhiChuHC,
            mucdich_id: this.params.p_LyDoCapTB,
            kieu_km: this.vkieu_km,
            kieuld_id: vkieuld_id,
            lydoton_id: this.params.p_LyDoTon,
            ghichu_ton: this.params.p_txtGhiChuTon,
            lydo_huy: this.params.p_LyDoHuy,
          })
          this.$toast.success("Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " + this.params.p_txtSoAo);
          await this.HIENTHI_DANHSACH_THUEBAO("");
          this.grcSuDungVT = []
        }else if(this.params.p_rdioCKb == "3"){ // PHIẾU TRẢ
          
          // if (k == 2 && this.grcPhieuVT.length > 0) {
          //   this.$toast.error( "Bạn hãy thực hiện xóa danh sách vật tư thu hồi" );
          //   return;
          // }
          if (k == 2 && this.params.p_txtGhiChuHC == "") {
            this.$toast.error("Bạn phải nhập nội dung thực hiện");
            return;
          }

          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có thực sự muốn hoàn công thu hồi vật tư cho thuê bao" +
              this.params.p_txtSoAo +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) {
            return
          }
          this.loading(true)
          let ketQua = await this.HoanCongThuHoiVTTB({
            loaiphieu: this.params.p_rdioCKb,
            phieu_id: this.vphieu_id,
            ketquaxl: this.params.p_KetQuaXL,
            nd_thuchien: this.params.p_txtGhiChuHC,
            mucdich_id: this.params.p_LyDoCapTB,
            kieu_km: this.vkieu_km,
            kieuld_id: vkieuld_id,
            lydoton_id: this.params.p_LyDoTon,
            ghichu_ton: this.params.p_txtGhiChuTon,
            lydo_huy: this.params.p_LyDoHuy,
          })
          this.$toast.success("Đã hoàn công thu hồi vật tư thuê bao cho thuê bao " + this.params.p_txtSoAo);
          await this.HIENTHI_DANHSACH_THUEBAO("");
          this.grcSuDungVT = []
        }else{ // Kiểu 4 5 dùng cho Hải Phòng, chưa bổ sung
          this.$toast.error("Kiểu phiếu chưa triển khai, vui lòng chờ")
        }
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    async btnXoaPhieu_PhieuVT_ButtonClick(data) {
      console.log("btnXoaPhieu_PhieuVT_ButtonClick", data);
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có thực sự muốn xóa phiếu thu hồi vật tư đang chọn không ?",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        )
        .then(async (value) => {
          if (value) {
            await this.XOAPHIEU();
          }
        })
        .catch((err) => {
          // An error occurred
        });
    },
    async btnNapVatTu_MouseClick() {
      await this.NAP_DS_SUDUNG_VT();
    },
    // async btnNapPhieu_MouseClick() {
    //   await this.NAP_DS_PHIEU_VT();
    // },
    async grvThueBao_FocusedRowChanged(row) {
      this.params.p_lblThuHoi = "";
      this.ma_tb_cur = "";
      // this.params.p_LyDoCapTB = 0;
      this.params.p_KetQuaXL = "0";
      this.gridViewThueBao_FocusedRow = row;
      console.log(row , " = gridViewThueBao_FocusedRow")
      if (this.gridViewThueBao_FocusedRow) {
        this.vphieu_cha_id = this.gridViewThueBao_FocusedRow.PHIEU_CHA_ID == null || this.gridViewThueBao_FocusedRow.PHIEU_CHA_ID == "" ? 0: parseInt(this.gridViewThueBao_FocusedRow.PHIEU_CHA_ID);
        this.phieu_id_temp = parseInt(
          this.gridViewThueBao_FocusedRow["PHIEU_ID"] || 0
        );
        this.hdtb_id_temp = parseInt(
          this.gridViewThueBao_FocusedRow["HDTB_ID"] || 0
        );
        this.Enabled.btnNhapMoi = true;

        await this.KHOAMO_TT_THUEBAO(true);
        this.Enabled.btnThuHoi = false;

        await this.HIENTHI_TT_THUEBAO(row);

        let filterA = this.dslh_md
          .filter((a) => a.loaiHdId == this.loaihd_id)
          .map((b) => b.mucDichId);
        var q = this.ds_lydoTC.filter((ds1) =>
          filterA.includes(parseInt(ds1.MUCDICH_ID))
        );
        if (q.length) {
          this.cboLyDoCapTB = q;
        } else {
          this.cboLyDoCapTB = [];
        }
        let phieu_id_chung = 0;
        let dt = this.gridThueBao;
        this.vphieu_id = parseInt(this.gridViewThueBao_FocusedRow["PHIEU_ID"]);
        console.log(this.vphieu_id , " = vphieu_id")
        if (this.params.p_rdioCKb == "1") {
          phieu_id_chung = this.vphieu_id;
        } else {
          phieu_id_chung = parseInt(
            this.gridViewThueBao_FocusedRow["PHIEU_CHA_ID"]
          );
        }
        let ds = await this.getDSNhanVienTheoPhieu({
          phieuId: phieu_id_chung,
          nhanVienId: this.$auth.getNhanVienID(),
          kieuId: 2,
        });
        if (ds.length) {
          this.gridGiaoviec = ds;
        } else {
          this.gridGiaoviec = [];
        }
        //new change
        console.log(this.gridViewThueBao_FocusedRow["LYDO_TON"] , " = LYDO_TON")
        if (dt.length && dt[0].hasOwnProperty("LYDO_ID")) {
          this.params.p_txtGhiChuTon =
            this.gridViewThueBao_FocusedRow["GHICHU"];
          if (this.gridViewThueBao_FocusedRow["LYDO_TON"] == "" || this.gridViewThueBao_FocusedRow["LYDO_TON"] == null) {
            this.params.p_KetQuaXL = "1";
            this.params.p_LyDoTon = this.gridViewThueBao_FocusedRow["LYDO_ID"];
          } else {
            this.params.p_KetQuaXL = "3";
            this.params.p_LyDoTon = this.gridViewThueBao_FocusedRow["LYDO_ID"];
          }
          console.log(this.params.p_KetQuaXL ,  " = p_KetQuaXL")
        }
      } else {
        this.phieu_cha_id_temp = 0;
        this.phieu_id_temp = 0;
        this.hdtb_id_temp = 0;
        this.grcSuDungVT = [];
        this.grcPhieuVT = [];
        this.gridVatTu = [];
        this.Enabled.btnNhapMoi = false;
        this.Enabled.btnThuHoi = false;
        this.Enabled.btnHoanCong = false;

        this.Enabled.txtGhiChuHC = false;
        this.Enabled.cboLyDoCapTB = false;

        //new change
        this.params.p_txtGhiChuTon = "";
        this.params.p_LyDoTon = 0;
        this.params.p_KetQuaXL = "0";
        //

        await this.KHOAMO_TT_THUEBAO(false);
        await this.KHOAMO_TT_VATTU(false);
      }
    },
    async grvPhieuVT_FocusedRowChanged(row) {
      this.grvPhieuVT_FocusedRow = row;
      if (this.grvPhieuVT_FocusedRow) {
        this.Text.lblThongTinVatTuThuHoi = "Thông tin phiếu thu hồi vật tư";
        await this.KHOAMO_TT_VATTU(true);
        this.Enabled.btnThuHoi = false;
        await this.CHON_PHIEU_VT(row);
      } else {
        await this.KHOAMO_TT_VATTU(false);
      }
    },
    async btnThuHoi_SuDungVT_ButtonClick(row_SuDungVT) {
      console.log("row_SuDungVT", row_SuDungVT);
      this.Text.lblThongTinVatTuThuHoi = "Thông tin phiếu thu hồi vật tư (mới)";
      this.vsdvt_id = parseInt(row_SuDungVT["SDVT_ID"]);
      await this.KHOAMO_TT_VATTU(true);
      this.params.p_TinhTrangTbi = null;
      await this.CHON_SUDUNG_VT(row_SuDungVT);
    },
    async cbxTuNgay_CheckedChanged() {
      this.Enabled.dtpTuNgay = this.params.p_cbxTuNgay;
      this.Enabled.dtpDenNgay = this.params.p_cbxTuNgay;
    },
    //c# visible(false)
    async btnTraCuuVatTu_Click() {
      await this.TRACUU_VATTU(true);
    },
    async btnNhapMoi_Click() {
      this.vsdvt_id = 0;
      this.params.p_txtSoLuong = "1";
      if (this.gridViewThueBao_FocusedRow) {
        this.Text.lblThongTinVatTuThuHoi =
          "Thông tin phiếu thu hồi vật tư (mới)";
        this.$refs.txtSerial.focus();
        this.params.p_TrangBi = 4;
        this.params.p_TinhTrangTbi = null;
        await this.KHOAMO_TT_VATTU(true);
        this.Enabled.cboTinhTrangTbi = true;
        this.Enabled.btnNhapMoi = false;
      } else {
        this.$toast.error("Hãy chọn thuê bao cần thu hồi vật tư");
      }
    },
    async txtSoThueBao_KeyUp() {
      this.isKeySearch = true;
      await this.HIENTHI_DANHSACH_THUEBAO(this.params.p_txtSoThueBao);
    },
    async tsbtnTroGiup_Click() {
      this.$toast.success("Hướng dẫn sử dụng");
    },
    async grvPhieuVT_DoubleClick(row) {
      await this.grvPhieuVT_FocusedRowChanged(row);
    },
    async txtSerial_KeyPress() {
      if (this.params.p_txtSerial) {
        this.vsdvt_id = 0;
        await this.TRACUU_VATTU(true);
        this.$refs.txtSerial.focus();
      }
    },
    async rdioCKb_EditValueChanged() {
      this.params.p_txtGhiChuTon = "";
      var str = [];
      if (this.params.p_rdioCKb == "1") {
        this.Enabled.cboLyDoTon = false;
        this.Enabled.txtGhiChuTon = false;
        this.Enabled.chkPhieuTon = true;
        this.params.p_chkPhieuTon = false;
        str = [
          { KQ_ID: "0", KETQUA: "" },
          { KQ_ID: "1", KETQUA: "Thu hồi được" },
          { KQ_ID: "2", KETQUA: "Báo tồn thu hồi" },
          { KQ_ID: "3", KETQUA: "Không thu hồi được" },
        ];
      } else {
        this.Enabled.cboLyDoTon = false;
        this.Enabled.txtGhiChuTon = false;
        this.Enabled.chkPhieuTon = false;
        this.params.p_chkPhieuTon = false;
        str = [
          { KQ_ID: "0", KETQUA: "" },
          { KQ_ID: "1", KETQUA: "Thu hồi được" },
          { KQ_ID: "3", KETQUA: "Không thu hồi được" },
        ];
      }
      this.cboKetQuaXL = str;
      if (this.kieu_goi != 0) {
        await this.HIENTHI_DANHSACH_THUEBAO(this.matb);
      } else {
        await this.HIENTHI_DANHSACH_THUEBAO("");
      }
      this.kieu_goi = 0;
    },
    async tsbtn_ItemClicked() {},
    async btnInPhieu_Click() {
      try {
        var dsIn = this.gridThueBao;
        if (!dsIn.length) {
          this.$toast.error("Không có phiếu để in");
          return;
        }
        let lst_chon = this.gridThueBao.filter((e) =>
          this.rowCheckedTB.includes(e.HDTB_ID)
        );
        if (!lst_chon.length) {
          this.$toast.error("Hãy chọn phiếu");
          return;
        }
        let n_hdkh_id = "";
        let n_hdtb_id = "";
        let n_phieu_id = "";
        for (const iterator of lst_chon) {
          n_hdkh_id += iterator["HDKH_ID"] + ",";
          n_hdtb_id += iterator["HDTB_ID"] + ",";
          n_phieu_id += iterator["PHIEU_ID"] + ",";
        }
        if (n_hdkh_id && n_hdtb_id && n_phieu_id) {
          this.paramsInBB.n_hdkh_id = n_hdkh_id.substring(
            0,
            n_hdkh_id.length - 1
          );
          this.paramsInBB.nvth_id = this.$auth.getNhanVienID();
          this.paramsInBB.cv_thuchien = "Thu hồi vật tư thuê bao";
          this.paramsInBB.n_phieu_id = n_phieu_id.substring(
            0,
            n_phieu_id.length - 1
          );
          this.paramsInBB.n_hdtb_id = n_hdtb_id.substring(
            0,
            n_hdtb_id.length - 1
          );
          this.paramsInBB.huonggiao_id = 3000;
          this.paramsInBB.tentram_tb = "";
          this.$refs.popupInPhieu.showModal();
        }
      } catch (error) {
        this.$toast.error(
          "Lỗi khi in phiếu thu hồi vật tư thuê bao\r\n" + e.message
        );
      }
    },
    async tsbtnHuyBo_Click() {
      this.vsdvt_id = 0;
      this.params.p_txtSoLuong = "1";
      if (this.gridViewThueBao_FocusedRow) {
        this.Text.lblThongTinVatTuThuHoi =
          "Thông tin phiếu thu hồi vật tư (mới)";
        this.$refs.txtSerial.focus();
        this.params.p_TrangBi = 4;
        await this.KHOAMO_TT_VATTU(true);
        //cboTinhTrangTbiPKien.Enabled = true;
        this.Enabled.cboTinhTrangTbi = true;
        this.Enabled.btnNhapMoi = false;
      }
    },
    async tsbtnGiaoViec_Click() {
      await this.GiaoViec();
    },
    async GiaoViec() {
      if (!this.gridViewThueBao_FocusedRow) {
        this.$toast.error("Bạn chưa chọn phiếu vật tư");
        return;
      }
      let dt = this.gridThueBao;
      let phieu_id_chung = 0;
      let vphieu_id = this.gridViewThueBao_FocusedRow["PHIEU_ID"];
      if (this.params.p_rdioCKb == "1") {
        phieu_id_chung = vphieu_id;
      } else {
        phieu_id_chung = parseInt(
          this.gridViewThueBao_FocusedRow["PHIEU_CHA_ID"]
        );
      }
      if (phieu_id_chung == "0") {
        this.$toast.error("Không tìm thấy giao phiếu của thuê bao này");
        return;
      }
      this.params.p_idChung = phieu_id_chung;
      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(
        phieu_id_chung,
        this.$auth.getDonViID(),
        this.$auth.getNhanVienID(),
        this.dateToString(new Date()),
        1
      );
      this.$refs.frnGiaoPhieuNV.vma_tb =
        this.gridViewThueBao_FocusedRow["MA_TB"];
      this.$refs.frnGiaoPhieuNV.vkieugiao = 1;

      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load();
        this.$refs.popupfrnGiaoPhieuNV.show();
      });
    },
    async onGiaoViecConfirm(d) {
      setTimeout(async () => {
        try {
          this.$root.loading(true);
          let ds = await this.getDSNhanVienTheoPhieu({
            phieuId: this.params.p_idChung,
            nhanVienId: this.$auth.getNhanVienID(),
            kieuId: 2,
          });
          console.log("onGiaoViecConfirm", ds);
          this.gridGiaoviec = ds;
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      }, 4000);
    },
    async chkPhanHoi_CheckedChanged() {
      this.Enabled.cboPhanHoi = this.params.p_chkPhanHoi;
      this.Enabled.cboLyDoHuy = this.params.p_chkPhanHoi;
    },
    async btnEdit_ButtonClick(row_SuDungVT) {
      let seri = row_SuDungVT["SERIAL"];
      let seri_new = "";
      this.vsdvt_id = parseInt(row_SuDungVT["SDVT_ID"]);
      let vvattu_id = parseInt(row_SuDungVT["VATTU_ID"]);
      let vvattu_id_new = 0;
      let dt = await this.KiemTraSerialMoiTBaoSuDung({ serial: seri });
      let dt_tbidacap = await this.getDSThietBiDaCapTheoSerialMoi({
        serial: seri,
      });
      // Kiểm tra xem đã từng có ai thu hồi serial này chưa
      if (dt_tbidacap.length) {
        this.$toast.error(
          "Serial " +
            this.params.p_txtSerial +
            " đã được thu hồi vào  ngày " +
            dt_tbidacap[0]["NgayCap"] +
            " bởi user " +
            dt_tbidacap[0]["NguoiCn"] +
            ""
        );
        return;
      }
      // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
      if (dt.length > 1) {
        await this.$bvModal
          .msgBoxConfirm(
            "Hiện đang có " +
              dt.length +
              " thuê bao đang sử dụng " +
              seri +
              ". Bạn có muốn cập nhật lại seri cho thuê bao này không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          )
          .then(async (value) => {
            if (value) {
              await btnEdit.call(this);
            }
          })
          .catch((err) => {
            // An error occurred
          });
      }
      // Nếu có 1 thuê bao thì kiểm tra tiếp
      if (dt.length == 1) {
        //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
        if (dt[0]["thueBaoId"] != this.vthuebao_id) {
          if (dt[0]["phieuVatTuId"] != "0") {
            // Kiemes tra duoc phep sua khong
            this.$toast.error(
              "Serial " +
                seri +
                " đã được sử dụng bởi thuê bao " +
                dt[0]["maThueBao"] +
                ". Bạn không có quyền được thu hồi"
            );
            return;
          }
          await this.$bvModal
            .msgBoxConfirm(
              "Serial " +
                seri +
                " đã được sử dụng bởi thuê bao " +
                dt.Rows[0]["ma_tb"] +
                ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
              {
                okVariant: "success",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              }
            )
            .then(async (value) => {
              if (value) {
                await btnEdit.call(this);
              }
            })
            .catch((err) => {
              // An error occurred
            });
        }
      }
      await btnEdit.call(this);
      async function btnEdit() {
        let ds = await this.getDSVatTu({});
        var f_rm = this.$refs.frmNhapText;
        await f_rm.Load_frmNhapText();
        // f_rm.fields = {value:'vatTuId', text: 'tenVatTu'}
        await f_rm.frmNhapText_4(
          "Serial thu hồi: " + seri + ". Nhập serial bạn muốn thay đổi",
          "Tên vật tư",
          ds
        );
        f_rm.params.p_VatTu = vvattu_id;
        f_rm.Text.title = "Đổi serial";
        f_rm.Text.btnXacNhan = "Đổi";
        f_rm.Text.btnHuyBo = "Hủy";
        f_rm.functionName = "btnEdit";
        this.$refs.popup_frmNhapText.show();
      }
      //Đổi serial
      //Lại có 2 trường hợp
      //1 Giữ nguyễn serial
      //2 Thay đổi serial
    },
    async XacNhan_frmNhapText() {
      var frm = this.$refs.frmNhapText;
      if (frm.functionName == "btnEdit") {
        if (frm.DialogResult == DialogResultObj.OK) {
          let vvattu_id_new = frm.CboOutPut;
          let seri_new = frm.Output;
          let seri = this.grvSuDungVT_FocusedRow.SERIAL;
          if (!seri_new) {
            this.$toast.error("Vui lòng nhập dữ liệu vào ô text Serial!");
            frm.Close();
            return;
          }
          if (seri_new == seri) {
            this.$toast.error("Seri cần thay đổi trùng với seri gốc");
            frm.Close();
            return;
          }
          let dtsd = await this.KiemTraSerialMoiTBaoSuDung({
            serial: seri,
          });
          if (dtsd.length > 1) {
            // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
            await this.$bvModal
              .msgBoxConfirm(
                "Hiện đang có " +
                  dtsd.length +
                  " thuê bao đang sử dụng " +
                  seri_new +
                  ". Bạn có muốn cập nhật lại seri cho thuê bao này không?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              )
              .then(async (value) => {
                if (value) {
                  await btnEdit_2.call(this);
                } else {
                  frm.Close();
                  return;
                }
              })
              .catch((err) => {
                // An error occurred
              });
          }
          if (dtsd.length == 1) {
            // Nếu có 1 thuê bao thì kiểm tra tiếp
            //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
            if (parseInt(dtsd[0]["thueBaoId"]) != this.vthuebao_id) {
              if (parseInt(dtsd[0]["phieuVatTuId"]) != 0) {
                // Kiemes tra duoc phep sua khong
                this.$toast.error(
                  "Serial " +
                    seri_new +
                    " đã được sử dụng bởi thuê bao " +
                    dtsd[0]["maThueBao"] +
                    ". Bạn không có quyền được thu hồi"
                );
                frm.Close();
                return;
              }
              await this.$bvModal
                .msgBoxConfirm(
                  "Serial " +
                    seri_new +
                    " đã được sử dụng bởi thuê bao " +
                    dtsd[0]["maThueBao"] +
                    ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
                  {
                    okVariant: "success",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                    centered: true,
                  }
                )
                .then(async (value) => {
                  if (value) {
                    await btnEdit_2.call(this);
                  } else {
                    frm.Close();
                    return;
                  }
                })
                .catch((err) => {
                  // An error occurred
                });
            }
          }
          // covert code Web
          
          await btnEdit_2.call(this);          

          async function btnEdit_2() {
            try {
              this.$root.loading(true);
              let s = await this.CapNhatSuDungVatTuThuHoi({
                kieu: 1,
                sdVtId: this.vsdvt_id,
                vatTuId: vvattu_id_new,
                thueBaoId: this.vthuebao_id,
                xoaVt: 0,
                kieuPvt: 0,
                lyDoCapTbiId: 0,
                tinhTrangTbiId: 0,
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID(),
                soLuong: 0,
                trangBiId: 0,
                phieuId: 0,
                ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                ghiChu: this.params.p_txtGhiChu,
                serial: seri,
                serialNew: seri_new,
              });
              if (s != "1") {
                this.$toast.error(s);
              }
              await this.NAP_DS_SUDUNG_VT();
            } catch (error) {
              console.log(error);
              this.$toast.error(error.data.message);
            } finally {
              this.$root.loading(false);
            }
          }
        }
      }

      if (frm.functionName == "ThuHoiVatTu_V2") {
        if (frm.DialogResult == DialogResultObj.Abort) {
          return;
        }
        if (frm.DialogResult == DialogResultObj.OK) {
          // nếu thay đổi serial
          let vvattu_id = frm.CboOutPut; //ok
          let vserial = frm.Output;
          this.vsdvt_id = 0;
          let kieu_pvt = 1; // Đổi serial -- 1 2 cua thue bao khac
          await this.KiemTra(vserial, vvattu_id, kieu_pvt);

          // code web covert => quy
          // let phieu_vt_id  = this.phvt_id
          // await this.Final_ThuHoiVatTu_V2(phieu_vt_id)
        }
        // code web covert => quy
        else {
          // Nếu không đổi
          let vvattu_id = frm.CboOutPut; // ok
          let vserial = txtSerial.Text;
          this.vsdvt_id = 0;
          let kieu_pvt = 2; //Không đổi
          await this.KiemTraVT(vvattu_id, vserial, kieu_pvt);
        }
      }
      if (frm.functionName == "lyDoKhongTaiSD") {
        if (frm.DialogResult == DialogResultObj.OK) {
          this.paramThuHoiSTB2515.not_resuse_dame = false;
          this.paramThuHoiSTB2515.not_resue_reason = frm.Output;
          let k = await this.finalThuHoi_STB_2515(
            this.paramThuHoiSTB2515.user_name,
            this.paramThuHoiSTB2515.stb_serial,
            this.paramThuHoiSTB2515.stb_serial_new,
            this.paramThuHoiSTB2515.stb_type,
            this.paramThuHoiSTB2515.date,
            this.paramThuHoiSTB2515.note,
            this.paramThuHoiSTB2515.isback,
            this.paramThuHoiSTB2515.notback_reason,
            this.paramThuHoiSTB2515.resue,
            this.paramThuHoiSTB2515.not_resuse_dame,
            this.paramThuHoiSTB2515.not_resue_reason
          );
          if (k == 1) {
            await this.thuHoiIsConfirm(this.paramThuHoiSTB2515.k);
          }
        } else if (frm.DialogResult == DialogResultObj.Cancel) {
          this.paramThuHoiSTB2515.not_resuse_dame = true;
          this.paramThuHoiSTB2515.not_resue_reason = "";
          let k = await this.finalThuHoi_STB_2515(
            this.paramThuHoiSTB2515.user_name,
            this.paramThuHoiSTB2515.stb_serial,
            this.paramThuHoiSTB2515.stb_serial_new,
            this.paramThuHoiSTB2515.stb_type,
            this.paramThuHoiSTB2515.date,
            this.paramThuHoiSTB2515.note,
            this.paramThuHoiSTB2515.isback,
            this.paramThuHoiSTB2515.notback_reason,
            this.paramThuHoiSTB2515.resue,
            this.paramThuHoiSTB2515.not_resuse_dame,
            this.paramThuHoiSTB2515.not_resue_reason
          );
          if (k == 1) {
            await this.thuHoiIsConfirm(this.paramThuHoiSTB2515.k);
          }
        } else {
          return "0";
        }
        //thieu api goi ngoai chò
      }
    },
    async KiemTraDuLieu_Th() {
      console.log("this.params.p_txtSerial", this.params.p_txtSerial);
      let boqua = false;
      var z = await this.getDSThaoTacTheoCode({ code: "BQ_GV_THUHOI" });
      if (z.length > 0) {
        boqua = true;
      }
      if (this.vthuebao_id <= 0) {
        this.$toast.error("Bạn phải chọn thuê bao");
        return false;
      }
      if (!this.params.p_VatTu || !this.params.p_VatTu) {
        this.$toast.error("Bạn phải chọn vật tư");
        return false;
      }
      if (this.params.p_txtSoLuong == "0") {
        this.$toast.error("Bạn phải nhập số lượng");
        return false;
      }
      if (!this.params.p_TinhTrangTbi) {
        this.$toast.error("Bạn chưa chọn tình trạng thiết bị");
        return false;
      }
      this.vloaitbi_id = 0;
      var r = this.dt_vattu.filter(
        (a) => a.vatTuId && a.vatTuId == this.params.p_VatTu
      );
      if (r.length) {
        r = r[0];
        if (r["loaiThietBiId"] == null || r["loaiThietBiId"] == "") {
          this.$toast.error("Vật tư chưa được thiết lập loại thiết bị");
          return false;
        }
        if (r["loaiThietBiId"] == "2") {
          if (this.params.p_txtSerial == "") {
            this.$toast.error(
              "Loại vật tư này thuộc loại phải có seri. Ban hãy nhập serial cho thiết bị"
            );
            this.$refs.txtSerial.focus();
            return false;
          }
          if (this.params.p_txtSerial != "") {
            if (this.params.p_txtSoLuong != "1") {
              this.$toast.error("Vật tư này có seri, số lượng phải nhập = 1");
              this.$refs.txtSoLuong.focus();
              return false;
            }
            let serialPatterns = this.check_serial;
            if (
              !this.params.p_txtSerial.match(serialPatterns) &&
              this.$auth.getMaTinh() != "TNH" &&
              this.$auth.getMaTinh() != "HNI" &&
              this.$auth.getMaTinh() != "HNM"
            ) {
              this.$toast.error(
                "Serial có ký tự không hợp lệ. Các ký tự cho phép [A-Z,0-9]"
              );
              this.$refs.txtSerial.focus();
              return false;
            }
          }
        } else {
          if (this.params.p_txtSerial) {
            this.$toast.error(
              "Loại vật tư này thuộc loại phải không có seri. Ban hãy xóa dữ liệu serial"
            );
            this.$refs.txtSerial.focus();
            return false;
          }
        }
        this.vloaitbi_id = parseInt(r["loaiThietBiId"]);
      }
      if (!this.gridGiaoviec.length && boqua == false) {
        this.$toast.error("Bạn chưa giao việc cho nhân viên!");
        return false;
      }
      return true;
    },
    async Serial_Thuc_Te() {
      if (this.$auth.getMaTinh() == "HNI") {
        if (this.params.p_txtSerial != "") {
          if (this.params.p_txtGhiChu == "") {
            await this.$bvModal
              .msgBoxConfirm(
                "Serial thực tế đang trống! Bạn có muốn tự động điền Serial không?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              )
              .then(async (value) => {
                if (value) {
                  this.params.p_txtGhiChu = this.vserial;
                } else {
                  return;
                }
              })
              .catch((err) => {
                // An error occurred
              });
          }
        }
      }
    },
    async ThuHoiVatTu_V2() {
      //Chia 2 truong hop thu hoi moi va sua phieu_vt
      //Truong hop 1 thu hoi moi
      let vseria_goc = this.params.p_txtSerial;
      let vserial = "";
      let vvattu_id = 0;
      let kieu_pvt = 0;
      let phieu_vt_id = 0;
      phieu_vt_id = this.phvt_id;
      let xoavt = 0;

      if (this.Enabled.btnNhapMoi == false) {
        // Đoạn này kiểm tra các điều kiện cần thiết
        if (await this.KiemTraDuLieu_Th()) {
          //Nếu là serial
          if (
            this.params.p_txtSerial != "" &&
            this.params.p_txtSerial != null
          ) {
            if (
              this.check_serial_gp &&
              this.params.p_txtSerial.startsWith("VNPT")
            ) {
              var check_gp = await this.KiemTraSerialThuHoi({
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                serial: this.params.p_txtSerial,
                sdVtId: this.vsdvt_id,
              });
              if (check_gp != "1") {
                return this.$toast.error(check_gp);
              }
            }
            try {
              this.loading(true);
              var checklogic = await this.KiemTraVatTuThuHoi({
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                serial: this.params.p_txtSerial,
              });
              console.log("checklogic = ", checklogic);
              if (checklogic != "1") {
                return this.$toast.error(checklogic);
              }
            } catch (err) {
              this.$toast.error(err.data.message);
              return;
            } finally {
              this.loading(false);
            }
            var dt = await this.KiemTraSerialMoiTBaoSuDung({
              serial: this.params.p_txtSerial,
            });
            let dt_tbidacap = await this.getDSThietBiDaCapTheoSerialMoi({
              serial: this.params.p_txtSerial,
            });
            // Kiểm tra xem đã từng có ai thu hồi serial này chưa

            if (dt_tbidacap.length > 0) {
              return this.$toast.error(
                "Serial " +
                  this.params.p_txtSerial +
                  " đã được thu hồi vào  ngày " +
                  dt_tbidacap[0]["NgayCap"] +
                  " bởi user " +
                  dt_tbidacap[0]["NguoiCn"] +
                  ""
              );
            }
            // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
            if (dt.length > 1) {
              return this.$toast.error(
                "Bạn hãy kiểm tra lại " +
                  this.params.p_txtSerial +
                  ". Hiện đang có " +
                  dt.length +
                  " thuê bao đang sử dụng."
              );
            }
            // Nếu có 1 thuê bao thì kiểm tra tiếp
            // Chưa xong
            if (dt.length == 1) {
              //Nếu thuê bao tìm thấy khác với thuê bao đang chọn
              if (dt[0]["thueBaoId"] != this.vthuebao_id) {
                if (dt[0]["phieuVatTuId"] != "0") {
                  // Kiemes tra duoc phep sua khong
                  this.$toast.error(
                    "Serial " +
                      this.params.p_txtSerial +
                      " đã được sử dụng bởi thuê bao " +
                      dt[0]["maThueBao"] +
                      ". Bạn không có quyền được thu hồi"
                  );
                  return;
                }
                let isConfirm = await this.$bvModal.msgBoxConfirm(
                  "Serial " +
                    this.params.p_txtSerial +
                    " đã được sử dụng bởi thuê bao " +
                    dt[0]["maThueBao"] +
                    ". Bạn có chắc chắn muốn tiếp tục thu hồi không?",
                  {
                    okVariant: "success",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                    centered: true,
                  }
                );
                if (!isConfirm) return;
                let ds = await this.getDSVatTu({});
                var frm = this.$refs.frmNhapText;
                await frm.Load_frmNhapText();
                await frm.frmNhapText_4(
                  "Serial thu hồi: " +
                    this.params.p_txtSerial +
                    ". Nhập serial mới nếu serial thu hồi sai",
                  "Tên vật tư",
                  ds
                );
                frm.params.p_VatTu = this.params.p_VatTu;
                frm.Text.title = "Đổi serial";
                frm.Text.btnXacNhan = "Đổi";
                frm.Text.btnHuyBo = "Không đổi";
                frm.Visible.btnHuyThat = true;
                frm.functionName = "ThuHoiVatTu_V2";
                this.$refs.popup_frmNhapText.show();
              }

              this.vsdvt_id = parseInt(dt[0]["suDungVtId"]);
              vserial = this.params.p_txtSerial;
              kieu_pvt = 4; //Không đổi

              await this.KiemTraVT(vvattu_id, vserial, kieu_pvt);
            }
            //xong
            if (dt.length <= 0) {
              await this.$bvModal
                .msgBoxConfirm(
                  "Không tìm thấy thuê bao nào đang sử dụng seri này.Bạn có muốn tiếp tục thu hồi không?",
                  {
                    okVariant: "success",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                    centered: true,
                  }
                )
                .then(async (value) => {
                  if (value) {
                    kieu_pvt = 3; //Khong tim thay cua thue bao nao
                    this.vsdvt_id = 0;
                    vvattu_id = parseInt(this.params.p_VatTu);
                    vserial = this.params.p_txtSerial;
                    await this.KiemTraVT(vvattu_id, vserial, kieu_pvt);
                  }
                })
                .catch((err) => {
                  this.$toast.error(err.data.message);
                });
            }

            //Cuối cùng vẫn là kiểm tra xem mã vật tư chọn kia số lượng >1 không
            // Có lớn hơn 1 thì sẽ hỏi bạn có muốn thu hồi cả 2 thiết bị không nếu có thì
          }
          // quy , bo sung else 27/11
          else {
            await this.Final_ThuHoiVatTu_V2(phieu_vt_id);
          }
        }
      } else {
        if (this.Enabled.btnNhapMoi) {
          if (phieu_vt_id == 0) {
            this.$toast.error(
              "Chưa có thông tin về phiếu vật tư. Bạn hãy ấn nhập mới và thao tác lại! "
            );
            return;
          }
        }
        // Đoạn này kiểm tra các điều kiện cần thiết
        if (await this.KiemTraDuLieu_Th()) {
          let phieu_id = 0;
          if (this.params.p_rdioCKb == "1") {
            phieu_id = this.vphieu_id;
          } else {
            phieu_id = this.vphieu_cha_id;
          }
          await this.Serial_Thuc_Te();
          await this.CapNhatPhieuVatTuThuHoi({
            sdVtId: this.vsdvt_id,
            insert: 2,
            phieuVtId: phieu_vt_id,
            hdTbId: this.vhdtb_id,
            baoHongId: 0,
            phieuId: phieu_id,
            thueBaoId: this.thuebao_id,
            donViId: this.$auth.getDonViID(),
            nhanVienId: this.$auth.getNhanVienID(),
            tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
            serial: vseria_goc,
            ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
            vatTuId: this.params.p_VatTu,
            thietBiId: 0,
            soLuong: parseInt(this.params.p_txtSoLuong),
            lyDoCapId: parseInt(this.params.p_LyDoCapTB),
            lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
            trangBiId: parseInt(this.params.p_TrangBi),
            kieu: 1,
            ghiChu: this.params.p_txtGhiChu,
          });
          this.$toast.success("Cập nhật dữ liệu thành công");
        }
        this.LoadDS();
      }
    },
    async KiemTra(vserial, vvattu_id, kieu_pvt) {
      let dt_sd = await this.KiemTraSerialMoiTBaoSuDung({
        serial: vserial,
      });
      let dt_tbidacap_sd = await this.getDSThietBiDaCapTheoSerialMoi({
        serial: vserial,
      });
      // Kiểm tra xem đã từng có ai thu hồi serial này chưa
      if (dt_tbidacap_sd.length > 0) {
        this.$toast.error(
          "Serial " +
            vserial +
            " đã được thu hồi vào  ngày " +
            dt_tbidacap_sd[0]["NgayCap"] +
            " bởi user " +
            dt_tbidacap_sd[0]["NguoiCn"] +
            ""
        );
        return;
      }
      // Nếu có 2 thuê bao dùng cùng 1 serial phải xem lại dữ liệu
      if (dt_sd.length > 1) {
        this.$toast.error(
          "Bạn hãy kiểm tra lại " +
            vserial +
            ". Hiện đang có " +
            dt_sd.length +
            " thuê bao đang sử dụng."
        );
        return;
      }
      // Nếu có 1 thuê bao thì kiểm tra tiếp
      if (dt_sd.length == 1) {
        // Kiemes tra duoc phep sua khong
        if (dt[0]["thueBaoId"] != this.vthuebao_id) {
          this.$toast.error(
            "Serial " +
              vserial +
              " đã được sử dụng bởi thuê bao " +
              dt_sd[0]["maThueBao"] +
              ". Bạn hãy chọn serial khác"
          );
          return;
        }
      }
      await this.KiemTraVT(vvattu_id, vserial, kieu_pvt);
    },
    async KiemTraVT(vvattu_id, vserial, kieu_pvt) {
      var slvt = await this.getSoLuongVatTuThueBao({
        thueBaoId: this.vthuebao_id,
        hdTbId: this.vhdtb_id,
        vatTuId: vvattu_id,
      });
      let xoavt = 0;
      //Nếu là trường hợp đổi serial thì không cho

      if (slvt.length > 0 && vserial != "") {
        return this.$toast.error(
          "Mã vật tư bạn chọn thuê bao đang sử nhiều hơn 1"
        );
      } else if (slvt.length > 1 && kieu_pvt == 4) {
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          "Thuê bao này đang sử dụng hơn 2 thiết bị cùng mã vật tư. " +
            "Bạn có chắc chắn muốn thu hồi cả hai không?. Nếu bạn chọn không thì sẽ xóa vật tư còn lại.",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (isConfirm) {
          xoavt = 0;
          await this.ThuHoi_VT(
            vvattu_id,
            xoavt,
            kieu_pvt,
            this.params.p_txtSerial,
            vserial
          );
        } else {
          xoavt = 1;
          await this.ThuHoi_VT(
            vvattu_id,
            xoavt,
            kieu_pvt,
            this.params.p_txtSerial,
            vserial
          );
        }
      } else {
        await this.ThuHoi_VT(
          vvattu_id,
          xoavt,
          kieu_pvt,
          this.params.p_txtSerial,
          vserial
        );
      }
    },
    async ThuHoi_VT(vvattu_id, xoavt, kieu_pvt, vseria_goc, vserial) {
      let phieu_id = 0;
      if (this.params.p_rdioCKb == "1") {
        phieu_id = this.vphieu_id;
      } else {
        phieu_id = this.vphieu_cha_id;
      }
      await this.Serial_Thuc_Te();
      var s = await this.CapNhatSuDungVatTuThuHoi({
        kieu: 2,
        sdVtId: this.vsdvt_id,
        vatTuId: vvattu_id,
        thueBaoId: this.vthuebao_id,
        xoaVt: xoavt,
        kieuPvt: kieu_pvt,
        lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
        tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
        hdTbId: this.vhdtb_id,
        baoHongId: 0,
        donViId: this.$auth.getDonViID(),
        nhanVienId: this.$auth.getNhanVienID(),
        soLuong: parseInt(this.params.p_txtSoLuong),
        trangBiId: parseInt(this.params.p_TrangBi),
        phieuId: phieu_id,
        ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
        ghiChu: this.params.p_txtGhiChu,
        serial: vseria_goc,
        serialNew: vserial,
      });
      if (s != "1") {
        this.$toast.error(s);
      }
      //Nhảy xuonogss code thu hồi và giữ lại con kia
      //Nếu không thì sẽ xóa 1 thiết bị và thu hồi 1 thiết Jump code thu hồi
      //Không thì bắt đầu thu hồi.
      // Lưu lịch sử trước khi bắt đầu thay đổi
      //Code thu hồi
      //Nếu đổi serial thì giữ nguyễn serial ở Thue bao xyz
      //Nếu không đổi serial thì serial ở thuê bao xyz đổi sang seri TAM....
      //SEri thu hồi về sẽ là trường hợp chưa từng có trong hệ thống.

      // bổ sung, covert do code web => quy
      let phieu_vt_id = this.phvt_id;
      await this.Final_ThuHoiVatTu_V2(phieu_vt_id);
    },
    async Final_ThuHoiVatTu_V2(phieu_vt_id) {
      let kieu = 0;
      let vphieuchung_id = 0;
      if (this.params.p_rdioCKb == "1") {
        vphieuchung_id = this.vphieu_id;
      } else {
        vphieuchung_id = this.vphieu_cha_id;
      }
      let lydo = 0;
      if (this.params.p_LyDoCapTB != null && this.params.p_LyDoCapTB) {
        lydo = parseInt(this.params.p_LyDoCapTB);
      }

      if (this.vloaitbi_id == 3) {
        try {
          if (this.vsdvt_id > 0) {
            kieu = 1;
            let ktsl = await this.getTongSoLuong({ sdVtId: this.vsdvt_id });
            if (ktsl && ktsl != "0") {
              let sl_treo = parseInt(ktsl);
              if (
                this.sl_goc - sl_treo <= parseInt(this.params.p_txtSoLuong) ||
                this.sl_goc - sl_treo <= 0
              ) {
                return this.$toast.error(
                  "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                    sl_treo +
                    "/" +
                    this.sl_goc +
                    ". Bạn hãy kiểm tra lại số lượng thu hồi"
                );
              }
            }

            if (!this.Enabled.btnNhapMoi) {
              this.$root.loading(true);
              await this.CapNhatPhieuVatTuThuHoi({
                sdVtId: this.vsdvt_id,
                insert: 1,
                phieuVtId: 0,
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                phieuId: vphieuchung_id,
                thueBaoId: this.thuebao_id,
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID(),
                tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                serial: "CUONG1094",
                ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                vatTuId: this.params.p_VatTu,
                thietBiId: 0,
                soLuong: parseInt(this.params.p_txtSoLuong),
                lyDoCapId: lydo,
                lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                trangBiId: parseInt(this.params.p_TrangBi),
                kieu: kieu,
                ghiChu: this.params.p_txtGhiChu,
              });
              this.$toast.success("Thu hồi thành công");
            }
            if (this.Enabled.btnNhapMoi) {
              await this.$bvModal
                .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                })
                .then(async (value) => {
                  if (value) {
                    this.$root.loading(true);
                    await this.CapNhatPhieuVatTuThuHoi({
                      sdVtId: this.vsdvt_id,
                      insert: 2,
                      phieuVtId: phieu_vt_id,
                      hdTbId: this.vhdtb_id,
                      baoHongId: 0,
                      phieuId: vphieuchung_id,
                      thueBaoId: this.thuebao_id,
                      donViId: this.$auth.getDonViID(),
                      nhanVienId: this.$auth.getNhanVienID(),
                      tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                      serial: "CUONG1094",
                      ngayTh: this.dateToString(
                        new Date(),
                        "DD/MM/YYYY HH:mm:ss"
                      ),
                      vatTuId: this.params.p_VatTu,
                      thietBiId: 0,
                      soLuong: parseInt(this.params.p_txtSoLuong),
                      lyDoCapId: lydo,
                      lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                      trangBiId: parseInt(this.params.p_TrangBi),
                      kieu: kieu,
                      ghiChu: this.params.p_txtGhiChu,
                    });
                    this.$toast.success("Cập nhật dữ liệu thành công");
                    return await this.LoadDS();
                  } else {
                    return;
                  }
                })
                .catch((err) => {
                  // An error occurred
                });
            }
            return await this.LoadDS();
          }

          kieu = 3;

          if (!this.Enabled.btnNhapMoi) {
            this.$root.loading(true);
            await this.CapNhatPhieuVatTuThuHoi({
              sdVtId: this.vsdvt_id,
              insert: 1,
              phieuVtId: 0,
              hdTbId: this.vhdtb_id,
              baoHongId: 0,
              phieuId: vphieuchung_id,
              thueBaoId: this.thuebao_id,
              donViId: this.$auth.getDonViID(),
              nhanVienId: this.$auth.getNhanVienID(),
              tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
              serial: "CUONG1094",
              ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
              vatTuId: this.params.p_VatTu,
              thietBiId: 0,
              soLuong: parseInt(this.params.p_txtSoLuong),
              lyDoCapId: lydo,
              lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
              trangBiId: parseInt(this.params.p_TrangBi),
              kieu: kieu,
              ghiChu: this.params.p_txtGhiChu,
            });
            this.$toast.success("Thu hồi thành công");
          }

          if (this.Enabled.btnNhapMoi) {
            await this.$bvModal
              .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                okVariant: "success",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
                centered: true,
              })
              .then(async (value) => {
                if (value) {
                  this.$root.loading(true);
                  await this.CapNhatPhieuVatTuThuHoi({
                    sdVtId: this.vsdvt_id,
                    insert: 2,
                    phieuVtId: phieu_vt_id,
                    hdTbId: this.vhdtb_id,
                    baoHongId: 0,
                    phieuId: vphieuchung_id,
                    thueBaoId: this.thuebao_id,
                    donViId: this.$auth.getDonViID(),
                    nhanVienId: this.$auth.getNhanVienID(),
                    tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                    serial: "CUONG1094",
                    ngayTh: this.dateToString(
                      new Date(),
                      "DD/MM/YYYY HH:mm:ss"
                    ),
                    vatTuId: this.params.p_VatTu,
                    thietBiId: 0,
                    soLuong: parseInt(this.params.p_txtSoLuong),
                    lyDoCapId: lydo,
                    lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                    trangBiId: parseInt(this.params.p_TrangBi),
                    kieu: kieu,
                    ghiChu: this.params.p_txtGhiChu,
                  });
                  this.$toast.success("Cập nhật dữ liệu thành công");
                  return await this.LoadDS();
                } else {
                  return;
                }
              })
              .catch((err) => {
                // An error occurred
              });
          }
          return await this.LoadDS();
        } catch (error) {
          console.log(error);
          if (error.data.message) this.$toast.error(error.data.message);
          else this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      }

      if (this.vloaitbi_id != 3 && this.vloaitbi_id != 2) {
        try {
          if (this.vsdvt_id > 0) {
            kieu = 1;
            let ktsl = await this.getTongSoLuong({ sdVtId: this.vsdvt_id });
            if (ktsl && ktsl != "0") {
              let sl_treo = parseInt(ktsl);
              if (
                this.sl_goc - sl_treo <= parseInt(this.params.p_txtSoLuong) ||
                this.sl_goc - sl_treo <= 0
              ) {
                this.$toast.error(
                  "Hiện số lượng cấp thu hồi của thuê chưa QT là " +
                    sl_treo +
                    "/" +
                    this.sl_goc +
                    ". Bạn hãy kiểm tra lại số lượng thu hồi"
                );
                return;
              }
            }
            // Insert PVT
            if (!this.Enabled.btnNhapMoi) {
              this.$root.loading(true);
              await this.CapNhatPhieuVatTuThuHoi({
                sdVtId: this.vsdvt_id,
                insert: 1,
                phieuVtId: 0,
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                phieuId: vphieuchung_id,
                thueBaoId: this.thuebao_id,
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID(),
                tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                serial: "DAYTB",
                ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                vatTuId: this.params.p_VatTu,
                thietBiId: 0,
                soLuong: parseInt(this.params.p_txtSoLuong),
                lyDoCapId: lydo,
                lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                trangBiId: parseInt(this.params.p_TrangBi),
                kieu: kieu,
                ghiChu: this.params.p_txtGhiChu,
              });
              this.$toast.success("Thu hồi thành công");
            }
            if (this.Enabled.btnNhapMoi) {
              await this.$bvModal
                .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                })
                .then(async (value) => {
                  if (value) {
                    this.$root.loading(true);
                    let kq = await this.CapNhatPhieuVatTuThuHoi({
                      sdVtId: this.vsdvt_id,
                      insert: 2,
                      phieuVtId: phieu_vt_id,
                      hdTbId: this.vhdtb_id,
                      baoHongId: 0,
                      phieuId: vphieuchung_id,
                      thueBaoId: this.thuebao_id,
                      donViId: this.$auth.getDonViID(),
                      nhanVienId: this.$auth.getNhanVienID(),
                      tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                      serial: "DAYTB",
                      ngayTh: this.dateToString(
                        new Date(),
                        "DD/MM/YYYY HH:mm:ss"
                      ),
                      vatTuId: this.params.p_VatTu,
                      thietBiId: 0,
                      soLuong: parseInt(this.params.p_txtSoLuong),
                      lyDoCapId: lydo,
                      lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                      trangBiId: parseInt(this.params.p_TrangBi),
                      kieu: kieu,
                      ghiChu: this.params.p_txtGhiChu,
                    });
                    console.log(kq);
                    this.$toast.success("Cập nhật dữ liệu thành công");
                  } else {
                    return;
                  }
                })
                .catch((err) => {
                  // An error occurred
                });
            }
          } else {
            kieu = 1;
            if (this.Enabled.btnNhapMoi) {
              await this.$bvModal
                .msgBoxConfirm("Bạn có muốn cập nhật dữ liệu không?", {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                })
                .then(async (value) => {
                  if (value) {
                    this.$root.loading(true);
                    let kq = await this.CapNhatPhieuVatTuThuHoi({
                      sdVtId: this.vsdvt_id,
                      insert: 2,
                      phieuVtId: phieu_vt_id,
                      hdTbId: this.vhdtb_id,
                      baoHongId: 0,
                      phieuId: vphieuchung_id,
                      thueBaoId: this.thuebao_id,
                      donViId: this.$auth.getDonViID(),
                      nhanVienId: this.$auth.getNhanVienID(),
                      tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                      serial: "DAYTB",
                      ngayTh: this.dateToString(
                        new Date(),
                        "DD/MM/YYYY HH:mm:ss"
                      ),
                      vatTuId: this.params.p_VatTu,
                      thietBiId: 0,
                      soLuong: parseInt(this.params.p_txtSoLuong),
                      lyDoCapId: lydo,
                      lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                      trangBiId: parseInt(this.params.p_TrangBi),
                      kieu: kieu,
                      ghiChu: this.params.p_txtGhiChu,
                    });
                    console.log(kq);
                    this.$toast.success("Cập nhật dữ liệu thành công");
                  } else {
                    return;
                  }
                })
                .catch((err) => {
                  // An error occurred
                });
            }
            if (!this.Enabled.btnNhapMoi) {
              this.$root.loading(true);
              let kq = await this.CapNhatPhieuVatTuThuHoi({
                sdVtId: this.vsdvt_id,
                insert: 1,
                phieuVtId: 0,
                hdTbId: this.vhdtb_id,
                baoHongId: 0,
                phieuId: vphieuchung_id,
                thueBaoId: this.thuebao_id,
                donViId: this.$auth.getDonViID(),
                nhanVienId: this.$auth.getNhanVienID(),
                tinhTrangTbiId: parseInt(this.params.p_TinhTrangTbi),
                serial: "DAYTB",
                ngayTh: this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                vatTuId: this.params.p_VatTu,
                thietBiId: 0,
                soLuong: parseInt(this.params.p_txtSoLuong),
                lyDoCapId: lydo,
                lyDoCapTbiId: parseInt(this.params.p_LyDoCap),
                trangBiId: parseInt(this.params.p_TrangBi),
                kieu: kieu,
                ghiChu: this.params.p_txtGhiChu,
              });
              console.log(kq);
              this.$toast.success("Thu hồi thành công");
            }
          }
          return await this.LoadDS();
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      }
      return await this.LoadDS();
    },
    async LoadDS() {
      try {
        this.loading(true);
        await this.NAP_DS_PHIEU_VT();
        await this.NAP_DS_SUDUNG_VT();
      } catch (error) {
        this.loading(false);
        console.log(error);
      }
    },
    async grvSuDungVT_CustomRowCellEdit() {
      // if (e.Column == colSua && e.RowHandle >= 0)
      // {
      //     if ((grvSuDungVT.GetRowCellValue(e.RowHandle, "PHIEUVT_ID") == DBNull.Value || grvSuDungVT.GetRowCellValue(e.RowHandle, "PHIEUVT_ID") == "0")
      //         && (grvSuDungVT.GetRowCellValue(e.RowHandle, "SERIAL") != DBNull.Value || grvSuDungVT.GetRowCellValue(e.RowHandle, "SERIAL") != ""))
      //         e.RepositoryItem = btnEdit;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
    },
    async cboKetQuaXL_EditValueChanged() {
      try {
        let kq_id = parseInt(this.params.p_KetQuaXL);
        let vnhom_ton = 0;
        if (kq_id == 2) {
          this.Text.label3 = "Lý do không thu hồi";
          this.Enabled.cboLyDoTon = false;
          this.Enabled.txtGhiChuTon = false;
          this.Enabled.tsbtnTon = true;
          await this.KHOAMO_TT_VATTU(false);
        } else if (kq_id == 3) {
          vnhom_ton = 8;
          this.Text.label3 = "Lý do thu hồi";
          this.Enabled.cboLyDoTon = true;
          this.Enabled.txtGhiChuTon = true;
          this.Enabled.tsbtnTon = false;
          await this.KHOAMO_TT_VATTU(false);
        } else {
          this.Text.label3 = "Lý do tồn";
          this.Enabled.cboLyDoTon = false;
          this.Enabled.txtGhiChuTon = kq_id == 1;
          this.Enabled.tsbtnTon = false;
          await this.KHOAMO_TT_VATTU(true);
          this.Enabled.btnThuHoi = false;
        }
        this.cboLyDoTon = await this.getDSLyDoTon({ nhomTonId: vnhom_ton });
      } catch (error) {
        console.log(error);
      }
    },
    async tsbtnTon_Click() {
      await this.$refs.frmGiaoPhieu_Ton.showPhieuTon(this.vphieu_id, 6, null);
    },
    async phieuTonModalDlgClose() {
      await this.HIENTHI_DANHSACH_THUEBAO("");
    },
    async chkPhieuTon_CheckedChanged() {
      if (this.params.p_rdioCKb == "1") {
        await this.HIENTHI_DANHSACH_THUEBAO("");
      }
    },
    async TaoDL_Ton_Khong_ThuHoi_Duoc(themmoi) {
      this.dston = [];
      let row = {};
      row.PHIEU_ID = this.vphieu_id;
      row.LYDOTONTC_ID = parseInt(this.params.p_LyDoTon);
      row.NGAY_BT = this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss");
      row.NHANVIEN_BT_ID = this.$auth.getNhanVienID();
      row.DONVI_BT_ID = this.$auth.getDonViID();
      row.NGUOI_CN = this.$auth.getUserName(); //tt_nd.ma_nd;
      row.NGAY_CN = this.dateToString(new Date(), "DD/MM/YYYY HH:mm:ss"); //tt_nd.ngay_cn;
      row.MAY_CN = this.$auth.getUserName(); //tt_nd.may_cn;
      row.GHICHU = this.params.p_txtGhiChuTon;
      this.dston.push(row);
    },
    async ckbChonTK_CheckedChanged() {
      if (this.params.p_ckbChonTK) {
        this.Text.ckbChonTK = "Serial";
      } else {
        this.Text.ckbChonTK = "Số máy/Acc";
      }
    },
    async inBBToolStripMenuItem_Click() {
      // var frmBC = new frmXemBaoCao("BBVTTH", new[] { vhdtb_id, tt_nd.nhanvien_id });
      // frmBC.XemNgay();
    },
    async btnXuatExcel_DSTT_Click() {
      if (this.gridThueBao.length <= 0) {
        return this.$toast.error(
          "Danh sách thu hồi vật tư thuê bao đang trống!"
        );
      }
      await ExportExcelCustom(
        gridTBColumns,
        this.gridThueBao,
        "FileDSVTThueBao",
        "export"
      );
    },
    async repositoryItemButtonEdit1_ButtonClick() {
      // if (gridViewThueBao.FocusedColumn.FieldName == "DIACHI_LD")
      // {
      //     var frm = new frmXemVB("Địa chỉ lắp đặt", gridViewThueBao.GetFocusedRowCellValue("DIACHI_LD"), false);
      //     frm.ShowDialog(this);
      // }
      // else
      //     if (gridViewThueBao.FocusedColumn.FieldName == "GHICHU")
      //     {
      //         var frm = new frmXemVB("Ghi chú", gridViewThueBao.GetFocusedRowCellValue("GHICHU"), false);
      //         frm.ShowDialog(this);
      //     }
    },
    async cboDVQL_EditValueChanged() {
      this.cboDonVI = [];
      if (!this.params.p_DVQL) {
        return;
      }
      this.cboDonVI = await this.getDanhMucDonViQuanLy({
        donViQl: this.params.p_DVQL,
        loaiDvi: this.LOAI_DV.VETINH,
      });
      this.params.p_DonVI = this.setFirstValue(this.cboDonVI, "DONVI_ID");
    },
    async tsbtnChuyenTo_Click() {
      try {
        if (!this.params.p_DonVI) {
          this.$toast.error("Bạn chưa chọn đơn vị cần chuyển tổ");
          return;
        }
        if (this.params.p_txtNoiDung == "") {
          this.$toast.error("Bạn chưa nhập nội dung chuyển tổ");
          return;
        }

        if (
          this.gridViewThueBao_FocusedRow.hasOwnProperty("DONVI_NHAN_ID") &&
          !this.gridViewThueBao_FocusedRow["DONVI_NHAN_ID"]
        ) {
          this.$toast.error("Không lấy được thông tin đơn vị");
          return;
        }
        if (
          this.params.p_DonVI ==
          this.gridViewThueBao_FocusedRow["DONVI_NHAN_ID"]
        ) {
          this.$toast.error(
            "Đơn vị chuyển tổ không được trùng với đơn vị hiện tại"
          );
          return;
        }
        if (this.params.p_rdioCKb == "1") {
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            "Bạn có muốn chuyển phiếu sang tổ " +
              this.cboDonVI.find((a) => a.DONVI_ID == this.params.p_DonVI)
                .TEN_DV +
              " không?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (isConfirm) {
            this.$root.loading(true);
            // await this.CapNhatChuyenPhieuTo({
            //   donViId: this.params.p_DonVI,
            //   nhanVienId: this.$auth.getNhanVienID(),
            //   phieuId: this.vphieu_id,
            //   noiDung: this.params.p_txtNoiDung,
            //   noiDung1: this.params.p_txtNoiDung,
            // });
            await this.CapNhatChuyenToThuHoiVT({
              donViId: this.params.p_DonVI,
              phieuId: this.vphieu_id,
              ghiChu: this.params.p_txtNoiDung,
              noiDung: this.params.p_txtNoiDung,
            });
            this.$toast.success("Chuyển tổ thành công");
            this.params.p_txtNoiDung = "";
            await this.HIENTHI_DANHSACH_THUEBAO();
          } else {
            this.$root.loading(false);
            return;
          }
        } else {
          this.$toast.error("Phiếu không thể chuyển tổ khi đã được giao về tổ");
          return;
        }
      } catch (error) {
        this.$toast.error(
          " Có lỗi trong quá trình chuyển tổ " + error.data.message
        );
      } finally {
        this.$root.loading(false);
      }
    },
    async LOC_DS_THUHOI() {
      if (this.params.p_TrangThaiTH == "0") {
        // gridViewThueBao.Columns["LYDO_TON"].ClearFilter();
      } else if (this.params.p_TrangThaiTH == "1") {
        // gridViewThueBao.Columns["LYDO_TON"].ClearFilter();
        // var viewFilterInfo_1 = new ViewColumnFilterInfo(gridViewThueBao.Columns["LYDO_TON"], new ColumnFilterInfo("[LYDO_TON] is  null ", ""));
        // gridViewThueBao.ActiveFilter.Add(viewFilterInfo_1);
      } else {
        // gridViewThueBao.Columns["LYDO_TON"].ClearFilter();
        // var viewFilterInfo_1 = new ViewColumnFilterInfo(gridViewThueBao.Columns["LYDO_TON"], new ColumnFilterInfo("[LYDO_TON] is  not null ", ""));
        // gridViewThueBao.ActiveFilter.Add(viewFilterInfo_1);
      }
    },

    async cboTrangThaiTH_EditValueChanged() {
      await this.LOC_DS_THUHOI();
    },
    async ckbTKTinhtrangVT_CheckedChanged() {
      this.Enabled.cboTKTinhTrangVT = this.params.p_ckbTKTinhtrangVT;
    },
    grvSuDungVT_FocusedRowChanged(args) {
      this.grvSuDungVT_FocusedRow = args;
    },
    async Func_TaoGiaoPhieuTon(dt) {
      dt.forEach(
        async (a) =>
          await this.TaoGiaoPhieuTon({
            phieuId: a.PHIEU_ID,
            lyDoTonTcId: a.LYDOTONTC_ID,
            ngayBt: a.NGAY_BT,
            nhanVienBtId: a.NHANVIEN_BT_ID,
            donViBtId: a.DONVI_BT_ID,
            ghiChu: a.GHICHU,
            ngayCn: a.NGAY_CN,
            nguoiCn: a.NGUOI_CN,
          })
      );
    },
    async getPdf(url, body) {
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/${url}`,
          method: "POST",
          responseType: "blob",
          data: body,
        }).then((response) => {
          this.previewPrint(response.data);
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.message);
      } finally {
        this.$root.loading(false);
      }
    },
    previewPrint(data) {
      var blobURL = window.URL.createObjectURL(
        new Blob([data], { type: "application/pdf" })
      );
      var iframe = document.createElement("iframe");
      document.body.appendChild(iframe);

      iframe.style.display = "none";
      iframe.src = blobURL;
      iframe.onload = function () {
        setTimeout(function () {
          iframe.focus();
          iframe.contentWindow.print();
        }, 1);
      };
    },
    downloadFile(data, fileName) {
      if (data) {
        var blob = new Blob([data], { type: "application/octet-stream" });
        var url = window.URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = fileName;
        a.click();
        document.body.removeChild(a);
      }
    },
    async DOWLOAD_OPEN(isShow) {
      let url = this.vurl_luoi;
      if (!url) return;
      let fileName = url.substring(url.lastIndexOf("/"));
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/minioStorage/downloadFile?fileSource=${url}`,
          method: "GET",
          responseType: "blob",
          data: {},
        }).then((response) => {
          if (fileName.includes(".pdf")) {
            this.previewPrint(response.data);
          } else {
            this.downloadFile(response.data, fileName);
          }
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async btnInBBBanGiao() {
      try {
        let url = "api/bao-cao/bao-cao-bien-ban-bg-th?extend=pdf";
        let body = {
          hdTbId: this.vhdtb_id,
          nhanVienId: this.$auth.getNhanVienID(),
        };
        this.getPdf(url, body);
      } catch (error) {
        this.$toast.warning("Báo cáo không tồn tại!");
      }
    },
    onClose: function (e) {
      this.tooltip.close();
    },

    onBeforeRender: function (args) {
      var result = this.$refs.cboLyDoHuy_ref.dataSource;
      var resultKHPH = this.$refs.cboKH_PhanHoi_ref.dataSource;
      var resultLyDoTon = this.$refs.cboLyDoTon.dataSource;
      var resultKetQua = this.$refs.cboKetQua_ref.dataSource;
      var resultLyDoThuHoi = this.$refs.cboLyDoThuHoi_ref.dataSource;
      var resultDonViQL = this.$refs.cboDonViQL_ref.dataSource;
      var resultDonVi = this.$refs.cboDonVi_ref.dataSource;

      this.tooltip.content = ""  

      for (var e of result) { 
        if (e.lyDoHuy === args.target.textContent) {
          this.tooltip.content = e.lyDoHuy;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultKHPH) { 
        if (e.TEN_KIEULD === args.target.textContent) {
          this.tooltip.content = e.TEN_KIEULD;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultLyDoTon) { 
        if (e.lyDoTonTc === args.target.textContent) {
          this.tooltip.content = e.lyDoTonTc;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultLyDoThuHoi) { 
        if (e.mucDich === args.target.textContent) {
          this.tooltip.content = e.mucDich;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultKetQua) { 
        if (e.KETQUA === args.target.textContent) {
          this.tooltip.content = e.KETQUA;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultDonViQL) { 
        if (e.tenHt === args.target.textContent) {
          this.tooltip.content = e.tenHt;
          this.tooltip.dataBind();
          break;
        }
      }

      for (var e of resultDonVi) { 
        if (e.TEN_DV === args.target.textContent) {
          this.tooltip.content = e.TEN_DV;
          this.tooltip.dataBind();
          break;
        }
      }

    },
    onDropdownCreate: function (args) {
      this.tooltip = new Tooltip({
        content: "",
        target: ".e-list-item",
        position: "top center",
        beforeRender: this.onBeforeRender,
      });
      this.tooltip.appendTo("body");
    },
  },
  created: async function () {
    await this.frmThuHoiVatTu_Load();
  },
  watch: {
    rowCheckedTB: function (value) {
      this.rowCheckedTB = value;
    },
    checkAllTB: function (value) {
      if (value) {
        for (const e of this.gridThueBao) {
          if (!this.rowCheckedTB.includes(e.HDTB_ID)) {
            this.rowCheckedTB.push(e.HDTB_ID);
          }
        }
      } else {
        this.rowCheckedTB = [];
      }
    },
  },
  destroyed() {},
};
</script>
<style src="./ThuHoiVatTuThueBao.scss">
</style>