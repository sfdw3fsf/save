export default {
    lay_ds_nhanvien: async (axios) => axios.post('/web-quantri/diadanh-nhanvien/diaban_laydsnv',{}),

    sp_ds_loainhanvien: async (axios, nhanvien_id, kieu) => axios.post('/web-quantri/nhanvien/sp_ds_loainhanvien',{
        p_nhanvien_id: nhanvien_id,
        p_kieu: kieu
    }),
    
    diaban_laydsnv_px: async (axios, nhanvien_id, loainv_id) => axios.post('/web-quantri/diadanh-nhanvien/diaban_laydsnv_px',{
        vnhanvien_id:nhanvien_id,
        vloainv_id: loainv_id
    }),

    diaban_taocap4: async (axios) => axios.post('/web-quantri/diadanh-nhanvien/diaban_taocap4',{}),
    diaban_taocap4_kinhdoan: async (axios) => axios.post('/web-quantri/diadanh-nhanvien/diaban_taocap4_kinhdoan',{}),

    sp_lay_ds_khuvuc_px_v2: async (axios, khuvuc_id) => axios.post('/web-quantri/diadanh-nhanvien/sp_lay_ds_khuvuc_px_v2',{
        vkhuvuc_id: khuvuc_id
    }),

    sp_cboloainv_changed: async (axios, ds_para) => axios.post('/web-quantri/diadanh-nhanvien/sp_cboloainv_changed',{
        ds_para: ds_para
    }),

    fn_nhanvien_diadanh_capnhat: async (axios, ds_para) => axios.post('/web-quantri/diadanh-nhanvien/fn_nhanvien_diadanh_capnhat',{
        ds_para: ds_para
    }),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
        keyname: keyname
    }),
}