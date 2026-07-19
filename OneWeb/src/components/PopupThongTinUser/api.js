export default {
  getDanhSachEmail: async (axios, data) => {
    return axios.post(`web-quantri/khoamodichvu/sp_lay_ds_email`, data);
  },
  getDanhSachUser: async (axios, data) => {
    return axios.post(`web-quantri/khoamodichvu/sp_lay_ds_user`, data);
  },
  getLoaiUser: async axios => {
    return axios.get(`web-quantri/khoamodichvu/get_loai_user`);
  },
  getDanhSachHopDongUser: async (axios, data) => {
    return axios.post(
      `web-hopdong/thanhly-thuebao-bancheo/lay_ds_hopdong_user`,
      data
    );
  }
};
