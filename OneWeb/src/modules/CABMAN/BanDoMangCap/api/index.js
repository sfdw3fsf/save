export default function(RequestContext) {
  return {
    api_get_ds_ttvt: data =>
      RequestContext.post("/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken"),
    api_get_ds_tokt: data =>
      RequestContext.post(
        "/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi",
        { id: data }
      ),
    api_get_ds_tram: data =>
      RequestContext.post("/web-ecms/danhmuc/layDsVeTinhTheoToQl", {
        id: data
      }),
    api_get_ds_cap: data =>
      RequestContext.get("/web-cabman/ban-do-mang-cap/danh-sach-cap", {
        donvi_id: data.donvi_id,
        nhomlc_id: data.nhomlc_id
      }),
    api_get_ds_cap_cache: data =>
      RequestContext.get("/web-cabman/ban-do-mang-cap/danh-sach-cap-cache", {
        donvi_id: data.donvi_id,
        nhomlc_id: data.nhomlc_id
      }),
    api_get_ds_ketcuoi_chua_nhapcap: cap_id =>
      RequestContext.get(
        `/web-cabman/ban-do-mang-cap/ketcuoi-chua-nhap-cap/${cap_id}`
      ),
    api_get_gian_do_cap: cap_id =>
      RequestContext.get(
        `/web-cabman/ban-do-mang-cap/gian-do-cap-v1/${cap_id}`
      ),
    api_get_gian_do_cap_cache: cap_id =>
      RequestContext.get(
        `/web-cabman/ban-do-mang-cap/gian-do-cap-v1-cache/${cap_id}`
      ),
    api_set_toado_doituong: data =>
      RequestContext.post(
        "/web-cabman/ban-do-mang-cap/cap-nhat-toado-doituong",
        data
      ),
    api_set_toado_cap: data =>
      RequestContext.post(
        "/web-cabman/ban-do-mang-cap/cap-nhat-toado-cap",
        data
      ),
    api_set_xoa_cap: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/xoa-cap?cap_id=${data.cap_id}&xoa_batbuoc=${data.xoa_batbuoc}`
      ),
    api_set_xoa_cap_cache: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/xoa-cap-cache?cap_id=${data.cap_id}&xoa_batbuoc=${data.xoa_batbuoc}`
      ),
    api_set_xoa_cap_midspan: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/xoa-cap-midspan?cap_id=${data.cap_id}&midspan_id=${data.midspan_id}`
      ),
    api_set_xoa_ketcuoi: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/xoa-ketcuoi?ketcuoi_id=${data.ketcuoi_id}&xoa_batbuoc=${data.xoa_batbuoc}`
      ),
    api_set_daochieu_cap: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/dao-chieu-cap/${data.cap_id}`
      ),
    api_set_chen_ketcuoi: data =>
      RequestContext.post(
        `/web-cabman/ban-do-mang-cap/chen-ket-cuoi?cap_id=${data.cap_id}&ketcuoi_id=${data.ketcuoi_id}&dodaicap1=${data.dodaicap1}&dodaicap2=${data.dodaicap2}`
      )
  };
}
