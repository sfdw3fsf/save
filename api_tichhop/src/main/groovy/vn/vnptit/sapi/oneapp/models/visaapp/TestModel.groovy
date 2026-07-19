package vn.vnptit.sapi.oneapp.models.visaapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties([ "metaClass" ])
public class TestModel {
    @JsonProperty("prop")
    public String prop
}