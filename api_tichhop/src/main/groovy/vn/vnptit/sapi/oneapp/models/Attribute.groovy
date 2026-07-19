package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class Attribute {
    @JsonProperty("type")
    public String type
    @JsonProperty("value")
    public String value

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getValue() {
        return value
    }

    void setValue(String value) {
        this.value = value == null ? "" : value
    }
}
