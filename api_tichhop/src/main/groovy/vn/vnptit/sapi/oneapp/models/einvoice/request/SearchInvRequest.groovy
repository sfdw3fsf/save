package vn.vnptit.sapi.oneapp.models.einvoice.request
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SearchInvRequest {
    String cusCode
    String pattern
    String serial
    String fromDate
    String toDate
    int invNumber
    int invStatus
    int page
    int cussignStatus
    int payment
}
