// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  danhSachSoNe: [],
  itemNe: { },
  selectNe: { },
  donViID: null,
  danhSachPrefixChuaChon: [],
  danhSachPrefixDaChon: [],
  isLoadingSelect: false,
  isLoadingNotSelect: false,
  danhSachCacLoaiSo: [],
  danhSachCacDonVi: [],
  danhSachQuanHuyen: [],
  danhSachTongDai: [],
  danhSachPhuongXa: [],
  dataNotSelect: [],
  dataSelect: [],
  danhSachDichVu: [],
  danhSachPrefixDaChonModal: [],
  dataItem: {},
  isShowModal: false,
  paramFixedNumber: {dichvu_vienthong_id: null, kieuso_id: 0, loaiso_id: null, nhomso_id: 0, prefix: null, so_batdau: null, so_ketthuc: null},
  danhSachDaiCoDinh: []
}

const namespaced = true

export const prefixOfCounty = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
