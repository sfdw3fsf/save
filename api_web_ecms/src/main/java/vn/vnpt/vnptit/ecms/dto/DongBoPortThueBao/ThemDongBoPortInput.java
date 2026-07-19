package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import vn.vnpt.message.ModelValidation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class ThemDongBoPortInput {
    @Valid
    @Size(min = 1, message = ModelValidation.INVALID)
    private List<DongBoPort> dsDongBoPort;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer pageSize;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer pageNum;

    public List<DongBoPort> getDsDongBoPort() {
        return dsDongBoPort;
    }

    public void setDsDongBoPort(List<DongBoPort> dsDongBoPort) {
        this.dsDongBoPort = dsDongBoPort;
    }


    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
