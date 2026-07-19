package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TienTrinhSMS {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long tientrinhscId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String dsSodt;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String maSc;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(max = 500, message = ModelValidation.MAX)
    private String noidung;

    public Long getTientrinhscId() {
        return tientrinhscId;
    }

    public void setTientrinhscId(Long tientrinhscId) {
        this.tientrinhscId = tientrinhscId;
    }

    public String getDsSodt() {
        return dsSodt;
    }

    public void setDsSodt(String dsSodt) {
        this.dsSodt = dsSodt;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
