export default function (RequestContext) {
  return {
    getDSKhieuNaiTheoMaKN: (axios, data) => axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_ds_khieunai_theoma?maKN=' + data),
    getDSDichVuVT: (axios, data) => axios.post('/web-gqkn/TraCuuPhieuKN/sp_lay_ds_dichvu_vt'),
    getDSLoaiHinhTB: (axios, data) => axios.post('/web-gqkn/xu-ly-khieu-nai/getDsLoaiHinhTB', { dichVuVTID: data }),
    getDanhBa: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-ba/ma-thue-bao?maTB=' + data),
    getTienTrinhKhieuNai: (axios, data) => axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhssach_tientrinh_khieunai?khieuNaiID=' + data),
    getGiamCuocKhieuNai: (axios, data) => axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhsach_giamcuoc_khieunai?khieuNaiID=' + data),
    getDSKhieuNaiTheoTrangThai: (axios, data) => axios.get('/web-gqkn/TraCuuKhieuNaiChung/sp_lay_danhsach_khieunai_theo_trangthai?TTKNID=' + data)
  }
}
