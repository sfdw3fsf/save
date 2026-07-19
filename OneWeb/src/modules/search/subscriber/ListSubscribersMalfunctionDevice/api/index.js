export default {
  // API
  sp_ds_loaihinh: (axios) => axios.get('/web-thicong/danhsach-tb-tbi-suco/sp_ds_loaihinh'),
  sp_chitiet_suco_baohong_fms: (axios, data) => axios.post('web-thicong/danhsach-tb-tbi-suco/sp_chitiet_suco_baohong_fms', data)
}
