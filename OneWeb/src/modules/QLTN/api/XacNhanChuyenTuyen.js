import axios from "axios";

export default {
  getDSNhanVienThuCuoc: async (axios, data) => axios.get('/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc', {
    params: data
  }),
  getDSChuyenTuyen: async (axios, data) => axios.get('/web-thuno/api/thu-no/xac-nhan-chuyen-tien/lay-danh-sach-phieu-chuyen-tuyen', {
    params: data
  }),
  putXacNhanPhieuChuyenTuyen: async (axios, data) => axios.put('/web-thuno/api/thu-no/xac-nhan-chuyen-tien/xac-nhan-phieu-chuyen-tuyen', data),
  putHuyXacNhanPhieuChuyenTuyen: async (axios, data) => axios.put('/web-thuno/api/thu-no/xac-nhan-chuyen-tien/huy-xac-nhan-phieu-chuyen-tuyen', data),
}
