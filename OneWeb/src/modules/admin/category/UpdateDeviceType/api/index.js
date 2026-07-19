export default {
  // API
  sp_lay_ds_thietbi_loaitbi: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/sp_lay_ds_thietbi_loaitbi', data),
  sp_lay_ds_nhom_tbi: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/sp_lay_ds_nhom_tbi', data),
  sp_lay_dl_thuoctinh_theo_rowid: (axios, data) => axios.post('web-hopdong/bss_group1/sp_lay_dl_thuoctinh_theo_rowid', data),
  sp_lay_tt_thuoctinh_theo_nhomtbi: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/sp_lay_tt_thuoctinh_theo_nhomtbi', data),
  // {
  //    "p_vattu_id": 10949,
  //         "p_nhomtbi_id": 50
  // }
  fn_frmnhom_tbi_capnhat: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/fn_frmnhom_tbi_capnhat', data),

  // {
  //    "p_nhomtbi_id":"100",
  //    "p_tennhom_tbi":"xxx",
  //    "p_vattu_id":"10",
  //    "p_nguoi_cn":"x",
  //    "p_may_cn":"x",
  //    "p_ip_cn":"x",
  //    "p_themmoi":"1"
  // }
  update_nhom_tbi_thuoctinh: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/update_nhom_tbi_thuoctinh', data),

  // {
  //     "p_nhomtbi_id": 1,
  //     "p_ttvt_id": 1,
  //     "p_gttt_id": 1,
  //     "p_kieutt_id": 1,
  //     "p_thuoctinh_id": 1,
  //     "p_giatri": 1,
  //     "p_nhom_tbi": 1
  // }
  fn_frmnhom_tbi_delete: (axios, data) => axios.post('web-ccdv/capnhat_loai_thietbi/fn_frmnhom_tbi_delete', data)
  // {
  //    "p_nhomtbi_id":523400
  // }
}
