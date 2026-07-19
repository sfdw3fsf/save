export default {
    getDonViThanhToan: (axios, id) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-don-vi-thanh-toan?pPhanVungId=" + id),
    getLanMoTrongNgay: (axios, data) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-lan-mo-lon-nhat-trong-ngay", { params: data }),
    getDSMoMay: (axios, data) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-mo-may", { params: data }),
    getDSMoMayTheoFile: (axios, data) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-mo-may-tu-file", { params: data }),
    updateLocDanhSach: (axios, data) => axios.post("/web-thuno/api/thu-no/mo-may-theo-danh-sach/loc-danh-sach-thue-bao", data),
    getDSLapHopDongKhacKMM: (axios, data) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-lap-hop-dong-khac-khoa-mo-may", { params: data }),
    updateChuyenDSKhoaMoSangHDKM: (axios, data) => axios.put("/web-thuno/api/thu-no/mo-may-theo-danh-sach/cap-nhat-danh-sach-mo-may", data),
    
    //Read file:
    getDSThamSoMDMATS: (axios, data) => axios.get("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-tham-so-md-mats", { params: data }),
    getDSKhoaMoFileTMPKXL: (axios, data) => axios.post("/web-thuno/api/thu-no/mo-may-theo-danh-sach/lay-danh-sach-khoa-mo-tu-file", data),

    //PopupChiTietNoTB:
    getChiTietNoMaTBMaTT: (axios, data) => axios.get("/web-thuno/api/thu-no/xu-ly-thue-bao/ds-no-theo-matt-matb", { params: data }),
    getTongNoTTTheoChuKy: (axios, data) => axios.get("/web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/lay-tong-no-thanh-toan-theo-ky-hoa-don", { params: data }),
}