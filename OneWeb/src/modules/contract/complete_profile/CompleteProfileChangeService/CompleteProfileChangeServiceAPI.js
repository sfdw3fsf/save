export default{
  insertUpdatePhanLoaiDichVu: (axios, data) => axios.post('/web-quantri/phanloaidv/fn_phanloai_dichvu_update', data),
  deletePhanLoaiDichVu: (axios, data) => axios.post('/web-quantri/phanloaidv/fn_phanloai_dichvu_delete', data),
  getDanhSachDichVuVT: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', { params }),
  getDanhSachLoaiHinhTB: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),
  getPhanLoaiDichVu: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_PHANLOAI_DV', { params }),
}
