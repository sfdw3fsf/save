export default {
  traCuuKhachHangTheoMa: (axios, data) =>
    axios.get(
      "web-thuno/api/thu-no/sp-lay-thong-tin-khach-hang-theo-ma-khach-hang",
      { params: data }
    ),
  traCuuKhachHangTheoMaInGhep: (axios, data) =>
    axios.get("web-thuno/api/thu-no/sp-lay-thong-tin-khach-hang-theo-mig", {
      params: data
    }),
  getDSThanhToanTheoMaIG: (axios, data) =>
    axios.get("web-thuno/api/thu-no/sp-lay-danh-sach-thanh-toan-in-ghep", {
      params: data
    }),
  traCuuThanhToan: (axios, data) =>
    axios.post("/web-cskh/api/tra-cuu/thanh-toan", data),
  traCuuLienHe: (axios, data) => axios.get("/web-cskh/api/lien-he/" + data)
};
