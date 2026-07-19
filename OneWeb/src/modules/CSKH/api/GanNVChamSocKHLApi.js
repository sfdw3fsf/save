import axios from "axios";

export default {
    getDsChuongTrinhCSKH: (axios, data) => axios.get('/web-cskh/api/gan-nhan-vien-cs-khl/danh-sach-ct', {params: data}),
    getDsQuanHuyen: (axios) => axios.get('/web-cskh/api/danh-muc/quan-huyen'),
    getDsDonVi: (axios, data) => axios.post('/web-cskh/api/don-vi/phong', data),
    getDsNhanVien: (axios, data) => axios.get('/web-cskh/api/nhan-vien-khao-sat', {params: data}),
    getDsKHL: (axios, data) => axios.post('/web-cskh/api/gan-nhan-vien-cs-khl/danh-sach-khl?pageNum=' + data.pageNum + '&pageSize=' + data.pageSize+ '&totalRow=' + data.totalRow, data),
    ganNVChamSocKHL: (axios, data) => axios.post('/web-cskh/api/gan-nhan-vien-cs-khl/cap-nhat', data),
    getDsDVKHL: (axios, data) => axios.post('/web-cskh/api/gan-don-vi-cs-khl/ds-khl?pageNum=' + data.pageNum + '&pageSize=' + data.pageSize+ '&totalRow=' + data.totalRow, data),
    ganDVChamSocKHL: (axios, data) => axios.post('/web-cskh/api/gan-don-vi-cs-khl/cap-nhat', data),
    uploadFileTmp: (axios, data) => axios.post('/web-cskh/api/tao-danh-sach-khl/doc-file', data),
    ganNVChamSocKHLTuFile: (axios, data) => axios.post('/web-cskh/api/gan-nhan-vien-cs-khl/gan-tu-file', data),
    ganDVChamSocKHLTuFile: (axios, data) => axios.post('/web-cskh/api/gan-don-vi-cs-khl/gan-tu-file', data),
}