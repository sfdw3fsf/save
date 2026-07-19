<template src="./ModalQLDoanCong.html"></template>
<script>
import moment from "moment";
import ModalTreocap from "../../ChuiCapQuaOngCong/Modal";
import ModalAnhChup from "../../AnhChup/Modal";
import ModalNVQLDoanCong from '../../../gan-nhanvien-quanly-cap/Modal'
import ModalChonDiaChi from "../../ChonDiaChi/Modal";
import {
  minLength,
  maxLength,
  numeric,
  required,
} from "vuelidate/lib/validators";

export default {
  name: "ModalQLDoanCong",
  props: ["modalId", "data"],
  components: { ModalTreocap, ModalAnhChup, moment, ModalNVQLDoanCong, ModalChonDiaChi },
  data: function () {
    return {
      fields: { text: "TEN_DV", value: "donViId" },
      // cotid: 0,
      dsChuQuan: [],
      dsLoaiCong: [],
      dsTaiSan: [],
      dsDuAn: [],
      dsTTSD: [],
      dsHuongBeA: [],
      dsHuongBeB: [],
      disabledClass: "",
      idPopupNVQLDoanCong: 'idPopupNVQLDoanCong',
      modalNVQLDoanCongShow: false,
      doiTuongNVQLDoanCong: {
        DOITUONG_ID: -1,
        LOAI_DT: 'CONG'
      },
      dataSelected: {
        doanCongId: null,
        loaiCongId: 1,
        chuQuanId: 1,
        taiSanId: null,
        duAnId: null,
        trangThaiSuDungId: 1,
        maDoanCong: "",
        tenDoanCong: "",
        kyHieu: "",
        soDongMatCat: 1,
        soCotMatCat: 1,
        soOngCong: 1,
        matCat: "1x2",
        duongKinhOngCong: 1,
        chieuDai: 0,
        doSau: 0,
        soCapDiQua: 0,
        beCapA: "",
        beCapAId: null,
        beCapB: "",
        beCapBId: null,
        tenhuongBeA: null,
        tenhuongBeB: null,
        ngayLapDat: Date,
        ngaySuDung: Date,
        tuyenCongBeId: null,
        donViQlId: null,
        ghiChu: "",
        ds_nhanvien_ql:null
      },
      category: {
        phanloai: 0,
        dinhdanh: 0,
        dactinh: 0,
        boTri: 0,
        ketNoiA: 0,
        ketNoiB: 0,
        lienket: 0,
        kiemke: 0,
        chuquan: 0,
        khac: 0,
        ngaythang: 0,
      },
      waterMark: "Chọn ngày",
      dateVal: new Date(),
      dateFormat: "dd/MM/yyyy",
      donviql: [],
      btnGhiLai_Enable: true,
    };
  },
  validations: {
    dataSelected: {
      tenDoanCong: {
        required,
      },
      kyHieu: {
        required,
      },
    },
  },
  created: async function () {},
  watch: {
    async "dataSelected.duAnId"(val) {
      console.log("🚀 ~ file: ModalQLDoanCong.vue:88 ~ val:", val);
      let rs = await this.$root.context.get(
        "/web-cabman/ban-do-mang-cap/danh-muc",
        { loai: "TAISAN", option: val === null ? "" : val }
      );
      console.log("🚀 ~ file: ModalQLDoanCongVaOngCong.vue:125 ~ rs:", rs);
      if (rs.data != null) {
        this.dsTaiSan = rs.data;
      } else {
        this.dsTaiSan = [];
      }
    },
  },
  computed: {
    dataSend () {
      return {
        DOANCONG_ID: this.dataSelected.doanCongId }
    },
  },
  methods: {
    getDiaChi: function (data) {
      this.dataSelected.diaChi = data.diaChi;
      this.dataSelected.toaDo = data.toaDo;
    },
    resetModal() {
      this.dataSelected.taiSanId = null;
      this.dataSelected.duAnId = null;
      this.dataSelected.maDoanCong = null;
      this.dataSelected.tenDoanCong = null;
      this.dataSelected.kyHieu = null;
      this.dataSelected.soDongMatCat = 1;
      this.dataSelected.soCotMatCat = 1;
      this.dataSelected.soOngCong = 1;
      this.dataSelected.matCat = "1x2";
      this.dataSelected.duongKinhOngCong = 1;
      this.dataSelected.chieuDai = 0;
      this.dataSelected.doSau = 0;
      this.dataSelected.soCapDiQua = 0;
      this.dataSelected.ghiChu = null;
    },
    focusMyElement: async function () {
      try {
        this.$root.showLoading(true);
        await this.initForm();
        if (this.data != null) {
          if (this.data.readOnly != null) {
            this.btnGhiLai_Enable = !this.data.readOnly;
          }
          if (this.data.doanCongId != null) {
            this.dataSelected.doanCongId = this.data.doanCongId;
            await this.getChiTietDoanCong(this.dataSelected.doanCongId);
          }

          if (this.data.dataBeA != null) {
            this.dataSelected.beCapA = this.data.dataBeA.KYHIEU;
            this.dataSelected.beCapAId = this.data.dataBeA.BECAP_ID;
            await this.getHuongBe(this.dataSelected.beCapAId, 1);
          }
          if (this.data.dataBeB != null) {
            this.dataSelected.beCapB = this.data.dataBeB.KYHIEU;
            this.dataSelected.beCapBId = this.data.dataBeB.BECAP_ID;
            await this.getHuongBe(this.dataSelected.beCapBId, 2);
          }
          if (this.data.tuyenCongId != null) {
            this.dataSelected.tuyenCongBeId = this.data.tuyenCongId;
          }
          if (this.data.dvql != null) {
            this.donviql = this.data.dvql;
            this.dataSelected.donViQlId = this.donviql.id;
          }
        }
      } catch (er) {
      } finally {
        this.$root.showLoading(false);
      }
    },

    getHuongBe: async function (beCapId, loai) {
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/danh-sach-huong-be?beCapId=" + beCapId
        );
        if (rs.data != null) {
          switch (loai) {
            case 1:
              this.dsHuongBeA = rs.data;
              this.dataSelected.huongBeA = this.dsHuongBeA[0].HUONG;
              break;
            case 2:
              this.dsHuongBeB = rs.data;
              this.dataSelected.huongBeB = this.dsHuongBeB[0].HUONG;
              break;
          }
        }
      } catch (error) {
      } finally {
      }
    },

    getChiTietDoanCong: async function (doanCongId) {
      if (doanCongId === null || doanCongId === 0) return;
      try {
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/chi-tiet-doan-cong?doanCongId=" +
            doanCongId
        );
        if (rs.data != null) {
          this.dataSelected.loaiCongId = rs.data.LOAICONG_ID;
          this.dataSelected.maDoanCong = rs.data.MA_DC;
          this.dataSelected.kyHieu = rs.data.KYHIEU;
          this.dataSelected.tenDoanCong = rs.data.TEN_DC;
          this.dataSelected.soDongMatCat = rs.data.SODONG_MC;
          this.dataSelected.soCotMatCat = rs.data.SOCOT_MC;
          this.dataSelected.matCat = rs.data.MATCAT;
          this.dataSelected.soOngCong = rs.data.SOONGCONG;
          this.dataSelected.duongKinhOngCong = rs.data.DUONGKINH_OC;
          this.dataSelected.chieuDai = rs.data.CHIEUDAI;
          this.dataSelected.doSau = rs.data.DOSAU;
          this.dataSelected.trangThaiSuDungId = rs.data.TTSD_ID;
          this.dataSelected.soCapDiQua = rs.data.SOCAP;
          this.dataSelected.beCapA = rs.data.BECAP_A;
          this.dataSelected.huongBeA = rs.data.HUONGBE_A;
          this.dataSelected.beCapB = rs.data.BECAP_B;
          this.dataSelected.huongBeB = rs.data.HUONGBE_B;
          this.dataSelected.ngayLapDat = moment(
            rs.data.NGAY_LD1,
            "DD/MM/YYYY"
          ).toDate();
          this.dataSelected.ngaySuDung = moment(
            rs.data.NGAY_SD1,
            "DD/MM/YYYY"
          ).toDate();
          this.dataSelected.tuyenCongBeId = rs.data.TUYENCB_ID;
          this.dataSelected.duAnId = rs.data.DUAN_ID;
          this.dataSelected.taiSanId = rs.data.TAISAN_ID;
          this.dataSelected.chuQuanId = rs.data.CHUQUAN_ID;
          this.dataSelected.donViQlId = rs.data.DONVI_ID;
          this.dataSelected.ghiChu = rs.data.GHICHU;
        }
      } catch (error) {
        console.log("🚀 ~ file: ModalQLDoanCong.vue:190 ~ error:", error);
      } finally {
      }
    },

    showNVQLDoanCong () {
      console.log('ẤDFASDFDA')
      if (this.dataSelected.doanCongIdId !== null) {
        try{
          console.log('AAAAAAAAAAAA')
          
        this.doiTuongNVQLDoanCong.DOITUONG_ID = this.dataSelected.doanCongId
        console.log(this.doiTuongNVQLDoanCong.DOITUONG_ID)
        this.doiTuongNVQLDoanCong.LOAI_DT = 'CONG'
                console.log('test')
        this.$bvModal.show(this.idPopupNVQLDoanCong)
        console.log('lllllllll')
        }
        catch(error){
          console.log(error.message)
        }
      } else {
        this.$alert('Cống chưa tồn tại, chưa thể gán nhân viên quản lý. Hãy nhấn nút <b>Ghi lại</b> để lưu thông tin cáp mới vào dữ liệu trước khi thực hiện chức năng gán nhân viên quản lý.', 'Nhân viên quản lý cột', {
          confirmButtonText: 'Đóng',
          dangerouslyUseHTMLString: true
        })
      }
    },
    onTREO_CAP () { alert('onTREO_CAP') },
    closeModalNVQLDoanCong: function () {
      this.modalNVQLDoanCongShow = !this.modalNVQLDoanCongShow

    },
    show (data) {
      return new Promise((resolve, reject) => {
        this.data = data
        this.$refs.modalBeDoanCong.show();
        //this.$bvModal.show(this.modalID)
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },

    initForm: async function () {
      try {
        this.dataSelected.taiSanId = null;
        this.dataSelected.duAnId = null;
        this.dataSelected.maDoanCong = null;
        this.dataSelected.tenDoanCong = null;
        this.dataSelected.kyHieu = null;
        this.dataSelected.soDongMatCat = 1;
        this.dataSelected.soCotMatCat = 1;
        this.dataSelected.soOngCong = 1;
        this.dataSelected.matCat = "1x2";
        this.dataSelected.duongKinhOngCong = 1;
        this.dataSelected.chieuDai = 0;
        this.dataSelected.doSau = 0;
        this.dataSelected.soCapDiQua = 0;
        this.dataSelected.ghiChu = null;
        let rs = await this.$root.context.get(
          "/web-cabman/BanDoTuyenCong/lay-cac-danh-muc-doan-cong"
        );
        if (rs.data != null) {
          this.dsChuQuan = rs.data.dsChuQuan;
          this.dsLoaiCong = rs.data.dsLoaiCong;
          this.dataSelected.loaiCongId = this.dsLoaiCong[0].LOAICONG_ID;
          this.dsTTSD = rs.data.dsTTSD;
          this.dataSelected.trangThaiSuDungId = this.dsTTSD[0].TTSD_ID;
          this.dsDuAn = rs.data.dsDuAn;
        }
      } catch (error) {
        console.log("🚀 ~ file: ModalQLDoanCong.vue:223 ~ error:", error);
      } finally {
      }
    },

    ghiLai: async function () {
      this.themMoi();
    },

    themMoi: async function () {
      try {
        this.$root.showLoading(true);
        if (this.validateData(this.dataSelected)) {
          return;
        }

        let rs = await this.$root.context.post(
          "/web-cabman/BanDoTuyenCong/them-doan-cong",
          this.dataSelected
        );
        if (rs.error_code == "BSS-00000000") {
          this.$toast.success("Thêm đoạn cống thành công");
          this.$emit("xacNhan");
          this.$bvModal.hide("popupQLDoanCong");
        } else {
          this.$toast.error(rs.message_detail);
        }
        this.$root.showLoading(false);
      } catch (error) {
        this.$toast.error(error.response.data.message_detail);
      } finally {
        this.$root.showLoading(false);
      }
    },
    xoaMaTS: function () {
      this.dataSelected.taiSanId = "";
    },
    xoaMaDA: function () {
      this.dataSelected.duAnId = "";
    },
    validateData(data) {
      this.$v.$touch();
      let flag = false;
      let errorMessage = [];

      if (data.kyHieu == null || data.kyHieu == "") {
        errorMessage.push("Ký hiệu không được trống.");
        if (!flag) {
          this.$refs["kyHieu"].focus();
        }
        flag = true;
      }

      if (data.tenDoanCong == null || data.tenDoanCong == "") {
        errorMessage.push("Tên đoạn cống không được trống.");
        if (!flag) {
          this.$refs["tenDoanCong"].focus();
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
  mounted: function () {},
};
</script>
<style>
div#popupQLBe___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
.modal-fullscreen .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  display: flex;
  position: fixed;
  z-index: 100000;
}
a.disabled {
  color: lightgrey !important;
  pointer-events: none;
  cursor: default;
  text-decoration: none;
}
</style>
