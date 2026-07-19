package vn.vnptit.sapi.oneapp.models.subman


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class XoaMay {
    @JsonProperty("tongDai")
    public int tongDai
    @JsonProperty("soMay")
    public String soMay
    @JsonProperty("viTri")
    public String viTri
}