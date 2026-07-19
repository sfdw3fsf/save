export default {
  lay_dvql: (axios) => axios.post('/web-quantri/danhmuc/donvi/theo_loai', {loai_donvi_id: 3}),
  lay_tramvt: (axios, data) => axios.post('/web-thicong/host/lay_ds_donvi_theo_dvcha_loaidv', data),
  capnhat_dvn_giaophieu: (axios, data) => axios.post('/web-thicong/host/capnhat_dvn_giaophieu', data)
}
