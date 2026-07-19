export default {
    lay_thamso_khoa_tao_moi_vattu: async (axios) => axios.post('/web-quantri/vattu/lay_ds_thamso_md_ds_mats',{
        ds_ma_ts: [
            "KHOA_TAOMOI_VATTU"
        ]
    }),
    lay_thamso_tu_sinh_ma_vt: async (axios) => axios.post('/web-quantri/vattu/lay_ds_thamso_md_ds_mats',{
        ds_ma_ts: [
            "TU_SINH_MA_VT"
        ]
    }),
    
    sp_ht_ds_vt_cha: async (axios, check) => axios.post('/web-quantri/vattu/sp_ht_ds_vt_cha',{
        check: check
    }),

    sp_ht_ds_danhmuc_mucdich: async (axios) => axios.post('/web-quantri/vattu/sp_ht_ds_danhmuc_mucdich',{}),

    lay_danhsach_donvitinh: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_DVI_TINH'),
    lay_danhsach_loaithietbi: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_LOAI_TBI'),
    lay_danhsach_loaivattu: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_LOAI_VT'),
    lay_danhsach_onu: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_ONU_BNM'),
    lay_vattu_mscp: async (axios) => axios.post('/web-quantri/vattu/lay_vattu_mscp',{}),
    lay_ds_chung_loai_vt: async (axios) => axios.post('/web-quantri/vattu/lay_ds_chung_loai_vt',{}),
    

    lay_danhsach_hang_sx: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_HANG_SX'),
    lay_danhsach_linhvuc: async (axios) => axios.get('/web-quantri/danhmuc-chung/QLVT_LINHVUC'),
    lay_tt_hethong_theo_linhvuc: async (axios, linhvuc_id) => axios.post('/web-quantri/vattu/lay_tt_hethong',{
        linhvuc_id: linhvuc_id
    }),
    sp_nap_ds_dong_tbi: async (axios, ds_para) => axios.post('/web-quantri/vattu/sp_nap_ds_dong_tbi',{
        ds_para: ds_para
    }),

    //tree view left/ kieu_check:1 Vattu, 2:CCDC // db_tbi, ht_id, kieu_check
    sp_ht_ds_danhmuc_vattu: async (axios, data) => axios.post('/web-quantri/vattu/sp_ht_ds_danhmuc_vattu', data),

    sp_nap_ds_hethong: async (axios, vattu_id) => axios.post('/web-quantri/vattu/sp_nap_ds_hethong', {
        vattu_id:vattu_id
    }),
    //kieu=2: Gan, 1:Chưa gán
    sp_ht_ds_mucdich_vt: async (axios, vattu_id, kieu) => axios.post('/web-quantri/vattu/sp_ht_ds_mucdich_vt', {
        vattu_id:vattu_id,
        kieu:kieu
    }),

    sp_ht_ds_mucdich_vt: async (axios, vattu_id, kieu) => axios.post('/web-quantri/vattu/sp_ht_ds_mucdich_vt', {
        vattu_id:vattu_id,
        kieu:kieu
    }),

    fn_ins_vattu_md: async (axios, vattu_id, ds_md) => axios.post('/web-quantri/vattu/fn_ins_vattu_md', {
        ds_md:ds_md,
        vattu_id:vattu_id
    }),

    fn_del_vattu_md: async (axios, vattu_id, ds_md) => axios.post('/web-quantri/vattu/fn_del_vattu_md', {
        ds_md:ds_md,
        vattu_id:vattu_id
    }),  

    fn_nap_ds_dong_tbi: async (axios, vattu_id, hangsx_id) => axios.post('/web-quantri/vattu/fn_nap_ds_dong_tbi', {
       ds_para: {
            HANGSX_ID: hangsx_id,
            VATTU_ID: vattu_id
        }
    }),
    //kieu==1:Gán, kieu==2: Chưa gán
    gan_go_dong_tbi: async (axios, ds_para, js_dong_tbi, kieu) => axios.post('/web-quantri/vattu/gan_go_dong_tbi', {
        ds_para: ds_para,
        js_dong_tbi: js_dong_tbi,
        kieu: kieu
    }),
    
    //lấy thông tin ảnh
    lay_tt_anh: async (axios, vattu_id) => axios.post('/web-quantri/vattu/lay_tt_anh', {
        vattu_id: vattu_id
    }),
    //cập nhật mô tả ảnh
    fn_update_mota: async (axios, vattu_id, ds_img) => axios.post('/web-quantri/vattu/fn_update_mota', {
        vattu_id: vattu_id,
        ds_img: ds_img
    }),

    fn_xoa_vattu: async (axios, vattu_id) => axios.post('/web-quantri/vattu/fn_xoa_vattu', {
        vattu_id: vattu_id
    }),

    fn_ins_up_vattu: async (axios, ds_para, js_vattu) => axios.post('/web-quantri/vattu/fn_ins_up_vattu', {
        ds_para: ds_para,
        js_vattu: js_vattu
    }),

    docfile_vattu: async (axios, vds) => axios.post('/web-quantri/vattu/docfile_vattu', {
        vds: vds
    }),

    get_asset_supplies_category_info: async (axios, data) => axios.post('/tichhop/qlts/get-asset-supplies-category-info', data),

    lay_ds_tt_dadongbo: async (axios, ds_ma_vt, ds_dvi_tinh) => axios.post('/web-quantri/vattu/lay_ds_tt_dadongbo', {
        p_ds_ma_vt: ds_ma_vt,
        p_ds_dvi_tinh: ds_dvi_tinh
    }),
    dongbo_vattu: async (axios, ds_ma_vt, ds_para) => axios.post('/web-quantri/vattu/dongbo_vattu', {
        p_ds_ma_vt: ds_ma_vt,
        p_ds_para: ds_para
    }),
    thongtin_nguoidung: async (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),

    link_view_file: async (axios, list_file) => axios.post('/web-quantri/upload/link_view_file', {
        list_file: list_file
    }),

    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),

    fn_update_mota: async (axios, vattu_id, ds_img) => axios.post('/web-quantri/vattu/fn_update_mota', {
        vattu_id: vattu_id,
        ds_img: ds_img
    }),

    thongtin_machine: async (axios) => axios.get('web-quantri/nguoidung/thongtin'),

    sp_lay_ds_vattu_capduoi: async (axios, vattu_id) => axios.post('/web-quantri/vattu/sp_lay_ds_vattu_capduoi', {
        vattu_id: vattu_id
    }),
    
    lay_ds_vattu_dokiem: async (axios, kieu) => axios.get(`/web-thicong/tiepnhan-yc-tongdai/lay_ds_vattu_dokiem?vkieu=${kieu}`),
    capnhat_vattu_dokiem_giaodien: async (axios, data) => axios.post('web-thicong/tiepnhan-yc-tongdai/capnhat_vattu_dokiem_giaodien', data),
    lay_ds_mucdich_phanvung: async (axios, kieu) => axios.get(`/web-thicong/tiepnhan-yc-tongdai/lay_ds_mucdich_phanvung?vkieu=${kieu}`),
    capnhat_mucdich_phanvung: async (axios, data) => axios.post('web-thicong/tiepnhan-yc-tongdai/capnhat_mucdich_phanvung', data),
    lay_ds_vattu_congnghe: async (axios, data) => axios.post('web-tracuu/tracuu/lay_ds_vattu_congnghe', data),
    gango_vattu_congnghe: async (axios, data) => axios.post('web-tracuu/tracuu/gango_vattu_congnghe', data),

    
}