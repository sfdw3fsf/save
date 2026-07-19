// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachVaiTro: [],
  isLoading: false,
  dataItem: {},
  danhSachDichVu: [],
  hopDongTheoTB: {},
  danhSachYeuCau: [],
  danhSachLoaiThietBi: [],
  danhSachThayTheVT: [],
  keyThemMoi:[],
  danhSachThayTheHopDong: []
}

const namespaced = true

export const changeModem = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
