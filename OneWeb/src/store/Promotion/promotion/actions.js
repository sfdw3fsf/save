import PromotionAPI from '../../../modules/admin/category/Promotion/PromotionAPI'

export const actions = {
  async getNhomKM({ commit, state }, data) {
    let response = await PromotionAPI.getNhomKM(data)
    try {
      const NhomKM = []
      const max = Number(response.data.data[0].macdinh)

      NhomKM.push({ ma_ts: 0, ten_ts: 'Không chọn' })
      for (let i = 1; i <= max; i++) {
        NhomKM.push({ ma_ts: i, ten_ts: 'Nhóm ' + i })

      }
      commit('setDanhSachNhomKM', NhomKM)
      return response
    } catch (error) {
      throw error
    }
  },
  async getNguonKM({ commit, state }, data) {
    let response = await PromotionAPI.getNguonKM(data)
    try {
      const dataNew = [{
        nguonkm_id: 0,
        ten_nguon: '-- Không lựa chọn --',
        ten_nhom: 'Doanh thu'
      },
        ...response.data.data
      ]
      commit('setDanhSachNguonKM', dataNew)
      // commit('setDanhSachNguonKMChuaGan', response.data.data)
      // commit('setDanhSachNguonKMDaGan', [])

      return response
    } catch (error) {
      throw error
    }
  },

  async getNguonLDChuaGan({ commit, state }, data) {
    let response = await PromotionAPI.getNguonKM(data)
    try {
      commit('setDanhSachNguonKMChuaGan', response.data.data)
      return response.data.data
    } catch (error) {
      throw error
    }
  }, async getNguonLDDaGan({ commit, state }, data) {
    let responseNew = await PromotionAPI.getDanhSachNguonDaGan(data)
    try {
      commit('setDanhSachNguonKMDaGan', responseNew.data.data)
      return responseNew.data.data
    } catch (error) {
      throw error
    }
  },

  async getDanhSachChiTiet({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachChiTiet(data)
    try {
      commit('setDanhSachChiTiet', response.data)
      return response
    } catch (error) {
      throw error
    }
  }, async getDanhSachNhomDatCoc({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachNhomDatCoc(data)
    try {

      const dataNew = [
        {
          nhom_datcoc_id: 0,
          ten_nhom: '-- Không đặt cọc --'
        },
        ...response.data.data
      ]
      commit('setDanhSachNhomDatCoc', dataNew)
      return response
    } catch (error) {
      throw error
    }
  }, async getDanhSachNhomTB({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachNhomTB(data)
    try {
      commit('setDanhSachNhomTB', response.data)
      return response
    } catch (error) {
      throw error
    }
  }, async getDanhSachLoaiHinhTB({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachLoaiHinhTB(data)
    try {
      if (data.vloai == 1) {
        commit('setDanhSachLoaiHinhTBDaGan', response.data.data)
        let setAllowEditPrice = true
        let loaitb_brcd = [58, 61, 210, 222, 224]

        if (response.data.data.length > 0) {
          for (let km of response.data.data) {
            if (loaitb_brcd.includes(km.loaitb_id)) {
              setAllowEditPrice = false
              break
            }
          }
        }

        commit('setAllowEditPrice', setAllowEditPrice)
      } else {
        commit('setDanhSachLoaiHinhTBChuaGan', response.data.data)
      }

      return response
    } catch (error) {
      throw error
    }
  }, async getDanhSachToaNha({ commit, state }, data) { //Start thitv.hue add
    let response = await PromotionAPI.getDanhSachToaNha(data)
    try {
      data.p_kieu == 1 ?
        commit('setDanhSachToaNhaDaGan', response.data.data) :
        commit('setDanhSachToaNhaChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    } //End thitv.hue add
  }, async getDanhSachLoainha({ commit, state }, data) { //Start thitv.hue add
    try {
      let response = await PromotionAPI.sp_khuyenmai_ctiet_layds_loainha(data)
      data.p_kieu == 1 ?
        commit('setDanhSachLoainhaDaGan', response.data.data) :
        commit('setDanhSachLoainhaChuaGan', response.data.data)
      return response
    } catch (error) {
      console.log(error)//throw error
    } //End thitv.hue add
  }, async getDanhSachTrangbi({ commit, state }, data) { //Start thitv.hue add
    try {
      let response = await PromotionAPI.sp_khuyenmai_ctiet_layds_trangbi(data)
      data.p_kieu == 1 ?
        commit('setDanhSachTrangbiDaGan', response.data.data) :
        commit('setDanhSachTrangbiChuaGan', response.data.data)
      return response
    } catch (error) {
      console.log(error)//throw error
    } //End thitv.hue add
  }, async getDanhSachKhuyenmai({ commit, state }, data) { //Start thitv.hue add
    try {
      let response = await PromotionAPI.sp_khuyenmai_ctiet_layds_khuyenmai(data)
      data.p_kieu == 1 ?
        commit('setDanhSachKhuyenmaiDaGan', response.data.data) :
        commit('setDanhSachKhuyenmaiChuaGan', response.data.data)
      return response
    } catch (error) {
      console.log(error)//throw error
    } //End thitv.hue add
  }, async getDanhSachGoiThamGiaChiTiet({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachGoiThamGiaChiTiet(data)
    try {

      data.kieu == 1 ?
        commit('setDanhSachGoiThamGiaChiTietDaGan', response.data.data) :
        commit('setDanhSachGoiThamGiaChiTietChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  }
  , async getDanhSachTraGopThietBi({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachTraGopThietBi(data)
    try {

      data.kieu == 1 ?
        commit('setDanhSachTraGopThietBiDaGan', response.data.data) :
        commit('setDanhSachTraGopThietBiChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  }

  , async getDanhSachDichVuGT({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachDichVuGT(data)
    try {

      data.kieu == 1 ?
        commit('setDanhSachDichVuGTDaGan', response.data.data) :
        commit('setDanhSachDichVuGTChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  },


  async getDanhSachKMTB({ commit, state }, data) {
    let response = await PromotionAPI.getDanhSachKMTB(data)
    try {

      data.kieu == 1 ?
        commit('setDanhSachKMTBDaGan', response.data.data) :
        commit('setDanhSachKMTBChuaGan', response.data.data)
      return response
    } catch (error) {
      throw error
    }
  }, async insertOrUpdateDetail({ commit, state }, data) {
    let response = await PromotionAPI.insertOrUpdateDetail(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  },
  async copyChiTietKhuyenMai({ commit, state }, data) {
    let response = await PromotionAPI.copyChiTietKhuyenMai(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  },
  async deleteChiTietKhuyenMai({ commit, state }, data) {
    let response = await PromotionAPI.deleteChiTietKhuyenMai(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  }, async updateQuotaByIDDetailPromotion({ commit, state }, data) {
    let response = await PromotionAPI.updateQuotaByIDDetailPromotion(data)

    try {

      return response
    } catch (error) {
      throw error
    }


  }, async getQuotaByIDDetailPromotion({ commit, state }, data) {
    let response = await PromotionAPI.sp_tt_ctkm_dinhmuc(data)

    try {
      commit('setListQuota', response.data.data)

      return response
    } catch (error) {
      throw error
    }
  },
  async deleteQuotaByIDDetailPromotion({ commit, state }, data) {
    let response = await PromotionAPI.deleteQuotaByIDDetailPromotion(data)

    try {

      return response
    } catch (error) {
      throw error
    }
  }
}


