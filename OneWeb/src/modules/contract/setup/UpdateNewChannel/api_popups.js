// https://api-onebss-dev.vnptit3.vn/proxy/swagger-ui.html?urls.primaryName=web-hopdong#/UR2.12.017

import axios from "axios";




export default {
  /*
  {
    "kieu": 1,
    "metaClass": {}
  }
  */
  lay_ds_kenh_trang: (axios, data) => axios.post("/web-hopdong/dongbokenhtrang/lay_ds_kenh_trang", data),

  /*
  {
    "hdtb_id": 0,
    "may_cn": 0,
    "metaClass": {},
    "nguoi_cn": 0,
    "nhanvien_id": 0
  }
   */
  capnhat_thoaitra_kenhtrang: (axios, data) => axios.post("/web-hopdong/dongbokenhtrang/capnhat_thoaitra_kenhtrang", data),


  /**
   *
   * Form "Thông tin kênh trắng"
   *
   */

  /**
   * 1. sự kiện form load
   */

  // UR4.1.001_frmTTKenhTrang_002 quốc tịch
  danhmuc_quoctich: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_QUOCTICH"),

  // UR4.1.001_frmTTKenhTrang_003 loại giấy tờ
  danhmuc_loai_giayto: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_LOAI_GT"),

  // UR4.1.001_frmTTKenhTrang_004 dân tộc
  danhmuc_dantoc: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_DANTOC"),

  // UR4.1.001_frmTTKenhTrang_005 dịch vụ VT
  danhmuc_dichvu_vt: async (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_DICHVU_VT"),

  // UR4.1.001_frmTTKenhTrang_006 đối tượng
  danhmuc_doituong: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_DOITUONG"),

  // UR4.1.001_frmTTKenhTrang_007 loại khách hàng
  danhmuc_loaikhachhang: (axios, data) => axios.get("web-quantri//danhmuc-chung/CSS_LOAI_KH"),

  // UR4.1.001_frmTTKenhTrang_008 loại kênh
  danhmuc_loaikenh: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_LOAI_KENH"),

  // UR4.1.001_frmTTKenhTrang_009 tốc độ CIR
  // TODO
  danhmuc_tocdocir: (axios, data) => axios.get("/web-quantri/quan-ly-danh-ba/lay-danhmuc/TOCDO_KENH"),

  // UR4.1.001_frmTTKenhTrang_010 tốc độ PIR
  // TODO
  danhmuc_tocdocpir: (axios, data) => axios.get("/web-quantri/quan-ly-danh-ba/lay-danhmuc/TOCDO_KENH"),

  // UR4.1.001_frmTTKenhTrang_011 modem
  danhmuc_modem: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_LOAI_MODEM"),

  // UR4.1.001_frmTTKenhTrang_012 loại thiết bị
  // TODO

  danhmuc_loaithietbi: (axios, data) => axios.get("web-quantri/danhmuc-chung/QLVT_LOAI_TBI"),

  // UR4.1.001_frmTTKenhTrang_013 loại kênh -> 008

  // UR4.1.001_frmTTKenhTrang_014 tốc độ kênh -> 009

  // UR4.1.001_frmTTKenhTrang_015 loại node -> hardcoded

  // UR4.1.001_frmTTKenhTrang_016 chủ quản
  // UR4.1.001_frmTTKenhTrang_017 chủ quản wan -> 016
  danhmuc_chuquan: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_CHUQUAN"),


  // UR4.1.001_frmTTKenhTrang_018 thời hạn
  // UR4.1.001_frmTTKenhTrang_019 thời hạn wan -> 018
  danhmuc_thoihan: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_THOIHAN"),

  // UR4.1.001_frmTTKenhTrang_020 trang bị
  // UR4.1.001_frmTTKenhTrang_021 trang bị wan -> 020
  danhmuc_trangbi: (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_TRANGBI"),


  // UR4.1.001_frmTTKenhTrang_022 loại thiết bị đầu
  // TODO

  // UR4.1.001_frmTTKenhTrang_023 loại thiết bị cuối
  // TODO

  // UR4.1.001_frmTTKenhTrang_024 loại cáp
  // UR4.1.001_frmTTKenhTrang_025 loại cáp đầu
  // UR4.1.001_frmTTKenhTrang_026 loại cáp cuối
  danhmuc_loaicap: (axios, data) => axios.get("web-quantri/danhmuc-chung/CABMAN_LOAI_CAP"),

  // UR4.1.001_frmTTKenhTrang_027 tham số mặc định
  // TODO

  // UR4.1.001_frmTTKenhTrang_028 Lấy _donviql_id, tuyenthu_id
  // TODO

  // UR4.1.001_frmTTKenhTrang_029 SinhMaDVVT
  // TODO

  /**
   * 2.Danh sách hiển thị
   */

  // UR4.1.001_frmTTKenhTrang_030 danh sách thuê bao
  // TODO

  /**
   * 3.Sự kiện liên quan text, combo, check...
   */

  // UR4.1.001_frmTTKenhTrang_031 txtMaKH_KeyPress
  // TODO

  // UR4.1.001_frmTTKenhTrang_032 HienThiTT_DBKH
  // TODO

  // UR4.1.001_frmTTKenhTrang_033 dịch vụ VT cboDichVuVT_SelectedValueChanged
  // sau đó FE xử lý đoạn where "dichvuvt_id = " + dichvuvt_id.ToString()
  danhmuc_loaihinh_tb: (axios, data) => axios.get(`web-quantri/danhmuc-chung/get_loai_hinh_tb_by_dichvuvt_id/${data}`),

  // UR4.1.001_frmTTKenhTrang_034 cboNganhNgheLoc_EditValueChanged -> 049

  /**
   * 4.Sự kiện liên quan lưới grid
   */

  // UR4.1.001_frmTTKenhTrang_035 gridviewThueBao_FocusedRowChanged
  // TODO

  // UR4.1.001_frmTTKenhTrang_036 Click dấu ... bên phải label Mã KH - bntLayMaKh_Click -> form tra cứu danh bạ

  // UR4.1.001_frmTTKenhTrang_037 Click nút chấp nhận - tsbtnCapNhan_Click
  // TODO

  // UR4.1.001_frmTTKenhTrang_038	CapNhat()	Click nút cập nhật - tsbtnCapNhatDoThu_Click
  // TODO

  // UR4.1.001_frmTTKenhTrang_039 TaoDuLieu_DBKH
  // TODO:

  // UR4.1.001_frmTTKenhTrang_040	TaoDuLieu_DBTT
  // TODO

  // UR4.1.001_frmTTKenhTrang_041	TaoDuLieu_DBTB
  // TODO

  // UR4.1.001_frmTTKenhTrang_042	TaoDuLieu_DiaChi
  // TODO

  // UR4.1.001_frmTTKenhTrang_043	TaoDuLieu_DB_TSL
  // TODO

  // UR4.1.001_frmTTKenhTrang_044	TaoDuLieu_DBTB_MGWAN
  // TODO

  // UR4.1.001_frmTTKenhTrang_045 CAPNHAT_THUEBAO_ID_DBTB_KV

  /* TODO
    "private void CAPNHAT_THUEBAO_ID_DBTB_KV()
    {
        for (int i = 0; i < dsHDTB_KV.DBTB_KV.Rows.Count; i++)
        {
            dsHDTB_KV.DBTB_KV.Rows[i][""THUEBAO_ID""] = thuebao_id;
        }
    }"
    Đây là dataset, FE thực hiện tạo dữ liệu như trên code, sau đó convert sang dạng json để truyền vào hàm
   */

  // UR4.1.001_frmTTKenhTrang_046	btnDiaChiLD_Click	Click dấu ... bên phải label địa chỉ LĐ - btnDiaChiLD_Click
  // Chuyển sang menu frmDiaChi

  // UR4.1.001_frmTTKenhTrang_047	btnChonDCCuoi_Click
  // TODO

  // UR4.1.001_frmTTKenhTrang_048	btnDiaChiKH_Click	Click dấu ... bên phải label địa chỉ KH - btnDiaChiKH_Click
  // Chuyển sang menu frmDiaChi

  // UR4.1.001_frmTTKenhTrang_049	Click dấu ... bên phải label ngành nghề - btnNganhNghe_Click
  // Chuyển sang menu frmChiTietNganhNghe
  danhmuc_nganhnghe: async (axios, data) => axios.get("web-quantri/danhmuc-chung/CSS_NGANHNGHE"),

  // UR4.1.001_frmTTKenhTrang_050	Click nút xóa phiếu - tsbtnXoaPhieu_Click
  // TODO

  // UR4.1.001_frmTTKenhTrang_051	Click dấu ... bên phải label địa chỉ TB - btnDiaChiTB_Click -> 046

  // UR4.1.001_frmTTKenhTrang_052	Click dấu ... bên phải label khu vực - btnMaNVKD_Click
  // frmTC_DB_KhuVuc obj = new frmTC_DB_KhuVuc(dsHDTB_KV);
  // Chuyển sang menu frmTC_DB_KhuVuc

  // UR4.1.001_frmTTKenhTrang_053	Click dấu ... bên phải label địa chỉ kn - btnDiaChi_KN_Click
  // Chuyển sang menu frmDiaChi

  // UR4.1.001_frmTTKenhTrang_054	Click btnHuongKNWan_Click
  // Chuyển sang menu frmDSHuongKN

  // UR4.1.001_frmTTKenhTrang_055	Click dấu ... bên phải label số ảo - btnSoAoWan_Click
  // frmChonSMPhuong f = new frmChonSMPhuong(quanld_id, phuongld_id, LOAISO.AO, false);
  // Chuyển sang menu frmChonSMPhuong

  // UR4.1.001_frmTTKenhTrang_056	Click btnSoAoTSL_Dau_Click
  // frmChonSMPhuong f = new frmChonSMPhuong(quanld_id, phuongld_id, LOAISO.AO, false);
  // Chuyển sang menu frmChonSMPhuong

  // UR4.1.001_frmTTKenhTrang_057	Click btnSoAoTSL_Cuoi_Click
  // frmChonSMPhuong f = new frmChonSMPhuong(quanld_id, phuongld_id, LOAISO.AO, false);
  // Chuyển sang menu frmChonSMPhuong

  // UR4.1.001_frmTTKenhTrang_058	Click dấu ... bên phải label mã TN - btnMaTN_Wan_Click
  // TODO

    kiemtra_thangthai_makh: (axios, data) => axios.post("web-hopdong/thong-tin-kenh-trang/kiemtra_makh", data),

    //tao_khachhangid: (axios, data) => axios.post("/webhopdong/thong-tin-kenh-trang/CHUA_CO", data),
    tao_makhachhang: (axios, data) => axios.post("/web-hopdong/thong-tin-kenh-trang/sinh_makh", data),
    tao_mahopdong: (axios, data) => axios.post("web-hopdong/thong-tin-kenh-trang/sinh_mahd", data),

    // Bo sung 16/04/2022
    // lay_tuyenThuId: (axios, data) => axios.post("web-hopdong/thong-tin-kenh-trang/fn_map_id", data),
    // lay_donViQuanLyId: (axios, data) => axios.post("web-hopdong/thong-tin-kenh-trang/fn_map_id", data),

    // Fix atbm 09/08/2022
    lay_tuyenThuId: (axios, data) => axios.post("web-tratruoc/thongtin_tratruoc_dn/fn_tt_tuyenthu", data),

    lay_donViQuanLyId: (axios, data) => axios.post("web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien", data),

    lay_thamSoMacDinh: (axios, data) => axios.post("web-hopdong/laydulieu/sp_tt_thamso_md", data),

    get_key: async (axios, data) => axios.post("web-ccdv/tien_trinh_bao_hong/get_keys", data),

    lay_ds_db_tsl_theo_tbid: (axios, thuebao_id) => axios.get(`web-hopdong/chuyendich-tsl/lay_ds_db_tsl_theo_tbid?thuebao_id=${thuebao_id}`),

    lay_thongtin_tb_theo_tb_id: (axios, data) => axios.get(`web-hopdong/thong-tin-kenh-trang/lay_thongtin_tb_theo_tb_id?thuebao_id`, data),

    lay_ds_dbtb_theo_khid_v2: (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/lay_ds_dbtb_theo_khid_v2`, data),

    log_ghep_khdn_thong_tin_kenh_trang: (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/sp_log_ghep_khdn`, data),

    lay_thongtin_tb_theo_tb_id:  (axios, data) => axios.get(`web-hopdong/thong-tin-kenh-trang/lay_thongtin_tb_theo_tb_id`, data),

    sinh_matt_v2: (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/sinh_matt_v2`, data),

    sinh_ma_kh: (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/sinh_makh`, data),

    kiemtra_dieukien_khac: (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/kiemtra_dieukien_khac`, data),

    lay_danhmuc_lapmoi_tt_chung_v7: async (axios, chuoi) => axios.post('/web-ccdv/tiepnhan_yc_ks_lapdat/lay_danhmuc_lapmoi_tt_chung_v7',{
      chuoi:chuoi
    }),

    post_fn_lay_tt_taodl_diachi : async (axios, data) => axios.post('/web-hopdong/lapdatmoi/fn_lay_tt_taodl_diachi',data),

    fn_capnhat_tt_kenhtrang: async (axios, data) => axios.post(`web-hopdong/thong-tin-kenh-trang/fn_capnhat_tt_kenhtrang`, data),



    sp_lay_tinh_tc_theo_tinh_id: async (axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/sp_lay_tinh_tc_theo_tinh_id',data),

    sinh_matb_svvt: async (axios, data) => axios.post('web-bancheo/tracuu/sinh_matb',data),

    lay_ds_db_tsl_theo_tbid: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_ds_db_tsl_theo_tbid', data),

    sinh_ma_lt: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/sinh_malt', data),

    lay_ds_donviql: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_ds_donviql', data),

    lay_thongtin_kh_theo_ma_kh: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_ma_kh', data),
    lay_thongtin_kh_theo_ma_tt: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_ma_tt', data),
    lay_thongtin_kh_theo_ma_tb: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_ma_tb', data),
    lay_thongtin_kh_theo_soao: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_soao', data),
    lay_thongtin_kh_theo_ma_tb_neo: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_thongtin_kh_theo_ma_tb_neo', data),

    lay_ds_db_megawan_theo_tbid: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_ds_db_megawan_theo_tbid', data),

    sp_ht_tocdo_kenh_combobox: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/sp_ht_tocdo_kenh_combobox', data),

    kiemtra_matb_khoso_ao: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/kiemtra_matb_khoso_ao', data),

    lay_ds_dbtt_theo_ttid: async(axios, data) => axios.post('web-hopdong/thong-tin-kenh-trang/lay_ds_dbtt_theo_ttid', data),

    get_keys: async (axios, keyname) => axios.post('/web-hopdong/khoiphucthanhly/get_keys',{
      keyname: keyname
  }),
};
