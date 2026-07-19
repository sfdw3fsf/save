<template src='./frmNhapKhoTH.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import Vue from "vue";
import breadcrumb from "@/components/breadcrumb";
import frmNhapKhoTHAPI from "../api/frmNhapKhoTHAPI";
import {
  getterName,
  actionName,
  statePropertyName,
  mutationName,
} from "../store/frmnhapkhoth";
import select2 from "@/components/Select2.vue";
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
import XLSX from "xlsx";

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
    },
  },
});

const Keys = {
  Insert: 45,
  Delete: 46,
  Space: 32,
  V: 86,
};

export default {
  components: { XLSX, breadcrumb, appSelect2: select2 },
  name: "frmNhapKhoTH",
  mounted() {},
  provide: {
    grid: [Freeze, Group, Page, Filter, Sort, Resize, ForeignKey],
  },
  computed: {
    ...mapState("frmnhapkhoth", statePropertyName),
    ...mapGetters("frmnhapkhoth", getterName),
    ma_tinh: {
      get() {
        return this.$auth.getMaTinh();
      },
    },
  },
  data() {
    return {
      header: {
        title: "TRA CỨU THIẾT BỊ TỒN KHO",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      disabledKieuPhieu :  true,
      vkho_id: "",
      ten_kho: "",
      ten_dv: "",
      dt_kho: [],

      mucdich_id: "",
      kho_kh: "",
      xdoc_goc: [],
      dtlh: [],
      yeucau_thaydoi: [],
      DSThietBi_Selected: [],
      giaophieu: 0,
      dstb: [],

      IS_USING_SUADONGIA: false, //-1,// Không check
      vtungay: null,
      vdenngay: null,
      params: {
        p_txtTenVatTu: "",
        p_dtpNgay: new Date(),
        p_LoHang: "",
        p_KieuPhieu: "",
        p_txtGhiChu: "",
        p_LoaiKho: "",
        p_MaKhoKT: "",
      },
      cboLoHang: [],
      cboKieuPhieu: [],
      cboLoaiKho: [],
      cboMaKhoKT: [],
      grcDS_VT_DQT: [],

      ctid: "",
      xn: "",

      Visible: {
        cboKieuPhieu: true,
        lblKieuPhieu: true,
        lblLoaiKho: true,
        cboLoaiKho: true,
        lblMaKhoKT: true,
        cboMaKhoKT: true,
      },
      Enable: {
        colDonGia: false,
      },
      // quy them
      grcDS_VT_DQT_FocusedRow: {},
      grcDS_VT_DQT_rowIndex: -1,
    };
  },
  methods: {
    ...mapActions("frmnhapkhoth", actionName),
    ...mapMutations("frmnhapkhoth", mutationName),

    // chuyển Date thành type string
    f_DateToString: function (value, format = "DD/MM/YYYY") {
      return moment(value).format(format);
    },
    f_StringToDate(value, format = "DD/MM/YYYY") {
      return moment(value, format).toDate();
    },
    async func_IS_USING_SUADONGIA() {
      if (this.IS_CHECK_SUADONGIA_ == false) {
        let IS_CHECK_SUADONGIA_ = await this.KiemTraMaThamSo({
          maTS: "CHECK_SUADONGIA",
        });
        if (IS_CHECK_SUADONGIA_.toString() == "1") {
          return 1;
        } else {
          return 0;
        }
      }
      return this.IS_CHECK_SUADONGIA_ == 1;
    },
    resetParams() {
      this.vkho_id = "";
      this.ten_kho = "";
      this.ten_dv = "";
      this.dt_kho = [];
      this.mucdich_id = "";
      this.kho_kh = "";
      this.xdoc_goc = [];
      this.dtlh = [];
      this.yeucau_thaydoi = [];
      this.DSThietBi_Selected = [];
      this.giaophieu = 0;
      this.dstb = [];
      this.IS_USING_SUADONGIA = false; //-1,// Không check
      this.params.p_txtTenVatTu = "";
      this.params.p_dtpNgay = new Date();
      this.params.p_LoHang = "";
      this.params.p_KieuPhieu = "";
      this.params.p_txtGhiChu = "";
      this.params.p_LoaiKho = "";
      this.params.p_MaKhoKT = "";

      this.cboLoHang = [];
      this.cboKieuPhieu = [];
      this.cboLoaiKho = [];
      this.cboMaKhoKT = [];
      this.grcDS_VT_DQT = [];

      this.ctid = "";
      this.xn = "";
    },
    async frmNhapKhoTH() {
      let res_SuaDG = await this.KiemTraMaThamSo({ maTS: "CHECK_SUADONGIA" });
      this.IS_USING_SUADONGIA =
        res_SuaDG != null && res_SuaDG.toString() == "1" ? true : false;
      this.params.p_dtpNgay = new Date();

      if (this.IS_USING_SUADONGIA) {
        this.Enable.colDonGia = true;
      } else {
        this.Enable.colDonGia = false;
      }
      // try {
      this.yeucau_thaydoi = await this.getDSThuHoiVatTu();

      var dt = await this.getDSLoHang({ suaDonGia: this.IS_USING_SUADONGIA });
      this.dtlh = dt;
      this.cboLoHang = dt;

      dt.length ? (this.params.p_LoHang = dt[0].loHang) : "";
      // } catch (error) {
      //   this.$toast.error("Lỗi khi nạp danh sách loại vật tư")
      // }
    },
    grcDSVatTu_DaDK_EmbeddedNavigator_ButtonClick() {},
    async ChinhFont() {
      // lam sau
    },
    async tsbtnHoanThanh_Click() {
      try {
        this.loading(true);
        if (this.params.p_txtGhiChu.trim() == "") {
          this.$toast.error("Bạn phải nhập nội dung chứng từ");
          return;
        }
        if (this.$auth.getMaTinh() == "HCM") {
          if (
            this.params.p_KieuPhieu == null ||
            this.params.p_KieuPhieu == ""
          ) {
            this.$toast.error("Bạn chưa chọn kiểu phiếu!");
            return;
          }
          // if (this.params.p_LoaiKho == null || this.params.p_LoaiKho == "") {
          //   this.$toast.error("Bạn chưa chọn loại kho!");
          //   return;
          // }
          // if (this.params.p_MaKhoKT == null || this.params.p_MaKhoKT == "") {
          //   this.$toast.error("Bạn chưa chọn mã kho KT!");
          //   return;
          // }
          for (let e of this.grcDS_VT_DQT) {
            if (!("LOAIKHO_ID" in e) || e.LOAIKHO == null || e.LOAIKHO == "") {
              this.$toast.error("Bạn chưa chọn loại kho!");
              return;
            }
            if (
              !("MAKHOKT_ID" in e) ||
              e.MAKHOKT_ID == null ||
              e.MAKHOKT_ID == ""
            ) {
              this.$toast.error("Bạn chưa chọn mã kho KT!");
              return;
            }
          }
        }
        var dem = 0;
        var dem_ = 0;
        var dem_dvt = 0;
        var dem_ltbi = 0;
        var i_d = 0;
        let sai_vt = "";

        if (this.grcDS_VT_DQT.length) {
          var dt = this.grcDS_VT_DQT;
          if (dt.length == 0) {
            this.$toast.error("Không có dữ liệu để Nhập kho");
            return;
          }

          if (
            this.IS_USING_SUADONGIA &&
            dt.filter(
              (p) =>
                (p.DONGIA <= 0 && p.LH == "TH") ||
                (p.LH != "TH" && p.DONGIA < 0)
            ).length > 0
          ) {
            this.$toast.error("Thông tin đơn giá chưa đúng");
            return;
          }
          for (let row of dt) {
            if (row["LH"] == null || row["LH"] == "") {
              dem += 1;
            }
            if (row["DVI_TINH"].toString() == "15") {
              dem_dvt += 1;
            }
            let a = row["LOAITBI_ID"];
            if (a != null && a != "") {
              if (a.toString() == "2" && row["SERIAL"].toString() == "") {
                dem_ltbi += 1;
              }
              if (a.toString() != "2" && row["SERIAL"].toString() != "") {
                dem_ltbi += 1;
              }
            }
            if (this.yeucau_thaydoi.includes("KHOA_VATTU")) {
              var checkout = await this.KiemTraHienThiIDVT({
                vatTuId: row["VATTU_ID"],
              });
              if (checkout.length) {
                sai_vt += row["TEN_VT"] + "\r\n";
              }
            }
          }

          if (dem != 0 && this.$auth.getMaTinh() != "HCM") {
            this.$toast.error("Có " + dem + " thiết bị chưa nhập lô hàng");
            return;
          }

          if (dem_dvt != 0) {
            this.$toast.error("Có " + dem_dvt + " đơn vị tính không đúng");
            return;
          }

          if (dem_ltbi != 0) {
            this.$toast.error(
              "Có " + dem_ltbi + " vật tư có serial nhưng mã vật tư không đúng"
            );
            return;
          }

          if (this.yeucau_thaydoi.includes("KHOA_VATTU")) {
            if (sai_vt != "") {
              this.$toast.error(
                "Vật tư đã ngừng sử dụng, không được phép thu hồi !\r\n" +
                  sai_vt
              );
              return;
            }
          }
          var arry = dt
            .filter((x) => x["SERIAL"].toString() != "")
            .map((p) => p["SERIAL"]);

          let findDuplicates = arry.filter(
            (item, index) => arry.indexOf(item) !== index
          );
          let q = findDuplicates.length;

          if (q.length) {
            this.$toast.error("Có " + q.length.toString() + " serial bị trùng");
            return;
          }

          let xdoc = [];
          dt.forEach((t) => xdoc.push({ sr: t["SERIAL"] }));

          let dston = [];
          let s = await this.KiemTraSerialThuHoi({
            serial: JSON.stringify(xdoc),
          });

          if (s != "ok") {
            let loi = s.split("/r/n");
            let kq = "";
            let d = 0;
            for (let sl of loi) {
              if (s1 != "") {
                d += 1;
                dston.push(sl);
              }
            }
            this.loading(false);
            await this.$bvModal
              .msgBoxConfirm(
                "Có " +
                  d +
                  " serial đang tồn tại ở trong kho. Bạn có muốn xuất excel những serial đó không?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              )
              .then(async (value) => {
                if (value) {
                  await this.SaveXlsAndOpenFile("Seriltrung", "", dston);
                  return;
                }
              })
              .catch((err) => {
                // An error occurred
              });
          } else {
            await this.NhapKho(dt);
          }
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
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
    async NhapKho(d) {
      try {
        this.xn = "";
        let js_vtquyettoan = [];
        for (let item of d) {
          if (
            this.f_StringToDate(item["NGAY_CAP"], "DD/MM/YYYY") > new Date()
          ) {
            this.$toast.error(
              "Ngày thu hồi (" +
                item["NGAY_CAP"] +
                ") của serial: " +
                item["SERIAL"] +
                " lớn hơn so với ngày nhập chứng từ"
            );
            return;
          }

          js_vtquyettoan.push({
            PHIEUVT_ID: item.PHIEUVT_ID,
            THIETBI_ID: item.THIETBI_ID,
            MA_TBI: item.MA_TBI,
            SOLUONG: item.SOLUONG,
            DONGIA: item.DONGIA,
            LH: item.LH,
            SERIAL: item.SERIAL != null && item.SERIAL != "" ? "0" : "",
            TAIKHOAN: this.$auth.getMaTinh() == "HCM" ? item.LOAIKHO_ID : "",
            KHOTD_ID: this.$auth.getMaTinh() == "HCM" ? item.MAKHOKT_ID : "",
          });
        }

        let js_phieuquyettoan = [];
        this.dstb.forEach((p) => {
          js_phieuquyettoan.push({
            PHIEU_ID: p.PHIEU_ID,
            PHIEU_CHA_ID: p.PHIEU_CHA_ID,
            HDTB_ID: p.HDTB_ID,
            BAOHONG_ID: p.BAOHONG_ID,
            THUEBAO_ID: p.THUEBAO_ID,
          });
        });
        this.loading(false);
        var isBoxConfirm = false;
        await this.$bvModal
          .msgBoxConfirm("Bạn chắc chắn muốn nhập kho không ?", {
            title: "Thông báo",
            centered: true,
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

        // let ketqua = await this.nhapKhoThuHoi({
        //   data: js_vtquyettoan,
        //   ds_phieu: js_phieuquyettoan,
        //   kho_giao_id: this.kho_kh,
        //   kho_nhan_id: this.vkho_id,
        //   giaophieu: this.giaophieu,
        //   nd_giao: this.params.p_txtGhiChu,
        //   ngay_nhapkho: this.f_DateToString(
        //     this.params.p_dtpNgay,
        //     "DD/MM/YYYY HH:mm:ss"
        //   ),
        //   mucdich_id: this.mucdich_id,
        //   kieuphieu: this.params.p_KieuPhieu,
        //   using_dg: this.IS_USING_SUADONGIA ? 1 : 0,
        // });

        let ketqua = await this.nhapKhoThuHoi_v2({
          data: js_vtquyettoan,
          ds_phieu: js_phieuquyettoan,
          kho_giao_id: this.kho_kh,
          kho_nhan_id: this.vkho_id,
          giaophieu: this.giaophieu,
          nd_giao: this.params.p_txtGhiChu,
          ngay_nhapkho: this.f_DateToString(
            this.params.p_dtpNgay,
            "DD/MM/YYYY HH:mm:ss"
          ),
          mucdich_id: this.mucdich_id,
          kieuphieu: this.params.p_KieuPhieu,
          using_dg: this.IS_USING_SUADONGIA ? 1 : 0,
          tuNgay: this.f_DateToString(this.vtungay),
          denNgay: this.f_DateToString(this.vdenngay),
        });
        this.xn = "1";
        this.ctid = ketqua.CHUNGTU_ID;
        this.Close();
      } catch (e) {
        this.$refs.popup_frmNhapKhoTH.hide();
        this.$toast.error(e.data.message);
        this.loading(false);
      } finally {
        // k tắt loading để load form Nhập Xuất ở form cha
      }
    },

    async frmNhapKhoTH_Load() {
      try {
        this.loading(true);
        this.params.p_txtTenVatTu = this.ten_kho;
        this.grcDS_VT_DQT = [];
        if (this.dt_kho.length) {
          this.grcDS_VT_DQT = this.dt_kho;
          this.grcDS_VT_DQT.forEach((a) => {
            a.NGAY_CAP
              ? (a.NGAY_CAP = this.f_DateToString(
                  this.f_StringToDate(a.NGAY_CAP, "DD-MM-YYYY hh:mm:ss"),
                  "DD/MM/YYYY"
                ))
              : "";
            // Sử dụng đc
            if (
              a.TINHTRANGTBI_ID != null &&
              a.TINHTRANGTBI_ID.toString() == "6"
            ) {
              if (this.yeucau_thaydoi.includes("VATTU_SUDUNGDUOC")) {
                // HNI
                a.LH = "SDD00";
                a.DONGIA = 0;
              }
              if (this.yeucau_thaydoi.includes("LOHANG_TH_TOT")) {
                // HCM
                a.LH = "TH_TOT";
                a.DONGIA = 0;
              }
            }
            // Hỏng
            if (
              a.TINHTRANGTBI_ID != null &&
              a.TINHTRANGTBI_ID.toString() == "5"
            ) {
              if (this.yeucau_thaydoi.includes("LOHANG_TH_XAU")) {
                // HCM
                a.LH = "TH_XAU";
                a.DONGIA = 0;
              }
            }
          });
        }
       
        if (this.$auth.getMaTinh() != "HCM") {
          this.Visible.cboKieuPhieu = false;
          this.Visible.lblKieuPhieu = false;
          this.Visible.lblLoaiKho = false;
          this.Visible.cboLoaiKho = false;
          this.Visible.lblMaKhoKT = false;
          this.Visible.cboMaKhoKT = false;
        } else {
          let cboKieuPhieu = await this.getDSKieuPhieu({ loaiCtId: 1 });          
          this.cboKieuPhieu =  cboKieuPhieu.filter(e=> e.mucDichId == 10178)
          this.params.p_KieuPhieu = 10178; //Thu hồi VTTB-Thuê Bao
        }
      } catch (error) {
        this.$toast.error(error.data.message);
      } finally {
        this.loading(false);
      }
    },
    async grvDS_VT_DQT_CellValueChanged(args) {
      console.log(args);
      let grvDS_VT_DQT = this.$refs.grvDS_VT_DQT;
      let changes = grvDS_VT_DQT.getBatchChanges();
      var row = args.rowData;
      if (args.columnName != "ISUPDATE") {
        var sr = args.value;
        var sr_goc = row["GOC"];

        if (sr != sr_goc) {
          changes.changedRecords[0].ISUPDATE = "1";
        } else {
          changes.changedRecords[0].ISUPDATE = "0";
        }
      }

      if (args.columnName == "DONGIA" && this.IS_USING_SUADONGIA) {
        var dongia = args.value;
        if (
          this.dtlh.filter((item) => item.donGia == dongia && item.lh != "TH")
            .length
        ) {
          changes.changedRecords[0].LH = "";
        } else {
          if (!this.yeucau_thaydoi.includes("CHON_LOHANG_BATKY_KHI_SUA_DG")) {
            changes.changedRecords[0].LH = "TH";
          }
        }
      }

      if (args.columnName == "SERIAL") {
        var sr_goc = row["GOC"];
        if (this.yeucau_thaydoi.includes("TUDONG_THEM_DUOI_SERIAL")) {
          var check_check = await this.KiemTraSLThietBiTrongKho({
            serial: args.value,
          });
          if (check_check.length) {
            await this.$bvModal
              .msgBoxConfirm(
                "Serial" +
                  args.value +
                  " đã tồn tại trong kho. Bạn có muốn thêm đuôi .S1 vào serial không ?",
                {
                  okVariant: "success",
                  okTitle: "Đồng ý",
                  cancelTitle: "Hủy",
                  centered: true,
                }
              )
              .then(async (value) => {
                if (value) {
                  changes.changedRecords[0].SERIAL = args.value + ".S1";
                } else {
                  changes.changedRecords[0].SERIAL = sr_goc;
                }
                grvDS_VT_DQT.batchUpdate(changes);
              })
              .catch((err) => {
                // An error occurred
              });
          }
        }
      }
      grvDS_VT_DQT.batchUpdate(changes);
      this.refreshGrid();
    },
    grvDS_VT_DQT_selected(data) {
      this.DSThietBi_Selected = data;
      console.log(this.DSThietBi_Selected, " = DSThietBi_Selected");
    },
    LoaiKhoDown_Click() {
      if (this.params.p_LoaiKho == null || this.params.p_LoaiKho == "") {
        this.$toast.error("Bạn chưa chọn loại kho!");
        return;
      }
      if (this.DSThietBi_Selected.length == 0) {
        this.$toast.error("Chưa chọn thiết bị!");
        return;
      }
      let LoaiKhoDaChon = this.cboLoaiKho.filter(
        (e) => e.loaiKhoTdId == this.params.p_LoaiKho
      );
      if (LoaiKhoDaChon.length == 0) {
        this.$toast.error("Không lấy được thông tin loại kho");
        return;
      }

      for (let e of this.grcDS_VT_DQT) {
        if (this.DSThietBi_Selected.includes(e.THIETBI_ID)) {
          e.LOAIKHO = LoaiKhoDaChon[0].tenLk;
          e.LOAIKHO_ID = this.params.p_LoaiKho;
        }
      }
      this.$refs.grvDS_VT_DQT.grid.refreshColumns();
    },
    MaKhoKTDown_Click() {
      if (this.params.p_LoaiKho == null || this.params.p_LoaiKho == "") {
        this.$toast.error("Bạn chưa chọn loại kho!");
        return;
      }
      if (this.params.p_MaKhoKT == null || this.params.p_MaKhoKT == "") {
        this.$toast.error("Bạn chưa chọn mã kho KT!");
        return;
      }
      if (this.DSThietBi_Selected.length == 0) {
        this.$toast.error("Chưa chọn thiết bị!");
        return;
      }

      let LoaiKhoDaChon = this.cboLoaiKho.filter(
        (e) => e.loaiKhoTdId == this.params.p_LoaiKho
      );
      if (LoaiKhoDaChon.length == 0) {
        this.$toast.error("Không lấy được thông tin loại kho");
        return;
      }

      let MaKhoKhoDaChon = this.cboMaKhoKT.filter(
        (e) => e.khoTDId == this.params.p_MaKhoKT
      );
      if (MaKhoKhoDaChon.length == 0) {
        this.$toast.error("Không lấy được thông tin mã kho");
        return;
      }

      for (let e of this.grcDS_VT_DQT) {
        if (this.DSThietBi_Selected.includes(e.THIETBI_ID)) {
          e.MAKHOKT = MaKhoKhoDaChon[0].maKhoTd;
          e.MAKHOKT_ID = this.params.p_MaKhoKT;
          e.LOAIKHO = LoaiKhoDaChon[0].tenLk;
          e.LOAIKHO_ID = this.params.p_LoaiKho;
        }
      }
      this.$refs.grvDS_VT_DQT.grid.refreshColumns();
    },
    async grcDS_VT_DQT_KeyDown(args) {
      console.log(args, " = keyPressed");
      if (args.name == "keyPressed" && args.keyCode == Keys.Delete) {
        this.grcDS_VT_DQT.forEach((row) => {
          row["LH"] = "";
          row["DONGIA"] = 1;
        });
        this.refreshGrid();
      }
      if (args.name == "keyPressed" && args.keyCode == Keys.Insert) {
        this.grcDS_VT_DQT.forEach((row) => {
          row["LH"] = this.params.p_LoHang;
          var q = this.dtlh.filter((l) => l.loHang == this.params.p_LoHang);
          if (q.length) {
            row["DONGIA"] = q[0]["donGia"];
          }
        });
        this.refreshGrid();
      }
      if (args.name == "keyPressed" && args.keyCode == Keys.Space) {
        this.grcDS_VT_DQT.forEach((row) => {
          if (this.DSThietBi_Selected.includes(row.THIETBI_ID)) {
            if (this.params.p_LoHang != "") {
              row.LH = this.params.p_LoHang;
              var q = this.dtlh.filter((l) => l.loHang == this.params.p_LoHang);
              if (q.length) {
                row.DONGIA = q[0]["donGia"];
              }
            } else {
              row.LH = "";
              row.DONGIA = 1;
            }
          }
        });
        this.refreshGrid();
      }
      if (args.name == "keyPressed" && args.keyCode == Keys.V) {
        this.grcDS_VT_DQT.forEach((row) => {
          if (this.DSThietBi_Selected.includes(row.THIETBI_ID)) {
            if (this.yeucau_thaydoi.includes("VATTU_HONG")) {
              if (row["TINHTRANGTBI_ID"].toString() == "5") {
                if (this.params.p_LoHang == "SDD00") {
                  this.$toast.error(
                    row["TEN_VT"] +
                      ": vật tư hỏng, không được chọn lô hàng SDD00 !"
                  );
                  return;
                }
              }
            }
            if (this.yeucau_thaydoi.includes("VATTU_SUDUNGDUOC")) {
              if (row["TINHTRANGTBI_ID"].toString() == "6") {
                if (
                  this.params.p_LoHang.startsWith("KSD") ||
                  this.params.p_LoHang.startsWith("BH")
                ) {
                  this.$toast.error(
                    row["TEN_VT"] +
                      ": vật tư sử dụng được, không được chọn lô hàng KSD và BH !"
                  );
                  return;
                }
              }
            }
            row["LH"] = this.params.p_LoHang;
            var q = this.dtlh.filter((l) => l.loHang == this.params.p_LoHang);
            if (q.length) {
              row["DONGIA"] = q[0]["donGia"];
            }
          }
        });
        this.refreshGrid();
      }
    },
    refreshGrid() {
      this.$refs.grvDS_VT_DQT.grid.refreshColumns();
      this.$refs.grvDS_VT_DQT.grid.refresh();
    },
    async grvDS_VT_DQT_RowStyle(args) {
      let str = args.data.LH;
      let dg = args.data.DONGIA;
      let dvt = args.data.DVI_TINH;
      // code C# ko sd
      // let serial = args.data.SERIAL
      // let loaitb_id = args.data.LOAITBI_ID

      if (str == null || str == "" || dvt == null || dvt == "") {
        args.row.bgColor = "#FFC0CB";
      } else {
        if (str == "TH") {
          if (this.IS_USING_SUADONGIA) {
            if (dg == null || dg == "" || dg.toString() == "-1") {
              args.row.bgColor = "#FFC0CB";
            }
          }
        }
        var kt = args.data.ISUPDATE;
        if (kt.toString() == "1") {
          args.row.bgColor = "#FFFF00";
        }
      }
    },
    async btnTimKiem_Click() {
      this.grcDS_VT_DQT = await this.getDSPhieuVatTu({
        data: JSON.stringify(this.xdoc_goc),
        status: 1,
        kieuTBi: 3,
      });
      // quy bo sung convert Date
      this.grcDS_VT_DQT.forEach((a) => {
        a.NGAY_CAP
          ? (a.NGAY_CAP = this.f_DateToString(
              this.f_StringToDate(a.NGAY_CAP, "DD-MM-YYYY hh:mm:ss"),
              "DD/MM/YYYY"
            ))
          : "";
      });
    },
    async tsbtnXuatExcel_Click() {
      // await this.HT_DLG_XUAT_EXCEL(this.grcDS_VT_DQT, "DSThuHoi")
      await this.SaveXlsAndOpenFile(
        "DSThuHoi.xlsx",
        "DSThuHoi",
        this.grcDS_VT_DQT
      );
    },
    IsValidFileName(fname) {
      // return fname.ToCharArray().Any(x => Path.GetInvalidFileNameChars().Contains(x));
      return true;
    },
    async HT_DLG_XUAT_EXCEL() {
      // lam sau
    },
    lnkXuatExcel_ProgressChanged() {},
    async cboKieuPhieu_EditValueChanged() {
      
      if (this.params.p_KieuPhieu != null && this.params.p_KieuPhieu != "") {
        this.cboLoaiKho = await this.getDSLoaiKho({
          mucDichId: this.params.p_KieuPhieu,
        });
      }
    },
    async cboLoaiKho_EditValueChanged() {
      if (this.params.p_LoaiKho != null && this.params.p_LoaiKho != "") {
        this.cboMaKhoKT = await this.getDSMaKhoTapDoan({
          loaiKhoTdId: this.params.p_LoaiKho,
        });
      }
    },
    async Close() {
      this.$refs.popup_frmNhapKhoTH.hide();
      await this.$parent.XacNhan_frmNhapKhoTH();      
    },
    async ShowDialog() {
      this.$refs.popup_frmNhapKhoTH.show();
    },
    async selected_grcDS_VT_DQT(args) {
      if (args) {
        this.grcDS_VT_DQT_FocusedRow = args.rowData;
        this.grcDS_VT_DQT_rowIndex = args.rowIndex;
      }
    },
  },
  created: async function () {},
  watch: {},
  destroyed() {},
};
</script>
<style>
.xuly-thietbibaohanh .e-grid tr.e-emptyrow td {
  text-align: center !important;
}
</style>