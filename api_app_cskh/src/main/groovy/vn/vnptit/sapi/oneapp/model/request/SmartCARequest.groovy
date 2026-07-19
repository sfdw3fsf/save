package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString
import io.swagger.models.auth.In

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SP_LAY_DANHMUC_QLDN {
    Integer kieu
    String param1
    String param2
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_DS_GIAOPHIEU_NV {
    Integer vloaiphieu_id
    Integer vnhiemvu_id
    Integer vttph_id // mới
    String vtungay //  dd/mm/yyyy
    String vdenngay // dd/mm/yyyy
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SP_LAY_HUONGGIAO_TIEPTHEO {
    Integer p_huonggiao_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SP_LAY_DS_DONVI_GIAOPHIEU {
    Integer p_huonggiao_id
    Integer phieu_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_DS_NHANVIEN_GIAOPHIEU_THAOTAC {
    Integer p_huonggiao_id
    Integer p_donvi_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class GUI_OTP_V2 {
    String vphieu_ids
    String vso_dt_sms
    String vnoidung_sms
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class KT_OTP {
    String vphieu_ids
    String votp
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_THONGTIN_PHIEU_KYSO {
    String vphieu_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_THONGTIN_NGUOI_KYSO {
    String vphieu_id
} 

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SP_LAY_THONGTIN_VITRI_KYSO {
    Integer p_phieu_cha_id
    Integer p_loaiphieu_id
    Integer p_huonggiao_id
    Integer p_baocao_id
    Integer p_kieu
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class KT_THAOTAC_PHIEU_VT {
    Integer vphieu_id
    String vthaotac
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_MA_PHIEU {
    Integer vphieu_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_THONGTIN_THUKHO_KYSO {
    Integer vphieu_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class FN_CAPNHAT_MOTA_PHIEU {
    Integer p_phieu_id
    Integer p_nhanvien_id
    String p_mota
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class CAPNHAT_SAU_KYSO_V2 {
    //Integer vphanvung_id
    Integer vphieu_id
    //Integer vnhanvien_id
    //Integer vdonvi_id
    Integer vdonvi_nhan_id
    Integer vnhanvien_nhan_id
    String vnd_giao
    String vmota
    String vnguoi_cn
    String vmay_cn
    String vip_cn
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LAY_DANHMUC_QLDN {
    //Integer vphanvung_id
    //Integer vnhanvien_id
    Integer vkieu
    String vparam1
    String vparam2
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class FN_TRAPHIEU_V2 {
    //Integer vphanvung_id
    Integer p_phieu_id
    Integer p_lydotra_id
    String p_nd_tra
    String  p_ghichu_tra
    //Integer p_nhanvien_id
    //Integer p_donvi_id
    //String p_nguoi_cn
    // String p_may_cn = 'VNPT EMPLOYEE'
    // String p_ip_cn = 'VNPT EMPLOYEE'
    String p_otp
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class FN_HUYPHIEU {
    //Integer vphanvung_id
    Integer p_phieu_id
    // Integer p_nhanvien_id
    //Integer p_donvi_id
    //String p_nguoi_cn
    // String p_may_cn = 'VNPT EMPLOYEE'
    // String p_ip_cn = 'VNPT EMPLOYEE'
}