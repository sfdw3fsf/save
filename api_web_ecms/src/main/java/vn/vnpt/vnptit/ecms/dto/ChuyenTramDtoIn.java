package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class ChuyenTramDtoIn {
    private Long phanvung_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private String type;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long tramvt_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslam_id;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long turack_id;
    private List<Long> list_tu_id;
    public Long getPhanvung_id() {
        return phanvung_id;
    }

    public String getType() {
        return type;
    }

    public Long getTramvt_id() {
        return tramvt_id;
    }

    public Long getDslam_id() {
        return dslam_id;
    }

    public Long getTurack_id() {
        return turack_id;
    }

    public List<Long> getTuId() {
        return list_tu_id;
    }

    public void setTuId(List<Long> tuId) {
        this.list_tu_id = tuId;
    }
}
