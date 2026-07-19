import api from '../../modules/contract/setup/RestoreLiquidationSubs/RestoreLiquidationSubsAPI'
export const actions = {
  async getListTelecomService ({ commit, state }) {
    let response = await api.getListTelecomService()
    try {
      commit('setListTelecomService', response)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListSubscriptionType ({ commit, state }) {
    let response = await api.getListSubscriptionType()
    try {
      commit('setListSubscriptionType', response)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListRequestType ({ commit, state }, {loaiTBID, loaiHDID}) {
    let response = await api.getListRequestType(loaiTBID, loaiHDID)
    try {
      commit('setListRequestType', response)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListDonVi ({ commit, state }, {donViID, loaiDonViID}) {
    let response = await api.lay_ds_donvi_theo_loaidv(donViID, loaiDonViID)
    try {
      commit('setListDonVi', response)
      return response
    } catch (error) {
      throw error
    }
  }
}
