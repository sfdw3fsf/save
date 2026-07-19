import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  pageInfo: {
    page: 0,
    maxSize: 10,
    totalElement: 0,
    totalPages: 0,
    sort: null,
    propertiesSort: null
  },
  pageInfoDsThongTinThueBaoNo: {
    page: 0,
    maxSize: 5,
    totalElement: 0,
    totalPages: 0,
    sort: null,
    propertiesSort: null
  },
  dsThueBaoKhongKhoaMo: [],
  dsThongTinThueBaoNo: [],
  formResult: [],
  formData: {
  },
  objSearch: {}
}

const namespaced = true

export const danhsachthuebaokhongkhaosat = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
