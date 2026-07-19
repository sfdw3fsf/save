import apiChuyenTuyen from "../../api/XacNhanChuyenTuyen"

export const actions = {
    async getDSNhanVienThuCuoc ({ commit, state }, data) {
      let response = await apiChuyenTuyen.getDSNhanVienThuCuoc(this._vm.axios, data)
      try {
        commit('setDanhSachNhanVienThuCuoc', response.data.data.data || [])
      } catch (error) {
        commit('setDanhSachNhanVienThuCuoc', [])
        throw error
      }
      return response
    },
    async getDSChuyenTuyen ({ commit, state }, data) {
      let response = await apiChuyenTuyen.getDSChuyenTuyen(this._vm.axios, data)
      try {
        commit('setDanhSachChuyenTuyen', response.data.data|| [])
      } catch (error) {
        commit('setDanhSachChuyenTuyen', [])
        throw error
      }
      return response
    },
    clearData ({commit, sate}) {
      commit('clearData')
    }
  }
  