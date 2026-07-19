export const getters = {
  getRequestTypeList: (state, getters, rootState) => {
    return state.requestTypeList
  },
  getEmployeeList: (state, getters, rootState) => {
    return state.employeeList
  },
  getServiceList: (state, getters, rootState) => {
    return state.serviceList
  },
  getProcessList: (state, getters, rootState) => {
    return state.processList
  },
  getContractList: (state, getters, rootState) => {
    return state.contractList
  },
  getLoaiHinhList: (state, getters, rootState) => {
    return state.loaiHinhList
  }
}
