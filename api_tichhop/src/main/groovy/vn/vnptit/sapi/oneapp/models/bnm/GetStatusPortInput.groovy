package vn.vnptit.sapi.oneapp.models.bnm
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class GetStatusPortInput {
    String vinput
}
