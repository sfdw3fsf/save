<template>
  <div style="height: 100vh; overflow: auto; padding: 10px 10px 80px 10px">
    <h3>Quản lý Vật tư</h3>
    <div class="row">
      <ul>
        <li>
          <router-link active-class="" to="/qlvt/HoanTraThietBiBaoHanh"
            >[BSS-1938] - Hoàn trả thiết bị bảo hành</router-link
          >
        </li>

        <li>
          <router-link active-class="" to="/qlvt/TiepNhanThietBiBaoHanh"
            >QLVT - Tiếp nhận thiết bị bảo hành</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/MoHinhBaoHanhThietBi"
            >QLVT - Mô hình bảo hành thiết bị</router-link
          >
        </li>
        <li v-on:click.prevent="openDialog('popupTraCuuVatTuTrongKho')">
          <!-- <router-link active-class="" to="/qlvt/TraCuuVatTuTrongKho"
            >QLVT - Tra cứu vật tư trong kho</router-link
          > -->
          <a class="icon one-calendar-sync">QLVT - Tra cứu vật tư trong kho</a>
          <TraCuuVatTuTrongKho ref="popupTraCuuVatTuTrongKho" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TraCuuChungTu"
            >QLVT - Tra cứu chứng từ</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TraCuuCCDC"
            >QLVT - Tra cứu công cụ dụng cụ</router-link
          >
        </li>
        <!-- <li>
          <a v-b-modal.popup-tracuuvattuthuebao>
            <span class="icon one-calendar-sync"></span> QLVT - Tra cứu vật tư
            thuê bao
          </a>
          <TraCuuVatTuThueBao />
        </li> -->
        <li>
          <router-link active-class="" to="/qlvt/CapPhatCCDC"
            >QLVT - Cấp phát công cụ dụng cụ</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/CapNhatSerial"
            >QLVT - Cập nhật Serial</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TraCuuHopDongVaDonHang"
            >QLVT - Tra cứu hợp đồng và đơn hàng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DuyetNhuCauDangKyVatTu"
            >QLVT - Duyệt nhu cầu đăng ký vật tư</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TraCuuTonKhoTTCUVT"
            >QLVT - Tra cứu tồn kho TTCUVT</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/QuyetToanVatTuCongTrinh"
            >QLVT - Quyết toán vật tư công trình</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TongHopNhuCauDangKyMuaSam"
            >QLVT - Tổng hợp nhu cầu đăng ký mua sắm</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/ThanhToanDonHang"
            >QLVT - Thanh toán đơn hàng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/XetDuyetCongCuBaoHo"
            >QLVT - Xét duyệt công cụ bảo hộ</router-link
          >
        </li>

        <li>
          <a href="#" v-on:click.prevent="openDialog('frmVatTuTrongKho')">
            <span class="icon one-calendar-sync"></span> QLVT - Tra cứ tồn kho
            theo User đăng nhập
          </a>
          <frmVatTuTrongKho ref="frmVatTuTrongKho" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/NhapThietBiBaoHanh"
            >QLVT - Nhập thiết bị bảo hành</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DuyetNhuCauCapPhatDoiVT?Tag=1"
            >QLVT - Duyệt nhu cầu cấp phát Đội VT (TTVT)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DuyetNhuCauCapPhatDoiVT?Tag=2"
            >QLVT - Duyệt nhu cầu sử dụng cấp phát (TTCU)</router-link
          >
        </li>
        <!-- <li>
          <router-link active-class="" to="/qlvt/DotTongHop"
            >QLVT - Đợt tổng hợp</router-link
          >
        </li> -->
        <!-- <a v-b-modal.popup-dottonghop>
          <span class="icon one-calendar-sync"></span> QLVT - Đợt tổng hợp
        </a>
        <DotTongHop /> -->
        <li>
          <router-link active-class="" to="/qlvt/TraCuuSerialThietBi"
            >QLVT - Tra cứu serial / Mã thiết bị</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DonHang"
            >QLVT - Đơn Hàng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DangKyNhuCauVT?Tag=1"
            >QLVT - Đăng ký nhu cầu với TT</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DangKyNhuCauVT?Tag=2"
            >QLVT - Đăng ký với PĐT</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DangKyNhuCauVT"
            >QLVT - Đăng ký nhu cầu mua sắm</router-link
          >
        </li>

        <li v-if="isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT_v2?Tag=1"
            >QLVT - Nhập vật tư</router-link
          >
        </li>
        <li v-if="isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT_v2?Tag=2"
            >QLVT - Nhập vật tư mua lẻ</router-link
          >
        </li>
        <li v-if="isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT_v2?Tag=3"
            >QLVT - Xuất vật tư</router-link
          >
        </li>
        <li v-if="isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT_v2?Tag=4"
            >QLVT - Chuyển vật tư</router-link
          >
        </li>
        <li v-if="!isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT?Tag=1"
            >QLVT - Nhập/xuất/điều chuyển vật tư</router-link
          >
        </li>
        <li v-if="!isHCM">
          <router-link active-class="" to="/qlvt/NhapXuatVT?Tag=2"
            >QLVT - Nhập vật tư mua lẻ</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TraCuuDonHangConHan"
            >QLVT - Tra cứu đơn hàng còn hạn</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TamUngVatTu"
            >QLVT - Tạm ứng vật tư cho nhân viên</router-link
          >
        </li>
        <!-- <li>
          <router-link active-class="" to="/qlvt/NghiemThuDonHang"
            >QLVT - Nghiệm thu đơn hàng</router-link
          >
        </li> -->
        <li>
          <router-link
            active-class=""
            to="/qlvt/GiaoPhieuBaoHanh?huongGiaoId=1401"
            >QLVT - Giao phiếu bảo hành</router-link
          >
        </li>
        <li>
          <router-link
            active-class=""
            to="/qlvt/GiaoPhieuBaoHanh?huongGiaoId=1402"
            >QLVT - Trung tâm viễn thông gửi bảo hành</router-link
          >
        </li>
        <li>
          <router-link
            active-class=""
            to="/qlvt/GiaoPhieuBaoHanh?huongGiaoId=1403"
            >QLVT - Gửi về trung tâm Viễn Thông thiết bị bảo hành
            xong</router-link
          >
        </li>
        <li>
          <router-link
            active-class=""
            to="/qlvt/GiaoPhieuBaoHanh?huongGiaoId=1404"
            >QLVT - Xác nhận thiết bị đã bảo hành</router-link
          >
        </li>
        <li>
          <a href="#" v-on:click.prevent="openDialog('popupLoaiThietBi')">
            <span class="icon one-calendar-sync"></span> QLVT - Loại thiết bị
          </a>
          <LoaiThietBi ref="popupLoaiThietBi" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/GiaoHoanTraVatTu"
            >QLVT - Giao chứng từ hoàn trả</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/ThuHoiVatTuThueBao"
            >QLVT - Thu hồi vật tư thuê bao (TT)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/GiaoHoanTraVatTu?tagFrm=2"
            >QLVT - Nhận chứng từ hoán trả theo đơn vị</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/QuyetToanVatTu"
            >QLVT - Quyết toán vật tư</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TongHopDanhSachThuHoi?Tag=0"
            >QLVT - Tổng hợp danh sách thu hồi</router-link
          >
          <!-- nen la 2 -->
        </li>
        <li>
          <router-link active-class="" to="/qlvt/TongHopDanhSachThuHoi?Tag=2"
            >QLVT - Tổng hợp vật tư thu hồi từ thuê bao</router-link
          >
          <!-- nen la 0 -->
        </li>
        <li>
          <router-link
            active-class=""
            to="/qlvt/TongHopDanhSachThuHoi_New?Tag=0"
            >QLVT - Tổng hợp thu hồi từ thuê bao</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DieuChuyenSuaChuaThuHoiCCDC"
            >QLVT - Điều chuyển, thu hồi và sửa chữa công cụ dụng
            cụ</router-link
          >
        </li>
        <li>
          <!-- <a href="#" v-on:click.prevent="openDialog('popupdanhMucThietBi')">
            <span class="icon one-calendar-sync"></span> QLVT - Danh mục thiết
            bị
          </a>
          <DanhMucThietBi ref="popupdanhMucThietBi" /> -->
          <router-link active-class="" to="/qlvt/DanhMucThietBi"
            >QLVT - Danh mục thiết bị</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/QuanLyNhaTram"
            >QLVT - Quản lý nhà trạm - phụ trợ BTS</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/HopDongMuaSam"
            >QLVT - Hợp đồng mua sắm</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/CapVatTuThueBao?Tag=1"
            >QLVT - Cấp vật tư thuê bao (TT)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/CapVatTuThueBao?Tag=2"
            >QLVT - Cấp vật tư TSL</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DanhSachQuyetToanVatTu"
            >QLVT - Danh sách quyết toán vật tư (TT)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/CapVatTuCT"
            >QLVT - Cấp vật tư công trình (TT)</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DeNghiQuyetToanVatTu"
            >QLVT - Đề nghị quyết toán vật tư</router-link
          >
        </li>
        <li>
          <a
            href="javascript:void(0);"
            v-on:click.prevent="openDialog('frmChonTBCT')"
          >
            <span class="icon one-calendar-sync"></span> QLVT - Chọn thiết bị
            công trình
          </a>
          <frmChonTBCT ref="frmChonTBCT" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/CapNhatTTThuHoi"
            >QLVT - Cập nhật thông tin tình trạng thiết bị</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/NhapDeNghiVatTu"
            >QLVT - Đề nghị vật tư</router-link
          >
        </li>
        <li>
          <a
            href="javascript:void(0);"
            v-on:click.prevent="openDialog('frmGiaoPhieu')"
          >
            <span class="icon one-calendar-sync"></span> QLVT - Giao phiếu quản
            lý vật tư
          </a>
          <frmGiaoPhieu ref="frmGiaoPhieu" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/XacNhanDeNghi"
            >QLVT - Xác nhận đề nghị</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/SuaSerialGP"
            >QLVT - Sửa serial GP</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DSPhieuGiao"
            >QLVT - Danh sách phiếu vật tư chờ ký</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/KiemKeCCDC"
            >QLVT - Kiểm kê công cụ dụng cụ</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/DinhMuc_VT"
            >QLVT - Định mức vật tư</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/ChuanHoaSerial"
            >QLVT - Chuẩn hoá serial</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/LoaiVT"
            >QLVT - Loại vật tư</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/ChuyenVatTuThueBao"
            >QLVT - Luân chuyển vật tư thuê bao</router-link
          >
        </li>
        <li>
          <a
            href="javascript:void(0);"
            v-on:click.prevent="openDialog('frmSuaCT')"
          >
            <span class="icon one-calendar-sync"></span> QLVT - Sửa chứng từ
          </a>
          <frmSuaCT ref="frmSuaCT" />
        </li>
        <li>
          <router-link active-class="" to="/qlvt/frmLoaiKho_MaKho"
            >QLVT - Loại kho - Mã kho</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/frmXuLyYeuCau_VT"
            >QLVT - KHKT-Xét duyệt cấp vật tư cho khách hàng</router-link
          >
        </li>
        <li>
          <router-link active-class="" to="/qlvt/frmCongTrinh"
            >QLVT -Quản lý công trình</router-link
          >
        </li>
        <li>
          <router-link
            active-class=""
            :to="{
              name: 'frmCongTrinhHeThong',
              query: { TAG: '1' },
            }"
            >QLVT -Quản lý công trình ( Hệ thống )</router-link
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
// import TraCuuVatTuThueBao from "./components/TraCuuVatTuThueBao/TraCuuVatTuThueBao";
// import DotTongHop from "./components/DotTongHop/DotTongHop";
// import TraCuuTonKhoTheoUser from "./components/TraCuuTonKhoTheoUser/TraCuuTonKhoTheoUser";
import frmVatTuTrongKho from "./frmVatTuTrongKho";

import LoaiThietBi from "./components/LoaiThietBi/LoaiThietBi.vue";
// import DanhMucThietBi from "./DanhMucThietBi/index.js";
import TraCuuVatTuTrongKho from "./TraCuuVatTuTrongKho";
import frmChonTB_CT from "./frmChonTBi_CT/ChonThietBiCT.vue";
import frmGiaoPhieu from "./frmGiaoPhieu/frmGiaoPhieu.vue";
import frmSuaCT from "./frmSuaCT/SuaChungTu.vue";
export default {
  name: "Home",
  components: {
    // TraCuuVatTuThueBao,
    // TraCuuTonKhoTheoUser,
    frmVatTuTrongKho,
    LoaiThietBi,
    // DanhMucThietBi,
    TraCuuVatTuTrongKho,
    frmChonTBCT: frmChonTB_CT,
    frmGiaoPhieu: frmGiaoPhieu,
    frmSuaCT: frmSuaCT,
  },
  data() {
    return {
      header: {
        title: "PHÂN HỆ QUẢN LÝ VẬT TƯ",
        list: [],
      },
    };
  },
  mounted() {},
  computed: {
    // đk tạm cho routerlink
    isHCM() {
      return this.$auth.getPhanVungID().toString() == "28";
    },
  },
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
