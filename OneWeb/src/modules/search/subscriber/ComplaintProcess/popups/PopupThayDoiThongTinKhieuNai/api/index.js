export default {
  // API
  thaydoi_ttkn_v3: (axios, data) => axios.post('web-ccdv/tientrinh-khieunai/thaydoi_ttkn_v3', data),
  lay_ketqua_kn_theo_loai: (axios) => axios.get('web-ccdv/tientrinh-khieunai/lay_ketqua_kn_theo_loai', { params: { vloai: 2 } }),
  sp_lay_ds_khieunai_by_id: (axios, params) => axios.get('web-ccdv/tientrinh-khieunai/sp_lay_ds_khieunai_by_id', { params })
}
