package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;

public class ThemLoaiTuDtoIn {

    @JsonProperty("loai_tu")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String tenLoaiTu;

    public String getTenLoaiTu() {
        return tenLoaiTu;
    }

    public void setTenLoaiTu(String tenLoaiTu) {
        this.tenLoaiTu = tenLoaiTu;
    }

}
