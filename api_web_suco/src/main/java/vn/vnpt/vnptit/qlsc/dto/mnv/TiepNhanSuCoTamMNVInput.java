package vn.vnpt.vnptit.qlsc.dto.mnv;

import javax.validation.constraints.NotEmpty;

import vn.vnpt.message.ModelValidation;

public class TiepNhanSuCoTamMNVInput {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String maSuCo = null;
    private String tinhTrang = null;
    private Long nhomSuCoId;

    public String getMaSuCo() {
        return maSuCo;
    }

    public void setMaSuCo(String maSuCo) {
        this.maSuCo = maSuCo;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Long getNhomSuCoId() {
        return nhomSuCoId;
    }

    public void setNhomSuCoId(Long nhomSuCoId) {
        this.nhomSuCoId = nhomSuCoId;
    }

}