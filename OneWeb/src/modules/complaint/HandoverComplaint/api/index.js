export default{
    getDanhSachTrangThaiPhieu: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_get_ticket_status', data),
    getDanhSachTrangHuongGiao: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_get_delivery_unit', data),
    getDanhSachDonViXl: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_dl_nghiepvu', data),
    getSearchChuaGiao: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_khieunai_chuagiao', data),
    getSearchDaGiao: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_khieunai_dagiao', data),
    giaophieu: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_giaophieu_khieunai', data),
    huyphieu: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_huyphieu_khieunai', data),
    capnhapdv: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_capnhat_donvinhan_xlk', data),
    getThamSo: (axios, data) => axios.post('/web-gqkn/GiaoPhieuXulyKhieuNai/gqkn_lay_ds_thamso_md', data),
  }
