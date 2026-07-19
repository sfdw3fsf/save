function cloneList (data) {
  return JSON.parse(JSON.stringify(data))
}

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

function recursionMapUnit (donViObject, rawData) {
  donViObject.children = cloneList(rawData.filter(item2 => item2.donvi_cha_id === donViObject.donvi_id && item2.donvi_id !== donViObject.donvi_id))
    .map(item3 => recursionMapUnit(item3, rawData))
  return donViObject
}

export function nestedListUnitAPI (flatUnit) {
  if (!flatUnit || flatUnit.length === 0) return []

  const nestedList = cloneList(flatUnit.ls.filter(item => item.unitlevel === 1)).map(item1 => recursionMapUnit(item1, flatUnit.ls))
  const rootNode =
    {
      ten_dv: flatUnit.tendv_cha,
      unitlevel: 0,
      donvi_id: flatUnit.donvi_id_cha || "0",
      donvi_cha_id: 0,
      children: nestedList
    }
  return [rootNode]
}

export function getDanhSachDonViTreeView (axios) {
  return axios.get('/web-quantri/donvi/sp_load_treeview').then(response => {
    if (response &&
      response.data &&
      response.data.data &&
      response.data.error_code &&
      response.data.error_code === 'BSS-00000000' &&
      response.data.data) {
      if (!response.data.data || !response.data.data.ls || response.data.data.ls.length === 0) return []
      return nestedListUnitAPI(response.data.data)
    }
    return []
  })
}

export function layDSHoSoCaiTaoBaoDuongTT (axios, params) {
  return axios.post('/web-tracuu/tcnone/lay_ds_hoso_caitao_baoduong_tt', params).then(getDataFromResponse)
}

export function spNapCboTrangthaiCC (axios) {
  return axios.get('/web-tracuu/tcnone/sp_nap_cbo_trangthai_cc').then(getDataFromResponse)
}

export function layDanhSachPTMCaiTaoBaoDuong (axios, params) {
  return axios.post('/web-tracuu/tcnone/lay_ds_ptm_caitao_baoduong', params).then(getDataFromResponse)
}

export function spNapGRDTienTrinhCC (axios, params) {
  return axios.get(`/web-tracuu/tcnone/sp_nap_grd_tientrinh_cc/${params}`).then(getDataFromResponse)
}

export function layDSGVCaiTaoBaoDuong (axios, params) {
  return axios.post("/web-tracuu/tcnone/lay_ds_gv_caitao_baoduong", params).then(getDataFromResponse)
}

export function traCuuPhieuCC (axios, params) {
  return axios.get(`/web-tracuu/tcnone/tracuu_phieu_cc/${params}`).then(getDataFromResponse)
}
