export default{
  getTraCuuLichSuNhacNo: async (axios, data) => axios.get('/web-thuno/api/thu-no/tra-cuu-danh-ba/lich-su-nhac-no-thue-bao', { params : data})
}
