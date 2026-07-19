export default{
  // form chính:
  layds_KQduyet: (axios, data) => axios.post('web-tratruoc/ob_ghtt/sp_lay_ds_ketqua_duyet'),
  layds_PhieuYC: (axios, data) => axios.post('web-tratruoc/ob_ghtt/lay_ds_duyet_yc_khoamay', data),
  ghilai_DuyetYC: (axios, data) => axios.post('web-tratruoc/ob_ghtt/duyet_yc_khoamay_thuebao', data),
  // popup xem chi tiết trạng thái sử dụng dịch vụ:
  lay_port_theoMATB: (axios, data) => axios.post('web-hopdong/TestPortV2/lay_port_theomatb', data),
  lay_trangthai_SDDV_fiber: async (axios, data) => axios.get(`/tichhop/ctsnewpost/getOnuTrafficDetail?deviceip=${data.deviceip}&slot=${data.slot}&port=${data.port}&vpi=${data.vpi}&frame=${data.frame}&fromTime=${data.fromTime}&toTime=${data.toTime}`),
}
  