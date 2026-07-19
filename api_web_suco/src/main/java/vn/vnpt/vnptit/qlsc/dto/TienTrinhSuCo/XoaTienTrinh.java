package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class XoaTienTrinh {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long tienTrinhSCId;

    public void setTienTrinhSCId(Long tienTrinhSCId) {
        this.tienTrinhSCId = tienTrinhSCId;
    }
    public Long getTienTrinhSCId() {
        return tienTrinhSCId;
    }
}
