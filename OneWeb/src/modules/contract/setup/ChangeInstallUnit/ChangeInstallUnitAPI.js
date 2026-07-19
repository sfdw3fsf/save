export default{
    lay_danhsach_dichvu: async (axios) => axios.get('/web-thicong/danhmuc/lay_danhsach_dichvu'),
    lay_danhsach_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    lay_danhsach_kieudonvi: async (axios) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieudonvi'),
    lay_danhsach_quan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN'),

    lay_danhsach_tinh: async (axios) => axios.get('/web-tracuu/cntt_tskt/ds_tinh'),
    lay_danhsach_quan_theo_tinh: async (axios, tinh_id) => axios.get('/web-quantri/danhmuc/diaban/quan?tinh_id='+tinh_id),

    //api chưa tường minh sài api khác tạm thời
    //lay_danhsach_phuong: async (axios) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG',{}),
    lay_danhsach_phuong: async (axios, quan_id) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan',{p_quan_id:quan_id}),
    lay_danhsach_pho: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 1 }),
    
    //khu, ấp chưa rõ output ra không có dữ liệu test
    lay_danhsach_ap: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 2 }),
    lay_danhsach_khu: async (axios, phuong_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{ ID: phuong_id, CATEGORY_NAME: 3}),

    //chưa rõ model truyền vào và input trả ra dùng tạm api khác
    //lay_danhsach_dacdiem:  async (axios, data) => axios.post('/web-quantri/danhmuc-chung/CSS_DACDIEM',data),
    lay_danhsach_dacdiem:  async (axios, pho_id, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_dacdiem', {
        p_pho_id: pho_id,
        p_phuong_id: phuong_id
    }),
    lay_ds_hopdong_theo_mathuebao: async (axios, maTb) => axios.get('/web-thicong/thuebao/lay_ds_hopdong_theo_mathuebao?maTb='+maTb),
    lay_ds_hopdong_theo_hdkhid_v2: async (axios, hdkhId) => axios.get('/web-thicong/thuebao/lay_ds_hopdong_theo_hdkhid_v2?hdkhId='+hdkhId),
    
    // "hdtb_id": 5854615,
    // "donvi_id": 10385,
    // "loaidv_id": 5,
    // "ten_dv": "Tổ Kỹ thuật - TT3"
    lay_ds_donvi_theo_hdtb: async (axios, hdtbId) => axios.get('/web-thicong/thuebao/lay_ds_donvi_theo_hdtb?hdtbId='+hdtbId),

    lay_donvi_ql: async (axios, donvi_id) => axios.post('/web-thicong/thuebao/kiemtra_dulieu',{
        inIdNeo: "ten_dv",
        inTable: "admin.donvi",
        inDk: "where donvi_id = "+donvi_id
    }),
    lay_ds_diachi_theo_hdtbid: async (axios, hdtbId) => axios.get('/web-thicong/thuebao/lay_ds_diachi_theo_hdtbid?hdtbId='+hdtbId),

    lay_ds_donvi_theo_loaidv_diachild: async (axios, loaidvId, phuongId, phoId, apId, khuId) => axios.post('/web-thicong/thuebao/lay_ds_donvi_theo_loaidv_diachild',{
        loaidvId: loaidvId, phuongId: phuongId, phoId: phoId, apId: apId, khuId: khuId
    }),

    capnhat_tramvattu_lapdat_v2: async (axios, data) => axios.post('/web-thicong/thuebao/capnhat_tramvattu_lapdat_v2',data),
}