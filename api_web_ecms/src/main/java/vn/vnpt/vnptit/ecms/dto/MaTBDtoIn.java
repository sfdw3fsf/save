package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class MaTBDtoIn {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String MA_TB;

    public String getMA_TB() {
        return MA_TB;
    }

    public void setMA_TB(String MA_TB) {
        this.MA_TB = MA_TB;
    }
}
