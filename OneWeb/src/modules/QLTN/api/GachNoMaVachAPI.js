export default {
  getHTTTMaVach: (axios) => axios.get("/web-thuno/api/thu-no/common/ds-httt-gach-ma-vach"),
  getDVQLTheoQuayThu: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-don-vi-quan-ly-theo-quay-thu", {params : data}),
  getThongTinTTTheoMaVach: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-thong-tin-thanh-toan-theo-ma-vach", {params : data}),
  getDSGachMaVach: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ds-da-quet-ma-vach", {params : data}),
  gachNoMaVach: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/gach-no-ma-vach", data),
  xoaKhoiDanhSach: (axios, data) => axios.post("/web-thuno/api/quan-ly-thu-no/xoa-gach-no-ma-vach", data),
}
