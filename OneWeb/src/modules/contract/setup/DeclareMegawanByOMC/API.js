export default {
   
    getNVTiepThi: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_thongtin_nhanvien_dv', data),
    getKenhThu: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_get_data_sql', data),
    getKieuHD: async (axios) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_kieu_hd'),
    getTongTien: async (axios, data) => axios.post(' /web-thicong/hoancongmegawan/lay_tongtien_theo_hdkh_id', data),
    //Thông tin khách hàng
    getLoaiHDPL: async (axios, loaihdid) => axios.get('/web-thicong/danhmuc/lay_ds_loai_hd?loaiHdId='+loaihdid),
    getQuyTrinh: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_quytrinh', data),
    //Thông tin hoàn công
    getThongSoThiCong: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_thongso_thicong', data), //checked
    getNhanVienTH: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_nhanvien_theo_donvi', data),
    getThoiHan: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    getLoaiKenh: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getLoaiModem: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getTocDo: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getTrangBi: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getLoaiNodeMGW: async (axios) => axios.post('/web-thicong/hoancongmegawan/sp_lay_loai_node_mgwan'),
    getLoaiTbiMGW: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    getChuQuan: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getDoiTac: async (axios, data) => axios.post('/web-quantri/khuyenmai/lay_ds_thamso_md', data),
    //Thông tin PE router VTN
    
    getRouterPE: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
    getTenTbi: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_thaydoi_ts_mgwan_danhmuc', data),
    getLoaiCap: async (axios) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_loai_cap'),
    //DS nhân viên thực hiện
    getDSNhanVienTH: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/lay_ds_nhanvien_theo_phieu_id', data),
    //DS thuê bao
    getDSHDThueBao: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_ds_phieu_giao_den', data),
    getPhieuTra: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_ht_phieu_tra', data),
    getDSHuongGiaoTheoQuyTrinh: async (axios, data) => axios.post('/web-thicong/hoancongmegawan/sp_lay_huonggiao_theo_quytrinh', data),
    get: async (axios) => axios.post(''),
    get: async (axios) => axios.post(''),
    get: async (axios) => axios.post(''),
    get: async (axios) => axios.post(''),
    get: async (axios) => axios.post(''),

}