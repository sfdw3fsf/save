export const mutations = {
  setStoreTypes (state, data) {
    state.stores.storeTypes = data
  },
  setCSHT (state, data) {
    state.stores.CSHT = data
  },
  setStores (state, data) {
    state.stores.stores = data
  },
  setStoresBelongAccount (state, data) {
    state.stores.storesBelongAccount = data
  },
  setDepartments (state, data) {
    state.stores.departments = data
  },
  setSelectedStore (state, store) {
    if (store) {
      state.stores.selectedStore = store
    }
  },
  setSelectedDepartment (state, data) {
    state.stores.selectedDepartment = data
  },
  setSelectedStoreTypeId (state, selectedStoreTypeId) {
    state.stores.selectedStoreTypeId = selectedStoreTypeId
  },
  setDepartmentsNotAdded (state, data) {
    state.stores.departmentsNotAdded = data
  },
  setDepartmentsAdded (state, data) {
    state.stores.departmentsAdded = data
  },
  setSelectedCSHT (state, data) {
    state.stores.selectedCSHT = data
  },
  addDepartmentsToDepartmentsAdded (state, departments) {
    if (departments && departments.length > 0) {
      for (let i = 0; i < departments.length; i++) {
        if (!state.stores.departmentsAdded.find(item => item.donvi_id === departments[i].donvi_id)) {
          state.stores.departmentsAdded.push(departments[i])
        }
      }
    }
  },
  removeDepartmentsFromDepartmentsAdded (state, departments) {
    if (departments && departments.length > 0) {
      // state.stores.departmentsNotAdded.push(...departments)
      state.stores.departmentsAdded = state.stores.departmentsAdded.filter(i => {
        for (let k = 0; k < departments.length; k++) {
          if (i.donvi_id.toString() === departments[k].donvi_id.toString()) {
            return false
          }
        }

        return true
      })
    }
  },
  resetDepartments (state, data) {
    state.stores.departmentsAdded = []
    // state.stores.departmentsNotAdded = []
  }
}
