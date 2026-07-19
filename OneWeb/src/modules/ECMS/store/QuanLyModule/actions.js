import QuanLyModule from '../../api/QuanLyModule'

var apiManager;
export const actions = {
  createApiManager () {
      apiManager = QuanLyModule(this._vm.$bssApiCaller)
  },

  async layDsModuleAction ({ commit }) {

      try {
        let response = await apiManager.layDsModule()
          commit('setDsModule', response.data.data)
      } catch (err) {
          throw err
      }
  },
  async chiTietModuleAction ({ commit},id) {
    try {
      let response = await apiManager.chiTietModule(id)
       commit('setModule', response.data.data)
    } catch (err) {
        throw err
    }
  },
  async themMoiModule ({ commit},data) {
    try {
      let response = await apiManager.themMoi(data)
       commit('setModuleNew', response.data.data)
        return  response.data
    } catch (err) {
        throw err
    }
  },
  async capNhatModule ({ commit},data) {
    try {
      let response = await apiManager.capNhat(data)
        return  response.data
    } catch (err) {
        throw err
    }
  },
  async xoaModuleAction ({commit},bras_id) {
    try {
      let response = await apiManager.xoa(bras_id)
        return  response.data
    } catch (err) {
        throw err
    }
  },
  clearState ({ commit }) {
      commit('clearState')
  }
}


