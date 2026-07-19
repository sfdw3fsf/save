package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class ViTriRackBatchDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Valid
    private ViTriRackDto[] data;

    public ViTriRackDto[] getData() {
        return data;
    }

    public void setData(ViTriRackDto[] data) {
        this.data = data;
    }
}
