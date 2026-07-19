function getDataFromResponse (response) {
  if (response &&
    response.data &&
    response.data.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000' &&
    response.data.data) {
    return response.data.data
  }
  return null
}

export function getNhanVienTiepThi (axios, nhanvien_id) {
  if (!nhanvien_id) return ''
  return axios.post('/web-ccdv/khaibaotsl/sp_lay_thongtin_nhanvien_dv', {
    nhanvien_id: nhanvien_id
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return result[0].nhanvien
    } else {
      return ''
    }
  })
}

export async function fetchDanhSachKenhThu (axios) {
  return await axios.post('/web-ccdv/khaibaoims/sp_lay_ds_kenhthu', {}).then(getDataFromResponse)
}

export function getKenhThu (axios, hdkh_id) {
  return axios.post('/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh', {
    hdkh_id: hdkh_id
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return Number(result[0].kenhthu_id)
    } else {
      return 0
    }
  })
}

export async function fetchKieuHopDong (axios) {
  return await axios.post('/web-ccdv/khaibaoims/sp_lay_ds_kieu_hd', {}).then(getDataFromResponse)
}

export function getTongTien (axios, hdkh_id) {
  return axios.post('/web-ccdv/khaibaotsl/lay_tongtien_theo_hdkh_id', {
    hdkh_id: hdkh_id
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return Number(result[0].tongtien)
    } else {
      return 0
    }
  })
}
