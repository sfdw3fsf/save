package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * RemoveVNN Request
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class RemoveVNNRequest {
    Account_Model accountModel
    String note
    String reason
}
