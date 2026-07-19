export const getters = {
  getDSCayDonVi (state, getters, rootState) {
    return state.stores.dsCayDonvi
  },
  getDepartmentsNotAdded (state, getters, rootState) {
    return state.stores.departmentsNotAdded
  },
  getSelectedStore (state, getters, rootState) {
    return state.stores.selectedStore
  },
  getSelectedDepartment (state, getters, rootState) {
    return state.stores.selectedDepartment
  },
  getDepartmentsAdded (state, getters, rootState) {
    return state.stores.departmentsAdded
  },
  getStores (state, getters, rootState) {
    return state.stores.stores
  }
}
