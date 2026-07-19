import DateTimeLib from "../../../../utils/DateTimeLib";

export const API = {
  getDSNhanVienXuLyKN: async (axios) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsNhanVienXuLyKNV2'),
  getDSDichVuVT: async (axios) => axios.post('web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
  getDSLoaiHinhThueBao: async (axios, dichVuVTID) => axios.post('/web-gqkn/giaophieu-nhanvien/get-loaihinh-thuebao', {pDichVuId: dichVuVTID}),
  getDSChuDe: async (axios) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsChuDe'),
  getDSTrangThaiKN: async (axios) => axios.post('/web-gqkn/giaophieu-nhanvien/get-trangthai-kn'),
  getDSKhieuNaiDaGiao: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/ds-khieunai-dagiao', params),
  getDSKhieuNaiChuaGiao: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/ds-khieunai-chuagiao', params),
  giaoNhanVien: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/giaophieu', params),
  huyGiaoNhanVien: async (axios, params) => axios.post('/web-gqkn/giaophieu-nhanvien/huy-giaophieu', params),
  layPhieuNVTuDong: async (axios) => axios.post('/web-gqkn/giaophieu-nhanvien/layphieu-tudong'),
}

export const LOAI_TRACUU = [
  {ID: 1, TEN: 'Số máy/Acc'},
  {ID: 2, TEN: 'Mã Giao dịch'},
  {ID: 3, TEN: 'Mã hợp đồng'},
  {ID: 4, TEN: 'Mã Khách hàng'},
  {ID: 5, TEN: 'Tên khách hàng'},
  {ID: 6, TEN: 'Mã thanh toán'},
  {ID: 7, TEN: 'Tên thanh toán'},
  {ID: 8, TEN: 'Mã số thuế'},
  {ID: 9, TEN: 'Địa chỉ lắp đặt'},
  {ID: 10, TEN: 'Tài khoản'},
  {ID: 11, TEN: 'Số giấy tờ'},
  {ID: 12, TEN: 'Số ảo'},
  {ID: 13, TEN: 'Điện thoại liên hệ'},
  {ID: 14, TEN: 'Mã HĐ TTKD'},
  {ID: 15, TEN: 'Tên miền'},
  {ID: 16, TEN: 'Site ID'},
]

export const CONSTANT = {
  CHUA_GIAO: 1,
  DA_GIAO: 0,
}