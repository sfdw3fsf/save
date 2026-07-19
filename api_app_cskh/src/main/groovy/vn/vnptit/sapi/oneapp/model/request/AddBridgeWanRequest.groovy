package vn.vnptit.sapi.oneapp.model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class AddBridgeWanRequest {
    Boolean enableWan
    String optical
    String serviceDescription
    Integer vlanMux802_1Priority
    Integer vlanMuxID
}
