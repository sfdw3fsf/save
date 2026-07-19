import axios from "axios";

export default {
    getDanhSachQuan: (axios) => axios.get('/web-cskh/api/danh-muc/quan-huyen'),
    getDanhSachLoaiKHL: (axios) => axios.get('/web-cskh/api/danh-muc/loai-khach-hang-lon?nhomKhl=2'),
    getDanhSachLoaiKH: (axios) => axios.get('/web-cskh/api/danh-muc/loai-khach-hang'),
    getDanhSachLanTao: (axios, data) => axios.get('/web-cskh/api/tao-danh-sach-khl/lan-tao?kyHoaDon=' + data),
    getDanhSachChuKyNo: (axios) => axios.get('/web-cskh/api/danh-muc/tat-ca-chu-ky-no'),
    getDanhSachKhachHang: (axios, pageNo, pageSize,totalRow, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/lay-ds?pageNum=' + pageNo + '&pageSize=' + pageSize+'&totalRow='+totalRow, data),
    xoaDanhSachLanTao: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/ds-lan-tao/xoa', data),
    chiTietDanhSachLanTao: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/ds-lan-tao/chi-tiet?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
    // xoaDanhSachLanTao: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/ds-lan-tao/xoa-kh-tmp', data),
    getThongTinNoTheoKhachHang: (axios, data) => axios.get('/web-cskh/api/danh-muc/thong-tin-no?khachHangId='+ data.khachHangId +'&tuNgay='+ data.tuNgay +'&denNgay=' + data.denNgay),
    luuThongTinKhachHangLe: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/ghi-kh-le', data),
    taoDanhSachKHL: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/tao', data),
    ghiDanhSachKHL: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/ghi', data),
    xoaDanhSachKHL: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/xoa', data),
    docFile: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/doc-file', data),
    taoDanhSachTuFile: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/tao-tu-file', data)
}