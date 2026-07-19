export default{

  runsql: async (axios, data) => axios.post('/web-danhba/huonggiao/sp_huonggiao_runsql', data),

  sp_lay_khuvuc: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_khuvuc?'+ data, null),
  sp_lay_khoanmuctt: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_khoanmuctt?'+ data, null),
  sp_lay_loaitb: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_loaitb?'+ data, null),

  sp_lay_dv_tinh: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_dv_tinh', null),
  sp_lay_tocdo_v2: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_tocdo_v2', null),
  sp_lay_tocdo: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_tocdo', null),
  sp_lay_loaitb_v2: async (axios, data) => axios.get('/web-hopdong/laydulieu/sp_lay_loaitb_v2?'+ data, null),


  CSS_DICHVU_VT: async (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT'),

    



  lay_ds_banggia: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_banggia', data),
  
  lay_ds_loaihinh: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_loaihinh_theo_banggia', data),
  lay_ds_kieu_ld: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_kieu_ld_theo_banggia', data),
  lay_ds_doituongtb: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_doituongtb_theo_banggia', data),
  lay_ds_trangbi: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_trangbi_theo_banggia', data),
  lay_ds_loaitb: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_lay_ds_loaitb_theo_banggia', data),

  sp_banggia_capnhat: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_banggia_capnhat', data),
  sp_banggia_xoa: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_banggia_xoa', data),

  //popup BangGiaMegawan
  sp_tocdokenh_capnhat: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_tocdokenh_capnhat', data),
  sp_tocdokenh_xoa: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_tocdokenh_xoa', data),

  //popup BGDoiTDMgWan
  lay_ds_tocdo_mgwan: async (axios, data) => axios.post('/web-quantri/bang-gia/lay_ds_tocdo_mgwan', data),
  sp_bgdoimgwan_xoa: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_bgdoimgwan_xoa', data),
  sp_bgdoimgwan_capnhat: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_bgdoimgwan_capnhat', data),

  sp_capnhat_cuoctc_lhtb: async (axios, data) => axios.post('/web-quantri/bang-gia/sp_capnhat_cuoctc_lhtb', data),

      


  }
  