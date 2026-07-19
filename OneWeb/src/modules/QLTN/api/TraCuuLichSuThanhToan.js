export default{
  getTraCuuLichSuThanhToan: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu/danh-sach-no/lich-su-thanh-toan', { params : data})
}
