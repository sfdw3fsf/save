package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ThemMoiPrefixIpDtoIn {

    @JsonProperty("bras_id")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String brasId;

    @JsonProperty("prefix")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String prefix;

    @JsonProperty("tuso")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    @Min(value = 0, message = ModelValidation.MIN)
    @Max(value = 999, message = ModelValidation.MAX)
    private Integer tuso;

    @JsonProperty("denso")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    @Min(value = 0, message = ModelValidation.MIN)
    @Max(value = 999, message = ModelValidation.MAX)
    private Integer denso;

    @JsonProperty("loai_id")
    @NotNull(message = ModelValidation.NOT_EMPTY)
    private Integer loaiId;

    @JsonProperty("ds_loaitb_id")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String dsLoaitbId;

    public String getBrasId() {
        return brasId;
    }

    public void setBrasId(String brasId) {
        this.brasId = brasId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getTuso() {
        return tuso;
    }

    public void setTuso(Integer tuso) {
        this.tuso = tuso;
    }

    public Integer getDenso() {
        return denso;
    }

    public void setDenso(Integer denso) {
        this.denso = denso;
    }

    public Integer getLoaiId() {
        return loaiId;
    }

    public void setLoaiId(Integer loaiId) {
        this.loaiId = loaiId;
    }

    public String getDsLoaitbId() {
        return dsLoaitbId;
    }

    public void setDsLoaitbId(String dsLoaitbId) {
        this.dsLoaitbId = dsLoaitbId;
    }
}
