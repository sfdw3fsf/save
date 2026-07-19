export default {
    sp_ht_dichvuvt_combobox: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_ht_dichvuvt_combobox',data),
    lay_ds_loaihinh_thuebao_dichvu: async (axios, id) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    lay_ds_kieu_ld_bc: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_kieu_ld_bc',data),
    sp_lay_ds_loai_thietbi_adsl: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_loai_thietbi_adsl'),
    lay_tocdo_adsl: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_adsl', data),

    sp_tt_loai_ip: async(axios,data) => axios.post('/web-quantri/lay-dulieu/sp_tt_loai_ip', data),
    sp_tt_muccuoc: async(axios,data) => axios.post('/web-quantri/lay-dulieu/sp_tt_muccuoc', data),
    sp_tt_thangtra: async(axios,data) => axios.post('/web-quantri/lay-dulieu/sp_tt_thangtra', data),

    sp_lay_ds_doituong_theo_dieukien: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien'),
    lay_ds_mucuoc_tb_v2: async(axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
    sp_lay_ds_trangbi_theo_dieukien: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_trangbi_theo_dieukien'),
    sp_thaydoi_tocdo_internet_hienthi: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthi', data),
    sp_thaydoi_tocdo_internet_hienthitt_danhba: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitt_danhba', data),
    //table ds thuê bao
    lay_ds_hdtb_thaydoi_tocdo_cntt: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_hdtb_thaydoi_tocdo_cntt', data),
    sp_hienthidanhsach_hdtb: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_hienthidanhsach_hdtb', data),
    lay_ds_hopdong_thuebao_theo_hdkh: async(axios, hdkhid) => axios.get('web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId='+hdkhid),
    //
    sp_thaydoi_tocdo_internet_hienthitthopdongkh: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitthopdongkh', data),
    
    fn_thaydoi_tocdo_internet_capnhat: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_capnhat',data),
    //fn_thaydoi_tocdo_internet_xoa_hdkh: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_xoa_hdkh', data),
   
    fn_thaydoi_tocdo_internet_themhdthuebao: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_themhdthuebao', data),
    fn_thaydoi_tocdo_internet_xoa_hdtb: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_xoa_hdtb', data),
    //Tham chiếu qua form lắp đặt mới
    post_lay_khoanmuc_tt_theo_loaihd : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',data),
    //kiểm tra hợp đồng khác
    kiemtra_thuebao_lap_hopdong_khac: async(axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),

    post_lay_khuyenmai_hdtb: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khuyenmai_hdtb', data),
    get_kiemTraThoiHanKM: async (axios, data) => axios.get('/web-tratruoc/hoanCongThuTienPS/kiemTraThoiHanKM?'+data),
    get_lay_ngaykt_km: async (axios, data) => axios.get('/web-hopdong/lapdatmoi/lay_ngaykt_km',data),
    //Thông tin Visa
    lookupRASAccountJson: async (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccountJson',data),
    lay_ds_thamso_md: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_tt_vdc_list_attr',data),

    post_get_keys: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',data),
    sinh_magd_v2: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd_v2',data),
    sinh_magd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd',data),
    lay_ds_danhba_adsl: async (axios, thuebaoid) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId='+thuebaoid),
    lay_huonggiao_tiepnhan: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan',data),
    ds_thamso_md: async (axios) => axios.get('/web-quantri/danhmuc/ds_thamso_md/0'),
    kiemtra_quyen_nd: async (axios, data) => axios.post('/web-hopdong//lapdatmoi/kiemtra_quyen_nd',data),
    post_lay_tien_khoanmuc_ld_v2 : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_tien_khoanmuc_ld_v2',data),
    ur2_1_035_038_temp : async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/ur2_1_035_038_temp',data),

    fn_tt_db_khachhang : async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang',data),
    checkdataMAP_ID_muccuoctb : async (axios,data) => axios.get('/web-tracuu/danhba/lay_muccuoc_tb_by_id?muccuocId='+data),
    lay_ds_danhba_cntt: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_cntt?thueBaoId='+data),
    lay_danhsach_thuoctinh_v2: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2',data),
    fn_lay_thongtin_nhanvien: async (axios, data) => axios.post('/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien',data),

    lay_ds_hopdong_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb',data),
    lay_ds_diachi_theo_dbtbid: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',data),
    lay_ds_hdtb_adsl_theo_hdtb_id: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId='+data),
    lay_chitiet_km_theo_hdtb_id: async (axios, data) => axios.post('/web-thicong/thicong/lay_chitiet_km_theo_hdtb_id',data),
    sp_Lay_tienhd_km_theo_hdtb: async (axios, data) => axios.post('/web-hopdong/thuebao_datcoc_thunghiem/sp_Lay_tienhd_km_theo_hdtb',data),
    lay_biendong_thongtin_thuebao: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_biendong_thongtin_thuebao?hdtbId='+data),
    lay_biendong_theo_hdtb_id_cntt: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_biendong_theo_hdtb_id_cntt',data),
    lay_ds_hd_adsl_theo_hdtbcntt: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_hd_adsl_theo_hdtbcntt',data),
    post_lay_ds_mucuoc_tb_v2 : async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
    lay_muccuoc_thuebao : async (axios, data) => axios.get('/web-hopdong/hopdong/lay_muccuoc_thuebao?mucCuocTbId='+ data),
    lay_ds_loaiip_theo_hdtb_id : async (axios, data) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_loaiip_theo_hdtb_id?hdtb_id='+ data),

    kiem_tra_quyen_nguoidung_chuquan: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/kiem_tra_quyen_nguoidung_chuquan',data),
    get_DS_QUYEN_CHUQUAN: async (axios) => axios.get('/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang?tenBang=CHUQUAN'),
    lay_ds_td_mytv_v2: async (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_ds_td_mytv_v2',data),
    kiemtra_hd_bancheo_tc: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_hd_bancheo_tc',data),
    lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd',data),
    lay_ds_hopdong_tb_theo_hdtb_id: async (axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id',data),
    lay_tocdo_thuc_adsl_theo_tocdo_id: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_thuc_adsl_theo_tocdo_id',data),
    post_fn_lay_thongtin_tc : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_thongtin_tc',data), //tham chiếu frm lắp mới
    lay_biendong_theo_hdtb_id: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_biendong_theo_hdtb_id',data),
    post_fn_lay_thang_sd : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_thang_sd',data),//tham chiếu frm lắp mới
    xoa_hdkh_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/xoa_hdkh_v2',data),
    post_fn_lay_tt_taodl_hdtb_adsl : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_tt_taodl_hdtb_adsl',data), //frm lắp mới
    lay_ds_tien_dc_conlai_doi_td: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_tien_dc_conlai_doi_td',data),

    fn_tt_tocdo_adsl: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',data),
    fn_tt_loaihinh_tb: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb',data),
    fn_tt_tocdo_adsl: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',data),
    fn_tt_loaihinh_tb: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb',data),
    fn_tt_tocdo_adsl: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',data),
    fn_tt_nhanvien: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien',data),

    kiemtra_makh_mtt: async (axios, data) => axios.post('/web-thicong/thicong/kiemtra_makh_mtt',data),
    tracuu_no_thuebao: async (axios, data) => axios.get('/web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId='+data),
   
    lay_tt_khachhang_phieu_tuvan: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_tt_khachhang_phieu_tuvan?hdkhchaId='+data),

    kiemtra_tb_mytv_chinhphu: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_tb_mytv_chinhphu',data),
    sp_load_hopdong_b2a: async (axios, data) => axios.get('/web-hopdong/thaydoitocdoadsl/sp_load_hopdong_b2a/', data),
    kiemtra_tb_dongbo_epg_vtt: async (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/kiemtra_tb_dongbo_epg_vtt',data),

    thongtin_nguoidung2: async (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2',data),
    getPackageEPG: async (axios, data) => axios.post('/tichhop/vasc/getPackageEPG',data),

    kiemtra_hdtb_khi_themmoi: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi',data),
    
    sp_lay_danhsach_congnghe: async (axios, data) => axios.post('/web-hopdong/hopdong/sp_lay_danhsach_congnghe',data),

    sp_kt_thoigian_datcoc_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/sp_kt_thoigian_datcoc_v2',data),

    kiemtra_tuvan_mytv: async (axios, data) => axios.get('/web-ccdv/tuvan_mytv/kiemtra_tuvan_mytv?hdkh_id='+data+'&loai=1'),
    lay_danhmuc_tuvan_mytv: async (axios, data) => axios.get('/web-ccdv/tuvan_mytv/lay_danhmuc_tuvan_mytv'),

    sp_lay_ds_dangky_dvgt_v2: async (axios, data) => axios.post('/web-hopdong/thanhly/lay_ds_dangky_dvgt_v2',data),

    LAY_DS_DB_ADSL_THEO_TBID: async (axios, thuebaoId) => axios.get(`/web-tracuu/tracu-danhba/ds_adsl_tb/${thuebaoId}`),

    get_fn_ktra_tocdo_dk_greennet: async (axios, data) => axios.get('/web-tracuu/hopdong/fn_ktra_tocdo_dk_greennet',data),

    changeGreenNet: async (axios, data) => axios.post('/tichhop/visa-com/changeGreenNet',data),

    sp_lay_ds_doituong_theo_tocdo: async (axios, data) => axios.post('/web-tracuu/tracuu/sp_lay_ds_doituong_theo_tocdo',data),

    fn_kiemtra_nangluc_mangluoi_xgspon_v2: async (axios, data) => axios.post('/web-tracuu/tracuu/fn_kiemtra_nangluc_mangluoi_xgspon_v2',data),
}