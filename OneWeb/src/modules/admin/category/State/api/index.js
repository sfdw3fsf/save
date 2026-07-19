export default {
  getCityList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_TINH", data),

  getDistrictList: (axios, data) => {
    return axios.get("/web-quantri/danhmuc-chung/CSS_QUAN_CA_NUOC");
  },

  checkStateExist: (axios, data) => {
    return axios.post("/web-quantri/diaban/kiemtra-dl-quan", data);
  },

  createState: (axios, data) => {
    return axios.post("/web-quantri/diaban/them-quan", data);
  },

  updateState: (axios, data) => {
    return axios.post("/web-quantri/diaban/sua-quan", data);
  },

  deleteState: (axios, data) => {
    return axios.post("/web-quantri/diaban/xoa-quan", data);
  }
};
