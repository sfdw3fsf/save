export default {
    ds_kyhoadon: async (axios, data) => axios.post('/web-quantri/chu_ky_no/sp_ds_chukyno'),
    // getDSDVVT: axios =>axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT"),
    // getKieuBD: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/QLVT_KIEU_BD', data),
    laythongtin_mk_acc: async (axios, data) => axios.post('/web-hopdong/tracuu_mk_acc/fn_lay_matkhau_my_account', data),
     
}