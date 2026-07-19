export default {

  lay_ds_dl_nghiepvu(axios, data) {
    return axios.post("web-hopdong/lapdatmoi/lay_ds_dl_nghiepvu", data);
  },

  dsKieuGoiCuoc(axios) {
    return this.lay_ds_dl_nghiepvu(axios, {
      p_ds_para: JSON.stringify({}),
      p_nghiepvu: "LAY_DS_KIEU_GOICUOC_DD_ALL"
    });
  },

  dsGoiCuoc(axios, packageTypeId, queryText) {
    return this.lay_ds_dl_nghiepvu(axios, {
      p_ds_para: JSON.stringify({
        KIEUGOI_ID: packageTypeId,
        QUERY: queryText
      }),
      p_nghiepvu: "LAY_DS_GOICUOC_DD"
    });
  },

  chiTietGoiCuoc(axios, packageId) {
    return this.lay_ds_dl_nghiepvu(axios, {
      p_ds_para: JSON.stringify({
        MOBILE_PACKAGE_ID: packageId,
      }),
      p_nghiepvu: "LAY_GOICUOC_DD_THEO_ID"
    });
  },

  dsChuKy(axios) {
    return this.lay_ds_dl_nghiepvu(axios, {
      p_ds_para: JSON.stringify({}),
      p_nghiepvu: "LAY_DS_CHUKY_DD"
    });
  },

  fn_capnhat_goicuoc_dd: (axios, data) => axios.post("/web-tracuu/pttb/fn_capnhat_goicuoc_dd", data),
  fn_themmoi_goicuoc_dd: (axios, data) => axios.post("/web-tracuu/pttb/fn_themmoi_goicuoc_dd", data),
  fn_xoa_goicuoc_dd: (axios, data) => axios.post("/web-tracuu/pttb/fn_xoa_goicuoc_dd", data),


};
