export default{
    get_thongtin_bancheo_db: async (axios, data) => axios.post('/web-tracuu/tra-cuu-danh-ba-hd-ban-cheo/lay_thongtin_bancheo_thuebao', data),
    get_thongtin_bancheo_hd: async (axios, data) => axios.post('/web-tracuu/tra-cuu-danh-ba-hd-ban-cheo/lay_hdtb_bancheo_thuebao', data),
    lay_ds_hd_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_hd_theo_ma_tb', data),
    tracuu_danhba_hd_bancheo: async (axios, data) => axios.post('/web-tracuu/tra-cuu-danh-ba-hd-ban-cheo/danhba_thuebao_bancheo', data),
    get_ds_hdtb_bancheo: async (axios, data) => axios.post('/web-tracuu/tra-cuu-danh-ba-hd-ban-cheo/hopdong_thuebao_bancheo', data),
    lay_tinh_thicong: async (axios, data) => axios.get('/web-ccdv/tientrinh-khieunai/lay_tinh_thi_cong', data),
}