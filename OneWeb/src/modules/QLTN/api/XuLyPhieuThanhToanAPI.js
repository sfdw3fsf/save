export default {
    deletePhieuThanhToan: async (axios, data) => axios.delete('web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/xoa-phieu-thanh-toan-gach-no', { data: data }),
    //deletePhieuThanhToanMK: async (axios, data) => axios.delete('web-thuno/api/quan-ly-thu-no/gach-no-tien-mat/sp-xoa-phieu-thanh-toan-mk', { data: data }),
    updateNgayThanhToan: async (axios, data) => axios.put('web-thuno/api/thu-no/quan-ly-thu-no/gach-no-tien-mat/doi-ngay-thanh-toan', data),
    updateNgayThanhToanMK: async (axios, data) => axios.put('web-thuno/api/thu-no/sp-sua-ngay-thanh-toan-mk', data),
    updateThongTinThanhToan: async (axios, data) => axios.put('web-thuno/api/quan-ly-thu-no/thong-tin-thanh-toan', data),
    updateGhiChuPhieuThanhToan: async (axios, data) => axios.put('web-thuno/api/thu-no/tra-cuu/cap-nhat-ghi-chu/phieu-tra', data),
    updateThongTinThanhToanV2: async (axios, data) => axios.put('web-thuno/api/quan-ly-thu-no/thong-tin-thanh-toan-v2', data)
}
