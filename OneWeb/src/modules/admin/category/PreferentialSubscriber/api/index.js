export default {
  getDichVuVTList: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/sp_lay_dulieu_theo_dieukien_dvvt"),

  getLoaiHinhTBList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"),

  getCongVanList: (axios, data) =>
    axios.post("/web-hopdong/danhmuc/socongvan_uudai"),

  getNguonKMList: (axios, data) =>
    axios.post("/web-hopdong/danhmuc/nguon_khuyen_mai"),

  getDanhSachThueBaoUuDao: (axios, data) =>
    axios.post("/web-hopdong/phattrienthuebao/layds_thuebao_uudai", data),

  saveThueBaoUuDai: (axios, data) =>
    axios.post("/web-quantri/thuebao_uudai/sp_insert_tb_uudai", data),

  updateThueBaoUuDai: (axios, data) =>
    axios.post("/web-quantri/thuebao_uudai/sp_tb_uudai_update", data),

  checkExistThueBaoUuDai: (axios, data) =>
    axios.post("/web-quantri/thuebao_uudai/sp_kiemtra_tb_uudai", data),

  getDanhBa: (axios, data) =>
    axios.post("/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB", data),

  getDsThongTinHopDongTB: (axios, data) =>
    axios.post("/web-hopdong/phattrienthuebao/layds_thongtin_hdtb_dc", data),

  // enter số máy
  apiLAY_DS_HOPDONG_THEO_MA_TB: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_ds_hopdong_theo_matb", data),

  
  traCuuDanhBaTheoMaTB:(axios, data)=>
    axios.post("/web-hopdong/hopdong/lay_danhba_theo_matb", data),
};
