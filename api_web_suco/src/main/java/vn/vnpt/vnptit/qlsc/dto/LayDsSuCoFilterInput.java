package vn.vnpt.vnptit.qlsc.dto;

import javax.validation.constraints.PositiveOrZero;
import vn.vnpt.message.ModelValidation;

public class LayDsSuCoFilterInput {

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Integer nguonSuCoID = null;

    private Integer nhomSuCoID;
    private Integer mangVtID;
    private String maSuCo;
    private String thueBao;
    private String noiDungSuCo;
    private String tuNgay; // Định dạng dd/MM/yyyy
    private String denNgay; // Định dạng dd/MM/yyyy

    public Integer getNguonSuCoID() {
        return nguonSuCoID;
    }

    public void setNguonSuCoID(Integer nguonSuCoID) {
        this.nguonSuCoID = nguonSuCoID;
    }

    public Integer getNhomSuCoID() {
        return nhomSuCoID;
    }

    public void setNhomSuCoID(Integer nhomSuCoID) {
        this.nhomSuCoID = nhomSuCoID;
    }

    public Integer getMangVtID() {
        return mangVtID;
    }

    public void setMangVtID(Integer mangVtID) {
        this.mangVtID = mangVtID;
    }

    public String getMaSuCo() {
        return maSuCo;
    }

    public void setMaSuCo(String maSuCo) {
        this.maSuCo = maSuCo;
    }

    public String getThueBao() {
        return thueBao;
    }

    public void setThueBao(String thueBao) {
        this.thueBao = thueBao;
    }

    public String getNoiDungSuCo() {
        return noiDungSuCo;
    }

    public void setNoiDungSuCo(String noiDungSuCo) {
        this.noiDungSuCo = noiDungSuCo;
    }

    public String getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(String tuNgay) {
        this.tuNgay = tuNgay;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }
}
