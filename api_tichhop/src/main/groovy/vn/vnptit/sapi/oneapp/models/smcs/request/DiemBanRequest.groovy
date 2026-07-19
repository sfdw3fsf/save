package vn.vnptit.sapi.oneapp.models.smcs.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import vn.vnptit.sapi.oneapp.models.smcs.model.AgentInfo

@JsonIgnoreProperties(["metaClass"])
class DiemBanRequest {
    @JsonProperty("account")
    public String account
    @JsonProperty("radius")
    public String radius
    @JsonProperty("Longitude")
    public String longitude
    @JsonProperty("Latitude")
    public String latitude
    @JsonProperty("type")
    public String type

    String getType() {
        return type
    }

    void setType(String type) {
        this.type = type
    }

    String getRadius() {
        return radius
    }

    void setRadius(String radius) {
        this.radius = radius
    }

    String getLongitude() {
        return longitude
    }

    void setLongitude(String longitude) {
        this.longitude = longitude
    }

    String getLatitude() {
        return latitude
    }

    void setLatitude(String latitude) {
        this.latitude = latitude
    }

    String getAccount() {
        return account
    }

    void setAccount(String account) {
        this.account = account
    }
}