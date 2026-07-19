export default {
    lay_ds_tieunganh:  async (axios, kieu, nganhnghe_id, vtc_id) => axios.post('/web-hopdong/lapdatmoi/lay_ds_tieunganh', {
        vkieu:kieu,
        vnganhnghe_id:nganhnghe_id,
        vtc_id:vtc_id
    }),

    timkiem_tieunganh:  async (axios, timkiem, kieu) => axios.get('/web-hopdong/thaydoithongtinkhuyenmai/timkiem_tieunganh?kieu='+kieu+'&&timkiem='+timkiem),

    
}