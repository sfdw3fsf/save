export default{
    getDonVi: (axios) => axios.post('/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_dsdonvi'),
    getNhanVien: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_get_nhanvien_donvi', data),
    getLoaiTinNhan: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_loaitin', data),
    getNhanVienGanTN: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_getnhanvien_donvi_dagan_tn', data),
    getDonViDagan: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_donvi', data),
    getKieuCanhbao: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_ql_tinnhan_kieucb', data),
    ghilai: (axios, data) => axios.post('/web-quantri/quanlytinnhanv2/sp_nhanvien_tn_update', data),
  }
