package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class CapNhatDslamShelfDtoInput extends TaoDslamShelfDtoInput{

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("dslamshelf_id")
    private Long dslamshelf_id;

    public Long getDslamshelf_id() {
        return dslamshelf_id;
    }

    public void setDslamshelf_id(Long dslamshelf_id) {
        this.dslamshelf_id = dslamshelf_id;
    }
}
