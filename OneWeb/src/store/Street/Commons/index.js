import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  street: {
    selectedTownAreaId: null,
    townIdNeo: null,
    townName: null,
    townId: 0,
    note: null,
    townAreas: [],
    districts: [],
    wardsAdded: [],
    wardsNotAdded: [],
    searchWards: [],
    townsByWard: [],
    selectedDistrictId: null,
    selectedSearchDistrictId: 0,
    currentSearchWardId: 0
  }
}

const namespaced = true

export const streetCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
