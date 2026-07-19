export default{
  getDanhMucChung: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/sp_lay_danhmuc_chung', data),
  getDsSanPham: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/sp_ds_sanpham', data),
  getLichSuSanPham: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/sp_lay_ls_sanpham', data),
  getMaDinhDanhDuKien: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/fn_get_madinhdanh_sp', data),
  pheDuyetCapTct: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/fn_duyet_sanpham_td', data),
  tuChoiDuyetCapTct: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/fn_tuchoi_sanpham_td', data),
  guiPheDuyetCapTct: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/fn_gui_duyet_sanpham_cty', data),
  guiPheDuyetCapTapDoan: async(axios, data) => axios.post('/web-qlspdv/ql_sanpham/fn_gui_duyet_sanpham_td', data)
}
