import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsThueBaoMoPhieu: [],
  totalPage: 0,
  pageSize: 10,
  totaItems: 0,
  objSearch: {},
  dsNhanVienTheoDonVi: [],
  dsLoaiHinhThueBaoKSDaGan: [],
  dsLoaiHinhThueBaoKSChuaGan: [],
  dsHopDongThueBaoKSDaGan: [],
  dsHopDongThueBaoKSChuaGan: [],
  dsPhanLoaiKhachHangKSDaGan: [],
  dsPhanLoaiKhachHangKSChuaGan: [],
  dsDonViKSDaGan: [],
  dsDonViKSChuaGan: [],
  dsLoaiKhachHangKSChuaGan: [],
  dsLoaiKhachHangKSDaGan: [],
}

const namespaced = true

export const phanquyennhanviennhankhaosat = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
