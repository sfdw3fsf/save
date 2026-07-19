export default{
    getDSPhieuThanhToan: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-danh-sach-phieu-thanh-toan', { params: data}),
    getDSThuNgan: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-tong-tien-theo-thu-ngan', { params: data}),
    getDSHinhThucTT: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-tong-tien-httt-theo-thu-ngan', { params: data}),
    getDSPhieuThu: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/lan-tra', { params: data}),
    getDSNVTC: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/tong-tien/nhan-vien-thu-cuoc', { params: data}),
    getDSHinhThucTT2: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/luot-tra', { params: data}),
    getQuyenGachNo: async (axios) => axios.get('web-thuno/api/quan-ly-thu-no/quyen-gach-no'),
    postKiemTraMatKhau: async (axios, data) => axios.post('/quantri/user/check_user_password', data),
    updateDongBoCCBS: async (axios, data) => axios.put('web-thuno/api/thu-no/dong-bo/ccbs', data),
    updateDongBoHDDT: async (axios, data) => axios.put('web-thuno/api/thu-no/dong-bo/hoa-don-dien-tu', data)
  }
  