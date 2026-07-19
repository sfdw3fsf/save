package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class ViTriThietBiBatchDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Valid
    private ViTriThietBiDto[] data;

    public ViTriThietBiDto[] getData() {
        return data;
    }

    public void setData(ViTriThietBiDto[] data) {
        this.data = data;
    }
}
