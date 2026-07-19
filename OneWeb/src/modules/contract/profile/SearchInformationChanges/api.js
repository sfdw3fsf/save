// [UR1.5.017] - Tìm kiếm thay đổi thông tin
// WinUI.WinUIQuanLyDanhBa.frmTC_DSThanhToan
export default {
    // UR1.5.017_001 -> Tham chiếu api UR1.1.042_001 - Sự kiện form load
    lay_chukyno_theo_kyhd: (axios, data) => axios.post(`/web-quantri/chuyennops/sp_lay_chukyno_theo_kyhd`, data),

    // UR1.5.017_002 - Load cbo điều kiện tìm kiếm
    lay_cb_timkiem: (axios, data) => axios.post(`/web-danhba/timkiem-thaydoi-tt/sp_tc_thanhtoan_dstimkiem`, data),

    // UR1.5.017_003 - Button tìm kiếm
    lay_ds_thanhtoan: (axios, data) => axios.post(`/web-danhba/timkiem-thaydoi-tt/sp_tc_ds_thanhtoan`, data),

    // UR1.5.017_004 - Cập nhật địa chỉ chứng từ
    capnhat_diachi_ct: (axios, data) => axios.post(`/web-danhba/timkiem-thaydoi-tt/sp_diachi_ct_update`, data),

    // UR1.5.017_006 - Gỡ mã in ghép
    capnhat_go_mig: (axios, data) => axios.post(`/web-danhba/timkiem-thaydoi-tt/sp_capnhat_go_mig`, data)
}