function checkResponse (response) {
  return response &&
    response.data &&
    response.data.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000' &&
    response.data.data
}

export function getInvoiceTypeOptions (axios) {
  return axios.post('/web-quantri/serihoadon/sp_lay_ds_kieu_hoadon')
    .then((response) => {
      if (checkResponse(response)) {
        return response.data.data
      } else {
        return []
      }
    })
}

export function getInvoiceList (axios) {
  return axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HOADON')
    .then((response) => {
      if (checkResponse(response)) {
        return response.data.data.map(invoiceType => {
          invoiceType.TenKieuHoaDon = invoiceType.KIEU === 2 || invoiceType.KIEU === 4 || invoiceType.KIEU === 5 ? 'Hóa đơn điện tử' : 'Hóa đơn giấy'
          invoiceType.MAP_KIEU = invoiceType.KIEU === 2 || invoiceType.KIEU === 4 || invoiceType.KIEU === 5 ? 2 : 1
          return invoiceType
        })
      } else {
        return []
      }
    })
}

export function saveInvoice (axios, url, data) {
  return axios.post(url, data).then(() => {
    return {
      status: true
    }
  }).catch((err) => {
    return {
      status: false,
      error: err.data.message_detail
    }
  })
}

export function deleteInvoice (axios, invoiceId) {
  return axios.post('/web-quantri/serihoadon/fn_xoa_loai_hoadon', {
    loaihoadon_id: invoiceId
  }).then(() => {
    return {
      status: true
    }
  }).catch((err) => {
    return {
      status: false,
      error: err.data.message_detail
    }
  })
}
