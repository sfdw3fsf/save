<template src="./CreateList.html"></template>

<script>
// import PopupTaoBangKe from "./components/PopupTaoBangKe/PopupTaoBangKe.vue";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "./CreateListAPI";
import moment from "moment";
import { LOAI_DV, Loai_NV, TRANGTHAI_BANGKE } from "@/const/enums/index.js";
import ActionTop from "@/components/kylq_components/ActionTop.vue";

export default {
  components: {
    DatePicker,
    ActionTop
  },
  data() {
    return {
      success_code: "BSS-00000000",
      pageSettings: { pageSize: 5 },
      selectionOptions: { type: "Multiple" },
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới",
          active: false,
          icon_class: "icon one-file-plus"
        },
        {
          id: "ghilai",
          name: "Ghi lại (F9)",
          active: false,
          icon_class: "icon one-save"
        },
        {
          id: "huy",
          name: "Hủy",
          active: false,
          icon_class: "icon nc-icon-glyph ui-1_circle-remove"
        },
        {
          id: "xoa",
          name: "Xóa BK",
          active: false,
          icon_class: "icon one-trash"
        },
        {
          id: "timkiem",
          name: "Tìm kiếm",
          active: true,
          icon_class: "icon one-search"
        },
        {
          id: "in",
          name: "In BN",
          active: true,
          icon_class: "icon one-print"
        }
      ],
      dateFormat: "DD/MM/YYYY",
      typeFormat: "date",
      tt_nd: {
        nhanvien_id: "",
        ngay_cn: "",
        donvi_dl_id: "",
        ma_nv: "",
        ma_nd: "",
        may_cn: "",
        phanvung_id: ""
      },
      bangke_id: 0,
      pstt: 0,
      dtpNgayTao: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      dtpDenNgay: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
      cboDaiVT: {
        selectedValue: -1,
        data: []
      },
      cboTramVT: {
        selectedValue: -1,
        data: []
      },
      cboNguoiNop: {
        selectedValue: -1,
        data: []
      },
      txtMaSo: "",
      txtTongCong: 0,
      txtTienBangChu: "Không đồng",
      vtungay: moment(new Date()).format("DD/MM/YYYY"),
      vdenngay: moment(new Date()).format("DD/MM/YYYY"),

      DSTbDaGan: {
        headers: [
          {
            fieldName: "chon",
            type: "checkbox",
            width: "39",
            maxWidth: "39",
            minWidth: "39",
            headerTextAlign: "center",
            textAlign: "center",
            allowResizing: "false",
            clipMode: "Ellipsis"
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày HT",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "tongtienFormat",
            headerText: "Tổng tiền",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "trangthai_hd",
            headerText: "Trạng thái HĐ",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          }
        ],
        selectItem: {},
        data: [],
        checked: []
      },
      DSTbChuaGan: {
        headers: [
          {
            fieldName: "chon",
            type: "checkbox",
            width: "39",
            maxWidth: "39",
            minWidth: "39",
            headerTextAlign: "center",
            textAlign: "center",
            allowResizing: "false",
            clipMode: "Ellipsis"
          },
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày HT",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "tongtienFormat",
            headerText: "Tổng tiền",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "trangthai_hd",
            headerText: "Trạng thái HĐ",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          }
        ],
        selectItem: {},
        data: [],
        checked: []
      },
      DSHDDaGan: {
        headers: [
          {
            fieldName: "chon",
            type: "checkbox",
            width: "39",
            maxWidth: "39",
            minWidth: "39",
            headerTextAlign: "center",
            textAlign: "center",
            allowResizing: "false",
            clipMode: "Ellipsis"
          },
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên khách hàng",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "diachi_kh",
            headerText: "Địa chỉ",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "tongtienFormat",
            headerText: "Tổng tiền",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày HT",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          }
        ],
        selectItem: {},
        data: [],
        checked: []
      },
      DSHDChuaGan: {
        headers: [
          {
            fieldName: "chon",
            type: "checkbox",
            width: "39",
            maxWidth: "39",
            minWidth: "39",
            headerTextAlign: "center",
            textAlign: "center",
            allowResizing: "false",
            clipMode: "Ellipsis"
          },
          {
            fieldName: "ma_gd",
            headerText: "Mã GD",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên khách hàng",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "diachi_kh",
            headerText: "Địa chỉ",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "tongtienFormat",
            headerText: "Tổng tiền",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          },
          {
            fieldName: "ngay_ht",
            headerText: "Ngày HT",
            allowFiltering: true,
            allowSorting: false,
            width: 170
          }
        ],
        selectItem: {},
        data: []
      },
      DSBangKe: {
        headers: [
          {
            fieldName: "stt",
            headerText: "STT",
            allowFiltering: true,
            allowSorting: false,
            width: "60",
            maxWidth: "60",
            minWidth: "60"
          },
          {
            fieldName: "ma_bk",
            headerText: "Mã bảng kê",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "ngay_bk",
            headerText: "Ngày lập",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "ma_nv_lap",
            headerText: "Người lập",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "ma_nv_nop",
            headerText: "Người nộp",
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: "tongtienFormat",
            headerText: "Tổng tiền",
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selectItem: {},
        data: []
      }
    };
  },
  computed: {
    lblTongTB_CG() {
      return this.DSTbChuaGan.data.length;
    },
    lblTongTB_DG() {
      return this.DSTbDaGan.data.length;
    },
    lblTongHD_CG() {
      return this.DSHDChuaGan.data.length;
    },
    lblTongHD_DG() {
      return this.DSHDDaGan.data.length;
    },
    formatTien() {
      return this.txtTongCong
        .toString()
        .split("")
        .reverse()
        .reduce((prev, next, index) => {
          return (index % 3 ? next : next + ",") + prev;
        });
    },
    kiemTraThongTinNguoiDung(){
      if( this.tt_nd.donvi_dl_id == 0 || this.tt_nd.donvi_dl_id == null){
        return false;
      }
      return true;
    }
  },
  mounted() {
    this.tt_nd.nhanvien_id = this.$root.token.getNhanVienID();
    this.tt_nd.ngay_cn = this.$auth.getNgayCapNhat();
    this.tt_nd.donvi_dl_id = this.$root.token.getDonViDuLieuID();
    this.tt_nd.ma_nv = this.$root.token.getMaNhanVien();
    this.tt_nd.ma_nd = this.$root.token.getUserName();
    this.tt_nd.phanvung_id = this.$root.token.getPhanVungID();
    this.setButton(-1);
    this.loadData();
  },
  methods: {
    async loadData() {
      this.loading(true);
      this.tt_nd.may_cn = await this.$root.token.getMachine();
      this.loading(false);
      this.loadCboDaiVT();
    },
    async loadCboDaiVT() {
      const res = await api.getDonVi(this.axios, {
        loai_donvi_id: LOAI_DV.DONVIQL_LD // DONVIQL_LD = 3
      });
      this.cboDaiVT.data = res.data.data;
      this.cboDaiVT.selectedValue = this.tt_nd.donvi_dl_id;
      this.loadCboTramVT();
    },
    async loadCboTramVT() {
      const res = await api.getDonViTheoLoaiDV(this.axios, {
        p_donvi_id: this.cboDaiVT.selectedValue,
        p_loaidv_id: LOAI_DV.TRAM_VT, //TRAM_VT = 5,
        p_nhanvien_id: this.tt_nd.nhanvien_id

      });
      this.cboTramVT.data = res.data.data;
      if (this.cboTramVT.data.length > 0) {
        this.cboTramVT.selectedValue = this.cboTramVT.data[0].donvi_id;
      }
      this.loadCboNguoiNop();
      this.hienThiBangKe();
    },
    async loadCboNguoiNop() {
      const res = await api.getNhanVienTheoDVLoaiDV(this.axios, {
        loainv_id: Loai_NV.THICONG_DAYMAY, // THICONG_DAYMAY = 6
        donvi_id: this.cboTramVT.selectedValue
      });
      this.cboNguoiNop.data = res.data.data;
      if (res.data.data.length > 0) {
        this.cboNguoiNop.selectedValue = res.data.data[0].nhanvien_id;
      } else {
        this.cboNguoiNop.selectedValue = "";
      }
    },

    async loadDSHDDaGan(bangke_id) {
      const res = await api.getDSHDDaGan(this.axios, {
        vdsbangke_id: bangke_id
      });
      if (res.data.data.length > 0) {
        this.DSHDDaGan.data = res.data.data;
        this.DSHDDaGan.data.forEach(element => {
          element.tongtienFormat = this.formatCurrency(element.tongtien);
        });
      } else {
        this.DSHDDaGan.data = [];
        this.txtTongCong = 0;
      }
    },
    async loadDSHDChuaGan() {
      this.loading(true);
      const res = await api.getDSHDChuaGan(this.axios, {
        vdonvi_id: this.cboTramVT.selectedValue,
        vdenngay: moment(this.dtpDenNgay, "DD/MM/YYYY HH:mm:ss").format(
          "DD/MM/YYYY"
        )
      });
      if (res.data.data.length > 0) {
        this.DSHDChuaGan.data = res.data.data;
        this.DSHDChuaGan.data.forEach(element => {
          element.tongtienFormat = this.formatCurrency(element.tongtien);
        });
      } else {
        this.DSHDChuaGan.data = [];
      }
      this.loading(false);
    },

    async loadDSTBChuaGan(vdshdkh_id) {
      const res = await api.getDSTBChuaGan(this.axios, {
        vdshdkh_id: vdshdkh_id
      });
      if (res.data.data.length > 0) {
        this.DSTbChuaGan.data = res.data.data;
        this.DSTbChuaGan.data.forEach(element => {
          element.tongtienFormat = this.formatCurrency(element.tongtien);
        });
      } else {
        this.DSTbChuaGan.data = [];
      }
    },
    async loadDSTBDaGan(vbangke_id, dshdkh_id) {
      // if(!vbangke_id){
      //   this.DSTbDaGan.data = [];
      //   this.txtTongCong = 0;
      //   return;
      // }
      const res = await api.getDSTBDaGan(this.axios, {
        vdsbangke_id: vbangke_id ? vbangke_id : "-1",
        vdshdkh_id: dshdkh_id
      });
      if (res.data.data.length > 0) {
        this.DSTbDaGan.data = res.data.data;
        let tongtien = 0;
        this.DSTbDaGan.data.forEach(item => {
          tongtien += item.tongtien;
          item.tongtienFormat = this.formatCurrency(item.tongtien);
        });
        this.txtTongCong = tongtien;
      } else {
        this.DSTbDaGan.data = [];
        this.txtTongCong = 0;
      }
    },

    async tienBangChu(num) {
      const res = await this.readMoney(num);
      this.txtTienBangChu = res;
    },
    readMoney: async function(num) {
      try {
        if (num == "") {
          return "Không đồng";
        }
        if (num === 0 || num === "0") {
          return "Không đồng";
        }
        let input = { money: num };
        let response = await api.getMoneyToText(this.axios, input);
        if (
          response.data.error == "200" &&
          response.data.error_code == "BSS-00000000"
        ) {
          return response.data.data;
        }
        return num;
      } catch (ex) {
        return num;
      }
    },

    async hienThiBangKe() {
      const res = await api
        .getDSBKDonVi(this.axios, {
          vdonvi_id: this.cboTramVT.selectedValue
            ? this.cboTramVT.selectedValue
            : 0,
          vma_bk: 0,
          vtungay: this.vtungay,
          vdenngay: this.vdenngay,
          vttbk_id: TRANGTHAI_BANGKE.MOI
        })
        .catch(err => {
          this.loading(false);
          this.ShowError("Lỗi");
        });
      if (res.data.error_code != this.success_code) {
        this.loading(false);
        this.DSBangKe.data = [];
        this.DSHDDaGan.data = [];
        this.loadDSHDChuaGan();
        this.DSTbChuaGan.data = [];
        this.DSTbDaGan.data = [];

        this.txtTongCong = 0;
        this.txtMaSo = "";
        this.stt = 0;
        this.dtpNgayTao = this.tt_nd.ngay_cn;
        this.$refs.dialogObjTaoBangKe.hide();
        this.setButton(2);
        return;
      }
      let dsBK = res.data.data;
      if (dsBK.length > 0) {
        this.DSBangKe.data = dsBK;
        this.DSBangKe.data.forEach(element => {
          element.tongtienFormat = this.formatCurrency(element.tongtien);
        });
      }
    },

    cboDaiVTChanged() {
      this.loadCboTramVT();
    },
    cboTramVTChanged() {
      this.loadCboNguoiNop();
      this.hienThiBangKe();
      // this.loadDSHDChuaGan();
    },
    btnChapNhanClick() {
      this.hideDialogshoObjTaoBangKe();
      this.hienThiBangKe();
    },

    // table selection change
    dSBangKeSelectionChange(val) {
      if (val.length == 0) {
        return;
      }
      this.DSBangKe.selectItem = val[0];
      this.txtMaSo = val[0].ma_bk;
      this.txtTongCong = val[0].tongtien; // Format.FM_TIEN_VND
      this.tienBangChu(this.txtTongCong);
      this.dtpNgayTao = moment(val[0].ngay_bk, "YYYY/MM/DD HH:mm:ss").format(
        "DD/MM/YYYY HH:mm:ss"
      );
      this.cboNguoiNop.selectedValue = val[0].nhanvien_id;
      this.stt = val[0].stt;

      this.loadDSHDDaGan(this.DSBangKe.selectItem.bangke_id);
      this.loadDSHDChuaGan();
      this.loadDSTBDaGan(this.DSBangKe.selectItem.bangke_id, "");
      let vdshdkh_id = this.DSHDDaGan.data
        .filter(i => i.chon == 1)
        .map(i => i.hdkh_id)
        .toString();
      this.loadDSTBChuaGan(vdshdkh_id ? vdshdkh_id : "-1");

      this.setButton(3);
    },

    dSHDDaGan_selectedItemsChanged(dataKeys) {
      if (dataKeys.length == 0) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSHDDaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 1;
          }
        });
      } else {
        this.DSHDDaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 1;
          } else {
            item.chon = 0;
          }
        });
      }

      let vdshdkh_id = this.DSHDDaGan.data
        .filter(i => i.chon == 1)
        .map(i => i.hdkh_id)
        .toString();
      this.loadDSTBDaGan(
        this.DSBangKe.selectItem.bangke_id,
        vdshdkh_id ? vdshdkh_id : "-1"
      );
      this.loadDSTBChuaGan(vdshdkh_id ? vdshdkh_id : "-1");
    },
    DSHDDaGan_RowDeSelected(dataKeys) {
      if (!dataKeys.data) {
        return;
      }
      if(!dataKeys.isInteracted){
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSHDDaGan.data.forEach(function(item) {
          if (dataKeys.data.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 0;
          }
        });
      } else {
        this.DSHDDaGan.data.forEach(function(item) {
          if (item.hdkh_id == dataKeys.data.hdkh_id) {
            item.chon = 0;
          }
        });
      }
      let vdshdkh_id = this.DSHDDaGan.data
        .filter(i => i.chon == 1)
        .map(i => i.hdkh_id)
        .toString();
      this.loadDSTBDaGan(
        this.DSBangKe.selectItem.bangke_id,
        vdshdkh_id ? vdshdkh_id : "-1"
      );
      this.loadDSTBChuaGan(vdshdkh_id ? vdshdkh_id : "-1");
    },

    dSHDChuaGan_selectedItemsChanged(dataKeys) {
      if (dataKeys.length == 0) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSHDChuaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 1;
          }
        });
      } else {
        this.DSHDChuaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 1;
          } else {
            item.chon = 0;
          }
        });
      }
    },
    DSHDChuaGan_RowDeSelected(dataKeys) {
      if (!dataKeys.data) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSHDChuaGan.data.forEach(function(item) {
          if (dataKeys.data.filter(x => x.hdkh_id == item.hdkh_id).length > 0) {
            item.chon = 0;
          }
        });
      } else {
        this.DSHDChuaGan.data.forEach(function(item) {
          if (item.hdkh_id == dataKeys.data.hdkh_id) {
            item.chon = 0;
          }
        });
      }
    },

    dSTBChuaGan_selectedItemsChanged(dataKeys) {
      if (dataKeys.length == 0) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSTbChuaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 1;
          }
        });
      } else {
        this.DSTbChuaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 1;
          } else {
            item.chon = 0;
          }
        });
      }
    },
    DSTBChuaGan_RowDeSelected(dataKeys) {
      if (!dataKeys.data) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSTbChuaGan.data.forEach(function(item) {
          if (dataKeys.data.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 0;
          }
        });
      } else {
        this.DSTbChuaGan.data.forEach(function(item) {
          if (item.ma_tb == dataKeys.data.ma_tb) {
            item.chon = 0;
          }
        });
      }
    },

    dSTBDaGan_selectedItemsChanged(dataKeys) {
      if (dataKeys.length == 0) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSTbDaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 1;
          }
        });
      } else {
        this.DSTbDaGan.data.forEach(function(item) {
          if (dataKeys.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 1;
          } else {
            item.chon = 0;
          }
        });
      }
    },
    DSTBDaGan_RowDeSelected(dataKeys) {
      if (!dataKeys.data) {
        return;
      }
      if (Array.isArray(dataKeys.data)) {
        this.DSTbDaGan.data.forEach(function(item) {
          if (dataKeys.data.filter(x => x.ma_tb == item.ma_tb).length > 0) {
            item.chon = 0;
          }
        });
      } else {
        this.DSTbDaGan.data.forEach(function(item) {
          if (item.ma_tb == dataKeys.data.ma_tb) {
            item.chon = 0;
          }
        });
      }
    },

    async capNhat() {
      this.loading(true);
      if (this.actions.find(a => a.id == "nhapmoi").active == false) {
        let bangKeObj = await this.taoDuLieu(true);
        if (!bangKeObj) {
          return;
        }
        let jsonData = {
          nhapmoi: 1,
          phanvung_id: this.tt_nd.phanvung_id,
          js_bangke: [
            {
              BANGKE_ID: bangKeObj.BANGKE_ID,
              MA_BK: bangKeObj.MA_BK,
              NGAY_BK: bangKeObj.NGAY_BK,
              NGUOITAO_ID: bangKeObj.NGUOITAO_ID,
              NHANVIEN_ID: bangKeObj.NHANVIEN_ID,
              TONGTIEN: bangKeObj.TONGTIEN,
              TTBK_ID: bangKeObj.TTBK_ID,
              NGUOINHAN_ID: bangKeObj.NGUOINHAN_ID,
              NGAY_NHAN: bangKeObj.NGAY_NHAN,
              STT: bangKeObj.STT,
              MAY_CN: bangKeObj.MAY_CN,
              NGAY_CN: bangKeObj.NGAY_CN,
              NGUOI_CN: bangKeObj.NGUOI_CN,
              PHANVUNG_ID: bangKeObj.PHANVUNG_ID
            }
          ],
          bangke_id: bangKeObj.BANGKE_ID,
          ds_tb_dagan: []
        };
        let tb_dg = this.DSTbDaGan.data.map(i => i.hdtb_id);
        tb_dg.forEach(hdtb_id => {
          jsonData.ds_tb_dagan.push({ hdtb_id: hdtb_id });
        });
        const res = await api.capNhatBangKeID(this.axios, {
          ds_para: JSON.stringify(jsonData)
        });
        this.loading(false);
        if (res.data.error_code != "BSS-00000000") {
          this.ShowError(res.data.message);
          return;
        }
        this.ShowSuccess(
          "Thêm mới bảng kê vào cơ sở dữ liệu tỉnh thành công !"
        );
        this.hienThiBangKe();
        this.setButton(3);
        this.loading(false);
      } else {
        if (this.DSBangKe.data.length == 0 || !this.DSBangKe.selectItem) {
          this.ShowAlert("Không có bảng kê để cập nhật !");
          return;
        }
        if (this.DSBangKe.selectItem.nguoitao_id != this.tt_nd.nhanvien_id) {
          this.ShowAlert("Bạn không được sửa bảng kê do người khác tạo !");
          return;
        }
        let bangKeObj = await this.taoDuLieu(false);
        let jsonData = {
          nhapmoi: 0,
          js_bangke: [
            {
              BANGKE_ID: bangKeObj.BANGKE_ID,
              MA_BK: bangKeObj.MA_BK,
              NGAY_BK: bangKeObj.NGAY_BK,
              NGUOITAO_ID: bangKeObj.NGUOITAO_ID,
              NHANVIEN_ID: bangKeObj.NHANVIEN_ID,
              TONGTIEN: bangKeObj.TONGTIEN,
              TTBK_ID: bangKeObj.TTBK_ID,
              NGUOINHAN_ID: bangKeObj.NGUOINHAN_ID,
              NGAY_NHAN: bangKeObj.NGAY_NHAN,
              STT: bangKeObj.STT,
              MAY_CN: bangKeObj.MAY_CN,
              NGAY_CN: bangKeObj.NGAY_CN,
              NGUOI_CN: bangKeObj.NGUOI_CN,
              PHANVUNG_ID: bangKeObj.PHANVUNG_ID
            }
          ],
          bangke_id: bangKeObj.BANGKE_ID,
          ds_tb_dagan: []
        };
        let tb_dg = this.DSTbDaGan.data.map(i => i.hdtb_id);
        tb_dg.forEach(hdtb_id => {
          jsonData.ds_tb_dagan.push({ hdtb_id: hdtb_id });
        });
        const res = await api.capNhatBangKeID(this.axios, {
          ds_para: JSON.stringify(jsonData)
        });
        this.loading(false);
        if (res.data.error != 200) {
          this.ShowError("Có lỗi");
          return;
        }
        this.ShowSuccess(
          "Cập nhật bảng kê vào cơ sở dữ liệu tỉnh thành công !"
        );
        this.hienThiBangKe();
        this.setButton(3);
      }
      this.loading(false);
    },
    async taoDuLieu(themMoi) {
      if (!this.dtpNgayTao) {
        return;
      }
      var bangKeObj = {
        STT: "",
        MA_BK: "",
        BANGKE_ID: "",
        // NGAY_BK: moment(this.dtpNgayTao, "DD/MM/YYYY").format(
        //   "DD/MM/YYYY HH:mm:ss"
        // ),
        NGAY_BK: this.dtpNgayTao,
        NGUOITAO_ID: this.tt_nd.nhanvien_id,
        NHANVIEN_ID: this.cboNguoiNop.selectedValue,
        TONGTIEN: this.txtTongCong,
        TTBK_ID: 1,
        NGAY_CN: moment(this.tt_nd.ngay_cn, "DD/MM/YYYY").format(
          "DD/MM/YYYY HH:mm:ss"
        ),
        NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: this.tt_nd.may_cn,
        PHANVUNG_ID: this.tt_nd.phanvung_id
      };
      // add
      if (themMoi) {
        const resKey = await api.getKeys(this.axios, { keyname: "BANGKE_ID" });
        if (resKey.data.data) {
          bangKeObj.BANGKE_ID = resKey.data.data;
        } else {
          this.ShowError("Có lỗi");
          return;
        }
        const res = await api.getMaxStt(this.axios, {
          vnhanvien_id: this.cboNguoiNop.selectedValue,
          vngaytao: moment(this.dtpNgayTao, "DD/MM/YYYY HH:mm:ss").format(
            "DD/MM/YYYY"
          )
        });
        if (!res.data.error == 200) {
          this.ShowError("Có lỗi");
          return;
        }
        if (res.data.data[0].stt != 0) {
          bangKeObj.STT = parseInt(res.data.data[0].stt) + 1;
          if (bangKeObj.STT.toString().length == 1) {
            bangKeObj.MA_BK =
              this.tt_nd.ma_nv.toString() +
              moment(this.dtpNgayTao, "DD/MM/YYYY HH:mm:ss").format(
                "YYYYMMDD"
              ) +
              "00" +
              bangKeObj.STT;
          }
          if (bangKeObj.STT.toString().length == 2) {
            bangKeObj.MA_BK =
              this.tt_nd.ma_nv.toString() +
              moment(this.dtpNgayTao, "DD/MM/YYYY HH:mm:ss").format(
                "YYYYMMDD"
              ) +
              "0" +
              bangKeObj.STT;
          }
          if (bangKeObj.STT.toString().length == 3) {
            bangKeObj.MA_BK =
              this.tt_nd.ma_nv.toString() +
              moment(this.dtpNgayTao, "DD/MM/YYYY HH:mm:ss").format(
                "YYYYMMDD"
              ) +
              bangKeObj.STT;
          }
        } else {
          bangKeObj.STT = 1;
          bangKeObj.MA_BK =
            this.tt_nd.ma_nv.toString() +
            moment(this.dtpNgayTao, "DD/MM/YYYY HH:mm:ss").format("YYYYMMDD") +
            "001";
        }
      } else {
        // edit
        bangKeObj.BANGKE_ID = this.DSBangKe.selectItem.bangke_id;
        bangKeObj.MA_BK = this.txtMaSo.toString().trim();
        bangKeObj.STT = this.stt;
      }
      return bangKeObj;
    },
    nhapMoi() {
      this.setButton(1);
      this.txtMaSo = "";
      this.txtTongCong = 0;
      this.txtTienBangChu = "Không đồng";
      this.dtpNgayTao = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.DSHDDaGan.data = [];
      this.DSHDDaGan.selectItem = {};

      this.DSHDChuaGan.data = [];
      this.DSHDChuaGan.selectItem = {};

      this.DSTbDaGan.data = [];
      this.DSTbDaGan.selectItem = {};

      this.DSTbChuaGan.data = [];
      this.DSTbChuaGan.selectItem = {};
      this.DSBangKe.selectItem = {};
      this.loadDSHDChuaGan();
    },
    ghiLai() {
      this.capNhat();
    },
    huy() {
      this.setButton(2);
      this.txtMaSo = "";
      this.txtTongCong = 0;
      this.txtTienBangChu = "Không đồng";
      this.dtpNgayTao = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.DSHDDaGan.data = [];
      this.DSHDDaGan.selectItem = {};

      this.DSHDChuaGan.data = [];
      this.DSHDChuaGan.selectItem = {};

      this.DSTbDaGan.data = [];
      this.DSTbDaGan.selectItem = {};

      this.DSTbChuaGan.data = [];
      this.DSTbChuaGan.selectItem = {};
      this.loadDSHDChuaGan();
    },
    async xoa() {
      if (this.DSBangKe.data.length == 0) {
        return;
      }
      if (this.DSBangKe.selectItem.nguoitao_id != this.tt_nd.nhanvien_id) {
        this.ShowAlert("Bạn không được xóa bảng kê do người khác tạo !");
        return;
      }
      const res = await api.xoaBanKe(this.axios, {
        bangke_id: this.DSBangKe.selectItem.bangke_id
      });
      if (res.data.error == 200) {
        this.ShowSuccess(
          "Xóa bảng kê trên dữ liệu viễn thông tỉnh thành công !"
        );
        this.hienThiBangKe();
        this.setButton(1);
        return;
      } else {
        this.ShowAlert(res.data.message);
      }
    },

    timKiem() {},
    in() {},

    // button action
    moveRight() {
      if (this.DSHDDaGan.data.length == 0) {
        return;
      }
      this.DSHDDaGan.data.forEach((item, index) => {
        if (item.chon == 1) {
          let tmp = {...item}
          tmp.chon = 0;
          this.DSHDChuaGan.data.push(tmp);
        }
      });
      this.DSHDDaGan.data = this.DSHDDaGan.data.filter(i => i.chon == 0);
      let vdshdkh_id = this.DSHDDaGan.checked.map(i => i.hdkh_id).toString();
      this.loadDSTBChuaGan(vdshdkh_id ? vdshdkh_id : "-1");
      vdshdkh_id = this.DSHDDaGan.data.map(i => i.hdkh_id).toString();
      this.loadDSTBDaGan(
        this.DSBangKe.selectItem.bangke_id,
        vdshdkh_id ? vdshdkh_id : "-1"
      );
    },
    moveRightAll() {
      if (this.DSHDDaGan.data == 0) {
        return;
      }
      this.DSHDDaGan.data.forEach(item => {
        this.DSHDChuaGan.data.push(item);
      });
      this.DSHDDaGan.data = [];

      this.loadDSTBChuaGan("-1");
      this.loadDSTBDaGan(this.DSBangKe.selectItem.bangke_id, "-1");
    },
    moveLeft() {
      if (this.DSHDChuaGan.data.length == 0) {
        return;
      }
      this.DSHDChuaGan.data.forEach((item, index) => {
        if (item.chon == 1) {
          let tmp = {...item}
          tmp.chon = 0;
          this.DSHDDaGan.data.push(tmp);
        }
      });
      this.DSHDChuaGan.data = this.DSHDChuaGan.data.filter(i => i.chon == 0);
    },
    moveLeftAll() {
      if (this.DSHDChuaGan.data == 0) {
        return;
      }
      this.DSHDChuaGan.data.forEach(item => {
        this.DSHDDaGan.data.push(item);
      });
      this.DSHDChuaGan.data = [];
    },

    moveRightTB() {
      if (this.DSTbDaGan.data.length == 0) {
        return;
      }
      this.DSTbDaGan.data.forEach((item, index) => {
        if (item.chon == 1) {
          let tmp = {...item}
          tmp.chon = 0;
          this.DSTbChuaGan.data.push(tmp);
        }
      });
      this.DSTbDaGan.data = this.DSTbDaGan.data.filter(i => i.chon == 0);

      this.hienThiTien();
      this.tienBangChu(this.txtTongCong);
    },
    moveRightAllTB() {
      if (this.DSTbDaGan.data == 0) {
        return;
      }
      this.DSTbDaGan.data.forEach(item => {
        this.DSTbChuaGan.data.push(item);
      });

      this.DSTbDaGan.data = [];
      this.hienThiTien();
    },
    moveLeftTB() {
      if (this.DSTbChuaGan.data.length == 0) {
        return;
      }
      this.DSTbChuaGan.data.forEach((item, index) => {
        if (item.chon == 1) {
          let tmp = {...item}
          tmp.chon = 0;
          this.DSTbDaGan.data.push(tmp);
        }
      });
      this.DSTbChuaGan.data = this.DSTbChuaGan.data.filter(i => i.chon == 0);

      this.hienThiTien();
      this.tienBangChu(this.txtTongCong);
    },
    moveLeftAllTB() {
      if (this.DSTbChuaGan.data == 0) {
        return;
      }
      this.DSTbChuaGan.data.forEach(item => {
        this.DSTbDaGan.data.push(item);
      });
      this.DSTbChuaGan.data = [];
      this.hienThiTien();
      this.tienBangChu(this.txtTongCong);
    },

    hienThiTien() {
      this.txtTongCong = this.DSTbDaGan.data.reduce(
        (n, { tongtien }) => n + tongtien,
        0
      );
    },

    onActionClick(action) {
      if (action.id == "nhapmoi") {
        this.nhapMoi();
      } else if (action.id == "ghilai") {
        this.ghiLai();
      } else if (action.id == "huy") {
        this.huy();
      } else if (action.id == "xoa") {
        this.xoa();
      } else if (action.id == "timkiem") {
        this.timKiem();
      } else if (action.id == "in") {
        this.in();
      }
    },
    showDialogshoObjTaoBangKe() {
      this.$refs.dialogObjTaoBangKe.show();
    },
    hideDialogshoObjTaoBangKe() {
      this.$refs.dialogObjTaoBangKe.hide();
    },

    formatCurrency(num) {
      return num
        .toString()
        .split("")
        .reverse()
        .reduce((prev, next, index) => {
          return (index % 3 ? next : next + ",") + prev;
        });
    },

    setButton(kieu) {
      this.actions.forEach(x => {
        x.active = false;
      });
      if (kieu == -1) {
        this.actions[
          this.actions.findIndex(x => x.id == "ghilai")
        ].active = true;
        this.actions[this.actions.findIndex(x => x.id == "huy")].active = true;
        this.actions[this.actions.findIndex(x => x.id == "timkiem")].active = true;
        this.actions[this.actions.findIndex(x => x.id == "in")].active = true;
      }
      if (kieu == 0) {
        this.actions[
          this.actions.findIndex(x => x.id == "nhapmoi")
        ].active = true;
      }
      if (kieu == 1) {
        this.actions[
          this.actions.findIndex(x => x.id == "ghilai")
        ].active = true;
        this.actions[this.actions.findIndex(x => x.id == "huy")].active = true;
      }
      if (kieu == 2) {
        this.actions[
          this.actions.findIndex(x => x.id == "nhapmoi")
        ].active = true;
        this.actions[this.actions.findIndex(x => x.id == "xoa")].active = true;
      }
      if (kieu == 3) {
        this.actions.forEach(x => {
          if (
            x.id == "nhapmoi" ||
            x.id == "xoa" ||
            x.id == "ghilai" ||
            x.id == "huy"
          ) {
            x.active = true;
          }
        });
      }
    },
    ShowAlert: function(msg) {
      this.$toast.warning(msg);
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg);
    },
    ShowError: function(msg) {
      this.$toast.error(msg);
    }
  }
};
</script>

<style></style>
