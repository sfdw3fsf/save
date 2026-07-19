export default{
  getTraCuuLichSuPhanAnh: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/tra-cuu/lich-su-phan-anh?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
  traCuuLichSuPhanAnh: (axios, pageNo, pageSize, data) => axios.post('/web-cskh/api/tra-cuu/lich-su-phan-anh?pageNo=' + pageNo + '&pageSize=' + pageSize, data),
}
