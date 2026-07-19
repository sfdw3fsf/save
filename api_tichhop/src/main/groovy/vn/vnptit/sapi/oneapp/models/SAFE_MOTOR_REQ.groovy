package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_TaoMoi_REQ {
    String customer_phone
    String transaction_code
    String mapping_code
    String full_name
    String serial_number
    String imei_number
    String device_phone
    String sale_unit
    String warranty_activated
    String partner_key
    String expire_date
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_ThayDoiThietBi_REQ {
    String order
    String transaction_code
    String customer
    String old_serial_number
    String old_imei_number
    String serial_number
    String imei_number
    String device_phone
    String sale_unit
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_ThayDoiThongTinKhachHang_REQ {
    String customer
    String customer_phone
    String transaction_code
    String full_name
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_ThayDoiThongTinThueBao_REQ {
    String order
    String transaction_code
    String old_device_phone
    String device_phone
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_GiaHan_REQ {
    String order
    String transaction_code
    String expire_date
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_TamNgung_REQ {
    String order
    String transaction_code
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_KhoiPhuc_REQ {
    String order
    String transaction_code
    String partner_key
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SafeMotor_HuyDichVu_REQ {
    String order
    String transaction_code
    String partner_key
}