package vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CapNhatTienTrinh {
//    @NotNull(message = ModelValidation.NOT_NULL)
//    private Integer donViId;
//    @NotNull(message = ModelValidation.NOT_NULL)
//    private Integer nhanVienId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long tienTrinhSCId;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String noiDung;
//    @NotEmpty(message = ModelValidation.NOT_EMPTY)
//    private String nguoiCN;
//    @NotEmpty(message = ModelValidation.NOT_EMPTY)
//    private String mayCN;
//    @NotEmpty(message = ModelValidation.NOT_EMPTY)
//    private String ipCN;


//    public void setDonViId(Integer donViId) {
//        this.donViId = donViId;
//    }
//    public Integer getDonViId() {
//        return donViId;
//    }
//
//    public void setNhanvien_id(Integer nhanvien_id) {
//        this.nhanVienId = nhanVienId;
//    }
//    public Integer getNhanVienId() {
//        return nhanVienId;
//    }

    public void setTienTrinhSCId(Long tienTrinhSCId) {
        this.tienTrinhSCId = tienTrinhSCId;
    }
    public Long getTienTrinhSCId() {
        return tienTrinhSCId;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    public String getNoiDung() {
        return noiDung;
    }

//    public void setNguoiCN(String nguoiCN) {
//        this.nguoiCN = nguoiCN;
//    }
//    public String getNguoiCN() {
//        return nguoiCN;
//    }
//
//    public void setMayCN(String mayCN) {
//        this.mayCN = mayCN;
//    }
//    public String getMayCN() {
//        return mayCN;
//    }
//
//    public void setIpCN(String ipCN) {
//        this.ipCN = ipCN;
//    }
//    public String getIpCN() {
//        return ipCN;
//    }
}
