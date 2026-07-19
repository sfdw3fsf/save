export default {
  lay_ds_db_hdtb_theo_khid_magd: async (axios, khachhangid, magd) => axios.post(`/web-cabman/ban-do-mang-cap/chuyendich/lay_ds_db_hdtb_theo_khid_magd?khachhang_id=${khachhangid}&ma_gd=${magd}`)
}
