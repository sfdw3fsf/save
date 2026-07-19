function checkResponse (response) {
  return response &&
    response.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000'
}

// export async function KhoiTaoThueBao (axios, maTinh, msisdn, imsi, password, type, profile) {
//   const dauSoIMS = await layDauSoIMSTheoTinh(axios, maTinh)
//   return axios
//     .post('tichhop/sps/sps_service_ims', {
//       imsi: '84' + dauSoIMS + imsi,
//       profile: profile,
//       msisdn: '84' + dauSoIMS + msisdn,
//       subtype: type,
//       user_password: password
//     })
//     .then((response) => {
//       if (response.data.data[0].Status === 9) {
//         return true
//       } else {
//         return response.data.data[0].StatusMessage
//       }
//     })
//     .catch((err) => {
//       throw err
//     })
// }

export async function KhoiTaoThueBao(axios, maTinh, msisdn, imsi, password, type, profile) {
  const dauSoIMS = await layDauSoIMSTheoTinh(axios, maTinh)
  return axios
    .post('web-hopdong/hopdong/sps_service_ims', {
      imsi: '84' + dauSoIMS + imsi,
      profile: profile,
      msisdn: '84' + dauSoIMS + msisdn,
      subtype: type,
      user_password: password
    })
    .then((response) => {
      if (response.data.data[0].Status === 9) {
        return true
      } else {
        return response.data.data[0].StatusMessage
      }
    })
    .catch((err) => {
      throw err
    })
}

export async function layDauSoIMSTheoTinh(axios, maTinh) {
  return axios.post('web-hopdong/khaibao-ims-bosung/sp_lay_dauso_ims_theo_tinh', {
    p_tentat: maTinh
  }).then(response => {
    if (checkResponse(response) && response.data) {
      return response.data.data[0].dauso_ims || ''
    }
    return ''
  }).catch(err => {
    throw err
  })
}


export async function lay_thong_tin_diachi (axios, input) {
  return axios.post('web-hopdong/khaibao-ims/lay_thongtin_diachi_ims', {
    "vphanvung_id": input.vphanvung_id,
    "vthuebao_id": input.vthuebao_id,
    "vhdtb_id": input.vhdtb_id,
    "vloaihd_id": input.vloaihd_id
  }).then(response => {
    if (checkResponse(response) && response.data) {
      return response.data.data || ''
    }
    return ''
  }).catch(err => {
    throw err
  })
}

export async function CHANGE_LOCATION (axios, input) {
  return axios.post('tichhop/sps/change_location', {
    "msisdn": input.ma_tb,
    "area": input.area
  }).then(response => {
    if (checkResponse(response)) {
      return {error_code: response.data.error_code, message: response.data.message_detail}
    }
    return {error_code: response.data.error_code, message: response.data.message_detail}
  }).catch(err => {
    throw err
  })
}

export function thayDoiDVGT (axios, param) {
  return axios.post('web-ccdv/kichhoatims/thaydoidvgt', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

export function KhoiTaoThueBao_siptrunk (axios, param) {
  return axios.post('web-ccdv/kichhoatims/khoitaothuebao_siptrunk', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

export function ThayDoiDVGT_Siptrunk (axios, param) {
  return axios.post('web-ccdv/kichhoatims/thaydoidvgt_siptrunk', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

export function HuyThueBao (axios, msisdn) {
  return axios.get(`web-ccdv/kichhoatims/huythuebao?msisdn=${msisdn}`).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.data.message
  }).catch(err => {
    throw err
  })
}

export function ThanhLyThueBao (axios, matb) {
  return axios
    .get(`web-ccdv/kichhoatims/ThanhLyThueBao?matb=${matb}`)
    .then((response) => {
      if (checkResponse(response)) {
        return true
      }
      return response.data.data.message
    })
    .catch((err) => {
      throw err
    })
}

// ims.ThanhLy_DuongTrunk
export function ThanhLyDuongTrunk (axios, param) {
  return axios.post('web-ccdv/kichhoatims/thanhly_duongtrunk', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

export function DongBoTestAPI (axios) {
  return axios.post('/web-hopdong/hopdong/get_app_config', {
    keyname: 'DONGBO_TEST'
  }).then(response => {
    if (checkResponse(response)) {
      return response.data.data.dongbo === "1"
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

// vtn_service.KhoiPhucThueBao
// {
//   hdtb_id
//   bd_loaitb_id_vtn
// }
export function KhoiPhucThueBao (axios, param) {
  return axios.post('web-ccdv/kichhoatims/khoiphucthuebao', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}

// ims.ThayDoiDVGT_Danhba
export function ThayDoiDVGT_Danhba (axios, param) {
  return axios.post('web-ccdv/kichhoatims/thaydoidvgt_danhba', param).then(response => {
    if (checkResponse(response)) {
      return true
    }
    return response.data.message
  }).catch(err => {
    throw err
  })
}
