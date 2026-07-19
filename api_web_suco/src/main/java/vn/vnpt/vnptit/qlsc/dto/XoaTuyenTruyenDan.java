package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class XoaTuyenTruyenDan {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long TUYENTD_ID;

    public Long getTUYENTD_ID() {
        return TUYENTD_ID;
    }

    public void setTUYENTD_ID(Long TUYENTD_ID) {
        this.TUYENTD_ID = TUYENTD_ID;
    }
}
