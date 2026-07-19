export default {
    getDSDichVuVT: (axios, data) => axios.get('/web-thuno/api/thu-no/common/dich-vu-vien-thong', { params: data }),
    getDSDieuKien: (axios, data) => axios.get('/web-thuno/api/thu-no/common/kieu-tim-kiem/nhom/' + data),
    getDSDonVi: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ds-don-vi-thu-no', {params : data}),
    getDSTuyenThuByDonVi: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ds-tuyen-thu/don-vi/' + data),
    getDSDanhBaThanhToan: (axios, data,params) => axios.post('/web-thuno/api/thu-no/tra-cuu-danh-ba/ds-danh-ba-thanh-toan-theo-tieu-chi', data,{params:params}),
    capNhatDoiSoat: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/doi-soat-tk-ngan-hang',data),
    doiSoatNganHang: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/doi-soat-ngan-hang',data),
}
