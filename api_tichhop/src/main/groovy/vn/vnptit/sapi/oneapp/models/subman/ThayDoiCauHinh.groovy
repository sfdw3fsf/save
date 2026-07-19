package vn.vnptit.sapi.oneapp.models.subman


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThayDoiCauHinh {
    @JsonProperty("tongDaiCu")
    public int tongDaiCu
    @JsonProperty("soMayCu")
    public String soMayCu
    @JsonProperty("tongDaiMoi")
    public int tongDaiMoi
    @JsonProperty("soMayMoi")
    public String soMayMoi
    @JsonProperty("viTriMoi")
    public String viTriMoi
}

class ThayDoiCauHinhResp {
    public String msg
    public boolean serviceLoss
}