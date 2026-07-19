export default{
    
    lay_donvi_ql: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/sp_fmdonviql_lay_donvi_ql', null),
    
    lay_loai_dv: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/sp_fmdonviql_lay_loai_dv', null),
    
    lay_ds_donvi_ql: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/sp_fmdonviql_lay_ds_donvi_ql', data),

    lay_ds_donvi_dagan: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/sp_fmdonviql_lay_ds_donvi_dagan', data),
   
    lay_ds_donvi_chuagan: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/sp_fmdonviql_lay_ds_donvi_chuagan', data),

    capnhat: async (axios, data) => axios.post('/web-quantri/TramVTVeTinh/fn_fmdonviql_capnhat', data),
  }
  