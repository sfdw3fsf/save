export default {
  GetListProvince: async (axios, data) => axios.get('/tichhop/bnm/getListProvince'),
  GetListConfig: async (axios, data) => axios.get('tichhop/bnm/getListConfig'),
  GetListVendor: async (axios, data) => axios.get('/tichhop/bnm/getListVendor'),
  GetListService: async (axios, data) => axios.get('/tichhop/bnm/getListService'),
  GetListScriptTempBNM: async (axios, data) => axios.get(`/tichhop/bnm/getListScriptTempBNM?province_id=${data.province_id}&vendor_id=${data.vendor_id}&service_id=${data.service_id}&config_type=${data.config_type}`),
  
}