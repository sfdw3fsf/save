export default {
    getQuyTrinhBHTB: (axios, data) => axios.get('/web-qlvt/api/mo-hinh-thiet-bi-bao-hanh/quy-trinh-bao-hanh'),
    getDSLuongPhieuTheoQT: (axios, data) => axios.get(`/web-qlvt/api/mo-hinh-thiet-bi-bao-hanh/luong-phieu-theo-quy-trinh/${data}`),
    getDSBaoHanhThietBiV2: (axios, data) => axios.post('/web-qlvt/api/mo-hinh-thiet-bi-bao-hanh/ds-bao-hanh-thiet-bi-v2', data),
    getDSLine: (axios, data) => axios.get(`/web-qlvt/api/mo-hinh-thiet-bi-bao-hanh/danh-sach-line/${data}`),
    getDSPic: (axios, data) => axios.get(`/web-qlvt/api/mo-hinh-thiet-bi-bao-hanh/danh-sach-pic/${data}`),
  }