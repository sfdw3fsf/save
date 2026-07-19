package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TraPhieuCapPhatThuHoiRequestDTO implements Serializable {
    @NotNull(message = ModelValidation.NOT_NULL)
    private String reason;

    public TraPhieuCapPhatThuHoiRequestDTO(String reason) {
        this.reason = reason;
    }

    public TraPhieuCapPhatThuHoiRequestDTO() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
