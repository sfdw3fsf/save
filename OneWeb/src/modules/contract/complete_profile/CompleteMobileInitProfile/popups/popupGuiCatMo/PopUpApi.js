
export default {
      
    lay_ds_hdkh_chua_tbdd: async (axios, data,header) => axios.post('/web-ccdv/tienhopdong/lay_ds_hdkh_chua_tbdd', data, header),
    fn_kiemtra_hdkh_co_chuyenmang: async (axios, data,header) => axios.post('/web-ccdv/tienhopdong/fn_kiemtra_hdkh_co_chuyenmang', data, header),
    lay_ds_hdtb_dd_theo_hdkhid: async (axios, data,header) => axios.post('/web-ccdv/tienhopdong/lay_ds_hdtb_dd_theo_hdkhid', data, header),
    sp_capnhat_ngayht: async (axios, data,header) => axios.post('/web-ccdv/tienhopdong/sp_capnhat_ngayht', data, header), 
    fn_tt_hd_thuebao: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao', data, header),  
    fn_tt_hd_khachhang: async (axios, data,header) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_khachhang', data, header), 
    
    lay_tt_chuyenmang_hd_thuebao: async (axios, data,header) => axios.post('/web-hopdong/hopdong/lay_tt_chuyenmang_hd_thuebao', data, header), 
    
}