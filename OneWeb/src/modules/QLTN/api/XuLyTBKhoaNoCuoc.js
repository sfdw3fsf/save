export default {
    getDanhMucDoiTuong: async (axios) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/danh-muc/DOITUONG'),
    getDsTrangThaiTT: async (axios, data) => axios.get('/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhom-ly-do-tra-truoc',{params:data}),
    getDsKieuLDKhoaMay: async (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-kieu-lap-dat-khoa-may-thu-hoi',{params:data}),
    getDsXlKhoaMayThuHoi: async (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-xu-ly-khoa-may-thu-hoi',{params:data}),
    getDsChiTietNo: async (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/chi-tiet-no-theo-khoan-muc',{params:data}),
    capNhatPhieuXLKhoaMay: async (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/cap-nhat-phieu-xl-khoa-may', data),
    traCuuNoTB: async (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/tra-cu-no/thue-bao/' + data),
}
