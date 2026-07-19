package vn.vnptit.sapi.oneapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class SP_CHANGE_AGENT_STATE_REQ {
    String   username
    String   extension
    String   status
}
