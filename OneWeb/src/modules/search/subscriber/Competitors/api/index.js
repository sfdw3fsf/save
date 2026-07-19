export default {
  // API

  //
  sp_lay_trangthai_doithu_canhtranh: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_lay_trangthai_doithu_canhtranh', { params }),
  sp_lay_ds_doithu_canhtranh: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_lay_ds_doithu_canhtranh', { params }),
  sp_ds_nguoidung_quantri: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_ds_nguoidung_quantri', { params }),
  sp_lay_ds_khuvuc_treeview: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_lay_ds_khuvuc_treeview', { params }),
  sp_lay_ds_khuvuc_nhanvien_id_treeview: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_lay_ds_khuvuc_nhanvien_id_treeview', { params }),
  sp_lay_ds_chitiet_doithu_canhtranh: (axios, params) => axios.get('web-ccdv/doithu-canhtranh/sp_lay_ds_chitiet_doithu_canhtranh', { params }),

  capnhat_ct_khuvuc: (axios, data) => axios.post('web-ccdv/doithu-canhtranh/capnhat_ct_khuvuc', data)
}
