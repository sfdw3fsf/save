export default {
  // getDanhSachNVThuCuoc: (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc', {params: data}),
  getTyLeThuNVThuCuoc: (axios, data) =>
    axios.post("/web-thuno/api/thu-no/sp-ty-le-thu-nhan-vien-thu-cuoc", data),
  getDanhSachTinhCuocTN: (axios, data) =>
    axios.get(`/web-thuno/api/thu-no/fn-lay-danh-sach-tinh-cuoc-thu-ngan?pNguoiDungId=${data}`)
};
