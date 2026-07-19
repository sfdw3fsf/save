export const mutations = {
  setDanhSachDonVi(state, data) {
    state.danhsachDonVi = data
  },
  setKyHoaDon(state, data) {
    state.thongtinKyHoaDon.kyHoaDonSelected = data
  },
  setChuKyHoaDon(state, data) {
    state.thongtinKyHoaDon.chuKySelected = data
  },
  setDanhSachNganHang(state, data) {
    state.danhSachNganHang = data
  },
  setDanhSachHinhThucThanhToan(state, data) {
    state.danhSachHinhThucThanhToan = data
  },
  setDanhSachNhanVienThuCuoc(state, data) {
    state.danhSachNhanVienThuCuoc = data
  },
  setDanhSachDichVuVienThong(state, data) {
    state.danhSachDichVuVienThong = data
  },
  setDanhSachLoaiHinhThueBao(state, data) {
    state.danhSachLoaiHinhThueBao = data
  },
  setDanhSachDichVuVienThongDangKy(state, data) {
    state.danhSachDichVuVienThongDangKy = data
  },
  setDanhMucQuanHuyen(state, data) {
    state.danhMucQuanHuyen = data
  },
  setDanhSachKHLon(state, data) {
    state.danhSachKhLon = data
  },
  setDanhSachLoaiKhachHang(state, data) {
    state.danhSachLoaiKH = data
  },
  setDanhSachLoaiPhieu(state, data) {
    state.danhSachLoaiPhieu = data
  },
  setDanhSachNhanVienThuNgan(state, data) {
    state.danhSachNhanVienThuNgan = data
  },
  setDanhSachLanTaoCongNo(state, data) {
    state.danhSachLanTaoCongNo = data
  },
  setDanhSachLyDoXacNhan(state, data) {
    state.danhSachLyDoXacNhan = data
  },
  setDanhSachNguyenNhanXacNhan(state, data) {
    state.danhSachNguyenNhanXacNhan = data
  },
  setDanhSachNganHangGachNo(state, data) {
    state.danhSachNganHangGachNo = data
  },
  setKyHoaDonHienTai(state, data) {
    state.kyHoaDonHienTai = data
  }
}
