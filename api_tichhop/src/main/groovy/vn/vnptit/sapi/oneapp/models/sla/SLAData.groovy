package vn.vnptit.sapi.oneapp.models.sla

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Canonical
@JsonIgnoreProperties(["metaClass"])
class SLAData {
    @JsonProperty("CODE")
    String CODE
    @JsonProperty("NAME")
    String NAME
    @JsonProperty("ADDRESS")
    String ADDRESS
    @JsonProperty("MOBILE")
    String MOBILE
    @JsonProperty("TYPE")
    String TYPE
    @JsonProperty("VNPTCODE")
    String VNPTCODE
    @JsonProperty("USERNAME")
    String USERNAME
    @JsonProperty("PASSWORD")
    String PASSWORD
    @JsonProperty("EMAIL")
    String EMAIL
    @JsonProperty("EMAILSENDER")
    Long EMAILSENDER
    @JsonProperty("SMS")
    Long SMS
    @JsonProperty("ACCOUNT")
    String ACCOUNT
    @JsonProperty("BANDWIDTH")
    Long BANDWIDTH
    @JsonProperty("SERVICESPEC")
    String SERVICESPEC
    @JsonProperty("SERVICETYPE")
    String SERVICETYPE
    @JsonProperty("IP")
    String IP
    @JsonProperty("SYSTEM")
    String SYSTEM
    @JsonProperty("RACK")
    String RACK
    @JsonProperty("SHELF")
    String SHELF
    @JsonProperty("SLOT")
    String SLOT
    @JsonProperty("PORT")
    String PORT
    @JsonProperty("VPI")
    String VPI
    @JsonProperty("VCI")
    String VCI
    @JsonProperty("SLID")
    String SLID
    @JsonProperty("DEVICETYPE")
    String DEVICETYPE
    @JsonProperty("TECHTYPE")
    String TECHTYPE
    @JsonProperty("ADSL_PORT")
    String ADSL_PORT
    @JsonProperty("STATUS")
    String STATUS
    @JsonProperty("ACCOUNTS")
    public List<SLAAccount> ACCOUNTS
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Canonical
@JsonIgnoreProperties(["metaClass"])
class SLAAccount
{
    @JsonProperty("ACCOUNT")
    String ACCOUNT
    @JsonProperty("BANDWIDTH")
    Long BANDWIDTH
    @JsonProperty("SERVICESPEC")
    String SERVICESPEC
    @JsonProperty("SERVICETYPE")
    String SERVICETYPE
    @JsonProperty("IP")
    String IP
    @JsonProperty("SYSTEM")
    String SYSTEM
    @JsonProperty("RACK")
    String RACK
    @JsonProperty("SHELF")
    String SHELF
    @JsonProperty("SLOT")
    String SLOT
    @JsonProperty("PORT")
    String PORT
    @JsonProperty("VPI")
    String VPI
    @JsonProperty("VCI")
    String VCI
    @JsonProperty("SLID")
    String SLID
    @JsonProperty("DEVICETYPE")
    String DEVICETYPE
    @JsonProperty("TECHTYPE")
    String TECHTYPE
    @JsonProperty("PROVINCEpublic")
    String PROVINCE
    @JsonProperty("DISTRICTpublic")
    String DISTRICT
    @JsonProperty("WARDpublic")
    String WARD
    @JsonProperty("VNPTCODE")
    String VNPTCODE

    // IT Transit
    @JsonProperty("CODE")
    String CODE
    @JsonProperty("TRAFFIC_TOTAL")
    String TRAFFIC_TOTAL
    @JsonProperty("NIX_IXP_SPLIT")
    String NIX_IXP_SPLIT
    @JsonProperty("INTERNAL_EXTERNAL_SPLIT")
    String INTERNAL_EXTERNAL_SPLIT
    @JsonProperty("ADSL_PORT")
    String ADSL_PORT
}
