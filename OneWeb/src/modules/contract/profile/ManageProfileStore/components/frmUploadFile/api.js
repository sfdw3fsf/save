export default {
  lay_ds_hd_tb_theo_hdkh_id: async (axios, data, header) => axios.post('/web-hopdong/hopdong/lay_ds_hd_tb_theo_hdkh_id', data, header),
  lay_ds_filehs_theo_kh: async (axios, data, header) => axios.get('web-hopdong/QLBoHS/lay_ds_filehs_theo_kh', data, header),
  nhom_file: async (axios, data, header) => axios.get('/web-quantri/danhmuc/hoso/nhom_file', data, header),
  loai_file: async (axios, data, header) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data, header),
  upload_file_hoso: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),
  fn_capnhat_file_hstb:(axios,data) => axios.post('/web-hopdong/hopdong/fn_capnhat_file_hstb', data),
  xoa_file: (axios, data) => axios.post('/web-quantri/upload/xoa_file', data),
}
