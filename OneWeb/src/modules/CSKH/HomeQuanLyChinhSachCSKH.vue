<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Quản lý chính sách CSKH</h3>
    <div class="row">
      <ul>
        
      </ul>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import ThietLapThamSoLayPhieuKhaoSat from "./ThietLapThamSoLayPhieuKhaoSat/ThietLapThamSoLayPhieuKhaoSat.vue";

export default {
  name: "HomeQuanLyChinhSachCSKH",
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
