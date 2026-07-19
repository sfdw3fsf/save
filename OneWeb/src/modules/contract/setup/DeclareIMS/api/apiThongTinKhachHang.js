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

export function getLoaiHinhThueBao (axios) {
  return axios.get('/web-quantri/danhmuc-chung/CSS_LOAIHINH_TB', {}).then(getDataFromResponse)
}

// get danh sách loại hợp đồng, fixed list 1
export function getLoaiHD (axios, param) {
  return axios.get(`/web-thicong/danhmuc/lay_ds_loai_hd?loaiHdId=${param}`, {}).then(getDataFromResponse)
}

export function getThongTinLapKem (axios, param) {
  return axios.post('/web-ccdv/khaibaoims/lay_thuebao_lapkem', param).then(getDataFromResponse)
}

export function fetchDanhSachQuyTrinhTheoLoaiHD (axios, param) {
  return axios.post('/web-ccdv/khaibaoims/sp_lay_ds_quytrinh_theo_loaihd_dichvu', param).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return result
    } else {
      return [{
        quytrinh: '--Không có quy trình--',
        quytrinh_id: -1
      }]
    }
  })
}

// trong code C# cũ là txtNhanVien
export function getThongTinNhanVienYC (axios, nhanvien_id) {
  return axios.get(`/web-hopdong/danhmuc/lay_thongtin_nhanvien/${nhanvien_id}`).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return result[0].NAME
    } else {
      return ''
    }
  })
}

export function getThongTinLapDatTheoYeuCau (axios, hdtb_id) {
  return axios.post(`/web-ccdv/khaibaotsl/sp_kiemtra_hen_hdtb`, {
    hdtb_id
  }).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return Number(result[0].c) > 0
    } else {
      return false
    }
  })
}

export function getThongTinHuongGiaoKhiThayDoiComboboxQuyTrinh (axios, quyTrinhId, tthd_id) {
  return axios.get(`/web-hopdong/danhmuc/ds_huonggiao/${quyTrinhId}/${tthd_id}`).then(response => {
    const result = getDataFromResponse(response)
    if (result) {
      return result[0]
    } else {
      return null
    }
  })
}

// get thông tin tọa độ
export function getThongTinBanDo (axios, hdtb_id, key) {
  return axios.post('/web-hopdong/thanhly/thanh_ly_map_id', {
    in_dk: `where hdtb_id = ${hdtb_id}`,
    id_neo: key,
    in_table: 'css.diachi_hdtb'
  }).then(getDataFromResponse)
}

// cập nhật tọa độ thuê bao
export function updateToaDoThueBao (axios, param) {
  return axios.post('/web-ccdv/khaibaocodinh/fn_capnhat_toado_hdtb', param).then(getDataFromResponse)
}
