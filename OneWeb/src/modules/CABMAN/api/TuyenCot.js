export default function (apiCaller) {
    return {
        layDsTrungTamVienThong: () => apiCaller.get('web-cabman/donvi/ds-ttvt-theo-nhanvien'),
        layDsToKyThuat: (donvi_id) => apiCaller.get(`web-cabman/donvi/ds-to-kythuat-theo-nhanvien/${donvi_id}`),
        layDsTuyenCotTheoDonVi: (donvi_id) => apiCaller.get(`web-cabman/TuyenCot/DanhSachTuyenCotTheoDonVi?donViId=${donvi_id}`),
        layDsCotVaToQuanLyTheoTuyen: (data) => apiCaller.get(`web-cabman/TuyenCot/DsCotVaToQuanLyTheoTuyen?ttvtId=${data.ttvtId}&tuyenCotId=${data.tuyenCotId}`),
        themTuyenCot: data => apiCaller.post('web-cabman/TuyenCot/ThemTuyenCot', data),
        capNhatTuyenCot: data => apiCaller.post('web-cabman/TuyenCot/CapNhatTuyenCot', data),
        xoaTuyenCot: data => apiCaller.post('web-cabman/TuyenCot/XoaTuyenCot', data),
    }
}
