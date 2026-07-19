export default {
    lay_ds_donvi_kho: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-donvi-kho/${data.nhanvien_id}/0`),
    lay_ds_don_hang: (axios, data) => axios.post(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-don-hang`,data),
    lay_ds_vattu_donhang: (axios, data) => axios.get(`/web-qlvt/api/tra-cuu-don-hang/lay-ds-vattu-donhang/${data.donhang_id}`),
  }
      