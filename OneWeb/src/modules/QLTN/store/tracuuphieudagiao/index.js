import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  pageInfo: {
    page: 0,
    maxSize: 10,
    totalElement: 0,
    totalPages: 0,
    sort: null,
    propertiesSort: null
  },
  formResult: [],
  formData: {
    p_buocxyly_no: 0,
    p_chk_ngaygiao: '1',
    p_ngaygiao_tungay: new Date(),
    p_ngaygiao_denngay: new Date(),
    p_chk_lantao: '1',
    p_chk_loainhanvien: '1',
    p_loai_nhanvien: 0,
    p_lantao_xyly_no: 0,
    p_chk_nhanvien: '1',
    p_nhanvien: 0,
    p_chk_luotgiao: '1',
    p_luotgiao: 0
  }
}

const namespaced = true

export const storeTraCuuPhieuDaGiao = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
