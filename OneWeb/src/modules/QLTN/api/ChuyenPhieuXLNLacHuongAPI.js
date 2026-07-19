import qs from "qs"
export default {
    getDanhSachPhuongTheoQuan: async (axios, data) => axios.get('web-thuno/api/thu-no/danh-muc/lay-danh-sach-phuong-theo-quan', { params: data}),
    getDanhSachPhoApKhuTheoPhuong: async (axios, data) => axios.get('web-thuno/api/thu-no/danh-muc/lay-danh-sach-pho-ap-khu-theo-phuong', { params: data}),
    getDanhSachDacDiem: async (axios, data) => axios.get('web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-muc-dac-diem', { params: data}),
    getDanhSachNhanVienXLNTheoQuan: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-no/nhan-vien-xu-ly-no-theo-quan', { params: data }),
    // getDanhBaNVTNTheoDiaDanh: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu-danh-ba/nhan-vien-thu-no/dia-danh', { params: data }),
    getDSNhanVienThuCuoc: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-thu-cuoc', { params: data }),
    getDanhSachXLNChuyenPhieu: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-no/chuyen-phieu', { params: data }),
    getDanhSachXLNChuyenPhieuTheoFile: async (axios, data) => axios.get('web-thuno/api/thu-no/xu-ly-no/lay-du-lieu-xln-chuyen-phieu-theo-file', { params: data, paramsSerializer: params => {return qs.stringify(params) } }),
    postKiemTraChuyenPhieuXLN: async (axios, data) => axios.post('web-thuno/api/thu-no/xu-ly-no/kiem-tra-chuyen-phieu-xln', data),
    updatePhieuXLNLacHuong: async (axios, data) => axios.put('web-thuno/api/thu-no/xu-ly-no/chuyen-phieu-xln', data),        
    getNhanVienIDTheoMaNV: async (axios, data) => axios.get('web-thuno/api/thu-no/common/nhan-vien-id/ma-nhan-vien/'+ data),
    
}