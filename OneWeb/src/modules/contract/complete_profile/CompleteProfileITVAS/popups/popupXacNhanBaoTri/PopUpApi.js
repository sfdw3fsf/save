
export default {
      
    lay_thuebao_xacnhan_baotri: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_thuebao_xacnhan_baotri`, data),
    sp_lay_nhanvien_theo_nhanvien_id: (axios, data) => axios.get(`/web-ccdv/tram-thicong-dv-cntt/sp_lay_nhanvien_theo_nhanvien_id`, data),
    sp_tb_traloi_theo_tbtl_id: (axios, data) => axios.get(`/web-ccdv/tram-thicong-dv-cntt/sp_tb_traloi_theo_tbtl_id`, data), 
    lay_ds_cauhoi_theoloainv: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_ds_cauhoi_theoloainv`, data), 
    
    sp_ct_tbtraloi: (axios, data) => axios.get(`/web-ccdv/tram-thicong-dv-cntt/sp_ct_tbtraloi`, data),
    fn_ghilai_xacnhanbaotri: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/fn_ghilai_xacnhanbaotri`, data),
    
}