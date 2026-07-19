
export default{
    formload_frmbcthaydoitocdoadsl: (axios,data) => axios.post('/web-hopdong/TocDoMucCuoc/formload_frmbcthaydoitocdoadsl', data),
    fn_lay_tt_ds_khuyenmai_cdlhtb: (axios,data) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_ds_khuyenmai_cdlhtb', data),
    lay_ds_hopdong_theo_matb: (axios,data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_matb', data),
    hienthitthopdongkh_frmbcthaydoitocdoadsl: (axios,data) => axios.post('/web-hopdong/TocDoMucCuoc/hienthitthopdongkh_frmbcthaydoitocdoadsl', data),
    hienthitthopdongtb_frmbcthaydoitocdoadsl: (axios,data) => axios.post('/web-hopdong/TocDoMucCuoc/hienthitthopdongtb_frmbcthaydoitocdoadsl', data),
    lay_tt_gan_thongso_bancheo_hdtb: (axios,data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tt_gan_thongso_bancheo_hdtb', data),
    lay_ds_mucuoc_tb_v2:  (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
    lay_tien_khoanmuc_ld:  (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),
    lay_thongtin_hd_tuvan: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_thongtin_hd_tuvan', data),
    tk_ma_tb_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/tk_ma_tb_frmbcthaydoitocdoadsl', data),
    hienthitt_danhba_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/hienthitt_danhba_frmbcthaydoitocdoadsl', data),
    lay_ds_thuoctinh_bang: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_thuoctinh_bang', data),
    lay_ds_danhba_adsl: (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=' + data),
    check_map_id: (axios, data) => axios.post('/web-hopdong/thongtin_tratruoc_dn/map_id', data),
    fn_tt_khuyenmai: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_khuyenmai', data),
    fn_tt_muccuoc_tb: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', data),
    fn_tt_tocdo_adsl: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', data),
   
    get_vdc_list_attr: (axios) => axios.get('/web-tracuu/visa/get_vdc_list_attr'),
    traCuuThueBao_vdc: (axios,data) => axios.get('web-hopdong/TocDoMucCuoc/traCuuThueBao?account='+ data),
    load_tdkld_theo_lhtb_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/load_tdkld_theo_lhtb_frmbcthaydoitocdoadsl', data),
    cbotocdoadsl_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/cbotocdoadsl_frmbcthaydoitocdoadsl', data),
    lay_thongtintc_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_thongtintc_frmbcthaydoitocdoadsl', data),
    sp_ht_tocdo_adsl_combobox_byloaitb: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_tocdo_adsl_combobox_byloaitb', data),
    kt_va_lay_tt_capnhat_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/kt_va_lay_tt_capnhat_frmbcthaydoitocdoadsl', data),
    xoahdkh_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/xoahdkh_frmbcthaydoitocdoadsl', data),
    xoahdtb_frmbcthaydoitocdoadsl:  (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/xoahdtb_frmbcthaydoitocdoadsl', data),
    lay_ds_hdtb_thaydoi_tocdo: (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_ds_hdtb_thaydoi_tocdo', data),
    tt_taodulieu_hdkh_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/tt_taodulieu_hdkh_frmbcthaydoitocdoadsl', data),
    tt_taodulieu_hd_thuebao_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/tt_taodulieu_hd_thuebao_frmbcthaydoitocdoadsl', data),
    get_keys: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys', data),
    getHdTBDV: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/ur2_1_035_038_temp', data),
    lay_ds_db_thuebao_theo_tbid: (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_db_thuebao_theo_tbid', data),
    lay_danhba_theo_thuebaoid: (axios, data) => axios.post('/web-bancheo/tracuu/lay_danhba_theo_thuebaoid', data),
    lay_ds_hopdong_theo_ma_gd: (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data),
    lay_ds_db_adsl_theo_tbid: (axios, data) => axios.post('/web-bancheo/tracuu/lay_ds_db_adsl_theo_tbid', data),
    tt_taodulieu_tdtd_adsl_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/tt_taodulieu_tdtd_adsl_frmbcthaydoitocdoadsl', data),
    lay_ds_danhba_cntt: (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_cntt?thueBaoId=' + data),
    layhuonggiao_tiepnhan: (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan', data),
    ins_update_hd_tdtd_bc_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/ins_update_hd_tdtd_bc_frmbcthaydoitocdoadsl', data),
    fn_lay_thang_sd: (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_thang_sd', data),
    lay_ds_tien_dc_conlai_doi_td: (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_tien_dc_conlai_doi_td', data),
    lay_ds_hopdong_tb_theo_hdtb_id: (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id', data),
    kt_va_lay_tt_them_hdtb_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/kt_va_lay_tt_them_hdtb_frmbcthaydoitocdoadsl', data),
    themhdthuebao_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/themhdthuebao_frmbcthaydoitocdoadsl', data),
    ht_tdtd_frmbcthaydoitocdoadsl: (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/ht_tdtd_frmbcthaydoitocdoadsl', data),
    kiemtra_thoihan_km: (axios, data) => axios.post('/web-hopdong/lapdatmoi/kiemtra_thoihan_km', data),











}
