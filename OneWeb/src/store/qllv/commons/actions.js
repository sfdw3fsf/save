import LinhVucAPI from '@/modules/admin/category/UpdateField/UpdateFieldAPI'

export const actions = {
  async getDanhSachUpdateField ({ commit, state }) {
    let response = await LinhVucAPI.getList(this._vm.axios)
    try {
      commit('setDanhSachLinhVuc', response.data)
    } catch (error) {
      throw error
    }
  }
}
