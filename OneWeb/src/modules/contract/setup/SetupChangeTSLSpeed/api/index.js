export default {
  ur_001_lay_tienkhoanmuc_loaihd: (axios, data) => axios.post('/web-thicong/danhmuc/lay_tienkhoanmuc_loaihd', data),
  ur_002_lay_tocdo_kenh: (axios, data) => axios.post('/web-thicong/danhmuc/lay_tocdo_kenh', data),
  ur_003_dichvu_vienthong: (axios, data) => axios.post('/web-hopdong/danhmuc/dichvu_vienthong', data),
  ur_004_danhsach_doitac: (axios, data) => axios.post('/web-thicong/danhmuc/danhsach_doitac', data),

  // ur_007, ur_010, ur_039
  ur_005_lay_khuyenmai_hdtb: (axios, data) => axios.post('/web-thicong/danhmuc/lay_khuyenmai_hdtb', data),  

  // ur_021, ur_036, ur_038, ur_041
  ur_006_lay_ds_hopdong_theo_ma_tb_tsl: (axios, data) => axios.post('/web-thicong/thicong/lay_ds_hopdong_theo_ma_tb_tsl', data),      

  ur_011_lay_tien_khoanmuc_ld: (axios, data) => axios.post('/web-thicong/thicong/lay_tien_khoanmuc_ld', data),  
  ur_008_lay_ds_diachi_theo_dbtbid: (axios, data) => axios.post('/web-thicong/thicong/lay_ds_diachi_theo_dbtbid', data),
  ur_009_loaihinh_thuebao: (axios, data) => axios.post('/web-hopdong/danhmuc/loaihinh_thuebao', data),
  ur_012_lay_ds_kieu_ld: (axios, data) => axios.post('/web-thicong/danhmuc/lay_ds_kieu_ld', data),

  // ur_013
  ur_012_lay_ds_kieu_ld_by_loaitb_loaihd: (axios, data) => axios.post('/web-thicong/danhmuc/lay_ds_kieu_ld_by_loaitb_loaihd', data),

  // ur_014, ur_015 => Tich hop tu form chuyen quyen, khong co api

  ur_016_lay_ds_hdtb_tsl_theo_hdtb_id: (axios, data) => axios.post('/web-thicong/thicong/lay_ds_hdtb_tsl_theo_hdtb_id', data),
  ur_017_lay_chitiet_km_theo_hdtb_id: (axios, data) => axios.post('/web-thicong/thicong/lay_chitiet_km_theo_hdtb_id', data),
  ur_018_lay_tien_hd_km_theo_hdtb: (axios, data) => axios.post('/web-thicong/thicong/lay_tien_hd_km_theo_hdtb', data),

  // ur_035, ur_040
  ur_022_lay_ds_hdtb_thaydoi_tocdo_tsl: (axios, data) => axios.post('/web-thicong/danhmuc/lay_ds_hdtb_thaydoi_tocdo_tsl', data), 

  ur_010_21030010_lay_ds_danhba_tsl: async (axios, data, header) => axios.get('/web-hopdong/hopdong/lay_ds_danhba_tsl', data, header),
  ur_019_21008054_kiemtra_hd_bancheo_tc: async (axios, data) => axios.post('/web-hopdong/thaydoidichvu/kiemtra_hd_bancheo_tc', data),
  ur_020_kiemtra_makh_mtt: async (axios, data) => axios.post('/web-thicong/thicong/kiemtra_makh_mtt', data),
  ur_023_kiemtra_thoihan_km: async (axios, data) => axios.post('/web-thicong/thicong/kiemtra_thoihan_km', data),
  CollectGeneratedAferInstallAPI_layNgayKM: async (axios, data, header) => axios.get('/web-tratruoc/hoanCongThuTienPS/layNgayKM', data, header),
  ur_024_211007003_kiemtra_thuebao_lap_hopdong_khac: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_thuebao_lap_hopdong_khac', data),
  ur_025_21030079_kiemtra_danhba_khi_themmoi: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_danhba_khi_themmoi', data),
  ur_026_21030081_lay_huonggiao_tiepnhan: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_huonggiao_tiepnhan', data),
  getKey: async (axios, data, header) => axios.post('/web-qlvt/api/cap-phat-ccdc/get-key', data, header),
  ur_21007051_sinh_magd: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/sinh_magd', data),
  fn_tt_db_khachhang: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_khachhang', data),
  fn_tt_doitac: (axios, data) => axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_doitac', data),
  ur_027_sp_themmoi_hopdongtb_thaydoitocdotsl: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_themmoi_hopdongtb_thaydoitocdotsl', data),
  ur_027A_sp_themmoi_hopdongtb_thaydoitocdotsl: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_themmoi_hopdongtb_thaydoitocdotsl', data),
  ur_028_fn_kiemtra_tb_tontai_trong_hdtb: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/fn_kiemtra_tb_tontai_trong_hdtb', data),
  ur_029_sp_capnhat_hopdong_thaydoi_tsl: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/sp_capnhat_hopdong_thaydoi_tsl', data),
  ur_030_21007065_update_huonggiao_tiepnhan: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/update_huonggiao_tiepnhan', data),
  ur_031_24023011_xoa_hopdong: async (axios, data) => axios.post('/web-hopdong/hopdong/khoa_mo_thuebao_no_cuoc/xoa_hopdong', data),
  ur_032_21030078_kiemtra_hdtb_khi_themmoi: async (axios, data) => axios.post('/web-hopdong/hopdong/kiemtra_hdtb_khi_themmoi', data),
  
  // ur_034
  ur_033_21007058_kiemtra_tb_cung_makh_tb_pl: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/kiemtra_tb_cung_makh_tb_pl', data),

  ur_037_21030050_lay_danhba_theo_matb: async (axios, data) => axios.post('/web-hopdong/hopdong/lay_danhba_theo_matb', data),

  ur_048_21007062_xoa_hdtb: async (axios, data) => axios.post('/web-hopdong/khoiphucthanhly/xoa_hdtb', data),
  ur_048A_lay_ds_hdtb_dv: async (axios, data) => axios.post('/web-hopdong/hopdong/khac/lay_ds_hdtb_dv', data),

  lay_db_theo_hdkh_cha: (axios, data) => axios.get(`web-hopdong/hopdong/lay_db_theo_hdkh_cha?hdkh_cha_id=${data.hdkh_cha_id}`, data),
  
}
