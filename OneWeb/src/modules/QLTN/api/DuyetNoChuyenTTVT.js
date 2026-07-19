export default {
  getDSDuyetXnKhoaMay: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-duyet-xac-nhan-khoa-may", { params: data }),
  giaophieuXacNhanTTVT: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-xac-nhan-ttvt-v2", { params: data }),
  getDanhSachCongNoTheoMaVach: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-theo-ma-vach", { params: data }),
  updateXacNhanKetQuaCongNoTheoId: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/xac-nhan-ket-qua-cong-no", data),
  getDanhSachCongNoDaGan: (axios, data) => axios.get("/web-thuno/api/thu-no/cong-no/ds-cong-no-da-gan", { params: data }),
  getDanhSachDonViXacNhanNo: (axios) => axios.get("/web-thuno/api/thu-no/xu-ly-no/don-vi-xac-nhan-no"),
  updateGiaoPhieuNhanVienXacNhan: (axios, data) => axios.put("/web-thuno/api/thu-no/cong-no/giao-phieu-nv-xac-nhan-cong-no", data)
};
