export default {
     lay_ds_donvi_ctv: async (axios) => axios.post('/web-quantri/gantbgoictv/lay_ds_donvi_ctv',{}),
    //  sp_lay_ds_loai_nv: async (axios) => axios.post('/web-quantri/gantbgoictv/sp_lay_ds_loai_nv',{
    //      //Nhóm nguoi gt = 11
    //      p_nhomlnv_id:11
    //  }),
     sp_lay_ds_loai_nv: async (axios) => axios.post('/web-ccdv/dongbo_danhba_vnp/ht_ds_nhomnv_combobox',{
         //Nhóm ctv = 6
         nhomlnv_id:11
     }),
     sp_lay_ds_nhanvien: async (axios, donvi_id, loainv_id) => axios.post('/web-hopdong/hopdong/khac/sp_lay_ds_nhanvien_gt',{
        donvi_id: donvi_id,
        loainv_id: loainv_id
     }),
}