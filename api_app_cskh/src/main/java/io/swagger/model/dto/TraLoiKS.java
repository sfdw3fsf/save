package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * TraLoiKS
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TraLoiKS {
    @JsonProperty("cauHoiId")
    private Long cauHoiId;
    @JsonProperty("traLoiId")
    private Long traLoiId;
    @JsonProperty("noiDung")
    private String noiDung;

    public Long getCauHoiId() {
        return cauHoiId;
    }

    public void setCauHoiId(Long cauHoiId) {
        this.cauHoiId = cauHoiId;
    }

    public Long getTraLoiId() {
        return traLoiId;
    }

    public void setTraLoiId(Long traLoiId) {
        this.traLoiId = traLoiId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
