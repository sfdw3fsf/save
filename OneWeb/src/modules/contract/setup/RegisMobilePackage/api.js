export default {

  /*
  {
    "in_dk": "string",
    "in_id_neo": "string",
    "in_table": "string"
  }
   */
  map_id: async (axios, data) => axios.post('/web-quantri/thamso/map_id', data),

  /*
  {
    "so_tb": "84918486486" // "84856334585"
  }
   */
  app_tracuu_tt_thuebao: async (axios, data) => axios.post('/ccbs/tracuu/app_tracuu_tt_thuebao', data),
  ts_tracuu_laytt_tb_ts: (axios, data) => axios.post('ccbs/executor/ts_tracuu_laytt_tb_ts', data),

  app_tracuu_sudung_dv: async (axios, data) => axios.post('/ccbs/tracuu/app_tracuu_sudung_dv', data),

  app_tracuu_lsthuebao: (axios, data) => axios.post('ccbs/tracuu/app_tracuu_lsthuebao', data),
  app_tracuu_ls_dichvu3g: async (axios, data) => axios.post('/ccbs/tracuu/app_tracuu_ls_dichvu3g', data),

  didong_goi: async (axios, data) => axios.post('/ccbs/didong/goi?detail=true', data),
  didong_nhom_taikhoan: async (axios, data) => axios.post('/ccbs/didong/nhom-taikhoan', data),
  didong_taikhoan: async (axios, data) => axios.post('/ccbs/didong/taikhoan', data),
  didong_taikhoan_tien: async (axios, data) => axios.post('/ccbs/didong/taikhoan-tien', data),

  fn_themmoi_tt_tiepthi_dd: async (axios, data) => axios.post('/web-tracuu/pttb/fn_themmoi_tt_tiepthi_dd', data),


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


};
