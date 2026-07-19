export default{
  post_ThucThiLenh : async (axios, data) => axios.post('/web-ccdv/subman/ThucThiLenh', data),
  post_insert_log_tongdai : async (axios, data) => axios.post('/web-thicong/thicong/insert_log_tongdai', data),
}