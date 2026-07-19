<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import moment from "moment";
import API from "../api/ThietLapTBC";
import DiemTN from "./components/diemtn-tbc";
import TienNoTBC from "./components/tienno-tbc";
import LoaiTinTBC from "./components/loaitin-tbc";

export default {
  components: {
    DiemTN,
    TienNoTBC,
    LoaiTinTBC,
  },
  data() {
    return {
      dsSTK: [],
      editSettings: { allowEditing: true, mode: "Batch" },
      dsGioTH: [],
      ttkd: "",
      diaChiTTKD: "",
      linkTT: "",
      linkMyVNPT: "",
      linkChiTiet: "",
      idDonVi: "",
      dsDonVi: [],
      idLoaiYC: "",
      dsLoaiYC: [],
      idDiemTN: "",
      dsDiemTN: [],
      idTienNo: "",
      dsTienNo: [],
      ngayTH: "",
      denNgayTH: "",
      ngayHH: "",
      sdt: "",
      dsThoiGianDaThietLap: [],
      cauhinh_tbc_id: -1,
      sdt_tbc: "",
      enabledNgayHH: true,
      isTab: 0,
      kieuKhoa: 1
    };
  },
  watch: {
    idDonVi: {
      handler: async function (value) {
        if (value) this.fetchDSCauHinhTBCV2(value);
      },
    },
    idLoaiYC: {
      handler: async function (value) {
        if (!value || value == -1) return;
        const result = await this.checkMapIHH(value);
        const kieu_hh = parseInt(result);
        if (kieu_hh == 1) {
          this.enabledNgayHH = true;
        } else {
          this.enabledNgayHH = false;
          this.ngayHH = "";
        }
      },
    },
  },
  async created() {
    this.fetchDSLoaiTin();
    this.fetchDSDiemTN();
    this.fetchDSTienNo();
    await this.fetchDSDonVi();
    this.onLoadForm();
  },
  mounted() {},
  methods: {
    changeTab(val) {
      this.isTab = val;
    },
    onLoadForm() {
      this.fetchLayTTCauHinhChungTBC("TEN_TTKD_TBC");
      this.fetchLayTTCauHinhChungTBC("DIACHI_TTKD_TBC");
      this.fetchLayTTCauHinhChungTBC("SODT_TTKD_TBC");
      this.fetchLayTTCauHinhChungTBC("LINK_PORTAL_TTKD_TBC");
      this.fetchLayTTCauHinhChungTBC("LINK_MYVNPT_TTKD_TBC");
      this.fetchLayTTCauHinhChungTBC("LINK_CHITIET_TTKD_TBC");
      if (this.ttkd.trim() == "") {
        //   const tt_nd = await this.$root.token.getThongTinNguoiDung();
        //         console.log("first123", tt_nd);
      }
      this.fetchDSCauHinhTBCV2(this.idDonVi);
      this.napDSSTK(this.$auth.getDonViID());
    },
    async fetchDSLoaiTin() {
      const response = await API.getDSDanhMuc(this.axios, "LOAITIN_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.dsLoaiYC = response.data.data;
      }
    },
    async fetchDSDiemTN() {
      const response = await API.getDSDanhMuc(this.axios, "DIEMTN_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.dsDiemTN = response.data.data;
      }
    },
    async fetchDSTienNo() {
      const response = await API.getDSDanhMuc(this.axios, "TIENNO_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.dsTienNo = response.data.data;
      }
    },
    async fetchDSDonVi() {
      const response = await API.getDSDonVi(this.axios);
      if (response.data.error_code === "BSS-00000000") {
        this.idDonVi = response.data.data[0].DONVI_ID;
        this.dsDonVi = response.data.data;
      }
    },
    async fetchLayTTCauHinhChungTBC(val) {
      const param = {
        mats: val,
      };
      const response = await API.getTTCauHinhChungTBC(this.axios, param);
      if (response.data.error_code === "BSS-00000000") {
        if (val == "TEN_TTKD_TBC") {
          this.ttkd = response.data.data[0].VALUE;
        }
        if (val == "DIACHI_TTKD_TBC") {
          this.diaChiTTKD = response.data.data[0].VALUE;
        }
        if (val == "SODT_TTKD_TBC") {
          this.sdt = response.data.data[0].VALUE;
        }
        if (val == "LINK_PORTAL_TTKD_TBC") {
          this.linkTT = response.data.data[0].VALUE;
        }
        if (val == "LINK_MYVNPT_TTKD_TBC") {
          this.linkMyVNPT = response.data.data[0].VALUE;
        }
        if (val == "LINK_CHITIET_TTKD_TBC") {
          this.linkChiTiet = response.data.data[0].VALUE;
        }
      }
    },
    async fetchDSCauHinhTBCV2(donvi_id) {
      try {
        //   this.loading(true);
        const parrams = {
          donViId: donvi_id,
        };
        const response = await API.getLayTTCauHinhTBC_v2(this.axios, parrams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsThoiGianDaThietLap = response.data.data;
        } else {
          this.dsThoiGianDaThietLap = [];
          this.dsGioTH = [];
        }
      } catch (error) {
        this.dsThoiGianDaThietLap = [];
        //   this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async napDSSTK(idDonVi) {
      try {
        this.loading(true);
        const data = {
          donViId: idDonVi,
        };
        const response = await API.getDSSTK(this.axios);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsSTK = response.data.data.map((item, index) => ({
            ...item,
            STT: index,
          }));
        } else {
          this.dsSTK = [];
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsSTK = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchLayTTCauHinhChung() {
      //    const response = await API.getDSDanhMuc(this.axios, "LOAITIN_TBC");
      //   if (response.data.error_code === "BSS-00000000") {
      //     this.dsLoaiYC = response.data.data;
      //   }
    },
    async onClickLayTT() {
      this.onLoadForm();
    },
    onClickNhapMoi() {
      //   this.idLoaiYC = this.dsLoaiYC[0].LOAITIN_ID;
      //   this.idDiemTN = this.dsDiemTN[0].DIEMTN_TBC_ID;
      //   this.idTienNo = this.dsTienNo[0].TIENNO_TBC_ID;
      this.idLoaiYC = -1;
      this.idDiemTN = -1;
      this.idTienNo = -1;

      this.ngayTH = "";
      this.denNgayTH = "";
      this.dsGioTH = [];
      this.cauhinh_tbc_id = -1;
      this.enabledNgayHH = true;
    },
    async onClickCapNhat() {
      if (this.enabledNgayHH && this.ngayHH.trim() == "") {
        this.$root.toastError("Hãy nhập ngày hết hạn!");
        this.$refs.ngayHHRef.focus();
        return;
      }
      if (this.enabledNgayHH && (this.ngayHH <= 0 || this.ngayHH > 31)) {
        this.$root.toastError("Ngày hết hạn phải lớn hơn 0 và nhỏ hơn 31!");
        this.$refs.ngayHHRef.focus();
        return;
      }
      await this.fetchCapNhatTTChungV3();
      await this.fetchCapNhatCauHinhTBC_v4();
      await this.fetchDSCauHinhTBCV2(this.idDonVi);
    },
    async fetchCapNhatTTChungV3() {
      try {
        this.loading(true);
        const ds_stk = this.dsSTK.map((item) => ({ SO_TK: item.SO_TK, NGANHANG: item.NGANHANG }));
        const data = {
          tenTTKD: this.ttkd.trim(),
          diaChiTTKD: this.diaChiTTKD.trim(),
          sodtTTKD: this.sdt.trim(),
          portalTTKD: this.linkTT.trim(),
          myvnptTTKD: this.linkMyVNPT.trim(),
          donViId: this.idDonVi,
          linhctTTKD: this.linkChiTiet.trim(),
          dsSTK: ds_stk,
        };
        const response = await API.capNhatTTTBC_v3(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật số tài khoản thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async fetchCapNhatCauHinhTBC_v4() {
      let ngaygui = this.ngayTH.trim();
      if (this.denNgayTH.trim() != "") {
        ngaygui = ngaygui + "-" + this.denNgayTH.trim();
      }
      let giogui = "";
      if (this.dsGioTH.length > 0) {
        this.dsGioTH.forEach((dr) => {
          let tgio =
            moment(dr.TUGIO).format("HH:mm") +
            "-" +
            moment(dr.DENGIO).format("HH:mm");
          giogui = giogui + tgio + ";";
        });
      }
      if (giogui.length > 1) {
        giogui = giogui.substring(0, giogui.length - 1);
      }
      if (this.idLoaiYC != "" && (ngaygui == "" || giogui == "")) {
        this.$root.toastError("Chưa thiết lập ngày gửi và giờ gửi!");
        return;
      }

      try {
        this.loading(true);
        const data = {
          cauHinhTBCId: this.cauhinh_tbc_id,
          loaiTinId: this.idLoaiYC,
          diemTNId: this.idDiemTN,
          tienNoId: this.idTienNo,
          ngayGui: ngaygui,
          gioGui: giogui,
          ngayHH: this.ngayHH,
          sdt: this.sdt_tbc.trim(),
          donViId: this.idDonVi,
          kieuKhoa: this.kieuKhoa
        };
        console.log("data", data);
        const response = await API.capNhatTBC_v4(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    onClickXoa() {
      this.$confirm(`Bạn thật sự muốn xoá dữ liệu không?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.fetchXoaCauHinh();
        })
        .catch(() => {});
    },
    async fetchXoaCauHinh() {
      try {
        this.loading(true);
        const response = await API.xoaCauHinhTBC(
          this.axios,
          this.cauhinh_tbc_id
        );
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa dữ liệu thành công!");
          this.fetchDSCauHinhTBCV2(this.idDonVi);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    onClickDiemTN() {
      this.$bvModal.show("modal-diemtn-tbc");
    },
    onClickTienNo() {
      this.$bvModal.show("modal-tienno-tbc");
    },
    onClickLoaiYC() {
      this.$bvModal.show("modal-loaitin-tbc");
    },
    stk_selectedRowChanged() {},
    tg_selectedRowChanged() {},
    gridSTKCommand(arg) {
      this.onDeleteRowSTKHandler(arg.rowData);
    },
    onDeleteRowSTKHandler(e) {
      this.dsSTK = this.dsSTK.filter((item) => item.STT !== e.STT);
    },
    addSoTaiKhoan() {
      const temp = {
        STT: this.dsSTK.length + 1,
        SO_TK: "",
        NGANHANG: ""
      };
      this.dsSTK = [temp, ...this.dsSTK];
    },
    async editGridSTK(arg) {
      let KEY = arg.rowData.STT;
      if (arg.columnName == "SO_TK") {
        if (arg.value) {
          this.dsSTK.forEach((element) => {
            if (element.STT == KEY) {
              element.SO_TK = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "NGANHANG") {
        if (arg.value) {
          this.dsSTK.forEach((element) => {
            if (element.STT == KEY) {
              element.NGANHANG = arg.value;
              return;
            }
          });
        }
      }
      this.dsSTK = [...this.dsSTK];
    },
    addGioTH() {
      const temp = {
        STT: this.dsGioTH.length + 1,
        TUGIO: "",
        DENGIO: "",
      };
      this.dsGioTH = [temp, ...this.dsGioTH];
    },
    async editGridThoiGianTH(arg) {
      let KEY = arg.rowData.STT;
      if (arg.columnName == "TUGIO") {
        if (arg.value) {
          this.dsGioTH.forEach((element) => {
            if (element.STT == KEY) {
              element.TUGIO = arg.value;
              return;
            }
          });
        }
      }
      if (arg.columnName == "DENGIO") {
        if (arg.value) {
          this.dsGioTH.forEach((element) => {
            if (element.STT == KEY) {
              element.DENGIO = arg.value;
              return;
            }
          });
        }
      }
      this.dsGioTH = [...this.dsGioTH];
    },
    gridTGTHCommand(arg) {
      const e = arg.rowData;
      this.dsGioTH = this.dsGioTH.filter((item) => item.STT !== e.STT);
    },
    async cauHinhtbc_selectedRowChanged(data) {
      if (data) {
        this.cauhinh_tbc_id = data.CAUHINH_TBC_ID;
        let kieu_hh = data.KIEU_HH;
        if (kieu_hh == 1) {
          this.enabledNgayHH = true;
          this.ngayHH = data.NGAY_HH || "";
        } else {
          this.enabledNgayHH = false;
          this.ngayHH = "";
        }
        this.idLoaiYC = data.LOAITIN_ID;
        this.idDiemTN = data.DIEMTN_TBC_ID ? data.DIEMTN_TBC_ID : -1;
        this.idTienNo = data.TIENNO_TBC_ID ? data.TIENNO_TBC_ID : -1;

        let ngaygui = data.NGAYGUI;
        this.ngayTH = "";
        this.denNgayTH = "";
        if (ngaygui != "") {
          if (ngaygui.includes("-")) {
            let t = ngaygui.split("-");
            this.ngayTH = t[0];
            this.denNgayTH = t[1];
          } else {
            this.ngayTH = ngaygui;
          }
        }
        this.sdt_tbc = data.SO_DT || "";
        if(data.KIEU_KHOA){
          if(data.KIEU_KHOA == 1) {
            this.kieuKhoa = 1
          } else {
            this.kieuKhoa = 2
          }
        } else {
          this.kieuKhoa = 1
        }
        this.nap_ds_thietlap(data.GIOGUI);
      }
    },
    async checkMapIHH(loaitin) {
      const bodyData = {
        tenId: "loaitin_id",
        ma: loaitin,
      };
      const res = await API.checkMapID(this.axios, bodyData);
      if (res.data.error_code === "BSS-00000000" && res.data.data.length > 0) {
        return res.data.data[0].KIEU_HH || 0;
      }
    },
    nap_ds_thietlap(gioGui) {
      let danhsach = [];
      if (gioGui.includes(";")) {
        danhsach = [];
        let gg22 = gioGui.split(";");
        gg22.forEach((tt, index) => {
          let giatri = tt;
          let strlist = giatri.split("-");
          let tugio = "";
          let dengio = "";
          let TUGIO = "";
          let DENGIO = "";
          if (strlist.length > 1) {
            tugio = strlist[0];
            dengio = strlist[1];
          }
          if (tugio != "") {
            TUGIO = moment(tugio, "HH:mm");
          } else {
            TUGIO = new moment("00:00", "HH:mm");
          }
          if (dengio != "") {
            DENGIO = moment(dengio, "HH:mm");
          } else {
            DENGIO = new moment("00:00", "HH:mm");
          }
          danhsach.push({ TUGIO, DENGIO, STT: index });
        });
      } else {
        let giatri = gioGui;
        let strlist = giatri.split("-");
        let tugio = "";
        let dengio = "";
        let TUGIO = "";
        let DENGIO = "";
        if (strlist.length > 1) {
          tugio = strlist[0];
          dengio = strlist[1];
        }
        if (tugio != "") {
          TUGIO = moment(tugio, "HH:mm");
        } else {
          TUGIO = new moment("00:00", "HH:mm");
        }
        if (dengio != "") {
          DENGIO = moment(dengio, "HH:mm");
        } else {
          DENGIO = new moment("00:00", "HH:mm");
        }
        danhsach.push({ TUGIO, DENGIO, STT: 0 });
      }
      this.dsGioTH = [...danhsach];
    },
  },
  computed: {},
};
</script>
