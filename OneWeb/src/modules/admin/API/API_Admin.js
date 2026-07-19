export default{
    get_ds_nhom_huy: async (axios, data) => axios.get('/web-quantri/hopdong/ds_nhomhuy', data),
    get_ds_lydo_huy: async (axios, data) => axios.get('/web-quantri/hopdong/ds_lydo_huy', data),
    get_ds_phieu_huy: async (axios, id) => axios.get('/web-ccdv/tiepnhanhopdong/ds_huydungthu/'+id, null).finally(function(){
      $('.overlay-common').removeClass('show');}),
    
      capnhatld_huy_hd_dungthu: async (axios, data) => axios.post('/web-ccdv/tiepnhanhopdong/capnhatld_huy_hd_dungthu', data),


    /////////////////PrintHandoverDoc////////
    get_ds_loai_hd: async (axios, data) => axios.get('/web-tracuu/bienban_nt/ds_loai_hd', null),
    get_ds_loai_dichvu_vt: async (axios, data) => axios.get('/web-tracuu/bienban_nt/ds_dichvu', null),
    get_ds_phieu_yc: async (axios, data) => axios.post('/web-tracuu/bienban_nt/tim_kiem', data).finally(function(){
      $('.overlay-common').removeClass('show');}),


    /////////////////ChangeInstallUnit////////
    get_ds_kieu_donvi: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieu_donvi', null),
    get_ds_quan: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_quan', null),
    get_ds_phuong: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_ds_phuongxa', data),
    get_ds_pho: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieu_donvi', null),
    get_ds_ap: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieu_donvi', null),
    get_ds_khu: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieu_donvi', null),
    get_ds_dacdiem: async (axios, data) => axios.get('/web-thicong/danhmuc/lay_danhsach_kieu_donvi', null),

    /////////////////CancelBookingVISAAccount////////
    CancelBookingVISAAccount_login: async (axios, data) => axios.post('/web-hopdong/HuyDatChoAccountVisa/pttb_xac_nhan_quyen', data).finally(function(){
      $('.overlay-common').removeClass('show');}),
    CancelBookingVISAAccount_huy_dc: async (axios, data) => axios.post('/web-hopdong//HuyDatChoAccountVisa/huy_dat_cho_account_visa', data).finally(function(){
      $('.overlay-common').removeClass('show');}),



  }
  