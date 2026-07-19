export default {
  // Tao moi YC
  sp_lay_thongtin_phieu_xacnhan_td: async (axios, p_hdkh_id) => axios.get(`/web-thicong/thaydoi-thongtin-kh/lay_thongtin_phieu_xacnhan_td?p_hdkh_id=${p_hdkh_id}`),
  
  sp_lay_thong_tin_file_upload: async (axios, p_tdtt_hdtb_id) => axios.get(`/web-tracuu/tracuu/lay_thong_tin_file_upload?tdtt_hdtb_id=${p_tdtt_hdtb_id}`),
  
  fn_capnhat_phieuyc_tdtt_kh: async (axios, data) => axios.post(`/web-thicong/thaydoi-thongtin-kh/fn_capnhat_phieuyc_tdtt_kh`, { 
    p_kieu: data.p_kieu, 
    p_tdtt_hdtb_id: data.p_tdtt_hdtb_id, 
    p_hdkh_id: data.p_hdkh_id, 
    p_hdtb_id: data.p_hdtb_id, 
    p_json_phieuyc: data.p_json_phieuyc 
  }),
  fn_gui_xacnhan_td_ttkh: async (axios, data) => axios.post(`/web-thicong/thaydoi-thongtin-kh/fn_gui_xacnhan_td_ttkh`, { 
    p_hdkh_id: data.p_hdkh_id, 
    p_tdtt_hdtb_id: data.p_tdtt_hdtb_id, 
    p_json_thongtin: data.p_json_thongtin, 
  }),

  // Phe duyet YC
  sp_lay_thongtin_phieu_can_xacnhan_nv: async (axios, p_nhanvien_id) => axios.get(`/web-thicong/thaydoi-thongtin-kh/lay_thongtin_phieu_can_xacnhan?p_nhanvien_id=${p_nhanvien_id}`),
  fn_pheduyet_tdtt_kh: async (axios, data) => axios.post(`/web-thicong/thaydoi-thongtin-kh/fn_pheduyet_tdtt_kh`, {
    p_phieu_id: data.p_phieu_id, 
    p_hdkh_id: data.p_hdkh_id, 
    p_tdtt_hdtb_id: data.p_tdtt_hdtb_id,
    p_kq_duyet: data.p_kq_duyet,
    p_noidung_duyet: data.p_noidung_duyet,
    p_nhanvien_id: data.p_nhanvien_id,
  }),

  fn_giuphieu_pheduyet: async (axios, data) => axios.post(`/web-tracuu/tracuu/fn_giuphieu_pheduyet`, {
    p_phieu_id: data.p_phieu_id,
    p_tdtt_hdtb_id: data.p_tdtt_hdtb_id,
    p_nhanvien_id: data.p_nhanvien_id,
  }),

  fn_huy_giuphieu_pheduyet: async (axios, data) => axios.post(`/web-tracuu/tracuu/fn_huy_giuphieu_pheduyet`, {
    p_phieu_id: data.p_phieu_id,
    p_nhanvien_id: data.p_nhanvien_id,
  }),

  sp_lay_ds_donvi_ldv: async (axios, loaidv_id) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/sp_lay_ds_donvi_ldv', {
    loaidv_id: loaidv_id
  }),

  sp_lay_lichsu_pheduyet: async (axios, p_hdkh_id) => axios.get(`/web-tracuu/tracuu/lay_lichsu_pheduyet?hdkh_id=${p_hdkh_id}`),

  fn_lay_ds_phieu_pheduyet_theo_nv: async (axios, data) => axios.post(`/web-tracuu/tracuu/lay_ds_phieu_pheduyet_theo_nv`, {
    p_nhanvien_id: data.p_nhanvien_id,
    p_ma_gd: data.p_ma_gd, 
    p_ma_kh: data.p_ma_kh, 
    p_tungay: data.p_tungay,
    p_denngay: data.p_denngay,
  }),
}