export default {
  GetListProvince: async (axios, data) => axios.get('/tichhop/bnm/getListProvince'),
  GetListConfig: async (axios, data) => axios.get('tichhop/bnm/getListConfig'),
  GetListVendor: async (axios, data) => axios.get('/tichhop/bnm/getListVendor'),
  GetListService: async (axios, data) => axios.get('/tichhop/bnm/getListService'),
  getListLogScript: async (axios, data) => axios.get(`/tichhop/bnm/getListLogScript?script_bnm_id=${data.script_bnm_id}&from_date=${data.from_date}&to_date=${data.to_date}`),
  GetDetailLogScript: async (axios, data) => axios.get(`/tichhop/bnm/getDetailLogScript?log_script_id=${data.log_script_id}`),
  GetListMessageQueue: async (axios, data) => axios.get(`/tichhop/bnm/getListMessageQueue?id_kich_ban=${data.id_kich_ban}`),
  GetDetailMessageQueue: async (axios, data) => axios.get(`/tichhop/bnm/getDetailMessageQueue?id_request=${data.id_request}`),
  getInfoScriptTempBNM: async	(axios, data) => axios.get(`/tichhop/bnm/getInfoScriptTempBnm?script_bnm_id=${data.script_bnm_id}`),
  getDetailLogScript: async	(axios, data) => axios.get(`/tichhop/bnm/getDetailLogScript?id=${data.id}`),// "id"
  // http://api-onebss.vnpt.vn/tichhop/bnm/getDetailLogScript?log_script_id=1
  GetListRequestBss: async	(axios, data) => axios.get(`/tichhop/bnm/GetListRequestBss?id=${data.id_kich_ban}`), //“id_kich_ban”:101
  GetDetailRequestBss : async	(axios, data) => axios.get(`/tichhop/bnm/GetDetailRequestBss?id=${data.id_request}`), // {“id_request”:101}
  GetListScriptTempBNM: async (axios, data) => axios.get(`/tichhop/bnm/getListScriptTempBNM?province_id=${data.province_id}&vendor_id=${data.vendor_id}&service_id=${data.service_id}&config_type=${data.config_type}`),
}

// http://api-onebss.vnpt.vn/tichhop/bnm/getDetailLogScript?log_script_id=
