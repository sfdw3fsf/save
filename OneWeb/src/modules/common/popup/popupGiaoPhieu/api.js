export default {
  getDanhSachEmail: async (axios, data) => {
    return axios.post(`web-quantri/khoamodichvu/sp_lay_ds_email`, data);
  },
  getDanhSachDonvichutri: async (axios, data) => {
    return axios.post(`web-net/thongtinchung/dsDanhMucChung`, data);
  },
  getDSUser: async (axios, data) => {
    return axios.post(`web-net/thongtinchung/dsNhanVienXuLy`, data);
  },
  giaophieu: async (axios, data) => {
    return axios.post(
      `web-net/thongtinchung/themMoiPhieuGiao`,
      data
    );
  } 
};  
