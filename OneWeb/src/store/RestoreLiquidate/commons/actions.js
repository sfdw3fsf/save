import api from '../../../modules/contract/setup/RestoreLiquidationSubs/RestoreLiquidationSubsAPI'
export const actions = {
  async getListTelecomService ({ commit, state }, data) {
    let response = await api.getListTelecomService(data)
    try {
      commit('setListTelecomService', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListSubscriptionType ({ commit, state }) {
    let response = await api.getListSubscriptionType(this._vm.axios)
    try {
      commit('setListSubscriptionType', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListRequestType ({ commit, state }) {
    let response = await api.getListRequestType(this._vm.axios)
    try {
      commit('setListRequestType', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }
}
