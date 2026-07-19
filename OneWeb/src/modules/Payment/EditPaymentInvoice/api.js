// [UR2.2.008] - Sửa hóa đơn thanh toán
// WinUI.WinUIThanhtoan.frmSuaHD_ThanhToan
export default {
    // UR2.2.008_001 - Sự kiện form load
    form_load: (axios, data) => axios.post(`/web-hopdong/hoadon_thanhtoan/fn_suahd_thanhtoan_load`, data),
    //UR2.2.008_002 và UR2.2.008_003- Lấy danh sách phiếu TT sửa HĐ
    lay_danh_sach: (axios, data) => axios.post(`/web-hopdong/hoadon_thanhtoan/lay_ds_phieutt_sua_hd`, data),
    // UR2.2.008_004 - Chọn phiếu trên lưới
    item_selected_change: (axios, data) => axios.post(`/web-hopdong/hoadon_thanhtoan/fn_suadh_thanhtoan_dgvdanhsach_selecttionchanged`, data),
    // UR2.2.008_005- Cập nhật
    update_data: (axios, data) => axios.post(`/web-hopdong/hoadon_thanhtoan/fn_suahd_thanhtoan_capnhat`, data),
    fn_tt_donvi: (axios, data) => axios.post(`/web-tratruoc/thongtin_tratruoc_dn/fn_tt_donvi`, data),

    getDmHinhThucTraV3: (axios, data) => axios.get(`/web-tracuu/tracuu/sp_lay_ht_tra_theo_nghiepvu?nghiepvu_id=${data.nghiepvu_id}`),
    getDmKenhThuV3: (axios, data) => axios.get(`/web-tracuu/tracuu/sp_lay_ds_kenhthu?nghiepvu_id=${data.nghiepvu_id}&hinhthuc_tra_id=${data.ht_tra_id}`),
}