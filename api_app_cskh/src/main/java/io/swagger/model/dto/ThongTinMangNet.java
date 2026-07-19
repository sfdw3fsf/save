package io.swagger.model.dto;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class ThongTinMangNet {

    @SerializedName("operator")
    private String operator = "";

    @SerializedName("tech")
    private String tech = "";

    @SerializedName("value")
    private String value = "";

    @SerializedName("lat")
    private String lat = "";

    @SerializedName("lng")
    private String lng = "";

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
