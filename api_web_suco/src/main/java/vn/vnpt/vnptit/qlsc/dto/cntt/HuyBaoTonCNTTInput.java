package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotNull;

public class HuyBaoTonCNTTInput {

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer phanvung_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Long phieu_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nnton_id;

    public Integer getPhanvung_id() { return this.phanvung_id; }
    public void setPhanvung_id(Integer phanvungId) { this.phanvung_id = phanvungId; }

    public Long getPhieu_id() {
        return phieu_id;
    }

    public void setPhieu_id(Long phieu_id) {
        this.phieu_id = phieu_id;
    }

    public Integer getNnton_id() {
        return nnton_id;
    }

    public void setNnton_id(Integer nnton_id) {
        this.nnton_id = nnton_id;
    }
}
