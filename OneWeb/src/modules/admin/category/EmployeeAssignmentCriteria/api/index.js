export default {
  ur_006_sp_ds_nhanvien_donvi: (axios, data) => axios.post('/web-quantri/nhanvien/sp_ds_nhanvien_donvi', data),
  ur_009_lay_ds_lkh_phantap_nv: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/lay_ds_lkh_phantap_nv', data),
  ur_010_lay_danhsach_lkh_nv_v2: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/lay_danhsach_lkh_nv_v2', data),
  ur_011_lay_danhsach_nn_nv: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/lay_danhsach_nn_nv', data),
  ur_012_lay_danhsach_dt_nv: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/lay_danhsach_dt_nv', data),
  ur_014_lay_danhsach_dtn_nv: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/lay_danhsach_dtn_nv', data),
  ur_015_fn_capnhat_nhanvien_ptkh: (axios, data) => axios.post('/web-quantri/gannhanvien-tieuchi/fn_capnhat_nhanvien_ptkh', data),

  fn_tt_nhanvien: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', data),
  fn_tt_nguoidung: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nguoidung', data),
}
