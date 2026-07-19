import {
  actions
} from './actions'
import {
  getters
} from './getters'
import {
  mutations
} from './mutations'

const state = {
  danhSachNhanVienThuCuoc: [],
  danhSachChuyenTuyen: []
}

const namespaced = true

export const xacnhanchuyentuyen = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
