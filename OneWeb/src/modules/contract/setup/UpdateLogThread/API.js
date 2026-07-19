export default{

    getDonvi:async (axios, donvicha) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI?donvi_cha_id='+donvicha),
    //getDonvi:async (axios) => axios.get('/web-quantri/danhmuc-chung/ADMIN_DONVI'),
    getDonViIDTheoND: async(axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_id_theo_ma_nd', data),
    getDonViTheoID: async(axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_donvi_theo_id', data),
    getLoaiNhom: async(axios) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_loainhom_capnhat_hen'),
    getTrangThai: async(axios) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_trangthai_capnhat_hen'),
    getDVVT: async(axios) => axios.post('/web-thicong/tienhtrinh_tcbh/ds_ht_dichvu_vt_combobox_dt'),
    getDSPhieuHenTC: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_phieu_hen_xuly_tientrinh_tc', data),
    getDSPhieuHenBH: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_phieu_henxly_tientrinh_bh', data),
    getDSPhieuHenBaoDuong: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_phieu_henxly_tientrinh_bd', data),
    lay_ds_hen_kh: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hen_kh', data),
    getDSTienTrinhBH: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_tientrinh_bh_theo_baohong_id', data),
    getDSTienTrinhTC: async(axios, data) => axios.get('web-thicong/tientrinh_thicong/lay_ds_tientrinh_xuly?hdtbId='+data),

    lay_ds_tientrinh_baoton: async(axios, data) => axios.get('/web-thicong/tientrinh_thicong/lay_ds_tientrinh_baoton?phieuId='+data),

    getThongTinXLTienTrinh: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/laythongtin_xuly_tientrinh', data),
    lay_ds_hdtb_adsl_theo_hdtb_id: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_adsl_theo_hdtb_id', data),
    lay_ds_hdtb_mgwan_theo_hdtb_id: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_mgwan_theo_hdtb_id', data),
    lay_ds_hdtb_tsl_theo_hdtb_id: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_tsl_theo_hdtb_id', data),
    xoa_xuly_tientrinh: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/xoa_xuly_tientrinh', data),
    suaXLTienTrinh: async(axios, data) => axios.post('', data),
    henKH: async(axios, data) => axios.post('', data),
    taoGhiChu: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/tao_ghichu_tientrinh', data),
    getLoaiHD: async(axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HD'),

    lay_ds_phieu_tientrinh_tc: async(axios, data) => axios.post('/web-thicong/tientrinh_thicong/lay_ds_phieu_tientrinh_tc', data),
    insert_tientrinh_tc_bh: async(axios, data) => axios.post('/web-thicong/tienhtrinh_tcbh/insert_tientrinh_tc_bh', data),
    fn_tt_db_con: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_con',data),
    fn_tt_loai_khl: async (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loai_khl',data),
}