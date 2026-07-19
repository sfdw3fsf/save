export const mutations = {
  setDanhSachNhomKM(state, data) {
    state.danhSachNhomKM = data
  },
  setDanhSachNguonKM(state, data) {
    state.danhSachNguonKM = data
  },
  setDanhSachChiTiet(state, data) {
    state.danhSachChiTiet = data.data
  },
  setDanhSachNhomDatCoc(state, data) {
    state.danhSachNhomDatCoc = data
  }, setDanhSachNhomTB(state, data) {
    state.danhSachNhomTB = data.data
  }, setDanhSachToaNhaDaGan(state, data) { //Start thitv.hue add
    state.danhSachToaNhaDaGan = data
  }, setDanhSachToaNhaChuaGan(state, data) {
    state.danhSachToaNhaChuaGan = data //End thitv.hue add
  }, setDanhSachTrangbiDaGan(state, data) { //Start thitv.hue add
    state.danhSachTrangbiDaGan = data
  }, setDanhSachTrangbiChuaGan(state, data) {
    state.danhSachTrangbiChuaGan = data //End thitv.hue add
  }, setDanhSachKhuyenmaiDaGan(state, data) { //Start thitv.hue add
    state.danhSachKhuyenmaiDaGan = data
  }, setDanhSachKhuyenmaiChuaGan(state, data) {
    state.danhSachKhuyenmaiChuaGan = data //End thitv.hue add
  }, setDanhSachLoainhaDaGan(state, data) { //Start thitv.hue add
    state.danhSachLoainhaDaGan = data
  }, setDanhSachLoainhaChuaGan(state, data) {
    state.danhSachLoainhaChuaGan = data //End thitv.hue add
  }, setDanhSachLoaiHinhTBDaGan(state, data) {
    state.danhSachLoaiHinhTBDaGan = data
  }, setDanhSachLoaiHinhTBChuaGan(state, data) {
    state.danhSachLoaiHinhTBChuaGan = data
  }, setDanhSachGoiThamGiaChiTietDaGan(state, data) {
    state.danhSachGoiThamGiaChiTietDaGan = data
  }, setDanhSachGoiThamGiaChiTietChuaGan(state, data) {
    state.danhSachGoiThamGiaChiTietChuaGan = data
  }, setDanhSachTraGopThietBiDaGan(state, data) {
    state.danhSachTraGopThietBiChiTietDaGan = data
  }, setDanhSachTraGopThietBiChuaGan(state, data) {
    state.danhSachTraGopThietBiChiTietChuaGan = data
  }, setDanhSachKMTBDaGan(state, data) {
    state.danhSachKMTBDaGan = data
  }, setDanhSachKMTBChuaGan(state, data) {
    state.danhSachKMTBChuaGan = data
  }, setDanhSachDichVuGTDaGan(state, data) {
    state.danhSachDichVuGTDaGan = data
  }, setDanhSachDichVuGTChuaGan(state, data) {
    state.danhSachDichVuGTChuaGan = data
  }
  , setDanhSachNguonKMDaGan(state, data) {
    state.danhSachNguonKMDaGan = data
  }, setDanhSachNguonKMChuaGan(state, data) {
    state.danhSachNguonKMChuaGan = data
  },
  setListQuota(state, data) {
    state.listQuota = data
  },
  setAllowEditPrice(state, data) {
    state.allowEditPrice = data;
  }
}
