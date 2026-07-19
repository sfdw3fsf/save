export default {
    sp_ht_dichvuvt_combobox: async (axios, dsdichvuvt_id) => axios.post('/web-thicong/thanhly-thuebao/sp_ht_dichvuvt_combobox',{
        dichvu_vt_id: dsdichvuvt_id
    }),

    lay_ds_tocdo_kenh: async (axios) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_tocdo_kenh',{}),

    sp_lay_ds_loai_tbi: async (axios, dichvuvt_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_lay_ds_loai_tbi',{
        p_dichvuvt_id:dichvuvt_id
    }),

    lay_ds_hdtb_con_theo_hdtbid: async (axios, hdtb_id, dichvuvt_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/lay_ds_hdtb_con_theo_hdtbid',{
        in_hdtb_id: hdtb_id,
        in_dichvuvt_id:dichvuvt_id
    }),

    lay_thuebao_lapkem: async (axios, hdtb_id, madoicap) => axios.post('/web-ccdv/khaibaoims/lay_thuebao_lapkem',{
        hdtb_id: hdtb_id,
        madoicap: madoicap
    }),

    lay_ds_db_con_theo_tbid: async (axios, thuebao_id, dichvuvt_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/lay_ds_db_con_theo_tbid',{
        in_thuebao_id: thuebao_id,
        in_dichvuvt_id: dichvuvt_id
    }),

    lay_thuebao_lapkem_db: async (axios, thuebao_id, madoicap) => axios.post('/web-ccdv/hths-thaydoi-tocdo/lay_thuebao_lapkem_db',{
        vthuebao_id: thuebao_id,
        vmadoicap: madoicap
    }),

    lay_tt_vatly_theo_port_logic: async (axios, port_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/lay_tt_vatly_theo_port_logic',{
        vport_id: port_id
    }),

    sp_loai_tbi_giaophieu_tbi_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_loai_tbi_giaophieu_tbi_theo_hdtb_id',{
        vhdtb_id: hdtb_id
    }),

    lay_huongkn_moi_cu: async (axios, hdtb_id) => axios.post('/web-tracuu/TraCuuPhieuTCBC/lay_huongkn_moi_cu',{
        hdtb_id: hdtb_id
    }),

    sp_ds_loai_tbi_theo_thuebao_id: async (axios, thuebao_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_ds_loai_tbi_theo_thuebao_id',{
        vthuebao_id: thuebao_id
    }),

    fn_lay_text_huongketnoi_v2: async (axios, huong_kn) => axios.post('/web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2',{
        huong_kn: huong_kn
    }),

    //fn_map_id: async (axios, data) => axios.post('/web-ccdv/khaibaotsl/map_id',data),
    fn_tt_loaicap_theo_loaicap_id: async (axios, loaicap_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_cap', {
        param: loaicap_id,
        type: 1 
    }),

    sp_lay_thongtin_lh_db_tsl: async (axios, thuebao_id, daucuoi_id) => axios.post('/web-ccdv/hths-thaydoi-tocdo/sp_lay_thongtin_lh_db_tsl',{
        vthuebao_id: thuebao_id,
        vdaucuoi_id: daucuoi_id
    }),
    

}