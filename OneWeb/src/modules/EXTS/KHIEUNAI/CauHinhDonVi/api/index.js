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

function cloneList (data) {
  return JSON.parse(JSON.stringify(data))
}

function recursionMapUnit (donViObject, rawData) {
  donViObject.children = cloneList(rawData.filter(item2 => item2.donvi_cha_id === donViObject.donvi_id && item2.donvi_id !== donViObject.donvi_id))
    .map(item3 => recursionMapUnit(item3, rawData))
  return donViObject
}

export function getDanhSachDonViTreeView (axios) {
  return axios.get('web-quantri/donvi/sp_load_treeview').then(response => {
    if (response &&
      response.data &&
      response.data.data &&
      response.data.error_code &&
      response.data.error_code === 'BSS-00000000' &&
      response.data.data) {
      if (!response.data.data || !response.data.data.ls || response.data.data.ls.length === 0) return []
      console.log('response.data.data.ls')
      console.log(response.data.data.ls.find(e => e.donvi_id == 10376))
      return nestedListUnitAPI(response.data.data)
    }
    return []
  })
}

export function nestedListUnitAPI (flatUnit) {
  if (!flatUnit || flatUnit.length === 0) return []

  const nestedList = cloneList(flatUnit.ls.filter(item => item.unitlevel === 1)).map(item1 => recursionMapUnit(item1, flatUnit.ls))
  const rootNode =
    {
      ten_dv: flatUnit.tendv_cha,
      unitlevel: 0,
      donvi_id: flatUnit.donvi_id_cha || '0',
      donvi_cha_id: 0,
      children: nestedList
    }
  return [rootNode]
}

export function getDanhSachDonViComboBox (axios) {
  return axios.post('/web-quantri/nhanvien/sp_cbo_donvi_dichvu_cha', {}).then(getDataFromResponse)
}

export function getDanhSachDonViDLComboBox (axios) {
  return axios.post('/web-quantri/nhanvien/sp_nv_donvi_dl', {}).then(getDataFromResponse)
}

// table danh sách nhân viên
export function getDanhSachNhanVienTheoDonVi (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_ds_nhanvien_donvi', param).then(getDataFromResponse)
}

export function getDanhSachHopDongComboBox (axios) {
  return axios.post('/web-quantri/nhanvien/sp_cbo_ds_hinhthuc_hopdong', {}).then(getDataFromResponse)
}

export function getDanhSachLoaiDonViComboBox (axios) {
  return axios.post('/web-quantri/khuvuc_diadanh/sp_lay_dulieu_theo_dieukien', {
    p_schema: 'admin',
    p_table_name: 'loai_dvi',
    p_value: 'loaidv_id',
    p_text: 'ten_loaidv',
    p_where: '',
    p_order: 'loaidv_id'
  }).then(getDataFromResponse)
}

// Load các table đã gán chưa gán khi khởi tạo màn hình

// Load danh sách loại Nhân Viên khi khởi tạo màn hình, fill data vào table loại nhân viên chưa gán
export function getDanhSachLoaiNhanVien (axios) {
  return axios.post('/web-quantri/nhanvien/sp_ds_loainv', {}).then(getDataFromResponse)
}

// load các table đã gán chưa gán khi chọn nhân viên

export function getDanhSachNhanVienQuanLy (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_ds_nhanvien_quanly', param).then(getDataFromResponse)
}

export function getDanhSachLoaiNhanVienTheoNhanVienId (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_ds_loainhanvien', param).then(getDataFromResponse)
}

export function getDanhSachDonViDaGan (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_ds_donvi_theo_donvidl_loaidv', param).then(getDataFromResponse)
}

export function getDanhSachDonViChuaGan (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_ds_donvi_chuagan', param).then(getDataFromResponse)
}

export function getDanhSachViTriTheoNhanVien (axios, param) {
  return axios.post('/web-quantri/nhanvien/sp_ds_vitri_nhanvien', param).then(getDataFromResponse)
}

// api insert update nhan vien

export function updateNhanVien (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_insert_update_nhanvien', param)
}
// api của modal
export function getDanhSachChucDanh (axios) {
  return axios.get('web-quantri/vitrinhanvien/ds_chucdanh', {}).then(getDataFromResponse)
}

export function getDanhSachViTriTable (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_nhanvien_vt_ds', param).then(getDataFromResponse)
}

export function updateGanViTriNhanVien (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_update_log_nhanvien_vt', param).then((response) => {
    return response &&
      response.data &&
      response.data === 'OK'
  })
}

export function deleteNhanVien (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_delete_nhanvien', param)
}

// modal khu vuc

export function getDanhSachKhuVucTable (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_lay_khuvuc_by_manv', param).then(getDataFromResponse)
}

// tìm kiếm nhân viên theo tên nhân viên hoặc mã nhân viên
export function searchStaffByCondition (axios, param) {
  return axios.post('web-quantri/nhanvien/sp_get_nhanvien_manv_tennv', param).then(getDataFromResponse)
}

export function searchStaffInStaffTable (axios, param) {
  return axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', param).then(getDataFromResponse)
}