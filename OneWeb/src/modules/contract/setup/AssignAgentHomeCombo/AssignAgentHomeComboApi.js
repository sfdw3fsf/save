export default{
    lay_ds_hdtb_goi: async (axios, ma_gd, tu_ngay, den_ngay, nhanvien_id) => axios.post('/web-quantri/gantbgoictv/lay_ds_hdtb_goi',{
        vma_tb: 0,
        vma_gd: ma_gd,
        vdenngay: den_ngay,
        vtungay: tu_ngay,
        nhanvien_id: nhanvien_id
    }),

    lay_ds_donvi_ctv: async (axios) => axios.post('/web-quantri/gantbgoictv/lay_ds_donvi_ctv',{}),

    sp_lay_ds_loai_nv: async (axios) => axios.post('/web-quantri/gantbgoictv/sp_lay_ds_loai_nv',{
        //6: Nhóm cộng tác viên
        p_nhomlnv_id:6
    }),

    sp_lay_ds_nhanvien_ctv: async (axios, donvi_id, loainv_id) => axios.post('/web-quantri/gantbgoictv/sp_lay_ds_nhanvien_ctv',{
        p_loainv_id: loainv_id,
        p_donvi_id: donvi_id
    }),

    fn_capnhat_thuebao_ctv: async (axios, data) => axios.post('/web-quantri/gantbgoictv/fn_capnhat_thuebao_ctv',data),


    
    
}