export default {
  // API
  lay_tongtien_datcoc_csd: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/lay_tongtien_datcoc_csd', data),

  // REQ:
  // {
  //     "vma_gd": "HPG-TT/01128236"
  // }
  // RES:
  // {
  //     "error": "200",
  //     "error_code": "BSS-00000000",
  //     "message": "Success",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": "0"
  // }

  lay_matb_phieutt: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/lay_matb_phieutt', data),
  // {
  //     "vma_gd": "HPG-TT/01128236"
  // }
  // RES:
  // {
  //     "error": "200",
  //     "error_code": "BSS-00000000",
  //     "message": "Success",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": "hdlongkt"
  // }
  lay_ds_phieutt: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/lay_ds_phieutt', data),

  // {
  //     "vnhanvien_id": 452,
  //     "vtungay":  "04/08/2021",
  //     "vdenngay":  "04/08/2021",
  //     "vma_gd": 0
  // }
  // RES:
  // {
  //     "error": "204",
  //     "error_code": "BSS-00000204",
  //     "message": "No Content",
  //     "message_detail": null,
  //     "request_id": null,
  //     "data": []
  // }

  kiemtra_xoa_phieutt_v2: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/kiemtra_xoa_phieutt_v2', data),
  //   {
  //     "vphieutt_id": 497500
  // }
  lay_tttb_theo_phieutt_id: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/lay_tttb_theo_phieutt_id', data),
  //   {
  //     "vphieutt_id": 497500
  // }
  fn_tt_phieutt_hd: (axios, data) => axios.post('web-tratruoc/thongtin_tratruoc_dn/fn_tt_phieutt_hd', data),
  // { param: string, type: number }
  capnhat_status_hopdong_thuebao: (axios, data) => axios.post('web-hopdong/hopdong/capnhat_status_hopdong_thuebao', data),
  // {
  //   "hdtbId": 0,
  //   "status": 0
  // }

  xoaphieu_tthd: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/xoaphieu_tthd', data),
  //   {
  //     "vphieutt_id": 497497
  // }
  lay_thongtin_gachvi_theo_matt: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/lay_thongtin_gachvi_theo_matt', data),
  //   {
  // "vma_tt": 497497,
  // "vphieu_id": 16930216,
  // "vhdkh_id": 1921730,
  // "vkyhoadon": 0
  // }
  insert_logsvnptpay_smp_v2: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/insert_logsvnptpay_smp_v2', data),
  //   {
  // "vma_tt": 497497,
  // "vinput": 16930216,
  // "vcode": 1921730,
  // "vname": "",
  // "vdescription": "",
  // "vtransactionid": "0",
  // "vkieu": 0,
  // "vtransfertype": 0,
  // "vphieu_id": 0,
  // "vkyhoadon": 0,
  // "vhdkh_id": 0
  // }
  capnhat_tt_hoanvi: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/capnhat_tt_hoanvi', data),
  //   {
  //     "vtransactionid": "0"
  // }
  ts_thuphi_hoamangmoi_thuphi_xoaphieu: (axios, data) => axios.post('ccbs/executor/ts_thuphi_hoamangmoi_thuphi_xoaphieu', data),
  ts_laphd_chuyenquyen_sudung_xoaphieu: (axios, data) => axios.post('ccbs/executor/ts_laphd_chuyenquyen_sudung_xoaphieu', data),
  cancelTransaction: (axios, data) => axios.post('tichhop/vnptpay/cancelTransaction', data),

  get_hoadon_vt01: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/get_hoadon_vt01', data),
  in_phieuthu: (axios, data) => axios.post('web-ccdv/phieu_thanhtoan/in_phieuthu', data)
}
