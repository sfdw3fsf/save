function getDataFromResponse(response) {
  if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
    return response.data.data
  }
  return []
}

function getBooleanResponse(response) {
  return response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && !!response.data.data
}

function checkResponse(response) {
  return response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000'
}

export function fetchDanhSachIMS(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/lay_ds_phieu_giaoden_ims', param).then(getDataFromResponse)
}

export function getDanhSachThaoTac(axios, luong_id) {
  return axios
    .post('/web-ccdv/khaibaoims/sp_lay_luong_thaotac', {
      luong_id
    })
    .then(getDataFromResponse)
}

// lấy danh sách dịch vụ đăng ký khi change selected record
export function getDanhSachDichVuDangKy(axios, hdtb_id) {
  return axios
    .post('/web-tracuu/tracuu_chitiet_thicong/sp_lay_ds_dangky_dvgt_by_hdtb_id', {
      hdtb_id // : 3431000
    })
    .then(getDataFromResponse)
}

// Lấy nhân viên theo mã phiếu table nhan viên
export function getNhanVienTheoMaPhieu(axios, phieu_id) {
  return axios
    .post('/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id', {
      phieu_id, // : 382800,
      kieu_id: 2
    })
    .then(getDataFromResponse)
}

// lấy danh sách tham số mặc định

export function getDanhSachThamSoMacDinh(axios) {
  return axios
    .post('/web-hopdong/thanhly/sp_lay_ds_thamso_md', {
      kieu_id: -1 // form này gán cứng = -1
    })
    .then(getDataFromResponse)
}

export function checkTramVatTu(axios, hdtb_id) {
  return axios
    .post('/web-ccdv/khaibaoims/kiemtra_tramvt1', {
      hdtb_id,
      kieu: 2 // fixed = 2
    })
    .then(getBooleanResponse)
}

export function checkPort(axios, hdtb_id) {
  return axios
    .post('/web-ccdv/khaibaoims/kiemtra_port', {
      hdtb_id
    })
    .then(getBooleanResponse)
}

export function getDanhSachNhanVienIdPhanCap(axios, hdtb_id) {
  return axios
    .post('/web-ccdv/khaibaoims/sp_lay_ds_nhanvien_theocap_hdtb', {
      hdtb_id
    })
    .then((response) => {
      const result = getDataFromResponse(response)
      if (result.length !== 0) {
        return Number(result[0].nhanvien_id)
      }
      return 0
    })
}

export function xoaPhieuTheoPhieuId(axios, phieuId) {
  return axios.get(`/web-ccdv/tiepnhanhopdong/xoa_phieu/${phieuId}`).then(getBooleanResponse)
}

export function insertPhieu(axios, json_giaophieu_nv) {
  return axios
    .post(`/web-ccdv/tiepnhanhopdong/insert_giaophieu_nv`, {
      json_giaophieu_nv
    })
    .then(getBooleanResponse)
}

export function apiUpdateThongTinGiaoViec(axios, param) {
  return axios.post(`/web-ccdv/khaibaoims/sp_update_tt_giaoviec`, param).then(getBooleanResponse)
}

export function checkConditionPort(axios, param) {
  return axios.post(`/web-ccdv/khaibaoims/sp_ktra_dk_port`, param).then(getDataFromResponse)
}

export function updateStatusPortNotUse(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_port', param).then(getBooleanResponse)
}

export function updateStatusPortIdNotUse(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/fn_capnhat_trangthai_port', param).then(getBooleanResponse)
}

export function updateDanhSachMaThueBaoTheoPort(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/fn_capnhat_ds_matb', param).then(getBooleanResponse)
}

export function updateThongTinPortIMS(axios, param) {
  return axios
    .post('/web-ccdv/khaibaoims/capnhat_thongtin_port_ims', param)
    .then((response) => {
      if (checkResponse(response)) {
        return true
      }
      return response.data.message_detail
    })
    .catch((err) => {
      throw err
    })
}

export function updateThongTinCapIMS(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/capnhat_thongtin_thicong_ims', param).then(getBooleanResponse)
}

export function getDanhSachHuongGiaoKhoiPhucSo(axios, hdtb_id) {
  return axios
    .post('/web-ccdv/khaibaoims/sp_lay_ds_huonggiao_giaophieu_theo_hdtb', {
      hdtb_id
    })
    .then(getDataFromResponse)
}

