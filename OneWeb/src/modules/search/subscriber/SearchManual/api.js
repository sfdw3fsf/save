export default {
    //Sự kiện form load
    form_load_loaiCamNang: (axios) => axios.get(`/web-tracuu/tracuu-camnang/sp_tracuucamnang_ht_loai_camnang`),
    //Lấy loại lĩnh vực
    get_linh_vuc: (axios, params) => axios.get(`/web-tracuu/tracuu-camnang/sp_tracuucamnang_ht_loai_linhvuc`, { params }),
    //tra cuu
    search_camNang: (axios, params) => axios.post(`/web-tracuu/tracuu-camnang/sp_tracuucamnang_ht_ds_timkiem_camnang`, params),
}