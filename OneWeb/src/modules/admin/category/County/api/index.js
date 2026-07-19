export default {
  cboTinhThanh_Load: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH', data,header),
  cboQuanHuyen_Load: async (axios, data, header) => axios.get('/web-quantri/danhmuc/diaban/quan', data,header),
  Hienthi_danhsach: async (axios, data, header) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan', data,header),
  xoa_phuong: async (axios, data, header) => axios.post('/web-quantri/diaban/xoa-phuong', data,header),
  them_phuong: async (axios, data, header) => axios.post('/web-quantri/diaban/them-phuong', data,header),
  sua_phuong: async (axios, data, header) => axios.post('/web-quantri/diaban/sua-phuong', data,header),
  kiemtra_dl_phuong: async (axios, data, header) => axios.post('/web-quantri/diaban/kiemtra-dl-phuong', data,header),
  valid_dl_phuong: async (axios, data, header) => axios.post('/web-quantri/diaban/valid-dl-phuong', data,header),
  getKey: async (axios, data, header) => axios.post('/web-qlvt/api/cap-phat-ccdc/get-key', data,header),

  dongbo_diadanh_ccbs: async (axios, data) => axios.get('/web-quantri/diaban/dongbo_diadanh_ccbs', data),
  ts_danhmuc_insert_phuongs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_insert_phuongs', data),
  ts_danhmuc_update_phuongs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_update_phuongs', data),
  ts_danhmuc_delete_phuongs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_delete_phuongs', data),
}
