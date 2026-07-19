export default {
    ks01_sp_lay_ds_congnghe_theo_kieuld_id: (axios, data) => axios.get(`web-hopdong/hopdong/sp_lay_ds_congnghe_theo_kieuld_id?kieuldId=${data.kieuldId}`, data),
    ks02_load_dm_cong_nghe: (axios, data) => axios.post('web-bancheo/danhmuc/load_dm_cong_nghe', data),
    ks03_sp_lay_ds_doituong_theo_dieukien: (axios, data) => axios.post('web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien', data),
    ks04_load_dm_doi_tuong: (axios, data) => axios.post('web-bancheo/danhmuc/load_dm_doi_tuong', data),
    ks05_sp_lay_ds_trangbi_theo_dieukien: (axios, data) => axios.post('web-hopdong/thaydoitocdoadsl/sp_lay_ds_trangbi_theo_dieukien', data),
    ks06_load_dm_trang_bi: (axios, data) => axios.post('web-bancheo/danhmuc/load_dm_trang_bi', data),
    ks07_load_dm_kieuld: (axios, data) => axios.post('web-bancheo/danhmuc/load_dm_kieuld', data),
    ks08_lay_ds_mucuoc_tb_v2: (axios, data) => axios.post('web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
    ks09_lay_ds_mucuoc_tb_v2: (axios, data) => axios.post('web-bancheo/tracuu/lay_ds_mucuoc_tb_v2', data),
    ks10_lay_ds_chuquan: (axios, data) => axios.post('web-hopdong/TocDoMucCuoc/lay_ds_chuquan', data),
    ks11_load_dm_chu_quan: (axios, data) => axios.post('web-bancheo/danhmuc/load_dm_chu_quan', data),

    lay_diachi_dbtb_bancheo: (axios, data) => axios.post('web-bancheo/danhmuc/lay_diachi_dbtb_bancheo', data),
    lay_chuquan_macdinh_theo_donvi_bancheo: (axios, data) => axios.get('web-hopdong/hopdong/lay_chuquan_macdinh_theo_donvi_bancheo', data),
    fn_tt_kieu_ld: (axios, data) => axios.post('web-bancheo/tracuu/fn_tt_kieu_ld', data),
}