export default {
    lay_danhsach_loai_donvi: async (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_LOAI_DVI', data),
    lay_danhsach_donvi_quanly: async (axios, data) => axios.post('/web-quantri/danhmuc/nghiepvu/DS_DONVI_QL_DIACHI_LD', data),
    lay_danhsach_tinh: async (axios) => axios.get('/web-tracuu/cntt_tskt/ds_tinh'),
    lay_danhsach_donvi: async (axios, loai_dv_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_donvi',{p_loaidvi_id: loai_dv_id}),
    lay_danhsach_donvi_px: async (axios, loai_dv_id, donvi_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_donvi_px',{p_loaidv_id: loai_dv_id, p_donvi_id: donvi_id}),
    
    lay_danhsach_quan_theo_tinh: async (axios, tinh_id) => axios.get('/web-quantri/danhmuc/diaban/quan?tinh_id='+tinh_id),
    lay_danhsach_phuong_theo_quan: async (axios, quan_id) => axios.post('/web-quantri/danhmuc/diaban/phuong-theo-quan',{p_quan_id: quan_id}),
    
    lay_danhsach_duong_pho_chua_gan: async (axios, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_pho_ap_khu',{p_nhom_id: 1, p_phuong_id:phuong_id}),
    lay_danhsach_ap_chua_gan: async (axios, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_pho_ap_khu',{p_nhom_id: 2, p_phuong_id:phuong_id}),
    lay_danhsach_khu_chua_gan: async (axios, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_pho_ap_khu',{p_nhom_id: 3, p_phuong_id:phuong_id}),
    
    lay_danhsach_dacdiem_chua_gan: async (axios, pho_id, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_dacdiem',{p_pho_id: pho_id, p_phuong_id:phuong_id}),
    capnhat_donvi_diadanh: async (axios, data) => axios.post('/web-quantri/DonViDiaDanh/capnhat_donvi_diadanh',data),

    sp_lay_ds_donvi_px_log: async (axios, data) => axios.post('web-quantri/DonViDiaDanh/sp_lay_ds_donvi_px_log',data),

    
}