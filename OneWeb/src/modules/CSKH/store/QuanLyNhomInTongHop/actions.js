import QuanLyNhomInTongHopApi from "./../../api/QuanLyNhomInTongHopApi"

export const actions = {
  async getDsNhomKHLon({commit, state}, data) {
    let ds = []
    let response = await QuanLyNhomInTongHopApi.getDsNhomKHLon(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        //this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsNhomKHLon', ds)
  },
  async getDsThanhToanThuocNhom({ commit, state }, data) {
    let ds = []
    let response = await QuanLyNhomInTongHopApi.getDsThanhToanThuocNhom(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        //this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsThanhToanThuocNhom', ds)
  },
  async getDsThanhToanKoThuocNhom({ commit, state }, data) {
    let ds = []
    let response = await QuanLyNhomInTongHopApi.getDSThanhToanKoThuocNhom(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.STT = index + 1)
      } else {
        //this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsThanhToanKoThuocNhom', ds)
  },
  clearData({ commit, state }, data) {
    commit('clearData', data)
  }
}