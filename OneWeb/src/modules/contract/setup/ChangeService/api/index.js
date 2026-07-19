import axios from 'axios'
export default {
  // danh sách dịch vụ
  getDichVuList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_DICHVU_VT_V3"),

  // danh sách loại hình
  getLoaiHinhList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB"),

  // ds kiểu yêu cầu khi ko có số máy acc
  getKieuYeuCauList: (axios, data) =>
    axios.get("/web-quantri/danhmuc-chung/CSS_KIEU_LD"),

  // ds kiểu yêu cầu khi có số máy acc
  getKieuYeuCauListLD_BC: (axios, data) =>
    axios.post("/web-hopdong/thaydoitocdoadsl/lay_ds_kieu_ld_bc", data),

  // danh sách thuê bao
  getDanhSachThueBao: (axios, data) =>
    axios.post(
      "/web-hopdong/thaydoiloaihinhtb/lay_ds_hopdong_tb_theo_hdtb_id",
      data
    ),
  sp_tt_dichvu_gt: () => axios.get(`/web-hopdong/dk_goi_dadichvu/sp_tt_dichvu_gt?type=1`).then(rs => rs.data.data),
  getDichVuList1: () => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT_V3`).then(rs => rs.data.data),
  // các api cho hàm danh sách dịch vụ gia tăng
  // 008
  getLay_ds_tb_dvgt_theo_tt_ims_v2: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/lay_ds_tb_dvgt_theo_tt_ims", data),

  // 009
  getLAY_DS_THUEBAO_DICHVU_IMS_V2: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_ims", data),

  //010
  getLAY_DS_THUEBAO_DICHVU_LINHVUC_V2: (axios, data) =>
    axios.post(
      "/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_linhvuc",
      data
    ),

  //011
  getLAY_DS_THUEBAO_DICHVU_V2: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_v2", data),

  // 012
  getLay_ds_thuebao_dichvu_v2: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/lay_ds_thuebao_dichvu_v2", data),

  // click so may Acc
  soMayAcc: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_ds_hopdong_theo_matb", data),

  // click ma giao dich
  maGiaoDich: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd", data),

  // lấy danh sách tổng tiền khoản mục
  getLay_DS_TONG_TIEN_KHOAN_MUC: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/sp_lay_ds_tien_khoanmuc", data),

  // lấy danh sách dịch vụ khác
  getLAY_DS_DICH_VU_KHAC: (axios, data) =>
    axios.post("web-hopdong/khoiphucthanhly/lay_ds_dangky_dvk", data),

  // xóa hợp đồng
  deleteHopDong: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/xoa_hdkh_v2", data),

  // kiểm tra hợp đồng bán chéo tc
  kiemTraHDBanCheoTc: (axios, data) =>
    axios.post("/thaydoidichvu/kiemtra_hd_bancheo_tc", data),

  // Nếu có sử dụng GreenOnline
  kiemTraDKSDGreenOnline: (axios, data) =>
    axios.post("/thaydoidichvu/kiemtra_hd_bancheo_tc", data),

  // Thêm hàm kiểm tra gói theo yc
  kiemTraThayDoiGoiTheoYc: (axios, data) =>
    axios.get(
      `/web-hopdong/hopdong/kiemtra_thaydoi?loaiHdId=${data.loaiHdId}&thueBaoId=${data.thueBaoId}`
    ),

  // Kiểm tra hủy khuyến mại dvgt mới cho hủy
  kiemTraKMDatCocDVGT: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/kiemtra_km_datcoc_dvgt", data).then(rs => rs.data.data),

  // Kiểm tra dv đã đăng ký
  kiemTraDVDaDki: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/kiemtradichvudadk", data),

  // Kiểm tra thuê bao thanh toán
  kiemTraThueBaoThanhToan: (axios, data) =>
    axios.post("/web-hopdong/thanhly/thanh_ly_map_id", data),

  //Kiểm tra theo lệnh mới
  apiKiemTraNoThanhLyYC: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/kiemtra_no_thanhly_yc", data),

  // KT mytv chính phụ - nút ghi lại
  kiemTraMyTVChinhPhu: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/kiemtra_tb_mytv_chinhphu", data),

  // Kiểm tra đăng lý dịch vụ gt
  // kiemTraDK_DV_GT: (axios, data) =>
  //   axios.post("/web-hopdong/thaydoidichvu/kiemtra_dangky_dvgt", data),

  // LAY_DS_THAMSO_MD_MATS
  layDsThamSo_Md_Mats: (axios, data) =>
    axios.post("/web-ccdv/tram-thicong-dv-cntt/lay_ds_thamso_md_mats", data),
  lay_ds_thamso_md: (kieu) => axios.post(`web-hopdong/thanhly/sp_lay_ds_thamso_md`, {'kieu_id': kieu}).then(rs => rs.data.data),
  fn_tt_db_thuebao: (data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao`, data).then(rs => rs.data.data),
  kiemtra_hd_bancheo_tc: (data) => axios.post(`/web-hopdong/thaydoidichvu/kiemtra_hd_bancheo_tc`, data).then(rs => rs.data.data),
  kiemtra_makh_matt_magd: (data) => axios.post(`/web-hopdong/hopdong/kiemtra_makh_matt_magd`, data).then(rs => rs.data.data),
  fn_kiemtra_dk_sd_greenonline: (data) => axios.post(`/web-hopdong/thaydoidichvu/fn_kiemtra_dk_sd_greenonline`, data).then(rs => rs.data.data),
  lay_ds_hopdong_tb_theo_hdtb_id: (data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id',data).then(rs => rs.data.data),
  fn_tt_loaihinh_tb: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb',data).then(rs => rs.data.data),
  lay_ds_hdtb_adsl_theo_hdtb_id: (data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_hdtb_adsl_theo_hdtb_id',data).then(rs => rs.data.data),
  lay_ds_hdtb_mgwan_theo_hdtb_id: (data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_mgwan_theo_hdtb_id',data).then(rs => rs.data.data),
  lay_ds_hdtb_tsl_theo_hdtb_id1: (hdtbId) => axios.get(`web-hopdong/hopdong/lay_ds_hdtb_tsl_theo_hdtb_id?hdtbId=${hdtbId}`).then(rs => rs.data.data),
  lay_ds_hdtb_tsl_theo_hdtb_id: (data) => axios.post('/web-thicong/tienhtrinh_tcbh/lay_ds_hdtb_tsl_theo_hdtb_id', data).then(rs => rs.data.data),
  lay_danhsach_thuoctinh_v2: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_danhsach_thuoctinh_v2',data).then(rs => rs.data.data),
  lay_ds_hdtb_cntt_theo_hdtb_id: (data) => axios.post('/web-thicong/hoinghi_truyenhinh/lay_ds_hdtb_cntt_theo_hdtb_id', data).then(rs => rs.data.data),
  lay_ds_isdn_theo_thuebao_id: (tbid) => axios.post(`web-hopdong/thanhly/lay_ds_isdn_theo_thuebao_id`, {'thuebao_id': tbid}).then(rs => rs.data.data),
  lay_ds_diachi_theo_dbtbid: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_diachi_theo_dbtbid',data).then(rs => rs.data.data),
  sp_Lay_tienhd_km_theo_hdtb: (data) => axios.post('/web-hopdong/thuebao_datcoc_thunghiem/sp_Lay_tienhd_km_theo_hdtb',data).then(rs => rs.data.data),
  lay_thongtin_phieu_tn: (data) => axios.post('web-hopdong/thanhly/sp_lay_thongtin_phieu_tn', data).then(rs => rs.data.data),
  LAY_DS_DANGKY_DVK: (data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_dangky_dvk', data).then(rs => rs.data.data),
  kiemtra_thaydoi: (data) => axios.get(`web-hopdong/hopdong/kiemtra_thaydoi?thueBaoId=${data.thueBaoId}&loaiHdId=${data.loaiHdId}`, data).then(rs => rs.data.data),
  kiemTraDK_DV_GT: (data) => axios.post("/web-hopdong/thaydoidichvu/kiemtra_dangky_dvgt", data).then(rs => rs.data.data),
  fn_tt_tocdo_adsl: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_tocdo_adsl',data).then(rs => rs.data.data),
  fn_tt_bd_thuebao: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_bd_thuebao',data).then(rs => rs.data.data),
  fn_tt_nhanvien: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien',data).then(rs => rs.data.data),
  fn_tt_doitac: (data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_doitac',data).then(rs => rs.data.data),
  //Lay_ds_db_dd_theo_tbid : (data) => axios.post('/web-quantri/thutienphatsinh/lay_ds_db_dd_theo_tbid',data).then(rs => rs.data.data),
  lay_ds_db_dd_theo_tbid: (data) => axios.post("/web-tracuu/danhba/lay_ds_db_dd_theo_tbid", data).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_tb: (data) => axios.post(`/web-hopdong/khoiphucthanhly/lay_ds_hopdong_theo_ma_tb`, data).then(rs => rs.data.data),
  lay_ds_danhba_megawan: (data) => axios.get(`/web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${data.thuebaoId}`, data).then(rs => rs.data.data),
  sp_ds_tienkhoanmuc_loaihd: (data) => axios.post(`/web-hopdong/thuebao_datcoc_thunghiem/sp_ds_tienkhoanmuc_loaihd`, data).then(rs => rs.data.data),
  checkGreenNet: (data) => axios.post(`/tichhop/visa-com/checkGreenNet`, data).then(rs => rs.data.data),
  get_fn_ktra_tocdo_dk_greennet: async (axios, data) => axios.get('/web-tracuu/hopdong/fn_ktra_tocdo_dk_greennet',data),
  lay_ds_thamso_md_mats: (data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/lay_ds_thamso_md_mats`, data).then(rs => rs.data.data),
  kiemtra_km_datcoc_dvgt: (data) => axios.post(`/web-hopdong/thaydoidichvu/kiemtra_km_datcoc_dvgt`, data).then(rs => rs.data.data),
  kt_thaotac: (luongId, code) => axios.post(`web-hopdong/khoiphucthanhly/fn_kt_thaotac`, { 'luong_id': luongId, 'code': code }).then(rs => rs.data.data),
  lay_ds_db_megawan_theo_tbid: (in_thuebao_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_megawan_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id
  }).then(rs => rs.data.data),
  lay_ds_db_adsl_theo_tbid: (tbID) => axios.post('web-ccdv/xuly_nghiemthubaohong/lay_ds_db_adsl_theo_tbid', {'thuebao_id': tbID}).then(rs => rs.data.data),
  lay_ds_db_ims_theo_tbid: (tbID) => axios.post('web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_ims_theo_tbid', {'thuebao_id': tbID}).then(rs => rs.data.data),
  lay_ds_db_cd_theo_tbid: (thuebaoId) => axios.post(`web-hopdong/thaydoiloaihinhtbcodinhims/lay_ds_db_cd_theo_tbid`, {'thuebao_id': thuebaoId}).then(rs => rs.data.data),
  lay_ds_db_tsl_theo_tbid: (in_thuebao_id, p_phanvung_id) => axios.post(`web-quantri/thutienphatsinh/lay_ds_db_tsl_theo_tbid`, {
    "in_thuebao_id": in_thuebao_id,
    "p_phanvung_id": p_phanvung_id
  }).then(rs => rs.data.data),
  ts_tracuu_laytt_tb: (data) => axios.post('ccbs/executor/ts_tracuu_laytt_tb', data).then(rs => rs.data.data),
  ts_thongtin_dichvu_tb: (data) => axios.post('ccbs/executor/ts_thongtin_dichvu_tb', data).then(rs => rs.data.data), //todo
  LAY_DS_SUDUNG_DVK_KIEU_LD: (data) => axios.post('/web-hopdong/thaydoidichvu/lay_ds_sudung_dvk_kieu_ld', data).then(rs => rs.data.data),
  LAY_DS_DICHVU_KHAC_DBID: (data) => axios.post('/web-hopdong/thaydoithongtinkhuyenmai/LAY_DS_DICHVU_KHAC_DBID', data).then(rs => rs.data.data),
  lay_thongtin_ims: (data) => axios.get(`web-hopdong/dk_goi_dadichvu/lay_thongtin_ims?thuebao_id =${data.thuebao_id}`, data).then(rs => rs.data.data),
  lay_ds_hopdong_theo_ma_gd: (data) => axios.post('/web-hopdong/hopdong/lay_ds_hopdong_theo_ma_gd', data).then(rs => rs.data.data),
  lay_ds_dbtb_dv: (data) => axios.post('/web-hopdong/dichvu-ca/sp_lay_ds_dbtb_dv',data).then(rs => rs.data.data),
  lay_ds_hdtb_dv: (data) => axios.post('/web-hopdong/hopdong/khac/lay_ds_hdtb_dv',data).then(rs => rs.data.data),
  kiemtra_hdtb_khi_themmoi: (data) => axios.post('/web-hopdong/khoiphucthanhly/kiemtra_hdtb_khi_themmoi',data).then(rs => rs.data.data),
  kiemtra_tb_cung_makh_tb_pl: (data) => axios.post('/web-hopdong/khoiphucthanhly/kiemtra_tb_cung_makh_tb_pl',data).then(rs => rs.data.data),
  kiemtra_thuebao_lap_hopdong_khac: (data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac',data).then(rs => rs.data.data),
  kiemtra_db_khi_themmoi: (data) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi',data).then(rs => rs.data.data),

  // lay_huonggiao_tiepnhan
  layHuongGiaoTiepNhan: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_huonggiao_tiepnhan", data),

  // KT danh bạ khi thêm
  kiemTraKhiThemMoi: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/kiemtra_db_khi_themmoi", data),

  // KIEMTRA_MA_TB_LAP_HOPDONG_KHAC
  kiemTraMaTbLapHDKhac: (axios, data) =>
    axios.post("/web-hopdong/doisim/kiemtra_ma_tb_lap_hopdong_khac", data),

  // nút xóa hd_tb
  xoaHDTB: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/xoa_hdtb", data),

  // TaoDuLieu_HDKH - api MAP_ID
  mapID: (axios, data) => axios.post("/web-danhba/thamso/map_id", data),

  // TaoDuLieu_HDTB_MGWAN - LAY_DS_DB_MEGAWAN_THEO_TBID
  apiLAY_DS_DB_MEGAWAN_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_megawan?thueBaoId=${hdtbId}`),

  // TaoDuLieu_HDTB_TSL - LAY_DS_DB_TSL_THEO_TBID
  apiLAY_DS_DB_TSL_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_tsl?thueBaoId=${hdtbId}`),

  // TaoDuLieu_HDTB_DV
  apiLAY_DS_HDTB_DV: (axios, variables) =>
    axios.post("/web-hopdong/hopdong/khac/lay_ds_hdtb_dv", variables),

  // TaoDuLieu_HDTB_CD
  apiLAY_DS_DB_CD_THEO_TBID: (axios, variables) =>
    axios.post("/web-quantri/thongsone/lay_ds_db_cd_theo_tbid", variables),

  // TaoDuLieu_HDTB_ADSL
  apiLAY_DS_DB_ADSL_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_adsl?thueBaoId=${hdtbId}`),

  // TaoDuLieu_HDTB_IMS
  apiLAY_DS_DB_IMS_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_ims?thueBaoId=${hdtbId}`),

  //TaoDuLieu_HDTB_GP
  apiLAY_DS_DB_GP_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${hdtbId}`),

  // TaoDuLieu_HDTB_CNTT
  apiLAY_DS_DB_CNTT_THEO_TBID: (axios, hdtbId) =>
    axios.get(`/web-hopdong/hopdong/lay_ds_danhba_cntt?thueBaoId=${hdtbId}`),

  // TaoDuLieu_BD_LYDOHUY
  apiLAY_DS_DB_LYDOHUY_THEO_TBID: (axios, hdtbId) =>
    axios.get(
      `/web-hopdong/thaydoidichvu/lay_ds_db_lydohuy_theo_tbid?thuebao_id=${hdtbId}`
    ),

  apiMapId: (axios, variables) =>
    axios.post("/web-danhba/thamso/map_id", variables),

  apiMapIdHuy: (axios, variables) =>
    axios.post("/web-tratruoc/thongtin_tratruoc_dn/map_id", variables),

  // nút ghi lai
  ghiLaiHDTB: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/fn_thaydoidv_ghilai", data),

  // nút thêm tb
  apiThemTB: (axios, data) =>
    axios.post("/web-hopdong/thaydoidichvu/fn_thaydoidv_themtb", data),

  LAY_DANHBA_THEO_MATB: (data) => axios.post('/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB', data).then(rs => rs.data.data),
    // HienThiTT_DanhBa
  apiLAY_DANHBA_THEO_MATB: (axios, data) =>
    axios.post("/web-hopdong/hopdong/lay_danhba_theo_matb", data),
  lay_ds_chitiet_km_theo_tbid: (data) => axios.post('/web-hopdong/thaydoiloaihinhtb/lay_ds_chitiet_km_theo_tbid', data).then(rs => rs.data.data),
  // hàm get key
  apiGetKeys: (axios, data) =>
    axios.post("/web-hopdong/khoiphucthanhly/get_keys", data),

  // sinh mã giao dịch
  apiLay_Ma_GD_V2: (axios, data) =>
    axios.post("/web-hopdong/thongtin_tratruoc_dn/sinh_magd_v2", data),

  // Sinh mã giao dịch theo đơn vị
  SINH_MAGD_V2: (data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd_theo_dv', data),
  // Sinh mã giao dịch
  SINH_MAGD: (data) => axios.post('/web-hopdong/CapNhatTraTruoc/sinh_magd', data),
  
  // api hướng tiếp nhận
  apiLayHuongGiao_TiepNhan: (axios, data) =>
    axios.post("/web-hopdong/thanhly-thuebao-bancheo/layhuonggiao_tiepnhan_v2", data),

  // api LAY_DS_HOPDONG_TB_THEO_HDKH_ID
  apiLAY_DS_HOPDONG_TB_THEO_HDKH_ID: (axios, hdKhId) =>
    axios.get(
      `/web-hopdong/hopdong/lay_ds_hopdong_thuebao_theo_hdkh?hdKhId=${hdKhId}`
    ),

  // LAY_DS_DK_LYDOHUY
  apiLAY_DS_DK_LYDOHUY: (axios, data) =>
    axios.post("/web-hopdong/thanhly/lay_ds_dk_lydohuy", data),

  // lấy tất cả ds lý do
  dsLyDo: (axios, data) =>
    axios.post("/web-hopdong/thanhly/lay_ds_lydo_huy", data),
  get_kiemtra_tuvan_mytv: async (axios, data) => axios.get(`/web-ccdv/tuvan_mytv/kiemtra_tuvan_mytv`,data),
  post_lay_khoanmuc_tt_theo_loaihd : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd',data),
  kt_donvi_quytrinh_cntt: async(data) =>  axios.get('/web-thicong/dvcntt/kt_donvi_quytrinh_cntt?hdtb_id=' + data),
};
