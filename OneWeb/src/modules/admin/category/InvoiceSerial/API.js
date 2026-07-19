export default{
    
  lay_ds_seri: async (axios, data) => axios.post('/web-quantri/serihoadon/ps_lay_ds_seri', null),
    
  lay_ds_kieuhd: async (axios, data) => axios.post('/web-quantri/serihoadon/ps_lay_ds_kieuhd', null),
    
  lay_ds_loaihd_seri: async (axios, data) => axios.post('/web-quantri/serihoadon/ps_lay_ds_loaihd_seri', data),

  insert_update_serihd: async (axios, data) => axios.post('/web-quantri/serihoadon/ps_insert_update_serihd', data),
   
  delete_serihd: async (axios, data) => axios.post('/web-quantri/serihoadon/ps_delete_serihd', data),
  
  get_key: async (axios, data) => axios.post('/web-hopdong/goi-da-dichvu/get_keys', data),

  }
  
  