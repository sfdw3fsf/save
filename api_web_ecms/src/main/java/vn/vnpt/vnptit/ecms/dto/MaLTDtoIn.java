package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class MaLTDtoIn {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String MA_LT;

    public String getMA_LT() {
        return MA_LT;
    }

    public void setMA_LT(String MA_LT) {
        this.MA_LT = MA_LT;
    }
}
