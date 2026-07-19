<template src='./TraCuuCCDC.html'></template>
<style scoped src='./TraCuuCCDC.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import TraCuuCCDCAPI from "../api/TraCuuCCDCAPI";
import Vue from "vue";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";

Vue.use(DatePickerPlugin);
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/tracuuccdc";
import select2 from "@/components/Select2.vue";
import {
  DataManager,
  Query,
  ODataV4Adaptor,
  JsonAdaptor,
} from "@syncfusion/ej2-data";
import {
  Freeze,
  Edit,
  Group,
  Page,
  Filter,
  Sort,
  Resize,
  editBegin,
} from "@syncfusion/ej2-vue-grids";
import XLSX from "xlsx";
import ChonThietBiCT from "../frmChonTBi_CT/ChonThietBiCT.vue";
var defaultParams = {
  p_txtLocDonVi: "",
  p_txtLocMaND: "",
  p_txtLocMaNV: "",
  p_txtLocTenNV: "",
  p_dtp: new Date(),
  p_ckbNhanVien: true,
  p_dtpNgayIn: new Date(),
  p_spMuc: 1,
  p_ckbMNV: false,
  p_spMuc2: 1,
  p_rdioCKb: 1.8,
  p_ckbMirror: false,
  p_ckbTietKiem: false,
};

