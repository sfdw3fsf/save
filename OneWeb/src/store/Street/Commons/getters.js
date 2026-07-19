export const getters = {
  getSelectedTownAreaId (state, getters, rootState) {
    return state.street.selectedTownAreaId
  },
  getSelectedDistrictId (state, getters, rootState) {
    return state.street.selectedDistrictId
  },
  getDistricts (state, getters, rootState) {
    return state.street.districts
  },
  getSearchWards (state, getters, rootState) {
    return state.street.searchWards
  },
  getTownsByWard (state, getters, rootState) {
    return state.street.townsByWard
  },
  getWardsAdded (state, getters, rootState) {
    return state.street.wardsAdded
  },
  getWardsNotAdded (state, getters, rootState) {
    return state.street.wardsNotAdded
  },
  getTownIdNeo (state, getters, rootState) {
    return state.street.townIdNeo
  },
  getTownName (state, getters, rootState) {
    return state.street.townName
  },
  getTownId (state, getters, rootState) {
    return state.street.townId
  },
  getTownAreas (state, getters, rootState) {
    return state.street.townAreas
  },
  getNote (state, getters, rootState) {
    return state.street.note
  },
  getSelectedSearchDistrictId (state, getters, rootState) {
    return state.street.selectedSearchDistrictId
  },
  getCurrentSearchWardId (state, getters, rootState) {
    return state.street.currentSearchWardId
  }
}
