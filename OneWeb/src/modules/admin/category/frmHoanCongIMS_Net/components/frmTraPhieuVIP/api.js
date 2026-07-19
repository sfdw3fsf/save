export default {
  bc_kt_va_lay_tt_phieu: async (axios, data) => axios.get(`/web-thicong/khaibao_dichvu_net_mgw/bc_kt_va_lay_tt_phieu?vphanvung_id=${data.vphanvung_id}&vphieu_id=${data.vphieu_id}`, data),
  bc_tra_phieu_vip: async (axios, data) => axios.post('/web-thicong/khaibao_dichvu_net_mgw/bc_tra_phieu_vip', data),
  bc_tra_phieu_vip_khdn_v2: async (axios, data) => axios.post('/web-thicong/khaibao_dichvu_net_mgw/bc_tra_phieu_vip_khdn_v2', data),
  tra_phieu_net_sms_vip_ph: async (axios, data) => axios.post('/web-thicong/khaibao_dichvu_net_mgw/tra_phieu_net_sms_vip_ph', data),
  sp_lay_ds_lydotra_vip: async (axios, data) => axios.get(`/web-thicong/khaibao_dichvu_net_mgw/sp_lay_ds_lydotra_vip?phanvung_id=${data.phanvung_id}`, data),
  select_nv_sdt: async (axios, data) => axios.get(`web-tracuu/tracuutonghop/select_nv_sdt?nhanvien_id=${data.nhanvien_id}`, data),
  
}   
