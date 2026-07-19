// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=B536:C536
export default {
    lay_ds_donvi_ql: async (axios, loaidv_id) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_ql', {
        loaidv_id:loaidv_id
    }),

    lay_ds_phieu_bh_lachuong: async (axios, dichvuvt_id, thuebao_id) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_bh_lachuong', {
        vdichvuvt_id: dichvuvt_id,
        vthuebao_id: thuebao_id
    }),

    lay_tt_baohong_chuyento_chung: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_tt_baohong_chuyento_chung', data),
    
    lay_ds_tramvt: async (axios, donvi_id, loaidv_id) => axios.post('/web-hopdong/tachnhapthuebao/sp_ht_donvi_loaidv_combobox', {
        donvi_id: donvi_id,
        loaidv_id: loaidv_id
    }),

    lay_dvcha_theo_dvcon: async (axios, donvi_id, loaidv_id) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_dvcha_theo_dvcon', {
        vdonvi_id: donvi_id,
        vloaidv_id: loaidv_id
    }),

    lay_ds_thamso_md: async (axios) => axios.get('/web-quantri/diaban_nvkd/lay_ds_thamso'),

    hienthi_ds_phieuth_chuyento: async (axios, baohong_id, hdtb_id) => axios.post('/web-thicong/hoinghi_truyenhinh/hienthi_ds_phieuth_chuyento', {
        vbaohong_id: baohong_id,
        vhdtb_id: hdtb_id
    }),

    lay_ds_baohong_theo_ma_tb: async (axios, data) => axios.post('/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb', data),
    
    
    //map_id: async (axios, data) => axios.post('/web-hopdong/thanhly/thanh_ly_map_id', data),
    fn_tt_giaophieu: async (axios, phieu_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_giaophieu', {
        param: phieu_id, 
        param1: null,
        type: 5
    }),

    capnhat_chuyento: async (axios, data) => axios.post('/web-thicong/hoinghi_truyenhinh/an_capnhat_chuyento', data),
    
}