package vn.vnptit.sapi.oneapp.models.ipcc

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

import javax.validation.constraints.Pattern

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class ChangeAgentStateReq {
    String username
    String password
    String extension

    //@Pattern(regexp = "^(inbound|outbound)", message = "Các giá trị đúng của active_type: inbound, outbound")
    String active_type

    //@Pattern(regexp = "^(time_in_wc|time_in_lunch|time_in_dinner|time_in_training|time_in_teamview|time_in_others)", message = "Các giá trị đúng của reason_code: time_in_wc, time_in_lunch, time_in_dinner, time_in_training, time_in_teamview, time_in_others")
    String reason_code

    //@Pattern(regexp = "^(LOGIN|LOGOUT|WRAP_UP|READY_IB|READY_OB|NOT_READY)", message = "Các giá trị đúng của status: LOGIN, LOGOUT, WRAP_UP, READY_IB, READY_OB, NOT_READY")
    String status
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class CallObReq {
    String username
    String extension
    String dataObject
}
