package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import vn.vnpt.message.ModelValidation;

public class CapNhatHeThongDichVuInput {
	private CapNhatHeThongDichVuDto capNhatDto;

	private Integer actionType;

	public String getActionType() {
		if (this.actionType == 1) {
			return "u";
		}
		return "a";
	}

	public CapNhatHeThongDichVuDto getCapNhatDto() {
		return capNhatDto;
	}

	public void setCapNhatDto(CapNhatHeThongDichVuDto capNhatDto) {
		this.capNhatDto = capNhatDto;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
}
