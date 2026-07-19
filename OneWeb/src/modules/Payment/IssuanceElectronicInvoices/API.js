export default {
  loadCboPattern: (axios) => axios.get('web-hopdong/hopdong/fn_xuat_hddt_file_load'),
  loadCboSerial: (axios, data) => axios.post('web-ccdv/thanhtoan/sp_lay_ds_seri_dientu_v2', data),
  loadCboKyPhatHanh: (axios) => axios.get('web-hopdong/hopdong/fn_xuat_hddt_file_hienthi_mucluc'),
  hienThiLichSu: (axios, data) => axios.post('web-hopdong/hopdong/fn_xuat_hddt_file_hienthi_lichsu', data),
  phatHanhHoaDon: (axios, data) => axios.post('web-hopdong/hopdong/fn_xuat_hddt_hoancong', data),
  capNhatKH: (axios, data) => axios.post('web-hopdong/hopdong/fn_xuat_hddt_file_capnhat_khachhang', data),
  // Api đa năng lấy tham số mặc định v2
  LAY_DS_THAMSO_MD_ALL_V2: async (axios, data) => axios.post('/web-quantri/hodan/sp_lay_dulieu_bang_theo_dieukien', data),
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  GET_FKEY: (axios) => axios.get('/web-hopdong/ThayDoiHopDong/sinh_fkey'),
  DOC_SO_THANH_CHU: (axios, data) => axios.post('web-tracuu/tracuu_th/fn_doisosangchu', data),
  CapNhat_BienDong: (axios, data) => axios.post('web-hopdong/hopdong/fn_xuat_hddt_file_capnhat_biendong', data),
  capNhatHoaDon: (axios, data) => axios.post('web-hopdong/hopdong/capnhat_hddt_hoadon', data),

  invoice_updateCustomer: (axios, data) => axios.post('tichhop/vnptinvoice/updateCus', data),
  invoice_importAndPublishInv: (axios, data) => axios.post('tichhop/vnptinvoice/importAndPublishInv', data),
  invoice_confirmPaymentFkey: (axios, data) => axios.post('tichhop/vnptinvoice/confirmPaymentFkey', data),
  thongtin_nguoidung2: (axios) => axios.post('quantri/user/thongtin_nguoidung2', {}),
}
