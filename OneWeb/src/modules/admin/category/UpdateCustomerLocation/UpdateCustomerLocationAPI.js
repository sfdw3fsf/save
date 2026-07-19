export default {
    lay_danhsach_quan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN'),
    lay_danhsach_phuong_theo_quan: async (axios, quan_id) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG',{
        ID:quan_id
    }),
    //loai=1: pho, 2: ấp, 3: khu
    lay_danhsach_pho_theo_phuong: async (axios, phuong_id, loai) => axios.post('/web-quantri/danhmuc-chung/CSS_PHO',{
        ID: phuong_id,
        CATEGORY_NAME: loai
    }),

    lay_danhsach_donvi_ctv: async (axios) => axios.post('/web-quantri/gantbgoictv/lay_ds_donvi_ctv',{}),

    //nhomlnv_id = 6 lấy nhóm cộng tác viên với 
    lay_danhsach_nhom_ctv: async (axios) => axios.post('/web-quantri/gantbgoictv/sp_lay_ds_loai_nv',{
        p_nhomlnv_id: 6
    }),

    lay_danhsach_nhanvien_ctv: async (axios, donvi_id, loainv_id) => axios.post('/web-quantri/hodan/sp_nhanvien_ctv_layds',{
        donvi_id: donvi_id,
        loainv_id: loainv_id
    }),

    lay_ds_hodan_theo_diachi: async (axios, data) => axios.post('/web-quantri/hodan/lay_ds_hodan_theo_diachi', data),

    lay_danhsach_donvi_theo_ctv: async (axios, nhanvien_id) => axios.post('/web-quantri/hodan/sp_donviql_layds_theo_ctv', {
        nhanvien_id: nhanvien_id
    }),

    lay_danhsach_nhomlnv_theo_ctv: async (axios, nhanvien_id) => axios.post('/web-quantri/hodan/sp_nhomlnv_layds_theo_ctv', {
        nhanvien_id: nhanvien_id,
        nhomlnv_id: 6
    }),

    capnhat_diachi_hodan_v2: async (axios, data) => axios.post('/web-quantri/hodan/capnhat_diachi_hodan_v2', data),


    lay_danhsach_dichvu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_chuquan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),
    lay_danhsach_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),

    lay_ds_hodan_dv: async (axios, hodan_id) => axios.post('/web-quantri/hodan/lay_ds_hodan_dv', {
        vhodan_id: hodan_id
    }),

    xoa_dichvu_hodan: async (axios, data) => axios.post('/web-quantri/hodan/xoa_dichvu_hodan',data),

    capnhat_dichvu_hodan: async (axios, data) => axios.post('/web-quantri/hodan/capnhat_dichvu_hodan',data),

    lay_danhsach_quanhe_tv: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUANHE_TV'),

    lay_ds_thanhvien_theo_hodan_id: async (axios, hodan_id) => axios.post('/web-quantri/hodan/lay_ds_thanhvien_theo_hodan_id', {
        vhodan_id: hodan_id  
    }),

    capnhat_tt_thanhvien: async (axios, data) => axios.post('/web-quantri/hodan/capnhat_tt_thanhvien',data),

    lay_loaihinhtb_hodan_sudung: async (axios, hodan_id, tvhd_id, kieu) => axios.post('/web-quantri/hodan/lay_loaihinhtb_hodan_sudung',{
        vhodan_id: hodan_id,
        vtvhd_id: tvhd_id,
        vkieu: kieu  
    }),

    sp_qldaily_dichvu_thanhvien_update: async (axios, data) => axios.post('/web-quantri/hodan/sp_qldaily_dichvu_thanhvien_update', data),
    
}