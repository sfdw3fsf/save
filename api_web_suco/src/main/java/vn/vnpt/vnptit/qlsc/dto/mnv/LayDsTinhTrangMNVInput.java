package vn.vnpt.vnptit.qlsc.dto.mnv;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LayDsTinhTrangMNVInput {
    @NotNull(message = "Nhóm sự cố không được để trống")
    @JsonProperty("nhomSuCoId")
    private Integer nhomScId;

    @NotNull(message = "Mã sự cố không được để trống")
    @JsonProperty("maSuCo")
    private String maSc;

    public Integer getNhomScId() {
        return nhomScId;
    }

    public void setNhomScId(Integer nhomScId) {
        this.nhomScId = nhomScId;
    }

    public String getMaSc() {
        return maSc;
    }

    public void setMaSc(String maSc) {
        this.maSc = maSc;
    }
}
