export default {
  fn_lay_thuoctinh_dong_hdtb_cntt: async (axios, data) => axios.get(`/web-thicong/giaoviec-vip/fn_lay_thuoctinh_dong_hdtb_cntt?vphanvung_id=${data.vphanvung_id}&vtinh_id=${data.vtinh_id}&vhdtb_id=${data.vhdtb_id}&voptions="${data.voptions}"`, data),
  sp_capnhat_thuoctinh_dong_hdtb_cntt: async (axios, data) => axios.post('/web-thicong/giaoviec-vip/sp_capnhat_thuoctinh_dong_hdtb_cntt', data),
  SP_LAY_DS_DL_NGHIEPVU: async (axios, data) => axios.post(`/web-hopdong/quanly_khdn_mnp/sp_lay_ds_dl_nghiepvu`, data),
}   
