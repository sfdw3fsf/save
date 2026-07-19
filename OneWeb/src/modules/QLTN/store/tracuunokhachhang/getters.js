export const getters = {
  danhsachThongTinNoGetter: (state, getters, rootState) => {
    const arr = state.danhsachThongTinNo.map((item,index)=>({...item,STT:index+1}))
    return arr
  },
}
