export default {
  lookup_account: async (axios, data, id) => axios.get('/web-tracuu/visa/lookup_account/' + id, data),
  ds_thamsodv: async (axios, data, header) => axios.get('/web-quantri/thamso/ds_thamsodv', data, header),
  map_id: async (axios, data, header) => axios.post('/web-quantri/thamso/map_id', data, header),
  TraCuuThueBao: (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccount', data),
  async getTenBras(context, brasId) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_bras?brasId=" + brasId).then({}).catch(() => {
    }).finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },
  async getTenDslam(context, dslam) {
    context.$root.showLoading(true);

    let response = await context.$root.context.get("web-quantri/thongso_internet/get_dslam_vdc?dslamId=" + dslam).catch(() => {
    }).finally(() => {
      console.log("vô đây để gỡ ")
      context.$root.showLoading(false);
    })
    return response
  },
  async getCustomerType(context, doituong_id) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_customer_type?doituongId=" + doituong_id).catch().finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },
  async getTariffType(context, tariffType) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_muccuoc?tariffType=" + tariffType).catch(() => {
    }).finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },
  Capnhat_log_cgnat: (axios, data) => axios.post('/web-hopdong/lapdatmoi/sp_capnhat_log_cgnat', data),
  ChangeCGNAT: (axios, data) => axios.post('/web-ccdv/hths_khoamo_may/changeCGNAT', data),

  async GetStatus(context,txtAccount) {
    context.$root.showLoading(false);
    let response = await context.$root.context.post(`/tichhop/green_online/GetStatus`, {
      id: txtAccount
    }).catch().finally(() => {
      context.$root.showLoading(false);
    })
    context.loading(false)
    return response
  }
}
