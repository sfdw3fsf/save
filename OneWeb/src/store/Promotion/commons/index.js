import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  listTelecomService: [],
  listDispatchNo: [],
  listPromotionType: [],
  listParticipationPromotion: [],
  listAccompanyingPromotion: [],
  listContractType: [],
  listPromotion: [],
  listContractTypeDeliver: [],
  listContractTypeNotDeliver: [],
  listSoCongVan_TimKiem: [],
  danhSachTrangBiDaGan: [],
  danhSachTrangBiChuaGan: [],
  DanhSachChiTietKhuyenMai: [],
  doiTuongTBDaGan: [],
  doiTuongTBChuaGan: [],
  danhSachGoiThamGiaDaGan: [],
  danhSachGoiThamGiaChuaGan: [],
  danhSachDichVuKemDaGan: [],
  danhSachDichVuKemChuaGan: [],
  danhSachDoiTuongTBDaGan: [],
  danhSachDoiTuongTBChuaGan: [],
  danhSachTocDoTheoTBDaGan: [],
  danhSachTocDoTheoTBChuaGan: [],
  danhSachKhuVucDaGan: [],
  danhSachKhuVucChuaGan: [],
  danhSachMaGhep: [],
  danhSachNgayHieuLuc:[],
  danhSachLoainhaDaGan: [],
  danhSachLoainhaChuaGan: [],


}

const namespaced = true

export const promotionCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
