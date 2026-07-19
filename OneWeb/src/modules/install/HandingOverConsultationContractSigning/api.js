export default {
  getDanhSachTuVan: (axios, body) => axios.post('web-thicong/giaophieu-kyhd/tuvan_ds_phieu_tiepnhan_v2', body),
  getDanhSachGiaoViec: (axios, body) => axios.post('web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', body)
}
