package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;

public class TNLK_UpdateDonViVanHanhInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    private int vanHanhId;
    private int donViNhanId;

    public Integer getVanHanhId() { return this.vanHanhId; }
    public Integer getDonViNhanId() { return this.donViNhanId; }
}
