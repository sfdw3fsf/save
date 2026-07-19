export default {
  sp_tt_nguoidung: async (axios, data, header) => axios.get(`/web-bancheo/hoan_cong_megawan_net/sp_tt_nguoidung?phanvung_id=${data.phanvung_id}&ma_nd=${data.ma_nd}`, data, header),
  sp_tt_nhanvien_2: async (axios, data, header) => axios.get(`/web-thicong/khaibao_dichvu_net_mgw/sp_tt_nhanvien_2?vnhanvien_id=${data.vnhanvien_id}`, data, header),
  insert_tientrinh_tc: async (axios, data, header) => axios.post('/web-bancheo/hoan_cong_megawan_net/insert_tientrinh_tc', data, header),
  update_hdtb_tientrinh_tc: async (axios, data, header) => axios.post('/web-bancheo/hoan_cong_megawan_net/update_hdtb_tientrinh_tc', data, header),
  update_tientrinh_tc: async (axios, data, header) => axios.post('/web-bancheo/hoan_cong_megawan_net/update_tientrinh_tc', data, header),
  sp_khdn_capnhat_tientrinh_tc: async (axios, data) => axios.post('/web-thicong/giaoviec-vip/sp_khdn_capnhat_tientrinh_tc', data)
}