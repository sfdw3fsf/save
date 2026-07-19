export default{
    getLoaiBienLai: (axios, data) => axios.get('/web-quantri/capnhat-loaibienlai/sp_dm_kieu_bienlai', data),
    getDanhSachBienLai: (axios, data) => axios.get('/web-quantri/capnhat-loaibienlai/sp_ds_loai_bienlai', data),
    ThemMoi_CapNhat: (axios, data) => axios.post('/web-quantri/capnhat-loaibienlai/fn_capnhat_loaibienlai', data),
    Xoa: (axios, data) => axios.post('/web-quantri/capnhat-loaibienlai/fn_xoa_loaibienlai', data),

    
  }