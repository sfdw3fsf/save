package vn.vnptit.sapi.oneapp.models.iveda

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class IVEDA_ADD_REQ {
    @JsonProperty("city_id")
    String city_id

    @JsonProperty("agent_id")
    String agent_id

    @JsonProperty("customer_id")
    String customer_id

    @JsonProperty("subscription_id")
    String subscription_id

    @JsonProperty("integrated_services")
    String integrated_services

    @JsonProperty("username")
    String username

    @JsonProperty("password")
    String password

    @JsonProperty("email")
    String email
}
