export default {
  getDanhSachThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-mo-may/ma-thue-bao", { params: data }),
  capNhatTB: (axios, data) => axios.put("/web-thuno/api/thu-no/xu-ly-thue-bao/chuyen-sang-hop-dong-mo-may", data),
  getThongTinThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thue-bao", { params: data }),
  getQuyTrinh: (axios, data) => axios.get("/web-thuno/api/thu-no/common/lay-quy-trinh-theo-hop-dong", { params: data }),
};
