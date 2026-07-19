<template src="./index.template.html"></template>
<script>
import xlsx from "xlsx";
import DateTimeLib from "../../../utils/DateTimeLib";
import { Sort, Filter, ExcelExport } from "@syncfusion/ej2-vue-grids";
import FormDiaChi from "./DiaChi/FormDiaChi.vue";
import RowCotTemplate from "./RowTemplate/cot.vue";
import RowCongTemplate from "./RowTemplate/cong.vue";
import RowBeTemplate from "./RowTemplate/be.vue";
import FormToaDo from "./ToaDo/FormToaDo.vue";
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});
export default {
  provide: {
    grid: [Sort, Filter, ExcelExport],
  },
  components: {
    FormDiaChi,
    FormToaDo,
  },
  data: function () {
    return {
      dsCot: [],
      danhSachCot: [],
      dsCong: [],
      danhSachCong: [],
      dsBe: [],
      danhSachBe: [],
      fileExcel: null,
      dsLoaiBe: null,
      dsViTri: null,
      dsHuongTamDan: null,
      dsLoaiDuong: null,
      dsLoaiCong: null,
      dsLoaiCot: null,
      dsChuQuan: null,
      dsKieuCot: null,
      dsTTSD: null,
      dsTramBA : null,
      //dsDuAn: null,
      rowTemplateCotOption: function (e) {
        return {
          template: RowCotTemplate,
        };
      },
      rowTemplateCongOption: function (e) {
        return {
          template: RowCongTemplate,
        };
      },
      rowTemplateBeOption: function (e) {
        return {
          template: RowBeTemplate,
        };
      },
      currentData: null,
      doiTuongCap: "COT",
    };
  },
  computed: {
    enabledThucThi: function () {
      if (this.doiTuongCap == "COT") {
        if (!(this.danhSachCot == null || this.danhSachCot.length == 0)) {
          if (this.danhSachCot.length > 0)
            return (
              this.danhSachCot[0].ID == null ||
              this.danhSachCot[0].ID == undefined ||
              this.danhSachCot[0].ID == 0
            );
        }
        return false;
      }
      if (this.doiTuongCap == "CONG") {
        if (!(this.danhSachCong == null || this.danhSachCong.length == 0)) {
          if (this.danhSachCong.length > 0)
            return (
              this.danhSachCong[0].ID == null ||
              this.danhSachCong[0].ID == undefined ||
              this.danhSachCong[0].ID == 0
            );
        }
        return false;
      } else {
        if (!(this.danhSachBe == null || this.danhSachBe.length == 0)) {
          if (this.danhSachBe.length > 0)
            return (
              this.danhSachBe[0].ID == null ||
              this.danhSachBe[0].ID == undefined ||
              this.danhSachBe[0].ID == 0
            );
        }
        return false;
      }
    },
  },
  watch: {
    fileExcel: async function (value) {
      await this.buttonReadFile_Click();
    },
  },
  methods: {
    linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Import_Cot_Cong_Be_v2.xlsx";
    },
    getColumns: function (row) {
      let keys = Object.keys(row);
      let cs = [];
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i];
        cs.push({
          column: key,
          caption: row[key]
        }
        );
      }
      return cs;
    },
    getColumnValue: function (cols, colCaption, row) {
      let result = null;
      let col = cols.find(x => x.caption == colCaption);
      if (!(col == null || col == undefined)) {
        result = row[col.column];
      }
      if (result == undefined)
        result = null;
      return result;
    },
    convertExcelDsCot: function (rows) {
      let result = [];
      if (!(rows == null || rows.length < 2)) {
        const cols = this.getColumns(rows[0]);
        for (let i = 1; i < rows.length; i++) {
          const row = rows[i];
          let item = {
            A: this.getColumnValue(cols, "Số hiệu", row),
            B: this.getColumnValue(cols, "Tên cột", row),
            C: this.getColumnValue(cols, "Độ cao", row),
            D: this.getColumnValue(cols, "Địa chỉ", row),
            E: this.getColumnValue(cols, "Tuyến cột(Nhập chính xác mã tuyến cột)", row),
            F: this.getColumnValue(cols, "Ngày lắp đặt (ngày/tháng/năm)", row),
            G: this.getColumnValue(cols, "Ngày sử dụng (ngày/tháng/năm)", row),
            H: this.getColumnValue(cols, "Loại cột", row),
            I: this.getColumnValue(cols, "Kiểu cột", row),
            J: this.getColumnValue(cols, "Cột sau (Nhập chính xác số hiệu cột sau)", row),
            K: this.getColumnValue(cols, "Khoảng cách cột sau", row),
            L: this.getColumnValue(cols, "Trạng thái sử dụng", row),
            M: this.getColumnValue(cols, "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)", row),
            N: this.getColumnValue(cols, "Chủ quản", row),
            O: this.getColumnValue(cols, "Kinh độ", row),
            P: this.getColumnValue(cols, "Vĩ độ", row),
            Q: this.getColumnValue(cols, "Dự án (Nhập chính xác mã dự án)", row),
            R: this.getColumnValue(cols, "Tài sản (Nhập chính xác mã tài sản)", row),
            S: this.getColumnValue(cols, "MA_CSHT", row),
            T: this.getColumnValue(cols, "MA_GOITHAU_IMS", row),
            U: this.getColumnValue(cols, "MA_HOPDONG_IMS", row),
            V: this.getColumnValue(cols, "TRAMBA_ID", row),
          };
          result.push(item);
        }
      }
      return result;
    },
    convertToDsCotItems: async function () {
      let r = [];
      if (this.dsCot != null) {
        for (let i = 0; i < this.dsCot.length; i++) {
          let v = await this.convertToCotItem(this.dsCot[i]);
          v.STT = i + 1;
          r.push(v);
        }
      }
      return r;
    },
    convertToCotItem: async function (dataItem) {
      // A: 'Số hiệu'
      // B: 'Tên cột'
      // C: 'Độ cao'
      // D: 'Địa chỉ'
      // E: 'Tuyến cột'
      // F: 'Ngày lắp đặt'
      // G: 'Ngày sử dụng'
      // H: 'Loại cột'
      // I: 'Kiểu cột'
      // J: 'Cột sau'
      // K: 'Khoảng cách cột sau'
      // L: 'Trạng thái sử dụng'
      // M: 'Tổ kỹ thuật quản lý'
      // N: 'Chủ quản'
      // O: 'Kinh độ'
      // P: 'Vĩ độ'
      // Q: 'Dự án'
      // R: 'Tài sản'
      // S: 'MA_CSHT'
      // V: Trạm biến áp
      let msg = {};
      let kinhDo = 0;
      if (!this.validateDouble(dataItem.O)) {
        //msg.kinhDo = "Vui lòng nhập đúng định dạng kinh độ (số thực)";
      } else kinhDo = dataItem.O;
      let viDo = 0;
      if (!this.validateDouble(dataItem.P)) {
        //msg.viDo = "Vui lòng nhập đúng định dạng vĩ độ (số thực)";
      } else viDo = dataItem.P;
      let ngayLapDat = null;
      if (!this.validateDate(dataItem.F)) {
        //msg.ngayLapDat = "Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)";
      } else ngayLapDat = DateTimeLib.toDateObject(dataItem.F);
      let ngaySuDung = null;
      if (!this.validateDate(dataItem.G)) {
        //msg.ngaySuDung = "Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)";
      } else ngaySuDung = DateTimeLib.toDateObject(dataItem.G);
      let tuyenCotID = null;
      let tuyenCot = "";
      if (!this.isNullOrEmpty(dataItem.E)) {
        let v = await this.getTuyenCotTheoMa(dataItem.E);
        if (v != null) {
          tuyenCotID = v.TUYENCOT_ID;
          tuyenCot = v.TUYEN_COT;
        } else {
          msg.tuyenCot =
            "Tuyến cột không tồn tại. Kiểm tra lại mã tuyến cột trong file nhập vào";
        }
      } else {
        msg.tuyenCot =
          "Chưa có thông tin tuyến cột. Hãy nhập mã tuyến cột vào cột dữ liệu trong file rồi thử lại";
      }
      let cotSauID = null;
      if (!this.isNullOrEmpty(dataItem.J)) {
        let v = await this.getTuyenCotTheoSoHieu(dataItem.J);
        if (v != null) cotSauID = v.COT_ID;
        else {
          //msg.cotSau = "Cột không tồn tại. Kiểm tra lại số hiệu cột sau trong file nhập vào";
        }
      }
      let donViID = null;
      let donVi = "";
      if (!this.isNullOrEmpty(dataItem.M)) {
        let v = await this.getDonViTheoTen(dataItem.M);
        if (v != null) {
          donViID = v.DONVI_ID;
          donVi = v.TEN_DV;
        } else {
          msg.donVi =
            "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        }
      } else {
        msg.donVi =
          "Chưa có tổ quản lý. Hãy nhập vào mã hoặc tên tổ kỹ thuật quản lý kết cuối vào cột dữ liệu tương ứng trong file rồi thử lại";
      }
      let duAnID = null;
      let taiSanID = null;
      let duAnText = null;
      let taiSanText = null;
      if (!this.isNullOrEmpty(dataItem.R)) {
        let v = await this.getTaiSanDuAnTheoMa(dataItem.R, this.isNullOrEmpty(dataItem.Q) ? null : dataItem.Q);
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
          duAnText = v.MA_CT;
          taiSanText = v.MA_TS;
        } else {
          //msg.duAn = "Không tìm thấy tài sản và dự án tương ứng";
        }
      } else {
        if (!this.isNullOrEmpty(dataItem.Q)) {
          let r = await this.getDuAnTheoSoHieu(dataItem.Q);
          if (r != null) {
            duAnID = r.DUAN_ID;
            duAnText = r.MA_CT;
          } else {
            //msg.duAn = "Không tìm thấy dự án tương ứng";
          }
        }
      }
      let result = {
        ID: null,
        MA_COT: null,
        SO_HIEU: dataItem.A,
        TEN_COT: dataItem.B,
        DOCAO: dataItem.C,
        DIACHI: dataItem.D,
        TUYENCOT: tuyenCot,
        TUYENCOT_ID: tuyenCotID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        LOAICOT_ID: dataItem.H,
        LOAICOT_TEXT: "",
        KIEUCOT_ID: dataItem.I,
        KIEUCOT_TEXT: "",
        COTSAU_ID: cotSauID,
        KC_COTSAU: dataItem.K,
        TTSD_ID: dataItem.L,
        TTSD_TEXT: "",
        DONVI: donVi,
        DONVI_ID: donViID,
        CHUQUAN_TEXT: "",
        CHUQUAN_ID: dataItem.N,
        KINHDO: kinhDo,
        VIDO: viDo,
        DUAN_ID: duAnID,
        DUAN_TEXT: duAnText,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        MA_CSHT: dataItem.S,
        MA_GOITHAU_IMS: dataItem.T,
        MA_HOPDONG_IMS: dataItem.U,
        DIACHI_ID: null,
        TINH_ID: null,
        QUAN_ID: null,
        PHUONG_ID: null,
        PHO_ID: null,
        AP_ID: null,
        KHU_ID: null,
        DACDIEM_ID: null,
        SONHA: null,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "COT",
        TRAMBA_ID: dataItem.V,
        TRAMBA_TEXT: "",
      };
      this.validateCot(result);
      // let msgValidateFields = this.validateFields(result);
      // if (msgValidateFields != "") {
      //   msgValidateFields =
      //     "<div>Vui lòng kiểm tra các trường dữ liệu còn trống: " +
      //     msgValidateFields +
      //     "</div>";
      // }
      // result.MESSAGE += msgValidateFields;
      return result;
    },
    validateCots: function () {
      let rs = true;
      if (this.danhSachCot != null) {
        for (let i = 0; i < this.danhSachCot.length; i++) {
          let r = this.validateCot(this.danhSachCot[i]);
          if (rs) rs = r;
        }
      }

      return rs;
    },
    validateCot: function (item) {
      let rs = true;
      if (this.isNullOrEmpty(item.SO_HIEU)) {
        item.MESSAGE.soHieu = "Số hiệu không được để trống";
        rs = false;
      } else item.MESSAGE.soHieu = "";
      if (this.isNullOrEmpty(item.TEN_COT)) {
        item.MESSAGE.tenCot = "Tên cột không được để trống";
        rs = false;
      } else item.MESSAGE.tenCot = "";
      if (this.isNullOrEmpty(item.DIACHI)) {
        item.MESSAGE.diaChi = "Địa chỉ không được để trống";
        rs = false;
      } else item.MESSAGE.diaChi = "";
      if (this.isNullOrEmpty(item.TRAMBA_ID)) {
        item.MESSAGE.tramba = "Trạm biến áp không được để trống";
        rs = false;
      } else item.MESSAGE.tramba = "";
      if (rs) {
        if (
          !this.isNullOrEmpty(item.MESSAGE.donVi) ||
          !this.isNullOrEmpty(item.MESSAGE.tuyenCot)
        )
          rs = false;
      }
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   else item.TRANGTHAI = "Đã lưu";
      // }
      // item.MESSAGE.isValid = rs;

      let i1 = this.dsLoaiCot.find((x) => x.LOAICOT_ID == item.LOAICOT_ID);
      if (!(i1 == undefined || i1 == null)) {
        item.LOAICOT_TEXT = i1.LOAI_COT;
      }
      let i2 = this.dsKieuCot.find((x) => x.KIEUCOT_ID == item.KIEUCOT_ID);
      if (!(i2 == undefined || i2 == null)) {
        item.KIEUCOT_TEXT = i2.KIEU_COT;
      }
      let i3 = this.dsTTSD.find((x) => x.TTSD_ID == item.TTSD_ID);
      if (!(i3 == undefined || i3 == null)) {
        item.TTSD_TEXT = i3.TRANGTHAI_SD;
      }
      let i4 = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(i4 == undefined || i4 == null)) {
        item.CHUQUAN_TEXT = i4.TENCHUQUAN;
      }
      // let i5 = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      // if (!(i5 == undefined || i5 == null)) {
      //   item.DUAN_TEXT = i5.MA_CT;
      // }


      let i6 = this.dsTramBA.find((x) => x.TRAMBA_ID == item.TRAMBA_ID);
      if (!(i6 == undefined || i6 == null)) {
        item.TRAMBA_TEXT = i6.TEN_TRAMBA;
      }

      this.updateStatus(item);
      return rs;
    },
    convertExcelDsCong: function (rows) {
      let result = [];
      if (!(rows == null || rows.length < 2)) {
        const cols = this.getColumns(rows[0]);
        for (let i = 1; i < rows.length; i++) {
          const row = rows[i];
          let item = {
            A: this.getColumnValue(cols, "Ký hiệu", row),
            B: this.getColumnValue(cols, "Tên đoạn cống", row),
            C: this.getColumnValue(cols, "Tuyến cống bể (Nhập chính xác mã tuyến cống bể)", row),
            D: this.getColumnValue(cols, "Ngày lắp đặt (ngày/tháng/năm)", row),
            E: this.getColumnValue(cols, "Ngày sử dụng (ngày/tháng/năm)", row),
            F: this.getColumnValue(cols, "Số dòng mặt cắt", row),
            G: this.getColumnValue(cols, "Số cột mặt cắt", row),
            H: this.getColumnValue(cols, "Đường kính ống cống(mm)", row),
            I: this.getColumnValue(cols, "Chiều dài(m)", row),
            J: this.getColumnValue(cols, "Độ sâu", row),
            K: this.getColumnValue(cols, "Ký hiệu bể A", row),
            L: this.getColumnValue(cols, "Ký hiệu bể B", row),
            M: this.getColumnValue(cols, "Dự án (Nhập chính xác mã dự án)", row),
            N: this.getColumnValue(cols, "Tài sản (Nhập chính xác mã tài sản)", row),
            O: this.getColumnValue(cols, "Loại cống", row),
            P: this.getColumnValue(cols, "Trạng thái sử dụng", row),
            Q: this.getColumnValue(cols, "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)", row),
            R: this.getColumnValue(cols, "GHICHU", row),
            S: this.getColumnValue(cols, "MA_CSHT", row),
            T: this.getColumnValue(cols, "MA_GOITHAU_IMS", row),
            U: this.getColumnValue(cols, "MA_HOPDONG_IMS", row),
          };
          result.push(item);
        }
      }
      return result;
    },
    convertToDsCongItems: async function () {
      let r = [];
      if (this.dsCong != null) {
        for (let i = 0; i < this.dsCong.length; i++) {
          let v = await this.convertToCongItem(this.dsCong[i]);
          v.STT = i + 1;
          r.push(v);
        }
      }
      return r;
    },
    convertToCongItem: async function (dataItem) {
      // A: 'Ký hiệu'
      // B: 'Tên đoạn cống'
      // C: 'Tuyến cống bể'
      // D: 'Ngày lắp đặt'
      // E: 'Ngày sử dụng'
      // F: 'Số dòng mặt cắt'
      // G: 'Số cột mặt cắt'
      // H: 'Đường kính ống cống (mm)'
      // I: 'Chiều dài (m)'
      // J: 'Độ sâu'
      // K: 'Ký hiệu bể A'
      // L: 'Ký hiệu bể B'
      // M: 'Dự án'
      // N: 'Tài sản'
      // O: 'Loại cống'
      // P: 'Trạng thái sử dụng'
      // Q: 'Tổ kỹ thuật quản lý'
      // R: 'GHICHU'
      // S: 'MA_CSHT'
      let msg = {};
      let ngayLapDat = null;
      if (!this.validateDate(dataItem.D)) {
        //msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
      } else ngayLapDat = DateTimeLib.toDateObject(dataItem.D);
      let ngaySuDung = null;
      if (!this.validateDate(dataItem.E)) {
        //msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
      } else ngaySuDung = DateTimeLib.toDateObject(dataItem.E);
      let tuyenCBID = null;
      let tuyenCB = "";
      if (!this.isNullOrEmpty(dataItem.C)) {
        let v = await this.getTuyenCBTheoMaTCB(dataItem.C);
        if (v != null) {
          tuyenCBID = v.TUYENCB_ID;
          tuyenCB = v.TUYEN_CB;
        } else {
          msg.tuyenCB =
            "Tuyến cống bể không tồn tại. Kiểm tra lại mã tuyến cống bể trong file nhập vào";
        }
      } else {
        msg.tuyenCB =
          "Chưa có thông tin tuyến cống bể. Hãy nhập mã tuyến cống bể vào cột dữ liệu trong file rồi thử lại";
      }
      let beCapAID = null;
      let beCapA = "";
      if (!this.isNullOrEmpty(dataItem.K)) {
        let v = await this.getBeCapTheoKyHieu(dataItem.K);
        if (v != null) {
          beCapAID = v.BECAP_ID;
          beCapA = v.KYHIEU;
        } else {
          msg.beCapA = "Không tồn tại bể cáp A với ký hiệu như trong file";
        }
      } else {
        msg.beCapA =
          "Chưa có thông tin bể cáp A. Hãy nhập ký hiệu bể cáp A vào cột dữ liệu trong file rồi thử lại";
      }
      let beCapBID = null;
      let beCapB = "";
      if (!this.isNullOrEmpty(dataItem.L)) {
        let v = await this.getBeCapTheoKyHieu(dataItem.L);
        if (v != null) {
          beCapBID = v.BECAP_ID;
          beCapB = v.KYHIEU;
        } else {
          msg.beCapB = "Không tồn tại bể cáp B với ký hiệu như trong file";
        }
      } else {
        msg.beCapB =
          "Chưa có thông tin bể cáp B. Hãy nhập ký hiệu bể cáp B vào cột dữ liệu trong file rồi thử lại";
      }
      if (
        !this.isNullOrEmpty(tuyenCBID) &&
        !this.isNullOrEmpty(beCapAID) &&
        !this.isNullOrEmpty(beCapBID)
      ) {
        let r_a = this.getBeCapTheoTuyenCB(beCapAID, tuyenCBID);
        let r_b = this.getBeCapTheoTuyenCB(beCapBID, tuyenCBID);
        if (
          (r_a == null || r_a.length == 0) &&
          (r_b == null || r_b.length == 0)
        ) {
          msg.beCapA =
            "Phải có ít nhất một bể cáp nằm cùng 1 tuyến cống bể với đoạn cống";
          msg.beCapB = msg.beCapA;
        }
      }
      let donViID = null;
      let donVi = "";
      if (!this.isNullOrEmpty(dataItem.Q)) {
        let v = await this.getDonViTheoTen(dataItem.Q);
        if (v != null) {
          donViID = v.DONVI_ID;
          donVi = v.TEN_DV;
        } else {
          msg.donVi =
            "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        }
      } else {
        msg.donVi =
          "Chưa có tổ quản lý. Hãy nhập vào mã hoặc tên tổ kỹ thuật quản lý cáp vào cột dữ liệu tương ứng trong file rồi thử lại";
      }
      let duAnID = null;
      let taiSanID = null;
      let duAnText = null;
      let taiSanText = null;
      if (!this.isNullOrEmpty(dataItem.N)) {
        let v = await this.getTaiSanDuAnTheoMa(dataItem.N, this.isNullOrEmpty(dataItem.M) ? null : dataItem.M);
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
          duAnText = v.MA_CT;
          taiSanText = v.MA_TS;
        } else {
          //msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
        }
      } else {
        if (!this.isNullOrEmpty(dataItem.M)) {
          let r = await this.getDuAnTheoSoHieu(dataItem.M);
          if (r != null) {
            duAnID = r.DUAN_ID;
            duAnText = r.MA_CT;
          } else {
            //msg += "<div>Không tìm thấy dự án tương ứng</div>";
          }
        }
      }
      let result = {
        ID: null,
        MA_DC: null,
        KYHIEU: dataItem.A,
        TEN_DC: dataItem.B,
        TUYENCB: tuyenCB,
        TUYENCB_ID: tuyenCBID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        SODONG_MC: dataItem.F,
        SOCOT_MC: dataItem.G,
        DUONGKINH_OC: dataItem.H,
        CHIEUDAI: dataItem.I,
        DOSAU: dataItem.J,
        BECAP_A: beCapA,
        BECAP_A_ID: beCapAID,
        BECAP_A_HUONG: null,
        BECAP_B: beCapB,
        BECAP_B_ID: beCapBID,
        BECAP_B_HUONG: null,
        DUAN_ID: duAnID,
        DUAN_TEXT: duAnText,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        LOAICONG_ID: dataItem.O,
        LOAICONG_TEXT: "",
        TTSD_ID: dataItem.P,
        TTSD_TEXT: "",
        DONVI: donVi,
        DONVI_ID: donViID,
        CHUQUAN_ID: null,
        CHUQUAN_TEXT: "",
        GHICHU: dataItem.R,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "CONG",
        MA_CSHT: dataItem.S,
        MA_GOITHAU_IMS: dataItem.T,
        MA_HOPDONG_IMS: dataItem.U,
      };
      this.validateCong(result);
      return result;
    },
    validateCongs: function () {
      let rs = true;
      if (this.danhSachCong != null) {
        for (let i = 0; i < this.danhSachCong.length; i++) {
          let r = this.validateCong(this.danhSachCong[i]);
          if (rs) rs = r;
        }
      }
      return rs;
    },
    validateCong: function (item) {
      let rs = true;
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else item.MESSAGE.kyHieu = "";
      if (rs) {
        if (
          !this.isNullOrEmpty(item.MESSAGE.beCapA) ||
          !this.isNullOrEmpty(item.MESSAGE.beCapB) ||
          !this.isNullOrEmpty(item.MESSAGE.donVi) ||
          !this.isNullOrEmpty(item.MESSAGE.tuyenCB)
        )
          rs = false;
      }
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   else item.TRANGTHAI = "Đã lưu";
      // }
      // item.MESSAGE.isValid = rs;

      let i2 = this.dsLoaiCong.find((x) => x.LOAICONG_ID == item.LOAICONG_ID);
      if (!(i2 == undefined || i2 == null)) {
        item.LOAICONG_TEXT = i2.LOAI_CONG;
      }

      let i3 = this.dsTTSD.find((x) => x.TTSD_ID == item.TTSD_ID);
      if (!(i3 == undefined || i3 == null)) {
        item.TTSD_TEXT = i3.TRANGTHAI_SD;
      }
      let i4 = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(i4 == undefined || i4 == null)) {
        item.CHUQUAN_TEXT = i4.TENCHUQUAN;
      }
      // let i5 = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      // if (!(i5 == undefined || i5 == null)) {
      //   item.DUAN_TEXT = i5.MA_CT;
      // }
      this.updateStatus(item);
      return rs;
    },
    convertExcelDsBe: function (rows) {
      let result = [];
      if (!(rows == null || rows.length < 2)) {
        const cols = this.getColumns(rows[0]);
        for (let i = 1; i < rows.length; i++) {
          const row = rows[i];
          let item = {
            A: this.getColumnValue(cols, "Ký hiệu", row),
            B: this.getColumnValue(cols, "Tên bể cáp", row),
            C: this.getColumnValue(cols, "Địa chỉ", row),
            D: this.getColumnValue(cols, "Số hướng bể", row),
            E: this.getColumnValue(cols, "Số tấm đan", row),
            F: this.getColumnValue(cols, "Chiều dài", row),
            G: this.getColumnValue(cols, "Chiều sâu", row),
            H: this.getColumnValue(cols, "Chiều rộng", row),
            I: this.getColumnValue(cols, "Tuyến cống bể (Nhập chính xác mã tuyến cống bể)", row),
            J: this.getColumnValue(cols, "Ngày lắp đặt (ngày/tháng/năm)", row),
            K: this.getColumnValue(cols, "Ngày sử dụng (ngày/tháng/năm)", row),
            L: this.getColumnValue(cols, "Tình trạng nước", row),
            M: this.getColumnValue(cols, "Loại bể cáp", row),
            N: this.getColumnValue(cols, "Vị trí đặt bể", row),
            O: this.getColumnValue(cols, "Hướng tâm đan", row),
            P: this.getColumnValue(cols, "Loại đường", row),
            Q: this.getColumnValue(cols, "Trạng thái sử dụng", row),
            R: this.getColumnValue(cols, "Kinh độ", row),
            S: this.getColumnValue(cols, "Vĩ độ", row),
            T: this.getColumnValue(cols, "Dự án (Nhập chính xác mã dự án)", row),
            U: this.getColumnValue(cols, "Tài sản (Nhập chính xác mã tài sản)", row),
            V: this.getColumnValue(cols, "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)", row),
            W: this.getColumnValue(cols, "Chủ quản", row),
            X: this.getColumnValue(cols, "GHICHU", row),
            Y: this.getColumnValue(cols, "MA_CSHT", row),
          };
          result.push(item);
        }
      }
      return result;
    },
    convertToDsBeItems: async function () {
      let r = [];
      if (this.dsBe != null) {
        for (let i = 0; i < this.dsBe.length; i++) {
          let v = await this.convertToBeItem(this.dsBe[i]);
          v.STT = i + 1;
          r.push(v);
        }
      }
      return r;
    },
    convertToBeItem: async function (dataItem) {
      // A: 'Ký hiệu'
      // B: 'Tên bể cáp'
      // C: 'Địa chỉ'
      // D: 'Số hướng bể'
      // E: 'Số tấm đan'
      // F: 'Chiều dài'
      // G: 'Chiều sâu'
      // H: 'Chiều rộng'
      // I: 'Tuyến cống bể'
      // J: 'Ngày lắp đặt'
      // K: 'Ngày sử dụng'
      // L: 'Tình trạng nước'
      // M: 'Loại bể cáp'
      // N: 'Vị trí đặt bể'
      // O: 'Hướng tâm đan'
      // P: 'Loại đường'
      // Q: 'Trạng thái sử dụng'
      // R: 'Kinh độ'
      // S: 'Vĩ độ'
      // T: 'Dự án'
      // U: 'Tài sản'
      // V: 'Tổ kỹ thuật quản lý'
      // W: 'Chủ quản'
      // X: 'GHICHU'
      // Y: 'MA_CSHT'
      let msg = {};
      let kinhDo = 0;
      if (!this.validateDouble(dataItem.R)) {
        //msg += "<div>Vui lòng nhập đúng định dạng kinh độ (số thực)</div>";
      } else kinhDo = dataItem.R;
      let viDo = 0;
      if (!this.validateDouble(dataItem.S)) {
        //msg += "<div>Vui lòng nhập đúng định dạng vĩ độ (số thực)</div>";
      } else viDo = dataItem.S;
      let ngayLapDat = null;
      if (!this.validateDate(dataItem.J)) {
        //msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
      } else ngayLapDat = DateTimeLib.toDateObject(dataItem.J);
      let ngaySuDung = null;
      if (!this.validateDate(dataItem.K)) {
        //msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
      } else ngaySuDung = DateTimeLib.toDateObject(dataItem.K);
      let tuyenCBID = null;
      let tuyenCB = "";
      if (!this.isNullOrEmpty(dataItem.I)) {
        let v = await this.getTuyenCBTheoMaTCB(dataItem.I);
        if (v != null) {
          tuyenCBID = v.TUYENCB_ID;
          tuyenCB = v.TUYEN_CB;
        } else {
          msg.tuyenCB =
            "Tuyến cống bể không tồn tại. Kiểm tra lại mã tuyến cống bể trong file nhập vào";
        }
      } else {
        msg.tuyenCB =
          "Chưa có thông tin tuyến cống bể. Hãy nhập mã tuyến cống bể vào cột dữ liệu trong file rồi thử lại";
      }
      let donViID = null;
      let donVi = "";
      if (!this.isNullOrEmpty(dataItem.V)) {
        let v = await this.getDonViTheoTen(dataItem.V);
        if (v != null) {
          donViID = v.DONVI_ID;
          donVi = v.TEN_DV;
        } else {
          msg.donVi =
            "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        }
      } else {
        msg.donVi =
          "Chưa có tổ quản lý. Hãy nhập vào mã hoặc tên tổ kỹ thuật quản lý cáp vào cột dữ liệu tương ứng trong file rồi thử lại";
      }
      let duAnID = null;
      let taiSanID = null;
      let duAnText = null;
      let taiSanText = null;
      if (!this.isNullOrEmpty(dataItem.U)) {
        let v = await this.getTaiSanDuAnTheoMa(dataItem.U, this.isNullOrEmpty(dataItem.T) ? null : dataItem.T);
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
          duAnText = v.MA_CT;
          taiSanText = v.MA_TS;
        } else {
          //msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
        }
      } else {
        if (!this.isNullOrEmpty(dataItem.T)) {
          let r = await this.getDuAnTheoSoHieu(dataItem.T);
          if (r != null) {
            duAnID = r.DUAN_ID;
            duAnText = r.MA_CT;
          } else {
            //msg += "<div>Không tìm thấy dự án tương ứng</div>";
          }
        }
      }
      let result = {
        ID: null,
        MA_BC: null,
        KYHIEU: dataItem.A,
        TEN_BC: dataItem.B,
        DIACHI: dataItem.C,
        SOHUONGBE: dataItem.D,
        SOTAMDAN: dataItem.E,
        CHIEUDAI: dataItem.F,
        CHIEUSAU: dataItem.G,
        CHIEURONG: dataItem.H,
        TUYENCB: tuyenCB,
        TUYENCB_ID: tuyenCBID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        TT_NUOC: dataItem.L,
        MAVACH: null,
        LOAIBC_ID: dataItem.M,
        LOAIBC_TEXT: "",
        VITRIDB_ID: dataItem.N,
        VITRIDB_TEXT: "",
        HUONGTD_ID: dataItem.O,
        HUONGTD_TEXT: "",
        LOAIDUONG_ID: dataItem.P,
        LOAIDUONG_TEXT: "",
        TTSD_ID: dataItem.Q,
        TTSD_TEXT: "",
        KINHDO: kinhDo,
        VIDO: viDo,
        DUAN_ID: duAnID,
        DUAN_TEXT: duAnText,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        DONVI: donVi,
        DONVI_ID: donViID,
        CHUQUAN_ID: dataItem.W,
        CHUQUAN_TEXT: "",
        DIACHI_ID: null,
        TINH_ID: null,
        QUAN_ID: null,
        PHUONG_ID: null,
        PHO_ID: null,
        AP_ID: null,
        KHU_ID: null,
        DACDIEM_ID: null,
        SONHA: null,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "BE",
        GHICHU: dataItem.X,
        MA_CSHT: dataItem.Y,
      };
      this.validateBe(result);
      return result;
    },
    validateBes: function () {
      let rs = true;
      if (this.danhSachBe != null) {
        for (let i = 0; i < this.danhSachBe.length; i++) {
          let r = this.validateBe(this.danhSachBe[i]);
          if (rs) rs = r;
        }
      }
      return rs;
    },
    validateBe: function (item) {
      let rs = true;
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else item.MESSAGE.kyHieu = "";
      if (this.isNullOrEmpty(item.TEN_BC)) {
        item.MESSAGE.tenBC = "Tên bể cáp không được để trống";
        rs = false;
      } else item.MESSAGE.tenBC = "";
      if (this.isNullOrEmpty(item.DIACHI)) {
        item.MESSAGE.diaChi = "Địa chỉ không được để trống";
        rs = false;
      } else item.MESSAGE.diaChi = "";
      if (rs) {
        if (
          !this.isNullOrEmpty(item.MESSAGE.donVi) ||
          !this.isNullOrEmpty(item.MESSAGE.tuyenCB)
        )
          rs = false;
      }
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   else item.TRANGTHAI = "Đã lưu";
      // }
      // item.MESSAGE.isValid = rs;

      let i3 = this.dsTTSD.find((x) => x.TTSD_ID == item.TTSD_ID);
      if (!(i3 == undefined || i3 == null)) {
        item.TTSD_TEXT = i3.TRANGTHAI_SD;
      }
      let i4 = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(i4 == undefined || i4 == null)) {
        item.CHUQUAN_TEXT = i4.TENCHUQUAN;
      }
      // let i5 = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      // if (!(i5 == undefined || i5 == null)) {
      //   item.DUAN_TEXT = i5.MA_CT;
      // }
      let i6 = this.dsLoaiBe.find((x) => x.LOAIBC_ID == item.LOAIBC_ID);
      if (!(i6 == undefined || i6 == null)) {
        item.LOAIBC_TEXT = i6.LOAI_BC;
      }
      let i7 = this.dsViTri.find((x) => x.VITRIDB_ID == item.VITRIDB_ID);
      if (!(i7 == undefined || i7 == null)) {
        item.VITRIDB_TEXT = i7.TENVT_DB;
      }
      let i8 = this.dsHuongTamDan.find((x) => x.HUONGTD_ID == item.HUONGTD_ID);
      if (!(i8 == undefined || i8 == null)) {
        item.HUONGTD_TEXT = i8.HUONG_TD;
      }
      let i9 = this.dsLoaiDuong.find(
        (x) => x.LOAIDUONG_ID == item.LOAIDUONG_ID
      );
      if (!(i9 == undefined || i9 == null)) {
        item.LOAIDUONG_TEXT = i9.LOAI_DUONG;
      }
      this.updateStatus(item);
      return rs;
    },
    getBeCapTheoKyHieu: async function (kyHieu) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getBeCapTheoKyHieu",
          {
            kyHieu: kyHieu,
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến cáp " + kyHieu);
        return null;
      }
    },
    getBeCapTheoTuyenCB: async function (beCapID, tuyenCBID) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getBeCapTheoTuyenCB",
          {
            beCapID: beCapID,
            tuyenCBID: tuyenCBID,
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến bể cáp " + beCapID + " theo tuyến cống bể " + tuyenCBID);
        return null;
      }
    },
    getDonViTheoTen: async function (tenDonVi) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getDonViTheoTen",
          {
            tenDonVi: tenDonVi.toString(),
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến đơn vị " + tenDonVi);
        return null;
      }
    },
    getDuAnTheoSoHieu: async function (soHieuCT) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getDuAnTheoSoHieu",
          {
            soHieuCT: soHieuCT.toString(),
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách dự án " + soHieuCT);
        return null;
      }
    },
    getTaiSanDuAnTheoMa: async function (maTS, soHieuCT) {
      try {
        //maTS: mã tài sản
        //soHieuCT: số hiệu chứng từ dự án
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getTaiSanDuAnTheoMa",
          {
            maTS: maTS.toString(),
            soHieuCT: soHieuCT,
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến tài sản " + maTS + " theo dự án " + soHieuCT);
        return null;
      }
    },
    getTuyenCotTheoMa: async function (maTC) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getTuyenCotTheoMa",
          {
            maTC: maTC.toString(),
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến tuyến cột theo mã " + maTC);
        return null;
      }
    },
    getTuyenCotTheoSoHieu: async function (soHieu) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getTuyenCotTheoSoHieu",
          {
            soHieu: soHieu.toString(),
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến tuyến cột theo số hiệu " + soHieu);
        return null;
      }
    },
    getTuyenCBTheoMaTCB: async function (maTCB) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getTuyenCBTheoMaTCB",
          {
            maTCB: maTCB.toString(),
          }
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến tuyến cống bể " + maTCB);
        return null;
      }
    },
    // getDsDuAn: async function () {
    //   let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDuAn");
    //   return rs.data;
    // },
    getDsTaiSan: async function (duAnID) {
      try {
        if (duAnID != null) {
          let rs = await this.$root.context.post(
            "/web-cabman/danhmuc/layDsTaiSan",
            {
              id: duAnID,
            }
          );
          return rs.data;
        } else return [];
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách tài sản theo dự án " + duAnID);
        return null;
      }
    },
    getDsTramBienAp: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsLoaiTramBA"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách trạm biến áp");
        return null;
      }
    },
    getDsLoaiCot: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsLoaiCot"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại cột");
        return null;
      }
    },
    getDsKieuCot: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsKieuCot"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách kiểu cột");
        return null;
      }
    },
    getDsTrangThaiSuDung: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsTrangThaiSuDung"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách trạng thái sử dụng");
        return null;
      }
    },
    getDsChuQuan: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsChuQuan"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách chủ quản");
        return null;
      }
    },
    getDsLoaiCong: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsLoaiCong"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại cống");
        return null;
      }
    },
    getDsLoaiBeCap: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsLoaiBeCap"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại bể cáp");
        return null;
      }
    },
    getDsViTriDatBe: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsViTriDatBe"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách vị trí đặt bể");
        return null;
      }
    },
    getDsHuongTamDan: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsHuongTamDan"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách hướng tâm đan");
        return null;
      }
    },
    getDsLoaiDuong: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsLoaiDuong"
        );
        return rs.data;
      }
      catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại đường");
        return null;
      }
    },
    validateFields: function (dataItem) {
      let keys = Object.keys(dataItem);
      let rs = "";
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i];
        if (key.toUpperCase() != "MESSAGE") {
          if (this.isNullOrEmpty(dataItem[key])) {
            if (rs != "") rs += ", ";
            rs += key;
          }
        }
      }
      return rs;
    },
    validateDate: function (value) {
      try {
        if (value == undefined || value == null) return null;
        else {
          let regEx = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
          return value.match(regEx);
        }
      }
      catch (e) {
        this.$root.toastError(
          "Lỗi định dạng ngày (dd/MM/yyyy) cho giá trị: " + value
        );
        return false;
      }
    },
    validateDouble: function (value) {
      try {
        if (value == undefined || value == null) return null;
        else {
          let regEx = /^(?!0\d)\d*(\.\d+)?$/;
          return value.toString().match(regEx);
        }
      }
      catch (e) {
        this.$root.toastError(
          "Lỗi định dạng số cho giá trị: " + value
        );
        return false;
      }
    },
    isNullOrEmpty: function (value) {
      return (
        value == undefined || value == null || value.toString().trim() == ""
      );
    },
    themDsCot: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachCot.length; i++) {
        if (this.danhSachCot[i].ID == null) {
          if (this.danhSachCot[i].MESSAGE.isValid === true) {
            this.danhSachCot[i].TRANGTHAI = "Đang lưu...";
            let r = await this.themCot(this.danhSachCot[i]);
            if (r != null) {
              count++;
              this.danhSachCot[i].ID = r.COT_ID;
              this.danhSachCot[i].TRANGTHAI = "Đã lưu";
            } else {
              this.danhSachCot[i].TRANGTHAI = "Lưu thất bại!";
            }
          }
        }
      }
      return count;
    },
    themCot: async function (dataItem) {
      let data = {
        apId: dataItem.AP_ID,
        chuquanId: dataItem.CHUQUAN_ID,
        cotId: null,
        cotsauId: dataItem.COTSAU_ID,
        dacdiemId: dataItem.DACDIEM_ID,
        diachi: dataItem.DIACHI,
        diachiId: dataItem.DIACHI_ID,
        doCao: dataItem.DOCAO,
        donviId: dataItem.DONVI_ID,
        duanId: dataItem.DUAN_ID,
        kcCotsau: dataItem.KC_COTSAU,
        khuId: dataItem.KHU_ID,
        kieucotId: dataItem.KIEUCOT_ID,
        kinhdo: dataItem.KINHDO,
        loaicotId: dataItem.LOAICOT_ID,
        maCot: dataItem.MA_COT,
        ngayLd:
          dataItem.NGAY_LD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD)
            : null,
        ngaySd:
          dataItem.NGAY_SD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD)
            : null,
        phoId: dataItem.PHO_ID,
        phuongId: dataItem.PHUONG_ID,
        quanId: dataItem.QUAN_ID,
        soHieu: dataItem.SO_HIEU,
        sonha: dataItem.SONHA,
        taisanId: dataItem.TAISAN_ID,
        tenCot: dataItem.TEN_COT,
        tinhId: dataItem.TINH_ID,
        ttssdId: dataItem.TTSD_ID,
        tuyencotId: dataItem.TUYENCOT_ID,
        vido: dataItem.VIDO,
        maCSHT: dataItem.MA_CSHT,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
        tramba_id : dataItem.TRAMBA_ID,
      };
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/them-cot-v3",
          data
        );
        return rs.data;
      }
      catch (error) {
        let msg = error;
        if (!(error.response == null || error.response == undefined)) {
          msg = error.response.data.message_detail;
          if (msg == null || msg == undefined || msg == "")
            msg = JSON.stringify(error.response.data.data);
        }
        this.$root.toastError(
          "Lỗi thêm cột " + data.soHieu + ": " + msg
        );
      }
      return null;
    },
    themDsCong: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachCong.length; i++) {
        if (this.danhSachCong[i].ID == null) {
          if (this.danhSachCong[i].MESSAGE.isValid === true) {
            this.danhSachCong[i].TRANGTHAI = "Đang lưu...";
            let r = await this.themCong(this.danhSachCong[i]);
            if (r != null) {
              count++;
              this.danhSachCong[i].ID = r.DOANCONG_ID;
              this.danhSachCong[i].TRANGTHAI = "Đã lưu";
            } else {
              this.danhSachCong[i].TRANGTHAI = "Lưu thất bại!";
            }
          }
        }
      }
      return count;
    },
    themCong: async function (dataItem) {
      let data = {
        becapaId: dataItem.BECAP_A_ID,
        becapbId: dataItem.BECAP_B_ID,
        chieuDai: dataItem.CHIEUDAI,
        doSau: dataItem.DOSAU,
        doancongId: null,
        donviId: dataItem.DONVI_ID,
        duanId: dataItem.DUAN_ID,
        duongkinhOc: dataItem.DUONGKINH_OC,
        huongbeA: dataItem.BECAP_A_HUONG,
        huongbeB: dataItem.BECAP_B_HUONG,
        kyHieu: dataItem.KYHIEU,
        loaicongId: dataItem.LOAICONG_ID,
        maDc: dataItem.MA_DC,
        ngayLd:
          dataItem.NGAY_LD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD)
            : null,
        ngaySd:
          dataItem.NGAY_SD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD)
            : null,
        soCap: 0,
        socotMc: dataItem.SOCOT_MC,
        sodongMc: dataItem.SODONG_MC,
        taisanId: dataItem.TAISAN_ID,
        tenDc: dataItem.TEN_DC,
        ttsdId: dataItem.TTSD_ID,
        tuyencbId: dataItem.TUYENCB_ID,
        ghiChu: dataItem.GHICHU,
        maCSHT: dataItem.MA_CSHT,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
        chuquanId: dataItem.CHUQUAN_ID,
      };
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/them-doancong-v2",
          data
        );
        return rs.data;
      }
      catch (error) {
        let msg = error;
        if (!(error.response == null || error.response == undefined)) {
          msg = error.response.data.message_detail;
          if (msg == null || msg == undefined || msg == "")
            msg = JSON.stringify(error.response.data.data);
        }
        this.$root.toastError(
          "Lỗi thêm đoạn cống " + data.kyHieu + ": " + msg
        );
      }
      return null;
    },
    themDsBe: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachBe.length; i++) {
        if (this.danhSachBe[i].ID == null) {
          if (this.danhSachBe[i].MESSAGE.isValid === true) {
            this.danhSachBe[i].TRANGTHAI = "Đang lưu...";
            let r = await this.themBe(this.danhSachBe[i]);
            if (r != null) {
              count++;
              this.danhSachBe[i].ID = r.BECAP_ID;
              this.danhSachBe[i].TRANGTHAI = "Đã lưu";
            } else {
              this.danhSachBe[i].TRANGTHAI = "Lưu thất bại!";
            }
          }
        }
      }
      return count;
    },
    themBe: async function (dataItem) {
      let data = {
        apId: dataItem.AP_ID,
        becapId: null,
        chieuDai: dataItem.CHIEUDAI,
        chieuRong: dataItem.CHIEURONG,
        chuquanId: dataItem.CHUQUAN_ID,
        // cotsauId: null,
        dacdiemId: dataItem.DIACHI_ID,
        diaChi: dataItem.DIACHI,
        diachiId: dataItem.DIACHI_ID,
        donviId: dataItem.DONVI_ID,
        duanId: dataItem.DUAN_ID,
        huongtdId: dataItem.HUONGTD_ID,
        khuId: dataItem.KHU_ID,
        kinhDo: dataItem.KINHDO,
        kyHieu: dataItem.KYHIEU,
        loaibcId: dataItem.LOAIBC_ID,
        loaiduongId: dataItem.LOAIDUONG_ID,
        maBc: dataItem.MA_BC,
        maVach: dataItem.MAVACH,
        ngayLd:
          dataItem.NGAY_LD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD)
            : null,
        ngaySd:
          dataItem.NGAY_SD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD)
            : null,
        phoId: dataItem.PHO_ID,
        phuongId: dataItem.PHUONG_ID,
        quanId: dataItem.QUAN_ID,
        soNha: dataItem.SONHA,
        sohuongBe: dataItem.SOHUONGBE,
        sotamDan: dataItem.SOTAMDAN,
        taisanId: dataItem.TAISAN_ID,
        tenBc: dataItem.TEN_BC,
        tinhId: dataItem.TINH_ID,
        ttNuoc: dataItem.TT_NUOC,
        ttsdId: dataItem.TTSD_ID,
        tuyencbId: dataItem.TUYENCB_ID,
        viDo: dataItem.VIDO,
        vitridbId: dataItem.VITRIDB_ID,
        ghiChu: dataItem.GHICHU,
        maCSHT: dataItem.MA_CSHT,
      };
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/them-becap",
          data
        );
        return rs.data;
      }
      catch (error) {
        let msg = error;
        if (!(error.response == null || error.response == undefined)) {
          msg = error.response.data.message_detail;
          if (msg == null || msg == undefined || msg == "")
            msg = JSON.stringify(error.response.data.data);
        }
        this.$root.toastError(
          "Lỗi thêm bể cáp " + data.kyHieu + ": " + msg
        );
      }
      return null;
    },
    getTextValues: function (dataItems) {
      let rs = [];
      for (let i = 0; i < dataItems.length; i++) {
        const dataItem = dataItems[i];
        rs.push({ Value: dataItem.A, Text: dataItem.B });
      }
      return rs;
    },
    buttonReadFile_Click: async function () {
      if (this.fileExcel != null) {
        let s = this.fileExcel.name.toLowerCase();
        if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
          let reader = new FileReader();
          reader.onload = async (e) => {
            /* Parse data */
            const data = e.target.result;
            const workBook = xlsx.read(data, { type: "binary" });
            if (workBook != null) {
              for (let i = 0; i < workBook.SheetNames.length; i++) {
                const sheetName = workBook.SheetNames[i];
                let rows = [];
                switch (sheetName.toUpperCase()) {
                  case "COT":
                    rows = xlsx.utils.sheet_to_json(
                      workBook.Sheets[sheetName],
                      { header: "A" }
                    );
                    this.dsCot = this.convertExcelDsCot(rows);
                    break;
                  case "CONG":
                    rows = xlsx.utils.sheet_to_json(
                      workBook.Sheets[sheetName],
                      { header: "A" }
                    );
                    this.dsCong = this.convertExcelDsCong(rows);
                    break;
                  case "BE":
                    rows = xlsx.utils.sheet_to_json(
                      workBook.Sheets[sheetName],
                      { header: "A" }
                    );
                    this.dsBe = this.convertExcelDsBe(rows);
                    break;
                  default:
                    break;
                }
              }
            }
            this.$root.showLoading(true);
            try {
              if (this.dsLoaiBe == null)
                this.dsLoaiBe = await this.getDsLoaiBeCap();
              if (this.dsViTri == null)
                this.dsViTri = await this.getDsViTriDatBe();
              if (this.dsHuongTamDan == null)
                this.dsHuongTamDan = await this.getDsHuongTamDan();
              if (this.dsLoaiDuong == null)
                this.dsLoaiDuong = await this.getDsLoaiDuong();
              if (this.dsLoaiCong == null)
                this.dsLoaiCong = await this.getDsLoaiCong();
              if (this.dsLoaiCot == null)
                this.dsLoaiCot = await this.getDsLoaiCot();
              if (this.dsChuQuan == null)
                this.dsChuQuan = await this.getDsChuQuan();
              if (this.dsKieuCot == null)
                this.dsKieuCot = await this.getDsKieuCot();
              if (this.dsTTSD == null)
                this.dsTTSD = await this.getDsTrangThaiSuDung();
              //if (this.dsDuAn == null) this.dsDuAn = await this.getDsDuAn();
              if (this.dsTramBA == null)
                this.dsTramBA = await this.getDsTramBienAp();
              this.danhSachCot = await this.convertToDsCotItems();

              this.danhSachCong = await this.convertToDsCongItems();
              this.danhSachBe = await this.convertToDsBeItems();
            } finally {
              this.$root.showLoading(false);
            }
          };
          reader.readAsBinaryString(this.fileExcel);
        } else {
          this.$root.toastError(
            "File không đúng định dạng cho phép(xls, xlsx) hoặc không đúng biểu mẫu.\nVui lòng thử lại."
          );
        }
      } else this.$root.toastError("Chưa chọn tệp dữ liệu");
    },
    buttonExportFile_Click: function () {
      let r = false;
      //let workBook = xlsx.utils.book_new();
      if (this.doiTuongCap == "COT") {
        if (this.danhSachCot.length > 0) {
          r = true;
          this.$refs.gridCot.excelExport();
          // let sheetCot = xlsx.utils.json_to_sheet(this.danhSachCot);
          // xlsx.utils.book_append_sheet(workBook, sheetCot, "cot");
        }
      } else if (this.doiTuongCap == "CONG") {
        if (this.danhSachCong.length > 0) {
          r = true;
          this.$refs.gridCong.excelExport();
          // let sheetCong = xlsx.utils.json_to_sheet(this.danhSachCong);
          // xlsx.utils.book_append_sheet(workBook, sheetCong, "cong");
        }
      } else if (this.doiTuongCap == "BE") {
        if (this.danhSachBe.length > 0) {
          r = true;
          this.$refs.gridBe.excelExport();
          // let sheetBe = xlsx.utils.json_to_sheet(this.danhSachBe);
          // xlsx.utils.book_append_sheet(workBook, sheetBe, "be");
        }
      }
      if (!r)
        this.$root.toastInfo("Không có dữ liệu để xuất file");
      // if (r) xlsx.writeFile(workBook, "du-lieu.xlsx");
      // else {
      //   this.$root.toastInfo("Không có dữ liệu để xuất file");
      // }
      // if (
      //   this.danhSachCot.length > 0 ||
      //   this.danhSachCong.length > 0 ||
      //   this.danhSachBe.length > 0
      // ) {
      //   let workBook = xlsx.utils.book_new();
      //   let sheetCot = xlsx.utils.json_to_sheet(this.danhSachCot);
      //   xlsx.utils.book_append_sheet(workBook, sheetCot, "cot");
      //   let sheetCong = xlsx.utils.json_to_sheet(this.danhSachCong);
      //   xlsx.utils.book_append_sheet(workBook, sheetCong, "cong");
      //   let sheetBe = xlsx.utils.json_to_sheet(this.danhSachBe);
      //   xlsx.utils.book_append_sheet(workBook, sheetBe, "be");
      //   // export Excel file
      //   xlsx.writeFile(workBook, "du-lieu.xlsx");
      // } else {
      //   this.$root.toastInfo("Không có dữ liệu để xuất file");
      // }
    },
    buttonExecute_Click: async function () {
      this.$root.showLoading(true);
      try {
        let count = 0;
        let total = 0;
        let boqua = 0;
        if (this.doiTuongCap == "COT") {
          total = this.danhSachCot.length;
          count = await this.themDsCot();
          this.$refs.gridCot.refresh();
        } else if (this.doiTuongCap == "CONG") {
          total = this.danhSachCong.length;
          count = await this.themDsCong();
          this.$refs.gridCong.refresh();
        } else if (this.doiTuongCap == "BE") {
          total = this.danhSachBe.length;
          count = await this.themDsBe();
          this.$refs.gridBe.refresh();
        }
        boqua = total - count;
        let msg =
          "Tổng cộng " +
          total +
          " đối tượng trên danh sách:\n-Bỏ qua: " +
          boqua +
          " đối tượng\n-Cập nhật thành công: " +
          count +
          " đối tượng";
        this.$root.toastSuccess(msg);
      } finally {
        this.$root.showLoading(false);
      }
    },
    showModalDiaChi: function (arg) {
      this.currentData = arg;
      this.$refs.modalDiaChi.show();
    },
    modalDiaChi_Ok: function (arg) {
      if (
        this.$refs.formDiaChi.PHO_ID > 0 ||
        this.$refs.formDiaChi.AP_ID > 0 ||
        this.$refs.formDiaChi.KHU_ID > 0
      ) {
        this.currentData.DIACHI_ID = this.$refs.formDiaChi.DIACHI_ID;
        this.currentData.TINH_ID = this.$refs.formDiaChi.TINH_ID;
        this.currentData.QUAN_ID = this.$refs.formDiaChi.QUAN_ID;
        this.currentData.PHUONG_ID = this.$refs.formDiaChi.PHUONG_ID;
        this.currentData.PHO_ID = this.$refs.formDiaChi.PHO_ID;
        this.currentData.AP_ID = this.$refs.formDiaChi.AP_ID;
        this.currentData.KHU_ID = this.$refs.formDiaChi.KHU_ID;
        this.currentData.DACDIEM_ID = this.$refs.formDiaChi.DACDIEM_ID;
        this.currentData.SONHA = this.$refs.formDiaChi.SONHA;
        this.currentData.DIACHI = this.$refs.formDiaChi.DIACHI;

        if (this.currentData.TINH_ID == 0) this.currentData.TINH_ID = null;
        if (this.currentData.QUAN_ID == 0) this.currentData.QUAN_ID = null;
        if (this.currentData.PHUONG_ID == 0) this.currentData.PHUONG_ID = null;
        if (this.currentData.PHO_ID == 0) this.currentData.PHO_ID = null;
        if (this.currentData.AP_ID == 0) this.currentData.AP_ID = null;
        if (this.currentData.KHU_ID == 0) this.currentData.KHU_ID = null;
        if (this.currentData.DACDIEM_ID == 0)
          this.currentData.DACDIEM_ID = null;
        if (this.currentData.DIACHI_ID == 0) this.currentData.DIACHI_ID = null;
        if (this.currentData.SONHA == "") this.currentData.SONHA = null;
        if (this.currentData.DIACHI == "") this.currentData.DIACHI = null;

        this.updateDataSource(this.currentData);
      } else {
        arg.preventDefault();
        this.$bvModal.msgBoxOk("Hãy chọn Phố, Ấp, Khu!", {
          title: "Thông báo",
          size: "sm",
          centered: true,
          okTitle: "Đóng",
          buttonSize: "sm",
        });
      }
    },
    modalDiaChi_Show: async function () {
      await this.$refs.formDiaChi.bindData(this.currentData);
    },
    updateDataSource: function (dataItem) {
      if (dataItem.ITEM_TYPE == "COT") {
        let item = this.danhSachCot.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateCot(item);
        this.$refs.gridCot.refresh();
      } else if (dataItem.ITEM_TYPE == "CONG") {
        let item = this.danhSachCong.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateCong(item);
        this.$refs.gridCong.refresh();
      } else if (dataItem.ITEM_TYPE == "BE") {
        let item = this.danhSachBe.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateBe(item);
        this.$refs.gridBe.refresh();
      }
    },
    updateData: function (item, newItem) {
      if (item != null) {
        let keys = Object.keys(item);
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          if (item[key] != newItem[key]) item[key] = newItem[key];
        }
      }
    },
    updateStatus: function (item) {
      let keys = Object.keys(item.MESSAGE);
      let msg = "";
      if (keys != null) {
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          if (key != "isValid") {
            if (!this.isNullOrEmpty(item.MESSAGE[key]))
              msg += item.MESSAGE[key] + "; ";
          }
        }
      }
      if (msg != "") {
        item.TRANGTHAI = msg.trim();
        item.MESSAGE.isValid = false;
      } else {
        item.TRANGTHAI = "Sẵn sàng";
        item.MESSAGE.isValid = true;
      }
    },
    showModalToaDo: function (arg) {
      this.currentData = arg;
      this.$refs.modalToaDo.show();
    },
    modalToaDo_Ok: function () {
      this.currentData.KINHDO = this.$refs.formToaDo.lng;
      this.currentData.VIDO = this.$refs.formToaDo.lat;
    },
    modalToaDo_Show: async function () {
      await this.$refs.formToaDo.bindData(
        this.currentData.KINHDO,
        this.currentData.VIDO,
        this.currentData.DIACHI
      );
    },
    btnInBanDo_Click: function () {
      this.$refs.formToaDo.printMap();
    }
  },
};
</script>
