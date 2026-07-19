export default{
    
    getDanhSachNVQL: (axios, data) => axios.post('/web-danhba/theodoi_tb_tratruoc/lay_nvql_theo_donvi_id', data),
    getDanhSachDonVi: (axios, data) => axios.post('/web-hopdong/xac_nhan_giahan_datcoc_tt/lay_donvi_gan_nhomcno', data),
    getDanhSachNVTC: (axios, data) => axios.post('/web-danhba/theodoi_tb_tratruoc/lay_nvtc_theo_nvkd', data),
    getDanhSach: (axios, data) => axios.post('/web-danhba/theodoi_tb_tratruoc/lay_ds_datcoc_theo_thoihan_v2', data),
    sendSMS: (axios, data) => axios.post('/tichhop/sms/send', data),
    getThamSoMD: (axios, data) => axios.get('/web-quantri/danhmuc/ds_thamso_md/0', data),
    getNguoiNhan: (axios, data) => axios.post('/web-danhba/theodoi_tb_tratruoc/lay_ds_nhanvien_theo_dv', data),
    CapNhatDL: (axios, data) => axios.post('/web-danhba/theodoi_tb_tratruoc/sp_ghtratruoc_capnhat_giaophieu_datcoc', data)
  }