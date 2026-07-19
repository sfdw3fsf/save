export default {
    getDSVTcbb: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_ht_dichvuvt_combobox',data),
    getLoaiHinh: async (axios, id) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId='+id),
    getKieuYeuCaucbb: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_kieu_ld_bc',data),
    getLoaiTBi: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_loai_thietbi_adsl'),
    getTocDo: async (axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_tocdo_adsl', data),
    getMucCuoc: async(axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getLoaiIP: async(axios,data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getThangTra: async(axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien',data),
    getDoiTuong: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_doituong_theo_dieukien'),
    getMucCuocTB: async(axios, data) => axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data),
    getTrangBi: async(axios) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_lay_ds_trangbi_theo_dieukien'),
    getDSHopDongTheoMaTB: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthi', data),
    getDSHopDongTheoMaTB_v2: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_matb', data),
    getTTDanhBa: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitt_danhba', data),
    //table ds thuê bao
    getDSHDTBCNTT: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_hdtb_thaydoi_tocdo_cntt', data),
    getDSHDTB: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_hienthidanhsach_hdtb', data),
    getDSHDTBTheoHDTBID: async(axios, data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id', data),
    getDSHDTBTheoHDKHID: async(axios, hdkhid) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId='+hdkhid),
    //
    getthaydoi_tocdointernet_httthdkh: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitthopdongkh', data),
    
    btnCapNhat: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_capnhat',data),
    btnXoaHDKH: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_xoa_hdkh', data),
   
    btnThemHDTB: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_themhdthuebao', data),
    btnXoaHDTB: async(axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/fn_thaydoi_tocdo_internet_xoa_hdtb', data),
    //Tham chiếu qua form lắp đặt mới
    post_lay_khoanmuc_tt_theo_loaihd : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',data),
    //kiểm tra hợp đồng khác
    kiemTraMaTBHopDongKhac: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/kiemtra_ma_tb_lap_hopdong_khac', data),

    post_lay_khuyenmai_hdtb : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khuyenmai_hdtb', data),
    post_lay_khuyenmai_hdtb_v2: async (axios, data) => axios.post('/web-hopdong/khuyenmai_tocdo_internet/lay_khuyenmai_hdtb', data),
    get_kiemTraThoiHanKM : async (axios, data) => axios.get('/web-tratruoc/hoanCongThuTienPS/kiemTraThoiHanKM',data),
    get_lay_ngaykt_km  : async (axios, data) => axios.get('/web-hopdong/lapdatmoi/lay_ngaykt_km',data),
    //Thông tin Visa
    lookupRASAccountJson  : async (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccountJson',data),
    dtService: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_tt_vdc_list_attr',data),

    GetKey  : async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',data),
    Lay_Ma_GD_V2  : async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd_v2',data),
    Lay_Ma_GD  : async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd',data),
    LAY_DS_DB_ADSL_THEO_TBID  : async (axios, thuebaoid) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId='+thuebaoid),
    LayHuongGiao_TiepNhan : async (axios, data) => axios.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan',data),

    LAY_DS_THAMSO_MD  : async (axios) => axios.get('/web-quantri/danhmuc/ds_thamso_md/0'),
    lay_ds_diachi_theo_dbtbid: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid', data),
    lay_ds_loaihinh_thuebao_dichvu: async (axios, id) => axios.get('/web-thicong/danhmuc/lay_ds_loaihinh_thuebao_dichvu?dichVuVtId=' + id),
    lay_ds_hd_adsl_theo_hdtbcntt: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_ds_hd_adsl_theo_hdtbcntt', data),
    lay_ds_hdtb_adsl_theo_hdtb_id: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=' + data),
    lay_chitiet_km_theo_hdtb_id: async (axios, data) => axios.post('/web-thicong/thicong/lay_chitiet_km_theo_hdtb_id', data),
    lay_biendong_theo_hdtb_id_cntt: async (axios, data) => axios.post('/web-hopdong/thaydoitocdoadsl/lay_biendong_theo_hdtb_id_cntt', data),
    lay_biendong_thongtin_thuebao: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_biendong_thongtin_thuebao?hdtbId='+data),
    sp_lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    lay_ds_loaiip_theo_hdtb_id: async (axios, data) => axios.get('/web-hopdong/thanhly-thuebao-bancheo/lay_ds_loaiip_theo_hdtb_id?hdtb_id=' + data),
    lay_muccuoc_thuebao: async (axios, data) => axios.get('/web-hopdong/hopdong/lay_muccuoc_thuebao?mucCuocTbId=' + data),
    get_DS_QUYEN_CHUQUAN: async (axios) => axios.get('/web-ccdv/hoanthien-hs-tachnhap-tbao/lay_dk_bang?tenBang=CHUQUAN'),
    lay_danhsach_thuoctinh_v2: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2', data),


}