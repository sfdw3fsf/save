<template src='./frmTraPhieuVT.html'></template>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import { Group, Page, Filter, Sort, Resize } from "@syncfusion/ej2-vue-grids";
import frmTraPhieuVTAPI from "../api/frmTraPhieuVTAPI";

export default {
  components: { breadcrumb },
  name: "frmTraPhieuVT",
  provide: {
    grid: [Group, Page, Filter, Sort, Resize],
  },
  mounted() {},
  computed: {},
  data() {
    return {
      dateFormat: "dd/MM/yyyy",

      phieuId: null, //lay tu form cha
      KtThucHien: false,
      phieuSelected: [],
      dsPhieu: [],
      cboLyDoTra: [],

      selectedRow: null,

      txtNgayGiao: "",
      txtDonViGiao: "",
      txtNguoiGiao: "",
      txtOTP: "",
      txtNgayTra: "",
      txtNoiDungTra: "",
      txtLyDoTra: "",
      txtNoiDung: "",
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
    };
  },
  methods: {
    openDialog() {
      this.$refs.dialogTraPhieu.show();
    },
    async dialogOpened() {
      await this.loadForm();
    },
    closeDialog() {},
    async CapNhat() {
      this.loading(true);
      try {
        var res = await frmTraPhieuVTAPI.capNhatTraPhieu(this.axios, {
          phieuId: this.selectedRow.PHIEU_ID,
          lyDoTraId: this.txtLyDoTra,
          ndTra: this.txtNoiDungTra,
          ghiChuTra: "",
          otp: this.txtOTP,
        });
        if (res.data.data.includes("OK")) {
          this.$toast.success("Đã trả phiếu thành công!");
          this.txtOTP = "";
          this.txtNoiDungTra = "";
          this.phieuId = 0;
          this.dsPhieu = [];
          this.KtThucHien = true;
        } else {
          this.$toast.error(res.data.data);
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail);
      }
      this.loading(false);
    },

    async onClickTraPhieu() {
      if (this.dsPhieu.length <= 0) return;
      if (this.selectedRow == null) return;

      this.$confirm(
        `Bạn thật sự muốn trả lại phiếu đã nhận không ?`,
        "Thông báo",
        {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }
      )
        .then(async () => {
          if (this.txtNoiDungTra.length == 0) {
            this.$toast.error("Bạn chưa nhập nội dung trả!");
            return;
          }
          if (this.txtLyDoTra == 0) {
            this.$toast.error("Hãy chọn lý do trả!");
            return;
          }
          if (this.txtOTP.length == 0) {
            this.$toast.error("Chưa nhập mã OTP");
            return;
          }
          await this.CapNhat();
          this.$emit("closed", this.KtThucHien);
        })
        .catch(() => {
          return;
        });
    },

    async HienThiDanhSach() {
      let res = await frmTraPhieuVTAPI.layThongTinPhieuGiao(
        this.axios,
        this.phieuId
      );
      this.txtNgayTra = new Date();
      let ds = res.data.data;
      if (ds.length > 0) {
        this.dsPhieu = ds;
      } else {
        this.dsPhieu = [];
        this.txtDonViGiao = "";
        this.txtNguoiGiao = "";
        this.txtNoiDung = "";
        this.txtNoiDungTra = "";
        this.txtNgayGiao = new Date();
        this.txtLyDoTra = 0;
      }
    },

    async onClickLayTT() {
      this.loading(true);
      this.HienThiDanhSach();
      this.loading(false);
    },

    async onClickOTP() {
      this.loading(true);
      if (this.phieuId == 0) {
        this.$toast.error("Chưa chọn phiếu");
        this.loading(false);
        return;
      }

      try {
        var res = await frmTraPhieuVTAPI.layLaiOTP(this.axios, {
          phieuIds: this.phieuId,
        });

        if (res.data.data.error_code != "BSS_00009491") {
          this.$toast.success("Đã gửi OTP đến số máy người dùng");
        } else {
          this.$toast.error("Có lỗi trong quá trình gửi yêu cầu OTP!");
        }
        this.loading(false);
      } catch (error) {
        this.$toast.error("Có lỗi trong quá trình gửi yêu cầu OTP!");
        this.loading(false);
      }
    },

    grid_onSelectedRowChanged(data) {
      this.selectedRow = data;
      if (this.dsPhieu.length > 0) {
        this.phieuId = data.PHIEU_ID;
        this.txtNoiDung = data.ND_THUCHIEN;
        this.txtNgayGiao = data.NGAYGIAO.substr(0, 10);
        this.txtDonViGiao = data.DONVI_GIAO;
        this.txtNguoiGiao = data.NHANVIEN_GIAO;
      }
    },
    async loadForm() {
      let res = await frmTraPhieuVTAPI.layDanhMucQLDN(this.axios, {
        nhanVienId: 0,
        kieu: 4,
        param1: "",
        param2: "",
      });
      this.cboLyDoTra = res.data.data;
      this.HienThiDanhSach();
    },
  },

  // created: async function () {
  //   this.loading(true);
  //   let res = await frmTraPhieuVTAPI.layDanhMucQLDN(this.axios, {
  //     nhanVienId: 0,
  //     kieu: 4,
  //     param1: "",
  //     param2: "",
  //   });
  //   this.cboLyDoTra = res.data.data;
  //   this.HienThiDanhSach();
  //   this.loading(false);
  // },
  watch: {},
  destroyed() {},
};
</script>
