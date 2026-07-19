export default{
  TraCuu: (axios, data) => axios.get(`/web-tracuu/tbcd/tb_co_dinh?maThueBao=${data.maThueBao}`),
  kiemTraThongTin: async (axios, data) => axios.post(`/tichhop/subman/kiemTraThongTin`, data)
}
