package vn.vnptit.sapi.oneapp.models.vtc.respones

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(["metaClass"])
class ThongTinSpDetailsKasRes {
    private String validFrom;
    private String ValidTo;
    private ThongTinSpDetailsKeyListKasRes[] keyList;
    private ThongTinSpDetailsDistrListKasRes[] distrList;

    @JsonProperty("validFrom")
    public String getValidFrom() { return validFrom; }

    @JsonProperty("validFrom")
    public void setValidFrom(String value) { this.validFrom = value; }

    @JsonProperty("ValidTo")
    public String getValidTo() { return ValidTo; }

    @JsonProperty("ValidTo")
    public void setValidTo(String value) { this.ValidTo = value; }

    @JsonProperty("keyList")
    public ThongTinSpDetailsKeyListKasRes[] getKeyList() { return keyList; }

    @JsonProperty("keyList")
    public void setKeyList(ThongTinSpDetailsKeyListKasRes[] value) { this.keyList = value; }

    @JsonProperty("distrList")
    public ThongTinSpDetailsDistrListKasRes[] getDistrList() { return distrList; }

    @JsonProperty("distrList")
    public void setDistrList(ThongTinSpDetailsDistrListKasRes[] value) { this.distrList = value; }
}
