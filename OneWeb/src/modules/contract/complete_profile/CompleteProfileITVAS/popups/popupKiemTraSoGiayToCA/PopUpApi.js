
export default {
      
    xem_ds_giayto_theo_magd_ca: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/xem_ds_giayto_theo_magd_ca`, data),
    sp_loadthongtinloaigt: (axios, data) => axios.get(`/web-ccdv/tram-thicong-dv-cntt/sp_loadthongtinloaigt`, data),
    
    capnhat_giayto_ca_hangloat: (axios, data) => axios.post(`/web-ccdv/tram-thicong-dv-cntt/capnhat_giayto_ca_hangloat`, data),
    
}