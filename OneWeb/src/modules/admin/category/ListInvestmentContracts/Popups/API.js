export default{
    get_ds_dv_trienkhai: async (axios) => axios.post('/web-toanha/dm_hopdong_dautu/sp_lay_ds_dvitk'),
    get_ds_tientrinh: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/sp_lay_ds_tientrinh_dtu', data),   
    ins_upd_del_tientrinh: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/fn_tientrinh_dtu', data),   
    get_ds_dv_vienthong: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    get_ds_lh_thuebao: async (axios,id) =>axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    get_ds_chitiet: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/sp_lay_ds_chitiet_hddtu', data),
    get_ds_doitac_chudt: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/sp_lay_ds_doitac_or_chu_dtu', data),
    ins_upd_chitiet: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/fn_ghilai_chitiet_hddtu', data),
    del_chitiet: async (axios, data) => axios.post('/web-toanha/dm_hopdong_dautu/fn_xoa_chitiet_hddtu', data),
  }