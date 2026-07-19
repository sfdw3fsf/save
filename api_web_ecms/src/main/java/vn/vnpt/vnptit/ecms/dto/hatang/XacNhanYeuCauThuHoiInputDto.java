package vn.vnpt.vnptit.ecms.dto.hatang;

public class XacNhanYeuCauThuHoiInputDto {
    private Long id;
    private Integer trangThaiXacNhan;
    private String xacNhan;
    private Integer ngat;
    private String thucHien;
    private String thuHoiIp;
    private String soCuThuHoi;
    private String soCuMoLai;

    public Long getId() {
        return id;
    }

    public Integer getTrangThaiXacNhan() {
        return trangThaiXacNhan;
    }

    public String getXacNhan() {
        return xacNhan;
    }

    public Integer getNgat() {
        return ngat;
    }

    public String getThucHien() {
        return thucHien;
    }

    public String getThuHoiIp() {
        return thuHoiIp;
    }

    public String getSoCuThuHoi(){
        return soCuThuHoi;
    }
    
    public String getSoCuMoLai() {
        return soCuMoLai;
    }
}
