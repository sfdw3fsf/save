export default{
    get_dichvu_vienthong: async (axios, data) => axios.get('https://api-onebss-dev.vnptit3.vn/web-hopdong/danhmuc/dichvu_vienthong', data),
    get_kieu_taikhoan: async (axios, data) => axios.get('https://api-onebss-dev.vnptit3.vn/web-quantri/danhmuc/kieu_taikhoan/', data),
    getNhanVienTheoLoai: async (axios, id) => axios.get('/api/thu-no/common/nhan-vien/loai-nhan-vien/' + id),
    
  }
  