import axios from "axios"
import api from "../api"
import {LoaiHopDong, KieuLapDat, TrangThaiHD } from "../Enum"
export default {
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
  async kiemtra_thuebao_lap_hopdong_khac (vmaTb, vloaiHDId) {
    var result = {isValid: false, message: ""}
    try {
      var body = {
        dichVuVtId: 2,
        loaiHdId: vloaiHDId,
        maGd: "0",
        maTb: vmaTb
      }
      var response = await api.kiemtra_thuebao_lap_hopdong_khac(axios, body)

      var data = this.getData(response)
      console.log("kiemtra_thuebao_lap_hopdong_khac: data", data)
      if (!data.error_msg && data.data && data.data.ma_tb) {
        result.isValid = false
        result.message = `Thuê bao ${data.data.ma_tb} đã được lập hợp đồng ${data.data.ten_loaihd} bởi ${data.data.nguoi_cn} vào ngày ${data.data.ngay_yc}`
      } else {
        result.isValid = true
      }
    } catch (e) {
      result.isValid = false
      result.message = e.message
    }
    return result
  },
  async tt_7_dk_hssv_lay_thongtin (vmaTb) {
    var response = await api.tt_7_dk_hssv_lay_thongtin(axios, {
      "so_tb": "84" + vmaTb
    })
    return this.getData(response)
  },
  async tt_7_dk_hssv_lskm (vmaTb) {
    var response = await api.tt_7_dk_hssv_lay_thongtin(axios, {
      "so_tb": "84" + vmaTb
    })
    return this.getData(response)
  },
  async ts_lichsu_capnhat_thuebao_tratruoc (vmaTb) {
    var response = await api.ts_lichsu_capnhat_thuebao_tratruoc(axios, {
      "so_tb": "84" + vmaTb
    })
    return this.getData(response)
  },
  async app_tb_image_dktt (vmaTb) {
    var response = await api.app_tb_image_dktt(axios, {
      "so_tb": "84" + vmaTb
    })
    return this.getData(response)
  },
  async upload_anh (vmaTb, anh1, anh2, anh3, anh4, lydo) {
    var response = await api.upload_anh(axios,
      {
        "gt_img_1": anh1,
        "gt_img_2": anh2,
        "gt_img_3": anh3,
        "gt_img_4": anh4,
        "ly_do": lydo,
        "so_tb": "84" + vmaTb
      }
    )
    return this.getData(response)
  },
  async tt_18_ma_xac_thuc (vMatb, userNeo) {
    var response = await api.tt_18_ma_xac_thuc(axios,
      {
        "so_tb": "84" + vMatb,
        "userid": userNeo
      }
    )
    return this.getData(response)
  },
  async ts_dk_hssv_dk (data) {
    var response = await api.ts_dk_hssv_dk(axios,
      data
    )
    return this.getData(response)
  },
  async ts_dk_hssv_huy (vsotb) {
    var response = await api.ts_dk_hssv_huy(axios,
      {
        "so_tb": "84" + vsotb
        // "userid": vuserid,
        // "userip": vuserid,
        // "ma_tinh": vmaTinh
      }
    )
    return this.getData(response)
  },
  async lay_thongtin_sinhvien_dd (vkieulay, vid) {
    // vkieulay = 0 : hopdong, truyen vao vid = hdtb_id
    // vkieulay = 1: danhba, vid = thuebao_id
    var body = {
      "p_ds_para": "{\"ID_TK\":" + vid + ",\"KIEU\":" + vkieulay + "}",
      "p_nghiepvu": "LAY_DS_NEO"
    }
    var response = await api.lay_thongtin_sinhvien_dd(axios, body)
    return this.getData(response)
  },
  async themmoi_hopdong_dd (data) {
    var response = await api.themmoi_hopdong_dd(axios, data)
    var result = this.getData(response)
    return {error_msg: result.error_msg, isSuccess: result.data == 1}
  },
  async capnhat_hopdong_dd (data) {
    var response = await api.capnhat_hopdong_dd(axios, data)
    var result = this.getData(response)
    return {error_msg: result.error_msg, isSuccess: result.data == 1}
  },
  async hths_dk_goicuoc_sv (hdtbId) {
    var response = await api.hths_dk_goicuoc_sv(axios, hdtbId)
    return this.getData(response)
  },
  async xoa_hopdong_khachhang (hdkhId, loaihdId) {
    var body = { "list": [ { "hdkh_id": hdkhId, "loaihd_id": loaihdId } ] }
    var response = await api.xoa_hopdong(axios, body)
    return this.getData(response)
  },
  async sinh_magd_v2 (data) {
    var response = await api.sinh_magd_v2(axios, data)
    var result = this.getData(response)
    return result
  },
  async get_keys (data) {
    var response = await api.get_keys(axios, data)
    var result = this.getData(response)
    console.log("getKey = ", result.data)
    return {error_msg: result.error_msg, data: parseInt(result.data)}
  },

  async lay_dshd_theo_ma_gd (maGd) {
    var body = {
      "in_loaihd_id": LoaiHopDong.DD_HSSV,
      "in_ma_gd": maGd,
      "in_nhanvien_id": 0,
      "in_tthd_id": TrangThaiHD.MOI
    }
    var response = await api.lay_dshd_theo_ma_gd(axios, body)
    return this.getData(response)
  },
  async upload_file (data) {
    var response = await api.upload_file(axios, data, {headers: { "Content-Type": "multipart/form-data" }})
    return this.getData(response)
  }

}
