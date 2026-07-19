import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhsachDonVi: [],
  thongtinKyHoaDon: {
    kyHoaDonSelected: null,
    chuKySelected: null
  },
  danhSachNganHang: [],
  danhSachHinhThucThanhToan: [],
  danhSachNhanVienThuCuoc: [],
  danhSachDichVuVienThong: [],
  danhSachLoaiHinhThueBao: [],
  danhSachDichVuVienThongDangKy: [],
  danhMucQuanHuyen: [],
  danhSachKhLon: [],
  danhSachLoaiKH: [],
  danhSachLoaiPhieu: [],
  danhSachNhanVienThuNgan: [],
  danhSachLanTaoCongNo: [],
  danhSachLyDoXacNhan: [],
  danhSachNguyenNhanXacNhan: [],
  danhSachNganHangGachNo: [],
  kyHoaDonHienTai: {}
}

const namespaced = true

export const qltnCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
