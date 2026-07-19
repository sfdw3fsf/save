export default {
  tinh_ds_nhanvien_tcs: async (axios, data) => axios.post('/ccbs/executor/tinh_ds_nhanvien_tcs', data),
  ts_danhmuc_delete_nhanvien_tcs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_delete_nhanvien_tcs', data),
  ts_dm_nhanvientcs_capnhat: async (axios, data) => axios.post('/ccbs/executor/ts_dm_nhanvientcs_capnhat', data),
  ts_danhmuc_insert_nhanvien_tcs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_insert_nhanvien_tcs', data),
  ts_danhmuc_update_nhanvien_tcs: async (axios, data) => axios.post('/ccbs/executor/ts_danhmuc_update_nhanvien_tcs', data)
}


// /executor/ts_dm_nhanvientcs_capnhat

// {"ma_bc": "string","dienthoai_lh": "string","sms": "string","donviql_id": "string","ma_nv": "string","ten_nv": "string","dia_chi": "string"}
// /executor/ts_danhmuc_insert_nhanvien_tcs

// {"diachi": "string","donvi": "string","manhom": "string","mabc": "string","tennhom": "string","sms": "string","dienthoailh": "string"}
// /executor/ts_danhmuc_update_nhanvien_tcs

// {"diachi": "string","donvi": "string","manhom": "string","mabc": "string","tennhom": "string","sms": "string","dienthoailh": "string"}
// /executor/ts_danhmuc_delete_nhanvien_tcs

// {"manv": "string"}