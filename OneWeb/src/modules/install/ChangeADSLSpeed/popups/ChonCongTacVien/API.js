export default {
     lay_ds_donvi_ctv: async (axios) => axios.post('/web-quantri/gantbgoictv/lay_ds_donvi_ctv',{}),
     sp_lay_ds_loai_nv: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_LOAI_NV',{}),
     sp_lay_ds_nhanvien: async (axios, donvi_id, loainv_id) => axios.post('/web-hopdong/hopdong/khac/sp_lay_ds_nhanvien_gt',{
        donvi_id: donvi_id,
        loainv_id: loainv_id
     }),
}
