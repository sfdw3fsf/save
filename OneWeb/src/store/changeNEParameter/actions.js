// các hàm dùng để gọi API, gọi hàm khác trong mutations để thay đổi state của của đối tượng trong store
import api from '@/modules/admin/subscriber/ChangeNEParameter/api'

export const actions = {
  async layDanhSachSoNe ({commit, state}, postData) {
    commit('SET_IS_LOADING', true)
    let response = await api.layDanhSachSoNe(this._vm.axios, postData)
    try {
      commit('SET_IS_LOADING', false)
      commit('GET_DANH_SACH_SO_NE', response.data.data)
    } catch (err) {
      throw err
    }
    return response
  }
}
