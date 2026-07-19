package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.common.exception.BadRequestExceptionMessage;

import java.util.List;
import java.util.stream.Collectors;

public class PhieuYeuCauThuHoiInputDto {
    private Long id;
    private String soPhieu;
    private Integer trangThaiPhieu;
    private Integer donViId;
    private Integer nguoiYcId;
    private String ngayYc;
    private String ngayThuHoi;
    private Integer spdvId;
    private String spdvTen;
    private String cumDichVu;
    private String soCuThuHoi;
    private String ngayHt;
    private Integer xacNhan;
    private Integer ngat;
    private String thucHienMayAo;
    private String thucHienBackup;
    private String thucHien;
    private String thuHoiIp;
    private String soCuMoLai;
    private String fileYc;
    private String ghiChu;
    private List<PhieuYCAoHoaDto> aoHoa;
    private List<PhieuYCK8sDto> k8s;
    private List<PhieuYCIDGDto> idg;
    private List<PhieuYCBackupDto> backup;

    public void validUpdate() {
        if (id == null || id < 1) throw new BadRequestExceptionMessage("Invalid params");
    }

    public Long getId() {
        return id;
    }

    public String getSoPhieu() {
        return soPhieu;
    }

    public Integer getTrangThaiPhieu() {
        return trangThaiPhieu;
    }

    public Integer getDonViId() {
        return donViId;
    }

    public Integer getNguoiYcId() {
        return nguoiYcId;
    }

    public String getNgayYc() {
        return ngayYc;
    }

    public String getNgayThuHoi() {
        return ngayThuHoi;
    }

    public Integer getSpdvId() {
        return spdvId;
    }

    public String getSpdvTen() {
        return spdvTen;
    }

    public String getCumDichVu() {
        return cumDichVu;
    }

    public String getSoCuThuHoi() {
        return soCuThuHoi;
    }

    public String getNgayHt() {
        return ngayHt;
    }

    public Integer getXacNhan() {
        return xacNhan;
    }

    public Integer getNgat() {
        return ngat;
    }

    public String getThucHienMayAo() {
        return thucHienMayAo;
    }

    public String getThucHienBackup() {
        return thucHienBackup;
    }

    public String getThucHien() {
        return thucHien;
    }

    public String getThuHoiIp() {
        return thuHoiIp;
    }

    public String getSoCuMoLai() {
        return soCuMoLai;
    }

    public String getFileYc() {
        return fileYc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public List<PhieuYCAoHoaDto> getAoHoa() {
        // format ip_address id
        return this.aoHoa.stream().map(item -> {
            String ipAddressIds = item.getIpAddressId();
            if (ipAddressIds!=null && ipAddressIds.contains(",")) {
                item.setIpAddressId(ipAddressIds.replaceAll(" ", ""));
            }
            return item;
        }).collect(Collectors.toList());
    }

    public List<PhieuYCK8sDto> getK8s() {
        return k8s;
    }

    public List<PhieuYCIDGDto> getIdg() {
        return idg;
    }

    public List<PhieuYCBackupDto> getBackup() {
        return backup;
    }
}
