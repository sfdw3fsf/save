export default{

  thongtin_nguoidung2: async (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2',data),
  map_id: async (axios, data) => axios.post('/web-quantri/thamso/map_id',data),
  sp_lay_luong_thaotac: async (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_lay_luong_thaotac`,data),
  lay_ct_thaotac_control: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ct_thaotac_control', data, header),
  lay_ds_kieu_ld: async (axios, data,header) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_kieu_ld', data, header),
  lay_huonggiao_theo_quytrinh: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/sp_lay_huonggiao_theo_quytrinh', data, header),
  sp_lay_ds_quytrinh_v2: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2', data, header),
  lay_ds_diachi_theo_hdtbid: async (axios, data,header) => axios.get('/web-thicong/thuebao/lay_ds_diachi_theo_hdtbid', data, header),
  sp_capnhattoadohdtb: async (axios, data,header) => axios.post('/web-thicong/hoancongmegawan/sp_capnhattoadohdtb', data, header),
  lay_ds_dangky_dvgt: async (axios, data,header) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_dangky_dvgt', data, header),
  post_get_keys : async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys',data),

  //HRM
  get_info_by_email: async (axios, data,header) => axios.get('/tichhop/hrm/hrm/employees/get_info_nhanvien_by_email', data, header),
  lay_huonggiao_theo_luong_id: async (axios, data,header) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', data, header),


}
