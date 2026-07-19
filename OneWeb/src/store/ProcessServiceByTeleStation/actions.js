import ProcessAPI from '@/modules/contract/setup/ProcessServiceByTeleStation/ProcessServiceByTeleStationAPI'

export const actions = {
  async getRequestTypeList ({ commit, state }) {
    let response = await ProcessAPI.getRequestTypeList()
    try {
      commit('setRequestTypeList', response.data)
    } catch (error) {
      throw error
    }
  },
  async getEmployeeList ({ commit, state }, data) {
    let response = await ProcessAPI.getEmployeeList(data)
    try {
      commit('setEmployeeList', response.data)
    } catch (error) {
      throw error
    }
  },
  async getServiceList ({ commit, state }, data) {
    let response = await ProcessAPI.getServiceList(data)
    try {
      commit('setServiceList', response.data)
    } catch (error) {
      throw error
    }
  },
  async getProcessList ({ commit, state }, data) {
    let response = await ProcessAPI.getProcessList(data)
    try {
      commit('setProcessList', response.data)
    } catch (error) {
      throw error
    }
  },
  async getContractList ({ commit, state }, data) {
    let response = await ProcessAPI.getContractList(data)
    try {
      commit('setContractList', response.data)
    } catch (error) {
      throw error
    }
  },
  async getLoaiHinhList ({ commit, state }, data) {
    let response = await ProcessAPI.getLoaiHinh(data)
    try {
      commit('setLoaiHinhList', response.data)
    } catch (error) {
      throw error
    }
  }
}
