export default{
  lay_danhsach_donvi_phathanh: async (axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_phathanh', data),
  lay_loai_cv: async (axios, data) => axios.post('/web-tracuu/socongvan/sp_lay_loai_cv', data),
  tra_cuu_CongVan: (axios, data) => axios.post('web-khdn/doanhthu/tracuu_congvan', data),
  getDsFileDinhKem: async(axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_file_congvan?p_congvan_id='+ data),
  download_file: async (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
}
