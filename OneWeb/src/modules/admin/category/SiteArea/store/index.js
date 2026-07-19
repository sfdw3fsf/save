// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  areaListFollowDividedPoint: [],
  parentAreaList: [],
  assignedArea: [],
  unassignedArea: [],
  districts: [],
  wards: [],
  towns: [],
  hamlets: [],
  areas: [],
  loadData: []
}

const namespaced = true

export const siteArea = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
