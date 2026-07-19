export default {

    ds_vattu_ton_kho_v2: (axios, data) => axios.post("/web-qlvt/api/tra-cuu-ton-kho-user/ds-vattu-ton-kho-v2",data),
    ds_kho_nhan_vien: (axios, nhanVienId) => axios.get(`/web-qlvt/api/tra-cuu-ton-kho-user/ds-kho-nhan-vien/${nhanVienId}`)

};
