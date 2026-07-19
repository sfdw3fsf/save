export default{
  siptrungking_dichvu_loaikh: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_loaikh', data),
  siptrungking_dichvu_nganhang: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_nganhang', data),
  siptrungking_dichvu_nguoi_gt: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_nguoi_gt', data),
  siptrungking_dichvu_checktb: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_checktb', data),
  siptrungking_dichvu_nguoi_gt: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_nguoi_gt', data),
  siptrungking_dichvu_themmoi: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_themmoi', data),
  siptrungking_dichvu_search: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_search', data),
  siptrungking_dichvu_huy: (axios, data) => axios.post('/ccbs/executor/siptrungking_dichvu_huy', data),
  
  

  kiemtra_thuebao_lap_hopdong_khac: (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),
  lay_danhba_theo_matb: (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),
  sp_lay_loaikh_id_neo: (axios, data,ma_tb) => axios.get('/web-hopdong/hopdong/sp_lay_loaikh_id_neo?ma_tb=' + ma_tb,data),

  get_keys: (axios, data) => axios.post('/web-ccdv/tien_trinh_bao_hong/get_keys', data),
  sinh_magd: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
  capnhat_giaophieu_ttph: (axios, data) => axios.post('/web-thicong/hths-dangky-multisim/capnhat_giaophieu_ttph', data),
  themmoi_hopdong_siptrunking: (axios, data) => axios.post('/web-thicong/dangky-dv-siptrunk/themmoi_hopdong_siptrunking', data),
  
  thongtin_nguoidung2: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    //getKenhThu: (axios, data) => axios.get('web-quantri/danhmuc-chung/CSS_KENHTHU', data),

    
    
  }