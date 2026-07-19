import QuanLyBras from '../../api/QuanLyBras'

var apiManager;
export const actions = {
  createApiManager () {
      apiManager = QuanLyBras(this._vm.$bssApiCaller)
  },

  async layDsBrasAction ({ commit }) {

      try {
        let response = await apiManager.layDsBras()
          commit('setDsBras', response.data.data)
      } catch (err) {
          throw err
      }
  },
  async chiTietBrasAction ({ commit},id) {
    try {
      let response = await apiManager.chiTietBras(id)
       commit('setBras', response.data.data)
    } catch (err) {
        throw err
    }
  },
  async themMoi ({ commit},data) {
    try {
      let response = await apiManager.themMoi(data)
       commit('setBrasNew', response.data.data)
        return  response.data
    } catch (err) {
        throw err
    }
  },
  async capNhat ({ commit},data) {
    try {
      let response = await apiManager.capNhat(data)
        return  response.data
    } catch (err) {
        throw err
    }
  },
  async xoaAction ({commit},bras_id) {
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


