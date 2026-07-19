export default {
  getDSNguoiGV: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/common/ds-nhan-vien/don-vi/" + data
    ),
  getDSKhoaMayLoi: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/quan-ly-dai-ly/ds-hdtb-khoa-may-loi", {params: data}
    ),
  getDSNhanVienTheoPhieu: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhan-vien-theo-phieu-id", {params: data}
    ),
  getThueBaoHaTocDo: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/tt-thue-bao-ha-toc-do/' + data),
  nangTocDoTB: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/nang-toc-do/' + data),
  getHuongGiaoTheoQuyTrinh: (axios, data) =>
    axios.get(
      "/web-thuno/api/thu-no/css/huong-giao/quy-trinh", {params: data}
    ),
  getHuongGiaoTheoLuong: (axios, data) => axios.get('/web-thuno/api/thu-no/xu-ly-thue-bao/ds-huong-giao/luong/' + data),
  getThaoTacTheoLuong: (axios, data) => axios.get('/web-thuno/api/thu-no/common/thao-tac/luong/' + data),
  getDSControlTheoThaoTac: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ds-control/thao-tac/' + data),
  capnhat: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-giao-viec-tu-dong', data),
  hoancong: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/hoan-cong', data),
  hoanthanh: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/hoan-thanh-mo-may-tu-dong-loi', data),

  hoanthienHS: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/hoan-thien-ho-so-thay-doi-dv/' + data),


  xoaHopDongKhachHang: (axios, data) => axios.get('/web-thuno//api/thu-no/quan-ly-dai-ly/hop-dong-khach-hang-v2', {params: data}),

};
