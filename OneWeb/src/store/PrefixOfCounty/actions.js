// các hàm dùng để gọi API, gọi hàm khác trong mutations để thay đổi state của của đối tượng trong store
import api from '@/modules/admin/category/PrefixOfCounty/api'

export const actions = {
  async layDanhSachLoaiSo ({commit, state}) {
    let response = await api.layDanhSachLoaiSo(this._vm.axios)
    try {
      commit('GET_DANH_SACH_LOAI_SO', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachQuanHuyen ({commit, state}) {
    let response = await api.layDanhSachQuanHuyen(this._vm.axios)
    try {
      commit('GET_DANH_SACH_QUAN_HUYEN', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachCacDonVi ({commit, state}, params) {
    let response = await api.layDanhSachCacDonVi(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_CAC_DON_VI', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachDichVu ({commit, state}, params) {
    let response = await api.layDanhSachDichVu(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_DICH_VU', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachTongDai ({commit, state}, params) {
    let response = await api.layDanhSachTongDai(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_TONG_DAI', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachPhuongXa ({commit, state}, params) {
    let response = await api.layDanhSachPhuongXa(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_PHUONG_XA', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },

  async layDanhSachPrefixChuaChon ({commit, state}, params) {
    commit('SET_IS_LOADING_NOT_SELECT', true)
    let response = await api.layDanhSachPrefixChuaChon(this._vm.axios, params)
    try {
      commit('SET_IS_LOADING_NOT_SELECT', false)
      commit('GET_DANH_SACH_PREFIX_CHUA_CHON', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachPrefixChuaChonModal ({commit, state}, params) {
    commit('SET_IS_LOADING_NOT_SELECT', true)
    let response = await api.layDanhSachPrefixChuaChon(this._vm.axios, params)
    try {
      commit('SET_IS_LOADING_NOT_SELECT', false)
      commit('GET_DANH_SACH_PREFIX_CHUA_CHON', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  async layDanhSachDaiCoDinh ({commit, state}, params) {
    let response = await api.layDanhSachDaiCoDinh(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_DAI_CO_DINH', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  },
  setDataSelect ({commit, state}, data) {
    commit('SET_DATA_SELECT', data)
  },

  async layDanhSachPrefixDaChon ({commit, state}, params) {
    commit('SET_IS_LOADING_SELECT', true)
    let response = await api.layDanhSachPrefixDaChon(this._vm.axios, params)
    try {
      commit('GET_DANH_SACH_PREFIX_DA_CHON', response.data.data)
      commit('SET_IS_LOADING_SELECT', false)
    } catch (err) {
      throw err
    }
    return response
  }

}
