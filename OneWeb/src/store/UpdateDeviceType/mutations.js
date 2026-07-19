export const mutations = {
  setDsThuocTinh(state, data) {
    state.dsThuocTinh = data
  },
  setValueThuocTinh(state, { id, gttt_id, giatri }) {
    const ds = state.dsThuocTinh
    const item = ds.find((item) => item.thuoctinh_id === id)
    if (item) {
      item.gttt_id = gttt_id
      item.giatri = giatri
    }
  },
  setErrorThuocTinh(state, { id, error }) {
    const ds = state.dsThuocTinh
    const item = ds.find((item) => item.thuoctinh_id === id)
    if (item) {
      item.error = error
    }
  }
}
