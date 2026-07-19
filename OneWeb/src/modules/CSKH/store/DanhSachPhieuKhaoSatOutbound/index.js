import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  dsLoaiNhomKhaoSat: [],
  dsTrangThaiLuotGiaoKhaoSat: [],
  dsTrangThaiPhieu: [],
  dsLuotGiaoNhanKhaoSat: [],
  dsPhieuDaGiaoKhaoSat: [],
  dsThongTinCSKHThueBao: [],
  dsNhanVienKyThuatDiaBan: [],
  dsNhanVienKinhDoanhDiaBan: [],
}

const namespaced = true

export const danhsachphieukhaosatoutbound = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
