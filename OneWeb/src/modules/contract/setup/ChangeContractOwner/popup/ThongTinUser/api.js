export default {
  //import duy CTV
  getDanhSachEmail: async (axios, data) => {
    return axios.post(`web-quantri/khoamodichvu/sp_lay_ds_email`, data)
  },
  getDanhSachUser: async (axios, data) => {
    return axios.post(`web-quantri/khoamodichvu/sp_lay_ds_user`, data)
  },
  getLoaiUser: async (axios) => {
    return axios.get(`web-quantri/khoamodichvu/get_loai_user`)
  },
  getDanhSachHopDongUser: async (axios, data) => {
    return axios.post(`web-hopdong/thanhly-thuebao-bancheo/lay_ds_hopdong_user`, data)
  },
  updateHopDongUser: async (axios, data) => {

    /**
     * POST: {{base_url}}/thanhly-thuebao-bancheo/fn_capnhat_hd_user
      {
          "p_json_hduser": "",
          "p_json_hdtt": "",
          "p_json_diachi": "",
          "p_json_diachi_hddt": "",
          "p_json_hdtt_user": "",
          "p_insert": 1
      }
     */
    return axios.post('web-hopdong/thanhly-thuebao-bancheo/fn_capnhat_hd_user', data)
  },
  deleteHopDongUser: async (axios, data) => {
    /**
         * POST: web-hopdong/thanhly-thuebao-bancheo/fn_xoa_hd_user
    {
        "p_hduser_id": 1
    }

     */
    return axios.post('web-hopdong/thanhly-thuebao-bancheo/fn_xoa_hd_user', data)
  },
  getKey: async(axios, data) => (axios.post('/web-hopdong/khoiphucthanhly/get_keys', data))
}

