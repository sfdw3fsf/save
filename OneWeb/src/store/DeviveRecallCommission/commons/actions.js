import api from '../../../modules/admin/category/DeviceRecallCommission/DeviceRecallCommission'
export const actions = {
  async getListDeviceGroup ({ commit, state }, data) {
    let response = await api.getListDeviceGroup(data)
    try {
      commit('setListDeviceGroup', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListDeviceType ({ commit, state }, data) {
    let response = await api.getListDeviceType(data)
    try {
      commit('setListDeviceType', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListCondition ({ commit, state }, data) {
    let response = await api.getListCondition(data)
    try {
      commit('setListCondition', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListDeviceRecallCommission ({ commit, state }, data) {
    let response = await api.getListDeviceRecallCommission(data)
    try {
      commit('setListDeviceRecallCommission', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
