export default{
    
    ds_loai_nh: async (axios, id) => axios.post('/web-quantri/NganHang/sp_ds_loai_nh', null),
    
    layds_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/sp_layds_nganhang', null),

    laydulieu_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/sp_laydulieu_nganhang', null),

    capnhat_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/fn_capnhat_nganhang', data),

    xoa_nganhang: async (axios, data) => axios.post('/web-quantri/NganHang/fn_xoa_nganhang', data),

    runsql: async (axios, data) => axios.post('/web-danhba/huonggiao/sp_huonggiao_runsql', data),

    sp_lay_ma_nh: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_lay_ma_nh', data),

    sp_lay_ten_nh: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_lay_ten_nh', data),

    sp_lay_ma_nh_v2: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_lay_ma_nh_v2', data),

    sp_lay_ten_nh_v2: async (axios, data) => axios.post('/web-hopdong/laydulieu/sp_lay_ten_nh_v2', data),
    
    ts_dm_nganhang_xoa: async (axios, data) => axios.post('/ccbs/executor/ts_dm_nganhang_xoa', data),
    ts_dm_nganhang_capnhat: async (axios, data) => axios.post('/ccbs/danhMuc/ts_dm_nganhang_capnhat', data),

  }
  