export default{
    
    ds_loai_nh: async (axios, id) => axios.post('/web-quantri/NganHang/sp_ds_loai_nh', null),
    
    layds_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/sp_layds_nganhang', null),

    laydulieu_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/sp_laydulieu_nganhang', null),

    capnhat_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/fn_capnhat_nganhang', data),

    xoa_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/fn_xoa_nganhang', data),

    

  }
  