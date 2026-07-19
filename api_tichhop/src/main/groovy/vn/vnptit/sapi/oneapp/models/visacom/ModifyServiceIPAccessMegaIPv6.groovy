package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.common.AccountModel
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class ModifyServiceIPAccessMegaIPv6 {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("ip")
    public String ip
    @JsonProperty("ipv6Lan")
    public String ipv6Lan
    @JsonProperty("ipv4Wan")
    public String ipv4Wan
    @JsonProperty("ipv6Wan")
    public String ipv6Wan

    ModifyServiceIPAccessMegaIPv6() {
        this.login = new Login()
    }
}