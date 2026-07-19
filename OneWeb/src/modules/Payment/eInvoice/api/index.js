export default {
  Xuat_HDDT_LOAD: (axios, data) => axios.post('/web-hopdong/hopdong/fn_xuat_hddt_load', data),
  Change_MauSo: (axios, data) => axios.post('/web-ccdv/thanhtoan/sp_lay_ds_seri_dientu_v2', data),
  HienThiThongTin: (axios, data) => axios.post('/web-hopdong/hopdong/fn_xuat_hddt_hienthi_thongtin', data),
  listInvByCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus_VTT', data),
  listInvByCus_TT78: (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus', data),
  thongtin_nguoidung2: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),


  downloadInvPDFFkeyNoPay_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay_VTT', data), 
  downloadInvPDFFkeyNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay', data), 
  downloadInvFkeyNoPay_TT78: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvFkeyNoPay', data), 
  downloadInvFkeyNoPay_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvFkeyNoPay_VTT', data), 
  downloadInvNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvNoPay', data), 
  getInvViewNoPay: (axios, data) => axios.post('/tichhop/vnptinvoice/getInvViewNoPay', data),

  CapNhat: (axios, data) => axios.post('/web-hopdong/hopdong/fn_xuat_hddt_capnhat', data),
  UpdateCus: (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus', data),
  UpdateCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus_VTT', data),
  ImportAndPublishInv: (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv', data),
  ImportAndPublishInv_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv_VTT', data),
  //form hóa đơn điện tử
  hoadon_dientu_load: (axios, data) => axios.get('/web-hopdong/hopdong/fn_hoadon_dientu_load', data),
  hoadon_dientu_change_mauso:(axios, data) => axios.post('/web-hopdong/hopdong/fn_hoadon_dientu_cbopattern', data),
  hienthidanhsach_hdkh:(axios, data) => axios.post('/web-hopdong/hopdong/fn_hoadon_dientu_hienthidanhsach_hdkh', data),
  hienthidanhsach_hdtb:(axios, data) => axios.post('/web-hopdong/hopdong/fn_hoadon_dientu_gvdanhsach', data),
  doi_so_sang_chu:(axios, data) => axios.post('/web-tracuu/tracuu_th/fn_doisosangchu', data),
  //upload file
  upload_file: async (axios, data,header) => axios.post('/web-quantri/upload', data,header),
  POST_GET_KEYS: async (axios, data) => axios.post('/web-quantri/donvitinh/get_keys', data),
  GET_FKEY: (axios, data) => axios.get('/web-hopdong/ThayDoiHopDong/sinh_fkey', data),


  TraCuuThueBao: (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccount', data),
  removeVNN: (axios, data) => axios.post('/tichhop/visa-app/lookupRASAccountJson', data),
  GetSubscriberInfo: (axios, data) => axios.post('/vasc-service-new/getSubscriberInfo', data),
  CancelSubscriberV2: (axios, data) => axios.post('/vasc-service-new', data),
  DeleteMyTV: (axios, data) => axios.post('/vasc-service-new', data),
  HUY_THUEBAO: (axios, data) => axios.post('/sps/sps_service_ims_deactive', data),

  ChangeCustomerType: (axios, data) => axios.post('/visa-app/changeCustomerType', data),
  convertForVerifyFkey: (axios, data) => axios.post('tichhop/vnptinvoice/convertForVerifyFkey', data),
  convertForStore: (axios, data) => axios.post('tichhop/vnptinvoice/convertForStore', data),
  convertForStoreFkey: (axios, data) => axios.post('tichhop/vnptinvoice/convertForStoreFkey', data),
  
  unConfirmPaymentFkey: (axios, data) => axios.post('tichhop/vnptinvoice/unConfirmPaymentFkey', data),
  ConfirmPaymentFkey: (axios, data) => axios.post('tichhop/vnptinvoice/confirmPaymentFkey', data),
  ConfirmPaymentFkey_VTT: (axios, data) => axios.post('tichhop/vnptinvoice/confirmPaymentFkey_VTT', data),
  adjustInvoiceAction_tt78: (axios, data) => axios.post('tichhop/vnptinvoice/adjustInvoiceAction', data),
  adjustInvoiceAction_v2: (axios, data) => axios.post('tichhop/vnptinvoice/adjustInv', data),

  replaceInvoiceAction:(axios, data) => axios.post('tichhop/vnptinvoice/replaceInvoiceAction', data),
  cancelInv:(axios, data) => axios.post('tichhop/vnptinvoice/cancelInv', data),
  DoiCongMegavnn:(axios, data) => axios.post('tichhop/visa-app/changePortMegaVNN', data),
  modifyServiceTypeMegaVNN:(axios, data) => axios.post('tichhop/visa-app/modifyServiceTypeMegaVNN', data),
  modifyServiceTypeMegaVNN1:(axios, data) => axios.post('tichhop/visa-app/modifyServiceTypeMegaVNN1', data),
  changeBillingInfo:(axios, data) => axios.post('tichhop/visa-app/changeBillingInfo', data),
  ThayDoiHTTT:(axios, data) => axios.post('tichhop/visa-app/modifyTariffType', data),
  updateDeviceInfo:(axios, data) => axios.post('tichhop/vasc/updateDeviceInfo', data),
  unReservedAccount:(axios, data) => axios.post('tichhop/visa-app/unReservedAccount', data),
  sps_service_ims_deactive:(axios, data) => axios.post('tichhop/sps/sps_service_ims_deactive', data),
  disconnectSubscriber:(axios, data) => axios.post('tichhop/vtn/disconnectSubscriber', data),
  


  Sinh_MaGD:(axios, data) => axios.post('web-hopdong/khoiphucthanhly/sinh_magd', data),
  changePackageAndMegaVNN:(axios, data) => axios.post('tichhop/vasc/changePackageAndMegaVNN', data),
  KhoaPhieu:(axios, data) => axios.post('web-hopdong/hopdong/fn_xuat_hddt_khoaphieu', data),
  ThaoTac_Changed:(axios, data) => axios.get('web-quantri/danhmuc/ds_thamso_md/-1', data),
  XOA_GOI_V3:(axios, data) => axios.get('ccbs/gdvp/delete-package-css', data),
  getmatocdo:(axios, data) => axios.get('web-hopdong/hopdong/fn_xuat_hddt_getmatocdo', data),


  //ktra quyền đồng bộ
  get_app_config:(axios, data) => axios.post('web-hopdong/hopdong/get_app_config', data),


  //Cập nhật trạng thái hddt
  sp_capnhat_phieutt_hd_v3:(axios, data) => axios.post('web-hopdong/hopdong/sp_capnhat_phieutt_hd_v3', data),


  KiemtraXuatHoaDon:(axios, phieutt_id) => axios.get('web-hopdong/hopdong/fn_check_xuat_hddt?phieutt_id='+ phieutt_id),
  
  fn_xuat_hddt_tq_load: (axios, body) => axios.post('web-ccdv/hoadondientu/fn_xuat_hddt_tq_load', body),

  checkHuyHDThoaiTraTam: (axios, hdkh_id,tag ) => axios.post('web-hopdong/thoaitrahopdong/fn_check_huy_hd_thoaitra_tam', {hdkh_id, tag}),

  }