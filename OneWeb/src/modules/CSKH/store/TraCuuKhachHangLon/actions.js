import TraCuuKhachHangLonApi from '@/modules/CSKH/api/TraCuuKhachHangLonApi'
import { currency } from '@/filters/currency'
export const actions = {
  async traCuuThanhToan({ commit, state }, data) {
    let response = await TraCuuKhachHangLonApi.traCuuThanhToan(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
        console.log(ds)
        ds.forEach((element, index) => {
          element.STT = index + 1
          element.NO_DK_TEXT = currency(element.NO_DK)
          element.NO_PS_TEXT = currency(element.NO_PS)
          element.TONG_NO_TEXT = currency(element.TONG_NO)
        })
        console.log(ds)
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsThanhToan', ds)
  },
  setDsMaTTTheoMaInGhepManual({ commit, state }, data) {
    commit('setDsMaTTTheoMaInGhep', data)
  },
  async getDsMaTTTheoMaInGhep({ commit, state }, data) {
    let response = await TraCuuKhachHangLonApi.getDsMaTTTheoMaInGhep(this._vm.axios, data)
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
    commit('setDsMaTTTheoMaInGhep', ds)
  },
  async getDsDonViQuanLy({ commit, state }, data) {
    let response = await TraCuuKhachHangLonApi.getDsDonViQuanLy(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined) {
        ds = response.data.data
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsDonViQuanLy', ds)
  },
  async getDsMIGTheoKhachHang({ commit, state }, data) {
    let response = await TraCuuKhachHangLonApi.getDsMIGTheoKhachHang(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsMIGTheoKhachHang', ds)
  },
  async getDsMaThanhToanChuaGan({ commit, state }, data) {
    data.daGan = 0
    let ds = []
    let response = await TraCuuKhachHangLonApi.getDsMaTTTheoMaIG(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsMaThanhToanChuaGan', ds)
  },
  async getDsMaThanhToanDaGan({ commit, state }, data) {
    data.daGan = 1
    let ds = []
    let response = await TraCuuKhachHangLonApi.getDsMaTTTheoMaIG(this._vm.axios, data);
    if (response.data.error_code === "BSS-00000000") {
      if (response.data.data !== undefined && response.data.data.data !== undefined) {
        ds = response.data.data.data
      } else {
        this.$toast.warning("Không tìm thấy dữ liệu thỏa điều kiện.");
      }
    } else {
      this.$root.toastError(respone.data.message);
    }
    commit('setDsMaThanhToanDaGan', ds)
  },
  async clearData({ commit, state }, data) {
    commit('clearData', data)
  },
  async getDsLienHe({ commit, state }, data) {
    console.log('data', data)
    let response = await TraCuuKhachHangLonApi.getDSLienHe(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      ds = response.data.data.data || []
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsLienHe', ds)
  },
  async clearDataDSThanhToan({ commit, state }, data) {
    commit('setDsThanhToan', [])
  },
}
