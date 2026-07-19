export default{
    lay_thamso_cabman_v2: async (axios) => axios.post('/web-hopdong/laydulieu/sp_tt_thamso_md',{
      p_type : 2,
      p_param : "USING_CABMAN_2.0"
    }),
    getQuyTrinh: (axios, data) => axios.post('/web-thicong/thanhly-thuebao/sp_lay_ds_quytrinh_v2', data),    
    getDichVu: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getLoaiKenh: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_KENH'),
    getTocDoPir: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getTocDoNix: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getTocDoIsp: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TOCDO_KENH'),
    getModem: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_MODEM'),
    getLoaiTBWan: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    // {"nghiepvu":"DS_THIETBI_DAUCUOI","ds_para":null}
    getTinhKN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TINH'),
    getChuQuanMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_CHUQUAN'),
    getTrangBiMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_TRANGBI'),
    getThoiHanMEGAWAN: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_THOIHAN'),
    // getDanhSachHDTB: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),
    getDanhSachNguoiGV: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),

    getThongTin: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data),
    getDanhSachHDTB: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_ds_phieu_giaoden', data),
    lay_tocdo_thaydoi_hopdong: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/lay_tocdo_thaydoi_hopdong',data),
    //web-ccdv/khaibaotsl/lay_ds_nhanvien_theo_phieu_id
    lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', data),

    //P_NGHIEPVU: LAY_THONGSO_KT P_DS_PARA: {"PORT_ID":751572, "VCI_VPI_ID":4777}
    lay_thongso_kt: (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    //"6. {{url}}thaydoi_thongso_megawan/get_hdtb_dv_by_hdtb_id {""hdtb_id"":2004874,""loaidv_id"":2}"
    get_hdtb_dv_by_hdtb_id: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/get_hdtb_dv_by_hdtb_id', data),

    //https://api-onebss-dev.vnptit3.vn/web-quantri/thamso/map_id
    lay_map_thamso: (axios, data) => axios.post('/web-quantri/thamso/map_id', data),
    fn_tt_db_thuebao: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao', data),
    fn_tt_dslam: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_dslam', data),
    ///

    //"POST: web-thicong/hths-thaydoi-tocdo/sp_ht_phieu_tra
    sp_ht_phieu_tra: (axios, data) => axios.post('/web-thicong/hths-thaydoi-tocdo/sp_ht_phieu_tra', data),

    getThongTinNhanVien: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/fn_lay_thongtin_nhanvien`,data),

    
    getLuong_Huonggiao: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/`+data.quytrinh_id+'/'+data.tthd_id),
    lay_huonggiao_theo_luong_id: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', data),
    ///web-thicong/hoinghi_truyenhinh/lay_luong_thaotac
    lay_luong_thaotac: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_luong_thaotac`,data),
    //"{{url}}thaydoi_thongso_megawan/sp_update_hdtb_mgwan {""ds_hdtb_mgwan_data"":}"
    sp_update_hdtb_mgwan: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_update_hdtb_mgwan', data),

    ///thaydoi_thongso_megawan/fn_capnhat_port_tdts_mgwan
    fn_capnhat_port_tdts_mgwan: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/fn_capnhat_port_tdts_mgwan', data),
    ///web-tracuu/hopdong/sp_lay_hdtb_mgwan_theo_hdtb_id
    sp_lay_hdtb_mgwan_theo_hdtb_id: (axios, param) => axios.get('/web-tracuu/hopdong/sp_lay_hdtb_mgwan_theo_hdtb_id?hdtb_id='+param),
    
    ///thaydoi_thongso_megawan/fn_kiemtra_chonport_tdts_mgwan
    fn_kiemtra_chonport_tdts_mgwan: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/fn_kiemtra_chonport_tdts_mgwan', data),

    //"{{url}}thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_kichhoat
    //{""ds_para"":""{ \""HDTB_ID\"":2751172, \""LUONG_ID\"":168, \""NGAY_HT\"":\""12/10/2021 11:55:00\"" }""}"
    sp_hths_thaydoi_ts_mgwan_kichhoat: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_kichhoat', data),
    //{{url}}thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_capnhat
    sp_hths_thaydoi_ts_mgwan_capnhat: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_capnhat', data),
    update_nd_thuchien: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/update_nd_thuchien', data),
    //{{url}}thaydoi_thongso_megawan/update_nd_thuchien {""phieu_id"":3621958,""nd_thuchien"":""xx""}

    lay_text_huongketnoi: (huongKNId) => axios.post(`web-hopdong/khoiphucthanhly/fn_lay_text_huongketnoi_v2`, {'huong_kn': huongKNId}).then(rs => rs.data.data),
    //{{url}}thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_hoancong
    sp_hths_thaydoi_ts_mgwan_hoancong: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_hoancong', data),
    sp_hths_thaydoi_ts_mgwan_hoanthien: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_hoanthien', data),
    hoangcong_hopdong_bancheo: (axios, data) => axios.post('/web-ccdv/thaydoi_thongso_megawan/hoangcong_hopdong_bancheo', data).then(rs => rs.data.data),
    sp_hths_thaydoi_ts_mgwan_giaonet: (axios, data) => axios.post('/web-hopdong/thaydoi_thongso_megawan/sp_hths_thaydoi_ts_mgwan_giaonet', data),

    lay_ct_thaotac_control: thaotac_id =>
    axios
      .post(`/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control`, {
        thaotac_id: thaotac_id
      })
      .then(rs => rs.data.data),
}