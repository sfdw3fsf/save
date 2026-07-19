// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=698575146&range=B536:C536
// WinUIQLDHXLSuCo.frmChuyenTo f = new WinUIQLDHXLSuCo.frmChuyenTo();
export default {
  lay_ds_donvi_ql: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_ql`, data),
  lay_ds_tramvt: (axios, data) => axios.post(`/web-hopdong/tachnhapthuebao/sp_ht_donvi_loaidv_combobox`, data),
  lay_ds_donvi_chuyento_baohong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_chuyento_baohong`, data),
  lay_ds_phieu_bh_lachuong: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_ds_phieu_bh_lachuong`, data),
  lay_tt_baohong_chuyento_chung: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_tt_baohong_chuyento_chung`, data),
  hienthi_ds_phieuth_chuyento: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/hienthi_ds_phieuth_chuyento`, data),
  capnhat_chuyento: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/an_capnhat_chuyento`, data),
  lay_ds_baohong_theo_ma_tb: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/sp_lay_ds_baohong_theo_ma_tb`, data),
  lay_danhsach_phieuth_bh: (axios, data) => axios.post(`/web-tracuu/tracuubaohong/lay_danhsach_phieuth_bh_v2`, data),
  lay_ds_thamso_md: (axios, data) => axios.get(`/web-quantri/diaban_nvkd/lay_ds_thamso`, data),
  lay_dvcha_theo_dvcon: (axios, data) => axios.post(`/web-thicong/hoinghi_truyenhinh/lay_dvcha_theo_dvcon`, data),
  LAY_DANHBA_THEO_MATB: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB`, data)
}
