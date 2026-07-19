package vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CapNhatDongBoPortInput {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private List<Long> dsDongBoPortId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer pageSize;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer pageNum;

    public List<Long> getDsDongBoPortId() {
        return dsDongBoPortId;
    }

    public void setDsDongBoPortId(List<Long> dsDongBoPortId) {
        this.dsDongBoPortId = dsDongBoPortId;
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
