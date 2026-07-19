package vn.vnptit.sapi.oneapp.models.einvoice.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class ImportAndPublishInvRequest {
    String account
    String acPass
    String xmlInvData
    String pattern
    String serial
    int convert
}
