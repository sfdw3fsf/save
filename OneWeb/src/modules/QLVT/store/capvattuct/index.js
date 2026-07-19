import { actions } from "./actions";
import { getters } from "./getters";
import { mutations } from "./mutations";

const state = {
  NhomCT: [],
  LoaiTBi: [],
  LoaiCT: [],
  Kho: [],
  danhMucVT_DVT: [],
  kieuThietBi: [],
  tinhTrang: [],
  listCongTrinhCapVT: [],
  listChungLoaiTB: [],
  ds_LSVT: [],
  DSVatTuTrongKho_CT: [],
  DSVatTuSD_CT: [],
  value_ins_vattu_congtrinh_v2: "",
  value_kiemtra_vt_thuhoi: [],
  value_ThongTinThueBaoTheoSerial: [],
  value_KiemTraTB_SDVT: [],
  value_ThietBi_DaCap: [],
  value_capnhat_sdvt_th: "",
  value_SL_DD: "",
  value_SL_HT: "",
  value_CAPNHAT_PHIEUVT_THUHOI: "",
};

const namespaced = true;

export const capvattuct = {
  namespaced,
  state,
  getters,
  actions,
  mutations
};
