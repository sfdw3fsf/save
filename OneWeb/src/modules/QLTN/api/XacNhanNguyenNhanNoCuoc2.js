export default {
  getDanhMucDaiLy: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc/' + data),
  getDSPhieuDangXacMinh: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-dang-xac-minh/' + data),
  getDSPhieuXacMinhNoCuoc: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-no-cuoc', {params: data}),
  getDSPhieuXacMinhChoXuLy: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-cho-xu-ly', {params: data}),
  getDSNhanVienTheoThanhToan: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhan-vien/thanh-toan/' + data),
  getDSPhieuXacMinhDaXuLy: (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-phieu-xac-minh-da-xu-ly/' + data),
  giaoPhieuXLNhatKyThuNo: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/giao-phieu-xu-ly-nhat-ky-thu-no', data),
  hoanthienXLNhatKyThuNo: (axios, data) => axios.put('/web-thuno/api/thu-no/quan-ly-dai-ly/hoan-thien-xu-ly-nhat-ky-thu-no', data),
  updateHoanThanhThongTinYeuCau: (axios, data) => axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/xac-nhan-yeu-cau-khoa-mo/cap-nhat-hoan-thanh-trang-thai-xac-nhan-khoa-may', data),
}
