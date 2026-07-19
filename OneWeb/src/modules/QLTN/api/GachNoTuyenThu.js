export default {
  getDSThuQuy: (axios) => axios.get("/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai"),
  getDSLuotTra: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/luot-tra", {params: data}),
  getDSLuotGiao: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-dai-ly/luot-giao", {params: data}),
  getDSChiTietNoPS: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-chi-tiet-no-phat-sịnh/tb-giao-phieu", {params: data}),
  getDSGachNoDaiLy: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/ds-no-theo-tuyen-thu", {params: data}),
  gachNoDSTuyenThu: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/gach-no-tuyen-thu", data),
  getDSLuotTraTheoPhieuGiao: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/ds-luot-thu-theo-phieu-giao", {params: data}),

  /* DANH SACH PHIEU TRA */
  getDanhSachNVTCTheoTuyen: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/ds-luot-thu-theo-phieu-giao", {params: data}),
  getDSLuotTra: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/luot-tra", {params: data}),
  getDSPhieuTT: (axios, data) => axios.get("/web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/lan-tra", {params: data}),
  /*
  getHTTTMaVach: (axios) => axios.get("/web-thuno/api/thu-no/common/ds-httt-gach-ma-vach"),
  getDVQLTheoQuayThu: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-don-vi-quan-ly-theo-quay-thu", {params : data}),
  getThongTinTTTheoMaVach: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-thong-tin-thanh-toan-theo-ma-vach", {params : data}),
  getDSGachMaVach: (axios, data) => axios.get("/web-thuno/api/quan-ly-thu-no/lay-ds-da-quet-ma-vach", {params : data}),
  gachNoMaVach: (axios, data) => axios.put("/web-thuno/api/quan-ly-thu-no/gach-no-ma-vach", data),
  */
}
