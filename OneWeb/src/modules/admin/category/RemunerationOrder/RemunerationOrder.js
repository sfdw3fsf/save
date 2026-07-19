export default {
    ur_11100_001_layds_luot_cttl_dachuyen: async (axios, data, header) => axios.get('/web-quantri/chitrathulao/layds_luot_cttl_dachuyen', data, header),
    ur_11100_002_fn_kiemtra_dl_laplenh_chithulao: (axios, data) => axios.post('/web-quantri/chitrathulao/fn_kiemtra_dl_laplenh_chithulao', data),
    ur_11100_003_fn_chuyen_duyet_lenh_chithulao: (axios, data) => axios.post('/web-quantri/chitrathulao/fn_chuyen_duyet_lenh_chithulao', data),
    ur_11100_008_lay_chitiet_luot_cttl: async (axios, data, header) => axios.get(`/web-quantri/chitrathulao/lay_chitiet_luot_cttl/${data.luotcttl_id}`, data, header),

  
}
  