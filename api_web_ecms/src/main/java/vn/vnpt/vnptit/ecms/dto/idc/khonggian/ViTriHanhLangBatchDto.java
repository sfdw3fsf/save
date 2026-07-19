package vn.vnpt.vnptit.ecms.dto.idc.khonggian;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class ViTriHanhLangBatchDto {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Valid
    private ViTriHanhLangDto[] data;

    public ViTriHanhLangDto[] getData() {
        return data;
    }

    public void setData(ViTriHanhLangDto[] data) {
        this.data = data;
    }
}
