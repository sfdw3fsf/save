export default {
    getDanhSachNhanVien: (axios, data) => axios.post('/web-qlvt/api/hoan-tra-thiet-bi-bao-hanh/danh-sach-nhan-vien', data),
    getDanhSachPhieuBH: (axios, data) => axios.post('/web-qlvt/api/hoan-tra-thiet-bi-bao-hanh/danh-sach-phieu-bh-tra-kh', data),
    getThongTinBaoHanhTB: (axios, data) => axios.post('/web-qlvt/api/tiep-nhan-thiet-bi-bao-hanh/thong-tin-bao-hanh-thiet-bi', data),
    hoanThienBaoHanh: (axios, data) => axios.put('/web-qlvt/api/giao-phieu-bao-hanh/hoan-thien-bh', data),
    getMaToTheoDonVi: (axios, data) => axios.get(`/web-qlvt/api/hoan-tra-thiet-bi-bao-hanh/ma-to-theo-don-vi/${data}`),
  }
  