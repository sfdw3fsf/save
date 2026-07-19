package vn.vnptit.sapi.oneapp.models.hddt
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class CapNhatKhachhangRequest {
    String short_name
    String company_name
    String taxcode
    String cus_id
    String org
    String address
    String represent_person
    String tel
    String email
    Boolean is_sme
    Integer system_type
    String taxauthority_code
    Integer service_type

}
