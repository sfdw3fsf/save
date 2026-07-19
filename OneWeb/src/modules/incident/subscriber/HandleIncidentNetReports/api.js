export default {

  
  getDsQuyTrinh: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_quytrinh_bh_theo_dichvuvt_id', data),
  getDsDichVuVT: async (axios, data, header) => axios.get('/web-quantri/danhmuc-chung/CSS_DICHVU_VT', data, header),
  getDanhSachThamSoMD: (axios) => axios.post('/web-hopdong/lapdatmoi/lay_ds_thamso_md_mats', {"ma_ts": ""}),
  getTinhTrang: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_tinhtrang', data),

  //thoains update api
  getQuytrinhByDVVT: (axios, dichvuvt_id) => axios.post('web-hopdong/xuly_nghiemthubaohong/sp_lay_ds_quytrinh_bh_theo_dichvuvt_id', { dichvuvt_id }),
  getNhomNguyenNhan: (axios, dichvuvt_id) => {
    return axios.post('/web-baohong/xuly_nghiemthubaohong/sp_lay_ds_nhom_nn_v2', { dichvuvt_id })
  },
  getNguyenNhanNet: (axios, data) => axios.post('web-baohong/nghiemthu_bh/lay_ds_nguyennhan_net', data),
  getNguyenNhanBHV2: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/lay_ds_nguyennhan_baohong_v2', data),

  getDanhSachBaoHongNet: (axios, data) => axios.post('/web-baohong/nghiemthu_bh/lay_ds_phieu_bh_v6', data),
  addNgiemThuBHNet: (axios, data) => axios.post('/web-baohong/nghiemthu_bh/fn_ngiemthubh_net_ghilai', data),
  updateNgiemThuBHNet: (axios, data) => axios.post('/web-baohong/nghiemthu_bh/fn_ngiemthubh_net_capnhat', data),
  checkHoanThanhPhieuNet: (axios) => axios.post('/web-baohong/baohong/kiemtra_hoanthanh_nhieuphieu_net'),


  getStatusBaoHong: (axios, baohong_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_mgwan', { type: 4, param: baohong_id }),
  getDSTienTrinhBH: (axios, baohong_id, phanvung_id) => axios.get(`web-baohong/baohong/sp_lay_ds_tientrinh_bh_dv?baohong_id=${baohong_id}&phanvung_id=${phanvung_id}`),

  getKey: (axios, data) => axios.post('web-bancheo/tracuu/get_key', data),
  
  getKeyBH_TC : (axios, tinhthicong_id) => axios.post('web-bancheo/tracuu/get_key', {tinhthicong_id, keyname: "BAOHONG" }),

  postDeleteTienTrinh: (axios, data) => axios.post('/web-baohong/nghiemthu_bh/fn_ngiemthubh_net_xoa_tientrinh', data),
  postUpdateTienTrinh: (axios, data) => axios.post('web-baohong/nghiemthu_bh/fn_ngiemthubh_net_ghilai', data),

  getFileDownloads: (axios, baohong_id, phanvung_id) => axios.get(`web-tracuu/tracuu-thuebao/lay_ds_file_nt_bh_bc/${baohong_id}?phanvung_id=${phanvung_id}`),
  upLoadFileToMinio: (axios, data) => axios.post('/web-quantri/upload', data),
  insertFileHS: (axios, { vfile_id, vurl, vfile_name }) => axios.post('web-baohong/nghiemthu_bh/sp_insert_file_hs', { vfile_id, vurl, vfile_name }),
  addFileHSToThueBao: (axios, data) => axios.post('web-baohong/nghiemthu_bh/sp_themmoi_hs_thuebao', data),
  
  updateBienBanNghiemThuBH: (axios, data) => axios.post('/web-baohong/xuly_nghiemthubaohong/capnhat_bbnt_bh_net', data),
 
  insertNguyenNhanChuyenIT360: (axios, { tinhthicong_id, vcthong_id, vphieu_id }) => axios.post('/web-bancheo/baohong/sp_insert_nguyennhan', { tinhthicong_id, vcthong_id, vphieu_id }),
  
  giaoPhieuDonViTiepV4: (axios, { tinhthicong_id, vbaohong_id, vphieu_id, vnhanvien_id, vhuonggiao_id }) => {
    return axios.post('/web-bancheo/baohong/giaophieu_donvi_tieptheo_v4', { tinhthicong_id, vbaohong_id, vphieu_id, vnhanvien_id, vhuonggiao_id })
  },
  updateTTPHPhieuHP: (axios, vphieu_id) => {
    return axios.post('/web-baohong/nghiemthu_bh/capnhat_ttph_phieuph', { vphieu_id })
  },
  checkPhieuHoanThanhTinh: (axios, { tinhthicong_id, vbaohong_id }) => {
    return axios.post('/web-bancheo/baohong/kiemtra_phieu_hoanthanh_tinh', { tinhthicong_id, vbaohong_id })
  },
  nghiemThuTinhNet: (axios, { tinhthicong_id, vbaohong_id }) => {
    return axios.post('/web-bancheo/baohong/nghiemthu_net_tinh', { tinhthicong_id, vbaohong_id })
  },
  checkGiaoTinh: (axios, { vphanvung_id_tc, vbaohong_id }) => {
    return axios.post('/web-baohong/nghiemthu_bh/fn_kiemtragiaotinh', { vphanvung_id_tc, vbaohong_id })
  },


  hoanThienBaoHongNetTinh: (axios, { vttph_id, vphieu_id, vnoidung_xuly }) => axios.post('/web-baohong/nghiemthu_bh/hoanthien_bh_net_tinh', { vttph_id, vphieu_id, vnoidung_xuly }),

  giaoPhieuBaoHong: (axios, { vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vhuongiao_id, vma_nd, vnd_giao, vphieu_cha_id, vttbh_id }) => axios.post('/web-baohong/TiepNhanBaoHong/giaophieu_bh', { vbaohong_id, vdonvi_giao_id, vdonvi_nhan_id, vhuongiao_id, vma_nd, vnd_giao, vphieu_cha_id, vttbh_id }),

  getTTBaoHong: (axios, { tinhthicong_id, vbaohong_id }) => axios.post('web-bancheo/baohong/sp_tt_baohong', { tinhthicong_id, vbaohong_id }),

  updateBaoHongNet: (axios, { p_phanvung_id_tc, p_ds_para }) => axios.post('web-baohong/nghiemthu_bh/fn_ngiemthubh_net_capnhat', { p_phanvung_id_tc, p_ds_para }),

  insertNguyenNhan: (axios, { tinhthicong_id, vcthong_id, vphieu_id }) => axios.post('web-bancheo/baohong/sp_insert_nguyennhan', { tinhthicong_id, vcthong_id, vphieu_id }),

  banCheoHoanThanhBaoHongV3: (axios, { vttph_id, vphieu_id, vct_hong, vkqxl_id, vghichu }) => axios.post('web-hopdong/bancheo-gpph-bh/bancheo_hoanthanh_baohong_v3', { vttph_id, vphieu_id, vct_hong, vkqxl_id, vghichu }),

  getThongTinNguoiDung: (axios, ma_nd) => axios.post('web-hopdong/laydulieu/sp_tt_nguoidung', { p_param: ma_nd }),

  giaoPhieuDonViTiepTheoV2: (axios, { vbaohong_id, vphieu_id, vhuonggiao_id , vphanvung_id}) => axios.post('web-hopdong/tien_trinh_bao_hong/giaophieu_donvi_tieptheo_vsecond', { vbaohong_id, vphieu_id, vhuonggiao_id , vphanvung_id}),

  giaoPhieuDonViTiepTheoV3: (axios, { vbaohong_id, vphieu_id, vhuonggiao_id , vphanvung_id}) => axios.post('web-hopdong/tien_trinh_bao_hong/giaophieu_donvi_tieptheo_vthird', { vbaohong_id, vphieu_id, vhuonggiao_id , vphanvung_id}),
  giaoPhieuDonViTiepTheo: (axios, { baohong_id, phieu_id, nhanvien_id, huonggiao_id, may_cn, ngay_cn, nguoi_cn, phanvung_id }) => axios.post('web-hopdong/xuly_nghiemthubaohong/giaophieu_donvi_tieptheo', { baohong_id, phieu_id, nhanvien_id, huonggiao_id, may_cn, ngay_cn, nguoi_cn, phanvung_id }),

  capNhatNhanVienTH: (axios, { vphieu_id, vnhanvien_th_id, vngay_th }) => axios.post('web-baohong/TiepNhanBaoHong/capnhat_nhanvien_th', { vphieu_id, vnhanvien_th_id, vngay_th }),
  capNhatGhiChu: (axios, { vghichu_th, vngay_cn, vphieu_id, vbaohong_id, phanvung_id }) => axios.post('web-baohong/nghiemthu_bh/sp_capnhat_ghichu_th', { vghichu_th, vngay_cn, vphieu_id, vbaohong_id , phanvung_id}),
  capNhaTrangThaiGiaoPhieu: (axios, { vphieu_id }) => axios.post('web-baohong/nghiemthu_bh/sp_capnhat_trangthai_giaophieu_ph', { vphieu_id }),

  kiemTraThueBaoBH: (axios, { tinhthicong_id, vthuebao_id }) => axios.post('web-bancheo/tracuu/kiemtra_thuebao_baohong', { tinhthicong_id, vthuebao_id, vkieu: 2 }),

  updateBaoHong_BanCheoBH: (axios, { vbaohong_id_tc, vbaohong_id_ban }) => axios.post('web-baohong/TiepNhanBaoHong/sp_update_baohong_bc_baohong_id', { vbaohong_id_tc, vbaohong_id_ban }),

  getTTGiaoPhieu: (axios, { type , param }) => axios.post('web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_giaophieu', { param , type }),
  getTTGiaoPhieu_PH: (axios, { baohong_id, tinhthicong_id,  phieu_id }) => axios.post('web-baohong/baohong/fn_tt_giaophieu_ph_trangthai', { baohong_id, tinhthicong_id,  phieu_id}),


  giaoPhieuTTBaoHong: (axios, baohong_id ) => axios.post('web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_giaophieu', {  type: 1, param: baohong_id }),
  
  getIdThueBaoThiCong: (axios, baohong_id) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_baohong_bc', { type: 4, param: baohong_id }),
  
  getStatusBC: (axios, baohong_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_baohong_bc', { type: 3, param: baohong_id }),
  
  checkGiaoPhieuVNPTIExits: (axios, baohong_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_giaophieu', { type: 4, param: baohong_id }),


  getBHId_TC: (axios, baohong_id ) => axios.post('web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_baohong_bc', { param: baohong_id, type: 1 }),

  getUserBHInfo: (axios, baohong_id ) => axios.post('web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_giaophieu', { param: baohong_id, type: 3 }),

  checkPhieuConHoanCong: (axios, phieu_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/baohong_fn_tt_giaophieu_ph', { type: 1, param: phieu_id }),
  getLoaiKenh: (axios, thuebao_id) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_mgwan', { type: 2, param: thuebao_id }),

  giaoPhieuTiepNhanNet: (axios, { tinhthicong_id, vbaohong_id, vloaitb_id, nguoi_cn }) => axios.post('web-bancheo/baohong/net_giaophieu_tiepnhan', { tinhthicong_id, vbaohong_id, vloaitb_id, nguoi_cn }),

  getThongTinGiaoPhieu: (axios, { tinhthicong_id, vbaohong_id, vtype }) => axios.post('web-bancheo/baohong/fn_tt_giaophieu', { tinhthicong_id, vbaohong_id, vtype }),

  giaoPhieuDVTT_TTVTV2: (axios, { tinhthicong_id, vbaohong_id, vphieu_id, vhuonggiao_id, vnguoi_cn_dai, vnguoi_cn_net, vthuebao_id, vloaitb_id }) =>
    axios.post('web-bancheo/baohong/giaophieu_donvi_tieptheo_ttvt_v2', { tinhthicong_id, vbaohong_id, vphieu_id, vhuonggiao_id, vnguoi_cn_dai, vnguoi_cn_net, vthuebao_id, vloaitb_id }),

  giaoPhieuNetDVTT: (axios, { tinhthicong_id, vbaohong_id, vphieu_id, vhuonggiao_id, vtinh_id, vdonvi_nhan_id }) => axios.post('web-bancheo/baohong/giaophieu_net_donvi_tieptheo', { tinhthicong_id, vbaohong_id, vphieu_id, vhuonggiao_id, vtinh_id, vdonvi_nhan_id }),
  layGoiKTId: (axios, {vthuebao_id,vhdtb_id}) => axios.post('web-hopdong/tien_trinh_bao_hong/lay_goikt_id',  {vthuebao_id,vhdtb_id}),

  layHuongGiaoTheoThuTu:(axios, { VPHANVUNG_ID, VLOAITB_ID, VTHUTU}) => axios.post('web-hopdong/tien_trinh_bao_hong/smp_layhuonggiao_baohong_theothutu',  {VPHANVUNG_ID, VLOAITB_ID, VTHUTU}),

  checkDichVuHoanCong_BHNet:(axios, vdichvuvt_id) => axios.post('web-baohong/nghiemthu_bh/kiemtra_dichvu_hoancong_baohong_net',  {vdichvuvt_id}),

  updateTrangThaiBaoHong: (axios, { baohong_id, ttbh_id }) => axios.post('web-hopdong/giaophieu-baohong/sp_capnhat_trangthai_baohong',  { baohong_id, ttbh_id }),
  
  updateTrangThaiBaoHongV2: (axios, { vtrang_thai, vbaohong_id_ban }) => axios.post('web-baohong/nghiemthu_bh/sp_update_baohong_bc_trangthai_v2',  { vtrang_thai, vbaohong_id_ban }),
  
  giaoPhieuDVTT_OMC: (axios, { vbaohong_id, vphieu_id, vhuonggiao_id, vnguoi_cn_dai, vnguoi_cn_net, vthuebao_id, vloaitb_id }) => 
    axios.post('web-baohong/nghiemthu_bh/giaophieu_donvi_tieptheo_omc',  { vbaohong_id, vphieu_id, vhuonggiao_id, vnguoi_cn_dai, vnguoi_cn_net, vthuebao_id, vloaitb_id } ),

  giaoPhieuBHMedia: (axios, { vphieu_id, vdonvi_giao_id }) => axios.post('web-baohong/baohong/giaophieu_baohong_media',  { vphieu_id, vdonvi_giao_id}),

    
  tiepNhanBaoHong: (axios, {tinh_tc, vloaitb_id, vthutu, vthuebao_id,vbaohong_id, dactrung, net_giao, ds,  dsBH_BC})=> 
    axios.post('web-baohong/TiepNhanBaoHong/nhan_hong', {tinh_tc, vloaitb_id, vthutu, vthuebao_id,vbaohong_id, dactrung, net_giao, ds,  dsBH_BC}),
  
  banCheoHoanThanhBaoHongV4: (axios, {vttph_id, vphieu_id, vct_hong, vghichu, vkqxl_id, vnhanvien_nt_id})=> 
    axios.post('web-baohong/baohong/bancheo_hoanthanh_baohong_v4', {vttph_id, vphieu_id, vct_hong, vghichu, vkqxl_id, vnhanvien_nt_id}),

  
  loadSMS: (axios,{ p_nhom_id, p_baohong_id, p_hdtb_id, p_tientrinhbh_id } )=>axios.post('web-hopdong/sms/fn_frmguisms_load',{ p_nhom_id, p_baohong_id, p_hdtb_id, p_tientrinhbh_id } ),
  sendSMS: (axios,{ p_nhom_id, p_tientrinhbh_id, p_noidung, p_so_dt, p_ma_tb } )=>axios.post('web-hopdong/sms/fn_frmguisms_btnguisms',{ p_nhom_id, p_tientrinhbh_id, p_noidung, p_so_dt, p_ma_tb }),
}
