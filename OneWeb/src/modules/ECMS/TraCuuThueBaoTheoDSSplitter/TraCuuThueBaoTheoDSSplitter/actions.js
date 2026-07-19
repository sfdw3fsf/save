import TraCuuThueBaoTheoDSSplitterApi from '../TraCuuThueBaoTheoDSSplitterApi'

var apiManager

export const actions = {
  createApiManager () {
    apiManager = TraCuuThueBaoTheoDSSplitterApi(this._vm.$bssApiCaller)
  },
  clearState ({commit}) {
    commit('clearState')
  },
  async getDsFilemauAction ({commit}) {
    try {
      let response = await apiManager.getDsFilemauApi()
      if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
        return response.data.data
      } else {
      }
    } catch (err) {
    }
  },
  async tracuuThueBaoAction ({commit}, data) {
    try {
      let response = await apiManager.tracuuThueBaoApi({kyHieuKetCuoi: data})
      if (response.data.error_code === 'BSS-00000000' && response.data.data != null) {
        return response.data.data
      } else {
      }
    } catch (err) {
    }
  }
}
