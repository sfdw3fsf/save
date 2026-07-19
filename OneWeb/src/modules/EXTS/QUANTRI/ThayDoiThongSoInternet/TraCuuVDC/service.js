export default {
  getAreaCode() {
    return null // TODO:
  },
  // Thay doi nhom khach hang uu tien (y te) VASC
  async TraCuuThueBao(context,account,login) {
    context.$root.showLoading(true);

    let response = await context.$root.context.post(`/tichhop/visa-app/lookupRASAccount`, {
      accountName: account,
      login: login
    }).catch().finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getCustomerType(context,doituong_id) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_customer_type?doituongId=" + doituong_id).catch().finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getDtService(context) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get(`web-quantri/thongso_internet/get_list_service`).catch().finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getTenBras(context,brasId) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_bras?brasId=" + brasId).then({

    }).catch(()=>{}).finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getAction(context,actionId) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_action?actionId=" + actionId).then({

    }).catch(()=>{}).finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getTenDslam(context,dslam) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_dslam_vdc?dslamId="  + dslam).catch(()=>{}).finally(() => {
      console.log("vô đây để gỡ ")
      context.$root.showLoading(false);
    })
    return response
  },
  async getTariffType(context,tariffType) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get("web-quantri/thongso_internet/get_muccuoc?tariffType="+ tariffType).catch(()=>{}).finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

  async getPass(context) {
    context.$root.showLoading(true);
    let response = await context.$root.context.get(`web-quantri/thongso_internet/get_bras`, {
      accountName: account,
      login: login
    }).catch().finally(() => {
      context.$root.showLoading(false);
    })
    return response
  },

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
