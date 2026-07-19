export default {
    getDanhSachThamSoMD: async (axios, id) => axios.get('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-danh-sach-tham-so-mac-dinh-theo-kieuid?pKieuId=' + id),
    getDanhSachKhongKhoaMo: async (axios, data) => axios.get('/web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-danh-sach-khong-khoa-mo', { params: data }),
    getTTThueBaoTheoMaTB: async (axios, matb) => axios.get('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-thong-tin-thue-bao-theo-ma-thue-bao?pMaTB=' + matb),
    getTTNoThueBao: async (axios, data) => axios.get('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-thong-tin-no-thue-bao', { params: data }),
    deleteDanhSachKhongXuLy: async (axios, data) => axios.delete('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/xoa-danh-sach-khong-xu-ly', { data: data }),
    postDanhSachKhongXuLy: async (axios, data) => axios.post('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/them-danh-sach-khong-xu-ly', data),
    updateDanhSachKhongXuLy: async (axios, data) => axios.put('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/cap-nhat-danh-sach-khong-xu-ly', data),
    getDanhSachRDSIDKhongXuLy: async (axios, data) => axios.get('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-danh-sach-khong-xu-ly-rdsId', { params: data }),
    
    //Doc file
    getKey: async (axios, key) => axios.get('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/lay-key?pKeyName=' + key),
    postLocDanhSachTuFileThem: async (axios, data) => axios.post('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/loc-danh-sach-khong-xu-ly-tu-file-them', data),
    postDanhSachKhongXuLyTuFileThem: async (axios, data) => axios.post('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/them-danh-sach-khong-xu-ly', data),

    //Xoa file
    postLocDanhSachTuFileXoa: async (axios, data) => axios.post('web-thuno/api/thu-no/danh-sach-thue-bao-khong-khoa-mo/loc-danh-sach-khong-xu-ly-tu-file-xoa', data),
    deleteDSKhachHangDacBiet: async (axios, data) => axios.delete('web-thuno/api/thu-no/xu-ly-thue-bao/ds-khach-hang-dac-biet', { data: data })
}