package vn.vnptit.sapi.oneapp.models.bnm
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class KhoaMay_PON_Input {
    String ipgpon
    int frame
    int slot
    int port
    int ponid
    int ontid
    int loaigpon
    String comm
}
