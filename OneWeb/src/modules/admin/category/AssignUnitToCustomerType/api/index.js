export default {
  getTypeofUnitList: (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_DVI'),
  getUnitList: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_dvi', data),
  getTypeofCustomerList: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_loaikh', data),
  save: (axios, data) => axios.post('/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_update', data)
}
