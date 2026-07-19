export default{
  get_loai_hopdong: async (axios) => axios.get("/web-quantri/danhmuc-chung/CSS_LOAI_HD"),
  get_dichvu_vt: async (axios, data) => axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT", data),
  sp_ds_nguoi_giaoviec: async (axios, data) => axios.post("/web-thicong/khoiphuccong/sp_ds_nguoi_giaoviec", data),
  sp_ds_phieu: async (axios, data) => axios.post("/web-thicong/khoiphuccong/sp_ds_phieu", data),
  lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.post("/web-thicong/hoinghi_truyenhinh/lay_ds_nhanvien_theo_phieu_id", data),
  sp_ds_thongtin_hopdong_khachhang: (axios, data) => axios.post("/web-thicong/khoiphuccong/sp_ds_thongtin_hopdong_khachhang", data),
  capnhat_phieu_thuhoi: (axios, data) => axios.post("/web-thicong/khoiphuccong/capnhat_phieu_thuhoi", data),
  capnhat_tt_phieugiao: (axios, data) => axios.post("/web-thicong/khoiphuccong/capnhat_tt_phieugiao", data),
  sp_update_noidung_thuchien: (axios, data) => axios.post("/web-thicong/khoiphuccong/sp_update_noidung_thuchien", data),
  thongtin_nguoidung: async (axios) => axios.post("/quantri/user/thongtin_nguoidung2", {})

}
