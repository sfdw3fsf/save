export default {
  // new DuLieuHTKHFacade().GetLineUser(ChucNang.Instance.Nhanvien_ID_Goc);
  sp_lay_tt_line_user: async (axios, input) => axios.get(`/web-baohong/tiepnhan_cuocgoi_tongdai/lay_tt_line_user?nhanvien_id=${input.nhanvien_id}`),

  // 2. DanhMuc = fcd.LayDSThamSo();
  sp_lay_ds_thamso: async (axios) => axios.get(`/web-baohong/tiepnhan_cuocgoi_tongdai/sp_laydsthamso`),

  // 3. btn_ChiTiet_CG_Click_LayLichSuCuocGoi
  sp_lay_LichSu_CuocGoi: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/layLichSuCuocGoi`, input),

  sp_LayDS_XuLy_CSKH: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/LayDS_XuLy_CSKH`, input), 

  // 4. lookUpUser_AccountAccepted >> LayDSThueBaoKH
  sp_lay_ds_thuebaokh: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/laydsthuebaokh`, { 
    vkhachhang_id: input.vkhachhang_id, 
    vma_dv: input.vma_dv
  }),

  // 5. gview_DanhSach_FocusedRowChanged >> tracuuCSKH.LayPhanLoaiKH(Convert.ToInt64(dr["THUEBAO_ID"]), Convert.ToInt32(dr["LOAITB_ID"]));
  sp_lay_PhanLoai_KH: async (axios, input) => axios.get(`/web-baohong/tiepnhan_cuocgoi_tongdai/LayPhanLoaiKH?vthuebao_id=${input.vthuebao_id}&vloaitb_id=${input.vloaitb_id}`),

  sp_lay_ds_kieu_tk: async (axios, nhom_id) => axios.get(`/web-tracuu/tracuutonghop/lay_ds_kieu_tk?nhom_id=${nhom_id}`), 
  
  // BusinessFacade.ChamSocKHFacade.CongViec_CSKH_Facade().TraCuuTongHop(thongtin_TimKiem)
  sp_CongViec_CSKH_TraCuu: async (axios, input) => axios.post(`/web-tracuu/tracuutonghop/CongViec_CSKH_TraCuu`, input),
  
  // mig_hni = 0/1
  sp_CSKH_18001166_TraCuu: async (axios, input) => axios.post(`/web-tracuu/tracuutonghop/CSKH_18001166_TraCuu?mig_hni=0`, input),

  // fcdLookUp.LayDSLienhe
  sp_lay_ds_lienhe: async (axios, input) => axios.post(`/web-tracuu/thongtin_cskh/lay_ds_lienhe`, input), 

  sp_hienthi_tt_kh_goiden: async (axios, input) => axios.post(`/web-tracuu/thongtin_cskh/hienthi_tt_kh_goiden`, input), 

  // frmChiTietCuocGoi
  // fcd.LayLoaiTV()
  sp_tt_loai_tuvan: async (axios, input) => axios.get(`/web-hopdong/laydulieu/sp_tt_loai_tuvan`), 

  sp_lay_ds_cuocgoi: async (axios, input) => axios.post(`/web-bancheo/tiepnhan_cuocgoi_tongdai/lay_ds_cuocgoi`, input), 

  // frmLienHeTapTrung
  // fcdLookUp.Lay_ThongTin_LienHe(txt_SDT_LienHe.Text)
  sp_lay_thongtin_lienhe: async (axios, sdt) => axios.get(`/web-tracuu/thongtin_cskh/lay_thongtin_lienhe?sdt=${sdt}`), 

  // fcdLookUp.LayDSThuebao_LienHe  
  sp_lay_ds_thuebao_lienhe: async (axios, lienhe_id) => axios.get(`/web-tracuu/thongtin_cskh/lay_ds_thuebao_lienhe?lienhe_id=${lienhe_id}`), 

  // fcdLookUp.ThemLienHe(lienhe);
  sp_them_lienhe: async (axios, input) => axios.post(`/web-tracuu/thongtin_cskh/them_lienhe`, input), 

  // fcdLookUp.ThemThueBao_LienHe(Current_ID, Convert.ToInt64(dr["thuebao_id"]), Convert.ToInt32(dr["tinh_id"]))
  sp_them_thuebao_lienhe: async (axios, input) => axios.post(`/web-tracuu/thongtin_cskh/them_thuebao_lienhe`, input), 

  // fcdLookUp.XoaThueBao_LienHe(Current_ID, Convert.ToInt64(dr["thuebao_id"]), Convert.ToInt32(dr["Tinh_ID"]))
  sp_xoa_thuebao_lienhe: async (axios, input) => axios.post(`/web-tracuu/thongtin_cskh/xoa_thuebao_lienhe`, input), 

  // 
  CSS_DICHVU_VT: async (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_DICHVU_VT`), 

  CSS_LOAIHINH_TB: async (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB`), 

  CSS_TINH: async (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_TINH`), 

  CSS_TRANGTHAI_TB: async (axios) => axios.get(`/web-quantri/danhmuc-chung/CSS_TRANGTHAI_TB`), 

  
  dctDSMaVung: () => ({
    "0296": "AGG", "0274": "BDG", "0256": "BDH", "0204": "BGG", 
    "0209": "BKN", "0291": "BLU", "0222": "BNH", "0271": "BPC", 
    "0275": "BTE", "0252": "BTN", "0206": "CBG", "0290": "CMU", 
    "0292": "CTO", "0215": "DBN", "0262": "DLC", "0236": "DNG", 
    "0251": "DNI", "0261": "DNO", "0277": "DTP", "0269": "GLI", 
    "0218": "HBH", "028": "HCM", "0220": "HDG", "0219": "HGG", 
    "0293": "HGI", "024": "HNI", "0226": "HNM", "0225": "HPG", 
    "0239": "HTH", "0234": "HUE", "0221": "HYN", "0297": "KGG", 
    "0258": "KHA", "0260": "KTM", "0272": "LAN", "0214": "LCI", 
    "0213": "LCU", "0263": "LDG", "0205": "LSN", "0238": "NAN", 
    "0229": "NBH", "0228": "NDH", "0259": "NTN", "0210": "PTO", 
    "0257": "PYN", "0232": "QBH", "0203": "QNH", "0255": "QNI", 
    "0235": "QNM", "0233": "QTI", "0212": "SLA", "0299": "STG", 
    "0227": "TBH", "0273": "TGG", "0237": "THA", "0276": "TNH", 
    "0208": "TNN", "0207": "TQG", "0294": "TVH", "0270": "VLG", 
    "0211": "VPC", "0254": "VTU", "0216": "YBI"
  }),

  ResourceDataTable: () => ({
    BaoHong: 1, 
    KhieuNai: 2, 
    DB_ThueBao: 10, 
    DB_KhachHang: 11, 
    DB_ThanhToan: 12, 
    LienHe_TapTrung: 200, 
    DB_CNTT: 201, 
    SubData: 255
  }),

  SearchType: () => ({
    Contain: 2,
    ExactlyMatch: 1,
    Like: 3,
    ExactlyMatchUpperCase: 4,
    ContainLowerCase: 5,
    ExactlyLowerCase: 6,
    StartWithLowerCase: 7
  })
}