export default {
  components: { XLSX, appSelect2: select2, ChonThietBiCT },
  name: "TraCuuCCDC",
  mounted() {},
  provide: {
    grid: [Freeze, Edit, Group, Page, Filter, Sort, Resize],
  },
  computed: {
    ...mapState("tracuuccdc", statePropertyName),
    ...mapGetters("tracuuccdc", getterName),
    vdonvi_id: {
      get() {
        return this.tvDonVi_FocusedNode.id || -1;
      },
    },
    vnhanvien_id() {
      console.log(this.grvNhanVien_FocusedRow.NHANVIEN_ID);
      return this.grvNhanVien_FocusedRow
        ? this.grvNhanVien_FocusedRow.NHANVIEN_ID
        : -1;
    },
  },
  data() {
    return {
      params: Object.assign({}, defaultParams),
      grvNhanVien_FocusedRow: "",
      tvDonVi_FocusedNode: {},
      tvDonVi: [],
      grcNhanVien: [],
      gridThietBi: [],
      gridThietBiItems: [],
      grcDS_TS: [],

      KhoCgCache: [],
      DviCache: [],

      KhoCgIndex: 0,
      DviIndex: 0,

      KhoCgWord: "",
      DviWord: "",

      vdvtemp_id: 0,
      dtThietBi: [],

      Visible: {
        txtLocDonVi: {
          Button: false,
        },
        tsbtnExcelDK: false,
        tsbtnMauEx: false,
        thoigian_kk: false,
        cabman: false,
        soluong: false,
        tenTSCD: true,
        bophan_quanly: true,
        nam: true,
      },
      RadioOptions: [
        { text: "2.4 x 10 cm", value: 2.4 },
        { text: "1.8 x 10 cm", value: 1.8 },
        { text: "1.2 x 10 cm", value: 1.2 },
      ],
      Enabled: {
        radio_size: true,
      },
      items: null,
      fields: {
        dataSource: [],
        id: "DONVI_ID",
        parentID: "DONVI_CHA_ID",
        text: "TEN_DV",
        hasChildren: "hasChild",
      },
      selectedNodes: [],
      isTab1: true,
      filterOptions: {
        enableCaseSensitivity: false,
        mode: "Immediate",
        ignoreAccent: true,
      },
      selectionSettings: { type: "Single", checkboxOnly: true },
      editSettings: { allowEditing: true },
      dateConfig: {
        default: new Date(),
        max: new Date(),
        format: "MM/yyyy",
        waterMark: "Chọn tháng",
      },
      templateOptions: {
        create: function (args) {
          if (args.column.field === "STT") {
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
            template: "<span >{{data}}</span>",
            data: function () {
              return { data: {} };
            },
          }), // end of template
        };
      },
      resultDV: [],
      arrayImportFile: {
        filexcel: null,
        ok: false,
        dsImportExcel: [],
        dataExcel: [],
        ds_file: [],
      },

      grcNhanVienPage: {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        sort: null,
        propertiesSort: null,
        currentRow: 0,
      },
      tabXtraSelected: "tab1",
    };
  },
  watch: {
    tab1Selected(newx) {
      console.log(newx);
    },
  },
  methods: {
    ...mapActions("tracuuccdc", actionName),
    ...mapMutations("tracuuccdc", mutationName),
    // chuyển Date thành type string
    f_DateToString(value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    dateToHour(value, format = "HH") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    tabSelected(value) {
      this.tabXtraSelected = value;
      if (value == "tab1") {
        this.Visible.tsbtnExcelDK = false;
        this.Visible.tsbtnMauEx = false;
      } else if (value == "tab2") {
        this.Visible.tsbtnExcelDK = true;
        this.Visible.tsbtnMauEx = true;
      }
    },
    grcNhanVienPageHandler(e) {
      this.grcNhanVienPage.page = e.pageIndex;
      this.grcNhanVienPage.maxSize = e.pageSize;
    },

    async NAP_DS_DONVI() {
      // try {
      this.params.p_dtpNgayIn = new Date();
      // Xóa cache
      this.DviIndex = 0;
      this.DviWord = "";
      this.DviCache = "";
      this.Visible.txtLocDonVi.Button = false;

      this.tvDonVi = await this.getDSDonVi();
      this.tvDonVi.splice(0, 0, {
        DONVI_ID: "900",
        ID: "900",
        TEN_DV: "Tập đoàn",
        DONVI_CHA_ID: "",
        UNITLEVEL: 0,
      });
      this.tvDonVi = this.createTreeDonVi(this.tvDonVi);
      this.fields = {
        dataSource: this.tvDonVi,
        id: "DONVI_ID",
        text: "TEN_DV",
        parentID: "DONVI_CHA_ID",
        hasChildren: "hasChild",
      };
      // get DonVi Hai Phòng với ID 0
      // this.params.p_donvi_id = 0;
      this.selectedNodes.push("0");
      // } catch (error) {
      //   this.$toast.error("Lỗi khi nạp danh sách đơn vị\r\n")
      // }
    },
    createTreeDonVi(dsDonVi) {
      let tree = [];
      let donviChaArray = dsDonVi.map((item) => item["DONVI_CHA_ID"]);
      let donviArray = dsDonVi.map((item) => item["DONVI_ID"]);
      dsDonVi.forEach((item) => {
        //check item la parent node
        if (donviChaArray.includes(item["DONVI_ID"])) {
          //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
          if (!donviArray.includes(item["DONVI_CHA_ID"])) {
            delete item.DONVI_CHA_ID;
          }
          tree.push({ ...item, hasChild: true, expanded: true });
        } else {
          tree.push(item);
        }
      });
      return tree;
    },
    async NAP_DS_NHANVIEN() {
      this.gridThietBi = []; // quy bo sung, mặc định khi load nhân viên thì gridThietBị rỗng
      if (this.vdonvi_id == -1) {
        this.grcNhanVien = await this.getDSNhanVien();
      } else {
        this.grcNhanVien = await this.getDSNhanVienTheoDV({
          kieu: 1,
          donViId: this.vdonvi_id,
        });
      }
    },
    async NAP_DS_CCDC() {
      // try{
      let loai = 2;
      if (this.params.p_ckbNhanVien) {
        //Neu tim theo nhan vien
        loai = 1;
      }

      this.dtThietBi = await this.getDanhSachCCDC({
        phanVungId: this.$auth.getPhanVungID(),
        donViId: this.vdonvi_id,
        nhanVienId: this.vnhanvien_id,
        vdtp: this.f_DateToString(this.params.p_dtp, "MM/yyyy"), // this.params.p_dtp
        loai: loai,
      });
      this.showData_dtThietBi();
      this.dtThietBi.forEach((item, index) => {
        item["STT"] = index;
      });
      this.gridThietBi = this.dtThietBi;
      // }
      // catch(error){
      //   this.$toast.error("Lỗi khi nạp công cụ dụng cụ " )
      // }
    },
    showData_dtThietBi() {
      this.dtThietBi.forEach((item) => {
        item.DONGIA ? (item.DONGIA = parseInt(item.DONGIA)) : (item.DONGIA = 0);
        item.TIEN ? (item.TIEN = parseInt(item.TIEN)) : (item.TIEN = 0);
        item.TIEN_PB
          ? (item.TIEN_PB = parseInt(item.TIEN_PB))
          : (item.TIEN_PB = 0);
        item.TIEN_CL
          ? (item.TIEN_CL = parseInt(item.TIEN_CL))
          : (item.TIEN_CL = 0);

        item.THANG_BD
          ? (item.THANG_BD = this.f_DateToString(
              this.f_StringToDate(item.THANG_BD, "YYYYMM"),
              "MM/YYYY"
            ))
          : (item.THANG_BD = "");
        item.THANG_KT
          ? (item.THANG_KT = this.f_DateToString(
              this.f_StringToDate(item.THANG_KT, "YYYYMM"),
              "MM/YYYY"
            ))
          : (item.THANG_KT = "");
      });
    },
    async frmGanKhoNV_Load() {
      await this.NAP_DS_DONVI();
      this.params.p_dtp = new Date();
    },
    async btnLamMoi_Click() {
      await this.NAP_DS_DONVI();
    },
    async btnTroGiup_Click() {},
    async tvDonVi_FocusedNodeChanged(node) {
      console.log(node.nodeData);
      this.tvDonVi_FocusedNode = node.nodeData;
      await this.NAP_DS_NHANVIEN();
    },

    async ketQuaLoc(key, value, res, msg, isMaND = false) {
      try {
        let valueSearch = value ? value : res.maNv;
        let donvi_id = "";
        if (isMaND) {
          donvi_id = res.donViId;
        } else {
          donvi_id = res;
        }
        console.log("donvi_id", donvi_id);
        let donViObj = this.tvDonVi.find(
          (a) => a.DONVI_ID.toString() == donvi_id.toString()
        );
        console.log(donViObj);
        if (Object.keys(donViObj).length) {
          console.log("vao roi");
          this.selectedNodes = [];

          this.selectedNodes.push(donViObj.DONVI_ID.toString());
          console.log("selectedNodes", this.selectedNodes);
          this.tvDonVi_FocusedNode = this.$refs.treeObj.getNode(
            donViObj.DONVI_ID.toString()
          );

          await this.NAP_DS_NHANVIEN();
          let rowNhanVien;
          if (key == "MA_NV") {
            rowNhanVien = this.grcNhanVien.find((b) => b[key] == valueSearch);
          } else {
            rowNhanVien = this.grcNhanVien.find((b) =>
              b[key].includes(valueSearch)
            );
          }
          if (rowNhanVien) {
            this.grvNhanVien_FocusedRow = rowNhanVien;
            // var frm = this.$refs.grvNhanVien
            // this.$refs.grvNhanVien.setCurrentSelectedRow(parseInt(rowNhanVien.STT) -1)
            // this.grcNhanVienPage.maxSize = 200
            // this.grcNhanVienPage.page = parseInt(parseInt(rowNhanVien.STT) / this.grcNhanVienPage.maxSize)
            // frm.setCurrentSelectedRow(parseInt(index % this.grcNhanVienPage.maxSize) -1 )
            // this.$refs.grvNhanVien.grid.selectRow( parseInt(this.grvNhanVien_FocusedRow.STT) -1)
            await this.NAP_DS_CCDC();
          }
        } else {
          this.$toast.error(msg);
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.loading(false);
      }
    },
    async txtLocMaND_KeyUp() {
      try {
        this.loading(true);
        if (this.params.p_txtLocMaND != "") {
          let resDV = await this.getDSTheoMaND({
            maNd: this.params.p_txtLocMaND,
          });
          if (resDV.length == 0) {
            return this.$toast.error(
              "Người dùng với mã " + this.params.p_txtLocMaND + " không tồn tại"
            );
          }
          if (Array.isArray(resDV) && resDV.length) {
            await this.ketQuaLoc(
              "MA_NV",
              "",
              resDV[0],
              "Không tìm thấy đơn vị của người dùng, hãy nhấn nút Làm mới và thử lại",
              true
            );
          }
        } else {
          await this.NAP_DS_NHANVIEN();
        }
      } catch (error) {
        //  this.$toast.error("Lỗi khi tìm kiếm nhân viên theo mã người dùng");
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    async txtLocMaNV_KeyUp() {
      try {
        this.loading(true);
        if (this.params.p_txtLocMaNV != "") {
          let resDV = await this.getDSDonViTheoMaNV({
            maNv: this.params.p_txtLocMaNV,
          });
          if (resDV.length == 0) {
            return this.$toast.error(
              "Nhân viên với mã " + this.params.p_txtLocMaNV + " không tồn tại"
            );
          }
          if (Array.isArray(resDV) && resDV.length) {
            await this.ketQuaLoc(
              "MA_NV",
              this.params.p_txtLocMaNV,
              resDV[0],
              "Không tìm thấy đơn vị của nhân viên, hãy nhấn nút Làm mới và thử lại",
              false
            );
          }
        } else {
          await this.NAP_DS_NHANVIEN();
        }
      } catch (error) {
        // this.$toast.error("Lỗi khi tìm kiếm nhân viên theo mã");
        console.log(error);
      } finally {
        this.loading(false);
      }
    },
    // can check them
    async txtLocTenNV_KeyUp() {
      try {
        this.loading(true);
        if (this.params.p_txtLocTenNV != "") {
          let resDV = await this.getDonViTheoTenNV({
            tenNv: this.params.p_txtLocTenNV.toLowerCase(),
          });
          if (resDV.length == 0) {
            return this.$toast.error(
              "Nhân viên với tên" + this.params.p_txtLocTenNV + " không tồn tại"
            );
          }
          if (Array.isArray(resDV) && resDV.length) {
            await this.ketQuaLoc(
              "TEN_NV",
              this.params.p_txtLocTenNV,
              resDV[0],
              "Không tìm thấy đơn vị của nhân viên, hãy nhấn nút Làm mới và thử lại",
              false
            );
          }
        } else {
          await this.NAP_DS_NHANVIEN();
        }
      } catch (error) {
        this.$toast.error("Lỗi khi tìm kiếm nhân viên theo tên");
      } finally {
        this.loading(false);
      }
    },
    async gridViewThietBi_CellValueChanged() {
      // lam sau
    },
    async btnLocDSSC_Click() {
      this.gridThietBi = this.$refs.gridViewThietBi.getSelectedRecords();
    },
    async btnNapDSSC_Click() {
      await this.NAP_DS_CCDC();
      this.$refs.gridViewThietBi.grid.clearFiltering();
      this.$refs.gridViewThietBi.grid.refresh();
    },
    async grvNhanVien_FocusedRowChanged(rowNhanVien) {
      if (Object.keys(rowNhanVien || {}).length) {
        this.grvNhanVien_FocusedRow = rowNhanVien.data;
        await this.NAP_DS_CCDC();
      }
      // quy edit, neu ko co nhan vien mac dinh gridThietBi rong
      else {
        this.gridThietBi = [];
      }
    },
    async ckbNhanVien_CheckedChanged() {
      await this.NAP_DS_CCDC();
    },
    gridThietBiSelectedItems(value) {
      this.gridThietBiItems = value;
      if (value) {
        if (value.length == 0) {
          this.gridThietBi.forEach((e, index) => {
            e.SL_XUAT = 0;
            this.$refs.gridThietBi.grid.updateCell(index, "SL_XUAT", 0);
          });
        } else {
          this.gridThietBi.forEach((e, index) => {
            if (value.includes(e.STT)) {
              e.SL_XUAT = e.SL_TON_THAT;
              this.$refs.gridThietBi.grid.updateCell(
                index,
                "SL_XUAT",
                e.SL_TON_THAT
              );
            }
          });
        }
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

    async getPdfBARCODE_CCDC(body) {
      console.log("getPdfBARCODE_CCDC", body);
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-barcode-ccdc`,
          method: "POST",
          responseType: "blob",
          data: body,
        }).then((response) => {
          this.previewPrint(response.data);
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.error_code);
      } finally {
        this.loading(false);
      }
    },
    async getPdfBARCODEByNameRP(name, body) {
      try {
        this.loading(true);
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-qlvt/api/bao-cao/bao-cao-barcode-ccdc-small?nameRP=${name}`,
          method: "POST",
          responseType: "blob",
          data: body,
        }).then((response) => {
          this.previewPrint(response.data);
        });
      } catch (error) {
        let res = await JSON.parse(await error.data.text());
        this.$toast.error(res.error_code);
      } finally {
        this.loading(false);
      }
    },
    async btnInTem() {
      // lam sau
      console.log(this.tabXtraSelected);
      if (this.tabXtraSelected == "tab1") {
        let soluongTem = 0;
        soluongTem = this.params.p_spMuc;
        let dsbarcode = [];
        let data = this.$refs.gridViewThietBi.getSelectedRecords();
        for (const row of data) {
          for (let i = 0; i < soluongTem; i++) {
            for (let j = 0; j < parseInt(row.SL_XUAT); j++) {
              let ma_vt = row["VATTU_ID"];
              let ma_dv = row["MA_CHA"];
              let ten_dv = row["TEN_CHA"];
              let ma_cc = row["MA_VT"];
              let ten_cc = row["TEN_VT"];
              let bophan = row["BOPHAN"];
              let ma_cn = row["MA_CN"];
              let ma_dvi = row["MA_DVI"];
              let ma_nvien = row["MA_CN"];

              let gioin = this.dateToHour(this.params.p_dtpNgayIn);
              let ngayin = this.f_DateToString(this.params.p_dtpNgayIn);

              let dt = this.f_StringToDate(row["THANG_BD"]);
              //   let nam_sd = this.f_DateToString(dt, "MM/YYYY");
              let nam_sd = row["THANGBD"];
              //   let nam = this.f_DateToString(dt, "YYYY");
              nam_sd = nam_sd.padStart(4, "0");
              let ma_sd = row["SDCC_ID"];

              if (gioin == "23") gioin = "24";
              let item = {
                serial: `C|${ma_vt}|${nam_sd}|${ma_sd}`,
                ma_dv: ma_dv,
                ten_dv: ten_dv,
                gioin: gioin,
                ngayin: ngayin,
                ma_cc: ma_cc,
                ten_cc: ten_cc,
                bophan: bophan,
                nam: `CCDC-VT${this.$auth.getMaTinh()}-${nam_sd}`,
                ma_nvien: `Mã NV:${ma_nvien}`,
              };
              dsbarcode.push(item);
            }
          }
        }
        if (dsbarcode.length == 0) {
          return this.$toast.error("Hãy chọn các serial cần in");
        }
        this.getPdfBARCODE_CCDC(dsbarcode);
        //In new frmXemBaoCao("BARCODE_CCDC", dsbarcode).XemNgay();
      } else {
        if (!this.params.p_ckbMNV) {
          let soluongTem = 0;
          soluongTem = this.params.p_spMuc2;
          let dsbarcode = [];
          let data = this.$refs.grvDS_TS.getSelectedRecords();
          for (const row of data) {
            for (let i = 0; i < soluongTem; i++) {
              for (let j = 0; j < parseInt(row.SOLUONG); j++) {
                let ma_vt = row["SOTHE"];
                let ma_dv = row["MA_DV"];
                let ma_so = row["MASO"];
                let ten_dv = "";
                let ma_cc = row["SOTHE"];
                let ten_cc = row["TEN_TS"];
                let bophan = row["BOPHAN"];
                let nam = row["MA_DV"];
                let dt = this.f_StringToDate(
                  row["THOIGIAN_KK"],
                  "DD/MM/YYYY HH:mm"
                );
                let gioin = this.dateToHour(dt);
                let ngayin = this.f_DateToString(dt);
                let nam_v2 = row["NAM_SD"];

                let item = {
                  serial: `${ma_vt}|${ma_so}`,
                  ma_dv: ma_dv,
                  ten_dv: ten_dv,
                  gioin: 24,
                  ngayin: ngayin,
                  ma_cc: ma_cc,
                  ten_cc: ten_cc,
                  bophan: bophan,
                  nam: `TSCĐ-VT${this.$auth.getMaTinh()}-${ma_dv}`,
                  ma_nvien: `Năm SD:${nam_v2}`,
                  maso: ma_so,
                };
                dsbarcode.push(item);
              }
            }
          }
          if (dsbarcode.length == 0) {
            return this.$toast.error("Hãy chọn các serial cần in");
          }
          this.getPdfBARCODE_CCDC(dsbarcode);
          //In new frmXemBaoCao("BARCODE_CCDC", dsbarcode).XemNgay();
        } else {
          let soluongTem = 0;
          soluongTem = this.params.p_spMuc2;
          let dsbarcode = [];
          let data = this.$refs.grvDS_TS.getSelectedRecords();
          for (const row of data) {
            for (let i = 0; i < soluongTem; i++) {
              for (let j = 0; j < parseInt(row.SOLUONG); j++) {
                let maCabman = row["MA_CABMAN"];
                let sothe = row["SOTHE"];
                let code = row["SOTHE"];
                let ten_ts = row["TEN_TS"];
                let ten_tt = row["TEN_TTVT"];
                let to_vt = row["TO_VT"];

                let item = {
                  serial: `${code}|${maCabman}`,
                  sothe: sothe,
                  ma_cabman: maCabman,
                  ten_ts: ten_ts,
                  ten_tt: ten_tt,
                  to_vt: to_vt,
                };
                dsbarcode.push(item);
              }
            }
          }
          if (dsbarcode.length == 0) {
            return this.$toast.error("Hãy chọn số thẻ cần in");
          }
          console.log(dsbarcode);
          if (!this.params.p_ckbMirror) {
            if (!this.params.p_ckbTietKiem) {
              switch (this.params.p_rdioCKb) {
                case 2.4:
                  //In new frmXemBaoCao("QR-MNV", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("QR-MNV", dsbarcode);
                  break;
                case 1.8:
                  //In new frmXemBaoCao("QR-MNV_2", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("QR-MNV_2", dsbarcode);
                  break;
                case 1.2:
                  //In new frmXemBaoCao("QR-MNV_3", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("QR-MNV_3", dsbarcode);
                  break;
              }
            } else {
              switch (this.params.p_rdioCKb) {
                case 2.4:
                  //In new frmXemBaoCao("TK-MNV", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("TK-MNV_3", dsbarcode);
                  break;
                case 1.8:
                  //In new frmXemBaoCao("TK-MNV_2", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("TK-MNV_3", dsbarcode);
                  break;
                case 1.2:
                  //In new frmXemBaoCao("TK-MNV_3", dsbarcode).XemNgay();
                  this.getPdfBARCODEByNameRP("TK-MNV_3", dsbarcode);
                  break;
              }
            }
          } else {
            switch (this.params.p_rdioCKb) {
              case 2.4:
                dsbarcode;
                //In new frmXemBaoCao("M-2.4", dsbarcode).XemNgay();
                this.getPdfBARCODEByNameRP("M-2.4", dsbarcode);
                break;
              case 1.8:
                //In new frmXemBaoCao("M-1.8", dsbarcode).XemNgay();
                this.getPdfBARCODEByNameRP("M-1.8", dsbarcode);
                break;
              case 1.2:
                //In new frmXemBaoCao("M-1.2", dsbarcode).XemNgay();
                this.getPdfBARCODEByNameRP("M-1.2", dsbarcode);
                break;
            }
          }
        }
      }
    },
    async gridViewThietBi_KeyDown() {},
    async btnLamMoi_Click_1() {
      await this.NAP_DS_CCDC();
    },
    async xtraTabControl1_SelectedPageChanged() {
      this.Visible.tsbtnExcelDK = false;
      this.Visible.tsbtnMauEx = false;
      if (this.xtraTabControl1.SelectedTabPage == "xtraTabPage2") {
        this.Visible.tsbtnExcelDK = true;
        this.Visible.tsbtnMauEx = true;
      }
    },
    async tsbtnMauEx_Click() {
      if (!this.params.p_ckbMNV) {
        let dateStr = this.f_DateToString(new Date(), "DD/MM/YYYY HH:mm");
        let dt = [
          {
            ISCHECK: "0",
            SOTHE: "",
            MASO: "",
            TEN_TS: "",
            NAM_SD: 0,
            MA_DV: "",
            BOPHAN: "",
            THOIGIAN_KK: dateStr,
            SOLUONG: 0,
          },
        ];
        this.SaveXlsAndOpenFile("fileTSCD.xlsx", "TS", dt);
      } else {
        let dt = [
          {
            ISCHECK: "0",
            SOTHE: "",
            MA_CABMAN: "",
            SOLUONG: 1,
            TEN_TTVT: "",
            TO_VT: "",
            TEN_TS: "",
          },
        ];
        this.SaveXlsAndOpenFile("fileMNV.xlsx", "TS", dt);
      }
    },
    async SaveXlsAndOpenFile(fileName, sheetName, exportData) {
      // if (!exportData.length){
      //   this.$toast.error("Không có bản ghi nào để xuất!")
      //   return
      // }
      let data1 = XLSX.utils.json_to_sheet(exportData);
      let wb = XLSX.utils.book_new(); // make Workbook of Excel
      XLSX.utils.book_append_sheet(wb, data1, sheetName); // sheetAName is name of Worksheet
      // export Excel file
      XLSX.writeFile(wb, fileName);
    },
    async tsbtnExcelDK_Click() {
      document.getElementById("fileUpload").click();
    },
    async Load_DL(dt) {
      this.grcDS_TS = dt;
    },
    grvDS_TS_KeyDown() {
      // lam sau
    },
    ckbMNV_CheckedChanged() {
      if (this.params.p_ckbMNV) {
        this.Visible.gridColumn48 = true;
        this.Visible.cabman = true;
        this.Visible.SOLUONG = true;
        this.Enabled.rdioCKb = true;
      } else {
        this.Visible.gridColumn48 = true;
        this.Visible.cabman = false;
        this.Visible.SOLUONG = true;
        this.Enabled.rdioCKb = true;
      }
    },
    resetParams() {
      this.params = Object.assign({}, defaultParams);
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!");
        return false;
      }
      if (
        this.arrayImportFile.filexcel.type !=
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" &&
        this.arrayImportFile.filexcel.type != "application/vnd.ms-excel"
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
        this.loading(true);
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
              headers.forEach((b) => {
                if (!Object.keys(jsonRow).includes(b)) {
                  jsonRow[b] = "";
                }
              });
              return jsonRow;
            });

            await this.Load_DL(JSON.parse(JSON.stringify(result)));
          };
          reader.readAsBinaryString(this.arrayImportFile.filexcel);
        }
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra: " + error);
        return;
      } finally {
        this.$refs.fileUpload.reset();
        this.loading(false);
      }
      this.arrayImportFile.filexcel = null;
    },
    tmrLocDonVi_Tick() {
      if (this.DviWord == this.params.p_txtLocDonVi) {
        this.DviIndex++;
        if (this.DviIndex >= this.DviCache.length) {
          this.DviIndex = 0;
        }
        this.selectedNodes = [this.DviCache[this.DviIndex]];
      } else {
        this.DviIndex = 0;
        this.DviWord = this.params.p_txtLocDonVi;
        this.DviCache = this.tvDonVi
          .filter((a) =>
            a.TEN_DV.toString()
              .toLowerCase()
              .includes(this.params.p_txtLocDonVi.toLowerCase())
          )
          .map((b) => b.DONVI_ID);
        if (this.DviCache.length) {
          this.selectedNodes = [this.DviCache[0]];
        } else {
          this.selectedNodes = ["0"];
        }
      }
    },
  },
  created: async function () {
    await this.frmGanKhoNV_Load();
    // this.btnLamMoi_Click()
  },
  watch: {},
  destroyed() {},
};
</script>
