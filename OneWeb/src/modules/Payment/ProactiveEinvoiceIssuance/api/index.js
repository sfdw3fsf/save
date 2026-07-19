export default {
    upload_file: async (axios, data, header) => axios.post('/web-quantri/upload', data, header),
    loadForm: (axios, data) => axios.post('/web-ccdv/hoadondientu/fn_xuat_hddt_tq_load', data),
    layMauSo: (axios) => axios.get('/web-quantri/danhmuc-chung/CSS_LOAI_HOADON'),
    layThaoTac: (axios, data) => axios.post('/web-hopdong/thanhtoan/sp_lay_ds_thaotac_hddt', data),
    layDieuChinh: (axios, data) => axios.post('/web-hopdong/thanhtoan/sp_lay_ds_dieuchinh_hddt', data),
    layLoaiKhachHang: (axios, data) => axios.post('/web-hopdong/thanhtoan/sp_lay_ds_loai_kh_hddt', data),
    laySeri: (axios, data) => axios.post('/web-ccdv/thanhtoan/sp_lay_ds_seri_dientu_v2', data),
    // timKiemBienDongHopDong: (axios, data) => axios.post('/web-hopdong/thanhtoan/timkiem-biendong-hopdongdientu', data),
    //{ "ma_tt": "HPG-04-558589" }
    layThamSoMacDinh: (axios, data) => axios.post('/web-ccdv/thanhly/sp_lay_ds_thamso_md', data),
    // {"kieu_id":-1}
    traCuuDSThanhToan: (axios, data) => axios.post('web-hopdong/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId=' + data),
    // web-tracuu/hopdong/ds_hopdong_tt_theo_hdkh_id?hdkhId=1947272
    capNhatHDDTVTT: (axios, data) => axios.post('/web-ccdv/thanhtoan/fn_capnhat_hddt_vtt', data),
    layThongTinNguoiDung: (axios, data) => axios.post('/quantri/user/thongtin_nguoidung2', data),
    docTienChu: (axios, data) => axios.post('/web-tracuu/tracuu_th/fn_doisosangchu', data),
    hienThiThongTin: (axios, data) => axios.post('/web-hopdong/thanhtoan/timkiem-biendong-hopdongdientu', data),
    // { "ma_tt": "" , "fkey": "", "kieu_id": 3}
    hienThiThongTinHopDong: (axios, data) => axios.post('/web-hopdong/hoadondientu/fn_xuat_hddt_tq_hienthi_thongtin_hopdong', data),
    sinhKey: (axios, data) => axios.get('/web-hopdong/thanhtoan/sinh_fkey?kieu=' + data),
    SINH_MA_KH_HDDT: (axios, data) => axios.get('/web-hopdong/thanhtoan/sinh_ma_kh_hddt?kieu=' + data),


    //*------------------------------------------*//

    // Envoice
    downloadInvNoPay_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/downloadInvNoPay_VTT', data),
    UnConfirmPaymentFkey_VTT_TT78: (axios, data) => axios.post('/tichhop/vnptinvoice/unConfirmPaymentFkey_VTT', data),
    UnConfirmPaymentFkey_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/unConfirmPaymentFkey_VTT', data),
    cancelInv_VTT_TT78: (axios, data) => axios.post('/tichhop/vnptinvoice/cancelInv_VTT', data),
    cancelInv_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/cancelInv_VTT', data),
    listInvByCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/listInvByCus_VTT', data),
    convertForVerify_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/convertForVerifyFkey_VTT', data),
    convertForStore_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/convertForStoreFkey_VTT', data),
    confirmPaymentFkey_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/confirmPaymentFkey_VTT', data),
    UpdateCus_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/updateCus_VTT', data),
    ImportAndPublishInv_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/importAndPublishInv_VTT', data),
    AdjustInvoiceAction_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/adjustInvoiceAction_VTT', data),
    ReplaceInvoiceAction_VTT: (axios, data) => axios.post('/tichhop/vnptinvoice/replaceInvoiceAction_VTT', data),

}