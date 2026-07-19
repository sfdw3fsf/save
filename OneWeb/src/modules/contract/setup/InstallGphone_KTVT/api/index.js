export default {
  getDanhSachHDTBGrid: (axios, data) => axios.post('/web-ccdv/thicong_ghone/lay_ds_hoancong_gphone_119', data),
  getComboNguoiGiaoViec: (axios, data) => axios.post('/web-ccdv/thicong_ghone/sp_ds_nguoi_giaoviec', data),
}
