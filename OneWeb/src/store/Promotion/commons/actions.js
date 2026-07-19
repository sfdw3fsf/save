import PromotionAPI from '../../../modules/admin/category/Promotion/PromotionAPI'

export const actions = {
  async getListTelecomService({ commit, state }) {
    let response = await PromotionAPI.getListTelecomService()
    try {
      commit('setListTelecomService', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListDispatchNo({ commit, state }, data) {
    let response = await PromotionAPI.sp_tt_socongvan(data)
    try {
      commit('setListDispatchNo', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  
  async getListPromotionType({ commit, state }) {
    let response = await PromotionAPI.getListPromotionType()
    try {

      commit('setListPromotionType', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListParticipationPromotion({ commit, state }, data) {
    let response = await PromotionAPI.sp_tt_khuyenmai(data)
    try {
      commit('setListParticipationPromotion', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListAccompanyingPromotion({ commit, state }, data) {
    let response = await PromotionAPI.getListAccompanyingPromotion(data)
    try {
      commit('setListAccompanyingPromotion', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListContractType({ commit, state }, data) {
    let response = await PromotionAPI.getListContractType(data)
    try {
      commit('setListContractType', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getListPromotion({ commit, state }, data) {
    let response = await PromotionAPI.getListPromotion(data)

    try {
      commit('setListPromotion', response.data)

      return response
    } catch (error) {
      throw error
    }
  },
  async getChiTietKhuyenMai({ commit, state }, data) {
    function compare(a, b) {
      if (a.chitietkm_id > b.chitietkm_id) return -1;
      if (a.chitietkm_id < b.chitietkm_id) return 1;
      return 0;
    }
    let response = await PromotionAPI.getChiTietKhuyenMai(data)

    try {
      commit('setDanhSachChiTietKhuyenMai', response.data.data.sort(compare))
      return response
    } catch (error) {
      throw error
    }
  },

  async getDataContractType({ commit, state }, data) {
    let response = await PromotionAPI.getDataContractType(data)

    try {
      data.kieu == 1 ?
        commit('setDataContractTypeDeliver', response.data.data) :
        commit('setDataContractTypeNotDelivered', response.data.data)


      return response
    } catch (error) {
      throw error
    }
  },
  async getListSoCongVan_TimKiem({ commit, state }, data) {
    let response = await PromotionAPI.getListSoCongVan_TimKiem(data)

    try {
      commit('setListSoCongVan_TimKiem', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachTrangBi({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachTrangBi(data)

    try {

      data.kieu == 1 ?
        commit('setDanhSachTrangBiDaGan', response.data.data) :
        commit('setDanhSachTrangBiChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachLoainha({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachLoainha(data)

    try {

      data.p_kieu == 1 ?
        commit('setDanhSachLoainhaDaGan', response.data.data) :
        commit('setDanhSachLoainhaChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async khuyenmaiInsertOUupdate({ commit, state }, data) {
    return new Promise((resolve, reject) => {
      PromotionAPI.khuyenmaiInsertOUupdate(data).then((response) => {
        resolve(response)
      }).catch((error) => reject(error))
    })


  },

  async xoaKhuyenmai({ commit, state }, data) {
    let response = await PromotionAPI.delete(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachGoiThamGia({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachGoiThamGia(data)

    try {

      data.kieu == 1 ?
        commit('setDanhSachGoiThamGiaDaGan', response.data.data) :
        commit('setDanhSachGoiThamGiaChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachDichVuKem({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachDichVuKem(data)

    try {

      data.kieu == 1 ?
        commit('setDanhSachDichVuKemDaGan', response.data.data) :
        commit('setDanhSachDichVuKemChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },

  async getDanhSachDoiTuongTB({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachDoiTuongTB(data)

    try {

      data.kieu == 1 ?
        commit('setDanhSachDoiTuongTBDaGan', response.data.data) :
        commit('setDanhSachDoiTuongTBChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachTocDoTheoTB({ commit, state }, data) {
    data.vloai == 1 ?
      commit('setDanhSachTocDoTheoTBDaGan', []) :
      commit('setDanhSachTocDoTheoTBChuaGan', [])
    let response = await PromotionAPI.getDanhSachTocDoTheoTB(data)
    try {

      data.vloai == 1 ?
        commit('setDanhSachTocDoTheoTBDaGan', response.data.data) :
        commit('setDanhSachTocDoTheoTBChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  }, async getDanhSachKhuVuc({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachKhuVuc(data)

    try {

      data.type == 1 ?
        commit('setDanhSachKhuVucDaGan', response.data.data) :
        commit('setDanhSachKhuVucChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async getDanhSachMaGhep({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachMaGhep(data)

    try {
      commit('setDanhSachMaGhep', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async xuatCTKM({ commit, state }, data) {
    let response = await PromotionAPI.xuatCTKM(data)

    try {
      return response
    } catch (error) {
      throw error
    }
  },
  async copyKhuyenMai({ commit, state }, data) {
    let response = await PromotionAPI.copyKhuyenMai(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  },

  async getNgayHieuLuc({ commit, state }, data) {
    let response = await PromotionAPI.getNgayHieuLuc(data)
    try {
      commit('setNgayHieuLuc', response.data)
      return response
    } catch (error) {
      throw error
    }
  },
  async insertDate({ commit, state }, data) {
    let response = await PromotionAPI.insertDate(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  },
  async deleteDate({ commit, state }, data) {
    let response = await PromotionAPI.deleteDate(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  }
}
