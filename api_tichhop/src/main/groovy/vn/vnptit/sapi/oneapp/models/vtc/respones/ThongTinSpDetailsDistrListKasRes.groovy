package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThongTinSpDetailsDistrListKasRes {
    private String key;
    private String name;
    private String value;

    @JsonProperty("key")
    public String getKey() { return key; }

    @JsonProperty("key")
    public void setKey(String value) { this.key = value; }

    @JsonProperty("name")
    public String getName() { return name; }

    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("value")
    public String getValue() { return value; }

    @JsonProperty("value")
    public void setValue(String value) { this.value = value; }
}
