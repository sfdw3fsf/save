export default{
    lay_ds_khuvuc: async (axios) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_khuvuc'),
    lay_ds_donvi_lhd: async (axios) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_donvi_lhd'),
    lay_ds_thuebao_dungthu: async (axios, khuvucId, donviId) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_thuebao_dungthu?khuvucId='+khuvucId+'&donviId='+donviId),
    
    lay_ds_doituong: async (axios) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_doituong'),

    // hdtb_id: 1398031,
    //     ngay_nt: "21/08/2021 00:00:00" 
    kt_dungthu_moi_v2: async (axios, hdtb_id, ngay_nt) => axios.post('/web-hopdong/hopdong_dungthu/kt_dungthu_moi_v2',{
        hdtb_id: hdtb_id,
        ngay_nt: ngay_nt 
    }),

    chuyen_hopdong_chinhthuc: async (axios, data) => axios.post('/web-hopdong/hopdong_dungthu/chuyen_hopdong_chinhthuc', data),
    
    lay_ds_nhom_huy: async (axios, loaitb_id) => axios.post('/web-hopdong/hopdong_dungthu/lay_ds_nhom_huy', {
        loaild_id: 3,
        loaitb_id: loaitb_id,
        kieu: 1
    }),
    
    lay_ds_lydo_huy: async (axios, loaitb_id, nhomhuy_id) => axios.post('/web-hopdong/hopdong_dungthu/lay_ds_lydo_huy', {
        loaild_id: 3,
        loaitb_id: loaitb_id,
        nhomhuy_id: nhomhuy_id,
        kieu: 1
    }),  

    huy_hopdong_dungthu: async (axios, data) => axios.post('/web-hopdong/hopdong_dungthu/huy_hopdong_dungthu', data),

    lay_ds_thamso_md: async (axios, kieu_id) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{
        p_param: kieu_id,
        p_type:4
    }),

    lay_ds_danhba_adsl: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId='+thuebao_id),
    sp_ktra_dk_port: async (axios, data) => axios.post('/web-ccdv/khaibaoims/sp_ktra_dk_port', data),

    fn_capnhat_trangthai_port: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_port', data),
    fn_capnhat_trangthai_portid: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_portid', data),
    fn_capnhat_ds_matb: async (axios, data) => axios.post('/web-ccdv/khaibaoims/fn_capnhat_ds_matb', data),
    Capnhat_port_vatly: async (axios, data) => axios.post('/web-ccdv/dongbo-visa-vasc/Capnhat_port_vatly', data),

    //
    kiemtra_datcoc_tb_dungthu: async (axios, thuebao_id) => axios.post('/web-hopdong/hopdong_dungthu/kiemtra_datcoc_tb_dungthu', {
        vthuebao_id: thuebao_id
    }),
    
}