export default {
  sp_tt_thamso_md: (axios, data) => axios.post('web-hopdong/laydulieu/sp_tt_thamso_md', data),
  ur_15001_001_get_dichvu_vt: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data, header),
  ur_15001_002_get_trangthai_tb: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGTHAI_TB', data, header),
  ur_15001_003_sp_lay_ds_dbtb_thanhly_theo_dvvt: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_dbtb_thanhly_theo_dvvt_v2', data),
  ur_15001_006_xoa_thuebao: async (axios, data) => axios.post('/web-danhba/xoa-thuebao-thanhly/xoa_thuebao', data),
  
}
