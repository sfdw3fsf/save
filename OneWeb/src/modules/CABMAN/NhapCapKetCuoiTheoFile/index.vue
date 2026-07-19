<template src="./index.template.html"></template>
<script>
import xlsx from "xlsx";
import DateTimeLib from "../../../utils/DateTimeLib";
import { Sort, Filter } from "@syncfusion/ej2-vue-grids";
import FormDiaChi from "../nhap-cot-doancong-becap-theofile/DiaChi/FormDiaChi.vue";
import RowCapTemplate from "./RowTemplate/cap.vue";
import RowKetCuoiTemplate from "./RowTemplate/ketcuoi.vue";
import FormToaDo from "../nhap-cot-doancong-becap-theofile/ToaDo/FormToaDo.vue";
export default {
  provide: {
    grid: [Sort, Filter],
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

      dsChuQuan: null,
      dsTTSD: null,
      dsDuAn: null,

      dsExcelCap: [],
      danhSachCap: [],
      dsExcelKetCuoi: [],
      danhSachKetCuoi: [],
      fileExcel: null,
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
      currentData: null,
      doiTuongCap: 'CAP'
    };
  },
  computed: {
    enabledThucThi: function () {
      if (this.doiTuongCap === 'CAP') {
        if (!(this.danhSachCap === null || this.danhSachCap.length === 0)) {
          if (this.danhSachCap.length > 0) {
            return (
              this.danhSachCap[0].ID == null ||
              this.danhSachCap[0].ID === undefined ||
              this.danhSachCap[0].ID === 0
            )
          }
        }
        return false
      }
      if (this.doiTuongCap === 'KETCUOI') {
        if (!(this.danhSachKetCuoi == null || this.danhSachKetCuoi.length === 0)) {
          if (this.danhSachKetCuoi.length > 0) {
            return (
              this.danhSachKetCuoi[0].ID == null ||
              this.danhSachKetCuoi[0].ID === undefined ||
              this.danhSachKetCuoi[0].ID === 0
            )
          }
        }
        return false
      }
    },
  },
  watch: {
    fileExcel: async function (value) {
      await this.buttonReadFile_Click()
    },
  },
  methods: {
    linkTaiFileMau_Click: function () {
      window.location.href = "/static/files/MauFile_Import_KC_Cap_V2.xlsx";
    },
    convertToDsKetCuoiItems: async function () {
      let r = [];
      if (this.dsExcelKetCuoi != null) {
        let dsID_EXCEL = [];
        let dsSO_HIEU_EXCEL = [];
        for (let j = 0; j < this.dsExcelKetCuoi.length; j++) {
          const ketCuoi = this.dsExcelKetCuoi[j];
          // if (
          //   !(this.isNullOrEmpty(ketCuoi.A) || dsID_EXCEL.includes(ketCuoi.A))
          // ) {
          //   dsID_EXCEL.push(ketCuoi.A);
          // }
          if (
            !(
              this.isNullOrEmpty(ketCuoi.B) ||
              dsSO_HIEU_EXCEL.includes(ketCuoi.B)
            )
          ) {
            dsSO_HIEU_EXCEL.push(ketCuoi.B);
          }
        }
        if (
          dsID_EXCEL.length == this.dsExcelKetCuoi.length ||
          dsSO_HIEU_EXCEL.length == this.dsExcelKetCuoi.length
        ) {
          // let useID = dsID_EXCEL.length == this.dsExcelKetCuoi.length;
          // let dsItemUpdate = await this.getDsKetCuoiUpdate(
          //   useID,
          //   useID ? dsID_EXCEL : dsSO_HIEU_EXCEL
          // );

          for (let i = 0; i < this.dsExcelKetCuoi.length; i++) {
            // let currentItem = null;
            // if (useID)
            //   currentItem = dsItemUpdate.find(
            //     (x) => x.KETCUOI_ID == this.dsExcelKetCuoi[i].A
            //   );
            // else
            //   currentItem = dsItemUpdate.find(
            //     (x) => x.KYHIEU == this.dsExcelKetCuoi[i].B
            //   );

            let v = await this.convertToKetCuoiItem(
              this.dsExcelKetCuoi[i]
              // currentItem
            );
            v.STT = i + 1;
            r.push(v);
          }
        } else {
          this.$root.toastError(
            "Trường cột id hoặc số hiệu cột đang bị thiếu dữ liệu. \nVui lòng bổ sung và thử lại."
          );
        }
      }
      return r;
    },
    convertToKetCuoiItem: async function (dataItem) {
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
      let msg = "";
      let dungLuong = 0;
      if (!this.isNullOrEmpty(dataItem.F)) {
        if (!this.validateDouble(dataItem.F)) {
          msg += "<div>Vui lòng nhập đúng định dạng dung lượng (số thực)</div>";
        } else dungLuong = dataItem.F;
      }
      // else {
      //   dungLuong = currentItem.DUNGLUONG;
      // }
      let kinhDo = 0;
      if (!this.isNullOrEmpty(dataItem.N)) {
        if (!this.validateDouble(dataItem.N)) {
          msg += "<div>Vui lòng nhập đúng định dạng kinh độ (số thực)</div>";
        } else kinhDo = dataItem.N;
      }
      // else {
      //   kinhDo = currentItem.KINHDO;
      // }
      let viDo = 0;
      if (!this.isNullOrEmpty(dataItem.O)) {
        if (!this.validateDouble(dataItem.O)) {
          msg += "<div>Vui lòng nhập đúng định dạng vĩ độ (số thực)</div>";
        } else viDo = dataItem.O;
      }
      // else {
      //   viDo = currentItem.VIDO;
      // }
      let ngayLapDat = null;
      if (!this.isNullOrEmpty(dataItem.T)) {
        if (!this.validateDate(dataItem.T)) {
          msg +=
            "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
        } else ngayLapDat = DateTimeLib.toDateObject(dataItem.T);
      }
      // else {
      //   ngayLapDat = DateTimeLib.toDateTimeObjectWithFormat(
      //     currentItem.NGAY_LD,
      //     "YYYY-MM-DD HH:mm:ss.S"
      //   );
      // }
      let ngaySuDung = null;
      if (!this.isNullOrEmpty(dataItem.U)) {
        if (!this.validateDate(dataItem.U)) {
          msg +=
            "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
        } else ngaySuDung = DateTimeLib.toDateObject(dataItem.S);
      }
      // else {
      //   ngaySuDung = DateTimeLib.toDateTimeObjectWithFormat(
      //     currentItem.NGAY_SD,
      //     "YYYY-MM-DD HH:mm:ss.S"
      //   );
      // }
      let donViID = null;
      if (!this.isNullOrEmpty(dataItem.O)) {
        let ck = this.dsToQuanLy.find(
          (x) => x.TEN_DV == dataItem.O || x.MA_DV == dataItem.O
        );
        if (ck == null) {
          msg +=
            "<div>Không tìm thấy tổ kỹ thuật có mã hoặc tên như trong file nhập vào</div>";
        } else donViID = ck.DONVI_ID;
      }
      // else {
      //   donViID = currentItem.DONVI_ID;
      // }
      let duAnID = null;
      let taiSanID = null;
      if (!this.isNullOrEmpty(dataItem.P) && !this.isNullOrEmpty(dataItem.Q)) {
        let v = await this.getTaiSanDuAnTheoMa(dataItem.Q, dataItem.P);
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
        } else {
          msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
        }
      }
      // else {
      //   taiSanID = currentItem.TAISAN_ID;
      //   if (!this.isNullOrEmpty(dataItem.P)) {
      //     let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem.P);
      //     if (ck == null) {
      //       msg += "<div>Không tìm thấy dự án tương ứng</div>";
      //     } else duAnID = ck.DUAN_ID;
      //   } else {
      //     duAnID = currentItem.DUAN_ID;
      //   }
      // }
      let capSP = null;
      if (!this.isNullOrEmpty(dataItem.E)) {
        let ck = this.dsCapSP.find((x) => x.CAPSP_ID == dataItem.E);
        if (ck == null) {
          msg += "<div>Không tìm thấy cấp Splitter</div>";
        } else capSP = ck.CAPSP_ID;
      }
      // else {
      //   capSP = currentItem.CAPSP_ID;
      // }
      let ttsdID = null;
      if (!this.isNullOrEmpty(dataItem.G)) {
        let ck = this.dsTTSD.find((x) => x.TTSD_ID == dataItem.G);
        if (ck == null) {
          msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
        } else ttsdID = ck.TTSD_ID;
      }
      // else {
      //   ttsdID = currentItem.TTSD_ID;
      // }
      let kieuLapID = null;
      if (!this.isNullOrEmpty(dataItem.H)) {
        let ck = this.dsKieuLap.find((x) => x.KIEULAP_ID == dataItem.H);
        if (ck == null) {
          msg += "<div>Không tìm thấy kiểu lắp</div>";
        } else kieuLapID = ck.KIEULAP_ID;
      }
      // else {
      //   kieuLapID = currentItem.KIEULAP_ID;
      // }
      let noiSXID = null;
      if (!this.isNullOrEmpty(dataItem.I)) {
        let ck = this.dsNoiSX.find((x) => x.NOISX_ID == dataItem.I);
        if (ck == null) {
          msg += "<div>Không tìm thấy nơi sản xuất</div>";
        } else noiSXID = ck.NOISX_ID;
      }
      // else {
      //   noiSXID = currentItem.NOISX_ID;
      // }
      let viTriID = null;
      if (!this.isNullOrEmpty(dataItem.J)) {
        viTriID = dataItem.J;
        let ck = this.dsViTriLapDat.find((x) => x.VITRI_ID == dataItem.J);
        if (ck == null) {
          msg += "<div>Không tìm thấy vị trí</div>";
        } else viTriID = ck.VITRI_ID;
      }
      // else {
      //   viTriID = currentItem.VITRI_ID;
      // }
      let chuQuanID = null;
      if (!this.isNullOrEmpty(dataItem.K)) {
        let ck = this.dsChuQuan.find((x) => x.CHUQUAN_ID == dataItem.K);
        if (ck == null) {
          msg += "<div>Không tìm thấy chủ quản</div>";
        } else chuQuanID = ck.CHUQUAN_ID;
      }
      // else {
      //   chuQuanID = currentItem.CHUQUAN_ID;
      // }
      let toaNhaID = null;
      if (!this.isNullOrEmpty(dataItem.R)) {
        let ck = this.dsToaNha.find((x) => x.MA_TOANHA == dataItem.R);
        if (ck == null) {
          msg += "<div>Không tìm thấy tòa nhà</div>";
        } else toaNhaID = ck.TOANHA_ID;
      }
      // else {
      //   toaNhaID = currentItem.TOANHA_ID;
      // }
      let phanLoaiKCID = dataItem.L;
      let phanLoaiKC = "";
      if (!this.isNullOrEmpty(phanLoaiKCID)) {
        let ck = this.dsPhanLoaiKC.find((x) => x.PHANLOAIKC_ID == dataItem.L);
        if (ck != null) {
          phanLoaiKC = ck.PHANLOAI_KC;
        }
      }
      let v = await this.getTaiSanDuAnTheoMa(dataItem.Q, dataItem.P);

      console.log('dataItemKetCuoi', dataItem)
      let result = {
        // AP_ID: currentItem.AP_ID,
        // BECAP_ID: currentItem.BECAP_ID,
        // CAP_ID: currentItem.CAP_ID,
        CAP_SP: capSP,
        CAPGOC_ID: dataItem.D,
        CHUQUAN_ID: chuQuanID,
        // COT_ID: currentItem.COT_ID,
        // DACDIEM_ID: currentItem.DACDIEM_ID,
        DIACHI: dataItem.E,
          // ? currentItem.DIACHI
          // : dataItem.F,
        // DIACHI_ID: currentItem.DIACHI_ID,
        DONVI_ID: donViID,
        DUAN_ID: duAnID,
        // DUNGLUONG: dungLuong,
        GHICHU: dataItem.V,
          // ? currentItem.GHICHU
          // : dataItem.U,
        // IMS_ID: currentItem.IMS_ID,
        // KETCUOI_CHA_ID: currentItem.KETCUOI_CHA_ID,
        // KETCUOI_ID: currentItem.KETCUOI_ID,
        // KHU_ID: currentItem.KHU_ID,
        KIEULAP_ID: kieuLapID,
        KINHDO: kinhDo,
        KYHIEU: dataItem.B,
          // ? currentItem.KYHIEU
          // : dataItem.B,
        LOAIKC_ID: dataItem.M,
        LOAIVO_ID: dataItem.L,
          // ? currentItem.LOAIVO_ID
          // : dataItem.L,
        // MA_CSHT: currentItem.MA_CSHT,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        // NGUONDICH: currentItem.NGUONDICH,
        NOISX_ID: noiSXID,
        PHANLOAIKC: phanLoaiKC,
        PHANLOAIKC_ID: phanLoaiKCID,
        // PHO_ID: currentItem.PHO_ID,
        // PHUONG_ID: currentItem.PHUONG_ID,
        // QUAN_ID: currentItem.QUAN_ID,
        // SONHA: currentItem.SONHA,
        TAISAN_ID: taiSanID,
        TEN_KC: dataItem.B,
          // ? currentItem.TEN_KC
          // : dataItem.C,
        // TINH_ID: currentItem.TINH_ID,
        TOANHA_ID: toaNhaID,
        TONGSO_DD: dungLuong,
        // TRAMTBI_ID: currentItem.TRAMTBI_ID,
        TTSD_ID: ttsdID,
        VIDO: viDo,
        VITRI_ID: viTriID,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "KETCUOI",
        IS_EDIT: true,
      };
      return result;
    },
    convertToDsCapItems: async function () {
      let r = [];
      if (this.dsExcelCap != null) {
        let dsID_EXCEL = [];
        let dsSO_HIEU_EXCEL = [];
        for (let j = 0; j < this.dsExcelCap.length; j++) {
          const cap = this.dsExcelCap[j];
          // if (!(this.isNullOrEmpty(cap.A) || dsID_EXCEL.includes(cap.A))) {
          //   dsID_EXCEL.push(cap.A);
          // }
          if (!(this.isNullOrEmpty(cap.B) || dsSO_HIEU_EXCEL.includes(cap.B))) {
            dsSO_HIEU_EXCEL.push(cap.B);
          }
        }
        if (
          dsID_EXCEL.length == this.dsExcelCap.length ||
          dsSO_HIEU_EXCEL.length == this.dsExcelCap.length
        ) {
          // let useID = dsID_EXCEL.length == this.dsExcelCap.length;
          // let dsItemUpdate = await this.getDsCapUpdate(
          //   useID,
          //   useID ? dsID_EXCEL : dsSO_HIEU_EXCEL
          // );
          for (let i = 0; i < this.dsExcelCap.length; i++) {
            // let currentItem = null;
            // if (useID)
            //   currentItem = dsItemUpdate.find(
            //     (x) => x.CAP_ID == this.dsExcelCap[i].A
            //   );
            // else
            //   currentItem = dsItemUpdate.find(
            //     (x) => x.KYHIEU == this.dsExcelCap[i].B
            //   );
            let v = await this.convertToCapItem(
              this.dsExcelCap[i],
              // currentItem
            );
            v.STT = i + 1;
            r.push(v);
          }
        } else {
          this.$root.toastError(
            "Trường cống id hoặc ký hiệu cáp đang bị thiếu dữ liệu. \nVui lòng bổ sung và thử lại."
          );
        }
      }
      return r;
    },
    convertToCapItem: async function (dataItem) {
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
      let msg = "";
      let ngayLapDat = null;
      if (!this.isNullOrEmpty(dataItem.U)) {
        if (!this.validateDate(dataItem.U)) {
          msg +=
            "<div>Vui lòng nhập đúng định dạng ngày lắp đặt (dd/MM/yyyy)</div>";
        } else ngayLapDat = DateTimeLib.toDateObject(dataItem.U);
      }
      // else {
      //   ngayLapDat = DateTimeLib.toDateTimeObjectWithFormat(
      //     currentItem.NGAY_LD,
      //     "YYYY-MM-DD HH:mm:ss.S"
      //   );
      // }
      let ngaySuDung = null;
      if (!this.isNullOrEmpty(dataItem.T)) {
        if (!this.validateDate(dataItem.T)) {
          msg +=
            "<div>Vui lòng nhập đúng định dạng ngày sử dụng (dd/MM/yyyy)</div>";
        } else ngaySuDung = DateTimeLib.toDateObject(dataItem.Y);
      }
      // else {
      //   ngaySuDung = DateTimeLib.toDateTimeObjectWithFormat(
      //     currentItem.NGAY_SD,
      //     "YYYY-MM-DD HH:mm:ss.S"
      //   );
      // }
      let soDoiDay = 0;
      if (!this.isNullOrEmpty(dataItem.D)) {
        if (!this.validateDouble(dataItem.D)) {
          msg += "<div>Vui lòng nhập đúng định dạng số đôi dây</div>";
        } else soDoiDay = dataItem.D;
      }
      // else {
      //   soDoiDay = currentItem.SODOIDAY;
      // }
      let chieuDai = 0;
      if (!this.isNullOrEmpty(dataItem.F)) {
        if (!this.validateDouble(dataItem.F)) {
          msg += "<div>Vui lòng nhập đúng định dạng chiều dài</div>";
        } else chieuDai = dataItem.F;
      }
      // else {
      //   chieuDai = currentItem.CHIEUDAI;
      // }
      let ttsdID = null;
      if (!this.isNullOrEmpty(dataItem.H)) {
        let ck = this.dsTTSD.find((x) => x.TTSD_ID == dataItem.H);
        if (ck == null) {
          msg += "<div>Không tìm thấy trạng thái sử dụng</div>";
        } else ttsdID = ck.TTSD_ID;
      }
      // else {
      //   ttsdID = currentItem.TTSD_ID;
      // }
      let chuQuanID = null;
      if (!this.isNullOrEmpty(dataItem.Q)) {
        let ck = this.dsChuQuan.find((x) => x.CHUQUAN_ID == dataItem.Q);
        if (ck == null) {
          msg += "<div>Không tìm thấy chủ quản</div>";
        } else chuQuanID = ck.CHUQUAN_ID;
      }
      // else {
      //   chuQuanID = currentItem.CHUQUAN_ID;
      // }
      let noiSXID = null;
      if (!this.isNullOrEmpty(dataItem.G)) {
        let ck = this.dsNoiSX.find((x) => x.NOISX_ID == dataItem.G);
        if (ck == null) {
          msg += "<div>Không tìm thấy nơi sản xuất</div>";
        } else noiSXID = ck.NOISX_ID;
      }
      // else {
      //   noiSXID = currentItem.NOISX_ID;
      // }
      let kichCoID = null;
      if (!this.isNullOrEmpty(dataItem.C)) {
        let ck = this.dsKichCoCap.find((x) => x.KICHCO_ID == dataItem.C);
        if (ck == null) {
          msg += "<div>Không tìm thấy kích cỡ cáp</div>";
        } else kichCoID = ck.KICHCO_ID;
      }
      // else {
      //   kichCoID = currentItem.KICHCO_ID;
      // }
      let dienCTID = null;
      if (!this.isNullOrEmpty(dataItem.E)) {
        let ck = this.dsDienCT.find((x) => x.DIENCT_ID == dataItem.E);
        if (ck == null) {
          msg += "<div>Không tìm thấy điện cao thế</div>";
        } else dienCTID = ck.DIENCT_ID;
      }
      // else {
      //   dienCTID = currentItem.DIENCT_ID;
      // }
      let loaiCapID = null;
      if (!this.isNullOrEmpty(dataItem.I)) {
        let ck = this.dsLoaiCap.find((x) => x.LOAICAP_ID == dataItem.I);
        if (ck == null) {
          msg += "<div>Không tìm thấy loại cáp</div>";
        } else loaiCapID = ck.LOAICAP_ID;
      }
      // else {
      //   loaiCapID = currentItem.LOAICAP_ID;
      // }
      let kieuDiCapID = null;
      if (!this.isNullOrEmpty(dataItem.L)) {
        let ck = this.dsKieuDC.find((x) => x.KIEUDC_ID == dataItem.L);
        if (ck == null) {
          msg += "<div>Không tìm thấy kiểu đi cáp</div>";
        } else kieuDiCapID = ck.KIEUDC_ID;
      }
      // else {
      //   kieuDiCapID = currentItem.KIEUDC_ID;
      // }
      let duAnID = null;
      let taiSanID = null;
      if (!this.isNullOrEmpty(dataItem.R) && !this.isNullOrEmpty(dataItem.S)) {
        let v = await this.getTaiSanDuAnTheoMa(dataItem.R, dataItem.S);
        if (v != null) {
          duAnID = v.DUAN_ID;
          taiSanID = v.TAISAN_ID;
        } else {
          msg += "<div>Không tìm thấy tài sản và dự án tương ứng</div>";
        }
      }
      // else {
      //   taiSanID = currentItem.TAISAN_ID;
      //   if (!this.isNullOrEmpty(dataItem.L)) {
      //     let ck = this.dsDuAn.find((x) => x.SOHIEU_CT == dataItem.L);
      //     if (ck == null) {
      //       duAnID = null;
      //       msg += "<div>Không tìm thấy dự án tương ứng</div>";
      //     } else duAnID = ck.DUAN_ID;
      //   } else {
      //     duAnID = currentItem.DUAN_ID;
      //   }
      // }
      // let donViID = currentItem.DONVI_ID;
      // let donVi = "";
      // if (!this.isNullOrEmpty(donViID)) {
      //   let v = await this.getTramThietBi(donViID);
      //   if (v != null) {
      //     donVi = v.TEN_DV;
      //   }
      // }
      let lopMCID = dataItem.LOPMC_ID;
      let lopMC = "";
      // if (!this.isNullOrEmpty(lopMCID)) {
        let ck = this.dsLopMC.find((x) => x.LOPMC_ID == dataItem.K);
        if (ck != null) {
          lopMC = ck.LOP_MC;
        }
      let v = await this.getTaiSanDuAnTheoMa(dataItem.Q, dataItem.P);
      // }
      console.log('dataItemCap', dataItem)
      let result = {
        // CAP_ID: currentItem.CAP_ID,
        // MA_CAP: currentItem.MA_CAP,
        CAPGOC_ID: dataItem.Q,
        KYHIEU: dataItem.A,
          // ? currentItem.KYHIEU
          // : dataItem.B,
        TEN_CAP: dataItem.B,
          // ? currentItem.TEN_CAP
          // : dataItem.C,
        KICHCO_ID: kichCoID,
        // KC_COT: currentItem.KC_COT,
        // TROKHANG: currentItem.TROKHANG,                  //tự nhập
        // SUYHAO: currentItem.SUYHAO,                      //tự nhập
        SODOIDAY: soDoiDay,
        // SODOI_SD: currentItem.SODOI_SD,
        // SO_DH: currentItem.SO_DH,
        // SO_DC: currentItem.SO_DC,
        // VITRI_COT: currentItem.VITRI_COT,
        // VITRI_DT: currentItem.VITRI_DT,
        DIENCT_ID: dienCTID,
        CHIEUDAI: chieuDai,
        // TUYENCAP_ID: currentItem.TUYENCAP_ID,
        NOISX_ID: noiSXID,
        LOAICAP_ID: loaiCapID,
        NGAY_LD: ngayLapDat,
        NGAY_SD: ngaySuDung,
        TTSD_ID: ttsdID,
        // CAPGOC_ID: currentItem.CAPGOC_ID,
        LOPMC: lopMC,
        LOPMC_ID: dataItem.K,
        NHOMLC_ID: dataItem.NHOMLC_ID,
        KIEUDC_ID: kieuDiCapID,
        // DONVI: donVi,
        DONVI_ID: dataItem.M,
        // DIADANH_ID: currentItem.DIADANH_ID,
        // KETCUOI_N_ID: currentItem.KETCUOI_N_ID,
        // KETCUOI_D_ID: currentItem.KETCUOI_D_ID,
        TOQL_ID: dataItem.N,
        // CAP_CHA_ID: currentItem.CAP_CHA_ID,
        // DOANCAP: currentItem.DOANCAP,
        // DONVI_TD_ID: currentItem.DONVI_TD_ID,
        KIEUCAP_ID: 0,
        DIENCAOTHE: dienCTID,
        // PHANCAP_ID: currentItem.PHANCAP_ID,
        GHICHU: dataItem.V,
          // ? currentItem.GHICHU
          // : dataItem.P,
        TAISAN_ID: taiSanID,
        DUAN_ID: duAnID,
        // KHUVUC_ID: currentItem.KHUVUC_ID,
        // SOI_BD: currentItem.SOI_BD,
        CHUQUAN_ID: chuQuanID,
        // IMS_ID: currentItem.IMS_ID,
        // MA_CSHT: currentItem.MA_CSHT,
        // CHIEUDAI_TK: currentItem.CHIEUDAI_TK,
        MESSAGE: msg,
        TRANGTHAI: "Sẵn sàng",
        ITEM_TYPE: "CAP",
        IS_EDIT: true,
      };
      return result;
    },
    getDonViTheoTen: async function (tenDonVi) {
      let rs = await this.$root.context.post(
        "/web-cabman/cot-cong-be/getDonViTheoTen",
        {
          tenDonVi: tenDonVi,
        }
      );
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
      //maTS: mã tài sản
      //soHieuCT: số hiệu chứng từ dự án
      let rs = await this.$root.context.post(
        "/web-cabman/cot-cong-be/getTaiSanDuAnTheoMa",
        {
          maTS: maTS.toString(),
          soHieuCT: soHieuCT.toString(),
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
        let rs = await this.$root.context.post(
          "/web-cabman/danhmuc/layDsTaiSan",
          {
            id: duAnID,
          }
        );
        return rs.data;
      } else return [];
    },
    //-----
    getDsPhanLoaiKC: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsPhanLoaiKC"
      );
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
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsKieuLap"
      );
      return rs.data;
    },
    getDsNoiSX: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsNoiSX");
      return rs.data;
    },
    getDsViTriLapDat: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsViTriLapDat"
      );
      return rs.data;
    },
    getDsLoaiVo: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsLoaiVo");
      return rs.data;
    },
    getDsKichCoCap: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsKichCoCap"
      );
      return rs.data;
    },
    getDsDienCT: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsDienCT");
      return rs.data;
    },
    getDsLoaiCap: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsLoaiCap"
      );
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
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsKieuCap"
      );
      return rs.data;
    },
    getDsToaNha: async function () {
      let rs = await this.$root.context.post("/web-cabman/danhmuc/layDsToaNha");
      return rs.data;
    },
    getDsToQuanLy: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsToQuanLy"
      );
      return rs.data;
    },
    getCotSoHieu: async function (id) {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layCotSoHieu",
        {
          id: id,
        }
      );
      return rs.data;
    },
    getTramThietBi: async function (id) {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layTramThietBi",
        {
          id: id,
        }
      );
      return rs.data;
    },
    //----------
    getDsTrangThaiSuDung: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsTrangThaiSuDung"
      );
      return rs.data;
    },
    getDsChuQuan: async function () {
      let rs = await this.$root.context.post(
        "/web-cabman/danhmuc/layDsChuQuan"
      );
      return rs.data;
    },
    getDsCapGoc: async function (id) {
      let rs = await this.$root.context.get(
        "/web-cabman/cap/lay-id-theo-kyhieu",
        {
          kyhieu: id,
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
        return value.match(regEx);
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
      return (
        value == undefined || value == null || value.toString().trim() == ""
      );
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
    themDsCap: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachCap.length; i++) {
        this.danhSachCap[i].TRANGTHAI = "Đang lưu...";
        let r = await this.themCap(this.danhSachCap[i]);
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
      return count;
    },
    themCap: async function (dataItem) {
      let capGocId = await this.getDsCapGoc(dataItem.CAPGOC_ID)
      console.log('capGocId', capGocId)
      if (capGocId == null){
        this.$root.toastWarning("Không tìm thấy cáp gốc id");
        return
      }
      let data = {
        // cap_id: dataItem.CAP_ID,
        toql_id: dataItem.TOQL_ID,
        capgoc_id: capGocId.CAP_ID,
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
          dataItem.NGAY_LD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD)
            : null,
        ngay_sd:
          dataItem.NGAY_SD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD)
            : null,
        nhomlc_id: dataItem.NHOMLC_ID,
        noisx_id: dataItem.NOISX_ID,
        sodoiday: dataItem.SODOIDAY,
        soi_bd: dataItem.SOI_BD,
        suyhao: dataItem.SUYHAO,
        taisan_id: dataItem.TAISAN_ID,
        ten_cap: dataItem.TEN_CAP,
        trokhang: dataItem.TROKHANG,
        ttsd_id: dataItem.TTSD_ID,
      };
      let rs = await this.$root.context.post("/web-cabman/cap/themmoi", data);
      if (rs.error_code != "BSS-00000000"){
        if (rs.message_detail != null){
          this.$root.toastError(rs.message_detail);
        }else {
          this.$root.toastError(rs.message);
        }
      }
      return rs.data;
    },
    themDsKetCuoi: async function () {
      let count = 0;
      for (let i = 0; i < this.danhSachKetCuoi.length; i++) {
        this.danhSachKetCuoi[i].TRANGTHAI = "Đang lưu...";
        let r = await this.themKetCuoi(this.danhSachKetCuoi[i]);
        if (r.error_code != "BSS-00000000"){
          if (r.message_detail != null){
            this.$root.toastError(r.message_detail);
          }else {
            this.$root.toastError(r.message);
          }
          return null;
        }
        if (r != null) {
          count++;
          this.danhSachKetCuoi[i].TRANGTHAI = "Đã lưu";
        } else {
          this.danhSachKetCuoi[i].TRANGTHAI = "Lưu thất bại!";
        }
      }
      return count;
    },
    themKetCuoi: async function (dataItem) {
      let capGocId = await this.getDsCapGoc(dataItem.CAPGOC_ID)
      console.log('capGocId', capGocId)
      if (capGocId == null){
        this.$root.toastWarning("Không tìm thấy cáp gốc id");
        return
      }
      let data = {
        ap_id: dataItem.AP_ID,
        becap_id: dataItem.BECAP_ID,
        cap_id: dataItem.CAP_ID,
        cap_sp: dataItem.CAP_SP,
        capgoc_id: capGocId.CAP_ID,
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
          dataItem.NGAY_LD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_LD)
            : null,
        ngay_sd:
          dataItem.NGAY_SD != null
            ? DateTimeLib.toDateDisplay(dataItem.NGAY_SD)
            : null,
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
      };
      let rs = await this.$root.context.post(
        "/web-cabman/ketcuoi/themmoi",
        data
      );
      if (rs.error_code != "BSS-00000000"){
        if (rs.message_detail != null){
          this.$root.toastError(rs.message_detail);
        }else {
          this.$root.toastError(rs.message);
        }
      }
      console.log('test data', rs.error_code)
      return rs.data;
    },
    buttonReadFile_Click: async function () {
      if (this.fileExcel != null) {
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
                  this.dsExcelCap.shift(); // remove first item
                  break;
                case "KETCUOI":
                  this.dsExcelKetCuoi = xlsx.utils.sheet_to_json(
                    workBook.Sheets[sheetName],
                    { header: "A" }
                  );
                  this.dsExcelKetCuoi.shift(); // remove first item
                  break;
                default:
                  break;
              }
            }
          }
          this.$root.showLoading(true);
          try {
            if (this.dsChuQuan == null)
              this.dsChuQuan = await this.getDsChuQuan();
            if (this.dsTTSD == null)
              this.dsTTSD = await this.getDsTrangThaiSuDung();
            if (this.dsPhanLoaiKC == null)
              this.dsPhanLoaiKC = await this.getDsPhanLoaiKC();
            if (this.dsLoaiKC == null) this.dsLoaiKC = await this.getDsLoaiKC();
            if (this.dsCapSP == null) this.dsCapSP = await this.getDsCapSP();
            if (this.dsKieuLap == null)
              this.dsKieuLap = await this.getDsKieuLap();
            if (this.dsNoiSX == null) this.dsNoiSX = await this.getDsNoiSX();
            if (this.dsViTriLapDat == null)
              this.dsViTriLapDat = await this.getDsViTriLapDat();
            if (this.dsLoaiVo == null) this.dsLoaiVo = await this.getDsLoaiVo();
            if (this.dsKichCoCap == null)
              this.dsKichCoCap = await this.getDsKichCoCap();
            if (this.dsDienCT == null) this.dsDienCT = await this.getDsDienCT();
            if (this.dsLoaiCap == null)
              this.dsLoaiCap = await this.getDsLoaiCap();
            if (this.dsLopMC == null) this.dsLopMC = await this.getDsLopMC();
            if (this.dsNhomLC == null) this.dsNhomLC = await this.getDsNhomLC();
            if (this.dsKieuDC == null) this.dsKieuDC = await this.getDsKieuDC();
            if (this.dsKieuCap == null)
              this.dsKieuCap = await this.getDsKieuCap();
            if (this.dsToaNha == null) this.dsToaNha = await this.getDsToaNha();
            if (this.dsToQuanLy == null)
              this.dsToQuanLy = await this.getDsToQuanLy();
            if (this.dsDuAn == null) this.dsDuAn = await this.getDsDuAn();

            this.danhSachKetCuoi = await this.convertToDsKetCuoiItems();
            this.danhSachCap = await this.convertToDsCapItems();
          } finally {
            this.$root.showLoading(false);
          }
        };
        reader.readAsBinaryString(this.fileExcel);
      } else this.$root.toastWarning("Chưa chọn tệp dữ liệu");
    },
    buttonExportFile_Click: function () {
      let cked = $('input[name="radioDoiTuong"]:checked').val();
      let r = false;
      let workBook = xlsx.utils.book_new();
      if (cked == "KETCUOI") {
        if (this.danhSachKetCuoi.length > 0) {
          r = true;
          let sheet = xlsx.utils.json_to_sheet(this.danhSachKetCuoi);
          xlsx.utils.book_append_sheet(workBook, sheet, "KETCUOI");
        }
      } else if (cked == "CAP") {
        if (this.danhSachCap.length > 0) {
          r = true;
          let sheet = xlsx.utils.json_to_sheet(this.danhSachCap);
          xlsx.utils.book_append_sheet(workBook, sheet, "CAP");
        }
      }
      if (r) xlsx.writeFile(workBook, "du-lieu.xlsx");
      else {
        this.$root.toastInfo("Không có dữ liệu để xuất file");
      }
    },
    buttonExecute_Click: async function () {
      this.$root.showLoading(true);
      try {
        let isValid = false
        let count = 0
        let doiTuong = ''
        if (this.doiTuongCap === 'CAP') {
          doiTuong = 'cáp'
          count = await this.themDsCap()
          // console.log('count data', count)
          // alert(count)
          this.$refs.gridCap.refresh()
        } else if (this.doiTuongCap === 'KETCUOI') {
          doiTuong = 'kết cuối'
          count = await this.themDsKetCuoi()
          // console.log('count data', count)
          // alert(count)
          this.$refs.gridKetCuoi.refresh()
        }
        if (count > 0) {
          let msg = 'Đã bổ sung ' + count + ' ' + doiTuong + ' từ file excel'
          // alert(msg)
          this.$root.toastSuccess(msg)
        } else {
          alert('msg')
          this.$root.toastError(
            // 'Đã bổ sung ' + 2 + ' ' + 'cáp' + ' từ file excel'
            'Không có ' +
            doiTuong +
            ' nào được bổ sung. \nCó thể là do danh sách không có dữ liệu hoặc đã được lưu trong lần trước. \nVui lòng kiểm tra lại'
          )
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    showModalDiaChi: function (arg) {
      this.currentData = arg;
      this.$refs.modalDiaChi.show();
    },
    modalDiaChi_Ok: function () {
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
      if (this.currentData.DACDIEM_ID == 0) this.currentData.DACDIEM_ID = null;
      if (this.currentData.DIACHI_ID == 0) this.currentData.DIACHI_ID = null;
      if (this.currentData.SONHA == "") this.currentData.SONHA = null;
      if (this.currentData.DIACHI == "") this.currentData.DIACHI = null;

      this.updateDataSource(this.currentData);
    },
    modalDiaChi_Show: async function () {
      await this.$refs.formDiaChi.bindData(this.currentData);
    },
    updateDataSource: function (dataItem) {
      if (dataItem.ITEM_TYPE == "CAP") {
        let item = this.danhSachCap.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
      } else if (dataItem.ITEM_TYPE == "KETCUOI") {
        let item = this.danhSachKetCuoi.find((x) => x.STT == dataItem.STT);
        this.updateData(item, dataItem);
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
  },
};
</script>
