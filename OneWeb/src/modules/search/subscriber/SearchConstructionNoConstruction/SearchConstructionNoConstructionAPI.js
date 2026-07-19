export default {
  lay_don_vi: async (axios, data) => axios.post('/web-tracuu/tcnone/sp_get_treeview', data),
  lay_loai_nhom: async (axios, data) => axios.get('/web-tracuu/tcnone/sp_ht_loai_nhom', data),
  lay_hop_dong: async (axios, data) => axios.get('/web-tracuu/tcnone/ht_loai_hd_bh/' + data),
  lay_dich_vu: async (axios, data) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_dvvt', data),
  lay_ds_phieu_chua_up: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_ds_phieu_giao_bbnt', data),
  ds_phieu: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_ds_phieu_giao', data),
  lay_ds_phieu_ht_bh: async (axios, data) => axios.post('/web-tracuu/tcnone/lay_ds_phieu_ht_bh', data),
  // tracuu: async (axios, data) => axios.post('/web-ccdv/tiepnhan-hdtuvan/tuvan_ds_phieu_tiepnhan', data),
  map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
  fn_tt_loai_khl: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_khl', data)
}