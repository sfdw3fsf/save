package vn.vnptit.sapi.oneapp.models.visareport

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties([ "metaClass" ])
class getIpsV6byGroupCustomer {
    @JsonProperty("brasId")
    public long brasId
    @JsonProperty("serviceId")
    public long serviceId
    @JsonProperty("groupCustomerCode")
    public long groupCustomerCode
}
