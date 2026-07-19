export default function (RequestContext) {
  return {
    //api combobox loại ranh
    api_get_ds_loairanh: (data) => RequestContext.post('/web-cabman/ban-do-ranh/danhmuc-ranh'),
    //api combobox TTVT
    api_get_ds_ttvt: (data) => RequestContext.post('/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken'),
    //api combobox tovt
    api_get_ds_tokt: (data) => RequestContext.post('/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi', { id: data }),
    //api danh sách ranh
    api_get_ds_ranhtram: (data) => RequestContext.post('/web-cabman/ban-do-ranh/danhsach-ranh', data)
  }
}
