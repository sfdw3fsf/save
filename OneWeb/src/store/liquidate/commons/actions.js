import api from '@/modules/contract/setup/Liquidate/LiquidateAPI'

export const actions = {
  async getListTelecomService ({ commit, state }) {
    let response = await api.getListTelecomService(this._vm.axios)
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
  async getListRequestType ({ commit, state }, {loaiTBID, loaiHDID}) {
    let response = await api.getListRequestType(loaiTBID, loaiHDID)
    console.log('getListRequestType', response)
    try {
      commit('setListRequestType', response.map(r => ({...r, ID: parseInt(r.ID)})))
      return response
    } catch (error) {
      throw error
    }
  },
  async getListChanelSpeed ({ commit, state }) {
    let response = await api.getListChanelSpeed(this._vm.axios)
    try {
      commit('setListChanelSpeed', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListSatellite ({ commit, state }, data) {
    let response = await api.getListSatellite(data)
    try {
      commit('setListSatellite', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async getListContract ({ commit, state }, data) {
    let response = await api.getListContract(data)
    try {
      commit('setListContract', response.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  },
  async layDsThueBao ({ commit, state }, data) {
    commit('setDsThueBao', [])

    if (!data) {
      return
    }

    let response = await api.lay_ds_hdtb_theo_hdkh_tthd(data)
    try {
      commit('setDsThueBao', response)
      return response
    } catch (error) {
      throw error
    }
  },
  async layDsNhomHuy ({ commit, state }, {loaildID, loaitbID, kieu}) {
    let rs = await api.lay_ds_nhomhuy_theo_loaitb(loaildID, loaitbID, kieu)
    commit('setDsNhomHuy', rs)
    return rs
  },
  async layDsLyDoHuy ({ commit, state }, {loaildID, loaitbID, nhomHuyID, kieu}) {
    let rs = await api.lay_ds_lydo_huy(loaildID, loaitbID, nhomHuyID, kieu)
    try {
      commit('setDsLyDoHuy', rs)
    } catch (error) {
      throw error
    }
  }
}
