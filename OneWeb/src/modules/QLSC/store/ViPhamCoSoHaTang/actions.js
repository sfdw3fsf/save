import ViPhamCoSoHaTangApi from '../../api/ViPhamCoSoHaTangApi'

var apiManager

export const actions = {
  createApiManager () {
    apiManager = ViPhamCoSoHaTangApi(this._vm.$bssApiCaller)
  },
  async layDsViPhamCSHT ({commit}) {
    try {
      let response = await apiManager.layDsViPhamCSHT()
      // console.log('response ', response)
      commit('setDsCsht', response.data.data)
      return response.data.data
    } catch (err) {
      throw err
    }
  },
  async layDsDonViXl ({commit}) {
    try {
      let response = await apiManager.layDsDonViXl()
      // console.log('response ', response)
      commit('setDsDonViXl', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async layDsKetQuaXl ({commit}) {
    try {
      let response = await apiManager.layDsKetQuaXl()
      // console.log('response ', response)
      commit('setKetQuaXl', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async layDsDonViViPham ({commit}) {
    try {
      let response = await apiManager.layDsDonViViPham()
      // console.log('response ', response)
      commit('setDonViViPham', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async themViPham ({commit}, data) {
    try {
      console.log(data)
      let response = await apiManager.themViPham(data)
      console.log('response ', response)
    } catch (err) {
      throw err
    }
  },
  async capNhatViPham ({commit}, data) {
    try {
      console.log(data)
      let response = await apiManager.capNhatViPham(data)
      // console.log('response ', response)
      commit('setDsCsht', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async xoaViPham({ commit }, id){

    try {
      let response = await apiManager.xoaViPham(id)
      return response.data.data
    } catch (err) {
      throw err
    }
  },
  clearState ({commit}) {
    commit('clearState')
  }
}
