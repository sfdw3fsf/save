export default {
    lay_danhsach_quan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN'),

    lay_danhsach_phuong: async (axios, quan_id) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan',{p_quan_id:quan_id}),
    lay_danhsach_pho: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 1 }),
    
    lay_danhsach_ap: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 2 }),
    lay_danhsach_khu: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 3}),

    lay_danhsach_dacdiem:  async (axios, pho_id, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_dacdiem', {
        p_pho_id: pho_id,
        p_phuong_id: phuong_id
    }),

    sp_lay_ds_khuvuc_theo_hdtb:  async (axios, daucuoi_id, hdtb_id) => axios.post('/web-hopdong/lapdatmoi/sp_lay_ds_khuvuc_theo_hdtb', {
        daucuoi_id: daucuoi_id,
        hdtb_id: hdtb_id
    }),

    sp_lay_diachi_theo_hd_db:  async (axios, id, hd_db) => axios.post('/web-hopdong/lapdatmoi/sp_lay_diachi_theo_hd_db', {
        hd_db: hd_db,
        id: id
    }),

    sp_lay_ds_donvi_tramtc:  async (axios, donvi_id) => axios.post('/web-hopdong/lapdatmoi/sp_lay_ds_donvi_tramtc', {
        donvi_id: donvi_id,
        loaidv_id: 5
    }),

    sp_lay_khuvuc_theo_diadanh: async (axios, data) => axios.post('/web-hopdong/hieuchinhhopdong/sp_lay_khuvuc_theo_diadanh', data),

    lay_ds_nhanvien_khuvuc: async (axios, khuvuc_id) => axios.post('/web-hopdong/hieuchinhhopdong/lay_ds_nhanvien_khuvuc', {
        vkhuvuc_id:khuvuc_id
    }),

    lay_ds_nhanvien_kv_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-hopdong/hieuchinhhopdong/lay_ds_nhanvien_kv_theo_hdtb_id', {
        vhdtb_id:hdtb_id
    }),

    sp_ins_hdtb_kv_v2: async (axios, data) => axios.post('/web-hopdong/hieuchinhhopdong/sp_ins_hdtb_kv_v2', {
        js_hdtb_kv:data
    }),
    
}