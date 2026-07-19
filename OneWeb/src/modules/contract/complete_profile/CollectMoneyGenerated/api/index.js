export default {
    getCBDichVu: (axios) => 
    axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

    getKQXL: (axios, params) => 
    axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_KETQUA_XULY', {params: params}),

    getTTTienKhoanMuc: (axios, params) => 
    axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_TIENKHOANMUC_LOAIHD', {params: params}),

    getDsNVGiaoViec: (axios, params) => 
    axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_DS_NHANVIEN_THEO_DONVI', {params: params}),

    getDsQuyTrinh: (axios, body) => 
    axios.post('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_DS_QUYTRINH_V2', body),

    getDsHDTBGiaoDenThuTien: (axios, body) => 
    axios.post('/web-hopdong//HoanCongThuTienPhatSinh/LAY_DS_HDTB_GIAODEN_THUTIEN', body),

    getLayTocDoCuTocDoMoi: (axios, params) =>  
    axios.get('/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_TOCDO_CU_TOCDO_MOI', {params: params}),

    
}