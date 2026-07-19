export default {
  // UR2.6.022_049_001
  LAY_DS_NHOM_LYDO_HUY: async (axios, request) => axios.post('/web-hopdong/thaydoilydohuy/lay_ds_nhom_lydo_huy', request),
  // UR2.6.022_049_002
  LAY_DS_LYDO_HUY: async (axios, request) => axios.post('/web-hopdong/thaydoilydohuy/lay_ds_lydo_huy', request),
  // UR2.6.022_049_003
  FN_THAYDOILYDOHUY_CAPNHAT: async (axios, request) => axios.post('/web-hopdong/thaydoilydohuy/fn_thaydoilydohuy_capnhat', request)

}
