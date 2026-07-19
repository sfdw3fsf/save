package vn.vnptit.sapi.oneapp.models.bnm
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class GetPort_QuanlityV2Input {
    String linetestUser
    String linetestPassword
    String input
    String att_gpon
    String att_adsl
    String snr_adsl
    String ip
    String rack
    String shelf
    String slot
    String port
    String onu_id
    String server_dk
}
