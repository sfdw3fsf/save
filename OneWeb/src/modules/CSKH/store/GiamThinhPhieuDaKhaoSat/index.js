import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsLoaiPhieuKhaoSat: [],
  dsDonViTrungTamVienThong: [],
  dsDienThoaiVien: [],
  dsPhanLoaiKhachHang: [],
  dsLoaiKQGiamThinh: [],
  dsThueBao: [],
  dsNoiDungKS: [],
}

const namespaced = true

export const giamthinhphieudakhaosat = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
