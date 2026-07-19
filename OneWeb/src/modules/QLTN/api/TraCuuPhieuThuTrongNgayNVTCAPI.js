export default{
    getDSPhieuThu: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/lan-tra', { params: data}),
    getDSNVTC: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/tong-tien/nhan-vien-thu-cuoc', { params: data}),
    getDSHinhThucTT: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/phieu-thanh-toan/luot-tra', { params: data})    
  }
  