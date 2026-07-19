import { aitoff } from "@syncfusion/ej2-vue-maps"
import axios from "axios"
import api from "./ChangeSubsTypeAPI"

export default {
  getData (response) {
    var result = {
      error_msg: null,
      data: null,
      message: null
    }
    if (
      response.data.error == "200" &&
      response.data.error_code == "BSS-00000000"
    ) {
      result.error_msg = null
      result.data = response.data.data
      result.message = response.message
    } else {
      result.error_msg = "error_code = " + response.data.error_code + " message_detail =" + response.data.message_detail
      console.error(result.error_msg)
    }
    return result
  },
  async lay_ds_phieu_chuatt (hdtb_id) {
    let response = await api.lay_ds_phieu_chuatt(axios, { "vhdtb_id": hdtb_id})
    return this.getData(response)
  },
  async fn_suakm_cdlh (data) {
    var response = await api.fn_suakm_cdlh(axios, data)
    var result = this.getData(response)
    if (!result.error_msg) {
      try {
        var jsonData = JSON.parse(result.data)
        result.data = jsonData
        if (jsonData.ERROR_CODE == 1) {
          result.error_msg = null
        } else {
          result.error_msg = jsonData.MESSAGE
        }
      } catch (e) {
        result.error_msg = e.message
        console.error(e.message)
      }
    }
    return result
  },
  async lay_hopdong_tb_hddt (data) {
    var response = await api.lay_hopdong_tb_hddt(axios, data)
    return this.getData(response)
  },
  async lay_tt_hddt_dc (data) {
    var response = await api.lay_tt_hddt_dc(axios, data)
    return this.getData(response)
  },

  async sp_capnhat_phieutt_hd_hdkh_id (data) {
    var response = await api.sp_capnhat_phieutt_hd_hdkh_id(axios, data)
    return this.getData(response)
  },
  async sp_kt_dieuchinh_hddt (data) {
    var response = await api.sp_kt_dieuchinh_hddt(axios, data)
    return this.getData(response)
  },
  async sp_lay_ds_phieutt_hd_loaihoadon (data) {
    var response = await api.sp_lay_ds_phieutt_hd_loaihoadon(axios, data)
    return this.getData(response)
  },
  async sp_lay_ds_bd_hddt_max (data) {
    var response = await api.sp_lay_ds_bd_hddt_max(axios, data)
    return this.getData(response)
  },
  async sp_tt_loaihoadon_seri (data) {
    var response = await api.sp_tt_loaihoadon_seri(axios, data)
    return this.getData(response)
  },
  async sp_insert_bd_hddt (data) {
    var response = await api.sp_insert_bd_hddt(axios, data)
    return this.getData(response)
  },
  async sp_insert_bd_hddt_ct (data) {
    var response = await api.sp_insert_bd_hddt_ct(axios, data)
    return this.getData(response)
  },
  async sp_capnhat_phieutt_hd_v2 (data) {
    var response = await api.sp_capnhat_phieutt_hd_v2(axios, data)
    return this.getData(response)
  },
  async sp_capnhat_bd_hddt_theo_bdhddt_cha_id (data) {
    var response = await api.sp_capnhat_bd_hddt_theo_bdhddt_cha_id(axios, data)
    return this.getData(response)
  },
  async fn_tt_db_thuebao (vid, vtype) {
    try {
      var response = await api.fn_tt_db_thuebao(axios, {param: vid, type: vtype})
      var muccuocId = Number(this.getData(response).data)
      return muccuocId
    } catch (e) {
      return -1
    }
  },
  async fn_tt_muccuoc_tb (vid, vtype) {
    try {
      var response = await api.fn_tt_muccuoc_tb(axios, {param: vid, type: vtype})
      return this.getData(response).data
    } catch (e) {
      return -1
    }
  },
  async fn_tt_goicuoc_dd (vparam, vtype = 1) {
    try {
      var response = await api.fn_tt_goicuoc_dd(axios, {param: vparam, type: vtype})
      return this.getData(response).data
    } catch (e) {
      return ""
    }
  },
  async fn_tt_tocdo_adsl (vparam, vtype) {
    try {
      var response = await api.fn_tt_tocdo_adsl(axios, {param: vparam, type: vtype})
      return this.getData(response).data
    } catch (e) {
      return -1
    }
  },

  async fn_tt_hdtb_dd (vparam, vtype) {
    try {
      var response = await api.fn_tt_hdtb_dd(axios, {param: vparam, type: vtype})
      return this.getData(response).data
    } catch (e) {
      return -1
    }
  },

  async fn_tt_khuyenmai (vparam, vtype) {
    try {
      var response = await api.fn_tt_khuyenmai(axios, {param: vparam, type: vtype})
      return this.getData(response).data
    } catch (e) {
      return -1
    }
  }

}
