export default function(RequestContext) {
  return {
    api_get_ds_ttvt: data =>
      RequestContext.post("/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken"),
    api_get_ds_ranhtram: data =>
      RequestContext.post("/web-cabman/ban-do-mang-cap/danhsach_ranh_tram", {
        p_ttvt_id: data
      })
  };
}
