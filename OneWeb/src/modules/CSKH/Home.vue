<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Chăm sóc khách hàng</h3>
    <div class="row">
      <ul>
        <li>
          <router-link active-class="" to="/cskh/homequanlykhl"
            >OB - QUẢN LÝ KHÁCH HÀNG LỚN</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/hometracuu"
            >OB - TRA CỨU</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/homequanlychinhsachcskh"
            >OB - QUẢN LÝ CHÍNH SÁCH CSKH</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/homexulythongtinphananh"
            >OB - XỬ LÝ THÔNG TIN PHẢN ÁNH</router-link
          >
        </li>
        <br>
        <li>
          <router-link active-class="" to="/cskh/DanhSachThueBaoKhongKhaoSat"
            >OB - Danh sách thuê bao không khảo sát</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/GiamThinhPhieuDaKhaoSat"
            >OB - Giám thính phiếu đã khảo sát</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/DanhSachPhieuKhaoSatOutbound"
            >OB - Danh sách phiếu khảo sát outbound</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/QuanLyNhomInTongHop"
            >OB - Quản lý nhóm in tổng hợp</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/MoPhieuKhaoSat"
            >OB - Mở phiếu khảo sát</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/PhanQuyenNhanVienNhanKS"
            >OB - Phân quyền nhân viên nhận khảo sát</router-link
          >
        </li>
        <li>
          <a v-b-modal.popup-thietLapThamSoLayPhieuKS>
            <span class="icon one-calendar-sync"></span> OB -Thiết lập tham số
            lấy phiếu khảo sát</a
          >
          <ThietLapThamSoLayPhieuKhaoSat />
        </li>
        <li>
          <router-link active-class="" to="/cskh/ChamSocThueBaoRoiMang"
            >OB - Chăm sóc thuê bao rời mạng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/DanhSachKSTB5NgayKLL"
            >OB - Danh sách khảo sát thuê bao 5 ngày không PS lưu
            lượng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/DanhSachKSTBGPONSuyHaoCao"
            >OB - Danh sách khảo sát thuê bao GPON suy hao cao</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/DanhSachKSTBMyTVKoDangNhap"
            >OB - Danh sách khảo sát thuê bao MyTV 5 ngày không đăng nhập</router-link
          >
        </li>
        
        <li>
          <router-link active-class="" to="/cskh/ChuongTrinhCSKH"
            >OB - Chương trình CSKH</router-link>
        </li>
        
        <li>
          <router-link active-class="" to="/cskh/ThietLapDoiTuongKhaoSat"
            >OB - Thiết lập đối tượng khảo sát</router-link>
        </li>
         <li>
          <router-link active-class="" to="/cskh/TaoCongViecCSKH"
            >OB - Tạo công việc chăm sóc hách hàng</router-link
          >
        </li>
        
        <li>
          <router-link active-class="" to="/cskh/XacNhanPhanHoiNVKT"
            >OB - Xác nhận phản hồi chất lượng NVKT</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/GiaoPhieuKhaoSatKhachHang">OB - Giao phiếu khảo sát khách hàng</router-link>
        </li>
        <li>
         <router-link active-class="" to="/cskh/DuyetCongViecCSKH">OB - Duyệt công việc chăm sóc hách hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/cskh/GiaoNVCSKH"
            >OB - Giao nhân viên chăm sóc hách hàng</router-link
          >
        </li>
        <li>
          <a @click="openPopupCapNhapTTCSKH">
            <span class="icon one-calendar-sync"></span> OB -Cập nhật thông tin chăm sóc khách hàng
          </a>
          <frmTTChamSocKH ref="frmTTChamSocKH"/>
        </li>
        <li>
          <router-link active-class="" to="/cskh/NhanPhieuKhaoSat"
            >OB - Nhận phiếu khảo sát khách hàng</router-link>
        </li>
        <li>
          <router-link active-class="" to="/cskh/InTongHopKHL"
            >OB - In tổng hợp khách hàng lớn</router-link>
        </li>
        <li>
          <router-link active-class="" to="/cskh/THUCHIENCSKH"
            >OB - Thực hiện CSKH</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/DSKhachHangKS"
            >OB - Danh sách khách hàng khảo sát (thanh lý)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/QuanLyNhanVienKhaoSat"
            >OB - Quản lý nhân viên khảo sát</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/cskh/GiaoPhieuKhaoSatTB"
            >OB - Giao phiếu khảo sát thuê bao trong db</router-link
          >
        </li>
         <li>
          <router-link active-class="" to="/cskh/NhanPhieuKhaoSatTB"
            >OB - Nhận phiếu khảo sát thuê bao trong db</router-link
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import ThietLapThamSoLayPhieuKhaoSat from "./ThietLapThamSoLayPhieuKhaoSat/ThietLapThamSoLayPhieuKhaoSat.vue";
import frmTTChamSocKH from "./CapNhapThongTinCSKH/frmTTChamSocKH.vue"

export default {
  name: "Home",
  components: {
    ThietLapThamSoLayPhieuKhaoSat,
    frmTTChamSocKH
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
    async openPopupCapNhapTTCSKH() {
      await this.$refs.frmTTChamSocKH.openDialog()
    }
  },
};
</script>
