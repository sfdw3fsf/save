package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class TaoGiaoPhieuThuTucInput {

    @JsonProperty("p_id_phieu")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long p_id_phieu;

    @JsonProperty("p_ma_proc")
    @NotNull(message = ModelValidation.NOT_NULL)
    private String p_ma_proc;

    public Long getP_id_phieu() { return p_id_phieu; }
    public String getP_ma_proc() { return p_ma_proc; }
}
