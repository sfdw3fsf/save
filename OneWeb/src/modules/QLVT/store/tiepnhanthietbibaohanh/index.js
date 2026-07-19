import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    danhsachDonVi: [],
    danhSachKieuBH: [],
    danhSachTinhTrang: [],
    danhSachNhomTB: [],
    danhSachLoaiTB: [],
    danhSachVatTu: [],
    danhSachTiepNhanBH: [],
    danhSachThamSoMD: [],
    objSearch: {},
    thongTinBaoHanhTB: "Không có thông tin thuê bao",
    vvt_id: "",
    ttBH: "",
    TiepNhanBHPage : {
      page: 0,
      maxSize: 10,
      totalElement: 0,
      totalPages: 0,
      sort: null,
      propertiesSort: null,
      currentRow: 0
  },
}

const namespaced = true

export const tiepnhanthietbibaohanh = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}

export const getterName = Object.getOwnPropertyNames(getters);
export const actionName = Object.getOwnPropertyNames(actions);
export const statePropertyName = Object.getOwnPropertyNames(state);
export const mutationName = Object.getOwnPropertyNames(mutations);