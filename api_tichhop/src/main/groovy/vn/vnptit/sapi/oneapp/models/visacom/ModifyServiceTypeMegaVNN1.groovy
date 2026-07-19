package vn.vnptit.sapi.oneapp.models.visacom

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import ws.client.visa.common.AccountModel
import ws.client.visa.common.AccountServiceModel
import ws.client.visa.common.Login

@JsonIgnoreProperties(["metaClass"])
class ModifyServiceTypeMegaVNN1 {
    @JsonProperty("login")
    public Login login
    @JsonProperty("accountModel")
    public AccountModel accountModel
    @JsonProperty("accountServiceModel")
    public AccountServiceModel accountServiceModel
    @JsonProperty("ip")
    public String ip
    @JsonProperty("autoIpFlag")
    public boolean autoIpFlag
    @JsonProperty("oldIp")
    public boolean oldIp
    @JsonProperty("ipv6")
    public String ipv6
    @JsonProperty("autoIpV6Flag")
    public boolean autoIpV6Flag

    ModifyServiceTypeMegaVNN1() {
        this.login = new Login()
    }
}