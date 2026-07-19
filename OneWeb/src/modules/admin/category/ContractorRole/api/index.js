
export default{
  layDanhSachVaiTro: (axios, data) => axios.get('/web-quantri/vaitro-tothau/sp_lay_ds_vaitro'),
  luuVaiTro: (axios, data) => axios.post('/web-quantri/vaitro-tothau/sp_capnhat_vaitro', data),
  xoaVaiTro: (axios, data) => axios.post('/web-quantri/vaitro-tothau/sp_xoa_vaitro', data),
  getKeys: (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data)
}
