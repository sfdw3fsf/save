package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XoaDslamDtoIn {

    @JsonProperty("dslam_id")
    private String dslamId;

    @JsonProperty("loaitbi_id")
    private String loaiTbiId;

    public String getDslamId() {
        return dslamId;
    }

    public void setDslamId(String dslamId) {
        this.dslamId = dslamId;
    }

    public String getLoaiTbiId() {
        return loaiTbiId;
    }

    public void setLoaiTbiId(String loaiTbiId) {
        this.loaiTbiId = loaiTbiId;
    }
}
