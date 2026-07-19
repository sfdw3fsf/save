import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachNhomKM: [],
  danhSachNguonKM: [],
  danhSachChiTiet: [],
  danhSachNhomDatCoc: [],
  danhSachNhomTB: [],
  danhSachLoaiHinhTBDaGan: [],
  danhSachLoaiHinhTBChuaGan: [],
  //Start thitv.hue add
  danhSachToaNhaDaGan: [],
  danhSachToaNhaChuaGan: [],
  danhSachTrangbiDaGan: [],
  danhSachTrangbiChuaGan: [],
  danhSachKhuyenmaiDaGan: [],
  danhSachKhuyenmaiChuaGan: [],
  danhSachLoainhaDaGan: [],
  danhSachLoainhaChuaGan: [],
  //End thitv.hue add
  danhSachGoiThamGiaChiTietDaGan: [],
  danhSachGoiThamGiaChiTietChuaGan: [],
  danhSachTraGopThietBiChiTietDaGan: [],
  danhSachTraGopThietBiChiTietChuaGan: [],
  danhSachKMTBDaGan: [],
  danhSachKMTBChuaGan: [],
  danhSachDichVuGTChuaGan: [],
  danhSachDichVuGTDaGan: [],
  danhSachKieubatdau: [{
    id: 1, name: "Sau tháng nghiệm thu"
  },
  {
    id: 2, name: "Sau tháng KT đặt cọc"
  }, {
    id: 3, name: "Giao dịch tự nhập"
  },
  {
    id: 4, name: "Sau tháng  đặt cọc"
  }],
  danhSachNguonKMChuaGan: [],
  danhSachNguonKMDaGan: [],
  listQuota: [],
  allowEditPrice: true
}

const namespaced = true

export const promotionDetail = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
