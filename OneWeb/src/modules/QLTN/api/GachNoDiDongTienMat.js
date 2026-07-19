export default {
    getDanhSachThuQuy: (axios, data) => axios.get('/web-thuno/api/thu-no/common/nhan-vien-thu-ngan/nguoi-dung-hien-tai'),
    getDanhSachUserNeo: (axios, data) => axios.get('/web-thuno/api/thu-no/common/lay-ds-user-neo'),
    getDanhSachNhomCTV: (axios, data) => axios.get('/web-thuno/api/thu-no/common/lay-ds-nhom-nhan-vien/' + data),
    getDanhSachDonViCTV: (axios, data) => axios.get('/web-thuno/api/thu-no/common/don-vi/loai-don-vi',{params:data}),
    getDanhSachNhanVienCTV: (axios, data) => axios.get('/web-thuno/api/thu-no/common/ds-nhan-vien/don-vi-loai-dv',{params:data}),
    getDanhSachPhieuThanhToan: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/lay-ds-phieu-thanh-toan-vinaphone',{params:data}),
    gachNoDongBo: (axios, data) => axios.put('/web-thuno/api/quan-ly-thu-no/dong-bo-ccbs-sang-dhsx', data),
    getDanhSachThueBao: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-thue-bao-tt-vinaphone',{params:data}),
    getDanhSachPhieuTTDiDong: (axios, data) => axios.get('/web-thuno/api/quan-ly-thu-no/ds-phieu-tt-di-dong',{params:data}),
    getDsThongTinTT: (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/thong-tin-thanh-toan/ma-thue-bao-va-ky-cuoc',{params:data}),
}
