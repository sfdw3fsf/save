export default {
  getDanhSachLanTao: (axios, data) => axios.get("/web-thuno/api/thu-no/common/lan-tao-xu-ly-no", { params: data }),
  getDanhSachMauIn: (axios, data) => axios.get("/web-thuno/api/thu-no/common/mau-in", { params: data }),
  getDanhSachInThongBao: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-no/thong-bao-no/535", { params: data }),
  getDanhSachInThongBaoLan2: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-no/thong-bao-no-lan2", { params: data }),
};
