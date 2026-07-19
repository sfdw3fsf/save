export const API = {
  // getDSNhanVienXuLyKN: async (axios) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsNhanVienXuLyKN'),
  // getDSDichVuVT: async (axios) => axios.post('web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
  // getDSLoaiHinhThueBao: async (axios, dichVuVTID) => axios.post('/web-gqkn/giaophieu-nhanvien/get-loaihinh-thuebao', {pDichVuId: dichVuVTID}),
  // getDSChuDe: async (axios) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsChuDe'),
  // getDSTrangThaiKN: async (axios) => axios.post('/web-gqkn/giaophieu-nhanvien/get-trangthai-kn'),
  // getDSKhieuNaiDaGiao: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/ds-khieunai-dagiao', params),
  // getDSKhieuNaiChuaGiao: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/ds-khieunai-chuagiao', params),
  // giaoNhanVien: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/giaophieu', params),
  // huyGiaoNhanVien: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/huy-giaophieu', params),
  // layPhieuNVTuDong: async (axios) => axios.post('/web-gqkn/giaophieu-nhanvien/layphieu-tudong'),
}

export const TT_DX = {
  DX_GTC    : 16,
  PDDX_GTC  : 17,
  TDDX_GTC  : 18,
  PD_KQTD   : 19,
  PD_GTC    : 20,
}


export const OPTIONS = {
  KN_CHUAGIAO: {
    TAT_CA: '0',
    PHIEU_MOI: '1',
    PHIEU_BI_TRA: '3'
  },
  KN_DAGIAO: {
    TAT_CA: '0',
    DANG_XULY: '2',
    DA_XULY: '4' 
  },
  YKIEN_TD: [
    {ID: 1, TEXT: 'Đồng ý'},
    {ID: 0, TEXT: 'Không đồng ý'},
  ],
  NAV_TAB: {
    CHUA_GIAO: 1,
    DA_GIAO: 2,
  }
}