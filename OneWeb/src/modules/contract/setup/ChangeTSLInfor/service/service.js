import api from "./api"
import axios from "axios"
export default{
  getData (response) {
    var result = {
      error_msg: null,
      data: null,
      message: null
    }
    if (
      response.data.error_code == "BSS-00000000"
    ) {
      result.error_msg = null
      result.data = response.data.data
      result.message = response.message
    } else {
      console.error("error code = " + response.data.error_code)
      result.error_msg = response.data.error_code + " " + response.data.message
    }
    return result
  },
  async css_tocdo_kenh () {
    var response = await api.css_tocdo_kenh(axios)
    return this.getData(response)
  },
  async sp_lay_ds_loai_tbi (dichvuvt_id) {
    var response = await api.sp_lay_ds_loai_tbi(axios, dichvuvt_id)
    return this.getData(response)
  },
  async cabman_loai_cap () {
    var response = await api.cabman_loai_cap(axios)
    return this.getData(response)
  },
  async sp_ht_tatca_vetinh_combobox1 () {
    var response = await api.sp_ht_tatca_vetinh_combobox1(axios, {"loaidv_id": 5})
    return this.getData(response)
  },
  async sp_lay_ds_tramtc_thuebao (ploaiDanhBaHopDong, thuebaoId) {
    var response = await api.sp_lay_ds_tramtc_thuebao(axios, {
      p_loai: ploaiDanhBaHopDong,
      p_kieudv: 2,
      p_db_id: thuebaoId
    })
    var data = (this.getData(response)).data
    return data ? data[0] : null
  },
  async map_id (dieuKien, truongCanLay, tenBang) {
    var response = await api.map_id(axios, {
      "dk": dieuKien,
      "id_neo": truongCanLay,
      "phanvung_id": 0,
      "table": tenBang
    })
    return this.getData(response)
  },
  async lay_ds_hopdong_theo_matb (jsonData) {
    var response = await api.lay_ds_hopdong_theo_matb(axios, jsonData)
    return this.getData(response)
  },
  async lay_danhba_theo_matb (maTb) {
    var response = await api.lay_danhba_theo_matb(axios, maTb)
    return this.getData(response)
  },
  async lay_ds_hopdong_thuebao_tls (hdtbId) {
    var response = await api.lay_ds_hopdong_thuebao_tls(axios, hdtbId)
    return this.getData(response)
  },
  async css_dichvu_vt () {
    var response = await api.css_dichvu_vt(axios)
    return this.getData(response)
  },
  async css_loaihinh_tb () {
    var response = await api.css_loaihinh_tb(axios)
    return this.getData(response)
  },
  async get_keys (keyName) {
    var response = await api.get_keys(axios, {
      "keyname": keyName
    })
    var result = this.getData(response)
    return result.data
  },
  async sp_insert_tdtt_db_tsl (jsonData) {
    var response = await api.sp_insert_tdtt_db_tsl(axios, jsonData)
    return this.getData(response)
  },
  async fn_capnhat_port_thaydoi_tskt_tsl (data) {
    return this.getData(await api.fn_capnhat_port_thaydoi_tskt_tsl(axios, data))
  },
  async fn_capnhat_port_thaydoi_tskt_tsl_v2 (data) {
    return this.getData(await api.fn_capnhat_port_thaydoi_tskt_tsl_v2(axios, data))
  },
  async lay_ds_db_con_theo_tbid (thuebaoid) {
    var response = this.getData(await api.lay_ds_db_con_theo_tbid(axios, thuebaoid))
    if (!response.error_msg) { return response.data }
    return []
  },
  async map_diachi_cuoi_id_danhba (thuebaoId) {
    var body = {
      param: thuebaoId,
      type: 1
    }
    var response = this.getData(await api.fn_tt_db_tsl(axios, body))
    return response.data
  },
  async map_diachi_cuoi_id_hopdong (hdtbId) {
    var body = {
      param: hdtbId,
      type: 1
    }
    var response = this.getData(await api.fn_tt_hdtb_tsl(axios, body))
    return response.data
  },
  async map_diachi_dau_id_danhba (thuebaoId) {
    var body = {
      param: thuebaoId,
      type: 2
    }
    var response = this.getData(await api.fn_tt_db_tsl(axios, body))
    return response.data
  },
  async map_diachi_dau_id_hopdong (hdtbId) {
    var body = {
      param: hdtbId,
      type: 2
    }
    var response = this.getData(await api.fn_tt_hdtb_tsl(axios, body))
    return response.data
  },
  async map_thuebaoid (hdtbId) {
    var body = {
      param: hdtbId,
      type: 5
    }
    var response = this.getData(await api.fn_tt_hd_thuebao(axios, body))
    return response.data
  },
  async map_dslam_id (portId) {
    var body = {
      param: portId,
      type: 1
    }
    var response = this.getData(await api.fn_tt_port(axios, body))
    return response.data
  },
  async map_thamso (dslamId) {
    var body = {
      param: dslamId,
      type: 1
    }
    var response = this.getData(await api.fn_tt_dslam(axios, body))
    return response.data
  }
}