export default {
    getDSDonVi: (axios, data) => axios.get('/web-toanha/tientrinh-dautu/sp_lay_ds_donvi'),
    getDSTienTrinhDauTu: (axios, data) => axios.get(`/web-toanha/tientrinh-dautu/sp_lay_ds_tientrinh_dautu/${data.hddt_id}`,),
    FN_TienTrinhDauTu: (axios, data) => axios.post('/web-toanha/tientrinh-dautu/fn_tientrinh_dautu',data),
    
}