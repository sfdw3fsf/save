<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Tra cứu</h3>
    <div class="row">
      <ul>
        <li>
          <router-link active-class="" to="/cskh/TraCuuThueBaoKhaoSat"
            >OB - Tra cứu thuê bao khảo sát</router-link
          >
        </li>
        
        <li>
          <router-link active-class="" to="/cskh/TraCuuKhachHangLon"
            >OB - Tra cứu khách hàng lớn</router-link
          >
        </li>
        
        <li>
          <router-link active-class="" to="/cskh/TraCuuLichSuPhanAnh"
            >OB - Tra cứu lịch sử phản ánh</router-link
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
  name: "HomeTraCuu",
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
