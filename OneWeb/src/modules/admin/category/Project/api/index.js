export default {
  getChuTruongList: (axios, data) =>
    axios.post(
      "/web-quantri/duan/sp_lay_dulieu_bang_theo_dieukien_phanvung_id",
      data
    ),

  getDanhSachDuAn: (axios, data) =>
    axios.post(
      "/web-quantri/duan/sp_lay_dulieu_bang_theo_dieukien_phanvung_id",
      data
    ),

  getDanhSachDonViDaGan: (axios, data) =>
    axios.post("/web-quantri/duan/fn_lay_danhsach_donvi_duan", data),

  getDanhSachDonViChuaGan: (axios, data) =>
    axios.post("/web-quantri/duan/fn_lay_danhsach_donvi_duan", data),

  saveProject: (axios, data) =>
    axios.post("/web-quantri/duan/fn_capnhat_tt_duan_qlvt", data),

  deleteProject: (axios, data) =>
    axios.post("/web-quantri/duan/fn_capnhat_tt_duan_qlvt", data),

  addDonVi: (axios, data) =>
    axios.post("/web-quantri/duan/fn_capnhat_tt_duan_donvi", data),

  removeDonVi: (axios, data) =>
    axios.post("/web-quantri/duan/fn_capnhat_tt_duan_donvi", data),

  getListProjects:(axios, data)=>
    axios.post(
      "web-hopdong/laydulieu/sp_danhmuc_cabman_duan",
      data
    ),
  getSubjectsList:(axios, data)=>axios.post(
    "web-hopdong/laydulieu/sp_danhmuc_cabman_duan",
    data
  ), 
  getLoai_DonVi: (axios, data)=>axios.post(
    "web-hopdong/laydulieu/sp_tt_loai_dvi",
    data
  )
  
};
