export default{
    get_ds_dvql_loaituyen: async (axios, data) => axios.post('/web-ccdv/capnhat_loai_thietbi/fn_chuyentuyenthu_load', {}),
    get_ds_nhanvien: async (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/fn_chuyentuyenthu_cbodonvi_ql', data),
    get_ds_tuyenthu: async (axios, data) => axios.post('/web-ccdv/capnhat_loai_thietbi/fn_chuyentuyenthu_hienthidanhsach', data),
    save_chuyentuyenthu: async (axios, data) => axios.post('/web-ccdv/capnhat_loai_thietbi/fn_chuyentuyenthu_save', data),
}