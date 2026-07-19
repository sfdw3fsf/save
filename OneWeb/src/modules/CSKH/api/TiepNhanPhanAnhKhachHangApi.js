import axios from "axios";

export default {
    getDsNganhNghe: (axios) => axios.get('/web-cskh/api/danh-muc/nganh-nghe'),
    getDsLoaiKH: (axios, data) => axios.get('/web-cskh/api/danh-muc/loai-khach-hang', {params: data}),
    getDsLoaiKHLon: (axios, data) => axios.get('/web-cskh/api/danh-muc/loai-khach-hang-lon', {params: data}),
    getDsHinhThucKN: (axios, data) => axios.get('/web-cskh/api/danh-muc/hinh-thuc-khieu-nai', {params: data}),
    getDsNoiDungPA: (axios, data) => axios.get('/web-cskh/api/danh-muc/chu-de-khieu-nai', {params: data}),
    getDsPAMoiTiepNhanTheoNV: (axios, data) => axios.get('/web-cskh/api/tiep-nhan-phan-anh/ds-theo-nv', {params: data}),
    getDsPAKhachHangTheoNV: (axios, data) => axios.get('/web-cskh/api/tiep-nhan-phan-anh/lich-su-theo-nv', {params: data}),
    capNhapPA: (axios, data) => axios.post('/web-cskh/api/tiep-nhan-phan-anh/cap-nhat', data),
    khoaPhieu: (axios, data) => axios.post('/web-cskh/api/tiep-nhan-phan-anh/khoa-phieu', data),
    xoaPA: (axios, data) => axios.delete('/web-cskh/api/tiep-nhan-phan-anh/xoa', {params: data}),
}