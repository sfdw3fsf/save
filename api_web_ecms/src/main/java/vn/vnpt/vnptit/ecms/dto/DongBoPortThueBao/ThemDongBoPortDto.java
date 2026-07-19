package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import vn.vnpt.message.ModelValidation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class ThemDongBoPortDto {
    @Valid
    @Size(min = 1, message = ModelValidation.INVALID)
    private List<DongBoPortDto> dsDongBoPort;

    public List<DongBoPortDto> getDsDongBoPort() {
        return dsDongBoPort;
    }

    public void setDsDongBoPort(List<DongBoPortDto> dsDongBoPort) {
        this.dsDongBoPort = dsDongBoPort;
    }

}
