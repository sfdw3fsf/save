export default {
    lay_danhsach_dichvu_vt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    lay_danhsach_donvi: async (axios) => axios.post('/web-quantri/thamso-macdinh/sp_tsmd_ht_donvi',{
        loaidv_id: 2
    }),
    lay_danhsach_quan: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_QUAN'),

    lay_danhsach_phuong_theo_quan: async (axios, quan_id) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan',{
        p_quan_id: quan_id
    }),

    lay_danhsach_nhanvien_donvi: async (axios, donvi_id) => axios.post('/web-quantri/nhanvien/sp_ds_nhanvien_donvi',{
        p_donvi_id: donvi_id,
        p_kieu: 1
    }),

    //lấy tham số mặc định, form load thông tin tab 1
    sp_lay_tham_so_mac_dinh: async (axios, ma_ts) => axios.post('/web-quantri/thamso-macdinh/sp_lay_tham_so_mac_dinh',{
        ma_ts: ma_ts
    }),

    //lây danh sách tham số đơn vị
    lay_ds_thamso_macdinh_dv: async (axios, donvi_id) => axios.post('/web-quantri/thamso-macdinh/lay_ds_thamso_macdinh_dv',{
        vdonvi_id: donvi_id,
        vkieu_id: 3
    }),

    sp_tsmd_ht_loaichuongtrinh: async (axios) => axios.post('/web-quantri/thamso-macdinh/sp_tsmd_ht_loaichuongtrinh',{}),

    //dùng tab 2, tab 4
    lay_ds_thamso_macdinh: async (axios, kieu) => axios.post('/web-quantri/thamso-macdinh/lay_ds_thamso_macdinh',{
        vkieu_id: kieu
    }),

    //ghi dữ liệu tab 1
    capnhat_thamso_macdinh: async (axios, ma_ts, giatri) => axios.post('/web-quantri/thamso-macdinh/capnhat_thamso_macdinh',{
        vma_ts: ma_ts,
        vgiatri: giatri
    }),
    //cập nhật tham số tab 2, 4 //"[{'MA_TS':'CHUCDANH_TTR_TEST','NGUOIDUNG_ID': 1, 'GIATRI': 2434}]"
    sp_ct_tsmd_update: async (axios, data) => axios.post('/web-quantri/thamso-macdinh/sp_ct_tsmd_update',{
        js_data: data
    }),
    //cập nhật dư liệu tab 5 //"[{'NGUOIDUNG_ID':3211,'LOAICT_ID': 4, 'MA_ND':'035-HPG000888', 'MATKHAU': 'hungle@12' }]"
    sp_nguoidung_loaict_update: async (axios, data) => axios.post('/web-quantri/thamso-macdinh/sp_nguoidung_loaict_update',{
        js_data: data
    }),

    //chưa có api
    sp_ct_tsmd_dv_update: async (axios, data) => axios.post('/web-quantri/thamso-macdinh/sp_ct_tsmd_dv_update',{
        js_data: data
    }),
    
}