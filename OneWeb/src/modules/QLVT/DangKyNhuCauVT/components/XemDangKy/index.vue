<template src='./index.html'></template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
export default {
  components: {},
  name: "XemDangKy",
  created: function () {},
  props: ["tag", "vdangky_id"],
  mounted() {},
  computed: {},
  watch: {
    checkListDSDK: async function (data) {
      if (data) {
        this.show_XacNhan = true;
      } else {
        this.show_XacNhan = false;
      }
    },
  },
  data() {
    return {
      xacnhan: false,
      kieu: 0,
      animationSettings: { effect: "Zoom" },
      target: ".main-wrapper",
      editSettings: { allowEditing: true, mode: "Batch" },
      listDK: [],
      checkListDSDK: [],
      bodyDSChiTietVT: [],
    };
  },
  methods: {
    ...mapActions("dangkynhucauVT", ["getDSXemDangKy", "chapNhanDangKy"]),

    async check_DSXemDangKy(_vdangky_ms_id) {
      this.loading(true);
      try {
        // let data = {
        //   vdangky_ms_id: _vdangky_ms_id,
        // };
        const response = await this.getDSXemDangKy(_vdangky_ms_id);
        this.listDK = response;
      } catch (ex) {
        this.$toast.error("Có lỗi đã xảy ra: ", ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async listDSDK_selectedRowChanged(data) {
      console.log("Dataa check", data);
    },

    async listDK_selectedItemChanged(data) {
      this.checkListDSDK = data;
    },

    onClickThoat() {
      this.Close();
    },

    async Open() {
      // await this.check_DSXemDangKy(this.tag)
      this.$refs.popupXemDangKy.show();
    },

    Close() {
      this.$refs.popupXemDangKy.hide();
      this.listDK = [];
    },
    async dialogOpen() {
      console.log("this.vdangky_id = ", this.vdangky_id);
      await this.check_DSXemDangKy(this.vdangky_id);
    },

    async TimKiem() {
      await this.check_DSXemDangKy(this.vdangky_id);
    },

    async fetch_XoaNhanDangKy() {
      this.loading(true);
      try {
        let result = [];
        this.bodyDSChiTietVT = [];
        if (this.checkListDSDK) {
          for (const i of this.checkListDSDK) {
            result = this.listDK.find((str) => i == str.DANGKY_ID);
            console.log("result = ", result);
            this.bodyDSChiTietVT.push(result);
          }
        }
        let data = {
          vdangky_id: this.vdangky_id,
          vjson: this.bodyDSChiTietVT,
          vkieu: 2,
        };
        console.log("chapNhanDangKy data == ", data);
        const response = await this.chapNhanDangKy(data);
        if (response == "1") {
          this.$toast.success("Xóa đăng ký thành công!");
        }
      } catch (ex) {
        this.$toast.error(ex.data.message);
      } finally {
        this.loading(false);
      }
    },

    async xoaDangKy() {
      if(this.checkListDSDK.length = 0){
        this.$toast.error("Bạn chưa chọn vật tư cần để XÓA!")
        return;
      }
      var isBoxConfirm = false;
      await this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
          title: "Thông báo",
          centered: true,
          size: "md",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then((value) => {
          if (value) {
            isBoxConfirm = true;
          }
        });

      if (!isBoxConfirm) {
        return;
      }
      try {
        this.loading(true);
        await this.fetch_XoaNhanDangKy();
      } catch (e) {
        this.$toast.error(e.data.message);
      } finally {
        await this.TimKiem();
        this.loading(false);
      }
    },
  },
};
</script>
