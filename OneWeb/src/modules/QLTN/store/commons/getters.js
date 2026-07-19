export const getters = {
  danhsachDonViGetter: (state, getters, rootState) => {
    return state.danhsachDonVi
  },
  /*
  getKyHoaDonGetter: (state, getters, rootState) => {
    return state.thongtinKyHoaDon.kyHoaDonSelected
  },
  getChuKyHoaDonGetter: (state, getters, rootState) => {
    return state.thongtinKyHoaDon.chuKySelected
  }
  */
  getKyHoaDonGetter: (state, getter, rootState) => {
    return state.thongtinKyHoaDon
  },
  danhSachNganHangGetter: (state, getters, rootState) => {
    return state.danhSachNganHang
  },
  danhSachHinhThucThanhToanGetter: (state, getters, rootState) => {
    return state.danhSachHinhThucThanhToan
  },
  danhSachNhanVienThuCuocGetter: (state, getters, rootState) => {
    return state.danhSachNhanVienThuCuoc
  },
  danhSachDichVuVienThongGetter: (state, getters, rootState) => {
    return state.danhSachDichVuVienThong
  },
  danhSachLoaiHinhThueBaoGetter: (state, getters, rootState) => {
    return state.danhSachLoaiHinhThueBao
  },
  danhSachDichVuVienThongDKGetter: (state, getters, rootState) => {
    return state.danhSachDichVuVienThongDangKy
  },
  danhMucQuanHuyenGetter: (state, getters, rootState) => {
    return state.danhMucQuanHuyen
  },
  danhSachKHLonGetter: (state, getters, rootState) => {
    return state.danhSachKhLon
  },
  danhSachLoaiKHGetter: (state, getters, rootState) => {
    return state.danhSachLoaiKH
  },
  danhSachLoaiPhieuGetter: (state, getters, rootState) => {
    return state.danhSachLoaiPhieu
  },
  danhSachNhanVienThuNganGetter: (state, getters, rootState) => {
    return state.danhSachNhanVienThuNgan
  },
  danhSachLanTaoCongNoGetter: (state, getters, rootState) => {
    return state.danhSachLanTaoCongNo
  },
  danhSachLyDoXacNhanGetter: (state, getters, rootState) => {
    return state.danhSachLyDoXacNhan
  },
  danhSachNguyenNhanXacNhanGetter: (state, getters, rootState) => {
    return state.danhSachNguyenNhanXacNhan
  },
  danhSachNganHangGachNoGetter: (state, getters, rootState) => {
    return state.danhSachNganHangGachNo
  },
  kyHoaDonHienTaiGetter: (state, getters, rootState) => {
    return state.kyHoaDonHienTai
  }
}
