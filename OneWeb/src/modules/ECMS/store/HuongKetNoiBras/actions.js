import HknNoiTinhApi from '../../api/HknNoiTinhApi'

var apiManager

export const actions = {
  createApiManager () {
    apiManager = HknNoiTinhApi(this._vm.$bssApiCaller)
  },
  async layDsHknNoiTinhAction ({ commit },data) {

    try {
      let response = await apiManager.layDsHuongKetNoi(data)
      commit('setDanhSachHkn', response.data.data)
      this.hknNoiTinh = response.data.data
      console.log('111',this.hknNoiTinh )
    } catch (err) {
      throw err
    }
  },
  async layDMHknNoiTinhAction ({ commit }) {

    try {
      let response = await apiManager.layDanhMucHuongKetNoi()
      commit('setDanhMucHkn', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async chiTietHknAction ({ commit},id) {
    try {
      let response = await apiManager.chiTietHuongKetNoi(id)
      commit('setHknChitiet', response.data.data)
    } catch (err) {
      throw err
    }
  },
  async themMoiHknAction ({ commit},data) {
    try {
      let response = await apiManager.themMoi(data)
      //commit('setHknNew',response.data.data)
      return  response.data
    } catch (err) {
      throw err
    }
  },
  async capNhatHknAction ({ commit},data) {
    try {
      let response = await apiManager.capNhat(data)
      return  response.data
    } catch (err) {
      throw err
    }
  },
  async xoaHknAction ({commit},hkn_id) {
    try {
      let response = await apiManager.xoa(hkn_id)
      return  response.data
    } catch (err) {
      throw err
    }
  },
  clearState ({commit}) {
    console.log('clear')
    commit('clearState')
  }
}
