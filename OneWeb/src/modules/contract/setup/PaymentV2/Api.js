// https://api-onebss-dev.vnptit3.vn/proxy/swagger-ui.html?urls.primaryName=web-hopdong#/UR2.2.001
// WinUI.WinUIThanhtoan.frmThanhToanHD_V2,PTTB.exe,2
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=898145042&range=A1:E1
export default {
  getDmKenhThu: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_kenhthu_tt', data),
  getDmKenhThuTGDD: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_kenhthu_tt_tgdd', data),
  getDmTrangThaiTT: (axios, data) => axios.post('/web-ccdv/tienhopdong/sp_lay_ds_trangthai_tt', data),
  getDmHinhThucTra: (axios, data) => axios.get('/web-quantri/danhmuc-chung/CSS_HINHTHUC_TRA', data),
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
  getPhieuThanhToanThoaiTra: (axios, data) => axios.post(`/web-ccdv/tienhopdong/phieutt_hd_by_hdkh`, data),
  thongtin_nguoidung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
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
  GET_FKEY: (axios, data) => axios.get('/web-hopdong/ThayDoiHopDong/sinh_fkey', data)
}
