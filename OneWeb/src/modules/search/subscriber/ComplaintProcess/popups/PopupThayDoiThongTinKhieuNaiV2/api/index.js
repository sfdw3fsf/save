export default {
  // API
  thaydoi_ttkn_v5: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/thaydoi_ttkn_v5', data),
  lay_ketqua_kn_theo_loai: (axios) => axios.get('web-ccdv/tientrinh-khieunai/lay_ketqua_kn_theo_loai', { params: { vloai: 2 } }),
  sp_lay_ds_khieunai_by_id: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_ds_khieunai_by_id', { params }),
  sp_lay_khieunai_gc_by_id: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_khieunai_gc_by_id', { params }),
  sp_lay_kygiamcuoc: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_kygiamcuoc', { params }),
  sp_lay_thang_gc: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/sp_lay_thang_gc', data)
}
