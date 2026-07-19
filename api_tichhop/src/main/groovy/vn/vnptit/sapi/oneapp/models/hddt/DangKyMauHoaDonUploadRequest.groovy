package vn.vnptit.sapi.oneapp.models.hddt
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class DangKyMauHoaDonUploadRequest {
    String cus_id
    String taxcode
    Integer templateType
    String invcateid
    String xmlFile
    String xsltFile
    String xsdFile
}
