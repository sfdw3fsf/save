import types from './types'
import { EventBus } from '@/utils/eventBus'
import api from '../api'
var apiManager
const toObject = data => {
  if (data.constructor.name === 'Array') {
    if (data.length > 0) {
      return data[0]
    } else {
      return {}
    }
  }
  if (data.constructor.name === 'Object') {
    return data
  }
}
export default {
  createApiManager () {
    apiManager = api(this._vm.$requestContext)
  },
  async setState ({ commit }, { key, value }) {
    commit(types.SET_STATE, { key, value })
  },
  async clearState ({ commit }) {
    commit(types.CLEAR_STATE)
  },
  async setDataGC ({ commit }, data) {
    try {
      commit(types.SET_DATAGC, data)
    } catch (error) {
      throw error
    }
  },
  async delDataGC ({ commit }, data) {
    try {
      commit(types.DEL_DATAGC, data)
    } catch (error) {
      throw error
    }
  },
  async getTTThueBao ({ commit }, data) {
    try {
      const response = await apiManager.getTTThueBao(data)
      commit(types.SET_TTTHUEBAO, response.data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getTTThueBaoMaTT ({ commit }, data) {
    try {
      const response = await apiManager.getTTThueBaoMaTT(data)
      commit(types.SET_TTTHUEBAO, response.data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDICHVU_VT ({ commit }, data) {
    try {
      const response = await apiManager.getDICHVU_VT(data)
      commit(types.SET_STATE, { key: 'DS_DICHVU_VT', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDANHMUC ({ commit }, data) {
    try {
      const response = await apiManager.getDANHMUC(data)
      commit(types.SET_STATE, { key: data.loai, value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setUpdate_PhuongAn_XuLy ({ commit }, data) {
    try {
      const response = await apiManager.setUpdate_PhuongAn_XuLy(data)
      return response
    } catch (error) {
      console.log('setUpdate_PhuongAn_XuLy error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_DS_DBKH_THEO_KHID ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_DS_DBKH_THEO_KHID(data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getCHUDEKN_THEO_LHTB ({ commit }, data) {
    try {
      const response = await apiManager.getCHUDEKN_THEO_LHTB(data)
      console.log(
        '🚀 ~ file: actions.js ~ line 104 ~ getCHUDEKN_THEO_LHTB ~ response.data',
        response.data
      )
      commit(types.SET_STATE, { key: 'DS_CHUDEKN', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getCHUDEKN_THEO_GANQUYEN ({ commit }, data) {
    try {
      const response = await apiManager.getCHUDEKN_THEO_GANQUYEN(data)
      commit(types.SET_STATE, { key: 'DS_CHUDEKN', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_CHUDEKN_THEO_LHTB_PLKH_V2 ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_CHUDEKN_THEO_LHTB_PLKH_V2(data)
      commit(types.SET_STATE, { key: 'DS_CHUDEKN', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_KHIEUNAI_HT ({ commit }, data) {
    try {
      const response = await apiManager.getDS_KHIEUNAI_HT(data)
      commit(types.SET_STATE, { key: 'DS_KHIEUNAI_HT', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getCHUKYNO_THEOTB ({ commit }, data) {
    try {
      const response = await apiManager.getCHUKYNO_THEOTB(data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI_ID ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI_ID(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI_DAKHOA ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI_DAKHOA', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI_DAGIAO ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI_DAGIAO', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getSYSDATE ({ commit }, data) {
    try {
      const response = await apiManager.getSYSDATE(data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAYHUONGGIAO_TIEPNHAN_XLKN ({ commit }, data) {
    try {
      const response = await apiManager.getLAYHUONGGIAO_TIEPNHAN_XLKN(data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getTaoMaKN ({ commit }, data) {
    try {
      const response = await apiManager.getTaoMaKN(data)
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TIENGC ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TIENGC(data)
      commit(types.SET_STATE, { key: 'DS_TIENGC', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getXOA_KHIEUNAI ({ commit }, data) {
    try {
      console.log('getXOA_KHIEUNAI', data)
      const response = await apiManager.getXOA_KHIEUNAI(data)
      return response
    } catch (error) {
      console.log('getXOA_KHIEUNAI error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getCHITIETNO_THEO_KHOANMUC ({ commit }, data) {
    try {
      const response = await apiManager.getCHITIETNO_THEO_KHOANMUC(data)
      commit(types.SET_STATE, {
        key: 'DS_CHITIETNO_THEO_KHOANMUC',
        value: response.data
      })

      return response
    } catch (error) {
      console.log('getCHITIETNO_THEO_KHOANMUC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getHUONGGIAO_LDV ({ commit }, data) {
    try {
      const response = await apiManager.getHUONGGIAO_LDV(data)
      commit(types.SET_STATE, {
        key: 'DS_HUONGGIAO_LDV',
        value: response.data
      })

      return response
    } catch (error) {
      console.log('getHUONGGIAO_LDV error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_DONVI_NGUOIDUNG_THEOBANG ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_DONVI_NGUOIDUNG_THEOBANG(data)
      return response
    } catch (error) {
      console.log('getLAY_DONVI_NGUOIDUNG_THEOBANG error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_DONVI_NHAN_KHIEUNAI ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_DONVI_NHAN_KHIEUNAI(data)
      return response
    } catch (error) {
      console.log('getLAY_DONVI_NHAN_KHIEUNAI error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setNHAP_KHIEUNAI_Insert_V2 ({ commit }, data) {
    try {
      const response = await apiManager.setNHAP_KHIEUNAI_Insert_V2(data)
      return response
    } catch (error) {
      console.log('setNHAP_KHIEUNAI_Insert_V2 error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setNHAP_KHIEUNAI_Insert_V3 ({ commit }, data) {
    try {
      const response = await apiManager.setNHAP_KHIEUNAI_Insert_V3(data)
      return response
    } catch (error) {
      console.log('🚀 ~ file: actions.js:320 ~ setNHAP_KHIEUNAI_Insert_V3 ~ error:', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setNHAP_KHIEUNAI_Insert_V4 ({ commit }, data) {
    try {
      const response = await apiManager.setNHAP_KHIEUNAI_Insert_V4(data)
      return response
    } catch (error) {
      console.log('🚀 ~ file: actions.js:320 ~ setNHAP_KHIEUNAI_Insert_V4 ~ error:', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setSUA_KHIEUNAI_Update_V2 ({ commit }, data) {
    try {
      const response = await apiManager.setSUA_KHIEUNAI_Update_V2(data)
      return response
    } catch (error) {
      console.log('setSUA_KHIEUNAI_Update_V2 error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setUPDATE_LYDO_KHONGXL_DUOC ({ commit }, data) {
    try {
      const response = await apiManager.setUPDATE_LYDO_KHONGXL_DUOC(data)
      return response
    } catch (error) {
      console.log('setUPDATE_LYDO_KHONGXL_DUOC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setCAPNHAT_GIAMCUOC ({ commit }, data) {
    try {
      const response = await apiManager.setCAPNHAT_GIAMCUOC(data)
      return response
    } catch (error) {
      console.log('setCAPNHAT_GIAMCUOC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_HG_GQKN ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_HG_GQKN(data)
      return response
    } catch (error) {
      console.log('getLAY_HG_GQKN error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setGIAOPHIEU_KN_KHI_HC ({ commit }, data) {
    try {
      const response = await apiManager.setGIAOPHIEU_KN_KHI_HC(data)
      return response
    } catch (error) {
      console.log('setGIAOPHIEU_KN_KHI_HC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setUpdate_KhoaPhieu_BaoTon_HoanThanh ({ commit }, data) {
    try {
      const response = await apiManager.setUpdate_KhoaPhieu_BaoTon_HoanThanh(
        data
      )
      return response
    } catch (error) {
      console.log('setUpdate_KhoaPhieu_BaoTon_HoanThanh error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setCAPNHAT_LOI ({ commit }, data) {
    try {
      const response = await apiManager.setCAPNHAT_LOI(data)
      return response
    } catch (error) {
      console.log('setCAPNHAT_LOI error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setUPDATE_TT_DAGIAO ({ commit }, data) {
    try {
      const response = await apiManager.setUPDATE_TT_DAGIAO(data)
      return response
    } catch (error) {
      console.log('setUPDATE_TT_DAGIAO error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setCAPNHAT_TIEPNHAN_KHOAPHIEU ({ commit }, data) {
    try {
      const response = await apiManager.setCAPNHAT_TIEPNHAN_KHOAPHIEU(data)
      return response
    } catch (error) {
      console.log('setCAPNHAT_TIEPNHAN_KHOAPHIEU error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setCAPNHAT_TT_PHIEUKN_HC ({ commit }, data) {
    try {
      const response = await apiManager.setCAPNHAT_TT_PHIEUKN_HC(data)
      return response
    } catch (error) {
      console.log('setCAPNHAT_TT_PHIEUKN_HC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setUPDATE_XULY ({ commit }, data) {
    try {
      const response = await apiManager.setUPDATE_XULY(data)
      return response
    } catch (error) {
      console.log('setUPDATE_XULY error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async setCapNhat_PAXL_KN ({ commit }, data) {
    try {
      const response = await apiManager.setCapNhat_PAXL_KN(data)
      return response
    } catch (error) {
      console.log('setCapNhat_PAXL_KN error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLay_thongtin_kn_theo_ma_tb ({ commit }, data) {
    try {
      const response = await apiManager.getLay_thongtin_kn_theo_ma_tb(data)
      return response
    } catch (error) {
      console.log('getLay_thongtin_kn_theo_ma_tb error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getKIEMTRA_TT_MaTB ({ commit }, data) {
    try {
      const response = await apiManager.getKIEMTRA_TT_MaTB(data)
      return response
    } catch (error) {
      console.log('getKIEMTRA_TT_MaTB error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_THANG_GC ({ commit }, data) {
    try {
      const response = await apiManager.getDS_THANG_GC(data)
      return response
    } catch (error) {
      console.log('getDS_THANG_GC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getKY_APDUNG ({ commit }, data) {
    try {
      const response = await apiManager.getKY_APDUNG(data)
      return response
    } catch (error) {
      console.log('getDS_THANG_GC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getKHIEUNAI_BS ({ commit }, data) {
    try {
      const response = await apiManager.getKHIEUNAI_BS(data)
      return response
    } catch (error) {
      console.log('getKHIEUNAI_BS error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getTAODULIEU_TIENGC ({ commit }, data) {
    try {
      const response = await apiManager.getTAODULIEU_TIENGC(data)
      return response
    } catch (error) {
      console.log('getTAODULIEU_TIENGC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDULIEU_TIENGC ({ commit }, data) {
    try {
      const response = await apiManager.getDULIEU_TIENGC(data)
      commit(types.SET_STATE, {
        key: 'DsGC',
        value: response.data
      })
      return response
    } catch (error) {
      console.log('getDULIEU_TIENGC error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getHOSO_KHIEUNAI ({ commit }, data) {
    try {
      const response = await apiManager.getHOSO_KHIEUNAI(data)
      return response
    } catch (error) {
      console.log('getHOSO_KHIEUNAI error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getHUONGGIAO_QUYTRINH ({ commit }, data) {
    try {
      const response = await apiManager.getHUONGGIAO_QUYTRINH(data)
      return response
    } catch (error) {
      console.log('getHUONGGIAO_QUYTRINH error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLAY_DS_HUONGGIAO ({ commit }, data) {
    try {
      const response = await apiManager.getLAY_DS_HUONGGIAO(data)
      return response
    } catch (error) {
      console.log('getLAY_DS_HUONGGIAO error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_DIACHI_THEO_KHIEUNAI_ID ({ commit }, data) {
    try {
      const response = await apiManager.getDS_DIACHI_THEO_KHIEUNAI_ID(data)
      return response
    } catch (error) {
      console.log('getDS_DIACHI_THEO_KHIEUNAI_ID error', error)
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLay_nguyennhan_kn_theo_chude ({ commit }, data) {
    try {
      const response = await apiManager.getLay_nguyennhan_kn_theo_chude(data)
      commit(types.SET_STATE, { key: 'DS_KQ_LOI_KN', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getLay_phuongan_xl_theo_chude ({ commit }, data) {
    try {
      const response = await apiManager.getLay_phuongan_xl_theo_chude(data)
      commit(types.SET_STATE, { key: 'DS_PAXL', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI_DAGIAO_V2 ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI_V2(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI_DAGIAO', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
  async getDS_TB_KHIEUNAI_V2 ({ commit }, data) {
    try {
      const response = await apiManager.getDS_TB_KHIEUNAI_V2(data)
      commit(types.SET_STATE, { key: 'DS_TB_KHIEUNAI', value: response.data })
      return response
    } catch (error) {
      if (error.response && error.response.status === 400) {
        throw new Error(error.response.data.message_detail)
      }
      throw error
    }
  },
}
