export default{
    map_id: async (axios, data) => axios.post('/web-quantri/thamso/map_id', data),

    lay_danhmuc_hieuchinh: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_danhmuc_hieuchinh', data),
    runsql: async (axios, data) => axios.post('/web-danhba/huonggiao/sp_huonggiao_runsql', data),
    sp_lay_ls_xn_hieuchinh: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_ls_xn_hieuchinh?'+data, null),

    fn_tt_khuvuc: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_khuvuc',data),
    sp_tt_hdtb_dv: async (axios, data) => axios.post('/web-quantri/lay-dulieu/sp_tt_hdtb_dv',data),

    sp_lay_ds_tram_thicong_theo_donvi:  (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_tram_thicong_theo_donvi', data),
    sp_lay_tocdo_theo_loaitb:  (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_tocdo_theo_loaitb', data),
    sp_lay_ds_thongtin_xacnhan: async (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thongtin_xacnhan', data),
    sp_lay_ds_thongtin_ketqua: async (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thongtin_ketqua', data),

    lay_ds_thamso_md: async (axios, data) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', data),
    
    lay_ds_hd_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),

    lay_ds_tiepnhan_yc_hieuchinh: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_tiepnhan_yc_hieuchinh', data),
    lay_ds_hopdong_kh_theo_hdkh_id: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hopdong_kh_theo_hdkh_id', data),
    lay_ds_hdtt_theo_hdkh_id_hc: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hdtt_theo_hdkh_id_hc', data),

    lay_ds_hdtb_theo_hdkh_id_hc: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hdtb_theo_hdkh_id_hc', data),
    lay_ds_ctkm_theo_hdtbid: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_ctkm_theo_hdtbid', data),
    lay_ds_dangky_dvgt_v2: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_dangky_dvgt_v2', data),
    lay_ds_goi_dadv_hdtb: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_goi_dadv_hdtb', data),
    lay_ds_mua_tbi: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_mua_tbi', data),

    lay_tien_hopdong: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_tien_hopdong?hdtbId='+data,null ),

    lay_khoanmuc_tt_theo_loaihd: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd', data),

    fn_capnhat_hieuchinhhopdong: async (axios, data) => axios.post('/web-hopdong/thanhly/fn_capnhat_hieuchinhhopdong', data),

    sp_insert_ls_xn_hieuchinh: async (axios, data) => axios.post('/web-hopdong/thanhly/sp_insert_ls_xn_hieuchinh', data),
    sp_delete_tdtt_hd_thuebao: async (axios, data) => axios.post('/web-hopdong/thanhly/sp_delete_tdtt_hd_thuebao', data),
    
    kiemtra_hoanthien_hieuchinh: async (axios, data) => axios.post('/web-hopdong/thanhly/kiemtra_hoanthien_hieuchinh', data),
    sp_update_tdtt_hd_thuebao: async (axios, data) => axios.post('/web-hopdong/thanhly/sp_update_tdtt_hd_thuebao', data),

    lay_thongtin_hieuchinh: async (axios, data) => axios.post('/web-tracuu/tracuu-thuebao/lay_thongtin_hieuchinh', data),

    SP_LAY_DS_DONVI_THEO_LOAIDV: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv', data),

    execute_get_query: async (axios, data) => axios.post('/web-tracuu/tracuu-thuebao/execute_get_query', data),

    insert_ls_hieuchinh: async (axios, data) => axios.post('/web-tracuu/tracuu-thuebao/insert_ls_hieuchinh', data),
}
    