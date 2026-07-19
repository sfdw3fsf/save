export default {
  sp_bancheo_lay_ds_thietbi_dbtb_idc: async (axios, data) => axios.get(`/web-thicong/giaoviec-vip/sp_bancheo_lay_ds_thietbi_dbtb_idc?vphanvung_id=${data.vphanvung_id}&vtinh_id=${data.vtinh_id}&vthuebao_id=${data.vthuebao_id}`, data),
  lay_ds_thietbi_hdtb_idc: async (axios, data) => axios.post('/web-thicong/giaoviec-vip/lay_ds_thietbi_hdtb_idc', data)
}
