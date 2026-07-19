export default {
  getDichVuList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT"),

  getLoaiHinhList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"),

  getKieuYeuCau: (axios, data) =>
    axios.get(`/web-hopdong/danhmuc/ds_kieu_ld/${data.loaiHd}/${data.loaiTb}`),

  getKieuYeuCauCoLoaiTB: (axios, data) =>
    axios.get(`/web-hopdong/danhmuc/ds_kieu_ld/${data.loaiHd}/${data.loaiTb}`),

  getKieuYeuCauKhongCoLoaiTB: (axios, data) =>
    axios.get(`/web-hopdong/danhmuc/ds_kieu_ld/${data.loaiHd}`),

  getDVTinhCuoc: (axios, data) =>
    axios.post(
      "/web-quantri/donvi-loai-khachhang/sp_dvi_loaikh_layds_dvi",
      data
    ),

  getTocDo: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/tocdo_adsl_v2", data),

  getMucCuoc: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_MUCCUOC"),

  getLoaiThietBi: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/sp_lay_tbdc_theo_dichvu", data),

  getBras: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/bras_v2", data),

  getDslam: (axios, data) =>
    axios.get(
      `/web-hopdong/thaydoiloaihinhtb/lay_ds_dslam?brasId=${data.brasId}&kieu=${data.kieu}`
    ),

  getInformationHopDongThueBaoGP: (axios, data) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=${data}`),

  checkMaSoThueBaoKhoSo: (axios, data) =>
    axios.get(
      `/web-tracuu/danhba/kiemtra_matb_khoso?maTb=${data.maTb}&dichvuvtId=${data.dichvuvtId}`
    ),

  checkMaSoThueBao: (axios, data) =>
    axios.post("/web-hopdong/hopdong/kiemtra_matb", data),

  getInformationBySoMayAcc: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb", data),

  getDanhSachHopDongTheoMaGiaoDich: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_dshd_theo_ma_gd", data),

  ghiLaiHoSoLoaiHinhThueBao: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/fn_fm_hthstdlh_capnhat", data),

  hoanThienHoSoLoaiHinhThueBao: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/fn_fm_hthstdlh_hoanthien", data),

  // lấy thông tin Khách hàng khi enter mã giao dịch
  api_LAY_DSHD_THEO_MA_GD: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_dshd_theo_ma_gd", data),

  // lấy thông tin thuê bao từ thông tin KH
  api_LAY_DS_HDTB_THEO_HDKH_TTHD: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_ds_hdtb_theo_hdkh_tthd", data),

  // LAY_DS_HDTB_HTHS
  api_LAY_DS_HDTB_HTHS: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/lay_ds_hdtb_hths", data),

  // lấy danh sách loại hình( loại thuê bao) theo dịch vụ
  api_HT_LoaiHinh_TB_Combobox: (axios, dichvuvt_id) =>
    axios.get(`web-hopdong/danhmuc/ds_loaitb/${dichvuvt_id}`),

  // lấy danh sách kiểu yêu cầu theo dịch vụ và loại hình - khi ko có data loại hình
  api_HT_Kieu_LD_Combobox_No_LoaiTb_Id: (axios, loaihd_id) =>
    axios.get(`web-hopdong/danhmuc/ds_kieu_ld/${loaihd_id}`),

  // lấy danh sách kiểu yêu cầu theo dịch vụ và loại hình - khi có data loại hình
  api_HT_Kieu_LD_Combobox_Have_LoaiTb_Id: (axios, data) =>
    axios.get(
      `web-hopdong/danhmuc/ds_kieu_ld/${data.loaihd_id}/${data.loaitb_id}`
    ),

  // list loại kênh
  api_HT_Loai_Kenh_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_LOAI_KENH"),

  // list thời hạn
  api_HT_ThoiHan_MegaWan_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_THOIHAN"),

  // list modem
  api_HT_Loai_Modem_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_LOAI_MODEM"),

  // tốc độ kênh
  api_HT_Tocdo_Kenh_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_TOCDO_KENH"),

  // trang bị
  api_HT_TrangBi_MegaWan_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_TRANGBI"),

  // chủ quản
  api_HT_ChuQuan_MegaWan_Combobox: (axios, data) =>
    axios.get("web-quantri/danhmuc-chung/CSS_CHUQUAN"),

  // LAY_DS_HDTB_ADSL_THEO_HDTB_ID
  apiLAY_DS_HDTB_ADSL_THEO_HDTB_ID: (axios, hdtbId) =>
    axios.get(
      `web-hopdong/hopdong/lay_ds_hopdong_thuebao_adsl?hdtbId=${hdtbId}`
    ),

  // lay_ds_hdtb_GP_theo_hdtb_id
  api_lay_ds_hdtb_GP_theo_hdtb_id: (axios, hdtbId) =>
    axios.get(`web-hopdong/hopdong/lay_ds_hopdong_thuebao_gp?hdtbId=${hdtbId}`),

  // enter số máy
  apiLAY_DS_HOPDONG_THEO_MA_TB: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_ds_hopdong_theo_matb", data),

  //LAY_DS_HDTB_MGWAN_THEO_HDTB_ID
  apiLAY_DS_HDTB_MGWAN_THEO_HDTB_ID: (axios, hdtbId) =>
    axios.get(
      `web-hopdong/hopdong/lay_ds_hopdong_thuebao_mgwan?hdtbId=${hdtbId}`
    ),

  apiHT_ROUTER_VTN_COMBOBOX: (axios, data) =>
    axios.post("/web-hopdong/thaydoiloaihinhtb/pe_v2", data)
};
