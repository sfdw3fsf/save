export default {
    //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=288822852  Link xem api
    //#region load form
    lay_ds_loai_hinh: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmtonnhanhkhac_laydsloaihinh', data),
    lay_ds_nguoi_dung_tinh: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmtonnhanhkhac_lay_ds_ngdung_tinh', data),
    lay_ds_nguoi_dung_don_vi_con: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/lay_ds_nd_dv_con', data),
    lay_danh_muc: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmtonnhanhkhac_lay_danhmuc', data),
    lay_ds_donvi_dai_htkh: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/lay_ds_donvi_dai_htkh', data),
    //#endregion

    //#region tìm kiếm
    lay_du_lieu_006: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmtonnhanhkhac_lay_dulieu_006', data),
    lay_du_lieu_20: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmtonnhanhkhac_lay_dulieu_20', data),
    lay_du_lieu_200: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_200', data),
    lay_du_lieu_201: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_201', data),
    lay_du_lieu_202: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_202', data),
    lay_du_lieu_203: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_203', data),
    lay_du_lieu_208: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_208', data),
    lay_du_lieu_209: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/frmTonNhanhKhac_lay_dulieu_209', data),
    //#endregion

    giuphieu_bh_nhanhkhac: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/giuphieu_bh_nhanhkhac', data),
    test_port: async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_thamso_macdinh', data),
    lay_ds_donvi_dai_htkh: async (axios, data) => axios.post('/web-ccdv/kiemsoat_ton_phieubaohong/lay_ds_donvi_dai_htkh', data),

    sp_lay_ds_nguoidung_tinh: async (axios, nhanvien_id, quyen) => axios.get(`/web-baohong/TiepNhanBaoHong/sp_lay_ds_nguoidung_tinh?nhanVienID=${nhanvien_id}&quyen=${quyen}`),
    lay_ds_donvi: async (axios) => axios.post(`/web-hopdong/ds_phieukhaosat_taptrung/lay_ds_donvi`),
}