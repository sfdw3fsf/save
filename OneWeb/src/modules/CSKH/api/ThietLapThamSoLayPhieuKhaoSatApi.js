export default {
    getDsThietLapThoiGian: (axios, data) => axios.get('/web-cskh/api/thoi-gian-lay-phieu/', {params: data}),
    capNhapThietLapThoiGian: (axios, data) => axios.post('/web-cskh/api/thoi-gian-lay-phieu', data),
    xoaThietLapThoiGian: (axios, data) => axios.post('/web-cskh/api/thoi-gian-lay-phieu/xoa', data),
}
