package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;
import java.util.Date;

public class TimSuCo {
    private String maSuCo;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer donViId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer trangThaiSuCoID;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nguonSuCoId;

    private Date tuNgayYC;
    private Date denNgayYC;
    private Date tuNgayHT;
    private Date denNgayHT;

    public void setMaSuCo(String maSuCo) {
        this.maSuCo = maSuCo;
    }
    public String getMaSuCo() {
        return maSuCo;
    }

    public void setDonViId(Integer donViId) {
        this.donViId = donViId;
    }
    public Integer getDonViId() {
        return donViId;
    }

    public void setTrangThaiSuCoID(Integer trangThaiSuCoID) {
        this.trangThaiSuCoID = trangThaiSuCoID;
    }
    public Integer getTrangThaiSuCoID() {
        return trangThaiSuCoID;
    }

    public void setNguonSuCoId(Integer nguonSuCoId) {
        this.nguonSuCoId = nguonSuCoId;
    }
    public Integer getNguonSuCoId() {
        return nguonSuCoId;
    }

    public void setTuNgayYC(Date tuNgayYC) {
        this.tuNgayYC = tuNgayYC;
    }
    public Date getTuNgayYC() {
        return tuNgayYC;
    }

    public void setDenNgayYC(Date denNgayYC) {
        this.denNgayYC = denNgayYC;
    }
    public Date getDenNgayYC() {
        return denNgayYC;
    }

    public void setTuNgayHT(Date tuNgayHT) {
        this.tuNgayHT = tuNgayHT;
    }
    public Date getTuNgayHT() {
        return tuNgayHT;
    }

    public void setDenNgayHT(Date denNgayHT) {
        this.denNgayHT = denNgayHT;
    }
    public Date getDenNgayHT() {
        return denNgayHT;
    }


}
