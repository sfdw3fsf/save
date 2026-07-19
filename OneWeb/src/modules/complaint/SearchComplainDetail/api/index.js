
export default{
    tracuuKNtheoMTB: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_matb', data),
    tracuuKNtheoMKN: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_ma_kn', data),
    tracuuKNtheoTBI: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_thuebao_id', data),
    getDichvuVienthong : (axios) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
    getLoaiHinh: (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
    getDanhSachTrangThaiPhieu: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_ds_trangthai_khieunai', data),
    getPhieuKhieuNai: (axios, data) => axios.post('/web-gqkn/khieunai/ds_phieu_khieunai', data),
    getKieuTaiKhoan: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_ht_kieu_tk', data),
    tracuuTB: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/tra_cuu_tb_kn', data),
    getNVTheoPhieuID: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/lay_ds_nhanvien_theo_phieukn_id', data),
    getKyKn:(axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/fn_lay_chukyno_theo_kyhoadon', data)
}
