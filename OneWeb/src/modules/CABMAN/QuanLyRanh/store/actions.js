// import { axios } from 'vue/types/umd'
import axios from 'axios'
import api from '../api'
var apiManager
export const actions = {
  createApiManager() {
    apiManager = api(this._vm.$requestContext)
  },
  async api_get_ds_ttvt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ttvt(data)
      commit('set_ds_ttvt', response.data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async api_get_ds_tokt({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_tokt(data)

      // Nếu response.data có length > 0 thì thêm    { DONVI_ID: -1, TEN_DV: 'Tất cả' } vào đầu mảng
      if (response.data.length > 0) {
        response.data.unshift({ DONVI_ID: -1, TEN_DV: 'Tất cả' })
      }
      commit('set_ds_tokt', response.data)
      return response
    } catch (error) {
      console.log('api_get_ds_tokt', data, error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async api_get_ds_loairanh({ commit }) {
    try {
      const response = await apiManager.api_get_ds_loairanh()
      commit('set_ds_loairanh', response.data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async api_get_ds_ranhtram({ commit }, data) {
    try {
      const response = await apiManager.api_get_ds_ranhtram(data)
      // Chuyển tất cả giá trị của RANH_ID thành string
      response.data.forEach((element) => {
        element.RANH_ID = element.RANH_ID.toString()
      })
      commit('set_ds_tram', response.data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },

  // api xuất excel danh sách ranh dùng axios
  async api_export_excel({ commit, state }) {
    try {
      // nếu state.ds_ranh_id có length > 0 thì đổi sang chuỗi string với id cách nhau bằng dấu ;
      let ds_ranh_id = ''
      if (state.ds_ranh_id.length > 0) {
        ds_ranh_id = state.ds_ranh_id.join(';')
      }
      const response = await axios({
        method: 'post',
        url: '/web-cabman/excel/download-danhsach-ketcuoi-cua-ranh',
        responseType: 'blob', // move responseType here
        data: {
          p_ranh_ids: ds_ranh_id
        }
      })
      // bóc tách file từ this.excelFile để lưu về máy
      var blob = new Blob([response.data], { type: 'application/vnd.ms-excel' })
      var url = window.URL.createObjectURL(blob)
      var a = document.createElement('a')
      a.href = url
      // Lấy tên file từ blob
      var fileName = 'DS_KETCUOI_TRONG_RANH.xlsx'
      // var fileName = response.headers['content-disposition'].split('filename=')[1]
      //  bỏ đi dấu _ ở đầu và cuối tên file
      // fileName = fileName.substring(1, fileName.length - 1)
      a.download = fileName
      a.click()
      window.URL.revokeObjectURL(url)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },

  async set_TabPageIndex({ commit }, TabPageIndex) {
    commit('set_TabPageIndex', TabPageIndex)
  },
  clearState({ commit }) {
    commit('clearState')
  },
  async setState({ commit }, { key, value }) {
    commit('setState', { key, value })
  }
}
