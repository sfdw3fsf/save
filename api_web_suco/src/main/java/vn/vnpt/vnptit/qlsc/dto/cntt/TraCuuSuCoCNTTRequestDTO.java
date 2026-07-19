package vn.vnpt.vnptit.qlsc.dto.cntt;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class TraCuuSuCoCNTTRequestDTO implements Serializable {
    private String maSuCo;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer donViId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer trangThaiSuCoID;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer nguonSuCoId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer dichVuId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaiHinhId;

    private Date tuNgayYC;
    private Date denNgayYC;
    private Date tuNgayHT;
    private Date denNgayHT;

    public TraCuuSuCoCNTTRequestDTO(String maSuCo, Integer donViId, Integer trangThaiSuCoID, Integer nguonSuCoId, Integer dichVuId, Integer loaiHinhId, Date tuNgayYC, Date denNgayYC, Date tuNgayHT, Date denNgayHT) {
        this.maSuCo = maSuCo;
        this.donViId = donViId;
        this.trangThaiSuCoID = trangThaiSuCoID;
        this.nguonSuCoId = nguonSuCoId;
        this.dichVuId = dichVuId;
        this.loaiHinhId = loaiHinhId;
        this.tuNgayYC = tuNgayYC;
        this.denNgayYC = denNgayYC;
        this.tuNgayHT = tuNgayHT;
        this.denNgayHT = denNgayHT;
    }

    public TraCuuSuCoCNTTRequestDTO() {
    }

    public String getMaSuCo() {
        return maSuCo;
    }

    public void setMaSuCo(String maSuCo) {
        this.maSuCo = maSuCo;
    }

    public Integer getDonViId() {
        return donViId;
    }

    public void setDonViId(Integer donViId) {
        this.donViId = donViId;
    }

    public Integer getTrangThaiSuCoID() {
        return trangThaiSuCoID;
    }

    public void setTrangThaiSuCoID(Integer trangThaiSuCoID) {
        this.trangThaiSuCoID = trangThaiSuCoID;
    }

    public Integer getNguonSuCoId() {
        return nguonSuCoId;
    }

    public void setNguonSuCoId(Integer nguonSuCoId) {
        this.nguonSuCoId = nguonSuCoId;
    }

    public Date getTuNgayYC() {
        return tuNgayYC;
    }

    public void setTuNgayYC(Date tuNgayYC) {
        this.tuNgayYC = tuNgayYC;
    }

    public Date getDenNgayYC() {
        return denNgayYC;
    }

    public void setDenNgayYC(Date denNgayYC) {
        this.denNgayYC = denNgayYC;
    }

    public Date getTuNgayHT() {
        return tuNgayHT;
    }

    public void setTuNgayHT(Date tuNgayHT) {
        this.tuNgayHT = tuNgayHT;
    }

    public Date getDenNgayHT() {
        return denNgayHT;
    }

    public void setDenNgayHT(Date denNgayHT) {
        this.denNgayHT = denNgayHT;
    }

    public Integer getDichVuId() {
        return dichVuId;
    }

    public void setDichVuId(Integer dichVuId) {
        this.dichVuId = dichVuId;
    }

    public Integer getLoaiHinhId() {
        return loaiHinhId;
    }

    public void setLoaiHinhId(Integer loaiHinhId) {
        this.loaiHinhId = loaiHinhId;
    }
}
