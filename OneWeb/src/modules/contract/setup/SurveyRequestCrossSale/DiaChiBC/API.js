export default {
    lay_danhsach_tinh: async (axios, data) => axios.post('/web-bancheo/tracuu/get_tinh_by_tinh_id',data),
    lay_danhsach_quan_theo_tinh: async (axios, data) => axios.post('/web-bancheo/tracuu/get_quan_by_tinh_id', data),

    //api chưa tường minh sài api khác tạm thời
    //lay_danhsach_phuong: async (axios) => axios.post('/web-quantri/danhmuc-chung/CSS_PHUONG',{}),
    lay_danhsach_phuong: async (axios, data) => axios.post('/web-bancheo/tracuu/get_phuong_by_quan_id',data),
    lay_danhsach_pho: async (axios, data) => axios.post('/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id',data),
    
    //khu, ấp chưa rõ output ra không có dữ liệu test
    lay_danhsach_ap: async (axios, data) => axios.post('/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id',data),
    lay_danhsach_khu: async (axios, data) => axios.post('/web-bancheo/tracuu/get_pho_by_phuong_nhom_pho_id',data),

    //chưa rõ model truyền vào và input trả ra dùng tạm api khác
    lay_danhsach_dacdiem:  async (axios, pho_id, phuong_id) => axios.post('/web-quantri/DonViDiaDanh/sp_lay_ds_dacdiem', {
        p_pho_id: pho_id,
        p_phuong_id: phuong_id
    }),

    sp_lay_ds_thamso_md: async (axios, data) => axios.post('/web-bancheo/tracuu/sp_lay_ds_thamso_md',data),
    

}