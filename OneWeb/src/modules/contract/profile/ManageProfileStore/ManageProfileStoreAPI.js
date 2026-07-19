export default{
    ds_quyen_nguoidung: async (axios, data,header) => axios.get('/web-quantri/quyen/ds_quyen_nguoidung', data),

    lay_ds_doituong_hs_v2: async (axios, data,header) => axios.post('/web-hopdong/QLBoHS/lay_ds_doituong_hs_v2', data,header),
    lay_ds_bo_hs_trongkho: async (axios, data,header) => axios.post('/web-hopdong/QLBoHS/lay_ds_bo_hs_trongkho', data,header),
    chuyen_hoso: async (axios, data,header) => axios.post('/web-hopdong/QLBoHS/chuyen_hoso', data,header),

    trangthai_bo_hoso: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/trangthai_bo_hoso', data,header),
    kho_hoso: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/kho_hoso', data,header),
    nhom_file: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/nhom_file', data,header),
    loai_file: async (axios, data,header) => axios.get('/web-quantri/danhmuc/hoso/loai_file', data,header),
    lay_ds_bo_hs: async (axios, data,header) => axios.post('/web-quantri/hopdong/lay_ds_bo_hs', data,header),
    layds_bohoso_trong_kho: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_bohoso_trong_kho', data,header),
    layds_file_trong_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_file_trong_hoso', data,header),
    layds_donvi_nhan_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/layds_donvi_nhan_hoso', data,header),
    layds_hopdong_chuaco_hoso: async (axios, data,header) => axios.post('/web-quantri/hopdong/layds_hopdong_chuaco_hoso', data,header),
    upload_file_hoso: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
    // xoa_bohoso: async (axios, data,header) => axios.post('/web-quantri/hoso/capnhat_bohoso', data,header),
    // xoa_file_hoso: async (axios, data,header) => axios.post('/web-quantri/hoso/fn_capnhat_file_hs', data,header),
    capnhat_bohoso: async (axios, data,header) => axios.post('/web-quantri/hoso/capnhat_bohoso', data,header),
    layds_theo_matb: async (axios, data,header) => axios.post('/web-quantri/hopdong/layds_theo_matb', data,header),
    sp_ds_bo_hs_theo_ma_gd: async (axios, data,header) => axios.post('/web-hopdong/QLBoHS/sp_ds_bo_hs_theo_ma_gd', data,header),
    lay_ds_ma_hs: async (axios, data,header) => axios.post('/web-hopdong/QLBoHS/lay_ds_ma_hs', data,header),

    lay_danhsach_hopdong_theo_matb: async (axios, data) => axios.get('/web-tracuu/pttb/lay_danhsach_hopdong_theo_matb', data),
    lay_ds_hd_tb_theo_hdkh_id: async (axios, data,header) => axios.post('/web-hopdong/hopdong/lay_ds_hd_tb_theo_hdkh_id', data,header),
    capnhat_file_hoso: async (axios, data,header) => axios.post('/web-hopdong/hopdong/capnhat_file_hoso', data,header),
    capnhat_filehs_ds: async (axios, data,header) => axios.post('/web-hopdong/hopdong/capnhat_filehs_ds', data,header),
    sp_lay_file_hs_nhom_id: async (axios, data,header) => axios.post('/web-quantri/hoso/sp_lay_file_hs_nhom_id', data,header),
    xoa_file: async (axios, data,header) => axios.post('/web-quantri/upload/xoa_file', data,header),
    link_view_file: async (axios, data,header) => axios.post('/web-quantri/upload/link_view_file', data,header),
    lay_ds_filehs_theo_kh: async (axios, data,header) => axios.get('web-hopdong/QLBoHS/lay_ds_filehs_theo_kh', data,header),
    
}