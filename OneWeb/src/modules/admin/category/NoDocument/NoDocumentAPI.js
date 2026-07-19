export default{
    lay_loai_cv_vnp: async (axios, data) => axios.post('/web-tracuu/socongvan/lay_dulieu_combo_congvan', data),
    lay_loai_cv: async (axios, data) => axios.post('/web-tracuu/socongvan/sp_lay_loai_cv', data),
    tracuu: async (axios, data) => axios.post('/web-tracuu/socongvan/sp_lay_ds_socongvan', data),
    ghi_lai: async (axios, data) => axios.post('/web-tracuu/socongvan/fn_frmsocongvan_xuly', data),
    lay_danhsach_donvi_phathanh: async (axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_phathanh', data),
    luu_thongtin_file_congvan: async (axios, data) => axios.post('/web-khdn/danhmucbancheo/luu_thongtin_file_congvan', data),
    lay_danhsach_file_congvan_v1: async (axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_file_congvan', data),
    uploadFile: (axios, data) => axios.post('/web-quantri/upload',data, {headers: {'Content-Type': 'multipart/form-data'}}),
    lay_danhsach_file_congvan: async (axios,data) => axios.get(`web-khdn/danhmucbancheo/lay_danhsach_file_congvan?p_congvan_id=${data.p_congvan_id}`),
}
    