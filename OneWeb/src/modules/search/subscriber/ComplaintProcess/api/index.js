export default {
  // API
  sp_lay_ds_thamso_md: (axios, data) => axios.post('web-hopdong/thanhly/sp_lay_ds_thamso_md', data),
  sp_lay_dulieu_bang_theo_dieukien: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/sp_lay_dulieu_bang_theo_dieukien', data),
  fn_tt_nguoidung: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_nguoidung', data),
  fn_tt_donvi: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi', data),
  sp_lay_donvi_id_theo_ma_nd: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_donvi_id_theo_ma_nd', { params }),
  sp_giaosim_get_treeview: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_giaosim_get_treeview', { params }),
  sp_nap_cbo_trangthai_bh: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_nap_cbo_trangthai_bh', { params }),
  sp_nap_cbo_loai_dv_bh: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_nap_cbo_loai_dv_bh', { params }),
  sp_lay_ds_tientrinh_kn: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_ds_tientrinh_kn', { params }),
  ds_khieunai_tientrinh_v2: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/ds_khieunai_tientrinh_v2', data),
  ds_khieunai_tientrinh: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/ds_khieunai_tientrinh', data),
  sp_ds_kycuoc: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_ds_kycuoc', { params }),
  sp_delete_tientrinh_kn: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/sp_delete_tientrinh_kn', data),
  sp_tt_loai_kn: (axios, data) => axios.post('web-hopdong/laydulieu/sp_tt_loai_kn', data)
}
