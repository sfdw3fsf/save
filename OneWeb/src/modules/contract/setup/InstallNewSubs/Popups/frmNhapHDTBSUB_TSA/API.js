export default{
  laydstinh: async (axios, data,header) => axios.post('tichhop/tsaservice/laydstinh', data,header),
  laydsquan: async (axios, data,header) => axios.post('tichhop/tsaservice/laydsquan', data,header),
  laydsphuong: async (axios, data,header) => axios.post('tichhop/tsaservice/laydsphuong', data,header),
}
