<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Xử lý thông tin phản ánh</h3>
    <div class="row">
      <ul>
        <li>
          <router-link active-class="" to="/cskh/TiepNhanPhanAnhKhachHang"
            >OB - Tiếp nhận phản ánh khách hàng</router-link
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import ThietLapThamSoLayPhieuKhaoSat from "./ThietLapThamSoLayPhieuKhaoSat/ThietLapThamSoLayPhieuKhaoSat.vue";

export default {
  name: "HomeXuLyThongTinPhanAnh",
  components: {
    ThietLapThamSoLayPhieuKhaoSat,
  },
  data() {
    return {
      header: {
        title: "PHÂN HỆ",
        list: [],
      },
    };
  },
  mounted() {},
  methods: {
    ...mapActions("user", ["clearCurrentUser"]),
    openDialog(obj) {
      // this.$refs.dialogObj.show()
      console.log(this.$refs[obj]);
      this.$refs[obj].openDialog();
    },
    async logout() {
      await this.$store.dispatch("user/clearCurrentUser").then((response) => {
        console.log(response);
        this.$router.push("Login");
      });
    },
  },
};
</script>
