package vn.vnptit.sapi.oneapp.models.visacom


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class LookupRASAccountNoLog {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountName")
    public String accountName

    LookupRASAccountNoLog() {
        this.login = new Login()
    }
}