export default {
  lay_thong_tin_gui_mail: (axios, hdtb_id) => axios.get(`/web-thicong/gui_mail_kich_hoat_brand_name/lay_thong_tin_gui_mail/${hdtb_id}`),
  sp_load_dulieu_form_tao_brn: (axios, hdtb_id) => axios.get(`/web-thicong/gui_mail_kich_hoat_brand_name/sp_load_dulieu_form_tao_brn/${hdtb_id}`),
  sp_lay_thongtin_mail_server_smsbrn: (axios) => axios.get(`/web-thicong/gui_mail_kich_hoat_brand_name/sp_lay_thongtin_mail_server_smsbrn`),
  gui_mail: (axios, data) => axios.post(`/web-ccdv/gui-mail-kich-hoat-brand-name/gui_mail`, data),
}
