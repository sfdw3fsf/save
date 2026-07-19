
export default {
     
    Get_Project: (axios, data) => axios.post(`/tichhop/jira/get_project`, data),
    Get_Issue_Info: async (axios, data,header) => axios.post('/tichhop/jira/get_Issue_Info', data, header),
    Get_IssueTypes_V2: (axios, data) => axios.post(`/tichhop/jira/Get_IssueTypes_V2`, data),
    Creat_Issue: (axios, data) => axios.post(`/tichhop/jira/Creat_Issue`, data),
    add_Issue_Attachments: (axios, data, params, header) => axios.post(`/tichhop/jira/add_Issue_Attachments`, data, params, header), 
    add_Issue_Participant: (axios, data) => axios.post(`/tichhop/jira/add_Issue_Participant`, data),  
      
    lay_thongtin_jira: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_thongtin_jira`, data),
    fn_lay_thongtin_tieude_jira: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_lay_thongtin_tieude_jira`, data),
    lay_thongtin_kh_it_360: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_thongtin_kh_it_360`, data),  
    sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_thongtin_tieudo_mota_baohong_frm_create_issue`, data),
    sp_vip_nap_thongtin_mota_tinh_frm_create_issue: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_vip_nap_thongtin_mota_tinh_frm_create_issue`, data),
    jira_get_customfield_options: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/jira_get_customfield_options`, data),
    kiemtra_gui_yc_it360: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/kiemtra_gui_yc_it360`, data),
    kiemtra_attach_file_it360: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/kiemtra_attach_file_it360`, data),
    sp_lay_mucdo_uutien_frmcreateissue: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_mucdo_uutien_frmcreateissue`, data),
    sp_lay_thongtin_label_jira: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_thongtin_label_jira`, data),
    fn_insert_jira_giaophieu_issue: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/fn_insert_jira_giaophieu_issue`, data),
    lay_tt_giao_vip_giamsat_it360: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tt_giao_vip_giamsat_it360`, data),
    giaophieuvip_cntt: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/giaophieuvip_cntt`, data),
    giaophieu_jira_baohong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/giaophieu_jira_baohong`, data),
    lay_ds_phieu_phoihop_khdn: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_phoihop_khdn`, data),
    sp_nap_ds_bieumau_frm_qlbieumau: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_nap_ds_bieumau_frm_qlbieumau`, data),
    
    giaophieu_ph: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/giaophieu_ph`, data),
    tinh_insert_vip_net: (axios, data) => axios.post(`/web-bancheo/tracuu/tinh_insert_vip_net`, data),
    
}