export function updateKhoiPhucSo(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/giaophieu_td_host_khoiphuclai', param).then(getBooleanResponse)
}

export function getHuongGiaoTheoQuyTrinh(axios, param) {
  return axios.post('/web-hopdong/thanhly/lay_idx_huonggiao_theoquytrinh_id', param).then((response) => {
    return Number(getDataFromResponse(response))
  })
}

export function getHuongGiaoLan2(axios, luong_id) {
  return axios
    .post('/web-ccdv/khaibaotsl/lay_huonggiao_theo_luong_id', {
      luong_id
    })
    .then((response) => {
      const result = getDataFromResponse(response)
      if (result && result.length > 0) {
        return Number(result[0].huonggiao_id)
      }
      return 0
    })
}

export function queryData(axios, param) {
  return axios.post('/web-hopdong/thanhly/thanh_ly_map_id', param).then(getDataFromResponse)
}

// Param to query
// {
//         "table": "css.diachi",
//         "where": "diachi_id = 1",
//         "result": "*",
//         "groupby": ""
// }
export function queryTable(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/lay_thongtin_table', param).then(getDataFromResponse)
}

export function layThamSoMacDinh(axios, thamSo) {
  return axios
    .post('/web-thicong/hoancongmegawan/lay_ds_thamso_md_mats', {
      ma_ts: thamSo
    })
    .then(getDataFromResponse)
}

// Kiểm tra chuyển tổ
export function getDanhSachPhieuKiemTraChuyenTo(axios, param) {
  return axios.post('/web-ccdv/xuly_nghiemthubaohong/sp_lay_ds_phieuvt_hdbh', param).then(getDataFromResponse)
}

// Chức năng hoàn thiện IMS
export function hoanThienAPI(axios, param) {
  return axios
    .post('/web-ccdv/khaibaoims/fn_hths_hoancongims', {
      vds: param
    })
    .then(getDataFromResponse)
}

// Chức năng hoàn công IMS
export function hoanCongAPI(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/hoancong', param)
}

// Chức năng hoàn công IMS - check giao sang OCM
export function kiemTraGiaoOCM(axios, param) {
  return axios.post('/web-ccdv/ht_hoso_thaydoidichvu/kiemtra_giao_omc', param).then(getDataFromResponse)
}

// Chức năng hoàn công IMS - API Gộp cập nhật giao việc
export function updateGiaoViecGopAPI(axios, param) {
  return axios.post('/web-ccdv/khaibaoims/fn_capnhat_hoancongims', param).then(getDataFromResponse)
}

// cập nhật thông tin phiếu IMS khi bấm nút cập nhật
export function capNhatThongTinPhieuIMS(axios, param) {
  return axios
    .post('/web-hopdong/DiemChiaTrungGianTSL/capnhat_tt_phieu', param)
    .then((response) => {
      if (checkResponse(response)) {
        return true
      } else {
        return response.data.message + '\n' + response.data.message_detail
      }
    })
    .catch((err) => {
      throw err
    })
}

// Hủy kích hoạt IMS
// LapHopDongFacade2().CAPNHAT_HUYKH_STATUS
export function huyKichHoatIMS(axios, param) {
  return axios.post('/web-ccdv/khaibaocodinh/capnhat_huykh_status', param).then(getDataFromResponse)
}

// API Thay thế cho map id

export function getDiaChiIDByHDTBID(axios, hdtb_id) {
  return axios
    .post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_hdtb', {
      type: 3,
      param: hdtb_id
    })
    .then((response) => {
      if (checkResponse(response)) {
        return Number(response.data.data)
      }
      return -1
    })
}
export function getDiaChiIDByThueBaoID(axios, thuebao_id) {
  return axios
    .post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_diachi_tb', {
      type: 3,
      param: thuebao_id
    })
    .then((response) => {
      if (checkResponse(response)) {
        return Number(response.data.data)
      }
      return -1
    })
}

export function getDanhSachDiaChi(axios, diachi_id) {
  return axios
    .post('/web-hopdong/laydulieu/sp_tt_diachi', {
      p_type: 1,
      p_param: diachi_id
    })
    .then(getDataFromResponse)
}

export function giaophieu_td_host_thuhoi_1(axios, data) {
  return axios.post(`/web-ccdv/dongbo_tb_codinh_lentongdai/giaophieu_td_host_thuhoi_1?hdtb_id=${data.hdtb_id}&phieu_id=${data.phieu_id}`, data).then(getDataFromResponse)
}

