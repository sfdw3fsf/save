export default {
  sp_lay_tt_hd_phuluc_load: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_hd_phuluc_load', data),
  sp_lay_tt_thanhtoan_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thanhtoan_by_hdtb_id', data),
  sp_lay_tt_khachhang_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_by_hdtb_id', data),
  sp_lay_tt_khachhang_tien_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_khachhang_tien_by_hdtb_id', data),
  sp_lay_tien_hd_theo_hdkh_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tien_hd_theo_hdkh_id', data),
  sp_lay_tt_thuebao_chinh_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_chinh_by_hdtb_id', data),
  sp_lay_tt_thuebao_them_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_them_by_hdtb_id', data),
  sp_lay_tt_thuebao_kythuat_by_hdtb_id: async (axios, data) => axios.post('web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_kythuat_by_hdtb_id', data),
  sp_lay_tt_thuebao_khac_by_hdtb_id: async (axios, data) => axios.post('web-tracuu/tracuu_chitiet_thicong/sp_lay_tt_thuebao_khac_by_hdtb_id', data),
  sp_lay_ds_thuebao_donvi_by_hdtb_id: async (axios, data) => axios.post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_thuebao_donvi_by_hdtb_id', data),
  sp_lay_phieuhd_tt_theo_hdkh: async (axios, data) => axios.post('/web-tracuu/tracuu/sp_lay_phieuhd_tt_theo_hdkh', data),
  sp_lay_phieu_tt_theo_phieutt: async (axios, data) => axios.post('/web-tracuu/tracuu/sp_lay_phieu_tt_theo_phieutt', data),
  sp_lay_tt_hentt_theo_phieuttid: async (axios, data) => axios.post('/web-tracuu/tracuu/sp_lay_tt_hentt_theo_phieuttid', data),
  sp_lay_tt_km_tratruoc: async (axios, data) => axios.post('/web-tracuu/tracuu_th/lay_tt_km_tratruoc', data),
  sp_lay_tt_goi_dadv_hd: async (axios, data) => axios.post('/web-tracuu/tracuu_th/lay_tt_goi_dadv_hd', data),
  lay_ls_ob_ghtt_theo_hdkh: async (axios,data) => axios.post("/web-tratruoc/ob_ghtt/lay_ls_ob_ghtt_theo_hdkh",data)
}


