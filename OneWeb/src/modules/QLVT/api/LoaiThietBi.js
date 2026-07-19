export default {
  getDSNhomTB: axios =>
    axios.get("/web-qlvt/api/loai-thiet-bi/ds-nhom-thiet-bi"),
  addDuLieuDSTB: (axios, data) =>
    axios.post("/web-qlvt/api/loai-thiet-bi/tao-nhom-thiet-bi", data),
  getKeyNhomThietBi: (axios, data) =>
    axios.post("/web-qlvt/api/cap-phat-ccdc/get-key", data),
  updateDuLieuDSTB: (axios, data) =>
    axios.put("/web-qlvt/api/loai-thiet-bi/cap-nhat-nhom-thiet-bi", data),
  deleteDLNTB: (axios, nhomTbiId) =>
    axios.delete(`/web-qlvt/api/loai-thiet-bi/xoa-nhom-thiet-bi/${nhomTbiId}`)
};
