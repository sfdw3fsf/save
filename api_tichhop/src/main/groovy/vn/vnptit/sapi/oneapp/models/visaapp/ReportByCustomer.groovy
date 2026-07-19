package vn.vnptit.sapi.oneapp.models.visaapp

import ws.client.visa.application.Login
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ReportByCustomer {
    @JsonProperty("login")
    public Login login

    @JsonProperty("customerInfo")
    public String customerInfo

    @JsonProperty("operation")
    public String operation

    @JsonProperty("value")
    public String value

    @JsonProperty("pageNum")
    public int pageNum

    ReportByCustomer() {
        this.login = new Login()
    }
}