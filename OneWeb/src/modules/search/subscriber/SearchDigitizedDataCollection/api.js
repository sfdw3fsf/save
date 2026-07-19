export default {
  lay_nguon_dl_doisoat_sohoa_thucuoc: async (axios, data) => axios.post('/web-tracuu/tracuu-sohoathucuoc/lay_nguon_dl_doisoat_sohoa_thucuoc', data),
  lay_dl_doisoat_sohoa_thucuoc: async (axios, data) => axios.post('/web-hopdong/tracuu-sohoathucuoc/lay_dl_doisoat_sohoa_thucuoc', data)
}
