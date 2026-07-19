export default{
  // getDanhSachLoaiHinhTB: (axios, params) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', { params }),
  fn_map_id: (axios, data) => axios.post(`/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_map_id`, data),
  gphone_reset_cell_list: (axios, data) => axios.post(`/ccbs/gphone/reset_cell_list`, data),
  gphone_reset_cell: (axios, data) => axios.post(`/ccbs/gphone/reset_cell_subs`, data),
}