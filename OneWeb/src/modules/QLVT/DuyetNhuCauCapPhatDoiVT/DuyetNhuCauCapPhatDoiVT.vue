<template src='./DuyetNhuCauCapPhatDoiVT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import TraCuuVatTuTrongKhoAPI from "../api/TraCuuVatTuTrongKhoAPI";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/duyetnhucaucapphatdoivt";
import select2 from "@/components/Select2.vue";
// import { Edit, Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query, Predicate } from "@syncfusion/ej2-data";
import frmNhapText from "../frmNhapText/frmNhapText.vue";
import DotTongHop from "../DotTongHop";
import {
  Page,
  Filter,
  ExcelExport,
  Sort,
  Edit,
  Resize,
} from "@syncfusion/ej2-vue-treegrid";
import XLSX from "xlsx";
import { previewPrint } from "@/utils/util";

import { L10n } from "@syncfusion/ej2-base";

import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from "@syncfusion/ej2-popups";
import frmGanSeri from "../frmGanSeri/frmGanSeri.vue";
import { currency } from "@/filters/currency";
Vue.use(TooltipPlugin);

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
    },
    pager: {
      currentPageInfo: "{0} trên {1} trang",
      totalItemsInfo: "({0} Bản ghi)",
      pagerDropDown: "Bản ghi trên trang",
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
    XLSX,
    DotTongHop,
    frmNhapText,
    breadcrumb,
    appSelect2: select2,
    frmGanSeri,
  },
  name: "DuyetNhuCauCapPhatDoiVT",
  mounted() {},
  provide: {
    // grid: [ Edit, Group, Page,Filter,Sort,Resize ],
    treegrid: [Edit, Page, Filter, ExcelExport, Sort, Resize],
  },
  computed: {
    ...mapState("duyetnhucaucapphatdoivt", statePropertyName),
    ...mapGetters("duyetnhucaucapphatdoivt", getterName),
    vdangky_luoi_id: {
      get() {
        return this.grvDSDangKy_FocusedRow
          ? this.grvDSDangKy_FocusedRow.DANGKY_ID
          : 0;
      },
    },
    vkho_luoi_id: {
      get() {
        return this.grvDSDangKy_FocusedRow
          ? this.grvDSDangKy_FocusedRow.KHO_ID
          : -1;
      },
    },
    vtg_dangky: {
      get() {
        return this.grvDSDangKy_FocusedRow
          ? this.grvDSDangKy_FocusedRow.TEN_TG
          : "";
      },
    },
    dv_dk: {
      get() {
        return this.grvDSDangKy_FocusedRow
          ? this.grvDSDangKy_FocusedRow.TEN_DV
          : "";
      },
    },
    dot_dk: {
      get() {
        return this.grvDSDangKy_FocusedRow
          ? this.grvDSDangKy_FocusedRow.DOT
          : "";
      },
    },
    PTAggregatesColumns() {
      return [
        {
          field: "SOLUONG",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalSL,
        },
        {
          field: "TIEN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTien,
        },       
      ];
    },
  },
  data() {
    return {
      header: {
        title: "DUYỆT NHU CẦU CẤP PHÁT CHO ĐỘI VT (TTVT)",
        list: [
          { name: "Quản lý vật tư", link: { name: "Ui.cards" } },
          {
            name: "Đăng ký nhu cầu vật tư",
            link: { name: "Ui.buttons" },
          },
          {
            name: "Duyệt nhu cầu cấp phát cho Đội VT (TTVT)",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fieldsCheckBox: {
        vatTu: { text: "TEN_VT", value: "VATTU_ID" },
      },

      params: {
        p_DVGIAO: "",
        p_ckb_vattu: false,
        p_dsvattu: [],

        p_ckb_thangdk: false,
        p_thangdk: new Date(),
        p_denthang: new Date(),

        p_ckb_tungay: true,
        p_tungay: new Date(),
        p_denngay: new Date(),

        p_ckb_ngayduyettu: false,
        p_ngayduyettu: new Date(),
        p_ngayduyetden: new Date(),

        p_trangthai: "1",
        p_xuly: "0",

        p_txtngayyc: "",
        p_txtthoigiandk: "",
        p_txtsocv: "",
        p_txtduan: "",
        p_txtdonvidk: "",
        p_txtkho: "",
        p_txtghichu: "",
        p_filecv: "",

        p_KieuTH: "1",

        p_ckbFindAll: true,
        p_khogiao_CTDK: "",
        p_ckbGiuCho: false,
        p_spNgayGiu: 30,
        p_KhoDatDen: "",

        p_dtpNgayDK: new Date(),
        p_ckbDatCho: false,
        p_spinSoNgay: 30,
        p_ckbKhoGiao: true,
        p_khogiao_TK: "", //cboKho
        p_KhoDen: "",

        p_MucDich: "",
        p_TimKiem: "",
        tinhDangKy : null,
        tinhTonKho: null,
       
      },
      pageSettings: {
        pageSizes: ["10", "20", "50", "100", "Tất cả"],
        pageSize: 10,
      },
      active: {
        tab3: true,
        tab4: false,
        tab3: false,
      },
      // isFirstLoadKho :true,
      isFirstLoadKhoGiao: true,
      readonly: {
        cboKhoDen: false,
        ckbGiuCho: false,
      },
      visible: {
        tsbtnNhapKho: true,
        tsbtnGiaoPhieu: true,
        tsbtnTraLai: true,
        btnHuyBo: true,
        tsbtnBoSungVT: true,
        tsbtnHoanThanh: true,
        tsbtnHuyTT: false,
        tsbtnTongHop: true,
        slCL: true,
        slChuyen: true,
        tsbtnThemVT: true,
        btnHuyXN: true,
        tsbtnXacNhan: true,
        xtraTabPage1: true,
        xtraTabPage2: false,
        grvDSDangKy: {
          TRANGTHAI_VT: true,
        },
        splDSVT: "Panel1",
      },
      isShowDatCho: false,
      DSVatTuTonKho: [],
      enable: {
        spinSoNgay: true,
        tsbtnThemVT: true,
        cboKhoGiao: true,        
        cboKhoDatDen: true,
        cboKho: true,
        gridVatTu: {
          colCuon: false, // DS cuộn
          SL_XUAT: false,
          CHON: true,
        },
        tvVatTu: false,
        grvDSVatTu_DaDK: {
          LYDO_DUYET: false,
        },
        grvDSDangKy: {
          TRANGTHAI_VT: false,
        },
        slDuyet: false,
        cboKho: true,
      },
      DSTinh: [],
      DSDatCho_Excel: [],
      cboTrangThai_firstLoad: true,
      cboTimKiem: [], //
      cbo_MucDich: [], //
      tvVatTu: [],
      tvVatTu_Goc: [],
      cboLoaiKHOTD: [],
      cboMaKHOTD: [],
      cboDVGIAO: [],
      cboDSVT: [],
      ds_cat_temp: [],

      cboTrangThai: [
        { TRANGTHAI_ID: "1", TRANGTHAI: "Mới đăng ký" },
        { TRANGTHAI_ID: "2", TRANGTHAI: "Đã duyệt" },
      ],
      cboXuLy: [
        { TRANGTHAI_ID: "0", TRANGTHAI_XL: "Tất cả" },
        { TRANGTHAI_ID: "1", TRANGTHAI_XL: "Chưa xử lý" },
        { TRANGTHAI_ID: "2", TRANGTHAI_XL: "Đang xử lý" },
        { TRANGTHAI_ID: "4", TRANGTHAI_XL: "Đã xử lý xong" },
      ],
      cboKieuTH: [
        { ID: "1", NOIDUNG: "Xuất đăng ký và đặt chỗ" },
        { ID: "2", NOIDUNG: "Đặt chỗ theo mục đích (Xuất kho)" },
        { ID: "3", NOIDUNG: "Đặt chỗ mục đích (Chuyển kho)" },
      ],
      cboKhoGiao: [],

      cboKhoDatDen: [],
      cboKho: [],
      cboKhoDen: [],

      dsQuyen: [],
      grcDSDangKy: [], // tab DS đăng ký
      grcVattu: [], // tab DS đăng ký
      ds_vattu: [], //tab DS đăng ký
      grcDSChungTu: [], // tab DS chứng từ
      grcDSVatTu_DaDK: [], // tab DS chi tiết đăng ký
      grcTonKho: [], // tab DS chi tiết đăng ký
      grcVT_NKHO: [], // tab DS tồn kho
      gridVatTu: [], // tab DS tồn kho
      gridChitietCT: [], // tab chi tiết vật tư
      grcFileCV: [],

      tagFrm: "",
      Tag: "1",
      ds_dk: "-1",
      json_list: [{}],
      allowEditGhiChu : true,
      grvDSDangKy_FocusedRow: {},
      grvDSChungTu_FocusedRow: {},
      tvVatTu_FocusedNode: {},
      grcDSVatTu_DaDK_FocusedRow: {},

      DS_QUYEN_VT: {
        HOANTHIEN_CHUNGTU: 1794,
        CAPNHAT_CHUNGTU: 1795,
        QUYENDUYET_VT: 2080,
        QUYENDUYET_TTVT: 2700,
        QUYENDUYET_TTCU: 2701,
        QUYENXUAT_KHO: 2081,
        QUYENLAYVT_DH: 2505,
        QUYET_DOIMACT: 3501,
        QUYET_XETDUYET: 3505,
      },

      json_list: "",

      chonvattu_lh: false, // global variabl
      tonkho_theouser: false, // global variabl
      ds_cat_temp: [], // global variable
      xac_nhan: [], // global variable
      kiemtra_quyen: false,
      dt_temp: [],
      ttph_id: "",
      vchungtu_id: "",

      kho_giao_id: 0,

      Text: {
        tsbtnGiaoPhieu: "Nhận đăng ký",
        tsbtnBoSungVT: "Nhận đăng ký",
        tsbtnBoSungVT: "Bổ sung VT",
        tsbtnNhapKho: "Chuyển kho",
        cboDSVT: "",
      },

      selectionSettings: { type: "Single", checkboxOnly: true },
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },

      // grcDSDangKyPage : {
      //     page: 0,
      //     maxSize: 10,
      //     totalElement: 0,
      //     totalPages: 0,
      //     sort: null,
      //     propertiesSort: null,
      //     currentRow: 0
      // },
      grcDSVatTu_DaDKPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      // grcTonKhoPage : {
      //     page: 0,
      //     maxSize: 10,
      //     totalElement: 0,
      //     totalPages: 0,
      //     sort: null,
      //     propertiesSort: null,
      //     currentRow: 0
      // },
      gridVatTuPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      isSpliter: true,
      columnsCheckboxAllDH: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input type="checkbox"  class="uncheck"/>
                                <span class="name"></span>
                            </div>
                        `,
            data: function () {
              return {};
            },
            computed: {
              parent() {
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      checkAllCT: false,
      rowSelectedCT: [],
      CheckboxAllCT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"  
                                    v-model="parent.checkAllCT"                                  
                                   />
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
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      CheckboxCT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"   
                                    v-model="parent.rowSelectedCT"
                                    :value="data.VTDK_ID"                                 
                                   />
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
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      checkAllVT: false,
      rowSelectedVT: [],
      CheckboxAllVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"  
                                    v-model="parent.checkAllVT"                                  
                                   />
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
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      CheckboxVT: function () {
        return {
          template: {
            template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="uncheck"   
                                    v-model="parent.rowSelectedVT"
                                    :value="data.STT"                                 
                                   />
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
                return this.$parent.$parent.$parent.$parent.$parent;
              },
            },
          },
        };
      },
      btnDelTemplate: function () {
        return {
          template: Vue.component("btnDelTemplate", {
            template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnDel" @click="btnDel_ButtonClick">
                          <span class="fa fa-close"></span>
                      </button>`,
            computed: {
              btnDel: {
                get() {
                  if (this.data["KIEUDK_ID"].toString() != "0") {
                    return true;
                  }
                  return false; // true =>test
                },
              },
            },
            data: function () {
              return { data: {} };
            },
            methods: {
              async btnDel_ButtonClick() {
                await this.$parent.$parent.$parent.$parent.$parent.btnDel_ButtonClick(
                  this.data
                );
              },
            },
          }),
        };
      },

      btnEdit_Template: function () {
        return {
          template: Vue.component("btnEdit_Template", {
            template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" v-if="btnEdit" @click="btnEdit_ButtonClick">
                          <span class="act -ap icon-chevron-thin-right"></span>
                      </button>`,
            data: function () {
              return { data: {} };
            },
            computed: {
              btnEdit: {
                get() {
                  if (
                    this.data["LOAITBI_ID"] &&
                    this.data["LOAITBI_ID"].toString() == "3"
                  ) {
                    return true;
                  }
                  return false; // true =>test
                },
              },
            },
            methods: {
              async btnEdit_ButtonClick() {
                await this.$parent.$parent.$parent.$parent.$parent.btnCuonCap_ButtonClick(
                  this.data
                );
              },
            },
          }),
        };
      },

      grcDSChungTu_Columns: [
        {
          fieldName: "btnXoa",
          headerText: "Xóa",
          textAlign: "Left",
          allowFiltering: true,
          freeze: "Left",
          width: "80",
          template: function () {
            return {
              template: Vue.component("btnXoaTemplate", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoa_ButtonClick">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                methods: {
                  async btnXoa_ButtonClick() {
                    await this.$parent.$parent.$parent.$parent.$parent.btnXoa_ButtonClick(
                      this.data
                    );
                  },
                },
              }),
            };
          },
        },
        {
          fieldName: "SOPHIEU",
          headerText: "Số phiếu",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_CT",
          headerText: "Ngày chứng từ",
          textAlign: "Right",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_CT",
          headerText: "Loại chứng từ",
          textAlign: "Left",
          allowFiltering: true,
        },

        {
          fieldName: "TRANGTHAI",
          headerText: "Trạng thái",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "TIEN",
          headerText: "Tiền",
          textAlign: "Right",
          allowFiltering: true,
        },
        {
          fieldName: "KHO_GIAO",
          headerText: "Từ kho",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "KHO_NHAN",
          headerText: "Đến kho",
          textAlign: "Left",
          allowFiltering: true,
        },

        {
          fieldName: "ND_GIAO",
          headerText: "Nội dung",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI",
          headerText: "Loại",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "NGAY_HH",
          headerText: "Ngày hết hạn",
          textAlign: "Right",
          allowFiltering: true,
        },
        {
          fieldName: "MA_DATCHO",
          headerText: "Mã đặt chỗ",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "SO_VB",
          headerText: "Số VB",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "NGUOI_CN",
          headerText: "Người Lập",
          textAlign: "Left",
          allowFiltering: true,
        },
      ],

      gridChitietCT_Columns: [
        {
          fieldName: "btnXoa_VT",
          headerText: "Xóa",
          allowFiltering: true,
          template: function () {
            return {
              template: Vue.component("btnXoa_VTTemplate", {
                template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoa_VT_ButtonClick">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                data: function () {
                  return { data: {} };
                },
                methods: {
                  async btnXoa_VT_ButtonClick() {
                    await this.$parent.$parent.$parent.btnXoa_VT_ButtonClick(
                      this.data
                    );
                  },
                },
              }),
            };
          },
        },
        {
          fieldName: "LOHANG",
          headerText: "Lô hàng",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "MA_VT",
          headerText: "Mã vật tư",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_VT",
          headerText: "Tên vật tư",
          textAlign: "Left",
          allowFiltering: true,
        },

        {
          fieldName: "DVI_TINH",
          headerText: "ĐVT",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "SOLUONG",
          headerText: "Số lượng",
          textAlign: "Right",
          allowFiltering: true,
          type:'number',
          format:'N1'
        },
        {
          fieldName: "DONGIA",
          headerText: "Đơn giá",
          textAlign: "Right",
          allowFiltering: true,
          type:'number',
          format:'N3'
        },
        {
          fieldName: "TIEN",
          headerText: "Thành tiền",
          textAlign: "Right",
          allowFiltering: true,
          type:'number',
          format:'N3'
        },

        {
          fieldName: "SOTHANG_PB",
          headerText: "Số tháng PB",
          textAlign: "Right",
          allowFiltering: true,
        },
        {
          fieldName: "LOAI_TBI",
          headerText: "Loại thiết bị",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "HAN_BH",
          headerText: "Hạn BH",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "MA_HD",
          headerText: "Mã HĐ",
          textAlign: "Left",
          allowFiltering: true,
        },
        {
          fieldName: "MA_TH",
          headerText: "Đợt TH",
          textAlign: "Left",
          allowFiltering: true,
        },
      ],
      showLocVT: false,
      selectedLocVT: "",

      ThaoTacTemplate: function () {
        return {
          template: Vue.component("ThaoTacTemplate", {
            template: `
                    <div>
                      <button class="btn btn-main pad4 lh14 w60 btn-primary" @click="tsbtnDatCho_Click" v-b-tooltip.hover title="Đặt chỗ">
                          <span class="icon one-file-plus"></span>
                      </button>
                      <button class="btn btn-main pad4 lh14 w60 btn-primary" @click="importFileToolStripMenuItem_Click" v-b-tooltip.hover title="Đặt chỗ từ file">
                          <span class="icon one-excel"></span>
                      </button>
                    </div>`,
            data: function () {
              return { data: {} };
            },
            methods: {
              async tsbtnDatCho_Click() {
                await this.$parent.$parent.$parent.tsbtnDatCho_Click(this.data);
              },
              async importFileToolStripMenuItem_Click() {
                if (!this.$parent.$parent.$parent.params.p_ckbGiuCho) {
                  this.$toast.error(
                    "Bạn không thể đặt chỗ nếu không chọn ngày "
                  );
                  return;
                }               
                document.getElementById("fileUpload").click();                
              },
            },
          }),
        };
      },
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: [],
      },
      dat_cho_trung_row: false,
      tooltip: Tooltip,
    };
  },
  methods: {
    ...mapActions("duyetnhucaucapphatdoivt", actionName),
    ...mapMutations("duyetnhucaucapphatdoivt", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    onClose: function (e) {
      this.tooltip.close();
    },
   
    getTotalSL() {
      var Tong = 0;
      this.gridChitietCT.forEach((e) => {
        e.SOLUONG = e.SOLUONG == "" || e.SOLUONG == null ? 0 : parseFloat(e.SOLUONG);
        Tong += e.SOLUONG;
      });
      return currency(Tong);
    },
    getTotalTien() {
      var Tong = 0;
      this.gridChitietCT.forEach((e) => {
        e.TIEN = e.TIEN == "" || e.TIEN == null ? 0 : parseFloat(e.TIEN);
        Tong += e.TIEN;
      });
      return currency(Tong);
    },
    dataBoundDSVatTu: function () {
      console.log("dataBoundDSVatTu");
      this.$refs.grvDSVatTu_DaDK.grid.autoFitColumns([
        "MA_VT",
        "TEN_VT",
        "DVI_TINH",
        "SL_TON_DK",
        "SOLUONG",
        "SL_DUYET",
        "SL_CAP",
        "SL_CHUYEN",
        "SL_CL",
        "GHICHU",
        "LYDO_DUYET",
      ]);
    },

    onFilteringVatTu(e) {
      var query = new Query();
      query =
        e.text !== "" ? query.where("TEN_VT", "contains", e.text, true) : query;
      e.updateData(this.cboDSVT, query);
    },
    renderDSChungTu(){      
      this.$refs.grvDSChungTu.grid.refreshColumns()     // load lại lưới tránh mất scroll ngang
    },
    clickTab(kieu) {
      if(kieu == 3){
        this.$refs.grvDSVatTu_DaDK.grid.refreshColumns() // load lại lưới tránh mất scroll ngang
      }
      this.isSpliter = kieu == 3;
      this.active.tab3 = kieu == 3;
      this.active.tab4 = kieu == 4;
      this.active.tab5 = kieu == 5;
    },
    onBeforeRender: function (args) {
      // var result = this.$refs.cboKhoGiao.dataSource;
      // var resultVT = this.$refs.cboVatTu.dataSource;
      // var resultDVGiao = this.$refs.cboDVGiao.dataSource;
      // var resultKhoDen = this.$refs.cboKhoDen.dataSource;
      this.tooltip.content = "";
      this.tooltip.content = args.target.textContent;
      this.tooltip.dataBind();
      // for (var e of result) {
      //   if (e.TEN_KHO === args.target.textContent) {
      //     this.tooltip.content = e.TEN_KHO;
      //     this.tooltip.dataBind();
      //     break;
      //   }
      // }
      // console.log(args.target.textContent  , " = textContent")
      // for (var e of resultVT) {
      //   if (e.TEN_VT === args.target.textContent) {
      //     this.tooltip.content = e.TEN_VT;
      //     this.tooltip.dataBind();
      //     break;
      //   }
      // }

      // for (var e of resultDVGiao) {
      //   if (e.tenDonVi === args.target.textContent) {
      //     this.tooltip.content = e.tenDonVi;
      //     this.tooltip.dataBind();
      //     break;
      //   }
      // }

      // for (var e of resultKhoDen) {
      //   if (e.tenKho === args.target.textContent) {
      //     this.tooltip.content = e.tenKho;
      //     this.tooltip.dataBind();
      //     break;
      //   }
      // }
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

    list_to_tree(list) {
      var map = {},
        node,
        roots = [],
        i;

      for (i = 0; i < list.length; i += 1) {
        map[list[i].VATTU_ID] = i; // initialize the map
        list[i].children = []; // initialize the children
      }

      for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.VATTU_CHA_ID !== "") {
          // if you have dangling branches check that map[node.parentId] exists
          list[map[node.VATTU_CHA_ID]].children.push(node);
        } else {
          roots.push(node);
        }
      }
      return roots;
    },

    async Load_quyen() {
      this.dsQuyen = await this.getDSQuyenND({
        nguoiDungId: this.$auth.getNguoiDungID(),
      });
    },
    onlyUnique(value, index, self) {
      return self.indexOf(value) === index;
    },
    createTreeGrid(list) {
      let arr = [];
      let donviCharArr = list.map((item) => item.VATTU_CHA_ID);
      let uniqueDonviChaArr = donviCharArr.filter(this.onlyUnique);
      uniqueDonviChaArr.forEach((donviChaId) => {
        let children = list.filter((item) => item.VATTU_CHA_ID === donviChaId);
        let tenTrungtam = this.tvVatTu.filter(
          (item) => item.VATTU_ID === donviChaId
        )[0].TEN_VT;
        if (children.length > 0) {
          arr.push({ TEN_VT: tenTrungtam, children: children });
        } else arr.push({ TEN_VT: tenTrungtam });
      });
      return arr;
    },
    async NAP_DS_COMBO() {
      // try {

      this.cboLoaiKHOTD = await this.getDSTenLoaiKhoTD();
      this.cboMaKHOTD = await this.getDSMaKhoTD();

      this.cboDVGIAO = await this.getDSKhoDonViDK();
      this.cboDVGIAO.length
        ? (this.params.p_DVGIAO = this.cboDVGIAO[0].donViId)
        : "";

      // REM đoạn này load sau, giảm thời gian load form
      // this.ds_vattu = await this.getDSVatTuDaDK({});
      // this.grcVattu = this.ds_vattu;
      // this.cboDSVT = this.ds_vattu; // test

      this.params.p_dtpNgayDK = new Date();
      this.params.p_tungay = new Date(
        new Date().getTime() - 10 * 24 * 60 * 60 * 1000
      );
      this.params.p_denngay = new Date();

      this.params.p_ngayduyettu = new Date(
        new Date().getTime() - 7 * 24 * 60 * 60 * 1000
      );
      this.params.p_ngayduyetden = new Date();

      await this.Load_quyen();

      this.cboTimKiem = await this.getDSVatTuMDDK({ nhomMucDichId: -1 });

      let dtmd = [];
      if (this.$auth.getPhanVungID().toString() == "28") {
        dtmd = await this.getDSMucDich({ kieu: 12 });
      } else {
        dtmd = await this.getDSMucDich({ kieu: 0 });
      }
      this.cbo_MucDich = dtmd;
      this.cbo_MucDich.length
        ? (this.params.p_MucDich = this.cbo_MucDich[0].mucDichId)
        : "";

      this.tvVatTu_Goc = await this.getDSVatTuDKTheoMD({
        donViId: this.$auth.getDonViID(),
        nhomMucDichId: 0,
      });
      this.tvVatTu = this.tvVatTu_Goc;

      await this.cboTrangThai_EditValueChanged();

      // bổ sung cho KHDN
      console.log(this.tagFrm , " = this.tagFrm")
      if(this.tagFrm != "4"){
        let dt = await this.getDSKho({
          tagFrm: this.tagFrm,
          nhanVienId: this.$auth.getNhanVienID(),
        });

        this.cboKho = dt;
        dt.length ? (this.params.p_khogiao_CTDK = dt[0].KHO_ID) : "";
        this.cboKhoGiao = dt;
        dt.length ? (this.params.p_khogiao_TK = dt[0].KHO_ID) : "";

        
        this.cboKhoDen = await this.getDSKho({
          tagFrm: 1,
          nhanVienId: this.$auth.getNhanVienID(),
        });
        this.cboKhoDen.length
          ? (this.params.p_KhoDen = this.cboKhoDen[0].KHO_ID)
          : "";
      

        this.cboKhoDatDen = await this.getDSKhoDatDen({
          nhanVienId: this.$auth.getNhanVienID(),
        });
        this.cboKhoDatDen.length
          ? (this.params.p_KhoDatDen = this.cboKhoDatDen[0].khoId)
          : "";
      }else{
        this.DSTinh = await this.getDSTinh()
        if(this.DSTinh.length > 0){
          this.params.tinhDangKy = this.DSTinh[0].tinh_id
          this.params.tinhTonKho = this.DSTinh[0].tinh_id
        }        
      }
      this.readonly.cboKhoDen = true;
      this.enable.spinSoNgay = false;

      // Thiết lập giao diện
      this.visible.tsbtnNhapKho = false;
      this.visible.tsbtnGiaoPhieu = false;
      this.visible.tsbtnTraLai = false;
      this.visible.btnHuyBo = false;
      this.visible.tsbtnBoSungVT = false;
      this.visible.tsbtnHoanThanh = false;
      this.visible.tsbtnTongHop = false;

      this.readonly.ckbGiuCho = true;

      if (
        this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT) &&
        !this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO)
      ) {
        this.visible.tsbtnTongHop = true;
        this.enable.tvVatTu = true;

        this.visible.tsbtnGiaoPhieu = true;
        this.visible.tsbtnTraLai = true;
        this.visible.tsbtnBoSungVT = true;
      }

      if (
        this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO) &&
        !this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)
      ) {
        this.params.p_trangthai = "2";
        this.visible.tsbtnHoanThanh = true;
        this.visible.tsbtnNhapKho = true;
        this.readonly.ckbGiuCho = false;
      } else if (
        this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO) &&
        this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)
      ) {
        this.readonly.ckbGiuCho = false;
        this.enable.tvVatTu = true;
        this.visible.tsbtnTongHop = true;
        this.visible.tsbtnBoSungVT = false;
        this.visible.tsbtnGiaoPhieu = false;
        this.visible.tsbtnTraLai = false;
        this.visible.btnHuyBo = false;
        this.visible.tsbtnNhapKho = false;
        this.visible.tsbtnHoanThanh = false;
        this.visible.slCL = false;
        this.visible.slChuyen = false;
        this.visible.tsbtnThemVT = false;
        this.visible.btnHuyXN = false;
        this.visible.tsbtnXacNhan = false;

        if (this.params.p_trangthai.toString() == "1") {
          this.enable.grvDSVatTu_DaDK.LYDO_DUYET = true;
          this.enable.slDuyet = true;
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
            this.visible.tsbtnGiaoPhieu = true;
            this.visible.tsbtnBoSungVT = true;
            this.visible.tsbtnTraLai = true;
          }
          this.visible.xtraTabPage1 = true;
          this.visible.xtraTabPage2 = false;
        } else if (this.params.p_trangthai == "2") {
          this.visible.xtraTabPage2 = true;
          this.visible.xtraTabPage1 = true;
          this.visible.slCL = true;
          this.enable.grvDSVatTu_DaDK.LYDO_DUYET = false;
          this.enable.slDuyet = false;

          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO)) {
            this.visible.tsbtnXacNhan = true;
            this.visible.tsbtnThemVT = true;
            this.visible.tsbtnHoanThanh = true;
            this.visible.tsbtnNhapKho = true;
            this.visible.btnHuyXN = true;
            this.enable.btnHuyXN = false;
          }
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
            this.visible.btnHuyBo = true;
          }
        }
      }

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi nạp danh sách combo")
      // }
    },
    async NAP_DS_DK_frmDangTai() {
      let tungay = "0";
      let denngay = "0";
      let duyet_tu = "0";
      let duyet_den = "0";

      if (this.params.p_ckb_tungay) {
        tungay = this.f_DateToString(this.params.p_tungay, "DD/MM/yyyy");
        denngay = this.f_DateToString(this.params.p_denngay, "DD/MM/yyyy");
      }
      if (this.params.p_ckb_ngayduyettu) {
        duyet_tu = this.f_DateToString(this.params.p_ngayduyettu, "DD/MM/yyyy");
        duyet_den = this.f_DateToString(
          this.params.p_ngayduyetden,
          "DD/MM/yyyy"
        );
      }
      let json_list = [];
      if (this.params.p_ckb_vattu && this.params.p_dsvattu.length > 0) {
        var dsvattu = [];
        for (let e of this.params.p_dsvattu) {
          dsvattu.push({ VATTU_ID: e });
        }
        json_list = dsvattu;
      }
      var ds = await this.getDSDangKyCapPhatV3({
        body: {
          tuThangDk: "0",
          denThangDk: "0",
          ttDkId: this.params.p_trangthai,
          donViId: this.$auth.getDonViID(),
          nhanVienId: this.$auth.getNhanVienID(),
          tuNgay: tungay,
          denNgay: denngay,
          tagForm: this.tagFrm,
          ttXlId: this.params.p_xuly,
          dsVatTu: json_list,
          duyetTu: duyet_tu,
          duyetDen: duyet_den,
        },
        // page: this.grcDSDangKyPage.page,
        // pageSize: this.grcDSDangKyPage.maxSize
      });
      return ds;

      // if (!ds.length){
      //   return []
      // }
      // else{
      //   return ds
      // }
    },
    async NAP_DS_VATTU_DK() {
      let DSDaDK = await this.getDanhSachDaDK({
        dangKyId: this.vdangky_luoi_id,
      });
      DSDaDK.forEach((e) => {
        e.SL_DUYET =
          e.SL_DUYET == "" || e.SL_DUYET == null ? 0 : parseFloat(e.SL_DUYET);
      });
      this.grcDSVatTu_DaDK = DSDaDK;
      this.rowSelectedCT = [];
      this.checkAllCT = false;
    },
    async resetThongTinDangKy() {
      this.params.p_txtngayyc = "";
      this.params.p_txtthoigiandk = "";
      this.params.p_txtduan = "";
      this.params.p_txtkho = "";
      this.params.p_txtsocv = "";
      this.params.p_txtdonvidk = "";
    },
    async NAP_DS_THONGTIN() {
      // try {
      await this.NAP_DS_VATTU_DK();

      await this.resetThongTinDangKy();

      // try {
      this.params.p_txtngayyc = this.grvDSDangKy_FocusedRow.NGAY_DK;
      this.params.p_txtthoigiandk = this.grvDSDangKy_FocusedRow.TEN_TG;
      this.params.p_txtduan = this.grvDSDangKy_FocusedRow.MA_CT;
      this.params.p_txtkho = this.grvDSDangKy_FocusedRow.TEN_KHO;
      this.params.p_txtsocv = this.grvDSDangKy_FocusedRow.SO_CV;
      this.params.p_txtdonvidk = this.grvDSDangKy_FocusedRow.TEN_DV;
      // } catch (error) {
      //   await this.resetThongTinDangKy()
      // }

      await this.NAP_DS_CHUNGTU();

      this.params.p_KhoDen = this.vkho_luoi_id;
      this.enable.tsbtnXacNhan = true;
      this.enable.btnHuyXN = false;
      this.kho_giao_id = 0;
      this.gridVatTu = [];
      this.DSVatTuTonKho = []
      this.checkAllVT = false;
      this.rowSelectedVT = [];
      this.grcVT_NKHO = [];
      this.enable.tsbtnThemVT = true;

      this.enable.gridVatTu.SL_XUAT = true;
      this.enable.gridVatTu.CHON = true;
      this.enable.gridVatTu.colCuon = true;

      await this.LAY_TON_VT(2);

      let dangky_id = parseInt(this.grvDSDangKy_FocusedRow.DANGKY_ID);

      let file_cv = await this.getFileCVDK({ dangKyId: dangky_id });
      if (file_cv.length && file_cv[0] != null && file_cv[0] != "") {
        let words = file_cv[0].split(",");
        var dt_file = [];
        for (var i of words) {
          if (i != null && i != "") {          
            var t = i.split("/");
            var t_t = i;            
            dt_file.push({ colFile: t_t, colFileName: t[t.length - 1]});
          }
        }

        this.grcFileCV = dt_file;
        console.log(this.grcFileCV , " = this.grcFileCV")
      }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin chi tiết đăng ký")
      // }
    },
    async CLEAR() {
      this.grcDSVatTu_DaDK = [];
      this.gridChitietCT = [];
      this.grcFileCV = [];
      this.rowSelectedCT = [];
      this.checkAllCT = false;
      this.resetThongTinDangKy()

    },
    async NAP_DS_DK(tb) {
      this.grcDSChungTu = [];
      this.grcDSDangKy = [];

      let res = await this.NAP_DS_DK_frmDangTai();

      if (!res.length) {
        this.CLEAR()
        if (tb == 1) {
          this.$toast.error("Không tìm thấy danh sách đăng ký");
        }
      } else {
        let DSDangKy = res;
        if (this.params.p_DVGIAO.toString() != "-1") {
          DSDangKy = DSDangKy.filter(
            (a) => a.DONVI_QL_ID.toString() == this.params.p_DVGIAO.toString()
          );
        }
        this.grcDSDangKy = DSDangKy;
      }
      this.rowSelectedCT = [];
      this.checkAllCT = false;
    },
    async frmDuyetVT_DK_Load() {
      this.tagFrm = this.Tag.toString();

      // Lấy TS mặc định
      var ds = await this.getThamSoMacDinh(0);
      if (ds.length) {
        let str_vtemp = "KIEMTRA_QUYENXN";
        let det = ds.filter((a) => a.maTS.toString() == str_vtemp);
        if (det.length) {
          if (det[0].tenTS.toString().trim() == "1") {
            this.kiemtra_quyen = true;
          }
        }

        str_vtemp = "CHONLOHANG_XDK";
        det = ds.filter((a) => a.maTS.toString() == str_vtemp);
        if (det.length) {
          if (det[0].tenTS.toString().trim() == "1") {
            this.chonvattu_lh = true;
          }
        }

        str_vtemp = "DAT_CHO_TRUNG_ROW";
        det = ds.filter((a) => a.maTS.toString() == str_vtemp);
        if (det.length) {
          if (det[0].tenTS.toString().trim() == "1") {
            this.dat_cho_trung_row = true;
          }
        }

        str_vtemp = "LAY_TONKHO_THEO_USER";
        det = ds.filter((a) => a.maTS.toString() == str_vtemp);
        if (det.length) {
          if (det[0].tenTS.toString().trim() == "1") {
            this.tonkho_theouser = true;
          }
        }
      }
     
      if (
        this.$auth.getMaTinh() == "HCM" ||
        this.$auth.getMaTinh() == "NET" ||
        this.$auth.getPhanVungID() == 97
      ) {
        // tinh_hientai == "HCM" || tinh_hientai == "NET"
        this.visible.colNguoiDuyet = true;
        this.visible.colNgayDuyet = true;
        this.enable.colGHICHU = true;
        this.visible.colChatLuongID = true;
        this.visible.colDongTbi = true;
        this.visible.colHeThong = true;
        this.visible.colLinhVucID = true;
        this.visible.colPartN = true;
        this.visible.colNgayNhap = true;
        this.visible.colMotaCT = true;
      }
      this.clickTab(3);      
    },
    async btnLayTT_Click() {
      try {
        this.loading(true);
        await this.NAP_DS_DK(1);
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KiemTra_NguoiDuyet() {
      var kq = await this.KiemTraNguoiDuyet({
        maNd: this.$auth.getUserName(), // ttnd.ma_nd
        dangKyId: this.vdangky_luoi_id,
        trangThai: this.params.p_trangthai,
      });

      if (kq.length && kq[0].toString() != "0" && kq[0].toString() != "1") {
        this.$toast.error(kq[0]);
        return false;
      }
      return true;
    },
    async KIEMTRA_PHIEU() {
      // try {
      var dt = this.grcDSDangKy;

      if (dt.length == 0) {
        this.$toast.error("Không có danh sách phiếu để thực hiện");
        return false;
      }
      if (this.$refs.grvDSDangKy.getSelectedRecords().length == 0) {
        this.$toast.error("Bạn chưa chọn phiếu để thực hiện");
        return false;
      }
      if (!(await this.KiemTra_NguoiDuyet())) {
        return false;
      }
      return true;
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra danh sách phiếu")
      //   return false
      // }
    },
    async KIEMTRA_NHAN_PHIEU() {
      // try {

      if (this.vdangky_luoi_id == 0 || this.vdangky_luoi_id == null) {
        this.$toast.error("Bạn chưa chọn phiếu đăng ký để duyệt");
        return false;
      }
      // rename variable
      var ds_all_dk = this.grcDSVatTu_DaDK;
      if (!ds_all_dk.length) {
        this.$toast.error("Không có danh sách chi tiết vật tư để nhận");
        return false;
      }
      // var ds = this.$refs.grvDSVatTu_DaDK.getSelectedRecords() // ds.Select("SELECTION='1'")

      var ds = this.grcDSVatTu_DaDK.filter((a) =>
        this.rowSelectedCT.includes(a.VTDK_ID)
      );
      if (!ds.length) {
        this.$toast.error("Bạn chưa lựa chọn duyệt số lượng đăng ký");
        return false;
      }
      // REM để chuyển lên DB
      // if (ds.length != ds_all_dk.length) {
      //   this.$toast.error("Bạn chưa lựa chọn duyệt hết số lượng đăng ký");
      //   return false;
      // }
      // if (ds.filter(a=>a.SL_DUYET.toString() == "0" && a.LYDO_DUYET == '').length > 0){
      //   this.$toast.error("Với vật tư duyệt số lượng 0 phải có lý do")
      //   return false
      // }
      return true;

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra danh sách phiếu")
      //   return false
      // }
    },
    async NHAN_DK_frmDangTai() {
      // TẠo dữ liệu phiếu
      var dt = this.grcDSVatTu_DaDK;
      if (!dt.length) {
        return;
      }
      let ds = [];
      var ds_check = this.grcDSVatTu_DaDK.filter((a) =>
        this.rowSelectedCT.includes(a.VTDK_ID)
      );
      for (let item of ds_check) {
        let row = {};
        row.vtdk_id = item.VTDK_ID.toString();
        row.slduyet = item.SL_DUYET.toString();
        row.lydo_duyet = item.LYDO_DUYET.toString();

        ds.push(row);
      }

      var ketqua = await this.NhanPhieuDuyetDK({
        dangKyId: this.vdangky_luoi_id,
        dsDuyet: ds,
        nguoiCn: this.$auth.getUserName(), // ttnd.ma_nd
        mayCn: this.$auth.getUserName(), // ttnd.may_cn
        ipCn: "10.59.90.123", // ttnd.ip
      });

      if (ketqua == null || ketqua == "") {
        return "Không có thông tin trả về khi thực hiện trả phiếu";
      } else {
        if (Array.isArray(ketqua) && ketqua.length && "message" in ketqua[0]) {
          return ketqua[0].message;
        } else {
          return "Không có thông tin trả về khi thực hiện trả phiếu";
        }
      }
    },
    async NHAN_DK() {
      if (!(await this.KIEMTRA_NHAN_PHIEU())) {
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn chắc chắn muốn nhận phiếu đăng ký" +
            this.vtg_dangky +
            " - Đơn vị " +
            this.dv_dk +
            " đợt " +
            this.dot_dk +
            " không ?",
          {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      try {
        this.loading(true);
        let ouput = await this.NHAN_DK_frmDangTai();
        if (ouput.toString() == "1") {
          this.$toast.success("Thực hiện nhận phiếu đăng ký thành công");
          await this.NAP_DS_DK(0);
        } else {
          this.$toast.error(ouput);
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async LAY_TON_VT(kieu) {
      if (this.params.p_ckbKhoGiao) {
        this.visible.colKho = false;
        this.enable.cboKho = true;
      } else {
        this.visible.colKho = true;
        this.enable.cboKho = false;
      }
      let data_st = [];
      this.ds_cat_temp = [];

      if (kieu == 0) {
        // Lấy tồn nút
        this.kho_giao_id = 0;
        this.gridVatTu = [];
        this.DSVatTuTonKho = []
        this.checkAllVT = false;
        this.rowSelectedVT = [];

        this.grcVT_NKHO = [];
        var ds_vt = this.grcDSVatTu_DaDK || [];
        if (ds_vt == null || ds_vt.length == 0) {
          this.$toast.error("Không có dữ liệu để lấy tồn vật tư");
          return;
        }
        if ((this.params.p_khogiao_TK == null || this.params.p_khogiao_TK == "") && this.tagFrm != "4") {
          this.$toast.error("Bạn chưa được gán kho giao không thể lấy tồn");
          return;
        }
        var vt_nhapkho = [];
        var ds_vattu_ton = [];

        var ds_vt_chon = this.grcDSVatTu_DaDK.filter((a) =>
          this.rowSelectedCT.includes(a.VTDK_ID)
        );

        if (ds_vt_chon != null && ds_vt_chon.length == 0) {
          for (let item of ds_vt) {
            if (parseInt(item.SL_CL) > 0) {
              item.SL_CHUYEN = 0;
              vt_nhapkho.push(item);
              ds_vattu_ton.push(item.VATTU_ID);
            }
          }
        } else {
          for (let item of ds_vt_chon) {
            if (parseInt(item.SL_CL) > 0) {
              item.SL_CHUYEN = 0;
              vt_nhapkho.push(item);
              ds_vattu_ton.push(item.VATTU_ID);
            }
          }
        }

        let k_id = 0;
        if (this.params.p_ckbKhoGiao) {
          k_id = this.tagFrm == "4" ? parseInt(this.params.tinhTonKho) : parseInt(this.params.p_khogiao_TK);
        }

        var res = await this.Load_getDSVatTuTonKho(k_id, ds_vattu_ton);

        this.clickTab(4);
        var dtVatTu = res;

        if (!dtVatTu.length && this.tagFrm != "4") {
          this.$toast.error(
            "Không tìm thấy danh sách vật tư tồn trong kho " +
              this.cboKho.find(
                (a) =>
                  a.KHO_ID.toString() == this.params.p_khogiao_TK.toString()
              ).TEN_KHO
          );
          return;
        }

        let json_list = "";
        if (dtVatTu.length) {
          let dt_kt = [];
          dtVatTu.forEach((a) => {
            dt_kt.push({
              VATTU_ID: a.VATTU_ID,
              LOHANG: a.LOHANG,
              LOAITBI_ID: a.LOAITBI_ID,
            });
          });

          json_list = dt_kt;
        }

        var dt_CTMTBI = await this.getDSCuonCap({
          khoId: k_id,
          listDs: json_list,
        });

        data_st.push(dtVatTu);
        data_st.push(dt_CTMTBI);

        this.kho_giao_id = this.params.p_khogiao_TK;
        this.grcVT_NKHO = vt_nhapkho;
        this.gridVatTu = dtVatTu;
        this.DSVatTuTonKho = dtVatTu // này dùng để lưu vật tư tồn kho khi filter
        this.checkAllVT = false;
        this.rowSelectedVT = [];
      } else if (kieu == 2) {
        // Lấy tồn nút
        this.grcTonKho = [];
        var ds_vattu_ton = [];
        var ds_vt = this.grcDSVatTu_DaDK;
        if (!ds_vt.length && !this.params.p_ckbGiuCho) {
          this.$toast.error("Không có dữ liệu để lấy tồn vật tư");
          return;
        }

        if (this.params.p_khogiao_CTDK.toString() == "" && this.tagFrm != "4") {
          if (kieu == 1) {
            this.$toast.error("Bạn chưa được gán kho không thể lấy tồn");
            return;
          } else {
            return;
          }
        }

        var ds_vt_chon = this.grcDSVatTu_DaDK.filter((a) =>
          this.rowSelectedCT.includes(a.VTDK_ID)
        );
        if (!this.params.p_ckbGiuCho) {
          if (ds_vt_chon.length == 0) {
            this.grcDSVatTu_DaDK.forEach((item) =>
              ds_vattu_ton.push(item.VATTU_ID)
            );
          } else {
            ds_vt_chon.forEach((item) => ds_vattu_ton.push(item.VATTU_ID));
          }
        }

        let k_id = 0;
        if (this.params.p_ckbFindAll) {
          k_id = this.tagFrm == "4" ? parseInt(this.params.tinhDangKy) : parseInt(this.params.p_khogiao_CTDK);
        }

        this.grcTonKho = await this.Load_getDSVatTuTonKho(k_id, ds_vattu_ton);
      }
    },
    grcVT_NKHO_Selected: function (dataItem) { 
      console.log(dataItem , " = dataItem") 
      // Filter cho dễ tìm 
      if(dataItem.length == 0) { 
        this.gridVatTu = this.DSVatTuTonKho
      }else{
        this.gridVatTu = this.DSVatTuTonKho.filter(e=>dataItem.includes(e.VATTU_ID))
      }
      
       
    },
    async Load_getDSVatTuTonKho(k_id, ds_vattu_ton) {
      var res = await this.getDSVatTuTonKho({
        body: {
          tagForm: this.tagFrm,
          khoId: k_id || "",
          ngayCt: this.f_DateToString(
            this.params.p_dtpNgayDK,
            "DD/MM/YYYY HH:mm:ss"
          ),
          chungTuId: 0,
          dsVatTuTon: ds_vattu_ton.toString(),
        },
      });

      return res;
    },
    async TRAPHIEU_frmDangTai(parameters) {
      // TẠo dữ liệu phiếu
      var dt = this.grcDSDangKy;
      if (dt.length == 0) {
        return;
      }
      var xdoc = [];
      for (let item of this.$refs.grvDSDangKy.getSelectedRecords()) {
        xdoc.push({ dk_id: item.DANGKY_ID.toString() });
      }

      var ketqua = await this.TraPhieuDKCapPhat({
        dangKy: xdoc,
        noiDungTra: parameters.toString(), //[1]
        nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd,
        mayCn: this.$auth.getUserName(), // ttnd.may_cn
        ipCn: "10.59.90.123", // ttnd.ip
        ttDk: 0,
      });
      if (ketqua == null || ketqua == "" || ketqua.length == 0) {
        return "Không có thông tin trả về khi thực hiện trả phiếu";
      } else {
        if (Array.isArray(ketqua) && ketqua.length) {
          return ketqua[0].message != "1" ? ketqua[0].message : "1";
        }
        return ketqua.toString() != "1" ? ketqua : "1";
      }
    },
    async TRAPHIEU() {
      // TẠo dữ liệu phiếu
      if (!(await this.KIEMTRA_PHIEU())) {
        return;
      }

      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn trả phiếu không ? ", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      var f_rm = this.$refs.frmNhapText;
      await f_rm.Load_frmNhapText();
      await f_rm.frmNhapText("Nhập nội dung trả phiếu");
      f_rm.Text.title = "Nhập nội dung trả phiếu";
      f_rm.functionName = "TRAPHIEU";
      this.$refs.popup_frmNhapText.show();
    },
    async XacNhan_frmNhapText() {
      try {
        this.loading(true);
        var frm = this.$refs.frmNhapText;

        if (frm.functionName == "XOA_CHUNGTU") {
          if (frm.DialogResult == DialogResultObj.OK) {
            console.log(frm.vkieu_luachon, " = frm.vkieu_luachon");
            let ketQua = await this.xoaChungTu3In1({
              chungtu_id: this.vchungtu_id,
              kieuxoa: frm.vkieu_luachon,
              sendmsm: 0,
              donvi_fmis: "",
            });
            let message =
              frm.vkieu_luachon == "1"
                ? "Xóa chứng từ thành công!"
                : "Xóa đồng bộ kế toán thành công!";
            this.$toast.success(message);

            await this.NAP_DS_CHUNGTU();
            if (frm.vkieu_luachon == 1 && this.params.p_KieuTH != 1) {
              await this.LAY_TON_VT(2);
            } else {
              await this.NAP_DS_THONGTIN();
              await this.LAY_TON_VT(0);
            }
          }
        }

        if (frm.functionName == "TRAPHIEU") {
          if (frm.DialogResult == DialogResultObj.OK) {
            let ouput = await this.TRAPHIEU_frmDangTai(frm.Output);
            if (ouput.toString() == "1") {
              this.$toast.success("Thực hiện trả phiếu thành công");
              await this.NAP_DS_DK(0);
            } else {
              this.$toast.error(ouput);
            }
          }
        }

        if (frm.functionName == "TAO_CHUNGTU") {
          if (frm.DialogResult == DialogResultObj.OK) {
            var dt = this.gridVatTu;
            if (dt.length == 0) {
              return;
            }

            var dt_vtdk = this.grcVT_NKHO;
            if (dt_vtdk.length == 0) {
              return;
            }
            
            if(this.tagFrm != "4"){
              if (this.params.p_ckbKhoGiao) {
                // Tạo dữ liệu
                var xdoc_vt = [];
                let DSVatTuChecked = this.gridVatTu.filter(
                  (a) =>
                    this.rowSelectedVT.includes(a.STT) && parseFloat(a.SL_XUAT) > 0
                );               
                for (let dx of DSVatTuChecked) {
                  let row = {
                    vtid: dx.VATTU_ID,
                    lh: dx.LOHANG,
                    dg: dx.DONGIA,
                    slnhap: this.chonvattu_lh ? "0" : parseFloat(dx.SL_XUAT),
                    ngaydat: this.params.p_ckbDatCho
                      ? this.params.p_spinSoNgay
                      : "0",
                    tag: this.tagFrm,
                    trangthai: dx.TRANGTHAI,
                    chatluong_id_temp: dx.CHATLUONG_ID_TEMP || "",
                    hethong_id_temp: dx.HETHONG_ID_TEMP || "",
                    dongtbi_id_temp: dx.DONGTBI_ID_TEMP || "",
                  };

                  xdoc_vt.push(row);
                }

                var xdoc_vtdk = [];
                for (let dx of dt_vtdk) {
                  if (parseInt(dx.SL_CHUYEN) > 0) {
                    xdoc_vtdk.push({
                      vtid: dx.VTDK_ID,
                      soluong: this.chonvattu_lh ? "0" : parseFloat(dx.SL_CHUYEN),
                    });
                  } else {
                    if (this.chonvattu_lh) {
                      let DSVatTuChecked = this.gridVatTu.filter((a) =>
                        this.rowSelectedVT.includes(a.STT)
                      );
                      var lenght = DSVatTuChecked.filter(
                        (a) => a.VATTU_ID.toString() == dx.VATTU_ID.toString()
                      ).length;
                      if (lenght > 0 && dx.SL_CL.toString() != "0") {
                        xdoc_vtdk.push({
                          vtid: dx.VTDK_ID.toString(),
                          soluong: this.chonvattu_lh ? "0" : dx.SL_CHUYEN,
                          kho_id: this.params.p_khogiao_CTDK,
                        });
                      }
                    }
                  }
                }
                // ĐỨC rà soát chổ cuộn cáp chưa được, tạm thời để fix sau
                let xdoc_temp = [];
                if (this.chonvattu_lh) {
                  var frm = this.$refs.frmChiaCuonCap;
                  frm.kieu = 1;
                  frm.Text = "Chọn số lượng vật tư cho đăng ký";
                  frm.xml_ds_vt_dk = xdoc_vtdk;
                } else {
                  xdoc_temp = xdoc_vtdk;
                }

                // let json_cuoncap = "-1"
                let json_cuoncap = [];
                if (this.ds_cat_temp.length) {
                  var ds_xacnhan = this.ds_cat_temp;
                  ds_xacnhan.forEach((a) => {
                    delete a.TEN_VT;
                  });
                  // json_cuoncap = JSON.stringify(ds_xacnhan)
                  json_cuoncap = ds_xacnhan;
                }

                let kq = await this.TaoChungTuDKCapPhatV2({
                  khoGiaoId: parseInt(this.params.p_khogiao_TK),
                  khoNhanId: parseInt(this.params.p_KhoDen),
                  donViGiaoId: this.$auth.getDonViID(),
                  nhanVienGiaoId: this.$auth.getNhanVienID(),
                  ngayCt: this.f_DateToString(
                    this.params.p_dtpNgayDK,
                    "DD/MM/YYYY HH:mm:ss"
                  ),
                  noiDung: frm.Output,
                  nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
                  mayCn: this.$auth.getUserName(), //ttnd.may_cn
                  ipCn: "10.59.90.123", //ttnd.ip
                  dsVatTu: xdoc_vt,
                  dsVatTuDk: xdoc_temp,
                  dangKyId: this.vdangky_luoi_id,
                  dsCuonCap: json_cuoncap,
                  dsKho: [],
                  checkAll: true,
                });
                this.$toast.success("Tạo chứng từ thành công");
                this.ds_cat_temp = [];
              } else {
                // Tạo dữ liệu
                var xdoc_vt = [];
                
                let DSVatTuChecked = this.gridVatTu.filter(
                  (a) =>
                    this.rowSelectedVT.includes(a.STT) && parseFloat(a.SL_XUAT) > 0
                );
             
                for (let dx of DSVatTuChecked) {
                  let row = {
                    vtid: dx.VATTU_ID,
                    lh: dx.LOHANG,
                    dg: dx.DONGIA,
                    slnhap: dx.SL_XUAT,
                    kho_id: dx.KHO_ID,
                    ngaydat: this.params.p_spinSoNgay,
                    tag: this.tagFrm,
                    trangthai: dx.TRANGTHAI,
                    chatluong_id_temp: dx.CHATLUONG_ID_TEMP || "",
                    hethong_id_temp: dx.HETHONG_ID_TEMP || "",
                    dongtbi_id_temp: dx.DONGTBI_ID_TEMP || "",
                  };
                  xdoc_vt.push(row);
                }

                let xdoc_vtdk = [];

                if (!this.chonvattu_lh) {
                  
                  for (let dx of this.dt_temp) {
                    let row = {
                      vtid: dx.VTDK_ID,
                      soluong: dx.SL_XUAT,
                      kho_id: dx.KHO_ID,
                    };
                    xdoc_vtdk.push(row);
                  }
                } else {
                  let DSVatTuChecked = this.gridVatTu.filter((a) =>
                    this.rowSelectedVT.includes(a.STT)
                  );
                  if (DSVatTuChecked.length) {
                    var distinctValues = [
                      ...new Set(DSVatTuChecked.map((a) => a.KHO_ID)),
                    ];
                    for (var drl of distinctValues) {
                      for (let dx of dt_vtdk) {
                        var lenght_kho = DSVatTuChecked.filter(
                          (a) =>
                            a.VATTU_ID.toString() == dx.VATTU_ID.toString() &&
                            a.KHO_ID.toString() == drl.KHO_ID.toString()
                        );
                        if (lenght_kho.length) {
                          let row = {
                            vtid: dx.VTDK_ID,
                            soluong: "0",
                            kho_id: drl.KHO_ID,
                          };
                          xdoc_vtdk.push(row);
                        }
                      }
                    }
                  }
                }
               
                if (this.chonvattu_lh) {
                  var frm = this.$refs.frmChiaCuonCap;
                  frm.vkieu = 1;
                  frm.Text = "Chọn số lượng vật tư cho đăng ký";
                  frm.xml_ds_vt_dk = xdoc_vtdk;
                }
                // let json_cuoncap = "-1"
                let json_cuoncap = [];
                if (this.ds_cat_temp.length) {
                  var ds_xacnhan = this.ds_cat_temp;
                  ds_xacnhan.forEach((a) => {
                    delete a.TEN_VT;
                  });
                  // json_cuoncap = JSON.stringify(ds_xacnhan)
                  json_cuoncap = ds_xacnhan;
                }
                // let json_kho = JSON.stringify(this.dt_temp) đổi dạng truyền
                let json_kho = this.dt_temp;

                let kq = await this.TaoChungTuDKCapPhatV3({
                  khoGiaoId: "0", // gộp API, tired
                  khoNhanId: parseInt(this.params.p_KhoDen),
                  donViGiaoId: this.$auth.getDonViID(),
                  nhanVienGiaoId: this.$auth.getNhanVienID(),
                  ngayCt: this.f_DateToString(
                    this.params.p_dtpNgayDK,
                    "DD/MM/YYYY HH:mm:ss"
                  ),
                  noiDung: frm.Output,
                  nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
                  mayCn: this.$auth.getUserName(), //ttnd.may_cn
                  ipCn: "10.59.90.123", //ttnd.ip
                  dsVatTu: xdoc_vt,
                  dsVatTuDk: xdoc_vtdk,
                  dangKyId: this.vdangky_luoi_id,
                  dsCuonCap: json_cuoncap,
                  dsKho: json_kho,
                  checkAll: false, // V3 là false, V2 là true
                });
                this.$toast.success("Tạo chứng từ thành công");
                this.ds_cat_temp = [];
              }
            }else{ // Tạo chứng từ cho KHDN
              
              let p_ds_dn_luanchuyen = [{
                  ND_DENGHI: frm.Output,
                  NGAY_CN : this.f_DateToString( new Date() ,"DD/MM/YYYY HH:mm:ss" ),
                  NGUOI_CN: this.$auth.getUserName(),
                  LOAIPHIEU_ID : 97 , // hardcode cho KHDN
                  NGAY_YC : this.f_DateToString( new Date() ,"DD/MM/YYYY HH:mm:ss" ),
                  TINH_GIAO_ID : this.params.tinhTonKho,
                  TINH_NHAN_ID : this.$auth.getPhanVungID(),
                  DANGKY_ID : this.vdangky_luoi_id
                }                
              ]

              let p_ds_ct_vattu = []
              for(let e of this.grcDSVatTu_DaDK){
                p_ds_ct_vattu.push({
                  VATTU_ID : e.VATTU_ID,
                  SOLUONG : e.SOLUONG,
                  DONGIA : e.DONGIA ,
                  TIEN : e.TIEN,
                  VAT : e.VAT,
                  VTDK_ID : e.VTDK_ID 
                })
              }


              var xdoc_vt = [];
              let DSVatTuChecked = this.gridVatTu.filter(
                (a) =>
                  this.rowSelectedVT.includes(a.STT) && parseInt(a.SL_XUAT) > 0
              );

              
              for (let dx of DSVatTuChecked) {
                let row = {
                  vtid: dx.VATTU_ID,
                  lh: dx.LOHANG,
                  dg: dx.DONGIA,
                  slnhap: this.chonvattu_lh ? "0" : dx.SL_XUAT,
                  ngaydat: this.params.p_ckbDatCho
                    ? this.params.p_spinSoNgay
                    : "0",
                  tag: this.tagFrm,
                  trangthai: dx.TRANGTHAI,
                  chatluong_id_temp: dx.CHATLUONG_ID_TEMP || "",
                  hethong_id_temp: dx.HETHONG_ID_TEMP || "",
                  dongtbi_id_temp: dx.DONGTBI_ID_TEMP || "",
                };

                xdoc_vt.push(row);
              }


              var xdoc_vtdk = [];
              for (let dx of dt_vtdk) {
                if (parseInt(dx.SL_CHUYEN) > 0) {
                  xdoc_vtdk.push({
                    vtid: dx.VTDK_ID,
                    soluong: this.chonvattu_lh ? "0" : dx.SL_CHUYEN.toString(),
                  });
                } else {
                  if (this.chonvattu_lh) {
                    let DSVatTuChecked = this.gridVatTu.filter((a) =>
                      this.rowSelectedVT.includes(a.STT)
                    );
                    var lenght = DSVatTuChecked.filter(
                      (a) => a.VATTU_ID.toString() == dx.VATTU_ID.toString()
                    ).length;
                    if (lenght > 0 && dx.SL_CL.toString() != "0") {
                      xdoc_vtdk.push({
                        vtid: dx.VTDK_ID.toString(),
                        soluong: this.chonvattu_lh ? "0" : dx.SL_CHUYEN,
                        kho_id: this.params.p_khogiao_CTDK,
                      });
                    }
                  }
                }
              }
              // ĐỨC rà soát chổ cuộn cáp chưa được, tạm thời để fix sau
              let xdoc_temp = [];
              if (this.chonvattu_lh) {
                var frm = this.$refs.frmChiaCuonCap;
                frm.kieu = 1;
                frm.Text = "Chọn số lượng vật tư cho đăng ký";
                frm.xml_ds_vt_dk = xdoc_vtdk;
              } else {
                xdoc_temp = xdoc_vtdk;
              }
            
              let json_cuoncap = [];
              if (this.ds_cat_temp.length) {
                var ds_xacnhan = this.ds_cat_temp;
                ds_xacnhan.forEach((a) => {
                  delete a.TEN_VT;
                });                
                json_cuoncap = ds_xacnhan;
              }
             
              let kq = await this.taoChungTuKHDN({
                kieu: 1,
                ds_dn_luanchuyen: p_ds_dn_luanchuyen,
                ds_ct_vattu: p_ds_ct_vattu,
                nhanvien_id: this.$auth.getNhanVienID(),
                donvi_id: this.$auth.getDonViID(),
                ds_vt: xdoc_vt,
                ds_vtdk: xdoc_temp,
                dangky_id: this.vdangky_luoi_id,
                ds_cuoncap: json_cuoncap,                
              });
              this.$toast.success("Tạo chứng từ thành công");
              this.ds_cat_temp = [];
            }
            // if (grvDSDangKy.FocusedRowHandle >= 0)
            await this.NAP_DS_THONGTIN();
            await this.LAY_TON_VT(0);
          }
        }

        if (frm.functionName == "GIAHAN") {
          if (frm.DialogResult == DialogResultObj.OK) {
            var kqua = await this.GIAHAN_DATCHO_CHUNGTU({
              chungTuId: parseInt(this.grvDSChungTu_FocusedRow["CHUNGTU_ID"]),
              ngayGiaHan: this.f_DateToString(frm.DateOutput, "DD/MM/YYYY"),
              nguoiCn: this.$auth.getUserName(), // ttnd.ma_nd
            });
            if (kqua != "1") {
              this.$toast.error(kqua);
            } else {
              this.$toast.error("Cập nhật ngày hết hạn thành công !");
              await this.NAP_DS_CHUNGTU();
            }
          }
        }

        if (frm.functionName == "DATCHO_EXCEL") {
          if (frm.DialogResult == DialogResultObj.OK) {
            try {
              this.loading(true);
              var dtVatTu = await this.getDSTonKhoDatCho({
                ds_vattukho: this.DSDatCho_Excel,
                ngay_ct: this.f_DateToString(
                  this.params.p_dtpNgayDK,
                  "DD/MM/YYYY HH:mm:ss"
                ),
              });

              // Tạo dữ liệu
              let ds_vt = {};
              let xdoc_vt = [];
              let t = frm.t;
              let z = frm.z;

              for (let dtz of t) {
                var dt_kt = dtVatTu.filter(
                  (item) =>
                    item.VATTU_ID == dtz.VATTU_ID &&
                    item.LOHANG == dtz.LOHANG &&
                    item.KHO_ID == dtz.KHO_ID
                );
                if (dt_kt.length) {
                  var dx = dt_kt[0];
                  xdoc_vt.push({
                    vtid: dx.VATTU_ID,
                    lh: dx["LOHANG"],
                    dg: dx["DONGIA"],
                    slnhap: dtz.SL_DAT,
                    kho_id: dx["KHO_ID"],
                    ngaydat: this.params.p_spNgayGiu,
                    tag: this.tagFrm,
                    kieu_th: this.params.p_KieuTH,
                  });
                } else {
                  this.$toast.error(
                    "Bạn hãy kiểm tra lại lô hàng " +
                      dtz["LOHANG"] +
                      ", mã VT " +
                      dtz["MA_VT"] +
                      " của mã kho " +
                      dtz["MA_KHO"] +
                      " không có tồn "
                  );
                  return;
                }
              }

              // let json_cuoncap = "-1"
              let json_cuoncap = [];
              let distinctValues = [...new Set(t.map((a) => a.KHO_ID))];
              let json_kho = [];
              distinctValues.forEach((value) =>
                json_kho.push({ KHO_ID: value })
              );

              var kq = await this.TaoChungTuDKCapPhatV3({
                khoGiaoId: 0,
                khoNhanId: parseInt(this.params.p_KhoDatDen),
                donViGiaoId: this.$auth.getDonViID(),
                nhanVienGiaoId: this.$auth.getNhanVienID(),
                ngayCt: this.f_DateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                noiDung: frm.Output,
                nguoiCn: this.$auth.getUserName(),
                mayCn: this.$auth.getUserName(), //ttnd.may_cn
                ipCn: "10.59.90.121", //ttnd.ip
                dsVatTu: xdoc_vt,
                dsVatTuDk: [],
                dangKyId: 0,
                dsCuonCap: json_cuoncap,
                dsKho: json_kho,
                checkAll: false, // false => taoChungTuDangKyCapPhatV3
              });
              this.$toast.success("Tạo chứng từ đặt chỗ thành công");
              await this.NAP_DS_CHUNGTU();
            } catch (e) {
              this.$toast.error(e.data.message);
            } finally {
              this.loading(false);
            }
          }
        }

        if (frm.functionName == "DAT_CHO") {
          if (frm.DialogResult == DialogResultObj.OK) {
            let t = frm.t;
            var dt = JSON.parse(JSON.stringify(t));
            if (this.params.p_ckbFindAll) {
              // Tạo dữ liệu
              var xdoc_vt = [];
              dt.forEach((dx) => {
                if (dx.SL_XUAT.toString() != "0") {
                  xdoc_vt.push({
                    vtid: dx.VATTU_ID.toString(),
                    lh: dx.LOHANG.toString(),
                    dg: dx.DONGIA.toString(),
                    slnhap: dx.SL_XUAT.toString(),
                    ngaydat: this.params.p_spNgayGiu.toString(),
                    tag: this.tagFrm,
                  });
                }
              });
              let xdoc_vtdk = "";
              // let json_cuoncap = "-1"
              let json_cuoncap = [];
              let kq = await this.TaoChungTuDKCapPhatV2({
                khoGiaoId: parseInt(this.params.p_khogiao_CTDK),
                khoNhanId: parseInt(this.params.p_KhoDatDen),
                donViGiaoId: this.$auth.getDonViID(),
                nhanVienGiaoId: this.$auth.getNhanVienID(),
                ngayCt: this.f_DateToString(
                  this.params.p_dtpNgayDK,
                  "DD/MM/YYYY  HH:mm:ss"
                ),
                noiDung: frm.Output,
                nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
                mayCn: this.$auth.getUserName(), //ttnd.may_cn
                ipCn: "10.59.90.123", //ttnd.ip
                dsVatTu: xdoc_vt,
                dsVatTuDk: [],
                dangKyId: -1,
                dsCuonCap: json_cuoncap,
                dsKho: [],
                checkAll: true,
              });
              this.$toast.success("Tạo chứng từ đặt chỗ thành công");
            } else {
              // Tạo dữ liệu
              var xdoc_vt = [];
              dt.forEach((dx) => {
                if (dx.SL_XUAT.toString() != "0") {
                  xdoc_vt.push({
                    vtid: dx.VATTU_ID.toString(),
                    lh: dx.LOHANG.toString(),
                    dg: dx.DONGIA.toString(),
                    slnhap: dx.SL_XUAT.toString(),
                    kho_id: dx.KHO_ID.toString(), // có khác cái này
                    ngaydat: this.params.p_spNgayGiu.toString(),
                    tag: this.tagFrm,
                  });
                }
              });
              let json_cuoncap = "-1";
              let distinctValues = [...new Set(dt.map((a) => a.KHO_ID))];
              let json_kho = [];
              distinctValues.forEach((value) =>
                json_kho.push({ KHO_ID: value })
              );
              var kq = await this.TaoChungTuDKCapPhatV3({
                khoGiaoId: "0",
                khoNhanId: parseInt(this.params.p_KhoDatDen),
                donViGiaoId: this.$auth.getDonViID(),
                nhanVienGiaoId: this.$auth.getNhanVienID(),
                ngayCt: this.f_DateToString(new Date(), "DD/MM/YYYY HH:mm:ss"),
                noiDung: frm.Output,
                nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
                mayCn: this.$auth.getUserName(), //ttnd.may_cn
                ipCn: "10.59.90.123", //ttnd.ip
                dsVatTu: xdoc_vt,
                dsVatTuDk: [],
                dangKyId: 0 || "",
                dsCuonCap: [],
                dsKho: json_kho,
                checkAll: false, // V3 là false, V2 là true
              });
              this.$toast.success("Tạo chứng từ đặt chỗ thành công");
            }

            if (this.$refs.grvDSDangKy.getSelectedRecords()) {
              await this.NAP_DS_THONGTIN();
              await this.LAY_TON_VT(2);
            }
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async HUYPHIEU_frmDangTai() {
      // try {
      // TẠo dữ liệu phiếu
      var dt = this.grcDSDangKy;
      if (!dt.length) {
        return;
      }
      var xdoc = [];
      // for (let i in dt){
      //   let item = dt[i]
      //   if (!item.ISCHECK || item.ISCHECK.toString() != "1"){
      //     continue
      //   }
      //   xdoc.push({dk_id: item.DANGKY_ID})
      // }
      this.$refs.grvDSDangKy
        .getSelectedRecords()
        .forEach((item) => xdoc.push({ dk_id: item.DANGKY_ID }));

      var ketqua = await this.TraPhieuDKCapPhat({
        dangKy: xdoc,
        noiDungTra: "",
        nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd,
        mayCn: this.$auth.getUserName(), // ttnd.may_cn,
        ipCn: "10.59.90.123", //ttnd.ip,
        ttDk: 3,
      });

      if (ketqua == null || ketqua == "") {
        return "Không có thông tin trả về khi thực hiện trả phiếu";
      } else {
        if (Array.isArray(ketqua) && ketqua.length) {
          return ketqua[0].toString() != "1" ? ketqua[0] : "1";
        }
        return ketqua.toString() != "1" ? ketqua : "1";
      }
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi giao phiếu ")
      // }
    },
    async HUYPHIEU() {
      try {
        if (!(await this.KIEMTRA_PHIEU())) {
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn chắc chắn muốn hủy phiếu không ?", {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          })
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        let ouput = await this.HUYPHIEU_frmDangTai();
        this.$toast.success("Thực hiện hủy phiếu thành công");
        await this.NAP_DS_DK(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async grvDSDangKy_FocusedRowChanged(row) {
      try {
        this.loading(true);
        this.params.p_txtngayyc = "";
        this.params.p_txtthoigiandk = "";
        this.params.p_txtduan = "";
        this.params.p_txtkho = "";
        this.params.p_txtkho = "";
        this.params.p_txtdonvidk = "";
        if (row) {
          this.grvDSDangKy_FocusedRow = row;
          this.CLEAR();
          await this.NAP_DS_THONGTIN();
        } else {
          this.CLEAR();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnTraLai_Click() {
      try{
        await this.TRAPHIEU();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },
    grvDSVatTu_DaDK_FocusedRowChanged(row) {
      if (row) {
        this.grcDSVatTu_DaDK_FocusedRow = row.rowData;
      }
    },
    grvDSVatTu_DaDK_CellValueChanged() {
      if (this.params.p_trangthai.toString() == "2") {
        if (!dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT.toString())) {
          return;
        }
      }
      //...
    },
    async tsbtnGiaoPhieu_Click() {
      try {
        if (this.params.p_trangthai.toString() == "1") {
          await this.NHAN_DK();
        } else {
          await this.DUYET_DK();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
      }
    },
    async DUYET_DK() {
      try {
        this.loading(true);
        var dt = this.grcDSVatTu_DaDK;

        if (dt.length == 0) {
          return;
        }
        var xdoc = [];
        if (this.grvDSDangKy_FocusedRow.TTDK_ID.toString() == "4") {
          this.$toast.error("Phiếu đã hoàn thành không thể duyệt");
          return;
        }
        let dem = 0;
        var ds_check = this.grcDSVatTu_DaDK.filter((a) =>
          this.rowSelectedCT.includes(a.VTDK_ID)
        );
        for (let item of ds_check) {
          xdoc.push({
            vtdk_id: item.VTDK_ID.toString(),
            slduyet: item.SL_DUYET.toString(),
          });
          dem += 1;
        }
        if (dem == 0) {
          this.$toast.error("Bạn chưa chọn số lượng duyệt");
          return;
        }
        var ds_kt = await this.getSLVatTuDK({
          listPhieu: xdoc,
          dangKyId: this.vdangky_luoi_id,
        });

        if (ds_kt.length && ds_kt[0]["COUNT(A.VTDK_ID)"].toString() != "0") {
          this.$toast.error(
            "Bạn hãy kiểm tra lại số lượng duyệt không được nhỏ hơn số lượng đã cấp"
          );
          return;
        }
        var ds_ktt = await this.getSLDangKyTheoTT({
          dangKyId: this.vdangky_luoi_id,
        });

        if (ds_ktt.toString() != "0") {
          this.$toast.error(
            " Trạng thái đăng ký đã hoàn thành không thể duyệt"
          );
          return;
        }

        var kq = await this.CapNhatVatTuDKTheoFile({
          listVatTu: JSON.stringify(xdoc),
        });
        this.$toast.success("Đã duyệt thành công");
        let dangKyFocus = this.grvDSDangKy_FocusedRow.DANGKY_ID
        await this.NAP_DS_DK(0);
        await this.FocusGridViewRow(dangKyFocus)
      } catch (error) {
      } finally {
        this.loading(false);
      }
    },
    async cboTrangThai_EditValueChanged() {
      try {
        if (this.cboTrangThai_firstLoad) {
          // tránh lặp lúc mới vào form
          this.cboTrangThai_firstLoad = false;
          return;
        }
        this.loading(true);
        this.visible.tsbtnBoSungVT = true; // luôn bật nút này theo yêu cầu HNI
        this.visible.tsbtnGiaoPhieu = false;
        this.visible.tsbtnTraLai = false;
        this.visible.btnHuyBo = false;
        this.visible.tsbtnHoanThanh = false;
        this.visible.tsbtnNhapKho = false;
        this.visible.slCL = false;
        this.visible.slChuyen = false;
        this.visible.tsbtnThemVT = false;
        this.visible.btnHuyXN = false;
        this.visible.tsbtnXacNhan = false;
        this.Text.tsbtnGiaoPhieu = "Nhận đăng ký";

        if (this.params.p_trangthai.toString() == "1") {
          // quý, bỏ điều kiện trạng thái vt 21/3/22
          // this.visible.grvDSDangKy.TRANGTHAI_VT = false
          this.enable.grvDSVatTu_DaDK.LYDO_DUYET = true;
          this.enable.slDuyet = true;
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
            this.visible.tsbtnGiaoPhieu = true;
            this.visible.tsbtnBoSungVT = true;
            this.visible.tsbtnTraLai = true;
          }
          this.visible.xtraTabPage1 = true;
          this.visible.xtraTabPage2 = false;
        } else if (this.params.p_trangthai.toString() == "2") {
          // this.visible.grvDSDangKy.TRANGTHAI_VT = true
          this.visible.xtraTabPage2 = true;
          this.visible.xtraTabPage1 = true;
          this.visible.slCL = true;
          this.enable.grvDSVatTu_DaDK.LYDO_DUYET = false;
          this.enable.slDuyet = false;
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO)) {
            this.visible.tsbtnXacNhan = true;
            this.visible.tsbtnThemVT = true;
            this.visible.tsbtnHoanThanh = true;
            this.visible.tsbtnNhapKho = true;
            this.visible.btnHuyXN = true;
            this.enable.btnHuyXN = false;
          }
          if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
            this.visible.tsbtnGiaoPhieu = true;
            this.Text.tsbtnGiaoPhieu = "Duyệt VT";
            this.enable.slDuyet = true;
            this.visible.btnHuyBo = true;
          }
        }

        await this.NAP_DS_DK(0);
        this.clickTab(3);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async FocusGridViewRow(value){
      let indexs = this.grcDSDangKy.findIndex(a=>a.DANGKY_ID != null && a.DANGKY_ID.toString() == value.toString())
      if (indexs != -1) {
        if (!(
            (indexs + 1) > (this.$refs['grvDSDangKy'].pageIndexData * this.$refs['grvDSDangKy'].pageSizeData) &&
            ((indexs + 1) <= ((this.$refs['grvDSDangKy'].pageIndexData + 1 ) * this.$refs['grvDSDangKy'].pageSizeData))
            )){
            this.$refs['grvDSDangKy'].$refs.pagination.gotoPage(parseInt(indexs /this.$refs['grvDSDangKy'].pageSizeData ))
        }
        let self = this
        let ind = parseInt(indexs%this.$refs['grvDSDangKy'].pageSizeData)
        setTimeout(() => {
          self.$refs['grvDSDangKy'].grid.selectRow(ind,true)
        }, 1000);
      }
    },
    async btnHuyBo_Click() {
      await this.HUYPHIEU();
    },
    async tsbtnThemVT_Click() {
      try {
        this.loading(true);
        await this.LAY_TON_VT(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async XACNHAN() {
      try {
        this.loading(true);
        if (this.gridVatTu.length == 0) {
          this.$toast.error("Không có danh sách vật tư tồn kho.");
          return;
        }

        var ds_dk = this.grcVT_NKHO;
        if (ds_dk.length == 0) {
          this.$toast.error(
            "Không có số lượng đăng ký định chuyển. Không thể nhập kho"
          );
          return;
        }

        if (this.rowSelectedVT.length == 0) {
          this.$toast.error("Bạn chưa chọn danh sách vật tư cần xuất kho");
          return;
        }
        if (
          ds_dk.filter((a) => parseFloat(a.SL_CL) == 0).length == ds_dk.length
        ) {
          this.$toast.error("Danh sách không còn vật tư để chuyển");
          return;
        }
        let DSVatTuChecked = this.gridVatTu.filter((a) =>
          this.rowSelectedVT.includes(a.STT)
        );
        this.dt_temp = []
        for (let dr of DSVatTuChecked) {
          let soluong = parseFloat(dr.SL_XUAT);
          let vtdk_id = "0";

          if (!this.chonvattu_lh) {
            //neu khong phai chon theo lo thi tru tu dong
            for (let dr_v of ds_dk) {
              if (
                dr.VATTU_ID.toString() == dr_v.VATTU_ID.toString() &&
                dr_v.SL_CHUYEN.toString() != dr_v.SL_CL.toString()
              ) {
                let sl_cl = parseFloat(dr_v.SL_CL);
                let sl_chuyen = parseFloat(dr_v.SL_CHUYEN);

                if (soluong >= sl_cl - sl_chuyen) {
                  dr_v.SL_CHUYEN = sl_chuyen + sl_cl - sl_chuyen;
                  await this.edit_grcVT_NKHO(
                    dr_v.VTDK_ID,
                    "SL_CHUYEN",
                    dr_v.SL_CHUYEN
                  );
                  soluong = soluong - (sl_cl - sl_chuyen);
                } else {
                  dr_v.SL_CHUYEN = sl_chuyen + soluong;
                  await this.edit_grcVT_NKHO(
                    dr_v.VTDK_ID,
                    "SL_CHUYEN",
                    dr_v.SL_CHUYEN
                  );
                  soluong = 0;
                }
                vtdk_id = dr_v.VTDK_ID.toString();
              }
            }
            // quy them vao
            this.$refs.grvVT_NKHO.grid.refresh();
            this.$refs.grvVT_NKHO.grid.refreshColumns();
            dr.SL_XUAT = parseFloat(dr.SL_XUAT) - soluong;
          }
          this.dt_temp.push({
            KHO_ID: dr.KHO_ID,
            LOHANG: dr.LOHANG,
            VATTU_ID: dr.VATTU_ID,
            SL_XUAT: this.chonvattu_lh ? 0 : dr["SL_XUAT"],
            DONGIA: dr.DONGIA,
            VTDK_ID: vtdk_id,
            SL_DUYET: 0,
          });
        }

        this.enable.tsbtnXacNhan = false;
        this.enable.btnHuyXN = true;
        this.enable.tsbtnThemVT = false;
        this.enable.gridVatTu.colCuon = true;
        this.enable.gridVatTu.SL_XUAT = false;
        this.enable.gridVatTu.CHON = false;
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async edit_grcVT_NKHO(key, field, value) {
      this.grcVT_NKHO.forEach((a) => {
        if (a.VTDK_ID.toString() == key) {
          a[field] = value;
        }
      });
    },
    async tsbtnXacNhan_Click() {
      await this.XACNHAN();
    },
    async btnHuyXN_Click() {
      try {
        this.loading(true);
        await this.LAY_TON_VT(0);
        this.enable.btnHuyXN = false;
        this.enable.tsbtnThemVT = true;
        this.enable.tsbtnXacNhan = true;
        this.enable.gridVatTu.CHON = true;
        this.enable.gridVatTu.SL_XUAT = true;
        this.enable.gridVatTu.colCuon = true;
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KIEMTRA_TAOCHUNGTU() {
      var dt = this.gridVatTu;
      if (dt.length == 0) {
        this.$toast.error("Không có danh sách vật tư tồn kho.");
        return false;
      }
      if (this.params.p_ckbDatCho) {
        if (this.params.p_spinSoNgay.toString() == "0") {
          this.$toast.error("Ngày đặt giữ chỗ phải khác 0");
          return false;
        }
      }

      var ds_dk = this.grcVT_NKHO;
      if (ds_dk.length == 0) {
        this.$toast.error(
          "Không có số lượng đăng ký định chuyển. Không thể nhập kho"
        );
        return false;
      }

      if (this.rowSelectedVT.length == 0) {
        this.$toast.error("Bạn chưa chọn danh sách vật tư cần xuất kho");
        return false;
      }
      if (this.enable.tsbtnXacNhan) {
        this.$toast.error(
          "Bạn chưa xác nhận không thể tạo chứng từ chuyển kho"
        );
        return false;
      }
      if(this.tagFrm != "4"){
        if (this.params.p_ckbKhoGiao) {
          if (this.params.p_KhoDen == null || this.params.p_KhoDen == "") {
            this.$toast.error("Bạn chưa chọn kho đến");
            return false;
          }
          if (
            this.params.p_khogiao_TK == null ||
            this.params.p_khogiao_TK == ""
          ) {
            this.$toast.error("Bạn chưa chọn kho giao");
            return false;
          }
          if (
            this.params.p_khogiao_TK.toString() != this.kho_giao_id.toString()
          ) {
            this.$toast.error(
              "Bạn thay đổi kho giao không đúng với kho lấy danh sách vật tư tồn"
            );
            return false;
          }
          if (
            this.params.p_khogiao_TK.toString() == this.params.p_KhoDen.toString()
          ) {
            this.$toast.error("Hai kho từ và đến không thể giống nhau");
            return false;
          }
        } else {
          if (this.params.p_KhoDen == null || this.params.p_KhoDen == "") {
            this.$toast.error("Bạn chưa chọn kho đến");
            return false;
          }
          if (this.dt_temp.length == 0) {
            this.$toast.error("Không lấy được thông tin nhập kho");
            return false;
          }
          if (
            this.dt_temp.filter(
              (a) => a.KHO_ID.toString() == this.params.p_KhoDen.toString()
            ).length
          ) {
            this.$toast.error("Hai kho từ và đến không thể giống nhau");
            return false;
          }
        }
      }
      return true;
    },
    async TAO_CHUNGTU() {
      if (!(await this.KIEMTRA_TAOCHUNGTU())) {
        return;
      }
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn chắc chắn muốn nhập vào kho " + this.getTenKhoDen() + " không ?",
          {
            title: "Thông báo",
            centered: true,
            size: "md",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
          }
        )
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          } else {
            return;
          }
        });

      if (!isBoxConfirm) {
        return;
      }

      var f_rm = this.$refs.frmNhapText;
      await f_rm.Load_frmNhapText();
      await f_rm.frmNhapText("Nhập nội dung chứng từ");
      f_rm.Text.title = "Nhập nội dung chứng từ";
      f_rm.functionName = "TAO_CHUNGTU";

      let ghichu = await this.SinhNoiDungDK({
        dangKyId: this.vdangky_luoi_id,
        chungTuId: this.vchungtu_id || 0,
      });
      if (ghichu.toString()) {
        f_rm.params.txtNhapText = ghichu.toString();
      }
      this.$refs.popup_frmNhapText.show();
    },
    getTenKhoDen() {
      let res = this.cboKhoDen.find(
        (a) => a.KHO_ID.toString() == this.params.p_KhoDen.toString()
      );
      if (res != null && res != undefined) {
        return res.TEN_KHO;
      }
      return "";
    },
    async tsbtnNhapKho_Click() {
      await this.TAO_CHUNGTU();
    },
    async frmDuyetVT_DK_Shown() {
      await this.NAP_DS_COMBO();
      this.params.p_dtpNgayDK = new Date();
    },
    async tsbtnBoSungVT_Click() {
      if (this.visible.splDSVT == "Panel1") {
        this.Text.tsbtnBoSungVT = "Ẩn DS";
        this.visible.splDSVT = "Both";
      } else {
        this.Text.tsbtnBoSungVT = "Bổ sung VT";
        this.visible.splDSVT = "Panel1";
      }
    },
    cboTimKiem_EditValueChanged(data) {
      this.showLocVT = !this.showLocVT;
      if (Object.keys(data).length) {
        this.$root.showLoading(true);
        this.selectedLocVT = data.TEN_VT;
        this.params.p_TimKiem = data.VATTU_ID;
        if (data.VATTU_ID == 1) {
          this.tvVatTu = this.tvVatTu_Goc;
        } else {
          this.tvVatTu = this.tvVatTu_Goc.filter(
            (e) => e.VATTU_ID == data.VATTU_ID
          );
        }
        let index = this.tvVatTu.findIndex(
          (item) => item.VATTU_ID == data.VATTU_ID
        );
        this.$refs.Tree_tvVatTu.selectRow(index, true);
        this.$root.showLoading(false);
      } else {
        this.tvVatTu_Goc = this.tvVatTu;
      }
      // if (cboTimKiem.EditValue != null)
      //   {
      //       tvVatTu.FocusedNode = tvVatTu.Nodes.FirstNode;
      //       TreeListNode node = tvVatTu.FindNodeByFieldValue("VTID", Convert.ToInt64(cboTimKiem.EditValue));
      //       tvVatTu.FocusedNode = node;
      //   }
      //   else
      //   {
      //       tvVatTu.FocusedNode = null;
      //   }
    },
    async TaoDuLieu_VTDK(
      themmoi,
      dk_id,
      vt_id,
      loai,
      dvt_id,
      sl_ld,
      gc,
      ten_vt,
      vmd_id,
      sl_ton
    ) {
      // try {
      let vten_vt = "";
      let v_vtdk_id = 0;
      let vdk_id = 0;
      let v_vt_id = -1;
      let vloai = 0;
      let vdvitinh_id = 0;
      let v_sl = 0;
      let vgc = "";
      let vsl_ton = 0;
      let vtdk_id = 0;
      var vout_vtdk_id = 0;

      // API tích hợp getKeys, khi tạo gọi hàm getKey
      // if (themmoi){
      //   vtdk_id = await this.getKeys({
      //           "keyName" : "VTDK_ID",
      //           "numBlockSize" : 1,
      //           "numRetry":  10
      //         })
      // }

      ten_vt ? (vten_vt = ten_vt) : "";

      v_vtdk_id = vtdk_id;
      vdk_id = this.vdangky_luoi_id;
      vt_id ? (v_vt_id = vt_id) : "";

      vloai = loai;
      vdvitinh_id = dvt_id;
      v_sl = sl_ld;
      vgc = gc;

      vsl_ton = sl_ton;
      let sl_duyet = 0;

      if (this.params.p_trangthai.toString() == "2") {
        sl_duyet = v_sl;
        vtdk_id = await this.TaoVatTuDk({
          dangKyId: vdk_id,
          vatTuId: v_vt_id,
          tenVatTu: "",
          loai: vloai,
          ghichu: vgc,
          ngayCn: this.f_DateToString(new Date(), "DD/MM/YYYY hh:mm:ss"),
          nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
          mayCn: this.$auth.getUserName(), // ttnd.may_cn
          ipCn: "10.59.90.123", // ttnd.ip
          dviTinhId: vdvitinh_id,
          slTon: vsl_ton,
          soLuong: v_sl,
          mucDichId: vmd_id,
          soLuongDuyet: sl_duyet,
          kieuDkId: 1,
          trangThai: this.params.p_trangthai,
        });
        // API trả về getKey
        vout_vtdk_id = vtdk_id;
        return vout_vtdk_id;
      } else {
        vtdk_id = await this.TaoVatTuDk({
          dangKyId: vdk_id,
          vatTuId: v_vt_id,
          tenVatTu: "",
          loai: vloai,
          ghichu: vgc,
          ngayCn: this.f_DateToString(new Date(), "DD/MM/YYYY hh:mm:ss"),
          nguoiCn: this.$auth.getUserName(), //ttnd.ma_nd
          mayCn: this.$auth.getUserName(), // ttnd.may_cn
          ipCn: "10.59.90.123", // ttnd.ip
          dviTinhId: vdvitinh_id,
          slTon: vsl_ton,
          soLuong: v_sl,
          mucDichId: vmd_id,
          kieuDkId: 1,
          trangThai: this.params.p_trangthai,
        });
        // API trả về getKey
        vout_vtdk_id = vtdk_id;
        return vout_vtdk_id;
      }

      // } catch (error) {
      //   this.vout_vtdk_id = 0;
      //   return 0;
      // }
    },
    async tvVatTu_EditorKeyDown() {
      if (this.vdangky_luoi_id == 0 || this.vdangky_luoi_id == null) {
        this.$toast.error("CHưa chọn phiếu đăng ký không thể bổ sung");
        return;
      }
      var ds = await this.KiemTraTrangThaiDangKy({
        dangKyId: this.vdangky_luoi_id,
      });

      if (ds.toString() == "0") {
        this.$toast.error(
          "Trạng thái phiếu đăng ký đã bị thay đổi. Bạn không thể thêm vật tư"
        );
        return;
      }

      // try {
      let SL_LapDat = parseInt(this.tvVatTu_FocusedNode.SOLUONG);
      let SL_TonKho = parseInt(this.tvVatTu_FocusedNode.SL_TON);

      if (
        SL_LapDat != 0 &&
        this.tvVatTu_FocusedNode.DVITINH_ID != null &&
        this.tvVatTu_FocusedNode.DVITINH_ID != ""
      ) {
        if (this.tvVatTu_FocusedNode.DVITINH_ID.toString() == "15") {
          this.$toast.error(
            "Bạn không thể đăng ký vật tư này do đơn vị tính là không xác định"
          );
          // tvVatTu.FocusedNode.SetValue("SOLUONG", 0);
          // tvVatTu.FocusedNode.SetValue("GHICHU", "");
          this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach((index) => {
            this.tvVatTu[index].SOLUONG = 0;
            this.tvVatTu[index].GHICHU = "";
          });

          this.$refs.Tree_tvVatTu.refresh();
          this.$refs.Tree_tvVatTu.refreshColumns();
          return;
        }
        let dem = 0;
        let vt_id = parseInt(this.tvVatTu_FocusedNode.VATTU_ID);
        let dvt_id = parseInt(this.tvVatTu_FocusedNode.DVITINH_ID);
        let md_id;
        var md = this.params.p_MucDich;
        if (!md.toString()) {
          this.$toast.error("Bạn phải lựa chọn mục đích sử dụng của vật tư");
          this.$refs.cbo_MucDich.focusIn();
        }

        let ghichu = !this.tvVatTu_FocusedNode.GHICHU.toString() ? "Bổ sung vật tư" : "Bổ sung vật tư " + this.tvVatTu_FocusedNode.GHICHU.toString();
        if (this.params.p_trangthai.toString() == "2") {
          ghichu = !this.tvVatTu_FocusedNode.GHICHU.toString() ? "Duyệt thêm vật tư" : "Duyệt thêm vật tư " + this.tvVatTu_FocusedNode.GHICHU.toString();
        }
        let vtdk_id = 0;
        let dt_dsvtdk = await this.getThongTinDSVatTu({
          dangKyId: this.vdangky_luoi_id,
          vatTuId: vt_id,
          dvi_id: 0 || "",
          mucDichId: parseInt(md),
          phanVungId: this.$auth.getPhanVungID(),
        });

        if (dt_dsvtdk.length) {
          // Kiểm tra trong DS có vật tư đó chưa
          this.$toast.error(
            "Vật tư và mã mục đích bạn chọn đã có không thể đăng ký thêm"
          );
          return;
        } else {
          dem += await this.TaoDuLieu_VTDK(
            true,
            this.vdangky_luoi_id,
            vt_id,
            1,
            dvt_id,
            SL_LapDat,
            ghichu,
            "",
            parseInt(this.params.p_MucDich),
            SL_TonKho
          );
        }

        if (dem == 0) {
          this.$toast.error("Có lỗi trong quá trình cập nhật");
          await this.NAP_DS_VATTU_DK();

          this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach((index) => {
            this.tvVatTu[index].SOLUONG = 0;
            this.tvVatTu[index].GHICHU = "";
          });
          this.$refs.Tree_tvVatTu.refresh();
          this.$refs.Tree_tvVatTu.refreshColumns();
          return;
        } else {
          await this.NAP_DS_VATTU_DK();
          this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach((index) => {
            this.tvVatTu[index].SOLUONG = 0;
            this.tvVatTu[index].GHICHU = "";
          });
          this.$refs.Tree_tvVatTu.refresh();
          this.$refs.Tree_tvVatTu.refreshColumns();
          return;
        }
      } else {
        let loi = "";
        if (SL_LapDat == 0) {
          loi += "Số lượng đăng ký phải lớn hơn 0";
        }

        // tvVatTu.SetColumnError(col_SLLD, "Số lượng đăng ký phải lớn hơn 0 ");
        this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach(
          (index) =>
            (this.tvVatTu[index].col_SLLD = "Số lượng đăng ký phải lớn hơn 0")
        );

        if (
          this.tvVatTu_FocusedNode.DVITINH_ID == "" &&
          this.tvVatTu_FocusedNode.DVITINH_ID == null
        ) {
          // tvVatTu.SetColumnError(colDVT, " Đơn vị tính không có ");
          this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach(
            (index) =>
              (this.tvVatTu[index].colDVT = "Số lượng đăng ký phải lớn hơn 0")
          );
          loi += " Đơn vị tính của vật tư không có \r\n";
        }
        if (loi != "") {
          this.$toast.error(loi);
          // tvVatTu.FocusedNode.SetValue("SOLUONG", 0);
          // tvVatTu.FocusedNode.SetValue("GHICHU", "");
          this.$refs.Tree_tvVatTu.getSelectedRowIndexes().forEach((index) => {
            this.tvVatTu[index].SOLUONG = 0;
            this.tvVatTu[index].GHICHU = "";
          });

          this.$refs.Tree_tvVatTu.refresh();
          this.$refs.Tree_tvVatTu.refreshColumns();
        }
      }
    },
    // func ẩn hiện button theo dk
    grvDSVatTu_DaDK_CustomRowCellEdit() {
      // if (e.Column == gcDelete && e.RowHandle >= 0)
      // {
      //     if (grvDSVatTu_DaDK.GetRowCellValue(e.RowHandle, "KIEUDK_ID").ToString() != "0")
      //         e.RepositoryItem = btnDel;
      //     else
      //         e.RepositoryItem = emptyEditor;
      // }
    },
    async btnDel_ButtonClick(row) {
      // try {
      if (!this.vdangky_luoi_id) {
        this.$toast.error("Chưa chọn phiếu đăng ký không thể bổ sung");
        return;
      }
      var ds = await this.KiemTraTrangThaiDangKy({
        dangKyId: this.vdangky_luoi_id,
      });
      // if (!ds.length){
      //   this.$toast.error("Có lỗi khi lấy trạng thái của phiếu đăng ký")
      //   return
      // }
      if (ds.toString() == "0" || ds == null) {
        this.$toast.error(
          "Trạng thái phiếu đăng ký đã bị thay đổi. Bạn không thể thêm vật tư"
        );
        return;
      }
      var bw = row; //this.grcDSVatTu_DaDK_FocusedRow
      var a = bw.VTDK_ID.toString();
      ds = await this.SLChungTuDKTheoVT({ dangKyId: a });
      // if (!ds.length){
      //   this.$toast.error("Có lỗi khi lấy trạng thái của phiếu đăng ký")
      //   return
      // }
      if (ds.toString() != "0") {
        this.$toast.error("Vật tư đã có chứng từ chuyển bạn không thể xóa");
        return;
      }

      await this.$bvModal
        .msgBoxConfirm("Bạn có muốn xóa dữ liệu không ?", {
          okVariant: "success",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
          centered: true,
        })
        .then(async (value) => {
          if (value) {
            await this.XoaVatTuDK({
              dangKyId: this.vdangky_luoi_id,
              vtdkId: parseInt(a.toString()),
            });
            this.$toast.success("Xóa dữ liệu vật tư thành công !");
            await this.NAP_DS_VATTU_DK();
          }
        })
        .catch((err) => {
          // An error occurred
        });
      // } catch (error) {
      //   this.$toast.error("Không thể xóa ràng buộc")
      // }
    },
    async cboKhoGiao_EditValueChanged() {
      if (this.isFirstLoadKhoGiao) {
        this.isFirstLoadKhoGiao = false;
      } else {
        if (this.params.p_ckbFindAll) {
          await this.LAY_TON_VT(2);
        }
      }
    },
    btnMoRongCTHS_Click() {
      if (this.visible.splitContainerControl2 == "Both") {
        this.visible.splitContainerControl2 = "Pannel1";
      } else {
        this.visible.splitContainerControl2 = "Both";
      }
    },
    async btnNap_DSSC_Click() {
      await this.LAY_TON_VT(1);
    },
    async cboDVGIAO_EditValueChanged() {
      await this.NAP_DS_DK(0);
    },
    async cboKho_EditValueChanged() {
      try {
        this.loading(true);
        await this.LAY_TON_VT(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnTongHop_Click() {
      var vthang_th = new Date().getFullYear() * 100 + new Date().getMonth();
      var frm = this.$refs.frmDotTongHop;

      frm.params.Thang = new Date(vthang_th / 100, vthang_th % 100, 1);
      if (this.dsQuyen.includes(2504)) {
        frm.params.view_all_thdk = "1";
      }

      frm.params.tagFrom = "1";
      frm.params.dsDK = this.tagFrm;
      frm.params.ghiChu = "Số VB";
      if (this.params.p_DVGIAO.toString() != "-1") {
        frm.params.vdonvi_timkiem = this.params.p_DVGIAO.toString();
      }
      frm.Visible.Duyet = false;
      frm.Visible.huyDuyet = false;
      frm.Open();
    },
    async closeDotTongHop() {
      try {
        this.loading(true);
        await this.NAP_DS_DK(1);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    btnXuatExcel_DSTT_Click() {
      // this.ExportExcel(this.grvDSVatTu_DaDK)
    },
    async mniInThang_Click() {
      try {
        this.loading(true);
        let s = "";
        var dt = this.grcDSDangKy;
        var q = this.$refs.grvDSDangKy.getSelectedRecords();
        // quy thêm đk check có dữ liệu
        if (this.checkEmptyData(q)) {
          return;
        }
        s = q.map((b) => b.DANGKY_ID).join(",");

        this.$root.showLoading(true);
        let res = await this.xemBaoCao_TongHopTheoThang({
          dangKyId: s,
          nhanVienId: this.$auth.getNhanVienID(),
        });
        this.loading(false);
        res ? previewPrint(res.data) : "";
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    checkEmptyData(data) {
      if (data.length == 0) {
        this.$toast.error("Không có dữ liệu hiển thị báo cáo");
        return true;
      }
      return false;
    },
    async grvDSChungTu_FocusedRowChanged(row) {
      if (row) {
        this.grvDSChungTu_FocusedRow = row;
        this.gridChitietCT = await this.getDSVatTuChungTu({
          chungTuId: row.CHUNGTU_ID,
        });
        
      } else {
        this.gridChitietCT = [];
      }
    },
    async KiemTraHanChotCT() {
      let vkho_nhan_id = 0;
      let vkho_giao_id = 0;
      let ngay_ct = this.params.p_dtpNgayDK;

      vkho_giao_id = parseInt(this.grvDSChungTu_FocusedRow.KHO_GIAO_ID);
      vkho_nhan_id = parseInt(this.grvDSChungTu_FocusedRow.KHO_NHAN_ID);

      let s = await this.KiemTraHanChungTu({
        chungTuId: this.vchungtu_id,
        khoGiaoId: vkho_giao_id,
        khoNhanId: vkho_nhan_id,
        ngayCt: this.f_DateToString(ngay_ct, "DD/MM/YYYY"),
      });
      if (s.toString() != "1") {
        this.$toast.error("Đã quá hạn sửa chứng từ!");
        return false;
      }
      return true;
    },
    async KIEMTRA_QUYEN_XN(kieu, kho_id) {
      // try {
      //Nếu là 1 là kho giao
      var dt = await this.ThongTinXacQuyenTrenKho({
        nhanVienId: this.$auth.getNhanVienID(),
        khoId: kho_id,
      });
      if (!dt.length) {
        this.$toast.error(
          "Không lấy được thông tin quyền xác nhận trên kho " +
            this.grvDSChungTu_FocusedRow.KHO_GIAO
        );
        return false;
      }
      if (kieu == 1) {
        if (dt[0].quyenKg.toString() == "0") {
          this.$toast.error(
            "Bạn không có quyền xác nhận ngày gửi trên kho " +
              this.grvDSChungTu_FocusedRow.KHO_GIAO
          );
          return false;
        }
      }
      if (kieu == 2) {
        if (dt[0].quyenKg.toString() == "0") {
          this.$toast.error(
            "Bạn không có quyền xác nhận ngày nhận trên kho " +
              this.grvDSChungTu_FocusedRow.KHO_GIAO
          );
          return false;
        }
      }

      if (kieu == 3) {
        if (dt[0].quyenKg.toString() == "0") {
          this.$toast.error(
            "Bạn không có quyền xác nhận trên kho " +
              this.grvDSChungTu_FocusedRow.KHO_NHAN +
              ".Bạn không thể hoàn công."
          );
          return false;
        }
      }
      if (kieu == 4) {
        if (dt[0].quyenKg.toString() == "0") {
          this.$toast.error(
            "Bạn không có quyền tạo thiết bị trên kho " +
              this.grvDSChungTu_FocusedRow.KHO_GIAO +
              ".Bạn không thể hoàn công."
          );
          return false;
        }
      }
      if (kieu == 5) {
        if (dt[0].quyenKg.toString() == "0") {
          this.$toast.error(
            "Bạn không có quyền xác nhận trên kho " +
              this.grvDSChungTu_FocusedRow.KHO_GIAO +
              ".Bạn không thể xóa/sửa chứng từ."
          );
          return false;
        }
      }
      return true;

      // } catch (error) {
      //   this.$toast.error("Có lỗi khi kiểm tra quyền xác nhận")
      //   return false
      // }
    },
    async KIEMTRA_THAYDOI(vct_id, cap) {
      // try {
      var xttph = await this.ThongTinTrangThaiPhieu({ chungTuId: vct_id });
      if (!xttph.length) {
        this.$toast.error("Không lấy được thông tin trạng thái phiếu");
        return false;
      }

      if (xttph.length) {
        if (this.ttph_id != xttph[0].ttphId) {
          this.$toast.error(
            "Thông tin trạng thái phiếu đã bị thay đổi. Bạn hãy lấy lại thông tin"
          );
          return false;
        }
        return true;
      }
      return true;
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi lấy thông tin trạng thái phiếu")
      // }
    },
    async btnXoa_ButtonClick(data) {
      try {
        this.loading(true);
        this.grvDSChungTu_FocusedRow = data;
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO)) {
          this.$toast.error("Bạn không có quyền xóa chứng từ");
          return;
        }
        if (!(await this.KiemTraNguoiCN())) {
          this.$toast.error("Bạn không được xoá chứng từ của người khác!");
          return;
        }

        this.ttph_id = this.grvDSChungTu_FocusedRow.TTPH_ID;
        this.vchungtu_id = this.grvDSChungTu_FocusedRow.CHUNGTU_ID;

        if (!this.vchungtu_id) {
          this.$toast.error("Bạn phải chọn chứng từ để xóa");
          return;
        }
        if (!(await this.KiemTraHanChotCT())) {
          // Kiếm tra hạn chốt ok thì xóa
          return;
        }
        if (!(await this.KIEMTRA_THAYDOI(this.vchungtu_id, ""))) {
          return;
        }
        var kiemtra = await this.KiemTraXoaChungTu({
          chungTuId: this.vchungtu_id,
          vma_nd: this.$auth.getUserName(), //ttnd.ma_nd
        });
        console.log(kiemtra, " = kiemtra");
        if (kiemtra.toString() != "1") {
          this.$toast.error(kiemtra.toString());
          return;
        }
        if (
          !(await this.KIEMTRA_QUYEN_XN(
            5,
            this.grvDSChungTu_FocusedRow.KHO_GIAO_ID
          ))
        ) {
          return;
        }
        this.loading(false);
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có chắc chắn muốn xóa chứng từ " +
              this.grvDSChungTu_FocusedRow.SOPHIEU +
              "không!",
            {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            }
          });

        if (!isBoxConfirm) {
          return;
        }

        var f_rm = this.$refs.frmNhapText;
        await f_rm.Load_frmNhapText();
        f_rm.functionName = "XOA_CHUNGTU";
        await f_rm.frmNhapText_3("Bạn hãy lựa chọn kiểu xóa chứng từ", 1);
        f_rm.Text.title = "Danh sách kiểu xóa chứng từ";
        this.$refs.popup_frmNhapText.show();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async KiemTraNguoiCN() {
      return this.grvDSChungTu_FocusedRow.NGUOI_CN == this.$auth.getUserName(); //ttnd.ma_nd
    },
    async tsbtnHoanThanh_Click() {
      try {
        var ds = this.grcDSDangKy;
        if (ds.length == 0) {
          this.$toast.error("Không có danh sách đăng ký không thể hoàn thành");
          return;
        }
        var ds_chon = this.$refs.grvDSDangKy.getSelectedRecords();
        if (ds_chon.length == 0) {
          this.$toast.error("Bạn chưa chọn danh sách đăng ký để hoàn thành");
          return;
        }
        var ds_json = [];
        ds_chon.forEach((a) => ds_json.push({ DANGKY_ID: a.DANGKY_ID }));
        var json_list = JSON.stringify(ds_json);
        var dem = await this.SLPhieuDKThayDoi({
          listPhieu: ds_json,
          ttDkId: [1, 2],
        });

        if (!(dem.length == 0 || dem[0].SL == 0)) {
          this.$toast.error(
            "Danh sách có phiếu đăng ký bị thay đổi bạn hãy lấy lại thông tin"
          );
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn hoàn thành" +
              ds_json.length +
              " phiếu đăng ký đã chọn không?",
            {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }

        this.loading(true);
        var kq = await this.CapNhatDKTheoFile({
          listPhieu: ds_json,
          ttDkId: 4,
        });
        if (!isNaN(parseInt(kq))) {
          this.$toast.success("Hoàn thành " + kq + " phiếu thành công");
          await this.NAP_DS_DK(1);
        } else {
          this.$toast.error("Hoàn thành phiếu đăng ký thất bại " + kq);
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async cboXuLy_EditValueChanged() {
      this.visible.tsbtnHoanThanh = false;
      this.visible.tsbtnHuyTT = false;
      this.visible.tsbtnBoSungVT = true; // hiện lên mọi trạng thái xử lý

      if (this.params.p_trangthai.toString() == "1") {
        this.visible.tsbtnHoanThanh = true;
        if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
          this.visible.tsbtnBoSungVT = true;
        }
      } else if (this.params.p_trangthai.toString() == "2") {
        if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENXUAT_KHO)) {
          if (this.params.p_xuly.toString() != "4") {
            this.visible.tsbtnHoanThanh = true;
          } else {
            this.visible.tsbtnHuyTT = true;
          }
        }

        if (this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)) {
          if (
            this.params.p_xuly.toString() != "0" &&
            this.params.p_xuly.toString() != "4"
          ) {
            this.visible.tsbtnBoSungVT = true;
            if (this.params.p_trangthai.toString() == "2") {
              this.enable.grvDSVatTu_DaDK.LYDO_DUYET = false;
              this.enable.slDuyet = true;
              this.visible.xtraTabPage1 = true;
              this.visible.xtraTabPage2 = false;
            }
          } else {
            this.visible.xtraTabPage2 = true;
            this.visible.xtraTabPage1 = true;
            this.visible.slCL = true;
            this.enable.grvDSVatTu_DaDK.LYDO_DUYET = false;
            this.enable.slDuyet = false;
            // slCL.VisibleIndex = 7;
          }
        }
      }
      await this.NAP_DS_DK(0);
    },
    async ckbFindAll_CheckedChanged() {
      try {
        this.loading(true);
        this.enable.cboKhoGiao = this.params.p_ckbFindAll;
        await this.LAY_TON_VT(2);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    
    async inDanhSachToolStripMenuItem_Click() {
      try {
        this.loading(true);
        let s = "";
        let dt = this.grcDSDangKy;
        var q = this.$refs.grvDSDangKy.getSelectedRecords();
        // quy thêm đk check có dữ liệu
        if (this.checkEmptyData(q)) {
          return;
        }
        s = q.map((a) => a.DANGKY_ID).join(",");

        let res = await this.xemBaoCao_DSDuyetDK({
          dangKyId: s,
          nhanVienId: this.$auth.getNhanVienID(),
        });
        this.loading(false);
        res ? previewPrint(res.data) : "";
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async XuatDanhSachToolStripMenuItem_Click() {
      try {
        this.loading(true);
        let s = "";
        let dt = this.grcDSDangKy;
        var q = this.$refs.grvDSDangKy.getSelectedRecords();
        // quy thêm đk check có dữ liệu
        if (this.checkEmptyData(q)) {
          return;
        }
        s = q.map((a) => a.DANGKY_ID).join(",");

        let res = await this.XuatExcel_DSDuyetDK({
          dangKyId: s,
          nhanVienId: this.$auth.getNhanVienID(),
        });
        this.loading(false);
        console.log(res, " = res");
        const url = URL.createObjectURL(
          new Blob([res.data], {
            type: "application/vnd.ms-excel",
          })
        );
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", "PhieuDuyetDangKy");
        document.body.appendChild(link);
        link.click();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async txtMaGD_Properties_ButtonClick() {
      try {
        this.loading(true);
        this.ds_vattu = await this.getDSVatTuDaDK({});
        this.grcVattu = this.ds_vattu;
        this.cboDSVT = this.ds_vattu; // test
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    grvVattu_CellValueChanged() {
      let tt = "";
      tt = this.$refs.grvVattu
        .getSelectedRecords()
        .map((a) => a.TEN_VT)
        .join(",");
      this.Text.cboDSVT = tt;
    },
    TenTuKho(value) {
      let Kho = this.cboKhoGiao.filter((a) => a.KHO_ID == value);
      return Kho.length > 0 ? Kho[0].TEN_KHO : "";
    },
    async btnCuonCap_ButtonClick(data) {
      try {
        this.loading(true);
        var frm = this.$refs.frmGanSeri;
        await frm.frmGanSeri_2(); // frmGanSeri(1)
        frm.Text.title = "Chọn/Cắt cuộn cáp";
        if (this.params.p_ckbKhoGiao) {
          frm.vkho_id = parseInt(this.params.p_khogiao_TK);
          frm.params.p_txtTenKho = this.TenTuKho(this.params.p_khogiao_CTDK);
        } else {
          frm.vkho_id = parseInt(data.KHO_ID);
          frm.params.p_txtTenKho = data.KHO;
        }

        frm.vkho_nhan_id = parseInt(this.params.p_KhoDen);
        frm.soluong_tong = parseInt(data.SL_XUAT);
        frm.vattu_id = parseInt(data.VATTU_ID);
        frm.lohang = data.LOHANG;
        frm.soluong_ton = 0;
        frm.cat_temp = true;
        if (this.ds_cat_temp.length > 0) {
          frm.ds_dachon = this.ds_cat_temp;
        }
        this.loading(false);
        await frm.ShowDialog();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    XacNhan_frmGanSeri() {
      var frm = this.$refs.frmGanSeri;
      if (frm.xacnhan) {
        if (this.enable.tsbtnXacNhan) {
          this.$toast.error(
            "Bạn phải xác nhận số lượng xuất mới chọn cuộn được"
          );
          return;
        }
        if (this.ds_cat_temp.length == 0) {
          this.ds_cat_temp = frm.ds_cat_temp;
        } else {
          // Cuộn đã cắt thì lấy cuộn mới cắt
          let ds_temp = frm.ds_cat_temp;
          let VT_them = [];
          for (let e of this.ds_cat_temp) {
            let dsCheck = ds_temp.filter(
              (a) =>
                a.THIETBI_ID == e.THIETBI_ID &&
                a.MA_TBI == e.MA_TBI &&
                a.LOHANG == e.LOHANG &&
                a.KHO_ID == e.KHO_ID
            );
            if (dsCheck.length == 0) {
              VT_them.push(e);
            }
          }
          for (let e of VT_them) {
            ds_temp.push(e);
          }

          this.ds_cat_temp = ds_temp;
        }
      }
    },

    async tsbtnHuyTT_Click() {
      try {
        var ds = this.grcDSDangKy;
        if (!ds.length) {
          this.$toast.error(
            "Không có danh sách đăng ký không thể hủy trạng thái"
          );
          return;
        }
        var ds_chon = this.$refs.grvDSDangKy.getSelectedRecords();
        if (!ds_chon.length) {
          this.$toast.error(
            "Bạn chưa chọn danh sách đăng ký để hủy trạng thái"
          );
          return;
        }
        let data = [];
        for (let e of ds_chon) {
          data.push({
            DANGKY_ID: e.DANGKY_ID,
          });
        }
        var dem = await this.SLPhieuDKThayDoi({
          listPhieu: data,
          ttDkId: [4],
        });
        if (dem.length != 0 && dem[0].SL > 0) {
          this.$toast.error(
            "Danh sách có phiếu đăng ký bị thay đổi bạn hãy lấy lại thông tin"
          );
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có muốn hủy trang thái " +
              data.length +
              " phiếu đăng ký đã chọn không?",
            {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        this.loading(true);
        var kq = await this.CapNhatDKTheoFile({
          listPhieu: data,
          ttDkId: 2,
        });

        if (!isNaN(parseInt(kq))) {
          this.$toast.success(
            "Hủy trạng thái " + parseInt(kq).toString() + " phiếu thành công"
          );
          await this.NAP_DS_DK(1);
        } else {
          this.$toast.error("Hủy trạng thái phiếu đăng ký thất bại " + kq);
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async ckbKhoGiao_CheckedChanged() {
      try {
        this.loading(true);
        if (this.params.p_ckbKhoGiao) {
          this.visible.colKho = false;
          this.enable.cboKho = true;
        } else {
          this.visible.colKho = true;
          this.enable.cboKho = false;
        }
        await this.LAY_TON_VT(0);
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async ckbDatCho_CheckedChanged() {
      this.enable.spinSoNgay = this.params.p_ckbDatCho;
      this.Text.tsbtnNhapKho = this.params.p_ckbDatCho
        ? "Đặt chỗ"
        : "Chuyển kho";
    },
    async ckbGiuCho_CheckedChanged() {
      if (this.params.p_ckbGiuCho) {
        this.enable.spNgayGiu = true;
        this.enable.cboKhoDatDen = true;
      } else {
        this.enable.spNgayGiu = false;
        this.enable.cboKhoDatDen = false;
      }
      await this.LAY_TON_VT(2);
    },
    async KIEMTRA_DATCHO(dt) {
      if (this.params.p_ckbGiuCho) {
        if (this.params.p_spinSoNgay.toString() == "0") {
          this.$toast.error("Ngày đặt giữ chỗ phải khác 0");
          return false;
        }
      }
      if (this.params.p_ckbFindAll) {
        if (!this.params.p_KhoDatDen) {
          this.$toast.error("Bạn chưa chọn kho đến");
          return false;
        }
        if (!this.params.p_khogiao_CTDK) {
          this.$toast.error("Bạn chưa chọn kho giao");
          return false;
        }

        if (
          this.params.p_khogiao_CTDK.toString() ==
          this.params.p_KhoDatDen.toString()
        ) {
          this.$toast.error("Hai kho từ và đến không thể giống nhau");
          return false;
        }
      } else {
        if (!this.params.p_KhoDatDen) {
          this.$toast.error("Bạn chưa chọn kho đến");
          return false;
        }
        if (!dt.length) {
          this.$toast.error("Không lấy được thông tin nhập kho");
          return false;
        }

        if (
          dt.filter(
            (a) => a.KHO_ID.toString() == this.params.p_KhoDatDen.toString()
          ).length > 0
        ) {
          this.$toast.error("Hai kho từ và đến không thể giống nhau");
          return false;
        }
      }
      return true;
    },
    async DAT_CHO() {
      // try {
      if (!this.params.p_ckbGiuCho) {
        this.$toast.error("Bạn không thể đặt chỗ nếu không chọn ngày ");
        return;
      }
      var ds = this.grcTonKho;
      if (!ds.length) {
        this.$toast.error("Không có dữ liêu vật tư để đặt chỗ");
        return;
      }
      var t = this.$refs.grvTonKho.getSelectedRecords();
      if (!t.length) {
        this.$toast.error("Không có dữ liêu vật tư để đặt chỗ");
        return;
      }

      if (!(await this.KIEMTRA_DATCHO(t))) {
        return;
      }

      await this.$bvModal
        .msgBoxConfirm(
          "Bạn chắc chắn muốn đặt chỗ xuất vào kho " +
            this.cboKhoDatDen.find((a) => a.khoId == this.params.p_KhoDatDen)
              .tenKho +
            " không ?",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        )
        .then(async (value) => {
          if (value) {
            var f_rm = this.$refs.frmNhapText;
            await f_rm.Load_frmNhapText();
            await f_rm.frmNhapText("Nhập nội dung chứng từ");
            f_rm.Text.title = "Nhập nội dung chứng từ";
            f_rm.functionName = "DAT_CHO";
            f_rm.t = t; // checkbox bị reset khi open popup
            this.$refs.popup_frmNhapText.show();
            // XacNhan_frmNhapText
          }
        })
        .catch((err) => {
          // An error occurred
        });
      // } catch (error) {
      //   this.$toast.error("Có lỗi khi đặt chỗ ")
      //   return
      // }
    },
    async tsbtnDatCho_Click() {
      await this.DAT_CHO();
    },
    grvTonKho_CellValueChanged(args) {
      let changes = this.$refs.grvTonKho.getBatchChanges();
      this.$refs.grvTonKho.batchUpdate(changes);
    },
    async cboKieuTH_EditValueChanged() {
      console.log(this.params.p_KieuTH, " = p_KieuTH");
      this.isShowDatCho = this.params.p_KieuTH == 1;
      await this.NAP_DS_CHUNGTU();
    },
    async NAP_DS_CHUNGTU() {
      if (!this.vdangky_luoi_id || !this.params.p_KieuTH) {
        return;
      }
      if (this.params.p_KieuTH.toString() == "1") {
        this.grcDSChungTu = [];
        this.grcDSChungTu = await this.getDSChungTu({
          kieu: 1,
          dangKyId: this.vdangky_luoi_id,
          loaiCtId: this.params.p_KieuTH,
        });
        // ds ? this.grcDSChungTu = ds.data : ""
      } else {
        this.grcDSChungTu = [];
        this.grcDSChungTu = await this.getDSChungTu({
          kieu: 2,
          dangKyId: this.vdangky_luoi_id,
          loaiCtId: this.params.p_KieuTH,
        });
        // ds ? this.grcDSChungTu = ds.data : ""
      }
    },
    vbtnResize_MouseClick() {},
    async importFileToolStripMenuItem_Click(dtDulieu) {
      await this.DATCHO_EXCEL(dtDulieu);
    },
    async DatChoTheoFile_Click() {
      if (!this.params.p_ckbGiuCho) {
        this.$toast.error("Bạn không thể đặt chỗ nếu không chọn ngày ");
        return;
      }
      // this.$parent.$parent.$parent.$refs.fileUpload.$el.click()
      document.getElementById("fileUpload").click();
      // await this.$parent.$parent.$parent.importFileToolStripMenuItem_Click(this.data)
    },
    async KIEMTRA_DATCHO_EXCEL(dt) {
      if (this.params.p_ckbGiuCho) {
        if (this.params.p_spinSoNgay.toString() == "0") {
          this.$toast.error("Ngày đặt giữ chỗ phải khác 0");
          return false;
        }
      }
      if (!this.params.p_KhoDatDen.toString()) {
        this.$toast.error("Bạn chưa chọn kho đến");
        return false;
      }
      if (!dt.length) {
        this.$toast.error("Không lấy được thông tin nhập kho");
        return false;
      }
      if (
        dt.filter(
          (a) => a.KHO_ID.toString() == this.params.p_KhoDatDen.toString()
        ).length
      ) {
        this.$toast.error("Hai kho từ và đến không thể giống nhau");
        return false;
      }
      return true;
    },
    async DATCHO_EXCEL(dtDulieu) {
      try {
        // if (!this.params.p_ckbGiuCho){
        //   this.$toast.error("Bạn không thể đặt chỗ nếu không chọn ngày ")
        //   return
        // }
        let dtDulieu_Columns = Object.keys(dtDulieu[0]) || [];
        if (this.dat_cho_trung_row) {
          if (
            !dtDulieu_Columns.includes("MA_KHO") ||
            !dtDulieu_Columns.includes("MA_VT") ||
            !dtDulieu_Columns.includes("LOHANG") ||
            !dtDulieu_Columns.includes("SL_DAT") ||
            !dtDulieu_Columns.includes("IDN")
          ) {
            this.$toast.error(
              "Trong file chưa đủ cột IDN, MA_KHO, MA_VT,LOHANG,SL_DAT"
            );
            return;
          }
        } else {
          if (
            !dtDulieu_Columns.includes("MA_KHO") ||
            !dtDulieu_Columns.includes("MA_VT") ||
            !dtDulieu_Columns.includes("LOHANG") ||
            !dtDulieu_Columns.includes("SL_DAT")
          ) {
            this.$toast.error(
              "Trong file chưa đủ cột MA_KHO, MA_VT,LOHANG,SL_DAT"
            );
            return;
          }
        }
        var json_ds = JSON.stringify(dtDulieu);
        var t = await this.getDSDatCho({
          listPhieu: dtDulieu, //json_ds
          checkRow: false, // them cho zui
          kieu: 1,
        });
        var z = await this.getDSDatCho({
          listPhieu: dtDulieu, //json_ds
          checkRow: this.dat_cho_trung_row, // them cho zui
          kieu: this.dat_cho_trung_row ? 2 : 3,
        });

        if (z.length) {
          let idn_error = this.dat_cho_trung_row
            ? "IDN: " + z[0]["IDN"].toString()
            : "Lo hang: " + z[0]["LOHANG"].toString();
          this.$toast.error(
            "Danh sách vật tư lô hàng và kho bị trùng. Bạn hãy kiểm tra lại. " +
              idn_error
          );
          return;
        }

        if (!(await this.KIEMTRA_DATCHO_EXCEL(t))) {
          return;
        }

        await this.$bvModal
          .msgBoxConfirm(
            "Bạn chắc chắn muốn đặt chỗ xuất vào kho " +
              this.cboKhoDatDen.find((a) => a.khoId == this.params.p_KhoDatDen)
                .tenKho +
              " không ?",
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          )
          .then(async (value) => {
            if (value) {
              var f_rm = this.$refs.frmNhapText;
              await f_rm.Load_frmNhapText();
              await f_rm.frmNhapText("Nhập nội dung chứng từ");
              f_rm.Text.title = "Nhập nội dung chứng từ";
              f_rm.functionName = "DATCHO_EXCEL";
              f_rm.t = t;
              f_rm.z = z;
              this.DSDatCho_Excel = dtDulieu;
              this.$refs.popup_frmNhapText.show();
            }
          })
          .catch((err) => {
            // An error occurred
          });

      } catch (error) {
        this.$toast.error(error.data.message)
      }finally{
        this.loading(false)
      }
    },
    async tsbtnExcel_Click() {
      var dt = this.grcDSDangKy;
      if (dt.length == 0) {
        this.$toast.error("Không có danh sách để xuất excel !");
        return;
      }
      await this.SaveXlsAndOpenFile(
        "DuyetVT - " +
          this.cboXuLy.find((a) => a.TRANGTHAI_ID == this.params.p_xuly)
            .TRANGTHAI_XL +
          ".xlsx",
        "sheet1",
        dt
      );
    },
    async btnXoa_VT_ButtonClick(data) {
      try{  
        if (!this.dsQuyen.includes(this.DS_QUYEN_VT.CAPNHAT_CHUNGTU)) {
          this.$toast.error("Bạn không có quyền thực hiện chức năng này!");
          return;
        }
        this.vchungtu_id = parseInt(this.grvDSChungTu_FocusedRow.CHUNGTU_ID);
        this.ttph_id = parseInt(this.grvDSChungTu_FocusedRow.TTPH_ID);

        if (this.kiemtra_quyen) {
          var ds_dt = await this.getNgayNhanThanhToan({
            chungTuId: this.vchungtu_id,
          });
          if (!ds_dt.length) {
            this.$toast.error("Không có dữ liệu ngày nhận không thể xóa");
            return;
          } else {
            if (ds_dt[0] != null && ds_dt[0] != "") {
              this.$toast.error(
                "Đã có dữ liệu ngày nhận kho bạn không thể xóa thiết bị"
              );
              return;
            }
          }
          if (
            !(await this.KIEMTRA_QUYEN_XN(
              5,
              parseInt(this.grvDSChungTu_FocusedRow.KHO_GIAO_ID)
            ))
          ) {
            return;
          }
        }
        if (!(await this.KiemTraNguoiCN())) {
          this.$toast.error("Bạn không được xoá chứng từ của người khác!");
          return;
        }
        if (!(await this.KiemTraHanChotCT())) {
          return;
        }


        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có chắc chán muốn xóa vật tư của chứng từ này!",
            {
              title: "Xác nhận hành động",
              centered: true,
              size: "md",
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy',
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            } else {
              return;
            }
          });

        if (!isBoxConfirm) {
          return
        }
        this.loading(true)
        if (this.ttph_id.toString() == "4") {
          this.$toast.error(
            "Trạng thái phiếu đã hoàn thành bạn không được quyền xoá"
          );
          return;
        }
        console.log(data , " = data ")   
        let k = await this.XoaChungTuBaoHanh({
          kieu: 2,
          chungTuId: this.vchungtu_id,
          chungTuCtId: data.CTCT_ID,
          thietBiId: 0,
        });
        if (k.toString() != "1") {
          this.$toast.error(k);
          return;
        }

        var kq = await this.XoaChiTietChungTu({ ctctId: data.CTCT_ID });
          if (kq.toString() != "1") {
            this.$toast.error(kq);           
          }

        await this.NAP_DS_CHUNGTU();
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false)
      }
    },

    async btnDowload_ButtonClick(value) {
      if(value) {           
        var checkFile = await this.checkFileExist({fileSource : value.colFile})        
        var linkFile = ""
        if(checkFile.message == "Success" && checkFile.data.data){
          let getLink = await this.getLinkFile({fileSource : value.colFile}) 
          linkFile = getLink.data.name                 
        }else{  
          linkFile  = "http://10.70.52.164:8081/FILES/" + this.$auth.getMaTinh() + "/VATTU/" + value.colFile     
        }
        const downloadLink = document.createElement("a");      
        downloadLink.href = linkFile;       
        const fileName = value.colFileName;
        downloadLink.download = fileName;    
        downloadLink.target = "_blank";
        downloadLink.click();   
      }
    },    
    async tsbtnInPhieuduyetDK_Click() {
      try {
        this.loading(true);
        let s = "";
        let dt = this.grcDSDangKy;
        var q = this.$refs.grvDSDangKy.getSelectedRecords();
        // quy thêm đk check có dữ liệu
        if (this.checkEmptyData(q)) {
          return;
        }
        s = q.map((a) => a.DANGKY_ID).join(",");
        let res = await this.xemBaoCao_PDDK_NET({ dsDangKy: s });
        this.loading(false);
        res ? previewPrint(res.data) : "";
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    repositoryItemMemoEdit1_DoubleClick() {},
    repositoryItemMemoEdit2_DoubleClick() {},
    // inPhiếuXuấtDuyệtĐăngKýToolStripMenuItem_Click
    async inPhieuXuatDuyetDangKyToolStripMenuItem_Click() {
      try {
        this.loading(true);
        let s = "";
        let dt = this.grcDSDangKy;
        var q = this.$refs.grvDSDangKy.getSelectedRecords();
        // quy thêm đk check có dữ liệu
        if (this.checkEmptyData(q)) {
          return;
        }
        s = q.map((a) => a.DANGKY_ID).join(",");

        let res = await this.xemBaoCao_PXDDK({ dangKyId: s });
        this.loading(false);
        res ? previewPrint(res.data) : "";
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async edit_grcDSVatTu_DaDK(arg) {
      try{
        if (
          this.params.p_trangthai == "2" &&
          !this.dsQuyen.includes(this.DS_QUYEN_VT.QUYENDUYET_VT)
        ) {
          this.$toast.error("Bạn không có quyền duyệt vật tư");
          return;
        }
        let changes = this.$refs.grvDSVatTu_DaDK.getBatchChanges();

        if (changes.changedRecords.length == 0) {
          return;
        }
        if (arg.columnName == "SL_DUYET") {
          if (parseInt(changes.changedRecords[0].SL_DUYET) < 0) {
            changes.changedRecords[0].SL_DUYET = 0;
            this.rowSelectedCT = this.removeElementInArray(
              this.rowSelectedCT,
              changes.changedRecords[0].VTDK_ID
            );
          } else {
            if (!this.rowSelectedCT.includes(changes.changedRecords[0].VTDK_ID)) {
              this.rowSelectedCT.push(changes.changedRecords[0].VTDK_ID);
            }
          }
        }

        if (arg.columnName == "GHICHU") {
          let tenvt = changes.changedRecords[0].TEN_VT;
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm(
              "Bạn có muốn cập nhật ghi chú cho vật tư: " + tenvt + " không ?",
              {
                title: "Thông báo",
                centered: true,
                size: "md",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
          this.loading(true);
          let ketQua = await this.CapNhatVatTuDK({
            ghiChu: changes.changedRecords[0].GHICHU,
            vatTuDkId: changes.changedRecords[0].VTDK_ID,
            dangKyId: changes.changedRecords[0].DANGKY_ID,
            vatTuId: changes.changedRecords[0].VATTU_ID,
          });
          await this.NAP_DS_THONGTIN();
        }

        if (arg.columnName == "LYDO_DUYET") {
          let tenvt = changes.changedRecords[0].TEN_VT;
          var isBoxConfirm = false;
          await this.$bvModal
            .msgBoxConfirm(
              "Bạn có muốn cập nhật lý do duyệt cho vật tư: " + tenvt + " không ?",
              {
                title: "Thông báo",
                centered: true,
                size: "md",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
              }
            )
            .then((value) => {
              if (value) {
                isBoxConfirm = true;
              } else {
                return;
              }
            });

          if (!isBoxConfirm) {
            return;
          }
          this.loading(true);
          let ketQua = await this.capNhatLyDoDuyet({
            ghiChu: changes.changedRecords[0].LYDO_DUYET,
            vatTuDkId: changes.changedRecords[0].VTDK_ID,
            dangKyId: changes.changedRecords[0].DANGKY_ID,
            vatTuId: changes.changedRecords[0].VATTU_ID,
          });
          await this.NAP_DS_THONGTIN();
        }
        this.$refs.grvDSVatTu_DaDK.batchUpdate(changes);
        this.$refs.grvDSVatTu_DaDK.grid.refreshColumns();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    removeElementInArray(array, element) {
      const index = array.indexOf(element);
      if (index > -1) {
        array.splice(index, 1);
      }
      return array;
    },
    async SaveXlsAndOpenFile(fileName, sheetName, exportData) {
      if (!exportData.length) {
        this.$toast.error("Không có bản ghi nào để xuất!");
        return;
      }
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName);
    },
    async btnDoiMucDich_Click() {
      try {
        if (this.grcDSVatTu_DaDK.length == 0) {
          this.$toast.error("Không có danh sách chi tiết đăng ký");
          return;
        }

        var chon_ischeck = this.grcDSVatTu_DaDK.filter((a) =>
          this.rowSelectedCT.includes(a.VTDK_ID)
        );
        if (chon_ischeck.length == 0) {
          this.$toast.error("Bạn chưa chọn vật tư cần đổi mục đích !");
          return;
        }

        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm(
            "Bạn có thực sự muốn đổi " +
              chon_ischeck.length +
              " vật tư sang mục đích [" +
              this.cbo_MucDich.find((a) => a.mucDichId == this.params.p_MucDich)
                .mucDich +
              "] không ?",
            {
              title: "Thông báo",
              centered: true,
              size: "md",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then((value) => {
            if (value) {
              isBoxConfirm = true;
            }
          });

        if (!isBoxConfirm) {
          return;
        }
        var js = chon_ischeck.map((item) => item.VTDK_ID).join(",");

        var thuchien = await this.CapNhatDoiMucDich({
          mucDichId: this.params.p_MucDich,
          dangKyId: this.vdangky_luoi_id,
          vtDkId: js,
        });
        this.$toast.success("Cập nhật thành công !");
        await this.NAP_DS_VATTU_DK();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async btnGiaHanGiuCho_Click() {
      // try {
      if (this.grcDSChungTu.length == 0) {
        this.$toast.error("Không có danh sách chứng từ đặt chỗ !");
        return;
      }
      if (Object.keys(this.grvDSChungTu_FocusedRow).length == 0) {
        this.$toast.error("Bạn chưa chọn chứng từ nào !");
        return;
      }
      if (this.grvDSChungTu_FocusedRow["TRANGTHAI"] == "HT") {
        this.$toast.error(
          "Chứng từ đã hoàn thiện, không thể thay đổi ngày đặt chỗ !"
        );
        return;
      }
      await this.$bvModal
        .msgBoxConfirm(
          "Bạn có muốn gia hạn đặt chỗ cho chứng từ" +
            this.grvDSChungTu_FocusedRow["SOPHIEU"] +
            "?",
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        )
        .then(async (value) => {
          if (value) {
            var f_rm = this.$refs.frmNhapText;
            await f_rm.Load_frmNhapText();
            await f_rm.frmNhapText_2("", "Ngày hết hạn");
            f_rm.Text.title = "Gia hạn ngày đặt chỗ";
            f_rm.params.txtNhapText = "Chỗ này để mặc định không cần nhập !";
            f_rm.params.dtpNgay = new Date();
            f_rm.functionName = "GIAHAN";

            this.$refs.popup_frmNhapText.show();
            // XacNhan_frmNhapText
          }
        })
        .catch((err) => {
          // An error occurred
        });
      // } catch (error) {
      //   this.$toast.error("GH: " + error)
      // }
    },
    async edit_tvVatTu(args) {
      var changes = this.$refs.Tree_tvVatTu.getBatchChanges();
      if (
        changes.hasOwnProperty("changedRecords") &&
        changes["changedRecords"].length
      ) {
        var changedRecords = changes.changedRecords;
        this.$refs.Tree_tvVatTu.updateRow(
          changedRecords[0].index,
          changedRecords[0]
        );
        this.tvVatTu_FocusedNode = changedRecords[0];
        await this.tvVatTu_EditorKeyDown();
      }
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!");
        return false;
      }
      if (
        this.arrayImportFile.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!");
        return false;
      }
      return true;
    },
    async onChange() {
      this.arrayImportFile.filexcel =
        this.$refs.fileUpload.$refs.input.files[0];

      let json_list = [];
      try {
        this.$root.showLoading(true);
        if (this.checkfile()) {
          let tencot;
          const reader = new FileReader();
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result;
            const wb = XLSX.read(bstr, { type: "binary" });
            /* Get first worksheet */
            const wsname = wb.SheetNames[0];
            const ws = wb.Sheets[wsname];
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
            // retrieve headers (i.e. remove first row)
            let headers = data.shift(); // bỏ dòng header đầu tiên trong data

            // then build the json for each row
            var result = data.map(function (a) {
              var jsonRow = {};
              a.forEach(function (cellValue, cellIndex) {
                jsonRow[headers[cellIndex]] = cellValue;
              });
              return jsonRow;
            });
           
            await this.importFileToolStripMenuItem_Click(result);
          };
          reader.readAsBinaryString(this.arrayImportFile.filexcel);
        }
      } catch (error) {
        this.$toast.error(e.data.message);
        return;
      } finally {
        this.$refs.fileUpload.reset();
        this.$root.showLoading(false);
      }
      this.arrayImportFile.filexcel = null;
    },
    gridViewVatTu_CellValueChanged(arg) {
      let changes = this.$refs.gridViewVatTu.getBatchChanges();
      if (changes.changedRecords.length == 0) {
        return;
      }
      if (arg.columnName == "SL_XUAT") {
        if (parseInt(changes.changedRecords[0].SL_XUAT) <= 0) {
          changes.changedRecords[0].SL_XUAT = 0;
          this.rowSelectedVT = this.removeElementInArray(
            this.rowSelectedVT,
            changes.changedRecords[0].STT
          );
        } else {
          if (!this.rowSelectedVT.includes(changes.changedRecords[0].STT)) {
            this.rowSelectedVT.push(changes.changedRecords[0].STT);
          }
        }
      }
      this.$refs.gridViewVatTu.batchUpdate(changes);
      this.$refs.gridViewVatTu.grid.refreshColumns();
    },
  },
  created: async function () {
    try {
      this.loading(true);
      this.allowEditGhiChu = this.$auth.getMaTinh() != "HCM" 
      this.Tag = this.$route.query.Tag || "1";
      this.tagFrm = this.Tag.toString();
      // this.clearData();
      await this.frmDuyetVT_DK_Load();
      await this.frmDuyetVT_DK_Shown();
      await this.cboXuLy_EditValueChanged();
    } catch (e) {
      this.$toast.error(e.data.message);
    } finally {
      this.loading(false);
    }
  },
  watch: {
    rowSelectedCT: function (value) {
      this.rowSelectedCT = value;
      for (let e of this.grcDSVatTu_DaDK) {
        if (this.rowSelectedCT.includes(e.VTDK_ID) && e.SL_DUYET == 0) {
          e.SL_DUYET = parseInt(e.SOLUONG);
        }
        // else if(!this.rowSelectedCT.includes(e.VTDK_ID)){
        //   e.SL_DUYET = 0
        // }
      }
      this.$refs.grvDSVatTu_DaDK.grid.refreshColumns();
    },
    checkAllCT: function (value) {
      if (value) {
        for (let e of this.grcDSVatTu_DaDK) {
          e.SL_DUYET = parseInt(e.SOLUONG);
          this.rowSelectedCT.push(e.VTDK_ID);
        }
      } else {
        this.rowSelectedCT = [];
        for (let e of this.grcDSVatTu_DaDK) {
          e.SL_DUYET = 0;
        }
      }
      this.$refs.grvDSVatTu_DaDK.grid.refreshColumns();
    },

    rowSelectedVT: function (value) {
      this.rowSelectedVT = value;
    },
    checkAllVT: function (value) {
      this.rowSelectedVT = [];
      if (value) {
        for (let e of this.$refs.gridViewVatTu.filteredDataSource) {
          this.rowSelectedVT.push(e.STT);
        }
      }
    },
  },
  destroyed() {},
};
</script>
<style>
  .disabled a {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
  }

  .disabled a:hover {
    cursor: default;
    color: #6c757d;
  }

  a.disabled {
    color: lightgrey !important;
    pointer-events: none;
    cursor: default;
    text-decoration: none;
  }

  .list-actions-top .list {
    overflow: visible !important;
  }

  .vattu_box {
    position: relative;
  }

  .vattu_table {
    position: absolute;
    z-index: 1;
    max-height: 500px;
    background: aliceblue;
    overflow-y: scroll;
    right: 0;
  }
  /* #tab1 .e-grid .e-content {
      max-height: 500px !important;
  } */
</style>