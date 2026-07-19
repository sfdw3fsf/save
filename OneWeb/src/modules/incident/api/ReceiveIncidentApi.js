export default{
    
    //UR4.1.021_001
    get_donvi_vienthong_tinh: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_vienthong_tinh/', data),
    //API_UR4.1.021_002 /baoHong/dsKyHD
    //API_UR4.1.021_003  /baoHong/dsChuKyNoTheoKyHD?kyHoaDon=202107
    //API_UR4.1.021_004 /baoHong/ds_loai_thongbao_suco
    //UR4.1.021_005 
    get_donvi_duoc_thongbao: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_duoc_thongbao/', data),
    //UR4.1.021_006
    get_trangthai_xuly_suco: async (axios, data) => axios.get('/web-quantri/danhmuc/trangthai_xuly_suco/', data)
    //API_UR4.1.021_007 /baoHong/dsThongBaoSuCoTTVT POST Lấy danh sách loại sự cố theo đơn vị của người dùng
    //API_UR4.1.021_008 /baoHong/cnThongBaoSuCoTTVT POST Thêm mới thông báo sự cố trung tâm viễn thông
    //API_UR4.1.021_009 /baoHong/cnThongBaoSuCoTTVT POST Sửa
    //API_UR4.1.021_010 /baoHong/cnThongBaoSuCoTTVT POST Xóa

    //get_donvi_ql_tt: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_ql_tt/', data),
    // get_dichvu_vienthong: async (axios, data) => axios.post('/web-hopdong/danhmuc/dichvu_vienthong', data),
    // get_kieu_taikhoan: async (axios, data) => axios.get('/web-quantri/danhmuc/kieu_taikhoan/', data),
    // get_donvi_ql_tt: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_ql_tt/', data),
    // // getNhanVienTheoLoai: async (axios, id) => axios.get('/api/thu-no/common/nhan-vien/loai-nhan-vien/' + id),
    // danhsach_doituong_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_doituong_hoso', data),
    // get_loaihinh_thuebao: async (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    // get_khachang_theo_site_id: async (axios, data) => axios.get('/web-tracuu/danhba/khachang_theo_site_id', data),
    // get_khachang_theo_matb: async (axios, data) => axios.get('/web-tracuu/danhba/khachang_theo_matb', data),
    // get_dbtt_theo_khid: async (axios, data) => axios.get('/web-tracuu/danhba/dbtt_theo_khid', data),
    // get_dbtb_theo_khid: async (axios, data) => axios.get('/web-tracuu/danhba/dbtb_theo_khid', data),
    // get_tracuu: async (axios, data) => axios.post('/web-tracuu/danhba/tracuu', data),
  }