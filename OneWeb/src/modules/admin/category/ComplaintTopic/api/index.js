export default{
  layDanhSachKhuyenMai: (axios, data) => axios.post('/web-quantri/khieunai/lay_ds_chude_kn_khoitao', data),
  apiRecord: (axios, data) => axios.post('/web-quantri/khieunai/capnhat_ghilai_chude_kn', data),
  apiXoa: (axios, data) => axios.post('/web-quantri/khieunai/del_chude_kn', data)
}
