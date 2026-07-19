<template src="./index.html"></template>
<style scoped>
.popup-box .popup-header {
  padding: 10px 15px;
  font-size: 20px;
  background-color: #fff;
  border-bottom: 1px solid #e0e0e0;
}
</style>
<script>

export default {
  name: "Email",
  components: {},
  props: ["khachHangId"],
  data: function () {
    return {
      dsEmail: [],
      dsKhachHang: [],
      dsThanhToan: [],
      dsThueBao: [],
    };
  },
  validations: {},
  watch: {},
  created: async function () {
    this.getDsEmail();
  },
  async mounted() {},
  methods: {
    onClose() {
      this.$emit("close");
    },
    onSelectRowGrid(value) {
      this.initDataGrid(value.EMAIL_ID);
    },
    initDataGrid(emailId) {
      this.getDsKH(emailId);
      this.getDsTT(emailId);
      this.getDsTB(emailId);
    },
    getDsEmail: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-email`,
          {
            pKhachHangId: this.khachHangId,
            pHDKHId: 0,
          }
        );
        this.dsEmail = rs.data;
        if (this.dsEmail.length == 0) this.initDataGrid(0);
      } catch (error) {}
    },
    getDsKH: async function (emailId) {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-email-theo-loai-dieu-kien`,
          {
            pKhachHangId: this.khachHangId,
            pHDKHId: 0,
            pTTKHId: 1,
            pHDEmailId: 0,
            pEmailId: emailId,
          }
        );
        this.dsKhachHang = rs.data;
      } catch (error) {}
    },
    getDsTT: async function (emailId) {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-email-theo-loai-dieu-kien`,
          {
            pKhachHangId: this.khachHangId,
            pHDKHId: 0,
            pTTKHId: 2,
            pHDEmailId: 0,
            pEmailId: emailId,
          }
        );
        this.dsThanhToan = rs.data;
      } catch (error) {}
    },
    getDsTB: async function (emailId) {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-email-theo-loai-dieu-kien`,
          {
            pKhachHangId: this.khachHangId,
            pHDKHId: 0,
            pTTKHId: 3,
            pHDEmailId: 0,
            pEmailId: emailId,
          }
        );
        this.dsThueBao = rs.data;
      } catch (error) {}
    },
  },
};
</script>
