//BSS-38770_frmCapNhat_CTK_CTC
export default {
    // 1. CSS.TRACUU_HOPDONG.LAY_DS_HDTB_MGWAN_THEO_HDTB_ID
    // input: hdtb_id=4938380
    lay_ds_hdtb_mgwan_theo_hdtb_id: (axios, params) => axios.get(`/web-hopdong/capnhat-ngay-truythu/lay_ds_hdtb_mgwan_theo_hdtb_id`, { params }),

    // 2. CSS.TRACUU_HOPDONG.LAY_DS_HDTB_TSL_THEO_HDTB_ID
    // input: hdtbId=2085048
    lay_ds_hopdong_thuebao_tls: (axios, params) => axios.get(`/web-hopdong/hopdong/lay_ds_hopdong_thuebao_tls`, { params }),

    getKeys: (axios, params) => axios.post('web-quantri/donvitinh/get_keys', params),
    fn_tt_hd_thuebao: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_hd_thuebao`, data),
    // POST: web-hopdong/capnhat-ngay-truythu/fn_insert_tdtt_mgwan_hdtb_mgwan
    // input: {
    //     "dstdtt_mgwan": "",
    //     "dichvuvt_id": 8,
    //     "cuoc_tk": 1,
    //     "cuoc_tc": 1,
    //     "cuoc_ht": 1,
    //     "cuoc_tbi": 1
    // }
    fn_insert_tdtt_mgwan_hdtb_mgwan: (axios, params) => axios.post(`/web-hopdong/capnhat-ngay-truythu/fn_insert_tdtt_mgwan_hdtb_mgwan`, params),

    // POST: web-hopdong/capnhat-ngay-truythu/fn_insert_tdtt_tsl_hdtb_tsl
    // input: {
    //     "dstdtt_tsl": "",
    //     "cuoc_tk": 1,
    //     "cuoc_tc": 1,
    //     "cuoc_ht": 1,
    //     "cuoc_tbi": 1
    // }
    fn_insert_tdtt_tsl_hdtb_tsl: (axios, params) => axios.post(`/web-hopdong/capnhat-ngay-truythu/fn_insert_tdtt_tsl_hdtb_tsl`, params),
}