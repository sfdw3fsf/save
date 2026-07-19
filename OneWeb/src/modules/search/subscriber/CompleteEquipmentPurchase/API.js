export default{
    fn_hienthidanhsachhdtb_frmhoancongmuatbi: async (axios, data) => axios.post('/web-ccdv/hoancong_mua_thietbi/fn_hienthidanhsachhdtb_frmhoancongmuatbi', data),
    fn_hienthidanhsachdtb_phieu_frmhoancongmuatbi: async (axios, data) => axios.post('/web-ccdv/hoancong_mua_thietbi/fn_hienthidanhsachdtb_phieu_frmhoancongmuatbi', data),
    fn_hoancong_hd_frmhoancongmuatbi: async (axios, data) => axios.post('/web-ccdv/hoancong_mua_thietbi/fn_hoancong_hd_frmhoancongmuatbi', data),
}