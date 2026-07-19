function checkResponse (response) {
  return response &&
    response.data &&
    response.data.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000' &&
    response.data.data
}

export function getAgentList (axios) {
  return axios.post('/web-quantri/thuno/sp_lay_dlthulao_theo_phanvung')
    .then((response) => {
      if (checkResponse(response)) {
        return response.data.data
      } else {
        return []
      }
    })
}

export function getKhuVucList (axios, param) {
  return axios.post('/web-quantri/thuno/sp_thulao_layds_khuvuc', param)
    .then((response) => {
      if (checkResponse(response)) {
        return response.data.data.map(item => ({
          value: item.khuvuctc_id,
          text: item.khuvuc_tc,
          isChecked: false
        }))
      } else {
        return []
      }
    })
}

export function getLoaiPhieuList (axios, param) {
  return axios.post('web-quantri/thuno/sp_thulao_layds_loaiphieu', param)
    .then((response) => {
      if (checkResponse(response)) {
        return response.data.data.map(item => ({
          value: item.loaiphieu_id,
          text: item.loaiphieu,
          isChecked: false
        }))
      } else {
        return []
      }
    })
}

export function updateAgent (axios, param) {
  return axios.post('web-quantri/thuno/sp_thulao_insert_or_update', param)
    .then((response) => {
      return response.data.message === 'Success'
    })
}

export function deleteAgent (axios, param) {
  return axios.post('/web-quantri/thuno/sp_thulao_delete', param)
    .then((response) => {
      return response.data.message === 'Success'
    })
}
