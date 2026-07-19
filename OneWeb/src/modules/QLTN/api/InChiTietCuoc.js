export default {
    inChiTietCuoc: (axios, data) => axios.post('/web-thuno/api/thu-no/in-hoa-don/in-chi-tiet-cuoc-goi-v3', data),
    getDanhSachCuocGoi: (axios, data) => axios.post('/web-thuno/api/thu-no/tra-cuu/chi-tiet-cuoc-goi-v3', data),
}
