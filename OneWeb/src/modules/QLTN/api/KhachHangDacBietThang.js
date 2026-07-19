export default {
  getDanhSachThueBao: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu-danh-ba/ds-khach-hang-dac-biet-theo-thang", { params: data }),
  capNhatThueBao: (axios, data) => axios.put("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-khach-hang-dac-biet", data),
  themMoiThueBao: (axios, data) => axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-khach-hang-dac-biet", data),
  xoaThueBao: (axios, data) => axios.delete("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-khach-hang-dac-biet", { data: data }),
  xoaThueBaoTheoFile: (axios, data) => axios.delete("/web-thuno/api/thu-no/xu-ly-thue-bao/danh-ba-khach-hang-dac-biet", { params: data }),
  kiemTraMaTB: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-thue-bao/kiem-tra-ma-tt", { params: data }),
};
