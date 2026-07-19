export default {
    
    Fill_Info: (axios, data) => axios.post('/web-ccdv/capnhat_loai_thietbi/sp_getpayment_infor', data),
    GetMa_TT_Neo: (axios, data) => axios.post('/web-ccdv/capnhat_loai_thietbi/lay_matt_neo_vnp', data),
    Ts_tracuu_ls_notra: (axios, data) => axios.post('/ccbs/executor/ts_tracuu_ls_notra', data),
    Ts_layds_chukyno: (axios, data) => axios.post('/ccbs/executor/ts_layds_chukyno', data),
    
    
    
}
  