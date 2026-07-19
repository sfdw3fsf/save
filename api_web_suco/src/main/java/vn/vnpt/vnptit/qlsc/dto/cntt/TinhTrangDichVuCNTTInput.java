package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TinhTrangDichVuCNTTInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer phanvung_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long suco_id;

    public void setPhanvung_id(Integer phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public Integer getPhanvung_id() {
        return phanvung_id;
    }

    public Long getSuco_id() {
        return suco_id;
    }

    public void setSuco_id(Long suco_id) {
        this.suco_id = suco_id;
    }
}
