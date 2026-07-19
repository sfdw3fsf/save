<template src="./ModalQLBe.html"></template>
<script>
import moment from "moment";
import ModalAnhChup from "../../AnhChup/Modal";
import ModalCsht from "../../Csht/Modal";
import ModalChonDiaChi from "../../ChonDiaChi/Modal";
import ToaDo from "../../ToaDo/Modal";
import ModalChonToaDo from "@/modules/CABMAN/BanDoMangCap/modal/ChonToaDo";
import ModalDUAN from "@/modules/CABMAN/BanDoMangCap/modal/DuAn/Modal";
import ModalNVQLBe from '../../../gan-nhanvien-quanly-cap/Modal'

import {
  minLength,
  maxLength,
  numeric,
  required,
} from "vuelidate/lib/validators";
import { async } from "q";
export default {
  name: "ModalQLBe",
  props: ["modalId", "data"],
  components: {
    ToaDo,
    ModalChonDiaChi,
    ModalCsht,
    ModalAnhChup,
    moment,
    ModalChonToaDo,
    ModalDUAN,
    ModalNVQLBe
  },
  data: function () {
    return {
      fields: { text: "TEN_DV", value: "donViId" },
      dsChuQuan: [],
      dsLoaiBC: [],
      dsViTriDB: [],
      dsHuongTD: [],
      dsLoaiDuong: [],
      dsTaiSan: [],
      dsDuAn: [],
      dsTTSD: [],
      dataSend: {},
      dataSelected: {
        maDuAn: null,
        beCapId: null,
        loaiBeCapId: 1,
        viTriDatBeId: 1,
        huongTamDanId: 1,
        loaiDuongId: 1,
        chuQuanId: 1,
        taiSanId: null,
        duAnId: null,
        trangThaiSuDungId: 1,
        maBC: "",
        tenBC: "",
        kyHieu: "",
        diaChi: "",
        soHuongBe: 1,
        soTamDan: 0,
        chieuDai: 0,
        chieuRong: 0,
        tinhTrangNuoc: "",
        maVach: "",
        ngayLapDat: Date,
        ngaySuDung: Date,
        tuyenCBId: null,
        maTuyenCongBe: null,
        donViId: null,
        kinhDo: null,
        viDo: null,
        maCSHT: null,
        diaChiId: null,
        tinhId: null,
        quanId: null,
        phuongId: null,
        phoId: null,
        apId: null,
        khuId: null,
        dacDiemId: null,
        soNha: "",
        ghiChu: null,
        TEN_NVQL: null,
      },
      idPopupNVQLBe: 'idPopupNVQLBe',
      doiTuongObj: {
        LOAI_DT: 'BE',
        DOITUONG_ID: -1,
      },
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        lienket: 0,
        kiemke: 0,
        chuquan: 0,
        bando: 0,
        macsht: 0,
        ngaythang: 0,
      },
      waterMark: "Chọn ngày",
      dateVal: new Date(),
      dateFormat: "dd/MM/yyyy",
      donviql: [],
      btnGhiLai_Enable: true,
      btnAnhChup_Enable: true,
    };
  },
  validations: {
    dataSelected: {
      kinhDo: {
        required,
      },
      viDo: {
        required,
      },
      trangThaiSuDungId: {
        required,
      },
      soHuongBe: {
        required,
      },
      diaChi: {
        required,
      },
      tenBC: {
        required,
      },
      kyHieu: {
        required,
      },
      loaiDuongId: {
        required,
      },
      huongTamDanId: {
        required,
      },
      viTriDatBeId: {
        required,
      },
      loaiBeCapId: {
        required,
      },
    },
  },
  created: async function () {},
  watch: {
    "dataSelected.duAnId"(val) {
      console.log("🚀 ~ file: ModalQLBe.vue:128 ~ val:", val);
      if (val !== null) {
        this.$root.context
          .get("/web-cabman/ban-do-mang-cap/danh-muc", {
            loai: "TAISAN",
            option: val,
          })
          .then((response) => {
            console.log("🚀 ~ file: ModalQLBe.vue:132 ~ response:", response);
            this.dsTaiSan = response.data;
          });
      }
    },
  },
  computed: {
    objToaDo: function () {
      return {
        diachi: this.dataSelected.diaChi,
        toado: this.dataSelected.viDo + "," + this.dataSelected.kinhDo,
      };
    },
    getPosition() {
      return {
        lat: parseFloat(this.dataSelected.viDo),
        lng: parseFloat(this.dataSelected.kinhDo),
      };
    },
    getDiachi() {
      return this.dataSelected.diaChi;
    },
  },
  methods: {
    modalNVQLBeShow(){

    },
    closeModalNVQLBe() {
      this.chiTietBeCap()
    },
    showModalNVQLBe() {
      this.$bvModal.show(this.idPopupNVQLBe)
    },
    lowercaseKeys(obj) {
      return Object.keys(obj).reduce((accumulator, key) => {
        accumulator[key.toLowerCase()] = obj[key];
        return accumulator;
      }, {});
    },
    getDUAN: function (args) {
      console.log("🚀 ~ file: ModalQLBe.vue:154 ~ args:", args);
      this.dataSelected.duAnId = args.duan_id;
      this.dataSelected.maDuAn = args.ma_duan;
    },
    focusMyElement: async function () {
      try {
        this.$root.showLoading(true);
        await this.initForm();
        if (this.data.readOnly != null) {
          this.btnGhiLai_Enable = !this.data.readOnly;
          this.btnAnhChup_Enable = !this.data.readOnly;
        }
        if (this.data.beCapId != null) {
          this.dataSelected.beCapId = this.data.beCapId;
          this.dataSend.beCapId = this.data.beCapId;
          if (this.data.tuyenCongId != null) {
            this.dataSelected.tuyenCBId = this.data.tuyenCongId;
          }
          if (this.data.dvql != null) {
            this.donviql = this.data.dvql;
            this.dataSelected.donViId = this.donviql.id;
          }
          if (this.dataSelected.beCapId != null) {
            await this.chiTietBeCap();
          }
        } else {
          this.donviql = this.data.dvql;
          this.dataSelected.beCapId = null;
          this.dataSelected.donViId = this.donviql.id;
          this.dataSelected.tuyenCBId = this.data.tuyenCongId;
          // this.dataSelected.loaiBeCapId = null
          // this.dataSelected.viTriDatBeId = null
          // this.dataSelected.huongTamDanId = null
          // this.dataSelected.loaiDuongId = null
          this.dataSelected.chuQuanId = null;
          this.dataSelected.taiSanId = null;
          this.dataSelected.duAnId = null;
          this.dataSelected.maDuAn = null;
          // this.dataSelected.trangThaiSuDungId = null
          this.dataSelected.maBC = null;
          this.dataSelected.tenBC = null;
          this.dataSelected.kyHieu = null;
          this.dataSelected.kyHieu = null;
          this.dataSelected.diaChi = null;
          this.dataSelected.soHuongBe = 1;
          this.dataSelected.soTamDan = 0;
          this.dataSelected.chieuDai = 0;
          this.dataSelected.chieuRong = 0;
          this.dataSelected.tinhTrangNuoc = null;
          this.dataSelected.maVach = null;
          this.dataSelected.ngayLapDat = new Date();
          this.dataSelected.ngaySuDung = new Date();
          this.dataSelected.kinhDo = 0;
          this.dataSelected.viDo = 0;
          this.dataSelected.maCSHT = null;
          this.dataSelected.diaChiId = null;

          this.dataSelected.tinhId = null;
          this.dataSelected.quanId = null;
          this.dataSelected.phuongId = null;
          this.dataSelected.phoId = null;
          this.dataSelected.apId = null;
          this.dataSelected.khuId = null;
          this.dataSelected.dacDiemId = null;
          this.dataSelected.soNha = null;
        }
        if (this.dataSelected.tuyenCBId) {
          let tuyenCongBe = await this.getMaTuyenCongBe(
            this.dataSelected.tuyenCBId
          );
          if (tuyenCongBe.data) {
            this.dataSelected.maTuyenCongBe = tuyenCongBe.data.MA_TCB;
          }
        }
      } catch (er) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getMaTuyenCongBe: async function (tuyenCongBeId) {
      return await this.$root.context.get(
        "/web-cabman/BanDoTuyenCong/getMaTuyenCongBe",
        {
          tuyenCongBeId: tuyenCongBeId,
        }
      );
    },
    chiTietBeCap: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/chi-tiet-be-cap?beCapId=" +
            this.dataSelected.beCapId
        );
        if (rs.data != null) {
          await this.mapDataBeCap(rs.data);
        }
      } catch (error) {
      } finally {
      }
    },
    xoaMaDUAN() {},
    XacNhanToaDo(position) {
      console.log(
        "🚀 ~ file: ModalQLBe.vue:231 ~ XacNhanToaDo ~ position:",
        position
      );
      this.dataSelected.viDo = position.lat;
      this.dataSelected.kinhDo = position.lng;
    },
    initForm: async function () {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/lay-cac-danh-muc-be-cap-v2"
        );
        if (rs.data != null) {
          this.dsChuQuan = rs.data.dsChuQuan;
          this.dsLoaiBC = rs.data.dsLoaiBC;
          this.dataSelected.loaiBeCapId = this.dsLoaiBC[0].LOAIBC_ID;
          this.dsViTriDB = rs.data.dsViTriDB;
          this.dataSelected.viTriDatBeId = this.dsViTriDB[0].VITRIDB_ID;
          this.dsHuongTD = rs.data.dsHuongTD;
          this.dataSelected.huongTamDanId = this.dsHuongTD[0].HUONGTD_ID;
          this.dsLoaiDuong = rs.data.dsLoaiDuong;
          this.dataSelected.loaiDuongId = this.dsLoaiDuong[0].LOAIDUONG_ID;
          this.dsTTSD = rs.data.dsTTSD;
          this.dataSelected.trangThaiSuDungId = this.dsTTSD[0].TTSD_ID;
          this.dsDuAn = [];
          this.dsTaiSan = [];
          this.dataSelected.duAnId = null;
          this.dataSelected.maDuAn = null;
        }
      } catch (error) {
      } finally {
      }
    },

    mapDataBeCap: async function (data) {
      try {
        if (data != null) {
          this.doiTuongObj.DOITUONG_ID = data.BECAP_ID;
          this.dataSelected.TEN_NVQL = data.DS_NHANVIEN_QL;
          this.dataSend.tenBC = data.TEN_BC;
          this.dataSend.tenBeCap = data.TEN_BC;
          this.dataSelected.loaiBeCapId = data.LOAIBC_ID;
          this.dataSelected.viTriDatBeId = data.VITRIDB_ID;
          this.dataSelected.huongTamDanId = data.HUONGTD_ID;
          this.dataSelected.loaiDuongId = data.LOAIDUONG_ID;
          this.dataSelected.chuQuanId = data.CHUQUAN_ID;
          this.dataSelected.taiSanId = data.TAISAN_ID
            ? parseInt(data.TAISAN_ID.toString(), 10)
            : null;
          this.dataSelected.duAnId = data.DUAN_ID
            ? parseInt(data.DUAN_ID.toString(), 10)
            : null;
          if (this.dataSelected.duAnId !== null) {
            let rs = await this.$root.context.get(
              "/web-cabman/ban-do-mang-cap/danh-muc",
              { loai: "DUAN_CT", option: this.dataSelected.duAnId }
            );
            if (rs && rs.data.length > 0) {
              this.dataSelected.maDuAn = rs.data[0].MA_CT2;
            }
            rs = await this.$root.context.get(
              "/web-cabman/ban-do-mang-cap/danh-muc",
              { loai: "TAISAN", option: this.dataSelected.duAnId }
            );
            if (rs && rs.data.length > 0) {
              this.dsTaiSan = rs.data;
            } else {
              this.dsTaiSan = [];
            }
          }
          this.dataSelected.trangThaiSuDungId = data.TTSD_ID;
          this.dataSelected.maBC = data.MA_BC;
          this.dataSelected.tenBC = data.TEN_BC;
          this.dataSelected.kyHieu = data.KYHIEU;
          this.dataSelected.kyHieu = data.KYHIEU;
          this.dataSelected.diaChi = data.DIACHI;
          this.dataSelected.soHuongBe = data.SOHUONGBE;
          this.dataSelected.soTamDan = data.SOTAMDAN;
          this.dataSelected.chieuDai = data.CHIEUDAI;
          this.dataSelected.chieuRong = data.CHIEURONG;
          this.dataSelected.tinhTrangNuoc = data.TT_NUOC;
          this.dataSelected.maVach = data.MA_VACH;
          this.dataSelected.ngayLapDat = data.NGAY_LD;
          this.dataSelected.ngaySuDung = data.NGAY_SD;
          this.dataSelected.kinhDo = data.KINHDO;
          this.dataSelected.viDo = data.VIDO;
          this.dataSelected.maCSHT = data.MA_CSHT;
          this.dataSelected.ghiChu = data.GHICHU;
          this.dataSelected.diaChiId = data.DIACHI_ID;

          this.dataSelected.tinhId = data.TINH_ID;
          this.dataSelected.quanId = data.QUAN_ID;
          this.dataSelected.phuongId = data.PHUONG_ID;
          this.dataSelected.phoId = data.PHO_ID;
          this.dataSelected.apId = data.AP_ID;
          this.dataSelected.khuId = data.KHU_ID;
          this.dataSelected.dacDiemId = data.DACDIEM_ID;
          this.dataSelected.soNha = data.SONHA;
        }
      } catch (error) {
        console.log("🚀 ~ file: ModalQLBe.vue:314 ~ error:", error);
      }
    },

    getDiaChi: function (data) {
      console.log("🚀 ~ file: ModalQLBe.vue:456 ~ data:", data);
      this.dataSelected.diaChi = data.diachimoi;
      this.dataSelected.tinhId = data.tinh_id;
      this.dataSelected.quanId = data.quan_id;
      this.dataSelected.phuongId = data.phuong_id;
      this.dataSelected.phoId = data.pho_id;
      this.dataSelected.apId = data.ap_id;
      this.dataSelected.khuId = data.khu_id;
      this.dataSelected.dacDiemId = data.dacdiem_id;
      this.dataSelected.soNha = data.so_nha;
    },

    getToaDo: function (toado) {
      if (toado != null) {
        this.dataSelected.viDo = toado.split(",")[0];
        this.dataSelected.kinhDo = toado.split(",")[1];
      }
    },

    ghiLai: async function () {
      if (this.dataSelected.beCapId != null) {
        this.capNhat();
      } else {
        this.themMoi();
      }
    },
    capNhat: async function () {
      try {
        this.$root.showLoading(true);
        if (this.validateData(this.dataSelected)) {
          return;
        }
        // console.log(this.dataSelected)
        this.dataSelected.ngayLapDat = moment(
          this.dataSelected.ngayLapDat,
          "yyyy-MM-dd"
        ).toDate();
        this.dataSelected.ngaySuDung = moment(
          this.dataSelected.ngaySuDung,
          "yyyy-MM-dd"
        ).toDate();

        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/cap-nhat-be-cap-v2",
          this.dataSelected
        );
        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Cập nhật bể cáp thành công");
          let obj = {
            beId: this.dataSelected.beCapId,
          };
          this.$emit("xacNhan", obj);
          this.$bvModal.hide(this.modalId);
        } else {
          this.$toast.error("Cập nhật bể cáp thất bại");
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
        this.$toast.error("Cập nhật bể cáp thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    themMoi: async function () {
      try {
        this.$root.showLoading(true);
        // console.log(this.dataSelected)
        // this.dataSelected.ngayLapDat= this.convertDate(this.dataSelected.ngayLapDat);
        // this.dataSelected.ngaySuDung= this.convertDate(this.dataSelected.ngaySuDung);
        if (this.validateData(this.dataSelected)) {
          return;
        }
        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/them-be-cap-v2",
          this.dataSelected
        );
        if (rs.error_code === "BSS-00000000") {
          this.$toast.success("Thêm bể cáp thành công");
          this.dataSelected.beCapId = rs.data.BECAP_ID;
          let obj = {
            beId: this.dataSelected.beCapId,
          };
          this.$emit("xacNhan", obj);
          this.$bvModal.hide(this.modalId);
        } else {
          this.$toast.error("Thêm bể cáp thất bại");
        }
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
        this.$toast.error("Thêm bể cáp thất bại");
      } finally {
        this.$root.showLoading(false);
      }
    },
    convertDate: function (date) {
      return (
        new Date(date).toLocaleDateString("en-US", { day: "numeric" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { month: "numeric" }) +
        "/" +
        new Date(date).toLocaleDateString("en-US", { year: "numeric" })
      );
    },
    xoaMaCSHT: function () {
      this.dataSelected.maCSHT = "";
    },
    xoaMaTS: function () {
      this.dataSelected.taiSanId = null;
    },
    xoaMaDA: function () {
      this.dataSelected.maDuAn = null;
      this.dataSelected.duAnId = null;
    },
    getMaCsht: function (args) {
      this.dataSelected.maCSHT = args;
    },
    validateData(data) {
      this.$v.$touch();
      let flag = false;
      let errorMessage = [];

      if (data.kyHieu == null || data.kyHieu == "") {
        errorMessage.push("Ký hiệu không được trống.");
        if (!flag) {
          this.$refs["ky_hieu"].focus();
        }
        flag = true;
      }

      if (data.tenBC == null || data.tenBC == "") {
        errorMessage.push("Tên bể cáp không được trống.");
        if (!flag) {
          this.$refs["ten_be_cap"].focus();
        }
        flag = true;
      }

      if (data.diaChi == null || data.diaChi == "") {
        errorMessage.push("Địa chỉ không được trống.");
        if (!flag) {
          this.$refs["dia_chi"].focus();
        }
        flag = true;
      }

      if (data.soHuongBe == null || data.soHuongBe == "") {
        errorMessage.push("Số hướng bể không được trống.");
        if (!flag) {
          this.$refs["so_huong_be"].focus();
        }
        flag = true;
      }

      if (data.kinhDo == null || data.kinhDo == "") {
        errorMessage.push("Kinh độ không được trống.");
        if (!flag) {
          this.$refs["kinh_do"].focus();
        }
        flag = true;
      }

      if (data.viDo == null || data.viDo == "") {
        errorMessage.push("Vĩ độ không được trống.");
        if (!flag) {
          this.$refs["vi_do"].focus();
        }
        flag = true;
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join("\n"));
        return true;
      } else {
        return false;
      }
    },
  },
  mounted: function () {
    // document.addEventListener('click',function(event){
    //     console.log(event.target);
    // })
  },
};
</script>
<style>
div#popupQLBe___BV_modal_outer_ {
  z-index: 1050 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 1040 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>
