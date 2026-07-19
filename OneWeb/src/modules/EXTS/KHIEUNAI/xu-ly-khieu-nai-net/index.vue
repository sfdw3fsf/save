<template src="./index.template.html"></template>
<script>
import DateTimeLib from "../../../../utils/DateTimeLib";
import TraPhieu from "../xu-ly-khieu-nai/modal-traphieu";
import GiaoPhieuXacMinhPhoiHop from "../xu-ly-khieu-nai/giaophieu-xacminh-phoihop";
import { L10n } from "@syncfusion/ej2-base";
import ModalDonViLoi from "../TiepNhanKN/modal/KetQuaLoi/DonViLoi";
import ModalNhanVienLoi from "../TiepNhanKN/modal/KetQuaLoi/NhanVienLoi";
import GiaoPhieuKhieuNai from "../GiaoPhieuKhieuNai";
//import ChenNo from "../TiepNhanKN/modal/ChenNo.vue";
import frmHoSoKhieuNai from "@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue";
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
  components: {
    TraPhieu,
    GiaoPhieuXacMinhPhoiHop,
    ModalDonViLoi,
    ModalNhanVienLoi,
    GiaoPhieuKhieuNai,
    //ChenNo,
    frmHoSoKhieuNai
  },
  props: {
    TTKNIDs: "0",
    HuongGiaoIDs: "21055",//10003
  },
  data: function () {
    return {
      currentItem: {
        dichVuID: 0,
        quyTrinhID: 0,
        chuDeID: 0,
        loaiHinhTBID: 0,
        tagValue: "",
        ketQuaXuLy: 1,
        kyCuoc: "",
        selectedKhieuNai: {},
      },
      danhSachTTKNIDs: this.TTKNIDs,
      danhSachHuongGiaoIDs: this.HuongGiaoIDs,
      dsDichVu: [],
      dsQuyTrinh: [],
      dsChuDe: [],
      dsLoaiHinhTB: [],
      dsNhanVienThucHien: [],
      dsThueBaoKhieuNai: [],
      dsPhuongAnXuLy: [],
      dsKetQuaKN: [],
      dsHinhThucTL: [],
      dsThaoTac: [],
      dsThaoTacControl: [],
      luongID: 0,
      huongGiaoID: 0,
      isToTruong: false,
      nhanVienThucHienID: 0,
      themKHDBKhiKhieuNai: false,
      dsKyApDung: [],
      dsThangGC: [],
      titleText: "Xử lý khiếu nại",
      showTabs: {
        TB: true,
        KN: true
      }
    };
  },
  mounted: async function () {
    this.$root.showLoading(true);
    try {
      let ts = await this.getDsThamSo();
      if (ts != null) {
        let THEM_KHDB_KHI_KHIEUNAI = ts.find(
          (x) => x.MA_TS == "THEM_KHDB_KHI_KHIEUNAI"
        );
        if (THEM_KHDB_KHI_KHIEUNAI != null) {
          this.themKHDBKhiKhieuNai =
            THEM_KHDB_KHI_KHIEUNAI.TEN_TS.toString().trim() == "1";
        }
      }
      //QUYEN.TOTRUONG_XLKN
      let kt = await this.kiemTraQuyenND(60);
      if (kt != null) {
        this.isToTruong = kt.Result == true;
      }
      this.currentItem.kyCuoc = await this.getKyCuoc();
      this.currentItem.tagValue = await this.getTagValue();
      this.dsDichVu = await this.getDsDichVu();
      this.dsQuyTrinh = await this.getDsQuyTrinh();
      this.dsChuDe = await this.getDsChuDe();

      this.dsNhanVienThucHien = await this.getDsNhanVienThucHien();
      this.dsPhuongAnXuLy = await this.getDsPhuongAnXuLy();

      this.dsKetQuaKN = await this.getDsKetQuaKN();
      this.dsHinhThucTL = await this.getDsHinhThucTraLoi();
      this.nhanVienThucHienID = this.$root.token.getNhanVienID();
      await this.loadDsKhieuNai();
    } finally {
      this.$root.showLoading(false);
    }
  },
  computed: {
    dsTTKNID: function () {
      // let v = "1";
      // if (this.currentItem.tagValue != null) {
      //   let rs = this.currentItem.tagValue.split("+");
      //   if (rs.length >= 1) {
      //     v = rs[0].replace(";", ",");
      //   }
      // }
      // return v;
      //return "2, 4";
      let r = "2, 4";
      if (!(this.TTKNIDs == undefined || this.TTKNIDs == "")) r = this.TTKNIDs;
      return r;
    },
    dsHuongGiaoID: function () {
      // let v = "1";
      // if (this.currentItem.tagValue != null) {
      //   let rs = this.currentItem.tagValue.split("+");
      //   if (rs.length >= 2) {
      //     v = rs[1].replace(";", ",");
      //   }
      // }
      // return v;
      //return "2252, 2256";
      let r = "21055";//10003
      if (
        !(
          this.danhSachHuongGiaoIDs == undefined ||
          this.danhSachHuongGiaoIDs == ""
        )
      )
        r = this.danhSachHuongGiaoIDs;
      return r;
    },
    kyCuoc: function () {
      let r = new Date();
      if (!(this.currentItem.kyCuoc == null || this.currentItem.kyCuoc == ""))
        r = DateTimeLib.toDateObjectWithFormat(
          this.currentItem.kyCuoc,
          "YYYYMMDD"
        );
      return r;
    },
    enableBtnGiaoPhieu: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnGiaoPhieu"
            ) > -1;
          if (result) {
            if (this.dsTTKNID != "12") {
              result = this.currentItem.ketQuaXuLy != 1;
            }
          }
        }
      }
      return result;
    },
    enableBtnGhiLai: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnGhiLai"
            ) > -1;
        }
      }
      return result;
    },
    enableBtnHoanThanh_GQKN: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnHoanThanh_GQKN"
            ) > -1;
          if (result) {
            result = this.currentItem.ketQuaXuLy == 1;
          }
        }
      }
      return result;
    },
    enableBtnHoanThanh: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnHoanThanh"
            ) > -1;
          if (result) {
            result = this.currentItem.ketQuaXuLy == 1;
          }
        }
      }
      return result;
    },
    enableBtnKhoaPhieu: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnKhoaPhieu"
            ) > -1;
          if (result) {
            result = this.currentItem.ketQuaXuLy == 1;
          }
        }
      }
      return result;
    },
    enableTxtNguyenNhan: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "txtNguyenNhan"
            ) > -1;
        }
      }
      return result;
    },
    enableTxtNoiDungXL: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "txtNoiDungXL"
            ) > -1;
        }
      }
      return result;
    },
    enableBtnGiaoViec: function () {
      let result = false;
      if (this.isToTruong) {
        if (
          !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
        ) {
          if (this.dsThaoTacControl.length > 0) {
            result =
              this.dsThaoTacControl.findIndex(
                (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbrnGiaoViec"
              ) > -1;
            //if (result) result = !this.isSelectedTabDaGiao();
          }
        }
      }
      return result;
    },
    enableBtnHuyGiao: function () {
      let result = false;
      if (this.isToTruong) {
        if (
          !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
        ) {
          if (this.dsThaoTacControl.length > 0) {
            result =
              this.dsThaoTacControl.findIndex(
                (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnHuyGiao"
              ) > -1;
            //if (result) result = this.isSelectedTabDaGiao();
          }
        }
      }
      return result;
    },
    enableBtnTraPhieu: function () {
      let result = true;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result = !(
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 0 && x.CONTROL_NAME == "tsbtnTraPhieu"
            ) > -1
          );
        }
      }
      return result;
    },
    enableBtnGiaoPhoiHop: function () {
      let result = false;
      if (
        !(this.dsThaoTacControl == null || this.dsThaoTacControl == undefined)
      ) {
        if (this.dsThaoTacControl.length > 0) {
          result =
            this.dsThaoTacControl.findIndex(
              (x) => x.ENABLE == 1 && x.CONTROL_NAME == "tsbtnGiaoPhoiHop"
            ) > -1;
        }
      }
      return result;
    },
    labelNoiDungTraLoiText: function () {
      let result = "Nội dung trả lời";
      if (this.currentItem.ketQuaXuLy != 1) result = "Lý do tồn";
      return result;
    },
    enableBtnKetQuaKNMore: function () {
      let result = false;
      if (this.currentItem.selectedKhieuNai != null) {
        result =
          this.currentItem.selectedKhieuNai.KETQUAKN_ID == 7 ||
          this.currentItem.selectedKhieuNai.KETQUAKN_ID == 9;
      }
      return result;
    },
  },
  methods: {
    btnTraPhieu_Click() {
      let modal = this.$refs["ref-modal-tra-phieu"];
      modal.setMaTb(this.currentItem.selectedKhieuNai.MA_TB);
      modal.show();
    },
    getDsThamSo: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsThamSo"
      );
      return rs.data;
    },
    getKyCuoc: async function () {
      let rs = await this.$root.context.get(
        "/web-thuno/api/thu-no/common/ky-cuoc-hien-hanh"
      );
      if (rs.data != null)
        if (rs.data.length > 0)
          if (!(rs.data[0].KYCUOC == null || rs.data[0].KYCUOC == undefined))
            return rs.data[0].KYCUOC.toString();
      return "";
    },
    getTagValue: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getTagValue",
        {
          id: 600,
        }
      );
      return rs.data.TAGVALUE;
    },
    getDsDichVu: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt"
      );
      let result = rs.data;
      if (result == null || result == undefined) result = [];
      result.splice(0, 0, { dichvuvt_id: 0, ten_dvvt: "(Lựa chọn ...)" });
      return result;
    },
    getDsQuyTrinh: async function () {
      //dsTTKNID = "2, 4";
      //dsHuongGiaoID = "2252, 2256";
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsQuyTrinh",
        {
          dsTTKNID: this.dsTTKNID,
          dsHuongGiaoID: this.dsHuongGiaoID,
        }
      );
      let result = rs.data;
      if (result == null || result == undefined) result = [];
      result.splice(0, 0, { QUYTRINH_ID: 0, QUYTRINH: "(Lựa chọn ...)" });
      return result;
    },
    getDsChuDe: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsChuDe"
      );
      let result = rs.data;
      if (result == null || result == undefined) result = [];
      result.splice(0, 0, { CHUDEKN_ID: 0, TENCHUDE: "(Lựa chọn ...)" });
      return result;
    },
    getDsLoaiHinh: async function (dichVuVTID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsLoaiHinhTB",
        {
          dichVuVTID: dichVuVTID,
        }
      );
      let result = rs.data;
      if (result == null || result == undefined) result = [];
      result.splice(0, 0, { LOAITB_ID: 0, LOAIHINH_TB: "(Lựa chọn ...)" });
      return result;
    },
    getDsNhanVienThucHien: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsNhanVienXuLyKN"
      );
      return rs.data;
    },
    getDsPhuongAnXuLy: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsPhuongAnXuLy"
      );
      return rs.data;
    },
    getDsTBKhieuNai: async function (chudekn_id, trangthai) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsTBKhieuNai",
        {
          ttkn_id: this.dsTTKNID,
          huonggiao_id: this.dsHuongGiaoID,
          chudekn_id: chudekn_id,
          trangthai: trangthai,
          totruong: this.isToTruong ? 1 : 0, //1: QUYEN.TOTRUONG_XLKN
        }
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((element) => {
          element.GIO_CL = this.getGioConLai(element.THOIGIAN_CK);
        });
      }
      return rs.data;
    },
    getDsTBKhieuNaiNET: async function (chudekn_id, trangthai) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsTBKhieuNaiNET",
        {
          ttkn_id: this.dsTTKNID,
          huonggiao_id: this.dsHuongGiaoID,
          chudekn_id: chudekn_id,
          trangthai: trangthai,
          totruong: this.isToTruong ? 1 : 0, //1: QUYEN.TOTRUONG_XLKN
        }
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((element) => {
          element.GIO_CL = this.getGioConLai(element.THOIGIAN_CK);
        });
      }
      return rs.data;
    },
    getDsKhieuNaiTheoTrangThai: async function (trangthai) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsKhieuNaiTheoTrangThai",
        {
          ttkn_id: this.dsTTKNID,
          huonggiao_id: this.dsHuongGiaoID,
          trangthai: trangthai,
          totruong: this.isToTruong ? 1 : 0, //1: QUYEN.TOTRUONG_XLKN
        }
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((element) => {
          element.GIO_CL = this.getGioConLai(element.THOIGIAN_CK);
        });
      }
      return rs.data;
    },
    getDsKhieuNaiKhac: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsKhieuNaiKhac",
        {
          ttkn_id: this.dsTTKNID,
          huonggiao_id: this.dsHuongGiaoID
        }
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((element) => {
          element.GIO_CL = this.getGioConLai(element.THOIGIAN_CK);
        });
      }
      return rs.data;
    },
    comboboxDichVu_Change: async function () {
      this.dsLoaiHinhTB = await this.getDsLoaiHinh(
        this.$refs.comboboxDichVu.value
      );
    },
    getDsGiamCuoc: async function (khieuNaiID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsGiamCuoc",
        {
          id: khieuNaiID,
        }
      );
      return rs.data;
    },
    getMaThanhToan: async function (thanhToanID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getMaThanhToan",
        {
          id: thanhToanID,
        }
      );
      return rs.data;
    },
    getPhieuGiaoKhieuNai: async function (phieuKNID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getPhieuGiaoKhieuNai",
        {
          id: phieuKNID,
        }
      );
      return rs.data;
    },
    getTuyenThu: async function (thanhToanID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getTuyenThu",
        {
          id: thanhToanID,
        }
      );
      return rs.data;
    },
    layThongTinNguoiDung: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layThongTinNguoiDung",
        {
          ma_nd: this.$root.token.getUserName(),
        }
      );
      return rs.data;
    },
    capNhatGiaoPhieuKNPH: async function (phieu_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatGiaoPhieuKNPH",
        {
          id: phieu_id,
        }
      );
      return rs.data;
    },
    hoanThienGiaoPHBanCheo: async function (phieu_id, noidung, nhanvien_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/hoanThienGiaoPHBanCheo",
        {
          phieu_id: phieu_id,
          noidung: noidung,
          nhanvien_id: nhanvien_id,
        }
      );
      return rs.data;
    },
    getDonVi: async function (donViID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDonVi",
        {
          id: donViID,
        }
      );
      return rs.data;
    },
    getNoiDungXacMinh: async function (khieuNaiID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getNoiDungXacMinh",
        {
          id: khieuNaiID,
        }
      );
      return rs.data;
    },
    getDsKetQuaKN: async function () {
      let result = [];
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsKetQuaKN",
        {
          id: 2,
        }
      );
      //BSS-72801
      if (rs.data != undefined) {
        for (let i = 0; i < rs.data.length; i++) {
          result.push(
            {
              NGUYENNHAN_ID: rs.data[i].KETQUAKN_ID,
              NGUYENNHAN: rs.data[i].NOIDUNG
            }
          );
        }
      }
      return result;
    },
    getNguyenNhanKN: async function (chuDeKNID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getNguyenNhanKN",
        {
          id: chuDeKNID,
        }
      );
      return rs.data;
    },
    getPhuongAnXL: async function (chuDeKNID) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getPhuongAnXL",
        {
          id: chuDeKNID,
        }
      );
      return rs.data;
    },
    getDsHinhThucTraLoi: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsHinhThucTraLoi"
      );
      return rs.data;
    },
    capNhatTTGiaoViecKN: async function (
      khieunai_id,
      nhanvien_th_id,
      p_ngay_th,
      phieukn_id
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatTTGiaoViecKN",
        {
          khieunai_id: khieunai_id,
          nhanvien_th_id: nhanvien_th_id,
          p_ngay_th: DateTimeLib.toDateTimeDisplayWithFormat(
            p_ngay_th,
            "YYYY-MM-DD HH:mm:ss"
          ),
          phieukn_id: phieukn_id,
        }
      );
      return rs.data;
    },
    capNhatXuLy: async function (
      httl_id,
      khieunai_id,
      macv,
      nd_ton,
      ngay_gq,
      nguyennhan,
      nhanvien_gq_id,
      noidung_gq
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatXuLy",
        {
          httl_id: httl_id,
          khieunai_id: khieunai_id,
          macv: macv,
          nd_ton: nd_ton,
          ngay_gq: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_gq,
            "YYYY-MM-DD HH:mm:ss"
          ),
          nguyennhan: nguyennhan,
          nhanvien_gq_id: nhanvien_gq_id,
          noidung_gq: noidung_gq,
        }
      );
      return rs.data;
    },
    capNhatThongTinXacMinh: async function (
      phieukn_id,
      nhanvien_th_id,
      nd_xacminh
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatThongTinXacMinh",
        {
          phieukn_id: phieukn_id,
          nhanvien_th_id: nhanvien_th_id,
          nd_xacminh: nd_xacminh,
        }
      );
      return rs.data;
    },
    capNhatTTPhieuGiao: async function (
      phieukn_id
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatTTPhieuGiao",
        {
          phieukn_id: phieukn_id,
          ttph_id: 4
        }
      );
      return rs.data;
    },
    getThongTinHuongGiao: async function (quytrinh_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getThongTinHuongGiao",
        {
          quytrinh_id: quytrinh_id,
          dsttkn_id: this.dsTTKNID,
        }
      );
      return rs.data;
    },
    getThongTinThaoTac: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getThongTinThaoTac",
        {
          id: this.luongID,
        }
      );
      return rs.data;
    },
    getThongTinThaoTacControl: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getThongTinThaoTacControl",
        {
          id: this.luongID,
        }
      );
      return rs.data;
    },
    checkPhaiLam: function (code) {
      if (!(this.dsThaoTac == null || this.dsThaoTac == undefined)) {
        return (
          this.dsThaoTac.filter((x) => x.ENABLE == 1 && x.CODE == code).length >
          0
        );
      } else {
        return false;
      }
    },
    kiemTraQuyenND: async function (quyen_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/kiemTraQuyenND",
        {
          nguoidung_id: this.$root.token.getNguoiDungID(),
          quyen_id: quyen_id,
        }
      );
      return rs.data;
    },
    giaoPhieuNhanVien: async function (
      phieukn_id,
      nhanvien_giao_id,
      nhanvien_th_id,
      ghichu
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/giaoPhieuNhanVien",
        {
          phieukn_id: phieukn_id,
          nhanvien_giao_id: nhanvien_giao_id,
          nhanvien_th_id: nhanvien_th_id,
          ghichu: ghichu,
        }
      );
      return rs.data;
    },
    capNhatNhanVienThucHien: async function (khieunai_id, nhanvien_gq_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatNhanVienThucHien",
        {
          khieunai_id: khieunai_id,
          nhanvien_gq_id: nhanvien_gq_id,
        }
      );
      return rs.data;
    },
    getNoiDungTinNhan: async function (khieunai_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getNoiDungTinNhan",
        {
          id: khieunai_id,
        }
      );
      return rs.data;
    },
    getNoiDungTinNhanHuyGiao: async function (khieunai_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getNoiDungTinNhanHuyGiao",
        {
          id: khieunai_id,
        }
      );
      return rs.data;
    },
    getSoDienThoaiNV: async function (nhanvien_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getSoDienThoaiNV",
        {
          id: nhanvien_id,
        }
      );
      return rs.data;
    },
    getSoDienThoaiNVGiao: async function (phieukn_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getSoDienThoaiNVGiao",
        {
          id: phieukn_id,
        }
      );
      return rs.data;
    },
    huyGiaoPhieu: async function (phieukn_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/huyGiaoPhieu",
        {
          id: phieukn_id,
        }
      );
      return rs.data;
    },
    huyNhanVienTH: async function (khieunai_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/huyNhanVienTH",
        {
          id: khieunai_id,
        }
      );
      return rs.data;
    },
    getDsKyApDung: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsKyApDung"
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((e) => {
          e.KY_GC = e.KY_GC.toString();
        });
      }
      return rs.data;
    },
    getDsThangGC: async function (ma_tt, ma_tb) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getDsThangGC",
        {
          ma_tt: ma_tt,
          ma_tb: ma_tb,
        }
      );
      if (!(rs.data == undefined || rs.data == null)) {
        rs.data.forEach((e) => {
          e.THANG_GC = e.THANG_GC.toString();
        });
      }
      return rs.data;
    },
    // guiSms: async function (so_dt, noidung) {
    //   let rs = await this.$root.context.post(
    //     "/web-cabman/traphieukhieunai/off-send-sms",
    //     {
    //       msisdn: so_dt,
    //       content: noidung,
    //       mt_type: "reply",
    //     }
    //   );
    //   return rs.data;
    // },
    getHuongGiao: async function (quytrinh_id, ttkn_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/getHuongGiao",
        {
          quytrinh_id: quytrinh_id,
          ttkn_id: ttkn_id,
        }
      );
      return rs.data;
    },
    getHuongGiaoTheoLuong: async function () {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/layHuongGiaoTheoLuong",
        {
          id: this.luongID,
        }
      );
      return rs.data;
    },
    kiemTraPhieuCon: async function (phieukn_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/kiemTraPhieuCon",
        {
          id: phieukn_id,
        }
      );
      return rs.data;
    },
    kiemTraKhoaPhieuKN: async function (phieukn_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/kiemTraKhoaPhieuKN",
        {
          id: phieukn_id,
        }
      );
      return rs.data;
    },
    capNhatPAXLDoHL: async function (khieunai_id, paxl_id, hailong) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatPAXLDoHL",
        {
          khieunai_id: khieunai_id,
          paxl_id: paxl_id,
          hailong: hailong,
        }
      );
      return rs.data;
    },
    capNhatKetQuaNoiDung: async function (
      khieunai_id,
      ketquakn_id,
      noidung_gq
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatKetQuaNoiDung",
        {
          khieunai_id: khieunai_id,
          ketquakn_id: ketquakn_id,
          noidung_gq: noidung_gq,
        }
      );
      return rs.data;
    },
    capNhatCongVan: async function (khieunai_id, congvan_xl) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatCongVan",
        {
          khieunai_id: khieunai_id,
          congvan_xl: congvan_xl,
        }
      );
      return rs.data;
    },
    capNhatKHDB: async function (khieunai_id, nhanvien_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatKHDB",
        {
          khieunai_id: khieunai_id,
          nhanvien_id: nhanvien_id,
        }
      );
      return rs.data;
    },
    capNhatHTHS: async function (
      khieunai_id,
      phieukn_id,
      nhanvien_th_id,
      ngay_ht,
      ttkn_id,
      nguyennhan,
      traloi,
      ngay_gq
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatHTHS",
        {
          khieunai_id: khieunai_id,
          phieukn_id: phieukn_id,
          nhanvien_th_id: nhanvien_th_id,
          ngay_ht: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_ht,
            "YYYY-MM-DD HH:mm:ss"
          ),
          ttkn_id: ttkn_id,
          nguyennhan: nguyennhan,
          traloi: traloi,
          ngay_gq: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_gq,
            "YYYY-MM-DD HH:mm:ss"
          ),
        }
      );
      return rs.data;
    },
    capNhatXuLyHT: async function (
      khieunai_id,
      macv,
      nd_ton,
      ngay_gq,
      nguyennhan,
      nhanvien_gq_id,
      noidung_gq
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatXuLyHT",
        {
          khieunai_id: khieunai_id,
          macv: macv,
          nd_ton: nd_ton,
          ngay_gq: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_gq,
            "YYYY-MM-DD HH:mm:ss"
          ),
          nguyennhan: nguyennhan,
          nhanvien_gq_id: nhanvien_gq_id,
          noidung_gq: noidung_gq,
        }
      );
      return rs.data;
    },
    capNhatTTDaGiao: async function (
      khieunai_id,
      phieukn_id,
      ngay_ht,
      nhanvien_th_id
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatTTDaGiao",
        {
          khieunai_id: khieunai_id,
          phieukn_id: phieukn_id,
          ngay_ht: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_ht,
            "YYYY-MM-DD HH:mm:ss"
          ),
          nhanvien_th_id: nhanvien_th_id,
        }
      );
      return rs.data;
    },
    capNhatBaoTonHT: async function (
      khieunai_id,
      ngay_gq,
      nhanvien_gq_id,
      noidung_gq,
      nd_ton,
      donvi_id,
      ttkn_id
    ) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/capNhatBaoTonHT",
        {
          khieunai_id: khieunai_id,
          ngay_gq: DateTimeLib.toDateTimeDisplayWithFormat(
            ngay_gq,
            "YYYY-MM-DD HH:mm:ss"
          ),
          nhanvien_gq_id: nhanvien_gq_id,
          noidung_gq: noidung_gq,
          nd_ton: nd_ton,
          donvi_id: donvi_id,
          ttkn_id: ttkn_id,
        }
      );
      return rs.data;
    },
    giaoPhieuKNKhiHC: async function (khieunai_id, phieukn_id, huonggiao_id, donvi_nhan_id) {
      let rs = await this.$root.context.post(
        "/web-gqkn/xu-ly-khieu-nai/giaoPhieuKNKhiHC",
        {
          khieunai_id: khieunai_id,
          phieukn_id: phieukn_id,
          huonggiao_id: huonggiao_id,
          donvi_giao_id: this.$root.token.getDonViID(),
          donvi_nhan_id: donvi_nhan_id,
          nhanvien_id: this.$root.token.getNhanVienID(),
        }
      );
      return rs.data;
    },
    loadDsKhieuNai: async function () {
      this.dsThaoTac = [];
      this.dsThaoTacControl = [];
      this.currentItem.selectedKhieuNai = {};
      this.dsThueBaoKhieuNai = [];

      this.dsThueBaoKhieuNai = await this.getDsTBKhieuNaiNET(0, 0);
      if (this.dsTTKNID == "10" || this.dsTTKNID == "11" || this.dsTTKNID == "2" || this.dsTTKNID == "4" || this.dsTTKNID == "10" || this.dsTTKNID == "10, 4") {
        this.dsThueBaoKhieuNai = await this.getDsTBKhieuNai(0, 0);
      }
      if (this.dsTTKNID == "3") {
        this.dsThueBaoKhieuNai = await this.getDsKhieuNaiTheoTrangThai(0);
      }
    },
    gridDsThueBaoKhieuNai_rowSelected: async function (args) {
      this.$root.showLoading(true);
      try {
        let tuyenThu = await this.getTuyenThu(args.data.THANHTOAN_ID);
        if (tuyenThu != null) {
          args.data.TUYEN_THU = tuyenThu.TUYEN_THU;
          args.data.MA_TT = tuyenThu.MA_TT;
        }
        let donViTra = await this.getDonVi(args.data.DONVITRA_CON_ID);
        if (donViTra != null) {
          args.data.DONVITRA_CON_TEXT = donViTra.TEN_DV;
        }
        let donViGiao = await this.getDonVi(args.data.DONVI_GIAO_ID);
        if (donViGiao != null) {
          args.data.DONVI_GIAO_TEXT = donViGiao.TEN_DV;
        }
        if (this.dsTTKNID != "3") {
          let v = await this.getNoiDungXacMinh(args.data.KHIEUNAI_ID);
          args.data.ND_XACMINH = v != null ? v.ND_XACMINH : null;
        }
        let huongGiao = await this.getThongTinHuongGiao(args.data.QUYTRINH_ID);
        if (huongGiao != null) {
          this.luongID = huongGiao[0].LUONG_ID;
          this.titleText = huongGiao[0].HUONGGIAO;
          this.huongGiaoID = huongGiao[0].HUONGGIAO_ID;
        } else {
          this.luongID = 0;
        }
        this.dsThaoTac = await this.getThongTinThaoTac();
        this.dsThaoTacControl = await this.getThongTinThaoTacControl();
        args.data.NGAY_TN_DATE = DateTimeLib.toDateTimeObjectWithFormat(
          args.data.NGAY_TN,
          "YYYY-MM-DD HH:mm:ss"
        );
        args.data.HENGQ_TU_DATE = DateTimeLib.toDateTimeObjectWithFormat(
          args.data.HENGQ_TU,
          "YYYY-MM-DD HH:mm:ss"
        );
        args.data.HENGQ_DEN_DATE = DateTimeLib.toDateTimeObjectWithFormat(
          args.data.HENGQ_DEN,
          "YYYY-MM-DD HH:mm:ss"
        );
        args.data.NGAY_GQ_DATE =
          args.data.NGAY_GQ == null
            ? new Date()
            : DateTimeLib.toDateTimeObjectWithFormat(
              args.data.NGAY_GQ,
              "YYYY-MM-DD HH:mm:ss"
            );
        if (args.data.NHANVIEN_GQ_ID == null || args.data.NHANVIEN_GQ_ID == 0)
          args.data.NHANVIEN_GQ_ID = this.$root.token.getNhanVienID();

        if (args.data.PAXL_ID == null || args.data.PAXL_ID == 0)
          args.data.PAXL_ID = 1;

        if (args.data.KETQUAKN_ID == null || args.data.KETQUAKN_ID == 0)
          args.data.KETQUAKN_ID = 6;

        this.currentItem.selectedKhieuNai = args.data;
        this.dsKetQuaKN = await this.getNguyenNhanKN(this.currentItem.selectedKhieuNai.CHUDEKN_ID);
        this.dsPhuongAnXuLy = await this.getPhuongAnXL(this.currentItem.selectedKhieuNai.CHUDEKN_ID);

        this.dsLoaiHinhTB = await this.getDsLoaiHinh(args.data.DICHVUVT_ID);
        this.dsKyApDung = await this.getDsKyApDung();
        this.dsThangGC = await this.getDsThangGC(
          args.data.MA_TT,
          args.data.MA_TB
        );

      } finally {
        this.$root.showLoading(false);
      }
    },
    comboboxNhanVienThucHien_selectedChanged: function (args) { },
    getGioConLai: function (beginDate) {
      let rs = null;
      if (!(beginDate == undefined || beginDate == null || beginDate == "")) {
        let now = new Date();
        let bd = DateTimeLib.toDateTimeObjectWithFormat(
          beginDate,
          "YYYY-MM-DD HH:mm:ss"
        );
        rs = (Math.abs(now - bd) / 3600000).toString();
      }
      return rs;
    },
    btnLayTT_Click: async function (args) {
      this.$root.showLoading(true);
      try {
        await this.loadDsKhieuNai();
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnGhiLai_Click: async function (args) {
      this.$root.showLoading(true);
      try {
        if (
          !this.checkEmptyString(this.currentItem.selectedKhieuNai.NGAY_GQ_DATE)
        ) {
          let now = new Date();
          if (this.currentItem.selectedKhieuNai.NGAY_GQ_DATE > now) {
            this.$root.toastError(
              "Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại"
            );
          } else {
            if (
              this.currentItem.selectedKhieuNai.NGAY_TN_DATE >
              this.currentItem.selectedKhieuNai.NGAY_GQ_DATE
            ) {
              this.$root.toastError(
                "Bạn không được phép chọn ngày hoàn thành nhỏ hơn ngày tiếp nhận"
              );
            } else {
              await this.capNhatTTGiaoViecKN(
                this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                this.$root.token.getNhanVienID(),
                this.currentItem.selectedKhieuNai.NGAY_GQ_DATE,
                this.currentItem.selectedKhieuNai.PHIEUKN_ID
              );
              await this.capNhatXuLy(
                this.currentItem.selectedKhieuNai.HTTL_ID,
                this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                this.currentItem.selectedKhieuNai.CONGVAN_XL,
                "",
                this.currentItem.selectedKhieuNai.NGAY_GQ_DATE,
                this.currentItem.selectedKhieuNai.NGUYENNHAN_KN,
                this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID,
                this.currentItem.selectedKhieuNai.NOIDUNG_GQ
              );
              if (this.checkPhaiLam("XACMINH")) {
                await this.capNhatThongTinXacMinh(
                  this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                  this.$root.token.getNhanVienID(),
                  this.currentItem.selectedKhieuNai.ND_XACMINH
                );
              }
              await this.loadDsKhieuNai();
              this.$root.toastSuccess("Cập nhật dữ liệu thành công !");
            }
          }
        } else {
          this.$root.toastError("Vui lòng chọn ngày hoàn thành");
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnGiaoNV_Click: async function (args) {
    },
    btnHuyGiaoNV_Click: async function (args) {
    },
    btnHoanThanh_Click: async function (args) {
      this.$root.showLoading(true);
      try {
        if (
          this.checkEmptyNumber(
            this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID
          )
        ) {
          this.$root.toastError("Bạn chưa chọn Nhân viên xử lý !");
        } else {
          if (
            this.checkEmptyString(this.currentItem.selectedKhieuNai.NOIDUNG_GQ)
          ) {
            this.$root.toastError("Bạn chưa nhập nội dung trả lời khiếu nại !");
          } else {
            if (
              this.checkEmptyString(
                this.currentItem.selectedKhieuNai.NGUYENNHAN_KN
              )
            ) {
              this.$root.toastError("Bạn chưa nhập nguyên nhân !");
            } else {
              let t1 = await this.kiemTraPhieuCon(
                this.currentItem.selectedKhieuNai.PHIEUKN_ID
              );
              if (t1.RS > 0) {
                this.$root.toastError(
                  "Vẫn còn phiếu con chưa thực hiện nên Bạn chưa được phép Hoàn thành!"
                );
              } else {
                let huongGiaoID = 0;
                //TRANGTHAI_KHIEU_NAI.XU_LY_XONG = 5
                let t2 = await this.getHuongGiao(
                  this.currentItem.selectedKhieuNai.QUYTRINH_ID,
                  5
                );
                if (t2 != null) {
                  huongGiaoID = t2.HUONGGIAO_ID;
                }

                await this.capNhatPAXLDoHL(
                  this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                  this.currentItem.selectedKhieuNai.PAXL_ID,
                  this.currentItem.selectedKhieuNai.HAILONG
                );

                await this.capNhatKetQuaNoiDung(
                  this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                  this.currentItem.selectedKhieuNai.KETQUAKN_ID,
                  this.currentItem.selectedKhieuNai.NOIDUNG_GQ
                );
                await this.capNhatCongVan(
                  this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                  this.currentItem.selectedKhieuNai.CONGVAN_XL
                );

                // if (this.checkPhaiLam("XACMINH"))
                //   await this.capNhatThongTinXacMinh(
                //     this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                //     this.$root.token.getNhanVienID(),
                //     this.currentItem.selectedKhieuNai.NOIDUNG_GQ
                //   );

                // await this.capNhatThongTinGiamCuoc(
                //   this.currentItem.selectedKhieuNai.KHIEUNAI_ID
                // );

                if (this.checkPhaiLam("TUDONG_HTHS")) {
                  await this.giaoPhieuKNKhiHC(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                    huongGiaoID,
                    this.$root.token.getDonViID()
                  );

                  //TRANGTHAI_KHIEU_NAI.KHIEU_NAI_HOAN_THANH = 6
                  await this.capNhatHTHS(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                    this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID,
                    new Date(),
                    6,
                    this.currentItem.selectedKhieuNai.NGUYENNHAN_KN,
                    this.currentItem.selectedKhieuNai.NOIDUNG_GQ,
                    this.currentItem.selectedKhieuNai.NGAY_GQ_DATE
                  );

                  if (this.themKHDBKhiKhieuNai) {
                    await this.capNhatKHDB(
                      this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                      this.$root.token.getNhanVienID()
                    );
                  }
                  this.$root.toastSuccess(
                    "Bạn đã khóa phiếu khiếu nại thành công !"
                  );
                } else {
                  await this.capNhatXuLyHT(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    this.currentItem.selectedKhieuNai.CONGVAN_XL,
                    "",
                    new Date(),
                    this.currentItem.selectedKhieuNai.NGUYENNHAN_KN,
                    this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID,
                    this.currentItem.selectedKhieuNai.NOIDUNG_GQ
                  );
                  await this.giaoPhieuKNKhiHC(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                    huongGiaoID,
                    this.currentItem.selectedKhieuNai.DONVI_ID
                  );
                  await this.capNhatTTDaGiao(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    this.currentItem.selectedKhieuNai.PHIEUKN_ID,
                    new Date(),
                    this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID
                  );
                  //TRANGTHAI_KHIEU_NAI.XU_LY_XONG = 5
                  await this.capNhatBaoTonHT(
                    this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
                    new Date(),
                    this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID,
                    this.currentItem.selectedKhieuNai.NOIDUNG_GQ,
                    "",
                    this.$root.token.getDonViID(),
                    5
                  );
                  this.$root.toastSuccess(
                    "Bạn đã xử lý khiếu nại thành công !"
                  );
                }
                await this.loadDsKhieuNai();
              }
            }
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    btnHoanThanh_Click_1: async function (args) {
      this.$root.showLoading(true);
      try {
        if (
          this.checkEmptyNumber(
            this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID
          )
        ) {
          this.$root.toastError("Bạn chưa chọn Nhân viên xử lý !");
        } else {
          let ck = true;
          if (this.dsTTKNID == "3") {
            if (this.checkEmptyString(this.currentItem.selectedKhieuNai.ND_XACMINH)) {
              this.$root.toastError("Bạn hãy nhập nội dung xác minh !");
              ck = false;
            }
          }
          if (ck) {
            if (this.currentItem.selectedKhieuNai.TINH_ID != 100) {
              let t1 = await this.layThongTinNguoiDung();
              if (!(t1 == null || t1.length == 0)) {
                await this.capNhatGiaoPhieuKNPH(this.currentItem.selectedKhieuNai.PHIEUKN_ID);
                await this.hoanThienGiaoPHBanCheo(this.currentItem.selectedKhieuNai.PHIEUKN_ID, this.currentItem.selectedKhieuNai.ND_XACMINH, t1[0].nhanvien_id);
              }
              else {
                this.$root.toastError("Không tồn tại user " + this.$root.token.getUserName() + "!");
              }
            }
            else {
              await this.capNhatTTPhieuGiao(this.currentItem.selectedKhieuNai.PHIEUKN_ID);
            }
            this.$root.toastSuccess(
              "Hoàn thành công việc thành công !"
            );
            await this.loadDsKhieuNai();
          }
        }
      }
      finally {
        this.$root.showLoading(false);
      }
    },
    btnKhoaPhieu_Click: async function (args) {
      this.$root.showLoading(true);
      try {
        if (
          this.checkEmptyNumber(
            this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID
          )
        ) {
          this.$root.toastError("Bạn chưa chọn Nhân viên xử lý !");
        } else {
          if (
            this.checkEmptyString(
              this.currentItem.selectedKhieuNai.NGUYENNHAN_KN
            )
          ) {
            this.$root.toastError("Bạn chưa nhập nguyên nhân !");
          } else {
            if (
              this.checkEmptyString(
                this.currentItem.selectedKhieuNai.NOIDUNG_GQ
              )
            ) {
              this.$root.toastError("Bạn chưa nhập nội dung xử lý !");
            } else {
              let t11 = await this.kiemTraKhoaPhieuKN(this.currentItem.selectedKhieuNai.PHIEUKN_ID);
              if (t11 != "ok") {
                this.$root.toastError(t11);
              }
              else {
                let t1 = await this.kiemTraPhieuCon(
                  this.currentItem.selectedKhieuNai.PHIEUKN_ID
                );
                if (t1.RS > 0) {
                  this.$root.toastError(
                    "Vẫn còn phiếu con chưa thực hiện nên Bạn chưa được phép Hoàn thành!"
                  );
                } else {
                  this.$bvModal
                    .msgBoxConfirm("Bạn có muốn Khóa phiếu không ?", {
                      title: "Thông báo",
                      size: "sm",
                      okTitle: "Đồng ý",
                      cancelTitle: "Hủy",
                      centered: true,
                    })
                    .then(async (v) => {
                      if (v) {
                        await this.khoaPhieu();
                      }
                    });
                }
              }
            }
          }
        }
      } finally {
        this.$root.showLoading(false);
      }
    },
    khoaPhieu: async function (args) {
      //TRANGTHAI_KHIEU_NAI.KHIEU_NAI_HOAN_THANH = 6
      await this.capNhatHTHS(
        this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
        this.currentItem.selectedKhieuNai.PHIEUKN_ID,
        this.currentItem.selectedKhieuNai.NHANVIEN_GQ_ID,
        new Date(),
        6,
        this.currentItem.selectedKhieuNai.NGUYENNHAN_KN,
        this.currentItem.selectedKhieuNai.NOIDUNG_GQ,
        this.currentItem.selectedKhieuNai.NGAY_GQ_DATE
      );

      if (this.themKHDBKhiKhieuNai) {
        await this.capNhatKHDB(
          this.currentItem.selectedKhieuNai.KHIEUNAI_ID,
          this.$root.token.getNhanVienID()
        );
      }
      this.$root.toastSuccess("Bạn đã khóa phiếu thành công !");

      this.loadDsKhieuNai();
    },
    btnHoSo_Click: function (args) {
      if (!this.checkEmptyNumber(this.currentItem.selectedKhieuNai.KHIEUNAI_ID)) {
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.currentItem.selectedKhieuNai.KHIEUNAI_ID;
        this.$refs.frmHoSoKhieuNai.show();
      }
      // this.$root.toastSuccess(
      //   "Chờ form tham chiếu để mở link: Quản lý hồ sơ khiếu nại (Tham chiếu UR4.1.005_Mục 1.1.7)"
      // );
    },
    checkEmptyNumber: function (args) {
      return args == undefined || args == null;
    },
    checkEmptyString: function (args) {
      return args == undefined || args == null || args.toString().trim() == "";
    },
    createID: function () {
      return Math.floor(Math.random() * 10000);
    },
    btnGiaoPH_Click: function (args) {
      this.$refs.modalGiaoPhieuXacMinhPhoiHop.show();
    },
    modalGiaoPhieuXacMinhPhoiHop_Shown: async function (args) {
      await this.$refs.formGiaoPhieuXacMinhPhoiHop.bindData(
        this.currentItem.selectedKhieuNai,
        false //this.nhanTinGiaoPhieuXLKN
      );
    },
    modalGiaoPhieuXacMinhPhoiHop_Hidden: async function (args) {
      await this.loadDsKhieuNai();
    },
    btnKetQuaLoi_Click: function (args) {
      if (this.currentItem.selectedKhieuNai.KETQUAKN_ID == 7) {
        this.$refs.modalNhanVienLoi
          .show(this.currentItem.selectedKhieuNai.KHIEUNAI_ID)
          .then((result) => {
            if (result.ok) {
            } else {
            }
          });
      } else if (this.currentItem.selectedKhieuNai.KETQUAKN_ID == 9) {
        this.$refs.modalDonViLoi
          .show(this.currentItem.selectedKhieuNai.KHIEUNAI_ID)
          .then((result) => {
            if (result.ok) {
            } else {
            }
          });
      }
    },
    btnGiaoPhieu_Click: async function (args) {
      this.$refs.modalGiaoPhieuKhieuNai.show();
      // let hg_id = await this.getHuongGiaoTheoLuong();
      // if(hg_id != null){
      //   await this.$refs.modalGiaoPhieuKhieuNai.bindData();
      //   this.$refs.modalGiaoPhieuKhieuNai.show();
      // }
      // else {
      //   this.$root.toastError(
      //         "Chưa có hướng giao được gán !"
      //       );
      // }
    },
    modalGiaoPhieuKhieuNai_Shown: async function (args) {
      //Bo sung theo yeu cau chi Thuy cho truong KHDN
      // let hgID = this.huongGiaoID;
      // if (this.dsTTKNID == "12"){
      //   //35220 là các tỉnh, còn HNI dúng id = 21056
      //   if(this.$root.token.getPhanVungID() == 21){
      //     hgID = 21056;
      //   }
      //   else {
      //     hgID = 35220;
      //   }
      // }
      let hgID = await this.getHuongGiaoTheoLuong();
      if (hgID != null) {
        await this.$refs.formGiaoPhieuKhieuNai.bindData(
          1,
          hgID.HUONGGIAO_ID,
          this.currentItem.selectedKhieuNai.MA_TB,
          1,
          "0"
        );
      }
      else {
        this.$root.toastError(
          "Chưa có hướng giao được gán !"
        );
      }
    },
    modalGiaoPhieuKhieuNai_Hidden: async function (args) {
      await this.loadDsKhieuNai();
    },
  },
};
</script>
<style>
#modalNhanVienLoi .modal-body,
#modalDonViLoi .modal-body {
  padding: 0px;
}

.grid-stack-box .gutter.gutter-horizontal {
  width: 18px !important;
}

.grid-stack-box .split .split {
  padding: 0px;
}

.split {
  overflow: initial;
}
</style>
