package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotEmpty;

public class DeleteRequestDto {
    @NotEmpty(message = ModelValidation.NOT_NULL)
    private String ids = null;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
