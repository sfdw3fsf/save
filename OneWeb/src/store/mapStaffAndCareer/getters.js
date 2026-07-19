export const getters = {
  getSelectedUnit: (state, getters, rootState) => {
    return state.selectedUnit
  },
  getSelectedTypeOfStaff: (state, getters, rootState) => {
    return state.selectedTypeOfStaff
  },
  getSelectedTypeOfArea: (state, getters, rootState) => {
    return state.selectedTypeOfArea
  },
  getStaffCode:  (state, getters, rootState) => {
    return state.staffCode
  },
}
