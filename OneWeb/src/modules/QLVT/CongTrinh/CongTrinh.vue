<template src="./CongTrinh.html"></template>

<style src="./CongTrinh.scss">
</style>
<script>
import API from "../api/CongTrinh.js";
import Calculator from "@/components/Calculator";
import frmNhapText from "../frmNhapText";
import moment from "moment";
import Vue from "vue";
import { Query } from "@syncfusion/ej2-data";
import { currency } from "@/filters/currency";
import {
  GridPlugin,
  Page,
  Toolbar,
  Edit,
  CommandColumn,
} from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);
export default {
  name: "frmCongTrinh",
  components: {
    Calculator: Calculator,
    frmNhapText,
  },
  provide: {
    grid: [Page, Edit, Toolbar, CommandColumn],
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      interval: "",
      Tag: "",
      gridFileVBColumn: [
        {
          fieldName: "colFileName",
          headerText: "",
          allowFiltering: true,
          allowEditing: true,
          width: 130,
        },
        {
          fieldName: "",
          headerText: "",
          allowFiltering: false,
          width: 30,
          cellCssClass: "css1",
          textAlign: "center",
          commands: [
            {
              type: "Edit",
              buttonOption: {
                iconCss: "fa fa-download",
              },
            },
          ],
        },
        {
          fieldName: "",
          headerText: "",
          allowFiltering: false,
          width: 30,
          cellCssClass: "css1",
          textAlign: "center",
          commands: [
            {
              type: "DELETE",
              buttonOption: {
                iconCss: "yellow fa fa-remove",
              },
            },
          ],
        },
      ],
      gridDsFileColumn: [
        {
          fieldName: "",
          headerText: "",
          allowFiltering: false,
          width: 40,
          textAlign: "center",
          commands: [
            {
              type: "Delete",
              buttonOption: {
                iconCss: "fa fa-remove",
              },
            },
          ],
        },
        {
          fieldName: "NGAY_VB",
          headerText: "Ngày VB",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "SO_VB",
          headerText: "Số CV",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LOAI_VB",
          headerText: "Loại VB",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "NGAY_TT",
          headerText: "Ngày TT",
          allowFiltering: true,
          allowSorting: true,
        },
        {
          fieldName: "LYDO_TH",
          headerText: "Lý do TH",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      frmNoiDungTT: {
        title: "",
      },
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
        THUKHO_TAISANBANGIAO: 2518,
        QUYEN_XOA_KYSO: 3366,
      },
      gParams: {
        txtMaCT_TK: "",
        ckbTuNgay: false,
        dtpTuNgay: new Date(),
        ckbDenNgay: false,
        dtpDenNgay: new Date(),
        txtLocDT: "",
        //TTCT
        txtMa_CT: "",
        dtpNgayTao: new Date(),
        txtMaCT_Cha: "",
        txtTen_CT: "",
        calGT_DT: 0,
        calGT_QT: 0,
        txtDiaChi_TC: "",
        cboDvi_QL: "",
        cboDvi_TC: "",
        cboDVi_PD: "",
        txtNoiDung: "",
        txtSoHieuCT: "",
        txtCS_DT: "",
        dtpNgayNT: new Date(),
        dtpNgayHT: new Date(),
        cboLoaiCT: "",
        cboNguonVon: "",
        cboTrangThai: 1,
        cboLoaiSoHieu: "",
        cboLyDoTC: "",
        cboChungLoai: "",
        txtHienTrang: "",
        txtBienPhap: "",
        cboNhomCT: "",
        cboDuAnIMS: "",
        cboNV_DD: "",
        cboHeThong: "",
        cboNV_KT: "",
        cboNV_TK: "",
        //end TTCT
        quyen: 0, // 0 la quyền của thêm công trình
        vcongtrinh_id: "",
        dsQuyen: [],
        tvDoiTuongSelected: "",
        grvDSCongTrinhRowChanged: "",
        vproject_id: "",
        vproject_code: "",
        lydo_tc: [],
        dt_nv: [],
        cboLoaiFile: "",
        dtpNgayVB: new Date(),
        dtpNgayTT: new Date(),
        txtSoCV: "",
        txtLyDoTH: "",
        txtGhiCHu: "",
        load_first: true,
        dr: "",
        dt_all: [],
        vfile_ct: "",
        s: "",
        grvDSFileRowChanged: "",
        grvFileVBRowChanged: "",
        result: "",
        vnoidung: "",
      },
      cboDvi_QL: [],
      cboDvi_TC: [],
      cboDVi_PD: [],
      cboNhomCT: [],
      cboLoaiCT: [],
      cboNguonVon: [],
      cboTrangThai: [
        {
          TRANGTHAI_CT_ID: 1,
          TRANGTHAI_CT: "Mới",
        },
      ],
      cboDuAnIMS: [],
      cboLoaiSoHieu: [],
      cboLyDoTC: [],
      cboChungLoai: [],
      cboNV_DD: [],
      cboHeThong: [],
      cboNV_KT: [],
      cboNV_TK: [],
      cboLoaiFile: [],
      //grid:
      grvDSCongTrinh: [],
      grvCTVT: [],
      grvDSCTDG: [],
      grvDSCTCG: [],
      grvDSFile: [],
      grvFileVB: [],
      //end grid
      enable: {
        isCalculatorDTShow: false,
        isCalculatorQTShow: false,
        tsbtnThemFile: true,
        layoutGroupTTCT: true,
        btnXoaPhieu: true,
        btnNhapMoi: true,
        btnFilePath: true,
        btnThemFile: true,
      },
      visible: {
        layoutDSCT: false,
        tabDSCongTrinh: true,
        tabDSCTGan: true,
        btnNhapMoi: true,
        btnCapNhat: false,
        btnHuyBo: false,
        btnXoaPhieu: false,
        tsbtnThemFile: false,
        tsbtnAnFile: true,
      },
      text: {
        btnThemFile: "Cập nhật",
      },
      tabActived: {
        currentTab: 0,
        tabDSCongTrinh: 0,
        tabCTVT: 1,
        tabDSCTGan: 2,
      },
      tvDSCT: [],
      tvDSCT1: [
        { CONGTRINH_ID: 1, TEN_HT: "Discover Music" },
        { CONGTRINH_ID: 2, CONGTRINH_CHA_ID: 1, TEN_HT: "Hot Singles" },
        { CONGTRINH_ID: 3, CONGTRINH_CHA_ID: 1, TEN_HT: "Rising Artists" },
        { CONGTRINH_ID: 4, CONGTRINH_CHA_ID: 1, TEN_HT: "Live Music" },
        { CONGTRINH_ID: 6, CONGTRINH_CHA_ID: 1, TEN_HT: "Best of 2017 So Far" },
        { CONGTRINH_ID: 7, TEN_HT: "Sales and Events" },
      ],
      customTemplate: function () {
        return {
          template: Vue.component("footerTemplate", {
            template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
            data() {
              return { data: { data: {} } };
            },
          }),
        };
      },
    };
  },
  computed: {
    PTAggregatesColumns() {
      return [
        {
          field: "TIEN",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTIEN,
        },
        {
          field: "TIEN_DT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTIEN_DT,
        },
        {
          field: "TIEN_QT",
          type: "Custom",
          footerTemplate: this.customTemplate,
          customAggregateFn: this.getTotalTIEN_QT,
        },
      ];
    },
    getTree() {
      return {
        dataSource: this.createTree(
          this.tvDSCT,
          "CONGTRINH_ID",
          "CONGTRINH_CHA_ID"
        ),
        id: "CONGTRINH_ID",
        parentID: "CONGTRINH_CHA_ID",
        text: "TEN_HT",
        hasChildren: "hasChild",
        selected: "isSelected",
      };
    },
    vurl_luoi: {
      get() {
        !this.gParams.grvFileVBRowChanged["colFile"]
          ? ""
          : this.gParams.grvFileVBRowChanged["colFile"];
      },
    },
    vcongtrinh_id_luoi: {
      get() {
        console.log(
          "vcongtrinh_id_luoi",
          this.gParams.grvDSCongTrinhRowChanged,
          this.gParams.grvDSCongTrinhRowChanged["CONGTRINH_ID"]
        );
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["CONGTRINH_ID"]
            ? 0
            : parseInt(this.gParams.tvDoiTuongSelected["CONGTRINH_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["CONGTRINH_ID"]
          ? 0
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["CONGTRINH_ID"]);
      },
    },
    vhethong_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["HETHONG_ID"]
            ? -1
            : parseInt(this.gParams.tvDoiTuongSelected["HETHONG_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["HETHONG_ID"]
          ? -1
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["HETHONG_ID"]);
      },
    },
    vloact_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["LOAICT_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["LOAICT_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["LOAICT_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["LOAICT_ID"]);
      },
    },
    vnguonvon_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NGUONVON_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["NGUONVON_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["NGUONVON_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["NGUONVON_ID"]);
      },
    },
    vnhanvien_tc_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NHANVIEN_TC_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["NHANVIEN_TC_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_TC_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_TC_ID"]);
      },
    },
    vnhanvien_tk_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NHANVIEN_TK_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["NHANVIEN_TK_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_TK_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_TK_ID"]);
      },
    },
    vnhanvien_dd_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NHANVIEN_DD_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["NHANVIEN_DD_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_DD_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["NHANVIEN_DD_ID"]);
      },
    },
    vdonvi_ql_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["DONVI_QL_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["DONVI_QL_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["DONVI_QL_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["DONVI_QL_ID"]);
      },
    },
    vdonvi_pd_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["DONVI_PD_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["DONVI_PD_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["DONVI_PD_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["DONVI_PD_ID"]);
      },
    },
    vlydotc_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["MUCDICH_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["MUCDICH_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["MUCDICH_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["MUCDICH_ID"]);
      },
    },
    vnhom_ct_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NHOM_CT_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["NHOM_CT_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["NHOM_CT_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["NHOM_CT_ID"]);
      },
    },
    vcltb_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["CLTB_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["CLTB_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["CLTB_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["CLTB_ID"]);
      },
    },
    vdonvi_tc_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["DONVI_TC_ID"]
            ? ""
            : parseInt(this.gParams.tvDoiTuongSelected["DONVI_TC_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["DONVI_TC_ID"]
          ? ""
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["DONVI_TC_ID"]);
      },
    },
    vgt_dutoan_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["GT_DUTOAN"]
            ? 0
            : parseInt(this.gParams.tvDoiTuongSelected["GT_DUTOAN"])
          : !this.gParams.grvDSCongTrinhRowChanged["GT_DUTOAN"]
          ? 0
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["GT_DUTOAN"]);
      },
    },
    vgt_qt_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["GT_QUYETTOAN"]
            ? 0
            : parseInt(this.gParams.tvDoiTuongSelected["GT_QUYETTOAN"])
          : !this.gParams.grvDSCongTrinhRowChanged["GT_QUYETTOAN"]
          ? 0
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["GT_QUYETTOAN"]);
      },
    },
    vma_ct_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["MA_CT"]
            ? ""
            : this.gParams.tvDoiTuongSelected["MA_CT"]
          : !this.gParams.grvDSCongTrinhRowChanged["MA_CT"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["MA_CT"];
      },
    },
    vmact_chung_luoi: {
      get() {
        console.log(this.gParams.grvDSCongTrinhRowChanged);
        console.log(this.Tag == "1");
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["MACT_CHUNG"]
            ? ""
            : this.gParams.tvDoiTuongSelected["MACT_CHUNG"]
          : !this.gParams.grvDSCongTrinhRowChanged["MACT_CHUNG"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["MACT_CHUNG"];
      },
    },
    vloaish_id_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["LOAISH_ID"]
            ? 0
            : parseInt(this.gParams.tvDoiTuongSelected["LOAISH_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["LOAISH_ID"]
          ? 0
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["LOAISH_ID"]);
      },
    },
    vdiachi_tc_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["DIACHI_TC"]
            ? ""
            : this.gParams.tvDoiTuongSelected["DIACHI_TC"]
          : !this.gParams.grvDSCongTrinhRowChanged["DIACHI_TC"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["DIACHI_TC"];
      },
    },
    vcs_dutoan_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["CS_DUTOAN"]
            ? ""
            : this.gParams.tvDoiTuongSelected["CS_DUTOAN"]
          : !this.gParams.grvDSCongTrinhRowChanged["CS_DUTOAN"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["CS_DUTOAN"];
      },
    },
    vhientrang_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["HIENTRANG"]
            ? ""
            : this.gParams.tvDoiTuongSelected["HIENTRANG"]
          : !this.gParams.grvDSCongTrinhRowChanged["HIENTRANG"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["HIENTRANG"];
      },
    },
    vbienphap_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["BP_GIAIQUYET"]
            ? ""
            : this.gParams.tvDoiTuongSelected["BP_GIAIQUYET"]
          : !this.gParams.grvDSCongTrinhRowChanged["BP_GIAIQUYET"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["BP_GIAIQUYET"];
      },
    },
    vsohieu_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["SOHIEU_CT"]
            ? ""
            : this.gParams.tvDoiTuongSelected["SOHIEU_CT"]
          : !this.gParams.grvDSCongTrinhRowChanged["SOHIEU_CT"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["SOHIEU_CT"];
      },
    },
    vnoidung_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NOIDUNG"]
            ? ""
            : this.gParams.tvDoiTuongSelected["NOIDUNG"]
          : !this.gParams.grvDSCongTrinhRowChanged["NOIDUNG"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["NOIDUNG"];
      },
    },
    vten_ct_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["TEN_CT"]
            ? ""
            : this.gParams.tvDoiTuongSelected["TEN_CT"]
          : !this.gParams.grvDSCongTrinhRowChanged["TEN_CT"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["TEN_CT"];
      },
    },
    vnguoilap_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NGUOI_CN"]
            ? ""
            : this.gParams.tvDoiTuongSelected["NGUOI_CN"]
          : !this.gParams.grvDSCongTrinhRowChanged["NGUOI_CN"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["NGUOI_CN"];
      },
    },
    ngaytao_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NGAYLAP"]
            ? ""
            : this.gParams.tvDoiTuongSelected["NGAYLAP"]
          : !this.gParams.grvDSCongTrinhRowChanged["NGAYLAP"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["NGAYLAP"];
      },
    },
    ngay_ht_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NGAY_HT"]
            ? ""
            : this.gParams.tvDoiTuongSelected["NGAY_HT"]
          : !this.gParams.grvDSCongTrinhRowChanged["NGAY_HT"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["NGAY_HT"];
      },
    },
    ngay_nt_luoi: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["NGAY_NT"]
            ? ""
            : this.gParams.tvDoiTuongSelected["NGAY_NT"]
          : !this.gParams.grvDSCongTrinhRowChanged["NGAY_NT"]
          ? ""
          : this.gParams.grvDSCongTrinhRowChanged["NGAY_NT"];
      },
    },
    vtrangthai_tc_luoi_id: {
      get() {
        return this.Tag == "1"
          ? !this.gParams.tvDoiTuongSelected["TRANGTHAI_CT_ID"]
            ? 1
            : parseInt(this.gParams.tvDoiTuongSelected["TRANGTHAI_CT_ID"])
          : !this.gParams.grvDSCongTrinhRowChanged["TRANGTHAI_CT_ID"]
          ? 1
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["TRANGTHAI_CT_ID"]);
      },
    },
  },

  methods: {
    getTotalTIEN() {
      var TongTien = 0;
      this.grvCTVT.forEach((e) => {
        TongTien += e.TIEN;
      });
      return currency(TongTien);
    },
    getTotalTIEN_DT() {
      var TongTien = 0;
      this.grvCTVT.forEach((e) => {
        TongTien += e.TIEN_DT;
      });
      return currency(TongTien);
    },
    getTotalTIEN_QT() {
      var TongTien = 0;
      this.grvCTVT.forEach((e) => {
        TongTien += e.TIEN_QT;
      });
      return currency(TongTien);
    },
    FilerDVThiCong(e) {
      var query = new Query();
      query =
        e.text !== "" ? query.where("TEN_DV", "contains", e.text, true) : query;
      e.updateData(this.cboDvi_TC, query);
    },
    FilerDVQuanLy(e) {
      var query = new Query();
      query =
        e.text !== "" ? query.where("TEN_DV", "contains", e.text, true) : query;
      e.updateData(this.cboDvi_QL, query);
    },
    FilerDVPD(e) {
      var query = new Query();
      query =
        e.text !== "" ? query.where("TEN_DV", "contains", e.text, true) : query;
      e.updateData(this.cboDVi_PD, query);
    },
    createTree(dataTree, id, pId) {
      for (const i in dataTree) {
        var nodeId = dataTree[i][id];
        var nodePId = dataTree[i][pId];
        if (!dataTree[i][pId]) {
          delete dataTree[i][pId];
        }
        var arrChild = dataTree.filter(function (el) {
          return el[pId] == nodeId;
        });

        if (arrChild.length > 0) {
          dataTree[i].hasChild = true;
        }

        // expanded vào root, đơn hàng cha hoặc hợp đồng
        dataTree[i].expanded = nodeId == -1 ? true : false;
        dataTree[i].isSelected = nodeId == -1 ? true : false;

        // remove DONHANG_CHA_ID
        var arrParrent = dataTree.filter(function (el) {
          return el[id] == nodePId;
        });
        if (arrParrent.length == 0) {
          delete dataTree[i][pId];
        }
      }
      return dataTree;
    },
    selectRowComboboxGrid(refCbbGrid, value) {
      setTimeout(() => {
        this.$refs[refCbbGrid].$children[0].selectRowByRowData(value);
      }, 300);
    },
    isNullOrEmpty(value) {
      return value === null || value === "";
    },
    dateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    stringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    setFirstValue(cbo, key) {
      if (cbo.length) {
        return cbo[0][key];
      } else {
        return "";
      }
    },
    removeCssRule: function (file, rule) {
      var el = document.querySelector(`link[href = "${file}"]`).sheet;
      if (!el || !el.cssRules) return;
      var i = [].slice
        .call(el.cssRules)
        .findIndex((v) => v.selectorText == rule);
      if (i > -1) el.deleteRule(i);
    },
    btnSearchMaCT() {},

    async Load_quyen() {
      let dtQuyen = await (
        await API.getDsQuyen(this.axios, {
          nguoiDungId: this.$auth.getNguoiDungID(),
        })
      ).data.data;
      this.gParams.dsQuyen = dtQuyen;
    },
    async onGridDSCongTrinhRowChanged(value) {
      console.log("Danh sách công trình = ", value);
      this.gParams.grvDSCongTrinhRowChanged = value;
      try {
        if (value) {
          await this.NAP_DS_CONGTRINH_CT();
          await this.NAP_DS_CTVT();
          if (this.tabActived.currentTab == this.tabActived.tabCTVT) {
            await this.NAP_DS_GANGO(1);
            await this.NAP_DS_GANGO(0);
          }
        } else {
          let dt = this.grvDSCongTrinh;
          if (!dt.length) {
            await this.XOA_FORM();
            await this.KHOAMO_FORM(false);
            this.enable.btnNhapMoi = true;
            this.visible.btnXoaPhieu = false;
          } else {
            await this.XOA_FORM();
            await this.KHOAMO_FORM(false);
            this.visible.btnXoaPhieu = false;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    async LOAD_COMBO() {
      try {
        this.$root.loading(true);
        await this.Load_quyen();
        this.tabActived.currentTab =
          this.Tag != "1"
            ? this.tabActived.tabDSCongTrinh
            : this.tabActived.tabCTVT;

        this.cboLoaiCT = await (
          await API.getLoaiCongTrinhs(this.axios, {})
        ).data.data;
        this.gParams.cboLoaiCT = this.setFirstValue(this.cboLoaiCT, "loaiCtId");

        this.cboLoaiSoHieu = await (
          await API.getLoaiSoHieu(this.axios, {})
        ).data.data;
        this.gParams.cboLoaiSoHieu = this.setFirstValue(
          this.cboLoaiSoHieu,
          "LOAISH_ID"
        );

        this.cboNguonVon = await (
          await API.getDsNguonVon(this.axios, {})
        ).data.data;
        this.gParams.cboNguonVon = this.setFirstValue(
          this.cboNguonVon,
          "NGUONVON_ID"
        );

        let dvql = await (await API.getDsDViQL(this.axios, {})).data.data;
        this.cboDvi_QL = dvql;
        this.gParams.cboDvi_QL = this.setFirstValue(this.cboDvi_QL, "DONVI_ID");
        this.cboDVi_PD = dvql;
        this.gParams.cboDVi_PD = this.setFirstValue(this.cboDVi_PD, "DONVI_ID");

        this.cboDvi_TC = await (
          await API.getDsDonViTC(this.axios, {})
        ).data.data;
        this.gParams.cboDvi_TC = this.setFirstValue(this.cboDvi_TC, "DONVI_ID");

        this.gParams.dt_nv = await (
          await API.getDsNhanVien(this.axios, {})
        ).data.data;
        this.cboNV_DD = this.gParams.dt_nv;
        this.cboNV_KT = this.gParams.dt_nv;
        this.cboNV_TK = this.gParams.dt_nv;

        this.cboLoaiFile = await (
          await API.getLoaiFile(this.axios, {})
        ).data.data;
        this.gParams.cboLoaiFile = this.setFirstValue(
          this.cboLoaiFile,
          "LOAIVB_ID"
        );

        this.gParams.lydo_tc = await (
          await API.getDsLyDoTC(this.axios, {})
        ).data.data;
        this.cboLyDoTC = this.gParams.lydo_tc;
        this.gParams.cboLyDoTC = this.setFirstValue(
          this.cboLyDoTC,
          "mucDichId"
        );

        let cboNhomCT = await (await API.getDsNhomCT(this.axios, {})).data.data;
        cboNhomCT.unshift({ mucDich: "", mucDichId: null, tenTat: null });
        console.log(cboNhomCT, " = cboNhomCT");
        this.cboNhomCT = cboNhomCT;
        this.cboHeThong = await (
          await API.getDsHeThong(this.axios, {})
        ).data.data;

        this.cboChungLoai = await (
          await API.getDsChungLoaiTB(this.axios, {})
        ).data.data;
        this.gParams.cboChungLoai = this.setFirstValue(
          this.cboChungLoai,
          "chungLoaiTbiId"
        );
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async HIENTHI_GIAODIEN(kieu, nut) {
      if (kieu == 0) {
        this.visible.btnNhapMoi = false;
        this.visible.btnCapNhat = false;
        this.visible.btnHuyBo = false;
        this.visible.btnXoaPhieu = false;
        this.visible.tsbtnThemFile = false;
        this.visible.tsbtnAnFile = false;
        if (this.gParams.quyen == 0) {
          this.visible.btnNhapMoi = true;
        }
      }
      if (kieu == 1) {
        if (nut == "THEMFILE") {
          this.visible.tsbtnThemFile = false;
          this.visible.tsbtnAnFile = true;
        }
        if (nut == "ANFILE") {
          this.visible.tsbtnThemFile = true;
          this.visible.tsbtnAnFile = false;
        }
      }
    },
    async HIENTHE_TAB() {
      await this.TIMKIEM("");
    },

    XOA_FORM() {
      this.$refs.cboNV_TK.$refs.grid.refresh();
      this.$refs.cboNV_KT.$refs.grid.refresh();
      this.$refs.cboGNhomCT.$refs.grid.refresh();
      this.$refs.cboHeThong.$refs.grid.refresh();
      this.$refs.cboGridNV_DD.$refs.grid.refresh();

      this.gParams.vcongtrinh_id = 0;
      this.gParams.dtpNgayTao = null;
      this.gParams.dtpNgayHT = null;
      this.gParams.dtpNgayNT = null;
      this.gParams.txtMa_CT = "";
      this.gParams.txtMaCT_Cha = "";
      this.gParams.txtTen_CT = "";
      this.gParams.txtSoHieuCT = "";
      this.gParams.txtCS_DT = "";
      this.gParams.txtHienTrang = "";
      this.gParams.txtBienPhap = "";
      this.gParams.txtDiaChi_TC = "";
      this.gParams.txtNoiDung = "";
      this.gParams.cboNV_DD = null;
      this.gParams.cboNV_KT = "";
      this.gParams.cboNV_TK = "";

      this.gParams.calGT_DT = 0;
      this.gParams.calGT_QT = 0;
      this.gParams.cboDvi_QL = null;
      this.gParams.cboChungLoai = null;
      this.gParams.cboDvi_TC = null;
      this.gParams.cboDVi_PD = null;
      this.gParams.cboNguonVon = null;
      this.gParams.cboNhomCT = null;
      this.gParams.cboLoaiSoHieu = null;
      this.gParams.cboTrangThai = null;
      this.gParams.cboLyDoTC = null;
      this.gParams.cboLoaiCT = null;
      this.grvDSCTCG = [];
      this.grvDSCTDG = [];
      this.grvCTVT = [];
      this.gParams.cboHeThong = null;
      this.visible.tsbtnThemFile = false;
      this.KHOAMO_FORM(false);
      this.visible.btnCapNhat = false;
      this.visible.btnHuyBo = false;
      this.visible.btnXoaPhieu = false;
    },
    KHOAMO_FORM(isKhoaMo) {
      this.enable.layoutGroupTTCT = isKhoaMo;
    },
    async NAP_DS_CONGTRINH_CT() {
      try {
        this.gParams.vcongtrinh_id = this.vcongtrinh_id_luoi;
        this.gParams.txtMa_CT = this.vma_ct_luoi;
        this.gParams.txtMaCT_Cha = this.vmact_chung_luoi;
        this.gParams.txtTen_CT = this.vten_ct_luoi;
        this.gParams.txtDiaChi_TC = this.vdiachi_tc_luoi;
        this.gParams.txtNoiDung = this.vnoidung_luoi;
        this.gParams.txtHienTrang = this.vhientrang_luoi;
        this.gParams.txtBienPhap = this.vbienphap_luoi;

        this.gParams.dtpNgayHT = this.ngay_ht_luoi
          ? this.stringToDate(this.ngay_ht_luoi)
          : null;

        this.gParams.dtpNgayNT = this.ngay_nt_luoi
          ? this.stringToDate(this.ngay_nt_luoi)
          : null;

        this.gParams.cboTrangThai = this.vtrangthai_tc_luoi_id;

        this.gParams.dtpNgayTao = this.stringToDate(this.ngaytao_luoi);
        this.gParams.txtSoHieuCT = this.vsohieu_luoi;
        this.gParams.txtCS_DT = this.vcs_dutoan_luoi;
        this.gParams.cboLoaiCT = this.vloact_id_luoi;
        this.gParams.cboNguonVon = this.vnguonvon_id_luoi;

        // this.gParams.cboNV_DD = this.vnhanvien_dd_id_luoi;
        this.selectRowComboboxGrid("cboGridNV_DD", this.vnhanvien_dd_id_luoi);
        // this.gParams.cboNV_KT = this.vnhanvien_tc_id_luoi;
        this.selectRowComboboxGrid("cboNV_KT", this.vnhanvien_tc_id_luoi);
        // this.gParams.cboNV_TK = this.vnhanvien_tk_id_luoi;
        this.selectRowComboboxGrid("cboNV_TK", this.vnhanvien_tk_id_luoi);
        this.gParams.cboDvi_QL = this.vdonvi_ql_id_luoi;
        this.gParams.cboDvi_TC = this.vdonvi_tc_id_luoi;
        this.gParams.cboDVi_PD = this.vdonvi_pd_id_luoi;
        this.gParams.cboLoaiSoHieu = this.vloaish_id_luoi;

        this.gParams.cboChungLoai = this.vcltb_id_luoi;
        this.gParams.cboLyDoTC = this.vlydotc_id_luoi;
        this.gParams.calGT_DT = this.vgt_dutoan_luoi;
        this.gParams.calGT_QT = this.vgt_qt_luoi;
        //this.gParams.cboNhomCT = this.vnhom_ct_luoi;
        this.selectRowComboboxGrid("cboGNhomCT", this.vnhom_ct_luoi);
        //this.gParams.cboHeThong = this.vhethong_id_luoi;
        this.selectRowComboboxGrid("cboHeThong", this.vhethong_id_luoi);
        this.gParams.vproject_code = !this.gParams.grvDSCongTrinhRowChanged[
          "MA_DUAN_IMS"
        ]
          ? null
          : this.gParams.grvDSCongTrinhRowChanged["MA_DUAN_IMS"];
        this.gParams.cboDuAnIMS = this.gParams.vproject_code;

        this.gParams.vproject_id = !this.gParams.grvDSCongTrinhRowChanged[
          "DUAN_IMS_ID"
        ]
          ? 0
          : parseInt(this.gParams.grvDSCongTrinhRowChanged["DUAN_IMS_ID"]);

        this.KHOAMO_FORM(true);

        this.visible.btnCapNhat = true;
        this.visible.tsbtnThemFile = true;
        this.visible.btnHuyBo = true;
        this.enable.btnXoaPhieu = true;
        this.visible.btnXoaPhieu = true;
        this.visible.btnNhapMoi = true;
        this.enable.btnNhapMoi = true;
        await this.HIENTHI_FILECT();
      } catch (error) {
        console.log(error);
      }
    },
    async HIENTHI_FILECT() {
      if (this.vcongtrinh_id_luoi == 0) return;
      try {
        this.$root.loading(true);
        this.gParams.s = ""; // reset lại giá trị
        let ds = await (
          await API.getDsFileVBCT(this.axios, {
            congTrinhId: this.vcongtrinh_id_luoi,
          })
        ).data.data;
        this.grvDSFile = ds;

        if (this.grvDSFile.length == 0) {
          this.CLEAR_FILE();
        }
        console.log(this.grvDSFile, " = grvDSFile");
      } catch (error) {
        this.toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async NAP_DS_CTVT() {
      try {
        this.$root.loading(true);
        console.log(
          "NAP_DS_CTVT this.vcongtrinh_id_luoi",
          this.vcongtrinh_id_luoi
        );
        let ds = await (
          await API.getDsChiTietVTCongTrinh(this.axios, {
            kieu: 0,
            congTrinhId: this.vcongtrinh_id_luoi,
          })
        ).data.data;
        this.grvCTVT = ds;
        console.log(this.grvCTVT, " = grvCTVT");
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async v_btnRef_Click() {
      this.cboDuAnIMS = await (await API.getDSIMS(this.axios)).data.data;

      // if(dt != null && dt != ""){
      //   var data = JSON.parse(dt)
      //   if("NewDataSet" in data && "Table" in data.NewDataSet){
      //     this.cboDuAnIMS = data.NewDataSet.Table
      //   }else{
      //     this.cboDuAnIMS = []
      //   }
      // }else{
      //   this.cboDuAnIMS = []
      // }
    },
    THAOTAC_NUT(kieu) {
      if (kieu == 1) {
        //nhap moi
        this.gParams.vcongtrinh_id = 0;
        this.XOA_FORM();
        this.KHOAMO_FORM(true);
        this.gParams.dtpNgayTao = new Date();
        // Cập nhật nút
        this.visible.btnHuyBo = true;
        this.visible.btnCapNhat = true;
        this.enable.btnNhapMoi = false;
        this.visible.tsbtnThemFile = false;
        this.visible.tsbtnAnFile = false;
        // Chọn control nhập
      } else if (kieu == 3) {
        // Xóa form  huy nhap
        this.XOA_FORM();
        // Cập nhật nút
        this.visible.btnCapNhat = false;
        this.visible.btnHuyBo = false;
        this.visible.btnXoaPhieu = false;
        this.enable.btnNhapMoi = true;
        this.visible.tsbtnThemFile = false;
        this.visible.tsbtnAnFile = false;
      }
    },
    async TIMKIEM(ma_ct) {
      try {
        this.gParams.dt_all = [];
        this.gParams.vcongtrinh_id = 0;
        if (this.Tag == "1") {
          this.tvDSCT = [];
        } else {
          this.grvDSCongTrinh = [];
        }
        if (ma_ct != "") {
          let data = await this.timKiemLoad();
          if (!data.data.length && !this.gParams.load_first) {
            this.$toast.error(data.message);
          } else {
            if (this.Tag != "1") {
              this.grvDSCongTrinh = [];
              this.grvDSCongTrinh = data.data;
              if (data.data.length) {
                this.gParams.dt_all = data.data;
              }
            } else {
              this.enable.btnNhapMoi = true;
              this.XOA_FORM();
              this.KHOAMO_FORM(false);
              this.visible.btnXoaPhieu = false;
              this.tvDSCT = data.data;
              if (data.data.length) {
                this.gParams.dt_all = data.data;
              }
            }
          }
        } else {
          let data = await this.timKiemLoad();
          if (!data.data.length && !this.gParams.load_first) {
            this.$toast.error(data.message);
          } else {
            if (this.Tag != "1") {
              this.grvDSCongTrinh = [];
              this.grvDSCongTrinh = data.data;
              if (data.data.length) {
                this.gParams.dt_all = data.data;
              }
            } else {
              this.enable.btnNhapMoi = true;
              this.XOA_FORM();
              this.KHOAMO_FORM(false);
              this.visible.btnXoaPhieu = false;
              this.tvDSCT = data.data;
              if (data.data.length) {
                this.gParams.dt_all = data.data;
              }
            }
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async timKiemLoad() {
      //TimKiem
      let search = [];
      let mact = "";
      let ngaybd = "";
      let ngaykt = "";
      if (!this.isNullOrEmpty(this.gParams.txtMaCT_TK)) {
        mact = this.gParams.txtMaCT_TK.trim();
      }
      if (this.gParams.ckbTuNgay) {
        ngaybd = this.dateToString(this.gParams.dtpTuNgay);
      }
      if (this.gParams.ckbDenNgay) {
        ngaykt = this.dateToString(this.gParams.dtpDenNgay);
      }
      search.push({
        MA_CT: mact,
        NGAY_BD: ngaybd,
        NGAY_KT: ngaykt,
        TAG: this.Tag != "1" ? "0" : "1",
      });
      try {
        this.$root.loading(true);
        let ds = await (
          await API.postTraCuuCT(this.axios, {
            data: search,
          })
        ).data.data;
        console.log("postTraCuuCT data = ", ds);
        if (ds && ds.length) {
          return { message: "OK", data: ds };
        } else {
          return { message: "Không có dữ liệu trả về", data: [] };
        }
        //end TimKiem
      } catch (error) {
        console.log(error);
        return { message: "Lỗi khi lấy dữ liệu ", data: error.data.error_code };
      } finally {
        this.$root.loading(false);
      }
    },
    async KIEMTRA_DULIEU() {
      try {
        if (
          this.gParams.txtTen_CT == null ||
          !this.gParams.txtTen_CT.toString()
        ) {
          this.$toast.error("Bạn chưa nhập tên công trình");
          return false;
        }
        if (
          this.gParams.txtDiaChi_TC == null ||
          !this.gParams.txtDiaChi_TC.toString()
        ) {
          this.$toast.error("Bạn chưa nhập địa chỉ thi công");
          return false;
        }
        if (
          this.gParams.cboDvi_QL == null ||
          !this.gParams.cboDvi_QL.toString()
        ) {
          this.$toast.error("Bạn chưa chọn đơn vị quản lý");
          return false;
        }
        if (
          this.gParams.cboDvi_TC == null ||
          !this.gParams.cboDvi_TC.toString()
        ) {
          this.$toast.error("Bạn chưa chọn đơn vị thi công");
          return false;
        }
        if (
          this.gParams.cboLoaiSoHieu == null ||
          !this.gParams.cboLoaiSoHieu.toString()
        ) {
          this.$toast.error("Bạn chưa chọn loại số hiệu cho công trình");
          return false;
        }
        if (
          this.gParams.txtSoHieuCT == null ||
          !this.gParams.txtSoHieuCT.toString()
        ) {
          this.$toast.error("Bạn chưa nhập số hiệu công trình");
          return false;
        }
        console.log(this.gParams.cboLoaiCT);
        if (
          this.gParams.cboLoaiCT == null ||
          !this.gParams.cboLoaiCT.toString()
        ) {
          this.$toast.error("Bạn chưa chọn loại công trình");
          return false;
        }

        if (
          this.gParams.cboNguonVon == null ||
          !this.gParams.cboNguonVon.toString()
        ) {
          this.$toast.error("Bạn chưa chọn nguồn vốn");
          return false;
        }

        if (this.gParams.dt_nv.length == 1) {
          this.$toast.error(
            "Không lấy được thông tin nhân viên. Bạn hãy kiểm tra lại."
          );
          return false;
        }

        if (
          this.gParams.cboNV_DD == null ||
          !this.gParams.cboNV_DD.toString() ||
          this.gParams.cboNV_DD == -1
        ) {
          this.$toast.error("Bạn chưa chọn  nhân viên đại diện");
          return false;
        }
        if (
          this.gParams.cboNV_KT == null ||
          !this.gParams.cboNV_KT.toString() ||
          this.gParams.cboNV_KT == -1
        ) {
          this.$toast.error("Bạn chưa chọn nhân viên kỹ thuật");
          return false;
        }
        if (
          this.gParams.cboNV_TK == null ||
          !this.gParams.cboNV_TK.toString() ||
          this.gParams.cboNV_TK == -1
        ) {
          this.$toast.error("Bạn chưa chọn nhân viên triển khai");
          return false;
        }
        if (
          this.gParams.cboDVi_PD == null ||
          !this.gParams.cboDVi_PD.toString()
        ) {
          this.$toast.error("Bạn chưa chọn đơn vị phê duyệt");
          return false;
        }
        if (
          this.gParams.cboNhomCT == null ||
          !this.gParams.cboNhomCT.toString()
        ) {
          this.$toast.error("Bạn chưa chọn nhóm công trình");
          return false;
        }
        if (
          this.gParams.cboChungLoai == null ||
          !this.gParams.cboChungLoai.toString()
        ) {
          this.$toast.error("Bạn chưa chọn chủng loại thiết bị");
          return false;
        }
        if (
          this.gParams.cboLyDoTC == null ||
          !this.gParams.cboLyDoTC.toString()
        ) {
          this.$toast.error("Bạn chưa chọn lý do thi công");
          return false;
        }
        if (this.enable.btnNhapMoi) {
          let dt = await (
            await API.getCountPhieuVTCT(this.axios, {
              congTrinhId: this.vcongtrinh_id_luoi,
            })
          ).data.data;
          if (
            this.vhethong_id_luoi == -1 &&
            this.gParams.cboHeThong != "-1" &&
            this.gParams.cboHeThong != null
          ) {
            this.$toast.error(
              "Bạn không được thay đổi thông tin công trình (Đã có vật tư) sang hệ thống công trình"
            );
            return false;
          }

          if (
            this.vhethong_id_luoi != -1 &&
            this.gParams.cboHeThong != this.vhethong_id_luoi
          ) {
            if (dt != 0) {
              this.$toast.error(
                "Bạn không được thay đổi thông tin công trình hệ thống (Đã có vật tư) sang hệ thống công trình khác"
              );
              return false;
            }
          }
          if (this.vhethong_id_luoi != -1 && this.gParams.cboHeThong == "-1") {
            if (dt != 0) {
              this.$toast.error(
                "Bạn không được thay đổi thông tin công trình hệ thống (Đã có vật tư) sang công trình "
              );
              return false;
            }
          }
        }
        if (
          this.Tag == "1" &&
          (this.gParams.cboHeThong == null || this.gParams.cboHeThong == "-1")
        ) {
          this.$toast.error("Bạn chưa chọn thông tin hệ thống ");
          return false;
        }
        return true;
      } catch (error) {
        this.$toast.error("Có lỗi khi kiểm tra dữ liệu  " + error);
        console.log(error);
      }
    },
    oncboHeThongChanged(value) {
      if (value) {
        this.gParams.cboHeThong = value.HETHONG_ID;
      }
    },
    oncboNhomCTChanged(value) {
      if (value) {
        this.gParams.cboNhomCT = value.mucDichId;
      }
    },
    oncboDuAnIMSChanged(value) {
      this.gParams.cboDuAnIMS = value.ProjectCode;
      this.gParams.vproject_id = value.ProjectID;
      this.gParams.vproject_code = value.ProjectCode;
    },
    async cboDuAnIMS_ButtonClick() {
      await this.v_btnRef_Click();
      this.gParams.cboDuAnIMS = "";
      this.gParams.vproject_id = 0;
      this.gParams.vproject_code = "";
    },
    oncboNV_DDChanged(value) {
      if (value) {
        this.gParams.cboNV_DD = value.nhanVienId;
      }
    },
    oncboNV_KTChanged(value) {
      if (value) {
        this.gParams.cboNV_KT = value.nhanVienId;
      }
    },
    oncboNV_TKChanged(value) {
      if (value) {
        this.gParams.cboNV_TK = value.nhanVienId;
      }
    },
    async CAPNHAT() {
      try {
        let isCheck = await this.KIEMTRA_DULIEU();
        if (!isCheck) return;
        if (this.enable.btnNhapMoi) {
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            `Bạn có muốn cập nhật thông tin công trình không?`,
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) return;
        }
        this.loading(true);
        let res = await this.capNhatLoad();
        if (res.startsWith("OK-")) {
          this.$toast.success(
            !this.enable.btnNhapMoi
              ? "Tạo công trình thành công"
              : "Cập nhật thông tin công trình thành công"
          );

          await this.TIMKIEM("");
          let vct_temp_id = parseInt(res.split("-")[1]);
          console.log(vct_temp_id, " = vct_temp_id");
          await this.FocusGridViewRow(vct_temp_id);
        } else {
          this.$toast.error("Thông báo: " + res);
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },

    async FocusGridViewRow(value) {
      let indexs = this.grvDSCongTrinh.findIndex(
        (a) =>
          a.CONGTRINH_ID != null &&
          a.CONGTRINH_ID.toString() == value.toString()
      );
      if (indexs != -1) {
        if (
          !(
            indexs + 1 >
              this.$refs["grvDS_CongTrinh"].pageIndexData *
                this.$refs["grvDS_CongTrinh"].pageSizeData &&
            indexs + 1 <=
              (this.$refs["grvDS_CongTrinh"].pageIndexData + 1) *
                this.$refs["grvDS_CongTrinh"].pageSizeData
          )
        ) {
          this.$refs["grvDS_CongTrinh"].$refs.pagination.gotoPage(
            parseInt(indexs / this.$refs["grvDS_CongTrinh"].pageSizeData)
          );
        }
        let self = this;
        let ind = parseInt(indexs % this.$refs["grvDS_CongTrinh"].pageSizeData);
        setTimeout(() => {
          self.$refs["grvDS_CongTrinh"].grid.selectRow(ind, true);
        }, 1000);
      }
    },
    async capNhatLoad() {
      let kieu = 0;
      this.gParams.dt_all = [];
      let dr = {
        CONGTRINH_ID: this.gParams.vcongtrinh_id,
        MA_CT: this.gParams.txtMa_CT,
        TEN_CT: this.gParams.txtTen_CT,
        SOHIEU_CT: this.gParams.txtSoHieuCT,
        LOAICT_ID: this.gParams.cboLoaiCT,
        NGUONVON_ID: this.gParams.cboNguonVon,
        DIACHI_TC: this.gParams.txtDiaChi_TC,
        CS_DUTOAN: this.gParams.txtCS_DT,
        BP_GIAIQUYET: this.gParams.txtBienPhap,
        HIENTRANG: this.gParams.txtHienTrang,
        NOIDUNG: this.gParams.txtNoiDung,
        GT_DUTOAN: this.gParams.calGT_DT,
        NHANVIEN_DD_ID: this.gParams.cboNV_DD,
        NHANVIEN_TC_ID: this.gParams.cboNV_KT,
        DONVI_QL_ID: this.gParams.cboDvi_QL,
        DONVI_TC_ID: this.gParams.cboDvi_TC,
        NHANVIEN_TK_ID: this.gParams.cboNV_TK,
        DONVI_PD_ID: this.gParams.cboDVi_PD,
        MUCDICH_ID: this.gParams.cboLyDoTC,
        NHOM_CT_ID: this.gParams.cboNhomCT,
        CLTB_ID: this.gParams.cboChungLoai,
        DUAN_IMS_ID: this.gParams.vproject_id,
        MA_DUAN_IMS: this.gParams.vproject_code,
        LOAISH_ID: this.gParams.cboLoaiSoHieu,
        HETHONG_ID:
          this.gParams.cboHeThong == null || this.gParams.cboHeThong == "-1"
            ? ""
            : this.gParams.cboHeThong,
        KIEU: this.Tag == "1" ? "1" : "0",
      };
      if (this.Tag == "1") {
        if (!this.enable.btnNhapMoi) {
          let node = this.gParams.tvDoiTuongSelected["CONGTRINH_ID"];
          if (node && note != "-1") {
            dr["CONGTRINH_CHA_ID"] = node;
          }
        }
      }
      this.gParams.dt_all.push(dr);
      if (!this.enable.btnNhapMoi) {
        kieu = 1;
      } else {
        kieu = 2;
      }
      let data = await (
        await API.postCapNhatCongTrinh(this.axios, {
          kieu: kieu,
          congTrinhId: this.gParams.vcongtrinh_id,
          dsCongTrinh: this.gParams.dt_all,
        })
      ).data.data;
      return data;
    },
    async XOAPHIEU() {
      try {
        if (this.vcongtrinh_id_luoi == 0) {
          this.$toast.error(
            "Không lấy được thông tin công trình không thể xóa"
          );
          return;
        }
        //Kiểm tra dữ liệu viết sau
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có muốn xóa thông tin công trình này không ?`,
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (!isConfirm) return;
        this.$root.loading(true);
        let kq = await (
          await API.getXoaPhieu(this.axios, {
            congTrinhId: this.vcongtrinh_id_luoi,
          })
        ).data.data;
        if (!kq) {
          return this.$toast.error("Không lấy được thông tin trả về");
        }
        if (kq == "1") {
          this.$toast.success("Xóa công trình thành công");
          await this.TIMKIEM("");
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async onbtnLayTT() {
      await this.TIMKIEM("");
    },
    onbtnCapNhat() {
      this.CAPNHAT();
    },
    onbtnXoaPhieu() {
      this.XOAPHIEU();
    },
    onbtnNhapMoi() {
      this.THAOTAC_NUT(1);
    },
    onbtnHuyBo() {
      this.THAOTAC_NUT(3);
    },
    async ontsbtnThemFile() {
      this.HIENTHI_GIAODIEN(1, "THEMFILE");
      // await this.HIENTHI_FILECT();
    },
    closeFile() {
      this.HIENTHI_GIAODIEN(1, "ANFILE");
      this.$bvModal.hide("modalthongtinfile");
    },
    CLEAR_FILE() {
      this.gParams.s = "";
      this.gParams.vfile_ct = 0;
      this.gParams.cboLoaiFile = null;
      this.gParams.dtpNgayVB = new Date();
      this.gParams.txtSoCV = "";
      this.gParams.txtLyDoTH = "";
      this.gParams.txtGhiCHu = "";
      this.grvDSFile = [];
      this.grvFileVB = [];
      this.enable.btnFilePath = false;
      this.text.btnThemFile = "Thêm mới";
    },
    async onbtnThemFile() {
      try {
        if (this.vcongtrinh_id_luoi == 0) {
          this.$toast.error(
            "Chưa có thông tin công trình không thể thêm file "
          );
          return;
        }
        if (!this.gParams.cboLoaiFile) {
          this.$toast.error("Bạn phải chọn loại văn bản");
          return;
        }
        if (!this.gParams.dtpNgayVB) {
          this.$toast.error("Bạn chưa nhập ngày văn bản");
          return;
        }
        if (!this.gParams.dtpNgayTT) {
          this.$toast.error("Bạn chưa nhập ngày thực tế");
          return;
        }
        if (!this.gParams.txtSoCV) {
          this.$toast.error("Bạn chưa chọn số công văn");
          return;
        }
        if (!this.gParams.vfile_ct) {
          let ds = await (
            await API.postThemVaCapNhatFileCT(this.axios, {
              fileId: null,
              loaiVbId: this.gParams.cboLoaiFile,
              ngayVb: this.dateToString(this.gParams.dtpNgayVB),
              ngayTt: this.dateToString(this.gParams.dtpNgayTT),
              congTrinhId: this.vcongtrinh_id_luoi,
              soVb: this.gParams.txtSoCV.trim(),
              lydoTh: this.gParams.txtLyDoTH.trim(),
              ghiChu: this.gParams.txtGhiCHu.trim(),
            })
          ).data.data;
          this.$toast.success("Tạo file văn bản thành công");
          await this.HIENTHI_FILECT();
        } else {
          let ds = await (
            await API.postThemVaCapNhatFileCT(this.axios, {
              fileId: this.gParams.vfile_ct,
              loaiVbId: this.gParams.cboLoaiFile,
              ngayVb: this.dateToString(this.gParams.dtpNgayVB),
              ngayTt: this.dateToString(this.gParams.dtpNgayTT),
              congTrinhId: this.vcongtrinh_id_luoi,
              soVb: this.gParams.txtSoCV.trim(),
              lydoTh: this.gParams.txtLyDoTH.trim(),
              ghiChu: this.gParams.txtGhiCHu.trim(),
            })
          ).data.data;
          this.$toast.success("Cập nhật file văn bản thành công");
          await this.HIENTHI_FILECT();
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    onbtnClear() {
      let grvDSFile = this.grvDSFile;
      this.CLEAR_FILE();
      this.grvDSFile = grvDSFile;
    },
    onGridFileVBRowChanged(value) {
      this.gParams.grvFileVBRowChanged = value;
    },
    onGridDsFileRowChanged(value) {
      this.gParams.grvDSFileRowChanged = value;
      if (value) {
        this.NAP_DS_CHITIET_FILE();
      } else {
        this.CLEAR_FILE();
      }
    },
    NAP_DS_CHITIET_FILE() {
      try {
        this.gParams.s = "";
        this.gParams.vfile_ct = parseInt(
          this.gParams.grvDSFileRowChanged["FILE_ID"]
        );
        this.gParams.cboLoaiFile = parseInt(
          this.gParams.grvDSFileRowChanged["LOAIVB_ID"]
        );
        this.gParams.dtpNgayVB = this.stringToDate(
          this.gParams.grvDSFileRowChanged["NGAY_VB"]
        );
        this.gParams.dtpNgayTT = this.stringToDate(
          this.gParams.grvDSFileRowChanged["NGAY_TT"]
        );
        this.gParams.txtSoCV = this.gParams.grvDSFileRowChanged["SO_VB"];
        this.gParams.txtLyDoTH = "";
        if (this.gParams.grvDSFileRowChanged["LYDO_TH"]) {
          this.gParams.txtLyDoTH = this.gParams.grvDSFileRowChanged["LYDO_TH"];
        }
        if (this.gParams.grvDSFileRowChanged["URL"]) {
          this.gParams.s = this.gParams.grvDSFileRowChanged["URL"].toString();
          let words = [];
          words = this.gParams.s.split("|");
          let dt = [];
          words.forEach((e) => {
            if (e) {
              let t = e;
              let t_t = e.split("/");
              dt.push({
                colFile: t,
                colFileName: t_t[t_t.length - 1],
              });
            }
          });
          this.grvFileVB = dt;
        } else {
          this.grvFileVB = [];
        }
        this.gParams.txtGhiCHu = "";
        if (this.gParams.grvDSFileRowChanged["GHICHU"])
          this.gParams.txtGhiCHu = this.gParams.grvDSFileRowChanged["GHICHU"];
        this.enable.btnFilePath = true;
        this.text.btnThemFile = "Cập nhật";
      } catch (error) {
        this.$toast.error(error.data.message);
      }
    },
    onbtnFilePath() {
      if (!this.gParams.vfile_ct)
        return this.$toast.error("Chưa chọn danh sách file");
      this.$refs["ref-choose-file"].$el.querySelector("input").click();
    },
    async onChangeFileVC(e) {
      try {
        let fileChooser = this.$refs["ref-choose-file"];
        let choosedFiles = fileChooser.$el.querySelector("input").files;

        if (choosedFiles.length == 0) {
          fileChooser.reset();
          return;
        }
        choosedFiles = [...choosedFiles];
        fileChooser.reset();

        this.loading(true);
        let formData = new FormData();
        for (let i = 0; i < choosedFiles.length; i++) {
          let item = choosedFiles[i];
          formData.append("file", item);
        }

        let response = await await API.uploadFileMinio(this.axios, formData);

        let v_file = this.gParams.s;
        if (response.data.error_code === "BSS-00000000") {
          let sDuongDanDinhKemFTP = response.data.data[0].message.relativeUrl;
          v_file += sDuongDanDinhKemFTP + "|";
        }

        let ds = await (
          await API.updateFileUrl(this.axios, {
            fileId: this.gParams.vfile_ct,
            url: v_file,
          })
        ).data.data;
        this.$toast.success(`Upload file thành công!`);
        await this.HIENTHI_FILECT();
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async onFileVBCommand(args) {
      console.log(args);
      if (args.commandColumn.type == "DELETE") {
        try {
          if (!this.gParams.vfile_ct) return;
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            `Bạn có muốn xóa file này không ?`,
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) return;
          this.$root.loading(true);
          let t = args.rowData.colFile;
          this.gParams.s = this.gParams.s.replace(t + "|", "");
          let ds = await (
            await API.updateFileUrl(this.axios, {
              fileId: this.gParams.vfile_ct,
              url: this.gParams.s,
            })
          ).data.data;
          await (
            await API.deleteFileMinIO(this.axios, {
              fileSource: t,
            })
          ).data.data;
          this.$toast.success("Xóa file thành công");
          await this.HIENTHI_FILECT();
        } catch (error) {
          this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      } else {
        try {
          this.DOWLOAD_OPEN(args.rowData);
        } catch (error) {}
      }
    },
    async onDsFileCommand(args) {
      console.log(args);
      try {
        if (!this.vcongtrinh_id_luoi || !this.gParams.vfile_ct) return;
        console.log(this.vcongtrinh_id_luoi, " = vcongtrinh_id_luoi");
        console.log(this.gParams.vfile_ct, " = this.gParams.vfile_ct");
        let isConfirm = await this.$bvModal.msgBoxConfirm(
          `Bạn có muốn xóa thông tin file văn bản này không ?`,
          {
            okVariant: "success",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy",
            centered: true,
          }
        );
        if (!isConfirm) return;

        this.$root.loading(true);
        let ds = await (
          await API.deleteFileDSFile(this.axios, {
            congTrinhId: this.vcongtrinh_id_luoi,
            fileId: this.gParams.vfile_ct,
          })
        ).data.data;
        if (ds == 1) this.$toast.success("Xóa file văn bản thành công");
        await this.HIENTHI_FILECT();
        this.grvFileVB = [];
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
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
        this.$toast.error(error.data.message);
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
    async DOWLOAD_OPEN(File) {
      let url = File.colFile;
      console.log(url, " = url");
      if (!url) return;
      let fileName = url.colFileName;
      try {
        this.loading(true);
        var getLink = await await API.getLinkFile(this.axios, {
          fileSource: url,
        });
        let linkFile = getLink.data.data.name;
        const downloadLink = document.createElement("a");
        downloadLink.href = linkFile;
        downloadLink.download = fileName;
        downloadLink.target = "_blank";
        downloadLink.click();
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async txtMaCT_TKEnter() {
      if (!this.gParams.txtMaCT_TK) return;
      await this.TIMKIEM(this.gParams.txtMaCT_TK);
    },
    btnBC_BB28() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTDTKP",
        loai: 8,
      };
      this.getPdf(url, body);
    },
    btnBC_BB29() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTPAKT",
        loai: 3,
      };
      this.getPdf(url, body);
    },
    btnBC_BB30() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTBBHT",
        loai: 4,
      };
      this.getPdf(url, body);
    },
    btnBC_BB31() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTBBSC",
        loai: 5,
      };
      this.getPdf(url, body);
    },
    btnBC_BB32() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTBBPS",
        loai: 6,
      };
      this.getPdf(url, body);
    },
    btnBC_BB33() {
      if (!this.vcongtrinh_id_luoi)
        return this.$toast.error("Bạn phải chọn 1 công trình để in");
      let url = "api/bao-cao/bao-cao-cong-trinh?extend=pdf";
      let body = {
        congTrinhId: this.vcongtrinh_id_luoi,
        maBc: "CTHSHC",
        loai: 7,
      };
      this.getPdf(url, body);
    },
    async SINH_SOHIEU_CT() {
      try {
        if (!this.enable.btnNhapMoi) {
          let sohieu_ct = "";
          if (this.gParams.dtpNgayTao) {
            let strNgayTao = this.dateToString(this.gParams.dtpNgayTao);
            let nam = strNgayTao.substring(strNgayTao.length - 2);
            let loaish = "";
            if (this.gParams.cboLoaiSoHieu) {
              let ds_lsh = this.cboLoaiSoHieu;
              loaish = ds_lsh.find(
                (e) => e.LOAISH_ID == this.gParams.cboLoaiSoHieu
              ).MA_LOAI;
            }
            let ma_dvi = await (
              await API.getMaDonVi(this.axios, {
                congTrinhId: this.vcongtrinh_id_luoi,
              })
            ).data.data;
            sohieu_ct = nam + ".xxxxx." + loaish + "." + ma_dvi;
          }
          this.gParams.txtSoHieuCT = sohieu_ct;
        }
      } catch (error) {
        console.log(error);
      }
    },
    cboLoaiSoHieuChanged() {
      if (
        this.gParams.cboLoaiSoHieu != null &&
        this.gParams.cboLoaiSoHieu != ""
      ) {
        this.SINH_SOHIEU_CT();
      }
    },
    dtpNgayTaoChanged() {
      if (this.gParams.dtpNgayTao != null && this.gParams.dtpNgayTao != "") {
        this.SINH_SOHIEU_CT();
      }
    },
    txtBienPhapDblClick() {
      this.frmNoiDungTT.title = "Nhập nội dung biện pháp";
      this.gParams.vnoidung = "";
      this.gParams.vnoidung = this.gParams.txtBienPhap;
      this.$bvModal.show("popupNoiDungTT");
    },
    txtHienTrangDblClick() {
      this.frmNoiDungTT.title = "Nhập nội dung hiện trạng";
      this.gParams.vnoidung = "";
      this.gParams.vnoidung = this.gParams.txtHienTrang;
      this.$bvModal.show("popupNoiDungTT");
    },
    txtCS_DTDblClick() {
      this.frmNoiDungTT.title = "Nhập nội dung cơ sở dự toán";
      this.gParams.vnoidung = "";
      this.gParams.vnoidung = this.gParams.txtCS_DT;
      this.$bvModal.show("popupNoiDungTT");
    },
    dbltxtTen_CTClick() {
      this.frmNoiDungTT.title = "Nhập nội dung tên công trình";
      this.gParams.vnoidung = "";
      this.gParams.vnoidung = this.gParams.txtTen_CT;
      this.$bvModal.show("popupNoiDungTT");
    },
    onClickXacNhan() {
      if (!this.gParams.vnoidung.length)
        return this.$toast.error("Bạn phải nhập nội dung");
      if (this.frmNoiDungTT.title == "Nhập nội dung biện pháp") {
        this.gParams.txtBienPhap = this.gParams.vnoidung.trim();
      } else if (this.frmNoiDungTT.title == "Nhập nội dung hiện trạng") {
        this.gParams.txtHienTrang = this.gParams.vnoidung.trim();
      } else if (this.frmNoiDungTT.title == "Nhập nội dung tên công trình") {
        this.gParams.txtTen_CT = this.gParams.vnoidung.trim();
      } else {
        this.gParams.txtCS_DT = this.gParams.vnoidung.trim();
      }
      this.$bvModal.hide("popupNoiDungTT");
    },
    kiemtra_nguoicn() {
      if (!this.vnguoilap_luoi) {
        this.$toast.error("Không tìm thấy người lập!!!");
        return false;
      }
      let list = [];
      this.gParams.dt_nv.forEach((e) => {
        list.push(e["nhanVienId"]);
      });
      if (
        list.some((x) => x == this.$auth.getNhanVienID()) ||
        this.vnguoilap_luoi == this.$auth.getMaNhanVien()
      ) {
        return true;
      }
      return false;
    },
    async ondtpNgayNTClick() {
      try {
        this.$root.loading(true);
        if (!this.vcongtrinh_id_luoi)
          return this.$toast.error("Bạn chưa chọn công trình để thực hiện");
        if (!this.kiemtra_nguoicn()) {
          return;
        }
        if (!this.gParams.dtpNgayNT) {
          if (this.ngay_ht_luoi) {
            return this.$toast.error(
              "Bạn không được huỷ ngày nghiệm thu khi vẫn còn ngày hoàn thành"
            );
          }
        } else {
          let ds = await (
            await API.postCapNhatNgayNT(this.axios, {
              congTrinhId: this.vcongtrinh_id_luoi,
              ngayNt: this.dateToString(this.gParams.dtpNgayNT),
              nguoiDungId: this.$auth.getNguoiDungID(),
            })
          ).data.data;
          if (ds == 1) {
            this.$toast.success("Cập nhật ngày nghiệm thu thành công");
          } else {
            this.$toast.error(ds);
          }
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async ondtpNgayHTClick() {
      try {
        this.$root.loading(true);
        if (!this.vcongtrinh_id_luoi)
          return this.$toast.error("Bạn chưa chọn công trình để thực hiện");
        if (!this.kiemtra_nguoicn()) {
          return;
        }
        // Đã bắt lỗi ở API
        // if (this.gParams.dtpNgayHT) {
        //   if (!this.ngay_nt_luoi) {
        //     return this.$toast.error(
        //       "Bạn phải có ngày nghiệm thu mới được cập nhật ngày hoàn thành"
        //     );
        //   } else {
        //     if (this.gParams.dtpNgayHT < this.stringToDate(this.ngay_nt_luoi)) {
        //       return this.$toast.error(
        //         "Bạn phải chọn ngày hoàn thành lớn hơn hoặc bằng ngày nghiệm thu"
        //       );
        //     }
        //   }
        // }
        let ds = await (
          await API.postCapNhatNgayHT(this.axios, {
            congTrinhId: this.vcongtrinh_id_luoi,
            ngayHt: this.dateToString(this.gParams.dtpNgayHT),
            nguoiDungId: this.$auth.getNguoiDungID(),
          })
        ).data.data;
        if (ds == 1) {
          this.$toast.success("Cập nhật ngày hoàn thành thành công");
        } else {
          this.$toast.error(ds);
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async GAN_GO_CONGTRINH(kieu) {
      if (kieu == 1) {
        try {
          let dt = this.grvDSCTCG;
          if (!dt.length) {
            return this.$toast.error("Không có danh sách công trình để gán");
          }
          let t = this.$refs.gridDSCTCG.getSelectedRecords();
          if (!t.length) {
            return this.$toast.error("Chưa chọn danh sách để gán");
          }
          let dt_kt = t.map((e) => e.CONGTRINH_ID);
          let json_list = dt_kt.join(",");
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            `Bạn có chắc chắn muốn gán vào công trình ${this.vsohieu_luoi} không?`,
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) return;
          this.$root.loading(true);
          let res = await (
            await API.postGoGanCT(this.axios, {
              kieu: 1,
              congTrinhChaId: this.vcongtrinh_id_luoi,
              dsCongTrinh: json_list,
            })
          ).data.data;
          if (res[0].CODE == 1) {
            this.$toast.success(res[0].MSG);
            await this.TIMKIEM("");
            await this.NAP_DS_GANGO(1);
            await this.NAP_DS_GANGO(0);
          } else {
            this.$toast.error(res[0].MSG);
          }
        } catch (error) {
          this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      } else {
        try {
          let dt = this.grvDSCTDG;
          if (!dt.length) {
            return this.$toast.error("Không có danh sách công trình để gỡ");
          }
          let t = this.$refs.gridDSCTDG.getSelectedRecords();
          if (!t.length) {
            return this.$toast.error("Chưa chọn danh sách để gỡ");
          }
          let dt_kt = t.map((e) => e.CONGTRINH_ID);
          let json_list = dt_kt.join(",");
          let isConfirm = await this.$bvModal.msgBoxConfirm(
            `Bạn có chắc chắn muốn gỡ công trình ra khỏi công trình ${this.vsohieu_luoi} không?`,
            {
              okVariant: "success",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
              centered: true,
            }
          );
          if (!isConfirm) return;
          this.$root.loading(true);
          let res = await (
            await API.postGoGanCT(this.axios, {
              kieu: 0,
              congTrinhChaId: this.vcongtrinh_id_luoi,
              dsCongTrinh: json_list,
            })
          ).data.data;
          if (res[0].CODE == 1) {
            this.$toast.success(res[0].MSG);
            await this.TIMKIEM("");
            await this.NAP_DS_GANGO(1);
            await this.NAP_DS_GANGO(0);
          } else {
            this.$toast.error(res[0].MSG);
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(error.data.message);
        } finally {
          this.$root.loading(false);
        }
      }
    },
    btnGan() {
      this.GAN_GO_CONGTRINH(1);
    },
    btnGo() {
      this.GAN_GO_CONGTRINH(0);
    },
    async NAP_DS_GANGO(vkieu) {
      try {
        this.$root.loading(true);
        let ds = await (
          await API.getDSCTDCGan(this.axios, {
            kieu: vkieu,
            congTrinhId: this.vcongtrinh_id_luoi,
          })
        ).data.data;
        if (vkieu == 0) {
          this.grvDSCTDG = ds;
        } else {
          this.grvDSCTCG = ds;
        }
      } catch (error) {
        console.log(error);
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    async tabDSChanged(value) {
      if (value == this.tabActived.tabDSCTGan) {
        await this.NAP_DS_GANGO(1);
        await this.NAP_DS_GANGO(0);
      }
    },
    async ontxtMa_CTClick() {
      if (!this.gParams.dsQuyen.includes(this.DS_QUYEN_VT.QUYET_DOIMACT)) {
        return this.$toast.error("Bạn không có quyền đổi mã công trình");
      }
      try {
        this.$root.loading(true);
        var f_rm = this.$refs.frmNhapText;
        await f_rm.Load_frmNhapText();
        await f_rm.frmNhapText(
          "Bạn hãy nhập mã công trình mới thay thế mã " + this.vma_ct_luoi
        );
        // await f_rm.frmNhapText("Bạn hãy nhập mã công trình mới thay thế mã ");
        this.$refs.popup_frmNhapText.show();
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.$root.loading(false);
      }
    },
    XacNhan_frmNhapText() {},
    async frmNhapTextConfirm(value) {
      if (value) {
        try {
          this.$root.loading(true);
          let ds = await (
            await API.postDoiMaCT(this.axios, {
              congTrinhId: this.vcongtrinh_id_luoi,
              maCt: value.trim(),
            })
          ).data.data;
          if (ds == 1) {
            let vct_temp_id = this.vcongtrinh_id_luoi;
            await this.onbtnLayTT();
            await this.FocusGridViewRow(vct_temp_id);
            this.$toast.success("Đã cập nhật thành công");
          }
        } catch (error) {
          console.log(error);
          this.$toast.error(
            "Có lỗi khi đổi mã công trình " + error.data.message
          );
        } finally {
          this.$root.loading(false);
        }
      }
    },
    async onTreeDSCTSelected(args) {
      this.gParams.tvDoiTuongSelected = this.tvDSCT.find(
        (e) => e.CONGTRINH_ID == args.nodeData.id
      );
      console.log(
        "this.gParams.tvDoiTuongSelected = ",
        this.gParams.tvDoiTuongSelected
      );
      if (!this.enable.btnNhapMoi) {
        return;
      }
      try {
        let nodeSelected = this.gParams.tvDoiTuongSelected;
        if (nodeSelected["CONGTRINH_ID"] != "-1") {
          await this.NAP_DS_CONGTRINH_CT();
          await this.NAP_DS_CTVT();
          if (this.tabActived.currentTab == this.tabActived.tabDSCTGan) {
            await this.NAP_DS_GANGO(1);
            await this.NAP_DS_GANGO(0);
          }
        } else {
          let dt = this.tvDSCT;
          if (!dt.length) {
            await this.XOA_FORM();
            await this.KHOAMO_FORM(false);
            this.enable.btnNhapMoi = true;
            this.visible.btnXoaPhieu = false;
          } else {
            this.enable.btnNhapMoi = true;
            await this.XOA_FORM();
            await this.KHOAMO_FORM(false);
            this.visible.btnXoaPhieu = false;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    ontxtLocDTClick() {
      this.THAOTAC_NUT(3);
      // selectedNodes;
      let filterCellText = this.gParams.txtLocDT.trim();
      this.tvDSCT1.forEach((e) => {
        if (e.CONGTRINH_ID == 7) {
          console.log(e);
          e.isSelected = true;
          this.tvDSCT1 = [...this.tvDSCT1];
          // this.$refs.tvDoiTuong.selectedNodes = e;
          // console.log(this.$refs.tvDoiTuong);
          // this.$refs.tvDoiTuong.removeNodes();
        }
      });
      // let node = this.tvDSCT1.filter((e) => e.CONGTRINH_ID == 2);
      // console.log(node);
      // node.isSelected = true;

      // this.$refs.tvDoiTuong.selectedNodes = node;
      if (filterCellText.toString()) {
      }
      //       else
      //       {
      //           FilterNodeOperation
      //               operation = new FilterNodeOperation(filterCellText, colTenHT, true);
      //           tvDoiTuong.NodesIterator.DoOperation(operation);
      //       }

      //       if (filterCellText != "")
      //       {
      //           tvDoiTuong.ExpandAll();
      //           TreeListNode t = tvDoiTuong.FindNodeByFieldValue("TEN_HT", filterCellText);
      //           tvDoiTuong.FocusedNode = tvDoiTuong.Nodes.FirstNode;
      //           if (t != null)
      //           {
      //               tvDoiTuong.FocusedNode = t;
      //           }
      //       }
      //       else
      //       {
      //           tvDoiTuong.CollapseAll();
      //           tvDoiTuong.FocusedNode = tvDoiTuong.Nodes.FirstNode;
      //       }
    },
  },

  created: async function () {
    let self = this;
    // Fix css breaking layout
    this.interval = setInterval(function () {
      self.removeCssRule("/static/css/custom.css", ".row");
      self.removeCssRule("/static/css/custom.css", ".custom-select");
      self.removeCssRule("/static/css/style.css", ".input-group");
      self.removeCssRule("/static/css/style.css", ".form-control");
    }, 1000);
    if (this.$route.query.Tag) {
      this.Tag = this.$route.query.Tag;
    }
    if (this.Tag == "1") {
      this.visible.layoutDSCT = true;
      this.visible.tabDSCongTrinh = false;
      this.visible.tabDSCTGan = false;
    }
    await this.HIENTHI_GIAODIEN(0, "NOTHING");
    await this.LOAD_COMBO();
    await this.HIENTHE_TAB();
    this.gParams.load_first = false;
  },
  destroyed() {
    clearInterval(this.interval);
  },
};
</script>
