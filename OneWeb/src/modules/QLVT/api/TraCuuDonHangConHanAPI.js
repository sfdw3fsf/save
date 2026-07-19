export default {
    getDSDonVi: (axios, nhanVienId) => axios.get(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-donvi-kho/${nhanVienId}/0`),
    getDSDonHang: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-don-hang`,data),
    getDSVatTuDonHang: (axios, donHangId) => axios.get(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-vattu-donhang/${donHangId}`),
}