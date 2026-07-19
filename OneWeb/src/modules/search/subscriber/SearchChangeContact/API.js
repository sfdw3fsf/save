export default {
    getDSDVVT: axios =>axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT"),
    getKieuBD: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_KIEU_BD', data),
    lay_ds_biendong_danhba: async (axios, data) => axios.post('/web-tracuu/tracuu_thaydoithongtin_db/lay_ds_biendong_danhba', data),
     
}
