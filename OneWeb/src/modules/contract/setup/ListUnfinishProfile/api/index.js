export default {
  ur_001_loai_hopdong_phanloai: async (axios, data, header) => axios.get('/web-hopdong/danhmuc/loai_hopdong_phanloai', data, header),
  ur_002_loai_file_hopdong: async (axios, data, header) => axios.get('/web-hopdong/danhmuc/loai_file_hopdong', data, header),
  ur_003_ds_hoso_cho_laphopdong: (axios, data) => axios.post('/web-hopdong/danhmuc/ds_hoso_cho_laphopdong', data),

  ur_004_danhmuc_ds_diachi_theo_sdkhid: (axios, data) => axios.post('/web-hopdong/danhmuc/ds_diachi_theo_sdkhid', data),

  ur_005_kiemtra_trangthai_phieu: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/kiemtra_trangthai_phieu', data),
  ur_005_capnhat_traphieu: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/capnhat_traphieu', data),
  ur_005_ds_lydotra_tc: (axios, data, header) => axios.get('/web-ccdv/tiepnhanhopdong/ds_lydotra_tc', data, header),
  ur_005_thaotac_phieu_hdtv: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/thaotac_phieu_hdtv', data),

  ur_008_count_hd_khachhang: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/count_hd_khachhang', data),
  ur_008_tuvan_hths: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/tuvan_hths', data),
  ur_008_update_giaophieu_hs: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/update_giaophieu_hs', data),
  ur_008_update_tt_didong: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/update_tt_didong', data),

  fn_tt_giaophieu_hs: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu_hs', data),

  ur_009_hienthi_hdpl_ghep: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/hienthi_hdpl_ghep', data),
  ur_009_thongtin_hdpl: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/thongtin_hdpl', data),
  ur_009_kiemtra_ghepma: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/kiemtra_ghepma', data),
  ur_009_update_ghepma_thanhcong: (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/update_ghepma_thanhcong', data),

  ur_011_fn_kiemtra_capnhat_lmcd: (axios, data) => axios.post('/web-hopdong/ds_hoso_cholap_hdpl/fn_kiemtra_capnhat_lmcd', data),

  ur_012_capnhat_thamso_macdinh: (axios, data) => axios.post('/web-hopdong/ds_hoso_cholap_hdpl/capnhat_thamso_macdinh', data),

  ur_014_sp_lay_anh_hs: (axios, data) => axios.post('/web-hopdong/ds_hoso_cholap_hdpl/sp_lay_anh_hs', data),

  get_data_by_url: (axios, data) => axios.get(`/tichhop/util/download?url=${data.url}&fileType=${data.fileType}`, data),
  
}
