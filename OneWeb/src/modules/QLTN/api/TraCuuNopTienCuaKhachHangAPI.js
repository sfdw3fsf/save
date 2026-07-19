export default{    
    getDSPhieuTra: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-danh-sach-phieu-tra', { params: data}),
    getDSPhieuTraTheoThuNgan: async (axios, data) => axios.get('web-thuno/api/thu-no/sp-lay-danh-sach-phieu-tra-theo-thu-ngan', { params: data})
  }
