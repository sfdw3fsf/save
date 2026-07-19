package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class KieuDsLamInput {
    @JsonProperty("kieuDslamId")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private int kieuDslamId;
    @JsonProperty("kieuDslam")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private String kieuDslam;

    public int getKieuDslamId() {
        return kieuDslamId;
    }

    public void setKieuDslamId(int kieuDslamId) {
        this.kieuDslamId = kieuDslamId;
    }

    public String getKieuDslam() {
        return kieuDslam;
    }

    public void setKieuDslam(String kieuDslam) {
        this.kieuDslam = kieuDslam;
    }
}
