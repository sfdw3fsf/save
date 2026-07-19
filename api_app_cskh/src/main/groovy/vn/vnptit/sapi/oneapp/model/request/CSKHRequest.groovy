package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString


@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class  FN_CAPNHAT_VATTU_SUDUNG_VT_REQUEST {
    Long vsdvt_id
    Long vthuebao_id
    String vserial
    Long vvattu_id
    Long vthietbi_id
    String vmay_cn = 'VNPT EMPLOYEE'
    String vip_cn = 'VNPT EMPLOYEE'
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class  FN_CAPNHAT_DOI_TBI_SERIAL_REQUEST {
    Long vsdvt_id
    String vserial_cu
    String vserial_moi
    String vserial_gp_moi
    String vmay_cn = 'VNPT EMPLOYEE'
    String vip_cn = 'VNPT EMPLOYEE'
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class  SNTOGPONSN_WS_REQUEST {
    String vserial
}