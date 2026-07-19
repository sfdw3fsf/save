export default {
    lay_danhsach_loai_kho: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_LOAI_KHO'),
    lay_danhsach_donvi_chuquan: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI'),
    sp_ds_kho:async (axios, kho_id) => axios.post('/web-quantri/quanlykhobienlai/sp_ds_kho',{
        kho_id: kho_id
    }),
    sp_ds_kho_chuagan: async (axios) => axios.post('/web-quantri/quanlykhobienlai/sp_ds_kho_chuagan',{}),

    sp_ds_kho_nv: async (axios, donvi_id, kho_id) => axios.post('/web-quantri/quanlykhobienlai/sp_ds_kho_nv',{
        donvi_id: donvi_id,
        kho_id: kho_id
    }),
    sp_ds_kho_nv_da_gan: async (axios, kho_id) => axios.post('/web-quantri/quanlykhobienlai/sp_ds_kho_nv_da_gan',{
        kho_id: kho_id
    }),

    
    //danhsach nhân viên
    // [{
    //         "quyen_kg": "0",
    //         "donvi_id": 1,
    //         "quyen_kn": "0",
    //         "nhanvien_id": "3971"
    //         }]
    //action 1: gán, 0: gỡ
    sp_capnhat_nhanvien_kho: async (axios, action, kho_id, ds_nhanvien) => axios.post('/web-quantri/quanlykhobienlai/sp_capnhat_nhanvien_kho',{
        action: action,
        kho_id: kho_id,
        ds_nhanvien:ds_nhanvien
    }),

    sp_xoa_kho_bienlai:async (axios, kho_id) => axios.post('/web-quantri/quanlykhobienlai/sp_xoa_kho_bienlai',{
        kho_id: kho_id
    }),
    sp_capnhat_kho_bienlai:async (axios, data) => axios.post('/web-quantri/quanlykhobienlai/sp_capnhat_kho_bienlai',data),

}