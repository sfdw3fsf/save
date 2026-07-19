<template src='./popupLoaiKhoMaKho.html'></template>
<style scoped src='./popupLoaiKhoMaKho.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/loaikhomakho";
import Vue from "vue";

import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { Query, Predicate } from "@syncfusion/ej2-data";

Vue.use(DatePickerPlugin);

export default {
  components: { breadcrumb },
  name: "popupLoaiKhoMaKho",
  mounted() {},

  data() {
    return {
      tagForm: "",
      Tag: "",
      loaict: 0,
      kieuphieu: 0,
      dt_dasua: [],
      loaikho: 0,
      khoid: 0,
      xtraTabControl1: 0,
      xacnhan: 0,

      Enabled: {
        tsbtnNew: true,
        tsbtnSave: true,
        tsbtnDel: true,
        tsbtnCancel: true,
        tabLoaiKho: true,
        tabMaKho: true,
      },

      Visible: {
        tsbtnXacNhan: false,
        tabEditLKMK: false,
        tabLoaiKho: true,
        tabMaKho: true,
        tabTKDU: true,

        tsbtnNew: true,
        tsbtnSave: true,
        tsbtnCancel: true,
        tsbtnDel: true,
      },

      tabLoaiKho: {
        txtMaLK: "",
        txtTenLK: "",
        txtGhiChuLK: "",
        DSLoaiKho: [],
        DSLKMD: [],
        DSMucDich: [],
        ckbXemTonKho: true,
        ckbTonKho: false,
        DSMucDichChecked: [],
        DSLKMDChecked: [],
        LoaiKhoSelected: {},
      },

      tabMaKho: {
        txtMaKhoKT: "",
        txtTenKhoKT: "",
        txtGhiChuLK: "",
        txtTaiKhoan: "",
        DSKhoKT: [],
        DSKTLKLeft: [],
        DSKTLK: [],
        DSKTLKChecked: [],
        DSKTLKLeftChecked: [],
        KhoKTSelected: {},
      },

      tabTKDU: {
        DSLoaiCT: [],
        cboLoaiCT: 0,
        DSKieuPhieu: [],
        cboKieuPhieu: 0,
        DSTKDU_DaGan: [],
        DSTKDU_DaGan_Checked: [],

        DSTKDU_ChuaGan: [],
        DSTKDU_ChuaGan_Checked: [],
      },

      tabLKMK: {
        DSKieuPhieu: [],
        DSChatLuong: [],
        cboKieuP: 0,
        cboChatLuong: 0,
        DSVatTu: [],
        cboChatluongRepo: [],
        DSLK: [],
        cboLK: 0,
        cboMKTD: 0,
        DSMKTD: [],
        DSVatTu_Checked: [],
        DSDongTB: [],
        cboDongTB: 0,
        cboHeThong: 0,
        DSHeThong: [],
      },

      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",

      onFilterLoaiCT: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("loaiCt", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterKieuPhieu: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("mucDich", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterChatLuong: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate(
            "chatLuong",
            "contains",
            e.text.trim(),
            true
          );
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterDongTbi: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate(
            "tenDongTbi",
            "contains",
            e.text.trim(),
            true
          );
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterLoaiKho: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("tenLk", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterMaKhoTD: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate("maKhoTd", "contains", e.text.trim(), true);
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
      onFilterHeThong: function (e) {
        var query = new Query();
        let predicate;

        if (e.text !== "") {
          predicate = new Predicate(
            "tenHeThong",
            "contains",
            e.text.trim(),
            true
          );
          query.where(predicate);
        }

        e.updateData(this.dataSource, query);
      },
    };
  },

  computed: {
    ...mapState("loaikhomakho", statePropertyName),
    ...mapGetters("loaikhomakho", getterName),
  },
  methods: {
    ...mapActions("loaikhomakho", actionName),
    ...mapMutations("loaikhomakho", mutationName),

    async OpenDialog() {
      try {
        this.loading(true);
        await this.frmLoaiKho_MaKho_Load();
        this.$refs.popupLoaiKhoMaKho.show();
      } catch (error) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async frmLoaiKho_MaKho_Load() {
      this.tagForm = this.Tag;
      await this.setButton(0);
      if (this.tagForm == "2") {
        var Text = "Chọn loại kho - mã kho";
        this.xtraTabControl1 = 3;
        this.Visible.tabEditLKMK = true;
        this.Visible.tabLoaiKho = false;
        this.Visible.tabMaKho = false;
        this.Visible.tabTKDU = false;

        this.Visible.tsbtnXacNhan = true;
        this.Visible.tsbtnSave = false;
        this.Visible.tsbtnCancel = false;
        this.Visible.tsbtnDel = false;
        this.Visible.tsbtnNew = false;

        var loai = this.loaict == 99 ? 1 : this.loaict;
        this.tabLKMK.DSKieuPhieu = await this.getDSKieuPhieu(loai);
        console.log(this.tabLKMK.DSKieuPhieu, " = DSKieuPhieu");
        this.tabLKMK.cboKieuP = this.kieuphieu;
        var DSChatLuong = await this.getDSChatLuong();
        this.tabLKMK.DSChatLuong = DSChatLuong;
        this.tabLKMK.cboChatluongRepo = DSChatLuong;
        console.log(this.tabLKMK.DSChatLuong, " = this.tabLKMK.DSChatLuong ");
        if (this.tabLKMK.DSChatLuong.length > 0) {
          this.tabLKMK.cboChatLuong = this.tabLKMK.DSChatLuong[0].chatLuongId;
        }
        this.tabLKMK.DSVatTu = this.dt_dasua;
      }
    },
    clear() {
      if (this.xtraTabControl1 == 0) {
        this.tabLoaiKho.txtMaLK = "";
        (this.tabLoaiKho.txtTenLK = ""), (this.tabLoaiKho.txtGhiChuLK = "");
      } else {
        this.tabMaKho.txtMaKhoKT = "";
        (this.tabMaKho.txtTenKhoKT = ""), (this.tabMaKho.txtGhiChuKT = "");
        this.tabMaKho.txtTaiKhoan = "";
      }
    },
    async setButton(kieu) {
      if (kieu == 0) {
        //Khoi tao luc load form
        this.Enabled.tsbtnSave = false;
        this.Enabled.tsbtnDel = false;

        this.tabLoaiKho.DSLoaiKho = [];
        this.tabLoaiKho.DSMucDich = [];
        this.tabLoaiKho.DSLKMD = [];

        this.tabMaKho.DSKhoKT = [];
        this.tabMaKho.DSKTLKLeft = [];
        this.tabMaKho.DSKTLK = [];
      } else if (kieu == 1) {
        //Bam them moi

        this.Enabled.tsbtnNew = false;
        this.Enabled.tsbtnDel = false;
        this.Enabled.tsbtnSave = true;
        this.Enabled.tsbtnCancel = true;
        this.clear();
        this.Enabled.tabLoaiKho = this.xtraTabControl1 == 0;
        this.Enabled.tabMaKho = this.xtraTabControl1 != 0;
      } else if (kieu == 2) {
        //Bam Huy
        this.Enabled.tsbtnNew = true;
        this.Enabled.tsbtnDel = true;
        this.Enabled.tsbtnSave = false;
        this.Enabled.tsbtnCancel = false;
        this.Enabled.tabLoaiKho = true;
        this.Enabled.tabMaKho = true;
        this.clear();
      } else if (kieu == 3) {
        this.Enabled.tsbtnNew = true;
        this.Enabled.tsbtnDel = true;
        this.Enabled.tsbtnSave = true;
        this.Enabled.tsbtnCancel = false;
        this.Enabled.tabLoaiKho = true;
        this.Enabled.tabMaKho = true;
      }
    },
    xtraTabControl1_SelectedPageChanged(kieu) {
      this.xtraTabControl1 = kieu;
      if (this.xtraTabControl1 == 2 || this.xtraTabControl1 == 3) {
        this.Visible.tsbtnNew = false;
        this.Visible.tsbtnSave = false;
        this.Visible.tsbtnCancel = false;
        this.Visible.tsbtnDel = false;
      } else {
        this.Visible.tsbtnNew = true;
        this.Visible.tsbtnSave = true;
        this.Visible.tsbtnCancel = true;
        this.Visible.tsbtnDel = true;
      }
    },
    async tsbtnLayTT_Click() {
      try {
        this.loading(true);
        await this.load_LoaiKho();
        await this.load_MaKhoKT();
        this.tabTKDU.DSLoaiCT = await this.getDSLoaiCT();
        if (this.tabTKDU.DSLoaiCT.length > 0) {
          this.tabTKDU.cboLoaiCT = this.tabTKDU.DSLoaiCT[0].loaiCtId;
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async load_LoaiKho() {
      var DSLoaiKhoMaKho = await this.getDSLoaiKhoMaKho({
        kieu: 0,
        loaiKhoId: this.tabLoaiKho.ckbXemTonKho ? 1 : 0,
      });
      this.tabMaKho.DSKTLK = DSLoaiKhoMaKho;
      this.tabLoaiKho.DSLoaiKho = DSLoaiKhoMaKho;
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        await this.setButton(1);
        this.tabLoaiKho.DSMucDich = [];
        this.tabLoaiKho.DSLKMD = [];
        this.tabLoaiKho.LoaiKhoSelected = {};
      }
    },
    async load_MaKhoKT() {
      this.tabMaKho.DSKhoKT = await this.getDSLoaiKhoMaKho({
        kieu: 1,
        loaiKhoId: 0,
      });
      if (this.tabMaKho.DSKhoKT.length == 0) {
        await this.setButton(1);
        this.tabMaKho.DSKTLKLeft = [];
        this.tabMaKho.DSKTLK = [];
        this.tabMaKho.KhoKTSelected = {};
      }
    },
    async grvLoaiKho_FocusedRowChanged(row) {
      if (row) {
        console.log(row, " = row");
        this.tabLoaiKho.LoaiKhoSelected = row;
        await this.setButton(3);
        this.tabLoaiKho.txtMaLK = row.MA_LK;
        this.tabLoaiKho.txtTenLK = row.TEN_LK;
        this.tabLoaiKho.txtGhiChuLK = row.GHICHU;
        this.tabLoaiKho.ckbTonKho = row.HIENTHI == 1;
        this.loaikho =
          row.LOAIKHO_TD_ID == null || row.LOAIKHO_TD_ID == ""
            ? 0
            : row.LOAIKHO_TD_ID;
        await this.load_LoaiKho_MucDich(this.loaikho);
        await this.load_MucDich(this.loaikho);
      }
    },
    async load_LoaiKho_MucDich(loaikho) {
      this.tabLoaiKho.DSLKMD = await this.getDSLoaiKhoMaKho({
        kieu: 4,
        loaiKhoId: loaikho,
      });
    },
    async load_MucDich(loaikho) {
      this.tabLoaiKho.DSMucDich = await this.getDSLoaiKhoMaKho({
        kieu: 2,
        loaiKhoId: loaikho,
      });
    },
    async btnLeftLKMD_Click() {
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        this.$toast.error("Không có dữ liệu loại kho!");
        return;
      }
      if (this.tabLoaiKho.DSMucDich.length == 0) {
        this.$toast.error("Không có kiểu phiếu nào được hiển thị! ");
        return;
      }
      console.log(this.tabLoaiKho.DSMucDichChecked, " DSMucDichChecked");
      if (this.tabLoaiKho.DSMucDichChecked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn kiểu phiếu nào!");
        return;
      }

      try {
        this.loading(true);
        var data = [];
        for (const e of this.tabLoaiKho.DSMucDichChecked) {
          data.push({
            maLk: this.loaikho,
            taiKhoan: e.MUCDICH_ID,
            lkId: 0,
            khoTdId: 0,
          });
        }

        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 0,
          loai: 2,
          data: data,
          tenLk: "",
          ghiChu: "",
        });
        await this.load_MucDich(this.loaikho);
        await this.load_LoaiKho_MucDich(this.loaikho);
        this.tabLoaiKho.DSMucDichChecked = [];
      } catch (e) {
        this.$toast.error("Đã xảy ra lỗi vui lòng thử lại!");
      } finally {
        this.loading(false);
      }
    },
    async btnRightLDKMD_Click() {
      if (this.tabLoaiKho.DSLoaiKho.length == 0) {
        this.$toast.error("Không có dữ liệu loại kho!");
        return;
      }
      if (this.tabLoaiKho.DSLKMD.length == 0) {
        this.$toast.error("Không có kiểu phiếu đã gán nào được hiển thị! ");
        return;
      }
      console.log(this.tabLoaiKho.DSLKMDChecked, " DSLKMDChecked");
      if (this.tabLoaiKho.DSLKMDChecked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn kiểu phiếu đã gán nào!");
        return;
      }

      try {
        this.loading(true);
        var data = [];
        for (const e of this.tabLoaiKho.DSLKMDChecked) {
          data.push({
            maLk: e.LOAIKHO_TD_ID,
            taiKhoan: 0,
            lkId: e.MUCDICH_ID,
            khoTdId: 0,
          });
        }

        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 2,
          loai: 2,
          data: data,
          tenLk: "",
          ghiChu: "",
        });

        await this.load_LoaiKho_MucDich(this.loaikho);
        await this.load_MucDich(this.loaikho);
        this.tabLoaiKho.DSLKMDChecked = [];
      } catch (e) {
        this.$toast.error("Đã xảy ra lỗi vui lòng thử lại!");
      } finally {
        this.loading(false);
      }
    },
    gridMucDich_Checked: function (dataItem) {
      this.tabLoaiKho.DSMucDichChecked = dataItem;
    },
    gridLKMD_Checked: function (dataItem) {
      this.tabLoaiKho.DSLKMDChecked = dataItem;
    },
    async grvKhoKT_FocusedRowChanged(row) {
      if (row) {
        this.tabMaKho.KhoKTSelected = row;
        await this.setButton(3);
        this.tabMaKho.txtMaKhoKT = row.MA_KHOTD;
        this.tabMaKho.txtTenKhoKT = row.TEN_KHOTD;
        this.tabMaKho.txtGhiChuKT = row.GHICHU;
        this.tabMaKho.txtTaiKhoan = row.TAIKHOAN;
        this.khoid =
          row.KHOTD_ID == null || row.KHOTD_ID == "" ? 0 : row.KHOTD_ID;
        this.load_LoaiKho_2(this.khoid);
        this.load_LoaiKho_MaKT(this.khoid);
      }
    },
    async load_LoaiKho_2(khoid) {
      this.tabMaKho.DSKTLK = await this.getDSLoaiKhoMaKho({
        kieu: 5,
        loaiKhoId: khoid,
      });
    },
    async load_LoaiKho_MaKT(khoid) {
      this.tabMaKho.DSKTLKLeft = await this.getDSLoaiKhoMaKho({
        kieu: 3,
        loaiKhoId: khoid,
      });
    },
    async btnLeftKT_Click() {
      if (this.tabMaKho.DSKhoKT.length == 0) {
        this.$toast.error("Không có dữ liệu mã kho!");
        return;
      }
      if (this.tabMaKho.DSKTLK.length == 0) {
        this.$toast.error("Không có loại kho nào được hiển thị! ");
        return;
      }

      if (this.tabMaKho.DSKTLKChecked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn kiểu phiếu đã gán nào!");
        return;
      }

      this.loading(true);
      var data = [];
      for (const LOAIKHO_TD_ID of this.tabMaKho.DSKTLKChecked) {
        data.push({
          maLk: LOAIKHO_TD_ID,
          taiKhoan: 0,
          lkId: parseInt(this.khoid),
          khoTdId: 0,
        });
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 0,
        loai: 3,
        data: data,
        tenLk: "",
        ghiChu: "",
      });
      await this.load_LoaiKho_2(this.khoid);
      await this.load_LoaiKho_MaKT(this.khoid);
      this.tabMaKho.DSKTLKChecked = [];
      this.loading(false);
    },

    async btnRightKT_Click() {
      if (this.tabMaKho.DSKhoKT.length == 0) {
        this.$toast.error("Không có dữ liệu mã kho!");
        return;
      }
      if (this.tabMaKho.DSKTLKLeft.length == 0) {
        this.$toast.error("Không có loại kho nào được hiển thị! ");
        return;
      }

      if (this.tabMaKho.DSKTLKLeftChecked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn kiểu phiếu đã gán nào!");
        return;
      }
      this.loading(true);
      var data = [];

      for (const e of this.tabMaKho.DSKTLKLeftChecked) {
        data.push({
          maLk: e.LOAIKHO_TD_ID,
          taiKhoan: 0,
          lkId: parseInt(e.KHOTD_ID),
          khoTdId: 0,
        });
      }

      var ketQua = await this.capNhatMaKhoLoaiKho({
        kieu: 2,
        loai: 3,
        data: data,
        tenLk: "",
        ghiChu: "",
      });
      await this.load_LoaiKho_MaKT(this.khoid);
      await this.load_LoaiKho_2(this.khoid);
      this.tabMaKho.DSKTLKLeftChecked = [];
      this.loading(false);
    },
    gridKTLK_Checked: function (dataItem) {
      this.tabMaKho.DSKTLKChecked = dataItem;
    },
    gridKTLKLeft_Checked: function (dataItem) {
      this.tabMaKho.DSKTLKLeftChecked = dataItem;
    },
    async cboLoaiCT_EditValueChanged() {
      this.tabTKDU.DSKieuPhieu = await this.getDSKieuPhieu(
        this.tabTKDU.cboLoaiCT
      );
      console.log(this.tabTKDU.DSKieuPhieu);
      if (this.tabTKDU.DSKieuPhieu.length > 0) {
        this.tabTKDU.cboKieuPhieu = this.tabTKDU.DSKieuPhieu[0].mucDichId;
      }
    },
    async cboKieuPhieu_EditValueChanged() {
      await this.load_TKDU();
      await this.load_TKDU_DaGan();
    },
    async load_TKDU() {
      let data = await this.getDSLoaiKhoMaKho({
        kieu: 7,
        loaiKhoId: this.tabTKDU.cboKieuPhieu,
      });
      data.forEach((e) => {
        e.TAIKHOAN =
          e.TAIKHOAN == null || e.TAIKHOAN == "" ? 0 : parseInt(e.TAIKHOAN);
      });
      this.tabTKDU.DSTKDU_ChuaGan = data;
      console.log(this.tabTKDU.DSTKDU_ChuaGan, " = DSTKDU_ChuaGan");
    },
    async load_TKDU_DaGan() {
      let data = await this.getDSLoaiKhoMaKho({
        kieu: 8,
        loaiKhoId: this.tabTKDU.cboKieuPhieu,
      });

      data.forEach((a) => {
        a.TAIKHOAN =
          a.TAIKHOAN == null || a.TAIKHOAN == "" ? 0 : parseInt(a.TAIKHOAN);
      });
      this.tabTKDU.DSTKDU_DaGan = data;
    },
    async editGridDU_DaGan(arg) {
      try {
        this.loading(true);
        let gridTKDU_DaGan = this.$refs.gridTKDU_DaGan;
        let changes = gridTKDU_DaGan.getBatchChanges();
        if (changes.changedRecords.length == 0) {
          return;
        }
        if (arg.columnName == "TAIKHOAN") {
          var data = [];
          if (parseInt(changes.changedRecords[0].TAIKHOAN) < 0) {
            this.$toast.error("Tài khoản không được âm !");
            return;
          }
          data.push({
            maLk: "",
            taiKhoan: changes.changedRecords[0].TAIKHOAN,
            lkId: this.tabTKDU.cboKieuPhieu,
            khoTdId: changes.changedRecords[0].KHOTD_ID,
          });
          var ketQua = await this.capNhatMaKhoLoaiKho({
            kieu: 1,
            loai: 2,
            data: data,
            tenLk: "",
            ghiChu: "",
          });
          await this.load_TKDU();
          await this.load_TKDU_DaGan();
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    async editGridDU_ChuaGan(arg) {
      let gridTKDU_ChuaGan = this.$refs.gridTKDU_ChuaGan;
      let changes = gridTKDU_ChuaGan.getBatchChanges();
      if (changes.changedRecords.length == 0) {
        return;
      }
      if (arg.columnName == "TAIKHOAN") {
        if (parseInt(changes.changedRecords[0].TAIKHOAN) < 0) {
          this.$toast.error("Tài khoản không được âm !");
          this.$refs.editGridDU_ChuaGan.grid.refreshColumns();
          return;
        }
        editGridDU_ChuaGan.batchUpdate(changes);
        this.$refs.editGridDU_ChuaGan.grid.refreshColumns();
      }
    },
    async btnLeftTKDU_Click() {
      try{
        if (this.tabTKDU.DSTKDU_ChuaGan.length == 0) {
          this.$toast.error("Không có dữ liệu được hiển thị! ");
          return;
        }

        if (this.tabTKDU.DSTKDU_ChuaGan_Checked.length == 0) {
          this.$toast.error("Bạn chưa tích chọn tài khoản đối ứng nào!");
          return;
        }

        this.loading(true);
        var data = [];
        let DSCheck = this.tabTKDU.DSTKDU_ChuaGan.filter(e=> this.tabTKDU.DSTKDU_ChuaGan_Checked.includes(e.KHOTD_ID))
        for (const e of DSCheck) {
          data.push({
            maLk: "",
            taiKhoan: parseInt(e.TAIKHOAN),
            lkId: this.tabTKDU.cboKieuPhieu,
            khoTdId: parseInt(e.KHOTD_ID),
          });
        }

        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 0,
          loai: 4,
          data: data,
          tenLk: "",
          ghiChu: "",          
        });
        await this.load_TKDU();
        await this.load_TKDU_DaGan();
        this.tabTKDU.DSTKDU_ChuaGan_Checked = [];
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },

    gridTKDU_ChuaGan_Checked: function (dataItem) {
      this.tabTKDU.DSTKDU_ChuaGan_Checked = dataItem;
    },

    gridTKDU_DaGan_Checked: function (dataItem) {
      this.tabTKDU.DSTKDU_DaGan_Checked = dataItem;
    },

    async btnRightTKDU_Click() {
      try{
        if (this.tabTKDU.DSTKDU_DaGan.length == 0) {
          this.$toast.error("Không có dữ liệu được hiển thị! ");
          return;
        }

        if (this.tabTKDU.DSTKDU_DaGan_Checked.length == 0) {
          this.$toast.error("Bạn chưa tích chọn tài khoản đối ứng nào!");
          return;
        }

        this.loading(true);
        var data = [];     
        let DSCheck = this.tabTKDU.DSTKDU_DaGan.filter(e=> this.tabTKDU.DSTKDU_DaGan_Checked.includes(e.ID))
        for (const e of DSCheck) {
          data.push({
            maLk: "",
            taiKhoan: 0,
            lkId: this.tabTKDU.cboKieuPhieu,
            khoTdId: parseInt(e.KHOTD_ID),
          });
        }

        var ketQua = await this.capNhatMaKhoLoaiKho({
          kieu: 2,
          loai: 4,
          data: data,
          tenLk: "",
          ghiChu: "",          
        });
        await this.load_TKDU_DaGan();
        await this.load_TKDU();
        this.tabTKDU.DSTKDU_DaGan_Checked = [];
        
      }catch(e){
        this.$toast.error(e.data.message)
      }finally{
        this.loading(false);
      }
    },

    tsbtnNew_Click() {
      this.setButton(1);
    },
    tsbtnCancel_Click() {
      this.setButton(2);
    },
    async tsbtnDel_Click() {
      try {
        if (this.xtraTabControl1 == 0) {
          if (this.tabLoaiKho.LoaiKhoSelected != {}) {
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm(
                "Bạn có thực sự muốn xóa [" +
                  this.tabLoaiKho.LoaiKhoSelected.MA_LK +
                  "] ?",
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

            var data = [];
            var vid_lk = parseInt(
              this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null ||
                this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == ""
                ? 0
                : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID
            );
            data.push({
              maLk: "",
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 2,
              loai: 0,
              data: data,
              tenLk: "",
              ghiChu: "",
            });
            if (ketQua == 1) {
              this.$toast.success("Xóa thành công!");
              await this.load_LoaiKho();
            } else {
              this.$toast.error(ketQua);
            }
          } else {
            this.$toast.error("Bạn chưa chọn dòng loại kho!");
          }
        } else {
          if (this.tabMaKho.KhoKTSelected != {}) {
            var isBoxConfirm = false;
            await this.$bvModal
              .msgBoxConfirm(
                "Bạn có thực sự muốn xóa [" +
                  this.tabMaKho.KhoKTSelected.MA_KHOTD +
                  "] ?",
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

            var data = [];
            var vid_makhotd = parseInt(
              this.tabMaKho.KhoKTSelected.KHOTD_ID == null ||
                this.tabMaKho.KhoKTSelected.KHOTD_ID == ""
                ? 0
                : this.tabMaKho.KhoKTSelected.KHOTD_ID
            );
            data.push({
              maLk: "",
              taiKhoan: 0,
              lkId: vid_makhotd,
              khoTdId: 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 2,
              loai: 1,
              data: data,
              tenLk: "",
              ghiChu: "",
            });

            if (ketQua == 1) {
              this.$toast.success("Xóa thành công!");
              await this.load_MaKhoKT();
            } else {
              this.$toast.error(ketQua);
            }
          } else {
            this.$toast.error("Bạn chưa chọn dòng mã kho!");
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },

    async tsbtnSave_Click() {
      try {
        if (this.Enabled.tsbtnNew) {
          // cập nhật

          if (this.xtraTabControl1 == 0) {
            // Loại Kho

            if (this.tabLoaiKho.txtMaLK.trim() == "") {
              this.$toast.error("Mã loại kho không được để trống!");
              this.$refs.txtMaLK.focus();
              return;
            }
            if (this.tabLoaiKho.txtTenLK.trim() == "") {
              this.$toast.error("Tên loại kho không được để trống!");
              this.$refs.txtTenLK.focus();
              return;
            }

            var data = [];
            var vid_lk = parseInt(
              this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null ||
                this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == ""
                ? 0
                : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID
            );
            data.push({
              maLk: this.tabLoaiKho.txtMaLK.trim(),
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: this.tabLoaiKho.ckbTonKho ? 1 : 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 1,
              loai: 0,
              data: data,
              tenLk: this.tabLoaiKho.txtTenLK.trim(),
              ghiChu: this.tabLoaiKho.txtGhiChuLK.trim(),
            });

            if (ketQua == 1) {
              this.$toast.success("Cập nhật loại kho thành công !");
              await this.load_LoaiKho();
            } else {
              this.$toast.error(ketQua);
            }
          } else {
            // Mã kho
            var kiemTra = this.KIEMTRA_DL_MAKHO();
            if (kiemTra == false) {
              return;
            }

            var data = [];
            var vid_makhotd = parseInt(
              this.tabMaKho.KhoKTSelected.KHOTD_ID == null ||
                this.tabMaKho.KhoKTSelected.KHOTD_ID == ""
                ? 0
                : this.tabMaKho.KhoKTSelected.KHOTD_ID
            );
            data.push({
              maLk: this.tabMaKho.txtMaKhoKT.trim(),
              taiKhoan: this.tabMaKho.txtTaiKhoan.trim(),
              lkId: vid_makhotd,
              khoTdId: 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 1,
              loai: 1,
              data: data,
              tenLk: this.tabMaKho.txtTenKhoKT.trim(),
              ghiChu: this.tabMaKho.txtGhiChuKT.trim(),
            });

            if (ketQua == 1) {
              this.$toast.success("Cập nhật mã kho KT thành công !");
              await this.load_MaKhoKT();
            } else {
              this.$toast.error(ketQua);
            }
          }
        } else {
          // Thêm mới

          if (this.xtraTabControl1 == 0) {
            // Loại Kho

            if (this.tabLoaiKho.txtMaLK.trim() == "") {
              this.$toast.error("Mã LK không được để trống!");
              this.$refs.txtMaLK.focus();
              return;
            }
            if (this.tabLoaiKho.txtTenLK.trim() == "") {
              this.$toast.error("Tên LK không được để trống!");
              this.$refs.txtTenLK.focus();
              return;
            }

            var data = [];
            var vid_lk = parseInt(
              this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == null ||
                this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID == ""
                ? 0
                : this.tabLoaiKho.LoaiKhoSelected.LOAIKHO_TD_ID
            );
            data.push({
              maLk: this.tabLoaiKho.txtMaLK.trim(),
              taiKhoan: 0,
              lkId: vid_lk,
              khoTdId: this.tabLoaiKho.ckbTonKho ? 1 : 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 0,
              loai: 0,
              data: data,
              tenLk: this.tabLoaiKho.txtTenLK.trim(),
              ghiChu: this.tabLoaiKho.txtGhiChuLK.trim(),
            });
            if (ketQua == 1) {
              this.$toast.success("Thêm mới loại kho thành công !");
              await this.load_LoaiKho();
            } else {
              this.$toast.error(ketQua);
            }
          } else {
            // Mã kho
            var kiemTra = this.KIEMTRA_DL_MAKHO();
            if (kiemTra == false) {
              return;
            }

            var data = [];
            var vid_makhotd = parseInt(
              this.tabMaKho.KhoKTSelected.KHOTD_ID == null ||
                this.tabMaKho.KhoKTSelected.KHOTD_ID == ""
                ? 0
                : this.tabMaKho.KhoKTSelected.KHOTD_ID
            );
            data.push({
              maLk: this.tabMaKho.txtMaKhoKT.trim(),
              taiKhoan: parseInt(this.tabMaKho.txtTaiKhoan),
              lkId: vid_makhotd,
              khoTdId: 0,
            });
            var ketQua = await this.capNhatMaKhoLoaiKho({
              kieu: 0,
              loai: 1,
              data: data,
              tenLk: this.tabMaKho.txtTenKhoKT.trim(),
              ghiChu: this.tabMaKho.txtGhiChuKT.trim(),
            });

            if (ketQua == 1) {
              this.$toast.success("Cập nhật mã kho KT thành công !");
              await this.load_MaKhoKT();
            } else {
              this.$toast.error(ketQua);
            }

            if (ketQua == 1) {
              this.$toast.success("Thêm mới mã kho KT thành công !");
              await this.load_MaKhoKT();
            } else {
              this.$toast.error(ketQua);
            }
          }
        }
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        this.loading(false);
      }
    },
    KIEMTRA_DL_MAKHO() {
      if (this.tabMaKho.txtMaKhoKT.trim() == "") {
        this.$toast.error("Mã Kho KT không được để trống! ");
        this.$refs.txtMaKhoKT.focus();
        this.$refs.txtTenKhoKT.focus();
        return false;
      }
      if (this.tabMaKho.txtTenKhoKT.trim() == "") {
        this.$toast.error("Tên Kho KT không được để trống! ");
        this.$refs.txtTenKhoKT.focus();
        return false;
      }
      if (this.tabMaKho.txtTaiKhoan.trim() == "") {
        this.$toast.error("Tài khoản không được để trống! ");
        this.$refs.txtTaiKhoan.focus();
        return false;
      }
      return true;
    },
    async cboKieuP_EditValueChanged() {
      this.tabLKMK.DSLK = await this.getDSLoaiKhoTheoMucDich(
        this.tabLKMK.cboKieuP
      );
      this.tabLKMK.cboLK =
        this.tabLKMK.DSLK.length > 0 ? this.tabLKMK.DSLK[0].loaiKhoTdId : 0;
    },
    async cboLK_EditValueChanged() {
      this.tabLKMK.DSMKTD = await this.getDSKhoTD(this.tabLKMK.cboLK);
      this.tabLKMK.cboMKTD =
        this.tabLKMK.DSMKTD.length > 0 ? this.tabLKMK.DSMKTD[0].khoTDId : 0;
    },
    tsbtnXacNhan_Click() {
      this.xacnhan = true;
      this.$refs.popupLoaiKhoMaKho.hide();
      this.$parent.XacNhan_frmLoaiKho_MaKho();
    },

    gridVatTu_Checked: async function (dataItem) {
      this.tabLKMK.DSVatTu_Checked = dataItem;
      if (dataItem.length > 0) {
        this.tabLKMK.DSDongTB = await this.getDSDongThietBi({
          dem: dataItem.length,
          vatTuId: dataItem,
        });
        this.tabLKMK.cboDongTB =
          this.tabLKMK.DSDongTB.length > 0
            ? this.tabLKMK.DSDongTB[0].dongTbiId
            : 0;
      }
    },
    async cboDongTB_EditValueChanged() {
      this.tabLKMK.DSHeThong = await this.getDSKhoTD(this.tabLKMK.cboLK);
      this.tabLKMK.cboHeThong =
        this.tabLKMK.DSHeThong.length > 0
          ? this.tabLKMK.DSHeThong[0].heThongId
          : 0;
    },
    btnLKDown_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn vật tư nào !!!");
        return;
      }
      if (this.tabLKMK.cboLK == 0 || this.tabLKMK.cboLK == null) {
        this.$toast.error("Bạn chưa chọn loại kho !!!");
        return;
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (
          this.tabLKMK.DSVatTu_Checked.includes(
            this.tabLKMK.DSVatTu[i].VATTU_ID
          )
        ) {
          this.tabLKMK.DSVatTu[i].LOAIKHO = this.tabLKMK.cboLK;
        }
      }
      this.$toast.success("Cập nhật loại kho thành công!!!");
    },
    btnChatLuong_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn vật tư nào !!!");
        return;
      }
      if (this.tabLKMK.cboChatLuong == 0 || this.tabLKMK.cboChatLuong == null) {
        this.$toast.error("Bạn chưa chọn chất lượng !!!");
        return;
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (
          this.tabLKMK.DSVatTu_Checked.includes(
            this.tabLKMK.DSVatTu[i].VATTU_ID
          )
        ) {
          this.tabLKMK.DSVatTu[i].CHATLUONG_ID = this.tabLKMK.cboChatLuong;
        }
      }
      this.$toast.success("Cập nhật chất lượng thành công!!!");
    },
    btnMKTDDown_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn vật tư nào !!!");
        return;
      }
      if (this.tabLKMK.cboLK == 0 || this.tabLKMK.cboLK == null) {
        this.$toast.error("Bạn chưa chọn loại kho !!!");
        return;
      }
      if (this.tabLKMK.cboMKTD == 0 || this.tabLKMK.cboMKTD == null) {
        this.$toast.error("Bạn chưa chọn mã kho TD !!!");
        return;
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (
          this.tabLKMK.DSVatTu_Checked.includes(
            this.tabLKMK.DSVatTu[i].VATTU_ID
          )
        ) {
          this.tabLKMK.DSVatTu[i].MAKHOTD = this.tabLKMK.cboMKTD;
        }
      }
      this.$toast.success("Cập nhật mã kho TĐ thành công!!!");
    },
    btnDongtbi_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn vật tư nào !!!");
        return;
      }
      if (this.tabLKMK.cboDongTB == 0 || this.tabLKMK.cboDongTB == null) {
        this.$toast.error("Bạn chưa chọn dòng thiết bị !!!");
        return;
      }

      for (const i in this.tabLKMK.DSVatTu) {
        if (
          this.tabLKMK.DSVatTu_Checked.includes(
            this.tabLKMK.DSVatTu[i].VATTU_ID
          )
        ) {
          this.tabLKMK.DSVatTu[i].DONGTBI_ID = this.tabLKMK.cboDongTB;
        }
      }
      this.$toast.success("Cập nhật dòng thiết bị thành công!!!");
    },
    btnHeThong_Click() {
      if (this.tabLKMK.DSVatTu_Checked.length == 0) {
        this.$toast.error("Bạn chưa tích chọn vật tư nào !!!");
        return;
      }
      if (this.tabLKMK.cboDongTB == 0 || this.tabLKMK.cboDongTB == null) {
        this.$toast.error("Bạn chưa chọn dòng thiết bị !!!");
        return;
      }
      if (this.tabLKMK.cboHeThong == 0 || this.tabLKMK.cboHeThong == null) {
        this.$toast.error("Bạn chưa chọn hệ thống !!!");
        return;
      }
      for (const i in this.tabLKMK.DSVatTu) {
        if (
          this.tabLKMK.DSVatTu_Checked.includes(
            this.tabLKMK.DSVatTu[i].VATTU_ID
          )
        ) {
          this.tabLKMK.DSVatTu[i].HETHONG_ID = this.tabLKMK.cboHeThong;
        }
      }
      this.$toast.success("Cập nhật hệ thống thành công!!!");
    },
  },
  created: async function () {},
};
</script>