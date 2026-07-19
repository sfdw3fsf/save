<template src="./index.html"></template>
<style scoped></style>

<script>
import API from "../api/CapNhatTTZalo";
export default {
  components: {},
  props: {
    soDienThoai: {
      type: String,
      default: "",
    },
    thueBaoID: {
      type: Number,
      default: 0, //277808
    },
  },
  data() {
    return {
      soDT: "",
      checkZalo: false,
      checkQuanTam: false,
      checkDanhBa: false,
      sources: [],
      thuebao_id: 0,
    };
  },
  watch: {},
  created() {
    this.soDT = this.soDienThoai;
    this.thuebao_id = this.thueBaoID;
    if(this.$route.query && this.$route.query.soDienThoai){
      this.soDT = this.$route.query.soDienThoai || "";
    }
    if(this.$route.query && this.$route.query.thueBaoID){
      this.thuebao_id = this.$route.query.thueBaoID || "";
    }
  },
  methods: {
    LayTT() {
      if (this.soDT.trim() == "") {
        this.$root.toastError("Bạn chưa nhập số ĐT");
        return;
      }
      this.traCuu();
      this.lay_ds();
    },
    CapNhat() {
      this.$confirm(
        `Bạn có thực sự muốn cập nhật thông tin zalo?`,
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Hủy",
        }
      )
        .then(() => {
          this.handlerCapNhat();
        })
        .catch(() => {});
    },
    async handlerCapNhat() {
      if (this.soDT.trim() == "") {
        this.$root.toastError("Bạn chưa nhập số ĐT");
        return;
      }
      this.traCuu();

      if (!this.checkZalo) {
        this.$root.toastError("Số điện thoại chưa sử dụng Zalo!");
        return;
      }
      if (!this.checkQuanTam) {
        this.$root.toastError("Số điện thoại chưa quan tâm OA VNPT Hải Phòng!");
        return;
      }
      const data = {
        thueBaoId: this.thuebao_id,
        soDT: this.soDT,
        nhanVienId: this.$auth.getNhanVienID(),
      };
      this.loading(true);
      try {
        const response = await API.capNhatZalo(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess(`Cập nhật thành công!`);
          this.lay_ds();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async traCuu() {
      const thucthi = await this.getIDZalo();
      const tontai = await this.kiemtraTonTaiCNZalo();
      let sl_tontai = 0;
      sl_tontai = parseInt(tontai);
      try {
        if (sl_tontai > 0) {
          this.checkDanhBa = true;
        } else this.checkDanhBa = false;
        if (thucthi == "-2" || thucthi == "-1") {
          this.checkZalo = false;
          this.checkQuanTam = false;
        } else if (thucthi == "-3") {
          this.checkZalo = true;
          this.checkQuanTam = false;
        } else if (parseInt(thucthi) > 0) {
          this.checkZalo = true;
          this.checkQuanTam = true;
        } else {
          this.checkZalo = false;
          this.checkQuanTam = false;
        }
      } catch (error) {
        this.checkZalo = false;
        this.checkQuanTam = false;
      }
    },
    async lay_ds() {
      this.loading(true);
      const response = await API.getLichSuCN(this.axios, this.thuebao_id);
      if (response.data.error_code == "BSS-00000000") {
        this.sources = response.data.data;
      } else {
        this.sources = [];
      }
      this.loading(false);
    },
    async getIDZalo() {
      const response = await API.getIDZalo(this.axios, this.soDT);
      if (response.data.error_code == "BSS-00000000") {
        return response.data.data.RESULT;
      }
    },
    async kiemtraTonTaiCNZalo() {
      const response = await API.kiemtraCNZalo(this.axios, this.thuebao_id);
      if (response.data.error_code == "BSS-00000000") {
        return response.data.data.RESULT;
      }
    },
    enterSoDT() {
      if (this.soDT.trim() == "") {
        this.$root.toastError("Bạn chưa nhập số ĐT");
        return;
      }
      this.traCuu();
    },
  },
};
</script>
