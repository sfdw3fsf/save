package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class DsPortLogicV2Dot {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamId;
    private String portIds;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long pageIndex;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long pageSize;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public String getPortIds() {
		return portIds;
	}

	public void setPortIds(String portIds) {
		this.portIds = portIds;
	}

	public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
