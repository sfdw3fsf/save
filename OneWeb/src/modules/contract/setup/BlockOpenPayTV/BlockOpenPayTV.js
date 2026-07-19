export default {
    ur_21054_001_getStatusBlockPayTV: async (axios, data) => axios.post('/tichhop/vasc/getStatusBlockPayTV', data),
    ur_21054_002_01_blockPayTV: async (axios, data) => axios.post('/tichhop/vasc/blockPayTV', data),
    ur_21054_002_02_capnhat_log_td_mytv: async (axios, data) => axios.post('/web-ccdv/khoamo_paytv/capnhat_log_td_mytv', data),
    ur_21054_004_tracuu_ls_khoamo_paytv: async (axios, data) => axios.post('/web-ccdv/khoamo_paytv/tracuu_ls_khoamo_paytv', data),

  }
  