package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class ViTriPhongBatchDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Valid
    private ViTriPhongDto[] data;

    public ViTriPhongDto[] getData() {
        return data;
    }

    public void setData(ViTriPhongDto[] data) {
        this.data = data;
    }
}
