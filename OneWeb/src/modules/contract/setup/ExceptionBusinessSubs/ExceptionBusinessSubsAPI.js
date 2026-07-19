export default{
    //chưa có api lấy CSS.SP_LAY_DS_LHTB_DVVT, result: (LOAITB_ID,LOAIHINH_TB,TEN_DVVT) 
    //dùng tạm 2 api này
    lay_danhsach_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    lay_danhsach_dich_vu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

    sp_lay_ct_tsmd: async (axios) => axios.post('/web-quantri/importngoaile/sp_lay_ct_tsmd',{
        p_ma_ts: "TS_CHO_THEM_NGOAI_LE_TINH_LUONG"
    }),
    lay_du_lieu_tb_ngoaile_tinh_luong: async (axios) => axios.post('/web-quantri/importngoaile/lay_du_lieu_tb_ngoaile_tinh_luong',{}),
    
    // Item danhsach
    // {
    //     "LOAITB_ID": "58",
    //     "MA_TB": "anh33277"
    // }
    insert_json_kenh_tinh_luong_ngoai_le: async (axios, danhsach) => axios.post('/web-quantri/importngoaile/insert_json_kenh_tinh_luong_ngoai_le',{
        jsondata: danhsach
    }),
    xoa_thuebao_ngoaile_thang: async (axios, thuebao_id, thang) => axios.post('/web-quantri/importngoaile/xoa_thuebao_ngoaile_thang',{
        vthuebao_id: thuebao_id,
        vthang: thang
    }),
    
    
}