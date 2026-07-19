import DuyetCongViecCSKHApi from '@/modules/CSKH/api/DuyetCongViecCSKHApi'

export const actions = {
  async getDsNhanVienCSKH({ commit, state }, data) {
    try {
      let response = await DuyetCongViecCSKHApi.getDsNhanVienCSKH(this._vm.axios, data)
      let ds = response.data.data.data || []
      let nhanVienCSKH = []
      ds.forEach(element => {
        nhanVienCSKH.push({
          id: element.NHANVIEN_ID,
          text: element.TEN_NV
        })
      });
      commit("setDsNhanVienCSKH", nhanVienCSKH);
      return response;
    } catch (error) {
      commit("setDsNhanVienCSKH", []);
      return error.response.data
    }
  },
  async getDsCongViecCSKHTheoNguoiTao({ commit, state }, data) {
    let response = await DuyetCongViecCSKHApi.getDsCongViecCSKHTheoNguoiTao(this._vm.axios, data)
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
    commit('setDsCongViecCSKHTheoNguoiTao', ds)
  },

  clearData({ commit, state }, data) {
    commit('clearData', data)
  },

}