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

function getBooleanResponse (response) {
  return response &&
    response.data &&
    response.data.data &&
    response.data.error_code &&
    response.data.error_code === 'BSS-00000000' && !!response.data.data
}

export function fetchDanhSachLoaiCap (axios) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_loai_cap', {}).then(getDataFromResponse)
}

export function fetchDanhSachBras (axios) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_bras_adsl', {}).then(getDataFromResponse)
}

export function fetchDanhSachLAM (axios, param) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_dslam_adsl', param).then(getDataFromResponse)
}

export function getLyDoTra (axios, lydotra_con_id) {
  return axios.post('/web-hopdong/thanhly/thanh_ly_map_id', {
    id_neo: 'lydotra',
    in_table: 'css.lydotra_tc',
    in_dk: `where lydotra_id = ${lydotra_con_id}`
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result && result !== '-1') {
      return result
    } else {
      return ''
    }
  })
}

export function getThongTinLienHe (axios, hdkh_id) {
  return axios.post('/web-ccdv/khaibaoims/fn_lay_thongtin_lh_lapdat', {
    hdkh_id
  }).then(getDataFromResponse)
}

export function getDanhSachChiTietDichVu (axios) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_chitietdv_ims', {
    kieu: 0
  }).then(getDataFromResponse)
}

export function getThongTinIdDonVi (axios, hdtb_id) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_hdtb_dv', {
    hdtb_id: hdtb_id,
    loaidv_id: 5 // Fixed = 5 , code C# = enum LOAI_DV.TRAM_VT
  }).then(getDataFromResponse)
}

export function getTenTramTheoIdDonVi (axios, donvi_id) {
  return axios.post('/web-hopdong/thanhly/thanh_ly_map_id', {
    id_neo: 'ten_dv',
    in_table: 'admin.donvi',
    in_dk: `where donvi_id = ${donvi_id}`
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result && result !== '-1') {
      return result
    } else {
      return ''
    }
  })
}

export function getDanhSachThietBi (axios) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_thietbi_ims', {}).then(getDataFromResponse)
}

// Lấy danh sách nhân viên gán cho combobox người GV.
export function getDanhSachNhanVien (axios) {
  return axios.get('/web-ccdv/khaibaoims/sp_lay_ds_nhanvien_theo_donvi', {}).then(getDataFromResponse)
}

// Lấy danh sách nhân viên gán cho combobox người GV.
export function getDanhSachThongTinKyThuat (axios, hdtb_id) {
  return axios.post('/web-ccdv/khaibaoims/lay_ds_thongtin_kythuat_hdtb', {
    hdtb_id: hdtb_id
  }).then(getDataFromResponse)
}

// phần chọn port
export function getKieuLapDat (axios, kieuld_id) {
  return axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_kieu_ld', {
    type: 1,
    param: kieuld_id
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return Number(result)
    } else {
      return 0
    }
  })
}

export function checkLapKemCoSan (axios, param) {
  return axios.post('/web-ccdv/tienhopdong/fn_kt_lap_kem_co_san', param).then(response => {
    return response &&
      response.data &&
      response.data.error_code &&
      response.data.error_code === 'BSS-00000000'
  })
}

export function getDanhSachPortTheoMaThueBao (axios, param) {
  return axios.post('/web-ccdv/tienhopdong/sp_lay_ds_port_theo_ma_tb', param).then(getDataFromResponse)
}

export function updateNoiDungTH (axios, param) {
  return axios.post(`/web-ccdv/khaibaoims/sp_ims_ndth_update`, param).then(getBooleanResponse)
}

export function getDSLAMTheoDiaChi (axios, param) {
  return axios.post('/web-ccdv/tienhopdong/sp_lay_ds_dslam_tt', param).then(getDataFromResponse)
}

export function getDSLAMTheoPortMoi (axios, portId) {
  return axios.get(`web-ccdv/khaibaoims/lay_dslam_theo_port_id?portId=${portId}`).then(getDataFromResponse)
}

export function getDanhSachPortTuDong (axios, param) {
  return axios.post('web-ccdv/khaibaoims/lay_ds_port_tudong', param).then(getDataFromResponse)
}

export function getDanhSachPortTuDongHNI (axios, param) {
  return axios.post('web-ccdv/khaibaoims/lay_ds_port_tudong_hni', param).then(getDataFromResponse)
}

export function getDanhSachPortTheoHopDongID (axios, hdtb_id, matb_tn) {
  return axios.get(`web-ccdv/khaibaoims/lay_ds_port_matb_tn?hdtb_id=${hdtb_id}&matb_tn=${matb_tn}`).then(getDataFromResponse)
}

export function getDanhBaTheoMaTB (axios, data) {
  return axios.post('/web-hopdong/thaydoitocdoadsl/sp_thaydoi_tocdo_internet_hienthitt_danhba', {
    parameters: {
      DICHVUVT_ID: data.DICHVUVT_ID,
      DONVIDL_ID: 0,
      MA_TB: data.MA_TB,
      THUEBAO_ID: 0
    }
  }).then(getDataFromResponse)
}

export function getDSHopDongTheoHDTBID (axios, hdtb_id) {
  return axios.post('/web-quantri/thutienphatsinh/lay_ds_hopdong_tb_theo_hdtb_id', {
    in_hdtb_id: hdtb_id
  }).then(getDataFromResponse)
}
