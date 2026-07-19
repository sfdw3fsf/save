<template src="./index.html"></template>
<style scoped src="./index.scss">
</style>
<script>
import ModalChonDiaChi from "../../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal";
import PopupTimKiemNhanVienThuCuoc from "../../../components/PopupTimKiemNhanVienThuCuoc/PopupTimKiemNhanVienThuCuoc";

export default {
  components: {
    ModalChonDiaChi,
    PopupTimKiemNhanVienThuCuoc,
  },
  props: ["kyhoadon", "khachhang_id", "tucthi", "data"],
  data: function () {
    return {
      diachiIG: {
        diachi: null,
        tinh_id: null,
        quan_id: null,
        phuong_id: null,
        pho_id: null,
        ap_id: null,
        khu_id: null,
        dacdiem_id: null,
        sonha: null,
        Lng: 0,
        Lat: 0,
        block: null,
        tang: null,
        phong: null,
        mota: null,
      },
      dataItem: null,
      dsDVQL: [],
      form: {
        idDVQL: this.donViQLID,
        maKH: "",
        maNVTC: "",
        maInGhep: "",
        maTuyenThu: "",
        diaChiIG: "",
        khachhang_id: 0,
        tuyenthu_id: 0,
      },
      khachHang_ID: 0,
      dsKhachHang: [],
      dsMIG: [],
      dsThanhToanIG_DaGan: [],
      dsThanhToanIG_ChuaGan: [],
      maIG: 0,
      dsSelectMoi: [],
      dsSelectCu: [],
      phuong_ID: 0,
      isDiaChi: false,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: "",
      popupComponentEvents: {
        "form-close": "popupClosed",
        acceptChangeCTV: "popupClosed",
        acceptChangeNGT: "popupClosed",
      },
    };
  },
  created: async function () {
    if (this.data && this.data.khachhang_id != "") {
      this.khachhang_id = this.data.khachhang_id;
      this.kyhoadon = this.data.kyhoadon;
      this.tucthi = this.data.tucthi;
    }

    if (this.khachhang_id != "") {
      this.form.khachhang_id = this.data.khachhang_id != "" ? this.data.khachhang_id : this.khachhang_id;
      await this.getDSMIG(0, this.data.khachhang_id, 0);

      await this.getDSThanhToanMIG_ChuaGan(this.khachHang_ID);
      await this.getDSThanhToanMIG_DaGan(this.maIG);
    }
  },
  methods: {
    async dialogOpen() {
      await this.getDsDVQL();

      if (this.khachhang_id != "") {
        this.form.khachhang_id = this.khachhang_id;
        console.log(this.form.khachhang_id);
        await this.getDSMIG(0, this.khachhang_id, 0);
        this.maIG = this.dsMIG[0].MIG_ID;
        await this.getDSThanhToanMIG_ChuaGan(this.khachHang_ID);
        await this.getDSThanhToanMIG_DaGan(this.maIG);
      }
    },
    openDialog() {
      this.$refs.dlgModalMaIG.show();
    },
    hideDialog() {
      this.$refs.dlgModalMaIG.hide();
    },
    getDsDVQL: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=2`
        );
        this.dsDVQL = rs.data;
        // if (rs.data.length > 0) {
        //   this.form.idDVQL = rs.data[0].DONVI_ID;
        // }
      } catch (error) { }
    },
    confirmChose(value) {
      this.$emit("close", this.form.maInGhep);
    },
    onEnterMaKH: async function () {
      this.dsMIG = [];
      this.form.maKH = this.form.maKH.trim();
      if (!this.form.maKH) {
        this.$toast.error("Hãy nhập mã khách hàng và ấn Enter để tìm kiếm!");
        return false;
      }
      await this.getDBKH(this.form.maKH);
      this.form.khachhang_id = this.dsKhachHang[0].KHACHHANG_ID;
      await this.getDSMIG(0, this.form.khachhang_id, 0);
      this.maIG = this.dsMIG[0].MIG_ID;
      await this.getDSThanhToanMIG_ChuaGan(this.form.khachhang_id);
      await this.getDSThanhToanMIG_DaGan(this.maIG);
    },
    getDBKH: async function (maKH) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danh-ba-khach-hang-theo-ma/${maKH}`
        );
        if (rs.data.length > 0) {
          this.dsKhachHang = rs.data;
        }
      } catch (error) { }
    },
    getDSMIG: async function (hdkhId, khacHangId, hdttId) {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/css/ds-mig-hddb-khach-hang?hdkhId=${hdkhId}&khacHangId=${khacHangId}&hdttId=${hdttId}`
        );
        this.dsMIG = rs.data;
        this.maIG = this.dsMIG[0].MIG_ID;
      } catch (error) { }
    },
    getDSThanhToanMIG_DaGan: async function (maIG) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thanhtoan-ig-khachhang-id?maIG=${maIG}&khachHangID=&kieu=1`
        );
        this.dsThanhToanIG_DaGan = rs.data;
      } catch (error) { }
    },
    getDSThanhToanMIG_ChuaGan: async function (khachHangID) {
      try {
        const rs = await this.$root.context.get(
          `/web-quantri/quan-ly-danh-ba/danhsach-thanhtoan-ig-khachhang-id?maIG=&khachHangID=${khachHangID}&kieu=0`
        );
        this.dsThanhToanIG_ChuaGan = rs.data;
      } catch (error) { }
    },
    getDiaChiIG(data) {
      this.diachiIG.diachi = data.diachimoi;
      this.diachiIG.tinh_id = data.tinh_id;
      this.diachiIG.quan_id = data.quan_id;
      this.diachiIG.phuong_id = data.phuong_id;
      this.diachiIG.pho_id = data.pho_id;
      this.diachiIG.ap_id = data.ap_id;
      this.diachiIG.khu_id = data.khu_id;
      this.diachiIG.dacdiem_id = data.dac_diem_id;
      this.diachiIG.so_nha = data.so_nha;
      this.diachiIG.lng = data.lng;
      this.diachiIG.lat = data.lat;
      this.diachiIG.mota = data.diachimoi;
      this.form.diaChiIG = this.diachiIG.diachi;
    },
    changMIG: async function (data) {
      if (data) {
        this.maIG = data.MIG_ID;
        this.form.maInGhep = data.MAIN_GHEP;
        this.form.tuyenthu_id = data.TUYENTHU_ID;
        this.form.maNVTC = data.MA_NV;
        this.form.diaChiIG = data.DIACHI_IG;
        const resData = {
          mig_id : data.MIG_ID,
          main_ghep: data.MAIN_GHEP
        }
        this.$emit("selectedMaIG", resData);
        await this.getDSThanhToanMIG_ChuaGan(this.form.khachhang_id);
        await this.getDSThanhToanMIG_DaGan(this.maIG);
      }
    },

    move() {
      //if (this.kiemTra()) return;
      let arr = [];
      const dsSelected_DaGan = this.$refs.dsDaGan.getSelectedRecords() || [];
      dsSelected_DaGan.map((val) => {
        let idx = this.dsThanhToanIG_DaGan.findIndex(
          (v) => v.MA_TT == val.MA_TT
        );
        if (idx >= 0) {
          arr.push(this.dsThanhToanIG_DaGan[idx]);
          this.dsThanhToanIG_DaGan = this.dsThanhToanIG_DaGan.filter(
            (v) => v.MA_TT != val.MA_TT
          );
        }
      });
      this.dsThanhToanIG_ChuaGan = [...arr, ...this.dsThanhToanIG_ChuaGan];
      this.dsSelectMoi = [];
    },
    moveAll() {
      //if (this.kiemTra()) return;
      this.dsThanhToanIG_ChuaGan = [
        ...this.dsThanhToanIG_DaGan,
        ...this.dsThanhToanIG_ChuaGan,
      ];
      this.dsThanhToanIG_DaGan = [];
      this.dsSelectMoi = [];
    },
    unmove() {
      //if (this.kiemTra()) return;
      let arr = [];
      const dsSelected_ChuaGan =
        this.$refs.dsChuaGan.getSelectedRecords() || [];
      dsSelected_ChuaGan.map((val) => {
        let idx = this.dsThanhToanIG_ChuaGan.findIndex(
          (v) => v.MA_TT == val.MA_TT
        );
        if (idx >= 0) {
          arr.push(this.dsThanhToanIG_ChuaGan[idx]);
          this.dsThanhToanIG_ChuaGan = this.dsThanhToanIG_ChuaGan.filter(
            (v) => v.MA_TT != val.MA_TT
          );
        }
      });
      this.dsThanhToanIG_DaGan = [...arr, ...this.dsThanhToanIG_DaGan];
      this.dsSelectCu = [];
    },
    unmoveAll() {
      //if (this.kiemTra()) return;
      this.dsThanhToanIG_DaGan = [
        ...this.dsThanhToanIG_ChuaGan,
        ...this.dsThanhToanIG_DaGan,
      ];
      this.dsThanhToanIG_ChuaGan = [];
      this.dsSelectCu = [];
    },
    daGanAggregate() {
      return `${this.dsSelectMoi.length}/${this.dsThanhToanIG_DaGan.length}`;
    },
    chuaGanAggregate() {
      return `${this.dsSelectcu.length}/${this.dsThanhToanIG_ChuaGan.length}`;
    },
    onTraCuuMaTT() {
      this.popupComponent = () =>
        import("@/modules/search/subscriber/SearchAccount");
      this.popupComponentName = "SearchAccount";
      this.Popup("popupMaInGhep");
    },
    onNVTC() {
      this.$refs.dlgTimKiemNVTC.openDialog()
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    popupClosed(val){
      if(val){
        this.form.maKH = val.ma_kh || "";
      }
      this.ClosePopup("popupMaInGhep");
    },
    getNVTCProcess(value) {
      this.form.maNVTC = value.maNVTC || "";
      this.form.maTuyenThu = value.maTuyen || "";
      this.form.tuyenthu_id = value.tuyenThuId;
    },
    async showConfirmPopup(message, fncCallback) {
      try {
        await this.$bvModal
          .msgBoxConfirm(message, {
            title: "Thông báo",
            size: "lg",
            buttonSize: "lg",
            okVariant: "primary",
            okTitle: "Đồng ý",
            cancelTitle: "Hủy bỏ",
            hideHeaderClose: false,
            centered: true,
            "modal-class": ["f18", "text-center"],
          })
          .then(async (value) => {
            if (value) {
              fncCallback();
            }
          })
          .catch((err) => {
            console.log(err);
          });
      } catch (error) {
        this.$toast.error("Có lỗi xảy ra, vui lòng thử lại!");
      }
    },
    onTaoMoi() {
      if (this.diachiIG.phuong_id === 0 || this.diachiIG.phuong_id === null) {
        this.$toast.error("Bạn chưa chọn địa chỉ in ghép");
        return;
      }
      if (this.form.maNVTC === "") {
        this.$toast.error("Bạn chưa chọn nhân viên thu cước");
        return;
      }
      if (this.form.khachhang_id === 0) {
        this.$toast.error("Bạn chưa nhập mã khách hàng");
        return;
      }
      this.showConfirmPopup(
        "Bạn có muốn sinh mã in ghép không ?",
        async () => {
          await taoMoiMIG();
        }
      );
    },
    onCapNhat() {
      if (this.dsMIG.length < 1) {
        this.$toast.error("Chưa có thông tin mã in ghép để Cập nhật!");
        return;
      }

      if (this.dsThanhToanIG_DaGan.length > 0) {
        let dsMaTT = "";
        for (var i = 0; i < this.dsThanhToanIG_DaGan.length; i++) {
          dsMaTT = dsMaTT + this.dsThanhToanIG_DaGan[i].MA_TT + ",";
        }

        this.taoMoiThanhtoanMIG(this.kyhoadon, dsMaTT, this.maIG, this.tucthi);
      } else {
        let dsMaTT = "";
        for (var i = 0; i < this.dsThanhToanIG_ChuaGan.length; i++) {
          dsMaTT = dsMaTT + this.dsThanhToanIG_ChuaGan[i].THANHTOAN_ID + ",";
        }
        this.goBoThanhtoanMIG(this.kyhoadon, dsMaTT, this.tucthi);
      }
    },

    taoMoiThanhtoanMIG: async function (kyHoaDon, dsMaTT, maIG, tucThi) {
      try {
        this.loading(true)
        var capnhat = await this.$root.context
          .post(
            `/web-quantri/quan-ly-danh-ba//themmoi-thanhtoan-inghep?kyCuoc=${kyHoaDon}&dsthanhToan=${dsMaTT}&mIG=${maIG}&tucThi=${tucThi}`
          )
          .then((rec) => {
            if (rec.data.length > 0)
              if (rec.data[0].CODE_ERROR === "BSS-00000000") {
                this.$toast.success("Thêm mới thành công!");
              } else {
                this.$toast.error(rec.data[0].CODE_MESSAGE);
              }
            this.loading(false);
          });
      } catch (err) {
        let message_err = err.response.data.message;
        let message_detail = err.response.data.message_detail;
        if (message_detail != null) {
          this.$toast.error(message_detail);
        } else if (message_err != null) {
          this.$toast.error(message_err);
        }
      } finally {
        this.loading(false);
      }
    },
    goBoThanhtoanMIG: async function (kyHoaDon, dsMaTT, tucThi) {
      try {
        this.loading(true)
        var capnhat = await this.$root.context
          .post(
            `/web-quantri/quan-ly-danh-ba/xoa-thanhtoan-inghep?kyCuoc=${kyHoaDon}&dsthanhToan=${dsMaTT}&tucThi=${tucThi}`
          )
          .then((rec) => {
            if (rec.data.length > 0)
              if (rec.data[0].CODE_ERROR === "BSS-00000000") {
                this.$toast.success("Gỡ bỏ thành công!");
              } else {
                this.$toast.error(rec.data[0].CODE_MESSAGE);
              }
            this.loading(false);
          });
      } catch (err) {
        let message_err = err.response.data.message;
        let message_detail = err.response.data.message_detail;
        if (message_detail != null) {
          this.$toast.error(message_detail);
        } else if (message_err != null) {
          this.$toast.error(message_err);
        }
      } finally {
        this.loading(false);
      }
    },
    onXoa() {
      if (this.maIG === "" || this.maIG === 0) {
        this.$toast.error("Bạn chưa chọn mã in ghép để xóa");
        return;
      }
      this.showConfirmPopup(
        "Bạn có muốn xóa mã in ghép này không ?",
        async () => {
          await this.xoaIMG(this.kyhoadon, this.maIG, this.tucthi);
          await this.getDSMIG(0, this.form.khachhang_id, 0);
          this.maIG = this.dsMIG[0].MIG_ID;
          await this.getDSThanhToanMIG_ChuaGan(this.form.khachhang_id);
          await this.getDSThanhToanMIG_DaGan(this.maIG);
        }
      );
    },
    xoaIMG: async function (kyHoaDon, maMIG, tucThi) {
      try {
        this.loading(true)
        var capnhat = await this.$root.context
          .post(
            `/web-quantri/quan-ly-danh-ba/xoa-danhba-inghep?kyCuoc=${kyHoaDon}&mIGID=${maMIG}&tucThi=${tucThi}`
          )
          .then((rec) => {
            if (rec.data.length > 0)
              if (rec.data[0].CODE_ERROR === "BSS-00000000") {
                this.$toast.success("Xóa mã in ghép thành công!");
              } else {
                this.$toast.error(rec.data[0].CODE_MESSAGE);
              }
            this.loading(false);
          });
      } catch (err) {
        let message_err = err.response.data.message;
        let message_detail = err.response.data.message_detail;
        if (message_detail != null) {
          this.$toast.error(message_detail);
        } else if (message_err != null) {
          this.$toast.error(message_err);
        }
      } finally {
        this.loading(false);
      }
    },
    taoMoiMIG: async function () {
      let dsMIG = {
        TUYENTHU_ID: this.form.tuyenthu_id,
        PHUONG_ID: this.diachiIG.phuong_id,
        MA_NVTC: this.form.maNVTC,
        DIACHI_IG: this.form.diaChiIG,
      },
        dsDiaChiMIG = {
          AP_ID: this.diachiIG.ap_id,
          BLOCK: this.diachiIG.block,
          DIACHI: this.diachiIG.dia_chi,
          DIACHI_ID: this.diachiIG.diachi_id,
          DACDIEM_ID: this.diachiIG.dacdiem_id,
          KHU_ID: this.diachiIG.khu_id,
          MOTA: this.diachiIG.mota,
          PHO_ID: this.diachiIG.pho_id,
          PHONG: this.diachiIG.phong,
          PHUONG_ID: this.diachiIG.phuong_id,
          QUAN_ID: this.diachiIG.quan_id,
          SONHA: this.diachiIG.sonha,
          TANG: this.diachiIG.tang,
          TINH_ID: this.diachiIG.tinh_id,
          KINHDO: this.diachiIG.lng,
          VIDO: this.diachiIG.lat,
        };
      let dsCapNhatDanhBaMIG = {
        dsMIG: dsMIG,
        dsDiaChiMIG: dsDiaChiMIG,
        khachHangID: this.form.khachhang_id,
        mIGID: 0,
      };

      try {
        this.loading(true)
        var capnhat = await this.$root.context
          .post(
            `/web-quantri/quan-ly-danh-ba/capnhat-danhba-mig`,
            dsCapNhatDanhBaMIG
          )
          .then((rec) => {
            if (rec.data.length > 0)
              if (rec.data[0].CODE_ERROR === "BSS-00000000") {
                this.$toast.success("Cập nhật thành công!");
                this.getDSMIG(0, this.form.khachhang_id, 0);
                this.maIG = this.dsMIG[0].MIG_ID;
              } else {
                this.$toast.error(rec.data[0].CODE_MESSAGE);
              }
            this.loading(false);
          });
      } catch (err) {
        let message_err = err.response.data.message;
        let message_detail = err.response.data.message_detail;
        if (message_detail != null) {
          this.$toast.error(message_detail);
        } else if (message_err != null) {
          this.$toast.error(message_err);
        }
      } finally {
        this.loading(false);
      }
    },
  },
};
</script>
