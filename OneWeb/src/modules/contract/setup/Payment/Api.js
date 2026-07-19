// https://api-onebss-dev.vnptit3.vn/proxy/swagger-ui.html?urls.primaryName=web-hopdong#/UR2.2.001
// WinUI.WinUIThanhtoan.frmThanhToanHD,PTTB.exe,1
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=898145042&range=A1:E1
export default {
  getDmKenhThu: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_kenhthu_tt', data),
  getDmKenhThuV2: (axios, data) => axios.get(`/web-ccdv/tienhopdong/sp_lay_ds_kenhthu_tt_v2?ht_tra_id=${data.ht_tra_id}`),
  getDmKenhThuV3: (axios, data) => axios.get(`/web-tracuu/tracuu/sp_lay_ds_kenhthu?nghiepvu_id=${data.nghiepvu_id}&hinhthuc_tra_id=${data.ht_tra_id}`), 
  getDmKenhThuTGDD: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_kenhthu_tt_tgdd', data),
  getDmTrangThaiTT: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_trangthai_tt', data),
  getDmHinhThucTra: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TRA', data),
  getDmHinhThucTraV2: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TRA_V2', data),
  getDmHinhThucTraV3: (axios, data) => axios.get(`/web-tracuu/tracuu/sp_lay_ht_tra_theo_nghiepvu?nghiepvu_id=${data.nghiepvu_id}`),
  getDmNganHang: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_NGANHANG', data),
  getDmLoaiHoaDon: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_ht_loai_hoadon', data),
  getDmLoaiHoaDon2: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_ht_loai_hoadon_v2', data),
  getCboLoaiHd: (axios, data) => axios.post('/web-ccdv/tienhopdong/fn_thanhtoan_cboloaihd', data),
  getDmSeri: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_ht_seri', data),
  getDmSeri2: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_ht_seri', data),
  getDmLoaiTien: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAITIEN', data),
  getDmThuNgan: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_thungan', data),
  getDmDonVi: (axios, data) => axios.post('', data),
  getSoHoaDon: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_max_hoadon', data),
  checkMnp: (axios, data) => axios.post('/web-ccdv/tienhopdong/checkmnp', data),
  getThamSoMacDinh: (axios, data) => axios.post('/web-hopdong/thanhly/sp_lay_ds_thamso_md', data),
  getThamSoMacDinhNguoiDung: (axios, data) => axios.post('/web-ccdv/tienhopdong/fn_lay_thamso_macdinh', data),
  kiemTraKenhTiepNhan: (axios, data) => axios.post(`/web-ccdv/tienhopdong/fn_kiemtra_kenh_tn`, data),
  kiemtra_phathanh_hddt_thanhtoan: (axios, data) => axios.post(`/web-ccdv/tienhopdong/kiemtra_phathanh_hddt_thanhtoan`, data),
  kiemtra_datcoc_goi_dadv: (axios, data) => axios.get(`/web-hopdong/goi-da-dichvu/kiemtra_datcoc_goi_dadv?hdkh_id=${data.hdkh_id}`, data),
  getPhieuThanhToanThoaiTra: (axios, data) => axios.post(`/web-ccdv/tienhopdong/phieutt_hd_by_hdkh`, data),
  lay_thang_no: (axios, data) => axios.get(`/web-ccdv/tracuu-cuocnong/lay_thang_no`),
  lay_chuky_nohoadon: (axios, data) => axios.get(`/web-ccdv/tiepnhanhopdong/lay_chuky_nohoadon`),
  thongtin_nguoidung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
  thongtin: (axios, data) => axios.get('/web-quantri/nguoidung/thongtin', data),
  // getTienHopDongTheoKhId: (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_hdtb_theo_hdkh_tthd', data),
  getTienHopDongTheoKhId: (axios, data) => axios.post('/web-ccdv/tienhopdong/lay_tienhd_tb_theo_hdkh', data),
  // getTienKhuyenMaiTheoHdThueBao: (axios, data) => axios.get(`/web-thicong/hoanCongThuTienPS/layTienVatKhoanMuc?khuyenMaiId=${data.khuyenMaiId}`, data),
  getTienKhuyenMaiTheoHdThueBao: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_tienhd_km_theo_hdtb`, data),
  getDsHopDongByMaGD: (axios, data) => axios.post('/web-ccdv/tienhopdong/lay_ds_hopdong_theo_ma_gd_2', data),
  getDsHopDongTGDD: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_hd_tgdd', data),
  getDsHopDongThanhToanTheoHDKHID: (axios, data) => axios.get(`/web-tracuu/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId=${data.hdkhId}`, data),
  getDsThanhToanTheoHDKHID: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_ds_thanhtoan_theo_hdkh_1`, data),
  getDsNguoiBaoLanh: (axios, data) => axios.post(`/web-ccdv/tienhopdong/sp_lay_ds_nguoibaolanh`, data),
  getMoneyToText: (axios, data) => axios.post(`/web-tracuu/tracuu_th/fn_doisosangchu`, data),
  getPhieuIdByHdkhId: (axios, data) => axios.post(`/web-ccdv/tienhopdong/get_phieu_id_by_hdkh_id`, data),
  // Thanh toán
  lay_thongtin_chitiet_khoanmuc_: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_thongtin_chitiet_khoanmuc`, data),
  lay_thongtin_chitiet_khoanmuc: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/lay_thongtin_chitiet_khoanmuc?magd=${data.magd}`, data),
  insert_logsvnptpay: (axios, data) => axios.post(`/web-ccdv/tienhopdong/insert_logsvnptpay`, data),
  fn_thanhtoan: (axios, data) => axios.post(`/web-ccdv/tienhopdong/fn_thanhtoan`, data),
  fn_thanhtoan_pay: (axios, data) => axios.post(`/web-ccdv/tienhopdong/fn_thanhtoan_pay`, data),
  // End thanh toán

  // Popup can thanh toan
  getDsLoaiHD: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_LOAI_HD`),
  getDsDVTT: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`),
  getDsTTHD: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_TRANGTHAI_HD`),
  getDsLoaiHinhTB: (axios, data) => axios.get(`web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`),
  getDshdCanThanhtoan: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_ds_hd_can_thanhtoan_2`, data),
  getDshdCanGiahanThanhtoan: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_ds_hd_gia_can_thanhtoan`, data),
  // End popup can thanh toan
  // Vi VNPT Pay
  getThongTinViVNPTPay: (axios, data) => axios.post(`/web-ccdv/khaibaotsl/lay_ds_thamso_md_mats`, data),
  thongso_vivnptpay_smp: (axios, data) => axios.post(`/web-ccdv/tienhopdong/thongso_vivnptpay_smp`, data),
  thongtin_thucuoc: (axios, data) => axios.post(`/web-ccdv/tienhopdong/thongtin_thucuoc`, data),
  // End Vi VNPT Pay
  // Chuyen don vi - CDV
  getThongTinDonVi_CDV: (axios, data) => axios.post(`/web-ccdv/tienhopdong/fn_lay_tt_frmchuyendonvi_load`, data),
  getDsPhieuHong_CDV: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_danhsach_phieuhg`, data),
  getDsSoNe_CDV: (axios, data) => axios.post(`/web-ccdv/tienhopdong/sp_danhsach_so_ne`, data),
  chuyenPhongBanHang: (axios, data) => axios.post(`/web-ccdv/tienhopdong/chuyen_phongbanhang`, data),
  // End Chuyen don vi
  // Get Key
  getKey: (axios, data) => axios.post(`/web-quantri/donvitinh/get_keys`, data),
  lay_huonggiao_quytrinh: (axios, data) => axios.post(`/web-hopdong/thanhly/fn_lay_huonggiao_quytrinh`, data),
  lay_ds_huonggiao: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_huonggiao`, data),
  sp_lay_ds_huonggiao: (axios, data) => axios.post(`/web-ccdv/giaophieu/sp_lay_ds_huonggiao`, data),

  // Vi VNPT Pay
  loginVnptPay: (axios, data) => axios.post(`/tichhop/vnptpay/login`, data),
  getBalance: (axios, data, token) =>
    axios.post(`/tichhop/vnptpay/getBalance`, data, {
      headers: {
        WalletToken: `${token}`
      }
    }),
  initTransaction: (axios, data, token) =>
    axios.post(`/tichhop/vnptpay/initTransaction`, data, {
      headers: {
        WalletToken: `${token}`
      }
    }),
  commitTransaction: (axios, data, token) =>
    axios.post(`/tichhop/vnptpay/commitTransaction`, data, {
      headers: {
        WalletToken: `${token}`
      }
    }),
  cancelTransaction: (axios, data, token) =>
    axios.post(`/tichhop/vnptpay/cancelTransaction`, data, {
      headers: {
        WalletToken: `${token}`
      }
    }),
  // E
  // Print
  get_hoadon_vt01: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/get_hoadon_vt01`, data),
  in_phieuthu: (axios, data) => axios.post(`/web-ccdv/phieu_thanhtoan/in_phieuthu`, data),
  listInvByCus: (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus', data),
  listInvByCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus_VTT', data),
  //
  fn_kt_lap_kem_co_san: (axios, data) => axios.post(`/web-thicong/hoancongmegawan/fn_kt_lap_kem_co_san`, data),
  sp_lay_ds_port_theo_ma_tb: (axios, data) => axios.post(`/web-thicong/hoancongmegawan/sp_lay_ds_port_theo_ma_tb`, data),
  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  Lay_DS_DsLam: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/sp_lay_ds_dslam_tt`, data),
  lay_thongtin_ctv_thanhtoanhd: (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/lay_thongtin_ctv_thanhtoanhd?hdkhId=${data.hdkhId}`),
  sp_lay_dulieu_hoadon: (axios, data) => axios.post(`/web-bancheo/thaydoimegawan/sp_lay_dulieu_hoadon`,data),
  fn_tt_donvi: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`, data),
  lay_dslam_theo_port_id: (axios, data) => axios.post(`/web-thicong/khaibao-truyensolieu/sp_lay_dslam_theo_port_id`, data),
  LAY_DS_PORT_TUDONG: (axios, data) => axios.post(`/web-ccdv/tienhopdong/lay_ds_port_tudong`, data),
  lay_hopdong_tb_hddt: (axios, data) => axios.post(`/web-hopdong/thaydoiloaihinhtb/lay_hopdong_tb_hddt`, data),
  UpdateCus: (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus', data),
  ImportAndPublishInv_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv', data),
  GET_FKEY: (axios, data) => axios.get('/web-hopdong/ThayDoiHopDong/sinh_fkey', data),
  capnhat_phieu_id_neo: (axios, data) => axios.post(`/web-ccdv/thanhtoan/capnhat_phieu_id_neo`, data),
  // CCBS
  ts_thuphi_hoamangmoi_layds_tb_ld: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_layds_tb_ld', data),
  ts_laphd_chuyenquyen_sudung_cacphieutra: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_cacphieutra', data),
  ts_thuphi_hoamangmoi_thuphi_xoaphieu: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_thuphi_xoaphieu', data),
  ts_laphd_chuyenquyen_sudung_xoaphieu: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_xoaphieu', data),
  ts_thuphi_hoamangmoi_thuphi_thanhtoan: (axios, data) => axios.post('/ccbs/executor/ts_thuphi_hoamangmoi_thuphi_thanhtoan', data),
  ts_laphd_chuyenquyen_sudung_thanhtoan: (axios, data) => axios.post('/ccbs/executor/ts_laphd_chuyenquyen_sudung_thanhtoan', data),
  //NewLife
  lay_ds_ht_tra_newlife: (axios, data) => axios.post('/web-thicong/new-life/lay_ds_ht_tra_newlife', data),
  lay_ds_trangthai_tt_newlife: (axios, data) => axios.post('/web-thicong/new-life/lay_ds_trangthai_tt_newlife', data),
  lay_ds_kenhthu_newlife: (axios, data) => axios.post('/web-thicong/new-life/lay_ds_kenhthu_newlife', data),
  kiemtra_tien_khoanmuc: (axios, data) => axios.post('/web-thicong/new-life/kiemtra_tien_khoanmuc', data),
  // app config
  getAppConfig: (axios, data) => axios.post('/web-hopdong/hopdong/get_app_config', data),
  updateUserState: (axios, data) => axios.post('/tichhop/ipcc/updateUserState', data),
  capnhat_status_hdtb: (axios, data) => axios.post(`/web-hopdong/hoanthienhschuyenquyen/capnhat_status_hdtb?hdtb_id=${data.hdtb_id}&status=${data.status}`, data),
  // frmThongTinTT
  fn_hienthi_nhap_loaitien_vnpti: (axios, data) => axios.get(`/web-hopdong/hopdong/fn_hienthi_nhap_loaitien_vnpti?hdkh_id=${data.hdkh_id}`),

  // Tú thêm
  lay_ds_thuebao_dichvu: (axios, data)  => axios.post('/web-hopdong/khoiphucthanhly/lay_ds_thuebao_dichvu',data), 
  //Thong tin ngoai te
  lay_tt_nganhang: (axios) => axios.get(`web-hopdong/quanlytygia/lay_tt_nganhang`),
  lay_tt_loaitien: (axios) => axios.get(`web-hopdong/quanlytygia/lay_tt_loaitien`),
  lay_ds_loai_tygia: (axios) => axios.get(`web-hopdong/quanlytygia/lay_ds_loai_tygia`),
  sp_lay_ds_thuebao_tygia: (axios, data) => axios.post(`web-hopdong/quanlytygia/sp_lay_ds_thuebao_tygia`, data),
  fn_update_thongtin_thanhtoan_bs_v2: (axios, data) => axios.post(`web-hopdong/quanlytygia/fn_update_thongtin_thanhtoan_bs_v2`, data),

  lay_hopdong_tb_hddt_tt78_v2: (axios, {  vhdkh_id,  vkieu,  vgopnhom }) => axios.post(`web-hopdong/hopdong/lay_hopdong_tb_hddt_tt78_v2`, {  vhdkh_id,  vkieu,  vgopnhom }),

}
