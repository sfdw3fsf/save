export default {
  tocdo_kenh: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH', null),
  dichvu_vt: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', null),
  lay_ds_hd_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
  lay_ds_hd_tb_theo_hdkh_id_v3: async (axios, data) => axios.post('/web-hopdong/tracuu_chitiet_thicong/lay_ds_hd_tb_theo_hdkh_id_v3', data),
  sp_lay_tt_search_by_hdkh_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_search_by_hdkh_id', data),
  sp_lay_tt_by_selection_index_changed: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_by_selection_index_changed', data),
  //popup dia chi lap dat
  sp_lay_tt_show_diachi_ld: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_show_diachi_ld', data),

  //popup toa nha
  css_quan: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN', null),
  css_phuong: async (axios, data) => axios.post('/web-quantri//danhmuc-chung/CSS_PHUONG', data),
  sp_lay_ds_toanha_by_quanphuong: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_toanha_by_quanphuong', data),
  sp_lay_tt_quan_phuong_by_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_quan_phuong_by_id', data),
  sp_lay_ds_thuebao_toanha_by_tag: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/sp_lay_ds_thuebao_toanha_by_tag', data),

  //popup goi da dich vu
  sp_lay_ds_tb_dk_goi: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_tb_dk_goi', data),

  //popup phieu ton
  sp_lay_ds_ton_phieu_hist: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_ton_phieu_hist', data),

  //popup port
  lay_ds_port_hdtb: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/lay_ds_port_hdtb', data),

  //popup vat tu HDTB
  sp_lay_kqxl_huonggiao_thuhoi: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_kqxl_huonggiao_thuhoi', data),
  sp_lay_ds_vattu_hdtb: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_vattu_hdtb', data),
  lay_ds_vattu_baohong: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/lay_ds_vattu_baohong', data),

  //popup cthd
  sp_lay_tt_khachhang_tien_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_tien_by_hdtb_id', data),
  sp_lay_tt_thuebao_chinh_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_chinh_by_hdtb_id', data),
  sp_lay_tt_thuebao_them_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_them_by_hdtb_id', data),
  sp_lay_tt_thuebao_kythuat_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_kythuat_by_hdtb_id', data),
  sp_lay_tt_thuebao_khac_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_khac_by_hdtb_id', data),

  sp_lay_tt_khachhang_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_by_hdtb_id', data),
  sp_lay_tt_thanhtoan_by_hdtb_id: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thanhtoan_by_hdtb_id', data),

  fn_get_econtract_id_by_ma_gd: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/fn_get_econtract_id_by_ma_gd', data),

  //db grid
  sp_lay_ds_nhanvien_thuchien: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_nhanvien_thuchien', data),

  //it360 jira
  sp_tracuu_phieu_issue: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_tracuu_phieu_issue', data),
  lay_ds_jira_dongbo_tracuujira: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/lay_ds_jira_dongbo_tracuujira', data),
  fn_dongbo_jira_frmdongbojira: (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/fn_dongbo_jira_frmdongbojira', data),
  get_jira_issues_by_hdtb_list: (axios, data) => axios.post('/web-hopdong/QuanLyIssue/get-jira-issues-by-hdtb-list', data),
  get_List_Issue: (axios, data) => axios.post('/tichhop/jira/get_List_Issue', data),
  dongbo_jira: (axios, data) => axios.post('/web-hopdong/QuanLyIssue/dongbo-jira', data),
  lay_ds_dangky_dvgt: async (axios, hdtb_id) => axios.get(`web-ccdv/dongbo-visa-vasc/lay_ds_dangky_dvgt/${hdtb_id}`),

  //tientrinhxuly
  getDanhSachTienTrinhXuLy: (axios, hdtbId) => axios.get(`web-thicong/tientrinh_thicong/lay_ds_tientrinh_xuly?hdtbId=${hdtbId}`),
}
