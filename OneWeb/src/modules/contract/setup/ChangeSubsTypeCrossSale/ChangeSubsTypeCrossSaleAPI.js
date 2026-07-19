export default {
    sp_lay_tienkhoanmuc_theo_loaihd: async (axios, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_tienkhoanmuc_theo_loaihd',{
        loaihd_id:loaihd_id
    }),
    ds_thamso_md: async (axios, kieu) => axios.get('/web-quantri/danhmuc/ds_thamso_md/'+kieu),
    lay_ds_loaihinh_tb: async (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB'),

    lay_danhmuc_cdlh_tt_chung: async (axios, chuoi) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_danhmuc_cdlh_tt_chung',{
        vchuoi:chuoi
    }),
    kiemtra_quyen_nd: async (axios, quyen_id) => axios.post('/web-hopdong/lapdatmoi/kiemtra_quyen_nd', {
        quyen_id:quyen_id
    }),
    thongtin_nguoidung: async (axios) => axios.post('/quantri/user/thongtin_nguoidung2', {}),
    lay_khuyenmai_hdtb: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khuyenmai_hdtb', data),
    lay_ds_kieu_ld_lhtb: async (axios, loaihd_id, loaitb_id, tinhkhac) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_kieu_ld_lhtb',{
        vloaihd_id: loaihd_id,
        vloaitb_id: loaitb_id,
        vtinhkhac: tinhkhac
    }),
    sp_ht_lhtb_moi_combobox: async (axios, kieuld_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_lhtb_moi_combobox', {
        vkieuld_id: kieuld_id
    }),

    //bỏ
    //fn_map_id: async (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/fn_map_id', data),
    // Map table db_thuebao, type thứ tự cột map tương ứng
    fn_map_db_thuebao: async (axios, thuebao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', {
        param: thuebao_id, 
        type: type
    }),

    fn_map_goicuoc_dd: async (axios, goicuoc_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_goicuoc_dd', {
        param: goicuoc_id, 
        type: type
    }),

    fn_map_tocdo_adsl: async (axios, tocdo_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl', {
        param: tocdo_id, 
        type: type
    }),

    fn_map_db_adsl: async (axios, thuebao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_adsl', {
        param: thuebao_id, 
        type: type
    }),

    fn_map_hdtb_dd: async (axios, hdtb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_dd', {
        param: hdtb_id, 
        type: type
    }),

    fn_map_muccuoc_tb: async (axios, muccuoctb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_muccuoc_tb', {
        param: muccuoctb_id, 
        type: type
    }),

    fn_map_khuyenmai: async (axios, khuyenmai_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_khuyenmai', {
        param: khuyenmai_id, 
        type: type
    }),
    fn_map_hd_thuebao: async (axios, hdtb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', {
        param: hdtb_id, 
        type: type
    }),
    fn_map_loaihinh_tb: async (axios, loaitb_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', {
        param: loaitb_id, 
        type: type
    }),

    fn_map_bancheo_muccuoc_tb: async (axios, muccuoctb_id, type, tinh_tc) => axios.post('/web-bancheo/tracuu/fn_tt_muccuoc_tb', {
        param:muccuoctb_id,
        type:type,
        tinhthicong_id:tinh_tc
    }),
    fn_map_bancheo_db_thuebao: async (axios, thuebao_id, type, tinh_tc) => axios.post('/web-bancheo/tracuu/fn_tt_db_thuebao', {
        type: type,
        thuebao_id: thuebao_id, 
        tinththicong_id: tinh_tc 
    }),
    fn_map_bancheo_tocdo_adsl: async (axios, tocdo_id, type, tinh_tc) => axios.post('/web-bancheo/tracuu/fn_tt_tocdo_adsl', {
        type: type,
        tinhthicong_id: tinh_tc,
        param: tocdo_id
    }),
    fn_map_bancheo_db_adsl: async (axios, thuebao_id, type, tinh_tc) => axios.post('/web-bancheo/tracuu/fn_tt_db_adsl', {
        type: type,
        tinhthicong_id: tinh_tc,
        param: thuebao_id 
    }),
    

    sp_lay_ctkm_loai_tbi: async (axios, ds_ctkm_id, loaitbi_id) => axios.post('/web-ccdv/ban-thiet-bi/sp_lay_ctkm_loai_tbi', {
        ds_ctkm_id: ds_ctkm_id,
        loaitbi_id: loaitbi_id
    }),
    sp_lay_ds_tragop_thietbi: async (axios, ds_ctkm_id, loaitbi_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_tragop_thietbi', {
        p_ds_ctkm_id: ds_ctkm_id,
        p_loaitbi_id: loaitbi_id
    }),
    lay_ds_tien_dc_conlai: async (axios, thuebao_id, loaitb_id, thang_thoai) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_tien_dc_conlai', {
        vthuebao_id: thuebao_id,
        vloaitb_id: loaitb_id,
        vthang_thoai: thang_thoai
    }),

    kiemtra_thoihan_khuyenmai: async (axios, khuyenmai_id, ngay_yc) => axios.post('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thoihan_khuyenmai', {
        khuyenmai_id: khuyenmai_id,
        ngay_yc:ngay_yc
    }),

    sp_lay_tocdo_adsl: async (axios, loaitb_id) => axios.post('/web-hopdong/dichvu-ca/sp_lay_tocdo_adsl', {
        loaitb_id: loaitb_id
    }),

    sp_ht_tocdo_adsl_combobox_byloaitb: async (axios, loaitb_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_tocdo_adsl_combobox_byloaitb', {
        p_loaitb_id: loaitb_id
    }),

    lay_ds_mucuoc_tb_v2: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),

    sp_lay_cuoctk_cuoctc: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/sp_lay_cuoctk_cuoctc', data),

    lay_tocdo_thuc_adsl_theo_tocdo_id: async (axios, tocdo_id) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_thuc_adsl_theo_tocdo_id', {
        tocdo_id: tocdo_id
    }),

    lay_tien_khoanmuc_ld: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld', data),

    kiemtra_thanhtoan_datmoi_lh: async (axios, hdkh_id) => axios.get('/web-hopdong/thaydoithongtinkhuyenmai/kiemtra_thanhtoan_datmoi_lh?hdkh_id='+hdkh_id),

    lay_tinh_theo_matb: async (axios, matb) => axios.get('/web-bancheo/thaydoimegawan/lay_tinh_theo_matb?matb='+matb),
    
    sp_ds_db_thuebao_bancheo_theo_matb: async (axios, ma_tb) => axios.post('/web-baohong/TiepNhanBaoHong/sp_ds_db_thuebao_bancheo_theo_matb',{
        vma_tb: ma_tb
    }),

    lay_tinhtc_id_tsl: async (axios, dichvuvt_id, ma_tb, tinhthicong_id) => axios.post('/web-bancheo/tracuu/lay_tinhtc_id_tsl',{
        dichvuvt_id: dichvuvt_id,
        ma_tb: ma_tb,
        tinhthicong_id: tinhthicong_id
    }),

    sp_lay_thuebaoid_thicong_theo_matb: async (axios, data) => axios.post('/web-baohong/TiepNhanBaoHong/sp_lay_thuebaoid_thicong_theo_matb',data),

    lay_ds_hopdong_theo_ma_tb: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb', data),
    
    sp_lay_danhba_theo_matb: async (axios, data) => axios.post('/web-hopdong/tracuulichsutb/sp_lay_danhba_theo_matb', data),

    kiemtra_thuebao_lap_hopdong_khac: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),

    lay_ds_chitiet_km_theo_tbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid', {
        vkieu: 2,
        vthuebao_id: thuebao_id
    }),

    lay_tinh_theo_thuebaoid: async (axios, thuebao_id) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_tinh_theo_thuebaoid?thuebao_id='+thuebao_id),

    lay_thuebaoid_thicong: async (axios, thuebao_id, tinh_id) => axios.post('/web-hopdong/chuyendoi-codinh-sang-ims-bancheo/lay_thuebaoid_thicong',{
        vthuebao_id: thuebao_id,
        vtinh_id: tinh_id
    }),

    lay_danhba_theo_thuebaoid: async (axios, tinhthicong_id, thuebao_id, dichvuvt_id, donvi_id, loaihdbc) => axios.post('/web-bancheo/tracuu/lay_danhba_theo_thuebaoid',{
        dichvuvt_id: dichvuvt_id,
        donvi_id: donvi_id,
        loaihdbc: loaihdbc,
        thuebao_id: thuebao_id,
        tinhthicong_id: tinhthicong_id
    }),

    //Thông số cố định
    lay_ds_db_cd_theo_tbid: async (axios, thuebao_id) => axios.get('/web-hopdong/chuyendich-codinh/lay_ds_db_cd_theo_tbid?thuebao_id='+thuebao_id),
    lay_ds_db_cd_theo_tbid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_ds_db_cd_theo_tbid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),
    //Thông số ims
    lay_ds_danhba_ims: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId='+thuebao_id),
    lay_ds_db_ims_theo_tbid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_ds_db_ims_theo_tbid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),
    //Thông số megawan/metronet
    lay_ds_danhba_megawan: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId='+thuebao_id),
    lay_ds_db_megawan_theo_tbid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_ds_db_megawan_theo_tbid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),
    //Thông số TSL
    lay_ds_danhba_tsl: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId='+thuebao_id),
    lay_ds_db_tsl_theo_tbid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_ds_db_tsl_theo_tbid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),
    //Thông số ADSL
    lay_ds_db_adsl_theo_tbid: async (axios, thuebao_id) => axios.get('/web-hopdong/chuyendich-adsl/lay_ds_db_adsl_theo_tbid?thuebao_id='+thuebao_id),
    lay_ds_db_adsl_theo_tbid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_ds_db_adsl_theo_tbid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),

    fn_lay_khachhangid_thicong: async (axios, khachhang_id, tinh_id) => axios.post('/web-hopdong/chuyendich-codinh/fn_lay_khachhangid_thicong',{
        p_khachhang_id: khachhang_id,
        p_tinhthicong_id: tinh_id
    }),

    lay_thongso_thuebao_thuebaoid: async (axios, thuebao_id) => axios.get('/web-hopdong/hopdong/lay_thongso_thuebao_thuebaoid?thueBaoId='+thuebao_id),
    lay_thongso_thuebao_thuebaoid_bancheo: async (axios, thuebao_id, tinh_id) => axios.post('/web-bancheo/tracuu/lay_thongso_thuebao_thuebaoid',{
        thuebao_id: thuebao_id,
        tinhthicong_id: tinh_id
    }),
    
    sp_lay_ds_khuvuc_theo_thuebaoid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_ds_khuvuc_theo_thuebaoid', {
        vthuebao_id: thuebao_id
    }),
    lay_ds_diachi_theo_dbtbid: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_diachi_theo_dbtbid', {
        thuebao_id: thuebao_id
    }),

    lay_ds_isdn_theo_thuebao_id: async (axios, thuebao_id) => axios.post('/web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id', {
        thuebao_id: thuebao_id
    }),

    fn_lay_tt_hienthittdiachi_tsl_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_lay_tt_hienthittdiachi_tsl_cdlhtb', {
        p_ds_para: ds_para
    }),
    fn_lay_text_huongketnoi_v2: async (axios, huong_kn) => axios.post('/web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2', {
        huong_kn:huong_kn
    }),
    //6:donvi_dau_id, 7:donvi_cuoi_id
    fn_tt_db_thuebao: async (axios, thuebao_id, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', {
        param: thuebao_id,
        type: type
    }),
    
    sp_lay_ds_donvi_theo_loaidv_v5: async (axios, donvi_id, loaidv_id) => axios.post('/web-hopdong/khoiphucthanhly/sp_lay_ds_donvi_theo_loaidv_v5', {
        donvi_id: donvi_id,
        loaidv_id: loaidv_id
    }),
    sp_lay_ds_tramtc_thuebao: async (axios, thuebao_id, loaidv_id, kieudv_id) => axios.post('/web-hopdong/thaydoi-thongsokythuat-tsl/sp_lay_ds_tramtc_thuebao', {
        p_db_id: thuebao_id,
        p_kieudv: kieudv_id,
        p_loai: loaidv_id
    }),
    lay_danhba_theo_matb_bancheo: async (axios, data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/lay_danhba_theo_matb', data),
    
    xoa_hopdong_thuebao: async (axios, hdtbId) => axios.post('/web-hopdong/hopdong/xoa_hopdong_thuebao?hdtbId='+hdtbId,{}),
    
    //UR2.1.033_017: CSS.LAY_DS_HOPDONG_THEO_MA_GD https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E1085
    lay_ds_hopdong_theo_ma_gd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_gd', data),

    xoa_hdbc_hdtb: async (axios, hdtbid, loaihd_id) => axios.post('/web-ccdv/ds_yeucau_khaosat/xoa_hdbc_hdtb', {
        vhdtbid: hdtbid,
        vloaihdid: loaihd_id
    }),

    xoa_hdbc: async (axios, hdkh_id, loaihd_id) => axios.post('/web-ccdv/ds_yeucau_khaosat/xoa_hdbc', {
        //Nhầm param vhdtbid tương ứng hdkh_id
        vhdtbid: hdkh_id,
        vloaihdid: loaihd_id
    }),
    xoa_hdkh_v2: async (axios, hdkh_id, loaihd_id) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdkh_v2', {
        hdkh_id:hdkh_id,
        loaihd_id:loaihd_id
    }),
    
    fn_lay_tt_ht_hdkh_cdlhtb_bancheo: async (axios, param) => axios.post('/web-baohong/TiepNhanBaoHong/fn_lay_tt_ht_hdkh_cdlhtb_bancheo', {
        ds_para: param
    }),

    lay_thongso_thuebao_hdbc: async (axios, hdtb_id) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_thongso_thuebao_hdbc?hdtbid='+hdtb_id),
    
    lay_ds_loaiip_theo_hdtb_id: async (axios, hdtb_id) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_loaiip_theo_hdtb_id?hdtbid='+hdtb_id),
    lay_ds_kieunt_theo_hdtb_id: async (axios, hdtb_id) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_kieunt_theo_hdtb_id?hdtbid='+hdtb_id),
    
    lay_tinhtc_id_tsl: async (axios,dichvuvt_id, matb) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/lay_tinhtc_id_tsl', {
        vdichvuvt_id: dichvuvt_id,
        vma_tb: matb
    }),

    sp_lay_hdtb_tsl_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/sp_lay_hdtb_tsl_theo_hdtb_id', {
        p_hdtb_id: hdtb_id
    }),

    lay_ds_diachi_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-hopdong/dichvu-ca/lay_ds_diachi_theo_hdtb_id', {
        in_hdtb_id: hdtb_id
    }),

    fn_map_bd_thuebao: async (axios, param, type) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bd_thuebao', {
        param: param,
        type: type
    }),

    sp_lay_ds_khuvuc_theo_hdtb_id_bancheo: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/sp_lay_ds_khuvuc_theo_hdtb_id_bancheo', {
        vhdtb_id: hdtb_id
    }),
    sp_lay_tt_ketcuoi_bancheo: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/sp_lay_tt_ketcuoi_bancheo', {
       vhdtb_id: hdtb_id
    }),
    lay_ds_chitiet_km_theo_hdtb: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_chitiet_km_theo_hdtb', {
       in_hdtb: hdtb_id
    }),
    //Lỗi code ouput
    lay_tien_hd_km_theo_hdtb: async (axios, hdtb_id) => axios.get('/web-hopdong/hoanthien-hs-doisim/lay_tien_hd_km_theo_hdtb/'+hdtb_id), 

    lay_chitiet_km_theo_hdtb_id: async (axios, hdtb_id, loai_km) => axios.get('/web-ccdv/HoanCongThuTienPhatSinh/lay_chitiet_km_theo_hdtb_id?vhdtb_id='+hdtb_id+'&&vloai_km='+loai_km), 
    
    lay_ds_mua_tbi_lhd: async (axios, hdtb_id) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_mua_tbi_lhd', {
       in_hdtb: hdtb_id
    }),

    sp_lay_tienhd_km_theo_hdtb: async (axios, hdtb_id) => axios.post('/web-ccdv/sua-tien-hop-dong/sp_Lay_tienhd_km_theo_hdtb', {
       vhdtb_id: hdtb_id
    }),

    //Lấy thông tin hợp đồng
    lay_ds_hopdong_thuebao_codinh: async (axios, hdtb_id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_codinh?hdtbId='+hdtb_id),
    lay_ds_hopdong_thuebao_adsl: async (axios, hdtb_id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId='+hdtb_id),
    lay_ds_hopdong_thuebao_mgwan: async (axios, hdtb_id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId='+hdtb_id),
    lay_ds_hdtb_tsl_theo_hdtb_id: async (axios, hdtb_id) => axios.post('/web-hopdong/DiemChiaTrungGianTSL/lay_ds_hdtb_tsl_theo_hdtb_id', {
       hdtb_id: hdtb_id
    }),
    lay_ds_hopdong_thuebao_ims: async (axios, hdtb_id) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_ims?hdtbId='+hdtb_id),

    sp_lay_ds_hdtb_dv_kieudv: async (axios, hdtb_id, loaidv_id, kieudv_id) => axios.post('/web-ccdv/khaibaotsl/sp_lay_ds_hdtb_dv_kieudv', {
        hdtb_id: hdtb_id,
        kieudv_id: kieudv_id,
        loaidv_id: loaidv_id
    }),
    fn_tt_taodulieu_hdkh_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdkh_cdlhtb', {
        p_ds_para:ds_para
    }),
    get_keys: async (axios, keyname) => axios.post('/web-hopdong/goi-da-dichvu/get_keys', {
        keyname: keyname,
        numblocksize: 1,
        numretry:10
    }),
    get_key_bancheo: async (axios, keyname, tinh_tc) => axios.post('/web-bancheo/tracuu/get_key', {
        keyname: keyname,
        numblocksize: 1,
        numretry:10,
        tinhthicong_id: tinh_tc
    }),
    
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=620043524&range=E4404
    fn_kt_thaotac: async (axios, luong_id, code) => axios.post('/web-hopdong/khoiphucthanhly/fn_kt_thaotac', {
        luong_id:luong_id,
        code:code
    }),
    sp_ht_danhmuc_dv_tdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_ht_danhmuc_dv_tdlhtb', {
        p_ds_para:ds_para
    }),
    ur2_1_035_038_temp: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtb/ur2_1_035_038_temp', {
        thuebao_id:thuebao_id
    }),
    sp_lay_donvi_theo_loaidv_diachi_ld_v1: async (axios, data) => axios.post('/web-hopdong/thaydoiloaihinhtb/sp_lay_donvi_theo_loaidv_diachi_ld_v1', data),

    lay_ds_db_cd_theo_tbid_pro: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid', {
        thuebao_id:thuebao_id
    }),
    lay_ds_db_ims_theo_tbid_pro: async (axios, thuebao_id) => axios.post('/web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', {
        thuebao_id:thuebao_id
    }),
    sp_lay_dauso_ims_theo_tinh: async (axios, tentat) => axios.post('/web-hopdong/khaibao-ims-bosung/sp_lay_dauso_ims_theo_tinh', {
        p_tentat: tentat
    }),
    fn_tt_taodulieu_hdtb_adsl_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdtb_adsl_cdlhtb', {
        p_ds_para: ds_para
    }),
    fn_tt_taodulieu_hdtb_mgwan_cdlhtb: async (axios, ds_para) => axios.post('/web-hopdong/thaydoiloaihinhtb/fn_tt_taodulieu_hdtb_mgwan_cdlhtb', {
        p_ds_para: ds_para
    }),
    sp_lay_tinh_tc_theo_tinh_id: async (axios, tinh_tc) => axios.get('/web-hopdong/khuyenmai_tocdo_internet/sp_lay_tinh_tc_theo_tinh_id?p_tinh_id='+tinh_tc),
    
    kiemtra_makh_matt_magd: async (axios, ma_kh, loaihd_id, kieu) => axios.post('/web-hopdong/hopdong/kiemtra_makh_matt_magd', {
        kieu: kieu,
        loaiHdId: loaihd_id,
        maKh: ma_kh
    }),
    kiemtra_tb_datmoi_isdn: async (axios, id_sochinh, loaitb_id, loai) => axios.post('/web-tracuu/inphieuthu/kiemtra_tb_datmoi_isdn', {
        vid_sochinh: id_sochinh,
        vloaitb_id: loaitb_id,
        vloai: loai
    }),
    kiemtra_thaydoi_goi_dadv_1: async (axios, thuebao_id, loaitb_id, tocdo_id, muccuoc_id) => axios.post('/web-tracuu/inphieuthu/kiemtra_thaydoi_goi_dadv_1', {
        vthuebao_id: thuebao_id,
        vloaitb_id: loaitb_id,
        vtocdo_id: tocdo_id,
        vmuccuoc_id: muccuoc_id
    }),

    lay_ds_thamso_md_mats: async (axios, ma_ts) => axios.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', {
        ma_ts: ma_ts
    }),

    kiemtra_danhba_khi_themmoi: async (axios, ma_tb, khachhang_id, thuebao_id, dichvuvt_id) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', {
        dichVuVtId: dichvuvt_id,
        khachHangId: khachhang_id,
        maTb: ma_tb,
        thueBaoId: thuebao_id
    }),

    layhuonggiao_tiepnhan_v2: async (axios, data) => axios.post('/web-hopdong/thanhly-thuebao-bancheo/layhuonggiao_tiepnhan_v2', data),
    layhuonggiao_tiepnhan: async (axios, data) => axios.post('/web-bancheo/danhmuc/layhuonggiao_tiepnhan', data),
    
    fn_ins_update_hd_cdlh_bancheo: async (axios, ds_para) => axios.post('/web-ccdv/ds_yeucau_khaosat/fn_ins_update_hd_cdlh_bancheo', {
        p_ds_para: ds_para
    }),

    fn_themmoi_hdbancheo_biendong: async (axios, ds_para) => axios.post('/web-ccdv/ds_yeucau_khaosat/fn_themmoi_hdbancheo_biendong', {
        p_ds_para: ds_para
    }),
    fn_themmoi_hdbancheo_biendong: async (axios, ds_para) => axios.post('/web-ccdv/ds_yeucau_khaosat/fn_themmoi_hdbancheo_biendong', {
        p_ds_para: ds_para
    }),
    fn_capnhat_hdbancheo_biendong: async (axios, ds_para) => axios.post('/web-ccdv/ds_yeucau_khaosat/fn_capnhat_hdbancheo_biendong', {
        p_ds_para: ds_para
    }),
    update_huonggiao_tiepnhan: async (axios, hdtb_id, huonggiao_id) => axios.post('/web-hopdong/chuyendich/update_huonggiao_tiepnhan', {
        hdtb_id: hdtb_id,
        huonggiao_id: huonggiao_id
    }),

    sinh_malt: async (axios, data) => axios.post('web-bancheo/tracuu/sinh_malt', data),
    kiemtra_hdtb_khi_themmoi: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi', data),

    kiemtra_tb_cung_makh_tb_pl: async (axios, ma_gd, thuebao_id) => axios.post('/web-hopdong/khoiphucthanhly/kiemtra_tb_cung_makh_tb_pl', {
        ma_gd:ma_gd,
        thuebao_id:thuebao_id
    }),

    fn_themtb_cdlh_bancheo: async (axios, ds_para) => axios.post('/web-hopdong/chuyendich-codinh/fn_themtb_cdlh_bancheo', {
        vtaodulieu:ds_para
    }),

    sp_lay_danhsach_donvi: async(axios,data)=> axios.post("/web-bancheo/tracuu/sp_lay_danhsach_donvi",data)
    
}