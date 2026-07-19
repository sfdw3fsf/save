export default{
    get_dichvu_vienthong: async (axios, data) => axios.post('/web-hopdong/danhmuc/dichvu_vienthong', data),
    get_kieu_taikhoan: async (axios, data) => axios.get('/web-quantri/danhmuc/kieu_taikhoan/', data),
    get_donvi_ql_tt: async (axios, data) => axios.get('/web-quantri/danhmuc/donvi_ql_tt/', data),
    // getNhanVienTheoLoai: async (axios, id) => axios.get('/api/thu-no/common/nhan-vien/loai-nhan-vien/' + id),
    danhsach_doituong_hoso: async (axios, data) => axios.post('/web-quantri/danhmuc/danhsach_doituong_hoso', data),
    get_loaihinh_thuebao: async (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    get_khachang_theo_site_id: async (axios, data) => axios.get('/web-tracuu/danhba/khachang_theo_site_id', data),
    get_khachang_theo_matb: async (axios, data) => axios.get('/web-tracuu/danhba/khachang_theo_matb', data),
    get_dbtt_theo_khid: async (axios, data) => axios.get('/web-tracuu/danhba/dbtt_theo_khid', data),
    get_dbtb_theo_khid: async (axios, data) => axios.get('/web-tracuu/danhba/dbtb_theo_khid', data),
    get_tracuu: async (axios, data) => axios.post('/web-tracuu/danhba/tracuu', data),
    post_sp_hienthidanhsachdkh_khdn_frmtracuudanhba: async (axios, data) => axios.post('/web-bancheo/khdn/sp_hienthidanhsachdkh_khdn_frmtracuudanhba', data),
  }
