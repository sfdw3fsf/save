package vn.vnptit.sapi.oneapp.model.request
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * SuspendSubcriberV2 Request
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SuspendSubcriberV2Request {
    String iptvaccount
    Long reasonID
}
