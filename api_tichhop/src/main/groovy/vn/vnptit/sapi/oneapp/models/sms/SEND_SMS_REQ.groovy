package vn.vnptit.sapi.oneapp.models.sms


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class SEND_SMS_REQ {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("content")
    public String content
    @JsonProperty("type")
    public String type
    @JsonProperty("note")
    public String note
}

@JsonIgnoreProperties(["metaClass"])
class TraCuuSMSReq {
    String key
    String service
    String typeid
    String msisdn
    String startDate
    String endDate
}

@JsonIgnoreProperties(["metaClass"])
class VSHOP_SEND_SMS_REQ {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("content")
    public List<String> content
    @JsonProperty("vshopKey")
    public String vshopKey
    @JsonProperty("type")
    public String type
}