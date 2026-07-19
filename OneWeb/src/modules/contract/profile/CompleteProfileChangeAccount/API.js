// [UR2.6.019] - Hoàn thiện hồ sơ Đổi số / Account
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1200265806&range=A509
// WinUI.WinUIHoanThienHS.frmHTHSDoiSoThueBao
export default {
  // form load
  // UR2.6.019_001
  lay_ds_dvvt: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`, data),
  // UR2.6.019_002->UR2.5.010_009
  lay_ds_nhanvien_thuchien_theo_dv: (axios, data) => axios.post(`/web-baohong/khaibaotsl/sp_lay_ds_nhanvien_thuchien_theo_dv`, data),
  // UR2.6.019_003
  lay_ds_phieu_giaoden_ims: (axios, data) => axios.post(`/web-ccdv/khaibaoims/lay_ds_phieu_giaoden_ims`, data),
  // UR2.6.019_004->UR2.6.042_008_001
  lay_ds_hopdong_huonggiao: (axios, data) => axios.post(`/web-thicong/hths-thaydoi-tocdo/lay_ds_hopdong_huonggiao`, data),
  // UR2.6.019_006 -> UR2.5.015_010_001 Lấy danh sách thao tác theo mã luồng
  lay_luong_thaotac: (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_lay_luong_thaotac`, data),
  // UR2.6.019_007 ->UR2.5.012_006 Lấy danh sách control thao tác theo id thao tác
  lay_ct_thaotac_control: (axios, data) => axios.post(`/web-thicong/hoancongmegawan/sp_lay_ct_thaotac_control`, data),
  // UR2.6.019_008 ->UR2.8.003_004 Lấy danh mục loại hình thuê bao loại dịch vụ viễn thông
  lay_ds_loaihinh_tb: (axios, data) => axios.get(`/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`, data),
  // UR2.6.019_009 ->UR2.4.063_005 Lấy danh sách combobox Quy trình theo Loại HĐ
  lay_ds_quytrinh: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_quytrinh_v2`, data),
  // UR2.6.019_010->UR2.4.083_012 Hiện thị danh sách combo đơn vị
  lay_ds_donvi_theo_loaidv: (axios, data) => axios.post(`/web-thicong/thanhly-thuebao/sp_lay_ds_donvi_theo_loaidv`, data),
  // UR2.6.019_011 ->UR2.4.090_004 Lấy thông tin hướng giao
  lay_ds_ds_huonggiao: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${data.quytrinh_id}/${data.dstthd_id}`, data),
  // UR2.6.019_012 ->UR2.6.019_004 Lấy danh sách hợp đồng theo mã hướng giao
  // lay_ds_ds_huonggiao: (axios, data) => axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${data.quytrinh_id}/${data.dstthd_id}`, data),
  // UR2.6.019_013 ->UR2.6.042_008_001
  // lay_ds_hopdong_huonggiao: (axios, data) => axios.get(`/web-thicong/hths-thaydoi-tocdo/lay_ds_hopdong_huonggiao`, data),
  // UR2.6.019_014
  map_id: (axios, data) => axios.post(`/web-hopdong/thanhly/thanh_ly_map_id`, data),
  fn_tt_sone: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_sone`, data),
  fn_tt_hdtb_cd: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hdtb_cd`, data),
  fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data),
  fn_tt_db_thanhtoan: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thanhtoan`, data),
  fn_tt_db_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_db_thuebao`, data),
  fn_tt_huonggiao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_huonggiao`, data),
  fn_tt_loaihinh_tb: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb`, data),
  // UR2.6.019_015 UR2.5.015_011_009 Lấy thông tin id HĐ đơn vị
  lay_ds_hdtb_dv: (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_lay_ds_hdtb_dv`, data),
  // UR2.6.019_016 ->UR2.5.015_011_001 ->Lấy danh sách nhân viên theo mã phiếu ( chưa có api)
  lay_ds_nhanvien_theo_phieu_id: (axios, data) => axios.post(`/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id`, data),
  // UR2.6.019_016A Lấy thông tin host
  lay_thongtin_host: (axios, data) => axios.get(`/web-ccdv/hoanthien_hoso_doiso_account/lay_thongtin_host?hdtbId=${data.hdtbId}&kieu=${data.kieu}`),
  // UR2.6.019_019->UR2.5.015_011_009
  // trung 015 lay_ds_hdtb_dv: (axios, data) => axios.get(`/web-ccdv/khaibaoims/sp_lay_ds_hdtb_dv`, data),
  // UR2.6.019_20
  lay_thongtin_ne_theo_tramvt: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_lay_thongtin_ne_theo_tramvt`, data),
  // UR2.6.019_21
  capnhat_sone_fn: (axios, data) => axios.post(`/web-hopdong/taosone/fn_capnhat_sone`, data),
  capnhat_sone: (axios, data) => axios.post(`/web-hopdong/hopdong/capnhat_sone`, data),
  // UR2.6.019_023->UR2.6.042_013_003 Lấy danh sách đơn vị giao phiếu
  lay_donvi_giaophieu: (axios, data) => axios.post(`web-thicong/hths-thaydoi-tocdo/sp_lay_donvi_giaophieu`, data),
  // UR2.6.019_024->UR2.6.042_013_004 Kiểm tra phiếu đã giao
  fn_kiemtra_phieu_dagiao: (axios, data) => axios.post(`web-thicong/hths-thaydoi-tocdo/fn_kiemtra_phieu_dagiao`, data),
  // UR2.6.019_025 ->UR2.4.039_029
  kt_taodb_thang: (axios, data) => axios.post(`/web-hopdong/thaydoiloaihinhtb/kt_taodb_thang`, data),
  // UR2.6.019_026 ->UR2.5.015_015_015
  capnhat_ngayht: (axios, data) => axios.post(`/web-hopdong/hopdong/fn_capnhat_ngayht`, data),
  // UR2.6.019_027
  fn_capnhat_ngayht: (axios, data) => axios.post(`/web-hopdong/hopdong/fn_capnhat_ngayht`, data),
  // UR2.6.019_028 ->UR2.6.042_013_007
  capnhat_tt_phieu_hc: (axios, data) => axios.post(`/web-thicong/hths-thaydoi-tocdo/capnhat_tt_phieu_hc`, data),
  // UR2.6.019_029 ->UR2.6.042_013_008
  hoanthanh_hoancong: (axios, data) => axios.post(`/web-thicong/hths-thaydoi-tocdo/hoanthanh_hoancong`, data),
  // UR2.6.019_030 ->UR2.6.042_013_008
  giaophieu_td_khi_hc: (axios, data) => axios.post(`/web-thicong/hths-thaydoi-tocdo/giaophieu_td_khi_hc`, data),
  // UR2.6.019_031 Hoàn thiện HS thay đổi Mã TB
  hoanthien_hs_thaydoi_matb: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/hoanthien_hs_thaydoi_matb`, data),
  // UR2.6.019_032 Cập nhật trạng thái phiếu Gphone
  capnhat_tt_phieu_gphone: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_capnhat_tt_phieu_gphone`, data),
  // UR2.6.019_033->UR2.13.007_001 Lấy ds tham số MĐ theo Mã TS
  lay_ds_thamso_md: (axios, data) => axios.get(`/web-quantri/diaban_nvkd/lay_ds_thamso`, data),
  // UR2.6.019_034->UR2.6.022_031 Lấy mã tb, neo id
  lay_matb_neo_id_theo_hdtb: (axios, data) => axios.post(`/web-ccdv/ht_hoso_thaydoidichvu/sp_lay_matb_neo_id_theo_hdtb`, data),
  // UR2.6.019_035->UR2.6.022_031 Lấy thông tin thuê bao
  get_db_thuebao_by_id: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/get_db_thuebao_by_id`, data),
  // UR2.6.019_036->Kiểm tra TB sử dụng DVGT
  kiemtra_tb_sd_dvgt: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_kiemtra_tb_sd_dvgt`, data),
  // UR2.6.019_037->Cập nhật ngày kích hoạt
  capnhat_ngay_kichhoat: (axios, data) => axios.post(`/web-ccdv/khaibaoims/capnhat_ngaykh_status_v2`, data),
  // UR2.6.019_038->Kiểm tra tạo bảng
  // kt_taodb_thang: (axios, data) => axios.post(`/web-hopdong/thaydoiloaihinhtb/kt_taodb_thang`, data),
  // UR2.6.019_039->UR2.5.015_015_015 Cập nhật ngày hoàn thành
  // Chua co API
  // UR2.6.019_040->UR2.5.015_015_005 Cập nhật ngày kích hoạt
  // -->UR2.6.019_037
  // UR2.6.019_041->UR2.8.031_001 Tra cứu thuê bao cố định trên tổng đài
  tracuu_tb_co_dinh: (axios, data) => axios.get(`/web-tracuu/tbcd/tb_co_dinh?maThueBao=${data.maThueBao}`),
  // UR2.6.019_042->Cập nhật trạng thái NE
  capnhat_trangthai_ne: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_capnhat_trangthai_ne`, data),
  // UR2.6.019_043->Lấy NE cũ, NE mới
  lay_ne_cu_ne_moi: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_lay_ne_cu_ne_moi`, data),
  // UR2.6.019_044->Kiểm tra thuê bao sử dụng NE
  fn_kt_thuebao_sd_ne: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/fn_kt_thuebao_sd_ne`, data),
  // UR2.6.019_045->UR2.8.031_001 Tra cứu thuê bao cố định trên tổng đài
  tb_co_dinh: (axios, data) => axios.post(`/web-tracuu/tbcd/tb_co_dinh?maThueBao=${data.maThueBao}`, data),
  // UR2.6.019_046 ->UR2.4.115_014 Lấy DS sử dụng DVGT
  // =>Map sai ,ham
  //
  // UR2.6.019_047->UR2.3.013 Mở form tra cứu hợp đồng
  // #########################
  // UR2.6.019_048->UR2.6.042_008_001 Mở form tra cứu hợp đồng
  // ############### da có
  // UR2.6.019_049->Xóa giao phiếu NV
  delete_giaophieu_nv: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_delete_giaophieu_nv`, data),
  // UR2.6.019_050->Thêm giao phiếu NV
  insert_giaophieu_nv: (axios, data) => axios.post(`/web-ccdv/hoanthien_hoso_doiso_account/sp_insert_giaophieu_nv`, data),
  // UR2.6.019_051->Cập nhật thông tin giao việc
  update_tt_giaoviec: (axios, data) => axios.post(`/web-ccdv/khaibaoims/sp_update_tt_giaoviec`, data),
  // form Ne
  ht_donvi_ne: (axios, data) => axios.post(`/web-hopdong/tachnhapthuebao/sp_ht_donvi_ne`, data),
  getDsVetinh: (axios, data) => axios.post('/web-ccdv/khaibaocodinh/sp_lay_ds_donvi_ql_ldv', data),
  // UR2.6.019_055 ->UR2.5.015_017_001
  lay_ds_phieuvt_hdbh: (axios, data) => axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_phieuvt_hdbh', data),
  lay_huonggiao_theo_luong_id: (axios, data) => axios.post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', data),
  // new api
  lay_dulieu_bang_theo_dieukien: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  lay_ds_loaihinh_tb_theo_dvvt_id: async (axios, data) => axios.get(`/web-bancheo/thaydoimegawan/lay_ds_loaihinh_tb_theo_dvvt_id?dvvtId=${data.dvvtId}`),
  lay_hdtb_thaydoi_dv_hc_theo_dv: async (axios, data) => axios.post('/web-ccdv/ht_hoso_thaydoidichvu/lay_hdtb_thaydoi_dv_hc_theo_dv', data),
  lay_ds_hopdong_huonggiao_theo_dv: async (axios, data) => axios.post('/web-thicong/thanhly-thuebao/lay_ds_hopdong_huonggiao_theo_dv', data),
  fn_hthsdoisothuebao_hienthidanhsach: async (axios, data) => axios.post('/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_hienthidanhsach', data),
  fn_hthsdoisothuebao_load: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_load`, data),
  fn_hthsdoisothuebao_capnhat: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_capnhat`, data),
  fn_hthsdoisothuebao_hoancong: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_hoancong`, data),
  fn_hthsdoisothuebao_chuyendb: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_chuyendb`, data),
  fn_hthsdoisothuebao_kichhoat: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_kichhoat`, data),
  fn_hthsdoisothuebao_chonne_tudong: (axios, data) => axios.post(`/web-ccdv/hoan_thien_ho_so_doi_so/fn_hthsdoisothuebao_chonne_tudong`, data)
}
