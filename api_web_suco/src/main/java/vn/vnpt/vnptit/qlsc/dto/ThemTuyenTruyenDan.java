package vn.vnpt.vnptit.qlsc.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class ThemTuyenTruyenDan {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String TUYEN_TD;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long LOAITTD_ID;

    public String getTUYEN_TD() {
        return TUYEN_TD;
    }

    public void setTUYEN_TD(String TUYEN_TD) {
        this.TUYEN_TD = TUYEN_TD;
    }

    public Long getLOAITTD_ID() {
        return LOAITTD_ID;
    }

    public void setLOAITTD_ID(Long LOAITTD_ID) {
        this.LOAITTD_ID = LOAITTD_ID;
    }
}
