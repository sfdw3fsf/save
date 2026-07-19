export default {
    lay_thongtin_ds_tinh_quan: (axios, data) => axios.get('/web-hopdong/hopdong/lay_thongtin_ds_tinh_quan', data),
    lay_thongtin_ds_quan_theo_tinh: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_ds_quan_theo_tinh?tinh_id=${data.tinh_id}`, data),
    lay_thongtin_ds_phuong_theo_quan: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_ds_phuong_theo_quan?quan_id=${data.quan_id}`, data),
    lay_thongtin_ds_pho_ngo_khu_theo_phuong: (axios, data) => axios.get(`/web-hopdong/hopdong/lay_thongtin_ds_pho_ngo_khu_theo_phuong?phuong_id=${data.phuong_id}&loai=${data.loai}`, data),

    lay_ds_tinh: (axios, data) => axios.post('web-bancheo/danhmuc/lay_ds_tinh', data),
    sp_lay_ds_thamso_md: (axios, data) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_thamso_md', data),
    lay_ds_quan: (axios, data) => axios.post('web-bancheo/danhmuc/lay_ds_quan', data),
    lay_ds_dacdiem: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/lay_ds_dacdiem', data),
    lay_ds_phuong: (axios, data) => axios.post('web-bancheo/danhmuc/lay_ds_phuong', data),
    lay_ds_pho: (axios, data) => axios.post('web-bancheo/danhmuc/lay_ds_pho', data),

}