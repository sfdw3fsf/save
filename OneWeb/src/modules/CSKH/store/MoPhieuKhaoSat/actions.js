import MoPhieuKhaoSatApi from '@/modules/CSKH/api/MoPhieuKhaoSatApi'

export const actions = {
  async getDsLoaiHopDong({ commit, state }, data) {
    let response = await MoPhieuKhaoSatApi.getDsLoaiHopDong(this._vm.axios, data)
    let ds = []
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined) {
        response.data.data.forEach(element => {
          ds.push({
            value: element.LOAIHD_ID,
            text: element.TEN_LOAIHD
          })
        })
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(respone.data.message)
    }
    commit('setDsLoaiHopDong')
  },
  async getDsThueBaoMoPhieu({ commit, state }, data) {
    let response = await MoPhieuKhaoSatApi.getDsThueBaoMoPhieu(this._vm.axios, data.pageNo, data.pageSize, data)
    let ds = []
    console.log('aaaaaaaaa',response.data.data.data)
    if (response.data.error_code === 'BSS-00000000') {
      if (response.data.data !== undefined
        && response.data.data.data !== undefined
        && response.data.data.data.length > 0) {
        ds = response.data.data.data
        ds.forEach((element, index) => element.ROWID = index + 1)
      } else {
        this._vm.$toast.warning('Không tìm thấy dữ liệu thỏa điều kiện.');
      }
    } else {
      this._vm.$root.toastError(response.data.message)
    }
    console.log(ds)
    commit('setDsThueBaoMoPhieu', ds)
  }
}