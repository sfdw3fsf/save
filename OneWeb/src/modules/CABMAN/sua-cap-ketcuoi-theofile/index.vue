<template src="./index.template.html"></template>
<script>
import xlsx from "xlsx";
import DateTimeLib from "../../../utils/DateTimeLib";
import { Sort, Filter, ExcelExport, Freeze, Resize } from "@syncfusion/ej2-vue-grids";
import FormDiaChi from "../nhap-cot-doancong-becap-theofile/DiaChi/FormDiaChi.vue";
import RowCapTemplate from "./RowTemplate/cap.vue";
import RowKetCuoiTemplate from "./RowTemplate/ketcuoi.vue";
import FormToaDo from "../nhap-cot-doancong-becap-theofile/ToaDo/FormToaDo.vue";
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
    grid: [Sort, Filter, ExcelExport, Freeze, Resize],
  },
  components: {
    FormDiaChi,
    FormToaDo,
  },
  data: function () {
    return {
      dsPhanLoaiKC: null,
      dsLoaiKC: null,
      dsCapSP: null,
      dsKieuLap: null,
      dsNoiSX: null,
      dsViTriLapDat: null,
      dsLoaiVo: null,
      dsKichCoCap: null,
      dsDienCT: null,
      dsLoaiCap: null,
      dsLopMC: null,
      dsNhomLC: null,
      dsKieuDC: null,
      dsKieuCap: null,
      dsToaNha: null,
      dsToQuanLy: null,
      dsTramTB: null,

      dsChuQuan: null,
      dsTTSD: null,
      dsDuAn: null,
      tenTinh: null,
      dsAddressCheck: [],
      dsExcelCap: [],
      danhSachCap: [],
      dsExcelKetCuoi: [],
      danhSachKetCuoi: [],
      fileExcel: null,
      headerKetCuoi: {},
      headerCap: {},
      rowTemplateCapOption: function (e) {
        return {
          template: RowCapTemplate,
        };
      },
      rowTemplateKetCuoiOption: function (e) {
        return {
          template: RowKetCuoiTemplate,
        };
      },
      dsKetCuoiTheoKyHieu: new Map(),
      dsTramTBTheoKyHieu: new Map(),
      dsCapGocTheoKyHieu: new Map(),
      dsTaiSanDuAnTheoMa: new Map(),
      dsDuAnTheoMaCT: new Map(),
      dsDuAnTheoDuAnId: new Map(),
      currentData: null,
      doiTuongCap: "KETCUOI",
      IS_FORM_EDIT: true,
      TUKHOA: {
        XOA: "NULL"
      }
    };
  },
  computed: {
    enabledThucThi: function () {
      if (this.doiTuongCap == "KETCUOI") {
        if (!(this.danhSachKetCuoi == null || this.danhSachKetCuoi.length == 0)) {
          return this.danhSachKetCuoi.length > 0;
        }
        return false;
      } else {
        if (!(this.danhSachCap == null || this.danhSachCap.length == 0)) {
          return this.danhSachCap.length > 0;
        }
        return false;
      }
    },
    gridKCCssClass: function () {
      let result = "grid-ext";
      if (!this.danhSachKetCuoi || !this.danhSachKetCuoi.length) {
        result += " grid-ext-nodata";
      }
      result += " grid-ext-freeze";
      return result;
    },
    gridCCssClass: function () {
      let result = "grid-ext";
      if (!this.danhSachCap || !this.danhSachCap.length) {
        result += " grid-ext-nodata";
      }
      result += " grid-ext-freeze";
      return result;
    },
  },
  watch: {
    doiTuongCap: function (val) {
      if (val == "KETCUOI") {
        this.danhSachKetCuoi = [...this.danhSachKetCuoi];
      } else this.danhSachCap = [...this.danhSachCap];
    },
    fileExcel: async function (value) {
      await this.buttonReadFile_Click();
    },
    danhSachKetCuoi: async function (val) {
      // remove element trangthai dư thừa do custom freeze + row template
      if (val.length > 0) {
        await this.$refs.gridKetCuoi.refresh();
        this.removeCellFrozen();
      }
    },
    danhSachCap: async function (val) {
      // remove element trangthai dư thừa do custom freeze + row template
      if (val.length > 0) {
        await this.$refs.gridCap.refresh();
        this.removeCellFrozen();
      }
    },
  },
  methods: {
    removeCellFrozen() {
      setTimeout(() => {
        $(".e-movablecontent").children().children().find("#trangthai").remove();
      }, 1000);
    },
    linkTaiFileMau_Click: async function () {
      this.$root.showLoading(true);
      try {
        let r = await this.$root.context.download(
          "POST",
          "web-cabman/excel/download-mau-capnhat-cap-ketcuoi",
          null
        );
        if (r && r.status == 200) {
          let fileName =
            "MAU_CAPNHAT_CAP_KETCUOI_" + this.$root.token.getMaTinh() + ".xlsx";
          if (!(r.headers == undefined || r.headers == null)) {
            let keys = Object.keys(r.headers);
            for (let i = 0; i < keys.length; i++) {
              const key = keys[i];
              if (key.toLowerCase() == "content-disposition") {
                if (r.headers[key].toLowerCase().indexOf("filename=") >= 0) {
                  let vs = r.headers[key].split(";");
                  vs.forEach((v) => {
                    if (v.includes("filename")) {
                      fileName = v.split("=")[1];
                      fileName = fileName.replaceAll('"', "");
                    }
                  });
                }
                break;
              }
            }
          }
          let tagA = document.createElement("a");
          tagA.href = window.URL.createObjectURL(new Blob([r.data]));
          tagA.download = fileName;
          document.body.appendChild(tagA);
          tagA.click();
          tagA.remove();
        }
      } catch (e) {
        this.$root.toastError(
          "Có lỗi xảy ra trong quá trình tải file. Vui lòng thử lại."
        );
      } finally {
        this.$root.showLoading(false);
      }
    },
    getColumns: function (row) {
      let keys = Object.keys(row);
      let cs = [];
      for (let i = 0; i < keys.length; i++) {
        const key = keys[i];
        cs.push({
          column: key,
          caption: row[key],
        });
      }
      return cs;
    },
    getColumnValue: function (cols, colCaption, row) {
      let result = null;
      let col = cols.find((x) => x.caption == colCaption);
      if (!(col == null || col == undefined)) {
        result = row[col.column];
      }
      if (result == undefined) result = null;
      return result;
    },
    convertExcelDsKetCuoi: function (rows) {
      let result = [];
      if (!(rows == null || rows.length < 2)) {
        const cols = this.getColumns(rows[0]);
        for (let i = 1; i < rows.length; i++) {
          const row = rows[i];
          let item = {
            A: this.getColumnValue(cols, "Kết cuối ID", row),
            B: this.getColumnValue(cols, "Ký hiệu", row),
            C: this.getColumnValue(cols, "Tên kết cuối", row),
            D: this.getColumnValue(cols, "Dung lượng lắp đặt", row),
            E: this.getColumnValue(cols, "Cấp splitter", row),
            F: this.getColumnValue(cols, "Địa chỉ", row),
            G: this.getColumnValue(cols, "Trạng thái sử dụng", row),
            H: this.getColumnValue(cols, "Kiểu lắp", row),
            I: this.getColumnValue(cols, "Nơi sản xuất", row),
            J: this.getColumnValue(cols, "Vị trí", row),
            K: this.getColumnValue(cols, "chuquan_id", row),
            L: this.getColumnValue(cols, "Loại vỏ", row),
            M: this.getColumnValue(cols, "Kinh độ", row),
            N: this.getColumnValue(cols, "Vĩ độ", row),
            O: this.getColumnValue(
              cols,
              "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)",
              row
            ),
            P: this.getColumnValue(cols, "Dự án (Nhập chính xác mã dự án)", row),
            Q: this.getColumnValue(cols, "Tài sản (Nhập chính xác mã tài sản)", row),
            R: this.getColumnValue(cols, "Toà nhà (Nhập chính xác mã toà nhà)", row),
            S: this.getColumnValue(cols, "Ngày sử dụng(dd/MM/yyyy)", row),
            T: this.getColumnValue(cols, "Ngày lắp đặt(dd/MM/yyyy)", row),
            U: this.getColumnValue(cols, "Ghi chú", row),
            V: this.getColumnValue(cols, "MA_CSHT", row),
            W: this.getColumnValue(cols, "MA_GOITHAU_IMS", row),
            X: this.getColumnValue(cols, "MA_HOPDONG_IMS", row),
            Y: this.getColumnValue(cols, "Ký hiệu mới", row),
            Z: this.getColumnValue(cols, "Tên kết cuối mới", row),
            AA: this.getColumnValue(cols, "Loại kết cuối", row),
            AG: this.getColumnValue(cols, "Số nhà", row),
            AB: this.getColumnValue(cols, "Quận/Huyện", row),
            AC: this.getColumnValue(cols, "Phường/Xã", row),
            AD: this.getColumnValue(cols, "Đường/Phố", row),
            AE: this.getColumnValue(cols, "Khu", row),
            AF: this.getColumnValue(cols, "Ngõ/Ấp", row),
            AH: this.getColumnValue(cols, "Ký hiệu kết cuối cha", row),
          };
          result.push(item);
        }
        // console.log("Bắt đầu check kết cuối", "");
        // let kQCheckKetCuoi =  this.checkAddressKetCuoi();
      }
      return result;
    },
    convertToDsKetCuoiItems: async function () {
      let r = [];
      if (this.dsExcelKetCuoi != null) {
        let dsID_EXCEL = [];
        let dsSO_HIEU_EXCEL = [];
        let dsKyHieuKC_CHA = [];
        for (let j = 0; j < this.dsExcelKetCuoi.length; j++) {
          const ketCuoi = this.dsExcelKetCuoi[j];
          ketCuoi.AH_ID = null;
          ketCuoi.AH_TEN = null;
          if (!(this.isNullOrEmpty(ketCuoi.AH) || dsID_EXCEL.includes(ketCuoi.AH))) {
            if(ketCuoi.AH.toString().toUpperCase() == this.TUKHOA.XOA){
              ketCuoi.AH_ID = -1;
              ketCuoi.AH = null;
            }
            else {
              dsKyHieuKC_CHA.push(ketCuoi.AH);
            }
          }
          if (!(this.isNullOrEmpty(ketCuoi.A) || dsID_EXCEL.includes(ketCuoi.A))) {
            dsID_EXCEL.push(ketCuoi.A);
          }
          if (!(this.isNullOrEmpty(ketCuoi.B) || dsSO_HIEU_EXCEL.includes(ketCuoi.B))) {
            dsSO_HIEU_EXCEL.push(ketCuoi.B);
          }
        }
        if (
          dsID_EXCEL.length == this.dsExcelKetCuoi.length ||
          dsSO_HIEU_EXCEL.length == this.dsExcelKetCuoi.length
        ) {
          let useID = dsID_EXCEL.length == this.dsExcelKetCuoi.length;
          let dsItemUpdate = await this.getDsKetCuoiUpdate(
            useID,
            useID ? dsID_EXCEL : dsSO_HIEU_EXCEL
          );
          if (!(dsItemUpdate == null || dsItemUpdate.length == 0)) {
            let dsKyHieuKC_CHA_DB = await this.getDsKetCuoiIDsTheoKyHieus(dsKyHieuKC_CHA);
            for (let i = 0; i < this.dsExcelKetCuoi.length; i++) {
              if(!(this.isNullOrEmpty(this.dsExcelKetCuoi[i].AH) || dsKyHieuKC_CHA_DB.length == 0)){
                let kc_cha = dsKyHieuKC_CHA_DB.find(x => x.KYHIEU == this.dsExcelKetCuoi[i].AH);
                if(kc_cha != null){
                  this.dsExcelKetCuoi[i].AH_ID = kc_cha.KETCUOI_ID;
                  this.dsExcelKetCuoi[i].AH_TEN = kc_cha.TEN_KC;
                }
              }
              let currentItem = null;
              if (useID)
                currentItem = dsItemUpdate.find(
                  (x) => x.KETCUOI_ID == this.dsExcelKetCuoi[i].A
                );
              else
                currentItem = dsItemUpdate.find(
                  (x) => x.KYHIEU == this.dsExcelKetCuoi[i].B
                );
              if (!(currentItem == null || currentItem == undefined)) {
                let v = await this.convertToKetCuoiItem(
                  this.dsExcelKetCuoi[i],
                  currentItem
                );
                v.STT = i + 1;
                r.push(v);
              }
            }
          }
        } else {
          this.$root.toastError(
            "Trường kết cuối id hoặc số hiệu kết cuối đang bị thiếu dữ liệu hoặc bị trùng lặp. \nVui lòng bổ sung và thử lại."
          );
        }
      }
      return r;
    },
    convertToKetCuoiItem: async function (dataItem, currentItem) {
      // A: 'Kết cuối ID'
      // B: 'Ký hiệu'
      // C: 'Tên kết cuối'
      // D: 'Dung lượng lắp đặt'
      // E: 'Cấp splitter'
      // F: 'Địa chỉ'
      // G: 'Trạng thái sử dụng'
      // H: 'Kiểu lắp'
      // I: 'Nơi sản xuất'
      // J: 'Vị trí'
      // K: 'chuquan_id'
      // L: 'Loại vỏ'
      // M: 'Kinh độ'
      // N: 'Vĩ độ'
      // O: 'Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)'
      // P: 'Dự án (Nhập chính xác mã dự án)'
      // Q: 'Tài sản (Nhập chính xác mã tài sản)'
      // R: 'Toàn nhà (Nhập chính xác mã toàn nhà)'
      // S: 'Ngày sử dụng(dd/MM/yyyy)'
      // T: 'Ngày lắp đặt(dd/MM/yyyy)'
      // U: 'Ghi chú'
      // AA: this.getColumnValue(cols, "Loại kết cuối", row),
      // AB: this.getColumnValue(cols, "Quận/Huyện", row),
      // AC: this.getColumnValue(cols, "Phường/Xã", row),
      // AD: this.getColumnValue(cols, "Đường/Phố", row),
      // AE: this.getColumnValue(cols, "Khu", row),
      // AF: this.getColumnValue(cols, "Ngõ/Ấp", row),
      let msg = {};
      let dungLuong = 0;
      if (!this.isNullOrEmpty(dataItem.D)) {
        if (!this.validateDouble(dataItem.D)) {
          //msg += "<div>Vui lòng nhập đúng định dạng dung lượng (số thực)</div>";
        } else dungLuong = dataItem.D;
      } else {
        dungLuong = currentItem.DUNGLUONG;
      }

      let kinhDo = 0;
      if (!this.isNullOrEmpty(dataItem.M)) {
        if (!this.validateDouble(dataItem.M)) {
          //msg += "<div>Vui lòng nhập đúng định dạng kinh độ (số thực)</div>";
        } else kinhDo = dataItem.M;
      } else {
        kinhDo = currentItem.KINHDO;
      }
      let viDo = 0;
      if (!this.isNullOrEmpty(dataItem.N)) {
        if (!this.validateDouble(dataItem.N)) {
          //msg += "<div>Vui lòng nhập đúng định dạng vĩ độ (số thực)</div>";
        } else viDo = dataItem.N;
      } else {
        viDo = currentItem.VIDO;
      }
      let ngayLapDat = null;
      if (!this.isNullOrEmpty(dataItem.T)) {
        if (!this.validateDate(dataItem.T)) {
          //msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
        } else ngayLapDat = DateTimeLib.toDateObject(dataItem.T);
      } else {
        ngayLapDat = DateTimeLib.toDateTimeObjectWithFormat(
          currentItem.NGAY_LD,
          "YYYY-MM-DD HH:mm:ss.S"
        );
      }
      let ngaySuDung = null;
      if (!this.isNullOrEmpty(dataItem.S)) {
        if (!this.validateDate(dataItem.S)) {
          //msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
        } else ngaySuDung = DateTimeLib.toDateObject(dataItem.S);
      } else {
        ngaySuDung = DateTimeLib.toDateTimeObjectWithFormat(
          currentItem.NGAY_SD,
          "YYYY-MM-DD HH:mm:ss.S"
        );
      }
      let donViID = null;
      let donVi = "";
      if (!this.isNullOrEmpty(dataItem.O)) {
        let ck = this.dsToQuanLy.find(
          (x) => x.TEN_DV == dataItem.O || x.MA_DV == dataItem.O
        );
        if (ck == null) {
          msg.donVi = "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        } else {
          donViID = ck.DONVI_ID;
          donVi = ck.TEN_DV;
        }
      } else {
        donViID = currentItem.DONVI_ID;
      }
      let duAnID = null;
      let taiSanID = null;
      let taiSanText = null;

      if (!this.isNullOrEmpty(dataItem.Q)) {
        if(dataItem.Q.toString().toUpperCase() != this.TUKHOA.XOA){
          let v = await this.getTaiSanDuAnTheoMa(
            dataItem.Q,
            this.isNullOrEmpty(dataItem.P) ? null : dataItem.P
          );
          if (v != null) {
            duAnID = v.DUAN_ID;
            taiSanID = v.TAISAN_ID;
            taiSanText = dataItem.Q;
          } else {
            //msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
          }
        }
      } else {
        taiSanID = currentItem.TAISAN_ID;
        if (!this.isNullOrEmpty(dataItem.P)) {
          let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem.P);
          if (ck == null) {
            //msg += "<div>Không tìm thấy dự án tương ứng</div>";
          } else duAnID = ck.DUAN_ID;
        } else {
          duAnID = currentItem.DUAN_ID;
        }
      }
      let capSP = null;
      if (!this.isNullOrEmpty(dataItem.E)) {
        let ck = this.dsCapSP.find((x) => x.CAPSP_ID == dataItem.E);
        if (ck == null) {
          //msg.capSP = "Không tìm thấy cấp Splitter";
        } else capSP = ck.CAPSP_ID;
      } else {
        capSP = currentItem.CAPSP_ID;
      }
      let ttsdID = null;
      if (!this.isNullOrEmpty(dataItem.G)) {
        let ck = this.dsTTSD.find((x) => x.TTSD_ID == dataItem.G);
        if (ck == null) {
          //msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
        } else ttsdID = ck.TTSD_ID;
      } else {
        ttsdID = currentItem.TTSD_ID;
      }
      let kieuLapID = null;
      if (!this.isNullOrEmpty(dataItem.H)) {
        let ck = this.dsKieuLap.find((x) => x.KIEULAP_ID == dataItem.H);
        if (ck == null) {
          //msg += "<div>Không tìm thấy kiểu lắp</div>";
        } else kieuLapID = ck.KIEULAP_ID;
      } else {
        kieuLapID = currentItem.KIEULAP_ID;
      }
      let noiSXID = null;
      if (!this.isNullOrEmpty(dataItem.I)) {
        let ck = this.dsNoiSX.find((x) => x.NOISX_ID == dataItem.I);
        if (ck == null) {
          //msg += "<div>Không tìm thấy nơi sản xuất</div>";
        } else noiSXID = ck.NOISX_ID;
      } else {
        noiSXID = currentItem.NOISX_ID;
      }
      let viTriID = null;
      if (!this.isNullOrEmpty(dataItem.J)) {
        viTriID = dataItem.J;
        let ck = this.dsViTriLapDat.find((x) => x.VITRI_ID == dataItem.J);
        if (ck == null) {
          //msg += "<div>Không tìm thấy vị trí</div>";
        } else viTriID = ck.VITRI_ID;
      } else {
        viTriID = currentItem.VITRI_ID;
      }
      let chuQuanID = null;
      if (!this.isNullOrEmpty(dataItem.K)) {
        let ck = this.dsChuQuan.find((x) => x.CHUQUAN_ID == dataItem.K);
        if (ck == null) {
          //msg += "<div>Không tìm thấy chủ quản</div>";
        } else chuQuanID = ck.CHUQUAN_ID;
      } else {
        chuQuanID = currentItem.CHUQUAN_ID;
      }
      let toaNhaID = null;
      if (!this.isNullOrEmpty(dataItem.R)) {
        let ck = this.dsToaNha.find((x) => x.MA_TOANHA == dataItem.R);
        if (ck == null) {
          //msg += "<div>Không tìm thấy tòa nhà</div>";
        } else toaNhaID = ck.TOANHA_ID;
      } else {
        toaNhaID = currentItem.TOANHA_ID;
      }
      let phanLoaiKCID = currentItem.PHANLOAIKC_ID;
      let phanLoaiKC = "";
      if (!this.isNullOrEmpty(phanLoaiKCID)) {
        let ck = this.dsPhanLoaiKC.find((x) => x.PHANLOAIKC_ID == phanLoaiKCID);
        if (ck != null) {
          phanLoaiKC = ck.PHANLOAI_KC;
        }
      }
      let Quan = dataItem.AB;
      let Phuong = dataItem.AC;
      let Duong = dataItem.AD;
      let Khu = dataItem.AE;
      let Ap = dataItem.AF;
      let phoId = null;
      let khuId = null;
      let apId = null;
      let phuongId = null;
      let quanId = null;
      let diachi = null;
      let vmessage = null;
      let kyhieuKetCuoi = dataItem.B;
      if (
        Quan !== null &&
        Phuong !== null &&
        (Ap !== null || Khu !== null || Duong !== null)
      ) {
        let data = {
          danhSach: [
            {
              KYHIEU: kyhieuKetCuoi,
              DIACHI: {
                PHO: Duong ?? null,
                PHUONG: Phuong ?? null,
                KHU: Khu ?? null,
                AP: Ap ?? null,
                QUAN: Quan ?? null,
              },
            },
          ],
        };
        let rsAddress = await this.$root.context.post(
          "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu",
          data
        );
        this.dsAddressCheck.push(rsAddress.data[0]);

        //Gán id cho các trường địa chỉ
        const addressCheck = this.dsAddressCheck.find((x) => x.KYHIEU == kyhieuKetCuoi);

        let vmessageck = addressCheck.Status.Message;

        if (!addressCheck.Status.Result) {
          vmessage = vmessageck;
        }

        if (addressCheck.Status.Result) {
          addressCheck.DIACHI.PHO_ID !== null
            ? (phoId = addressCheck.DIACHI.PHO_ID)
            : (phoId = null);
          addressCheck.DIACHI.KHU_ID !== null
            ? (khuId = addressCheck.DIACHI.KHU_ID)
            : (khuId = null);
          addressCheck.DIACHI.AP_ID !== null
            ? (apId = addressCheck.DIACHI.AP_ID)
            : (apId = null);
          addressCheck.DIACHI.PHUONG_ID !== null
            ? (phuongId = addressCheck.DIACHI.PHUONG_ID)
            : (phuongId = null);
          addressCheck.DIACHI.QUAN_ID !== null
            ? (quanId = addressCheck.DIACHI.QUAN_ID)
            : (quanId = null);
          //nếu trường nào có id thì cộng tên trường đó vào biến địa chỉ
          if (khuId !== null) {
            diachi += ", " + addressCheck.DIACHI.KHU;
          }
          if (apId !== null) {
            diachi += ", " + addressCheck.DIACHI.AP;
          }
          if (phoId !== null) {
            diachi += ", " + addressCheck.DIACHI.PHO;
          }
          if (phuongId !== null) {
            diachi += ", " + addressCheck.DIACHI.PHUONG;
          }
          if (quanId !== null) {
            diachi += ", " + addressCheck.DIACHI.QUAN;
          }
          if (!this.isNullOrEmpty(this.tenTinh)) {
            diachi += ", " + this.tenTinh;
          }
        }
      }

      let result = {
        AP_ID: currentItem.AP_ID,
        BECAP_ID: currentItem.BECAP_ID,
        CAP_ID: currentItem.CAP_ID,
        CAP_SP: capSP,
        CAPGOC_ID: currentItem.CAPGOC_ID,
        CHUQUAN_ID: chuQuanID,
        COT_ID: currentItem.COT_ID,
        DACDIEM_ID: currentItem.DACDIEM_ID,
        DIACHI: this.isNullOrEmpty(dataItem.F) ? currentItem.DIACHI : dataItem.F,
        DIACHI_CK: vmessage,
        DIACHI_ID: currentItem.DIACHI_ID,
        DONVI_ID: donViID,
        DONVI_TEXT: donVi,
        DUAN_ID: duAnID,
        DUNGLUONG: dungLuong,
        GHICHU: this.isNullOrEmpty(dataItem.U) ? currentItem.GHICHU : dataItem.U,
        IMS_ID: currentItem.IMS_ID,
        KETCUOI_CHA_ID: dataItem.AH_ID == -1 ? null : (this.isNullOrEmpty(dataItem.AH_ID) ? currentItem.KETCUOI_CHA_ID : dataItem.AH_ID),
        KYHIEU_KETCUOI_CHA: dataItem.AH_ID == -1 ? null : (this.isNullOrEmpty(dataItem.AH_ID) ? currentItem.KETCUOI_CHA_KYHIEU : dataItem.AH),
        KETCUOI_CHA_TEXT: dataItem.AH_ID == -1 ? null : (this.isNullOrEmpty(dataItem.AH_ID) ? currentItem.KETCUOI_CHA_TEN : dataItem.AH_TEN),
        KETCUOI_ID: currentItem.KETCUOI_ID,
        KHU_ID: currentItem.KHU_ID,
        KIEULAP_ID: kieuLapID,
        KINHDO: kinhDo,
        KYHIEU: this.isNullOrEmpty(dataItem.B) ? currentItem.KYHIEU : dataItem.B,
        KYHIEU_MOI: this.isNullOrEmpty(dataItem.Y) ? currentItem.KYHIEU : dataItem.Y,
        LOAIKC_ID: this.isNullOrEmpty(dataItem.AA)
          ? currentItem.LOAIKC_ID
          : Number(dataItem.AA),
        LOAIVO_ID: this.isNullOrEmpty(dataItem.L) ? currentItem.LOAIVO_ID : dataItem.L,
        MA_CSHT: currentItem.MA_CSHT,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        NGUONDICH: currentItem.NGUONDICH,
        NOISX_ID: noiSXID,
        PHANLOAIKC: phanLoaiKC,
        PHANLOAIKC_ID: phanLoaiKCID,
        PHO_ID:  currentItem.PHO_ID,
        PHUONG_ID: currentItem.PHUONG_ID,
        QUAN_ID: currentItem.QUAN_ID,
        SONHA: this.isNullOrEmpty(dataItem.AG) ? currentItem.SONHA : dataItem.AG,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        TEN_KC: this.isNullOrEmpty(dataItem.C) ? currentItem.TEN_KC : dataItem.C,
        TEN_KC_MOI: this.isNullOrEmpty(dataItem.Z) ? currentItem.TEN_KC : dataItem.Z,
        QUAN_HUYEN: Quan,
        PHUONG_XA: Phuong,
        DUONG_PHO: Duong,
        KHU: Khu,
        NGO_AP: Ap,
        TINH_ID: currentItem.TINH_ID,
        TOANHA_ID: toaNhaID,
        TONGSO_DD: currentItem.TONGSO_DD,
        TRAMTBI_ID: currentItem.TRAMTBI_ID,
        TTSD_ID: ttsdID,
        VIDO: viDo,
        VITRI_ID: viTriID,
        MA_CSHT: this.isNullOrEmpty(dataItem.V) ? currentItem.MA_CSHT : dataItem.V,
        MA_GOITHAU_IMS: this.isNullOrEmpty(dataItem.W)
          ? currentItem.MA_GOITHAU_IMS
          : dataItem.W,
        MA_HOPDONG_IMS: this.isNullOrEmpty(dataItem.X)
          ? currentItem.MA_HOPDONG_IMS
          : dataItem.X,

        QUAN: this.isNullOrEmpty(dataItem.AB) ? currentItem.QUAN : dataItem.AB,

        PHUONG: this.isNullOrEmpty(dataItem.AC) ? currentItem.PHUONG : dataItem.AC,
        DUONG_PHO: this.isNullOrEmpty(dataItem.AD) ? currentItem.DUONG_PHO : dataItem.AD,
        KHU: this.isNullOrEmpty(dataItem.AE) ? currentItem.KHU : dataItem.AE,
        AP: this.isNullOrEmpty(dataItem.AF) ? currentItem.AP : dataItem.AF,
        MESSAGE: msg,

        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "KETCUOI",
        IS_EDIT: true,
        AP_ID_UPDATE: apId ?? null,
        KHU_ID_UPDATE: khuId ?? null,
        PHUONG_ID_UPDATE: phuongId ?? null,
        QUAN_ID_UPDATE: quanId ?? null,
        DIACHI_UPDATE: diachi ? diachi.replace("null,", "") : null,
      };
      this.validateKetCuoi(result);
      return result;
    },
    validateKetCuois: function () {
      let rs = true;
      if (this.danhSachKetCuoi != null) {
        for (let i = 0; i < this.danhSachKetCuoi.length; i++) {
          let r = this.validateKetCuoi(this.danhSachKetCuoi[i]);
          if (rs) rs = r;
        }
      }
      return rs;
    },
    validateKetCuoi: function (item) {
      let it = this.dsLoaiKC.find((x) => x.LOAIKC_ID == item.LOAIKC_ID);
      if (!(it == undefined || it == null)) {
        item.LOAIKC_TEXT = it.LOAI_KC;
      }
      it = this.dsCapSP.find((x) => x.CAP_SP == item.CAPSP_ID);
      if (!(it == undefined || it == null)) {
        item.CAP_SP_TEXT = it.TEN_CAP_SP;
      }
      it = this.dsTTSD.find((x) => x.TTSD_ID == item.TTSD_ID);
      if (!(it == undefined || it == null)) {
        item.TTSD_TEXT = it.TRANGTHAI_SD;
      }
      it = this.dsKieuLap.find((x) => x.KIEULAP_ID == item.KIEULAP_ID);
      if (!(it == undefined || it == null)) {
        item.KIEULAP_TEXT = it.KIEU_LAP;
      }
      it = this.dsNoiSX.find((x) => x.NOISX_ID == item.NOISX_ID);
      if (!(it == undefined || it == null)) {
        item.NOISX_TEXT = it.NOI_SX;
      }
      it = this.dsViTriLapDat.find((x) => x.VITRI_ID == item.VITRI_ID);
      if (!(it == undefined || it == null)) {
        item.VITRI_TEXT = it.VITRI;
      }
      it = this.dsLoaiVo.find((x) => x.LOAIVO_ID == item.LOAIVO_ID);
      if (!(it == undefined || it == null)) {
        item.LOAIVO_TEXT = it.LOAI_VO;
      }
      it = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      if (!(it == undefined || it == null)) {
        item.DUAN_TEXT = it.MA_CT;
      }
      it = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(it == undefined || it == null)) {
        item.CHUQUAN_TEXT = it.TENCHUQUAN;
      }
      it = this.dsToQuanLy.find((x) => x.DONVI_ID == item.DONVI_ID);
      if (!(it == undefined || it == null)) {
        item.DONVI_TEXT = it.TEN_DV;
      } else item.DONVI_TEXT = "";
      it = this.dsToaNha.find((x) => x.TOANHA_ID == item.TOANHA_ID);
      if (!(it == undefined || it == null)) {
        item.TOANHA_TEXT = it.TEN_TOANHA;
      }

      let rs = true;
      if (this.isNullOrEmpty(item.DONVI_TEXT)) {
        item.MESSAGE.donVi =
          "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        rs = false;
      } else item.MESSAGE.donVi = "";
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else item.MESSAGE.kyHieu = "";
      if (this.isNullOrEmpty(item.TEN_KC)) {
        item.MESSAGE.tenKC = "Tên kết cuối không được để trống";
        rs = false;
      } else item.MESSAGE.tenKC = "";
      if (this.isNullOrEmpty(item.DIACHI)) {
        item.MESSAGE.diaChi = "Địa chỉ không được để trống";
        rs = false;
      } else item.MESSAGE.diaChi = "";
      if (this.isNullOrEmpty(item.TONGSO_DD)) {
        item.MESSAGE.dungLuong = "Dung lượng không được để trống";
        rs = false;
      } else {
        if (!this.validateDouble(item.TONGSO_DD)) {
          item.MESSAGE.dungLuong = "Dung lượng phải là một số";
          rs = false;
        } else item.MESSAGE.dungLuong = "";
      }
      if (!this.isNullOrEmpty(item.DIACHI_CK)) {
        item.MESSAGE.diachi_nhap = item.DIACHI_CK;
        rs = false;
      } else item.MESSAGE.diachi_nhap = "";

      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   item.TRANGTHAI = "Sẵn sàng";
      //   // if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   // else item.TRANGTHAI = "Đã lưu";
      // }
      // item.MESSAGE.isValid = rs;
      this.updateStatus(item);
      return rs;
    },
    convertExcelDsCap: function (rows) {
      let result = [];
      if (!(rows == null || rows.length < 2)) {
        const cols = this.getColumns(rows[0]);
        for (let i = 1; i < rows.length; i++) {
          const row = rows[i];
          let item = {
            A: this.getColumnValue(cols, "Cáp ID", row),
            B: this.getColumnValue(cols, "Ký hiệu", row),
            C: this.getColumnValue(cols, "Tên cáp", row),
            D: this.getColumnValue(cols, "Số đôi dây", row),
            E: this.getColumnValue(cols, "Kích cỡ", row),
            F: this.getColumnValue(cols, "Điện cao thế", row),
            G: this.getColumnValue(cols, "Chiều dài", row),
            H: this.getColumnValue(cols, "Nơi sản xuất", row),
            I: this.getColumnValue(cols, "Trạng thái sử dụng", row),
            J: this.getColumnValue(cols, "Loại cáp", row),
            K: this.getColumnValue(cols, "Kiểu đi cáp", row),
            L: this.getColumnValue(cols, "Dự án (Nhập chính xác mã dự án)", row),
            M: this.getColumnValue(cols, "Tài sản (Nhập chính xác mã tài sản)", row),
            N: this.getColumnValue(cols, "Ngày sử dụng(dd/MM/yyyy)", row),
            O: this.getColumnValue(cols, "Ngày lắp đặt(dd/MM/yyyy)", row),
            P: this.getColumnValue(cols, "Ghi chú", row),
            Q: this.getColumnValue(cols, "chuquan_id", row),
            R: this.getColumnValue(
              cols,
              "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)",
              row
            ),
            S: this.getColumnValue(cols, "MA_CSHT", row),
            T: this.getColumnValue(cols, "MA_GOITHAU_IMS", row),
            U: this.getColumnValue(cols, "MA_HOPDONG_IMS", row),
            V: this.getColumnValue(cols, "Ký hiệu mới", row),
            W: this.getColumnValue(cols, "Tên cáp mới", row),
          };
          result.push(item);
        }
      }
      return result;
    },
    convertToDsCapItems: async function () {
      let r = [];
      if (this.dsExcelCap != null) {
        let dsID_EXCEL = [];
        let dsSO_HIEU_EXCEL = [];
        for (let j = 0; j < this.dsExcelCap.length; j++) {
          const cap = this.dsExcelCap[j];
          if (!(this.isNullOrEmpty(cap.A) || dsID_EXCEL.includes(cap.A))) {
            dsID_EXCEL.push(cap.A);
          }
          if (!(this.isNullOrEmpty(cap.B) || dsSO_HIEU_EXCEL.includes(cap.B))) {
            dsSO_HIEU_EXCEL.push(cap.B);
          }
        }
        if (
          dsID_EXCEL.length == this.dsExcelCap.length ||
          dsSO_HIEU_EXCEL.length == this.dsExcelCap.length
        ) {
          let useID = dsID_EXCEL.length == this.dsExcelCap.length;
          let dsItemUpdate = await this.getDsCapUpdate(
            useID,
            useID ? dsID_EXCEL : dsSO_HIEU_EXCEL
          );
          if (!(dsItemUpdate == null || dsItemUpdate.length == 0)) {
            for (let i = 0; i < this.dsExcelCap.length; i++) {
              let currentItem = null;
              if (useID)
                currentItem = dsItemUpdate.find((x) => x.CAP_ID == this.dsExcelCap[i].A);
              else
                currentItem = dsItemUpdate.find((x) => x.KYHIEU == this.dsExcelCap[i].B);
              if (!(currentItem == null || currentItem == undefined)) {
                let v = await this.convertToCapItem(this.dsExcelCap[i], currentItem);
                v.STT = i + 1;
                r.push(v);
              }
            }
          }
        } else {
          this.$root.toastError(
            "Trường cáp id hoặc ký hiệu cáp đang bị thiếu dữ liệu hoặc bị trùng lặp. \nVui lòng bổ sung và thử lại."
          );
        }
      }
      return r;
    },
    convertToCapItem: async function (dataItem, currentItem) {
      // A: 'Cáp ID'
      // B: 'Ký hiệu'
      // C: 'Tên cáp'
      // D: 'SODOIDAY'
      // E: 'Kích cỡ'
      // F: 'Điện cao thế'
      // G: 'Chiều dài'
      // H: 'Nơi sản xuất'
      // I: 'Trạng thái sử dụng'
      // J: 'Loại cáp'
      // K: 'Kiểu đi cáp'
      // L: 'Dự án (Nhập chính xác mã dự án)'
      // M: 'Tài sản (Nhập chính xác mã tài sản)'
      // N: 'Ngày sử dụng(dd/MM/yyyy)'
      // O: 'Ngày lắp đặt(dd/MM/yyyy)'
      // P: 'Ghi chú'
      // Q: 'chuquan_id'
      let msg = {};
      let ngayLapDat = null;
      if (!this.isNullOrEmpty(dataItem.N)) {
        if (!this.validateDate(dataItem.N)) {
          //msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
        } else ngayLapDat = DateTimeLib.toDateObject(dataItem.N);
      } else {
        ngayLapDat = DateTimeLib.toDateTimeObjectWithFormat(
          currentItem.NGAY_LD,
          "YYYY-MM-DD HH:mm:ss.S"
        );
      }
      let ngaySuDung = null;
      if (!this.isNullOrEmpty(dataItem.O)) {
        if (!this.validateDate(dataItem.O)) {
          //msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
        } else ngaySuDung = DateTimeLib.toDateObject(dataItem.O);
      } else {
        ngaySuDung = DateTimeLib.toDateTimeObjectWithFormat(
          currentItem.NGAY_SD,
          "YYYY-MM-DD HH:mm:ss.S"
        );
      }
      let soDoiDay = 0;
      if (!this.isNullOrEmpty(dataItem.D)) {
        if (!this.validateDouble(dataItem.D)) {
          //msg += "<div>Vui lòng nhập đúng định dạng số đôi dây</div>";
        } else soDoiDay = dataItem.D;
      } else {
        soDoiDay = currentItem.SODOIDAY;
      }
      let chieuDai = 0;
      if (!this.isNullOrEmpty(dataItem.G)) {
        if (!this.validateDouble(dataItem.G)) {
          //msg += "<div>Vui lòng nhập đúng định dạng chiều dài</div>";
        } else chieuDai = dataItem.G;
      } else {
        chieuDai = currentItem.CHIEUDAI;
      }
      let ttsdID = null;
      if (!this.isNullOrEmpty(dataItem.I)) {
        let ck = this.dsTTSD.find((x) => x.TTSD_ID == dataItem.I);
        if (ck == null) {
          //msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
        } else ttsdID = ck.TTSD_ID;
      } else {
        ttsdID = currentItem.TTSD_ID;
      }
      let chuQuanID = null;
      if (!this.isNullOrEmpty(dataItem.Q)) {
        let ck = this.dsChuQuan.find((x) => x.CHUQUAN_ID == dataItem.Q);
        if (ck == null) {
          //msg += "<div>Không tìm thấy chủ quản</div>";
        } else chuQuanID = ck.CHUQUAN_ID;
      } else {
        chuQuanID = currentItem.CHUQUAN_ID;
      }
      let noiSXID = null;
      if (!this.isNullOrEmpty(dataItem.H)) {
        let ck = this.dsNoiSX.find((x) => x.NOISX_ID == dataItem.H);
        if (ck == null) {
          //msg += "<div>Không tìm thấy nơi sản xuất</div>";
        } else noiSXID = ck.NOISX_ID;
      } else {
        noiSXID = currentItem.NOISX_ID;
      }
      let kichCoID = null;
      if (!this.isNullOrEmpty(dataItem.E)) {
        let ck = this.dsKichCoCap.find((x) => x.KICHCO_ID == dataItem.E);
        if (ck == null) {
          //msg += "<div>Không tìm thấy kích cỡ cáp</div>";
        } else kichCoID = ck.KICHCO_ID;
      } else {
        kichCoID = currentItem.KICHCO_ID;
      }
      let dienCTID = null;
      if (!this.isNullOrEmpty(dataItem.F)) {
        let ck = this.dsDienCT.find((x) => x.DIENCT_ID == dataItem.F);
        if (ck == null) {
          //msg += "<div>Không tìm thấy điện cao thế</div>";
        } else dienCTID = ck.DIENCT_ID;
      } else {
        dienCTID = currentItem.DIENCT_ID;
      }
      let loaiCapID = null;
      if (!this.isNullOrEmpty(dataItem.J)) {
        let ck = this.dsLoaiCap.find((x) => x.LOAICAP_ID == dataItem.J);
        if (ck == null) {
          //msg += "<div>Không tìm thấy loại cáp</div>";
        } else loaiCapID = ck.LOAICAP_ID;
      } else {
        loaiCapID = currentItem.LOAICAP_ID;
      }
      let kieuDiCapID = null;
      if (!this.isNullOrEmpty(dataItem.K)) {
        let ck = this.dsKieuDC.find((x) => x.KIEUDC_ID == dataItem.K);
        if (ck == null) {
          //msg += "<div>Không tìm thấy kiểu đi cáp</div>";
        } else kieuDiCapID = ck.KIEUDC_ID;
      } else {
        kieuDiCapID = currentItem.KIEUDC_ID;
      }
      let duAnID = null;
      let taiSanID = null;
      let taiSanText = null;
      if (!this.isNullOrEmpty(dataItem.M)) {
        let v = await this.getTaiSanDuAnTheoMa(
          dataItem.M,
          this.isNullOrEmpty(dataItem.L) ? null : dataItem.L
        );
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
          taiSanText = dataItem.M;
        } else {
          //msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
        }
      } else {
        taiSanID = currentItem.TAISAN_ID;
        if (!this.isNullOrEmpty(dataItem.L)) {
          let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem.L);
          if (ck == null) {
            duAnID = null;
            //msg += "<div>Không tìm thấy dự án tương ứng</div>";
          } else duAnID = ck.DUAN_ID;
        } else {
          duAnID = currentItem.DUAN_ID;
        }
      }
      let TOQL_ID = null;
      let TOQL_TEXT = "";
      //currentItem.TOQL_ID
      if (!this.isNullOrEmpty(dataItem.R)) {
        let ck = this.dsToQuanLy.find(
          (x) => x.TEN_DV == dataItem.R || x.MA_DV == dataItem.R
        );
        if (ck == null) {
          msg.toQL = "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        } else {
          TOQL_ID = ck.DONVI_ID;
          TOQL_TEXT = ck.TEN_DV;
        }
      } else {
        TOQL_ID = currentItem.TOQL_ID;
      }
      let lopMCID = currentItem.LOPMC_ID;
      let lopMC = "";
      if (!this.isNullOrEmpty(lopMCID)) {
        let ck = this.dsLopMC.find((x) => x.LOPMC_ID == lopMCID);
        if (ck != null) {
          lopMC = ck.LOP_MC;
        }
      }
      // let kyhieuField = this.CapFields.find((x) => x.name == "KYHIEU");
      //     let kyhieu =
      //       kyhieuField && kyhieuField.positionColumn
      //         ? dataItem[kyhieuField.positionColumn]
      //         : null;

      //     let tencapField = this.CapFields.find((x) => x.name == "TEN_CAP");
      //     let tencap =
      //       tencapField && tencapField.positionColumn
      //         ? dataItem[tencapField.positionColumn]
      //         : null;
      let result = {
        CAP_ID: currentItem.CAP_ID,
        MA_CAP: currentItem.MA_CAP,
        KYHIEU: this.isNullOrEmpty(dataItem.B) ? currentItem.KYHIEU : dataItem.B,
        TEN_CAP: this.isNullOrEmpty(dataItem.C) ? currentItem.TEN_CAP : dataItem.C,
        KYHIEU_MOI: this.isNullOrEmpty(dataItem.V) ? currentItem.KYHIEU : dataItem.V,
        TEN_CAP_MOI: this.isNullOrEmpty(dataItem.W) ? currentItem.TEN_CAP : dataItem.W,
        KICHCO_ID: kichCoID,
        KC_COT: currentItem.KC_COT,
        TROKHANG: currentItem.TROKHANG,
        SUYHAO: currentItem.SUYHAO,
        SODOIDAY: soDoiDay,
        SODOI_SD: currentItem.SODOI_SD,
        SO_DH: currentItem.SO_DH,
        SO_DC: currentItem.SO_DC,
        VITRI_COT: currentItem.VITRI_COT,
        VITRI_DT: currentItem.VITRI_DT,
        DIENCT_ID: dienCTID,
        CHIEUDAI: chieuDai,
        TUYENCAP_ID: currentItem.TUYENCAP_ID,
        NOISX_ID: noiSXID,
        LOAICAP_ID: loaiCapID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        TTSD_ID: ttsdID,
        CAPGOC_ID: currentItem.CAPGOC_ID,
        LOPMC: lopMC,
        LOPMC_ID: lopMCID,
        NHOMLC_ID: currentItem.NHOMLC_ID,
        KIEUDC_ID: kieuDiCapID,
        DIADANH_ID: currentItem.DIADANH_ID,
        KETCUOI_N_ID: currentItem.KETCUOI_N_ID,
        KETCUOI_D_ID: currentItem.KETCUOI_D_ID,
        TOQL_ID: TOQL_ID,
        TOQL_TEXT: TOQL_TEXT,
        CAP_CHA_ID: currentItem.CAP_CHA_ID,
        DOANCAP: currentItem.DOANCAP,
        DONVI_TD_ID: currentItem.DONVI_TD_ID,
        KIEUCAP_ID: currentItem.KIEUCAP_ID,
        DIENCAOTHE: dienCTID,
        PHANCAP_ID: currentItem.PHANCAP_ID,
        GHICHU: this.isNullOrEmpty(dataItem.P) ? currentItem.GHICHU : dataItem.P,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        DUAN_ID: duAnID,
        KHUVUC_ID: currentItem.KHUVUC_ID,
        SOI_BD: currentItem.SOI_BD,
        CHUQUAN_ID: chuQuanID,
        IMS_ID: currentItem.IMS_ID,
        MA_CSHT: this.isNullOrEmpty(dataItem.S) ? currentItem.MA_CSHT : dataItem.S,
        MA_GOITHAU_IMS: this.isNullOrEmpty(dataItem.T)
          ? currentItem.MA_GOITHAU_IMS
          : dataItem.T,
        MA_HOPDONG_IMS: this.isNullOrEmpty(dataItem.U)
          ? currentItem.MA_HOPDONG_IMS
          : dataItem.U,
        CHIEUDAI_TK: currentItem.CHIEUDAI_TK,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "CAP",
        IS_EDIT: true,
      };
      this.validateCap(result);
      return result;
    },
    validateCaps: function () {
      let rs = true;
      if (this.danhSachCap != null) {
        for (let i = 0; i < this.danhSachCap.length; i++) {
          let r = this.validateCap(this.danhSachCap[i]);
          if (rs) rs = r;
        }
      }
      return rs;
    },
    validateCap: function (item) {
      let it = this.dsKichCoCap.find((x) => x.KICHCO_ID == item.KICHCO_ID);
      if (!(it == undefined || it == null)) {
        item.KICHCO_TEXT = it.KICHCO;
      }
      it = this.dsDienCT.find((x) => x.DIENCT_ID == item.DIENCT_ID);
      if (!(it == undefined || it == null)) {
        item.DIENCT_TEXT = it.DIEN_CT;
      }
      it = this.dsNoiSX.find((x) => x.NOISX_ID == item.NOISX_ID);
      if (!(it == undefined || it == null)) {
        item.NOISX_TEXT = it.NOI_SX;
      }
      it = this.dsTTSD.find((x) => x.TTSD_ID == item.TTSD_ID);
      if (!(it == undefined || it == null)) {
        item.TTSD_TEXT = it.TRANGTHAI_SD;
      }
      it = this.dsLoaiCap.find((x) => x.LOAICAP_ID == item.LOAICAP_ID);
      if (!(it == undefined || it == null)) {
        item.LOAICAP_TEXT = it.LOAI_CAP;
      }
      it = this.dsNhomLC.find((x) => x.NHOMLC_ID == item.NHOMLC_ID);
      if (!(it == undefined || it == null)) {
        item.NHOMLC_TEXT = it.NHOM_LC;
      }
      it = this.dsKieuDC.find((x) => x.KIEUDC_ID == item.KIEUDC_ID);
      if (!(it == undefined || it == null)) {
        item.KIEUDC_TEXT = it.KIEU_DC;
      }
      it = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      if (!(it == undefined || it == null)) {
        item.DUAN_TEXT = it.MA_CT;
      }
      it = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(it == undefined || it == null)) {
        item.CHUQUAN_TEXT = it.TENCHUQUAN;
      }
      it = this.dsToQuanLy.find((x) => x.DONVI_ID == item.TOQL_ID);
      if (!(it == undefined || it == null)) {
        item.TOQL_TEXT = it.TEN_DV;
      } else item.TOQL_TEXT = "";

      let rs = true;
      if (this.isNullOrEmpty(item.TOQL_TEXT)) {
        item.MESSAGE.toQL =
          "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
        rs = false;
      } else item.MESSAGE.toQL = "";
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else item.MESSAGE.kyHieu = "";
      if (this.isNullOrEmpty(item.TEN_CAP)) {
        item.MESSAGE.tenCap = "Tên cáp không được để trống";
        rs = false;
      } else item.MESSAGE.tenCap = "";
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   item.TRANGTHAI = "Sẵn sàng";
      //   // if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   // else item.TRANGTHAI = "Đã lưu";
      // }
      // item.MESSAGE.isValid = rs;
      this.updateStatus(item);
      return rs;
    },
    getDonViTheoTen: async function (tenDonVi) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getDonViTheoTen",
          {
            tenDonVi: tenDonVi,
          }
        );
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến đơn vị: " + tenDonVi);
        return null;
      }
    },
    getDuAnTheoSoHieu: async function (soHieuCT) {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getDuAnTheoSoHieu",
          {
            soHieuCT: soHieuCT,
          }
        );
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến dự án theo số hiệu: " + soHieuCT);
        return null;
      }
    },
    getTaiSanDuAnTheoMa: async function (maTS, soHieuCT) {
      //maTS: mã tài sản
      //soHieuCT: số hiệu chứng từ dự án
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/cot-cong-be/getTaiSanDuAnTheoMa",
          {
            maTS: maTS.toString(),
            soHieuCT: soHieuCT,
          }
        );
        return rs.data;
      } catch (e) {
        this.$root.toastError(
          "Lỗi truy cập đến tài sản theo mã: " + maTS + " và số hiệu: " + soHieuCT
        );
        return null;
      }
    },
    getDsDuAn: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDuAn");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách dự án");
        return null;
      }
    },
    getDsTaiSan: async function (duAnID) {
      try {
        if (duAnID != null) {
          let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsTaiSan", {
            id: duAnID,
          });
          return rs.data;
        } else return [];
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách tài sản theo dự án: " + duAnID);
        return null;
      }
    },
    getDsPhanLoaiKC: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsPhanLoaiKC");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách phân loại kết cuối");
        return null;
      }
    },
    getDsLoaiKC: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiKC");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại kết cuối");
        return null;
      }
    },
    getDsCapSP: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsCapSP");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách cáp splitter");
        return null;
      }
    },
    getDsKieuLap: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuLap");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách kiểu lắp");
        return null;
      }
    },
    getDsNoiSX: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsNoiSX");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách nơi sản xuất");
        return null;
      }
    },
    getDsViTriLapDat: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsViTriLapDat");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách vị trí lắp đặt");
        return null;
      }
    },
    getDsLoaiVo: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiVo");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại vỏ");
        return null;
      }
    },
    getDsKichCoCap: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKichCoCap");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách kích cỡ cáp");
        return null;
      }
    },
    getDsDienCT: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDienCT");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách điện cao thế");
        return null;
      }
    },
    getDsLoaiCap: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiCap");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách loại cáp");
        return null;
      }
    },
    getDsLopMC: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLopMC");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách lớp mặt cắt");
        return null;
      }
    },
    getDsNhomLC: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsNhomLC");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách nhóm loại cáp");
        return null;
      }
    },
    getDsKieuDC: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuDC");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách kiểu đi cáp");
        return null;
      }
    },
    getDsKieuCap: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuCap");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách kiểu cáp");
        return null;
      }
    },
    getDsToaNha: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsToaNha");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách tòa nhà");
        return null;
      }
    },
    getDsToQuanLy: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsToQuanLy");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách tổ quản lý");
        return null;
      }
    },
    getCotSoHieu: async function (id) {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layCotSoHieu", {
          id: id,
        });
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến số hiệu cột: " + id);
        return null;
      }
    },
    getTramThietBi: async function (id) {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layTramThietBi", {
          id: id,
        });
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến trạm thiết bị: " + id);
        return null;
      }
    },
    getDsTrangThaiSuDung: async function () {
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsTrangThaiSuDung"
        );
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách trạng thái sử dụng");
        return null;
      }
    },
    getDsChuQuan: async function () {
      try {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsChuQuan");
        return rs.data;
      } catch (e) {
        this.$root.toastError("Lỗi truy cập đến danh sách chủ quản");
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
      } catch (e) {
        this.$root.toastError("Lỗi định dạng ngày (dd/MM/yyyy) cho giá trị: " + value);
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
      } catch (e) {
        this.$root.toastError("Lỗi định dạng số cho giá trị: " + value);
        return false;
      }
    },
    isNullOrEmpty: function (value) {
      return value == undefined || value == null || value.toString().trim() == "";
    },
    getDsCapUpdate: async function (useID, danhSach) {
      if (danhSach == null || danhSach.length == 0) {
        return [];
      } else {
        let url = "/web-cabman/cap/layDsCapTheoIDs";
        let param = {
          capIDs: danhSach,
        };
        if (!useID) {
          url = "/web-cabman/cap/layDsCapTheoKyHieus";
          param = {
            kyHieus: danhSach,
          };
        }
        let rs = await this.$root.context.post(url, param);
        return rs.data;
      }
    },
    kiemTraDiaChiKetCuoi: async function (useID, danhSach) {
      if (danhSach == null || danhSach.length == 0) {
        return [];
      } else {
        let url = "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoID";
        let param = {
          capIDs: danhSach,
        };
        if (!useID) {
          url = "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu";
          param = {
            kyHieus: danhSach,
          };
        }
        let rs = await this.$root.context.post(url, param);
        return rs.data;
      }
    },
    getDsKetCuoiUpdate: async function (useID, danhSach) {
      if (danhSach == null || danhSach.length == 0) {
        return [];
      } else {
        let url = "/web-cabman/ketcuoi/layDsKetCuoiTheoIDs";
        let param = {
          ketCuoiIDs: danhSach,
        };
        if (!useID) {
          url = "/web-cabman/ketcuoi/layDsKetCuoiTheoKyHieus";
          param = {
            kyHieus: danhSach,
          };
        }

        let rs = await this.$root.context.post(url, param);
        return rs.data;
      }
    },
    getDsKetCuoiIDsTheoKyHieus: async function (danhSach) {
      if (danhSach == null || danhSach.length == 0) {
        return [];
      } else {
        let url = "/web-cabman/ketcuoi/layDsKetCuoiIDsTheoKyHieus";
        let param = {
          kyHieus: danhSach,
        };
        let rs = await this.$root.context.post(url, param);
        return rs.data;
      }
    },
    capNhatDsCap: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachCap.length; i++) {
        if (this.danhSachCap[i].MESSAGE.isValid === true) {
          this.danhSachCap[i].TRANGTHAI = "Đang lưu...";
          let r = await this.capNhatCap(this.danhSachCap[i]);
          if (r != null) {
            if (r.result == true) {
              count++;
              this.danhSachCap[i].TRANGTHAI = "Đã lưu";
            } else {
              this.danhSachCap[i].TRANGTHAI = "Lưu thất bại!";
            }
          } else {
            this.danhSachCap[i].TRANGTHAI = "Lưu thất bại!";
          }
        }
      }
      return count;
    },
    capNhatCap: async function (dataItem) {
      let data = {
        cap_id: dataItem.CAP_ID,
        maCap: dataItem.MA_CAP,
        chieudai: dataItem.CHIEUDAI,
        chieudai_tk: dataItem.CHIEUDAI_TK,
        chuquan_id: dataItem.CHUQUAN_ID,
        dienct_id: dataItem.DIENCT_ID,
        donvi_td_id: dataItem.DONVI_TD_ID,
        duan_id: dataItem.DUAN_ID,
        ghichu: dataItem.GHICHU,
        ims_id: dataItem.IMS_ID,
        kichco_id: dataItem.KICHCO_ID,
        kieucap_id: dataItem.KIEUCAP_ID,
        kieudc_id: dataItem.KIEUDC_ID,
        kyhieu: dataItem.KYHIEU_MOI,
        // kyhieu: dataItem.KYHIEU,
        // kyhieu_moi: dataItem.KYHIEU_MOI,
        loaicap_id: dataItem.LOAICAP_ID,
        lopmc_id: dataItem.LOPMC_ID,
        ma_csht: dataItem.MA_CSHT,
        ngay_ld:
          dataItem.NGAY_LD != null ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD) : null,
        ngay_sd:
          dataItem.NGAY_SD != null ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD) : null,
        nhomlc_id: dataItem.NHOMLC_ID,
        noisx_id: dataItem.NOISX_ID,
        sodoiday: dataItem.SODOIDAY,
        soi_bd: dataItem.SOI_BD,
        suyhao: dataItem.SUYHAO,
        taisan_id: dataItem.TAISAN_ID,
        ten_cap: dataItem.TEN_CAP_MOI,
        // ten_cap: dataItem.TEN_CAP,
        // ten_cap_moi: dataItem.TEN_CAP_MOI,
        toql_id: dataItem.TOQL_ID,
        trokhang: dataItem.TROKHANG,
        ttsd_id: dataItem.TTSD_ID,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
      };
      try {
        let rs = await this.$root.context.post("/web-cabman/cap/capnhat_theofile", data);
        return rs.data;
      } catch (error) {
        let msg = error;
        if (!(error.response == null || error.response == undefined)) {
          msg = error.response.data.message_detail;
          if (msg == null || msg == undefined || msg == "")
            msg = JSON.stringify(error.response.data.data);
        }
        this.$root.toastError("Lỗi cập nhật cáp ID " + data.cap_id + ": " + msg);
      }
      return null;
    },
    capNhatDsKetCuoi: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachKetCuoi.length; i++) {
        if (this.danhSachKetCuoi[i].MESSAGE.isValid === true) {
          this.danhSachKetCuoi[i].TRANGTHAI = "Đang lưu...";
          let r = await this.capNhatKetCuoi(this.danhSachKetCuoi[i]);
          if (r != null) {
            count++;
            //update old address = new address
            this.danhSachKetCuoi[i].TRANGTHAI = "Đã lưu";
            this.danhSachKetCuoi[i].DIACHI = this.danhSachKetCuoi[i].DIACHI_UPDATE;
          } else {
            this.danhSachKetCuoi[i].TRANGTHAI = "Lưu thất bại!";
          }
        }
      }
      return count;
    },
    capNhatKetCuoi: async function (dataItem) {
      let data = {
        ap_id: dataItem.AP_ID_UPDATE ? dataItem.AP_ID_UPDATE : dataItem.AP_ID,
        ma_kc: dataItem.KETCUOI_MA,
        becap_id: dataItem.BECAP_ID,
        cap_id: dataItem.CAP_ID,
        cap_sp: dataItem.CAP_SP,
        capgoc_id: dataItem.CAPGOC_ID,
        chuquan_id: dataItem.CHUQUAN_ID,
        cot_id: dataItem.COT_ID,
        dacdiem_id: dataItem.DACDIEM_ID,
        diachi: dataItem.DIACHI_UPDATE ? dataItem.DIACHI_UPDATE : dataItem.DIACHI,
        diachi_id: dataItem.DIACHI_ID,
        donvi_id: dataItem.DONVI_ID,
        duan_id: dataItem.DUAN_ID,
        dungluong: dataItem.DUNGLUONG,
        ghichu: dataItem.GHICHU,
        ims_id: dataItem.IMS_ID,
        ketcuoi_cha_id: dataItem.KETCUOI_CHA_ID,
        ketcuoi_id: dataItem.KETCUOI_ID,
        khu_id: dataItem.KHU_ID,
        kieulap_id: dataItem.KIEULAP_ID,
        kinhdo: dataItem.KINHDO,
        kyhieu: dataItem.KYHIEU_MOI,
        // kyhieu: dataItem.KYHIEU,
        // kyhieu_moi: dataItem.KYHIEU_MOI,
        loaikc_id: dataItem.LOAIKC_ID,
        loaivo_id: dataItem.LOAIVO_ID,
        ma_csht: dataItem.MA_CSHT,
        ngay_ld:
          dataItem.NGAY_LD != null ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD) : null,
        ngay_sd:
          dataItem.NGAY_SD != null ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD) : null,
        nguondich: dataItem.NGUONDICH,
        noisx_id: dataItem.NOISX_ID,
        phanloaikc_id: dataItem.PHANLOAIKC_ID,
        pho_id: dataItem.PHO_ID_UPDATE ? dataItem.PHO_ID_UPDATE : dataItem.PHO_ID,
        phuong_id: dataItem.PHUONG_ID_UPDATE ? dataItem.PHUONG_ID_UPDATE : dataItem.PHUONG_ID,
        quan_id: dataItem.QUAN_ID_UPDATE ? dataItem.QUAN_ID_UPDATE : dataItem.QUAN_ID,
        sonha: dataItem.SONHA,
        taisan_id: dataItem.TAISAN_ID,
        ten_kc: dataItem.TEN_KC_MOI,
        // ten_kc: dataItem.TEN_KC,
        // ten_kc_moi: dataItem.TEN_KC_MOI,
        tinh_id: dataItem.TINH_ID,
        toanha_id: dataItem.TOANHA_ID,
        tongso_dd: dataItem.TONGSO_DD,
        tramtbi_id: dataItem.TRAMTBI_ID,
        ttsd_id: dataItem.TTSD_ID,
        vido: dataItem.VIDO,
        vitri_id: dataItem.VITRI_ID,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
      };
      try {
        let rs = await this.$root.context.post(
          "/web-cabman/ketcuoi/capnhat_theofile",
          data
        );
        return rs.data;
      } catch (error) {
        let msg = error;
        if (!(error.response == null || error.response == undefined)) {
          msg = error.response.data.message_detail;
          if (msg == null || msg == undefined || msg == "")
            msg = JSON.stringify(error.response.data.data);
        }
        this.$root.toastError("Lỗi cập nhật kết cuối ID " + data.ketcuoi_id + ": " + msg);
      }
      return null;
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
                  case "CAP":
                    rows = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                      header: "A",
                    });
                    this.dsExcelCap = this.convertExcelDsCap(rows);
                    break;
                  case "KETCUOI":
                    rows = xlsx.utils.sheet_to_json(workBook.Sheets[sheetName], {
                      header: "A",
                    });
                    this.dsExcelKetCuoi = this.convertExcelDsKetCuoi(rows);

                    break;
                  default:
                    break;
                }
              }
            }
            this.$root.showLoading(true);
            try {
              if (this.dsChuQuan == null) this.dsChuQuan = await this.getDsChuQuan();
              if (this.dsTTSD == null) this.dsTTSD = await this.getDsTrangThaiSuDung();
              if (this.dsPhanLoaiKC == null)
                this.dsPhanLoaiKC = await this.getDsPhanLoaiKC();
              if (this.dsLoaiKC == null) this.dsLoaiKC = await this.getDsLoaiKC();
              if (this.dsCapSP == null) this.dsCapSP = await this.getDsCapSP();
              if (this.dsKieuLap == null) this.dsKieuLap = await this.getDsKieuLap();
              if (this.dsNoiSX == null) this.dsNoiSX = await this.getDsNoiSX();
              if (this.dsViTriLapDat == null)
                this.dsViTriLapDat = await this.getDsViTriLapDat();
              if (this.dsLoaiVo == null) this.dsLoaiVo = await this.getDsLoaiVo();
              if (this.dsKichCoCap == null)
                this.dsKichCoCap = await this.getDsKichCoCap();
              if (this.dsDienCT == null) this.dsDienCT = await this.getDsDienCT();
              if (this.dsLoaiCap == null) this.dsLoaiCap = await this.getDsLoaiCap();
              if (this.dsLopMC == null) this.dsLopMC = await this.getDsLopMC();
              if (this.dsNhomLC == null) this.dsNhomLC = await this.getDsNhomLC();
              if (this.dsKieuDC == null) this.dsKieuDC = await this.getDsKieuDC();
              if (this.dsKieuCap == null) this.dsKieuCap = await this.getDsKieuCap();
              if (this.dsToaNha == null) this.dsToaNha = await this.getDsToaNha();
              if (this.dsToQuanLy == null) this.dsToQuanLy = await this.getDsToQuanLy();
              if (this.dsDuAn == null) this.dsDuAn = await this.getDsDuAn();

              this.danhSachKetCuoi = await this.convertToDsKetCuoiItems();
              this.danhSachCap = await this.convertToDsCapItems();
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
      let cked = $('input[name="radioDoiTuong"]:checked').val();
      let r = false;
      //let workBook = xlsx.utils.book_new();
      if (cked == "KETCUOI") {
        if (this.danhSachKetCuoi.length > 0) {
          r = true;
          this.$refs.gridKetCuoi.excelExport();
          // //let sheet = xlsx.utils.json_to_sheet(this.danhSachKetCuoi);
          // let dataExcel = [];
          // this.danhSachKetCuoi.forEach((ketCuoi) => {
          //   dataExcel.push({
          //     "Kết cuối ID": ketCuoi.KETCUOI_ID,
          //     "Ký hiệu": ketCuoi.KYHIEU,
          //     "Tên kết cuối": ketCuoi.TEN_KC,
          //     "Dung lượng lắp đặt": ketCuoi.DUNGLUONG,
          //     "Cấp splitter": ketCuoi.CAP_SP,
          //     "Địa chỉ": ketCuoi.DIACHI,
          //     "Trạng thái sử dụng": ketCuoi.TTSD_ID,
          //     "Kiểu lắp": ketCuoi.KIEULAP_ID,
          //     "Nơi sản xuất": ketCuoi.NOISX_ID,
          //     "Vị trí": ketCuoi.VITRI_ID,
          //     chuquan_id: ketCuoi.CHUQUAN_ID,
          //     "Loại vỏ": ketCuoi.LOAIVO_ID,
          //     "Kinh độ": ketCuoi.KINHDO,
          //     "Vĩ độ": ketCuoi.VIDO,
          //     "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)":
          //       ketCuoi.DONVI_ID,
          //     "Dự án (Nhập chính xác mã dự án)": ketCuoi.DUAN_ID,
          //     "Tài sản (Nhập chính xác mã tài sản)": ketCuoi.TAISAN_ID,
          //     "Toàn nhà (Nhập chính xác mã toàn nhà)": ketCuoi.TOANHA_ID,
          //     "Ngày sử dụng(dd/MM/yyyy)": DateTimeLib.toDateDisplay(
          //       ketCuoi.NGAY_SD
          //     ),
          //     "Ngày lắp đặt(dd/MM/yyyy)": DateTimeLib.toDateDisplay(
          //       ketCuoi.NGAY_LD
          //     ),
          //     "Ghi chú": ketCuoi.GHICHU,
          //   });
          // });
          // let sheet = xlsx.utils.json_to_sheet(dataExcel);
          // xlsx.utils.book_append_sheet(workBook, sheet, "KETCUOI");
        }
      } else if (cked == "CAP") {
        if (this.danhSachCap.length > 0) {
          r = true;
          this.$refs.gridCap.excelExport();
          // let dataExcel = [];
          // this.danhSachCap.forEach((cap) => {
          //   dataExcel.push({
          //     "Cáp ID": cap.CAP_ID,
          //     "Ký hiệu": cap.KYHIEU,
          //     "Tên cáp": cap.TEN_CAP,
          //     SODOIDAY: cap.SODOIDAY,
          //     "Kích cỡ": cap.KICHCO_ID,
          //     "Điện cao thế": cap.DIENCT_ID,
          //     "Chiều dài": cap.CHIEUDAI,
          //     "Nơi sản xuất": cap.NOISX_ID,
          //     "Trạng thái sử dụng": cap.TTSD_ID,
          //     "Loại cáp": cap.LOAICAP_ID,
          //     "Kiểu đi cáp": cap.KIEUDC_ID,
          //     "Dự án (Nhập chính xác mã dự án)": cap.DUAN_ID,
          //     "Tài sản (Nhập chính xác mã tài sản)": cap.TAISAN_ID,
          //     "Ngày sử dụng(dd/MM/yyyy)": DateTimeLib.toDateDisplay(
          //       cap.NGAY_SD
          //     ),
          //     "Ngày lắp đặt(dd/MM/yyyy)": DateTimeLib.toDateDisplay(
          //       cap.NGAY_LD
          //     ),
          //     "Ghi chú": cap.GHICHU,
          //     chuquan_id: cap.CHUQUAN_ID,
          //   });
          // });
          // //let sheet = xlsx.utils.json_to_sheet(this.danhSachCap);
          // let sheet = xlsx.utils.json_to_sheet(dataExcel);
          // xlsx.utils.book_append_sheet(workBook, sheet, "CAP");
        }
      }
      if (!r) this.$root.toastInfo("Không có dữ liệu để xuất file");
      // if (r) xlsx.writeFile(workBook, "du-lieu.xlsx");
      // else {
      //   this.$root.toastInfo("Không có dữ liệu để xuất file");
      // }
    },
    buttonExecute_Click: async function () {
      this.$root.showLoading(true);
      try {
        let count = 0;
        let total = 0;
        let boqua = 0;
        if (this.doiTuongCap == "CAP") {
          total = this.danhSachCap.length;
          count = await this.capNhatDsCap();
          this.$refs.gridCap.refresh();
        } else if (this.doiTuongCap == "KETCUOI") {
          total = this.danhSachKetCuoi.length;
          count = await this.capNhatDsKetCuoi();
          this.$refs.gridKetCuoi.refresh();
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
        this.currentData.QUAN_ID_UPDATE = this.$refs.formDiaChi.QUAN_ID;
        this.currentData.PHUONG_ID = this.$refs.formDiaChi.PHUONG_ID;
        this.currentData.PHUONG_ID_UPDATE = this.$refs.formDiaChi.PHUONG_ID;
        this.currentData.PHO_ID = this.$refs.formDiaChi.PHO_ID;
        this.currentData.PHO_ID_UPDATE = this.$refs.formDiaChi.PHO_ID;
        this.currentData.AP_ID = this.$refs.formDiaChi.AP_ID;
        this.currentData.AP_ID_UPDATE = this.$refs.formDiaChi.AP_ID;
        this.currentData.KHU_ID = this.$refs.formDiaChi.KHU_ID;
        this.currentData.KHU_ID_UPDATE = this.$refs.formDiaChi.KHU_ID;
        this.currentData.DACDIEM_ID = this.$refs.formDiaChi.DACDIEM_ID;
        this.currentData.SONHA = this.$refs.formDiaChi.SONHA;
        this.currentData.DIACHI = this.$refs.formDiaChi.DIACHI;
        this.currentData.DIACHI_UPDATE = this.$refs.formDiaChi.DIACHI;

        if (this.currentData.TINH_ID == 0) this.currentData.TINH_ID = null;
        if (this.currentData.QUAN_ID == 0) this.currentData.QUAN_ID = null;
        if (this.currentData.PHUONG_ID == 0) this.currentData.PHUONG_ID = null;
        if (this.currentData.PHO_ID == 0) this.currentData.PHO_ID = null;
        if (this.currentData.AP_ID == 0) this.currentData.AP_ID = null;
        if (this.currentData.KHU_ID == 0) this.currentData.KHU_ID = null;
        if (this.currentData.DACDIEM_ID == 0) this.currentData.DACDIEM_ID = null;
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
      if (dataItem.ITEM_TYPE == "CAP") {
        let item = this.danhSachCap.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateCap(item);
        //this.updateStatus(item);
        this.$refs.gridCap.refresh();
      } else if (dataItem.ITEM_TYPE == "KETCUOI") {
        let item = this.danhSachKetCuoi.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateKetCuoi(item);
        //this.updateStatus(item);
        this.$refs.gridKetCuoi.refresh();
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
            if (!this.isNullOrEmpty(item.MESSAGE[key])) msg += item.MESSAGE[key] + "; ";
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
    },
    checkAddressDSKetCuoi: async function () {
      const data = {
        danhSach: this.dsExcelKetCuoi.map((item, index) => {
          return {
            KYHIEU: item.A,
            DIACHI: {
              QUAN: item.AB ? item.AB : null,
              PHUONG: item.AC ? item.AC : null,
              PHO: item.AD ? item.AD : null,
              KHU: item.AE ? item.AE : null,
              AP: item.AF ? item.AF : null,
            },
          };
        }),
      };
      const rs = await this.$root.context.post(
        "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu",
        data
      );
      let dsAddressCheck = rs.data;
      return rs;
    },
    checkAddressKetCuoi: async function (data) {
      const rs = await this.$root.context.post(
        "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu",
        data
      );
      let dsAddressCheck = rs.data;
      return rs;
    },
  },
};
</script>
