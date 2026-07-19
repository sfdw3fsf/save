export default {
    lay_ds_duan: async (axios) => axios.get('/web-ccdv/khaibaotsl/lay_ds_duan'),
    lay_ds_hdtb_by_id: async (axios, hdtbId) => axios.get('/web-ccdv/khaibaotsl/lay_ds_hdtb_by_id?hdtbId='+hdtbId),
    update_duan_hdtb: async (axios, hdtb_id, duan_id) => axios.post('/web-ccdv/khaibaotsl/update_duan_hdtb',{
        hdtb_id: hdtb_id,
        duan_id: duan_id
    }),
}