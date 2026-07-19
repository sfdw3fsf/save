const API = "web-thicong";

/**@typedef {{row_id: String, ma_kh: String, ten_khachhang: String, diachi: String, ms_thue: String, ma_tt: String, ky_cuoc: String, thutu_in: Number, tenkhoanmuc_hd: String, dv_tinh: String, so_luong: Number, don_gia: Number, tien: Number}} T_NOIDUNG_HD */
/**@typedef {T_NOIDUNG_HD & {goc: T_NOIDUNG_HD}} NOIDUNG_HD */

/**
 * API Phát hành hóa đơn KHDN v1.0
 */
export default {
    /**
     * Lấy account invoice
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String} | {username: String, password: String}}, any>}
     */
    lay_account_invoice: async (axios) => await axios.post(`${API}/xuathoadon/lay_account_invoice`, null),
    /**
     * Lấy danh sách mã thanh toán cần phát hành
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: {ma_tt: String, ky_cuoc: String}[]}}, any>}
     */
    lay_ds_ma_tt_can_phathanh: async (axios) => await axios.post(`${API}/xuathoadon/lay_ds_ma_tt_can_phathanh`, null),
    /**
     * Lấy danh sách hóa đơn điện tử
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_tu_thang: String, p_den_thang: String, p_trangthai: Number, p_ds_ma_tt: String}} data
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    lay_ds_hddt: async (axios, data) => await axios.post(`${API}/xuathoadon/lay_ds_hddt`, data),
    /**
     * Thêm mã thanh toán cần phát hành vào danh sách
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_ds_ma_tt: String}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    them_ma_tt_can_phathanh: async (axios, data) => await axios.post(`${API}/xuathoadon/them_ma_tt_can_phathanh`, data),
    /**
     * Xóa danh sách mã thanh toán cần phát hành
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    xoa_ds_ma_tt_can_phathanh: async (axios) => await axios.post(`${API}/xuathoadon/xoa_ds_ma_tt_can_phathanh`, null),
    /**
     * Xóa mã thanh toán cần phát hành
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_ds_ma_tt: String, p_ky_cuoc: Number}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    xoa_ma_tt_can_phathanh: async (axios, data) => await axios.post(`${API}/xuathoadon/xoa_ma_tt_can_phathanh`, data),
    /**
     * Xem trước hóa đơn
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: any[]}}, any>}
     */
    xemtruoc_hoadon: async (axios) => await axios.post(`${API}/xuathoadon/xemtruoc_hoadon`, null),
    /**
     * Kiểm tra danh sách mã thanh toán cần phát hành
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: {ma_tt: String, ky_cuoc: Number, xml: String, cus?: String}[]}}, any>}
     */
    kiemtra_ds_ma_tt_can_phathanh: async (axios) => await axios.post(`${API}/xuathoadon/kiemtra_ds_ma_tt_can_phathanh`, null),
    /**
     * Cập nhật trạng thái phát hành
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_ma_tt: String, p_ky_cuoc: Number, p_no: Number | String, p_pattern: String, p_serial: String, p_ngay_ph: String, p_fkey?: String}} data
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    capnhat_trangthai_phathanh: async (axios, data) => await axios.post(`${API}/xuathoadon/capnhat_trangthai_phathanh`, data),
    /**
     * Lấy danh sách hóa đơn điện tử
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_tu_thang: String, p_den_thang: String, p_trangthai: Number, p_ds_ma_tt: String}} data
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    lay_ds_hddt_xuat_xls: async (axios, data) => await axios.post(`${API}/xuathoadon/lay_ds_hddt_xuat_xls`, data),

    /**
     * Lấy nội dung hóa đơn
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_ds_ma_tt: String}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: NOIDUNG_HD[]}}, any>}
     */
    lay_noidung_hoadon: async (axios, data) => await axios.post(`${API}/xuathoadon/lay_noidung_hoadon`, data),
    /**
     * Cập nhật nội dung hóa đơn
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{p_noidung_hd: String}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: String}}, any>}
     */
    capnhat_noidung_hoadon: async (axios, data) => await axios.post(`${API}/xuathoadon/capnhat_noidung_hoadon`, data),
    /**
     * Lấy danh sách serial
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @return {axios.AxiosResponse<{data:{error_message?: String, data?: { pattern: String, serial: String }[]}}, any>}
     */
    lay_ds_serial: async (axios) => await axios.post(`${API}/xuathoadon/lay_ds_serial`, null),

    /**
     * Tải PDF theo Fkey
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{userName: String, userPass: String, fkey: String}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{faultString: String, errorCode: Number, data: String}, any>}
     */
    downloadInvPDFFkeyNoPay: async (axios, data) => await axios.post(`/tichhop/vnptinvoice/downloadInvPDFFkeyNoPay`, data),
    /**
     * Tải XML theo Fkey
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{userName: String, userPass: String, fkey: String}} data - Tham số đầu vào
     * @returns {axios.AxiosResponse<{faultString: String, errorCode: Number, data: String}, any>}
     */
    downloadInvFkeyNoPay: async (axios, data) => await axios.post(`/tichhop/vnptinvoice/downloadInvFkeyNoPay`, data),
    /**
     * Phát hành hóa đơn
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{account: String, acPass: String, convert: 0|1, pattern: String, serial: String, xmlInvData: String, cusCode: String}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<{faultString: String, errorCode: Number, data: String}, any>}
     */
    importAndPublishInv: async (axios, data) => await axios.post(`/tichhop/vnptinvoice/importAndPublishInv`, data),
    /**
     * Cập nhật thông tin khách hàng
     * @param {axios.AxiosStatic} axios - Đối tượng axios
     * @param {{account: String, acPass: String, xmlCusData: String, convert: 0 | 1}} data - Tham số đầu vào
     * @return {axios.AxiosResponse<number, any>}
     */
    updateCus: async (axios, data) => await axios.post(`/tichhop/vnptinvoice/updateCus`, data),
}