export default {
  getDanhSachNVThuCuoc: async (axios, data) => axios.get('web-thuno/api/thu-no/common/nhan-vien-thu-cuoc', { params: data }),
  getDanhSachNVThuCuocDungHuong: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/nvtc-dung-huong', { params: data }),
  getDanhSachHoaDon: async (axios, data) => axios.get('web-thuno/api/thu-no/quan-ly-dai-ly/hoa-don/nhan-vien-thu-cuoc', { params: data }),
  chuyenPhieu: async (axios, data) => axios.put('web-thuno/api/thu-no/quan-ly-dai-ly/chuyen-phieu-lac-huong', data),
}
