<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import xlsx from "xlsx";
import API from "../api/KhieuNaiGiamTruCuoc";
import frmHoSoKhieuNai from "@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue";

export default {
  data() {
    return {
      uiForm: {
        dsPhieuChua: "Chưa phê duyệt",
        dsPhieuDa: "Đã phê duyệt",
        thongTin: "Thông tin phê Duyệt",
        textButton: "Phê duyệt",
      },
      tabPhieuDaHuy: true,
      maKN: "",
      dsDichVuVT: [],
      dsLoaiPAGC: [],
      dsKieuPAGC: [],
      dsDSLoaiHinhTB: [],
      loaiHinhTBID: 0,
      dichVuVTID: 0,
      khieuNaiID: 0,
      phieuKNID: 0,
      pTTKNID: null,
      chuDeKN: "",
      noiDungDuyet: "",
      noiDungKN: "",

      ngayKN: "",
      dsTienTrinh: [],
      dsThongTinGiamTru: [],
      dsPhieuChuaXL: [],
      dsPhieuDaXL: [],
      phuongAnXL: {
        loai_PAGC: 1,
        kieu_PAGC: 1,
      },
      ketQuaXacMinh: {
        noiDungXacMinh: "",
        ttHopDong: "",
        ttHoaDon: "",
        ttCuoc: "",
      },
      khachHang: {
        ma_kh: "",
        ma_tb: "",
        mst: "",
        ten_kh: "",
        ma_tt: "",
        so_hd: "",
        ngay_hd: "",
        ten_tt: "",
        giatri_hd: 0,
        ma_ngansach: "",
      },
    };
  },
  props: {
    pTTKNID: "",
  },
  components: {
    frmHoSoKhieuNai,
  },
  mounted: async function () {
    this.dsDichVuVT = await this.getDsDichVu();
    this.dsPhieuChuaXL = await this.getDSKhieuNaiTheoTrangThai(this.pTTKNID);
    this.dsPhieuDaXL = await this.getDSKhieuNaiTheoTrangThai(parseInt(this.pTTKNID) + 1);
    this.dsKieuPAGC = await this.getDSKieuPAGC();
    this.dsLoaiPAGC = await this.getDSLoaiPAGC();
    console.log(this.dsPhieuChuaXL);
  },

  created() {
    // this.pTTKNID = this.$route.query.TAG;
    // if (this.pTTKNID == null && this.pTTKNID == undefined) {
    //   this.$toast.error('Link chức năng chưa đúng định dạng');
    // }
    //console.log('khanh',this.pTTKNID)
    if (this.pTTKNID == 17) {
      this.uiForm.dsPhieuChua = "Chưa phê duyệt";
      this.uiForm.dsPhieuDa = "Đã phê duyệt";
      this.uiForm.thongTin = "Thông tin phê Duyệt";
      this.uiForm.textButton = "Phê duyệt";
    }
    if (this.pTTKNID == 18) {
      this.uiForm.dsPhieuChua = "Chưa Thẩm định";
      this.uiForm.dsPhieuDa = "Đã Thẩm định";
      this.uiForm.thongTin = "Thông tin thẩm định";
      this.uiForm.textButton = "Thẩm định";
    }
  },
  methods: {
    maKN_keyPress: async function (event) {
      if (event.code == "Enter") {
        try {
          this.$root.showLoading(true);
          //let madg = this.khachhang.ma_gd == "" ? "0" : this.khachhang.ma_kh;
          ("11378/BZ4/2020/HNI");
          let ma = this.maKN;

          // Object.assign(this.$data, this.initialState());
          await this.getDsKhieuNaiTheoMaKN(ma);
        } catch (e) {
        } finally {
          this.$root.showLoading(false);
        }
      }
    },

    async getDsKhieuNaiTheoMaKN(ma) {
      try {
        const data = await API.getDSKhieuNaiTheoMaKN(this.axios, ma);
        let rs = [];
        this.maKN = ma;
        if (data.data.data.length != 0) rs = data.data.data[0];
        this.dichVuVTID = rs.DICHVUVT_ID;
        this.loaiHinhTBID = rs.LOAITB_ID;
        this.chuDeKN = rs.TENCHUDE;
        this.noiDungKN = rs.ND_KHIEUNAI;
        this.maKN = rs.MA_KN;
        this.ngayKN = rs.NGAY_KN;
        this.khieuNaiID = rs.KHIEUNAI_ID;
        this.ketQuaXacMinh.noiDungXacMinh = rs.ND_XACMINH;
        this.ketQuaXacMinh.ttHopDong = rs.TT_HOPDONG;
        this.ketQuaXacMinh.ttHoaDon = rs.TT_HOADON;
        this.ketQuaXacMinh.ttCuoc = rs.TT_CUOC;
        this.phuongAnXL.loai_PAGC = rs.LOAIPAGC_ID;
        this.phuongAnXL.kieu_PAGC = rs.KIEUPAGC_ID;
        await this.getDanhBa(rs.MA_TB);
        await this.getTienTrinhKhieuNai(this.khieuNaiID);
        await this.getGiamCuocKhieuNai(this.khieuNaiID);
      } catch (error) {
        console.log(error);
      }
    },
    async getTienTrinhKhieuNai(khieuNaiID) {
      try {
        const data = await API.getTienTrinhKhieuNai(this.axios, khieuNaiID);
        let rs = [];
        //this.maKN = "95313/HNI000000/2023/HNI";
        if (data.data.data.length != 0) {
          this.dsTienTrinh = data.data.data;
        }
      } catch (error) {
        console.log(error);
      }
    },
    async getGiamCuocKhieuNai(khieuNaiID) {
      try {
        const data = await API.getGiamCuocKhieuNai(this.axios, khieuNaiID);
        let rs = [];
        //this.maKN = "95313/HNI000000/2023/HNI";
        if (data.data.data.length != 0) {
          this.dsThongTinGiamTru = data.data.data;
        }
      } catch (error) {
        console.log(error);
      }
    },
    async getDSKhieuNaiTheoTrangThai(tTKNID) {
      try {
        const data = await API.getDSKhieuNaiTheoTrangThai(this.axios, tTKNID);
        let rs = [];
        //this.maKN = "95313/HNI000000/2023/HNI";
        if (data.data.data.length != 0) {
          rs = data.data.data;
        }
        return rs;
      } catch (error) {
        return [];
        console.log(error);
      }
    },

    onRowChangedChuaXL: async function (item) {
      try {
        this.$root.showLoading(true);
        this.phieuKNID = item.PHIEUKN_ID;
        await this.getDsKhieuNaiTheoMaKN(item.MA_KN);
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getDanhBa(pMaTB) {
      try {
        const data = await API.getDanhBa(this.axios, pMaTB);
        let rs = [];
        //this.maKN = "95313/HNI000000/2023/HNI";
        if (data.data.data.length != 0) {
          rs = data.data.data[0];
          this.khachHang.ma_kh = rs.MA_KH;
          this.khachHang.ma_tb = rs.MA_TB;
          this.khachHang.ten_kh = rs.TEN_KH;
          this.khachHang.ma_tt = rs.MA_TT;
          this.khachHang.so_hd = rs.MA_HD;
          this.khachHang.ngay_hd = rs.NGAYLAP_HD;
          this.khachHang.ten_tt = rs.TEN_TT;
          this.khachHang.mst = rs.MST_TT;
        }
      } catch (error) {
        console.log(error);
      }
    },
    onSearch: async function () {
      //console.log("lay du lieu");
      //await this.getDsKhieuNaiTheoMaKN();
      try {
        this.$root.showLoading(true);
        this.dsPhieuChuaXL = await this.getDSKhieuNaiTheoTrangThai(this.pTTKNID);
        this.dsPhieuDaXL = await this.getDSKhieuNaiTheoTrangThai(
          parseInt(this.pTTKNID) + 1
        );
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },

    async tsbtnHSKN_Click() {
      try {
        this.$root.showLoading(true);
        // if (this.isEmptyObj(this.phieuKNID)) return
        if (this.khieuNaiID === null) return;
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.khieuNaiID;
        this.$refs.frmHoSoKhieuNai.show();
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDsDichVu: async function () {
      const rs = await API.getDSDichVuVT(this.axios);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      // if (result == null || result == undefined) result = [];
      //   result.splice(0, 0, { dichvuvt_id: 0, ten_dvvt: "(Lựa chọn ...)" });
      return result;
    },

    getDSKieuPAGC: async function () {
      const rs = await API.getKieuPAGC(this.axios);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      // if (result == null || result == undefined) result = [];
      //   result.splice(0, 0, { dichvuvt_id: 0, ten_dvvt: "(Lựa chọn ...)" });
      return result;
    },

    getDSLoaiPAGC: async function () {
      const rs = await API.getLoaiPAGC(this.axios);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      // if (result == null || result == undefined) result = [];
      //   result.splice(0, 0, { dichvuvt_id: 0, ten_dvvt: "(Lựa chọn ...)" });
      return result;
    },
    onTuChoi: async function () {
      try {
        this.$root.showLoading(true);
        if (
          this.noiDungDuyet == null ||
          this.noiDungDuyet == undefined ||
          this.noiDungDuyet == ""
        ) {
          this.$toast.error("Bạn Chưa nhập nội dung");
          return;
        }

        const data = {
          khieuNaiID: this.khieuNaiID,
          phieuKNID: this.phieuKNID,
          trangTKNID: parseInt(this.pTTKNID),
          noiDung: this.noiDungDuyet,
        };
        console.log(data);
        const rs = await API.postTraPhieuGiamCuocKhieuNai(this.axios, data);
        if (rs.data.error_code == "BSS-00000000") {
          this.$toast.success("Từ chối "+this.uiForm.textButton+" thành công");
          await this.onSearch();
        } else this.$toast.error(rs.data.message_detail);
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    onPheDuyet: async function () {
      try {
        this.$root.showLoading(true);
        if (
          this.noiDungDuyet == null ||
          this.noiDungDuyet == undefined ||
          this.noiDungDuyet == ""
        ) {
          this.$toast.error("Bạn Chưa nhập nội dung");
          return;
        }

        const data = {
          khieuNaiID: this.khieuNaiID,
          phieuKNID: this.phieuKNID,
          trangTKNID: parseInt(this.pTTKNID),
          noiDung: this.noiDungDuyet,
        };
        console.log(data);
        const rs = await API.postGiaoPhieuGiamCuocKhieuNai(this.axios, data);
        if (rs.data.error_code == "BSS-00000000") {
          this.$toast.success("Cập nhật thành công");
          await this.onSearch();
        } else this.$toast.error(rs.data.message_detail);
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    getDsLoaiHinhTB: async function (dichvuvt_id) {
      const rs = await API.getDSLoaiHinhTB(this.axios, dichvuvt_id);
      let result = [];
      if (rs.data.data.length != 0) result = rs.data.data;
      // if (result == null || result == undefined) result = [];
      //   result.splice(0, 0, { dichvuvt_id: 0, ten_dvvt: "(Lựa chọn ...)" });
      return result;
    },
  },
  watch: {
    dichVuVTID: async function (newVal, oldVal) {
      console.log(newVal);
      this.dsDSLoaiHinhTB = await this.getDsLoaiHinhTB(newVal);
    },
  },
};
</script>
