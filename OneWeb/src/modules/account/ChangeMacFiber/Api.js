
// vttho36553
// https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=537265640&range=A487:E487
export default {
  getDmDvVT: (axios, data) => axios.get(`/web-tracuu/tracu-danhba/ds_lhtb_dv/${data.dichvuvt_id}`),
  capNhatAdsl_Old: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/insert_db_adsl`, data),
  capNhatAdsl: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/sp_insert_tdtt_db_adsl_one`, data),
  capNhatMac: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/update_mac`, data),
  capNhatMacHDTB: (axios, data) => axios.post(`/web-hopdong//tracu-danhba/sp_capnhat_diachimac_hdtb`, data),
  changeMac: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/change_mac`, data),
  changeMac2: (axios, data) => axios.post(`/tichhop/visa-com/changeMacAddress`, data),
  capNhat: (axios, data) => axios.post(`/web-quantri/phanquyenhths/sp_ghilai_thamso_md_gan_dv`, data),
  getDsThueBaoAdsl: (axios, data) => axios.get(`/web-tracuu/tracu-danhba/ds_adsl_tb/${data.thuebao_id}`),
  getDsHdThueBaoThayDoiMac: (axios, data) => axios.get(`/web-tracuu/tracu-danhba/ds_hdtb_thaydoi_mac/${data.ma_tb}`),
  getDanhBaTheoMaTb: (axios, data) => axios.post(`/web-tracuu/tracu-danhba/LAY_DANHBA_THEO_MATB`, data),
  getKey: (axios, data) => axios.post(`/web-quantri/donvitinh/get_keys`, data)
}
