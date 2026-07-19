export default function (RequestContext) {
  return {
    api_get_ds_ttvt: data =>
      RequestContext.post(
        '/web-ecms/danhmuc/layDsTTVTTrongToken'
      ),
    api_get_ds_tokt: data =>
      RequestContext.post(
        '/web-ecms/danhmuc/layDsToKT', { id: data }
      ),
    api_get_ds_tram: data =>
      RequestContext.post(
        '/web-ecms/danhmuc/layDsVeTinhTheoToQl', { id: data }),
    api_execute: (data) => RequestContext.post('/web-cabman/ban-do-mang-cap/execute1', data)
  }
}
