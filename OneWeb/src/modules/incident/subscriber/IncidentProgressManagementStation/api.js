export default {
  getDSPhieuYeuCau: async (axios, data) => {
    return axios.post(
      `web-gqkn/xu-ly-khieu-nai/sp_lay_danhsach_tientrinh_baohong`,
      data
    );
  },
  deleteTienTrinhBaoHong: async (axios, data) => {
    return axios.get(`web-gqkn/xu-ly-khieu-nai/fn_xoa_tientrinh_baohong`, {
      params: data
    });
  },
  editTienTrinhBaoHong: async (axios, data) => {
    return axios.post(
      `web-gqkn/xu-ly-khieu-nai/fn_capnhat_tientrinh_baohong`,
      data
    );
  },
  getDSTienTrinhBaoHong: async (axios, data) => {
    return axios.get(`web-gqkn/xu-ly-khieu-nai/sp_lay_ds_tientrinh_baohong`, {
      params: data
    });
  },
  getDanhSachLoaiHinhTB: axios =>
    axios.get("/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"),
  getDichVuVTList: axios =>
    axios.post("/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_dvvt")
};
