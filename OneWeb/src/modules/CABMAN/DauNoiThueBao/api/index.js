/* eslint-disable quotes */
export default function(RequestContext) {
  return {
    api_getThongTinCap: data =>
      RequestContext.post(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/thong-tin-cap",
        data
      ),
    api_getDanDoCap: data =>
      RequestContext.post("/web-cabman/tracuu/dan-do-cap-ketcuoi", data),
    api_saveGhiChuCap: data =>
      RequestContext.post(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/cap-nhat-ghi-chu-cap",
        data
      ),
    api_getDsThueBaoTheoMaTB: data =>
      RequestContext.post(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-thue-bao-theo-ma-tb",
        {
          ma_tb: data.ma_tb,
          kieu: data.kieu,
          dichvuvt_id: data.dichvuvt_id
        }
      ),
    api_getKetCuoiTheoKyHieu: data =>
      RequestContext.get(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/tim-ketcuoi-theo-kyhieu",
        { kyhieu: data }
      ),
    api_getPortKetCuoiRoi: data =>
      RequestContext.get(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/ds-port-ketcuoi-roi",
        data
      ),
    api_getCapGocTheoKetCuoiID: data =>
      RequestContext.get(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/capgoc-theo-ketcuoi-ngon",
        data
      ),
    api_saveNhapCapThueBao: data =>
      RequestContext.post(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/nhap-cap-thue-bao",
        data
      ),
    api_saveGiaiPhongCapThueBao: data =>
      RequestContext.post(
        "/web-cabman/dau-noi-thue-bao-vao-tuyen-cap/giai-phong-thue-bao",
        data
      )
  };
}
