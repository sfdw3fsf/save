export default {
    lay_ds_thuoctinh: async (axios) => axios.post('/web-quantri/giatri-thuoctinh/sp_cbb_thuoctinh',{}),
    sp_grv_thuoctinh: async (axios, thuoctinh_id) => axios.post('/web-quantri/giatri-thuoctinh/sp_grv_thuoctinh',{
        thuoctinh_id: thuoctinh_id,
        pagenumber: 1,
        pagesize: 100000000
    }),
    sp_del_giatritt: async (axios, gttt_id) => axios.post('/web-quantri/giatri-thuoctinh/sp_del_giatritt',{
        gttt_id: gttt_id
    }),

    sp_upd_giatritt: async (axios, data) => axios.post('/web-quantri/giatri-thuoctinh/sp_upd_giatritt', {
        json_giatritt: data
    }),
}