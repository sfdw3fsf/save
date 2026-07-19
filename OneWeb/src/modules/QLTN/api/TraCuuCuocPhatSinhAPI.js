export default{    
    getDSDoanhThuCuocPSTheoNhanVien: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/doanh-thu-cuoc-phat-sinh', { params: data }),
    getXuatFileXlsx: async (axios, data) => axios.get('web-thuno/api/thu-no/tra-cuu/xuat-xlsx-ds-doanh-thu-cuoc-phat-sinh', { params: data })
  }
  