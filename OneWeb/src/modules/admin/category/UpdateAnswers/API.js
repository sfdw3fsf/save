export default {
    getDSCauTraLoi: axios =>axios.get("/web-quantri/capnhatcautraloi/sp_ht_ds_traloi"),
    getDSLoaiCauTraLoi: axios =>axios.get("/web-quantri/capnhatcautraloi/sp_ht_loaitraloi"),
    insertCauTraLoi: async (axios, data) => axios.post('/web-quantri/capnhatcautraloi/sp_traloi_update', data),
    // deleteCauTraLoi: async (axios, data) => axios.post('/web-quantri/capnhatcautraloi/sp_traloi_delete?traloi_id=', {data})
    deleteCauTraLoi: async (axios, data) => axios.post('/web-quantri/capnhatcautraloi/sp_traloi_delete', data),
  
}
