/* eslint-disable quotes */
export default function (RequestContext) {
  return {
    layDsTTVT: () =>
      // RequestContext.post("/web-ecms/danhmuc/layDsTTVTTheoNVTrongToken"),
      RequestContext.post("/web-ecms/danhmuc/layDsTTVTTrongToken"),
    layDsTOQL: donvi_id =>
      // eslint-disable-next-line quotes
      // RequestContext.post("/web-ecms/danhmuc/layDsToKTTheoNVTrongTokenVaDonVi", {
      //   id: donvi_id
      // }),
      RequestContext.post("/web-ecms/danhmuc/layDsToKT", {
        id: donvi_id
      }),

    layDsTuyenCot: donvi_id =>
      // eslint-disable-next-line quotes
      RequestContext.get("/web-cabman/danhmuc/cot-cap/ds-tuyen-cot", {
        donvi_id
      }),
    layDsCot: data =>
      // eslint-disable-next-line quotes
      RequestContext.post("/web-cabman/danhmuc/cot-cap/ds-cot", data),
    tracuuCot: query =>
      // eslint-disable-next-line quotes
      RequestContext.get("/web-cabman/danhmuc/cot-cap/tra-cuu", {
        query
      })
  }
}
