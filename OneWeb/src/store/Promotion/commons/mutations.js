export const mutations = {
  setListTelecomService(state, data) {
    let list = data.data.sort((a, b) => {
      if (a.NAME > b.NAME) {
        return 1
      } else {
        return -1
      }
    })
    state.listTelecomService = list
  },
  setListDispatchNo(state, data) {
    state.listDispatchNo = data.data
  },
  setListPromotionType(state, data) {
    state.listPromotionType = data.data
  },
  setListParticipationPromotion(state, data) {
    state.listParticipationPromotion = data.data
  },
  setListAccompanyingPromotion(state, data) {
    state.listAccompanyingPromotion = data.data
  },
  setListContractType(state, data) {
    state.listContractType = data.data
  },
  setListPromotion(state, data) {
    state.listPromotion = data.data || []
  },
  setDataContractTypeDeliver(state, data) {
    state.listContractTypeDeliver = data || []
  },
  setDataContractTypeNotDelivered(state, data) {
    state.listContractTypeNotDeliver = data || []
  },
  setListSoCongVan_TimKiem(state, data) {
    state.listSoCongVan_TimKiem = data.data || []
  },


  setDanhSachTrangBiDaGan(state, data) {
    state.danhSachTrangBiDaGan = data || []
  },
  setDanhSachTrangBiChuaGan(state, data) {

    state.danhSachTrangBiChuaGan = data || []
  },
  setDanhSachChiTietKhuyenMai(state, data) {

    state.DanhSachChiTietKhuyenMai = data || []
  },
  setDoiTuongTBDaGan(state, data) {

    state.doiTuongTBDaGan = data || []
  },
  setDoiTuongTBChuaGan(state, data) {

    state.doiTuongTBChuaGan = data || []
  }, setDanhSachGoiThamGiaDaGan(state, data) {
    state.danhSachGoiThamGiaDaGan = data || []
  }, setDanhSachLoainhaDaGan(state, data) { //Start thitv.hue add
    state.danhSachLoainhaDaGan = data
  }, setDanhSachLoainhaChuaGan(state, data) {
      state.danhSachLoainhaChuaGan = data //End thitv.hue add
  },
  setDanhSachGoiThamGiaChuaGan(state, data) {

    state.danhSachGoiThamGiaChuaGan = data || []
  }, setDanhSachDichVuKemDaGan(state, data) {
    state.danhSachDichVuKemDaGan = data || []
  },
  setDanhSachDichVuKemChuaGan(state, data) {

    state.danhSachDichVuKemChuaGan = data || []
  },
  setDanhSachDoiTuongTBDaGan(state, data) {
    state.danhSachDoiTuongTBDaGan = data || []
  },
  setDanhSachDoiTuongTBChuaGan(state, data) {

    state.danhSachDoiTuongTBChuaGan = data || []
  }, setDanhSachTocDoTheoTBDaGan(state, data) {
    state.danhSachTocDoTheoTBDaGan = data || []
  },
  setDanhSachTocDoTheoTBChuaGan(state, data) {

    state.danhSachTocDoTheoTBChuaGan = data || []
  }, setDanhSachKhuVucDaGan(state, data) {
    state.danhSachKhuVucDaGan = data || []
  },
  setDanhSachKhuVucChuaGan(state, data) {

    state.danhSachKhuVucChuaGan = data || []
  }, setDanhSachMaGhep(state, data) {

    state.danhSachMaGhep = data.data || []
  },
  setNgayHieuLuc(state, data) {
    state.danhSachNgayHieuLuc = data.data || []
  }
}
