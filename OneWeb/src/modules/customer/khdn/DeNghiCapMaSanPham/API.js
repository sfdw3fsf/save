export default{
  sp_lay_danhmuc_chung:async(axios) =>  axios.post('/web-qlspdv/ql_sanpham/sp_lay_danhmuc_chung'),
  sp_ds_sanpham: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/sp_ds_sanpham',data),
  sp_lay_ds_sanpham_cha: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/sp_lay_ds_sanpham_cha',data),
  fn_themmoi_sanpham: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_themmoi_sanpham',data),
  fn_capnhat_sanpham: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_capnhat_sanpham',data),
  sp_lay_ls_sanpham: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/sp_lay_ls_sanpham',data),
  fn_xoa_sanpham: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_xoa_sanpham',data),
  fn_gui_duyet_sanpham_cty: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_gui_duyet_sanpham_cty',data),
  fn_duyet_sanpham_cty: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_duyet_sanpham_cty',data),
  fn_tuchoi_sanpham_cty: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_tuchoi_sanpham_cty',data),
  fn_gui_duyet_sanpham_td: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_gui_duyet_sanpham_td',data),
  fn_duyet_sanpham_td: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_duyet_sanpham_td',data),
  fn_tuchoi_sanpham_td: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_tuchoi_sanpham_td',data),
  sp_check_sanpham_import: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/sp_check_sanpham_import',data),
  fn_them_sanpham_import: async(axios,data) =>  axios.post('/web-qlspdv/ql_sanpham/fn_them_sanpham_import',data),

}
