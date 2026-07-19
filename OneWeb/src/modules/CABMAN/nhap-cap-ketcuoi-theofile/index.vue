<template src="./index.template.html"></template>
<script>
import xlsx from "xlsx";
import DateTimeLib from "../../../utils/DateTimeLib";
import { Sort, Filter, ExcelExport, Freeze, Resize } from "@syncfusion/ej2-vue-grids";
import FormDiaChi from "../nhap-cot-doancong-becap-theofile/DiaChi/FormDiaChi.vue";
import RowCapTemplate from "../sua-cap-ketcuoi-theofile/RowTemplate/cap.vue";
import RowKetCuoiTemplate from "../sua-cap-ketcuoi-theofile/RowTemplate/ketcuoi.vue";
import FormToaDo from "../nhap-cot-doancong-becap-theofile/ToaDo/FormToaDo.vue";
import debounce from "lodash/debounce";
import Axios from "axios";
import token from "../../../plugins/token";
// import { L10n } from "@syncfusion/ej2-base";
// L10n.load({
//   "vi-VN": {
//     grid: {
//       EmptyRecord: "Không có bản ghi nào để hiển thị",
//       Item: "Bản ghi",
//       Items: "Bản ghi",
//       FilterbarTitle: "",
//     },
//   },
// });
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
      KetCuoiFields: [
        {
          name: "KYHIEU",
          text: "Ký hiệu",
          tableName: null,
        },
        {
          name: "TEN_KC",
          text: "Tên kết cuối",
          tableName: null,
        },
        {
          name: "CAP_SP",
          text: "Cấp splitter",
          tableName: null,
        },
        {
          name: "CAPGOC_ID",
          text: "Ký hiệu cáp gốc",
          tableName: null,
        },
        {
          name: "KETCUOI_CHA_ID",
          text: "Ký hiệu kết cuối cha",
          tableName: null,
        },
        {
          name: "DIACHI",
          text: "Địa chỉ",
          tableName: null,
        },
        {
          name: "TONGSO_DD",
          text: "Số cổng",
          tableName: null,
        },
        {
          name: "TTSD_ID",
          text: "Trạng thái sử dụng",
          tableName: "TRANGTHAI_SD",
        },
        {
          name: "KIEULAP_ID",
          text: "Kiểu lắp",
          tableName: "KIEU_LAP",
        },
        {
          name: "NOISX_ID",
          text: "Nơi sản xuất",
          tableName: "NOI_SX",
        },
        {
          name: "VITRI_ID",
          text: "Vị trí",
          tableName: "VITRI",
        },
        {
          name: "LOAIVO_ID",
          text: "Loại vỏ",
          tableName: "LOAI_VO",
        },
        {
          name: "PHANLOAIKC_ID",
          text: "Phân loại KC",
          tableName: "PHANLOAI_KC",
        },
        {
          name: "LOAIKC_ID",
          text: "Loại kết cuối",
          tableName: "LOAI_KC",
        },
        {
          name: "KINHDO",
          text: "Kinh độ",
          tableName: null,
        },
        {
          name: "VIDO",
          text: "Vĩ độ",
          tableName: null,
        },
        {
          name: "DONVI_ID",
          text: "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)",
          tableName: null,
        },
        {
          name: "DUAN_ID",
          text: "Dự án (Nhập chính xác mã dự án)",
          tableName: null,
        },
        {
          name: "TAISAN_ID",
          text: "Tài sản (Nhập chính xác mã tài sản)",
          tableName: null,
        },
        {
          name: "TOANHA_ID",
          text: "Toàn nhà (Nhập chính xác mã toàn nhà)",
          tableName: null,
        },
        {
          name: "NGAY_SD",
          text: "Ngày sử dụng(dd/MM/yyyy)",
          tableName: null,
        },
        {
          name: "NGAY_LD",
          text: "Ngày lắp đặt(dd/MM/yyyy)",
          tableName: null,
        },
        {
          name: "GHICHU",
          text: "Ghi chú",
          tableName: null,
        },
        {
          name: "MA_CSHT",
          text: "MA_CSHT",
          tableName: null,
        },
        {
          name: "MA_GOITHAU_IMS",
          text: "MA_GOITHAU_IMS",
          tableName: null,
        },
        {
          name: "MA_HOPDONG_IMS",
          text: "MA_HOPDONG_IMS",
          tableName: null,
        },
        {
          name: "SONHA",
          text: "Số nhà",
          tableName: null,
        },
        {
          name: "KHU",
          text: "Khu",
          tableName: null,
        },
        {
          name: "NGO_AP",
          text: "Ngõ/Ấp",
          tableName: null,
        },
        {
          name: "DUONG_PHO",
          text: "Đường/Phố",
          tableName: null,
        },
        {
          name: "PHUONG_XA",
          text: "Phường/Xã",
          tableName: null,
        },
        {
          name: "QUAN_HUYEN",
          text: "Quận/Huyện",
          tableName: null,
        },
      ],
      CapFields: [
        {
          name: "KYHIEU",
          text: "Ký hiệu",
          tableName: null,
        },
        {
          name: "TEN_CAP",
          text: "Tên cáp",
          tableName: null,
        },
        {
          name: "CAPGOC_ID",
          text: "Ký hiệu cáp gốc",
          tableName: null,
        },
        {
          name: "SODOIDAY",
          text: "Số đôi dây",
          tableName: null,
        },
        {
          name: "KICHCO_ID",
          text: "Kích cỡ",
          tableName: null,
        },
        {
          name: "DIENCT_ID",
          text: "Điện cao thế",
          tableName: null,
        },
        {
          name: "CHIEUDAI",
          text: "Chiều dài",
          tableName: null,
        },
        {
          name: "NOISX_ID",
          text: "Nơi sản xuất",
          tableName: null,
        },
        {
          name: "TTSD_ID",
          text: "Trạng thái sử dụng",
          tableName: null,
        },
        {
          name: "LOAICAP_ID",
          text: "Loại cáp",
          tableName: null,
        },
        {
          name: "NHOMLC_ID",
          text: "Nhóm loại cáp",
          tableName: null,
        },
        {
          name: "KIEUDC_ID",
          text: "Kiểu đi cáp",
          tableName: null,
        },
        {
          name: "PHANLOAIKC_ID",
          text: "Phân loại KC",
          tableName: "PHANLOAI_KC",
        },
        {
          name: "LOPMC_ID",
          text: "Lớp mạng cáp",
          tableName: null,
        },
        {
          name: "KIEUCAP_ID",
          text: "Kiểu cáp",
          tableName: null,
        },
        {
          name: "DONVI_ID",
          text: "Trạm thiết bị (Nhập chính xác tên hoặc mã đơn vị)",
          tableName: null,
        },
        {
          name: "TOQL_ID",
          text: "Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)",
          tableName: null,
        },
        {
          name: "KETCUOI_N_ID",
          text: "Ký hiệu kết cuối nguồn",
          tableName: null,
        },
        {
          name: "KETCUOI_D_ID",
          text: "Ký hiệu kết cuối đích",
          tableName: null,
        },
        {
          name: "DUAN_ID",
          text: "Dự án (Nhập chính xác mã dự án)",
          tableName: null,
        },
        {
          name: "TAISAN_ID",
          text: "Tài sản (Nhập chính xác mã tài sản)",
          tableName: null,
        },
        {
          name: "NGAY_SD",
          text: "Ngày sử dụng(dd/MM/yyyy)",
          tableName: null,
        },
        {
          name: "NGAY_LD",
          text: "Ngày lắp đặt(dd/MM/yyyy)",
          tableName: null,
        },
        {
          name: "GHICHU",
          text: "Ghi chú",
          tableName: null,
        },
        {
          name: "MA_CSHT",
          text: "MA_CSHT",
          tableName: null,
        },
        {
          name: "MA_GOITHAU_IMS",
          text: "MA_GOITHAU_IMS",
          tableName: null,
        },
        {
          name: "MA_HOPDONG_IMS",
          text: "MA_HOPDONG_IMS",
          tableName: null,
        },
        {
          name: "SOI_BD",
          text: "Sợi bắt đầu",
          tableName: null,
        },
      ],
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
          "web-cabman/excel/download-mau-nhapmoi-cap-ketcuoi",
          null
        );
        if (r && r.status == 200) {
          let fileName =
            "MAU_NHAPMOI_CAP_KETCUOI_" + this.$root.token.getMaTinh() + ".xlsx";
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
    convertToDsKetCuoiItems: async function () {
      try {
        let r = [];
        if (this.dsExcelKetCuoi != null) {
          let dsID_EXCEL = [];
          let dsSO_HIEU_EXCEL = [];
          let ketCuoiSize = this.dsExcelKetCuoi.length;
          for (let j = 0; j < ketCuoiSize; j++) {
            const ketCuoi = this.dsExcelKetCuoi[j];
            let kyhieuField = this.KetCuoiFields.find((x) => x.name == "KYHIEU");
            if (kyhieuField != null && kyhieuField.positionColumn != null) {
              if (
                !(
                  this.isNullOrEmpty(ketCuoi[kyhieuField.positionColumn]) ||
                  dsSO_HIEU_EXCEL.includes(ketCuoi[kyhieuField.positionColumn])
                )
              ) {
                dsSO_HIEU_EXCEL.push(ketCuoi[kyhieuField.positionColumn]);
              }
            }
          }
          if (dsID_EXCEL.length == ketCuoiSize || dsSO_HIEU_EXCEL.length == ketCuoiSize) {
            if (ketCuoiSize == 0) return r;
            // first, save cache map
            const first = await this.convertToKetCuoiItem(this.dsExcelKetCuoi[0]);
            let convertToKetCuoiList = [];
            for (let i = 0; i < ketCuoiSize; i++) {
              convertToKetCuoiList.push(
                this.convertToKetCuoiItem(this.dsExcelKetCuoi[i])
              );
            }
            r = await Promise.all(convertToKetCuoiList);
            r.forEach((e, index) => (e.STT = index + 1));
            // for (let i = 0; i < ketCuoiSize; i++) {
            //
            //   let v = await this.convertToKetCuoiItem(
            //     this.dsExcelKetCuoi[i]
            //     // currentItem
            //   );
            //   v.STT = i + 1;
            //   r.push(v);
            // }
          } else {
            this.$root.toastError(
              "Trường cột id hoặc số hiệu kết cuối đang bị thiếu dữ liệu. \nVui lòng bổ sung và thử lại."
            );
          }
        }
        return r;
      } catch (e) {
        console.log(e);
      }
    },
    convertToKetCuoiItem: async function (dataItem) {
      // A: 'Ký hiệu'
      // B: 'Tên kết cuối'
      // C: 'Cấp splitter'
      // D: 'Ký hiệu cáp gốc'
      // E: 'Địa chỉ'
      // F: 'Số cổng'
      // G: 'Trạng thái sử dụng'
      // H: 'Kiểu lắp'
      // I: 'Nơi sản xuất'
      // J: 'Vị trí'
      // K: 'Loại vỏ'
      // L: 'Phân loại KC'
      // M: 'Loại kết cuối'
      // N: 'Kinh độ'
      // O: 'Vĩ độ'
      // P: 'Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)'
      // Q: 'Dự án (Nhập chính xác mã dự án)'
      // R: 'Tài sản (Nhập chính xác mã tài sản)'
      // S: 'Toàn nhà (Nhập chính xác mã toàn nhà)'
      // T: 'Ngày sử dụng(dd/MM/yyyy)'
      // U: 'Ngày lắp đặt(dd/MM/yyyy)'
      // V: 'Ghi chú'
      // W: MA_CSHT
      // X: MA_GOITHAU_IMS
      // Y: MA_HOPDONG_IMS
      // Z: Khu
      // AA: Số nhà
      // AB: Ngõ/Ấp
      // AC: Đường/Phố
      // AD: Phường/Xã
      // AE: Quận/Huyện
      // AF: Ký hiệu kết cuối cha
      let msg = {};
      // let dungLuong = 0;
      // if (!this.isNullOrEmpty(dataItem.F)) {
      //   if (!this.validateDouble(dataItem.F)) {
      //     //msg += "<div>Vui lòng nhập đúng định dạng dung lượng (số thực)</div>";
      //   } else dungLuong = dataItem.F;
      // }
      // else {
      //   dungLuong = currentItem.DUNGLUONG;
      // }

      let kinhDo = 0;
      let kinhDoField = this.KetCuoiFields.find((x) => x.name == "KINHDO");
      if (kinhDoField && kinhDoField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[kinhDoField.positionColumn])) {
          // if (!this.validateDouble(dataItem[kinhDoField.positionColumn])) {
          //   // msg += "<div>Vui lòng nhập đúng định dạng kinh độ (số thực)</div>";
          // } else kinhDo = dataItem.N;
          kinhDo = dataItem[kinhDoField.positionColumn];
        }
      }
      // else {
      //   kinhDo = currentItem.KINHDO;
      // }
      let viDo = 0;
      let viDoField = this.KetCuoiFields.find((x) => x.name == "VIDO");
      if (viDoField && viDoField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[viDoField.positionColumn])) {
          if (!this.validateDouble(dataItem[viDoField.positionColumn])) {
            // msg += "<div>Vui lòng nhập đúng định dạng vĩ độ (số thực)</div>";
          } else viDo = dataItem[viDoField.positionColumn];
        }
      }

      let ngayLapDat = null;
      let ngayLDField = this.KetCuoiFields.find((x) => x.name == "NGAY_LD");
      if (ngayLDField && ngayLDField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ngayLDField.positionColumn])) {
          let ngayld = dataItem[ngayLDField.positionColumn];
          if (typeof ngayld === "number") {
            ngayLapDat = new Date(Math.round((ngayld - 25569) * 86400 * 1000));
          } else {
            if (!this.validateDate(dataItem[ngayLDField.positionColumn])) {
              // msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
            } else
              ngayLapDat = DateTimeLib.toDateObject(dataItem[ngayLDField.positionColumn]);
          }
        }
      }
      let ngaySuDung = null;
      let ngaySDField = this.KetCuoiFields.find((x) => x.name == "NGAY_SD");
      if (ngaySDField && ngaySDField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ngaySDField.positionColumn])) {
          let ngaysd = dataItem[ngaySDField.positionColumn];
          if (typeof ngaysd === "number") {
            ngaySuDung = new Date(Math.round((ngaysd - 25569) * 86400 * 1000));
          } else {
            if (!this.validateDate(dataItem[ngaySDField.positionColumn])) {
              // msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
            } else
              ngaySuDung = DateTimeLib.toDateObject(dataItem[ngaySDField.positionColumn]);
          }
        }
      }

      // Gán lại tài sản và dự án
      let duAnID = null;
      let taiSanID = null;
      let taiSanText = null;
      let duanField = this.KetCuoiFields.find((x) => x.name == "DUAN_ID");
      let taisanField = this.KetCuoiFields.find((x) => x.name == "TAISAN_ID");
      if (
        duanField &&
        duanField.positionColumn &&
        taisanField &&
        taisanField.positionColumn
      ) {
        if (
          (!this.isNullOrEmpty(dataItem[duanField.positionColumn]) &&
            !this.isNullOrEmpty(dataItem[taisanField.positionColumn])) ||
          !this.isNullOrEmpty(dataItem[taisanField.positionColumn])
        ) {
          // Nếu tài sản-dự án đi 1 cặp hoặc chỉ có tài sản
          let key =
            dataItem[taisanField.positionColumn] +
            ":" +
            dataItem[duanField.positionColumn];
          // check map
          let vMap = this.dsTaiSanDuAnTheoMa.get(key);
          let v;
          if (!vMap) {
            v = await this.getTaiSanDuAnTheoMa(
              dataItem[taisanField.positionColumn],
              dataItem[duanField.positionColumn]
            );
            this.dsTaiSanDuAnTheoMa.set(key, v);
          } else {
            v = vMap;
          }
          if (v != null) {
            duAnID = v.DUAN_ID;
            taiSanID = v.TAISAN_ID;
            taiSanText = dataItem[taisanField.positionColumn];
          } else if (!this.isNullOrEmpty(dataItem[duanField.positionColumn])) {
            // Nếu chỉ có thông tin dự án
            let ck;
            let vMap = this.dsDuAnTheoMaCT.get(dataItem[duanField.positionColumn]);
            if (!vMap) {
              ck = await this.getDuAnTheoSoHieu(dataItem[duanField.positionColumn]);
              this.dsDuAnTheoMaCT.set(dataItem[duanField.positionColumn], ck);
              this.dsDuAnTheoDuAnId.set(ck.DUAN_ID, ck);
            } else {
              ck = vMap;
            }
            // let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem[duanField.positionColumn]);
            if (ck == null) {
              duAnID = null;
              taiSanID = null;
              // msg += "<div>Không tìm thấy dự án tương ứng</div>";
            } else {
              duAnID = ck.DUAN_ID;
              taiSanID = null;
            }
          } else {
            duAnID = null;
            taiSanID = null;
          }
        } else {
          taiSanID = null;
          if (duanField && duanField.positionColumn) {
            if (!this.isNullOrEmpty(dataItem[duanField.positionColumn])) {
              let ck;
              let vMap = this.dsDuAnTheoMaCT.get(dataItem[duanField.positionColumn]);
              if (!vMap) {
                ck = await this.getDuAnTheoSoHieu(dataItem[duanField.positionColumn]);
                console.log(ck, "ck");
                this.dsDuAnTheoMaCT.set(dataItem[duanField.positionColumn], ck);
                this.dsDuAnTheoDuAnId.set(ck.DUAN_ID, ck);
              } else {
                ck = vMap;
              }
              // let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem[duanField.positionColumn]);
              if (ck == null) {
                duAnID = null;
                // msg += "<div>Không tìm thấy dự án tương ứng</div>";
              } else duAnID = ck.DUAN_ID;
            } else {
              duAnID = null;
            }
          }
        }
      }

      let capSP = null;
      let capSPField = this.KetCuoiFields.find((x) => x.name == "CAP_SP");
      if (capSPField && capSPField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[capSPField.positionColumn])) {
          let ck = this.dsCapSP.find(
            (x) => x.CAPSP_ID == dataItem[capSPField.positionColumn]
          );
          if (ck == null) {
            // msg.capSP = "Không tìm thấy cấp Splitter";
          } else capSP = ck.CAPSP_ID;
        }
      }

      let ttsdID = null;
      let ttsdField = this.KetCuoiFields.find((x) => x.name == "TTSD_ID");
      if (ttsdField && ttsdField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ttsdField.positionColumn])) {
          let ck = this.dsTTSD.find(
            (x) => x.TTSD_ID == dataItem[ttsdField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
          } else ttsdID = ck.TTSD_ID;
        }
      }

      let kieuLapID = null;
      let ketlapField = this.KetCuoiFields.find((x) => x.name == "KIEULAP_ID");
      if (ketlapField && ketlapField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ketlapField.positionColumn])) {
          let ck = this.dsKieuLap.find(
            (x) => x.KIEULAP_ID == dataItem[ketlapField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy kiểu lắp</div>";
          } else kieuLapID = ck.KIEULAP_ID;
        }
      }

      let noiSXID = null;
      let noiSXField = this.KetCuoiFields.find((x) => x.name == "NOISX_ID");
      if (noiSXField && noiSXField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[noiSXField.positionColumn])) {
          let ck = this.dsNoiSX.find(
            (x) => x.NOISX_ID == dataItem[noiSXField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy nơi sản xuất</div>";
          } else noiSXID = ck.NOISX_ID;
        }
      }
      let viTriID = null;
      let vitriField = this.KetCuoiFields.find((x) => x.name == "VITRI_ID");
      if (vitriField && vitriField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[vitriField.positionColumn])) {
          viTriID = dataItem.J;
          let ck = this.dsViTriLapDat.find(
            (x) => x.VITRI_ID == dataItem[vitriField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy vị trí</div>";
          } else viTriID = ck.VITRI_ID;
        }
      }

      let toaNhaID = null;
      let toanhaField = this.KetCuoiFields.find((x) => x.name == "TOANHA_ID");
      if (toanhaField && toanhaField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[toanhaField.positionColumn])) {
          let ck = this.dsToaNha.find(
            (x) => x.MA_TOANHA == dataItem[toanhaField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy tòa nhà</div>";
          } else toaNhaID = ck.TOANHA_ID;
        }
      }
      let phanloaiKCField = this.KetCuoiFields.find((x) => x.name == "PHANLOAIKC_ID");
      let phanLoaiKCID =
        phanloaiKCField && phanloaiKCField.positionColumn
          ? dataItem[phanloaiKCField.positionColumn]
          : null;
      let phanLoaiKC = "";
      if (!this.isNullOrEmpty(phanLoaiKCID)) {
        let ck = this.dsPhanLoaiKC.find((x) => x.PHANLOAIKC_ID == phanLoaiKCID);
        if (ck != null) {
          phanLoaiKC = ck.PHANLOAI_KC;
        }
      }

      let kyhieuCapgocField = this.KetCuoiFields.find((x) => x.name == "CAPGOC_ID");
      let kyhieuCapgoc = null;
      if (kyhieuCapgocField && kyhieuCapgocField.positionColumn) {
        kyhieuCapgoc = dataItem[kyhieuCapgocField.positionColumn];
      }

      let diachiField = this.KetCuoiFields.find((x) => x.name == "DIACHI");
      let diachi = null;
      if (diachiField && diachiField.positionColumn) {
        diachi = dataItem[diachiField.positionColumn];
      }

      let toktField = this.KetCuoiFields.find((x) => x.name == "DONVI_ID");
      let tokt = null;
      console.log(toktField, "toktField");
      if (toktField && toktField.positionColumn) {
        tokt = dataItem[toktField.positionColumn];
      }

      let ghichuField = this.KetCuoiFields.find((x) => x.name == "GHICHU");
      let ghichu = null;
      if (ghichuField && ghichuField.positionColumn) {
        ghichu = dataItem[ghichuField.positionColumn];
      }

      let kyhieuKetCuoiChaField = this.KetCuoiFields.find(
        (x) => x.name == "KETCUOI_CHA_ID"
      );
      let kyhieuKetCuoiCha = null;
      if (kyhieuKetCuoiChaField && kyhieuKetCuoiChaField.positionColumn) {
        kyhieuKetCuoiCha = dataItem[kyhieuKetCuoiChaField.positionColumn];
      }

      let kyhieuKetCuoiField = this.KetCuoiFields.find((x) => x.name == "KYHIEU");
      let kyhieuKetCuoi = null;
      if (kyhieuKetCuoiField && kyhieuKetCuoiField.positionColumn) {
        kyhieuKetCuoi = dataItem[kyhieuKetCuoiField.positionColumn];
      }

      let loaiKCField = this.KetCuoiFields.find((x) => x.name == "LOAIKC_ID");
      let loaiKCId = null;
      if (loaiKCField && loaiKCField.positionColumn) {
        loaiKCId = parseInt(dataItem[loaiKCField.positionColumn], 10);
      }

      let loaiVoField = this.KetCuoiFields.find((x) => x.name == "LOAIVO_ID");
      let loaiVoId = null;
      if (loaiVoField && loaiVoField.positionColumn) {
        loaiVoId = parseInt(dataItem[loaiVoField.positionColumn], 10);
      }

      let maTaisan =
        taisanField && taisanField.positionColumn
          ? dataItem[taisanField.positionColumn]
          : null;

      let tenKCField = this.KetCuoiFields.find((x) => x.name == "TEN_KC");
      let tenKC = null;
      if (tenKCField && tenKCField.positionColumn) {
        tenKC = dataItem[tenKCField.positionColumn];
      }

      let tongsoDDField = this.KetCuoiFields.find((x) => x.name == "TONGSO_DD");
      let tongsoDD = null;
      if (tongsoDDField && tongsoDDField.positionColumn) {
        tongsoDD = dataItem[tongsoDDField.positionColumn];
      }
      let maCSHTField = this.KetCuoiFields.find((x) => x.name == "MA_CSHT");
      let maCSHT = null;
      if (maCSHTField && maCSHTField.positionColumn) {
        maCSHT = dataItem[maCSHTField.positionColumn];
      }

      let maGoiThauIMSField = this.KetCuoiFields.find((x) => x.name == "MA_GOITHAU_IMS");
      let maGoiThauIMS = null;
      if (maGoiThauIMSField && maGoiThauIMSField.positionColumn) {
        maGoiThauIMS = dataItem[maGoiThauIMSField.positionColumn];
      }

      let maHopDongIMSField = this.KetCuoiFields.find((x) => x.name == "MA_HOPDONG_IMS");
      let maHopDongIMS = null;
      if (maHopDongIMSField && maHopDongIMSField.positionColumn) {
        maHopDongIMS = dataItem[maHopDongIMSField.positionColumn];
      }

      let soNhaField = this.KetCuoiFields.find((x) => x.name == "SONHA");
      let soNha = null;
      if (soNhaField && soNhaField.positionColumn) {
        soNha = dataItem[soNhaField.positionColumn];
      }

      let khuField = this.KetCuoiFields.find((x) => x.name == "KHU");
      let khu = null;
      if (khuField && khuField.positionColumn) {
        khu = dataItem[khuField.positionColumn];
      }

      let ngoApField = this.KetCuoiFields.find((x) => x.name == "NGO_AP");
      let ngoAp = null;
      if (ngoApField && ngoApField.positionColumn) {
        ngoAp = dataItem[ngoApField.positionColumn];
      }

      let duongPhoField = this.KetCuoiFields.find((x) => x.name == "DUONG_PHO");
      let duongPho = null;
      if (duongPhoField && duongPhoField.positionColumn) {
        duongPho = dataItem[duongPhoField.positionColumn];
      }

      let phuongXaField = this.KetCuoiFields.find((x) => x.name == "PHUONG_XA");
      let phuongXa = null;
      if (phuongXaField && phuongXaField.positionColumn) {
        phuongXa = dataItem[phuongXaField.positionColumn];
      }

      let quanHuyenField = this.KetCuoiFields.find((x) => x.name == "QUAN_HUYEN");
      let quanHuyen = null;
      if (quanHuyenField && quanHuyenField.positionColumn) {
        quanHuyen = dataItem[quanHuyenField.positionColumn];
      }

      let data = {
        danhSach: [
          {
            KYHIEU: kyhieuKetCuoi,
            DIACHI: {
              PHO: duongPho ?? null,
              PHUONG: phuongXa ?? null,
              KHU: khu ?? null,
              AP: ngoAp ?? null,
              QUAN: quanHuyen ?? null,
            },
          },
        ]
      }
      let rsAddress = await this.$root.context.post(
        "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu",
        data
      );
      this.dsAddressCheck.push(rsAddress.data[0]);

      //Gán id cho các trường địa chỉ
      const addressCheck = this.dsAddressCheck.find((x) => x.KYHIEU == kyhieuKetCuoi);
      let phoId = null;
      let khuId = null;
      let apId = null;
      let phuongId = null;
      let quanId = null;
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


      let ketcuoiChaId = null;
      let ketcuoiChaText = null;
      if (kyhieuKetCuoiCha) {
        let ck = await this.getKetCuoiTheoKyHieu(kyhieuKetCuoiCha);
        if (ck){
          ketcuoiChaId = ck.KETCUOI_ID;
          ketcuoiChaText = ck.TEN_KC;
        }
      }

      let result = {
        CAP_SP: capSP,
        CAPGOC_ID: null,
        KYHIEU_CAPGOC: kyhieuCapgoc,
        DIACHI: diachi,
        DONVI_ID: null, // id to ky thuat
        TOKYTHUAT: tokt, // tên hoặc mã đơn vị tổ kỹ thuật
        DUAN_ID: duAnID,
        GHICHU: ghichu,
        KETCUOI_CHA_ID: ketcuoiChaId,
        KETCUOI_CHA_TEXT: ketcuoiChaText,
        KYHIEU_KETCUOI_CHA: kyhieuKetCuoiCha,
        KIEULAP_ID: kieuLapID,
        KINHDO: kinhDo,
        KYHIEU: kyhieuKetCuoi,
        LOAIKC_ID: loaiKCId,
        LOAIVO_ID: loaiVoId,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        // NGUONDICH: currentItem.NGUONDICH,
        NOISX_ID: parseInt(noiSXID, 10),
        PHANLOAIKC: phanLoaiKC,
        PHANLOAIKC_ID: parseInt(phanLoaiKCID, 10),
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        TEN_KC: tenKC,
        TOANHA_ID: toaNhaID,
        TONGSO_DD: parseInt(tongsoDD, 10),
        TTSD_ID: ttsdID,
        VIDO: viDo,
        VITRI_ID: viTriID,
        MA_CSHT: maCSHT,
        MA_GOITHAU_IMS: maGoiThauIMS,
        MA_HOPDONG_IMS: maHopDongIMS,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "KETCUOI",
        IS_EDIT: false,
        KHU: khu,
        NGO_AP: ngoAp,
        DUONG_PHO: duongPho,
        PHUONG_XA: phuongXa,
        QUAN_HUYEN: quanHuyen,
        SONHA: soNha,
        KHU_ID: khuId,
        PHO_ID: phoId,
        AP_ID: apId,
        PHUONG_ID: phuongId,
        QUAN_ID: quanId,
        TINH_ID: this.$root.token.getPhanVungID(),
      };
      await this.validateKetCuoi(result);
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
    validateKetCuoi: async function (item) {
      let it = this.dsLoaiKC.find((x) => x.LOAIKC_ID == item.LOAIKC_ID);
      if (!(it == undefined || it == null)) {
        item.LOAIKC_TEXT = it.LOAI_KC;
      }
      it = this.dsCapSP.find((x) => x.CAPSP_ID.toString() == item.CAP_SP + "");
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
      it = this.dsDuAnTheoDuAnId.get(item.DUAN_ID);
      if (it) {
        item.DUAN_TEXT = it.MA_CT;
      }
      // it = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      // if (!(it == undefined || it == null)) {
      //   item.DUAN_TEXT = it.MA_CT;
      // }
      it = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(it == undefined || it == null)) {
        item.CHUQUAN_TEXT = it.TENCHUQUAN;
      }
      it = this.dsToQuanLy.find(
        (x) => x.MA_DV == item.TOKYTHUAT || x.TEN_DV == item.TOKYTHUAT
      );
      if (!(it == undefined || it == null)) {
        item.DONVI_TEXT = it.TEN_DV;
        item.DONVI_ID = it.DONVI_ID;
      }
      it = this.dsToaNha.find((x) => x.TOANHA_ID == item.TOANHA_ID);
      if (!(it == undefined || it == null)) {
        item.TOANHA_TEXT = it.TEN_TOANHA;
      }

      let rs = true;
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else {
        let ketcuoi = await this.getKetCuoiTheoKyHieuVaPhanLoai(
          item.KYHIEU,
          item.PHANLOAIKC_ID
        );
        if (ketcuoi.KETCUOI_ID && item.KETCUOI_ID == null) {
          item.MESSAGE.kyHieu =
            "Phân loại kết cuối, Ký hiệu đã được sử dụng.\r\nVui lòng chọn một Phân loại kết cuối, Ký hiệu khác.";
          rs = false;
        }
      }
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
      if (this.isNullOrEmpty(item.DONVI_ID)) {
        let ck = this.dsToQuanLy.find(
          (x) => x.TEN_DV == item.TOKYTHUAT || x.MA_DV == item.TOKYTHUAT
        );
        if (ck == null) {
          item.MESSAGE.donVi =
            "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
          rs = false;
        } else {
          item.DONVI_ID = ck.DONVI_ID;
        }
      } else {
        item.MESSAGE.donVi = "";
      }
      // Gán lại cáp gốc
      if (!this.isNullOrEmpty(item.KYHIEU_CAPGOC)) {
        // check map
        let capGocMap = this.dsCapGocTheoKyHieu.get(item.KYHIEU_CAPGOC);
        let capGoc;
        if (!capGocMap) {
          capGoc = await this.getDsCapGoc(item.KYHIEU_CAPGOC);
          this.dsCapGocTheoKyHieu.set(item.KYHIEU_CAPGOC, capGoc);
        } else {
          capGoc = capGocMap;
        }
        if (capGoc && capGoc.CAP_ID) {
          item.CAPGOC_ID = capGoc.CAP_ID;
        } else {
          item.CAPGOC_ID = null;
          item.MESSAGE.capgoc =
            "Cáp gốc không tồn tại. Kiểm tra lại ký hiệu cáp gốc trong file nhập vào";
          rs = false;
          // msg += "<div>Cáp gốc không tồn tại. Kiểm tra lại ký hiệu cáp gốc trong file nhập vào</div>";
        }
      } else {
        item.CAPGOC_ID = null;
        item.MESSAGE.capgoc =
          "Chưa có thông tin cáp gốc. Hãy nhập ký hiệu cáp gốc vào cột dữ liệu trong file rồi thử lại";
        rs = false;
        // msg += "<div>Chưa có thông tin cáp gốc. Hãy nhập ký hiệu cáp gốc vào cột dữ liệu trong file rồi thử lại</div>";
      }
      // Gán lại kết cuối cha
      if (!this.isNullOrEmpty(item.KYHIEU_KETCUOI_CHA)) {
        // check map
        let kcMap = this.dsKetCuoiTheoKyHieu.get(item.KYHIEU_KETCUOI_CHA);
        if (!kcMap) {
          let ketcuoi = await this.getKetCuoiTheoKyHieu(item.KYHIEU_KETCUOI_CHA);
          if (ketcuoi && ketcuoi.KETCUOI_ID) {
            item.KETCUOI_CHA_ID = ketcuoi.KETCUOI_ID;
            item.KETCUOI_CHA_TEXT = ketcuoi.TEN_KC;
            this.dsKetCuoiTheoKyHieu.set(item.KYHIEU_KETCUOI_CHA, ketcuoi);
          } else {
            item.KETCUOI_CHA_ID = null;
            item.MESSAGE.ketcuoi_cha_id =
              "Kết cuối cha không tồn tại. Kiểm tra lại ký hiệu kết cuối cha trong file nhập vào";
          }
        } else {
          item.KETCUOI_CHA_ID = kcMap.KETCUOI_ID;
        }
      }

      console.log(item, 'itemitem')
      // Validate address ket cuoi
      const addressCheck = this.dsAddressCheck.find((x) => x.KYHIEU == item.KYHIEU);
      if (addressCheck?.Status.InvalidFields.length > 0) {
        addressCheck?.Status.InvalidFields.forEach((e) => {
          switch (e) {
            case "AP":
              item.MESSAGE.ngoAp = addressCheck.Status.Message;
              rs = false;
              break;
            case "PHO":
              item.MESSAGE.duongPho = addressCheck.Status.Message;
              rs = false;
              break;
            case "PHUONG":
              item.MESSAGE.phuongXa = addressCheck.Status.Message;
              rs = false;
              break;
            case "QUAN":
              item.MESSAGE.quanHuyen = addressCheck.Status.Message;
              rs = false;
              break;
            case "KHU":
              item.MESSAGE.khu = addressCheck.Status.Message;
              rs = false;
              break;
            default:
              break;
          }
        });
      }

      //check
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   else item.TRANGTHAI = "Đã lưu";
      // }
      // Kiểm tra mess lỗi
      for (let key in item.MESSAGE) {
        if (item.MESSAGE[key] !== "" && key !== "isValid") {
          item.TRANGTHAI = item.MESSAGE[key];
          rs = false;
          break;
        }
      }
      // Kiểm tra TRANGTHAI
      // if (item.TRANGTHAI !== "Sẵn sàng" && item.TRANGTHAI !== "Đã lưu")
      //   rs = false
      item.MESSAGE.isValid = rs;
      // Kiểm tra TRANGTHAI
      if (rs) {
        if (item.KETCUOI_ID == null) item.TRANGTHAI = "Sẵn sàng";
        else item.TRANGTHAI = "Đã lưu";
      }
      console.log(item, 'itemm')
      return rs;
    },
    convertToDsCapItems: async function () {
      let r = [];
      if (this.dsExcelCap != null) {
        let dsID_EXCEL = [];
        let dsSO_HIEU_EXCEL = [];
        let capSize = this.dsExcelCap.length;
        for (let j = 0; j < capSize; j++) {
          const cap = this.dsExcelCap[j];
          let kyhieuField = this.CapFields.find((x) => x.name == "KYHIEU");
          if (kyhieuField != null && kyhieuField.positionColumn != null) {
            if (
              !(
                this.isNullOrEmpty(cap[kyhieuField.positionColumn]) ||
                dsSO_HIEU_EXCEL.includes(cap[kyhieuField.positionColumn])
              )
            ) {
              dsSO_HIEU_EXCEL.push(cap[kyhieuField.positionColumn]);
            }
          }
        }
        if (dsID_EXCEL.length == capSize || dsSO_HIEU_EXCEL.length == capSize) {
          if (capSize == 0) return r;
          // first, save cache map
          const first = await this.convertToCapItem(this.dsExcelCap[0]);
          let convertToCapList = [];
          for (let i = 0; i < capSize; i++) {
            convertToCapList.push(this.convertToCapItem(this.dsExcelCap[i]));
          }
          r = await Promise.all(convertToCapList);
          r.forEach((e, index) => (e.STT = index + 1));
          // for (let i = 0; i < capSize; i++) {
          //   let v = await this.convertToCapItem(
          //     this.dsExcelCap[i]
          //     // currentItem
          //   );
          //   v.STT = i + 1;
          //   r.push(v);
          // }
        } else {
          this.$root.toastError(
            "Trường cột id hoặc ký hiệu cáp đang bị thiếu dữ liệu. \nVui lòng bổ sung và thử lại."
          );
        }
      }
      return r;
    },
    convertToCapItem: async function (dataItem) {
      // A: 'Ký hiệu'
      // B: 'Tên cáp'
      // C: 'Kích cỡ'
      // D: Số đôi dây
      // E: Điện cao thế
      // F: 'Chiều dài'
      // G: 'Nơi sản xuất'
      // H: 'Trạng thái sử dụng'
      // I:'Loại cáp'
      // J: Nhóm loại cáp
      // K: Lớp mạng cáp
      // L: Kiểu đi cáp
      // M: Trạm thiết bị (Nhập chính xác tên hoặc mã đơn vị)
      // N: Tổ kỹ thuật quản lý (Nhập chính xác tên hoặc mã đơn vị)
      // O: Ký hiệu kết cuối nguồn
      // P: Ký hiệu kết cuối đích
      // Q: Ký hiệu cáp gốc
      // R: Dự án (Nhập chính xác mã dự án)
      // S: Tài sản (Nhập chính xác mã tài sản)
      // T: Ngày sử dụng(dd/MM/yyyy)
      // U: Ngày lắp đặt(dd/MM/yyyy)
      // V: Ghi chú
      // W: Mã CSHT
      // X: Mã gói thầu IMS
      // Y: Mã hợp đồng IMS
      let msg = {};
      let ngayLapDat = null;
      let ngayLDField = this.CapFields.find((x) => x.name == "NGAY_LD");
      if (ngayLDField && ngayLDField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ngayLDField.positionColumn])) {
          let ngayld = dataItem[ngayLDField.positionColumn];
          if (typeof ngayld === "number") {
            ngayLapDat = new Date(Math.round((ngayld - 25569) * 86400 * 1000));
          } else {
            if (!this.validateDate(dataItem[ngayLDField.positionColumn])) {
              // msg += "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
            } else
              ngayLapDat = DateTimeLib.toDateObject(dataItem[ngayLDField.positionColumn]);
          }
        }
      }

      let ngaySuDung = null;
      let ngaySDField = this.CapFields.find((x) => x.name == "NGAY_SD");
      if (ngaySDField && ngaySDField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ngaySDField.positionColumn])) {
          let ngaysd = dataItem[ngaySDField.positionColumn];
          if (typeof ngaysd === "number") {
            ngaySuDung = new Date(Math.round((ngaysd - 25569) * 86400 * 1000));
          } else {
            if (!this.validateDate(dataItem[ngaySDField.positionColumn])) {
              // msg += "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
            } else
              ngaySuDung = DateTimeLib.toDateObject(dataItem[ngaySDField.positionColumn]);
          }
        }
      }

      let soDoiDay = 0;
      let soDoiDayField = this.CapFields.find((x) => x.name == "SODOIDAY");
      if (soDoiDayField && soDoiDayField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[soDoiDayField.positionColumn])) {
          if (!this.validateDouble(dataItem[soDoiDayField.positionColumn])) {
            // msg += "<div>Vui lòng nhập đúng định dạng số đôi dây</div>";
          } else soDoiDay = dataItem[soDoiDayField.positionColumn];
        }
      }

      let chieuDai = 0;
      let chieuDaiField = this.CapFields.find((x) => x.name == "CHIEUDAI");
      if (chieuDaiField && chieuDaiField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[chieuDaiField.positionColumn])) {
          if (!this.validateDouble(dataItem[chieuDaiField.positionColumn])) {
            // msg += "<div>Vui lòng nhập đúng định dạng chiều dài</div>";
          } else chieuDai = dataItem[chieuDaiField.positionColumn];
        }
      }

      let ttsdID = null;
      let ttsdField = this.CapFields.find((x) => x.name == "TTSD_ID");
      if (ttsdField && ttsdField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[ttsdField.positionColumn])) {
          let ck = this.dsTTSD.find(
            (x) => x.TTSD_ID == dataItem[ttsdField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
          } else ttsdID = ck.TTSD_ID;
        }
      }

      let noiSXID = null;
      let noiSXField = this.CapFields.find((x) => x.name == "NOISX_ID");
      if (noiSXField && noiSXField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[noiSXField.positionColumn])) {
          let ck = this.dsNoiSX.find(
            (x) => x.NOISX_ID == dataItem[noiSXField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy nơi sản xuất</div>";
          } else noiSXID = ck.NOISX_ID;
        }
      }

      let kichCoID = null;
      let kichCoField = this.CapFields.find((x) => x.name == "KICHCO_ID");
      if (kichCoField && kichCoField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[kichCoField.positionColumn])) {
          let ck = this.dsKichCoCap.find(
            (x) => x.KICHCO_ID == dataItem[kichCoField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy kích cỡ cáp</div>";
          } else kichCoID = ck.KICHCO_ID;
        }
      }

      let dienCTID = null;
      let dienCTField = this.CapFields.find((x) => x.name == "DIENCT_ID");
      if (dienCTField && dienCTField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[dienCTField.positionColumn])) {
          let ck = this.dsDienCT.find(
            (x) => x.DIENCT_ID == dataItem[dienCTField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy điện cao thế</div>";
          } else dienCTID = ck.DIENCT_ID;
        }
      }

      let loaiCapID = null;
      let loaiCapField = this.CapFields.find((x) => x.name == "LOAICAP_ID");
      if (loaiCapField && loaiCapField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[loaiCapField.positionColumn])) {
          let ck = this.dsLoaiCap.find(
            (x) => x.LOAICAP_ID == dataItem[loaiCapField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy loại cáp</div>";
          } else loaiCapID = ck.LOAICAP_ID;
        }
      }

      let kieuDiCapID = null;
      let kieuDiCapField = this.CapFields.find((x) => x.name == "KIEUDC_ID");
      if (kieuDiCapField && kieuDiCapField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[kieuDiCapField.positionColumn])) {
          let ck = this.dsKieuDC.find(
            (x) => x.KIEUDC_ID == dataItem[kieuDiCapField.positionColumn]
          );
          if (ck == null) {
            // msg += "<div>Không tìm thấy kiểu đi cáp</div>";
          } else kieuDiCapID = ck.KIEUDC_ID;
        }
      }

      let duAnID = null;
      let taiSanID = null;
      let taiSanText = null;
      let duanField = this.CapFields.find((x) => x.name == "DUAN_ID");
      let taisanField = this.CapFields.find((x) => x.name == "TAISAN_ID");
      if (
        duanField &&
        duanField.positionColumn &&
        taisanField &&
        taisanField.positionColumn
      ) {
        if (
          (!this.isNullOrEmpty(dataItem[duanField.positionColumn]) &&
            !this.isNullOrEmpty(dataItem[taisanField.positionColumn])) ||
          !this.isNullOrEmpty(dataItem[taisanField.positionColumn])
        ) {
          // Nếu tài sản-dự án đi 1 cặp hoặc chỉ có tài sản
          let key =
            dataItem[taisanField.positionColumn] +
            ":" +
            dataItem[duanField.positionColumn];
          // check map
          let vMap = this.dsTaiSanDuAnTheoMa.get(key);
          let v;
          if (!vMap) {
            v = await this.getTaiSanDuAnTheoMa(
              dataItem[taisanField.positionColumn],
              dataItem[duanField.positionColumn]
            );
            this.dsTaiSanDuAnTheoMa.set(key, v);
          } else {
            v = vMap;
          }
          if (v != null) {
            duAnID = v.DUAN_ID;
            taiSanID = v.TAISAN_ID;
            taiSanText = dataItem[taisanField.positionColumn];
          } else if (!this.isNullOrEmpty(dataItem[duanField.positionColumn])) {
            // Nếu chỉ có thông tin dự án
            let ck;
            let vMap = this.dsDuAnTheoMaCT.get(dataItem[duanField.positionColumn]);
            if (!vMap) {
              ck = await this.getDuAnTheoSoHieu(dataItem[duanField.positionColumn]);
              this.dsDuAnTheoMaCT.set(dataItem[duanField.positionColumn], ck);
              this.dsDuAnTheoDuAnId.set(ck.DUAN_ID, ck);
            } else {
              ck = vMap;
            }
            // let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem[duanField.positionColumn]);
            if (ck == null) {
              duAnID = null;
              taiSanID = null;
            } else {
              duAnID = ck.DUAN_ID;
              taiSanID = null;
            }
          } else {
            duAnID = null;
            taiSanID = null;
          }
        } else {
          taiSanID = null;
          if (duanField && duanField.positionColumn) {
            if (!this.isNullOrEmpty(dataItem[duanField.positionColumn])) {
              // Nếu chỉ có thông tin dự án
              let ck;
              let vMap = this.dsDuAnTheoMaCT.get(dataItem[duanField.positionColumn]);
              if (!vMap) {
                ck = await this.getDuAnTheoSoHieu(dataItem[duanField.positionColumn]);
                this.dsDuAnTheoMaCT.set(dataItem[duanField.positionColumn], ck);
                this.dsDuAnTheoDuAnId.set(ck.DUAN_ID, ck);
              } else {
                ck = vMap;
              }
              // let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem[duanField.positionColumn]);
              if (ck == null) {
                duAnID = null;
                // msg += "<div>Không tìm thấy dự án tương ứng</div>";
              } else duAnID = ck.DUAN_ID;
            } else {
              duAnID = null;
            }
          }
        }
      }

      // Gán lại trạm thiết bị
      let donVi = null;
      let tramtbId = null;
      let tramtbField = this.CapFields.find((x) => x.name == "DONVI_ID");
      if (tramtbField && tramtbField.positionColumn) {
        if (!this.isNullOrEmpty(dataItem[tramtbField.positionColumn])) {
          // check map
          let tramMap = this.dsTramTBTheoKyHieu.get(dataItem[tramtbField.positionColumn]);
          if (!tramMap) {
            let ck = await this.getTramThietBiTheoKyHieu(
              dataItem[tramtbField.positionColumn]
            );
            if (ck != null) {
              donVi = ck.TEN_DV;
              tramtbId = ck.DONVI_ID;
              this.dsTramTBTheoKyHieu.set(dataItem[tramtbField.positionColumn], ck);
            }
          } else {
            donVi = tramMap.TEN_DV;
            tramtbId = tramMap.DONVI_ID;
          }
        }
      }
      console.log("🚀 ~ file: index.vue:1290 ~ dataItem:", dataItem);
      let ketcuoiNField = this.CapFields.find((x) => x.name == "KETCUOI_N_ID");
      console.log("🚀 ~ file: index.vue:1288 ~ ketcuoiNField:", ketcuoiNField);
      let ketcuoiDField = this.CapFields.find((x) => x.name == "KETCUOI_D_ID");
      console.log("🚀 ~ file: index.vue:1290 ~ ketcuoiDField:", ketcuoiDField);
      let ketcuoi_n = dataItem[ketcuoiNField.positionColumn]; // ky hieu nguon
      let ketcuoi_d = dataItem[ketcuoiDField.positionColumn]; // ky hieu dich
      // handle ketcuoi_n_id / ketcuoi_d_id in validateCap

      let lopMCField = this.CapFields.find((x) => x.name == "LOPMC_ID");
      let lopMCID =
        lopMCField && lopMCField.positionColumn
          ? dataItem[lopMCField.positionColumn]
          : null;
      console.log("🚀 ~ file: index.vue:1290 ~ lopMCID:", lopMCID);
      console.log("🚀 ~ file: index.vue:1294 ~ this.dsLopMC:", this.dsLopMC);
      let lopMC = "";
      if (!this.isNullOrEmpty(lopMCID)) {
        let ck = this.dsLopMC.find((x) => x.LOPMC_ID == lopMCID);
        if (ck != null) {
          lopMC = ck.LOP_MC;
        }
      }

      let kyhieuField = this.CapFields.find((x) => x.name == "KYHIEU");
      let kyhieu =
        kyhieuField && kyhieuField.positionColumn
          ? dataItem[kyhieuField.positionColumn]
          : null;

      let tencapField = this.CapFields.find((x) => x.name == "TEN_CAP");
      let tencap =
        tencapField && tencapField.positionColumn
          ? dataItem[tencapField.positionColumn]
          : null;

      let kyhieuCapGocField = this.CapFields.find((x) => x.name == "CAPGOC_ID");
      let kyhieuCapGoc =
        kyhieuCapGocField && kyhieuCapGocField.positionColumn
          ? dataItem[kyhieuCapGocField.positionColumn]
          : null;

      let nhomlcField = this.CapFields.find((x) => x.name == "NHOMLC_ID");
      let nhomlc =
        nhomlcField && nhomlcField.positionColumn
          ? dataItem[nhomlcField.positionColumn]
          : null;

      let madvToktField = this.CapFields.find((x) => x.name == "TOQL_ID");
      let madvTokt =
        madvToktField && madvToktField.positionColumn
          ? dataItem[madvToktField.positionColumn]
          : null;

      let ghichuField = this.CapFields.find((x) => x.name == "GHICHU");
      let ghichu =
        ghichuField && ghichuField.positionColumn
          ? dataItem[ghichuField.positionColumn]
          : null;
      let soi_bdField = this.CapFields.find((x) => x.name == "SOI_BD");
      let soi_bd =
        soi_bdField && soi_bdField.positionColumn
          ? dataItem[soi_bdField.positionColumn]
          : null;

      let maCSHTField = this.KetCuoiFields.find((x) => x.name == "MA_CSHT");
      let maCSHT =
        maCSHTField && maCSHTField.positionColumn
          ? dataItem[maCSHTField.positionColumn]
          : null;

      let maGoiThauIMSField = this.KetCuoiFields.find((x) => x.name == "MA_GOITHAU_IMS");
      let maGoiThauIMS =
        maGoiThauIMSField && maGoiThauIMSField.positionColumn
          ? dataItem[maGoiThauIMSField.positionColumn]
          : null;

      let maHopDongIMSField = this.KetCuoiFields.find((x) => x.name == "MA_HOPDONG_IMS");
      let maHopDongIMS =
        maHopDongIMSField && maHopDongIMSField.positionColumn
          ? dataItem[maHopDongIMSField.positionColumn]
          : null;

      let result = {
        KYHIEU: kyhieu,
        TEN_CAP: tencap,
        KICHCO_ID: kichCoID,
        SODOIDAY: soDoiDay,
        DIENCT_ID: dienCTID,
        CHIEUDAI: chieuDai,
        NOISX_ID: noiSXID,
        LOAICAP_ID: loaiCapID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        TTSD_ID: ttsdID,
        CAPGOC_ID: null,
        KYHIEU_CAPGOC: kyhieuCapGoc,
        LOPMC: lopMC,
        LOPMC_ID: lopMCID,
        NHOMLC_ID: nhomlc != null ? parseInt(nhomlc, 10) : null,
        KIEUDC_ID: kieuDiCapID,
        DONVI: donVi, // tên trạm
        // TRAMTB_ID:tramtbId,
        DONVI_ID: tramtbId,
        MA_DV_TOQL: madvTokt, // mã / tên tổ quản lý
        KETCUOI_N_ID: null,
        KETCUOI_D_ID: null,
        KETCUOI_N: ketcuoi_n,
        KETCUOI_D: ketcuoi_d,
        TOQL_ID: null,
        KIEUCAP_ID: 0,
        DIENCAOTHE: dienCTID,
        GHICHU: ghichu,
        TAISAN_ID: taiSanID,
        TAISAN_TEXT: taiSanText,
        DUAN_ID: duAnID,
        CHUQUAN_ID: null,
        MA_CSHT: maCSHT,
        MA_GOITHAU_IMS: maGoiThauIMS,
        MA_HOPDONG_IMS: maHopDongIMS,
        SOI_BD: soi_bd,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "CAP",
        IS_EDIT: false,
      };
      console.log("🚀 ~ file: index.vue:1371 ~ result:", result);
      await this.validateCap(result);
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
    validateCap: async function (item) {
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
      it = this.dsDuAnTheoDuAnId.get(item.DUAN_ID);
      if (it) {
        item.DUAN_TEXT = it.MA_CT;
      }
      // it = this.dsDuAn.find((x) => x.DUAN_ID == item.DUAN_ID);
      // if (!(it == undefined || it == null)) {
      //   item.DUAN_TEXT = it.MA_CT;
      // }
      it = this.dsChuQuan.find((x) => x.CHUQUAN_ID == item.CHUQUAN_ID);
      if (!(it == undefined || it == null)) {
        item.CHUQUAN_TEXT = it.TENCHUQUAN;
      }
      // tramtb handled
      // it = this.dsToQuanLy.find((x) => x.DONVI_ID == item.DONVI_ID);
      // if (!(it == undefined || it == null)) {
      //   item.DONVI_TEXT = it.TEN_DV;
      // }

      let rs = true;
      if (this.isNullOrEmpty(item.KYHIEU)) {
        item.MESSAGE.kyHieu = "Ký hiệu không được để trống";
        rs = false;
      } else {
        await this.$root.context
          .post("/web-cabman/cap/layDsCapTheoKyHieus", { kyHieus: [item.KYHIEU] })
          .then((res) => {
            if (res.error === "200") {
              if (res.data.length > 0 && item.CAP_ID == null) {
                item.MESSAGE.kyHieu =
                  "Ký hiệu đã được sử dụng. Vui lòng chọn một ký hiệu khác.";
                rs = false;
              } else item.MESSAGE.kyHieu = "";
            }
          })
          .catch((e) => {
            throw e;
          });
      }
      if (this.isNullOrEmpty(item.TEN_CAP)) {
        item.MESSAGE.tenCap = "Tên cáp không được để trống";
        rs = false;
      } else item.MESSAGE.tenCap = "";
      if (this.isNullOrEmpty(item.LOAICAP_ID)) {
        item.MESSAGE.loaiCap = "Loại cáp không được để trống";
        rs = false;
      } else item.MESSAGE.loaiCap = "";
      // if (!rs) item.TRANGTHAI = "Lỗi dữ liệu";
      // else {
      //   if (item.ID == null) item.TRANGTHAI = "Sẵn sàng";
      //   else item.TRANGTHAI = "Đã lưu";
      // }
      // Gán lại cáp gốc
      if (!this.isNullOrEmpty(item.KYHIEU_CAPGOC)) {
        if (!item.LOPMC_ID) {
          item.CAPGOC_ID = null;
        } else {
          // check map
          let capGocMap = this.dsCapGocTheoKyHieu.get(item.KYHIEU_CAPGOC);
          let capGoc;
          if (!capGocMap) {
            capGoc = await this.getDsCapGoc(item.KYHIEU_CAPGOC);
            this.dsCapGocTheoKyHieu.set(item.KYHIEU_CAPGOC);
          } else {
            capGoc = capGocMap;
          }
          if (capGoc && capGoc.CAP_ID) {
            item.CAPGOC_ID = capGoc.CAP_ID;
          } else {
            item.CAPGOC_ID = null;
            item.MESSAGE.capgoc =
              "Cáp gốc không tồn tại. Kiểm tra lại ký hiệu cáp gốc trong file nhập vào";
            rs = false;
            // msg += "<div>Cáp gốc không tồn tại. Kiểm tra lại ký hiệu cáp gốc trong file nhập vào</div>";
          }
        }
      } else {
        item.CAPGOC_ID = null;
        item.MESSAGE.capgoc =
          "Chưa có thông tin cáp gốc. Hãy nhập ký hiệu cáp gốc vào cột dữ liệu trong file rồi thử lại";
        rs = false;
        // msg += "<div>Chưa có thông tin cáp gốc. Hãy nhập ký hiệu cáp gốc vào cột dữ liệu trong file rồi thử lại</div>";
      }

      // Gán lại kết cuối nguồn
      console.log("🚀 ~ file: index.vue:1489 ~ item:", item);
      console.log(
        "🚀 ~ file: index.vue:1490 ~ this.isNullOrEmpty(item.KETCUOI_N):",
        this.isNullOrEmpty(item.KETCUOI_N)
      );
      if (!this.isNullOrEmpty(item.KETCUOI_N)) {
        if (item.LOPMC_ID + "" == "1") {
          item.KETCUOI_N_ID = null;
        } else {
          let ketcuoi_n = await this.getKetCuoiTheoKyHieuVaPhanLoai(
            item.KETCUOI_N,
            this.isNullOrEmpty(item.NHOMLC_ID) ? 1 : item.NHOMLC_ID
          );
          if (ketcuoi_n) {
            item.KETCUOI_N_ID = ketcuoi_n.KETCUOI_ID;
          } else {
            item.KETCUOI_N_ID = null;
            item.MESSAGE.KETCUOI_N_ID =
              "Không tồn tại kết cuối nguồn với ký hiệu như trong file hoặc có tồn tại nhưng không phù hợp với nhóm loại cáp (quang hoặc đồng)";
          }
        }
      } else {
        if (item.LOPMC_ID + "" != "1") {
          item.MESSAGE.KETCUOI_N_ID =
            "Chưa có thông tin kết cuối nguồn. Hãy nhập ký hiệu kết cuối nguồn vào cột dữ liệu trong file rồi thử lại";
        }
      }

      // Gán lại kết cuối đích
      if (!this.isNullOrEmpty(item.KETCUOI_D)) {
        // if (item.LOPMC_ID + '' == '1') {
        //   item.KETCUOI_D_ID = null
        // } else {
        //   let ketcuoi_d = await this.getKetCuoiTheoKyHieuVaPhanLoai(item.KETCUOI_D, this.isNullOrEmpty(item.NHOMLC_ID) ? 1 : item.NHOMLC_ID)
        //   if (ketcuoi_d) {
        //     item.KETCUOI_D_ID = ketcuoi_d.KETCUOI_ID
        //   } else {
        //     item.KETCUOI_D_ID = null
        //     item.MESSAGE.KETCUOI_D_ID = 'Không tồn tại kết cuối đích với ký hiệu như trong file hoặc có tồn tại nhưng không phù hợp với nhóm loại cáp (quang hoặc đồng)'
        //   }
        // }
        let ketcuoi_d = await this.getKetCuoiTheoKyHieuVaPhanLoai(
          item.KETCUOI_D,
          this.isNullOrEmpty(item.NHOMLC_ID) ? 1 : item.NHOMLC_ID
        );
        if (ketcuoi_d) {
          item.KETCUOI_D_ID = ketcuoi_d.KETCUOI_ID;
        } else {
          item.KETCUOI_D_ID = null;
          item.MESSAGE.KETCUOI_D_ID =
            "Không tồn tại kết cuối đích với ký hiệu như trong file hoặc có tồn tại nhưng không phù hợp với nhóm loại cáp (quang hoặc đồng)";
        }
      } else {
        if (item.LOPMC_ID + "" != "1") {
          item.MESSAGE.KETCUOI_D_ID =
            "Chưa có thông tin kết cuối đích. Hãy nhập ký hiệu kết cuối đích vào cột dữ liệu trong file rồi thử lại";
        }
      }
      // //Gán lại tổ quản lý
      // if (this.isNullOrEmpty(item.DONVI_ID)){
      if (!this.isNullOrEmpty(item.MA_DV_TOQL)) {
        let toql = this.dsToQuanLy.find(
          (x) => x.TEN_DV == item.MA_DV_TOQL || x.MA_DV == item.MA_DV_TOQL
        );
        if (toql == null) {
          item.MESSAGE.donVi =
            "Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào";
          rs = false;
        } else {
          item.TOQL_ID = toql.DONVI_ID;
          item.TOQL_TEXT = toql.TEN_DV;
        }
      } else {
        item.MESSAGE.donVi =
          "Chưa có tổ quản lý. Hãy nhập vào mã hoặc tên tổ kỹ thuật quản lý cáp vào cột dữ liệu tương ứng trong file rồi thử lại";
        rs = false;
      }
      // }else item.MESSAGE.donVi = ""

      // Kiểm tra mess lỗi
      for (let key in item.MESSAGE) {
        if (item.MESSAGE[key] !== "" && key !== "isValid") {
          item.TRANGTHAI = item.MESSAGE[key];
          rs = false;
          break;
        }
      }
      // Kiểm tra TRANGTHAI
      // if (item.TRANGTHAI !== "Sẵn sàng" && item.TRANGTHAI !== "Đã lưu")
      //   rs = false
      item.MESSAGE.isValid = rs;
      // Kiểm tra TRANGTHAI
      if (rs) {
        if (item.CAP_ID == null) item.TRANGTHAI = "Sẵn sàng";
        else item.TRANGTHAI = "Đã lưu";
      }

      return rs;
    },
    getDonViTheoTen: async function (tenDonVi) {
      let rs = await this.$root.context.post("/web-cabman/cot-cong-be/getDonViTheoTen", {
        tenDonVi: tenDonVi,
      });
      return rs.data;
    },
    getDuAnTheoSoHieu: async function (soHieuCT) {
      let rs = await this.$root.context.post(
        "/web-cabman/cot-cong-be/getDuAnTheoSoHieu",
        {
          soHieuCT: soHieuCT,
        }
      );
      return rs.data;
    },
    getTaiSanDuAnTheoMa: async function (maTS, soHieuCT) {
      // maTS: mã tài sản
      // soHieuCT: số hiệu chứng từ dự án
      let rs = await this.$root.context.post(
        "/web-cabman/cot-cong-be/getTaiSanDuAnTheoMa",
        {
          maTS: maTS.toString().trim(),
          soHieuCT: soHieuCT.toString().trim(),
        }
      );
      return rs.data;
    },
    getDsDuAn: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDuAn");
      return rs.data;
    },
    getDsTaiSan: async function (duAnID) {
      if (duAnID != null) {
        let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsTaiSan", {
          id: duAnID,
        });
        return rs.data;
      } else return [];
    },
    // -----
    getDsPhanLoaiKC: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsPhanLoaiKC");
      return rs.data;
    },
    getDsLoaiKC: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiKC");
      return rs.data;
    },
    getDsCapSP: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsCapSP");
      return rs.data;
    },
    getDsKieuLap: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuLap");
      return rs.data;
    },
    getDsNoiSX: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsNoiSX");
      return rs.data;
    },
    getDsViTriLapDat: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsViTriLapDat");
      return rs.data;
    },
    getDsLoaiVo: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiVo");
      return rs.data;
    },
    getDsKichCoCap: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKichCoCap");
      return rs.data;
    },
    getDsDienCT: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDienCT");
      return rs.data;
    },
    getDsLoaiCap: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiCap");
      return rs.data;
    },
    getDsLopMC: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLopMC");
      return rs.data;
    },
    getDsNhomLC: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsNhomLC");
      return rs.data;
    },
    getDsKieuDC: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuDC");
      return rs.data;
    },
    getDsKieuCap: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsKieuCap");
      return rs.data;
    },
    getDsToaNha: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsToaNha");
      return rs.data;
    },
    getDsToQuanLy: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsToQuanLy");
      return rs.data;
    },
    getCotSoHieu: async function (id) {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layCotSoHieu", {
        id: id,
      });
      return rs.data;
    },
    getTramThietBi: async function (id) {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layTramThietBi", {
        id: id,
      });
      return rs.data;
    },
    getTramThietBiTheoKyHieu: async function (kyhieu) {
      let rs = await this.$root.context.get(
        "/web-cabman/danhmuc/layTramThietBiTheoKyHieu",
        {
          kyhieu: kyhieu,
        }
      );
      return rs.data;
    },
    // ----------
    getDsTrangThaiSuDung: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsTrangThaiSuDung");
      return rs.data;
    },
    getDsChuQuan: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsChuQuan");
      return rs.data;
    },
    getDsCapGoc: async function (id) {
      let rs = await this.$root.context.get("/web-cabman/cap/lay-id-theo-kyhieu", {
        kyhieu: id,
      });
      return rs.data;
    },
    getKetCuoiTheoKyHieu: async function (kyhieu) {
      let rs = await this.$root.context.post("/web-cabman/ketcuoi/lay-id-theo-kyhieu", {
        kyhieu_kc: kyhieu,
      });
      return rs.data;
    },
    getKetCuoiTheoKyHieuVaPhanLoai: async function (kyhieu, phanloaikc_id) {
      let rs = await this.$root.context.post(
        "/web-cabman/ketcuoi/lay-id-theo-kyhieu-va-phanloai",
        {
          kyhieu_kc: kyhieu,
          phanloaikc_id: phanloaikc_id,
        }
      );
      return rs.data;
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
      if (value == undefined || value == null) return null;
      else {
        let regEx = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
        return value.toString().match(regEx);
      }
    },
    validateDouble: function (value) {
      if (value == undefined || value == null) return null;
      else {
        let regEx = /^(?!0\d)\d*(\.\d+)?$/;
        return value.toString().match(regEx);
      }
    },
    isNullOrEmpty: function (value) {
      return value == undefined || value == null || value.toString().trim() == "";
    },
    getDsCapUpdate: async function (useID, danhSach) {
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
    },
    getDsKetCuoiUpdate: async function (useID, danhSach) {
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
        kyhieu: dataItem.KYHIEU,
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
        ten_cap: dataItem.TEN_CAP,
        toql_id: dataItem.TOQL_ID,
        trokhang: dataItem.TROKHANG,
        ttsd_id: dataItem.TTSD_ID,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
        soi: dataItem.SOI,
      };
      let rs = await this.$root.context.post("/web-cabman/cap/capnhat_v3", data);
      return rs.data;
    },
    capNhatDsKetCuoi: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachKetCuoi.length; i++) {
        if (this.danhSachKetCuoi[i].MESSAGE.isValid === true) {
          this.danhSachKetCuoi[i].TRANGTHAI = "Đang lưu...";
          let r = await this.capNhatKetCuoi(this.danhSachKetCuoi[i]);
          if (r != null) {
            count++;
            this.danhSachKetCuoi[i].TRANGTHAI = "Đã lưu";
          } else {
            this.danhSachKetCuoi[i].TRANGTHAI = "Lưu thất bại!";
          }
        }
      }
      return count;
    },
    capNhatKetCuoi: async function (dataItem) {
      let data = {
        ap_id: dataItem.AP_ID,
        becap_id: dataItem.BECAP_ID,
        cap_id: dataItem.CAP_ID,
        cap_sp: dataItem.CAP_SP,
        capgoc_id: dataItem.CAPGOC_ID,
        chuquan_id: dataItem.CHUQUAN_ID,
        cot_id: dataItem.COT_ID,
        dacdiem_id: dataItem.DACDIEM_ID,
        diachi: dataItem.DIACHI,
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
        kyhieu: dataItem.KYHIEU,
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
        pho_id: dataItem.PHO_ID,
        phuong_id: dataItem.PHUONG_ID,
        quan_id: dataItem.QUAN_ID,
        sonha: dataItem.SONHA,
        taisan_id: dataItem.TAISAN_ID,
        ten_kc: dataItem.TEN_KC,
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
      let rs = await this.$root.context.post("/web-cabman/ketcuoi/capnhat", data);
      return rs.data;
    },
    themDsCap: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachCap.length; i++) {
        // if (this.danhSachCap[i].MESSAGE.isValid === true) {
        if (this.danhSachCap[i].TRANGTHAI != "Sẵn sàng") continue;
        this.danhSachCap[i].TRANGTHAI = "Đang lưu...";
        try {
          let r = await this.themCap(this.danhSachCap[i]);
          if (r != null) {
            if (r.CAP_ID) {
              count++;
              this.danhSachCap[i].TRANGTHAI = "Đã lưu";
              this.danhSachCap[i].CAP_ID = r.CAP_ID;
              // this.danhSachCap[i].IS_EDIT = true
              // xóa msg error
              for (let key in this.danhSachCap[i].MESSAGE) {
                if (key !== "isValid") this.danhSachCap[i].MESSAGE[key] = "";
              }
            } else {
              this.danhSachCap[i].TRANGTHAI = "Lưu thất bại!";
              this.danhSachCap[i].MESSAGE.isValid = false;
            }
          } else {
            this.danhSachCap[i].TRANGTHAI = "Lưu thất bại!";
            this.danhSachCap[i].MESSAGE.isValid = false;
          }
        } catch (e) {
          if (e && e.response && e.response.data && e.response.data.message_detail) {
            this.danhSachCap[i].TRANGTHAI = e.response.data.message_detail;
            if (e.response.data.message_detail.includes("Ký hiệu đã được sử dụng")) {
              this.danhSachCap[i].MESSAGE.kyhieu = e.response.data.message_detail;
            }
            // this.danhSachCap[i].TRANGTHAI = e.response.data.message_detail
          } else {
            this.danhSachCap[i].TRANGTHAI =
              "Lưu thất bại!. " +
              JSON.stringify(e.response.data.message || {}) +
              JSON.stringify(e.response.data.data || {});
          }
          this.danhSachCap[i].MESSAGE.isValid = false;
        }
        // }
      }
      return count;
    },
    themCap: async function (dataItem) {
      // let capGocId = await this.getDsCapGoc(dataItem.CAPGOC_ID)
      // console.log('capGocId', capGocId)
      // if (capGocId == null){
      //   this.$root.toastError("Không tìm thấy cáp gốc id");
      //   return
      // }
      let data = {
        // cap_id: dataItem.CAP_ID,
        toql_id: dataItem.TOQL_ID,
        donvi_id: dataItem.DONVI_ID,
        capgoc_id: dataItem.CAPGOC_ID,
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
        kyhieu: dataItem.KYHIEU,
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
        ten_cap: dataItem.TEN_CAP,
        trokhang: dataItem.TROKHANG,
        ttsd_id: dataItem.TTSD_ID,
        ma_goithau_ims: dataItem.MA_GOITHAU_IMS,
        ma_hopdong_ims: dataItem.MA_HOPDONG_IMS,
        ketcuoi_n_id: dataItem.KETCUOI_N_ID,
        ketcuoi_d_id: dataItem.KETCUOI_D_ID,
      };
      let rs = await this.$root.context.post("/web-cabman/cap/themmoi_v3", data);
      if (rs.error_code != "BSS-00000000") {
        if (rs.message_detail != null) {
          this.$root.toastError(rs.message_detail);
        } else {
          this.$root.toastError(rs.message);
        }
      }
      return rs.data;
    },
    themDsKetCuoi: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachKetCuoi.length; i++) {
        // if (this.danhSachKetCuoi[i].MESSAGE.isValid === true) {
        if (this.danhSachKetCuoi[i].TRANGTHAI != "Sẵn sàng") continue;
        this.danhSachKetCuoi[i].TRANGTHAI = "Đang lưu...";
        try {
          let r = await this.themKetCuoi(this.danhSachKetCuoi[i]);
          // if (r.error_code != "BSS-00000000"){
          //   if (r.message_detail != null){
          //     this.$root.toastError(r.message_detail);
          //   }else {
          //     this.$root.toastError(r.message);
          //   }
          //   return null;
          // }
          if (r != null) {
            count++;
            this.danhSachKetCuoi[i].TRANGTHAI = "Đã lưu";
            this.danhSachKetCuoi[i].KETCUOI_ID = r.KETCUOI_ID;
            this.danhSachKetCuoi[i].DIACHI_ID = r.DIACHI_ID;
            // xóa msg error
            for (let key in this.danhSachKetCuoi[i].MESSAGE) {
              if (key !== "isValid") this.danhSachKetCuoi[i].MESSAGE[key] = "";
            }
          } else {
            this.danhSachKetCuoi[i].TRANGTHAI = "Lưu thất bại!";
          }
        } catch (e) {
          if (e && e.response && e.response.data && e.response.data.message_detail) {
            this.danhSachKetCuoi[i].TRANGTHAI = e.response.data.message_detail;
            if (
              e.response.data.message_detail.includes(
                "Phân loại kết cuối, Ký hiệu đã được sử dụng"
              )
            ) {
              this.danhSachKetCuoi[i].MESSAGE.kyHieu = e.response.data.message_detail;
            }
          } else {
            this.danhSachKetCuoi[i].TRANGTHAI =
              "Lưu thất bại!. " +
              JSON.stringify(e.response.data.message || {}) +
              JSON.stringify(e.response.data.data || {});
          }
          this.danhSachKetCuoi[i].MESSAGE.isValid = false;
        }
        // }
      }
      return count;
    },
    themKetCuoi: async function (dataItem) {
      // let capGocId = await this.getDsCapGoc(dataItem.CAPGOC_ID)
      // console.log('capGocId', capGocId)
      // if (capGocId == null){
      //   this.$root.toastError("Không tìm thấy cáp gốc id");
      //   return
      // }
      let data = {
        ap_id: dataItem.AP_ID,
        becap_id: dataItem.BECAP_ID,
        cap_id: dataItem.CAP_ID,
        cap_sp: dataItem.CAP_SP,
        capgoc_id: dataItem.CAPGOC_ID,
        chuquan_id: dataItem.CHUQUAN_ID,
        cot_id: dataItem.COT_ID,
        dacdiem_id: dataItem.DACDIEM_ID,
        diachi: dataItem.DIACHI,
        // diachi_id: dataItem.DIACHI_ID,
        donvi_id: dataItem.DONVI_ID,
        duan_id: dataItem.DUAN_ID,
        dungluong: dataItem.DUNGLUONG,
        ghichu: dataItem.GHICHU,
        ims_id: dataItem.IMS_ID,
        ketcuoi_cha_id: dataItem.KETCUOI_CHA_ID,
        // ketcuoi_id: dataItem.KETCUOI_ID,
        khu_id: dataItem.KHU_ID,
        kieulap_id: dataItem.KIEULAP_ID,
        kinhdo: dataItem.KINHDO,
        kyhieu: dataItem.KYHIEU,
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
        pho_id: dataItem.PHO_ID,
        phuong_id: dataItem.PHUONG_ID,
        quan_id: dataItem.QUAN_ID,
        sonha: dataItem.SONHA,
        taisan_id: dataItem.TAISAN_ID,
        ten_kc: dataItem.TEN_KC,
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
      let rs = await this.$root.context.post("/web-cabman/ketcuoi/themmoi_v3", data);
      if (rs.error_code != "BSS-00000000") {
        if (rs.message_detail != null) {
          this.$root.toastError(rs.message_detail);
        } else {
          this.$root.toastError(rs.message);
        }
      }
      return rs.data;
    },
    buttonReadFile_Click: async function () {
      this.dsExcelCap = [];
      this.dsExcelKetCuoi = [];
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
                switch (sheetName.toUpperCase()) {
                  case "CAP":
                    this.dsExcelCap = xlsx.utils.sheet_to_json(
                      workBook.Sheets[sheetName],
                      { header: "A" }
                    );
                    this.headerCap = { ...this.dsExcelCap[0] };
                    this.mappingFieldsColumn(this.headerCap, this.CapFields);
                    this.dsExcelCap.shift(); // remove first item
                    break;
                  case "KETCUOI":
                    this.dsExcelKetCuoi = [];
                    this.dsExcelKetCuoi = xlsx.utils.sheet_to_json(
                      workBook.Sheets[sheetName],
                      { header: "A" }
                    );
                    this.headerKetCuoi = { ...this.dsExcelKetCuoi[0] };
                    this.mappingFieldsColumn(this.headerKetCuoi, this.KetCuoiFields);
                    this.dsExcelKetCuoi.shift(); // remove first item
                    break;
                  default:
                    break;
                }
              }
            }
            this.$root.showLoading(true);
            try {
              [
                this.dsChuQuan,
                this.dsTTSD,
                this.dsPhanLoaiKC,
                this.dsLoaiKC,
                this.dsCapSP,
                this.dsKieuLap,
                this.dsNoiSX,
                this.dsViTriLapDat,
                this.dsLoaiVo,
                this.dsKichCoCap,
                this.dsDienCT,
                this.dsLoaiCap,
                this.dsLopMC,
                this.dsNhomLC,
                this.dsKieuDC,
                this.dsKieuCap,
                this.dsToaNha,
                this.dsToQuanLy,
                this.dsTramTB,
                // this.dsDuAn,
              ] = await Promise.all([
                this.getDsChuQuan(),
                this.getDsTrangThaiSuDung(),
                this.getDsPhanLoaiKC(),
                this.getDsLoaiKC(),
                this.getDsCapSP(),
                this.getDsKieuLap(),
                this.getDsNoiSX(),
                this.getDsViTriLapDat(),
                this.getDsLoaiVo(),
                this.getDsKichCoCap(),
                this.getDsDienCT(),
                this.getDsLoaiCap(),
                this.getDsLopMC(),
                this.getDsNhomLC(),
                this.getDsKieuDC(),
                this.getDsKieuCap(),
                this.getDsToaNha(),
                this.getDsToQuanLy(),
                this.getTramThietBi(),
                // this.getDsDuAn()
              ]);
              // if (this.dsChuQuan == null)
              //   this.dsChuQuan = await this.getDsChuQuan();
              // if (this.dsTTSD == null)
              //   this.dsTTSD = await this.getDsTrangThaiSuDung();
              // if (this.dsPhanLoaiKC == null)
              //   this.dsPhanLoaiKC = await this.getDsPhanLoaiKC();
              // if (this.dsLoaiKC == null)
              //   this.dsLoaiKC = await this.getDsLoaiKC();
              // if (this.dsCapSP == null) this.dsCapSP = await this.getDsCapSP();
              // if (this.dsKieuLap == null)
              //   this.dsKieuLap = await this.getDsKieuLap();
              // if (this.dsNoiSX == null) this.dsNoiSX = await this.getDsNoiSX();
              // if (this.dsViTriLapDat == null)
              //   this.dsViTriLapDat = await this.getDsViTriLapDat();
              // if (this.dsLoaiVo == null)
              //   this.dsLoaiVo = await this.getDsLoaiVo();
              // if (this.dsKichCoCap == null)
              //   this.dsKichCoCap = await this.getDsKichCoCap();
              // if (this.dsDienCT == null)
              //   this.dsDienCT = await this.getDsDienCT();
              // if (this.dsLoaiCap == null)
              //   this.dsLoaiCap = await this.getDsLoaiCap();
              // if (this.dsLopMC == null) this.dsLopMC = await this.getDsLopMC();
              // if (this.dsNhomLC == null)
              //   this.dsNhomLC = await this.getDsNhomLC();
              // if (this.dsKieuDC == null)
              //   this.dsKieuDC = await this.getDsKieuDC();
              // if (this.dsKieuCap == null)
              //   this.dsKieuCap = await this.getDsKieuCap();
              // if (this.dsToaNha == null)
              //   this.dsToaNha = await this.getDsToaNha();
              // if (this.dsToQuanLy == null)
              //   this.dsToQuanLy = await this.getDsToQuanLy();
              // if (this.dsTramTB == null)
              //   this.dsTramTB = await this.getTramThietBi()
              // if (this.dsDuAn == null) this.dsDuAn = await this.getDsDuAn();
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
    mappingFieldsColumn(header, fields) {
      var positionColumns = Object.keys(header);
      for (let i of positionColumns) {
        let columnText = header[i];
        var Field = fields.find(
          (x) =>
            x.name.toUpperCase() == columnText.toUpperCase() ||
            x.text.toUpperCase() == columnText.toUpperCase()
        );
        if (Field) {
          Field.positionColumn = i;
        }
      }
    },
    buttonExportFile_Click: function () {
      let cked = $('input[name="radioDoiTuong"]:checked').val();
      let r = false;
      // let workBook = xlsx.utils.book_new();
      if (cked == "KETCUOI") {
        if (this.danhSachKetCuoi.length > 0) {
          let dsKC = [...this.danhSachKetCuoi];
          dsKC.forEach((item) => {
            item.NGAY_LD = DateTimeLib.toDateDisplay(item.NGAY_LD);
            item.NGAY_SD = DateTimeLib.toDateDisplay(item.NGAY_SD);
          });
          let exportProperties = {
            dataSource: dsKC,
          };
          this.$refs.gridKetCuoi.excelExport(exportProperties);

          r = true;
          // let sheet = xlsx.utils.json_to_sheet(this.danhSachKetCuoi);
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
          //     "chuquan_id": ketCuoi.CHUQUAN_ID,
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
          let dsCap = [...this.danhSachCap];
          dsCap.forEach((item) => {
            item.NGAY_LD = DateTimeLib.toDateDisplay(item.NGAY_LD);
            item.NGAY_SD = DateTimeLib.toDateDisplay(item.NGAY_SD);
          });

          let exportProperties = {
            dataSource: dsCap,
          };
          this.$refs.gridCap.excelExport(exportProperties);

          r = true;
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
          //     "Chủ quản": cap.CHUQUAN_ID,
          //   });
          // });
          // //let sheet = xlsx.utils.json_to_sheet(this.danhSachCap);
          // let sheet = xlsx.utils.json_to_sheet(dataExcel);
          // xlsx.utils.book_append_sheet(workBook, sheet, "CAP");
        }
      }
      // if (r) xlsx.writeFile(workBook, "du-lieu.xlsx");
      if (!r) {
        this.$root.toastError("Không có dữ liệu để xuất file");
      }
    },
    buttonExecute_Click: async function () {
      this.$root.showLoading(true);
      try {
        let count = 0;
        let total = 0;
        let boqua = 0;
        if (this.doiTuongCap == "CAP") {
          total = this.danhSachCap.length;
          count = await this.themDsCap();
          await this.$refs.gridCap.refresh();
          this.removeCellFrozen();
        } else if (this.doiTuongCap == "KETCUOI") {
          total = this.danhSachKetCuoi.length;
          count = await this.themDsKetCuoi();
          await this.$refs.gridKetCuoi.refresh();
          this.removeCellFrozen();
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
        if (this.currentData.DACDIEM_ID == 0) {
          this.currentData.DACDIEM_ID = null;
        }
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
      } else if (dataItem.ITEM_TYPE == "KETCUOI") {
        let item = this.danhSachKetCuoi.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
        this.validateKetCuoi(item);
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
    checkAddressKetCuoi: async function () {
      const data = {
        danhSach: this.dsExcelKetCuoi.map((item, index) => {
          return {
            KYHIEU: item.A,
            DIACHI: {
              PHO: item.AC ?? null,
              PHUONG: item.AD ?? null,
              KHU: item.AA ?? null,
              AP: item.AB ?? null,
              QUAN: item.AE ?? null,
            },
          };
        }),
      };
      const rs = await this.$root.context.post(
        "/web-cabman/ketcuoi/kiemTraDiaChiKetCuoiTheoKyHieu",
        data
      );
      this.dsAddressCheck = rs.data;
      return rs;
    },
    getTinh: async function () {
      try {
        this.$root.showLoading(true);
        var rs = await this.$root.context.get(
          "/web-cabman/bando_tuyencot/lay_thongtin_tinh"
        );
        this.tenTinh = rs.data.TENTINH;
      } catch (error) {
        console.log(error);
      } finally {
        this.$root.showLoading(false);
      }
    },
  },
  updated: debounce(function () {
    this.$nextTick(() => {
      console.log("updated"); // runs only once
      setTimeout(() => {
        $(".e-movablecontent").children().children().find("#trangthai").remove();
      }, 200);
    });
  }, 250), // increase to ur needs
  created: async function () {
    this.getTinh();
  },
};
</script>
