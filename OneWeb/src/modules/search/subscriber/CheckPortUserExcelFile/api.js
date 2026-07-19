export default{
    post_lay_port_theomatb: async (axios, data) => axios.post('/web-ccdv/TestPortV2/lay_port_theomatb', data),
    post_lay_ts_dokiem_theo_tinh: async (axios, data) => axios.post('/web-ccdv/TestPortV2/lay_ts_dokiem_theo_tinh', data),
    post_lay_ds_server_dokiem:async (axios, data) => axios.post('/web-ccdv/TestPortV2/lay_ds_server_dokiem', data),
    // post_lay_ds_thamso_md_mats:async (axios,data) => axios.post('/web-ccdv/TestPortV2/lay_ds_thamso_md_mats', data),
    post_lay_ds_thamso_md_mats:async (axios,data) => axios.post('/web-ccdv/tram-thicong-dv-cntt/lay_ds_thamso_md_mats', data),
    post_do_kiem: async (axios,data)=>axios.post('/app-cskh/TestPortV2/dokiem', data),
  }