function getDataFromResponse (response) {
  if (response &&
    response.data &&
    response.data.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000' &&
    response.data.data) {
    return response.data.data
  }
  return []
}

export function getDanhSachHeThong (axios) {
  return axios.post('/web-quantri/serihoadon/sp_lay_ds_hethong_seri_dt').then(getDataFromResponse)
}

export function getDanhSachMauSo (axios, kieu) {
  return axios.post('/web-quantri/serihoadon/sp_lay_ds_mauso_seri_dt', {
    loaihoadon_id: kieu
  }).then(getDataFromResponse)
}

export function getDanhSachSuDung (axios, param) {
  return axios.get('/web-quantri/serihoadon/sp_lay_ds_sudung_seri_dt').then(getDataFromResponse)
}

export function getDanhSachSeri (axios, loaihoadon_id) {
  return axios.post('/web-quantri/serihoadon/sp_lay_ds_seri_dt', {
    loaihoadon_id: loaihoadon_id
  }).then(getDataFromResponse)
}

export function getDanhSachSeriPortal (axios, pattern) {
  return axios.post('/tichhop/vnptinvoice/getSerialByPattern', {
    pattern: pattern
  })
}

export function updateSeri (axios, param) {
  return axios.post('/web-quantri/serihoadon/fn_capnhat_seri_dt', param)
}
