export default{
    get_ds_tinhtc: async (axios, data) => axios.post('/web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox', {}),
    get_ds_dvvt: async (axios, data) => axios.post('/web-tracuu/TraCuuDanhBaTB/lay_ds_dvvt', {}),
    get_doituong: async (axios) => axios.get('/web-hopdong/hopdong_dungthu/lay_ds_doituong'),
    get_hinhthuc_tt: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TT'),
    get_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),
    get_chuquan: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_chuquanld', data),
    get_dbkh_theo_makh: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_danhba_kh_theo_makh', data),
    get_tbhd_pl: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),
    get_tt_db_theo_kh: async (axios, data) => axios.post('/web-bancheo/tracuu/sp_lay_tt_db_theo_kh', data),
    get_ds_biendong_chua_hoanthanh_kh: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_biendong_chua_hoanthanh_kh', data),
    get_ds_danhba_tb_v2: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_dbtb_theo_khid_v2', data),
    get_ds_kh_theo_ma_tb: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_thongtin_kh_theo_ma_tb', data),
    get_ds_kh_theo_ma_tt: async (axios, data) => axios.post('/web-bancheo/tracuu/lay_thongtin_kh_theo_ma_tt', data),
    // get_ds_tt_db_theo_thuebao: async (axios, data) => axios.post('/web-hopdong/bss_group1/fn_lay_tt_db_theo_thuebao_bc', data),
    get_ds_tt_db_theo_thuebao: async (axios, data) => axios.post('/web-bancheo/tracuu/fn_lay_tt_db_theo_thuebao_bc', data),
    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',{
        in_thuebao_id: thuebao_id
    }),
    lay_ds_nhanvien_dbtb_by_id: (axios, data) => axios.post('/web-tracuu/danhba/lay_ds_nhanvien_dbtb_by_id', data),
}