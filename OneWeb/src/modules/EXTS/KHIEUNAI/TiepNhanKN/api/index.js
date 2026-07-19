export default function (RequestContext) {
  return {
    getTTThueBao: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay-thongtin-tt-theo-matb-ht',
        {
          ...data
        }
      ),
    getTTThueBaoMaTT: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay-thongtin-tt-tb-theo-matt',
        {
          ...data
        }
      ),
    getDICHVU_VT: data =>
      RequestContext.get(
        '/web-gqkn/tiep-nhan-khieu-nai/getDsDichVuVT?DS_DICHVUVT_ID='
      ),
    getDANHMUC: data =>
      RequestContext.get('/web-cabman/ban-do-mang-cap/danh-muc', {
        loai: data.loai,
        option: data.option
      }),
    getLAY_DS_DBKH_THEO_KHID: data =>
      RequestContext.get(
        '/web-gqkn/tiep-nhan-khieu-nai/getDanhBaKHTheoKHID?KHACHHANG_ID=' +
          data.KHACHHANG_ID
      ),
    getCHUDEKN_THEO_LHTB: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/getDsChuDeKNTheoLoaiTB',
        {
          ...data
        }
      ),
    getCHUDEKN_THEO_GANQUYEN: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/getDsChuDeKNTheoGanQuyen',
        {
          ...data
        }
      ),
    getLAY_CHUDEKN_THEO_LHTB_PLKH_V2: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_chudekn_theo_lhtb_plkh_v2',
        {
          ...data
        }
      ),
    getDS_KHIEUNAI_HT: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/lay-ds-khieunai-ht', {
        ...data
      }),
    getCHUKYNO_THEOTB: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/lay-chukyno-theotb', {
        ...data
      }),
    getDS_TB_KHIEUNAI: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/lay-ds-tb-khieunai-v2', {
        ...data
      }),
    getDS_TB_KHIEUNAI_ID: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_ds_theo_khieunai_id',
        {
          ...data
        }
      ),
    getSYSDATE: data =>
      RequestContext.get('/web-gqkn/tiep-nhan-khieu-nai/getsysdate'),
    getLAYHUONGGIAO_TIEPNHAN_XLKN: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/layhuonggiao-tiepnhan-xlkn',
        {
          ...data
        }
      ),
    getTaoMaKN: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/taomakn', {
        ...data
      }),
    getDS_TIENGC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/nap_ds_tiengc', {
        ...data
      }),
    getXOA_KHIEUNAI: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/xoa_khieunai', {
        ...data
      }),
    getCHITIETNO_THEO_KHOANMUC: data =>
      RequestContext.get(
        '/web-thuno/api/quan-ly-thu-no/chi-tiet-no-theo-khoan-muc?dsMaTB=' +
          data.MA_TB +
          '&dsMaTT=' +
          data.MA_TT +
          '&kyCuoc=' +
          data.KYCUOC
      ),
    getHUONGGIAO_LDV: data =>
      RequestContext.get(
        '/web-gqkn/tiep-nhan-khieu-nai/huonggiao-ldv?QUYTRINH_ID=' +
          data.QUYTRINH_ID
      ),
    getLAY_DONVI_NGUOIDUNG_THEOBANG: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay-donvi-nguoidung-theobang',
        {
          ...data
        }
      ),
    getLAY_DONVI_NHAN_KHIEUNAI: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_donvi_nhan_khieunai',
        {
          ...data
        }
      ),
    setNHAP_KHIEUNAI_Insert_V2: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/nhap_khieunai_v2', {
        ...data
      }),
    setNHAP_KHIEUNAI_Insert_V3: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/nhap_khieunai_v3', {
        ...data
      }),
    setNHAP_KHIEUNAI_Insert_V4: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/nhap_khieunai_v4', {
        ...data
      }),
    setUPDATE_LYDO_KHONGXL_DUOC: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/update_lydo_khongxl_duoc',
        {
          ...data
        }
      ),
    setCAPNHAT_GIAMCUOC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/capnhat_giamcuoc', {
        ...data
      }),
    setSUA_KHIEUNAI_Update_V2: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/sua_khieunai_v2', {
        ...data
      }),
    getLAY_HG_GQKN: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/lay_hg_gqkn', {
        ...data
      }),
    setGIAOPHIEU_KN_KHI_HC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/giaophieu_kn_khi_hc', {
        ...data
      }),
    setUpdate_KhoaPhieu_BaoTon_HoanThanh: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/capnhat_baoton_hoanthanh',
        {
          ...data
        }
      ),
    setCAPNHAT_LOI: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/capnhat_loi', {
        ...data
      }),
    setUPDATE_TT_DAGIAO: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/update_tt_dagiao', {
        ...data
      }),
    setCAPNHAT_TIEPNHAN_KHOAPHIEU: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/capnhat_tiepnhan_khoaphieu',
        {
          ...data
        }
      ),
    setCAPNHAT_TT_PHIEUKN_HC: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/capnhat_tt_phieukn_hc',
        {
          ...data
        }
      ),
    setUPDATE_XULY: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/update_xuly', {
        ...data
      }),
    getLay_thongtin_kn_theo_ma_tb: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_thongtin_kn_theo_ma_tb',
        {
          ...data
        }
      ),
    getKIEMTRA_TT_MaTB: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/kiemtra_tt_matb', {
        ...data
      }),
    getDS_THANG_GC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/ds_thang_gc', {
        ...data
      }),
    getKY_APDUNG: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/ky_apdung', {
        ...data
      }),
    getKHIEUNAI_BS: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/khieunai_bs', {
        ...data
      }),
    getTAODULIEU_TIENGC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/taodulieu_tiengc', {
        ...data
      }),
    getDULIEU_TIENGC: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/napdulieu_tiengc', {
        ...data
      }),
    getHOSO_KHIEUNAI: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/get_hoso_khieunai', {
        ...data
      }),
    getHUONGGIAO_QUYTRINH: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_huonggiao_quytrinh',
        {
          ...data
        }
      ),
    getLAY_DS_HUONGGIAO: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/lay_ds_huonggiao', {
        ...data
      }),
    getDS_DIACHI_THEO_KHIEUNAI_ID: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_ds_diachi_theo_khieunai_id',
        {
          ...data
        }
      ),
    getLay_nguyennhan_kn_theo_chude: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_nguyennhan_kn_theo_chude',
        {
          ...data
        }
      ),
    getLay_phuongan_xl_theo_chude: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/lay_phuongan_xl_theo_chude',
        {
          ...data
        }
      ),
    setCapNhat_PAXL_KN: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/capnhat_paxl_kn', {
        ...data
      }),
      setUpdate_PhuongAn_XuLy: data =>
      RequestContext.post(
        '/web-gqkn/tiep-nhan-khieu-nai/capnhat_phuognan_xuly',
        {
          ...data
        }
      ),
    getDS_TB_KHIEUNAI_V2: data =>
      RequestContext.post('/web-gqkn/tiep-nhan-khieu-nai/ds-khieunai-theo-trangthai', {
        ...data
      }),           

    }
}
