package vn.vnptit.sapi.oneapp.models.einvoice.request
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class AdjustInvoiceRequest {
    String account
    String acPass
    String xmlInvData
    String fkey
    String attachFile
    int convert
}
