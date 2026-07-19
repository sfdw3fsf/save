export default {
  danh_sach_tinh_ccbs: async (axios, data) => axios.post('/ccbs/executor/ts_dm_dstinh', data),
  danh_sach_kieu_hien_thi_ccbs: async (axios, data) => axios.post('/ccbs/danhMuc/ts_danhmuc_cuoccao_kieu_hienthi', data),
  danh_sach_loai_bang_cuoc_cao: async (axios, data) => axios.post('/ccbs/danhMuc/ts_danhmuc_cuoccao_loaibang', data),
  danh_sach_cuoc_cao: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_cuoccao', data),
  danh_sach_cuoc_cao_xu_ly: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_cuoccao_xuly', data)
}
