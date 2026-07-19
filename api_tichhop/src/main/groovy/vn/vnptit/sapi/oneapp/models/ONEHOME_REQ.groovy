package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class TaoTaiKhoan_REQ {
    String ma_khach_hang
    String ten_khach_hang
    String dia_chi
    String email
    String so_dien_thoai
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ThayDoiThongTinTaiKhoan_REQ {
    String sdt_cu
    String otp_sdt_cu
    String sdt_moi
    String otp_sdt_moi
    List<String> serial_numbers
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class HoanCong_REQ {
    String serialNumber
    String phoneNumber
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class HoanCongNhieuPhieu_REQ {
    List<HoanCong_REQ> dsPhieu
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class KhoaThietBi_REQ {
    List serialNumber
}

class OneHomeConstant {
    static Map<String, String> TaoTaiKhoan = [
            "error.ma_khach_hang_is_require" : "Mã khách hàng để trống",
            "error.ten_khach_hang_is_require": "Tên khách hàng để trống",
            "error.email_is_require"         : "Email khách hàng để trống",
            "error.email_invalid"            : "Email không hợp lệ",
            "error.dia_chi_is_require"       : "Địa chỉ khách hàng để trống",
            "error.so_dien_thoai_is_require" : "Số điện thoại khách hàng để trống",
            "error.so_dien_thoai_invalid"    : "Số điện thoại không hợp lệ",
            "error.email_duplicate"          : "Email đã tồn tại",
            "error.so_dien_thoai_exists"     : "Số điện thoại đã tồn tại"
    ]

    static Map<String, String> HoanCong = ["error.device.deviceIsNotFound"                       : "Không tìm thấy thiết bị",
                                           "error.BssCommunicationErrorKey.PhoneNumberIsNotFound": "Không tìm thấy số điện thoại",
                                           "error.BssCommunicationErrorKey.PhoneNumberIsRequired": "SĐT không được để trống",
                                           "error.BssCommunicationErrorKey.ItemOverLimit"        : "Số lượng phiếu vượt quá quy định của OneHome"
    ]

    static Map<String, String> KiemTraMaXacThuc = ["error.otpCodeInvalid": "Mã OTP không đúng"]

    static Map<String, String> ThayDoiThongTinTaiKhoan = ["error.so_dien_thoai_is_require": "Số điện thoại để trống",
                                                          "error.so_dien_thoai_invalid"   : "Số điện thoại không hợp lệ",
                                                          "error.otp_is_require"          : "OTP để trống",
                                                          "error.serial_number_is_require": "Serial để trống",
                                                          "error.khach_hang_is_not_found" : "Tài khoản k tồn tại",
                                                          "error.otpCodeInvalid"          : "Sai OTP",
                                                          "error.smartOtpCodeHasExpired"  : "OTP hết hạn",
                                                          "error.device.deviceNotExists"  : "Thiết bị không thuộc tài khoản cần cập nhật"
    ]
}