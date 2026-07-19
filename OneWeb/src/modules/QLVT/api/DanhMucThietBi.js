export default {
  getDSLoaiHinhTB: (axios, dichVuVtId, loaiTbiId, chk) =>
    axios.get(
      `/web-qlvt/api/danh-muc-thiet-bi/ds-loai-hinh-thue-bao/${dichVuVtId}/${loaiTbiId}/${chk}`
    ),
  getDSNhomTB: axios =>
    axios.get("/web-qlvt/api/danh-muc-thiet-bi/ds-nhom-thiet-bi"),
  getDSDVT: axios =>
    axios.get("/web-qlvt/api/danh-muc-thiet-bi/ds-don-vi-thiet-bi"),
  getDSLoaiPhi: axios =>
    axios.get("/web-qlvt/api/danh-muc-thiet-bi/ds-loai-phi"),
  getDSTyLeVat: axios =>
    axios.get("/web-qlvt/api/danh-muc-thiet-bi/ds-ty-le-vat"),
  getDSDichVuVT: (axios, nguoiDungId) =>
    axios.get(
      `/web-qlvt/api/danh-muc-thiet-bi/ds-dich-vu-vien-thong/${nguoiDungId}`
    ),
  deleteLTBLoaiHinhTB: (axios, loaiTbiId) =>
    axios.delete(
      `/web-qlvt/api/danh-muc-thiet-bi/xoa-loai-tbi-loai-hinh-tbao/${loaiTbiId}`
    ),
  deleteLTB: (axios, loaiTbiId) =>
    axios.delete(`/web-qlvt/api/danh-muc-thiet-bi/xoa-loai-tbi/${loaiTbiId}`),
  getDKBangLBT: (axios, nguoiDungId, tenBang) =>
    axios.post(
      `/web-qlvt/api/danh-muc-thiet-bi/xoa-loai-tbi/${nguoiDungId}/${tenBang}`
    ),
  getKeyLBT: (axios, data) =>
    axios.post("/web-qlvt/api/cap-phat-ccdc/get-key", data),
  createDSLTB: (axios, data) =>
    axios.post("/web-qlvt/api/danh-muc-thiet-bi/tao-ds-loai-tbi", data),
  createLTBLoaiHinhTB: (axios, loaiTbiId, loaiTbId) =>
    axios.post(
      `/web-qlvt/api/danh-muc-thiet-bi/tao-ds-loai-tbi-lh-thue-bao?loaiTbiId=${loaiTbiId}&loaiTbId=${loaiTbId}`
    ),
  updateDSLTB: (axios, data) =>
    axios.put("/web-qlvt/api/danh-muc-thiet-bi/cap-nhat-loai-thiet-bi", data),
  ganLoaiPhi: (axios, loaiTbiId, loaiPhiId) =>
    axios.put(
      `/web-qlvt/api/danh-muc-thiet-bi/cap-nhat-gan-loai-phi?loaiTbiId=${loaiTbiId}&loaiPhiId=${loaiPhiId}`
    ),
  getDSDanhMucLoaiThietBi: axios =>
    axios.get("/web-qlvt/api/loai-thiet-bi/ds-loai-thiet-bi")
};
