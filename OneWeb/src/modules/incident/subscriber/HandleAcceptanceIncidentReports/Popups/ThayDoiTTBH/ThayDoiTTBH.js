export default {
    sp_tt_thamso_md: (axios, data) => axios.post('web-hopdong/laydulieu/sp_tt_thamso_md', data),
    ur_41003_14_002_sp_kiemtra_quyen_kh_goi_baohong_v2: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_kiemtra_quyen_kh_goi_baohong_v2', data),
    ur_41003_14_002_sp_cn_thongtin_bh: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_cn_thongtin_bh', data),

    lay_nhom_nd_id: (axios, data) => axios.get('/web-ccdv/tien_trinh_bao_hong/lay_nhom_nd_id', data),

}