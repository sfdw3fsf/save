function checkResponse (response) {
  return response &&
    response.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000'
}

export function getLoaiHinhThueBao (axios, loaiHinhThueBaoId) {
  return axios.post('/web-ccdv/khaibao-ims/fn_tt_loaihinh_tb', {
    p_param: loaiHinhThueBaoId,
    p_type: 3
  }).then(response => {
    if (checkResponse(response)) {
      return response.data.data
    } else {
      return ''
    }
  }).catch(err => {
    throw err
  })
}

export function getDataTable (axios, param) {
  return axios.post('/web-hopdong/thanhly/thanh_ly_map_id', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data
    } else {
      return ''
    }
  }).catch(err => {
    throw err
  })
}

export function CAPNHAT_NGAYKH_STATUS_V2 (axios, param) {
  return axios.post('/web-ccdv/khaibaoims/capnhat_ngaykh_status_v2', param).then(response => {
    if (checkResponse(response)) {
      return true
    } else {
      return response.message_detail
    }
  }).catch(err => {
    throw err
  })
}

export function capNhatSoLuongCuocGoiIP (axios, param) {
  return axios.post('/web-ccdv/khaibao-ims/sp_capnhat_slgoicuoc_ip_ims', param).then(response => {
    if (checkResponse(response)) {
      return true
    } else {
      return response.message_detail
    }
  }).catch(err => {
    throw err
  })
}

export function getThongTinLoaiHinhThueBao (axios, param) {
  return axios.post('/web-ccdv/khaibao-ims/fn_tt_loaihinh_tb', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data
    } else {
      return false
    }
  }).catch(err => {
    throw err
  })
}

export function capNhatStatusDongBoHopDong (axios, param) {
  return axios.post('web-ccdv/khaibao-ims/sp_capnhat_status_hdtb', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data === 1
    } else {
      return false
    }
  }).catch(err => {
    throw err
  })
}

export function capNhatNgayHoanThanh (axios, param) {
  return axios.post('web-ccdv/khaibao-ims/sp_capnhat_ngayht', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data === 1
    } else {
      return response.data.message_detail
    }
  }).catch(err => {
    throw err
  })
}

export function checkTaoDBThang (axios, phanvung_id, kyhoadon) {
  return axios.get(`web-ccdv/khaibao-ims/sp_kt_taodb_thang?p_phanvung_id=${phanvung_id}&p_kyhoadon=${kyhoadon}`).then(response => {
    if (checkResponse(response)) {
      return response.data.data[0].kq
    } else {
      return false
    }
  }).catch(err => {
    throw err
  })
}

export function capNhatNgayKichHoat (axios, param) {
  return axios.post('web-ccdv/khaibao-ims/sp_capnhat_ngaykh', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data === 1
    } else {
      return false
    }
  }).catch(err => {
    throw err
  })
}

export function DatMoiThueBao (axios, param) {
  return axios.post('web-ccdv/kichhoatims/datmoithuebao', param).then(response => {
    if (checkResponse(response)) {
      return response.data.data
    } else {
      return false
    }
  }).catch(err => {
    throw err
  })
}

export function getThongTinKichHoatThueBao (axios, hdtb_id, type) {
  return axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_bd_thuebao', {
    type,
    param: hdtb_id
  }).then(response => {
    if (response &&
      response.data &&
      response.data.error_code &&
      response.data.error_code === 'BSS-00000000') {
      return response.data.data
    }
    return {
      hasError: true,
      message: response.data.message_detail
    }
  }).catch(err => {
    throw err
  })
}

export function getThongTinKichHoatLoaiHinhThueBao (axios, loaitb_id, type) {
  return axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', {
    type,
    param: loaitb_id
  }).then(response => {
    if (response &&
      response.data &&
      response.data.error_code &&
      response.data.error_code === 'BSS-00000000') {
      return response.data.data
    }
    return {
      hasError: true,
      message: response.data.message_detail
    }
  }).catch(err => {
    throw err
  })
}
