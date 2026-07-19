export const actions = {
  async getLoaiDSThueBaoKhongKhaoSat ({ commit, state }, data) {
    // let response = await API.getDanhSachThueBao(this._vm.axios)
    // try {
    //   commit('setDanhSachThueBao', response.data)
    // } catch (error) {
    //   throw err
    // }
    // return response;
    const data1 = [
      { value: null, text: 'Loại thue bao' },
      { value: '6', text: 'Thuê bao không khảo sát Lắp mới' },
      { value: '7', text: 'Thuê bao không khảo sát Báo hỏng' }
    ]
    commit('setDanhSachThueBao', data1)
  }
}
