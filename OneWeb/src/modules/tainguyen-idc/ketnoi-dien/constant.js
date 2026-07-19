export const PORT_TYPE = {
  INTERFACE: {
    ID: 1,
    NAME: 'Cổng dữ liệu'
  },
  POWER: {
    ID: 2,
    NAME: 'Cổng nguồn'
  },
  CONSOLE: {
    ID: 3,
    NAME: 'Cổng điều khiển'
  }
}
export const TRANG_THAI_KN_VL = {
  DA_KET_NOI: {
    ID: 1,
    NAME: 'Đã kết nối'
  },
  DA_LOAI_BO: {
    ID: 0,
    NAME: 'Chưa kết nối'
  }
}
export const TRANG_THAI_KN_VH = {
  UP: {
    ID: 1,
    NAME: 'Up'
  },
  DOWN: {
    ID: 0,
    NAME: 'Down'
  }
}

export const TRANG_THAI_PHIEU = {
  HOAN_THANH: 1,
  TRA_PHIEU: 2
}

export const port_type_options = Array.from(Object.values(PORT_TYPE)).map((item) => ({
  id: item.ID,
  text: item.NAME
}))

export const trang_thai_kn_vl_options = Array.from(Object.values(TRANG_THAI_KN_VL)).map((item) => ({
  id: item.ID,
  text: item.NAME
}))

export const trang_thai_kn_vh_options = Array.from(Object.values(TRANG_THAI_KN_VH)).map((item) => ({
  id: item.ID,
  text: item.NAME
}))

export const LOAI_KETNOI = {
  KN_DIEN: {
    id: 1,
    text: 'Kết nối điện'
  },
  KN_MANG: {
    id: 2,
    text: 'Kết nối mạng'
  }
}

export const LOAI_DANH_MUC = {
  DS_DONVI: 'IDC-DS-DONVI',
  IDC_BY_DONVI: 'IDC-IDC-BY-DONVI-ID',
  LOP_KET_NOI: 'IDC-LOPKN',
  LOAI_VAT_LIEU_KN: 'IDC-LOAIVATLIEUKN',
  PORT_BY_TBI_AND_PHANLOAI: 'IDC-PORT-BY-TBI-ID-AND-PHANLOAI',
  TBI_BY_IDC_ID: 'IDC-TBI-BY-IDC',
  HINH_THUC_KN: 'IDC-HINHTHUC-BY-LOAIKN',
  ALL_IDC: 'IDC-DONVI',
  IDC_NHANVIEN_QLY: 'IDC-NHANVIEN-QLY',
  IDC_MANG_TBI: 'IDC-MANG-TBI',
  IDC_LOAI_TBI: 'IDC-LOAI-TBI',
  IDC_HE_THONG: 'IDC-HETHONG-TBI',
  TRANGTHAI_THICONG_IDC: 'TRANGTHAI-THICONG-IDC',
  IDC_THANHNGUON_RACK: 'IDC-THANHNGUON-BY-RACK',
  IDC_OCAM_THANHNGUON: 'IDC-OCAM-BY-THANHNGUON',
  IDC_PATCHPANNEL_RACK: 'IDC-TBI-PATCH-PANNEL-BY-RACK',
}
