import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
  loaiDSThueBaoKhongKhaoSat: [
    // { value: '', text: 'Loại thuê bao' },
    // { value: '6', text: 'Thuê bao không khảo sát Lắp mới' },
    // { value: '7', text: 'Thuê bao không khảo sát Báo hỏng' }
  ],
  loadDanhMucVT: [],
  dsDichVu: [],
  cayDonVi: [],
  dichVuVienThongEnum: {
    TATCA: -1,
    CO_DINH: 1,
    DI_DONG: 2,
    ADSL: 4,
    DD_TRATRUOC: 5,
    EMAIL: 6,
    METRONET: 7,
    MEGAWAN: 8,
    TSL: 9,
    GPHONE: 10,
    IMS: 11,
    MEGA_EYES: 12,
    HOINGHI_TRUYENHINH: 13,
    ANTOAN_BAOMAT_TT: 14,
    DICHVU_CNTT: 15,
    TRUNGTAM_DULIEU: 16,
    HATANG_VIENTHONG: 19,
  
    IMS_DOITAC: 21,
    BRCD_DOITAC: 22,
    MGW_DOITAC: 23,
    TSL_DOITAC: 24,
  }
}

const namespaced = true

export const cskhCommon = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
