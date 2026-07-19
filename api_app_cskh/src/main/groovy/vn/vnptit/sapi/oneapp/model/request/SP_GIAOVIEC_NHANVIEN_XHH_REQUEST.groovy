package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class  SP_GIAOVIEC_NHANVIEN_XHH_REQUEST {
    Long p_phanvung_id;
    Long p_phieu_id;
    String p_ten_nhanvien_th;
    String p_so_dt;
    Integer p_nhanvien_giao_id;
    String p_nhiemvu;
    String p_ghichu;
}