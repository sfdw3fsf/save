export default {
  SP_HT_KIEULD_LOAITB_BANCHEO: async (axios, req) => axios.post('/web-hopdong/thanhly-bancheo-chonlydohuy/sp_ht_kieuld_loaitb_bancheo', req),
  SP_MOBILE_LAYDS_NHOMHUY: async (axios, req) => axios.post('/web-hopdong/thanhly-bancheo-chonlydohuy/sp_mobile_layds_nhomhuy', req),
  LAY_DS_LYDO_HUY: async (axios, req) => axios.post('/web-hopdong/thanhly-bancheo-chonlydohuy/lay_ds_lydo_huy', req),
  sp_layds_nhomhuy: async (axios, req) => axios.post('/web-hopdong/hopdong/sp_layds_nhomhuy', req)
}
