export default{
    getDonvi: async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI'),
    getDonviBh: async (axios, donvicha) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_phathanh'),
    getDonviCon: async (axios, donvicha) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_donvi_banhang?p_donvi_id='+donvicha),
    getDonViIDTheoND: async(axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_id_theo_ma_nd', data),
    getDsCongVanBanCheo: async(axios, data) => axios.post('/web-khdn/tracuudieuhuong/tracuu_congvan_bancheo', data),
    getDsPhanChiaDoanhThu: async(axios, data) => axios.get('/web-khdn/doanhthu/tracuu_tt_phanchia_doanhthu?p_coche_id=' + data),
    getDsPhanChiaDoanhThuTheo_SoCv_MaCoChe: async(axios, data) => axios.post('/web-khdn/doanhthu/lay_thongtin_phanchia_doanhthu_socv_macoche', data),
    getDsCoCheBanCheoChoThueBao: async(axios, data) => axios.get('/web-khdn/tracuudieuhuong/lay_danhsach_coche_gan_tb?p_coche_id='+ data),
    getDsTbaoDaGanCoCheBanCheo: async(axios, data) => axios.get('/web-khdn/doanhthu/lay_ds_thuebao_gan_coche_bancheo', data),
    getDsFileDinhKem: async(axios, data) => axios.get('/web-khdn/danhmucbancheo/lay_danhsach_file_congvan?p_congvan_id='+ data),
    download_file: async (axios, data) => axios.post('/web-quantri/upload/link_view_file', data),
}