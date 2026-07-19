export default {
  list_table_cache: (axios) => axios.get('/web-hopdong/cacheService/list_table_cache'),
  delete_cache_name_app: async (axios, data) => axios.post('/web-hopdong/cacheService/delete_cache_name_app', data),
}
