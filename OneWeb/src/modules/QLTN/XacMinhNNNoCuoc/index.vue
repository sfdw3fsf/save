<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from "../api/XacMinhNNNoCuoc";

export default {
  components: { },
  data() {
    return {
      dsDanhMuc: [],
      idKQXL: "",
      noiDung: "",
      dsPhieuChoXL: [],
      dsChiTietXacMinh: [],
      selectItem: null,
      TTHD_ID: 27, // nhân viên địa bàn
    };
  },
  watch: {},
  created() {
    this.initForm();
    this.fetchDanhMucXacMinh();
  },
  mounted() {},
  methods: {
    initForm() {
      if (this.$route.query && this.$route.query.TTHD_ID) {
        this.TTHD_ID = this.$route.query.TTHD_ID;
      }
    },
    async fetchDanhMucXacMinh() {
      this.loading(true);
      const response = await API.getDanhMucXacMinh(this.axios);
      if (
        response.data.error_code === "BSS-00000000" &&
        response.data.data.length >= 0
      ) {
        this.dsDanhMuc = response.data.data;
      }
      this.loading(false);
    },
    async LayDanhSach() {
      try {
        this.loading(true);
        const params = {
          tthdId: this.TTHD_ID, //14
          nhanVienId: this.$auth.getNhanVienID(), // 4881
        };
        const response = await API.getDSPhieuChoXL(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsPhieuChoXL = response.data.data;
        } else {
          this.dsPhieuChoXL = [];
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsPhieuChoXL = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async XacMinh() {
      if (this.idKQXL == "") {
        this.$root.toastError("Chưa chọn kết quả xử lý!");
        return;
      }
      if (this.selectItem == null) {
        this.$root.toastError("Chọn phiếu chờ xử lý!");
        return;
      }
      try {
        this.loading(true);
        const data = {
          nhatKyTNId: 0,
          phieuId: this.selectItem.GPHD_ID,
          kqxlId: this.idKQXL,
          noiDungGiaoPhieu: this.noiDung,
        };
        const response = await API.xacMinhPhieu(this.axios, data);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.$root.toastSuccess("Xác minh thành công!");
          this.LayDanhSach();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    phieu_selectedRowChanged(data) {
      if (data) {
        this.fetchChiTietXacMinh(data);
        this.selectItem = data;
      } else {
        this.selectItem = null;
      }
    },
    async fetchChiTietXacMinh(data) {
      try {
        this.loading(true);
        const response = await API.getDSChiTiet(this.axios, data.NHATKYTN_ID); //99
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.length > 0
        ) {
          this.dsChiTietXacMinh = response.data.data;
        } else {
          this.dsChiTietXacMinh = [];
        }
      } catch (error) {
        this.dsChiTietXacMinh = [];
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
  },
  computed: {},
};
</script>
