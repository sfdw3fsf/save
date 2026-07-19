export default {
  GetDetailLogScript: async (axios, data) => axios.get(`/tichhop/bnm/getDetailLogScript?log_script_id=${data.log_script_id}`),
  GetListProvince: async (axios, data) => axios.get('/tichhop/bnm/getListProvince'),
  GetListConfig: async (axios, data) => axios.get('tichhop/bnm/getListConfig'),
  GetListVendor: async (axios, data) => axios.get('/tichhop/bnm/getListVendor'),
  GetListService: async (axios, data) => axios.get('/tichhop/bnm/getListService'),
  AddScriptTempBnm: async (axios, data) => axios.post('/tichhop/bnm/addScriptTempBnm',data),
  testScript: async (axios, data) => axios.post('/tichhop/bnm/testScript',data),
  getListParamName: async (axios, data) => axios.get('/tichhop/bnm/getListParamName'),
  getDetailScriptTempBNM: async (axios, data) => axios.get('/tichhop/bnm/getDetail,ScriptTempBNM'),
  updateScriptTempBnm: async (axios, data) => axios.post('/tichhop/bnm/updateScriptTempBnm'),
}