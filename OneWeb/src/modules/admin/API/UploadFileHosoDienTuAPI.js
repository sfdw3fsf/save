export default{
  get_danhsach_doituong_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_doituong_hoso', data),
  get_danhsach_hopdong_theo_matb: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_hopdong_theo_matb', data),
  get_danhsach_hoso_icon: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_hoso_icon', data),
  get_danhsach_lichsu_baohong_theo_matb: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_lichsu_baohong_theo_matb', data),
  get_danhsach_nhom_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_nhom_file_hoso', data),
  get_danhsach_loai_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_loai_file_hoso', data),
  get_danhsach_thuebao_theo_makh: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_thuebao_theo_makh', data),
  get_chitiet_file_hs: async (axios, data) => axios.post('/web-quantri/danhmuc/lay_chitiet_file_hs', data),
  get_ds_file_hs: async (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_file_hs', data),
  get_ds_hd_tb_theo_hdkh_id: async (axios, data) => axios.post('/web-quantri/danhmuc/lay_ds_hd_tb_theo_hdkh_id', data),
  tracuu_makh: async (axios, data) => axios.post('/web-quantri/danhmuc/tracuu_makh', data),
  cap_nhat_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/cap_nhat_file_hoso', data),
  gan_thuebao_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/gan_thuebao_file_hoso', data),
  them_moi_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/them_moi_file_hoso', data),
  xoa_file_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/xoa_file_hoso', data),
}
  