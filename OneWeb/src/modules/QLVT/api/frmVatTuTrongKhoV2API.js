export default {

    getDSVatTuTonKhoV2: (axios, data) => axios.post("/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho-v2",data),
    layDSKho: (axios, nhanVienId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${nhanVienId}`)

};
