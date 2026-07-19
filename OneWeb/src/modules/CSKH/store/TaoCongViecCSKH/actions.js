import TaoCongViecCSKHApi from '@/modules/CSKH/api/TaoCongViecCSKHApi'

export const actions = {
  async getDsChuongTrinhCSKH({ commit, state }, data) {
    try {
      let response = await TaoCongViecCSKHApi.getDsChuongTrinhCSKH(this._vm.axios, data)
      let ds = response.data.data.data || []
      let chuongTrinhCSKH = []
      ds.forEach(element => {
        chuongTrinhCSKH.push({
          id: element.CTCSKH_ID,
          text: element.TEN_CT
        })
      });
      commit("setDsChuongTrinhCSKH", chuongTrinhCSKH);
      return response;
    } catch (error) {
      commit("setDsChuongTrinhCSKH", []);
      return error.response.data
    }
  },
  async getDsCongViecCSKH({ commit, state }, data) {
    let response = await TaoCongViecCSKHApi.getDsCongViecCSKH(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsCongViecCSKH', ds)
  },

  clearData({ commit, state }, data) {
    commit('clearData', data)
  },

}
