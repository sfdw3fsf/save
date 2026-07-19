export const getters = {
  getStoreTypes (state, getters, rootState) {
    const storeTypes = []
    for (let i = 0; i < state.stores.storeTypes.length; i++) {
      const ele = {...state.stores.storeTypes[i]}
      ele.LOAI_KHO_NAME = `${ele.MA_LOAI} - ${ele.LOAI_KHO}`
      storeTypes.push(ele)
    }
    return storeTypes
  },
  getCSHT (state, getters, rootState) {
    return state.stores.CSHT
  },
  getSelectedCSHT (state, getters, rootState) {
    return state.stores.selectedCSHT
  },
  getStores (state, getters, rootState) {
    return state.stores.stores
  },
  getStoresIncludeRoot (state, getters, rootState) {
    const storesIncludeRoot = [{
      'unitlevel': 0,
      'ten_kho': 'Root',
      'kho_id': -1,
      'kho_cha_id': null,
      'ma_kho': '.',
      'thutu': 0,
      'loaikho_id': null,
      'donvi_id': null,
      'chungtu': 0,
      'trangthai': 0,
      'ghichu': null,
      'tonkho': 0,
      'so_dt': null,
      'dongbo': 0,
      'ma_tk': null,
      'thukho_id': null
    },
    ...state.stores.stores
    ]

    return storesIncludeRoot
  },
  getDepartments (state, getters, rootState) {
    return state.stores.departments
  },
  getDepartmentsCustomName (state, getters, rootState) {
    const departments = []
    for (let i = 0; i < state.stores.departments.length; i++) {
      const ele = {}
      for (let key in state.stores.departments[i]) {
        if (state.stores.departments[i].hasOwnProperty(key)) {
          if (key.toString() === 'TEN_DV') {
            ele[key] = `${state.stores.departments[i]['MA_DV']} - ${state.stores.departments[i][key]} - ${(state.stores.departments[i]['TEN_DV_CHA']==null?'':state.stores.departments[i]['TEN_DV_CHA'])}`
          } else {
            ele[key] = state.stores.departments[i][key]
          }
        }
      }
      departments.push(ele)
    }
    return departments
  },
  getDepartmentById: (state, getters, rootState) => (id) => {
    return state.stores.departments.find(i => i.DONVI_ID !== null && i.DONVI_ID !== undefined && id !== null && id !== undefined && i.DONVI_ID.toString() === id.toString())
  },
  getSelectedStore (state, getters, rootState) {
    return state.stores.selectedStore
  },
  getSelectedDepartment (state, getters, rootState) {
    return state.stores.selectedDepartment
  },
  getDepartmentsNotAdded (state, getters, rootState) {
    return state.stores.departmentsNotAdded
  },
  getDepartmentsAdded (state, getters, rootState) {
    return state.stores.departmentsAdded
  }
}
